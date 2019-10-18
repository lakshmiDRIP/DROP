
package org.drip.sample.assetallocation;

import org.drip.feed.loader.*;
import org.drip.function.rdtor1descent.LineStepEvolutionControl;
import org.drip.function.rdtor1solver.InteriorPointBarrierControl;
import org.drip.measure.statistics.MultivariateMoments;
import org.drip.numerical.common.FormatUtil;
import org.drip.portfolioconstruction.allocator.*;
import org.drip.portfolioconstruction.asset.*;
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
 * <i>ReturnsConstrainedVarianceMinimizer</i> demonstrates the Construction of an Optimal Portfolio using the
 * Variance Minimizing Allocator with Weight Normalization Constraints and Design Returns Constraints.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/assetallocation/README.md">Asset Allocation</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ReturnsConstrainedVarianceMinimizer
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		String seriesPath = "T:\\Lakshmi\\DROP\\Daemons\\Feeds\\MeanVarianceOptimizer\\FormattedSeries1.csv";

		CSVGrid csvGrid = CSVParser.NamedStringGrid (
			seriesPath
		);

		String[] variateHeaderArray = csvGrid.headers();

		double designReturn = 0.0026;
		double assetHoldingsLowerBound = 0.05;
		double assetHoldingsUpperBound = 0.65;
		String[] assetIDArray = new String[variateHeaderArray.length - 1];
		double[][] variateSampleGrid = new double[variateHeaderArray.length - 1][];

		for (int assetIndex = 0;
			assetIndex < assetIDArray.length;
			++assetIndex)
		{
			assetIDArray[assetIndex] = variateHeaderArray[assetIndex + 1];

			variateSampleGrid[assetIndex] = csvGrid.doubleArrayAtColumn (
				assetIndex + 1
			);
		}

		AssetUniverseStatisticalProperties assetUniverseStatisticalProperties =
			AssetUniverseStatisticalProperties.FromMultivariateMetrics (
				MultivariateMoments.Standard (
					assetIDArray,
					variateSampleGrid
				)
			);

		double[][] covarianceMatrix = assetUniverseStatisticalProperties.covariance (
			assetIDArray
		);

		System.out.println ("\n\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                  CROSS ASSET COVARIANCE MATRIX                                 ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		String header = "\t|     |";

		for (int assetIndex = 0;
			assetIndex < assetIDArray.length;
			++assetIndex)
		{
			header += "    " + assetIDArray[assetIndex] + "     |";
		}

		System.out.println (header + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int assetIndexI = 0;
			assetIndexI < assetIDArray.length;
			++assetIndexI)
		{
			String strDump = "\t| " + assetIDArray[assetIndexI] + " ";

			for (int assetIndexJ = 0;
				assetIndexJ < assetIDArray.length;
				++assetIndexJ)
			{
				strDump += "|" + FormatUtil.FormatDouble (
					covarianceMatrix[assetIndexI][assetIndexJ], 1, 8, 1.
				) + " ";
			}

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||\n\n");

		System.out.println ("\t|------------------||");

		System.out.println ("\t|   ASSET BOUNDS   ||");

		System.out.println ("\t|------------------||");

		for (int assetIndex = 0;
			assetIndex < assetIDArray.length;
			++assetIndex)
		{
			System.out.println (
				"\t| " + assetIDArray[assetIndex] + " | " +
				FormatUtil.FormatDouble (assetHoldingsLowerBound, 1, 0, 100.) + "% | " +
				FormatUtil.FormatDouble (assetHoldingsUpperBound, 2, 0, 100.) + "% ||"
			);
		}

		System.out.println ("\t|------------------||\n\n");

		InteriorPointBarrierControl interiorPointBarrierControl = InteriorPointBarrierControl.Standard();

		System.out.println ("\t|--------------------------------------------||");

		System.out.println ("\t|  INTERIOR POINT METHOD BARRIER PARAMETERS  ||");

		System.out.println ("\t|--------------------------------------------||");

		System.out.println (
			"\t|    Barrier Decay Velocity        : " + 1. / interiorPointBarrierControl.decayVelocity()
		);

		System.out.println (
			"\t|    Barrier Decay Steps           : " + interiorPointBarrierControl.decayStepCount()
		);

		System.out.println (
			"\t|    Initial Barrier Strength      : " + interiorPointBarrierControl.initialStrength()
		);

		System.out.println (
			"\t|    Barrier Convergence Tolerance : " + interiorPointBarrierControl.relativeTolerance()
		);

		System.out.println ("\t|--------------------------------------------||\n\n");

		BoundedPortfolioConstructionParameters boundedPortfolioConstructionParameters =
			new BoundedPortfolioConstructionParameters (
				assetIDArray,
				CustomRiskUtilitySettings.VarianceMinimizer(),
				new EqualityConstraintSettings (
					EqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT |
						EqualityConstraintSettings.RETURNS_CONSTRAINT,
					designReturn
				)
			);

		for (int assetIndex = 0;
			assetIndex < assetIDArray.length;
			++assetIndex)
		{
			boundedPortfolioConstructionParameters.addBound (
				assetIDArray[assetIndex],
				assetHoldingsLowerBound,
				assetHoldingsUpperBound
			);
		}

		OptimizationOutput optimizationOutput = new ConstrainedMeanVarianceOptimizer (
			interiorPointBarrierControl,
			LineStepEvolutionControl.NocedalWrightStrongWolfe (
				false
			)
		).allocate (
			boundedPortfolioConstructionParameters,
			assetUniverseStatisticalProperties
		);

		System.out.println ("\t|---------------||");

		System.out.println ("\t| ASSET WEIGHTS ||");

		System.out.println ("\t|---------------||");

		for (AssetComponent assetComponent : optimizationOutput.optimalPortfolio().assetComponentArray())
		{
			System.out.println (
				"\t| " + assetComponent.id() + " | " + FormatUtil.FormatDouble (
					assetComponent.amount(), 2, 2, 100.
				) + "% ||"
			);
		}

		System.out.println ("\t|---------------||\n\n");

		System.out.println ("\t|-----------------------------------------||");

		System.out.println (
			"\t| Portfolio Notional           : " + FormatUtil.FormatDouble (
				optimizationOutput.optimalPortfolio().notional(), 1, 4, 1.
			) + "  ||"
		);

		System.out.println (
			"\t| Portfolio Design Return      : " + FormatUtil.FormatDouble (
				designReturn, 1, 4, 100.
			) + "% ||"
		);

		System.out.println (
			"\t| Portfolio Expected Return    : " + FormatUtil.FormatDouble (
				optimizationOutput.optimalMetrics().excessReturnsMean(), 1, 4, 100.
			) + "% ||"
		);

		System.out.println (
			"\t| Portfolio Standard Deviation : " + FormatUtil.FormatDouble (
				optimizationOutput.optimalMetrics().excessReturnsStandardDeviation(), 1, 4, 100.
			) + "% ||"
		);

		System.out.println ("\t|-----------------------------------------||\n");

		EnvManager.TerminateEnv();
	}
}
