
package org.drip.portfolioconstruction.cardinality;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>TadonkiVialMeanVarianceOptimizer</i> builds an Optimal Portfolio Based on MPT Using the Asset Pool
 * 	Statistical Properties with the Specified Lower/Upper Bounds on the Component Assets, along with an Upper
 * 	Bound on Portfolio Cardinality, using the Tadonki and Vial (2004) Heuristic Scheme. The References are:
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

public class TadonkiVialMeanVarianceOptimizer
	extends org.drip.portfolioconstruction.allocator.ConstrainedMeanVarianceOptimizer
{

	private org.drip.portfolioconstruction.allocator.BoundedHoldingsAllocationControl
		workingPortfolioAllocationControl (
			final java.lang.String[] assetIDArray,
			final org.drip.portfolioconstruction.allocator.BoundedHoldingsAllocationControl
				boundedHoldingsAllocationControl,
			final java.util.Set<java.lang.String> pruneAssetIDSet)
	{
		int prunedAssetIDIndex = 0;
		org.drip.portfolioconstruction.allocator.BoundedHoldingsAllocationControl
			workingPortfolioAllocationControl = null;

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
			workingPortfolioAllocationControl =
				new org.drip.portfolioconstruction.allocator.BoundedHoldingsAllocationControl (
					prunedAssetIDArray,
					boundedHoldingsAllocationControl.customRiskUtilitySettings(),
					boundedHoldingsAllocationControl.equalityConstraintSettings()
				);

			for (int prunedAssetIndex = 0;
				prunedAssetIndex < prunedAssetIDArray.length;
				++prunedAssetIndex)
			{
				workingPortfolioAllocationControl.addBound (
					prunedAssetIDArray[prunedAssetIndex],
					0.,
					boundedHoldingsAllocationControl.upperBound (
						prunedAssetIDArray[prunedAssetIndex]
					)
				);
			}

			return workingPortfolioAllocationControl;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private int firstGreedyPruneList (
		final org.drip.portfolioconstruction.asset.Portfolio optimalPortfolio,
		final org.drip.portfolioconstruction.allocator.BoundedHoldingsAllocationControl
			boundedHoldingsAllocationControl,
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
					boundedHoldingsAllocationControl.lowerBound (
						assetID
					)
				)
				{
					pruneAssetIDSet.add (
						assetID
					);

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

	private boolean secondGreedyPruneList (
		final org.drip.portfolioconstruction.asset.Portfolio optimalPortfolio,
		final org.drip.portfolioconstruction.allocator.BoundedHoldingsAllocationControl
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
	 * TadonkiVialMeanVarianceOptimizer Constructor
	 * 
	 * @param interiorPointBarrierControl Interior Fixed Point Barrier Control Parameters
	 * @param lineStepEvolutionControl Line Step Evolution Control Parameters
	 */

	public TadonkiVialMeanVarianceOptimizer (
		final org.drip.function.rdtor1solver.InteriorPointBarrierControl interiorPointBarrierControl,
		final org.drip.function.rdtor1descent.LineStepEvolutionControl lineStepEvolutionControl)
	{
		super (
			interiorPointBarrierControl,
			lineStepEvolutionControl
		);
	}

	@Override public org.drip.portfolioconstruction.cardinality.TadonkiVialHoldingsAllocation allocate (
		final org.drip.portfolioconstruction.allocator.HoldingsAllocationControl
			holdingsAllocationControl,
		final org.drip.portfolioconstruction.params.AssetUniverseStatisticalProperties
			assetUniverseStatisticalProperties)
	{
		if (!(holdingsAllocationControl instanceof
			org.drip.portfolioconstruction.cardinality.UpperBoundHoldingsAllocationControl))
		{
			return null;
		}

		org.drip.portfolioconstruction.cardinality.UpperBoundHoldingsAllocationControl
			upperBoundHoldingsAllocationControl =
				(org.drip.portfolioconstruction.cardinality.UpperBoundHoldingsAllocationControl)
					holdingsAllocationControl;

		int cardinalityUpperBound = upperBoundHoldingsAllocationControl.cardinalityUpperBound();

		java.lang.String[] assetIDArray = upperBoundHoldingsAllocationControl.assetIDArray();

		if (cardinalityUpperBound >= assetIDArray.length)
		{
			return org.drip.portfolioconstruction.cardinality.TadonkiVialHoldingsAllocation.Standard (
				super.allocate (
					holdingsAllocationControl,
					assetUniverseStatisticalProperties
				)
			);
		}

		java.util.Set<java.lang.String> pruneAssetIDSet = new java.util.HashSet<java.lang.String>();

		org.drip.portfolioconstruction.allocator.BoundedHoldingsAllocationControl
			workingPortfolioAllocationControl = workingPortfolioAllocationControl (
				assetIDArray,
				upperBoundHoldingsAllocationControl,
				pruneAssetIDSet
			);

		org.drip.portfolioconstruction.allocator.HoldingsAllocation floorPassHoldingsAllocation =
			super.allocate (
				workingPortfolioAllocationControl,
				assetUniverseStatisticalProperties
			);

		if (null == floorPassHoldingsAllocation)
		{
			return null;
		}

		org.drip.portfolioconstruction.allocator.HoldingsAllocation firstPrunePassHoldingsAllocation =
			floorPassHoldingsAllocation;

		org.drip.portfolioconstruction.asset.Portfolio optimalPortfolio =
			firstPrunePassHoldingsAllocation.optimalPortfolio();

		while (0 != firstGreedyPruneList (
			optimalPortfolio,
			upperBoundHoldingsAllocationControl,
			pruneAssetIDSet
		))
		{
			workingPortfolioAllocationControl = workingPortfolioAllocationControl (
				assetIDArray,
				upperBoundHoldingsAllocationControl,
				pruneAssetIDSet
			);

			firstPrunePassHoldingsAllocation = super.allocate (
				workingPortfolioAllocationControl,
				assetUniverseStatisticalProperties
			);

			if (null == firstPrunePassHoldingsAllocation)
			{
				return null;
			}

			optimalPortfolio = firstPrunePassHoldingsAllocation.optimalPortfolio();
		}

		if (cardinalityUpperBound >= optimalPortfolio.cardinality())
		{
			org.drip.portfolioconstruction.cardinality.TadonkiVialHoldingsAllocation
				tadonkiVialHoldingsAllocation = 
					org.drip.portfolioconstruction.cardinality.TadonkiVialHoldingsAllocation.Standard (
						firstPrunePassHoldingsAllocation
					);

			return null == tadonkiVialHoldingsAllocation ||
				!tadonkiVialHoldingsAllocation.setFloorPassHoldingsAllocation (
					floorPassHoldingsAllocation
				) || !tadonkiVialHoldingsAllocation.setFirstPrunePassHoldingsAllocation (
					firstPrunePassHoldingsAllocation
				) ? null : tadonkiVialHoldingsAllocation;
		}

		secondGreedyPruneList (
			optimalPortfolio,
			upperBoundHoldingsAllocationControl,
			pruneAssetIDSet,
			optimalPortfolio.cardinality() - cardinalityUpperBound
		);

		workingPortfolioAllocationControl = workingPortfolioAllocationControl (
			assetIDArray,
			upperBoundHoldingsAllocationControl,
			pruneAssetIDSet
		);

		org.drip.portfolioconstruction.allocator.HoldingsAllocation secondPrunePassHoldingsAllocation =
			super.allocate (
				workingPortfolioAllocationControl,
				assetUniverseStatisticalProperties
			);

		if (null == secondPrunePassHoldingsAllocation)
		{
			return null;
		}

		org.drip.portfolioconstruction.cardinality.TadonkiVialHoldingsAllocation
			tadonkiVialHoldingsAllocation = 
				org.drip.portfolioconstruction.cardinality.TadonkiVialHoldingsAllocation.Standard (
					secondPrunePassHoldingsAllocation
				);

		return null == tadonkiVialHoldingsAllocation ||
			!tadonkiVialHoldingsAllocation.setFloorPassHoldingsAllocation (
				floorPassHoldingsAllocation
			) || !tadonkiVialHoldingsAllocation.setFirstPrunePassHoldingsAllocation (
				firstPrunePassHoldingsAllocation
			) || !tadonkiVialHoldingsAllocation.setSecondPrunePassHoldingsAllocation (
				secondPrunePassHoldingsAllocation
			) ? null : tadonkiVialHoldingsAllocation;
	}
}
