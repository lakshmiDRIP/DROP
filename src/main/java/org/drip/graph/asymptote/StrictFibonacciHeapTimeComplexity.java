
package org.drip.graph.asymptote;

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
 * <i>StrictFibonacciHeapTimeComplexity</i> maintains the Asymptotic Behavior Specifications of a
 * 	Strict-Fibonacci Heap's Operations. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Brodal, G. S. (1996): Priority Queue on Parallel Machines <i>Scandinavian Workshop on Algorithm
 *  			Theory � SWAT �96</i> 416-427
 *  	</li>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms
 *  			3<sup>rd</sup> Edition</i> <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		Sanders, P., K. Mehlhorn, M. Dietzfelbinger, and R. Dementiev (2019): <i>Sequential and Parallel
 *  			Algorithms and Data Structures � A Basic Toolbox</i> <b>Springer</b>
 *  	</li>
 *  	<li>
 *  		Sundell, H., and P. Tsigas (2005): Fast and Lock-free Concurrent Priority Queues for
 *  			Multi-threaded Systems <i>Journal of Parallel and Distributed Computing</i> <b>65 (5)</b>
 *  			609-627
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Priority Queue https://en.wikipedia.org/wiki/Priority_queue
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/asymptote/README.md">Big O Algorithm Asymptotic Analysis</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class StrictFibonacciHeapTimeComplexity
	extends org.drip.graph.asymptote.AlgorithmTimeComplexity
{

	/**
	 * Build the Algorithm Time Complexity for a Strict Fibonacci Heap
	 * 
	 * @return The Algorithm Time Complexity for a Strict Fibonacci Heap
	 */

	public static final StrictFibonacciHeapTimeComplexity Standard()
	{
		org.drip.graph.asymptote.StrictFibonacciHeapTimeComplexity strictFibonacciHeapTimeComplexity =
			new org.drip.graph.asymptote.StrictFibonacciHeapTimeComplexity();

		try
		{
			if (!strictFibonacciHeapTimeComplexity.addOperationTimeComplexity (
					"find-min",
					new org.drip.graph.asymptote.OperationTimeComplexity (
						null,
						org.drip.graph.asymptote.BigOAsymptoteSpec.Unamortized (
							org.drip.graph.asymptote.AlgorithmTimeComplexity.ConstantTime(),
							org.drip.graph.asymptote.BigOAsymptoteType.BIG_THETA,
							org.drip.graph.asymptote.BigOAsymptoteForm.CONSTANT
						),
						null,
						null
					)
				)
			)
			{
				return null;
			}

			if (!strictFibonacciHeapTimeComplexity.addOperationTimeComplexity (
					"delete-min",
					new org.drip.graph.asymptote.OperationTimeComplexity (
						org.drip.graph.asymptote.BigOAsymptoteSpec.Unamortized (
							org.drip.graph.asymptote.AlgorithmTimeComplexity.LogarithmicTime(),
							org.drip.graph.asymptote.BigOAsymptoteType.BIG_O,
							org.drip.graph.asymptote.BigOAsymptoteForm.LOG_N
						),
						null,
						null,
						null
					)
				)
			)
			{
				return null;
			}

			if (!strictFibonacciHeapTimeComplexity.addOperationTimeComplexity (
					"insert",
					new org.drip.graph.asymptote.OperationTimeComplexity (
						null,
						org.drip.graph.asymptote.BigOAsymptoteSpec.Unamortized (
							org.drip.graph.asymptote.AlgorithmTimeComplexity.ConstantTime(),
							org.drip.graph.asymptote.BigOAsymptoteType.BIG_THETA,
							org.drip.graph.asymptote.BigOAsymptoteForm.CONSTANT
						),
						null,
						null
					)
				)
			)
			{
				return null;
			}

			if (!strictFibonacciHeapTimeComplexity.addOperationTimeComplexity (
					"decrease-key",
					new org.drip.graph.asymptote.OperationTimeComplexity (
						null,
						org.drip.graph.asymptote.BigOAsymptoteSpec.Unamortized (
							org.drip.graph.asymptote.AlgorithmTimeComplexity.ConstantTime(),
							org.drip.graph.asymptote.BigOAsymptoteType.BIG_THETA,
							org.drip.graph.asymptote.BigOAsymptoteForm.CONSTANT
						),
						null,
						null
					)
				)
			)
			{
				return null;
			}

			if (!strictFibonacciHeapTimeComplexity.addOperationTimeComplexity (
					"meld",
					new org.drip.graph.asymptote.OperationTimeComplexity (
						null,
						org.drip.graph.asymptote.BigOAsymptoteSpec.Unamortized (
							org.drip.graph.asymptote.AlgorithmTimeComplexity.ConstantTime(),
							org.drip.graph.asymptote.BigOAsymptoteType.BIG_THETA,
							org.drip.graph.asymptote.BigOAsymptoteForm.CONSTANT
						),
						null,
						null
					)
				)
			)
			{
				return null;
			}

			return strictFibonacciHeapTimeComplexity;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
