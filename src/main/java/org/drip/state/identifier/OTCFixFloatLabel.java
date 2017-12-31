
package org.drip.state.identifier;

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
 * OTCFixFloatLabel contains the Index Parameters referencing a Payment on an OTC Fix/Float IRS Par Rate
 *  Index.
 *  
 * @author Lakshmi Krishnamurthy
 */

public class OTCFixFloatLabel extends org.drip.state.identifier.FloaterLabel
{
	private java.lang.String _strFixFloatTenor = "";

	/**
	 * Construct a OTCFixFloatLabel from the corresponding Fully Qualified Name
	 * 
	 * @param strFullyQualifiedName The Fully Qualified Name
	 * 
	 * @return OTCFixFloatLabel Instance
	 */

	public static final OTCFixFloatLabel Standard (
		final java.lang.String strFullyQualifiedName)
	{
		if (null == strFullyQualifiedName || strFullyQualifiedName.isEmpty()) return null;

		java.lang.String[] astr = strFullyQualifiedName.split ("-");

		if (null == astr || 3 != astr.length) return null;

		java.lang.String strTenor = astr[1];
		java.lang.String strCurrency = astr[0];
		java.lang.String strFixFloatTenor = astr[2];

		org.drip.market.definition.FloaterIndex floaterIndex = "ON".equalsIgnoreCase (strTenor) ||
			"1D".equalsIgnoreCase (strTenor) ?
				org.drip.market.definition.OvernightIndexContainer.IndexFromJurisdiction (strCurrency) :
					org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction (strCurrency);

		try {
			return new OTCFixFloatLabel (
				floaterIndex,
				strTenor,
				strFixFloatTenor
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * OTCFixFloatLabel Constructor
	 * 
	 * @param floaterIndex Floater Index
	 * @param strForwardTenor Forward Tenor
	 * @param strFixFloatTenor Fix Float Tenor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public OTCFixFloatLabel (
		final org.drip.market.definition.FloaterIndex floaterIndex,
		final java.lang.String strForwardTenor,
		final java.lang.String strFixFloatTenor)
		throws java.lang.Exception
	{
		super (floaterIndex, strForwardTenor);

		if (null == (_strFixFloatTenor = strFixFloatTenor) || _strFixFloatTenor.isEmpty())
			throw new java.lang.Exception ("OTCFixFloatLabel Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Fix Float Tenor
	 * 
	 * @return The Fix Float Tenor
	 */

	public java.lang.String fixFloatTenor()
	{
		return _strFixFloatTenor;
	}

	@Override public java.lang.String fullyQualifiedName()
	{
		return super.fullyQualifiedName() + "::" + _strFixFloatTenor;
	}

	@Override public boolean match (
		final org.drip.state.identifier.LatentStateLabel lslOther)
	{
		if (!(lslOther instanceof org.drip.state.identifier.OTCFixFloatLabel)) return false;

		return super.match (lslOther);
	}
}
