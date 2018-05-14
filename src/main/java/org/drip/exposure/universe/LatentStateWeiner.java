
package org.drip.exposure.universe;

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
 * LatentStateWeiner generates the Edge Latent State Weiner Increments across Trajectory Vertexes needed for
 *  computing the Valuation Adjustment. The References are:<br><br>
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

public class LatentStateWeiner
{
	private java.util.Map<java.lang.String, double[]> _latentStateWeinerMap = new
		org.drip.analytics.support.CaseInsensitiveHashMap<double[]>();

	/**
	 * Construct an Instance of LatentStateWeiner from the Arrays of Latent State and their Weiner Increments
	 * 
	 * @param latentStateLabelList Latent State Label List
	 * @param latentStateWeinerIncrementArray Latent State Weiner Increment Array
	 * 
	 * @return Instance of LatentStateWeiner
	 */

	public static final LatentStateWeiner FromUnitRandom (
		final java.util.List<org.drip.state.identifier.LatentStateLabel> latentStateLabelList,
		final double[][] latentStateWeinerIncrementArray)
	{
		if (null == latentStateLabelList || null == latentStateWeinerIncrementArray)
		{
			return null;
		}

		int latentStateCount = latentStateLabelList.size();

		if (0 == latentStateCount || latentStateCount != latentStateWeinerIncrementArray.length)
		{
			return null;
		}

		LatentStateWeiner latentStateWeiner = new LatentStateWeiner();

		for (int latentStateIndex = 0; latentStateIndex < latentStateCount; ++latentStateIndex)
		{
			if (!latentStateWeiner.add (
				latentStateLabelList.get (latentStateIndex),
				latentStateWeinerIncrementArray[latentStateIndex]))
			{
				return null;
			}
		}

		return latentStateWeiner;
	}

	/**
	 * Empty LatentStateWeiner Constructor
	 */

	public LatentStateWeiner()
	{
	}

	/**
	 * Retrieve the Count of the Latent States Available
	 * 
	 * @return The Count of the Latent States Available
	 */

	public int stateCount()
	{
		return _latentStateWeinerMap.size();
	}

	/**
	 * Add the Weiner Increment corresponding to the Specified Latent State Label
	 * 
	 * @param latentStateLabel The Latent State Label
	 * @param weinerIncrementArray The Weiner Increment Array
	 * 
	 * @return TRUE -The Weiner Increment corresponding to the Specified Latent State Label
	 */

	public boolean add (
		final org.drip.state.identifier.LatentStateLabel latentStateLabel,
		final double[] weinerIncrementArray)
	{
		if (null == latentStateLabel ||
			null == weinerIncrementArray ||
			0 == weinerIncrementArray.length ||
			!org.drip.quant.common.NumberUtil.IsValid (weinerIncrementArray))
		{
			return false;
		}

		_latentStateWeinerMap.put (
			latentStateLabel.fullyQualifiedName(),
			weinerIncrementArray
		);

		return true;
	}

	/**
	 * Retrieve the Latent State Weiner Increment Map
	 * 
	 * @return The Latent State Weiner Increment Map
	 */

	public java.util.Map<java.lang.String, double[]> latentStateWeinerMap()
	{
		return _latentStateWeinerMap;
	}

	/**
	 * Indicate if the specified Latent State is available in the Weiner Increment Map
	 * 
	 * @param latentStateLabel Latent State Label
	 * 
	 * @return TRUE - The specified Latent State is available in the Weiner Increment Map
	 */

	public boolean containsLatentState (
		final org.drip.state.identifier.LatentStateLabel latentStateLabel)
	{
		return null != latentStateLabel && _latentStateWeinerMap.containsKey
			(latentStateLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Weiner Increment Array for the Specified Latent State
	 * 
	 * @param latentStateLabel Latent State Label
	 * 
	 * @return The Weiner Increment Array for the Specified Latent State
	 */

	public double[] incrementArray (
		final org.drip.state.identifier.LatentStateLabel latentStateLabel)
	{
		return containsLatentState (latentStateLabel) ? _latentStateWeinerMap.get
			(latentStateLabel.fullyQualifiedName()) : null;
	}
}
