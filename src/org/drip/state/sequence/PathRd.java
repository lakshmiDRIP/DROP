
package org.drip.state.sequence;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * PathRd exposes the Functionality to generate a Sequence of the Path Vertex Latent State R^d Realizations
 * 	across Multiple Paths.
 *
 * @author Lakshmi Krishnamurthy
 */

public class PathRd {
	private double[] _adblMean = null;
	private boolean _bLogNormal = false;
	private double _dblVolatility = java.lang.Double.NaN;

	/**
	 * PathRd Constructor
	 * 
	 * @param adblMean Array of Mean
	 * @param dblVolatility Volatility
	 * @param bLogNormal TRUE - The Generated Random Numbers are Log Normal
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public PathRd (
		final double[] adblMean,
		final double dblVolatility,
		final boolean bLogNormal)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_adblMean = adblMean) || null == _adblMean || 0 ==
			_adblMean.length || !org.drip.quant.common.NumberUtil.IsValid (_dblVolatility = dblVolatility) ||
				0. >= _dblVolatility)
			throw new java.lang.Exception ("PathRd Constructor => Invalid Inputs");

		_bLogNormal = bLogNormal;
	}

	/**
	 * Indicate if the Random Numbers are Gaussian/LogNormal
	 * 
	 * @return TRUE - The Generated Random Numbers are Log Normal
	 */

	public boolean logNormal()
	{
		return _bLogNormal;
	}

	/**
	 * Retrieve the R^d Dimension
	 * 
	 * @return The R^d Dimension
	 */

	public int dimension()
	{
		return _adblMean.length;
	}

	/**
	 * Retrieve the Array of Means
	 * 
	 * @return The Array of Means
	 */

	public double[] mean()
	{
		return _adblMean;
	}

	/**
	 * Retrieve the Volatility
	 * 
	 * @return The Volatility
	 */

	public double volatility()
	{
		return _dblVolatility;
	}

	/**
	 * Generate the Sequence of Path Realizations
	 * 
	 * @param iNumPath Number of Paths
	 * 
	 * @return The Sequence of Path Realizations
	 */

	public double[][] sequence (
		final int iNumPath)
	{
		if (0 >= iNumPath) return null;

		int iNumDimension = _adblMean.length;
		double[][] aadblSequence = new double[iNumPath][iNumDimension];

		for (int iPath = 0; iPath < iNumPath; ++iPath) {
			double[] adblRandom = org.drip.measure.discrete.SequenceGenerator.Gaussian (iNumDimension);

			if (null == adblRandom || iNumDimension != adblRandom.length) return null;

			for (int iDimension = 0; iDimension < iNumDimension; ++iDimension) {
				double dblWander = _dblVolatility * adblRandom[iDimension];

				aadblSequence[iPath][iDimension] = _bLogNormal ? _adblMean[iDimension] * java.lang.Math.exp
					(dblWander) : _adblMean[iDimension] + dblWander;
			}
		}

		return aadblSequence;
	}
}
