
package org.drip.sample.digamma;

import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.specialfunction.digamma.CumulativeSeriesEstimator;
import org.drip.specialfunction.digamma.SaddlePoints;
import org.drip.specialfunction.loggamma.InfiniteSumEstimator;

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
 * <i>ZeroToOneEstimate</i> demonstrates the Estimation of the Digamma Function using the Mezo-Hoffman (2017)
 * 	Series. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): Handbook of Mathematics Functions <b>Dover Book on
 * 				Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Blagouchine, I. V. (2018): Three Notes on Ser's and Hasse's Representations for the
 * 				Zeta-Functions https://arxiv.org/abs/1606.02044 <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Mezo, I., and M. E. Hoffman (2017): Zeros of the Digamma Function and its Barnes G-function
 * 				Analogue <i>Integral Transforms and Special Functions</i> <b>28 (28)</b> 846-858
 * 		</li>
 * 		<li>
 * 			Whitaker, E. T., and G. N. Watson (1996): <i>A Course on Modern Analysis</i> <b>Cambridge
 * 				University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Digamma Function https://en.wikipedia.org/wiki/Digamma_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/digamma/README.md">Estimates of the Digamma Function</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ZeroToOneEstimate
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int termCount = 1000000;
		int gaussTermCount = 100;
		int saddlePointCount = 100000;
		int logGammaTermCount = 1000000;
		double[] zArray =
		{
			0.05,
			0.10,
			0.15,
			0.20,
			0.25,
			0.30,
			0.35,
			0.40,
			0.45,
			0.50,
			0.55,
			0.60,
			0.65,
			0.70,
			0.75,
			0.80,
			0.85,
			0.90,
			0.95,
		};

		CumulativeSeriesEstimator abramowitzStegunSeries =
			CumulativeSeriesEstimator.AbramowitzStegun2007 (termCount);

		CumulativeSeriesEstimator mezoHoffmanSeries = CumulativeSeriesEstimator.MezoHoffman2017 (
			InfiniteSumEstimator.Weierstrass (logGammaTermCount),
			SaddlePoints.HermiteEnhancement(),
			saddlePointCount
		);

		CumulativeSeriesEstimator gaussSeries = CumulativeSeriesEstimator.Gauss (gaussTermCount);

		CumulativeSeriesEstimator exponentialAsymptoticSeries =
			CumulativeSeriesEstimator.ExponentialAsymptote();

		System.out.println ("\t|---------------------------------------------------------------------------||");

		System.out.println ("\t|                         0 -> 1 DIGAMMA ESTIMATE                           ||");

		System.out.println ("\t|---------------------------------------------------------------------------||");

		System.out.println ("\t|    L -> R:                                                                ||");

		System.out.println ("\t|        - z                                                                ||");

		System.out.println ("\t|        - Abramowitz-Stegun (2007)                                         ||");

		System.out.println ("\t|        - Gauss                                                            ||");

		System.out.println ("\t|        - Exponential Asymptotic                                           ||");

		System.out.println ("\t|        - Mezo Hoffman (2017)                                              ||");

		System.out.println ("\t|---------------------------------------------------------------------------||");

		for (double z : zArray)
		{
			System.out.println (
				"\t|" + FormatUtil.FormatDouble (z, 1, 2, 1.) + " => " +
				FormatUtil.FormatDouble (
					abramowitzStegunSeries.evaluate (z),
					2, 10, 1.
				) + " | " + FormatUtil.FormatDouble (
					gaussSeries.evaluate (z),
					2, 10, 1.
				) + " | " + FormatUtil.FormatDouble (
					-1. * Math.log (exponentialAsymptoticSeries.evaluate (z)),
					2, 10, 1.
				) + " | " + FormatUtil.FormatDouble (
					mezoHoffmanSeries.evaluate (z),
					2, 10, 1.
				) + " ||"
			);
		}

		System.out.println ("\t|---------------------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
