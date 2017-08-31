
package org.drip.dynamics.sabr;

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
 * StochasticVolatilityStateEvolver provides the SABR Stochastic Volatility Evolution Dynamics.
 *
 * @author Lakshmi Krishnamurthy
 */

public class StochasticVolatilityStateEvolver implements org.drip.dynamics.evolution.PointStateEvolver {
	private double _dblRho = java.lang.Double.NaN;
	private double _dblBeta = java.lang.Double.NaN;
	private double _dblIdiosyncraticRho = java.lang.Double.NaN;
	private double _dblVolatilityOfVolatility = java.lang.Double.NaN;
	private org.drip.state.identifier.ForwardLabel _lslForward = null;
	private org.drip.sequence.random.UnivariateSequenceGenerator _usgForwardRate = null;
	private org.drip.sequence.random.UnivariateSequenceGenerator _usgForwardRateVolatilityIdiosyncratic =
		null;

	/**
	 * Create a Gaussian SABR Instance
	 * 
	 * @param lslForward The Forward Rate Latent State Label
	 * @param dblRho SABR Rho
	 * @param dblVolatilityOfVolatility SABR Volatility Of Volatility
	 * @param usgForwardRate The Forward Rate Univariate Sequence Generator
	 * @param usgForwardRateVolatilityIdiosyncratic The Idiosyncratic Component Forward Rate Volatility
	 *  Univariate Sequence Generator
	 * 
	 * @return The Gaussian SABR Instance
	 */

	public static final StochasticVolatilityStateEvolver Gaussian (
		final org.drip.state.identifier.ForwardLabel lslForward,
		final double dblRho,
		final double dblVolatilityOfVolatility,
		final org.drip.sequence.random.UnivariateSequenceGenerator usgForwardRate,
		final org.drip.sequence.random.UnivariateSequenceGenerator usgForwardRateVolatilityIdiosyncratic)
	{
		try {
			return new StochasticVolatilityStateEvolver (lslForward, 0., dblRho, dblVolatilityOfVolatility,
				usgForwardRate, usgForwardRateVolatilityIdiosyncratic);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a Log-normal SABR Instance
	 * 
	 * @param lslForward The Forward Rate Latent State Label
	 * @param dblRho SABR Rho
	 * @param dblVolatilityOfVolatility SABR Volatility Of Volatility
	 * @param usgForwardRate The Forward Rate Univariate Sequence Generator
	 * @param usgForwardRateVolatilityIdiosyncratic The Idiosyncratic Component Forward Rate Volatility
	 *  Univariate Sequence Generator
	 * 
	 * @return The Log-normal SABR Instance
	 */

	public static final StochasticVolatilityStateEvolver Lognormal (
		final org.drip.state.identifier.ForwardLabel lslForward,
		final double dblRho,
		final double dblVolatilityOfVolatility,
		final org.drip.sequence.random.UnivariateSequenceGenerator usgForwardRate,
		final org.drip.sequence.random.UnivariateSequenceGenerator usgForwardRateVolatilityIdiosyncratic)
	{
		try {
			return new StochasticVolatilityStateEvolver (lslForward, 1., dblRho, dblVolatilityOfVolatility,
				usgForwardRate, usgForwardRateVolatilityIdiosyncratic);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a Constant Elasticity of Variance SABR Instance
	 * 
	 * @param lslForward The Forward Rate Latent State Label
	 * @param dblBeta SABR Beta
	 * @param dblRho SABR Rho
	 * @param usgForwardRate The Forward Rate Univariate Sequence Generator
	 * @param usgForwardRateVolatilityIdiosyncratic The Idiosyncratic Component Forward Rate Volatility
	 *  Univariate Sequence Generator
	 * 
	 * @return The Constant Elasticity of Variance SABR Instance
	 */

	public static final StochasticVolatilityStateEvolver CEV (
		final org.drip.state.identifier.ForwardLabel lslForward,
		final double dblBeta,
		final double dblRho,
		final org.drip.sequence.random.UnivariateSequenceGenerator usgForwardRate,
		final org.drip.sequence.random.UnivariateSequenceGenerator usgForwardRateVolatilityIdiosyncratic)
	{
		try {
			return new StochasticVolatilityStateEvolver (lslForward, dblBeta, dblRho, 0., usgForwardRate,
				usgForwardRateVolatilityIdiosyncratic);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * StochasticVolatilityStateEvolver Constructor
	 * 
	 * @param lslForward The Forward Rate Latent State Label
	 * @param dblBeta SABR Beta
	 * @param dblRho SABR Rho
	 * @param dblVolatilityOfVolatility SABR Volatility Of Volatility
	 * @param usgForwardRate The Forward Rate Univariate Sequence Generator
	 * @param usgForwardRateVolatilityIdiosyncratic The Idiosyncratic Component Forward Rate Volatility
	 *  Univariate Sequence Generator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public StochasticVolatilityStateEvolver (
		final org.drip.state.identifier.ForwardLabel lslForward,
		final double dblBeta,
		final double dblRho,
		final double dblVolatilityOfVolatility,
		final org.drip.sequence.random.UnivariateSequenceGenerator usgForwardRate,
		final org.drip.sequence.random.UnivariateSequenceGenerator usgForwardRateVolatilityIdiosyncratic)
		throws java.lang.Exception
	{
		if (null == (_lslForward = lslForward) || !org.drip.quant.common.NumberUtil.IsValid (_dblBeta =
			dblBeta) || !org.drip.quant.common.NumberUtil.IsValid (_dblRho = dblRho) || _dblRho < -1. ||
				_dblRho > 1. || !org.drip.quant.common.NumberUtil.IsValid (_dblVolatilityOfVolatility =
					dblVolatilityOfVolatility) || null == (_usgForwardRate = usgForwardRate) || (0. !=
						_dblVolatilityOfVolatility && null == (_usgForwardRateVolatilityIdiosyncratic =
							usgForwardRateVolatilityIdiosyncratic)))
			throw new java.lang.Exception ("StochasticVolatilityStateEvolver ctr => Invalid Inputs");

		_dblIdiosyncraticRho = java.lang.Math.sqrt (1. - _dblRho * _dblRho);
	}

	/**
	 * Retrieve the Forward Label
	 * 
	 * @return The Forward Label
	 */

	public org.drip.state.identifier.ForwardLabel forwardLabel()
	{
		return _lslForward;
	}

	/**
	 * Retrieve SABR Volatility of Volatility
	 * 
	 * @return SABR Volatility of Volatility
	 */

	public double volatilityOfVolatility()
	{
		return _dblVolatilityOfVolatility;
	}

	/**
	 * Retrieve SABR Beta
	 * 
	 * @return SABR Beta
	 */

	public double beta()
	{
		return _dblBeta;
	}

	/**
	 * Retrieve SABR Rho
	 * 
	 * @return SABR Rho
	 */

	public double rho()
	{
		return _dblRho;
	}

	/**
	 * The Forward Rate Univariate Random Variable Generator Sequence
	 * 
	 * @return The Forward Rate Univariate Random Variable Generator Sequence
	 */

	public org.drip.sequence.random.UnivariateSequenceGenerator usgForwardRate()
	{
		return _usgForwardRate;
	}

	/**
	 * The Idiosyncratic Component of Forward Rate Volatility Univariate Random Variable Generator Sequence
	 * 
	 * @return The Idiosyncratic Component of Forward Rate Volatility Univariate Random Variable Generator
	 *  Sequence
	 */

	public org.drip.sequence.random.UnivariateSequenceGenerator usgForwardRateVolatilityIdiosyncratic()
	{
		return _usgForwardRateVolatilityIdiosyncratic;
	}

	@Override public org.drip.dynamics.evolution.LSQMPointUpdate evolve (
		final int iSpotDate,
		final int iViewDate,
		final int iSpotTimeIncrement,
		final org.drip.dynamics.evolution.LSQMPointUpdate lsqmPrev)
	{
		if (iViewDate < iSpotDate || null == lsqmPrev || !(lsqmPrev instanceof
			org.drip.dynamics.sabr.ForwardRateUpdate))
			return null;
		double dblForwardRateZ = _usgForwardRate.random();


		double dblAnnualizedIncrement = 1. * iSpotTimeIncrement / 365.25;
		org.drip.dynamics.sabr.ForwardRateUpdate fruPrev = (org.drip.dynamics.sabr.ForwardRateUpdate)
			lsqmPrev;

		double dblAnnualizedIncrementSQRT = java.lang.Math.sqrt (dblAnnualizedIncrement);

		try {
			double dblForwardRate = fruPrev.forwardRate();

			double dblForwardRateVolatility = fruPrev.forwardRateVolatility();

			double dblForwardRateIncrement = dblForwardRateVolatility * java.lang.Math.pow (dblForwardRate,
				_dblBeta) * dblAnnualizedIncrementSQRT * dblForwardRateZ;

			double dblForwardRateVolatilityIncrement = _dblVolatilityOfVolatility * dblForwardRateVolatility
				* dblAnnualizedIncrementSQRT * (_dblRho * dblForwardRateZ + _dblIdiosyncraticRho *
					_usgForwardRateVolatilityIdiosyncratic.random());

			return org.drip.dynamics.sabr.ForwardRateUpdate.Create (_lslForward, iSpotDate, iSpotDate +
				iSpotTimeIncrement, iViewDate, dblForwardRate + dblForwardRateIncrement,
					dblForwardRateIncrement, dblForwardRateVolatility + dblForwardRateVolatilityIncrement,
						dblForwardRateVolatilityIncrement);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Implied ATM Black Volatility for the ATM Forward Rate and the TTE
	 * 
	 * @param dblATMForwardRate ATM Forward Rate
	 * @param dblTTE Time to Expiry
	 * @param dblSigma0 Initial Sigma
	 * 
	 * @return The Implied Black Volatility Instance
	 */

	public org.drip.dynamics.sabr.ImpliedBlackVolatility computeATMBlackVolatility (
		final double dblATMForwardRate,
		final double dblTTE,
		final double dblSigma0)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblATMForwardRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblTTE) || !org.drip.quant.common.NumberUtil.IsValid
				(dblSigma0))
			return null;

		double dblF0KExpSQRT = java.lang.Math.pow (dblATMForwardRate, 1. - _dblBeta);

		double dblA = dblSigma0 / dblF0KExpSQRT;
		double dblB = 1. + dblTTE * (((1. - _dblBeta) * (1. - _dblBeta) * dblSigma0 * dblSigma0 / (24. *
			dblF0KExpSQRT * dblF0KExpSQRT)) + (_dblRho * _dblBeta * _dblVolatilityOfVolatility * dblSigma0 /
				(4. * dblF0KExpSQRT)) + ((2. - 3. * _dblRho * _dblRho) * _dblVolatilityOfVolatility *
					_dblVolatilityOfVolatility / 24.));

		try {
			return new org.drip.dynamics.sabr.ImpliedBlackVolatility (dblATMForwardRate, dblATMForwardRate,
				dblTTE, dblA, 0., 0., dblB, dblA * dblB);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Implied Black Volatility for the Specified Strike, the ATM Forward Rate, and the TTE
	 * 
	 * @param dblStrike Strike
	 * @param dblATMForwardRate ATM Forward Rate
	 * @param dblTTE Time to Expiry
	 * @param dblSigma0 Initial Sigma
	 * 
	 * @return The Implied Black Volatility Instance
	 */

	public org.drip.dynamics.sabr.ImpliedBlackVolatility computeBlackVolatility (
		final double dblStrike,
		final double dblATMForwardRate,
		final double dblTTE,
		final double dblSigma0)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblStrike) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblATMForwardRate) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblTTE) ||
					!org.drip.quant.common.NumberUtil.IsValid (dblSigma0))
			return null;

		if (dblStrike == dblATMForwardRate)
			return computeATMBlackVolatility (dblATMForwardRate, dblTTE, dblSigma0);

		double dblLogF0ByK = java.lang.Math.log (dblATMForwardRate / dblStrike);

		double dblF0KExpSQRT = java.lang.Math.pow (dblATMForwardRate * dblStrike, 0.5 * (1. - _dblBeta));

		double dblZ = _dblVolatilityOfVolatility * dblF0KExpSQRT * dblLogF0ByK / dblSigma0;
		double dblOneMinusBetaLogF0ByK = (1. - _dblBeta) * (1. - _dblBeta) * dblLogF0ByK * dblLogF0ByK;
		double dblA = dblSigma0 / (dblF0KExpSQRT * (1. + (dblOneMinusBetaLogF0ByK / 24.) +
			(dblOneMinusBetaLogF0ByK * dblOneMinusBetaLogF0ByK / 1920.)));
		double dblB = 1. + dblTTE * (((1. - _dblBeta) * (1. - _dblBeta) * dblSigma0 * dblSigma0 / (24. *
			dblF0KExpSQRT * dblF0KExpSQRT)) + (_dblRho * _dblBeta * _dblVolatilityOfVolatility * dblSigma0 /
				(4. * dblF0KExpSQRT)) + ((2. - 3. * _dblRho * _dblRho) * _dblVolatilityOfVolatility *
					_dblVolatilityOfVolatility / 24.));

		double dblChiZ = java.lang.Math.log ((java.lang.Math.sqrt (1. - 2. * _dblRho * dblZ + dblZ * dblZ) +
			dblZ - _dblRho) / (1. - _dblRho));

		try {
			return new org.drip.dynamics.sabr.ImpliedBlackVolatility (dblStrike, dblATMForwardRate, dblTTE,
				dblA, dblZ, dblChiZ, dblB, dblA * dblZ * dblB / dblChiZ);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
