
package org.drip.service.jsonparser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.drip.service.representation.JSONArray;
import org.drip.service.representation.JSONObject;

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
 * <i>LexicalProcessor</i> is an Adaptation of the JSONParser Class from the RFC4627 compliant JSON Simple
 * (https://code.google.com/p/json-simple/). It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Processor Initialized</li>
 * 		<li>Processor Initialized with Value</li>
 * 		<li>Processor Initialized with Object</li>
 * 		<li>Processor Initialized with Array</li>
 * 		<li>Processor Initialized with Key Pair</li>
 * 		<li>Processor Initialized with Value Pair</li>
 * 		<li>Processor Finished</li>
 * 		<li>Processor In Error</li>
 * 		<li>Reset the parser to the initial state without resetting the underlying reader</li>
 * 		<li>Reset the parser to the initial state with a new character reader</li>
 * 		<li>Retrieve the position of the beginning of the current token</li>
 * 		<li>Parse JSON text into java object from the input source</li>
 * 		<li>Parse the JSON String</li>
 * 		<li>Parse an Object from the String</li>
 * 		<li>Parse from the Input Reader</li>
 * 		<li>Stream processing of JSON text</li>
 * 		<li>Parse the String using the specified Content Handler #1</li>
 * 		<li>Parse the String using the specified Content Handler #2</li>
 * 		<li>Parse from the Input Reader using the specified Content Handler</li>
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

public class LexicalProcessor
{

	/**
	 * Processor Initialized
	 */

	public static final int S_INIT = 0;

	/**
	 * Processor Initialized with Value
	 */

    public static final int S_IN_FINISHED_VALUE = 1; // string, number, boolean, null, object, array

	/**
	 * Processor Initialized with Object
	 */

    public static final int S_IN_OBJECT = 2;

	/**
	 * Processor Initialized with Array
	 */

    public static final int S_IN_ARRAY = 3;

	/**
	 * Processor Initialized with Key Pair
	 */

    public static final int S_PASSED_PAIR_KEY = 4;

	/**
	 * Processor Initialized with Value Pair
	 */

    public static final int S_IN_PAIR_VALUE = 5;

	/**
	 * Processor Finished
	 */

    public static final int S_END = 6;

	/**
	 * Processor In Error
	 */

    public static final int S_IN_ERROR = -1;
    
    @SuppressWarnings ("rawtypes") private LinkedList _handlerStatusStack = null;

    private Yylex _yylexer = new Yylex ((Reader) null);
    private Yytoken _yyToken = null;
    private int _status = S_INIT;
    
    @SuppressWarnings ("rawtypes") private int peekStatus (
		final LinkedList statusStackLinkedList)
    {
        return 0 == statusStackLinkedList.size() ?
    		-1 : ((Integer) statusStackLinkedList.getFirst()).intValue();
    }

    private void nextToken()
		throws ParseException, IOException
    {
        if (null == (_yyToken = _yylexer.yylex())) {
        	_yyToken = new Yytoken (Yytoken.TYPE_EOF, null);
        }
    }

    @SuppressWarnings ("rawtypes") private Map createObjectContainer (
		final ContainerFactory containerFactory)
    {
        if (null == containerFactory) {
            return new JSONObject();
        }

        Map m = containerFactory.createObjectContainer();

        return null == m ? new JSONObject() : m;
    }

    @SuppressWarnings ("rawtypes") private List createArrayContainer (
		final ContainerFactory containerFactory)
    {
        if (null == containerFactory) {
            return new JSONArray();
        }

        List list = containerFactory.creatArrayContainer();

        return null == list ? new JSONArray() : list;
    }

	/**
	 * Reset the parser to the initial state without resetting the underlying reader
	 */

    public void reset()
    {
		_yyToken = null;
		_status = S_INIT;
	    _handlerStatusStack = null;
	}

	/**
	 * Reset the parser to the initial state with a new character reader.
	 * 
	 * @param reader - The new character reader.
	 */

    public void reset (
		final Reader reader)
    {
    	_yylexer.yyreset (reader);

    	reset();
    }
    
    /**
     * Retrieve the position of the beginning of the current token.
     * 
     * @return The position of the beginning of the current token.
     */

    public int getPosition()
    {
        return _yylexer.getPosition();
    }

    /**
     * Parse JSON text into java object from the input source.
     *      
     * @param inputReader The Input Reader
	 * @param containerFactory - Use this factory to createyour own JSON object and JSON array containers.
     * @return Instance of the following:
     *  org.json.simple.JSONObject,
     *      org.json.simple.JSONArray,
     *      java.lang.String,
     *      java.lang.Number,
     *      java.lang.Boolean,
     *      null
     * 
	 * @throws IOException Thrown if the Inputs are Invalid
     * 
	 * @throws ParseException Thrown if the Inputs are Invalid
     */

    @SuppressWarnings ({"rawtypes", "unchecked"}) public Object parse (
		final Reader inputReader,
		final ContainerFactory containerFactory)
		throws IOException, ParseException
    {
        reset (inputReader);

        LinkedList valueStackLinkedList = new LinkedList();

        LinkedList statusStackLinkedList = new LinkedList();

        try {
            do {
                nextToken();

                switch (_status) {
                    case S_INIT:
                        switch (_yyToken.type) {
	                        case Yytoken.TYPE_VALUE:
                                statusStackLinkedList.addFirst (_status = S_IN_FINISHED_VALUE);

                                valueStackLinkedList.addFirst (_yyToken.value);

                                break;

	                        case Yytoken.TYPE_LEFT_BRACE:
                                statusStackLinkedList.addFirst (_status = S_IN_OBJECT);

                                valueStackLinkedList.addFirst (createObjectContainer (containerFactory));

                                break;

	                        case Yytoken.TYPE_LEFT_SQUARE:

                                statusStackLinkedList.addFirst (_status = S_IN_ARRAY);

                                valueStackLinkedList.addFirst (createArrayContainer (containerFactory));

                                break;

	                        default:
	                        	_status = S_IN_ERROR;
                        } // inner switch

                        break;

                    case S_IN_FINISHED_VALUE:
                        if (Yytoken.TYPE_EOF == _yyToken.type) {
                            return valueStackLinkedList.removeFirst();
                        }

                        throw new ParseException (
                    		getPosition(),
                    		ParseException.ERROR_UNEXPECTED_TOKEN,
                    		_yyToken
                		);

                    case S_IN_OBJECT:
                        switch (_yyToken.type) {
                            case Yytoken.TYPE_COMMA:
                                break;

                            case Yytoken.TYPE_VALUE:
                                if (_yyToken.value instanceof String) {
                                    valueStackLinkedList.addFirst ((String) _yyToken.value);

                                    statusStackLinkedList.addFirst (_status = S_PASSED_PAIR_KEY);
                                } else {
                                	_status = S_IN_ERROR;
                                }

                                break;

                            case Yytoken.TYPE_RIGHT_BRACE:
                                if (1 < valueStackLinkedList.size()) {
                                    statusStackLinkedList.removeFirst();

                                    valueStackLinkedList.removeFirst();

                                    _status = peekStatus (statusStackLinkedList);
                                } else {
                                	_status = S_IN_FINISHED_VALUE;
                                }

                                break;

                            default:
                            	_status = S_IN_ERROR;

                            	break;
                            } // inner switch

                        break;

                    case S_PASSED_PAIR_KEY:
                        switch (_yyToken.type) {
                            case Yytoken.TYPE_COLON:
                                break;

                            case Yytoken.TYPE_VALUE:
                                statusStackLinkedList.removeFirst();

                                ((Map) valueStackLinkedList.getFirst()).put (
                            		(String)valueStackLinkedList.removeFirst(),
                            		_yyToken.value
                        		);

                                _status = peekStatus (statusStackLinkedList);

                                break;

                            case Yytoken.TYPE_LEFT_SQUARE:
                                statusStackLinkedList.removeFirst();

                                List newArray = createArrayContainer (containerFactory);

                                ((Map) valueStackLinkedList.getFirst()).put (
                            		(String) valueStackLinkedList.removeFirst(),
                            		newArray
                        		);

                                _status = S_IN_ARRAY;

                                statusStackLinkedList.addFirst (_status);

                                valueStackLinkedList.addFirst (newArray);

                                break;

                            case Yytoken.TYPE_LEFT_BRACE:
                                statusStackLinkedList.removeFirst();

                                Map newObject = createObjectContainer (containerFactory);

                                ((Map) valueStackLinkedList.getFirst()).put (
                            		(String) valueStackLinkedList.removeFirst(),
                            		newObject
                        		);

                                statusStackLinkedList.addFirst (_status = S_IN_OBJECT);

                                valueStackLinkedList.addFirst (newObject);

                                break;

                            default:
                            	_status = S_IN_ERROR;
                            }

                        break;

                    case S_IN_ARRAY:
                        switch (_yyToken.type) {
                            case Yytoken.TYPE_COMMA:
                                break;

                            case Yytoken.TYPE_VALUE:
                                ((List) valueStackLinkedList.getFirst()).add (_yyToken.value);

                                break;

                            case Yytoken.TYPE_RIGHT_SQUARE:
                                if (1 < valueStackLinkedList.size()) {
                                    statusStackLinkedList.removeFirst();

                                    valueStackLinkedList.removeFirst();

                                    _status = peekStatus (statusStackLinkedList);
                                } else {
                                	_status = S_IN_FINISHED_VALUE;
                                }

                                break;

                            case Yytoken.TYPE_LEFT_BRACE:
                                Map newObject = createObjectContainer (containerFactory);

                                ((List) valueStackLinkedList.getFirst()).add (newObject);

                                statusStackLinkedList.addFirst (_status = S_IN_OBJECT);

                                valueStackLinkedList.addFirst (newObject);

                                break;

                            case Yytoken.TYPE_LEFT_SQUARE:
                                List newArray = createArrayContainer (containerFactory);

                                ((List) valueStackLinkedList.getFirst()).add (newArray);

                                statusStackLinkedList.addFirst (_status = S_IN_ARRAY);

                                valueStackLinkedList.addFirst (newArray);

                                break;

                            default:
                            	_status = S_IN_ERROR;
                            } // inner switch

                        break;

                    case S_IN_ERROR:
                        throw new ParseException (
                    		getPosition(),
                    		ParseException.ERROR_UNEXPECTED_TOKEN,
                    		_yyToken
                		);
                    } // switch

                if (S_IN_ERROR == _status) {
                    throw new ParseException (
                		getPosition(),
                		ParseException.ERROR_UNEXPECTED_TOKEN,
                		_yyToken
            		);
                }
            } while (Yytoken.TYPE_EOF != _yyToken.type);
        } catch (IOException ie) {
            throw ie;
        }

        throw new ParseException (getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, _yyToken);
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

    public Object parse (
		final String s,
		final ContainerFactory containerFactory)
		throws ParseException
    {
    	StringReader in = new StringReader (s);

    	try {
            return parse(in, containerFactory);
        } catch (IOException ie) {

        	/*
             * Actually it will never happen.
             */

        	throw new ParseException (-1, ParseException.ERROR_UNEXPECTED_EXCEPTION, ie);
        }
    }

    /**
     * Parse an Object from the String
     * 
     * @param s Input String
     * 
     * @return The Parsed Object
     * 
     * @throws ParseException Thrown if the Parsing cannot be completed
     */

    public Object parse (
		final String s)
		throws ParseException
    {
        return parse (s, (ContainerFactory) null);
    }

    /**
     * Parse from the Input Reader
     * 
     * @param in The Input Reader
     * 
     * @return The Parsed Object
     * 
     * @throws IOException Thrown if the IOException is encountered
     * 
     * @throws ParseException Thrown if the Inputs are Invalid
     */

    public Object parse (
		final Reader in)
		throws IOException, ParseException
    {
        return parse (in, (ContainerFactory) null);
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
     * @throws IOException Thrown if the Inputs are Invalid
     * 
	 * @throws ParseException Thrown if the Inputs are Invalid
     */

    @SuppressWarnings ({"rawtypes", "unchecked"}) public void parse (
		final Reader in,
		final ContentHandler contentHandler,
		boolean isResume)
		throws IOException, ParseException
    {
        if (!isResume) {
            reset (in);

            _handlerStatusStack = new LinkedList();
        } else {
            if (null == _handlerStatusStack) {
                isResume = false;

                reset (in);

                _handlerStatusStack = new LinkedList();
            }
        }

        LinkedList statusStack = _handlerStatusStack;

        try {
            do {
                switch (_status) {
	                case S_INIT:
                        contentHandler.startJSON();

                        nextToken();

                        switch (_yyToken.type) {
	                        case Yytoken.TYPE_VALUE:
	                        	statusStack.addFirst (_status = S_IN_FINISHED_VALUE);

	                        	if (!contentHandler.primitive (_yyToken.value)) {
                                    return;
	                        	}

	                        	break;

	                        case Yytoken.TYPE_LEFT_BRACE:
                                statusStack.addFirst (_status = S_IN_OBJECT);

                                if (!contentHandler.startObject()) {
                                    return;
                                }

                                break;

	                        case Yytoken.TYPE_LEFT_SQUARE:
                                statusStack.addFirst (_status = S_IN_ARRAY);

                                if (!contentHandler.startArray()) {
                                    return;
                                }

                                break;

	                        default:
	                        	_status = S_IN_ERROR;
                        } // inner switch

                        break;

	                case S_IN_FINISHED_VALUE:
                        nextToken();

                        if (Yytoken.TYPE_EOF == _yyToken.type) {
                            contentHandler.endJSON();

                            _status = S_END;
                            return;
                        }

                    	_status = S_IN_ERROR;

                    	throw new ParseException (
                			getPosition(),
                			ParseException.ERROR_UNEXPECTED_TOKEN,
                			_yyToken
            			);

	                case S_IN_OBJECT:
                        nextToken();

                        switch (_yyToken.type) {
                        	case Yytoken.TYPE_COMMA:
                                break;

                        	case Yytoken.TYPE_VALUE:
                                if (_yyToken.value instanceof String) {
                                    statusStack.addFirst (_status = S_PASSED_PAIR_KEY);

                                    if (!contentHandler.startObjectEntry ((String) _yyToken.value)) {
                                        return;
                                    }
                                } else {
                                	_status = S_IN_ERROR;
                                }

                                break;

                        	case Yytoken.TYPE_RIGHT_BRACE:
                                if (1 < statusStack.size()) {
                                    statusStack.removeFirst();

                                    _status = peekStatus (statusStack);
                                } else {
                                	_status = S_IN_FINISHED_VALUE;
                                }

                                if (!contentHandler.endObject()) {
                                    return;
                                }

                                break;

                        	default:
                        		_status = S_IN_ERROR;
                                break;
                        } // inner switch

                        break;
                        
	                case S_PASSED_PAIR_KEY:
                        nextToken();

                        switch (_yyToken.type) {
                        	case Yytoken.TYPE_COLON:
                                break;

                        	case Yytoken.TYPE_VALUE:
                                statusStack.removeFirst();

                                _status = peekStatus (statusStack);

                                if (!contentHandler.primitive (_yyToken.value) ||
                            		!contentHandler.endObjectEntry())
                                {
                                    return;
                                }

                                break;

                        	case Yytoken.TYPE_LEFT_SQUARE:
                                statusStack.removeFirst();

                                statusStack.addFirst (S_IN_PAIR_VALUE);

                                statusStack.addFirst (_status = S_IN_ARRAY);

                                if (!contentHandler.startArray()) {
                                    return;
                                }

                                break;

                        	case Yytoken.TYPE_LEFT_BRACE:
                                statusStack.removeFirst();

                                statusStack.addFirst (S_IN_PAIR_VALUE);

                                statusStack.addFirst (_status = S_IN_OBJECT);

                                if (!contentHandler.startObject()) {
                                    return;
                                }

                                break;

                        	default:
                        		_status = S_IN_ERROR;
                        }

                        break;

	                case S_IN_PAIR_VALUE:

	                	/*
                         * S_IN_PAIR_VALUE is just a marker to indicate the end of an object entry, it
                         * 	doesn't proccess any token, therefore delay consuming token until next round.
                         */

	                	statusStack.removeFirst();

	                	_status = peekStatus (statusStack);

	                	if (!contentHandler.endObjectEntry()) {
                            return;
	                	}

	                	break;

	                case S_IN_ARRAY:
                        nextToken();

                        switch (_yyToken.type) {
                        	case Yytoken.TYPE_COMMA:
                                break;

                        	case Yytoken.TYPE_VALUE:
                                if (!contentHandler.primitive (_yyToken.value)) {
                                    return;
                                }

                                break;

                        	case Yytoken.TYPE_RIGHT_SQUARE:
                                if (1 < statusStack.size()) {
                                    statusStack.removeFirst();

                                    _status = peekStatus (statusStack);
                                } else {
                                	_status = S_IN_FINISHED_VALUE;
                                }

                                if (!contentHandler.endArray()) {
                                    return;
                                }

                                break;

                        	case Yytoken.TYPE_LEFT_BRACE:
                                statusStack.addFirst (_status = S_IN_OBJECT);

                                if (!contentHandler.startObject()) {
                                    return;
                                }

                                break;

                        	case Yytoken.TYPE_LEFT_SQUARE:
                                statusStack.addFirst (_status = S_IN_ARRAY);

                                if (!contentHandler.startArray()) {
                                    return;
                                }

                                break;

                        	default:
                        		_status = S_IN_ERROR;
                        } // inner switch

                        break;

	                case S_END:
                        return;

	                case S_IN_ERROR:
                        throw new ParseException (
                    		getPosition(),
                    		ParseException.ERROR_UNEXPECTED_TOKEN,
                    		_yyToken
                		);
                } // switch

                if (S_IN_ERROR == _status) {
                    throw new ParseException (
                		getPosition(),
                		ParseException.ERROR_UNEXPECTED_TOKEN,
                		_yyToken
            		);
                }
            } while (Yytoken.TYPE_EOF != _yyToken.type);
        } catch (IOException ie) {
        	_status = S_IN_ERROR;
        	throw ie;
        } catch (ParseException pe) {
        	_status = S_IN_ERROR;
            throw pe;
        } catch (RuntimeException re) {
        	_status = S_IN_ERROR;
            throw re;
        } catch (Error e) {
        	_status = S_IN_ERROR;
            throw e;
        }

        _status = S_IN_ERROR;

        throw new ParseException (getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, _yyToken);
    }

    /**
     * Parse the String using the specified Content Handler #1
     * 
     * @param s Input String
     * @param contentHandler The Content Handler
     * @param isResume TRUE - Resume from where it was left
     * 
     * @throws ParseException Thrown if Parser Exception encountered
     */

    public void parse (
		final String s,
		final ContentHandler contentHandler,
		final boolean isResume)
		throws ParseException
    {
    	StringReader in = new StringReader(s);

    	try {
            parse (in, contentHandler, isResume);
        } catch (IOException ie) {

        	/*
             * Actually it will never happen.
             */

        	throw new ParseException (-1, ParseException.ERROR_UNEXPECTED_EXCEPTION, ie);
        }
    }

    /**
     * Parse the String using the specified Content Handler #2
     * 
     * @param s Input String
     * @param contentHandler The Content Handler
     * 
     * @throws ParseException Thrown if Parser Exception encountered
     */
    
    public void parse (
		final String s,
		final ContentHandler contentHandler)
		throws ParseException
    {
        parse (s, contentHandler, false);
    }

    /**
     * Parse from the Input Reader using the specified Content Handler
     * 
     * @param in Input Reader
     * @param contentHandler The Content Handler
     * 
     * @throws IOException Thrown if the IOException is encountered
     * 
     * @throws ParseException Thrown if Parser Exception encountered
     */
    
    public void parse (
		final Reader in,
		final ContentHandler contentHandler)
		throws IOException, ParseException
    {
        parse (in, contentHandler, false);
    }
}
