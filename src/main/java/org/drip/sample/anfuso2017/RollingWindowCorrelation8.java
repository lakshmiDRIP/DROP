
package org.drip.sample.anfuso2017;

import java.util.ArrayList;
import java.util.List;

import org.drip.measure.crng.CorrelatedFactorsPathVertexRealization;
import org.drip.measure.crng.RandomNumberGenerator;
import org.drip.measure.statistics.MultivariateDiscrete;
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
 * <i>RollingWindowCorrelation8</i> demonstrates computing the Correlation on a Rolling Window Basis between
 * 	Two Correlated Series as illustrated in Table 8 of Anfuso, Karyampas, and Nawroth (2017).
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Anfuso, F., D. Karyampas, and A. Nawroth (2017): A Sound Basel III Compliant Framework for
 *  			Back-testing Credit Exposure Models
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2264620 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Diebold, F. X., T. A. Gunther, and A. S. Tay (1998): Evaluating Density Forecasts with
 *  			Applications to Financial Risk Management, International Economic Review 39 (4) 863-883
 *  	</li>
 *  	<li>
 *  		Kenyon, C., and R. Stamm (2012): Discounting, LIBOR, CVA, and Funding: Interest Rate and Credit
 *  			Pricing, Palgrave Macmillan
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018): Probability Integral Transform
 *  			https://en.wikipedia.org/wiki/Probability_integral_transform
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): p-value https://en.wikipedia.org/wiki/P-value
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/anfuso2017/README.md">Anfuso, Karyampas, and Nawroth (2013) Replications</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RollingWindowCorrelation8
{

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int pathCount = 1;
		int vertexCount = 390;
		int rollingWindow = 26;
		double[][] correlation = 
		{
			{1.000, 0.161},	// SNP500
			{0.161, 1.000},	// CHFUSD
		};

		CorrelatedFactorsPathVertexRealization correlatedPathVertexDimension = new CorrelatedFactorsPathVertexRealization (
			new RandomNumberGenerator(),
			correlation,
			vertexCount,
			pathCount,
			false,
			null
		);

		double[][] correlatedSequence =
			correlatedPathVertexDimension.multiTrajectoryStraightNodeRd()[0].flatform();

		List<double[]> windowSequence = new ArrayList<double[]>();

		for (int rollingIndex = 0; rollingIndex < rollingWindow; ++rollingIndex)
		{
			windowSequence.add (correlatedSequence[rollingIndex]);
		}

		double[][] rollingWindowSequence = new double[rollingWindow][2];

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\t|   Time Series Rolling Window Correlation    ||");

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\t|    L -> R:                                  ||");

		System.out.println ("\t|            - SNP500                         ||");

		System.out.println ("\t|            - CHFUSD                         ||");

		System.out.println ("\t|            - SNP500 vs. CHFUSD Correlation  ||");

		System.out.println ("\t|            - CHFUSD vs. SNP500 Correlation  ||");

		System.out.println ("\t|---------------------------------------------||");

		for (int index = rollingWindow; index < vertexCount; ++index)
		{
			windowSequence.toArray (rollingWindowSequence);

			MultivariateDiscrete multivariateDiscrete = new MultivariateDiscrete (rollingWindowSequence);

			double[][] rollingWindowCorrelation = multivariateDiscrete.correlation();

			System.out.println ("\t| " +
				FormatUtil.FormatDouble (correlatedSequence[index][0], 1, 8, 1.) + " | " +
				FormatUtil.FormatDouble (correlatedSequence[index][1], 1, 8, 1.) + " | " +
				FormatUtil.FormatDouble (rollingWindowCorrelation[0][1], 2, 1, 100.) + "% | " +
				FormatUtil.FormatDouble (rollingWindowCorrelation[1][0], 2, 1, 100.) + "% ||"
			);

			if (index < vertexCount - 1)
			{
				windowSequence.remove (0);

				windowSequence.add (correlatedSequence[index + 1]);
			}
		}

		System.out.println ("\t|---------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
