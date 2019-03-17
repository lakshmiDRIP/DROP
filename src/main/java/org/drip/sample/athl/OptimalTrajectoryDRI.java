
package org.drip.sample.athl;

import org.drip.execution.athl.DynamicsParameters;
import org.drip.execution.dynamics.LinearPermanentExpectationParameters;
import org.drip.execution.nonadaptive.ContinuousPowerImpact;
import org.drip.execution.optimum.PowerImpactContinuous;
import org.drip.execution.parameters.AssetFlowSettings;
import org.drip.execution.strategy.DiscreteTradingTrajectory;
import org.drip.function.definition.R1ToR1;
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
 * <i>OptimalTrajectoryDRI</i> demonstrates the Trade Scheduling using the Equity Market Impact Functions
 * determined empirically by Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization of Almgren
 * (2003) for DRI. The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 *  	</li>
 *  	<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 *  	</li>
 *  	<li>
 * 			Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk
 * 				<i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18
 *  	</li>
 *  	<li>
 * 			Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102
 *  	</li>
 *  	<li>
 * 			Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact <i>Risk</i> <b>18
 * 				(7)</b> 57-62
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/athl/README.md">Almgren, Thum, Hauptmann, and Li (2005) Calibration</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OptimalTrajectoryDRI {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		String strAssetName = "DRI";
		double dblAverageDailyVolume = 1929000.;
		double dblSharesOutstanding = 168000000.;
		double dblDailyVolatility = 2.26;

		double dblTradeSize = 192900.;
		double dblTradeTime = 1.;
		int iNumInterval = 20;

		double dblRiskAversion = 1.e-02;

		AssetFlowSettings afs = new AssetFlowSettings (
			strAssetName,
			dblAverageDailyVolume,
			dblSharesOutstanding,
			dblDailyVolatility
		);

		LinearPermanentExpectationParameters lpep = new DynamicsParameters (afs).almgren2003();

		ContinuousPowerImpact cpi = ContinuousPowerImpact.Standard (
			dblTradeSize,
			dblTradeTime,
			lpep,
			dblRiskAversion
		);

		PowerImpactContinuous pic = (PowerImpactContinuous) cpi.generate();

		R1ToR1 r1ToR1Holdings = pic.holdings();

		double[] adblHoldings = new double[iNumInterval];
		double[] adblExecutionTime = new double[iNumInterval];

		for (int i = 1; i <= iNumInterval; ++i) {
			adblExecutionTime[i - 1] = dblTradeTime * i / iNumInterval;

			adblHoldings[i - 1] = r1ToR1Holdings.evaluate (adblExecutionTime[i - 1]);
		}

		DiscreteTradingTrajectory dtt = DiscreteTradingTrajectory.Standard (
			adblExecutionTime,
			adblHoldings
		);

		double[] adblTradeList = dtt.tradeList();

		System.out.println();

		System.out.println ("\t|-----------------------------------||");

		System.out.println ("\t| DRI ATHL 2005 Optimal Trajectory  ||");

		System.out.println ("\t|-----------------------------------||");

		System.out.println ("\t|       L -> R:                     ||");

		System.out.println ("\t|             - Execution Time Node ||");

		System.out.println ("\t|             - Holdings Remaining  ||");

		System.out.println ("\t|             - Trade List Amount   ||");

		System.out.println ("\t|             - Holdings (%)        ||");

		System.out.println ("\t|-----------------------------------||");

		for (int i = 1; i < adblExecutionTime.length; ++i)
			System.out.println (
				"\t| " +
				FormatUtil.FormatDouble (adblExecutionTime[i], 1, 2, 1.) + " | " +
				FormatUtil.FormatDouble (adblHoldings[i], 6, 0, 1.) + " | " + 
				FormatUtil.FormatDouble (adblTradeList[i - 1], 5, 0, 1.) + " | " + 
				FormatUtil.FormatDouble (adblHoldings[i] / dblTradeSize, 2, 1, 100.) + "% ||"
			);

		System.out.println ("\t|-----------------------------------||");

		System.out.println();

		System.out.println ("\t|---------------------------------------------------------------------||");

		System.out.println ("\t|      DRI ATHL 2005 Optimal Trajectory Transaction Cost Measures     ||");

		System.out.println ("\t|---------------------------------------------------------------------||");

		System.out.println (
			"\t| Transaction Cost Expectation ( X 10^-06)                  : " +
			FormatUtil.FormatDouble (pic.transactionCostExpectation(), 3, 2, 1.e-06) + " ||"
		);

		System.out.println (
			"\t| Transaction Cost Variance ( X 10^-09)                     : " +
			FormatUtil.FormatDouble (pic.transactionCostVariance(), 3, 2, 1.e-09) + " ||"
		);

		System.out.println (
			"\t| Characteristic Time                                       : " +
			FormatUtil.FormatDouble (pic.characteristicTime(), 3, 2, 1.) + " ||"
		);

		System.out.println (
			"\t| Efficient Frontier Hyperboloid Boundary Value ( X 10^-12) : " +
			FormatUtil.FormatDouble (pic.hyperboloidBoundaryValue(), 3, 2, 1.e-12) + " ||"
		);

		System.out.println ("\t|---------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
