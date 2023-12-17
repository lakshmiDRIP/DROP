
package org.drip.template.state;

import org.drip.analytics.date.*;
import org.drip.product.params.CurrencyPair;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.fx.FXCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>FXState</i> sets up the Calibration and the Construction of the FX Latent State and examine the Emitted
 * Metrics.
 *
 *  <br><br><br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/README.md">Pricing/Risk Templates for Fixed Income Component Products</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/state/README.md">Standard Latent State Construction Template</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FXState {

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Argument Array
	 * 
	 * @throws Exception Propagate the Exception Upwards
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		CurrencyPair currencyPair = new CurrencyPair (
			"EUR",
			"USD",
			"USD",
			10000.
		);

		JulianDate spotDate = DateUtil.Today().addBusDays (0, currencyPair.denomCcy());

		double fxSpot = 1.1013;

		String[] maturityTenorArray = new String[] {
			"1D",
			"2D",
			"3D",
			"1W",
			"2W",
			"3W",
			"1M",
			"2M",
			"3M",
			"6M",
			"9M"
		};

		double[] fxForwardArray = new double[] {
			1.1011,		// "1D"
			1.1007,		// "2D"
			1.0999,		// "3D"
			1.0976,		// "1W"
			1.0942,		// "2W"
			1.0904,		// "3W"
			1.0913,		// "1M"
			1.0980,		// "2M"
			1.1088,		// "3M"
			1.1115,		// "6M"
			1.1011		// "9M"
		};

		FXCurve fxForwardCurve = LatentMarketStateBuilder.SmoothFXCurve (
			spotDate,
			currencyPair,
			maturityTenorArray,
			fxForwardArray,
			"Outright",
			fxSpot
		);

		String latentStateLabel = fxForwardCurve.label().fullyQualifiedName();

		System.out.println ("\n\n\t||--------------------------------------------------------------||");

		for (int i = 0; i < fxForwardArray.length; ++i)
			System.out.println (
				"\t||  " + latentStateLabel + " |  FX FORWARD  | " +
				maturityTenorArray[i] + " | " + FormatUtil.FormatDouble (fxForwardArray[i], 1, 4, 1.) +
				" | Outright | " +
				FormatUtil.FormatDouble (fxForwardCurve.fx (maturityTenorArray[i]), 1, 4, 1.) +
				"  ||"
			);

		System.out.println ("\t||--------------------------------------------------------------||\n");
	}
}
