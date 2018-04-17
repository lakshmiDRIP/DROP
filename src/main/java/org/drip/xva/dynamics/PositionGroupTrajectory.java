
package org.drip.xva.dynamics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * PositionGroupTrajectory generates the Customized Position Group Trajectories. The References are:<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b>
 *  	82-87.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75.<br><br>
 *  
 *  - Albanese, C., L. Andersen, and, S. Iabichino (2015): The FVA Puzzle: Accounting, Risk Management, and
 *  	Collateral Trading <b>https://papers.ssrn.com/sol3/paper.cfm?abstract_id_2517301</b><br><br>
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  	<i>Risk</i> <b>21 (2)</b> 97-102.<br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PositionGroupTrajectory
{
	private double[][] _positionGroupArrayVertex = null;
	private org.drip.xva.universe.MarketPath _marketPath = null;
	private org.drip.xva.proto.PositionGroupSpecification _positionGroupSpecification = null;

	private org.drip.measure.bridge.BrokenDateInterpolator brokenDateInterpolator (
		final int positionGroupIndex,
		final int vertexIndex)
	{
		int brokenDateScheme = _positionGroupSpecification.brokenDateScheme();

		org.drip.analytics.date.JulianDate[] vertexDateArray = _marketPath.anchorDates();

		try
		{
			if (org.drip.xva.settings.BrokenDateScheme.LINEAR_TIME == brokenDateScheme)
			{
				return 0 == vertexIndex ? null : new org.drip.measure.bridge.BrokenDateInterpolatorLinearT (
					vertexDateArray[vertexIndex - 1].julian(),
					vertexDateArray[vertexIndex].julian(),
					_positionGroupArrayVertex[positionGroupIndex][vertexIndex - 1],
					_positionGroupArrayVertex[positionGroupIndex][vertexIndex]
				);
			}

			if (org.drip.xva.settings.BrokenDateScheme.SQUARE_ROOT_OF_TIME == brokenDateScheme)
			{
				return 0 == vertexIndex ? null : new org.drip.measure.bridge.BrokenDateInterpolatorSqrtT (
					vertexDateArray[vertexIndex - 1].julian(),
					vertexDateArray[vertexIndex].julian(),
					_positionGroupArrayVertex[positionGroupIndex][vertexIndex - 1],
					_positionGroupArrayVertex[positionGroupIndex][vertexIndex]
				);
			}

			if (org.drip.xva.settings.BrokenDateScheme.THREE_POINT_BROWNIAN_BRIDGE == brokenDateScheme)
			{
				return 0 == vertexIndex || 1 == vertexIndex ? null : new
					org.drip.measure.bridge.BrokenDateInterpolatorBrownian3P (
						vertexDateArray[vertexIndex - 2].julian(),
						vertexDateArray[vertexIndex - 1].julian(),
						vertexDateArray[vertexIndex].julian(),
						_positionGroupArrayVertex[positionGroupIndex][vertexIndex - 2],
						_positionGroupArrayVertex[positionGroupIndex][vertexIndex - 1],
						_positionGroupArrayVertex[positionGroupIndex][vertexIndex]
					);
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private double collateralBalance (
		final int positionGroupIndex,
		final int vertexIndex)
		throws java.lang.Exception
	{
		org.drip.measure.bridge.BrokenDateInterpolator brokenDateInterpolator = brokenDateInterpolator (
			positionGroupIndex,
			vertexIndex
		);

		return null == brokenDateInterpolator ? 0. : new org.drip.xva.margin.CollateralAmountEstimator (
			_positionGroupSpecification,
			brokenDateInterpolator,
			java.lang.Double.NaN
		).postingRequirement (_marketPath.anchorDates()[vertexIndex]);
	}

	private double[][] positionGroupCollateralBalanceArray()
	{
		int vertexCount = _marketPath.anchorDates().length;

		int positionGroupCount = _positionGroupArrayVertex.length;
		double[][] collateralBalanceArray = new double[positionGroupCount][vertexCount];

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				try
				{
					collateralBalanceArray[positionGroupIndex][vertexIndex] = collateralBalance (
						positionGroupIndex,
						vertexIndex
					);
				}
				catch (java.lang.Exception e)
				{
					e.printStackTrace();

					return null;
				}
			}
		}

		return collateralBalanceArray;
	}

	private org.drip.xva.hypothecation.CollateralGroupVertex positionGroupVertex (
		final org.drip.analytics.date.JulianDate anchorDate,
		final double positionGroupValue,
		final double realizedCashFlow,
		final double collateralBalance,
		final org.drip.xva.universe.MarketVertex marketVertexLeft,
		final org.drip.xva.universe.MarketVertex marketVertexRight)
	{
		int closeOutScheme = _positionGroupSpecification.closeOutScheme();

		int positionReplicationScheme = _positionGroupSpecification.positionReplicationScheme();

		org.drip.xva.definition.CloseOut closeOut =
			org.drip.xva.settings.CloseOutScheme.ISDA_92 == closeOutScheme ? null :
				org.drip.xva.definition.CloseOutBilateral.Market (marketVertexRight);

		try
		{
			if (org.drip.xva.settings.PositionReplicationScheme.ALBANESE_ANDERSEN_VERTEX ==
				positionReplicationScheme)
			{
				return new org.drip.xva.vertex.AlbaneseAndersen (
					anchorDate,
					positionGroupValue,
					realizedCashFlow,
					collateralBalance
				);
			}

			if (org.drip.xva.settings.PositionReplicationScheme.BURGARD_KJAER_HEDGE_ERROR_DUAL_BOND_VERTEX ==
				positionReplicationScheme)
			{
				return null != marketVertexLeft ?
					org.drip.xva.vertex.BurgardKjaerBuilder.HedgeErrorDualBond (
						anchorDate,
						positionGroupValue,
						realizedCashFlow,
						collateralBalance,
						_positionGroupSpecification.hedgeError(),
						new org.drip.xva.universe.MarketEdge (
							marketVertexLeft,
							marketVertexRight
						),
						closeOut
					) : org.drip.xva.vertex.BurgardKjaerBuilder.Initial (
						anchorDate,
						positionGroupValue,
						marketVertexRight,
						closeOut
					);
			}

			if (org.drip.xva.settings.PositionReplicationScheme.BURGARD_KJAER_SEMI_REPLICATION_DUAL_BOND_VERTEX
				== positionReplicationScheme)
			{
				return null != marketVertexLeft ?
					org.drip.xva.vertex.BurgardKjaerBuilder.SemiReplicationDualBond (
						anchorDate,
						positionGroupValue,
						realizedCashFlow,
						collateralBalance,
						new org.drip.xva.universe.MarketEdge (
							marketVertexLeft,
							marketVertexRight
						),
						closeOut
					) : org.drip.xva.vertex.BurgardKjaerBuilder.Initial (
						anchorDate,
						positionGroupValue,
						marketVertexRight,
						closeOut
					);
			}

			if (org.drip.xva.settings.PositionReplicationScheme.BURGARD_KJAER_GOLD_PLATED_TWO_WAY_CSA_VERTEX
				== positionReplicationScheme)
			{
				return null != marketVertexLeft ?
					org.drip.xva.vertex.BurgardKjaerBuilder.GoldPlatedTwoWayCSA (
						anchorDate,
						positionGroupValue,
						realizedCashFlow,
						new org.drip.xva.universe.MarketEdge (
							marketVertexLeft,
							marketVertexRight
						),
						closeOut
					) : org.drip.xva.vertex.BurgardKjaerBuilder.Initial (
						anchorDate,
						positionGroupValue,
						marketVertexRight,
						closeOut
					);
			}

			if (org.drip.xva.settings.PositionReplicationScheme.BURGARD_KJAER_ONE_WAY_CSA_VERTEX ==
				positionReplicationScheme)
			{
				return null != marketVertexLeft ?
					org.drip.xva.vertex.BurgardKjaerBuilder.OneWayCSA (
						anchorDate,
						positionGroupValue,
						realizedCashFlow,
						new org.drip.xva.universe.MarketEdge (
							marketVertexLeft,
							marketVertexRight
						),
						closeOut
					) : org.drip.xva.vertex.BurgardKjaerBuilder.Initial (
						anchorDate,
						positionGroupValue,
						marketVertexRight,
						closeOut
					);
			}

			if (org.drip.xva.settings.PositionReplicationScheme.BURGARD_KJAER_SET_OFF_VERTEX ==
				positionReplicationScheme)
			{
				return null != marketVertexLeft ?
					org.drip.xva.vertex.BurgardKjaerBuilder.SetOff (
						anchorDate,
						positionGroupValue,
						realizedCashFlow,
						collateralBalance,
						new org.drip.xva.universe.MarketEdge (
							marketVertexLeft,
							marketVertexRight
						)
					) : org.drip.xva.vertex.BurgardKjaerBuilder.Initial (
						anchorDate,
						positionGroupValue,
						marketVertexRight,
						closeOut
					);
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * PositionGroupTrajectory Constructor
	 * 
	 * @param positionGroupSpecification The Position Group Specification
	 * @param marketPath The Market Path
	 * @param positionGroupArrayVertex Vertexes of the Position Group Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PositionGroupTrajectory (
		final org.drip.xva.proto.PositionGroupSpecification positionGroupSpecification,
		final org.drip.xva.universe.MarketPath marketPath,
		final double[][] positionGroupArrayVertex)
		throws java.lang.Exception
	{
		if (null == (_positionGroupSpecification = positionGroupSpecification) ||
			null == (_marketPath = marketPath) ||
			null == (_positionGroupArrayVertex = positionGroupArrayVertex))
		{
			throw new java.lang.Exception ("PositionGroupTrajectory Constructor => Invalid Inputs");
		}

		int positionGroupCount = _positionGroupArrayVertex.length;

		if (0 == positionGroupCount)
		{
			throw new java.lang.Exception ("PositionGroupTrajectory Constructor => Invalid Inputs");
		}

		int vertexCount = _marketPath.anchorDates().length;

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			if (null == _positionGroupArrayVertex[positionGroupIndex] ||
				vertexCount != _positionGroupArrayVertex[positionGroupIndex].length ||
				!org.drip.quant.common.NumberUtil.IsValid (_positionGroupArrayVertex[positionGroupIndex]))
			{
				throw new java.lang.Exception ("PositionGroupTrajectory Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Position Group Specification
	 * 
	 * @return The Position Group Specification
	 */

	public org.drip.xva.proto.PositionGroupSpecification positionGroupSpecification()
	{
		return _positionGroupSpecification;
	}

	/**
	 * Retrieve the Market Path
	 * 
	 * @return The Market Path
	 */

	public org.drip.xva.universe.MarketPath marketPath()
	{
		return _marketPath;
	}

	/**
	 * Retrieve the Position Group Array Vertex Value
	 * 
	 * @return The Position Group Array Vertex Value
	 */

	public double[][] positionGroupArrayVertex()
	{
		return _positionGroupArrayVertex;
	}

	/**
	 * Generate the Position Collateral Group Vertex Array
	 * 
	 * @return The Position Collateral Group Vertex Array
	 */

	public org.drip.xva.hypothecation.CollateralGroupVertex[][] positionGroupVertexArray()
	{
		double[][] collateralBalanceArray = positionGroupCollateralBalanceArray();

		if (null == collateralBalanceArray)
		{
			return null;
		}

		org.drip.analytics.date.JulianDate[] vertexDateArray = _marketPath.anchorDates();

		int vertexCount = vertexDateArray.length;
		int positionGroupCount = _positionGroupArrayVertex.length;
		org.drip.xva.hypothecation.CollateralGroupVertex[][] positionGroupVertexArray = new
			org.drip.xva.hypothecation.CollateralGroupVertex[positionGroupCount][vertexCount];

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				org.drip.analytics.date.JulianDate vertexDate = vertexDateArray[vertexIndex];

				try
				{
					positionGroupVertexArray[positionGroupIndex][vertexIndex] = positionGroupVertex (
						vertexDateArray[vertexIndex],
						_positionGroupArrayVertex[positionGroupIndex][vertexIndex],
						0.,
						collateralBalanceArray[positionGroupIndex][vertexIndex],
						0 == vertexIndex ? null :
							_marketPath.marketVertex (vertexDateArray[vertexIndex - 1].julian()),
							_marketPath.marketVertex (vertexDate.julian())
					);
				}
				catch (java.lang.Exception e)
				{
					e.printStackTrace();

					return null;
				}
			}
		}

		return positionGroupVertexArray;
	}
}
