
package org.drip.sample.almgren2003;

import org.drip.execution.capture.*;
import org.drip.execution.dynamics.*;
import org.drip.execution.impact.ParticipationRateLinear;
import org.drip.execution.nonadaptive.ContinuousConstantTradingEnhanced;
import org.drip.execution.optimum.EfficientTradingTrajectoryContinuous;
import org.drip.execution.profiletime.UniformParticipationRateLinear;
import org.drip.execution.strategy.DiscreteTradingTrajectory;
import org.drip.function.definition.R1ToR1;
import org.drip.measure.gaussian.R1UnivariateNormal;
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
 * <i>ConstantTradingEnhancedVolatility</i> demonstrates the Generation of the Optimal Trading Trajectory
 * under the Condition of Constant Trading Enhanced Volatility. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 *  	</li>
 * 
 *  	<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 *  	</li>
 * 
 *  	<li>
 * 			Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk
 * 				<i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18.
 *  	</li>
 * 
 *  	<li>
 * 			Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102
 *  	</li>
 * 
 *  	<li>
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgren2003/README.md">Almgren (2003) Power Law Liquidity</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ConstantTradingEnhancedVolatility {

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

		double dblEta = 5.e-06;
		double dblAlpha = 1.;
		double dblSigma = 1.;
		double dblLambda = 1.e-05;
		double dblX = 100000.;
		double dblT = 5.;
		int iNumInterval = 50000;

		ArithmeticPriceEvolutionParameters apep = ArithmeticPriceEvolutionParametersBuilder.TradingEnhancedVolatility (
			dblSigma,
			new UniformParticipationRateLinear (ParticipationRateLinear.SlopeOnly (dblEta)),
			new UniformParticipationRateLinear (
				new ParticipationRateLinear (
					dblAlpha,
					0.
				)
			)
		);

		ContinuousConstantTradingEnhanced ccte = ContinuousConstantTradingEnhanced.Standard (
			dblX,
			dblT,
			apep,
			dblLambda
		);

		EfficientTradingTrajectoryContinuous ettc = (EfficientTradingTrajectoryContinuous) ccte.generate();

		R1ToR1 r1ToR1Holdings = ettc.holdings();

		double[] adblHoldings = new double[iNumInterval];
		double[] adblExecutionTime = new double[iNumInterval];

		for (int i = 1; i <= iNumInterval; ++i) {
			adblExecutionTime[i - 1] = dblT * i / iNumInterval;

			adblHoldings[i - 1] = r1ToR1Holdings.evaluate (adblExecutionTime[i - 1]);
		}

		DiscreteTradingTrajectory dtt = DiscreteTradingTrajectory.Standard (
			adblExecutionTime,
			adblHoldings
		);

		TrajectoryShortfallEstimator tse = new TrajectoryShortfallEstimator (dtt);

		R1UnivariateNormal r1un = tse.totalCostDistributionSynopsis (apep);

		double[] adblTradeList = dtt.tradeList();

		for (int i = 1; i < adblExecutionTime.length; ++i) {
			System.out.println ("\t| " +
				FormatUtil.FormatDouble (adblExecutionTime[i], 1, 4, 1.) + " => " +
				FormatUtil.FormatDouble (adblHoldings[i] / dblX, 2, 4, 100.) + "% | " +
				FormatUtil.FormatDouble (adblTradeList[i - 1] / dblX, 1, 4, 100.) + "% ||"
			);
		}

		System.out.println ("\t|---------------------------------||");

		System.out.println ("\n\t|--------------------------------------------------------------||");

		System.out.println ("\t|  TRANSACTION COST RECONCILIATION: EXPLICIT vs. ALMGREN 2003  ||");

		System.out.println ("\t|--------------------------------------------------------------||");

		System.out.println (
			"\t| Transaction Cost Expectation         : " +
			FormatUtil.FormatDouble (r1un.mean(), 6, 1, 1.) + " | " +
			FormatUtil.FormatDouble (ettc.transactionCostExpectation(), 6, 1, 1.) + " ||"
		);

		System.out.println (
			"\t| Transaction Cost Variance (X 10^-06) : " +
			FormatUtil.FormatDouble (r1un.variance(), 6, 1, 1.e-06) + " | " +
			FormatUtil.FormatDouble (ettc.transactionCostVariance(), 6, 1, 1.e-06) + " ||"
		);

		System.out.println ("\t|--------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
