
package org.drip.xva.dynamics;

import org.drip.analytics.date.JulianDate;
import org.drip.exposure.mpor.CollateralAmountEstimator;
import org.drip.exposure.universe.MarketEdge;
import org.drip.exposure.universe.MarketPath;
import org.drip.exposure.universe.MarketVertex;
import org.drip.measure.bridge.BrokenDateInterpolator;
import org.drip.measure.bridge.BrokenDateInterpolatorBrownian3P;
import org.drip.measure.bridge.BrokenDateInterpolatorLinearT;
import org.drip.measure.bridge.BrokenDateInterpolatorSqrtT;
import org.drip.numerical.common.NumberUtil;
import org.drip.xva.definition.CloseOut;
import org.drip.xva.definition.CloseOutBilateral;
import org.drip.xva.hypothecation.CollateralGroupVertex;
import org.drip.xva.proto.PositionGroupSpecification;
import org.drip.xva.settings.BrokenDateScheme;
import org.drip.xva.settings.CloseOutScheme;
import org.drip.xva.settings.PositionReplicationScheme;
import org.drip.xva.vertex.AlbaneseAndersen;
import org.drip.xva.vertex.BurgardKjaerBuilder;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * <i>PositionGroupTrajectory</i> generates the Customized Position Group Trajectories. The References are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Albanese, C., L. Andersen, and, S. Iabichino (2015): The FVA Puzzle: Accounting, Risk Management,
 *  			and Collateral Trading https://papers.ssrn.com/sol3/paper.cfm?abstract_id_2517301
 *  			<b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b>
 *  			82-87
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
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

public class PositionGroupTrajectory
{
	private MarketPath _marketPath = null;
	private double[][] _positionGroupArrayVertex = null;
	private PositionGroupSpecification _positionGroupSpecification = null;

	private BrokenDateInterpolator brokenDateInterpolator (
		final int positionGroupIndex,
		final int vertexIndex)
	{
		int brokenDateScheme = _positionGroupSpecification.brokenDateScheme();

		JulianDate[] vertexDateArray = _marketPath.anchorDates();

		try {
			if (BrokenDateScheme.LINEAR_TIME == brokenDateScheme) {
				return 0 == vertexIndex ? null : new BrokenDateInterpolatorLinearT (
					vertexDateArray[vertexIndex - 1].julian(),
					vertexDateArray[vertexIndex].julian(),
					_positionGroupArrayVertex[positionGroupIndex][vertexIndex - 1],
					_positionGroupArrayVertex[positionGroupIndex][vertexIndex]
				);
			}

			if (BrokenDateScheme.SQUARE_ROOT_OF_TIME == brokenDateScheme) {
				return 0 == vertexIndex ? null : new BrokenDateInterpolatorSqrtT (
					vertexDateArray[vertexIndex - 1].julian(),
					vertexDateArray[vertexIndex].julian(),
					_positionGroupArrayVertex[positionGroupIndex][vertexIndex - 1],
					_positionGroupArrayVertex[positionGroupIndex][vertexIndex]
				);
			}

			if (BrokenDateScheme.THREE_POINT_BROWNIAN_BRIDGE == brokenDateScheme) {
				return 0 == vertexIndex || 1 == vertexIndex ? null : new BrokenDateInterpolatorBrownian3P (
					vertexDateArray[vertexIndex - 2].julian(),
					vertexDateArray[vertexIndex - 1].julian(),
					vertexDateArray[vertexIndex].julian(),
					_positionGroupArrayVertex[positionGroupIndex][vertexIndex - 2],
					_positionGroupArrayVertex[positionGroupIndex][vertexIndex - 1],
					_positionGroupArrayVertex[positionGroupIndex][vertexIndex]
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private double collateralBalance (
		final int positionGroupIndex,
		final int vertexIndex)
		throws Exception
	{
		BrokenDateInterpolator brokenDateInterpolator = brokenDateInterpolator (
			positionGroupIndex,
			vertexIndex
		);

		return null == brokenDateInterpolator ? 0. : new CollateralAmountEstimator (
			_positionGroupSpecification,
			brokenDateInterpolator,
			Double.NaN
		).postingRequirement (
			_marketPath.anchorDates()[vertexIndex]
		);
	}

	private double[][] positionGroupCollateralBalanceArray()
	{
		int vertexCount = _marketPath.anchorDates().length;

		int positionGroupCount = _positionGroupArrayVertex.length;
		double[][] collateralBalanceArray = new double[positionGroupCount][vertexCount];

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex) {
			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				try {
					collateralBalanceArray[positionGroupIndex][vertexIndex] = collateralBalance (
						positionGroupIndex,
						vertexIndex
					);
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		return collateralBalanceArray;
	}

	private CollateralGroupVertex positionGroupVertex (
		final JulianDate anchorDate,
		final double positionGroupValue,
		final double realizedCashFlow,
		final double collateralBalance,
		final MarketVertex marketVertexLeft,
		final MarketVertex marketVertexRight)
	{
		int closeOutScheme = _positionGroupSpecification.closeOutScheme();

		int positionReplicationScheme = _positionGroupSpecification.positionReplicationScheme();

		CloseOut closeOut = CloseOutScheme.ISDA_92 == closeOutScheme ? null :
			CloseOutBilateral.Market (marketVertexRight);

		try {
			if (PositionReplicationScheme.ALBANESE_ANDERSEN_VERTEX == positionReplicationScheme) {
				return new AlbaneseAndersen (
					anchorDate,
					positionGroupValue,
					realizedCashFlow,
					collateralBalance
				);
			}

			if (PositionReplicationScheme.BURGARD_KJAER_HEDGE_ERROR_DUAL_BOND_VERTEX ==
				positionReplicationScheme) {
				return null != marketVertexLeft ? BurgardKjaerBuilder.HedgeErrorDualBond (
					anchorDate,
					positionGroupValue,
					realizedCashFlow,
					collateralBalance,
					_positionGroupSpecification.hedgeError(),
					new MarketEdge (marketVertexLeft, marketVertexRight),
					closeOut
				) : BurgardKjaerBuilder.Initial (
					anchorDate,
					positionGroupValue,
					marketVertexRight,
					closeOut
				);
			}

			if (PositionReplicationScheme.BURGARD_KJAER_SEMI_REPLICATION_DUAL_BOND_VERTEX ==
				positionReplicationScheme) {
				return null != marketVertexLeft ? BurgardKjaerBuilder.SemiReplicationDualBond (
					anchorDate,
					positionGroupValue,
					realizedCashFlow,
					collateralBalance,
					new MarketEdge (marketVertexLeft, marketVertexRight),
					closeOut
				) : BurgardKjaerBuilder.Initial (
					anchorDate,
					positionGroupValue,
					marketVertexRight,
					closeOut
				);
			}

			if (PositionReplicationScheme.BURGARD_KJAER_GOLD_PLATED_TWO_WAY_CSA_VERTEX ==
				positionReplicationScheme) {
				return null != marketVertexLeft ? BurgardKjaerBuilder.GoldPlatedTwoWayCSA (
					anchorDate,
					positionGroupValue,
					realizedCashFlow,
					new MarketEdge (marketVertexLeft, marketVertexRight),
					closeOut
				) : BurgardKjaerBuilder.Initial (
					anchorDate,
					positionGroupValue,
					marketVertexRight,
					closeOut
				);
			}

			if (PositionReplicationScheme.BURGARD_KJAER_ONE_WAY_CSA_VERTEX == positionReplicationScheme) {
				return null != marketVertexLeft ? BurgardKjaerBuilder.OneWayCSA (
					anchorDate,
					positionGroupValue,
					realizedCashFlow,
					new MarketEdge (marketVertexLeft, marketVertexRight),
					closeOut
				) : BurgardKjaerBuilder.Initial (
					anchorDate,
					positionGroupValue,
					marketVertexRight,
					closeOut
				);
			}

			if (PositionReplicationScheme.BURGARD_KJAER_SET_OFF_VERTEX == positionReplicationScheme) {
				return null != marketVertexLeft ? BurgardKjaerBuilder.SetOff (
					anchorDate,
					positionGroupValue,
					realizedCashFlow,
					collateralBalance,
					new MarketEdge (marketVertexLeft, marketVertexRight)
				) : BurgardKjaerBuilder.Initial (
					anchorDate,
					positionGroupValue,
					marketVertexRight,
					closeOut
				);
			}
		} catch (Exception e) {
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
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public PositionGroupTrajectory (
		final PositionGroupSpecification positionGroupSpecification,
		final MarketPath marketPath,
		final double[][] positionGroupArrayVertex)
		throws Exception
	{
		if (null == (_positionGroupSpecification = positionGroupSpecification) ||
			null == (_marketPath = marketPath) ||
			null == (_positionGroupArrayVertex = positionGroupArrayVertex)) {
			throw new Exception ("PositionGroupTrajectory Constructor => Invalid Inputs");
		}

		int positionGroupCount = _positionGroupArrayVertex.length;

		if (0 == positionGroupCount) {
			throw new Exception ("PositionGroupTrajectory Constructor => Invalid Inputs");
		}

		int vertexCount = _marketPath.anchorDates().length;

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex) {
			if (null == _positionGroupArrayVertex[positionGroupIndex] ||
				vertexCount != _positionGroupArrayVertex[positionGroupIndex].length ||
				!NumberUtil.IsValid (_positionGroupArrayVertex[positionGroupIndex])) {
				throw new Exception ("PositionGroupTrajectory Constructor => Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Position Group Specification
	 * 
	 * @return The Position Group Specification
	 */

	public PositionGroupSpecification positionGroupSpecification()
	{
		return _positionGroupSpecification;
	}

	/**
	 * Retrieve the Market Path
	 * 
	 * @return The Market Path
	 */

	public MarketPath marketPath()
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

	public CollateralGroupVertex[][] positionGroupVertexArray()
	{
		double[][] collateralBalanceArray = positionGroupCollateralBalanceArray();

		if (null == collateralBalanceArray) {
			return null;
		}

		JulianDate[] vertexDateArray = _marketPath.anchorDates();

		int vertexCount = vertexDateArray.length;
		int positionGroupCount = _positionGroupArrayVertex.length;
		CollateralGroupVertex[][] positionGroupVertexArray = new
			CollateralGroupVertex[positionGroupCount][vertexCount];

		for (int positionGroupIndex = 0; positionGroupIndex < positionGroupCount; ++positionGroupIndex) {
			for (int vertexIndex = 0; vertexIndex < vertexCount; ++vertexIndex) {
				JulianDate vertexDate = vertexDateArray[vertexIndex];

				try {
					positionGroupVertexArray[positionGroupIndex][vertexIndex] = positionGroupVertex (
						vertexDateArray[vertexIndex],
						_positionGroupArrayVertex[positionGroupIndex][vertexIndex],
						0.,
						collateralBalanceArray[positionGroupIndex][vertexIndex],
						0 == vertexIndex ? null :
							_marketPath.marketVertex (vertexDateArray[vertexIndex - 1].julian()),
							_marketPath.marketVertex (vertexDate.julian())
					);
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		return positionGroupVertexArray;
	}
}
