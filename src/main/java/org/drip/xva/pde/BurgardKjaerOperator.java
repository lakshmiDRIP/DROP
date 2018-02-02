
package org.drip.xva.pde;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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

public class BurgardKjaerOperator
{
	private org.drip.xva.evolver.PrimarySecurityContainer _tradeablesContainer = null;
	private org.drip.xva.definition.PDEEvolutionControl _pdeEvolutionControl = null;

	/**
	 * BurgardKjaerOperator Constructor
	 * 
	 * @param tradeablesContainer The Universe of Tradeable Assets
	 * @param pdeEvolutionControl The XVA Control Settings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BurgardKjaerOperator (
		final org.drip.xva.evolver.PrimarySecurityContainer tradeablesContainer,
		final org.drip.xva.definition.PDEEvolutionControl pdeEvolutionControl)
		throws java.lang.Exception
	{
		if (null == (_tradeablesContainer = tradeablesContainer) ||
			null == (_pdeEvolutionControl = pdeEvolutionControl))
		{
			throw new java.lang.Exception ("BurgardKjaerOperator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Universe of Trade-able Assets
	 * 
	 * @return The Universe of Trade-able Assets
	 */

	public org.drip.xva.evolver.PrimarySecurityContainer tradeablesContainer()
	{
		return _tradeablesContainer;
	}

	/**
	 * Retrieve the XVA Control Settings
	 * 
	 * @return The XVA Control Settings
	 */

	public org.drip.xva.definition.PDEEvolutionControl pdeEvolutionControl()
	{
		return _pdeEvolutionControl;
	}

	/**
	 * Generate the Derivative Value Time Increment using the Burgard Kjaer Scheme
	 * 
	 * @param marketEdge The Market Edge
	 * @param initialTrajectoryVertex The Evolution Trajectory Vertex
	 * @param collateral The Off-setting Collateral
	 * 
	 * @return The Time Increment using the Burgard Kjaer Scheme
	 */

	public org.drip.xva.pde.BurgardKjaerEdgeRun edgeRun (
		final org.drip.xva.universe.MarketEdge marketEdge,
		final org.drip.xva.derivative.EvolutionTrajectoryVertex initialTrajectoryVertex,
		final double collateral)
	{
		if (null == marketEdge ||
			null == initialTrajectoryVertex ||
			!org.drip.quant.common.NumberUtil.IsValid (collateral))
		{
			return null;
		}

		org.drip.xva.universe.MarketVertex finalMarketVertex = marketEdge.finish();

		org.drip.xva.universe.MarketVertexEntity finalBankMarketVertex = finalMarketVertex.bank();

		org.drip.xva.universe.MarketVertexEntity finalCounterPartyMarketVertex =
			finalMarketVertex.counterParty();

		org.drip.xva.derivative.PositionGreekVertex initialPositionGreekVertex =
			initialTrajectoryVertex.positionGreekVertex();

		double initialDerivativeXVAValue = initialPositionGreekVertex.derivativeXVAValue();

		double gainOnBankDefault = initialTrajectoryVertex.gainOnBankDefault();

		double initialPortfolioValue = finalMarketVertex.positionManifestValue();

		double portfolioValueBump = _pdeEvolutionControl.sensitivityShiftFactor() * initialPortfolioValue;

		double bankSeniorDefaultIntensity = finalBankMarketVertex.hazardRate();

		double counterPartyDefaultIntensity = finalCounterPartyMarketVertex.hazardRate();

		double bankGainOnCounterPartyDefault = initialTrajectoryVertex.gainOnCounterPartyDefault();

		double gainOnCounterPartyDefault = counterPartyDefaultIntensity * bankGainOnCounterPartyDefault;

		try
		{
			double[] bumpedThetaArray = new org.drip.xva.pde.ParabolicDifferentialOperator
				(_tradeablesContainer.position()).thetaUpDown (
					initialTrajectoryVertex,
					initialPortfolioValue,
					portfolioValueBump
				);

			if (null == bumpedThetaArray || 3 != bumpedThetaArray.length)
			{
				return null;
			}

			return new org.drip.xva.pde.BurgardKjaerEdgeRun (
				portfolioValueBump,
				-1. * bumpedThetaArray[0],
				-1. * bumpedThetaArray[1],
				-1. * bumpedThetaArray[2],
				finalMarketVertex.csaReplicator() * collateral,
				(bankSeniorDefaultIntensity + counterPartyDefaultIntensity) * initialDerivativeXVAValue,
				-1. * bankSeniorDefaultIntensity * gainOnBankDefault,
				-1. * gainOnCounterPartyDefault,
				0.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Time Increment Run Attribution using the Burgard Kjaer Scheme
	 * 
	 * @param marketEdge The Market Edge
	 * @param initialTrajectoryVertex The Starting Evolution Trajectory Vertex
	 * @param collateral The Off-setting Collateral
	 * 
	 * @return The Time Increment Run Attribution using the Burgard Kjaer Scheme
	 */

	public org.drip.xva.pde.BurgardKjaerEdgeAttribution edgeRunAttribution (
		final org.drip.xva.universe.MarketEdge marketEdge,
		final org.drip.xva.derivative.EvolutionTrajectoryVertex initialTrajectoryVertex,
		final double collateral)
	{
		if (null == marketEdge ||
			null == initialTrajectoryVertex)
		{
			return null;
		}

		org.drip.xva.universe.MarketVertex finalMarketVertex = marketEdge.finish();

		double derivativeXVAValue = initialTrajectoryVertex.positionGreekVertex().derivativeXVAValue();

		org.drip.xva.universe.MarketVertexEntity finalBankMarketVertex = finalMarketVertex.bank();

		org.drip.xva.universe.MarketVertexEntity finalCounterPartyMarketVertex =
			finalMarketVertex.counterParty();

		double counterPartyRecoveryRate = finalCounterPartyMarketVertex.seniorRecoveryRate();

		double bankDefaultIntensity = finalBankMarketVertex.hazardRate();

		double counterPartyDefaultIntensity = finalCounterPartyMarketVertex.hazardRate();

		double closeOutMTM = org.drip.xva.definition.PDEEvolutionControl.CLOSEOUT_GREGORY_LI_TANG ==
			_pdeEvolutionControl.closeOutScheme() ? derivativeXVAValue : derivativeXVAValue;

		double bankExposure = closeOutMTM > 0. ? closeOutMTM : finalBankMarketVertex.seniorRecoveryRate() *
			closeOutMTM;

		double initialPortfolioValue = finalMarketVertex.positionManifestValue();

		double portfolioValueBump = _pdeEvolutionControl.sensitivityShiftFactor() * initialPortfolioValue;

		double derivativeXVACounterPartyDefaultGrowth = -1. * counterPartyDefaultIntensity *
			(closeOutMTM < 0. ? closeOutMTM : counterPartyRecoveryRate * closeOutMTM);

		double bankSeniorFundingSpread = finalBankMarketVertex.seniorFundingSpread() /
			marketEdge.vertexIncrement();

		try
		{
			double[] bumpedThetaArray = new org.drip.xva.pde.ParabolicDifferentialOperator
				(_tradeablesContainer.position()).thetaUpDown (
					initialTrajectoryVertex,
					initialPortfolioValue,
					portfolioValueBump
				);

			if (null == bumpedThetaArray || 3 != bumpedThetaArray.length)
			{
				return null;
			}

			return new org.drip.xva.pde.BurgardKjaerEdgeAttribution (
				portfolioValueBump,
				-1. * bumpedThetaArray[0],
				-1. * bumpedThetaArray[1],
				-1. * bumpedThetaArray[2],
				finalMarketVertex.csaReplicator() * collateral,
				(bankDefaultIntensity + counterPartyDefaultIntensity) * derivativeXVAValue,
				bankSeniorFundingSpread * bankExposure,
				-1. * bankDefaultIntensity * bankExposure,
				derivativeXVACounterPartyDefaultGrowth
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
