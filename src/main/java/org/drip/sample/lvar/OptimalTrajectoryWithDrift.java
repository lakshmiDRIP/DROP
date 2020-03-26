
package org.drip.sample.lvar;

import org.drip.execution.capture.LinearImpactTrajectoryEstimator;
import org.drip.execution.dynamics.*;
import org.drip.execution.impact.*;
import org.drip.execution.nonadaptive.StaticOptimalSchemeDiscrete;
import org.drip.execution.optimum.EfficientTradingTrajectoryDiscrete;
import org.drip.execution.parameters.ArithmeticPriceDynamicsSettings;
import org.drip.execution.profiletime.UniformParticipationRateLinear;
import org.drip.execution.risk.PowerVarianceObjectiveUtility;
import org.drip.execution.strategy.*;
import org.drip.function.r1tor1.FlatUnivariate;
import org.drip.measure.gaussian.R1UnivariateNormal;
import org.drip.numerical.common.FormatUtil;
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
 * <i>OptimalTrajectoryWithDrift</i> generates the Trade/Holdings List of Optimal Execution Schedule based on
 *  the Evolution Walk Parameters specified according to the Liquidity VaR Optimal Objective Function,
 *  inclusive of Drift. The Generation follows a Numerical Optimizer Scheme. The References are:
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
 * 			Almgren, R. (2003): Optimal Execution with Non-linear Impact Functions and Trading Enhanced Risk
 * 				<i>Applied Mathematical Finance</i> <b>10</b> 1-18
 *  	</li>
 *  	<li>
 * 			Artzner, P., F. Delbaen, J. M. Eber, and D. Heath (1999): Coherent Measures of Risk
 * 				<i>Mathematical Finance</i> <b>9</b> 203-228
 *  	</li>
 *  	<li>
 * 			Basak, S., and A. Shapiro (2001): Value-at-Risk Based Risk Management: Optimal Policies and Asset
 * 				Prices <i>Review of Financial Studies</i> <b>14</b> 371-405
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/lvar/README.md">Liquidity VaR Based Optimal Trajectory</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OptimalTrajectoryWithDrift {

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblS0 = 50.;
		double dblSigma = 0.9487;
		double dblAlpha = 0.02;
		double dblEpsilon = 0.0625;
		double dblGamma = 2.5e-07;
		double dblEta = 2.5e-06;
		double dblConfidenceLevel = 0.90;

		double dblX = 1000000.;
		double dblT = 5.;
		int iN = 5;

		double dblLambdaV = R1UnivariateNormal.Standard().confidenceInterval (dblConfidenceLevel);

		DiscreteTradingTrajectoryControl dttc = DiscreteTradingTrajectoryControl.FixedInterval (
			new OrderSpecification (
				dblX,
				dblT
			),
			iN
		);

		LinearPermanentExpectationParameters lpep = ArithmeticPriceEvolutionParametersBuilder.LinearExpectation (
			new ArithmeticPriceDynamicsSettings (
				dblAlpha,
				new FlatUnivariate (dblSigma),
				0.
			),
			new UniformParticipationRateLinear (
				new ParticipationRateLinear (
					0.,
					dblGamma
				)
			),
			new UniformParticipationRateLinear (
				new ParticipationRateLinear (
					dblEpsilon,
					dblEta
				)
			)
		);

		EfficientTradingTrajectoryDiscrete ettd = (EfficientTradingTrajectoryDiscrete) new StaticOptimalSchemeDiscrete (
			dttc,
			lpep,
			PowerVarianceObjectiveUtility.LiquidityVaR (dblLambdaV)
		).generate();

		double[] adblExecutionTimeNode = ettd.executionTimeNode();

		double[] adblTradeList = ettd.tradeList();

		double[] adblHoldings = ettd.holdings();

		LinearImpactTrajectoryEstimator lite = new LinearImpactTrajectoryEstimator (ettd);

		R1UnivariateNormal r1un = lite.totalCostDistributionSynopsis (lpep);

		System.out.println ("\n\t|---------------------------------------------||");

		System.out.println ("\t| ALMGREN-CHRISS TRAJECTORY GENERATOR INPUTS  ||");

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\t| Initial Stock Price           : " + dblS0);

		System.out.println ("\t| Initial Holdings              : " + dblX);

		System.out.println ("\t| Liquidation Time              : " + dblT);

		System.out.println ("\t| Number of Time Periods        : " + iN);

		System.out.println ("\t| Daily Volume 5 million Shares : " + dblGamma);

		System.out.println ("\t| VaR Confidence Level          :" + FormatUtil.FormatDouble (dblConfidenceLevel, 2, 2, 100.) + "%");

		System.out.println ("\t| VaR Based Risk Aversion       : " + dblLambdaV);

		System.out.println ("\t|");

		System.out.println (
			"\t| Daily Volatility              : " +
			FormatUtil.FormatDouble (dblSigma, 1, 4, 1.)
		);

		System.out.println (
			"\t| Daily Returns                 : " +
			FormatUtil.FormatDouble (dblAlpha, 1, 4, 1.)
		);

		System.out.println ("\t| Temporary Impact Fixed Offset :  " + dblEpsilon);

		System.out.println ("\t| Eta                           :  " + dblEta);

		System.out.println ("\t| Gamma                         :  " + dblGamma);

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\n\t|-----------------------------||");

		System.out.println ("\t| Optimal Trading Trajectory  ||");

		System.out.println ("\t| ------- ------- ----------  ||");

		System.out.println ("\t|     L -> R:                 ||");

		System.out.println ("\t|        Time Node            ||");

		System.out.println ("\t|        Holdings             ||");

		System.out.println ("\t|        Trade Amount         ||");

		System.out.println ("\t|-----------------------------||");

		for (int i = 0; i <= iN; ++i) {
			if (i == 0)
				System.out.println (
					"\t|" + FormatUtil.FormatDouble (adblExecutionTimeNode[i], 1, 0, 1.) + " => " +
					FormatUtil.FormatDouble (adblHoldings[i], 7, 1, 1.) + " | " +
					FormatUtil.FormatDouble (0., 6, 1, 1.) + " ||"
				);
			else
				System.out.println (
					"\t|" + FormatUtil.FormatDouble (adblExecutionTimeNode[i], 1, 0, 1.) + " => " +
					FormatUtil.FormatDouble (adblHoldings[i], 7, 1, 1.) + " | " +
					FormatUtil.FormatDouble (adblTradeList[i - 1], 6, 1, 1.) + " ||"
				);
		}

		System.out.println ("\t|-----------------------------||");

		System.out.println ("\n\t|----------------------------------------------------------------||");

		System.out.println ("\t|  TRANSACTION COST RECONCILIATION: OPTIMAL vs. EXPLICIT LINEAR  ||");

		System.out.println ("\t|----------------------------------------------------------------||");

		System.out.println (
			"\t| Transaction Cost Expectation         : " +
			FormatUtil.FormatDouble (r1un.mean(), 7, 1, 1.) + " | " +
			FormatUtil.FormatDouble (ettd.transactionCostExpectation(), 7, 1, 1.) + " ||"
		);

		System.out.println (
			"\t| Transaction Cost Variance (X 10^-06) : " +
			FormatUtil.FormatDouble (r1un.variance(), 7, 1, 1.e-06) + " | " +
			FormatUtil.FormatDouble (ettd.transactionCostVariance(), 7, 1, 1.e-06) + " ||"
		);

		System.out.println ("\t|----------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
