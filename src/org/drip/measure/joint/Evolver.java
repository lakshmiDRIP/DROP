
package org.drip.measure.joint;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * Evolver exposes the Functionality that guides the Multi-Factor Random Process Variable Evolution.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class Evolver {
	private double[][] _aadblCorrelation = null;
	private org.drip.measure.dynamics.LocalEvaluator[] _aLDEVDrift = null;
	private org.drip.measure.dynamics.LocalEvaluator[] _aLDEVVolatility = null;

	protected Evolver (
		final org.drip.measure.dynamics.LocalEvaluator[] aLDEVDrift,
		final org.drip.measure.dynamics.LocalEvaluator[] aLDEVVolatility,
		final double[][] aadblCorrelation)
		throws java.lang.Exception
	{
		if (null == (_aLDEVDrift = aLDEVDrift) || null == (_aLDEVVolatility = aLDEVVolatility) || null ==
			(_aadblCorrelation = aadblCorrelation))
			throw new java.lang.Exception ("Evolver Constructor => Invalid Inputs");

		int iNumFactor = _aLDEVDrift.length;

		if (0 == iNumFactor || iNumFactor != _aLDEVVolatility.length || iNumFactor !=
			_aadblCorrelation.length)
			throw new java.lang.Exception ("Evolver Constructor => Invalid Inputs");

		for (int i = 0; i < iNumFactor; ++i) {
			if (null == _aLDEVDrift[i] || null == _aLDEVVolatility[i] || null == _aadblCorrelation[i] ||
				iNumFactor != _aadblCorrelation[i].length || !org.drip.quant.common.NumberUtil.IsValid
					(_aadblCorrelation[i]))
				throw new java.lang.Exception ("Evolver Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of the LDEV Drift Functions of the Individual Marginal Processes
	 * 
	 * @return The Array of the LDEV Drift Function of the Individual Marginal Processes
	 */

	public org.drip.measure.dynamics.LocalEvaluator[] driftLDEV()
	{
		return _aLDEVDrift;
	}

	/**
	 * Retrieve the Array of the LDEV Volatility Function of the Individual Marginal Processes
	 * 
	 * @return The Array of the LDEV Volatility Function of the Individual Marginal Processes
	 */

	public org.drip.measure.dynamics.LocalEvaluator[] volatilityLDEV()
	{
		return _aLDEVVolatility;
	}

	/**
	 * Retrieve the Correlation Matrix
	 * 
	 * @return The Correlation Matrix
	 */

	public double[][] correlation()
	{
		return _aadblCorrelation;
	}

	/**
	 * Generate the Adjacent Increment from the Array of the specified Random Variate
	 * 
	 * @param js The Joint Snap
	 * @param adblRandomVariate The Array of Random Variates
	 * @param adblRandomUnitRealization The Array of Random Stochastic Realization Variate Units
	 * @param dblTimeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Joint Level Realization
	 */

	public abstract org.drip.measure.joint.Edge increment (
		final org.drip.measure.joint.Vertex js,
		final double[] adblRandomVariate,
		final double[] adblRandomUnitRealization,
		final double dblTimeIncrement);

	/**
	 * Generate the Array of the Adjacent Increments from the Array of the specified Random Variate
	 * 
	 * @param aJS Array of Joint Snap Instances
	 * @param aadblRandomVariate Array of R^d Variates
	 * @param aadblRandomUnitRealization Array of R^d Stochastic Realization Units
	 * @param dblTimeIncrement The Time Increment Evolution Unit
	 * 
	 * @return Array of the Joint Level Realization
	 */

	public abstract org.drip.measure.joint.Edge[][] incrementSequence (
		final org.drip.measure.joint.Vertex[] aJS,
		final double[][] aadblRandomVariate,
		final double[][] aadblRandomUnitRealization,
		final double dblTimeIncrement);
}
