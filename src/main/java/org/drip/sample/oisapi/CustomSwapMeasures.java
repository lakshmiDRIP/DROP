
package org.drip.sample.oisapi;

import java.util.*;

import org.drip.analytics.date.*;
import org.drip.service.env.EnvManager;
import org.drip.service.product.OvernightIndexSwapAPI;

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
 * <i>CustomSwapMeasures</i> demonstrates the Invocation and Usage of the OIS API.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/oisapi/README.md">OIS Construction and Valuation API</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CustomSwapMeasures {

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		JulianDate dtSpot = DateUtil.Today();

		String strOISCurrency = "USD";
		String strOISTenor = "3W";
		double dblOISCoupon = 0.0043;

		String[] astrOvernightCurveDepositTenor = new String[] {
			"1D"
		};

		double[] adblOvernightCurveDepositQuote = new double[] {
			0.0010
		};

		String[] astrOvernightCurveOISTenor = new String[] {
			"1W",
			"2W",
			"3W",
			"1M",
			"2M",
			"3M",
			"4M",
			"5M",
			"6M",
			"9M",
			"1Y",
			"18M",
			"2Y",
			"3Y",
			"4Y",
			"5Y"
		};

		double[] adblOvernightCurveOISQuote = new double[] {
			0.0020,	// 1W
			0.0028,	// 2W
			0.0043,	// 3W
			0.0064,	// 1M
			0.0086,	// 2M
			0.0109,	// 3M
			0.0133,	// 4M
			0.0154,	// 5M
			0.0171,	// 6M
			0.0210,	// 9M
			0.0231,	// 1Y
			0.0234,	// 18M
			0.0235,	// 2Y
			0.0235,	// 3Y
			0.0237,	// 4Y
			0.0240	// 5Y
		};

		Map<String, Double> mapMeasures = OvernightIndexSwapAPI.ValuationMetrics (
			strOISCurrency,
			strOISTenor,
			dblOISCoupon,
			dtSpot.julian(),
			astrOvernightCurveDepositTenor,
			adblOvernightCurveDepositQuote,
			astrOvernightCurveOISTenor,
			adblOvernightCurveOISQuote,
			false
		);

		Set<String> setstrKeys = mapMeasures.keySet();

		for (String strKey : setstrKeys)
			System.out.println ("\t" + strKey + " => " + mapMeasures.get (strKey));

		EnvManager.TerminateEnv();
	}
}
