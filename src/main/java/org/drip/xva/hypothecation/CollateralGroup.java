
package org.drip.xva.hypothecation;

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
 * CollateralGroup generates the Customized Collateral Group Trajectories. The References are:<br><br>
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

public class CollateralGroup
{
	private double[][] _positionGroupArrayVertex = null;
	private org.drip.xva.universe.MarketVertex[] _marketVertexArray = null;
	private org.drip.xva.proto.CollateralGroupSpecification _collateralGroupSpecification = null;

	private org.drip.measure.bridge.BrokenDateInterpolator brokenDateInterpolator (
		final int positionGroupIndex,
		final int vertexIndex)
	{
		int brokenDateScheme = _collateralGroupSpecification.brokenDateScheme();

		try
		{
			if (org.drip.xva.settings.BrokenDateScheme.LINEAR_TIME == brokenDateScheme)
			{
				return 0 == vertexIndex ? null : new org.drip.measure.bridge.BrokenDateInterpolatorLinearT (
					_marketVertexArray[vertexIndex - 1].anchorDate().julian(),
					_marketVertexArray[vertexIndex].anchorDate().julian(),
					_positionGroupArrayVertex[positionGroupIndex][vertexIndex - 1],
					_positionGroupArrayVertex[positionGroupIndex][vertexIndex]
				);
			}

			if (org.drip.xva.settings.BrokenDateScheme.SQUARE_ROOT_OF_TIME == brokenDateScheme)
			{
				return 0 == vertexIndex ? null : new org.drip.measure.bridge.BrokenDateInterpolatorSqrtT (
					_marketVertexArray[vertexIndex - 1].anchorDate().julian(),
					_marketVertexArray[vertexIndex].anchorDate().julian(),
					_positionGroupArrayVertex[positionGroupIndex][vertexIndex - 1],
					_positionGroupArrayVertex[positionGroupIndex][vertexIndex]
				);
			}

			if (org.drip.xva.settings.BrokenDateScheme.THREE_POINT_BROWNIAN_BRIDGE == brokenDateScheme)
			{
				return 0 == vertexIndex || 1 == vertexIndex ? null : new
					org.drip.measure.bridge.BrokenDateInterpolatorBrownian3P (
						_marketVertexArray[vertexIndex - 2].anchorDate().julian(),
						_marketVertexArray[vertexIndex - 1].anchorDate().julian(),
						_marketVertexArray[vertexIndex].anchorDate().julian(),
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

		return null == brokenDateInterpolator ? 0. : new org.drip.xva.hypothecation.CollateralAmountEstimator (
			_collateralGroupSpecification,
			brokenDateInterpolator,
			java.lang.Double.NaN
		).postingRequirement (_marketVertexArray[vertexIndex].anchorDate());
	}

	private double[][] positionGroupCollateralBalanceArray()
	{
		int vertexCount = _marketVertexArray.length;
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

	private org.drip.xva.hypothecation.CollateralGroupVertex collateralGroupVertex (
		final org.drip.analytics.date.JulianDate anchorDate,
		final double positionGroupValue,
		final double realizedCashFlow,
		final double collateralBalance,
		final org.drip.xva.universe.MarketVertex marketVertexLeft,
		final org.drip.xva.universe.MarketVertex marketVertexRight)
	{
		int closeOutScheme = _collateralGroupSpecification.closeOutScheme();

		int positionReplicationScheme = _collateralGroupSpecification.positionReplicationScheme();

		org.drip.xva.definition.CloseOut closeOut =
			org.drip.xva.settings.CloseOutScheme.ISDA_92 == closeOutScheme ? null :
				org.drip.xva.definition.CloseOutBilateral.Market (marketVertexRight);

		try
		{
			if (org.drip.xva.settings.PositionReplicationScheme.ALBANESE_ANDERSEN_VERTEX ==
				positionReplicationScheme)
			{
				return new org.drip.xva.hypothecation.AlbaneseAndersenVertex (
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
					org.drip.xva.hypothecation.BurgardKjaerVertexBuilder.HedgeErrorDualBond (
						anchorDate,
						positionGroupValue,
						realizedCashFlow,
						collateralBalance,
						_collateralGroupSpecification.hedgeError(),
						new org.drip.xva.universe.MarketEdge (
							marketVertexLeft,
							marketVertexRight
						),
						closeOut
					) : org.drip.xva.hypothecation.BurgardKjaerVertexBuilder.Initial (
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
					org.drip.xva.hypothecation.BurgardKjaerVertexBuilder.SemiReplicationDualBond (
						anchorDate,
						positionGroupValue,
						realizedCashFlow,
						collateralBalance,
						new org.drip.xva.universe.MarketEdge (
							marketVertexLeft,
							marketVertexRight
						),
						closeOut
					) : org.drip.xva.hypothecation.BurgardKjaerVertexBuilder.Initial (
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
					org.drip.xva.hypothecation.BurgardKjaerVertexBuilder.GoldPlatedTwoWayCSA (
						anchorDate,
						positionGroupValue,
						realizedCashFlow,
						new org.drip.xva.universe.MarketEdge (
							marketVertexLeft,
							marketVertexRight
						),
						closeOut
					) : org.drip.xva.hypothecation.BurgardKjaerVertexBuilder.Initial (
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
					org.drip.xva.hypothecation.BurgardKjaerVertexBuilder.OneWayCSA (
						anchorDate,
						positionGroupValue,
						realizedCashFlow,
						new org.drip.xva.universe.MarketEdge (
							marketVertexLeft,
							marketVertexRight
						),
						closeOut
					) : org.drip.xva.hypothecation.BurgardKjaerVertexBuilder.Initial (
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
					org.drip.xva.hypothecation.BurgardKjaerVertexBuilder.SetOff (
						anchorDate,
						positionGroupValue,
						realizedCashFlow,
						collateralBalance,
						new org.drip.xva.universe.MarketEdge (
							marketVertexLeft,
							marketVertexRight
						)
					) : org.drip.xva.hypothecation.BurgardKjaerVertexBuilder.Initial (
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
	 * CollateralGroup Constructor
	 * 
	 * @param collateralGroupSpecification The Collateral Group Specification
	 * @param marketVertexArray Array of Market Vertexes
	 * @param positionGroupArrayVertex Vertexes of the Position Group Array
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CollateralGroup (
		final org.drip.xva.proto.CollateralGroupSpecification collateralGroupSpecification,
		final org.drip.xva.universe.MarketVertex[] marketVertexArray,
		final double[][] positionGroupArrayVertex)
		throws java.lang.Exception
	{
		if (null == (_collateralGroupSpecification = collateralGroupSpecification) ||
			null == (_marketVertexArray = marketVertexArray) ||
			null == (_positionGroupArrayVertex = positionGroupArrayVertex))
		{
			throw new java.lang.Exception ("CollateralGroup Constructor => Invalid Inputs");
		}

		int vertexCount = _marketVertexArray.length;
		int positionGroupCount = _positionGroupArrayVertex.length;

		if (0 == vertexCount ||
			0 == positionGroupCount)
		{
			throw new java.lang.Exception ("CollateralGroup Constructor => Invalid Inputs");
		}

		for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
		{
			if (null == _marketVertexArray[vertexIndex])
			{
				throw new java.lang.Exception ("CollateralGroup Constructor => Invalid Inputs");
			}
		}

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			if (null == _positionGroupArrayVertex[positionGroupIndex] ||
				vertexCount != _positionGroupArrayVertex[positionGroupIndex].length ||
				!org.drip.quant.common.NumberUtil.IsValid (_positionGroupArrayVertex[positionGroupIndex]))
			{
				throw new java.lang.Exception ("CollateralGroup Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Collateral Group Specification
	 * 
	 * @return The Collateral Group Specification
	 */

	public org.drip.xva.proto.CollateralGroupSpecification collateralGroupSpecification()
	{
		return _collateralGroupSpecification;
	}

	/**
	 * Retrieve the Market Vertex Array
	 * 
	 * @return The Market Vertex Array
	 */

	public org.drip.xva.universe.MarketVertex[] marketVertexArray()
	{
		return _marketVertexArray;
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

		int vertexCount = _marketVertexArray.length;
		int positionGroupCount = _positionGroupArrayVertex.length;
		org.drip.xva.hypothecation.CollateralGroupVertex[][] collateralGroupVertexArray = new
			org.drip.xva.hypothecation.CollateralGroupVertex[positionGroupCount][vertexCount];

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex)
		{
			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex)
			{
				try
				{
					collateralGroupVertexArray[positionGroupIndex][vertexIndex] = collateralGroupVertex (
						_marketVertexArray[vertexIndex].anchorDate(),
						_positionGroupArrayVertex[positionGroupIndex][vertexIndex],
						0.,
						collateralBalanceArray[positionGroupIndex][vertexIndex],
						0 == vertexIndex ? null : _marketVertexArray[vertexIndex - 1],
						_marketVertexArray[vertexIndex]
					);
				}
				catch (java.lang.Exception e)
				{
					e.printStackTrace();

					return null;
				}
			}
		}

		return collateralGroupVertexArray;
	}
}
