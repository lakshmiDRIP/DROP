
package org.drip.sample.principal;

import org.drip.execution.dynamics.*;
import org.drip.execution.impact.*;
import org.drip.execution.nonadaptive.ContinuousPowerImpact;
import org.drip.execution.optimum.PowerImpactContinuous;
import org.drip.execution.parameters.*;
import org.drip.execution.principal.Almgren2003Estimator;
import org.drip.execution.profiletime.*;
import org.drip.function.definition.R1ToR1;
import org.drip.function.r1tor1.FlatUnivariate;
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
 * <i>OptimalTrajectoryMeasures</i> demonstrates the Trade Scheduling using the Equity Market Impact
 * 	Functions determined empirically by Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization
 * 	of Almgren (2003) for IBM. It generates the Transaction Cost/Principal Discount Measures from the Run.
 * 	The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/principal/README.md">Information Ratio Based Principal Trading</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class OptimalTrajectoryMeasures {

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
		EnvManager.InitEnv ("");

		double dblS0 = 50.;
		double dblX = 100000.;
		double dblVolatility = 1.;
		double dblDailyVolume = 1000000.;
		double dblDailyVolumeExecutionFactor = 0.1;
		double dblPermanentImpactFactor = 0.;
		double dblTemporaryImpactFactor = 0.01;
		double dblT = 5.;
		int iNumInterval = 20;
		double dblLambda = 1.e-06;
		double dblK = 1.;
		double dblPrincipalDiscount = 0.15;

		PriceMarketImpactPower pmip = new PriceMarketImpactPower (
			new AssetTransactionSettings (
				dblS0,
				dblDailyVolume,
				0.
			),
			dblPermanentImpactFactor,
			dblTemporaryImpactFactor,
			dblDailyVolumeExecutionFactor,
			dblK
		);

		LinearPermanentExpectationParameters lpep = ArithmeticPriceEvolutionParametersBuilder.Almgren2003 (
			new ArithmeticPriceDynamicsSettings (
				0.,
				new FlatUnivariate (dblVolatility),
				0.
			),
			new UniformParticipationRateLinear ((ParticipationRateLinear) pmip.permanentTransactionFunction()),
			new UniformParticipationRate ((ParticipationRatePower) pmip.temporaryTransactionFunction())
		);

		ContinuousPowerImpact cpi = ContinuousPowerImpact.Standard (
			dblX,
			dblT,
			lpep,
			dblLambda
		);

		PowerImpactContinuous pic = (PowerImpactContinuous) cpi.generate();

		R1ToR1 r1ToR1Holdings = pic.holdings();

		double[] adblHoldings = new double[iNumInterval];
		double[] adblExecutionTime = new double[iNumInterval];

		for (int i = 1; i <= iNumInterval; ++i) {
			adblExecutionTime[i - 1] = dblT * i / iNumInterval;

			adblHoldings[i - 1] = r1ToR1Holdings.evaluate (adblExecutionTime[i - 1]);
		}

		Almgren2003Estimator a2003e = new Almgren2003Estimator (
			pic,
			lpep
		);

		System.out.println();

		System.out.println ("\t|----------------------------------||");

		System.out.println ("\t| IBM ATHL 2005 Optimal Trajectory ||");

		System.out.println ("\t|----------------------------------||");

		System.out.println ("\t|     L -> R:                      ||");

		System.out.println ("\t|           - Execution Time Node  ||");

		System.out.println ("\t|           - Holdings Remaining   ||");

		System.out.println ("\t|           - Trade List Amount    ||");

		System.out.println ("\t|           - Holdings (%)         ||");

		System.out.println ("\t|----------------------------------||");

		for (int i = 1; i < adblExecutionTime.length; ++i)
			System.out.println (
				"\t| " +
				FormatUtil.FormatDouble (adblExecutionTime[i], 1, 2, 1.) + " | " +
				FormatUtil.FormatDouble (adblHoldings[i], 5, 0, 1.) + " | " + 
				FormatUtil.FormatDouble (adblHoldings[i] - adblHoldings[i - 1], 5, 0, 1.) + " | " + 
				FormatUtil.FormatDouble (adblHoldings[i] / dblX, 2, 1, 100.) + "% ||"
			);

		System.out.println ("\t|----------------------------------||");

		System.out.println();

		System.out.println ("\t|-----------------------------------------------------------------------||");

		System.out.println ("\t|       IBM ATHL 2005 Optimal Trajectory Transaction Cost Measures      ||");

		System.out.println ("\t|-----------------------------------------------------------------------||");

		System.out.println (
			"\t| Transaction Cost Expectation ( X 10^-03)                  : " +
			FormatUtil.FormatDouble (pic.transactionCostExpectation(), 5, 2, 1.e-03) + " ||"
		);

		System.out.println (
			"\t| Transaction Cost Variance ( X 10^-06)                     : " +
			FormatUtil.FormatDouble (pic.transactionCostVariance(), 5, 2, 1.e-06) + " ||"
		);

		System.out.println (
			"\t| Characteristic Time                                       : " +
			FormatUtil.FormatDouble (pic.characteristicTime(), 5, 2, 1.) + " ||"
		);

		System.out.println (
			"\t| Efficient Frontier Hyperboloid Boundary Value ( X 10^-12) : " +
			FormatUtil.FormatDouble (pic.hyperboloidBoundaryValue(), 5, 2, 1.e-12) + " ||"
		);

		System.out.println (
			"\t| Break-even Principal Discount (cents per unit)            : " +
			FormatUtil.FormatDouble (a2003e.breakevenPrincipalDiscount(), 5, 2, 100.) + " ||"
		);

		System.out.println (
			"\t| Gross Profit Expectation                                  : " +
			FormatUtil.FormatDouble (a2003e.principalMeasure (dblPrincipalDiscount).mean(), 5, 2, 1.) + " ||"
		);

		System.out.println (
			"\t| Gross Profit Variance ( X 10^-06)                         : " +
			FormatUtil.FormatDouble (a2003e.principalMeasure (dblPrincipalDiscount).variance(), 5, 2, 1.e-06) + " ||"
		);

		System.out.println (
			"\t| Gross Returns Expectation                                 : " +
			FormatUtil.FormatDouble (a2003e.horizonPrincipalMeasure (dblPrincipalDiscount).mean(), 5, 2, 1.) + " ||"
		);

		System.out.println (
			"\t| Gross Returns Variance ( X 10^-06)                        : " +
			FormatUtil.FormatDouble (a2003e.horizonPrincipalMeasure (dblPrincipalDiscount).variance(), 5, 2, 1.e-06) + " ||"
		);

		System.out.println (
			"\t| Information Ratio ( X 10^+03)                             : " +
			FormatUtil.FormatDouble (a2003e.informationRatio (dblPrincipalDiscount), 5, 2, 1.e+03) + " ||"
		);

		System.out.println (
			"\t| Optimal Information Ratio ( X 10^+03)                     : " +
			FormatUtil.FormatDouble (a2003e.optimalInformationRatio (dblPrincipalDiscount), 5, 2, 1.e+03) + " ||"
		);

		System.out.println (
			"\t| Optimal Information Ratio Horizon                         : " +
			FormatUtil.FormatDouble (a2003e.optimalInformationRatioHorizon (dblPrincipalDiscount), 5, 2, 1.) + " ||"
		);

		System.out.println ("\t|-----------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
