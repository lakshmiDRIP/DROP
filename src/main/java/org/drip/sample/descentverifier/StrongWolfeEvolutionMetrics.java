
package org.drip.sample.descentverifier;

import org.drip.function.rdtor1descent.LineStepEvolutionControl;
import org.drip.function.rdtor1solver.*;
import org.drip.measure.statistics.MultivariateMoments;
import org.drip.portfolioconstruction.allocator.*;
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
 * <i>StrongWolfeEvolutionMetrics</i> demonstrates the Impact of applying the Strong Wolfe Criterion on the
 * Evolution of the R<sup>d</sup> Fixed Point of a Constrained Minimization Search.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/descentverifier/README.md">Armijo/Wolfe Strong/Weak Curvature</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class StrongWolfeEvolutionMetrics
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		FixedRdFinder.s_verifierIncrementBlog = true;

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
		double[] assetLowerBoundArray = new double[]
		{
			0.05,
			0.04,
			0.06,
			0.03,
			0.03,
			0.03,
			0.13
		};
		double[] assetUpperBoundArray = new double[]
		{
			0.43,
			0.27,
			0.44,
			0.32,
			0.66,
			0.32,
			0.88
		};
		double[] assetExpectedReturnsArray = new double[]
		{
			0.1300,
			0.0700,
			0.0400,
			0.0300,
			0.0800,
			0.1000,
			0.0100
		};
		double portfolioDesignReturn = 0.06000;
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

		AssetUniverseStatisticalProperties assetUniverseStatisticalProperties =
			AssetUniverseStatisticalProperties.FromMultivariateMetrics (
				MultivariateMoments.Standard (
					assetNameArray,
					assetExpectedReturnsArray,
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

		for (int assetNameIndex = 0;
			assetNameIndex < assetNameArray.length;
			++assetNameIndex)
		{
			header += "    " + assetNameArray[assetNameIndex] + "     |";
		}

		System.out.println (header + "|");

		System.out.println ("\t|------------------------------------------------------------------------------------------------||");

		for (int assetNameIndexI = 0;
			assetNameIndexI < assetNameArray.length;
			++assetNameIndexI)
		{
			String strDump = "\t| " + assetNameArray[assetNameIndexI] + " ";

			for (int assetNameIndexJ = 0;
				assetNameIndexJ < assetNameArray.length;
				++assetNameIndexJ)
			{
				strDump += "|" + FormatUtil.FormatDouble (
					covarianceMatrix[assetNameIndexI][assetNameIndexJ], 1, 8, 1.
				) + " ";
			}

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------||\n\n");

		System.out.println ("\t|-------------------||");

		System.out.println ("\t|   ASSET BOUNDS    ||");

		System.out.println ("\t|-------------------||");

		for (int assetNameIndex = 0;
			assetNameIndex < assetNameArray.length;
			++assetNameIndex)
		{
			System.out.println (
				"\t| " + assetNameArray[assetNameIndex] + " | " +
				FormatUtil.FormatDouble (assetLowerBoundArray[assetNameIndex], 2, 0, 100.) + "% | " +
				FormatUtil.FormatDouble (assetUpperBoundArray[assetNameIndex], 2, 0, 100.) + "% ||"
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
					EqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT |
						EqualityConstraintSettings.RETURNS_CONSTRAINT,
					portfolioDesignReturn
				)
			);

		for (int assetNameIndex = 0;
			assetNameIndex < assetNameArray.length;
			++assetNameIndex)
		{
			boundedPortfolioConstructionParameters.addBound (
				assetNameArray[assetNameIndex],
				assetLowerBoundArray[assetNameIndex],
				assetUpperBoundArray[assetNameIndex]
			);
		}

		new ConstrainedMeanVarianceOptimizer (
			interiorPointBarrierControl,
			LineStepEvolutionControl.NocedalWrightStrongWolfe (
				false
			)
		).allocate (
			boundedPortfolioConstructionParameters,
			assetUniverseStatisticalProperties
		);

		EnvManager.TerminateEnv();
	}
}
