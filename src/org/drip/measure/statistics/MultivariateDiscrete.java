
package org.drip.measure.statistics;

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
 * MultivariateDiscrete analyzes and computes the Moment and Metric Statistics for the Realized Multivariate
 *  Sequence.
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultivariateDiscrete {
	private double[] _adblMean = null;
	private double[] _adblError = null;
	private double[] _adblVariance = null;
	private double[][] _aadblCovariance = null;
	private double[][] _aadblCorrelation = null;
	private double[] _adblStandardDeviation = null;

	/**
	 * MultivariateDiscrete Constructor
	 * 
	 * @param aadblSequence The Array of Multivariate Realizations
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MultivariateDiscrete (
		final double[][] aadblSequence)
		throws java.lang.Exception
	{
		if (null == aadblSequence)
			throw new java.lang.Exception ("MultivariateDiscrete Constructor => Invalid Inputs");

		int iNumVariate = -1;
		int iSequenceSize = aadblSequence.length;

		if (0 == iSequenceSize)
			throw new java.lang.Exception ("MultivariateDiscrete Constructor => Invalid Inputs");

		for (int iSequence = 0; iSequence < iSequenceSize; ++iSequence) {
			if (null == aadblSequence[iSequence] || !org.drip.quant.common.NumberUtil.IsValid
				(aadblSequence[iSequence]))
				throw new java.lang.Exception ("MultivariateDiscrete Constructor => Invalid Inputs");

			if (0 == iSequence) {
				if (0 == (iNumVariate = aadblSequence[0].length))
					throw new java.lang.Exception ("MultivariateDiscrete Constructor => Invalid Inputs");

				_adblMean = new double[iNumVariate];
				_adblError = new double[iNumVariate];
				_adblVariance = new double[iNumVariate];
				_adblStandardDeviation = new double[iNumVariate];
				_aadblCovariance = new double[iNumVariate][iNumVariate];
				_aadblCorrelation = new double[iNumVariate][iNumVariate];

				for (int iVariate = 0; iVariate < iNumVariate; ++iVariate) {
					_adblMean[iVariate] = 0.;
					_adblError[iVariate] = 0.;

					for (int iVariateOther = 0; iVariateOther < iNumVariate; ++iVariateOther)
						_aadblCovariance[iVariate][iVariateOther] = 0.;
				}
			} else if (iNumVariate != aadblSequence[iSequence].length)
				throw new java.lang.Exception ("MultivariateDiscrete Constructor => Invalid Inputs");

			for (int iVariate = 0; iVariate < iNumVariate; ++iVariate)
				_adblMean[iVariate] += aadblSequence[iSequence][iVariate];
		}

		for (int iVariate = 0; iVariate < iNumVariate; ++iVariate)
			_adblMean[iVariate] /= iSequenceSize;

		for (int iSequence = 0; iSequence < iSequenceSize; ++iSequence) {
			for (int iVariate = 0; iVariate < iNumVariate; ++iVariate) {
				double dblOffsetFromMean = aadblSequence[iSequence][iVariate] - _adblMean[iVariate];

				_adblError[iVariate] += java.lang.Math.abs (dblOffsetFromMean);

				for (int iVariateOther = 0; iVariateOther < iNumVariate; ++iVariateOther)
					_aadblCovariance[iVariate][iVariateOther] += dblOffsetFromMean *
						(aadblSequence[iSequence][iVariateOther] - _adblMean[iVariateOther]);
			}
		}

		for (int iVariate = 0; iVariate < iNumVariate; ++iVariate) {
			_adblError[iVariate] /= iSequenceSize;

			for (int iVariateOther = 0; iVariateOther < iNumVariate; ++iVariateOther)
				_aadblCovariance[iVariate][iVariateOther] /= iSequenceSize;

			_adblStandardDeviation[iVariate] = java.lang.Math.sqrt (_adblVariance[iVariate] =
				_aadblCovariance[iVariate][iVariate]);
		}

		for (int iVariate = 0; iVariate < iNumVariate; ++iVariate) {
			for (int iVariateOther = 0; iVariateOther < iNumVariate; ++iVariateOther)
				_aadblCorrelation[iVariate][iVariateOther] = _aadblCovariance[iVariate][iVariateOther] /
					(_adblStandardDeviation[iVariate] * _adblStandardDeviation[iVariateOther]);
		}
	}

	/**
	 * Retrieve the Multivariate Means
	 * 
	 * @return The Multivariate Means
	 */

	public double[] mean()
	{
		return _adblMean;
	}

	/**
	 * Retrieve the Multivariate Sequence "Error"
	 * 
	 * @return The Multivariate Sequence "Error"
	 */

	public double[] error()
	{
		return _adblError;
	}

	/**
	 * Retrieve the Multivariate Covariance
	 * 
	 * @return The Multivariate Covariance
	 */

	public double[][] covariance()
	{
		return _aadblCovariance;
	}

	/**
	 * Retrieve the Multivariate Correlation
	 * 
	 * @return The Multivariate Correlation
	 */

	public double[][] correlation()
	{
		return _aadblCorrelation;
	}

	/**
	 * Retrieve the Multivariate Variance
	 * 
	 * @return The Multivariate Variance
	 */

	public double[] variance()
	{
		return _adblVariance;
	}

	/**
	 * Retrieve the Multivariate Standard Deviation
	 * 
	 * @return The Multivariate Standard Deviation
	 */

	public double[] standardDeviation()
	{
		return _adblStandardDeviation;
	}
}
