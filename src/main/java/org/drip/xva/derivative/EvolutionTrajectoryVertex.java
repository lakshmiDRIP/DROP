
package org.drip.xva.derivative;

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
 * EvolutionTrajectoryVertex holds the Evolution Snapshot of the Trade-able Prices, the Cash Account, the
 *  Replication Portfolio, and the corresponding Derivative Value, as laid out in Burgard and Kjaer (2014).
 *  The References are:
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

public class EvolutionTrajectoryVertex
{
	private double _time = java.lang.Double.NaN;
	private double _collateral = java.lang.Double.NaN;
	private double _hedgeError = java.lang.Double.NaN;
	private double _bankGainOnCounterPartyDefault = java.lang.Double.NaN;
	private double _counterPartyGainOnBankDefault = java.lang.Double.NaN;
	private org.drip.xva.derivative.PositionGreekVertex _positionGreekVertex = null;
	private org.drip.xva.derivative.ReplicationPortfolioVertex _replicationPortfolioVertex = null;

	/**
	 * EvolutionTrajectoryVertex Constructor
	 * 
	 * @param time The Evolution Trajectory Edge Time
	 * @param replicationPortfolioVertex The Replication Portfolio Vertex
	 * @param positionGreekVertex The Position Greek Vertex
	 * @param counterPartyGainOnBankDefault Counter Party Gain On Bank Default
	 * @param bankGainOnCounterPartyDefault Bank Gain On Default of Counter Party
	 * @param collateral The Vertex Collateral
	 * @param hedgeError The Vertex Hedge Error
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EvolutionTrajectoryVertex (
		final double time,
		final org.drip.xva.derivative.ReplicationPortfolioVertex replicationPortfolioVertex,
		final org.drip.xva.derivative.PositionGreekVertex positionGreekVertex,
		final double counterPartyGainOnBankDefault,
		final double bankGainOnCounterPartyDefault,
		final double collateral,
		final double hedgeError)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_time = time) ||
			null == (_replicationPortfolioVertex = replicationPortfolioVertex) ||
			null == (_positionGreekVertex = positionGreekVertex) ||
			!org.drip.quant.common.NumberUtil.IsValid (_counterPartyGainOnBankDefault =
				counterPartyGainOnBankDefault) ||
			!org.drip.quant.common.NumberUtil.IsValid (_bankGainOnCounterPartyDefault =
				bankGainOnCounterPartyDefault) ||
			!org.drip.quant.common.NumberUtil.IsValid (_collateral = collateral) ||
			!org.drip.quant.common.NumberUtil.IsValid (_hedgeError = hedgeError))
		{
			throw new java.lang.Exception ("EvolutionTrajectoryVertex Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Time Instant
	 * 
	 * @return The Time Instant
	 */

	public double time()
	{
		return _time;
	}

	/**
	 * Retrieve the Collateral
	 * 
	 * @return The Collateral
	 */

	public double collateral()
	{
		return _collateral;
	}

	/**
	 * Retrieve the Hedge Error
	 * 
	 * @return The Hedge Error
	 */

	public double hedgeError()
	{
		return _hedgeError;
	}

	/**
	 * Retrieve the Replication Portfolio Vertex
	 * 
	 * @return The Replication Portfolio Vertex
	 */

	public org.drip.xva.derivative.ReplicationPortfolioVertex replicationPortfolioVertex()
	{
		return _replicationPortfolioVertex;
	}

	/**
	 * Retrieve the Position Greek Vertex
	 * 
	 * @return The Position Greek Vertex
	 */

	public org.drip.xva.derivative.PositionGreekVertex positionGreekVertex()
	{
		return _positionGreekVertex;
	}

	/**
	 * Retrieve the Counter Party Gain On Bank Default
	 * 
	 * @return The Counter Party Gain On Bank Default
	 */

	public double gainOnBankDefault()
	{
		return _counterPartyGainOnBankDefault;
	}

	/**
	 * Retrieve the Bank Gain On Individual Counter Party Default
	 * 
	 * @return The Bank Gain On Individual Counter Party Default
	 */

	public double gainOnCounterPartyDefault()
	{
		return _bankGainOnCounterPartyDefault;
	}

	/**
	 * Indicate whether Replication Portfolio satisfies the Funding Constraint implied by the Vertex
	 * 	Numeraire
	 * 
	 * @param marketVertex The Market Vertex
	 * 
	 * @return The Funding Constraint Verification Mismatch
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double verifyFundingConstraint (
		final org.drip.xva.universe.MarketVertex marketVertex)
		throws java.lang.Exception
	{
		if (null == marketVertex)
			throw new java.lang.Exception
				("EvolutionTrajectoryVertex::verifyFundingConstraint => Invalid Inputs");

		org.drip.xva.universe.EntityMarketVertex bankMarketVertex = marketVertex.bank();

		double fundingConstraint = _positionGreekVertex.derivativeXVAValue() +
			bankMarketVertex.seniorFundingLatentState().nodal() *
			_replicationPortfolioVertex.bankSeniorNumeraireUnits();

		org.drip.xva.universe.LatentStateMarketVertex bankSubordinateFundingMarketVertex =
			bankMarketVertex.subordinateFundingLatentState();

		if (null != bankSubordinateFundingMarketVertex)
			fundingConstraint += bankSubordinateFundingMarketVertex.nodal() *
				_replicationPortfolioVertex.bankSubordinateNumeraireUnits();

		return fundingConstraint;
	}
}
