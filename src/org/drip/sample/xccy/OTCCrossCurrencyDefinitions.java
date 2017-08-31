
package org.drip.sample.xccy;

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
 * OTCFloatFloatDefinitions contains all the pre-fixed Definitions of the OTC Cross-Currency Float-Float Swap
 * 	Contracts.
 *
 * @author Lakshmi Krishnamurthy
 */

public class OTCCrossCurrencyDefinitions {
	public static final void main (
		String[] args)
	{
		EnvManager.InitEnv ("");

		System.out.println ("\n\t--------------------------------------------------------------------------------------------------------");

		System.out.println ("\t\tL -> R:");

		System.out.println ("\t\t\tReference Currency");

		System.out.println ("\t\t\tReference Tenor");

		System.out.println ("\t\t\tQuote Basis on Reference");

		System.out.println ("\t\t\tDerived Currency");

		System.out.println ("\t\t\tDerived Tenor");

		System.out.println ("\t\t\tQuote Basis on Derived");

		System.out.println ("\t\t\tFixing Setting Type");

		System.out.println ("\t\t\tSpot Lag in Business Days");

		System.out.println ("\t--------------------------------------------------------------------------------------------------------");

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("AUD"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("CAD"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("CHF"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("CLP"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("DKK"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("EUR"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("GBP"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("JPY"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("MXN"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("NOK"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("PLN"));

		System.out.println ("\t\t" + CrossFloatConventionContainer.ConventionFromJurisdiction ("SEK"));

		System.out.println ("\t--------------------------------------------------------------------------------------------------------");
	}
}
