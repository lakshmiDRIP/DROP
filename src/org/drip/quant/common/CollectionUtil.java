
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
 * The CollectionUtil class implements generic utility functions used in DRIP modules. Some of the functions
 *  it exposes are:
 *  - Map Merging Functionality
 *  - Map Key Functionality - key-value flatteners, key prefixers
 *  - Decompose/transform List/Set/Array Contents
 *  - Multi-Dimensional Map Manipulator Routines
 *  - Construct n-derivatives array from Slope
 *  - Collate Wengerts to a bigger Wengert
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CollectionUtil {

	/**
	 * Prefix the keys in the input map, and return them in a new map
	 * 
	 * @param mapIn Input map
	 * @param strPrefix The prefix
	 * 
	 * @return Map containing the prefixed entries
	 */

	public static final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> PrefixKeys (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapIn,
		final java.lang.String strPrefix)
	{
		if (null == mapIn || null == mapIn.entrySet() || null == strPrefix || strPrefix.isEmpty())
			return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapOut = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> me : mapIn.entrySet()) {
			if (null != me.getKey() && !me.getKey().isEmpty())
				mapOut.put (strPrefix + me.getKey(), me.getValue());
		}

		return mapOut;
	}

	/**
	 * Merge two maps
	 * 
	 * @param map1 Map 1
	 * @param map2 Map 2
	 * 
	 * @return The merged map
	 */

	public static final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> MergeMaps (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> map1,
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> map2)
	{
		if (null == map1 && null == map2) return null;

		if (null == map1 && null != map2) return map2;

		if (null != map1 && null == map2) return map1;

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapOut = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> me : map1.entrySet())
			mapOut.put (me.getKey(), me.getValue());

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> me : map2.entrySet())
			mapOut.put (me.getKey(), me.getValue());

		return mapOut;
	}

	/**
	 * Merge the secondary map onto the main map
	 * 
	 * @param mapMain Main Map
	 * @param mapToAdd Secondary Map to Add
	 * 
	 * @return True - If successfully merged with main
	 */

	public static final boolean MergeWithMain (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapMain,
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapToAdd)
	{
		if (null == mapMain || null == mapMain.entrySet() || null == mapToAdd || null ==
			mapToAdd.entrySet())
			return false;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> me : mapToAdd.entrySet())
			mapMain.put (me.getKey(), me.getValue());

		return true;
	}

	/**
	 * Flatten an input 2D string/double map into a delimited string array
	 * 
	 * @param map2DSD 2D String/Double map
	 * @param strKVDelimiter Element delimiter
	 * @param strRecordDelimiter Record delimiter
	 * 
	 * @return Flattened map string
	 */

	public static final java.lang.String TwoDSDMapToFlatString (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> map2DSD,
		final java.lang.String strKVDelimiter,
		final java.lang.String strRecordDelimiter)
	{
		if (null == map2DSD || 0 == map2DSD.size() || null == map2DSD.entrySet() || null == strKVDelimiter ||
			strKVDelimiter.isEmpty() || null == strRecordDelimiter || strRecordDelimiter.isEmpty())
			return "";

		boolean bFirstEntry = true;

		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> me : map2DSD.entrySet()) {
			if (null == me || null == me.getKey() || me.getKey().isEmpty()) continue;

			if (bFirstEntry)
				bFirstEntry = false;
			else
				sb.append (strRecordDelimiter);

			sb.append (me.getKey() + strKVDelimiter + me.getValue());
		}

		return sb.toString();
	}

	/**
	 * Flatten a 3D SSD map structure onto a string array
	 * 
	 * @param map3DSD 3D SSD map
	 * @param strMultiLevelKeyDelimiter Multi Level KeyDelimiter
	 * @param strKVDelimiter Key-Value Delimiter
	 * @param strRecordDelimiter Record Delimiter
	 * 
	 * @return Flattened String
	 */

	public static final java.lang.String ThreeDSDMapToFlatString (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				map3DSD,
		final java.lang.String strMultiLevelKeyDelimiter,
		final java.lang.String strKVDelimiter,
		final java.lang.String strRecordDelimiter)
	{
		if (null == map3DSD || 0 == map3DSD.size() || null == map3DSD.entrySet() || null ==
			strMultiLevelKeyDelimiter || strMultiLevelKeyDelimiter.isEmpty() || null == strKVDelimiter ||
				strKVDelimiter.isEmpty() || null == strRecordDelimiter || strRecordDelimiter.isEmpty())
			return null;

		boolean bFirstEntry = true;

		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		for (java.util.Map.Entry<java.lang.String,
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>> meOut : map3DSD.entrySet()) {
			if (null == meOut || null == meOut.getValue() || null == meOut.getValue().entrySet()) continue;

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> meIn : meOut.getValue().entrySet())
			{
				if (null == meIn || null == meIn.getKey() || meIn.getKey().isEmpty()) continue;

				if (bFirstEntry)
					bFirstEntry = false;
				else
					sb.append (strRecordDelimiter);

				sb.append (meOut.getKey() + strMultiLevelKeyDelimiter + meIn.getKey() + strKVDelimiter +
					meIn.getValue());
			}
		}

		return sb.toString();
	}

	/**
	 * Flatten a 4D SSSD map structure onto a string array
	 * 
	 * @param map4DSD 4D SSSD map
	 * @param strMultiLevelKeyDelimiter Multi Level KeyDelimiter
	 * @param strKVDelimiter Key-Value Delimiter
	 * @param strRecordDelimiter Record Delimiter
	 * 
	 * @return Flattened String
	 */

	public static final java.lang.String FourDSDMapToFlatString (
		final
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
				map4DSD,
		final java.lang.String strMultiLevelKeyDelimiter,
		final java.lang.String strKVDelimiter,
		final java.lang.String strRecordDelimiter)
	{
		if (null == map4DSD || 0 == map4DSD.size() || null == map4DSD.entrySet() || null ==
			strMultiLevelKeyDelimiter || strMultiLevelKeyDelimiter.isEmpty() || null == strKVDelimiter ||
				strKVDelimiter.isEmpty() || null == strRecordDelimiter || strRecordDelimiter.isEmpty())
			return null;

		boolean bFirstEntry = true;

		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		for (java.util.Map.Entry<java.lang.String,org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			meOut : map4DSD.entrySet()) {
			if (null == meOut || null == meOut.getValue() || null == meOut.getValue().entrySet() || null ==
				meOut.getKey() || meOut.getKey().isEmpty())
				continue;

			for (java.util.Map.Entry<java.lang.String,
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>> meIn :
					meOut.getValue().entrySet()) {
				if (null == meIn || null == meIn.getValue() || null == meIn.getValue().entrySet() || null ==
					meIn.getKey() || meIn.getKey().isEmpty())
					continue;

				for (java.util.Map.Entry<java.lang.String, java.lang.Double> me : meIn.getValue().entrySet())
				{
					if (null == me || null == me.getKey() || me.getKey().isEmpty()) continue;

					if (bFirstEntry)
						bFirstEntry = false;
					else
						sb.append (strRecordDelimiter);

					sb.append (meOut.getKey() + strMultiLevelKeyDelimiter + meIn.getKey() +
						strMultiLevelKeyDelimiter + me.getKey() + strKVDelimiter + me.getValue());
				}
			}
		}

		return sb.toString();
	}

	/**
	 * Turn a flattened 2D (string, double) string sequence into its corresponding map
	 * 
	 * @param str2DMap Flattened 2D array input
	 * @param strKVDelimiter Key-Value delimiter string
	 * @param strRecordDelimiter Record delimiter string
	 * @param bSkipNullValue Indicates whether NULL Values are to be skipped
	 * @param strNULLString NULL string
	 * 
	 * @return [String, double] map
	 */

	public static final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>
		FlatStringTo2DSDMap (
			final java.lang.String str2DMap,
			final java.lang.String strKVDelimiter,
			final java.lang.String strRecordDelimiter,
			final boolean bSkipNullValue,
			final java.lang.String strNULLString)
	{
		if (null == str2DMap || str2DMap.isEmpty() || null == strNULLString || strNULLString.isEmpty() ||
			strNULLString.equalsIgnoreCase (str2DMap) || null == strKVDelimiter || strKVDelimiter.isEmpty()
				|| null == strRecordDelimiter || strRecordDelimiter.isEmpty())
			return null;

		java.lang.String[] astrRecord = org.drip.quant.common.StringUtil.Split (str2DMap,
			strRecordDelimiter);

		if (null == astrRecord || 0 == astrRecord.length) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> map2D = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		for (int i = 0; i < astrRecord.length; ++i) {
			if (null == astrRecord[i] || astrRecord[i].isEmpty() || strNULLString.equalsIgnoreCase
				(astrRecord[i]))
				continue;

			java.lang.String[] astrKVPair = org.drip.quant.common.StringUtil.Split (astrRecord[i],
				strKVDelimiter);
			
			if (null == astrKVPair || 2 != astrKVPair.length || null == astrKVPair[0] ||
				astrKVPair[0].isEmpty() || strNULLString.equalsIgnoreCase (astrKVPair[0]) || (bSkipNullValue
					&& (null == astrKVPair[1] || astrKVPair[1].isEmpty() || strNULLString.equalsIgnoreCase
						(astrKVPair[1]))))
				continue;

			map2D.put (astrKVPair[0], new java.lang.Double (astrKVPair[1]));
		}

		if (0 == map2D.size()) return null;

		return map2D;
	}

	/**
	 * Turn a flattened 3D (string, string, double) string sequence into its corresponding map
	 * 
	 * @param str3DMap Flattened 3D array input
	 * @param strMultiLevelKeyDelimiter Multi-level key delimiter string
	 * @param strKVDelimiter Key-Value delimiter string
	 * @param strRecordDelimiter Record delimiter string
	 * @param bSkipNullValue Indicates whether NULL Values are to be skipped
	 * @param strNULLString NULL string
	 * 
	 * @return [String, [String, double]] map
	 */

	public static final
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			FlatStringTo3DSDMap (
				final java.lang.String str3DMap,
				final java.lang.String strMultiLevelKeyDelimiter,
				final java.lang.String strKVDelimiter,
				final java.lang.String strRecordDelimiter,
				final boolean bSkipNullValue,
				final java.lang.String strNULLString)
	{
		if (null == str3DMap || str3DMap.isEmpty() || null == strNULLString || strNULLString.isEmpty() ||
			strNULLString.equalsIgnoreCase (str3DMap) || null == strKVDelimiter || strKVDelimiter.isEmpty()
				|| null == strRecordDelimiter || strRecordDelimiter.isEmpty())
			return null;

		java.lang.String[] astrRecord = org.drip.quant.common.StringUtil.Split (str3DMap, strRecordDelimiter);

		if (null == astrRecord || 0 == astrRecord.length) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
			map3D = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>();

		for (int i = 0; i < astrRecord.length; ++i) {
			if (null == astrRecord[i] || astrRecord[i].isEmpty()) continue;

			java.lang.String[] astrKVPair = org.drip.quant.common.StringUtil.Split (astrRecord[i], strKVDelimiter);
			
			if (null == astrKVPair || 2 != astrKVPair.length || null == astrKVPair[0] ||
				astrKVPair[0].isEmpty() || strNULLString.equalsIgnoreCase (astrKVPair[0]) || (bSkipNullValue
					&& (null == astrKVPair[1] || astrKVPair[1].isEmpty() || strNULLString.equalsIgnoreCase
						(astrKVPair[1]))))
				continue;

			java.lang.String[] astrKeySet = org.drip.quant.common.StringUtil.Split (astrKVPair[0],
				strMultiLevelKeyDelimiter);
			
			if (null == astrKeySet || 2 != astrKeySet.length || null == astrKeySet[0] ||
				astrKeySet[0].isEmpty() || strNULLString.equalsIgnoreCase (astrKeySet[0]) || null ==
					astrKeySet[1] || astrKeySet[1].isEmpty() || strNULLString.equalsIgnoreCase
						(astrKeySet[1]))
				continue;

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> map2D = map3D.get
				(astrKeySet[0]);

			if (null == map2D)
				map2D = new org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

			map2D.put (astrKeySet[1], new java.lang.Double (astrKVPair[1]));

			map3D.put (astrKeySet[0], map2D);
		}

		if (0 == map3D.size()) return null;

		return map3D;
	}

	/**
	 * Turn a flattened 4D (string, string, string, double) string sequence into its corresponding map
	 * 
	 * @param str4DMap Flattened 4D array input
	 * @param strMultiLevelKeyDelimiter Multi-level key delimiter string
	 * @param strKVDelimiter Key-Value delimiter string
	 * @param strRecordDelimiter Record delimiter string
	 * @param bSkipNullValue Indicates whether NULL Values are to be skipped
	 * @param strNULLString NULL string
	 * 
	 * @return [String, [String, [String, double]]] map
	 */

	public static final
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			FlatStringTo4DSDMap (
				final java.lang.String str4DMap,
				final java.lang.String strMultiLevelKeyDelimiter,
				final java.lang.String strKVDelimiter,
				final java.lang.String strRecordDelimiter,
				final boolean bSkipNullValue,
				final java.lang.String strNULLString)
	{
		if (null == str4DMap || str4DMap.isEmpty() || null == strNULLString || strNULLString.isEmpty() ||
			strNULLString.equalsIgnoreCase (str4DMap) || null == strKVDelimiter || strKVDelimiter.isEmpty()
				|| null == strRecordDelimiter || strRecordDelimiter.isEmpty())
			return null;

		java.lang.String[] astrRecord = org.drip.quant.common.StringUtil.Split (str4DMap, strRecordDelimiter);

		if (null == astrRecord || 0 == astrRecord.length) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>
			map4D = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>>();

		for (int i = 0; i < astrRecord.length; ++i) {
			if (null == astrRecord[i] || astrRecord[i].isEmpty() || strNULLString.equalsIgnoreCase
				(astrRecord[i]))
				continue;

			java.lang.String[] astrKVPairOut = org.drip.quant.common.StringUtil.Split (astrRecord[i],
				strKVDelimiter);
			
			if (null == astrKVPairOut || 2 != astrKVPairOut.length || null == astrKVPairOut[0] ||
				astrKVPairOut[0].isEmpty() || strNULLString.equalsIgnoreCase (astrKVPairOut[0]) ||
					(bSkipNullValue && (null == astrKVPairOut[1] || astrKVPairOut[1].isEmpty() ||
						strNULLString.equalsIgnoreCase (astrKVPairOut[1]))))
				continue;

			java.lang.String[] astrKeySet = org.drip.quant.common.StringUtil.Split (astrKVPairOut[0],
				strMultiLevelKeyDelimiter);
			
			if (null == astrKeySet || 3 != astrKeySet.length || null == astrKeySet[0] ||
				astrKeySet[0].isEmpty() || strNULLString.equalsIgnoreCase (astrKeySet[0]) || null ==
					astrKeySet[1] || astrKeySet[1].isEmpty() || strNULLString.equalsIgnoreCase
						(astrKeySet[1]) || null == astrKeySet[2] || astrKeySet[2].isEmpty() ||
							strNULLString.equalsIgnoreCase (astrKeySet[2]))
				continue;

			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>
				map3D = map4D.get (astrKeySet[0]);

			if (null == map3D)
				map3D = new
					org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>();

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> map2D = map3D.get
				(astrKeySet[1]);

			if (null == map2D)
				map2D = new org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

			map2D.put (astrKeySet[2], new java.lang.Double (astrKVPairOut[1]));

			map3D.put (astrKeySet[1], map2D);

			map4D.put (astrKeySet[0], map3D);
		}

		if (0 == map4D.size()) return null;

		return map4D;
	}

	/**
	 * Populate an array of derivatives using the input slope (and setting the other to zero)
	 * 
	 * @param iNumDerivs Number of Derivatives to be populated
	 * @param dblSlope Slope
	 * 
	 * @return Array of derivatives
	 */

	public static final double[] DerivArrayFromSlope (
		final int iNumDerivs,
		final double dblSlope)
	{
		if (0 >= iNumDerivs || !org.drip.quant.common.NumberUtil.IsValid (dblSlope)) return null;

		double[] adblDeriv = new double[iNumDerivs];

		for (int i = 0; i < iNumDerivs; ++i)
			adblDeriv[i] = (0 == i) ? dblSlope : 0.;

		return adblDeriv;
	}

	/**
	 * Append the Wengert Jacobians inside the list onto one single composite
	 * 
	 * @param lsWJ List of Wengert Jacobians
	 * 
	 * @return The Composite Wengert Jacobian
	 */

	public static final org.drip.quant.calculus.WengertJacobian AppendWengert (
		final java.util.List<org.drip.quant.calculus.WengertJacobian> lsWJ)
	{
		if (null == lsWJ || 0 == lsWJ.size()) return null;

		int iNumQuote = 0;
		int iQuoteCursor = 0;
		org.drip.quant.calculus.WengertJacobian wjCombined = null;

		for (org.drip.quant.calculus.WengertJacobian wj : lsWJ)
			if (null != wj) iNumQuote += wj.numParameters();

		try {
			wjCombined = new org.drip.quant.calculus.WengertJacobian (1, iNumQuote);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (org.drip.quant.calculus.WengertJacobian wj : lsWJ) {
			if (null == wj) continue;

			int iNumParams = wj.numParameters();

			for (int i = 0; i < iNumParams; ++i) {
				try {
					if (!wjCombined.accumulatePartialFirstDerivative (0, iQuoteCursor++, wj.firstDerivative
						(0, i)))
						return null;
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		return wjCombined;
	}
}
