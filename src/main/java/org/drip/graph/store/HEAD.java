
package org.drip.graph.store;

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
 * <i>NODE</i> implements the Top-Level Head List of the Soft Heap. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Blum, M., R. W. Floyd, V. Pratt, R. L. Rivest, and R. E. Tarjan (1973): Time Bounds for Selection
 *  			<i> Journal of Computer and System Sciences</i> <b>7 (4)</b> 448-461
 *  	</li>
 *  	<li>
 *  		Chazelle, B. (2000): The Soft Heap: An Approximate Priority Queue with Optimal Error Rate
 *  			<i>Journal of the Association for Computing Machinery</i> <b>47 (6)</b> 1012-1027
 *  	</li>
 *  	<li>
 *  		Chazelle, B. (2000): A Minimum Spanning Tree Algorithm with Inverse-Ackerman Type Complexity
 *  			<i>Journal of the Association for Computing Machinery</i> <b>47 (6)</b> 1028-1047
 *  	</li>
 *  	<li>
 *  		Fredman, M. L., and R. E. Tarjan (1987): Fibonacci Heaps and their Uses in Improved Network
 *  			Optimization Algorithms <i>Journal of the Association for Computing Machinery</i> <b>34
 *  			(3)</b> 596-615
 *  	</li>
 *  	<li>
 *  		Vuillemin, J. (2000): A Data Structure for Manipulating Priority Queues <i>Communications of the
 *  			ACM</i> <b>21 (4)</b> 309-315
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/store/README.md">Graph Navigation Storage Data Structures</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class HEAD
{
	private HEAD _next = null;
	private HEAD _prev = null;
	private HEAD _suffixMin = null;
	private org.drip.graph.store.SoftQueueNode _queue = null;
	private double _rank = java.lang.Double.POSITIVE_INFINITY;

	/**
	 * HEAD Constructor
	 */

	public HEAD()
	{
	}

	/**
	 * Retrieve the Queue
	 * 
	 * @return The Queue
	 */

	public org.drip.graph.store.SoftQueueNode queue()
	{
		return _queue;
	}

	/**
	 * Retrieve the Next HEAD
	 * 
	 * @return The Next HEAD
	 */

	public HEAD next()
	{
		return _next;
	}

	/**
	 * Retrieve the Previous HEAD
	 * 
	 * @return The Previous HEAD
	 */

	public HEAD prev()
	{
		return _prev;
	}

	/**
	 * Retrieve the Minimum Suffix HEAD
	 * 
	 * @return The Minimum Suffix HEAD
	 */

	public HEAD suffixMin()
	{
		return _suffixMin;
	}

	/**
	 * Retrieve the Rank
	 * 
	 * @return The Rank
	 */

	public double rank()
	{
		return _rank;
	}

	/**
	 * Set the Head Soft Queue
	 * 
	 * @param queue The Head Soft Queue
	 * 
	 * @return TRUE - The Head Soft Queue successfully set
	 */

	public boolean setQueue (
		final SoftQueueNode queue)
	{
		_queue = queue;
		return true;
	}

	/**
	 * Set the Next HEAD List
	 * 
	 * @param next The Next HEAD List
	 * 
	 * @return TRUE - The Next HEAD List successfully set
	 */

	public boolean setNext (
		final HEAD next)
	{
		_next = next;
		return true;
	}

	/**
	 * Set the Previous HEAD List
	 * 
	 * @param prev The Previous HEAD List
	 * 
	 * @return TRUE - The Previous HEAD List successfully set
	 */

	public boolean setPrev (
		final HEAD prev)
	{
		_prev = prev;
		return true;
	}

	/**
	 * Set the Suffix Minimum HEAD List
	 * 
	 * @param suffixMin The Suffix Minimum HEAD List
	 * 
	 * @return TRUE - The Suffix Minimum HEAD List successfully set
	 */

	public boolean setSuffixMin (
		final HEAD suffixMin)
	{
		_suffixMin = suffixMin;
		return true;
	}

	/**
	 * Set the Rank
	 * 
	 * @param rank The Rank
	 * 
	 * @return TRUE - The Rank successfully set
	 */

	public boolean setRank (
		final double rank)
	{
		if (java.lang.Double.isNaN (
			rank
		))
		{
			return false;
		}

		_rank = rank;
		return true;
	}

	@Override public java.lang.String toString()
	{
		java.lang.String display = "{Rank = " + _rank + ";";

		display = display + " Queue = " + (null == _queue ? "null;" : _queue.toString() + ";");

		display = display + " Next = " + (null == _next ? "null;" : _next.toString() + ";");

		// display = display + " Prev = " + (null == _prev ? "null;" : _prev.toString() + ";");

		// display = display + " Suffix Min = " + (null == _suffixMin ? "null;" : _suffixMin.toString() + ";");

		return display + "}";
	}
}
