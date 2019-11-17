
package org.drip.spaces.big;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>BigR1Array</i> contains an Implementation of a variety of in-place Sorting Algorithms for Big Double
 * Arrays.
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 * 			C A R Hoare's Quick Sort
 *  	</li>
 *  	<li>
 * 			J von Neumann's Merge Sort
 *  	</li>
 *  	<li>
 * 			R W Floyd's Heap Sort
 *  	</li>
 *  	<li>
 * 			Insertion Sort
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/big/README.md">Big-data In-place Manipulator</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BigR1Array {
	private int _iLength = -1;
	private double[] _adblA = null;

	private void swapLocations (
		final int i1,
		final int i2)
	{
		double dblTemp = _adblA[i1];
		_adblA[i1] = _adblA[i2];
		_adblA[i2] = dblTemp;
	}

	private int dropOffIndex (
		final int iPickupIndex)
	{
		for (int i = 0; i < iPickupIndex; ++i) {
			if (_adblA[i] > _adblA[iPickupIndex]) return i;
		}

		return iPickupIndex;
	}

	/**
	 * BigR1Array Constructor
	 * 
	 * @param adblA The Array to be Sorted
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BigR1Array (
		final double[] adblA)
		throws java.lang.Exception
	{
		if (null == (_adblA = adblA) || 0 == (_iLength = _adblA.length))
			throw new java.lang.Exception ("BigR1Array Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Array Contents
	 * 
	 * @return The Array Contents
	 */

	public double[] array()
	{
		return _adblA;
	}

	/**
	 * Transfer all Elements from the Pickup Index to the Drop Off Index, and contiguously Shift the
	 *  Intermediate Array
	 *
	 * @param iPickupIndex The Pickup Index
	 * @param iDropOffIndex The Drop off Index
	 * 
	 * @return TRUE - Successfully transferred the Pickup to the Drop off Locations and the related
	 *  Translations
	 */

	public boolean transfer (
		final int iPickupIndex,
		final int iDropOffIndex)
	{
		if (iPickupIndex < 0 || iPickupIndex >= _iLength || iDropOffIndex < 0 || iDropOffIndex >= _iLength)
			return false;

		if (iPickupIndex == iDropOffIndex) return true;

		double dblTemp = _adblA[iPickupIndex];

		if (iPickupIndex > iDropOffIndex) {
			for (int i = iPickupIndex; i > iDropOffIndex; --i)
				_adblA[i] = _adblA[i - 1];
		} else {
			for (int i = iPickupIndex; i > iDropOffIndex; ++i)
				_adblA[i] = _adblA[i + 1];
		}

		_adblA[iDropOffIndex] = dblTemp;
		return true;
	}

	/**
	 * Sort the Specified Range in the Array using Quick Sort
	 * 
	 * @param iLow Lower Index of the Range (Inclusive)
	 * @param iHigh Upper Index of the Range (Inclusive)
	 * 
	 * @return TRUE - The Range has been successfully sorted
	 */

	public boolean quickSort (
		final int iLow,
		final int iHigh)
	{
		if (iLow < 0 || iLow >= iHigh || iLow >= _iLength)
		{
			return false;
		}

		int iLeft = iLow;
		int iRight = iHigh;
		double dblPivot = _adblA[(iLow + iHigh) / 2];

		while (iLeft <= iRight)
		{
			while (_adblA[iLeft] < dblPivot)
			{
				++iLeft;
			}

			while (_adblA[iRight] > dblPivot)
			{
				--iRight;
			}

			if (iLeft <= iRight)
			{
				swapLocations (iLeft, iRight);

				++iLeft;
				--iRight;
			}
		}

		if (iLow < iRight && !quickSort (iLow, iRight)) return false;

		if (iLeft < iHigh && !quickSort (iLeft, iHigh)) return false;

		return true;
	}

	/**
	 * Sort the Full Array using Quick Sort
	 * 
	 * @return TRUE - The Full Array has been successfully sorted
	 */

	public boolean quickSort()
	{
		return quickSort (0, _iLength - 1);
	}

	/**
	 * Merge the Sorted Sub Array Pair
	 * 
	 * @param i1Start The Left Starting Index (Inclusive)
	 * @param i1End The Left Ending Index (Inclusive)
	 * @param i2Start The Right Starting Index (Inclusive)
	 * @param i2End The Right End Index (Inclusive)
	 * 
	 * @return TRUE - Successfully Merged the Sorted Array Pair and Re-assigned Back to the Master
	 */

	public boolean mergeSort (
		final int i1Start,
		final int i1End,
		final int i2Start,
		final int i2End)
	{
		if (i1Start >= _iLength || i1Start > i1End || i2Start > i2End || i1End >= i2Start) return false;

		if (i1Start == i1End && i2Start == i2End) {
			if (_adblA[i1Start] > _adblA[i2Start]) swapLocations (i1Start, i2Start);
		} else {
			if (i1Start == i1End) {
				int i2Mid = (i2Start + i2End) / 2;

				if (!mergeSort (i2Start, i2Mid, i2Mid + 1, i2End)) return false;
			}
		}

		int i = 0;
		int i1 = i1Start;
		int i2 = i2Start;
		double[] adblMerged = new double[i1End - i1Start + i2End - i2Start + 2];

		while (i1 <= i1End && i2 <= i2End) {
			if (_adblA[i1] < _adblA[i2])
				adblMerged[i++] = _adblA[i1++];
			else if (_adblA[i1] > _adblA[i2])
				adblMerged[i++] = _adblA[i2++];
			else {
				adblMerged[i++] = _adblA[i1++];
				adblMerged[i++] = _adblA[i2++];
			}
		}

		while (i1 <= i1End)
			adblMerged[i++] = _adblA[i1++];

		while (i2 <= i2End)
			adblMerged[i++] = _adblA[i2++];

		i = 0;

		for (int j = i1Start; j <= i1End; ++j)
			_adblA[j] = adblMerged[i++];

		for (int j = i2Start; j <= i2End; ++j)
			_adblA[j] = adblMerged[i++];

		return true;
	}

	/**
	 * Contiguous Stretch Merge Sort
	 * 
	 * @param iStart The Master Starting Index (Inclusive)
	 * @param iEnd The Master Ending Index (Inclusive)
	 * 
	 * @return TRUE - Successfully Merged the Sorted Array Stretch and Re-assigned Back to the Master
	 */

	public boolean mergeSort (
		final int iStart,
		final int iEnd)
	{
		if (iStart < 0 || iStart > iEnd || iStart >= _iLength) return false;

		if (iStart == iEnd) return true;

		if (iStart == iEnd - 1) {
			if (_adblA[iStart] > _adblA[iEnd]) swapLocations (iStart, iEnd);

			return true;
		}

		double[] adblMerged = new double[iEnd - iStart + 1];
		int iMid = (iStart + iEnd) / 2;
		int iRightStart = iMid + 1;
		int iRight = iRightStart;
		int iLeftStart = iStart;
		int iLeft = iLeftStart;
		int iRightEnd = iEnd;
		int iLeftEnd = iMid;
		int i = 0;

		if (!mergeSort (iLeftStart, iLeftEnd) || !mergeSort (iRightStart, iRightEnd)) return false;

		while (iLeft <= iLeftEnd && iRight <= iRightEnd) {
			if (_adblA[iLeft] < _adblA[iRight])
				adblMerged[i++] = _adblA[iLeft++];
			else if (_adblA[iLeft] > _adblA[iRight])
				adblMerged[i++] = _adblA[iRight++];
			else {
				adblMerged[i++] = _adblA[iLeft++];
				adblMerged[i++] = _adblA[iRight++];
			}
		}

		while (iLeft <= iLeftEnd)
			adblMerged[i++] = _adblA[iLeft++];

		while (iRight <= iRightEnd)
			adblMerged[i++] = _adblA[iRight++];

		i = 0;

		for (int j = iLeftStart; j <= iLeftEnd; ++j)
			_adblA[j] = adblMerged[i++];

		for (int j = iRightStart; j <= iRightEnd; ++j)
			_adblA[j] = adblMerged[i++];

		return true;
	}

	/**
	 * In-place Big Array Merge Sort
	 * 
	 * @return TRUE - Successfully Merged the Big Double Array and Re-assigned Back to the Master
	 */

	public boolean mergeSort()
	{
		return mergeSort (0, _iLength - 1);
	}

	/**
	 * Heap Sort the Big Array
	 * 
	 * @return TRUE - Successfully Merged the Big Double Array and Re-assigned Back to the Master
	 */

	public boolean heapSort()
	{
		org.drip.spaces.big.BinaryTree bt = null;

		try {
			bt = new org.drip.spaces.big.BinaryTree (_adblA[0], null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		for (int i = 1; i < _iLength; ++i) {
			if (null == bt.insert (_adblA[i])) return false;
		}

		try {
			return _iLength == bt.ascendingNodeArray (_adblA, 0);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Insertion Sort the Big Array
	 * 
	 * @return TRUE - Successfully Merged the Big Double Array and Re-assigned Back to the Master
	 */

	public boolean insertionSort()
	{
		for (int iPickupIndex = 1; iPickupIndex < _iLength; ++iPickupIndex) {
			if (!transfer (iPickupIndex, dropOffIndex (iPickupIndex))) return false;
		}

		return true;
	}
}
