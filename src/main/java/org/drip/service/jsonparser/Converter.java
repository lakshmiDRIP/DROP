
package org.drip.service.jsonparser;

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
 * <i>TypeConverter</i> transforms the JSON Object to certain Primitive/Simple Data Type Arrays, i.e.,
 * double, integer, String, or JulianDate Arrays.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/jsonparser">RFC4627 Compliant JSON Message Parser</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Converter {

	/**
	 * Convert the JSON Entry to a String
	 * 
	 * @param json The Object
	 * @param strEntryKey The Entry Key
	 * 
	 * @return The String Form of the JSON Entry
	 */

	public static final java.lang.String StringEntry (
		final org.drip.service.representation.JSONObject json,
		final java.lang.String strEntryKey)
	{
		if (null == json || !json.containsKey (strEntryKey)) return null;

		java.lang.Object objEntry = json.get (strEntryKey);

		return null == objEntry || !(objEntry instanceof java.lang.String) ? null : (java.lang.String)
			objEntry;
	}

	/**
	 * Convert the JSON Entry to a String Array
	 * 
	 * @param json The Object
	 * @param strEntryKey The Entry Key
	 * 
	 * @return The String Array From of the JSON Entry
	 */

	public static final java.lang.String[] StringArrayEntry (
		final org.drip.service.representation.JSONObject json,
		final java.lang.String strEntryKey)
	{
		if (null == json || !json.containsKey (strEntryKey)) return null;

		java.lang.Object objEntry = json.get (strEntryKey);

		if (null == objEntry || !(objEntry instanceof org.drip.service.representation.JSONArray)) return null;

		org.drip.service.representation.JSONArray jsonArray = (org.drip.service.representation.JSONArray) objEntry;

		int iNumElement = jsonArray.size();

		if (0 == iNumElement) return null;

		java.lang.String[] astr = new java.lang.String[iNumElement];

		for (int i = 0; i < iNumElement; ++i) {
			java.lang.Object objElement = jsonArray.get (i);

			astr[i] = null == objElement || !(objElement instanceof java.lang.String) ? null :
				(java.lang.String) objElement;
		}

		return astr;
	}

	/**
	 * Convert the JSON Entry to a Date
	 * 
	 * @param json The Object
	 * @param strEntryKey The Entry Key
	 * 
	 * @return The Date Form of the JSON Entry
	 */

	public static final org.drip.analytics.date.JulianDate DateEntry (
		final org.drip.service.representation.JSONObject json,
		final java.lang.String strEntryKey)
	{
		return org.drip.analytics.date.DateUtil.CreateFromDDMMMYYYY (StringEntry (json, strEntryKey));
	}

	/**
	 * Convert the JSON Entry to a Date Array
	 * 
	 * @param json The Object
	 * @param strEntryKey The Entry Key
	 * 
	 * @return The Date Array From of the JSON Entry
	 */

	public static final org.drip.analytics.date.JulianDate[] DateArrayEntry (
		final org.drip.service.representation.JSONObject json,
		final java.lang.String strEntryKey)
	{
		if (null == json || !json.containsKey (strEntryKey)) return null;

		java.lang.Object objEntry = json.get (strEntryKey);

		if (null == objEntry || !(objEntry instanceof org.drip.service.representation.JSONArray)) return null;

		org.drip.service.representation.JSONArray jsonArray = (org.drip.service.representation.JSONArray) objEntry;

		int iNumElement = jsonArray.size();

		if (0 == iNumElement) return null;

		org.drip.analytics.date.JulianDate[] adt = new org.drip.analytics.date.JulianDate[iNumElement];

		for (int i = 0; i < iNumElement; ++i) {
			java.lang.Object objElement = jsonArray.get (i);

			if (null == objElement || !(objElement instanceof java.lang.String) || null == (adt[i] =
				org.drip.analytics.date.DateUtil.CreateFromMDY ((java.lang.String) objElement, "/")))
				return null;
		}

		return adt;
	}

	/**
	 * Convert the JSON Entry to a Double
	 * 
	 * @param json The Object
	 * @param strEntryKey The Entry Key
	 * 
	 * @return The Double Form of the JSON Entry
     * 
     * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double DoubleEntry (
		final org.drip.service.representation.JSONObject json,
		final java.lang.String strEntryKey)
		throws java.lang.Exception
	{
		if (null == json || !json.containsKey (strEntryKey))
			throw new java.lang.Exception ("Converter::DoubleEntry => Invalid Inputs");

		java.lang.Object objEntry = json.get (strEntryKey);

		if (null == objEntry) throw new java.lang.Exception ("Converter::DoubleEntry => Invalid Inputs");

		return java.lang.Double.parseDouble (objEntry.toString().trim());
	}

	/**
	 * Convert the JSON Entry to a Double Array
	 * 
	 * @param json The Object
	 * @param strEntryKey The Entry Key
	 * 
	 * @return The Double Array From of the JSON Entry
	 */

	public static final double[] DoubleArrayEntry (
		final org.drip.service.representation.JSONObject json,
		final java.lang.String strEntryKey)
	{
		if (null == json || !json.containsKey (strEntryKey)) return null;

		java.lang.Object objEntry = json.get (strEntryKey);

		if (null == objEntry || !(objEntry instanceof org.drip.service.representation.JSONArray)) return null;

		org.drip.service.representation.JSONArray jsonArray = (org.drip.service.representation.JSONArray) objEntry;

		int iNumElement = jsonArray.size();

		if (0 == iNumElement) return null;

		double[] adbl = new double[iNumElement];

		for (int i = 0; i < iNumElement; ++i) {
			java.lang.Object objElement = jsonArray.get (i);

			if (null == objElement) return null;

			try {
				adbl[i] = java.lang.Double.parseDouble (objElement.toString().trim());
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adbl;
	}

	/**
	 * Convert the JSON Entry to a Dual Double Array
	 * 
	 * @param json The Object
	 * @param strEntryKey The Entry Key
	 * 
	 * @return The Dual Double Array From of the JSON Entry
	 */

	public static final double[][] DualDoubleArrayEntry (
		final org.drip.service.representation.JSONObject json,
		final java.lang.String strEntryKey)
	{
		if (null == json || !json.containsKey (strEntryKey)) return null;

		java.lang.Object objEntry = json.get (strEntryKey);

		if (null == objEntry || !(objEntry instanceof org.drip.service.representation.JSONArray)) return null;

		org.drip.service.representation.JSONArray jsonArray = (org.drip.service.representation.JSONArray) objEntry;

		int iNumOuterElement = jsonArray.size();

		if (0 == iNumOuterElement) return null;

		double[][] aadbl = new double[iNumOuterElement][];

		for (int i = 0; i < iNumOuterElement; ++i) {
			java.lang.Object objOuterElement = jsonArray.get (i);

			if (null == objOuterElement || !(objOuterElement instanceof org.drip.service.representation.JSONArray))
				return null;

			org.drip.service.representation.JSONArray jsonOuterArray = (org.drip.service.representation.JSONArray) objOuterElement;

			int iNumInnerElement = jsonOuterArray.size();

			if (0 == iNumInnerElement) return null;

			aadbl[i] = new double[iNumInnerElement];

			try {
				for (int j = 0; j < iNumInnerElement; ++j) {
					java.lang.Object objInnerElement = jsonOuterArray.get (j);

					if (null == objInnerElement) return null;

					aadbl[i][j] = java.lang.Double.parseDouble (objInnerElement.toString().trim());
				}
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return aadbl;
	}

	/**
	 * Convert the JSON Entry to an Integer
	 * 
	 * @param json The Object
	 * @param strEntryKey The Entry Key
	 * 
	 * @return The Integer Form of the JSON Entry
     * 
     * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final int IntegerEntry (
		final org.drip.service.representation.JSONObject json,
		final java.lang.String strEntryKey)
		throws java.lang.Exception
	{
		if (null == json || !json.containsKey (strEntryKey))
			throw new java.lang.Exception ("Converter::IntegerEntry => Invalid Inputs");

		java.lang.Object objEntry = json.get (strEntryKey);

		if (null == objEntry) throw new java.lang.Exception ("Converter::IntegerEntry => Invalid Inputs");

		return java.lang.Integer.parseInt (objEntry.toString().trim());
	}

	/**
	 * Convert the JSON Entry to an Integer Array
	 * 
	 * @param objJSON The JSON Object
	 * 
	 * @return The Integer Array From of the JSON Entry
	 */

	public static final int[] IntegerArrayEntry (
		final java.lang.Object objJSON)
	{
		if (null == objJSON || !(objJSON instanceof org.drip.service.representation.JSONArray)) return null;

		org.drip.service.representation.JSONArray jsonArray = (org.drip.service.representation.JSONArray) objJSON;

		int iNumElement = jsonArray.size();

		if (0 == iNumElement) return null;

		int[] ai = new int[iNumElement];

		for (int i = 0; i < iNumElement; ++i) {
			java.lang.Object json = jsonArray.get (i);

			if (null == json) return null;

			ai[i] = java.lang.Integer.parseInt (json.toString().trim());
		}

		return ai;
	}

	/**
	 * Convert the JSON Entry to an Boolean
	 * 
	 * @param json The Object
	 * @param strEntryKey The Entry Key
	 * 
	 * @return The Boolean Form of the JSON Entry
     * 
     * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final boolean BooleanEntry (
		final org.drip.service.representation.JSONObject json,
		final java.lang.String strEntryKey)
		throws java.lang.Exception
	{
		if (null == json || !json.containsKey (strEntryKey))
			throw new java.lang.Exception ("Converter::BooleanEntry => Invalid Inputs");

		java.lang.Object objEntry = json.get (strEntryKey);

		if (null == objEntry) throw new java.lang.Exception ("Converter::BooleanEntry => Invalid Inputs");

		return java.lang.Boolean.parseBoolean (objEntry.toString().trim());
	}

	/**
	 * Convert the JSON Entry to a Boolean Array
	 * 
	 * @param objJSON The Object
	 * 
	 * @return The Boolean Array From of the JSON Entry
	 */

	public static final boolean[] BooleanArrayEntry (
		final java.lang.Object objJSON)
	{
		if (null == objJSON || !(objJSON instanceof org.drip.service.representation.JSONArray)) return null;

		org.drip.service.representation.JSONArray jsonArray = (org.drip.service.representation.JSONArray) objJSON;

		int iNumElement = jsonArray.size();

		if (0 == iNumElement) return null;

		boolean[] ab = new boolean[iNumElement];

		for (int i = 0; i < iNumElement; ++i) {
			java.lang.Object json = jsonArray.get (i);

			if (null == json) return null;

			ab[i] = java.lang.Boolean.parseBoolean (json.toString().trim());
		}

		return ab;
	}

	/**
	 * Construct a JSON Array out of the String Array
	 * 
	 * @param astr The String Array
	 * 
	 * @return The JSON Array Instance
	 */

	@SuppressWarnings ("unchecked") public static final org.drip.service.representation.JSONArray Array (
		final java.lang.String[] astr)
	{
		if (null == astr || 0 == astr.length) return null;

		org.drip.service.representation.JSONArray jsonArray = new org.drip.service.representation.JSONArray();

		for (java.lang.String str : astr)
			jsonArray.add (str);

		return jsonArray;
	}

	/**
	 * Construct a JSON Array out of the Integer Array
	 * 
	 * @param ai The Integer Array
	 * 
	 * @return The JSON Array Instance
	 */

	@SuppressWarnings ("unchecked") public static final org.drip.service.representation.JSONArray Array (
		final int[] ai)
	{
		if (null == ai || 0 == ai.length) return null;

		org.drip.service.representation.JSONArray jsonArray = new org.drip.service.representation.JSONArray();

		for (int i : ai)
			jsonArray.add (i);

		return jsonArray;
	}

	/**
	 * Construct a JSON Array out of the Double Array
	 * 
	 * @param adbl The Double Array
	 * 
	 * @return The JSON Array Instance
	 */

	@SuppressWarnings ("unchecked") public static final org.drip.service.representation.JSONArray Array (
		final double[] adbl)
	{
		if (null == adbl || 0 == adbl.length) return null;

		org.drip.service.representation.JSONArray jsonArray = new org.drip.service.representation.JSONArray();

		for (double dbl : adbl)
			jsonArray.add (dbl);

		return jsonArray;
	}

	/**
	 * Construct a JSON 2D Array out of the 2D Double Array
	 * 
	 * @param aadbl The 2D Double Array
	 * 
	 * @return The JSON 2D Array Instance
	 */

	@SuppressWarnings ("unchecked") public static final org.drip.service.representation.JSONArray Array (
		final double[][] aadbl)
	{
		if (null == aadbl || 0 == aadbl.length) return null;

		org.drip.service.representation.JSONArray jsonArray = new org.drip.service.representation.JSONArray();

		for (double[] adbl : aadbl)
			jsonArray.add (Array (adbl));

		return jsonArray;
	}

	/**
	 * Construct a JSON Array out of the Boolean Array
	 * 
	 * @param ab The Boolean Array
	 * 
	 * @return The JSON Array Instance
	 */

	@SuppressWarnings ("unchecked") public static final org.drip.service.representation.JSONArray Array (
		final boolean[] ab)
	{
		if (null == ab || 0 == ab.length) return null;

		org.drip.service.representation.JSONArray jsonArray = new org.drip.service.representation.JSONArray();

		for (boolean b : ab)
			jsonArray.add (b);

		return jsonArray;
	}

	/**
	 * Construct a JSON Array out of the JulianDate Array
	 * 
	 * @param adt The JulianDate Array
	 * 
	 * @return The JSON Array Instance
	 */

	@SuppressWarnings ("unchecked") public static final org.drip.service.representation.JSONArray Array (
		final org.drip.analytics.date.JulianDate[] adt)
	{
		if (null == adt || 0 == adt.length) return null;

		org.drip.service.representation.JSONArray jsonArray = new org.drip.service.representation.JSONArray();

		for (org.drip.analytics.date.JulianDate dt : adt)
			jsonArray.add (dt);

		return jsonArray;
	}
}
