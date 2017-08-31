
package org.drip.measure.continuous;

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
 * R1Multivariate contains the Generalized Joint Multivariate R^1 Distributions.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class R1Multivariate {
	private org.drip.measure.continuous.MultivariateMeta _meta = null;

	protected R1Multivariate (
		final org.drip.measure.continuous.MultivariateMeta meta)
		throws java.lang.Exception
	{
		if (null == (_meta = meta))
			throw new java.lang.Exception ("R1Multivariate Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Multivariate Meta Instance
	 * 
	 * @return The Multivariate Meta Instance
	 */

	public org.drip.measure.continuous.MultivariateMeta meta()
	{
		return _meta;
	}

	/**
	 * Retrieve the Left Edge Bounding Multivariate
	 * 
	 * @return The Left Edge Bounding Multivariate
	 */

	public double[] leftEdge()
	{
		int iNumVariate = _meta.numVariable();

		double[] adblLeftEdge = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i)
			adblLeftEdge[i] = java.lang.Double.MIN_NORMAL;

		return adblLeftEdge;
	}

	/**
	 * Retrieve the Right Edge Bounding Multivariate
	 * 
	 * @return The Right Edge Bounding Multivariate
	 */

	public double[] rightEdge()
	{
		int iNumVariate = _meta.numVariable();

		double[] adblRightEdge = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i)
			adblRightEdge[i] = java.lang.Double.MAX_VALUE;

		return adblRightEdge;
	}

	/**
	 * Compute the Density under the Distribution at the given Multivariate
	 * 
	 * @param adblVariate The Multivariate at which the Density needs to be computed
	 * 
	 * @return The Density
	 * 
	 * @throws java.lang.Exception Thrown if the Density cannot be computed
	 */

	public abstract double density (
		final double[] adblVariate)
		throws java.lang.Exception;

	/**
	 * Convert the Multivariate Density into an RdToR1 Functions Instance
	 * 
	 * @return The Multivariate Density converted into an RdToR1 Functions Instance
	 */

	public org.drip.function.definition.RdToR1 densityRdToR1()
	{
		return new org.drip.function.definition.RdToR1 (null) {
			@Override public int dimension()
			{
				return _meta.numVariable();
			}

			@Override public double evaluate (
				final double[] adblVariate)
				throws java.lang.Exception
			{
				return density (adblVariate);
			}
		};
	}

	/**
	 * Compute the Cumulative under the Distribution to the given Variate Values
	 * 
	 * @param adblVariate Array of Variate Values to which the Cumulative is to be computed
	 * 
	 * @return The Cumulative
	 * 
	 * @throws java.lang.Exception Thrown if the Cumulative cannot be computed
	 */

	public double cumulative (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		return densityRdToR1().integrate (leftEdge(), adblVariate);
	}

	/**
	 * Compute the Incremental under the Distribution between the 2 Multivariate Instances
	 * 
	 * @param adblVariateLeft Left Multivariate Instance to which the Cumulative is to be computed
	 * @param adblVariateRight Right Multivariate Instance to which the Cumulative is to be computed
	 * 
	 * @return The Incremental
	 * 
	 * @throws java.lang.Exception Thrown if the Incremental cannot be computed
	 */

	public double incremental (
		final double[] adblVariateLeft,
		final double[] adblVariateRight)
		throws java.lang.Exception
	{
		return densityRdToR1().integrate (adblVariateLeft, adblVariateRight);
	}

	/**
	 * Compute the Expectation of the Specified R^d To R^1 Function Instance
	 * 
	 * @param funcRdToR1 The R^d To R^1 Function Instance
	 * 
	 * @return The Expectation of the Specified R^d To R^1 Function Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double expectation (
		final org.drip.function.definition.RdToR1 funcRdToR1)
		throws java.lang.Exception
	{
		if (null == funcRdToR1)
			throw new java.lang.Exception ("R1Multivariate::expectation => Invalid Inputs");

		return new org.drip.function.definition.RdToR1 (null) {
			@Override public int dimension()
			{
				return _meta.numVariable();
			}

			@Override public double evaluate (
				final double[] adblVariate)
				throws java.lang.Exception
			{
				return density (adblVariate) * funcRdToR1.evaluate (adblVariate);
			}
		}.integrate (leftEdge(), rightEdge());
	}

	/**
	 * Compute the Mean of the Distribution
	 * 
	 * @return The Mean of the Distribution
	 */

	public double[] mean()
	{
		int iNumVariate = _meta.numVariable();

		double[] adblMean = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i) {
			final int iVariate = i;

			try {
				adblMean[i] = expectation (new org.drip.function.definition.RdToR1 (null) {
					@Override public int dimension()
					{
						return _meta.numVariable();
					}

					@Override public double evaluate (
						final double[] adblVariate)
						throws java.lang.Exception
					{
						return density (adblVariate) * adblVariate[iVariate];
					}
				});
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblMean;
	}

	/**
	 * Compute the Variance of the Distribution
	 * 
	 * @return The Variance of the Distribution
	 */

	public double[] variance()
	{
		final double[] adblMean = mean();

		if (null == adblMean) return null;

		final int iNumVariate = adblMean.length;
		double[] adblVariance = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i) {
			final int iVariate = i;

			try {
				adblVariance[i] = expectation (new org.drip.function.definition.RdToR1 (null) {
					@Override public int dimension()
					{
						return _meta.numVariable();
					}

					@Override public double evaluate (
						final double[] adblVariate)
						throws java.lang.Exception
					{
						double dblSecondMoment = 0.;

						for (int i = 0; i < iNumVariate; ++i) {
							double dblOffset = adblVariate[iVariate] - adblMean[iVariate];
							dblSecondMoment = dblSecondMoment + dblOffset * dblOffset;
						}

						return density (adblVariate) * dblSecondMoment;
					}
				});
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblVariance;
	}
}
