
package org.drip.sample.json;

import java.io.*;
import java.util.*;

import org.drip.json.parser.*;
import org.drip.json.simple.*;
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
 * <i>Test</i> is an Adaptation of the Test Class from the RFC4627 compliant JSON Simple
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

public class Test {

    @SuppressWarnings ("rawtypes") public static final void testDecode() throws Exception{
            System.out.println("=======decode=======");
            
            String s="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
            Object obj=JSONValue.parse(s);
            JSONArray array=(JSONArray)obj;
            System.out.println("======the 2nd element of array======");
            System.out.println(array.get(1));
            System.out.println();
            System.out.println ("{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}".equalsIgnoreCase(array.get(1).toString()));
            
            JSONObject obj2=(JSONObject)array.get(1);
            System.out.println("======field \"1\"==========");
            System.out.println(obj2.get("1"));      
            System.out.println ("{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}".equalsIgnoreCase(obj2.get("1").toString()));
            
            s="{}";
            obj=JSONValue.parse(s);
            System.out.println ("{}".equalsIgnoreCase(obj.toString()));
            
            s="[5,]";
            obj=JSONValue.parse(s);
            System.out.println ("[5]".equalsIgnoreCase(obj.toString()));
            
            s="[5,,2]";
            obj=JSONValue.parse(s);
            System.out.println ("[5,2]".equalsIgnoreCase(obj.toString()));
            
            s="[\"hello\\bworld\\\"abc\\tdef\\\\ghi\\rjkl\\n123\\u4e2d\"]";
            obj=JSONValue.parse(s);
            System.out.println ("hello\bworld\"abc\tdef\\ghi\rjkl\n123中".equalsIgnoreCase(((List)obj).get(0).toString()));
            
            JSONParser parser = new JSONParser();
            s="{\"name\":";
            try{
                    obj = parser.parse(s);
            }
            catch(ParseException pe){
            	System.out.println (ParseException.ERROR_UNEXPECTED_TOKEN == pe.getErrorType());
            	System.out.println (8 == pe.getPosition());
            }
            
            s="{\"name\":}";
            try{
                    obj = parser.parse(s);
            }
            catch(ParseException pe){
                    System.out.println (ParseException.ERROR_UNEXPECTED_TOKEN == pe.getErrorType());
                    System.out.println (8 == pe.getPosition());
            }
            
            
            s="{\"name";
            try{
                    obj = parser.parse(s);
            }
            catch(ParseException pe){
                    System.out.println (ParseException.ERROR_UNEXPECTED_TOKEN == pe.getErrorType());
                    System.out.println (6 == pe.getPosition());
            }
            
            
            s = "[[null, 123.45, \"a\\\tb c\"}, true]";
            try{
                    parser.parse(s);
            }
            catch(ParseException pe){
                    System.out.println (24 == pe.getPosition());
                    System.out.println("Error at character position: " + pe.getPosition());
                    switch(pe.getErrorType()){
                    case ParseException.ERROR_UNEXPECTED_TOKEN:
                            System.out.println("Unexpected token: " + pe.getUnexpectedObject());
                            break;
                    case ParseException.ERROR_UNEXPECTED_CHAR:
                            System.out.println("Unexpected character: " + pe.getUnexpectedObject());
                            break;
                    case ParseException.ERROR_UNEXPECTED_EXCEPTION:
                            ((Exception)pe.getUnexpectedObject()).printStackTrace();
                            break;
                    }
            }
            
            s = "{\"first\": 123, \"second\": [4, 5, 6], \"third\": 789}";
            ContainerFactory containerFactory = new ContainerFactory(){
                    public List creatArrayContainer() {
                            return new LinkedList();
                    }

                    public Map createObjectContainer() {
                            return new LinkedHashMap();
                    }
                    
            };
            
            try{
                    Map json = (Map)parser.parse(s, containerFactory);
                    Iterator iter = json.entrySet().iterator();
                    System.out.println("==iterate result==");
                    while(iter.hasNext()){
                            Map.Entry entry = (Map.Entry)iter.next();
                            System.out.println(entry.getKey() + "=>" + entry.getValue());
                    }
                    
                    System.out.println("==toJSONString()==");                       
                    System.out.println(JSONValue.toJSONString(json));
                    System.out.println ("{\"first\":123,\"second\":[4,5,6],\"third\":789}".equalsIgnoreCase(JSONValue.toJSONString(json)));
            }
            catch(ParseException pe){
                    pe.printStackTrace();
            }
            
            s = "{\"first\": 123, \"second\": [{\"s1\":{\"s11\":\"v11\"}}, 4, 5, 6], \"third\": 789}";
            ContentHandler myHandler = new ContentHandler() {

                    public boolean endArray() throws ParseException {
                            System.out.println("endArray()");
                            return true;
                    }

                    public void endJSON() throws ParseException {
                            System.out.println("endJSON()");
                    }

                    public boolean endObject() throws ParseException {
                            System.out.println("endObject()");
                            return true;
                    }

                    public boolean endObjectEntry() throws ParseException {
                            System.out.println("endObjectEntry()");
                            return true;
                    }

                    public boolean primitive(Object value) throws ParseException {
                            System.out.println("primitive(): " + value);
                            return true;
                    }

                    public boolean startArray() throws ParseException {
                            System.out.println("startArray()");
                            return true;
                    }

                    public void startJSON() throws ParseException {
                            System.out.println("startJSON()");
                    }

                    public boolean startObject() throws ParseException {
                            System.out.println("startObject()");
                            return true;
                    }

                    public boolean startObjectEntry(String key) throws ParseException {
                            System.out.println("startObjectEntry(), key:" + key);
                            return true;
                    }
                    
            };
            try{
                    parser.parse(s, myHandler);
            }
            catch(ParseException pe){
                    pe.printStackTrace();
            }
    
    class KeyFinder implements ContentHandler{
        private Object value;
        private boolean found = false;
        private boolean end = false;
        private String key;
        private String matchKey;
        
        public void setMatchKey(String matchKey){
            this.matchKey = matchKey;
        }
        
        public Object getValue(){
            return value;
        }
        
        public boolean isEnd(){
            return end;
        }
        
        public void setFound(boolean found){
            this.found = found;
        }
        
        public boolean isFound(){
            return found;
        }
        
        public void startJSON() throws ParseException, IOException {
            found = false;
            end = false;
        }

        public void endJSON() throws ParseException, IOException {
            end = true;
        }

        public boolean primitive(Object value) throws ParseException, IOException {
            if(key != null){
                if(key.equals(matchKey)){
                    found = true;
                    this.value = value;
                    key = null;
                    return false;
                }
            }
            return true;
        }

        public boolean startArray() throws ParseException, IOException {
            return true;
        }

        
        public boolean startObject() throws ParseException, IOException {
            return true;
        }

        public boolean startObjectEntry(String key) throws ParseException, IOException {
            this.key = key;
            return true;
        }
        
        public boolean endArray() throws ParseException, IOException {
            return false;
        }

        public boolean endObject() throws ParseException, IOException {
            return true;
        }

        public boolean endObjectEntry() throws ParseException, IOException {
            return true;
        }
    };
    
    s = "{\"first\": 123, \"second\": [{\"k1\":{\"id\":\"id1\"}}, 4, 5, 6, {\"id\": 123}], \"third\": 789, \"id\": null}";
    parser.reset();
    KeyFinder keyFinder = new KeyFinder();
    keyFinder.setMatchKey("id");
    int i = 0;
    try{
        while(!keyFinder.isEnd()){
            parser.parse(s, keyFinder, true);
            if(keyFinder.isFound()){
                i++;
                keyFinder.setFound(false);
                System.out.println("found id:");
                System.out.println(keyFinder.getValue());
                if(i == 1)
                    System.out.println ("id1".equalsIgnoreCase((String)keyFinder.getValue()));
                if(i == 2){
                	System.out.println (keyFinder.getValue() instanceof Number);
                    System.out.println ("123".equalsIgnoreCase(String.valueOf(keyFinder.getValue())));
                }
                if(i == 3)
                	System.out.println (null == keyFinder.getValue());
            }
        }
    }
    catch(ParseException pe){
        pe.printStackTrace();
    }
    }
    
    @SuppressWarnings ({"rawtypes", "unchecked"}) public static final void testEncode() throws Exception{
            System.out.println("=======encode=======");
            
            JSONArray array1=new JSONArray();
            array1.add("abc\u0010a/");
            array1.add(Integer.toString(123));
            array1.add(Double.toString(222.123));
            array1.add(Boolean.toString(true));
            System.out.println("======array1==========");
            System.out.println(array1);
            System.out.println();
            System.out.println ("[\"abc\\u0010a\\/\",123,222.123,true]".equalsIgnoreCase(array1.toString()));
            
            JSONObject obj1=new JSONObject();
            obj1.put("name","fang");
            obj1.put("age",Integer.toString(27));
            obj1.put("is_developer", Boolean.toString(true));
            obj1.put("weight", Double.toString(60.21));
            obj1.put("array1",array1);
            System.out.println("======obj1 with array1===========");
            System.out.println(obj1);
            System.out.println();
            System.out.println ("{\"array1\":[\"abc\\u0010a\\/\",123,222.123,true],\"weight\":60.21,\"age\":27,\"name\":\"fang\",\"is_developer\":true}".equalsIgnoreCase(obj1.toString()));
            
            obj1.remove("array1");
            array1.add(obj1);
            System.out.println("======array1 with obj1========");
            System.out.println(array1);
            System.out.println();
            System.out.println ("[\"abc\\u0010a\\/\",123,222.123,true,{\"weight\":60.21,\"age\":27,\"name\":\"fang\",\"is_developer\":true}]".equalsIgnoreCase(array1.toString()));
    
            List list = new ArrayList();
            list.add("abc\u0010a/");
            list.add(Integer.toString (123));
            list.add(Double.toString (222.123));
            list.add(Boolean.toString (true));
            list.add(null);
            System.out.println("======list==========");
            System.out.println(JSONArray.toJSONString(list));
            System.out.println();
            System.out.println ("[\"abc\\u0010a\\/\",123,222.123,true,null]".equalsIgnoreCase(JSONArray.toJSONString(list)));
            
            Map map = new HashMap();
            map.put("name","fang");
            map.put("age", Integer.toString (27));
            map.put("is_developer", Boolean.toString (true));
            map.put("weight", Double.toString (60.21));
            map.put("array1",list);
            System.out.println("======map with list===========");
            System.out.println(map);
            System.out.println();
            System.out.println ("{\"array1\":[\"abc\\u0010a\\/\",123,222.123,true,null],\"weight\":60.21,\"age\":27,\"name\":\"fang\",\"is_developer\":true}".equalsIgnoreCase(JSONObject.toJSONString(map)));               
            
    Map m1 = new LinkedHashMap();
    Map m2 = new HashMap();
    List  l1 = new LinkedList();

    m1.put("k11","v11");
    m1.put("k12","v12");
    m1.put("k13", "v13");
    m2.put("k21","v21");
    m2.put("k22","v22");
    m2.put("k23","v23");
    l1.add(m1);
    l1.add(m2);
    String jsonString = JSONValue.toJSONString(l1);
    System.out.println(jsonString);
    System.out.println ("[{\"k11\":\"v11\",\"k12\":\"v12\",\"k13\":\"v13\"},{\"k22\":\"v22\",\"k21\":\"v21\",\"k23\":\"v23\"}]".equalsIgnoreCase(jsonString));

    StringWriter out = new StringWriter();
    JSONValue.writeJSONString(l1, out);
    jsonString = out.toString();
    System.out.println(jsonString);
    System.out.println ("[{\"k11\":\"v11\",\"k12\":\"v12\",\"k13\":\"v13\"},{\"k22\":\"v22\",\"k21\":\"v21\",\"k23\":\"v23\"}]".equalsIgnoreCase(jsonString));
    
    List l2 = new LinkedList();
    Map m3 = new LinkedHashMap();
    m3.put("k31", "v3");
    m3.put("k32", Double.toString (123.45));
    m3.put("k33", Boolean.toString (false));
    m3.put("k34", null);
    l2.add("vvv");
    l2.add("1.23456789123456789");
    l2.add(Boolean.toString (true));
    l2.add(null);
    m3.put("k35", l2);
    m1.put("k14", m3);
    out = new StringWriter();
    JSONValue.writeJSONString(l1, out);
    jsonString = out.toString();
    System.out.println(jsonString);
    System.out.println ("[{\"k11\":\"v11\",\"k12\":\"v12\",\"k13\":\"v13\",\"k14\":{\"k31\":\"v3\",\"k32\":123.45,\"k33\":false,\"k34\":null,\"k35\":[\"vvv\",\"1.23456789123456789\",true,null]}},{\"k22\":\"v22\",\"k21\":\"v21\",\"k23\":\"v23\"}]".equalsIgnoreCase(jsonString));
}

    public static final void main (
    	final String[] astrArgs)
    	throws Exception
    {
    	EnvManager.InitEnv ("");

    	testEncode();

    	testDecode();

    	EnvManager.TerminateEnv();
    }
}
