
package org.drip.graph.mst;

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
 * <i>CompleteRandomGraphEnsemble</i> implements the Ensemble of Complete Random Graphs. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Bader, D. A., and G. Cong (2006): Fast Shared Memory Algorithms for computing the Minimum
 *  			Spanning Forests of Sparse Graphs <i>Journal of Parallel and Distributed Computing</i>
 *  			<b>66 (11)</b> 1366-1378
 *  	</li>
 *  	<li>
 *  		Chazelle, B. (2000): A Minimum Spanning Tree Algorithm with Inverse-Ackerman Type Complexity
 *  			<i> Journal of the Association for Computing Machinery</i> <b>47 (6)</b> 1028-1047
 *  	</li>
 *  	<li>
 *  		Karger, D. R., P. N. Klein, and R. E. Tarjan (1995): A Randomized Linear-Time Algorithm to find
 *  			Minimum Spanning Trees <i> Journal of the Association for Computing Machinery</i> <b>42
 *  			(2)</b> 321-328
 *  	</li>
 *  	<li>
 *  		Pettie, S., and V. Ramachandran (2002): An Optimal Minimum Spanning Tree <i>Algorithm Journal of
 *  			the ACM</i> <b>49 (1)</b> 16-34
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Minimum Spanning Tree https://en.wikipedia.org/wiki/Minimum_spanning_tree
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/mst/README.md">Agnostic Minimum Spanning Tree Properties</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CompleteRandomGraphEnsemble
{
	private org.drip.graph.mst.CompleteRandomGraph _completeRandomGraph = null;
	private org.drip.graph.treebuilder.OptimalSpanningForestGenerator _minimumSpanningForestGenerator = null;

	/**
	 * Construct the Prim based CompleteRandomGraphEnsemble
	 * 
	 * @param completeRandomGraph The Complete Random Graph
	 * 
	 * @return The CompleteRandomGraphEnsemble Instance
	 */

	public static final CompleteRandomGraphEnsemble Prim (
		final org.drip.graph.mst.CompleteRandomGraph completeRandomGraph)
	{
		try
		{
			return new CompleteRandomGraphEnsemble (
				completeRandomGraph,
				new org.drip.graph.mstgreedy.PrimGenerator (
					completeRandomGraph,
					false
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Kruskal based CompleteRandomGraphEnsemble
	 * 
	 * @param completeRandomGraph The Complete Random Graph
	 * 
	 * @return The CompleteRandomGraphEnsemble Instance
	 */

	public static final CompleteRandomGraphEnsemble Kruskal (
		final org.drip.graph.mst.CompleteRandomGraph completeRandomGraph)
	{
		try
		{
			return new CompleteRandomGraphEnsemble (
				completeRandomGraph,
				new org.drip.graph.mstgreedy.KruskalGenerator (
					completeRandomGraph,
					false
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Reverse-Delete based CompleteRandomGraphEnsemble
	 * 
	 * @param completeRandomGraph The Complete Random Graph
	 * 
	 * @return The CompleteRandomGraphEnsemble Instance
	 */

	public static final CompleteRandomGraphEnsemble ReverseDelete (
		final org.drip.graph.mst.CompleteRandomGraph completeRandomGraph)
	{
		try
		{
			return new CompleteRandomGraphEnsemble (
				completeRandomGraph,
				new org.drip.graph.mstgreedy.ReverseDeleteGenerator (
					completeRandomGraph,
					false
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Boruvka based CompleteRandomGraphEnsemble
	 * 
	 * @param completeRandomGraph The Complete Random Graph
	 * 
	 * @return The CompleteRandomGraphEnsemble Instance
	 */

	public static final CompleteRandomGraphEnsemble Boruvka (
		final org.drip.graph.mst.CompleteRandomGraph completeRandomGraph)
	{
		try
		{
			return new CompleteRandomGraphEnsemble (
				completeRandomGraph,
				new org.drip.graph.mstgreedy.BoruvkaGenerator (
					completeRandomGraph,
					false
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CompleteRandomGraphEnsemble Constructor
	 * 
	 * @param completeRandomGraph The Underlying Complete Random Graph
	 * @param minimumSpanningForestGenerator The Minimum Spanning Forest Generator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CompleteRandomGraphEnsemble (
		final org.drip.graph.mst.CompleteRandomGraph completeRandomGraph,
		final org.drip.graph.treebuilder.OptimalSpanningForestGenerator minimumSpanningForestGenerator)
		throws java.lang.Exception
	{
		if (null == (_completeRandomGraph = completeRandomGraph) ||
			null == (_minimumSpanningForestGenerator = minimumSpanningForestGenerator)
		)
		{
			throw new java.lang.Exception (
				"CompleteRandomGraphEnsemble Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Underlying Complete Random Graph
	 * 
	 * @return The Underlying Complete Random Graph
	 */

	public org.drip.graph.mst.CompleteRandomGraph completeRandomGraph()
	{
		return _completeRandomGraph;
	}

	/**
	 * Retrieve the Minimum Spanning Forest Generator
	 * 
	 * @return The Minimum Spanning Forest Generator
	 */

	public org.drip.graph.treebuilder.OptimalSpanningForestGenerator minimumSpanningForestGenerator()
	{
		return _minimumSpanningForestGenerator;
	}

	/**
	 * Compute the Length of the Minimum Spanning Forest
	 * 
	 * @return Length of the Minimum Spanning Forest
	 * 
	 * @throws java.lang.Exception Thrown if the Length of the Minimum Spanning Forest cannot be calculated
	 */

	public double length()
		throws java.lang.Exception
	{
		org.drip.graph.core.Forest minimumSpanningForest =
			_minimumSpanningForestGenerator.optimalSpanningForest();

		if (null == minimumSpanningForest)
		{
			throw new java.lang.Exception (
				"CompleteRandomGraphEnsemble::length => Cannot compute the Minimum Spanning Forest"
			);
		}

		return minimumSpanningForest.length();
	}
}
