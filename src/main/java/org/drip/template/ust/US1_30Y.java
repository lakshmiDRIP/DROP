
package org.drip.template.ust;

import org.drip.analytics.date.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.product.definition.Bond;
import org.drip.product.govvie.TreasuryFutures;
import org.drip.service.env.EnvManager;
import org.drip.service.template.ExchangeInstrumentBuilder;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * <i>US1_30Y</i> demonstrates the Details behind the Implementation and the Pricing of the 30Y US1 UST
 * Futures Contract.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/README.md">Pricing/Risk Templates for Fixed Income Component Products</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/ust/README.md">Standard UST Suite Construction Template</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class US1_30Y {

	private static final String DeliveryMonths (
		final TreasuryFutures tsyFutures)
	{
		int[] aiDeliveryMonth = tsyFutures.deliveryMonths();

		String strDeliveryMonths = "";
		int iNumDeliveryMonth = null == aiDeliveryMonth ? 0 : aiDeliveryMonth.length;

		if (0 != iNumDeliveryMonth) {
			for (int i = 0; i < iNumDeliveryMonth; ++i) {
				if (0 == i)
					strDeliveryMonths += "{";
				else
					strDeliveryMonths += ",";

				strDeliveryMonths += DateUtil.MonthChar (aiDeliveryMonth[i]);
			}

			strDeliveryMonths += "}";
		}

		return strDeliveryMonths;
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.CreateFromYMD (
			2015,
			DateUtil.NOVEMBER,
			18
		);

		TreasuryFutures us1 = ExchangeInstrumentBuilder.TreasuryFutures (
			dtSpot,
			"UST",
			new org.drip.analytics.date.JulianDate[] {
				DateUtil.CreateFromYMD (2014, DateUtil.FEBRUARY, 15), // 912810FT
				DateUtil.CreateFromYMD (2014, DateUtil.MAY,      15), // 912810PU
				DateUtil.CreateFromYMD (2014, DateUtil.FEBRUARY, 15), // 912810PT
				DateUtil.CreateFromYMD (2014, DateUtil.MAY,      15), // 912810PX
				DateUtil.CreateFromYMD (2014, DateUtil.FEBRUARY, 15), // 912810PW
				DateUtil.CreateFromYMD (2014, DateUtil.AUGUST,   15), // 912810QC
				DateUtil.CreateFromYMD (2014, DateUtil.FEBRUARY, 15), // 912810QE
				DateUtil.CreateFromYMD (2014, DateUtil.MAY,      15), // 912810QB
				DateUtil.CreateFromYMD (2014, DateUtil.NOVEMBER, 15), // 912810QD
				DateUtil.CreateFromYMD (2014, DateUtil.MAY,      15), // 912810QH
				DateUtil.CreateFromYMD (2014, DateUtil.NOVEMBER, 15), // 912810QL
				DateUtil.CreateFromYMD (2014, DateUtil.AUGUST,   15), // 912810QK
				DateUtil.CreateFromYMD (2014, DateUtil.FEBRUARY, 15)  // 912810QA
			},
			new org.drip.analytics.date.JulianDate[] {
				DateUtil.CreateFromYMD (2036, DateUtil.FEBRUARY, 15), // 912810FT
				DateUtil.CreateFromYMD (2037, DateUtil.MAY,      15), // 912810PU
				DateUtil.CreateFromYMD (2037, DateUtil.FEBRUARY, 15), // 912810PT
				DateUtil.CreateFromYMD (2038, DateUtil.MAY,      15), // 912810PX
				DateUtil.CreateFromYMD (2038, DateUtil.FEBRUARY, 15), // 912810PW
				DateUtil.CreateFromYMD (2039, DateUtil.AUGUST,   15), // 912810QC
				DateUtil.CreateFromYMD (2040, DateUtil.FEBRUARY, 15), // 912810QE
				DateUtil.CreateFromYMD (2039, DateUtil.MAY,      15), // 912810QB
				DateUtil.CreateFromYMD (2039, DateUtil.NOVEMBER, 15), // 912810QD
				DateUtil.CreateFromYMD (2040, DateUtil.MAY,      15), // 912810QH
				DateUtil.CreateFromYMD (2040, DateUtil.NOVEMBER, 15), // 912810QL
				DateUtil.CreateFromYMD (2040, DateUtil.AUGUST,   15), // 912810QK
				DateUtil.CreateFromYMD (2039, DateUtil.FEBRUARY, 15)  // 912810QA
			},
			new double[] {
				0.04500, // 912810FT
				0.05000, // 912810PU
				0.04750, // 912810PT
				0.04500, // 912810PX
				0.04375, // 912810PW
				0.04500, // 912810QC
				0.04625, // 912810QE
				0.04250, // 912810QB
				0.04375, // 912810QD
				0.04375, // 912810QH
				0.04250, // 912810QL
				0.03875, // 912810QK
				0.03500  // 912810QA
			},
			new double[] {
				0.8266, // 912810FT
				0.8807, // 912810PU
				0.8519, // 912810PT
				0.8170, // 912810PX
				0.8029, // 912810PW
				0.8123, // 912810QC
				0.8263, // 912810QE
				0.7820, // 912810QB
				0.7956, // 912810QD
				0.7939, // 912810QH
				0.7758, // 912810QL
				0.7290, // 912810QK
				0.6903, // 912810QA
			},
			"TREASURY",
			"BOND",
			"30Y"
		);

		double dblFuturesPrice = 153.750000;

		double[] adblCleanPrice = new double[] {
			1.2765625, // 912810FT
			1.3643750, // 912810PU
			1.3203125, // 912810PT
			1.2775000, // 912810PX
			1.2556250, // 912810PW
			1.2731250, // 912810QC
			1.2956250, // 912810QE
			1.2287500, // 912810QB
			1.2506250, // 912810QD
			1.2506250, // 912810QH
			1.2281250, // 912810QL
			1.1603125, // 912810QK
			1.1009375  // 912810QA
		};

		Bond bondCTD = us1.cheapestToDeliverYield (
			dtSpot.julian(),
			adblCleanPrice
		).bond();

		System.out.println ("\n\t|---------------------------------------------------------||");

		System.out.println ("\t|      Futures Type      : " + us1.type() + "                            ||");

		System.out.println ("\t|      Deliverable Grade : " + us1.minimumMaturity() + " -> " + us1.maximumMaturity() + "                     ||");

		System.out.println ("\t|      Reference Coupon  : " + FormatUtil.FormatDouble (us1.referenceCoupon(), 1, 2, 100.) + "%                         ||");

		System.out.println ("\t|      Contract Size     : " + FormatUtil.FormatDouble (us1.notionalValue(), 1, 2, 1.) + "                     ||");

		System.out.println ("\t|      Tick Size         : " + FormatUtil.FormatDouble (us1.minimumPriceMovement(), 1, 6, 1.) + "                      ||");

		System.out.println ("\t|      Tick Value        : " + FormatUtil.FormatDouble (us1.tickValue(), 1, 2, 1.) + "                       ||");

		System.out.println ("\t|      Delivery Months   : " + DeliveryMonths (us1) + " ||");

		System.out.println ("\t|      Last Trading Lag  : " + us1.lastTradingDayLag() + " Business Days Prior Expiry   ||");

		System.out.println ("\t|      Futures Price     : " + FormatUtil.FormatDouble (dblFuturesPrice, 2, 5, 1.) + "                     ||");

		System.out.println ("\t|      Contract Value    : " + FormatUtil.FormatDouble (0.01 * us1.notionalValue() * dblFuturesPrice, 1, 2, 1.) + "                     ||");

		System.out.println ("\t|---------------------------------------------------------||\n");

		System.out.println ("\n\t|----------------------------------------------||");

		System.out.println ("\t|                                              ||");

		for (int i = 0; i < us1.basket().length; ++i)
			System.out.println ("\t|\t" + us1.basket()[i].name() + " => " + FormatUtil.FormatDouble (adblCleanPrice[i], 2, 5, 1.) + "   ||");

		System.out.println ("\t|                                              ||");

		System.out.println ("\t|----------------------------------------------||");

		System.out.println ("\t| Cheapest to Deliver: " + bondCTD.name() + "  ||");

		System.out.println ("\t|----------------------------------------------||");
	}
}
