
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
 * <i>JSONValue</i> is an Adaptation of the JSONValue Class from the RFC4627 compliant JSON Simple
 *  (https://code.google.com/p/json-simple/).
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

public class JSONValue {
    /**
     * Parse JSON text into java object from the input source. 
     * Please use parseWithException() if you don't want to ignore the exception.
     * 
     * @see org.drip.json.parser.JSONParser#parse(Reader)
     * @see #parseWithException(Reader)
     * 
     * @param in Input Reader
     * 
     * @return Instance of the following:
     *      org.drip.json.simple.JSONObject,
     *      org.drip.json.simple.JSONArray,
     *      java.lang.String,
     *      java.lang.Number,
     *      java.lang.Boolean,
     *      null
     * 
     */
    public static Object parse(java.io.Reader in){
            try{
                    org.drip.json.parser.JSONParser parser=new org.drip.json.parser.JSONParser();
                    return parser.parse(in);
            }
            catch(Exception e){
                    return null;
            }
    }
    
    public static Object parse(String s){
    	java.io.StringReader in=new java.io.StringReader(s);
            return parse(in);
    }
    
    /**
     * Parse JSON text into java object from the input source.
     * 
     * @see org.drip.json.parser.JSONParser
     * 
     * @param in Input Reader
     * @return Instance of the following:
     *      org.json.simple.JSONObject,
     *      org.json.simple.JSONArray,
     *      java.lang.String,
     *      java.lang.Number,
     *      java.lang.Boolean,
     *      null
     * 
     * @throws java.io.IOException Thrown if the Inputs are Invalid
     * 
     * @throws org.drip.json.parser.ParseException Thrown if the Inputs are Invalid
     */
    public static Object parseWithException(java.io.Reader in) throws java.io.IOException, org.drip.json.parser.ParseException{
    	org.drip.json.parser.JSONParser parser=new org.drip.json.parser.JSONParser();
            return parser.parse(in);
    }
    
    public static Object parseWithException(String s) throws org.drip.json.parser.ParseException{
    	org.drip.json.parser.JSONParser parser=new org.drip.json.parser.JSONParser();
            return parser.parse(s);
    }
    
/**
 * Encode an object into JSON text and write it to out.
 * <p>
 * If this object is a Map or a List, and it's also a JSONStreamAware or a JSONAware, JSONStreamAware or JSONAware will be considered firstly.
 * <p>
 * DO NOT call this method from writeJSONString(Writer) of a class that implements both JSONStreamAware and (Map or List) with 
 * "this" as the first parameter, use JSONObject.writeJSONString(Map, Writer) or JSONArray.writeJSONString(List, Writer) instead. 
 * 
 * @see org.drip.json.simple.JSONObject#writeJSONString(Map, Writer)
 * @see org.drip.json.simple.JSONArray#writeJSONString(List, Writer)
 * 
 * @param value The JSON Object
 * @param out The JSON Writer
 * 
 * @throws java.io.IOException Thrown if the Inputs are Invalid
 */
    @SuppressWarnings ("rawtypes") public static void writeJSONString(Object value, java.io.Writer out) throws java.io.IOException {
            if(value == null){
                    out.write("null");
                    return;
            }
            
            if(value instanceof String){            
        out.write('\"');
                    out.write(escape((String)value));
        out.write('\"');
                    return;
            }
            
            if(value instanceof Double){
                    if(((Double)value).isInfinite() || ((Double)value).isNaN())
                            out.write("null");
                    else
                            out.write(value.toString());
                    return;
            }
            
            if(value instanceof Float){
                    if(((Float)value).isInfinite() || ((Float)value).isNaN())
                            out.write("null");
                    else
                            out.write(value.toString());
                    return;
            }               
            
            if(value instanceof Number){
                    out.write(value.toString());
                    return;
            }
            
            if(value instanceof Boolean){
                    out.write(value.toString());
                    return;
            }
            
            if((value instanceof JSONStreamAware)){
                    ((JSONStreamAware)value).writeJSONString(out);
                    return;
            }
            
            if((value instanceof JSONAware)){
                    out.write(((JSONAware)value).toJSONString());
                    return;
            }
            
            if(value instanceof java.util.Map){
            	org.drip.json.simple.JSONObject.writeJSONString((java.util.Map)value, out);
                    return;
            }
            
            if(value instanceof java.util.List){
                    JSONArray.writeJSONString((java.util.List)value, out);
        return;
            }
            
            out.write(value.toString());
    }

    /**
     * Convert an object to JSON text.
     * <p>
     * If this object is a Map or a List, and it's also a JSONAware, JSONAware will be considered firstly.
     * <p>
     * DO NOT call this method from toJSONString() of a class that implements both JSONAware and Map or List with 
     * "this" as the parameter, use JSONObject.toJSONString(Map) or JSONArray.toJSONString(List) instead. 
     * 
     * @see org.drip.json.simple.JSONObject#toJSONString(Map)
     * @see org.drip.json.simple.JSONArray#toJSONString(List)
     * 
     * @param value The JSON Object
     * 
     * @return JSON text, or "null" if value is null or it's an NaN or an INF number.
     */

    @SuppressWarnings ("rawtypes") public static String toJSONString(Object value){
            if(value == null)
                    return "null";
            
            if(value instanceof String)
                    return "\""+escape((String)value)+"\"";
            
            if(value instanceof Double){
                    if(((Double)value).isInfinite() || ((Double)value).isNaN())
                            return "null";
                    else
                            return value.toString();
            }
            
            if(value instanceof Float){
                    if(((Float)value).isInfinite() || ((Float)value).isNaN())
                            return "null";
                    else
                            return value.toString();
            }               
            
            if(value instanceof Number)
                    return value.toString();
            
            if(value instanceof Boolean)
                    return value.toString();
            
            if((value instanceof JSONAware))
                    return ((JSONAware)value).toJSONString();
            
            if(value instanceof java.util.Map)
                    return org.drip.json.simple.JSONObject.toJSONString((java.util.Map)value);
            
            if(value instanceof java.util.List)
                    return org.drip.json.simple.JSONArray.toJSONString((java.util.List)value);
            
            return value.toString();
    }

    /**
     * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F).
     * 
     * @param s Pre-escape String
     * 
     * @return The Escape String
     */
    public static String escape(String s){
            if(s==null)
                    return null;
    StringBuffer sb = new StringBuffer();
    escape(s, sb);
    return sb.toString();
}

/**
 * @param s - Must not be null.
 * @param sb The StringBuffer
 */
static void escape(String s, StringBuffer sb) {
            for(int i=0;i<s.length();i++){
                    char ch=s.charAt(i);
                    switch(ch){
                    case '"':
                            sb.append("\\\"");
                            break;
                    case '\\':
                            sb.append("\\\\");
                            break;
                    case '\b':
                            sb.append("\\b");
                            break;
                    case '\f':
                            sb.append("\\f");
                            break;
                    case '\n':
                            sb.append("\\n");
                            break;
                    case '\r':
                            sb.append("\\r");
                            break;
                    case '\t':
                            sb.append("\\t");
                            break;
                    case '/':
                            sb.append("\\/");
                            break;
                    default:
            //Reference: http://www.unicode.org/versions/Unicode5.1.0/
                            if((ch>='\u0000' && ch<='\u001F') || (ch>='\u007F' && ch<='\u009F') || (ch>='\u2000' && ch<='\u20FF')){
                                    String ss=Integer.toHexString(ch);
                                    sb.append("\\u");
                                    for(int k=0;k<4-ss.length();k++){
                                            sb.append('0');
                                    }
                                    sb.append(ss.toUpperCase());
                            }
                            else{
                                    sb.append(ch);
                            }
                    }
            }//for
    }
}
