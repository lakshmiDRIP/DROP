
package org.drip.graph.core;

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
 * <i>Forest</i> holds a Map of Trees indexed by the Starting Vertex Names. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Bollobas, B. (1998): <i>Modern Graph Theory</i> <b>Springer</b>
 *  	</li>
 *  	<li>
 *  		Eppstein, D. (1999): Spanning Trees and Spanners
 *  			https://www.ics.uci.edu/~eppstein/pubs/Epp-TR-96-16.pdf
 *  	</li>
 *  	<li>
 *  		Gross, J. L., and J. Yellen (2005): <i>Graph Theory and its Applications</i> <b>Springer</b>
 *  	</li>
 *  	<li>
 *  		Kocay, W., and D. L. Kreher (2004): <i>Graphs, Algorithms, and Optimizations</i> <b>CRC Press</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Spanning Tree https://en.wikipedia.org/wiki/Spanning_tree
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/core/README.md">Vertexes, Edges, Trees, and Graphs</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Forest<V>
{
	private java.util.Set<java.lang.String> _vertexSet = null;
	private java.util.Map<java.lang.String, org.drip.graph.core.Tree<?>> _treeMap = null;
	private java.util.Map<java.lang.String, java.lang.String> _containingTreeNameMap = null;

	/**
	 * Forest Constructor
	 */

	public Forest()
	{
		_vertexSet = new java.util.HashSet<java.lang.String>();

		_treeMap = new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.graph.core.Tree<?>>();

		_containingTreeNameMap = new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.String>();
	}

	/**
	 * Retrieve the Map of Trees in the Forest
	 * 
	 * @return Map of Trees in the Forest
	 */

	public java.util.Map<java.lang.String, org.drip.graph.core.Tree<?>> treeMap()
	{
		return _treeMap;
	}

	/**
	 * Retrieve the Map of Containing Tree Names
	 * 
	 * @return Map of Containing Tree Names
	 */

	public java.util.Map<java.lang.String, java.lang.String> containingTreeNameMap()
	{
		return _containingTreeNameMap;
	}

	/**
	 * Retrieve the Set of Vertexes
	 * 
	 * @return The Set of Vertexes
	 */

	public java.util.Set<java.lang.String> vertexSet()
	{
		return _vertexSet;
	}

	/**
	 * Create and add a Unit Vertex Tree into the Forest
	 * 
	 * @param vertexName Vertex Name
	 * @param graph Parent Graph
	 * 
	 * @return TRUE - The Unit Vertex Tree successfully added into the Forest
	 */

	public boolean unitVertexTree (
		final java.lang.String vertexName,
		final org.drip.graph.core.Directed<?> graph)
	{
		if (null == vertexName || vertexName.isEmpty() ||
			null == graph)
		{
			return false;
		}

		org.drip.graph.core.Tree<V> tree = new org.drip.graph.core.Tree<V>();

		if (!tree.addStandaloneVertex (
			vertexName
		))
		{
			return true;
		}

		_treeMap.put (
			vertexName,
			tree
		);

		_containingTreeNameMap.put (
			vertexName,
			vertexName
		);

		return true;
	}

	/**
	 * Add a Named Tree to the Forest
	 *  
	 * @param treeName The Tree Name
	 * @param tree The Tree
	 * @param graph The Graph
	 * 
	 * @return TRUE - The Keyed Tree successfully added to the Forest
	 */

	public boolean addTree (
		final java.lang.String treeName,
		final org.drip.graph.core.Tree<?> tree,
		final org.drip.graph.core.Directed<?> graph)
	{
		if (null == treeName || _treeMap.containsKey (
				treeName
			) || null == tree
			|| null == graph
		)
		{
			return false;
		}

		_treeMap.put (
			treeName,
			tree
		);

		java.util.Set<java.lang.String> treeVertexSet = tree.vertexNameSet();

		_vertexSet.addAll (
			treeVertexSet
		);

		for (java.lang.String vertexName : treeVertexSet)
		{
			_containingTreeNameMap.put (
				vertexName,
				treeName
			);
		}

		return true;
	}

	/**
	 * Retrieve the Set of the Tree Names
	 * 
	 * @return The Set of the Tree Names
	 */

	public java.util.Set<java.lang.String> treeNameSet()
	{
		return _treeMap.keySet();
	}

	/**
	 * Indicate if the Vertex is Contained in the Forest
	 * 
	 * @param vertexName The Vertex Name
	 * 
	 * @return TRUE - The Vertex is Contained in the Forest
	 */

	public boolean containsVertex (
		final java.lang.String vertexName)
	{
		return null != vertexName && _containingTreeNameMap.containsKey (
			vertexName
		);
	}

	/**
	 * Retrieve the Tree that contains the specified Vertex
	 * 
	 * @param vertexName The Vertex Name
	 * 
	 * @return Tree that contains the specified Vertex
	 */

	public org.drip.graph.core.Tree<?> containingTree (
		final java.lang.String vertexName)
	{
		return containsVertex (
			vertexName
		) ? _treeMap.get (
			_containingTreeNameMap.get (
				vertexName
			)
		) : null;
	}

	/**
	 * Conditionally Merge the Specified Source and Destination Trees of the Edge
	 * 
	 * @param edge The Edge
	 * @param graph The Graph
	 * 
	 * @return TRUE - The Source and the Destination Trees successfully conditionally merged
	 */

	public boolean conditionalMerge (
		final org.drip.graph.core.Edge edge,
		final org.drip.graph.core.Directed<?> graph)
	{
		if (null == edge ||
			null == graph)
		{
			return false;
		}

		java.lang.String sourceVertexName = edge.sourceVertexName();

		java.lang.String destinationVertexName = edge.destinationVertexName();

		if (!_containingTreeNameMap.containsKey (
				sourceVertexName
			) || !_containingTreeNameMap.containsKey (
				destinationVertexName
			)
		)
		{
			return false;
		}

		java.lang.String sourceTreeName = _containingTreeNameMap.get (
			sourceVertexName
		);

		java.lang.String destinationTreeName = _containingTreeNameMap.get (
			destinationVertexName
		);

		if (sourceTreeName.equalsIgnoreCase (
			destinationTreeName
		))
		{
			return true;
		}

		org.drip.graph.core.Tree<?> destinationTree = _treeMap.get (
			destinationTreeName
		);

		java.util.Set<java.lang.String> destinationTreeVertexSet = destinationTree.vertexNameSet();

		for (java.lang.String vertexName : destinationTreeVertexSet)
		{
			_containingTreeNameMap.put (
				vertexName,
				sourceTreeName
			);
		}

		if (!_treeMap.get (
			sourceTreeName
		).absorbTreeAndEdge (
			destinationTree,
			edge
		))
		{
			return false;
		}

		_treeMap.remove (
			destinationTreeName
		);

		return true;
	}

	/**
	 * Retrieve the Length of the Forest
	 * 
	 * @return Length of the Forest
	 */

	public double length()
	{
		double length = 0.;

		for (org.drip.graph.core.Tree<?> tree : _treeMap.values())
		{
			length = length + tree.length();
		}

		return length;
	}
}
