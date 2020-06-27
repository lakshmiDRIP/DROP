
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
 * <i>HashSelector</i> implements the Hash-table Based Selection Algorithm. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Eppstein, D. (2007): Blum-style Analysis of Quickselect
 *  			https://11011110.github.io/blog/2007/10/09/blum-style-analysis-of.html
 *  	</li>
 *  	<li>
 *  		Hoare, C. A. R. (1961): Algorithm 65: Find <i>Communications of the ACM</i> <b>4 (1)</b> 321-322
 *  	</li>
 *  	<li>
 *  		Knuth, D. (1997): <i>The Art of Computer Programming 3<sup>rd</sup> Edition</i>
 *  			<b>Addison-Wesley</b>
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Quickselect https://en.wikipedia.org/wiki/Quickselect
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Selection Algorithm https://en.wikipedia.org/wiki/Selection_algorithm
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

public class HashSelector
	extends org.drip.graph.selection.OrderStatisticSelector<java.lang.Double>
{
	private int _bucketCount = -1;

	private java.util.List<java.util.List<java.lang.Double>> _elementBucketList =
		new java.util.ArrayList<java.util.List<java.lang.Double>>();

	/**
	 * HashSelector Constructor
	 * 
	 * @param elementArray Array of Elements
	 * @param bucketCount Count of the Number of Buckets
	 * 
	 * @throws java.lang.Exception Thrown if the Input is Invalid
	 */

	public HashSelector (
		final java.lang.Double[] elementArray,
		final int bucketCount)
		throws java.lang.Exception
	{
		super (
			elementArray
		);

		if (0 >= (_bucketCount = bucketCount))
		{
			throw new java.lang.Exception (
				"HashSelector Constructor => Invalid Bucket Count"
			);
		}

		double maxValue = elementArray[0];
		double minValue = elementArray[0];

		for (double value : elementArray)
		{
			if (value < minValue)
			{
				minValue = value;
			}
			else if (value > maxValue)
			{
				maxValue = value;
			}
		}

		for (int bucketIndex = 0;
			bucketIndex < _bucketCount;
			++bucketIndex)
		{
			_elementBucketList.add (
				new java.util.ArrayList<java.lang.Double>()
			);
		}

		double bucketWidth = (maxValue - minValue) / _bucketCount;

		for (double value : elementArray)
		{
			_elementBucketList.get (
				(int) ((value - minValue) / bucketWidth)
			).add (
				value
			);
		}
	}

	/**
	 * Retrieve the Count of Buckets
	 * 
	 * @return The Bucket Count
	 */

	public int bucketCount()
	{
		return _bucketCount;
	}

	@Override public java.lang.Double select (
		final int k)
	{
		int bucketIndex = 0;
		int orderStatistics = 0;

		int bucketSize = _elementBucketList.get (
			bucketIndex
		).size();

		while (bucketIndex < _bucketCount - 1 &&
			orderStatistics + bucketSize < k)
		{
			orderStatistics = orderStatistics + bucketSize;

			bucketSize = _elementBucketList.get (
				++bucketIndex
			).size();
		}

		java.util.List<java.lang.Double> valueList = _elementBucketList.get (
			bucketIndex
		);

		java.lang.Double[] valueArray = new java.lang.Double[valueList.size()];

		valueList.toArray (
			valueArray
		);

		try
		{
			return new org.drip.graph.selection.PartialSortSelector<java.lang.Double> (
				valueArray
			).select (
				k
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
