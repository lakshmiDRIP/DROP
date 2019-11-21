
package org.drip.specialfunction.digamma;

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
 * <i>SaddlePoints</i> contains the Hermite Based Saddle Point Roots of the Digamma Function. The References
 * are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): Handbook of Mathematics Functions <b>Dover Book on
 * 				Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Blagouchine, I. V. (2018): Three Notes on Ser's and Hasse's Representations for the
 * 				Zeta-Functions https://arxiv.org/abs/1606.02044 <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Mezo, I., and M. E. Hoffman (2017): Zeros of the Digamma Function and its Barnes G-function
 * 				Analogue <i>Integral Transforms and Special Functions</i> <b>28 (28)</b> 846-858
 * 		</li>
 * 		<li>
 * 			Whitaker, E. T., and G. N. Watson (1996): <i>A Course on Modern Analysis</i> <b>Cambridge
 * 				University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Digamma Function https://en.wikipedia.org/wiki/Digamma_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/digamma/README.md">Estimation Techniques for Digamma Function</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SaddlePoints
{

	/**
	 * Generate the Set of Leading Digamma Saddle Points
	 * 
	 * @return Set of Leading Digamma Saddle Points
	 */

	public static final java.util.TreeSet<java.lang.Double> LeadingZeros()
	{
		java.util.TreeSet<java.lang.Double> zeroSet = new java.util.TreeSet<java.lang.Double>();

		zeroSet.add ( 1.461632144968);

		zeroSet.add (-0.504083008000);

		zeroSet.add (-1.573498473000);

		zeroSet.add (-2.610720868000);

		zeroSet.add (-3.635293366000);

		return zeroSet;
	}

	/**
	 * Construct the R<sup>1</sup> to R<sup>1</sup> Hermite Digamma Root Function
	 * 
	 * @return The R<sup>1</sup> to R<sup>1</sup> Hermite Digamma Root Function
	 */

	public static final org.drip.function.definition.R1ToR1 Hermite()
	{
		try
		{
			return new org.drip.function.definition.R1ToR1 (null)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (1. > z)
					{
						throw new java.lang.Exception ("SaddlePoints::Hermite::evaluate => Invalid Inputs");
					}

					return 1. / java.lang.Math.log (z) - z;
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the R<sup>1</sup> to R<sup>1</sup> Hermite Extension Digamma Root Function
	 * 
	 * @return The R<sup>1</sup> to R<sup>1</sup> Hermite Extension Digamma Root Function
	 */

	public static final org.drip.function.definition.R1ToR1 HermiteExtension()
	{
		try
		{
			return new org.drip.function.definition.R1ToR1 (null)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (1. > z)
					{
						throw new java.lang.Exception
							("SaddlePoints::HermiteExtension::evaluate => Invalid Inputs");
					}

					return java.lang.Math.atan (java.lang.Math.PI / java.lang.Math.log (z)) /
						java.lang.Math.PI - z;
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the R<sup>1</sup> to R<sup>1</sup> Hermite Enhancement Digamma Root Function
	 * 
	 * @return The R<sup>1</sup> to R<sup>1</sup> Hermite Enhancement Digamma Root Function
	 */

	public static final org.drip.function.definition.R1ToR1 HermiteEnhancement()
	{
		try
		{
			return new org.drip.function.definition.R1ToR1 (null)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (1. > z)
					{
						throw new java.lang.Exception
							("SaddlePoints::HermiteEnhancement::evaluate => Invalid Inputs");
					}

					return java.lang.Math.atan (java.lang.Math.PI / (java.lang.Math.log (z) + (0.125 / z))) /
						java.lang.Math.PI - z;
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the R<sup>1</sup> to R<sup>1</sup> Mezo-Hoffman (2017) Digamma Root Function
	 * 
	 * @return The R<sup>1</sup> to R<sup>1</sup> Mezo-Hoffman (2017) Digamma Root Function
	 */

	public static final org.drip.function.definition.R1ToR1 MezoHoffman2017()
	{
		try
		{
			return new org.drip.function.definition.R1ToR1 (null)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (1. > z)
					{
						throw new java.lang.Exception
							("SaddlePoints::MezoHoffman2017::evaluate => Invalid Inputs");
					}

					double logZReciprocal = 1. / java.lang.Math.log (z);

					return logZReciprocal - z - 0.5 * logZReciprocal * logZReciprocal / z;
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Array of Leading Roots
	 * 
	 * @param rootFunction The Root Generation Function
	 * @param rootCount The Root Count
	 * 
	 * @return The Array of Leading Roots
	 */

	public static final double[] LeadingRoots (
		final org.drip.function.definition.R1ToR1 rootFunction,
		final int rootCount)
	{
		if (null == rootFunction ||
			0 >= rootCount)
		{
			return null;
		}

		double[] leadingRootArray = new double[rootCount + 1];
		leadingRootArray[0] = org.drip.specialfunction.gamma.Definitions.MINIMUM_VARIATE_LOCATION;

		for (int rootIndex = 1; rootIndex <= rootCount; ++rootIndex)
		{
			try
			{
				leadingRootArray[rootIndex] = rootFunction.evaluate (rootIndex);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();
			}
		}

		return leadingRootArray;
	}
}
