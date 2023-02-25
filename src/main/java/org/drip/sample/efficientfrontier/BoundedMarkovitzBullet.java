
package org.drip.sample.efficientfrontier;

import java.util.*;

import org.drip.function.rdtor1descent.LineStepEvolutionControl;
import org.drip.function.rdtor1solver.InteriorPointBarrierControl;
import org.drip.measure.statistics.MultivariateMoments;
import org.drip.portfolioconstruction.allocator.*;
import org.drip.portfolioconstruction.asset.*;
import org.drip.portfolioconstruction.mpt.MarkovitzBullet;
import org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties;
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
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>BoundedMarkovitzBullet</i> demonstrates the Construction of the Efficient Frontier using the
 * Constrained Mean Variance Optimizer for a Bounded Portfolio.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/efficientfrontier/README.md">Efficient Frontier Markovitz Bullet Variants</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BoundedMarkovitzBullet
{

	private static void DisplayPortfolioMetrics (
		final HoldingsAllocation optimalOutput)
		throws Exception
	{
		AssetComponent[] globalMinimumAssetComponentArray =
			optimalOutput.optimalPortfolio().assetComponentArray();

		String dump = "\t|" +
			FormatUtil.FormatDouble (optimalOutput.optimalMetrics().excessReturnsMean(), 1, 4, 100.) + "% |" +
			FormatUtil.FormatDouble (optimalOutput.optimalMetrics().excessReturnsStandardDeviation(), 1, 4, 100.) + " |";

		for (AssetComponent assetComponent : globalMinimumAssetComponentArray)
		{
			dump += " " + FormatUtil.FormatDouble (
				assetComponent.amount(), 3, 2, 100.
			) + "% |";
		}

		System.out.println (dump + "|");
	}

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] assetNameArray = new String[]
		{
			"TOK",
			"EWJ",
			"HYG",
			"LQD",
			"EMD",
			"GSG",
			"BWX"
		};
		double[] assetHoldingsLowerBoundArray = new double[]
		{
			0.05,
			0.05,
			0.05,
			0.10,
			0.05,
			0.05,
			0.03
		};
		double[] assetHoldingsUpperBoundArray = new double[]
		{
			0.40,
			0.40,
			0.30,
			0.60,
			0.35,
			0.15,
			0.50
		};
		double[] expectedAssetReturnsArray = new double[]
		{
			0.008430,
			0.007230,
			0.006450,
			0.002560,
			0.004480,
			0.006840,
			0.001670
		};
		double[][] assetReturnsCovarianceMatrix = new double[][]
		{
			{0.002733, 0.002083, 0.001593, 0.000488, 0.001172, 0.002312, 0.000710},
			{0.002083, 0.002768, 0.001302, 0.000457, 0.001105, 0.001647, 0.000563},
			{0.001593, 0.001302, 0.001463, 0.000639, 0.001050, 0.001110, 0.000519},
			{0.000488, 0.000457, 0.000639, 0.000608, 0.000663, 0.000042, 0.000370},
			{0.001172, 0.001105, 0.001050, 0.000663, 0.001389, 0.000825, 0.000661},
			{0.002312, 0.001647, 0.001110, 0.000042, 0.000825, 0.005211, 0.000749},
			{0.000710, 0.000563, 0.000519, 0.000370, 0.000661, 0.000749, 0.000703}
		};
		int frontierSampleUnits = 20;

		AssetUniverseStatisticalProperties assetUniverseStatisticalProperties =
			AssetUniverseStatisticalProperties.FromMultivariateMetrics (
				MultivariateMoments.Standard (
					assetNameArray,
					expectedAssetReturnsArray,
					assetReturnsCovarianceMatrix
				)
			);

		double[][] covarianceMatrix = assetUniverseStatisticalProperties.covariance (
			assetNameArray
		);

		System.out.println ("\n\n\t|------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                  CROSS ASSET COVARIANCE MATRIX                                 ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		String header = "\t|     |";

		for (int assetIndex = 0;
			assetIndex < assetNameArray.length;
			++assetIndex)
		{
			header += "    " + assetNameArray[assetIndex] + "     |";
		}

		System.out.println (header + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int assetIndexI = 0;
			assetIndexI < assetNameArray.length;
			++assetIndexI)
		{
			String dump = "\t| " + assetNameArray[assetIndexI] + " ";

			for (int assetIndexJ = 0;
				assetIndexJ < assetNameArray.length;
				++assetIndexJ)
			{
				dump += "|" + FormatUtil.FormatDouble (
					covarianceMatrix[assetIndexI][assetIndexJ], 1, 8, 1.
				) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||\n\n");

		System.out.println ("\t|-------------------||");

		System.out.println ("\t|   ASSET BOUNDS    ||");

		System.out.println ("\t|-------------------||");

		for (int assetIndex = 0;
			assetIndex < assetNameArray.length;
			++assetIndex)
		{
			System.out.println (
				"\t| " + assetNameArray[assetIndex] + " | " +
				FormatUtil.FormatDouble (assetHoldingsLowerBoundArray[assetIndex], 2, 0, 100.) + "% | " +
				FormatUtil.FormatDouble (assetHoldingsUpperBoundArray[assetIndex], 2, 0, 100.) + "% ||"
			);
		}

		System.out.println ("\t|-------------------||\n\n");

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

		BoundedHoldingsAllocationControl boundedPortfolioConstructionParameters =
			new BoundedHoldingsAllocationControl (
				assetNameArray,
				CustomRiskUtilitySettings.VarianceMinimizer(),
				new EqualityConstraintSettings (
					EqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT,
					Double.NaN
				)
			);

		for (int assetIndex = 0;
			assetIndex < assetNameArray.length;
			++assetIndex)
		{
			boundedPortfolioConstructionParameters.addBound (
				assetNameArray[assetIndex],
				assetHoldingsLowerBoundArray[assetIndex],
				assetHoldingsUpperBoundArray[assetIndex]
			);
		}

		MarkovitzBullet markovitzBullet = new ConstrainedMeanVarianceOptimizer (
			interiorPointBarrierControl,
			LineStepEvolutionControl.NocedalWrightStrongWolfe (
				false
			)
		).efficientFrontier (
			boundedPortfolioConstructionParameters,
			assetUniverseStatisticalProperties,
			frontierSampleUnits
		);

		System.out.println ("\n\n\t|-----------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                     GLOBAL MINIMUM VARIANCE AND MAXIMUM RETURNS PORTFOLIOS                    ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		header = "\t| RETURNS | RISK % |";

		for (int assetIndex = 0;
			assetIndex < assetNameArray.length;
			++assetIndex)
		{
			header += "   " + assetNameArray[assetIndex] + "    |";
		}

		System.out.println (header + "|");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		DisplayPortfolioMetrics (markovitzBullet.globalMinimumVariance());

		DisplayPortfolioMetrics (markovitzBullet.longOnlyMaximumReturns());

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||\n\n\n");

		TreeMap<Double, HoldingsAllocation> frontierPortfolioMap = markovitzBullet.optimalPortfolioMap();

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		System.out.println ("\t|         EFFICIENT FRONTIER: PORTFOLIO RISK & RETURNS + CORRESPONDING ASSET ALLOCATION         ||");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		System.out.println (header + "|");

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||");

		for (Map.Entry<Double, HoldingsAllocation> me : frontierPortfolioMap.entrySet())
		{
			DisplayPortfolioMetrics (me.getValue());
		}

		System.out.println ("\t|-----------------------------------------------------------------------------------------------||\n\n");

		EnvManager.TerminateEnv();
	}
}
