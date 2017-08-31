
package org.drip.sample.treasuryfutures;

import org.drip.market.exchange.TreasuryFuturesConventionContainer;
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
 * ContractEligibilitySettlementDefinitions contains all the pre-fixed Definitions of the Bond Futures
 *  Contracts.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ContractEligibilitySettlementDefinitions {

	private static final void DisplayBondFuturesInfo (
		String strCurrency,
		String strUnderlierType,
		String strUnderlierSubtype,
		String strMaturityTenor)
	{
		System.out.println ("--------------------------------------------------------------------------------------------------------\n");

		System.out.println ("\t" +
			TreasuryFuturesConventionContainer.FromJurisdictionTypeMaturity (
				strCurrency,
				strUnderlierType,
				strUnderlierSubtype,
				strMaturityTenor
			)
		);
	}

	public static final void main (
		final String[] args)
	{
		EnvManager.InitEnv ("");

		System.out.println();

		DisplayBondFuturesInfo ("AUD", "BANK", "BILLS", "3M");

		DisplayBondFuturesInfo ("AUD", "TREASURY", "BOND", "3Y");

		DisplayBondFuturesInfo ("AUD", "TREASURY", "BOND", "10Y");

		DisplayBondFuturesInfo ("EUR", "EURO", "SCHATZ", "2Y");

		DisplayBondFuturesInfo ("EUR", "EURO", "BOBL", "5Y");

		DisplayBondFuturesInfo ("EUR", "EURO", "BUND", "10Y");

		DisplayBondFuturesInfo ("EUR", "EURO", "BUXL", "30Y");

		DisplayBondFuturesInfo ("EUR", "TREASURY", "BONO", "10Y");

		DisplayBondFuturesInfo ("GBP", "SHORT", "GILT", "2Y");

		DisplayBondFuturesInfo ("GBP", "MEDIUM", "GILT", "5Y");

		DisplayBondFuturesInfo ("GBP", "LONG", "GILT", "10Y");

		DisplayBondFuturesInfo ("JPY", "TREASURY", "JGB", "5Y");

		DisplayBondFuturesInfo ("JPY", "TREASURY", "JGB", "10Y");

		DisplayBondFuturesInfo ("USD", "TREASURY", "NOTE", "2Y");

		DisplayBondFuturesInfo ("USD", "TREASURY", "NOTE", "3Y");

		DisplayBondFuturesInfo ("USD", "TREASURY", "NOTE", "5Y");

		DisplayBondFuturesInfo ("USD", "TREASURY", "NOTE", "10Y");

		DisplayBondFuturesInfo ("USD", "TREASURY", "BOND", "30Y");

		DisplayBondFuturesInfo ("USD", "TREASURY", "BOND", "ULTRA");

		System.out.println ("--------------------------------------------------------------------------------------------------------\n");
	}
}
