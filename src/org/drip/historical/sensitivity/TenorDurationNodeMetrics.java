
package org.drip.historical.sensitivity;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * TenorDurationNodeMetrics holds the KRD Duration Nodes and associated Metrics.
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
		if (null == strKRDNode || strKRDNode.isEmpty() || !org.drip.quant.common.NumberUtil.IsValid
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
		if (null == strKey || strKey.isEmpty() || !org.drip.quant.common.NumberUtil.IsValid (dblR1))
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
