
package org.drip.exposure.universe;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>MarketVertex</i> holds the Market Realizations at a Market Trajectory Vertex needed for computing the
 * Valuation Adjustment. The References are:
 *  
 * <br><br>
 *  	<ul>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2013): Funding Costs, Funding Strategies <i>Risk</i> <b>23
 *  				(12)</b> 82-87
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75
 *  		</li>
 *  		<li>
 *  			Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-
 *  				party Risk and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19
 *  		</li>
 *  		<li>
 *  			Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  				86-90
 *  		</li>
 *  		<li>
 *  			Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives
 *  				Pricing <i>Risk</i> <b>21 (2)</b> 97-102
 *  		</li>
 *  		<li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure Group Level Collateralized/Uncollateralized Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/universe/README.md">Exposure Generation - Market States Simulation</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MarketVertex
{
	private double _csaSpread = java.lang.Double.NaN;
	private double _csaReplicator = java.lang.Double.NaN;
	private double _overnightRate = java.lang.Double.NaN;
	private double _overnightReplicator = java.lang.Double.NaN;
	private org.drip.analytics.date.JulianDate _anchorDate = null;
	private org.drip.exposure.universe.MarketVertexEntity _clientMarketVertex = null;
	private org.drip.exposure.universe.MarketVertexEntity _dealerMarketVertex = null;
	private org.drip.exposure.evolver.LatentStateVertexContainer _latentStateVertexContainer = null;

	/**
	 * Generate an Initial Instance of MarketVertex
	 * 
	 * @param anchorDate The Anchor Date
	 * @param overnightReplicator The Realized Overnight Latent State Replicator
	 * @param csaReplicator The Realized CSA Latent State Replicator
	 * @param dealerHazardRate Realized Dealer Hazard Rate
	 * @param dealerRecoveryRate Realized Dealer Recovery Rate
	 * @param dealerFundingSpread Realized Dealer Funding Spread
	 * @param clientHazardRate Realized Client Hazard Rate
	 * @param clientRecoveryRate Realized Client Recovery Rate
	 * @param clientFundingSpread Realized Client Funding Spread
	 * @param latentStateVertexContainer Latent State Vertex Container
	 * 
	 * @return The Initial MarketVertex Instance
	 */

	public static final MarketVertex Epochal (
		final org.drip.analytics.date.JulianDate anchorDate,
		final double overnightReplicator,
		final double csaReplicator,
		final double dealerHazardRate,
		final double dealerRecoveryRate,
		final double dealerFundingSpread,
		final double clientHazardRate,
		final double clientRecoveryRate,
		final double clientFundingSpread,
		final org.drip.exposure.evolver.LatentStateVertexContainer latentStateVertexContainer)
	{
		try
		{
			return new org.drip.exposure.universe.MarketVertex (
				anchorDate,
				0.,
				overnightReplicator,
				0.,
				csaReplicator,
				org.drip.exposure.universe.MarketVertexEntity.Senior (
					0.,
					dealerHazardRate,
					dealerRecoveryRate,
					dealerFundingSpread,
					0.
				),
				org.drip.exposure.universe.MarketVertexEntity.Senior (
					0.,
					clientHazardRate,
					clientRecoveryRate,
					clientFundingSpread,
					0.
				),
				latentStateVertexContainer
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate an Initial Instance of MarketVertex
	 * 
	 * @param anchorDate The Anchor Date
	 * @param overnightReplicator The Realized Overnight Latent State Replicator
	 * @param csaReplicator The Realized CSA Latent State Replicator
	 * @param dealerHazardRate Realized Dealer Hazard Rate
	 * @param dealerSeniorRecoveryRate Realized Dealer Senior Recovery Rate
	 * @param dealerSeniorFundingSpread Realized Dealer Senior Funding Spread
	 * @param dealerSubordinateRecoveryRate Realized Dealer Subordinate Recovery Rate
	 * @param dealerSubordinateFundingSpread Realized Dealer Subordinate Funding Spread
	 * @param clientHazardRate Realized Client Hazard Rate
	 * @param clientRecoveryRate Realized Client Recovery Rate
	 * @param clientFundingSpread Realized Client Funding Spread
	 * @param latentStateVertexContainer Latent State Vertex Container
	 * 
	 * @return The Initial MarketVertex Instance
	 */

	public static final MarketVertex Epochal (
		final org.drip.analytics.date.JulianDate anchorDate,
		final double overnightReplicator,
		final double csaReplicator,
		final double dealerHazardRate,
		final double dealerSeniorRecoveryRate,
		final double dealerSeniorFundingSpread,
		final double dealerSubordinateRecoveryRate,
		final double dealerSubordinateFundingSpread,
		final double clientHazardRate,
		final double clientRecoveryRate,
		final double clientFundingSpread,
		final org.drip.exposure.evolver.LatentStateVertexContainer latentStateVertexContainer)
	{
		try {
			return new org.drip.exposure.universe.MarketVertex (
				anchorDate,
				0.,
				overnightReplicator,
				0.,
				csaReplicator,
				org.drip.exposure.universe.MarketVertexEntity.SeniorSubordinate (
					0.,
					dealerHazardRate,
					dealerSeniorRecoveryRate,
					dealerSeniorFundingSpread,
					dealerSubordinateRecoveryRate,
					dealerSubordinateFundingSpread,
					0.
				),
				org.drip.exposure.universe.MarketVertexEntity.Senior (
					0.,
					clientHazardRate,
					clientRecoveryRate,
					clientFundingSpread,
					0.
				),
				latentStateVertexContainer
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Nodal Market Vertex
	 * 
	 * @param anchorDate The Vertex Date Anchor
	 * @param overnightRate The Realized Overnight Rate
	 * @param overnightReplicator The Realized Overnight Latent State Replicator
	 * @param csaSpread The Realized CSA Spread
	 * @param csaReplicator The Realized CSA Latent State Replicator
	 * @param dealerMarketVertex Dealer Market Vertex Instance
	 * @param clientMarketVertex Client Market Vertex Instance
	 * @param latentStateVertexContainer Latent State Vertex Container
	 * 
	 * @return The Nodal Market Vertex Instance
	 */

	public static final MarketVertex Nodal (
		final org.drip.analytics.date.JulianDate anchorDate,
		final double overnightRate,
		final double overnightReplicator,
		final double csaSpread,
		final double csaReplicator,
		final org.drip.exposure.universe.MarketVertexEntity dealerMarketVertex,
		final org.drip.exposure.universe.MarketVertexEntity clientMarketVertex,
		final org.drip.exposure.evolver.LatentStateVertexContainer latentStateVertexContainer)
	{
		try
		{
			return new MarketVertex (
				anchorDate,
				overnightRate,
				overnightReplicator,
				csaSpread,
				csaReplicator,
				dealerMarketVertex,
				clientMarketVertex,
				latentStateVertexContainer
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MarketVertex Constructor
	 * 
	 * @param anchorDate The Vertex Date Anchor
	 * @param overnightRate The Realized Overnight Rate
	 * @param overnightReplicator The Realized Overnight Latent State Replicator
	 * @param csaSpread The Realized CSA Spread
	 * @param csaReplicator The Realized CSA Latent State Replicator
	 * @param dealerMarketVertex Dealer Market Vertex Instance
	 * @param clientMarketVertex Client Market Vertex Instance
	 * @param latentStateVertexContainer Latent State Vertex Container
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	protected MarketVertex (
		final org.drip.analytics.date.JulianDate anchorDate,
		final double overnightRate,
		final double overnightReplicator,
		final double csaSpread,
		final double csaReplicator,
		final org.drip.exposure.universe.MarketVertexEntity dealerMarketVertex,
		final org.drip.exposure.universe.MarketVertexEntity clientMarketVertex,
		final org.drip.exposure.evolver.LatentStateVertexContainer latentStateVertexContainer)
		throws java.lang.Exception
	{
		if (null == (_anchorDate = anchorDate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_overnightRate = overnightRate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_overnightReplicator = overnightReplicator) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_csaSpread = csaSpread) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_csaReplicator = csaReplicator) ||
			null == (_dealerMarketVertex = dealerMarketVertex) ||
			null == (_clientMarketVertex = clientMarketVertex) ||
			null == (_latentStateVertexContainer = latentStateVertexContainer))
		{
			throw new java.lang.Exception ("MarketVertex Constructor => Invalid Inputs");
		}

		_latentStateVertexContainer = latentStateVertexContainer;
	}

	/**
	 * Retrieve the Date Anchor
	 * 
	 * @return The Date Anchor
	 */

	public org.drip.analytics.date.JulianDate anchorDate()
	{
		return _anchorDate;
	}

	/**
	 * Retrieve the Latent State Vertex Container
	 * 
	 * @return The Latent State Vertex Container
	 */

	org.drip.exposure.evolver.LatentStateVertexContainer latentStateVertexContainer()
	{
		return _latentStateVertexContainer;
	}

	/**
	 * Retrieve the Realized Value for the Latent State
	 * 
	 * @param latentStateLabel The Latent State Label
	 * 
	 * @return The Realized Value for the Latent State
	 *
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double latentStateValue (
		final org.drip.state.identifier.LatentStateLabel latentStateLabel)
		throws java.lang.Exception
	{
		return _latentStateVertexContainer.value (latentStateLabel);
	}

	/**
	 * Retrieve the Realized Overnight Index Rate
	 * 
	 * @return The Realized Overnight Index Rate
	 */

	public double overnightRate()
	{
		return _overnightRate;
	}

	/**
	 * Retrieve the Realized Overnight Index Numeraire
	 * 
	 * @return The Realized Overnight Index Numeraire
	 */

	public double overnightReplicator()
	{
		return _overnightReplicator;
	}

	/**
	 * Retrieve the Realized Spread over the Overnight Policy Rate corresponding to the CSA Scheme
	 * 
	 * @return The Realized Spread over the Overnight Policy Rate corresponding to the CSA Scheme
	 */

	public double csaSpread()
	{
		return _csaSpread;
	}

	/**
	 * Retrieve the Realized CSA Scheme Numeraire
	 * 
	 * @return The Realized CSA Scheme Numeraire
	 */

	public double csaReplicator()
	{
		return _csaReplicator;
	}

	/**
	 * Retrieve the Realized CSA Scheme Rate
	 * 
	 * @return The Realized CSA Scheme Rate
	 */

	public double csaRate()
	{
		return _overnightRate + _csaSpread;
	}

	/**
	 * Retrieve the Realized Dealer Senior Market Vertex
	 * 
	 * @return The Realized Dealer Senior Market Vertex
	 */

	public org.drip.exposure.universe.MarketVertexEntity dealer()
	{
		return _dealerMarketVertex;
	}

	/**
	 * Retrieve the Realized Client Market Vertex
	 * 
	 * @return The Realized Client Market Vertex
	 */

	public org.drip.exposure.universe.MarketVertexEntity client()
	{
		return _clientMarketVertex;
	}
}
