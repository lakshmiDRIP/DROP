
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
 * <i>RecursionUtil</i> implements Recursion Utility Functions. It implements the following Functions:
 * 
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/common/README.md">Assorted Data Structures Support Utilities</a></td></tr>
 *  </table>
 * <br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RecursionUtil
{

	private static final String NthRow (
		final int n)
	{
		if (1 == n) {
			return "0";
		}

		String nthRow = "";

		String prevRow = NthRow (n - 1);

		for (int i = 0; i < prevRow.length(); ++i) {
			char c = prevRow.charAt (i);

			if ('0' == c) {
				nthRow = nthRow + "01";
			} else if ('1' == c) {
				nthRow = nthRow + "10";
			}
		}

		return nthRow;
	}

	private static final boolean SubString (
		List<String> subSequenceList,
		final char[] charArray,
		final int currentIndex)
	{
		if (currentIndex == charArray.length) {
			return true;
		}

		List<String> subSequenceListCurrent = new ArrayList<String>();

		for (String sequence : subSequenceList) {
			subSequenceListCurrent.add (sequence + charArray[currentIndex]);
		}

		subSequenceList.addAll (subSequenceListCurrent);

		subSequenceList.add ("" + charArray[currentIndex]);

		return SubString (subSequenceList, charArray, currentIndex + 1);
	}

	private static final char[] PhoneCharArray (
		final int digit)
	{
		if (0 == digit || 1 == digit) {
			return new char[] {};
		}

		if (2 == digit) {
			return new char[] {'A', 'B', 'C',};
		}

		if (3 == digit) {
			return new char[] {'D', 'E', 'F',};
		}

		if (4 == digit) {
			return new char[] {'G', 'H', 'I',};
		}

		if (5 == digit) {
			return new char[] {'J', 'K', 'L',};
		}

		if (6 == digit) {
			return new char[] {'M', 'N', 'O',};
		}

		if (7 == digit) {
			return new char[] {'P', 'Q', 'R', 'S',};
		}

		if (8 == digit) {
			return new char[] {'T', 'U', 'V',};
		}

		return new char[] {'W', 'X', 'Y', 'Z',};
	}

	private static final boolean PhoneCharList (
		List<String> phoneCharList,
		final int[] digitArray,
		final int currentIndex)
	{
		if (currentIndex == digitArray.length) {
			return true;
		}

		List<String> phoneCharCurrentList = new ArrayList<String>();

		for (char c : PhoneCharArray (digitArray[currentIndex])) {
			if (phoneCharList.isEmpty()) {
				phoneCharCurrentList.add ("" + c);
			} else {
				for (String phoneString : phoneCharList) {
					phoneCharCurrentList.add (phoneString + c);
				}
			}
		}

		if (!phoneCharCurrentList.isEmpty()) {
			phoneCharList.clear();

			phoneCharList.addAll (phoneCharCurrentList);
		}

		return PhoneCharList (phoneCharList, digitArray, currentIndex + 1);
	}

	/**
	 * On the first row, we write a 0. Now in every subsequent row, we look at the previous row and replace
	 *  each occurrence of 0 with 01, and each occurrence of 1 with 10.
	 *  
	 * Given row N and index K, return the K-th indexed symbol in row N. (The values of K are 1-indexed.)
	 * 
	 * @param n N
	 * @param k K
	 * 
	 * @return The Kth Grammar Integer
	 */

	public static final int KthGrammar (
		final int n,
		final int k)
	{
		String nthRow = NthRow (n);

		return nthRow.length() < k ? -1 : nthRow.charAt (k - 1) - '0';
	}

	/**
	 * Generate the Set of Sub-sequence Strings
	 * 
	 * @param s Input String
	 * 
	 * @return The Set of Sub-sequence Strings
	 */

	public static final List<String> SubSequenceList (
		final String s)
	{
		List<String> subSequenceList = new ArrayList<String>();

		SubString (subSequenceList, s.toCharArray(), 0);

		return subSequenceList;
	}

	/**
	 * Given a list of strings, you need to find the longest uncommon subsequence among them. The longest
	 * 	uncommon subsequence is defined as the longest subsequence of one of these strings and this
	 *  subsequence should not be any subsequence of the other strings.
	 *  
	 * A subsequence is a sequence that can be derived from one sequence by deleting some characters without
	 * 	changing the order of the remaining elements. Trivially, any string is a subsequence of itself and an
	 *  empty string is a subsequence of any string.
	 *  
	 * The input will be a list of strings, and the output needs to be the length of the longest uncommon
	 *  subsequence. If the longest uncommon subsequence doesn't exist, return -1.
	 *  
	 * @param stringArray Input String Array
	 * 
	 * @return Length of the Longest Uncommon Sub-sequence Length
	 */

	public static final int LongestUncommonSubsequenceLength (
		final String[] stringArray)
	{
		Map<String, Integer> sequenceMap =
			new HashMap<String, Integer>();

		for (String string : stringArray)
		{
			List<String> subSequenceList = SubSequenceList (
				string
			);

			for (String sequence : subSequenceList)
			{
				if (!sequenceMap.containsKey (
					sequence
				))
				{
					sequenceMap.put (
						sequence,
						1
					);
				}
				else
				{
					sequenceMap.put (
						sequence,
						sequenceMap.get (
							sequence
						) + 1
					);
				}
			}
		}

		TreeMap<Integer, List<String>> sequenceCountMap =
			new TreeMap<Integer, List<String>>();

		for (Map.Entry<String, Integer> sequenceMapEntry :
			sequenceMap.entrySet())
		{
			int count = sequenceMapEntry.getValue();

			String sequence = sequenceMapEntry.getKey();

			if (!sequenceCountMap.containsKey (
				count
			))
			{
				List<String> countList =
					new ArrayList<String>();

				countList.add (
					sequence
				);

				sequenceCountMap.put (
					count,
					countList
				);
			}
			else
			{
				sequenceCountMap.get (
					count
				).add (
					sequence
				);
			}
		}

		int longestLength = 0;

		for (String s : sequenceCountMap.firstEntry().getValue())
		{
			int length = s.length();

			if (length > longestLength)
			{
				longestLength = length;
			}
		}

		return longestLength;
	}

	private static final int NumberSequenceSum (
		final int[] numberArray,
		final Set<Integer> numberIndexSequence)
	{
		int sum = 0;

		for (int numberIndex : numberIndexSequence)
		{
			sum = sum + numberArray[numberIndex];
		}

		return sum;
	}

	private static final boolean RemoveFulfilledIndex (
		final boolean[] fulfilledArray,
		final List<Set<Integer>> numberIndexSequenceList,
		final List<Integer> removalIndexList)
	{
		for (int removalIndex : removalIndexList)
		{
			fulfilledArray[removalIndex] = true;

			for (Set<Integer> numberIndexSequence : numberIndexSequenceList)
			{
				for (int numberIndex : numberIndexSequence)
				{
					if (removalIndex == numberIndex)
					{
						numberIndexSequence.remove (
							numberIndex
						);
					}
				}
			}
		}

		return true;
	}

	private static final boolean CanPartitionKSubsets (
		final boolean[] fulfilledArray,
		final List<Set<Integer>> numberIndexSequenceList,
		int remainingPartitionCount,
		final int currentIndex,
		final int[] numberArray,
		final int target)
	{
		int arrayLength = numberArray.length;

		if (currentIndex == arrayLength)
		{
			if (0 != remainingPartitionCount)
			{
				return false;
			}

			for (int index = 0;
				index < arrayLength;
				++index)
			{
				if (!fulfilledArray[index])
				{
					return false;
				}
			}

			return true;
		}

		int currentNumber = numberArray[currentIndex];

		if (currentNumber == target)
		{
			fulfilledArray[currentIndex] = true;
			--remainingPartitionCount;
		}
		else
		{
			for (Set<Integer> numberIndexSequence : numberIndexSequenceList)
			{
				int numberSequenceSum = NumberSequenceSum (
					numberArray,
					numberIndexSequence
				);

				if (target == currentNumber + numberSequenceSum)
				{
					List<Integer> removalIndexList =
						new ArrayList<Integer>();

					removalIndexList.addAll (
						numberIndexSequence
					);

					removalIndexList.add (
						currentIndex
					);

					RemoveFulfilledIndex (
						fulfilledArray,
						numberIndexSequenceList,
						removalIndexList
					);

					--remainingPartitionCount;
				}
			}

			for (int listIndex = 0;
				listIndex < numberIndexSequenceList.size();
				++listIndex)
			{
				Set<Integer> numberIndexSequence = numberIndexSequenceList.get (
					listIndex
				);

				if (0 == numberIndexSequence.size())
				{
					numberIndexSequenceList.remove (
						listIndex
					);
				}
			}
		}

		if (!fulfilledArray[currentIndex])
		{
			List<Set<Integer>> numberIndexSequenceCurrentList =
				new ArrayList<Set<Integer>>();

				for (Set<Integer> numberIndexSequence : numberIndexSequenceList)
				{
					Set<Integer> numberIndexSequenceCurrent =
						new HashSet<Integer>();

					numberIndexSequenceCurrent.addAll (
						numberIndexSequence
					);

					numberIndexSequenceCurrent.add (
						currentIndex
					);

					numberIndexSequenceCurrentList.add (
						numberIndexSequenceCurrent
					);
				}

			numberIndexSequenceList.addAll (
				numberIndexSequenceCurrentList
			);

			Set<Integer> currentIndexSequence =
				new HashSet<Integer>();

			currentIndexSequence.add (
				currentIndex
			);

			numberIndexSequenceList.add (
				currentIndexSequence
			);
		}

		return CanPartitionKSubsets (
			fulfilledArray,
			numberIndexSequenceList,
			remainingPartitionCount,
			currentIndex + 1,
			numberArray,
			target
		);
	}

	/**
	 * Generate all the Words corresponding to the Specified Digits
	 * 
	 * @param phoneNumber The Digits of Phone Number
	 * 
	 * @return Words corresponding to the Specified Digits
	 */

	public static final List<String> PhoneCharList (
		final int[] phoneNumber)
	{
		List<String> phoneCharList = new ArrayList<String>();

		PhoneCharList (
			phoneCharList,
			phoneNumber,
			0
		);

		return phoneCharList;
	}

	/**
	 * Given an array of integers and a positive integer k, find whether it's possible to divide this array
	 * 	into k non-empty subsets whose sums are all equal.
	 * 
	 * @param numberArray The Number Array
	 * @param partitionCount The Partition Count
	 * 
	 * @return TRUE - It is possible to divide this array into k non-empty subsets whose sums are all equal.
	 */

	public static final boolean CanPartitionKSubsets (
		final int[] numberArray,
		final int partitionCount)
	{
		int sum = 0;
		int arrayCount = numberArray.length;
		boolean[] fulfilledArray = new boolean[arrayCount];

		List<Set<Integer>> numberIndexSequenceList =
			new ArrayList<Set<Integer>>();

		for (int arrayIndex = 0;
			arrayIndex < arrayCount;
			++arrayIndex)
		{
			fulfilledArray[arrayIndex] = false;
			sum = sum + numberArray[arrayIndex];
		}

		return CanPartitionKSubsets (
			fulfilledArray,
			numberIndexSequenceList,
			partitionCount,
			0,
			numberArray,
			sum / 4
		);
	}

	/**
	 * Generate the Set of n Parenthesis
	 * 
	 * @param n n
	 * 
	 * @return The Set of n Parenthesis
	 */

	public static final Set<String> GenerateParenthesis (
		final int n)
	{
		Set<String> parenthesisSet = new HashSet<String>();

		if (1 == n)
		{
			parenthesisSet.add (
				"()"
			);

			return parenthesisSet;
		}

		for (int outerIndex = 1;
			outerIndex < n;
			++outerIndex)
		{
			String levelLeftParenthesis = "";
			String levelRightParenthesis = "";

			for (int levelIndex = 1;
				levelIndex <= outerIndex;
				++levelIndex
			)
			{
				levelLeftParenthesis = levelLeftParenthesis + "(";
				levelRightParenthesis = levelRightParenthesis + ")";
			}

			Set<String> innerParenthesisSet = GenerateParenthesis (
				n - outerIndex
			);

			for (String innerParenthesis : innerParenthesisSet)
			{
				parenthesisSet.add (
					levelLeftParenthesis + innerParenthesis + levelRightParenthesis
				);
			}
		}

		for (int leftLevel = 1;
			leftLevel < n;
			++leftLevel
		)
		{
			Set<String> leftParenthesisList = GenerateParenthesis (
				leftLevel
			);

			Set<String> rightParenthesisList = GenerateParenthesis (
				n - leftLevel
			);

			for (String leftParenthesis : leftParenthesisList)
			{
				for (String rightParenthesis : rightParenthesisList)
				{
					parenthesisSet.add (
						leftParenthesis + rightParenthesis
					);
				}
			}
		}

		return parenthesisSet;
	}

	/**
	 * Calculate the Size of the Shortest Path through the Maze
	 * 
	 * @param maze The Maze
	 * 
	 * @return Size of the Shortest Path through the Maze
	 */

	public static final int ShortestPathSize (
		final int[][] maze)
	{
		Set<String> visitedLocationSet = new HashSet<String>();

		List<int[]> navigationList = new ArrayList<int[]>();

		int minPathSize = Integer.MAX_VALUE;

		navigationList.add(new int[] {0, 0, 0});

		int yCount = maze[0].length;
		int xCount = maze.length;

		while (!navigationList.isEmpty())
		{
			int[] locationPathCount = navigationList.remove(0);

			int x = locationPathCount[0];
			int y = locationPathCount[1];

			visitedLocationSet.add(x + "_" + y);

			if (x == xCount - 1 && y == yCount - 1)
			{
				minPathSize = minPathSize < locationPathCount[2] ? minPathSize : locationPathCount[2];
				continue;
			}

			int xUp = x + 1;
			int xDown = x - 1;
			int yLeft = y - 1;
			int yRight = y + 1;

			if (xDown >= 0 && 0 != maze[xDown][y] && !visitedLocationSet.contains(xDown + "_" + y))
			{
				navigationList.add(new int[] {xDown, y, locationPathCount[2] + 1});
			}

			if (xUp < xCount && 0 != maze[xUp][y] && !visitedLocationSet.contains(xUp + "_" + y))
			{
				navigationList.add(new int[] {xUp, y, locationPathCount[2] + 1});
			}

			if (yLeft >= 0 && 0 != maze[x][yLeft] && !visitedLocationSet.contains(x + "_" + yLeft))
			{
				navigationList.add(new int[] {x, yLeft, locationPathCount[2] + 1});
			}

			if (yRight < yCount && 0 != maze[x][yRight] && !visitedLocationSet.contains(x + "_" + yRight))
			{
				navigationList.add(new int[] {x, yRight, locationPathCount[2] + 1});
			}
		}

		return minPathSize;
	}

	/**
	 * Restore the IP Address in the String
	 * 
	 * @param s The String
	 * 
	 * @return IP Address in the String
	 */

	public static final List<String> RestoreIPAddresses (
		final String s)
	{
		List<String> ipAddressList = new ArrayList<String>();

		List<List<Integer>> ipSubnetQueue =
			new ArrayList<List<Integer>>();

		List<Integer> ipSubnetQueueStartEntry =
			new ArrayList<Integer>();

		ipSubnetQueueStartEntry.add(0);

		ipSubnetQueue.add(ipSubnetQueueStartEntry);

		while (!ipSubnetQueue.isEmpty())
		{
			List<Integer> ipSubnetQueueEntry = ipSubnetQueue.remove(0);

			int currentIndex = ipSubnetQueueEntry.get(0);

			int listSize = ipSubnetQueueEntry.size();

			if (5 == listSize)
			{
				if (currentIndex == s.length())
				{
					ipAddressList.add (
						ipSubnetQueueEntry.get(1) + "." + ipSubnetQueueEntry.get(2) +
						"." + ipSubnetQueueEntry.get(3) + "." + ipSubnetQueueEntry.get(4)
					);
				}

				continue;
			}

			int endIndex = currentIndex;

			while (endIndex < s.length())
			{
				int value = StringUtil.DecimalNumberFromString (s.substring(currentIndex, endIndex + 1));

				if (value > 255)
				{
					break;
				}

				List<Integer> ipSubnetQueueNextEntry =
					new ArrayList<Integer>();

				ipSubnetQueueNextEntry.add(endIndex + 1);

				for (int i = 1; i < listSize; ++i)
				{
					ipSubnetQueueNextEntry.add(ipSubnetQueueEntry.get(i));
				}

				ipSubnetQueueNextEntry.add(value);

				ipSubnetQueue.add(ipSubnetQueueNextEntry);

				++endIndex;
			}
		}

		return ipAddressList;
	}

	private static final char[] AlphabetArray()
	{
		char[] alphabetArray = new char[26];
		alphabetArray[0] = 'a';
		alphabetArray[1] = 'b';
		alphabetArray[2] = 'c';
		alphabetArray[3] = 'd';
		alphabetArray[4] = 'e';
		alphabetArray[5] = 'f';
		alphabetArray[6] = 'g';
		alphabetArray[7] = 'h';
		alphabetArray[8] = 'i';
		alphabetArray[9] = 'j';
		alphabetArray[10] = 'k';
		alphabetArray[11] = 'l';
		alphabetArray[12] = 'm';
		alphabetArray[13] = 'n';
		alphabetArray[14] = 'o';
		alphabetArray[15] = 'p';
		alphabetArray[16] = 'q';
		alphabetArray[17] = 'r';
		alphabetArray[18] = 's';
		alphabetArray[19] = 't';
		alphabetArray[20] = 'u';
		alphabetArray[21] = 'v';
		alphabetArray[22] = 'w';
		alphabetArray[23] = 'x';
		alphabetArray[24] = 'y';
		alphabetArray[25] = 'z';
		return alphabetArray;
	}

	/**
	 * Calculate the smallest Number of changes needed to make the begin word to the end using the Words in
	 * 	the Dictionary
	 * 
	 * @param beginWord The Begin Word
	 * @param endWord The End Word
	 * @param wordSet Dictionary
	 * 
	 * @return Smallest Number of changes needed
	 */

	public static final int WordLadderLength (
		final String beginWord,
		final String endWord,
		final Set<String> wordSet)
	{
		List<String> wordProcessQueue = new ArrayList<String>();

		Set<String> visitedWordSet = new HashSet<String>();

		int length = Integer.MAX_VALUE;

		char[] alphabetArray = AlphabetArray();

		wordProcessQueue.add(beginWord + "0");

		int wordLength = beginWord.length();

		while (!wordProcessQueue.isEmpty())
		{
			String wordProcessQueueEntry = wordProcessQueue.remove (0);

			int entrySize = wordProcessQueueEntry.length();

			int count = StringUtil.DecimalNumberFromString (
				wordProcessQueueEntry.substring(wordLength + 1, entrySize)
			);

			String word = wordProcessQueueEntry.substring (0, wordLength);

			visitedWordSet.add(word);

			if (word.equals(endWord))
			{
				if (length > count + 1)
				{
					length = count + 1;
				}

				continue;
			}

			char[] wordCharArray = word.toCharArray();

			for (int charIndex = 0; charIndex < wordCharArray.length; ++charIndex)
			{
				char[] nextWordCharArray = new char[wordCharArray.length];

				for (int newCharIndex = 0; newCharIndex < wordCharArray.length; ++newCharIndex)
				{
					nextWordCharArray[newCharIndex] =
						newCharIndex == charIndex ? ' ' : wordCharArray[newCharIndex];
				}

				for (char c : alphabetArray)
				{
					nextWordCharArray[charIndex] = c;

					String newWord = new String (nextWordCharArray);

					if (wordSet.contains (newWord) && !visitedWordSet.contains(newWord))
					{
						wordProcessQueue.add(newWord + (count + 1));
					}
				}
			}
		}

		return length;
	}

	private static final List<Integer> SmallestPerfectSquareSet (
		final HashMap<Integer, List<Integer>> memoizeMap,
		final int number)
	{
		// if (memoizeMap.containsKey (number)) return memoizeMap.get (number);

		List<Integer> squaresList = new ArrayList<Integer>();

		double sqrt = Math.sqrt (number);

		if (0. == (sqrt - (int) sqrt)) {
			squaresList.add ((int) sqrt);

			memoizeMap.put (number, squaresList);

			return squaresList;
		}

		(squaresList = SmallestPerfectSquareSet (memoizeMap, 1)).addAll (SmallestPerfectSquareSet
			(memoizeMap, number - 1));

		for (int i = 2; i <= (int) sqrt; ++i) {
			List<Integer> squaresSubList = SmallestPerfectSquareSet (memoizeMap, i * i);

			squaresSubList.addAll (SmallestPerfectSquareSet (memoizeMap, number - i * i));

			if (squaresSubList.size() <= squaresList.size()) squaresList = squaresSubList;
		}

		memoizeMap.put (number, squaresList);

		return squaresList;
	}

	/**
	 * Generate the List of Smallest Perfect Squares that add to the given Number
	 * 
	 * @param number The Number
	 * 
	 * @return The List of Smallest Perfect Squares that add to the given Number
	 */

	public static final List<Integer> SmallestPerfectSquareSet (
		final int number)
	{
		HashMap<Integer, List<Integer>> memoizeMap = new
			HashMap<Integer, List<Integer>>();

		List<Integer> squaresList = new ArrayList<Integer>();

		squaresList.add (1);

		memoizeMap.put (1, squaresList);

		List<Integer> smallestPerfectSquareSet = SmallestPerfectSquareSet (memoizeMap, number);

		return smallestPerfectSquareSet;
	}

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Array of Arguments
	 * 
	 * @throws Exception The Exception encountered
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		System.out.println (SmallestPerfectSquareSet (12));
	}
}
