
package org.drip.xva.universe;

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
 * LatentStateEvolverContainer holds the Latent States and their Jump Diffusion Evolvers. The References
 * 	are:<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2013): Funding Strategies, Funding Costs <i>Risk</i> <b>24 (12)</b>
 *  	82-87.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): PDE Representations of Derivatives with Bilateral Counter-party Risk
 *  	and Funding Costs <i>Journal of Credit Risk</i> <b>7 (3)</b> 1-19.<br><br>
 *  
 *  - Burgard, C., and M. Kjaer (2014): In the Balance <i>Risk</i> <b>24 (11)</b> 72-75.<br><br>
 *  
 *  - Gregory, J. (2009): Being Two-faced over Counter-party Credit Risk <i>Risk</i> <b>20 (2)</b>
 *  	86-90.<br><br>
 * 
 *  - Piterbarg, V. (2010): Funding Beyond Discounting: Collateral Agreements and Derivatives Pricing
 *  	<i>Risk</i> <b>21 (2)</b> 97-102.<br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateEvolverContainer
{
	private java.util.Map<java.lang.String, org.drip.xva.universe.LatentStateEvolver> _evolverMap =
		new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.xva.universe.LatentStateEvolver>();

	/**
	 * Empty LatentStateEvolverContainer Constructor
	 */

	public LatentStateEvolverContainer()
	{
	}

	/**
	 * Retrieve the Jump Diffusion Evolver Map
	 * 
	 * @return The Jump Diffusion Evolver Map
	 */

	public java.util.Map<java.lang.String, org.drip.xva.universe.LatentStateEvolver> evolverMap()
	{
		return _evolverMap;
	}

	/**
	 * Add a Latent State Jump Diffusion Evolver
	 * 
	 * @param latentStateEvolver The Latent State Jump Diffusion Evolver
	 * 
	 * @return TRUE - The Latent State Evolver successfully added
	 */

	public boolean addEvolver (
		final org.drip.xva.universe.LatentStateEvolver latentStateEvolver)
	{
		if (null == latentStateEvolver)
		{
			return false;
		}

		_evolverMap.put (
			latentStateEvolver.label().fullyQualifiedName(),
			latentStateEvolver
		);

		return true;
	}

	/**
	 * Indicate if the Latent State Jump Diffusion Evolver exists
	 * 
	 * @param label The Latent State Label
	 * 
	 * @return TRUE - The Latent State Evolver exists
	 */

	public boolean containsEvolver (
		final org.drip.state.identifier.LatentStateLabel label)
	{
		return null == label ? false : _evolverMap.containsKey (label.fullyQualifiedName());
	}

	/**
	 * Retrieve the Latent State Jump Diffusion Evolver given the Label
	 * 
	 * @param label The Latent State Label
	 * 
	 * @return The Latent State Evolver from the Label
	 */

	public org.drip.xva.universe.LatentStateEvolver evolver (
		final org.drip.state.identifier.LatentStateLabel label)
	{
		return containsEvolver (label) ? _evolverMap.get (label.fullyQualifiedName()) : null;
	}
}
