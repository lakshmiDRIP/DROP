
package org.drip.sample.fx;

import org.drip.market.definition.FXSettingContainer;
import org.drip.service.env.EnvManager;

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
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>FXCurrencyPairConventions</i> demonstrates the accessing of the Standard FX Currency Order and Currency
 * Pair Conventions.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/fx/README.md">FX Curve Builder</a></li>
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
