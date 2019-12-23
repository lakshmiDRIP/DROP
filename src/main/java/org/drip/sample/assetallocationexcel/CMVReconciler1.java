
package org.drip.sample.assetallocationexcel;

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
 * <i>CMVReconciler1</i> demonstrates the Execution and Reconciliation of the Dual Constrained Mean Variance
 * against an XL-based Implementation for Portfolio Design Returns #1.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/assetallocationexcel/README.md">Asset-Bound Allocator Excel Reconciliation</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CMVReconciler1
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		String[] assetIDArray = new String[]
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
			0.03,
			0.07,
			0.02,
			0.09,
			0.06,
			0.01,
			0.24
		};
		double[] assetHoldingsUpperBoundArray = new double[]
		{
			0.30,
			0.35,
			0.28,
			0.38,
			0.28,
			0.16,
			0.43
		};
		double[] expectedAssetReturnsArray = new double[]
		{
			0.0800,
			0.0700,
			0.0600,
			0.0400,
			0.0500,
			0.0900,
			0.0100
		};
		double portfolioDesignReturn = 0.05000;
		double[][] assetReturnsCovarianceMatrix = new double[][]
		{
			{0.002733 * 12, 0.002083 * 12, 0.001593 * 12, 0.000488 * 12, 0.001172 * 12, 0.002312 * 12, 0.000710 * 12},
			{0.002083 * 12, 0.002768 * 12, 0.001302 * 12, 0.000457 * 12, 0.001105 * 12, 0.001647 * 12, 0.000563 * 12},
			{0.001593 * 12, 0.001302 * 12, 0.001463 * 12, 0.000639 * 12, 0.001050 * 12, 0.001110 * 12, 0.000519 * 12},
			{0.000488 * 12, 0.000457 * 12, 0.000639 * 12, 0.000608 * 12, 0.000663 * 12, 0.000042 * 12, 0.000370 * 12},
			{0.001172 * 12, 0.001105 * 12, 0.001050 * 12, 0.000663 * 12, 0.001389 * 12, 0.000825 * 12, 0.000661 * 12},
			{0.002312 * 12, 0.001647 * 12, 0.001110 * 12, 0.000042 * 12, 0.000825 * 12, 0.005211 * 12, 0.000749 * 12},
			{0.000710 * 12, 0.000563 * 12, 0.000519 * 12, 0.000370 * 12, 0.000661 * 12, 0.000749 * 12, 0.000703 * 12}
		};
		double[] reconcilerAssetWeightsArray = new double[]
		{
			0.1617,
			0.0700,
			0.0200,
			0.2883,
			0.0600,
			0.1600,
			0.2400
		};

		AssetComponent[] reconcilerAssetComponentArray =
			new AssetComponent[reconcilerAssetWeightsArray.length];

		for (int assetIndex = 0;
			assetIndex < reconcilerAssetWeightsArray.length;
			++assetIndex)
		{
			reconcilerAssetComponentArray[assetIndex] = new AssetComponent (
				assetIDArray[assetIndex],
				reconcilerAssetWeightsArray[assetIndex]
			);
		}

		AssetUniverseStatisticalProperties assetUniverseStatisticalProperties =
			AssetUniverseStatisticalProperties.FromMultivariateMetrics (
				MultivariateMoments.Standard (
					assetIDArray,
					expectedAssetReturnsArray,
					assetReturnsCovarianceMatrix
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
			String dump = "\t| " + assetIDArray[assetIndexI] + " ";

			for (int assetIndexJ = 0;
				assetIndexJ < assetIDArray.length;
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
			assetIndex < assetIDArray.length;
			++assetIndex)
		{
			System.out.println (
				"\t| " + assetIDArray[assetIndex] + " | " +
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

		BoundedPortfolioConstructionParameters boundedPortfolioConstructionParameters =
			new BoundedPortfolioConstructionParameters (
				assetIDArray,
				CustomRiskUtilitySettings.VarianceMinimizer(),
				new EqualityConstraintSettings (
					EqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT |
						EqualityConstraintSettings.RETURNS_CONSTRAINT,
					portfolioDesignReturn
				)
			);

		for (int assetIndex = 0;
			assetIndex < assetIDArray.length;
			++assetIndex)
		{
			boundedPortfolioConstructionParameters.addBound (
				assetIDArray[assetIndex],
				assetHoldingsLowerBoundArray[assetIndex],
				assetHoldingsUpperBoundArray[assetIndex]
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

		AssetComponent[] optimalAssetComponentArray =
			optimizationOutput.optimalPortfolio().assetComponentArray();

		System.out.println ("\t|--------------------------||");

		System.out.println ("\t|   OPTIMAL ASSET WEIGHTS  ||");

		System.out.println ("\t|--------------------------||");

		System.out.println ("\t| ASSET |  DROP  |  EXCEL  ||");

		System.out.println ("\t|--------------------------||");

		for (int assetIndex = 0;
			assetIndex < optimalAssetComponentArray.length;
			++assetIndex)
		{
			System.out.println (
				"\t|  " + optimalAssetComponentArray[assetIndex].id() + "  |" +
				FormatUtil.FormatDouble (
					optimalAssetComponentArray[assetIndex].amount(), 2, 2, 100.
				) + "% | " +
				FormatUtil.FormatDouble (
					reconcilerAssetComponentArray[assetIndex].amount(), 2, 2, 100.
				) + "% ||"
			);
		}

		System.out.println ("\t|--------------------------||\n\n");

		System.out.println ("\t|------------------------------------------------||");

		System.out.println (
			"\t| Optimal Portfolio Normalize          : " + FormatUtil.FormatDouble (
				optimizationOutput.optimalPortfolio().notional(), 1, 2, 1.
			) + "   ||"
		);

		System.out.println (
			"\t| Optimal Portfolio Input Return       : " + FormatUtil.FormatDouble (
				portfolioDesignReturn, 1, 2, 100.
			) + "%  ||"
		);

		System.out.println (
			"\t| Optimal Portfolio Expected Return    : " + FormatUtil.FormatDouble (
				optimizationOutput.optimalMetrics().excessReturnsMean(), 1, 2, 100.
			) + "%  ||"
		);

		System.out.println (
			"\t| Optimal Portfolio Standard Deviation : " + FormatUtil.FormatDouble (
				optimizationOutput.optimalMetrics().excessReturnsStandardDeviation(), 2, 2, 100.
			) + "% ||"
		);

		System.out.println (
			"\t| Excel Portfolio Standard Deviation   : " + FormatUtil.FormatDouble (
				Math.sqrt (
					new Portfolio (
						reconcilerAssetComponentArray
					).variance (
						assetUniverseStatisticalProperties
					)
				), 2, 2, 100.
			) + "% ||"
		);

		System.out.println ("\t|------------------------------------------------||\n");

		EnvManager.TerminateEnv();
	}
}
