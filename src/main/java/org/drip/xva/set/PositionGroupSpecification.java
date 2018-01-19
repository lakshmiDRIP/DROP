
package org.drip.xva.set;

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
 * PositionGroupSpecification contains the Specifications of a Position Group. The References are:
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

public class PositionGroupSpecification extends org.drip.xva.set.RollUpGroupSpecification
{
	private org.drip.xva.set.RollUpGroupSpecification _fundingGroupSpecification = null;
	private org.drip.xva.set.NettingGroupSpecification _nettingGroupSpecification = null;
	private org.drip.xva.set.CollateralGroupSpecification _collateralGroupSpecification = null;
	private org.drip.xva.set.CounterPartyGroupSpecification _counterPartyGroupSpecification = null;

	/**
	 * PositionGroupSpecification Constructor
	 * 
	 * @param id The Position Group ID
	 * @param name The Position Group Name
	 * @param collateralGroupSpecification The Position's Collateral Group Specification
	 * @param counterPartyGroupSpecification The Position's Counter Party Group Specification
	 * @param nettingGroupSpecification The Position's Netting Group Specification
	 * @param fundingGroupSpecification The Position's Funding Group Specification
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PositionGroupSpecification (
		final java.lang.String id,
		final java.lang.String name,
		final org.drip.xva.set.CollateralGroupSpecification collateralGroupSpecification,
		final org.drip.xva.set.CounterPartyGroupSpecification counterPartyGroupSpecification,
		final org.drip.xva.set.NettingGroupSpecification nettingGroupSpecification,
		final org.drip.xva.set.RollUpGroupSpecification fundingGroupSpecification)
		throws java.lang.Exception
	{
		super (
			id,
			name
		);

		if (null == (_collateralGroupSpecification = collateralGroupSpecification) ||
			null == (_counterPartyGroupSpecification = counterPartyGroupSpecification) ||
			null == (_nettingGroupSpecification = nettingGroupSpecification) ||
			null == (_fundingGroupSpecification = fundingGroupSpecification))
		{
			throw new java.lang.Exception ("PositionGroupSpecification Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Position's Collateral Group Specification
	 * 
	 * @return The Position's Collateral Group Specification
	 */

	public org.drip.xva.set.CollateralGroupSpecification collateralGroupSpecification()
	{
		return _collateralGroupSpecification;
	}

	/**
	 * Retrieve the Position's Counter Party Group Specification
	 * 
	 * @return The Position's Counter Party Group Specification
	 */

	public org.drip.xva.set.CounterPartyGroupSpecification counterPartyGroupSpecification()
	{
		return _counterPartyGroupSpecification;
	}

	/**
	 * Retrieve the Position's Netting Group Specification
	 * 
	 * @return The Position's Netting Group Specification
	 */

	public org.drip.xva.set.NettingGroupSpecification nettingGroupSpecification()
	{
		return _nettingGroupSpecification;
	}

	/**
	 * Retrieve the Position's Funding Group Specification
	 * 
	 * @return The Position's Funding Group Specification
	 */

	public org.drip.xva.set.RollUpGroupSpecification fundingGroupSpecification()
	{
		return _fundingGroupSpecification;
	}
}
