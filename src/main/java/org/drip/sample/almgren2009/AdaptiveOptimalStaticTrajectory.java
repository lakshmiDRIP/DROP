
package org.drip.sample.almgren2009;

import org.drip.execution.adaptive.*;
import org.drip.execution.hjb.NonDimensionalCostEvolverSystemic;
import org.drip.execution.optimum.EfficientTradingTrajectoryContinuous;
import org.drip.execution.risk.MeanVarianceObjectiveUtility;
import org.drip.execution.strategy.OrderSpecification;
import org.drip.execution.tradingtime.CoordinatedVariation;
import org.drip.function.definition.R1ToR1;
import org.drip.measure.dynamics.DiffusionEvaluatorOrnsteinUhlenbeck;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>AdaptiveOptimalStaticTrajectory</i> determines the Outstanding Holdings and the Trade Rate from the
 * "Mean Market State" Static Trajectory using the Market State Trajectory the follows the Zero Mean
 * Ornstein-Uhlenbeck Evolution Dynamics. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgren2009/README.md">Almgren (2009)</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class AdaptiveOptimalStaticTrajectory {

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

		DiffusionEvaluatorOrnsteinUhlenbeck oup1D = DiffusionEvaluatorOrnsteinUhlenbeck.ZeroMean (
			dblBurstiness,
			dblRelaxationTime
		);

		CoordinatedVariationStatic cvs = new CoordinatedVariationTrajectoryGenerator (
			os,
			cv,
			new MeanVarianceObjectiveUtility (dblRiskAversion),
			NonDimensionalCostEvolverSystemic.Standard (oup1D),
			CoordinatedVariationTrajectoryGenerator.TRADE_RATE_STATIC_INITIALIZATION
		).nonAdaptive();

		EfficientTradingTrajectoryContinuous ettc = cvs.trajectory();

		R1ToR1 r1ToR1Holdings = ettc.holdings();

		R1ToR1 r1ToR1TradeRate = ettc.tradeRate();

		R1ToR1 r1ToR1TransactionCostExpectation = ettc.transactionCostExpectationFunction();

		System.out.println ("\t||-------------------------------------||");

		System.out.println ("\t||     ADAPTIVE OPTIMAL TRAJECTORY     ||");

		System.out.println ("\t||-------------------------------------||");

		System.out.println ("\t||     L -> R:                         ||");

		System.out.println ("\t||             - Time                  ||");

		System.out.println ("\t||             - Holdings              ||");

		System.out.println ("\t||             - Trade Rate            ||");

		System.out.println ("\t||             - Realized Cost         ||");

		System.out.println ("\t||-------------------------------------||");

		double dblInitialNonDimensionalCost = r1ToR1TransactionCostExpectation.evaluate (0.);

		for (int i = 0; i < iNumTimeNode - 1; ++i) {
			String strDump = "\t|| " + FormatUtil.FormatDouble (i * dblNonDimensionalTimeInterval * dblRelaxationTime, 1, 2, 1.);

			strDump = strDump + " | " + FormatUtil.FormatDouble (r1ToR1Holdings.evaluate (i * dblNonDimensionalTimeInterval), 1, 4, 1.);

			strDump = strDump + " | " + FormatUtil.FormatDouble (r1ToR1TradeRate.evaluate (i * dblNonDimensionalTimeInterval), 1, 4, 1.);

			strDump = strDump + " | " + FormatUtil.FormatDouble (dblInitialNonDimensionalCost - r1ToR1TransactionCostExpectation.evaluate (i * dblNonDimensionalTimeInterval), 1, 4, 1.);

			System.out.println (strDump + " ||");
		}

		System.out.println ("\t||-------------------------------------||");

		System.out.println();

		CoordinatedVariationTrajectoryDeterminant cvtd = cvs.trajectoryDeterminant();

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
