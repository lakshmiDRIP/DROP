
package org.drip.sample.sabr;

import org.drip.analytics.date.*;
import org.drip.dynamics.sabr.*;
import org.drip.sequence.random.BoxMullerGaussian;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.state.identifier.ForwardLabel;

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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>ForwardRateEvolution</i> demonstrates the Construction and Usage of the SABR Model Dynamics for the
 * 	Evolution of Forward Rate.
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

public class ForwardRateEvolution {

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

	private static void SABREvolution (
		final StochasticVolatilityStateEvolver seSABR1,
		final StochasticVolatilityStateEvolver seSABR2,
		final StochasticVolatilityStateEvolver seSABR3,
		final int iSpotDate,
		final int iTerminalDate,
		final ForwardRateUpdate lsqmInitial1,
		final ForwardRateUpdate lsqmInitial2,
		final ForwardRateUpdate lsqmInitial3)
		throws Exception
	{
		int iDayStep = 2;
		int iDate = iSpotDate;
		ForwardRateUpdate lsqm1 = lsqmInitial1;
		ForwardRateUpdate lsqm2 = lsqmInitial2;
		ForwardRateUpdate lsqm3 = lsqmInitial3;

		System.out.println ("\n\t||---------------------------------------------------------------------------------||");

		System.out.println ("\t||     SABR  EVOLUTION  DYNAMICS                                                   ||");

		System.out.println ("\t||---------------------------------------------------------------------------------||");

		System.out.println ("\t||    L -> R:                                                                      ||");

		System.out.println ("\t||        Forward Rate (%)  - Gaussian (beta = 0.0)                                ||");

		System.out.println ("\t||        Forward Rate Vol (%)  - Gaussian (beta = 0.0)                            ||");

		System.out.println ("\t||        Forward Rate (%)  - beta = 0.5                                           ||");

		System.out.println ("\t||        Forward Rate Vol (%)  - beta = 0.5                                       ||");

		System.out.println ("\t||        Forward Rate (%)  - Lognormal (beta = 1.0)                               ||");

		System.out.println ("\t||        Forward Rate Vol (%)  - Lognormal (beta = 1.0)                           ||");

		System.out.println ("\t||---------------------------------------------------------------------------------||");

		while (iDate < iTerminalDate) {
			lsqm1 = (ForwardRateUpdate) seSABR1.evolve (
				iSpotDate,
				iDate,
				iDayStep,
				lsqm1
			);

			lsqm2 = (ForwardRateUpdate) seSABR2.evolve (
				iSpotDate,
				iDate,
				iDayStep,
				lsqm2
			);

			lsqm3 = (ForwardRateUpdate) seSABR3.evolve (
				iSpotDate,
				iDate,
				iDayStep,
				lsqm3
			);

			System.out.println (
				"\t|| " + new JulianDate (iDate) + " => " +
				FormatUtil.FormatDouble (lsqm1.forwardRate(), 1, 4, 100.) + " % | " +
				FormatUtil.FormatDouble (lsqm1.forwardRateVolatility(), 1, 2, 100.) + " % || " +
				FormatUtil.FormatDouble (lsqm2.forwardRate(), 1, 4, 100.) + " % | " +
				FormatUtil.FormatDouble (lsqm2.forwardRateVolatility(), 1, 1, 100.) + " % || " +
				FormatUtil.FormatDouble (lsqm3.forwardRate(), 1, 4, 100.) + " % | " +
				FormatUtil.FormatDouble (lsqm3.forwardRateVolatility(), 1, 1, 100.) + " % ||"
			);

			iDate += iDayStep;
		}

		System.out.println ("\t||---------------------------------------------------------------------------------||");
	}

	public static void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		JulianDate dtSpot = DateUtil.Today();

		double dblRho = 0.1;
		double dblForwardRate = 0.04;
		double dblVolatilityOfVolatility = 0.59;
		String strViewTenor = "3M";
		double[] adblBeta = {
			0.00, 0.50, 1.00
		};
		double[] adblForwardRateVolatility = {
			0.03, 0.26, 0.51
		};

		int iViewDate = dtSpot.addTenor (strViewTenor).julian();

		StochasticVolatilityStateEvolver seSABR1 = SABREvolver (
			adblBeta[0],
			dblRho,
			dblVolatilityOfVolatility
		);

		StochasticVolatilityStateEvolver seSABR2 = SABREvolver (
			adblBeta[1],
			dblRho,
			dblVolatilityOfVolatility
		);

		StochasticVolatilityStateEvolver seSABR3 = SABREvolver (
			adblBeta[2],
			dblRho,
			dblVolatilityOfVolatility
		);

		ForwardRateUpdate lsqmInitial1 = ForwardRateUpdate.Create (
			ForwardLabel.Create (
				"USD",
				"6M"
			),
			dtSpot.julian(),
			dtSpot.julian(),
			iViewDate,
			dblForwardRate,
			0.,
			adblForwardRateVolatility[0],
			0.
		);

		ForwardRateUpdate lsqmInitial2 = ForwardRateUpdate.Create (
			ForwardLabel.Create (
				"USD",
				"6M"
			),
			dtSpot.julian(),
			dtSpot.julian(),
			iViewDate,
			dblForwardRate,
			0.,
			adblForwardRateVolatility[1],
			0.
		);

		ForwardRateUpdate lsqmInitial3 = ForwardRateUpdate.Create (
			ForwardLabel.Create (
				"USD",
				"6M"
			),
			dtSpot.julian(),
			dtSpot.julian(),
			iViewDate,
			dblForwardRate,
			0.,
			adblForwardRateVolatility[2],
			0.
		);

		SABREvolution (
			seSABR1,
			seSABR2,
			seSABR3,
			dtSpot.julian(),
			iViewDate,
			lsqmInitial1,
			lsqmInitial2,
			lsqmInitial3
		);

		EnvManager.TerminateEnv();
	}
}
