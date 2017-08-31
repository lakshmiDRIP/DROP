
package org.drip.sample.fixfloat;

import org.drip.market.otc.IBORFixedFloatContainer;
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
 * JurisdictionOTCIndexDefinitions contains all the pre-fixed definitions of the Jurisdiction-specific OTC
 *  Fix-Float IRS contracts.
 *
 * @author Lakshmi Krishnamurthy
 */

public class JurisdictionOTCIndexDefinitions {
	private static final void DisplayIRSOTCInfo (
		String strCurrency,
		String strLocation,
		String strMaturityTenor,
		String strIndex)
	{
		System.out.println (
			"\t" + strCurrency + "-" + strLocation + "-" + strMaturityTenor + "-" + strIndex + " => " +
			IBORFixedFloatContainer.ConventionFromJurisdiction (
				strCurrency,
				strLocation,
				strMaturityTenor,
				strIndex
			)
		);
	}

	public static final void main (
		final String[] args)
	{
		EnvManager.InitEnv ("");

		System.out.println ("\n\t--------------------------------------------------------------------------------------------------------\n");

		DisplayIRSOTCInfo ("AUD", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("AUD", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("BRL", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("BRL", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("CAD", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("CAD", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("CHF", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("CHF", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("CNY", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("CNY", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("DKK", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("DKK", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("EUR", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("EUR", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("GBP", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("GBP", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("HKD", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("HKD", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("INR", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("INR", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("JPY", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("JPY", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("JPY", "ALL", "1Y", "TIBOR");

		DisplayIRSOTCInfo ("JPY", "ALL", "5Y", "TIBOR");

		DisplayIRSOTCInfo ("KRW", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("KRW", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("MYR", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("MYR", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("NOK", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("NOK", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("NZD", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("NZD", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("PLN", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("PLN", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("SEK", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("SEK", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("SGD", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("SGD", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("THB", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("THB", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("TWD", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("TWD", "ALL", "5Y", "MAIN");

		DisplayIRSOTCInfo ("USD", "LON", "1Y", "MAIN");

		DisplayIRSOTCInfo ("USD", "LON", "5Y", "MAIN");

		DisplayIRSOTCInfo ("USD", "NYC", "1Y", "MAIN");

		DisplayIRSOTCInfo ("USD", "NYC", "5Y", "MAIN");

		DisplayIRSOTCInfo ("ZAR", "ALL", "1Y", "MAIN");

		DisplayIRSOTCInfo ("ZAR", "ALL", "5Y", "MAIN");

		System.out.println ("\n\t--------------------------------------------------------------------------------------------------------\n");
	}
}
