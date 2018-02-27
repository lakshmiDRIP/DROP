
package org.drip.xva.hypothecation;

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
 * BurgardKjaerVertexBuilder contains the Builders that construct the Burgard Kjaer Vertex using a Variant of
 *  the Generalized Burgard Kjaer (2013) Scheme. The References are:
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

public class BurgardKjaerVertexBuilder
{

	/**
	 * Construct the Initial Dynamic Bank Portfolio
	 * 
	 * @param anchorDate The Anchor Date
	 * @param forward The Unrealized Forward Exposure
	 * @param marketVertex The Market Vertex
	 * @param closeOutScheme The Generic Close Out Instance
	 * 
	 * @return The Burgard Kjaer Bank Portfolio Vertex
	 */

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex Initial (
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

		org.drip.xva.hypothecation.BurgardKjaerVertexExposure burgardKjaerVertexExposure =
			org.drip.xva.hypothecation.BurgardKjaerVertexExposure.Initial (
				forward,
				collateralGroupVertexCloseOut
			);

		if (null == burgardKjaerVertexExposure)
		{
			return null;
		}

		double fundingExposure = burgardKjaerVertexExposure.funding();

		double bankDefaultCloseOut = collateralGroupVertexCloseOut.bank();

		org.drip.xva.universe.MarketVertexEntity bankMarketVertex = marketVertex.bank();

		double bankSubordinateFundingMarketVertex = bankMarketVertex.subordinateFundingReplicator();

		double bankSurvival = bankMarketVertex.survivalProbability();

		double bankSeniorRecoveryRate = bankMarketVertex.seniorRecoveryRate();

		double bankSubordinateRecoveryRate = bankMarketVertex.subordinateRecoveryRate();

		double counterPartySurvival = marketVertex.counterParty().survivalProbability();

		double incrementalBankSurvival = bankSurvival - 1.;

		double adjustedExposure =
			forward + bankSurvival * (counterPartySurvival - 1.) * burgardKjaerVertexExposure.credit() +
			counterPartySurvival * incrementalBankSurvival * burgardKjaerVertexExposure.debt() +
			counterPartySurvival * incrementalBankSurvival * fundingExposure -
			bankSurvival * counterPartySurvival * marketVertex.csaSpread() *
				burgardKjaerVertexExposure.collateralBalance();

		try
		{
			return new org.drip.xva.hypothecation.BurgardKjaerVertex (
				anchorDate,
				forward,
				0.,
				burgardKjaerVertexExposure,
				collateralGroupVertexCloseOut,
				new org.drip.xva.derivative.ReplicationPortfolioVertexBank (
					(fundingExposure + bankSubordinateRecoveryRate * adjustedExposure - bankDefaultCloseOut) /
						(bankSeniorRecoveryRate - bankSubordinateRecoveryRate) / bankMarketVertex.seniorFundingReplicator(),
					(fundingExposure + bankSeniorRecoveryRate * adjustedExposure - bankDefaultCloseOut) /
						(bankSubordinateRecoveryRate - bankSeniorRecoveryRate) / bankSubordinateFundingMarketVertex
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
	 * Construct a Path-wise Dynamic Bank Portfolio
	 * 
	 * @param anchorDate The Anchor Date
	 * @param collateralGroupVertexExposure The Raw Collateral Group Vertex Exposure
	 * @param marketEdge The Market Edge
	 * @param collateralGroupVertexCloseOut The Collateral Group Vertex Close Out
	 * @param burgardKjaerVertexExposure The Collateral Group Vertex Exposure Decomposition
	 * 
	 * @return The Burgard Kjaer Bank Portfolio Vertex
	 */

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex BankPortfolioBuilder (
		final org.drip.analytics.date.JulianDate anchorDate,
		final org.drip.xva.hypothecation.CollateralGroupVertexExposure collateralGroupVertexExposure,
		final org.drip.xva.universe.MarketEdge marketEdge,
		final org.drip.xva.hypothecation.CollateralGroupVertexCloseOut collateralGroupVertexCloseOut,
		final org.drip.xva.hypothecation.BurgardKjaerVertexExposure burgardKjaerVertexExposure)
	{
		if (null == collateralGroupVertexExposure ||
			null == marketEdge ||
			null == collateralGroupVertexCloseOut ||
			null == burgardKjaerVertexExposure)
		{
			return null;
		}

		double fundingExposure = burgardKjaerVertexExposure.funding();

		double bankDefaultCloseOut = collateralGroupVertexCloseOut.bank();

		org.drip.xva.universe.MarketVertex marketVertexStart = marketEdge.start();

		org.drip.xva.universe.MarketVertex marketVertexFinish = marketEdge.finish();

		org.drip.xva.universe.MarketVertexEntity bankMarketVertexFinish = marketVertexFinish.bank();

		double bankSubordinateFundingMarketVertexFinish = bankMarketVertexFinish.subordinateFundingReplicator();

		double bankSurvivalFinish = bankMarketVertexFinish.survivalProbability();

		double bankSeniorRecoveryRateFinish = bankMarketVertexFinish.seniorRecoveryRate();

		double bankSubordinateRecoveryRateFinish = bankMarketVertexFinish.subordinateRecoveryRate();

		double counterPartySurvivalFinish = marketVertexFinish.counterParty().survivalProbability();

		double incrementalBankSurvival = bankSurvivalFinish -
			(null == marketVertexStart ? 1. : marketVertexStart.bank().survivalProbability());

		double adjustedExposure =
			collateralGroupVertexExposure.uncollateralized() +
			bankSurvivalFinish *
				(counterPartySurvivalFinish -
					(null == marketVertexStart ? 1. : marketVertexStart.counterParty().survivalProbability())) *
				burgardKjaerVertexExposure.credit() +
			counterPartySurvivalFinish * incrementalBankSurvival * burgardKjaerVertexExposure.debt() +
			counterPartySurvivalFinish * incrementalBankSurvival * fundingExposure -
			bankSurvivalFinish * counterPartySurvivalFinish * marketVertexFinish.csaSpread() *
				burgardKjaerVertexExposure.collateralBalance();

		try
		{
			return new org.drip.xva.hypothecation.BurgardKjaerVertex (
				anchorDate,
				collateralGroupVertexExposure.forward(),
				collateralGroupVertexExposure.accrued(),
				burgardKjaerVertexExposure,
				collateralGroupVertexCloseOut,
				new org.drip.xva.derivative.ReplicationPortfolioVertexBank (
					(fundingExposure + bankSubordinateRecoveryRateFinish * adjustedExposure -
						bankDefaultCloseOut) / (bankSeniorRecoveryRateFinish -
						bankSubordinateRecoveryRateFinish) /
						bankMarketVertexFinish.seniorFundingReplicator(),
					(fundingExposure + bankSeniorRecoveryRateFinish * adjustedExposure - bankDefaultCloseOut)
						/ (bankSubordinateRecoveryRateFinish - bankSeniorRecoveryRateFinish) /
						bankSubordinateFundingMarketVertexFinish
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
	 * Construct a Standard Instance of BurgardKjaerVertex using the specified Hedge Error with Two Bank
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
	 * @return The Standard Instance of BurgardKjaerVertex using the specified Hedge Error with Two Bank
	 *  Bonds
	 */

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex HedgeErrorDualBond (
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
			return BankPortfolioBuilder (
				anchorDate,
				new org.drip.xva.hypothecation.CollateralGroupVertexExposure (
					exposure,
					realizedCashFlow
				),
				marketEdge,
				collateralGroupVertexCloseOut,
				new org.drip.xva.hypothecation.BurgardKjaerVertexExposure (
					collateralizedExposure - collateralGroupVertexCloseOut.counterParty(),
					collateralizedExposure - collateralGroupVertexCloseOut.bank(),
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

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex SemiReplicationDualBond (
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
			return BankPortfolioBuilder (
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
				new org.drip.xva.hypothecation.BurgardKjaerVertexExposure (
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

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex GoldPlatedTwoWayCSA (
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
			return BankPortfolioBuilder (
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
				new org.drip.xva.hypothecation.BurgardKjaerVertexExposure (
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

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex OneWayCSA (
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
			return BankPortfolioBuilder (
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
				new org.drip.xva.hypothecation.BurgardKjaerVertexExposure (
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

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex SetOff (
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

		double bankSeniorRecoveryRateFinish = marketVertexFinish.bank().seniorRecoveryRate();

		double dblCounterPartyRecoveryFinish = marketVertexFinish.counterParty().seniorRecoveryRate();

		double collateralizedExposure = exposure + realizedCashFlow - collateralBalance;

		try
		{
			return BankPortfolioBuilder (
				anchorDate,
				new org.drip.xva.hypothecation.CollateralGroupVertexExposure (
					exposure,
					realizedCashFlow
				),
				marketEdge,
				new org.drip.xva.hypothecation.CollateralGroupVertexCloseOut (
					collateralizedExposure * bankSeniorRecoveryRateFinish,
					collateralizedExposure * dblCounterPartyRecoveryFinish
				),
				new org.drip.xva.hypothecation.BurgardKjaerVertexExposure (
					collateralizedExposure * (1. - dblCounterPartyRecoveryFinish),
					collateralizedExposure * (1. - bankSeniorRecoveryRateFinish),
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
