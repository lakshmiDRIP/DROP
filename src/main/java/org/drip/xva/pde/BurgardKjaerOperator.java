
package org.drip.xva.pde;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva">XVA</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/pde">PDE</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/XVA">XVA Analytics Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BurgardKjaerOperator
{
	private org.drip.xva.definition.PDEEvolutionControl _pdeEvolutionControl = null;
	private org.drip.exposure.evolver.PrimarySecurityDynamicsContainer _tradeablesContainer = null;

	/**
	 * BurgardKjaerOperator Constructor
	 * 
	 * @param tradeablesContainer The Universe of Tradeable Assets
	 * @param pdeEvolutionControl The XVA Control Settings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BurgardKjaerOperator (
		final org.drip.exposure.evolver.PrimarySecurityDynamicsContainer tradeablesContainer,
		final org.drip.xva.definition.PDEEvolutionControl pdeEvolutionControl)
		throws java.lang.Exception
	{
		if (null == (_tradeablesContainer = tradeablesContainer) ||
			null == (_pdeEvolutionControl = pdeEvolutionControl))
		{
			throw new java.lang.Exception ("BurgardKjaerOperator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Universe of Trade-able Assets
	 * 
	 * @return The Universe of Trade-able Assets
	 */

	public org.drip.exposure.evolver.PrimarySecurityDynamicsContainer tradeablesContainer()
	{
		return _tradeablesContainer;
	}

	/**
	 * Retrieve the XVA Control Settings
	 * 
	 * @return The XVA Control Settings
	 */

	public org.drip.xva.definition.PDEEvolutionControl pdeEvolutionControl()
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

	public org.drip.xva.pde.BurgardKjaerEdgeRun edgeRun (
		final org.drip.exposure.universe.MarketEdge marketEdge,
		final org.drip.xva.derivative.EvolutionTrajectoryVertex initialTrajectoryVertex,
		final double collateral)
	{
		if (null == marketEdge ||
			null == initialTrajectoryVertex ||
			!org.drip.quant.common.NumberUtil.IsValid (collateral))
		{
			return null;
		}

		org.drip.exposure.universe.MarketVertex finalMarketVertex = marketEdge.finish();

		org.drip.exposure.universe.MarketVertexEntity finalDealerMarketVertex = finalMarketVertex.dealer();

		org.drip.exposure.universe.MarketVertexEntity finalClientMarketVertex = finalMarketVertex.client();

		org.drip.xva.derivative.PositionGreekVertex initialPositionGreekVertex =
			initialTrajectoryVertex.positionGreekVertex();

		double initialDerivativeXVAValue = initialPositionGreekVertex.derivativeXVAValue();

		double gainOnDealerDefault = initialTrajectoryVertex.gainOnDealerDefault();

		double dealerSeniorDefaultIntensity = finalDealerMarketVertex.hazardRate();

		double clientDefaultIntensity = finalClientMarketVertex.hazardRate();

		double dealerGainOnClientDefault = initialTrajectoryVertex.gainOnClientDefault();

		double gainOnClientDefault = clientDefaultIntensity * dealerGainOnClientDefault;

		try
		{
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

			if (null == bumpedThetaArray || 3 != bumpedThetaArray.length)
			{
				return null;
			}

			return new org.drip.xva.pde.BurgardKjaerEdgeRun (
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
		}
		catch (java.lang.Exception e)
		{
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

	public org.drip.xva.pde.BurgardKjaerEdgeAttribution edgeRunAttribution (
		final org.drip.exposure.universe.MarketEdge marketEdge,
		final org.drip.xva.derivative.EvolutionTrajectoryVertex initialTrajectoryVertex,
		final double collateral)
	{
		if (null == marketEdge ||
			null == initialTrajectoryVertex)
		{
			return null;
		}

		org.drip.exposure.universe.MarketVertex finalMarketVertex = marketEdge.finish();

		double derivativeXVAValue = initialTrajectoryVertex.positionGreekVertex().derivativeXVAValue();

		org.drip.exposure.universe.MarketVertexEntity finalDealerMarketVertex = finalMarketVertex.dealer();

		org.drip.exposure.universe.MarketVertexEntity finalClientMarketVertex = finalMarketVertex.client();

		double clientRecoveryRate = finalClientMarketVertex.seniorRecoveryRate();

		double dealerDefaultIntensity = finalDealerMarketVertex.hazardRate();

		double clientDefaultIntensity = finalClientMarketVertex.hazardRate();

		double closeOutMTM = org.drip.xva.definition.PDEEvolutionControl.CLOSEOUT_GREGORY_LI_TANG ==
			_pdeEvolutionControl.closeOutScheme() ? derivativeXVAValue : derivativeXVAValue;

		double dealerExposure = closeOutMTM > 0. ? closeOutMTM : finalDealerMarketVertex.seniorRecoveryRate()
			* closeOutMTM;

		double derivativeXVAClientDefaultGrowth = -1. * clientDefaultIntensity *
			(closeOutMTM < 0. ? closeOutMTM : clientRecoveryRate * closeOutMTM);

		double dealerSeniorFundingSpread = finalDealerMarketVertex.seniorFundingSpread() /
			marketEdge.vertexIncrement();

		try
		{
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

			if (null == bumpedThetaArray || 3 != bumpedThetaArray.length)
			{
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
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
