
package org.drip.numerical.common;

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
 * <i>PrimeUtil</i> implements Generic Prime Number Utility Functions.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/common">Primitives/Array Manipulate Format Display Utilities</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PrimeUtil
{

	private static final int ComputeFromPrimeFactorMap (
		final java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeCountMap)
	{
		int primeFactor = 1;

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> primeCountEntry :
			primeCountMap.entrySet())
		{
			int prime = primeCountEntry.getKey();

			int primeCount = primeCountEntry.getValue();

			while (0 != primeCount)
			{
				primeFactor = primeFactor * prime;
				--primeCount;
			}
		}

		return primeFactor;
	}

	private static final org.drip.numerical.common.PrimeFactorCount PrimeFactorToIncrement (
		final java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeFactorMap,
		final java.util.TreeMap<java.lang.Integer, java.lang.Integer> baselinePrimeFactorMap)
	{
		int primeFactorToIncrement = 1;
		int primeFactorIncrementCount = -1;
		int smallestCompoundedPrimeFactor = java.lang.Integer.MAX_VALUE;

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> baselinePrimeFactorEntry :
			baselinePrimeFactorMap.entrySet())
		{
			int primeFactor = baselinePrimeFactorEntry.getKey();

			int baselinePrimeFactorCount = baselinePrimeFactorEntry.getValue();

			int primeFactorCount = primeFactorMap.containsKey (
				primeFactor
			) ? primeFactorMap.get (
				primeFactor
			) : 0;

			int primeFactorCountTarget = baselinePrimeFactorCount > primeFactorCount ?
				baselinePrimeFactorCount : primeFactorCount + 1;
			int primeFactorCountIndex = primeFactorCountTarget;
			int compoundedPrimeFactor = 1;

			while (primeFactorCountIndex-- > 0)
			{
				compoundedPrimeFactor = compoundedPrimeFactor * primeFactor;
			}

			if (smallestCompoundedPrimeFactor > compoundedPrimeFactor)
			{
				primeFactorToIncrement = primeFactor;
				smallestCompoundedPrimeFactor = compoundedPrimeFactor;
				primeFactorIncrementCount = primeFactorCountTarget - primeFactorCount;
			}
		}

		try
		{
			return new org.drip.numerical.common.PrimeFactorCount (
				primeFactorToIncrement,
				primeFactorIncrementCount
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Map of Prime Factor Count for the given Number
	 * 
	 * @param n The Given Number
	 * 
	 * @return Map of Prime Factor Count
	 */

	public static final java.util.TreeMap<java.lang.Integer, java.lang.Integer> PrimeFactorMap (
		int n)
	{
		if (0 >= n)
		{
			n = -1 * n;
		}

		int dePrimed = n;

		java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeFactorMap =
			new java.util.TreeMap<java.lang.Integer, java.lang.Integer>();

		int max = (int) java.lang.Math.sqrt (
			n
		);

		for (int index = 2;
			index <= max;
			++index
		)
		{
			int remainder = dePrimed % index;

			while (0 == remainder)
			{
				if (primeFactorMap.containsKey (
					index
				))
				{
					primeFactorMap.put (
						index,
						primeFactorMap.get (
							index
						) + 1
					);
				}
				else
				{
					primeFactorMap.put (
						index,
						1
					);
				}

				dePrimed = dePrimed / index;
				remainder = dePrimed % index;
			}
		}

		primeFactorMap.put (
			dePrimed,
			1
		);

		return primeFactorMap;
	}

	/**
	 * Indicate if the specified Number is a Prime Number
	 * 
	 * @param n n
	 * 
	 * @return The specified Number is Prime
	 */

	public static final boolean IsPrime (
		final int n)
	{
		return PrimeFactorMap (
			n
		).containsKey (
			n
		);
	}

	/**
	 * Compute the Prime Factor for a given Integer
	 * 
	 * @param n The Integer
	 * 
	 * @return Prime Factor for a given Integer
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double PrimeFactor (
		final int n)
		throws java.lang.Exception
	{
		java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeFactorMap = PrimeFactorMap (
			n
		);

		if (null == primeFactorMap || 0 == primeFactorMap.size())
		{
			throw new java.lang.Exception (
				"PrimeFactorTable::PrimeFactor => Cannot extract Map"
			);
		}

		return ComputeFromPrimeFactorMap (
			primeFactorMap
		);
	}

	/**
	 * Find the n-th ugly number. Ugly numbers are positive integers which are divisible by a or b or c.
	 * 
	 * @param n n
	 * @param a a
	 * @param b b
	 * @param c c
	 * 
	 * @return The n-th Ugly Number
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final int UglyNumber (
		final int n,
		final int a,
		final int b,
		final int c)
		throws java.lang.Exception
	{
		if (0 >= n ||
			0 >= a ||
			0 >= b ||
			0 >= c)
		{
			throw new java.lang.Exception (
				"NumberUtil::UglyNumber => Invalid Inputs"
			);
		}

		int ugly1 = -1;

		if (a < b && a < c)
		{
			ugly1 = a;
		}

		if (b < a && b < c)
		{
			ugly1 = b;
		}

		if (c < a && c < b)
		{
			ugly1 = c;
		}

		if (1 == n)
		{
			return ugly1;
		}

		java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeFactorMap = PrimeFactorMap (
			ugly1
		);

		java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeFactorMapA = ugly1 == a ? primeFactorMap
			: PrimeFactorMap (
				a
			);

		java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeFactorMapB = ugly1 == b ? primeFactorMap
			: PrimeFactorMap (
				b
			);

		java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeFactorMapC = ugly1 == c ? primeFactorMap
			: PrimeFactorMap (
				c
			);

		java.util.TreeMap<java.lang.Integer, java.lang.Integer> baselinePrimeFactorMap =
			new java.util.TreeMap<java.lang.Integer, java.lang.Integer>();

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> primeFactorMapAEntry :
			primeFactorMapA.entrySet())
		{
			baselinePrimeFactorMap.put (
				primeFactorMapAEntry.getKey(),
				primeFactorMapAEntry.getValue()
			);
		}

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> primeFactorMapBEntry :
			primeFactorMapB.entrySet())
		{
			int primeKey = primeFactorMapBEntry.getKey();

			int primeCount = primeFactorMapBEntry.getValue();

			if (baselinePrimeFactorMap.containsKey (
				primeKey
			))
			{
				if (baselinePrimeFactorMap.get (
						primeKey
					) < primeCount
				)
				{
					baselinePrimeFactorMap.put (
						primeKey,
						primeCount
					);
				}
			}
			else
			{
				baselinePrimeFactorMap.put (
					primeKey,
					primeCount
				);
			}
		}

		for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> primeFactorMapCEntry :
			primeFactorMapC.entrySet())
		{
			int primeKey = primeFactorMapCEntry.getKey();

			int primeCount = primeFactorMapCEntry.getValue();

			if (baselinePrimeFactorMap.containsKey (
				primeKey
			))
			{
				if (baselinePrimeFactorMap.get (
						primeKey
					) < primeCount
				)
				{
					baselinePrimeFactorMap.put (
						primeKey,
						primeCount
					);
				}
			}
			else
			{
				baselinePrimeFactorMap.put (
					primeKey,
					primeCount
				);
			}
		}

		for (int uglyIndex = 2;
			uglyIndex <= n;
			++uglyIndex)
		{
			org.drip.numerical.common.PrimeFactorCount primeFactorCount = PrimeFactorToIncrement (
				primeFactorMap,
				baselinePrimeFactorMap
			);

			if (null == primeFactorCount)
			{
				throw new java.lang.Exception (
					"NumberUtil::UglyNumber => Cannot find out Prime Factor to Increment"
				);
			}

			int primeFactor = primeFactorCount.primeFactor();

			if (primeFactorMap.containsKey (
				primeFactor
			))
			{
				primeFactorMap.put (
					primeFactor,
					primeFactorMap.get (
						primeFactor
					) + primeFactorCount.count()
				);
			}
			else
			{
				primeFactorMap.put (
					primeFactor,
					primeFactorCount.count()
				);
			}
		}

		return ComputeFromPrimeFactorMap (
			primeFactorMap
		);
	}
}
