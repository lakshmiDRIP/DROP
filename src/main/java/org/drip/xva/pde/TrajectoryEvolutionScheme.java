
package org.drip.xva.pde;

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
 * <i>TrajectoryEvolutionScheme</i> holds the Evolution Edges of a Trajectory evolved in a Dynamically
 * Adaptive Manner, as laid out in Burgard and Kjaer (2014). The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): <i>Modeling,
 *  			Pricing, and Hedging Counter-party Credit Exposure - A Technical Guide</i> <b>Springer
 *  			Finance</b> New York
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading
 *  			Strategies in the Presence of Counter-party Credit Risk for the Fixed Income Market</i>
 *  			<b>World Scientific Publishing</b> Singapore
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md">Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/pde/README.md">Burgard Kjaer PDE Evolution Scheme</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class TrajectoryEvolutionScheme
{
	private org.drip.xva.definition.PDEEvolutionControl _pdeEvolutionControl = null;
	private org.drip.exposure.evolver.PrimarySecurityDynamicsContainer _tradeablesContainer = null;

	/**
	 * TrajectoryEvolutionScheme Constructor
	 * 
	 * @param tradeablesContainer The Universe of Tradeables
	 * @param pdeEvolutionControl The XVA PDE Control Settings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TrajectoryEvolutionScheme (
		final org.drip.exposure.evolver.PrimarySecurityDynamicsContainer tradeablesContainer,
		final org.drip.xva.definition.PDEEvolutionControl pdeEvolutionControl)
		throws java.lang.Exception
	{
		if (null == (_tradeablesContainer = tradeablesContainer) ||
			null == (_pdeEvolutionControl = pdeEvolutionControl))
		{
			throw new java.lang.Exception ("TrajectoryEvolutionScheme Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Universe of Tradeables
	 * 
	 * @return The Universe of Tradeables
	 */

	public org.drip.exposure.evolver.PrimarySecurityDynamicsContainer tradeablesContainer()
	{
		return _tradeablesContainer;
	}

	/**
	 * Retrieve the XVA PDE Control Settings
	 * 
	 * @return The XVA PDE Control Settings
	 */

	public org.drip.xva.definition.PDEEvolutionControl pdeEvolutionControl()
	{
		return _pdeEvolutionControl;
	}

	/**
	 * Re-balance the Cash Account and generate the Derivative Value Update
	 * 
	 * @param initialTrajectoryVertex The Starting Evolution Trajectory Vertex
	 * @param marketEdge Market Edge Instance
	 * 
	 * @return The CashAccountRebalancer Instance
	 */

	public org.drip.xva.derivative.CashAccountRebalancer rebalanceCash (
		final org.drip.xva.derivative.EvolutionTrajectoryVertex initialTrajectoryVertex,
		final org.drip.exposure.universe.MarketEdge marketEdge)
	{
		if (null == initialTrajectoryVertex ||
			null == marketEdge)
		{
			return null;
		}

		org.drip.xva.derivative.ReplicationPortfolioVertex initialReplicationPortfolioVertex =
			initialTrajectoryVertex.replicationPortfolioVertex();

		double initialPortfolioHoldings = initialReplicationPortfolioVertex.positionHoldings();

		double initialDealerSeniorNumeraireHoldings =
			initialReplicationPortfolioVertex.dealerSeniorNumeraireHoldings();

		double initialClientNumeraireHoldings = initialReplicationPortfolioVertex.clientNumeraireHoldings();

		double initialDealerSubordinateNumeraireHoldings =
			initialReplicationPortfolioVertex.dealerSubordinateNumeraireHoldings();

		org.drip.exposure.universe.MarketVertex initialMarketVertex = marketEdge.start();

		org.drip.exposure.universe.MarketVertex finalMarketVertex = marketEdge.finish();

		org.drip.exposure.universe.MarketVertexEntity emvDealerStart = initialMarketVertex.dealer();

		org.drip.exposure.universe.MarketVertexEntity dealerMarketVertex = finalMarketVertex.dealer();

		org.drip.exposure.universe.MarketVertexEntity clientMarketVertex = finalMarketVertex.client();

		double finalDealerSeniorFundingNumeraire = dealerMarketVertex.seniorFundingReplicator();

		double finalClientNumeraire = clientMarketVertex.seniorFundingReplicator();

		double initialDealerSubordinateFundingNumeraire = emvDealerStart.subordinateFundingReplicator();

		double finalDealerSubordinateFundingNumeraire = dealerMarketVertex.subordinateFundingReplicator();

		double timeIncrement = marketEdge.vertexIncrement() / 365.25;

		org.drip.exposure.evolver.PrimarySecurity clientFundingTradeable = _tradeablesContainer.clientFunding();

		double clientCashAccumulation = initialClientNumeraireHoldings *
			clientFundingTradeable.cashAccumulationRate() * finalClientNumeraire * timeIncrement;

		double clientHoldingsValueChange = initialClientNumeraireHoldings * (finalClientNumeraire -
			initialMarketVertex.client().seniorFundingReplicator());

		double cashAccountBalance = -1. * initialTrajectoryVertex.positionGreekVertex().derivativeXVAValue()
			- initialDealerSeniorNumeraireHoldings * finalDealerSeniorFundingNumeraire;

		if (org.drip.numerical.common.NumberUtil.IsValid (finalDealerSubordinateFundingNumeraire))
		{
			cashAccountBalance -= initialDealerSubordinateNumeraireHoldings *
				finalDealerSubordinateFundingNumeraire;
		}

		org.drip.exposure.evolver.PrimarySecurity csaTradeable = _tradeablesContainer.csa();

		org.drip.exposure.evolver.PrimarySecurity dealerSeniorFundingTradeable =
			_tradeablesContainer.dealerSeniorFunding();

		double dealerCashAccumulation = cashAccountBalance * (cashAccountBalance > 0. ?
			csaTradeable.cashAccumulationRate() : dealerSeniorFundingTradeable.cashAccumulationRate()) *
				timeIncrement;

		try
		{
			double finalPortfolioValue = finalMarketVertex.latentStateValue
				(_tradeablesContainer.assetList().get (0).label());

			double portfolioCashChange = initialPortfolioHoldings *
				_tradeablesContainer.assetList().get (0).cashAccumulationRate() * finalPortfolioValue *
					timeIncrement;

			double derivativeXVAValueChange = -1. * (initialPortfolioHoldings * (finalPortfolioValue -
				initialMarketVertex.latentStateValue (_tradeablesContainer.assetList().get (0).label())) +
					initialDealerSeniorNumeraireHoldings * (finalDealerSeniorFundingNumeraire -
						emvDealerStart.seniorFundingReplicator()) + clientHoldingsValueChange +
							(portfolioCashChange + clientCashAccumulation + dealerCashAccumulation) *
								timeIncrement);

			if (org.drip.numerical.common.NumberUtil.IsValid (initialDealerSubordinateFundingNumeraire) &&
				org.drip.numerical.common.NumberUtil.IsValid (finalDealerSubordinateFundingNumeraire))
			{
				derivativeXVAValueChange += initialDealerSubordinateNumeraireHoldings *
					(finalDealerSubordinateFundingNumeraire - initialDealerSubordinateFundingNumeraire);
			}

			return new org.drip.xva.derivative.CashAccountRebalancer (
				new org.drip.xva.derivative.CashAccountEdge (
					portfolioCashChange,
					dealerCashAccumulation * timeIncrement,
					clientCashAccumulation * timeIncrement
				),
				derivativeXVAValueChange
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Execute a Single Euler Time Step Walk
	 * 
	 * @param marketEdge Market Edge Instance
	 * @param burgardKjaerOperator The Burgard Kjaer Operator Instance
	 * @param initialTrajectoryVertex The Starting ETV Instance
	 * @param collateral The Applicable Collateral
	 * 
	 * @return The Evolution Trajectory Edge
	 */

	public org.drip.xva.derivative.EvolutionTrajectoryEdge eulerWalk (
		final org.drip.exposure.universe.MarketEdge marketEdge,
		final org.drip.xva.pde.BurgardKjaerOperator burgardKjaerOperator,
		final org.drip.xva.derivative.EvolutionTrajectoryVertex initialTrajectoryVertex,
		final double collateral)
	{
		if (null == marketEdge ||
			null == burgardKjaerOperator ||
			null == initialTrajectoryVertex)
		{
			return null;
		}

		org.drip.xva.derivative.PositionGreekVertex initialPositionGreekVertex =
			initialTrajectoryVertex.positionGreekVertex();

		org.drip.xva.pde.BurgardKjaerEdgeRun burgardKjaerEdgeRun = burgardKjaerOperator.edgeRun (
			marketEdge,
			initialTrajectoryVertex,
			collateral
		);

		double initialTime = initialTrajectoryVertex.time();

		double timeIncrement = marketEdge.vertexIncrement() / 365.25;

		if (null == burgardKjaerEdgeRun)
		{
			return null;
		}

		double theta = burgardKjaerEdgeRun.theta();

		double positionValueBump = burgardKjaerEdgeRun.positionValueBump();

		double thetaPositionValueUp = burgardKjaerEdgeRun.thetaPositionValueUp();

		double thetaPositionValueDown = burgardKjaerEdgeRun.thetaPositionValueDown();

		org.drip.exposure.universe.MarketVertex finalMarketVertex = marketEdge.finish();

		org.drip.exposure.universe.MarketVertexEntity dealerMarketVertex = finalMarketVertex.dealer();

		org.drip.exposure.universe.MarketVertexEntity clientMarketVertex = finalMarketVertex.client();

		double derivativeXVAValueDeltaFinish =
			initialPositionGreekVertex.derivativeXVAValueDelta() +
			0.5 * (thetaPositionValueUp - thetaPositionValueDown) * timeIncrement / positionValueBump;

		double clientGainOnDealerDefault = java.lang.Double.NaN;
		double finalGainOnClientDefault = java.lang.Double.NaN;

		double derivativeXVAValueFinish = initialPositionGreekVertex.derivativeXVAValue() - theta *
			timeIncrement;

		try
		{
			org.drip.xva.definition.CloseOut closeOutScheme = new
				org.drip.xva.definition.CloseOutBilateral (
					dealerMarketVertex.seniorRecoveryRate(),
					clientMarketVertex.seniorRecoveryRate()
				);

			clientGainOnDealerDefault = closeOutScheme.dealerDefault (derivativeXVAValueFinish);

			finalGainOnClientDefault = -1. * (derivativeXVAValueFinish - closeOutScheme.clientDefault
				(derivativeXVAValueFinish));
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		double dealerSubordinateFundingNumeraire = dealerMarketVertex.subordinateFundingReplicator();

		double gainOnDealerDefaultFinish = -1. * (derivativeXVAValueFinish - clientGainOnDealerDefault);

		double finalClientHoldings = finalGainOnClientDefault / clientMarketVertex.seniorFundingReplicator();

		org.drip.xva.derivative.CashAccountRebalancer cashAccountRebalancer = rebalanceCash (
			initialTrajectoryVertex,
			marketEdge
		);

		if (null == cashAccountRebalancer)
		{
			return null;
		}

		org.drip.xva.derivative.CashAccountEdge cashAccountEdge = cashAccountRebalancer.cashAccountEdge();

		double dealerSeniorFundingNumeraire = dealerMarketVertex.seniorFundingReplicator();

		org.drip.exposure.evolver.PrimarySecurity csaTradeable = _tradeablesContainer.csa();

		try
		{
			org.drip.xva.derivative.EvolutionTrajectoryVertex finalTrajectoryVertex = new
				org.drip.xva.derivative.EvolutionTrajectoryVertex (
				initialTime + timeIncrement,
				new org.drip.xva.derivative.ReplicationPortfolioVertex (
					-1. * derivativeXVAValueDeltaFinish,
					gainOnDealerDefaultFinish / dealerSeniorFundingNumeraire,
					!org.drip.numerical.common.NumberUtil.IsValid (dealerSubordinateFundingNumeraire) ? 0. :
						gainOnDealerDefaultFinish / dealerSubordinateFundingNumeraire,
					finalClientHoldings,
					initialTrajectoryVertex.replicationPortfolioVertex().cashAccount() +
						cashAccountEdge.accumulation()
				),
				new org.drip.xva.derivative.PositionGreekVertex (
					derivativeXVAValueFinish,
					derivativeXVAValueDeltaFinish,
					initialPositionGreekVertex.derivativeXVAValueGamma() +
						(thetaPositionValueUp + thetaPositionValueDown - 2. * theta) *
						timeIncrement / (positionValueBump * positionValueBump),
					initialPositionGreekVertex.derivativeFairValue() * java.lang.Math.exp (
						-1. * timeIncrement *
						csaTradeable.evolver().evaluator().drift().value (
							new org.drip.measure.realization.JumpDiffusionVertex (
								initialTime - 0.5 * timeIncrement,
								marketEdge.start().csaReplicator(),
								0.,
								false
							)
						)
					)
				),
				gainOnDealerDefaultFinish,
				finalGainOnClientDefault,
				collateral,
				burgardKjaerEdgeRun.derivativeXVAHedgeErrorGrowth()
			);

			return new org.drip.xva.derivative.EvolutionTrajectoryEdge (
				initialTrajectoryVertex,
				finalTrajectoryVertex,
				cashAccountEdge
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Execute a Sequential Array of Euler Time Step Walks
	 * 
	 * @param marketVertexArray Array of Market Vertexes
	 * @param burgardKjaerOperator The Burgard Kjaer Operator Instance
	 * @param initialTrajectoryVertex The Starting EET Instance
	 * @param collateral The Applicable Collateral
	 * 
	 * @return Array of EvolutionTrajectoryEdge Instances
	 */

	public org.drip.xva.derivative.EvolutionTrajectoryEdge[] eulerWalk (
		final org.drip.exposure.universe.MarketVertex[] marketVertexArray,
		final org.drip.xva.pde.BurgardKjaerOperator burgardKjaerOperator,
		final org.drip.xva.derivative.EvolutionTrajectoryVertex initialTrajectoryVertex,
		final double collateral)
	{
		if (null == marketVertexArray)
		{
			return null;
		}

		int vertexCount = marketVertexArray.length;
		org.drip.xva.derivative.EvolutionTrajectoryVertex trajectoryVertex = initialTrajectoryVertex;
		org.drip.xva.derivative.EvolutionTrajectoryEdge[] evolutionTrajectoryEdgeArray = 1 >= vertexCount ?
			null : new org.drip.xva.derivative.EvolutionTrajectoryEdge[vertexCount - 1];

		if (0 == vertexCount)
		{
			return null;
		}

		for (int i = vertexCount - 2; i >= 0; --i)
		{
			try
			{
				if (null == (evolutionTrajectoryEdgeArray[i] = eulerWalk (
					new org.drip.exposure.universe.MarketEdge (
						marketVertexArray[i],
						marketVertexArray[i + 1]
					),
					burgardKjaerOperator,
					trajectoryVertex,
					collateral)))
				{
					return null;
				}
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();
			}

			trajectoryVertex = evolutionTrajectoryEdgeArray[i].vertexFinish();
		}

		return evolutionTrajectoryEdgeArray;
	}
}
