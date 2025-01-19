
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
 * <i>Yytoken</i> is an Adaptation of the Yytoken Class from the RFC4627 compliant JSON Simple
 * 	(https://code.google.com/p/json-simple/). It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Error - Unexpected Character</li>
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

public class Yytoken
{

	/**
	 * Value Token
	 */

	public static final int TYPE_VALUE = 0; // JSON primitive value: string, number, boolean, null

	/**
	 * Left Brace Token
	 */

    public static final int TYPE_LEFT_BRACE=1;

	/**
	 * Right Brace Token
	 */

    public static final int TYPE_RIGHT_BRACE=2;

	/**
	 * Left Square Token
	 */

    public static final int TYPE_LEFT_SQUARE=3;

	/**
	 * Right Square Token
	 */

    public static final int TYPE_RIGHT_SQUARE=4;

	/**
	 * Comma Token
	 */

    public static final int TYPE_COMMA=5;

	/**
	 * Colon Token
	 */

    public static final int TYPE_COLON=6;

	/**
	 * EOF Token
	 */

    public static final int TYPE_EOF=-1;//end of file

	/**
	 * Type
	 */

    public int type=0;

	/**
	 * Value
	 */

    public Object value=null;

    /**
     * Yytoken Constructor
     * 
     * @param type Token Type
     * @param value Token Value
     */

    public Yytoken(int type,Object value){
            this.type=type;
            this.value=value;
    }

    @Override public String toString(){
            StringBuffer sb = new StringBuffer();
            switch(type){
            case TYPE_VALUE:
                    sb.append("VALUE(").append(value).append(")");
                    break;
            case TYPE_LEFT_BRACE:
                    sb.append("LEFT BRACE({)");
                    break;
            case TYPE_RIGHT_BRACE:
                    sb.append("RIGHT BRACE(})");
                    break;
            case TYPE_LEFT_SQUARE:
                    sb.append("LEFT SQUARE([)");
                    break;
            case TYPE_RIGHT_SQUARE:
                    sb.append("RIGHT SQUARE(])");
                    break;
            case TYPE_COMMA:
                    sb.append("COMMA(,)");
                    break;
            case TYPE_COLON:
                    sb.append("COLON(:)");
                    break;
            case TYPE_EOF:
                    sb.append("END OF FILE");
                    break;
            }
            return sb.toString();
    }
}
