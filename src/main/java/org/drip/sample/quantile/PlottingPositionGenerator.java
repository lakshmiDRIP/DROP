
package org.drip.sample.quantile;

import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.validation.quantile.PlottingPosition;
import org.drip.validation.quantile.PlottingPositionGeneratorFilliben;
import org.drip.validation.quantile.PlottingPositionGeneratorHeuristic;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>PlottingPositionGenerator</i> compares several Order Statistics Mean and Median Based Plotting Position
 * 	Generators.
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Gibbons, J. D., and S. Chakraborti (2003): <i>Non-parametric Statistical Inference 4th
 *  			Edition</i> <b>CRC Press</b>
 *  	</li>
 *  	<li>
 *  		Filliben, J. J. (1975): The Probability Plot Correlation Coefficient Test for Normality
 *  			<i>Technometrics, American Society for Quality</i> <b>17 (1)</b> 111-117
 *  	</li>
 *  	<li>
 *  		Gnanadesikan, R. (1977): <i>Methods for Statistical Analysis of Multivariate Observations</i>
 *  			<b>Wiley</b>
 *  	</li>
 *  	<li>
 *  		Thode, H. C. (2002): <i>Testing for Normality</i> <b>Marcel Dekker</b> New York
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018): Q-Q Plot https://en.wikipedia.org/wiki/Q%E2%80%93Q_plot
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/quantile">Quantile Generation and Comparison Testing</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PlottingPositionGenerator
{

	private static final void OrderStatisticQuantiles (
		final PlottingPosition[] plottingPositionArrayStandard,
		final PlottingPosition[] plottingPositionArrayBernardBosLevenbach,
		final PlottingPosition[] plottingPositionArrayNIST,
		final PlottingPosition[] plottingPositionArrayYuHuang,
		final PlottingPosition[] plottingPositionArrayBMDP,
		final PlottingPosition[] plottingPositionArrayBlom,
		final PlottingPosition[] plottingPositionArrayCunnane,
		final PlottingPosition[] plottingPositionArrayGringorten,
		final PlottingPosition[] plottingPositionArrayHazen,
		final PlottingPosition[] plottingPositionArrayLarsenCurrantHunt,
		final PlottingPosition[] plottingPositionArrayFilliben,
		final PlottingPosition[] plottingPositionArrayFillibenMedian)
		throws Exception
	{
		for (int plotIndex = 0; plotIndex < plottingPositionArrayStandard.length; ++plotIndex)
		{
			System.out.println (
				"\t| " + FormatUtil.FormatDouble (
					plottingPositionArrayStandard[plotIndex].orderStatisticOrdinal(), 2, 0, 1.
				) + " => " +
				FormatUtil.FormatDouble (plottingPositionArrayStandard[plotIndex].quantile(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (plottingPositionArrayBernardBosLevenbach[plotIndex].quantile(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (plottingPositionArrayNIST[plotIndex].quantile(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (plottingPositionArrayYuHuang[plotIndex].quantile(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (plottingPositionArrayBMDP[plotIndex].quantile(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (plottingPositionArrayBlom[plotIndex].quantile(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (plottingPositionArrayCunnane[plotIndex].quantile(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (plottingPositionArrayGringorten[plotIndex].quantile(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (plottingPositionArrayHazen[plotIndex].quantile(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (plottingPositionArrayLarsenCurrantHunt[plotIndex].quantile(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (plottingPositionArrayFilliben[plotIndex].quantile(), 1, 4, 1.) + " || " +
				FormatUtil.FormatDouble (plottingPositionArrayFillibenMedian[plotIndex].quantile(), 1, 4, 1.) + " ||"
			);
		}
	}

	public static final void main (
		final String[] argumentArray)
		throws java.lang.Exception
	{
		EnvManager.InitEnv ("");

		int orderStatisticCount = 25;

		System.out.println ("\t|-------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                 EXPECTED ORDER STATISTICS BASED PLOTTING POSITION GENERATORS                                  ||");

		System.out.println ("\t|-------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                                                                                ||");

		System.out.println ("\t|                - Order Statistic Ordinal                                                                                      ||");

		System.out.println ("\t|                - Wikipedia Standard (2018)                                                                                    ||");

		System.out.println ("\t|                - Bernard and Bos-Leverbach (1953)                                                                             ||");

		System.out.println ("\t|                - NIST (2013)                                                                                                  ||");

		System.out.println ("\t|                - Yu and Huang (2001)                                                                                          ||");

		System.out.println ("\t|                - BMDP (2018)                                                                                                  ||");

		System.out.println ("\t|                - Blom (1958)                                                                                                  ||");

		System.out.println ("\t|                - Cunnane (1978)                                                                                               ||");

		System.out.println ("\t|                - Gringorten (1963)                                                                                            ||");

		System.out.println ("\t|                - Hazen (1913)                                                                                                 ||");

		System.out.println ("\t|                - Larsen, Currant, and Hunt (1980)                                                                             ||");

		System.out.println ("\t|                - Filliben (1975)                                                                                              ||");

		System.out.println ("\t|                - Filliben (1975) Median Based                                                                                 ||");

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------------||---------||");

		OrderStatisticQuantiles (
			PlottingPositionGeneratorHeuristic.Standard (orderStatisticCount).generate(),
			PlottingPositionGeneratorHeuristic.BernardBosLevenbach1953 (orderStatisticCount).generate(),
			PlottingPositionGeneratorHeuristic.NIST2013 (orderStatisticCount).generate(),
			PlottingPositionGeneratorHeuristic.YuHuang2001 (orderStatisticCount).generate(),
			PlottingPositionGeneratorHeuristic.BMDP2018 (orderStatisticCount).generate(),
			PlottingPositionGeneratorHeuristic.Blom1958 (orderStatisticCount).generate(),
			PlottingPositionGeneratorHeuristic.Cunnane1978 (orderStatisticCount).generate(),
			PlottingPositionGeneratorHeuristic.Gringorten1963 (orderStatisticCount).generate(),
			PlottingPositionGeneratorHeuristic.Hazen1913 (orderStatisticCount).generate(),
			PlottingPositionGeneratorHeuristic.LarsenCurrantHunt1980 (orderStatisticCount).generate(),
			PlottingPositionGeneratorHeuristic.Filliben1975 (orderStatisticCount).generate(),
			new PlottingPositionGeneratorFilliben (orderStatisticCount).generate()
		);

		System.out.println ("\t|--------------------------------------------------------------------------------------------------------------------||---------||");

		EnvManager.TerminateEnv();
	}
}
