
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

	private static final boolean IsPalindrome (
		final char[] charArray,
		int startIndex,
		int endIndex)
	{
		while (endIndex > startIndex)
		{
			if (charArray[endIndex--] != charArray[startIndex++])
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
	 * @return The longest palindromic substring
	 */

	public static final java.lang.String LongestPalindromicSubstring (
		final java.lang.String s)
	{
		char[] charArray = s.toCharArray();

		int beginIndex = 0;
		int longestPalindromeSize = 0;
		int stringLength = charArray.length;
		java.lang.String longestPalindromicSubstring = "";

		while (beginIndex + longestPalindromeSize < stringLength)
		{
			int endIndex = stringLength - 1;

			while (endIndex > beginIndex + longestPalindromeSize)
			{
				if (IsPalindrome (
					charArray,
					beginIndex,
					endIndex
				))
				{
					java.lang.String palindrome = s.substring (
						beginIndex,
						endIndex + 1
					);

					int palindromeSize = palindrome.length();

					if (palindromeSize > longestPalindromeSize)
					{
						longestPalindromicSubstring = palindrome;
						longestPalindromeSize = palindromeSize;
					}
				}

				--endIndex;
			}

			++beginIndex;
		}

		return longestPalindromicSubstring;
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

		for (char c : t.toCharArray())
		{
			if (tCharacterCountMap.containsKey (c))
				tCharacterCountMap.put (c, tCharacterCountMap.get (c) + 1);
			else
				tCharacterCountMap.put (c, 1);
		}

		int leftIndex = 0;
		int minLeftIndex = 0;
		int matchCharCount = 0;
		int minLen = sLength + 1;

		for (int rightIndex = 0; rightIndex < sLength; ++rightIndex)
		{
			char rightChar = s.charAt (rightIndex);

			if (tCharacterCountMap.containsKey (rightChar))
			{
				int rightSideCharacterCount = tCharacterCountMap.get (rightChar);

				tCharacterCountMap.put (rightChar, rightSideCharacterCount - 1);

				if (rightSideCharacterCount > 0) ++matchCharCount;

				while (matchCharCount == tLength)
				{
					if (rightIndex - leftIndex + 1 < minLen)
					{
						minLeftIndex = leftIndex;
						minLen = rightIndex - leftIndex + 1;
					}

					char leftChar = s.charAt (leftIndex);

					if (tCharacterCountMap.containsKey (leftChar))
					{
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

	public static final void main (
		final String[] argumentArray)
	{
		System.out.println (
			DecodeStringAtIndex (
				"leet2code3",
				10
			)
		);

		System.out.println (
			DecodeStringAtIndex (
				"ha22",
				5
			)
		);

		System.out.println (
			DecodeStringAtIndex (
				"a2345678999999999999999",
				1
			)
		);
	}
}
