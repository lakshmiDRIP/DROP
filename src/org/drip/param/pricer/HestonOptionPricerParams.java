
package org.drip.param.pricer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * HestonOptionPricerParams holds the parameters that drive the dynamics of the Heston stochastic volatility
 * 	model.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class HestonOptionPricerParams {
	private int _iPayoffTransformScheme = -1;
	private double _dblRho = java.lang.Double.NaN;
	private double _dblKappa = java.lang.Double.NaN;
	private double _dblSigma = java.lang.Double.NaN;
	private double _dblTheta = java.lang.Double.NaN;
	private double _dblLambda = java.lang.Double.NaN;
	private int _iMultiValuePhaseTrackerType =
		org.drip.quant.fourier.PhaseAdjuster.MULTI_VALUE_BRANCH_POWER_PHASE_TRACKER_KAHL_JACKEL;

	/**
	 * HestonOptionPricerParams constructor
	 * 
	 * @param iPayoffTransformScheme The Payoff Transformation Scheme
	 * @param dblRho Rho
	 * @param dblKappa Kappa
	 * @param dblSigma Sigma
	 * @param dblTheta Theta
	 * @param dblLambda Lambda
	 * @param iMultiValuePhaseTrackerType The Multi Valued Phase Tracking Error Corrector
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public HestonOptionPricerParams (
		final int iPayoffTransformScheme,
		final double dblRho,
		final double dblKappa,
		final double dblSigma,
		final double dblTheta,
		final double dblLambda,
		final int iMultiValuePhaseTrackerType)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblRho = dblRho) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblKappa = dblKappa) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblSigma = dblSigma) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblTheta = dblTheta) ||
						!org.drip.quant.common.NumberUtil.IsValid (_dblLambda = dblLambda))
			throw new java.lang.Exception ("HestonOptionPricerParams ctr: Invalid Inputs!");

		_iPayoffTransformScheme = iPayoffTransformScheme;
		_iMultiValuePhaseTrackerType = iMultiValuePhaseTrackerType;
	}

	/**
	 * Retrieve Kappa
	 * 
	 * @return The Kappa
	 */

	public double kappa()
	{
		return _dblKappa;
	}

	/**
	 * Retrieve Lambda
	 * 
	 * @return The Lambda
	 */

	public double lambda()
	{
		return _dblLambda;
	}

	/**
	 * Retrieve Rho
	 * 
	 * @return The Rho
	 */

	public double rho()
	{
		return _dblRho;
	}

	/**
	 * Retrieve Sigma
	 * 
	 * @return The Sigma
	 */

	public double sigma()
	{
		return _dblSigma;
	}

	/**
	 * Retrieve Theta
	 * 
	 * @return The Theta
	 */

	public double theta()
	{
		return _dblTheta;
	}

	/**
	 * Return the Multi Valued Principal Branch Maintaining Phase Tracker Type
	 * 
	 * @return The Multi Valued Principal Branch Maintaining Phase Tracker Type
	 */

	public int phaseTrackerType()
	{
		return _iMultiValuePhaseTrackerType;
	}

	/**
	 * Return the Payoff Fourier Transformation Scheme
	 * 
	 * @return The Payoff Fourier Transformation Scheme
	 */

	public int payoffTransformScheme()
	{
		return _iPayoffTransformScheme;
	}
}
