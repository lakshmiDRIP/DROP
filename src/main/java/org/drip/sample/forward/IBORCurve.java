
package org.drip.sample.forward;

import java.util.List;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.*;
import org.drip.market.otc.*;
import org.drip.param.creator.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.*;
import org.drip.param.valuation.*;
import org.drip.product.creator.SingleStreamComponentBuilder;
import org.drip.product.fra.FRAStandardComponent;
import org.drip.product.fx.ComponentPair;
import org.drip.product.rates.*;
import org.drip.service.common.FormatUtil;
import org.drip.spline.params.*;
import org.drip.spline.stretch.*;
import org.drip.state.creator.ScenarioForwardCurveBuilder;
import org.drip.state.discount.*;
import org.drip.state.estimator.LatentStateStretchBuilder;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.ForwardLabel;
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
 * <i>IBORCurve</i> illustrates the Construction and Usage of the IBOR Forward Curve.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/forward/README.md">IBOR Spline Forward Curve Construction</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class IBORCurve {

	private static final FloatFloatComponent OTCFloatFloat (
		final JulianDate dtSpot,
		final String strCurrency,
		final String strDerivedTenor,
		final String strMaturityTenor,
		final double dblBasis)
	{
		FloatFloatSwapConvention ffConv = IBORFloatFloatContainer.ConventionFromJurisdiction (strCurrency);

		return ffConv.createFloatFloatComponent (
			dtSpot,
			strDerivedTenor,
			strMaturityTenor,
			dblBasis,
			1.
		);
	}

	private static final FixFloatComponent OTCIRS (
		final JulianDate dtSpot,
		final String strCurrency,
		final String strLocation,
		final String strMaturityTenor,
		final String strIndex,
		final double dblCoupon)
	{
		FixedFloatSwapConvention ffConv = IBORFixedFloatContainer.ConventionFromJurisdiction (
			strCurrency,
			strLocation,
			strMaturityTenor,
			strIndex
		);

		return ffConv.createFixFloatComponent (
			dtSpot,
			strMaturityTenor,
			dblCoupon,
			0.,
			1.
		);
	}

	private static final ComponentPair OTCComponentPair (
		final JulianDate dtSpot,
		final String strCurrency,
		final String strDerivedTenor,
		final String strMaturityTenor,
		final double dblReferenceFixedCoupon,
		final double dblDerivedFixedCoupon,
		final double dblBasis)
	{
		FloatFloatSwapConvention ffConv = IBORFloatFloatContainer.ConventionFromJurisdiction (strCurrency);

		return ffConv.createFixFloatComponentPair (
			dtSpot,
			strDerivedTenor,
			strMaturityTenor,
			dblReferenceFixedCoupon,
			dblDerivedFixedCoupon,
			dblBasis,
			1.
		);
	}

	/*
	 * Construct the Array of Deposit Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final SingleStreamComponent[] DepositFromMaturityDays (
		final JulianDate dtEffective,
		final String[] astrMaturityTenor,
		final ForwardLabel fri)
		throws Exception
	{
		if (null == astrMaturityTenor || 0 == astrMaturityTenor.length) return null;

		SingleStreamComponent[] aDeposit = new SingleStreamComponent[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			aDeposit[i] = SingleStreamComponentBuilder.Deposit (
				dtEffective,
				dtEffective.addTenor (astrMaturityTenor[i]),
				fri
			);

		return aDeposit;
	}

	/*
	 * Construct the Array of FRA from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FRAStandardComponent[] FRAFromMaturityDays (
		final JulianDate dtEffective,
		final ForwardLabel fri,
		final String[] astrMaturityTenor,
		final double[] adblFRAStrike)
		throws Exception
	{
		if (null == astrMaturityTenor || null == adblFRAStrike || 0 == astrMaturityTenor.length) return null;

		FRAStandardComponent[] aFRA = new FRAStandardComponent[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			aFRA[i] = SingleStreamComponentBuilder.FRAStandard (
				dtEffective.addTenor (astrMaturityTenor[i]),
				fri,
				adblFRAStrike[i]
			);

		return aFRA;
	}

	private static final FixFloatComponent[] FixFloatSwap2 (
		final JulianDate dtEffective,
		final ForwardLabel fri,
		final String[] astrMaturityTenor)
		throws Exception
	{
		if (null == astrMaturityTenor || 0 == astrMaturityTenor.length) return null;

		FixFloatComponent[] aFixFloat = new FixFloatComponent[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			aFixFloat[i] = OTCIRS (
				dtEffective,
				fri.currency(),
				"ALL",
				astrMaturityTenor[i],
				"MAIN",
				0.
			);

		return aFixFloat;
	}

	private static final FixFloatComponent[] FixFloatSwap (
		final JulianDate dtValue,
		final ForwardLabel fri,
		final String[] astrMaturityTenor)
		throws Exception
	{
		if (null == astrMaturityTenor || 0 == astrMaturityTenor.length) return null;

		JulianDate dtEffective = dtValue.addDays (2);

		String strCurrency = fri.currency();

		FixFloatComponent[] aFFC = new FixFloatComponent[astrMaturityTenor.length];

		int iTenorInMonths = Integer.parseInt (fri.tenor().split ("M")[0]);

		UnitCouponAccrualSetting ucasFixed = new UnitCouponAccrualSetting (
			1,
			"Act/360",
			false,
			"Act/360",
			false,
			strCurrency,
			true,
			CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
		);

		ComposableFloatingUnitSetting cfusFloating = new ComposableFloatingUnitSetting (
			fri.tenor(),
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			fri,
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		CompositePeriodSetting cpsFloating = new CompositePeriodSetting (
			12 / iTenorInMonths,
			fri.tenor(),
			strCurrency,
			null,
			-1.,
			null,
			null,
			null,
			null
		);

		CashSettleParams csp = new CashSettleParams (
			0,
			strCurrency,
			0
		);

		for (int i = 0; i < astrMaturityTenor.length; ++i) {
			int iTenorCompare = Helper.TenorCompare (
				astrMaturityTenor[i],
				"6M"
			);

			String strFixedTenor = Helper.LEFT_TENOR_LESSER == iTenorCompare ? astrMaturityTenor[i] : "6M";

			ComposableFixedUnitSetting cfusFixed = new ComposableFixedUnitSetting (
				strFixedTenor,
				CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
				null,
				0.,
				0.,
				strCurrency
			);

			CompositePeriodSetting cpsFixed = new CompositePeriodSetting (
				1,
				strFixedTenor,
				strCurrency,
				null,
				1.,
				null,
				null,
				null,
				null
			);

			List<Integer> lsFixedStreamEdgeDate = CompositePeriodBuilder.BackwardEdgeDates (
				dtEffective,
				dtEffective.addTenor (astrMaturityTenor[i]),
				"1Y",
				null,
				CompositePeriodBuilder.SHORT_STUB
			);

			List<Integer> lsFloatingStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
				dtEffective,
				fri.tenor(),
				astrMaturityTenor[i],
				null
			);

			Stream floatingStream = new Stream (
				CompositePeriodBuilder.FloatingCompositeUnit (
					lsFloatingStreamEdgeDate,
					cpsFloating,
					cfusFloating
				)
			);

			Stream fixedStream = new Stream (
				CompositePeriodBuilder.FixedCompositeUnit (
					lsFixedStreamEdgeDate,
					cpsFixed,
					ucasFixed,
					cfusFixed
				)
			);

			aFFC[i] = new FixFloatComponent (
				fixedStream,
				floatingStream,
				csp
			);

			aFFC[i].setPrimaryCode ("FixFloat:" + astrMaturityTenor[i]);
		}

		return aFFC;
	}

	/*
	 * Construct an array of float-float swaps from the corresponding reference (6M) and the derived legs.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FloatFloatComponent[] FloatFloatSwap (
		final JulianDate dtSpot,
		final ForwardLabel fri,
		final String[] astrMaturityTenor)
		throws Exception
	{
		if (null == astrMaturityTenor || 0 == astrMaturityTenor.length) return null;

		FloatFloatComponent[] aFFC = new FloatFloatComponent[astrMaturityTenor.length];

		int iTenorInMonths = Integer.parseInt (fri.tenor().split ("M")[0]);

		String strCurrency = fri.currency();

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			aFFC[i] = OTCFloatFloat (
				dtSpot,
				strCurrency,
				iTenorInMonths + "M",
				astrMaturityTenor[i],
				0.
			);

		return aFFC;
	}

	/*
	 * Construct an array of fix-float component pairs from the corresponding reference (6M) and the derived legs.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final ComponentPair[] FixFloatComponentPair (
		final JulianDate dtSpot,
		final CurveSurfaceQuoteContainer csqs,
		final ForwardLabel friDerived,
		final String[] astrMaturityTenor)
		throws Exception
	{
		if (null == astrMaturityTenor || 0 == astrMaturityTenor.length) return null;

		ComponentPair[] aFFCP = new ComponentPair[astrMaturityTenor.length];

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			friDerived.currency()
		);

		for (int i = 0; i < astrMaturityTenor.length; ++i) {
			ComponentPair cp = OTCComponentPair (
				dtSpot,
				friDerived.currency(),
				friDerived.tenor(),
				astrMaturityTenor[i],
				0.,
				0.,
				0.
			);

			double dblReferenceFixedCoupon = cp.referenceComponent().measureValue (
				valParams,
				null,
				csqs,
				null,
				"FairPremium"
			);

			double dblDerivedFixedCoupon = cp.derivedComponent().measureValue (
				valParams,
				null,
				csqs,
				null,
				"FairPremium"
			);

			aFFCP[i] = OTCComponentPair (
				dtSpot,
				friDerived.currency(),
				friDerived.tenor(),
				astrMaturityTenor[i],
				dblReferenceFixedCoupon,
				dblDerivedFixedCoupon,
				0.
			);
		}

		return aFFCP;
	}

	public static final ForwardCurve CustomIBORBuilderSample (
		final MergedDiscountForwardCurve dc,
		final ForwardCurve fcReference,
		final ForwardLabel fri,
		final SegmentCustomBuilderControl scbc,
		final String[] astrDepositTenor,
		final double[] adblDepositQuote,
		final String strDepositCalibMeasure,
		final String[] astrFRATenor,
		final double[] adblFRAQuote,
		final String strFRACalibMeasure,
		final String[] astrFixFloatTenor,
		final double[] adblFixFloatQuote,
		final String strFixFloatCalibMeasure,
		final String[] astrFloatFloatTenor,
		final double[] adblFloatFloatQuote,
		final String strFloatFloatCalibMeasure,
		final String[] astrSyntheticFloatFloatTenor,
		final double[] adblSyntheticFloatFloatQuote,
		final String strSyntheticFloatFloatCalibMeasure,
		final String strHeaderComment,
		final boolean bPrintMetric)
		throws Exception
	{
		if (bPrintMetric) {
			System.out.println ("\n\t----------------------------------------------------------------");

			System.out.println ("\t     " + strHeaderComment);

			System.out.println ("\t----------------------------------------------------------------");
		}

		JulianDate dtValue = dc.epoch();

		SingleStreamComponent[] aDeposit = DepositFromMaturityDays (
			dtValue,
			astrDepositTenor,
			fri
		);

		/*
		 * Construct the Deposit Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec depositStretch = LatentStateStretchBuilder.ForwardStretchSpec (
			"DEPOSIT",
			aDeposit,
			strDepositCalibMeasure,
			adblDepositQuote
		);

		FRAStandardComponent[] aFRA = FRAFromMaturityDays (
			dtValue,
			fri,
			astrFRATenor,
			adblFRAQuote
		);

		/*
		 * Construct the FRA Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec fraStretch = LatentStateStretchBuilder.ForwardStretchSpec (
			"FRA",
			aFRA,
			strFRACalibMeasure,
			adblFRAQuote
		);

		FixFloatComponent[] aFixFloat = FixFloatSwap2 (
			dtValue,
			fri,
			astrFixFloatTenor
		);

		/*
		 * Construct the Fix-Float Component Set Stretch Builder
		 */

		LatentStateStretchSpec fixFloatStretch = LatentStateStretchBuilder.ForwardStretchSpec (
			"FIXFLOAT",
			aFixFloat,
			strFixFloatCalibMeasure,
			adblFixFloatQuote
		);

		FloatFloatComponent[] aFloatFloat = FloatFloatSwap (
			dtValue,
			fri,
			astrFloatFloatTenor
		);

		/*
		 * Construct the Float-Float Component Set Stretch Builder
		 */

		LatentStateStretchSpec floatFloatStretch = LatentStateStretchBuilder.ForwardStretchSpec (
			"FLOATFLOAT",
			aFloatFloat,
			strFloatFloatCalibMeasure,
			adblFloatFloatQuote
		);

		FloatFloatComponent[] aSyntheticFloatFloat = FloatFloatSwap (
			dtValue,
			fri,
			astrSyntheticFloatFloatTenor
		);

		/*
		 * Construct the Synthetic Float-Float Component Set Stretch Builder
		 */

		LatentStateStretchSpec syntheticFloatFloatStretch = LatentStateStretchBuilder.ForwardStretchSpec (
			"SYNTHETICFLOATFLOAT",
			aSyntheticFloatFloat,
			strSyntheticFloatFloatCalibMeasure,
			adblSyntheticFloatFloatQuote
		);

		LatentStateStretchSpec[] aStretchSpec = new LatentStateStretchSpec[] {
			depositStretch,
			fraStretch,
			fixFloatStretch,
			floatFloatStretch,
			syntheticFloatFloatStretch
		};

		/*
		 * Set up the Linear Curve Calibrator using the following parameters:
		 * 	- Cubic Exponential Mixture Basis Spline Set
		 * 	- Ck = 2, Segment Curvature Penalty = 2
		 * 	- Quadratic Rational Shape Controller
		 * 	- Natural Boundary Setting
		 */

		LinearLatentStateCalibrator lcc = new LinearLatentStateCalibrator (
			scbc,
			BoundarySettings.NaturalStandard(),
			MultiSegmentSequence.CALIBRATE,
			null,
			null
		);

		ValuationParams valParams = new ValuationParams (
			dtValue,
			dtValue,
			fri.currency()
		);

		/*
		 * Set the discount curve based component market parameters.
		 */

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Create (
			dc,
			fcReference,
			null,
			null,
			null,
			null,
			null,
			null
		);

		/*
		 * Construct the Shape Preserving Forward Curve by applying the linear curve calibrator to the array
		 *  of Deposit and Swap Stretches.
		 */

		ForwardCurve fcDerived = ScenarioForwardCurveBuilder.ShapePreservingForwardCurve (
			lcc,
			aStretchSpec,
			fri,
			valParams,
			null,
			mktParams,
			null,
			null == adblDepositQuote || 0 == adblDepositQuote.length ? adblFRAQuote[0] : adblDepositQuote[0]
		);

		/*
		 * Set the discount curve + cubic polynomial forward curve based component market parameters.
		 */

		mktParams.setForwardState (fcDerived);

		if (bPrintMetric) {
			/*
			 * Cross-Comparison of the Deposit Calibration Instrument "Forward" metric.
			 */

			if (null != aDeposit && null != adblDepositQuote) {
				System.out.println ("\t----------------------------------------------------------------");

				System.out.println ("\t     DEPOSIT INSTRUMENTS QUOTE RECOVERY");

				System.out.println ("\t----------------------------------------------------------------");

				for (int i = 0; i < aDeposit.length; ++i)
					System.out.println ("\t[" + aDeposit[i].effectiveDate() + " - " + aDeposit[i].maturityDate() + "] = " +
						FormatUtil.FormatDouble (aDeposit[i].measureValue (valParams, null, mktParams, null, strDepositCalibMeasure), 1, 6, 1.) +
							" | " + FormatUtil.FormatDouble (adblDepositQuote[i], 1, 6, 1.) + " | " +
								FormatUtil.FormatDouble (fcDerived.forward (aDeposit[i].maturityDate()), 1, 4, 100.) + "%");
			}

			/*
			 * Cross-Comparison of the FRA Calibration Instrument "Forward" metric.
			 */

			if (null != aFRA && null != adblFRAQuote) {
				System.out.println ("\t----------------------------------------------------------------");

				System.out.println ("\t     FRA INSTRUMENTS QUOTE RECOVERY");

				System.out.println ("\t----------------------------------------------------------------");

				for (int i = 0; i < aFRA.length; ++i)
					System.out.println ("\t[" + aFRA[i].effectiveDate() + " - " + aFRA[i].maturityDate() + "] = " +
						FormatUtil.FormatDouble (aFRA[i].measureValue (valParams, null, mktParams, null, strFRACalibMeasure), 1, 6, 1.) +
							" | " + FormatUtil.FormatDouble (adblFRAQuote[i], 1, 6, 1.) + " | " +
								FormatUtil.FormatDouble (fcDerived.forward (aFRA[i].maturityDate()), 1, 4, 100.) + "%");
			}

			/*
			 * Cross-Comparison of the Fix-Float Calibration Instrument "DerivedParBasisSpread" metric.
			 */

			if (null != aFixFloat && null != adblFixFloatQuote) {
				System.out.println ("\t----------------------------------------------------------------");

				System.out.println ("\t     FIX-FLOAT INSTRUMENTS QUOTE RECOVERY");

				System.out.println ("\t----------------------------------------------------------------");

				for (int i = 0; i < aFixFloat.length; ++i)
					System.out.println ("\t[" + aFixFloat[i].effectiveDate() + " - " + aFixFloat[i].maturityDate() + "] = " +
						FormatUtil.FormatDouble (aFixFloat[i].measureValue (valParams, null, mktParams, null, strFixFloatCalibMeasure), 1, 4, 100.) +
							"% | " + FormatUtil.FormatDouble (adblFixFloatQuote[i], 1, 4, 100.) + "% | " +
								FormatUtil.FormatDouble (fcDerived.forward (aFixFloat[i].maturityDate()), 1, 4, 100.) + "%");
			}

			/*
			 * Cross-Comparison of the Float-Float Calibration Instrument "DerivedParBasisSpread" metric.
			 */

			if (null != aFloatFloat && null != adblFloatFloatQuote) {
				System.out.println ("\t----------------------------------------------------------------");

				System.out.println ("\t     FLOAT-FLOAT INSTRUMENTS QUOTE RECOVERY");

				System.out.println ("\t----------------------------------------------------------------");

				for (int i = 0; i < aFloatFloat.length; ++i)
					System.out.println ("\t[" + aFloatFloat[i].effectiveDate() + " - " + aFloatFloat[i].maturityDate() + "] = " +
						FormatUtil.FormatDouble (aFloatFloat[i].measureValue (valParams, null, mktParams, null, strFloatFloatCalibMeasure), 1, 2, 1.) +
							" | " + FormatUtil.FormatDouble (adblFloatFloatQuote[i], 1, 2, 10000.) + " | " +
								FormatUtil.FormatDouble (fcDerived.forward (aFloatFloat[i].maturityDate()), 1, 4, 100.) + "%");
			}

			/*
			 * Cross-Comparison of the Synthetic Float-Float Calibration Instrument "DerivedParBasisSpread" metric.
			 */

			if (null != aSyntheticFloatFloat && null != adblSyntheticFloatFloatQuote) {
				System.out.println ("\t----------------------------------------------------------------");

				System.out.println ("\t     SYNTHETIC FLOAT-FLOAT INSTRUMENTS QUOTE RECOVERY");

				System.out.println ("\t----------------------------------------------------------------");

				for (int i = 0; i < aSyntheticFloatFloat.length; ++i)
					System.out.println ("\t[" + aSyntheticFloatFloat[i].effectiveDate() + " - " + aSyntheticFloatFloat[i].maturityDate() + "] = " +
						FormatUtil.FormatDouble (aSyntheticFloatFloat[i].measureValue (valParams, null, mktParams, null, strSyntheticFloatFloatCalibMeasure), 1, 2, 1.) +
							" | " + FormatUtil.FormatDouble (adblSyntheticFloatFloatQuote[i], 1, 2, 10000.) + " | " +
								FormatUtil.FormatDouble (fcDerived.forward (aSyntheticFloatFloat[i].maturityDate()), 1, 4, 100.) + "%");
			}
		}

		return fcDerived;
	}

	public static final ForwardCurve CustomIBORBuilderSample2 (
		final MergedDiscountForwardCurve dc,
		final ForwardCurve fcReference,
		final ForwardLabel fri,
		final SegmentCustomBuilderControl scbc,
		final String[] astrDepositTenor,
		final double[] adblDepositQuote,
		final String strDepositCalibMeasure,
		final String[] astrFRATenor,
		final double[] adblFRAQuote,
		final String strFRACalibMeasure,
		final String[] astrFixFloatTenor,
		final double[] adblFixFloatQuote,
		final String strFixFloatCalibMeasure,
		final String[] astrComponentPairTenor,
		final double[] adblComponentPairQuote,
		final String strComponentPairCalibMeasure,
		final String[] astrSyntheticComponentPairTenor,
		final double[] adblSyntheticComponentPairQuote,
		final String strSyntheticComponentPairCalibMeasure,
		final String strHeaderComment,
		final boolean bPrintMetric)
		throws Exception
	{
		if (bPrintMetric) {
			System.out.println ("\n\t----------------------------------------------------------------");

			System.out.println ("\t     " + strHeaderComment);

			System.out.println ("\t----------------------------------------------------------------");
		}

		JulianDate dtValue = dc.epoch();

		ValuationParams valParams = new ValuationParams (
			dtValue,
			dtValue,
			fri.currency()
		);

		/*
		 * Set the discount curve based component market parameters.
		 */

		CurveSurfaceQuoteContainer mktParams = MarketParamsBuilder.Create (
			dc,
			fcReference,
			null,
			null,
			null,
			null,
			null,
			null
		);

		SingleStreamComponent[] aDeposit = DepositFromMaturityDays (
			dtValue,
			astrDepositTenor,
			fri
		);

		/*
		 * Construct the Deposit Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec depositStretch = LatentStateStretchBuilder.ForwardStretchSpec (
			"DEPOSIT",
			aDeposit,
			strDepositCalibMeasure,
			adblDepositQuote
		);

		FRAStandardComponent[] aFRA = FRAFromMaturityDays (
			dtValue,
			fri,
			astrFRATenor,
			adblFRAQuote
		);

		/*
		 * Construct the FRA Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec fraStretch = LatentStateStretchBuilder.ForwardStretchSpec (
			"FRA",
			aFRA,
			strFRACalibMeasure,
			adblFRAQuote
		);

		FixFloatComponent[] aFixFloat = FixFloatSwap (
			dtValue,
			fri,
			astrFixFloatTenor
		);

		/*
		 * Construct the Fix-Float Component Set Stretch Builder
		 */

		LatentStateStretchSpec fixFloatStretch = LatentStateStretchBuilder.ForwardStretchSpec (
			"FIXFLOAT",
			aFixFloat,
			strFixFloatCalibMeasure,
			adblFixFloatQuote
		);

		org.drip.product.fx.ComponentPair[] aComponentPair = FixFloatComponentPair (
			dtValue,
			mktParams,
			fri,
			astrComponentPairTenor
		);

		/*
		 * Construct the Float-Float Component Set Stretch Builder
		 */

		LatentStateStretchSpec fixFloatCPStretch = LatentStateStretchBuilder.ComponentPairForwardStretch (
			"FIXFLOATCP",
			aComponentPair,
			valParams,
			mktParams,
			adblComponentPairQuote,
			true,
			true
		);

		org.drip.product.fx.ComponentPair[] aSyntheticComponentPair = FixFloatComponentPair (
			dtValue,
			mktParams,
			fri,
			astrSyntheticComponentPairTenor
		);

		/*
		 * Construct the Synthetic Fix-Float Component Set Stretch Builder
		 */

		LatentStateStretchSpec syntheticFixFloatCPStretch = LatentStateStretchBuilder.ComponentPairForwardStretch (
			"SYNTHETICFIXFLOATCP",
			aSyntheticComponentPair,
			valParams,
			mktParams,
			adblSyntheticComponentPairQuote,
			true,
			true
		);

		LatentStateStretchSpec[] aStretchSpec = new LatentStateStretchSpec[] {
			depositStretch,
			fraStretch,
			fixFloatStretch,
			fixFloatCPStretch,
			syntheticFixFloatCPStretch
		};

		/*
		 * Set up the Linear Curve Calibrator using the following parameters:
		 * 	- Cubic Exponential Mixture Basis Spline Set
		 * 	- Ck = 2, Segment Curvature Penalty = 2
		 * 	- Quadratic Rational Shape Controller
		 * 	- Natural Boundary Setting
		 */

		LinearLatentStateCalibrator lcc = new LinearLatentStateCalibrator (
			scbc,
			BoundarySettings.NaturalStandard(),
			MultiSegmentSequence.CALIBRATE,
			null,
			null
		);

		/*
		 * Construct the Shape Preserving Forward Curve by applying the linear curve calibrator to the array
		 *  of Deposit and Swap Stretches.
		 */

		ForwardCurve fcDerived = ScenarioForwardCurveBuilder.ShapePreservingForwardCurve (
			lcc,
			aStretchSpec,
			fri,
			valParams,
			null,
			mktParams,
			null,
			null == adblDepositQuote || 0 == adblDepositQuote.length ? adblFRAQuote[0] : adblDepositQuote[0]
		);

		/*
		 * Set the discount curve + cubic polynomial forward curve based component market parameters.
		 */

		mktParams.setForwardState (fcDerived);

		if (bPrintMetric) {
			/*
			 * Cross-Comparison of the Deposit Calibration Instrument "Forward" metric.
			 */

			if (null != aDeposit && null != adblDepositQuote) {
				System.out.println ("\t----------------------------------------------------------------");

				System.out.println ("\t     DEPOSIT INSTRUMENTS QUOTE RECOVERY");

				System.out.println ("\t----------------------------------------------------------------");

				for (int i = 0; i < aDeposit.length; ++i)
					System.out.println ("\t[" + aDeposit[i].effectiveDate() + " - " + aDeposit[i].maturityDate() + "] = " +
						FormatUtil.FormatDouble (aDeposit[i].measureValue (valParams, null, mktParams, null, strDepositCalibMeasure), 1, 6, 1.) +
							" | " + FormatUtil.FormatDouble (adblDepositQuote[i], 1, 6, 1.) + " | " +
								FormatUtil.FormatDouble (fcDerived.forward (aDeposit[i].maturityDate()), 1, 4, 100.) + "%");
			}

			/*
			 * Cross-Comparison of the FRA Calibration Instrument "Forward" metric.
			 */

			if (null != aFRA && null != adblFRAQuote) {
				System.out.println ("\t----------------------------------------------------------------");

				System.out.println ("\t     FRA INSTRUMENTS QUOTE RECOVERY");

				System.out.println ("\t----------------------------------------------------------------");

				for (int i = 0; i < aFRA.length; ++i)
					System.out.println ("\t[" + aFRA[i].effectiveDate() + " - " + aFRA[i].maturityDate() + "] = " +
						FormatUtil.FormatDouble (aFRA[i].measureValue (valParams, null, mktParams, null, strFRACalibMeasure), 1, 6, 1.) +
							" | " + FormatUtil.FormatDouble (adblFRAQuote[i], 1, 6, 1.) + " | " +
								FormatUtil.FormatDouble (fcDerived.forward (aFRA[i].maturityDate()), 1, 4, 100.) + "%");
			}

			/*
			 * Cross-Comparison of the Fix-Float Calibration Instrument "DerivedParBasisSpread" metric.
			 */

			if (null != aFixFloat && null != adblFixFloatQuote) {
				System.out.println ("\t----------------------------------------------------------------");

				System.out.println ("\t     FIX-FLOAT INSTRUMENTS QUOTE RECOVERY");

				System.out.println ("\t----------------------------------------------------------------");

				for (int i = 0; i < aFixFloat.length; ++i)
					System.out.println ("\t[" + aFixFloat[i].effectiveDate() + " - " + aFixFloat[i].maturityDate() + "] = " +
						FormatUtil.FormatDouble (aFixFloat[i].measureValue (valParams, null, mktParams, null, strFixFloatCalibMeasure), 1, 2, 100.) +
							"% | " + FormatUtil.FormatDouble (adblFixFloatQuote[i], 1, 2, 100.) + "% | " +
								FormatUtil.FormatDouble (fcDerived.forward (aFixFloat[i].maturityDate()), 1, 4, 100.) + "%");
			}

			/*
			 * Cross-Comparison of the Fix-Float Component Pair "DerivedParBasisSpread" metric.
			 */

			if (null != aComponentPair && null != adblComponentPairQuote) {
				System.out.println ("\t----------------------------------------------------------------");

				System.out.println ("\t     FIX-FLOAT COMPONENT PAIR QUOTE RECOVERY");

				System.out.println ("\t----------------------------------------------------------------");

				for (int i = 0; i < aComponentPair.length; ++i)
					System.out.println ("\t[" + aComponentPair[i].effective() + " - " + aComponentPair[i].maturity() + "] = " +
						FormatUtil.FormatDouble (aComponentPair[i].measureValue (valParams, null, mktParams, null, strComponentPairCalibMeasure), 1, 2, 1.) +
							" | " + FormatUtil.FormatDouble (adblComponentPairQuote[i], 1, 2, 10000.) + " | " +
								FormatUtil.FormatDouble (fcDerived.forward (aComponentPair[i].maturity()), 1, 4, 100.) + "%");
			}

			/*
			 * Cross-Comparison of the Synthetic Float-Float Component Pair "DerivedParBasisSpread" metric.
			 */

			if (null != aSyntheticComponentPair && null != adblSyntheticComponentPairQuote) {
				System.out.println ("\t----------------------------------------------------------------");

				System.out.println ("\t     SYNTHETIC FIX-FLOAT COMPONENT PAIR QUOTE RECOVERY");

				System.out.println ("\t----------------------------------------------------------------");

				for (int i = 0; i < aSyntheticComponentPair.length; ++i)
					System.out.println ("\t[" + aSyntheticComponentPair[i].effective() + " - " + aSyntheticComponentPair[i].maturity() + "] = " +
						FormatUtil.FormatDouble (aSyntheticComponentPair[i].measureValue (valParams, null, mktParams, null, strSyntheticComponentPairCalibMeasure), 1, 2, 1.) +
							" | " + FormatUtil.FormatDouble (adblSyntheticComponentPairQuote[i], 1, 2, 10000.) + " | " +
								FormatUtil.FormatDouble (fcDerived.forward (aSyntheticComponentPair[i].maturity()), 1, 4, 100.) + "%");
			}
		}

		return fcDerived;
	}

	private static final void ForwardJack (
		final JulianDate dt,
		final ForwardCurve fc,
		final String strStartDateTenor,
		final String strManifestMeasure)
	{
		JulianDate dtJack = dt.addTenor (strStartDateTenor);

		System.out.println ("\t" + 
			dtJack + " | " +
			strStartDateTenor + ": " +
			fc.jackDForwardDManifestMeasure (
				strManifestMeasure,
				dtJack).displayString()
			);
	}

	public static final void ForwardJack (
		final JulianDate dt,
		final String strHeaderComment,
		final ForwardCurve fc,
		final String strManifestMeasure)
	{
		System.out.println ("\n\t----------------------------------------------------------------");

		System.out.println ("\t" + strHeaderComment);

		System.out.println ("\t----------------------------------------------------------------");

		ForwardJack (dt, fc, "1Y", strManifestMeasure);

		ForwardJack (dt, fc, "2Y", strManifestMeasure);

		ForwardJack (dt, fc, "3Y", strManifestMeasure);

		ForwardJack (dt, fc, "5Y", strManifestMeasure);

		ForwardJack (dt, fc, "7Y", strManifestMeasure);
	}
}
