
package org.drip.xva.dynamics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>PathSimulator</i> drives the Simulation for various Latent States and Exposures. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Albanese, C., and L. Andersen (2014): Accounting for OTC Derivatives: Funding Adjustments and the
 *  			Re-Hypothecation Option https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2482955
 *  			<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2017): Derivatives Funding, Netting, and Accounting
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2534011 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  			<i>Risk</i> <b>21 (2)</b> 97-102
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/XVAAnalyticsLibrary.md">XVA Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/README.md">Valuation Adjustments that account for Collateral, CC Credit/Debt and Funding Overhead</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/dynamics/README.md">XVA Dynamics - Settings and Evolution</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PathSimulator
{
	private int _iCount = -1;
	private int _adjustmentDigestScheme = -1;
	private org.drip.exposure.universe.MarketVertexGenerator _marketVertexGenerator = null;
	private org.drip.exposure.holdings.PositionGroupContainer _positionGroupContainer = null;

	private double[][] positionGroupValueArray (
		final org.drip.exposure.universe.MarketPath marketPath)
	{
		org.drip.exposure.holdings.PositionGroup[] positionGroupArray =
			_positionGroupContainer.positionGroupArray();

		org.drip.analytics.date.JulianDate[] vertexDateArray = marketPath.anchorDates();

		int vertexCount = vertexDateArray.length;
		int positionGroupCount = positionGroupArray.length;
		double[][] positionGroupValueArray = new double[positionGroupCount][vertexCount];

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				int forwardDate = vertexDateArray[vertexIndex].julian();

				try {
					positionGroupValueArray[positionGroupIndex][vertexIndex] =
						null == positionGroupArray[positionGroupIndex].positionGroupEstimator() ? 1. :
						positionGroupArray[positionGroupIndex].positionGroupEstimator().variationMarginEstimate (
							forwardDate,
							marketPath
						);
				}
				catch (java.lang.Exception e)
				{
					e.printStackTrace();

					return null;
				}
			}
		}

		return positionGroupValueArray;
	}

	private boolean collateralGroupPathArray (
		final org.drip.exposure.universe.MarketPath marketPath)
	{
		org.drip.xva.dynamics.PositionGroupTrajectory collateralGroup = null;

		try
		{
			collateralGroup = new org.drip.xva.dynamics.PositionGroupTrajectory (
				_positionGroupContainer.positionGroupArray()[0].positionGroupSpecification().positionGroupSpecification(),
				marketPath,
				positionGroupValueArray (marketPath)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return false;
		}

		org.drip.xva.hypothecation.CollateralGroupVertex[][] collateralGroupVertexArray =
			collateralGroup.positionGroupVertexArray();

		if (null == collateralGroupVertexArray)
		{
			return false;
		}

		int positionGroupCount = collateralGroupVertexArray.length;

		try
		{
			for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
			{
				if (!_positionGroupContainer.setCollateralGroupPath (
					positionGroupIndex,
					new org.drip.xva.netting.CollateralGroupPath (
						collateralGroupVertexArray[positionGroupIndex],
						marketPath
					)))
					return false;
			}

			return true;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Generate a PathSimulator Instance with the corresponding Position Group Value
	 * 
	 * @param iPathCount Path Count
	 * @param marketVertexGenerator Market Vertex Generator
	 * @param positionGroupContainer Container of Position Groups
	 * 
	 * @return The PathSimulator Instance
	 */

	public static final PathSimulator UnitPositionGroupValue (
		final int iPathCount,
		final org.drip.exposure.universe.MarketVertexGenerator marketVertexGenerator,
		final org.drip.exposure.holdings.PositionGroupContainer positionGroupContainer)
	{
		try
		{
			return new PathSimulator (
				iPathCount,
				marketVertexGenerator,
				org.drip.xva.settings.AdjustmentDigestScheme.ALBANESE_ANDERSEN_METRICS_POINTER,
				positionGroupContainer
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * PathSimulator Constructor
	 * 
	 * @param iCount Path Count
	 * @param marketVertexGenerator Market Vertex Generator
	 * @param adjustmentDigestScheme Adjustment Digest Scheme
	 * @param positionGroupContainer Container of Position Groups
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PathSimulator (
		final int iCount,
		final org.drip.exposure.universe.MarketVertexGenerator marketVertexGenerator,
		final int adjustmentDigestScheme,
		final org.drip.exposure.holdings.PositionGroupContainer positionGroupContainer)
		throws java.lang.Exception
	{
		if (0 >= (_iCount = iCount) ||
			null == (_marketVertexGenerator = marketVertexGenerator) ||
			null == (_positionGroupContainer = positionGroupContainer))
		{
			throw new java.lang.Exception ("PathSimulator Constructor => Invalid Inputs");
		}

		_adjustmentDigestScheme = adjustmentDigestScheme;
	}

	/**
	 * Retrieve the Path Count
	 * 
	 * @return The Path Count
	 */

	public int count()
	{
		return _iCount;
	}

	/**
	 * Retrieve the Market Vertex Generator
	 * 
	 * @return The Market Vertex Generator
	 */

	public org.drip.exposure.universe.MarketVertexGenerator marketVertexGenerator()
	{
		return _marketVertexGenerator;
	}

	/**
	 * Retrieve the Adjustment Digest Scheme
	 * 
	 * @return The Adjustment Digest Scheme
	 */

	public int adjustmentDigestScheme()
	{
		return _adjustmentDigestScheme;
	}

	/**
	 * Retrieve the Position Group Container
	 * 
	 * @return Position Group Container
	 */

	public org.drip.exposure.holdings.PositionGroupContainer positionGroupContainer()
	{
		return _positionGroupContainer;
	}

	/**
	 * Generate a Single Trajectory from the Specified Initial Market Vertex and the Evolver Sequence
	 * 
	 * @param initialMarketVertex The Initial Market Vertex
	 * @param latentStateWeiner The Latent State Weiner Instance
	 * 
	 * @return Single Trajectory Path Exposure Adjustment
	 */

	public org.drip.xva.gross.PathExposureAdjustment singleTrajectory (
		final org.drip.exposure.universe.MarketVertex initialMarketVertex,
		final org.drip.exposure.universe.LatentStateWeiner latentStateWeiner)
	{
		try
		{
			org.drip.exposure.universe.MarketPath marketPath = new org.drip.exposure.universe.MarketPath (
				_marketVertexGenerator.marketVertex (
					initialMarketVertex,
					latentStateWeiner
				)
			);

			if (!collateralGroupPathArray (marketPath))
			{
				return null;
			}

			org.drip.xva.netting.CollateralGroupPath[][] positionFundingGroupPath =
				_positionGroupContainer.fundingSegmentPaths();

			org.drip.xva.netting.CollateralGroupPath[][] positionCreditDebtGroupPath =
				_positionGroupContainer.creditDebtSegmentPaths();

			int positionFundingGroupCount = positionFundingGroupPath.length;
			org.drip.xva.netting.FundingGroupPath[] fundingGroupPathArray = new
				org.drip.xva.strategy.AlbaneseAndersenFundingGroupPath[positionFundingGroupCount];

			if (org.drip.xva.settings.AdjustmentDigestScheme.ALBANESE_ANDERSEN_METRICS_POINTER ==
				_adjustmentDigestScheme)
			{
				int positionCreditDebtGroupCount = positionCreditDebtGroupPath.length;

				org.drip.xva.netting.CreditDebtGroupPath[] creditDebtGroupPathArray = new
					org.drip.xva.strategy.AlbaneseAndersenNettingGroupPath[positionCreditDebtGroupCount];

				for (int positionCreditDebtGroupIndex = 0; positionCreditDebtGroupIndex <
					positionCreditDebtGroupCount; ++positionCreditDebtGroupIndex)
				{
					creditDebtGroupPathArray[positionCreditDebtGroupIndex] =
						new org.drip.xva.strategy.AlbaneseAndersenNettingGroupPath (
							positionCreditDebtGroupPath[positionCreditDebtGroupIndex],
							marketPath
						);
				}

				for (int positionFundingGroupIndex = 0; positionFundingGroupIndex <
					positionFundingGroupCount; ++positionFundingGroupIndex)
				{
					fundingGroupPathArray[positionFundingGroupIndex] =
						new org.drip.xva.strategy.AlbaneseAndersenFundingGroupPath (
							creditDebtGroupPathArray,
							marketPath
						);
				}
			}

			return new org.drip.xva.gross.MonoPathExposureAdjustment (fundingGroupPathArray);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Simulate the Realized State/Entity Values and their Aggregates over the Paths
	 * 
	 * @param latentStateLabelList Latent State Label List
	 * @param initialMarketVertex The Initial Market Vertex
	 * @param correlatedPathVertexDimension Path Vertex Dimension Generator
	 * 
	 * @return The Exposure Adjustment Aggregator - Simulation Result
	 */

	public org.drip.xva.gross.ExposureAdjustmentAggregator simulate (
		final java.util.List<org.drip.state.identifier.LatentStateLabel> latentStateLabelList,
		final org.drip.exposure.universe.MarketVertex initialMarketVertex,
		final org.drip.measure.discrete.CorrelatedPathVertexDimension correlatedPathVertexDimension)
	{
		if (null == correlatedPathVertexDimension)
		{
			return null;
		}

		org.drip.xva.gross.PathExposureAdjustment[] pathExposureAdjustmentArray = new
			org.drip.xva.gross.PathExposureAdjustment[_iCount];

		for (int pathIndex = 0; pathIndex < _iCount; ++pathIndex)
		{
			if (null == (pathExposureAdjustmentArray[pathIndex] = singleTrajectory (
				initialMarketVertex,
				org.drip.exposure.universe.LatentStateWeiner.FromUnitRandom (
					latentStateLabelList,
					org.drip.numerical.linearalgebra.Matrix.Transpose
						(org.drip.numerical.linearalgebra.Matrix.Transpose
						(correlatedPathVertexDimension.straightPathVertexRd().flatform()))))))
			{
				return null;
			}
		}

		try
		{
			return new org.drip.xva.gross.ExposureAdjustmentAggregator (pathExposureAdjustmentArray);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
