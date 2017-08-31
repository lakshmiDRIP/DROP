
package org.drip.market.otc;

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
 * OvernightFixedFloatContainer holds the settings of the standard OTC Overnight Fix-Float Swap Contract
 *  Conventions.
 *
 * @author Lakshmi Krishnamurthy
 */

public class OvernightFixedFloatContainer {
	private static final java.util.Map<java.lang.String, org.drip.market.otc.FixedFloatSwapConvention>
		_mapFundConvention = new java.util.TreeMap<java.lang.String,
			org.drip.market.otc.FixedFloatSwapConvention>();

	/**
	 * Initialize the Fix-Float Conventions Container with the pre-set Fix-Float Contracts
	 * 
	 * @return TRUE - The Fix-Float Conventions Container successfully initialized with the pre-set
	 *  Fix-Float Contracts
	 */

	public static final boolean Init()
	{
		try {
			_mapFundConvention.put ("AUD", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("AUD", "Act/365", "AUD", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention
							(org.drip.state.identifier.ForwardLabel.Create
								(org.drip.market.definition.OvernightIndexContainer.IndexFromJurisdiction
									("AUD"), "ON"), "ON"), 1));

			_mapFundConvention.put ("CAD", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("CAD", "Act/365", "CAD", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.OvernightIndexContainer.IndexFromJurisdiction
									("CAD"), "ON"), "ON"), 0));

			_mapFundConvention.put ("CHF", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("CHF", "Act/360", "CHF", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.OvernightIndexContainer.IndexFromJurisdiction
									("CHF"), "ON"), "ON"), 2));

			_mapFundConvention.put ("EUR", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("EUR", "Act/360", "EUR", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.OvernightIndexContainer.IndexFromJurisdiction
									("EUR"), "ON"), "ON"), 2));

			_mapFundConvention.put ("GBP", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("GBP", "Act/365", "GBP", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.OvernightIndexContainer.IndexFromJurisdiction
									("GBP"), "ON"), "ON"), 1));

			_mapFundConvention.put ("INR", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("INR", "Act/365", "INR", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.OvernightIndexContainer.IndexFromJurisdiction
									("INR"), "ON"), "ON"), 1));

			_mapFundConvention.put ("JPY", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("JPY", "Act/365", "JPY", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.OvernightIndexContainer.IndexFromJurisdiction
									("JPY"), "ON"), "ON"), 2));

			_mapFundConvention.put ("NZD", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("NZD", "Act/365", "NZD", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention
							(org.drip.state.identifier.ForwardLabel.Create
								(org.drip.market.definition.OvernightIndexContainer.IndexFromJurisdiction
									("NZD"), "ON"), "ON"), 1));

			_mapFundConvention.put ("SEK", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("SEK", "Act/360", "SEK", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.OvernightIndexContainer.IndexFromJurisdiction
									("SEK"), "ON"), "ON"), 2));

			_mapFundConvention.put ("SGD", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("SGD", "Act/365", "SGD", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.OvernightIndexContainer.IndexFromJurisdiction
									("SGD"), "ON"), "ON"), 2));

			_mapFundConvention.put ("USD", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("USD", "Act/360", "USD", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.OvernightIndexContainer.IndexFromJurisdiction
									("USD"), "ON"), "ON"), 2));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Fix-Float Overnight Fund Convention for the specified Jurisdiction
	 * 
	 * @param strJurisdictionName The Jurisdiction Name
	 * 
	 * @return The Fix-Float Overnight Fund Convention
	 */

	public static final org.drip.market.otc.FixedFloatSwapConvention FundConventionFromJurisdiction (
		final java.lang.String strJurisdictionName)
	{
		return null == strJurisdictionName || strJurisdictionName.isEmpty() ||
			!_mapFundConvention.containsKey (strJurisdictionName) ? null : _mapFundConvention.get
				(strJurisdictionName);
	}

	/**
	 * Retrieve the Fix-Float Overnight Index Convention for the specified Jurisdiction
	 * 
	 * @param strJurisdictionName The Jurisdiction Name
	 * @param strMaturityTenor The Maturity Tenor
	 * 
	 * @return The Fix-Float Overnight Index Convention
	 */

	public static final org.drip.market.otc.FixedFloatSwapConvention IndexConventionFromJurisdiction (
		final java.lang.String strJurisdictionName,
		final java.lang.String strMaturityTenor)
	{
		org.drip.market.otc.FixedFloatSwapConvention ffscFund = null == strJurisdictionName ||
			strJurisdictionName.isEmpty() || !_mapFundConvention.containsKey (strJurisdictionName) ? null :
				_mapFundConvention.get (strJurisdictionName);

		if (null == ffscFund) return null;

		org.drip.market.otc.FloatStreamConvention fundFloatConvention = ffscFund.floatStreamConvention();

		try {
			org.drip.market.otc.FloatStreamConvention overnightFloatConvention = new
				org.drip.market.otc.FloatStreamConvention (fundFloatConvention.floaterIndex(),
					org.drip.analytics.support.Helper.LEFT_TENOR_LESSER ==
						org.drip.analytics.support.Helper.TenorCompare (strMaturityTenor, "1Y") ?
							strMaturityTenor : "1Y");

			return new org.drip.market.otc.FixedFloatSwapConvention (ffscFund.fixedStreamConvention(),
				overnightFloatConvention, ffscFund.spotLag());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
