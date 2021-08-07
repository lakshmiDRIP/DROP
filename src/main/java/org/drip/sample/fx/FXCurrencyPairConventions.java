
package org.drip.sample.fx;

import org.drip.market.definition.FXSettingContainer;
import org.drip.service.env.EnvManager;

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
 * <i>FXCurrencyPairConventions</i> demonstrates the accessing of the Standard FX Currency Order and Currency
 * Pair Conventions.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fx/README.md">Smooth Shape Preserving FX Curve</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FXCurrencyPairConventions {

	private static final void CurrencyOrder (
		final String strCurrency)
		throws Exception
	{
		System.out.println ("\t|     " + strCurrency + "   " +
			FXSettingContainer.CurrencyOrder (
				strCurrency
			) + "    |"
		);
	}

	private static final void CurrencyPairInfo (
		final String strCurrency1,
		final String strCurrency2)
		throws Exception
	{
		System.out.println ("\t|  " + strCurrency1 + "/" + strCurrency2 + " => " +
			FXSettingContainer.CurrencyPair (
				strCurrency1,
				strCurrency2
			)
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		System.out.println ("\t|----------------|");

		System.out.println ("\t| CURRENCY ORDER |");

		System.out.println ("\t|----------------|");

		CurrencyOrder ("AUD");

		CurrencyOrder ("CAD");

		CurrencyOrder ("CHF");

		CurrencyOrder ("EUR");

		CurrencyOrder ("GBP");

		CurrencyOrder ("JPY");

		CurrencyOrder ("NZD");

		CurrencyOrder ("USD");

		CurrencyOrder ("ZAR");

		System.out.println ("\t|----------------|\n\n");

		System.out.println ("\t|---------------------------------------|");

		System.out.println ("\t|     PAIR    NUM  DENOM  BASE   FACTOR |");

		System.out.println ("\t|---------------------------------------|");

		CurrencyPairInfo ("AUD", "EUR");

		CurrencyPairInfo ("AUD", "USD");

		CurrencyPairInfo ("EUR", "GBP");

		CurrencyPairInfo ("EUR", "JPY");

		CurrencyPairInfo ("EUR", "USD");

		CurrencyPairInfo ("GBP", "JPY");

		CurrencyPairInfo ("GBP", "USD");

		CurrencyPairInfo ("USD", "BRL");

		CurrencyPairInfo ("USD", "CAD");

		CurrencyPairInfo ("USD", "CHF");

		CurrencyPairInfo ("USD", "CNY");

		CurrencyPairInfo ("USD", "EGP");

		CurrencyPairInfo ("USD", "HUF");

		CurrencyPairInfo ("USD", "INR");

		CurrencyPairInfo ("USD", "JPY");

		CurrencyPairInfo ("USD", "KRW");

		CurrencyPairInfo ("USD", "MXN");

		CurrencyPairInfo ("USD", "PLN");

		CurrencyPairInfo ("USD", "TRY");

		CurrencyPairInfo ("USD", "TWD");

		CurrencyPairInfo ("USD", "ZAR");

		System.out.println ("\t|---------------------------------------|");

		EnvManager.TerminateEnv();
	}
}
