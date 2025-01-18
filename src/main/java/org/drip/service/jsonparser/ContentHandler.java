
package org.drip.service.jsonparser;

import java.io.IOException;

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
 * <i>ContentHandler</i> is an Adaptation of the ContentHandler Interface from the RFC4627 compliant JSON
 * 	Simple (https://code.google.com/p/json-simple/). It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Receive notification of the beginning of JSON processing. The parser will invoke this method only once</li>
 * 		<li>Receive notification of the end of JSON processing</li>
 * 		<li>Receive notification of the beginning of a JSON object</li>
 * 		<li>Receive notification of the end of a JSON object</li>
 * 		<li>Receive notification of the beginning of a JSON object entry</li>
 * 		<li>Receive notification of the end of the value of previous object entry</li>
 * 		<li>Receive notification of the beginning of a JSON array</li>
 * 		<li>Receive notification of the end of a JSON array</li>
 * 		<li>Receive notification of the JSON primitive values:</li>
 *  	<ul>
 *      	<li>java.lang.String</li>
 *      	<li>java.lang.Number</li>
 *      	<li>java.lang.Boolean</li>
 *      	<li>null</li>
 *  	</ul>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/jsonparser/README.md">RFC4627 Compliant JSON Message Parser</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Fang Yidong
 * @author Lakshmi Krishnamurthy
 */

public interface ContentHandler
{

	/**
     * Receive notification of the beginning of JSON processing. The parser will invoke this method only
     * 	once.
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving
     *  this exception.
     * 
     * @throws IOException JSONParser will stop and throw the same exception to the caller when receiving
     * 	this exception.
     */

	void startJSON()
		throws ParseException, IOException;
    
    /**
     * Receive notification of the end of JSON processing
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving
     * 	 this exception.
     * 
     * @throws IOException JSONParser will stop and throw the same exception to the caller when receiving
     * 	this exception.
     */

	void endJSON()
		throws ParseException, IOException;
    
    /**
     * Receive notification of the beginning of a JSON object.
     * 
     * @return false if the handler wants to stop parsing after return.
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     * 
     * @throws IOException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     * @see #endJSON
     */

	boolean startObject()
		throws ParseException, IOException;
    
    /**
     * Receive notification of the end of a JSON object.
     * 
     * @return false if the handler wants to stop parsing after return.
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving
     * 	this exception.
     * 
     * @throws IOException JSONParser will stop and throw the same exception to the caller when receiving
     * 	this exception.
	 * 
	 * @see #startObject
     */

	boolean endObject()
		throws ParseException, IOException;
    
    /**
     * Receive notification of the beginning of a JSON object entry.
     * 
     * @param key - Key of a JSON object entry. 
     * 
     * @return false if the handler wants to stop parsing after return.
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving
     * 	this exception.
     * 
     * @throws IOException JSONParser will stop and throw the same exception to the caller when receiving
     * 	this exception.
	 * 
	 * @see #endObjectEntry
     */

	boolean startObjectEntry (
		final String key)
		throws ParseException, IOException;
    
    /**
     * Receive notification of the end of the value of previous object entry.
     * 
     * @return false if the handler wants to stop parsing after return.
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     * 
     * @throws IOException JSONParser will stop and throw the same exception to the caller when receiving this exception.
	 * 
	 * @see #startObjectEntry
     */

	boolean endObjectEntry()
		throws ParseException, IOException;
    
    /**
     * Receive notification of the beginning of a JSON array.
     * 
     * @return false if the handler wants to stop parsing after return.
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     * 
     * @throws IOException JSONParser will stop and throw the same exception to the caller when receiving this exception.
	 * 
	 * @see #endArray
     */

	boolean startArray()
		throws ParseException, IOException;
    
    /**
     * Receive notification of the end of a JSON array.
     * 
     * @return false if the handler wants to stop parsing after return.
     * 
     * @throws ParseException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     * 
     * @throws IOException JSONParser will stop and throw the same exception to the caller when receiving this exception.
	 * 
	 * @see #startArray
     */

    boolean endArray()
		throws ParseException, IOException;
    
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
     * @throws IOException JSONParser will stop and throw the same exception to the caller when receiving this exception.
     */

    boolean primitive (
		final Object value)
		throws ParseException, IOException;
}
