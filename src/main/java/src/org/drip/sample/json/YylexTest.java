
package org.drip.sample.json;

import java.io.*;

import org.drip.service.env.EnvManager;
import org.drip.service.jsonparser.*;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>YylexTest</i> is an Adaptation of the YylexTest Class from the RFC4627 compliant JSON Simple
 * 	(https://code.google.com/p/json-simple/).
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AlgorithmSupportLibrary.md">Algorithm Support Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/json/README.md">JSON Serialization and De-serialization</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Fang Yidong
 * @author Lakshmi Krishnamurthy
 */

public class YylexTest {

    private static final void testYylex() throws Exception{
            String s="\"\\/\"";
            System.out.println(s);
            StringReader in = new StringReader(s);
            Yylex lexer=new Yylex(in);
            Yytoken token=lexer.yylex();
            System.out.println (Yytoken.TYPE_VALUE == token.type);
            System.out.println ("/".equalsIgnoreCase ((String) token.value));
            
            s="\"abc\\/\\r\\b\\n\\t\\f\\\\\"";
            System.out.println(s);
            in = new StringReader(s);
            lexer=new Yylex(in);
            token=lexer.yylex();
            System.out.println (Yytoken.TYPE_VALUE == token.type);
            System.out.println ("abc/\r\b\n\t\f\\".equalsIgnoreCase ((String)token.value));
            
            s="[\t \n\r\n{ \t \t\n\r}";
            System.out.println(s);
            in = new StringReader(s);
            lexer=new Yylex(in);
            token=lexer.yylex();
            System.out.println (Yytoken.TYPE_LEFT_SQUARE == token.type);
            token=lexer.yylex();
            System.out.println (Yytoken.TYPE_LEFT_BRACE == token.type);
            token=lexer.yylex();
            System.out.println (Yytoken.TYPE_RIGHT_BRACE == token.type);
            
            s="\b\f{";
            System.out.println(s);
            in = new StringReader(s);
            lexer=new Yylex(in);
            ParseException err=null;
            try{
                    token=lexer.yylex();
            }
            catch(ParseException e){
                    err=e;
                    System.out.println("error:"+err);
                    System.out.println (ParseException.ERROR_UNEXPECTED_CHAR == e.getErrorType());
                    System.out.println (0 == e.getPosition());
                    System.out.println (Character.toString ('\b') == e.getUnexpectedObject());
            }
            catch(IOException ie){
                    throw ie;
            }
            System.out.println (err!=null);
            
            s="{a : b}";
            System.out.println(s);
            in = new StringReader(s);
            lexer=new Yylex(in);
            err=null;
            try{
                    lexer.yylex();
                    token=lexer.yylex();
            }
            catch(ParseException e){
                    err=e;
                    System.out.println("error:"+err);
                    System.out.println (ParseException.ERROR_UNEXPECTED_CHAR == e.getErrorType());
                    System.out.println (Character.toString ('a') == e.getUnexpectedObject());
                    System.out.println (1 == e.getPosition());
            }
            catch(IOException ie){
                    throw ie;
            }
            System.out.println (err!=null);
    }

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

    public static final void main (
    	final String[] astrArgs)
    	throws Exception
	{
    	EnvManager.InitEnv ("");

    	testYylex();

    	EnvManager.TerminateEnv();
	}
}
