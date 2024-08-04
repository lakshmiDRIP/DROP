
package org.drip.sample.ois;

import java.util.*;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.support.*;
import org.drip.function.r1tor1.*;
import org.drip.function.r1tor1operator.Flat;
import org.drip.market.definition.OvernightIndex;
import org.drip.market.otc.*;
import org.drip.numerical.common.*;
import org.drip.param.creator.*;
import org.drip.param.market.*;
import org.drip.param.period.*;
import org.drip.param.valuation.*;
import org.drip.product.creator.*;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.rates.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.*;
import org.drip.spline.stretch.*;
import org.drip.state.creator.*;
import org.drip.state.discount.*;
import org.drip.state.estimator.LatentStateStretchBuilder;
import org.drip.state.identifier.*;
import org.drip.state.inference.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>CrossOvernightStream</i> demonstrates the construction, customization, and valuation of Cross-Currency
 * 	Overnight Floating Streams.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/ois/README.md">Index/Fund OIS Curve Reconcilation</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CrossOvernightFloatingStream {

	private static final FixFloatComponent OTCOISFixFloat (
		final JulianDate dtSpot,
		final String strCurrency,
		final String strMaturityTenor,
		final double dblCoupon)
	{
		FixedFloatSwapConvention ffConv = OvernightFixedFloatContainer.FundConventionFromJurisdiction (
			strCurrency
		);

		return ffConv.createFixFloatComponent (
			dtSpot,
			strMaturityTenor,
			dblCoupon,
			0.,
			1.
		);
	}

	/*
	 * Construct the Array of Deposit Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final SingleStreamComponent[] DepositInstrumentsFromMaturityDays (
		final JulianDate dtEffective,
		final String strCurrency,
		final int[] aiDay)
		throws Exception
	{
		SingleStreamComponent[] aDeposit = new SingleStreamComponent[aiDay.length];

		for (int i = 0; i < aiDay.length; ++i)
			aDeposit[i] = SingleStreamComponentBuilder.Deposit (
				dtEffective,
				dtEffective.addBusDays (
					aiDay[i],
					strCurrency
				),
				OvernightLabel.Create (
					strCurrency
				)
			);

		return aDeposit;
	}

	/*
	 * Construct the Array of Swap Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FixFloatComponent[] OISFromMaturityTenor (
		final JulianDate dtSpot,
		final String strCurrency,
		final String[] astrMaturityTenor,
		final double[] adblCoupon)
		throws Exception
	{
		FixFloatComponent[] aOIS = new FixFloatComponent[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			aOIS[i] = OTCOISFixFloat (
				dtSpot,
				strCurrency,
				astrMaturityTenor[i],
				adblCoupon[i]
			);

		return aOIS;
	}

	private static final MergedDiscountForwardCurve CustomOISCurveBuilderSample (
		final JulianDate dtSpot,
		final String strCurrency)
		throws Exception
	{
		/*
		 * Construct the Array of Deposit Instruments and their Quotes from the given set of parameters
		 */

		SingleStreamComponent[] aDepositComp = DepositInstrumentsFromMaturityDays (
			dtSpot,
			strCurrency,
			new int[] {
				1, 2, 3, 7, 14, 21, 30, 60
			}
		);

		double[] adblDepositQuote = new double[] {
			0.01200, 0.01200, 0.01200, 0.01450, 0.01550, 0.01600, 0.01660, 0.01850, // Cash
		};

		/*
		 * Construct the Deposit Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec depositStretch = LatentStateStretchBuilder.ForwardFundingStretchSpec (
			"DEPOSIT",
			aDepositComp,
			"ForwardRate",
			adblDepositQuote
		);

		/*
		 * Construct the Array of EDF Instruments and their Quotes from the given set of parameters
		 */

		SingleStreamComponent[] aEDFComp = SingleStreamComponentBuilder.ForwardRateFuturesPack (
			dtSpot,
			4,
			strCurrency
		);

		double[] adblEDFQuote = new double[] {
			0.01612, 0.01580, 0.01589, 0.01598
		};

		/*
		 * Construct the Cash Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec edfStretch = LatentStateStretchBuilder.ForwardFundingStretchSpec (
			"EDF",
			aEDFComp,
			"ForwardRate",
			adblEDFQuote
		);

		/*
		 * Construct the Array of OIS Instruments and their Quotes from the given set of parameters
		 */

		double[] adblOISQuote = new double[] {
			0.02604,    //  4Y
			0.02808,    //  5Y
			0.02983,    //  6Y
			0.03136,    //  7Y
			0.03268,    //  8Y
			0.03383,    //  9Y
			0.03488     // 10Y
		};

		CalibratableComponent[] aOISComp = OISFromMaturityTenor (
			dtSpot,
			strCurrency,
			new java.lang.String[] {
				"4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y"
			},
			adblOISQuote
		);

		/*
		 * Construct the OIS Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec oisStretch = LatentStateStretchBuilder.ForwardFundingStretchSpec (
			"SWAP",
			aOISComp,
			"SwapRate",
			adblOISQuote
		);

		LatentStateStretchSpec[] aStretchSpec = new LatentStateStretchSpec[] {
			depositStretch,
			edfStretch,
			oisStretch
		};

		/*
		 * Set up the Linear Curve Calibrator using the following parameters:
		 * 	- Cubic Exponential Mixture Basis Spline Set
		 * 	- Ck = 2, Segment Curvature Penalty = 2
		 * 	- Quadratic Rational Shape Controller
		 * 	- Natural Boundary Setting
		 */

		LinearLatentStateCalibrator lcc = new LinearLatentStateCalibrator (
			new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (4),
				SegmentInelasticDesignControl.Create (
					2,
					2
				),
				new ResponseScalingShapeControl (
					true,
					new QuadraticRationalShapeControl (0.)
				),
				null
			),
			BoundarySettings.NaturalStandard(),
			MultiSegmentSequence.CALIBRATE,
			null,
			null
		);

		/*
		 * Construct the Shape Preserving Discount Curve by applying the linear curve calibrator to the array
		 *  of Cash and Swap Stretches.
		 */

		return ScenarioDiscountCurveBuilder.ShapePreservingDFBuild (
			strCurrency,
			lcc,
			aStretchSpec,
			new ValuationParams (
				dtSpot,
				dtSpot,
				strCurrency
			),
			null,
			null,
			null,
			1.
		);
	}

	private static final LatentStateFixingsContainer SetFlatOvernightFixings (
		final JulianDate dtStart,
		final JulianDate dtEnd,
		final JulianDate dtValue,
		final ForwardLabel fri,
		final double dblFlatFixing,
		final double dblNotional)
		throws Exception
	{
		LatentStateFixingsContainer lsfc = new LatentStateFixingsContainer();

		double dblAccount = 1.;

		int iPrevDate = dtStart.julian();

		JulianDate dt = dtStart.addDays (1);

		while (dt.julian() <= dtEnd.julian()) {
			lsfc.add (
				dt,
				fri,
				dblFlatFixing
			);

			if (dt.julian() <= dtValue.julian()) {
				double dblAccrualFraction = Convention.YearFraction (
					iPrevDate,
					dt.julian(),
					"Act/360",
					false,
					null,
					"USD"
				);

				dblAccount *= (1. + dblFlatFixing * dblAccrualFraction);
			}

			iPrevDate = dt.julian();

			dt = dt.addBusDays (
				1,
				"USD"
			);
		}

		System.out.println ("\tManual Calc Float Accrued (Geometric Compounding): " + (dblAccount - 1.) * dblNotional);

		System.out.println ("\tManual Calc Float Accrued (Arithmetic Compounding): " +
			((dtValue.julian() - dtStart.julian()) * dblNotional * dblFlatFixing / 360.));

		return lsfc;
	}

	private static final Map<String, Double> CompoundingRun (
		final ForwardLabel fri)
		throws Exception
	{
		double dblOISVol = 0.3;
		double dblUSDFundingVol = 0.3;
		double dblUSDFundingUSDOISCorrelation = 0.3;

		String strCurrency = fri.currency();

		JulianDate dtToday = org.drip.analytics.date.DateUtil.Today().addTenorAndAdjust (
			"0D",
			strCurrency
		);

		MergedDiscountForwardCurve dc = CustomOISCurveBuilderSample (
			dtToday,
			strCurrency
		);

		JulianDate dtCustomOISStart = dtToday.subtractTenor ("2M");

		JulianDate dtCustomOISMaturity = dtToday.addTenor ("4M");

		CompositePeriodSetting cpsFloating = new CompositePeriodSetting (
			360,
			"ON",
			strCurrency,
			null,
			-1.,
			null,
			null,
			null,
			null
		);

		ComposableFloatingUnitSetting cfusFloating = new ComposableFloatingUnitSetting (
			"ON",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_OVERNIGHT,
			null,
			fri,
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		List<Integer> lsFloatingStreamEdgeDate = CompositePeriodBuilder.OvernightEdgeDates (
			dtCustomOISStart,
			dtCustomOISStart.addTenorAndAdjust (
				"6M",
				strCurrency
			),
			strCurrency
		);

		Stream floatStream = new Stream (
			CompositePeriodBuilder.FloatingCompositeUnit (
				lsFloatingStreamEdgeDate,
				cpsFloating,
				cfusFloating
			)
		);

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Create (
			dc,
			null,
			null,
			null,
			null,
			null,
			SetFlatOvernightFixings (
				dtCustomOISStart,
				dtCustomOISMaturity,
				dtToday,
				fri,
				0.003,
				-1.
			)
		);

		ValuationParams valParams = new ValuationParams (
			dtToday,
			dtToday,
			strCurrency
		);

		FundingLabel fundingLabelUSD = FundingLabel.Standard ("USD");

		mktParams.setForwardVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtToday.julian(),
				VolatilityLabel.Standard (fri),
				fri.currency(),
				dblOISVol
			)
		);

		mktParams.setFundingVolatility (
			ScenarioDeterministicVolatilityBuilder.FlatForward (
				dtToday.julian(),
				VolatilityLabel.Standard (fundingLabelUSD),
				"USD",
				dblUSDFundingVol
			)
		);

		mktParams.setForwardFundingCorrelation (
			fri,
			fundingLabelUSD,
			new Flat (dblUSDFundingUSDOISCorrelation)
		);

		return floatStream.value (
			valParams,
			null,
			mktParams,
			null
		);
	}

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		String strCurrency = "USD";

		Map<String, Double> mapArithmeticOutput = CompoundingRun (
			OvernightLabel.Create (
				strCurrency
			)
		);

		Map<String, Double> mapGeometricOutput = CompoundingRun (
			ForwardLabel.Create (
				new OvernightIndex (
					strCurrency + "OIS",
					"OIS",
					strCurrency,
					"Act/360",
					strCurrency,
					"ON",
					0,
					CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
				),
				"ON"
			)
		);

		System.out.println ("\n\t-----------------------------------");

		System.out.println ("\t  GEOMETRIC |  ARITHMETIC | CHECK");

		System.out.println ("\t-----------------------------------\n");

		for (Map.Entry<String, Double> meGeometric : mapGeometricOutput.entrySet()) {
			String strKey = meGeometric.getKey();

			double dblGeometricMeasure = meGeometric.getValue();

			double dblArithmeticMeasure = mapArithmeticOutput.get (strKey);

			String strMatch = NumberUtil.WithinTolerance (
				dblGeometricMeasure,
				dblArithmeticMeasure,
				1.e-08,
				1.e-04
			) ?
			"MATCH " :
			"DIFFER";

			System.out.println ("\t" +
				FormatUtil.FormatDouble (dblGeometricMeasure, 1, 8, 1.) + " | " +
				FormatUtil.FormatDouble (dblArithmeticMeasure, 1, 8, 1.) + " | " +
				strMatch + " <= " + strKey
			);
		}

		EnvManager.TerminateEnv();
	}
}
