
package org.drip.feed.loader;

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
 * <i>CSVGrid</i> Holds the Outputs of a CSV Parsing Exercise.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Load, Transform, and compute Target Metrics across Feeds</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/loader/README.md">Reference/Market Data Feed Loader</a></li>
 *  </ul>
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblScaler) || 0 > iColumn) return null;

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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblScaler) || null == _lsastr) return null;

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
				dblHeader = java.lang.Double.parseDouble (strHeader);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblHeader)) return null;

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
						dblClosingMark = java.lang.Double.parseDouble (strClosingMark);
				} catch (java.lang.Exception e) {
				}

				if (org.drip.numerical.common.NumberUtil.IsValid (dblClosingMark))
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblScaler) || null == _lsastr) return null;

		int iNumClose = _lsastr.size();

		if (0 == iNumClose) return null;

		java.lang.String[] astrHeader = _lsastr.get (0);

		int iNumMark = astrHeader.length;
		java.lang.String[] astrTenor = new java.lang.String[iNumMark - 1];
		java.lang.String[] astrInstrument = new java.lang.String[iNumMark - 1];

		for (int i = 1; i < iNumMark; ++i) {
			astrHeader[i].trim();

			if (null == astrHeader[i] || astrHeader[i].isEmpty()) astrHeader[i] = "@:#";

			java.lang.String[] astrInstrumentTenor = org.drip.numerical.common.StringUtil.Split
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
						dblClosingMark = java.lang.Double.parseDouble (strClosingMark);
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
