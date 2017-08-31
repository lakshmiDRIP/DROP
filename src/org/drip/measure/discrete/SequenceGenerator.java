
package org.drip.measure.discrete;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * SequenceGenerator generates the specified Univariate Sequence of the Given Distribution Type.
 *
 * @author Lakshmi Krishnamurthy
 */

public class SequenceGenerator {

	/**
	 * Generate a Sequence of Uniform Random Numbers
	 * 
	 * @param iCount The Count in the Sequence
	 * 
	 * @return The Sequence of Uniform Random Numbers
	 */

	public static final double[] Uniform (
		final int iCount)
	{
		if (0 >= iCount) return null;

		double[] adblRandom = new double[iCount];

		for (int i = 0; i < iCount; ++i)
			adblRandom[i] = java.lang.Math.random();

		return adblRandom;
	}

	/**
	 * Generate a Sequence of Gaussian Random Numbers
	 * 
	 * @param iCount The Count in the Sequence
	 * 
	 * @return The Sequence of Gaussian Random Numbers
	 */

	public static final double[] Gaussian (
		final int iCount)
	{
		if (0 >= iCount) return null;

		double[] adblRandom = new double[iCount];

		for (int i = 0; i < iCount; ++i) {
			try {
				adblRandom[i] = org.drip.measure.gaussian.NormalQuadrature.Random();
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblRandom;
	}

	/**
	 * Generate a Sequence of Log Normal Random Numbers
	 * 
	 * @param iCount The Count in the Sequence
	 * 
	 * @return The Sequence of Log Normal Random Numbers
	 */

	public static final double[] LogNormal (
		final int iCount)
	{
		if (0 >= iCount) return null;

		double[] adblRandom = new double[iCount];

		double dblNormalizer = 1. / java.lang.Math.sqrt (java.lang.Math.E);

		for (int i = 0; i < iCount; ++i) {
			try {
				adblRandom[i] = java.lang.Math.exp (org.drip.measure.gaussian.NormalQuadrature.Random()) *
					dblNormalizer;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblRandom;
	}

	/**
	 * Generate a Sequence of R^d Correlated Gaussian Random Numbers
	 * 
	 * @param iCount The Count in the Sequence
	 * @param aadblCorrelation The Correlation Matrix
	 * 
	 * @return The Sequence of R^d Correlated Gaussian Random Numbers
	 */

	public static final double[][] GaussianJoint (
		final int iCount,
		final double[][] aadblCorrelation)
	{
		if (0 >= iCount) return null;

		double[][] aadblCholesky = org.drip.quant.linearalgebra.Matrix.CholeskyBanachiewiczFactorization
			(aadblCorrelation);

		if (null == aadblCholesky) return null;

		int iDimension = aadblCholesky.length;
		double[][] aadblRandom = new double[iCount][];

		for (int k = 0; k < iCount; ++k) {
			double[] adblUncorrelatedRandom = Gaussian (iDimension);

			if (null == adblUncorrelatedRandom || iDimension != adblUncorrelatedRandom.length) return null;

			double[] adblCorrelatedRandom = new double[iDimension];

			for (int i = 0; i < iDimension; ++i) {
				adblCorrelatedRandom[i] = 0.;

				for (int j = 0; j < iDimension; ++j)
					adblCorrelatedRandom[i] += aadblCholesky[i][j] * adblUncorrelatedRandom[j];
			}

			aadblRandom[k] = adblCorrelatedRandom;
		}

		return aadblRandom;
	}
}
