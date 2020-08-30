
package org.drip.service.common;

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

	public static final void main (
		final String[] argumentArray)
	{
		java.util.ArrayList<String> itemList1 = new java.util.ArrayList<String>();

		itemList1.add ("product1");

		itemList1.add ("product2");

		itemList1.add ("product3");

		java.util.ArrayList<String> itemList2 = new java.util.ArrayList<String>();

		itemList2.add ("product5");

		itemList2.add ("product2");

		java.util.ArrayList<String> itemList3 = new java.util.ArrayList<String>();

		itemList3.add ("product6");

		itemList3.add ("product7");

		java.util.ArrayList<String> itemList4 = new java.util.ArrayList<String>();

		itemList4.add ("product8");

		itemList4.add ("product7");

		java.util.List<java.util.List<String>> itemListSequence = new
			java.util.ArrayList<java.util.List<String>>();

		itemListSequence.add (itemList1);

		itemListSequence.add (itemList2);

		itemListSequence.add (itemList3);

		itemListSequence.add (itemList4);

		System.out.println (LargestGroup (itemListSequence));
	}
}
