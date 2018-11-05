
package org.drip.dynamics.lmm;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>ContinuousForwardRateEvolver</i> sets up and implements the Multi-Factor No-arbitrage Dynamics of the
 *  Rates State Quantifiers traced from the Evolution of the Continuously Compounded Forward Rate as
 *  formulated in:
 * 	<br>
 *  <ul>
 *  	<li>
 *  		Goldys, B., M. Musiela, and D. Sondermann (1994): <i>Log-normality of Rates and Term Structure
 *  			Models</i> <b>The University of New South Wales</b>
 *  	</li>
 *  	<li>
 *  		Musiela, M. (1994): <i>Nominal Annual Rates and Log-normal Volatility Structure</i> <b>The
 *  			University of New South Wales</b>
 *  	</li>
 *  	<li>
 * 			Brace, A., D. Gatarek, and M. Musiela (1997): The Market Model of Interest Rate Dynamics
 * 				<i>Mathematical Finance</i> <b>7 (2)</b> 127-155
 *  	</li>
 *  </ul>
 * 	<br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics">Dynamics</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/lmm">LIBOR Market Model</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/Fixed Income">Fixed Income Analytics</a></li>
 *  </ul>
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
