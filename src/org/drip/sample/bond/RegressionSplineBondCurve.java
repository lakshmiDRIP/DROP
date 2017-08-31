
package org.drip.sample.bond;

import java.util.*;

import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.*;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.support.Helper;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.BondBuilder;
import org.drip.product.definition.Bond;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.grid.OverlappingStretchSpan;
import org.drip.spline.params.*;
import org.drip.spline.stretch.*;
import org.drip.state.curve.DiscountFactorDiscountCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * RegressionSplineBondCurve demonstrates the Functionality behind the Regression Spline based OLS best-fit
 *  Construction of a Bond Discount Curve Based on Input Price/Yield.
 *
 * @author Lakshmi Krishnamurthy
 */

public class RegressionSplineBondCurve {

	static class CashFlowYieldDF {
		double _dblCumulativeCashFlow = java.lang.Double.NaN;
		double _dblDiscountedCumulativeCashFlow = java.lang.Double.NaN;

		CashFlowYieldDF (
			final double dblCashFlow,
			final double dblYieldDF)
		{
			_dblDiscountedCumulativeCashFlow = (_dblCumulativeCashFlow = dblCashFlow) * dblYieldDF;
		}

		void accumulate (
			final double dblCashFlow,
			final double dblYieldDF)
		{
			_dblCumulativeCashFlow += dblCashFlow;
			_dblDiscountedCumulativeCashFlow += dblCashFlow * dblYieldDF;
		}

		double cumulativeCashFlow()
		{
			return _dblCumulativeCashFlow;
		}

		double discountedCumulativeCashFlow()
		{
			return _dblDiscountedCumulativeCashFlow;
		}

		double weightedDF()
		{
			return _dblDiscountedCumulativeCashFlow / _dblCumulativeCashFlow;
		}
	}

	private static final SegmentCustomBuilderControl PolynomialSplineSegmentBuilder()
		throws Exception
	{
		int iCk = 2;
		int iNumPolyBasis = 4;

		SegmentInelasticDesignControl sdic = new SegmentInelasticDesignControl (
			iCk,
			null, // SegmentFlexurePenaltyControl (iLengthPenaltyDerivativeOrder, dblLengthPenaltyAmplitude)
			null  // SegmentFlexurePenaltyControl (iCurvaturePenaltyDerivativeOrder, dblCurvaturePenaltyAmplitude)
		);

		return new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
			new PolynomialFunctionSetParams (iNumPolyBasis),
			sdic,
			null,
			null
		);
	}

	private static final Bond FixedCouponBond (
		final String strName,
		final JulianDate dtEffective,
		final JulianDate dtMaturity,
		final double dblCoupon,
		final String strCurrency,
		final String strDayCount,
		final int iFreq)
		throws Exception
	{
		return BondBuilder.CreateSimpleFixed (
			strName,
			strCurrency,
			"",
			dblCoupon,
			iFreq,
			strDayCount,
			dtEffective,
			dtMaturity,
			null,
			null
		);
	}

	private static final Bond[] CalibBondSet (
		final String strCurrency,
		final String strDayCount)
		throws Exception
	{
		Bond bond1 = FixedCouponBond (
			"MBONO  8.00  12/17/2015",
			DateUtil.CreateFromYMD (
				2006,
				DateUtil.JANUARY,
				5
			),
			DateUtil.CreateFromYMD (
				2015,
				DateUtil.DECEMBER,
				17
			),
			0.08,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond2 = FixedCouponBond (
			"MBONO  6.25  06/16/2016",
			DateUtil.CreateFromYMD (
				2011,
				DateUtil.JULY,
				22
			),
			DateUtil.CreateFromYMD (
				2016,
				DateUtil.JUNE,
				16
			),
			0.08,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond3 = FixedCouponBond (
			"MBONO  7.25  12/15/2016",
			DateUtil.CreateFromYMD (
				2007,
				DateUtil.FEBRUARY,
				1
			),
			DateUtil.CreateFromYMD (
				2016,
				DateUtil.DECEMBER,
				15
			),
			0.0725,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond4 = FixedCouponBond (
			"MBONO  5.00  06/15/2017",
			DateUtil.CreateFromYMD (
				2012,
				DateUtil.JULY,
				19
			),
			DateUtil.CreateFromYMD (
				2017,
				DateUtil.JUNE,
				15
			),
			0.0500,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond5 = FixedCouponBond (
			"MBONO  7.75  12/14/2017",
			DateUtil.CreateFromYMD (
				2008,
				DateUtil.JANUARY,
				31
			),
			DateUtil.CreateFromYMD (
				2017,
				DateUtil.DECEMBER,
				14
			),
			0.0775,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond6 = FixedCouponBond (
			"MBONO  4.75  06/14/2018",
			DateUtil.CreateFromYMD (
				2013,
				DateUtil.AUGUST,
				30
			),
			DateUtil.CreateFromYMD (
				2018,
				DateUtil.JUNE,
				14
			),
			0.0475,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond7 = FixedCouponBond (
			"MBONO  8.50  12/13/2018",
			DateUtil.CreateFromYMD (
				2009,
				DateUtil.FEBRUARY,
				12
			),
			DateUtil.CreateFromYMD (
				2018,
				DateUtil.DECEMBER,
				13
			),
			0.085,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond8 = FixedCouponBond (
			"MBONO  5.00  12/11/2019",
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.NOVEMBER,
				7
			),
			DateUtil.CreateFromYMD (
				2019,
				DateUtil.DECEMBER,
				11
			),
			0.05,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond9 = FixedCouponBond (
			"MBONO  8.00  06/11/2020",
			DateUtil.CreateFromYMD (
				2010,
				DateUtil.FEBRUARY,
				25
			),
			DateUtil.CreateFromYMD (
				2020,
				DateUtil.JUNE,
				11
			),
			0.08,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond10 = FixedCouponBond (
			"MBONO  6.50  06/10/2021",
			DateUtil.CreateFromYMD (
				2011,
				DateUtil.FEBRUARY,
				3
			),
			DateUtil.CreateFromYMD (
				2021,
				DateUtil.JUNE,
				10
			),
			0.065,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond11 = FixedCouponBond (
			"MBONO  6.50  06/09/2022",
			DateUtil.CreateFromYMD (
				2012,
				DateUtil.FEBRUARY,
				15
			),
			DateUtil.CreateFromYMD (
				2022,
				DateUtil.JUNE,
				9
			),
			0.065,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond12 = FixedCouponBond (
			"MBONO  8.00  12/07/2023",
			DateUtil.CreateFromYMD (
				2003,
				DateUtil.OCTOBER,
				30
			),
			DateUtil.CreateFromYMD (
				2023,
				DateUtil.DECEMBER,
				7
			),
			0.065,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond13 = FixedCouponBond (
			"MBONO 10.00  12/05/2024",
			DateUtil.CreateFromYMD (
				2005,
				DateUtil.JANUARY,
				20
			),
			DateUtil.CreateFromYMD (
				2024,
				DateUtil.DECEMBER,
				5
			),
			0.1,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond14 = FixedCouponBond (
			"MBONO  7.50  06/03/2027",
			DateUtil.CreateFromYMD (
				2007,
				DateUtil.JANUARY,
				18
			),
			DateUtil.CreateFromYMD (
				2027,
				DateUtil.JUNE,
				3
			),
			0.075,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond15 = FixedCouponBond (
			"MBONO  8.50  05/31/2029",
			DateUtil.CreateFromYMD (
				2009,
				DateUtil.JANUARY,
				15
			),
			DateUtil.CreateFromYMD (
				2029,
				DateUtil.MAY,
				31
			),
			0.085,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond16 = FixedCouponBond (
			"MBONO  7.75  05/29/2031",
			DateUtil.CreateFromYMD (
				2009,
				DateUtil.SEPTEMBER,
				11
			),
			DateUtil.CreateFromYMD (
				2031,
				DateUtil.MAY,
				29
			),
			0.0775,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond17 = FixedCouponBond (
			"MBONO  7.75  11/23/2034",
			DateUtil.CreateFromYMD (
				2014,
				DateUtil.APRIL,
				11
			),
			DateUtil.CreateFromYMD (
				2034,
				DateUtil.NOVEMBER,
				23
			),
			0.0775,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond18 = FixedCouponBond (
			"MBONO 10.00  11/20/2036",
			DateUtil.CreateFromYMD (
				2006,
				DateUtil.OCTOBER,
				26
			),
			DateUtil.CreateFromYMD (
				2036,
				DateUtil.NOVEMBER,
				20
			),
			0.1,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond19 = FixedCouponBond (
			"MBONO  8.50  11/18/2038",
			DateUtil.CreateFromYMD (
				2009,
				DateUtil.JANUARY,
				29
			),
			DateUtil.CreateFromYMD (
				2038,
				DateUtil.NOVEMBER,
				18
			),
			0.085,
			strCurrency,
			strDayCount,
			2
		);

		Bond bond20 = FixedCouponBond (
			"MBONO  7.75  11/13/2042",
			DateUtil.CreateFromYMD (
				2012,
				DateUtil.APRIL,
				20
			),
			DateUtil.CreateFromYMD (
				2042,
				DateUtil.NOVEMBER,
				13
			),
			0.0775,
			strCurrency,
			strDayCount,
			2
		);

		return new Bond[] {
			bond1,
			bond2,
			bond3,
			bond4,
			bond5,
			bond6,
			bond7,
			bond8,
			bond9,
			bond10,
			bond11,
			bond12,
			bond13,
			bond14,
			bond15,
			bond16,
			bond17,
			bond18,
			bond19,
			bond20
		};
	}

	private static final Map<JulianDate, CashFlowYieldDF> BondYieldFlows (
		final Bond[] aBond,
		final double[] adblYield,
		final int iValueDate)
		throws Exception
	{
		Map<JulianDate, CashFlowYieldDF> mapDateYieldDF = new TreeMap<JulianDate, CashFlowYieldDF>();

		ValuationParams valParams = new ValuationParams (
			new JulianDate (iValueDate),
			new JulianDate (iValueDate),
			""
		);

		for (int i = 0; i < aBond.length; ++i) {
			for (CompositePeriod cp : aBond[i].couponPeriods()) {
				if (cp.payDate() <= iValueDate) continue;

				double dblCashFlow = aBond[i].couponMetrics (
					cp.endDate(),
					valParams,
					null
				).rate() / aBond[i].freq();

				double dblYieldDF = Helper.Yield2DF (
					aBond[i].freq(),
					adblYield[i],
					Convention.YearFraction (
						iValueDate,
						cp.payDate(),
						aBond[i].couponDC(),
						false,
						null,
						aBond[i].currency()
					)
				);

				JulianDate dtPay = new JulianDate (cp.payDate());

				if (mapDateYieldDF.containsKey (dtPay))
					mapDateYieldDF.get (dtPay).accumulate (
						dblCashFlow,
						dblYieldDF
					);
				else
					mapDateYieldDF.put (
						dtPay,
						new CashFlowYieldDF (
							dblCashFlow,
							dblYieldDF
						)
					);
			}

			JulianDate dtMaturity = aBond[i].maturityDate();

			double dblYieldDF = Helper.Yield2DF (
				aBond[i].freq(),
				adblYield[i],
				Convention.YearFraction (
					iValueDate,
					dtMaturity.julian(),
					aBond[i].couponDC(),
					false,
					null,
					aBond[i].currency()
				)
			);

			if (mapDateYieldDF.containsKey (dtMaturity))
				mapDateYieldDF.get (dtMaturity).accumulate (
					1.,
					dblYieldDF
				);
			else
				mapDateYieldDF.put (
					dtMaturity,
					new CashFlowYieldDF (
						1.,
						dblYieldDF
					)
				);
		}

		return mapDateYieldDF;
	}

	private static final StretchBestFitResponse SBFR (
		final Map<JulianDate, CashFlowYieldDF> mapDateYieldDF)
		throws Exception
	{
		int iMapSize = mapDateYieldDF.size();

		int i = 0;
		int[] aiDate = new int[iMapSize];
		double[] adblYieldDF = new double[iMapSize];
		double[] adblWeight = new double[iMapSize];

		for (Map.Entry<JulianDate, CashFlowYieldDF> me : mapDateYieldDF.entrySet()) {
			aiDate[i] = me.getKey().julian();

			adblYieldDF[i] = me.getValue().weightedDF();

			adblWeight[i] = me.getValue().cumulativeCashFlow();

			++i;
		}

		return StretchBestFitResponse.Create (
			aiDate,
			adblYieldDF,
			adblWeight
		);
	}

	private static final MultiSegmentSequence BondRegressionSplineStretch (
		final JulianDate dtSpot,
		final Bond[] aBondSet,
		final int iNumKnots,
		final Map<JulianDate, CashFlowYieldDF> mapDateDF)
		throws Exception
	{
		SegmentCustomBuilderControl scbc = PolynomialSplineSegmentBuilder();

		double dblXStart = dtSpot.julian();

		double dblXFinish = aBondSet[aBondSet.length - 1].maturityDate().julian();

		double adblX[] = new double[iNumKnots + 2];
		adblX[0] = dblXStart;

		for (int i = 1; i < adblX.length; ++i)
			adblX[i] = adblX[i - 1] + (dblXFinish - dblXStart) / (iNumKnots + 1);

		SegmentCustomBuilderControl[] aSCBC = new SegmentCustomBuilderControl[adblX.length - 1]; 

		for (int i = 0; i < adblX.length - 1; ++i)
			aSCBC[i] = scbc;

		return MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
			"SPLINE_STRETCH",
			adblX,
			1.,
			null,
			aSCBC,
			SBFR (mapDateDF), 
			BoundarySettings.NaturalStandard(),
			MultiSegmentSequence.CALIBRATE
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iNumKnots = 10;
		String strCurrency = "MXN";
		String strDayCount = "30/360";

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2015,
			DateUtil.JUNE,
			13
		);

		double[] aCalibYield = new double[] {
			0.0315960,
			0.0354184,
			0.0389543,
			0.0412860,
			0.0435245,
			0.0464521,
			0.0486307,
			0.0524561,
			0.0532168,
			0.0562230,
			0.0585227,
			0.0606205,
			0.0611038,
			0.0637935,
			0.0648727,
			0.0661705,
			0.0673744,
			0.0675774,
			0.0683684,
			0.0684978
		};

		Bond[] aBondSet = CalibBondSet (
			strCurrency,
			strDayCount
		);

		Map<JulianDate, CashFlowYieldDF> mapDateDF = BondYieldFlows (
			aBondSet,
			aCalibYield,
			dtSpot.julian()
		);

		MultiSegmentSequence mss = BondRegressionSplineStretch (
			dtSpot,
			aBondSet,
			iNumKnots,
			mapDateDF
		);

		MergedDiscountForwardCurve dfdc = new DiscountFactorDiscountCurve (
			strCurrency,
			new OverlappingStretchSpan (mss)
		);

		System.out.println ("\n\n\t|--------------------------------------------|");

		System.out.println ("\t|  Curve Stretch [" +
			new JulianDate ((int) mss.getLeftPredictorOrdinateEdge()) + " -> " +
			new JulianDate ((int) mss.getRightPredictorOrdinateEdge()) + "]  |"
		);

		System.out.println ("\t|--------------------------------------------|");

		for (Map.Entry<JulianDate, CashFlowYieldDF> me : mapDateDF.entrySet()) {
			System.out.println (
				"\t|\t " + me.getKey() + " => " +
				FormatUtil.FormatDouble (me.getValue().weightedDF(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (dfdc.df (me.getKey().julian()), 1, 4, 1.) + "     |"
			);
		}

		System.out.println ("\t|--------------------------------------------|\n\n");

		System.out.println ("\t|---------------------------------------------------------------||");

		System.out.println ("\t|     Market Yield vs. Regression Curve                         ||");

		System.out.println ("\t|---------------------------------------------------------------||");

		System.out.println ("\t|     L -> R                                                    ||");

		System.out.println ("\t|           Bond Name                                           ||");

		System.out.println ("\t|           Market Yield                                        ||");

		System.out.println ("\t|           Regressed Yield (Bond Basis)                        ||");

		System.out.println ("\t|           Regressed Yield (Yield Spread)                      ||");

		System.out.println ("\t|           Continuous Zero To Maturity                         ||");

		System.out.println ("\t|---------------------------------------------------------------||");

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			""
		);

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Discount (dfdc);

		for (int i = 0; i < aBondSet.length; ++i) {
			System.out.println (
				"\t| " + aBondSet[i].name() + " ==> " +
				FormatUtil.FormatDouble (aCalibYield[i], 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (aBondSet[i].yieldFromBondBasis (
					valParams,
					mktParams,
					null,
					0.
				), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (aBondSet[i].yieldFromYieldSpread (
					valParams,
					mktParams,
					null,
					0.
				), 1, 2, 100.) + "% | " +
				FormatUtil.FormatDouble (dfdc.zero (
					aBondSet[i].maturityDate().julian()
				), 1, 2, 100.) + "% || "
			);
		}

		System.out.println ("\t|---------------------------------------------------------------||\n\n");
	}
}
