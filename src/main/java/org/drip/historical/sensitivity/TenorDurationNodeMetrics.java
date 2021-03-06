
package org.drip.historical.sensitivity;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 * <i>TenorDurationNodeMetrics</i> holds the KRD Duration Nodes and associated Metrics.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/README.md">Historical State Processing Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/historical/sensitivity/README.md">Product Horizon Change Tenor Sensitivity</a></li>
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
