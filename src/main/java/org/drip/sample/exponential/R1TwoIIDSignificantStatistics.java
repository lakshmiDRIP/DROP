
package org.drip.sample.exponential;

import org.drip.measure.exponential.R1RateDistribution;
import org.drip.measure.exponential.TwoIIDSum;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>R1TwoIIDSignificantStatistics</i> illustrates the Generation of Significant Statistics for the Sum of
 * 	Two IID R<sup>1</sup> Exponential Distributions. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Devroye, L. (1986): <i>Non-Uniform Random Variate Generation</i> <b>Springer-Verlag</b> New York
 * 		</li>
 * 		<li>
 * 			Exponential Distribution (2019): Exponential Distribution
 * 				https://en.wikipedia.org/wiki/Exponential_distribution
 * 		</li>
 * 		<li>
 * 			Norton, M., V. Khokhlov, and S. Uryasev (2019): Calculating CVaR and bPOE for Common Probability
 * 				Distributions with Application to Portfolio Optimization and Density Estimation <i>Annals of
 * 				Operations Research</i> <b>299 (1-2)</b> 1281-1315
 * 		</li>
 * 		<li>
 * 			Ross, S. M. (2009): <i>Introduction to Probability and Statistics for Engineers and Scientists
 * 				4<sup>th</sup> Edition</i> <b>Associated Press</b> New York, NY
 * 		</li>
 * 		<li>
 * 			Schmidt, D. F., and D. Makalic (2009): Universal Models for the Exponential Distribution <i>IEEE
 * 				Transactions on Information Theory</i> <b>55 (7)</b> 3087-3090
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/exponential/README.md">R<sup>1</sup> Exponential Distribution Implementation/Properties</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1TwoIIDSignificantStatistics
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

		double[] lambdaArray = {0.02, 0.04, 0.08, 0.16, 0.32, 0.64, 1.3, 2.6, 5.1, 10.2, 20.5};
		// double[] lambdaArray = {1.};

		System.out.println (
			"\t||----------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t||    L -> R:"
		);

		System.out.println (
			"\t||          - Lambda Pair (Input)"
		);

		System.out.println (
			"\t||          - Mean"
		);

		System.out.println (
			"\t||          - Mode"
		);

		System.out.println (
			"\t||          - Variance"
		);

		System.out.println (
			"\t||          - Skewness"
		);

		System.out.println (
			"\t||          - Excess Kurtosis"
		);

		System.out.println (
			"\t||          - Fisher Information"
		);

		System.out.println (
			"\t||          - Inter-quantile Range (IQR)"
		);

		System.out.println (
			"\t||----------------------------------------------------------------------------------||"
		);

		for (int i = 0; i < lambdaArray.length; ++i)
		{
			for (int j = 0; j < lambdaArray.length; ++j)
			{
				TwoIIDSum exponentialDistribution = new TwoIIDSum (
					new R1RateDistribution (lambdaArray[i]),
					new R1RateDistribution (lambdaArray[j])
				);

				System.out.println (
					"\t|| {" + FormatUtil.FormatDouble (
						lambdaArray[i], 2, 2, 1., false
					) + "," + FormatUtil.FormatDouble (
						lambdaArray[j], 2, 2, 1.
					) + "} =>" + FormatUtil.FormatDouble (
						exponentialDistribution.mean(), 2, 3, 1.
					) + " |" + FormatUtil.FormatDouble (
						exponentialDistribution.median(), 2, 3, 1.
					) + " |" + FormatUtil.FormatDouble (
						exponentialDistribution.mode(), 2, 3, 1.
					) + " |" + FormatUtil.FormatDouble (
						exponentialDistribution.variance(), 4, 4, 1.
					/* ) + " |" + FormatUtil.FormatDouble (
						exponentialDistribution.skewness(), 1, 1, 1.
					) + " |" + FormatUtil.FormatDouble (
						exponentialDistribution.excessKurtosis(), 2, 3, 1.
					) + " |" + FormatUtil.FormatDouble (
						exponentialDistribution.fisherInformation(), 4, 4, 1.
					) + " |" + FormatUtil.FormatDouble (
						exponentialDistribution.iqr(), 2, 3, 1. */
					) + " ||"
				);
			}
		}

		System.out.println (
			"\t||----------------------------------------------------------------------------------||"
		);

		EnvManager.TerminateEnv();
	}
}
