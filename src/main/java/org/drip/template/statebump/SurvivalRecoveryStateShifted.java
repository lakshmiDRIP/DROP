
package org.drip.template.statebump;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.Component;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.*;
import org.drip.state.credit.CreditCurve;
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
 * <i>SurvivalRecoveryStateShifted</i> demonstrates the Generation of the Tenor Bumped Credit Curves.
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

public class SurvivalRecoveryStateShifted {

	private static final MergedDiscountForwardCurve OvernightCurve (
		final JulianDate spotDate,
		final String currency)
		throws Exception
	{
		String[] depositMaturityTenorArray = new String[] {
			"1D",
			// "2D",
			"3D"
		};

		double[] depositQuoteArray = new double[] {
			0.0004,		// 1D
			// 0.0004,		// 2D
			0.0004		// 3D
		};

		String[] shortEndOISMaturityTenorArray = new String[] {
			"1W",
			"2W",
			"3W",
			"1M"
		};

		double[] shortEndOISQuoteArray = new double[] {
			0.00070,    //   1W
			0.00069,    //   2W
			0.00078,    //   3W
			0.00074     //   1M
		};

		String[] oisFuturesEffectiveTenorArray = new String[] {
			"1M",
			"2M",
			"3M",
			"4M",
			"5M"
		};

		String[] oisFuturesMaturityTenorArray = new String[] {
			"1M",
			"1M",
			"1M",
			"1M",
			"1M"
		};

		double[] oisFuturesQuoteArray = new double[] {
			 0.00046,    //   1M x 1M
			 0.00016,    //   2M x 1M
			-0.00007,    //   3M x 1M
			-0.00013,    //   4M x 1M
			-0.00014     //   5M x 1M
		};

		String[] longEndOISMaturityTenorArray = new String[] {
			"15M",
			"18M",
			"21M",
			"02Y",
			"03Y",
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
			"30Y"
		};

		double[] longEndOISQuoteArray = new double[] {
			0.00002,    //  15M
			0.00008,    //  18M
			0.00021,    //  21M
			0.00036,    //   2Y
			0.00127,    //   3Y
			0.00274,    //   4Y
			0.00456,    //   5Y
			0.00647,    //   6Y
			0.00827,    //   7Y
			0.00996,    //   8Y
			0.01147,    //   9Y
			0.01280,    //  10Y
			0.01404,    //  11Y
			0.01516,    //  12Y
			0.01764,    //  15Y
			0.01939,    //  20Y
			0.02003,    //  25Y
			0.02038     //  30Y
		};

		return LatentMarketStateBuilder.SmoothOvernightCurve (
			spotDate,
			currency,
			depositMaturityTenorArray,
			depositQuoteArray,
			"Rate",
			shortEndOISMaturityTenorArray,
			shortEndOISQuoteArray,
			"SwapRate",
			oisFuturesEffectiveTenorArray,
			oisFuturesMaturityTenorArray,
			oisFuturesQuoteArray,
			"SwapRate",
			longEndOISMaturityTenorArray,
			longEndOISQuoteArray,
			"SwapRate"
		);
	}

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
		/*
		 * Initialize the Credit Analytics Library
		 */

		EnvManager.InitEnv ("");

		String currency = "EUR";
		double bump = 1.0;
		boolean isBumpProportional = false;

		JulianDate spotDate = DateUtil.CreateFromYMD (
			2017,
			DateUtil.DECEMBER,
			21
		);

		MergedDiscountForwardCurve oisCurve = OvernightCurve (spotDate, currency);

		String[] cdsMaturityTenorArray = new String[] {
			"06M",
			"01Y",
			"02Y",
			"03Y",
			"04Y",
			"05Y",
			"07Y",
			"10Y"
		};

		double[] cdsParSpreadArray = new double[] {
			 60.,	//  6M
			 68.,	//  1Y
			 88.,	//  2Y
			102.,	//  3Y
			121.,	//  4Y
			138.,	//  5Y
			168.,	//  7Y
			188.	// 10Y
		};

		Map<String, CreditCurve> bumpedCreditCurveMap = LatentMarketStateBuilder.BumpedCreditCurve (
			spotDate,
			"QTX",
			cdsMaturityTenorArray,
			cdsParSpreadArray,
			cdsParSpreadArray,
			"FairPremium",
			oisCurve,
			bump,
			isBumpProportional
		);

		Component[] cdsComponentArray = OTCInstrumentBuilder.CDS (
			spotDate,
			cdsMaturityTenorArray,
			cdsParSpreadArray,
			currency,
			"QTX"
		);

		System.out.println ("\n\t|----------------------------------------------------------------------------||");

		ValuationParams valuationParams = ValuationParams.Spot (spotDate.julian());

		for (Map.Entry<String, CreditCurve> bumpedCreditCurveMapEntry : bumpedCreditCurveMap.entrySet()) {
			String key = bumpedCreditCurveMapEntry.getKey();

			if (!key.startsWith ("cds")) continue;

			CurveSurfaceQuoteContainer marketParamContainer = new CurveSurfaceQuoteContainer();

			marketParamContainer.setFundingState (oisCurve);

			marketParamContainer.setCreditState (bumpedCreditCurveMapEntry.getValue());

			System.out.print ("\t|  [" + bumpedCreditCurveMapEntry.getKey() + "] => ");

			for (Component cdsComponent : cdsComponentArray)
				System.out.print (
					FormatUtil.FormatDouble (
						cdsComponent.measureValue (
							valuationParams,
							null,
							marketParamContainer,
							null,
							"FairPremium"
						),
						1,
						1,
						1.
					) + " |"
				);

			System.out.print ("|\n");
		}

		System.out.println ("\t|----------------------------------------------------------------------------||");

		System.out.println ("\n\t|--------------------------------------------||");

		CurveSurfaceQuoteContainer baseMarketParamContainer = new CurveSurfaceQuoteContainer();

		baseMarketParamContainer.setFundingState (oisCurve);

		baseMarketParamContainer.setCreditState (bumpedCreditCurveMap.get ("Base"));

		CurveSurfaceQuoteContainer bumpedMarketParamContainer = new CurveSurfaceQuoteContainer();

		bumpedMarketParamContainer.setFundingState (oisCurve);

		bumpedMarketParamContainer.setCreditState (bumpedCreditCurveMap.get ("Bump"));

		for (Component cdsComponent : cdsComponentArray)
			System.out.println (
				"\t| FAIR PREMIUM => " +
				cdsComponent.maturityDate() + " |" +
				FormatUtil.FormatDouble (
					cdsComponent.measureValue (
						valuationParams,
						null,
						baseMarketParamContainer,
						null,
						"FairPremium"
					),
					3,
					1,
					1.
				) + " |" +
				FormatUtil.FormatDouble (
					cdsComponent.measureValue (
						valuationParams,
						null,
						bumpedMarketParamContainer,
						null,
						"FairPremium"
					),
					3,
					1,
					1.
				) + " ||"
			);

		System.out.println ("\t|--------------------------------------------||");
	}
}
