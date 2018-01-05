
package org.drip.xva.dynamics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * StateEntityRealization holds the Realizations for the Latent State and the Derivative Entities. The
 *  References are:
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955.
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting, eSSRN,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class StateEntityRealization
{
	private double _dblCSANumeraire = java.lang.Double.NaN;
	private double _dblPortfolioValue = java.lang.Double.NaN;
	private double _dblBankHazardRate = java.lang.Double.NaN;
	private double _dblBankRecoveryRate = java.lang.Double.NaN;
	private double _dblBankFundingSpread = java.lang.Double.NaN;
	private double _dblOvernightNumeraire = java.lang.Double.NaN;
	private double _dblCounterPartyHazardRate = java.lang.Double.NaN;
	private double _dblCounterPartyRecoveryRate = java.lang.Double.NaN;
	private double _dblCounterPartyFundingSpread = java.lang.Double.NaN;

	/**
	 * StateEntityRealization Constructor
	 * 
	 * @param dblPortfolioValue Realized Portfolio Value
	 * @param dblOvernightNumeraire Realized Overnight Numeraire
	 * @param dblCSANumeraire Realized CSA Numeraire
	 * @param dblBankHazardRate Realized Bank Hazard Rate
	 * @param dblBankRecoveryRate Realized Bank Recovery Rate
	 * @param dblBankFundingSpread Realized Bank Funding Spread
	 * @param dblCounterPartyHazardRate Realized Counter Party Hazard Rate
	 * @param dblCounterPartyRecoveryRate Realized Counter Party Recovery Rate
	 * @param dblCounterPartyFundingSpread Realized Counter Party Funding Spread
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public StateEntityRealization (
		final double dblPortfolioValue,
		final double dblOvernightNumeraire,
		final double dblCSANumeraire,
		final double dblBankHazardRate,
		final double dblBankRecoveryRate,
		final double dblBankFundingSpread,
		final double dblCounterPartyHazardRate,
		final double dblCounterPartyRecoveryRate,
		final double dblCounterPartyFundingSpread)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblPortfolioValue = dblPortfolioValue) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblOvernightNumeraire = dblOvernightNumeraire) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblCSANumeraire = dblCSANumeraire) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblBankHazardRate = dblBankHazardRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblBankRecoveryRate = dblBankRecoveryRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblBankFundingSpread = dblBankFundingSpread) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblCounterPartyHazardRate =
				dblCounterPartyHazardRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblCounterPartyRecoveryRate =
				dblCounterPartyRecoveryRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblCounterPartyFundingSpread =
				dblCounterPartyFundingSpread))
			throw new java.lang.Exception ("StateEntityRealization Constructor > Invalid Inputs");
	}

	/**
	 * Retrieve the Realized Portfolio Value
	 * 
	 * @return The Realized Portfolio Value
	 */

	public double portfolioValue()
	{
		return _dblPortfolioValue;
	}

	/**
	 * Retrieve the Realized Overnight Numeraire
	 * 
	 * @return The Realized Overnight Numeraire
	 */

	public double overnightNumeraire()
	{
		return _dblOvernightNumeraire;
	}

	/**
	 * Retrieve the Realized CSA Numeraire
	 * 
	 * @return The Realized CSA Numeraire
	 */

	public double csaNumeraire()
	{
		return _dblCSANumeraire;
	}

	/**
	 * Retrieve the Realized Bank Hazard Rate
	 * 
	 * @return The Realized Bank Hazard Rate
	 */

	public double bankHazardRate()
	{
		return _dblBankHazardRate;
	}

	/**
	 * Retrieve the Realized Bank Recovery Rate
	 * 
	 * @return The Realized Bank Recovery Rate
	 */

	public double bankRecoveryRate()
	{
		return _dblBankRecoveryRate;
	}

	/**
	 * Retrieve the Realized Bank Funding Spread
	 * 
	 * @return The Realized Bank Funding Spread
	 */

	public double bankFundingSpread()
	{
		return _dblBankFundingSpread;
	}

	/**
	 * Retrieve the Realized Counter Party Hazard Rate
	 * 
	 * @return The Realized Counter Party Hazard Rate
	 */

	public double counterPartyHazardRate()
	{
		return _dblCounterPartyHazardRate;
	}

	/**
	 * Retrieve the Realized Counter Party Recovery Rate
	 * 
	 * @return The Realized Counter Party Recovery Rate
	 */

	public double counterPartyRecoveryRate()
	{
		return _dblCounterPartyRecoveryRate;
	}

	/**
	 * Retrieve the Realized Counter Party Funding Spread
	 * 
	 * @return The Realized Counter Party Funding Spread
	 */

	public double counterPartyFundingSpread()
	{
		return _dblCounterPartyFundingSpread;
	}
}
