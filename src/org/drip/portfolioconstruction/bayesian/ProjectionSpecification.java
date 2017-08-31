
package org.drip.portfolioconstruction.bayesian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * ProjectionSpecification contains the Black Litterman Projection Specification Settings. The References
 *  are:
 *  
 *  - He. G., and R. Litterman (1999): The Intuition behind the Black-Litterman Model Portfolios, Goldman
 *  	Sachs Asset Management
 *  
 *  - Idzorek, T. (2005): A Step-by-Step Guide to the Black-Litterman Model: Incorporating User-Specified
 *  	Confidence Levels, Ibbotson Associates, Chicago
 *
 * @author Lakshmi Krishnamurthy
 */

public class ProjectionSpecification {
	private double[][] _aadblAssetSpaceLoading = null;
	private org.drip.measure.gaussian.R1MultivariateNormal _r1mnExcessReturnsDistribution = null;

	/**
	 * ProjectionSpecification Constructor
	 * 
	 * @param r1mnExcessReturnsDistribution The R^1 Projection Space Excess Returns Normal Distribution
	 * @param aadblAssetSpaceLoading Double Array of Asset To-From Projection Portfolio Pick Weights
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ProjectionSpecification (
		final org.drip.measure.gaussian.R1MultivariateNormal r1mnExcessReturnsDistribution,
		final double[][] aadblAssetSpaceLoading)
		throws java.lang.Exception
	{
		if (null == (_r1mnExcessReturnsDistribution = r1mnExcessReturnsDistribution) || null ==
			(_aadblAssetSpaceLoading = aadblAssetSpaceLoading))
			throw new java.lang.Exception ("ProjectionSpecification Constructor => Invalid Inputs");

		int iNumProjection = _aadblAssetSpaceLoading.length;

		for (int i = 0; i < iNumProjection; ++i) {
			if (null == _aadblAssetSpaceLoading[i] || !org.drip.quant.common.NumberUtil.IsValid
				(_aadblAssetSpaceLoading[i]))
				throw new java.lang.Exception ("ProjectionSpecification Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the R^1 Projection Space Excess Returns Normal Distribution
	 * 
	 * @return The R^1 Projection Space Excess Returns Normal Distribution
	 */

	public org.drip.measure.gaussian.R1MultivariateNormal excessReturnsDistribution()
	{
		return _r1mnExcessReturnsDistribution;
	}

	/**
	 * Retrieve the Matrix of Asset To-From Projection Portfolio Pick Weights
	 * 
	 * @return The Matrix of Asset To-From Projection Portfolio Pick Weights
	 */

	public double[][] assetSpaceLoading()
	{
		return _aadblAssetSpaceLoading;
	}
}
