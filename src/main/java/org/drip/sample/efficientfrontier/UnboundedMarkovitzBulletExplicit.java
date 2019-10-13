
package org.drip.sample.efficientfrontier;

import org.drip.feed.loader.*;
import org.drip.measure.statistics.MultivariateMoments;
import org.drip.numerical.common.FormatUtil;
import org.drip.portfolioconstruction.allocator.*;
import org.drip.portfolioconstruction.asset.AssetComponent;
import org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>UnboundedMarkovitzBulletExplicit</i> demonstrates the Explicit Construction of the Efficient Frontier.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationLibrary.md">Asset Allocation Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/efficientfrontier/README.md">Markovitz Efficient Frontier</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class UnboundedMarkovitzBulletExplicit
{

	private static double DisplayPortfolioMetrics (
		final OptimizationOutput optimizationOutput)
		throws Exception
	{
		AssetComponent[] globalMinimumAssetComponentArray =
			optimizationOutput.optimalPortfolio().assetComponentArray();

		String dump = "\t|" + FormatUtil.FormatDouble (
				optimizationOutput.optimalMetrics().excessReturnsMean(), 1, 4, 100.
			) + "% |" + FormatUtil.FormatDouble (
				optimizationOutput.optimalMetrics().excessReturnsStandardDeviation(), 1, 4, 100.
			) + " |";

		for (AssetComponent assetComponent : globalMinimumAssetComponentArray)
		{
			dump += " " + FormatUtil.FormatDouble (
				assetComponent.amount(), 3, 2, 100.
			) + "% |";
		}

		System.out.println (dump + "|");

		return optimizationOutput.optimalMetrics().excessReturnsMean();
	}

	public static final void main (
		final String[] agrumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int riskReturnGranularity = 40;
		double riskToleranceFactor = 0.;
		String seriesLocation =
			"T:\\Lakshmi\\DROP\\Daemons\\Feeds\\MeanVarianceOptimizer\\FormattedSeries1.csv";

		CSVGrid csvGrid = CSVParser.NamedStringGrid (
			seriesLocation
		);

		String[] variateHeaderArray = csvGrid.headers();

		String[] assetIDArray = new String[variateHeaderArray.length - 1];
		double[][] variateSampleGrid = new double[variateHeaderArray.length - 1][];

		for (int assetIndex = 0;
			assetIndex < assetIDArray.length;
			++assetIndex)
		{
			assetIDArray[assetIndex] = variateHeaderArray[assetIndex + 1];

			variateSampleGrid[assetIndex] = csvGrid.doubleArrayAtColumn (assetIndex + 1);
		}

		AssetUniverseStatisticalProperties assetUniverseStatisticalProperties =
			AssetUniverseStatisticalProperties.FromMultivariateMetrics (
				MultivariateMoments.Standard (
					assetIDArray,
					variateSampleGrid
				)
			);

		PortfolioConstructionParameters portfolioConstructionParameters =
			new PortfolioConstructionParameters (
				assetIDArray,
				CustomRiskUtilitySettings.RiskTolerant (
					riskToleranceFactor
				),
				new EqualityConstraintSettings (
					EqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT,
					Double.NaN
				)
			);

		MeanVarianceOptimizer meanVarianceOptimizer = new QuadraticMeanVarianceOptimizer();

		System.out.println ("\n\n\t|-----------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                     GLOBAL MINIMUM VARIANCE AND MAXIMUM RETURNS PORTFOLIOS                    ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		String header = "\t| RETURNS | RISK % |";

		for (int assetIndex = 0;
			assetIndex < assetIDArray.length;
			++assetIndex)
		{
			header += "   " + assetIDArray[assetIndex] + "    |";
		}

		System.out.println (header + "|");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		double globalMinimumVarianceReturns = DisplayPortfolioMetrics (
			meanVarianceOptimizer.globalMinimumVarianceAllocate (
				portfolioConstructionParameters,
				assetUniverseStatisticalProperties
			)
		);

		double maximumReturns = DisplayPortfolioMetrics (
			meanVarianceOptimizer.longOnlyMaximumReturnsAllocate (
				portfolioConstructionParameters,
				assetUniverseStatisticalProperties
			)
		);

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||\n\n\n");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		System.out.println ("\t|         EFFICIENT FRONTIER: PORTFOLIO RISK & RETURNS + CORRESPONDING ASSET ALLOCATION         ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		System.out.println (header + "|");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		double returnsGrain = (maximumReturns - globalMinimumVarianceReturns) / riskReturnGranularity;

		for (int returnOffset = 0;
			returnOffset <= riskReturnGranularity;
			++returnOffset)
		{
			DisplayPortfolioMetrics (
				meanVarianceOptimizer.allocate (
					new PortfolioConstructionParameters (
						assetIDArray,
						CustomRiskUtilitySettings.VarianceMinimizer(),
						new EqualityConstraintSettings (
							EqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT |
								EqualityConstraintSettings.RETURNS_CONSTRAINT,
							globalMinimumVarianceReturns + returnOffset * returnsGrain
						)
					),
					assetUniverseStatisticalProperties
				)
			);
		}

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||\n\n");

		EnvManager.TerminateEnv();
	}
}
