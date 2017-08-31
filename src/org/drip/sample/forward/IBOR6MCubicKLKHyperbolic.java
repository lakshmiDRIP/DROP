
package org.drip.sample.forward;

import org.drip.analytics.date.*;
import org.drip.function.r1tor1.QuadraticRationalShapeControl;
import org.drip.service.env.EnvManager;
import org.drip.spline.basis.ExponentialTensionSetParams;
import org.drip.spline.params.*;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.discount.*;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.ForwardLabel;

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
 * This Sample illustrates the Construction and Usage of the IBOR 6M Forward Curve Using Cubic KLK
 * 	Hyperbolic Tension B-Spline.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class IBOR6MCubicKLKHyperbolic {
	public static final ForwardCurve Make6MForward (
		final JulianDate dtValue,
		final String strCurrency,
		final String strTenor)
		throws Exception
	{
		ForwardLabel fri = ForwardLabel.Create (
			strCurrency,
			strTenor
		);

		MergedDiscountForwardCurve dcEONIA = OvernightIndexCurve.MakeDC (
			dtValue,
			strCurrency
		);

		SegmentCustomBuilderControl scbcCubicKLKHyperbolic = new SegmentCustomBuilderControl (
			MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION,
			new ExponentialTensionSetParams (1.),
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
		 * Construct the Array of Deposit Instruments and their Quotes from the given set of parameters
		 */

		double[] adblDepositQuote = new double[] {
			0.003565,	// 1D
			0.003858,	// 1W
			0.003840,	// 2W
			0.003922,	// 3W
			0.003869,	// 1M
			0.003698,	// 2M
			0.003527,	// 3M
			0.003342,	// 4M
			0.003225	// 5M
		};

		String[] astrDepositTenor = new String[] {
			"1D",
			"1W",
			"2W",
			"3W",
			"1M",
			"2M",
			"3M",
			"4M",
			"5M"
		};

		/*
		 * Construct the Array of FRAs and their Quotes from the given set of parameters
		 */

		double[] adblFRAQuote = new double[] {
			0.003120,	//  0D
			0.002930,	//  1M
			0.002720,	//  2M
			0.002600,	//  3M
			0.002560,	//  4M
			0.002520,	//  5M
			0.002480,	//  6M
			0.002540,	//  7M
			0.002610,	//  8M
			0.002670,	//  9M
			0.002790,	// 10M
			0.002910,	// 11M
			0.003030,	// 12M
			0.003180,	// 13M
			0.003350,	// 14M
			0.003520,	// 15M
			0.003710,	// 16M
			0.003890,	// 17M
			0.004090	// 18M
		};

		String[] astrFRATenor = new String[] {
			 "0D",
			 "1M",
			 "2M",
			 "3M",
			 "4M",
			 "5M",
			 "6M",
			 "7M",
			 "8M",
			 "9M",
			"10M",
			"11M",
			"12M",
			"13M",
			"14M",
			"15M",
			"16M",
			"17M",
			"18M"
		};

		/*
		 * Construct the Array of Fix-Float Component and their Quotes from the given set of parameters
		 */

		double[] adblFixFloatQuote = new double[] {
			0.004240,	//  3Y
			0.005760,	//  4Y			
			0.007620,	//  5Y
			0.009540,	//  6Y
			0.011350,	//  7Y
			0.013030,	//  8Y
			0.014520,	//  9Y
			0.015840,	// 10Y
			0.018090,	// 12Y
			0.020370,	// 15Y
			0.021870,	// 20Y
			0.022340,	// 25Y
			0.022560,	// 30Y
			0.022950,	// 35Y
			0.023480,	// 40Y
			0.024210,	// 50Y
			0.024630	// 60Y
		};

		String[] astrFixFloatTenor = new String[] {
			 "3Y",
			 "4Y",
			 "5Y",
			 "6Y",
			 "7Y",
			 "8Y",
			 "9Y",
			"10Y",
			"12Y",
			"15Y",
			"20Y",
			"25Y",
			"30Y",
			"35Y",
			"40Y",
			"50Y",
			"60Y"
		};

		ForwardCurve fc = IBORCurve.CustomIBORBuilderSample (
			dcEONIA,
			null,
			fri,
			scbcCubicKLKHyperbolic,
			astrDepositTenor,
			adblDepositQuote,
			"ForwardRate",
			astrFRATenor,
			adblFRAQuote,
			"ParForwardRate",
			astrFixFloatTenor,
			adblFixFloatQuote,
			"SwapRate",
			null,
			null,
			"DerivedParBasisSpread",
			null,
			null,
			"DerivedParBasisSpread",
			"---- EURIBOR 6M CUBIC KLK HYPERBOLIC TENSION B-SPLINE FORWARD CURVE ---",
			true
		);

		/* IBOR.ForwardJack (
			dtValue,
			"---- EURIBOR 6M CUBIC KLK HYPERBOLIC TENSION B-SPLINE FORWARD CURVE SENSITIVITY ---",
			fc,
			"DerivedParBasisSpread"
		); */

		return fc;
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		Make6MForward (
			DateUtil.CreateFromYMD (
				2012,
				DateUtil.DECEMBER,
				11
			),
			"EUR",
			"6M"
		);
	}
}
