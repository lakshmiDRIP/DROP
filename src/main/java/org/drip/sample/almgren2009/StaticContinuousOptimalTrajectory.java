
package org.drip.sample.almgren2009;

import org.drip.execution.dynamics.*;
import org.drip.execution.impact.ParticipationRateLinear;
import org.drip.execution.nonadaptive.ContinuousAlmgrenChriss;
import org.drip.execution.optimum.EfficientTradingTrajectoryContinuous;
import org.drip.execution.parameters.*;
import org.drip.execution.profiletime.UniformParticipationRateLinear;
import org.drip.function.definition.R1ToR1;
import org.drip.function.r1tor1operator.Flat;
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
 * <i>StaticContinuousOptimalTrajectory</i> demonstrates the Generation and Usage of Continuous Version of
 * the Discrete Trading Trajectory generated by the Almgren and Chriss (2000) Scheme under the Criterion of
 * No-Drift. The References are:
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

public class StaticContinuousOptimalTrajectory {

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		double dblS0 = 50.;
		double dblX = 1000000.;
		double dblT = 5.;
		double dblAnnualVolatility = 0.30;
		double dblAnnualReturns = 0.10;
		double dblBidAsk = 0.125;
		double dblDailyVolume = 5.e06;
		double dblDailyVolumePermanentImpact = 0.1;
		double dblDailyVolumeTemporaryImpact = 0.01;
		double dblLambdaU = 1.e-06;

		int iN = 20;

		double dblTime = 0.;
		double dblTimeWidth = dblT / iN;

		ArithmeticPriceDynamicsSettings apds = ArithmeticPriceDynamicsSettings.FromAnnualReturnsSettings (
			dblAnnualReturns,
			dblAnnualVolatility,
			0.,
			dblS0
		);

		double dblSigma = apds.epochVolatility();

		PriceMarketImpactLinear pmil = new PriceMarketImpactLinear (
			new AssetTransactionSettings (
				dblS0,
				dblDailyVolume,
				dblBidAsk
			),
			dblDailyVolumePermanentImpact,
			dblDailyVolumeTemporaryImpact
		);

		ParticipationRateLinear prlPermanent = (ParticipationRateLinear) pmil.permanentTransactionFunction();

		ParticipationRateLinear prlTemporary = (ParticipationRateLinear) pmil.temporaryTransactionFunction();

		LinearPermanentExpectationParameters lpep = ArithmeticPriceEvolutionParametersBuilder.LinearExpectation (
			new ArithmeticPriceDynamicsSettings (
				0.,
				new Flat (dblSigma),
				0.
			),
			new UniformParticipationRateLinear (prlPermanent),
			new UniformParticipationRateLinear (prlTemporary)
		);

		ContinuousAlmgrenChriss cac = ContinuousAlmgrenChriss.Standard (
			dblX,
			dblT,
			lpep,
			dblLambdaU
		);

		EfficientTradingTrajectoryContinuous ettc = (EfficientTradingTrajectoryContinuous) cac.generate();

		R1ToR1 r1ToR1Holdings = ettc.holdings();

		R1ToR1 r1ToR1TradeRate = ettc.tradeRate();

		R1ToR1 r1ToR1TransactionCostVariance = ettc.transactionCostVarianceFunction();

		R1ToR1 r1ToR1TransactionCostExpectation = ettc.transactionCostExpectationFunction();

		System.out.println ();

		System.out.println ("\t|-------------------------------------------||");

		System.out.println ("\t|    STATIC CONTINUOUS OPTIMAL TRAJECTORY   ||");

		System.out.println ("\t|-------------------------------------------||");

		System.out.println ("\t|    L -> R:                                ||");

		System.out.println ("\t|            - Time                         ||");

		System.out.println ("\t|            - Holdings                     ||");

		System.out.println ("\t|            - Trade Rate                   ||");

		System.out.println ("\t|            - Transaction Cost Expectation ||");

		System.out.println ("\t|            - Transaction Cost Volatility  ||");

		System.out.println ("\t|-------------------------------------------||");

		for (int i = 1; i <= iN; ++i) {
			dblTime = dblTime + dblTimeWidth;

			System.out.println (
				"\t|" +
				FormatUtil.FormatDouble (dblTime, 1, 2, 1.) + " => " +
				FormatUtil.FormatDouble (r1ToR1Holdings.evaluate (dblTime) / dblX, 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (r1ToR1TradeRate.evaluate (dblTime) * dblTimeWidth / dblX, 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (r1ToR1TransactionCostExpectation.evaluate (dblTime) / dblX, 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (Math.sqrt (r1ToR1TransactionCostVariance.evaluate (dblTime)) / dblX, 1, 3, 1.) + " ||"
			);
		}

		System.out.println ("\t|-------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
