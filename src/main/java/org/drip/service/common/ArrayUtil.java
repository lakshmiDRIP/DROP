
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
 * <i>ArrayUtil</i> implements Generic Array Utility Functions used in DROP modules.
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

public class ArrayUtil
{

	private static final int PivotIndex (
		final int[] numberArray,
		int leftIndex,
		int rightIndex)
	{
		while (numberArray[leftIndex] > numberArray[rightIndex])
		{
			int midIndex = (leftIndex + rightIndex) / 2;

			if (numberArray[midIndex] > numberArray[midIndex + 1])
			{
				return midIndex;
			}

			if (numberArray[leftIndex] > numberArray[midIndex])
			{
				rightIndex = midIndex;
			}
			else
			{
				leftIndex = midIndex;
			}
		}

		return -1;
	}

	private static final int SearchPivotIndex (
		final int[] numberArray,
		final int target,
		int leftIndex,
		int rightIndex)
	{
		int midIndex = (leftIndex + rightIndex) / 2;

		while (leftIndex < rightIndex + 1)
		{
			if (numberArray[midIndex] == target)
			{
				return midIndex;
			}

			if (numberArray[midIndex] < target)
			{
				leftIndex = midIndex;
			}
			else
			{
				rightIndex = midIndex;
			}
		}

		return -1;
	}

	private static final int CircularArrayLoopLength (
		final int[] numberArray,
		final int startIndex)
	{
		int index = startIndex;
		int arraySize = numberArray.length;
		boolean forward = numberArray[index] > 0 ? true : false;

		java.util.Set<java.lang.Integer> indexSet = new java.util.HashSet<java.lang.Integer>();

		while (arraySize > indexSet.size())
		{
			if (indexSet.contains (
				index
			))
			{
				return indexSet.size();
			}

			if (forward != numberArray[index] > 0)
			{
				return -1;
			}

			indexSet.add (
				index
			);

			index = index + numberArray[index];
			index = index < 0 ? index + arraySize : index - arraySize;
		}

		return -1;
	}

	private static final int SlidingWindowCount (
		final int[] numberArray,
		final int index,
		final int windowSize)
	{
		int count = 0;

		for (int left = 0;
			left <= numberArray.length - windowSize;
			++left)
		{
			if (index >= left && index <= left + windowSize - 1)
			{
				++count;
			}
		}

		return count;
	}

	private static final boolean CanMakePalindrome (
		final java.lang.String s,
		final int left,
		final int right,
		final int k)
	{
		java.util.Set<java.lang.Character> charSet = new java.util.HashSet<java.lang.Character>();

		char[] charArray = s.toCharArray();

		for (int charIndex = left;
			charIndex <= right;
			++charIndex)
		{
			if (charSet.contains (
				charArray[charIndex]
			))
			{
				charSet.remove (
					charArray[charIndex]
				);
			}
			else
			{
				charSet.add (
					charArray[charIndex]
				);
			}
		}

		return charSet.size() <= 2 * k + 1;
	}

	private static final boolean ReverseMatchPossible (
		int[] a,
		int[] b,
		int startIndex,
		int endIndex)
	{
		while (startIndex <= endIndex)
		{
			if (a[startIndex++] != b[endIndex--])
			{
				return false;
			}
		}

		return true;
	}

	private static final boolean FlipSequence (
		final int[] numberArray,
		int endIndex)
	{
		int startIndex = 0;

		while (startIndex < endIndex)
		{
			int temp = numberArray[startIndex];
			numberArray[startIndex] = numberArray[endIndex];
			numberArray[endIndex] = temp;
			++startIndex;
			--endIndex;
		}

		return true;
	}

	private static final boolean DoRangesOverlap (
		final int[] range1,
		final int[] range2)
	{
		return (range1[0] >= range2[0] && range1[0] <= range2[1]) ||
			(range1[1] >= range2[0] && range1[1] <= range2[1]);
	}

	private static final int ApplyOperation (
		final java.lang.String leftNumber,
		final java.lang.String operation,
		final java.lang.String rightNumber)
	{
		int left = org.drip.service.common.StringUtil.DecimalNumberFromString (
			leftNumber
		);

		int right = org.drip.service.common.StringUtil.DecimalNumberFromString (
			rightNumber
		);

		if ("/".equalsIgnoreCase (
			operation
		))
		{
			return left / right;
		}

		if ("*".equalsIgnoreCase (
			operation
		))
		{
			return left * right;
		}

		if ("-".equalsIgnoreCase (
			operation
		))
		{
			return left - right;
		}

		return left + right;
	}

	private static final boolean CollapseOperation (
		final java.util.List<java.lang.String> elementList,
		final java.lang.String operation)
	{
		int elementCount = elementList.size();

		for (int elementIndex = elementCount - 1;
			elementIndex >= 0;
			--elementIndex)
		{
			if (operation.equalsIgnoreCase (
				elementList.get (
					elementIndex
				))
			)
			{
				if (0 == elementIndex || elementIndex == elementCount - 1)
				{
					return false;
				}

				int result = ApplyOperation (
					elementList.get (
						elementIndex - 1
					),
					operation,
					elementList.get (
						elementIndex + 1
					)
				);

				elementList.remove (
					elementIndex + 1
				);

				elementList.remove (
					elementIndex
				);

				elementList.set (
					elementIndex - 1,
					"" + result
				);
			}
		}

		return true;
	}

	private static final int LocateIndex (
		final int[] numberArray,
		final int leftIndex,
		final int rightIndex,
		final int number)
	{
		if (leftIndex == rightIndex) return number == numberArray[leftIndex] ? leftIndex : -1;

		int midIndex = (leftIndex + rightIndex) / 2;

		if (number == numberArray[midIndex]) return midIndex;

		return number < numberArray[midIndex] ? LocateIndex (numberArray, leftIndex - 1, midIndex, number) :
			LocateIndex (numberArray, midIndex + 1, rightIndex, number);
	}

	/**
	 * Search for the Target in a Rotated Array
	 * 
	 * @param numberArray The Rotated Number Array
	 * @param target The Target
	 * 
	 * @return TRUE - The Number exists
	 */

	public static final boolean SearchRotatedArray (
		final int[] numberArray,
		final int target)
	{
		int arrayLength = numberArray.length;
		int rightIndex = arrayLength - 1;
		int midIndex = arrayLength / 2;

		int pivotIndex = PivotIndex (
			numberArray,
			0,
			midIndex
		);

		if (-1 == pivotIndex)
		{
			pivotIndex = PivotIndex (
				numberArray,
				midIndex + 1,
				arrayLength - 1
			);
		}

		return numberArray[rightIndex] < target ? -1 != SearchPivotIndex (
			numberArray,
			target,
			0,
			pivotIndex
		) : -1 != SearchPivotIndex (
			numberArray,
			target,
			pivotIndex + 1,
			rightIndex
		);
	}

	/**
	 * Search for the Target in a Rotated Array
	 * 
	 * @param numberArray The Rotated Number Array
	 * @param target The Target
	 * 
	 * @return TRUE - The Rotated Index
	 */

	public static final int SearchRotatedArray2 (
		final int[] numberArray,
		final int target)
	{
		int arrayLength = numberArray.length;
		int rightIndex = arrayLength - 1;
		int midIndex = arrayLength / 2;

		int pivotIndex = PivotIndex (
			numberArray,
			0,
			midIndex
		);

		if (-1 == pivotIndex)
		{
			pivotIndex = PivotIndex (
				numberArray,
				midIndex + 1,
				arrayLength - 1
			);
		}

		return numberArray[rightIndex] < target ? SearchPivotIndex (
			numberArray,
			target,
			0,
			pivotIndex
		) : SearchPivotIndex (
			numberArray,
			target,
			pivotIndex + 1,
			rightIndex
		);
	}

	private static final int[] excludeAndSort (
		final int[] numberArray,
		final int excludeIndex)
	{
		int excludeAndSortIndex = 0;
		int excludeAndSortArraySize = numberArray.length - 1;
		int[] excludeAndSortArray = new int[excludeAndSortArraySize];

		for (int numberArrayIndex = 0;
			numberArrayIndex < numberArray.length;
			++numberArrayIndex)
		{
			if (numberArrayIndex != excludeIndex)
			{
				excludeAndSortArray[excludeAndSortIndex++] = numberArray[numberArrayIndex];
			}
		}

		java.util.Arrays.sort (
			excludeAndSortArray
		);

		return excludeAndSortArray;
	}

	/**
	 * Given an array of n integers, find all unique triplets in the array which gives the sum of zero.
	 * 
	 * @param numberArray The Number Array
	 * 
	 * @return The List of Unique Triplets
	 */

	public static final java.util.Map<java.lang.String, int[]> ThreeSum (
		final int[] numberArray)
	{
		java.util.Map<java.lang.String, int[]> tripletMap =
			new java.util.HashMap<java.lang.String, int[]>();

		for (int numberArrayIndex = 0;
			numberArrayIndex < numberArray.length;
			++numberArrayIndex)
		{
			int[] excludeAndSortArray = excludeAndSort (
				numberArray,
				numberArrayIndex
			);

			for (int excludeAndSortArrayIndex = 0;
				excludeAndSortArrayIndex < excludeAndSortArray.length;
				++excludeAndSortArrayIndex)
			{
				int tripletIndex = excludeAndSortArrayIndex + 1;
				int target = numberArray[numberArrayIndex] + excludeAndSortArray[excludeAndSortArrayIndex];

				while (tripletIndex < excludeAndSortArray.length && 
					target + excludeAndSortArray[tripletIndex] <= 0
				)
				{
					if (0 == target + excludeAndSortArray[tripletIndex])
					{
						int[] keyElementArray = new int[]
						{
							numberArray[numberArrayIndex],
							excludeAndSortArray[excludeAndSortArrayIndex],
							excludeAndSortArray[tripletIndex]
						};

						java.util.Arrays.sort (
							keyElementArray
						);

						tripletMap.put (
							keyElementArray[0] + "@" + keyElementArray[1] + "@" + keyElementArray[2] + "@",
							new int[]
							{
								numberArray[numberArrayIndex],
								excludeAndSortArray[excludeAndSortArrayIndex],
								excludeAndSortArray[tripletIndex]
							}
						);
					}

					++tripletIndex;
				}
			}
		}

		return tripletMap;
	}

	/**
	 * Determine if there is a loop (or a cycle) in numberArray. A cycle must start and end at the same index
	 *  and the cycle's length gt 1. Furthermore, movements in a cycle must all follow a single direction. In
	 *  other words, a cycle must not consist of both forward and backward movements.
	 *  
	 * @param numberArray The Number Array
	 * 
	 * @return TRUE - The Circle has a Loop
	 */

	public static final int CircularArrayLoop (
		final int[] numberArray)
	{
		for (int arrayIndex = 0;
			arrayIndex < numberArray.length;
			++arrayIndex)
		{
			int circleLength = CircularArrayLoopLength (
				numberArray,
				arrayIndex
			);

			if (-1 != circleLength)
			{
				return circleLength;
			}
		}

		return -1;
	}

	/**
	 * Given a rectangular cake with height h and width w, and two arrays of integers horizontalCuts and
	 *  verticalCuts where horizontalCuts[i] is the distance from the top of the rectangular cake to the ith
	 *  horizontal cut and similarly, verticalCuts[j] is the distance from the left of the rectangular cake
	 *  to the jth vertical cut. Return the maximum area of a piece of cake after you cut at each horizontal
	 *  and vertical position  provided in the arrays horizontalCuts and verticalCuts.
	 * 
	 * @param h Cake Height
	 * @param w Cake Width
	 * @param horizontalCuts Array of Horizontal Cuts
	 * @param verticalCuts Array of Vertical Cuts
	 * 
	 * @return Maximum area of a piece of cake
	 */

	public static final int MaxCutArea (
		final int h,
		final int w,
		final int[] horizontalCuts,
		final int[] verticalCuts)
	{
		int verticalCutCount = verticalCuts.length;
		int horizontalCutCount = horizontalCuts.length;
		int[] verticalCutArray = new int[verticalCutCount + 1];
		int[] horizontalCutArray = new int[horizontalCutCount + 1];
		horizontalCutArray[horizontalCutCount] = h;
		verticalCutArray[verticalCutCount] = w;
		int maxArea = 0;

		for (int horizontalCutIndex = 0;
			horizontalCutIndex < horizontalCutCount;
			++horizontalCutIndex)
		{
			horizontalCutArray[horizontalCutIndex] = horizontalCuts[horizontalCutIndex];
		}

		java.util.Arrays.sort (
			horizontalCutArray
		);

		for (int verticalCutIndex = 0;
			verticalCutIndex < verticalCutCount;
			++verticalCutIndex)
		{
			verticalCutArray[verticalCutIndex] = verticalCuts[verticalCutIndex];
		}

		java.util.Arrays.sort (
			verticalCutArray
		);

		for (int verticalCutIndex = 0;
			verticalCutIndex <= verticalCutCount;
			++verticalCutIndex)
		{
			int width = verticalCutArray[verticalCutIndex] - (
				0 == verticalCutIndex ? 0 : verticalCutArray[verticalCutIndex - 1]
			);

			for (int horizontalCutIndex = 0;
				horizontalCutIndex <= horizontalCutCount;
				++horizontalCutIndex)
			{
				int sliceArea = width * (horizontalCutArray[horizontalCutIndex] - (
					0 == horizontalCutIndex ? 0 : horizontalCutArray[horizontalCutIndex - 1]
				));

				if (maxArea < sliceArea)
				{
					maxArea = sliceArea;
				}
			}
		}

		return maxArea;
	}

	/**
	 * A transaction is possibly invalid if:
	 * 
	 * 	the amount exceeds $1000, or;
	 * 	if it occurs within (and including) 60 minutes of another transaction with the same name in a
	 * 		different city.
	 * 
	 * Each transaction string transactions[i] consists of comma separated values representing the name, time
	 * 	(in minutes), amount, and city of the transaction.
	 * 
	 * Given a list of transactions, return a list of transactions that are possibly invalid.  You may return
	 * 	the answer in any order.
	 * 
	 * @param transactionArray Array of Transactions
	 * 
	 * @return Array of Invalid Transactions
	 */

	public static final java.util.Collection<java.lang.String> InvalidTransactions (
		final java.lang.String[] transactionArray)
	{
		java.util.Map<java.lang.String, java.lang.String> invalidTransactionMap =
			new java.util.HashMap<java.lang.String, java.lang.String>();

		boolean first = true;
		int prevTransactionTime = -1;
		java.lang.String prevTransaction = "";
		java.lang.String prevTransactionCity = "";
		java.lang.String prevTransactionName = "";

		for (java.lang.String transaction : transactionArray)
		{
			java.lang.String[] transactionDetailArray = transaction.split (
				","
			);

			java.lang.String transactionCity = transactionDetailArray[3];
			java.lang.String transactionName = transactionDetailArray[0];

			int transactionTime = java.lang.Integer.parseInt (
				transactionDetailArray[1]
			);

			int transactionAmount = java.lang.Integer.parseInt (
				transactionDetailArray[2]
			);

			if (!first)
			{
				boolean sequenceViolation = transactionTime - prevTransactionTime <= 60 &&
					transactionName.equalsIgnoreCase (
						prevTransactionName
					) && !transactionCity.equalsIgnoreCase (
						prevTransactionCity
					);

				if (sequenceViolation)
				{
					invalidTransactionMap.put (
						prevTransaction,
						prevTransaction
					);

					invalidTransactionMap.put (
						transaction,
						transaction
					);
				}
				else if (transactionAmount > 1000)
				{
					invalidTransactionMap.put (
						transaction,
						transaction
					);
				}
			}

			first = false;
			prevTransaction = transaction;
			prevTransactionCity = transactionCity;
			prevTransactionName = transactionName;
			prevTransactionTime = transactionTime;
		}

		return invalidTransactionMap.values();
	}

	/**
	 * Given an integer array, find the contiguous sub-array within an array (containing at least one number)
	 * 	which has the largest product.
	 * 
	 * @param numberArray The Number Array
	 * 
	 * @return The Maximum Product
	 */

	public static final int MaximumProductSubarray (
		final int[] numberArray)
	{
		int max = numberArray[0];
		int min = numberArray[0];
		int result = numberArray[0];

		for (int i = 1; i < numberArray.length; ++i)
		{
			int temp = max;

			max = java.lang.Math.max (
				max * numberArray[i],
				java.lang.Math.max (
					min * numberArray[i],
					numberArray[i]
				)
			);

			min = java.lang.Math.min (
				temp * numberArray[i],
				java.lang.Math.min (
					min * numberArray[i],
					numberArray[i]
				)
			);

			if (max > result)
			{
				result = max;
			}
		}

		return result;
	}

	/**
	 * Given an array of integers, find the sum of min(B), where B ranges over every (contiguous) sub-array.
	 * 
	 * @param numberArray Array if Integers
	 * 
	 * @return Sum of min(B)
	 */

	public static final int SubarrayMinimum (
		final int[] numberArray)
	{
		if (1 == numberArray.length)
		{
			return numberArray[0];
		}

		int minIndex = -1;
		int minValue = java.lang.Integer.MAX_VALUE;

		for (int index = 0;
			index < numberArray.length;
			++index)
		{
			if (minValue > numberArray[index])
			{
				minIndex = index;
				minValue = numberArray[index];
			}
		}

		int minValueInstanceCount = 0;

		for (int index = 0;
			index < numberArray.length;
			++index)
		{
			minValueInstanceCount = minValueInstanceCount + SlidingWindowCount (
				numberArray,
				minIndex,
				index + 1
			);
		}

		if (minIndex == 0)
		{
			int[] numberSubarray = new int[numberArray.length - 1];

			for (int index = 1;
				index < numberArray.length;
				++index)
			{
				numberSubarray[index - 1] = numberArray[index];
			}

			return minValueInstanceCount * minValue + SubarrayMinimum (
				numberSubarray
			);
		}

		if (minIndex == numberArray.length - 1)
		{
			int[] numberSubarray = new int[numberArray.length - 1];

			for (int index = 0;
				index < numberArray.length - 1;
				++index)
			{
				numberSubarray[index] = numberArray[index];
			}

			return minValueInstanceCount * minValue + SubarrayMinimum (
				numberSubarray
			);
		}

		int[] leftSubarray = new int[minIndex];
		int[] rightSubarray = new int[numberArray.length - minIndex - 1];

		for (int index = 0;
			index < numberArray.length;
			++index)
		{
			if (minIndex > index)
			{
				leftSubarray[index] = numberArray[index];
			}
			else if (minIndex < index)
			{
				rightSubarray[index - minIndex - 1] = numberArray[index];
			}
		}

		return minValueInstanceCount * minValue + SubarrayMinimum (
			leftSubarray
		) + SubarrayMinimum (
			rightSubarray
		);
	}

	private static final int[] ExcludeAndSort (
		final int[] numberArray,
		final int exclusionIndex)
	{
		int[] excludedAndSortedArray = new int[numberArray.length - 1];

		for (int index = 0;
			index < numberArray.length;
			++index)
		{
			if (index < exclusionIndex)
			{
				excludedAndSortedArray[index] = numberArray[index];
			}
			else if (index > exclusionIndex)
			{
				excludedAndSortedArray[index - 1] = numberArray[index];
			}
		}

		java.util.Arrays.sort (
			excludedAndSortedArray
		);

		return excludedAndSortedArray;
	}

	/**
	 * Given an array of n integers and an integer target, are there elements a, b, c, and d in the array
	 * 	such that a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of
	 * 	target.
	 * 
	 * @param numberArray The Number Array
	 * @param target Target
	 * 
	 * @return Collection of Unique Quadruplets
	 */

	public static final java.util.Collection<java.util.List<java.lang.Integer>> FourSum (
		final int[] numberArray,
		final int target)
	{
		java.util.Map<java.lang.String, java.util.List<java.lang.Integer>> fourSumListMap =
			new java.util.HashMap<java.lang.String, java.util.List<java.lang.Integer>>();

		for (int exclusionIndex = 0;
			exclusionIndex < numberArray.length;
			++exclusionIndex)
		{
			int[] excludedAndSortedArray = ExcludeAndSort (
				numberArray,
				exclusionIndex
			);

			for (int excludedAndSortedArrayIndex = 0;
				excludedAndSortedArrayIndex < excludedAndSortedArray.length;
				++excludedAndSortedArrayIndex)
			{
				for (int threeSumIndex = excludedAndSortedArrayIndex + 1;
					threeSumIndex < excludedAndSortedArray.length;
					++threeSumIndex)
				{
					int fourSumIndex = excludedAndSortedArray.length - 1;

					while (fourSumIndex > threeSumIndex)
					{
						int sum = numberArray[exclusionIndex] +
							excludedAndSortedArray[excludedAndSortedArrayIndex] +
							excludedAndSortedArray[threeSumIndex] +
							excludedAndSortedArray[fourSumIndex];

						if (target == sum)
						{
							int[] fourSumComponentArray = new int[4];
							fourSumComponentArray[0] = numberArray[exclusionIndex];
							fourSumComponentArray[1] = excludedAndSortedArray[excludedAndSortedArrayIndex];
							fourSumComponentArray[2] = excludedAndSortedArray[threeSumIndex];
							fourSumComponentArray[3] = excludedAndSortedArray[fourSumIndex];

							java.util.Arrays.sort (
								fourSumComponentArray
							);

							java.util.List<java.lang.Integer> fourSumList =
								new java.util.ArrayList<java.lang.Integer>();

							fourSumList.add (
								numberArray[exclusionIndex]
							);

							fourSumList.add (
								excludedAndSortedArray[excludedAndSortedArrayIndex]
							);

							fourSumList.add (
								excludedAndSortedArray[threeSumIndex]
							);

							fourSumList.add (
								excludedAndSortedArray[fourSumIndex]
							);

							fourSumListMap.put (
								fourSumComponentArray[0] + "#" +
									fourSumComponentArray[1] + "#" +
									fourSumComponentArray[2] + "#" +
									fourSumComponentArray[3],
								fourSumList
							);
						}

						--fourSumIndex;
					}
				}
			}
		}

		return fourSumListMap.values();
	}

	/**
	 * Compute the Maximum Sum of any Sub-array
	 * 
	 * @param numberArray The Number Array
	 * 
	 * @return The Maximum Sum of any Sub-array
	 */

	public static final int MaximumSubarraySum (
		final int[] numberArray)
	{
		int endIndex = 0;
		int startIndex = 0;
		int maxSum = java.lang.Integer.MIN_VALUE;

		while (endIndex < numberArray.length)
		{
			int currentSum = 0;

			while (endIndex < numberArray.length &&
				0 <= numberArray[endIndex])
			{
				currentSum = currentSum + numberArray[endIndex++];
			}

			while (startIndex <= endIndex &&
				0 >= numberArray[startIndex])
			{
				currentSum = currentSum - numberArray[startIndex++];
			}

			if (currentSum > maxSum)
			{
				maxSum = currentSum;
			}

			endIndex++;
		}

		return maxSum;
	}

	/**
	 * Compute the Minimum Sum of any Sub-array
	 * 
	 * @param numberArray The Number Array
	 * 
	 * @return The Minimum Sum of any Sub-array
	 */

	public static final int MinimumSubarraySum (
		final int[] numberArray)
	{
		int endIndex = 0;
		int startIndex = 0;
		int minSum = java.lang.Integer.MAX_VALUE;

		while (endIndex < numberArray.length)
		{
			int currentSum = 0;

			while (endIndex < numberArray.length &&
				0 >= numberArray[endIndex])
			{
				currentSum = currentSum + numberArray[endIndex++];
			}

			while (startIndex <= endIndex &&
				0 >= numberArray[startIndex])
			{
				currentSum = currentSum - numberArray[startIndex++];
			}

			if (currentSum < minSum)
			{
				minSum = currentSum;
			}

			endIndex++;
		}

		return minSum;
	}

	/**
	 * Given a circular array C of integers represented by A, find the maximum possible sum of a non-empty
	 * 	sub-array of C. Here, a circular array means the end of the array connects to the beginning of the
	 * 	array.  (Formally, C[i] = A[i] when 0 lte i lt A.length, and C[i+A.length] = C[i] when i gte 0.)
	 * 
	 * Also, a sub-array may only include each element of the fixed buffer A at most once.  (Formally, for a
	 * 	sub-array C[i], C[i+1], ..., C[j], there does not exist i lte k1, k2 gte j with k1 % A.length = k2 %
	 *  A.length.)
	 * 
	 * @param numberArray The Number Array
	 * 
	 * @return The Maximum Sum of any Circular Sub-array
	 */

	public static final double MaximumSubarraySumCircular (
		final int[] numberArray)
	{
		int sum = 0;
		boolean allNegative = true;
		boolean allPositive = true;
		int max = java.lang.Integer.MIN_VALUE;

		for (int index = 0;
			index < numberArray.length;
			++index)
		{
			if (0 > numberArray[index])
			{
				allPositive = false;
			}
			else if (0 < numberArray[index])
			{
				allNegative = false;
			}

			sum = sum + numberArray[index];

			if (max < numberArray[index])
			{
				max = numberArray[index];
			}
		}

		if (allPositive)
		{
			return sum;
		}

		if (allNegative)
		{
			return max;
		}

		System.out.println (
			MinimumSubarraySum (
				numberArray
			)
		);

		return java.lang.Math.max (
			MaximumSubarraySum (
				numberArray
			),
			sum - MinimumSubarraySum (
				numberArray
			)
		);
	}

	private static final boolean SpiralMatrixTopBottom (
		final java.util.List<java.lang.Integer> elementList,
		final int[][] matrix,
		final int top,
		final int bottom,
		final int col,
		final boolean reverse)
	{
		if (top > bottom) return true;

		if (!reverse) {
			int row = top;

			while (row <= bottom) elementList.add (matrix[row++][col]);
		} else {
			int row = bottom;

			while (row >= top) elementList.add (matrix[row--][col]);
		}

		return true;
	}

	private static final boolean SpiralMatrixLeftRight (
		final java.util.List<java.lang.Integer> elementList,
		final int[][] matrix,
		final int left,
		final int right,
		final int row,
		final boolean reverse)
	{
		if (right < left) return true;

		if (!reverse) {
			int col = left;

			while (col <= right) elementList.add (matrix[row][col++]);
		} else {
			int col = right;

			while (col >= left) elementList.add (matrix[row][col--]);
		}

		return true;
	}

	/**
	 * Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral
	 * 	order.
	 * 
	 * @param matrix m x n Matrix
	 * 
	 * @return Elements of the matrix in spiral order.
	 */

	public static final java.util.List<java.lang.Integer> SpiralMatrixOrder (
		final int[][] matrix)
	{
		java.util.List<java.lang.Integer> elementList = new java.util.ArrayList<java.lang.Integer>();

		int top = 0;
		int row = -1;
		int left = -1;
		int col = matrix[0].length;
		int bottom = matrix.length - 1;
		int right = matrix[0].length - 1;

		while (true) {
			SpiralMatrixLeftRight (elementList, matrix, ++left, right, ++row, false);

			if (left > right) break;

			SpiralMatrixTopBottom (elementList, matrix, ++top, bottom, --col, false);

			if (top > bottom) break;

			SpiralMatrixLeftRight (elementList, matrix, left, --right, matrix.length - 1 - row, true);

			if (left > right) break;

			SpiralMatrixTopBottom (elementList, matrix, top, --bottom, matrix[0].length - 1 - col, true);

			if (top > bottom) break;
		}

		return elementList;
	}

	/**
	 * A robot is located at the top-left corner of a m x n grid. The robot can only move either down or
	 *  right at any point in time. The robot is trying to reach the bottom-right corner of the grid. Now
	 *  consider if some obstacles are added to the grids. How many unique paths would there be?
	 *  
	 * @param obstacleGrid The Obstacle Grid
	 * 
	 * @return Count of the Unique Paths
	 */

	public static final int UniquePathsWithObstacles (
		final int[][] obstacleGrid)
	{
		int uniquePathsWithObstacles = 0;
		int height = obstacleGrid.length;
		int width = obstacleGrid[0].length;

		java.util.List<int[]> locationList = new java.util.ArrayList<int[]>();

		locationList.add (
			new int[]
			{
				0,
				0
			}
		);

		while (!locationList.isEmpty())
		{
			int[] location = locationList.remove (
				0
			);

			int locationX = location[0];
			int locationY = location[1];

			if (locationX == width - 1 &&
				locationY == height - 1
			)
			{
				++uniquePathsWithObstacles;
				continue;
			}

			int down = locationY + 1;
			int right = locationX + 1;

			if (right < width &&
				1 != obstacleGrid[right][locationY]
			)
			{
				locationList.add (
					new int[]
					{
						right,
						locationY
					}
				);
			}

			if (down < height &&
				1 != obstacleGrid[locationX][down]
			)
			{
				locationList.add (
					new int[]
					{
						locationX,
						down
					}
				);
			}
		}

		return uniquePathsWithObstacles;
	}

	/**
	 * Given a string s, we make queries on substrings of s. For each query queries[i] = [left, right, k], we
	 *  may rearrange the substring s[left], ..., s[right], and then choose up to k of them to replace with
	 *  any lower-case English letter. If the substring is possible to be a palindrome string after the
	 *  operations above, the result of the query is true. Otherwise, the result is false.
	 *  
	 * Return an array answer[], where answer[i] is the result of the i-th query queries[i].
	 *  
	 * Note that: Each letter is counted individually for replacement so if for example
	 *  s[left..right] = "aaa", and k = 2, we can only replace two of the letters.  (Also, note that the
	 *  initial string s is never modified by any query.)
	 *  
	 * @param s Input String
	 * @param queries Array of Queries
	 * 
	 * @return List of Query Results
	 */

	public static final java.util.List<java.lang.Boolean> CanMakePalindromeQueries (
		final java.lang.String s,
		final int[][] queries)
	{
		java.util.List<java.lang.Boolean> queryArray = new java.util.ArrayList<java.lang.Boolean>();

		for (int[] query : queries)
		{
			queryArray.add (
				CanMakePalindrome (
					s,
					query[0],
					query[1],
					query[2]
				)
			);
		}

		return queryArray;
	}

	/**
	 * Find all pairs of integers in the array which have difference equal to the number d.
	 * 
	 * @param x The Array
	 * @param d The Difference
	 * 
	 * @return All pairs of integers in the array which have difference equal to the number d.
	 */

	public static final java.util.List<int[]> ArrayPairList (
		int[] x,
		int d)
	{
		if (null == x || 0 == x.length)
		{
			return null;
		}

		java.util.List<int[]> arrayPairList = new java.util.ArrayList<int[]>();

		java.util.Arrays.sort (
			x
		);

		for (int i = 0;
			i < x.length - 1;
			++i)
		{
			int j = x.length - 1;

			while (j > i &&
				x[j] - x[i] >= d
			)
			{
				if (x[j] == x[i] + d)
				{
					arrayPairList.add (
						new int[]
						{
							x[i],
							x[j]
						}
					);
				}

				--j;
			}
		}

		return arrayPairList;
	}

	private static final int FourSeaterCount (
		final int[] row)
	{
		int left = 1;
		int right = 1;
		int center = 1;

		if (1 == row[1] || 1 == row[2])
		{
			left = 0;
		}

		if (1 == row[3] || 1 == row[4])
		{
			left = 0;
			center = 0;
		}

		if (1 == row[5] || 1 == row[6])
		{
			right = 0;
			center = 0;
		}

		if (1 == row[7] || 1 == row[8])
		{
			right = 0;
		}

		if (1 == left && 1 == right)
		{
			center = 0;
		}

		return left + center + right;
	}

	/**
	 * A cinema has n rows of seats, numbered from 1 to n and there are ten seats in each row, labeled from 1
	 * 	to 10.
	 * 
	 * Given the array reservedSeats containing the numbers of seats already reserved, for example,
	 * 	reservedSeats[i] = [3,8] means the seat located in row 3 and labeled with 8 is already reserved.
	 * 
	 * Return the maximum number of four-person groups you can assign on the cinema seats. A four-person
	 * 	group occupies four adjacent seats in one single row. Seats across an aisle (such as [3,3] and [3,4])
	 *  are not considered to be adjacent, but there is an exceptional case on which an aisle split a
	 *  four-person group, in that case, the aisle split a four-person group in the middle, which means to
	 *  have two people on each side.
	 *  
	 * @param n Number of Rows
	 * @param reservedSeats Locations of Reserved Seats
	 * 
	 * @return Maximum number of four-person groups.
	 */

	public static final int MaximumNumberOfFamilies (
		final int n,
		final int[][] reservedSeats)
	{
		int maximumNumberOfFamilies = 0;
		int[][] seatGrid = new int[n][10];

		for (int i = 0;
			i < n;
			++i)
		{
			for (int j = 0;
				j < 10;
				++j)
			{
				seatGrid[i][j] = 0;
			}
		}

		for (int[] reservedSeat : reservedSeats)
		{
			seatGrid[reservedSeat[0] - 1][reservedSeat[1] - 1] = 1;
		}

		for (int[] seatRow : seatGrid)
		{
			maximumNumberOfFamilies = maximumNumberOfFamilies + FourSeaterCount (
				seatRow
			);
		}

		return maximumNumberOfFamilies;
	}

	/**
	 * Given an array of non-negative integers, you are initially positioned at the first index of the array.
	 * 
	 * Each element in the array represents your maximum jump length at that position.
	 * 
	 * Determine if you are able to reach the last index.
	 * 
	 * @param numberArray Array of non-negative Integers
	 * 
	 * @return TRUE - The Last Index can be reached.
	 */

	public static final boolean CanJumpToLastIndex (
		final int[] numberArray)
	{
		java.util.List<java.lang.Integer> traversalList = new java.util.ArrayList<java.lang.Integer>();

		java.util.Set<java.lang.Integer> visitedSet = new java.util.HashSet<java.lang.Integer>();

		int lastIndex = numberArray.length;

		traversalList.add (
			0
		);

		visitedSet.add (
			0
		);

		while (!traversalList.isEmpty())
		{
			int index = traversalList.remove (
				0
			);

			if (index == lastIndex)
			{
				return true;
			}

			for (int jump = 0;
				jump <= numberArray[index];
				++jump)
			{
				int nextLocation = index + jump;

				if (!visitedSet.contains (
					nextLocation
				))
				{
					traversalList.add (
						nextLocation
					);

					visitedSet.add (
						nextLocation
					);
				}
			}
		}

		return false;
	}

	private static final boolean ConditionalStep (
		final int x,
		final int y,
		final int wordCharIndex,
		final char[][] board,
		final char[] wordCharArray,
		final java.util.List<int[]> traversalList,
		final java.util.Set<java.lang.Integer> visitedLocationSet)
	{
		if (x >= 0 &&
			x < board.length &&
			y >= 0 &&
			y < board[0].length &&
			wordCharIndex < wordCharArray.length)
		{
			int nextLocation = x * board.length + y;

			if (wordCharArray[wordCharIndex] == board[x][y] &&
				!visitedLocationSet.contains (
					nextLocation
				)
			)
			{
				traversalList.add (
					new int[]
					{
						x,
						y,
						wordCharIndex
					}
				);

				visitedLocationSet.add (
					nextLocation
				);
			}
		}

		return true;
	}

	private static final boolean WordExists (
		final int startX,
		final int startY,
		final char[][] board,
		final char[] wordCharArray)
	{
		int wordLastIndex = wordCharArray.length - 1;

		java.util.List<int[]> traversalList = new java.util.ArrayList<int[]>();

		java.util.Set<java.lang.Integer> visitedLocationSet = new java.util.HashSet<java.lang.Integer>();

		traversalList.add (
			new int[]
			{
				startX,
				startY,
				0
			}
		);

		visitedLocationSet.add (
			startX * board.length + startY
		);

		while (!traversalList.isEmpty())
		{
			int[] traversalItem = traversalList.remove (
				0
			);

			int x = traversalItem[0];
			int y = traversalItem[1];
			int wordCharIndex = traversalItem[2];

			if (wordCharArray[wordCharIndex] != board[x][y])
			{
				continue;
			}

			if (wordCharIndex == wordLastIndex)
			{
				return true;
			}

			ConditionalStep (
				x - 1,
				y,
				wordCharIndex + 1,
				board,
				wordCharArray,
				traversalList,
				visitedLocationSet
			);

			ConditionalStep (
				x + 1,
				y,
				wordCharIndex + 1,
				board,
				wordCharArray,
				traversalList,
				visitedLocationSet
			);

			ConditionalStep (
				x,
				y - 1,
				wordCharIndex + 1,
				board,
				wordCharArray,
				traversalList,
				visitedLocationSet
			);

			ConditionalStep (
				x,
				y + 1,
				wordCharIndex + 1,
				board,
				wordCharArray,
				traversalList,
				visitedLocationSet
			);
		}

		return false;
	}

	/**
	 * Given a 2D board and a word, find if the word exists in the grid.
	 * 
	 * The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are
	 *  those horizontally or vertically neighboring. The same letter cell may not be used more than once.
	 *  
	 * @param board The Board
	 * @param word The Word
	 * 
	 * @return TRUE - The Word exists on the board
	 */

	public static final boolean WordExistsInBoard (
		final char[][] board,
		final java.lang.String word)
	{
		char[] wordCharArray = word.toCharArray();

		for (int row = 0;
			row < board.length;
			++row)
		{
			for (int column = 0;
				column < board[0].length;
				++column)
			{
				if (wordCharArray[0] == board[row][column])
				{
					if (WordExists (
						row,
						column,
						board,
						wordCharArray
					))
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Check if Reverse match is Possible
	 * 
	 * @param a Array A
	 * @param b Array B
	 * 
	 * @return TRUE - Reverse Match is Possible
	 */

	public static final boolean ReverseMatchPossible (
		final int[] a,
		final int[] b)
	{
		int index = 0;
		int length = a.length;
		int mismatchStartIndex = -1;

		while (index < length)
		{
			while (index < length && a[index] == b[index])
			{
				++index;
			}

			mismatchStartIndex = index;

			while (index < length && a[mismatchStartIndex] != b[index])
			{
				++index;
			}

			if (index == length)
			{
				return false;
			}

			if (!ReverseMatchPossible (
				a,
				b,
				mismatchStartIndex,
				index
			))
			{
				return false;
			}

			++index;
		}

		return true;
	}

	/**
	 * There are n students, numbered from 1 to n, each with their own year-book. They would like to pass
	 * 	their year- books around and get them signed by other students.
	 * 
	 * You are given a list of n integers arr[1..n], which is guaranteed to be a permutation of 1..n (in
	 * 	other words, it includes the integers from 1 to n exactly once each, in some order). The meaning of
	 * 	this list is described below.
	 * 
	 * Initially, each student is holding their own year-book. The students will then repeat the following two
	 * 	steps each minute: Each student i will first sign the year-book that they're currently holding (which
	 * 	may either belong to themselves or to another student), and then they'll pass it to student arr[i].
	 *  It is possible that arr[i] = i for any given i, in which case student i will pass their year-book back
	 *  to themselves. Once a student has received their own year-book back, they will hold on to it and no
	 *  longer participate in the passing process.
	 *  
	 * It is guaranteed that, for any possible valid input, each student will eventually receive their own
	 *  year-book back and will never end up holding more than one year-book at a time.
	 * 
	 * You must compute a list of n integers output, whose ith element is equal to the number of signatures
	 * 	that will be present in student i's year-book once they receive it back.

	 * @param studentArray The Starting Array
	 * 
	 * @return The Signature Count Array
	 */

	public static final int[] FindSignatureCount (
		final int[] studentArray)
	{
		int personCount = studentArray.length;
		int[] book = new int[personCount];
		int[] signatureArray = new int[personCount];

		java.util.Set<java.lang.Integer> bookNotAtOwner = new java.util.HashSet<java.lang.Integer>();

		for (int person = 0;
			person < personCount;
			++person)
		{
			signatureArray[person] = 1;
			book[person] = studentArray[person] - 1;

			bookNotAtOwner.add (
				person
			);
		}

		while (!bookNotAtOwner.isEmpty())
		{
			for (int person = 0;
				person < personCount;
				++person)
			{
				if (book[person] == person)
				{
					if (bookNotAtOwner.contains (
						person
					))
					{
						bookNotAtOwner.remove (
							person
						);
					}
				}
				else
				{
					signatureArray[person]++;
					book[person] = studentArray[book[person]] - 1;
				}
			}
		}

		return signatureArray;
	}

	/**
	 * You are given an array a of N integers. For each index i, you are required to determine the number of
	 * 	contiguous sub-arrays that fulfills the following conditions:
	 * 
	 * 	The value at index i must be the maximum element in the contiguous subarrays, and:
	 * 
	 * 	These contiguous subarrays must either start from or end on index i.
	 * 
	 * @param arr Input Array
	 * 
	 * @return Number of Contiguous Sub-arrays
	 */

	public static final int[] CountSubArrays (
		final int[] arr)
	{
		int arrayLength = arr.length;
		int[] subArrayCount = new int[arrayLength];
		boolean[] peakEncountered = new boolean[arrayLength];

		for (int i = 0;
			i < arrayLength;
			++i)
		{
			subArrayCount[i] = 1;
			peakEncountered[i] = false;
		}

		for (int i = 0;
			i < arrayLength;
			++i)
		{
			for (int j = 0;
				j < i;
				++j)
			{
				if (arr[i] > arr[j])
				{
					peakEncountered[j] = true;
				}
				else if (!peakEncountered[j])
				{
					++subArrayCount[j];
				}
			}
		}

		for (int i = 0;
			i < arrayLength;
			++i)
		{
			peakEncountered[i] = false;
		}

		for (int i = arrayLength - 1;
			i >= 0;
			--i)
		{
			for (int j = arrayLength - 1;
				j > i;
				--j)
			{
				if (arr[i] > arr[j])
				{
					peakEncountered[j] = true;
				}
				else if (!peakEncountered[j])
				{
					++subArrayCount[j];
				}
			}
		}

		return subArrayCount;
	}

	public static final int[][] KClosestPoints (
		final int[][] pointGrid,
		final int k)
	{
		java.util.TreeMap<java.lang.Double, int[]> orderedPointMap =
			new java.util.TreeMap<java.lang.Double, int[]>();

		for (int pointIndex = 0;
			pointIndex < pointGrid.length;
			++pointIndex)
		{
			int x = pointGrid[pointIndex][0];
			int y = pointGrid[pointIndex][1];
			double distance = x * x + y * y;

			orderedPointMap.put (
				distance,
				pointGrid[pointIndex]
			);
		}

		int i = 0;
		int[][] kClosestPoints = new int[k][2];

		java.util.Set<java.lang.Double> distanceSet = orderedPointMap.keySet();

		for (double distance : distanceSet)
		{
			if (i >= k)
			{
				break;
			}

			kClosestPoints[i++] = orderedPointMap.get (
				distance
			);
		}

		return kClosestPoints;
	}

	/**
	 * Merge the Array of Sorted Arrays into a Single Array
	 * 
	 * @param arrayOfArrays Array of Sorted Arrays
	 * 
	 * @return Resulting Single Array
	 */

	public static final int[] MergeSortedArrays (
		final int[][] arrayOfArrays)
	{
		int mergedArrayLength = 0;
		int arrayCount = arrayOfArrays.length;
		int[] arrayCursor = new int[arrayCount];
		int[] arrayLength = new int[arrayCount];

		for (int arrayIndex = 0;
			arrayIndex < arrayCount;
			++arrayIndex)
		{
			arrayCursor[arrayIndex] = 0;
			arrayLength[arrayIndex] = arrayOfArrays[arrayIndex].length;
			mergedArrayLength = mergedArrayLength + arrayOfArrays[arrayIndex].length;
		}

		int mergedArrayIndex = 0;
		int[] mergedArray = new int[mergedArrayLength];

		while (mergedArrayIndex < mergedArrayLength)
		{
			int startIndex = 0;

			while (arrayCursor[startIndex] >= arrayLength[startIndex])
			{
				++startIndex;
			}

			int minArrayIndex = startIndex;
			int minValue = arrayOfArrays[startIndex][arrayCursor[startIndex]];

			for (int arrayIndex = startIndex + 1;
				arrayIndex < arrayCount;
				++arrayIndex)
			{
				if (arrayCursor[arrayIndex] < arrayLength[arrayIndex] &&
					arrayOfArrays[arrayIndex][arrayCursor[arrayIndex]] < minValue
				)
				{
					minArrayIndex = arrayIndex;
					minValue = arrayOfArrays[arrayIndex][arrayCursor[arrayIndex]];
				}
			}

			mergedArray[mergedArrayIndex] = minValue;
			++arrayCursor[minArrayIndex];
			++mergedArrayIndex;
		}

		return mergedArray;
	}

	/**
	 * Given a circular array (the next element of the last element is the first element of the array), print
	 *  the Next Greater Number for every element. The Next Greater Number of a number x is the first greater
	 *  number to its traversing-order next in the array, which means you could search circularly to find its
	 *  next greater number. If it doesn't exist, output -1 for this number.
	 *   
	 * @param numberArray Input Number Array
	 * 
	 * @return Array of Next Greater Numbers
	 */

	public static final int[] NextGreaterElement (
		final int[] numberArray)
	{
		int arrayLength = numberArray.length;
		int[] nextGreaterElementArray = new int[arrayLength];

		for (int i = 0;
			i < arrayLength;
			++i)
		{
			int index = i + 1 == arrayLength ? 0 : i + 1;
			int nextGreaterNumber = numberArray[i];

			while (index != i)
			{
				if (numberArray[index] > nextGreaterNumber)
				{
					nextGreaterNumber = numberArray[index];
				}

				if (++index == arrayLength)
				{
					index = 0;
				}
			}

			nextGreaterElementArray[i] = numberArray[i] == nextGreaterNumber ? -1 : nextGreaterNumber;
		}

		return nextGreaterElementArray;
	}

	/**
	 * Given an array of integers, find out whether there are two distinct indices i and j in the array such
	 * 	that the absolute difference between two numbers in it is at most t and the absolute difference
	 * 	between i and j is at most k.
	 * 
	 * @param numberArray The Number Array
	 * @param k Index Difference Bound
	 * @param t Value Difference Bound
	 * 
	 * @return TRUE - There is a near-by Duplicate
	 */

	public static final boolean NearbyAlmostDuplicate (
		final int[] numberArray,
		final int k,
		final int t)
	{
		if (null == numberArray || 0 == numberArray.length)
		{
			return false;
		}

		int arrayLength = numberArray.length;

		if (0 > k || k >= arrayLength ||
			0 > t || t >= arrayLength)
		{
			return false;
		}

		for (int index = 0;
			index < arrayLength;
			++index)
		{
			int compareIndex = index < k ? 0 : index - k;
			int upperIndex = index + k >= arrayLength ? arrayLength - 1 : index + k;

			while (compareIndex <= upperIndex)
			{
				if (compareIndex != index && java.lang.Math.abs (
						numberArray[index] - numberArray[compareIndex]
					) <= t
				)
				{
					return true;
				}

				++compareIndex;
			}
		}

		return false;
	}

	/**
	 * Given a list of non-negative numbers and a target integer k, check if the array has a continuous
	 * 	sub-array of size at least 2 that sums up to a multiple of k, that is, sums up to n*k where n is also
	 * 	an integer.
	 * 
	 * @param numberArray The Number Array
	 * @param k Target Sum k
	 * 
	 * @return TRUE - The array has a continuous sub-array of size at least 2 that sums up to a multiple of k
	 */

	public static final boolean ContinuousSubarraySum (
		final int[] numberArray,
		final int k)
	{
		if (null == numberArray)
		{
			return false;
		}

		int arrayLength = numberArray.length;

		if (1 >= arrayLength)
		{
			return false;
		}

		java.util.HashMap<java.lang.Integer, java.lang.Integer> remainerSizeMap =
			new java.util.HashMap<java.lang.Integer, java.lang.Integer>();

		int sum = numberArray[0];

		for (int i = 1;
			i < arrayLength;
			++i)
		{
			sum = sum + numberArray[i];
			int currentRemain = sum % k;

			if (0 == currentRemain ||
				remainerSizeMap.containsKey (
					k - currentRemain
				) && i - 1 >= remainerSizeMap.get (
					k - currentRemain
				)
			)
			{
				return true;
			}

			remainerSizeMap.put (
				currentRemain,
				i + 1
			);
		}

		return false;
	}

	/**
	 * Given an integer array arr and an integer k, modify the array by repeating it k times.
	 * 
	 * For example, if the array is [1, 2] and k = 3 then the modified array will be [1, 2, 1, 2, 1, 2].
	 * 
	 * Return the maximum sub-array sum in the modified array. Note that the length of the sub-array can be 0
	 *  and its sum in that case is 0.
	 * 
	 * @param numberArray The Number Array
	 * @param k The Repeat Count
	 * 
	 * @return Maximum sub-array sum in the modified array
	 */

	public static final int KConcatenatedMaximumSum (
		final int[] numberArray,
		final int k)
	{
		if (null == numberArray)
		{
			return 0;
		}

		int arrayLength = numberArray.length;

		if (0 == arrayLength)
		{
			return 0;
		}

		int indexInclusiveLeftMax = numberArray[0] > 0 ? numberArray[0] : 0;
		int indexInclusiveLeftMin = numberArray[0] < 0 ? numberArray[0] : 0;
		int[] indexInclusiveLeftMaxArray = new int[arrayLength];
		int[] indexInclusiveLeftMinArray = new int[arrayLength];
		indexInclusiveLeftMaxArray[0] = numberArray[0];

		for (int i = 1;
			i < arrayLength;
			++i)
		{
			int sum = indexInclusiveLeftMaxArray[i - 1] + numberArray[i];
			indexInclusiveLeftMaxArray[i] = sum > numberArray[i] ? sum : numberArray[i];
			indexInclusiveLeftMinArray[i] = sum < numberArray[i] ? sum : numberArray[i];
			indexInclusiveLeftMax = indexInclusiveLeftMax > indexInclusiveLeftMaxArray[i] ?
				indexInclusiveLeftMax : indexInclusiveLeftMaxArray[i];
			indexInclusiveLeftMin = indexInclusiveLeftMin < indexInclusiveLeftMinArray[i] ?
				indexInclusiveLeftMin : indexInclusiveLeftMinArray[i];
		}

		int singleMax = indexInclusiveLeftMax - indexInclusiveLeftMin;

		if (1 == k)
		{
			return singleMax;
		}

		int[] indexInclusiveRightMaxArray = new int[arrayLength];
		int[] indexInclusiveRightMinArray = new int[arrayLength];
		indexInclusiveRightMaxArray[arrayLength - 1] = numberArray[arrayLength - 1];
		int indexInclusiveRightMax = numberArray[arrayLength - 1] > 0 ? numberArray[arrayLength - 1] : 0;
		int indexInclusiveRightMin = numberArray[arrayLength - 1] < 0 ? numberArray[arrayLength - 1] : 0;

		for (int i = arrayLength - 2;
			i >= 0;
			--i)
		{
			int sum = indexInclusiveLeftMaxArray[i + 1] + numberArray[i];
			indexInclusiveRightMaxArray[i] = sum > numberArray[i] ? sum : numberArray[i];
			indexInclusiveRightMinArray[i] = sum < numberArray[i] ? sum : numberArray[i];
			indexInclusiveRightMax = indexInclusiveRightMax > indexInclusiveRightMaxArray[i] ?
				indexInclusiveRightMax : indexInclusiveRightMaxArray[i];
			indexInclusiveRightMin = indexInclusiveRightMin < indexInclusiveRightMinArray[i] ?
				indexInclusiveRightMin : indexInclusiveRightMinArray[i];
		}

		int spanMax = indexInclusiveLeftMax + indexInclusiveRightMax;
		return k * singleMax > spanMax ? k * singleMax : spanMax;
	}

	private static final int KthHighestOrderStatistic (
		final int[] numberArray,
		final int k)
	{
		java.util.PriorityQueue<java.lang.Integer> heap = new java.util.PriorityQueue<java.lang.Integer>();

		int arrayCount = numberArray.length;

		for (int i = 0;
			i < arrayCount;
			++i)
		{
			if (i < k)
			{
				heap.offer(
					numberArray[i]
				);
			}
			else
			{
				if (numberArray[i] > heap.peek())
				{
					heap.poll();

					heap.offer(
						numberArray[i]
					);
				}
			}
		}

		return heap.peek();
	}

	/**
	 * Given an unsorted array numberArray, reorder it such that numberArray[0] le numberArray[1] ge
	 * 	numberArray[2] le numberArray[3]....
	 * 
	 * @param numberArray Number Array
	 * 
	 * @return TRUE - The Number Array is Wiggle Sorted
	 */

	public static final boolean WiggleSort (
		final int[] numberArray)
	{
		if (null == numberArray)
		{
			return true;
		}

		int oddLocation = 1;
		int arrayLength = numberArray.length;
		int evenLocation = 0 == arrayLength % 2 ? arrayLength - 2 : arrayLength - 1;
		java.lang.Integer[] wiggleArray = 2 >= arrayLength ? null : new java.lang.Integer[arrayLength];

		if (null == wiggleArray)
		{
			return true;
		}

		int median = KthHighestOrderStatistic (
			numberArray,
			(arrayLength + 1) / 2
		);

		for (int i = 0;
			i < arrayLength;
			++i)
		{
			if (numberArray[i] > median)
			{
				wiggleArray[oddLocation] = numberArray[i];
				oddLocation += 2;
			}
			else if (numberArray[i] < median)
			{
				wiggleArray[evenLocation] = numberArray[i];
				evenLocation -= 2;
			}
		}

		for (int i = 0;
			i < arrayLength;
			++i)
		{
			numberArray[i] = null == wiggleArray[i] ? median : wiggleArray[i];
		}

		return true;
	}

	public static final int TargetSum (
		final int[] numberArray,
		final int target)
	{
		int targetSumCount = 0;

		java.util.List<int[]> indexAndResultList = new java.util.ArrayList<int[]>();

		indexAndResultList.add (
			new int[]
			{
				-1,
				0
			}
		);

		while (!indexAndResultList.isEmpty())
		{
			int[] indexAndResult = indexAndResultList.remove (
				0
			);

			int nextIndex = indexAndResult[0] + 1;
			int result = indexAndResult[1];

			if (nextIndex == numberArray.length)
			{
				if (result == target)
				{
					++targetSumCount;
				}
			}
			else
			{
				indexAndResultList.add (
					new int[]
					{
						nextIndex,
						result + numberArray[nextIndex]
					}
				);

				indexAndResultList.add (
					new int[]
					{
						nextIndex,
						result - numberArray[nextIndex]
					}
				);
			}
		}

		return targetSumCount;
	}

	public static final int UniqueElementsInSortedArray (
		final int[] numberArray)
	{
		int i = 1;
		int count = 0 ;
		int prevNumber = numberArray[0];

		while (i < numberArray.length)
		{
			while (i < numberArray.length &&
				prevNumber == numberArray[i]
			)
			{
				++i;
			}

			count++;

			if (i < numberArray.length)
			{
				prevNumber = numberArray[i];
			}
		}

		return count;
	}

	private static final boolean GeneratePathCombinations (
		final java.util.List<java.util.List<java.lang.Integer>> pathIndexList,
		final int currentIndex,
		final int[] numberArray)
	{
		int arrayLength = numberArray.length;

		if (currentIndex == arrayLength)
		{
			return true;
		}

		java.util.List<java.util.List<java.lang.Integer>> currentPathIndexList =
			new java.util.ArrayList<java.util.List<java.lang.Integer>>();

		java.util.List<java.lang.Integer> currentIndexPath = new java.util.ArrayList<java.lang.Integer>();

		currentIndexPath.add (
			numberArray[currentIndex]
		);

		currentPathIndexList.add (
			currentIndexPath
		);

		for (java.util.List<java.lang.Integer> pathIndex : pathIndexList)
		{
			java.util.List<java.lang.Integer> currentPath = new java.util.ArrayList<java.lang.Integer>();

			currentPath.addAll (
				pathIndex
			);

			currentPath.add (
				numberArray[currentIndex]
			);

			currentPathIndexList.add (
				currentPath
			);
		}

		pathIndexList.addAll (
			currentPathIndexList
		);

		return GeneratePathCombinations (
			pathIndexList,
			currentIndex + 1,
			numberArray
		);
	}

	public static final java.util.Set<java.util.List<java.lang.Integer>> GeneratePathCombinations (
		final int[] numberArray,
		final int target)
	{
		java.util.List<java.util.List<java.lang.Integer>> pathIndexList =
			new java.util.ArrayList<java.util.List<java.lang.Integer>>();

		GeneratePathCombinations (
			pathIndexList,
			0,
			numberArray
		);

		java.util.Set<java.util.List<java.lang.Integer>> validPathIndexSet =
			new java.util.HashSet<java.util.List<java.lang.Integer>>();

		for (java.util.List<java.lang.Integer> pathIndexSequence : pathIndexList)
		{
			int sum = 0;

			for (int pathIndexValue : pathIndexSequence)
			{
				sum = sum + pathIndexValue;
			}

			if (sum == target)
			{
				validPathIndexSet.add (
					pathIndexSequence
				);
			}
		}

		return validPathIndexSet;
	}

	private static final int[] FirstAndLastPosition (
		final int leftIndex,
		final int rightIndex,
		final int[] numberArray,
		final int target)
	{
		int arrayLength = numberArray.length;
		int midIndex = (leftIndex + rightIndex) / 2;

		if (rightIndex == leftIndex || rightIndex == leftIndex + 1)
		{
			if (numberArray[leftIndex] != target && numberArray[rightIndex] != target)
			{
				return new int[]
				{
					-1,
					-1
				};
			}
		}

		if (numberArray[midIndex] == target)
		{
			int leftLocation = midIndex;
			int rightLocation = midIndex;

			while (leftLocation >= 0 && numberArray[leftLocation] == target)
			{
				--leftLocation;
			}

			while (rightLocation < arrayLength && numberArray[rightLocation] == target)
			{
				++rightLocation;
			}

			return new int[]
			{
				numberArray[leftLocation] == target ? leftLocation : leftLocation + 1,
				numberArray[rightLocation] == target ? rightLocation : rightLocation - 1
			};
		}
		else if (target < numberArray[midIndex])
		{
			return FirstAndLastPosition (
				leftIndex,
				midIndex,
				numberArray,
				target
			);
		}

		return FirstAndLastPosition (
			midIndex,
			rightIndex,
			numberArray,
			target
		);
	}

	public static final int[] FirstAndLastPosition (
		final int[] numberArray,
		final int target)
	{
		int arrayLength = numberArray.length;

		if (target < numberArray[0] || target > numberArray[arrayLength - 1])
		{
			return new int[]
			{
				-1,
				-1
			};
		}

		return FirstAndLastPosition (
			0,
			arrayLength - 1,
			numberArray,
			target
		);
	}

	public static final int[] ProductOfArrayExceptSelf (
		final int[] numberArray)
	{
		int arrayLength = numberArray.length;
		int[] productOfArrayExceptSelf = new int[arrayLength];
		int[] leftProductExcludingSelf = new int[arrayLength];
		int[] rightProductExcludingSelf = new int[arrayLength];

		for (int i = 0;
			i < arrayLength;
			++i)
		{
			leftProductExcludingSelf[i] = 0 == i ? 1 : leftProductExcludingSelf[i - 1] * numberArray[i - 1];
		}

		for (int i = arrayLength - 1;
			i >= 0;
			--i)
		{
			rightProductExcludingSelf[i] = i == arrayLength - 1 ? 1 :
				numberArray[i + 1] * rightProductExcludingSelf[i + 1];
		}

		for (int i = 0;
			i < arrayLength;
			++i)
		{
			productOfArrayExceptSelf[i] = leftProductExcludingSelf[i] * rightProductExcludingSelf[i];
		}

		return productOfArrayExceptSelf;
	}

	private static final boolean MarkIslands (
		final int xStart,
		final int yStart,
		final int[][] islandMark,
		final int[][] grid)
	{
		int rowCount = grid.length;
		int columnCount = grid[0].length;

		java.util.List<int[]> locationList = new java.util.ArrayList<int[]>();

		locationList.add(new int[] {xStart, yStart});

		while (!locationList.isEmpty())
		{
			int[] location = locationList.remove (0);

			int x = location[0];
			int y = location[1];
			islandMark[x][y] = 1;
			int xNext = x + 1;
			int yNext = y + 1;

			if (xNext < rowCount && 0 != grid[xNext][y])
			{
				locationList.add(new int[] {xNext, y});
			}

			if (yNext < columnCount && 0 != grid[x][yNext])
			{
				locationList.add(new int[] {x, yNext});
			}
		}

		return true;
	}

	public static final int IslandCounter (
		final int[][] grid)
	{
		int islandCount = 0;
		int rowCount = grid.length;
		int columnCount = grid[0].length;
		int[][] islandMark = new int[rowCount][columnCount];
		int x = 0;
		int y = 0;

		for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex)
		{
			for (int columnIndex = 0; columnIndex < columnCount; ++columnIndex)
			{
				islandMark[rowIndex][columnIndex] = 1 - grid[rowIndex][columnIndex];
			}
		}

		while (x < columnCount && y < rowCount)
		{
			while (y < columnCount && 1 == islandMark[x][y])
			{
				x++;

				if (rowCount == x)
				{
					x = 0;
					y++;
				}
			}

			if (y == columnCount)
			{
				break;
			}

			MarkIslands (
				x,
				y,
				islandMark,
				grid
			);

			++islandCount;
		}

		return islandCount;
	}

	public static final int[] PancakeFlipSort (
		final int[] numberArray)
	{
		int arrayLength = numberArray.length;
		int endIndex = arrayLength - 1;

		while (endIndex >= 0)
		{
			int maxIndex = 0;
			int maxValue = numberArray[0];

			for (int index = 0;
				index <= endIndex;
				++index)
			{
				if (numberArray[index] > maxValue)
				{
					maxValue = numberArray[index];
					maxIndex = index;
				}
			}

			if (0 != maxIndex)
			{
				FlipSequence (
					numberArray,
					maxIndex
				);
			}

			FlipSequence (
				numberArray,
				endIndex
			);

			--endIndex;
		}

		return numberArray;
	}

	private static final int RoundLotUnits (
		final int lotSize,
		final int unitSize)
	{
		return lotSize / unitSize + (0 == lotSize % unitSize ? 0 : 1);
	}

	private static final int TimeConsumed (
		final int[] lotSizeArray,
		final int rate)
	{
		int timeConsumed = 0;

		for (int index = 0;
			index < lotSizeArray.length;
			++index)
		{
			timeConsumed = timeConsumed + RoundLotUnits (
				lotSizeArray[index],
				rate
			);
		}

		return timeConsumed;
	}

	public static final int MinimumConsumptionRate (
		final int[] lotSizeArray,
		final int totalTime)
	{
		int totalSize = 0;
		int lotCount = lotSizeArray.length;

		for (int lotIndex = 0;
			lotIndex < lotCount;
			++lotIndex)
		{
			totalSize = totalSize + lotSizeArray[lotIndex];
		}

		int consumptionRate = totalSize / totalTime;

		if (0 == consumptionRate)
		{
			return -1;
		}

		int timeConsumed = TimeConsumed (
			lotSizeArray,
			consumptionRate
		);

		while (timeConsumed > totalTime)
		{
			timeConsumed = TimeConsumed (
				lotSizeArray,
				++consumptionRate
			);
		}

		return consumptionRate;
	}

	/**
	 * Collapse any Overlapping Ranges inside the Specified List
	 * 
	 * @param rangeList Ranges in the List
	 * 
	 * @return Collection of the Collapsed Ranges
	 */

	public static final java.util.Collection<int[]> CollapseOverlappingRanges (
		final java.util.List<int[]> rangeList)
	{
		java.util.TreeMap<java.lang.Integer, int[]> rangeMap =
			new java.util.TreeMap<java.lang.Integer, int[]>();

		for (int[] range : rangeList)
		{
			if (rangeMap.isEmpty())
			{
				rangeMap.put (
					range[0],
					range
				);

				continue;
			}

			int newRangeEnd = range[1];
			int newRangeStart = range[0];

			java.lang.Integer floorKey = rangeMap.floorKey (
				newRangeStart
			);

			floorKey = null != floorKey ? floorKey : rangeMap.firstKey();

			java.util.Map<java.lang.Integer, int[]> tailRangeMap = rangeMap.tailMap (
				floorKey
			);

			java.util.List<java.lang.Integer> rangeTrimList = new java.util.ArrayList<java.lang.Integer>();

			if (null != tailRangeMap && 0 != tailRangeMap.size())
			{
				for (java.util.Map.Entry<java.lang.Integer, int[]> tailRangeEntry : tailRangeMap.entrySet())
				{
					int[] tailRange = tailRangeEntry.getValue();

					if (DoRangesOverlap (
							range,
							tailRange
						) || DoRangesOverlap (
							tailRange,
							range
						))
					{
						newRangeEnd = newRangeEnd > tailRange[1] ? newRangeEnd : tailRange[1];
						newRangeStart = newRangeStart < tailRange[0] ? newRangeStart : tailRange[0];

						rangeTrimList.add (
							tailRange[0]
						);
					}
				}
			}

			for (int rangeStart : rangeTrimList)
			{
				rangeMap.remove (
					rangeStart
				);
			}

			rangeMap.put (
				newRangeStart,
				new int[]
				{
					newRangeStart,
					newRangeEnd
				}
			);
		}

		return rangeMap.values();
	}

	private static final boolean UploadCounterRangeMaps (
		final java.util.TreeMap<java.lang.Integer, java.lang.Integer> sliceCounterMap,
		final java.util.TreeMap<java.lang.Integer, int[]> sliceRangeMap,
		final int[] range,
		final int rangeCount)
	{
		if (range[0] >= range[1])
		{
			return false;
		}

		sliceCounterMap.put (
			range[0],
			rangeCount
		);

		sliceRangeMap.put (
			range[0],
			range
		);

		return true;
	}

	private static final int[] ProcessRange (
		final java.util.TreeMap<java.lang.Integer, java.lang.Integer> sliceCounterMap,
		final java.util.TreeMap<java.lang.Integer, int[]> sliceRangeMap,
		final int[] inputRange,
		final int[] mapRange,
		final int mapRangeCount)
	{
		if (inputRange[1] < mapRange[0] || inputRange[0] > mapRange[1])
		{
			UploadCounterRangeMaps (
				sliceCounterMap,
				sliceRangeMap,
				inputRange,
				1
			);

			UploadCounterRangeMaps (
				sliceCounterMap,
				sliceRangeMap,
				mapRange,
				mapRangeCount
			);

			return null;
		}

		if (inputRange[0] >= mapRange[0] && inputRange[1] <= mapRange[1])
		{
			UploadCounterRangeMaps (
				sliceCounterMap,
				sliceRangeMap,
				new int[]
				{
					mapRange[0],
					inputRange[0]
				},
				mapRangeCount
			);

			UploadCounterRangeMaps (
				sliceCounterMap,
				sliceRangeMap,
				new int[]
				{
					inputRange[0],
					inputRange[1]
				},
				mapRangeCount + 1
			);

			UploadCounterRangeMaps (
				sliceCounterMap,
				sliceRangeMap,
				new int[]
				{
					inputRange[1],
					mapRange[1]
				},
				mapRangeCount
			);

			return null;
		}

		if (inputRange[0] <= mapRange[0] && inputRange[1] >= mapRange[1])
		{
			UploadCounterRangeMaps (
				sliceCounterMap,
				sliceRangeMap,
				new int[]
				{
					inputRange[0],
					mapRange[0]
				},
				1
			);

			UploadCounterRangeMaps (
				sliceCounterMap,
				sliceRangeMap,
				new int[]
				{
					mapRange[0],
					mapRange[1]
				},
				mapRangeCount + 1
			);

			return new int[]
			{
				mapRange[1],
				inputRange[1]
			};
		}

		if (inputRange[0] <= mapRange[0] && inputRange[1] <= mapRange[1])
		{
			UploadCounterRangeMaps (
				sliceCounterMap,
				sliceRangeMap,
				new int[]
				{
					inputRange[0],
					mapRange[0]
				},
				1
			);

			UploadCounterRangeMaps (
				sliceCounterMap,
				sliceRangeMap,
				new int[]
				{
					mapRange[0],
					inputRange[1]
				},
				mapRangeCount + 1
			);

			UploadCounterRangeMaps (
				sliceCounterMap,
				sliceRangeMap,
				new int[]
				{
					inputRange[0],
					mapRange[1]
				},
				mapRangeCount
			);

			return null;
		}

		if (inputRange[0] >= mapRange[0] && inputRange[1] >= mapRange[1])
		{
			UploadCounterRangeMaps (
				sliceCounterMap,
				sliceRangeMap,
				new int[]
				{
					mapRange[0],
					inputRange[0]
				},
				mapRangeCount
			);

			UploadCounterRangeMaps (
				sliceCounterMap,
				sliceRangeMap,
				new int[]
				{
					inputRange[0],
					mapRange[1]
				},
				mapRangeCount + 1
			);

			return new int[]
			{
				mapRange[1],
				inputRange[1]
			};
		}

		return null;
	}

	/**
	 * Generate a Counter Map of the Overlapping Slice Ranges
	 * 
	 * @param rangeList The Range List
	 * 
	 * @return Counter Map of the Overlapping Slice Ranges
	 */

	public static final java.util.TreeMap<java.lang.Integer, java.lang.Integer> SliceOverlappingRanges (
		final java.util.List<int[]> rangeList)
	{
		java.util.TreeMap<java.lang.Integer, java.lang.Integer> rangeCounterMap =
			new java.util.TreeMap<java.lang.Integer, java.lang.Integer>();

		java.util.TreeMap<java.lang.Integer, int[]> rangeMap =
			new java.util.TreeMap<java.lang.Integer, int[]>();

		for (int[] range : rangeList)
		{
			if (rangeMap.isEmpty())
			{
				rangeMap.put (
					range[0],
					range
				);

				rangeCounterMap.put (
					range[0],
					1
				);
			}
			else
			{
				int[] residualRange = range;

				java.util.TreeMap<java.lang.Integer, java.lang.Integer> sliceCounterMap =
					new java.util.TreeMap<java.lang.Integer, java.lang.Integer>();

				java.util.TreeMap<java.lang.Integer, int[]> sliceRangeMap =
					new java.util.TreeMap<java.lang.Integer, int[]>();

				java.util.HashSet<java.lang.Integer> trimList = new java.util.HashSet<java.lang.Integer>();

				for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> rangeCounterMapEntry :
					rangeCounterMap.entrySet())
				{
					int mapRangeLeft = rangeCounterMapEntry.getKey();

					if (null == residualRange)
					{
						break;
					}

					residualRange = ProcessRange (
						sliceCounterMap,
						sliceRangeMap,
						residualRange,
						rangeMap.get (
							mapRangeLeft
						),
						rangeCounterMapEntry.getValue()
					);

					trimList.add (
						mapRangeLeft
					);
				}

				for (int trimRangeLeft : trimList)
				{
					rangeMap.remove (
						trimRangeLeft
					);

					rangeCounterMap.remove (
						trimRangeLeft
					);
				}

				rangeMap.putAll (
					sliceRangeMap
				);

				rangeCounterMap.putAll (
					sliceCounterMap
				);

				if (null != residualRange)
				{
					rangeMap.put (
						residualRange[0],
						residualRange
					);

					rangeCounterMap.put (
						residualRange[0],
						1
					);
				}
			}
		}

		return rangeCounterMap;
	}

	/**
	 * There are n guests attending a dinner party, numbered from 1 to n. The i<sup>th</sup> guest has a
	 * 	height of heightArray[i] inches. The guests will sit down at a circular table which has n seats,
	 * 	numbered from 1 to n in clockwise order around the table. As the host, you will choose how to arrange
	 * 	the guests, one per seat. Note that there are n! possible permutations of seat assignments. Once the
	 * 	guests have sat down, the awkwardness between a pair of guests sitting in adjacent seats is defined
	 * 	as the absolute difference between their two heights. Note that, because the table is circular, seats
	 * 	1 and n are considered to be adjacent to one another, and that there are therefore n pairs of
	 * 	adjacent guests. The overall awkwardness of the seating arrangement is then defined as the maximum
	 *  awkwardness of any pair of adjacent guests. Determine the minimum possible overall awkwardness of any
	 *  seating arrangement.
	 *  
	 * @param heightArray The Height Array
	 * 
	 * @return The Maximum Awkwardness of the Arrangement
	 */

	public static final int MinimumOverallAwkwardness (
		final int[] heightArray)
	{
		if (null == heightArray)
		{
			return -1;
		}

		java.util.Arrays.sort (
			heightArray
		);

		int heightCount = heightArray.length;

		if (1 >= heightCount)
		{
			return -1;
		}

		int[] unawkwardHeightArray = new int[heightCount];
		int heightIndex = heightCount - 1;
		boolean assignmentFlip = false;
		int unawkwardHeightIndex = 0;
		int awkwardness = 0;

		while (heightIndex >= 0)
		{
			unawkwardHeightArray[assignmentFlip ? heightCount - 1 - unawkwardHeightIndex :
				unawkwardHeightIndex] = heightArray[heightIndex];

			if (!(assignmentFlip = !assignmentFlip))
			{
				++unawkwardHeightIndex;
			}

			--heightIndex;
		}

		for (int index = 0;
			index < heightCount;
			++index)
		{
			int priorIndex = index - 1 < 0 ? heightCount - 1 : index - 1;

			int currentAwkwardness = java.lang.Math.abs (
				unawkwardHeightArray[index] - unawkwardHeightArray[priorIndex]
			);

			if (currentAwkwardness > awkwardness)
			{
				awkwardness = currentAwkwardness;
			}
		}

		return awkwardness;
	}

	/**
	 * Given a list of integers and a target. There are 2 symbols + and -. For each integer, choose one from
	 *  + and - as its new symbol.
	 *  
	 * Find out the ways to assign symbols to make sum of integers equal to target.
	 * 
	 * @param numberArray The Number Array
	 * @param target The Sum Target
	 * 
	 * @return List of the Target Approach Paths
	 */

	public static final java.util.List<java.lang.String> TargetApproachPathList (
		final int[] numberArray,
		final int target)
	{
		java.util.List<java.lang.String> targetApproachPathList =
			new java.util.ArrayList<java.lang.String>();

		java.util.List<java.lang.Integer> indexQueue = new java.util.ArrayList<java.lang.Integer>();

		java.util.List<java.lang.Integer> sumQueue = new java.util.ArrayList<java.lang.Integer>();

		java.util.List<java.lang.String> pathQueue = new java.util.ArrayList<java.lang.String>();

		int arrayCount = numberArray.length;

		indexQueue.add (
			0
		);

		sumQueue.add (
			0
		);

		pathQueue.add (
			""
		);

		while (!indexQueue.isEmpty())
		{
			int queueTailIndex = indexQueue.size() - 1;

			int index = indexQueue.remove (
				queueTailIndex
			);

			int sum = sumQueue.remove (
				queueTailIndex
			);

			java.lang.String path = pathQueue.remove (
				queueTailIndex
			);

			if (index == arrayCount - 1)
			{
				if (target == sum + numberArray[index])
				{
					targetApproachPathList.add (
						path + "+" + numberArray[index]
					);
				}

				if (target == sum - numberArray[index])
				{
					targetApproachPathList.add (
						path + "-" + numberArray[index]
					);
				}

				continue;
			}

			indexQueue.add (
				index + 1
			);

			sumQueue.add (
				sum - numberArray[index]
			);

			pathQueue.add (
				path + "-" + numberArray[index]
			);

			indexQueue.add (
				index + 1
			);

			sumQueue.add (
				sum + numberArray[index]
			);

			pathQueue.add (
				path + "+" + numberArray[index]
			);
		}

		return targetApproachPathList;
	}

	/**
	 * Execute a BODMAS Evaluation of the Expression
	 * 
	 * @param s The Expression String
	 * 
	 * @return result of the BODMAS Evaluation
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final int BODMAS (
		final java.lang.String s)
		throws java.lang.Exception
	{
		if (null == s || s.isEmpty())
		{
			throw new java.lang.Exception (
				"ArrayUtil::BODMAS => Invalid Inputs"
			);
		}

		char[] charArray = s.toCharArray();

		int prevIndex = 0;
		int stringLength = charArray.length;

		java.util.List<java.lang.String> elementList = new java.util.ArrayList<java.lang.String>();

		for (int index = 0;
			index < stringLength;
			++index)
		{
			char c = charArray[index];

			if (c == '+' || c == '-' || c == '*' || c == '/')
			{
				elementList.add (
					s.substring (
						prevIndex,
						index
					)
				);

				elementList.add (
					"" + c
				);

				prevIndex = index + 1;
			}
		}

		if (prevIndex < stringLength)
		{
			elementList.add (
				s.substring (
					prevIndex,
					stringLength
				)
			);
		}

		if (!CollapseOperation (
			elementList,
			"/"
		))
		{
			throw new java.lang.Exception (
				"ArrayUtil::BODMAS => Cannot compute"
			);
		}

		if (!CollapseOperation (
			elementList,
			"*"
		))
		{
			throw new java.lang.Exception (
				"ArrayUtil::BODMAS => Cannot compute"
			);
		}

		if (!CollapseOperation (
			elementList,
			"-"
		))
		{
			throw new java.lang.Exception (
				"ArrayUtil::BODMAS => Cannot compute"
			);
		}

		if (!CollapseOperation (
			elementList,
			"+"
		))
		{
			throw new java.lang.Exception (
				"ArrayUtil::BODMAS => Cannot compute"
			);
		}

		if (1 != elementList.size())
		{
			throw new java.lang.Exception (
				"ArrayUtil::BODMAS => Cannot compute"
			);
		}

		return org.drip.service.common.StringUtil.DecimalNumberFromString (
			elementList.get (
				0
			)
		);
	}

	/**
	 * Given a string that contains only digits 0-9 and a target value, return all possibilities to add
	 *  binary operators (not unary) +, -, or * between the digits so they evaluate to the target value.
	 * 
	 * @param numberArray The Number Array
	 * @param target The Target
	 * 
	 * @return List of the Expression Possibilities
	 */

	public static final java.util.List<java.lang.String> ExpressionOperatorPathList (
		final int[] numberArray,
		final int target)
	{
		java.util.List<java.lang.String> expressionOperatorPathList =
			new java.util.ArrayList<java.lang.String>();

		java.util.List<java.lang.Integer> indexQueue = new java.util.ArrayList<java.lang.Integer>();

		java.util.List<java.lang.String> pathQueue = new java.util.ArrayList<java.lang.String>();

		int arrayCount = numberArray.length;

		indexQueue.add (
			1
		);

		pathQueue.add (
			"" + numberArray[0]
		);

		while (!indexQueue.isEmpty())
		{
			int queueTailIndex = indexQueue.size() - 1;

			int index = indexQueue.remove (
				queueTailIndex
			);

			java.lang.String path = pathQueue.remove (
				queueTailIndex
			);

			if (index == arrayCount - 1)
			{
				java.lang.String bodmasExpression = path + "+" + numberArray[index];

				try
				{
					if (target == BODMAS (
						bodmasExpression
					))
					{
						expressionOperatorPathList.add (
							bodmasExpression
						);
					}

					if (target == BODMAS (
						bodmasExpression = path + "-" + numberArray[index]
					))
					{
						expressionOperatorPathList.add (
							bodmasExpression
						);
					}

					if (target == BODMAS (
						bodmasExpression = path + "*" + numberArray[index]
					))
					{
						expressionOperatorPathList.add (
							bodmasExpression
						);
					}

					if (target == BODMAS (
						bodmasExpression = path + "" + numberArray[index]
					))
					{
						expressionOperatorPathList.add (
							bodmasExpression
						);
					}
				}
				catch (java.lang.Exception e)
				{
					e.printStackTrace();

					return null;
				}

				continue;
			}

			indexQueue.add (
				index + 1
			);

			pathQueue.add (
				path + "-" + numberArray[index]
			);

			indexQueue.add (
				index + 1
			);

			pathQueue.add (
				path + "+" + numberArray[index]
			);

			indexQueue.add (
				index + 1
			);

			pathQueue.add (
				path + "*" + numberArray[index]
			);

			indexQueue.add (
				index + 1
			);

			pathQueue.add (
				path + "" + numberArray[index]
			);
		}

		return expressionOperatorPathList;
	}

	/**
	 * Given a set of <i>non-overlapping</i> intervals, insert a new interval into the intervals (merge if
	 * 	necessary).
	 * 
	 * Assume that the intervals were initially sorted according to their start times.
	 * 
	 * @param intervals Array of the Sorted Intervals
	 * @param newInterval The New Interval
	 * 
	 * @return List of the Merged Intervals
	 */

	public static final java.util.List<int[]> InsertIntoNonOverlappingIntervals (
		final int[][] intervals,
		final int[] newInterval)
	{
		java.util.List<int[]> insertedIntervalList = new java.util.ArrayList<int[]>();

		int[] currentInterval = newInterval;

		for (int[] oldInterval : intervals)
		{
			if (DoRangesOverlap (
				oldInterval,
				currentInterval
			))
			{
				currentInterval[0] = currentInterval[0] < oldInterval[0] ? currentInterval[0] :
					oldInterval[0];
				currentInterval[1] = currentInterval[1] > oldInterval[1] ? currentInterval[1] :
					oldInterval[1];
			}
			else if (oldInterval[1] < currentInterval[0])
			{
				insertedIntervalList.add (
					oldInterval
				);
			}
			else if (currentInterval[1] < oldInterval[0])
			{
				insertedIntervalList.add (
					currentInterval
				);

				currentInterval = oldInterval;
			}
		}

		insertedIntervalList.add (
			currentInterval
		);

		return insertedIntervalList;
	}

	private static final boolean SwapElements (
		final int[] numberArray,
		final int index1,
		final int index2)
	{
		int number = numberArray[index1];
		numberArray[index1] = numberArray[index2];
		numberArray[index2] = number;
		return true;
	}

	/**
	 * Given an array with n objects colored red, white or blue, sort them in-place so that objects of the
	 * 	same color are adjacent, with the colors in the order red, white and blue.
	 * 
	 * Here, use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
	 * 
	 * Note: You are not suppose to use the library's sort function for this problem.
	 * 
	 * @param numberArray The Number Array
	 */

	public static final void SortColor (int[] numberArray)
	{
		int twoIndex = numberArray.length - 1;
		int zeroIndex = 0;

		while (zeroIndex < numberArray.length && 0 == numberArray[zeroIndex]) ++zeroIndex;

		while (0 <= twoIndex && 2 == numberArray[twoIndex]) --twoIndex;

		if (numberArray.length == zeroIndex || -1 == twoIndex) return;

		int index = zeroIndex;

		while (index <= twoIndex) {
			if (0 == numberArray[index])
				SwapElements (numberArray, index, zeroIndex++);
			else if (2 == numberArray[index])
				SwapElements (numberArray, index, twoIndex--);

			++index;
		}
	}

	/**
	 * Construct a Sparse Matrix Representation
	 * 
	 * @param matrix The Sparse Matrix
	 * 
	 * @return Th Sparse Matrix Representation
	 */

	public static final java.util.Map<java.lang.String, java.lang.Integer> SparseMatrixRepresentation (
		final int[][] matrix)
	{
		java.util.Map<java.lang.String, java.lang.Integer> sparseMatrixRepresentation = new
			java.util.HashMap<java.lang.String, java.lang.Integer>();

		for (int i = 0; i < matrix.length; ++i) {
			for (int j = 0; j < matrix[0].length; ++j) {
				if (0 != matrix[i][j]) sparseMatrixRepresentation.put (i + "_" + j, matrix[i][j]);
			}
		}

		return sparseMatrixRepresentation;
	}

	/**
	 * Compute the Dot Product of the Sparse Matrix
	 * 
	 * @param matrix The Sparse Matrix
	 * 
	 * @return Dot Product of the Sparse Matrix
	 */

	public static final int SparseMatrixDotProduct (
		final int[][] matrix)
	{
		int dotProduct = 0;

		java.util.Map<java.lang.String, java.lang.Integer> sparseMatrixRepresentation =
			SparseMatrixRepresentation (matrix);

		java.util.Set<java.lang.String> locationSet = new java.util.HashSet<java.lang.String>();

		locationSet.addAll (sparseMatrixRepresentation.keySet());

		for (java.lang.String location : locationSet) {
			if (sparseMatrixRepresentation.containsKey (location)) {
				java.lang.String[] rowCol = location.split ("_");

				if (sparseMatrixRepresentation.containsKey (rowCol[1] + "_" + rowCol[0])) {
					dotProduct += sparseMatrixRepresentation.get (location) *
						sparseMatrixRepresentation.get (rowCol[1] + "_" + rowCol[0]);

					sparseMatrixRepresentation.remove (rowCol[1] + "_" + rowCol[0]);
				}

				sparseMatrixRepresentation.remove (location);
			}
		}

		return dotProduct;
	}

	/**
	 * Given an array of integers sorted in ascending order, find the starting and ending position of a given
	 *  target value.
	 *  
	 * The algorithm's runtime complexity must be in the order of <i>O(log n)</i>.
	 * 
	 * If the target is not found in the array, return [-1, -1].
	 * 
	 * @param numberArray The Sorted Number Array
	 * @param target The Target Number
	 * 
	 * @return The starting and ending positions.
	 */

	public static final int[] LocationInSortedArray (
		final int[] numberArray,
		final int target)
	{
		int index = LocateIndex (numberArray, 0, numberArray.length - 1, target);

		if (-1 == index) return new int[] {-1, -1};

		int leftLocationIndex = index;
		int rightLocationIndex = index;

		while (leftLocationIndex >= 0 && target == numberArray[leftLocationIndex]) --leftLocationIndex;

		while (rightLocationIndex < numberArray.length && target == numberArray[rightLocationIndex])
			++rightLocationIndex;

		if (-1 == leftLocationIndex || target != numberArray[leftLocationIndex]) ++leftLocationIndex;

		if (numberArray.length == rightLocationIndex || target != numberArray[rightLocationIndex])
			--rightLocationIndex;

		return new int[] {leftLocationIndex, rightLocationIndex};
	}

	/**
	 * Given a m-by-n grid, generate k mines on this grid randomly. Each cell should have equal probability
	 *  of k / (m * n) of being chosen.
	 *  
	 * @param m m
	 * @param n n
	 * @param k k
	 * 
	 * @return k Random Mines on this Grid
	 */

	public static final int[][] RandomMinesInGrid (
		final int m,
		final int n,
		final int k)
	{
		java.util.Set<java.lang.Integer> mineLocationSet = new java.util.HashSet<java.lang.Integer>();

		while (k > mineLocationSet.size()) mineLocationSet.add ((int)(java.lang.Math.random() * m * n));

		int mineIndex = 0;
		int[][] mineLocations = new int[k][2];

		for (int mineLocation : mineLocationSet) {
			mineLocations[mineIndex][0] = mineLocation / m;
			mineLocations[mineIndex++][1] = mineLocation % m;
		}

		return mineLocations;
	}

	/**
	 * Given a matrix, return all elements of the matrix in diagonal flip-flop order.
	 * 
	 * @param matrix The Matrix
	 * 
	 * @return Elements of the matrix in diagonal flip-flop order
	 */

	public static final int[] EnumerateDiagonalFlipFlop (
		final int[][] matrix)
	{
		int index = 0;
		boolean straight = false;
		int[] diagonalFlipFlop = new int[matrix.length * matrix[0].length];

		for (int colIndex = 0; colIndex < matrix.length + matrix[0].length - 1; ++colIndex) {
			if (straight) {
				int scanCol = colIndex;
				int scanRow = 0;

				while (scanCol >= 0 && scanRow <= colIndex) {
					if (scanCol < matrix[0].length && scanRow < matrix.length)
						diagonalFlipFlop[index++] = matrix[scanRow][scanCol];

					++scanRow;
					--scanCol;
				}
			} else {
				int scanCol = 0;
				int scanRow = colIndex;

				while (scanCol <= colIndex && scanRow >= 0) {
					if (scanCol < matrix[0].length && scanRow < matrix.length)
						diagonalFlipFlop[index++] = matrix[scanRow][scanCol];

					--scanRow;
					++scanCol;
				}
			}

			straight = !straight;
		}

		return diagonalFlipFlop;
	}

	/**
	 * Given a matrix, return all elements of the matrix in anti-diagonal order.
	 * 
	 * @param matrix The Matrix
	 * 
	 * @return Elements of the matrix in anti-diagonal order.
	 */

	public static final java.util.List<java.util.List<java.lang.Integer>> EnumerateDiagonalOrder (
		final int[][] matrix)
	{
		java.util.List<java.util.List<java.lang.Integer>> diagonalOrder = new
			java.util.ArrayList<java.util.List<java.lang.Integer>>();

		for (int colIndex = 0; colIndex < matrix.length + matrix[0].length - 1; ++colIndex) {
			int scanCol = colIndex;
			int scanRow = 0;

			java.util.List<java.lang.Integer> diagonalOrderRow = new
				java.util.ArrayList<java.lang.Integer>();

			while (scanCol >= 0 && scanRow <= colIndex) {
				if (scanCol < matrix[0].length && scanRow < matrix.length)
					diagonalOrderRow.add (matrix[scanRow][scanCol]);

				++scanRow;
				--scanCol;
			}

			diagonalOrder.add (diagonalOrderRow);
		}

		return diagonalOrder;
	}

    public static final int[] SlidingWindowMaximum (
    	final int[] numberArray,
    	final int k)
    {
    	int[] maximumSlidingWindowArray = new int[numberArray.length - k + 1];
    	maximumSlidingWindowArray[0] = java.lang.Integer.MIN_VALUE;

    	for (int j = 0; j < k; ++j)
    		maximumSlidingWindowArray[0] = maximumSlidingWindowArray[0] > numberArray[j] ?
    			maximumSlidingWindowArray[0] : numberArray[j];

    	for (int i = 1; i <= numberArray.length - k; ++i) {
        	maximumSlidingWindowArray[i] = java.lang.Integer.MIN_VALUE;

        	for (int j = i; j < i + k; ++j)
        	{
        		maximumSlidingWindowArray[i] = maximumSlidingWindowArray[i] > numberArray[j] ?
        			maximumSlidingWindowArray[i] : numberArray[j];
        	}
    	}

    	return maximumSlidingWindowArray;
    }

    /**
     * Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.
     * 
     * @param matrix The Given Matrix
     * 
     * @return TRUE - The Altered Matrix
     */

    public static final boolean SetMatrixZeroes (
    	final int[][] matrix)
    {
    	java.util.Set<int[]> zeroLocationSet = new java.util.HashSet<int[]>();

    	java.util.Set<java.lang.Integer> processedRowSet = new java.util.HashSet<java.lang.Integer>();

    	java.util.Set<java.lang.Integer> processedColumnSet = new java.util.HashSet<java.lang.Integer>();

    	for (int i = 0; i < matrix.length; ++i) {
        	for (int j = 0; j < matrix[0].length; ++j)
        		if (0 == matrix[i][j]) zeroLocationSet.add(new int[] {i, j});
    	}

    	for (int[] location : zeroLocationSet) {
    		if (!processedRowSet.contains (location[0])) {
	        	for (int j = 0; j < matrix[0].length; ++j)
	        		matrix[location[0]][j] = 0;

	        	processedRowSet.add (location[0]);
    		}

    		if (!processedColumnSet.contains (location[1])) {
	        	for (int i = 0; i < matrix.length; ++i)
	        		matrix[i][location[1]] = 0;

	        	processedRowSet.add (location[1]);
    		}
    	}

    	return true;
    }

    /**
     * Given an unsorted array return whether an increasing subsequence of length 3 exists or not in the
     * 	array.
     * 
     * Formally the function should:
     * 
     * 	Return true if there exists <i>i, j, k</i> such that <i>arr[i] lt arr[j] lt arr[k]</i> given <i>0 le
     * 		i lt j lt le ≤ n-1</i> else return false.
     * 
     * <b>Note</b>: Your algorithm should run in O(n) time complexity and O(1) space complexity.
     * 
     * @param numberArray The Number Array
     * 
     * @return TRUE - Increasing Triplet Subsequence Exists
     */

    public static final boolean IncreasingTripletSubsequenceExists (
    	final int[] numberArray)
    {
    	int minimum = java.lang.Integer.MAX_VALUE;
    	int nextMininum = java.lang.Integer.MAX_VALUE;

    	for (int number : numberArray) {
    		if (number > nextMininum && nextMininum > minimum) return true;

    		if (number <= minimum)
    			minimum = number;
    		else if (number <= nextMininum)
    			nextMininum = number;
    	}

    	return false;
    }

    /**
     * Given four lists A, B, C, D of integer values, compute how many tuples (i, j, k, l) there are such
     *  that A[i] + B[j] + C[k] + D[l] is zero.
     * 
     * @param numberArrayA Number Array A
     * @param numberArrayB Number Array B
     * @param numberArrayC Number Array C
     * @param numberArrayD Number Array D
     * 
     * @return Count of the Zero 4 Sums
     */

    public static final int FourSumCount (
    	final int[] numberArrayA,
    	final int[] numberArrayB,
    	final int[] numberArrayC,
    	final int[] numberArrayD)
    {
    	int fourSumCount = 0;

    	java.util.HashSet<java.lang.Integer> arrayABHashSet = new java.util.HashSet<java.lang.Integer>();

    	for (int numberA : numberArrayA) {
    		for (int numberB : numberArrayB)
        		arrayABHashSet.add (-1 * (numberA + numberB));
    	}

    	for (int numberC : numberArrayC) {
    		for (int numberD : numberArrayD)
        		if (arrayABHashSet.contains (numberC + numberD)) ++fourSumCount;
    	}

    	return fourSumCount;
    }

    /**
     * Given non-negative integers a<sub>1</sub>, a<sub>2</sub>, ..., a<sub>n</sub> , where each represents a
     *  point at coordinate (i, a<sub>i</sub>). n vertical lines are drawn such that the two end-points of
     *  line i is at (i, a<sub>i</sub>) and (i, 0). Find two lines, which together with x-axis forms a
     *  container, such that the container contains the most water.
     * 
     * @param heightArray Array of Heights
     * 
     * @return The Line Locations and the Maximum Container Area
     */

    public static final int[] MaximumAreaUnderContainer (
    	final int[] heightArray)
    {
    	int[] leftMaxHeightIndex = new int[heightArray.length];
    	int[] rightMaxHeightIndex = new int[heightArray.length];
    	int maximumAreaUnderContainer = java.lang.Integer.MIN_VALUE;
    	rightMaxHeightIndex[heightArray.length - 1] = heightArray.length - 1;
    	leftMaxHeightIndex[0] = 0;
    	int rightIndex = 0;
    	int leftIndex = 0;

    	for (int i = 1; i < heightArray.length; ++i)
    		leftMaxHeightIndex[i] = heightArray[leftMaxHeightIndex[i - 1]] >= heightArray[i - 1] ?
    			leftMaxHeightIndex[i - 1] : i - 1;

    	for (int i = heightArray.length - 2; i >= 0; --i)
    		rightMaxHeightIndex[i] = heightArray[rightMaxHeightIndex[i + 1]] >= heightArray[i + 1] ?
    			rightMaxHeightIndex[i + 1] : i + 1;

    	for (int i = 0; i < heightArray.length; ++i) {
    		int areaThroughLevel = (rightMaxHeightIndex[i] - leftMaxHeightIndex[i]) * java.lang.Math.min
    			(heightArray[leftMaxHeightIndex[i]], heightArray[rightMaxHeightIndex[i]]);

    		int areaLeftOfLevel = (i - leftMaxHeightIndex[i]) * java.lang.Math.min
    			(heightArray[leftMaxHeightIndex[i]], heightArray[i]);

    		int areaRightOfLevel = (rightMaxHeightIndex[i] - i) * java.lang.Math.min (heightArray[i],
    			heightArray[rightMaxHeightIndex[i]]);

    		if (maximumAreaUnderContainer < areaThroughLevel) {
    			maximumAreaUnderContainer = areaThroughLevel;
    			rightIndex = rightMaxHeightIndex[i];
    			leftIndex = leftMaxHeightIndex[i];
    		}

    		if (maximumAreaUnderContainer < areaLeftOfLevel) {
    			maximumAreaUnderContainer = areaLeftOfLevel;
    			leftIndex = leftMaxHeightIndex[i];
    			rightIndex = i;
    		}

    		if (maximumAreaUnderContainer < areaRightOfLevel) {
    			maximumAreaUnderContainer = areaRightOfLevel;
    			rightIndex = rightMaxHeightIndex[i];
    			leftIndex = i;
    		}
    	}

    	return new int[] {leftIndex, rightIndex, maximumAreaUnderContainer};
    }

    public static final int FirstMisingPositiveInteger (
    	final int[] numberArray)
    {
    	int firstIndex = 0;
    	int firstMisingPositiveInteger = 1;

    	while (firstIndex < numberArray.length && numberArray[firstIndex] <= 0) ++firstIndex;

    	if (firstIndex == numberArray.length) return 1;

    	java.util.Arrays.sort (numberArray);

    	return firstMisingPositiveInteger;
    }

    public static final void main (
		final String[] argumentArray)
		throws java.lang.Exception
	{
    	int[] locationArea = MaximumAreaUnderContainer (new int[] {1, 8, 6, 2, 5, 4, 8, 3, 7});

    	System.out.println (locationArea[0] + " | " + locationArea[1] + " => " + locationArea[2]);
	}
}
