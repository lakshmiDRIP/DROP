
package org.drip.market.exchange;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>TreasuryFuturesOptionContainer</i> holds the Details of the Treasury Futures Options Contracts.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Market</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/exchange">Exchange</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryFuturesOptionContainer {
	private static java.util.Map<java.lang.String, org.drip.market.exchange.TreasuryFuturesOptionConvention>
		_mapFuturesOptions = null;

	/**
	 * Initialize the Treasury Futures Options Convention Container with the Conventions
	 * 
	 * @return TRUE - The Treasury Futures Options Convention Container successfully initialized
	 */

	public static final boolean Init()
	{
		if (null != _mapFuturesOptions) return true;

		_mapFuturesOptions = new java.util.TreeMap<java.lang.String,
			org.drip.market.exchange.TreasuryFuturesOptionConvention>();

		try {
			org.drip.product.params.LastTradingDateSetting[] aLTDS = new
				org.drip.product.params.LastTradingDateSetting[] {new
					org.drip.product.params.LastTradingDateSetting
						(org.drip.product.params.LastTradingDateSetting.MID_CURVE_OPTION, "2D",
							java.lang.Integer.MIN_VALUE), new org.drip.product.params.LastTradingDateSetting
								(org.drip.product.params.LastTradingDateSetting.MID_CURVE_OPTION_QUARTERLY,
									"", java.lang.Integer.MIN_VALUE)};

			_mapFuturesOptions.put ("USD-TREASURY-BOND-ULTRA", new
				org.drip.market.exchange.TreasuryFuturesOptionConvention (new java.lang.String[] {"OUB",
					"OUL"}, "USD-TREASURY-BOND-ULTRA", 100000., true, aLTDS));

			_mapFuturesOptions.put ("USD-TREASURY-BOND-30Y", new
				org.drip.market.exchange.TreasuryFuturesOptionConvention (new java.lang.String[] {"OZB",
					"CG-PG"}, "USD-TREASURY-BOND-30Y", 100000., true, aLTDS));

			_mapFuturesOptions.put ("USD-TREASURY-NOTE-10Y", new
				org.drip.market.exchange.TreasuryFuturesOptionConvention (new java.lang.String[] {"OZN",
					"TC-TP"}, "USD-TREASURY-NOTE-10Y", 100000., true, aLTDS));

			_mapFuturesOptions.put ("USD-TREASURY-NOTE-5Y", new
				org.drip.market.exchange.TreasuryFuturesOptionConvention (new java.lang.String[] {"OZF",
					"FL-FP"}, "USD-TREASURY-NOTE-5Y", 100000., true, aLTDS));

			_mapFuturesOptions.put ("USD-TREASURY-NOTE-2Y", new
				org.drip.market.exchange.TreasuryFuturesOptionConvention (new java.lang.String[] {"OZT",
					"TUC-TUP"}, "USD-TREASURY-NOTE-2Y", 200000., true, aLTDS));

			_mapFuturesOptions.put ("EUR-EURO-BUXL-30Y", new
				org.drip.market.exchange.TreasuryFuturesOptionConvention (new java.lang.String[] {"BUXL"},
					"EUR-EURO-BUXL-30Y", 100000., false, aLTDS));

			_mapFuturesOptions.put ("EUR-EURO-BUND-10Y", new
				org.drip.market.exchange.TreasuryFuturesOptionConvention (new java.lang.String[] {"BUND"},
					"EUR-EURO-BUND-10Y", 100000., false, aLTDS));

			_mapFuturesOptions.put ("EUR-EURO-BOBL-5Y", new
				org.drip.market.exchange.TreasuryFuturesOptionConvention (new java.lang.String[] {"BOBL"},
					"EUR-EURO-BOBL-10Y", 100000., false, aLTDS));

			_mapFuturesOptions.put ("EUR-EURO-SCHATZ-2Y", new
				org.drip.market.exchange.TreasuryFuturesOptionConvention (new java.lang.String[] {"SCHATZ"},
					"EUR-EURO-SCHATZ-10Y", 100000., false, aLTDS));

			_mapFuturesOptions.put ("EUR-TREASURY-BONO-10Y", new
				org.drip.market.exchange.TreasuryFuturesOptionConvention (new java.lang.String[] {"BONO"},
					"EUR-TREASURY-BONO-10Y", 100000., false, aLTDS));

			return true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Retrieve the Treasury Futures Option Convention from the Contract Name
	 * 
	 * @param strContractName The Options Contract Name
	 * 
	 * @return The Treasury Futures Option Convention
	 */

	public static final org.drip.market.exchange.TreasuryFuturesOptionConvention FromContract (
		final java.lang.String strContractName)
	{
		return null == strContractName || strContractName.isEmpty() || !_mapFuturesOptions.containsKey
			(strContractName) ? null : _mapFuturesOptions.get (strContractName);
	}
}
