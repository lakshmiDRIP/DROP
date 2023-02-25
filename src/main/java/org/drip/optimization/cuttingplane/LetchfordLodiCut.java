
package org.drip.optimization.cuttingplane;

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
 * <i>LetchfordLodiCut</i> implements the Letchford-Lodi Cut for ILP. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Burdet, C. A., and E. L. Johnson (1977): A Sub-additive Approach to Solve Linear Integer Programs
 * 				<i>Annals of Discrete Mathematics</i> <b>1</b> 117-143
 *  	</li>
 *  	<li>
 * 			Chvatal, V. (1973): Edmonds Polytopes in a Hierarchy of Combinatorial Problems <i>Discrete
 * 				Mathematics</i> <b>4 (4)</b> 305-337
 *  	</li>
 *  	<li>
 * 			Gomory, R. E. (1958): Outline of an Algorithm for Integer Solutions to Linear Programs
 * 				<i>Bulletin of the American Mathematical Society</i> <b>64 (5)</b> 275-278
 *  	</li>
 *  	<li>
 * 			Kelley, J. E. (1960): The Cutting Plane Method for Solving Convex Problems <i>Journal for the
 * 				Society of the Industrial and Applied Mathematics</i> <b>8 (4)</b> 703-712
 *  	</li>
 *  	<li>
 * 			Letchford, A. N. and A. Lodi (2002): Strengthening Chvatal-Gomory Cuts and Gomory Fractional Cuts
 * 				<i>Operations Research Letters</i> <b>30 (2)</b> 74-82
 *  	</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md">Necessary, Sufficient, and Regularity Checks for Gradient Descent and LP/MILP/MINLP Schemes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/cuttingplane/README.md">Polyhedral Cutting Plane Generation Schemes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LetchfordLodiCut
	extends org.drip.optimization.cuttingplane.ChvatalGomoryCut
{

	private static final java.util.Set<java.lang.Integer> Partition (
		final double[] coefficientFractionArray)
	{
		java.util.Set<java.lang.Integer> partitionSet = new java.util.TreeSet<java.lang.Integer>();

		int coefficientCount = coefficientFractionArray.length;
		double a0Fraction = coefficientFractionArray[0];

		for (int coefficientIndex = 1;
			coefficientIndex < coefficientCount;
			++coefficientIndex)
		{
			if (a0Fraction >= coefficientFractionArray[coefficientIndex])
			{
				partitionSet.add (
					coefficientIndex
				);
			}
		}

		return partitionSet;
	}

	private static final java.util.Set<java.lang.Integer> Partition (
		final double[] coefficientFractionArray,
		final int k,
		final int p)
	{
		java.util.Set<java.lang.Integer> partitionSet = new java.util.TreeSet<java.lang.Integer>();

		int coefficientCount = coefficientFractionArray.length;
		double oneMinusA0FractionOverK = (1. - coefficientFractionArray[0]) / k;
		double upperBound = coefficientFractionArray[0] + oneMinusA0FractionOverK * p;
		double lowerBound = coefficientFractionArray[0] + oneMinusA0FractionOverK * (p - 1);

		for (int coefficientIndex = 1;
			coefficientIndex < coefficientCount;
			++coefficientIndex)
		{
			if (lowerBound < coefficientFractionArray[coefficientIndex] &&
				upperBound >= coefficientFractionArray[coefficientIndex])
			{
				partitionSet.add (
					coefficientIndex
				);
			}
		}

		return partitionSet;
	}

	/**
	 * ILPLetchfordLodiCut Constructor
	 * 
	 * @param aGrid "A" Constraint Grid
	 * @param bArray "b" Constraint Array
	 * @param lambdaArray The Lambda Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LetchfordLodiCut (
		final int[][] aGrid,
		final int[] bArray,
		final double[] lambdaArray)
		throws java.lang.Exception
	{
		super (
			aGrid,
			bArray,
			lambdaArray
		);
	}

	/**
	 * Generate the Partition Map
	 * 
	 * @return The Partition Map
	 */

	public org.drip.optimization.cuttingplane.LetchfordLodiPartitionMap partitionMap()
	{
		double[] unadjustedCoefficientArray = unadjustedCoefficientArray();

		if (null == unadjustedCoefficientArray)
		{
			return null;
		}

		int coefficientCount = unadjustedCoefficientArray.length;
		double[] coefficientFractionArray = new double[coefficientCount];

		if (0 == coefficientCount)
		{
			return null;
		}

		java.util.TreeMap<java.lang.Integer, java.util.Set<java.lang.Integer>> partitionMap =
			new java.util.TreeMap<java.lang.Integer, java.util.Set<java.lang.Integer>>();

		try
		{
			for (int coefficientIndex = 0;
				coefficientIndex < coefficientCount;
				++coefficientIndex)
			{
				coefficientFractionArray[coefficientIndex] =
					org.drip.numerical.common.NumberUtil.Fractional (
						unadjustedCoefficientArray[0]
					);
			}

			partitionMap.put (
				0,
				Partition (
					unadjustedCoefficientArray
				)
			);

			int k = org.drip.numerical.common.NumberUtil.ReciprocalIntegerFloor (
				coefficientFractionArray[0]
			);

			for (int p = 1;
				p <= k;
				++p)
			{
				partitionMap.put (
					p,
					Partition (
						unadjustedCoefficientArray,
						k,
						p
					)
				);
			}

			return new org.drip.optimization.cuttingplane.LetchfordLodiPartitionMap (
				k,
				unadjustedCoefficientArray,
				partitionMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override public double[] adjustedCoefficientArray()
	{
		org.drip.optimization.cuttingplane.LetchfordLodiPartitionMap letchfordLodiPartitionMap =
			partitionMap();

		if (null == letchfordLodiPartitionMap)
		{
			return null;
		}

		java.util.TreeMap<java.lang.Integer, java.util.Set<java.lang.Integer>> partitionMap =
			letchfordLodiPartitionMap.partitionMap();

		double[] unadjustedCoefficientArray = letchfordLodiPartitionMap.unadjustedCoefficientArray();

		int k = letchfordLodiPartitionMap.k();

		int coefficientCount = unadjustedCoefficientArray.length;
		double[] adjustedCoefficientArray = new double[coefficientCount];

		for (int coefficientIndex = 0;
			coefficientIndex < coefficientCount;
			++coefficientIndex)
		{
			adjustedCoefficientArray[coefficientIndex] = 0.;
		}

		try
		{
			for (int p = 0;
				p <= k;
				++p)
			{
				for (int listEntry : partitionMap.get (
					p
				))
				{
					adjustedCoefficientArray[listEntry] += p +
						(k + 1) * ((int) unadjustedCoefficientArray[listEntry]);
				}
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		adjustedCoefficientArray[0] = (k + 1) * ((int) unadjustedCoefficientArray[0]);
		return adjustedCoefficientArray;
	}
}
