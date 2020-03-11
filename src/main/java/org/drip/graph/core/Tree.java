
package org.drip.graph.core;

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
 * <i>Tree</i> holds the Vertexes and the Edges associated with a Tree. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Wikipedia (2019a): Kruskal's Algorithm https://en.wikipedia.org/wiki/Kruskal%27s_algorithm
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019b): Prim's Algorithm https://en.wikipedia.org/wiki/Prim%27s_algorithm
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020a): Breadth-First Search https://en.wikipedia.org/wiki/Breadth-first_search
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020b): Depth-First Search https://en.wikipedia.org/wiki/Depth-first_search
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/core/README.md">Vertexes, Edges, Trees, and Graphs</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Tree
{
	private java.util.Set<java.lang.String> _vertexSet = null;
	private java.util.Map<java.lang.String, org.drip.graph.core.Edge> _edgeMap = null;

	/**
	 * Tree Constructor
	 */

	public Tree()
	{
		_vertexSet = new java.util.HashSet<java.lang.String>();

		_edgeMap =
			new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.graph.core.Edge>();
	}

	/**
	 * Retrieve the Edge Map
	 * 
	 * @return The Edge Map
	 */

	public java.util.Map<java.lang.String, org.drip.graph.core.Edge> edgeMap()
	{
		return _edgeMap;
	}

	/**
	 * Retrieve the Vertex Set
	 * 
	 * @return The Vertex Set
	 */

	public java.util.Set<java.lang.String> vertexSet()
	{
		return _vertexSet;
	}

	/**
	 * Add a Stand-alone Vertex to the Tree
	 *  
	 * @param vertexName The Stand-alone Vertex Name
	 * 
	 * @return TRUE - The Stand-alone Vertex successfully added to the Tree
	 */

	public boolean addStandaloneVertex (
		final java.lang.String vertexName)
	{
		if (null == vertexName || vertexName.isEmpty() ||
			!_vertexSet.isEmpty() ||
			!_edgeMap.isEmpty()
		)
		{
			return false;
		}

		_vertexSet.add (
			vertexName
		);

		return true;
	}

	/**
	 * Add an Edge to the Tree
	 * 
	 * @param edge The Edge
	 * 
	 * @return TRUE - The Edge successfully added
	 */

	public boolean addEdge (
		final org.drip.graph.core.Edge edge)
	{
		if (null == edge)
		{
			return false;
		}

		java.lang.String sourceVertexName = edge.sourceVertexName();

		java.lang.String destinationVertexName = edge.destinationVertexName();

		_edgeMap.put (
			sourceVertexName + "_" + destinationVertexName,
			edge
		);

		_edgeMap.put (
			destinationVertexName + "_" + sourceVertexName,
			edge
		);

		_vertexSet.add (
			sourceVertexName
		);

		_vertexSet.add (
			destinationVertexName
		);

		return true;
	}

	/**
	 * Indicate if the Vertex is Contained in the Tree
	 * 
	 * @param vertexName The Vertex Name
	 * 
	 * @return TRUE - The Vertex is Contained in the Tree
	 */

	public boolean containsVertex (
		final java.lang.String vertexName)
	{
		return null != vertexName && _vertexSet.contains (
			vertexName
		);
	}

	/**
	 * Absorb the Specified Tree and Edge
	 * 
	 * @param tree The Tree
	 * @param edge The Edge
	 * 
	 * @return TRUE - The Tree and Edge successfully absorbed
	 */

	public boolean absorbTreeAndEdge (
		final org.drip.graph.core.Tree tree,
		final org.drip.graph.core.Edge edge)
	{
		if (null == tree || !addEdge (
			edge
		))
		{
			return false;
		}

		for (org.drip.graph.core.Edge treeEdge : tree.edgeMap().values())
		{
			if (!addEdge (
				treeEdge
			))
			{
				System.out.println ("Cannot add edge");
				return false;
			}
		}

		return true;
	}

	/**
	 * Retrieve the Count of the Tree Edges
	 * 
	 * @return The Count of the Tree Edges
	 */

	public int edgeCount()
	{
		return _edgeMap.size();
	}

	/**
	 * Retrieve the Count of the Tree Vertexes
	 * 
	 * @return The Count of the Tree Vertexes
	 */

	public int vertexCount()
	{
		return _vertexSet.size();
	}

	/**
	 * Retrieve the Length of the Tree
	 * 
	 * @return Length of the Tree
	 */

	public double length()
	{
		double length = 0.;

		for (org.drip.graph.core.Edge edge : _edgeMap.values())
		{
			length = length + edge.distance();
		}

		return length;
	}

	/**
	 * Indicate if the Edge forms a Cycle with the Tree
	 * 
	 * @param edge The Edge
	 * 
	 * @return TRUE - T Edge forms a Cycle with the Tree
	 */

	public boolean isCycle (
		final org.drip.graph.core.Edge edge)
	{
		return null == edge ? false :_vertexSet.contains (
			edge.sourceVertexName()
		) && _vertexSet.contains (
			edge.destinationVertexName()
		);
	}

	/**
	 * Indicate if the Specified Edge matches with any Edges in the Tree
	 * 
	 * @param edgeOther The "Other" Edge
	 * 
	 * @return TRUE - The Specified Edge matches with any Edges in the Tree
	 */

	public boolean containsEdge (
		final org.drip.graph.core.Edge edgeOther)
	{
		if (null == edgeOther)
		{
			return false;
		}

		for (org.drip.graph.core.Edge edge : _edgeMap.values())
		{
			if (edge.compareWith (
				edgeOther
			))
			{
				return true;
			}
		}

		return false;
	}
}
