
package org.drip.function.rdtor1solver;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>ConvergenceControl</i> contains the R<sup>d</sup> To R<sup>1</sup> Convergence Control/Tuning
 * Parameters.
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

public class ConvergenceControl
{

	/**
	 * Solve Using the Convergence of the Objective Function Realization
	 */

	public static final int OBJECTIVE_FUNCTION_SEQUENCE_CONVERGENCE = 1;

	/**
	 * Solve Using the Convergence of the Variate/Constraint Multiplier Tuple Realization
	 */

	public static final int VARIATE_CONSTRAINT_SEQUENCE_CONVERGENCE = 2;

	private int _finderStepCount = -1;
	private double _absoluteTolerance = java.lang.Double.NaN;
	private double _relativeTolerance = java.lang.Double.NaN;
	private int _convergenceType = VARIATE_CONSTRAINT_SEQUENCE_CONVERGENCE;

	/**
	 * Construct a Standard ConvergenceControl Instance
	 * 
	 * @return The Standard ConvergenceControl Instance
	 */

	public static ConvergenceControl Standard()
	{
		try
		{
			return new ConvergenceControl (
				VARIATE_CONSTRAINT_SEQUENCE_CONVERGENCE,
				5.0e-02,
				1.0e-06,
				70
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ConvergenceControl Constructor
	 * 
	 * @param convergenceType The Convergence Type
	 * @param relativeTolerance The Objective Function Relative Tolerance
	 * @param absoluteTolerance The Objective Function Absolute Tolerance
	 * @param finderStepCount The Number of the Fixed Point Finder Steps
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ConvergenceControl (
		final int convergenceType,
		final double relativeTolerance,
		final double absoluteTolerance,
		final int finderStepCount)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_relativeTolerance = relativeTolerance) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_absoluteTolerance = absoluteTolerance) ||
			1 > (_finderStepCount = finderStepCount))
		{
			throw new java.lang.Exception ("ConvergenceControl Constructor => Invalid Inputs");
		}

		_convergenceType = convergenceType;
	}

	/**
	 * Retrieve the Convergence Type
	 * 
	 * @return The Convergence Type
	 */

	public int convergenceType()
	{
		return _convergenceType;
	}

	/**
	 * Retrieve the Number of Finder Steps
	 * 
	 * @return The Number of Finder Steps
	 */

	public int finderStepCount()
	{
		return _finderStepCount;
	}

	/**
	 * Retrieve the Relative Tolerance
	 * 
	 * @return The Relative Tolerance
	 */

	public double relativeTolerance()
	{
		return _relativeTolerance;
	}

	/**
	 * Retrieve the Absolute Tolerance
	 * 
	 * @return The Absolute Tolerance
	 */

	public double absoluteTolerance()
	{
		return _absoluteTolerance;
	}
}
