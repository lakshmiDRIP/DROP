
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
 * <i>KaplanZwickTargetSize</i> implements the Target Size Metrics described in Kaplan and Zwick (2009). The
 * 	References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Chazelle, B. (2000): The Discrepancy Method: Randomness and Complexity
 *  			https://www.cs.princeton.edu/~chazelle/pubs/book.pdf
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
 *  		Kaplan, H., and U. Zwick (2009): A simpler implementation and analysis of Chazelle's Soft Heaps
 *  			https://epubs.siam.org/doi/abs/10.1137/1.9781611973068.53?mobileUi=0
 *  	</li>
 *  	<li>
 *  		Pettie, S., and V. Ramachandran (2008): Randomized Minimum Spanning Tree Algorithms using
 *  			Exponentially Fewer Random Bits <i>ACM Transactions on Algorithms</i> <b>4 (1)</b> 1-27
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

public class KaplanZwickTargetSize
{

	/**
	 * Retrieve the Rank Scaler used in Kaplan and Zwick (2009)
	 */

	public static final double STANDARD_RANK_SCALER = 1.5;

	private int _estimate = -1;
	private double _lowerBound = java.lang.Double.NaN;
	private double _rankScaler = java.lang.Double.NaN;
	private double _upperBound = java.lang.Double.NaN;

	private static final int TargetSize (
		final int k,
		final int r,
		final double rankScaler)
	{
		if (k <= r)
		{
			return 1;
		}

		return (int) java.lang.Math.ceil (
			rankScaler * TargetSize (
				k - 1,
				r,
				rankScaler
			)
		);
	}

	/**
	 * Compute the Standard Kaplan-Zwick Target Size Metrics
	 * 
	 * @param k The Heap Rank
	 * @param r The R Parameter
	 * @param rankScaler The Rank Scaler
	 * 
	 * @return Kaplan-Zwick Target Size Metrics
	 */

	public static final KaplanZwickTargetSize Standard (
		final int k,
		final int r,
		final double rankScaler)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				rankScaler
			) || rankScaler <= 1. || rankScaler >= 2.
		)
		{
			return null;
		}

		double lowerBound = k < r ? 1. : java.lang.Math.pow (
			rankScaler,
			k - r
		);

		try
		{
			return new KaplanZwickTargetSize (
				rankScaler,
				TargetSize (
					k,
					r,
					rankScaler
				),
				lowerBound,
				k < r ? 1. : 2. * lowerBound - 1.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Standard Kaplan-Zwick Target Size Metrics Using the Standard Rank Scaler
	 * 
	 * @param k The Heap Rank
	 * @param r The R Parameter
	 * 
	 * @return Kaplan-Zwick Target Size Metrics
	 */

	public static final KaplanZwickTargetSize Standard (
		final int k,
		final int r)
	{
		return Standard (
			k,
			r,
			STANDARD_RANK_SCALER
		);
	}

	/**
	 * KaplanZwickTargetSize Constructor
	 * 
	 * @param rankScaler Target Size Rank Scaler
	 * @param estimate Target Size Estimate
	 * @param lowerBound Target Size Lower Bound
	 * @param upperBound Target Size Upper Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public KaplanZwickTargetSize (
		final double rankScaler,
		final int estimate,
		final double lowerBound,
		final double upperBound)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				_rankScaler = rankScaler
			) || _rankScaler <= 1. || _rankScaler >= 2. ||
				0 >= (_estimate = estimate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				_lowerBound = lowerBound
			) || !org.drip.numerical.common.NumberUtil.IsValid (
				_upperBound = upperBound
			)
		)
		{
			throw new java.lang.Exception (
				"KaplanZwickTargetSize Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Target Size Rank Scaler
	 * 
	 * @return The Target Size Rank Scaler
	 */

	public double rankScaler()
	{
		return _rankScaler;
	}

	/**
	 * Retrieve the Target Size Estimate
	 * 
	 * @return The Target Size Estimate
	 */

	public int estimate()
	{
		return _estimate;
	}

	/**
	 * Retrieve the Target Size Lower Bound
	 * 
	 * @return The Target Size Lower Bound
	 */

	public double lowerBound()
	{
		return _lowerBound;
	}

	/**
	 * Retrieve the Target Size Upper Bound
	 * 
	 * @return The Target Size Upper Bound
	 */

	public double upperBound()
	{
		return _upperBound;
	}
}
