
package org.drip.sample.almgren2009;

import org.drip.function.r1tor1.AlmgrenEnhancedEulerUpdate;
import org.drip.quant.common.FormatUtil;
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
 * EnhancedEulerScheme demonstrates the Enhancement used by Almgren (2009, 2012) to deal with Time Evolution
 * 	under Singular Initial Conditions. The References are:
 * 
 * 	- Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3
 * 		(2) 5-39.
 *
 * 	- Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 * 		https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf.
 *
 * 	- Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility, SIAM Journal of
 * 		Financial Mathematics  3 (1) 163-181.
 * 
 * 	- Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes, Mathematical Finance 11 (1)
 * 		79-96.
 * 
 * 	- Walia, N. (2006): Optimal Trading: Dynamic Stock Liquidation Strategies, Senior Thesis, Princeton
 * 		University.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EnhancedEulerScheme {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblA = 2.;
		double dblB = 1.;
		double dblTimeIncrement = 0.1;
		double dblSimulationTime = 1.0;
		int iK = 2;

		int iNumSimulationSteps = (int) (dblSimulationTime / dblTimeIncrement);
		double dblInitialOrder0 = 1. / (iK * dblTimeIncrement);
		double dblInitialOrder1 = dblInitialOrder0 + 0.5 * (dblA + dblB);
		double dblOrder0Euler = dblInitialOrder0;
		double dblOrder1Euler = dblInitialOrder1;
		double dblOrder0EnhancedEuler = dblInitialOrder0;
		double dblOrder1EnhancedEuler = dblInitialOrder1;

		AlmgrenEnhancedEulerUpdate aeeu = new AlmgrenEnhancedEulerUpdate (
			dblA,
			dblB
		);

		System.out.println();

		System.out.println ("\t||----------------------------------------------------||");

		System.out.println ("\t||      L -> R:                                       ||");

		System.out.println ("\t||            - Time                                  ||");

		System.out.println ("\t||            - Exact Solution                        ||");

		System.out.println ("\t||            - Order 1 Initial + Enhanced Euler      ||");

		System.out.println ("\t||            - Order 0 Initial + Enhanced Euler      ||");

		System.out.println ("\t||            - Order 1 Initial + Regular Euler       ||");

		System.out.println ("\t||            - Order 0 Initial + Regular Euler       ||");

		System.out.println ("\t||----------------------------------------------------||");

		for (int i = iK; i <= iNumSimulationSteps; ++i) {
			double dblTime = i * dblTimeIncrement;

			System.out.println (
				"\t|| " +
				FormatUtil.FormatDouble (dblTime, 1, 1, 1.) + " => " +
				FormatUtil.FormatDouble (aeeu.evaluate (dblTime), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (dblOrder1EnhancedEuler, 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (dblOrder0EnhancedEuler, 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (dblOrder1Euler, 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (dblOrder0Euler, 1, 3, 1.) + " ||"
			);

			double dblOrder0EulerIncrement = -1. * (dblOrder0Euler - dblA) * (dblOrder0Euler - dblB) * dblTimeIncrement;
			double dblOrder1EulerIncrement = -1. * (dblOrder1Euler - dblA) * (dblOrder1Euler - dblB) * dblTimeIncrement;
			dblOrder0Euler = dblOrder0Euler + dblOrder0EulerIncrement;
			dblOrder1Euler = dblOrder1Euler + dblOrder1EulerIncrement;
			double dblOrder0EnhancedEulerIncrement = -1. * (dblOrder0EnhancedEuler - dblA) * (dblOrder0EnhancedEuler - dblB)
				* dblTimeIncrement * iK / (iK + 1);
			dblOrder0EnhancedEuler = dblOrder0EnhancedEuler + dblOrder0EnhancedEulerIncrement;
			double dblOrder1EnhancedEulerIncrement = -1. * (dblOrder1EnhancedEuler - dblA) * (dblOrder1EnhancedEuler - dblB)
				* dblTimeIncrement * iK / (iK + 1);
			dblOrder1EnhancedEuler = dblOrder1EnhancedEuler + dblOrder1EnhancedEulerIncrement;
		}

		System.out.println ("\t||----------------------------------------------------||");

		System.out.println();
	}
}
