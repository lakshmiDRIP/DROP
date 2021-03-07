
package org.drip.market.exchange;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>FuturesOptionsContainer</i> holds the short term futures options contracts.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market">Static Market Fields - the Definitions, the OTC/Exchange Traded Products, and Treasury Settings</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/market/exchange">Deliverable Swap, STIR, Treasury Futures</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FuturesOptionsContainer {
	private static java.util.Map<java.lang.String, org.drip.market.exchange.FuturesOptions>
		_mapFuturesOptions = null;

	/**
	 * Initialize the Overnight Index Container with the Overnight Indexes
	 * 
	 * @return TRUE - The Overnight Index Container successfully initialized with the indexes
	 */

	public static final boolean Init()
	{
		if (null != _mapFuturesOptions) return true;

		_mapFuturesOptions = new
			java.util.TreeMap<java.lang.String, org.drip.market.exchange.FuturesOptions>();

		try {
			org.drip.product.params.LastTradingDateSetting ltdsMidCurveQuarterly = new
				org.drip.product.params.LastTradingDateSetting
					(org.drip.product.params.LastTradingDateSetting.MID_CURVE_OPTION_QUARTERLY, "",
						java.lang.Integer.MIN_VALUE);

			org.drip.product.params.LastTradingDateSetting ltdsMidCurve1M = new
				org.drip.product.params.LastTradingDateSetting
					(org.drip.product.params.LastTradingDateSetting.MID_CURVE_OPTION, "1M",
						java.lang.Integer.MIN_VALUE);

			org.drip.product.params.LastTradingDateSetting ltdsMidCurve2M = new
				org.drip.product.params.LastTradingDateSetting
					(org.drip.product.params.LastTradingDateSetting.MID_CURVE_OPTION, "2M",
						java.lang.Integer.MIN_VALUE);

			org.drip.product.params.LastTradingDateSetting ltdsMidCurve1Y = new
				org.drip.product.params.LastTradingDateSetting
					(org.drip.product.params.LastTradingDateSetting.MID_CURVE_OPTION, "1Y",
						java.lang.Integer.MIN_VALUE);

			org.drip.product.params.LastTradingDateSetting ltdsMidCurve2Y = new
				org.drip.product.params.LastTradingDateSetting
					(org.drip.product.params.LastTradingDateSetting.MID_CURVE_OPTION, "2Y",
						java.lang.Integer.MIN_VALUE);

			org.drip.product.params.LastTradingDateSetting[] s_aLTDSMidCurveAll = new
				org.drip.product.params.LastTradingDateSetting[] {ltdsMidCurveQuarterly, ltdsMidCurve1M,
					ltdsMidCurve2M, ltdsMidCurve1Y, ltdsMidCurve2Y, new
						org.drip.product.params.LastTradingDateSetting
							(org.drip.product.params.LastTradingDateSetting.MID_CURVE_OPTION, "4Y",
								java.lang.Integer.MIN_VALUE)};

			org.drip.market.exchange.FuturesOptions foCHFLIBOR3M_MARGIN = new
				org.drip.market.exchange.FuturesOptions ("CHF-LIBOR-3M", "MARGIN");

			foCHFLIBOR3M_MARGIN.setLDTS ("LIFFE", new org.drip.product.params.LastTradingDateSetting[]
				{ltdsMidCurveQuarterly});

			_mapFuturesOptions.put ("CHF-LIBOR-3M|MARGIN", foCHFLIBOR3M_MARGIN);

			org.drip.market.exchange.FuturesOptions foGBPLIBOR3M_MARGIN = new
				org.drip.market.exchange.FuturesOptions ("GBP-LIBOR-3M", "MARGIN");

			foGBPLIBOR3M_MARGIN.setLDTS ("LIFFE", new org.drip.product.params.LastTradingDateSetting[]
				{ltdsMidCurveQuarterly, ltdsMidCurve1M, ltdsMidCurve2M, ltdsMidCurve2Y});

			_mapFuturesOptions.put ("GBP-LIBOR-3M|MARGIN", foGBPLIBOR3M_MARGIN);

			org.drip.market.exchange.FuturesOptions foEUREURIBOR3M_MARGIN = new
				org.drip.market.exchange.FuturesOptions ("EUR-EURIBOR-3M", "MARGIN");

			foEUREURIBOR3M_MARGIN.setLDTS ("EUREX", new org.drip.product.params.LastTradingDateSetting[]
				{ltdsMidCurveQuarterly, ltdsMidCurve1Y});

			foEUREURIBOR3M_MARGIN.setLDTS ("LIFFE", new org.drip.product.params.LastTradingDateSetting[]
				{ltdsMidCurveQuarterly, ltdsMidCurve1M, ltdsMidCurve2M, ltdsMidCurve2Y});

			_mapFuturesOptions.put ("EUR-EURIBOR-3M|MARGIN", foEUREURIBOR3M_MARGIN);

			org.drip.market.exchange.FuturesOptions foJPYLIBOR3M_PREMIUM = new
				org.drip.market.exchange.FuturesOptions ("JPY-LIBOR-3M", "PREMIUM");

			foJPYLIBOR3M_PREMIUM.setLDTS ("SGX", s_aLTDSMidCurveAll);

			_mapFuturesOptions.put ("JPY-LIBOR-3M|PREMIUM", foJPYLIBOR3M_PREMIUM);

			org.drip.market.exchange.FuturesOptions foJPYTIBOR3M_PREMIUM = new
				org.drip.market.exchange.FuturesOptions ("JPY-TIBOR-3M", "PREMIUM");

			foJPYTIBOR3M_PREMIUM.setLDTS ("SGX", s_aLTDSMidCurveAll);

			_mapFuturesOptions.put ("JPY-TIBOR-3M|PREMIUM", foJPYTIBOR3M_PREMIUM);

			org.drip.market.exchange.FuturesOptions foUSDLIBOR1M_PREMIUM = new
				org.drip.market.exchange.FuturesOptions ("USD-LIBOR-1M", "PREMIUM");

			foUSDLIBOR1M_PREMIUM.setLDTS ("CME", s_aLTDSMidCurveAll);

			_mapFuturesOptions.put ("USD-LIBOR-1M|PREMIUM", foUSDLIBOR1M_PREMIUM);

			org.drip.market.exchange.FuturesOptions foUSDLIBOR3M_MARGIN = new
				org.drip.market.exchange.FuturesOptions ("USD-LIBOR-3M", "MARGIN");

			foUSDLIBOR3M_MARGIN.setLDTS ("LIFFE", new org.drip.product.params.LastTradingDateSetting[]
				{ltdsMidCurveQuarterly, ltdsMidCurve1M, ltdsMidCurve2M});

			_mapFuturesOptions.put ("USD-LIBOR-3M|MARGIN", foUSDLIBOR3M_MARGIN);

			org.drip.market.exchange.FuturesOptions foUSDLIBOR3M_PREMIUM = new
				org.drip.market.exchange.FuturesOptions ("USD-LIBOR-3M", "PREMIUM");

			foUSDLIBOR3M_PREMIUM.setLDTS ("CME", s_aLTDSMidCurveAll);

			foUSDLIBOR3M_PREMIUM.setLDTS ("SGX", s_aLTDSMidCurveAll);

			_mapFuturesOptions.put ("USD-LIBOR-3M|PREMIUM", foUSDLIBOR3M_PREMIUM);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * Retrieve the FuturesOptions Exchange Info
	 * 
	 * @param strFullyQualifiedName Fully Qualified Name
	 * @param strTradingMode Trading Mode
	 * 
	 * @return The FuturesOptions Exchange Info
	 */

	public static final org.drip.market.exchange.FuturesOptions ExchangeInfo (
		final java.lang.String strFullyQualifiedName,
		final java.lang.String strTradingMode)
	{
		if (null == strFullyQualifiedName || strFullyQualifiedName.isEmpty() || null == strTradingMode ||
			strTradingMode.isEmpty() || !_mapFuturesOptions.containsKey (strFullyQualifiedName + "|" +
				strTradingMode))
			return null;

		java.lang.String strFuturesOptionsKey = strFullyQualifiedName + "|" + strTradingMode;

		return !_mapFuturesOptions.containsKey (strFuturesOptionsKey) ? null : _mapFuturesOptions.get
			(strFuturesOptionsKey);
	}
}
