
package org.drip.service.jsonparser;

import org.drip.analytics.date.DateUtil;
import org.drip.analytics.date.JulianDate;
import org.drip.service.representation.JSONArray;
import org.drip.service.representation.JSONObject;

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
 * <i>TypeConverter</i> transforms the JSON Object to certain Primitive/Simple Data Type Arrays, i.e.,
 * 	double, integer, String, or JulianDate Arrays. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Convert the JSON Entry to a String</li>
 * 		<li>Convert the JSON Entry to a String Array</li>
 * 		<li>Convert the JSON Entry to a Date</li>
 * 		<li>Convert the JSON Entry to a Date Array</li>
 * 		<li>Convert the JSON Entry to a Double</li>
 * 		<li>Convert the JSON Entry to a Double Array</li>
 * 		<li>Convert the JSON Entry to a Dual Double Array</li>
 * 		<li>Convert the JSON Entry to an Integer</li>
 * 		<li>Convert the JSON Entry to an Integer Array</li>
 * 		<li>Convert the JSON Entry to an Boolean</li>
 * 		<li>Convert the JSON Entry to a Boolean Array</li>
 * 		<li>Construct a JSON Array out of the String Array</li>
 * 		<li>Construct a JSON Array out of the Integer Array</li>
 * 		<li>Construct a JSON Array out of the Double Array</li>
 * 		<li>Construct a JSON 2D Array out of the 2D Double Array</li>
 * 		<li>Construct a JSON Array out of the JulianDate Array</li>
 * 		<li>Construct a JSON Array out of the Boolean Array</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/jsonparser/README.md">RFC4627 Compliant JSON Message Parser</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Converter
{

	/**
	 * Convert the JSON Entry to a String
	 * 
	 * @param json The Object
	 * @param entryKey The Entry Key
	 * 
	 * @return The String Form of the JSON Entry
	 */

	public static final String StringEntry (
		final JSONObject json,
		final String entryKey)
	{
		if (null == json || !json.containsKey (entryKey)) return null;

		Object entry = json.get (entryKey);

		return null == entry || !(entry instanceof String) ? null : (String) entry;
	}

	/**
	 * Convert the JSON Entry to a String Array
	 * 
	 * @param json The Object
	 * @param entryKey The Entry Key
	 * 
	 * @return The String Array From of the JSON Entry
	 */

	public static final String[] StringArrayEntry (
		final JSONObject json,
		final String entryKey)
	{
		if (null == json || !json.containsKey (entryKey)) {
			return null;
		}

		Object entry = json.get (entryKey);

		if (null == entry || !(entry instanceof JSONArray)) {
			return null;
		}

		JSONArray jsonArray = (JSONArray) entry;

		int elementCount = jsonArray.size();

		if (0 == elementCount) {
			return null;
		}

		String[] stringArray = new String[elementCount];

		for (int elementIndex = 0; elementIndex < elementCount; ++elementIndex) {
			Object objElement = jsonArray.get (elementIndex);

			stringArray[elementIndex] =
				null == objElement || !(objElement instanceof String) ? null : (String) objElement;
		}

		return stringArray;
	}

	/**
	 * Convert the JSON Entry to a Date
	 * 
	 * @param json The Object
	 * @param entryKey The Entry Key
	 * 
	 * @return The Date Form of the JSON Entry
	 */

	public static final JulianDate DateEntry (
		final JSONObject json,
		final String entryKey)
	{
		return DateUtil.CreateFromDDMMMYYYY (StringEntry (json, entryKey));
	}

	/**
	 * Convert the JSON Entry to a Date Array
	 * 
	 * @param json The Object
	 * @param entryKey The Entry Key
	 * 
	 * @return The Date Array From of the JSON Entry
	 */

	public static final JulianDate[] DateArrayEntry (
		final JSONObject json,
		final String entryKey)
	{
		if (null == json || !json.containsKey (entryKey)) {
			return null;
		}

		Object entry = json.get (entryKey);

		if (null == entry || !(entry instanceof JSONArray)) {
			return null;
		}

		JSONArray jsonArray = (JSONArray) entry;

		int elementCount = jsonArray.size();

		if (0 == elementCount) {
			return null;
		}

		JulianDate[] dateArray = new JulianDate[elementCount];

		for (int element = 0; element < elementCount; ++element) {
			Object objElement = jsonArray.get (element);

			if (null == objElement || !(objElement instanceof String) ||
				null == (dateArray[element] = DateUtil.CreateFromMDY ((String) objElement, "/")))
			{
				return null;
			}
		}

		return dateArray;
	}

	/**
	 * Convert the JSON Entry to a Double
	 * 
	 * @param json The Object
	 * @param entryKey The Entry Key
	 * 
	 * @return The Double Form of the JSON Entry
     * 
     * @throws Exception Thrown if the Inputs are Invalid
	 */

	public static final double DoubleEntry (
		final JSONObject json,
		final String entryKey)
		throws Exception
	{
		if (null == json || !json.containsKey (entryKey)) {
			throw new Exception ("Converter::DoubleEntry => Invalid Inputs");
		}

		Object entry = json.get (entryKey);

		if (null == entry) {
			throw new Exception ("Converter::DoubleEntry => Invalid Inputs");
		}

		return Double.parseDouble (entry.toString().trim());
	}

	/**
	 * Convert the JSON Entry to a Double Array
	 * 
	 * @param json The Object
	 * @param entryKey The Entry Key
	 * 
	 * @return The Double Array From of the JSON Entry
	 */

	public static final double[] DoubleArrayEntry (
		final JSONObject json,
		final String entryKey)
	{
		if (null == json || !json.containsKey (entryKey)) {
			return null;
		}

		Object entry = json.get (entryKey);

		if (null == entry || !(entry instanceof JSONArray)) return null;

		JSONArray jsonArray = (JSONArray) entry;

		int elementCount = jsonArray.size();

		if (0 == elementCount) {
			return null;
		}

		double[] doubleArray = new double[elementCount];

		for (int element = 0; element < elementCount; ++element) {
			Object objElement = jsonArray.get (element);

			if (null == objElement) {
				return null;
			}

			try {
				doubleArray[element] = Double.parseDouble (objElement.toString().trim());
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return doubleArray;
	}

	/**
	 * Convert the JSON Entry to a Dual Double Array
	 * 
	 * @param json The Object
	 * @param entryKey The Entry Key
	 * 
	 * @return The Dual Double Array From of the JSON Entry
	 */

	public static final double[][] DualDoubleArrayEntry (
		final JSONObject json,
		final String entryKey)
	{
		if (null == json || !json.containsKey (entryKey)) {
			return null;
		}

		Object objEntry = json.get (entryKey);

		if (null == objEntry || !(objEntry instanceof JSONArray)) {
			return null;
		}

		JSONArray jsonArray = (JSONArray) objEntry;

		int outerElement = jsonArray.size();

		if (0 == outerElement) {
			return null;
		}

		double[][] elementGrid = new double[outerElement][];

		for (int rowIndex = 0; rowIndex < outerElement; ++rowIndex) {
			Object objOuterElement = jsonArray.get (rowIndex);

			if (null == objOuterElement || !(objOuterElement instanceof JSONArray)) {
				return null;
			}

			JSONArray jsonOuterArray = (JSONArray) objOuterElement;

			int innerElementCount = jsonOuterArray.size();

			if (0 == innerElementCount) {
				return null;
			}

			elementGrid[rowIndex] = new double[innerElementCount];

			try {
				for (int columnIndex = 0; columnIndex < innerElementCount; ++columnIndex) {
					Object objInnerElement = jsonOuterArray.get (columnIndex);

					if (null == objInnerElement) {
						return null;
					}

					elementGrid[rowIndex][columnIndex] =
						Double.parseDouble (objInnerElement.toString().trim());
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return elementGrid;
	}

	/**
	 * Convert the JSON Entry to an Integer
	 * 
	 * @param json The Object
	 * @param entryKey The Entry Key
	 * 
	 * @return The Integer Form of the JSON Entry
     * 
     * @throws Exception Thrown if the Inputs are Invalid
	 */

	public static final int IntegerEntry (
		final JSONObject json,
		final String entryKey)
		throws Exception
	{
		if (null == json || !json.containsKey (entryKey)) {
			throw new Exception ("Converter::IntegerEntry => Invalid Inputs");
		}

		Object objEntry = json.get (entryKey);

		if (null == objEntry) {
			throw new Exception ("Converter::IntegerEntry => Invalid Inputs");
		}

		return Integer.parseInt (objEntry.toString().trim());
	}

	/**
	 * Convert the JSON Entry to an Integer Array
	 * 
	 * @param json The JSON Object
	 * 
	 * @return The Integer Array From of the JSON Entry
	 */

	public static final int[] IntegerArrayEntry (
		final Object json)
	{
		if (null == json || !(json instanceof JSONArray)) {
			return null;
		}

		JSONArray jsonArray = (JSONArray) json;

		int elementCount = jsonArray.size();

		if (0 == elementCount) {
			return null;
		}

		int[] integerArray = new int[elementCount];

		for (int elementIndex = 0; elementIndex < elementCount; ++elementIndex) {
			Object jsonElement = jsonArray.get (elementIndex);

			if (null == jsonElement) {
				return null;
			}

			integerArray[elementIndex] = Integer.parseInt (jsonElement.toString().trim());
		}

		return integerArray;
	}

	/**
	 * Convert the JSON Entry to an Boolean
	 * 
	 * @param json The Object
	 * @param entryKey The Entry Key
	 * 
	 * @return The Boolean Form of the JSON Entry
     * 
     * @throws Exception Thrown if the Inputs are Invalid
	 */

	public static final boolean BooleanEntry (
		final JSONObject json,
		final String entryKey)
		throws Exception
	{
		if (null == json || !json.containsKey (entryKey)) {
			throw new Exception ("Converter::BooleanEntry => Invalid Inputs");
		}

		Object entry = json.get (entryKey);

		if (null == entry) {
			throw new Exception ("Converter::BooleanEntry => Invalid Inputs");
		}

		return Boolean.parseBoolean (entry.toString().trim());
	}

	/**
	 * Convert the JSON Entry to a Boolean Array
	 * 
	 * @param jsonObject The Object
	 * 
	 * @return The Boolean Array From of the JSON Entry
	 */

	public static final boolean[] BooleanArrayEntry (
		final Object jsonObject)
	{
		if (null == jsonObject || !(jsonObject instanceof JSONArray)) {
			return null;
		}

		JSONArray jsonArray = (JSONArray) jsonObject;

		int elementCount = jsonArray.size();

		if (0 == elementCount) {
			return null;
		}

		boolean[] booleanArray = new boolean[elementCount];

		for (int elementIndex = 0; elementIndex < elementCount; ++elementIndex) {
			Object json = jsonArray.get (elementIndex);

			if (null == json) {
				return null;
			}

			booleanArray[elementIndex] = Boolean.parseBoolean (json.toString().trim());
		}

		return booleanArray;
	}

	/**
	 * Construct a JSON Array out of the String Array
	 * 
	 * @param stringArray The String Array
	 * 
	 * @return The JSON Array Instance
	 */

	@SuppressWarnings ("unchecked") public static final JSONArray Array (
		final String[] stringArray)
	{
		if (null == stringArray || 0 == stringArray.length) {
			return null;
		}

		JSONArray jsonArray = new JSONArray();

		for (String s : stringArray) {
			jsonArray.add (s);
		}

		return jsonArray;
	}

	/**
	 * Construct a JSON Array out of the Integer Array
	 * 
	 * @param integerArray The Integer Array
	 * 
	 * @return The JSON Array Instance
	 */

	@SuppressWarnings ("unchecked") public static final JSONArray Array (
		final int[] integerArray)
	{
		if (null == integerArray || 0 == integerArray.length) {
			return null;
		}

		JSONArray jsonArray = new JSONArray();

		for (int i : integerArray) {
			jsonArray.add (i);
		}

		return jsonArray;
	}

	/**
	 * Construct a JSON Array out of the Double Array
	 * 
	 * @param doubleArray The Double Array
	 * 
	 * @return The JSON Array Instance
	 */

	@SuppressWarnings ("unchecked") public static final JSONArray Array (
		final double[] doubleArray)
	{
		if (null == doubleArray || 0 == doubleArray.length) {
			return null;
		}

		JSONArray jsonArray = new JSONArray();

		for (double d : doubleArray) {
			jsonArray.add (d);
		}

		return jsonArray;
	}

	/**
	 * Construct a JSON 2D Array out of the 2D Double Array
	 * 
	 * @param grid The 2D Double Array
	 * 
	 * @return The JSON 2D Array Instance
	 */

	@SuppressWarnings ("unchecked") public static final JSONArray Array (
		final double[][] grid)
	{
		if (null == grid || 0 == grid.length) {
			return null;
		}

		JSONArray jsonArray = new JSONArray();

		for (double[] row : grid) {
			jsonArray.add (Array (row));
		}

		return jsonArray;
	}

	/**
	 * Construct a JSON Array out of the JulianDate Array
	 * 
	 * @param booleanArray The Boolean Array
	 * 
	 * @return The JSON Array Instance
	 */

	@SuppressWarnings ("unchecked") public static final JSONArray Array (
		final boolean[] booleanArray)
	{
		if (null == booleanArray || 0 == booleanArray.length) {
			return null;
		}

		JSONArray jsonArray = new JSONArray();

		for (boolean b : booleanArray) {
			jsonArray.add (b);
		}

		return jsonArray;
	}

	/**
	 * Construct a JSON Array out of the JulianDate Array
	 * 
	 * @param dateArray The JulianDate Array
	 * 
	 * @return The JSON Array Instance
	 */

	@SuppressWarnings ("unchecked") public static final JSONArray Array (
		final JulianDate[] dateArray)
	{
		if (null == dateArray || 0 == dateArray.length) {
			return null;
		}

		JSONArray jsonArray = new JSONArray();

		for (JulianDate date : dateArray) {
			jsonArray.add (date);
		}

		return jsonArray;
	}
}
