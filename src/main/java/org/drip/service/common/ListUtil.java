
package org.drip.service.common;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
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

	/**
	 * <i>ListNode</i> inside of <i>ListUtil</i>.
	 */

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

	/**
	 * Imagine a small store that has exactly one turn-stile. It can be used by customers either as an
	 *  entrance or an exit. Sometimes multiple customers want to pass through the turn-stile and their
	 *  directions can be different. The i<sup>th</sup> customer comes to the turn-stile at time[i] and wants
	 *  to either exit the store if direction [i] = 1 or enter the store if direction[i] = 0. Customers form
	 *  2 queues, one to exit and one to enter. They are ordered by the time when they came to the turn-stile
	 *  and, if the times are equal, by their indices.
	 *  
	 * If one customer wants to enter the store and another customer wants to exit at the same moment, there
	 *  are three cases:
	 *   
	 * If in the previous second the turn-stile was not used (maybe it was used before, but not at the
	 *  previous second), then the customer who wants to exit goes first.
	 *  
	 * If in the previous second the turn-stile was used as an exit, then the customer who wants to leave
	 *  goes first.
	 *  
	 * If in the previous second the turn-stile was used as an entrance, then the customer who wants to enter
	 *  goes first.
	 *  
	 * Passing through the turn-stile takes 1 second.
	 * 
	 * Write an algorithm to find the time for each customer when they will pass through the turn-stile.

	 * @param arrivalTimeArray Array of Arrival Times
	 * @param directionArray Array of Entry/Exit Directions
	 * 
	 * @return Array of Pass Times
	 */

	public static final int[] TurnstilePassingTimeArray (
		final int[] arrivalTimeArray,
		final int[] directionArray)
	{
		int prevDirection = -1;
		int time = arrivalTimeArray[0];
		int[] turnstilePassingTimeArray = new int[arrivalTimeArray.length];

		java.util.TreeMap<Integer, java.util.List<Integer>> exitListMap =
			new java.util.TreeMap<Integer, java.util.List<Integer>>();

		java.util.TreeMap<Integer, java.util.List<Integer>> entryListMap =
			new java.util.TreeMap<Integer, java.util.List<Integer>>();

		for (int i = 0; i < arrivalTimeArray.length; ++i) {
			if (0 == directionArray[i]) {
				if (entryListMap.containsKey (arrivalTimeArray[i]))
					entryListMap.get (arrivalTimeArray[i]).add (i);
				else {
					java.util.List<Integer> entryIndexList = new java.util.ArrayList<Integer>();

					entryIndexList.add (i);

					entryListMap.put (arrivalTimeArray[i], entryIndexList);
				}
			} else if (1 == directionArray[i]) {
				if (exitListMap.containsKey (arrivalTimeArray[i]))
					exitListMap.get (arrivalTimeArray[i]).add (i);
				else {
					java.util.List<Integer> exitIndexList = new java.util.ArrayList<Integer>();

					exitIndexList.add (i);

					exitListMap.put (arrivalTimeArray[i], exitIndexList);
				}
			}
		}

		while (0 != entryListMap.size() || 0 != exitListMap.size()) {
			if (-1 == prevDirection || 1 == prevDirection) {
				boolean exitListNotEmpty = 0 != exitListMap.size();

				java.util.Map.Entry<Integer, java.util.List<Integer>> firstElement = exitListNotEmpty ?
					exitListMap.firstEntry() : entryListMap.firstEntry();

				int key = firstElement.getKey();

				java.util.List<Integer> firstIndexList = firstElement.getValue();

				int index = firstIndexList.get (0);

				firstIndexList.remove (0);

				if (exitListNotEmpty) {
					if (0 == firstIndexList.size()) exitListMap.remove (key);
				} else {
					if (0 == firstIndexList.size()) entryListMap.remove (key);
				}

				time = key > time ? key : time;
				prevDirection = exitListNotEmpty ? 1 : 0;
				turnstilePassingTimeArray[index] = time;
			} else if (0 == prevDirection) {
				boolean entryListNotEmpty = 0 != entryListMap.size();

				java.util.Map.Entry<Integer, java.util.List<Integer>> firstElement = entryListNotEmpty ?
					entryListMap.firstEntry() : exitListMap.firstEntry();

				int key = firstElement.getKey();

				java.util.List<Integer> firstIndexList = firstElement.getValue();

				int index = firstIndexList.get (0);

				firstIndexList.remove (0);

				if (entryListNotEmpty) {
					if (0 == firstIndexList.size()) entryListMap.remove (key);
				} else {
					if (0 == firstIndexList.size()) exitListMap.remove (key);
				}

				time = key > time ? key : time;
				prevDirection = entryListNotEmpty ? 0 : 1;
				turnstilePassingTimeArray[index] = time;
			}

			++time;
		}

		return turnstilePassingTimeArray;
	}

	/**
	 * Find the k post offices located closest to you, given your location and a list of locations of all
	 *  post offices available.
	 *  
	 * Locations are given in 2D coordinates in [X, Y], where X and Y are integers.
	 * 
	 * Euclidean distance is applied to find the distance between you and a post office.
	 * 
	 * Assume your location is [m, n] and the location of a post office is [p, q], the Euclidean distance
	 * 	between the office and you is SquareRoot((m - p) * (m - p) + (n - q) * (n - q)).
	 * 
	 * K is a positive integer much smaller than the given number of post offices. 
	 * 
	 * @param officeLocationList List of Office Coordinates
	 * @param k k Nearest Offices
	 * 
	 * @return List of Nearest Office Coordinates
	 */

	public static final java.util.List<int[]> NearestOffices (
		final java.util.List<int[]> officeLocationList,
		final int k)
	{
		java.util.HashMap<Double, java.util.ArrayList<int[]>> officeDistanceMap =
			new java.util.HashMap<Double, java.util.ArrayList<int[]>>();

		for (int[] officeLocation : officeLocationList) {
			double distance = Math.sqrt (officeLocation[0] * officeLocation[0] + officeLocation[1] *
				officeLocation[1]);

			if (officeDistanceMap.containsKey (distance))
				officeDistanceMap.get (distance).add (officeLocation);
			else {
				java.util.ArrayList<int[]> officeList = new java.util.ArrayList<int[]>();

				officeList.add (officeLocation);

				officeDistanceMap.put (distance, officeList);
			}
		}

		java.util.PriorityQueue<Double> nearestOfficeHeap = new
			java.util.PriorityQueue<Double>((x, y) -> Double.compare (y, x));

		for (double distance : officeDistanceMap.keySet()) {
			if (k < nearestOfficeHeap.size()) {
				if (nearestOfficeHeap.peek() > distance) nearestOfficeHeap.poll();
			}

			nearestOfficeHeap.offer (distance);
		}

		java.util.List<int[]> nearestOfficesList = new java.util.ArrayList<int[]>();

		int i = 0;
		boolean set = false;

		while (!nearestOfficeHeap.isEmpty()) {
			java.util.ArrayList<int[]> officeList = officeDistanceMap.get (nearestOfficeHeap.poll());

			for (int[] officeLocation : officeList) {
				if (set)
					nearestOfficesList.set (i, officeLocation);
				else
					nearestOfficesList.add (officeLocation);

				if (k == ++i) {
					i = 0;
					set = true;
				}
			}
		}

		return nearestOfficesList;
	}

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Argument Array
	 * 
	 * @throws java.lang.Exception The Exception Encountered
	 */

	public static final void main (
		final java.lang.String[] argumentArray)
		throws java.lang.Exception
	{
		java.util.ArrayList<int[]> officeLocationList = new java.util.ArrayList<int[]>();

		officeLocationList.add (new int[] {-16,  5});

		officeLocationList.add (new int[] { -1,  2});

		officeLocationList.add (new int[] {  4,  3});

		officeLocationList.add (new int[] { 10, -2});

		officeLocationList.add (new int[] {  0,  3});

		officeLocationList.add (new int[] { -5, -9});

		for (int[] nearestOfficeLocation : NearestOffices (officeLocationList, 3))
			System.out.print ("[" + nearestOfficeLocation[0] + ", " + nearestOfficeLocation[1] + "] ");
	}
}
