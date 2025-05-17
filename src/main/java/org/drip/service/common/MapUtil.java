
package org.drip.service.common;

import java.util.ArrayList;
import java.util.Collection;
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
 * <i>MapUtil</i> implements Utility Functions based on Maps. It implements the following Functions:
 * <br>
 * <ul>
 * 		<li>Given two integers representing the numerator and denominator of a fraction, return the fraction in string format. If the fractional part is repeating, enclose the repeating part in parentheses</li>
 * 		<li>Identify the Least Interval Task Scheduler</li>
 * 		<li>There are <code>N</code> people. We have information about interests of each person. The task is to find minimum set of groups that these people can be placed into so there are no any 2 persons who share the same interests in one particular group. Each person can have infinitely large number of interests</li>
 * </ul>
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

public class MapUtil
{

	private static final boolean ElementsOverlap (
		final Set<String> set1,
		final Set<String> set2)
	{
		for (String element1 : set1) {
			if (set2.contains (element1)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Given two integers representing the numerator and denominator of a fraction, return the fraction in
	 *  string format. If the fractional part is repeating, enclose the repeating part in parentheses.
	 * 
	 * @param numerator Numerator
	 * @param denominator Denominator
	 * 
	 * @return The Fraction in String Format
	 */

	public static final String FractionToDecimal (
		int numerator,
		int denominator)
	{
		Map<Integer, Integer> repeatingFractionMap = new HashMap<Integer, Integer>();

		int index = 0;
		int remainer = numerator % denominator;
		String fractionToDecimal = "" + (numerator / denominator);

		if (0 == remainer) {
			return fractionToDecimal;
		}

		fractionToDecimal = fractionToDecimal + ".";

		while (0 != remainer && !repeatingFractionMap.containsKey (remainer)) {
			int remainerTimes10 = remainer * 10;
			int quotient = remainerTimes10 / denominator;

			repeatingFractionMap.put (remainer, remainerTimes10 / denominator);

			remainer = remainerTimes10 % denominator;
			fractionToDecimal = fractionToDecimal + quotient;
		}

		if (0 == remainer) {
			return fractionToDecimal;
		}

		int remainerQuotient = repeatingFractionMap.get (remainer);

		int fractionToDecimalLength = fractionToDecimal.length();

		while (index < fractionToDecimalLength) {
			if (((int) fractionToDecimal.charAt (index) - '0') == remainerQuotient) {
				break;
			}

			++index;
		}

		return fractionToDecimal.substring (0, index) + "(" +
			fractionToDecimal.substring (index, fractionToDecimalLength) + ")";
	}

	/**
	 * Identify the Least Interval Task Scheduler
	 * 
	 * @param taskArray The Array of Tasks
	 * @param coolOffInterval The Minimum Cool-off Interval
	 * 
	 * @return Least Interval Task Scheduler
	 */

	public static final int LeastIntervalTaskScheduler (
		final char[] taskArray,
		final int coolOffInterval)
	{
		int leastIntervalTaskScheduler = 0;

		HashMap<Character, Integer> taskCountMap = new HashMap<Character, Integer>();

		HashMap<Character, Integer> taskCoolDownMap = new HashMap<Character, Integer>();

		for (char c : taskArray) {
			taskCountMap.put (c, taskCountMap.containsKey (c) ? taskCountMap.get (c) + 1 : 1);

			taskCoolDownMap.put (c, 0);
		}

		TreeMap<Integer, List<Character>> taskFrequencyMap = new TreeMap<Integer, List<Character>>();

		for (HashMap.Entry<Character, Integer> taskCountMapEntry : taskCountMap.entrySet()) {
			int freq = taskCountMapEntry.getValue();

			char task = taskCountMapEntry.getKey();

			if (!taskFrequencyMap.containsKey (freq)) {
				List<Character> freqList = new ArrayList<Character>();

				freqList.add (task);

				taskFrequencyMap.put (freq, freqList);
			} else {
				taskFrequencyMap.get (freq).add (task);
			}
		}

		while (!taskFrequencyMap.isEmpty()) {
			Set<Integer> freqSet = taskFrequencyMap.descendingKeySet();

			++leastIntervalTaskScheduler;
			char taskPicked = ' ';
			int freqPicked = -1;
			int pickIndex = -1;

			for (int freq : freqSet) {
				pickIndex = 0;

				if (-1 != freqPicked) {
					break;
				}

				List<Character> freqList = taskFrequencyMap.get (freq);

				for (char task : freqList) {
					if (0 == taskCoolDownMap.get (task)) {
						taskPicked = task;
						freqPicked = freq;
						break;
					}

					++pickIndex;
				}
			}

			if (' ' != taskPicked) {
				List<Character> taskList = taskFrequencyMap.get (freqPicked);

				taskList.remove (pickIndex);

				if (taskList.isEmpty()) {
					taskFrequencyMap.remove (freqPicked);
				}

				freqPicked = freqPicked - 1;

				if (0 < freqPicked) {
					if (taskFrequencyMap.containsKey (freqPicked)) {
						taskFrequencyMap.get (freqPicked).add (taskPicked);
					} else {
						List<Character> freqPickedList = new ArrayList<Character>();

						freqPickedList.add (taskPicked);

						taskFrequencyMap.put (freqPicked, freqPickedList);
					}
				}
			}

			for (Map.Entry<Character, Integer> taskCoolDownEntry : taskCoolDownMap.entrySet()) {
				char c = taskCoolDownEntry.getKey();

				int coolDown = taskCoolDownEntry.getValue();

				if (c == taskPicked) {
					taskCoolDownMap.put (c, coolOffInterval);
				} else if (0 < coolDown) {
					taskCoolDownMap.put (c, coolDown - 1);
				}
			}
		}

		return leastIntervalTaskScheduler;
	}

	/**
	 * There are N people. We have information about interests of each person. The task is to find minimum
	 *  set of groups that these people can be placed into so there are no any 2 persons who share the same
	 *  interests in one particular group. Each person can have infinitely large number of interests.
	 * 
	 * @param personInterestSetMap The Person Interest Set Map
	 * 
	 * @return Minimum Set of Groups
	 */

	public static final Collection<Set<String>> MinimumSetOfGroups (
		final Map<String, Set<String>> personInterestSetMap)
	{
		Map<Integer, Set<String>> groupMembershipMap = new HashMap<Integer, Set<String>>();

		Map<Integer, Set<String>> groupInterestMap = new HashMap<Integer, Set<String>>();

		TreeMap<Integer, Set<Integer>> groupCountMap = new TreeMap<Integer, Set<Integer>>();

		int groupIndex = 0;
		int nonOverlappingGroupIndex = -1;

		for (Map.Entry<String, Set<String>> personInterestSetEntry : personInterestSetMap.entrySet()) {
			String person = personInterestSetEntry.getKey();

			Set<String> personInterestSet = personInterestSetEntry.getValue();

			if (groupCountMap.isEmpty()) {
				Set<Integer> groupIndexSet = new HashSet<Integer>();

				groupIndexSet.add (groupIndex);

				groupCountMap.put (personInterestSet.size(), groupIndexSet);

				groupInterestMap.put (groupIndex, personInterestSet);

				Set<String> personSet = new HashSet<String>();

				personSet.add (person);

				groupMembershipMap.put (groupIndex, personSet);

				++groupIndex;
				continue;
			}

			Set<Integer> groupCountKeySet = groupCountMap.descendingKeySet();

			for (int groupCount : groupCountKeySet) {
				if (-1 != nonOverlappingGroupIndex) {
					break;
				}

				Set<Integer> groupIndexSet = groupCountMap.get (groupCount);

				for (int groupID : groupIndexSet) {
					if (!ElementsOverlap (personInterestSet, groupInterestMap.get (groupID))) {
						nonOverlappingGroupIndex = groupID;
					}
				}
			}

			if (-1 != nonOverlappingGroupIndex) {
				Set<String> groupInterestSet = groupInterestMap.get (nonOverlappingGroupIndex);

				int oldGroupInterestSize = groupInterestSet.size();

				groupInterestSet.addAll (personInterestSet);

				int newGroupInterestSize = groupInterestSet.size();

				groupMembershipMap.get (nonOverlappingGroupIndex).add (person);

				groupCountMap.get (oldGroupInterestSize).remove (nonOverlappingGroupIndex);

				if (groupCountMap.containsKey (newGroupInterestSize)) {
					groupCountMap.get (newGroupInterestSize).add (nonOverlappingGroupIndex);
				} else {
					Set<Integer> groupCountSet = new HashSet<Integer>();

					groupCountSet.add (nonOverlappingGroupIndex);

					groupCountMap.put (newGroupInterestSize, groupCountSet);
				}
			} else {
				Set<String> groupInterestSet = new HashSet<String>();

				groupInterestSet.addAll (personInterestSet);

				groupInterestMap.put (groupIndex, groupInterestSet);

				Set<String> groupMembershipSet = new HashSet<String>();

				groupMembershipSet.add (person);

				groupMembershipMap.put (groupIndex, groupMembershipSet);

				int groupInterestSize = groupInterestSet.size();

				if (groupCountMap.containsKey (groupInterestSize)) {
					groupCountMap.get (groupInterestSize).add (groupIndex);
				} else {
					Set<Integer> groupCountSet = new HashSet<Integer>();

					groupCountSet.add (groupIndex);

					groupCountMap.put (groupInterestSize, groupCountSet);
				}

				++groupIndex;
			}
		}

		return groupMembershipMap.values();
	}
}
