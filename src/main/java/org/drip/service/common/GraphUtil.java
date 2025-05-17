
package org.drip.service.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>GraphUtil</i> implements Graph Utility Functions. It implements the following Functions:
 * <br>
 * 
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/common/README.md">Assorted Data Structures Support Utilities</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class GraphUtil
{

	private static final HashMap<String, Character> CharacterCodeMap()
	{
		HashMap<String, Character> characterCodeMap = new HashMap<String, Character>();

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

	/**
	 * Check if the Graph is Bipartite
	 * 
	 * @param graph Graph as an Adjacency List
	 * 
	 * @return TRUE - The Graph is Bipartite
	 */

	public static final boolean IsGraphBipartite (
		final int[][] graph)
	{
		Map<Integer, Set<Integer>> vertexEdgesMap = new HashMap<Integer, Set<Integer>>();

		for (int[] edge : graph) {
			if (vertexEdgesMap.containsKey (edge[0])) {
				vertexEdgesMap.get (edge[0]).add (edge[1]);
			} else {
				Set<Integer> neighborSet = new HashSet<Integer>();

				neighborSet.add (edge[1]);

				vertexEdgesMap.put (edge[0], neighborSet);
			}

			if (vertexEdgesMap.containsKey (edge[1])) {
				vertexEdgesMap.get (edge[1]).add (edge[0]);
			} else {
				Set<Integer> neighborSet = new HashSet<Integer>();

				neighborSet.add (edge[0]);

				vertexEdgesMap.put (edge[1], neighborSet);
			}
		}

		List<Set<Integer>> vertexSetCluster = new ArrayList<Set<Integer>>();

		for (Map.Entry<Integer, Set<Integer>> vertexEdgesEntry : vertexEdgesMap.entrySet()) {
			Set<Integer> vertexNeighborSet = vertexEdgesEntry.getValue();

			boolean vertexSetFound = false;

			for (Set<Integer> vertexSet : vertexSetCluster) {
				for (int neighborVertex : vertexNeighborSet) {
					if (vertexSet.contains (neighborVertex)) {
						vertexSet.addAll (vertexNeighborSet);

						vertexSetFound = true;
						break;
					}
				}
			}

			if (!vertexSetFound) {
				vertexSetCluster.add (vertexNeighborSet);
			}
		}

		int vertexSetClusterSize = vertexSetCluster.size();

		return 2 == vertexSetClusterSize || graph.length <= vertexSetClusterSize;
	}

	/**
	 * Decode all possible Combinations of the Number
	 * 
	 * @param number The Input Number
	 * 
	 * @return All possible Combinations of the Number
	 */

	public static final Set<String> DecodeCombinations (
		final String number)
	{
		HashMap<String, Character> characterCodeMap = CharacterCodeMap();

		List<String> combinationList = new ArrayList<String>();

		Set<String> combinationSet = new HashSet<String>();

		List<Integer> indexList = new ArrayList<Integer>();

		combinationList.add ("");

		indexList.add (0);

		while (!indexList.isEmpty()) {
			int queueTailIndex = indexList.size() - 1;

			int index = indexList.remove (queueTailIndex);

			String combination = combinationList.remove (queueTailIndex);

			if (index >= number.length()) {
				combinationSet.add (combination);

				continue;
			}

			String singleDigit = number.substring (index, index + 1);

			if (characterCodeMap.containsKey (singleDigit)) {
				combinationList.add (combination + characterCodeMap.get (singleDigit));

				indexList.add (index + 1);
			}

			if (index == number.length() - 1) {
				continue;
			}

			String doubleDigit = number.substring (index, index + 2);

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

	public static final ArrayList<HashSet<String>> LargestGroup (
		List<List<String>> itemListSequence)
	{
		ArrayList<HashSet<String>> componentList = new ArrayList<HashSet<String>>();

		for (List<String> itemList : itemListSequence) {
			if (componentList.isEmpty()) {
				HashSet<String> itemSet = new HashSet<String>();

				itemSet.addAll (itemList);

				componentList.add (itemSet);

				continue;
			}

			ArrayList<Integer> mergeList = new ArrayList<Integer>();

			for (String item : itemList) {
				for (int componentIndex = 0; componentIndex < componentList.size(); ++componentIndex) {
					if (componentList.get (componentIndex).contains (item)) {
						mergeList.add (componentIndex);
					}
				}
			}

			if (mergeList.isEmpty()) {
				HashSet<String> itemSet = new HashSet<String>();

				itemSet.addAll (itemList);

				componentList.add (itemSet);
			} else {
				HashSet<String> component = componentList.get (mergeList.get (0));

				for (int mergeListIndex = 1; mergeListIndex < mergeList.size(); ++mergeListIndex) {
					component.addAll (componentList.get (mergeListIndex));
				}

				component.addAll (itemList);

				for (int mergeListIndex = mergeList.size() - 1; mergeListIndex > 1 ; --mergeListIndex) {
					componentList.remove (mergeListIndex);
				}
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

	public static final ArrayList<Integer> CriticalNodes (
		final int[][] edgeArray)
	{
		HashMap<Integer, HashSet<Integer>> vertexNeighborSetMap = new HashMap<Integer, HashSet<Integer>>();

		for (int[] edge : edgeArray) {
			if (vertexNeighborSetMap.containsKey (edge[0])) {
				vertexNeighborSetMap.get (edge[0]).add (edge[1]);
			} else {
				HashSet<Integer> neighborSet = new HashSet<Integer>();

				neighborSet.add (edge[1]);

				vertexNeighborSetMap.put (edge[0], neighborSet);
			}

			if (vertexNeighborSetMap.containsKey (edge[1])) {
				vertexNeighborSetMap.get (edge[1]).add (edge[0]);
			} else {
				HashSet<Integer> neighborSet = new HashSet<Integer>();

				neighborSet.add (edge[0]);

				vertexNeighborSetMap.put (edge[1], neighborSet);
			}
		}

		HashSet<Integer> intermediateVertexSet = new HashSet<Integer>();

		for (int vertex : vertexNeighborSetMap.keySet()) {
			if (1 != vertexNeighborSetMap.get (vertex).size()) intermediateVertexSet.add (vertex);
		}

		ArrayList<Integer> criticalVertexList = new ArrayList<Integer>();

		for (int intermediateVertex : intermediateVertexSet) {
			HashMap<Integer, HashSet<Integer>> modifiedVertexNeighborSetMap =
				new HashMap<Integer, HashSet<Integer>>();

			int startingVertex = -1;

			for (int[] edge : edgeArray) {
				if (edge[0] == intermediateVertex || edge[1] == intermediateVertex) {
					continue;
				}

				if (-1 == startingVertex) {
					startingVertex = edge[0];
				}

				if (modifiedVertexNeighborSetMap.containsKey (edge[0])) {
					modifiedVertexNeighborSetMap.get (edge[0]).add (edge[1]);
				} else {
					HashSet<Integer> modifiedNeighborSet = new HashSet<Integer>();

					modifiedNeighborSet.add (edge[1]);

					modifiedVertexNeighborSetMap.put (edge[0], modifiedNeighborSet);
				}

				if (modifiedVertexNeighborSetMap.containsKey (edge[1])) {
					modifiedVertexNeighborSetMap.get (edge[1]).add (edge[0]);
				} else {
					HashSet<Integer> modifiedNeighborSet = new HashSet<Integer>();

					modifiedNeighborSet.add (edge[0]);

					modifiedVertexNeighborSetMap.put (edge[1], modifiedNeighborSet);
				}
			}

			ArrayList<Integer> bfsVertexStack = new ArrayList<Integer>();

			HashSet<Integer> visitedVertexSet = new HashSet<Integer>();

			bfsVertexStack.add (startingVertex);

			while (!bfsVertexStack.isEmpty()) {
				int vertexID = bfsVertexStack.remove (bfsVertexStack.size() - 1);

				visitedVertexSet.add (vertexID);

				HashSet<Integer> modifiedNeighborSet = modifiedVertexNeighborSetMap.get (vertexID);

				if (modifiedNeighborSet.isEmpty()) {
					continue;
				}

				for (int modifiedNeighbor : modifiedNeighborSet) {
					if (!visitedVertexSet.contains (modifiedNeighbor))
						bfsVertexStack.add (modifiedNeighbor);
				}
			}

			if (visitedVertexSet.size() != vertexNeighborSetMap.size() - 1)
				criticalVertexList.add (intermediateVertex);
		}

		return criticalVertexList;
	}

	/**
	 * There are N cities numbered from 1 to N.
	 * 
	 * You are given connections, where each connections[i] = [city1, city2, cost] represents the cost to
	 *  connect city1 and city2 together. (A connection is bidirectional: connecting city1 and city2 is the
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
		HashMap<String, Integer> edgeWeightMap = new HashMap<String, Integer>();

		HashMap<Integer, TreeMap<Integer, HashSet<Integer>>> vertexNeighborSetWeightMap =
			new HashMap<Integer, TreeMap<Integer, HashSet<Integer>>>();

		for (int[] weightedEdge : weightedEdgeArray) {
			if (!vertexNeighborSetWeightMap.containsKey (weightedEdge[0])) {
				vertexNeighborSetWeightMap.put (weightedEdge[0], new TreeMap<Integer, HashSet<Integer>>());
			}

			if (!vertexNeighborSetWeightMap.containsKey (weightedEdge[1])) {
				vertexNeighborSetWeightMap.put (weightedEdge[1], new TreeMap<Integer, HashSet<Integer>>());
			}

			edgeWeightMap.put (weightedEdge[0] + "_" + weightedEdge[1], weightedEdge[2]);

			edgeWeightMap.put (weightedEdge[1] + "_" + weightedEdge[0], weightedEdge[2]);
		}

		for (int[] weightedEdge : weightedEdgeArray) {
			TreeMap<Integer, HashSet<Integer>> weightSortedNeighborSet =
				vertexNeighborSetWeightMap.get (weightedEdge[0]);

			if (weightSortedNeighborSet.containsKey (weightedEdge[2])) {
				weightSortedNeighborSet.get (weightedEdge[2]).add (weightedEdge[1]);
			} else {
				HashSet<Integer> neighborSet = new HashSet<Integer>();

				neighborSet.add (weightedEdge[1]);

				weightSortedNeighborSet.put (weightedEdge[2], neighborSet);
			}

			weightSortedNeighborSet = vertexNeighborSetWeightMap.get (weightedEdge[1]);

			if (weightSortedNeighborSet.containsKey (weightedEdge[2])) {
				weightSortedNeighborSet.get (weightedEdge[2]).add (weightedEdge[0]);
			} else {
				HashSet<Integer> neighborSet = new HashSet<Integer>();

				neighborSet.add (weightedEdge[0]);

				weightSortedNeighborSet.put (weightedEdge[2], neighborSet);
			}
		}

		int cost = 0;
		int startingVertex = weightedEdgeArray[0][0];

		TreeMap<Integer, ArrayList<String>> edgeWeightPriorityQueue =
			new TreeMap<Integer, ArrayList<String>>();

		TreeMap<Integer, HashSet<Integer>> weightSortedNeighborSetMap =
			vertexNeighborSetWeightMap.get (startingVertex);

		if (weightSortedNeighborSetMap.isEmpty()) {
			return -1;
		}

		for (int edgeWeight : weightSortedNeighborSetMap.keySet()) {
			for (int neighborVertex : weightSortedNeighborSetMap.get (edgeWeight)) {
				if (edgeWeightPriorityQueue.containsKey (edgeWeight)) {
					edgeWeightPriorityQueue.get (edgeWeight).add (startingVertex + "_" + neighborVertex);
				} else {
					ArrayList<String> edgeList = new ArrayList<String>();

					edgeList.add (startingVertex + "_" + neighborVertex);

					edgeWeightPriorityQueue.put (edgeWeight, edgeList);
				}
			}
		}

		ArrayList<String> spanningEdgeList = new ArrayList<String>();

		HashSet<Integer> visitedVertexSet = new HashSet<Integer>();

		visitedVertexSet.add (startingVertex);

		while (!edgeWeightPriorityQueue.isEmpty()) {
			int shortestDistance = edgeWeightPriorityQueue.firstKey();

			ArrayList<String> edgeList = edgeWeightPriorityQueue.get (shortestDistance);

			String nextEdge = edgeList.remove (0);

			if (edgeList.isEmpty()) {
				edgeWeightPriorityQueue.remove (shortestDistance);
			}

			int nextVertex = Integer.parseInt (nextEdge.split ("_")[1]);

			if (!visitedVertexSet.contains (nextVertex)) {
				spanningEdgeList.add (nextEdge);

				visitedVertexSet.add (nextVertex);

				if ((weightSortedNeighborSetMap = vertexNeighborSetWeightMap.get (nextVertex)).isEmpty()) {
					continue;
				}

				for (int edgeWeight : weightSortedNeighborSetMap.keySet()) {
					for (int neighborVertex : weightSortedNeighborSetMap.get (edgeWeight)) {
						if (edgeWeightPriorityQueue.containsKey (edgeWeight)) {
							edgeWeightPriorityQueue.get (edgeWeight).add (nextVertex + "_" + neighborVertex);
						} else {
							(edgeList = new ArrayList<String>()).add (nextVertex + "_" + neighborVertex);

							edgeWeightPriorityQueue.put (edgeWeight, edgeList);
						}
					}
				}
			}
		}

		if (visitedVertexSet.size() < vertexNeighborSetWeightMap.size()) {
			return -1;
		}

		for (String edge : spanningEdgeList) {
			cost = cost + edgeWeightMap.get (edge);
		}

		return cost;
	}

	/**
	 * Entry Point
	 * 
	 * @param argumentArray The Argument Array
	 */

	public static final void main (
		final String[] argumentArray)
	{
		System.out.println (MSPCost (new int[][] {{1, 2, 5}, {1, 3, 6}, {2, 3, 1}}));

		System.out.println (MSPCost (new int[][] {{1, 2, 3}, {3, 4, 4}}));
	}
}
