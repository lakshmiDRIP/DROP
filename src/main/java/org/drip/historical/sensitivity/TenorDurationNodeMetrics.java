
package org.drip.historical.sensitivity;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>TenorDurationNodeMetrics</i> holds the KRD Duration Nodes and associated Metrics.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AlgorithmSupportLibrary.md">Algorithm Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/README.md">Historical</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/sensitivity/README.md">Sensitivity</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TenorDurationNodeMetrics {
	private org.drip.analytics.date.JulianDate _dtSnap = null;

	private java.util.Map<java.lang.String, java.lang.String> _mapCustomC1 = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.String>();

	private java.util.Map<java.lang.String, java.lang.Double> _mapCustomR1 = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> _mapKRD = new
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

	private java.util.Map<java.lang.String, org.drip.analytics.date.JulianDate> _mapCustomDate = new
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.analytics.date.JulianDate>();

	/**
	 * TenorDurationNodeMetrics Constructor
	 * 
	 * @param dtSnap The Date Snap
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TenorDurationNodeMetrics (
		final org.drip.analytics.date.JulianDate dtSnap)
		throws java.lang.Exception
	{
		if (null == (_dtSnap = dtSnap))
			throw new java.lang.Exception ("TenorDurationNodeMetrics ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the KRD Date Snap
	 * 
	 * @return The KRD Date Snap
	 */

	public org.drip.analytics.date.JulianDate dateSnap()
	{
		return _dtSnap;
	}

	/**
	 * Insert a KRD Node
	 * 
	 * @param strKRDNode KRD Node Key
	 * @param dblKRDValue KRD Node Value
	 * 
	 * @return TRUE - The KRD Entry successfully inserted
	 */

	public boolean addKRDNode (
		final java.lang.String strKRDNode,
		final double dblKRDValue)
	{
		if (null == strKRDNode || strKRDNode.isEmpty() || !org.drip.numerical.common.NumberUtil.IsValid
			(dblKRDValue))
			return false;

		_mapKRD.put (strKRDNode, dblKRDValue);

		return true;
	}

	/**
	 * Retrieve the KRD Map
	 * 
	 * @return The KRD Map
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> krdMap()
	{
		return _mapKRD;
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
	 * 
	 * @return TRUE - Custom Number successfully set
	 */

	public boolean setR1 (
		final java.lang.String strKey,
		final double dblR1)
	{
		if (null == strKey || strKey.isEmpty() || !org.drip.numerical.common.NumberUtil.IsValid (dblR1))
			return false;

		_mapCustomR1.put (strKey, dblR1);

		return true;
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
}
