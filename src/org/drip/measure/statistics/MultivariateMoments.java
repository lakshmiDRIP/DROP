
package org.drip.measure.statistics;

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
 * MultivariateMoments generates and holds the Specified Multivariate Series Mean, Co-variance, and other
 *  selected Moments.
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultivariateMoments {
	private org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> _mapMean = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	private org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> _mapCovariance = new
		org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

	/**
	 * Generate the MultivariateMetrics Instance from the Series Realizations provided
	 * 
	 * @param astrVariateName Array of Variate Name Headers
	 * @param aadblVariate Array of Variate Realization Arrays
	 * 
	 * @return The MultivariateMetrics Instance
	 */

	public static final MultivariateMoments Standard (
		final java.lang.String[] astrVariateName,
		final double[][] aadblVariate)
	{
		if (null == astrVariateName || null == aadblVariate) return null;

		int iNumVariate = astrVariateName.length;
		double[] adblMean = new double[iNumVariate];

		if (0 == iNumVariate || iNumVariate != aadblVariate.length) return null;

		int iNumSample = aadblVariate[0].length;

		if (0 == iNumSample) return null;

		MultivariateMoments mvm = new MultivariateMoments();

		for (int i = 0; i < iNumVariate; ++i) {
			adblMean[i] = 0.;
			double[] adblVariateSample = aadblVariate[i];

			if (null == adblVariateSample || adblVariateSample.length != iNumSample) return null;

			for (int k = 0; k < iNumSample; ++k) {
				if (!org.drip.quant.common.NumberUtil.IsValid (adblVariateSample[k])) return null;

				adblMean[i] += adblVariateSample[k];
			}

			if (!mvm.addMean (astrVariateName[i], adblMean[i] /= iNumSample)) return null;
		}

		for (int i = 0; i < iNumVariate; ++i) {
			for (int j = 0; j < iNumVariate; ++j) {
				double dblCovariance = 0.;

				for (int k = 0; k < iNumSample; ++k)
					dblCovariance += (aadblVariate[i][k] - adblMean[i]) * (aadblVariate[j][k] - adblMean[j]);

				if (!mvm.addCovariance (astrVariateName[i], astrVariateName[j], dblCovariance / iNumSample))
					return null;
			}
		}

		return mvm;
	}

	/**
	 * Generate the MultivariateMetrics Instance from the Specified Mean and Co-variance Inputs
	 * 
	 * @param astrVariateName Array of Variate Name Headers
	 * @param adblMean Array of Variate Means
	 * @param aadblCovariance Double Array of the Variate Co-variance
	 * 
	 * @return The MultivariateMetrics Instance
	 */

	public static final MultivariateMoments Standard (
		final java.lang.String[] astrVariateName,
		final double[] adblMean,
		final double[][] aadblCovariance)
	{
		if (null == astrVariateName || null == adblMean || null == aadblCovariance) return null;

		int iNumVariate = astrVariateName.length;

		if (0 == iNumVariate || iNumVariate != adblMean.length || iNumVariate != aadblCovariance.length ||
			null == aadblCovariance[0] || iNumVariate != aadblCovariance[0].length)
			return null;

		MultivariateMoments mvm = new MultivariateMoments();

		for (int i = 0; i < iNumVariate; ++i) {
			if (!mvm.addMean (astrVariateName[i], adblMean[i])) return null;
		}

		for (int i = 0; i < iNumVariate; ++i) {
			for (int j = 0; j < iNumVariate; ++j) {
				if (!mvm.addCovariance (astrVariateName[i], astrVariateName[j], aadblCovariance[i][j]))
					return null;
			}
		}

		return mvm;
	}

	protected MultivariateMoments()
	{
	}

	/**
	 * Retrieve the Number of Variates in the Distribution
	 * 
	 * @return The Number of Variates in the Distribution
	 */

	public int numVariate()
	{
		return _mapMean.size();
	}

	/**
	 * Retrieve the Variates for which the Metrics are available
	 * 
	 * @return The Set of Variates
	 */

	public java.util.Set<java.lang.String> variateList()
	{
		return _mapMean.keySet();
	}

	/**
	 * Add the Mean for the Named Variate
	 * 
	 * @param strVariateName The Named Variate
	 * @param dblMean The Variate Mean
	 * 
	 * @return TRUE - The Variate Mean successfully added
	 */

	public boolean addMean (
		final java.lang.String strVariateName,
		final double dblMean)
	{
		if (null == strVariateName || strVariateName.isEmpty() || !org.drip.quant.common.NumberUtil.IsValid
			(dblMean))
			return false;

		_mapMean.put (strVariateName, dblMean);

		return true;
	}

	/**
	 * Retrieve the Mean of the Named Variate
	 * 
	 * @param strVariateName The Named Variate
	 * 
	 * @return Mean of the Named Variate
	 * 
	 * @throws java.lang.Exception Thrown if the Named Variate Mean cannot be retrieved
	 */

	public double mean (
		final java.lang.String strVariateName)
		throws java.lang.Exception
	{
		if (null == strVariateName || strVariateName.isEmpty() || !_mapMean.containsKey (strVariateName))
			throw new java.lang.Exception ("MultivariateMetrics::mean => Invalid Inputs");

		return _mapMean.get (strVariateName);
	}

	/**
	 * Add the Co-variance for the Named Variate Pair
	 * 
	 * @param strVariate1Name The Named Variate #1
	 * @param strVariate2Name The Named Variate #2
	 * @param dblCovariance The Variate Mean
	 * 
	 * @return TRUE - The Variate Pair Co-variance successfully added
	 */

	public boolean addCovariance (
		final java.lang.String strVariate1Name,
		final java.lang.String strVariate2Name,
		final double dblCovariance)
	{
		if (null == strVariate1Name || strVariate1Name.isEmpty() || null == strVariate2Name ||
			strVariate2Name.isEmpty() || !org.drip.quant.common.NumberUtil.IsValid (dblCovariance))
			return false;

		_mapCovariance.put (strVariate1Name + "@#" + strVariate2Name, dblCovariance);

		_mapCovariance.put (strVariate2Name + "@#" + strVariate1Name, dblCovariance);

		return true;
	}

	/**
	 * Retrieve the Variance of the Named Variate
	 * 
	 * @param strVariateName The Named Variate
	 * 
	 * @return Variance of the Named Variate
	 * 
	 * @throws java.lang.Exception Thrown if the Named Variate Variance cannot be retrieved
	 */

	public double variance (
		final java.lang.String strVariateName)
		throws java.lang.Exception
	{
		if (null == strVariateName || strVariateName.isEmpty())
			throw new java.lang.Exception ("MultivariateMetrics::variance => Invalid Inputs");

		java.lang.String strVarianceEntry = strVariateName + "@#" + strVariateName;

		if (!_mapCovariance.containsKey (strVarianceEntry))
			throw new java.lang.Exception ("MultivariateMetrics::variance => Invalid Inputs");

		return _mapCovariance.get (strVarianceEntry);
	}

	/**
	 * Retrieve the Co-variance of the Named Variate Pair
	 * 
	 * @param strVariate1Name The Named Variate #1
	 * @param strVariate2Name The Named Variate #2
	 * 
	 * @return Co-variance of the Named Variate Pair
	 * 
	 * @throws java.lang.Exception Thrown if the Named Variate Co-variance cannot be retrieved
	 */

	public double covariance (
		final java.lang.String strVariate1Name,
		final java.lang.String strVariate2Name)
		throws java.lang.Exception
	{
		if (null == strVariate1Name || strVariate1Name.isEmpty() || null == strVariate2Name ||
			strVariate2Name.isEmpty())
			throw new java.lang.Exception ("MultivariateMetrics::covariance => Invalid Inputs");

		java.lang.String strCovarianceEntry = strVariate1Name + "@#" + strVariate2Name;

		if (!_mapCovariance.containsKey (strCovarianceEntry))
			throw new java.lang.Exception ("MultivariateMetrics::coariance => Invalid Inputs");

		return _mapCovariance.get (strCovarianceEntry);
	}

	/**
	 * Retrieve the Correlation between the Named Variate Pair
	 * 
	 * @param strVariate1Name The Named Variate #1
	 * @param strVariate2Name The Named Variate #2
	 * 
	 * @return Correlation between the Named Variate Pair
	 * 
	 * @throws java.lang.Exception Thrown if the Named Variate Correlation cannot be retrieved
	 */

	public double correlation (
		final java.lang.String strVariate1Name,
		final java.lang.String strVariate2Name)
		throws java.lang.Exception
	{
		return covariance (strVariate1Name, strVariate2Name) / java.lang.Math.sqrt (variance
			(strVariate1Name) * variance (strVariate2Name));
	}
}
