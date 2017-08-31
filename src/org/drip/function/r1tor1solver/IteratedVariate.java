
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
 * 	IteratedVariate holds the variate and the corresponding value for the objective function during each
 * 		iteration.
 *
 * @author Lakshmi Krishnamurthy
 */

public class IteratedVariate {
	private double _dblOF = java.lang.Double.NaN;
	private double _dblVariate = java.lang.Double.NaN;

	/**
	 * IteratedVariate constructor
	 * 
	 * @param eiop Execution Initialization Output
	 * @param dblOF Objective Function Value
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public IteratedVariate (
		final org.drip.function.r1tor1solver.ExecutionInitializationOutput eiop,
		final double dblOF)
		throws java.lang.Exception
	{
		if (null == eiop || !org.drip.quant.common.NumberUtil.IsValid (_dblOF = dblOF))
			throw new java.lang.Exception ("IteratedVariate constructor: Invalid Inputs");

		_dblVariate = eiop.getStartingVariate();
	}

	/**
	 * Retrieve the variate
	 * 
	 * @return Variate
	 */

	public double getVariate()
	{
		return _dblVariate;
	}

	/**
	 * Set the variate
	 * 
	 * @param dblVariate Variate
	 * 
	 * @return TRUE - Variate set successfully
	 */

	public boolean setVariate (
		final double dblVariate)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate)) return false;

		_dblVariate = dblVariate;
		return true;
	}

	/**
	 * Retrieve the Objective Function Value
	 * 
	 * @return The Objective Function Value
	 */

	public double getOF()
	{
		return _dblOF;
	}

	/**
	 * Set the Objective Function Value
	 * 
	 * @param dblOF Objective Function Value
	 * 
	 * @return TRUE - Objective Function Value set successfully
	 */

	public boolean setOF (
		final double dblOF)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblOF)) return false;

		_dblOF = dblOF;
		return true;
	}
}
