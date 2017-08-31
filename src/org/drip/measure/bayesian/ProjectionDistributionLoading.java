
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
 * ProjectionDistributionLoading contains the Projection Distribution and its Loadings to the Scoping
 *  Distribution.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ProjectionDistributionLoading {
	private double[][] _aadblScopingLoading = null;
	private org.drip.measure.continuous.R1Multivariate _r1mDistribution = null;

	/**
	 * Generate the Projection Co-variance Matrix from the Confidence Level
	 * 
	 * @param aadblScopingCovariance The Scoping Co-variance Matrix
	 * @param aadblScopingLoading The Projection-Scoping Variate Loadings
	 * @param dblTau The Tau Parameter
	 * 
	 * @return The Projection Co-variance Matrix
	 */

	public static final double[][] ProjectionCovariance (
		final double[][] aadblScopingCovariance,
		final double[][] aadblScopingLoading,
		final double dblTau)
	{
		if (null == aadblScopingCovariance || null == aadblScopingLoading ||
			!org.drip.quant.common.NumberUtil.IsValid (dblTau))
			return null;

		int iNumProjection = aadblScopingLoading.length;
		double[][] aadblProjectionCovariance = 0 == iNumProjection ? null : new
			double[iNumProjection][iNumProjection];

		if (0 == iNumProjection || iNumProjection != aadblScopingLoading.length) return null;

		for (int i = 0; i < iNumProjection; ++i) {
			for (int j = 0; j < iNumProjection; ++j) {
				try {
					aadblProjectionCovariance[i][j] = i != j ? 0. : dblTau *
						org.drip.quant.linearalgebra.Matrix.DotProduct (aadblScopingLoading[i],
							org.drip.quant.linearalgebra.Matrix.Product (aadblScopingCovariance,
								aadblScopingLoading[j]));
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		return aadblProjectionCovariance;
	}

	/**
	 * Generate the ProjectionDistributionLoading Instance from the Confidence Level
	 * 
	 * @param meta The R^1 Multivariate Meta Headers
	 * @param adblMean Array of the Univariate Means
	 * @param aadblScopingCovariance The Scoping Co-variance Matrix
	 * @param aadblScopingLoading The Projection-Scoping Variate Loadings
	 * @param dblTau The Tau Parameter
	 * 
	 * @return The ProjectionDistributionLoading Instance
	 */

	public static final ProjectionDistributionLoading FromConfidence (
		final org.drip.measure.continuous.MultivariateMeta meta,
		final double[] adblMean,
		final double[][] aadblScopingCovariance,
		final double[][] aadblScopingLoading,
		final double dblTau)
	{
		try {
			return new ProjectionDistributionLoading (new org.drip.measure.gaussian.R1MultivariateNormal
				(meta, adblMean, new org.drip.measure.gaussian.Covariance (ProjectionCovariance
					(aadblScopingCovariance, aadblScopingLoading, dblTau))), aadblScopingLoading);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ProjectionDistributionLoading Constructor
	 * 
	 * @param r1mDistribution The Projection Distribution Instance
	 * @param aadblScopingLoading The Projection-Scoping Variate Loadings
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ProjectionDistributionLoading (
		final org.drip.measure.continuous.R1Multivariate r1mDistribution,
		final double[][] aadblScopingLoading)
		throws java.lang.Exception
	{
		if (null == (_r1mDistribution = r1mDistribution) || null == (_aadblScopingLoading =
			aadblScopingLoading))
			throw new java.lang.Exception ("ProjectionDistributionLoading Constructor => Invalid Inputs!");

		int iNumProjectionView = _r1mDistribution.meta().numVariable();

		if (iNumProjectionView != _aadblScopingLoading.length)
			throw new java.lang.Exception ("ProjectionDistributionLoading Constructor => Invalid Inputs!");

		for (int i = 0; i < iNumProjectionView; ++i) {
			if (null == _aadblScopingLoading[i] || 0 == _aadblScopingLoading[i].length ||
				!org.drip.quant.common.NumberUtil.IsValid (_aadblScopingLoading[i]))
				throw new java.lang.Exception
					("ProjectionDistributionLoading Constructor => Invalid Inputs!");
		}
	}

	/**
	 * Retrieve the Projection Distribution
	 * 
	 * @return The Projection Distribution
	 */

	public org.drip.measure.continuous.R1Multivariate distribution()
	{
		return _r1mDistribution;
	}

	/**
	 * Retrieve the Matrix of the Scoping Loadings
	 * 
	 * @return The Matrix of the Scoping Loadings
	 */

	public double[][] scopingLoading()
	{
		return _aadblScopingLoading;
	}

	/**
	 * Retrieve the Number of the Projection Variates
	 * 
	 * @return The Number of the Projection Variates
	 */

	public int numberOfProjectionVariate()
	{
		return _aadblScopingLoading.length;
	}

	/**
	 * Retrieve the Number of the Scoping Variate
	 * 
	 * @return The Number of the Scoping Variate
	 */

	public int numberOfScopingVariate()
	{
		return _aadblScopingLoading[0].length;
	}
}
