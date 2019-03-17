
package org.drip.xva.vertex;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>BurgardKjaerBuilder</i> contains the Builders that construct the Burgard Kjaer Vertex using a Variant
 * of the Generalized Burgard Kjaer (2013) Scheme. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva">XVA</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/vertex">Vertex</a></li>
 *  </ul>
 * <br><br>
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
		final org.drip.exposure.universe.MarketVertex marketVertex,
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

		org.drip.exposure.universe.MarketVertexEntity dealerMarketVertex = marketVertex.dealer();

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
				burgardKjaerVertexExposure.variationMarginPosting();

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
		final org.drip.exposure.universe.MarketEdge marketEdge,
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

		org.drip.exposure.universe.MarketVertex marketVertexStart = marketEdge.start();

		org.drip.exposure.universe.MarketVertex marketVertexFinish = marketEdge.finish();

		org.drip.exposure.universe.MarketVertexEntity dealerMarketVertexFinish = marketVertexFinish.dealer();

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
				burgardKjaerVertexExposure.variationMarginPosting();

		try
		{
			return new org.drip.xva.vertex.BurgardKjaer (
				anchorDate,
				collateralGroupVertexExposure.variationMarginEstimate(),
				collateralGroupVertexExposure.tradePayment(),
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
		final org.drip.exposure.universe.MarketEdge marketEdge,
		final org.drip.xva.definition.CloseOut closeOutScheme)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (exposure) ||
			!org.drip.numerical.common.NumberUtil.IsValid (realizedCashFlow) ||
			!org.drip.numerical.common.NumberUtil.IsValid (collateralBalance) ||
			!org.drip.numerical.common.NumberUtil.IsValid (hedgeError))
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
		final org.drip.exposure.universe.MarketEdge marketEdge,
		final org.drip.xva.definition.CloseOut closeOutScheme)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (exposure) ||
			!org.drip.numerical.common.NumberUtil.IsValid (realizedCashFlow) ||
			!org.drip.numerical.common.NumberUtil.IsValid (collateralBalance))
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
		final org.drip.exposure.universe.MarketEdge marketEdge,
		final org.drip.xva.definition.CloseOut closeOutScheme)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (exposure) ||
			!org.drip.numerical.common.NumberUtil.IsValid (realizedCashFlow))
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
		final org.drip.exposure.universe.MarketEdge marketEdge,
		final org.drip.xva.definition.CloseOut closeOutScheme)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (exposure) ||
			!org.drip.numerical.common.NumberUtil.IsValid (realizedCashFlow))
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
		final org.drip.exposure.universe.MarketEdge marketEdge)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (exposure) ||
			!org.drip.numerical.common.NumberUtil.IsValid (realizedCashFlow) ||
			!org.drip.numerical.common.NumberUtil.IsValid (collateralBalance))
		{
			return null;
		}

		org.drip.exposure.universe.MarketVertex marketVertexFinish = marketEdge.finish();

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
