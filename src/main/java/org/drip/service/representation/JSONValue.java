
package org.drip.service.representation;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.drip.service.jsonparser.LexicalProcessor;
import org.drip.service.jsonparser.ParseException;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>JSONValue</i> is an Adaptation of the JSONValue Class from the RFC4627 compliant JSON Simple
 * 	(https://code.google.com/p/json-simple/). It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Implement "escape" on the String and copy it into the Buffer</li>
 * 		<li>Parse JSON text into java object from the input source. Please use parseWithException() if you don't want to ignore the exception</li>
 * 		<li>Parse the Input String into an Object</li>
 * 		<li>Parse JSON text into java object from the input source</li>
 * 		<li>Parse JSON text into java object from the input string</li>
 * 		<li>Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F)</li>
 * 		<li>Encode an object into JSON text and write it to writer</li>
 * 		<li>Convert an object to JSON text</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/representation/README.md">RFC4627 Compliant JSON Message Object</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Fang Yidong
 * @author Lakshmi Krishnamurthy
 */

public class JSONValue
{

	/**
	 * Implement "escape" on the String and copy it into the Buffer
	 * 
	 * @param s - Must not be null
	 * @param stringBuffer The StringBuffer
	 */

	static void escape (
		final String s,
		final StringBuffer stringBuffer)
	{
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);

            switch (ch) {
                case '"':
                	stringBuffer.append ("\\\"");

                	break;

                case '\\':
                	stringBuffer.append ("\\\\");

                	break;

                case '\b':
                	stringBuffer.append ("\\b");

                	break;

                case '\f':
                	stringBuffer.append ("\\f");

                	break;

                case '\n':
                	stringBuffer.append ("\\n");

                	break;

                case '\r':
                	stringBuffer.append ("\\r");

                	break;

                case '\t':
                	stringBuffer.append ("\\t");

                	break;

                case '/':
                	stringBuffer.append ("\\/");

                	break;

                default:

                	/*
                	 * Reference: http://www.unicode.org/versions/Unicode5.1.0/
                	 */

                	if (('\u0000' <= ch && '\u001F' >= ch) ||
            			('\u007F' <= ch && '\u009F' >= ch) ||
            			('\u2000' <= ch && '\u20FF' >= ch))
                	{
                        String charToHex = Integer.toHexString (ch);

                        stringBuffer.append ("\\u");

                        for (int k = 0; k < 4 - charToHex.length(); ++k) {
                        	stringBuffer.append ('0');
                        }

                        stringBuffer.append (charToHex.toUpperCase());
                    } else {
                    	stringBuffer.append (ch);
                    }
                }
        } //for
    }

	/**
     * Parse JSON text into java object from the input source. Please use parseWithException() if you don't
     * 	want to ignore the exception.
     * 
     * @see LexicalProcessor#parse(Reader)
     * @see #parseWithException(Reader)
     * 
     * @param inStringReader Input String Reader
     * 
     * @return Instance of the following:
     *      org.drip.json.simple.JSONObject,
     *      org.drip.json.simple.JSONArray,
     *      java.lang.String,
     *      java.lang.Number,
     *      java.lang.Boolean,
     *      null
     */

	public static Object parse (
		final StringReader inStringReader)
	{
        try
        {
            return new LexicalProcessor().parse (inStringReader);
        } catch(Exception e) {
            return null;
        }
    }

    /**
     * Parse the Input String into an Object
     * 
     * @param s Input String
     * 
     * @return The Parsed Object
     */

    public static Object parse (
		final String s)
    {
    	return parse (new StringReader(s));
    }

    /**
     * Parse JSON text into java object from the input source.
     * 
     * @see LexicalProcessor
     * 
     * @param inputReader Input Reader
     * 
     * @return Instance of the following:
     *      org.json.simple.JSONObject,
     *      org.json.simple.JSONArray,
     *      java.lang.String,
     *      java.lang.Number,
     *      java.lang.Boolean,
     *      null
     * 
     * @throws IOException Thrown if the Inputs are Invalid
     * 
     * @throws ParseException Thrown if the Inputs are Invalid
     */

    public static Object parseWithException (
		final Reader inputReader)
		throws IOException, ParseException
    {
        return new LexicalProcessor().parse (inputReader);
    }

    /**
     * Parse JSON text into java object from the input string.
     * 
     * @see LexicalProcessor
     * 
     * @param s Input String
     * @return Instance of the following:
     *      org.json.simple.JSONObject,
     *      org.json.simple.JSONArray,
     *      java.lang.String,
     *      java.lang.Number,
     *      java.lang.Boolean,
     *      null
     * 
     * @throws ParseException Thrown if the Inputs are Invalid
     */

    public static Object parseWithException (
		final String s)
		throws ParseException
    {
        return new LexicalProcessor().parse (s);
    }

    /**
     * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F).
     * 
     * @param s Pre-escape String
     * 
     * @return The Escape String
     */

    public static String escape (
		final String s)
    {
        if (null == s) {
            return null;
        }

        StringBuffer stringBuffer = new StringBuffer();

        escape (s, stringBuffer);

        return stringBuffer.toString();
	}

	/**
	 * Encode an object into JSON text and write it to writer.
	 * <p>
	 * If this object is a Map or a List, and it's also a <i>JSONStreamAware</i> or a <i>JSONAware</i>,
	 *  <i>JSONStreamAware</i>, or <i>JSONAware</i> will be considered firstly.
	 * <p>
	 * 
	 * DO NOT call this method from writeJSONString(Writer) of a class that implements both
	 * 	<i>JSONStreamAware</i> and (Map or List) with  "this" as the first parameter, use
	 *  JSONObject.writeJSONString(Map, Writer) or JSONArray.writeJSONString(List, Writer) instead. 
	 * 
	 * @see JSONObject#writeJSONString(Map, Writer)
	 * @see JSONArray#writeJSONString(List, Writer)
	 * 
	 * @param value The JSON Object
	 * @param writer The JSON Writer
	 * 
	 * @throws IOException Thrown if the Inputs are Invalid
	 */

    @SuppressWarnings ("rawtypes") public static void writeJSONString (
		final Object value,
		final Writer writer)
		throws IOException
    {
        if (null == value) {
        	writer.write ("null");

        	return;
        }

        if (value instanceof String) {
	        writer.write ('\"');

	        writer.write (escape ((String) value));

	        writer.write ('\"');

	        return;
        }

        if (value instanceof Double) {
        	Double doubleValue = (Double) value;
 
        	if (doubleValue.isInfinite() || doubleValue.isNaN()) {
                writer.write ("null");
        	} else {
                writer.write (value.toString());
        	}

            return;
        }

        if (value instanceof Float) {
        	Double floatValue = (Double) value;
        	 
            if (floatValue.isInfinite() || floatValue.isNaN()) {
                writer.write ("null");
            } else {
                writer.write (value.toString());
            }

            return;
        }               

        if (value instanceof Number) {
            writer.write (value.toString());

            return;
        }

        if (value instanceof Boolean) {
            writer.write (value.toString());

            return;
        }

        if (value instanceof JSONStreamAware) {
            ((JSONStreamAware) value).writeJSONString (writer);

            return;
        }

        if (value instanceof JSONAware) {
            writer.write (((JSONAware) value).toJSONString());

            return;
        }

        if (value instanceof Map) {
        	JSONObject.writeJSONString ((Map) value, writer);

        	return;
        }

        if (value instanceof List) {
            JSONArray.writeJSONString ((List) value, writer);

            return;
        }

        writer.write (value.toString());
    }

    /**
     * Convert an object to JSON text.
     * <p>
     * If this object is a Map or a List, and it's also a JSONAware, JSONAware will be considered firstly.
     * <p>
     * DO NOT call this method from toJSONString() of a class that implements both JSONAware and Map or List with 
     * "this" as the parameter, use JSONObject.toJSONString(Map) or JSONArray.toJSONString(List) instead. 
     * 
     * @see JSONObject#toJSONString(Map)
     * @see JSONArray#toJSONString(List)
     * 
     * @param value The JSON Object
     * 
     * @return JSON text, or "null" if value is null or it's an NaN or an INF number.
     */

    @SuppressWarnings ("rawtypes") public static String toJSONString (
		final Object value)
    {
        if (null == value) {
            return "null";
        }

        if (value instanceof String) {
            return "\"" + escape ((String) value) + "\"";
        }

        if (value instanceof Double) {
        	Double doubleValue = (Double) value;

            return doubleValue.isInfinite() || doubleValue.isNaN() ? "null" : value.toString();
        }

        if (value instanceof Float) {
        	Float floatValue = (Float) value;

            return floatValue.isInfinite() || floatValue.isNaN() ? "null" : value.toString();
        }               

        if (value instanceof Number) {
            return value.toString();
        }

        if (value instanceof Boolean) {
            return value.toString();
        }

        if (value instanceof JSONAware) {
            return ((JSONAware) value).toJSONString();
        }

        if (value instanceof Map) {
            return JSONObject.toJSONString ((Map) value);
        }

        if (value instanceof List) {
            return JSONArray.toJSONString ((List) value);
        }

        return value.toString();
    }
}
