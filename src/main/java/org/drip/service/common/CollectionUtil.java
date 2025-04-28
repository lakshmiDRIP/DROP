
package org.drip.service.common;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.differentiation.WengertJacobian;

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
 * <br>
 *  <ul>
 * 		<li>>Prefix the keys in the input map, and return them in a new map</li>
 * 		<li>>Merge two maps</li>
 * 		<li>>Merge the secondary map onto the main map</li>
 * 		<li>>Flatten an input 2D string/double map into a delimited string array</li>
 * 		<li>>Flatten a 3D SSD map structure onto a string array</li>
 * 		<li>>Flatten a 4D SSSD Multi-map structure onto a string array</li>
 * 		<li>>Turn a flattened 2D (string, double) string sequence into its corresponding map</li>
 * 		<li>>Turn a flattened 3D (string, string, double) string sequence into its corresponding map</li>
 * 		<li>>Turn a flattened 4D (string, string, string, double) string sequence into its corresponding map</li>
 * 		<li>>Populate an array of derivatives using the input slope (and setting the other to zero)</li>
 * 		<li>>Append the Wengert Jacobians inside the list onto one single composite</li>
 * 		<li>>Given an integer array numberArray and two integers <code>k</code> and <code>t</code>, return true if there are <b>two distinct indices</b> <code>i</code> and <code>j</code> in the array such that <code>abs(numberArray[i] - numberArray[j]) .le. t</code> and <code>abs(i - j) .le. k</code></li>
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
		if (null == map1 && null == map2) {
			return null;
		}

		if (null == map1 && null != map2) {
			return map2;
		}

		if (null != map1 && null == map2) {
			return map1;
		}

		CaseInsensitiveTreeMap<Double> outputMap = new CaseInsensitiveTreeMap<Double>();

		for (Map.Entry<String, Double> map1Entry : map1.entrySet()) {
			outputMap.put (map1Entry.getKey(), map1Entry.getValue());
		}

		for (Map.Entry<String, Double> map2Entry : map2.entrySet()) {
			outputMap.put (map2Entry.getKey(), map2Entry.getValue());
		}

		return outputMap;
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
		if (null == mapMain || null == mapMain.entrySet() || null == mapToAdd || null == mapToAdd.entrySet())
		{
			return false;
		}

		for (Map.Entry<String, Double> mapEntry : mapToAdd.entrySet()) {
			mapMain.put (mapEntry.getKey(), mapEntry.getValue());
		}

		return true;
	}

	/**
	 * Flatten an input 2D string/double map into a delimited string array
	 * 
	 * @param doubleValueMap 2D String/Double map
	 * @param keyValueDelimiter Element delimiter
	 * @param recordDelimiter Record delimiter
	 * 
	 * @return Flattened map string
	 */

	public static final String TwoDSDMapToFlatString (
		final CaseInsensitiveTreeMap<Double> doubleValueMap,
		final String keyValueDelimiter,
		final String recordDelimiter)
	{
		if (null == doubleValueMap || 0 == doubleValueMap.size() || null == doubleValueMap.entrySet() ||
			null == keyValueDelimiter || keyValueDelimiter.isEmpty() || null == recordDelimiter ||
			recordDelimiter.isEmpty())
		{
			return "";
		}

		boolean bFirstEntry = true;

		StringBuffer stringBuffer = new StringBuffer();

		for (Map.Entry<String, Double> doubleValueMapEntry : doubleValueMap.entrySet()) {
			if (null == doubleValueMapEntry ||
				null == doubleValueMapEntry.getKey() ||
				doubleValueMapEntry.getKey().isEmpty())
			{
				continue;
			}

			if (bFirstEntry) {
				bFirstEntry = false;
			} else {
				stringBuffer.append (recordDelimiter);
			}

			stringBuffer.append (
				doubleValueMapEntry.getKey() + keyValueDelimiter + doubleValueMapEntry.getValue()
			);
		}

		return stringBuffer.toString();
	}

	/**
	 * Flatten a 3D SSD map structure onto a string array
	 * 
	 * @param doubleValuedDoubleMap 3D SSD map
	 * @param multiLevelKeyDelimiter Multi Level KeyDelimiter
	 * @param keyValueDelimiter Key-Value Delimiter
	 * @param recordDelimiter Record Delimiter
	 * 
	 * @return Flattened String
	 */

	public static final String ThreeDSDMapToFlatString (
		final CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>> doubleValuedDoubleMap,
		final String multiLevelKeyDelimiter,
		final String keyValueDelimiter,
		final String recordDelimiter)
	{
		if (null == doubleValuedDoubleMap || 0 == doubleValuedDoubleMap.size() ||
			null == doubleValuedDoubleMap.entrySet() ||
			null == multiLevelKeyDelimiter || multiLevelKeyDelimiter.isEmpty() ||
			null == keyValueDelimiter || keyValueDelimiter.isEmpty() ||
			null == recordDelimiter || recordDelimiter.isEmpty())
		{
			return null;
		}

		boolean firstEntry = true;

		StringBuffer sb = new StringBuffer();

		for (Map.Entry<String, CaseInsensitiveTreeMap<Double>> outerMapEntry :
			doubleValuedDoubleMap.entrySet())
		{
			if (null == outerMapEntry) {
				continue;
			}

			CaseInsensitiveTreeMap<Double> innerMap = outerMapEntry.getValue();

			if (null == innerMap) {
				continue;
			}

			Set<Map.Entry<String, Double>> innerMapEntrySet = innerMap.entrySet();

			if (null == innerMapEntrySet) {
				continue;
			}

			for (Map.Entry<String, Double> innerMapEntry : innerMapEntrySet) {
				if (null == innerMapEntry) {
					continue;
				}

				String innerMapKey = innerMapEntry.getKey();

				Double innerMapValue = innerMapEntry.getValue();

				if (null == innerMapKey || innerMapKey.isEmpty() || null == innerMapValue) {
					continue;
				}

				if (firstEntry) {
					firstEntry = false;
				} else {
					sb.append (recordDelimiter);
				}

				sb.append (
					outerMapEntry.getKey() + multiLevelKeyDelimiter + innerMapKey + keyValueDelimiter +
						innerMapValue
				);
			}
		}

		return sb.toString();
	}

	/**
	 * Flatten a 4D SSSD Multi-map structure onto a string array
	 * 
	 * @param multiMap 4D SSSD map
	 * @param multiLevelKeyDelimiter Multi-Level Key Delimiter
	 * @param keyValueDelimiter Key-Value Delimiter
	 * @param recordDelimiter Record Delimiter
	 * 
	 * @return Flattened String
	 */

	public static final String FourDSDMapToFlatString (
		final CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>> multiMap,
		final String multiLevelKeyDelimiter,
		final String keyValueDelimiter,
		final String recordDelimiter)
	{
		if (null == multiMap || 0 == multiMap.size() ||
			null == multiLevelKeyDelimiter || multiLevelKeyDelimiter.isEmpty() ||
			null == keyValueDelimiter || keyValueDelimiter.isEmpty() ||
			null == recordDelimiter || recordDelimiter.isEmpty())
		{
			return null;
		}

		boolean firstEntry = true;

		StringBuffer stringBuffer = new StringBuffer();

		for (Map.Entry<String,CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>> outerMapEntry :
			multiMap.entrySet())
		{
			if (null == outerMapEntry) {
				continue;
			}

			String outerMapKey = outerMapEntry.getKey();

			if (null == outerMapKey || outerMapKey.isEmpty()) {
				continue;
			}

			CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>> middleMap = outerMapEntry.getValue();

			if (null == middleMap) {
				continue;
			}

			Set<Map.Entry<String, CaseInsensitiveTreeMap<Double>>> middleMapEntrySet = middleMap.entrySet();

			if (null == middleMapEntrySet) {
				continue;
			}

			for (Map.Entry<String, CaseInsensitiveTreeMap<Double>> middleMapEntry : middleMapEntrySet) {
				if (null == middleMapEntry) {
					continue;
				}

				String middleMapKey = middleMapEntry.getKey();

				if (null == middleMapKey || middleMapKey.isEmpty()) {
					continue;
				}

				CaseInsensitiveTreeMap<Double> innerMap = middleMapEntry.getValue();

				if (null == innerMap || null == innerMap.entrySet()) {
					continue;
				}

				Set<Map.Entry<String, Double>> innerMapEntrySet = innerMap.entrySet();

				if (null == innerMapEntrySet) {
					continue;
				}

				for (Map.Entry<String, Double> innerMapEntry : innerMapEntrySet) {
					if (null == innerMapEntry) {
						continue;
					}

					String innerKey = innerMapEntry.getKey();

					if (null == innerKey || innerKey.isEmpty()) {
						continue;
					}

					if (firstEntry) {
						firstEntry = false;
					} else {
						stringBuffer.append (recordDelimiter);
					}

					stringBuffer.append (
						outerMapKey + multiLevelKeyDelimiter + middleMapKey + multiLevelKeyDelimiter +
							innerKey + keyValueDelimiter + innerMapEntry.getValue()
					);
				}
			}
		}

		return stringBuffer.toString();
	}

	/**
	 * Turn a flattened 2D (string, double) string sequence into its corresponding map
	 * 
	 * @param twoDMap Flattened 2D array input
	 * @param keyValueDelimiter Key-Value delimiter string
	 * @param recordDelimiter Record delimiter string
	 * @param skipNULLValue Indicates whether NULL Values are to be skipped
	 * @param nullString NULL string
	 * 
	 * @return [String, double] map
	 */

	public static final CaseInsensitiveTreeMap<Double> FlatStringTo2DSDMap (
		final String twoDMap,
		final String keyValueDelimiter,
		final String recordDelimiter,
		final boolean skipNULLValue,
		final String nullString)
	{
		if (null == twoDMap || twoDMap.isEmpty() ||
			null == nullString || nullString.isEmpty() || nullString.equalsIgnoreCase (twoDMap) ||
			null == keyValueDelimiter || keyValueDelimiter.isEmpty() ||
			null == recordDelimiter || recordDelimiter.isEmpty())
		{
			return null;
		}

		String[] recordArray = StringUtil.Split (twoDMap, recordDelimiter);

		if (null == recordArray || 0 == recordArray.length) {
			return null;
		}

		CaseInsensitiveTreeMap<Double> twoDTreeMap = new CaseInsensitiveTreeMap<Double>();

		for (int i = 0; i < recordArray.length; ++i) {
			if (null == recordArray[i] ||
				recordArray[i].isEmpty() ||
				nullString.equalsIgnoreCase (recordArray[i]))
			{
				continue;
			}

			String[] keyValuePairArray = StringUtil.Split (recordArray[i], keyValueDelimiter);
			
			if (null == keyValuePairArray || 2 != keyValuePairArray.length ||
				null == keyValuePairArray[0] || keyValuePairArray[0].isEmpty() ||
				nullString.equalsIgnoreCase (keyValuePairArray[0]) || (
					skipNULLValue && (
						null == keyValuePairArray[1] || keyValuePairArray[1].isEmpty() ||
							nullString.equalsIgnoreCase (keyValuePairArray[1])
					)
				)
			)
			{
				continue;
			}

			twoDTreeMap.put (keyValuePairArray[0], Double.parseDouble (keyValuePairArray[1]));
		}

		return 0 == twoDTreeMap.size() ? null : twoDTreeMap;
	}

	/**
	 * Turn a flattened 3D (string, string, double) string sequence into its corresponding map
	 * 
	 * @param threeDMap Flattened 3D array input
	 * @param multiLevelKeyDelimiter Multi-level key delimiter string
	 * @param keyValueDelimiter Key-Value delimiter string
	 * @param recordDelimiter Record delimiter string
	 * @param skipNULLValue Indicates whether NULL Values are to be skipped
	 * @param nullString NULL string
	 * 
	 * @return [String, [String, double]] map
	 */

	public static final CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>> FlatStringTo3DSDMap (
		final String threeDMap,
		final String multiLevelKeyDelimiter,
		final String keyValueDelimiter,
		final String recordDelimiter,
		final boolean skipNULLValue,
		final String nullString)
	{
		if (null == threeDMap || threeDMap.isEmpty() || nullString.equalsIgnoreCase (threeDMap) ||
			null == nullString || nullString.isEmpty() ||
			null == keyValueDelimiter || keyValueDelimiter.isEmpty() ||
			null == recordDelimiter || recordDelimiter.isEmpty())
		{
			return null;
		}

		String[] recordArray = StringUtil.Split (threeDMap, recordDelimiter);

		if (null == recordArray || 0 == recordArray.length) {
			return null;
		}

		CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>> threeDTreeMap =
			new CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>();

		for (int i = 0; i < recordArray.length; ++i) {
			if (null == recordArray[i] || recordArray[i].isEmpty()) {
				continue;
			}

			String[] keyValuePairArray = StringUtil.Split (recordArray[i], keyValueDelimiter);
			
			if (null == keyValuePairArray || 2 != keyValuePairArray.length ||
				null == keyValuePairArray[0] || keyValuePairArray[0].isEmpty() ||
				nullString.equalsIgnoreCase (keyValuePairArray[0]) || (
					skipNULLValue && (
						null == keyValuePairArray[1] ||
						keyValuePairArray[1].isEmpty() ||
						nullString.equalsIgnoreCase (keyValuePairArray[1])
					)
				)
			)
			{
				continue;
			}

			String[] keySetArray = StringUtil.Split (keyValuePairArray[0], multiLevelKeyDelimiter);
			
			if (null == keySetArray || 2 != keySetArray.length ||
				null == keySetArray[0] || keySetArray[0].isEmpty() ||
					nullString.equalsIgnoreCase (keySetArray[0]) ||
				null == keySetArray[1] || keySetArray[1].isEmpty() ||
					nullString.equalsIgnoreCase (keySetArray[1]
				)
			)
			{
				continue;
			}

			CaseInsensitiveTreeMap<Double> twoDTreeMap = threeDTreeMap.get (keySetArray[0]);

			if (null == twoDTreeMap) {
				twoDTreeMap = new CaseInsensitiveTreeMap<Double>();
			}

			twoDTreeMap.put (keySetArray[1], Double.parseDouble (keyValuePairArray[1]));

			threeDTreeMap.put (keySetArray[0], twoDTreeMap);
		}

		return 0 == threeDTreeMap.size() ? null : threeDTreeMap;
	}

	/**
	 * Turn a flattened 4D (string, string, string, double) string sequence into its corresponding map
	 * 
	 * @param fourDMap Flattened 4D array input
	 * @param multiLevelKeyDelimiter Multi-level key delimiter string
	 * @param keyValueDelimiter Key-Value delimiter string
	 * @param recordDelimiter Record delimiter string
	 * @param skipNullValue Indicates whether NULL Values are to be skipped
	 * @param nullString NULL string
	 * 
	 * @return [String, [String, [String, double]]] map
	 */

	public static final CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>>
		FlatStringTo4DSDMap (
			final String fourDMap,
			final String multiLevelKeyDelimiter,
			final String keyValueDelimiter,
			final String recordDelimiter,
			final boolean skipNullValue,
			final String nullString)
	{
		if (null == fourDMap || fourDMap.isEmpty() || nullString.equalsIgnoreCase (fourDMap) ||
			null == nullString || nullString.isEmpty() ||
			null == keyValueDelimiter || keyValueDelimiter.isEmpty() ||
			null == recordDelimiter || recordDelimiter.isEmpty())
		{
			return null;
		}

		String[] recordArray = StringUtil.Split (fourDMap, recordDelimiter);

		if (null == recordArray || 0 == recordArray.length) {
			return null;
		}

		CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>> fourDTreeMap =
			new CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>>();

		for (int i = 0; i < recordArray.length; ++i) {
			if (null == recordArray[i] || recordArray[i].isEmpty() ||
				nullString.equalsIgnoreCase (recordArray[i]))
			{
				continue;
			}

			String[] outputKeyValuePairArray = StringUtil.Split (recordArray[i], keyValueDelimiter);
			
			if (null == outputKeyValuePairArray || 2 != outputKeyValuePairArray.length ||
				null == outputKeyValuePairArray[0] || outputKeyValuePairArray[0].isEmpty() ||
				nullString.equalsIgnoreCase (outputKeyValuePairArray[0]) || (
					skipNullValue && (
						null == outputKeyValuePairArray[1] ||
						outputKeyValuePairArray[1].isEmpty() ||
						nullString.equalsIgnoreCase (outputKeyValuePairArray[1])
					)
				)
			)
			{
				continue;
			}

			String[] keySetArray = StringUtil.Split (outputKeyValuePairArray[0], multiLevelKeyDelimiter);
			
			if (null == keySetArray || 3 != keySetArray.length ||
				null == keySetArray[0] || keySetArray[0].isEmpty() ||
					nullString.equalsIgnoreCase (keySetArray[0]) ||
				null == keySetArray[1] || keySetArray[1].isEmpty() ||
					nullString.equalsIgnoreCase (keySetArray[1]) ||
				null == keySetArray[2] || keySetArray[2].isEmpty() ||
					nullString.equalsIgnoreCase (keySetArray[2])
			)
			{
				continue;
			}

			CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>> threeDTreeMap =
				fourDTreeMap.get (keySetArray[0]);

			if (null == threeDTreeMap) {
				threeDTreeMap = new CaseInsensitiveTreeMap<CaseInsensitiveTreeMap<Double>>();
			}

			CaseInsensitiveTreeMap<Double> twoDTreeMap = threeDTreeMap.get (keySetArray[1]);

			if (null == twoDTreeMap) {
				twoDTreeMap = new CaseInsensitiveTreeMap<Double>();
			}

			twoDTreeMap.put (keySetArray[2], Double.parseDouble (outputKeyValuePairArray[1]));

			threeDTreeMap.put (keySetArray[1], twoDTreeMap);

			fourDTreeMap.put (keySetArray[0], threeDTreeMap);
		}

		return 0 == fourDTreeMap.size() ? null : fourDTreeMap;
	}

	/**
	 * Populate an array of derivatives using the input slope (and setting the other to zero)
	 * 
	 * @param derivativeCount Number of Derivatives to be populated
	 * @param slope Slope
	 * 
	 * @return Array of derivatives
	 */

	public static final double[] DerivArrayFromSlope (
		final int derivativeCount,
		final double slope)
	{
		if (0 >= derivativeCount || !NumberUtil.IsValid (slope)) {
			return null;
		}

		double[] derivativeArray = new double[derivativeCount];

		for (int i = 0; i < derivativeCount; ++i) {
			derivativeArray[i] = 0 == i ? slope : 0.;
		}

		return derivativeArray;
	}

	/**
	 * Append the Wengert Jacobians inside the list onto one single composite
	 * 
	 * @param wengertJacobianList List of Wengert Jacobians
	 * 
	 * @return The Composite Wengert Jacobian
	 */

	public static final WengertJacobian AppendWengert (
		final List<WengertJacobian> wengertJacobianList)
	{
		if (null == wengertJacobianList || 0 == wengertJacobianList.size()) {
			return null;
		}

		int quoteCount = 0;
		int quoteCursor = 0;
		WengertJacobian combinedWengertJacobian = null;

		for (WengertJacobian wengertJacobian : wengertJacobianList) {
			if (null != wengertJacobian) {
				quoteCount += wengertJacobian.numParameters();
			}
		}

		try {
			combinedWengertJacobian = new WengertJacobian (1, quoteCount);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		for (WengertJacobian wengertJacobian : wengertJacobianList) {
			if (null == wengertJacobian) {
				continue;
			}

			for (int i = 0; i < wengertJacobian.numParameters(); ++i) {
				try {
					if (!combinedWengertJacobian.accumulatePartialFirstDerivative (
						0,
						quoteCursor++,
						wengertJacobian.firstDerivative (0, i)
					))
					{
						return null;
					}
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		return combinedWengertJacobian;
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
		if (0 == t) {
			return true;
		}

		TreeSet<Integer> slidingWindowSet = new TreeSet<Integer>();

		for (int i = 0; i < numberArray.length; ++i) {
			Integer floor = slidingWindowSet.floor (numberArray[i]);

			Integer ceiling = slidingWindowSet.ceiling (numberArray[i]);

			if (null != floor && numberArray[i] - floor <= t) {
				return true;
			}

			if (null != ceiling && ceiling - numberArray[i] <= t) {
				return true;
			}

			slidingWindowSet.add (numberArray[i]);

			if (k < slidingWindowSet.size()) {
				slidingWindowSet.remove (numberArray[i - k]);
			}
		}

		return false;
	}
}
