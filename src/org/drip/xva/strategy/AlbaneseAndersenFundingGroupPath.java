
package org.drip.xva.strategy;

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
 * AlbaneseAndersenFundingGroupPath rolls up the Path Realizations of the Sequence in a Single Path
 *  Projection Run over Multiple Collateral Groups onto a Single Funding Group in accordance with the
 *   Albanese Andersen (2014) Scheme. The References are:
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955, eSSRN.
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AlbaneseAndersenFundingGroupPath extends org.drip.xva.netting.FundingGroupPath {

	/**
	 * Generate a "Mono" AlbaneseAndersenFundingGroupPath Instance
	 * 
	 * @param hgp The "Mono" Hypothecation Group Path
	 * @param mp The Market Path
	 * 
	 * @return The "Mono" AlbaneseAndersenFundingGroupPath Instance
	 */

	public static final AlbaneseAndersenFundingGroupPath Mono (
		final org.drip.xva.hypothecation.CollateralGroupPath hgp,
		final org.drip.xva.universe.MarketPath mp)
	{
		try {
			return new org.drip.xva.strategy.AlbaneseAndersenFundingGroupPath (new
				org.drip.xva.hypothecation.CollateralGroupPath[] {hgp}, mp);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * AlbaneseAndersenFundingGroupPath Constructor
	 * 
	 * @param aHGP Array of the Collateral Group Trajectory Paths
	 * @param mp The Market Path
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AlbaneseAndersenFundingGroupPath (
		final org.drip.xva.hypothecation.CollateralGroupPath[] aHGP,
		final org.drip.xva.universe.MarketPath mp)
		throws java.lang.Exception
	{
		super (aHGP, mp);
	}

	@Override public double fundingValueAdjustment()
		throws java.lang.Exception
	{
		return bilateralFundingValueAdjustment();
	}

	@Override public double fundingDebtAdjustment()
		throws java.lang.Exception
	{
		return bilateralFundingDebtAdjustment();
	}

	@Override public double fundingCostAdjustment()
	{
		return unilateralFundingValueAdjustment();
	}

	@Override public double fundingBenefitAdjustment()
	{
		return unilateralFundingDebtAdjustment();
	}

	@Override public double[] periodFundingValueAdjustment()
		throws java.lang.Exception
	{
		return periodBilateralFundingValueAdjustment();
	}

	@Override public double[] periodFundingDebtAdjustment()
		throws java.lang.Exception
	{
		return periodBilateralFundingDebtAdjustment();
	}

	@Override public double[] periodFundingCostAdjustment()
	{
		return periodUnilateralFundingValueAdjustment();
	}

	@Override public double[] periodFundingBenefitAdjustment()
	{
		return periodUnilateralFundingDebtAdjustment();
	}
}
