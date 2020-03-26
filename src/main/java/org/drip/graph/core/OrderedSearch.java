
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
 * <i>OrderedSearch</i> holds the Results of the Ordered Search (BFS/DFS) of the Vertexes of a Graph. The
 * 	References are:
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

public class OrderedSearch
{
	private boolean _containsCycle = false;
	private java.util.Set<java.lang.String> _vertexNameSet = null;
	private java.util.List<java.lang.String> _vertexNameList = null;

	/**
	 * OrderedSearch Constructor
	 */

	public OrderedSearch()
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
	 * Retrieve the List of available Vertexes
	 * 
	 * @return List of available Vertexes
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

	public boolean addVertex (
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

			_vertexNameList.add (
				vertexName
			);
		}

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
}
