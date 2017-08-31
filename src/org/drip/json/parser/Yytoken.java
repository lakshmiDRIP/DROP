
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
 * Yytoken is an Adaptation of the Yytoken Class from the RFC4627 compliant JSON Simple
 *  (https://code.google.com/p/json-simple/).
 *
 * @author Fang Yidong
 * @author Lakshmi Krishnamurthy
 */

public class Yytoken {
    public static final int TYPE_VALUE=0;//JSON primitive value: string,number,boolean,null
    public static final int TYPE_LEFT_BRACE=1;
    public static final int TYPE_RIGHT_BRACE=2;
    public static final int TYPE_LEFT_SQUARE=3;
    public static final int TYPE_RIGHT_SQUARE=4;
    public static final int TYPE_COMMA=5;
    public static final int TYPE_COLON=6;
    public static final int TYPE_EOF=-1;//end of file
    
    public int type=0;
    public Object value=null;
    
    public Yytoken(int type,Object value){
            this.type=type;
            this.value=value;
    }
    
    public String toString(){
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
