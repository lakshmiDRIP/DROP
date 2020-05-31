
package org.drip.numerical.common;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>NumberUtil</i> implements number utility functions. It exposes the following functions:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *			Verify number/number array validity, and closeness/sign match
 *  	</li>
 *  	<li>
 *			Factorial Permutation/Combination functionality
 *  	</li>
 *  	<li>
 *			Dump multi-dimensional array contents
 *  	</li>
 *  	<li>
 *			Min/Max/Bound the array entries within limits
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/common">Primitives/Array Manipulate Format Display Utilities</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class NumberUtil {
	private static final double DEFAULT_ABSOLUTE_TOLERANCE = 1.0e-03;
	private static final double DEFAULT_RELATIVE_TOLERANCE = 1.0e-03;

	private static final int Quotient (
		final int dividend,
		final int divisor)
	{
		if (dividend < divisor)
		{
			return 0;
		}

		int quotient = 1;
		int divisorSeriesSum = divisor;

		while (true)
		{
			int divisorSeriesSumTemp = divisorSeriesSum + divisorSeriesSum;

			if (divisorSeriesSumTemp > dividend)
			{
				break;
			}

			quotient += quotient;
			divisorSeriesSum =  divisorSeriesSumTemp;
		}

		return quotient + Quotient (
			dividend - divisorSeriesSum,
			divisor
		);
	}

	private static final int ComputeFromPrimeFactorMap (
		final java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeCountMap)
	{
		int primeFactor = 1;

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> primeCountEntry :
			primeCountMap.entrySet())
		{
			int prime = primeCountEntry.getKey();

			int primeCount = primeCountEntry.getValue();

			while (0 != primeCount)
			{
				primeFactor = primeFactor * prime;
				--primeCount;
			}
		}

		return primeFactor;
	}

	private static final int PrimeNumberToIncrement (
		final java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeCountMap)
	{
		int smallestCompoundedPrime = 1;
		int primeNumberToIncrement = 1;

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> primeCountEntry :
			primeCountMap.entrySet())
		{
			int compoundedPrime = 1;

			int prime = primeCountEntry.getKey();

			int primeCount = primeCountEntry.getValue();

			while (0 != primeCount)
			{
				compoundedPrime = compoundedPrime * prime;
				--primeCount;
			}

			if (smallestCompoundedPrime > compoundedPrime)
			{
				smallestCompoundedPrime = compoundedPrime;
				primeNumberToIncrement = prime;
			}
		}

		return primeNumberToIncrement;
	}

	/**
	 * Check if the Input Long is MIN_VALUE or MAX_VALUE
	 * 
	 * @param l Input Long
	 * 
	 * @return TRUE - Input Long is MIN_VALUE or MAX_VALUE
	 */

	public static final boolean IsValid (
		final long l)
	{
		return java.lang.Long.MIN_VALUE != l && java.lang.Long.MAX_VALUE != l;
	}

	/**
	 * Check if the Input Long Array contains a MIN_VALUE or MAX_VALUE
	 * 
	 * @param al Input Long Array
	 * 
	 * @return TRUE - Input Long Array contains a MIN_VALUE or MAX_VALUE
	 */

	public static final boolean IsValid (
		final long[] al)
	{
		if (null == al) return true;

		for (int i = 0; i < al.length; ++i) {
			if (!IsValid (al[i])) return false;
		}

		return true;
	}

	/**
	 * Checks if the input double is Infinite or NaN
	 * 
	 * @param dbl Input double
	 * 
	 * @return TRUE - Input double is Infinite or NaN
	 */

	public static final boolean IsValid (
		final double dbl)
	{
		return !java.lang.Double.isNaN (dbl) && !java.lang.Double.isInfinite (dbl);
	}

	/**
	 * Checks if the input double array contains an Infinite or an NaN
	 * 
	 * @param adbl Input double array
	 * 
	 * @return TRUE - Input double contains an Infinite or an NaN
	 */

	public static final boolean IsValid (
		final double[] adbl)
	{
		if (null == adbl) return true;

		for (int i = 0; i < adbl.length; ++i) {
			if (!IsValid (adbl[i])) return false;
		}

		return true;
	}

	/**
	 * Compare and checks if the two input numbers fall within a specified tolerance
	 * 
	 * @param dbl1 Number #1
	 * @param dbl2 Number #2
	 * @param dblAbsoluteTolerance Absolute Tolerance
	 * @param dblRelativeTolerance Relative Tolerance
	 * 
	 * @return TRUE if they fall within the tolerance
	 */

	public static final boolean WithinTolerance (
		final double dbl1,
		final double dbl2,
		final double dblAbsoluteTolerance,
		final double dblRelativeTolerance)
	{
		if (!IsValid (dbl1) || !IsValid (dbl2)) return false;

		if (dblAbsoluteTolerance >= java.lang.Math.abs (dbl1)) {
			if (dblAbsoluteTolerance >= java.lang.Math.abs (dbl2)) return true;

			return false;
		}

		if (dblRelativeTolerance >= java.lang.Math.abs ((dbl2 - dbl1) / dbl1)) return true;

		return false;
	}

	/**
	 * Compare and checks if the two input numbers fall within a specified tolerance
	 * 
	 * @param dbl1 Number #1
	 * @param dbl2 Number #2
	 * 
	 * @return TRUE if they fall within the tolerance
	 */

	public static final boolean WithinTolerance (
		final double dbl1,
		final double dbl2)
	{
		return WithinTolerance (dbl1, dbl2, DEFAULT_ABSOLUTE_TOLERANCE, DEFAULT_RELATIVE_TOLERANCE);
	}

	/**
	 * This function implements Factorial N.
	 * 
	 * @param n N
	 * 
	 * @return Factorial N
	 */

	public static final int Factorial (
		final int n)
	{
		int iNFact = 1;

		for (int i = 1; i <= n; ++i)
			iNFact *= i;

		return iNFact;
	}

	/**
	 * This function implements N Permute K.
	 * 
	 * @param n N
	 * @param k K
	 * 
	 * @return N Permute K
	 */

	public static final int NPK (
		final int n,
		final int k)
	{
		int iK = n < k ? n : k;
		int iN = n > k ? n : k;

		return Factorial (iN) / Factorial (iK);
	}

	/**
	 * This function implements N choose K.
	 * 
	 * @param n N
	 * @param k K
	 * 
	 * @return N choose K
	 */

	public static final int NCK (
		final int n,
		final int k)
	{
		int iK = n < k ? n : k;
		int iN = n > k ? n : k;

		return Factorial (iN) / Factorial (iK) / Factorial (iN - iK);
	}

	/**
	 * Bound the input to within (floor, Ceiling), i.e., compute Min (Max (floor, X), Ceiling)
	 * 
	 * @param dblX Input Number
	 * @param dblFloor Floor
	 * @param dblCeiling Ceiling
	 * 
	 * @return Min (Max (floor, X), Ceiling)
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public static final double Bound (
		final double dblX,
		final double dblFloor,
		final double dblCeiling)
		throws java.lang.Exception
	{
		if (!IsValid (dblX) || !IsValid (dblFloor)|| !IsValid (dblCeiling) || dblFloor > dblCeiling)
			throw new java.lang.Exception ("NumberUtil::Bound => Invalid Inputs");

		double dblBound = dblX < dblFloor ? dblFloor : dblX;
		return dblBound > dblCeiling ? dblCeiling : dblBound;
	}

	/**
	 * Retrieve the Minimum Element in the specified Array
	 * 
	 * @param adbl Array of elements
	 * 
	 * @return The Minimum Element
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public static final double Minimum (
		final double[] adbl)
		throws java.lang.Exception
	{
		if (!IsValid (adbl)) throw new java.lang.Exception ("NumberUtil::Minimum => Invalid Inputs");

		double dblMinimum = adbl[0];
		int iNumElement = adbl.length;

		for (int i = 1; i < iNumElement; ++i)
			dblMinimum = dblMinimum < adbl[i] ? dblMinimum : adbl[i];

		return dblMinimum;
	}

	/**
	 * Retrieve the Maximum Element in the specified Array
	 * 
	 * @param adbl Array of elements
	 * 
	 * @return The Maximum Element
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public static final double Maximum (
		final double[] adbl)
		throws java.lang.Exception
	{
		if (!IsValid (adbl)) throw new java.lang.Exception ("NumberUtil::Maximum => Invalid Inputs");

		double dblMaximum = adbl[0];
		int iNumElement = adbl.length;

		for (int i = 1; i < iNumElement; ++i)
			dblMaximum = dblMaximum > adbl[i] ? dblMaximum : adbl[i];

		return dblMaximum;
	}

	/**
	 * Check if the specified array contains elements all of the same sign
	 * 
	 * @param adbl Array of elements
	 * 
	 * @return TRUE - Same Sign
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public static final boolean SameSign (
		final double[] adbl)
		throws java.lang.Exception
	{
		if (!IsValid (adbl)) throw new java.lang.Exception ("NumberUtil::SameSign => Invalid Inputs");

		if (adbl[0] <= 0.) return false;

		int iNumElement = adbl.length;

		for (int i = 1; i < iNumElement; ++i) {
			if (adbl[0] * adbl[i] <= 0.) return false;
		}

		return true;
	}

	/**
	 * Print the contents of the 1D array
	 * 
	 * @param strName Label Name
	 * @param adblA The 1D array
	 * @param bBailOnNaN Bail on encountering an NaN
	 * 
	 * @return TRUE - Print Successful
	 */

	public static final boolean Print1DArray (
		final java.lang.String strName,
		final double[] adblA,
		final boolean bBailOnNaN)
	{
		if (null == adblA || 0 == adblA.length) return false;

		int iSize = adblA.length;

		for (int i = 0; i < iSize; ++i) {
			if (!org.drip.numerical.common.NumberUtil.IsValid (adblA[i]) && bBailOnNaN) return false;

			System.out.println (strName + "[" + i + "] = " + adblA[i]);
		}

		return true;
	}

	/**
	 * Print the contents of the 1D array to the Specified Decimal Location
	 * 
	 * @param strName Label Name
	 * @param adblA The 1D array
	 * @param iNumDecimal Number of Decimal Places to Display
	 * @param bBailOnNaN Bail on encountering an NaN
	 * 
	 * @return TRUE - Print Successful
	 */

	public static final boolean Print1DArray (
		final java.lang.String strName,
		final double[] adblA,
		final int iNumDecimal,
		final boolean bBailOnNaN)
	{
		if (null == adblA || 0 == adblA.length) return false;

		int iSize = adblA.length;

		for (int i = 0; i < iSize; ++i) {
			if (!org.drip.numerical.common.NumberUtil.IsValid (adblA[i]) && bBailOnNaN) return false;

			System.out.println (strName + "[" + i + "] = " + org.drip.numerical.common.FormatUtil.FormatDouble
				(adblA[i], 1, iNumDecimal, 1.));
		}

		return true;
	}

	/**
	 * Print the contents of the 2D array
	 * 
	 * @param strName Label Name
	 * @param aadblA The 2D array
	 * @param bBailOnNaN Bail on encountering an NaN
	 * 
	 * @return TRUE - Print Successful
	 */

	public static final boolean Print2DArray (
		final java.lang.String strName,
		final double[][] aadblA,
		final boolean bBailOnNaN)
	{
		if (null == aadblA) return false;

		int iRowSize = aadblA.length;

		if (0 == iRowSize || null == aadblA[0]) return false;

		int iColSize = aadblA[0].length;

		if (0 == iColSize) return false;

		for (int i = 0; i < iRowSize; ++i) {
			for (int j = 0; j < iColSize; ++j) {
				if (!org.drip.numerical.common.NumberUtil.IsValid (aadblA[i][j]) && bBailOnNaN) return false;

				System.out.println (strName + "[" + i + "][" + j + "] = " +
					org.drip.numerical.common.FormatUtil.FormatDouble (aadblA[i][j], 1, 6, 1.));
			}
		}

		return true;
	}

	/**
	 * Print the Contents of the 2D Array Pair
	 * 
	 * @param strLeftLabel Left Label
	 * @param strRightLabel Right Label
	 * @param aadblLeft The Left 2D array
	 * @param aadblRight The Right 2D array
	 * @param bBailOnNaN Bail on encountering an NaN
	 * 
	 * @return TRUE - Print Successful
	 */

	public static final boolean Print2DArrayPair (
		final java.lang.String strLeftLabel,
		final java.lang.String strRightLabel,
		final double[][] aadblLeft,
		final double[][] aadblRight,
		final boolean bBailOnNaN)
	{
		if (null == aadblLeft || null == aadblRight) return false;

		int iSize = aadblLeft.length;

		if (0 == iSize || iSize != aadblRight.length) return false;

		for (int i = 0; i < iSize; ++i) {
			for (int j = 0; j < iSize; ++j) {
				if (!org.drip.numerical.common.NumberUtil.IsValid (aadblLeft[i][j]) &&
					!org.drip.numerical.common.NumberUtil.IsValid (aadblRight[i][j]) && bBailOnNaN)
					return false;

				System.out.println (strLeftLabel + "[" + i + "][" + j + "] = " +
					org.drip.numerical.common.FormatUtil.FormatDouble (aadblLeft[i][j], 1, 6, 1.) + "  |  " +
						strRightLabel + "[" + i + "][" + j + "] = " +
							org.drip.numerical.common.FormatUtil.FormatDouble (aadblRight[i][j], 1, 6, 1.));
			}
		}

		return true;
	}

	/**
	 * Print the Contents of the 2D Array Triplet
	 * 
	 * @param strLeftLabel Left Label
	 * @param strMiddleLabel Middle Label
	 * @param strRightLabel Right Label
	 * @param aadblLeft The Left 2D array
	 * @param aadblMiddle The Middle 2D array
	 * @param aadblRight The Right 2D array
	 * @param bBailOnNaN Bail on encountering an NaN
	 * 
	 * @return TRUE - Print Successful
	 */

	public static final boolean Print2DArrayTriplet (
		final java.lang.String strLeftLabel,
		final java.lang.String strMiddleLabel,
		final java.lang.String strRightLabel,
		final double[][] aadblLeft,
		final double[][] aadblMiddle,
		final double[][] aadblRight,
		final boolean bBailOnNaN)
	{
		if (null == aadblLeft || null == aadblMiddle || null == aadblRight) return false;

		int iSize = aadblLeft.length;

		if (0 == iSize || iSize != aadblMiddle.length || iSize != aadblRight.length) return false;

		for (int i = 0; i < iSize; ++i) {
			for (int j = 0; j < iSize; ++j) {
				if (!org.drip.numerical.common.NumberUtil.IsValid (aadblLeft[i][j]) &&
						!org.drip.numerical.common.NumberUtil.IsValid (aadblLeft[i][j]) &&
							!org.drip.numerical.common.NumberUtil.IsValid (aadblRight[i][j]) && bBailOnNaN)
					return false;

				System.out.println (strLeftLabel + "[" + i + "][" + j + "] = " +
					org.drip.numerical.common.FormatUtil.FormatDouble (aadblLeft[i][j], 1, 6, 1.) + "  |  " +
						strMiddleLabel + "[" + i + "][" + j + "] = " +
							org.drip.numerical.common.FormatUtil.FormatDouble (aadblMiddle[i][j], 1, 6, 1.) +
								"  |  " + strRightLabel + "[" + i + "][" + j + "] = " +
									org.drip.numerical.common.FormatUtil.FormatDouble (aadblRight[i][j], 1, 6,
										1.));
			}
		}

		return true;
	}

	/**
	 * Print the Matrix Contents
	 * 
	 * @param strName Name of the Matrix
	 * @param aadblA Matrix
	 * 
	 * @return TRUE - Matrix Contents Successfully printed
	 */

	public static final boolean PrintMatrix (
		final java.lang.String strName,
		final double[][] aadblA)
	{
		if (null == aadblA || 0 == aadblA.length) return false;

		int iSize = aadblA.length;

		for (int i = 0; i < iSize; ++i) {
			java.lang.String strDump = strName  + " => ";

			for (int j = 0; j < iSize; ++j)
				strDump += org.drip.numerical.common.FormatUtil.FormatDouble (aadblA[i][j], 1, 6, 1.) + " |";

			System.out.println (strDump);
		}

		return true;
	}

	/**
	 * Compute (n - 0.5)!
	 * 
	 * @param n n
	 * 
	 * @return (n - 0.5)! Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double HalfDownShiftedFactorial (
		final int n)
		throws java.lang.Exception
	{
		if (-1 >= n)
		{
			throw new java.lang.Exception ("NumberUtil::HalfDownShiftedFactorial => Invalid Inputs");
		}

		double halfDownShiftedFactorial = java.lang.Math.sqrt (java.lang.Math.PI);

		for (double index = 1; index < n; ++index)
		{
			halfDownShiftedFactorial = halfDownShiftedFactorial * (index + 0.5);
		}

		return halfDownShiftedFactorial;
	}

	/**
	 * Compute (2n - 1)!!
	 * 
	 * @param n n
	 * 
	 * @return (2n - 1)!!
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double DoubleFactorial (
		final int n)
		throws java.lang.Exception
	{
		if (-1 >= n)
		{
			throw new java.lang.Exception ("NumberUtil::DoubleFactorial => Invalid Inputs");
		}

		double doubleFactorial = 1.;

		for (int index = 1; index <= n; ++index)
		{
			doubleFactorial = doubleFactorial * (2. * n - 1.);
		}

		return doubleFactorial;
	}

	/**
	 * Compute the Rising Pochhammer Symbol for the Specified s and k
	 * 
	 * @param s s
	 * @param k k
	 * 
	 * @return The Rising Pochhammer Symbol
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double RisingPochhammerSymbol (
		final double s,
		final int k)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (s) || 0 > k)
		{
			throw new java.lang.Exception ("NumberUtil::RisingPochhammerSymbol => Invalid Inputs");
		}

		if (0 == k)
		{
			return 1.;
		}

		double pochhammerSymbol = s;

		for (int index = 1; index < k; ++index)
		{
			pochhammerSymbol = pochhammerSymbol * (s + index);
		}

		return pochhammerSymbol;
	}

	/**
	 * Compute the Pochhammer Symbol for the Specified s and k
	 * 
	 * @param s s
	 * @param k k
	 * 
	 * @return Pochhammer Symbol
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double PochhammerSymbol (
		final double s,
		final int k)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (s) || 0 > k)
		{
			throw new java.lang.Exception ("NumberUtil::PochhammerSymbol => Invalid Inputs");
		}

		double pochhammerSymbol = s;

		for (int index = 1; index < k; ++index)
		{
			pochhammerSymbol = pochhammerSymbol * (s + index);
		}

		return pochhammerSymbol;
	}

	/**
	 * Indicate if z is an Integer
	 * 
	 * @param z Z
	 * 
	 * @return TRUE - z is an Integer
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final boolean IsInteger (
		final double z)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (z))
		{
			throw new java.lang.Exception ("NumberUtil::IsInteger => Invalid Inputs");
		}

		double absoluteZ = java.lang.Math.abs (z);

		return 0. == absoluteZ - (int) absoluteZ;
	}

	/**
	 * Indicate if z is a Positive Integer
	 * 
	 * @param z Z
	 * 
	 * @return TRUE - z is a Positive Integer
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final boolean IsPositiveInteger (
		final double z)
		throws java.lang.Exception
	{
		return IsInteger (z) && z > 0.;
	}

	/**
	 * Indicate if z is a Non-Positive Integer
	 * 
	 * @param z Z
	 * 
	 * @return TRUE - z is a Non-Positive Integer
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final boolean IsNonPositiveInteger (
		final double z)
		throws java.lang.Exception
	{
		return IsInteger (z) && z <= 0.;
	}

	/**
	 * Indicate if z is a Negative Integer
	 * 
	 * @param z Z
	 * 
	 * @return TRUE - z is a Negative Integer
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final boolean IsNegativeInteger (
		final double z)
		throws java.lang.Exception
	{
		return IsInteger (z) && z < 0.;
	}

	/**
	 * Indicate if z is a Non-Negative Integer
	 * 
	 * @param z Z
	 * 
	 * @return TRUE - z is a Non-Negative Integer
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final boolean IsNonNegativeInteger (
		final double z)
		throws java.lang.Exception
	{
		return IsInteger (z) && z >= 0.;
	}

	/**
	 * Indicate the Sign of z
	 * 
	 * @param z Z
	 * 
	 * @return Sign of z
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double Sign (
		final double z)
		throws java.lang.Exception
	{
		if (!IsValid (z))
		{
			throw new java.lang.Exception ("NumberUtil::Sign => Invalid Inputs");
		}

		return 0. == z ? 1. : java.lang.Math.abs (z) / z;
	}

	/**
	 * Check if the Array Elements are Normalized and Positive
	 * 
	 * @param array Array
	 * 
	 * @return TRUE - The Array Elements are Normalized and Positive
	 */

	public static final boolean NormalizedPositive (
		final double[] array)
	{
		if (null == array)
		{
			return false;
		}

		double sum = 0.;
		int size = array.length;

		if (0 == size)
		{
			return false;
		}

		for (int index = 0; index < size; ++index)
		{
			if (!org.drip.numerical.common.NumberUtil.IsValid (array[index]) || 0. >= array[index])
			{
				return false;
			}

			sum = sum + array[index];
		}

		return 1. == sum;
	}

	/**
	 * Retrieve the Fractional Part of z
	 * 
	 * @param z Z
	 * 
	 * @return The Fractional Part of z
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double Fractional (
		final double z)
		throws java.lang.Exception
	{
		if (!IsValid (
			z
		))
		{
			throw new java.lang.Exception (
				"NumberUtil::Fractional => Invalid Inputs"
			);
		}

		return z - (int) z;
	}

	/**
	 * Retrieve the Reciprocal Integer Floor of z
	 * 
	 * @param z Z
	 * 
	 * @return The Reciprocal Integer Floor of z
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final int ReciprocalIntegerFloor (
		final double z)
		throws java.lang.Exception
	{
		if (!IsValid (
			z
		))
		{
			throw new java.lang.Exception (
				"NumberUtil::ReciprocalIntegerFloor => Invalid Inputs"
			);
		}

		return (int) (1. / z);
	}

	/**
	 * Retrieve the Map of Prime Factor Count for the given Number
	 * 
	 * @param n The Given Number
	 * 
	 * @return Map of Prime Factor Count
	 */

	public static final java.util.TreeMap<java.lang.Integer, java.lang.Integer> PrimeFactorMap (
		int n)
	{
		if (0 >= n)
		{
			n = -1 * n;
		}

		int dePrimed = n;

		java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeFactorMap =
			new java.util.TreeMap<java.lang.Integer, java.lang.Integer>();

		int max = (int) java.lang.Math.sqrt (
			n
		);

		for (int index = 2;
			index <= max;
			++index
		)
		{
			int remainder = dePrimed % index;

			while (0 == remainder)
			{
				if (primeFactorMap.containsKey (
					index
				))
				{
					primeFactorMap.put (
						index,
						primeFactorMap.get (
							index
						) + 1
					);
				}
				else
				{
					primeFactorMap.put (
						index,
						1
					);
				}

				dePrimed = dePrimed / index;
				remainder = dePrimed % index;
			}
		}

		primeFactorMap.put (
			dePrimed,
			1
		);

		return primeFactorMap;
	}

	/**
	 * Compute the Prime Factor for a given Integer
	 * 
	 * @param n The Integer
	 * 
	 * @return Prime Factor for a given Integer
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double PrimeFactor (
		final int n)
		throws java.lang.Exception
	{
		java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeFactorMap = PrimeFactorMap (
			n
		);

		if (null == primeFactorMap || 0 == primeFactorMap.size())
		{
			throw new java.lang.Exception (
				"PrimeFactorTable::PrimeFactor => Cannot extract Map"
			);
		}

		return ComputeFromPrimeFactorMap (
			primeFactorMap
		);
	}

	/**
	 * Compute the Exponent 2 of Prime Factorization for a given Integer
	 * 
	 * @param n The Integer
	 * 
	 * @return Exponent 2 of Prime Factorization for a given Integer
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double PrimeFactorExponentTwo (
		final int n)
		throws java.lang.Exception
	{
		return java.lang.Math.log (
			n
		) / java.lang.Math.log (
			2.
		);
	}

	/**
	 * Retrieve the Binary Digit Count
	 * 
	 * @param n N
	 * 
	 * @return The Binary Digit Count
	 */

	public static final int BinaryDigitCount (
		int n)
	{
		if (0 == n)
		{
			return 0;
		}

		if (n < 0)
		{
			n= n * -1;
		}

		int binaryDigitCount = 0;

		while (n != 0)
		{
			binaryDigitCount = binaryDigitCount + (n % 2);
			n = n / 2;
		}

		return binaryDigitCount;
	}

	/**
	 * Divide two integers without using multiplication, division, and mod operator. The integer division
	 *  should truncate toward zero, which means losing its fractional part. For example, truncate(8.345) = 8
	 *  and truncate(-2.7335) = -2.
	 *   
	 * @param dividend Dividend
	 * @param divisor Divisor
	 * 
	 * @return The quotient after dividing dividend by divisor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final int DivideIntegers (
		final int dividend,
		final int divisor)
		throws java.lang.Exception
	{
		if (0 == divisor)
		{
			throw new java.lang.Exception (
				"NumberUtil::DivideIntegers => Invalid Divisor"
			);
		}

		int sign = 1;
		int unsignedDivisor = divisor;
		int unsignedDividend = dividend;

		if (0 > unsignedDivisor && 0 > unsignedDividend)
		{
			unsignedDivisor = -1 * unsignedDivisor;
			unsignedDividend = -1 * unsignedDividend;
		}
		else if (0 > unsignedDivisor)
		{
			sign = -1;
			unsignedDivisor = -1 * unsignedDivisor;
		}
		else if (0 > unsignedDividend)
		{
			sign = -1;
			unsignedDividend = -1 * unsignedDividend;
		}

		return sign * Quotient (
			unsignedDividend,
			unsignedDivisor
		);
	}

	/**
	 * Find the n-th ugly number. Ugly numbers are positive integers which are divisible by a or b or c.
	 * 
	 * @param n n
	 * @param a a
	 * @param b b
	 * @param c c
	 * 
	 * @return The n-th Ugly Number
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final int UglyNumber (
		final int n,
		final int a,
		final int b,
		final int c)
		throws java.lang.Exception
	{
		if (0 >= n ||
			0 >= a ||
			0 >= b ||
			0 >= c)
		{
			throw new java.lang.Exception (
				"NumberUtil::UglyNumber => Invalid Divisor"
			);
		}

		int ugly1 = -1;

		if (a < b && a < c)
		{
			ugly1 = a;
		}

		if (b < a && b < c)
		{
			ugly1 = b;
		}

		if (c < a && c < b)
		{
			ugly1 = c;
		}

		if (1 == n)
		{
			return ugly1;
		}

		java.util.TreeMap<java.lang.Integer, java.lang.Integer> startingPrimeFactorMap = PrimeFactorMap (
			ugly1
		);

		java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeFactorMapA = ugly1 == a ?
			startingPrimeFactorMap : PrimeFactorMap (
				a
			);

		java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeFactorMapB = ugly1 == b ?
			startingPrimeFactorMap : PrimeFactorMap (
				b
			);

		java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeFactorMapC = ugly1 == c ?
			startingPrimeFactorMap : PrimeFactorMap (
				c
			);

		java.util.TreeMap<java.lang.Integer, java.lang.Integer> baselinePrimeCountMap =
			new java.util.TreeMap<java.lang.Integer, java.lang.Integer>();

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> primeCountMapAEntry :
			primeFactorMapA.entrySet())
		{
			baselinePrimeCountMap.put (
				primeCountMapAEntry.getKey(),
				primeCountMapAEntry.getValue()
			);
		}

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> primeCountMapBEntry :
			primeFactorMapB.entrySet())
		{
			int primeKey = primeCountMapBEntry.getKey();

			int primeCount = primeCountMapBEntry.getValue();

			if (baselinePrimeCountMap.containsKey (
				primeKey
			))
			{
				if (baselinePrimeCountMap.get (
						primeKey
					) < primeCount
				)
				{
					baselinePrimeCountMap.put (
						primeKey,
						primeCount
					);
				}
			}
			else
			{
				baselinePrimeCountMap.put (
					primeKey,
					primeCount
				);
			}
		}

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> primeCountMapCEntry :
			primeFactorMapC.entrySet())
		{
			int primeKey = primeCountMapCEntry.getKey();

			int primeCount = primeCountMapCEntry.getValue();

			if (baselinePrimeCountMap.containsKey (
				primeKey
			))
			{
				if (baselinePrimeCountMap.get (
						primeKey
					) < primeCount
				)
				{
					baselinePrimeCountMap.put (
						primeKey,
						primeCount
					);
				}
			}
			else
			{
				baselinePrimeCountMap.put (
					primeKey,
					primeCount
				);
			}
		}

		java.util.TreeMap<java.lang.Integer, java.lang.Integer> compositePrimeCountUpdateMap =
			new java.util.TreeMap<java.lang.Integer, java.lang.Integer>();

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> compositePrimeCountMapEntry :
			baselinePrimeCountMap.entrySet())
		{
			compositePrimeCountUpdateMap.put (
				compositePrimeCountMapEntry.getKey(),
				0
			);
		}

		for (int uglyIndex = 4;
			uglyIndex <= n;
			++uglyIndex)
		{
			int primeNumberToIncrement = PrimeNumberToIncrement (
				compositePrimeCountUpdateMap
			);

			compositePrimeCountUpdateMap.put (
				primeNumberToIncrement,
				compositePrimeCountUpdateMap.get (
					primeNumberToIncrement
				) + 1
			);
		}

		return ComputeFromPrimeFactorMap (
			startingPrimeFactorMap
		) * ComputeFromPrimeFactorMap (
			compositePrimeCountUpdateMap
		);
	}
}
