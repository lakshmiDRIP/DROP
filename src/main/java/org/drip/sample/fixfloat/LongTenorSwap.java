
package org.drip.sample.fixfloat;

import java.util.List;

import org.drip.analytics.date.*;
import org.drip.analytics.support.*;
import org.drip.function.r1tor1.QuadraticRationalShapeControl;
import org.drip.numerical.common.FormatUtil;
import org.drip.param.creator.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.*;
import org.drip.param.valuation.*;
import org.drip.product.creator.*;
import org.drip.product.rates.*;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.*;
import org.drip.spline.stretch.*;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;
import org.drip.state.discount.*;
import org.drip.state.estimator.LatentStateStretchBuilder;
import org.drip.state.identifier.*;
import org.drip.state.inference.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>LongTenorSwap</i> demonstrates the Construction and Valuation of In-Advance and In-Arrears Long Tenor
 * Swap.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fixfloat/README.md">Fix Float Swap Analytics</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LongTenorSwap {

	/*
	 * Construct the Array of Deposit Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final SingleStreamComponent[] DepositInstrumentsFromMaturityDays (
		final JulianDate dtEffective,
		final String strCurrency,
		final int[] aiDay,
		final int iRefPeriodType)
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
			iRefPeriodType,
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
		final String[] astrMaturityTenor,
		final int iRefPeriodType,
		final String strFloatingTenor,
		final String strCompositeTenor)
		throws Exception
	{
		FixFloatComponent[] aIRS = new FixFloatComponent[astrMaturityTenor.length];

		UnitCouponAccrualSetting ucasFixed = new UnitCouponAccrualSetting (
			Helper.TenorToFreq (strCompositeTenor),
			"Act/360",
			false,
			"Act/360",
			false,
			strCurrency,
			true,
			CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
		);

		ComposableFloatingUnitSetting cfusFloating = new ComposableFloatingUnitSetting (
			strCompositeTenor,
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			ForwardLabel.Create (
				strCurrency,
				strFloatingTenor
			),
			iRefPeriodType,
			0.
		);

		ComposableFixedUnitSetting cfusFixed = new ComposableFixedUnitSetting (
			strCompositeTenor,
			CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
			null,
			0.,
			0.,
			strCurrency
		);

		CompositePeriodSetting cpsFloating = new CompositePeriodSetting (
			Helper.TenorToFreq (strCompositeTenor),
			strCompositeTenor,
			strCurrency,
			null,
			-1.,
			null,
			null,
			null,
			null
		);

		CompositePeriodSetting cpsFixed = new CompositePeriodSetting (
			Helper.TenorToFreq (strCompositeTenor),
			strCompositeTenor,
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
				strCompositeTenor,
				astrMaturityTenor[i],
				null
			);

			List<Integer> lsFloatingStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
				dtEffective,
				strCompositeTenor,
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
	 * This sample demonstrates discount curve calibration and input instrument calibration quote recovery.
	 * 	It shows the following:
	 * 	- Construct the Array of Cash/Swap Instruments and their Quotes from the given set of parameters.
	 * 	- Construct the Cash/Swap Instrument Set Stretch Builder.
	 * 	- Set up the Linear Curve Calibrator using the following parameters:
	 * 		- Cubic Exponential Mixture Basis Spline Set
	 * 		- Ck = 2, Segment Curvature Penalty = 2
	 * 		- Quadratic Rational Shape Controller
	 * 		- Natural Boundary Setting
	 * 	- Construct the Shape Preserving Discount Curve by applying the linear curve calibrator to the array
	 * 		of Cash and Swap Stretches.
	 * 	- Cross-Comparison of the Cash/Swap Calibration Instrument "Rate" metric across the different curve
	 * 		construction methodologies.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void CustomDiscountCurveBuilderSample (
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
				1, 2, 7, 14, 30, 60
			},
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE
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

		FixFloatComponent[] aSwapInAdvance = SwapInstrumentsFromMaturityTenor (
			dtSpot,
			strCurrency,
			new java.lang.String[] {
				"4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y", "40Y", "50Y"
			},
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			"6M",
			"6M"
		);

		FixFloatComponent[] aSwapInAdvanceLongTenor = SwapInstrumentsFromMaturityTenor (
			dtSpot,
			strCurrency,
			new java.lang.String[] {
				"4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y", "40Y", "50Y"
			},
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
			"3M",
			"12M"
		);

		FixFloatComponent[] aSwapInArrearsLongTenor = SwapInstrumentsFromMaturityTenor (
			dtSpot,
			strCurrency,
			new java.lang.String[] {
				"4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y", "40Y", "50Y"
			},
			CompositePeriodBuilder.REFERENCE_PERIOD_IN_ARREARS,
			"3M",
			"12M"
		);

		double[] adblSwapQuote = new double[] {
			0.0166, 0.0206, 0.0241, 0.0269, 0.0292, 0.0311, 0.0326, 0.0340, 0.0351, 0.0375, 0.0393, 0.0402, 0.0407, 0.0409, 0.0409
		};

		/*
		 * Construct the Swap Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec swapStretch = LatentStateStretchBuilder.ForwardFundingStretchSpec (
			"SWAP",
			aSwapInAdvance,
			"SwapRate",
			adblSwapQuote
		);

		LatentStateStretchSpec[] aStretchSpec = new LatentStateStretchSpec[] {
			depositStretch,
			edfStretch,
			swapStretch
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

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		/*
		 * Construct the Shape Preserving Discount Curve by applying the linear curve calibrator to the array
		 *  of Deposit, Futures, and Swap Stretches.
		 */

		MergedDiscountForwardCurve dc = ScenarioDiscountCurveBuilder.ShapePreservingDFBuild (
			strCurrency,
			lcc,
			aStretchSpec,
			valParams,
			null,
			null,
			null,
			1.
		);

		CurveSurfaceQuoteContainer csqs = MarketParamsBuilder.Create (
			dc,
			null,
			null,
			null,
			null,
			null,
			null
		);

		/*
		 * Cross-Comparison of the In-Advance/Arrears Swap "Rate" metric across the different curve
		 * 	construction methodologies.
		 */

		System.out.println ("\n\t-------------------------------------------------------------------------------");

		System.out.println ("\t            IN-ADVANCE/IN-ARREARS LONG TENOR SWAP METRIC COMPARISON");

		System.out.println ("\t-------------------------------------------------------------------------------");

		System.out.println ("\t\tL -> R:");

		System.out.println ("\t\t\t - Swap Maturity");

		System.out.println ("\t\t\t - In Advance Calibration Quote");

		System.out.println ("\t\t\t - In Advance Fair Premium");

		System.out.println ("\t\t\t - In Advance Swap Rate");

		System.out.println ("\t\t\t - In Advance Long Tenor Swap Rate");

		System.out.println ("\t\t\t - In Arrears Long Tenor Swap Rate");

		System.out.println ("\t\t\t - In Advance Long Tenor Swap Rate Shift");

		System.out.println ("\t\t\t - In Arrears Long Tenor Swap Rate Shift");

		System.out.println ("\t-------------------------------------------------------------------------------");

		for (int i = 0; i < aSwapInAdvance.length; ++i) {
			double dblInAdvanceLongTenorFairPremium = aSwapInAdvanceLongTenor[i].measureValue (
				valParams,
				null,
				csqs,
				null,
				"FairPremium"
			);

			double dblInArrearsLongTenorFairPremium = aSwapInArrearsLongTenor[i].measureValue (
				valParams,
				null,
				csqs,
				null,
				"FairPremium"
			);

			System.out.println ("\t[" + aSwapInAdvance[i].maturityDate() + "] = " +
				FormatUtil.FormatDouble (aSwapInAdvance[i].measureValue (valParams, null, csqs, null, "CalibSwapRate"), 1, 4, 100.) + "% | " +
				FormatUtil.FormatDouble (adblSwapQuote[i], 1, 4, 100.) + "% | " +
				FormatUtil.FormatDouble (aSwapInAdvance[i].measureValue (valParams, null, csqs, null, "FairPremium"), 1, 4, 100.) + "% | " +
				FormatUtil.FormatDouble (dblInAdvanceLongTenorFairPremium, 1, 4, 100.) + "% | " +
				FormatUtil.FormatDouble (dblInArrearsLongTenorFairPremium, 1, 4, 100.) + "% | " +
				FormatUtil.FormatDouble (dblInAdvanceLongTenorFairPremium - adblSwapQuote[i], 1, 0, 10000.) + " | " +
				FormatUtil.FormatDouble (dblInArrearsLongTenorFairPremium - adblSwapQuote[i], 1, 0, 10000.)
			);
		}

		System.out.println ("\n\t-------------------------------------------------------------------------------");

		System.out.println ("\t            IN-ADVANCE/IN-ARREARS LONG TENOR SWAP DV01 COMPARISON");

		System.out.println ("\t-------------------------------------------------------------------------------");

		System.out.println ("\t\tL -> R:");

		System.out.println ("\t\t\t - Swap Maturity");

		System.out.println ("\t\t\t - In Advance Swap DV01");

		System.out.println ("\t\t\t - In Advance Long Tenor Swap DV01");

		System.out.println ("\t\t\t - In Arrears Long Tenor Swap DV01");

		System.out.println ("\t\t\t - In Advance Long Tenor Swap DV01 Shift");

		System.out.println ("\t\t\t - In Arrears Long Tenor Swap DV01 Shift");

		System.out.println ("\t-------------------------------------------------------------------------------");

		for (int i = 0; i < aSwapInAdvance.length; ++i) {
			double dblInAdvanceDV01 = aSwapInAdvance[i].measureValue (
				valParams,
				null,
				csqs,
				null,
				"FixedDV01"
			);

			double dblInAdvanceLongTenorDV01 = aSwapInAdvanceLongTenor[i].measureValue (
				valParams,
				null,
				csqs,
				null,
				"FixedDV01"
			);

			double dblInArrearsLongTenorDV01 = aSwapInAdvanceLongTenor[i].measureValue (
				valParams,
				null,
				csqs,
				null,
				"FixedDV01"
			);

			System.out.println ("\t[" + aSwapInAdvance[i].maturityDate() + "] = " +
				FormatUtil.FormatDouble (dblInAdvanceDV01, 2, 1, 10000.) + " | " +
				FormatUtil.FormatDouble (dblInAdvanceLongTenorDV01, 2, 1, 10000.) + " | " +
				FormatUtil.FormatDouble (dblInArrearsLongTenorDV01, 2, 1, 10000.) + " | " +
				FormatUtil.FormatDouble (dblInAdvanceLongTenorDV01 - dblInAdvanceDV01, 1, 2, 10000.) + " | " +
				FormatUtil.FormatDouble (dblInArrearsLongTenorDV01 - dblInAdvanceDV01, 1, 2, 10000.)
			);
		}

		System.out.println ("\t-------------------------------------------------------------------------------");
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		JulianDate dtToday = DateUtil.Today().addTenor ("0D");

		String strCurrency = "USD";

		CustomDiscountCurveBuilderSample (
			dtToday,
			strCurrency
		);

		EnvManager.TerminateEnv();
	}
}
