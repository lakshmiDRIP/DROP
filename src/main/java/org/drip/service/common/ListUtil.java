
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

		/**
		 * Set the Node Value
		 * 
		 * @param value The Node Value
		 * 
		 * @return TRUE - The Node Value successfully set
		 */

		public boolean setValue (
			final V value)
		{
			_value = value;
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

	/**
	 * Given a singly linked list, group all odd nodes together followed by the even nodes. Please note here
	 *  we are talking about the node number and not the value in the nodes.
	 *  
	 * You should try to do it in place. The program should run in O(1) space complexity and O(nodes) time
	 *  complexity.
	 *  
	 * @param <V> Value Type
	 * @param head Head Node
	 * 
	 * @return TRUE - Operation successfully completed
	 */

	public static <V> boolean OddEvenNodeShuffle (
		final ListNode<V> head)
	{
		ListNode<V> even = head.next();

		ListNode<V> prevOdd = head;
		ListNode<V> firstEven = even;

		while (null != even) {
			ListNode<V> nextOdd = even.next();

			if (null == nextOdd) {
				prevOdd.setNext (firstEven);

				break;
			}

			ListNode<V> nextEven = nextOdd.next();

			prevOdd.setNext (nextOdd);

			nextOdd.setNext (firstEven);

			even.setNext (nextEven);

			even = nextEven;
			prevOdd = nextOdd;
		}

		return true;
	}

	/**
	 * Write a program to find the node at which the intersection of two singly linked lists begins.
	 * 
	 * @param <V> Value Type
	 * 
	 * @param headNode1 Head Node of List #1
	 * @param headNode2 Head Node of List #2
	 * 
	 * @return Head Node of the Intersection List
	 */

	public static <V> ListNode<V> IntersectingNode (
		final ListNode<V> headNode1,
		final ListNode<V> headNode2)
	{
		ListNode<V> node1 = headNode1;
		ListNode<V> node2 = headNode2;

		java.util.HashSet<ListNode<V>> nodeHashSet = new java.util.HashSet<ListNode<V>>();

		while (null != node1) {
			if (nodeHashSet.contains (node1)) return node1;

			nodeHashSet.add (node1);

			node1 = node1.next();
		}

		while (null != node2) {
			if (nodeHashSet.contains (node2)) return node2;

			node2 = node2.next();
		}

		return null;
	}

	/**
	 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored
	 *  in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as
	 *  a linked list.
	 *  
	 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
	 * 
	 * @param headNode1 Head Node of List #1
	 * @param headNode2 Head Node of List #2
	 * 
	 * @return Head Node of the Added List
	 */

	public static final ListNode<java.lang.Integer> Add (
		final ListNode<java.lang.Integer> headNode1,
		final ListNode<java.lang.Integer> headNode2)
	{
		int carry = 0;
		ListNode<java.lang.Integer> node1 = headNode1;
		ListNode<java.lang.Integer> node2 = headNode2;
		ListNode<java.lang.Integer> additionHeadNode = null;
		ListNode<java.lang.Integer> additionPrevNode = null;

		while (null != node1 && null != node2) {
			int sum = carry + node1.value() + node2.value();

			ListNode<java.lang.Integer> additionNode = new ListNode<java.lang.Integer> (sum % 10, null);

			if (null != additionPrevNode) additionPrevNode.setNext (additionNode);

			if (null == additionHeadNode) additionHeadNode = additionNode;

			additionPrevNode = additionNode;
			carry = sum / 10;

			node1 = node1.next();

			node2 = node2.next();
		}

		while (null != node1) {
			int sum = carry + node1.value();

			ListNode<java.lang.Integer> additionNode = new ListNode<java.lang.Integer> (sum % 10, null);

			if (null != additionPrevNode) additionPrevNode.setNext (additionNode);

			if (null == additionHeadNode) additionHeadNode = additionNode;

			additionPrevNode = additionNode;
			carry = sum / 10;

			node1 = node1.next();
		}

		while (null != node2) {
			int sum = carry + node2.value();

			ListNode<java.lang.Integer> additionNode = new ListNode<java.lang.Integer> (sum % 10, null);

			if (null != additionPrevNode) additionPrevNode.setNext (additionNode);

			if (null == additionHeadNode) additionHeadNode = additionNode;

			additionPrevNode = additionNode;
			carry = sum / 10;

			node2 = node2.next();
		}

		return additionHeadNode;
	}

	public static final void main (
		final java.lang.String[] argumentArray)
		throws java.lang.Exception
	{
		ListNode<java.lang.Integer> list11 = new ListNode<java.lang.Integer> (2, null);

		ListNode<java.lang.Integer> list12 = new ListNode<java.lang.Integer> (4, null);

		ListNode<java.lang.Integer> list13 = new ListNode<java.lang.Integer> (3, null);

		list11.setNext (list12);

		list12.setNext (list13);

		ListNode<java.lang.Integer> list21 = new ListNode<java.lang.Integer> (5, null);

		ListNode<java.lang.Integer> list22 = new ListNode<java.lang.Integer> (6, null);

		ListNode<java.lang.Integer> list23 = new ListNode<java.lang.Integer> (4, null);

		list21.setNext (list22);

		list22.setNext (list23);

		ListNode<java.lang.Integer> node = Add (list11, list21);

		while (null != node) {
			System.out.print (node.value() + " -> ");

			node = node.next();
		}
	}
}
