
package org.drip.regression.core;

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
 * UnitRegressionExecutor implements the UnitRegressor, and splits the regression execution into pre-,
 * 	execute, and post-regression. It provides default implementations for pre-regression and post-regression.
 * 	Most typical regressors only need to over-ride the execRegression method.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class UnitRegressionExecutor implements org.drip.regression.core.UnitRegressor {
	private static final boolean _bDisplayStatus = false;

	private java.lang.String _strRegressorSet = "";
	private java.lang.String _strRegressorName = "";

	/**
	 * Constructor for the unit regression executor
	 * 
	 * @param strRegressorName Name of the unit regressor
	 * @param strRegressorSet Name of the regressor set
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	protected UnitRegressionExecutor (
		final java.lang.String strRegressorName,
		final java.lang.String strRegressorSet)
		throws java.lang.Exception
	{
		if (null == (_strRegressorName = strRegressorName) || strRegressorName.isEmpty() || null ==
			(_strRegressorSet = strRegressorSet) || _strRegressorSet.isEmpty())
			throw new java.lang.Exception ("UnitRegressionExecutor ctr: Invalid inputs");
	}

	/**
	 * One-time initialization to set up the objects needed for the regression
	 * 
	 * @return TRUE - Initialization successful
	 */

	public boolean preRegression()
	{
		return true;
	}

	/**
	 * Execute the regression call within this function
	 * 
	 * @return The result of the regression
	 */

	public abstract boolean execRegression();

	/**
	 * Clean-up of the objects set-up for the regression
	 * 
	 * @param rnvd Regression Run Detail object to capture the regression details
	 * 
	 * @return TRUE - Clean-up successful
	 */

	public boolean postRegression (
		final org.drip.regression.core.RegressionRunDetail rnvd)
	{
		return true;
	}

	@Override public org.drip.regression.core.RegressionRunOutput regress()
	{
		org.drip.regression.core.RegressionRunOutput ro = null;

		try {
			ro = new org.drip.regression.core.RegressionRunOutput (_strRegressorSet + "." +
				_strRegressorName);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		if (!preRegression()) {
			if (_bDisplayStatus)
				System.out.println (_strRegressorSet + "." + _strRegressorName +
					": Cannot set-up the regressor!");

			return null;
		}

		long lStartTime = System.nanoTime();

		if (!execRegression()) {
			if (_bDisplayStatus)
				System.out.println (_strRegressorSet + "." + _strRegressorName + ": failed");

			ro.setTerminationStatus (false);

			return ro;
		}

		ro._lExecTime = (long) (1.e-03 * (System.nanoTime() - lStartTime));

		if (!postRegression (ro.getRegressionDetail())) {
			if (_bDisplayStatus)
				System.out.println (_strRegressorSet + "." + _strRegressorName +
					": Regressor clean-up unsuccessful!");

			return null;
		}

		if (_bDisplayStatus) System.out.println (_strRegressorSet + "." + _strRegressorName + ": succeeded");

		ro.setTerminationStatus (true);

		return ro;
	}

	@Override public java.lang.String getName()
	{
		return _strRegressorName;
	}
}
