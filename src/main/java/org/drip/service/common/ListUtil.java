
package org.drip.service.common;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
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
 * <i>ListUtil</i> implements Generic List Utility Functions used in DROP modules.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/common">Assorted Data Structures Support Utilities</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ListUtil<V>
{

	public static class ListNode<V>
	{
		private V _value = null;
		private ListNode<V> _next = null;

		/**
		 * ListNode Constructor
		 * 
		 * @param value Node Value
		 * @param next Next Node
		 */

		public ListNode (
			final V value,
			final ListNode<V> next)
		{
			_next = next;
			_value = value;
		}

		/**
		 * Retrieve the Node Value
		 * 
		 * @return The Node Value
		 */

		public V value()
		{
			return _value;
		}

		/**
		 * Retrieve the Next Node
		 * 
		 * @return The Next Node
		 */

		public ListNode<V> next()
		{
			return _next;
		}

		/**
		 * Set the Next Node
		 * 
		 * @param next The Next Node
		 * 
		 * @return TRUE - The Next Node successfully set
		 */

		public boolean setNext (
			final ListNode<V> next)
		{
			_next = next;
			return true;
		}
	}

	/**
	 * Given a linked list, rotate the list to the right by k places, where k is non-negative.
	 * 
	 * @param <V> Value Type
	 * @param head Old Head Node
	 * @param k Nodes to Shift By
	 * 
	 * @return The New Head Node
	 */

	public static <V> ListNode<V> Rotate (
		final ListNode<V> head,
		final int k)
	{
		if (null == head || 0 >= k)
		{
			return null;
		}

		int rotationCount = 0;
		ListNode<V> prevNode = null;
		ListNode<V> currentNode = head;

		ListNode<V> nextNode = head.next();

		while (++rotationCount < k)
		{
			if (null == nextNode)
			{
				return null;
			}

			prevNode = currentNode;
			currentNode = nextNode;

			nextNode = nextNode.next();
		}

		if (!prevNode.setNext (
			null
		))
		{
			return null;
		}

		ListNode<V> rotatedHead = currentNode;

		nextNode = currentNode.next();

		while (null != nextNode)
		{
			currentNode = nextNode;

			nextNode = nextNode.next();
		}

		return currentNode.setNext (
			head
		) ? rotatedHead : null;
	}
}
