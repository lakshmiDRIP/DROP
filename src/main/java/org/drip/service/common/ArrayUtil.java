
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

	private static final boolean SpiralMatrixOrder (
		final java.util.List<java.lang.Integer> spiralMatrixOrder,
		final int[][] matrix,
		final int rowShift,
		final int columnShift)
	{
		int rowCount = matrix.length;
		int columnCount = matrix[0].length;

		if (rowShift > rowCount - rowShift ||
			columnShift > columnCount - columnShift)
		{
			return true;
		}

		for (int columnIndex = columnShift;
			columnIndex < columnCount - columnShift;
			++columnIndex)
		{
			spiralMatrixOrder.add (
				matrix[rowShift][columnIndex]
			);
		}

		if (2 * rowShift < rowCount)
		{
			for (int rowIndex = rowShift + 1;
				rowIndex < rowCount - rowShift - 1;
				++rowIndex)
			{
				spiralMatrixOrder.add (
					matrix[rowIndex][columnCount - columnShift - 1]
				);
			}
		}

		if (columnCount >= columnShift + 1 &&
			2 * rowShift != rowCount - 1)
		{
			for (int columnIndex = columnCount - columnShift - 1;
				columnIndex >= columnShift;
				--columnIndex)
			{
				spiralMatrixOrder.add (
					matrix[rowCount - rowShift - 1][columnIndex]
				);
			}
		}

		if (rowCount >= rowShift + 2)
		{
			for (int rowIndex = rowCount - rowShift - 2;
				rowIndex >= rowShift + 1;
				--rowIndex)
			{
				spiralMatrixOrder.add (
					matrix[rowIndex][columnShift]
				);
			}
		}

		return SpiralMatrixOrder (
			spiralMatrixOrder,
			matrix,
			rowShift + 1,
			columnShift + 1
		);
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
	 * 	array.  (Formally, C[i] = A[i] when 0 <= i < A.length, and C[i+A.length] = C[i] when i gte 0.)
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
		java.util.List<java.lang.Integer> spiralMatrixOrder =
			new java.util.ArrayList<java.lang.Integer>();

		return SpiralMatrixOrder (
			spiralMatrixOrder,
			matrix,
			0,
			0
		) ? spiralMatrixOrder : null;
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

	public static final void main (
		String[] args)
	{
		java.lang.String s = "abcda";
		int[][] queries = new int[][]
		{
			{3, 3, 0},
			{1, 2, 0},
			{0, 3, 1},
			{0, 3, 2},
			{0, 4, 1},
		};

		System.out.println (
			CanMakePalindromeQueries (
				s,
				queries
			)
		);
	}
}
