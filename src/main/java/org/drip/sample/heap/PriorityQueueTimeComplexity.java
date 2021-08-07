
package org.drip.sample.heap;

import java.util.Map;

import org.drip.graph.asymptote.AlgorithmTimeComplexity;
import org.drip.graph.asymptote.BigOAsymptoteSpec;
import org.drip.graph.asymptote.BinaryHeapTimeComplexity;
import org.drip.graph.asymptote.BinomialHeapTimeComplexity;
import org.drip.graph.asymptote.BrodalHeapTimeComplexity;
import org.drip.graph.asymptote.FibonacciHeapTimeComplexity;
import org.drip.graph.asymptote.LeftistHeapTimeComplexity;
import org.drip.graph.asymptote.OperationTimeComplexity;
import org.drip.graph.asymptote.PairingHeapTimeComplexity;
import org.drip.graph.asymptote.RankPairingHeapTimeComplexity;
import org.drip.graph.asymptote.StrictFibonacciHeapTimeComplexity;
import org.drip.graph.asymptote.TwoThreeHeapTimeComplexity;
import org.drip.service.env.EnvManager;

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
 * <i>PriorityQueueTimeComplexity</i> illustrates the Asymptotic Estimates of the Priority Queue Time
 * 	Complexity for Heap Based Implementations. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Brodal, G. S. (1996): Priority Queue on Parallel Machines <i>Scandinavian Workshop on Algorithm
 *  			Theory – SWAT ’96</i> 416-427
 *  	</li>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms
 *  			3<sup>rd</sup> Edition</i> <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		Sanders, P., K. Mehlhorn, M. Dietzfelbinger, and R. Dementiev (2019): <i>Sequential and Parallel
 *  			Algorithms and Data Structures – A Basic Toolbox</i> <b>Springer</b>
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/heap/README.md">Priority Queue and Heap Algorithms</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PriorityQueueTimeComplexity
{

	private static final String BigOAsymptoteComplexity (
		final BigOAsymptoteSpec bigOAsymptoteSpec)
		throws Exception
	{
		return null == bigOAsymptoteSpec ? "{ }" : "{" +
			bigOAsymptoteSpec.type() + " => " +
			bigOAsymptoteSpec.form() +
		"}";
	}

	private static final void AsymptoteTimeComplexity (
		final String header,
		final AlgorithmTimeComplexity algorithmTimeComplexity)
		throws Exception
	{
		System.out.println (
			"\t|----------------------------------------------------------------------------------"
		);

		System.out.println (
			"\t|  " + header
		);

		System.out.println (
			"\t|----------------------------------------------------------------------------------"
		);


		for (Map.Entry<String, OperationTimeComplexity> operationTimeComplexityEntry :
			algorithmTimeComplexity.operationTimeComplexityMap().entrySet())
		{
			OperationTimeComplexity operationTimeComplexity = operationTimeComplexityEntry.getValue();

			System.out.println (
				"\t| " + operationTimeComplexityEntry.getKey() + " => " +
				BigOAsymptoteComplexity (
					operationTimeComplexity.bigOSpec()
				) + " | " +	BigOAsymptoteComplexity (
					operationTimeComplexity.bigThetaSpec()
				) + " | " + BigOAsymptoteComplexity (
					operationTimeComplexity.bigOmegaSpec()
				) + " | " + BigOAsymptoteComplexity (
					operationTimeComplexity.smallOSpec()
				)
			);
		}

		System.out.println (
			"\t|----------------------------------------------------------------------------------"
		);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		AsymptoteTimeComplexity (
			"BINARY HEAP TIME COMPLEXITY",
			BinaryHeapTimeComplexity.Standard()
		);

		System.out.println();

		AsymptoteTimeComplexity (
			"LEFTIST HEAP TIME COMPLEXITY",
			LeftistHeapTimeComplexity.Standard()
		);

		System.out.println();

		AsymptoteTimeComplexity (
			"BINOMIAL HEAP TIME COMPLEXITY",
			BinomialHeapTimeComplexity.Standard()
		);

		System.out.println();

		AsymptoteTimeComplexity (
			"FIBONACCI HEAP TIME COMPLEXITY",
			FibonacciHeapTimeComplexity.Standard()
		);

		System.out.println();

		AsymptoteTimeComplexity (
			"PAIRING HEAP TIME COMPLEXITY",
			PairingHeapTimeComplexity.Standard()
		);

		System.out.println();

		AsymptoteTimeComplexity (
			"BRODAL HEAP TIME COMPLEXITY",
			BrodalHeapTimeComplexity.Standard()
		);

		System.out.println();

		AsymptoteTimeComplexity (
			"RANK PAIRING HEAP TIME COMPLEXITY",
			RankPairingHeapTimeComplexity.Standard()
		);

		System.out.println();

		AsymptoteTimeComplexity (
			"STRICT FIBONACCI HEAP TIME COMPLEXITY",
			StrictFibonacciHeapTimeComplexity.Standard()
		);

		System.out.println();

		AsymptoteTimeComplexity (
			"2-3 HEAP TIME COMPLEXITY",
			TwoThreeHeapTimeComplexity.Standard()
		);

		EnvManager.TerminateEnv();
	}
}
