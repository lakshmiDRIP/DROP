
package org.drip.function.rdtor1solver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>ConvergenceControl</i> contains the R<sup>d</sup> To R<sup>1</sup> Convergence Control/Tuning
 * Parameters.
 * <br><br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function">Function</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/rdtor1solver">R<sup>d</sup> To R<sup>1</sup> Solver</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ConvergenceControl {

	/**
	 * Solve Using the Convergence of the Objective Function Realization
	 */

	public static final int OBJECTIVE_FUNCTION_SEQUENCE_CONVERGENCE = 1;

	/**
	 * Solve Using the Convergence of the Variate/Constraint Multiplier Tuple Realization
	 */

	public static final int VARIATE_CONSTRAINT_SEQUENCE_CONVERGENCE = 2;

	private int _iNumFinderSteps = -1;
	private double _dblAbsoluteTolerance = java.lang.Double.NaN;
	private double _dblRelativeTolerance = java.lang.Double.NaN;
	private int _iConvergenceType = VARIATE_CONSTRAINT_SEQUENCE_CONVERGENCE;

	/**
	 * Construct a Standard ConvergenceControl Instance
	 * 
	 * @return The Standard ConvergenceControl Instance
	 */

	public static ConvergenceControl Standard()
	{
		try {
			return new ConvergenceControl (VARIATE_CONSTRAINT_SEQUENCE_CONVERGENCE, 5.0e-02, 1.0e-06, 70);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ConvergenceControl Constructor
	 * 
	 * @param iConvergenceType The Convergence Type
	 * @param dblRelativeTolerance The Objective Function Relative Tolerance
	 * @param dblAbsoluteTolerance The Objective Function Absolute Tolerance
	 * @param iNumFinderSteps The Number of the Fixed Point Finder Steps
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ConvergenceControl (
		final int iConvergenceType,
		final double dblRelativeTolerance,
		final double dblAbsoluteTolerance,
		final int iNumFinderSteps)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblRelativeTolerance = dblRelativeTolerance) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblAbsoluteTolerance = dblAbsoluteTolerance) || 1 >
				(_iNumFinderSteps = iNumFinderSteps))
			throw new java.lang.Exception ("ConvergenceControl Constructor => Invalid Inputs");

		_iConvergenceType = iConvergenceType;
	}

	/**
	 * Retrieve the Convergence Type
	 * 
	 * @return The Convergence Type
	 */

	public int convergenceType()
	{
		return _iConvergenceType;
	}

	/**
	 * Retrieve the Number of Finder Steps
	 * 
	 * @return The Number of Finder Steps
	 */

	public int numFinderSteps()
	{
		return _iNumFinderSteps;
	}

	/**
	 * Retrieve the Relative Tolerance
	 * 
	 * @return The Relative Tolerance
	 */

	public double relativeTolerance()
	{
		return _dblRelativeTolerance;
	}

	/**
	 * Retrieve the Absolute Tolerance
	 * 
	 * @return The Absolute Tolerance
	 */

	public double absoluteTolerance()
	{
		return _dblAbsoluteTolerance;
	}
}
