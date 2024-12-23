
package org.drip.service.representation;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
 * <i>JSONArray</i> is an Adaptation of the JSONArray class from the RFC4627 compliant JSON Simple
 * 	(https://code.google.com/p/json-simple/). A JSON array. JSONObject supports List interface. It provides
 * 	the following Functionality:
 *
 *  <ul>
 * 		<li>Encode a list into JSON text and write it to out.</li>
 * 		<li>Write the Contents of "this" to the Output Writer</li>
 * 		<li>Convert "this" to JSON text. The result is a JSON array. #1</li>
 * 		<li>Convert "this" to JSON text. The result is a JSON array. #2</li>
 * 		<li>Convert "this" to a "String", simply defers to JSON String</li>
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

@SuppressWarnings ("rawtypes") public class JSONArray
	extends ArrayList
	implements List, JSONAware, JSONStreamAware
{
    private static final long serialVersionUID = 3957988303675231981L;

	/**
	 * Encode a list into JSON text and write it to out. If this list is also a JSONStreamAware or a
	 *  JSONAware, JSONStreamAware and JSONAware specific behaviours will be ignored at this top level.
	 * 
	 * @see org.drip.service.representation.JSONValue#writeJSONString(Object, Writer)
	 * 
	 * @param list List
	 * @param outputWriter Output Writer
	 * 
	 * @throws IOException Thrown if the Inputs are invalid
	 */

    public static void writeJSONString (
		final List list,
		final Writer outputWriter)
		throws IOException
    {
        if (null == list) {
        	outputWriter.write ("null");

        	return;
        }
        
        boolean first = true;

        Iterator iterator = list.iterator();
        
        outputWriter.write ('[');

        while (iterator.hasNext()) {
	        if (first) {
	            first = false;
	        } else {
	        	outputWriter.write (',');
	        }

            Object value = iterator.next();

            if (null == value) {
            	outputWriter.write ("null");

            	continue;
            }

            JSONValue.writeJSONString (value, outputWriter);
        }

        outputWriter.write (']');
    }

    /**
     * Write the Contents of "this" to the Output Writer
     * 
	 * @see org.drip.service.representation.JSONValue#writeJSONString(Object, Writer)
	 * 
	 * @param outputWriter Output Writer
	 * 
	 * @throws IOException Thrown if the Inputs are invalid
     */

    public void writeJSONString (
		final Writer outputWriter)
		throws IOException
    {
        writeJSONString (this, outputWriter);
    }

    /**
     * Convert a list to JSON text. The result is a JSON array.
     * If this list is also a JSONAware, JSONAware specific behaviours will be omitted at this top level.
     * 
     * @see org.drip.service.representation.JSONValue#toJSONString(Object)
     * 
     * @param list List
     * 
     * @return JSON text, or "null" if list is null.
     */

    public static String toJSONString (
		final List list)
    {
        if (null == list) {
            return "null";
        }

	    boolean first = true;

	    Iterator iterator = list.iterator();

	    StringBuffer stringBuffer = new StringBuffer();

	    stringBuffer.append ('[');

	    while (iterator.hasNext()) {
	    	if (first) {
	    		first = false;
	    	} else {
	    		stringBuffer.append (',');
	    	}

            Object value = iterator.next();

            if (null == value) {
            	stringBuffer.append ("null");

            	continue;
            }

            stringBuffer.append (JSONValue.toJSONString (value));
        }

	    stringBuffer.append (']');

	    return stringBuffer.toString();
    }

    /**
     * Convert "this" to JSON text. The result is a JSON array. 
     * If this list is also a JSONAware, JSONAware specific behaviours will be omitted at this top level.
     * 
     * @see org.drip.service.representation.JSONValue#toJSONString(Object)
     * 
     * @return JSON text, or "null" if list is null.
     */

    public String toJSONString()
    {
        return toJSONString (this);
    }

    /**
     * Convert "this" to a "String", simply defers to JSON String
     * 
     * @return JSON text, or "null" if list is null.
     */
    
    public String toString()
    {
        return toJSONString();
    }
}
