
package org.drip.function.rdtor1solver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>ConstraintFunctionPointMetrics</i> holds the R<sup>d</sup> Point Base and Sensitivity Metrics of the
 * Constraint Function.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/rdtor1solver/README.md">R<sup>d</sup> To R<sup>1</sup> Solver</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ConstraintFunctionPointMetrics
{
	private double[] _constraintFunctionValueArray = null;
	private double[] _constraintFunctionMultiplierArray = null;
	private double[][] _constraintFunctionJacobianArray = null;

	/**
	 * ConstraintFunctionPointMetrics Constructor
	 * 
	 * @param constraintFunctionValueArray Constraint Function Value Array
	 * @param constraintFunctionJacobianArray Constraint Function Jacobian Array
	 * @param constraintFunctionMultiplierArray Constraint Function Karush-Kahn-Tucker Multiplier Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ConstraintFunctionPointMetrics (
		final double[] constraintFunctionValueArray,
		final double[][] constraintFunctionJacobianArray,
		final double[] constraintFunctionMultiplierArray)
		throws java.lang.Exception
	{
		if (null == (_constraintFunctionValueArray = constraintFunctionValueArray) ||
			null == (_constraintFunctionJacobianArray = constraintFunctionJacobianArray) ||
			null == (_constraintFunctionMultiplierArray = constraintFunctionMultiplierArray))
		{
			throw new java.lang.Exception ("ConstraintFunctionPointMetrics Constructor => Invalid Inputs");
		}

		int constraintCount = _constraintFunctionValueArray.length;
		int functionDimension = _constraintFunctionJacobianArray.length;

		if (0 == constraintCount || constraintCount != _constraintFunctionMultiplierArray.length ||
			0 == functionDimension)
		{
			throw new java.lang.Exception ("ConstraintFunctionPointMetrics Constructor => Invalid Inputs");
		}

		for (int constraintIndex = 0;
			constraintIndex < constraintCount;
			++constraintIndex)
		{
			if (!org.drip.numerical.common.NumberUtil.IsValid (
					_constraintFunctionValueArray[constraintIndex]
				) || !org.drip.numerical.common.NumberUtil.IsValid (
					_constraintFunctionMultiplierArray[constraintIndex]
				)
			)
			{
				throw new java.lang.Exception (
					"ConstraintFunctionPointMetrics Constructor => Invalid Inputs"
				);
			}
		}

		for (int functionDimensionIndex = 0;
			functionDimensionIndex < functionDimension;
			++functionDimensionIndex)
		{
			if (null == _constraintFunctionJacobianArray[functionDimensionIndex] ||
				constraintCount != _constraintFunctionJacobianArray[functionDimensionIndex].length ||
				!org.drip.numerical.common.NumberUtil.IsValid (
					_constraintFunctionJacobianArray[functionDimensionIndex]
				)
			)
			{
				throw new java.lang.Exception
					("ConstraintFunctionPointMetrics Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Constraint Count
	 * 
	 * @return The Constraint Count
	 */

	public int count()
	{
		return _constraintFunctionValueArray.length;
	}

	/**
	 * Retrieve the Constraint Dimension
	 * 
	 * @return The Constraint Dimension
	 */

	public int dimension()
	{
		return _constraintFunctionJacobianArray.length;
	}

	/**
	 * Retrieve the Constraint Function Value Array
	 * 
	 * @return The Constraint Function Value Array
	 */

	public double[] constraintFunctionValueArray()
	{
		return _constraintFunctionValueArray;
	}

	/**
	 * Retrieve the Constraint Function KKR Multiplier Array
	 * 
	 * @return The Constraint Function KKR Multiplier Array
	 */

	public double[] constraintFunctionMultiplierArray()
	{
		return _constraintFunctionMultiplierArray;
	}

	/**
	 * Retrieve the Constraint Function Jacobian Array
	 * 
	 * @return The Constraint Function Jacobian Array
	 */

	public double[][] constraintFunctionJacobianArray()
	{
		return _constraintFunctionJacobianArray;
	}
}
