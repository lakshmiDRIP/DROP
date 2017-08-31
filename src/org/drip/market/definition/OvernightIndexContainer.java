
package org.drip.market.definition;

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
 * OvernightIndexContainer holds the definitions of the overnight index definitions corresponding to
 *  different jurisdictions.
 *
 * @author Lakshmi Krishnamurthy
 */

public class OvernightIndexContainer {
	private static final
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.market.definition.OvernightIndex>
			_mapJurisdictionOvernightIndex = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.market.definition.OvernightIndex>();

	private static final
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.market.definition.OvernightIndex>
			_mapNamedOvernightIndex = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.market.definition.OvernightIndex>();

	/**
	 * Initialize the Overnight Index Container with the Overnight Indexes
	 * 
	 * @return TRUE - The Overnight Index Container successfully initialized with the indexes
	 */

	public static final boolean Init()
	{
		try {
			org.drip.market.definition.OvernightIndex oiAUD = new org.drip.market.definition.OvernightIndex
				("AUD-RBA ON AONIA", "AONIA", "AUD", "Act/365", "AUD", "ON", 0,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("AUD", oiAUD);

			_mapNamedOvernightIndex.put ("AUD-AONIA", oiAUD);

			_mapNamedOvernightIndex.put ("AUD-RBA ON AONIA", oiAUD);

			org.drip.market.definition.OvernightIndex oiBRL = new org.drip.market.definition.OvernightIndex
				("BRL-CETIP", "CETIP", "BRL", "Bus/252", "BRL", "ON", 1,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("BRL", oiBRL);

			_mapNamedOvernightIndex.put ("BRL-CETIP", oiBRL);

			_mapNamedOvernightIndex.put ("BRL-DI", oiBRL);

			org.drip.market.definition.OvernightIndex oiCAD = new org.drip.market.definition.OvernightIndex
				("CAD-CORRA", "CORRA", "CAD", "Act/365", "CAD", "ON", 1,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("CAD", oiCAD);

			_mapNamedOvernightIndex.put ("CAD-CORRA", oiCAD);

			org.drip.market.definition.OvernightIndex oiCHF = new org.drip.market.definition.OvernightIndex
				("CHF-TOIS", "TOIS", "CHF", "Act/360", "CHF", "TN", -1,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("CHF", oiCHF);

			_mapNamedOvernightIndex.put ("CHF-TOIS", oiCHF);

			org.drip.market.definition.OvernightIndex oiCZK = new org.drip.market.definition.OvernightIndex
				("CZK-CZEONIA", "CZEONIA", "CZK", "Act/360", "CZK", "ON", 0,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("CZK", oiCZK);

			_mapNamedOvernightIndex.put ("CZK-CZEONIA", oiCZK);

			org.drip.market.definition.OvernightIndex oiDKK = new org.drip.market.definition.OvernightIndex
				("DKK-DNB TN", "DNBTN", "DKK", "Act/360", "DKK", "TN", -1,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("DKK", oiDKK);

			_mapNamedOvernightIndex.put ("DKK-DNBTN", oiDKK);

			_mapNamedOvernightIndex.put ("DKK-DNB TN", oiDKK);

			org.drip.market.definition.OvernightIndex oiEUR = new org.drip.market.definition.OvernightIndex
				("EUR-EONIA", "EONIA", "EUR", "Act/360", "EUR", "ON", 0,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("EUR", oiEUR);

			_mapNamedOvernightIndex.put ("EUR-EONIA", oiEUR);

			org.drip.market.definition.OvernightIndex oiGBP = new org.drip.market.definition.OvernightIndex
				("GBP-SONIA", "SONIA", "GBP", "Act/365", "GBP", "ON", 0,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("GBP", oiGBP);

			_mapNamedOvernightIndex.put ("GBP-SONIA", oiGBP);

			org.drip.market.definition.OvernightIndex oiHKD = new org.drip.market.definition.OvernightIndex
				("HKD-HONIX", "HONIX", "HKD", "Act/365", "HKD", "ON", 0,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("HKD", oiHKD);

			_mapNamedOvernightIndex.put ("HKD-HONIX", oiHKD);

			org.drip.market.definition.OvernightIndex oiHUF = new org.drip.market.definition.OvernightIndex
				("HUF-HUFONIA", "HUFONIA", "HUF", "Act/360", "HUF", "ON", 0,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("HUF", oiHUF);

			_mapNamedOvernightIndex.put ("HUF-HUFONIA", oiHUF);

			org.drip.market.definition.OvernightIndex oiINR = new org.drip.market.definition.OvernightIndex
				("INR-ON MIBOR", "MIBOR", "INR", "Act/365", "INR", "ON", 0,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("INR", oiINR);

			_mapNamedOvernightIndex.put ("INR-MIBOR", oiINR);

			_mapNamedOvernightIndex.put ("INR-ON MIBOR", oiINR);

			org.drip.market.definition.OvernightIndex oiINR2 = new org.drip.market.definition.OvernightIndex
				("INR-MITOR", "MITOR", "INR", "Act/365", "INR", "TN", 0,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("INR2", oiINR2);

			_mapNamedOvernightIndex.put ("INR-MITOR", oiINR2);

			org.drip.market.definition.OvernightIndex oiJPY = new org.drip.market.definition.OvernightIndex
				("JPY-TONAR", "TONAR", "JPY", "Act/365", "JPY", "ON", 1,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("JPY", oiJPY);

			_mapNamedOvernightIndex.put ("JPY-TONAR", oiJPY);

			org.drip.market.definition.OvernightIndex oiNZD = new org.drip.market.definition.OvernightIndex
				("NZD-NZIONA", "NZIONA", "NZD", "Act/365", "NZD", "ON", 0,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("NZD", oiNZD);

			_mapNamedOvernightIndex.put ("NZD-NZIONA", oiNZD);

			org.drip.market.definition.OvernightIndex oiPLN = new org.drip.market.definition.OvernightIndex
				("PLN-POLONIA", "POLONIA", "PLN", "Act/365", "PLN", "ON", 0,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("PLN", oiPLN);

			_mapNamedOvernightIndex.put ("PLN-POLONIA", oiPLN);

			org.drip.market.definition.OvernightIndex oiSEK = new org.drip.market.definition.OvernightIndex
				("SEK-SIOR TN STIBOR", "STIBOR", "SEK", "Act/360", "SEK", "ON", -1,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("SEK", oiSEK);

			_mapNamedOvernightIndex.put ("SEK-SIOR", oiSEK);

			_mapNamedOvernightIndex.put ("SEK-SIOR TN STIBOR", oiSEK);

			_mapNamedOvernightIndex.put ("SEK-STIBOR", oiSEK);

			org.drip.market.definition.OvernightIndex oiSGD = new org.drip.market.definition.OvernightIndex
				("SGD-SONAR", "SONAR", "SGD", "Act/365", "SGD", "ON", 0,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("SGD", oiSGD);

			_mapNamedOvernightIndex.put ("SGD-SONAR", oiSGD);

			org.drip.market.definition.OvernightIndex oiUSD = new org.drip.market.definition.OvernightIndex
				("USD-Fed Fund", "FedFund", "USD", "Act/360", "USD", "ON", 1,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_ARITHMETIC);

			_mapJurisdictionOvernightIndex.put ("USD", oiUSD);

			_mapNamedOvernightIndex.put ("USD-Fed Fund", oiUSD);

			_mapNamedOvernightIndex.put ("USD-FedFund", oiUSD);

			org.drip.market.definition.OvernightIndex oiZAR = new org.drip.market.definition.OvernightIndex
				("ZAR-SAFEX ON Dep Rate", "SAFEX", "ZAR", "Act/365", "ZAR", "ON", 0,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("ZAR", oiZAR);

			_mapNamedOvernightIndex.put ("ZAR-SAFEX", oiZAR);

			_mapNamedOvernightIndex.put ("ZAR-SAFEX ON Dep Rate", oiZAR);

			org.drip.market.definition.OvernightIndex oiZAR2 = new org.drip.market.definition.OvernightIndex
				("ZAR-SAONIA", "SAONIA", "ZAR", "Act/365", "ZAR", "ON", 0,
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionOvernightIndex.put ("ZAR2", oiZAR2);

			_mapNamedOvernightIndex.put ("ZAR-SAONIA", oiZAR2);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Overnight Index from the Jurisdiction Name
	 * 
	 * @param strName The Overnight Index Jurisdiction Name
	 * 
	 * @return The Overnight Index
	 */

	public static final org.drip.market.definition.OvernightIndex IndexFromJurisdiction (
		final java.lang.String strName)
	{
		return _mapJurisdictionOvernightIndex.containsKey (strName) ? _mapJurisdictionOvernightIndex.get
			(strName) : null;
	}

	/**
	 * Retrieve the Overnight Index from the Index Name
	 * 
	 * @param strName The Overnight Index Index Name
	 * 
	 * @return The Overnight Index
	 */

	public static final org.drip.market.definition.OvernightIndex IndexFromName (
		final java.lang.String strName)
	{
		return _mapNamedOvernightIndex.containsKey (strName) ? _mapNamedOvernightIndex.get (strName) : null;
	}
}
