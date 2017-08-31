
package org.drip.function.r1tor1solver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * 	IteratedBracket holds the left/right bracket variates and the corresponding values for the objective
 *  	function during each iteration.
 *
 * @author Lakshmi Krishnamurthy
 */

public class IteratedBracket {
	private double _dblOFLeft = java.lang.Double.NaN;
	private double _dblOFRight = java.lang.Double.NaN;
	private double _dblVariateLeft = java.lang.Double.NaN;
	private double _dblVariateRight = java.lang.Double.NaN;

	/**
	 * BracketingVariateIterator constructor
	 * 
	 * @param bop Bracketing Output
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public IteratedBracket (
		final org.drip.function.r1tor1solver.BracketingOutput bop)
		throws java.lang.Exception
	{
		if (null == bop) throw new java.lang.Exception ("IteratedBracket constructor: Invalid inputs");

		_dblOFLeft = bop.getOFLeft();

		_dblOFRight = bop.getOFRight();

		_dblVariateLeft = bop.getVariateLeft();

		_dblVariateRight = bop.getVariateRight();
	}

	/**
	 * Retrieve the left variate
	 * 
	 * @return Left Variate
	 */

	public double getVariateLeft()
	{
		return _dblVariateLeft;
	}

	/**
	 * Set the left variate
	 * 
	 * @param dblVariateLeft Left Variate
	 * 
	 * @return TRUE - Left Variate set successfully
	 */

	public boolean setVariateLeft (
		final double dblVariateLeft)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariateLeft)) return false;

		_dblVariateLeft = dblVariateLeft;
		return true;
	}

	/**
	 * Retrieve the right variate
	 * 
	 * @return Right Variate
	 */

	public double getVariateRight()
	{
		return _dblVariateRight;
	}

	/**
	 * Set the right variate
	 * 
	 * @param dblVariateRight Right Variate
	 * 
	 * @return TRUE - Right Variate set successfully
	 */

	public boolean setVariateRight (
		final double dblVariateRight)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariateRight)) return false;

		_dblVariateRight = dblVariateRight;
		return true;
	}

	/**
	 * Retrieve the left objective function value
	 * 
	 * @return Left Objective Function Value
	 */

	public double getOFLeft()
	{
		return _dblOFLeft;
	}

	/**
	 * Set the left objective function value
	 * 
	 * @param dblOFLeft Left Objective Function Value
	 * 
	 * @return TRUE - Left Objective Function set successfully
	 */

	public boolean setOFLeft (
		final double dblOFLeft)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblOFLeft)) return false;

		_dblOFLeft = dblOFLeft;
		return true;
	}

	/**
	 * Retrieve the right objective function value
	 * 
	 * @return Right objective function value
	 */

	public double getOFRight()
	{
		return _dblOFRight;
	}

	/**
	 * Set the right objective function value
	 * 
	 * @param dblOFRight Right Objective Function Value
	 * 
	 * @return TRUE - Right Objective Function set successfully
	 */

	public boolean setOFRight (
		final double dblOFRight)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblOFRight)) return false;

		_dblOFRight = dblOFRight;
		return true;
	}
}
