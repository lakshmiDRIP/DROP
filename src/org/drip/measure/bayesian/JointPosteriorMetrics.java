
package org.drip.measure.bayesian;

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
 * JointPosteriorMetrics holds the Inputs and the Results of a Bayesian Computation Execution.
 *
 * @author Lakshmi Krishnamurthy
 */

public class JointPosteriorMetrics {
	private org.drip.measure.continuous.R1Multivariate _r1mJoint = null;
	private org.drip.measure.continuous.R1Multivariate _r1mPrior = null;
	private org.drip.measure.continuous.R1Multivariate _r1mPosterior = null;
	private org.drip.measure.continuous.R1Multivariate _r1mConditional = null;
	private org.drip.measure.continuous.R1Multivariate _r1mUnconditional = null;

	/**
	 * JointPosteriorMetrics Constructor
	 * 
	 * @param r1mPrior The R^1 Multivariate Prior Distribution (Input)
	 * @param r1mUnconditional The R^1 Multivariate Unconditional Distribution (Input)
	 * @param r1mConditional The R^1 Multivariate Conditional Distribution (Input)
	 * @param r1mJoint The R^1 Multivariate Joint Distribution (Output)
	 * @param r1mPosterior The R^1 Multivariate Posterior Distribution (Output)
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public JointPosteriorMetrics (
		final org.drip.measure.continuous.R1Multivariate r1mPrior,
		final org.drip.measure.continuous.R1Multivariate r1mUnconditional,
		final org.drip.measure.continuous.R1Multivariate r1mConditional,
		final org.drip.measure.continuous.R1Multivariate r1mJoint,
		final org.drip.measure.continuous.R1Multivariate r1mPosterior)
		throws java.lang.Exception
	{
		if (null == (_r1mPrior = r1mPrior) || null == (_r1mUnconditional = r1mUnconditional) || null ==
			(_r1mConditional= r1mConditional) || null == (_r1mJoint= r1mJoint) || null == (_r1mPosterior =
				r1mPosterior))
			throw new java.lang.Exception ("JointPosteriorMetrics Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Prior Distribution
	 * 
	 * @return The Prior Distribution
	 */

	public org.drip.measure.continuous.R1Multivariate prior()
	{
		return _r1mPrior;
	}

	/**
	 * Retrieve the Unconditional Distribution
	 * 
	 * @return The Unconditional Distribution
	 */

	public org.drip.measure.continuous.R1Multivariate unconditional()
	{
		return _r1mUnconditional;
	}

	/**
	 * Retrieve the Conditional Distribution
	 * 
	 * @return The Conditional Distribution
	 */

	public org.drip.measure.continuous.R1Multivariate conditional()
	{
		return _r1mConditional;
	}

	/**
	 * Retrieve the Joint Distribution
	 * 
	 * @return The Joint Distribution
	 */

	public org.drip.measure.continuous.R1Multivariate joint()
	{
		return _r1mJoint;
	}

	/**
	 * Retrieve the Posterior Distribution
	 * 
	 * @return The Posterior Distribution
	 */

	public org.drip.measure.continuous.R1Multivariate posterior()
	{
		return _r1mPosterior;
	}
}
