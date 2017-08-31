
package org.drip.state.identifier;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * OvernightLabel contains the Index Parameters referencing an Overnight Index. It provides the functionality
 *  to Retrieve Index, Tenor, Currency, and Fully Qualified Name.
 *  
 * @author Lakshmi Krishnamurthy
 */

public class OvernightLabel extends org.drip.state.identifier.ForwardLabel {
	private org.drip.market.definition.OvernightIndex _overnightIndex = null;

	/**
	 * Construct an OvernightLabel from the Jurisdiction
	 * 
	 * @param strCurrency The Currency
	 * 
	 * @return The OvernightLabel Instance
	 */

	public static final OvernightLabel Create (
		final java.lang.String strCurrency)
	{
		try {
			return new OvernightLabel
				(org.drip.market.definition.OvernightIndexContainer.IndexFromJurisdiction (strCurrency));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an OvernightLabel from the Index
	 * 
	 * @param overnightIndex The Overnight Index Details
	 * 
	 * @return The OvernightLabel Instance
	 */

	public static final OvernightLabel Create (
		final org.drip.market.definition.OvernightIndex overnightIndex)
	{
		try {
			return new OvernightLabel (overnightIndex);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * OvernightLabel Constructor
	 * 
	 * @param overnightIndex The Overnight Index Details
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	private OvernightLabel (
		final org.drip.market.definition.OvernightIndex overnightIndex)
		throws java.lang.Exception
	{
		super (overnightIndex, "ON");

		if (null == (_overnightIndex = overnightIndex))
			throw new java.lang.Exception ("OvernightLabel ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Currency
	 * 
	 * @return The Currency
	 */

	public java.lang.String currency()
	{
		return _overnightIndex.currency();
	}

	/**
	 * Retrieve the Family
	 * 
	 * @return The Family
	 */

	public java.lang.String family()
	{
		return _overnightIndex.family();
	}

	/**
	 * Retrieve the Tenor
	 * 
	 * @return The Tenor
	 */

	public java.lang.String tenor()
	{
		return "ON";
	}

	/**
	 * Indicate if the Index is an Overnight Index
	 * 
	 * @return TRUE - Overnight Index
	 */

	public boolean overnight()
	{
		return true;
	}

	/**
	 * Retrieve the Overnight Index
	 * 
	 * @return The Overnight Index
	 */

	public org.drip.market.definition.OvernightIndex overnightIndex()
	{
		return _overnightIndex;
	}

	/**
	 * Retrieve a Unit Coupon Accrual Setting
	 * 
	 * @return Unit Coupon Accrual Setting
	 */

	public org.drip.param.period.UnitCouponAccrualSetting ucas()
	{
		java.lang.String strDayCount = _overnightIndex.dayCount();

		try {
			return new org.drip.param.period.UnitCouponAccrualSetting (360, strDayCount, false, strDayCount,
				false, _overnightIndex.currency(), false, _overnightIndex.accrualCompoundingRule());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public java.lang.String fullyQualifiedName()
	{
		return _overnightIndex.currency() + "-" + _overnightIndex.family() + "-ON";
	}

	@Override public boolean match (
		final org.drip.state.identifier.LatentStateLabel lslOther)
	{
		return null == lslOther || !(lslOther instanceof org.drip.state.identifier.OvernightLabel) ? false :
			fullyQualifiedName().equalsIgnoreCase (lslOther.fullyQualifiedName());
	}
}
