
package org.drip.function.gammaincomplete;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
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
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>GaussContinuedFraction</i> implements the Gauss Continued Fraction Based Estimates for the Lower/Upper
 * Incomplete Gamma Function. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/gammaincomplete/README.md">Upper/Lower Incomplete Gamma Functions</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class GaussContinuedFraction
{

	private static final double Lower (
		final double z,
		final double s,
		final int k,
		final int n)
	{
		if (k == n)
		{
			return 0;
		}

		double bottom = s + 2 * k + 1 + ((k + 1) * z) / Lower (
			z,
			s,
			k + 1,
			n
		);

		return s + 2 * k - (((k + s) * z) / bottom);
	}

	private static final double UpperAbramowitzStegun2007 (
		final double z,
		final double s,
		final int k,
		final int n)
	{
		if (k == n)
		{
			return 0;
		}

		double bottom = 1 + (k + 1.) / UpperAbramowitzStegun2007 (
			z,
			s,
			k + 1,
			n
		);

		return z + (k + 1 - s) / bottom;
	}

	private static final double Upper (
		final double z,
		final double s,
		final int k,
		final int n)
	{
		if (k == n)
		{
			return 0;
		}

		return 1. + 2. * k + z - s + (k + 1.) * ((s - k - 1.) / Upper (
			z,
			s,
			k + 1,
			n
		));
	}

	/**
	 * Compute the Lower Incomplete Gamma Function using the Gauss Continued Fraction
	 * 
	 * @param z z
	 * @param s s
	 * @param n Count of the Number of Terms
	 * 
	 * @return The Lower Incomplete Gamma Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double Lower (
		final double z,
		final double s,
		final int n)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (z) ||
			!org.drip.numerical.common.NumberUtil.IsValid (s) ||
			0 == n)
		{
			throw new java.lang.Exception ("GaussContinuedFraction::Lower => Invalid Inputs");
		}

		return java.lang.Math.pow (
			z,
			s
		) * java.lang.Math.exp (-z) / Lower (
			z,
			s,
			0,
			n
		);
	}

	/**
	 * Compute the Upper Incomplete Gamma Function using the Abramowitz-Stegun Gauss Continued Fraction
	 * 
	 * @param z z
	 * @param s s
	 * @param n Count of the Number of Terms
	 * 
	 * @return The Upper Incomplete Gamma Function using the Abramowitz-Stegun Gauss Continued Fraction
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double UpperAbramowitzStegun2007 (
		final double z,
		final double s,
		final int n)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (z) ||
			!org.drip.numerical.common.NumberUtil.IsValid (s) ||
			0 == n)
		{
			throw new java.lang.Exception
				("GaussContinuedFraction::UpperAbramowitzStegun2007 => Invalid Inputs");
		}

		return java.lang.Math.pow (
			z,
			s
		) * java.lang.Math.exp (-z) / UpperAbramowitzStegun2007 (
			z,
			s,
			0,
			n
		);
	}

	/**
	 * Compute the Upper Incomplete Gamma Function using Gauss Continued Fraction
	 * 
	 * @param z z
	 * @param s s
	 * @param n Count of the Number of Terms
	 * 
	 * @return The Upper Incomplete Gamma Function using Gauss Continued Fraction
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double Upper (
		final double z,
		final double s,
		final int n)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (z) ||
			!org.drip.numerical.common.NumberUtil.IsValid (s) ||
			0 == n)
		{
			throw new java.lang.Exception ("GaussContinuedFraction::Upper => Invalid Inputs");
		}

		return java.lang.Math.pow (
			z,
			s
		) * java.lang.Math.exp (-z) / Upper (
			z,
			s,
			0,
			n
		);
	}
}
