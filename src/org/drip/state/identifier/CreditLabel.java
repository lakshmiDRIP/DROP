
package org.drip.state.identifier;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * CreditLabel contains the Identifier Parameters referencing the Latent State of the named Credit Curve.
 *  Currently it only contains the Reference Entity Name.
 *  
 * @author Lakshmi Krishnamurthy
 */

public class CreditLabel implements org.drip.state.identifier.LatentStateLabel {
	private java.lang.String _strReferenceEntity = "";

	/**
	 * Make a Standard Credit Label from the Reference Entity Name
	 * 
	 * @param strReferenceEntity The Reference Entity Name
	 * 
	 * @return The Credit Label
	 */

	public static final CreditLabel Standard (
		final java.lang.String strReferenceEntity)
	{
		try {
			return new CreditLabel (strReferenceEntity);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * CreditLabel constructor
	 * 
	 * @param strReferenceEntity The Reference Entity Name
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public CreditLabel (
		final java.lang.String strReferenceEntity)
		throws java.lang.Exception
	{
		if (null == (_strReferenceEntity = strReferenceEntity) || _strReferenceEntity.isEmpty())
			throw new java.lang.Exception ("CreditLabel ctr: Invalid Inputs");
	}

	@Override public java.lang.String fullyQualifiedName()
	{
		return _strReferenceEntity;
	}

	@Override public boolean match (
		final org.drip.state.identifier.LatentStateLabel lslOther)
	{
		return null == lslOther || !(lslOther instanceof org.drip.state.identifier.CreditLabel) ? false :
			_strReferenceEntity.equalsIgnoreCase (lslOther.fullyQualifiedName());
	}

	/**
	 * Retrieve the Reference Entity
	 * 
	 * @return The Reference Entity
	 */

	public java.lang.String referenceEntity()
	{
		return _strReferenceEntity;
	}
}
