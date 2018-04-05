
package org.drip.xva.vertex;

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
 * BurgardKjaerBuilder contains the Builders that construct the Burgard Kjaer Vertex using a Variant of the
 *  Generalized Burgard Kjaer (2013) Scheme. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
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

public class BurgardKjaerBuilder
{

	/**
	 * Construct the Initial Dynamic Dealer Portfolio
	 * 
	 * @param anchorDate The Anchor Date
	 * @param forward The Unrealized Forward Exposure
	 * @param marketVertex The Market Vertex
	 * @param closeOutScheme The Generic Close Out Instance
	 * 
	 * @return The Burgard Kjaer Dealer Portfolio Vertex
	 */

	public static final org.drip.xva.vertex.BurgardKjaer Initial (
		final org.drip.analytics.date.JulianDate anchorDate,
		final double forward,
		final org.drip.xva.universe.MarketVertex marketVertex,
		final org.drip.xva.definition.CloseOut closeOutScheme)
	{
		if (null == marketVertex)
		{
			return null;
		}

		org.drip.xva.hypothecation.CollateralGroupVertexCloseOut collateralGroupVertexCloseOut =
			org.drip.xva.hypothecation.CollateralGroupVertexCloseOut.Standard (
				closeOutScheme,
				forward,
				0.
			);

		org.drip.xva.vertex.BurgardKjaerExposure burgardKjaerVertexExposure =
			org.drip.xva.vertex.BurgardKjaerExposure.Initial (
				forward,
				collateralGroupVertexCloseOut
			);

		if (null == burgardKjaerVertexExposure)
		{
			return null;
		}

		double fundingExposure = burgardKjaerVertexExposure.funding();

		double dealerDefaultCloseOut = collateralGroupVertexCloseOut.dealer();

		org.drip.xva.universe.MarketVertexEntity dealerMarketVertex = marketVertex.dealer();

		double dealerSubordinateFundingMarketVertex = dealerMarketVertex.subordinateFundingReplicator();

		double dealerSurvival = dealerMarketVertex.survivalProbability();

		double dealerSeniorRecoveryRate = dealerMarketVertex.seniorRecoveryRate();

		double dealerSubordinateRecoveryRate = dealerMarketVertex.subordinateRecoveryRate();

		double clientSurvival = marketVertex.client().survivalProbability();

		double incrementalDealerSurvival = dealerSurvival - 1.;

		double adjustedExposure =
			forward + dealerSurvival * (clientSurvival - 1.) * burgardKjaerVertexExposure.credit() +
			clientSurvival * incrementalDealerSurvival * burgardKjaerVertexExposure.debt() +
			clientSurvival * incrementalDealerSurvival * fundingExposure -
			dealerSurvival * clientSurvival * marketVertex.csaSpread() *
				burgardKjaerVertexExposure.collateralBalance();

		try
		{
			return new org.drip.xva.vertex.BurgardKjaer (
				anchorDate,
				forward,
				0.,
				burgardKjaerVertexExposure,
				collateralGroupVertexCloseOut,
				new org.drip.xva.derivative.ReplicationPortfolioVertexDealer (
					(fundingExposure + dealerSubordinateRecoveryRate * adjustedExposure - dealerDefaultCloseOut) /
						(dealerSeniorRecoveryRate - dealerSubordinateRecoveryRate) / dealerMarketVertex.seniorFundingReplicator(),
					(fundingExposure + dealerSeniorRecoveryRate * adjustedExposure - dealerDefaultCloseOut) /
						(dealerSubordinateRecoveryRate - dealerSeniorRecoveryRate) / dealerSubordinateFundingMarketVertex
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Path-wise Dynamic Dealer Portfolio
	 * 
	 * @param anchorDate The Anchor Date
	 * @param collateralGroupVertexExposure The Raw Collateral Group Vertex Exposure
	 * @param marketEdge The Market Edge
	 * @param collateralGroupVertexCloseOut The Collateral Group Vertex Close Out
	 * @param burgardKjaerVertexExposure The Collateral Group Vertex Exposure Decomposition
	 * 
	 * @return The Burgard Kjaer Dealer Portfolio Vertex
	 */

	public static final org.drip.xva.vertex.BurgardKjaer DealerPortfolioBuilder (
		final org.drip.analytics.date.JulianDate anchorDate,
		final org.drip.xva.hypothecation.CollateralGroupVertexExposure collateralGroupVertexExposure,
		final org.drip.xva.universe.MarketEdge marketEdge,
		final org.drip.xva.hypothecation.CollateralGroupVertexCloseOut collateralGroupVertexCloseOut,
		final org.drip.xva.vertex.BurgardKjaerExposure burgardKjaerVertexExposure)
	{
		if (null == collateralGroupVertexExposure ||
			null == marketEdge ||
			null == collateralGroupVertexCloseOut ||
			null == burgardKjaerVertexExposure)
		{
			return null;
		}

		double fundingExposure = burgardKjaerVertexExposure.funding();

		double dealerDefaultCloseOut = collateralGroupVertexCloseOut.dealer();

		org.drip.xva.universe.MarketVertex marketVertexStart = marketEdge.start();

		org.drip.xva.universe.MarketVertex marketVertexFinish = marketEdge.finish();

		org.drip.xva.universe.MarketVertexEntity dealerMarketVertexFinish = marketVertexFinish.dealer();

		double dealerSubordinateFundingMarketVertexFinish = dealerMarketVertexFinish.subordinateFundingReplicator();

		double dealerSurvivalFinish = dealerMarketVertexFinish.survivalProbability();

		double dealerSeniorRecoveryRateFinish = dealerMarketVertexFinish.seniorRecoveryRate();

		double dealerSubordinateRecoveryRateFinish = dealerMarketVertexFinish.subordinateRecoveryRate();

		double clientSurvivalFinish = marketVertexFinish.client().survivalProbability();

		double incrementalDealerSurvival = dealerSurvivalFinish -
			(null == marketVertexStart ? 1. : marketVertexStart.dealer().survivalProbability());

		double adjustedExposure =
			collateralGroupVertexExposure.uncollateralized() +
			dealerSurvivalFinish *
				(clientSurvivalFinish -
					(null == marketVertexStart ? 1. : marketVertexStart.client().survivalProbability())) *
				burgardKjaerVertexExposure.credit() +
			clientSurvivalFinish * incrementalDealerSurvival * burgardKjaerVertexExposure.debt() +
			clientSurvivalFinish * incrementalDealerSurvival * fundingExposure -
			dealerSurvivalFinish * clientSurvivalFinish * marketVertexFinish.csaSpread() *
				burgardKjaerVertexExposure.collateralBalance();

		try
		{
			return new org.drip.xva.vertex.BurgardKjaer (
				anchorDate,
				collateralGroupVertexExposure.forward(),
				collateralGroupVertexExposure.accrued(),
				burgardKjaerVertexExposure,
				collateralGroupVertexCloseOut,
				new org.drip.xva.derivative.ReplicationPortfolioVertexDealer (
					(fundingExposure + dealerSubordinateRecoveryRateFinish * adjustedExposure -
						dealerDefaultCloseOut) / (dealerSeniorRecoveryRateFinish -
						dealerSubordinateRecoveryRateFinish) /
						dealerMarketVertexFinish.seniorFundingReplicator(),
					(fundingExposure + dealerSeniorRecoveryRateFinish * adjustedExposure - dealerDefaultCloseOut)
						/ (dealerSubordinateRecoveryRateFinish - dealerSeniorRecoveryRateFinish) /
						dealerSubordinateFundingMarketVertexFinish
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of BurgardKjaerVertex using the specified Hedge Error with Two Dealer
	 *  Bonds
	 * 
	 * @param anchorDate The Vertex Date Anchor
	 * @param exposure The Exposure at the Path Vertex Time Node
	 * @param realizedCashFlow The Default Window Realized Cash-flow at the Path Vertex Time Node
	 * @param collateralBalance The Collateral Balance at the Path Vertex Time Node
	 * @param hedgeError The Hedge Error
	 * @param marketEdge The Market Edge
	 * @param closeOutScheme The Generic Close-Out Evaluator Instance
	 * 
	 * @return The Standard Instance of BurgardKjaerVertex using the specified Hedge Error with Two Dealer
	 *  Bonds
	 */

	public static final org.drip.xva.vertex.BurgardKjaer HedgeErrorDualBond (
		final org.drip.analytics.date.JulianDate anchorDate,
		final double exposure,
		final double realizedCashFlow,
		final double collateralBalance,
		final double hedgeError,
		final org.drip.xva.universe.MarketEdge marketEdge,
		final org.drip.xva.definition.CloseOut closeOutScheme)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (exposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (realizedCashFlow) ||
			!org.drip.quant.common.NumberUtil.IsValid (collateralBalance) ||
			!org.drip.quant.common.NumberUtil.IsValid (hedgeError))
		{
			return null;
		}

		double uncollateralizedExposure = exposure + realizedCashFlow;
		double collateralizedExposure = uncollateralizedExposure - collateralBalance;

		org.drip.xva.hypothecation.CollateralGroupVertexCloseOut collateralGroupVertexCloseOut =
			org.drip.xva.hypothecation.CollateralGroupVertexCloseOut.Standard (
				closeOutScheme,
				uncollateralizedExposure,
				collateralBalance
			);

		if (null == collateralGroupVertexCloseOut)
		{
			return null;
		}

		try
		{
			return DealerPortfolioBuilder (
				anchorDate,
				new org.drip.xva.hypothecation.CollateralGroupVertexExposure (
					exposure,
					realizedCashFlow
				),
				marketEdge,
				collateralGroupVertexCloseOut,
				new org.drip.xva.vertex.BurgardKjaerExposure (
					collateralizedExposure - collateralGroupVertexCloseOut.client(),
					collateralizedExposure - collateralGroupVertexCloseOut.dealer(),
					hedgeError,
					collateralBalance
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of BurgardKjaerVertex using semi-replication with no Short-fall at own
	 *  Default using Two Bonds
	 * 
	 * @param anchorDate The Vertex Date Anchor
	 * @param exposure The Exposure at the Path Vertex Time Node
	 * @param realizedCashFlow The Default Window Realized Cash-flow at the Path Vertex Time Node
	 * @param collateralBalance The Collateral Balance at the Path Vertex Time Node
	 * @param marketEdge The Market Edge
	 * @param closeOutScheme The Generic Close-Out Evaluator Instance
	 * 
	 * @return The Standard Instance of BurgardKjaerVertex using semi-replication with no Short-fall at own
	 *  Default using Two Bonds
	 */

	public static final org.drip.xva.vertex.BurgardKjaer SemiReplicationDualBond (
		final org.drip.analytics.date.JulianDate anchorDate,
		final double exposure,
		final double realizedCashFlow,
		final double collateralBalance,
		final org.drip.xva.universe.MarketEdge marketEdge,
		final org.drip.xva.definition.CloseOut closeOutScheme)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (exposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (realizedCashFlow) ||
			!org.drip.quant.common.NumberUtil.IsValid (collateralBalance))
		{
			return null;
		}

		double uncollateralizedExposure = exposure + realizedCashFlow;
		double collateralizedExposure = uncollateralizedExposure - collateralBalance;

		try
		{
			return DealerPortfolioBuilder (
				anchorDate,
				new org.drip.xva.hypothecation.CollateralGroupVertexExposure (
					exposure,
					realizedCashFlow
				),
				marketEdge,
				org.drip.xva.hypothecation.CollateralGroupVertexCloseOut.Standard (
					closeOutScheme,
					uncollateralizedExposure,
					collateralBalance
				),
				new org.drip.xva.vertex.BurgardKjaerExposure (
					0. < collateralizedExposure ? collateralizedExposure : 0.,
					0. > collateralizedExposure ? collateralizedExposure : 0.,
					0. < collateralizedExposure ? collateralizedExposure : 0.,
					collateralBalance
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of BurgardKjaerVertex using a Fully Collateralized Strategy, i.e., also
	 * 	referred to as the 2 Way Gold Plated CSA
	 * 
	 * @param anchorDate The Vertex Date Anchor
	 * @param exposure The Exposure at the Path Vertex Time Node
	 * @param realizedCashFlow The Default Window Realized Cash-flow at the Path Vertex Time Node
	 * @param marketEdge The Market Edge
	 * @param closeOutScheme The Generic Close-Out Evaluator Instance
	 * 
	 * @return The Standard Instance of BurgardKjaerVertex using using a Fully Collateralized Strategy
	 */

	public static final org.drip.xva.vertex.BurgardKjaer GoldPlatedTwoWayCSA (
		final org.drip.analytics.date.JulianDate anchorDate,
		final double exposure,
		final double realizedCashFlow,
		final org.drip.xva.universe.MarketEdge marketEdge,
		final org.drip.xva.definition.CloseOut closeOutScheme)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (exposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (realizedCashFlow))
		{
			return null;
		}

		double uncollateralizedExposure = exposure + realizedCashFlow;

		try
		{
			return DealerPortfolioBuilder (
				anchorDate,
				new org.drip.xva.hypothecation.CollateralGroupVertexExposure (
					exposure,
					realizedCashFlow
				),
				marketEdge,
				org.drip.xva.hypothecation.CollateralGroupVertexCloseOut.Standard (
					closeOutScheme,
					uncollateralizedExposure,
					uncollateralizedExposure
				),
				new org.drip.xva.vertex.BurgardKjaerExposure (
					0.,
					0.,
					0.,
					uncollateralizedExposure
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of BurgardKjaerVertex using One Way CSA
	 * 
	 * @param anchorDate The Vertex Date Anchor
	 * @param exposure The Exposure at the Path Vertex Time Node
	 * @param realizedCashFlow The Default Window Realized Cash-flow at the Path Vertex Time Node
	 * @param marketEdge The Market Edge
	 * @param closeOutScheme The Generic Close-Out Evaluator Instance
	 * 
	 * @return The Standard Instance of BurgardKjaerVertex using One Way CSA
	 */

	public static final org.drip.xva.vertex.BurgardKjaer OneWayCSA (
		final org.drip.analytics.date.JulianDate anchorDate,
		final double exposure,
		final double realizedCashFlow,
		final org.drip.xva.universe.MarketEdge marketEdge,
		final org.drip.xva.definition.CloseOut closeOutScheme)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (exposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (realizedCashFlow))
		{
			return null;
		}

		double uncollateralizedExposure = exposure + realizedCashFlow;
		double collateralBalance = 0. > uncollateralizedExposure ? uncollateralizedExposure : 0.;

		try
		{
			return DealerPortfolioBuilder (
				anchorDate,
				new org.drip.xva.hypothecation.CollateralGroupVertexExposure (
					exposure,
					realizedCashFlow
				),
				marketEdge,
				org.drip.xva.hypothecation.CollateralGroupVertexCloseOut.Standard (
					closeOutScheme,
					uncollateralizedExposure,
					collateralBalance
				),
				new org.drip.xva.vertex.BurgardKjaerExposure (
					0. < uncollateralizedExposure ? uncollateralizedExposure : 0.,
					0.,
					0. < uncollateralizedExposure ? uncollateralizedExposure : 0.,
					collateralBalance
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of BurgardKjaerVertex using the "Set Off" Legal Agreement Scheme
	 * 
	 * @param anchorDate The Vertex Date Anchor
	 * @param exposure The Exposure at the Path Vertex Time Node
	 * @param realizedCashFlow The Default Window Realized Cash-flow at the Path Vertex Time Node
	 * @param collateralBalance The Collateral Balance at the Path Vertex Time Node
	 * @param marketEdge The Market Edge
	 * 
	 * @return The Standard Instance of BurgardKjaerVertex using the "Set Off" Legal Agreement Scheme
	 */

	public static final org.drip.xva.vertex.BurgardKjaer SetOff (
		final org.drip.analytics.date.JulianDate anchorDate,
		final double exposure,
		final double realizedCashFlow,
		final double collateralBalance,
		final org.drip.xva.universe.MarketEdge marketEdge)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (exposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (realizedCashFlow) ||
			!org.drip.quant.common.NumberUtil.IsValid (collateralBalance))
		{
			return null;
		}

		org.drip.xva.universe.MarketVertex marketVertexFinish = marketEdge.finish();

		double dealerSeniorRecoveryRateFinish = marketVertexFinish.dealer().seniorRecoveryRate();

		double clientRecoveryFinish = marketVertexFinish.client().seniorRecoveryRate();

		double collateralizedExposure = exposure + realizedCashFlow - collateralBalance;

		try
		{
			return DealerPortfolioBuilder (
				anchorDate,
				new org.drip.xva.hypothecation.CollateralGroupVertexExposure (
					exposure,
					realizedCashFlow
				),
				marketEdge,
				new org.drip.xva.hypothecation.CollateralGroupVertexCloseOut (
					collateralizedExposure * dealerSeniorRecoveryRateFinish,
					collateralizedExposure * clientRecoveryFinish
				),
				new org.drip.xva.vertex.BurgardKjaerExposure (
					collateralizedExposure * (1. - clientRecoveryFinish),
					collateralizedExposure * (1. - dealerSeniorRecoveryRateFinish),
					0.,
					collateralBalance
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
