
package org.drip.sample.pykhtin2009;

import org.drip.exposure.regression.LocalVolatilityGenerationControl;
import org.drip.exposure.regression.PykhtinPillar;
import org.drip.exposure.regression.PykhtinPillarDynamics;
import org.drip.function.definition.R1ToR1;
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
 * <i>LocalVolatilityRegressor</i> is a Demonstration of the Exposure Regression Local Volatility Methodology
 * 	of Pykhtin (2009). The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  			Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  			<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Re-thinking Margin Period of Risk
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2902737 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Andersen, L. B. G., M. Pykhtin, and A. Sokol (2017): Credit Exposure in the Presence of Initial
 *  			Margin https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2806156 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/pykhtin2009/README.md">Regression Based Secondary Stochastic Projection</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LocalVolatilityRegressor
{

	/**
	 * Entry Point
	 * 
	 * @param args Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] args)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int exposureCount = 1000;
		double exposureLow = 70.;
		double exposureHigh = 130.;
		double[] exposureArray = new double[exposureCount];

		LocalVolatilityGenerationControl localVolatilityGenerationControl =
			LocalVolatilityGenerationControl.Standard (exposureCount);

		for (int exposureIndex = 0; exposureIndex < exposureCount; ++exposureIndex)
		{
			exposureArray[exposureIndex] = exposureLow + (exposureHigh - exposureLow) * Math.random();
		}

		PykhtinPillarDynamics vertexRealization = PykhtinPillarDynamics.Standard (exposureArray);

		PykhtinPillar[] pillarVertexArray = vertexRealization.pillarVertexArray
			(localVolatilityGenerationControl);

		R1ToR1 localVolatilityR1ToR1 = vertexRealization.localVolatilityR1ToR1 (
			localVolatilityGenerationControl,
			pillarVertexArray
		);

		System.out.println ("\t||-----------------------------------------------------||");

		System.out.println ("\t||       Pykhtin (2009) Terminal Brownian Bridge       ||");

		System.out.println ("\t||-----------------------------------------------------||");

		System.out.println ("\t||                                                     ||");

		System.out.println ("\t||  L -> R:                                            ||");

		System.out.println ("\t||                                                     ||");

		System.out.println ("\t||      Terminal Numeraire                             ||");

		System.out.println ("\t||      Ranking Ordinal                                ||");

		System.out.println ("\t||      Uniform CDF                                    ||");

		System.out.println ("\t||      Gaussian Predictor Variate                     ||");

		System.out.println ("\t||      Local Volatility Estimate                      ||");

		System.out.println ("\t||-----------------------------------------------------||");

		for (PykhtinPillar pillarVertex : pillarVertexArray)
		{
			double exposure = pillarVertex.exposure();

			System.out.println (
				"\t|| " +
				FormatUtil.FormatDouble (exposure, 3, 2, 1.) + " | " +
				FormatUtil.FormatDouble (pillarVertex.order(), 3, 0, 1.) + " | " +
				FormatUtil.FormatDouble (pillarVertex.cdf(), 1, 3, 1.) + " | " +
				FormatUtil.FormatDouble (pillarVertex.variate(), 1, 4, 1.) + " | " +
				FormatUtil.FormatDouble (pillarVertex.localVolatility(), 2, 2, 1.) + " | " +
				FormatUtil.FormatDouble (localVolatilityR1ToR1.evaluate (exposure), 2, 2, 1.) + " ||"
			);
		}

		System.out.println ("\t||-----------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
