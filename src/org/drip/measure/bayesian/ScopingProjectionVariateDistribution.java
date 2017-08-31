
package org.drip.measure.bayesian;

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
 * ScopingProjectionVariateContainer holds the Scoping Variate Distribution, the Projection Variate
 *  Distributions, and the Projection Variate Loadings based off of the Scoping Variates.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ScopingProjectionVariateDistribution {
	private org.drip.measure.continuous.R1Multivariate _r1mScopingDistribution = null;

	private java.util.Map<java.lang.String, org.drip.measure.bayesian.ProjectionDistributionLoading> _mapPDL
		= new
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.measure.bayesian.ProjectionDistributionLoading>();

	/**
	 * ScopingProjectionVariateDistribution Constructor
	 * 
	 * @param r1mScopingDistribution The Multivariate R^1 Scoping Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ScopingProjectionVariateDistribution (
		final org.drip.measure.continuous.R1Multivariate r1mScopingDistribution)
		throws java.lang.Exception
	{
		if (null == (_r1mScopingDistribution = r1mScopingDistribution))
			throw new java.lang.Exception
				("ScopingProjectionVariateDistribution Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Scoping Distribution
	 * 
	 * @return The Scoping Distribution
	 */

	public org.drip.measure.continuous.R1Multivariate scopingDistribution()
	{
		return _r1mScopingDistribution;
	}

	/**
	 * Generate Loadings Native to the Scoping Distribution
	 * 
	 * @return  The Matrix of Loadings Native to the Scoping Distribution
	 */

	public double[][] nativeLoading()
	{
		int iNumScopingVariate = _r1mScopingDistribution.meta().numVariable();

		double[][] aadblNativeLoading = new double[iNumScopingVariate][iNumScopingVariate];

		for (int i = 0; i < iNumScopingVariate; ++i) {
			for (int j = 0; j < iNumScopingVariate; ++j)
				aadblNativeLoading[i][j] = i == j ? 1. : 0.;
		}

		return aadblNativeLoading;
	}

	/**
	 * Add the Named Projection Distribution Loading
	 * 
	 * @param strName The Projection Distribution Name
	 * @param pdl The Projection Distribution Loading
	 * 
	 * @return TRUE - The Projection Distribution Loading successfully added
	 */

	public boolean addProjectionDistributionLoading (
		final java.lang.String strName,
		final org.drip.measure.bayesian.ProjectionDistributionLoading pdl)
	{
		if (null == strName || strName.isEmpty() || null == pdl) return false;

		_mapPDL.put (strName, pdl);

		return true;
	}

	/**
	 * Retrieve the Named Projection Distribution Loading
	 * 
	 * @param strName The Projection Distribution Name
	 * 
	 * @return The Projection Distribution Loading
	 */

	public org.drip.measure.bayesian.ProjectionDistributionLoading projectionDistributionLoading (
		final java.lang.String strName)
	{
		if (null == strName || strName.isEmpty()) return null;

		if (strName.equalsIgnoreCase ("NATIVE")) {
			try {
				return new org.drip.measure.bayesian.ProjectionDistributionLoading (_r1mScopingDistribution,
					nativeLoading());
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return _mapPDL.containsKey (strName) ? _mapPDL.get (strName) : null;
	}
}
