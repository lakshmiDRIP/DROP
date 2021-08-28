
package org.drip.sample.trend;

import org.drip.execution.bayesian.*;
import org.drip.execution.cost.LinearTemporaryImpact;
import org.drip.execution.impact.ParticipationRateLinear;
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
 * <i>BayesianDriftTrajectoryDependence</i> demonstrates the Dependence of the Trading Trajectory achieved
 * 	from using an Optimal Trajectory for a Price Process as a Function of the Bayesian Drift Parameters. The
 *  References are:
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

public class BayesianDriftTrajectoryDependence {

	static final void RunScenario (
		final double dblAlphaBar,
		final double dblNu,
		final double dblSigma,
		final double dblT,
		final ParticipationRateLinear prlTemporary)
		throws Exception
	{
		PriorDriftDistribution pdd = new PriorDriftDistribution (
			dblAlphaBar,
			dblNu
		);

		double dblTimeWidth = 0.5 * dblT;

		double[] adblAlpha = pdd.realizedDrift (2);

		ConditionalPriceDistribution cpd0 = new ConditionalPriceDistribution (
			adblAlpha[0],
			dblSigma,
			1.0 * dblTimeWidth
		);

		double dblPriceSwing0 = cpd0.priceVolatilitySwing();

		double dblRealizedPriceChange0 = adblAlpha[0] * dblTimeWidth + dblPriceSwing0;

		PriorConditionalCombiner pcc0 = new PriorConditionalCombiner (
			pdd,
			cpd0
		);

		LinearTemporaryImpact lti0 = LinearTemporaryImpact.Unconstrained (
			1.0 * dblTimeWidth,
			dblT,
			1.,
			pcc0,
			dblRealizedPriceChange0,
			prlTemporary
		);

		double dblInstantanenousTradeRate0 = lti0.instantaneousTradeRate();

		double dblX0 = 1. - dblInstantanenousTradeRate0 * dblTimeWidth;

		ConditionalPriceDistribution cpd1 = new ConditionalPriceDistribution (
			adblAlpha[1],
			dblSigma,
			2.0 * dblTimeWidth
		);

		double dblPriceSwing1 = cpd1.priceVolatilitySwing();

		double dblRealizedPriceChange1 = adblAlpha[1] * dblTimeWidth + dblPriceSwing1;

		PriorConditionalCombiner pcc1 = new PriorConditionalCombiner (
			pdd,
			cpd1
		);

		LinearTemporaryImpact lti1 = LinearTemporaryImpact.Unconstrained (
			1.0 * dblTimeWidth,
			dblT,
			dblX0,
			pcc1,
			dblRealizedPriceChange1,
			prlTemporary
		);

		double dblInstantanenousTradeRate1 = lti1.instantaneousTradeRate();

		double dblX1 = 1. - dblInstantanenousTradeRate1 * dblTimeWidth;

		System.out.println (
			"\t|[" +
			FormatUtil.FormatDouble (dblAlphaBar, 1, 1, 1.) + "," +
			FormatUtil.FormatDouble (dblNu, 1, 1, 1.) + "," +
			FormatUtil.FormatDouble (dblSigma, 1, 1, 1.) + "] => " +
			FormatUtil.FormatDouble (dblX0, 1, 3, 1.) + " | " +
			FormatUtil.FormatDouble (dblX1, 1, 3, 1.) + " || " +
			FormatUtil.FormatDouble (dblInstantanenousTradeRate0, 1, 3, 1.) + " | " +
			FormatUtil.FormatDouble (dblInstantanenousTradeRate1, 1, 3, 1.) + " || " +
			FormatUtil.FormatDouble (lti0.driftExpectationEstimate(), 1, 3, 1.) + " | " +
			FormatUtil.FormatDouble (lti1.driftExpectationEstimate(), 1, 3, 1.) + " || " +
			FormatUtil.FormatDouble (lti0.driftVolatilityEstimate(), 3, 0, 100.) + "% | " +
			FormatUtil.FormatDouble (lti1.driftVolatilityEstimate(), 3, 0, 100.) + "% || "
		);
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblT = 1.;
		double dblEta = 0.07;

		double[] adblNu = new double[] {
			0.5,
			1.0,
			2.0
		};

		double[] adblSigma = new double[] {
			0.5,
			1.7,
			2.9
		};

		double[] adblAlphaBar = new double[] {
			0.2,
			0.7,
			1.2
		};

		ParticipationRateLinear prlTemporary = ParticipationRateLinear.SlopeOnly (dblEta);

		System.out.println();

		System.out.println ("\t|-------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                     BAYESIAN GAIN INPUT DRIFT DISTRIBUTION DEPENDENCE                     ||");

		System.out.println ("\t|-------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                                                           ||");

		System.out.println ("\t|        Inputs L -> R:                                                                     ||");

		System.out.println ("\t|             - Alpha Bar                                                                   ||");

		System.out.println ("\t|             - Sigma                                                                       ||");

		System.out.println ("\t|             - Nu                                                                          ||");

		System.out.println ("\t|                                                                                           ||");

		System.out.println ("\t|-------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                                                                           ||");

		System.out.println ("\t|        Outputs L -> R:                                                                    ||");

		System.out.println ("\t|                                                                                           ||");

		System.out.println ("\t|             - Phase #1 Outstanding Holdings                                               ||");

		System.out.println ("\t|             - Phase #2 Outstanding Holdings                                               ||");

		System.out.println ("\t|             - Phase #1 Instantaneous Trade Rate                                           ||");

		System.out.println ("\t|             - Phase #2 Instantaneous Trade Rate                                           ||");

		System.out.println ("\t|             - Phase #1 Drift Expectation Estimate                                         ||");

		System.out.println ("\t|             - Phase #2 Drift Expectation Estimate                                         ||");

		System.out.println ("\t|             - Phase #1 Drift Volatility Estimate                                          ||");

		System.out.println ("\t|             - Phase #2 Drift Volatility Estimate                                          ||");

		System.out.println ("\t|                                                                                           ||");

		System.out.println ("\t|-------------------------------------------------------------------------------------------||");

		for (double dblAlphaBar : adblAlphaBar) {
			for (double dblNu : adblNu) {
				for (double dblSigma : adblSigma)
					RunScenario (
						dblAlphaBar,
						dblNu,
						dblSigma,
						dblT,
						prlTemporary
					);
			}
		}

		System.out.println ("\t|-------------------------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
