
package org.drip.sample.almgren2009;

import org.drip.function.r1tor1.AlmgrenEnhancedEulerUpdate;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>EnhancedEulerScheme</i> demonstrates the Enhancement used by Almgren (2009, 2012) to deal with Time
 * Evolution under Singular Initial Conditions. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 *  	</li>
 *  	<li>
 * 			Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 * 				https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf
 *  	</li>
 *  	<li>
 * 			Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility <i>SIAM Journal
 * 				of Financial Mathematics</i> <b>3 (1)</b> 163-181
 *  	</li>
 *  	<li>
 * 			Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes <i>Mathematical
 * 				Finance</i> <b>11 (1)</b> 79-96
 *  	</li>
 *  	<li>
 * 			Walia, N. (2006): Optimal Trading: Dynamic Stock Liquidation Strategies <b>Princeton
 * 				University</b>
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgren2009/README.md">Almgren (2009) Optimal Adaptive HJB</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EnhancedEulerScheme {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

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

		EnvManager.TerminateEnv();
	}
}
