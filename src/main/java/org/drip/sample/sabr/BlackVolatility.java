
package org.drip.sample.sabr;

import org.drip.dynamics.sabr.*;
import org.drip.numerical.common.FormatUtil;
import org.drip.sequence.random.BoxMullerGaussian;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.ForwardLabel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>BlackVolatility</i> demonstrates the Construction and Usage of the SABR Model to Imply the Black
 * 	Volatility of a given Contract.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/preferred/README.md">SABR Forward Evolution Black Volatility</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BlackVolatility {

	private static StochasticVolatilityStateEvolver SABREvolver (
		final double dblBeta,
		final double dblRho,
		final double dblVolatilityOfVolatility)
		throws Exception
	{
		return new StochasticVolatilityStateEvolver (
			ForwardLabel.Create (
				"USD",
				"6M"
			),
			dblBeta,
			dblRho,
			dblVolatilityOfVolatility,
			new BoxMullerGaussian (
				0.,
				1.
			),
			new BoxMullerGaussian (
				0.,
				1.
			)
		);
	}

	private static void VolatilitySurface (
		final StochasticVolatilityStateEvolver seSABR,
		final double[] adblStrike,
		final double dblATMForwardRate,
		final double dblTTE,
		final double dblSigma0)
	{
		String strDump = "\t| " + FormatUtil.FormatDouble (dblTTE, 1, 2, 1.) + " => ";

		for (int i = 0; i < adblStrike.length; ++i) {
			ImpliedBlackVolatility ibv = seSABR.computeBlackVolatility (
				adblStrike[i],
				dblATMForwardRate,
				dblTTE,
				dblSigma0
			);

			strDump += FormatUtil.FormatDouble (ibv.impliedVolatility(), 2, 1, 100.) + " | ";
		}

		System.out.println (strDump);
	}

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblRho = 0.;
		double dblBeta = 0.7;
		double dblATMForwardRate = 0.04;
		double dblVolatilityOfVolatility = 0.5;
		double adblForwardRateVolatility = 0.10;
		double[] adblStrike = {
			0.30, 0.35, 0.40, 0.45, 0.50
		};
		double[] adblTTE = {
			0.25, 0.50, 0.75, 1.00, 2.00, 3.00, 4.00, 5.00, 7.00, 9.99
		};

		StochasticVolatilityStateEvolver seSABR = SABREvolver (
			dblBeta,
			dblRho,
			dblVolatilityOfVolatility
		);

		System.out.println ("\n\t|------------------------------------------------|");

		System.out.println ("\t|         SABR IMPLIED BLACK VOLATILITY          |");

		System.out.println ("\t|------------------------------------------------|");

		for (double dblTTE : adblTTE)
			VolatilitySurface (
				seSABR,
				adblStrike,
				dblATMForwardRate,
				dblTTE,
				adblForwardRateVolatility
			);

		System.out.println ("\t|------------------------------------------------|");

		EnvManager.TerminateEnv();
	}
}
