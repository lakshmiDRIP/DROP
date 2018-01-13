
package org.drip.xva.universe;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * EntityMarketVertex holds the Realizations at a Market Trajectory Vertex of the given Entity (i.e.,
 *  Bank/Counter Party). The References are:<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b> 82-87.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75.<br><br>
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b> 86-90.<br><br>
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing <i>Risk</i>
 *  	<b>21 (2)</b> 97-102.<br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EntityMarketVertex
{
	private double _hazardRate = java.lang.Double.NaN;
	private double _seniorRecoveryRate = java.lang.Double.NaN;
	private double _seniorFundingSpread = java.lang.Double.NaN;
	private double _survivalProbability = java.lang.Double.NaN;
	private double _subordinateRecoveryRate = java.lang.Double.NaN;
	private double _subordinateFundingSpread = java.lang.Double.NaN;
	private org.drip.xva.universe.LatentStateMarketVertex _seniorFundingLatentState = null;
	private org.drip.xva.universe.LatentStateMarketVertex _subordinateFundingLatentState = null;

	/**
	 * Instance of Senior EntityMarketVertex
	 * 
	 * @param timeWidth The Time Width of the Node
	 * @param hazardRate The Hazard Rate
	 * @param recoveryRate The Recovery Rate
	 * @param fundingSpread The Funding Spread
	 * @param previousEntityMarketVertex The Previous Instance of EntityMarketVertex
	 * 
	 * @return Instance of Senior EntityMarketVertex
	 */

	public static final EntityMarketVertex Senior (
		final double timeWidth,
		final double hazardRate,
		final double recoveryRate,
		final double fundingSpread,
		final org.drip.xva.universe.EntityMarketVertex previousEntityMarketVertex)
	{
		org.drip.xva.universe.LatentStateMarketVertex lsmvSenior = null == previousEntityMarketVertex ? null :
			previousEntityMarketVertex.seniorFundingLatentState();

		try
		{
			return new org.drip.xva.universe.EntityMarketVertex (
				(null == previousEntityMarketVertex ? 1. : previousEntityMarketVertex.survivalProbability())
					* java.lang.Math.exp (-1. * hazardRate * timeWidth),
				hazardRate,
				recoveryRate,
				fundingSpread,
				new org.drip.xva.universe.LatentStateMarketVertex (
					null == lsmvSenior ? 1. : lsmvSenior.epochal(),
					(null == lsmvSenior ? 1. : lsmvSenior.nodal()) *
						java.lang.Math.exp (-1. * fundingSpread * timeWidth)
				),
				java.lang.Double.NaN,
				java.lang.Double.NaN,
				null
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Instance of Senior EntityMarketVertex
	 * 
	 * @param dblTimeWidth The Time Width
	 * @param hazardRate The Hazard Rate
	 * @param seniorRecoveryRate The Senior Recovery Rate
	 * @param seniorFundingSpread The Senior Funding Spread
	 * @param subordinateRecoveryRate The Subordinate Recovery Rate
	 * @param subordinateFundingSpread The Subordinate Funding Spread
	 * @param previousEntityMarketVertex The Previous Instance of EntityMarketVertex
	 * 
	 * @return Instance of Senior EntityMarketVertex
	 */

	public static final EntityMarketVertex SeniorSubordinate (
		final double dblTimeWidth,
		final double hazardRate,
		final double seniorRecoveryRate,
		final double seniorFundingSpread,
		final double subordinateRecoveryRate,
		final double subordinateFundingSpread,
		final org.drip.xva.universe.EntityMarketVertex previousEntityMarketVertex)
	{
		org.drip.xva.universe.LatentStateMarketVertex previousSeniorFundingLatentState = null ==
			previousEntityMarketVertex ? null : previousEntityMarketVertex.seniorFundingLatentState();

		org.drip.xva.universe.LatentStateMarketVertex previousSubordinateFundingLatentState = null ==
			previousEntityMarketVertex ? null : previousEntityMarketVertex.subordinateFundingLatentState();

		try
		{
			return null == previousSubordinateFundingLatentState ? null : new
				org.drip.xva.universe.EntityMarketVertex (
				(null == previousEntityMarketVertex ? 1. : previousEntityMarketVertex.survivalProbability())
					* java.lang.Math.exp (-1. * hazardRate * dblTimeWidth),
				hazardRate,
				seniorRecoveryRate,
				seniorFundingSpread,
				new org.drip.xva.universe.LatentStateMarketVertex (
					null == previousSeniorFundingLatentState ? 1. : previousSeniorFundingLatentState.epochal(),
					(null == previousSeniorFundingLatentState ? 1. : previousSeniorFundingLatentState.nodal())
						* java.lang.Math.exp (-1. * seniorFundingSpread * dblTimeWidth)
				),
				subordinateRecoveryRate,
				subordinateFundingSpread,
				new org.drip.xva.universe.LatentStateMarketVertex (
					null == previousSubordinateFundingLatentState ? 1. :
						previousSubordinateFundingLatentState.epochal(),
					(null == previousSubordinateFundingLatentState ? 1. :
						previousSubordinateFundingLatentState.nodal()) * java.lang.Math.exp (-1. *
							subordinateFundingSpread * dblTimeWidth)
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
	 * EntityMarketVertex Constructor
	 * 
	 * @param survivalProbability The Realized Entity Survival Probability
	 * @param hazardRate The Realized Entity Hazard Rate
	 * @param seniorRecoveryRate The Entity Senior Recovery Rate
	 * @param seniorFundingSpread The Entity Senior Funding Spread
	 * @param seniorFundingLatentState The Entity Senior Funding Latent State Vertex
	 * @param subordinateRecoveryRate The Entity Subordinate Recovery Rate
	 * @param subordinateFundingSpread The Entity Subordinate Funding Spread
	 * @param subordinateFundingLatentState The Entity Subordinate Funding Latent State Vertex
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EntityMarketVertex (
		final double survivalProbability,
		final double hazardRate,
		final double seniorRecoveryRate,
		final double seniorFundingSpread,
		final org.drip.xva.universe.LatentStateMarketVertex seniorFundingLatentState,
		final double subordinateRecoveryRate,
		final double subordinateFundingSpread,
		final org.drip.xva.universe.LatentStateMarketVertex subordinateFundingLatentState)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_survivalProbability = survivalProbability) ||
			!org.drip.quant.common.NumberUtil.IsValid (_seniorRecoveryRate = seniorRecoveryRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (_hazardRate = hazardRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (_seniorFundingSpread = seniorFundingSpread) ||
			null == (_seniorFundingLatentState = seniorFundingLatentState))
		{
			throw new java.lang.Exception ("EntityMarketVertex Constructor => Invalid Inputs");
		}

		_subordinateRecoveryRate = subordinateRecoveryRate;
		_subordinateFundingSpread = subordinateFundingSpread;
		_subordinateFundingLatentState = subordinateFundingLatentState;
	}

	/**
	 * Retrieve the Realized Entity Hazard Rate
	 * 
	 * @return The Realized Entity Hazard Rate
	 */

	public double hazardRate()
	{
		return _hazardRate;
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
	 * Retrieve the Realized Entity Senior Recovery Rate
	 * 
	 * @return The Realized Entity Senior Recovery Rate
	 */

	public double seniorRecoveryRate()
	{
		return _seniorRecoveryRate;
	}

	/**
	 * Retrieve the Realized Entity Senior Funding Spread
	 * 
	 * @return The Realized Entity Senior Funding Spread
	 */

	public double seniorFundingSpread()
	{
		return _seniorFundingSpread;
	}

	/**
	 * Retrieve the Realized Entity Senior Funding Latent State Vertex
	 * 
	 * @return The Realized Entity Senior Funding Latent State Vertex
	 */

	public org.drip.xva.universe.LatentStateMarketVertex seniorFundingLatentState()
	{
		return _seniorFundingLatentState;
	}

	/**
	 * Retrieve the Realized Entity Subordinate Recovery Rate
	 * 
	 * @return The Realized Entity Subordinate Recovery Rate
	 */

	public double subordinateRecoveryRate()
	{
		return _subordinateRecoveryRate;
	}

	/**
	 * Retrieve the Realized Entity Subordinate Funding Spread
	 * 
	 * @return The Realized Entity Subordinate Funding Spread
	 */

	public double subordinateFundingSpread()
	{
		return _subordinateFundingSpread;
	}

	/**
	 * Retrieve the Realized Entity Subordinate Funding Latent State Vertex
	 * 
	 * @return The Realized Entity Subordinate Funding Latent State Vertex
	 */

	public org.drip.xva.universe.LatentStateMarketVertex subordinateFundingLatentState()
	{
		return _subordinateFundingLatentState;
	}
}
