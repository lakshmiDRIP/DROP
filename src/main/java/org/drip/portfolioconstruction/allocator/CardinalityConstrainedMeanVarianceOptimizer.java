
package org.drip.portfolioconstruction.allocator;

import org.drip.numerical.common.FormatUtil;

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
 * <i>CardinalityConstrainedMeanVarianceOptimizer</i> builds an Optimal Portfolio Based on MPT Using the
 * 	Asset Pool Statistical Properties with the Specified Lower/Upper Bounds on the Component Assets, along
 * 	with an Upper Bound on Portfolio Cardinality. The References are:
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/allocator/README.md">MVO Based Portfolio Allocation Construction</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CardinalityConstrainedMeanVarianceOptimizer
	extends org.drip.portfolioconstruction.allocator.ConstrainedMeanVarianceOptimizer
{
	private void PrintPortfolio (
		final org.drip.portfolioconstruction.asset.Portfolio optimalPortfolio)
	{
		System.out.println ("\t|------------------||");

		System.out.println ("\t|  OPTIMAL WEIGHTS ||");

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
	}

	private org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters
		workingPortfolioConstructionParameters (
			final java.lang.String[] assetIDArray,
			final org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters
				parentBoundedPortfolioConstructionParameters,
			final java.util.Set<java.lang.String> pruneAssetIDSet)
	{
		int prunedAssetIDIndex = 0;
		org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters
			boundedPortfolioConstructionParameters = null;

		java.lang.String[] prunedAssetIDArray =
			new java.lang.String[assetIDArray.length - pruneAssetIDSet.size()];

		for (int assetIndex = 0;
			assetIndex < assetIDArray.length;
			++assetIndex)
		{
			if (null == pruneAssetIDSet ||
				!pruneAssetIDSet.contains (
					assetIDArray[assetIndex]
				)
			)
			{
				prunedAssetIDArray[prunedAssetIDIndex++] = assetIDArray[assetIndex];
			}
		}

		try
		{
			boundedPortfolioConstructionParameters =
				new org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters (
					prunedAssetIDArray,
					parentBoundedPortfolioConstructionParameters.customRiskUtilitySettings(),
					parentBoundedPortfolioConstructionParameters.equalityConstraintSettings()
				);

			for (int prunedAssetIndex = 0;
				prunedAssetIndex < prunedAssetIDArray.length;
				++prunedAssetIndex)
			{
				boundedPortfolioConstructionParameters.addBound (
					prunedAssetIDArray[prunedAssetIndex],
					0.,
					parentBoundedPortfolioConstructionParameters.upperBound (
						prunedAssetIDArray[prunedAssetIndex]
					)
				);
			}

			return boundedPortfolioConstructionParameters;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private int firstPassPruneList (
		final org.drip.portfolioconstruction.asset.Portfolio optimalPortfolio,
		final org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters
			parentBoundedPortfolioConstructionParameters,
		final java.util.Set<java.lang.String> pruneAssetIDSet)
	{
		int pruneCount = 0;

		org.drip.portfolioconstruction.asset.AssetComponent[] assetComponentArray =
			optimalPortfolio.assetComponentArray();

		try
		{
			for (int assetIndex = 0;
				assetIndex < assetComponentArray.length;
				++assetIndex)
			{
				java.lang.String assetID = assetComponentArray[assetIndex].id();

				if (assetComponentArray[assetIndex].amount() <
					parentBoundedPortfolioConstructionParameters.lowerBound (
						assetID
					)
				)
				{
					pruneAssetIDSet.add (
						assetID
					);

					System.out.println ("\tPruning: " + assetID);

					++pruneCount;
				}
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}


		return pruneCount;
	}

	private boolean secondPassPruneList (
		final org.drip.portfolioconstruction.asset.Portfolio optimalPortfolio,
		final org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters
			parentBoundedPortfolioConstructionParameters,
		final java.util.Set<java.lang.String> pruneAssetIDSet,
		int pruneCount)
	{
		java.util.Map<java.lang.Double, java.lang.String> boundsDepartureMap =
			new java.util.TreeMap<java.lang.Double, java.lang.String>();

		org.drip.portfolioconstruction.asset.AssetComponent[] assetComponentArray =
			optimalPortfolio.assetComponentArray();

		for (int assetIndex = 0;
			assetIndex < assetComponentArray.length;
			++assetIndex)
		{
			java.lang.String assetID = assetComponentArray[assetIndex].id();

			try
			{
				boundsDepartureMap.put (
					assetComponentArray[assetIndex].amount() -
					parentBoundedPortfolioConstructionParameters.lowerBound (
						assetID
					),
					assetID
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return false;
			}
		}

		for (java.util.Map.Entry<java.lang.Double, java.lang.String> boundsDepartureEntry :
			boundsDepartureMap.entrySet())
		{
			if (0 == pruneCount)
			{
				break;
			}

			pruneAssetIDSet.add (
				boundsDepartureEntry.getValue()
			);

			--pruneCount;
		}

		return true;
	}

	/**
	 * CardinalityConstrainedMeanVarianceOptimizer Constructor
	 * 
	 * @param interiorPointBarrierControl Interior Fixed Point Barrier Control Parameters
	 * @param lineStepEvolutionControl Line Step Evolution Control Parameters
	 */

	public CardinalityConstrainedMeanVarianceOptimizer (
		final org.drip.function.rdtor1solver.InteriorPointBarrierControl interiorPointBarrierControl,
		final org.drip.function.rdtor1descent.LineStepEvolutionControl lineStepEvolutionControl)
	{
		super (
			interiorPointBarrierControl,
			lineStepEvolutionControl
		);
	}

	@Override public org.drip.portfolioconstruction.allocator.OptimizationOutput allocate (
		final org.drip.portfolioconstruction.allocator.PortfolioConstructionParameters
			portfolioConstructionParameters,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
			assetUniverseStatisticalProperties)
	{
		if (!(portfolioConstructionParameters instanceof
			org.drip.portfolioconstruction.allocator.BoundedCardinalityParameters))
		{
			return null;
		}

		org.drip.portfolioconstruction.allocator.BoundedCardinalityParameters boundedCardinalityParameters =
			(org.drip.portfolioconstruction.allocator.BoundedCardinalityParameters)
				portfolioConstructionParameters;

		int cardinalityUpperBound = boundedCardinalityParameters.cardinalityUpperBound();

		java.lang.String[] assetIDArray = boundedCardinalityParameters.assetIDArray();

		if (cardinalityUpperBound >= assetIDArray.length)
		{
			return super.allocate (
				portfolioConstructionParameters,
				assetUniverseStatisticalProperties
			);
		}

		java.util.Set<java.lang.String> pruneAssetIDSet = new java.util.HashSet<java.lang.String>();

		org.drip.portfolioconstruction.allocator.BoundedPortfolioConstructionParameters
			workingPortfolioConstructionParameters = workingPortfolioConstructionParameters (
				assetIDArray,
				boundedCardinalityParameters,
				pruneAssetIDSet
			);

		org.drip.portfolioconstruction.allocator.OptimizationOutput optimizationOutput = super.allocate (
			workingPortfolioConstructionParameters,
			assetUniverseStatisticalProperties
		);

		org.drip.portfolioconstruction.asset.Portfolio optimalPortfolio =
			optimizationOutput.optimalPortfolio();

		PrintPortfolio (
			optimalPortfolio
		);

		if (cardinalityUpperBound >= optimalPortfolio.cardinality())
		{
			return optimizationOutput;
		}

		while (0 != firstPassPruneList (
			optimalPortfolio,
			boundedCardinalityParameters,
			pruneAssetIDSet
		))
		{
			workingPortfolioConstructionParameters = workingPortfolioConstructionParameters (
				assetIDArray,
				boundedCardinalityParameters,
				pruneAssetIDSet
			);

			optimizationOutput = super.allocate (
				workingPortfolioConstructionParameters,
				assetUniverseStatisticalProperties
			);

			optimalPortfolio = optimizationOutput.optimalPortfolio();

			PrintPortfolio (
				optimalPortfolio
			);
		}

		if (cardinalityUpperBound >= optimalPortfolio.cardinality())
		{
			return optimizationOutput;
		}

		secondPassPruneList (
			optimalPortfolio,
			boundedCardinalityParameters,
			pruneAssetIDSet,
			optimalPortfolio.cardinality() - cardinalityUpperBound
		);

		workingPortfolioConstructionParameters = workingPortfolioConstructionParameters (
			assetIDArray,
			boundedCardinalityParameters,
			pruneAssetIDSet
		);

		optimizationOutput = super.allocate (
			workingPortfolioConstructionParameters,
			assetUniverseStatisticalProperties
		);

		optimalPortfolio = optimizationOutput.optimalPortfolio();

		PrintPortfolio (
			optimalPortfolio
		);

		return optimizationOutput;
	}
}
