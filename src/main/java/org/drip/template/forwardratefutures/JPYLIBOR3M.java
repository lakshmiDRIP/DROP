
package org.drip.template.forwardratefutures;

import java.util.Map;

import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.*;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.Component;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.*;

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
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
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
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>JPYLIBOR3M</i> contains a Templated Pricing of the LIBOR 3M JPY Futures Instrument.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template">Template</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/forwardratefutures">Forward Rate Futures</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class JPYLIBOR3M {

	public static final void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.Today();

		String strCurrency = "JPY";

		Component futures = ExchangeInstrumentBuilder.ForwardRateFutures (
			dtSpot,
			strCurrency
		);

		CurveSurfaceQuoteContainer csqc = new CurveSurfaceQuoteContainer();

		csqc.setFundingState (
			LatentMarketStateBuilder.SmoothFundingCurve (
				dtSpot,
				strCurrency,
				new String[] {
					"04D", "07D", "14D", "30D", "60D"
				},
				new double[] {
					0.0017, 0.0017, 0.0018, 0.0020, 0.0023
				},
				"ForwardRate",
				new double[] {
					0.0027, 0.0032, 0.0041, 0.0054, 0.0077, 0.0104, 0.0134, 0.0160
				},
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

		Map<String, Double> mapOutput = futures.value (
			ValuationParams.Spot (dtSpot.julian()),
			null,
			csqc,
			null
		);

		for (Map.Entry<String, Double> me : mapOutput.entrySet())
			System.out.println ("\t | " + me.getKey() + " => " + me.getValue() + " ||");

		System.out.println ("\t |------------------------------||");

		System.out.println ("\n\n\t\t|-----------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t\t|    Cash Flow Details                                                                                                  ||");

		System.out.println ("\t\t|    -----------------                                                                                                  ||");

		System.out.println ("\t\t|               Start Date                                                                                              ||");

		System.out.println ("\t\t|               End Date                                                                                                ||");

		System.out.println ("\t\t|               Pay Date                                                                                                ||");

		System.out.println ("\t\t|               FX Fixing Date                                                                                          ||");

		System.out.println ("\t\t|               Base Notional                                                                                           ||");

		System.out.println ("\t\t|               Period DCF                                                                                              ||");

		System.out.println ("\t\t|               Tenor                                                                                                   ||");

		System.out.println ("\t\t|               Funding Label                                                                                           ||");

		System.out.println ("\t\t|               Forward Label                                                                                           ||");

		System.out.println ("\t\t|               Pay Discount Factor                                                                                     ||");

		System.out.println ("\t\t|               Coupon Rate                                                                                             ||");

		System.out.println ("\t\t|-----------------------------------------------------------------------------------------------------------------------||");

		for (CompositePeriod cp : futures.couponPeriods())
			System.out.println ("\t\t| [" +
				new JulianDate (cp.startDate()) + " - " +
				new JulianDate (cp.endDate()) + "] => " +
				new JulianDate (cp.payDate()) + " | " +
				new JulianDate (cp.fxFixingDate()) + " | " +
				FormatUtil.FormatDouble (cp.baseNotional(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (cp.couponDCF(), 1, 4, 1.) + " | " +
				cp.tenor() + " | " +
				cp.fundingLabel().fullyQualifiedName() + " | " +
				cp.floaterLabel().fullyQualifiedName() + " | " +
				FormatUtil.FormatDouble (cp.df (csqc), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (cp.couponMetrics (dtSpot.julian(), csqc).rate(), 1, 2, 100.) + "% ||"
			);

		System.out.println ("\t\t|-----------------------------------------------------------------------------------------------------------------------||");
	}
}
