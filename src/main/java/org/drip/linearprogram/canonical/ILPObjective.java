
package org.drip.linearprogram.canonical;

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
 * <i>ILPObjective</i> holds the Coefficients of the Objective Term of an Integer Linear Program
 * 	c<sup>T</sup>x where c is R<sup>n</sup> and x is Z<sub>+</sub><sup>n</sup>. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/linearprogram/README.md">Linear Programming Solution Suite</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/linearprogram/canonical/README.md">Linear Programming Framework Canonical Elements</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ILPObjective
{
	private double[] _c = null;

	/**
	 * ILPObjective Constructor
	 * 
	 * @param c The Objective Coefficient Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ILPObjective (
		final double[] c)
		throws java.lang.Exception
	{
		if (null == (_c = c))
		{
			throw new java.lang.Exception (
				"ILPObjective Constructor => Invalid Inputs"
			);
		}

		int dimension = _c.length;

		if (0 == dimension)
		{
			throw new java.lang.Exception (
				"ILPObjective Constructor => Invalid Inputs"
			);
		}

		for (int dimensionIndex = 0;
			dimensionIndex < dimension;
			++dimensionIndex)
		{
			if (!org.drip.numerical.common.NumberUtil.IsValid (
				_c[dimensionIndex]
			))
			{
				throw new java.lang.Exception (
					"ILPObjective Constructor => Invalid Inputs"
				);
			}
		}
	}

	/**
	 * Retrieve "c"
	 * 
	 * @return c
	 */

	public double[] c()
	{
		return _c;
	}

	/**
	 * Retrieve the Variate Dimension
	 * 
	 * @return Variate Dimension
	 */

	public int dimension()
	{
		return _c.length;
	}

	/**
	 * Validate the Variate Input
	 * 
	 * @param variateArray The Input Variate Array
	 * 
	 * @return TRUE - The Input Variate successfully Validated
	 */

	public boolean validate (
		final int[] variateArray)
	{
		if (null == variateArray)
		{
			return false;
		}

		int dimension = _c.length;

		if (dimension != variateArray.length)
		{
			return false;
		}

		for (int dimensionIndex = 0;
			dimensionIndex < dimension;
			++dimensionIndex)
		{
			if (0 >= variateArray[dimensionIndex])
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Evaluate the Objective Function at the specified Variate Array
	 * 
	 * @param variateArray The Input Variate Array
	 * 
	 * @return The Objective Function at the specified Variate Array
	 * 
	 * @throws java.lang.Exception Thrown if the Evaluation cannot be done
	 */

	public double evaluate (
		final int[] variateArray)
		throws java.lang.Exception
	{
		if (!validate (
			variateArray
		))
		{
			throw new java.lang.Exception (
				"ILPObjective::evaluate => Variate Array not Valid"
			);
		}

		double value = 0.;
		int dimension = _c.length;

		for (int dimensionIndex = 0;
			dimensionIndex < dimension;
			++dimensionIndex)
		{
			value += _c[dimensionIndex] * variateArray[dimensionIndex];
		}

		return value;
	}
}
