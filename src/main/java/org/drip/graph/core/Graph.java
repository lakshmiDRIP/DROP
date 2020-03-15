
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
 * <i>Graph</i> implements a Vertex/Edge Topology corresponding to the Graph. The References are:
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
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/core/README.md">Vertexes, Edges, Trees, and Graphs</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Graph
	extends org.drip.graph.core.Network
{
	/**
	 * Graph Constructor
	 */

	public Graph()
	{
		super();
	}

	/**
	 * Add the Specified Graph to the Current
	 * 
	 * @param graph The Specified Graph
	 * 
	 * @return TRUE - The Specified Graph successfully Added
	 */

	public boolean addGraph (
		final org.drip.graph.core.Graph graph)
	{
		if (null == graph)
		{
			return false;
		}

		java.util.Collection<org.drip.graph.core.BidirectionalEdge> edgeCollection =
			graph.edgeMap().values();

		if (null == edgeCollection || 0 == edgeCollection.size())
		{
			return true;
		}

		for (org.drip.graph.core.BidirectionalEdge edge : edgeCollection)
		{
			if (!addBidirectionalEdge (
				edge
			))
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Indicate if the Graph is Connected
	 * 
	 * @return TRUE - The Graph is Connected
	 */

	public boolean isConnected()
	{
		if (_vertexMap.isEmpty())
		{
			return false;
		}

		java.util.Set<java.lang.String> vertexNameSet = _vertexMap.keySet();

		org.drip.graph.core.OrderedSearch orderedSearch =
			new org.drip.graph.core.OrderedSearch();

		if (!bfs (
			initialVertexName(),
			orderedSearch
		))
		{
			return false;
		}

		return orderedSearch.vertexNameSet().size() == vertexNameSet.size();
	}

	/**
	 * Indicate of the Specified Tree spans the Graph
	 * 
	 * @param tree The Tree
	 * 
	 * @return TRUE - The Tree spans the Graph
	 */

	public boolean isTreeSpanning (
		final org.drip.graph.core.Tree tree)
	{
		if (null == tree)
		{
			return false;
		}

		java.util.Set<java.lang.String> graphVertexNameSet = new java.util.HashSet<java.lang.String>();

		for (org.drip.graph.core.Vertex graphVertex : _vertexMap.values())
		{
			graphVertexNameSet.add (
				graphVertex.name()
			);
		}

		for (java.lang.String treeVertexName : tree.vertexMap().keySet())
		{
			if (!graphVertexNameSet.contains (
				treeVertexName
			))
			{
				return false;
			}

			graphVertexNameSet.remove (
				treeVertexName
			);
		}

		return 0 == graphVertexNameSet.size();
	}

	/**
	 * Retrieve the Set of the Fundamental Cycles using the Spanning Tree
	 * 
	 * @param tree Spanning Tree
	 * 
	 * @return Set of the Fundamental Cycles using the Spanning Tree
	 */

	public java.util.Set<org.drip.graph.core.BidirectionalEdge> fundamentalCycleEdgeSet (
		final org.drip.graph.core.Tree tree)
	{
		if (null == tree)
		{
			return null;
		}

		java.util.Set<org.drip.graph.core.BidirectionalEdge> edgeSet =
			new java.util.HashSet<org.drip.graph.core.BidirectionalEdge> (
				_edgeMap.values()
			);

		for (org.drip.graph.core.BidirectionalEdge edge : tree.edgeMap().values())
		{
			if (!containsEdge (
				edge
			))
			{
				return null;
			}

			edgeSet.remove (
				edge
			);
		}

		return edgeSet;
	}

	/**
	 * Retrieve the List of the Leaf Vertex Names
	 * 
	 * @return List of the Leaf Vertex Names
	 */

	public java.util.List<java.lang.String> leafVertexNameList()
	{
		java.util.List<java.lang.String> leafVertexNameList = new java.util.ArrayList<java.lang.String>();

		for (java.util.Map.Entry<java.lang.String, org.drip.graph.core.Vertex> vertexMapEntry :
			_vertexMap.entrySet())
		{
			java.util.Map<java.lang.Double, org.drip.graph.core.BidirectionalEdge> adjacencyMap =
				vertexMapEntry.getValue().adjacencyMap();

			if (null == adjacencyMap || 1 == adjacencyMap.size())
			{
				leafVertexNameList.add (
					vertexMapEntry.getKey()
				);
			}
		}

		return leafVertexNameList;
	}

	/**
	 * Indicate if the Graph contains a Cycle
	 * 
	 * @return TRUE - The Graph contains a Cycle
	 */

	public boolean containsCycle()
	{
		java.util.List<java.lang.String> leafVertexNameList = leafVertexNameList();

		if (null == leafVertexNameList || 0 == leafVertexNameList.size())
		{
			return true;
		}

		for (java.lang.String leafVertexName : leafVertexNameList)
		{
			org.drip.graph.core.OrderedSearch orderedSearch =
				new org.drip.graph.core.OrderedSearch();

			bfs (
				leafVertexName,
				orderedSearch
			);

			if (orderedSearch.containsCycle())
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Indicate if the Graph is Cyclical
	 * 
	 * @return TRUE - The Graph is Cyclical
	 */

	public boolean isCyclical()
	{
		java.util.List<java.lang.String> leafVertexNameList = leafVertexNameList();

		return null == leafVertexNameList || 0 == leafVertexNameList.size() ? true : false;
	}

	/**
	 * Indicate if the Graph is a Tree
	 * 
	 * @return TRUE - The Graph is a Tree
	 */

	public boolean isTree()
	{
		return isConnected() && !containsCycle();
	}

	/**
	 * Indicate if the Graph is Complete
	 * 
	 * @return TRUE - The Graph is Complete
	 */

	public boolean isComplete()
	{
		int n = _vertexMap.size() - 1;

		for (java.util.Map.Entry<java.lang.String, org.drip.graph.core.Vertex> vertexMapEntry :
			_vertexMap.entrySet())
		{
			java.util.Map<java.lang.Double, org.drip.graph.core.BidirectionalEdge> adjacencyMap =
				vertexMapEntry.getValue().adjacencyMap();

			if (null == adjacencyMap || n != adjacencyMap.size())
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Indicate if the Graph is Dense
	 * 
	 * @return TRUE - The Graph is Dense
	 */

	public boolean isDense()
	{
		int vertexCount = _vertexMap.size();

		return 0.5 * _edgeMap.size() >= vertexCount * java.lang.Math.log (
			java.lang.Math.log (
				java.lang.Math.log (
					vertexCount
				)
			)
		);
	}

	/**
	 * Retrieve the Map of the Vertex Adjacency Degree
	 * 
	 * @return Map of the Vertex Adjacency Degree
	 */

	public java.util.Map<java.lang.String, java.lang.Integer> vertexDegreeMap()
	{
		java.util.Map<java.lang.String, java.lang.Integer> vertexDegreeMap =
			new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();

		for (java.util.Map.Entry<java.lang.String, org.drip.graph.core.Vertex> vertexMapEntry :
			_vertexMap.entrySet())
		{
			java.util.Map<java.lang.Double, org.drip.graph.core.BidirectionalEdge> adjacencyMap =
				vertexMapEntry.getValue().adjacencyMap();

			vertexDegreeMap.put (
				vertexMapEntry.getKey(),
				null == adjacencyMap ? 0 : adjacencyMap.size()
			);
		}

		return vertexDegreeMap;
	}

	/**
	 * Indicate if the specified Vertexes are Adjacent
	 * 
	 * @param vertexName1 Vertex #1
	 * @param vertexName2 Vertex #2
	 * 
	 * @return TRUE - The Vertexes are Adjacent
	 */

	public boolean areVertexesAdjacent (
		final java.lang.String vertexName1,
		final java.lang.String vertexName2)
	{
		if (null == vertexName1 || vertexName1.isEmpty() ||
			null == vertexName2 || vertexName2.isEmpty())
		{
			return false;
		}

		return _edgeMap.containsKey (
			vertexName1 + "_" + vertexName2
		);
	}

	/**
	 * Retrieve the Graph Type
	 * 
	 * @return The Graph Type
	 */

	public int type()
	{
		if (isTree())
		{
			return org.drip.graph.core.GraphType.TREE;
		}

		if (isCyclical())
		{
			return org.drip.graph.core.GraphType.CYCLICAL;
		}

		if (isComplete())
		{
			return org.drip.graph.core.GraphType.COMPLETE;
		}

		return org.drip.graph.core.GraphType.UNSPECIFIED;
	}

	/**
	 * Retrieve the Count of the Spanning Trees Using Kirchoff's Matrix-Tree Theorem
	 * 
	 * @return Count of the Spanning Trees Using Kirchoff's Matrix-Tree Theorem
	 */

	public double kirchoffSpanningTreeCount()
	{
		java.util.Map<java.lang.String, java.lang.Integer> vertexDegreeMap = vertexDegreeMap();

		java.util.Set<java.lang.String> vertexNameSetI = vertexDegreeMap.keySet();

		java.util.Set<java.lang.String> vertexNameSetJ = vertexDegreeMap.keySet();

		int vertexCount = vertexDegreeMap.size();

		double[][] laplacianMatrix = new double[vertexCount][vertexCount];
		double[][] laplacianMatrixCoFactor = new double[vertexCount - 1][vertexCount - 1];

		for (int vertexIndexI = 0;
			vertexIndexI < vertexCount;
			++vertexIndexI)
		{
			for (int vertexIndexJ = 0;
				vertexIndexJ < vertexCount;
				++vertexIndexJ)
			{
				laplacianMatrix[vertexIndexI][vertexIndexJ] = 0.;
			}
		}

		int vertexIndexI = 0;

		for (java.lang.String vertexNameI : vertexNameSetI)
		{
			int vertexIndexJ = 0;

			for (java.lang.String vertexNameJ : vertexNameSetJ)
			{
				if (vertexNameI.equalsIgnoreCase (
					vertexNameJ
				))
				{
					laplacianMatrix[vertexIndexI][vertexIndexJ] = vertexDegreeMap.get (
						vertexNameI
					);
				}
				else
				{
					laplacianMatrix[vertexIndexI][vertexIndexJ] = areVertexesAdjacent (
						vertexNameI,
						vertexNameJ
					) ? -1. : 0.;
				}

				++vertexIndexJ;
			}

			++vertexIndexI;
		}

		for (vertexIndexI = 0;
			vertexIndexI < vertexCount - 1;
			++vertexIndexI)
		{
			for (int vertexIndexJ = 0;
				vertexIndexJ < vertexCount - 1;
				++vertexIndexJ)
			{
				laplacianMatrixCoFactor[vertexIndexI][vertexIndexJ] =
					laplacianMatrix[vertexIndexI][vertexIndexJ];
			}
		}

		try
		{
			return new org.drip.function.matrix.Square (
				laplacianMatrixCoFactor
			).determinant();
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * Retrieve the Count of the Spanning Trees
	 * 
	 * @return Count of the Spanning Trees
	 */

	public double spanningTreeCount()
	{
		if (!isConnected())
		{
			return 0;
		}

		int type = type();

		if (type == org.drip.graph.core.GraphType.TREE)
		{
			return 1;
		}

		if (type == org.drip.graph.core.GraphType.CYCLICAL)
		{
			return _vertexMap.size();
		}

		if (type == org.drip.graph.core.GraphType.COMPLETE)
		{
			int n = _vertexMap.size();

			return java.lang.Math.pow (
				n,
				n - 2
			);
		}

		return kirchoffSpanningTreeCount();
	}
}
