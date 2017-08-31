
package org.drip.sequence.random;

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
 * MultivariateSequenceGenerator implements the Multivariate Random Sequence Generator Functionality.
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultivariateSequenceGenerator {
	private double[][] _aadblCholesky = null;
	private double[][] _aadblCorrelation = null;
	private org.drip.sequence.random.UnivariateSequenceGenerator[] _aUSG = null;

	/**
	 * MultivariateSequenceGenerator Constructor
	 * 
	 * @param aUSG Array of Univariate Sequence Generators
	 * @param aadblCorrelation The Correlation Matrix
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public MultivariateSequenceGenerator (
		final org.drip.sequence.random.UnivariateSequenceGenerator[] aUSG,
		final double[][] aadblCorrelation)
		throws java.lang.Exception
	{
		if (null == (_aUSG = aUSG) || null == (_aadblCorrelation = aadblCorrelation))
			throw new java.lang.Exception ("MultivariateSequenceGenerator ctr: Invalid Inputs");

		_aadblCholesky = org.drip.quant.linearalgebra.Matrix.CholeskyBanachiewiczFactorization
			(aadblCorrelation);

		int iNumVariate = aUSG.length;

		if (null == _aadblCholesky || null == _aadblCholesky[0] || iNumVariate != _aadblCholesky.length ||
			iNumVariate != _aadblCholesky[0].length)
			throw new java.lang.Exception ("MultivariateSequenceGenerator ctr: Invalid Inputs");

		for (int i = 0; i < iNumVariate; ++i) {
			if (null == _aUSG[i])
				throw new java.lang.Exception ("MultivariateSequenceGenerator ctr: Invalid Inputs");

			for (int j = 0; j < iNumVariate; ++j) {
				if (!org.drip.quant.common.NumberUtil.IsValid (_aadblCorrelation[i][j]))
					throw new java.lang.Exception ("MultivariateSequenceGenerator ctr: Invalid Inputs");
			}
		}
	}

	/**
	 * Retrieve the Array of Univariate Sequence Generators
	 * 
	 * @return Array of Univariate Sequence Generators
	 */

	public org.drip.sequence.random.UnivariateSequenceGenerator[] usg()
	{
		return _aUSG;
	}

	/**
	 * Retrieve the Correlation Matrix
	 * 
	 * @return The Correlation Matrix
	 */

	public double[][] correlation()
	{
		return _aadblCorrelation;
	}

	/**
	 * Retrieve the Cholesky Factorial
	 * 
	 * @return The Cholesky Factorial
	 */

	public double[][] cholesky()
	{
		return _aadblCholesky;
	}

	/**
	 * Retrieve the Number of Variates
	 * 
	 * @return The Number of Variates
	 */

	public int numVariate()
	{
		return _aUSG.length;
	}

	/**
	 * Generate the Set of Multivariate Random Numbers according to the specified rule
	 * 
	 * @return The Set of Multivariate Random Numbers
	 */

	public double[] random()
	{
		int iNumVariate = _aUSG.length;
		double[] adblRandom = new double[iNumVariate];
		double[] adblUncorrelatedRandom = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i)
			adblUncorrelatedRandom[i] = _aUSG[i].random();

		for (int i = 0; i < iNumVariate; ++i) {
			adblRandom[i] = 0.;

			for (int j = 0; j < iNumVariate; ++j)
				adblRandom[i] += _aadblCholesky[i][j] * adblUncorrelatedRandom[j];
		}

		return adblRandom;
	}
}
