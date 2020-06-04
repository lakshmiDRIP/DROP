
package org.drip.sample.almgrenchriss;

import org.drip.execution.dynamics.*;
import org.drip.execution.impact.*;
import org.drip.execution.nonadaptive.DiscreteAlmgrenChriss;
import org.drip.execution.optimum.AlmgrenChrissDiscrete;
import org.drip.execution.parameters.ArithmeticPriceDynamicsSettings;
import org.drip.execution.profiletime.UniformParticipationRateLinear;
import org.drip.function.r1tor1.FlatUnivariate;
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
 * <i>EfficientFrontierNoDrift</i> constructs the Efficient Frontier over a Sequence of Risk Aversion
 * Parameters for Optimal Trading Trajectories computed in accordance with the Specification of Almgren and
 * Chriss (2000), and calculates the corresponding Execution Half Life and the Trajectory Penalty without
 * regard to the Drift. The References are:
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

public class EfficientFrontierNoDrift {

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
		double dblSigma = 0.95;
		double dblAlpha = 0.02;
		double dblEpsilon = 0.0625;
		double dblGamma = 2.5e-07;
		double dblEta = 2.5e-06;
		double[] adblLambdaShortEndU = {
			0.001e-06,
			0.002e-06,
			0.003e-06,
			0.004e-06,
			0.005e-06,
			0.006e-06,
			0.007e-06,
			0.008e-06,
			0.009e-06
		};
		double[] adblLambdaLongEndU = {
			0.250e-06,
			0.500e-06,
			0.750e-06,
			1.000e-06,
			1.250e-06,
			1.500e-06,
			1.750e-06,
			2.000e-06,
			2.250e-06,
			2.500e-06,
			2.750e-06,
			3.000e-06,
			3.250e-06,
			3.500e-06,
			3.750e-06,
			4.000e-06
		};

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

		System.out.println ("\n\t|---------------------------------------------||");

		System.out.println ("\t| ALMGREN-CHRISS TRAJECTORY GENERATOR INPUTS  ||");

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\t| Initial Stock Price           : " + dblS0);

		System.out.println ("\t| Initial Holdings              : " + dblX);

		System.out.println ("\t| Liquidation Time              : " + dblT);

		System.out.println ("\t| Number of Time Periods        : " + iN);

		System.out.println ("\t| 30% Annual Volatility         : " + dblSigma);

		System.out.println ("\t| 10% Annual Growth             : " + dblAlpha);

		System.out.println ("\t| Bid-Ask Spread = 1/8          : " + dblEpsilon);

		System.out.println ("\t| Daily Volume 5 million Shares : " + dblGamma);

		System.out.println ("\t| Impact at 1% of Market        : " + dblEta);

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------||");

		System.out.println ("\t|      SHORT END COST DISTRIBUTION, PENALTY, AND DECAY       ||");

		System.out.println ("\t|------------------------------------------------------------||");

		System.out.println ("\t|       LAMBDA      |   MEAN  | SIGMA^2 | PENALTY | HALFLIFE ||");

		System.out.println ("\t|------------------------------------------------------------||");

		for (double dblLambda : adblLambdaShortEndU) {
			AlmgrenChrissDiscrete acd = (AlmgrenChrissDiscrete) DiscreteAlmgrenChriss.Standard (
				dblX,
				dblT,
				iN,
				lpep,
				dblLambda
			).generate();
	
			String strHoldings = "\t| [LAMBDA = " + FormatUtil.FormatDouble (dblLambda, 1, 3, dblX) + "]";

			double dblTransactionCostExpectation = acd.transactionCostExpectation();

			double dblTransactionCostVariance = acd.transactionCostVariance();

			double dblTransactionCostPenalty = dblTransactionCostExpectation + dblLambda * dblTransactionCostVariance;

			strHoldings = strHoldings + " | " + FormatUtil.FormatDouble (dblTransactionCostExpectation / dblX, 1, 4, 1.);

			strHoldings = strHoldings + " | " + FormatUtil.FormatDouble (dblTransactionCostVariance / dblX / dblX, 1, 4, 1.);

			strHoldings = strHoldings + " | " + FormatUtil.FormatDouble (dblTransactionCostPenalty / dblX, 1, 4, 1.);

			strHoldings = strHoldings + " | " + FormatUtil.FormatDouble (acd.halfLife(), 2, 2, 1.);

			System.out.println (strHoldings + "   ||");
		}

		System.out.println ("\t|------------------------------------------------------------||");

		System.out.println ("\n\t|------------------------------------------------------------||");

		System.out.println ("\t|       LONG END COST DISTRIBUTION, PENALTY, AND DECAY       ||");

		System.out.println ("\t|------------------------------------------------------------||");

		System.out.println ("\t|       LAMBDA      |   MEAN  | SIGMA^2 | PENALTY | HALFLIFE ||");

		System.out.println ("\t|------------------------------------------------------------||");

		for (double dblLambda : adblLambdaLongEndU) {
			AlmgrenChrissDiscrete acd = (AlmgrenChrissDiscrete) DiscreteAlmgrenChriss.Standard (
				dblX,
				dblT,
				iN,
				lpep,
				dblLambda
			).generate();
	
			String strHoldings = "\t| [LAMBDA = " + FormatUtil.FormatDouble (dblLambda, 1, 3, dblX) + "]";

			double dblTransactionCostExpectation = acd.transactionCostExpectation();

			double dblTransactionCostVariance = acd.transactionCostVariance();

			double dblTransactionCostPenalty = dblTransactionCostExpectation + dblLambda * dblTransactionCostVariance;

			strHoldings = strHoldings + " | " + FormatUtil.FormatDouble (dblTransactionCostExpectation / dblX, 1, 4, 1.);

			strHoldings = strHoldings + " | " + FormatUtil.FormatDouble (dblTransactionCostVariance / dblX / dblX, 1, 4, 1.);

			strHoldings = strHoldings + " | " + FormatUtil.FormatDouble (dblTransactionCostPenalty / dblX, 1, 4, 1.);

			strHoldings = strHoldings + " |  " + FormatUtil.FormatDouble (acd.halfLife(), 1, 2, 1.);

			System.out.println (strHoldings + "   ||");
		}

		System.out.println ("\t|------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
