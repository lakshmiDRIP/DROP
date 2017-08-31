
package org.drip.sample.forwardratefutures;

import org.drip.market.exchange.*;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * ShortTermFuturesDefinition illustrates the Construction and Usage of the Short Term Futures Exchange
 *  Details.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ShortTermFuturesDefinition {
	private static final void DisplayExchangeInfo (
		final String strFullyQualifiedNameName)
	{
		ShortTermFutures stf = ShortTermFuturesContainer.ExchangeInfo (strFullyQualifiedNameName);

		String strExchange = "";

		for (int i = 0; i < stf.exchanges().length; ++i) {
			strExchange += stf.exchanges()[i];

			if (0 != i) strExchange += " | ";
		}

		System.out.println ("\t[" +
			strFullyQualifiedNameName + "] => " +
			stf.notional() + " || " +
			strExchange
		);
	}

	public static final void main (
		String[] args)
	{
		EnvManager.InitEnv ("");

		System.out.println ("\n\t---------------\n\t---------------\n");

		DisplayExchangeInfo ("CAD-CDOR-3M");

		DisplayExchangeInfo ("CHF-LIBOR-3M");

		DisplayExchangeInfo ("DKK-CIBOR-3M");

		DisplayExchangeInfo ("EUR-EURIBOR-3M");

		DisplayExchangeInfo ("GBP-LIBOR-3M");

		DisplayExchangeInfo ("JPY-LIBOR-3M");

		DisplayExchangeInfo ("JPY-TIBOR-3M");

		DisplayExchangeInfo ("USD-LIBOR-1M");

		DisplayExchangeInfo ("USD-LIBOR-3M");

		DisplayExchangeInfo ("ZAR-JIBAR-3M");
	}
}
