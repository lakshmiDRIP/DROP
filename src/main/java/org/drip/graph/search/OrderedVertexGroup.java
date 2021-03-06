
package org.drip.graph.search;

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
 * <i>OrderedVertexGroup</i> holds the Grouping of the Ordered Search (BFS/DFS) of the Vertexes of a Graph.
 * 	The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Cormen, T., C. E. Leiserson, R. Rivest, and C. Stein (2009): <i>Introduction to Algorithms
 *  			3<sup>rd</sup> Edition</i> <b>MIT Press</b>
 *  	</li>
 *  	<li>
 *  		de Fraysseix, H., O. de Mendez, and P. Rosenstiehl (2006): Tremaux Trees and Planarity
 *  			<i>International Journal of Foundations of Computer Science</i> <b>17 (5)</b> 1017-1030
 *  	</li>
 *  	<li>
 *  		Mehlhorn, K., and P. Sanders (2008): <i>Algorithms and Data Structures: The Basic Tool-box</i>
 *  			<b>Springer</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Breadth-first Search https://en.wikipedia.org/wiki/Breadth-first_search
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Depth-first Search https://en.wikipedia.org/wiki/Depth-first_search
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/search/README.md">BFS, DFS, and Ordered Vertexes</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OrderedVertexGroup
{
	private boolean _containsCycle = false;
	private java.util.Set<java.lang.String> _vertexNameSet = null;
	private java.util.List<java.lang.String> _vertexNameList = null;

	/**
	 * OrderedVertexGroup Constructor
	 */

	public OrderedVertexGroup()
	{
		_vertexNameSet = new java.util.HashSet<java.lang.String>();

		_vertexNameList = new java.util.ArrayList<java.lang.String>();
	}

	/**
	 * Retrieve the Set of available Vertexes
	 * 
	 * @return Set of available Vertexes
	 */

	public java.util.Set<java.lang.String> vertexNameSet()
	{
		return _vertexNameSet;
	}

	/**
	 * Retrieve the List of Visited Vertexes
	 * 
	 * @return List of Visited Vertexes
	 */

	public java.util.List<java.lang.String> vertexNameList()
	{
		return _vertexNameList;
	}

	/**
	 * Indicate if the Ordered Search contains a Cycle
	 * 
	 * @return TRUE - The Ordered Search contains a Cycle
	 */

	public boolean containsCycle()
	{
		return _containsCycle;
	}

	/**
	 * Indicate if the Specified Vertex in Present in the Search
	 * 
	 * @param vertexName The Vertex Name
	 * 
	 * @return TRUE - The Specified Vertex is present in the Search
	 */

	public boolean vertexPresent (
		final java.lang.String vertexName)
	{
		return null != vertexName && !vertexName.isEmpty() && _vertexNameSet.contains (
			vertexName
		);
	}

	/**
	 * Add the specified Vertex to the Search
	 * 
	 * @param vertexName The Vertex Name
	 * 
	 * @return TRUE - The Specified Vertex successfully added to the Search
	 */

	public boolean addVertexName (
		final java.lang.String vertexName)
	{
		if (null == vertexName || vertexName.isEmpty())
		{
			return false;
		}

		if (!_vertexNameSet.contains (
			vertexName
		))
		{
			_vertexNameSet.add (
				vertexName
			);
		}

		_vertexNameList.add (
			vertexName
		);

		return true;
	}

	/**
	 * "Touch" the specified Vertex
	 * 
	 * @param vertexName The Vertex Name
	 * 
	 * @return TRUE - The Specified Vertex successfully "touched"
	 */

	public boolean touchVertex (
		final java.lang.String vertexName)
	{
		if (null == vertexName || vertexName.isEmpty())
		{
			return false;
		}

		_vertexNameList.add (
			vertexName
		);

		_containsCycle = true;
		return true;
	}

	/**
	 * Set to Indicate that the Ordered Search contains a Cycle
	 * 
	 * @return TRUE - The Indicator successfully set
	 */

	public boolean setContainsCycle()
	{
		_containsCycle = true;
		return true;
	}

	/**
	 * Retrieve the Set of Pre-ordered Vertexes
	 * 
	 * @return Set of Pre-ordered Vertexes
	 */

	public java.util.Collection<java.lang.String> preOrder()
	{
		java.util.Set<java.lang.String> vertexNameSet = new java.util.HashSet<java.lang.String>();

		java.util.Map<java.lang.Integer, java.lang.String> orderNameMap =
			new java.util.TreeMap<java.lang.Integer, java.lang.String>();

		int vertexOrder = 1;

		for (java.lang.String vertexName : _vertexNameList)
		{
			if (!vertexNameSet.contains (
				vertexName
			))
			{
				vertexNameSet.add (
					vertexName
				);

				orderNameMap.put (
					vertexOrder++,
					vertexName
				);
			}
		}

		return orderNameMap.values();
	}

	/**
	 * Retrieve the Set of Post-ordered Vertexes
	 * 
	 * @return Set of Post-ordered Vertexes
	 */

	public java.util.Collection<java.lang.String> postOrder()
	{
		java.util.Set<java.lang.String> vertexNameSet = new java.util.HashSet<java.lang.String>();

		java.util.Map<java.lang.Integer, java.lang.String> orderNameMap =
			new java.util.TreeMap<java.lang.Integer, java.lang.String>();

		int vertexCount = _vertexNameList.size();

		int vertexOrder = 1;

		for (int vertexIndex = vertexCount - 1;
			vertexIndex >= 0;
			--vertexIndex)
		{
			java.lang.String vertexName = _vertexNameList.get (
				vertexIndex
			);

			if (!vertexNameSet.contains (
				vertexName
			))
			{
				vertexNameSet.add (
					vertexName
				);

				orderNameMap.put (
					vertexOrder++,
					vertexName
				);
			}
		}

		return orderNameMap.values();
	}

	/**
	 * Retrieve the Set of Reverse Pre-ordered Vertexes
	 * 
	 * @return Set of Reverse Pre-ordered Vertexes
	 */

	public java.util.Collection<java.lang.String> reversePreOrder()
	{
		java.util.Set<java.lang.String> vertexNameSet = new java.util.HashSet<java.lang.String>();

		java.util.Map<java.lang.Integer, java.lang.String> orderNameMap =
			new java.util.TreeMap<java.lang.Integer, java.lang.String>();

		int vertexOrder = 1;

		for (java.lang.String vertexName : _vertexNameList)
		{
			if (!vertexNameSet.contains (
				vertexName
			))
			{
				vertexNameSet.add (
					vertexName
				);

				orderNameMap.put (
					-1 * vertexOrder,
					vertexName
				);

				++vertexOrder;
			}
		}

		return orderNameMap.values();
	}

	/**
	 * Retrieve the Set of Reverse Post-ordered Vertexes
	 * 
	 * @return Set of Reverse Post-ordered Vertexes
	 */

	public java.util.Collection<java.lang.String> reversePostOrder()
	{
		java.util.Set<java.lang.String> vertexNameSet = new java.util.HashSet<java.lang.String>();

		java.util.Map<java.lang.Integer, java.lang.String> orderNameMap =
			new java.util.TreeMap<java.lang.Integer, java.lang.String>();

		int vertexCount = _vertexNameList.size();

		int vertexOrder = 1;

		for (int vertexIndex = vertexCount - 1;
			vertexIndex >= 0;
			--vertexIndex)
		{
			java.lang.String vertexName = _vertexNameList.get (
				vertexIndex
			);

			if (!vertexNameSet.contains (
				vertexName
			))
			{
				vertexNameSet.add (
					vertexName
				);

				orderNameMap.put (
					-1 * vertexOrder,
					vertexName
				);

				++vertexOrder;
			}
		}

		return orderNameMap.values();
	}

	/**
	 * Retrieve the Set of Lexicographically Ordered Vertexes
	 * 
	 * @return Set of Lexicographically Ordered Vertexes
	 */

	public java.util.Collection<java.lang.String> lexicographicalOrdering()
	{
		return preOrder();
	}

	/**
	 * Retrieve the Set of Topologically Ordered Vertexes
	 * 
	 * @return Set of Topologically Ordered Vertexes
	 */

	public java.util.Collection<java.lang.String> topologicalSorting()
	{
		return reversePostOrder();
	}
}
