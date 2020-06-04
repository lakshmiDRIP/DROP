
package org.drip.sample.funding;

import java.util.List;

import org.drip.analytics.date.*;
import org.drip.analytics.definition.Turn;
import org.drip.analytics.support.*;
import org.drip.function.r1tor1.QuadraticRationalShapeControl;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.period.*;
import org.drip.param.valuation.*;
import org.drip.product.creator.*;
import org.drip.product.rates.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.*;
import org.drip.spline.stretch.*;
import org.drip.state.curve.DiscountFactorDiscountCurve;
import org.drip.state.discount.*;
import org.drip.state.estimator.LatentStateStretchBuilder;
import org.drip.state.identifier.*;
import org.drip.state.inference.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>CustomFundingCurveReconciler</i> demonstrates the multi-stretch transition custom Funding curve
 * construction, turns application, discount factor extraction, and calibration quote recovery. It shows the
 * following steps:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Setup the linear curve calibrator.
 *  	</li>
 *  	<li>
 * 			Setup the cash instruments and their quotes for calibration.
 *  	</li>
 *  	<li>
 * 			Setup the cash instruments stretch latent state representation - this uses the discount factor
 * 				quantification metric and the "rate" manifest measure.
 *  	</li>
 *  	<li>
 * 			Setup the swap instruments and their quotes for calibration.
 *  	</li>
 *  	<li>
 * 			Setup the swap instruments stretch latent state representation - this uses the discount factor
 * 				quantification metric and the "rate" manifest measure.
 *  	</li>
 *  	<li>
 * 			Calibrate over the instrument set to generate a new overlapping latent state span instance.
 *  	</li>
 *  	<li>
 * 			Retrieve the "cash" stretch from the span.
 *  	</li>
 *  	<li>
 * 			Retrieve the "swap" stretch from the span.
 *  	</li>
 *  	<li>
 * 			Create a discount curve instance by converting the overlapping stretch to an exclusive
 * 				non-overlapping stretch.
 *  	</li>
 *  	<li>
 * 			Compare the discount factors and their monotonicity emitted from the discount curve, the
 * 				non-overlapping span, and the "swap" stretch across the range of tenor predictor ordinates.
 *  	</li>
 *  	<li>
 * 			Cross-Recovery of the Cash Calibration Instrument "Rate" metric across the different curve
 * 				construction methodologies.
 *  	</li>
 *  	<li>
 * 			Cross-Recovery of the Swap Calibration Instrument "Rate" metric across the different curve
 * 				construction methodologies.
 *  	</li>
 *  	<li>
 * 			Create a turn list instance and add new turn instances.
 *  	</li>
 *  	<li>
 * 			Update the discount curve with the turn list.
 *  	</li>
 *  	<li>
 * 			Compare the discount factor implied the discount curve with and without applying the turns
 * 				adjustment.
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/funding/README.md">Shape Preserving Local Funding Curve</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CustomFundingCurveReconciler {

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

		ComposableFloatingUnitSetting cfus = new ComposableFloatingUnitSetting (
			"3M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
			null,
			ForwardLabel.Create (
				strCurrency,
				"3M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		CompositePeriodSetting cps = new CompositePeriodSetting (
			4,
			"3M",
			strCurrency,
			null,
			1.,
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

		for (int i = 0; i < aiDay.length; ++i) {
			aDeposit[i] = new SingleStreamComponent (
				"DEPOSIT_" + aiDay[i],
				new Stream (
					CompositePeriodBuilder.FloatingCompositeUnit (
						CompositePeriodBuilder.EdgePair (
							dtEffective,
							dtEffective.addBusDays (
								aiDay[i],
								strCurrency
							)
						),
						cps,
						cfus
					)
				),
				csp
			);

			aDeposit[i].setPrimaryCode (aiDay[i] + "D");
		}

		return aDeposit;
	}

	/*
	 * Construct the Array of Swap Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FixFloatComponent[] SwapInstrumentsFromMaturityTenor (
		final JulianDate dtEffective,
		final String strCurrency,
		final String[] astrMaturityTenor)
		throws Exception
	{
		FixFloatComponent[] aIRS = new FixFloatComponent[astrMaturityTenor.length];

		UnitCouponAccrualSetting ucasFixed = new UnitCouponAccrualSetting (
			2,
			"Act/360",
			false,
			"Act/360",
			false,
			strCurrency,
			true,
			CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
		);

		ComposableFloatingUnitSetting cfusFloating = new ComposableFloatingUnitSetting (
			"6M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			ForwardLabel.Create (
				strCurrency,
				"6M"
			),
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			0.
		);

		ComposableFixedUnitSetting cfusFixed = new ComposableFixedUnitSetting (
			"6M",
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			0.,
			0.,
			strCurrency
		);

		CompositePeriodSetting cpsFloating = new CompositePeriodSetting (
			2,
			"6M",
			strCurrency,
			null,
			-1.,
			null,
			null,
			null,
			null
		);

		CompositePeriodSetting cpsFixed = new CompositePeriodSetting (
			2,
			"6M",
			strCurrency,
			null,
			1.,
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
			List<Integer> lsFixedStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
				dtEffective,
				"6M",
				astrMaturityTenor[i],
				null
			);

			List<Integer> lsFloatingStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
				dtEffective,
				"6M",
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

			FixFloatComponent irs = new FixFloatComponent (
				fixedStream,
				floatingStream,
				csp
			);

			irs.setPrimaryCode ("IRS." + astrMaturityTenor[i] + "." + strCurrency);

			aIRS[i] = irs;
		}

		return aIRS;
	}

	/*
	 * This sample demonstrates the multi-stretch transition custom discount curve construction, turns
	 * 	application, discount factor extraction, and calibration quote recovery. It shows the following
	 * 	steps:
	 * 	- Setup the linear curve calibrator.
	 * 	- Setup the cash instruments and their quotes for calibration.
	 * 	- Setup the cash instruments stretch latent state representation - this uses the discount factor
	 * 		quantification metric and the "rate" manifest measure.
	 * 	- Setup the swap instruments and their quotes for calibration.
	 * 	- Setup the swap instruments stretch latent state representation - this uses the discount factor
	 * 		quantification metric and the "rate" manifest measure.
	 * 	- Calibrate over the instrument set to generate a new overlapping latent state span instance.
	 * 	- Retrieve the "cash" stretch from the span.
	 * 	- Retrieve the "swap" stretch from the span.
	 * 	- Create a discount curve instance by converting the overlapping stretch to an exclusive
	 * 		non-overlapping stretch.
	 * 	- Compare the discount factors and their monotonicity emitted from the discount curve, the
	 * 		non-overlapping span, and the "swap" stretch across the range of tenor predictor ordinates.
	 * 	- Cross-Recovery of the Cash Calibration Instrument "Rate" metric across the different curve
	 * 		construction methodologies.
	 * 	- Cross-Recovery of the Swap Calibration Instrument "Rate" metric across the different curve
	 * 		construction methodologies.
	 * 	- Create a turn list instance and add new turn instances.
	 * 	- Update the discount curve with the turn list.
	 * 	- Compare the discount factor implied the discount curve with and without applying the turns
	 * 		adjustment.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void SplineLinearDiscountCurve (
		final JulianDate dtSpot,
		final String strCurrency,
		final SegmentCustomBuilderControl scbc)
		throws Exception
	{
		/*
		 * Construct the Array of Deposit Instruments and their Quotes from the given set of parameters
		 */

		SingleStreamComponent[] aDepositComp = DepositInstrumentsFromMaturityDays (
			dtSpot,
			strCurrency,
			new int[] {
				1, 2, 7, 14, 30, 60
			}
		);

		double[] adblDepositQuote = new double[] {
			0.0013, 0.0017, 0.0017, 0.0018, 0.0020, 0.0023
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
			8,
			strCurrency
		);

		double[] adblEDFQuote = new double[] {
			0.0027, 0.0032, 0.0041, 0.0054, 0.0077, 0.0104, 0.0134, 0.0160
		};

		/*
		 * Construct the EDF Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec edfStretch = LatentStateStretchBuilder.ForwardFundingStretchSpec (
			"EDF",
			aEDFComp,
			"ForwardRate",
			adblEDFQuote
		);

		/*
		 * Construct the Array of Swap Instruments and their Quotes from the given set of parameters
		 */

		FixFloatComponent[] aSwapComp = SwapInstrumentsFromMaturityTenor (
			dtSpot,
			strCurrency,
			new java.lang.String[] {
				"4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y", "40Y", "50Y"
			}
		);

		double[] adblSwapQuote = new double[] {
			0.0166, 0.0206, 0.0241, 0.0269, 0.0292, 0.0311, 0.0326, 0.0340, 0.0351, 0.0375, 0.0393, 0.0402, 0.0407, 0.0409, 0.0409
		};

		/*
		 * Construct the Swap Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec swapStretch = LatentStateStretchBuilder.ForwardFundingStretchSpec (
			"SWAP",
			aSwapComp,
			"SwapRate",
			adblSwapQuote
		);

		LatentStateStretchSpec[] aStretchSpec = new LatentStateStretchSpec[] {depositStretch, edfStretch, swapStretch};

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
			dtSpot,
			dtSpot,
			strCurrency
		);

		/*
		 * Calibrate over the instrument set to generate a new overlapping latent state span instance
		 */

		org.drip.spline.grid.OverlappingStretchSpan ors = lcc.calibrateSpan (
			aStretchSpec,
			1.,
			valParams,
			null,
			null,
			null
		);

		/*
		 * Retrieve the "Deposit" stretch from the span
		 */

		MultiSegmentSequence mssDeposit = ors.getStretch ("DEPOSIT");

		/*
		 * Retrieve the "swap" stretch from the span
		 */

		MultiSegmentSequence mssSwap = ors.getStretch ("SWAP");

		/*
		 * Create a discount curve instance by converting the overlapping stretch to an exclusive
		 * 	non-overlapping stretch.
		 */

		MergedDiscountForwardCurve dfdc = new DiscountFactorDiscountCurve (
			strCurrency,
			ors
		);

		/*
		 * Compare the discount factors and their monotonicity emitted from the discount curve, the
		 * non-overlapping span, and the Deposit stretch across the range of tenor predictor ordinates.
		 */

		System.out.println ("\n\t----------------------------------------------------------------");

		System.out.println ("\t     DEPOSIT DF            DFDC     STRETCH           LOCAL");

		System.out.println ("\t----------------------------------------------------------------");

		for (int iX = (int) mssDeposit.getLeftPredictorOrdinateEdge(); iX <= (int) mssDeposit.getRightPredictorOrdinateEdge();
			iX += 0.1 * (mssDeposit.getRightPredictorOrdinateEdge() - mssDeposit.getLeftPredictorOrdinateEdge())) {
			try {
				System.out.println ("\tDeposit [" + new JulianDate (iX) + "] = " +
					FormatUtil.FormatDouble (dfdc.df (iX), 1, 8, 1.) + " || " +
						ors.getContainingStretch (iX).name() + " || " +
							FormatUtil.FormatDouble (mssDeposit.responseValue (iX), 1, 8, 1.) + " | " +
								mssDeposit.monotoneType (iX));
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		/*
		 * Compare the discount factors and their monotonicity emitted from the discount curve, the
		 * non-overlapping span, and the "swap" stretch across the range of tenor predictor ordinates.
		 */

		System.out.println ("\n\t----------------------------------------------------------------");

		System.out.println ("\t     SWAP DF            DFDC     STRETCH            LOCAL");

		System.out.println ("\t----------------------------------------------------------------");

		for (int iX = (int) mssSwap.getLeftPredictorOrdinateEdge(); iX <= (int) mssSwap.getRightPredictorOrdinateEdge();
				iX += 0.05 * (mssSwap.getRightPredictorOrdinateEdge() - mssSwap.getLeftPredictorOrdinateEdge())) {
				System.out.println ("\tSwap [" + new JulianDate (iX) + "] = " +
					FormatUtil.FormatDouble (dfdc.df (iX), 1, 8, 1.) + " || " +
						ors.getContainingStretch (iX).name() + " || " +
							FormatUtil.FormatDouble (mssSwap.responseValue (iX), 1, 8, 1.) + " | " +
								mssSwap.monotoneType (iX));
		}

		System.out.println ("\tSwap [" + dtSpot.addTenor ("60Y") + "] = " +
			FormatUtil.FormatDouble (dfdc.df (dtSpot.addTenor ("60Y")), 1, 8, 1.));

		/*
		 * Cross-Recovery of the Deposit Calibration Instrument "Rate" metric across the different curve
		 * 	construction methodologies.
		 */

		System.out.println ("\n\t----------------------------------------------------------------");

		System.out.println ("\t     DEPOSIT INSTRUMENTS CALIBRATION RECOVERY");

		System.out.println ("\t----------------------------------------------------------------");

		for (int i = 0; i < aDepositComp.length; ++i)
			System.out.println ("\t[" + aDepositComp[i].maturityDate() + "] = " +
				FormatUtil.FormatDouble (aDepositComp[i].measureValue (valParams, null,
					MarketParamsBuilder.Create (dfdc, null, null, null, null, null, null),
						null, "Rate"), 1, 6, 1.) + " | " + FormatUtil.FormatDouble (adblDepositQuote[i], 1, 6, 1.));

		/*
		 * Cross-Recovery of the Swap Calibration Instrument "Rate" metric across the different curve
		 * 	construction methodologies.
		 */

		System.out.println ("\n\t----------------------------------------------------------------");

		System.out.println ("\t     SWAP INSTRUMENTS CALIBRATION RECOVERY");

		System.out.println ("\t----------------------------------------------------------------");

		for (int i = 0; i < aSwapComp.length; ++i)
			System.out.println ("\t[" + aSwapComp[i].maturityDate() + "] = " +
				FormatUtil.FormatDouble (aSwapComp[i].measureValue (valParams, null,
					MarketParamsBuilder.Create (dfdc, null, null, null, null, null, null),
						null, "CalibSwapRate"), 1, 6, 1.) + " | " + FormatUtil.FormatDouble (adblSwapQuote[i], 1, 6, 1.));

		/*
		 * Create a turn list instance and add new turn instances
		 */

		TurnListDiscountFactor tldc = new TurnListDiscountFactor();

		tldc.addTurn (
			new Turn (
				dtSpot.addTenor ("5Y").julian(),
				dtSpot.addTenor ("40Y").julian(),
				0.001
			)
		);

		/*
		 * Update the discount curve with the turn list.
		 */

		dfdc.setTurns (tldc);

		/*
		 * Compare the discount factor implied the discount curve with and without applying the turns
		 * 	adjustment.
		 */

		System.out.println ("\n\t-------------------------------");

		System.out.println ("\t     SWAP DF            DFDC");

		System.out.println ("\t-------------------------------");

		for (int iX = (int) mssSwap.getLeftPredictorOrdinateEdge(); iX <= (int) mssSwap.getRightPredictorOrdinateEdge();
				iX += 0.05 * (mssSwap.getRightPredictorOrdinateEdge() - mssSwap.getLeftPredictorOrdinateEdge())) {
				System.out.println ("\tSwap [" + new JulianDate (iX) + "] = " +
					FormatUtil.FormatDouble (dfdc.df (iX), 1, 8, 1.));
		}

		System.out.println ("\t-------------------------------");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		/*
		 * Construct the segment Custom builder using the following parameters:
		 * 	- Cubic Exponential Mixture Basis Spline Set
		 * 	- Ck = 2, Segment Curvature Penalty = 2
		 * 	- Quadratic Rational Shape Controller
		 */

		SegmentCustomBuilderControl prbpPolynomial = new SegmentCustomBuilderControl (
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
		);

		/*
		 * Run the full spline linear discount curve builder sample.
		 */

		SplineLinearDiscountCurve (
			DateUtil.Today(),
			"USD",
			prbpPolynomial
		);

		EnvManager.TerminateEnv();
	}
}
