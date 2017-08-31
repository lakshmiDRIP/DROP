
package org.drip.measure.gaussian;

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
 * Covariance holds the Standard Covariance Matrix, and provides functions to manipulate it.
 *
 * @author Lakshmi Krishnamurthy
 */

public class Covariance {
	private double[][] _aadblPrecision = null;
	private double[][] _aadblCovariance = null;

	/**
	 * Covariance Constructor
	 * 
	 * @param aadblCovariance Double Array of the Covariance Matrix
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Covariance (
		final double[][] aadblCovariance)
		throws java.lang.Exception
	{
		if (null == (_aadblCovariance = aadblCovariance))
			throw new java.lang.Exception ("Covariance Constructor => Invalid Inputs!");

		int iNumVariate = _aadblCovariance.length;

		if (0 == iNumVariate)
			throw new java.lang.Exception ("R1MultivariateNormal Constructor => Invalid Inputs!");

		for (int i = 0; i < iNumVariate; ++i) {
			if (null == _aadblCovariance[i] || iNumVariate != _aadblCovariance[i].length ||
				!org.drip.quant.common.NumberUtil.IsValid (_aadblCovariance[i]))
				throw new java.lang.Exception ("R1MultivariateNormal Constructor => Invalid Inputs!");
		}

		if (null == (_aadblPrecision = org.drip.quant.linearalgebra.Matrix.InvertUsingGaussianElimination
			(_aadblCovariance)))
			throw new java.lang.Exception ("R1MultivariateNormal Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Number of Variates
	 * 
	 * @return The Number of Variates
	 */

	public int numVariate()
	{
		return _aadblCovariance.length;
	}

	/**
	 * Retrieve the Covariance Matrix
	 * 
	 * @return The Covariance Matrix
	 */

	public double[][] covarianceMatrix()
	{
		return _aadblCovariance;
	}

	/**
	 * Retrieve the Precision Matrix
	 * 
	 * @return The Precision Matrix
	 */

	public double[][] precisionMatrix()
	{
		return _aadblPrecision;
	}

	/**
	 * Retrieve the Variance Array
	 * 
	 * @return The Variance Array
	 */

	public double[] variance()
	{
		int iNumVariate = _aadblCovariance.length;
		double[] adblVariance = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i)
			adblVariance[i] = _aadblCovariance[i][i];

		return adblVariance;
	}

	/**
	 * Retrieve the Volatility Array
	 * 
	 * @return The Volatility Array
	 */

	public double[] volatility()
	{
		int iNumVariate = _aadblCovariance.length;
		double[] adblVolatility = new double[iNumVariate];

		for (int i = 0; i < iNumVariate; ++i)
			adblVolatility[i] = java.lang.Math.sqrt (_aadblCovariance[i][i]);

		return adblVolatility;
	}

	/**
	 * Retrieve the Correlation Matrix
	 * 
	 * @return The Correlation Matrix
	 */

	public double[][] correlationMatrix()
	{
		int iNumVariate = _aadblCovariance.length;
		double[][] aadblCorrelation = new double[iNumVariate][iNumVariate];

		double[] adblVolatility = volatility();

		for (int i = 0; i < iNumVariate; ++i) {
			for (int j = 0; j < iNumVariate; ++j)
				aadblCorrelation[i][j] = _aadblCovariance[i][j] / (adblVolatility[i] * adblVolatility[j]);
		}

		return aadblCorrelation;
	}
}
