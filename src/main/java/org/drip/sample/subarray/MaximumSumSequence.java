
package org.drip.sample.subarray;

import org.drip.graph.subarray.Kadane;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
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
 * <i>MaximumSumSequence</i> illustrates Kadane's Maximum Sequential Sub-array Sum Algorithm. The References
 * 	are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Bentley, J. (1984): Programming Pearls: Algorithm Design Techniques <i>Communications of the
 *  			ACM</i> <b>27 (9)</b> 865-873
 *  	</li>
 *  	<li>
 *  		Bentley, J. (1989): <i>Programming Pearls <sup>nd</sup> Edition</i> <b>Addison-Wesley</b> Reading
 *  			MA
 *  	</li>
 *  	<li>
 *  		Gries, D. (1982): A Note on a Standard Strategy for developing Loop Invariants and Loops
 *  			<i>Science of Computer Programming</i> <b>2 (3)</b> 207-214
 *  	</li>
 *  	<li>
 *  		Takaoka, T. (2002): Efficient Algorithms for the Maximum Sub-array Problem by Distance Matrix
 *  			Multiplication https://www.sciencedirect.com/science/article/pii/S1571066104003135?via%3Dihub
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Maximum Sub-array Problem
 *  			https://en.wikipedia.org/wiki/Maximum_subarray_problem
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/subarray/README.md">Sub-set and Sub-array Sums/Matches</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MaximumSumSequence
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		int[] numberArray = new int[]
		{
			-2,
			 1,
			-3,
			 4,
			-1,
			 2,
			 1,
			-5,
			 4,
		};

		Kadane kadanePositiveNegative = new Kadane (
			numberArray,
			true
		);

		System.out.println (
			"\t POSITIVE AND NEGATIVE => " +
			kadanePositiveNegative.maximumSubArraySum() + " [" +
			kadanePositiveNegative.maximumSubarraySequence()[0] + " -> " +
			kadanePositiveNegative.maximumSubarraySequence()[1] + "]"
		);

		Kadane kadanePositive = new Kadane (
			numberArray,
			false
		);

		System.out.println (
			"\t POSITIVE ONLY         => " +
			kadanePositive.maximumSubArraySum() + " [" +
			kadanePositive.maximumSubarraySequence()[0] + " -> " +
			kadanePositive.maximumSubarraySequence()[1] + "]"
		);

		EnvManager.TerminateEnv();
	}
}
