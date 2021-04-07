
package org.drip.sample.almgren2009;

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
 * <i>AdaptiveOptimalRollingHorizonTrajectory</i> simulates the Outstanding Holdings and the Trade Rate from
 * the Sample Realization of the Rolling Horizon Approximation of the HJB Based Adaptive Cost Strategy using
 * the Market State Trajectory the follows the Zero Mean Ornstein-Uhlenbeck Evolution Dynamics. The
 * References are:
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

public class AdaptiveOptimalRollingHorizonTrajectory {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		double dblSize = 1.;
		int iNumTimeNode = 41;
		double dblBurstiness = 1.;
		double dblExecutionTime = 10.;
		double dblRelaxationTime = 1.;
		double dblReferenceLiquidity = 1.;
		double dblReferenceVolatility = 1.;
		double dblInitialMarketState = -0.5;
		double dblRiskAversion = 0.5;

		System.out.println();

		System.out.println ("\t||--------------------------------------------------------||");

		System.out.println ("\t||     ADAPTIVE OPTIMAL TRAJECTORY GENERATION INPUTS      ||");

		System.out.println ("\t||--------------------------------------------------------||");

		System.out.println (
			"\t|| Order Size                                 =>  " +
			FormatUtil.FormatDouble (dblSize, 1, 4, 1.) + " ||"
		);

		System.out.println (
			"\t|| Order Execution Time                       => " +
			FormatUtil.FormatDouble (dblExecutionTime, 2, 0, 1.) + "      ||"
		);

		System.out.println (
			"\t|| Ornstein Uhlenbeck Burstiness              =>  " +
			FormatUtil.FormatDouble (dblBurstiness, 1, 4, 1.) + " ||"
		);

		System.out.println (
			"\t|| Ornstein Uhlenbeck Relaxation Time         =>  " +
			FormatUtil.FormatDouble (dblRelaxationTime, 1, 4, 1.) + " ||"
		);

		System.out.println (
			"\t|| Coordinated Variation Reference Liquidity  =>  " +
			FormatUtil.FormatDouble (dblReferenceLiquidity, 1, 4, 1.) + " ||"
		);

		System.out.println (
			"\t|| Coordinated Variation Reference Volatility =>  " +
			FormatUtil.FormatDouble (dblReferenceVolatility, 1, 4, 1.) + " ||"
		);

		System.out.println (
			"\t|| Mean Variance Risk Aversion                =>  " +
			FormatUtil.FormatDouble (dblReferenceVolatility, 1, 4, 1.) + " ||"
		);

		System.out.println (
			"\t|| Number of Evolution Nodes                  => " +
			FormatUtil.FormatDouble (iNumTimeNode - 1, 2, 0, 1.) + "      ||"
		);

		System.out.println ("\t||--------------------------------------------------------||");

		System.out.println();

		double dblNonDimensionalTimeInterval = dblExecutionTime / (iNumTimeNode - 1) / dblRelaxationTime;

		OrderSpecification os = new OrderSpecification (
			dblSize,
			dblExecutionTime
		);

		CoordinatedVariation cv = new CoordinatedVariation (
			dblReferenceVolatility,
			dblReferenceLiquidity
		);

		DiffusionEvaluatorOrnsteinUhlenbeck deou = DiffusionEvaluatorOrnsteinUhlenbeck.ZeroMean (
			dblBurstiness,
			dblRelaxationTime
		);

		MarketState[] aMS = OrnsteinUhlenbeckSequence.Systemic (
			deou,
			dblNonDimensionalTimeInterval * dblRelaxationTime,
			dblInitialMarketState,
			iNumTimeNode
		).realizedMarketState();

		CoordinatedVariationRollingHorizon cvrh = new CoordinatedVariationTrajectoryGenerator (
			os,
			cv,
			new MeanVarianceObjectiveUtility (dblRiskAversion),
			NonDimensionalCostEvolverSystemic.Standard (deou),
			CoordinatedVariationTrajectoryGenerator.TRADE_RATE_STATIC_INITIALIZATION
		).rollingHorizon (aMS);

		double[] adblNonDimensionalHoldings = cvrh.nonDimensionalHoldings();

		double[] adblNonDimensionalTradeRate = cvrh.nonDimensionalTradeRate();

		double[] adblNonDimensionalCost = cvrh.nonDimensionalCost();

		System.out.println ("\t||-------------------------------------||");

		System.out.println ("\t||     ADAPTIVE OPTIMAL TRAJECTORY     ||");

		System.out.println ("\t||-------------------------------------||");

		System.out.println ("\t||     L -> R:                         ||");

		System.out.println ("\t||             - Time                  ||");

		System.out.println ("\t||             - Holdings              ||");

		System.out.println ("\t||             - Trade Rate            ||");

		System.out.println ("\t||             - Realized Cost         ||");

		System.out.println ("\t||-------------------------------------||");

		for (int i = 0; i < iNumTimeNode - 1; ++i) {
			String strDump = "\t|| " + FormatUtil.FormatDouble (i * dblNonDimensionalTimeInterval * dblRelaxationTime, 1, 2, 1.);

			strDump = strDump + " | " + FormatUtil.FormatDouble (adblNonDimensionalHoldings[i], 1, 4, 1.);

			strDump = strDump + " | " + FormatUtil.FormatDouble (adblNonDimensionalTradeRate[i], 1, 4, 1.);

			strDump = strDump + " | " + FormatUtil.FormatDouble (adblNonDimensionalCost[i], 1, 4, 1.);

			System.out.println (strDump + " ||");
		}

		System.out.println ("\t||-------------------------------------||");

		System.out.println();

		CoordinatedVariationTrajectoryDeterminant cvtd = cvrh.trajectoryDeterminant();

		System.out.println ("\t||---------------------------------||");

		System.out.println ("\t||    OPTIMAL TRAJECTORY OUTPUTS   ||");

		System.out.println ("\t||---------------------------------||");

		System.out.println (
			"\t|| Time Scale          =>  " +
			FormatUtil.FormatDouble (cvtd.timeScale(), 1, 4, 1.) + " ||"
		);

		System.out.println (
			"\t|| Trade Rate Scale    =>  " +
			FormatUtil.FormatDouble (cvtd.tradeRateScale(), 1, 4, 1.) + " ||"
		);

		System.out.println (
			"\t|| Cost Scale          =>  " +
			FormatUtil.FormatDouble (cvtd.costScale(), 1, 4, 1.) + " ||"
		);

		System.out.println (
			"\t|| Mean Market Urgency =>  " +
			FormatUtil.FormatDouble (cvtd.meanMarketUrgency(), 1, 4, 1.) + " ||"
		);

		System.out.println (
			"\t|| Market Power        =>  " +
			FormatUtil.FormatDouble (cvtd.marketPower(), 1, 4, 1.) + " ||"
		);

		System.out.println (
			"\t|| Risk Aversion Scale =>  " +
			FormatUtil.FormatDouble (cvtd.nonDimensionalRiskAversion(), 1, 4, 1.) + " ||"
		);

		System.out.println ("\t||---------------------------------||");

		EnvManager.TerminateEnv();
	}
}
