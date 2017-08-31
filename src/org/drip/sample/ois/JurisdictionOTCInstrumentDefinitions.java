
package org.drip.sample.ois;

import org.drip.market.otc.OvernightFixedFloatContainer;
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
 * JurisdictionOTCInstrumentDefinitions contains all the pre-fixed definitions of the Jurisdiction OTC OIS
 *  Instrument Contracts.
 *
 * @author Lakshmi Krishnamurthy
 */

public class JurisdictionOTCInstrumentDefinitions {
	private static final void DisplayOvernightOTCInfo (
		String strCurrency)
	{
		System.out.println (
			"\t" + strCurrency + " => " +
			OvernightFixedFloatContainer.FundConventionFromJurisdiction (
				strCurrency
			)
		);
	}

	public static final void main (
		final String[] args)
	{
		EnvManager.InitEnv ("");

		System.out.println ("\n\t--------------------------------------------------------------------------------------------------------\n");

		DisplayOvernightOTCInfo ("AUD");

		DisplayOvernightOTCInfo ("CAD");

		DisplayOvernightOTCInfo ("GBP");

		DisplayOvernightOTCInfo ("EUR");

		DisplayOvernightOTCInfo ("INR");

		DisplayOvernightOTCInfo ("JPY");

		DisplayOvernightOTCInfo ("SGD");

		DisplayOvernightOTCInfo ("USD");

		System.out.println ("\n\t--------------------------------------------------------------------------------------------------------\n");
	}
}
