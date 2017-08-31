
package org.drip.sample.treasurypnl;

import org.drip.feed.metric.TreasuryBondPnLAttributor;
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
 * NGBBenchmarkAttribution demonstrates the Computation of the PnL Time Series Metrics for the NGB Benchmark
 *  Bond Series.
 *
 * @author Lakshmi Krishnamurthy
 */

public class NGBBenchmarkAttribution {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String strTreasuryCode = "NGB";

		String strTreasuryMarkLocation = "C:\\DRIP\\CreditAnalytics\\Daemons\\Transforms\\TreasuryYieldMarks\\" + strTreasuryCode +
			"BenchmarksReconstituted.csv";

		String[] astrMaturityTenor = new String[] {
			 "1Y",
			 "2Y",
			 "3Y",
			 "4Y",
			 "5Y",
			 "6Y",
			 "7Y",
			 "8Y",
			 "9Y",
			"10Y",
			"11Y",
			"12Y",
			"15Y",
			"20Y",
			"25Y"
		};

		int[] aiHorizonGap = new int[] {
			1,
			// 22,
			// 67
		};

		String[] astrGovvieTreasuryTenor = new String[] {
			 "2Y",
			 "3Y",
			 "4Y",
			 "5Y",
			 "7Y",
			"10Y",
			"20Y",
			"30Y"
		};

		int[] aiGovvieTreasuryColumn = new int[] {
			1,
			2,
			3,
			4,
			5,
			6,
			7,
			8
		};

		String[] astrRollDownHorizonMap = new String[] {
			"1M",
			"3M"
		};

		TreasuryBondPnLAttributor.TenorHorizonExplainComponents (
			astrMaturityTenor,
			strTreasuryCode,
			aiHorizonGap,
			strTreasuryMarkLocation,
			astrGovvieTreasuryTenor,
			aiGovvieTreasuryColumn,
			astrRollDownHorizonMap
		);
	}
}
