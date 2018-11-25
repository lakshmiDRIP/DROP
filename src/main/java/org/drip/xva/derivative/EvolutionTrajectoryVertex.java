
package org.drip.xva.derivative;

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
 * <i>EvolutionTrajectoryVertex</i> holds the Evolution Snapshot of the Trade-able Prices, the Cash Account,
 * the Replication Portfolio, and the corresponding Derivative Value, as laid out in Burgard and Kjaer
 * (2014). The References are:
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
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/xva/derivative">Derivative</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/XVA">XVA Analytics Library</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EvolutionTrajectoryVertex
{
	private double _time = java.lang.Double.NaN;
	private double _collateral = java.lang.Double.NaN;
	private double _hedgeError = java.lang.Double.NaN;
	private double _clientGainOnDealerDefault = java.lang.Double.NaN;
	private double _dealerGainOnClientDefault = java.lang.Double.NaN;
	private org.drip.xva.derivative.PositionGreekVertex _positionGreekVertex = null;
	private org.drip.xva.derivative.ReplicationPortfolioVertex _replicationPortfolioVertex = null;

	/**
	 * EvolutionTrajectoryVertex Constructor
	 * 
	 * @param time The Evolution Trajectory Edge Time
	 * @param replicationPortfolioVertex The Replication Portfolio Vertex
	 * @param positionGreekVertex The Position Greek Vertex
	 * @param clientGainOnDealerDefault Client Gain On Dealer Default
	 * @param dealerGainOnClientDefault Dealer Gain On Default of Client
	 * @param collateral The Vertex Collateral
	 * @param hedgeError The Vertex Hedge Error
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EvolutionTrajectoryVertex (
		final double time,
		final org.drip.xva.derivative.ReplicationPortfolioVertex replicationPortfolioVertex,
		final org.drip.xva.derivative.PositionGreekVertex positionGreekVertex,
		final double clientGainOnDealerDefault,
		final double dealerGainOnClientDefault,
		final double collateral,
		final double hedgeError)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_time = time) ||
			null == (_replicationPortfolioVertex = replicationPortfolioVertex) ||
			null == (_positionGreekVertex = positionGreekVertex) ||
			!org.drip.quant.common.NumberUtil.IsValid (_clientGainOnDealerDefault =
				clientGainOnDealerDefault) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dealerGainOnClientDefault =
				dealerGainOnClientDefault) ||
			!org.drip.quant.common.NumberUtil.IsValid (_collateral = collateral) ||
			!org.drip.quant.common.NumberUtil.IsValid (_hedgeError = hedgeError))
		{
			throw new java.lang.Exception ("EvolutionTrajectoryVertex Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Time Instant
	 * 
	 * @return The Time Instant
	 */

	public double time()
	{
		return _time;
	}

	/**
	 * Retrieve the Collateral
	 * 
	 * @return The Collateral
	 */

	public double collateral()
	{
		return _collateral;
	}

	/**
	 * Retrieve the Hedge Error
	 * 
	 * @return The Hedge Error
	 */

	public double hedgeError()
	{
		return _hedgeError;
	}

	/**
	 * Retrieve the Replication Portfolio Vertex
	 * 
	 * @return The Replication Portfolio Vertex
	 */

	public org.drip.xva.derivative.ReplicationPortfolioVertex replicationPortfolioVertex()
	{
		return _replicationPortfolioVertex;
	}

	/**
	 * Retrieve the Position Greek Vertex
	 * 
	 * @return The Position Greek Vertex
	 */

	public org.drip.xva.derivative.PositionGreekVertex positionGreekVertex()
	{
		return _positionGreekVertex;
	}

	/**
	 * Retrieve the Client Gain On Dealer Default
	 * 
	 * @return The Client Gain On Dealer Default
	 */

	public double gainOnDealerDefault()
	{
		return _clientGainOnDealerDefault;
	}

	/**
	 * Retrieve the Dealer Gain On Individual Client Default
	 * 
	 * @return The Dealer Gain On Individual Client Default
	 */

	public double gainOnClientDefault()
	{
		return _dealerGainOnClientDefault;
	}

	/**
	 * Indicate whether Replication Portfolio satisfies the Funding Constraint implied by the Vertex
	 * 	Numeraire
	 * 
	 * @param marketVertex The Market Vertex
	 * 
	 * @return The Funding Constraint Verification Mismatch
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double verifyFundingConstraint (
		final org.drip.exposure.universe.MarketVertex marketVertex)
		throws java.lang.Exception
	{
		if (null == marketVertex)
		{
			throw new java.lang.Exception
				("EvolutionTrajectoryVertex::verifyFundingConstraint => Invalid Inputs");
		}

		org.drip.exposure.universe.MarketVertexEntity dealerMarketVertex = marketVertex.dealer();

		double fundingConstraint = _positionGreekVertex.derivativeXVAValue() +
			dealerMarketVertex.seniorFundingReplicator() *
				_replicationPortfolioVertex.dealerSeniorNumeraireHoldings();

		double dealerSubordinateFundingMarketVertex = dealerMarketVertex.subordinateFundingReplicator();

		if (org.drip.quant.common.NumberUtil.IsValid (dealerSubordinateFundingMarketVertex))
		{
			fundingConstraint += dealerSubordinateFundingMarketVertex *
				_replicationPortfolioVertex.dealerSubordinateNumeraireHoldings();
		}

		return fundingConstraint;
	}
}
