
package org.drip.sample.alm;

import org.drip.alm.dynamics.AssetEvolver;
import org.drip.alm.dynamics.EvolutionDigest;
import org.drip.alm.dynamics.MatchingPortfolio;
import org.drip.alm.dynamics.MaturingAsset;
import org.drip.alm.dynamics.NonMaturingAsset;
import org.drip.alm.dynamics.SpotMarketParameters;
import org.drip.measure.statistics.UnivariateDiscreteThin;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>MatchingPortfolioEvolution</i> illustrates the Market Value Evolution of a Matching Portfolio. The
 * References are:
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ALMAnalyticsLibrary.md">Asset Liability Management Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/alm/dynamics/README.md">ALM Dynamics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/alm/dynamics/README.md">ALM Portfolio Allocation and Evolution</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MatchingPortfolioEvolution
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int maturity = 5;
		double ytm = 0.0363;
		int pathCount = 100000;
		int evolutionTenorInMonths = 3;
		double forwardYieldLowerBound = -100.;
		double nonMaturingAssetInitialPrice = 1.;
		double nonMaturingAssetAnnualReturn = 0.07;
		double nonMaturingAssetAnnualVolatility = 0.10;

		double initialPriceVolatility = 0.04 / Math.sqrt (maturity);

		double initialPrice = Math.exp (-1. * maturity * ytm);

		String maturityTenor = maturity + "Y";
		double maturingAssetHoldings = 0. / initialPrice;
		double nonMaturingAssetHoldings = 10.;

		SpotMarketParameters spotMarketParameters = new SpotMarketParameters (
			initialPrice,
			initialPriceVolatility,
			forwardYieldLowerBound,
			nonMaturingAssetInitialPrice,
			nonMaturingAssetAnnualReturn,
			nonMaturingAssetAnnualVolatility
		);

		MatchingPortfolio matchingPortfolio = new MatchingPortfolio (
			"MATCHING PORTFOLIO",
			new MaturingAsset (
				"MATURING ASSET",
				maturingAssetHoldings,
				maturityTenor
			),
			new NonMaturingAsset (
				"NON MATURING ASSET",
				nonMaturingAssetHoldings
			)
		);

		AssetEvolver assetEvolver = new AssetEvolver (
			pathCount,
			evolutionTenorInMonths + "M",
			maturityTenor
		);

		System.out.println ("\t|------------------------------------------------||");

		System.out.println ("\t|               Simulation Set-up                ||");

		System.out.println ("\t|------------------------------------------------||");

		System.out.println ("\t| Portfolio Maturity Tenor                 => " + maturityTenor);

		System.out.println ("\t| Maturing Asset Starting Yield            => " + (100. * ytm) + "%");

		System.out.println ("\t| Maturing Asset Starting Price            => " + (100. * initialPrice));

		System.out.println ("\t| Maturing Asset Starting Price Volatility => " + (100. * initialPriceVolatility * Math.sqrt (maturity)) + "%");

		System.out.println ("\t| Maturing Asset Initial Holdings          => " + (maturingAssetHoldings * initialPrice));

		System.out.println ("\t| Non-Maturing Asset Returns               => " + (100. * nonMaturingAssetAnnualReturn) + "%");

		System.out.println ("\t| Non-Maturing Asset Volatility            => " + (100. * nonMaturingAssetAnnualVolatility) + "%");

		System.out.println ("\t| Non-Maturing Asset Initial Holdings      => " + nonMaturingAssetHoldings);

		System.out.println ("\t| Number of Simulated Paths                => " + pathCount);

		System.out.println ("\t| Evolution Tenor                          => " + evolutionTenorInMonths + "M");

		System.out.println ("\t|------------------------------------------------||");

		System.out.println();

		EvolutionDigest evolutionDigest = assetEvolver.simulate (
			matchingPortfolio,
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
