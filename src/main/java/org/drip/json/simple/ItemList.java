
package org.drip.json.simple;

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
 * <i>ItemList</i> is an Adaptation of the ItemList Interface from the RFC4627 compliant JSON Simple
 *  (https://code.google.com/p/json-simple/).
 *
 * 		|a:b:c| = |a|,|b|,|c|
 * 		|:| = ||,||
 * 		|a:| = |a|,||
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json">JSON</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json/simple">Simple</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/AssetAllocation">Asset Allocation Library</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/MachineLearning">Machine Learning Library</a></li>
 *  </ul>
 *
 * @author Fang Yidong
 * @author Lakshmi Krishnamurthy
 */

public class ItemList {
    private String sp=",";
    @SuppressWarnings ("rawtypes") java.util.List items=new java.util.ArrayList();
    
    
    public ItemList(){}
    
    
    public ItemList(String s){
            this.split(s,sp,items);
    }
    
    public ItemList(String s,String sp){
            this.sp=s;
            this.split(s,sp,items);
    }
    
    public ItemList(String s,String sp,boolean isMultiToken){
            split(s,sp,items,isMultiToken);
    }
    
    @SuppressWarnings ("rawtypes") public java.util.List getItems(){
            return this.items;
    }
    
    public String[] getArray(){
            return (String[])this.items.toArray();
    }
    
    @SuppressWarnings ({"rawtypes", "unchecked"}) public void split(String s,String sp,java.util.List append,boolean isMultiToken){
            if(s==null || sp==null)
                    return;
            if(isMultiToken){
            	java.util.StringTokenizer tokens=new java.util.StringTokenizer(s,sp);
                    while(tokens.hasMoreTokens()){
                            append.add(tokens.nextToken().trim());
                    }
            }
            else{
                    this.split(s,sp,append);
            }
    }
    
    @SuppressWarnings ({"rawtypes", "unchecked"}) public void split(String s,String sp,java.util.List append){
            if(s==null || sp==null)
                    return;
            int pos=0;
            int prevPos=0;
            do{
                    prevPos=pos;
                    pos=s.indexOf(sp,pos);
                    if(pos==-1)
                            break;
                    append.add(s.substring(prevPos,pos).trim());
                    pos+=sp.length();
            }while(pos!=-1);
            append.add(s.substring(prevPos).trim());
    }
    
    public void setSP(String sp){
            this.sp=sp;
    }
    
    @SuppressWarnings ("unchecked") public void add(int i,String item){
            if(item==null)
                    return;
            items.add(i,item.trim());
    }

    @SuppressWarnings ("unchecked") public void add(String item){
            if(item==null)
                    return;
            items.add(item.trim());
    }
    
    @SuppressWarnings ("unchecked") public void addAll(ItemList list){
            items.addAll(list.items);
    }
    
    public void addAll(String s){
            this.split(s,sp,items);
    }
    
    public void addAll(String s,String sp){
            this.split(s,sp,items);
    }
    
    public void addAll(String s,String sp,boolean isMultiToken){
            this.split(s,sp,items,isMultiToken);
    }
    
    /**
     * @param i 0-based
     * @return i
     */
    public String get(int i){
            return (String)items.get(i);
    }
    
    public int size(){
            return items.size();
    }

    public String toString(){
            return toString(sp);
    }
    
    public String toString(String sp){
            StringBuffer sb=new StringBuffer();
            
            for(int i=0;i<items.size();i++){
                    if(i==0)
                            sb.append(items.get(i));
                    else{
                            sb.append(sp);
                            sb.append(items.get(i));
                    }
            }
            return sb.toString();

    }
    
    public void clear(){
            items.clear();
    }
    
    public void reset(){
            sp=",";
            items.clear();
    }
}
