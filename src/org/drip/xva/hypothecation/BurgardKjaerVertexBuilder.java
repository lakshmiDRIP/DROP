
package org.drip.xva.hypothecation;

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

public class BurgardKjaerVertexBuilder {

	/**
	 * Construct the Initial Dynamic Bank Portfolio
	 * 
	 * @param dtAnchor The Anchor Date
	 * @param dblForward The Unrealized Forward Exposure
	 * @param mv The Market Vertex
	 * @param cog The Generic Close Out Instance
	 * 
	 * @return The Burgard Kjaer Bank Portfolio Vertex
	 */

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex Initial (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final double dblForward,
		final org.drip.xva.universe.MarketVertex mv,
		final org.drip.xva.definition.CloseOutGeneral cog)
	{
		if (null == mv) return null;

		org.drip.xva.hypothecation.CollateralGroupVertexCloseOut cgvco =
			org.drip.xva.hypothecation.CollateralGroupVertexCloseOut.Standard (cog, dblForward, 0.);

		org.drip.xva.hypothecation.BurgardKjaerVertexExposure cgvea =
			org.drip.xva.hypothecation.BurgardKjaerVertexExposure.Initial (dblForward, cgvco);

		if (null == cgvea) return null;

		double dblFundingExposure = cgvea.funding();

		double dblBankDefaultCloseOut = cgvco.bank();

		org.drip.xva.universe.EntityMarketVertex emvBank = mv.bank();

		org.drip.xva.universe.NumeraireMarketVertex nmvBankSubordinate =
			emvBank.subordinateFundingNumeraire();

		if (null == nmvBankSubordinate) return null;

		double dblBankSurvival = emvBank.survivalProbability();

		double dblBankSeniorRecovery = emvBank.seniorRecoveryRate();

		double dblBankSubordinateRecovery = emvBank.subordinateRecoveryRate();

		double dblCounterPartySurvival = mv.counterParty().survivalProbability();

		double dblIncrementalBankSurvival = dblBankSurvival - 1.;

		double dblAdjustedExposure = dblForward + dblBankSurvival *
			(dblCounterPartySurvival - 1.) * cgvea.credit() +
			dblCounterPartySurvival * dblIncrementalBankSurvival * cgvea.debt() +
			dblCounterPartySurvival * dblIncrementalBankSurvival * dblFundingExposure -
			dblBankSurvival * dblCounterPartySurvival * mv.collateralSchemeSpread() * cgvea.collateralBalance();

		try {
			return new org.drip.xva.hypothecation.BurgardKjaerVertex (
				dtAnchor,
				dblForward,
				0.,
				cgvea,
				cgvco,
				new org.drip.xva.derivative.ReplicationPortfolioVertexBank (
					(dblFundingExposure + dblBankSubordinateRecovery * dblAdjustedExposure - dblBankDefaultCloseOut) /
						(dblBankSeniorRecovery - dblBankSubordinateRecovery) / emvBank.seniorFundingNumeraire().forward(),
					(dblFundingExposure + dblBankSeniorRecovery * dblAdjustedExposure - dblBankDefaultCloseOut) /
						(dblBankSubordinateRecovery - dblBankSeniorRecovery) / nmvBankSubordinate.forward()
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Path-wise Dynamic Bank Portfolio
	 * 
	 * @param dtAnchor The Anchor Date
	 * @param cgver The Raw Collateral Group Vertex Exposure
	 * @param me The Market Edge
	 * @param cgvco The Collateral Group Vertex Close Out
	 * @param cgvea The Collateral Group Vertex Exposure Decomposition
	 * 
	 * @return The Burgard Kjaer Bank Portfolio Vertex
	 */

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex BankPortfolioBuilder (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final org.drip.xva.hypothecation.CollateralGroupVertexExposure cgver,
		final org.drip.xva.universe.MarketEdge me,
		final org.drip.xva.hypothecation.CollateralGroupVertexCloseOut cgvco,
		final org.drip.xva.hypothecation.BurgardKjaerVertexExposure cgvea)
	{
		if (null == cgver || null == me || null == cgvco || null == cgvea) return null;

		double dblFundingExposure = cgvea.funding();

		double dblBankDefaultCloseOut = cgvco.bank();

		org.drip.xva.universe.MarketVertex mvStart = me.start();

		org.drip.xva.universe.MarketVertex mvFinish = me.finish();

		org.drip.xva.universe.EntityMarketVertex emvBankFinish = mvFinish.bank();

		org.drip.xva.universe.NumeraireMarketVertex nmvBankSubordinateFinish =
			emvBankFinish.subordinateFundingNumeraire();

		if (null == nmvBankSubordinateFinish) return null;

		double dblBankSurvivalFinish = emvBankFinish.survivalProbability();

		double dblBankSeniorRecoveryFinish = emvBankFinish.seniorRecoveryRate();

		double dblBankSubordinateRecoveryFinish = emvBankFinish.subordinateRecoveryRate();

		double dblCounterPartySurvivalFinish = mvFinish.counterParty().survivalProbability();

		double dblIncrementalBankSurvival = dblBankSurvivalFinish - mvStart.bank().survivalProbability();

		double dblAdjustedExposure = cgver.uncollateralized() + dblBankSurvivalFinish *
			(dblCounterPartySurvivalFinish - mvStart.counterParty().survivalProbability()) * cgvea.credit() +
			dblCounterPartySurvivalFinish * dblIncrementalBankSurvival * cgvea.debt() +
			dblCounterPartySurvivalFinish * dblIncrementalBankSurvival * dblFundingExposure -
			dblBankSurvivalFinish * dblCounterPartySurvivalFinish * mvFinish.collateralSchemeSpread() * cgvea.collateralBalance();

		try {
			return new org.drip.xva.hypothecation.BurgardKjaerVertex (
				dtAnchor,
				cgver.forward(),
				cgver.accrued(),
				cgvea,
				cgvco,
				new org.drip.xva.derivative.ReplicationPortfolioVertexBank (
					(dblFundingExposure + dblBankSubordinateRecoveryFinish * dblAdjustedExposure - dblBankDefaultCloseOut) /
						(dblBankSeniorRecoveryFinish - dblBankSubordinateRecoveryFinish) /
							emvBankFinish.seniorFundingNumeraire().forward(),
					(dblFundingExposure + dblBankSeniorRecoveryFinish * dblAdjustedExposure - dblBankDefaultCloseOut) /
						(dblBankSubordinateRecoveryFinish - dblBankSeniorRecoveryFinish) / nmvBankSubordinateFinish.forward()
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of BurgardKjaerVertex using the specified Hedge Error with Two Bank
	 *  Bonds
	 * 
	 * @param dtAnchor The Vertex Date Anchor
	 * @param dblExposure The Exposure at the Path Vertex Time Node
	 * @param dblRealizedCashFlow The Default Window Realized Cash-flow at the Path Vertex Time Node
	 * @param dblCollateralBalance The Collateral Balance at the Path Vertex Time Node
	 * @param dblHedgeError The Hedge Error
	 * @param me The Market Edge
	 * @param cog The Generic Close-Out Evaluator Instance
	 * 
	 * @return The Standard Instance of BurgardKjaerVertex using the specified Hedge Error with Two Bank
	 *  Bonds
	 */

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex HedgeErrorDualBond (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final double dblExposure,
		final double dblRealizedCashFlow,
		final double dblCollateralBalance,
		final double dblHedgeError,
		final org.drip.xva.universe.MarketEdge me,
		final org.drip.xva.definition.CloseOutGeneral cog)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRealizedCashFlow) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblCollateralBalance) ||
					!org.drip.quant.common.NumberUtil.IsValid (dblHedgeError))
			return null;

		double dblUncollateralizedExposure = dblExposure + dblRealizedCashFlow;
		double dblCollateralizedExposure = dblUncollateralizedExposure - dblCollateralBalance;

		org.drip.xva.hypothecation.CollateralGroupVertexCloseOut cgvco =
			org.drip.xva.hypothecation.CollateralGroupVertexCloseOut.Standard (
				cog,
				dblUncollateralizedExposure,
				dblCollateralBalance
			);

		if (null == cgvco) return null;

		try {
			return BankPortfolioBuilder (
				dtAnchor,
				new org.drip.xva.hypothecation.CollateralGroupVertexExposure (
					dblExposure,
					dblRealizedCashFlow
				),
				me,
				cgvco,
				new org.drip.xva.hypothecation.BurgardKjaerVertexExposure (
					dblCollateralizedExposure - cgvco.counterParty(),
					dblCollateralizedExposure - cgvco.bank(),
					dblHedgeError,
					dblCollateralBalance
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of BurgardKjaerVertex using semi-replication with no Short-fall at own
	 *  Default using Two Bonds
	 * 
	 * @param dtAnchor The Vertex Date Anchor
	 * @param dblExposure The Exposure at the Path Vertex Time Node
	 * @param dblRealizedCashFlow The Default Window Realized Cash-flow at the Path Vertex Time Node
	 * @param dblCollateralBalance The Collateral Balance at the Path Vertex Time Node
	 * @param me The Market Edge
	 * @param cog The Generic Close-Out Evaluator Instance
	 * 
	 * @return The Standard Instance of BurgardKjaerVertex using semi-replication with no Short-fall at own
	 *  Default using Two Bonds
	 */

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex SemiReplicationDualBond (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final double dblExposure,
		final double dblRealizedCashFlow,
		final double dblCollateralBalance,
		final org.drip.xva.universe.MarketEdge me,
		final org.drip.xva.definition.CloseOutGeneral cog)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRealizedCashFlow) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblCollateralBalance))
			return null;

		double dblUncollateralizedExposure = dblExposure + dblRealizedCashFlow;
		double dblCollateralizedExposure = dblUncollateralizedExposure - dblCollateralBalance;

		try {
			return BankPortfolioBuilder (
				dtAnchor,
				new org.drip.xva.hypothecation.CollateralGroupVertexExposure (
					dblExposure,
					dblRealizedCashFlow
				),
				me,
				org.drip.xva.hypothecation.CollateralGroupVertexCloseOut.Standard (
					cog,
					dblUncollateralizedExposure,
					dblCollateralBalance
				),
				new org.drip.xva.hypothecation.BurgardKjaerVertexExposure (
					0. < dblCollateralizedExposure ? dblCollateralizedExposure : 0.,
					0. > dblCollateralizedExposure ? dblCollateralizedExposure : 0.,
					0. < dblCollateralizedExposure ? dblCollateralizedExposure : 0.,
					dblCollateralBalance
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of BurgardKjaerVertex using a Fully Collateralized Strategy, i.e., also
	 * 	referred to as the 2 Way Gold Plated CSA
	 * 
	 * @param dtAnchor The Vertex Date Anchor
	 * @param dblExposure The Exposure at the Path Vertex Time Node
	 * @param dblRealizedCashFlow The Default Window Realized Cash-flow at the Path Vertex Time Node
	 * @param me The Market Edge
	 * @param cog The Generic Close-Out Evaluator Instance
	 * 
	 * @return The Standard Instance of BurgardKjaerVertex using using a Fully Collateralized Strategy
	 */

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex GoldPlatedTwoWayCSA (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final double dblExposure,
		final double dblRealizedCashFlow,
		final org.drip.xva.universe.MarketEdge me,
		final org.drip.xva.definition.CloseOutGeneral cog)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRealizedCashFlow))
			return null;

		double dblUncollateralizedExposure = dblExposure + dblRealizedCashFlow;

		try {
			return BankPortfolioBuilder (
				dtAnchor,
				new org.drip.xva.hypothecation.CollateralGroupVertexExposure (
					dblExposure,
					dblRealizedCashFlow
				),
				me,
				org.drip.xva.hypothecation.CollateralGroupVertexCloseOut.Standard (
					cog,
					dblUncollateralizedExposure,
					dblUncollateralizedExposure
				),
				new org.drip.xva.hypothecation.BurgardKjaerVertexExposure (
					0.,
					0.,
					0.,
					dblUncollateralizedExposure
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of BurgardKjaerVertex using One Way CSA
	 * 
	 * @param dtAnchor The Vertex Date Anchor
	 * @param dblExposure The Exposure at the Path Vertex Time Node
	 * @param dblRealizedCashFlow The Default Window Realized Cash-flow at the Path Vertex Time Node
	 * @param me The Market Edge
	 * @param cog The Generic Close-Out Evaluator Instance
	 * 
	 * @return The Standard Instance of BurgardKjaerVertex using One Way CSA
	 */

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex OneWayCSA (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final double dblExposure,
		final double dblRealizedCashFlow,
		final org.drip.xva.universe.MarketEdge me,
		final org.drip.xva.definition.CloseOutGeneral cog)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRealizedCashFlow))
			return null;

		double dblUncollateralizedExposure = dblExposure + dblRealizedCashFlow;
		double dblCollateralBalance = 0. > dblUncollateralizedExposure ? dblUncollateralizedExposure : 0.;

		try {
			return BankPortfolioBuilder (
				dtAnchor,
				new org.drip.xva.hypothecation.CollateralGroupVertexExposure (
					dblExposure,
					dblRealizedCashFlow
				),
				me,
				org.drip.xva.hypothecation.CollateralGroupVertexCloseOut.Standard (
					cog,
					dblUncollateralizedExposure,
					dblCollateralBalance
				),
				new org.drip.xva.hypothecation.BurgardKjaerVertexExposure (
					0. < dblUncollateralizedExposure ? dblUncollateralizedExposure : 0.,
					0.,
					0. < dblUncollateralizedExposure ? dblUncollateralizedExposure : 0.,
					dblCollateralBalance
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Standard Instance of BurgardKjaerVertex using the "Set Off" Legal Agreement Scheme
	 * 
	 * @param dtAnchor The Vertex Date Anchor
	 * @param dblExposure The Exposure at the Path Vertex Time Node
	 * @param dblRealizedCashFlow The Default Window Realized Cash-flow at the Path Vertex Time Node
	 * @param dblCollateralBalance The Collateral Balance at the Path Vertex Time Node
	 * @param me The Market Edge
	 * 
	 * @return The Standard Instance of BurgardKjaerVertex using the "Set Off" Legal Agreement Scheme
	 */

	public static final org.drip.xva.hypothecation.BurgardKjaerVertex SetOff (
		final org.drip.analytics.date.JulianDate dtAnchor,
		final double dblExposure,
		final double dblRealizedCashFlow,
		final double dblCollateralBalance,
		final org.drip.xva.universe.MarketEdge me)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblExposure) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblRealizedCashFlow) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblCollateralBalance))
			return null;

		org.drip.xva.universe.MarketVertex mvFinish = me.finish();

		double dblBankSeniorRecoveryFinish = mvFinish.bank().seniorRecoveryRate();

		double dblCounterPartyRecoveryFinish = mvFinish.counterParty().seniorRecoveryRate();

		double dblCollateralizedExposure = dblExposure + dblRealizedCashFlow - dblCollateralBalance;

		try {
			return BankPortfolioBuilder (
				dtAnchor,
				new org.drip.xva.hypothecation.CollateralGroupVertexExposure (
					dblExposure,
					dblRealizedCashFlow
				),
				me,
				new org.drip.xva.hypothecation.CollateralGroupVertexCloseOut (
					dblCollateralizedExposure * dblBankSeniorRecoveryFinish,
					dblCollateralizedExposure * dblCounterPartyRecoveryFinish
				),
				new org.drip.xva.hypothecation.BurgardKjaerVertexExposure (
					dblCollateralizedExposure * (1. - dblCounterPartyRecoveryFinish),
					dblCollateralizedExposure * (1. - dblBankSeniorRecoveryFinish),
					0.,
					dblCollateralBalance
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
