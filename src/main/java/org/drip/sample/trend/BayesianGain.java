
package org.drip.sample.trend;

import org.drip.execution.bayesian.*;
import org.drip.execution.cost.*;
import org.drip.execution.impact.ParticipationRateLinear;
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
 * <i>BayesianGain</i> demonstrates the Gains achieved from using an Optimal Trajectory for a Price Process
 * 	with Bayesian Drift, Arithmetic Volatility, and Linear Temporary Market Impact across a Set of Drifts.
 * 	The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 *  	</li>
 *  	<li>
 * 			Almgren, R., and J. Lorenz (2006): Bayesian Adaptive Trading with a Daily Cycle <i>Journal of
 * 				Trading</i> <b>1 (4)</b> 38-46
 *  	</li>
 *  	<li>
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 *				Markets</i> <b>1</b> 1-50
 *  	</li>
 *  	<li>
 * 			Brunnermeier, L. K., and L. H. Pedersen (2005): Predatory Trading <i>Journal of Finance</i>
 * 				<b>60 (4)</b> 1825-1863
 *  	</li>
 *  	<li>
 * 			Kissell, R., and R. Malamut (2007): Algorithmic Decision Making Framework <i>Journal of
 * 				Trading</i> <b>1 (1)</b> 12-21
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/trend/README.md">Fixed/Variable Bayesian Drift Gain</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BayesianGain {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iN = 50;
		double dblT = 1.;
		double dblX0 = 1.;
		double dblNu = 1.;
		double dblEta = 0.07;
		double dblSigma = 1.5;
		double dblAlphaBar = 0.7;

		double dblTime = 0.;
		double dblTimeWidth = dblT / iN;
		double dblXUnconstrained = dblX0;

		ParticipationRateLinear prlTemporary = ParticipationRateLinear.SlopeOnly (dblEta);

		PriorDriftDistribution pdd = new PriorDriftDistribution (
			dblAlphaBar,
			dblNu
		);

		double[] adblAlpha = pdd.realizedDrift (iN);

		System.out.println();

		System.out.println ("\t|-----------------------------------------------------------------------||");

		System.out.println ("\t|  L -> R                                                               ||");

		System.out.println ("\t|-----------------------------------------------------------------------||");

		System.out.println ("\t|    - Time                                                             ||");

		System.out.println ("\t|    - Realized Drift                                                   ||");

		System.out.println ("\t|    - Realized Price Change                                            ||");

		System.out.println ("\t|    - Estimated Drift                                                  ||");

		System.out.println ("\t|    - Unconstrained Trade Rate                                         ||");

		System.out.println ("\t|    - Unconstrained Holdings                                           ||");

		System.out.println ("\t|    - Transaction Cost                                                 ||");

		System.out.println ("\t|    - Transaction Cost Gain                                            ||");

		System.out.println ("\t|-----------------------------------------------------------------------||");

		for (int i = 0; i < iN - 1; ++i) {
			dblTime = dblTime + dblTimeWidth;

			ConditionalPriceDistribution cpd = new ConditionalPriceDistribution (
				adblAlpha[i],
				dblSigma,
				dblTime
			);

			double dblPriceSwing = cpd.priceVolatilitySwing();

			double dblRealizedPriceChange = adblAlpha[i] * dblTimeWidth + dblPriceSwing;

			PriorConditionalCombiner pcc = new PriorConditionalCombiner (
				pdd,
				cpd
			);

			LinearTemporaryImpact lti = LinearTemporaryImpact.Unconstrained (
				dblTime,
				dblT,
				dblXUnconstrained,
				pcc,
				dblRealizedPriceChange,
				prlTemporary
			);

			double dblUnconstrainedInstantaneousTradeRate = lti.instantaneousTradeRate();

			dblXUnconstrained = dblXUnconstrained - dblUnconstrainedInstantaneousTradeRate * dblTimeWidth;

			System.out.println (
				"\t| " + FormatUtil.FormatDouble (dblTime, 1, 2, 1.) + " => " +
				FormatUtil.FormatDouble (adblAlpha[i], 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (dblRealizedPriceChange, 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (lti.driftExpectationEstimate(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (dblUnconstrainedInstantaneousTradeRate, 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (dblXUnconstrained, 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (lti.staticTransactionCost(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (lti.transactionCostGain(), 1, 3, 1.) + " ||"
			);
		}

		System.out.println ("\t|-----------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
