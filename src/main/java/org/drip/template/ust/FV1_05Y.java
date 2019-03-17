
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
 * <i>FV1_05Y</i> demonstrates the Details behind the Implementation and the Pricing of the 5Y FV1 UST
 * Futures Contract.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template">Template</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/template/ust">US Treasuries</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class FV1_05Y {

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

		TreasuryFutures fv1 = ExchangeInstrumentBuilder.TreasuryFutures (
			dtSpot,
			"UST",
			new org.drip.analytics.date.JulianDate[] {
				DateUtil.CreateFromYMD (2014, DateUtil.FEBRUARY,  28), // 912828J5
				DateUtil.CreateFromYMD (2014, DateUtil.MARCH,     31), // 912828J8
				DateUtil.CreateFromYMD (2014, DateUtil.APRIL,     30), // 912828K5
				DateUtil.CreateFromYMD (2014, DateUtil.MAY,       31), // 912828XE
				DateUtil.CreateFromYMD (2014, DateUtil.JUNE,      30), // 912828XH
				DateUtil.CreateFromYMD (2014, DateUtil.JULY,      31), // 912828XM
				DateUtil.CreateFromYMD (2014, DateUtil.AUGUST,    31), // 912828L3
				DateUtil.CreateFromYMD (2014, DateUtil.SEPTEMBER, 30), // 912828L6
				DateUtil.CreateFromYMD (2014, DateUtil.OCTOBER,   31)  // 912828L9
			},
			new org.drip.analytics.date.JulianDate[] {
				DateUtil.CreateFromYMD (2020, DateUtil.FEBRUARY,  28), // 912828J5
				DateUtil.CreateFromYMD (2020, DateUtil.MARCH,     31), // 912828J8
				DateUtil.CreateFromYMD (2020, DateUtil.APRIL,     30), // 912828K5
				DateUtil.CreateFromYMD (2020, DateUtil.MAY,       31), // 912828XE
				DateUtil.CreateFromYMD (2020, DateUtil.JUNE,      30), // 912828XH
				DateUtil.CreateFromYMD (2020, DateUtil.JULY,      31), // 912828XM
				DateUtil.CreateFromYMD (2020, DateUtil.AUGUST,    31), // 912828L3
				DateUtil.CreateFromYMD (2020, DateUtil.SEPTEMBER, 30), // 912828L6
				DateUtil.CreateFromYMD (2020, DateUtil.OCTOBER,   31)  // 912828L9
			},
			new double[] {
				0.01375, // 912828J5
				0.01375, // 912828J8
				0.01375, // 912828K5
				0.01500, // 912828XE
				0.01625, // 912828XH
				0.01625, // 912828XM
				0.01375, // 912828L3
				0.01375, // 912828L6
				0.01375  // 912828L9
			},
			new double[] {
				0.8317, // 912828J5
				0.8287, // 912828J8
				0.8258, // 912828K5
				0.8276, // 912828XE
				0.8297, // 912828XH
				0.8269, // 912828XM
				0.8141, // 912828L3
				0.8113, // 912828L6
				0.8084	// 912828L9
			},
			"TREASURY",
			"NOTE",
			"5Y"
		);

		double dblFuturesPrice = 119.00000;

		double[] adblCleanPrice = new double[] {
			 0.99909375, // 912828J5
			 0.99900000, // 912828J8
			 0.99890625, // 912828K5
			 0.99943750, // 912828XE
			 0.99984375, // 912828XH
			 0.99978125, // 912828XM
			 0.99862500, // 912828L3
			 0.99850000, // 912828L6
			 0.99853125  // 912828L9
		};

		Bond bondCTD = fv1.cheapestToDeliverYield (
			dtSpot.julian(),
			adblCleanPrice
		).bond();

		System.out.println ("\n\t|---------------------------------------------------------||");

		System.out.println ("\t|      Futures Type      : " + fv1.type() + "                            ||");

		System.out.println ("\t|      Deliverable Grade : " + fv1.minimumMaturity() + " -> " + fv1.maximumMaturity() + "                     ||");

		System.out.println ("\t|      Reference Coupon  : " + FormatUtil.FormatDouble (fv1.referenceCoupon(), 1, 2, 100.) + "%                         ||");

		System.out.println ("\t|      Contract Size     : " + FormatUtil.FormatDouble (fv1.notionalValue(), 1, 2, 1.) + "                     ||");

		System.out.println ("\t|      Tick Size         : " + FormatUtil.FormatDouble (fv1.minimumPriceMovement(), 1, 6, 1.) + "                      ||");

		System.out.println ("\t|      Tick Value        : " + FormatUtil.FormatDouble (fv1.tickValue(), 1, 2, 1.) + "                       ||");

		System.out.println ("\t|      Delivery Months   : " + DeliveryMonths (fv1) + " ||");

		System.out.println ("\t|      Last Trading Lag  : " + fv1.lastTradingDayLag() + " Business Days Prior Expiry   ||");

		System.out.println ("\t|      Futures Price     : " + FormatUtil.FormatDouble (dblFuturesPrice, 2, 5, 1.) + "                     ||");

		System.out.println ("\t|      Contract Value    : " + FormatUtil.FormatDouble (0.01 * fv1.notionalValue() * dblFuturesPrice, 1, 2, 1.) + "                     ||");

		System.out.println ("\t|---------------------------------------------------------||\n");

		System.out.println ("\n\t|---------------------------------------------||");

		System.out.println ("\t|                                             ||");

		for (int i = 0; i < fv1.basket().length; ++i)
			System.out.println ("\t|\t" + fv1.basket()[i].name() + " => " + FormatUtil.FormatDouble (adblCleanPrice[i], 2, 5, 1.) + "   ||");

		System.out.println ("\t|                                             ||");

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\t| Cheapest to Deliver: " + bondCTD.name() + " ||");

		System.out.println ("\t|---------------------------------------------||");
	}
}
