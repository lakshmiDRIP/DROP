
package org.drip.product.creator;

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
 * <i>SingleStreamOptionBuilder</i> contains the suite of helper functions for creating the Options Product
 * Instance off of a single stream underlying.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md">Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option AssetClasses</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator/README.md">Streams and Products Construction Utilities</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SingleStreamOptionBuilder {

	/**
	 * Create a Standard Futures Option
	 * 
	 * @param dtEffective Effective date
	 * @param forwardLabel The Forward Label
	 * @param dblStrike The Option Strike
	 * @param strManifestMeasure Measure of the Underlying Component
	 * @param bIsCaplet Is the Futures Option a Caplet? TRUE - YES
	 * @param csp Cash Settle Parameters
	 * 
	 * @return The Standard Futures Option Instance
	 */

	public static final org.drip.product.fra.FRAStandardCapFloorlet FuturesOption (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final double dblStrike,
		final java.lang.String strManifestMeasure,
		final boolean bIsCaplet,
		final org.drip.param.valuation.CashSettleParams csp)
	{
		org.drip.product.fra.FRAStandardComponent fraStandard =
			org.drip.product.creator.SingleStreamComponentBuilder.ForwardRateFutures (dtEffective,
				forwardLabel);

		try {
			return null == fraStandard? null : new org.drip.product.fra.FRAStandardCapFloorlet
				(fraStandard.name() + "::OPT", fraStandard, strManifestMeasure, bIsCaplet, dblStrike, 1., new
					org.drip.product.params.LastTradingDateSetting
						(org.drip.product.params.LastTradingDateSetting.MID_CURVE_OPTION_QUARTERLY, "",
							java.lang.Integer.MIN_VALUE), new org.drip.pricer.option.BlackScholesAlgorithm(),
								csp);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Exchange-traded Standard Futures Option
	 * 
	 * @param dtEffective Effective date
	 * @param forwardLabel The Forward Label
	 * @param dblStrike The Option Strike
	 * @param strManifestMeasure Measure of the Underlying Component
	 * @param bIsCaplet Is the Futures Option a Caplet? TRUE - YES
	 * @param strTradingMode The Trading Mode
	 * @param strExchange The Exchange
	 * 
	 * @return The Standard Futures Option Instance
	 */

	public static final org.drip.product.fra.FRAStandardCapFloorlet ExchangeTradedFuturesOption (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final double dblStrike,
		final java.lang.String strManifestMeasure,
		final boolean bIsCaplet,
		final java.lang.String strTradingMode,
		final java.lang.String strExchange)
	{
		if (null == forwardLabel) return null;

		org.drip.market.exchange.FuturesOptions fo =
			org.drip.market.exchange.FuturesOptionsContainer.ExchangeInfo (forwardLabel.fullyQualifiedName(),
				strTradingMode);

		if (null == fo) return null;

		java.util.Set<java.lang.String> setExchanges = fo.exchanges();

		if (null == setExchanges || !setExchanges.contains (strExchange)) return null;

		org.drip.product.fra.FRAStandardComponent fraStandard =
			org.drip.product.creator.SingleStreamComponentBuilder.ForwardRateFutures (dtEffective,
				forwardLabel);

		try {
			return null == fraStandard ? null : new org.drip.product.fra.FRAStandardCapFloorlet
				(fraStandard.name() + "::OPT", fraStandard, strManifestMeasure, bIsCaplet, dblStrike, 1.,
					fo.ltdsArray (strExchange)[0], new org.drip.pricer.option.BlackScholesAlgorithm(), null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
