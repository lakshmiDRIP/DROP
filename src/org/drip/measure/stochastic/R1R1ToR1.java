
package org.drip.measure.stochastic;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * R1R1ToR1 interface exposes the stubs for the evaluation of the objective function and its derivatives for
 *  a R^1 Deterministic + R^1 Random To R^1 Stochastic Function with one Random Component.
 *
 * @author Lakshmi Krishnamurthy
 */

public interface R1R1ToR1 {

	/**
	 * Evaluate a Single Realization for the given variate
	 * 
	 * @param dblVariate Variate
	 *  
	 * @return Return the Single Realization for the given variate
	 * 
	 * @throws java.lang.Exception Thrown if evaluation cannot be done
	 */

	public abstract double evaluateRealization (
		final double dblVariate)
		throws java.lang.Exception;

	/**
	 * Evaluate the Expectation for the given variate
	 * 
	 * @param dblVariate Variate
	 *  
	 * @return Return the Expectation for the given variate
	 * 
	 * @throws java.lang.Exception Thrown if evaluation cannot be done
	 */

	public abstract double evaluateExpectation (
		final double dblVariate)
		throws java.lang.Exception;

	/**
	 * Evaluate the Derivative for a Single Realization for the given variate
	 * 
	 * @param dblVariate Variate at which the derivative is to be calculated
	 * @param iOrder Order of the derivative to be computed
	 *  
	 * @return Return the Derivative for a Single Realization for the given variate
	 * 
	 * @throws java.lang.Exception Thrown if evaluation cannot be done
	 */

	public abstract double derivativeRealization (
		final double dblVariate,
		final int iOrder)
		throws java.lang.Exception;

	/**
	 * Evaluate the Derivative Expectation at the given variate
	 * 
	 * @param dblVariate Variate at which the derivative is to be calculated
	 * @param iOrder Order of the derivative to be computed
	 *  
	 * @return Return the Derivative Expectation at the given variate
	 * 
	 * @throws java.lang.Exception Thrown if evaluation cannot be done
	 */

	public abstract double derivativeExpectation (
		final double dblVariate,
		final int iOrder)
		throws java.lang.Exception;

	/**
	 * Evaluate a Path-wise Integral between the Vriates
	 * 
	 * @param dblStart Variate Start
	 * @param dblEnd Variate End
	 *  
	 * @return The Path-wise Integral between the Variates
	 * 
	 * @throws java.lang.Exception Thrown if evaluation cannot be done
	 */

	public abstract double integralRealization (
		final double dblStart,
		final double dblEnd)
		throws java.lang.Exception;

	/**
	 * Evaluate the Expected Path-wise Integral between the Vriates
	 * 
	 * @param dblStart Variate Start
	 * @param dblEnd Variate End
	 *  
	 * @return The Expected Path-wise Integral between the Variates
	 * 
	 * @throws java.lang.Exception Thrown if evaluation cannot be done
	 */

	public abstract double integralExpectation (
		final double dblStart,
		final double dblEnd)
		throws java.lang.Exception;
}
