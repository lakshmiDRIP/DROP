
package org.drip.exposure.universe;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 * <i>MarketVertexEntity</i> holds the Realizations at a Market Trajectory Vertex of the given XVA Entity
 * (i.e., Dealer/Client). The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ExposureAnalyticsLibrary.md">Exposure Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/README.md">Exposure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/exposure/universe/README.md">Universe</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MarketVertexEntity
{
	private double _hazardRate = java.lang.Double.NaN;
	private double _seniorRecoveryRate = java.lang.Double.NaN;
	private double _seniorFundingSpread = java.lang.Double.NaN;
	private double _survivalProbability = java.lang.Double.NaN;
	private double _seniorFundingReplicator = java.lang.Double.NaN;
	private double _subordinateRecoveryRate = java.lang.Double.NaN;
	private double _subordinateFundingSpread = java.lang.Double.NaN;
	private double _subordinateFundingReplicator = java.lang.Double.NaN;

	/**
	 * Instance of Senior MarketVertexEntity
	 * 
	 * @param timeWidth The Time Width of the Node
	 * @param hazardRate The Hazard Rate Latent State
	 * @param seniorRecoveryRate The Recovery Rate Latent State
	 * @param seniorFundingSpread The Funding Spread Latent State
	 * @param baseRate The Period Base Discount Rate
	 * @param previousMarketVertexEntity The Previous Instance of MarketVertexEntity
	 * 
	 * @return Instance of Senior MarketVertexEntity
	 */

	public static final MarketVertexEntity Senior (
		final double timeWidth,
		final double hazardRate,
		final double seniorRecoveryRate,
		final double seniorFundingSpread,
		final double baseRate,
		final org.drip.exposure.universe.MarketVertexEntity previousMarketVertexEntity)
	{
		if (null == previousMarketVertexEntity)
		{
			return null;
		}

		try
		{
			return new org.drip.exposure.universe.MarketVertexEntity (
				previousMarketVertexEntity.survivalProbability()
					* java.lang.Math.exp (-1. * hazardRate * timeWidth),
				hazardRate,
				seniorRecoveryRate,
				seniorFundingSpread,
				previousMarketVertexEntity.seniorFundingReplicator() *
					java.lang.Math.exp ((baseRate + seniorFundingSpread) * timeWidth),
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				java.lang.Double.NaN
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Instance of Senior MarketVertexEntity
	 * 
	 * @param timeWidth The Time Width of the Node
	 * @param hazardRate The Hazard Rate Latent State
	 * @param seniorRecoveryRate The Recovery Rate Latent State
	 * @param seniorFundingSpread The Funding Spread Latent State
	 * @param baseRate The Period Base Discount Rate
	 * 
	 * @return Instance of Senior MarketVertexEntity
	 */

	public static final MarketVertexEntity Senior (
		final double timeWidth,
		final double hazardRate,
		final double seniorRecoveryRate,
		final double seniorFundingSpread,
		final double baseRate)
	{
		try
		{
			return Senior (
				timeWidth,
				hazardRate,
				seniorRecoveryRate,
				seniorFundingSpread,
				baseRate,
				new MarketVertexEntity (
					1.,
					hazardRate,
					seniorRecoveryRate,
					seniorFundingSpread,
					1.,
					java.lang.Double.NaN,
					java.lang.Double.NaN,
					java.lang.Double.NaN
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Instance of Senior + Subordinate MarketVertexEntity
	 * 
	 * @param timeWidth The Time Width
	 * @param hazardRate The Hazard Rate Latent State
	 * @param seniorRecoveryRate The Senior Recovery Rate Latent State
	 * @param seniorFundingSpread The Senior Funding Spread Latent State
	 * @param subordinateRecoveryRate The Subordinate Recovery Rate Latent State
	 * @param subordinateFundingSpread The Subordinate Funding Spread Latent State
	 * @param baseRate The Period Base Discount Rate
	 * @param previousMarketVertexEntity The Previous Instance of MarketVertexEntity
	 * 
	 * @return Instance of Senior MarketVertexEntity
	 */

	public static final MarketVertexEntity SeniorSubordinate (
		final double timeWidth,
		final double hazardRate,
		final double seniorRecoveryRate,
		final double seniorFundingSpread,
		final double subordinateRecoveryRate,
		final double subordinateFundingSpread,
		final double baseRate,
		final org.drip.exposure.universe.MarketVertexEntity previousMarketVertexEntity)
	{
		if (null == previousMarketVertexEntity)
		{
			return null;
		}

		try
		{
			return new org.drip.exposure.universe.MarketVertexEntity (
				previousMarketVertexEntity.survivalProbability() *
					java.lang.Math.exp (-1. * hazardRate * timeWidth),
				hazardRate,
				seniorRecoveryRate,
				seniorFundingSpread,
				previousMarketVertexEntity.seniorFundingReplicator() *
					java.lang.Math.exp ((baseRate + seniorFundingSpread) * timeWidth),
				subordinateRecoveryRate,
				subordinateFundingSpread,
				previousMarketVertexEntity.subordinateFundingReplicator() *
					java.lang.Math.exp ((baseRate + subordinateFundingSpread) * timeWidth)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Instance of Senior + Subordinate MarketVertexEntity
	 * 
	 * @param timeWidth The Time Width
	 * @param hazardRate The Hazard Rate Latent State
	 * @param seniorRecoveryRate The Senior Recovery Rate Latent State
	 * @param seniorFundingSpread The Senior Funding Spread Latent State
	 * @param subordinateRecoveryRate The Subordinate Recovery Rate Latent State
	 * @param subordinateFundingSpread The Subordinate Funding Spread Latent State
	 * @param baseRate The Period Base Discount Rate
	 * 
	 * @return Instance of Senior MarketVertexEntity
	 */

	public static final MarketVertexEntity SeniorSubordinate (
		final double timeWidth,
		final double hazardRate,
		final double seniorRecoveryRate,
		final double seniorFundingSpread,
		final double subordinateRecoveryRate,
		final double subordinateFundingSpread,
		final double baseRate)
	{
		try
		{
			return SeniorSubordinate (
				timeWidth,
				hazardRate,
				seniorRecoveryRate,
				seniorFundingSpread,
				subordinateRecoveryRate,
				subordinateFundingSpread,
				baseRate,
				new MarketVertexEntity (
					1.,
					hazardRate,
					seniorRecoveryRate,
					seniorFundingSpread,
					1.,
					subordinateRecoveryRate,
					subordinateFundingSpread,
					1.
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MarketVertexEntity Constructor
	 * 
	 * @param survivalProbability The Realized Entity Survival Probability
	 * @param hazardRate The Realized Entity Hazard Rate Latent State
	 * @param seniorRecoveryRate The Entity Senior Recovery Rate Latent State
	 * @param seniorFundingSpread The Entity Senior Funding Spread Latent State
	 * @param seniorFundingReplicator The Entity Senior Funding Replicator Vertex Latent State
	 * @param subordinateRecoveryRate The Entity Subordinate Recovery Rate Latent State
	 * @param subordinateFundingSpread The Entity Subordinate Funding Spread Latent State
	 * @param subordinateFundingReplicator The Entity Subordinate Funding Replicator Vertex Latent State
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarketVertexEntity (
		final double survivalProbability,
		final double hazardRate,
		final double seniorRecoveryRate,
		final double seniorFundingSpread,
		final double seniorFundingReplicator,
		final double subordinateRecoveryRate,
		final double subordinateFundingSpread,
		final double subordinateFundingReplicator)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_survivalProbability = survivalProbability) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_hazardRate = hazardRate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_seniorRecoveryRate = seniorRecoveryRate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_seniorFundingSpread = seniorFundingSpread) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_seniorFundingReplicator = seniorFundingReplicator))
		{
			throw new java.lang.Exception ("MarketVertexEntity Constructor => Invalid Inputs");
		}

		_subordinateRecoveryRate = subordinateRecoveryRate;
		_subordinateFundingSpread = subordinateFundingSpread;
		_subordinateFundingReplicator = subordinateFundingReplicator;
	}

	/**
	 * Retrieve the Realized Entity Survival Probability
	 * 
	 * @return The Realized Entity Survival Probability
	 */

	public double survivalProbability()
	{
		return _survivalProbability;
	}

	/**
	 * Retrieve the Realized Entity Hazard Rate Vertex Latent State
	 * 
	 * @return The Realized Entity Hazard Rate Vertex Latent State
	 */

	public double hazardRate()
	{
		return _hazardRate;
	}

	/**
	 * Retrieve the Realized Entity Senior Recovery Rate Vertex Latent State
	 * 
	 * @return The Realized Entity Senior Recovery Rate Vertex Latent State
	 */

	public double seniorRecoveryRate()
	{
		return _seniorRecoveryRate;
	}

	/**
	 * Retrieve the Realized Entity Senior Funding Spread Vertex Latent State
	 * 
	 * @return The Realized Entity Senior Funding Spread Vertex Latent State
	 */

	public double seniorFundingSpread()
	{
		return _seniorFundingSpread;
	}

	/**
	 * Retrieve the Realized Entity Senior Funding Replicator Vertex Latent State
	 * 
	 * @return The Realized Entity Senior Funding Replicator Vertex Latent State
	 */

	public double seniorFundingReplicator()
	{
		return _seniorFundingReplicator;
	}

	/**
	 * Retrieve the Realized Entity Subordinate Recovery Rate Vertex Latent State
	 * 
	 * @return The Realized Entity Subordinate Recovery Rate Vertex Latent State
	 */

	public double subordinateRecoveryRate()
	{
		return _subordinateRecoveryRate;
	}

	/**
	 * Retrieve the Realized Entity Subordinate Funding Spread Vertex Latent State
	 * 
	 * @return The Realized Entity Subordinate Funding Spread Vertex Latent State
	 */

	public double subordinateFundingSpread()
	{
		return _subordinateFundingSpread;
	}

	/**
	 * Retrieve the Realized Entity Subordinate Funding Replicator Vertex Latent State
	 * 
	 * @return The Realized Entity Subordinate Funding Replicator Vertex Latent State
	 */

	public double subordinateFundingReplicator()
	{
		return _subordinateFundingReplicator;
	}
}
