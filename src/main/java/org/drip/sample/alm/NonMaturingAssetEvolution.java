
package org.drip.sample.alm;

import org.drip.alm.dynamics.EvolutionDigest;
import org.drip.alm.dynamics.NonMaturingAsset;
import org.drip.alm.dynamics.AssetEvolver;
import org.drip.alm.dynamics.SpotMarketParameters;
import org.drip.measure.statistics.UnivariateDiscreteThin;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;

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
 * <i>NonMaturingAssetEvolution</i> illustrates the Price Evolution of a Non-maturing Asset. The References
 * are:
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ALMAnalyticsLibrary.md">Asset Liability Management Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/alm/README.md">Sharpe-Tint-Yotsuzuka ALM Module</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class NonMaturingAssetEvolution
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double initialPrice = 0.84;
		int pathCount = 100000;
		String maturityTenor = "5Y";
		int evolutionTenorInMonths = 3;
		double initialPriceVolatility = 0.020;
		double forwardYieldLowerBound = 0.0001;
		double nonMaturingAssetInitialPrice = 50.;
		double nonMaturingAssetAnnualReturn = 0.07;
		double nonMaturingAssetAnnualVolatility = 0.10;
		double nonMaturingAssetHoldings = 1.;

		SpotMarketParameters spotMarketParameters = new SpotMarketParameters (
			initialPrice,
			initialPriceVolatility,
			forwardYieldLowerBound,
			nonMaturingAssetInitialPrice,
			nonMaturingAssetAnnualReturn,
			nonMaturingAssetAnnualVolatility
		);

		NonMaturingAsset nonMaturingAsset = new NonMaturingAsset (
			"NON MATURING ASSET",
			nonMaturingAssetHoldings
		);

		AssetEvolver assetEvolver = new AssetEvolver (
			pathCount,
			evolutionTenorInMonths + "M",
			maturityTenor
		);

		System.out.println ("\t|------------------------------------||");

		System.out.println ("\t|      Maturing Asset Simulation     ||");

		System.out.println ("\t|------------------------------------||");

		System.out.println ("\t| Maturity Tenor            => " + maturityTenor);

		System.out.println ("\t| Starting Price            => " + (100. * initialPrice));

		System.out.println ("\t| Starting Price Volatility => " + initialPriceVolatility);

		System.out.println ("\t| Forward Yield Lower Bound => " + forwardYieldLowerBound);

		System.out.println ("\t| Number of Simulated Paths => " + pathCount);

		System.out.println ("\t| Evolution Tenor           => " + evolutionTenorInMonths + "M");

		System.out.println ("\t|------------------------------------||");

		System.out.println();

		EvolutionDigest evolutionDigest = assetEvolver.simulate (
			nonMaturingAsset,
			spotMarketParameters
		);

		String[] evolutionTenorArray = evolutionDigest.pathForwardTenorArray();

		String trajectoryTenor = "";

		for (String trajectoryEvolutionTenor : evolutionTenorArray)
		{
			trajectoryTenor = trajectoryTenor + trajectoryEvolutionTenor + ",";
		}

		System.out.println (trajectoryTenor);

		double[][] pathForwardPriceGrid = evolutionDigest.pathForwardPriceGrid();

		for (int pathIndex = 0; pathIndex < assetEvolver.pathCount(); ++pathIndex)
		{
			String trajectory = "";

			for (int forwardTenorIndex = 0; forwardTenorIndex < evolutionTenorArray.length; ++forwardTenorIndex)
			{
				trajectory = trajectory + FormatUtil.FormatDouble (
					pathForwardPriceGrid[forwardTenorIndex][pathIndex], 3, 8, 1., false
				) + ",";
			}

			System.out.println (trajectory);
		}

		System.out.println();

		UnivariateDiscreteThin[] univariateDiscreteThinArray = evolutionDigest.thinStatisticsArray();

		System.out.println ("\t|---------------------------------------------------------------||");

		System.out.println ("\t|            STATISTICS AT THE EVOLUTION TENOR NODES            ||");

		System.out.println ("\t|---------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                ||");

		System.out.println ("\t|                - Minimum                                      ||");

		System.out.println ("\t|                - Maximum                                      ||");

		System.out.println ("\t|                - Mean                                         ||");

		System.out.println ("\t|                - Error                                        ||");

		System.out.println ("\t|---------------------------------------------------------------||");

		for (int forwardPriceIndex = 0; forwardPriceIndex < evolutionTenorArray.length; ++forwardPriceIndex)
		{
			String tenorStatisticsDump = "\t| " +
				evolutionTenorArray[forwardPriceIndex] + " => " +
				FormatUtil.FormatDouble (
					univariateDiscreteThinArray[forwardPriceIndex].minimum(), 3, 8, 1., false
				) + " | " +
				FormatUtil.FormatDouble (
					univariateDiscreteThinArray[forwardPriceIndex].maximum(), 3, 8, 1., false
				) + " | " +
				FormatUtil.FormatDouble (
					univariateDiscreteThinArray[forwardPriceIndex].average(), 3, 8, 1., false
				) + " | " +
				FormatUtil.FormatDouble (
					univariateDiscreteThinArray[forwardPriceIndex].error(), 1, 8, 1., false
				) + " ||";

			System.out.println (tenorStatisticsDump);
		}

		System.out.println ("\t|----------------------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
