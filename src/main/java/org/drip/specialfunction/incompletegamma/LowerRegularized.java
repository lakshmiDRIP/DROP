
package org.drip.specialfunction.incompletegamma;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>LowerRegularized</i> implements the Regularized Version of the Lower Incomplete Gamma. The References
 * are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Geddes, K. O., M. L. Glasser, R. A. Moore, and T. C. Scott (1990): Evaluation of Classes of
 * 				Definite Integrals involving Elementary Functions via Differentiation of Special Functions
 * 				<i>Applicable Algebra in Engineering, Communications, and </i> <b>1 (2)</b> 149-165
 * 		</li>
 * 		<li>
 * 			Gradshteyn, I. S., I. M. Ryzhik, Y. V. Geronimus, M. Y. Tseytlin, and A. Jeffrey (2015):
 * 				<i>Tables of Integrals, Series, and Products</i> <b>Academic Press</b>
 * 		</li>
 * 		<li>
 * 			Mathar, R. J. (2010): Numerical Evaluation of the Oscillatory Integral over
 *				e<sup>iπx</sup> x<sup>(1/x)</sup> between 1 and ∞
 *				https://arxiv.org/pdf/0912.3844.pdf <b>arXiV</b>
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2019): Incomplete Gamma and Related Functions
 * 				https://dlmf.nist.gov/8
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Incomplete Gamma Function
 * 				https://en.wikipedia.org/wiki/Incomplete_gamma_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/gammaincomplete/README.md">Upper/Lower Incomplete Gamma Functions</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class LowerRegularized
{

	/**
	 * Construct the Gauss Continued Version of Lower Regularized Incomplete Gamma Function
	 * 
	 * @param n Count of the Number of Terms
	 * 
	 * @return Gauss Continued Version of Lower Regularized Incomplete Gamma Function
	 */

	public static final LowerRegularized GaussContinuedFraction (
		final int n)
	{
		return new LowerRegularized()
		{
			@Override public double p (
				final double s,
				final double z)
				throws java.lang.Exception
			{
				double p = org.drip.specialfunction.incompletegamma.GaussContinuedFraction.Lower (
					z,
					s,
					n
				) / new org.drip.specialfunction.gamma.NemesAnalytic (null).evaluate (s);

				return p > 1. ? 1. : p;
			}
		};
	}

	/**
	 * Construct the Euler Integral Version of Lower Regularized Incomplete Gamma Function
	 * 
	 * @return Euler Integral Version of Lower Regularized Incomplete Gamma Function
	 */

	public static final LowerRegularized EulerIntegral()
	{
		return new LowerRegularized()
		{
			@Override public double p (
				final double s,
				final double z)
				throws java.lang.Exception
			{
				double p = new org.drip.specialfunction.incompletegamma.LowerEulerIntegral (
					null,
					z
				).evaluate (s) / new org.drip.specialfunction.gamma.NemesAnalytic (null).evaluate (s);

				return p > 1. ? 1. : p;
			}
		};
	}

	/**
	 * Construct the Weierstrass Limit Version of Lower Regularized Incomplete Gamma Function
	 * 
	 * @param n Count of the Number of Terms
	 * 
	 * @return Weierstrass Limit Version of Lower Regularized Incomplete Gamma Function
	 */

	public static final LowerRegularized WeierstrassLimit (
		final int n)
	{
		return new LowerRegularized()
		{
			@Override public double p (
				final double s,
				final double z)
				throws java.lang.Exception
			{
				double p = org.drip.specialfunction.incompletegamma.LowerSFixed.WeierstrassLimit (
					s,
					n
				).evaluate (z) / new org.drip.specialfunction.gamma.NemesAnalytic (null).evaluate (s);

				return p > 1. ? 1. : p;
			}
		};
	}

	/**
	 * Construct the NIST (2019) Version of Lower Regularized Incomplete Gamma Function
	 * 
	 * @param n Count of the Number of Terms
	 * 
	 * @return NIST (2019) Version of Lower Regularized Incomplete Gamma Function
	 */

	public static final LowerRegularized NIST2019 (
		final int n)
	{
		return new LowerRegularized()
		{
			@Override public double p (
				final double s,
				final double z)
				throws java.lang.Exception
			{
				double p = org.drip.specialfunction.incompletegamma.LowerSFixed.NIST2019 (
					s,
					n
				).evaluate (z) / new org.drip.specialfunction.gamma.NemesAnalytic (null).evaluate (s);

				return p > 1. ? 1. : p;
			}
		};
	}

	private LowerRegularized()
	{
	}

	/**
	 * Compute p (s, z)
	 * 
	 * @param s s
	 * @param z z
	 * 
	 * @return p(s, z)
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double p (
		final double s,
		final double z)
		throws java.lang.Exception;
}
