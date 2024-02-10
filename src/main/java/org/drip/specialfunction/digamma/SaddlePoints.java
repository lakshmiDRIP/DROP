
package org.drip.specialfunction.digamma;

import java.util.TreeSet;

import org.drip.function.definition.R1ToR1;
import org.drip.specialfunction.gamma.Definitions;

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
 * <i>SaddlePoints</i> contains the Hermite Based Saddle Point Roots of the Digamma Function. The References
 * 	are:
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
 * 	It provides the following functionality:
 *
 *  <ul>
 * 		<li>Generate the Set of Leading Digamma Saddle Points</li>
 * 		<li>Construct the R<sup>1</sup> to R<sup>1</sup> Hermite Digamma Root Function</li>
 * 		<li>Construct the R<sup>1</sup> to R<sup>1</sup> Hermite Extension Digamma Root Function</li>
 * 		<li>Construct the R<sup>1</sup> to R<sup>1</sup> Hermite Enhancement Digamma Root Function</li>
 * 		<li>Construct the R<sup>1</sup> to R<sup>1</sup> Mezo-Hoffman (2017) Digamma Root Function</li>
 * 		<li>Generate the Array of Leading Roots</li>
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
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation and Analysis</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/digamma/README.md">Estimation Techniques for Digamma Function</a></td></tr>
 *  </table>
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

	public static final TreeSet<Double> LeadingZeros()
	{
		TreeSet<Double> zeroSet = new TreeSet<Double>();

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

	public static final R1ToR1 Hermite()
	{
		try {
			return new R1ToR1 (null) {
				@Override public double evaluate (
					final double z)
					throws Exception
				{
					if (1. > z) {
						throw new Exception ("SaddlePoints::Hermite::evaluate => Invalid Inputs");
					}

					return 1. / Math.log (z) - z;
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the R<sup>1</sup> to R<sup>1</sup> Hermite Extension Digamma Root Function
	 * 
	 * @return The R<sup>1</sup> to R<sup>1</sup> Hermite Extension Digamma Root Function
	 */

	public static final R1ToR1 HermiteExtension()
	{
		try {
			return new R1ToR1 (null) {
				@Override public double evaluate (
					final double z)
					throws Exception
				{
					if (1. > z) {
						throw new Exception ("SaddlePoints::HermiteExtension::evaluate => Invalid Inputs");
					}

					return Math.atan (Math.PI / Math.log (z)) / Math.PI - z;
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the R<sup>1</sup> to R<sup>1</sup> Hermite Enhancement Digamma Root Function
	 * 
	 * @return The R<sup>1</sup> to R<sup>1</sup> Hermite Enhancement Digamma Root Function
	 */

	public static final R1ToR1 HermiteEnhancement()
	{
		try {
			return new R1ToR1 (null) {
				@Override public double evaluate (
					final double z)
					throws Exception
				{
					if (1. > z) {
						throw new Exception ("SaddlePoints::HermiteEnhancement::evaluate => Invalid Inputs");
					}

					return Math.atan (Math.PI / (Math.log (z) + (0.125 / z))) / Math.PI - z;
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the R<sup>1</sup> to R<sup>1</sup> Mezo-Hoffman (2017) Digamma Root Function
	 * 
	 * @return The R<sup>1</sup> to R<sup>1</sup> Mezo-Hoffman (2017) Digamma Root Function
	 */

	public static final R1ToR1 MezoHoffman2017()
	{
		try {
			return new R1ToR1 (null) {
				@Override public double evaluate (
					final double z)
					throws Exception
				{
					if (1. > z) {
						throw new Exception ("SaddlePoints::MezoHoffman2017::evaluate => Invalid Inputs");
					}

					double logZReciprocal = 1. / Math.log (z);

					return logZReciprocal - z - 0.5 * logZReciprocal * logZReciprocal / z;
				}
			};
		} catch (Exception e) {
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
		final R1ToR1 rootFunction,
		final int rootCount)
	{
		if (null == rootFunction || 0 >= rootCount) {
			return null;
		}

		double[] leadingRootArray = new double[rootCount + 1];
		leadingRootArray[0] = Definitions.MINIMUM_VARIATE_LOCATION;

		for (int rootIndex = 1; rootIndex <= rootCount; ++rootIndex) {
			try {
				leadingRootArray[rootIndex] = rootFunction.evaluate (rootIndex);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return leadingRootArray;
	}
}
