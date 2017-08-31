
package org.drip.market.exchange;

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
 * TreasuryFuturesConventionContainer holds the Details of the Treasury Futures Contracts.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryFuturesConventionContainer {
	private static final java.util.Map<java.lang.String, org.drip.market.exchange.TreasuryFuturesConvention>
		_mapFutures = new
			java.util.TreeMap<java.lang.String, org.drip.market.exchange.TreasuryFuturesConvention>();

	/**
	 * Initialize the Bond Futures Convention Container with the Conventions
	 * 
	 * @return TRUE - The Bond Futures Convention Container successfully initialized with the Conventions
	 */

	public static final boolean Init()
	{
		int[] aiStandardDeliveryMonth = {org.drip.analytics.date.DateUtil.FEBRUARY,
			org.drip.analytics.date.DateUtil.MAY, org.drip.analytics.date.DateUtil.AUGUST,
				org.drip.analytics.date.DateUtil.NOVEMBER};

		try {
			_mapFutures.put ("AUD-BANK-BILLS-3M", new org.drip.market.exchange.TreasuryFuturesConvention
				("AUD-BANKBILLS-3M", new java.lang.String[] {"AUD-BANKBILLS-3M"}, "AUD", "AUD", "3M",
					1000000., 0.03125, 100000., new java.lang.String[] {"ASX"}, "BANK", "BILLS", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_WEEK_DAY, false,
								-1, org.drip.analytics.date.DateUtil.FRIDAY, 2, -1), new
									org.drip.market.exchange.TreasuryFuturesEligibility ("85D", "95D", new
										java.lang.String[]
											{"Australia and New Zealand Banking Group Limited",
												"Commonwealth Bank of Australia",
													"National Australia Bank Limited",
														"Westpac Banking Corporation"}, 0.), new
															org.drip.market.exchange.TreasuryFuturesSettle (1, 1,
																0, 0,
																	org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_FLAT, false,
					java.lang.Double.NaN, java.lang.Double.NaN, aiStandardDeliveryMonth)));

			_mapFutures.put ("AUD-TREASURY-BOND-3Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("AUD-TREASURY-BOND-3Y", new java.lang.String[] {"YMA"}, "AUD", "AUD", "3Y", 100000., 0.03125,
					0., new java.lang.String[] {"SFE"}, "TREASURY", "BOND", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_WEEK_DAY, false,
								-1, org.drip.analytics.date.DateUtil.FRIDAY, 2, -1), new
									org.drip.market.exchange.TreasuryFuturesEligibility ("2Y", "4Y", new
										java.lang.String[] {}, 0.), new
											org.drip.market.exchange.TreasuryFuturesSettle (1, 1, 0, 0,
												org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_CASH,
													org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_AUD_BOND_FUTURES_STYLE,
				false, java.lang.Double.NaN, java.lang.Double.NaN, aiStandardDeliveryMonth)));

			_mapFutures.put ("AUD-TREASURY-BOND-10Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("AUD-TREASURY-BOND-10Y", new java.lang.String[] {"XMA"}, "AUD", "AUD", "10Y", 100000.,
					0.03125, 0., new java.lang.String[] {"SFE"}, "TREASURY", "BOND", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_WEEK_DAY, false,
								-1, org.drip.analytics.date.DateUtil.FRIDAY, 2, -1), new
									org.drip.market.exchange.TreasuryFuturesEligibility ("8Y", "12Y", new
										java.lang.String[] {}, 0.), new
											org.drip.market.exchange.TreasuryFuturesSettle (1, 1, 0, 0,
												org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_CASH,
													org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_AUD_BOND_FUTURES_STYLE,
				false, java.lang.Double.NaN, java.lang.Double.NaN, aiStandardDeliveryMonth)));

			_mapFutures.put ("USD-TREASURY-BOND-ULTRA", new org.drip.market.exchange.TreasuryFuturesConvention
				("ULTRA T-BOND", new java.lang.String[] {"UB", "UL", "UBE"}, "USD", "USD", "ULTRA", 100000.,
					0.03125, 0., new java.lang.String[] {"CBOT"}, "TREASURY", "BOND", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								7, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("25Y",
									"MAX", new java.lang.String[] {"US Government Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (1, 1, -1, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("USD-TREASURY-BOND-30Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("USD 30-YR BOND", new java.lang.String[] {"ZB", "US"}, "USD", "USD", "30Y", 100000., 0.03125,
					0., new java.lang.String[] {"CBOT"}, "TREASURY", "BOND", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								7, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("15Y",
									"25Y", new java.lang.String[] {"US Government Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (1, 1, -1, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("USD-TREASURY-NOTE-10Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("USD 10-YR NOTE", new java.lang.String[] {"ZN", "TY"}, "USD", "USD", "10Y", 100000., 0.03125,
					0., new java.lang.String[] {"CBOT"}, "TREASURY", "NOTE", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								7, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("78M",
									"10Y", new java.lang.String[] {"US Government Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (1, 1, -1, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("USD-TREASURY-NOTE-5Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("USD 5-YR NOTE", new java.lang.String[] {"ZF", "FV"}, "USD", "USD", "5Y", 100000., 0.03125,
					0., new java.lang.String[] {"CBOT"}, "TREASURY", "NOTE", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								0, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("50M",
									"63M", new java.lang.String[] {"US Government Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (3, 3, 0, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("USD-TREASURY-NOTE-3Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("USD 3-YR NOTE", new java.lang.String[] {"Z3N", "3YR"}, "USD", "USD", "3Y", 200000., 0.03125,
					0., new java.lang.String[] {"CBOT"}, "TREASURY", "NOTE", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								0, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("33M",
									"3Y", new java.lang.String[] {"US Government Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (1, 1, 0, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("USD-TREASURY-NOTE-2Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("USD 2-YR NOTE", new java.lang.String[] {"ZT", "TU"}, "USD", "USD", "2Y", 200000., 0.03125,
					0., new java.lang.String[] {"CBOT"}, "TREASURY", "NOTE", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								0, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("21M",
									"2Y", new java.lang.String[] {"US Government Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (1, 1, 0, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("EUR-EURO-BUXL-30Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("EUR EURO-BUXL", new java.lang.String[] {"BUXL"}, "EUR", "EUR", "30Y", 100000., 0.03125, 0.,
					new java.lang.String[] {"EUREX"}, "EURO", "BUXL", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								10, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("24Y",
									"35Y", new java.lang.String[] {"EUR-Germany BUXL Bonds"}, 5000000000.),
										new org.drip.market.exchange.TreasuryFuturesSettle (1, 1, 0, 2,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.04, 0.04, aiStandardDeliveryMonth)));

			_mapFutures.put ("EUR-EURO-BUND-10Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("EUR EURO-BUND", new java.lang.String[] {"BUND"}, "EUR", "EUR", "10Y", 100000., 0.03125, 0.,
					new java.lang.String[] {"EUREX", "NLX"}, "EURO", "BUND", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								10, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("102M",
									"126M", new java.lang.String[] {"EUR-Germany BUND Bonds"}, 5000000000.),
										new org.drip.market.exchange.TreasuryFuturesSettle (1, 1, 0, 2,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("EUR-EURO-BOBL-5Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("EUR EURO-BUND", new java.lang.String[] {"BOBL"}, "EUR", "EUR", "5Y", 100000., 0.03125, 0.,
					new java.lang.String[] {"EUREX", "NLX"}, "EURO", "BOBL", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								10, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("54M",
									"66M", new java.lang.String[] {"EUR-Germany BOBL Bonds"}, 5000000000.),
										new org.drip.market.exchange.TreasuryFuturesSettle (1, 1, 0, 2,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("EUR-EURO-SCHATZ-2Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("EUR EURO-BUND", new java.lang.String[] {"SCHATZ"}, "EUR", "EUR", "2Y", 100000., 0.03125, 0.,
					new java.lang.String[] {"EUREX", "NLX"}, "EURO", "SCHATZ", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								10, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("21M",
									"27M", new java.lang.String[] {"EUR-Germany SCHATZ Bonds"}, 5000000000.),
										new org.drip.market.exchange.TreasuryFuturesSettle (1, 1, 0, 2,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("EUR-TREASURY-BONO-10Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("EUR 10Y BONO", new java.lang.String[] {"10Y-BONO"}, "EUR", "EUR", "10Y", 100000., 0.03125,
					0., new java.lang.String[] {"MEFF", "SENAF"}, "TREASURY", "BONO", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_SPECIFIC_DAY_OF_MONTH,
				false, 7, -1, -1, 10), new org.drip.market.exchange.TreasuryFuturesEligibility ("102M", "MAX",
					new java.lang.String[] {"EUR BONO"}, 0.), new org.drip.market.exchange.TreasuryFuturesSettle
						(1, 1, -2, -2,
							org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
								org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR,
				false, 0.06, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("GBP-SHORT-GILT-2Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("GBP SHORT-GILT", new java.lang.String[] {"SHORT-GILT"}, "GBP", "GBP", "2Y", 100000.,
					0.03125, 0., new java.lang.String[] {"LIFFE"}, "SHORT", "GILT", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								2, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("18M",
									"39M", new java.lang.String[] {"GBP Short GILT Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (0, 22, -2, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.04, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("GBP-MEDIUM-GILT-5Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("GBP MEDIUM-GILT", new java.lang.String[] {"MEDIUM-GILT"}, "GBP", "GBP", "5Y", 100000.,
					0.03125, 0., new java.lang.String[] {"LIFFE"}, "MEDIUM", "GILT", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								2, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("4Y",
									"75M", new java.lang.String[] {"GBP Medium GILT Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (0, 22, -2, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.04, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("GBP-LONG-GILT-10Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("GBP LONG-GILT", new java.lang.String[] {"LONG-GILT"}, "GBP", "GBP", "10Y", 100000., 0.03125,
					0., new java.lang.String[] {"LIFFE", "NLX"}, "MEDIUM", "GILT", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_EDGE_LAG, true,
								2, -1, -1, -1), new org.drip.market.exchange.TreasuryFuturesEligibility ("105M",
									"13Y", new java.lang.String[] {"GBP Long GILT Bonds"}, 0.), new
										org.drip.market.exchange.TreasuryFuturesSettle (0, 22, -2, 0,
											org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
				org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR, false,
					0.03, 0.06, aiStandardDeliveryMonth)));

			_mapFutures.put ("JPY-TREASURY-JGB-5Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("JPY 5Y JGB", new java.lang.String[] {"5Y-JGB"}, "JPY", "JPY", "5Y", 1000000000., 0.03125,
					0., new java.lang.String[] {"TSE"}, "TREASURY", "JGB", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_SPECIFIC_DAY_OF_MONTH,
				true, 7, -1, -1, 20), new org.drip.market.exchange.TreasuryFuturesEligibility ("4Y", "63M", new
					java.lang.String[] {"JPY JGB"}, 0.), new org.drip.market.exchange.TreasuryFuturesSettle (1,
						1, 0, -7, org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
							org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR,
				false, 0.03, 0.03, aiStandardDeliveryMonth)));

			_mapFutures.put ("JPY-TREASURY-JGB-10Y", new org.drip.market.exchange.TreasuryFuturesConvention
				("JPY 10Y JGB", new java.lang.String[] {"10Y-JGB"}, "JPY", "JPY", "10Y", 1000000000., 0.03125,
					0., new java.lang.String[] {"TSE"}, "TREASURY", "JGB", new
						org.drip.analytics.eventday.DateInMonth
							(org.drip.analytics.eventday.DateInMonth.INSTANCE_GENERATOR_RULE_SPECIFIC_DAY_OF_MONTH,
				true, 7, -1, -1, 20), new org.drip.market.exchange.TreasuryFuturesEligibility ("7Y", "10Y", new
					java.lang.String[] {"JPY JGB"}, 0.), new org.drip.market.exchange.TreasuryFuturesSettle (1,
						1, 0, -7, org.drip.market.exchange.TreasuryFuturesSettle.SETTLE_TYPE_PHYSICAL_DELIVERY,
							org.drip.market.exchange.TreasuryFuturesSettle.QUOTE_REFERENCE_INDEX_CONVERSION_FACTOR,
				false, 0.06, 0.06, aiStandardDeliveryMonth)));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Treasury Futures Convention from the Currency, the Type, the Sub-type, and the Maturity
	 *	Tenor
	 * 
	 * @param strCurrency The Currency
	 * @param strUnderlierType The Underlier Type
	 * @param strUnderlierSubtype The Underlier Sub-type
	 * @param strMaturityTenor The Maturity Tenor
	 * 
	 * @return The Treasury Futures Instance
	 */

	public static final org.drip.market.exchange.TreasuryFuturesConvention FromJurisdictionTypeMaturity (
		final java.lang.String strCurrency,
		final java.lang.String strUnderlierType,
		final java.lang.String strUnderlierSubtype,
		final java.lang.String strMaturityTenor)
	{
		if (null == strCurrency || strCurrency.isEmpty() || null == strUnderlierType ||
			strUnderlierType.isEmpty() || null == strUnderlierSubtype || strUnderlierSubtype.isEmpty() ||
				null == strMaturityTenor || strMaturityTenor.isEmpty())
			return null;

		java.lang.String strKey = strCurrency + "-" + strUnderlierType + "-" + strUnderlierSubtype + "-" +
			strMaturityTenor;

		return _mapFutures.containsKey (strKey) ? _mapFutures.get (strKey) : null;
	}
}
