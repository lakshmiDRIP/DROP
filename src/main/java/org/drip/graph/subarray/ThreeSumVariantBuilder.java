
package org.drip.graph.subarray;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
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
 * <i>ThreeSumVariantBuilder</i> converts the specified 3SUM Variant into a Standard 3SUM Problem. The
 * 	References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Chan, T. M. (2018): More Logarithmic Factor Speedups for 3SUM, (median+) Convolution, and some
 *  			Geometric 3SUM-Hard Problems <i>Proceedings of the 29<sup>th</sup> Annual ACM SIAM Symposium
 *  			on Discrete Algorithms</i> 881-897
 *  	</li>
 *  	<li>
 *  		Gajentaan, A., and M. H. Overmars (1995): On a Class of O(n<sup>2</sup>) Problems in
 *  			Computational Geometry <i>Computational Geometry: Theory and Applications</i> <b>5 (3)</b>
 *  			165-185
 *  	</li>
 *  	<li>
 *  		Kopelowitz, T., S. Pettie, and E. Porat (2014): Higher Lower Bounds from the 3SUM Conjecture
 *  			https://arxiv.org/abs/1407.6756 <b>arXiV</b>
 *  	</li>
 *  	<li>
 *  		Patrascu, M. (2010): Towards Polynomial Lower Bounds for Dynamic Problems <i>Proceedings of the
 *  			42<sup>nd</sup> ACM Symposium on Theory of Computing</i> 603-610
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): 3Sum https://en.wikipedia.org/wiki/3SUM
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/subarray/README.md">Sub-set Sum, k-Sum, and Maximum Sub-array Problems</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ThreeSumVariantBuilder
{

	/**
	 * Comparator Based 3SUM Check
	 */

	public static final int COMPARATOR = 1;

	/**
	 * Hash-Table Based 3SUM Check
	 */

	public static final int HASH_TABLE = 2;

	/**
	 * Construct a 3SUM Check where the Target is non-zero
	 * 
	 * @param numberArray Number Array
	 * @param target The Non-zero Target
	 * @param type 3SUM Check Type
	 * 
	 * @return The 3SUM Check
	 */

	public static final org.drip.graph.subarray.ThreeSum NonZeroSum (
		final double[] numberArray,
		final double target,
		final int type)
	{
		if (null == numberArray)
		{
			return null;
		}

		double offset = target / 3.;
		int arrayLength = numberArray.length;
		double[] modifiedArray = new double[arrayLength];

		if (0 == arrayLength)
		{
			return null;
		}

		for (int i = 0;
			i < arrayLength;
			++i)
		{
			modifiedArray[i] = numberArray[i] - offset;
		}

		try
		{
			if (COMPARATOR == type)
			{
				return new org.drip.graph.subarray.ThreeSumQuadraticComparator (
					modifiedArray
				);
			}

			if (HASH_TABLE == type)
			{
				return new org.drip.graph.subarray.ThreeSumQuadraticHash (
					modifiedArray
				);
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a 3SUM Check where the Target Sum across the Three Arrays is non-zero.
	 * 
	 * @param numberArrayA Number Array A
	 * @param numberArrayB Number Array B
	 * @param numberArrayC Number Array C
	 * @param type 3SUM Check Type
	 * 
	 * @return The 3SUM Check
	 */

	public static final org.drip.graph.subarray.ThreeSum ThreeDistinctArrays (
		final double[] numberArrayA,
		final double[] numberArrayB,
		final double[] numberArrayC,
		final int type)
	{
		if (null == numberArrayA ||
			null == numberArrayB ||
			null == numberArrayC)
		{
			return null;
		}

		int index = 0;
		int arrayLengthA = numberArrayA.length;
		int arrayLengthB = numberArrayB.length;
		int arrayLengthC = numberArrayC.length;
		double[] numberArray = new double[arrayLengthA + arrayLengthB + arrayLengthC];

		if (0 == arrayLengthA ||
			0 == arrayLengthB ||
			0 == arrayLengthC)
		{
			return null;
		}

		for (int indexA = 0;
			indexA < arrayLengthA;
			++indexA)
		{
			numberArray[index++] = 10. * numberArrayA[indexA] + 1;
		}

		for (int indexB = 0;
			indexB < arrayLengthB;
			++indexB)
		{
			numberArray[index++] = 10. * numberArrayB[indexB] + 2;
		}

		for (int indexC = 0;
			indexC < arrayLengthC;
			++indexC)
		{
			numberArray[index++] = 10. * numberArrayC[indexC] - 3;
		}

		try
		{
			if (COMPARATOR == type)
			{
				return new org.drip.graph.subarray.ThreeSumQuadraticComparator (
					numberArray
				);
			}

			if (HASH_TABLE == type)
			{
				return new org.drip.graph.subarray.ThreeSumQuadraticHash (
					numberArray
				);
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * Construct a 3SUM Check for i<sup>th</sup> element and j<sup>th</sup> element add up to
	 *  (i + j)<sup>th</sup> for some i, j
	 * 
	 * @param numberArray Number Array
	 * @param type 3SUM Check Type
	 * 
	 * @return The 3SUM Check
	 */

	public static final org.drip.graph.subarray.ThreeSum Convolution3SUM (
		final double[] numberArray,
		final int type)
	{
		if (null == numberArray)
		{
			return null;
		}

		int arrayLength = numberArray.length;
		double[] convolutionArray = new double[arrayLength];

		if (0 == arrayLength)
		{
			return null;
		}

		for (int i = 0;
			i < arrayLength;
			++i)
		{
			convolutionArray[i] = 2 * arrayLength * numberArray[i] + i;
		}

		try
		{
			if (COMPARATOR == type)
			{
				return new org.drip.graph.subarray.ThreeSumQuadraticComparator (
					convolutionArray
				);
			}

			if (HASH_TABLE == type)
			{
				return new org.drip.graph.subarray.ThreeSumQuadraticHash (
					convolutionArray
				);
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
