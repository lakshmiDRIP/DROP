
package org.drip.dynamics.hjm;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>MultiFactorStateEvolver</i> sets up and implements the Base Multi-Factor No-arbitrage Dynamics of the
 * Rates State Quantifiers as formulated in:
 * 
 * <ul>
 * 	<li>
 * 		Heath, D., R. Jarrow, and A. Morton (1992): Bond Pricing and Term Structure of Interest Rates: A New
 * 			Methodology for Contingent Claims Valuation <i>Econometrica</i> <b>60 (1)</b> 77-105
 * 	</li>
 * </ul>
 *
 *	<br><br>
 * In particular it looks to evolve the Multi-factor Instantaneous Forward Rates.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">HJM, Hull White, LMM, and SABR Dynamic Evolution Models</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/hjm/README.md">HJM Based Latent State Evolution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultiFactorStateEvolver implements org.drip.dynamics.evolution.PointStateEvolver {
	private org.drip.dynamics.hjm.MultiFactorVolatility _mfv = null;
	private org.drip.state.identifier.ForwardLabel _lslForward = null;
	private org.drip.state.identifier.FundingLabel _lslFunding = null;
	private org.drip.function.definition.R1ToR1 _auInitialInstantaneousForwardRate = null;

	/**
	 * MultiFactorStateEvolver Constructor
	 * 
	 * @param lslFunding The Funding Latent State Label
	 * @param lslForward The Forward Latent State Label
	 * @param mfv The Multi-Factor Volatility Instance
	 * @param auInitialInstantaneousForwardRate The Initial Instantaneous Forward Rate Term Structure
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public MultiFactorStateEvolver (
		final org.drip.state.identifier.FundingLabel lslFunding,
		final org.drip.state.identifier.ForwardLabel lslForward,
		final org.drip.dynamics.hjm.MultiFactorVolatility mfv,
		final org.drip.function.definition.R1ToR1 auInitialInstantaneousForwardRate)
		throws java.lang.Exception
	{
		if (null == (_lslFunding = lslFunding) || null == (_lslForward = lslForward) || null == (_mfv = mfv)
			|| null == (_auInitialInstantaneousForwardRate = auInitialInstantaneousForwardRate))
			throw new java.lang.Exception ("MultiFactorStateEvolver ctr: Invalid Inputs");
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

	/**
	 * Retrieve the Initial Instantaneous Forward Rate Term Structure
	 * 
	 * @return The Initial Instantaneous Forward Rate Term Structure
	 */

	public org.drip.function.definition.R1ToR1 instantaneousForwardInitialTermStructure()
	{
		return _auInitialInstantaneousForwardRate;
	}

	/**
	 * Compute the Instantaneous Forward Rate Increment given the View Date, the Target Date, and the View
	 * 	Time Increment
	 * 
	 * @param iViewDate The View Date
	 * @param iTargetDate The Target Date
	 * @param iViewTimeIncrement The View Time Increment
	 * 
	 * @return The Instantaneous Forward Rate Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Instantaneous Forward Rate Increment cannot be computed
	 */

	public double instantaneousForwardRateIncrement (
		final int iViewDate,
		final int iTargetDate,
		final int iViewTimeIncrement)
		throws java.lang.Exception
	{
		if (iTargetDate <= iViewDate)
			throw new java.lang.Exception
				("MultiFactorStateEvolver::instantaneousForwardRateIncrement => Invalid Inputs");

		org.drip.sequence.random.PrincipalFactorSequenceGenerator pfsg = _mfv.msg();

		int iNumFactor = pfsg.numFactor();

		double[] adblMultivariateRandom = pfsg.random();

		double dblIntantaneousForwardRateIncrement = 0.;
		double dblAnnualizedTimeIncrement = 1. * iViewTimeIncrement / 365.25;

		double dblAnnualizedTimeIncrementSQRT = java.lang.Math.sqrt (dblAnnualizedTimeIncrement);

		for (int i = 0; i < iNumFactor; ++i) {
			double dblWeightedFactorPointVolatility = _mfv.weightedFactorPointVolatility (i, iViewDate,
				iTargetDate);

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblWeightedFactorPointVolatility))
				throw new java.lang.Exception
					("MultiFactorStateEvolver::instantaneousForwardRateIncrement => Cannot compute View/Target Date Point Volatility");

			dblIntantaneousForwardRateIncrement += _mfv.volatilityIntegral (i, iViewDate, iTargetDate) *
				dblWeightedFactorPointVolatility * dblAnnualizedTimeIncrement +
					dblWeightedFactorPointVolatility * dblAnnualizedTimeIncrementSQRT *
						adblMultivariateRandom[i];
		}

		return dblIntantaneousForwardRateIncrement;
	}

	/**
	 * Compute the Proportional Price Increment given the View Date, the Target Date, the Short Rate, and the
	 *  View Time Increment
	 * 
	 * @param iViewDate The View Date
	 * @param iTargetDate The Target Date
	 * @param dblShortRate The Short Rate
	 * @param iViewTimeIncrement The View Time Increment
	 * 
	 * @return The Proportional Price Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Proportional Price Increment cannot be computed
	 */

	public double proportionalPriceIncrement (
		final int iViewDate,
		final int iTargetDate,
		final double dblShortRate,
		final int iViewTimeIncrement)
		throws java.lang.Exception
	{
		if (iTargetDate <= iViewDate || !org.drip.numerical.common.NumberUtil.IsValid (dblShortRate))
			throw new java.lang.Exception
				("MultiFactorStateEvolver::proportionalPriceIncrement => Invalid Inputs");

		org.drip.sequence.random.PrincipalFactorSequenceGenerator pfsg = _mfv.msg();

		int iNumFactor = pfsg.numFactor();

		double[] adblMultivariateRandom = pfsg.random();

		double dblAnnualizedTimeIncrement = 1. * iViewTimeIncrement / 365.25;
		double dblProportionalPriceIncrement = dblShortRate * dblAnnualizedTimeIncrement;

		double dblAnnualizedTimeIncrementSQRT = java.lang.Math.sqrt (dblAnnualizedTimeIncrement);

		for (int i = 0; i < iNumFactor; ++i)
			dblProportionalPriceIncrement -= _mfv.volatilityIntegral (i, iViewDate, iTargetDate) *
				dblAnnualizedTimeIncrementSQRT * adblMultivariateRandom[i];

		return dblProportionalPriceIncrement;
	}

	/**
	 * Compute the Short Rate Increment given the Spot Date, the View Date, and the View Time Increment
	 * 
	 * @param iSpotDate The Spot Date
	 * @param iViewDate The View Date
	 * @param iViewTimeIncrement The View Time Increment
	 * 
	 * @return The Short Rate Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Short Rate Increment cannot be computed
	 */

	public double shortRateIncrement (
		final int iSpotDate,
		final int iViewDate,
		final int iViewTimeIncrement)
		throws java.lang.Exception
	{
		if (iSpotDate > iViewDate)
			throw new java.lang.Exception ("MultiFactorStateEvolver::shortRateIncrement => Invalid Inputs");

		org.drip.sequence.random.PrincipalFactorSequenceGenerator pfsg = _mfv.msg();

		double[] adblMultivariateRandom = pfsg.random();

		int iNumFactor = pfsg.numFactor();

		double dblShortRateIncrement = 0.;
		double dblAnnualizedIncrement = 1. * iViewTimeIncrement / 365.25;

		double dblAnnualizedIncrementSQRT = java.lang.Math.sqrt (dblAnnualizedIncrement);

		for (int i = 0; i < iNumFactor; ++i) {
			double dblViewWeightedFactorVolatility = _mfv.weightedFactorPointVolatility (i, iViewDate,
				iViewDate);

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblViewWeightedFactorVolatility))
				throw new java.lang.Exception
					("MultiFactorStateEvolver::shortRateIncrement => Cannot compute View Date Factor Volatility");

			dblShortRateIncrement += _mfv.volatilityIntegral (i, iSpotDate, iViewDate) *
				dblViewWeightedFactorVolatility * dblAnnualizedIncrement + dblViewWeightedFactorVolatility *
					dblAnnualizedIncrementSQRT * adblMultivariateRandom[i];
		}

		return dblShortRateIncrement;
	}

	/**
	 * Compute the Continuously Compounded Short Rate Increment given the Spot Date, the View Date, the
	 *  Target Date, the Continuously Compounded Short Rate, the Current Short Rate, and the View Time
	 *  Increment.
	 * 
	 * @param iSpotDate The Spot Date
	 * @param iViewDate The View Date
	 * @param iTargetDate The Target Date
	 * @param dblCompoundedShortRate The Compounded Short Rate
	 * @param dblShortRate The Short Rate
	 * @param iViewTimeIncrement The View Time Increment
	 * 
	 * @return The Short Rate Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Continuously Compounded Short Rate Increment cannot be
	 * computed
	 */

	public double compoundedShortRateIncrement (
		final int iSpotDate,
		final int iViewDate,
		final int iTargetDate,
		final double dblCompoundedShortRate,
		final double dblShortRate,
		final int iViewTimeIncrement)
		throws java.lang.Exception
	{
		if (iSpotDate > iViewDate || iViewDate >= iTargetDate)
			throw new java.lang.Exception
				("MultiFactorStateEvolver::compoundedShortRateIncrement => Invalid Inputs");

		org.drip.sequence.random.PrincipalFactorSequenceGenerator pfsg = _mfv.msg();

		int iNumFactor = pfsg.numFactor();

		double[] adblMultivariateRandom = pfsg.random();

		double dblAnnualizedIncrement = 1. * iViewTimeIncrement / 365.25;
		double dblCompoundedShortRateIncrement = (dblCompoundedShortRate - dblShortRate) *
			dblAnnualizedIncrement;

		double dblAnnualizedIncrementSQRT = java.lang.Math.sqrt (dblAnnualizedIncrement);

		for (int i = 0; i < iNumFactor; ++i) {
			double dblViewTargetVolatilityIntegral = _mfv.volatilityIntegral (i, iViewDate, iTargetDate);

			dblCompoundedShortRateIncrement += 0.5 * dblViewTargetVolatilityIntegral *
				dblViewTargetVolatilityIntegral * dblAnnualizedIncrement + dblViewTargetVolatilityIntegral *
					dblAnnualizedIncrementSQRT * adblMultivariateRandom[i];
		}

		return dblCompoundedShortRateIncrement * 365.25 / (iTargetDate - iViewDate);
	}

	/**
	 * Compute the LIBOR Forward Rate Increment given the Spot Date, the View Date, the Target Date, the
	 *  Current LIBOR Forward Rate, and the View Time Increment
	 * 
	 * @param iSpotDate The Spot Date
	 * @param iViewDate The View Date
	 * @param iTargetDate The Target Date
	 * @param dblLIBORForwardRate The LIBOR Forward Rate
	 * @param iViewTimeIncrement The View Time Increment
	 * 
	 * @return The Forward Rate Increment
	 * 
	 * @throws java.lang.Exception Thrown if the LIBOR Forward Rate Increment cannot be computed
	 */

	public double liborForwardRateIncrement (
		final int iSpotDate,
		final int iViewDate,
		final int iTargetDate,
		final double dblLIBORForwardRate,
		final int iViewTimeIncrement)
		throws java.lang.Exception
	{
		if (iSpotDate > iViewDate || iViewDate >= iTargetDate || !org.drip.numerical.common.NumberUtil.IsValid
			(dblLIBORForwardRate))
			throw new java.lang.Exception
				("MultiFactorStateEvolver::liborForwardRateIncrement => Invalid Inputs");

		double dblAnnualizedTimeIncrementSQRT = java.lang.Math.sqrt (1. * iViewTimeIncrement / 365.25);

		org.drip.sequence.random.PrincipalFactorSequenceGenerator pfsg = _mfv.msg();

		double[] adblMultivariateRandom = pfsg.random();

		double dblLIBORForwardRateVolIncrement = 0.;

		int iNumFactor = pfsg.numFactor();

		for (int i = 0; i < iNumFactor; ++i)
			dblLIBORForwardRateVolIncrement += _mfv.volatilityIntegral (i, iViewDate, iTargetDate) *
				(_mfv.volatilityIntegral (i, iSpotDate, iTargetDate) + dblAnnualizedTimeIncrementSQRT *
					adblMultivariateRandom[i]);

		return (dblLIBORForwardRate + (365.25 / (iTargetDate - iViewDate))) *
			dblLIBORForwardRateVolIncrement;
	}

	/**
	 * Compute the Shifted LIBOR Forward Rate Increment given the Spot Date, the View Date, the Target Date,
	 * 	the Current Shifted LIBOR Forward Rate, and the View Time Increment
	 * 
	 * @param iSpotDate The Spot Date
	 * @param iViewDate The View Date
	 * @param iTargetDate The Target Date
	 * @param dblShiftedLIBORForwardRate The Shifted LIBOR Forward Rate
	 * @param iViewTimeIncrement The View Time Increment
	 * 
	 * @return The Shifted Forward Rate Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Shifted LIBOR Forward Rate Increment cannot be computed
	 */

	public double shiftedLIBORForwardIncrement (
		final int iSpotDate,
		final int iViewDate,
		final int iTargetDate,
		final double dblShiftedLIBORForwardRate,
		final int iViewTimeIncrement)
		throws java.lang.Exception
	{
		if (iSpotDate > iViewDate || iViewDate >= iTargetDate || !org.drip.numerical.common.NumberUtil.IsValid
			(dblShiftedLIBORForwardRate))
			throw new java.lang.Exception
				("MultiFactorStateEvolver::shiftedLIBORForwardIncrement => Invalid Inputs");

		double dblAnnualizedTimeIncrementSQRT = java.lang.Math.sqrt (1. * iViewTimeIncrement / 365.25);

		org.drip.sequence.random.PrincipalFactorSequenceGenerator pfsg = _mfv.msg();

		double[] adblMultivariateRandom = pfsg.random();

		double dblShiftedLIBORVolIncrement = 0.;

		int iNumFactor = pfsg.numFactor();

		for (int i = 0; i < iNumFactor; ++i)
			dblShiftedLIBORVolIncrement += _mfv.volatilityIntegral (i, iViewDate, iTargetDate) *
				(_mfv.volatilityIntegral (i, iSpotDate, iTargetDate) + dblAnnualizedTimeIncrementSQRT *
					adblMultivariateRandom[i]);

		return dblShiftedLIBORForwardRate * dblShiftedLIBORVolIncrement;
	}

	@Override public org.drip.dynamics.evolution.LSQMPointUpdate evolve (
		final int iSpotDate,
		final int iViewDate,
		final int iSpotTimeIncrement,
		final org.drip.dynamics.evolution.LSQMPointUpdate lsqmPrev)
	{
		if (iSpotDate > iViewDate || null == lsqmPrev || !(lsqmPrev instanceof
			org.drip.dynamics.hjm.ShortForwardRateUpdate))
			return null;

		org.drip.sequence.random.PrincipalFactorSequenceGenerator pfsg = _mfv.msg();

		double dblAnnualizedIncrement = 1. * iSpotTimeIncrement / 365.25;

		double dblAnnualizedIncrementSQRT = java.lang.Math.sqrt (dblAnnualizedIncrement);

		double[] adblMultivariateRandom = pfsg.random();

		int iNumFactor = pfsg.numFactor();

		org.drip.dynamics.hjm.ShortForwardRateUpdate qmInitial =
			(org.drip.dynamics.hjm.ShortForwardRateUpdate) lsqmPrev;

		try {
			double dblInitialPrice = qmInitial.price();

			double dblInitialShortRate = qmInitial.shortRate();

			double dblInitialLIBORForwardRate = qmInitial.liborForwardRate();

			double dblInitialCompoundedShortRate = qmInitial.compoundedShortRate();

			int iTargetDate = new org.drip.analytics.date.JulianDate (iViewDate).addTenor
				(_lslForward.tenor()).julian();

			double dblShortRateIncrement = 0.;
			double dblShiftedLIBORForwardRateIncrement = 0.;
			double dblInstantaneousForwardRateIncrement = 0.;
			double dblPriceIncrement = dblInitialShortRate * dblAnnualizedIncrement;
			double dblCompoundedShortRateIncrement = (dblInitialCompoundedShortRate - dblInitialShortRate) *
				dblAnnualizedIncrement;

			for (int i = 0; i < iNumFactor; ++i) {
				double dblViewDateFactorVolatility = _mfv.weightedFactorPointVolatility (i, iViewDate,
					iViewDate);

				if (!org.drip.numerical.common.NumberUtil.IsValid (dblViewDateFactorVolatility)) return null;

				double dblViewTargetFactorVolatility = _mfv.weightedFactorPointVolatility (i, iViewDate,
					iTargetDate);

				if (!org.drip.numerical.common.NumberUtil.IsValid (dblViewTargetFactorVolatility)) return null;

				double dblViewTargetVolatilityIntegral = _mfv.volatilityIntegral (i, iViewDate, iTargetDate);

				if (!org.drip.numerical.common.NumberUtil.IsValid (dblViewTargetVolatilityIntegral)) return null;

				double dblSpotViewVolatilityIntegral = _mfv.volatilityIntegral (i, iSpotDate, iViewDate);

				if (!org.drip.numerical.common.NumberUtil.IsValid (dblSpotViewVolatilityIntegral)) return null;

				double dblSpotTargetVolatilityIntegral = _mfv.volatilityIntegral (i, iSpotDate, iTargetDate);

				if (!org.drip.numerical.common.NumberUtil.IsValid (dblSpotTargetVolatilityIntegral)) return null;

				double dblScaledMultivariateRandom = dblAnnualizedIncrementSQRT * adblMultivariateRandom[i];
				dblInstantaneousForwardRateIncrement += dblViewTargetVolatilityIntegral *
					dblViewTargetFactorVolatility * dblAnnualizedIncrement + dblViewTargetFactorVolatility *
						dblScaledMultivariateRandom;
				dblShortRateIncrement += dblSpotViewVolatilityIntegral * dblViewDateFactorVolatility *
					dblAnnualizedIncrement + dblViewDateFactorVolatility * dblScaledMultivariateRandom;
				dblCompoundedShortRateIncrement += 0.5 * dblViewTargetVolatilityIntegral *
					dblViewTargetVolatilityIntegral * dblAnnualizedIncrement +
						dblViewTargetVolatilityIntegral * dblScaledMultivariateRandom;
				dblShiftedLIBORForwardRateIncrement += dblViewTargetVolatilityIntegral *
					(dblSpotTargetVolatilityIntegral + dblScaledMultivariateRandom);
				dblPriceIncrement -= dblViewTargetVolatilityIntegral * dblScaledMultivariateRandom;
			}

			dblPriceIncrement *= dblInitialPrice;
			dblCompoundedShortRateIncrement *= 365.25 / (iTargetDate - iViewDate);
			double dblLIBORForwardRateIncrement = (dblInitialLIBORForwardRate + (365.25 / (iTargetDate -
				iViewDate))) * dblShiftedLIBORForwardRateIncrement;

			return org.drip.dynamics.hjm.ShortForwardRateUpdate.Create (_lslFunding, _lslForward, iSpotDate,
				iSpotDate + iSpotTimeIncrement, iTargetDate, qmInitial.instantaneousForwardRate() +
					dblInstantaneousForwardRateIncrement, dblInstantaneousForwardRateIncrement,
						dblInitialLIBORForwardRate + dblLIBORForwardRateIncrement,
							dblLIBORForwardRateIncrement, qmInitial.shiftedLIBORForwardRate() +
								dblShiftedLIBORForwardRateIncrement, dblShiftedLIBORForwardRateIncrement,
									dblInitialShortRate + dblShortRateIncrement, dblShortRateIncrement,
										dblInitialCompoundedShortRate + dblCompoundedShortRateIncrement,
											dblCompoundedShortRateIncrement, dblInitialPrice +
												dblPriceIncrement, dblPriceIncrement);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
