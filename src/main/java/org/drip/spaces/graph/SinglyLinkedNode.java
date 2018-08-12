
package org.drip.spaces.graph;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * SinglyLinkedNode implements the Node behind a Singly Linked List. The References are:
 *  
 *  1) Donald Knuth (1973): The Art of Computer Programming, Addison-Wesley.
 *  
 *  2) Oracle (2018): LinkedList (Java Platform SE 7),
 *  	https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html.
 *  
 *  3) Wikipedia (2018a): Linked List https://en.wikipedia.org/wiki/Linked_list.
 *  
 *  4) Wikipedia (2018b): Doubly Linked List https://en.wikipedia.org/wiki/Doubly_linked_list.
 *  
 *  5) Wikipedia (2018c): Linked Data Structure, https://en.wikipedia.org/wiki/Linked_data_structure.
 *
 * @author Lakshmi Krishnamurthy
 */

public class SinglyLinkedNode
{
	private SinglyLinkedNode _tail = null;
	private SinglyLinkedNode _adjacent = null;
	private double _value = java.lang.Double.NaN;

	/**
	 * Generate a FIFO Linked List from the Value Array
	 * 
	 * @param valueArray The Value Array
	 * 
	 * @return The FIFO Linked List
	 */

	public static final SinglyLinkedNode FIFOListFromArray (
		final double[] valueArray)
	{
		if (null == valueArray)
		{
			return null;
		}

		SinglyLinkedNode singlyLinkedNode = null;
		int arraySize = valueArray.length;

		if (0 == arraySize)
		{
			return null;
		}

		try
		{
			for (int valueIndex = 0; valueIndex < arraySize; ++valueIndex)
			{
				if (null == singlyLinkedNode)
				{
					singlyLinkedNode = new SinglyLinkedNode (
						valueArray[valueIndex],
						null
					);
				}
				else
				{
					if (null == singlyLinkedNode.addToTail (
						new SinglyLinkedNode (
							valueArray[valueIndex],
							null
						)
					))
					{
						return null;
					}
				}
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a LIFO Linked List from the Value Array
	 * 
	 * @param valueArray The Value Array
	 * 
	 * @return The LIFO Linked List
	 */

	public static final SinglyLinkedNode lIFOListFromArray (
		final double[] valueArray)
	{
		if (null == valueArray)
		{
			return null;
		}

		SinglyLinkedNode singlyLinkedNode = null;
		int arraySize = valueArray.length;

		if (0 == arraySize)
		{
			return null;
		}

		try
		{
			for (int valueIndex = 0; valueIndex < arraySize; ++valueIndex)
			{
				if (null == singlyLinkedNode)
				{
					singlyLinkedNode = new SinglyLinkedNode (
						valueArray[valueIndex],
						null
					);
				}
				else
				{
					if (null == singlyLinkedNode.addToHead (
						new SinglyLinkedNode (
							valueArray[valueIndex],
							null
						)
					))
					{
						return null;
					}
				}
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * SinglyLinkedNode Constructor
	 * 
	 * @param value The Linked Node Value
	 * @param adjacent The Adjacent Node
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SinglyLinkedNode (
		final double value,
		final SinglyLinkedNode adjacent)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_value = value))
		{
			throw new java.lang.Exception ("SinglyLinkedNodeConstructor => Invalid Inputs");
		}

		_adjacent = adjacent;
		_tail = this;
	}

	/**
	 * Retrieve the Linked Node Value
	 * 
	 * @return The Linked Node Value
	 */

	public double value()
	{
		return _value;
	}

	/**
	 * Retrieve the Adjacent Node
	 * 
	 * @return The Adjacent Node
	 */

	public SinglyLinkedNode adjacent()
	{
		return _adjacent;
	}

	/**
	 * Retrieve the Head Node
	 * 
	 * @return The Head Node
	 */

	public SinglyLinkedNode head()
	{
		return this;
	}

	/**
	 * Retrieve the Tail Node
	 * 
	 * @return The Tail Node
	 */

	public SinglyLinkedNode tail()
	{
		return _tail;
	}

	/**
	 * Append "This" to the Tail of the "Other"
	 * 
	 * @param otherList The "Other"
	 * 
	 * @return Appending/Addition successfully done
	 */

	public SinglyLinkedNode addToHead (
		final SinglyLinkedNode otherList)
	{
		if (null == otherList)
		{
			return null;
		}

		SinglyLinkedNode otherTail = otherList.tail();

		otherTail._tail = this;
		return otherTail;
	}

	/**
	 * Add "Other" to the Tail of "This"
	 * 
	 * @param otherList The "Other"
	 * 
	 * @return Appending/Addition successfully done
	 */

	public SinglyLinkedNode addToTail (
		final SinglyLinkedNode otherList)
	{
		_tail = otherList;
		return this;
	}

	/**
	 * Locate the Node that contains the specified Value
	 * 
	 * @param value The Value to Search for
	 * 
	 * @return The Node
	 */

	public SinglyLinkedNode locate (
		final double value)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (value))
		{
			return null;
		}

		SinglyLinkedNode node = this;

		while (null != node)
		{
			if (node.value() == value)
			{
				return node;
			}

			node = node.adjacent();
		}

		return null;
	}

	/**
	 * Check if the Node that containing the specified Value Exists
	 * 
	 * @param value The Value to Check for
	 * 
	 * @return TRUE - The Node Exists
	 */

	public boolean containsValue (
		final double value)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (value))
		{
			return false;
		}

		SinglyLinkedNode node = this;

		while (null != node)
		{
			if (node.value() == value)
			{
				return true;
			}

			node = node.adjacent();
		}

		return false;
	}

	/**
	 * Insert the given Value after the Specified Location Node
	 * 
	 * @param locationNodeValue The Location Node's Value
	 * @param insertionNodeValue The Value to be inserted
	 * 
	 * @return TRUE - The Value was successfully inserted
	 */

	public boolean insertAfter (
		final double locationNodeValue,
		final double insertionNodeValue)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (locationNodeValue) ||
			!org.drip.quant.common.NumberUtil.IsValid (insertionNodeValue))
		{
			return false;
		}

		SinglyLinkedNode locationNode = locate (locationNodeValue);

		if (null == locationNode)
		{
			return false;
		}

		SinglyLinkedNode locationNodeAdjacent = locationNode.adjacent();

		try
		{
			locationNode._adjacent = new SinglyLinkedNode (
				insertionNodeValue,
				locationNodeAdjacent
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Remove the Node at the specified Value
	 * 
	 * @param removalNodeValue The Node to be removed
	 * 
	 * @return The "Removed"
	 */

	public SinglyLinkedNode removeAt (
		final double removalNodeValue)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (removalNodeValue))
		{
			return null;
		}

		boolean nodeFound = false;
		SinglyLinkedNode priorNode = null;
		SinglyLinkedNode currentNode = this;

		while (null != currentNode)
		{
			if (currentNode.value() == removalNodeValue)
			{
				nodeFound = true;
				break;
			}

			priorNode = currentNode;

			currentNode = currentNode.adjacent();
		}

		if (!nodeFound)
		{
			return null;
		}

		if (null == priorNode)
		{
			try
			{
				return addToHead (
					new SinglyLinkedNode (
						removalNodeValue,
						currentNode.adjacent()
					)
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();
			}

			return null;
		}

		try
		{
			priorNode._adjacent = new SinglyLinkedNode (
				removalNodeValue,
				currentNode.adjacent()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		return this;
	}

	/**
	 * Convert the Linked List to an Array List
	 * 
	 * @return The Array List
	 */

	public java.util.List<java.lang.Double> toList()
	{
		java.util.List<java.lang.Double> valueList = new java.util.ArrayList<java.lang.Double>();

		SinglyLinkedNode node = this;

		while (null != node)
		{
			valueList.add (node.value());

			node = node.adjacent();
		}

		return valueList;
	}
}
