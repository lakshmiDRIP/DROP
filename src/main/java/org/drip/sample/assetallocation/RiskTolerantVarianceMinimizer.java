
package org.drip.sample.assetallocation;

import org.drip.feed.loader.*;
import org.drip.measure.statistics.MultivariateMoments;
import org.drip.portfolioconstruction.allocator.*;
import org.drip.portfolioconstruction.asset.*;
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
 * <i>RiskTolerantVarianceMinimizer</i> demonstrates the Construction of an Optimal Portfolio using the
 * Variance Minimization with a Fully Invested Constraint on a Risk Tolerance Objective Function.
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/assetallocation/README.md">MVO Based Constrained Optimal Allocator</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RiskTolerantVarianceMinimizer
{

	static final void RiskTolerancePortfolio (
		final String[] assetIDArray,
		final AssetUniverseStatisticalProperties assetUniverseStatisticalProperties,
		final double riskTolerance)
		throws Exception
	{
		HoldingsAllocation optimizationOutput = new QuadraticMeanVarianceOptimizer().allocate (
			new HoldingsAllocationControl (
				assetIDArray,
				CustomRiskUtilitySettings.RiskTolerant (
					riskTolerance
				),
				new EqualityConstraintSettings (
					EqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT,
					Double.NaN
				)
			),
			assetUniverseStatisticalProperties
		);

		System.out.println ("\t|----------------||");

		for (AssetComponent assetComponent : optimizationOutput.optimalPortfolio().assetComponentArray())
		{
			System.out.println (
				"\t| " + assetComponent.id() + " | " + FormatUtil.FormatDouble (
					assetComponent.amount(), 3, 2, 100.
				) + "% ||"
			);
		}

		System.out.println ("\t|----------------||");

		System.out.println ("\t|---------------------------------------||");

		System.out.println (
			"\t| Portfolio Notional           : " + FormatUtil.FormatDouble (
				optimizationOutput.optimalPortfolio().notional(), 1, 3, 1.
			) + " ||"
		);

		System.out.println (
			"\t| Portfolio Expected Return    : " + FormatUtil.FormatDouble (
				optimizationOutput.optimalMetrics().excessReturnsMean(), 1, 2, 100.
			) + "% ||"
		);

		System.out.println (
			"\t| Portfolio Standard Deviation : " + FormatUtil.FormatDouble (
				optimizationOutput.optimalMetrics().excessReturnsStandardDeviation(), 1, 2, 100.
			) + "% ||"
		);

		System.out.println ("\t|---------------------------------------||\n");
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		String seriesPath = "C:\\DROP\\Daemons\\Feeds\\MeanVarianceOptimizer\\FormattedSeries1.csv";

		CSVGrid csvGrid = CSVParser.NamedStringGrid (
			seriesPath
		);

		String[] variateHeaderArray = csvGrid.headers();

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

		double[] riskToleranceArray = new double[]
		{
			0.1,
			0.2,
			0.3,
			0.5,
			1.0
		};

		for (double riskTolerance : riskToleranceArray)
		{
			System.out.println ("\n\t|---------------------------------------------||");

			System.out.println ("\t| Running Optimization For Risk Tolerance " + riskTolerance + " ||");

			System.out.println ("\t|---------------------------------------------||");

			RiskTolerancePortfolio (
				assetIDArray,
				assetUniverseStatisticalProperties,
				riskTolerance
			);
		}

		EnvManager.TerminateEnv();
	}
}
