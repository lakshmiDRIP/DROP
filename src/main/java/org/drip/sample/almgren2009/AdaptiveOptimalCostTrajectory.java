
package org.drip.sample.almgren2009;

import org.drip.execution.hjb.*;
import org.drip.execution.latent.MarketStateSystemic;
import org.drip.measure.dynamics.DiffusionEvaluatorOrnsteinUhlenbeck;
import org.drip.measure.process.DiffusionEvolver;
import org.drip.measure.realization.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>AdaptiveOptimalCostTrajectory</i> traces a Sample Realization of the Adaptive Cost Strategy using the
 * Market State Trajectory the follows the Zero Mean Ornstein-Uhlenbeck Evolution Dynamics. The References
 * are:
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

public class AdaptiveOptimalCostTrajectory {

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		double dblTime = 0.;
		double dblBurstiness = 1.;
		double dblDimensionlessRiskAversion = 0.1;
		double dblRelaxationTime = 1.;
		double dblSimulationTime = 10.;
		double dblTimeInterval = 0.25;
		double dblInitialMarketState = -0.5;

		double dblNonDimensionalHoldings = 1.;
		int iNumTimeNode = (int) (dblSimulationTime / dblTimeInterval);
		MarketStateSystemic[] aMSS = new MarketStateSystemic[iNumTimeNode + 1];

		aMSS[0] = new MarketStateSystemic (dblInitialMarketState);

		DiffusionEvaluatorOrnsteinUhlenbeck deou = DiffusionEvaluatorOrnsteinUhlenbeck.ZeroMean (
			dblBurstiness,
			dblRelaxationTime
		);

		DiffusionEvolver oup1D = new DiffusionEvolver (deou);

		for (int i = 0; i < iNumTimeNode; ++i) {
			JumpDiffusionEdge gi = oup1D.weinerIncrement (
				new JumpDiffusionVertex (
					dblTime,
					aMSS[i].common(),
					0.,
					false
				),
				dblTimeInterval
			);

			dblTime += dblTimeInterval;

			aMSS[i + 1] = new MarketStateSystemic (aMSS[i].common() + gi.deterministic() + gi.diffusionStochastic());
		}

		NonDimensionalCostEvolverSystemic ndces = NonDimensionalCostEvolverSystemic.Standard (deou);

		NonDimensionalCostSystemic ndcs = NonDimensionalCostSystemic.Zero();

		System.out.println();

		System.out.println ("\t||-------------------------------------------------------------------||");

		System.out.println ("\t||      L -> R:                                                      ||");

		System.out.println ("\t||              - Non Dimensional Time                               ||");

		System.out.println ("\t||              - Realized Market State                              ||");

		System.out.println ("\t||              - Non Dimensional Cost                               ||");

		System.out.println ("\t||              - Non Dimensional Cost Gradient                      ||");

		System.out.println ("\t||              - Non Dimensional Cost Jacobian                      ||");

		System.out.println ("\t||              - Non Dimensional Cost Trade Velocity                ||");

		System.out.println ("\t||              - Non Dimensional Outstanding Holdings               ||");

		System.out.println ("\t||-------------------------------------------------------------------||");

		System.out.println ("\t||" + 
			FormatUtil.FormatDouble (0., 1, 2, 1.) + " => " +
			FormatUtil.FormatDouble (aMSS[0].common(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (ndcs.realization(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (ndcs.gradient(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (ndcs.jacobian(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (ndcs.nonDimensionalTradeRate(), 1, 4, 1.) + " | " +
			FormatUtil.FormatDouble (dblNonDimensionalHoldings, 1, 4, 1.) + " ||"
		);

		for (int i = 1; i < iNumTimeNode; ++i) {
			ndcs = (NonDimensionalCostSystemic) ndces.evolve (
				ndcs,
				aMSS[i],
				dblDimensionlessRiskAversion,
				(iNumTimeNode - i) * dblTimeInterval,
				dblTimeInterval
			);

			double dblNonDimensionalTradeRate = dblNonDimensionalHoldings * ndcs.nonDimensionalTradeRate();

			dblNonDimensionalHoldings = dblNonDimensionalHoldings - dblNonDimensionalTradeRate * dblTimeInterval;

			System.out.println ("\t||" + 
				FormatUtil.FormatDouble (dblTimeInterval * i, 1, 2, 1.) + " => " +
				FormatUtil.FormatDouble (aMSS[i].common(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (ndcs.realization(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (ndcs.gradient(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (ndcs.jacobian(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (dblNonDimensionalTradeRate, 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (dblNonDimensionalHoldings, 1, 4, 1.) + " ||"
			);
		}

		System.out.println ("\t||-------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
