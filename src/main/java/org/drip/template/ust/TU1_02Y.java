
package org.drip.template.ust;

import org.drip.analytics.date.*;
import org.drip.product.definition.Bond;
import org.drip.product.govvie.TreasuryFutures;
import org.drip.quant.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.service.template.ExchangeInstrumentBuilder;

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
 * <i>TU1_02Y</i> demonstrates the Details behind the Implementation and the Pricing of the 2Y TU1 UST
 * Futures Contract.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template">Template</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/ust">US Treasury Futures</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TU1_02Y {

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

		TreasuryFutures tu1 = ExchangeInstrumentBuilder.TreasuryFutures (
			dtSpot,
			"UST",
			new org.drip.analytics.date.JulianDate[] {
				DateUtil.CreateFromYMD (2014, DateUtil.DECEMBER,  31), // 912828UE
				DateUtil.CreateFromYMD (2014, DateUtil.DECEMBER,  15), // 912828G7
				DateUtil.CreateFromYMD (2014, DateUtil.NOVEMBER,  30), // 912828UA
				DateUtil.CreateFromYMD (2014, DateUtil.NOVEMBER,  15), // 912828G2
				DateUtil.CreateFromYMD (2014, DateUtil.OCTOBER,   31), // 912828TW
				DateUtil.CreateFromYMD (2014, DateUtil.OCTOBER,   15), // 912828F5
				DateUtil.CreateFromYMD (2014, DateUtil.SEPTEMBER, 30), // 912828TS
				DateUtil.CreateFromYMD (2014, DateUtil.SEPTEMBER, 15)  // 912828D9
			},
			new org.drip.analytics.date.JulianDate[] {
				DateUtil.CreateFromYMD (2017, DateUtil.DECEMBER,  31), // 912828UE
				DateUtil.CreateFromYMD (2017, DateUtil.DECEMBER,  15), // 912828G7
				DateUtil.CreateFromYMD (2017, DateUtil.NOVEMBER,  30), // 912828UA
				DateUtil.CreateFromYMD (2017, DateUtil.NOVEMBER,  15), // 912828G2
				DateUtil.CreateFromYMD (2017, DateUtil.OCTOBER,   31), // 912828TW
				DateUtil.CreateFromYMD (2017, DateUtil.OCTOBER,   15), // 912828F5
				DateUtil.CreateFromYMD (2017, DateUtil.SEPTEMBER, 30), // 912828TS
				DateUtil.CreateFromYMD (2017, DateUtil.SEPTEMBER, 15)  // 912828D9
			},
			new double[] {
				0.00750, // 912828UE
				0.01000, // 912828G7
				0.00625, // 912828UA
				0.00875, // 912828G2
				0.00750, // 912828TW
				0.00875, // 912828F5
				0.00625, // 912828TS
				0.01000  // 912828D9
			},
			new double[] {
				0.9024, // 912828UE
				0.9071, // 912828G7
				0.9040, // 912828UA
				0.9085, // 912828G2
				0.9101, // 912828TW
				0.9122, // 912828F5
				0.9119, // 912828TS
				0.9181, // 912828D9
			},
			"TREASURY",
			"NOTE",
			"2Y"
		);

		double dblFuturesPrice = 108.08594;

		double[] adblCleanPrice = new double[] {
			0.99956250, // 912828UE
			1.00093750, // 912828G7
			0.99937500, // 912828UA
			0.99990625, // 912828G2
			0.99975000, // 912828TW
			1.00000000, // 912828F5
			0.99953125, // 912828TS
			1.00250000  // 912828D9
		};

		Bond bondCTD = tu1.cheapestToDeliverYield (
			dtSpot.julian(),
			adblCleanPrice
		).bond();

		System.out.println ("\n\t|---------------------------------------------------------||");

		System.out.println ("\t|      Futures Type      : " + tu1.type() + "                            ||");

		System.out.println ("\t|      Deliverable Grade : " + tu1.minimumMaturity() + " -> " + tu1.maximumMaturity() + "                      ||");

		System.out.println ("\t|      Reference Coupon  : " + FormatUtil.FormatDouble (tu1.referenceCoupon(), 1, 2, 100.) + "%                         ||");

		System.out.println ("\t|      Contract Size     : " + FormatUtil.FormatDouble (tu1.notionalValue(), 1, 2, 1.) + "                     ||");

		System.out.println ("\t|      Tick Size         : " + FormatUtil.FormatDouble (tu1.minimumPriceMovement(), 1, 6, 1.) + "                      ||");

		System.out.println ("\t|      Tick Value        : " + FormatUtil.FormatDouble (tu1.tickValue(), 1, 2, 1.) + "                       ||");

		System.out.println ("\t|      Delivery Months   : " + DeliveryMonths (tu1) + " ||");

		System.out.println ("\t|      Last Trading Lag  : " + tu1.lastTradingDayLag() + " Business Days Prior Expiry   ||");

		System.out.println ("\t|      Futures Price     : " + FormatUtil.FormatDouble (dblFuturesPrice, 2, 5, 1.) + "                     ||");

		System.out.println ("\t|      Contract Value    : " + FormatUtil.FormatDouble (0.01 * tu1.notionalValue() * dblFuturesPrice, 1, 2, 1.) + "                     ||");

		System.out.println ("\t|---------------------------------------------------------||\n");

		System.out.println ("\n\t|---------------------------------------------||");

		System.out.println ("\t|                                             ||");

		for (int i = 0; i < tu1.basket().length; ++i)
			System.out.println ("\t|\t" + tu1.basket()[i].name() + " => " + FormatUtil.FormatDouble (adblCleanPrice[i], 2, 5, 1.) + "   ||");

		System.out.println ("\t|                                             ||");

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\t| Cheapest to Deliver: " + bondCTD.name() + " ||");

		System.out.println ("\t|---------------------------------------------||");
	}
}
