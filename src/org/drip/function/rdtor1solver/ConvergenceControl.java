
package org.drip.function.rdtor1solver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * ConvergenceControl contains the R^d To R^1 Convergence Control/Tuning Parameters.
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
