
package org.drip.specialfunction.group;

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
 * <i>MonodromyTransform2F1</i> builds out the Monodromy Loop Solution Transformation Matrices for Paths
 * around the Singular Points. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Gessel, I., and D. Stanton (1982): Strange Evaluations of Hyper-geometric Series <i>SIAM Journal
 * 				on Mathematical Analysis</i> <b>13 (2)</b> 295-308
 * 		</li>
 * 		<li>
 * 			Koepf, W (1995): Algorithms for m-fold Hyper-geometric Summation <i>Journal of Symbolic
 * 				Computation</i> <b>20 (4)</b> 399-417
 * 		</li>
 * 		<li>
 * 			Lavoie, J. L., F. Grondin, and A. K. Rathie (1996): Generalization of Whipple’s Theorem on the
 * 				Sum of a (_2^3)F(a,b;c;z) <i>Journal of Computational and Applied Mathematics</i> <b>72</b>
 * 				293-300
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2019): Hyper-geometric Function
 * 				https://dlmf.nist.gov/15
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Hyper-geometric Function https://en.wikipedia.org/wiki/Hypergeometric_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/group/README.md">Special Function Singularity Solution Group</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MonodromyTransform2F1
{

	/**
	 * Generate the Monodromy Group Matrix G0 around the '0' Singularity
	 * 
	 * @param pathExponent1 Path Monodromy Exponents of the Fundamental Group #1
	 * @param pathExponent2 Path Monodromy Exponents of the Fundamental Group #2
	 * 
	 * @return The Monodromy Group Matrix G0 around the '0' Singularity
	 */

	public static final org.drip.numerical.fourier.ComplexNumber[][] G0 (
		final org.drip.specialfunction.group.FundamentalGroupPathExponent2F1 pathExponent1,
		final org.drip.specialfunction.group.FundamentalGroupPathExponent2F1 pathExponent2)
	{
		if (null == pathExponent1 || null == pathExponent2)
		{
			return null;
		}

		org.drip.numerical.fourier.ComplexNumber[][] g0 = new org.drip.numerical.fourier.ComplexNumber[2][2];

		double theta1 = 2. * java.lang.Math.PI * pathExponent1.alpha();

		double theta2 = 2. * java.lang.Math.PI * pathExponent2.alpha();

		try
		{
			g0[0][0] = new org.drip.numerical.fourier.ComplexNumber (
				java.lang.Math.cos (theta1),
				java.lang.Math.sin (theta1)
			);

			g0[0][1] = new org.drip.numerical.fourier.ComplexNumber (
				0.,
				0.
			);

			g0[1][0] = new org.drip.numerical.fourier.ComplexNumber (
				0.,
				0.
			);

			g0[1][1] = new org.drip.numerical.fourier.ComplexNumber (
				java.lang.Math.cos (theta2),
				java.lang.Math.sin (theta2)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return g0;
	}

	/**
	 * Compute the "Mu" Intermediate for the G1 Monodromy Matrix
	 * 
	 * @param pathExponent1 Path Monodromy Exponents of the Fundamental Group #1
	 * @param pathExponent2 Path Monodromy Exponents of the Fundamental Group #2
	 * 
	 * @return The "Mu" Intermediate for the G1 Monodromy Matrix
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double G1Mu (
		final org.drip.specialfunction.group.FundamentalGroupPathExponent2F1 pathExponent1,
		final org.drip.specialfunction.group.FundamentalGroupPathExponent2F1 pathExponent2)
		throws java.lang.Exception
	{
		if (null == pathExponent1 || null == pathExponent2)
		{
			throw new java.lang.Exception ("MonodromyTransform2F1::G1Mu => Invalid Inputs");
		}

		double beta1 = pathExponent1.beta();

		double beta2 = pathExponent2.beta();

		double alpha1 = pathExponent1.alpha();

		double alpha2 = pathExponent2.alpha();

		double gamma2 = pathExponent2.gamma();

		return java.lang.Math.sin (java.lang.Math.PI * (alpha1 + beta2 + gamma2)) *
			java.lang.Math.sin (java.lang.Math.PI * (alpha2 + beta1 + gamma2)) /
			java.lang.Math.sin (java.lang.Math.PI * (alpha2 + beta2 + gamma2)) /
			java.lang.Math.sin (java.lang.Math.PI * (alpha1 + beta1 + gamma2));
	}

	/**
	 * Generate the Monodromy Group Matrix G1 around the '1' Singularity
	 * 
	 * @param pathExponent1 Path Monodromy Exponents of the Fundamental Group #1
	 * @param pathExponent2 Path Monodromy Exponents of the Fundamental Group #2
	 * 
	 * @return The Monodromy Group Matrix G1 around the '1' Singularity
	 */

	public static final org.drip.numerical.fourier.ComplexNumber[][] G1 (
		final org.drip.specialfunction.group.FundamentalGroupPathExponent2F1 pathExponent1,
		final org.drip.specialfunction.group.FundamentalGroupPathExponent2F1 pathExponent2)
	{
		if (null == pathExponent1 || null == pathExponent2)
		{
			return null;
		}

		org.drip.numerical.fourier.ComplexNumber[][] g0 = new org.drip.numerical.fourier.ComplexNumber[2][2];

		double theta1 = 2. * java.lang.Math.PI * pathExponent1.beta();

		double theta2 = 2. * java.lang.Math.PI * pathExponent2.beta();

		try
		{
			double g1Mu = G1Mu (
				pathExponent1,
				pathExponent2
			);

			double muMinus1 = g1Mu - 1.;
			double muMinus1Squared = muMinus1 * muMinus1;

			g0[0][0] = new org.drip.numerical.fourier.ComplexNumber (
				(g1Mu * java.lang.Math.cos (theta1) - java.lang.Math.cos (theta2)) / muMinus1,
				(g1Mu * java.lang.Math.sin (theta1) - java.lang.Math.sin (theta2)) / muMinus1
			);

			g0[0][1] = new org.drip.numerical.fourier.ComplexNumber (
				java.lang.Math.cos (theta2) - java.lang.Math.cos (theta1),
				java.lang.Math.sin (theta2) - java.lang.Math.sin (theta1)
			);

			g0[1][0] = new org.drip.numerical.fourier.ComplexNumber (
				g1Mu * (java.lang.Math.cos (theta2) - java.lang.Math.cos (theta1)) /
					(muMinus1Squared * muMinus1Squared),
				g1Mu * (java.lang.Math.sin (theta2) - java.lang.Math.sin (theta1)) /
					(muMinus1Squared * muMinus1Squared)
			);

			g0[1][1] = new org.drip.numerical.fourier.ComplexNumber (
				(g1Mu * java.lang.Math.cos (theta2) - java.lang.Math.cos (theta1)) / muMinus1,
				(g1Mu * java.lang.Math.sin (theta2) - java.lang.Math.sin (theta1)) / muMinus1
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return g0;
	}
}
