
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
 * <i>MapUtil</i> implements Utility Functions based on Maps.
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

public class MapUtil
{

	/**
	 * Given two integers representing the numerator and denominator of a fraction, return the fraction in
	 *  string format.
	 *  
	 * If the fractional part is repeating, enclose the repeating part in parentheses.
	 * 
	 * @param numerator Numerator
	 * @param denominator Denominator
	 * 
	 * @return The Fraction in String Format
	 */

	public static final java.lang.String FractionToDecimal (
		int numerator,
		int denominator)
	{
		java.util.Map<java.lang.Integer, java.lang.Integer> repeatingFractionMap =
			new java.util.HashMap<java.lang.Integer, java.lang.Integer>();

		int index = 0;
		int remainer = numerator % denominator;
		java.lang.String fractionToDecimal = "" + (numerator / denominator);

		if (remainer == 0)
		{
			return fractionToDecimal;
		}

		fractionToDecimal = fractionToDecimal + ".";

		while (remainer != 0 &&
			!repeatingFractionMap.containsKey (
				remainer
			)
		)
		{
			int remainerTimes10 = remainer * 10;
			int quotient = remainerTimes10 / denominator;

			repeatingFractionMap.put (
				remainer,
				remainerTimes10 / denominator
			);

			remainer = remainerTimes10 % denominator;
			fractionToDecimal = fractionToDecimal + quotient;
		}

		if (0 == remainer)
		{
			return fractionToDecimal;
		}

		int remainerQuotient = repeatingFractionMap.get (
			remainer
		);

		int fractionToDecimalLength = fractionToDecimal.length();

		while (index < fractionToDecimalLength)
		{
			if (((int) fractionToDecimal.charAt (
				index
			) - '0') == remainerQuotient)
			{
				break;
			}

			index++;
		}

		return fractionToDecimal.substring (
			0,
			index
		) + "(" + fractionToDecimal.substring (
			index,
			fractionToDecimalLength
		) + ")";
	}

	public static final int LeastIntervalTaskScheduler (
		final char[] taskArray,
		final int coolOffInterval)
	{
		int leastIntervalTaskScheduler = 0;

		java.util.HashMap<java.lang.Character, java.lang.Integer> taskCountMap =
			new java.util.HashMap<java.lang.Character, java.lang.Integer>();

		java.util.HashMap<java.lang.Character, java.lang.Integer> taskCoolDownMap =
			new java.util.HashMap<java.lang.Character, java.lang.Integer>();

		for (char c : taskArray)
		{
			if (taskCountMap.containsKey(c))
			{
				taskCountMap.put(c, taskCountMap.get(c) + 1);
			}
			else
			{
				taskCountMap.put(c, 1);
			}

			taskCoolDownMap.put(c, 0);
		}

		java.util.TreeMap<java.lang.Integer, java.util.List<java.lang.Character>> taskFrequencyMap =
			new java.util.TreeMap<java.lang.Integer, java.util.List<java.lang.Character>>();

		for (java.util.HashMap.Entry<java.lang.Character, java.lang.Integer> taskCountMapEntry :
			taskCountMap.entrySet())
		{
			int freq = taskCountMapEntry.getValue();

			char task = taskCountMapEntry.getKey();

			if (!taskFrequencyMap.containsKey(freq))
			{
				java.util.List<java.lang.Character> freqList = new java.util.ArrayList<java.lang.Character>();

				freqList.add(task);

				taskFrequencyMap.put(freq, freqList);
			}
			else
			{
				taskFrequencyMap.get(freq).add(task);
			}
		}

		while (!taskFrequencyMap.isEmpty())
		{
			java.util.Set<java.lang.Integer> freqSet = taskFrequencyMap.descendingKeySet();

			int pickIndex = -1;
			int freqPicked = -1;
			char taskPicked = ' ';
			++leastIntervalTaskScheduler;

			for (int freq : freqSet)
			{
				pickIndex = 0;

				if (-1 != freqPicked)
				{
					break;
				}

				java.util.List<java.lang.Character> freqList = taskFrequencyMap.get(freq);

				for (char task : freqList)
				{
					if (0 == taskCoolDownMap.get(task))
					{
						taskPicked = task;
						freqPicked = freq;
						break;
					}

					++pickIndex;
				}
			}

			if (' ' != taskPicked)
			{
				java.util.List<java.lang.Character> taskList = taskFrequencyMap.get(freqPicked);

				taskList.remove(pickIndex);

				if (taskList.isEmpty())
				{
					taskFrequencyMap.remove(freqPicked);
				}

				freqPicked = freqPicked - 1;

				if (freqPicked > 0)
				{
					if (taskFrequencyMap.containsKey(freqPicked))
					{
						taskFrequencyMap.get(freqPicked).add(taskPicked);
					}
					else
					{
						java.util.List<java.lang.Character> freqPickedList =
							new java.util.ArrayList<java.lang.Character>();

						freqPickedList.add(taskPicked);

						taskFrequencyMap.put(freqPicked, freqPickedList);
					}
				}
			}

			for (java.util.Map.Entry<java.lang.Character, java.lang.Integer> taskCoolDownEntry :
				taskCoolDownMap.entrySet())
			{
				char c = taskCoolDownEntry.getKey();

				int coolDown = taskCoolDownEntry.getValue();

				if (c == taskPicked)
				{
					taskCoolDownMap.put(c, coolOffInterval);
				}
				else if (coolDown > 0)
				{
					taskCoolDownMap.put(c, coolDown - 1);
				}
			}
		}

		return leastIntervalTaskScheduler;
	}

	private static final boolean ElementsOverlap (
		final java.util.Set<java.lang.String> set1,
		final java.util.Set<java.lang.String> set2)
	{
		for (java.lang.String element1 : set1)
		{
			if (set2.contains (
				element1
			))
			{
				return true;
			}
		}

		return false;
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

	public static final java.util.Collection<java.util.Set<java.lang.String>>
		MinimumSetOfGroups (
			final java.util.Map<java.lang.String, java.util.Set<java.lang.String>> personInterestSetMap)
	{
		java.util.Map<java.lang.Integer, java.util.Set<java.lang.String>> groupMembershipMap =
			new java.util.HashMap<java.lang.Integer, java.util.Set<java.lang.String>>();

		java.util.Map<java.lang.Integer, java.util.Set<java.lang.String>> groupInterestMap =
			new java.util.HashMap<java.lang.Integer, java.util.Set<java.lang.String>>();

		java.util.TreeMap<java.lang.Integer, java.util.Set<java.lang.Integer>> groupCountMap =
			new java.util.TreeMap<java.lang.Integer, java.util.Set<java.lang.Integer>>();

		int groupIndex = 0;
		int nonOverlappingGroupIndex = -1;

		for (java.util.Map.Entry<java.lang.String, java.util.Set<java.lang.String>> personInterestSetEntry :
			personInterestSetMap.entrySet())
		{
			java.lang.String person = personInterestSetEntry.getKey();

			java.util.Set<java.lang.String> personInterestSet = personInterestSetEntry.getValue();

			if (groupCountMap.isEmpty())
			{
				java.util.Set<java.lang.Integer> groupIndexSet = new java.util.HashSet<java.lang.Integer>();

				groupIndexSet.add (
					groupIndex
				);

				groupCountMap.put (
					personInterestSet.size(),
					groupIndexSet
				);

				groupInterestMap.put (
					groupIndex,
					personInterestSet
				);

				java.util.Set<java.lang.String> personSet = new java.util.HashSet<java.lang.String>();

				personSet.add (
					person
				);

				groupMembershipMap.put (
					groupIndex,
					personSet
				);

				++groupIndex;
				continue;
			}

			java.util.Set<java.lang.Integer> groupCountKeySet = groupCountMap.descendingKeySet();

			for (int groupCount : groupCountKeySet)
			{
				if (-1 != nonOverlappingGroupIndex)
				{
					break;
				}

				java.util.Set<java.lang.Integer> groupIndexSet = groupCountMap.get (
					groupCount
				);

				for (int groupID : groupIndexSet)
				{
					if (!ElementsOverlap (
						personInterestSet,
						groupInterestMap.get (
							groupID
						)
					))
					{
						nonOverlappingGroupIndex = groupID;
					}
				}
			}

			if (-1 != nonOverlappingGroupIndex)
			{
				java.util.Set<java.lang.String> groupInterestSet = groupInterestMap.get (
					nonOverlappingGroupIndex
				);

				int oldGroupInterestSize = groupInterestSet.size();

				groupInterestSet.addAll (
					personInterestSet
				);

				int newGroupInterestSize = groupInterestSet.size();

				groupMembershipMap.get (
					nonOverlappingGroupIndex
				).add (
					person
				);

				groupCountMap.get (
					oldGroupInterestSize
				).remove (
					nonOverlappingGroupIndex
				);

				if (groupCountMap.containsKey (
					newGroupInterestSize
				))
				{
					groupCountMap.get (
						newGroupInterestSize
					).add (
						nonOverlappingGroupIndex
					);
				}
				else
				{
					java.util.Set<java.lang.Integer> groupCountSet =
						new java.util.HashSet<java.lang.Integer>();

					groupCountSet.add (
						nonOverlappingGroupIndex
					);

					groupCountMap.put (
						newGroupInterestSize,
						groupCountSet
					);
				}
			}
			else
			{
				java.util.Set<java.lang.String> groupInterestSet = new java.util.HashSet<java.lang.String>();

				groupInterestSet.addAll (
					personInterestSet
				);

				groupInterestMap.put (
					groupIndex,
					groupInterestSet
				);

				java.util.Set<java.lang.String> groupMembershipSet =
					new java.util.HashSet<java.lang.String>();

				groupMembershipSet.add (
					person
				);

				groupMembershipMap.put (
					groupIndex,
					groupMembershipSet
				);

				int groupInterestSize = groupInterestSet.size();

				if (groupCountMap.containsKey (
					groupInterestSize
				))
				{
					groupCountMap.get (
						groupInterestSize
					).add (
						groupIndex
					);
				}
				else
				{
					java.util.Set<java.lang.Integer> groupCountSet =
						new java.util.HashSet<java.lang.Integer>();

					groupCountSet.add (
						groupIndex
					);

					groupCountMap.put (
						groupInterestSize,
						groupCountSet
					);
				}

				++groupIndex;
			}
		}

		return groupMembershipMap.values();
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		java.util.Map<java.lang.String, java.util.Set<java.lang.String>> personInterestSetMap =
			new java.util.HashMap<java.lang.String, java.util.Set<java.lang.String>>();

		java.util.Set<java.lang.String> aInterestSet = new java.util.HashSet<java.lang.String>();

		aInterestSet.add (
			"lit"
		);

		aInterestSet.add (
			"math"
		);

		java.util.Set<java.lang.String> bInterestSet = new java.util.HashSet<java.lang.String>();

		bInterestSet.add (
			"math"
		);

		java.util.Set<java.lang.String> cInterestSet = new java.util.HashSet<java.lang.String>();

		cInterestSet.add (
			"sci"
		);

		personInterestSetMap.put (
			"a",
			aInterestSet
		);

		personInterestSetMap.put (
			"b",
			bInterestSet
		);

		personInterestSetMap.put (
			"c",
			cInterestSet
		);

		System.out.println (
			MinimumSetOfGroups (
				personInterestSetMap
			)
		);
	}
}
