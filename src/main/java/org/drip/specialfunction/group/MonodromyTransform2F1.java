
package org.drip.specialfunction.group;

import org.drip.numerical.complex.C1Cartesian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>MonodromyTransform2F1</i> builds out the Monodromy Loop Solution Transformation Matrices for Paths
 * 	around the Singular Points. The References are:
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
 * 	It provides the following functionality:
 *
 *  <ul>
 * 		<li>Generate the Monodromy Group Matrix G0 around the '0' Singularity</li>
 * 		<li>Generate the "Mu" Intermediate for the G1 Monodromy Matrix</li>
 * 		<li>Generate the Monodromy Group Matrix G1 around the '1' Singularity</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation and Analysis</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/group/README.md">Special Function Singularity Solution Group</a></td></tr>
 *  </table>
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

	public static final C1Cartesian[][] G0 (
		final FundamentalGroupPathExponent2F1 pathExponent1,
		final FundamentalGroupPathExponent2F1 pathExponent2)
	{
		if (null == pathExponent1 || null == pathExponent2) {
			return null;
		}

		C1Cartesian[][] g0 = new C1Cartesian[2][2];

		double theta1 = 2. * Math.PI * pathExponent1.alpha();

		double theta2 = 2. * Math.PI * pathExponent2.alpha();

		try {
			g0[0][0] = new C1Cartesian (Math.cos (theta1), Math.sin (theta1));

			g0[0][1] = new C1Cartesian (0., 0.);

			g0[1][0] = new C1Cartesian (0., 0.);

			g0[1][1] = new C1Cartesian (Math.cos (theta2), Math.sin (theta2));
		} catch (Exception e) {
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
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public static final double G1Mu (
		final FundamentalGroupPathExponent2F1 pathExponent1,
		final FundamentalGroupPathExponent2F1 pathExponent2)
		throws Exception
	{
		if (null == pathExponent1 || null == pathExponent2) {
			throw new Exception ("MonodromyTransform2F1::G1Mu => Invalid Inputs");
		}

		double beta1 = pathExponent1.beta();

		double beta2 = pathExponent2.beta();

		double alpha1 = pathExponent1.alpha();

		double alpha2 = pathExponent2.alpha();

		double gamma2 = pathExponent2.gamma();

		return Math.sin (Math.PI * (alpha1 + beta2 + gamma2)) *
			Math.sin (Math.PI * (alpha2 + beta1 + gamma2)) /
			Math.sin (Math.PI * (alpha2 + beta2 + gamma2)) /
			Math.sin (Math.PI * (alpha1 + beta1 + gamma2));
	}

	/**
	 * Generate the Monodromy Group Matrix G1 around the '1' Singularity
	 * 
	 * @param pathExponent1 Path Monodromy Exponents of the Fundamental Group #1
	 * @param pathExponent2 Path Monodromy Exponents of the Fundamental Group #2
	 * 
	 * @return The Monodromy Group Matrix G1 around the '1' Singularity
	 */

	public static final C1Cartesian[][] G1 (
		final FundamentalGroupPathExponent2F1 pathExponent1,
		final FundamentalGroupPathExponent2F1 pathExponent2)
	{
		if (null == pathExponent1 || null == pathExponent2) {
			return null;
		}

		C1Cartesian[][] g0 = new C1Cartesian[2][2];

		double theta1 = 2. * Math.PI * pathExponent1.beta();

		double theta2 = 2. * Math.PI * pathExponent2.beta();

		try {
			double g1Mu = G1Mu (pathExponent1, pathExponent2);

			double muMinus1 = g1Mu - 1.;
			double muMinus1Squared = muMinus1 * muMinus1;

			g0[0][0] = new C1Cartesian (
				(g1Mu * Math.cos (theta1) - Math.cos (theta2)) / muMinus1,
				(g1Mu * Math.sin (theta1) - Math.sin (theta2)) / muMinus1
			);

			g0[0][1] = new C1Cartesian (
				Math.cos (theta2) - Math.cos (theta1),
				Math.sin (theta2) - Math.sin (theta1)
			);

			g0[1][0] = new C1Cartesian (
				g1Mu * (Math.cos (theta2) - Math.cos (theta1)) / (muMinus1Squared * muMinus1Squared),
				g1Mu * (Math.sin (theta2) - Math.sin (theta1)) / (muMinus1Squared * muMinus1Squared)
			);

			g0[1][1] = new C1Cartesian (
				(g1Mu * Math.cos (theta2) - Math.cos (theta1)) / muMinus1,
				(g1Mu * Math.sin (theta2) - Math.sin (theta1)) / muMinus1
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return g0;
	}
}
