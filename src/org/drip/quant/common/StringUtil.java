
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
 * StringUtil implements string utility functions. It exports the following functions:
 * 	- Decompose + Transform string arrays into appropriate target type set/array/list, and vice versa
 * 	- General-purpose String processor functions, such as GUID generator, splitter, type converter and input
 * 		checker
 * 
 * @author Lakshmi Krishnamurthy
 */

public class StringUtil {

	/**
	 * Null serialized string
	 */

	public static final java.lang.String NULL_SER_STRING = "<<null>>";

	/**
	 * Serialization Version - ALWAYS prepend this on all derived classes
	 */

	public static final double VERSION = 2.4;

	/**
	 * Look for a match of the field in the input array
	 * 
	 * @param strFieldToMatch Field To Match
	 * @param astrMatchSet Array of fields to compare with
	 * @param bCaseMatch TRUE - Match case
	 * 
	 * @return TRUE - Match found according to the criteria specified
	 */

	public static final boolean MatchInStringArray (
		final java.lang.String strFieldToMatch,
		final java.lang.String[] astrMatchSet,
		final boolean bCaseMatch)
	{
		if (null == strFieldToMatch || strFieldToMatch.isEmpty() || null == astrMatchSet || 0 ==
			astrMatchSet.length)
			return false;

		for (java.lang.String strMatchSetEntry : astrMatchSet) {
			if (null == strMatchSetEntry || strMatchSetEntry.isEmpty()) continue;

			if (strMatchSetEntry.equals (strFieldToMatch)) return true;

			if (!bCaseMatch && strMatchSetEntry.equalsIgnoreCase (strFieldToMatch)) return true;
		}

		return false;
	}

	/**
	 * Look for a match of the field in the field set to an entry in the input array
	 * 
	 * @param astrFieldToMatch Field Array To Match
	 * @param astrMatchSet Array of fields to compare with
	 * @param bCaseMatch TRUE - Match case
	 * 
	 * @return TRUE - Match found according to the criteria specified
	 */

	public static final boolean MatchInStringArray (
		final java.lang.String[] astrFieldToMatch,
		final java.lang.String[] astrMatchSet,
		final boolean bCaseMatch)
	{
		if (null == astrFieldToMatch || 0 == astrFieldToMatch.length || null == astrMatchSet || 0 ==
			astrMatchSet.length)
			return false;

		for (java.lang.String strFieldToMatch : astrFieldToMatch) {
			if (MatchInStringArray (strFieldToMatch, astrMatchSet, bCaseMatch)) return true;
		}

		return false;
	}

	/**
	 * Format the given string parameter into an argument
	 * 
	 * @param strArg String Argument
	 * 
	 * @return Parameter from the Argument
	 */

	public static final java.lang.String MakeStringArg (
		final java.lang.String strArg)
	{
		if (null == strArg) return "null";

		if (strArg.isEmpty()) return "\"\"";

		return "\"" + strArg.trim() + "\"";
	}

	/**
	 * Check the Input String to Check for NULL - and return it
	 * 
	 * @param strIn Input String
	 * @param bEmptyToNULL TRUE if Empty String needs to be converted to NULL
	 * 
	 * @return The Processed String
	 */

	public static final java.lang.String ProcessInputForNULL (
		final java.lang.String strIn,
		final boolean bEmptyToNULL)
	{
		if (null == strIn) return null;

		if (strIn.isEmpty()) return bEmptyToNULL ? null : "";

		if ("null".equalsIgnoreCase (strIn.trim())) return null;

		if (strIn.trim().toUpperCase().startsWith ("NO")) return null;

		return strIn;
	}

	/**
	 * Parse and Split the Input Phrase into a String Array using the specified Delimiter
	 * 
	 * @param strPhrase Input Phrase
	 * @param strDelim Delimiter
	 * 
	 * @return Array of Sub-Strings
	 */

	public static final java.lang.String[] Split (
		final java.lang.String strPhrase,
		final java.lang.String strDelim)
	{
		if (null == strPhrase || strPhrase.isEmpty() || null == strDelim || strDelim.isEmpty()) return null;

		java.util.List<java.lang.Integer> lsDelimIndex = new java.util.ArrayList<java.lang.Integer>();

		int iDelimIndex = -1;

		while (-1 != (iDelimIndex = strPhrase.indexOf (strDelim, iDelimIndex + 1)))
			lsDelimIndex.add (iDelimIndex);

		int iNumField = lsDelimIndex.size();

		if (0 == iNumField) return null;

		int iBeginIndex = 0;
		java.lang.String[] astr = new java.lang.String[iNumField + 1];

		for (int i = 0; i < iNumField; ++i) {
			int iFinishIndex = lsDelimIndex.get (i);

			astr[i] = iBeginIndex >= iFinishIndex ? "" : strPhrase.substring (iBeginIndex, iFinishIndex);

			iBeginIndex = lsDelimIndex.get (i) + 1;
		}

		astr[iNumField] = strPhrase.substring (iBeginIndex);

		return astr;
	}

	/**
	 * Check if the string represents an unitary boolean
	 * 
	 * @param strUnitaryBoolean String input
	 * 
	 * @return TRUE - Unitary Boolean
	 */

	public static final boolean ParseFromUnitaryString (
		final java.lang.String strUnitaryBoolean)
	{
		if (null == strUnitaryBoolean || strUnitaryBoolean.isEmpty() || !"1".equalsIgnoreCase
			(strUnitaryBoolean.trim()))
			return false;

		return true;
	}

	/**
	 * Make an array of Integers from a string tokenizer
	 * 
	 * @param st Tokenizer containing delimited doubles
	 *  
	 * @return Double array
	 */

	public static final int[] MakeIntegerArrayFromStringTokenizer (
		final java.util.StringTokenizer st)
	{
		if (null == st) return null;

		java.util.List<java.lang.Integer> li = new java.util.ArrayList<java.lang.Integer>();

		while (st.hasMoreTokens())
			li.add (new java.lang.Integer (st.nextToken()));

		if (0 == li.size()) return null;

		int[] ai = new int[li.size()];

		int i = 0;

		for (int iValue : li)
			ai[i++] = iValue;

		return ai;
	}

	/**
	 * Make an array of double from a string tokenizer
	 * 
	 * @param stdbl Tokenizer containing delimited doubles
	 *  
	 * @return Double array
	 */

	public static final double[] MakeDoubleArrayFromStringTokenizer (
		final java.util.StringTokenizer stdbl)
	{
		if (null == stdbl) return null;

		java.util.List<java.lang.Double> lsdbl = new java.util.ArrayList<java.lang.Double>();

		while (stdbl.hasMoreTokens())
			lsdbl.add (new java.lang.Double (stdbl.nextToken()));

		if (0 == lsdbl.size()) return null;

		double[] adbl = new double[lsdbl.size()];

		int i = 0;

		for (double dbl : lsdbl)
			adbl[i++] = dbl;

		return adbl;
	}

	/**
	 * Generate a GUID string
	 * 
	 * @return String representing the GUID
	 */

	public static final java.lang.String GUID()
	{
	    return java.util.UUID.randomUUID().toString();
	}

	/**
	 * Split the string array into pairs of key-value doubles and returns them
	 * 
	 * @param lsdblKey [out] List of Keys
	 * @param lsdblValue [out] List of Values
	 * @param strArray [in] String containing KV records
	 * @param strRecordDelim [in] Record Delimiter
	 * @param strKVDelim [in] Key-Value Delimiter
	 * 
	 * @return True if parsing is successful
	 */

	public static final boolean KeyValueListFromStringArray (
		final java.util.List<java.lang.Double> lsdblKey,
		final java.util.List<java.lang.Double> lsdblValue,
		final java.lang.String strArray,
		final java.lang.String strRecordDelim,
		final java.lang.String strKVDelim)
	{
		if (null == strArray || strArray.isEmpty() || null == strRecordDelim || strRecordDelim.isEmpty() ||
			null == strKVDelim || strKVDelim.isEmpty() || null == lsdblKey || null == lsdblValue)
			return false;

		java.lang.String[] astr = Split (strArray, strRecordDelim);

		if (null == astr || 0 == astr.length) return false;

		for (int i = 0; i < astr.length; ++i) {
			if (null == astr[i] || astr[i].isEmpty()) return false;

			java.lang.String[] astrRecord = Split (astr[i], strKVDelim);

			if (null == astrRecord || 2 != astrRecord.length || null == astrRecord[0] ||
				astrRecord[0].isEmpty() || null == astrRecord[1] || astrRecord[1].isEmpty())
				return false;

			lsdblKey.add (new java.lang.Double (astrRecord[0]).doubleValue());

			lsdblValue.add (new java.lang.Double (astrRecord[1]).doubleValue());
		}

		return true;
	}

	/**
	 * Create a list of integers from a delimited string
	 * 
	 * @param lsi [Output] List of Integers
	 * @param strList Delimited String input
	 * @param strDelim Delimiter
	 * 
	 * @return True if successful
	 */

	public static final boolean IntegerListFromString (
		final java.util.List<java.lang.Integer> lsi,
		final java.lang.String strList,
		final java.lang.String strDelim)
	{
		if (null == lsi || null == strList || strList.isEmpty() || null == strDelim || strDelim.isEmpty())
			return false;

		java.lang.String[] astr = Split (strList, strDelim);

		if (null == astr || 0 == astr.length) return false;

		for (int i = 0; i < astr.length; ++i) {
			if (null == astr[i] || astr[i].isEmpty()) continue;

			lsi.add (new java.lang.Integer (astr[i]).intValue());
		}

		return true;
	}

	/**
	 * Create a list of booleans from a delimited string
	 * 
	 * @param lsb [Output] List of Booleans
	 * @param strList Delimited String input
	 * @param strDelim Delimiter
	 * 
	 * @return True if successful
	 */

	public static final boolean BooleanListFromString (
		final java.util.List<java.lang.Boolean> lsb,
		final java.lang.String strList,
		final java.lang.String strDelim)
	{
		if (null == lsb || null == strList || strList.isEmpty() || null == strDelim || strDelim.isEmpty())
			return false;

		java.lang.String[] astr = Split (strList, strDelim);

		if (null == astr || 0 == astr.length) return false;

		for (int i = 0; i < astr.length; ++i) {
			if (null == astr[i] || astr[i].isEmpty()) continue;

			lsb.add (new java.lang.Boolean (astr[i]).booleanValue());
		}

		return true;
	}

	/**
	 * Convert the String Array to a Record Delimited String
	 * 
	 * @param astr Input String Array
	 * @param strRecordDelimiter The String Record Delimiter
	 * @param strNULL NULL String Indicator
	 * 
	 * @return The Record Delimited String Array
	 */

	public static final java.lang.String StringArrayToString (
		final java.lang.String[] astr,
		final java.lang.String strRecordDelimiter,
		final java.lang.String strNULL)
	{
		if (null == astr || null == strRecordDelimiter || strRecordDelimiter.isEmpty() || null == strNULL ||
			strNULL.isEmpty())
			return null;

		int iNumStr = astr.length;

		if (0 == iNumStr) return null;

		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		for (int i = 0; i < iNumStr; ++i) {
			java.lang.String str = astr[i];

			if (0 != i) sb.append (strRecordDelimiter);

			sb.append (null == str || str.isEmpty() ? strNULL : str);
		}

		return sb.toString();
	}

	/**
	 * Indicate if the Input String is Empty
	 * 
	 * @param str The Input String
	 * 
	 * @return TRUE - The Input String is Empty
	 */

	public static final boolean IsEmpty (
		final java.lang.String str)
	{
		return null == str || str.isEmpty();
	}

	/**
	 * Indicate it the pair of Strings Match each other in Value
	 * 
	 * @param strLeft The Left String
	 * @param strRight The Right String
	 * 
	 * @return TRUE - The Strings Match
	 */

	public static final boolean StringMatch (
		final java.lang.String strLeft,
		final java.lang.String strRight)
	{
		boolean bIsLeftEmpty = IsEmpty (strLeft);

		boolean bIsRightEmpty = IsEmpty (strRight);

		if (bIsLeftEmpty && bIsRightEmpty) return true;

		if ((bIsLeftEmpty && !bIsRightEmpty) || (!bIsLeftEmpty && bIsRightEmpty)) return false;

		return strLeft.equalsIgnoreCase (strRight);
	}
}
