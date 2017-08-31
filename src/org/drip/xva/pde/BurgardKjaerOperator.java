
package org.drip.xva.pde;

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
 * BurgardKjaerOperator sets up the Parabolic Differential Equation PDE based on the Ito Evolution
 * 	Differential for the Reference Underlier Asset, as laid out in Burgard and Kjaer (2014). The References
 * 	are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): Modeling, Pricing,
 *  	and Hedging Counter-party Credit Exposure - A Technical Guide, Springer Finance, New York.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 *  
 *  - Li, B., and Y. Tang (2007): Quantitative Analysis, Derivatives Modeling, and Trading Strategies in the
 *  	Presence of Counter-party Credit Risk for the Fixed Income Market, World Scientific Publishing,
 *  	Singapore.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BurgardKjaerOperator {
	private org.drip.xva.universe.TradeablesContainer _tc = null;
	private org.drip.xva.definition.PDEEvolutionControl _pdeec = null;

	/**
	 * BurgardKjaerOperator Constructor
	 * 
	 * @param tc The Universe of Tradeable Assets
	 * @param pdeec The XVA Control Settings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BurgardKjaerOperator (
		final org.drip.xva.universe.TradeablesContainer tc,
		final org.drip.xva.definition.PDEEvolutionControl pdeec)
		throws java.lang.Exception
	{
		if (null == (_tc = tc) || null == (_pdeec = pdeec))
			throw new java.lang.Exception ("BurgardKjaerOperator Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Universe of Trade-able Assets
	 * 
	 * @return The Universe of Trade-able Assets
	 */

	public org.drip.xva.universe.TradeablesContainer universe()
	{
		return _tc;
	}

	/**
	 * Retrieve the XVA Control Settings
	 * 
	 * @return The XVA Control Settings
	 */

	public org.drip.xva.definition.PDEEvolutionControl pdeec()
	{
		return _pdeec;
	}

	/**
	 * Generate the Derivative Value Time Increment using the Burgard Kjaer Scheme
	 * 
	 * @param me The Market Edge
	 * @param etvStart The Evolution Trajectory Vertex
	 * @param dblCollateral The Off-setting Collateral
	 * 
	 * @return The Time Increment using the Burgard Kjaer Scheme
	 */

	public org.drip.xva.pde.BurgardKjaerEdgeRun edgeRun (
		final org.drip.xva.universe.MarketEdge me,
		final org.drip.xva.derivative.EvolutionTrajectoryVertex etvStart,
		final double dblCollateral)
	{
		if (null == me || null == etvStart || !org.drip.quant.common.NumberUtil.IsValid (dblCollateral))
			return null;

		org.drip.xva.universe.MarketVertex mvFinish = me.finish();

		org.drip.xva.universe.EntityMarketVertex emvBankFinish = mvFinish.bank();

		org.drip.xva.universe.EntityMarketVertex emvCounterPartyFinish = mvFinish.counterParty();

		org.drip.xva.derivative.AssetGreekVertex agvStart = etvStart.assetGreekVertex();

		double dblDerivativeXVAValueStart = agvStart.derivativeXVAValue();

		double dblGainOnBankDefault = etvStart.gainOnBankDefault();

		double dblAssetValue = mvFinish.assetNumeraire();

		double dblAssetBump = _pdeec.sensitivityShiftFactor() * dblAssetValue;

		double dblBankSeniorDefaultIntensity = emvBankFinish.hazardRate();

		double dblCounterPartyDefaultIntensity = emvCounterPartyFinish.hazardRate();

		double dblBankGainOnCounterPartyDefault = etvStart.gainOnCounterPartyDefault();

		double dblGainOnCounterPartyDefault = dblCounterPartyDefaultIntensity *
			dblBankGainOnCounterPartyDefault;

		try {
			double[] adblBumpedTheta = new org.drip.xva.pde.ParabolicDifferentialOperator
				(_tc.asset()).thetaUpDown (etvStart, dblAssetValue, dblAssetBump);

			if (null == adblBumpedTheta || 3 != adblBumpedTheta.length) return null;

			return new org.drip.xva.pde.BurgardKjaerEdgeRun (
				dblAssetBump,
				-1. * adblBumpedTheta[0],
				-1. * adblBumpedTheta[1],
				-1. * adblBumpedTheta[2],
				mvFinish.collateralSchemeNumeraire().forward() * dblCollateral,
				(dblBankSeniorDefaultIntensity + dblCounterPartyDefaultIntensity) * dblDerivativeXVAValueStart,
				-1. * dblBankSeniorDefaultIntensity * dblGainOnBankDefault,
				-1. * dblGainOnCounterPartyDefault,
				0.
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Time Increment Run Attribution using the Burgard Kjaer Scheme
	 * 
	 * @param me The Market Edge
	 * @param etvStart The Starting Evolution Trajectory Vertex
	 * @param dblCollateral The Off-setting Collateral
	 * 
	 * @return The Time Increment Run Attribution using the Burgard Kjaer Scheme
	 */

	public org.drip.xva.pde.BurgardKjaerEdgeAttribution edgeRunAttribution (
		final org.drip.xva.universe.MarketEdge me,
		final org.drip.xva.derivative.EvolutionTrajectoryVertex etvStart,
		final double dblCollateral)
	{
		if (null == me || null == etvStart) return null;

		org.drip.xva.universe.MarketVertex mvFinish = me.finish();

		double dblDerivativeXVAValue = etvStart.assetGreekVertex().derivativeXVAValue();

		org.drip.xva.universe.EntityMarketVertex emvBankFinish = mvFinish.bank();

		org.drip.xva.universe.EntityMarketVertex emvCounterPartyFinish = mvFinish.counterParty();

		double dblCounterPartyRecovery = emvCounterPartyFinish.seniorRecoveryRate();

		double dblBankDefaultIntensity = emvBankFinish.hazardRate();

		double dblCounterPartyDefaultIntensity = emvCounterPartyFinish.hazardRate();

		double dblCloseOutMTM = org.drip.xva.definition.PDEEvolutionControl.CLOSEOUT_GREGORY_LI_TANG ==
			_pdeec.closeOutScheme() ? dblDerivativeXVAValue : dblDerivativeXVAValue;

		double dblBankExposure = dblCloseOutMTM > 0. ? dblCloseOutMTM : emvBankFinish.seniorRecoveryRate() *
			dblCloseOutMTM;

		double dblAssetValue = mvFinish.assetNumeraire();

		double dblAssetBump = _pdeec.sensitivityShiftFactor() * dblAssetValue;

		double dblDerivativeXVACounterPartyDefaultGrowth = -1. * dblCounterPartyDefaultIntensity *
			(dblCloseOutMTM < 0. ? dblCloseOutMTM : dblCounterPartyRecovery * dblCloseOutMTM);

		double dblBankSeniorFundingSpread = emvBankFinish.seniorFundingSpread() / me.vertexIncrement();

		try {
			double[] adblBumpedTheta = new org.drip.xva.pde.ParabolicDifferentialOperator
				(_tc.asset()).thetaUpDown (etvStart, dblAssetValue, dblAssetBump);

			if (null == adblBumpedTheta || 3 != adblBumpedTheta.length) return null;

			return new org.drip.xva.pde.BurgardKjaerEdgeAttribution (
				dblAssetBump,
				-1. * adblBumpedTheta[0],
				-1. * adblBumpedTheta[1],
				-1. * adblBumpedTheta[2],
				mvFinish.collateralSchemeNumeraire().forward() * dblCollateral,
				(dblBankDefaultIntensity + dblCounterPartyDefaultIntensity) * dblDerivativeXVAValue,
				dblBankSeniorFundingSpread * dblBankExposure,
				-1. * dblBankDefaultIntensity * dblBankExposure,
				dblDerivativeXVACounterPartyDefaultGrowth
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
