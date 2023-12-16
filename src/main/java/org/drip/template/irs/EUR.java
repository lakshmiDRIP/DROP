
package org.drip.template.irs;

import java.util.Map;

import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.rates.FixFloatComponent;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.*;

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
 * <i>EUR</i> contains a Templated Pricing of the OTC Fix-Float EUR IRS Instrument.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/README.md">Pricing/Risk Templates for Fixed Income Component Products</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/irs/README.md">Standard IRS Fix-Float Template</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EUR
{

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Argument Array
	 * 
	 * @throws Exception Propagate the Exception encountered
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate spotDate = DateUtil.Today();

		String currency = "EUR";
		String maturityTenor = "5Y";

		FixFloatComponent irs = OTCInstrumentBuilder.FixFloatStandard (
			spotDate,
			currency,
			"ALL",
			maturityTenor,
			"MAIN",
			0.0206
		);

		CurveSurfaceQuoteContainer marketDataContainer = new CurveSurfaceQuoteContainer();

		marketDataContainer.setFundingState (
			LatentMarketStateBuilder.SmoothFundingCurve (
				spotDate,
				currency,
				new String[] {"04D", "07D", "14D", "30D", "60D"},
				new double[] {0.0017, 0.0017, 0.0018, 0.0020, 0.0023},
				"ForwardRate",
				new double[] {0.0027, 0.0032, 0.0041, 0.0054, 0.0077, 0.0104, 0.0134, 0.0160},
				"ForwardRate",
				new String[] {
					"04Y", "05Y", "06Y", "07Y", "08Y", "09Y", "10Y", "11Y", "12Y", "15Y", "20Y", "25Y", "30Y", "40Y", "50Y"
				},
				new double[] {
					0.0166, 0.0206, 0.0241, 0.0269, 0.0292, 0.0311, 0.0326, 0.0340, 0.0351, 0.0375, 0.0393, 0.0402, 0.0407, 0.0409, 0.0409
				},
				"SwapRate"
			)
		);

		Map<String, Double> measureMap = irs.value (
			ValuationParams.Spot (spotDate.julian()),
			null,
			marketDataContainer,
			null
		);

		for (Map.Entry<String, Double> measureMapEntry : measureMap.entrySet())
			System.out.println ("\t | " + measureMapEntry.getKey() + " => " + measureMapEntry.getValue() + " ||");

		System.out.println ("\t |------------------------------||");

		System.out.println ("\n\n\t\t|----------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t\t|    Floating Stream Cash Flow Details                                                                                 ||");

		System.out.println ("\t\t|    -------- ------ ---- ---- -------                                                                                 ||");

		System.out.println ("\t\t|               Start Date                                                                                             ||");

		System.out.println ("\t\t|               End Date                                                                                               ||");

		System.out.println ("\t\t|               Pay Date                                                                                               ||");

		System.out.println ("\t\t|               FX Fixing Date                                                                                         ||");

		System.out.println ("\t\t|               Base Notional                                                                                          ||");

		System.out.println ("\t\t|               Period DCF                                                                                             ||");

		System.out.println ("\t\t|               Tenor                                                                                                  ||");

		System.out.println ("\t\t|               Funding Label                                                                                          ||");

		System.out.println ("\t\t|               Forward Label                                                                                          ||");

		System.out.println ("\t\t|               Pay Discount Factor                                                                                    ||");

		System.out.println ("\t\t|               Coupon Rate                                                                                            ||");

		System.out.println ("\t\t|----------------------------------------------------------------------------------------------------------------------||");

		for (CompositePeriod compositePeriod : irs.derivedStream().cashFlowPeriod())
			System.out.println ("\t\t| [" +
				new JulianDate (compositePeriod.startDate()) + " - " +
				new JulianDate (compositePeriod.endDate()) + "] => " +
				new JulianDate (compositePeriod.payDate()) + " | " +
				new JulianDate (compositePeriod.fxFixingDate()) + " | " +
				FormatUtil.FormatDouble (compositePeriod.baseNotional(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (compositePeriod.couponDCF(), 1, 4, 1.) + " | " +
				compositePeriod.tenor() + " | " +
				compositePeriod.fundingLabel().fullyQualifiedName() + " | " +
				compositePeriod.floaterLabel().fullyQualifiedName() + " | " +
				FormatUtil.FormatDouble (compositePeriod.df (marketDataContainer), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (
					compositePeriod.couponMetrics (spotDate.julian(), marketDataContainer).rate(),
					1,
					2,
					100.
				) + "% ||"
			);

		System.out.println ("\t\t|----------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\n\n\t\t|--------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t\t|    Fixed Stream Cash Flow Details                                                                      ||");

		System.out.println ("\t\t|    ----- ------ ---- ---- -------                                                                      ||");

		System.out.println ("\t\t|               Start Date                                                                               ||");

		System.out.println ("\t\t|               End Date                                                                                 ||");

		System.out.println ("\t\t|               Pay Date                                                                                 ||");

		System.out.println ("\t\t|               FX Fixing Date                                                                           ||");

		System.out.println ("\t\t|               Base Notional                                                                            ||");

		System.out.println ("\t\t|               Period DCF                                                                               ||");

		System.out.println ("\t\t|               Tenor                                                                                    ||");

		System.out.println ("\t\t|               Funding Label                                                                            ||");

		System.out.println ("\t\t|               Pay Discount Factor                                                                      ||");

		System.out.println ("\t\t|               Coupon Rate                                                                              ||");

		System.out.println ("\t\t|--------------------------------------------------------------------------------------------------------||");

		for (CompositePeriod compositePeriod : irs.referenceStream().cashFlowPeriod())
			System.out.println ("\t\t| [" +
				new JulianDate (compositePeriod.startDate()) + " - " +
				new JulianDate (compositePeriod.endDate()) + "] => " +
				new JulianDate (compositePeriod.payDate()) + " | " +
				new JulianDate (compositePeriod.fxFixingDate()) + " | " +
				FormatUtil.FormatDouble (compositePeriod.baseNotional(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (compositePeriod.couponDCF(), 1, 4, 1.) + " | " +
				compositePeriod.tenor() + " | " +
				compositePeriod.fundingLabel().fullyQualifiedName() + " | " +
				FormatUtil.FormatDouble (compositePeriod.df (marketDataContainer), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (
					compositePeriod.couponMetrics (spotDate.julian(), marketDataContainer).rate(),
					1,
					2,
					100.
				) + "% ||"
			);

		System.out.println ("\t\t|--------------------------------------------------------------------------------------------------------||");
	}
}
