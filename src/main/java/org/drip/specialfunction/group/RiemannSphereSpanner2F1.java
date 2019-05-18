
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
 * <i>RiemannSphereSpanner</i> determines the Conformality and Tile Scheme of the Schwarz Singular Triangle
 * Maps over the Riemann Sphere composed of the 2F1 Solutions. The References are:
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

public class RiemannSphereSpanner2F1
{

	/**
	 * Generate the 2F1 Instance of the RiemannSphereSpanner
	 * 
	 * @param regularHypergeometricEstimator Regular Hyper-geometric Estimator
	 * @param connectionCoefficientArray Array of the Singularity Point Connection Coefficients
	 * 
	 * @return The 2F1 Instance of the RiemannSphereSpanner
	 */

	public static final org.drip.specialfunction.group.RiemannSphereSpanner Generate (
		final org.drip.specialfunction.definition.RegularHypergeometricEstimator
			regularHypergeometricEstimator,
		final double[] connectionCoefficientArray)
	{
		if (null == connectionCoefficientArray || 3 != connectionCoefficientArray.length)
		{
			return null;
		}

		org.drip.specialfunction.ode.RegularSingularityIndependentSolution
			regularSingularityIndependentSolution2F1 =
				org.drip.specialfunction.ode.RegularSingularityIndependentSolution2F1.Create
					(regularHypergeometricEstimator);

		if (null == regularSingularityIndependentSolution2F1)
		{
			return null;
		}

		java.util.Map<java.lang.Double, org.drip.specialfunction.ode.IndependentLinearSolutionList>
			linearSolutionFunctionMap = regularSingularityIndependentSolution2F1.linearSolutionFunctionMap();

		if (null == linearSolutionFunctionMap ||
			!linearSolutionFunctionMap.containsKey (0.) ||
			!linearSolutionFunctionMap.containsKey (1.) ||
			!linearSolutionFunctionMap.containsKey (java.lang.Double.POSITIVE_INFINITY))
		{
			return null;
		}

		org.drip.specialfunction.group.SchwarzTriangleMap[] schwarzTriangleMapArray = new
			org.drip.specialfunction.group.SchwarzTriangleMap[3];

		try
		{
			java.util.List<org.drip.function.definition.R1ToR1> solutionFunctionList0 =
				linearSolutionFunctionMap.get (0.).solutionFunctionList();

			schwarzTriangleMapArray[0] = new org.drip.specialfunction.group.SchwarzTriangleMap (
				0.,
				solutionFunctionList0.get (0),
				solutionFunctionList0.get (1),
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
					{
						return z;
					}
				},
				connectionCoefficientArray[0]
			);

			java.util.List<org.drip.function.definition.R1ToR1> solutionFunctionList1 =
				linearSolutionFunctionMap.get (1.).solutionFunctionList();

			schwarzTriangleMapArray[1] = new org.drip.specialfunction.group.SchwarzTriangleMap (
				1.,
				solutionFunctionList1.get (0),
				solutionFunctionList1.get (1),
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
					{
						return 1. - z;
					}
				},
				connectionCoefficientArray[1]
			);

			java.util.List<org.drip.function.definition.R1ToR1> solutionFunctionList2 =
				linearSolutionFunctionMap.get (java.lang.Double.POSITIVE_INFINITY).solutionFunctionList();

			schwarzTriangleMapArray[2] = new org.drip.specialfunction.group.SchwarzTriangleMap (
				java.lang.Double.POSITIVE_INFINITY,
				solutionFunctionList2.get (0),
				solutionFunctionList2.get (1),
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double z)
					{
						return 1. / z;
					}
				},
				connectionCoefficientArray[2]
			);

			return new org.drip.specialfunction.group.RiemannSphereSpanner (schwarzTriangleMapArray);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
