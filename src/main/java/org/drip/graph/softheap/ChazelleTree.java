
package org.drip.graph.softheap;

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
 * <i>ChazelleTree</i> implements the Top-Level Head List of the Chazelle Soft Heap. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/softheap/README.md">Soft Heap - Approximate Priority Queue</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ChazelleTree
{
	private ChazelleTree _next = null;
	private ChazelleTree _prev = null;
	private ChazelleTree _suffixExtremum = null;
	private org.drip.graph.softheap.ChazelleSoftQueueNode _root = null;

	/**
	 * Update the Suffix Extremum of all Trees at and preceding the specified Tree
	 * 
	 * @param tree Specified Tree
	 * 
	 * @return TRUE - Update Suffix Extremum successfully completed
	 */

	public static final boolean UpdateSuffixExtremum (
		ChazelleTree tree)
	{
		if (null == tree)
		{
			return false;
		}

		while (null != tree)
		{
			ChazelleTree next = tree.next();

			org.drip.graph.softheap.ChazelleSoftQueueNode currentRoot = tree.root();

			ChazelleTree nextSuffixExtremum = null == next ? null : next.suffixExtremum();

			if (null == nextSuffixExtremum ||
				1 != currentRoot.cEntry().compareTo (
					nextSuffixExtremum.root().cEntry()
				)
			)
			{
				tree.setSuffixExtremum (
					tree
				);
			}
			else
			{
				tree.setSuffixExtremum (
					nextSuffixExtremum
				);
			}

			tree = tree.prev();
		}

		return true;
	}

	/**
	 * ChazelleTree Constructor
	 * 
	 * @param root Root of the Current Tree
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ChazelleTree (
		final ChazelleSoftQueueNode root)
		throws java.lang.Exception
	{
		if (null == (_root = root))
		{
			throw new java.lang.Exception (
				"ChazelleTree Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Root
	 * 
	 * @return The Root
	 */

	public org.drip.graph.softheap.ChazelleSoftQueueNode root()
	{
		return _root;
	}

	/**
	 * Retrieve the Previous Tree
	 * 
	 * @return The Previous Tree
	 */

	public ChazelleTree prev()
	{
		return _prev;
	}

	/**
	 * Retrieve the Next Tree
	 * 
	 * @return The Next Tree
	 */

	public ChazelleTree next()
	{
		return _next;
	}
	/**
	 * Retrieve the Extremum ckey Tree among those following this in the List
	 * 
	 * @return The Extremum ckey Tree among those following this in the List
	 */

	public ChazelleTree suffixExtremum()
	{
		return _suffixExtremum;
	}

	/**
	 * Retrieve the Rank
	 * 
	 * @return The Rank
	 */

	public double rank()
	{
		return _root.k();
	}

	/**
	 * Set the Root Soft Queue
	 * 
	 * @param root The Root Soft Queue
	 * 
	 * @return TRUE - The Root Soft Queue successfully set
	 */

	public boolean setRoot (
		final ChazelleSoftQueueNode root)
	{
		_root = root;
		return true;
	}

	/**
	 * Set the Previous Tree
	 * 
	 * @param prev The Previous Tree
	 * 
	 * @return TRUE - The Previous Tree successfully set
	 */

	public boolean setPrev (
		final ChazelleTree prev)
	{
		_prev = prev;
		return true;
	}

	/**
	 * Set the Next Tree
	 * 
	 * @param next The Next Tree
	 * 
	 * @return TRUE - The Next Tree successfully set
	 */

	public boolean setNext (
		final ChazelleTree next)
	{
		_next = next;
		return true;
	}

	/**
	 * (Re-)set the Suffix Extremum Tree
	 * 
	 * @param suffixExtremum The Suffix Extremum
	 * 
	 * @return TRUE - The Suffix Extremum Tree successfully (re-)set
	 */

	public boolean setSuffixExtremum (
		final ChazelleTree suffixExtremum)
	{
		if (null == suffixExtremum)
		{
			return false;
		}

		_suffixExtremum = suffixExtremum;
		return true;
	}

	/**
	 * Generate a Stand-alone Tree with the Root Node alone in its List
	 *  
	 * @return Stand-alone Tree with the Root Node alone in its List
	 */

	public ChazelleTree rootTree()
	{
		try
		{
			return new ChazelleTree (
				_root
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
