
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
 * <i>TY1_10Y</i> demonstrates the Details behind the Implementation and the Pricing of the 10Y TY1 UST
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

public class TY1_10Y {

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

		TreasuryFutures ty1 = ExchangeInstrumentBuilder.TreasuryFutures (
			dtSpot,
			"UST",
			new org.drip.analytics.date.JulianDate[] {
				DateUtil.CreateFromYMD (2014, DateUtil.JUNE,      30), // 912828XG
				DateUtil.CreateFromYMD (2014, DateUtil.AUGUST,    31), // 912828L2
				DateUtil.CreateFromYMD (2014, DateUtil.JULY,      31), // 912828XQ
				DateUtil.CreateFromYMD (2014, DateUtil.AUGUST,    15), // 912828TJ
				DateUtil.CreateFromYMD (2014, DateUtil.OCTOBER,   31), // 912828M4
				DateUtil.CreateFromYMD (2014, DateUtil.SEPTEMBER, 30), // 912828L5
				DateUtil.CreateFromYMD (2014, DateUtil.NOVEMBER,  15), // 912828TY
				DateUtil.CreateFromYMD (2014, DateUtil.FEBRUARY,  15), // 912828UN
				DateUtil.CreateFromYMD (2014, DateUtil.MAY,       15), // 912828VB
				DateUtil.CreateFromYMD (2014, DateUtil.AUGUST,    15), // 912828VS
				DateUtil.CreateFromYMD (2014, DateUtil.NOVEMBER,  15), // 912828WE
				DateUtil.CreateFromYMD (2014, DateUtil.FEBRUARY,  15), // 912828B6
				DateUtil.CreateFromYMD (2014, DateUtil.MAY,       15), // 912828WJ
				DateUtil.CreateFromYMD (2014, DateUtil.AUGUST,    15), // 912828D5
				DateUtil.CreateFromYMD (2014, DateUtil.NOVEMBER,  15), // 912828G3
				DateUtil.CreateFromYMD (2014, DateUtil.FEBRUARY,  15), // 912828J2
				DateUtil.CreateFromYMD (2014, DateUtil.MAY,       15), // 912828XB
				DateUtil.CreateFromYMD (2014, DateUtil.AUGUST,    15), // 912828K7
				DateUtil.CreateFromYMD (2014, DateUtil.NOVEMBER,  15)  // 912828M5
			},
			new org.drip.analytics.date.JulianDate[] {
				DateUtil.CreateFromYMD (2022, DateUtil.JUNE,      30), // 912828XG
				DateUtil.CreateFromYMD (2022, DateUtil.AUGUST,    31), // 912828L2
				DateUtil.CreateFromYMD (2022, DateUtil.JULY,      31), // 912828XQ
				DateUtil.CreateFromYMD (2022, DateUtil.AUGUST,    15), // 912828TJ
				DateUtil.CreateFromYMD (2022, DateUtil.OCTOBER,   31), // 912828M4
				DateUtil.CreateFromYMD (2022, DateUtil.SEPTEMBER, 30), // 912828L5
				DateUtil.CreateFromYMD (2022, DateUtil.NOVEMBER,  15), // 912828TY
				DateUtil.CreateFromYMD (2023, DateUtil.FEBRUARY,  15), // 912828UN
				DateUtil.CreateFromYMD (2023, DateUtil.MAY,       15), // 912828VB
				DateUtil.CreateFromYMD (2023, DateUtil.AUGUST,    15), // 912828VS
				DateUtil.CreateFromYMD (2023, DateUtil.NOVEMBER,  15), // 912828WE
				DateUtil.CreateFromYMD (2024, DateUtil.FEBRUARY,  15), // 912828B6
				DateUtil.CreateFromYMD (2024, DateUtil.MAY,       15), // 912828WJ
				DateUtil.CreateFromYMD (2024, DateUtil.AUGUST,    15), // 912828D5
				DateUtil.CreateFromYMD (2024, DateUtil.NOVEMBER,  15), // 912828G3
				DateUtil.CreateFromYMD (2025, DateUtil.FEBRUARY,  15), // 912828J2
				DateUtil.CreateFromYMD (2025, DateUtil.MAY,       15), // 912828XB
				DateUtil.CreateFromYMD (2025, DateUtil.AUGUST,    15), // 912828K7
				DateUtil.CreateFromYMD (2025, DateUtil.NOVEMBER,  15)  // 912828M5
			},
			new double[] {
				0.02125, // 912828XG
				0.01875, // 912828L2
				0.02000, // 912828XQ
				0.01625, // 912828TJ
				0.01875, // 912828M4
				0.01875, // 912828L5
				0.01625, // 912828TY
				0.02000, // 912828UN
				0.01750, // 912828VB
				0.02500, // 912828VS
				0.02750, // 912828WE
				0.02750, // 912828B6
				0.02500, // 912828WJ
				0.02375, // 912828D5
				0.02250, // 912828G3
				0.02000, // 912828J2
				0.02125, // 912828XB
				0.02000, // 912828K7
				0.02125  // 912828M5
			},
			new double[] {
				0.7939, // 912828XG
				0.7807, // 912828L2
				0.7873, // 912828XQ
				0.7674, // 912828TJ
				0.7738, // 912828M4
				0.7669, // 912828L5
				0.7600, // 912828TY
				0.7741, // 912828UN
				0.7531, // 912828VB
				0.7911, // 912828VS
				0.8009, // 912828WE
				0.7959, // 912828B6
				0.7748, // 912828WJ
				0.7614, // 912828D5
				0.7475, // 912828G3
				0.7249, // 912828J2
				0.7279, // 912828XB
				0.7135, // 912828K7
				0.7262  // 912828M5
			},
			"TREASURY",
			"NOTE",
			"10Y"
		);

		double dblFuturesPrice = 126.578125;

		double[] adblCleanPrice = new double[] {
			1.0071875, // 912828XG
			0.9903125, // 912828L2
			0.9990625, // 912828XQ
			0.9756250, // 912828TJ
			0.9893750, // 912828M4
			0.9818750, // 912828L5
			0.9734375, // 912828TY
			0.9968750, // 912828UN
			0.9756250, // 912828VB
			1.0281250, // 912828VS
			1.0459375, // 912828WE
			1.0443750, // 912828B6
			1.0231250, // 912828WJ
			1.0128125, // 912828D5
			0.9996875, // 912828G3
			0.9768750, // 912828J2
			0.9865625, // 912828XB
			0.9750000, // 912828K7
			0.9978125  // 912828M5
		};

		Bond bondCTD = ty1.cheapestToDeliverYield (
			dtSpot.julian(),
			adblCleanPrice
		).bond();

		System.out.println ("\n\t|---------------------------------------------------------||");

		System.out.println ("\t|      Futures Type      : " + ty1.type() + "                            ||");

		System.out.println ("\t|      Deliverable Grade : " + ty1.minimumMaturity() + " -> " + ty1.maximumMaturity() + "                      ||");

		System.out.println ("\t|      Reference Coupon  : " + FormatUtil.FormatDouble (ty1.referenceCoupon(), 1, 2, 100.) + "%                         ||");

		System.out.println ("\t|      Contract Size     : " + FormatUtil.FormatDouble (ty1.notionalValue(), 1, 2, 1.) + "                     ||");

		System.out.println ("\t|      Tick Size         : " + FormatUtil.FormatDouble (ty1.minimumPriceMovement(), 1, 6, 1.) + "                      ||");

		System.out.println ("\t|      Tick Value        : " + FormatUtil.FormatDouble (ty1.tickValue(), 1, 2, 1.) + "                       ||");

		System.out.println ("\t|      Delivery Months   : " + DeliveryMonths (ty1) + " ||");

		System.out.println ("\t|      Last Trading Lag  : " + ty1.lastTradingDayLag() + " Business Days Prior Expiry   ||");

		System.out.println ("\t|      Futures Price     : " + FormatUtil.FormatDouble (dblFuturesPrice, 2, 5, 1.) + "                     ||");

		System.out.println ("\t|      Contract Value    : " + FormatUtil.FormatDouble (0.01 * ty1.notionalValue() * dblFuturesPrice, 1, 2, 1.) + "                     ||");

		System.out.println ("\t|---------------------------------------------------------||\n");

		System.out.println ("\n\t|---------------------------------------------||");

		System.out.println ("\t|                                             ||");

		for (int i = 0; i < ty1.basket().length; ++i)
			System.out.println ("\t|\t" + ty1.basket()[i].name() + " => " + FormatUtil.FormatDouble (adblCleanPrice[i], 2, 5, 1.) + "   ||");

		System.out.println ("\t|                                             ||");

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\t| Cheapest to Deliver: " + bondCTD.name() + " ||");

		System.out.println ("\t|---------------------------------------------||");
	}
}
