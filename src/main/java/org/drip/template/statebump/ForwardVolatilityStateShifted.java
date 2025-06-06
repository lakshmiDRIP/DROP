
package org.drip.template.statebump;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.Component;
import org.drip.product.fra.FRAStandardCapFloor;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.*;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.forward.ForwardCurve;
import org.drip.state.identifier.ForwardLabel;
import org.drip.state.volatility.VolatilityCurve;

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
 * <i>ForwardVolatilityStateShifted</i> demonstrates the Generation and the Usage of Tenor Bumped Forward
 * Volatility Curves.
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

public class ForwardVolatilityStateShifted {

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

		String fraTenor = "3M";
		String currency = "GBP";
		double bump = 0.0000001;
		boolean isBumpProportional = false;

		JulianDate spotDate = DateUtil.CreateFromYMD (
			2017,
			DateUtil.DECEMBER,
			21
		);

		ForwardLabel forwardLabel = ForwardLabel.Create (currency, fraTenor);

		MergedDiscountForwardCurve fundingCurve = LatentMarketStateBuilder.SmoothFundingCurve (
			spotDate,
			currency,
			new String[] {
				 "30D",
				 "60D",
				 "91D",
				"182D",
				"273D"
			},
			new double[] {
				0.0668750,	//  30D
				0.0675000,	//  60D
				0.0678125,	//  91D
				0.0712500,	// 182D
				0.0750000	// 273D
			},
			"ForwardRate",
			null,
			"ForwardRate",
			new String[] {
				"2Y",
				"3Y",
				"4Y",
				"5Y",
				"7Y",
				"10Y"
			},
			new double[] {
				0.08265,    //  2Y
				0.08550,    //  3Y
				0.08655,    //  4Y
				0.08770,    //  5Y
				0.08910,    //  7Y
				0.08920     // 10Y
			},
			"SwapRate"
		);

		String[] maturityTenorArray = new String[] {
			"01Y",
			"02Y",
			"03Y",
			"04Y",
			"05Y",
			"07Y",
			"10Y"
		};

		double[] strikeArray = new double[] {
			0.0788, //  "1Y",
			0.0839, // 	"2Y",
			0.0864, //  "3Y",
			0.0869, //  "4Y",
			0.0879, //  "5Y",
			0.0890, //  "7Y",
			0.0889  // "10Y"
		};

		double[] priceArray = new double[] {
			0.0017, //  "1Y",
			0.0132, // 	"2Y",
			0.0234, //  "3Y",
			0.0343, //  "4Y",
			0.0491, //  "5Y",
			0.0968, //  "7Y"
			0.1625  // "10Y"
		};

		ForwardCurve forwardCurve = fundingCurve.nativeForwardCurve (fraTenor);

		Map<String, VolatilityCurve> bumpedForwardVolatilityCurveMap =
			LatentMarketStateBuilder.BumpedForwardVolatilityCurve (
				spotDate,
				forwardLabel,
				true,
				maturityTenorArray,
				strikeArray,
				priceArray,
				"Price",
				fundingCurve,
				forwardCurve,
				bump,
				isBumpProportional
			);

		FRAStandardCapFloor[] fraCapFloorArray = OTCInstrumentBuilder.CapFloor (
			spotDate,
			forwardLabel,
			maturityTenorArray,
			strikeArray,
			true
		);

		System.out.println ("\n\t|---------------------------------------------------------------------------------------------------------------||");

		ValuationParams valuationParams = ValuationParams.Spot (spotDate.julian());

		for (Map.Entry<String, VolatilityCurve> bumpedForwardVolatilityCurveMapEntry :
			bumpedForwardVolatilityCurveMap.entrySet()) {
			String key = bumpedForwardVolatilityCurveMapEntry.getKey();

			if (!key.startsWith ("capfloor")) {
				continue;
			}

			CurveSurfaceQuoteContainer marketParamsContainer = new CurveSurfaceQuoteContainer();

			marketParamsContainer.setFundingState (fundingCurve);

			marketParamsContainer.setForwardState (forwardCurve);

			marketParamsContainer.setForwardVolatility (bumpedForwardVolatilityCurveMapEntry.getValue());

			System.out.print ("\t|  [" + key + "] => ");

			for (Component fraCapFloor : fraCapFloorArray)
				System.out.print (
					FormatUtil.FormatDouble (
						fraCapFloor.measureValue (
							valuationParams,
							null,
							marketParamsContainer,
							null,
							"Price"
						),
						1,
						8,
						1.
					) + " |"
				);

			System.out.print ("|\n");
		}

		System.out.println ("\t|---------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\t|-------------------------------------------------||");

		CurveSurfaceQuoteContainer baseMarketParamsContainer = new CurveSurfaceQuoteContainer();

		baseMarketParamsContainer.setFundingState (fundingCurve);

		baseMarketParamsContainer.setForwardState (forwardCurve);

		baseMarketParamsContainer.setForwardVolatility (bumpedForwardVolatilityCurveMap.get ("Base"));

		CurveSurfaceQuoteContainer bumpedMarketParamsContainer = new CurveSurfaceQuoteContainer();

		bumpedMarketParamsContainer.setFundingState (fundingCurve);

		bumpedMarketParamsContainer.setForwardState (forwardCurve);

		bumpedMarketParamsContainer.setForwardVolatility (bumpedForwardVolatilityCurveMap.get ("Bump"));

		for (Component fraCapFloor : fraCapFloorArray)
			System.out.println (
				"\t| PRICE => " +
				fraCapFloor.maturityDate() + " | " +
				FormatUtil.FormatDouble (
					fraCapFloor.measureValue (
						valuationParams,
						null,
						baseMarketParamsContainer,
						null,
						"Price"
					),
					1,
					8,
					1.
				) + " | " +
				FormatUtil.FormatDouble (
					fraCapFloor.measureValue (
						valuationParams,
						null,
						bumpedMarketParamsContainer,
						null,
						"Price"
					),
					1,
					8,
					1.
				) + " ||"
			);

		System.out.println ("\t|-------------------------------------------------||");
	}
}
