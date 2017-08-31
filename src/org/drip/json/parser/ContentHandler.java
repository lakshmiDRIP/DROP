
package org.drip.json.parser;

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
 * ContentHandler is an Adaptation of the ContentHandler Interface from the RFC4627 compliant JSON Simple
 *  (https://code.google.com/p/json-simple/).
 *
 * @author Fang Yidong
 * @author Lakshmi Krishnamurthy
 */

public interface ContentHandler {
    /**
     * Receive notification of the beginning of JSON processing.
     * The parser will invoke this method only once.
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     * 
     * @throws java.io.IOException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     */
    void startJSON() throws ParseException, java.io.IOException;
    
    /**
     * Receive notification of the end of JSON processing.
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     * 
     * @throws java.io.IOException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     */
    void endJSON() throws ParseException, java.io.IOException;
    
    /**
     * Receive notification of the beginning of a JSON object.
     * 
     * @return false if the handler wants to stop parsing after return.
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     * 
     * @throws java.io.IOException JSONParser will stop and throw the same exception to the caller when receiving this exception.
 * @see #endJSON
     */
    boolean startObject() throws ParseException, java.io.IOException;
    
    /**
     * Receive notification of the end of a JSON object.
     * 
     * @return false if the handler wants to stop parsing after return.
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     * 
     * @throws java.io.IOException JSONParser will stop and throw the same exception to the caller when receiving this exception.
 * 
 * @see #startObject
     */
    boolean endObject() throws ParseException, java.io.IOException;
    
    /**
     * Receive notification of the beginning of a JSON object entry.
     * 
     * @param key - Key of a JSON object entry. 
     * 
     * @return false if the handler wants to stop parsing after return.
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     * 
     * @throws java.io.IOException JSONParser will stop and throw the same exception to the caller when receiving this exception.
 * 
 * @see #endObjectEntry
     */
    boolean startObjectEntry(String key) throws ParseException, java.io.IOException;
    
    /**
     * Receive notification of the end of the value of previous object entry.
     * 
     * @return false if the handler wants to stop parsing after return.
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     * 
     * @throws java.io.IOException JSONParser will stop and throw the same exception to the caller when receiving this exception.
 * 
 * @see #startObjectEntry
     */
    boolean endObjectEntry() throws ParseException, java.io.IOException;
    
    /**
     * Receive notification of the beginning of a JSON array.
     * 
     * @return false if the handler wants to stop parsing after return.
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     * 
     * @throws java.io.IOException JSONParser will stop and throw the same exception to the caller when receiving this exception.
 * 
 * @see #endArray
     */
    boolean startArray() throws ParseException, java.io.IOException;
    
    /**
     * Receive notification of the end of a JSON array.
     * 
     * @return false if the handler wants to stop parsing after return.
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     * 
     * @throws java.io.IOException JSONParser will stop and throw the same exception to the caller when receiving this exception.
 * 
 * @see #startArray
     */
    boolean endArray() throws ParseException, java.io.IOException;
    
    /**
     * Receive notification of the JSON primitive values:
     *      java.lang.String,
     *      java.lang.Number,
     *      java.lang.Boolean
     *      null
     * 
     * @param value - Instance of the following:
     *                      java.lang.String,
     *                      java.lang.Number,
     *                      java.lang.Boolean
     *                      null
     * 
     * @return false if the handler wants to stop parsing after return.
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     * 
     * @throws java.io.IOException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     */
    boolean primitive(Object value) throws ParseException, java.io.IOException;
}
