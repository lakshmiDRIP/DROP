
package org.drip.xva.evolver;

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
 * PrimarySecurityContainer holds the Economy with the following Traded Assets - the Overnight Index
 *  Numeraire, the Collateral Scheme Numeraire, the Default-able Bank Bond Numeraire, the Array of
 *  Default-able Counter-party Numeraires, and an Asset that follows Brownian Motion. The References are:
 *  
 *  - Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  	Re-Hypothecation Option, eSSRN, https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955.
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies, Risk, 23 (12) 82-87.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting, eSSRN,
 *  	https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PrimarySecurityContainer extends org.drip.xva.evolver.DynamicsContainer
{
	private org.drip.state.identifier.CSALabel _csaLabel = null;
	private org.drip.state.identifier.OvernightLabel _overnightLabel = null;
	private org.drip.state.identifier.LatentStateLabel _positionLabel = null;
	private org.drip.state.identifier.EntityFundingLabel _bankSeniorFundingLabel = null;
	private org.drip.state.identifier.EntityFundingLabel _counterPartyFundingLabel = null;
	private org.drip.state.identifier.EntityFundingLabel _bankSubordinateFundingLabel = null;

	/**
	 * PrimarySecurityContainer Constructor
	 * 
	 * @param position The Position Primary Security
	 * @param overnight The Overnight Index Primary Security
	 * @param csa The CSA Primary Security
	 * @param bankSeniorFunding Bank Senior Funding Primary Security
	 * @param bankSubordinateFunding Bank Subordinate Funding Primary Security
	 * @param counterPartyFunding Counter Party Funding Primary Security
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PrimarySecurityContainer (
		final org.drip.xva.evolver.PrimarySecurity position,
		final org.drip.xva.evolver.PrimarySecurity overnight,
		final org.drip.xva.evolver.PrimarySecurity csa,
		final org.drip.xva.evolver.PrimarySecurity bankSeniorFunding,
		final org.drip.xva.evolver.PrimarySecurity bankSubordinateFunding,
		final org.drip.xva.evolver.PrimarySecurity counterPartyFunding)
		throws java.lang.Exception
	{
		if (!addPrimarySecurity (overnight) ||
			!addPrimarySecurity (csa) ||
			!addPrimarySecurity (bankSeniorFunding) ||
			!addPrimarySecurity (counterPartyFunding) ||
			!addPrimarySecurity (bankSubordinateFunding))
		{
			throw new java.lang.Exception ("PrimarySecurityContainer Constructor => Invalid Inputs");
		}

		addPrimarySecurity (position);

		org.drip.state.identifier.LatentStateLabel csaLabel = csa.label();

		org.drip.state.identifier.LatentStateLabel overnightLabel = overnight.label();

		org.drip.state.identifier.LatentStateLabel bankSeniorFundingLabel = bankSeniorFunding.label();

		org.drip.state.identifier.LatentStateLabel counterPartyFundingLabel = counterPartyFunding.label();

		org.drip.state.identifier.LatentStateLabel bankSubordinateFundingLabel = null ==
			bankSubordinateFunding ? null : bankSubordinateFunding.label();

		if (!(csaLabel instanceof org.drip.state.identifier.CSALabel) ||
			!(overnightLabel instanceof org.drip.state.identifier.OvernightLabel) ||
			!(bankSeniorFundingLabel instanceof org.drip.state.identifier.EntityFundingLabel) ||
			!(counterPartyFundingLabel instanceof org.drip.state.identifier.EntityFundingLabel) ||
			(null != bankSubordinateFundingLabel && !(bankSubordinateFundingLabel instanceof
				org.drip.state.identifier.EntityFundingLabel)))
		{
			throw new java.lang.Exception ("PrimarySecurityContainer Constructor => Invalid Inputs");
		}

		_csaLabel = (org.drip.state.identifier.CSALabel) csaLabel;
		_overnightLabel = (org.drip.state.identifier.OvernightLabel) overnightLabel;
		_bankSeniorFundingLabel = (org.drip.state.identifier.EntityFundingLabel) bankSeniorFundingLabel;
		_counterPartyFundingLabel = (org.drip.state.identifier.EntityFundingLabel) counterPartyFundingLabel;
		_bankSubordinateFundingLabel = null == bankSubordinateFundingLabel ? null :
			(org.drip.state.identifier.EntityFundingLabel) bankSubordinateFundingLabel;

		_positionLabel = null == position ? null : position.label();
	}

	/**
	 * Retrieve the Position Primary Security
	 * 
	 * @return The Position Primary Security
	 */

	public org.drip.xva.evolver.PrimarySecurity position()
	{
		return primarySecurity (_positionLabel);
	}

	/**
	 * Retrieve the Overnight Index Primary Security
	 * 
	 * @return The Overnight Index Primary Security
	 */

	public org.drip.xva.evolver.PrimarySecurity overnight()
	{
		return primarySecurity (_overnightLabel);
	}

	/**
	 * Retrieve the CSA Primary Security
	 * 
	 * @return The CSA Primary Security
	 */

	public org.drip.xva.evolver.PrimarySecurity csa()
	{
		return primarySecurity (_csaLabel);
	}

	/**
	 * Retrieve the Bank Senior Funding Primary Security
	 * 
	 * @return The Bank Senior Funding Primary Security
	 */

	public org.drip.xva.evolver.PrimarySecurity bankSeniorFunding()
	{
		return primarySecurity (_bankSeniorFundingLabel);
	}

	/**
	 * Retrieve the Bank Subordinate Funding Primary Security
	 * 
	 * @return The Bank Subordinate Funding Primary Security
	 */

	public org.drip.xva.evolver.PrimarySecurity bankSubordinateFunding()
	{
		return primarySecurity (_bankSubordinateFundingLabel);
	}

	/**
	 * Retrieve the Counter Party Funding Primary Security
	 * 
	 * @return The Counter Party Funding Primary Security
	 */

	public org.drip.xva.evolver.PrimarySecurity counterPartyFunding()
	{
		return primarySecurity (_counterPartyFundingLabel);
	}
}
