
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
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * WN1 demonstrates the Details behind the Implementation and the Pricing of the ULTRA LONG BOND WN1 UST
 *  Futures Contract.
 *
 * @author Lakshmi Krishnamurthy
 */

public class WN1 {

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

		TreasuryFutures wn1 = ExchangeInstrumentBuilder.TreasuryFutures (
			dtSpot,
			"UST",
			new org.drip.analytics.date.JulianDate[] {
				DateUtil.CreateFromYMD (2014, DateUtil.FEBRUARY, 15), // 912810QN
				DateUtil.CreateFromYMD (2014, DateUtil.MAY,      15), // 912810QQ
				DateUtil.CreateFromYMD (2014, DateUtil.AUGUST,   15), // 912810QS
				DateUtil.CreateFromYMD (2014, DateUtil.AUGUST,   15), // 912810RC
				DateUtil.CreateFromYMD (2014, DateUtil.NOVEMBER, 15), // 912810RD
				DateUtil.CreateFromYMD (2014, DateUtil.NOVEMBER, 15), // 912810QT
				DateUtil.CreateFromYMD (2014, DateUtil.FEBRUARY, 15), // 912810QU
				DateUtil.CreateFromYMD (2014, DateUtil.FEBRUARY, 15), // 912810RE
				DateUtil.CreateFromYMD (2014, DateUtil.FEBRUARY, 15), // 912810QZ
				DateUtil.CreateFromYMD (2014, DateUtil.MAY,      15), // 912810QW
				DateUtil.CreateFromYMD (2014, DateUtil.MAY,      15), // 912810RG
				DateUtil.CreateFromYMD (2014, DateUtil.AUGUST,   15), // 912810QX
				DateUtil.CreateFromYMD (2014, DateUtil.MAY,      15), // 912810RB
				DateUtil.CreateFromYMD (2014, DateUtil.NOVEMBER, 15), // 912810QY
				DateUtil.CreateFromYMD (2014, DateUtil.AUGUST,   15), // 912810RH
				DateUtil.CreateFromYMD (2014, DateUtil.NOVEMBER, 15), // 912810RJ
				DateUtil.CreateFromYMD (2014, DateUtil.MAY,      15), // 912810RM
				DateUtil.CreateFromYMD (2014, DateUtil.NOVEMBER, 15), // 912810RP
				DateUtil.CreateFromYMD (2014, DateUtil.AUGUST,   15), // 912810RN
				DateUtil.CreateFromYMD (2014, DateUtil.FEBRUARY, 15)  // 912810RK
			},
			new org.drip.analytics.date.JulianDate[] {
				DateUtil.CreateFromYMD (2041, DateUtil.FEBRUARY, 15), // 912810QN
				DateUtil.CreateFromYMD (2041, DateUtil.MAY,      15), // 912810QQ
				DateUtil.CreateFromYMD (2041, DateUtil.AUGUST,   15), // 912810QS
				DateUtil.CreateFromYMD (2043, DateUtil.AUGUST,   15), // 912810RC
				DateUtil.CreateFromYMD (2043, DateUtil.NOVEMBER, 15), // 912810RD
				DateUtil.CreateFromYMD (2041, DateUtil.NOVEMBER, 15), // 912810QT
				DateUtil.CreateFromYMD (2043, DateUtil.FEBRUARY, 15), // 912810QU
				DateUtil.CreateFromYMD (2044, DateUtil.FEBRUARY, 15), // 912810RE
				DateUtil.CreateFromYMD (2043, DateUtil.FEBRUARY, 15), // 912810QZ
				DateUtil.CreateFromYMD (2042, DateUtil.MAY,      15), // 912810QW
				DateUtil.CreateFromYMD (2044, DateUtil.MAY,      15), // 912810RG
				DateUtil.CreateFromYMD (2042, DateUtil.AUGUST,   15), // 912810QX
				DateUtil.CreateFromYMD (2043, DateUtil.MAY,      15), // 912810RB
				DateUtil.CreateFromYMD (2042, DateUtil.NOVEMBER, 15), // 912810QY
				DateUtil.CreateFromYMD (2044, DateUtil.AUGUST,   15), // 912810RH
				DateUtil.CreateFromYMD (2044, DateUtil.NOVEMBER, 15), // 912810RJ
				DateUtil.CreateFromYMD (2045, DateUtil.MAY,      15), // 912810RM
				DateUtil.CreateFromYMD (2045, DateUtil.NOVEMBER, 15), // 912810RP
				DateUtil.CreateFromYMD (2045, DateUtil.AUGUST,   15), // 912810RN
				DateUtil.CreateFromYMD (2045, DateUtil.FEBRUARY, 15)  // 912810RK
			},
			new double[] {
				0.04750, // 912810QN
				0.04375, // 912810QQ
				0.03750, // 912810QS
				0.03625, // 912810RC
				0.03750, // 912810RD
				0.03125, // 912810QT
				0.03125, // 912810QU
				0.03625, // 912810RE
				0.03125, // 912810QZ
				0.03000, // 912810QW
				0.03625, // 912810RG
				0.02375, // 912810QX
				0.02875, // 912810RB
				0.02750, // 912810QY
				0.03125, // 912810RH
				0.03000, // 912810RJ
				0.03000, // 912810RM
				0.03000, // 912810RP
				0.02875, // 912810RN
				0.02500  // 912810RK
			},
			new double[] {
				0.8392, // 912810QN
				0.7900, // 912810QQ
				0.7080, // 912810QS
				0.6821, // 912810RC
				0.6976, // 912810RD
				0.6253, // 912810QT
				0.6239, // 912810QU
				0.6798, // 912810RE
				0.6179, // 912810QZ
				0.6059, // 912810QW
				0.6448, // 912810RG
				0.5714, // 912810QX
				0.5831, // 912810RB
				0.5697, // 912810QY
				0.6097, // 912810RH
				0.5913, // 912810RJ
				0.5887, // 912810RM
				0.5861, // 912810RP
				0.5702, // 912810RN
				0.5217, // 912810RK
			},
			"TREASURY",
			"BOND",
			"ULTRA"
		);

		double dblFuturesPrice = 156.687500;

		double[] adblCleanPrice = new double[] {
			1.3200000, // 912810QN
			1.2540625, // 912810QQ
			1.1412500, // 912810QS
			1.1193750, // 912810RC
			1.1450000, // 912810RD
			1.0262500, // 912810QT
			1.0256250, // 912810QU
			1.1184375, // 912810RE
			1.0181250, // 912810QZ
			0.9990625, // 912810QW
			1.0659375, // 912810RG
			0.9481250, // 912810QX
			0.9681250, // 912810RB
			0.9459375, // 912810QY
			1.0153125, // 912810RH
			0.9896875, // 912810RJ
			0.9890625, // 912810RM
			0.9918750, // 912810RP
			0.9656250, // 912810RN
			0.8909375  // 912810RK
		};

		Bond bondCTD = wn1.cheapestToDeliverYield (
			dtSpot.julian(),
			adblCleanPrice
		).bond();

		System.out.println ("\n\t|---------------------------------------------------------||");

		System.out.println ("\t|      Futures Type      : " + wn1.type() + "                            ||");

		System.out.println ("\t|      Deliverable Grade : " + wn1.minimumMaturity() + " -> " + wn1.maximumMaturity() + "                     ||");

		System.out.println ("\t|      Reference Coupon  : " + FormatUtil.FormatDouble (wn1.referenceCoupon(), 1, 2, 100.) + "%                         ||");

		System.out.println ("\t|      Contract Size     : " + FormatUtil.FormatDouble (wn1.notionalValue(), 1, 2, 1.) + "                     ||");

		System.out.println ("\t|      Tick Size         : " + FormatUtil.FormatDouble (wn1.minimumPriceMovement(), 1, 6, 1.) + "                      ||");

		System.out.println ("\t|      Tick Value        : " + FormatUtil.FormatDouble (wn1.tickValue(), 1, 2, 1.) + "                       ||");

		System.out.println ("\t|      Delivery Months   : " + DeliveryMonths (wn1) + " ||");

		System.out.println ("\t|      Last Trading Lag  : " + wn1.lastTradingDayLag() + " Business Days Prior Expiry   ||");

		System.out.println ("\t|      Futures Price     : " + FormatUtil.FormatDouble (dblFuturesPrice, 2, 5, 1.) + "                     ||");

		System.out.println ("\t|      Contract Value    : " + FormatUtil.FormatDouble (0.01 * wn1.notionalValue() * dblFuturesPrice, 1, 2, 1.) + "                     ||");

		System.out.println ("\t|---------------------------------------------------------||\n");

		System.out.println ("\n\t|----------------------------------------------||");

		System.out.println ("\t|                                              ||");

		for (int i = 0; i < wn1.basket().length; ++i)
			System.out.println ("\t|\t" + wn1.basket()[i].name() + " => " + FormatUtil.FormatDouble (adblCleanPrice[i], 2, 5, 1.) + "   ||");

		System.out.println ("\t|                                              ||");

		System.out.println ("\t|----------------------------------------------||");

		System.out.println ("\t| Cheapest to Deliver: " + bondCTD.name() + "  ||");

		System.out.println ("\t|----------------------------------------------||");
	}
}
