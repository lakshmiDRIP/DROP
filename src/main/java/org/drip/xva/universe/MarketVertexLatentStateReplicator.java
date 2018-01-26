
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
 * MarketVertexLatentStateReplicator holds the Epochal and the Nodal Latent State Realizations at a Market
 *  Trajectory Vertex needed for computing the Valuation Adjustment. The References are:
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs, Risk, 24 (12) 82-87.
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs, Journal of Credit Risk, 7 (3) 1-19.
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance, Risk, 24 (11) 72-75.
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk, Risk 20 (2) 86-90.
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing, Risk
 *  	21 (2) 97-102.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MarketVertexLatentStateReplicator extends org.drip.xva.universe.MarketVertexLatentState
{
	private double _epochal = java.lang.Double.NaN;

	/**
	 * Build the Epochal MarketVertexLatentStateReplicator Instance
	 * 
	 * @param label The Latent State Label
	 * @param epochal The Epochal Replicator Numeraire Value
	 * 
	 * @return The Epochal MarketVertexLatentStateReplicator Instance
	 */

	public static final MarketVertexLatentStateReplicator Epochal (
		final org.drip.state.identifier.LatentStateLabel label,
		final double epochal)
	{
		try
		{
			return new MarketVertexLatentStateReplicator (
				label,
				epochal,
				epochal
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Build the Nodal MarketVertexLatentStateReplicator Instance
	 * 
	 * @param label The Latent State Label
	 * @param nodal The Nodal Replicator Numeraire Value
	 * 
	 * @return The Nodal MarketVertexLatentStateReplicator Instance
	 */

	public static final MarketVertexLatentStateReplicator Nodal (
		final org.drip.state.identifier.LatentStateLabel label,
		final double nodal)
	{
		try
		{
			return new MarketVertexLatentStateReplicator (
				label,
				1.,
				nodal
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MarketVertexLatentStateReplicator Constructor
	 * 
	 * @param label The Latent State Label
	 * @param epochal The Epochal Latent State Realization
	 * @param nodal The Nodal Latent State Realization
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarketVertexLatentStateReplicator (
		final org.drip.state.identifier.LatentStateLabel label,
		final double epochal,
		final double nodal)
		throws java.lang.Exception
	{
		super (
			label,
			nodal
		);

		if (!org.drip.quant.common.NumberUtil.IsValid (_epochal = epochal))
		{
			throw new java.lang.Exception
				("MarketVertexLatentStateReplicator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Epochal Latent State Realization
	 * 
	 * @return The Epochal Latent State Realization
	 */

	public double epochal()
	{
		return _epochal;
	}

	/**
	 * Retrieve the Epochal/Nodal Latent State Scale
	 * 
	 * @return The Epochal/Nodal Latent State Scale
	 */

	public double epochalNodalScale()
	{
		return _epochal / nodal();
	}
}
