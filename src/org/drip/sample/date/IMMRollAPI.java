
package org.drip.sample.date;

import org.drip.analytics.date.*;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * IMMRollAPI demonstrates the API used to generate IMM Rolled Dates specific to different Products.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class IMMRollAPI {

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dt = DateUtil.CreateFromYMD (
			2010,
			DateUtil.DECEMBER,
			30
		);

		int iDateGap = 10;
		int iNumDate = 30;

		System.out.println ("\n\n\t|--------------------------------------------------------------------------------||");

		System.out.println ("\t|  SPOT DATE | NEXT RATES FUTURES IMM | NEXT BOND FUTURES IMM | NEXT CDS/CDX IMM ||");

		System.out.println ("\t|--------------------------------------------------------------------------------||");

		for (int i = 0; i < iNumDate; ++i) {
			System.out.println ("\t| " + dt + " |       " +
				dt.nextRatesFuturesIMM (3) + "       |       " +
				dt.nextBondFuturesIMM (3, "USD") + "      |    " +
				dt.nextCreditIMM (3) + "    ||"
			);

			dt = dt.addDays (iDateGap);
		}

		System.out.println ("\t|--------------------------------------------------------------------------------||");
	}
}
