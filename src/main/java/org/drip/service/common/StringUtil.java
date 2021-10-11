
package org.drip.service.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	public static final int DecimalNumberFromString (
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

	private static final int CharToDigit (
		final int c)
	{
		return (int) c - (int) '0';
	}

	private static final java.util.List<java.lang.Integer> StringToNumber (
		final java.lang.String s)
	{
		java.util.List<java.lang.Integer> numberList = new java.util.ArrayList<java.lang.Integer>();

		for (int i = 0;
			i < s.length();
			++i)
		{
			numberList.add (
				CharToDigit (
					s.charAt (
						i
					)
				)
			);
		}

		return numberList;
	}

	private static final boolean IsPalindrome (
		final java.util.List<java.lang.Integer> digitList)
	{
		int listLength = digitList.size();

		for (int listIndex = 0;
			listIndex < listLength;
			++listIndex)
		{
			if (digitList.get (
					listIndex
				) != digitList.get (
					listLength - listIndex - 1
				)
			)
			{
				return false;
			}
		}

		return true;
	}

	private static final boolean IsPalindrome (
		final int i)
	{
		return IsPalindrome (
			IntegerToDigitList (
				i
			)
		);
	}

	private static final java.util.List<java.lang.Integer> SwapIntoPalindrome (
		final java.util.List<java.lang.Integer> digitList)
	{
		if (IsPalindrome (
			digitList
		))
		{
			return digitList;
		}

		int digitCount = digitList.size();

		int digitIndexLeft = 0;
		int digitIndexRight = digitCount - 1;

		while (digitIndexLeft < digitIndexRight)
		{
			int leftDigit = digitList.get (
				digitIndexLeft
			);

			if (leftDigit != digitList.get (
				digitIndexRight
			))
			{
				digitList.remove (
					digitIndexRight
				);

				digitList.add (
					digitIndexRight,
					leftDigit
				);
			}

			++digitIndexLeft;
			--digitIndexRight;
		}

		return digitList;
	}

	private static final java.util.List<java.lang.Integer> IntegerToDigitList (
		int i)
	{
		java.util.List<java.lang.Integer> integerToDigitList = new java.util.ArrayList<java.lang.Integer>();

		while (i != 0)
		{
			int digit = i % 10;

			integerToDigitList.add (
				0,
				digit
			);

			i = i / 10;
		}

		return integerToDigitList;
	}

	private static final int DigitListToInteger (
		final java.util.List<java.lang.Integer> integerToDigitList)
	{
		int integer = 0;

		for (int digit : integerToDigitList)
		{
			integer = 10 * integer + digit;
		}

		return integer;
	}

	private static final java.lang.String EvaluateSubSum (
		final int level,
		final java.lang.String s)
	{
		int subSum = 0;

		java.lang.String[] numberString = s.split (
			","
		);

		for (java.lang.String number : numberString)
		{
			subSum = subSum + DecimalNumberFromString (
				number
			);
		}

		return "" + (level * subSum);
	}

	private static final java.lang.String NestedArrayDepthSum (
		final java.lang.String expression,
		final int level)
	{
		char[] charArray = expression.toCharArray();

		int expressionLength = charArray.length;
		int rightBracketIndex = -1;
		int leftBracketIndex = -1;
		int index = 0;

		while (index < expressionLength)
		{
			if (charArray[index] == '[')
			{
				leftBracketIndex = index;
				break;
			}

			++index;
		}

		while (index < expressionLength)
		{
			if (charArray[index] == ']')
			{
				rightBracketIndex = index;
			}

			++index;
		}

		if (-1 == leftBracketIndex &&
			-1 == rightBracketIndex
		)
		{
			return EvaluateSubSum (
				level,
				expression
			);
		}

		java.lang.String leftSubstring = expression.substring (
			0,
			leftBracketIndex
		);

		java.lang.String rightSubstring = expression.substring (
			rightBracketIndex + 1,
			expressionLength
		);

		java.lang.String evaluation = NestedArrayDepthSum (
			expression.substring (
				leftBracketIndex + 1,
				rightBracketIndex
			),
			level + 1
		);

		return EvaluateSubSum (
			level,
			leftSubstring + evaluation + rightSubstring
		);
	}

	private static final int[] CenteredPalindome (
		final char[] charArray,
		final int mid)
	{
		int left = mid;
		int right = mid;
		int leftIndex = left;
		int rightIndex = right;

		while (leftIndex >= 0 && rightIndex < charArray.length && charArray[leftIndex] == charArray[rightIndex]) {
			left = leftIndex--;
			right = rightIndex++;
		}

		int[] range = new int[] {left, right};

		if (mid == charArray.length - 1) return range;

		left = mid;
		right = mid + 1;
		leftIndex = left;
		rightIndex = right;

		if (charArray[leftIndex] != charArray[rightIndex]) return range;

		while (leftIndex >= 0 && rightIndex < charArray.length && charArray[leftIndex] == charArray[rightIndex]) {
			left = leftIndex--;
			right = rightIndex++;
		}

		return right - left > range[1] - range[0] ? new int[] {left, right} : range;
	}

    private static final boolean RangesOverlap (
    	final int[] range1,
    	final int[] range2)
    {
    	return range2[0] > range1[0] && range2[0] < range1[1];
    }

    private static final boolean IsConsonant (
    	final char c)
    {
    	return 'b' == c || 'c' == c || 'd' == c || 'y' == c || 'f' == c || 'g' == c || 'h' == c || 'j' == c
    		|| 'k' == c || 'l' == c || 'm' == c || 'n' == c || 'p' == c || 'q' == c || 'r' == c || 's' == c
    		|| 't' == c || 'v' == c || 'w' == c || 'x' == c || 'z' == c;
    }

    private static final boolean DecrementCharCount (
    	final int[] charCountArray,
    	final char c)
    {
    	if (0 == charCountArray[(int) c]) return false;

    	--charCountArray[(int) c];
    	return true;
    }

    private static final char CharWithLargestCount (
    	final int[] charCountArray)
    {
    	int a = charCountArray[(int) 'a'];
    	int b = charCountArray[(int) 'b'];
    	int c = charCountArray[(int) 'c'];

    	if (a >= b && a >= c) return 'a';

    	return b >= c ? 'b' : 'c';
    }

    private static final char CharWithSmallestCount (
    	final int[] charCountArray)
    {
    	int a = charCountArray[(int) 'a'];
    	int b = charCountArray[(int) 'b'];
    	int c = charCountArray[(int) 'c'];

    	if (a <= b && a <= c) return 'a';

    	return b <= c ? 'b' : 'c';
    }

    private static final char CharWithMediumCount (
    	final int[] charCountArray)
    {
    	char charWithLargestCount = CharWithLargestCount (charCountArray);

    	char charWithSmallestCount = CharWithSmallestCount (charCountArray);

    	if ('a' != charWithLargestCount && 'a' != charWithSmallestCount) return 'a';

    	if ('b' != charWithLargestCount && 'b' != charWithSmallestCount) return 'b';

    	return 'c';
    }

    private static final boolean SingleCharacterArray (
    	final int[] charCountArray)
    {
    	int zeroCount = 0;

    	if (0 == charCountArray[(int) 'a']) ++zeroCount;

    	if (0 == charCountArray[(int) 'b']) ++zeroCount;

    	if (0 == charCountArray[(int) 'c']) ++zeroCount;

    	return 2 <= zeroCount;
    }

    private static final boolean EmptyCharCountArray (
    	final int[] charCountArray)
    {
    	return 0 == charCountArray[(int) 'a'] && 0 == charCountArray[(int) 'b'] &&
    		0 == charCountArray[(int) 'c'];
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
	 * If version1 gt version2 return 1; if version1 lt version2 return -1;otherwise return 0.
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

	/**
	 * Given a string, find the longest palindromic substring.
	 * 
	 * @param s Input String
	 * 
	 * @return The Longest Palindromic substring
	 */

	public static final java.lang.String LongestPalindromicSubstring (
		final java.lang.String s)
	{
		char[] charArray = s.toCharArray();

		int[] maxRange = CenteredPalindome (charArray, 0);

		for (int i = 1; i < charArray.length; ++i) {
			int[] range = CenteredPalindome (charArray, i);

			if (maxRange[1] - maxRange[0] < range[1] - range[0]) maxRange = range;
		}

		return s.substring (maxRange[0], maxRange[1] + 1);
	}

	/**
	 * Find the length of the <b>longest substring</b> without repeating characters.
	 * 
	 * @param s Input String
	 * 
	 * @return Length of the <b>longest substring</b> without repeating characters.
	 */

	public static final int LengthOfLongestNonRepeatingSubstring (
		final java.lang.String s)
	{
		char[] charArray = s.toCharArray();

		int index = 0;
		int beginIndex = 0;
		int lengthOfLongestNonRepeatingSubstring = 0;

		java.util.Map<java.lang.Character, java.lang.Integer> charMap =
			new java.util.HashMap<java.lang.Character, java.lang.Integer>();

		while (index < charArray.length)
		{
			if (charMap.containsKey (
				charArray[index]
			))
			{
				int lengthOfNonRepeatingSubstring = index - beginIndex;

				if (lengthOfLongestNonRepeatingSubstring < lengthOfNonRepeatingSubstring)
				{
					lengthOfLongestNonRepeatingSubstring = lengthOfNonRepeatingSubstring;
				}

				beginIndex = charMap.get (
					charArray[index]
				) + 1;

				charMap.clear();

				for (int currentNonRepeatingSubstringIndex = beginIndex;
					currentNonRepeatingSubstringIndex <= index;
					++currentNonRepeatingSubstringIndex)
				{
					charMap.put (
						charArray[currentNonRepeatingSubstringIndex],
						currentNonRepeatingSubstringIndex + beginIndex
					);
				}
			}
			else
			{
				charMap.put (
					charArray[index],
					index
				);
			}

			++index;
		}

		int lengthOfNonRepeatingSubstring = charArray.length - beginIndex;

		if (lengthOfLongestNonRepeatingSubstring < lengthOfNonRepeatingSubstring)
		{
			lengthOfLongestNonRepeatingSubstring = lengthOfNonRepeatingSubstring;
		}

		return lengthOfLongestNonRepeatingSubstring;
	}

	/**
	 * Given a string containing only three types of characters: '(', ')' and '*', write a function to check
	 * 	whether this string is valid. We define the validity of a string by these rules:
	 * 
	 * Any left parenthesis '(' must have a corresponding right parenthesis ')'.
	 * Any right parenthesis ')' must have a corresponding left parenthesis '('.
	 * Left parenthesis '(' must go before the corresponding right parenthesis ')'.
	 * '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty
	 *  string.
	 * An empty string is also valid.
	 *  
	 * @param s Input String
	 * 
	 * @return TRUE - The String has Valid Parenthesis
	 */

	public static final boolean ValidateParenthesisString (
		final java.lang.String s)
	{
		char[] charArray = s.toCharArray();

		int rightBracketIndex = charArray.length - 1;
		int leftBracketIndex = charArray.length - 2;
		int rightParenthesisCount = 0;
		int leftParenthesisCount = 0;
		int wildCardCount = 0;
		int wildCardIndex = 0;

		for (char c : charArray)
		{
			if (c == ')')
			{
				++rightParenthesisCount;
			}
			else if (c == '(')
			{
				++leftParenthesisCount;
			}
			else if (c == '*')
			{
				++wildCardCount;
			}
		}

		while (rightParenthesisCount > 0 && leftParenthesisCount > 0 && rightBracketIndex >= 0)
		{
			while (charArray[rightBracketIndex] != ')')
			{
				--rightBracketIndex;
			}

			leftBracketIndex = rightBracketIndex - 1 < leftBracketIndex ? rightBracketIndex - 1 :
				leftBracketIndex;

			while (leftBracketIndex >= 0)
			{
				if (charArray[leftBracketIndex] == '(')
				{
					--rightParenthesisCount;
					--leftParenthesisCount;
					break;
				}

				--leftBracketIndex;
			}

			--rightBracketIndex;
		}

		while (rightParenthesisCount > 0 && wildCardCount > 0 && rightBracketIndex > 0)
		{
			while (charArray[rightBracketIndex] != ')')
			{
				--rightBracketIndex;
			}

			while (wildCardIndex < rightBracketIndex)
			{
				if (charArray[wildCardIndex] == '*')
				{
					--rightParenthesisCount;
					--wildCardCount;
					break;
				}

				++wildCardIndex;
			}

			--rightBracketIndex;
		}

		if (rightParenthesisCount > 0)
		{
			return false;
		}

		if (leftParenthesisCount == 0)
		{
			return true;
		}

		int leftBracketIndexUpperBound = leftBracketIndex - 1;

		while (charArray[leftBracketIndexUpperBound] != '(')
		{
			--leftBracketIndexUpperBound;
		}

		leftBracketIndex = 0;

		while (leftParenthesisCount > 0 && wildCardCount > 0 &&
			leftBracketIndex <= leftBracketIndexUpperBound)
		{
			while (charArray[leftBracketIndex] != '(')
			{
				++leftBracketIndex;
			}

			wildCardIndex = leftBracketIndex + 1;

			while (wildCardIndex < charArray.length)
			{
				if (charArray[wildCardIndex] == '*')
				{
					--leftParenthesisCount;
					--wildCardCount;
					break;
				}

				++wildCardIndex;
			}

			++leftBracketIndex;
		}

		return 0 == leftParenthesisCount;
	}

	/**
	 * Given a positive 32-bit integer, you need to find the smallest 32-bit integer which has exactly the
	 * 	same digits existing in the integer and is greater in value. If no such positive 32-bit integer
	 * 	exists, you need to return -1.
	 * 
	 * @param n Input Integer
	 * 
	 * @return Next Greater Number
	 */

	public static final int NextGreaterInteger (
		int n)
	{
		java.util.List<java.lang.Integer> integerList = new java.util.ArrayList<java.lang.Integer>();

		while (n != 0)
		{
			integerList.add (
				0,
				n % 10
			);

			n = n / 10;
		}

		int integerListSize = integerList.size();

		if (1 == integerListSize)
		{
			return -1;
		}

		int rightInteger = integerList.get (
			integerListSize - 1
		);

		int leftIndex = integerListSize - 2;

		while (leftIndex >= 0)
		{
			int leftInteger = integerList.get (
				leftIndex
			);

			if (leftInteger < rightInteger)
			{
				int number = 0;

				for (int index = 0;
					index < integerListSize;
					++index)
				{
					if (index == leftIndex)
					{
						number = number * 10 + rightInteger;
					}
					else if (index == leftIndex + 1)
					{
						number = number * 10 + leftInteger;
					}
					else
					{
						number = number * 10 + integerList.get (
							index
						);
					}
				}

				return number;
			}

			rightInteger = leftInteger;
			--leftIndex;
		}

		return -1;
	}

	/**
	 * Given an absolute path for a file (Unix-style), simplify it. Or in other words, convert it to the
	 * 	canonical path.
	 * 
	 * In a UNIX-style file system, a period . refers to the current directory. Furthermore, a double period
	 * 	.. moves the directory up a level.
	 * 
	 * Note that the returned canonical path must always begin with a slash /, and there must be only a
	 * 	single slash / between two directory names. The last directory name (if it exists) must not end with
	 * 	a trailing /. Also, the canonical path must be the shortest string representing the absolute path.
	 * 
	 * @param path Input Path
	 * 
	 * @return Simplified Path
	 */

	public static final java.lang.String SimplifyPath (
		java.lang.String path)
	{
		while (path.startsWith (
			"/"
		))
		{
			path = path.substring (
				1
			);
		}

		while (path.endsWith (
			"/"
		))
		{
			path = path.substring (
				0,
				path.length() - 1
			);
		}

		java.lang.String[] folders = Split (
			path,
			"/"
		);

		java.util.List<java.lang.String> folderList = new java.util.ArrayList<java.lang.String>();

		for (java.lang.String folder : folders)
		{
			if (null == folder || folder.isEmpty())
			{
				continue;
			}

			if (folder.equals (
				".."
			))
			{
				int folderListIndex = folderList.size() - 1;

				if (0 <= folderListIndex)
				{
					folderList.remove (
						folderListIndex
					);
				}
			}
			else if (!folder.equals (
				"."
			))
			{
				folderList.add (
					folder
				);
			}
		}

		if (folderList.isEmpty())
		{
			return "/";
		}

		java.lang.String simplifiedPath = "";

		for (java.lang.String folder : folderList)
		{
			simplifiedPath = simplifiedPath + "/" + folder;
		}

		return simplifiedPath;
	}

	/**
	 * Given a string containing only 4 kinds of characters 'Q', 'W', 'E' and 'R'.
	 * 
	 * A string is said to be balanced if each of its characters appears n/4 times where n is the length of
	 * 	the string.
	 * 
	 * Return the minimum length of the substring that can be replaced with any other string of the same
	 *  length to make the original string balanced.
	 *  
	 * Return 0 if the string is already balanced.
	 * 
	 * @param s Input String
	 * 
	 * @return Minimum length of the substring
	 */

	public static final int BalanceString (
		final java.lang.String s)
	{
		char[] charArray = s.toCharArray();

		int stringLength = charArray.length;
		int charCount = stringLength / 4;
		int qCount = 0;
		int wCount = 0;
		int eCount = 0;
		int rCount = 0;

		for (int charIndex = 0;
			charIndex < stringLength;
			++charIndex)
		{
			if ('Q' == charArray[charIndex])
			{
				++qCount;
			}
			else if ('W' == charArray[charIndex])
			{
				++wCount;
			}
			else if ('E' == charArray[charIndex])
			{
				++eCount;
			}
			else if ('R' == charArray[charIndex])
			{
				++rCount;
			}
		}

		return (java.lang.Math.abs (
			qCount - charCount
		) + java.lang.Math.abs (
			wCount - charCount
		) + java.lang.Math.abs (
			eCount - charCount
		) + java.lang.Math.abs (
			rCount - charCount
		)) / 2;
	}

	/**
	 * 
	 * Given two non-negative integers represented as strings, return their product, also represented as a
	 *  string.
	 * 
	 * @param num1 First Number
	 * @param num2 Second Number
	 * 
	 * @return The Product
	 */

	public static final java.lang.String MultiplyNumbers (
		final java.lang.String num1,
		final java.lang.String num2)
	{
		java.util.List<java.lang.Integer> numberList1 = StringToNumber (
			num1
		);

		java.util.List<java.lang.Integer> numberList2 = StringToNumber (
			num2
		);

		int scale1 = 1;
		int multiplication = 0;

		int number1Length = numberList1.size();

		int number2Length = numberList2.size();

		for (int index1 = number1Length - 1;
			index1 >= 0;
			--index1)
		{
			int scale2 = 1;

			int digit1 = numberList1.get (
				index1
			);

			for (int index2 = number2Length - 1;
				index2 >= 0;
				--index2)
			{
				int digit2 = numberList2.get (
					index2
				);

				multiplication = multiplication + digit1 * scale1 * digit2 * scale2;
				scale2 = scale2 * 10;
			}

			scale1 = scale1 * 10;
		}

		return "" + multiplication;
	}

	private static final java.util.Map<java.lang.Character, java.lang.Integer> UpperChar2IntMap()
	{
		java.util.Map<java.lang.Character, java.lang.Integer> upperChar2IntMap =
			new java.util.HashMap<java.lang.Character, java.lang.Integer>();

		upperChar2IntMap.put (
			'A',
			0
		);

		upperChar2IntMap.put (
			'B',
			1
		);

		upperChar2IntMap.put (
			'C',
			2
		);

		upperChar2IntMap.put (
			'D',
			3
		);

		upperChar2IntMap.put (
			'E',
			4
		);

		upperChar2IntMap.put (
			'F',
			5
		);

		upperChar2IntMap.put (
			'G',
			6
		);

		upperChar2IntMap.put (
			'H',
			7
		);

		upperChar2IntMap.put (
			'I',
			8
		);

		upperChar2IntMap.put (
			'J',
			9
		);

		upperChar2IntMap.put (
			'K',
			10
		);

		upperChar2IntMap.put (
			'L',
			11
		);

		upperChar2IntMap.put (
			'M',
			12
		);

		upperChar2IntMap.put (
			'N',
			13
		);

		upperChar2IntMap.put (
			'O',
			14
		);

		upperChar2IntMap.put (
			'P',
			15
		);

		upperChar2IntMap.put (
			'Q',
			16
		);

		upperChar2IntMap.put (
			'R',
			17
		);

		upperChar2IntMap.put (
			'S',
			18
		);

		upperChar2IntMap.put (
			'T',
			19
		);

		upperChar2IntMap.put (
			'U',
			20
		);

		upperChar2IntMap.put (
			'V',
			21
		);

		upperChar2IntMap.put (
			'W',
			22
		);

		upperChar2IntMap.put (
			'X',
			23
		);

		upperChar2IntMap.put (
			'Y',
			24
		);

		upperChar2IntMap.put (
			'Z',
			25
		);

		return upperChar2IntMap;
	}

	private static final java.util.Map<java.lang.Character, java.lang.Integer> LowerChar2IntMap()
	{
		java.util.Map<java.lang.Character, java.lang.Integer> lowerChar2IntMap =
			new java.util.HashMap<java.lang.Character, java.lang.Integer>();

		lowerChar2IntMap.put (
			'a',
			0
		);

		lowerChar2IntMap.put (
			'b',
			1
		);

		lowerChar2IntMap.put (
			'c',
			2
		);

		lowerChar2IntMap.put (
			'd',
			3
		);

		lowerChar2IntMap.put (
			'e',
			4
		);

		lowerChar2IntMap.put (
			'f',
			5
		);

		lowerChar2IntMap.put (
			'g',
			6
		);

		lowerChar2IntMap.put (
			'h',
			7
		);

		lowerChar2IntMap.put (
			'i',
			8
		);

		lowerChar2IntMap.put (
			'j',
			9
		);

		lowerChar2IntMap.put (
			'k',
			10
		);

		lowerChar2IntMap.put (
			'l',
			11
		);

		lowerChar2IntMap.put (
			'm',
			12
		);

		lowerChar2IntMap.put (
			'n',
			13
		);

		lowerChar2IntMap.put (
			'o',
			14
		);

		lowerChar2IntMap.put (
			'p',
			15
		);

		lowerChar2IntMap.put (
			'q',
			16
		);

		lowerChar2IntMap.put (
			'r',
			17
		);

		lowerChar2IntMap.put (
			's',
			18
		);

		lowerChar2IntMap.put (
			't',
			19
		);

		lowerChar2IntMap.put (
			'u',
			20
		);

		lowerChar2IntMap.put (
			'v',
			21
		);

		lowerChar2IntMap.put (
			'w',
			22
		);

		lowerChar2IntMap.put (
			'x',
			23
		);

		lowerChar2IntMap.put (
			'y',
			24
		);

		lowerChar2IntMap.put (
			'z',
			25
		);

		return lowerChar2IntMap;
	}

	private static final char[] Int2UpperCharArray()
	{
		char[] int2UpperCharArray = new char[26];
		int2UpperCharArray[0] = 'A';
		int2UpperCharArray[1] = 'B';
		int2UpperCharArray[2] = 'C';
		int2UpperCharArray[3] = 'D';
		int2UpperCharArray[4] = 'E';
		int2UpperCharArray[5] = 'F';
		int2UpperCharArray[6] = 'G';
		int2UpperCharArray[7] = 'H';
		int2UpperCharArray[8] = 'I';
		int2UpperCharArray[9] = 'J';
		int2UpperCharArray[10] = 'K';
		int2UpperCharArray[11] = 'L';
		int2UpperCharArray[12] = 'M';
		int2UpperCharArray[13] = 'N';
		int2UpperCharArray[14] = 'O';
		int2UpperCharArray[15] = 'P';
		int2UpperCharArray[16] = 'Q';
		int2UpperCharArray[17] = 'R';
		int2UpperCharArray[18] = 'S';
		int2UpperCharArray[19] = 'T';
		int2UpperCharArray[20] = 'U';
		int2UpperCharArray[21] = 'V';
		int2UpperCharArray[22] = 'W';
		int2UpperCharArray[23] = 'X';
		int2UpperCharArray[24] = 'Y';
		int2UpperCharArray[25] = 'Z';
		return int2UpperCharArray;
	}

	private static final char[] Int2LowerCharArray()
	{
		char[] int2LowerCharArray = new char[26];
		int2LowerCharArray[0] = 'a';
		int2LowerCharArray[1] = 'b';
		int2LowerCharArray[2] = 'c';
		int2LowerCharArray[3] = 'd';
		int2LowerCharArray[4] = 'e';
		int2LowerCharArray[5] = 'f';
		int2LowerCharArray[6] = 'g';
		int2LowerCharArray[7] = 'h';
		int2LowerCharArray[8] = 'i';
		int2LowerCharArray[9] = 'j';
		int2LowerCharArray[10] = 'k';
		int2LowerCharArray[11] = 'l';
		int2LowerCharArray[12] = 'm';
		int2LowerCharArray[13] = 'n';
		int2LowerCharArray[14] = 'o';
		int2LowerCharArray[15] = 'p';
		int2LowerCharArray[16] = 'q';
		int2LowerCharArray[17] = 'r';
		int2LowerCharArray[18] = 's';
		int2LowerCharArray[19] = 't';
		int2LowerCharArray[20] = 'u';
		int2LowerCharArray[21] = 'v';
		int2LowerCharArray[22] = 'w';
		int2LowerCharArray[23] = 'x';
		int2LowerCharArray[24] = 'y';
		int2LowerCharArray[25] = 'z';
		return int2LowerCharArray;
	}

	private static final java.util.Map<java.lang.Character, java.lang.Integer> Int2IntMap()
	{
		java.util.Map<java.lang.Character, java.lang.Integer> int2IntMap =
			new java.util.HashMap<java.lang.Character, java.lang.Integer>();

		int2IntMap.put (
			'0',
			0
		);

		int2IntMap.put (
			'1',
			1
		);

		int2IntMap.put (
			'2',
			2
		);

		int2IntMap.put (
			'3',
			3
		);

		int2IntMap.put (
			'4',
			4
		);

		int2IntMap.put (
			'5',
			5
		);

		int2IntMap.put (
			'6',
			6
		);

		int2IntMap.put (
			'7',
			7
		);

		int2IntMap.put (
			'8',
			8
		);

		int2IntMap.put (
			'9',
			9
		);

		return int2IntMap;
	}

	/**
	 * One simple way to encrypt a string is to "rotate" every alphanumeric character by a certain amount.
	 *  Rotating a character means replacing it with another character that is a certain number of steps away
	 *  in normal alphabetic or numerical order.
	 *  
	 * For example, if the string "Zebra-493?" is rotated 3 places, the resulting string is "Cheud-726?".
	 * 	Every alphabetic character is replaced with the character 3 letters higher (wrapping around from Z to
	 *  A), and every numeric character replaced with the character 3 digits higher (wrapping around from 9
	 *  to 0). Note that the non-alphanumeric characters remain unchanged.
	 *  
	 * Given a string and a rotation factor, return an encrypted string.
	 *  
	 * @param in Input String
	 * @param rotationFactor Rotation Factor
	 * 
	 * @return The Encrypted String
	 */

	public static final java.lang.String RotationalCipher (
		final java.lang.String in,
		final int rotationFactor)
	{
		java.lang.String out = "";

		char[] int2LowerCharArray = Int2LowerCharArray();

		char[] int2UpperCharArray = Int2UpperCharArray();

		java.util.Map<java.lang.Character, java.lang.Integer> int2IntMap = Int2IntMap();

		java.util.Map<java.lang.Character, java.lang.Integer> lowerChar2IntMap = LowerChar2IntMap();

		java.util.Map<java.lang.Character, java.lang.Integer> upperChar2IntMap = UpperChar2IntMap();

		for (int i = 0; i < in.length(); ++i)
		{
			char c = in.charAt (
				i
			);

			if (int2IntMap.containsKey (
				c
			))
			{
				int value = int2IntMap.get (
					c
				) + rotationFactor;

				while (value >= 10)
				{
					value = value - 10;
				}

				out = out + value;
			}
			else if (lowerChar2IntMap.containsKey (
				c
			))
			{
				int value = lowerChar2IntMap.get (
					c
				) + rotationFactor;

				while (value >= 26)
				{
					value = value - 26;
				}

				out = out + int2LowerCharArray[value];
			}
			else if (upperChar2IntMap.containsKey (
				c
			))
			{
				int value = upperChar2IntMap.get (
					c
				) + rotationFactor;

				while (value >= 26)
				{
					value = value - 26;
				}

				out = out + int2UpperCharArray[value];
			}
			else
			{
				out = out + c;
			}
		}

		return out;
	}

	public static final int MatchingPairCount (
		final java.lang.String s,
		final java.lang.String t)
	{
		int length = s.length();

		int matchingPairCount = 0;

		java.util.List<java.lang.Integer> mismatchedIndexList =
			new java.util.ArrayList<java.lang.Integer>();

		for (int i = 0; i < length; ++i)
		{
			if (s.charAt (
					i
				) == t.charAt (
					i
				)
			)
			{
				++matchingPairCount;
			}
			else
			{
				mismatchedIndexList.add (
					i
				);
			}
		}

		int mismatchedIndexListSize = mismatchedIndexList.size();

		if (0 == mismatchedIndexListSize || 1 == mismatchedIndexListSize)
		{
			return length - 2;
		}

		boolean singleMatch = false;
		boolean doubleMatch = false;

		for (int i = 0; i < mismatchedIndexListSize; ++i)
		{
			if (doubleMatch)
			{
				break;
			}

			int indexI = mismatchedIndexList.get (i);

			char tI = t.charAt (indexI);

			char sI = s.charAt (indexI);

			for (int j = i + 1; j < mismatchedIndexListSize; ++j)
			{
				int indexJ = mismatchedIndexList.get (j);

				char tJ = t.charAt (indexJ);

				char sJ = s.charAt (indexJ);

				if (sI == tJ && tI == sJ)
				{
					doubleMatch = true;
					break;
				}
				else if (sI == tJ || tI == sJ)
				{
					singleMatch = true;
				}
			}
		}

		if (doubleMatch)
		{
			matchingPairCount = matchingPairCount + 2;
		}
		else if (singleMatch)
		{
			matchingPairCount = matchingPairCount + 1;
		}

		return matchingPairCount;
	}

	public static final java.lang.String MinimumWindowSubstring (
		final java.lang.String s,
		final java.lang.String t)
	{
		if (null == s || s.isEmpty() || null == t || t.isEmpty() || s.length() < t.length()) return "";

		int sLength = s.length();

		int tLength = t.length();

		if (sLength < tLength) return "";

		java.util.Map<java.lang.Character, java.lang.Integer> tCharacterCountMap =
			new java.util.HashMap<java.lang.Character, java.lang.Integer>();

		for (char c : t.toCharArray()) {
			if (tCharacterCountMap.containsKey (c))
				tCharacterCountMap.put (c, tCharacterCountMap.get (c) + 1);
			else
				tCharacterCountMap.put (c, 1);
		}

		int leftIndex = 0;
		int minLeftIndex = 0;
		int matchCharCount = 0;
		int minLen = sLength + 1;

		for (int rightIndex = 0; rightIndex < sLength; ++rightIndex) {
			char rightChar = s.charAt (rightIndex);

			if (tCharacterCountMap.containsKey (rightChar)) {
				int rightSideCharacterCount = tCharacterCountMap.get (rightChar);

				tCharacterCountMap.put (rightChar, rightSideCharacterCount - 1);

				if (rightSideCharacterCount > 0) ++matchCharCount;

				while (matchCharCount == tLength) {
					if (rightIndex - leftIndex + 1 < minLen) {
						minLeftIndex = leftIndex;
						minLen = rightIndex - leftIndex + 1;
					}

					char leftChar = s.charAt (leftIndex);

					if (tCharacterCountMap.containsKey (leftChar)) {
						int leftSideCharacterCount = tCharacterCountMap.get (leftChar);

						tCharacterCountMap.put (rightChar, leftSideCharacterCount + 1);

						if (leftSideCharacterCount <= 0) --matchCharCount;
					}

					++leftChar;
				}
			}
		}

		return minLen > sLength ? "" : s.substring (minLeftIndex, minLeftIndex + minLen);
	}

	/**
	 * Given a non-empty string, you may delete at most one character. Judge whether you can make it a
	 * 	palindrome.
	 * 
	 * @param s Input String
	 * 
	 * @return TRUE - Can be turned into a Palindrome with one Deletion
	 */

	public static final boolean IsValidPalindrome (
		final java.lang.String s)
	{
		char[] charArray = s.toCharArray();

		int stringLength = charArray.length;
		int rightIndex = stringLength - 1;
		int leftIndex = 0;

		while (leftIndex <= rightIndex)
		{
			if (charArray[leftIndex] != charArray[rightIndex])
			{
				if (charArray[leftIndex + 1] == charArray[rightIndex])
				{
					++leftIndex;
				}
				else if (charArray[leftIndex] == charArray[rightIndex - 1])
				{
					--rightIndex;
				}
				else
				{
					return false;
				}
			}

			++leftIndex;
			--rightIndex;
		}

		return true;
	}

	/**
	 * Given an integer n, find the closest integer (not including itself), which is a palindrome.
	 * 
	 * The 'closest' is defined as absolute difference minimized between two integers.
	 * 
	 * @param i The Integer
	 * 
	 * @return Closest Palindrome Integer
	 */

	public static final int ClosestPalindromicInteger (
		final int i)
	{
		java.util.List<java.lang.Integer> integerToDigitList = IntegerToDigitList (
			i
		);

		int palindromeInteger = DigitListToInteger (
			SwapIntoPalindrome (
					integerToDigitList
				)
			);

		int difference = i - palindromeInteger;
		int absoluteDifference = difference < 0 ? -1 * difference : difference;
		int rightNumber = i + absoluteDifference;
		int leftNumber = i - absoluteDifference;

		while (leftNumber < rightNumber)
		{
			if (IsPalindrome (leftNumber))
			{
				int currentDifference = leftNumber - i;

				if (absoluteDifference > -1 * currentDifference)
				{
					difference = currentDifference;
					absoluteDifference = -1 * currentDifference;
				}
			}
			else if (IsPalindrome (rightNumber))
			{
				int currentDifference = rightNumber - i;

				if (absoluteDifference > currentDifference)
				{
					difference = currentDifference;
					absoluteDifference = currentDifference;
				}
			}

			++leftNumber;
			--rightNumber;
		}

		return i + difference;
	}

	/**
	 * Given Nested Array calculate the Depth Sum. 
	 * 
	 * @param expression The Expression
	 * 
	 * @return Value of the Nested Array Sum
	 */

	public static final java.lang.String NestedArrayDepthSum (
		final java.lang.String expression)
	{
		return NestedArrayDepthSum (
			expression.substring (
				1,
				expression.length() - 1
			),
			1
		);
	}

	/**
	 * An encoded string is given.  To find and write the decoded string to a tape, the encoded string is
	 * 	read one character at a time and the following steps are taken:
	 * 
	 * If the character read is a letter, that letter is written onto the tape.
	 * 
	 * If the character read is a digit (say d), the entire current tape is repeatedly written d-1 more times
	 * 	in total.
	 * 
	 * Now for some encoded string, and an index K, find and return the k<sup>th</sup> letter (1 indexed) in
	 * 	the decoded string.
	 * 
	 * @param s Input String
	 * @param k k<sup>th</sup> Location (1-indexed)
	 * 
	 * @return k<sup>th</sup> letter (1 indexed) in the decoded string.
	 */

	public static final java.lang.String DecodeStringAtIndex (
		final java.lang.String s,
		final int k)
	{
		java.util.List<java.lang.Integer> indexList = new java.util.ArrayList<java.lang.Integer>();

		java.util.List<java.lang.String> wordList = new java.util.ArrayList<java.lang.String>();

		char[] charArray = s.toCharArray();

		int charIndex = 0;
		int startWordIndex = 0;
		java.lang.String prevWord = "";
		int stringLength = charArray.length;

		while (true)
		{
			while (charIndex < stringLength &&
				!java.lang.Character.isDigit (
					charArray[charIndex]
				)
			)
			{
				++charIndex;
			}

			wordList.add (
				s.substring (
					startWordIndex,
					charIndex
				)
			);

			if (charIndex == stringLength)
			{
				break;
			}

			int numberStartIndex = charIndex;
			startWordIndex = charIndex + 1;

			while (charIndex < stringLength &&
				java.lang.Character.isDigit (
					charArray[charIndex]
				)
			)
			{
				++charIndex;
			}

			indexList.add (
				DecimalNumberFromString (
					s.substring (
						numberStartIndex,
						charIndex
					)
				)
			);

			if (charIndex == stringLength)
			{
				break;
			}
		}

		if (indexList.isEmpty() && k > stringLength)
		{
			return "";
		}

		for (int i = 0;
			i < indexList.size();
			++i)
		{
			java.lang.String currentWord = prevWord + wordList.get (
				i
			);

			int repeatCount = indexList.get (
				i
			);

			int currentWordLength = currentWord.length();

			if (currentWordLength * repeatCount > k)
			{
				return "" + currentWord.charAt (
					(k - 1) % currentWordLength
				);
			}

			prevWord = "";

			for (int j = 0;
				j < repeatCount;
				++j)
			{
				prevWord = prevWord + currentWord;
			}
		}

		return "";
	}

	/**
	 * Given a string, find the longest palindromic substring in it. You may assume that the maximum length
	 *  of the string is 1000.
	 * 
	 * @param s The Input String
	 * 
	 * @return The Longest Palindrome Sub-string
	 */

	public static final java.lang.String LongestPalindromeSubstring (
		final java.lang.String s)
	{
		if (null == s || s.isEmpty())
		{
			return "";
		}

		char[] charArray = s.toCharArray();

		int stringLength = charArray.length;
		int rightPalindromeIndex = -1;
		int leftPalindromeIndex = -1;

		java.util.HashMap<java.lang.Character, java.util.List<java.lang.Integer>> charLocationMap =
			new java.util.HashMap<java.lang.Character, java.util.List<java.lang.Integer>>();

		for (int index = 0;
			index < stringLength;
			++index)
		{
			char c = charArray[index];

			if (charLocationMap.containsKey (
				c
			))
			{
				charLocationMap.get (
					c
				).add (
					index
				);
			}
			else
			{
				java.util.List<java.lang.Integer> locationList =
					new java.util.ArrayList<java.lang.Integer>();

				locationList.add (
					index
				);

				charLocationMap.put (
					c,
					locationList
				);
			}
		}

		java.util.Set<java.lang.Character> charKeySet = charLocationMap.keySet();

		for (java.lang.Character c : charKeySet)
		{
			java.util.List<java.lang.Integer> locationList = charLocationMap.get (
				c
			);

			int locationListSize = locationList.size();

			for (int i = 0;
				i < locationListSize;
				++i)
			{
				for (int j = locationListSize - 1;
					j > i;
					--j)
				{
					int leftIndexStart = locationList.get (
						i
					);

					int rightIndexStart = locationList.get (
						j
					);

					boolean isPalindrome = true;
					int leftIndex = leftIndexStart;
					int rightIndex = rightIndexStart;

					if ((leftPalindromeIndex < leftIndex && rightPalindromeIndex > leftIndex) ||
						(leftPalindromeIndex < rightIndex && rightPalindromeIndex > rightIndex)
					)
					{
						continue;
					}

					while (leftIndex < rightIndex)
					{
						if (charArray[leftIndex++] != charArray[rightIndex--])
						{
							isPalindrome = false;
							break;
						}
					}

					if (isPalindrome)
					{
						leftPalindromeIndex = leftIndexStart;
						rightPalindromeIndex = rightIndexStart;
					}
				}
			}
		}

		return -1 == leftPalindromeIndex ? "" : s.substring (
			leftPalindromeIndex,
			rightPalindromeIndex + 1
		);
	}

	/**
	 * Given a string, find the length of the longest substring without repeating characters.
	 * 
	 * @param s The Given String
	 * 
	 * @return The Longest Non-repeating Sub-string
	 */

	public static final java.lang.String LongestNonRepeatingSubstring (
		final java.lang.String s)
	{
		if (null == s || s.isEmpty())
		{
			return "";
		}

		char[] charArray = s.toCharArray();

		int endNonRepeatingIndex = -1;
		int beginNonRepeatingIndex = -1;
		int nonRepeatingIndexFromRight = 0;
		int stringLength = charArray.length;
		int nonRepeatingIndexFromLeft = stringLength;

		java.util.HashMap<java.lang.Character, java.util.List<java.lang.Integer>> charLocationMap =
			new java.util.HashMap<java.lang.Character, java.util.List<java.lang.Integer>>();

		for (int index = 0;
			index < stringLength;
			++index)
		{
			char c = charArray[index];

			if (charLocationMap.containsKey (
				c
			))
			{
				charLocationMap.get (
					c
				).add (
					index
				);
			}
			else
			{
				java.util.List<java.lang.Integer> locationList =
					new java.util.ArrayList<java.lang.Integer>();

				locationList.add (
					index
				);

				charLocationMap.put (
					c,
					locationList
				);
			}
		}

		java.util.Set<java.lang.Character> charKeySet = charLocationMap.keySet();

		for (java.lang.Character c : charKeySet)
		{
			java.util.List<java.lang.Integer> locationList = charLocationMap.get (
				c
			);

			int locationListSize = locationList.size();

			if (locationListSize < 2)
			{
				continue;
			}

			int leftLocationIndex = locationList.get (
				1
			);

			int rightLocationIndex = locationList.get (
				locationListSize - 2
			);

			if (nonRepeatingIndexFromLeft > leftLocationIndex)
			{
				nonRepeatingIndexFromLeft = leftLocationIndex;
			}

			if (nonRepeatingIndexFromRight < rightLocationIndex)
			{
				nonRepeatingIndexFromRight = rightLocationIndex;
			}

			for (int i = 1;
				i < locationListSize;
				++i)
			{
				int leftIndex = locationList.get (
					i - 1
				);

				int rightIndex = locationList.get (
					i
				);

				if (-1 == endNonRepeatingIndex)
				{
					endNonRepeatingIndex = rightIndex;
					beginNonRepeatingIndex = leftIndex;
				}
				else if (
					(leftIndex > beginNonRepeatingIndex && leftIndex < endNonRepeatingIndex) ||
					(rightIndex > beginNonRepeatingIndex && rightIndex < endNonRepeatingIndex)
				)
				{
					if (rightIndex - leftIndex < endNonRepeatingIndex - beginNonRepeatingIndex)
					{
						endNonRepeatingIndex = rightIndex;
						beginNonRepeatingIndex = leftIndex;
					}
				}
				else
				{
					if (rightIndex - leftIndex > endNonRepeatingIndex - beginNonRepeatingIndex)
					{
						endNonRepeatingIndex = rightIndex;
						beginNonRepeatingIndex = leftIndex;
					}
				}
			}
		}

		if (-1 == endNonRepeatingIndex)
		{
			return nonRepeatingIndexFromLeft > stringLength - nonRepeatingIndexFromRight ? s.substring (
				0,
				nonRepeatingIndexFromLeft
			) :  s.substring (
				nonRepeatingIndexFromRight + 1,
				stringLength - nonRepeatingIndexFromRight - 1
			);
		}

		int substringLeftIndex = beginNonRepeatingIndex;
		int substringRightIndex = endNonRepeatingIndex;

		if (endNonRepeatingIndex - beginNonRepeatingIndex < nonRepeatingIndexFromLeft)
		{
			substringLeftIndex = 0;
			substringRightIndex = nonRepeatingIndexFromLeft;
		}

		return substringRightIndex - substringLeftIndex > stringLength - nonRepeatingIndexFromRight - 1 ?
			s.substring (
				substringLeftIndex,
				substringRightIndex
			) :  s.substring (
				nonRepeatingIndexFromRight + 1,
				stringLength
			);
	}

	private static final java.lang.String[] ZeroToTwentyTable()
	{
		java.lang.String[] zeroToTwentyTable = new java.lang.String[20];
		zeroToTwentyTable[0] = "Zero";
		zeroToTwentyTable[1] = "One";
		zeroToTwentyTable[2] = "Two";
		zeroToTwentyTable[3] = "Three";
		zeroToTwentyTable[4] = "Four";
		zeroToTwentyTable[5] = "Five";
		zeroToTwentyTable[6] = "Six";
		zeroToTwentyTable[7] = "Seven";
		zeroToTwentyTable[8] = "Eight";
		zeroToTwentyTable[9] = "None";
		zeroToTwentyTable[10] = "Ten";
		zeroToTwentyTable[11] = "Eleven";
		zeroToTwentyTable[12] = "Twelve";
		zeroToTwentyTable[13] = "Thirteen";
		zeroToTwentyTable[14] = "Fourteen";
		zeroToTwentyTable[15] = "Fifteen";
		zeroToTwentyTable[16] = "Sixteen";
		zeroToTwentyTable[17] = "Seventeen";
		zeroToTwentyTable[18] = "Eighteen";
		zeroToTwentyTable[19] = "Nineteen";
		return zeroToTwentyTable;
	}

	private static final java.lang.String[] TensPlaceTable()
	{
		java.lang.String[] tensPlaceTable = new java.lang.String[10];
		tensPlaceTable[2] = "Twenty";
		tensPlaceTable[3] = "Thirty";
		tensPlaceTable[4] = "Forty";
		tensPlaceTable[5] = "Fifty";
		tensPlaceTable[6] = "Sixty";
		tensPlaceTable[7] = "Seventy";
		tensPlaceTable[8] = "Eighty";
		tensPlaceTable[9] = "No=inety";
		return tensPlaceTable;
	}

	private static final java.lang.String TwoDigitNumber (
		final int i)
	{
		java.lang.String[] zeroToTwentyTable = ZeroToTwentyTable();

		if (i < 20)
		{
			return zeroToTwentyTable[i];
		}

		java.lang.String twoDigitNumber = TensPlaceTable()[i / 10];

		int rightDigit = i % 10;
		return 0 == rightDigit ? twoDigitNumber : twoDigitNumber + " " + zeroToTwentyTable[rightDigit];
	}

	public static final java.lang.String ThreeDigitNumber (
		final int i)
	{
		return i < 100 ? TwoDigitNumber (
			i
		) : ZeroToTwentyTable()[i / 100] + " Hundred and " + TwoDigitNumber (
			i % 100
		);
	}

	public static final java.lang.String ReorganizeString (
		final java.lang.String in)
	{
		char[] charArrayIn = in.toCharArray();

		int evenIndexLocation = 0;
		boolean oddLocation = false;
		int oddLeftIndexLocation = 1;
		int stringLength = charArrayIn.length;
		char[] charArrayOut = new char[stringLength];
		int oddRightIndexLocation = 0 == stringLength % 2 ? stringLength - 1 : stringLength - 2;

		java.util.TreeMap<java.lang.Character, java.lang.Integer> charCountMap =
			new java.util.TreeMap<java.lang.Character, java.lang.Integer>();

		for (char c : charArrayIn)
		{
			if (charCountMap.containsKey (
				c
			))
			{
				charCountMap.put(c, charCountMap.get(c) + 1);
			}
			else
			{
				charCountMap.put(c, 1);
			}
		}

		while (!charCountMap.isEmpty())
		{
			java.util.Map.Entry<java.lang.Character, java.lang.Integer> entry = charCountMap.firstEntry();

			char c = entry.getKey();

			int count = entry.getValue();

			if (count > (1 + stringLength) / 2)
			{
				return "";
			}

			charCountMap.remove (
				c
			);

			if (!oddLocation)
			{
				for (;
					evenIndexLocation < stringLength;
					evenIndexLocation = evenIndexLocation + 2)
				{
					charArrayOut[evenIndexLocation] = c;
					--count;
				}

				while (0 != count)
				{
					charArrayOut[oddRightIndexLocation] = c;
					oddRightIndexLocation = oddRightIndexLocation - 2;
					--count;
				}

				oddLocation = !oddLocation;
			}
			else
			{
				for (;
					oddLeftIndexLocation < stringLength;
					oddLeftIndexLocation = oddLeftIndexLocation + 2)
				{
					charArrayOut[oddLeftIndexLocation] = c;
					--count;
				}

				oddLocation = !oddLocation;
			}
		}

		return new java.lang.String (
			charArrayOut
		);
	}

	public static final java.lang.String RemoveMinimumValidParenthesis (
		final java.lang.String s)
	{
		java.util.List<java.lang.Integer> leftBracketIndexList = new java.util.ArrayList<java.lang.Integer>();

		java.util.List<java.lang.Integer> rightBracketIndexList = new java.util.ArrayList<java.lang.Integer>();

		char[] charArray = s.toCharArray();

		int stringLength = charArray.length;

		for (int index = 0;
			index < stringLength;
			++index)
		{
			char c = charArray[index];

			if ('(' == c)
			{
				leftBracketIndexList.add (
					index
				);
			}
			else if (')' == c)
			{
				rightBracketIndexList.add (
					index
				);
			}
		}

		if (leftBracketIndexList.isEmpty() && rightBracketIndexList.isEmpty())
		{
			return s;
		}

		java.util.Set<java.lang.Integer> indexRemovalList = new java.util.HashSet<java.lang.Integer>();

		while (!leftBracketIndexList.isEmpty() && !rightBracketIndexList.isEmpty())
		{
			int rightMostLeftBracketIndex = leftBracketIndexList.get (
				leftBracketIndexList.size() - 1
			);

			int pairingRightIndex = -1;
			int pairingRightBracketIndexListCursor = -1;

			for (int rightBracketIndexListCursor = 0;
				rightBracketIndexListCursor < rightBracketIndexList.size();
				++rightBracketIndexListCursor)
			{
				int rightIndex = rightBracketIndexList.get (
					rightBracketIndexListCursor
				);

				if (rightIndex > rightMostLeftBracketIndex)
				{
					pairingRightIndex = rightIndex;
					pairingRightBracketIndexListCursor = rightBracketIndexListCursor;
				}
			}

			if (-1 == pairingRightIndex)
			{
				indexRemovalList.add (
					rightMostLeftBracketIndex
				);

				leftBracketIndexList.remove (
					leftBracketIndexList.size() - 1
				);
			}
			else
			{
				leftBracketIndexList.remove (
					leftBracketIndexList.size() - 1
				);

				rightBracketIndexList.remove (
					pairingRightBracketIndexListCursor
				);
			}
		}

		if (!leftBracketIndexList.isEmpty())
		{
			indexRemovalList.addAll (
				leftBracketIndexList
			);
		}
		else if (!rightBracketIndexList.isEmpty())
		{
			indexRemovalList.addAll (
				rightBracketIndexList
			);
		}

		java.lang.String output = "";

		for (int index = 0;
			index < stringLength;
			++index)
		{
			if (!indexRemovalList.contains (
				index
			))
			{
				output = output + charArray[index];
			}
		}

		return output;
	}

	private static final java.util.Set<java.lang.String> PermutationSet (
		final java.util.Set<java.lang.String> permutationSet,
		final java.util.Set<java.lang.Integer> exclusionIndexSet,
		final java.lang.String s)
	{
		char[] charArray = s.toCharArray();

		int stringLength = charArray.length;

		if (exclusionIndexSet.size() == stringLength - 1)
		{
			int exclusionIndex = -1;

			for (int i = 0; i < stringLength; ++i)
			{
				if (!exclusionIndexSet.contains(i))
				{
					exclusionIndex = i;
					break;
				}
			}

			java.util.Set<java.lang.String> currentPermutationSet =
				new java.util.HashSet<java.lang.String>();

			for (java.lang.String permutation : permutationSet)
			{
				currentPermutationSet.add(permutation + charArray[exclusionIndex]);
			}

			return currentPermutationSet;
		}

		java.util.Set<java.lang.Integer> inclusionIndexSet = new java.util.HashSet<java.lang.Integer>();

		for (int i = 0; i < stringLength; ++i)
		{
			if (!exclusionIndexSet.contains(i))
			{
				inclusionIndexSet.add (i);
			}
		}

		java.util.Set<java.lang.String> currentPermutationSet =
			new java.util.HashSet<java.lang.String>();

		for (int index : inclusionIndexSet)
		{
			java.util.Set<java.lang.Integer> tempExclusionIndexSet =
				new java.util.HashSet<java.lang.Integer>();

			tempExclusionIndexSet.addAll (exclusionIndexSet);

			tempExclusionIndexSet.add (index);

			java.util.Set<java.lang.String> tempPermutationSet =
				new java.util.HashSet<java.lang.String>();

			if (permutationSet.isEmpty())
			{
				tempPermutationSet.add ("" + charArray[index]);
			}
			else
			{
				for (java.lang.String permutation : permutationSet)
				{
					tempPermutationSet.add (permutation + charArray[index]);
				}
			}

			currentPermutationSet.addAll (
				PermutationSet (
					tempPermutationSet,
					tempExclusionIndexSet,
					s
				)
			);
		}

		return currentPermutationSet;
	}

	public static final java.util.Set<java.lang.String> PermutationSet (
		final java.lang.String s)
	{
		java.util.Set<java.lang.String> permutationSet = new java.util.HashSet<java.lang.String>();

		java.util.Set<java.lang.Integer> exclusionIndexSet = new java.util.HashSet<java.lang.Integer>();

		return PermutationSet (
			permutationSet,
			exclusionIndexSet,
			s
		);
	}

	public static final boolean IsPermutationPresent (
		final java.lang.String s1,
		final java.lang.String s2)
	{
		java.util.Set<java.lang.String> s1PermutationSet = PermutationSet (s1);

		int s1Size = s1.length();

		for (int i = 0; i < s2.length() - s1Size; ++i)
		{
			if (s1PermutationSet.contains (s2.substring(i, i + s1Size)))
			{
				return true;
			}
		}

		return false;
	}

	public static final java.util.List<java.util.List<java.lang.String>> MergeAccountList (
		final java.util.List<java.util.List<java.lang.String>> accounts)
	{
		java.util.Map<java.lang.String, java.lang.Integer> emailIndexMap =
			new java.util.HashMap<java.lang.String, java.lang.Integer>();

		for (int index = 0;
			index < accounts.size();
			++index)
		{
			java.util.List<java.lang.String> accountDetail = accounts.get(index);

			int matchingAccountIndex = -1;
			java.lang.String matchingEmail = "";

			for (int listIndex = 1; listIndex < accountDetail.size(); ++listIndex)
			{
				java.lang.String email = accountDetail.get(listIndex);

				if (emailIndexMap.containsKey(email))
				{
					matchingAccountIndex = emailIndexMap.get(email);

					matchingEmail = email;
					break;
				}
			}

			if (-1 == matchingAccountIndex)
			{
				for (int listIndex = 1; listIndex < accountDetail.size(); ++listIndex)
				{
					emailIndexMap.put(accountDetail.get(listIndex), index);
				}
			}
			else
			{
				java.util.List<java.lang.String> matchingAccountDetail = accounts.get(matchingAccountIndex);

				for (int listIndex = 1; listIndex < accountDetail.size(); ++listIndex)
				{
					java.lang.String email = accountDetail.get(listIndex);

					emailIndexMap.put(email, matchingAccountIndex);

					if (!matchingEmail.equals(email)) matchingAccountDetail.add(email);
				}

				accounts.set(index, null);
			}
		}

		java.util.List<java.util.List<java.lang.String>> mergedAccountList =
			new java.util.ArrayList<java.util.List<java.lang.String>>();

		for (int index = 0;
			index < accounts.size();
			++index)
		{
			java.util.List<java.lang.String> accountDetail = accounts.get(index);

			if (null != accountDetail) mergedAccountList.add(accountDetail);
		}

		return mergedAccountList;
	}

	private static final int SumOfDigits (int n)
	{
		int sum = 0;

		while (n != 0)
		{
			sum = sum + (n % 10);
			n = n / 10;
		}

		return sum;
	}

    public static final int solution(int[] A)
    {
    	java.util.HashMap<java.lang.Integer, java.util.List<java.lang.Integer>> integerSumListMap =
    		new java.util.HashMap<java.lang.Integer, java.util.List<java.lang.Integer>>();

    	int max = java.lang.Integer.MIN_VALUE;

    	for (int i : A)
    	{
    		int digitSum = SumOfDigits (i);

    		if (integerSumListMap.containsKey (digitSum))
    		{
    			integerSumListMap.get(digitSum).add(i);
    		}
    		else
    		{
    			java.util.List<java.lang.Integer> integerList  = new java.util.ArrayList<java.lang.Integer>();

    			integerList.add(i);

    			integerSumListMap.put(digitSum, integerList);
    		}
    	}

    	for (java.util.Map.Entry<java.lang.Integer, java.util.List<java.lang.Integer>> integerSumListEntry :
    		integerSumListMap.entrySet())
    	{
    		java.util.List<java.lang.Integer> integerList = integerSumListEntry.getValue();

    		int listSize = integerList.size();

    		if (1 != listSize)
    		{
    			for (int i = 0; i < listSize; ++i)
    			{
        			for (int j = 0; j < i; ++j)
        			{
        				int sum = integerList.get(i) + integerList.get(j);

        				if (sum > max)
        				{
        					max = sum;
        				}
        			}
    			}
    		}
    	}

    	return java.lang.Integer.MIN_VALUE == max ? -1 : max;
    }

    /**
     * Indicate if the Input String is composed of Valid Parenthesis Sequence
     * 
     * @param input Input String
     * 
     * @return TRUE - The Input String is composed of Valid Parenthesis Sequence
     */

    public static final boolean ValidateParenthesis (
    	final java.lang.String input)
    {
    	if (null == input || input.isEmpty())
    	{
    		return false;
    	}

    	java.util.List<java.lang.Character> charList = new java.util.ArrayList<java.lang.Character>();

    	for (char c : input.toCharArray())
    	{
    		if ('(' == c || '{' == c || '[' == c)
    		{
    			charList.add (
    				c
    			);
    		}
    		else if (')' == c || '}' == c || ']' == c)
    		{
    			if (0 == charList.size())
    			{
    				return false;
    			}

    			char prevChar = charList.get (
    				charList.size() - 1
    			);

    			if (')' == c)
				{
    				if (prevChar != '(')
    				{
    					return false;
    				}

    				charList.remove (
	    				charList.size() - 1
	    			);
				}
    			else if (']' == c)
				{
    				if (prevChar != '[')
    				{
    					return false;
    				}

    				charList.remove (
	    				charList.size() - 1
	    			);
				}
    			else if ('}' == c)
				{
    				if (prevChar != '{')
    				{
    					return false;
    				}

    				charList.remove (
	    				charList.size() - 1
	    			);
				}
    		}
    	}

    	return charList.isEmpty();
    }

    /**
     * Generate the Longest Distinct Substring
     * 
     * @param s Input String
     * 
     * @return The Longest Distinct Substring
     */

    public static final java.lang.String LongestDistinctSubstring (
    	final java.lang.String s)
    {
    	char[] charArray = s.toCharArray();

    	int index = 0;
    	int bestLeft = 0;
    	int bestRight = 0;
    	int currentLeft = 0;
    	int stringLength = charArray.length;

    	java.util.Map<java.lang.Character, java.lang.Integer> charLocationMap =
    		new java.util.HashMap<java.lang.Character, java.lang.Integer>();

    	while (index < stringLength)
    	{
    		char c = charArray[index];

    		if (charLocationMap.containsKey (
    			c
    		))
    		{
    			if (index - currentLeft > bestRight - bestLeft)
    			{
    				bestRight = index;
    				bestLeft = currentLeft;
    			}

    			currentLeft = charLocationMap.get (
    				c
    			) + 1;
    		}

    		charLocationMap.put (
    			c,
    			index
    		);

    		++index;
    	}

		if (index - currentLeft > bestRight - bestLeft)
		{
			bestRight = index;
			bestLeft = currentLeft;
		}

    	return s.substring (
    		bestLeft,
    		bestRight
    	);
    }

    /**
     * Given a string of '(' , ')' and lowercase English characters, remove the minimum number of parentheses
     *  ( '(' or ')', in any positions ) so that the resulting parentheses string is valid and return any
     *  valid string.
     *  
     * Formally, a parentheses string is valid if and only if:
     *   It is the empty string, contains only lowercase characters, or
     *   It can be written as AB (A concatenated with B), where A and B are valid strings, or
     *   It can be written as (A), where A is a valid string.
     * 
     * @param s The Input String
     * 
     * @return The Valid Parenthesis
     */

    public static final java.lang.String InvalidParenthesisMinimalRemove (
    	final java.lang.String s)
    {
    	java.util.List<java.lang.Integer> leftParenthesisLocationList =
    		new java.util.ArrayList<java.lang.Integer>();

    	java.util.List<java.lang.Integer> rightParenthesisLocationList =
    		new java.util.ArrayList<java.lang.Integer>();

    	for (int i = 0; i < s.length(); ++i) {
    		char c = s.charAt (i);

    		if (c == '(')
    			leftParenthesisLocationList.add (i);
    		else if (c == ')')
    			rightParenthesisLocationList.add (i);
    	}

    	for (int i = leftParenthesisLocationList.size() - 1; i >= 0; --i) {
    		for (int j = 0; j < rightParenthesisLocationList.size(); ++j) {
    			if (rightParenthesisLocationList.get (j) > leftParenthesisLocationList.get (i)) {
    				leftParenthesisLocationList.remove (i);

    				rightParenthesisLocationList.remove (j);

    				break;
    			}
    		}
    	}

    	java.util.Set<java.lang.Integer> trimLocationSet = new java.util.HashSet<java.lang.Integer>();

    	trimLocationSet.addAll (leftParenthesisLocationList);

    	trimLocationSet.addAll (rightParenthesisLocationList);

    	java.lang.String validParenthesis = "";

    	for (int i = 0; i < s.length(); ++i) {
    		if (!trimLocationSet.contains(i)) validParenthesis += s.charAt (i);
    	}

    	return validParenthesis;
    }

    /**
     * Given a string of '(' and ')' parentheses, add the minimum number of parentheses ( '(' or ')', and in
     *  any positions ) so that the resulting parentheses string is valid.
     *  
     * Formally, a parentheses string is valid if and only if:
     * 
     *  It is the empty string, or
     *  It can be written as AB (A concatenated with B), where A and B are valid strings, or
     *  It can be written as (A), where A is a valid string.
     *  
     * Given a parentheses string, return the minimum number of parentheses one must add to make the
     *  resulting string valid.
     * 
     * @param s Input String
     * 
     * @return Minimum Number of Parentheses to be added
     */

    public static final int InvalidParenthesisMinimalAdd (
    	final java.lang.String s)
    {
    	java.util.List<java.lang.Integer> invalidLeftParenthesisList =
    		new java.util.ArrayList<java.lang.Integer>();

    	java.util.List<java.lang.Integer> invalidRightParenthesisList =
    		new java.util.ArrayList<java.lang.Integer>();

    	for (int i = 0; i < s.length(); ++i) {
    		char c = s.charAt (i);

    		if (c == '(')
    			invalidLeftParenthesisList.add (i);
    		else if (c == ')') {
    			if (invalidLeftParenthesisList.isEmpty())
    				invalidRightParenthesisList.add (i);
    			else
    				invalidLeftParenthesisList.remove (invalidLeftParenthesisList.size() - 1);
    		}
    	}

    	return invalidLeftParenthesisList.size() + invalidRightParenthesisList.size();
    }

    /**
     * Break the given string into words, provided by a given hash-map of frequency of word as word : frequency.
     * 
     * @param wordFrequencyMap Word Frequency Map
     * @param s Input String
     * 
     * @return Broken Down Sequence of Words
     */

    public static final java.util.List<java.lang.String> FrequencyBasedWordDecomposition (
    	final java.util.Map<java.lang.String, java.lang.Integer> wordFrequencyMap,
    	final java.lang.String s)
    {
    	java.util.List<java.lang.String> frequencyBasedWordDecomposition = new
    		java.util.ArrayList<java.lang.String>();

    	java.util.List<java.util.Map<java.lang.String, java.lang.Integer>> frequencyUsageMapQueue = new
    		java.util.ArrayList<java.util.Map<java.lang.String, java.lang.Integer>>();

    	java.util.List<java.lang.String> decompositionQueue = new java.util.ArrayList<java.lang.String>();

    	java.util.List<java.lang.Integer> indexQueue = new java.util.ArrayList<java.lang.Integer>();

    	java.util.Map<java.lang.String, java.lang.Integer> frequencyUsageMapInitial =
			new java.util.HashMap<java.lang.String, java.lang.Integer>();

    	frequencyUsageMapInitial.putAll (wordFrequencyMap);

    	frequencyUsageMapQueue.add (frequencyUsageMapInitial);

    	decompositionQueue.add ("");

    	indexQueue.add (0);

    	while (!indexQueue.isEmpty()) {
    		int queueIndex = indexQueue.size() - 1;

    		int stringIndex = indexQueue.remove (queueIndex);

    		java.lang.String decomposedWord = decompositionQueue.remove (queueIndex);

    		java.util.Map<java.lang.String, java.lang.Integer> frequencyUsageMap =
    			frequencyUsageMapQueue.remove (queueIndex);

    		if (stringIndex >= s.length()) {
    			frequencyBasedWordDecomposition.add (decomposedWord);

    			continue;
    		}

    		java.lang.String sCurrent = s.substring (stringIndex);

    		for (java.lang.String wordKey : frequencyUsageMap.keySet()) {
    			if (sCurrent.startsWith (wordKey) && 0 != frequencyUsageMap.get(wordKey)) {
    				indexQueue.add (stringIndex + wordKey.length());

    				decompositionQueue.add(decomposedWord + " " + wordKey);

    		    	java.util.Map<java.lang.String, java.lang.Integer> frequencyUsageMapNext =
    					new java.util.HashMap<java.lang.String, java.lang.Integer>();

    		    	frequencyUsageMapNext.putAll (frequencyUsageMap);

    		    	frequencyUsageMapNext.put (wordKey, frequencyUsageMapNext.get(wordKey) - 1);

    		    	frequencyUsageMapQueue.add (frequencyUsageMapNext);
    			}
    		}
    	}

    	return frequencyBasedWordDecomposition;
    }

    private static final boolean CompareCharHashMaps (
    	final java.util.HashMap<java.lang.Character, java.lang.Integer> charHashMap1,
    	final java.util.HashMap<java.lang.Character, java.lang.Integer> charHashMap2)
    {
    	java.util.Set<java.lang.Character> mergedCharKeySet = new java.util.HashSet<java.lang.Character>();

    	mergedCharKeySet.addAll (charHashMap1.keySet());

    	mergedCharKeySet.addAll (charHashMap2.keySet());

    	for (char charKey : mergedCharKeySet) {
    		if (!charHashMap1.containsKey (charKey) || !charHashMap1.containsKey (charKey) ||
    			charHashMap1.get (charKey) != charHashMap2.get (charKey))
    			return false;
    	}

    	return true;
    }

    private static final java.util.HashMap<java.lang.Character, java.lang.Integer> CharHashMap (
    	final java.lang.String s)
    {
    	java.util.HashMap<java.lang.Character, java.lang.Integer> charHashMap = new
    		java.util.HashMap<java.lang.Character, java.lang.Integer>();

    	for (char c : s.toCharArray()) {
    		if (charHashMap.containsKey (c))
    			charHashMap.put (c, charHashMap.get (c) + 1);
    		else
    			charHashMap.put (c, 1);
    	}

    	return charHashMap;
    }

    /**
     * Given an array of words, group anagrams together.
     * 
     * @param wordArray Array of Words
     * 
     * @return The Anagram Group List
     */

    public static final java.util.List<java.util.List<java.lang.String>> GroupAnagrams2 (
    	final java.lang.String[] wordArray)
    {
    	java.util.List<java.util.HashMap<java.lang.Character, java.lang.Integer>> charHashMapList = new
    		java.util.ArrayList<java.util.HashMap<java.lang.Character, java.lang.Integer>>();

    	java.util.List<java.util.List<java.lang.String>> anagramListList = new
    		java.util.ArrayList<java.util.List<java.lang.String>>();

    	charHashMapList.add (CharHashMap (wordArray[0]));

    	java.util.List<java.lang.String> anagramList = new java.util.ArrayList<java.lang.String>();

    	anagramList.add (wordArray[0]);

    	anagramListList.add (anagramList);

    	for (int i = 1; i < wordArray.length; ++i) {
        	int anagramIndex = -1;

    		java.util.HashMap<java.lang.Character, java.lang.Integer> charHashMap = CharHashMap
    			(wordArray[i]);

    		for (int j = 0; j < charHashMapList.size(); ++j) {
    			if (CompareCharHashMaps (charHashMap, charHashMapList.get (j))) {
    				anagramIndex = j;
    				break;
    			}
    		}

        	if (-1 != anagramIndex) {
        		anagramListList.get (anagramIndex).add (wordArray[i]);
        	} else {
            	charHashMapList.add (charHashMap);

            	java.util.List<java.lang.String> newAnagramList = new
            		java.util.ArrayList<java.lang.String>();

            	newAnagramList.add (wordArray[i]);

            	anagramListList.add (newAnagramList);
        	}
    	}

    	return anagramListList;
    }

    /**
     * Given an array of words, group anagrams together.
     * 
     * @param wordArray Array of Words
     * 
     * @return The Anagram Group List
     */

    public static final java.util.List<java.util.List<java.lang.String>> GroupAnagrams (
    	final java.lang.String[] wordArray)
    {
    	java.util.HashMap<java.lang.String, java.util.List<java.lang.String>> anagramListMap = new
    		java.util.HashMap<java.lang.String, java.util.List<java.lang.String>>();

    	for (int i = 0; i < wordArray.length; ++i) {
    		char[] charArray = wordArray[i].toCharArray();

    		java.util.Arrays.sort (charArray);

    		java.lang.String anagram = new java.lang.String (charArray);

    		if (anagramListMap.containsKey (anagram))
    			anagramListMap.get (anagram).add (wordArray[i]);
    		else {
    			java.util.List<java.lang.String> anagramList = new java.util.ArrayList<java.lang.String>();

    			anagramList.add (wordArray[i]);

    			anagramListMap.put (anagram, anagramList);
    		}
    	}

    	java.util.List<java.util.List<java.lang.String>> anagramList = new
    		java.util.ArrayList<java.util.List<java.lang.String>>();

    	for (java.util.Map.Entry<java.lang.String, java.util.List<java.lang.String>> anagramListEntry :
    		anagramListMap.entrySet())
    		anagramList.add(anagramListEntry.getValue());

    	return anagramList;
    }

    /**
     * Company A has Fulfillment Centers in multiple cities within a large geographic region. The cities are
     * 	arranged on a graph that has been divided up like an ordinary Cartesian plane. Each city is located
     *  at an integral (x, y) coordinate intersection. City names and locations are given in the form of
     *  three arrays: c, x, and y, which are aligned by the index to provide the city name (c[i]), and its
     *  coordinates, (x[i], y[i]).
     *  
     *  Write an algorithm to determine the name of the nearest city that shares either an x or a y
     *   coordinate with the queried city. If no other cities share an xory coordinate, return NONE. If two
     *   cities have the same distance to the queried city, q[i], consider the one with an alphabetically
     *   smaller name (i.e. 'ab' lt 'aba' lt 'abb') as the closest choice.
     *   
     * The distance is denoted on a Euclidean plane: the difference in x plus the difference in y.
     * 
     * @param cities Array of Cities
     * @param xCoordinateArray Array of City X Coordinates
     * @param yCoordinateArray Array of City Y Coordinates
     * @param queryCities Cities to be Queried
     * 
     * @return Array of the Nearest Cities
     */

    public static final java.lang.String[] NearestCities (
    	final java.lang.String[] cities,
    	final int[] xCoordinateArray,
    	final int[] yCoordinateArray,
    	final java.lang.String[] queryCities)
    {
    	java.lang.String[] nearestCities = new java.lang.String[queryCities.length];

    	java.util.HashMap<java.lang.String, java.lang.Integer> cityIndexMap =
    		new java.util.HashMap<java.lang.String, java.lang.Integer>();

    	for (int j = 0; j < cities.length; ++j)
    		cityIndexMap.put (cities[j], j);

    	for (int i = 0; i < queryCities.length; ++i) {
    		nearestCities[i] = "NONE";

    		int queryCityIndex = cityIndexMap.get (queryCities[i]);

        	for (int j = 0; j < cities.length;++j) {
        		if (i == j) continue;

        		if (xCoordinateArray[j] == xCoordinateArray[queryCityIndex] ||
        			yCoordinateArray[j] == yCoordinateArray[queryCityIndex]) {
        			if ("NONE".equalsIgnoreCase (nearestCities[i]))
        				nearestCities[i] = cities[j];
        			else {
        				if (1 == nearestCities[i].compareTo (cities[j])) nearestCities[i] = cities[j];
        			}
        		}
        	}
    	}

    	return nearestCities;
    }

    private static final boolean IsPrime (
    	final String number)
    {
    	int decimalNumber = DecimalNumberFromString (number);

    	return -1 != decimalNumber && org.drip.numerical.common.PrimeUtil.IsPrime (decimalNumber);
    }

    /**
     * A company's operations team needs an algorithm that can break out a list of products for a given
     * 	order. The products in the order are listed as a string and the order items are represented as prime
     *  numbers. Given a string consisting of digits [0-9], count the number of ways the given string can be
     *  split into prime numbers, which represent unique items in the order. The digits must remain in the
     *  order given and the entire string must be used.
     *  
     * Write an algorithm to find the number of ways the given string can be split into unique prime numbers
     *  using the entire string.
     *  
     * @param number The Input Number
     * 
     * @return The Sequence List of Unique Primes
     */

    public static final java.util.List<java.util.List<String>> SplitIntoUniquePrimes (
    	final String number)
    {
    	int numberLength = number.length();

		java.util.List<java.util.List<String>> primeSequenceList = new
			java.util.ArrayList<java.util.List<String>>();

		if (IsPrime (number)) {
			java.util.List<String> primeList = new java.util.ArrayList<String>();

			primeList.add (number);

			primeSequenceList.add (primeList);
		}

    	if (1 == numberLength) return primeSequenceList;

    	for (int i = 1; i < numberLength; ++i) {
    		String leftSubstring = number.substring (0, i);

    		if (!IsPrime (leftSubstring)) continue;

    		java.util.List<java.util.List<String>> uniquePrimesSequenceList = SplitIntoUniquePrimes
    			(number.substring (i, numberLength));

    		if (!uniquePrimesSequenceList.isEmpty()) {
    			for (java.util.List<String> uniquePrimesList : uniquePrimesSequenceList)
    				uniquePrimesList.add (0, leftSubstring);
    		}

    		primeSequenceList.addAll (uniquePrimesSequenceList);
    	}

    	return primeSequenceList;
    }

    /**
     * Given a string s of lower-case letters, partition s into as many as parts so that one letter only
     * 	appear in one part. Return the partitions as a list.
     * 
     * @param s The Input String
     * 
     * @return List of Same Character Partitions
     */

    public static final java.util.List<String> CollectSameCharacters (
    	final String s)
    {
    	java.util.HashMap<Character, int[]> charRangeMap = new java.util.HashMap<Character, int[]>();

    	for (int i = 0; i < s.length(); ++i) {
    		char c = s.charAt (i);

    		if (!charRangeMap.containsKey (c))
    			charRangeMap.put (c, new int[] {i, i});
    		else {
    			int[] range = charRangeMap.get (c);

    			range[1] = i;
    		}
    	}

    	java.util.TreeMap<Integer, int[]> sortedRangeMap = new java.util.TreeMap<Integer, int[]>();

    	for (int[] range : charRangeMap.values())
    		sortedRangeMap.put (range[0], range);

    	int[] prevRange = null;

    	java.util.TreeMap<Integer, int[]> nonOverlappingRangeMap = new java.util.TreeMap<Integer, int[]>();

    	for (int rangeLeft : sortedRangeMap.keySet()) {
    		int[] currentRange = sortedRangeMap.get (rangeLeft);

    		if (null == prevRange) {
    			nonOverlappingRangeMap.put (rangeLeft, prevRange = currentRange);

    			continue;
    		}

    		if (!RangesOverlap (prevRange, currentRange))
    			nonOverlappingRangeMap.put (rangeLeft, prevRange = currentRange);
    		else
    			nonOverlappingRangeMap.put (prevRange[0], prevRange = new int[] {prevRange[0],
    				currentRange[1] > prevRange[1] ? currentRange[1] : prevRange[1]});
    	}

    	java.util.List<String> partitionList = new java.util.ArrayList<String>();

    	for (int rangeLeft : nonOverlappingRangeMap.keySet())
    		partitionList.add (s.substring (rangeLeft, nonOverlappingRangeMap.get (rangeLeft)[1] + 1));

    	return partitionList;
    }

    /**
     * Given a string of lower characters, remove at most two substrings of any length from the given string
     *  such that the remaining string contains vowels('a','e','i','o','u') only.
     *  
     * Your aim is to maximize the length of the remaining string. Output the length of remaining string
     * 	after removal of at most two substrings.
     * 
     * @param s Input String
     * 
     * @return Longest String after Removal
     */

    public static final String LongestVowel (
    	final String s)
    {
    	java.util.List<Integer> consonantLocationList = new java.util.ArrayList<Integer>();

    	for (int i = 0; i < s.length(); ++i) {
    		if (IsConsonant (s.charAt (i))) consonantLocationList.add (i);
    	}

    	int consonantCount = consonantLocationList.size();

    	if (2 >= consonantCount) {
    		String longestVowel = "";

        	for (int i = 0; i < s.length(); ++i) {
        		if (!consonantLocationList.contains (i)) longestVowel = longestVowel + s.charAt (i);
        	}

        	return longestVowel;
    	}

    	String longestVowel = "";
    	int optimalLeftStringEndIndex = -1;
    	int optimalRightStringStartIndex = -1;
    	int minimalSnippedStringSize = java.lang.Integer.MAX_VALUE;

    	int firstConsonantIndex = consonantLocationList.get (0);

    	int lastConsonantIndex = consonantLocationList.get (consonantCount - 1);

    	for (int leftStringEndLocation = 0; leftStringEndLocation < consonantCount - 1;
    		++leftStringEndLocation) {
    		int leftStringEndIndex = consonantLocationList.get (leftStringEndLocation);

			int rightStringStartIndex = consonantLocationList.get (leftStringEndLocation + 1);

			int snippedStringSize = leftStringEndIndex - firstConsonantIndex + lastConsonantIndex -
				rightStringStartIndex + 2;

    		if (-1 == optimalLeftStringEndIndex && -1 == optimalRightStringStartIndex) {
    			minimalSnippedStringSize = snippedStringSize;
    			optimalLeftStringEndIndex = leftStringEndIndex;
    			optimalRightStringStartIndex = rightStringStartIndex;
    			continue;
    		}

    		if (minimalSnippedStringSize > snippedStringSize) {
    			minimalSnippedStringSize = snippedStringSize;
    			optimalLeftStringEndIndex = leftStringEndIndex;
    			optimalRightStringStartIndex = rightStringStartIndex;
    		}
    	}

    	if (0 != firstConsonantIndex) longestVowel = longestVowel + s.substring (0, firstConsonantIndex);

		longestVowel = longestVowel + s.substring (optimalLeftStringEndIndex + 1,
			optimalRightStringStartIndex);

    	if (s.length() - 1 != lastConsonantIndex)
    		longestVowel = longestVowel + s.substring (lastConsonantIndex + 1);

    	return longestVowel;
    }

    /**
     * Given a, b, c, find any string of maximum length that can be created such that no 3 consecutive
     *  characters are same. There can be at max a 'a', b 'b' and c 'c'.
     * 
     * @param a Count of the Number of 'A's
     * @param b Count of the Number of 'B's
     * @param c Count of the Number of 'C's
     * 
     * @return The Longest Word
     */

    public static final String ConstrainedWord (
    	final int a,
    	final int b,
    	final int c)
    {
    	int[] charCountArray = new int[(int) 'c' + 1];
    	charCountArray[(int) 'a'] = a;
    	charCountArray[(int) 'b'] = b;
    	charCountArray[(int) 'c'] = c;

    	char ch = CharWithLargestCount (charCountArray);

    	String constrainedWord = "" + ch;

    	DecrementCharCount (charCountArray, ch);

    	constrainedWord = constrainedWord + (ch = CharWithLargestCount (charCountArray));

    	DecrementCharCount (charCountArray, ch);

    	while (!EmptyCharCountArray (charCountArray)) {
    		ch = CharWithLargestCount (charCountArray);

    		if (constrainedWord.substring (constrainedWord.length() - 2).equalsIgnoreCase ("" + ch + ch)) {
    			if (SingleCharacterArray (charCountArray)) break;

    			ch = CharWithMediumCount (charCountArray);
    		}

			constrainedWord = constrainedWord + ch;

	    	DecrementCharCount (charCountArray, ch);
    	}

    	return constrainedWord;
    }

    /**
     * Given a string s of lower-case letters, find as many sub-strings as possible that meet the following
     * 	criteria:
     * 
     *  - no overlap among strings
     *  - one letter can only exist in one string. For every letter c in the sub-string, all instances of c
     *  	must also be in the sub-string
     *  - find as many sub-strings as possible
     *  - if there are two solutions with the same number of sub-strings, return the one with the smaller
     *  	total length.
     *  
     * Return sub-strings as a list.
     * 
     * @param s Input String
     * 
     * @return The Conditional Word List
     */

    public static final java.util.List<String> ConditionalWordList (
    	final String s)
    {
    	java.util.HashMap<Character, java.util.ArrayList<Integer>> charIndexListMap = new
    		java.util.HashMap<Character, java.util.ArrayList<Integer>>();

    	char[] charArray = s.toCharArray();

    	for (int i = 0; i < s.length(); ++i) {
    		char c = charArray[i];

    		if (charIndexListMap.containsKey (c))
    			charIndexListMap.get (c).add (i);
    		else {
    			java.util.ArrayList<Integer> charIndexList = new java.util.ArrayList<Integer>();

    			charIndexList.add (i);

    			charIndexListMap.put (c, charIndexList);
    		}
    	}

    	for (java.util.ArrayList<Integer> charIndexList : charIndexListMap.values()) {
    		boolean discontinuousCharLocation = false;

    		int prevIndex = charIndexList.get (0);

    		for (int i = 1; i < charIndexList.size(); ++i) {
    			int currentIndex = charIndexList.get (i);

    			if (currentIndex != prevIndex + 1) {
    				discontinuousCharLocation = true;
    				break;
    			}

    			prevIndex = currentIndex;
    		}

    		if (discontinuousCharLocation) {
    			for (int charIndex : charIndexList)
    				charArray[charIndex] = '_';
    		}
    	}

    	char prevChar = charArray[0];
    	String sModified = "" + prevChar;

    	for (int i = 1; i < charArray.length; ++i) {
			sModified = sModified + (prevChar == charArray[i] || prevChar == '_' || charArray[i] == '_' ? ""
				: "_") + charArray[i];
    		prevChar = charArray[i];
    	}

    	while (sModified.startsWith ("_"))
    		sModified = sModified.substring (1);

    	while (sModified.endsWith ("_"))
    		sModified = sModified.substring (0, sModified.length() - 1);

    	String[] conditionalWordArray = sModified.split ("_");

    	java.util.List<String> conditionalWordList = new java.util.ArrayList<String>();

    	for (String conditionalWord : conditionalWordArray)
    		conditionalWordList.add (conditionalWord);

    	return conditionalWordList;
    }

    private static final boolean IsPalindrome (
    	final String s,
    	final int start,
    	final int end)
    {
    	int left = start;
    	int right = end;

    	while (left < right)
    	{
    		if (s.charAt (left) != s.charAt(right))
			{
    			return false;
			}

    		++left;
    		--right;
    	}

    	return true;
    }

    private static final Map<Character, List<Integer>> CharacterLocationListMap (
		final String s)
    {
    	Map<Character, List<Integer>> characterLocationListMap = new HashMap<Character, List<Integer>>();

    	for (int i = 0; i < s.length(); ++i)
    	{
    		char ch = s.charAt (i);

    		if (characterLocationListMap.containsKey (ch))
    		{
    			int lastLocation = characterLocationListMap.get (ch).get (
					characterLocationListMap.get (ch).size() - 1
				);

    			if (0 == (i - lastLocation) % 2)
				{
    				characterLocationListMap.get (ch).add (i);
				}
    		}
    		else
    		{
    			List<Integer> locationList = new ArrayList<Integer>();

    			locationList.add(i);

    			characterLocationListMap.put (ch, locationList);
    		}
    	}

    	return characterLocationListMap;
    }

    private static final boolean Overlap (
		final int[] firstRange,
		final int[] secondRange)
    {
    	if (firstRange[0] >= secondRange[0] && firstRange[0] <= secondRange[1])
		{
    		return true;
		}

    	return secondRange[0] >= firstRange[0] && secondRange[0] <= firstRange[1];
    }

    private static final int MaximumNonOverlappingProduct (
		final List<int[]> palindromeLocationList)
    {
    	if (null == palindromeLocationList || 1 >= palindromeLocationList.size())
		{
    		return -1;
		}

    	int maximumNonOverlappingProduct = -1;

    	for (int i = 0; i < palindromeLocationList.size(); ++i)
    	{
    		int[] leftPalindromeLocation = palindromeLocationList.get(i);

    		for (int j = 0; j < i; ++j)
    		{
        		int[] rightPalindromeLocation = palindromeLocationList.get(j);

        		if (!Overlap (leftPalindromeLocation, rightPalindromeLocation))
        		{
        			int currentProduct = (leftPalindromeLocation[1] - leftPalindromeLocation[0] + 1) *
    					(rightPalindromeLocation[1] - rightPalindromeLocation[0] + 1);

        			if (currentProduct > maximumNonOverlappingProduct)
    				{
        				maximumNonOverlappingProduct = currentProduct;
    				}
        		}
        	}
    	}

    	return maximumNonOverlappingProduct;
    }

    public static final int MaximumPalindromeProductLength (
		final String s)
    {
    	Map<Character, List<Integer>> characterLocationListMap = CharacterLocationListMap (s);

    	List<int[]> palindromeLocationList = new ArrayList<int[]>();

    	for (char ch : characterLocationListMap.keySet())
    	{
    		List<Integer> characterLocationList = characterLocationListMap.get(ch);

    		if (2 > characterLocationList.size()) continue;

    		for (int i = 0; i < characterLocationList.size(); ++i)
    		{
        		for (int j = i + 1; j < characterLocationList.size(); ++j)
        		{
        			if (IsPalindrome (s, characterLocationList.get (i), characterLocationList.get (j)))
        			{
        				palindromeLocationList.add (
    						new int[] {characterLocationList.get (i), characterLocationList.get (j)}
						);
        			}
        		}
    		}
    	}

    	return MaximumNonOverlappingProduct (palindromeLocationList);
    }

    private static final List<Integer> SubstringStartLocation (String full, String sub)
    {
    	List<Integer> substringStartLocationList = new ArrayList<Integer>();

    	int startIndex = full.indexOf(sub, 0);

    	while (startIndex < full.length() && -1 != startIndex)
    	{
    		substringStartLocationList.add (startIndex);

    		startIndex = full.indexOf(sub, startIndex + sub.length());
    	}

    	return substringStartLocationList;
    }

    private static final List<Integer> StartLocationList (String full, Set<String> subSet)
    {
    	List<Integer> startLocationList = new ArrayList<Integer>();

    	for (String sub : subSet)
    	{
    		List<Integer> substringStartLocationList = SubstringStartLocation (full, sub);

    		if (substringStartLocationList.isEmpty()) return new ArrayList<Integer>();

    		startLocationList.addAll(substringStartLocationList);
    	}

    	return startLocationList;
    }

    private static final List<Integer> ContiguousWordsLocationList (
		String full,
		Set<String> subset,
		int startIndex)
    {
    	List<Integer> contiguousWordsLocationList = new ArrayList<Integer>();

    	List<Integer> currentLocationStack = new ArrayList<Integer>();

    	currentLocationStack.add(startIndex);

    	List<Set<String>> processedSubsetStack = new ArrayList<Set<String>>();

    	processedSubsetStack.add(new HashSet<String> (subset));

    	while (!currentLocationStack.isEmpty())
    	{
    		int stackIndex = currentLocationStack.size() - 1;
 
    		int currentLocation = currentLocationStack.get(stackIndex);

    		currentLocationStack.remove(stackIndex);

    		String currentFull = full.substring(currentLocation);

    		Set<String> currentSubset = processedSubsetStack.get(stackIndex);

    		Set<String> nextSubset = new HashSet<String> (currentSubset);

    		processedSubsetStack.remove(stackIndex);

    		for (String currentSub : currentSubset) {
    			if (currentFull.startsWith (currentSub)) {
    				nextSubset.remove(currentSub);

    				if (nextSubset.isEmpty())
    					contiguousWordsLocationList.add(startIndex);
    				else {
    					int nextLocation = currentLocation + currentSub.length();

    					if (nextLocation < full.length())
    					{
    						currentLocationStack.add(nextLocation);

	    					processedSubsetStack.add(nextSubset);
    					}
    				}
    			}
    		}
    	}

    	return contiguousWordsLocationList;
    }

    public static final List<Integer> WordConcatenationStartList (String full, Set<String> subset)
    {
    	List<Integer> wordConcatenationStartList = new ArrayList<Integer>();

    	List<Integer> startLocationList = StartLocationList (full, subset);

    	if (startLocationList.isEmpty()) return wordConcatenationStartList;

    	for (int startIndex : startLocationList)
	    	wordConcatenationStartList.addAll (ContiguousWordsLocationList (full, subset, startIndex));

	    return wordConcatenationStartList;
    }

    static class PasswordSynposys
    {
    	int charShortfall = 0;
    	boolean containsDigit = false;
    	boolean containsLower = false;
    	boolean containsUpper = false;
    	List<int[]> invalidRange = null;

    	PasswordSynposys (
			int charShortfall,
			boolean containsDigit,
			boolean containsLower,
			boolean containsUpper,
			List<int[]> invalidRange)
    	{
    		this.charShortfall = charShortfall;
    		this.containsDigit = containsDigit;
    		this.containsLower = containsLower;
    		this.containsUpper = containsUpper;
    		this.invalidRange = invalidRange;
    	}
    }

    private static final PasswordSynposys IsPasswordStrong (
    	final String password)
    {
    	char charState = '0';
    	int charShortfall = 0;
    	int repeatCharCount = 0;
    	boolean containsDigit = false;
    	boolean containsLower = false;
    	boolean containsUpper = false;

    	int passwordLength = password.length();

    	if (20 < passwordLength)
    		charShortfall = 20 - passwordLength;
    	else if (6 > passwordLength)
    		charShortfall = 6 - passwordLength;

    	List<int[]> invalidRange = new ArrayList<int[]>();

    	for (int i = 0; i < passwordLength; ++i) {
    		char ch = password.charAt (i);

    		if (Character.isDigit (ch)) containsDigit = true;

    		if (Character.isLowerCase (ch)) containsLower = true;

    		if (Character.isUpperCase (ch)) containsUpper = true;

    		if (0 == i)
    			charState = ch;
    		else {
    			if (ch == charState)
    				++repeatCharCount;
    			else {
    				if (3 <= repeatCharCount) invalidRange.add (new int[] {i - repeatCharCount + 1, i});

    				charState = ch;
        			repeatCharCount = 1;
    			}
    		}
    	}

    	return new PasswordSynposys (
			charShortfall,
			containsDigit,
			containsLower,
			containsUpper,
			invalidRange
		);
    }

    public static final int PasswordChangeSteps (String password)
    {
    	PasswordSynposys passwordSynposys = IsPasswordStrong (password);

    	if (0 < passwordSynposys.charShortfall) return passwordSynposys.charShortfall;

		int repeatsToModify = 0;
		int containmentChanges = 0;

		for (int[] range : passwordSynposys.invalidRange)
    		repeatsToModify += (range[1] - range[0] + 1) / 3;

		if (!passwordSynposys.containsDigit) ++containmentChanges;

		if (!passwordSynposys.containsLower) ++containmentChanges;

		if (!passwordSynposys.containsUpper) ++containmentChanges;

		return (repeatsToModify > containmentChanges ? repeatsToModify : containmentChanges) - passwordSynposys.charShortfall;
    }

    private static final boolean IsNumber (
		String numberString)
    {
    	if (null == numberString || numberString.isEmpty()) return false;

    	char leadingChar = numberString.charAt (0);

    	if ('+' == leadingChar || '-' == leadingChar) numberString = numberString.substring (1);

    	for (int i = 0; i < numberString.length(); ++i) {
    		char currentChar = numberString.charAt (i);

    		if (!Character.isDigit(currentChar) && currentChar != '.') return false;
    	}

    	return !".".equals (numberString);
    }

    /*
     * A <b>valid number</b> can be split up into these components (in order):
     * 
     *  A <b>decimal number</b> or an <b>integer</b>.
     *  (Optional) An 'e' or 'E', followed by an <b>integer</b>.
     *  
     * A <b>decimal number</b> can be split up into these components (in order):
     * 
     *  (Optional) A sign character (either '+' or '-').
     *  One of the following formats:
     *   One or more digits, followed by a dot '.'.
     *   One or more digits, followed by a dot '.', followed by one or more digits.
     *   A dot '.', followed by one or more digits.
     *   
     * An <b>integer</b> can be split up into these components (in order):
     * 
     *  (Optional) A sign character (either '+' or '-').
     *  One or more digits.
     *  
     * For example, all the following are valid numbers: ["2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10",
     *  "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789"], while the following are not valid numbers:
     *  ["abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53"].
     * 
     * Given a string s, return true if s is a <b>valid number</b>.
     * 
     * @param s The Input Number
     * 
     * @return TRUE - Number is Valid
     */

    public static final boolean ValidateNumber (
    	final String s)
    {
    	String sLower = s.toLowerCase();

    	if (!sLower.contains ("e")) return IsNumber (sLower);

    	String[] numberArray = sLower.split ("e");

    	return null != numberArray && 2 == numberArray.length &&
			IsNumber (numberArray[0]) && IsNumber (numberArray[1]);
    }

    public static final void main (
		final String[] argumentArray)
	{
    	System.out.println (ValidateNumber ("0"));

    	System.out.println (ValidateNumber ("e"));

    	System.out.println (ValidateNumber ("."));

    	System.out.println (ValidateNumber (".1"));
	}
}
