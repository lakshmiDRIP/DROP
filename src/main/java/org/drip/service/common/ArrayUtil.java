
package org.drip.service.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
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
 * <i>ArrayUtil</i> implements Generic Array Utility Functions used in DROP modules. It provides the
 * 	following Functions:
 * 
 * <ul>
 * 		<li>Affix the Headers on the JSON Request</li>
 * 		<li>Affix the Headers on the JSON Response</li>
 * </ul>
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

public class ArrayUtil
{

	private static final int PivotIndex (
		final int[] numberArray,
		int leftIndex,
		int rightIndex)
	{
		while (numberArray[leftIndex] > numberArray[rightIndex]) {
			int midIndex = (leftIndex + rightIndex) / 2;

			if (numberArray[midIndex] > numberArray[midIndex + 1]) {
				return midIndex;
			}

			if (numberArray[leftIndex] > numberArray[midIndex]) {
				rightIndex = midIndex;
			} else {
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

		while (leftIndex < rightIndex + 1) {
			if (numberArray[midIndex] == target) {
				return midIndex;
			}

			if (numberArray[midIndex] < target) {
				leftIndex = midIndex;
			} else {
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
		boolean forward = 0 < numberArray[index];

		Set<Integer> indexSet = new HashSet<Integer>();

		while (arraySize > indexSet.size()) {
			if (indexSet.contains (index)) {
				return indexSet.size();
			}

			if (forward != 0 < numberArray[index]) {
				return -1;
			}

			indexSet.add (index);

			index = index + numberArray[index];
			index = 0 > index ? index + arraySize : index - arraySize;
		}

		return -1;
	}

	private static final int SlidingWindowCount (
		final int[] numberArray,
		final int index,
		final int windowSize)
	{
		int count = 0;

		for (int left = 0; left <= numberArray.length - windowSize; ++left) {
			if (index >= left && index <= left + windowSize - 1) {
				++count;
			}
		}

		return count;
	}

	private static final boolean CanMakePalindrome (
		final String s,
		final int left,
		final int right,
		final int k)
	{
		char[] charArray = s.toCharArray();

		Set<Character> charSet = new HashSet<Character>();

		for (int charIndex = left; charIndex <= right; ++charIndex) {
			if (charSet.contains (charArray[charIndex])) {
				charSet.remove (charArray[charIndex]);
			} else {
				charSet.add (charArray[charIndex]);
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
		while (startIndex <= endIndex) {
			if (a[startIndex++] != b[endIndex--]) {
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

		while (startIndex < endIndex) {
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
		final String leftNumber,
		final String operation,
		final String rightNumber)
	{
		int left = StringUtil.DecimalNumberFromString (leftNumber);

		int right = StringUtil.DecimalNumberFromString (rightNumber);

		if ("/".equalsIgnoreCase (operation)) {
			return left / right;
		}

		if ("*".equalsIgnoreCase (operation)) {
			return left * right;
		}

		if ("-".equalsIgnoreCase (operation)) {
			return left - right;
		}

		return left + right;
	}

	private static final boolean CollapseOperation (
		final List<String> elementList,
		final String operation)
	{
		int elementCount = elementList.size();

		for (int elementIndex = elementCount - 1; 0 <= elementIndex; --elementIndex) {
			if (operation.equalsIgnoreCase (elementList.get (elementIndex))) {
				if (0 == elementIndex || elementIndex == elementCount - 1) {
					return false;
				}

				int result = ApplyOperation (
					elementList.get (elementIndex - 1),
					operation,
					elementList.get (elementIndex + 1)
				);

				elementList.remove (elementIndex + 1);

				elementList.remove (elementIndex);

				elementList.set (elementIndex - 1, "" + result);
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
		if (leftIndex == rightIndex) {
			return number == numberArray[leftIndex] ? leftIndex : -1;
		}

		int midIndex = (leftIndex + rightIndex) / 2;

		if (number == numberArray[midIndex]) {
			return midIndex;
		}

		return number < numberArray[midIndex] ? LocateIndex (numberArray, leftIndex - 1, midIndex, number) :
			LocateIndex (numberArray, midIndex + 1, rightIndex, number);
	}

    private static final TreeMap<Integer, int[]> DisaggregateSkyscrapers (
    	final int currentLeft,
    	final int[] currentRightHeight,
    	final int newLeft,
    	final int[] newRightHeight)
    {
    	TreeMap<Integer, int[]> disaggregationMap = new TreeMap<Integer, int[]>();

    	if (currentRightHeight[0] <= newLeft) {
        	disaggregationMap.put (currentLeft, currentRightHeight);

        	disaggregationMap.put (newLeft, newRightHeight);

        	return disaggregationMap;
    	}

    	if (newRightHeight[0] <= currentLeft) {
        	disaggregationMap.put (newLeft, newRightHeight);

        	disaggregationMap.put (currentLeft, currentRightHeight);

        	return disaggregationMap;
    	}

    	int first = -1;
    	int third = -1;
    	int fourth = -1;
    	int second = -1;
    	int height12 = -1;
    	int height34 = -1;
    	int height23 = currentRightHeight[1] > newRightHeight[1] ? currentRightHeight[1] : newRightHeight[1];

    	if (newLeft <= currentLeft) {
        	first = newLeft;
        	second = currentLeft;
        	height12 = newRightHeight[1];
    	} else {
        	second = newLeft;
        	first = currentLeft;
        	height12 = currentRightHeight[1];
    	}

    	if (currentRightHeight[0] <= newRightHeight[0]) {
	    	third = currentRightHeight[0];
	    	height34 = newRightHeight[1];
	    	fourth = newRightHeight[0];
    	} else {
	    	third = newRightHeight[0];
	    	height34 = currentRightHeight[1];
	    	fourth = currentRightHeight[0];
    	}

    	if (first < second) {
    		disaggregationMap.put (first, new int[] {second, height12});
    	}

    	disaggregationMap.put (second, new int[] {third, height23});

    	if (third < fourth) {
    		disaggregationMap.put (third, new int[] {fourth, height34});
    	}

    	return disaggregationMap;
    }

    private static final List<int[]> NondecreasingSequenceList (
    	final int[] numberArray)
    {
    	int leftIndex = 0;
    	int rightIndex = 0;

    	List<int[]> nondecreasingSequenceList = new ArrayList<int[]>();

    	for (int i = 1; i < numberArray.length; ++i) {
    		if (numberArray[i] >= numberArray[i - 1])
    			++rightIndex;
    		else {
    			nondecreasingSequenceList.add (new int[] {leftIndex, rightIndex});

    			rightIndex = i;
    			leftIndex = i;
    		}
    	}

		nondecreasingSequenceList.add (new int[] {leftIndex, numberArray.length - 1});

    	return nondecreasingSequenceList;
    }

    private static final boolean IsPrime (
    	final Set<Integer> primeSet,
    	final int number)
    {
    	int maximumPrime = 1;

    	for (int prime : primeSet) {
    		maximumPrime = maximumPrime < prime ? prime : maximumPrime;

    		if (0 == number % prime) {
    			return false;
    		}
    	}

    	for (int nextNumber = maximumPrime + 1; nextNumber <= 1 + (int) Math.sqrt (number); ++nextNumber) {
    		if (IsPrime (primeSet, nextNumber)) {
    			primeSet.add (nextNumber);

        		if (0 == number % nextNumber) {
        			return false;
        		}
    		}
    	}

    	return true;
    }

	private static final int[] ExcludeAndSort (
		final int[] numberArray,
		final int excludeIndex)
	{
		int excludeAndSortIndex = 0;
		int excludeAndSortArraySize = numberArray.length - 1;
		int[] excludeAndSortArray = new int[excludeAndSortArraySize];

		for (int numberArrayIndex = 0; numberArrayIndex < numberArray.length; ++numberArrayIndex) {
			if (numberArrayIndex != excludeIndex) {
				excludeAndSortArray[excludeAndSortIndex++] = numberArray[numberArrayIndex];
			}
		}

		Arrays.sort (excludeAndSortArray);

		return excludeAndSortArray;
	}

	private static final boolean SpiralMatrixTopBottom (
		final List<Integer> elementList,
		final int[][] matrix,
		final int top,
		final int bottom,
		final int col,
		final boolean reverse)
	{
		if (top > bottom) {
			return true;
		}

		if (!reverse) {
			int row = top;

			while (row <= bottom) {
				elementList.add (matrix[row++][col]);
			}
		} else {
			int row = bottom;

			while (row >= top) {
				elementList.add (matrix[row--][col]);
			}
		}

		return true;
	}

	private static final boolean SpiralMatrixLeftRight (
		final List<Integer> elementList,
		final int[][] matrix,
		final int left,
		final int right,
		final int row,
		final boolean reverse)
	{
		if (right < left) {
			return true;
		}

		if (!reverse) {
			int col = left;

			while (col <= right) {
				elementList.add (matrix[row][col++]);
			}
		} else {
			int col = right;

			while (col >= left) {
				elementList.add (matrix[row][col--]);
			}
		}

		return true;
	}

	private static final int FourSeaterCount (
		final int[] row)
	{
		int left = 1;
		int right = 1;
		int center = 1;

		if (1 == row[1] || 1 == row[2]) {
			left = 0;
		}

		if (1 == row[3] || 1 == row[4]) {
			left = 0;
			center = 0;
		}

		if (1 == row[5] || 1 == row[6]) {
			right = 0;
			center = 0;
		}

		if (1 == row[7] || 1 == row[8]) {
			right = 0;
		}

		if (1 == left && 1 == right) {
			center = 0;
		}

		return left + center + right;
	}

	private static final boolean ConditionalStep (
		final int x,
		final int y,
		final int wordCharIndex,
		final char[][] board,
		final char[] wordCharArray,
		final List<int[]> traversalList,
		final Set<Integer> visitedLocationSet)
	{
		if (x >= 0 &&
			x < board.length &&
			y >= 0 &&
			y < board[0].length &&
			wordCharIndex < wordCharArray.length)
		{
			int nextLocation = x * board.length + y;

			if (wordCharArray[wordCharIndex] == board[x][y] && !visitedLocationSet.contains (nextLocation)) {
				traversalList.add (new int[] {x, y, wordCharIndex});

				visitedLocationSet.add (nextLocation);
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

		List<int[]> traversalList = new ArrayList<int[]>();

		Set<Integer> visitedLocationSet = new HashSet<Integer>();

		traversalList.add (new int[] {startX, startY, 0});

		visitedLocationSet.add (startX * board.length + startY);

		while (!traversalList.isEmpty()) {
			int[] traversalItem = traversalList.remove (0);

			int x = traversalItem[0];
			int y = traversalItem[1];
			int wordCharIndex = traversalItem[2];

			if (wordCharArray[wordCharIndex] != board[x][y]) {
				continue;
			}

			if (wordCharIndex == wordLastIndex) {
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

	private static final int KthHighestOrderStatistic (
		final int[] numberArray,
		final int k)
	{
		PriorityQueue<Integer> heap = new PriorityQueue<Integer>();

		int arrayCount = numberArray.length;

		for (int i = 0; i < arrayCount; ++i) {
			if (i < k) {
				heap.offer (numberArray[i]);
			} else {
				if (numberArray[i] > heap.peek()) {
					heap.poll();

					heap.offer (numberArray[i]);
				}
			}
		}

		return heap.peek();
	}

	private static final boolean GeneratePathCombinations (
		final List<List<Integer>> pathIndexList,
		final int currentIndex,
		final int[] numberArray)
	{
		if (currentIndex == numberArray.length) {
			return true;
		}

		List<List<Integer>> currentPathIndexList = new ArrayList<List<Integer>>();

		List<Integer> currentIndexPath = new ArrayList<Integer>();

		currentIndexPath.add (numberArray[currentIndex]);

		currentPathIndexList.add (currentIndexPath);

		for (List<Integer> pathIndex : pathIndexList) {
			List<Integer> currentPath = new ArrayList<Integer>();

			currentPath.addAll (pathIndex);

			currentPath.add (numberArray[currentIndex]);

			currentPathIndexList.add (pathIndex);
		}

		pathIndexList.addAll (currentPathIndexList);

		return GeneratePathCombinations (pathIndexList, currentIndex + 1, numberArray);
	}

	private static final int[] FirstAndLastPosition (
		final int leftIndex,
		final int rightIndex,
		final int[] numberArray,
		final int target)
	{
		int arrayLength = numberArray.length;
		int midIndex = (leftIndex + rightIndex) / 2;

		if (rightIndex == leftIndex || rightIndex == leftIndex + 1) {
			if (numberArray[leftIndex] != target && numberArray[rightIndex] != target) {
				return new int[] {-1, -1};
			}
		}

		if (numberArray[midIndex] == target) {
			int leftLocation = midIndex;
			int rightLocation = midIndex;

			while (leftLocation >= 0 && numberArray[leftLocation] == target) {
				--leftLocation;
			}

			while (rightLocation < arrayLength && numberArray[rightLocation] == target) {
				++rightLocation;
			}

			return new int[] {
				numberArray[leftLocation] == target ? leftLocation : leftLocation + 1,
				numberArray[rightLocation] == target ? rightLocation : rightLocation - 1
			};
		}
		else if (target < numberArray[midIndex]) {
			return FirstAndLastPosition (leftIndex, midIndex, numberArray, target);
		}

		return FirstAndLastPosition (midIndex, rightIndex, numberArray, target);
	}

	private static final boolean MarkIslands (
		final int xStart,
		final int yStart,
		final int[][] islandMarkerGrid,
		final int[][] locationGrid)
	{
		int rowCount = locationGrid.length;
		int columnCount = locationGrid[0].length;

		List<int[]> locationList = new ArrayList<int[]>();

		locationList.add (new int[] {xStart, yStart});

		while (!locationList.isEmpty()) {
			int[] location = locationList.remove (0);

			int x = location[0];
			int y = location[1];
			int xNext = x + 1;
			int yNext = y + 1;
			islandMarkerGrid[x][y] = 1;

			if (xNext < rowCount && 0 != locationGrid[xNext][y]) {
				locationList.add (new int[] {xNext, y});
			}

			if (yNext < columnCount && 0 != locationGrid[x][yNext]) {
				locationList.add (new int[] {x, yNext});
			}
		}

		return true;
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

		for (int index = 0; index < lotSizeArray.length; ++index) {
			timeConsumed = timeConsumed + RoundLotUnits (lotSizeArray[index], rate);
		}

		return timeConsumed;
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

		int pivotIndex = PivotIndex (numberArray, 0, midIndex);

		if (-1 == pivotIndex) {
			pivotIndex = PivotIndex (numberArray, midIndex + 1, arrayLength - 1);
		}

		return numberArray[rightIndex] < target ?
			-1 != SearchPivotIndex (numberArray, target, 0, pivotIndex) :
			-1 != SearchPivotIndex (numberArray, target, pivotIndex + 1, rightIndex);
	}

	/**
	 * Given an array of n integers, find all unique triplets in the array which gives the sum of zero.
	 * 
	 * @param numberArray The Number Array
	 * 
	 * @return The List of Unique Triplets
	 */

	public static final Map<String, int[]> ThreeSum (
		final int[] numberArray)
	{
		Map<String, int[]> tripletMap = new HashMap<String, int[]>();

		for (int numberArrayIndex = 0; numberArrayIndex < numberArray.length; ++numberArrayIndex) {
			int[] excludeAndSortArray = ExcludeAndSort (numberArray, numberArrayIndex);

			for (int excludeAndSortArrayIndex = 0;
				excludeAndSortArrayIndex < excludeAndSortArray.length;
				++excludeAndSortArrayIndex)
			{
				int tripletIndex = excludeAndSortArrayIndex + 1;
				int target = numberArray[numberArrayIndex] + excludeAndSortArray[excludeAndSortArrayIndex];

				while (tripletIndex < excludeAndSortArray.length && 
					target + excludeAndSortArray[tripletIndex] <= 0)
				{
					if (0 == target + excludeAndSortArray[tripletIndex]) {
						int[] keyElementArray = new int[] {
							numberArray[numberArrayIndex],
							excludeAndSortArray[excludeAndSortArrayIndex],
							excludeAndSortArray[tripletIndex]
						};

						Arrays.sort (keyElementArray);

						tripletMap.put (
							keyElementArray[0] + "@" + keyElementArray[1] + "@" + keyElementArray[2] + "@",
							new int[] {
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
		for (int arrayIndex = 0; arrayIndex < numberArray.length; ++arrayIndex) {
			int circleLength = CircularArrayLoopLength (numberArray, arrayIndex);

			if (-1 != circleLength) {
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
	 *  and vertical position provided in the arrays horizontalCuts and verticalCuts.
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

		for (int horizontalCutIndex = 0; horizontalCutIndex < horizontalCutCount; ++horizontalCutIndex) {
			horizontalCutArray[horizontalCutIndex] = horizontalCuts[horizontalCutIndex];
		}

		Arrays.sort (horizontalCutArray);

		for (int verticalCutIndex = 0; verticalCutIndex < verticalCutCount; ++verticalCutIndex) {
			verticalCutArray[verticalCutIndex] = verticalCuts[verticalCutIndex];
		}

		Arrays.sort (verticalCutArray);

		for (int verticalCutIndex = 0; verticalCutIndex <= verticalCutCount; ++verticalCutIndex) {
			int width = verticalCutArray[verticalCutIndex] - (
				0 == verticalCutIndex ? 0 : verticalCutArray[verticalCutIndex - 1]
			);

			for (int horizontalCutIndex = 0; horizontalCutIndex <= horizontalCutCount; ++horizontalCutIndex)
			{
				int sliceArea = width * (horizontalCutArray[horizontalCutIndex] - (
					0 == horizontalCutIndex ? 0 : horizontalCutArray[horizontalCutIndex - 1]
				));

				if (maxArea < sliceArea) {
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

	public static final Collection<String> InvalidTransactions (
		final String[] transactionArray)
	{
		Map<String, String> invalidTransactionMap = new HashMap<String, String>();

		boolean first = true;
		int prevTransactionTime = -1;
		String prevTransaction = "";
		String prevTransactionCity = "";
		String prevTransactionName = "";

		for (String transaction : transactionArray) {
			String[] transactionDetailArray = transaction.split (",");

			String transactionCity = transactionDetailArray[3];
			String transactionName = transactionDetailArray[0];

			int transactionTime = Integer.parseInt (transactionDetailArray[1]);

			int transactionAmount = Integer.parseInt (transactionDetailArray[2]);

			if (!first) {
				boolean sequenceViolation = 60 >= transactionTime - prevTransactionTime &&
					transactionName.equalsIgnoreCase (prevTransactionName) &&
					!transactionCity.equalsIgnoreCase (prevTransactionCity);

				if (sequenceViolation) {
					invalidTransactionMap.put (prevTransaction, prevTransaction);

					invalidTransactionMap.put (transaction, transaction);
				} else if (1000 < transactionAmount) {
					invalidTransactionMap.put (transaction, transaction);
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

		for (int i = 1; i < numberArray.length; ++i) {
			int temp = max;

			max = Math.max (max * numberArray[i], Math.max (min * numberArray[i], numberArray[i]));

			min = Math.min (temp * numberArray[i], Math.min (min * numberArray[i], numberArray[i]));

			if (max > result) {
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

	public static final int SubarrayMinimumSum (
		final int[] numberArray)
	{
		if (1 == numberArray.length) {
			return numberArray[0];
		}

		int minIndex = -1;
		int minValueInstanceCount = 0;
		int minValue = Integer.MAX_VALUE;

		for (int index = 0; index < numberArray.length; ++index) {
			if (minValue > numberArray[index]) {
				minIndex = index;
				minValue = numberArray[index];
			}
		}

		for (int index = 0; index < numberArray.length; ++index) {
			minValueInstanceCount = minValueInstanceCount + SlidingWindowCount (
				numberArray,
				minIndex,
				index + 1
			);
		}

		if (0 == minIndex) {
			int[] numberSubarray = new int[numberArray.length - 1];

			for (int index = 1; index < numberArray.length; ++index) {
				numberSubarray[index - 1] = numberArray[index];
			}

			return minValueInstanceCount * minValue + SubarrayMinimumSum (numberSubarray);
		}

		if (minIndex == numberArray.length - 1) {
			int[] numberSubarray = new int[numberArray.length - 1];

			for (int index = 0; index < numberArray.length - 1; ++index) {
				numberSubarray[index] = numberArray[index];
			}

			return minValueInstanceCount * minValue + SubarrayMinimumSum (numberSubarray);
		}

		int[] leftSubarray = new int[minIndex];
		int[] rightSubarray = new int[numberArray.length - minIndex - 1];

		for (int index = 0; index < numberArray.length; ++index) {
			if (minIndex > index) {
				leftSubarray[index] = numberArray[index];
			} else if (minIndex < index) {
				rightSubarray[index - minIndex - 1] = numberArray[index];
			}
		}

		return minValueInstanceCount * minValue + SubarrayMinimumSum (leftSubarray) +
			SubarrayMinimumSum (rightSubarray);
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

	public static final Collection<List<Integer>> FourSum (
		final int[] numberArray,
		final int target)
	{
		Map<String, List<Integer>> fourSumListMap = new HashMap<String, List<Integer>>();

		for (int exclusionIndex = 0; exclusionIndex < numberArray.length; ++exclusionIndex) {
			int[] excludedAndSortedArray = ExcludeAndSort (numberArray, exclusionIndex);

			for (int excludedAndSortedArrayIndex = 0;
				excludedAndSortedArrayIndex < excludedAndSortedArray.length;
				++excludedAndSortedArrayIndex)
			{
				for (int threeSumIndex = excludedAndSortedArrayIndex + 1;
					threeSumIndex < excludedAndSortedArray.length;
					++threeSumIndex)
				{
					int fourSumIndex = excludedAndSortedArray.length - 1;

					while (fourSumIndex > threeSumIndex) {
						int sum = numberArray[exclusionIndex] +
							excludedAndSortedArray[excludedAndSortedArrayIndex] +
							excludedAndSortedArray[threeSumIndex] +
							excludedAndSortedArray[fourSumIndex];

						if (target == sum) {
							int[] fourSumComponentArray = new int[4];
							fourSumComponentArray[0] = numberArray[exclusionIndex];
							fourSumComponentArray[1] = excludedAndSortedArray[excludedAndSortedArrayIndex];
							fourSumComponentArray[2] = excludedAndSortedArray[threeSumIndex];
							fourSumComponentArray[3] = excludedAndSortedArray[fourSumIndex];

							Arrays.sort (fourSumComponentArray);

							List<Integer> fourSumList = new ArrayList<Integer>();

							fourSumList.add (numberArray[exclusionIndex]);

							fourSumList.add (excludedAndSortedArray[excludedAndSortedArrayIndex]);

							fourSumList.add (excludedAndSortedArray[threeSumIndex]);

							fourSumList.add (excludedAndSortedArray[fourSumIndex]);

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
		int maxSum = Integer.MIN_VALUE;

		while (endIndex < numberArray.length) {
			int currentSum = 0;

			while (endIndex < numberArray.length && 0 <= numberArray[endIndex]) {
				currentSum = currentSum + numberArray[endIndex++];
			}

			while (startIndex <= endIndex && 0 >= numberArray[startIndex]) {
				currentSum = currentSum - numberArray[startIndex++];
			}

			if (currentSum > maxSum) {
				maxSum = currentSum;
			}

			++endIndex;
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
		int minSum = Integer.MAX_VALUE;

		while (endIndex < numberArray.length) {
			int currentSum = 0;

			while (endIndex < numberArray.length && 0 >= numberArray[endIndex]) {
				currentSum = currentSum + numberArray[endIndex++];
			}

			while (startIndex <= endIndex && 0 >= numberArray[startIndex]) {
				currentSum = currentSum - numberArray[startIndex++];
			}

			if (currentSum < minSum) {
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
		int max = Integer.MIN_VALUE;

		for (int index = 0; index < numberArray.length; ++index) {
			if (0 > numberArray[index]) {
				allPositive = false;
			} else if (0 < numberArray[index]) {
				allNegative = false;
			}

			sum = sum + numberArray[index];

			if (max < numberArray[index]) {
				max = numberArray[index];
			}
		}

		if (allPositive) {
			return sum;
		}

		if (allNegative) {
			return max;
		}

		return Math.max (MaximumSubarraySum (numberArray), sum - MinimumSubarraySum (numberArray));
	}

	/**
	 * Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral
	 * 	order.
	 * 
	 * @param matrix m x n Matrix
	 * 
	 * @return Elements of the matrix in spiral order.
	 */

	public static final List<Integer> SpiralMatrixOrder (
		final int[][] matrix)
	{
		List<Integer> elementList = new ArrayList<Integer>();

		int top = 0;
		int row = -1;
		int left = -1;
		int col = matrix[0].length;
		int bottom = matrix.length - 1;
		int right = matrix[0].length - 1;

		while (true) {
			SpiralMatrixLeftRight (elementList, matrix, ++left, right, ++row, false);

			if (left > right) {
				break;
			}

			SpiralMatrixTopBottom (elementList, matrix, ++top, bottom, --col, false);

			if (top > bottom) {
				break;
			}

			SpiralMatrixLeftRight (elementList, matrix, left, --right, matrix.length - 1 - row, true);

			if (left > right) {
				break;
			}

			SpiralMatrixTopBottom (elementList, matrix, top, --bottom, matrix[0].length - 1 - col, true);

			if (top > bottom) {
				break;
			}
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

		List<int[]> locationList = new ArrayList<int[]>();

		locationList.add (new int[] {0, 0});

		while (!locationList.isEmpty()) {
			int[] location = locationList.remove (0);

			int locationX = location[0];
			int locationY = location[1];

			if (locationX == width - 1 && locationY == height - 1) {
				++uniquePathsWithObstacles;
				continue;
			}

			int down = locationY + 1;
			int right = locationX + 1;

			if (right < width && 1 != obstacleGrid[right][locationY]) {
				locationList.add (new int[] {right, locationY});
			}

			if (down < height && 1 != obstacleGrid[locationX][down]) {
				locationList.add (new int[] {locationX, down});
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
	 * @param queryGrid Array of Queries
	 * 
	 * @return List of Query Results
	 */

	public static final List<Boolean> CanMakePalindromeQueries (
		final String s,
		final int[][] queryGrid)
	{
		List<Boolean> queryResultArray = new ArrayList<Boolean>();

		for (int[] query : queryGrid) {
			queryResultArray.add (CanMakePalindrome (s, query[0], query[1], query[2]));
		}

		return queryResultArray;
	}

	/**
	 * Find all pairs of integers in the array which have difference equal to the number d.
	 * 
	 * @param numberArray The Number Array
	 * @param x The Difference
	 * 
	 * @return All pairs of integers in the array which have difference equal to the number d.
	 */

	public static final List<int[]> ArrayPairList (
		final int[] numberArray,
		final int x)
	{
		if (null == numberArray || 0 == numberArray.length) {
			return null;
		}

		List<int[]> arrayPairList = new ArrayList<int[]>();

		HashSet<Integer> numberSet = new HashSet<Integer>();

		for (int number : numberArray) {
			if (numberSet.contains (number - x)) {
				arrayPairList.add (new int[] {number, number - x});
			} else if (numberSet.contains (number + x)) {
				arrayPairList.add (new int[] {number, number + x});
			}

			numberSet.add (number);
		}

		return arrayPairList;
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
	 * @param reservedSeatGrid Locations of Reserved Seats
	 * 
	 * @return Maximum number of four-person groups.
	 */

	public static final int MaximumNumberOfFamilies (
		final int n,
		final int[][] reservedSeatGrid)
	{
		int maximumNumberOfFamilies = 0;
		int[][] seatGrid = new int[n][10];

		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < 10; ++j) {
				seatGrid[i][j] = 0;
			}
		}

		for (int[] reservedSeat : reservedSeatGrid) {
			seatGrid[reservedSeat[0] - 1][reservedSeat[1] - 1] = 1;
		}

		for (int[] seatRow : seatGrid) {
			maximumNumberOfFamilies = maximumNumberOfFamilies + FourSeaterCount (seatRow);
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
		List<Integer> traversalList = new ArrayList<Integer>();

		Set<Integer> visitedSet = new HashSet<Integer>();

		int lastIndex = numberArray.length;

		traversalList.add (0);

		visitedSet.add (0);

		while (!traversalList.isEmpty()) {
			int index = traversalList.remove (0);

			if (index == lastIndex) {
				return true;
			}

			for (int jump = 0; jump <= numberArray[index]; ++jump) {
				int nextLocation = index + jump;

				if (!visitedSet.contains (nextLocation)) {
					traversalList.add (nextLocation);

					visitedSet.add (nextLocation);
				}
			}
		}

		return false;
	}

	/**
	 * Given a 2D board and a word, find if the word exists in the grid.
	 * 
	 * The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are
	 *  those horizontally or vertically neighboring. The same letter cell may not be used more than once.
	 *  
	 * @param boardGrid The Board Grid
	 * @param word The Word
	 * 
	 * @return TRUE - The Word exists on the board
	 */

	public static final boolean WordExistsInBoard (
		final char[][] boardGrid,
		final String word)
	{
		char[] wordCharArray = word.toCharArray();

		for (int row = 0; row < boardGrid.length; ++row) {
			for (int column = 0; column < boardGrid[0].length; ++column) {
				if (wordCharArray[0] == boardGrid[row][column]) {
					if (WordExists (row, column, boardGrid, wordCharArray)) {
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

		while (index < length) {
			while (index < length && a[index] == b[index]) {
				++index;
			}

			mismatchStartIndex = index;

			while (index < length && a[mismatchStartIndex] != b[index]) {
				++index;
			}

			if (index == length || !ReverseMatchPossible (a, b, mismatchStartIndex, index)) {
				return false;
			}

			++index;
		}

		return true;
	}

	/**
	 * There are n students, numbered from 1 to n, each with their own year-book. They would like to pass
	 * 	their year-books around and get them signed by other students.
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
		int[] bookArray = new int[personCount];
		int[] signatureArray = new int[personCount];

		Set<Integer> bookSetNotAtOwner = new HashSet<Integer>();

		for (int person = 0; person < personCount; ++person) {
			signatureArray[person] = 1;
			bookArray[person] = studentArray[person] - 1;

			bookSetNotAtOwner.add (person);
		}

		while (!bookSetNotAtOwner.isEmpty()) {
			for (int person = 0; person < personCount; ++person) {
				if (bookArray[person] == person) {
					if (bookSetNotAtOwner.contains (person)) {
						bookSetNotAtOwner.remove (person);
					}
				} else {
					++signatureArray[person];
					bookArray[person] = studentArray[bookArray[person]] - 1;
				}
			}
		}

		return signatureArray;
	}

	/**
	 * You are given an array a of N integers. For each index i, you are required to determine the number of
	 * 	contiguous sub-arrays that fulfills the following conditions:
	 * 
	 * 	The value at index i must be the maximum element in the contiguous sub-arrays, and:
	 * 
	 * 	These contiguous sub-arrays must either start from or end on index i.
	 * 
	 * @param array Input Array
	 * 
	 * @return Number of Contiguous Sub-arrays
	 */

	public static final int[] CountSubArrays (
		final int[] array)
	{
		int arrayLength = array.length;
		int[] subArrayCount = new int[arrayLength];
		boolean[] peakEncountered = new boolean[arrayLength];

		for (int i = 0; i < arrayLength; ++i) {
			subArrayCount[i] = 1;
			peakEncountered[i] = false;
		}

		for (int i = 0; i < arrayLength; ++i) {
			for (int j = 0; j < i; ++j) {
				if (array[i] > array[j])
				{
					peakEncountered[j] = true;
				} else if (!peakEncountered[j]) {
					++subArrayCount[j];
				}
			}
		}

		for (int i = 0; i < arrayLength; ++i) {
			peakEncountered[i] = false;
		}

		for (int i = arrayLength - 1; i >= 0; --i) {
			for (int j = arrayLength - 1; j > i; --j) {
				if (array[i] > array[j]) {
					peakEncountered[j] = true;
				} else if (!peakEncountered[j]) {
					++subArrayCount[j];
				}
			}
		}

		return subArrayCount;
	}

	/**
	 * Extract the K-Closest Points
	 * 
	 * @param pointGrid Grid of Points
	 * @param k K
	 * 
	 * @return K-Closest Points
	 */

	public static final int[][] KClosestPoints (
		final int[][] pointGrid,
		final int k)
	{
		TreeMap<Double, int[]> orderedPointMap = new TreeMap<Double, int[]>();

		for (int pointIndex = 0; pointIndex < pointGrid.length; ++pointIndex) {
			int x = pointGrid[pointIndex][0];
			int y = pointGrid[pointIndex][1];

			orderedPointMap.put ((double) (x * x + y * y), pointGrid[pointIndex]);
		}

		int i = 0;
		int[][] kClosestPoints = new int[k][2];

		Set<Double> distanceSet = orderedPointMap.keySet();

		for (double distance : distanceSet) {
			if (i >= k) {
				break;
			}

			kClosestPoints[i++] = orderedPointMap.get (distance);
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

		for (int arrayIndex = 0; arrayIndex < arrayCount; ++arrayIndex) {
			arrayCursor[arrayIndex] = 0;
			arrayLength[arrayIndex] = arrayOfArrays[arrayIndex].length;
			mergedArrayLength = mergedArrayLength + arrayOfArrays[arrayIndex].length;
		}

		int mergedArrayIndex = 0;
		int[] mergedArray = new int[mergedArrayLength];

		while (mergedArrayIndex < mergedArrayLength) {
			int startIndex = 0;

			while (arrayCursor[startIndex] >= arrayLength[startIndex]) {
				++startIndex;
			}

			int minArrayIndex = startIndex;
			int minValue = arrayOfArrays[startIndex][arrayCursor[startIndex]];

			for (int arrayIndex = startIndex + 1; arrayIndex < arrayCount; ++arrayIndex) {
				if (arrayCursor[arrayIndex] < arrayLength[arrayIndex] &&
					arrayOfArrays[arrayIndex][arrayCursor[arrayIndex]] < minValue)
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

		for (int i = 0; i < arrayLength; ++i)
		{
			int index = i + 1 == arrayLength ? 0 : i + 1;
			int nextGreaterNumber = numberArray[i];

			while (index != i) {
				if (numberArray[index] > nextGreaterNumber) {
					nextGreaterNumber = numberArray[index];
				}

				if (++index == arrayLength) {
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
		if (null == numberArray || 0 == numberArray.length) {
			return false;
		}

		int arrayLength = numberArray.length;

		if (0 > k || k >= arrayLength || 0 > t || t >= arrayLength) {
			return false;
		}

		for (int index = 0; index < arrayLength; ++index) {
			int compareIndex = index < k ? 0 : index - k;
			int upperIndex = index + k >= arrayLength ? arrayLength - 1 : index + k;

			while (compareIndex <= upperIndex) {
				if (compareIndex != index && Math.abs (numberArray[index] - numberArray[compareIndex]) <= t)
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
		if (null == numberArray) {
			return false;
		}

		int arrayLength = numberArray.length;

		if (1 >= arrayLength) {
			return false;
		}

		HashMap<Integer, Integer> remainerSizeMap = new HashMap<Integer, Integer>();

		int sum = numberArray[0];

		for (int i = 1; i < arrayLength; ++i) {
			sum = sum + numberArray[i];
			int currentRemain = sum % k;

			if (0 == currentRemain ||  (
				remainerSizeMap.containsKey (k - currentRemain) &&
				i - 1 >= remainerSizeMap.get (k - currentRemain)
			))
			{
				return true;
			}

			remainerSizeMap.put (currentRemain, i + 1);
		}

		return false;
	}

	/**
	 * Given an integer array numberArray and an integer k, modify the array by repeating it k times.
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
		if (null == numberArray) {
			return 0;
		}

		int arrayLength = numberArray.length;

		if (0 == arrayLength) {
			return 0;
		}

		int indexInclusiveLeftMax = numberArray[0] > 0 ? numberArray[0] : 0;
		int indexInclusiveLeftMin = numberArray[0] < 0 ? numberArray[0] : 0;
		int[] indexInclusiveLeftMaxArray = new int[arrayLength];
		int[] indexInclusiveLeftMinArray = new int[arrayLength];
		indexInclusiveLeftMaxArray[0] = numberArray[0];

		for (int i = 1; i < arrayLength; ++i) {
			int sum = indexInclusiveLeftMaxArray[i - 1] + numberArray[i];
			indexInclusiveLeftMaxArray[i] = sum > numberArray[i] ? sum : numberArray[i];
			indexInclusiveLeftMinArray[i] = sum < numberArray[i] ? sum : numberArray[i];
			indexInclusiveLeftMax = indexInclusiveLeftMax > indexInclusiveLeftMaxArray[i] ?
				indexInclusiveLeftMax : indexInclusiveLeftMaxArray[i];
			indexInclusiveLeftMin = indexInclusiveLeftMin < indexInclusiveLeftMinArray[i] ?
				indexInclusiveLeftMin : indexInclusiveLeftMinArray[i];
		}

		int singleMax = indexInclusiveLeftMax - indexInclusiveLeftMin;

		if (1 == k) {
			return singleMax;
		}

		int[] indexInclusiveRightMaxArray = new int[arrayLength];
		int[] indexInclusiveRightMinArray = new int[arrayLength];
		indexInclusiveRightMaxArray[arrayLength - 1] = numberArray[arrayLength - 1];
		int indexInclusiveRightMax = numberArray[arrayLength - 1] > 0 ? numberArray[arrayLength - 1] : 0;
		int indexInclusiveRightMin = numberArray[arrayLength - 1] < 0 ? numberArray[arrayLength - 1] : 0;

		for (int i = arrayLength - 2; i >= 0; --i) {
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
		if (null == numberArray) {
			return true;
		}

		int oddLocation = 1;
		int arrayLength = numberArray.length;
		int evenLocation = 0 == arrayLength % 2 ? arrayLength - 2 : arrayLength - 1;
		Integer[] wiggleArray = 2 >= arrayLength ? null : new Integer[arrayLength];

		if (null == wiggleArray) {
			return true;
		}

		int median = KthHighestOrderStatistic (numberArray, (arrayLength + 1) / 2);

		for (int i = 0; i < arrayLength; ++i) {
			if (numberArray[i] > median) {
				wiggleArray[oddLocation] = numberArray[i];
				oddLocation += 2;
			} else if (numberArray[i] < median) {
				wiggleArray[evenLocation] = numberArray[i];
				evenLocation -= 2;
			}
		}

		for (int i = 0; i < arrayLength; ++i) {
			numberArray[i] = null == wiggleArray[i] ? median : wiggleArray[i];
		}

		return true;
	}

	/**
	 * Count the Number of Ways to reach the Target
	 * 
	 * @param numberArray The Input Array
	 * @param target The Target
	 * 
	 * @return The Number of Ways to reach the Target
	 */

	public static final int TargetSum (
		final int[] numberArray,
		final int target)
	{
		List<int[]> indexAndResultList = new ArrayList<int[]>();

		indexAndResultList.add (new int[] {-1, 0});

		int targetSumCount = 0;

		while (!indexAndResultList.isEmpty()) {
			int[] indexAndResult = indexAndResultList.remove (0);

			int nextIndex = indexAndResult[0] + 1;
			int result = indexAndResult[1];

			if (nextIndex == numberArray.length) {
				if (result == target) {
					++targetSumCount;
				}
			} else {
				indexAndResultList.add (new int[] {nextIndex, result + numberArray[nextIndex]});

				indexAndResultList.add (new int[] {nextIndex, result - numberArray[nextIndex]});
			}
		}

		return targetSumCount;
	}

	/**
	 * Count the Unique Elements in the Sorted Array
	 * 
	 * @param numberArray The Number Array
	 * 
	 * @return Count of the Unique Elements in the Sorted Array
	 */

	public static final int UniqueElementsInSortedArray (
		final int[] numberArray)
	{
		int i = 1;
		int count = 0 ;
		int prevNumber = numberArray[0];

		while (i < numberArray.length) {
			while (i < numberArray.length &&prevNumber == numberArray[i]) {
				++i;
			}

			++count;

			if (i < numberArray.length) {
				prevNumber = numberArray[i];
			}
		}

		return count;
	}

	/**
	 * Generate the Set of the Location Paths that meet the specified Target
	 * 
	 * @param numberArray Input Array
	 * @param target The Target
	 * 
	 * @return The Set of the Location Paths that meet the specified Target
	 */

	public static final Set<List<Integer>> GeneratePathCombinations (
		final int[] numberArray,
		final int target)
	{
		List<List<Integer>> pathIndexList = new ArrayList<List<Integer>>();

		GeneratePathCombinations (pathIndexList, 0, numberArray);

		Set<List<Integer>> validPathIndexSet = new HashSet<List<Integer>>();

		for (List<Integer> pathIndexSequence : pathIndexList) {
			int sum = 0;

			for (int pathIndexValue : pathIndexSequence) {
				sum = sum + pathIndexValue;
			}

			if (sum == target) {
				validPathIndexSet.add (pathIndexSequence);
			}
		}

		return validPathIndexSet;
	}

	/**
	 * Find the First and the Last Locations of the Target in the Array
	 * 
	 * @param numberArray The Number Array
	 * @param target The Target
	 * 
	 * @return The First and the Last Locations of the Target in the Array
	 */

	public static final int[] FirstAndLastPosition (
		final int[] numberArray,
		final int target)
	{
		return target < numberArray[0] || target > numberArray[numberArray.length - 1] ?
			new int[] {-1, -1} : FirstAndLastPosition (0, numberArray.length - 1, numberArray, target);
	}

	/**
	 * Compute the Array of Product of Array Except Self
	 * 
	 * @param numberArray The Input Array
	 * 
	 * @return The Array of Product of Array Except Self
	 */

	public static final int[] ProductOfArrayExceptSelf (
		final int[] numberArray)
	{
		int arrayLength = numberArray.length;
		int[] productOfArrayExceptSelf = new int[arrayLength];
		int[] leftProductExcludingSelf = new int[arrayLength];
		int[] rightProductExcludingSelf = new int[arrayLength];

		for (int i = 0; i < arrayLength; ++i) {
			leftProductExcludingSelf[i] = 0 == i ? 1 : leftProductExcludingSelf[i - 1] * numberArray[i - 1];
		}

		for (int i = arrayLength - 1; i >= 0; --i) {
			rightProductExcludingSelf[i] = i == arrayLength - 1 ? 1 :
				numberArray[i + 1] * rightProductExcludingSelf[i + 1];
		}

		for (int i = 0; i < arrayLength; ++i) {
			productOfArrayExceptSelf[i] = leftProductExcludingSelf[i] * rightProductExcludingSelf[i];
		}

		return productOfArrayExceptSelf;
	}

	/**
	 * Count the Number of Islands in the Grid
	 * 
	 * @param grid The Grid
	 * 
	 * @return The Number of Islands in the Grid
	 */

	public static final int IslandCounter (
		final int[][] grid)
	{
		int islandCount = 0;
		int rowCount = grid.length;
		int columnCount = grid[0].length;
		int[][] islandMark = new int[rowCount][columnCount];
		int x = 0;
		int y = 0;

		for (int rowIndex = 0; rowIndex < rowCount; ++rowIndex) {
			for (int columnIndex = 0; columnIndex < columnCount; ++columnIndex) {
				islandMark[rowIndex][columnIndex] = 1 - grid[rowIndex][columnIndex];
			}
		}

		while (x < columnCount && y < rowCount) {
			while (y < columnCount && 1 == islandMark[x][y]) {
				++x;

				if (rowCount == x) {
					x = 0;
					++y;
				}
			}

			if (y == columnCount) {
				break;
			}

			MarkIslands (x, y, islandMark, grid);

			++islandCount;
		}

		return islandCount;
	}

	/**
	 * Implement the Pancake Flip Sort
	 * 
	 * @param numberArray The Input Array
	 * 
	 * @return Array of Sorted Pancake Flips
	 */

	public static final int[] PancakeFlipSort (
		final int[] numberArray)
	{
		int arrayLength = numberArray.length;
		int endIndex = arrayLength - 1;

		while (0 <= endIndex) {
			int maxIndex = 0;
			int maxValue = numberArray[0];

			for (int index = 0; index <= endIndex; ++index) {
				if (numberArray[index] > maxValue) {
					maxValue = numberArray[index];
					maxIndex = index;
				}
			}

			if (0 != maxIndex) {
				FlipSequence (numberArray, maxIndex);
			}

			FlipSequence (numberArray, endIndex);

			--endIndex;
		}

		return numberArray;
	}

	/**
	 * Calculate the Minimum Consumption Rate of the Array of Lot Sizes within the Total Time
	 * 
	 * @param lotSizeArray Array of Lot Sizes
	 * @param totalTime The Total Time
	 * 
	 * @return The Minimum Consumption Rate
	 */

	public static final int MinimumConsumptionRate (
		final int[] lotSizeArray,
		final int totalTime)
	{
		int totalSize = 0;
		int lotCount = lotSizeArray.length;

		for (int lotIndex = 0; lotIndex < lotCount; ++lotIndex) {
			totalSize = totalSize + lotSizeArray[lotIndex];
		}

		int consumptionRate = totalSize / totalTime;

		if (0 == consumptionRate) {
			return -1;
		}

		int timeConsumed = TimeConsumed (lotSizeArray, consumptionRate);

		while (timeConsumed > totalTime) {
			timeConsumed = TimeConsumed (lotSizeArray, ++consumptionRate);
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

	public static final Collection<int[]> CollapseOverlappingRanges (
		final List<int[]> rangeList)
	{
		TreeMap<Integer, int[]> rangeMap = new TreeMap<Integer, int[]>();

		for (int[] range : rangeList) {
			if (rangeMap.isEmpty()) {
				rangeMap.put (range[0], range);

				continue;
			}

			int newRangeEnd = range[1];
			int newRangeStart = range[0];

			Integer floorKey = rangeMap.floorKey (newRangeStart);

			floorKey = null != floorKey ? floorKey : rangeMap.firstKey();

			Map<Integer, int[]> tailRangeMap = rangeMap.tailMap (newRangeStart);

			List<Integer> rangeTrimList = new ArrayList<Integer>();

			if (null != tailRangeMap && 0 != tailRangeMap.size()) {
				for (Map.Entry<Integer, int[]> tailRangeEntry : tailRangeMap.entrySet()) {
					int[] tailRange = tailRangeEntry.getValue();

					if (DoRangesOverlap (range, tailRange) || DoRangesOverlap (tailRange, range)) {
						newRangeEnd = newRangeEnd > tailRange[1] ? newRangeEnd : tailRange[1];
						newRangeStart = newRangeStart < tailRange[0] ? newRangeStart : tailRange[0];

						rangeTrimList.add (tailRange[0]);
					}
				}
			}

			for (int rangeStart : rangeTrimList) {
				rangeMap.remove (rangeStart);
			}

			rangeMap.put (newRangeStart, new int[] {newRangeStart, newRangeEnd});
		}

		return rangeMap.values();
	}

	private static final boolean UploadCounterRangeMaps (
		final TreeMap<Integer, Integer> sliceCounterMap,
		final TreeMap<Integer, int[]> sliceRangeMap,
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
		final TreeMap<Integer, Integer> sliceCounterMap,
		final TreeMap<Integer, int[]> sliceRangeMap,
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

	public static final TreeMap<Integer, Integer> SliceOverlappingRanges (
		final List<int[]> rangeList)
	{
		TreeMap<Integer, Integer> rangeCounterMap =
			new TreeMap<Integer, Integer>();

		TreeMap<Integer, int[]> rangeMap =
			new TreeMap<Integer, int[]>();

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

				TreeMap<Integer, Integer> sliceCounterMap =
					new TreeMap<Integer, Integer>();

				TreeMap<Integer, int[]> sliceRangeMap =
					new TreeMap<Integer, int[]>();

				HashSet<Integer> trimList = new HashSet<Integer>();

				for (Map.Entry<Integer, Integer> rangeCounterMapEntry :
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

		Arrays.sort (
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

			int currentAwkwardness = Math.abs (
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

	public static final List<String> TargetApproachPathList (
		final int[] numberArray,
		final int target)
	{
		List<String> targetApproachPathList =
			new ArrayList<String>();

		List<Integer> indexQueue = new ArrayList<Integer>();

		List<Integer> sumQueue = new ArrayList<Integer>();

		List<String> pathQueue = new ArrayList<String>();

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

			String path = pathQueue.remove (
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
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public static final int BODMAS (
		final String s)
		throws Exception
	{
		if (null == s || s.isEmpty())
		{
			throw new Exception (
				"ArrayUtil::BODMAS => Invalid Inputs"
			);
		}

		char[] charArray = s.toCharArray();

		int prevIndex = 0;
		int stringLength = charArray.length;

		List<String> elementList = new ArrayList<String>();

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
			throw new Exception (
				"ArrayUtil::BODMAS => Cannot compute"
			);
		}

		if (!CollapseOperation (
			elementList,
			"*"
		))
		{
			throw new Exception (
				"ArrayUtil::BODMAS => Cannot compute"
			);
		}

		if (!CollapseOperation (
			elementList,
			"-"
		))
		{
			throw new Exception (
				"ArrayUtil::BODMAS => Cannot compute"
			);
		}

		if (!CollapseOperation (
			elementList,
			"+"
		))
		{
			throw new Exception (
				"ArrayUtil::BODMAS => Cannot compute"
			);
		}

		if (1 != elementList.size())
		{
			throw new Exception (
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

	public static final List<String> ExpressionOperatorPathList (
		final int[] numberArray,
		final int target)
	{
		List<String> expressionOperatorPathList =
			new ArrayList<String>();

		List<Integer> indexQueue = new ArrayList<Integer>();

		List<String> pathQueue = new ArrayList<String>();

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

			String path = pathQueue.remove (
				queueTailIndex
			);

			if (index == arrayCount - 1)
			{
				String bodmasExpression = path + "+" + numberArray[index];

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
				catch (Exception e)
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

	public static final List<int[]> InsertIntoNonOverlappingIntervals (
		final int[][] intervals,
		final int[] newInterval)
	{
		List<int[]> insertedIntervalList = new ArrayList<int[]>();

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

	public static final Map<String, Integer> SparseMatrixRepresentation (
		final int[][] matrix)
	{
		Map<String, Integer> sparseMatrixRepresentation = new
			HashMap<String, Integer>();

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

		Map<String, Integer> sparseMatrixRepresentation =
			SparseMatrixRepresentation (matrix);

		Set<String> locationSet = new HashSet<String>();

		locationSet.addAll (sparseMatrixRepresentation.keySet());

		for (String location : locationSet) {
			if (sparseMatrixRepresentation.containsKey (location)) {
				String[] rowCol = location.split ("_");

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
		Set<Integer> mineLocationSet = new HashSet<Integer>();

		while (k > mineLocationSet.size()) mineLocationSet.add ((int)(Math.random() * m * n));

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

	public static final List<List<Integer>> EnumerateDiagonalOrder (
		final int[][] matrix)
	{
		List<List<Integer>> diagonalOrder = new
			ArrayList<List<Integer>>();

		for (int colIndex = 0; colIndex < matrix.length + matrix[0].length - 1; ++colIndex) {
			int scanCol = colIndex;
			int scanRow = 0;

			List<Integer> diagonalOrderRow = new
				ArrayList<Integer>();

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

	/**
	 * Build the Array of Maximum Sliding Window of Size k
	 * 
	 * @param numberArray The Number Array
	 * @param k Size
	 * 
	 * @return The Array of Maximum Sliding Window of Size k
	 */

	public static final int[] SlidingWindowMaximum (
    	final int[] numberArray,
    	final int k)
    {
    	int[] maximumSlidingWindowArray = new int[numberArray.length - k + 1];
    	maximumSlidingWindowArray[0] = Integer.MIN_VALUE;

    	for (int j = 0; j < k; ++j)
    		maximumSlidingWindowArray[0] = maximumSlidingWindowArray[0] > numberArray[j] ?
    			maximumSlidingWindowArray[0] : numberArray[j];

    	for (int i = 1; i <= numberArray.length - k; ++i) {
        	maximumSlidingWindowArray[i] = Integer.MIN_VALUE;

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
    	Set<int[]> zeroLocationSet = new HashSet<int[]>();

    	Set<Integer> processedRowSet = new HashSet<Integer>();

    	Set<Integer> processedColumnSet = new HashSet<Integer>();

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

	        	processedColumnSet.add (location[1]);
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
    	int minimum = Integer.MAX_VALUE;
    	int nextMininum = Integer.MAX_VALUE;

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

    	HashSet<Integer> arrayABHashSet = new HashSet<Integer>();

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
    	int maximumAreaUnderContainer = Integer.MIN_VALUE;
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
    		int areaThroughLevel = (rightMaxHeightIndex[i] - leftMaxHeightIndex[i]) * Math.min
    			(heightArray[leftMaxHeightIndex[i]], heightArray[rightMaxHeightIndex[i]]);

    		int areaLeftOfLevel = (i - leftMaxHeightIndex[i]) * Math.min
    			(heightArray[leftMaxHeightIndex[i]], heightArray[i]);

    		int areaRightOfLevel = (rightMaxHeightIndex[i] - i) * Math.min (heightArray[i],
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

    /**
     * Given an unsorted integer array, find the smallest missing positive integer.
     * 
     * @param numberArray Unsorted Number Array
     * 
     * @return Smallest Missing Positive Integer
     */

    public static final int FirstMisingPositiveInteger (
    	final int[] numberArray)
    {
    	for (int i = 0; i < numberArray.length; ++i) {
    		if (numberArray[i] >= 1 && numberArray[i] <= numberArray.length && numberArray[i] != i + 1)
    			SwapElements (numberArray, i, numberArray[i] - 1);

    		if (numberArray[i] >= 1 && numberArray[i] <= numberArray.length && numberArray[i] != i + 1)
    			SwapElements (numberArray, i, numberArray[i] - 1);
    	}

    	for (int i = 0; i < numberArray.length; ++i)
    		if (numberArray[i] != i + 1) return i + 1;

    	return numberArray.length + 1;
    }

    /**
     * Given an array containing n + 1 integers where each integer is between 1 and n (inclusive), assuming
     *  there is only one duplicate number, find the duplicate one.
     * 
     * @param numberArray The Number Array
     * 
     * @return The Duplicate Number
     */

    public static final int IdentifyDuplicate (
    	final int[] numberArray)
    {
    	int sum = 0;

    	for (int i : numberArray) sum += i;

    	return sum - (numberArray.length - 1) * numberArray.length / 2;
    }

    /**
     * Implement the Rain-water Catchment Area given the array of Heights
     * 
     * @param segmentHeightArray Array of Heights
     * 
     * @return The Rain-water Catchment Area
     */

    public static final int RainWaterCatchmentArea (
    	final int[] segmentHeightArray)
    {
    	int[] segmentLeftArray = new int[segmentHeightArray.length];
    	int[] segmentRightArray = new int[segmentHeightArray.length];
    	int[] leftPeakIndexArray = new int[segmentHeightArray.length];
    	int[] rightPeakIndexArray = new int[segmentHeightArray.length];
    	rightPeakIndexArray[segmentHeightArray.length - 1] = segmentHeightArray.length - 1;
    	int rainWaterCatchmentArea = 0;
    	leftPeakIndexArray[0] = 0;

    	for (int i = 0; i < segmentHeightArray.length; ++i) {
    		segmentLeftArray[i] = 0 == i ? 0 : segmentHeightArray[i - 1];
    		segmentRightArray[i] = segmentHeightArray.length - 1 == i ? 0 : segmentHeightArray[i + 1];
    	}

    	for (int i = 1; i < segmentHeightArray.length; ++i)
    		leftPeakIndexArray[i] = segmentLeftArray[leftPeakIndexArray[i - 1]] >= segmentLeftArray[i] ?
    			leftPeakIndexArray[i - 1] : i;

    	for (int i = segmentHeightArray.length - 2; i >= 0; --i)
    		rightPeakIndexArray[i] = segmentRightArray[rightPeakIndexArray[i + 1]] >=
    			segmentRightArray[i] ? rightPeakIndexArray[i + 1] : i;

    	for (int i = 0; i < segmentHeightArray.length; ++i) {
    		int wallHeight = Math.min (segmentLeftArray[leftPeakIndexArray[i]],
    			segmentRightArray[rightPeakIndexArray[i]]);

    		rainWaterCatchmentArea = rainWaterCatchmentArea + (wallHeight < segmentHeightArray[i] ? 0 :
    			wallHeight - segmentHeightArray[i]);
    	}

    	return rainWaterCatchmentArea;
    }

    /**
     * A Random list of people standing in a queue. Each person is described by a pair of integers (h, k),
     *  where h is the height of the person and k is the number of people in front of this person who have a
     *  height greater than or equal to h. Reconstruct the queue.
     * 
     * @param heightOrderArray The Height+Order Tuple Array
     * 
     * @return The Queue Array
     */

    public static final int[] QueueReconstructionByHeight (
    	final int[][] heightOrderArray)
    {
    	int[] queue = new int[heightOrderArray.length];

    	TreeMap<Integer, List<Integer>> heightOrderMap = new
    		TreeMap<Integer, List<Integer>>();

    	for (int i = 0; i < heightOrderArray.length; ++i) {
    		queue[i] = -1;

    		if (heightOrderMap.containsKey (heightOrderArray[i][0]))
    			heightOrderMap.get (heightOrderArray[i][0]).add (heightOrderArray[i][1]);
    		else {
    			List<Integer> orderList = new ArrayList<Integer>();

    			orderList.add (heightOrderArray[i][1]);

        		heightOrderMap.put (heightOrderArray[i][0], orderList);
    		}
    	}

    	Set<Integer> heightSet = heightOrderMap.keySet();

    	for (int height : heightSet) {
    		for (int order : heightOrderMap.get (height)) {
    			int index = 0;
    			int queueIndex = 0;

    			while (queueIndex < heightOrderArray.length) {
    				if (queue[queueIndex] >= height)
    					++index;
    				else if (-1 == queue[queueIndex]) {
    					if (++index == order + 1) {
    						queue[queueIndex] = height;
    						break;
    					}
    				}

    				++queueIndex;
    			}
    		}
    	}

    	return queue;
    }

    private static final int MedianOfSortedArray (
    	final int[] sortedArray)
    {
    	int n = sortedArray.length;
    	return 1 == n % 2 ? sortedArray[n / 2] : (sortedArray[n / 2] + sortedArray[n / 2 - 1]) / 2;
    }

    private static final int LocateMedian2In1 (
    	final int[] sortedArray1,
    	final int median2,
    	final int leftIndex,
    	final int rightIndex)
    {
    	if (sortedArray1[leftIndex] == median2) return leftIndex;

    	if (sortedArray1[rightIndex] == median2) return rightIndex;

    	if (leftIndex + 1 == rightIndex && sortedArray1[leftIndex] < median2 &&
    		sortedArray1[rightIndex] > median2)
    		return leftIndex;

    	int midIndex = (leftIndex + rightIndex) / 2;

    	return sortedArray1[midIndex] > median2 ? LocateMedian2In1 (sortedArray1, median2, leftIndex,
    		midIndex) : LocateMedian2In1 (sortedArray1, median2, midIndex, rightIndex);
    }

    /**
     * There are two sorted arrays of size m and n respectively. Find the median of the two sorted arrays.
     *  The overall run time complexity should be O(log (m+n)).
     * 
     * Assume arrays cannot be both empty.
     * 
     * @param sortedArray1 Sorted Array #1
     * @param sortedArray2 Sorted Array #2
     * 
     * @return The Overall Median
     */

    public static final double MedianOfSortedArrays (
    	final int[] sortedArray1,
    	final int[] sortedArray2)
    {
    	int m = sortedArray1.length;
    	int n = sortedArray2.length;

    	int median1 = MedianOfSortedArray (sortedArray1);

    	int median2 = MedianOfSortedArray (sortedArray2);

    	if (median1 == median2) return median1;

    	if (sortedArray1[m - 1] <= median2) {
    		if (n < m) return sortedArray2[(n - m) / 2 - 1];

    		if (m < n) return sortedArray1[(m + n) / 2];

    		return ((double) (sortedArray1[m - 1] + sortedArray2[0])) / 2.;
    	}

    	if (sortedArray2[n - 1] < median1)
    		return m > n ? sortedArray1[(m - n) / 2 - 1] : sortedArray2[(n + m) / 2];

    	int array1Location = LocateMedian2In1 (sortedArray1, median2, 0, m - 1);

    	int array2Location = n / 2;
    	int elementsToLeft = array1Location + array2Location;

    	if (elementsToLeft > (m + n) / 2) {
    		if (sortedArray1[array1Location] < sortedArray2[array2Location]) {
    			--array2Location;
    			--elementsToLeft;
    		} else if (sortedArray2[array2Location] < sortedArray1[array1Location]) {
    			--array1Location;
    			--elementsToLeft;
    		} else {
    			--array1Location;
    			--array2Location;
    			elementsToLeft -= 2;
    		}
    	}

    	return 1 == elementsToLeft % 2 ? sortedArray1[elementsToLeft / 2] : (sortedArray1[elementsToLeft / 2]
    		+ sortedArray1[elementsToLeft / 2 - 1]) / 2;
    }

    private static final int MinimumRowIndex (
    	final int[][] matrix,
    	final int[] columnIndex)
    {
    	int startRowIndex = 0;
    	int minimumColumnIndex = columnIndex[0];

    	while (minimumColumnIndex >= matrix[startRowIndex].length)
    		minimumColumnIndex = columnIndex [++startRowIndex];

    	int minimumRowIndex = startRowIndex;
    	int minimum = matrix[startRowIndex][columnIndex[startRowIndex]];

    	for (int i = startRowIndex; i < matrix.length; ++i) {
    		if (columnIndex[i] < matrix[i].length) {
	    		if (minimum > matrix[i][columnIndex[i]]) {
	    			minimumRowIndex = i;
	    			minimum = matrix[i][columnIndex[i]];
	    		}
    		}
    	}

    	return minimumRowIndex;
    }

    /**
     * Given a n x n matrix where each of the rows and columns are sorted in ascending order, find the
     *  k<sup>th</sup> smallest element in the matrix.
     *  
     * Note that it is the k<sup>th</sup> smallest element in the sorted order, not the k<sup>th</sup>
     *  distinct element.
     * 
     * @param matrix The Matrix
     * @param k k<sup>th</sup> Element
     * 
     * @return k<sup>th</sup> Smallest Element
     */

    public static final int SortedMatrixKthElement (
    	final int[][] matrix,
    	final int k)
    {
    	int elementCount = k;
    	int minimumRowIndex = -1;
    	int[] rowColumnIndex = new int[matrix.length];

    	for (int i = 0; i < matrix.length; ++i) rowColumnIndex[i] = 0;

    	while (0 < elementCount) {
    		minimumRowIndex = MinimumRowIndex (matrix, rowColumnIndex);

    		--elementCount;
    		rowColumnIndex[minimumRowIndex] = rowColumnIndex[minimumRowIndex] + 1;
    	}

    	return matrix[minimumRowIndex][rowColumnIndex[minimumRowIndex]];
    }

    /**
     * Given non-negative integers representing the histogram bar height where the width of each bar is 1,
     *  find the area of largest rectangle in the histogram.
     * 
     * @param heightArray The Bar Array
     * 
     * @return Area of the Largest Rectangle
     */

    public static final int LargestRectangleInHistogram (
    	final int[] heightArray)
    {
    	int maxArea = Integer.MIN_VALUE;
    	int[] shortestRightIndex = new int[heightArray.length];
    	int[] adjacentTallerRightIndex = new int[heightArray.length];
    	shortestRightIndex[heightArray.length - 1] = heightArray.length - 1;
    	adjacentTallerRightIndex[heightArray.length - 1] = heightArray.length - 1;

    	for (int i = heightArray.length - 2; i >= 0; --i)
    		shortestRightIndex[i] = heightArray[i] < heightArray[shortestRightIndex[i + 1]] ? i :
    			shortestRightIndex[i + 1];

    	for (int i = heightArray.length - 2; i >= 0; --i) {
    		int j = i;

    		while (j < heightArray.length - 1) {
    			if (heightArray[j + 1] < heightArray[i]) break;

    			++j;
    		}

			adjacentTallerRightIndex[i] = j;

			if (j == heightArray.length - 1)
				adjacentTallerRightIndex[i] = heightArray[j] >= heightArray[i] ? j : j - 1;
    	}

    	for (int i = 0; i < heightArray.length; ++i) {
    		int widestArea = heightArray[shortestRightIndex[i]] * (heightArray.length - i);

    		if (maxArea < widestArea) maxArea = widestArea;

    		int adjacentArea = heightArray[i] * (adjacentTallerRightIndex[i] - i + 1);

    		if (maxArea < adjacentArea) maxArea = adjacentArea;
    	}

    	return maxArea;
    }

    /**
     * A city skyline is the outer contour of the silhouette formed by all the buildings in that city when
     *  viewed from a distance. <b>Given the locations and height of all the buildings</b> as shown on a
     *  cityscape photo, <b>output the skyline</b> formed by these buildings collectively.
     * 
     * @param skscraperMap Cityscape represented as a Map of Skyscapers
     * 
     * @return The Skyline Map
     */

    public static final TreeMap<Integer, int[]> GenerateSkyline (
    	final TreeMap<Integer, int[]> skscraperMap)
    {
    	TreeMap<Integer, int[]> skylineMap = new
    		TreeMap<Integer, int[]>();

    	for (Map.Entry<Integer, int[]> skyscraperEntry : skscraperMap.entrySet()) {
    		if (skylineMap.isEmpty()) {
    			skylineMap.put (skyscraperEntry.getKey(), skyscraperEntry.getValue());

    			continue;
    		}

        	TreeMap<Integer, int[]> disaggregationMap = new
        		TreeMap<Integer, int[]>();

        	for (Map.Entry<Integer, int[]> skyLineEntry : skylineMap.entrySet()) {
        		disaggregationMap.putAll (DisaggregateSkyscrapers (skyLineEntry.getKey(),
        			skyLineEntry.getValue(), skyscraperEntry.getKey(),skyscraperEntry.getValue()));
        	}

        	skylineMap = disaggregationMap;
    	}

    	return skylineMap;
    }

    private static final int BurstCandidateIndex (
    	final List<Integer> integerList)
    {
    	if (1 == integerList.size()) return 0;

    	int maximum = integerList.get (0);

    	int maxIndex = 0;
    	int minIndex = 0;
    	int minimum = maximum;

    	for (int i = 1; i < integerList.size(); ++i) {
    		if (integerList.get (i) < minimum) {
    			minimum = integerList.get (i);

    			minIndex = i;
    		}

    		if (integerList.get (i) > maximum) {
    			maximum = integerList.get (i);

    			maxIndex = i;
    		}
    	}

    	if (2 == integerList.size() || (0 != minIndex && integerList.size() - 1 != minIndex))
    		return minIndex;

    	if (0 == minIndex) {
        	int burstCandidateIndex = 1;

        	while (burstCandidateIndex == maxIndex) ++burstCandidateIndex;

        	minimum = integerList.get (burstCandidateIndex);

        	for (int i = burstCandidateIndex; i < integerList.size(); ++i) {
	    		if (i != maxIndex && integerList.get (i) < minimum) {
	    			minimum = integerList.get (i);
	
	    			burstCandidateIndex = i;
	    		}
    		}

        	return burstCandidateIndex;
    	}

    	int burstCandidateIndex = integerList.size() - 2;

    	while (burstCandidateIndex == maxIndex) --burstCandidateIndex;

    	minimum = integerList.get (burstCandidateIndex);

    	for (int i = burstCandidateIndex; i >= 0; --i) {
    		if (i != maxIndex && integerList.get (i) < minimum) {
    			minimum = integerList.get (i);

    			burstCandidateIndex = i;
    		}
		}

    	return burstCandidateIndex;
    }

    /**
     * Given an array of balloons, each balloon is painted with a number on it represented by array. You are
     *  asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] *
     *  nums[right] coins. Here left and right are adjacent indices of i. After the burst, the left and right
     *  then becomes adjacent.
     *  
     * Find the maximum coins you can collect by bursting the balloons wisely.
     * 
     * @param balloonCoinArray Balloon Coin Array
     * 
     * @return Wise Balloon Burst Sum
     */

    public static final int StrategicBalloonBurstSum (
    	final int[] balloonCoinArray)
    {
    	List<Integer> balloonCoinList = new ArrayList<Integer>();

    	for (int balloonCoin : balloonCoinArray)
    		balloonCoinList.add (balloonCoin);

    	int sum = 0;

    	while (!balloonCoinList.isEmpty()) {
    		int burstCandidateIndex = BurstCandidateIndex (balloonCoinList);

    		int product = balloonCoinList.get (burstCandidateIndex);

    		if (burstCandidateIndex > 0) product = product * balloonCoinList.get (burstCandidateIndex - 1);

    		if (burstCandidateIndex < balloonCoinList.size() - 1)
    			product = product * balloonCoinList.get (burstCandidateIndex + 1);

    		sum += product;

    		balloonCoinList.remove (burstCandidateIndex);
    	}

    	return sum;
    }

    private static final int Partition (
    	final int[] numberArray,
    	final int left,
    	final int right,
    	final int pivotIndex)
    {
    	int pivotValue = numberArray[pivotIndex];

    	SwapElements (numberArray, pivotIndex, right);

    	int partitionIndex = left;

    	for (int index = left; index < right; ++index)
    		if (numberArray[index] < pivotValue) SwapElements (numberArray, partitionIndex++, index);

    	SwapElements (numberArray, partitionIndex, right);

    	return partitionIndex;
    }

    private static final int Select (
    	final int[] numberArray,
    	final int left,
    	final int right,
    	final int k)
    {
    	if (left == right) return numberArray[left];

    	int pivotIndex = Partition (numberArray, left, right,
    		left + ((int) Math.random() * (right - left + 1)));

    	if (k == pivotIndex) return numberArray[k];

    	return k < pivotIndex ? Select (numberArray, left, pivotIndex - 1, k) :
    		Select (numberArray, pivotIndex + 1, right, k);
    }

    /**
     * Evaluate the Array Median
     * 
     * @param numberArray The Number Array
     * 
     * @return The Array Median
     */

    public static final double Median (
    	final int[] numberArray)
    {
    	int arrayLength = numberArray.length;

    	if (1 == arrayLength % 2) return Select (numberArray, 0, arrayLength - 1, arrayLength / 2);

    	return 0.5 * (Select (numberArray, 0, arrayLength - 1, arrayLength / 2 - 1) +
    		Select (numberArray, 0, arrayLength - 1, arrayLength / 2));
    }

    /**
     * Implement the Wiggle Sort Version 2
     * 
     * @param numberArray The Number Array
     * 
     * @return TRUE - The Sort Implementation done
     */

    public static final boolean WiggleSort2 (
    	final int[] numberArray)
    {
    	Median (numberArray);

    	int index = 1;
    	int offset = 1 == numberArray.length % 2 ? 0 : 1;
    	int upperBound = 1 == numberArray.length % 2 ? numberArray.length / 2: numberArray.length / 2 - 1;

    	while (index <= upperBound) {
    		SwapElements (numberArray, index, numberArray.length - offset - index);

    		index += 2;
    	}

    	return true;
    }

    private static final boolean IsJumpForbidden (
    	final int[] forbiddenLocationArray,
    	final int location)
    {
    	for (int forbiddenLocation : forbiddenLocationArray) {
    		if (forbiddenLocation == location) return true;
    	}

    	return false;
    }

    /**
     * Tom plays a game in which he throws a baseball at various blocks marked with a symbol. Each block
     * 	comes with a symbol which can be an integer, ‘X’, ‘+’, or ‘Z’. Given a list of strings represent
     * 	blocks, return the final score.
     * 
     * @param scoreEventArray Array of the Score/Event Strings
     * 
     * @return The Baseball Score
     */

    public static final int BaseballScore (
    	final String[] scoreEventArray)
    {
    	int score = 0;
    	int prevScore = -1;
    	int prevPrevScore = -1;

    	for (String scoreEvent : scoreEventArray) {
    		if ("X".equalsIgnoreCase (scoreEvent))
    			prevScore += prevScore;
    		else if ("+".equalsIgnoreCase (scoreEvent))
    			score += (prevScore + prevPrevScore);
    		else if ("Z".equalsIgnoreCase (scoreEvent))
    			prevScore = 0;
    		else {
	    		if (-1 != prevScore) prevPrevScore = prevScore;

	    		score = score + (prevScore = StringUtil.DecimalNumberFromString (scoreEvent));
    		}
    	}

    	return score;
    }

    /**
     * A company has several suppliers for its products. For each of the products, the stock is represented
     *  by a list of a number of items for each supplier. As items are purchased, the supplier raises the
     *  price by 1 per item purchased. Let's assume Amazon's profit on any single item is the same as the
     *  number of items the supplier has left. For example, if a supplier has 4 items, company's profit on
     *  the first item sold is 4, then 3, then 2 and the profit of the last one is 1.
     *  
     * Given a list where each value in the list is the number of the item at a given supplier and also given
     *  the number of items to be ordered, write an algorithm to find the highest profit that can be
     *  generated for the given product.
     * 
     * @param inventoryArray The Number of Suppliers
     * @param orderCount The Order Count
     * 
     * @return The Inventory Profit
     */

    public static final int InventoryProfit (
    	final int[] inventoryArray,
    	final int orderCount)
    {
    	int count = 0;
    	int profit = 0;

    	org.drip.graph.heap.BinomialTreePriorityQueue<Integer, Integer> inventoryHeap = new
    		org.drip.graph.heap.BinomialTreePriorityQueue<Integer, Integer> (false);

    	for (int inventory : inventoryArray)
    		inventoryHeap.insert (inventory, inventory);

    	while (!inventoryHeap.isEmpty() && count < orderCount) {
    		int itemProfit = inventoryHeap.extractExtremum().key();

    		if (itemProfit >= 1) inventoryHeap.insert (itemProfit - 1, itemProfit - 1);

    		profit = profit + itemProfit;
    		++count;
    	}

    	return profit;
    }

    /**
     * Company A is performing an analysis on the computers at one of its offices. The computers are spaced
     * 	along a single row. The analysis is performed in the following way:
     * 
     * Choose a contiguous segment of a certain number of computers, starting from the beginning of the row.
     * 	Analyze the available hard disk space on each of the computers. Determine the minimum available disk
     *  space within this segment. After performing these steps for the first segment, it is then repeated
     *  for the next segment, continuing this procedure until the end of the row (i.e. if the segment size is
     *  4, computers 1 to 4 would be analyzed, then 2 to 5, etc.)
     *  
     * Given this analysis procedure, write an algorithm to find the maximum available disk space among all
     *  the minima that are found during the analysis.
     * 
     * @param diskSpaceArray Array of Disk Space
     * @param segmentLength The Segment Length
     * 
     * @return The Maximum Available Disk Space
     */

    public static final int MaximumAvailableDiskSpace (
    	final int[] diskSpaceArray,
    	final int segmentLength)
    {
    	int index = segmentLength;

    	PriorityQueue<Integer> diskSpaceQueue = new PriorityQueue<Integer>();

    	for (int i = 0; i < segmentLength; ++i)
    		diskSpaceQueue.offer (diskSpaceArray[i]);

    	int maximumAvailableDiskSpace = diskSpaceQueue.peek();

    	while (index < diskSpaceArray.length) {
    		diskSpaceQueue.remove (diskSpaceArray[index - segmentLength]);

        	int minimumSegmentDiskSpace = diskSpaceQueue.peek();

        	if (maximumAvailableDiskSpace < minimumSegmentDiskSpace)
        		maximumAvailableDiskSpace = minimumSegmentDiskSpace;

        	++index;
    	}

    	return maximumAvailableDiskSpace;
    }

    private static final boolean Infect (
    	final HashSet<String> uninfectedCellSet,
    	final int rowSize,
    	final int columnSize)
    {
    	if (uninfectedCellSet.isEmpty()) return true;

    	HashSet<String> infectedCellSet = new HashSet<String>();

    	for (String uninfectedCell : uninfectedCellSet) {
    		String[] x_y = uninfectedCell.split ("_");

    		int x = Integer.parseInt (x_y[0]);

    		int y = Integer.parseInt (x_y[1]);

    		int left = x - 1;
    		int right = x + 1;
    		int above = y - 1;
    		int below = y + 1;

    		if (left >= 0) {
    			String cell = left + "_" + y;

    			if (!uninfectedCellSet.contains (cell)) infectedCellSet.add (uninfectedCell);
    		}

    		if (right < rowSize) {
    			String cell = right + "_" + y;

    			if (!uninfectedCellSet.contains (cell)) infectedCellSet.add (uninfectedCell);
    		}

    		if (above >= 0) {
    			String cell = x + "_" + above;

    			if (!uninfectedCellSet.contains (cell)) infectedCellSet.add (uninfectedCell);
    		}

    		if (below < columnSize) {
    			String cell = x + "_" + below;

    			if (!uninfectedCellSet.contains (cell)) infectedCellSet.add (uninfectedCell);
    		}
    	}

    	for (String infectedCell : infectedCellSet)
    		uninfectedCellSet.remove (infectedCell);

    	return false;
    }

    /**
     * Given a 2D grid, each cell is either a zombie or a human. Zombies can turn adjacent
     *  (up/down/left/right) human beings into zombies every day. Find out how many days does it take to
     *  infect all humans?
     * 
     * @param infectionMatrix The Infection Matrix
     * 
     * @return Number of Days to infect the entire Population
     */

    public static final int PopulationInfection (
    	final int[][] infectionMatrix)
    {
    	int dayCount = 0;

    	HashSet<String> uninfectedCellSet = new HashSet<String>();

    	for (int i = 0; i < infectionMatrix.length; ++i) {
        	for (int j = 0; j < infectionMatrix[i].length; ++j) {
        		if (0 == infectionMatrix[i][j]) uninfectedCellSet.add (i + "_" + j);
        	}
    	}

    	if (infectionMatrix.length * infectionMatrix[0].length == uninfectedCellSet.size()) return -1;

    	while (!Infect (uninfectedCellSet, infectionMatrix.length, infectionMatrix[0].length)) ++dayCount;

    	return dayCount;
    }

    /**
     * Given an array containing only positive integers, return if you can pick two integers from the array
     * 	which cuts the array into three pieces such that the sum of elements in all pieces is equal.
     * 
     * @param numberArray The Number Array
     * 
     * @return TRUE - A Balanced Partition Exists
     */

    public static final boolean BalancedPartition (
    	final int[] numberArray)
    {
    	int sumFromLeft = 0;
    	int[] sumFromRightArray = new int[numberArray.length];
    	sumFromRightArray[numberArray.length - 1] = numberArray[numberArray.length - 1];

    	for (int i = numberArray.length - 2; i >= 0; --i)
    		sumFromRightArray[i] = sumFromRightArray[i + 1] + numberArray[i];

    	for (int i = 0; i < numberArray.length - 2; ++i) {
    		sumFromLeft = sumFromLeft + numberArray[i];

    		if (2 * sumFromLeft != sumFromRightArray[i + 1]) continue;

    		for (int j = i + 2; j < numberArray.length; ++j) {
    			if (2 * sumFromRightArray[j] == sumFromRightArray[i + 1]) return true;
    		}
    	}

    	return false;
    }

    /**
     * Give a computer with total k memory space, and an array of foreground tasks and background tasks the
     *  computer needs to do. Write an algorithm to find a pair of tasks from each array to maximize the
     *  memory usage. Notice the tasks could be done without origin order.
     * 
     * @param foregroundTasks Array of Foreground Tasks
     * @param backgroundTasks Array of Background Tasks
     * @param k Memory Limit
     * 
     * @return List of [fore, back] pairs
     */

    public static final List<int[]> OptimizeMemoryUsage (
    	final int[] foregroundTasks,
    	final int[] backgroundTasks,
    	final int k)
    {
    	int[] longerTaskSequence = foregroundTasks.length > backgroundTasks.length ? foregroundTasks :
    		backgroundTasks;
    	int[] shorterTaskSequence = foregroundTasks.length < backgroundTasks.length ? foregroundTasks :
    		backgroundTasks;

    	TreeMap<Integer, List<Integer>> shorterTaskIndexMap = new
    		TreeMap<Integer, List<Integer>>();

    	for (int i = 0; i < shorterTaskSequence.length; ++i) {
    		if (shorterTaskIndexMap.containsKey (shorterTaskSequence[i]))
    			shorterTaskIndexMap.get (shorterTaskSequence[i]).add (i);
    		else {
    			List<Integer> indexList = new ArrayList<Integer>();

    			indexList.add (i);

    			shorterTaskIndexMap.put (shorterTaskSequence[i], indexList);
    		}
    	}

    	int closestMemoryGap = Integer.MAX_VALUE;

    	List<int[]> closestIndexPairList = new ArrayList<int[]>();

    	for (int i = 0; i < longerTaskSequence.length; ++i) {
    		Integer shorterKey = shorterTaskIndexMap.floorKey (k - longerTaskSequence[i]);

    		if (null == shorterKey) continue;

    		if (k - shorterKey - longerTaskSequence[i] < closestMemoryGap) {
    			closestMemoryGap = k - shorterKey - longerTaskSequence[i];

    			closestIndexPairList.clear();

    			for (int shorterTaskIndex : shorterTaskIndexMap.get (shorterKey))
    				closestIndexPairList.add (new int[] {i, shorterTaskIndex});
    		} else if (k - shorterKey - longerTaskSequence[i] == closestMemoryGap) {
    			for (int shorterTaskIndex : shorterTaskIndexMap.get (shorterKey))
    				closestIndexPairList.add (new int[] {i, shorterTaskIndex});
    		}
    	}

    	for (int i = 0; i < longerTaskSequence.length; ++i) {
    		if (Math.abs (k - longerTaskSequence[i]) < closestMemoryGap) {
    			closestMemoryGap = Math.abs (k - longerTaskSequence[i]);

    			closestIndexPairList.clear();

				closestIndexPairList.add (new int[] {i, -1});
    		} else if (Math.abs (k - longerTaskSequence[i]) == closestMemoryGap)
				closestIndexPairList.add (new int[] {i, -1});
    	}

    	for (int i = 0; i < shorterTaskSequence.length; ++i) {
    		if (Math.abs (k - shorterTaskSequence[i]) < closestMemoryGap) {
    			closestMemoryGap = Math.abs (k - shorterTaskSequence[i]);

    			closestIndexPairList.clear();

				closestIndexPairList.add (new int[] {-1, i});
    		} else if (Math.abs (k - shorterTaskSequence[i]) == closestMemoryGap)
				closestIndexPairList.add (new int[] {-1, i});
    	}

    	return closestIndexPairList;
    }

    /**
     * Given a two 2D matrix, find the max score of a path from the upper left cell to bottom right cell that
     * 	doesn't visit any of the cells twice. The score of a path is the minimum value in that path.
     * 
     * @param matrix Matrix of Node Values
     * 
     * @return The Maximum Path Score
     */

    public static final int MaxPathScore (
    	final int[][] matrix)
    {
    	int xSize = matrix.length;
    	int ySize = matrix[0].length;
    	int maxPathScore = Integer.MIN_VALUE;

    	List<int[]> locationStack = new ArrayList<int[]>();

    	List<Integer> pathMinimumStack = new ArrayList<Integer>();

    	List<HashSet<String>> visitedVertexSetStack = new
    		ArrayList<HashSet<String>>();

    	locationStack.add (new int[] {0, 0});

    	pathMinimumStack.add (Integer.MAX_VALUE);

    	HashSet<String> initialVisitedVertex = new HashSet<String>();

    	initialVisitedVertex.add ("0_0");

    	visitedVertexSetStack.add (initialVisitedVertex);

    	while (!locationStack.isEmpty()) {
    		int tailIndex = locationStack.size() - 1;

    		int[] location = locationStack.remove (tailIndex);

    		int prevPathMinimum = pathMinimumStack.remove (tailIndex);

    		int pathMinimum = matrix[location[0]][location[1]] < prevPathMinimum ?
    			matrix[location[0]][location[1]] : prevPathMinimum;

    		HashSet<String> visitedVertexSet = visitedVertexSetStack.remove (tailIndex);

    		visitedVertexSet.add (location[0] + "_" + location[1]);

    		if (location[0] == xSize - 1 && location[1] == ySize - 1) {
    			if (maxPathScore < pathMinimum) maxPathScore = pathMinimum;

    			continue;
    		}

    		int left = location[0] - 1;
    		int right = location[0] + 1;
    		int above = location[1] - 1;
    		int below = location[1] + 1;

    		if (left >= 0 && !visitedVertexSet.contains (left + "_" + location[1])) {
    	    	locationStack.add (new int[] {left, location[1]});

    	    	pathMinimumStack.add (pathMinimum);

    	    	visitedVertexSetStack.add (visitedVertexSet);
    		}

    		if (right < xSize && !visitedVertexSet.contains (right + "_" + location[1])) {
    	    	locationStack.add (new int[] {right, location[1]});

    	    	pathMinimumStack.add (pathMinimum);

    	    	visitedVertexSetStack.add (visitedVertexSet);
    		}

    		if (above >= 0 && !visitedVertexSet.contains (location[0] + "_" + above)) {
    	    	locationStack.add (new int[] {location[0], above});

    	    	pathMinimumStack.add (pathMinimum);

    	    	visitedVertexSetStack.add (visitedVertexSet);
    		}

    		if (below < ySize && !visitedVertexSet.contains (location[0] + "_" + below)) {
    	    	locationStack.add (new int[] {location[0], below});

    	    	pathMinimumStack.add (pathMinimum);

    	    	visitedVertexSetStack.add (visitedVertexSet);
    		}
    	}

    	return maxPathScore;
    }

    /**
     * A shopkeeper has a sale to complete and has arranged the items being sold in a list. Starting from the
     *  left, the shop keeper rings up each item at its full price less the price of the first lower or
     *  equally priced item to its right. If there is no item to the right that costs less than or equal to
     *  the current item's price the current item is sold at full price.
     * 
     * Print total cost of all items.
     * 
     * Also print the list of integers representing the indexes of the non- discounted items in ascending
     *  index order.
     * 
     * @param priceArray Array of Item Prices
     * @param nonDiscountedItems List of Non-discounted Items
     * 
     * @return Total Cost of Discounted Items
     */

    public static final int DiscountedSale (
    	final int[] priceArray,
    	final List<Integer> nonDiscountedItems)
    {
    	int totalCost = 0;

    	nonDiscountedItems.clear();

    	for (int i = 0; i < priceArray.length; ++i) {
    		int discount = 0;

    		for (int j = i + 1; j < priceArray.length; ++j) {
        		if (priceArray[j] <= priceArray[i]) {
        			discount = priceArray[j];
        			break;
        		}
        	}

    		totalCost = totalCost + priceArray[i] - discount;

    		if (0 == discount) nonDiscountedItems.add (i);
    	}

    	return totalCost;
    }

    /**
     * Given an integer array and an integer, return the length of the shortest non-empty subarray with a sum
     *  of at least sum. If there is no such subarray, return -1.
     *  
     * A subarray is a contiguous part of an array.
     * 
     * @param numberArray Array of Numbers
     * @param sum The Target Sum
     * 
     * @return Size of the Shortest Subarray
     */

    public static final int ShortestSubarrayAtLeastSum (
		final int[] numberArray,
		final int sum)
    {
    	int leftIndex = 0;
    	int rightIndex = 0;
    	int currentSum = 0;
    	int shortestLength = Integer.MAX_VALUE;

    	while (rightIndex < numberArray.length) {
    		currentSum = currentSum + numberArray[rightIndex];

    		if (currentSum < sum)
    			++rightIndex;
    		else {
    			while (leftIndex < rightIndex && currentSum >= sum) 
    				currentSum = currentSum - numberArray[leftIndex++];

    			if (currentSum < sum) --leftIndex;

    			int currentLength = rightIndex - leftIndex + 1;

    			if (currentLength < shortestLength) shortestLength = currentLength;

    			leftIndex = ++rightIndex;
    			currentSum = 0;
    		}
    	}

    	return Integer.MAX_VALUE == shortestLength ? -1 : shortestLength;
    }

    private static final int NumberFromDigitArray (int[] numberArray, int startIndex, int endIndex)
    {
    	int number = 0;

    	for (int index = startIndex; index <= endIndex; ++index)
    		number = 10 * number + numberArray[index];

    	return number;
    }

    private static final int CountWaysToSeparate (int[] numberArray, int currentIndex, int previousNumber)
    {
    	int countWaysToSeparate = 1;

    	if (currentIndex >= numberArray.length) return 0;

    	if (currentIndex == numberArray.length - 1)
    		return numberArray[currentIndex] >= previousNumber ? 1 : 0;

    	for (int index = currentIndex; index < numberArray.length; ++index) {
    		int nextCandidateNumber = NumberFromDigitArray (numberArray, index + 1, numberArray.length - 1);

    		if (previousNumber > nextCandidateNumber) continue;

    		countWaysToSeparate = countWaysToSeparate + CountWaysToSeparate (numberArray, index + 1, nextCandidateNumber);
    	}

    	return countWaysToSeparate;
    }

    /**
     * Count the Number of Ways to Separate the Number
     * 
     * @param numberStr The Number
     * 
     * @return Number of Ways to Separate the Number
     */

    public static final int CountWaysToSeparate (final String numberStr)
    {
    	if (null == numberStr || numberStr.isEmpty()) return 0;

    	int[] digitArray = new int[numberStr.length()];

    	int number = Integer.parseInt (numberStr);

    	for (int i = digitArray.length - 1; i >= 0; --i) {
    		digitArray[i] = number % 10;
    		number = number / 10;
    	}

    	return 1 + CountWaysToSeparate (digitArray, 1, digitArray[0]);
    }

    private static final boolean PartitionAroundMean (
		double[] numberArray,
		List<Double> numberLeftOfAverage,
		List<Double> numberRightOfAverage,
		final double average)
    {
    	for (double number : numberArray)
    	{
    		if (number < average)
    			numberLeftOfAverage.add(number);
    		else if (number > average)
    			numberRightOfAverage.add(number);
    	}

    	return true;
    }

    private static final void SumCountList (
		final List<double[]> sumCountList,
		final List<Double> numberList,
		final int startIndex)
    {
    	if (startIndex >= numberList.size()) return;

    	double count = 1.;

    	double sum = numberList.get(startIndex);

    	sumCountList.add (new double[] {sum, count});

    	for (int i = startIndex + 1; i < numberList.size(); ++i)
    	{
    		sum = sum + numberList.get(i);

    		sumCountList.add (new double[] {sum, count = count + 1});
    	}

    	SumCountList (sumCountList, numberList, startIndex + 1);
    }

    private static final boolean IsSplittable (
    	final List<double[]> leftSumCountList,
    	final List<double[]> rightSumCountList,
    	final double average)
    {
    	for (int i = 0; i < leftSumCountList.size(); ++i)
    	{
    		double leftSum = leftSumCountList.get(i)[0];

    		double leftCount = leftSumCountList.get(i)[1];

    		for (int j = 0; j < rightSumCountList.size(); ++j)
        	{
    			if (leftSumCountList.size() - 1 == i && rightSumCountList.size() - 1 == j) break;

    			double rightSum = rightSumCountList.get(i)[0];

        		double rightCount = rightSumCountList.get(i)[1];

        		if (leftSum + rightSum == average * (leftCount + rightCount)) return true;
        	}
    	}

    	return false;
    }

    /**
     * Check if the Array can be split so that the Two Sides add to the same
     * 
     * @param numberArray The Input Array
     * 
     * @return TRUE - The Array can be split so that the Two Sides add to the same
     */

    public static final boolean SplitIntoSameAverage (double[] numberArray)
    {
    	double average = 0.;

    	for (int i = 0; i < numberArray.length; ++i)
    	{
    		average = average + numberArray[i];
    	}

    	average = average / numberArray.length;

    	for (int i = 0; i < numberArray.length; ++i)
    	{
    		if (average == numberArray[i]) return true;
    	}

		List<Double> listLeftOfAverage = new ArrayList<Double>();

		List<Double> listRightOfAverage = new ArrayList<Double>();

		if (!PartitionAroundMean (numberArray, listLeftOfAverage, listRightOfAverage, average))
			return false;

		List<double[]> leftSumCountList = new ArrayList<double[]>();

		SumCountList (leftSumCountList, listLeftOfAverage, 0);

		List<double[]> rightSumCountList = new ArrayList<double[]>();

		SumCountList (rightSumCountList, listRightOfAverage, 0);

		return IsSplittable (leftSumCountList, rightSumCountList, average);
    }

    /**
     * Given an array of points where points[i] = [x<sub>i</sub>, y<sub>i</sub>] represents a point on the
     *  <b>X-Y</b> plane, return the maximum number of points that lie on the same straight line.
     * 
     * @param points Points Grid
     * 
     * @return Maximum Points In Line
     */

    public static final int MaximumPointsInLine (
    	final int[][] points)
    {
    	double m = Double.NaN;
    	int maximumPointsCount = -1;
    	int numPoints = points.length;

    	Map<String, Set<String>> linePointSetMap = new HashMap<String, Set<String>>();

    	for (int i = 0; i < numPoints; ++i) {
    		String point1 = points[i][0] + "_" + points[i][1];

    		for (int j = 0; j < numPoints; ++j) {
        		if (i == j) continue;

        		if (points[i][0] == points[j][0])
        			m = points[i][1] > points[j][1] ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
        		else
        			m = (points[i][1] - points[j][1]) / (points[i][0] - points[j][0]);

        		String point2 = points[j][0] + "_" + points[j][1];
        		double c = points[i][1] - m * points[i][0];
        		String key = m + "_" + c;

        		Set<String> pointSet = linePointSetMap.containsKey(key) ?
        			linePointSetMap.get(key) : new HashSet<String>();

        		pointSet.add (point1);

        		pointSet.add (point2);

        		if (!linePointSetMap.containsKey(key)) linePointSetMap.put(key, pointSet);
    		}
    	}

    	for (Set<String> pointSet : linePointSetMap.values()) {
    		int pointSetCount = pointSet.size();

    		if (pointSetCount > maximumPointsCount) maximumPointsCount = pointSetCount;
    	}

    	return maximumPointsCount;
    }

    /**
     * Given an array of integers, your task is to check if it could become non-decreasing by modifying <b>at
     *  most one element</b>.
     *  
     * We define an array is non-decreasing if nums[i] le nums[i + 1] holds for every i (<b>0-based</b>) such
     *  that (0 le i le n - 2).
     * 
     * @param numberArray Number Array
     * 
     * @return TRUE - The Conversion is Possible
     */

    public static final boolean ConversionToNondecreasing (
    	final int[] numberArray)
    {
    	List<int[]> nondecreasingSequenceList = NondecreasingSequenceList (numberArray);

    	int sequenceListSize = nondecreasingSequenceList.size();

    	if (1 == sequenceListSize) return true;

    	if (2 < sequenceListSize) return false;

    	int[] leftRange = nondecreasingSequenceList.get (0);

    	int[] rightRange = nondecreasingSequenceList.get (1);

    	if (leftRange[0] == leftRange[1] || rightRange[0] == rightRange[1]) return true;

    	return numberArray[leftRange[1] - 1] <= numberArray[rightRange[0]] ||
    		numberArray[leftRange[1]] <= numberArray[rightRange[0] + 1];
    }

    /**
     * You are given an integer array of 2 * n integers. You need to partition it into <b>two</b> arrays of
     *  length n to <b>minimize the absolute difference</b> of the <b>sums</b> of the arrays. To partition
     *  the array, put each element into <b>one</b> of the two arrays.
     *  
     * Return the <b>minimum</b> possible absolute difference.
     * 
     * @param numberArray The Number Array
     * 
     * @return Minimum Possible Difference
     */

    public static final int PartitionArrayMinimizeSum (
    	final int[] numberArray)
    {
    	Arrays.sort (numberArray);

    	int leftIndex = 1;
    	int leftPartitionSum = numberArray[0];
    	int rightIndex = numberArray.length - 2;
    	int rightPartitionSum = numberArray[numberArray.length - 1];

    	while (leftIndex < rightIndex) {
    		if (leftPartitionSum < rightPartitionSum) {
    			leftPartitionSum = leftPartitionSum + numberArray[rightIndex];
    			rightPartitionSum = rightPartitionSum + numberArray[leftIndex];
    		} else {
    			leftPartitionSum = leftPartitionSum + numberArray[leftIndex];
    			rightPartitionSum = rightPartitionSum + numberArray[rightIndex];
    		}

    		++leftIndex;
    		--rightIndex;
    	}

    	return leftPartitionSum > rightPartitionSum ?
    		leftPartitionSum - rightPartitionSum : rightPartitionSum - leftPartitionSum;
    }

    /**
     * Indicate the Destination is Reachable
     * 
     * @param s The String
     * @param minJump Lower Jump Bound
     * @param maxJump Upper Jump Bound
     * 
     * @return TRUE - The Destination is Reachable
     */

    public static final boolean JumpGameDestinationReachable (
    	final String s,
    	final int minJump,
    	final int maxJump)
    {
    	Set<Integer> visitedLocationSet = new HashSet<Integer>();

    	Stack<Integer> locationStack = new Stack<Integer>();

    	locationStack.add (0);

    	while (!locationStack.isEmpty()) {
    		int location = locationStack.pop();

    		if (s.length() - 1 == location) return true;

    		visitedLocationSet.add (location);

    		for (int i = location + minJump; i <= location + maxJump; ++i) {
    			if (i < s.length() && !visitedLocationSet.contains (i) && '0' == s.charAt (i))
    				locationStack.add (i);
    		}
    	}

    	return false;
    }

    /**
     * Given an integer array numberArray and an integer k, modify the array by repeating it k times. For
     *  example, if arr = [1, 2] and k = 3 then the modified array will be [1, 2, 1, 2, 1, 2].
     *  
     * Return the maximum sub-array sum in the modified array. Note that the length of the sub-array can be 0
     *  and its sum in that case is 0.
     *  
     * As the answer can be very large, return the answer <b>modulo</b> 10<sup>9</sup> + 7.
     * 
     * @param numberArray Number Array
     * @param k K
     * 
     * @return Maximum Sum after k Concatenations
     */

    public static final int KConcatenationMaximumSum (
    	final int[] numberArray,
    	final int k)
    {
    	int rawIndex = 1;
    	int bestMaxSum = numberArray[0];
    	int currentMaxSum = numberArray[0];

    	while (rawIndex < k * numberArray.length) {
    		int newCurrentSum = currentMaxSum + numberArray[rawIndex % numberArray.length];
    		currentMaxSum = newCurrentSum > 0 ? newCurrentSum : 0;

    		if (bestMaxSum < currentMaxSum) bestMaxSum = currentMaxSum;

    		++rawIndex;
    	}

    	return bestMaxSum;
    }

    /**
     * A certain bug's home is on the x-axis at position x. Help them get there from position 0.
     * 
     * The bug jumps according to the following rules:
     * 
     *  It can jump exactly a positions <b>forward</b> (to the right).
     *  It can jump exactly b positions <b>backward</b> (to the left).
     *  It cannot jump backward twice in a row.
     *  It cannot jump to any forbidden positions.
     *  The bug may jump forward beyond its home, but it cannot jump to positions numbered with negative
     *   integers.
     *   
     * Given an array of integers forbidden, where forbidden[i] means that the bug cannot jump to the
     *  position forbidden[i], and integers a, b, and x, return the minimum number of jumps needed for the
     *  bug to reach its home. If there is no possible sequence of jumps that lands the bug on position x,
     *  return -1.

     * @param forbiddenLocationArray Array of Forbidden Locations
     * @param a Steps of Right Jump
     * @param b Steps of Left Jump
     * @param target Target
     * 
     * @return Minimum Steps to reach Target
     */

    public static final int ReachTargetMinimumJumps (
    	final int[] forbiddenLocationArray,
    	final int a,
    	final int b,
    	final int target)
    {
    	Stack<Boolean> jumpDirectionStack = new Stack<Boolean>();

    	Set<Integer> visitedLocationSet = new HashSet<Integer>();

    	Stack<Integer> jumpCountStack = new Stack<Integer>();

    	Stack<Integer> locationStack = new Stack<Integer>();

    	int minimumJumpCount = Integer.MAX_VALUE;

    	jumpDirectionStack.push (false);

    	jumpCountStack.push (0);

    	locationStack.push (0);

    	while (!locationStack.isEmpty()) {
    		int jumpCount = jumpCountStack.pop();

    		int location = locationStack.pop();

    		if (target == location) {
    			if (minimumJumpCount > jumpCount) minimumJumpCount = jumpCount;

    			break;
    		}

    		boolean previousJumpBack = jumpDirectionStack.pop();

    		visitedLocationSet.add (location);

    		int leftLocation = location - b;
    		int rightLocation = location + a;

    		if (!IsJumpForbidden (forbiddenLocationArray, rightLocation) &&
    			!visitedLocationSet.contains (rightLocation)) {
    			locationStack.push (rightLocation);

    			jumpDirectionStack.push (false);

    			jumpCountStack.push (jumpCount + 1);
    		}

    		if (leftLocation >= 0 && !previousJumpBack && !IsJumpForbidden (forbiddenLocationArray, leftLocation) &&
    			!visitedLocationSet.contains (leftLocation)) {
    			locationStack.push (leftLocation);

    			jumpDirectionStack.push (true);

    			jumpCountStack.push (jumpCount + 1);
    		}
    	}

    	return Integer.MAX_VALUE == minimumJumpCount ? -1 : minimumJumpCount;
    }

    /**
     * Given an integer n, return the smallest <b>prime palindrome</b> greater than or equal to n.
     * 
     * An integer is <b>prime</b> if it has exactly two divisors: 1 and itself. Note that 1 is not a prime
     *  number.
     * 
     *  For example, 2, 3, 5, 7, 11, and 13 are all primes.
     * 
     * An integer is a <b>palindrome</b> if it reads the same from left to right as it does from right to
     *  left.
     * 
     *  For example, 101 and 12321 are palindromes.
     * 
     * The test cases are generated so that the answer always exists and is in the range [2, 2 *
     *  10<sup>8</sup>].
     * 
     * @param number Given Number
     * 
     * @return Closest Next Prime Palindrome
     */

    public static final int ClosestNextPrimeNumber (
    	final int number)
    {
    	Set<Integer> primeSet = new HashSet<Integer>();

    	primeSet.add (2);

    	int nextNumber = number + 1;

    	while (!IsPrime (primeSet, nextNumber))
    		++nextNumber;

    	return nextNumber;
    }

    /**
     * Given an integer array and an integer k, return true if the array has a continuous sub-array of size
     *  at least two whose elements sum up to a multiple of k, or false otherwise.
     * 
     * An integer x is a multiple of k if there exists an integer n such that x = n * k. 0 is always a
     *  multiple of k.

     * @param numberArray Number Array
     * @param k K
     * 
     * @return TRUE - The array has a continuous sub-array of size at least two whose elements sum up to a
     *  multiple of k
     */

    public static final boolean ContinuousSubarraySumMod (
    	final int[] numberArray,
    	final int k)
    {
    	int sum = 0;
    	int previousRemainder = -1;

    	Set<Integer> remainderSet = new HashSet<Integer>();

    	for (int i = 0; i < numberArray.length; ++i) {
    		sum = sum + numberArray[i];
    		int remainder = sum % k;

    		if (remainderSet.contains (remainder)) return true;

    		if (0 != i) remainderSet.add (previousRemainder);

    		previousRemainder = remainder;
    	}

    	return remainderSet.contains (0);
    }

    /**
     * Entry Point
     * 
     * @param argumentArray Argument Array
     * 
     * @throws Exception Thrown if Exception Encountered
     */

    public static final void main (
		final String[] argumentArray)
		throws Exception
	{
    	System.out.println (ContinuousSubarraySumMod (new int[] {23, 2, 4, 6, 7}, 6));

    	System.out.println (ContinuousSubarraySumMod (new int[] {23, 2, 6, 4, 7}, 6));

    	System.out.println (ContinuousSubarraySumMod (new int[] {23, 2, 6, 4, 7}, 13));
	}
}
