
package org.drip.sample.forward;

import org.drip.analytics.date.*;
import org.drip.function.r1tor1custom.QuadraticRationalShapeControl;
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
 * <i>IBOR3MCubicKLKHyperbolic</i> illustrates the Construction and Usage of the IBOR 3M Forward Curve Using
 * Cubic KLK Hyperbolic Tension B-Spline.
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

public class IBOR3MCubicKLKHyperbolic {

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

		JulianDate dtValue = DateUtil.CreateFromYMD (
			2012,
			DateUtil.DECEMBER,
			11
		);

		String strTenor = "3M";
		String strCurrency = "USD";

		ForwardLabel fri = ForwardLabel.Create (
			strCurrency,
			strTenor
		);

		MergedDiscountForwardCurve dcEONIA = OvernightIndexCurve.MakeDC (
			dtValue,
			strCurrency
		);

		SegmentCustomBuilderControl scbcKLKHyperbolic = new SegmentCustomBuilderControl (
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
			0.001865,
			0.001969,
			0.001951,
			0.001874
		};

		String[] astrDepositTenor = new String[] {
			"2W",
			"3W",
			"1M",
			"2M"
		};

		/*
		 * Construct the Array of FRAs and their Quotes from the given set of parameters
		 */

		double[] adblFRAQuote = new double[] {
			0.001790,
			0.001775,
			0.001274,
			0.001222,
			0.001269,
			0.001565,
			0.001961,
			0.002556,
			0.003101
		};

		String[] astrFRATenor = new String[] {
			 "0D",
			 "1M",
			 "3M",
			 "6M",
			 "9M",
			"12M",
			"15M",
			"18M",
			"21M"
		};

		/*
		 * Construct the Array of Fix-Float Component and their Quotes from the given set of parameters
		 */

		double[] adblFixFloatQuote = new double[] {
			0.002850,	//  3Y
			0.004370,	//  4Y
			0.006230,	//  5Y
			0.008170,	//  6Y
			0.010000,	//  7Y
			0.011710,	//  8Y
			0.013240,	//  9Y
			0.014590,	// 10Y
			0.016920,	// 12Y
			0.019330,	// 15Y
			0.020990,	// 20Y
			0.021560,	// 25Y
			0.021860 	// 30Y
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
			"30Y"
		};

		/*
		 * Construct the Array of Terminal Synthetic Float-Float Components and their Quotes from the given set of parameters
		 */

		String[] astrSyntheticFloatFloatTenor = new String[] {
			"35Y",
			"40Y",
			"50Y",
			"60Y"
		};

		double[] adblSyntheticFloatFloatQuote = new double[] {
			0.00065,
			0.00060,
			0.00054,
			0.00050
		};

		ForwardCurve fc6M = IBOR6MCubicPolyVanilla.Make6MForward (
			dtValue,
			strCurrency,
			"6M",
			true
		);

		ForwardCurve fc = IBORCurve.CustomIBORBuilderSample (
			dcEONIA,
			fc6M,
			fri,
			scbcKLKHyperbolic,
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
			astrSyntheticFloatFloatTenor,
			adblSyntheticFloatFloatQuote,
			"DerivedParBasisSpread",
			"---- VANILLA CUBIC KLK HYPERBOLIC TENSION B-SPLINE FORWARD CURVE ---",
			true
		);

		IBORCurve.ForwardJack (
			dtValue,
			"---- VANILLA CUBIC KLK HYPERBOLIC TENSION B-SPLINE FORWARD CURVE SENSITIVITY ---",
			fc,
			"DerivedParBasisSpread"
		);

		EnvManager.TerminateEnv();
	}
}
