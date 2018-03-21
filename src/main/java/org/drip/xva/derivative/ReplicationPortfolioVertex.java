
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
 * ReplicationPortfolioVertex contains the Dynamic Replicating Portfolio of the Pay-out using the Assets in
 *  the Economy, from the Dealer's View Point. The References are:
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

public class ReplicationPortfolioVertex
{
	private double _cashAccount = java.lang.Double.NaN;
	private double _positionHoldings = java.lang.Double.NaN;
	private double _clientNumeraireHoldings = java.lang.Double.NaN;
	private double _dealerSeniorNumeraireHoldings = java.lang.Double.NaN;
	private double _dealerSubordinateNumeraireHoldings = java.lang.Double.NaN;

	/**
	 * Construct a ReplicationPortfolioVertex Instance without the Zero Recovery Dealer Numeraire
	 * 
	 * @param positionHoldings The Asset Numeraire Holdings
	 * @param dealerSeniorNumeraireHoldings The Dealer Senior Numeraire Holdings
	 * @param clientNumeraireHoldings The Client Numeraire Replication Holdings
	 * @param cashAccount The Cash Account
	 * 
	 * @return The ReplicationPortfolioVertex Instance without the Zero Recovery Dealer Numeraire
	 */

	public static final ReplicationPortfolioVertex Standard (
		final double positionHoldings,
		final double dealerSeniorNumeraireHoldings,
		final double clientNumeraireHoldings,
		final double cashAccount)
	{
		try
		{
			return new ReplicationPortfolioVertex (
				positionHoldings,
				dealerSeniorNumeraireHoldings,
				0.,
				clientNumeraireHoldings,
				cashAccount
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ReplicationPortfolioVertex Constructor
	 * 
	 * @param positionHoldings The Asset Numeraire Holdings
	 * @param dealerSeniorNumeraireHoldings The Dealer Senior Numeraire Holdings
	 * @param dealerSubordinateNumeraireHoldings The Dealer Subordinate Numeraire Holdings
	 * @param clientNumeraireHoldings The Client Numeraire Holdings
	 * @param cashAccount The Cash Account
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ReplicationPortfolioVertex (
		final double positionHoldings,
		final double dealerSeniorNumeraireHoldings,
		final double dealerSubordinateNumeraireHoldings,
		final double clientNumeraireHoldings,
		final double cashAccount)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dealerSeniorNumeraireHoldings =
				dealerSeniorNumeraireHoldings) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dealerSubordinateNumeraireHoldings =
				dealerSubordinateNumeraireHoldings) ||
			!org.drip.quant.common.NumberUtil.IsValid (_clientNumeraireHoldings = clientNumeraireHoldings) ||
			!org.drip.quant.common.NumberUtil.IsValid (_cashAccount = cashAccount))
		{
			throw new java.lang.Exception ("ReplicationPortfolioVertex Constructor => Invalid Inputs");
		}

		_positionHoldings = positionHoldings;
	}

	/**
	 * Retrieve the Number of Position Holdings
	 * 
	 * @return The Number of Position Holdings
	 */

	public double positionHoldings()
	{
		return _positionHoldings;
	}

	/**
	 * Retrieve the Number of Dealer Senior Numeraire Holdings
	 * 
	 * @return The Number of Dealer Senior Numeraire Holdings
	 */

	public double dealerSeniorNumeraireHoldings()
	{
		return _dealerSeniorNumeraireHoldings;
	}

	/**
	 * Retrieve the Number of Dealer Subordinate Numeraire Holdings
	 * 
	 * @return The Number of Dealer Subordinate Numeraire Holdings
	 */

	public double dealerSubordinateNumeraireHoldings()
	{
		return _dealerSubordinateNumeraireHoldings;
	}

	/**
	 * Retrieve the Client Numeraire Holdings
	 * 
	 * @return The Client Numeraire Holdings
	 */

	public double clientNumeraireHoldings()
	{
		return _clientNumeraireHoldings;
	}

	/**
	 * Retrieve the Cash Account Amount
	 * 
	 * @return The Cash Account Amount
	 */

	public double cashAccount()
	{
		return _cashAccount;
	}

	/**
	 * Compute the Market Value of the Dealer Position Pre-Default
	 * 
	 * @param marketVertex The Market Vertex
	 * 
	 * @return The Market Value of the Dealer Position Pre-Default
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double dealerPreDefaultPositionValue (
		final org.drip.xva.universe.MarketVertex marketVertex)
		throws java.lang.Exception
	{
		if (null == marketVertex)
		{
			throw new java.lang.Exception
				("ReplicationPortfolioVertex::dealerPreDefaultPositionValue => Invalid Inputs");
		}

		org.drip.xva.universe.MarketVertexEntity dealerMarketVertex = marketVertex.dealer();

		double value = -1. * dealerMarketVertex.seniorFundingReplicator() * _dealerSeniorNumeraireHoldings;

		double dealerSubordinateFundingMarketVertex = dealerMarketVertex.subordinateFundingReplicator();

		if (org.drip.quant.common.NumberUtil.IsValid (dealerSubordinateFundingMarketVertex))
		{
			value -= dealerSubordinateFundingMarketVertex * _dealerSubordinateNumeraireHoldings;
		}

		return value;
	}

	/**
	 * Compute the Market Value of the Dealer Position Post-Default
	 * 
	 * @param marketVertex The Market Vertex
	 * 
	 * @return The Market Value of the Dealer Position Post-Default
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double dealerPostDefaultPositionValue (
		final org.drip.xva.universe.MarketVertex marketVertex)
		throws java.lang.Exception
	{
		if (null == marketVertex)
		{
			throw new java.lang.Exception
				("ReplicationPortfolioVertex::dealerPostDefaultPositionValue => Invalid Inputs");
		}

		org.drip.xva.universe.MarketVertexEntity dealerMarketVertex = marketVertex.dealer();

		double value = dealerMarketVertex.seniorFundingReplicator() * _dealerSeniorNumeraireHoldings *
			dealerMarketVertex.seniorRecoveryRate();

		double dealerSubordinateFundingMarketVertex = dealerMarketVertex.subordinateFundingReplicator();

		if (org.drip.quant.common.NumberUtil.IsValid (dealerSubordinateFundingMarketVertex))
		{
			value -= dealerSubordinateFundingMarketVertex * _dealerSubordinateNumeraireHoldings *
				dealerMarketVertex.subordinateRecoveryRate();
		}

		return value;
	}
}
