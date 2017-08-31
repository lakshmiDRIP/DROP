
package org.drip.feed.loader;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * CSVGrid Holds the Outputs of a CSV Parsing Exercise.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CSVGrid {
	private java.lang.String[] _astrHeader = null;
	private java.util.List<java.lang.String[]> _lsastr = null;

	/**
	 * Convert the String Element to double. Fall back is NaN.
	 * 
	 * @param strElement String Element
	 * 
	 * @return The Return Value
	 */

	public static final double ToDouble (
		final java.lang.String strElement)
	{
		if (null == strElement || strElement.trim().isEmpty()) return java.lang.Double.NaN;

		try {
			return java.lang.Double.parseDouble (strElement);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return java.lang.Double.NaN;
	}

	/**
	 * Convert the String Element to int. Fall back is MIN_VALUE.
	 * 
	 * @param strElement String Element
	 * 
	 * @return The Return Value
	 */

	public static final int ToInteger (
		final java.lang.String strElement)
	{
		if (null == strElement || strElement.trim().isEmpty()) return java.lang.Integer.MIN_VALUE;

		try {
			return java.lang.Integer.parseInt (strElement);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return java.lang.Integer.MIN_VALUE;
	}

	/**
	 * Convert the String Element to a JulianDate Instance.
	 * 
	 * @param strElement String Element
	 * 
	 * @return The Return Value
	 */

	public static final org.drip.analytics.date.JulianDate ToDate (
		final java.lang.String strElement)
	{
		org.drip.analytics.date.JulianDate dt = org.drip.analytics.date.DateUtil.CreateFromDDMMMYYYY
			(strElement.trim());

		if (null != dt || null != (dt = org.drip.analytics.date.DateUtil.MakeJulianFromDDMMMYY (strElement,
			"-")))
			return dt;

		return org.drip.analytics.date.DateUtil.CreateFromMDY (strElement, "-");
	}

	/**
	 * Empty CSVGrid Constructor
	 */

	public CSVGrid()
	{
	}

	/**
	 * Retrieve the Underlying CSV Grid
	 * 
	 * @return The Underlying CSV Field Grid
	 */

	public java.util.List<java.lang.String[]> grid()
	{
		return _lsastr;
	}

	/**
	 * Retrieve the Size of the Sample Set
	 * 
	 * @return Size of the Sample Set
	 */

	public int size()
	{
		return null == _lsastr ? 0 : _lsastr.size();
	}

	/**
	 * Set the Column Headers
	 * 
	 * @param astrHeader The Column Headers
	 * 
	 * @return TRUE - Column Headers successfully set
	 */

	public boolean setHeader (
		final java.lang.String[] astrHeader)
	{
		if (null == astrHeader || 0 == astrHeader.length) return false;

		_astrHeader = astrHeader;
		return true;
	}

	/**
	 * Add a String Array to the Grid
	 * 
	 * @param astr The String Array
	 * 
	 * @return TRUE - The String Array successfully added
	 */

	public boolean add (
		final java.lang.String[] astr)
	{
		if (null == astr || 0 == astr.length) return false;

		if (null == _lsastr) _lsastr = new java.util.ArrayList<java.lang.String[]>();

		_lsastr.add (astr);

		return true;
	}

	/**
	 * Retrieve the Array of Headers
	 * 
	 * @return The Header Array
	 */

	public java.lang.String[] headers()
	{
		return _astrHeader;
	}

	/**
	 * Retrieve the Header identified by the Index
	 * 
	 * @param iIndex The Index
	 * 
	 * @return The Header identified by the Index
	 */

	public java.lang.String header (
		final int iIndex)
	{
		return null == _astrHeader || _astrHeader.length <= iIndex ? null : _astrHeader[iIndex];
	}

	/**
	 * 
	 * Retrieve the Array of String Values corresponding to the specified Column Index
	 * 
	 * @param iColumn The Column Index
	 * 
	 * @return The Array of Strings
	 */

	public java.lang.String[] stringArrayAtColumn (
		final int iColumn)
	{
		if (0 > iColumn) return null;

		int iRow = 0;

		java.lang.String[] astrColumn = new java.lang.String[_lsastr.size()];

		for (java.lang.String[] astr : _lsastr) {
			if (null == astr || astr.length <= iColumn) return null;

			astrColumn[iRow++] = astr[iColumn];
		}

		return astrColumn;
	}

	/**
	 * Retrieve the Array of Integer Values corresponding to the specified Column Index
	 * 
	 * @param iColumn The Column Index
	 * 
	 * @return The Array of Integers
	 */

	public int[] intArrayAtColumn (
		final int iColumn)
	{
		if (0 > iColumn) return null;

		int iRow = 0;

		int[] ai = new int[_lsastr.size()];

		for (java.lang.String[] astr : _lsastr) {
			if (null == astr || astr.length <= iColumn) return null;

			ai[iRow++] = ToInteger (astr[iColumn]);
		}

		return ai;
	}

	/**
	 * Retrieve the Array of Double Values corresponding to the specified Column Index
	 * 
	 * @param iColumn The Column Index
	 * @param dblScaler The Scaling Multiplier
	 * 
	 * @return The Array of Doubles
	 */

	public double[] doubleArrayAtColumn (
		final int iColumn,
		final double dblScaler)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblScaler) || 0 > iColumn) return null;

		int iRow = 0;

		double[] adbl = new double[_lsastr.size()];

		for (java.lang.String[] astr : _lsastr) {
			if (null == astr || astr.length <= iColumn) return null;

			adbl[iRow++] = dblScaler * ToDouble (astr[iColumn]);
		}

		return adbl;
	}

	/**
	 * Retrieve the Array of Double Values corresponding to the specified Column Index
	 * 
	 * @param iColumn The Column Index
	 * 
	 * @return The Array of Doubles
	 */

	public double[] doubleArrayAtColumn (
		final int iColumn)
	{
		return doubleArrayAtColumn (iColumn, 1.);
	}

	/**
	 * Retrieve the Array of JulianDate corresponding to the specified Column Index
	 * 
	 * @param iColumn The Column Index
	 * 
	 * @return The Array of JulianDate
	 */

	public org.drip.analytics.date.JulianDate[] dateArrayAtColumn (
		final int iColumn)
	{
		if (0 > iColumn) return null;

		int iRow = 0;

		org.drip.analytics.date.JulianDate[] adt = new org.drip.analytics.date.JulianDate[_lsastr.size()];

		for (java.lang.String[] astr : _lsastr) {
			if (null == astr || astr.length <= iColumn) return null;

			adt[iRow++] = ToDate (astr[iColumn]);
		}

		return adt;
	}

	/**
	 * Construct a Historical Map of Scaled/Keyed Double
	 * 
	 * @param dblScaler The Scale to be applied
	 * 
	 * @return Historical Map of Scaled/Keyed Double
	 */

	public java.util.Map<org.drip.analytics.date.JulianDate, java.util.Map<java.lang.Double,
		java.lang.Double>>
			doubleMap (
				final double dblScaler)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblScaler) || null == _lsastr) return null;

		int iNumClose = _lsastr.size();

		if (0 == iNumClose) return null;

		java.lang.String[] astrHeader = _lsastr.get (0);

		int iNumMark = astrHeader.length;
		double adblHeader[] = new double[iNumMark];

		for (int i = 1; i < iNumMark; ++i) {
			if (null == astrHeader[i] || astrHeader[i].isEmpty()) return null;

			double dblHeader = java.lang.Double.NaN;

			java.lang.String strHeader = astrHeader[i].trim();

			if (null == strHeader || strHeader.isEmpty()) return null;

			try {
				dblHeader = new java.lang.Double (strHeader);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (!org.drip.quant.common.NumberUtil.IsValid (dblHeader)) return null;

			adblHeader[i] = dblHeader;;
		}

		java.util.Map<org.drip.analytics.date.JulianDate, java.util.Map<java.lang.Double, java.lang.Double>>
			mapClose = new java.util.TreeMap<org.drip.analytics.date.JulianDate,
				java.util.Map<java.lang.Double, java.lang.Double>>();

		for (int i = 1; i < iNumClose; ++i) {
			java.util.Map<java.lang.Double, java.lang.Double> mapClosingMark = new
				java.util.TreeMap<java.lang.Double, java.lang.Double>();

			java.lang.String[] astrClosingMark = _lsastr.get (i);

			for (int j = 1; j < iNumMark; ++j) {
				double dblClosingMark = java.lang.Double.NaN;

				java.lang.String strClosingMark = null == astrClosingMark[j] || astrClosingMark[j].isEmpty()
					? null : astrClosingMark[j].trim();

				try {
					if (null != strClosingMark && !strClosingMark.isEmpty())
						dblClosingMark = new java.lang.Double (strClosingMark);
				} catch (java.lang.Exception e) {
				}

				if (org.drip.quant.common.NumberUtil.IsValid (dblClosingMark))
					mapClosingMark.put (adblHeader[j], dblScaler * dblClosingMark);
			}

			if (0 != mapClosingMark.size())
				mapClose.put (org.drip.analytics.date.DateUtil.CreateFromDDMMMYYYY (astrClosingMark[0]),
					mapClosingMark);
		}

		return mapClose;
	}

	/**
	 * Construct a Historical Map of Scaled/Keyed/Tenor Ordered Double
	 * 
	 * @param dblScaler The Scale to be applied
	 * 
	 * @return Historical Map of Scaled/Keyed/Tenor Ordered Double
	 */

	public java.util.Map<org.drip.analytics.date.JulianDate, org.drip.feed.loader.InstrumentSetTenorQuote>
		groupedOrderedDouble (
			final double dblScaler)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblScaler) || null == _lsastr) return null;

		int iNumClose = _lsastr.size();

		if (0 == iNumClose) return null;

		java.lang.String[] astrHeader = _lsastr.get (0);

		int iNumMark = astrHeader.length;
		java.lang.String[] astrTenor = new java.lang.String[iNumMark - 1];
		java.lang.String[] astrInstrument = new java.lang.String[iNumMark - 1];

		for (int i = 1; i < iNumMark; ++i) {
			astrHeader[i].trim();

			if (null == astrHeader[i] || astrHeader[i].isEmpty()) astrHeader[i] = "@:#";

			java.lang.String[] astrInstrumentTenor = org.drip.quant.common.StringUtil.Split
				(astrHeader[i].trim(), ":");

			if (null == astrInstrumentTenor || 2 != astrInstrumentTenor.length) return null;

			astrTenor[i - 1] = astrInstrumentTenor[1];
			astrInstrument[i - 1] = astrInstrumentTenor[0];
		}

		java.util.Map<org.drip.analytics.date.JulianDate, org.drip.feed.loader.InstrumentSetTenorQuote>
			mapISTQ = new java.util.TreeMap<org.drip.analytics.date.JulianDate,
				org.drip.feed.loader.InstrumentSetTenorQuote>();

		for (int i = 1; i < iNumClose; ++i) {
			java.lang.String[] astrClosingMark = _lsastr.get (i);

			org.drip.feed.loader.InstrumentSetTenorQuote istq = new
				org.drip.feed.loader.InstrumentSetTenorQuote();

			for (int j = 1; j < iNumMark; ++j) {
				double dblClosingMark = java.lang.Double.NaN;

				java.lang.String strClosingMark = null == astrClosingMark[j] || astrClosingMark[j].isEmpty()
					? null : astrClosingMark[j].trim();

				try {
					if (null != strClosingMark && !strClosingMark.isEmpty())
						dblClosingMark = new java.lang.Double (strClosingMark);
				} catch (java.lang.Exception e) {
				}

				istq.add (astrInstrument[j - 1], astrTenor[j - 1], dblClosingMark, dblScaler);
			}

			if (!istq.isEmpty())
				mapISTQ.put (org.drip.analytics.date.DateUtil.CreateFromDDMMMYYYY (astrClosingMark[0]),
					istq);
		}

		return mapISTQ;
	}
}
