
package org.drip.template.state;

import org.drip.analytics.date.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;

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
 * <i>FundingState</i> sets up the Calibration of the Funding Latent State and examine the Emitted Metrics.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/README.md">Pricing/Risk Templates for Fixed Income Component Products</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/state/README.md">Standard Latent State Construction Template</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FundingState {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strCurrency = "USD";

		JulianDate dtSpot = DateUtil.Today().addBusDays (
			0,
			strCurrency
		);

		String[] astrDepositMaturityTenor = new String[] {
			"01D",
			"04D",
			"07D",
			"14D",
			"30D",
			"60D"
		};

		double[] adblDepositQuote = new double[] {
			0.0013,		//  1D
			0.0017,		//  2D
			0.0017,		//  7D
			0.0018,		// 14D
			0.0020,		// 30D
			0.0023		// 60D
		};

		double[] adblFuturesQuote = new double[] {
			0.0027,
			0.0032,
			0.0041,
			0.0054,
			0.0077,
			0.0104,
			0.0134,
			0.0160
		};

		String[] astrFixFloatMaturityTenor = new String[] {
			"04Y",
			"05Y",
			"06Y",
			"07Y",
			"08Y",
			"09Y",
			"10Y",
			"11Y",
			"12Y",
			"15Y",
			"20Y",
			"25Y",
			"30Y",
			"40Y",
			"50Y"
		};

		double[] adblFixFloatQuote = new double[] {
			0.0166,		//   4Y
			0.0206,		//   5Y
			0.0241,		//   6Y
			0.0269,		//   7Y
			0.0292,		//   8Y
			0.0311,		//   9Y
			0.0326,		//  10Y
			0.0340,		//  11Y
			0.0351,		//  12Y
			0.0375,		//  15Y
			0.0393,		//  20Y
			0.0402,		//  25Y
			0.0407,		//  30Y
			0.0409,		//  40Y
			0.0409		//  50Y
		};

		MergedDiscountForwardCurve dcFunding = LatentMarketStateBuilder.SmoothFundingCurve (
			dtSpot,
			strCurrency,
			astrDepositMaturityTenor,
			adblDepositQuote,
			"ForwardRate",
			adblFuturesQuote,
			"ForwardRate",
			astrFixFloatMaturityTenor,
			adblFixFloatQuote,
			"SwapRate"
		);

		String strLatentStateLabel = dcFunding.label().fullyQualifiedName();

		System.out.println ("\n\n\t||---------------------------------------------------------------||");

		for (int i = 0; i < adblDepositQuote.length; ++i)
			System.out.println (
				"\t||  " + strLatentStateLabel +
				" |  DEPOSIT  | " + astrDepositMaturityTenor[i] + "  | " +
				FormatUtil.FormatDouble (adblDepositQuote[i], 1, 4, 1.) +
				" | Forward Rate | " +
				FormatUtil.FormatDouble (dcFunding.df (astrDepositMaturityTenor[i]), 1, 6, 1.) +
				"  ||"
			);

		System.out.println ("\t||---------------------------------------------------------------||");

		System.out.println ("\n\n\t||--------------------------------------------------------||");

		for (int i = 0; i < adblFuturesQuote.length; ++i)
			System.out.println (
				"\t||  " + strLatentStateLabel + " |  FUTURES  | " +
				FormatUtil.FormatDouble (adblFuturesQuote[i], 1, 4, 1.) +
				" | Forward Rate | " +
				FormatUtil.FormatDouble (dcFunding.df ((3 + 3 * i) + "M"), 1, 6, 1.) +
				"  ||"
			);

		System.out.println ("\t||--------------------------------------------------------||");

		System.out.println ("\n\n\t||--------------------------------------------------------------||");

		for (int i = 0; i < adblFixFloatQuote.length; ++i)
			System.out.println (
				"\t||  " + strLatentStateLabel +
				" |  FIX FLOAT  | " + astrFixFloatMaturityTenor[i] + "  | " +
				FormatUtil.FormatDouble (adblFixFloatQuote[i], 1, 4, 1.) +
				" | Swap Rate | " +
				FormatUtil.FormatDouble (dcFunding.df (astrFixFloatMaturityTenor[i]), 1, 6, 1.) +
				"  ||"
			);

		System.out.println ("\t||--------------------------------------------------------------||\n");
	}
}
