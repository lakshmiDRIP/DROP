
package org.drip.sample.floatfloat;

import org.drip.market.otc.*;
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
 * JurisdictionOTCIndexDefinitions contains all the pre-fixed Definitions of the Jurisdiction OTC Float-Float
 *  Swap Contracts.
 *
 * @author Lakshmi Krishnamurthy
 */

public class JurisdictionOTCIndexDefinitions {
	private static final void DisplayOTCInfo (
		String strCurrency)
	{
		FloatFloatSwapConvention ffConv = IBORFloatFloatContainer.ConventionFromJurisdiction (strCurrency);

		System.out.println (
			"\t\t" + strCurrency + " => " +
			ffConv.referenceTenor() + " | " +
			ffConv.spotLag() + " | " +
			ffConv.basisOnDerivedStream() + " | " +
			ffConv.basisOnDerivedComponent() + " | " +
			ffConv.derivedCompoundedToReference() + " | " +
			ffConv.componentPair()
		);
	}

	public static final void main (
		String[] args)
	{
		EnvManager.InitEnv ("");

		System.out.println ("\n\t--------------------------------------------------------------------------------------------------------");

		System.out.println ("\t\tL -> R:");

		System.out.println ("\t\t\tCurrency");

		System.out.println ("\t\t\tReference Tenor");

		System.out.println ("\t\t\tSpot Lag");

		System.out.println ("\t\t\tBasis on Derived Stream");

		System.out.println ("\t\t\tBasis on Derived Component");

		System.out.println ("\t\t\tDerived Stream Compounded To Reference Stream");

		System.out.println ("\t\t\tComponent Pair");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------");

		DisplayOTCInfo ("AUD");

		DisplayOTCInfo ("CAD");

		DisplayOTCInfo ("CHF");

		DisplayOTCInfo ("CNY");

		DisplayOTCInfo ("DKK");

		DisplayOTCInfo ("EUR");

		DisplayOTCInfo ("GBP");

		DisplayOTCInfo ("HKD");

		DisplayOTCInfo ("INR");

		DisplayOTCInfo ("JPY");

		DisplayOTCInfo ("NOK");

		DisplayOTCInfo ("NZD");

		DisplayOTCInfo ("PLN");

		DisplayOTCInfo ("SEK");

		DisplayOTCInfo ("SGD");

		DisplayOTCInfo ("USD");

		DisplayOTCInfo ("ZAR");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------");
	}
}
