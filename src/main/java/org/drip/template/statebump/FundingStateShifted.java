
package org.drip.template.statebump;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.Component;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.*;
import org.drip.state.discount.MergedDiscountForwardCurve;

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
 * <i>FundingStateShifted</i> generates a Sequence of Tenor Bumped Funding Curves.
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/statebump/README.md">Shifted Latent State Construction Template</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FundingStateShifted {

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

		String currency = "USD";
		double bump = 0.0001;
		boolean isBumpProportional = false;

		JulianDate spotDate = DateUtil.CreateFromYMD (
			2017,
			DateUtil.DECEMBER,
			21
		);

		String[] depositMaturityTenorArray = new String[] {
			"01D",
			"02D",
			"07D",
			"14D",
			"30D",
			"60D"
		};

		double[] depositQuoteArray = new double[] {
			0.0013,		//  1D
			0.0017,		//  2D
			0.0017,		//  7D
			0.0018,		// 14D
			0.0020,		// 30D
			0.0023		// 60D
		};

		double[] futuresQuoteArray = new double[] {
			0.0027,
			0.0032,
			0.0041,
			0.0054,
			0.0077,
			0.0104,
			0.0134,
			0.0160
		};

		String[] fixFloatMaturityTenorArray = new String[] {
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

		double[] fixFloatQuoteArray = new double[] {
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

		Map<String, MergedDiscountForwardCurve> fundingCurveMap = LatentMarketStateBuilder.BumpedFundingCurve (
			spotDate,
			currency,
			depositMaturityTenorArray,
			depositQuoteArray,
			"ForwardRate",
			futuresQuoteArray,
			"ForwardRate",
			fixFloatMaturityTenorArray,
			fixFloatQuoteArray,
			"SwapRate",
			LatentMarketStateBuilder.SMOOTH,
			bump,
			isBumpProportional
		);

		Component[] depositComponentArray = OTCInstrumentBuilder.FundingDeposit (
			spotDate,
			currency,
			depositMaturityTenorArray
		);

		Component[] futuresComponentArray = ExchangeInstrumentBuilder.ForwardRateFuturesPack (
			spotDate,
			futuresQuoteArray.length,
			currency
		);

		Component[] fixFloatComponentArray = OTCInstrumentBuilder.FixFloatStandard (
			spotDate,
			currency,
			"ALL",
			fixFloatMaturityTenorArray,
			"MAIN",
			0.
		);

		ValuationParams valuationParams = ValuationParams.Spot (spotDate.julian());

		System.out.println ("\n\t|-------------------------------------------------------------------------||");

		for (Map.Entry<String, MergedDiscountForwardCurve> fundingCurveMapEntry : fundingCurveMap.entrySet())
		{
			String key = fundingCurveMapEntry.getKey();

			if (!key.startsWith ("deposit")) {
				continue;
			}

			CurveSurfaceQuoteContainer marketParamsContainer = new CurveSurfaceQuoteContainer();

			marketParamsContainer.setFundingState (fundingCurveMapEntry.getValue());

			System.out.print ("\t|  [" + key + "] => ");

			for (Component depositComponent : depositComponentArray)
				System.out.print (
					FormatUtil.FormatDouble (
						depositComponent.measureValue (
							valuationParams,
							null,
							marketParamsContainer,
							null,
							"ForwardRate"
						),
						1,
						4,
						1.
					) + " |"
				);

			System.out.print ("|\n");
		}

		System.out.println ("\t|-------------------------------------------------------------------------||");

		System.out.println ("\n\t|-----------------------------------------------------------------------------------------||");

		for (Map.Entry<String, MergedDiscountForwardCurve> fundingCurveMapEntry : fundingCurveMap.entrySet())
		{
			String key = fundingCurveMapEntry.getKey();

			if (!key.startsWith ("futures")) {
				continue;
			}

			CurveSurfaceQuoteContainer marketParamsContainer = new CurveSurfaceQuoteContainer();

			marketParamsContainer.setFundingState (fundingCurveMapEntry.getValue());

			System.out.print ("\t|  [" + key + "] => ");

			for (Component futuresComponent : futuresComponentArray)
				System.out.print (
					FormatUtil.FormatDouble (
						futuresComponent.measureValue (
							valuationParams,
							null,
							marketParamsContainer,
							null,
							"ForwardRate"
						),
						1,
						4,
						1.
					) + " |"
				);

			System.out.print ("|\n");
		}

		System.out.println ("\t|-----------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|--------------------------------------------------------------------------------------------------------------------------------------------||");

		for (Map.Entry<String, MergedDiscountForwardCurve> fundingCurveMapEntry : fundingCurveMap.entrySet())
		{
			String key = fundingCurveMapEntry.getKey();

			if (!key.startsWith ("fixfloat")) {
				continue;
			}

			CurveSurfaceQuoteContainer marketParamsContainer = new CurveSurfaceQuoteContainer();

			marketParamsContainer.setFundingState (fundingCurveMapEntry.getValue());

			System.out.print ("\t|  [" + key + "] => ");

			for (Component fixFloatComponent : fixFloatComponentArray)
				System.out.print (
					FormatUtil.FormatDouble (
						fixFloatComponent.measureValue (
							valuationParams,
							null,
							marketParamsContainer,
							null,
							"SwapRate"
						),
						1,
						4,
						1.
					) + " |"
				);

			System.out.print ("|\n");
		}

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|-----------------------------------------------------||");

		CurveSurfaceQuoteContainer baseMarketParamsContainer = new CurveSurfaceQuoteContainer();

		baseMarketParamsContainer.setFundingState (fundingCurveMap.get ("Base"));

		CurveSurfaceQuoteContainer bumpedMarketParamsContainer = new CurveSurfaceQuoteContainer();

		bumpedMarketParamsContainer.setFundingState (fundingCurveMap.get ("Bump"));

		for (Component depositComponent : depositComponentArray)
			System.out.println (
				"\t| FORWARD RATE  => " +
				depositComponent.maturityDate() + " | " +
				FormatUtil.FormatDouble (
					depositComponent.measureValue (
						valuationParams,
						null,
						baseMarketParamsContainer,
						null,
						"ForwardRate"
					),
					1,
					6,
					1.
				) + " | " +
				FormatUtil.FormatDouble (
					depositComponent.measureValue (
						valuationParams,
						null,
						bumpedMarketParamsContainer,
						null,
						"ForwardRate"
					),
					1,
					6,
					1.
				) + " ||"
			);

		for (Component futuresComponent : futuresComponentArray)
			System.out.println (
				"\t| FORWARD RATE  => " +
				futuresComponent.maturityDate() + " | " +
				FormatUtil.FormatDouble (
					futuresComponent.measureValue (
						valuationParams,
						null,
						baseMarketParamsContainer,
						null,
						"ForwardRate"
					),
					1,
					6,
					1.
				) + " | " +
				FormatUtil.FormatDouble (
					futuresComponent.measureValue (
						valuationParams,
						null,
						bumpedMarketParamsContainer,
						null,
						"ForwardRate"
					),
					1,
					6,
					1.
				) + " ||"
			);

		for (Component fixFloatComponent : fixFloatComponentArray)
			System.out.println (
				"\t|  SWAP   RATE  => " +
				fixFloatComponent.maturityDate() + " | " +
				FormatUtil.FormatDouble (
					fixFloatComponent.measureValue (
						valuationParams,
						null,
						baseMarketParamsContainer,
						null,
						"SwapRate"
					),
					1,
					6,
					1.
				) + " | " +
				FormatUtil.FormatDouble (
					fixFloatComponent.measureValue (
						valuationParams,
						null,
						bumpedMarketParamsContainer,
						null,
						"SwapRate"
					),
					1,
					6,
					1.
				) + " ||"
			);

		System.out.println ("\t|-----------------------------------------------------||");
	}
}
