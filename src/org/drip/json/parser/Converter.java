
package org.drip.json.parser;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * TypeConverter transforms the JSON Object to certain Primitive/Simple Data Type Arrays, i.e., double,
 *  integer, String, or JulianDate Arrays.
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
		final org.drip.json.simple.JSONObject json,
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
		final org.drip.json.simple.JSONObject json,
		final java.lang.String strEntryKey)
	{
		if (null == json || !json.containsKey (strEntryKey)) return null;

		java.lang.Object objEntry = json.get (strEntryKey);

		if (null == objEntry || !(objEntry instanceof org.drip.json.simple.JSONArray)) return null;

		org.drip.json.simple.JSONArray jsonArray = (org.drip.json.simple.JSONArray) objEntry;

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
		final org.drip.json.simple.JSONObject json,
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
		final org.drip.json.simple.JSONObject json,
		final java.lang.String strEntryKey)
	{
		if (null == json || !json.containsKey (strEntryKey)) return null;

		java.lang.Object objEntry = json.get (strEntryKey);

		if (null == objEntry || !(objEntry instanceof org.drip.json.simple.JSONArray)) return null;

		org.drip.json.simple.JSONArray jsonArray = (org.drip.json.simple.JSONArray) objEntry;

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
		final org.drip.json.simple.JSONObject json,
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
		final org.drip.json.simple.JSONObject json,
		final java.lang.String strEntryKey)
	{
		if (null == json || !json.containsKey (strEntryKey)) return null;

		java.lang.Object objEntry = json.get (strEntryKey);

		if (null == objEntry || !(objEntry instanceof org.drip.json.simple.JSONArray)) return null;

		org.drip.json.simple.JSONArray jsonArray = (org.drip.json.simple.JSONArray) objEntry;

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
		final org.drip.json.simple.JSONObject json,
		final java.lang.String strEntryKey)
	{
		if (null == json || !json.containsKey (strEntryKey)) return null;

		java.lang.Object objEntry = json.get (strEntryKey);

		if (null == objEntry || !(objEntry instanceof org.drip.json.simple.JSONArray)) return null;

		org.drip.json.simple.JSONArray jsonArray = (org.drip.json.simple.JSONArray) objEntry;

		int iNumOuterElement = jsonArray.size();

		if (0 == iNumOuterElement) return null;

		double[][] aadbl = new double[iNumOuterElement][];

		for (int i = 0; i < iNumOuterElement; ++i) {
			java.lang.Object objOuterElement = jsonArray.get (i);

			if (null == objOuterElement || !(objOuterElement instanceof org.drip.json.simple.JSONArray))
				return null;

			org.drip.json.simple.JSONArray jsonOuterArray = (org.drip.json.simple.JSONArray) objOuterElement;

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
		final org.drip.json.simple.JSONObject json,
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
		if (null == objJSON || !(objJSON instanceof org.drip.json.simple.JSONArray)) return null;

		org.drip.json.simple.JSONArray jsonArray = (org.drip.json.simple.JSONArray) objJSON;

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
		final org.drip.json.simple.JSONObject json,
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
		if (null == objJSON || !(objJSON instanceof org.drip.json.simple.JSONArray)) return null;

		org.drip.json.simple.JSONArray jsonArray = (org.drip.json.simple.JSONArray) objJSON;

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

	@SuppressWarnings ("unchecked") public static final org.drip.json.simple.JSONArray Array (
		final java.lang.String[] astr)
	{
		if (null == astr || 0 == astr.length) return null;

		org.drip.json.simple.JSONArray jsonArray = new org.drip.json.simple.JSONArray();

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

	@SuppressWarnings ("unchecked") public static final org.drip.json.simple.JSONArray Array (
		final int[] ai)
	{
		if (null == ai || 0 == ai.length) return null;

		org.drip.json.simple.JSONArray jsonArray = new org.drip.json.simple.JSONArray();

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

	@SuppressWarnings ("unchecked") public static final org.drip.json.simple.JSONArray Array (
		final double[] adbl)
	{
		if (null == adbl || 0 == adbl.length) return null;

		org.drip.json.simple.JSONArray jsonArray = new org.drip.json.simple.JSONArray();

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

	@SuppressWarnings ("unchecked") public static final org.drip.json.simple.JSONArray Array (
		final double[][] aadbl)
	{
		if (null == aadbl || 0 == aadbl.length) return null;

		org.drip.json.simple.JSONArray jsonArray = new org.drip.json.simple.JSONArray();

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

	@SuppressWarnings ("unchecked") public static final org.drip.json.simple.JSONArray Array (
		final boolean[] ab)
	{
		if (null == ab || 0 == ab.length) return null;

		org.drip.json.simple.JSONArray jsonArray = new org.drip.json.simple.JSONArray();

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

	@SuppressWarnings ("unchecked") public static final org.drip.json.simple.JSONArray Array (
		final org.drip.analytics.date.JulianDate[] adt)
	{
		if (null == adt || 0 == adt.length) return null;

		org.drip.json.simple.JSONArray jsonArray = new org.drip.json.simple.JSONArray();

		for (org.drip.analytics.date.JulianDate dt : adt)
			jsonArray.add (dt);

		return jsonArray;
	}
}
