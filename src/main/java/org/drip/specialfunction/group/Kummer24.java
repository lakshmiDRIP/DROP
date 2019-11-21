
package org.drip.specialfunction.group;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>Kummer24</i> contains the Isomorphic Klein-4 Group of the Transformations built out of the Solutions
 * emanating from the Singularities of the Hyper-geometric 2F1 Function. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/group/README.md">Special Function Singularity Solution Group</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Kummer24 extends org.drip.specialfunction.group.FuchsianEquation
{

	/**
	 * Construct the Kummer24 Isomorphic Array Version of the Fuchsian Equation
	 * 
	 * @param regularHypergeometricEstimator The Regular Hyper-geometric Estimator
	 * 
	 * @return The Kummer24 Isomorphic Array Version of the Fuchsian Equation
	 */

	public static final Kummer24 Standard (
		final org.drip.specialfunction.definition.RegularHypergeometricEstimator
			regularHypergeometricEstimator)
	{
		if (null == regularHypergeometricEstimator)
		{
			return null;
		}

		org.drip.specialfunction.definition.HypergeometricParameters hypergeometricParameters =
			regularHypergeometricEstimator.hypergeometricParameters();

		final double a = hypergeometricParameters.a();

		final double b = hypergeometricParameters.b();

		final double c = hypergeometricParameters.c();

		try
		{
			return new Kummer24 (
				new org.drip.function.definition.R1ToR1[]
				{
					regularHypergeometricEstimator.albinate (
						new org.drip.specialfunction.definition.HypergeometricParameters (
							a,
							c - b,
							c
						),
						new org.drip.function.definition.R1ToR1 (null)
						{
							@Override public double evaluate (
								final double z)
								throws java.lang.Exception
							{
								return java.lang.Math.pow (
									1. - z,
									-a
								);
							}
						},
						new org.drip.function.definition.R1ToR1 (null)
						{
							@Override public double evaluate (
								final double z)
								throws java.lang.Exception
							{
								return z / (z - 1.);
							}
						}
					),
					regularHypergeometricEstimator.albinate (
						new org.drip.specialfunction.definition.HypergeometricParameters (
							a,
							b,
							1. + a + b - c
						),
						null,
						new org.drip.function.definition.R1ToR1 (null)
						{
							@Override public double evaluate (
								final double z)
								throws java.lang.Exception
							{
								return 1. - z;
							}
						}
					),
					regularHypergeometricEstimator.albinate (
						new org.drip.specialfunction.definition.HypergeometricParameters (
							c - a,
							b,
							c
						),
						new org.drip.function.definition.R1ToR1 (null)
						{
							@Override public double evaluate (
								final double z)
								throws java.lang.Exception
							{
								return java.lang.Math.pow (
									1. - z,
									-b
								);
							}
						},
						new org.drip.function.definition.R1ToR1 (null)
						{
							@Override public double evaluate (
								final double z)
								throws java.lang.Exception
							{
								return z / (z - 1.);
							}
						}
					),
				}
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	protected Kummer24 (
		final org.drip.function.definition.R1ToR1[] kleinGroupFunctionArray)
		throws java.lang.Exception
	{
		super (kleinGroupFunctionArray);
	}

	/**
	 * Generate the Transposition (12) under the Fuchsian Isomorphism with Symmetry Group on points 1, 2, 3
	 * 
	 * @return The Transposition (12) under the Fuchsian Isomorphism with Symmetry Group on points 1, 2, 3
	 */

	public org.drip.specialfunction.definition.RegularHypergeometricEstimator transposition12()
	{
		return (org.drip.specialfunction.definition.RegularHypergeometricEstimator)
			kleinGroupFunctionArray()[0];
	}

	/**
	 * Generate the Transposition (23) under the Fuchsian Isomorphism with Symmetry Group on points 1, 2, 3
	 * 
	 * @return The Transposition (23) under the Fuchsian Isomorphism with Symmetry Group on points 1, 2, 3
	 */

	public org.drip.specialfunction.definition.RegularHypergeometricEstimator transposition23()
	{
		return (org.drip.specialfunction.definition.RegularHypergeometricEstimator)
			kleinGroupFunctionArray()[1];
	}

	/**
	 * Generate the Transposition (34) under the Fuchsian Isomorphism with Symmetry Group on points 1, 2, 3
	 * 
	 * @return The Transposition (34) under the Fuchsian Isomorphism with Symmetry Group on points 1, 2, 3
	 */

	public org.drip.specialfunction.definition.RegularHypergeometricEstimator transposition34()
	{
		return (org.drip.specialfunction.definition.RegularHypergeometricEstimator)
			kleinGroupFunctionArray()[2];
	}
}
