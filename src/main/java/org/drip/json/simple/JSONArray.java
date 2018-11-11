
package org.drip.json.simple;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>JSONArray</i> is an Adaptation of the JSONArray class from the RFC4627 compliant JSON Simple
 *  (https://code.google.com/p/json-simple/). A JSON array. JSONObject supports java.util.List interface.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json">JSON</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json/simple">Simple</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetAllocation">Asset Allocation Library</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/MachineLearning">Machine Learning Library</a></li>
 *  </ul>
 * 
 * @author Fang Yidong
 * @author Lakshmi Krishnamurthy
 */

@SuppressWarnings ("rawtypes") public class JSONArray extends java.util.ArrayList implements java.util.List, JSONAware, JSONStreamAware {
    private static final long serialVersionUID = 3957988303675231981L;

/**
 * Encode a list into JSON text and write it to out. 
 * If this list is also a JSONStreamAware or a JSONAware, JSONStreamAware and JSONAware specific behaviours will be ignored at this top level.
 * 
 * @see org.drip.json.simple.JSONValue#writeJSONString(Object, Writer)
 * 
 * @param list List
 * @param out Output Writer
 * 
 * @throws java.io.IOException Thrown if the Inputs are invalid
 */

    public static void writeJSONString(java.util.List list, java.io.Writer out) throws java.io.IOException{
            if(list == null){
                    out.write("null");
                    return;
            }
            
            boolean first = true;
            java.util.Iterator iter=list.iterator();
            
    out.write('[');
            while(iter.hasNext()){
        if(first)
            first = false;
        else
            out.write(',');
        
                    Object value=iter.next();
                    if(value == null){
                            out.write("null");
                            continue;
                    }
                    
                    JSONValue.writeJSONString(value, out);
            }
            out.write(']');
    }
    
    public void writeJSONString(java.io.Writer out) throws java.io.IOException{
            writeJSONString(this, out);
    }
    
    /**
     * Convert a list to JSON text. The result is a JSON array. 
     * If this list is also a JSONAware, JSONAware specific behaviours will be omitted at this top level.
     * 
     * @see org.drip.json.simple.JSONValue#toJSONString(Object)
     * 
     * @param list List
     * @return JSON text, or "null" if list is null.
     */
    public static String toJSONString(java.util.List list){
            if(list == null)
                    return "null";
            
    boolean first = true;
    StringBuffer sb = new StringBuffer();
    java.util.Iterator iter=list.iterator();
    
    sb.append('[');
            while(iter.hasNext()){
        if(first)
            first = false;
        else
            sb.append(',');
        
                    Object value=iter.next();
                    if(value == null){
                            sb.append("null");
                            continue;
                    }
                    sb.append(JSONValue.toJSONString(value));
            }
    sb.append(']');
            return sb.toString();
    }

    public String toJSONString(){
            return toJSONString(this);
    }
    
    public String toString() {
            return toJSONString();
    }
}
