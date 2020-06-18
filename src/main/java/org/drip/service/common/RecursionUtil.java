
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
 * <i>RecursionUtil</i> implements Recursion Utility Functions.
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

public class RecursionUtil
{

	private static final java.lang.String NthRow (
		final int n)
	{
		if (1 == n)
		{
			return "0";
		}

		java.lang.String nthRow = "";

		java.lang.String prevRow = NthRow (
			n - 1
		);

		for (int i = 0;
			i < prevRow.length();
			++i)
		{
			char c = prevRow.charAt (
				i
			);

			if ('0' == c)
			{
				nthRow = nthRow + "01";
			}
			else if ('1' == c)
			{
				nthRow = nthRow + "10";
			}
		}

		return nthRow;
	}

	private static final boolean SubString (
		java.util.List<java.lang.String> subSequenceList,
		final char[] charArray,
		final int currentIndex)
	{
		if (currentIndex == charArray.length)
		{
			return true;
		}

		java.util.List<java.lang.String> subSequenceListCurrent =
			new java.util.ArrayList<java.lang.String>();

		for (java.lang.String sequence : subSequenceList)
		{
			subSequenceListCurrent.add (
				sequence + charArray[currentIndex]
			);
		}

		subSequenceList.addAll (
			subSequenceListCurrent
		);

		subSequenceList.add (
			"" + charArray[currentIndex]
		);

		return SubString (
			subSequenceList,
			charArray,
			currentIndex + 1
		);
	}

	private static final char[] PhoneCharArray (
		final int digit)
	{
		if (0 == digit ||
			1 == digit
		)
		{
			return new char[]
			{
			};
		}

		if (2 == digit)
		{
			return new char[]
			{
				'A',
				'B',
				'C',
			};
		}

		if (3 == digit)
		{
			return new char[]
			{
				'D',
				'E',
				'F',
			};
		}

		if (4 == digit)
		{
			return new char[]
			{
				'G',
				'H',
				'I',
			};
		}

		if (5 == digit)
		{
			return new char[]
			{
				'J',
				'K',
				'L',
			};
		}

		if (6 == digit)
		{
			return new char[]
			{
				'M',
				'N',
				'O',
			};
		}

		if (7 == digit)
		{
			return new char[]
			{
				'P',
				'Q',
				'R',
				'S',
			};
		}

		if (8 == digit)
		{
			return new char[]
			{
				'T',
				'U',
				'V',
			};
		}

		return new char[]
		{
			'W',
			'X',
			'Y',
			'Z',
		};
	}

	private static final boolean PhoneCharList (
		java.util.List<java.lang.String> phoneCharList,
		final int[] digitArray,
		final int currentIndex)
	{
		if (currentIndex == digitArray.length)
		{
			return true;
		}

		java.util.List<java.lang.String> phoneCharCurrentList = new java.util.ArrayList<java.lang.String>();

		for (char c : PhoneCharArray (
			digitArray[currentIndex]
		))
		{
			if (phoneCharList.isEmpty())
			{
				phoneCharCurrentList.add (
					"" + c
				);
			}
			else
			{
				for (java.lang.String phoneString : phoneCharList)
				{
					phoneCharCurrentList.add (
						phoneString + c
					);
				}
			}
		}

		if (!phoneCharCurrentList.isEmpty())
		{
			phoneCharList.clear();

			phoneCharList.addAll (
				phoneCharCurrentList
			);
		}

		return PhoneCharList (
			phoneCharList,
			digitArray,
			currentIndex + 1
		);
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
		int n,
		int k)
	{
		java.lang.String nthRow = NthRow (
			n
		);

		return nthRow.length() < k ? -1 : nthRow.charAt (
			k - 1
		) - '0';
	}

	/**
	 * Generate the Set of Sub-sequence Strings
	 * 
	 * @param s Input String
	 * 
	 * @return The Set of Sub-sequence Strings
	 */

	public static final java.util.List<java.lang.String> SubSequenceList (
		final java.lang.String s)
	{
		java.util.List<java.lang.String> subSequenceList = new java.util.ArrayList<java.lang.String>();

		SubString (
			subSequenceList,
			s.toCharArray(),
			0
		);

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
		final java.lang.String[] stringArray)
	{
		java.util.Map<java.lang.String, java.lang.Integer> sequenceMap =
			new java.util.HashMap<java.lang.String, java.lang.Integer>();

		for (java.lang.String string : stringArray)
		{
			java.util.List<java.lang.String> subSequenceList = SubSequenceList (
				string
			);

			for (java.lang.String sequence : subSequenceList)
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

		java.util.TreeMap<java.lang.Integer, java.util.List<java.lang.String>> sequenceCountMap =
			new java.util.TreeMap<java.lang.Integer, java.util.List<java.lang.String>>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Integer> sequenceMapEntry :
			sequenceMap.entrySet())
		{
			int count = sequenceMapEntry.getValue();

			java.lang.String sequence = sequenceMapEntry.getKey();

			if (!sequenceCountMap.containsKey (
				count
			))
			{
				java.util.List<java.lang.String> countList =
					new java.util.ArrayList<java.lang.String>();

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

		for (java.lang.String s : sequenceCountMap.firstEntry().getValue())
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
		final java.util.Set<java.lang.Integer> numberIndexSequence)
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
		final java.util.List<java.util.Set<java.lang.Integer>> numberIndexSequenceList,
		final java.util.List<java.lang.Integer> removalIndexList)
	{
		for (int removalIndex : removalIndexList)
		{
			fulfilledArray[removalIndex] = true;

			for (java.util.Set<java.lang.Integer> numberIndexSequence : numberIndexSequenceList)
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
		final java.util.List<java.util.Set<java.lang.Integer>> numberIndexSequenceList,
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
			for (java.util.Set<java.lang.Integer> numberIndexSequence : numberIndexSequenceList)
			{
				int numberSequenceSum = NumberSequenceSum (
					numberArray,
					numberIndexSequence
				);

				if (target == currentNumber + numberSequenceSum)
				{
					java.util.List<java.lang.Integer> removalIndexList =
						new java.util.ArrayList<java.lang.Integer>();

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
				java.util.Set<java.lang.Integer> numberIndexSequence = numberIndexSequenceList.get (
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
			java.util.List<java.util.Set<java.lang.Integer>> numberIndexSequenceCurrentList =
				new java.util.ArrayList<java.util.Set<java.lang.Integer>>();

				for (java.util.Set<java.lang.Integer> numberIndexSequence : numberIndexSequenceList)
				{
					java.util.Set<java.lang.Integer> numberIndexSequenceCurrent =
						new java.util.HashSet<java.lang.Integer>();

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

			java.util.Set<java.lang.Integer> currentIndexSequence =
				new java.util.HashSet<java.lang.Integer>();

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

	public static final java.util.List<java.lang.String> PhoneCharList (
		final int[] phoneNumber)
	{
		java.util.List<java.lang.String> phoneCharList = new java.util.ArrayList<java.lang.String>();

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

		java.util.List<java.util.Set<java.lang.Integer>> numberIndexSequenceList =
			new java.util.ArrayList<java.util.Set<java.lang.Integer>>();

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

	public static final java.util.Set<java.lang.String> GenerateParenthesis (
		final int n)
	{
		java.util.Set<java.lang.String> parenthesisSet = new java.util.HashSet<java.lang.String>();

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
			java.lang.String levelLeftParenthesis = "";
			java.lang.String levelRightParenthesis = "";

			for (int levelIndex = 1;
				levelIndex <= outerIndex;
				++levelIndex
			)
			{
				levelLeftParenthesis = levelLeftParenthesis + "(";
				levelRightParenthesis = levelRightParenthesis + ")";
			}

			java.util.Set<java.lang.String> innerParenthesisSet = GenerateParenthesis (
				n - outerIndex
			);

			for (java.lang.String innerParenthesis : innerParenthesisSet)
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
			java.util.Set<java.lang.String> leftParenthesisList = GenerateParenthesis (
				leftLevel
			);

			java.util.Set<java.lang.String> rightParenthesisList = GenerateParenthesis (
				n - leftLevel
			);

			for (java.lang.String leftParenthesis : leftParenthesisList)
			{
				for (java.lang.String rightParenthesis : rightParenthesisList)
				{
					parenthesisSet.add (
						leftParenthesis + rightParenthesis
					);
				}
			}
		}

		return parenthesisSet;
	}

	public static final int ShortestPathSize (
		final int[][] maze)
	{
		java.util.Set<java.lang.String> visitedLocationSet = new java.util.HashSet<java.lang.String>();

		java.util.List<int[]> navigationList = new java.util.ArrayList<int[]>();

		int minPathSize = java.lang.Integer.MAX_VALUE;

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

	public static final java.util.List<java.lang.String> RestoreIPAddresses (
		final java.lang.String s)
	{
		java.util.List<java.lang.String> ipAddressList = new java.util.ArrayList<java.lang.String>();

		java.util.List<java.util.List<java.lang.Integer>> ipSubnetQueue =
			new java.util.ArrayList<java.util.List<java.lang.Integer>>();

		java.util.List<java.lang.Integer> ipSubnetQueueStartEntry =
			new java.util.ArrayList<java.lang.Integer>();

		ipSubnetQueueStartEntry.add(0);

		ipSubnetQueue.add(ipSubnetQueueStartEntry);

		while (!ipSubnetQueue.isEmpty())
		{
			java.util.List<java.lang.Integer> ipSubnetQueueEntry = ipSubnetQueue.remove(0);

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

				java.util.List<java.lang.Integer> ipSubnetQueueNextEntry =
					new java.util.ArrayList<java.lang.Integer>();

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

	public static final int WordLadderLength (
		final java.lang.String beginWord,
		final java.lang.String endWord,
		final java.util.Set<java.lang.String> wordSet)
	{
		java.util.List<java.lang.String> wordProcessQueue = new java.util.ArrayList<java.lang.String>();

		java.util.Set<java.lang.String> visitedWordSet = new java.util.HashSet<java.lang.String>();

		int length = java.lang.Integer.MAX_VALUE;

		char[] alphabetArray = AlphabetArray();

		wordProcessQueue.add(beginWord + "0");

		int wordLength = beginWord.length();

		while (!wordProcessQueue.isEmpty())
		{
			java.lang.String wordProcessQueueEntry = wordProcessQueue.remove (0);

			int entrySize = wordProcessQueueEntry.length();

			int count = StringUtil.DecimalNumberFromString (
				wordProcessQueueEntry.substring(wordLength + 1, entrySize)
			);

			java.lang.String word = wordProcessQueueEntry.substring (0, wordLength);

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

					java.lang.String newWord = new java.lang.String (nextWordCharArray);

					if (wordSet.contains (newWord) && !visitedWordSet.contains(newWord))
					{
						wordProcessQueue.add(newWord + (count + 1));
					}
				}
			}
		}

		return length;
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
	}
}
