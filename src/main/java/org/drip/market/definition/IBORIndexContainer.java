
package org.drip.market.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>IBORIndexContainer</i> holds the definitions of the IBOR index definitions corresponding to the
 * different jurisdictions.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Market</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/definition">Definition</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class IBORIndexContainer {
	private static
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.market.definition.IBORIndex>
			_mapNamedIBORIndex = null;
	private static
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.market.definition.IBORIndex>
			_mapJurisdictionIBORIndex = null;

	/**
	 * Initialize the IBOR Index Container with the Overnight Indexes
	 * 
	 * @return TRUE - The IBOR Index Container successfully initialized with the indexes
	 */

	public static final boolean Init()
	{
		if (null != _mapNamedIBORIndex) return true;

		_mapNamedIBORIndex = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.market.definition.IBORIndex>();

		_mapJurisdictionIBORIndex = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.market.definition.IBORIndex>();

		try {
			org.drip.market.definition.IBORIndex iborAUD = new org.drip.market.definition.IBORIndex
				("AUD-BBSW", "BBSW", "AUD", "Act/365", "AUD", 0, "1M", "6M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("AUD", iborAUD);

			_mapNamedIBORIndex.put ("AUD-BBSW", iborAUD);

			_mapNamedIBORIndex.put ("AUD-LIBOR", iborAUD);

			org.drip.market.definition.IBORIndex iborBRL = new org.drip.market.definition.IBORIndex
				("BRL-LIBOR", "LIBOR", "BRL", "Bus/252", "BRL", 0, "ON", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("BRL", iborBRL);

			_mapNamedIBORIndex.put ("BRL-LIBOR", iborBRL);

			org.drip.market.definition.IBORIndex iborCAD = new org.drip.market.definition.IBORIndex
				("CAD-CDOR", "CDOR", "CAD", "Act/365", "CAD", 0, "1M", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("CAD", iborCAD);

			_mapNamedIBORIndex.put ("CAD-CDOR", iborCAD);

			_mapNamedIBORIndex.put ("CAD-LIBOR", iborCAD);

			org.drip.market.definition.IBORIndex iborCHF = new org.drip.market.definition.IBORIndex
				("CHF-LIBOR", "LIBOR", "CHF", "Act/360", "CHF", 2, "ON", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("CHF", iborCHF);

			_mapNamedIBORIndex.put ("CHF-LIBOR", iborCHF);

			org.drip.market.definition.IBORIndex iborCLP = new org.drip.market.definition.IBORIndex
				("CLP-LIBOR", "LIBOR", "CLP", "Act/360", "CLP", 2, "ON", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("CLP", iborCLP);

			_mapNamedIBORIndex.put ("CLP-LIBOR", iborCLP);

			org.drip.market.definition.IBORIndex iborCNY = new org.drip.market.definition.IBORIndex
				("CNY-Repo", "Repo", "CNY", "Act/365", "CNY", 0, "ON", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("CNY", iborCNY);

			_mapNamedIBORIndex.put ("CNY-LIBOR", iborCNY);

			_mapNamedIBORIndex.put ("CNY-Repo", iborCNY);

			org.drip.market.definition.IBORIndex iborCZK = new org.drip.market.definition.IBORIndex
				("CZK-PRIBOR", "PRIBOR", "CZK", "Act/360", "CZK", 2, "", "",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("CZK", iborCZK);

			_mapNamedIBORIndex.put ("CZK-LIBOR", iborCZK);

			_mapNamedIBORIndex.put ("CZK-PRIBOR", iborCZK);

			org.drip.market.definition.IBORIndex iborDKK = new org.drip.market.definition.IBORIndex
				("DKK-CIBOR", "CIBOR", "DKK", "Act/360", "DKK", 2, "1W", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("DKK", iborDKK);

			_mapNamedIBORIndex.put ("DKK-CIBOR", iborDKK);

			_mapNamedIBORIndex.put ("DKK-LIBOR", iborDKK);

			org.drip.market.definition.IBORIndex iborEUR = new org.drip.market.definition.IBORIndex
				("EUR-EURIBOR", "EURIBOR", "EUR", "Act/360", "EUR", 2, "1W", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("EUR", iborEUR);

			_mapNamedIBORIndex.put ("EUR-EURIBOR", iborEUR);

			org.drip.market.definition.IBORIndex iborEUR2 = new org.drip.market.definition.IBORIndex
				("EUR-EURIBOR", "EURIBOR", "EUR", "Act/365", "EUR", 2, "1W", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapNamedIBORIndex.put ("EUR-EURIBOR2", iborEUR2);

			org.drip.market.definition.IBORIndex iborEUR3 = new org.drip.market.definition.IBORIndex
				("EUR-LIBOR", "LIBOR", "EUR", "Act/360", "EUR", 2, "ON", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapNamedIBORIndex.put ("EUR-LIBOR", iborEUR3);

			org.drip.market.definition.IBORIndex iborEUR4 = new org.drip.market.definition.IBORIndex
				("EUR-LIBOR", "LIBOR", "EUR", "Act/360", "EUR", 0, "ON", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapNamedIBORIndex.put ("EUR-LIBOR2", iborEUR4);

			org.drip.market.definition.IBORIndex iborGBP = new org.drip.market.definition.IBORIndex
				("GBP-LIBOR", "LIBOR", "GBP", "Act/365", "GBP", 0, "ON", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("GBP", iborGBP);

			_mapNamedIBORIndex.put ("GBP-LIBOR", iborGBP);

			org.drip.market.definition.IBORIndex iborHKD = new org.drip.market.definition.IBORIndex
				("HKD-HIBOR", "HIBOR", "HKD", "Act/365", "HKD", 2, "1M", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("HKD", iborHKD);

			_mapNamedIBORIndex.put ("HKD-HIBOR", iborHKD);

			_mapNamedIBORIndex.put ("HKD-LIBOR", iborHKD);

			org.drip.market.definition.IBORIndex iborHUF = new org.drip.market.definition.IBORIndex
				("HUF-BUBOR", "BUBOR", "HUF", "Act/360", "HUF", 2, "", "",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("HUF", iborHUF);

			_mapNamedIBORIndex.put ("HUF-BUBOR", iborHUF);

			_mapNamedIBORIndex.put ("HUF-LIBOR", iborHUF);

			org.drip.market.definition.IBORIndex iborILS = new org.drip.market.definition.IBORIndex
				("ILS-LIBOR", "LIBOR", "ILS", "Act/360", "ILS", 2, "", "",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("ILS", iborILS);

			_mapNamedIBORIndex.put ("ILS-LIBOR", iborILS);

			org.drip.market.definition.IBORIndex iborIDR = new org.drip.market.definition.IBORIndex
				("IDR-IDRFIX", "IDRFIX", "IDR", "Act/360", "IDR", 2, "", "",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("IDR", iborIDR);

			_mapNamedIBORIndex.put ("IDR-IDRFIX", iborIDR);

			_mapNamedIBORIndex.put ("IDR-LIBOR", iborIDR);

			org.drip.market.definition.IBORIndex iborINR = new org.drip.market.definition.IBORIndex
				("INR-MIFOR", "MIFOR", "INR", "Act/365", "INR", 2, "", "",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("INR", iborINR);

			_mapNamedIBORIndex.put ("INR-LIBOR", iborINR);

			_mapNamedIBORIndex.put ("INR-MIFOR", iborINR);

			org.drip.market.definition.IBORIndex iborJPY = new org.drip.market.definition.IBORIndex
				("JPY-LIBOR", "LIBOR", "JPY", "Act/360", "JPY", 2, "ON", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("JPY", iborJPY);

			_mapNamedIBORIndex.put ("JPY-LIBOR", iborJPY);

			org.drip.market.definition.IBORIndex iborJPYTIBOR = new org.drip.market.definition.IBORIndex
				("JPY-Japan TIBOR", "TIBOR", "JPY", "Act/365", "JPY", 2, "1W", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapNamedIBORIndex.put ("JPY-TIBOR", iborJPYTIBOR);

			_mapNamedIBORIndex.put ("JPY-TIBOR", iborJPYTIBOR);

			org.drip.market.definition.IBORIndex iborJPYEuroyen = new org.drip.market.definition.IBORIndex
				("JPY-Euroyen TIBOR", "TIBOR", "JPY", "Act/360", "JPY", 2, "1W", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapNamedIBORIndex.put ("JPY-EUROYEN", iborJPYEuroyen);

			_mapNamedIBORIndex.put ("JPY-EURTIBOR", iborJPYEuroyen);

			org.drip.market.definition.IBORIndex iborKRW = new org.drip.market.definition.IBORIndex
				("KRW-LIBOR", "LIBOR", "KRW", "Act/365", "KRW", 1, "ON", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("KRW", iborKRW);

			_mapNamedIBORIndex.put ("KRW-LIBOR", iborKRW);

			org.drip.market.definition.IBORIndex iborMXN = new org.drip.market.definition.IBORIndex
				("MXN-LIBOR", "LIBOR", "MXN", "Act/360", "MXN", 2, "ON", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("MXN", iborMXN);

			_mapNamedIBORIndex.put ("MXN-LIBOR", iborMXN);

			org.drip.market.definition.IBORIndex iborMYR = new org.drip.market.definition.IBORIndex
				("MYR-LIBOR", "LIBOR", "MYR", "Act/365", "MYR", 0, "ON", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("MYR", iborMYR);

			_mapNamedIBORIndex.put ("MYR-LIBOR", iborMYR);

			org.drip.market.definition.IBORIndex iborNOK = new org.drip.market.definition.IBORIndex
				("NOK-NIBOR", "NIBOR", "NOK", "Act/360", "NOK", 2, "", "",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("NOK", iborNOK);

			_mapNamedIBORIndex.put ("NOK-LIBOR", iborNOK);

			_mapNamedIBORIndex.put ("NOK-NIBOR", iborNOK);

			org.drip.market.definition.IBORIndex iborNZD = new org.drip.market.definition.IBORIndex
				("NZD-BBR", "BBR", "NZD", "Act/365", "NZD", 0, "", "",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("NZD", iborNZD);

			_mapNamedIBORIndex.put ("NZD-BBR", iborNZD);

			_mapNamedIBORIndex.put ("NZD-BKBM", iborNZD);

			_mapNamedIBORIndex.put ("NZD-LIBOR", iborNZD);

			org.drip.market.definition.IBORIndex iborPLN = new org.drip.market.definition.IBORIndex
				("PLN-WIBOR", "WIBOR", "PLN", "Act/365", "PLN", 2, "", "",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("PLN", iborPLN);

			_mapNamedIBORIndex.put ("PLN-LIBOR", iborPLN);

			_mapNamedIBORIndex.put ("PLN-WIBOR", iborPLN);

			org.drip.market.definition.IBORIndex iborRMB = new org.drip.market.definition.IBORIndex
				("RMB-SHIBOR", "SHIBOR", "RMB", "Act/360", "RMB", 0, "ON", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("RMB", iborRMB);

			_mapNamedIBORIndex.put ("RMB-LIBOR", iborRMB);

			_mapNamedIBORIndex.put ("RMB-SHIBOR", iborRMB);

			org.drip.market.definition.IBORIndex iborSEK = new org.drip.market.definition.IBORIndex
				("SEK-STIBOR", "STIBOR", "SEK", "Act/360", "SEK", 2, "", "",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("SEK", iborSEK);

			_mapNamedIBORIndex.put ("SEK-LIBOR", iborSEK);

			_mapNamedIBORIndex.put ("SEK-STIBOR", iborSEK);

			org.drip.market.definition.IBORIndex iborSGD = new org.drip.market.definition.IBORIndex
				("SGD-SIBOR", "SIBOR", "SGD", "Act/365", "SGD", 2, "", "",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("SGD", iborSGD);

			_mapNamedIBORIndex.put ("SGD-LIBOR", iborSGD);

			_mapNamedIBORIndex.put ("SGD-SIBOR", iborSGD);

			_mapNamedIBORIndex.put ("SGD-SOR", iborSGD);

			org.drip.market.definition.IBORIndex iborSKK = new org.drip.market.definition.IBORIndex
				("SKK-BRIBOR", "BRIBOR", "SKK", "Act/360", "SKK", 2, "", "",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("SKK", iborSKK);

			_mapNamedIBORIndex.put ("SKK-BRIBOR", iborSKK);

			_mapNamedIBORIndex.put ("SKK-LIBOR", iborSKK);

			org.drip.market.definition.IBORIndex iborTHB = new org.drip.market.definition.IBORIndex
				("THB-LIBOR", "LIBOR", "THB", "Act/365", "THB", 2, "ON", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("THB", iborTHB);

			_mapNamedIBORIndex.put ("THB-LIBOR", iborTHB);

			org.drip.market.definition.IBORIndex iborTRY = new org.drip.market.definition.IBORIndex
				("TRY-LIBOR", "LIBOR", "TRY", "Act/360", "TRY", 2, "ON", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("TRY", iborTRY);

			_mapNamedIBORIndex.put ("TRY-LIBOR", iborTRY);

			org.drip.market.definition.IBORIndex iborTWD = new org.drip.market.definition.IBORIndex
				("TWD-LIBOR", "LIBOR", "TWD", "Act/365", "TWD", 2, "ON", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("TWD", iborTWD);

			_mapNamedIBORIndex.put ("TWD-LIBOR", iborTWD);

			org.drip.market.definition.IBORIndex iborUSD = new org.drip.market.definition.IBORIndex
				("USD-LIBOR", "LIBOR", "USD", "Act/360", "USD", 2, "ON", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("USD", iborUSD);

			_mapNamedIBORIndex.put ("USD-LIBOR", iborUSD);

			org.drip.market.definition.IBORIndex iborZAR = new org.drip.market.definition.IBORIndex
				("ZAR-JIBAR", "JIBAR", "ZAR", "Act/365", "ZAR", 0, "1M", "12M",
					org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			_mapJurisdictionIBORIndex.put ("ZAR", iborZAR);

			_mapNamedIBORIndex.put ("ZAR-JIBAR", iborZAR);

			_mapNamedIBORIndex.put ("ZAR-LIBOR", iborZAR);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the IBOR Index from the Jurisdiction Name
	 * 
	 * @param strName The IBOR Index Jurisdiction Name
	 * 
	 * @return The IBOR Index
	 */

	public static final org.drip.market.definition.IBORIndex IndexFromJurisdiction (
		final java.lang.String strName)
	{
		return _mapJurisdictionIBORIndex.containsKey (strName) ? _mapJurisdictionIBORIndex.get (strName) :
			null;
	}

	/**
	 * Retrieve the IBOR Index from the Index Name
	 * 
	 * @param strName The IBOR Index Index Name
	 * 
	 * @return The IBOR Index
	 */

	public static final org.drip.market.definition.IBORIndex IndexFromName (
		final java.lang.String strName)
	{
		return _mapNamedIBORIndex.containsKey (strName) ? _mapNamedIBORIndex.get (strName) : null;
	}
}
