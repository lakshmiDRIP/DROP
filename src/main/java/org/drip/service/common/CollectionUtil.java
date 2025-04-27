
package org.drip.service.common;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.drip.analytics.support.CaseInsensitiveTreeMap;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 * <i>CollectionUtil</i> implements generic utility functions used in DROP modules. It provides the
 *	following Functions:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Map Merging Functionality
 *  	</li>
 *  	<li>
 *  		Map Key Functionality - key-value flatteners, key prefixers
 *  	</li>
 *  	<li>
 *  		Decompose/transform List/Set/Array Contents
 *  	</li>
 *  	<li>
 *  		Multi-Dimensional Map Manipulator Routines
 *  	</li>
 *  	<li>
 *  		Construct n-derivatives array from Slope
 *  	</li>
 *  	<li>
 *  		Collate Wengerts to a bigger Wengert
 *  	</li>
 *  </ul>
 * 
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/common/README.md">Assorted Data Structures Support Utilities</a></td></tr>
 *  </table>
 * <br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CollectionUtil
{

	/**
	 * Prefix the keys in the input map, and return them in a new map
	 * 
	 * @param inputMap Input map
	 * @param prefix The prefix
	 * 
	 * @return Map containing the prefixed entries
	 */

	public static final CaseInsensitiveTreeMap<Double> PrefixKeys (
		final CaseInsensitiveTreeMap<Double> inputMap,
		final String prefix)
	{
		if (null == inputMap || null == inputMap.entrySet() || null == prefix || prefix.isEmpty()) {
			return null;
		}

		CaseInsensitiveTreeMap<Double> outputMap = new CaseInsensitiveTreeMap<Double>();

		for (Map.Entry<String, Double> inputMapEntry : inputMap.entrySet()) {
			if (null != inputMapEntry.getKey() && !inputMapEntry.getKey().isEmpty()) {
				outputMap.put (prefix + inputMapEntry.getKey(), inputMapEntry.getValue());
			}
		}

		return outputMap;
	}

	/**
	 * Merge two maps
	 * 
	 * @param map1 Map 1
	 * @param map2 Map 2
	 * 
	 * @return The merged map
	 */

	public static final CaseInsensitiveTreeMap<Double> MergeMaps (
		final CaseInsensitiveTreeMap<Double> map1,
		final CaseInsensitiveTreeMap<Double> map2)
	{
		if (null == map1 && null == map2) return null;

		if (null == map1 && null != map2) return map2;

		if (null != map1 && null == map2) return map1;

		CaseInsensitiveTreeMap<Double> mapOut = new
			CaseInsensitiveTreeMap<Double>();

		for (Map.Entry<String, Double> me : map1.entrySet())
			mapOut.put (me.getKey(), me.getValue());

		for (Map.Entry<String, Double> me : map2.entrySet())
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
		final CaseInsensitiveTreeMap<Double> mapMain,
		final CaseInsensitiveTreeMap<Double> mapToAdd)
	{
		if (null == mapMain || null == mapMain.entrySet() || null == mapToAdd || null ==
			mapToAdd.entrySet())
			return false;

		for (Map.Entry<String, Double> me : mapToAdd.entrySet())
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

	public static final String TwoDSDMapToFlatString (
		final CaseInsensitiveTreeMap<Double> map2DSD,
		final String strKVDelimiter,
		final String strRecordDelimiter)
	{
		if (null == map2DSD || 0 == map2DSD.size() || null == map2DSD.entrySet() || null == strKVDelimiter ||
			strKVDelimiter.isEmpty() || null == strRecordDelimiter || strRecordDelimiter.isEmpty())
			return "";

		boolean bFirstEntry = true;

		StringBuffer sb = new StringBuffer();

		for (Map.Entry<String, Double> me : map2DSD.entrySet()) {
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

	public static final String ThreeDSDMapToFlatString (
		final
			CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>
				map3DSD,
		final String strMultiLevelKeyDelimiter,
		final String strKVDelimiter,
		final String strRecordDelimiter)
	{
		if (null == map3DSD || 0 == map3DSD.size() || null == map3DSD.entrySet() || null ==
			strMultiLevelKeyDelimiter || strMultiLevelKeyDelimiter.isEmpty() || null == strKVDelimiter ||
				strKVDelimiter.isEmpty() || null == strRecordDelimiter || strRecordDelimiter.isEmpty())
			return null;

		boolean bFirstEntry = true;

		StringBuffer sb = new StringBuffer();

		for (Map.Entry<String,
			CaseInsensitiveTreeMap<Double>> meOut : map3DSD.entrySet()) {
			if (null == meOut || null == meOut.getValue() || null == meOut.getValue().entrySet()) continue;

			for (Map.Entry<String, Double> meIn : meOut.getValue().entrySet())
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

	public static final String FourDSDMapToFlatString (
		final
			CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>>
				map4DSD,
		final String strMultiLevelKeyDelimiter,
		final String strKVDelimiter,
		final String strRecordDelimiter)
	{
		if (null == map4DSD || 0 == map4DSD.size() || null == map4DSD.entrySet() || null ==
			strMultiLevelKeyDelimiter || strMultiLevelKeyDelimiter.isEmpty() || null == strKVDelimiter ||
				strKVDelimiter.isEmpty() || null == strRecordDelimiter || strRecordDelimiter.isEmpty())
			return null;

		boolean bFirstEntry = true;

		StringBuffer sb = new StringBuffer();

		for (Map.Entry<String,CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>>
			meOut : map4DSD.entrySet()) {
			if (null == meOut || null == meOut.getValue() || null == meOut.getValue().entrySet() || null ==
				meOut.getKey() || meOut.getKey().isEmpty())
				continue;

			for (Map.Entry<String,
				CaseInsensitiveTreeMap<Double>> meIn :
					meOut.getValue().entrySet()) {
				if (null == meIn || null == meIn.getValue() || null == meIn.getValue().entrySet() || null ==
					meIn.getKey() || meIn.getKey().isEmpty())
					continue;

				for (Map.Entry<String, Double> me : meIn.getValue().entrySet())
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

	public static final CaseInsensitiveTreeMap<Double>
		FlatStringTo2DSDMap (
			final String str2DMap,
			final String strKVDelimiter,
			final String strRecordDelimiter,
			final boolean bSkipNullValue,
			final String strNULLString)
	{
		if (null == str2DMap || str2DMap.isEmpty() || null == strNULLString || strNULLString.isEmpty() ||
			strNULLString.equalsIgnoreCase (str2DMap) || null == strKVDelimiter || strKVDelimiter.isEmpty()
				|| null == strRecordDelimiter || strRecordDelimiter.isEmpty())
			return null;

		String[] astrRecord = org.drip.service.common.StringUtil.Split (str2DMap,
			strRecordDelimiter);

		if (null == astrRecord || 0 == astrRecord.length) return null;

		CaseInsensitiveTreeMap<Double> map2D = new
			CaseInsensitiveTreeMap<Double>();

		for (int i = 0; i < astrRecord.length; ++i) {
			if (null == astrRecord[i] || astrRecord[i].isEmpty() || strNULLString.equalsIgnoreCase
				(astrRecord[i]))
				continue;

			String[] astrKVPair = org.drip.service.common.StringUtil.Split (astrRecord[i],
				strKVDelimiter);
			
			if (null == astrKVPair || 2 != astrKVPair.length || null == astrKVPair[0] ||
				astrKVPair[0].isEmpty() || strNULLString.equalsIgnoreCase (astrKVPair[0]) || (bSkipNullValue
					&& (null == astrKVPair[1] || astrKVPair[1].isEmpty() || strNULLString.equalsIgnoreCase
						(astrKVPair[1]))))
				continue;

			map2D.put (astrKVPair[0], Double.parseDouble (astrKVPair[1]));
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
		CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>
			FlatStringTo3DSDMap (
				final String str3DMap,
				final String strMultiLevelKeyDelimiter,
				final String strKVDelimiter,
				final String strRecordDelimiter,
				final boolean bSkipNullValue,
				final String strNULLString)
	{
		if (null == str3DMap || str3DMap.isEmpty() || null == strNULLString || strNULLString.isEmpty() ||
			strNULLString.equalsIgnoreCase (str3DMap) || null == strKVDelimiter || strKVDelimiter.isEmpty()
				|| null == strRecordDelimiter || strRecordDelimiter.isEmpty())
			return null;

		String[] astrRecord = org.drip.service.common.StringUtil.Split (str3DMap, strRecordDelimiter);

		if (null == astrRecord || 0 == astrRecord.length) return null;

		CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>
			map3D = new
				CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>();

		for (int i = 0; i < astrRecord.length; ++i) {
			if (null == astrRecord[i] || astrRecord[i].isEmpty()) continue;

			String[] astrKVPair = org.drip.service.common.StringUtil.Split (astrRecord[i], strKVDelimiter);
			
			if (null == astrKVPair || 2 != astrKVPair.length || null == astrKVPair[0] ||
				astrKVPair[0].isEmpty() || strNULLString.equalsIgnoreCase (astrKVPair[0]) || (bSkipNullValue
					&& (null == astrKVPair[1] || astrKVPair[1].isEmpty() || strNULLString.equalsIgnoreCase
						(astrKVPair[1]))))
				continue;

			String[] astrKeySet = org.drip.service.common.StringUtil.Split (astrKVPair[0],
				strMultiLevelKeyDelimiter);
			
			if (null == astrKeySet || 2 != astrKeySet.length || null == astrKeySet[0] ||
				astrKeySet[0].isEmpty() || strNULLString.equalsIgnoreCase (astrKeySet[0]) || null ==
					astrKeySet[1] || astrKeySet[1].isEmpty() || strNULLString.equalsIgnoreCase
						(astrKeySet[1]))
				continue;

			CaseInsensitiveTreeMap<Double> map2D = map3D.get
				(astrKeySet[0]);

			if (null == map2D)
				map2D = new CaseInsensitiveTreeMap<Double>();

			map2D.put (astrKeySet[1], Double.parseDouble (astrKVPair[1]));

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
		CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>>
			FlatStringTo4DSDMap (
				final String str4DMap,
				final String strMultiLevelKeyDelimiter,
				final String strKVDelimiter,
				final String strRecordDelimiter,
				final boolean bSkipNullValue,
				final String strNULLString)
	{
		if (null == str4DMap || str4DMap.isEmpty() || null == strNULLString || strNULLString.isEmpty() ||
			strNULLString.equalsIgnoreCase (str4DMap) || null == strKVDelimiter || strKVDelimiter.isEmpty()
				|| null == strRecordDelimiter || strRecordDelimiter.isEmpty())
			return null;

		String[] astrRecord = org.drip.service.common.StringUtil.Split (str4DMap, strRecordDelimiter);

		if (null == astrRecord || 0 == astrRecord.length) return null;

		CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>>
			map4D = new
				CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>>();

		for (int i = 0; i < astrRecord.length; ++i) {
			if (null == astrRecord[i] || astrRecord[i].isEmpty() || strNULLString.equalsIgnoreCase
				(astrRecord[i]))
				continue;

			String[] astrKVPairOut = org.drip.service.common.StringUtil.Split (astrRecord[i],
				strKVDelimiter);
			
			if (null == astrKVPairOut || 2 != astrKVPairOut.length || null == astrKVPairOut[0] ||
				astrKVPairOut[0].isEmpty() || strNULLString.equalsIgnoreCase (astrKVPairOut[0]) ||
					(bSkipNullValue && (null == astrKVPairOut[1] || astrKVPairOut[1].isEmpty() ||
						strNULLString.equalsIgnoreCase (astrKVPairOut[1]))))
				continue;

			String[] astrKeySet = org.drip.service.common.StringUtil.Split (astrKVPairOut[0],
				strMultiLevelKeyDelimiter);
			
			if (null == astrKeySet || 3 != astrKeySet.length || null == astrKeySet[0] ||
				astrKeySet[0].isEmpty() || strNULLString.equalsIgnoreCase (astrKeySet[0]) || null ==
					astrKeySet[1] || astrKeySet[1].isEmpty() || strNULLString.equalsIgnoreCase
						(astrKeySet[1]) || null == astrKeySet[2] || astrKeySet[2].isEmpty() ||
							strNULLString.equalsIgnoreCase (astrKeySet[2]))
				continue;

			CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>
				map3D = map4D.get (astrKeySet[0]);

			if (null == map3D)
				map3D = new
					CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>();

			CaseInsensitiveTreeMap<Double> map2D = map3D.get
				(astrKeySet[1]);

			if (null == map2D)
				map2D = new CaseInsensitiveTreeMap<Double>();

			map2D.put (astrKeySet[2], Double.parseDouble (astrKVPairOut[1]));

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
		if (0 >= iNumDerivs || !org.drip.numerical.common.NumberUtil.IsValid (dblSlope)) return null;

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

	public static final org.drip.numerical.differentiation.WengertJacobian AppendWengert (
		final List<org.drip.numerical.differentiation.WengertJacobian> lsWJ)
	{
		if (null == lsWJ || 0 == lsWJ.size()) return null;

		int iNumQuote = 0;
		int iQuoteCursor = 0;
		org.drip.numerical.differentiation.WengertJacobian wjCombined = null;

		for (org.drip.numerical.differentiation.WengertJacobian wj : lsWJ)
			if (null != wj) iNumQuote += wj.numParameters();

		try {
			wjCombined = new org.drip.numerical.differentiation.WengertJacobian (1, iNumQuote);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		for (org.drip.numerical.differentiation.WengertJacobian wj : lsWJ) {
			if (null == wj) continue;

			int iNumParams = wj.numParameters();

			for (int i = 0; i < iNumParams; ++i) {
				try {
					if (!wjCombined.accumulatePartialFirstDerivative (0, iQuoteCursor++, wj.firstDerivative
						(0, i)))
						return null;
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		return wjCombined;
	}

	/**
	 * Given an integer array numberArray and two integers k and t, return true if there are <b>two distinct
	 *  indices</b> i and j in the array such that abs(numberArray[i] - numberArray[j]) .le. t and abs(i - j)
	 *  .le. k
	 * 
	 * @param numberArray Number Array
	 * @param k K
	 * @param t T
	 * 
	 * @return TRUE - Element matching the criteria above found
	 */

	public static final boolean ContainsNearbyAlmostDuplicate (
		final int[] numberArray,
		final int k,
		final int t)
	{
		if (0 == t) return true;

		TreeSet<Integer> slidingWindowSet = new TreeSet<Integer>();

		for (int i = 0; i < numberArray.length; ++i) {
			Integer floor = slidingWindowSet.floor (numberArray[i]);

			Integer ceiling = slidingWindowSet.ceiling (numberArray[i]);

			if (null != floor && numberArray[i] - floor <= t) return true;

			if (null != ceiling && ceiling - numberArray[i] <= t) return true;

			slidingWindowSet.add (numberArray[i]);

			if (k < slidingWindowSet.size()) slidingWindowSet.remove (numberArray[i - k]);
		}

		return false;
	}

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Argument Array
	 * 
	 * @throws Exception Thrown if an Exception is encountered
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		System.out.println (ContainsNearbyAlmostDuplicate (new int[] {1, 2, 3, 1}, 3, 0));

		System.out.println (ContainsNearbyAlmostDuplicate (new int[] {1, 0, 1, 1}, 1, 2));

		System.out.println (ContainsNearbyAlmostDuplicate (new int[] {1, 5, 9, 1, 5, 9}, 2, 3));
	}
}
