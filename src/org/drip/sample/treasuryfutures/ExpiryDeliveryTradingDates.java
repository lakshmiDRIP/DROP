
package org.drip.sample.treasuryfutures;

import org.drip.analytics.date.*;
import org.drip.market.exchange.*;
import org.drip.service.env.EnvManager;

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
 * ExpiryDeliveryTradingDates illustrates Generation of Event Dates from the Expiry Month/Year of the Bond
 *  Futures Contracts.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ExpiryDeliveryTradingDates {

	private static final void DisplayEventDateInfo (
		String strCurrency,
		String strUnderlierType,
		String strUnderlierSubtype,
		String strMaturityTenor,
		JulianDate dtSettle)
		throws Exception
	{
		TreasuryFuturesConvention bfc = TreasuryFuturesConventionContainer.FromJurisdictionTypeMaturity (
			strCurrency,
			strUnderlierType,
			strUnderlierSubtype,
			strMaturityTenor
		);

		System.out.println ("\t| " +
			bfc.eventDates (
				DateUtil.Year (dtSettle.julian()),
				DateUtil.Month (dtSettle.julian())
			) + " | [" +
			strCurrency + "-" +
			strUnderlierType + "-" +
			strUnderlierSubtype + "-" +
			strMaturityTenor + "]"
		);
	}

	public static final void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		System.out.println();

		java.lang.String strForwardTenor = "3M";

		JulianDate dtToday = DateUtil.Today().addTenor (strForwardTenor);

		System.out.println ("\t|------------------------------------------------------------------------------------------------|");

		System.out.println ("\t|   EXPIRY   | DELIV START |  DELIV END | DELIV NOTICE | LAST TRADE |           FUTURE           |");

		System.out.println ("\t|------------------------------------------------------------------------------------------------|");

		DisplayEventDateInfo ("AUD", "BANK", "BILLS", "3M", dtToday);

		DisplayEventDateInfo ("AUD", "TREASURY", "BOND", "3Y", dtToday);

		DisplayEventDateInfo ("AUD", "TREASURY", "BOND", "10Y", dtToday);

		DisplayEventDateInfo ("EUR", "EURO", "SCHATZ", "2Y", dtToday);

		DisplayEventDateInfo ("EUR", "EURO", "BOBL", "5Y", dtToday);

		DisplayEventDateInfo ("EUR", "EURO", "BUND", "10Y", dtToday);

		DisplayEventDateInfo ("EUR", "EURO", "BUXL", "30Y", dtToday);

		DisplayEventDateInfo ("EUR", "TREASURY", "BONO", "10Y", dtToday);

		DisplayEventDateInfo ("GBP", "SHORT", "GILT", "2Y", dtToday);

		DisplayEventDateInfo ("GBP", "MEDIUM", "GILT", "5Y", dtToday);

		DisplayEventDateInfo ("GBP", "LONG", "GILT", "10Y", dtToday);

		DisplayEventDateInfo ("JPY", "TREASURY", "JGB", "5Y", dtToday);

		DisplayEventDateInfo ("JPY", "TREASURY", "JGB", "10Y", dtToday);

		DisplayEventDateInfo ("USD", "TREASURY", "NOTE", "2Y", dtToday);

		DisplayEventDateInfo ("USD", "TREASURY", "NOTE", "3Y", dtToday);

		DisplayEventDateInfo ("USD", "TREASURY", "NOTE", "5Y", dtToday);

		DisplayEventDateInfo ("USD", "TREASURY", "NOTE", "10Y", dtToday);

		DisplayEventDateInfo ("USD", "TREASURY", "BOND", "30Y", dtToday);

		DisplayEventDateInfo ("USD", "TREASURY", "BOND", "ULTRA", dtToday);

		System.out.println ("\t|------------------------------------------------------------------------------------------------|\n");
	}
}
