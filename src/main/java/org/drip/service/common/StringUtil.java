
package org.drip.service.common;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>StringUtil</i> implements string utility functions. It exports the following functions:
 * 
 *  <ul>
 *  	<li>
 * 			Decompose + Transform string arrays into appropriate target type set/array/list, and vice versa
 *  	</li>
 *  	<li>
 * 			General-purpose String processor functions, such as GUID generator, splitter, type converter and
 * 				input checker
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/common">Assorted Data Structures Support Utilities</a></li>
 *  </ul>
 * <br><br>
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

	private static final int DecimalIntegerDigit (
		final char c)
	{
		if ('0' == c)
		{
			return 0;
		}

		if ('1' == c)
		{
			return 1;
		}

		if ('2' == c)
		{
			return 2;
		}

		if ('3' == c)
		{
			return 3;
		}

		if ('4' == c)
		{
			return 4;
		}

		if ('5' == c)
		{
			return 5;
		}

		if ('6' == c)
		{
			return 6;
		}

		if ('7' == c)
		{
			return 7;
		}

		if ('8' == c)
		{
			return 8;
		}

		if ('9' == c)
		{
			return 9;
		}

		return -1;
	}

	private static final int HexadecimalIntegerDigit (
		final char c)
	{
		if ('0' == c)
		{
			return 0;
		}

		if ('1' == c)
		{
			return 1;
		}

		if ('2' == c)
		{
			return 2;
		}

		if ('3' == c)
		{
			return 3;
		}

		if ('4' == c)
		{
			return 4;
		}

		if ('5' == c)
		{
			return 5;
		}

		if ('6' == c)
		{
			return 6;
		}

		if ('7' == c)
		{
			return 7;
		}

		if ('8' == c)
		{
			return 8;
		}

		if ('9' == c)
		{
			return 9;
		}

		if ('a' == c)
		{
			return 10;
		}

		if ('b' == c)
		{
			return 11;
		}

		if ('c' == c)
		{
			return 12;
		}

		if ('d' == c)
		{
			return 13;
		}

		if ('e' == c)
		{
			return 14;
		}

		if ('f' == c)
		{
			return 15;
		}

		return -1;
	}

	private static final int DecimalNumberFromString (
		final java.lang.String s)
	{
		char[] charArray = s.toCharArray();

		int size = charArray.length;
		int numberFromString = 0;

		for (int charIndex = 0;
			charIndex < size;
			++charIndex)
		{
			int decimal = DecimalIntegerDigit (
				charArray[charIndex]
			);

			if (-1 == decimal)
			{
				return -1;
			}

			numberFromString = 10 * numberFromString + decimal;
		}

		return numberFromString;
	}

	private static final int HexadecimalNumberFromString (
		final java.lang.String s)
	{
		char[] charArray = s.toCharArray();

		int size = charArray.length;
		int numberFromString = 0;

		for (int charIndex = 0;
			charIndex < size;
			++charIndex)
		{
			int hexadecimal = HexadecimalIntegerDigit (
				charArray[charIndex]
			);

			if (-1 == hexadecimal)
			{
				return -1;
			}

			numberFromString = 10 * numberFromString + hexadecimal;
		}

		return numberFromString;
	}

	private static final boolean ValidIPv4 (
		final java.lang.String address)
	{
		java.lang.String[] subnetAddress = Split (
			address.toLowerCase(),
			"."
		);

		if (null == subnetAddress || 4 != subnetAddress.length)
		{
			return false;
		}

		for (int i = 0;
			i < 3;
			++i)
		{
			int numberFromString = DecimalNumberFromString (
				subnetAddress[i]
			);

			if (0 > numberFromString || 256 <= numberFromString)
			{
				return false;
			}
		}

		return true;
	}

	private static final boolean ValidIPv6 (
		final java.lang.String address)
	{
		java.lang.String[] subnetAddress = Split (
			address.toLowerCase(),
			":"
		);

		if (null == subnetAddress || 8 != subnetAddress.length)
		{
			return false;
		}

		for (int i = 0;
			i < 8;
			++i)
		{
			int numberFromString = HexadecimalNumberFromString (
				subnetAddress[i]
			);

			if (0 > numberFromString || 65536 <= numberFromString)
			{
				return false;
			}
		}

		return true;
	}

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
	 * @param inputPhrase Input Phrase
	 * @param delimiter Delimiter
	 * 
	 * @return Array of Sub-Strings
	 */

	public static final java.lang.String[] Split (
		final java.lang.String inputPhrase,
		final java.lang.String delimiter)
	{
		if (null == inputPhrase || inputPhrase.isEmpty() ||
			null == delimiter || delimiter.isEmpty()
		)
		{
			return null;
		}

		if (-1 == inputPhrase.indexOf (
			delimiter,
			0
		))
		{
			return new java.lang.String[]
			{
				inputPhrase
			};
		}

		int delimiterIndex = -1;

		java.util.List<java.lang.Integer> delimiterIndexList = new java.util.ArrayList<java.lang.Integer>();

		while (-1 != (delimiterIndex = inputPhrase.indexOf (
			delimiter,
			delimiterIndex + 1
		)))
		{
			delimiterIndexList.add (
				delimiterIndex
			);
		}

		int fieldCount = delimiterIndexList.size();

		if (0 == fieldCount)
		{
			return null;
		}

		int beginIndex = 0;
		java.lang.String[] fieldArray = new java.lang.String[fieldCount + 1];

		for (int fieldIndex = 0;
			fieldIndex < fieldCount;
			++fieldIndex)
		{
			int endIndex = delimiterIndexList.get (fieldIndex);

			fieldArray[fieldIndex] = beginIndex >= endIndex ? "" : inputPhrase.substring (
				beginIndex,
				endIndex
			);

			beginIndex = delimiterIndexList.get (
				fieldIndex
			) + 1;
		}

		fieldArray[fieldCount] = inputPhrase.substring (
			beginIndex
		);

		return fieldArray;
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
			li.add (java.lang.Integer.parseInt (st.nextToken()));

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
			lsdbl.add (java.lang.Double.parseDouble (stdbl.nextToken()));

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

			lsdblKey.add (java.lang.Double.parseDouble (astrRecord[0]));

			lsdblValue.add (java.lang.Double.parseDouble (astrRecord[1]));
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

			lsi.add (java.lang.Integer.parseInt (astr[i]));
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

			lsb.add (java.lang.Boolean.parseBoolean (astr[i]));
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

	/**
	 * Implement atoi which converts a string to an integer.
	 * 
	 * The function first discards as many whitespace characters as necessary until the first non-whitespace
	 * 	character is found. Then, starting from this character, takes an optional initial plus or minus sign
	 * 	followed by as many numerical digits as possible, and interprets them as a numerical value.
	 * 
	 * The string can contain additional characters after those that form the integral number, which are
	 * 	ignored and have no effect on the behavior of this function.
	 * 
	 * If the first sequence of non-whitespace characters in s is not a valid integral number, or if no such
	 *  sequence exists because either s is empty or it contains only whitespace characters, no conversion is
	 *  performed.
	 * 
	 * If no valid conversion could be performed, a zero value is returned.
	 * 
	 * Note:
	 * 
	 * Only the space character ' ' is considered as whitespace character.
	 * 
	 * Assume we are dealing with an environment which could only store integers within the 32-bit signed
	 *  integer range: [-10^231, 10^231 − 1]. If the numerical value is out of the range of representable
	 *  values, INT_MAX (-10^231) or INT_MIN (10^231 - 1) is returned.
	 *  
	 * @param s Input String
	 * 
	 * @return The Integer
	 */

	public static final int AToI (
		final java.lang.String s)
	{
		char[] charArray = s.toCharArray();

		int stringLength = charArray.length;
		int valuePrev = 0;
		int value = 0;
		int index = 0;
		int sign = 1;

		java.util.List<java.lang.Integer> integerList = new java.util.ArrayList<java.lang.Integer>();

		while (index < stringLength &&
			' ' == charArray[index])
		{
			++index;
		}

		if (index < stringLength &&
			'-' == charArray[index])
		{
			sign = -1;
			++index;
		}

		while (index < stringLength)
		{
			int integerValue = DecimalIntegerDigit (
				charArray[index]
			);

			if (-1 == integerValue)
			{
				break;
			}

			integerList.add (
				integerValue
			);

			++index;
		}

		int listSize = integerList.size();

		for (int i = 0;
			i < listSize;
			++i)
		{
			value = 10 * value + integerList.get (
				i
			);

			if (value < valuePrev)
			{
				return java.lang.Integer.MIN_VALUE;
			}

			valuePrev = value;
		}

		return value * sign;
	}

	/**
	 * Given an input string, reverse the string word by word.
	 * 
	 * @param s Input String
	 * 
	 * @return String with Words Reversed
	 */

	public static final java.lang.String ReverseWords (
		final java.lang.String s)
	{
		java.lang.String[] wordArray = s.split (
			" "
		);

		boolean firstWord = true;
		java.lang.String reverseString = "";

		for (int wordIndex = wordArray.length - 1;
			wordIndex >= 0;
			--wordIndex)
		{
			java.lang.String gap = " ";

			if (firstWord)
			{
				gap = "";
			}

			if (null != wordArray[wordIndex] && !wordArray[wordIndex].isBlank())
			{
				reverseString = reverseString + gap + wordArray[wordIndex];
				firstWord = false;
			}
		}

		return reverseString;
	}

	/**
	 * Function to check whether an input string is a valid IPv4 address or IPv6 address or neither.
	 * 
	 * IPv4 addresses are canonically represented in dot-decimal notation, which consists of four decimal
	 * 	numbers, each ranging from 0 to 255, separated by dots ("."), e.g.,172.16.254.1;
	 * 
	 * Besides, leading zeros in the IPv4 is invalid. For example, the address 172.16.254.01 is invalid.
	 * 
	 * IPv6 addresses are represented as eight groups of four hexadecimal digits, each group representing 16
	 * 	bits. The groups are separated by colons (":"). For example, the address
	 *  2001:0db8:85a3:0000:0000:8a2e:0370:7334 is a valid one. Also, we could omit some leading zeros among
	 *  four hexadecimal digits and some low-case characters in the address to upper-case ones, so
	 *  2001:db8:85a3:0:0:8A2E:0370:7334 is also a valid IPv6 address(Omit leading zeros and using upper
	 *  cases).
	 *  
	 *  However, we don't replace a consecutive group of zero value with a single empty group using two
	 *   consecutive colons (::) to pursue simplicity. For example, 2001:0db8:85a3::8A2E:0370:7334 is an
	 *   invalid IPv6 address.
	 *   
	 *  Besides, extra leading zeros in the IPv6 is also invalid. For example, the address
	 *   02001:0db8:85a3:0000:0000:8a2e:0370:7334 is invalid.
	 *   
	 *  Note: You may assume there is no extra space or special characters in the input string.
	 * 
	 * @param s Input String
	 * 
	 * @return IP Address Type
	 */

	public static final java.lang.String ValidIPAddressType (
		final java.lang.String s)
	{
		if (ValidIPv4 (
			s
		))
		{
			return "IPv4";
		}

		return ValidIPv6 (
			s
		) ? "IPv6" : "Neither";
	}

	/**
	 * Compare two version numbers version1 and version2.
	 * 
	 * If version1 > version2 return 1; if version1 < version2 return -1;otherwise return 0.
	 * 
	 * You may assume that the version strings are non-empty and contain only digits and the . character.
	 * 
	 * The . character does not represent a decimal point and is used to separate number sequences.
	 * 
	 * For instance, 2.5 is not "two and a half" or "half way to version three", it is the fifth second-level
	 * 	revision of the second first-level revision.
	 * 
	 * You may assume the default revision number for each level of a version number to be 0. For example,
	 *  version number 3.4 has a revision number of 3 and 4 for its first and second level revision number.
	 *  Its third and fourth level revision number are both 0.
	 * 
	 * @param version1 First Version String
	 * @param version2 Second Version String
	 * 
	 * @return Results of the Comparison
	 */

	public static final int VersionCompare (
		final java.lang.String version1,
		final java.lang.String version2)
	{
		java.lang.String[] subVersion1 = Split (
			version1,
			"."
		);

		java.lang.String[] subVersion2 = Split (
			version2,
			"."
		);

		int subVersion1Index = 0;
		int subVersion2Index = 0;

		while (subVersion1Index < subVersion1.length &&
			subVersion2Index < subVersion2.length)
		{
			int subVersion1Number = DecimalNumberFromString (
				subVersion1[subVersion1Index]
			);

			int subVersion2Number = DecimalNumberFromString (
				subVersion2[subVersion1Index]
			);

			if (subVersion1Number > subVersion2Number)
			{
				return 1;
			}

			if (subVersion1Number < subVersion2Number)
			{
				return -1;
			}

			++subVersion1Index;
			++subVersion2Index;
		}

		if (subVersion1.length == subVersion2.length)
		{
			return 0;
		}

		if (subVersion1.length > subVersion2.length)
		{
			while (subVersion1Index < subVersion1.length)
			{
				int subVersion1Number = DecimalNumberFromString (
					subVersion1[subVersion1Index]
				);

				if (0 != subVersion1Number)
				{
					return 1;
				}

				++subVersion1Index;
			}
		}
		else
		{
			while (subVersion2Index < subVersion2.length)
			{
				int subVersion2Number = DecimalNumberFromString (
					subVersion2[subVersion1Index]
				);

				if (0 != subVersion2Number)
				{
					return -1;
				}

				++subVersion2Index;
			}
		}

		return 0;
	}

	public static final void main (
		String[] args)
	{
		System.out.println (
			VersionCompare (
				"0.1",
				"1.1"
			)
		);

		System.out.println (
			VersionCompare (
				"1.0.1",
				"1"
			)
		);

		System.out.println (
			VersionCompare (
				"7.5.2.4",
				"7.5.3"
			)
		);

		System.out.println (
			VersionCompare (
				"1.01",
				"1.001"
			)
		);

		System.out.println (
			VersionCompare (
				"1.0",
				"1.0.0"
			)
		);
	}
}
