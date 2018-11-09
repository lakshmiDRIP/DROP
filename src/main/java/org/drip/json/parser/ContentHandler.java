
package org.drip.json.parser;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>ContentHandler</i> is an Adaptation of the ContentHandler Interface from the RFC4627 compliant JSON
 * Simple (https://code.google.com/p/json-simple/).
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json">JSON</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json/parser">Parser</li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetAllocation">Asset Allocation Library</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/MachineLearning">Machine Learning Library</a></li>
 *  </ul>
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
