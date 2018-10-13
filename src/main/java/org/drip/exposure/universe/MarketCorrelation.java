
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
 * MarketCorrelation holds the Cross Latent State Correlations needed for computing the Valuation Adjustment.
 *  The References are:<br><br>
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

public class MarketCorrelation
{
	private double[][] _matrix = null;
	private java.util.List<org.drip.state.identifier.LatentStateLabel> _latentStateLabelList = null;

	private java.util.Map<java.lang.String, java.lang.Integer> _latentStateIndexMap = new
		java.util.HashMap<java.lang.String, java.lang.Integer>();

	/**
	 * MarketCorrelation Constructor
	 * 
	 * @param latentStateLabelList The Latent State Label List
	 * @param matrix The Square Correlation Matrix
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarketCorrelation (
		final java.util.List<org.drip.state.identifier.LatentStateLabel> latentStateLabelList,
		final double[][] matrix)
		throws java.lang.Exception
	{
		if (null == (_latentStateLabelList = latentStateLabelList) ||
			null == (_matrix = matrix))
		{
			throw new java.lang.Exception ("MarketCorrelation Constructor => Invalid Inputs");
		}

		int latentStateCount = _latentStateLabelList.size();

		if (0 == latentStateCount || latentStateCount != _matrix.length)
		{
			throw new java.lang.Exception ("MarketCorrelation Constructor => Invalid Inputs");
		}

		for (int latentStateIndex = 0; latentStateIndex < latentStateCount; ++latentStateIndex)
		{
			org.drip.state.identifier.LatentStateLabel latentStateLabel = _latentStateLabelList.get
				(latentStateIndex);

			if (null == latentStateLabel ||
				null == _matrix[latentStateIndex] ||
				latentStateCount != _matrix[latentStateIndex].length)
			{
				throw new java.lang.Exception ("MarketCorrelation Constructor => Invalid Inputs");
			}

			for (int matrixColumnIndex = 0; matrixColumnIndex < latentStateCount; ++matrixColumnIndex)
			{
				double correlationEntry = _matrix[latentStateIndex][matrixColumnIndex];

				if (!org.drip.quant.common.NumberUtil.IsValid (correlationEntry) ||
					(latentStateIndex == matrixColumnIndex && 1 != correlationEntry) ||
					(latentStateIndex != matrixColumnIndex && (1 < correlationEntry ||
						-1 < correlationEntry)))
				{
					throw new java.lang.Exception ("MarketCorrelation Constructor => Invalid Inputs");
				}
			}

			java.lang.String latentStateLabelFQN = latentStateLabel.fullyQualifiedName();

			if (_latentStateIndexMap.containsKey (latentStateLabelFQN))
			{
				throw new java.lang.Exception ("MarketCorrelation Constructor => Invalid Inputs");
			}

			_latentStateIndexMap.put (
				latentStateLabelFQN,
				latentStateIndex
			);
		}
	}

	/**
	 * Retrieve the Latent State Label List
	 * 
	 * @return The Latent State Label List
	 */

	public java.util.List<org.drip.state.identifier.LatentStateLabel> latentStateLabelList()
	{
		return _latentStateLabelList;
	}

	/**
	 * Retrieve the Cross-Latent State Correlation Matrix
	 * 
	 * @return The Cross-Latent State Correlation Matrix
	 */

	public double[][] matrix()
	{
		return _matrix;
	}

	/**
	 * Check if the Latent State  is available in the Correlation Matrix
	 * 
	 * @param latentStateLabel The Latent State Label
	 * 
	 * @return TRUE - The Latent State  is available in the Correlation Matrix
	 */

	public boolean latentStateExists (
		final org.drip.state.identifier.LatentStateLabel latentStateLabel)
	{
		return null != latentStateLabel && _latentStateIndexMap.containsKey
			(latentStateLabel.fullyQualifiedName());
	}

	/**
	 * Retrieve the Cross State Correlation
	 * 
	 * @param latentStateLabel1 Latent State Label #1
	 * @param latentStateLabel2 Latent State Label #2
	 * 
	 * @return The Cross State Correlation
	 */

	public double entry (
		final org.drip.state.identifier.LatentStateLabel latentStateLabel1,
		final org.drip.state.identifier.LatentStateLabel latentStateLabel2)
	{
		return !latentStateExists (latentStateLabel1) || !latentStateExists (latentStateLabel2) ? 0. :
			_matrix[_latentStateIndexMap.get (latentStateLabel1.fullyQualifiedName())]
				[_latentStateIndexMap.get (latentStateLabel2.fullyQualifiedName())];
	}

	/**
	 * Synthesize a MarketCorrelation Instance for the Custom Latent State List
	 * 
	 * @param customLatentStateLabelList The Custom Latent State List
	 * 
	 * @return The MarketCorrelation Instance for the Custom Latent State List
	 */

	public MarketCorrelation customMarketCorrelation (
		final java.util.List<org.drip.state.identifier.LatentStateLabel> customLatentStateLabelList)
	{
		if (null == customLatentStateLabelList)
		{
			return null;
		}

		int customLatentStateCount = customLatentStateLabelList.size();

		if (0 == customLatentStateCount)
		{
			return null;
		}

		double[][] customMatix = new double[customLatentStateCount][customLatentStateCount];

		for (int customLatentStateIndexOuter = 0; customLatentStateIndexOuter < customLatentStateCount;
			++customLatentStateIndexOuter)
		{
			for (int customLatentStateIndexInner = 0; customLatentStateIndexInner < customLatentStateCount;
				++customLatentStateIndexInner)
			{
				customMatix[customLatentStateIndexOuter][customLatentStateIndexInner] =
					customLatentStateIndexOuter == customLatentStateIndexInner ? 1. :
					entry (
						customLatentStateLabelList.get (customLatentStateIndexOuter),
						customLatentStateLabelList.get (customLatentStateIndexInner)
					);
			}
		}

		try
		{
			return new MarketCorrelation (
				customLatentStateLabelList,
				customMatix
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
