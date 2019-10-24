
package org.drip.historical.attribution;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>PositionMarketSnap</i> contains the Metrics Snapshot associated with the relevant Manifest Measures for
 * a given Position.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/README.md">Historical State Processing Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/attribution/README.md">Position Market Change Components Attribution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PositionMarketSnap {
	private double _dblMarketValue = java.lang.Double.NaN;
	private org.drip.analytics.date.JulianDate _dtSnap = null;

	private java.util.Map<java.lang.String, org.drip.historical.attribution.PositionManifestMeasureSnap>
		_mapPMMS = new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.historical.attribution.PositionManifestMeasureSnap>();

	private java.util.Map<java.lang.String, java.lang.String> _mapCustomC1 = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.String>();

	private java.util.Map<java.lang.String, java.lang.Double> _mapCustomR1 = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	private java.util.Map<java.lang.String, org.drip.analytics.date.JulianDate> _mapCustomDate = new
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.analytics.date.JulianDate>();

	/**
	 * PositionMarketSnap Constructor
	 * 
	 * @param dtSnap The Snapshot Date
	 * @param dblMarketValue The Snapshot Market Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PositionMarketSnap (
		final org.drip.analytics.date.JulianDate dtSnap,
		final double dblMarketValue)
		throws java.lang.Exception
	{
		if (null == (_dtSnap = dtSnap) || !org.drip.numerical.common.NumberUtil.IsValid (_dblMarketValue =
			dblMarketValue))
			throw new java.lang.Exception ("PositionMarketSnap Constructor: Invalid Inputs");
	}

	/**
	 * Retrieve the Date of the Snap
	 * 
	 * @return Date of the Snap
	 */

	public org.drip.analytics.date.JulianDate snapDate()
	{
		return _dtSnap;
	}

	/**
	 * Retrieve the Position Market Value
	 * 
	 * @return The Position Market Value
	 */

	public double marketValue()
	{
		return _dblMarketValue;
	}

	/**
	 * Add an Instance of the Position Manifest Measure Snap from the Specified Inputs
	 * 
	 * @param strManifestMeasure The Manifest Measure
	 * @param dblManifestMeasureRealization The Manifest Measure Realization
	 * @param dblManifestMeasureSensitivity The Manifest Measure Sensitivity
	 * @param dblManifestMeasureRollDown The Manifest Measure Roll Down
	 * 
	 * @return TRUE - The Manifest Measure Snap Metrics successfully added
	 */

	public boolean addManifestMeasureSnap (
		final java.lang.String strManifestMeasure,
		final double dblManifestMeasureRealization,
		final double dblManifestMeasureSensitivity,
		final double dblManifestMeasureRollDown)
	{
		if (null == strManifestMeasure || strManifestMeasure.isEmpty()) return false;

		try {
			_mapPMMS.put (strManifestMeasure, new org.drip.historical.attribution.PositionManifestMeasureSnap
				(dblManifestMeasureRealization, dblManifestMeasureSensitivity, dblManifestMeasureRollDown));

			return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Retrieve the Snapshot associated with the specified Manifest Measure
	 * 
	 * @param strManifestMeasure The Manifest Measure
	 * 
	 * @return The Snapshot associated with the specified Manifest Measure
	 */

	public org.drip.historical.attribution.PositionManifestMeasureSnap manifestMeasureSnap (
		final java.lang.String strManifestMeasure)
	{
		return null == strManifestMeasure || !_mapPMMS.containsKey (strManifestMeasure) ? null : _mapPMMS.get
			(strManifestMeasure);
	}

	/**
	 * Retrieve the Set of Manifest Measures
	 * 
	 * @return The Set of Manifest Measures
	 */

	public java.util.Set<java.lang.String> manifestMeasures()
	{
		return _mapPMMS.keySet();
	}

	/**
	 * Set the Custom Date Entry corresponding to the Specified Key
	 * 
	 * @param strKey The Key
	 * @param dtCustom The Custom Date Entry
	 * 
	 * @return TRUE - Custom Date successfully set
	 */

	public boolean setDate (
		final java.lang.String strKey,
		final org.drip.analytics.date.JulianDate dtCustom)
	{
		if (null == strKey || strKey.isEmpty() || null == dtCustom) return false;

		_mapCustomDate.put (strKey, dtCustom);

		return true;
	}

	/**
	 * Retrieve the Custom Date Entry corresponding to the Specified Key
	 * 
	 * @param strKey The Key
	 * 
	 * @return The Custom Date Entry
	 */

	public org.drip.analytics.date.JulianDate date (
		final java.lang.String strKey)
	{
		return null == strKey || !_mapCustomDate.containsKey (strKey) ? null : _mapCustomDate.get (strKey);
	}

	/**
	 * Set the Custom C^1 Entry corresponding to the Specified Key
	 * 
	 * @param strKey The Key
	 * @param strC1 The Custom C^1 Entry
	 * 
	 * @return TRUE - Custom C^1 Entry successfully set
	 */

	public boolean setC1 (
		final java.lang.String strKey,
		final java.lang.String strC1)
	{
		if (null == strKey || strKey.isEmpty() || null == strC1 || strC1.isEmpty()) return false;

		_mapCustomC1.put (strKey, strC1);

		return true;
	}

	/**
	 * Retrieve the Custom C^1 Entry corresponding to the Specified Key
	 * 
	 * @param strKey The Key
	 * 
	 * @return The Custom C^1 Entry
	 */

	public java.lang.String c1 (
		final java.lang.String strKey)
	{
		return null == strKey || !_mapCustomC1.containsKey (strKey) ? null : _mapCustomC1.get (strKey);
	}

	/**
	 * Set the Custom R^1 Entry corresponding to the Specified Key
	 * 
	 * @param strKey The Key
	 * @param dblR1 The Custom R^1 Entry
	 * @param bIgnoreNaN TRUE - Ignore NaN Entry
	 * 
	 * @return TRUE - Custom Number successfully set
	 */

	public boolean setR1 (
		final java.lang.String strKey,
		final double dblR1,
		final boolean bIgnoreNaN)
	{
		if (null == strKey || strKey.isEmpty() || (!bIgnoreNaN && !org.drip.numerical.common.NumberUtil.IsValid
			(dblR1)))
			return false;

		_mapCustomR1.put (strKey, dblR1);

		return true;
	}

	/**
	 * Set the Custom R^1 Entry corresponding to the Specified Key
	 * 
	 * @param strKey The Key
	 * @param dblR1 The Custom R^1 Entry
	 * 
	 * @return TRUE - Custom Number successfully set
	 */

	public boolean setR1 (
		final java.lang.String strKey,
		final double dblR1)
	{
		return setR1 (strKey, dblR1, true);
	}

	/**
	 * Retrieve the Custom R^1 Entry corresponding to the Specified Key
	 * 
	 * @param strKey The Key
	 * 
	 * @return The Custom R^1 Entry
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double r1 (
		final java.lang.String strKey)
		throws java.lang.Exception
	{
		if (null == strKey || !_mapCustomR1.containsKey (strKey))
			throw new java.lang.Exception ("PositionMarketSnap::r1 => Invalid Inputs");

		return _mapCustomR1.get (strKey);
	}

	/**
	 * Set the Market Measure Name
	 * 
	 * @param strMarketMeasureName The Market Measure Name
	 * 
	 * @return The Market Measure Name successfully set
	 */

	public boolean setMarketMeasureName (
		final java.lang.String strMarketMeasureName)
	{
		return setC1 ("MarketMeasureName", strMarketMeasureName);
	}

	/**
	 * Retrieve the Market Measure Name
	 * 
	 * @return The Market Measure Name
	 */

	public java.lang.String marketMeasureName()
	{
		return c1 ("MarketMeasureName");
	}

	/**
	 * Set the Market Measure Value
	 * 
	 * @param dblMarketMeasureValue The Market Measure Value
	 * 
	 * @return The Market Measure Value successfully set
	 */

	public boolean setMarketMeasureValue (
		final double dblMarketMeasureValue)
	{
		return setR1 ("MarketMeasureValue", dblMarketMeasureValue);
	}

	/**
	 * Retrieve the Market Measure Value
	 * 
	 * @return The Market Measure Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double marketMeasureValue()
		throws java.lang.Exception
	{
		return r1 ("MarketMeasureValue");
	}

	/**
	 * Set the Cumulative Coupon Amount
	 * 
	 * @param dblCumulativeCouponAmount The Cumulative Coupon Amount
	 * 
	 * @return TRUE - The Cumulative Coupon Amount successfully set
	 */

	public boolean setCumulativeCouponAmount (
		final double dblCumulativeCouponAmount)
	{
		return setR1 ("CumulativeCouponAmount", dblCumulativeCouponAmount);
	}

	/**
	 * Retrieve the Cumulative Coupon Amount
	 * 
	 * @return The Cumulative Coupon Amount
	 * 
	 * @throws java.lang.Exception Thrown if the Cumulative Coupon Amount cannot be obtained
	 */

	public double cumulativeCouponAmount()
		throws java.lang.Exception
	{
		return r1 ("CumulativeCouponAmount");
	}

	/**
	 * Retrieve the Row of Header Fields
	 * 
	 * @param strPrefix The Prefix that precedes each Header Field
	 * 
	 * @return The Row of Header Fields
	 */

	public java.lang.String header (
		final java.lang.String strPrefix)
	{
		java.lang.String strHeader = "";

		for (java.lang.String strR1Key : _mapCustomR1.keySet())
			strHeader = strHeader + strPrefix + strR1Key + ",";

		for (java.lang.String strC1Key : _mapCustomC1.keySet())
			strHeader = strHeader + strPrefix + strC1Key + ",";

		for (java.lang.String strDateKey : _mapCustomDate.keySet())
			strHeader = strHeader + strPrefix + strDateKey + ",";

		return strHeader;
	}

	/**
	 * Retrieve the Row of Content Fields
	 * 
	 * @return The Row of Content Fields
	 */

	public java.lang.String content()
	{
		java.lang.String strContent = "";

		for (java.lang.String strR1Key : _mapCustomR1.keySet())
			strContent = strContent + org.drip.numerical.common.FormatUtil.FormatDouble (_mapCustomR1.get
				(strR1Key), 1, 8, 1.) + ",";

		for (java.lang.String strC1Key : _mapCustomC1.keySet())
			strContent = strContent + _mapCustomC1.get (strC1Key) + ",";

		for (java.lang.String strDateKey : _mapCustomDate.keySet())
			strContent = strContent + _mapCustomDate.get (strDateKey).toString() + ",";

		return strContent;
	}
}
