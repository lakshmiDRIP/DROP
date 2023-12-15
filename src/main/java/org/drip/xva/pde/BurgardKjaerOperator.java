
package org.drip.xva.pde;

import org.drip.exposure.evolver.PrimarySecurityDynamicsContainer;
import org.drip.exposure.universe.MarketEdge;
import org.drip.exposure.universe.MarketVertex;
import org.drip.exposure.universe.MarketVertexEntity;
import org.drip.numerical.common.NumberUtil;
import org.drip.xva.definition.PDEEvolutionControl;
import org.drip.xva.derivative.EvolutionTrajectoryVertex;
import org.drip.xva.derivative.PositionGreekVertex;

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
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>BurgardKjaerOperator</i> sets up the Parabolic Differential Equation PDE based on the Ito Evolution
 * Differential for the Reference Underlier Asset, as laid out in Burgard and Kjaer (2014). The References
 * are:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party
 *  			Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  	</li>
 *  	<li>
 *  		Cesari, G., J. Aquilina, N. Charpillon, X. Filipovic, G. Lee, and L. Manda (2009): <i>Modeling,
 *  			Pricing, and Hedging Counter-party Credit Exposure - A Technical Guide</i> <b>Springer
 *  			Finance</b> New York
 *  	</li>
 *  	<li>
 *  		Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  			86-90
 *  	</li>
 *  	<li>
 *  		Li, B., and Y. Tang (2007): <i>Quantitative Analysis, Derivatives Modeling, and Trading
 *  			Strategies in the Presence of Counter-party Credit Risk for the Fixed Income Market</i>
 *  			<b>World Scientific Publishing</b> Singapore
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/pde/README.md">Burgard Kjaer PDE Evolution Scheme</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BurgardKjaerOperator
{
	private PDEEvolutionControl _pdeEvolutionControl = null;
	private PrimarySecurityDynamicsContainer _tradeablesContainer = null;

	/**
	 * BurgardKjaerOperator Constructor
	 * 
	 * @param tradeablesContainer The Universe of Tradeable Assets
	 * @param pdeEvolutionControl The XVA Control Settings
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BurgardKjaerOperator (
		final PrimarySecurityDynamicsContainer tradeablesContainer,
		final PDEEvolutionControl pdeEvolutionControl)
		throws java.lang.Exception
	{
		if (null == (_tradeablesContainer = tradeablesContainer) ||
			null == (_pdeEvolutionControl = pdeEvolutionControl)) {
			throw new Exception ("BurgardKjaerOperator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Universe of Trade-able Assets
	 * 
	 * @return The Universe of Trade-able Assets
	 */

	public PrimarySecurityDynamicsContainer tradeablesContainer()
	{
		return _tradeablesContainer;
	}

	/**
	 * Retrieve the PDE Evolution Control Settings
	 * 
	 * @return The PDE Evolution Control Settings
	 */

	public PDEEvolutionControl pdeEvolutionControl()
	{
		return _pdeEvolutionControl;
	}

	/**
	 * Generate the Derivative Value Time Increment using the Burgard Kjaer Scheme
	 * 
	 * @param marketEdge The Market Edge
	 * @param initialTrajectoryVertex The Evolution Trajectory Vertex
	 * @param collateral The Off-setting Collateral
	 * 
	 * @return The Time Increment using the Burgard Kjaer Scheme
	 */

	public BurgardKjaerEdgeRun edgeRun (
		final MarketEdge marketEdge,
		final EvolutionTrajectoryVertex initialTrajectoryVertex,
		final double collateral)
	{
		if (null == marketEdge || null == initialTrajectoryVertex || !NumberUtil.IsValid (collateral)) {
			return null;
		}

		MarketVertex finalMarketVertex = marketEdge.finish();

		MarketVertexEntity finalDealerMarketVertex = finalMarketVertex.dealer();

		MarketVertexEntity finalClientMarketVertex = finalMarketVertex.client();

		PositionGreekVertex initialPositionGreekVertex = initialTrajectoryVertex.positionGreekVertex();

		double initialDerivativeXVAValue = initialPositionGreekVertex.derivativeXVAValue();

		double gainOnDealerDefault = initialTrajectoryVertex.gainOnDealerDefault();

		double dealerSeniorDefaultIntensity = finalDealerMarketVertex.hazardRate();

		double clientDefaultIntensity = finalClientMarketVertex.hazardRate();

		double dealerGainOnClientDefault = initialTrajectoryVertex.gainOnClientDefault();

		double gainOnClientDefault = clientDefaultIntensity * dealerGainOnClientDefault;

		try {
			double initialPortfolioValue = finalMarketVertex.latentStateValue
				(_tradeablesContainer.assetList().get (0).label());

			double portfolioValueBump = _pdeEvolutionControl.sensitivityShiftFactor() *
				initialPortfolioValue;

			double[] bumpedThetaArray = new org.drip.xva.pde.ParabolicDifferentialOperator
				(_tradeablesContainer.assetList().get (0)).thetaUpDown (
					initialTrajectoryVertex,
					initialPortfolioValue,
					portfolioValueBump
				);

			if (null == bumpedThetaArray || 3 != bumpedThetaArray.length) {
				return null;
			}

			return new BurgardKjaerEdgeRun (
				portfolioValueBump,
				-1. * bumpedThetaArray[0],
				-1. * bumpedThetaArray[1],
				-1. * bumpedThetaArray[2],
				finalMarketVertex.csaReplicator() * collateral,
				(dealerSeniorDefaultIntensity + clientDefaultIntensity) * initialDerivativeXVAValue,
				-1. * dealerSeniorDefaultIntensity * gainOnDealerDefault,
				-1. * gainOnClientDefault,
				0.
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Time Increment Run Attribution using the Burgard Kjaer Scheme
	 * 
	 * @param marketEdge The Market Edge
	 * @param initialTrajectoryVertex The Starting Evolution Trajectory Vertex
	 * @param collateral The Off-setting Collateral
	 * 
	 * @return The Time Increment Run Attribution using the Burgard Kjaer Scheme
	 */

	public BurgardKjaerEdgeAttribution edgeRunAttribution (
		final MarketEdge marketEdge,
		final EvolutionTrajectoryVertex initialTrajectoryVertex,
		final double collateral)
	{
		if (null == marketEdge || null == initialTrajectoryVertex) {
			return null;
		}

		MarketVertex finalMarketVertex = marketEdge.finish();

		double derivativeXVAValue = initialTrajectoryVertex.positionGreekVertex().derivativeXVAValue();

		MarketVertexEntity finalDealerMarketVertex = finalMarketVertex.dealer();

		MarketVertexEntity finalClientMarketVertex = finalMarketVertex.client();

		double clientRecoveryRate = finalClientMarketVertex.seniorRecoveryRate();

		double dealerDefaultIntensity = finalDealerMarketVertex.hazardRate();

		double clientDefaultIntensity = finalClientMarketVertex.hazardRate();

		double closeOutMTM = PDEEvolutionControl.CLOSEOUT_GREGORY_LI_TANG ==
			_pdeEvolutionControl.closeOutScheme() ? derivativeXVAValue : derivativeXVAValue;

		double dealerExposure = 0. < closeOutMTM ? closeOutMTM : finalDealerMarketVertex.seniorRecoveryRate()
			* closeOutMTM;

		double derivativeXVAClientDefaultGrowth = -1. * clientDefaultIntensity *
			(0. > closeOutMTM ? closeOutMTM : clientRecoveryRate * closeOutMTM);

		double dealerSeniorFundingSpread = finalDealerMarketVertex.seniorFundingSpread() /
			marketEdge.vertexIncrement();

		try {
			double initialPortfolioValue = finalMarketVertex.latentStateValue
				(_tradeablesContainer.assetList().get (0).label());

			double portfolioValueBump = _pdeEvolutionControl.sensitivityShiftFactor() *
				initialPortfolioValue;

			double[] bumpedThetaArray = new org.drip.xva.pde.ParabolicDifferentialOperator
				(_tradeablesContainer.assetList().get (0)).thetaUpDown (
					initialTrajectoryVertex,
					initialPortfolioValue,
					portfolioValueBump
				);

			if (null == bumpedThetaArray || 3 != bumpedThetaArray.length) {
				return null;
			}

			return new org.drip.xva.pde.BurgardKjaerEdgeAttribution (
				portfolioValueBump,
				-1. * bumpedThetaArray[0],
				-1. * bumpedThetaArray[1],
				-1. * bumpedThetaArray[2],
				finalMarketVertex.csaReplicator() * collateral,
				(dealerDefaultIntensity + clientDefaultIntensity) * derivativeXVAValue,
				dealerSeniorFundingSpread * dealerExposure,
				-1. * dealerDefaultIntensity * dealerExposure,
				derivativeXVAClientDefaultGrowth
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
