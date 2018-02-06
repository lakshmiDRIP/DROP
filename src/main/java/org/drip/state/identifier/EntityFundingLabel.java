
package org.drip.state.identifier;

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
 * EntityFundingLabel contains the Identifier Parameters referencing the Latent State of the Entity Funding
 *  Curve.
 *  
 * @author Lakshmi Krishnamurthy
 */

public class EntityFundingLabel extends org.drip.state.identifier.EntityCreditLabel
{

	/**
	 * Make a Standard SENIOR Entity Funding Label from the Reference Entity
	 * 
	 * @param referenceEntity The Reference Entity
	 * @param currency The Currency
	 * 
	 * @return The SENIOR Funding Label
	 */

	public static final EntityFundingLabel Senior (
		final java.lang.String referenceEntity,
		final java.lang.String currency)
	{
		try
		{
			return new EntityFundingLabel (
				referenceEntity,
				currency,
				org.drip.state.identifier.EntityCreditLabel.SENIORITY_SENIOR
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Make a Standard SUBORDINATE Entity Funding Label from the Reference Entity
	 * 
	 * @param referenceEntity The Reference Entity
	 * @param currency The Currency
	 * 
	 * @return The SUBORDINATE Funding Label
	 */

	public static final EntityFundingLabel Subordinate (
		final java.lang.String referenceEntity,
		final java.lang.String currency)
	{
		try
		{
			return new EntityFundingLabel (
				referenceEntity,
				currency,
				org.drip.state.identifier.EntityCreditLabel.SENIORITY_SUBORDINATE
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * EntityFundingLabel constructor
	 * 
	 * @param referenceEntity The Reference Entity
	 * @param currency The Currency
	 * @param seniority The Obligation Seniority
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public EntityFundingLabel (
		final java.lang.String referenceEntity,
		final java.lang.String currency,
		final java.lang.String seniority)
		throws java.lang.Exception
	{
		super (
			referenceEntity,
			currency,
			seniority
		);
	}

	@Override public boolean match (
		final org.drip.state.identifier.LatentStateLabel lslOther)
	{
		return null == lslOther || !(lslOther instanceof org.drip.state.identifier.EntityFundingLabel) ?
			false : super.match (lslOther);
	}
}
