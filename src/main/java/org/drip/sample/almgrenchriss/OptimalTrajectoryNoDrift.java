
package org.drip.sample.almgrenchriss;

import org.drip.execution.capture.*;
import org.drip.execution.dynamics.*;
import org.drip.execution.impact.*;
import org.drip.execution.nonadaptive.DiscreteAlmgrenChriss;
import org.drip.execution.optimum.AlmgrenChrissDiscrete;
import org.drip.execution.parameters.*;
import org.drip.execution.profiletime.UniformParticipationRateLinear;
import org.drip.function.r1tor1.FlatUnivariate;
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
 * <i>OptimalTrajectoryNoDrift</i> demonstrates the Generation of the Optimal Trading Trajectory in
 * accordance with the Specification of Almgren and Chriss (2000) for the given Risk Aversion Parameter
 * without the Asset Drift. The References are:
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
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 *  	</li>
 *  	<li>
 * 			Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional
 * 				Trades <i>Journal of Finance</i> <b>50</b> 1147-1174
 *  	</li>
 *  	<li>
 * 			Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange
 * 				Analysis of Institutional Equity Trades <i>Journal of Financial Economics</i> <b>46</b>
 * 				265-292
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/almgrenchriss/README.md">Almgren Chriss Efficient Frontier Trajectories</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class OptimalTrajectoryNoDrift {

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
		int iN = 5;
		double dblAnnualVolatility = 0.30;
		double dblAnnualReturns = 0.10;
		double dblBidAsk = 0.125;
		double dblDailyVolume = 5.e06;
		double dblDailyVolumePermanentImpact = 0.1;
		double dblDailyVolumeTemporaryImpact = 0.01;
		double dblLambdaU = 1.e-06;

		ArithmeticPriceDynamicsSettings apds = ArithmeticPriceDynamicsSettings.FromAnnualReturnsSettings (
			dblAnnualReturns,
			dblAnnualVolatility,
			0.,
			dblS0
		);

		double dblAlpha = apds.drift();

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
				new FlatUnivariate (dblSigma),
				0.
			),
			new UniformParticipationRateLinear (prlPermanent),
			new UniformParticipationRateLinear (prlTemporary)
		);

		DiscreteAlmgrenChriss dac = DiscreteAlmgrenChriss.Standard (
			dblX,
			dblT,
			iN,
			lpep,
			dblLambdaU
		);

		AlmgrenChrissDiscrete acd = (AlmgrenChrissDiscrete) dac.generate();

		double[] adblExecutionTimeNode = acd.executionTimeNode();

		double[] adblTradeList = acd.tradeList();

		double[] adblHoldings = acd.holdings();

		LinearImpactTrajectoryEstimator lite = new LinearImpactTrajectoryEstimator (acd);

		TrajectoryShortfallAggregate tsa = lite.totalCostDistributionDetail (lpep);

		double[] adblIncrementalPermanentImpact = tsa.incrementalPermanentImpactExpectation();

		double[] adblIncrementalTemporaryImpact = tsa.incrementalTemporaryImpactExpectation();

		double[] adblCumulativePermanentImpact = tsa.cumulativePermanentImpactExpectation();

		double[] adblCumulativeTemporaryImpact = tsa.cumulativeTemporaryImpactExpectation();

		double[] adblIncrementalShortfallVariance = tsa.incrementalVariance();

		double[] adblCumulativeShortfallVariance = tsa.cumulativeVariance();

		double[] adblIncrementalShortfallMean = tsa.incrementalExpectation();

		double[] adblCumulativeShortfallMean = tsa.cumulativeExpectation();

		R1UnivariateNormal r1un = lite.totalCostDistributionSynopsis (lpep);

		System.out.println ("\n\t|---------------------------------------------||");

		System.out.println ("\t| ALMGREN-CHRISS TRAJECTORY GENERATOR INPUTS  ||");

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\t| Initial Stock Price           : " + dblS0);

		System.out.println ("\t| Initial Holdings              : " + dblX);

		System.out.println ("\t| Liquidation Time              : " + dblT);

		System.out.println ("\t| Number of Time Periods        : " + iN);

		System.out.println ("\t| Annual Volatility             :" + FormatUtil.FormatDouble (dblAnnualVolatility, 1, 0, 100.) + "%");

		System.out.println ("\t| Annual Growth                 :" + FormatUtil.FormatDouble (dblAnnualReturns, 1, 0, 100.) + "%");

		System.out.println ("\t| Bid-Ask Spread                : " + dblBidAsk);

		System.out.println ("\t| Daily Volume                  : " + dblDailyVolume);

		System.out.println ("\t| Daily Volume Temporary Impact : " + dblDailyVolumeTemporaryImpact);

		System.out.println ("\t| Daily Volume Permanent Impact : " + dblDailyVolumePermanentImpact);

		System.out.println ("\t| Daily Volume 5 million Shares : " + prlPermanent.slope());

		System.out.println ("\t| Static Holdings 11,000 Shares : " + dblLambdaU);

		System.out.println ("\t|");

		System.out.println (
			"\t| Daily Volatility              : " +
			FormatUtil.FormatDouble (dblSigma, 1, 4, 1.)
		);

		System.out.println (
			"\t| Daily Returns                 : " +
			FormatUtil.FormatDouble (dblAlpha, 1, 4, 1.)
		);

		System.out.println ("\t| Temporary Impact Fixed Offset :  " + prlTemporary.offset());

		System.out.println ("\t| Eta                           :  " + prlTemporary.slope());

		System.out.println ("\t| Gamma                         :  " + prlPermanent.slope());

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\n\t|-----------------------------||");

		System.out.println ("\t| AC2000 Optimal Trajectory   ||");

		System.out.println ("\t| ------ ------- ----------   ||");

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

		System.out.println ("\n\t|-----------------------------------------------------------||");

		System.out.println ("\t|               PERIOD LEVEL COST DISTRIBUTION              ||");

		System.out.println ("\t|-----------------------------------------------------------||");

		System.out.println ("\t|  PERIOD   |          MEAN         |        VARIANCE       ||");

		System.out.println ("\t|-----------------------------------------------------------||");

		System.out.println ("\t|  PERIOD   |    INCR   |    CUML   |    INCR   |    CUML   ||");

		System.out.println ("\t|-----------------------------------------------------------||");

		for (int i = 0; i < adblIncrementalShortfallMean.length; ++i)
			System.out.println (
				"\t| PERIOD #" + (i + 1) + " | " +
				FormatUtil.FormatDouble (adblIncrementalShortfallMean[i], 6, 1, 1.) + " | " +
				FormatUtil.FormatDouble (adblCumulativeShortfallMean[i], 6, 1, 1.) + " | " +
				FormatUtil.FormatDouble (adblIncrementalShortfallVariance[i], 6, 1, 1.e-06) + " | " +
				FormatUtil.FormatDouble (adblCumulativeShortfallVariance[i], 6, 1, 1.e-06) + " ||"
			);

		System.out.println ("\t|-----------------------------------------------------------||");

		System.out.println ("\n\t|-----------------------------------------------------------||");

		System.out.println ("\t|            PERIOD LEVEL COST IMPACT CONTRIBUTION          ||");

		System.out.println ("\t|-----------------------------------------------------------||");

		System.out.println ("\t|  PERIOD   |       PERMANENT       |       TEMPORARY       ||");

		System.out.println ("\t|-----------------------------------------------------------||");

		System.out.println ("\t|  PERIOD   |    INCR   |    CUML   |    INCR   |    CUML   ||");

		System.out.println ("\t|-----------------------------------------------------------||");

		for (int i = 0; i < adblIncrementalPermanentImpact.length; ++i)
			System.out.println (
				"\t| PERIOD #" + (i + 1) + " | " +
				FormatUtil.FormatDouble (adblIncrementalPermanentImpact[i], 6, 1, 1.) + " | " +
				FormatUtil.FormatDouble (adblCumulativePermanentImpact[i], 6, 1, 1.) + " | " +
				FormatUtil.FormatDouble (adblIncrementalTemporaryImpact[i], 6, 1, 1.) + " | " +
				FormatUtil.FormatDouble (adblCumulativeTemporaryImpact[i], 6, 1, 1.) + " ||"
			);

		System.out.println ("\t|-----------------------------------------------------------||");

		System.out.println ("\n\t|--------------------------------------------------------------||");

		System.out.println ("\t| TRANSACTION COST RECONCILIATION: AC2000 vs. EXPLICIT LINEAR  ||");

		System.out.println ("\t|--------------------------------------------------------------||");

		System.out.println (
			"\t| Transaction Cost Expectation         : " +
			FormatUtil.FormatDouble (r1un.mean(), 6, 1, 1.) + " | " +
			FormatUtil.FormatDouble (acd.transactionCostExpectation(), 6, 1, 1.) + " ||"
		);

		System.out.println (
			"\t| Transaction Cost Variance (X 10^-06) : " +
			FormatUtil.FormatDouble (r1un.variance(), 6, 1, 1.e-06) + " | " +
			FormatUtil.FormatDouble (acd.transactionCostVariance(), 6, 1, 1.e-06) + " ||"
		);

		System.out.println ("\t|--------------------------------------------------------------||");

		System.out.println ("\n\t|-----------------------||");

		System.out.println ("\t|  AC2000 METRICS DUMP  ||");

		System.out.println ("\t|-----------------------||");

		System.out.println ("\t| Kappa       : " + FormatUtil.FormatDouble (acd.kappa(), 1, 4, 1.) + " ||");

		System.out.println ("\t| Kappa Tilda : " + FormatUtil.FormatDouble (acd.kappaTilda(), 1, 4, 1.) + " ||");

		System.out.println ("\t| Half Life   : " + FormatUtil.FormatDouble (acd.halfLife(), 1, 4, 1.) + " ||");

		System.out.println ("\t| Market Power: " + FormatUtil.FormatDouble (acd.marketPower(), 1, 4, 1.) + " ||");

		System.out.println ("\t|-----------------------||");

		EnvManager.TerminateEnv();
	}
}
