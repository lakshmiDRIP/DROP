
package org.drip.sample.overnight;

import java.util.List;

import org.drip.analytics.date.*;
import org.drip.analytics.definition.LatentStateStatic;
import org.drip.analytics.support.*;
import org.drip.function.r1tor1.QuadraticRationalShapeControl;
import org.drip.market.otc.*;
import org.drip.param.creator.*;
import org.drip.param.period.*;
import org.drip.param.valuation.*;
import org.drip.product.creator.*;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.rates.*;
import org.drip.quant.common.FormatUtil;
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
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * ShapeOvernightZeroLocalSmooth demonstrates the usage of different local smoothing techniques involved in
 *  the Overnight curve creation. It shows the following:
 * 	- Construct the Array of Deposit/OIS Instruments and their Quotes from the given set of parameters.
 * 	- Construct the Deposit/OIS Instrument Set Stretch Builder.
 * 	- Set up the Linear Curve Calibrator using the following parameters:
 * 		- Cubic Exponential Mixture Basis Spline Set
 * 		- Ck = 2, Segment Curvature Penalty = 2
 * 		- Quadratic Rational Shape Controller
 * 		- Natural Boundary Setting
 * 	- Set up the Akima Local Curve Control parameters as follows:
 * 		- C1 Akima Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 * 		- Zero Rate Quantification Metric
 * 		- Cubic Polynomial Basis Spline Set
 * 		- Ck = 2, Segment Curvature Penalty = 2
 * 		- Quadratic Rational Shape Controller
 * 		- Natural Boundary Setting
 * 	- Set up the Harmonic Local Curve Control parameters as follows:
 * 		- C1 Harmonic Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 * 		- Zero Rate Quantification Metric
 * 		- Cubic Polynomial Basis Spline Set
 * 		- Ck = 2, Segment Curvature Penalty = 2
 * 		- Quadratic Rational Shape Controller
 * 		- Natural Boundary Setting
 * 	- Set up the Hyman 1983 Local Curve Control parameters as follows:
 * 		- C1 Hyman 1983 Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 * 		- Zero Rate Quantification Metric
 * 		- Cubic Polynomial Basis Spline Set
 * 		- Ck = 2, Segment Curvature Penalty = 2
 * 		- Quadratic Rational Shape Controller
 * 		- Natural Boundary Setting
 * 	- Set up the Hyman 1989 Local Curve Control parameters as follows:
 * 		- C1 Akima Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 * 		- Zero Rate Quantification Metric
 * 		- Cubic Polynomial Basis Spline Set
 * 		- Ck = 2, Segment Curvature Penalty = 2
 * 		- Quadratic Rational Shape Controller
 * 		- Natural Boundary Setting
 * 	- Set up the Huynh-Le Floch Delimited Local Curve Control parameters as follows:
 * 		- C1 Huynh-Le Floch Delimited Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 * 		- Zero Rate Quantification Metric
 * 		- Cubic Polynomial Basis Spline Set
 * 		- Ck = 2, Segment Curvature Penalty = 2
 * 		- Quadratic Rational Shape Controller
 * 		- Natural Boundary Setting
 * 	- Set up the Kruger Local Curve Control parameters as follows:
 * 		- C1 Kruger Monotone Smoothener with spurious extrema elimination and monotone filtering applied
 * 		- Zero Rate Quantification Metric
 * 		- Cubic Polynomial Basis Spline Set
 * 		- Ck = 2, Segment Curvature Penalty = 2
 * 		- Quadratic Rational Shape Controller
 * 		- Natural Boundary Setting
 * 	- Construct the Shape Preserving OIS Discount Curve by applying the linear curve calibrator to the array
 * 		of Deposit and OIS Stretches.
 * 	- Construct the Akima Locally Smoothened OIS Discount Curve by applying the linear curve calibrator and
 * 		the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape
 * 		preserving discount curve.
 * 	- Construct the Harmonic Locally Smoothened OIS Discount Curve by applying the linear curve calibrator
 * 		and the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape
 * 		preserving discount curve.
 * 	- Construct the Hyman 1983 Locally Smoothened OIS Discount Curve by applying the linear curve calibrator
 * 		and the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape
 * 		preserving discount curve.
 * 	- Construct the Hyman 1989 Locally Smoothened OIS Discount Curve by applying the linear curve calibrator
 * 		and the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape
 * 		preserving discount curve.
 * 	- Construct the Huynh-Le Floch Delimiter Locally Smoothened OIS Discount Curve by applying the linear
 * 		curve calibrator and the Local Curve Control parameters to the array of Deposit and OIS Stretches
 * 		and the shape preserving discount curve.
 * 	- Construct the Kruger Locally Smoothened OIS Discount Curve by applying the linear curve calibrator and
 * 		the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape
 * 		preserving discount curve.
 * 	- Cross-Comparison of the Deposit/OIS Calibration Instrument "Rate" metric across the different curve
 * 		construction methodologies.
 *  - Cross-Comparison of the OIS Calibration Instrument "Rate" metric across the different curve
 *  	construction methodologies for a sequence of bespoke swap instruments.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ShapeOvernightZeroLocalSmooth {

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
	 * Construct the Array of Overnight Index Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FixFloatComponent[] OvernightIndexFromMaturityTenor (
		final JulianDate dtEffective,
		final String[] astrMaturityTenor,
		final double[] adblCoupon,
		final String strCurrency)
		throws Exception
	{
		FixFloatComponent[] aOIS = new FixFloatComponent[astrMaturityTenor.length];

		UnitCouponAccrualSetting ucasFixed = new UnitCouponAccrualSetting (
			2,
			"Act/360",
			false,
			"Act/360",
			false,
			strCurrency,
			false,
			CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
		);

		CashSettleParams csp = new CashSettleParams (
			0,
			strCurrency,
			0
		);

		for (int i = 0; i < astrMaturityTenor.length; ++i) {
			java.lang.String strFixedTenor = Helper.LEFT_TENOR_LESSER == Helper.TenorCompare (
				astrMaturityTenor[i],
				"6M"
			) ? astrMaturityTenor[i] : "6M";

			java.lang.String strFloatingTenor = Helper.LEFT_TENOR_LESSER == Helper.TenorCompare (
				astrMaturityTenor[i],
				"3M"
			) ? astrMaturityTenor[i] : "3M";

			ComposableFloatingUnitSetting cfusFloating = new ComposableFloatingUnitSetting (
				"ON",
				CompositePeriodBuilder.EDGE_DATE_SEQUENCE_OVERNIGHT,
				null,
				OvernightLabel.Create (
					strCurrency
				),
				CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
				0.
			);

			ComposableFixedUnitSetting cfusFixed = new ComposableFixedUnitSetting (
				strFixedTenor,
				CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR,
				null,
				adblCoupon[i],
				0.,
				strCurrency
			);

			CompositePeriodSetting cpsFloating = new CompositePeriodSetting (
				4,
				strFloatingTenor,
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
				strFixedTenor,
				strCurrency,
				null,
				1.,
				null,
				null,
				null,
				null
			);

			List<Integer> lsFixedStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
				dtEffective,
				strFixedTenor,
				astrMaturityTenor[i],
				null
			);

			List<Integer> lsFloatingStreamEdgeDate = CompositePeriodBuilder.RegularEdgeDates (
				dtEffective,
				strFloatingTenor,
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

			FixFloatComponent ois = new FixFloatComponent (
				fixedStream,
				floatingStream,
				csp
			);

			ois.setPrimaryCode ("OIS." + astrMaturityTenor[i] + "." + strCurrency);

			aOIS[i] = ois;
		}

		return aOIS;
	}

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

	/*
	 * Construct the Array of Overnight Index Future Instruments from the given set of parameters
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final FixFloatComponent[] OISFuturesFromMaturityTenor (
		final JulianDate dtSpot,
		final String strCurrency,
		final String[] astrStartTenor,
		final String[] astrMaturityTenor,
		final double[] adblCoupon)
		throws Exception
	{
		FixFloatComponent[] aOISFutures = new FixFloatComponent[astrMaturityTenor.length];

		for (int i = 0; i < astrMaturityTenor.length; ++i)
			aOISFutures[i] = OTCOISFixFloat (
				dtSpot.addTenor (astrStartTenor[i]),
				strCurrency,
				astrMaturityTenor[i],
				adblCoupon[i]
			);

		return aOISFutures;
	}

	/*
	 * This sample demonstrates the usage of different local smoothing techniques involved in the OIS discount
	 * 	curve creation. It shows the following:
	 * 	- Construct the Array of Deposit/OIS Instruments and their Quotes from the given set of parameters.
	 * 	- Construct the Deposit/OIS Instrument Set Stretch Builder.
	 * 	- Set up the Linear Curve Calibrator using the following parameters:
	 * 		- Cubic Exponential Mixture Basis Spline Set
	 * 		- Ck = 2, Segment Curvature Penalty = 2
	 * 		- Quadratic Rational Shape Controller
	 * 		- Natural Boundary Setting
	 * 	- Set up the Akima Local Curve Control parameters as follows:
	 * 		- C1 Akima Monotone Smoothener with spurious extrema elimination and monotone filtering applied
	 * 		- Zero Rate Quantification Metric
	 * 		- Cubic Polynomial Basis Spline Set
	 * 		- Ck = 2, Segment Curvature Penalty = 2
	 * 		- Quadratic Rational Shape Controller
	 * 		- Natural Boundary Setting
	 * 	- Set up the Harmonic Local Curve Control parameters as follows:
	 * 		- C1 Harmonic Monotone Smoothener with spurious extrema elimination and monotone filtering applied
	 * 		- Zero Rate Quantification Metric
	 * 		- Cubic Polynomial Basis Spline Set
	 * 		- Ck = 2, Segment Curvature Penalty = 2
	 * 		- Quadratic Rational Shape Controller
	 * 		- Natural Boundary Setting
	 * 	- Set up the Hyman 1983 Local Curve Control parameters as follows:
	 * 		- C1 Hyman 1983 Monotone Smoothener with spurious extrema elimination and monotone filtering applied
	 * 		- Zero Rate Quantification Metric
	 * 		- Cubic Polynomial Basis Spline Set
	 * 		- Ck = 2, Segment Curvature Penalty = 2
	 * 		- Quadratic Rational Shape Controller
	 * 		- Natural Boundary Setting
	 * 	- Set up the Hyman 1989 Local Curve Control parameters as follows:
	 * 		- C1 Akima Monotone Smoothener with spurious extrema elimination and monotone filtering applied
	 * 		- Zero Rate Quantification Metric
	 * 		- Cubic Polynomial Basis Spline Set
	 * 		- Ck = 2, Segment Curvature Penalty = 2
	 * 		- Quadratic Rational Shape Controller
	 * 		- Natural Boundary Setting
	 * 	- Set up the Huynh-Le Floch Delimited Local Curve Control parameters as follows:
	 * 		- C1 Huynh-Le Floch Delimited Monotone Smoothener with spurious extrema elimination and monotone filtering applied
	 * 		- Zero Rate Quantification Metric
	 * 		- Cubic Polynomial Basis Spline Set
	 * 		- Ck = 2, Segment Curvature Penalty = 2
	 * 		- Quadratic Rational Shape Controller
	 * 		- Natural Boundary Setting
	 * 	- Set up the Kruger Local Curve Control parameters as follows:
	 * 		- C1 Kruger Monotone Smoothener with spurious extrema elimination and monotone filtering applied
	 * 		- Zero Rate Quantification Metric
	 * 		- Cubic Polynomial Basis Spline Set
	 * 		- Ck = 2, Segment Curvature Penalty = 2
	 * 		- Quadratic Rational Shape Controller
	 * 		- Natural Boundary Setting
	 * 	- Construct the Shape Preserving OIS Discount Curve by applying the linear curve calibrator to the array
	 * 		of Deposit and OIS Stretches.
	 * 	- Construct the Akima Locally Smoothened OIS Discount Curve by applying the linear curve calibrator and
	 * 		the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape
	 * 		preserving discount curve.
	 * 	- Construct the Harmonic Locally Smoothened OIS Discount Curve by applying the linear curve calibrator
	 * 		and the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape
	 * 		preserving discount curve.
	 * 	- Construct the Hyman 1983 Locally Smoothened OIS Discount Curve by applying the linear curve calibrator
	 * 		and the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape
	 * 		preserving discount curve.
	 * 	- Construct the Hyman 1989 Locally Smoothened OIS Discount Curve by applying the linear curve calibrator
	 * 		and the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape
	 * 		preserving discount curve.
	 * 	- Construct the Huynh-Le Floch Delimiter Locally Smoothened OIS Discount Curve by applying the linear
	 * 		curve calibrator and the Local Curve Control parameters to the array of Deposit and OIS Stretches
	 * 		and the shape preserving discount curve.
	 * 	- Construct the Kruger Locally Smoothened OIS Discount Curve by applying the linear curve calibrator and
	 * 		the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape
	 * 		preserving discount curve.
	 * 	- Cross-Comparison of the Deposit/OIS Calibration Instrument "Rate" metric across the different curve
	 * 		construction methodologies.
	 *  - Cross-Comparison of the OIS Calibration Instrument "Rate" metric across the different curve
	 *  	construction methodologies for a sequence of bespoke OIS instruments.
	 * 
	 *  	USE WITH CARE: This sample ignores errors and does not handle exceptions.
	 */

	private static final void ShapeOISDFZeroLocalSmoothSample (
		final JulianDate dtSpot,
		final String strHeaderComment,
		final String strCurrency)
		throws Exception
	{
		System.out.println ("\n\t----------------------------------------------------------------");

		System.out.println ("\t" + strHeaderComment);

		/*
		 * Construct the Array of Deposit Instruments and their Quotes from the given set of parameters
		 */

		SingleStreamComponent[] aDepositComp = DepositInstrumentsFromMaturityDays (
			dtSpot,
			strCurrency,
			new int[] {
				1, 2, 3
			}
		);

		double[] adblDepositQuote = new double[] {
			0.0004, 0.0004, 0.0004		 // Deposit
		};

		/*
		 * Construct the Deposit Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec depositStretch = LatentStateStretchBuilder.ForwardFundingStretchSpec (
			"   DEPOSIT   ",
			aDepositComp,
			"ForwardRate",
			adblDepositQuote
		);

		/*
		 * Construct the Array of Short End OIS Instruments and their Quotes from the given set of parameters
		 */

		double[] adblShortEndOISQuote = new double[] {
			0.00070,    //   1W
			0.00069,    //   2W
			0.00078,    //   3W
			0.00074     //   1M
		};

		CalibratableComponent[] aShortEndOISComp = OISFromMaturityTenor (
			dtSpot,
			strCurrency,
			new java.lang.String[] {
				"1W", "2W", "3W", "1M"
			},
			adblShortEndOISQuote
		);

		/*
		 * Construct the Short End OIS Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec oisShortEndStretch = LatentStateStretchBuilder.ForwardFundingStretchSpec (
			"SHORT END OIS",
			aShortEndOISComp,
			"SwapRate",
			adblShortEndOISQuote
		);

		/*
		 * Construct the Array of OIS Futures Instruments and their Quotes from the given set of parameters
		 */

		double[] adblOISFutureQuote = new double[] {
			 0.00046,    //   1M x 1M
			 0.00016,    //   2M x 1M
			-0.00007,    //   3M x 1M
			-0.00013,    //   4M x 1M
			-0.00014     //   5M x 1M
		};

		CalibratableComponent[] aOISFutureComp = OISFuturesFromMaturityTenor (
			dtSpot,
			strCurrency,
			new java.lang.String[] {
				"1M", "2M", "3M", "4M", "5M"
			},
			new java.lang.String[] {
				"1M", "1M", "1M", "1M", "1M"
			},
			adblOISFutureQuote
		);

		/*
		 * Construct the OIS Future Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec oisFutureStretch = LatentStateStretchBuilder.ForwardFundingStretchSpec (
			" OIS FUTURE  ",
			aOISFutureComp,
			"SwapRate",
			adblOISFutureQuote
		);

		/*
		 * Construct the Array of Long End OIS Instruments and their Quotes from the given set of parameters
		 */

		double[] adblLongEndOISQuote = new double[] {
			0.00002,    //  15M
			0.00008,    //  18M
			0.00021,    //  21M
			0.00036,    //   2Y
			0.00127,    //   3Y
			0.00274,    //   4Y
			0.00456,    //   5Y
			0.00647,    //   6Y
			0.00827,    //   7Y
			0.00996,    //   8Y
			0.01147,    //   9Y
			0.01280,    //  10Y
			0.01404,    //  11Y
			0.01516,    //  12Y
			0.01764,    //  15Y
			0.01939,    //  20Y
			0.02003,    //  25Y
			0.02038     //  30Y
		};

		CalibratableComponent[] aLongEndOISComp = OISFromMaturityTenor (
			dtSpot,
			strCurrency,
			new java.lang.String[] {
				"15M", "18M", "21M", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y"
			},
			adblLongEndOISQuote
		);

		/*
		 * Construct the Long End OIS Instrument Set Stretch Builder
		 */

		LatentStateStretchSpec oisLongEndStretch = LatentStateStretchBuilder.ForwardFundingStretchSpec (
			"LONG END OIS ",
			aLongEndOISComp,
			"SwapRate",
			adblLongEndOISQuote
		);

		LatentStateStretchSpec[] aStretchSpec = new LatentStateStretchSpec[] {
			depositStretch,
			oisShortEndStretch,
			oisFutureStretch,
			oisLongEndStretch
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
		 * Set up the Akima Local Curve Control parameters as follows:
		 * 	- C1 Akima Monotone Smoothener with spurious extrema elimination and monotone filtering applied
		 * 	- Zero Rate Quantification Metric
		 * 	- Cubic Polynomial Basis Spline Set
		 * 	- Ck = 2, Segment Curvature Penalty = 2
		 * 	- Quadratic Rational Shape Controller
		 * 	- Natural Boundary Setting
		 */

		LocalControlCurveParams lccpAkima = new LocalControlCurveParams (
			LocalMonotoneCkGenerator.C1_AKIMA,
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
			true,
			true
		);

		/*
		 * Set up the Harmonic Local Curve Control parameters as follows:
		 * 	- C1 Harmonic Monotone Smoothener with spurious extrema elimination and monotone filtering
		 * 		applied
		 * 	- Zero Rate Quantification Metric
		 * 	- Cubic Polynomial Basis Spline Set
		 * 	- Ck = 2, Segment Curvature Penalty = 2
		 * 	- Quadratic Rational Shape Controller
		 * 	- Natural Boundary Setting
		 */

		LocalControlCurveParams lccpHarmonic = new LocalControlCurveParams (
			LocalMonotoneCkGenerator.C1_HARMONIC,
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
			true,
			true
		);

		/*
		 * Set up the Hyman 1983 Local Curve Control parameters as follows:
		 * 	- C1 Hyman 1983 Monotone Smoothener with spurious extrema elimination and monotone filtering
		 * 		applied
		 * 	- Zero Rate Quantification Metric
		 * 	- Cubic Polynomial Basis Spline Set
		 * 	- Ck = 2, Segment Curvature Penalty = 2
		 * 	- Quadratic Rational Shape Controller
		 * 	- Natural Boundary Setting
		 */

		LocalControlCurveParams lccpHyman83 = new LocalControlCurveParams (
			LocalMonotoneCkGenerator.C1_HYMAN83,
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
			true,
			true
		);

		/*
		 * Set up the Hyman 1989 Local Curve Control parameters as follows:
		 * 	- C1 Hyman 1989 Monotone Smoothener with spurious extrema elimination and monotone filtering
		 * 		applied
		 * 	- Zero Rate Quantification Metric
		 * 	- Cubic Polynomial Basis Spline Set
		 * 	- Ck = 2, Segment Curvature Penalty = 2
		 * 	- Quadratic Rational Shape Controller
		 * 	- Natural Boundary Setting
		 */

		LocalControlCurveParams lccpHyman89 = new LocalControlCurveParams (
			LocalMonotoneCkGenerator.C1_HYMAN89,
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
			true,
			true
		);

		/*
		 * Set up the Huynh-LeFloch Limiter Local Curve Control parameters as follows:
		 * 	- C1 Huynh-LeFloch Limiter Monotone Smoothener with spurious extrema elimination and monotone
		 * 		filtering applied
		 * 	- Zero Rate Quantification Metric
		 * 	- Cubic Polynomial Basis Spline Set
		 * 	- Ck = 2, Segment Curvature Penalty = 2
		 * 	- Quadratic Rational Shape Controller
		 * 	- Natural Boundary Setting
		 */

		LocalControlCurveParams lccpHuynhLeFloch = new LocalControlCurveParams (
			LocalMonotoneCkGenerator.C1_HUYNH_LE_FLOCH,
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
			true,
			true
		);

		/*
		 * Set up the Kruger Local Curve Control parameters as follows:
		 * 	- C1 Kruger Monotone Smoothener with spurious extrema elimination and monotone filtering applied
		 * 	- Zero Rate Quantification Metric
		 * 	- Cubic Polynomial Basis Spline Set
		 * 	- Ck = 2, Segment Curvature Penalty = 2
		 * 	- Quadratic Rational Shape Controller
		 * 	- Natural Boundary Setting
		 */

		LocalControlCurveParams lccpKruger = new LocalControlCurveParams (
			LocalMonotoneCkGenerator.C1_KRUGER,
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
			true,
			true
		);

		ValuationParams valParams = new ValuationParams (
			dtSpot,
			dtSpot,
			strCurrency
		);

		/*
		 * Construct the Shape Preserving Discount Curve by applying the linear curve calibrator to the array
		 *  of Deposit and OIS Stretches.
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
		 * Construct the Akima Locally Smoothened Discount Curve by applying the linear curve calibrator and
		 * 	the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape
		 * 	preserving discount curve.
		 */

		MergedDiscountForwardCurve dcLocalAkima = ScenarioDiscountCurveBuilder.SmoothingLocalControlBuild (
			dcShapePreserving,
			lcc,
			lccpAkima,
			valParams,
			null,
			null,
			null
		);

		/*
		 * Construct the Harmonic Locally Smoothened Discount Curve by applying the linear curve calibrator
		 * 	and the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape
		 * 	preserving discount curve.
		 */

		MergedDiscountForwardCurve dcLocalHarmonic = ScenarioDiscountCurveBuilder.SmoothingLocalControlBuild (
			dcShapePreserving,
			lcc,
			lccpHarmonic,
			valParams,
			null,
			null,
			null
		);

		/*
		 * Construct the Hyman 1983 Locally Smoothened Discount Curve by applying the linear curve calibrator
		 * 	and the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape
		 * 	preserving discount curve.
		 */

		MergedDiscountForwardCurve dcLocalHyman83 = ScenarioDiscountCurveBuilder.SmoothingLocalControlBuild (
			dcShapePreserving,
			lcc,
			lccpHyman83,
			valParams,
			null,
			null,
			null
		);

		/*
		 * Construct the Hyman 1989 Locally Smoothened OIS Discount Curve by applying the linear curve calibrator
		 * 	and the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape
		 * 	preserving discount curve.
		 */

		MergedDiscountForwardCurve dcLocalHyman89 = ScenarioDiscountCurveBuilder.SmoothingLocalControlBuild (
			dcShapePreserving,
			lcc,
			lccpHyman89,
			valParams,
			null,
			null,
			null
		);

		/*
		 * Construct the Huynh-Le Floch delimited Locally Smoothened OIS Discount Curve by applying the linear
		 * 	curve calibrator and the Local Curve Control parameters to the array of Deposit and OIS Stretches
		 * 	and the shape preserving discount curve.
		 */

		MergedDiscountForwardCurve dcLocalHuynhLeFloch = ScenarioDiscountCurveBuilder.SmoothingLocalControlBuild (
			dcShapePreserving,
			lcc,
			lccpHuynhLeFloch,
			valParams,
			null,
			null,
			null
		);

		/*
		 * Construct the Kruger Locally Smoothened OIS Discount Curve by applying the linear curve calibrator and
		 *  the Local Curve Control parameters to the array of Deposit and OIS Stretches and the shape
		 * 	preserving discount curve.
		 */

		MergedDiscountForwardCurve dcLocalKruger = ScenarioDiscountCurveBuilder.SmoothingLocalControlBuild (
			dcShapePreserving,
			lcc,
			lccpKruger,
			valParams,
			null,
			null,
			null
		);

		/*
		 * Cross-Comparison of the Deposit Calibration Instrument "Rate" metric across the different curve
		 * 	construction methodologies.
		 */

		System.out.println ("\n\t-------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t                                                DEPOSIT INSTRUMENTS CALIBRATION RECOVERY");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t        SHAPE PRESERVING   |  LOCAL AKIMA  | LOCAL HARMONIC | LOCAL HYMAN83 | LOCAL HYMAN89 | LOCAL HUYNHLF | LOCAL KRUGER  |  INPUT QUOTE  ");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------------------");

		for (int i = 0; i < aDepositComp.length; ++i)
			System.out.println ("\t[" + aDepositComp[i].maturityDate() + "] = " +
				FormatUtil.FormatDouble (
					aDepositComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcShapePreserving, null, null, null, null, null, null),
						null,
						"Rate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aDepositComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalAkima, null, null, null, null, null, null),
						null,
						"Rate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aDepositComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalHarmonic, null, null, null, null, null, null),
						null,
						"Rate"),
					1, 6, 1.) + "    |   " +
				FormatUtil.FormatDouble (
					aDepositComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalHyman83, null, null, null, null, null, null),
						null,
						"Rate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aDepositComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalHyman89, null, null, null, null, null, null),
						null,
						"Rate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aDepositComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalHuynhLeFloch, null, null, null, null, null, null),
						null,
						"Rate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aDepositComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalKruger, null, null, null, null, null, null),
						null,
						"Rate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (adblDepositQuote[i], 1, 6, 1.)
			);

		/*
		 * Cross-Comparison of the Short End OIS Calibration Instrument "Rate" metric across the different curve
		 * 	construction methodologies.
		 */

		System.out.println ("\n\t--------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t                                                 SHORT END OIS INSTRUMENTS CALIBRATION RECOVERY");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t        SHAPE PRESERVING   |  LOCAL AKIMA  | LOCAL HARMONIC | LOCAL HYMAN83 | LOCAL HYMAN89 | LOCAL HUYNHLF | LOCAL KRUGER  |  INPUT QUOTE  ");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------------------");

		for (int i = 0; i < aShortEndOISComp.length; ++i)
			System.out.println ("\t[" + aShortEndOISComp[i].maturityDate() + "] = " +
				FormatUtil.FormatDouble (
					aShortEndOISComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcShapePreserving, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aShortEndOISComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalAkima, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aShortEndOISComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalHarmonic, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "    |   " +
				FormatUtil.FormatDouble (
					aShortEndOISComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalHyman83, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aShortEndOISComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalHyman89, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aShortEndOISComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalHuynhLeFloch, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aShortEndOISComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalKruger, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (adblShortEndOISQuote[i], 1, 6, 1.)
			);

		/*
		 * Cross-Comparison of the OIS Future Calibration Instrument "Rate" metric across the different curve
		 * 	construction methodologies.
		 */

		System.out.println ("\n\t--------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t                                                 OIS FUTURE INSTRUMENTS CALIBRATION RECOVERY");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t        SHAPE PRESERVING   |  LOCAL AKIMA  | LOCAL HARMONIC | LOCAL HYMAN83 | LOCAL HYMAN89 | LOCAL HUYNHLF | LOCAL KRUGER  |  INPUT QUOTE  ");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------------------");

		for (int i = 0; i < aOISFutureComp.length; ++i)
			System.out.println ("\t[" + aOISFutureComp[i].maturityDate() + "] = " +
				FormatUtil.FormatDouble (
					aOISFutureComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcShapePreserving, null, null, null, null, null, null),
						null,
						"SwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aOISFutureComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalAkima, null, null, null, null, null, null),
						null,
						"SwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aOISFutureComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalHarmonic, null, null, null, null, null, null),
						null,
						"SwapRate"),
					1, 6, 1.) + "    |   " +
				FormatUtil.FormatDouble (
					aOISFutureComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalHyman83, null, null, null, null, null, null),
						null,
						"SwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aOISFutureComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalHyman89, null, null, null, null, null, null),
						null,
						"SwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aOISFutureComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalHuynhLeFloch, null, null, null, null, null, null),
						null,
						"SwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aOISFutureComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalKruger, null, null, null, null, null, null),
						null,
						"SwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (adblOISFutureQuote[i], 1, 6, 1.)
			);

		System.out.println ("\n\t--------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t                                                 LONG END OIS INSTRUMENTS CALIBRATION RECOVERY");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t        SHAPE PRESERVING   |  LOCAL AKIMA  | LOCAL HARMONIC | LOCAL HYMAN83 | LOCAL HYMAN89 | LOCAL HUYNHLF | LOCAL KRUGER  |  INPUT QUOTE  ");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------------------");

		for (int i = 0; i < aLongEndOISComp.length; ++i)
			System.out.println ("\t[" + aLongEndOISComp[i].maturityDate() + "] = " +
				FormatUtil.FormatDouble (
					aLongEndOISComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcShapePreserving, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aLongEndOISComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalAkima, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aLongEndOISComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalHarmonic, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "    |   " +
				FormatUtil.FormatDouble (
					aLongEndOISComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalHyman83, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aLongEndOISComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalHyman89, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aLongEndOISComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalHuynhLeFloch, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aLongEndOISComp[i].measureValue (
						valParams, null,
						MarketParamsBuilder.Create (dcLocalKruger, null, null, null, null, null, null),
						null,
						"CalibSwapRate"),
					1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (adblLongEndOISQuote[i], 1, 6, 1.)
			);

		/*
		 * Cross-Comparison of the OIS Calibration Instrument "Rate" metric across the different curve
		 * 	construction methodologies for a sequence of bespoke OIS instruments.
		 */

		CalibratableComponent[] aCC = OvernightIndexFromMaturityTenor (
			dtSpot,
			new java.lang.String[] {
				"3Y", "6Y", "9Y", "12Y", "15Y", "18Y", "21Y", "24Y", "27Y", "30Y"
			},
			new double[] {
				0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01
			},
			strCurrency
		);

		System.out.println ("\n\t--------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t                                                BESPOKE OIS PAR RATE");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t        SHAPE PRESERVING   |  LOCAL AKIMA  | LOCAL HARMONIC | LOCAL HYMAN83  | LOCAL HYMAN89  | LOCAL HUYNHLF  | LOCAL KRUGER ");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------------------------------");

		for (int i = 0; i < aCC.length; ++i)
			System.out.println ("\t[" + aCC[i].maturityDate() + "] = " +
				FormatUtil.FormatDouble (
					aCC[i].measureValue (valParams, null,
					MarketParamsBuilder.Create (dcShapePreserving, null, null, null, null, null, null),
					null,
					"CalibSwapRate"),
				1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aCC[i].measureValue (valParams, null,
					MarketParamsBuilder.Create (dcLocalAkima, null, null, null, null, null, null),
					null,
					"CalibSwapRate"),
				1, 6, 1.) + "   |   " +
				FormatUtil.FormatDouble (
					aCC[i].measureValue (valParams, null,
					MarketParamsBuilder.Create (dcLocalHarmonic, null, null, null, null, null, null),
					null,
					"CalibSwapRate"),
				1, 6, 1.) + "    |   " +
				FormatUtil.FormatDouble (
					aCC[i].measureValue (valParams, null,
					MarketParamsBuilder.Create (dcLocalHyman83, null, null, null, null, null, null),
					null,
					"CalibSwapRate"),
					1, 6, 1.) + "    |   " +
				FormatUtil.FormatDouble (
					aCC[i].measureValue (valParams, null,
					MarketParamsBuilder.Create (dcLocalHyman89, null, null, null, null, null, null),
					null,
					"CalibSwapRate"),
					1, 6, 1.) + "    |   " +
				FormatUtil.FormatDouble (
					aCC[i].measureValue (valParams, null,
					MarketParamsBuilder.Create (dcLocalHuynhLeFloch, null, null, null, null, null, null),
					null,
					"CalibSwapRate"),
					1, 6, 1.) + "    |   " +
				FormatUtil.FormatDouble (
					aCC[i].measureValue (valParams, null,
					MarketParamsBuilder.Create (dcLocalKruger, null, null, null, null, null, null),
					null,
					"CalibSwapRate"),
				1, 6, 1.)
			);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.Today();

		ShapeOISDFZeroLocalSmoothSample (
			dtSpot,
			"---- DISCOUNT CURVE WITH OVERNIGHT INDEX ---",
			"EUR"
		);
	}
}
