
package org.drip.sample.tadonkivial;

import org.drip.function.rdtor1descent.LineStepEvolutionControl;
import org.drip.function.rdtor1solver.InteriorPointBarrierControl;
import org.drip.measure.statistics.MultivariateMoments;
import org.drip.portfolioconstruction.allocator.*;
import org.drip.portfolioconstruction.asset.Portfolio;
import org.drip.portfolioconstruction.cardinality.UpperBoundHoldingsAllocationControl;
import org.drip.portfolioconstruction.cardinality.TadonkiVialHoldingsAllocation;
import org.drip.portfolioconstruction.cardinality.TadonkiVialMeanVarianceOptimizer;
import org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties;
import org.drip.service.common.FormatUtil;
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
 * <i>HeuristicCardinalityBoundOptimizer01</i> demonstrates the Setup and Execution of a Cardinality Bounded
 * 	Portfolio Allocator with Asset Level Bounds using the Tadonki-Vial (2004) Heuristics. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Chang, T., J., N. Meade, J. E. Beasley, and Y. M. Sharaiha (2000): Heuristics for Cardinality
 * 				Constrained Portfolio Optimization <i>Computers and Operations Research</i> <b>27 (13)</b>
 * 				1271-1302
 *  	</li>
 *  	<li>
 * 			Chvatal, V. (1973): Edmonds Polytopes in a Hierarchy of Combinatorial Problems <i>Discrete
 * 				Mathematics</i> <b>4 (4)</b> 305-337
 *  	</li>
 *  	<li>
 * 			Jobst, N. J., M. D. Horniman, C. A. Lucas, and G. Mitra (2001): Computational Aspects of
 * 				Alternative Portfolio Selection Models in the Presence of Discrete Asset Choice Constraints
 * 				<i>Quantitative Finance</i> <b>1 (5)</b> 1-13
 *  	</li>
 *  	<li>
 * 			Letchford, A. N. and A. Lodi (2002): Strengthening Chvatal-Gomory Cuts and Gomory Fractional Cuts
 * 				<i>Operations Research Letters</i> <b>30 (2)</b> 74-82
 *  	</li>
 *  	<li>
 * 			Tadonki, C., and J. P. Vial (2004): Portfolio Selection with Cardinality and Bound Constraints
 * 				https://www.cri.ensmp.fr/~tadonki/PaperForWeb/Tadonki_PF.pdf
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/tadonkivial/README.md">Tadonki-Vial Cardinality Bound Allocation</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class HeuristicCardinalityBoundOptimizer01
{

	private static final void PrintPortfolio (
		final String header,
		final HoldingsAllocation holdingsAllocation)
	{
		if (null == holdingsAllocation)
		{
			return;
		}

		Portfolio optimalPortfolio = holdingsAllocation.optimalPortfolio();

		System.out.println ("\t|------------------||");

		System.out.println ("\t|  " + header);

		System.out.println ("\t|------------------||");

		System.out.println ("\t| ASSET |   DROP   ||");

		System.out.println ("\t|------------------||");

		for (int assetIndex = 0;
			assetIndex < optimalPortfolio.assetComponentArray().length;
			++assetIndex)
		{
			System.out.println (
				"\t|  " + optimalPortfolio.assetComponentArray()[assetIndex].id() + "  |" +
				FormatUtil.FormatDouble (
					optimalPortfolio.assetComponentArray()[assetIndex].amount(), 2, 4, 100.
				) + "% ||"
			);
		}

		System.out.println ("\t|------------------||");

		System.out.println ("\t| Cardinality => " + optimalPortfolio.cardinality());

		System.out.println ("\t|------------------||");

		System.out.println();
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			"",
			true
		);

		int cardinalityUpperBound = 4;
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
			0.008355,
			0.007207,
			0.006279,
			0.002466,
			0.004472,
			0.006821,
			0.001570
		};
		double portfolioDesignReturn = 0.005497;
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

		AssetUniverseStatisticalProperties assetUniverseStatisticalProperties =
			AssetUniverseStatisticalProperties.FromMultivariateMetrics (
				MultivariateMoments.Standard (
					assetIDArray,
					expectedAssetReturnsArray,
					assetReturnsCovarianceMatrix
				)
			);

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

		System.out.println ("\t|-------------------||");

		UpperBoundHoldingsAllocationControl boundedCardinalityParameters =
			new UpperBoundHoldingsAllocationControl (
				assetIDArray,
				CustomRiskUtilitySettings.VarianceMinimizer(),
				new EqualityConstraintSettings (
					EqualityConstraintSettings.FULLY_INVESTED_CONSTRAINT |
						EqualityConstraintSettings.RETURNS_CONSTRAINT,
					portfolioDesignReturn
				),
				cardinalityUpperBound
			);

		for (int assetIndex = 0;
			assetIndex < assetIDArray.length;
			++assetIndex)
		{
			boundedCardinalityParameters.addBound (
				assetIDArray[assetIndex],
				assetHoldingsLowerBoundArray[assetIndex],
				assetHoldingsUpperBoundArray[assetIndex]
			);
		}

		TadonkiVialHoldingsAllocation tadonkiVialHoldingsAllocation = new TadonkiVialMeanVarianceOptimizer (
			InteriorPointBarrierControl.Standard(),
			LineStepEvolutionControl.NocedalWrightStrongWolfe (
				false
			)
		).allocate (
			boundedCardinalityParameters,
			assetUniverseStatisticalProperties
		);

		PrintPortfolio (
			"FLOOR PASS",
			tadonkiVialHoldingsAllocation.floorPassHoldingsAllocation()
		);

		PrintPortfolio (
			"FIRST GREEDY PRUNE PASS",
			tadonkiVialHoldingsAllocation.firstPrunePassHoldingsAllocation()
		);

		PrintPortfolio (
			"SECOND GREEDY PRUNE PASS",
			tadonkiVialHoldingsAllocation.secondPrunePassHoldingsAllocation()
		);

		EnvManager.TerminateEnv();
	}
}
