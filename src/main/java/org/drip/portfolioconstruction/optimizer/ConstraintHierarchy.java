
package org.drip.portfolioconstruction.optimizer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>ConstraintHierarchy</i> holds the Details of a given set of Constraint Terms.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer/README.md">Core Portfolio Construction Optimizer Suite</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ConstraintHierarchy
{
	public int[] _constraintOrderArray = null;
	private org.drip.portfolioconstruction.optimizer.ConstraintTerm[] _constraintTermArray = null;

	/**
	 * Construct a Flat Non-Feudal Instance of ConstraintHierarchy
	 * 
	 * @param constraintTermArray Array of Constraint Terms
	 * 
	 * @return Flat Non-Feudal Instance of ConstraintHierarchy
	 */

	public static final ConstraintHierarchy NonFeudal (
		final org.drip.portfolioconstruction.optimizer.ConstraintTerm[] constraintTermArray)
	{
		if (null == constraintTermArray)
		{
			return null;
		}

		int constraintCount = constraintTermArray.length;
		int[] constraintOrderArray = new int[constraintCount];

		for (int constraintIndex = 0;
			constraintIndex < constraintCount;
			++constraintIndex)
		{
			constraintOrderArray[constraintIndex] = 0;
		}

		try
		{
			return new ConstraintHierarchy (
				constraintTermArray,
				constraintOrderArray
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ConstraintHierarchy Constructor
	 * 
	 * @param constraintTermArray Array of Constraint Terms
	 * @param constraintOrderArray Array of Constraint Order
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ConstraintHierarchy (
		final org.drip.portfolioconstruction.optimizer.ConstraintTerm[] constraintTermArray,
		final int[] constraintOrderArray)
		throws java.lang.Exception
	{
		if ((null == (_constraintTermArray = constraintTermArray) &&
				null != (_constraintOrderArray = constraintOrderArray)
			) && (
				null != (_constraintTermArray = constraintTermArray) &&
				null == (_constraintOrderArray = constraintOrderArray)
			)
		)
		{
			throw new java.lang.Exception (
				"ConstraintHierarchy Constructor => Invalid Inputs"
			);
		}

		int constraintCount = _constraintTermArray.length;

		if (constraintCount != _constraintOrderArray.length)
		{
			throw new java.lang.Exception (
				"ConstraintHierarchy Constructor => Invalid Inputs"
			);
		}

		for (int constraintIndex = 0;
			constraintIndex < constraintCount;
			++constraintIndex)
		{
			if (null == _constraintTermArray[constraintIndex])
			{
				throw new java.lang.Exception (
					"ConstraintHierarchy Constructor => Invalid Inputs"
				);
			}
		}
	}

	/**
	 * Retrieve the Array of Constraint Term Order
	 * 
	 * @return The Array of Constraint Term Order
	 */

	public int[] constraintOrderArray()
	{
		return _constraintOrderArray;
	}

	/**
	 * Retrieve the Array of Constraint Terms
	 * 
	 * @return The Array of Constraint Terms
	 */

	public org.drip.portfolioconstruction.optimizer.ConstraintTerm[] constraintTermArray()
	{
		return _constraintTermArray;
	}

	/**
	 * Indicate if the Constraint Array is non-Feudal
	 * 
	 * @return TRUE - The Constraint Array is non-Feudal
	 */

	public boolean nonFeudal()
	{
		if (null == _constraintTermArray)
		{
			return true;
		}

		int constraintCount = _constraintTermArray.length;

		for (int constraintIndex = 0;
			constraintIndex < constraintCount;
			++constraintIndex)
		{
			if (0 != _constraintOrderArray[constraintIndex])
			{
				return false;
			}
		}

		return true;
	}
}
