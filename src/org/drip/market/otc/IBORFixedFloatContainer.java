
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
 * IBORFixedFloatContainer holds the settings of the standard OTC IBOR fix-float swap contract conventions.
 *
 * @author Lakshmi Krishnamurthy
 */

public class IBORFixedFloatContainer {
	private static final java.util.Map<java.lang.String, org.drip.market.otc.FixedFloatSwapConvention>
		_mapConvention = new java.util.TreeMap<java.lang.String,
			org.drip.market.otc.FixedFloatSwapConvention>();

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
