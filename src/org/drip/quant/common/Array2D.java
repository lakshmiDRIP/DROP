
package org.drip.quant.common;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * Array2D the contains array of x and y. It provides methods to create/access different varieties of x/y
 *  schedule creation.
 *
 * @author Lakshmi Krishnamurthy
 */

public class Array2D {
	private double _adblX[] = null;
	private double _adblY[] = null;

	/**
	 * Generate the Array2D Schedule from the String Representation of the Vertex Dates and Edge Payments
	 * 	Combination.
	 * 
	 * @param strDateAmountVertex The String Array consisting of the Date and the Amount Vertexes
	 * @param iMaturityDate The Maturity Date
	 * @param dblInitialAmount The Initial Amount
	 * 
	 * @return The Array2D Instance
	 */

	public static final Array2D FromDateAmountVertex (
		final java.lang.String strDateAmountVertex,
		final int iMaturityDate,
		final double dblInitialAmount)
	{
		if (null == strDateAmountVertex || strDateAmountVertex.isEmpty() ||
			!org.drip.quant.common.NumberUtil.IsValid (dblInitialAmount) || 0. >= dblInitialAmount)
			return null;

		java.lang.String[] astrDateAmountEdge = org.drip.quant.common.StringUtil.Split (strDateAmountVertex,
			";");

		if (null == astrDateAmountEdge) return null;

		int iNumDateFactorPair = astrDateAmountEdge.length;
		int iNumVertex = iNumDateFactorPair / 2 + 1;
		double[] adblVertexDate = new double[iNumVertex];
		double[] adblVertexFactor = new double[iNumVertex];
		double[] adblVertexOutstanding = new double[iNumVertex];
		adblVertexOutstanding[0] = dblInitialAmount;

		if (0 == iNumDateFactorPair || 1 == iNumDateFactorPair % 2) return null;

		for (int i = 0; i < iNumVertex - 1; ++i) {
			org.drip.analytics.date.JulianDate dt = org.drip.analytics.date.DateUtil.FromMDY
				(astrDateAmountEdge[2 * i], "/");

			java.lang.String strEdgeAmount = astrDateAmountEdge[2 * i + 1];

			if (null == dt || null == strEdgeAmount || strEdgeAmount.isEmpty()) return null;

			adblVertexDate[i] = dt.julian();

			double dblPrincipalPayAmount = java.lang.Double.parseDouble (strEdgeAmount.trim());

			if (!org.drip.quant.common.NumberUtil.IsValid (dblPrincipalPayAmount)) return null;

			if (0. >= dblPrincipalPayAmount || 0. >= (adblVertexOutstanding[i + 1] = adblVertexOutstanding[i]
				- dblPrincipalPayAmount))
				return null;
		}

		for (int i = 0; i < iNumVertex; ++i)
			adblVertexFactor[i] = adblVertexOutstanding[i] / dblInitialAmount;

		adblVertexDate[iNumVertex - 1] = iMaturityDate;

		return FromArray (adblVertexDate, adblVertexFactor);
	}

	/**
	 * Generate an Array2D Instance from the String Array containing semi-colon delimited Date/Factor Vertex
	 *  Pair
	 * 
	 * @param strDateFactorVertex The String Array containing semi-colon delimited Date/Edge Pair
	 * @param iMaturityDate The Maturity Date
	 * @param dblInitialAmount The Initial Amount
	 * 
	 * @return The Array2D Instance
	 */

	public static final Array2D FromDateFactorVertex (
		final java.lang.String strDateFactorVertex,
		final int iMaturityDate,
		final double dblInitialAmount)
	{
		if (null == strDateFactorVertex || strDateFactorVertex.isEmpty() ||
			!org.drip.quant.common.NumberUtil.IsValid (dblInitialAmount))
			return null;

		java.lang.String[] astrDateFactorVertex = org.drip.quant.common.StringUtil.Split
			(strDateFactorVertex, ";");

		if (null == astrDateFactorVertex) return null;

		int iNumDateFactorPair = astrDateFactorVertex.length;
		int iNumVertex = iNumDateFactorPair / 2 + 1;
		double[] adblVertexDate = new double[iNumVertex];
		double[] adblVertexFactor = new double[iNumVertex];
		adblVertexFactor[0] = 1.;

		if (0 == iNumDateFactorPair || 1 == iNumDateFactorPair % 2) return null;

		for (int i = 0; i < iNumVertex - 1; ++i) {
			org.drip.analytics.date.JulianDate dt = org.drip.analytics.date.DateUtil.FromMDY
				(astrDateFactorVertex[2 * i], "/");

			java.lang.String strFactor = astrDateFactorVertex[2 * i + 1];

			if (null == dt || null == strFactor || strFactor.isEmpty()) return null;

			adblVertexDate[i] = dt.julian();

			if (!org.drip.quant.common.NumberUtil.IsValid (adblVertexFactor[i + 1] =
				java.lang.Double.parseDouble (strFactor.trim()) / dblInitialAmount))
				return null;
		}

		adblVertexDate[iNumVertex - 1] = iMaturityDate;

		return FromArray (adblVertexDate, adblVertexFactor);
	}

	/**
	 * Generate an Array2D Instance from the String Array containing semi-colon delimited Date/Factor Vertex
	 *  Pair
	 * 
	 * @param strDateFactorVertex The String Array containing semi-colon delimited Date/Edge Pair
	 * @param iMaturityDate The Maturity Date
	 * 
	 * @return The Array2D Instance
	 */

	public static final Array2D FromDateFactorVertex (
		final java.lang.String strDateFactorVertex,
		final int iMaturityDate)
	{
		if (null == strDateFactorVertex || strDateFactorVertex.isEmpty()) return null;

		java.lang.String[] astrDateFactorVertex = org.drip.quant.common.StringUtil.Split
			(strDateFactorVertex, ";");

		if (null == astrDateFactorVertex) return null;

		int iNumDateFactorPair = astrDateFactorVertex.length;
		int iNumVertex = iNumDateFactorPair / 2 + 1;
		double[] adblVertexDate = new double[iNumVertex];
		double[] adblVertexFactor = new double[iNumVertex];
		adblVertexFactor[0] = 1.;

		if (0 == iNumDateFactorPair || 1 == iNumDateFactorPair % 2) return null;

		for (int i = 0; i < iNumVertex - 1; ++i) {
			org.drip.analytics.date.JulianDate dt = org.drip.analytics.date.DateUtil.FromMDY
				(astrDateFactorVertex[2 * i], "/");

			java.lang.String strFactor = astrDateFactorVertex[2 * i + 1];

			if (null == dt || null == strFactor || strFactor.isEmpty()) return null;

			adblVertexDate[i] = dt.julian();

			if (!org.drip.quant.common.NumberUtil.IsValid (adblVertexFactor[i + 1] =
				java.lang.Double.parseDouble (strFactor.trim())))
				return null;
		}

		adblVertexDate[iNumVertex - 1] = iMaturityDate;

		return FromArray (adblVertexDate, adblVertexFactor);
	}

	/**
	 * Create the Array2D Instance from a Matched String Array of X and Y
	 * 
	 * @param strX String Array of X
	 * @param strY String Array of Y
	 * 
	 * @return The Array2D Instance
	 */

	public static final Array2D FromStringSet (
		final java.lang.String strX,
		final java.lang.String strY)
	{
		if (null == strX || strX.isEmpty() || null == strY || strY.isEmpty()) return null;

		try {
			return new Array2D (org.drip.quant.common.StringUtil.MakeDoubleArrayFromStringTokenizer (new
				java.util.StringTokenizer (strX, ";")),
					org.drip.quant.common.StringUtil.MakeDoubleArrayFromStringTokenizer (new
						java.util.StringTokenizer (strY, ";")));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the Array2D Instance from a Matched Array of X and Y
	 * 
	 * @param adblX Array of X
	 * @param adblY Array of Y
	 * 
	 * @return The Array2D Instance
	 */

	public static final Array2D FromArray (
		final double[] adblX,
		final double[] adblY)
	{
		try {
			return new Array2D (adblX, adblY);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the Array2D Instance from a Matched Array of X and Y
	 * 
	 * @param aiX Array of X
	 * @param adblY Array of Y
	 * 
	 * @return The Array2D Instance
	 */

	public static final Array2D FromArray (
		final int[] aiX,
		final double[] adblY)
	{
		if (null == aiX) return null;

		int iSize = aiX.length;
		double[] adblX = new double[iSize];

		if (0 == iSize) return null;

		for (int i = 0; i < iSize; ++i)
			adblX[i] = aiX[i];

		return FromArray (adblX, adblY);
	}

	/**
	 * Create the Array2D Instance from a Matched Array of X and Y Deltas
	 * 
	 * @param adblX Array of X
	 * @param adblYDelta Array of Y Deltas
	 * @param dblYInitial The Initial Value of Y
	 * 
	 * @return The Array2D Instance
	 */

	public static final Array2D FromXYDeltaArray (
		final double[] adblX,
		final double[] adblYDelta,
		final double dblYInitial)
	{
		if (null == adblX || null == adblYDelta || !org.drip.quant.common.NumberUtil.IsValid (dblYInitial))
			return null;

		int i = 0;
		int iLength = adblX.length;
		double[] adblY = new double[iLength];
		adblY[0] = dblYInitial;

		if (0 == iLength || iLength != adblYDelta.length) return null;

		for (double dblYDelta : adblYDelta) {
			if (i < iLength - 1) adblY[i + 1] = adblY[i] - dblYDelta;

			++i;
		}

		try {
			return new Array2D (adblX, adblY);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Array2D Instance from the Flat Unit Y
	 * 
	 * @return The Array2D Instance
	 */

	public static final Array2D BulletSchedule()
	{
		double[] adblFactor = new double[1];
		double[] adblDate = new double[1];
		adblFactor[0] = 1.;

		adblDate[0] = org.drip.analytics.date.DateUtil.CreateFromYMD (1900, 1, 1).julian();

		try {
			return new Array2D (adblDate, adblFactor);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private Array2D (
		final double[] adblX,
		final double[] adblY)
		throws java.lang.Exception
	{
		if (null == adblX || null == adblY) throw new java.lang.Exception ("Array2D ctr => Invalid params");

		int iLength = adblX.length;
		_adblX = new double[iLength];
		_adblY = new double[iLength];

		if (0 == iLength || iLength != adblY.length)
			throw new java.lang.Exception ("Array2D ctr => Invalid params");

		for (int i = 0; i < iLength; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (_adblX[i] = adblX[i]) ||
				!org.drip.quant.common.NumberUtil.IsValid (_adblY[i] = adblY[i]))
				throw new java.lang.Exception ("Array2D ctr => Invalid params");
		}
	}

	/**
	 * Retrieve the Y given X
	 * 
	 * @param dblX X
	 * 
	 * @return Y 
	 * 
	 * @throws java.lang.Exception Thrown if the Y cannot be computed
	 */

	public double y (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception ("Array2D::y => Invalid Input");

		if (dblX <= _adblX[0]) return _adblY[0];

		int iLength = _adblX.length;

		for (int i = 1; i < iLength; ++i) {
			if (dblX > _adblX[i - 1] && dblX <= _adblX[i]) return _adblY[i];
		}

		return _adblY[iLength - 1];
	}

	/**
	 * Retrieve the Index that corresponds to the given X
	 * 
	 * @param dblX X
	 * 
	 * @return Index 
	 * 
	 * @throws java.lang.Exception Thrown if the Index cannot be computed
	 */

	public int index (
		final double dblX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblX))
			throw new java.lang.Exception ("Array2D::index => Invalid Input/State");

		if (dblX <= _adblX[0]) return 0;

		int iLength = _adblX.length;

		for (int i = 1; i < iLength; ++i) {
			if (dblX <= _adblX[i]) return i;
		}

		return iLength - 1;
	}

	/**
	 * Retrieve the X-Weighted Y
	 * 
	 * @param dblStartX Start X
	 * @param dblEndX End X
	 * 
	 * @return The X-Weighted Y 
	 * 
	 * @throws java.lang.Exception Thrown if the Y cannot be computed
	 */

	public double y (
		final double dblStartX,
		final double dblEndX)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblStartX) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblEndX))
			throw new java.lang.Exception ("Array2D::y => Invalid Inputs");

		int iEndIndex = index (dblEndX);

		int iStartIndex = index (dblStartX);

		if (iStartIndex == iEndIndex) return _adblY[iStartIndex];

		double dblWeightedY = _adblY[iStartIndex] * (_adblX[iStartIndex] - dblStartX);

		for (int i = iStartIndex + 1; i <= iEndIndex; ++i)
			dblWeightedY += _adblY[i] * (_adblX[i] - _adblX[i - 1]);

		return (dblWeightedY + _adblY[iEndIndex] * (dblEndX - _adblX[iEndIndex])) / (dblEndX - dblStartX);
	}

	/**
	 * Retrieve the Array of X
	 * 
	 * @return Array of X
	 */

	public double[] x()
	{
		return _adblX;
	}

	/**
	 * Retrieve the Array of Y
	 * 
	 * @return Array of Y
	 */

	public double[] y()
	{
		return _adblY;
	}

	/**
	 * Indicate if this Array2D Instance matches the "other" Entry-by-Entry
	 * 
	 * @param a2DOther The "Other" Array2D Instance
	 * 
	 * @return TRUE - The Array2D Instances match Entry-by-Entry
	 */

	public boolean match (
		final Array2D a2DOther)
	{
		if (null == a2DOther) return false;

		double[] adblOtherX = a2DOther._adblX;
		double[] adblOtherY = a2DOther._adblY;
		int iNumOtherX = adblOtherX.length;
		int iNumOtherY = adblOtherY.length;

		if (iNumOtherX != _adblX.length || iNumOtherY != _adblY.length) return false;

		for (int i = 0; i < iNumOtherX; ++i) {
			if (adblOtherX[i] != _adblX[i]) return false;
		}

		for (int i = 0; i < iNumOtherY; ++i) {
			if (adblOtherY[i] != _adblY[i]) return false;
		}

		return true;
	}
}
