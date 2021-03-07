
package org.drip.market.otc;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>IBORFixedFloatContainer</i> holds the settings of the standard OTC IBOR fix-float swap contract
 * conventions.
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

public class IBORFixedFloatContainer {
	private static java.util.Map<java.lang.String, org.drip.market.otc.FixedFloatSwapConvention>
		_mapConvention = null;

	private static final java.lang.String TenorSubKey (
		final java.lang.String strCurrency,
		final java.lang.String strMaturityTenor)
	{
		if (null == strCurrency) return null;

		try {
			if ("AUD".equalsIgnoreCase (strCurrency))
				return 36 >= org.drip.analytics.support.Helper.TenorToMonths (strMaturityTenor) ? "36M" :
					"MAX";

			if ("CAD".equalsIgnoreCase (strCurrency) || "CHF".equalsIgnoreCase (strCurrency) ||
				"EUR".equalsIgnoreCase (strCurrency) || "GBP".equalsIgnoreCase (strCurrency) ||
					"INR".equalsIgnoreCase (strCurrency))
				return 12 >= org.drip.analytics.support.Helper.TenorToMonths (strMaturityTenor) ? "12M" :
					"MAX";
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return "MAX";
	}

	/**
	 * Initialize the Fix-Float Conventions Container with the pre-set Fix-Float Contracts
	 * 
	 * @return TRUE - The Fix-Float Conventions Container successfully initialized with the pre-set
	 *  Fix-Float Contracts
	 */

	public static final boolean Init()
	{
		if (null != _mapConvention) return true;

		_mapConvention = new java.util.TreeMap<java.lang.String,
			org.drip.market.otc.FixedFloatSwapConvention>();

		try {
			_mapConvention.put ("AUD|ALL|36M|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("AUD", "Act/365", "AUD", "3M", "3M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("AUD"),
									"3M"), "3M"), 1));

			_mapConvention.put ("AUD|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("AUD", "Act/365", "AUD", "6M", "6M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("AUD"),
									"6M"), "6M"), 1));

			_mapConvention.put ("BRL|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("BRL", "Bus/252", "BRL", "1W", "1W",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("BRL"),
									"1D"), "1D"), 0));

			_mapConvention.put ("CAD|ALL|12M|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("CAD", "Act/365", "CAD", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("CAD"),
									"3M"), "1Y"), 0));

			_mapConvention.put ("CAD|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("CAD", "Act/365", "CAD", "6M", "6M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("CAD"),
									"3M"), "6M"), 0));

			_mapConvention.put ("CHF|ALL|12M|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("CHF", "30/360", "CHF", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("CHF"),
									"3M"), "3M"), 2));

			_mapConvention.put ("CHF|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("CHF", "30/360", "CHF", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("CHF"),
									"6M"), "6M"), 2));

			_mapConvention.put ("CNY|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("CNY", "Act/365", "CNY", "3M", "3M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("CNY"),
									"1W"), "3M"), 2));

			_mapConvention.put ("CZK|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("CZK", "30/360", "CZK", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("CZK"),
									"6M"), "6M"), 2));

			_mapConvention.put ("DKK|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("DKK", "30/360", "DKK", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("DKK"),
									"6M"), "6M"), 2));

			_mapConvention.put ("EUR|ALL|12M|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("EUR", "30/360", "EUR", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("EUR"),
									"3M"), "3M"), 2));

			_mapConvention.put ("EUR|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("EUR", "30/360", "EUR", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("EUR"),
									"6M"), "6M"), 2));

			_mapConvention.put ("GBP|ALL|12M|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("GBP", "Act/365", "GBP", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("GBP"),
									"3M"), "3M"), 0));

			_mapConvention.put ("GBP|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("GBP", "Act/365", "GBP", "6M", "6M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("GBP"),
									"6M"), "6M"), 0));

			_mapConvention.put ("HKD|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("HKD", "Act/365", "HKD", "3M", "3M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("HKD"),
									"3M"), "3M"), 0));

			_mapConvention.put ("HUF|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("HUF", "30/360", "HUF", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("HUF"),
									"6M"), "6M"), 2));

			_mapConvention.put ("ILS|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("ILS", "30/360", "ILS", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("ILS"),
									"6M"), "6M"), 2));

			_mapConvention.put ("INR|ALL|12M|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("INR", "Act/365", "INR", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("INR"),
									"3M"), "3M"), 2));

			_mapConvention.put ("INR|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("INR", "Act/365", "INR", "6M", "6M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("INR"),
									"6M"), "6M"), 2));

			_mapConvention.put ("JPY|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("JPY", "Act/365", "JPY", "6M", "6M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("JPY"),
									"6M"), "6M"), 2));

			_mapConvention.put ("JPY|ALL|MAX|TIBOR", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("JPY", "Act/365", "JPY", "6M", "6M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromName ("JPY-TIBOR"),
									"3M"), "3M"), 2));

			_mapConvention.put ("KRW|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("KRW", "Act/365", "KRW", "3M", "3M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("KRW"),
									"3M"), "3M"), 1));

			_mapConvention.put ("MXN|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("MXN", "28/360", "MXN", "3M", "3M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("MXN"),
									"3M"), "3M"), 2));

			_mapConvention.put ("MYR|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("MYR", "Act/365", "MYR", "3M", "3M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("MYR"),
									"3M"), "3M"), 0));

			_mapConvention.put ("NOK|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("NOK", "30/360", "NOK", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("NOK"),
									"6M"), "6M"), 2));

			_mapConvention.put ("NZD|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("NZD", "Act/365", "NZD", "6M", "6M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("NZD"),
									"3M"), "3M"), 0));

			_mapConvention.put ("PLN|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("PLN", "Act/Act ISDA", "PLN", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("PLN"),
									"6M"), "6M"), 2));

			_mapConvention.put ("SEK|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("SEK", "30/360", "SEK", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("SEK"),
									"6M"), "6M"), 2));

			_mapConvention.put ("SGD|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("SGD", "Act/365", "SGD", "6M", "6M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("SGD"),
									"6M"), "6M"), 2));

			_mapConvention.put ("THB|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("THB", "Act/365", "THB", "6M", "6M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("THB"),
									"6M"), "6M"), 2));

			_mapConvention.put ("TRY|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("TRY", "30/360", "TRY", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("TRY"),
									"6M"), "6M"), 2));

			_mapConvention.put ("TWD|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("TWD", "Act/365", "TWD", "3M", "3M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("TWD"),
									"3M"), "3M"), 2));

			_mapConvention.put ("USD|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("USD", "30/360", "USD", "6M", "6M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("USD"),
									"3M"), "3M"), 2));

			_mapConvention.put ("USD|LON|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("USD", "Act/360", "USD", "1Y", "1Y",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("USD"),
									"3M"), "3M"), 2));

			_mapConvention.put ("USD|NYC|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("USD", "30/360", "USD", "6M", "6M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("USD"),
									"3M"), "3M"), 2));

			_mapConvention.put ("ZAR|ALL|MAX|MAIN", new org.drip.market.otc.FixedFloatSwapConvention (new
				org.drip.market.otc.FixedStreamConvention ("ZAR", "Act/365", "ZAR", "3M", "3M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC),
						new org.drip.market.otc.FloatStreamConvention (
							org.drip.state.identifier.ForwardLabel.Create (
								org.drip.market.definition.IBORIndexContainer.IndexFromJurisdiction ("ZAR"),
									"3M"), "3M"), 0));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Fix-Float Convention for the specified Jurisdiction
	 * 
	 * @param strJurisdictionName The Jurisdiction Name
	 * 
	 * @return The Fix-Float Convention
	 */

	public static final org.drip.market.otc.FixedFloatSwapConvention ConventionFromJurisdiction (
		final java.lang.String strJurisdictionName)
	{
		if (null == strJurisdictionName) return null;

		java.lang.String strKey = strJurisdictionName + "|ALL|MAX|MAIN";

		return _mapConvention.containsKey (strKey) ? _mapConvention.get (strKey) : null;
	}

	/**
	 * Retrieve the Fix-Float Convention for the specified Jurisdiction for the specified Maturity Tenor
	 * 
	 * @param strJurisdictionName The Jurisdiction Name
	 * @param strMaturityTenor The Maturity Tenor
	 * 
	 * @return The Fix-Float Convention
	 */

	public static final org.drip.market.otc.FixedFloatSwapConvention ConventionFromJurisdictionMaturity (
		final java.lang.String strJurisdictionName,
		final java.lang.String strMaturityTenor)
	{
		if (null == strJurisdictionName || null == strMaturityTenor) return null;

		java.lang.String strKey = strJurisdictionName + "|ALL|" + TenorSubKey (strJurisdictionName,
			strMaturityTenor) + "|MAIN";

		return _mapConvention.containsKey (strKey) ? _mapConvention.get (strKey) : null;
	}

	/**
	 * Retrieve the Fix-Float Convention for the specified Jurisdiction for the specified Location
	 * 
	 * @param strJurisdictionName The Jurisdiction Name
	 * @param strLocation The Location
	 * 
	 * @return The Fix-Float Convention
	 */

	public static final org.drip.market.otc.FixedFloatSwapConvention ConventionFromJurisdictionLocation (
		final java.lang.String strJurisdictionName,
		final java.lang.String strLocation)
	{
		if (null == strJurisdictionName || null == strLocation) return null;

		java.lang.String strKey = strJurisdictionName + "|" + strLocation + "|MAX|MAIN";

		return _mapConvention.containsKey (strKey) ? _mapConvention.get (strKey) : null;
	}

	/**
	 * Retrieve the Fix-Float Convention for the specified Jurisdiction for the specified Index
	 * 
	 * @param strJurisdictionName The Jurisdiction Name
	 * @param strIndexName The Index Name
	 * 
	 * @return The Fix-Float Convention
	 */

	public static final org.drip.market.otc.FixedFloatSwapConvention ConventionFromJurisdictionIndex (
		final java.lang.String strJurisdictionName,
		final java.lang.String strIndexName)
	{
		if (null == strJurisdictionName || null == strIndexName) return null;

		java.lang.String strKey = strJurisdictionName + "|ALL|MAX|" + strIndexName;

		return _mapConvention.containsKey (strKey) ? _mapConvention.get (strKey) : null;
	}

	/**
	 * Retrieve the Fix-Float Convention for the specified Jurisdiction for the specified Index, Location,
	 * 	and Maturity Tenor
	 * 
	 * @param strJurisdictionName The Jurisdiction Name
	 * @param strLocation The Location
	 * @param strMaturityTenor Maturity Tenor
	 * @param strIndexName The Index Name
	 * 
	 * @return The Fix-Float Convention
	 */

	public static final org.drip.market.otc.FixedFloatSwapConvention ConventionFromJurisdiction (
		final java.lang.String strJurisdictionName,
		final java.lang.String strLocation,
		final java.lang.String strMaturityTenor,
		final java.lang.String strIndexName)
	{
		if (null == strJurisdictionName || null == strLocation || null == strMaturityTenor || null ==
			strIndexName)
			return null;

		java.lang.String strKey = strJurisdictionName + "|" + strLocation + "|" + TenorSubKey
			(strJurisdictionName, strMaturityTenor) + "|" + strIndexName;

		return _mapConvention.containsKey (strKey) ? _mapConvention.get (strKey) : null;
	}
}
