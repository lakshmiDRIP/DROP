
package org.drip.sample.almgren2012;

import org.drip.execution.adaptive.*;
import org.drip.execution.hjb.NonDimensionalCostEvolverSystemic;
import org.drip.execution.latent.*;
import org.drip.execution.risk.MeanVarianceObjectiveUtility;
import org.drip.execution.strategy.OrderSpecification;
import org.drip.execution.tradingtime.CoordinatedVariation;
import org.drip.measure.dynamics.DiffusionEvaluatorOrnsteinUhlenbeck;
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
 * <i>AdaptiveZeroInitialHoldings</i> simulates the Outstanding Holdings from the Sample Realization of the
 * Adaptive Cost Strategy using the Market State Trajectory the follows the Zero Mean Ornstein-Uhlenbeck
 * Evolution Dynamics. The Initial Dynamics is derived from the "Mean Market State" Initial Static
 * Trajectory. The Initial Dynamics corresponds to the Zero Cost, Zero Cost Sensitivities, and Zero Trade
 * Rate. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgren2012/README.md">Almgren (2012) Dynamic Optimal Adaptive</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AdaptiveZeroInitialHoldings {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		double dblSize = 1.;
		int iNumTimeNode = 51;
		double dblBurstiness = 1.;
		double dblExecutionTime = 10.;
		double dblRelaxationTime = 1.;
		double dblReferenceLiquidity = 1.;
		double dblReferenceVolatility = 1.;
		double dblInitialMarketState = -0.5;
		double[] adblRiskAversion = new double[] {
			0.01,
			0.04,
			0.09,
			0.16,
			0.36,
			0.64,
			1.00
		};

		double dblNonDimensionalTimeInterval = dblExecutionTime / (iNumTimeNode - 1) / dblRelaxationTime;
		double[][] aadblNonDimensionalHoldings = new double[adblRiskAversion.length][];

		OrderSpecification os = new OrderSpecification (
			dblSize,
			dblExecutionTime
		);

		CoordinatedVariation cv = new CoordinatedVariation (
			dblReferenceVolatility,
			dblReferenceLiquidity
		);

		DiffusionEvaluatorOrnsteinUhlenbeck oup1D = DiffusionEvaluatorOrnsteinUhlenbeck.ZeroMean (
			dblBurstiness,
			dblRelaxationTime
		);

		MarketState[] aMS = OrnsteinUhlenbeckSequence.Systemic (
			oup1D,
			dblNonDimensionalTimeInterval * dblRelaxationTime,
			dblInitialMarketState,
			iNumTimeNode
		).realizedMarketState();

		for (int i = 0; i < adblRiskAversion.length; ++i)
			aadblNonDimensionalHoldings[i] = new CoordinatedVariationTrajectoryGenerator (
				os,
				cv,
				new MeanVarianceObjectiveUtility (adblRiskAversion[i]),
				NonDimensionalCostEvolverSystemic.Standard (oup1D),
				CoordinatedVariationTrajectoryGenerator.TRADE_RATE_ZERO_INITIALIZATION
			).adaptive (aMS).nonDimensionalHoldings();

		System.out.println();

		System.out.println ("\t||-----------------------------------------------------------------------------||");

		System.out.println ("\t||                     ADAPTIVE OPTIMAL TRAJECTORY HOLDINGS                    ||");

		System.out.println ("\t||-----------------------------------------------------------------------------||");

		System.out.println ("\t||     L -> R:                                                                 ||");

		System.out.println ("\t||             - Time                                                          ||");

		for (int j = 0; j < adblRiskAversion.length; ++j)
			System.out.println (
				"\t||             - Non Dimensional Risk Aversion =>" +
				FormatUtil.FormatDouble (dblRelaxationTime * dblReferenceVolatility * Math.sqrt (adblRiskAversion[j] / dblReferenceLiquidity), 1, 2, 1.) +
				"                         ||"
			);

		System.out.println ("\t||-----------------------------------------------------------------------------||");

		for (int i = 0; i < iNumTimeNode - 1; ++i) {
			String strDump = "\t|| " + FormatUtil.FormatDouble (i * dblNonDimensionalTimeInterval * dblRelaxationTime, 1, 2, 1.);

			for (int j = 0; j < adblRiskAversion.length; ++j)
				strDump = strDump + " | " + FormatUtil.FormatDouble (aadblNonDimensionalHoldings[j][i], 1, 4, 1.);

			System.out.println (strDump + " ||");
		}

		System.out.println ("\t||-----------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
