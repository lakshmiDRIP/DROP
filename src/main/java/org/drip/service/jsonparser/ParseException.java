
package org.drip.service.jsonparser;

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
 * <i>ParseException</i> is an Adaptation of the ParseException Class from the RFC4627 compliant JSON Simple
 * (https://code.google.com/p/json-simple/). It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Error - Unexpected Character</li>
 * 		<li>Error - Unexpected Token</li>
 * 		<li>Error - Unexpected Exception</li>
 * 		<li><i>ParseException</i> Constructor #1</li>
 * 		<li><i>ParseException</i> Constructor #2</li>
 * 		<li><i>ParseException</i> Constructor #3</li>
 * 		<li>Retrieve the Error Type</li>
 * 		<li>Set the Error Type</li>
 * 		<li>Get the Position</li>
 * 		<li>Set the Position</li>
 * 		<li>Get the Unexpected Object</li>
 * 		<li>Set the Unexpected Object</li>
 * 		<li>String Version of the State</li>
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

public class ParseException
	extends Exception
{
    private static final long serialVersionUID = -7880698968187728548L;

    /**
     * Error - Unexpected Character
     */

    public static final int ERROR_UNEXPECTED_CHAR = 0;

    /**
     * Error - Unexpected Token
     */

    public static final int ERROR_UNEXPECTED_TOKEN = 1;

    /**
     * Error - Unexpected Exception
     */

    public static final int ERROR_UNEXPECTED_EXCEPTION = 2;

    /**
     * Error Type
     */

    private int _errorType = Integer.MIN_VALUE;

    /**
     * Unexpected Exception
     */

    private Object _unexpectedObject = null;

    /**
     * Position
     */

    private int _position = Integer.MIN_VALUE;

    /**
     * <i>ParseException</i> Constructor #1
     * 
     * @param position Position
     * @param errorType Error Type
     * @param unexpectedObject Unexpected Object
     */

    public ParseException (
		final int position,
		final int errorType,
		final Object unexpectedObject)
    {
        _position = position;
        _errorType = errorType;
        _unexpectedObject = unexpectedObject;
    }

    /**
     * <i>ParseException</i> Constructor #2
     * 
     * @param errorType Error Type
     * @param unexpectedObject Unexpected Error Object
     */

    public ParseException (
		final int errorType,
		final Object unexpectedObject)
    {
        this (-1, errorType, unexpectedObject);
    }

    /**
     * <i>ParseException</i> Constructor #3
     * 
     * @param errorType Error Type
     */

    public ParseException (
		final int errorType)
    {
        this (-1, errorType, null);
    }

    /**
     * Retrieve the Error Type
     * 
     * @return The Error Type
     */

    public int getErrorType()
    {
        return _errorType;
    }

    /**
     * Set the Error Type
     * 
     * @param errorType The Error Type
     */

    public void setErrorType (
		final int errorType)
    {
        _errorType = errorType;
    }

    /**
     * Get the Position
     * 
     * @see org.drip.service.jsonparser.LexicalProcessor#getPosition()
     * 
     * @return The character position (starting with 0) of the input where the error occurs.
     */

    public int getPosition()
    {
        return _position;
    }

    /**
     * Set the Position
     * 
     * @param position The Position
     */

    public void setPosition (
		final int position)
    {
        _position = position;
    }
    
    /**
     * Get the Unexpected Object
     * 
     * @see org.drip.service.jsonparser.Yytoken
     * 
     * @return One of the following base on the value of errorType:
     *                      ERROR_UNEXPECTED_CHAR           java.lang.Character
     *                      ERROR_UNEXPECTED_TOKEN          org.json.simple.parser.Yytoken
     *                      ERROR_UNEXPECTED_EXCEPTION      java.lang.Exception
     */

    public Object getUnexpectedObject()
    {
        return _unexpectedObject;
    }

    /**
     * Set the Unexpected Object
     * 
     * @param unexpectedObject The Unexpected Object
     */

    public void setUnexpectedObject (
		final Object unexpectedObject)
    {
        _unexpectedObject = unexpectedObject;
    }

    /**
     * String Version of the State
     * 
     * @return String Version of the State
     */

    @Override public String toString()
    {
        StringBuffer stringBuffer = new StringBuffer();

        switch (_errorType) {
        	case ERROR_UNEXPECTED_CHAR:
        		stringBuffer.append (
    				"Unexpected character ("
				).append (
					_unexpectedObject
				).append (
					") at position "
				).append (
					_position
				).append (
					"."
				);

        		break;

        	case ERROR_UNEXPECTED_TOKEN:
        		stringBuffer.append (
    				"Unexpected token "
				).append (
					_unexpectedObject
				).append (
					" at position "
				).append (
					_position
				).append (
					"."
				);

        		break;

        	case ERROR_UNEXPECTED_EXCEPTION:
        		stringBuffer.append (
    				"Unexpected exception at position "
				).append (
					_position
				).append (
					": "
				).append (
					_unexpectedObject
				);

        		break;

        	default:
        		stringBuffer.append ("Unkown error at position ").append (_position).append (".");

        		break;
        }

        return stringBuffer.toString();
    }
}
