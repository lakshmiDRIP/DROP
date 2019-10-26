
package org.drip.json.simple;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * (https://code.google.com/p/json-simple/).
 *
 * 		|a:b:c| = |a|,|b|,|c|
 * 		|:| = ||,||
 * 		|a:| = |a|,||
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json">RFC-4627 Compliant JSON Encoder/Decoder (Parser)</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/json/simple">RFC4627 Compliant JSON Message Object</a></li>
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
