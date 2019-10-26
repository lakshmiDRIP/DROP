
package org.drip.market.otc;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>OvernightFixedFloatContainer</i> holds the settings of the standard OTC Overnight Fix-Float Swap
 * Contract Conventions.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Static Market Fields - the Definitions, the OTC/Exchange Traded Products, and Treasury Settings</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/otc">OTC Dual Stream Option Container</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OvernightFixedFloatContainer {
	private static java.util.Map<java.lang.String, org.drip.market.otc.FixedFloatSwapConvention>
		_mapFundConvention = null;

	/**
	 * Initialize the Fix-Float Conventions Container with the pre-set Fix-Float Contracts
	 * 
	 * @return TRUE - The Fix-Float Conventions Container successfully initialized with the pre-set
	 *  Fix-Float Contracts
	 */

	public static final boolean Init()
	{
		if (null != _mapFundConvention) return true;

		_mapFundConvention = new java.util.TreeMap<java.lang.String,
			org.drip.market.otc.FixedFloatSwapConvention>();

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
