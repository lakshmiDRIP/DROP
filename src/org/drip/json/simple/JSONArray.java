
package org.drip.json.simple;

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
 * JSONArray is an Adaptation of the JSONArray class from the RFC4627 compliant JSON Simple
 *  (https://code.google.com/p/json-simple/).
 *
 * A JSON array. JSONObject supports java.util.List interface.
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
