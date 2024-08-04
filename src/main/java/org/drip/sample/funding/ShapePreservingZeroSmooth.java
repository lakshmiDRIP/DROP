
package org.drip.sample.funding;

import org.drip.analytics.date.*;
import org.drip.analytics.definition.LatentStateStatic;
import org.drip.function.r1tor1custom.QuadraticRationalShapeControl;
import org.drip.market.otc.*;
import org.drip.param.creator.*;
import org.drip.param.valuation.*;
import org.drip.product.creator.*;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.rates.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.*;
import org.drip.spline.params.*;
import org.drip.spline.pchip.LocalMonotoneCkGenerator;
import org.drip.spline.stretch.*;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;
import org.drip.state.discount.*;
import org.drip.state.estimator.*;
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
 * <i>ShapePreservingZeroSmooth</i> demonstrates the usage of different shape preserving and smoothing
 * techniques involved in the funding curve creation. It shows the following:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Construct the Array of Cash/Swap Instruments and their Quotes from the given set of parameters.
 *  	</li>
 *  	<li>
 * 			Construct the Cash/Swap Instrument Set Stretch Builder.
 *  	</li>
 *  	<li>
 * 			Set up the Linear Curve Calibrator using the following parameters:
 *  		<ul>
 *  			<li>
 * 					Cubic Exponential Mixture Basis Spline Set
 *  			</li>
 *  			<li>
 * 					C<sub>k</sub> = 2
 * 					Segment Curvature Penalty = 2
 *  			</li>
 *  			<li>
 * 					Quadratic Rational Shape Controller
 *  			</li>
 *  			<li>
 * 					Natural Boundary Setting
 *  			</li>
 *  		</ul>
 *  	</li>
 *  	<li>
 * 			Set up the Global Curve Control parameters as follows:
 *  		<ul>
 *  			<li>
 * 					Zero Rate Quantification Metric
 *  			</li>
 *  			<li>
 * 					Cubic Polynomial Basis Spline Set
 *  			</li>
 *  			<li>
 * 					C<sub>k</sub> = 2
 * 					Segment Curvature Penalty = 2
 *  			</li>
 *  			<li>
 * 					Quadratic Rational Shape Controller
 *  			</li>
 *  			<li>
 * 					Natural Boundary Setting
 *  			</li>
 *  		</ul>
 *  	</li>
 *  	<li>
 * 			Set up the Local Curve Control parameters as follows:
 *  		<ul>
 *  			<li>
 * 					C1 Bessel Monotone Smoothener with no spurious extrema elimination and no monotone filter
 *  			</li>
 *  			<li>
 * 					Zero Rate Quantification Metric
 *  			</li>
 *  			<li>
 * 					Cubic Polynomial Basis Spline Set
 *  			</li>
 *  			<li>
 * 					C<sub>k</sub> = 2
 * 					Segment Curvature Penalty = 2
 *  			</li>
 *  			<li>
 * 					Quadratic Rational Shape Controller
 *  			</li>
 *  			<li>
 * 					Natural Boundary Setting
 *  			</li>
 *  		</ul>
 *  	</li>
 *  	<li>
 * 			Construct the Shape Preserving Discount Curve by applying the linear curve calibrator to the
 * 				array of Cash and Swap Stretches.
 *  	</li>
 *  	<li>
 * 			Construct the Globally Smoothened Discount Curve by applying the linear curve calibrator and the
 * 				Global Curve Control parameters to the array of Cash and Swap Stretches and the shape
 * 				preserving discount curve.
 *  	</li>
 *  	<li>
 * 			Construct the Locally Smoothened Discount Curve by applying the linear curve calibrator and the
 * 				Local Curve Control parameters to the array of Cash and Swap Stretches and the shape
 * 				preserving discount curve.
 *  	</li>
 *  	<li>
 * 			Cross-Comparison of the Cash/Swap Calibration Instrument "Rate" metric across the different curve
 * 				construction methodologies.
 *  	</li>
 *  	<li>
 *  		Cross-Comparison of the Swap Calibration Instrument "Rate" metric across the different curve
 *  			construction methodologies for a sequence of bespoke swap instruments.
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

public class ShapePreservingZeroSmooth {

	private static final FixFloatComponent OTCIRS (
		final JulianDate dtSpot,
		final String strCurrency,
		final String strMaturityTenor,
		final double dblCoupon)
	{
		FixedFloatSwapConvention ffConv = IBORFixedFloatContainer.ConventionFromJurisdiction (
			strCurrency,
			"ALL",
			strMaturityTenor,
			"MAIN"
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
				ForwardLabel.Create (
					"USD",
					"3M"
				)
			);

		return aDeposit;
	}

	/*
	 * Construct the Array of Swap Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FixFloatComponent[] SwapInstrumentsFromMaturityTenor (
		final JulianDate dtSpot,
		final String strCurrency,
		final String[] astrMaturityTenor)
		throws Exception
	{
		FixFloatComponent[] aIRS = new FixFloatComponent[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i) {
			FixFloatComponent irs = OTCIRS (
				dtSpot,
				strCurrency,
				astrMaturityTenor[i],
				0.
			);

			irs.setPrimaryCode ("IRS." + astrMaturityTenor[i] + "." + strCurrency);

			aIRS[i] = irs;
		}

		return aIRS;
	}

	/*
	 * This sample demonstrates the usage of different shape preserving and smoothing techniques involved in
	 * 	the discount curve creation. It shows the following:
	 * 	- Construct the Array of Cash/Swap Instruments and their Quotes from the given set of parameters.
	 * 	- Construct the Cash/Swap Instrument Set Stretch Builder.
	 * 	- Set up the Linear Curve Calibrator using the following parameters:
	 * 		- Cubic Exponential Mixture Basis Spline Set
	 * 		- Ck = 2, Segment Curvature Penalty = 2
	 * 		- Quadratic Rational Shape Controller
	 * 		- Natural Boundary Setting
	 * 	- Set up the Global Curve Control parameters as follows:
	 * 		- Zero Rate Quantification Metric
	 * 		- Cubic Polynomial Basis Spline Set
	 * 		- Ck = 2, Segment Curvature Penalty = 2
	 * 		- Quadratic Rational Shape Controller
	 * 		- Natural Boundary Setting
	 * 	- Set up the Local Curve Control parameters as follows:
	 * 		- C1 Bessel Monotone Smoothener with no spurious extrema elimination and no monotone filter
	 * 		- Zero Rate Quantification Metric
	 * 		- Cubic Polynomial Basis Spline Set
	 * 		- Ck = 2, Segment Curvature Penalty = 2
	 * 		- Quadratic Rational Shape Controller
	 * 		- Natural Boundary Setting
	 * 	- Construct the Shape Preserving Discount Curve by applying the linear curve calibrator to the array
	 * 		of Cash and Swap Stretches.
	 * 	- Construct the Globally Smoothened Discount Curve by applying the linear curve calibrator and the
	 * 		Global Curve Control parameters to the array of Cash and Swap Stretches and the shape preserving
	 * 		discount curve.
	 * 	- Construct the Locally Smoothened Discount Curve by applying the linear curve calibrator and the
	 * 		Local Curve Control parameters to the array of Cash and Swap Stretches and the shape preserving
	 *  	discount curve.
	 * 	- Cross-Comparison of the Cash/Swap Calibration Instrument "Rate" metric across the different curve
	 * 		construction methodologies.
	 *  - Cross-Comparison of the Swap Calibration Instrument "Rate" metric across the different curve
	 *  	construction methodologies for a sequence of bespoke swap instruments.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void ShapePreservingDFZeroSmoothSample (
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
				2,
				7,
				14,
				30,
				60
			}
		);

		double[] adblDepositQuote = new double[] {
			0.0013,
			0.0017,
			0.0018,
			0.0020,
			0.0023
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
			0.0027,
			0.0032,
			0.0041,
			0.0054,
			0.0077,
			0.0104,
			0.0134,
			0.0160
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
			new SegmentCustomBuilderControl (
				MultiSegmentSequenceBuilder.BASIS_SPLINE_EXPONENTIAL_MIXTURE,
				new ExponentialMixtureSetParams (
					new double[] {
						0.01,
						0.05,
						0.25
					}
				),
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
		 * Set up the Global Curve Control parameters as follows:
		 * 	- Zero Rate Quantification Metric
		 * 	- Cubic Polynomial Basis Spline Set
		 * 	- Ck = 2, Segment Curvature Penalty = 2
		 * 	- Quadratic Rational Shape Controller
		 * 	- Natural Boundary Setting
		 */

		GlobalControlCurveParams gccp = new GlobalControlCurveParams (
			LatentStateStatic.DISCOUNT_QM_ZERO_RATE,
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
		 * Set up the Local Curve Control parameters as follows:
		 * 	- C1 Bessel Monotone Smoothener with no spurious extrema elimination and no monotone filter
		 * 	- Zero Rate Quantification Metric
		 * 	- Cubic Polynomial Basis Spline Set
		 * 	- Ck = 2, Segment Curvature Penalty = 2
		 * 	- Quadratic Rational Shape Controller
		 * 	- Natural Boundary Setting
		 */

		LocalControlCurveParams lccp = new LocalControlCurveParams (
			LocalMonotoneCkGenerator.C1_BESSEL,
			LatentStateStatic.DISCOUNT_QM_ZERO_RATE,
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
			MultiSegmentSequence.CALIBRATE,
			null,
			null,
			false,
			false
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

		MergedDiscountForwardCurve dcShapePreserving = ScenarioDiscountCurveBuilder.ShapePreservingDFBuild (
			strCurrency,
			lcc,
			aStretchSpec,
			valParams,
			null,
			null,
			null,
			1.
		);

		/*
		 * Construct the Globally Smoothened Discount Curve by applying the linear curve calibrator and the
		 * 	Global Curve Control parameters to the array of Cash and Swap Stretches and the shape preserving
		 * 	discount curve.
		 */

		MergedDiscountForwardCurve dcGloballySmooth = ScenarioDiscountCurveBuilder.SmoothingGlobalControlBuild (
			dcShapePreserving,
			lcc,
			gccp,
			valParams,
			null,
			null,
			null
		);

		/*
		 * Construct the Locally Smoothened Discount Curve by applying the linear curve calibrator and the
		 * 	Local Curve Control parameters to the array of Cash and Swap Stretches and the shape preserving
		 *  discount curve.
		 */

		MergedDiscountForwardCurve dcLocallySmooth = ScenarioDiscountCurveBuilder.SmoothingLocalControlBuild (
			dcShapePreserving,
			lcc,
			lccp,
			valParams,
			null,
			null,
			null
		);

		/*
		 * Cross-Comparison of the Deposit Calibration Instrument "Rate" metric across the different curve
		 * 	construction methodologies.
		 */

		System.out.println ("\n\t----------------------------------------------------------------");

		System.out.println ("\t----------------------------------------------------------------");

		System.out.println ("\t               DEPOSIT INSTRUMENTS CALIBRATION RECOVERY");

		System.out.println ("\t----------------------------------------------------------------");

		System.out.println ("\t        SHAPE PRESERVING   | SMOOTHING #1  | SMOOTHING #2  |  INPUT QUOTE  ");

		System.out.println ("\t----------------------------------------------------------------");

		System.out.println ("\t----------------------------------------------------------------");

		for (int i = 0; i < aDepositComp.length; ++i)
			System.out.println ("\t[" + aDepositComp[i].maturityDate() + "] = " +
				FormatUtil.FormatDouble (
					aDepositComp[i].measureValue (
						valParams,
						null,
						MarketParamsBuilder.Create (dcShapePreserving, null, null, null, null, null, null),
						null,
						"Rate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aDepositComp[i].measureValue (
						valParams,
						null,
						MarketParamsBuilder.Create (dcGloballySmooth, null, null, null, null, null, null),
						null,
						"Rate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aDepositComp[i].measureValue (
						valParams,
						null,
						MarketParamsBuilder.Create (dcLocallySmooth, null, null, null, null, null, null),
						null,
						"Rate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (adblDepositQuote[i], 1, 6, 1.)
			);

		/*
		 * Cross-Comparison of the Swap Calibration Instrument "Rate" metric across the different curve
		 * 	construction methodologies.
		 */

		System.out.println ("\n\t----------------------------------------------------------------");

		System.out.println ("\t----------------------------------------------------------------");

		System.out.println ("\t               SWAP INSTRUMENTS CALIBRATION RECOVERY");

		System.out.println ("\t----------------------------------------------------------------");

		System.out.println ("\t        SHAPE PRESERVING   | SMOOTHING #1  | SMOOTHING #2  |  INPUT QUOTE  ");

		System.out.println ("\t----------------------------------------------------------------");

		System.out.println ("\t----------------------------------------------------------------");

		for (int i = 0; i < aSwapComp.length; ++i)
			System.out.println ("\t[" + aSwapComp[i].maturityDate() + "] = " +
				FormatUtil.FormatDouble (
					aSwapComp[i].measureValue (
						valParams,
						null,
						MarketParamsBuilder.Create (dcShapePreserving, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "   |   " +
				/* FormatUtil.FormatDouble (
					aSwapComp[i].measureValue (
						new ValuationParams (dtToday, dtToday, "MXN"), null,
						MarketParamsBuilder.Create (dcGloballySmooth, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "   |   " + */
				FormatUtil.FormatDouble (
					aSwapComp[i].measureValue (
						valParams,
						null,
						MarketParamsBuilder.Create (dcLocallySmooth, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (adblSwapQuote[i], 1, 6, 1.)
			);

		/*
		 * Cross-Comparison of the Swap Calibration Instrument "Rate" metric across the different curve
		 * 	construction methodologies for a sequence of bespoke swap instruments.
		 */

		CalibratableComponent[] aCC = SwapInstrumentsFromMaturityTenor (
			dtSpot,
			strCurrency,
			new java.lang.String[] {
				"3Y", "6Y", "9Y", "12Y", "15Y", "18Y", "21Y", "24Y", "27Y", "30Y"
			}
		);

		System.out.println ("\n\t----------------------------------------------------------------");

		System.out.println ("\t----------------------------------------------------------------");

		System.out.println ("\t           BESPOKE SWAPS PAR RATE");

		System.out.println ("\t----------------------------------------------------------------");

		System.out.println ("\t        SHAPE PRESERVING   |  SMOOTHING #1 |  SMOOTHING #2");

		System.out.println ("\t----------------------------------------------------------------");

		System.out.println ("\t----------------------------------------------------------------");

		for (int i = 0; i < aCC.length; ++i)
			System.out.println ("\t[" + aCC[i].maturityDate() + "] = " +
				FormatUtil.FormatDouble (
					aCC[i].measureValue (
						valParams,
						null,
						MarketParamsBuilder.Create (dcShapePreserving, null, null, null, null, null, null),
						null,
						"CalibSwapRate"
					),
				1, 6, 1.) + "   |   " +
				/* FormatUtil.FormatDouble (
					aCC[i].measureValue (new ValuationParams (dtToday, dtToday, "MXN"), null,
					MarketParamsBuilder.Create (dcGloballySmooth, null, null, null, null, null, null),
					null,
					"CalibSwapRate"),
				1, 6, 1.) + "   |   " + */
				FormatUtil.FormatDouble (
					aCC[i].measureValue (
						valParams,
						null,
						MarketParamsBuilder.Create (dcLocallySmooth, null, null, null, null, null, null),
						null,
						"CalibSwapRate"
					),
				1, 6, 1.)
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

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2017,
			DateUtil.DECEMBER,
			21
		);

		String strCurrency = "USD";

		ShapePreservingDFZeroSmoothSample (
			dtSpot,
			strCurrency
		);

		EnvManager.TerminateEnv();
	}
}
