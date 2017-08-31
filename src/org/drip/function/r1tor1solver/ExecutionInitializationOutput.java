
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
 * ExecutionInitializationOutput holds the output of the root initializer calculation.
 * 
 * The following are the fields held by ExecutionInitializationOutput:
 * 	- Whether the initialization completed successfully
 * 	- the number of iterations, the number of objective function calculations, and the time taken for the
 * 		initialization
 * 	- the starting variate from the initialization
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class ExecutionInitializationOutput {
	private int _iNumOFCalcs = 0;
	private long _lStartTime = 0L;
	private boolean _bDone = false;
	private int _iNumIterations = 0;
	private int _iNumOFDerivCalcs = 0;
	private double _dblTime = java.lang.Double.NaN;
	private double _dblStartingVariate = java.lang.Double.NaN;

	protected ExecutionInitializationOutput()
	{
		_lStartTime = System.nanoTime();
	}

	protected ExecutionInitializationOutput (
		final ExecutionInitializationOutput eiopOther)
		throws java.lang.Exception
	{
		if (null == eiopOther)
			throw new java.lang.Exception ("ExecutionInitializationOutput constructor: Invalid inputs!");

		_iNumOFCalcs = eiopOther._iNumOFCalcs;
		_lStartTime = eiopOther._lStartTime;
		_bDone = eiopOther._bDone;
		_iNumIterations = eiopOther._iNumIterations;
		_iNumOFDerivCalcs = eiopOther._iNumOFDerivCalcs;
		_dblTime = eiopOther._dblTime;
		_dblStartingVariate = eiopOther._dblStartingVariate;
	}

	protected boolean done()
	{
		_dblTime = (System.nanoTime() - _lStartTime) * 0.000001;

		return _bDone = true;
	}

	/**
	 * Increment the Number of Iterations
	 * 
	 * @return TRUE - Successfully incremented
	 */

	public final boolean incrIterations()
	{
		++_iNumIterations;
		return true;
	}

	/**
	 * Return The number of Iterations consumed
	 * 
	 * @return Number of Iterations consumed
	 */

	public final int getNumIterations()
	{
		return _iNumIterations;
	}

	/**
	 * Increment the Number of Objective Function Evaluations
	 * 
	 * @return TRUE - Successfully incremented
	 */

	public final boolean incrOFCalcs()
	{
		++_iNumOFCalcs;
		return true;
	}

	/**
	 * Retrieve the number of objective function calculations needed
	 * 
	 * @return Number of objective function calculations needed
	 */

	public final int getNumOFCalcs()
	{
		return _iNumOFCalcs;
	}

	/**
	 * Increment the number of Objective Function Derivative evaluations
	 * 
	 * @return TRUE - Successfully incremented
	 */

	public final boolean incrOFDerivCalcs()
	{
		++_iNumOFDerivCalcs;
		return true;
	}

	/**
	 * Retrieve the number of objective function derivative calculations needed
	 * 
	 * @return Number of objective function derivative calculations needed
	 */

	public final int getNumOFDerivCalcs()
	{
		return _iNumOFDerivCalcs;
	}

	/**
	 * Indicate if the execution initialization is done
	 * 
	 * @return TRUE - Execution initialization is done
	 */

	public final boolean isDone()
	{
		return _bDone;
	}

	/**
	 * Return the time elapsed for the execution initialization operation
	 * 
	 * @return execution initialization time
	 */

	public final double time()
	{
		return _dblTime;
	}

	/**
	 * Set the Starting Variate
	 * 
	 * @param dblStartingVariate Starting Variate
	 * 
	 * @return TRUE - Starting Variate set successfully
	 */

	public boolean setStartingVariate (
		final double dblStartingVariate)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblStartingVariate)) return false;

		_dblStartingVariate = dblStartingVariate;
		return true;
	}

	/**
	 * Return the Starting Variate
	 * 
	 * @return Starting Variate
	 */

	public double getStartingVariate()
	{
		return _dblStartingVariate;
	}

	/**
	 * Return a string form of the Initializer output
	 * 
	 * @return String form of the Initializer output
	 */

	public java.lang.String displayString()
	{
		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		sb.append ("\t\tInitialization Done? " + isDone() + " [" + time() + " msec]");

		sb.append ("\n\t\tNum Iterations: " + getNumIterations());

		sb.append ("\n\t\tNum OF Calculations: " + getNumOFCalcs());

		sb.append ("\n\t\tNum OF Derivative Calculations: " + getNumOFDerivCalcs());

		sb.append ("\n\t\tStarting Variate: " + getStartingVariate());

		return sb.toString();
	}
}
