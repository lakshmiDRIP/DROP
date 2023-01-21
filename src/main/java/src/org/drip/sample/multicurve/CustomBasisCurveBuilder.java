
package org.drip.sample.multicurve;

import org.drip.analytics.date.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.basis.BasisCurve;
import org.drip.state.creator.ScenarioBasisCurveBuilder;
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
 * <i>CustomBasisCurveBuilder</i> contains the sample demonstrating the full functionality behind creating
 * 	highly customized spline based Basis curves.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/multicurve/README.md">Multi-Curve Construction and Valuation</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CustomBasisCurveBuilder {

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

		JulianDate dtToday = DateUtil.Today();

		String[] astrTenor = new String[] {
			"1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y"
		};

		double[] adblBasis = new double[] {
			0.00186,    //  1Y
			0.00127,    //  2Y
			0.00097,    //  3Y
			0.00080,    //  4Y
			0.00067,    //  5Y
			0.00058,    //  6Y
			0.00051,    //  7Y
			0.00046,    //  8Y
			0.00042,    //  9Y
			0.00038,    // 10Y
			0.00035,    // 11Y
			0.00033,    // 12Y
			0.00028,    // 15Y
			0.00022,    // 20Y
			0.00020,    // 25Y
			0.00018     // 30Y
		};

		BasisCurve bcCubicPolynomial = ScenarioBasisCurveBuilder.CubicPolynomialBasisCurve (
			"USD3M6MBasis_CubicPolynomial",
			dtToday,
			ForwardLabel.Create (
				"USD",
				"6M"
			),
			ForwardLabel.Create (
				"USD",
				"3M"
			),
			false,
			astrTenor,
			adblBasis
		);

		BasisCurve bcQuinticPolynomial = ScenarioBasisCurveBuilder.QuarticPolynomialBasisCurve (
			"USD3M6MBasis_QuinticPolynomial",
			dtToday,
			ForwardLabel.Create (
				"USD",
				"6M"
			),
			ForwardLabel.Create (
				"USD",
				"3M"
			),
			false,
			astrTenor,
			adblBasis
		);

		BasisCurve bcKaklisPandelis = ScenarioBasisCurveBuilder.KaklisPandelisBasisCurve (
			"USD3M6MBasis_KaklisPandelis",
			dtToday,
			ForwardLabel.Create (
				"USD",
				"6M"
			),
			ForwardLabel.Create (
				"USD",
				"3M"
			),
			false,
			astrTenor,
			adblBasis
		);

		BasisCurve bcKLKHyperbolic = ScenarioBasisCurveBuilder.KLKHyperbolicBasisCurve (
			"USD3M6MBasis_KLKHyperbolic",
			dtToday,
			ForwardLabel.Create (
				"USD",
				"6M"
			),
			ForwardLabel.Create (
				"USD",
				"3M"
			),
			false,
			astrTenor,
			adblBasis,
			1.
		);

		BasisCurve bcKLKRationalLinear = ScenarioBasisCurveBuilder.KLKRationalLinearBasisCurve (
			"USD3M6MBasis_KLKRationalLinear",
			dtToday,
			ForwardLabel.Create (
				"USD",
				"6M"
			),
			ForwardLabel.Create (
				"USD",
				"3M"
			),
			false,
			astrTenor,
			adblBasis,
			0.1
		);

		BasisCurve bcKLKRationalQuadratic = ScenarioBasisCurveBuilder.KLKRationalLinearBasisCurve (
			"USD3M6MBasis_KLKRationalQuadratic",
			dtToday,
			ForwardLabel.Create (
				"USD",
				"6M"
			),
			ForwardLabel.Create (
				"USD",
				"3M"
			),
			false,
			astrTenor,
			adblBasis,
			2.
		);

		System.out.println ("\tPrinting the Basis Node Values in Order (Left -> Right):");

		System.out.println ("\t\tCalculated Cubic Polynomial Basis (%)");

		System.out.println ("\t\tCalculated Quintic Polynomial Basis (%)");

		System.out.println ("\t\tCalculated Kaklis Pandelis Basis (%)");

		System.out.println ("\t\tCalculated KLK Hyperbolic Basis (%)");

		System.out.println ("\t\tCalculated KLK Rational Linear Basis (%)");

		System.out.println ("\t\tCalculated KLK Rational Quadratic Basis (%)");

		System.out.println ("\t\tInput Quote (bp)");

		System.out.println ("\t-------------------------------------------------------------");

		System.out.println ("\t-------------------------------------------------------------");

		for (int i = 0; i < adblBasis.length; ++i)
			System.out.println ("\t" + astrTenor[i] + " => " +
				FormatUtil.FormatDouble (bcCubicPolynomial.basis (astrTenor[i]), 1, 2, 10000.) + " | " +
				FormatUtil.FormatDouble (bcQuinticPolynomial.basis (astrTenor[i]), 1, 2, 10000.) + " | " +
				FormatUtil.FormatDouble (bcKaklisPandelis.basis (astrTenor[i]), 1, 2, 10000.) + " | " +
				FormatUtil.FormatDouble (bcKLKHyperbolic.basis (astrTenor[i]), 1, 2, 10000.) + " | " +
				FormatUtil.FormatDouble (bcKLKRationalLinear.basis (astrTenor[i]), 1, 2, 10000.) + " | " +
				FormatUtil.FormatDouble (bcKLKRationalQuadratic.basis (astrTenor[i]), 1, 2, 10000.) + " | " +
				FormatUtil.FormatDouble (adblBasis[i], 1, 2, 10000.)
			);

		System.out.println ("\n\t|----------------------------------------------------------------------------|");

		System.out.println ("\t|  DATE    =>  CUBIC | QUINTIC  | KAKPAND | KLKHYPER | KLKRATLNR | KLKRATQUA |");

		System.out.println ("\t|----------------------------------------------------------------------------|\n");

		for (int i = 3; i < 30; ++i) {
			JulianDate dt = dtToday.addTenor (i + "Y");

			System.out.println ("\t" + dt + " => " +
				FormatUtil.FormatDouble (bcCubicPolynomial.basis (dt), 1, 2, 10000.) + "  |  " +
				FormatUtil.FormatDouble (bcQuinticPolynomial.basis (dt), 1, 2, 10000.) + "   |  " +
				FormatUtil.FormatDouble (bcKaklisPandelis.basis (dt), 1, 2, 10000.) + "  |  " +
				FormatUtil.FormatDouble (bcKLKHyperbolic.basis (dt), 1, 2, 10000.) + "   |  " +
				FormatUtil.FormatDouble (bcKLKRationalLinear.basis (dt), 1, 2, 10000.) + "    |  " +
				FormatUtil.FormatDouble (bcKLKRationalQuadratic.basis (dt), 1, 2, 10000.) + "    |  "
			);
		}

		System.out.println ("\n\t|----------------------------------------------------------------------------|");

		EnvManager.TerminateEnv();
	}
}
