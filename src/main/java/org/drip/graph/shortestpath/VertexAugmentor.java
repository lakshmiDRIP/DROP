
package org.drip.graph.shortestpath;

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
 * <i>VertexAugmentor</i> augments and maintains the set of Path Vertexes. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Dijkstra, E. W. (1959): A Note on Two Problems in Connection with Graphs <i>Numerische
 *  			Mathematik</i> <b>1</b> 269-271
 *  	</li>
 *  	<li>
 *  		Felner, A. (2011): Position Paper: Dijkstra’s Algorithm versus Uniform Cost Search or a Case
 *  			against Dijkstra’s Algorithm <i>Proceedings of the 4<sup>th</sup> International Symposium on
 *  			Combinatorial Search</i> 47-51
 *  	</li>
 *  	<li>
 *  		Mehlhorn, K. W., and P. Sanders (2008): <i>Algorithms and Data Structures: The Basic Toolbox</i>
 *  			<b>Springer</b>
 *  	</li>
 *  	<li>
 *  		Russell, S., and P. Norvig (2009): <i>Artificial Intelligence: A Modern Approach 3<sup>rd</sup>
 *  			Edition</i> <b>Prentice Hall</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Dijkstra's Algorithm https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/shortestpath/README.md">Shortest Path Generation Algorithm Family</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class VertexAugmentor
{
	private boolean _shortestPath = false;
	private java.lang.String _sourceVertexName = "";
	private java.util.Map<java.lang.String, org.drip.graph.shortestpath.AugmentedVertex> _augmentedVertexMap
		= null;

	private final boolean compareIntermediatePath (
		final double gScoreThroughIntermediateVertex,
		final double augmentedVertexGScore
		)
	{
		return _shortestPath ? gScoreThroughIntermediateVertex < augmentedVertexGScore :
			gScoreThroughIntermediateVertex > augmentedVertexGScore;
	}

	/**
	 * VertexAugmentor Constructor
	 * 
	 * @param sourceVertexName The Source vertex Name
	 * @param shortestPath TRUE - Shortest Path Sought
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public VertexAugmentor (
		final java.lang.String sourceVertexName,
		final boolean shortestPath)
		throws java.lang.Exception
	{
		if (null == (_sourceVertexName = sourceVertexName) || _sourceVertexName.isEmpty())
		{
			throw new java.lang.Exception (
				"VertexAugmentor Constructor => Invalid Input"
			);
		}

		_augmentedVertexMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.graph.shortestpath.AugmentedVertex>();

		org.drip.graph.shortestpath.AugmentedVertex sourceAugmentedVertex =
			new org.drip.graph.shortestpath.AugmentedVertex();

		if (!sourceAugmentedVertex.setPrecedingEdge (
				null
			) || !sourceAugmentedVertex.setGScore (
				0.
			)
		)
		{
			throw new java.lang.Exception (
				"VertexAugmentor Constructor => Cannot set Initial Augmented Vertex"
			);
		}

		_augmentedVertexMap.put (
			_sourceVertexName,
			sourceAugmentedVertex
		);

		_shortestPath = shortestPath;
	}

	/**
	 * Initialize the Set of Vertexes
	 * 
	 * @param vertexNameSet The Vertex Name Set
	 * 
	 * @return TRUE - The Set of Vertexes successfully initialized
	 */

	public boolean initializeVertexNameSet (
		final java.util.Set<java.lang.String> vertexNameSet)
	{
		if (null == vertexNameSet || 0 == vertexNameSet.size())
		{
			return false;
		}

		double initialWeight = _shortestPath ? java.lang.Double.MAX_VALUE : java.lang.Double.MIN_VALUE;

		for (java.lang.String vertexName : vertexNameSet)
		{
			if (!_augmentedVertexMap.containsKey (
				vertexName
			))
			{
				org.drip.graph.shortestpath.AugmentedVertex augmentedVertex =
					new org.drip.graph.shortestpath.AugmentedVertex();

				if (!augmentedVertex.setPrecedingEdge (
						null
					) || !augmentedVertex.setGScore (
						initialWeight
					)
				)
				{
					return false;
				}

				_augmentedVertexMap.put (
					vertexName,
					augmentedVertex
				);
			}
		}

		return true;
	}

	/**
	 * Retrieve the Source Vertex Name
	 * 
	 * @return The Source Vertex Name
	 */

	public java.lang.String sourceVertexName()
	{
		return _sourceVertexName;
	}

	/**
	 * Indicate if the Shortest Path is Sought
	 * 
	 * @return TRUE - Shortest Path Sought
	 */

	public boolean shortestPath()
	{
		return _shortestPath;
	}

	/**
	 * Retrieve the Map of Augmented Vertexes
	 * 
	 * @return Map of Augmented Vertexes
	 */

	public java.util.Map<java.lang.String, org.drip.graph.shortestpath.AugmentedVertex> augmentedVertexMap()
	{
		return _augmentedVertexMap;
	}

	/**
	 * Update the Augmented Vertex through the Preceding Vertex represented in the Preceding Edge
	 * 
	 * @param precedingEdge The Preceding Edge
	 * 
	 * @return TRUE - Update of the Augmented Vertex is Successful
	 */

	public boolean updateAugmentedVertex (
		final org.drip.graph.core.Edge precedingEdge)
	{
		if (null == precedingEdge)
		{
			return false;
		}

		java.lang.String precedingAugmentedVertexName = precedingEdge.sourceVertexName();

		if (!_augmentedVertexMap.containsKey (
			precedingAugmentedVertexName
		))
		{
			return false;
		}

		java.lang.String augmentedVertexName = precedingEdge.destinationVertexName();

		double gScoreThroughPrecedingVertex = _augmentedVertexMap.get (
			precedingAugmentedVertexName
		).gScore() + precedingEdge.weight();

		if (!_augmentedVertexMap.containsKey (
			augmentedVertexName
		))
		{
			org.drip.graph.shortestpath.AugmentedVertex augmentedVertex =
				new org.drip.graph.shortestpath.AugmentedVertex();

			if (!augmentedVertex.setPrecedingEdge (
					precedingEdge
				) || !augmentedVertex.setGScore (
					gScoreThroughPrecedingVertex
				)
			)
			{
				return false;
			}

			_augmentedVertexMap.put (
				augmentedVertexName,
				augmentedVertex
			);
		}
		else
		{
			org.drip.graph.shortestpath.AugmentedVertex augmentedVertex = _augmentedVertexMap.get (
				augmentedVertexName
			);

			if (compareIntermediatePath (
				gScoreThroughPrecedingVertex,
				augmentedVertex.gScore()
			))
			{
				if (!augmentedVertex.setPrecedingEdge (
						precedingEdge
					) || !augmentedVertex.setGScore (
						gScoreThroughPrecedingVertex
					)
				)
				{
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Set the Augmented Vertex as having been Processed
	 * 
	 * @param augmentedVertexName The Augmented Vertex Name
	 * 
	 * @return TRUE - The Augmented Vertex is set as having been Processed
	 */

	public boolean setVertexProcessed (
		final java.lang.String augmentedVertexName)
	{
		return null == augmentedVertexName || !_augmentedVertexMap.containsKey (
			augmentedVertexName
		) ? false : _augmentedVertexMap.get (
			augmentedVertexName
		).setProcessed (
			true
		);
	}

	/**
	 * Indicate if the Augmented Vertex has been Processed
	 * 
	 * @param augmentedVertexName The Augmented Vertex Name
	 * 
	 * @return TRUE - The Augmented Vertex has been Processed
	 */

	public boolean vertexProcessed (
		final java.lang.String augmentedVertexName)
	{
		return null == augmentedVertexName || !_augmentedVertexMap.containsKey (
			augmentedVertexName
		) ? false : _augmentedVertexMap.get (
			augmentedVertexName
		).processed();
	}

	/**
	 * Generate the Path to the Destination Vertex
	 * 
	 * @param destinationVertexName The Destination Vertex
	 * 
	 * @return Path to the Destination Vertex
	 */

	public org.drip.graph.core.Path generatePath (
		final java.lang.String destinationVertexName)
	{
		if (null == destinationVertexName)
		{
			return null;
		}

		java.lang.String currentDestinationVertexName = destinationVertexName;

		java.util.List<org.drip.graph.core.Edge> edgeList =
			new java.util.ArrayList<org.drip.graph.core.Edge>();

		while (!currentDestinationVertexName.equalsIgnoreCase (
			_sourceVertexName
		))
		{
			if (!_augmentedVertexMap.containsKey (
				currentDestinationVertexName
			))
			{
				return null;
			}

			org.drip.graph.core.Edge currentEdge = _augmentedVertexMap.get (
				currentDestinationVertexName
			).precedingEdge();

			edgeList.add (
				0,
				currentEdge
			);

			currentDestinationVertexName = currentEdge.sourceVertexName();
		}

		try
		{
			return new org.drip.graph.core.Path (
				edgeList
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
