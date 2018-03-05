
package org.drip.xva.proto;

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
 * CreditDebtGroupSpecification contains the Specification of a Credit/Debt Netting Group. The References
 *  are:
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

public class CreditDebtGroupSpecification extends org.drip.xva.proto.ObjectSpecification
{
	private boolean _contractual = true;
	private boolean _enforceable = true;
	private org.drip.state.identifier.EntityHazardLabel _bankHazardLabel = null;
	private org.drip.state.identifier.EntityHazardLabel _counterPartyHazardLabel = null;
	private org.drip.state.identifier.EntityRecoveryLabel _bankSeniorRecoveryLabel = null;
	private org.drip.state.identifier.EntityRecoveryLabel _counterPartyRecoveryLabel = null;
	private org.drip.state.identifier.EntityRecoveryLabel _bankSubordinateRecoveryLabel = null;

	/**
	 * Generate a Standard Instance of CreditDebtGroupSpecification
	 * 
	 * @param id The Collateral Group ID
	 * @param name The Collateral Group Name
	 * @param bankHazardLabel The Bank Hazard Rate Latent State Label
	 * @param counterPartyHazardLabel The Counter Party Hazard Rate Latent State Label
	 * @param bankSeniorRecoveryLabel The Bank Senior Recovery Rate Latent State Label
	 * @param counterPartyRecoveryLabel The Counter Party Recovery Rate Latent State Label
	 * @param bankSubordinateRecoveryLabel The Bank Subordinate Recovery Rate Latent State Label
	 * 
	 * @return Standard Instance of NettingGroupSpecification
	 */

	public static final CreditDebtGroupSpecification Standard (
		final java.lang.String id,
		final java.lang.String name,
		final org.drip.state.identifier.EntityHazardLabel bankHazardLabel,
		final org.drip.state.identifier.EntityHazardLabel counterPartyHazardLabel,
		final org.drip.state.identifier.EntityRecoveryLabel bankSeniorRecoveryLabel,
		final org.drip.state.identifier.EntityRecoveryLabel counterPartyRecoveryLabel,
		final org.drip.state.identifier.EntityRecoveryLabel bankSubordinateRecoveryLabel)
	{
		try {
			return new CreditDebtGroupSpecification (
				id,
				name,
				bankHazardLabel,
				counterPartyHazardLabel,
				bankSeniorRecoveryLabel,
				counterPartyRecoveryLabel,
				bankSubordinateRecoveryLabel,
				true,
				true
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CreditDebtGroupSpecification Constructor
	 * 
	 * @param id The Collateral Group ID
	 * @param name The Collateral Group Name
	 * @param bankHazardLabel The Bank Hazard Rate Latent State Label
	 * @param counterPartyHazardLabel The Counter Party Hazard Rate Latent State Label
	 * @param bankSeniorRecoveryLabel The Bank Senior Recovery Rate Latent State Label
	 * @param counterPartyRecoveryLabel The Counter Party Recovery Rate Latent State Label
	 * @param bankSubordinateRecoveryLabel The Bank Subordinate Recovery Rate Latent State Label
	 * @param contractual TRUE - The Netting is Contractual
	 * @param enforceable TRUE - The Netting is Enforceable
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CreditDebtGroupSpecification (
		final java.lang.String id,
		final java.lang.String name,
		final org.drip.state.identifier.EntityHazardLabel bankHazardLabel,
		final org.drip.state.identifier.EntityHazardLabel counterPartyHazardLabel,
		final org.drip.state.identifier.EntityRecoveryLabel bankSeniorRecoveryLabel,
		final org.drip.state.identifier.EntityRecoveryLabel counterPartyRecoveryLabel,
		final org.drip.state.identifier.EntityRecoveryLabel bankSubordinateRecoveryLabel,
		final boolean contractual,
		final boolean enforceable)
		throws java.lang.Exception
	{
		super (
			id,
			name
		);

		if (null == (_bankHazardLabel = bankHazardLabel) ||
			null == (_counterPartyHazardLabel = counterPartyHazardLabel) ||
			null == (_bankSeniorRecoveryLabel = bankSeniorRecoveryLabel) ||
			null == (_counterPartyRecoveryLabel = counterPartyRecoveryLabel))
		{
			throw new java.lang.Exception ("CreditDebtGroupSpecification Constructor");
		}

		_contractual = contractual;
		_enforceable = enforceable;
		_bankSubordinateRecoveryLabel = bankSubordinateRecoveryLabel;
	}

	/**
	 * Indicate if the Netting allowed is Contractual
	 * 
	 * @return TRUE - The Netting allowed is Contractual
	 */

	public boolean contractual()
	{
		return _contractual;
	}

	/**
	 * Indicate if the Netting is Enforceable
	 * 
	 * @return TRUE - The Netting is Enforceable
	 */

	public boolean enforceable()
	{
		return _enforceable;
	}

	/**
	 * Retrieve the Bank Hazard Label
	 * 
	 * @return The Bank Hazard Label
	 */

	public org.drip.state.identifier.EntityHazardLabel bankHazardLabel()
	{
		return _bankHazardLabel;
	}

	/**
	 * Retrieve the Counter Party Hazard Label
	 * 
	 * @return The Counter Party Hazard Label
	 */

	public org.drip.state.identifier.EntityHazardLabel counterPartyHazardLabel()
	{
		return _counterPartyHazardLabel;
	}

	/**
	 * Retrieve the Bank Senior Recovery Label
	 * 
	 * @return The Bank Senior Recovery Label
	 */

	public org.drip.state.identifier.EntityRecoveryLabel bankSeniorRecoveryLabel()
	{
		return _bankSeniorRecoveryLabel;
	}

	/**
	 * Retrieve the Bank Subordinate Recovery Label
	 * 
	 * @return The Bank Subordinate Recovery Label
	 */

	public org.drip.state.identifier.EntityRecoveryLabel bankSubordinateRecoveryLabel()
	{
		return _bankSubordinateRecoveryLabel;
	}

	/**
	 * Retrieve the Counter Party Recovery Label
	 * 
	 * @return The Counter Party Recovery Label
	 */

	public org.drip.state.identifier.EntityRecoveryLabel counterPartyRecoveryLabel()
	{
		return _counterPartyRecoveryLabel;
	}
}
