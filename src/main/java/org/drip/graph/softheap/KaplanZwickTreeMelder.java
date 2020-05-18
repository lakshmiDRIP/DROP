
package org.drip.graph.softheap;

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
 * <i>KaplanZwickTreeMelder</i> grows the Melded Tree List. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Chazelle, B. (2000): The Discrepancy Method: Randomness and Complexity
 *  			https://www.cs.princeton.edu/~chazelle/pubs/book.pdf
 *  	</li>
 *  	<li>
 *  		Chazelle, B. (2000): The Soft Heap: An Approximate Priority Queue with Optimal Error Rate
 *  			<i>Journal of the Association for Computing Machinery</i> <b>47 (6)</b> 1012-1027
 *  	</li>
 *  	<li>
 *  		Chazelle, B. (2000): A Minimum Spanning Tree Algorithm with Inverse-Ackerman Type Complexity
 *  			<i>Journal of the Association for Computing Machinery</i> <b>47 (6)</b> 1028-1047
 *  	</li>
 *  	<li>
 *  		Kaplan, H., and U. Zwick (2009): A simpler implementation and analysis of Chazelle's Soft Heaps
 *  			https://epubs.siam.org/doi/abs/10.1137/1.9781611973068.53?mobileUi=0
 *  	</li>
 *  	<li>
 *  		Pettie, S., and V. Ramachandran (2008): Randomized Minimum Spanning Tree Algorithms using
 *  			Exponentially Fewer Random Bits <i>ACM Transactions on Algorithms</i> <b>4 (1)</b> 1-27
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/softheap/README.md">Soft Heap - Approximate Priority Queue</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class KaplanZwickTreeMelder<KEY extends java.lang.Comparable<KEY>, ITEM>
{
	private org.drip.graph.softheap.KaplanZwickTree<KEY, ITEM> _head = null;
	private org.drip.graph.softheap.KaplanZwickTree<KEY, ITEM> _tail = null;

	/**
	 * KaplanZwickTreeMelder Constructor
	 * 
	 * @param mergedTreeList The Merged Tree List
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public KaplanZwickTreeMelder (
		final org.drip.graph.softheap.KaplanZwickTree<KEY, ITEM> mergedTreeList)
		throws java.lang.Exception
	{
		if (null == mergedTreeList)
		{
			throw new java.lang.Exception (
				"KaplanZwickTreeMelder Constructor => Invalid Inputs"
			);
		}

		_tail = (_head = mergedTreeList.rootTree());
	}

	/**
	 * Retrieve the Head of the Melded Tree
	 * 
	 * @return Head of the Melded Tree
	 */

	public org.drip.graph.softheap.KaplanZwickTree<KEY, ITEM> head()
	{
		return _head;
	}

	/**
	 * Retrieve the Tail of the Melded Tree
	 * 
	 * @return Tail of the Melded Tree
	 */

	public org.drip.graph.softheap.KaplanZwickTree<KEY, ITEM> tail()
	{
		return _tail;
	}

	/**
	 * Retrieve the Rank of the Tail
	 * 
	 * @return Rank of the Tail
	 */

	public int tailRank()
	{
		return _tail.rank();
	}

	/**
	 * Grow the Tail Root Node using the Supplied Root Node
	 * 
	 * @param rootNode Root Node
	 * 
	 * @return TRUE - The Tail Root Node grown successfully using the Supplied Root Node
	 */

	public boolean growTail (
		final org.drip.graph.softheap.KaplanZwickBinaryNode<KEY, ITEM> rootNode)
	{
		return _tail.setRoot (
			org.drip.graph.softheap.KaplanZwickBinaryNode.CombineRootNodePair (
				_tail.root(),
				rootNode
			)
		);
	}

	/**
	 * Append to the Tail using the New Tail
	 * 
	 * @param tail The New Tail
	 * 
	 * @return TRUE - The Tail Root Node appended successfully using the Supplied Root Node
	 */

	public boolean appendToTail (
		final org.drip.graph.softheap.KaplanZwickTree<KEY, ITEM> tail)
	{
		if (null == tail ||
			!_tail.setNext (
				tail
			) || !tail.setPrev (
				_tail
			)
		)
		{
			return false;
		}

		_tail = tail;
		return true;
	}
}
