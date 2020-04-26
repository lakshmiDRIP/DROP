
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
 * <i>PrimeFactorTable</i> implements the Cumulative Table of Prime Factors of a Number.
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

public class PrimeFactorTable
{

	/**
	 * Retrieve the Map of Prime Number Count for the given Number
	 * 
	 * @param n The Given Number
	 * @return Map of Prime Number Count
	 */

	public static final java.util.TreeMap<java.lang.Integer, java.lang.Integer> PrimeCountMap (
		int n)
	{
		if (0 >= n)
		{
			n = -1 * n;
		}

		int dePrimed = n;

		java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeCountMap =
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
				if (primeCountMap.containsKey (
					index
				))
				{
					primeCountMap.put (
						index,
						primeCountMap.get (
							index
						) + 1
					);
				}
				else
				{
					primeCountMap.put (
						index,
						1
					);
				}

				dePrimed = dePrimed / index;
				remainder = dePrimed % index;
			}
		}

		primeCountMap.put (
			dePrimed,
			1
		);

		return primeCountMap;
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

	public double PrimeFactor (
		final int n)
		throws java.lang.Exception
	{
		java.util.TreeMap<java.lang.Integer, java.lang.Integer> primeCountMap = PrimeCountMap (
			n
		);

		if (null == primeCountMap || 0 == primeCountMap.size())
		{
			throw new java.lang.Exception (
				"PrimeFactorTable::PrimeFactor => Cannot extract Map"
			);
		}

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
}
