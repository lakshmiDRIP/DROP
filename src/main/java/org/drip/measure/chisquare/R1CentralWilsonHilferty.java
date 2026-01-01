
package org.drip.measure.chisquare;

import org.drip.measure.gaussian.NormalQuadrature;
import org.drip.measure.gaussian.R1UnivariateNormal;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>R1CentralWilsonHilferty</i> implements the Normal Proxy Version for the R<sup>1</sup> Chi-Square
 * 	Distribution using the Wilson-Hilferty Transfomation. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Backstrom, T., and J. Fischer (2018): Fast Randomization for Distributed Low Bit-rate Coding of
 * 				Speech and Audio <i>IEEE/ACM Transactions on Audio, Speech, and Language Processing</i> <b>26
 * 				(1)</b> 19-30
 * 		</li>
 * 		<li>
 * 			Chi-Squared Distribution (2019): Chi-Squared Function
 * 				https://en.wikipedia.org/wiki/Chi-squared_distribution
 * 		</li>
 * 		<li>
 * 			Johnson, N. L., S. Kotz, and N. Balakrishnan (1994): <i>Continuous Univariate Distributions
 * 				2<sup>nd</sup> Edition</i> <b>John Wiley and Sons</b>
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2019): Chi-Squared Distribution
 * 				https://www.itl.nist.gov/div898/handbook/eda/section3/eda3666.htm
 * 		</li>
 * 	</ul>
 * 
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Construct a Standard Instance of <i>R1CentralWilsonHilferty</i></li>
 * 		<li>Transform x into the Wilson-Hilferty Variate</li>
 * 		<li>Transform the Wilson-Hilferty Variate into x</li>
 * 		<li>Generate a Random Variable corresponding to the Distribution</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/chisquare/README.md">Chi-Square Distribution Implementation/Properties</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1CentralWilsonHilferty
	extends R1WilsonHilferty
{

	/**
	 * Construct a Standard Instance of <i>R1CentralWilsonHilferty</i>
	 * 
	 * @param degreesOfFreedom Degrees of Freedom
	 * 
	 * @return Standard Instance of <i>R1CentralWilsonHilferty</i>
	 */

	public static final R1CentralWilsonHilferty Standard (
		final int degreesOfFreedom)
	{
		if (0 >= degreesOfFreedom) {
			return null;
		}

		double twoOver_9degreesOfFreedom_ = 2. / (9. * degreesOfFreedom);

		try {
			return new R1CentralWilsonHilferty (
				degreesOfFreedom,
				new R1UnivariateNormal (1. - twoOver_9degreesOfFreedom_, twoOver_9degreesOfFreedom_)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected R1CentralWilsonHilferty (
		final int degreesOfFreedom,
		final R1UnivariateNormal r1UnivariateNormal)
		throws Exception
	{
		super (degreesOfFreedom, r1UnivariateNormal);
	}

	/**
	 * Transform x into the Wilson-Hilferty Variate
	 * 
	 * @param x X
	 * 
	 * @return The Wilson-Hilferty Variate
	 */

	@Override public double transform (
		final double x)
	{
		return x;
	}

	/**
	 * Transform the Wilson-Hilferty Variate into x
	 * 
	 * @param wilsonHilferty The Wilson-Hilferty Variate
	 * 
	 * @return The Wilson-Hilferty Variate transformed back to x
	 */

	@Override public double inverseTransform (
		final double wilsonHilferty)
	{
		return wilsonHilferty;
	}

	/**
	 * Generate a Random Variable corresponding to the Distribution
	 * 
	 * @return Random Variable corresponding to the Distribution
	 * 
	 * @throws Exception Thrown if the Random Instance cannot be estimated
	 */

	@Override public double random()
		throws Exception
	{
		double sumOfStandardNormalSquares = 0.;

		double degreesOfFreedom = degreesOfFreedom();

		for (int drawIndex = 0; drawIndex < degreesOfFreedom; ++drawIndex) {
			double randomStandardNormal = NormalQuadrature.InverseCDF (Math.random());

			sumOfStandardNormalSquares = sumOfStandardNormalSquares +
				randomStandardNormal * randomStandardNormal;
		}

		return Math.pow (sumOfStandardNormalSquares / degreesOfFreedom, 1. / 3.);
	}
}
