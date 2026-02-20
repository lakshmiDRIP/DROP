
package org.drip.sample.r1pdfmetrics;

import org.drip.measure.generators.R1DegenerateMGF;
import org.drip.measure.pdf.R1DegenerateDistribution;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
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
 * <i>DegenerateDistribution</i> implements the Degenerate Probability Distribution Function. The References
 * 	are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Wikipedia (2025): Degenerate Distribution https://en.wikipedia.org/wiki/Degenerate_distribution
 * 		</li>
 * 	</ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/r1pdfmetrics">R<sup>1</sup> Probability Distribution Function Metrics</a></li>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DegenerateDistributionAnalyzer
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

		double a = 1.0;
		double x1 = 0.5;
		double x2 = 1.0;
		double x3 = 1.5;

		R1DegenerateDistribution degenerateDistribution = new R1DegenerateDistribution (a);

		double[] leftRightSupportArray = degenerateDistribution.support();

		System.out.println ("\t|---------------------------------------------------------------||");

		System.out.println ("\t|                DEGENERATE DISTRIBUTION METRICS                ||");

		System.out.println ("\t|---------------------------------------------------------------||");

		System.out.println ("\t| a                                 => " + a);

		System.out.println ("\t|---------------------------------------------------------------||");

		System.out.println (
			"\t| Support                           => {" +
				leftRightSupportArray[0] + " | " + leftRightSupportArray[1] + "}"
		);

		System.out.println (
			"\t| Supported?                        => {" + x1 + "} | " +
				degenerateDistribution.supported (x1)
		);

		System.out.println (
			"\t| Supported?                        => {" + x2 + "} | " +
				degenerateDistribution.supported (x2)
		);

		System.out.println (
			"\t| Supported?                        => {" + x3 + "} | " +
				degenerateDistribution.supported (x3)
		);

		System.out.println ("\t|---------------------------------------------------------------||");

		System.out.println (
			"\t| Density                           => {" + x1 + "} | " + degenerateDistribution.density (x1)
		);

		System.out.println (
			"\t| Density                           => {" + x2 + "} | " + degenerateDistribution.density (x2)
		);

		System.out.println (
			"\t| Density                           => {" + x3 + "} | " + degenerateDistribution.density (x3)
		);

		System.out.println ("\t|---------------------------------------------------------------||");

		System.out.println (
			"\t| Cumulative                        => {" + x1 + "} | " +
				degenerateDistribution.cumulative (x1)
		);

		System.out.println (
			"\t| Cumulative                        => {" + x2 + "} | " +
				degenerateDistribution.cumulative (x2)
		);

		System.out.println (
			"\t| Cumulative                        => {" + x3 + "} | " +
				degenerateDistribution.cumulative (x3)
		);

		System.out.println ("\t|---------------------------------------------------------------||");

		System.out.println (
			"\t| Incremental                       => {" + x1 + " - " + x2 + "}: " +
				degenerateDistribution.incremental (x1, x2)
		);

		System.out.println (
			"\t| Incremental                       => {" + x2 + " - " + x3 + "}: " +
				degenerateDistribution.incremental (x2, x3)
		);

		System.out.println ("\t|---------------------------------------------------------------||");

		System.out.println ("\t| Mean                              => " + degenerateDistribution.mean());

		System.out.println ("\t| Median                            => " + degenerateDistribution.median());

		System.out.println ("\t| Mode                              => " + degenerateDistribution.mode());

		System.out.println ("\t| Variance                          => " + degenerateDistribution.variance());

		System.out.println (
			"\t| Differential Entropy              => " + degenerateDistribution.differentialEntropy()
		);

		System.out.println ("\t|---------------------------------------------------------------||");

		R1DegenerateMGF r1DegenerateMGF = new R1DegenerateMGF (degenerateDistribution);

		System.out.println ("\t| Mean (From MGF)                   => " + r1DegenerateMGF.mean());

		System.out.println ("\t| Variance (From MGF)               => " + r1DegenerateMGF.variance());

		System.out.println (
			"\t| 2nd Non-central Moment (From MGF) => " + r1DegenerateMGF.nonCentralMoment (2)
		);

		System.out.println (
			"\t| 3rd Non-central Moment (From MGF) => " + r1DegenerateMGF.nonCentralMoment (3)
		);

		System.out.println ("\t|---------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
