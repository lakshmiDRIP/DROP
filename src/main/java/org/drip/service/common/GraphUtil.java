
package org.drip.service.common;

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
 * <i>GraphUtil</i> implements Graph Utility Functions.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/common">Assorted Data Structures Support Utilities</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class GraphUtil
{

	public static final boolean IsGraphBipartite (
		final int[][] graph)
	{
		java.util.Map<java.lang.Integer, java.util.Set<java.lang.Integer>> vertexEdgesMap =
			new java.util.HashMap<java.lang.Integer, java.util.Set<java.lang.Integer>>();

		for (int[] edge : graph)
		{
			if (vertexEdgesMap.containsKey(edge[0]))
			{
				vertexEdgesMap.get(edge[0]).add(edge[1]);
			}
			else
			{
				java.util.Set<java.lang.Integer> neighborSet = new java.util.HashSet<java.lang.Integer>();

				neighborSet.add(edge[1]);

				vertexEdgesMap.put (edge[0], neighborSet);
			}

			if (vertexEdgesMap.containsKey(edge[1]))
			{
				vertexEdgesMap.get(edge[1]).add(edge[0]);
			}
			else
			{
				java.util.Set<java.lang.Integer> neighborSet = new java.util.HashSet<java.lang.Integer>();

				neighborSet.add(edge[0]);

				vertexEdgesMap.put (edge[1], neighborSet);
			}
		}

		java.util.List<java.util.Set<java.lang.Integer>> vertexSetCluster =
			new java.util.ArrayList<java.util.Set<java.lang.Integer>>();

		for (java.util.Map.Entry<java.lang.Integer, java.util.Set<java.lang.Integer>> vertexEdgesEntry :
			vertexEdgesMap.entrySet())
		{
			java.util.Set<java.lang.Integer> neighborSet = vertexEdgesEntry.getValue();

			boolean vertexSetFound = false;

			for (java.util.Set<java.lang.Integer> vertexSet : vertexSetCluster)
			{
				for (int neighborVertex : neighborSet)
				{
					if (vertexSet.contains(neighborVertex))
					{
						vertexSet.addAll(neighborSet);

						vertexSetFound = true;
						break;
					}
				}
			}

			if (!vertexSetFound)
			{
				vertexSetCluster.add(neighborSet);
			}
		}

		return 2 == vertexSetCluster.size() || graph.length <= vertexSetCluster.size();
	}

	private static final java.util.HashMap<java.lang.String, java.lang.Character> CharacterCodeMap()
	{
		java.util.HashMap<java.lang.String, java.lang.Character> characterCodeMap = new
			java.util.HashMap<java.lang.String, java.lang.Character>();

		characterCodeMap.put ("1", 'A');

		characterCodeMap.put ("2", 'B');

		characterCodeMap.put ("3", 'C');

		characterCodeMap.put ("4", 'D');

		characterCodeMap.put ("5", 'E');

		characterCodeMap.put ("6", 'F');

		characterCodeMap.put ("7", 'G');

		characterCodeMap.put ("8", 'H');

		characterCodeMap.put ("9", 'I');

		characterCodeMap.put ("10", 'J');

		characterCodeMap.put ("11", 'K');

		characterCodeMap.put ("12", 'L');

		characterCodeMap.put ("13", 'M');

		characterCodeMap.put ("14", 'N');

		characterCodeMap.put ("15", 'O');

		characterCodeMap.put ("16", 'P');

		characterCodeMap.put ("17", 'Q');

		characterCodeMap.put ("18", 'R');

		characterCodeMap.put ("19", 'S');

		characterCodeMap.put ("20", 'T');

		characterCodeMap.put ("21", 'U');

		characterCodeMap.put ("22", 'V');

		characterCodeMap.put ("23", 'W');

		characterCodeMap.put ("24", 'X');

		characterCodeMap.put ("25", 'Y');

		characterCodeMap.put ("26", 'Z');

		return characterCodeMap;
	}

	public static final java.util.Set<java.lang.String> DecodeCombinations (
		final java.lang.String number)
	{
		java.util.HashMap<java.lang.String, java.lang.Character> characterCodeMap = CharacterCodeMap();

		java.util.List<java.lang.String> combinationList = new java.util.ArrayList<java.lang.String>();

		java.util.Set<java.lang.String> combinationSet = new java.util.HashSet<java.lang.String>();

		java.util.List<java.lang.Integer> indexList = new java.util.ArrayList<java.lang.Integer>();

		indexList.add (0);

		combinationList.add ("");

		while (!indexList.isEmpty()) {
			int queueTailIndex = indexList.size() - 1;

			int index = indexList.remove (queueTailIndex);

			java.lang.String combination = combinationList.remove (queueTailIndex);

			if (index >= number.length()) {
				combinationSet.add (combination);

				continue;
			}

			java.lang.String singleDigit = number.substring (index, index + 1);

			if (characterCodeMap.containsKey (singleDigit)) {
				combinationList.add (combination + characterCodeMap.get (singleDigit));

				indexList.add (index + 1);
			}

			if (index == number.length() - 1) continue;

			java.lang.String doubleDigit = number.substring (index, index + 2);

			if (characterCodeMap.containsKey (doubleDigit)) {
				combinationList.add (combination + characterCodeMap.get (doubleDigit));

				indexList.add (index + 2);
			}
		}

		return combinationSet;
	}

	/**
	 * Establish the Separate Components connecting the Items
	 * 
	 * @param itemListSequence Sequence of Items Lists
	 * 
	 * @return The Connected Components
	 */

	public static final java.util.ArrayList<java.util.HashSet<String>> LargestGroup (
		java.util.List<java.util.List<String>> itemListSequence)
	{
		java.util.ArrayList<java.util.HashSet<String>> componentList = new
			java.util.ArrayList<java.util.HashSet<String>>();

		for (java.util.List<String> itemList : itemListSequence) {
			if (componentList.isEmpty()) {
				java.util.HashSet<String> itemSet = new java.util.HashSet<String>();

				itemSet.addAll (itemList);

				componentList.add (itemSet);

				continue;
			}

			java.util.ArrayList<Integer> mergeList = new java.util.ArrayList<Integer>();

			for (String item : itemList) {
				for (int componentIndex = 0; componentIndex < componentList.size(); ++componentIndex) {
					if (componentList.get (componentIndex).contains (item)) mergeList.add (componentIndex);
				}
			}

			if (mergeList.isEmpty()) {
				java.util.HashSet<String> itemSet = new java.util.HashSet<String>();

				itemSet.addAll (itemList);

				componentList.add (itemSet);
			} else {
				java.util.HashSet<String> component = componentList.get (mergeList.get (0));

				for (int mergeListIndex = 1; mergeListIndex < mergeList.size(); ++mergeListIndex)
					component.addAll (componentList.get (mergeListIndex));

				component.addAll (itemList);

				for (int mergeListIndex = mergeList.size() - 1; mergeListIndex > 1 ; --mergeListIndex)
					componentList.remove (mergeListIndex);
			}
		}

		return componentList;
	}

	/**
	 * Given an undirected graph, find out all the vertices when removed will make the graph disconnected.
	 * 	Initially the graph is connected.
	 * 
	 * @param edgeArray Array of the Edges
	 * 
	 * @return List of Critical Nodes
	 */

	public static final java.util.ArrayList<Integer> CriticalNodes (
		final int[][] edgeArray)
	{
		java.util.HashMap<Integer, java.util.HashSet<Integer>> neighborSetMap = new
			java.util.HashMap<Integer, java.util.HashSet<Integer>>();

		for (int[] edge : edgeArray) {
			if (neighborSetMap.containsKey (edge[0]))
				neighborSetMap.get (edge[0]).add (edge[1]);
			else {
				java.util.HashSet<Integer> neighborSet = new java.util.HashSet<Integer>();

				neighborSet.add (edge[1]);

				neighborSetMap.put (edge[0], neighborSet);
			}

			if (neighborSetMap.containsKey (edge[1]))
				neighborSetMap.get (edge[1]).add (edge[0]);
			else {
				java.util.HashSet<Integer> neighborSet = new java.util.HashSet<Integer>();

				neighborSet.add (edge[0]);

				neighborSetMap.put (edge[1], neighborSet);
			}
		}

		java.util.HashSet<Integer> intermediateNodeSet = new java.util.HashSet<Integer>();

		for (int nodeID : neighborSetMap.keySet()) {
			if (1 != neighborSetMap.get (nodeID).size()) intermediateNodeSet.add (nodeID);
		}

		java.util.ArrayList<Integer> criticalNodeList = new java.util.ArrayList<Integer>();

		for (int intermediateNodeID : intermediateNodeSet) {
			java.util.HashMap<Integer, java.util.HashSet<Integer>> modifiedNeighborSetMap = new
				java.util.HashMap<Integer, java.util.HashSet<Integer>>();

			int startingVertex = -1;

			for (int[] edge : edgeArray) {
				if (edge[0] == intermediateNodeID || edge[1] == intermediateNodeID) continue;

				if (-1 == startingVertex) startingVertex = edge[0];

				if (modifiedNeighborSetMap.containsKey (edge[0]))
					modifiedNeighborSetMap.get (edge[0]).add (edge[1]);
				else {
					java.util.HashSet<Integer> modifiedNeighborSet = new java.util.HashSet<Integer>();

					modifiedNeighborSet.add (edge[1]);

					modifiedNeighborSetMap.put (edge[0], modifiedNeighborSet);
				}

				if (modifiedNeighborSetMap.containsKey (edge[1]))
					modifiedNeighborSetMap.get (edge[1]).add (edge[0]);
				else {
					java.util.HashSet<Integer> modifiedNeighborSet = new java.util.HashSet<Integer>();

					modifiedNeighborSet.add (edge[0]);

					modifiedNeighborSetMap.put (edge[1], modifiedNeighborSet);
				}
			}

			java.util.HashSet<Integer> visitedVertexSet = new java.util.HashSet<Integer>();

			java.util.ArrayList<Integer> bfsVertexStack = new java.util.ArrayList<Integer>();

			bfsVertexStack.add (startingVertex);

			while (!bfsVertexStack.isEmpty()) {
				int vertexID = bfsVertexStack.remove (bfsVertexStack.size() - 1);

				visitedVertexSet.add (vertexID);

				java.util.HashSet<Integer> modifiedNeighborSet = modifiedNeighborSetMap.get (vertexID);

				if (modifiedNeighborSet.isEmpty()) continue;

				for (int modifiedNeighborID : modifiedNeighborSet) {
					if (!visitedVertexSet.contains (modifiedNeighborID))
						bfsVertexStack.add (modifiedNeighborID);
				}
			}

			if (visitedVertexSet.size() != neighborSetMap.size() - 1)
				criticalNodeList.add (intermediateNodeID);
		}

		return criticalNodeList;
	}

	/**
	 * There are N cities numbered from 1 to N.
	 * 
	 * You are given connections, where each connections[i] = [city1, city2, cost] represents the cost to
	 *  connect city1 and city2together. (A connection is bidirectional: connecting city1 and city2 is the
	 *   same as connecting city2 and city1.)
	 * 
	 * Return the minimum cost so that for every pair of cities, there exists a path of connections (possibly
	 *  of length 1) that connects those two cities together. The cost is the sum of the connection costs
	 *  used. If the task is impossible, return -1.
	 *  
	 * @param weightedEdgeArray Array of Weighted Edges
	 * 
	 * @return The MSP Cost
	 */

	public static final int MSPCost (
		final int[][] weightedEdgeArray)
	{
		java.util.HashMap<String, Integer> edgeWeightMap = new java.util.HashMap<String, Integer>();

		java.util.HashMap<Integer, java.util.TreeMap<Integer, java.util.HashSet<Integer>>> weightedGraph =
			new java.util.HashMap<Integer, java.util.TreeMap<Integer, java.util.HashSet<Integer>>>();

		for (int[] weightedEdge : weightedEdgeArray) {
			weightedGraph.put (weightedEdge[0], new java.util.TreeMap<Integer,
				java.util.HashSet<Integer>>());

			weightedGraph.put (weightedEdge[1], new java.util.TreeMap<Integer,
				java.util.HashSet<Integer>>());

			edgeWeightMap.put (weightedEdge[0] + "_" + weightedEdge[1], weightedEdge[2]);

			edgeWeightMap.put (weightedEdge[1] + "_" + weightedEdge[0], weightedEdge[2]);
		}

		for (int[] weightedEdge : weightedEdgeArray) {
			java.util.TreeMap<Integer, java.util.HashSet<Integer>> weightSortedNeighborSet =
				weightedGraph.get (weightedEdge[0]);

			if (weightSortedNeighborSet.containsKey (weightedEdge[2]))
				weightSortedNeighborSet.get (weightedEdge[2]).add (weightedEdge[1]);
			else {
				java.util.HashSet<Integer> neighborSet = new java.util.HashSet<Integer>();

				neighborSet.add (weightedEdge[1]);

				weightSortedNeighborSet.put (weightedEdge[2], neighborSet);
			}

			weightSortedNeighborSet = weightedGraph.get (weightedEdge[1]);

			if (weightSortedNeighborSet.containsKey (weightedEdge[2]))
				weightSortedNeighborSet.get (weightedEdge[2]).add (weightedEdge[0]);
			else {
				java.util.HashSet<Integer> neighborSet = new java.util.HashSet<Integer>();

				neighborSet.add (weightedEdge[0]);

				weightSortedNeighborSet.put (weightedEdge[2], neighborSet);
			}
		}

		int cost = 0;
		int startingVertex = weightedEdgeArray[0][0];

		java.util.TreeMap<Integer, java.util.ArrayList<String>> edgePriorityQueue = new
			java.util.TreeMap<Integer, java.util.ArrayList<String>>();

		java.util.TreeMap<Integer, java.util.HashSet<Integer>> weightSortedNeighborSet =
			weightedGraph.get (startingVertex);

		if (weightSortedNeighborSet.isEmpty()) return -1;

		for (int edgeWeight : weightSortedNeighborSet.keySet()) {
			for (int neighborVertex : weightSortedNeighborSet.get (edgeWeight)) {
				if (edgePriorityQueue.containsKey (edgeWeight))
					edgePriorityQueue.get (edgeWeight).add (startingVertex + "_" + neighborVertex);
				else {
					java.util.ArrayList<String> edgeList = new java.util.ArrayList<String>();

					edgeList.add (startingVertex + "_" + neighborVertex);

					edgePriorityQueue.put (edgeWeight, edgeList);
				}
			}
		}

		java.util.ArrayList<String> spanningEdgeList = new java.util.ArrayList<String>();

		java.util.HashSet<Integer> visitedVertexSet = new java.util.HashSet<Integer>();

		visitedVertexSet.add (startingVertex);

		while (!edgePriorityQueue.isEmpty()) {
			int shortestDistance = edgePriorityQueue.firstKey();

			java.util.ArrayList<String> edgeList = edgePriorityQueue.get (shortestDistance);

			String nextEdge = edgeList.remove (0);

			if (edgeList.isEmpty()) edgePriorityQueue.remove (shortestDistance);

			int nextVertex = Integer.parseInt (nextEdge.split ("_")[1]);

			if (!visitedVertexSet.contains (nextVertex)) {
				visitedVertexSet.add (nextVertex);

				spanningEdgeList.add (nextEdge);

				weightSortedNeighborSet = weightedGraph.get (nextVertex);

				if (weightSortedNeighborSet.isEmpty()) continue;

				for (int edgeWeight : weightSortedNeighborSet.keySet()) {
					for (int neighborVertex : weightSortedNeighborSet.get (edgeWeight)) {
						if (edgePriorityQueue.containsKey (edgeWeight))
							edgePriorityQueue.get (edgeWeight).add (nextVertex + "_" + neighborVertex);
						else {
							edgeList = new java.util.ArrayList<String>();

							edgeList.add (nextVertex + "_" + neighborVertex);

							edgePriorityQueue.put (edgeWeight, edgeList);
						}
					}
				}
			}
		}

		if (visitedVertexSet.size() < weightedGraph.size()) return -1;

		for (String edge : spanningEdgeList)
			cost = cost + edgeWeightMap.get (edge);

		return cost;
	}

	public static final void main (
		final String[] argumentArray)
	{
		System.out.println (MSPCost (new int[][] {{1, 2, 5}, {1, 3, 6}, {2, 3, 1}}));

		System.out.println (MSPCost (new int[][] {{1, 2, 3}, {3, 4, 4}}));
	}
}
