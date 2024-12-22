
package org.drip.service.representation;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
 * <i>ItemList</i> is an Adaptation of the ItemList Interface from the RFC4627 compliant JSON Simple
 * 	(https://code.google.com/p/json-simple/).
 *
 * 		|a:b:c| = |a|,|b|,|c|
 * 		|:| = ||,||
 * 		|a:| = |a|,||
 *
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Split the String using the Separator</li>
 * 		<li><i>ItemList</i> Constructor #1</li>
 * 		<li><i>ItemList</i> Constructor #2</li>
 * 		<li>Split the String using the Separator</li>
 * 		<li><i>ItemList</i> Constructor #3</li>
 * 		<li>Retrieve the List of Items</li>
 * 		<li>Retrieve the Array of Items</li>
 * 		<li>Set the Separator</li>
 * 		<li>Add the Specified Item at the Location</li>
 * 		<li>Add the Specified Item</li>
 * 		<li>Add all the Items in the List</li>
 * 		<li>Add all the Items in the Input String #1</li>
 * 		<li>Add all the Items in the Input String #2</li>
 * 		<li>Add all the Items in the Input String #3</li>
 * 		<li>Retrieve the Indexed Item</li>
 * 		<li>Retrieve the Number of Items</li>
 * 		<li>Convert the Item List to String</li>
 * 		<li>Generate the Item-separated String</li>
 * 		<li>Clear the List</li>
 * 		<li>Reset the List</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/representation/README.md">RFC4627 Compliant JSON Message Object</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Fang Yidong
 * @author Lakshmi Krishnamurthy
 */

public class ItemList
{
    private String _separator = ",";

    @SuppressWarnings ("rawtypes") List items = new ArrayList();

    /**
     * Empty ItemList Constructor
     */

    public ItemList()
    {
    }

    /**
     * Split the String using the Separator
     * 
     * @param s Input String
     * @param separator Separator
     * @param appendList List to Append to
     */

    @SuppressWarnings ({"rawtypes", "unchecked"}) public void split (
		final String s,
		final String separator,
		final List appendList)
    {
        if (null == s || null == separator) {
            return;
        }

        int position = 0;
        int previousPosition = 0;

        do {
        	previousPosition = position;

        	position = s.indexOf (separator,position);

        	if (-1 == position) {
                break;
        	}

        	appendList.add (s.substring (previousPosition,position).trim());

        	position += separator.length();
        } while (-1 != position);

        appendList.add (s.substring (previousPosition).trim());
    }

    /**
     * <i>ItemList</i> Constructor #1
     * 
     * @param s Input String
     */

    public ItemList (
		final String s)
    {
        split (s, _separator, items);
    }

    /**
     * <i>ItemList</i> Constructor #2
     * 
     * @param s Input String
     * @param separator Separator
     */

    public ItemList (
		final String s,
		final String separator)
    {
    	_separator = s;

    	split (s, separator, items);
    }

    /**
     * Split the String using the Separator
     * 
     * @param s Input String
     * @param separator Separator
     * @param appendList List to Append to
     * @param isMultiToken TRUE - Token is Multiple
     */

    @SuppressWarnings ({"rawtypes", "unchecked"}) public void split (
		final String s,
		final String separator,
		final List appendList,
		final boolean isMultiToken)
    {
        if (null == s || null == separator) {
            return;
        }

        if (isMultiToken) {
        	StringTokenizer stringTokenizer = new StringTokenizer (s, separator);

        	while (stringTokenizer.hasMoreTokens()) {
                appendList.add (stringTokenizer.nextToken().trim());
            }
        } else {
            split (s, separator, appendList);
        }
    }

    /**
     * <i>ItemList</i> Constructor #3
     * 
     * @param s Input String
     * @param separator Separator
     * @param isMultiToken TRUE - Token is Multiple
     */

    public ItemList (
		final String s,
		final String separator,
		final boolean isMultiToken)
    {
        split (s, separator, items, isMultiToken);
    }

    /**
     * Retrieve the List of Items
     * 
     * @return List of Items
     */

    @SuppressWarnings ("rawtypes") public List getItems()
    {
        return items;
    }

    /**
     * Retrieve the Array of Items
     * 
     * @return Array of Items
     */

    public String[] getArray()
    {
        return (String[]) items.toArray();
    }

    /**
     * Set the Separator
     * 
     * @param separator The Separator
     */

    public void setSP (
		final String separator)
    {
    	_separator = separator;
    }

    /**
     * Add the Specified Item at the Location
     * 
     * @param i Location
     * @param item Item
     */
    
    @SuppressWarnings ("unchecked") public void add (
		final int i,
		final String item)
    {
        if (null == item) {
            return;
        }

        items.add (i, item.trim());
    }

    /**
     * Add the Specified Item
     * 
     * @param item Item
     */

    @SuppressWarnings ("unchecked") public void add (
		final String item)
    {
        if (null == item) {
            return;
        }

        items.add (item.trim());
    }

    /**
     * Add all the Items in the List #1
     * 
     * @param itemList List of Items
     */
    
    @SuppressWarnings ("unchecked") public void addAll (
		final ItemList itemList)
    {
        items.addAll (itemList.items);
    }

    /**
     * Add all the Items in the Input String #1
     * 
     * @param s Input String
     */
    
    public void addAll (
		final String s)
    {
        split (s, _separator, items);
    }

    /**
     * Add all the Items in the Input String #2
     * 
     * @param s Input String
     * @param separator Separator
     */
    
    public void addAll (
		final String s,
		final String separator)
    {
        split (s, separator, items);
    }

    /**
     * Add all the Items in the Input String #3
     * 
     * @param s Input String
     * @param separator Separator
     * @param isMultiToken TRUE - Multiple Token
     */

    public void addAll (
		final String s,
		final String separator,
		final boolean isMultiToken)
    {
        split (s, separator, items, isMultiToken);
    }

    /**
     * Retrieve the Indexed Item
     * 
     * @param i 0-based
     * 
     * @return i
     */

    public String get (
		final int i)
    {
        return (String) items.get (i);
    }

    /**
     * Retrieve the Number of Items
     * 
     * @return Number of Items
     */

    public int size()
    {
        return items.size();
    }

    @Override public String toString()
    {
        return toString (_separator);
    }

    /**
     * Generate the Item-separated String
     * 
     * @param separator Separator
     * 
     * @return Item-separated String
     */

    public String toString (
		final String separator)
    {
        StringBuffer stringBuffer = new StringBuffer();
        
        for (int itemIndex = 0; itemIndex < items.size(); ++itemIndex) {
            if (0 == itemIndex) {
            	stringBuffer.append (items.get (itemIndex));
            } else {
            	stringBuffer.append (separator);

            	stringBuffer.append (items.get (itemIndex));
            }
        }

        return stringBuffer.toString();
    }

    /**
     * Clear the List
     */

    public void clear()
    {
        items.clear();
    }

    /**
     * Reset the List
     */

    public void reset()
    {
    	_separator = ",";

    	items.clear();
    }
}
