
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

	/* public static final double MaximumSubarrayCumCircular (
		final double[] numberArray)
	{
		
	} */

	public static final void main (
		String[] args)
	{
		int target = 0;
		int[] numberArray = new int[]
		{
			 1,
			 0,
			-1,
			 0,
			-2,
			 2,
		};

		System.out.println (
			FourSum (
				numberArray,
				target
			)
		);
	}
}
