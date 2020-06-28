
package org.drip.graph.selection;

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
 * <i>IntroselectControl</i> contains the Introselect-based Control Schemes to augment Quickselect. The
 * 	References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Hoare, C. A. R. (1961): Algorithm 65: Find <i>Communications of the ACM</i> <b>4 (1)</b> 321-322
 *  	</li>
 *  	<li>
 *  		Knuth, D. (1997): <i>The Art of Computer Programming 3<sup>rd</sup> Edition</i>
 *  			<b>Addison-Wesley</b>
 *  	</li>
 *  	<li>
 *  		Musser, D. R. (1997): Introselect Sorting and Selection Algorithms <i>Software: Practice and
 *  			Experience</i> <b>27 (8)</b> 983-993
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Quickselect https://en.wikipedia.org/wiki/Quickselect
 *  	</li>
 *  	<li>
 *  		Wikipedia (2020): Introselect https://en.wikipedia.org/wiki/Introselect
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/selection/README.md">k<sup>th</sup> Order Statistics Selection Scheme</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class IntroselectControl
{
	private int _problemSize = 0;
	private int _iterationCount = 0;
	private int _subPartitionReductionLimit = -1;
	private double _subPartitionCumulativeSize = 0;
	private double _subPartitionCumulativeLimit = 0;
	private double _subPartitionCumulativeFactor = java.lang.Double.NaN;

	/**
	 * IntroselectControl Constructor
	 * 
	 * @param subPartitionReductionLimit Sub-partition Reduction Limit
	 * @param subPartitionCumulativeFactor Sub-partition Cumulative Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public IntroselectControl (
		final int subPartitionReductionLimit,
		final double subPartitionCumulativeFactor)
		throws java.lang.Exception
	{
		if (1 >= (_subPartitionReductionLimit = subPartitionReductionLimit) ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				_subPartitionCumulativeFactor = subPartitionCumulativeFactor
			) || 1. >= _subPartitionCumulativeFactor
		)
		{
			throw new java.lang.Exception (
				"IntroselectControl Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Sub-partition Reduction Limit
	 * 
	 * @return The Sub-partition Reduction Limit
	 */

	public int subPartitionReductionLimit()
	{
		return _subPartitionReductionLimit;
	}

	/**
	 * Retrieve the Sub-partition Cumulative Factor
	 * 
	 * @return The Sub-partition Cumulative Factor
	 */

	public double subPartitionCumulativeFactor()
	{
		return _subPartitionCumulativeFactor;
	}

	/**
	 * Retrieve the Iteration Count
	 * 
	 * @return The Iteration Count
	 */

	public int iterationCount()
	{
		return _iterationCount;
	}

	/**
	 * Retrieve the Cumulative Sub-partition Size
	 * 
	 * @return The Cumulative Sub-partition Size
	 */

	public double subPartitionCumulativeSize()
	{
		return _subPartitionCumulativeSize;
	}

	/**
	 * Initialize using the Array Size
	 * 
	 * @param size The Array Size
	 * 
	 * @return TRUE - Initialization successful
	 */

	public boolean init (
		final int size)
	{
		if (0 >= size)
		{
			return false;
		}

		_subPartitionCumulativeLimit = (_problemSize = size) * _subPartitionCumulativeFactor;
		_subPartitionCumulativeSize = size;
		_iterationCount = 0;
		return true;
	}

	/**
	 * Check if the Sufficient Progress has occurred
	 * 
	 * @param size The Current Size of the Partition
	 * 
	 * @return TRUE - Sufficient Progress has occurred
	 */

	public boolean progressCheck (
		final int size)
	{
		_subPartitionCumulativeSize = _subPartitionCumulativeSize + size;
		return _subPartitionCumulativeSize > _subPartitionCumulativeLimit || (
			++_iterationCount > _subPartitionReductionLimit &&
			size > _problemSize / 2
		) ? false : true;
	}
}
