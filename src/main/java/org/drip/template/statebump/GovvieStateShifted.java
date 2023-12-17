
package org.drip.template.statebump;

import java.util.Map;

import org.drip.analytics.date.*;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.credit.BondComponent;
import org.drip.product.govvie.TreasuryComponent;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.*;
import org.drip.state.govvie.GovvieCurve;

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
 * <i>GovvieStateShifted</i> demonstrates the Construction and Usage of Tenor Bumped Govvie Curves.
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

public class GovvieStateShifted {

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

		JulianDate spotDate = DateUtil.CreateFromYMD (
			2017,
			DateUtil.DECEMBER,
			21
		);

		String code = "UST";
		double bump = 0.0001;
		boolean isBumpProportional = false;

		JulianDate[] effectiveDateArray = new JulianDate[] {
			DateUtil.CreateFromYMD (2010, DateUtil.SEPTEMBER, 21),
			DateUtil.CreateFromYMD (2009, DateUtil.JULY, 14),
			DateUtil.CreateFromYMD (2011, DateUtil.MARCH, 8),
			DateUtil.CreateFromYMD (2010, DateUtil.AUGUST, 25),
			DateUtil.CreateFromYMD (2010, DateUtil.DECEMBER, 3)
		};

		JulianDate[] maturityDateArray = new JulianDate[] {
			spotDate.addTenor ("2Y"),
			spotDate.addTenor ("4Y"),
			spotDate.addTenor ("5Y"),
			spotDate.addTenor ("7Y"),
			spotDate.addTenor ("10Y")
		};

		double[] couponArray = new double[] {
			0.0200,
			0.0250,
			0.0300,
			0.0325,
			0.0375
		};

		double[] yieldArray = new double[] {
			0.0200,
			0.0250,
			0.0300,
			0.0325,
			0.0375
		};

		Map<String, GovvieCurve> bumpedGovvieCurveMap = LatentMarketStateBuilder.BumpedGovvieCurve (
			code,
			spotDate,
			effectiveDateArray,
			maturityDateArray,
			couponArray,
			yieldArray,
			"Yield",
			LatentMarketStateBuilder.SHAPE_PRESERVING,
			bump,
			isBumpProportional
		);

		TreasuryComponent[] treasuryComponentArray = TreasuryBuilder.FromCode (
			code,
			effectiveDateArray,
			maturityDateArray,
			couponArray
		);

		ValuationParams valuationParams = ValuationParams.Spot (spotDate.julian());

		System.out.println ("\n\t|-------------------------------------------------------------------||");

		for (Map.Entry<String, GovvieCurve> bumpedGovvieCurveMapEntry : bumpedGovvieCurveMap.entrySet()) {
			String key = bumpedGovvieCurveMapEntry.getKey();

			if (!key.startsWith ("tsy")) {
				continue;
			}

			System.out.print ("\t|  [" + key + "] => ");

			GovvieCurve govvieCurve = bumpedGovvieCurveMapEntry.getValue();

			for (BondComponent treasury : treasuryComponentArray)
				System.out.print (
					FormatUtil.FormatDouble (
						treasury.yieldFromPrice (
							valuationParams,
							null,
							null,
							treasury.maturityDate().julian(),
							1.,
							treasury.priceFromYield (
								valuationParams,
								null,
								null,
								govvieCurve.yld (treasury.maturityDate().julian()
							)
						)
					),
					1,
					4,
					1.
				) + " |"
			);

			System.out.print ("|\n");
		}

		System.out.println ("\t|-------------------------------------------------------------------||");

		System.out.println ("\n\t|-------------------------------------||");

		GovvieCurve baseGovvieCurve = bumpedGovvieCurveMap.get ("Base");

		GovvieCurve bumpedGovvieCurve = bumpedGovvieCurveMap.get ("Bump");

		for (TreasuryComponent treasury : treasuryComponentArray)
			System.out.println (
				"\t| YIELD => " +
				treasury.maturityDate() + " |" +
				FormatUtil.FormatDouble (
					treasury.yieldFromPrice (
						valuationParams,
						null,
						null,
						treasury.maturityDate().julian(),
						1.,
						treasury.priceFromYield (
							valuationParams,
							null,
							null,
							baseGovvieCurve.yld (treasury.maturityDate().julian())
						)
					),
					1,
					2,
					100.
				) + "% |" +
				FormatUtil.FormatDouble (
					treasury.yieldFromPrice (
						valuationParams,
						null,
						null,
						treasury.maturityDate().julian(),
						1.,
						treasury.priceFromYield (
							valuationParams,
							null,
							null,
							bumpedGovvieCurve.yld (treasury.maturityDate().julian())
						)
					),
					1,
					2,
					100.
				) + "% ||"
			);

		System.out.println ("\t|-------------------------------------||");
	}
}
