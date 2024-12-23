
package org.drip.service.representation;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
 * <i>JSONObject</i> is an Adaptation of the JSONObject Class from the RFC4627 compliant JSON Simple
 * 	(https://code.google.com/p/json-simple/). It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F). It's the same as JSONValue.escape() only for compatibility here</li>
 * 		<li>Encode a map into JSON text and write it to out. If this map is also a <i>JSONAware</i> or <i>JSONStreamAware</i>, <i>JSONAware</i>, or <i>JSONStreamAware</i> specific behavior will be ignored at this top level</li>
 * 		<li>Convert a map to JSON text. The result is a JSON object.  If this map is also a <i>JSONAware</i>, <i>JSONAware</i> specific behavior will be omitted at this top level</li>
 * 		<li>Encode "this" map into JSON text and write it to out. If this map is also a <i>JSONAware</i> or <i>JSONStreamAware</i>, <i>JSONAware</i>, or <i>JSONStreamAware</i> specific behavior will be ignored at this top level</li>
 * 		<li>Convert "this" map to JSON text. The result is a JSON object.  If this map is also a <i>JSONAware</i>, <i>JSONAware</i> specific behavior will be omitted at this top level</li>
 * 		<li>Convert "this" to JSON text - this directly defers to JSON'izer</li>
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

@SuppressWarnings ("rawtypes") public class JSONObject
	extends HashMap
	implements Map, JSONAware, JSONStreamAware
{
    private static final long serialVersionUID = -503443796854799292L;

    private static String toJSONString (
		final String key,
		final Object value,
		final StringBuffer stringBuffer)
    {
    	stringBuffer.append ('\"');

        if (null == key) {
        	stringBuffer.append ("null");
        } else {
        	JSONValue.escape (key, stringBuffer);
        }

        stringBuffer.append ('\"').append (':');

        stringBuffer.append (JSONValue.toJSONString (value));

        return stringBuffer.toString();
    }

    /**
     * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F). It's the
     * 	same as JSONValue.escape() only for compatibility here.
     * 
     * @see org.drip.service.representation.JSONValue#escape(String)
     * 
     * @param s The Input String
     * 
     * @return Escaped String
     */

    public static String escape (
		final String s)
    {
        return JSONValue.escape (s);
    }

	/**
	 * Encode a map into JSON text and write it to out. If this map is also a <i>JSONAware</i> or
	 * 	<i>JSONStreamAware</i>, <i>JSONAware</i>, or <i>JSONStreamAware</i> specific behavior will be ignored
	 *  at this top level.
	 * 
	 * @see org.drip.service.representation.JSONValue#writeJSONString(Object, Writer)
	 * 
	 * @param map Input Map
	 * @param outputWriter Output Writer
	 * 
	 * @throws IOException Thrown if the Inputs are Invalid
	 */

    public static void writeJSONString (
		final Map map,
		final Writer outputWriter)
		throws IOException
    {
        if (null == map) {
        	outputWriter.write ("null");

        	return;
        }

        boolean first = true;

        Iterator iterator = map.entrySet().iterator();

        outputWriter.write ('{');

        while (iterator.hasNext()) {
	        if (first) {
	            first = false;
	        } else {
	        	outputWriter.write (',');
	        }

	        Map.Entry entry = (Map.Entry) iterator.next();

	        outputWriter.write('\"');

	        outputWriter.write (escape (String.valueOf (entry.getKey())));

	        outputWriter.write ('\"');

	        outputWriter.write (':');

	        JSONValue.writeJSONString (entry.getValue(), outputWriter);
        }

        outputWriter.write ('}');
    }

    /**
     * Convert a map to JSON text. The result is a JSON object.  If this map is also a <i>JSONAware</i>,
     * 	<i>JSONAware</i> specific behavior will be omitted at this top level.
     * 
     * @see org.drip.service.representation.JSONValue#toJSONString(Object)
     * 
     * @param map The JSON Map
     * 
     * @return JSON text, or "null" if map is null.
     */

    public static String toJSONString (
		final Map map)
    {
        if (null == map) {
            return "null";
        }

	    StringBuffer stringBuffer = new StringBuffer();
	    
	    Iterator iterator = map.entrySet().iterator();

	    stringBuffer.append ('{');

	    boolean first = true;

	    while (iterator.hasNext()) {
	    	if (first) {
	    		first = false;
	    	} else {
	    		stringBuffer.append (',');
	    	}

	    	Map.Entry entry = (Map.Entry) iterator.next();

	    	toJSONString (String.valueOf (entry.getKey()), entry.getValue(), stringBuffer);
        }

	    stringBuffer.append ('}');

	    return stringBuffer.toString();
    }

    /**
     * JSONize the key-value String
     * 
     * @param key Key
     * @param value Value
     * 
     * @return The JSONized Key-Value String
     */

    public static String toString (
		final String key,
		final Object value)
    {
	    StringBuffer stringBuffer = new StringBuffer();

	    toJSONString (key, value, stringBuffer);

	    return stringBuffer.toString();
    }

    /**
     * Empty <i>JSONObject</i> Constructor
     * JSONize the key-value String
     * Empty <i>JSONObject</i> Constructor
     * Allows creation of a <i>JSONObject</i> from a Map. After that, both the generated <i>JSONObject</i>
     * 	and the Map can be modified independently.
	 * Encode "this" map into JSON text and write it to out. If this map is also a <i>JSONAware</i> or
	 * 	<i>JSONStreamAware</i>, <i>JSONAware</i>, or <i>JSONStreamAware</i> specific behavior will be ignored
	 *  at this top level.
     */

    public JSONObject()
    {
        super();
    }

    /**
     * Allows creation of a <i>JSONObject</i> from a Map. After that, both the generated <i>JSONObject</i>
     * 	and the Map can be modified independently.
     * 
     * @param map Input JSON Map
     */

    @SuppressWarnings ("unchecked") public JSONObject (
		final Map map)
    {
        super (map);
    }

	/**
	 * Encode "this" map into JSON text and write it to out. If this map is also a <i>JSONAware</i> or
	 * 	<i>JSONStreamAware</i>, <i>JSONAware</i>, or <i>JSONStreamAware</i> specific behavior will be ignored
	 *  at this top level.
	 * 
	 * @see org.drip.service.representation.JSONValue#writeJSONString(Object, Writer)
	 * 
	 * @param outputWriter Output Writer
	 * 
	 * @throws IOException Thrown if the Inputs are Invalid
	 */

    public void writeJSONString (
		final Writer outputWriter)
		throws IOException
    {
        writeJSONString (this, outputWriter);
    }

    /**
     * Convert "this" map to JSON text. The result is a JSON object.  If this map is also a <i>JSONAware</i>,
     * 	<i>JSONAware</i> specific behavior will be omitted at this top level.
     * 
     * @see org.drip.service.representation.JSONValue#toJSONString(Object)
     * 
     * @return JSON text, or "null" if map is null.
     */

    public String toJSONString()
    {
        return toJSONString (this);
    }

    /**
     * Convert "this" to JSON text - this directly defers to JSON'izer
     * 
     * @see org.drip.service.representation.JSONValue#toJSONString(Object)
     * 
     * @return JSON text, or "null" if map is null.
     */
    
    @Override public String toString()
    {
        return toJSONString();
    }
}
