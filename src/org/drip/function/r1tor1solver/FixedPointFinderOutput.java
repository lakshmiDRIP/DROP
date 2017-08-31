
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
 * FixedPointFinderOutput holds the result of the fixed point search.
 *
 * FixedPointFinderOutput contains the following fields:
 * 	- Whether the search completed successfully
 * 	- The number of iterations, the number of objective function base/derivative calculations, and the time
 * 		taken for the search
 * 	- The output from initialization
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixedPointFinderOutput {
	private int _iNumOFCalcs = 0;
	private long _lStartTime = 0L;
	private int _iNumIterations = 0;
	private int _iNumOFDerivCalcs = 0;
	private boolean _bHasRoot = false;
	private double _dblRoot = java.lang.Double.NaN;
	private double _dblRootFindingTime = java.lang.Double.NaN;
	private org.drip.function.r1tor1solver.ExecutionInitializationOutput _eiop = null;

	/**
	 * FixedPointFinderOutput constructor
	 * 
	 * @param eiop Execution Initialization Output 1D
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public FixedPointFinderOutput (
		final org.drip.function.r1tor1solver.ExecutionInitializationOutput eiop)
		throws java.lang.Exception
	{
		if (null == (_eiop = eiop))
			throw new java.lang.Exception ("FixedPointFinderOutput constructor: Invalid inputs!");

		_lStartTime = System.nanoTime();
	}

	/**
	 * Set the Root
	 * 
	 * @param dblRoot Root
	 * 
	 * @return TRUE - Successfully set
	 */

	public boolean setRoot (
		final double dblRoot)
	{
		_dblRootFindingTime = (System.nanoTime() - _lStartTime) * 0.000001;

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblRoot = dblRoot)) return false;

		return _bHasRoot = true;
	}

	/**
	 * Indicate whether the root is present in the output, i.e., if the finder has successfully completed.
	 * 
	 * @return TRUE - Root exists in the output
	 */

	public boolean containsRoot()
	{
		return _bHasRoot;
	}

	/**
	 * Return the time elapsed for the the full root finding operation
	 * 
	 * @return Time taken for root finding
	 */

	public double time()
	{
		return _dblRootFindingTime;
	}

	/**
	 * Return the root
	 * 
	 * @return Root
	 */

	public double getRoot()
	{
		return _dblRoot;
	}

	/**
	 * Increment the number of Iterations
	 * 
	 * @return TRUE - Successfully incremented
	 */

	public boolean incrIterations()
	{
		++_iNumIterations;
		return true;
	}

	/**
	 * Return The number of iterations taken
	 * 
	 * @return Number of iterations taken
	 */

	public int getNumIterations()
	{
		return _iNumIterations;
	}

	/**
	 * Increment the number of Objective Function evaluations
	 * 
	 * @return TRUE - Successfully incremented
	 */

	public boolean incrOFCalcs()
	{
		++_iNumOFCalcs;
		return true;
	}

	/**
	 * Retrieve the number of objective function calculations needed
	 * 
	 * @return Number of objective function calculations needed
	 */

	public int getNumOFCalcs()
	{
		return _iNumOFCalcs;
	}

	/**
	 * Increment the number of Objective Function Derivative evaluations
	 * 
	 * @return TRUE - Successfully incremented
	 */

	public boolean incrOFDerivCalcs()
	{
		++_iNumOFDerivCalcs;
		return true;
	}

	/**
	 * Retrieve the number of objective function derivative calculations needed
	 * 
	 * @return Number of objective function derivative calculations needed
	 */

	public int getNumOFDerivCalcs()
	{
		return _iNumOFDerivCalcs;
	}

	/**
	 * Retrieve the Execution Initialization Output
	 * 
	 * @return Execution Initialization Output
	 */

	public org.drip.function.r1tor1solver.ExecutionInitializationOutput getEIOP()
	{
		return _eiop;
	}

	/**
	 * Return a string form of the root finder output
	 * 
	 * @return String form of the root finder output
	 */

	public java.lang.String displayString()
	{
		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		sb.append (_eiop.displayString());

		sb.append ("\n\tRoot finding Done? " + _bHasRoot + " [" + _dblRootFindingTime + " msec]");

		sb.append ("\n\tRoot: " + _dblRoot);

		sb.append ("\n\tNum Iterations: " + _iNumIterations);

		sb.append ("\n\tNum OF Calculations: " + _iNumOFCalcs);

		sb.append ("\n\tNum OF Derivative Calculations: " + _iNumOFDerivCalcs);

		return sb.toString();
	}
}
