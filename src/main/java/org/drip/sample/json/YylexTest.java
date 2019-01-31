
package org.drip.sample.json;

import java.io.*;

import org.drip.json.parser.*;
import org.drip.service.env.EnvManager;

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
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>YylexTest</i> is an Adaptation of the YylexTest Class from the RFC4627 compliant JSON Simple
 * (https://code.google.com/p/json-simple/).
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AlgorithmSupportLibrary.md">Algorithm Support Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/json/README.md">JSON Serialization and De-serialization</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Fang Yidong
 * @author Lakshmi Krishnamurthy
 */

public class YylexTest {

    public static final void testYylex() throws Exception{
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

    public static final void main (
    	final String[] astrArgs)
    	throws Exception
	{
    	EnvManager.InitEnv ("");

    	testYylex();

    	EnvManager.TerminateEnv();
	}
}
