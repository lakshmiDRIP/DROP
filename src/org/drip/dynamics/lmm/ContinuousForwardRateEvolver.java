
package org.drip.dynamics.lmm;

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
 * ContinuousForwardRateEvolver sets up and implements the Multi-Factor No-arbitrage Dynamics of the Rates
 *  State Quantifiers traced from the Evolution of the Continuously Compounded Forward Rate as formulated in:
 * 
 *  1) Goldys, B., M. Musiela, and D. Sondermann (1994): Log-normality of Rates and Term Structure Models,
 *  	The University of New South Wales.
 * 
 *  2) Musiela, M. (1994): Nominal Annual Rates and Log-normal Volatility Structure, The University of New
 *   	South Wales.
 * 
 * 	3) Brace, A., D. Gatarek, and M. Musiela (1997): The Market Model of Interest Rate Dynamics, Mathematical
 * 		Finance 7 (2), 127-155.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ContinuousForwardRateEvolver implements org.drip.dynamics.evolution.PointStateEvolver {
	private org.drip.dynamics.hjm.MultiFactorVolatility _mfv = null;
	private org.drip.state.identifier.ForwardLabel _lslForward = null;
	private org.drip.state.identifier.FundingLabel _lslFunding = null;

	private double volatilityRandomDotProduct (
		final int iViewDate,
		final int iTargetDate,
		final int iViewTimeIncrement)
		throws java.lang.Exception
	{
		double dblViewTimeIncrementSQRT = java.lang.Math.sqrt (iViewTimeIncrement);

		org.drip.sequence.random.PrincipalFactorSequenceGenerator pfsg = _mfv.msg();

		double[] adblMultivariateRandom = pfsg.random();

		double dblVolatilityRandomDotProduct = 0.;

		int iNumFactor = pfsg.numFactor();

		for (int i = 0; i < iNumFactor; ++i)
			dblVolatilityRandomDotProduct += _mfv.weightedFactorPointVolatility (i, iViewDate, iTargetDate) *
				adblMultivariateRandom[i] * dblViewTimeIncrementSQRT;

		return dblVolatilityRandomDotProduct;
	}

	private double volatilityRandomDotDerivative (
		final int iViewDate,
		final int iTargetDate,
		final int iViewTimeIncrement,
		final boolean bTerminal)
		throws java.lang.Exception
	{
		org.drip.function.definition.R1ToR1 pointVolatilityFunctionR1ToR1 = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				return bTerminal ? volatilityRandomDotProduct (iViewDate, (int) dblX, iViewTimeIncrement) :
					volatilityRandomDotProduct ((int) dblX, iTargetDate, iViewTimeIncrement);
			}
		};

		return pointVolatilityFunctionR1ToR1.derivative (bTerminal ? iTargetDate : iViewDate, 1);
	}

	/**
	 * ContinuousForwardRateEvolver Constructor
	 * 
	 * @param lslFunding The Funding Latent State Label
	 * @param lslForward The Forward Latent State Label
	 * @param mfv The Multi-Factor Volatility Instance
	 * @param auInitialInstantaneousForwardRate The Instantaneous Forward Rate Function
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public ContinuousForwardRateEvolver (
		final org.drip.state.identifier.FundingLabel lslFunding,
		final org.drip.state.identifier.ForwardLabel lslForward,
		final org.drip.dynamics.hjm.MultiFactorVolatility mfv,
		final org.drip.function.definition.R1ToR1 auInitialInstantaneousForwardRate)
		throws java.lang.Exception
	{
		if (null == (_lslFunding = lslFunding) || null == (_lslForward = lslForward) || null == (_mfv = mfv))
			throw new java.lang.Exception ("ContinuousForwardRateEvolver ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Funding Label
	 * 
	 * @return The Funding Label
	 */

	public org.drip.state.identifier.FundingLabel fundingLabel()
	{
		return _lslFunding;
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
	 * Retrieve the Multi-factor Volatility Instance
	 * 
	 * @return The Multi-factor Volatility Instance
	 */

	public org.drip.dynamics.hjm.MultiFactorVolatility mfv()
	{
		return _mfv;
	}

	@Override public org.drip.dynamics.lmm.ContinuousForwardRateUpdate evolve (
		final int iSpotDate,
		final int iViewDate,
		final int iSpotTimeIncrement,
		final org.drip.dynamics.evolution.LSQMPointUpdate lsqmPrev)
	{
		if (iSpotDate > iViewDate || (null != lsqmPrev && !(lsqmPrev instanceof
			org.drip.dynamics.lmm.ContinuousForwardRateUpdate)))
			return null;

		org.drip.dynamics.lmm.ContinuousForwardRateUpdate bgmPrev =
			(org.drip.dynamics.lmm.ContinuousForwardRateUpdate) lsqmPrev;

		double dblDContinuousForwardDXTerminalPrev = bgmPrev.dContinuousForwardDXTerminal();

		double dblDContinuousForwardDXInitialPrev = bgmPrev.dContinuousForwardDXInitial();

		try {
			double dblDiscountFactorPrev = bgmPrev.discountFactor();

			double dblSpotRateIncrement = dblDContinuousForwardDXInitialPrev * iSpotTimeIncrement +
				volatilityRandomDotDerivative (iSpotDate, iViewDate, iSpotTimeIncrement, false);

			double dblContinuousForwardIncrement = (dblDContinuousForwardDXTerminalPrev + 0.5 *
				_mfv.pointVolatilityModulusDerivative (iSpotDate, iViewDate, 1, true)) * iSpotTimeIncrement +
					volatilityRandomDotDerivative (iSpotDate, iViewDate, iSpotTimeIncrement, true);

			double dblContinuousForwardRate = bgmPrev.continuousForwardRate() +
				dblContinuousForwardIncrement;

			double dblSpotRate = bgmPrev.spotRate() + dblSpotRateIncrement;

			double dblDiscountFactorIncrement = dblDiscountFactorPrev * ((dblSpotRate -
				dblContinuousForwardRate) * iSpotTimeIncrement - volatilityRandomDotProduct (iSpotDate,
					iViewDate, iSpotTimeIncrement));

			return org.drip.dynamics.lmm.ContinuousForwardRateUpdate.Create (_lslFunding, _lslForward,
				iSpotDate, iSpotDate + iSpotTimeIncrement, iViewDate, dblContinuousForwardRate,
					dblContinuousForwardIncrement, dblSpotRate, dblSpotRateIncrement, dblDiscountFactorPrev +
						dblDiscountFactorIncrement, dblDiscountFactorIncrement,
							dblDContinuousForwardDXInitialPrev, dblDContinuousForwardDXTerminalPrev);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Realized Zero Coupon Bond Forward Price
	 * 
	 * @param iSpotDate The Spot Date
	 * @param iForwardDate The Forward Date
	 * @param iMaturityDate The Maturity Date
	 * @param dblSpotPrice The Spot Price
	 * @param dblSpotForwardReinvestmentAccrual The Continuously Re-invested Accruing Bank Account
	 * 
	 * @return The Realized Zero Coupon Bond Forward Price
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public double zeroCouponForwardPrice (
		final int iSpotDate,
		final int iForwardDate,
		final int iMaturityDate,
		final double dblSpotPrice,
		final double dblSpotForwardReinvestmentAccrual)
		throws java.lang.Exception
	{
		if (iSpotDate > iForwardDate || iForwardDate > iMaturityDate ||
			!org.drip.quant.common.NumberUtil.IsValid (dblSpotPrice) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblSpotForwardReinvestmentAccrual))
			throw new java.lang.Exception
				("ContinuousForwardRateEvolver::zeroCouponForwardPrice => Invalid Inputs");

		int iPeriodIncrement = iForwardDate - iSpotDate;

		return dblSpotPrice / dblSpotForwardReinvestmentAccrual * java.lang.Math.exp (-1. *
			(volatilityRandomDotProduct (iSpotDate, iForwardDate, iPeriodIncrement) + 0.5 * iPeriodIncrement
				* _mfv.pointVolatilityModulus (iSpotDate, iForwardDate)));
	}
}
