
package org.drip.graph.shortestpath;

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
 * <i>AugmentedVertex</i> contains the Augmentations of a Vertex during a Shortest Path Algorithm. The
 * 	References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Dijkstra, E. W. (1959): A Note on Two Problems in Connection with Graphs <i>Numerische
 *  			Mathematik</i> <b>1</b> 269-271
 *  	</li>
 *  	<li>
 *  		Felner, A. (2011): Position Paper: Dijkstra�s Algorithm versus Uniform Cost Search or a Case
 *  			against Dijkstra�s Algorithm <i>Proceedings of the 4<sup>th</sup> International Symposium on
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

public class AugmentedVertex
{
	private boolean _processed = false;
	private double _gScore = java.lang.Double.NaN;
	private double _hScore = java.lang.Double.NaN;
	private org.drip.graph.core.Edge _precedingEdge = null;

	/**
	 * Generate a Non-heuristic Instance of AugmentedVertex
	 * 
	 * @return Non-heuristic Instance of AugmentedVertex
	 */

	public static final AugmentedVertex NonHeuristic()
	{
		try
		{
			return new AugmentedVertex (
				java.lang.Double.POSITIVE_INFINITY,
				java.lang.Double.POSITIVE_INFINITY
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * AugmentedVertex Constructor
	 * 
	 * @param gScore G Score
	 * @param hScore H Score
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AugmentedVertex (
		final double gScore,
		final double hScore)
		throws java.lang.Exception
	{
		if (java.lang.Double.isNaN (
				_gScore = gScore
			) || java.lang.Double.isNaN (
				_hScore = hScore
			)
		)
		{
			throw new java.lang.Exception (
				"AugmentedVertex Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Preceding Edge
	 * 
	 * @return The Preceding Edge
	 */

	public org.drip.graph.core.Edge precedingEdge()
	{
		return _precedingEdge;
	}

	/**
	 * Indicate if the Vertex has been Processed
	 * 
	 * @return TRUE - The Vertex has been Processed
	 */

	public boolean processed()
	{
		return _processed;
	}

	/**
	 * Retrieve the Vertex Path G Score
	 * 
	 * @return The Vertex Path G Score
	 */

	public double gScore()
	{
		return _gScore;
	}

	/**
	 * Retrieve the Vertex Path H Score
	 * 
	 * @return The Vertex Path H Score
	 */

	public double hScore()
	{
		return _hScore;
	}

	/**
	 * Retrieve the Vertex Path F Score
	 * 
	 * @return The Vertex Path F Score
	 */

	public double fScore()
	{
		return _gScore + _hScore;
	}

	/**
	 * Set the Preceding Edge in the Path
	 * 
	 * @param precedingEdge The Preceding Edge in the Path
	 * 
	 * @return TRUE - The Preceding Edge in the Path successfully set
	 */

	public boolean setPrecedingEdge (
		final org.drip.graph.core.Edge precedingEdge)
	{
		_precedingEdge = precedingEdge;
		return true;
	}

	/**
	 * Set the Vertex Processing Status
	 * 
	 * @param processed The Vertex Processing Status
	 * 
	 * @return TRUE - The Vertex Processing Status successfully set
	 */

	public boolean setProcessed (
		final boolean processed)
	{
		_processed = processed;
		return true;
	}

	/**
	 * Update the Vertex Path G Score
	 * 
	 * @param gScore The Vertex Path G Score
	 * 
	 * @return TRUE - The Vertex Path G Score successfully updated
	 */

	public boolean updateGScore (
		final double gScore)
	{
		if (java.lang.Double.isNaN (
			gScore
		))
		{
			return false;
		}

		_gScore = gScore;
		return true;
	}

	/**
	 * Retrieve the Preceding Vertex Name
	 * 
	 * @return The Preceding Vertex Name
	 */

	public java.lang.String precedingVertexName()
	{
		return _precedingEdge.sourceVertexName();
	}
}
