
package org.drip.graph.core;

import java.util.Map;
import java.util.Set;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.graph.heap.BinomialTreePriorityQueue;
import org.drip.graph.heap.PriorityQueue;

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
 * <i>Network</i> implements a Generic Topological Network containing Discrete Vertexes and Edges. The
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

abstract public class Network<V>
{
	private String _initialVertexName = "";

	protected Map<String, Edge> _edgeMap = null;
	protected Map<String, Vertex<?>> _vertexMap = null;

	protected String addVertexEdge (
		final Edge edge)
	{
		String edgeKey = "";

		String sourceVertexName = edge.sourceVertexName();

		String destinationVertexName = edge.destinationVertexName();

		if (_vertexMap.containsKey (sourceVertexName)) {
			edgeKey = _vertexMap.get (sourceVertexName).addEdge (edge);
		} else {
			try {
				Vertex<?> vertex = Vertex.Standard (sourceVertexName);

				edgeKey = vertex.addEdge (edge);

				_vertexMap.put (sourceVertexName, vertex);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		if (!_vertexMap.containsKey (destinationVertexName)) {
			addVertex (destinationVertexName);
		}

		return edgeKey;
	}

	protected String addVertexEdge (
		final Edge edge,
		final Map<String, V> vertexValueMap)
	{
		String edgeKey = "";

		String sourceVertexName = edge.sourceVertexName();

		String destinationVertexName = edge.destinationVertexName();

		if (_vertexMap.containsKey (sourceVertexName)) {
			edgeKey = _vertexMap.get (sourceVertexName).addEdge (edge);
		} else {
			try {
				Vertex<V> vertex = new Vertex<V> (sourceVertexName, vertexValueMap.get (sourceVertexName));

				edgeKey = vertex.addEdge (edge);

				_vertexMap.put (sourceVertexName, vertex);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		if (!_vertexMap.containsKey (destinationVertexName)) {
			addVertex (destinationVertexName, vertexValueMap.get (destinationVertexName));
		}

		return edgeKey;
	}

	protected boolean removeVertexEdge (
		final String vertexName,
		final String edgeKey)
	{
		return _vertexMap.containsKey (vertexName) ?
			_vertexMap.get (vertexName).removeEdge (edgeKey) : true;
	}

	protected Network()
	{
		_edgeMap = new CaseInsensitiveHashMap<Edge>();

		_vertexMap = new CaseInsensitiveHashMap<Vertex<?>>();
	}

	/**
	 * Retrieve the Vertex Map
	 * 
	 * @return The Vertex Map
	 */

	public Map<String, Vertex<?>> vertexMap()
	{
		return _vertexMap;
	}

	/**
	 * Retrieve the Edge Map
	 * 
	 * @return The Edge Map
	 */

	public Map<String, Edge> edgeMap()
	{
		return _edgeMap;
	}

	/**
	 * Retrieve the Initial Vertex Name
	 * 
	 * @return The Initial Vertex Name
	 */

	public String initialVertexName()
	{
		return _initialVertexName;
	}

	/**
	 * Retrieve the Count of the Edges
	 * 
	 * @return The Count of the Edges
	 */

	public int edgeCount()
	{
		return _edgeMap.size();
	}

	/**
	 * Retrieve the Count of the Vertexes
	 * 
	 * @return The Count of the Vertexes
	 */

	public int vertexCount()
	{
		return _vertexMap.size();
	}

	/**
	 * Retrieve the Set of Vertex Names
	 * 
	 * @return The Set of Vertex Names
	 */

	public Set<String> vertexNameSet()
	{
		return _vertexMap.keySet();
	}

	/**
	 * Retrieve the Value contained in the Vertex
	 * 
	 * @param vertexName Vertex Name
	 * 
	 * @return Value contained in the Vertex
	 */

	@SuppressWarnings ("unchecked") public V vertexValue (
		final String vertexName)
	{
		return null == vertexName || vertexName.isEmpty() || _vertexMap.containsKey (vertexName) ?
			null : (V) _vertexMap.get (vertexName).value();
	}

	/**
	 * Add a Vertex to the Network
	 *  
	 * @param vertexName The Vertex Name
	 * 
	 * @return TRUE - The Vertex successfully added to the Network
	 */

	public boolean addVertex (
		final String vertexName)
	{
		if (null == vertexName || vertexName.isEmpty() || _vertexMap.containsKey (vertexName)) {
			return false;
		}

		if (_initialVertexName.isEmpty()) {
			_initialVertexName = vertexName;
		}

		try {
			_vertexMap.put (vertexName, Vertex.Standard (vertexName));
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Add a Vertex to the Network
	 * 
	 * @param vertexName The Vertex Name
	 * @param vertexValue The Vertex Value
	 * 
	 * @return TRUE - The Vertex successfully added to the Network
	 */

	public boolean addVertex (
		final String vertexName,
		final V vertexValue)
	{
		if (null == vertexName || vertexName.isEmpty() || _vertexMap.containsKey (vertexName)) {
			return false;
		}

		if (_initialVertexName.isEmpty()) {
			_initialVertexName = vertexName;
		}

		try {
			_vertexMap.put (vertexName, new Vertex<V> (vertexName, vertexValue));
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Add an Edge to the Network
	 * 
	 * @param edge The Edge
	 * 
	 * @return TRUE - The Edge successfully added
	 */

	public boolean addEdge (
		final Edge edge)
	{
		if (null == edge) {
			return false;
		}

		String edgeKey = addVertexEdge (edge);

		if (null == edgeKey || edgeKey.isEmpty()) {
			return false;
		}

		if (_initialVertexName.isEmpty()) {
			_initialVertexName = edge.sourceVertexName();
		}

		_edgeMap.put (edgeKey, edge);

		return true;
	}

	/**
	 * Add an Edge to the Network
	 * 
	 * @param edge The Edge
	 * @param vertexValueMap Vertex Value Map
	 * 
	 * @return TRUE - The Edge successfully added
	 */

	public boolean addEdge (
		final Edge edge,
		final Map<String, V> vertexValueMap)
	{
		if (null == edge) {
			return false;
		}

		String edgeKey = addVertexEdge (edge, vertexValueMap);

		if (null == edgeKey || edgeKey.isEmpty()) {
			return false;
		}

		if (_initialVertexName.isEmpty()) {
			_initialVertexName = edge.sourceVertexName();
		}

		_edgeMap.put (edgeKey, edge);

		return true;
	}

	/**
	 * Add a Bidirectional Edge to the Network
	 * 
	 * @param edge The Bidirectional Edge
	 * 
	 * @return TRUE - The Bidirectional Edge successfully added
	 */

	public boolean addBidirectionalEdge (
		final Edge edge)
	{
		return addEdge (edge) && addEdge (edge.invert());
	}

	/**
	 * Add a Bidirectional Edge to the Network
	 * 
	 * @param edge The Bidirectional Edge
	 * @param vertexValueMap Vertex Value Map
	 * 
	 * @return TRUE - The Bidirectional Edge successfully added
	 */

	public boolean addBidirectionalEdge (
		final Edge edge,
		final Map<String, V> vertexValueMap)
	{
		return addEdge (edge, vertexValueMap) && addEdge (edge.invert(), vertexValueMap);
	}

	/**
	 * Remove an Edge from the Network
	 * 
	 * @param edgeKey The Edge Key
	 * 
	 * @return TRUE - The Edge successfully removed
	 */

	public boolean removeEdge (
		final String edgeKey)
	{
		if (null == edgeKey || !_edgeMap.containsKey (edgeKey) ||
			!removeVertexEdge (_edgeMap.get (edgeKey).sourceVertexName(), edgeKey))
		{
			return false;
		}

		_edgeMap.remove (edgeKey);

		return true;
	}

	/**
	 * Construct an Edge Priority Queue
	 * 
	 * @param minHeap TRUE - The Priority Queue is in the Ascending Order of Weight
	 * 
	 * @return The Edge Priority Queue
	 */

	public PriorityQueue<Double, String> edgePriorityQueue (
		final boolean minHeap)
	{
		PriorityQueue<Double, String> edgePriorityQueue =
			new BinomialTreePriorityQueue<Double, String> (minHeap);

		for (Map.Entry<String, Edge> edgeMapEntry : _edgeMap.entrySet()) {
			edgePriorityQueue.insert (edgeMapEntry.getValue().weight(), edgeMapEntry.getKey());
		}

		return edgePriorityQueue;
	}

	/**
	 * Indicate if the Network is Empty
	 * 
	 * @return TRUE - The Network is Empty
	 */

	public boolean isEmpty()
	{
		return _vertexMap.isEmpty();
	}

	/**
	 * Indicate if the Vertex is Contained in the Network
	 * 
	 * @param vertexName The Vertex Name
	 * 
	 * @return TRUE - The Vertex is Contained in the Network
	 */

	public boolean containsVertex (
		final String vertexName)
	{
		return null != vertexName && _vertexMap.containsKey (vertexName);
	}

	/**
	 * Retrieve the Length of the Discrete Object
	 * 
	 * @return Length of the Discrete Object
	 */

	public double length()
	{
		double length = 0.;

		for (Edge edge : _edgeMap.values()) {
			length = length + edge.weight();
		}

		return length;
	}

	/**
	 * Indicate if the Edge forms a Cycle with the Network
	 * 
	 * @param edge The Edge
	 * 
	 * @return TRUE - The Edge forms a Cycle with the Network
	 */

	public boolean isEdgeACycle (
		final Edge edge)
	{
		return null != edge && _vertexMap.containsKey (edge.sourceVertexName()) &&
			_vertexMap.containsKey (edge.destinationVertexName());
	}

	/**
	 * Indicate if the Specified Edge matches with any Edges in the Network
	 * 
	 * @param edgeOther The "Other" Edge
	 * 
	 * @return TRUE - The Specified Edge matches with any Edges in the Network
	 */

	public boolean containsEdge (
		final Edge edgeOther)
	{
		if (null == edgeOther) {
			return false;
		}

		for (Edge edge : _edgeMap.values()) {
			if (edge.compareWith (edgeOther)) {
				return true;
			}
		}

		return false;
	}
}
