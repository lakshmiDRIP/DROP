
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
 * <i>ThreeSumQuadraticHash</i> implements the Check that indicates if the Set of Numbers contains 3 that Sum
 *  to Zero using a Hash-table, leading to a Quadratic Time Algorithm. The References are:
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

public class ThreeSumQuadraticHash
	extends org.drip.graph.subarray.ThreeSum
{

	/**
	 * ThreeSumQuadraticHash Constructor
	 * 
	 * @param numberArray The Number Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ThreeSumQuadraticHash (
		final double[] numberArray)
		throws java.lang.Exception
	{
		super (
			numberArray
		);
	}

	@Override public boolean zeroSumExists()
	{
		java.util.HashSet<java.lang.Double> elementSet = new java.util.HashSet<java.lang.Double>();

		double[] numberArray = numberArray();

		for (double number : numberArray)
		{
			elementSet.add (
				-1. * number
			);
		}

		for (double number1 : numberArray)
		{
			for (double number2 : numberArray)
			{
				if (elementSet.contains (
					number1 + number2
				))
				{
					return true;
				}
			}
		}

		return false;
	}
}
