
package org.drip.sample.stochasticvolatility;

import org.drip.numerical.fourier.PhaseAdjuster;
import org.drip.param.pricer.HestonOptionPricerParams;
import org.drip.pricer.option.*;
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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * <i>HestonAMSTPayoffTransform</i> contains an Comparison of the two ways of computing the Fourier
 * 	convolution of the terminal payoff - the original Heston (1993) method, and the Albrecher, Mayer,
 * 	Schoutens, and Tistaert tweak (2007).
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/stochasticvolatility/README.md">Heston AMST Stochastic Volatility Pricing</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class HestonAMSTPayoffTransform {
	public static final double TestPayoffScheme (
		final double dblTimeToExpiry,
		final int iPayoffTransformScheme,
		final boolean bProb1)
		throws Exception
	{
		double dblRho = 0.3;
		double dblKappa = 1.;
		double dblSigma = 0.5;
		double dblTheta = 0.2;
		double dblLambda = 0.;

		HestonOptionPricerParams fphp = new HestonOptionPricerParams (
			iPayoffTransformScheme,
			dblRho,
			dblKappa,
			dblSigma,
			dblTheta,
			dblLambda,
			PhaseAdjuster.MULTI_VALUE_BRANCH_POWER_PHASE_TRACKER_KAHL_JACKEL
		);

		HestonStochasticVolatilityAlgorithm hsva = new HestonStochasticVolatilityAlgorithm (fphp);

		double dblStrike = 1.;
		double dblRiskFreeRate = 0.0;
		double dblSpot = 1.;
		double dblSpotVolatility = 0.1;

		Greeks greeks = hsva.greeks (
			dblStrike,
			dblTimeToExpiry,
			dblRiskFreeRate,
			dblSpot,
			false,
			false,
			dblSpotVolatility
		);

		return bProb1 ? greeks.prob1() : greeks.prob2();
	}

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		double[] adblTTE = new double[] {
			0.5, 1., 2., 3., 4., 5., 7., 10., 12., 15., 20., 25., 30.
		};

		System.out.println ("\n\t|---------------------------|");

		System.out.println ("\t|     Prob 1 Comparison     |");

		System.out.println ("\t|---------------------------|");

		System.out.println ("\t|  TTE  =   HSTN  |   AMST  |");

		System.out.println ("\t|---------------------------|");

		for (double dblTTE : adblTTE) {
			System.out.println ("\t|" + FormatUtil.FormatDouble (dblTTE, 2, 2, 1.) + " = " +
				FormatUtil.FormatDouble (TestPayoffScheme (dblTTE,
					HestonStochasticVolatilityAlgorithm.PAYOFF_TRANSFORM_SCHEME_HESTON_1993, true), 1, 4, 1.) + " | "  +
				FormatUtil.FormatDouble (TestPayoffScheme (dblTTE,
					HestonStochasticVolatilityAlgorithm.PAYOFF_TRANSFORM_SCHEME_AMST_2007, true), 1, 4, 1.) + " |");
		}

		System.out.println ("\t|---------------------------|");

		System.out.println ("\n\t|---------------------------|");

		System.out.println ("\t|     Prob 2 Comparison     |");

		System.out.println ("\t|---------------------------|");

		System.out.println ("\t|  TTE  =   HSTN  |   AMST  |");

		System.out.println ("\t|---------------------------|");

		for (double dblTTE : adblTTE) {
			System.out.println ("\t|" + FormatUtil.FormatDouble (dblTTE, 2, 2, 1.) + " = " +
				FormatUtil.FormatDouble (TestPayoffScheme (dblTTE,
					HestonStochasticVolatilityAlgorithm.PAYOFF_TRANSFORM_SCHEME_HESTON_1993, false), 1, 4, 1.) + " | "  +
				FormatUtil.FormatDouble (TestPayoffScheme (dblTTE,
					HestonStochasticVolatilityAlgorithm.PAYOFF_TRANSFORM_SCHEME_AMST_2007, false), 1, 4, 1.) + " |");
		}

		System.out.println ("\t|---------------------------|");

		EnvManager.TerminateEnv();
	}
}
