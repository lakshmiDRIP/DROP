
package org.drip.state.identifier;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * EntityCreditLabel contains the Identifier Parameters referencing the Latent State of the named Entity
 *  Credit Curve.
 *  
 * @author Lakshmi Krishnamurthy
 */

public abstract class EntityCreditLabel extends org.drip.state.identifier.EntityDesignateLabel
{

	/**
	 * The "SENIOR" Seniority Setting
	 */

	public static final java.lang.String SENIORITY_SENIOR = "SENIOR";

	/**
	 * The "SUBORDINATE" Seniority Setting
	 */

	public static final java.lang.String SENIORITY_SUBORDINATE = "SUBORDINATE";

	private java.lang.String _seniority = "";

	protected EntityCreditLabel (
		final java.lang.String referenceEntity,
		final java.lang.String currency,
		final java.lang.String seniority)
		throws java.lang.Exception
	{
		super (
			referenceEntity,
			currency
		);

		if (null == (_seniority = seniority) ||
			(!SENIORITY_SENIOR.equals (_seniority) && !SENIORITY_SUBORDINATE.equals (_seniority)))
		{
			throw new java.lang.Exception ("EntityCreditLabel Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Seniority
	 * 
	 * @return The Seniority
	 */

	public java.lang.String seniority()
	{
		return _seniority;
	}

	@Override public java.lang.String fullyQualifiedName()
	{
		return super.fullyQualifiedName() + "::" + _seniority;
	}

	@Override public boolean match (
		final org.drip.state.identifier.LatentStateLabel lslOther)
	{
		if (null == lslOther || !(lslOther instanceof org.drip.state.identifier.EntityCreditLabel))
		{
			return false;
		}

		return super.match (lslOther) && _seniority.equalsIgnoreCase
			(((org.drip.state.identifier.EntityCreditLabel) lslOther).seniority());
	}
}
