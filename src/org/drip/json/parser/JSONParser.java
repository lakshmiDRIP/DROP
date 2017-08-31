
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
 * JSONParser is an Adaptation of the JSONParser Class from the RFC4627 compliant JSON Simple
 *  (https://code.google.com/p/json-simple/).
 *
 * @author Fang Yidong
 * @author Lakshmi Krishnamurthy
 */

public class JSONParser {
    public static final int S_INIT=0;
    public static final int S_IN_FINISHED_VALUE=1;//string,number,boolean,null,object,array
    public static final int S_IN_OBJECT=2;
    public static final int S_IN_ARRAY=3;
    public static final int S_PASSED_PAIR_KEY=4;
    public static final int S_IN_PAIR_VALUE=5;
    public static final int S_END=6;
    public static final int S_IN_ERROR=-1;
    
    @SuppressWarnings ("rawtypes") private java.util.LinkedList handlerStatusStack;

    private Yylex lexer = new Yylex((java.io.Reader)null);
    private Yytoken token = null;
    private int status = S_INIT;
    
    @SuppressWarnings ("rawtypes") private int peekStatus(java.util.LinkedList statusStack){
            if(statusStack.size()==0)
                    return -1;
            Integer status=(Integer)statusStack.getFirst();
            return status.intValue();
    }
    
/**
 *  Reset the parser to the initial state without resetting the underlying reader.
 *
 */
public void reset(){
    token = null;
    status = S_INIT;
    handlerStatusStack = null;
}

/**
 * Reset the parser to the initial state with a new character reader.
 * 
 * @param in - The new character reader.
 */
    public void reset(java.io.Reader in){
            lexer.yyreset(in);
            reset();
    }
    
    /**
     * @return The position of the beginning of the current token.
     */
    public int getPosition(){
            return lexer.getPosition();
    }
    
    public Object parse(String s) throws ParseException{
            return parse(s, (ContainerFactory)null);
    }
    
    /**
     * Parse the JSON String
     * 
     * @param s The String
     * @param containerFactory The Container Factory
     * 
     * @return The JSON Object
     * 
     * @throws ParseException Thrown if the Inputs are Invalid
     */

    public Object parse(String s, ContainerFactory containerFactory) throws ParseException{
    	java.io.StringReader in=new java.io.StringReader(s);
            try{
                    return parse(in, containerFactory);
            }
            catch(java.io.IOException ie){
                    /*
                     * Actually it will never happen.
                     */
                    throw new ParseException(-1, ParseException.ERROR_UNEXPECTED_EXCEPTION, ie);
            }
    }
    
    public Object parse(java.io.Reader in) throws java.io.IOException, ParseException{
            return parse(in, (ContainerFactory)null);
    }
    
    /**
     * Parse JSON text into java object from the input source.
     *      
     * @param in The Input Reader
 * @param containerFactory - Use this factory to createyour own JSON object and JSON array containers.
     * @return Instance of the following:
     *  org.json.simple.JSONObject,
     *      org.json.simple.JSONArray,
     *      java.lang.String,
     *      java.lang.Number,
     *      java.lang.Boolean,
     *      null
     * 
	 * @throws java.io.IOException Thrown if the Inputs are Invalid
     * 
	 * @throws ParseException Thrown if the Inputs are Invalid
     */
    @SuppressWarnings ({"rawtypes", "unchecked"}) public Object parse(java.io.Reader in, ContainerFactory containerFactory) throws java.io.IOException, ParseException{
            reset(in);
            java.util.LinkedList statusStack = new java.util.LinkedList();
            java.util.LinkedList valueStack = new java.util.LinkedList();
            
            try{
                    do{
                            nextToken();
                            switch(status){
                            case S_INIT:
                                    switch(token.type){
                                    case Yytoken.TYPE_VALUE:
                                            status=S_IN_FINISHED_VALUE;
                                            statusStack.addFirst(new Integer(status));
                                            valueStack.addFirst(token.value);
                                            break;
                                    case Yytoken.TYPE_LEFT_BRACE:
                                            status=S_IN_OBJECT;
                                            statusStack.addFirst(new Integer(status));
                                            valueStack.addFirst(createObjectContainer(containerFactory));
                                            break;
                                    case Yytoken.TYPE_LEFT_SQUARE:
                                            status=S_IN_ARRAY;
                                            statusStack.addFirst(new Integer(status));
                                            valueStack.addFirst(createArrayContainer(containerFactory));
                                            break;
                                    default:
                                            status=S_IN_ERROR;
                                    }//inner switch
                                    break;
                                    
                            case S_IN_FINISHED_VALUE:
                                    if(token.type==Yytoken.TYPE_EOF)
                                            return valueStack.removeFirst();
                                    else
                                            throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
                                    
                            case S_IN_OBJECT:
                                    switch(token.type){
                                    case Yytoken.TYPE_COMMA:
                                            break;
                                    case Yytoken.TYPE_VALUE:
                                            if(token.value instanceof String){
                                                    String key=(String)token.value;
                                                    valueStack.addFirst(key);
                                                    status=S_PASSED_PAIR_KEY;
                                                    statusStack.addFirst(new Integer(status));
                                            }
                                            else{
                                                    status=S_IN_ERROR;
                                            }
                                            break;
                                    case Yytoken.TYPE_RIGHT_BRACE:
                                            if(valueStack.size()>1){
                                                    statusStack.removeFirst();
                                                    valueStack.removeFirst();
                                                    status=peekStatus(statusStack);
                                            }
                                            else{
                                                    status=S_IN_FINISHED_VALUE;
                                            }
                                            break;
                                    default:
                                            status=S_IN_ERROR;
                                            break;
                                    }//inner switch
                                    break;
                                    
                            case S_PASSED_PAIR_KEY:
                                    switch(token.type){
                                    case Yytoken.TYPE_COLON:
                                            break;
                                    case Yytoken.TYPE_VALUE:
                                            statusStack.removeFirst();
                                            String key=(String)valueStack.removeFirst();
                                            java.util.Map parent=(java.util.Map)valueStack.getFirst();
                                            parent.put(key,token.value);
                                            status=peekStatus(statusStack);
                                            break;
                                    case Yytoken.TYPE_LEFT_SQUARE:
                                            statusStack.removeFirst();
                                            key=(String)valueStack.removeFirst();
                                            parent=(java.util.Map)valueStack.getFirst();
                                            java.util.List newArray=createArrayContainer(containerFactory);
                                            parent.put(key,newArray);
                                            status=S_IN_ARRAY;
                                            statusStack.addFirst(new Integer(status));
                                            valueStack.addFirst(newArray);
                                            break;
                                    case Yytoken.TYPE_LEFT_BRACE:
                                            statusStack.removeFirst();
                                            key=(String)valueStack.removeFirst();
                                            parent=(java.util.Map)valueStack.getFirst();
                                            java.util.Map newObject=createObjectContainer(containerFactory);
                                            parent.put(key,newObject);
                                            status=S_IN_OBJECT;
                                            statusStack.addFirst(new Integer(status));
                                            valueStack.addFirst(newObject);
                                            break;
                                    default:
                                            status=S_IN_ERROR;
                                    }
                                    break;
                                    
                            case S_IN_ARRAY:
                                    switch(token.type){
                                    case Yytoken.TYPE_COMMA:
                                            break;
                                    case Yytoken.TYPE_VALUE:
                                    	java.util.List val=(java.util.List)valueStack.getFirst();
                                            val.add(token.value);
                                            break;
                                    case Yytoken.TYPE_RIGHT_SQUARE:
                                            if(valueStack.size()>1){
                                                    statusStack.removeFirst();
                                                    valueStack.removeFirst();
                                                    status=peekStatus(statusStack);
                                            }
                                            else{
                                                    status=S_IN_FINISHED_VALUE;
                                            }
                                            break;
                                    case Yytoken.TYPE_LEFT_BRACE:
                                            val=(java.util.List)valueStack.getFirst();
                                            java.util.Map newObject=createObjectContainer(containerFactory);
                                            val.add(newObject);
                                            status=S_IN_OBJECT;
                                            statusStack.addFirst(new Integer(status));
                                            valueStack.addFirst(newObject);
                                            break;
                                    case Yytoken.TYPE_LEFT_SQUARE:
                                            val=(java.util.List)valueStack.getFirst();
                                            java.util.List newArray=createArrayContainer(containerFactory);
                                            val.add(newArray);
                                            status=S_IN_ARRAY;
                                            statusStack.addFirst(new Integer(status));
                                            valueStack.addFirst(newArray);
                                            break;
                                    default:
                                            status=S_IN_ERROR;
                                    }//inner switch
                                    break;
                            case S_IN_ERROR:
                                    throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
                            }//switch
                            if(status==S_IN_ERROR){
                                    throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
                            }
                    }while(token.type!=Yytoken.TYPE_EOF);
            }
            catch(java.io.IOException ie){
                    throw ie;
            }
            
            throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
    }
    
    private void nextToken() throws ParseException, java.io.IOException{
            token = lexer.yylex();
            if(token == null)
                    token = new Yytoken(Yytoken.TYPE_EOF, null);
    }
    
    @SuppressWarnings ("rawtypes") private java.util.Map createObjectContainer(ContainerFactory containerFactory){
            if(containerFactory == null)
                    return new org.drip.json.simple.JSONObject();
            java.util.Map m = containerFactory.createObjectContainer();
            
            if(m == null)
                    return new org.drip.json.simple.JSONObject();
            return m;
    }
    
    @SuppressWarnings ("rawtypes") private java.util.List createArrayContainer(ContainerFactory containerFactory){
            if(containerFactory == null)
                    return new org.drip.json.simple.JSONArray();
            java.util.List l = containerFactory.creatArrayContainer();
            
            if(l == null)
                    return new org.drip.json.simple.JSONArray();
            return l;
    }
    
    public void parse(String s, ContentHandler contentHandler) throws ParseException{
            parse(s, contentHandler, false);
    }
    
    public void parse(String s, ContentHandler contentHandler, boolean isResume) throws ParseException{
    	java.io.StringReader in=new java.io.StringReader(s);
            try{
                    parse(in, contentHandler, isResume);
            }
            catch(java.io.IOException ie){
                    /*
                     * Actually it will never happen.
                     */
                    throw new ParseException(-1, ParseException.ERROR_UNEXPECTED_EXCEPTION, ie);
            }
    }
    
    public void parse(java.io.Reader in, ContentHandler contentHandler) throws java.io.IOException, ParseException{
            parse(in, contentHandler, false);
    }
    
    /**
     * Stream processing of JSON text.
     * 
     * @see ContentHandler
     * 
     * @param in The Input Reader
     * @param contentHandler The Content Handler Instance
     * @param isResume - Indicates if it continues previous parsing operation.
 *                   If set to true, resume parsing the old stream, and parameter 'in' will be ignored. 
     *                   If this method is called for the first time in this instance, isResume will be ignored.
     * 
     * @throws java.io.IOException Thrown if the Inputs are Invalid
     * 
	 * @throws ParseException Thrown if the Inputs are Invalid
     */
    @SuppressWarnings ({"rawtypes", "unchecked"}) public void parse(java.io.Reader in, ContentHandler contentHandler, boolean isResume) throws java.io.IOException, ParseException{
            if(!isResume){
                    reset(in);
                    handlerStatusStack = new java.util.LinkedList();
            }
            else{
                    if(handlerStatusStack == null){
                            isResume = false;
                            reset(in);
                            handlerStatusStack = new java.util.LinkedList();
                    }
            }
            
            java.util.LinkedList statusStack = handlerStatusStack;    
            
            try{
                    do{
                            switch(status){
                            case S_INIT:
                                    contentHandler.startJSON();
                                    nextToken();
                                    switch(token.type){
                                    case Yytoken.TYPE_VALUE:
                                            status=S_IN_FINISHED_VALUE;
                                            statusStack.addFirst(new Integer(status));
                                            if(!contentHandler.primitive(token.value))
                                                    return;
                                            break;
                                    case Yytoken.TYPE_LEFT_BRACE:
                                            status=S_IN_OBJECT;
                                            statusStack.addFirst(new Integer(status));
                                            if(!contentHandler.startObject())
                                                    return;
                                            break;
                                    case Yytoken.TYPE_LEFT_SQUARE:
                                            status=S_IN_ARRAY;
                                            statusStack.addFirst(new Integer(status));
                                            if(!contentHandler.startArray())
                                                    return;
                                            break;
                                    default:
                                            status=S_IN_ERROR;
                                    }//inner switch
                                    break;
                                    
                            case S_IN_FINISHED_VALUE:
                                    nextToken();
                                    if(token.type==Yytoken.TYPE_EOF){
                                            contentHandler.endJSON();
                                            status = S_END;
                                            return;
                                    }
                                    else{
                                            status = S_IN_ERROR;
                                            throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
                                    }
                    
                            case S_IN_OBJECT:
                                    nextToken();
                                    switch(token.type){
                                    case Yytoken.TYPE_COMMA:
                                            break;
                                    case Yytoken.TYPE_VALUE:
                                            if(token.value instanceof String){
                                                    String key=(String)token.value;
                                                    status=S_PASSED_PAIR_KEY;
                                                    statusStack.addFirst(new Integer(status));
                                                    if(!contentHandler.startObjectEntry(key))
                                                            return;
                                            }
                                            else{
                                                    status=S_IN_ERROR;
                                            }
                                            break;
                                    case Yytoken.TYPE_RIGHT_BRACE:
                                            if(statusStack.size()>1){
                                                    statusStack.removeFirst();
                                                    status=peekStatus(statusStack);
                                            }
                                            else{
                                                    status=S_IN_FINISHED_VALUE;
                                            }
                                            if(!contentHandler.endObject())
                                                    return;
                                            break;
                                    default:
                                            status=S_IN_ERROR;
                                            break;
                                    }//inner switch
                                    break;
                                    
                            case S_PASSED_PAIR_KEY:
                                    nextToken();
                                    switch(token.type){
                                    case Yytoken.TYPE_COLON:
                                            break;
                                    case Yytoken.TYPE_VALUE:
                                            statusStack.removeFirst();
                                            status=peekStatus(statusStack);
                                            if(!contentHandler.primitive(token.value))
                                                    return;
                                            if(!contentHandler.endObjectEntry())
                                                    return;
                                            break;
                                    case Yytoken.TYPE_LEFT_SQUARE:
                                            statusStack.removeFirst();
                                            statusStack.addFirst(new Integer(S_IN_PAIR_VALUE));
                                            status=S_IN_ARRAY;
                                            statusStack.addFirst(new Integer(status));
                                            if(!contentHandler.startArray())
                                                    return;
                                            break;
                                    case Yytoken.TYPE_LEFT_BRACE:
                                            statusStack.removeFirst();
                                            statusStack.addFirst(new Integer(S_IN_PAIR_VALUE));
                                            status=S_IN_OBJECT;
                                            statusStack.addFirst(new Integer(status));
                                            if(!contentHandler.startObject())
                                                    return;
                                            break;
                                    default:
                                            status=S_IN_ERROR;
                                    }
                                    break;
                            
                            case S_IN_PAIR_VALUE:
                                    /*
                                     * S_IN_PAIR_VALUE is just a marker to indicate the end of an object entry, it doesn't proccess any token,
                                     * therefore delay consuming token until next round.
                                     */
                                    statusStack.removeFirst();
                                    status = peekStatus(statusStack);
                                    if(!contentHandler.endObjectEntry())
                                            return;
                                    break;
                                    
                            case S_IN_ARRAY:
                                    nextToken();
                                    switch(token.type){
                                    case Yytoken.TYPE_COMMA:
                                            break;
                                    case Yytoken.TYPE_VALUE:
                                            if(!contentHandler.primitive(token.value))
                                                    return;
                                            break;
                                    case Yytoken.TYPE_RIGHT_SQUARE:
                                            if(statusStack.size()>1){
                                                    statusStack.removeFirst();
                                                    status=peekStatus(statusStack);
                                            }
                                            else{
                                                    status=S_IN_FINISHED_VALUE;
                                            }
                                            if(!contentHandler.endArray())
                                                    return;
                                            break;
                                    case Yytoken.TYPE_LEFT_BRACE:
                                            status=S_IN_OBJECT;
                                            statusStack.addFirst(new Integer(status));
                                            if(!contentHandler.startObject())
                                                    return;
                                            break;
                                    case Yytoken.TYPE_LEFT_SQUARE:
                                            status=S_IN_ARRAY;
                                            statusStack.addFirst(new Integer(status));
                                            if(!contentHandler.startArray())
                                                    return;
                                            break;
                                    default:
                                            status=S_IN_ERROR;
                                    }//inner switch
                                    break;
                                    
                            case S_END:
                                    return;
                                    
                            case S_IN_ERROR:
                                    throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
                            }//switch
                            if(status==S_IN_ERROR){
                                    throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
                            }
                    }while(token.type!=Yytoken.TYPE_EOF);
            }
            catch(java.io.IOException ie){
                    status = S_IN_ERROR;
                    throw ie;
            }
            catch(ParseException pe){
                    status = S_IN_ERROR;
                    throw pe;
            }
            catch(RuntimeException re){
                    status = S_IN_ERROR;
                    throw re;
            }
            catch(Error e){
                    status = S_IN_ERROR;
                    throw e;
            }
            
            status = S_IN_ERROR;
            throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
    }
}
