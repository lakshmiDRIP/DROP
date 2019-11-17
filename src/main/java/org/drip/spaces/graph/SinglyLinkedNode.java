
package org.drip.spaces.graph;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>SinglyLinkedNode</i> implements the Node behind a Singly Linked List. The References are:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Knuth, D. (1973): <i>The Art of Computer Programming</i> <b>Addison-Wesley</b>
 *  	</li>
 *  	<li>
 *  		Oracle (2018): LinkedList (Java Platform SE 7)
 *  			https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018a): Linked List https://en.wikipedia.org/wiki/Linked_list
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018b): Doubly Linked List https://en.wikipedia.org/wiki/Doubly_linked_list
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018c): Linked Data Structure https://en.wikipedia.org/wiki/Linked_data_structure
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/graph/README.md">Graph Representation and Traversal Algorithms</a></li>
 *  </ul>
 * <br><br>
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (_value = value))
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (value))
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (value))
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (locationNodeValue) ||
			!org.drip.numerical.common.NumberUtil.IsValid (insertionNodeValue))
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (removalNodeValue))
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
