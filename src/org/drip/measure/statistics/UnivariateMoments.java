
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
 * UnivariateMoments generates and holds the Specified Univariate Series Mean, Variance, and a few selected
 * 	Moments.
 *
 * @author Lakshmi Krishnamurthy
 */

public class UnivariateMoments {
	private int _iNumSample = 0;
	private java.lang.String _strName = "";
	private double _dblMean = java.lang.Double.NaN;
	private double _dblVariance = java.lang.Double.NaN;
	private java.util.Map<java.lang.Integer, java.lang.Double> _mapMoment = null;

	/**
	 * Construct a UnivariateMoments Instance for the specified Series
	 * 
	 * @param strName Series Name
	 * @param adblEntry Series Entry
	 * @param aiMoment Array of Moments to be Calculated
	 * 
	 * @return The UnivariateMoments Instance
	 */

	public static final UnivariateMoments Standard (
		final java.lang.String strName,
		final double[] adblEntry,
		final int[] aiMoment)
	{
		if (null == adblEntry) return null;

		double dblMean = 0.;
		double dblVariance = 0.;
		int iNumSample = adblEntry.length;
		int iNumMoment = null == aiMoment ? 0 : aiMoment.length;
		double[] adblMoment = 0 == iNumMoment ? null : new double[iNumMoment];

		java.util.Map<java.lang.Integer, java.lang.Double> mapMoment = 0 == iNumMoment ? null : new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		if (0 == iNumSample) return null;

		for (int i = 0; i < iNumSample; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (adblEntry[i])) return null;

			dblMean += adblEntry[i];
		}

		dblMean /= iNumSample;

		for (int j = 0; j < iNumMoment; ++j)
			adblMoment[j] = 0.;

		for (int i = 0; i < iNumSample; ++i) {
			double dblError = dblMean - adblEntry[i];
			dblVariance += (dblError * dblError);

			for (int j = 0; j < iNumMoment; ++j)
				adblMoment[j] = adblMoment[j] + java.lang.Math.pow (dblError, aiMoment[j]);
		}

		for (int j = 0; j < iNumMoment; ++j)
			mapMoment.put (aiMoment[j], adblMoment[j]);

		try {
			return new UnivariateMoments (strName, dblMean, dblVariance / iNumSample, iNumSample, mapMoment);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a UnivariateMoments Instance for the specified Series
	 * 
	 * @param strName Series Name
	 * @param adblEntry Series Entry
	 * 
	 * @return The UnivariateMoments Instance
	 */

	public static final UnivariateMoments Standard (
		final java.lang.String strName,
		final double[] adblEntry)
	{
		return Standard (strName, adblEntry, null);
	}

	protected UnivariateMoments (
		final java.lang.String strName,
		final double dblMean,
		final double dblVariance,
		final int iNumSample,
		final java.util.Map<java.lang.Integer, java.lang.Double> mapMoment)
		throws java.lang.Exception
	{
		if (null == (_strName = strName) || _strName.isEmpty() || !org.drip.quant.common.NumberUtil.IsValid
			(_dblMean = dblMean) || !org.drip.quant.common.NumberUtil.IsValid (_dblVariance = dblVariance) ||
				0 >= (_iNumSample = iNumSample))
			throw new java.lang.Exception ("UnivariateMetrics Constructor => Invalid Inputs!");

		_mapMoment = mapMoment;
	}

	/**
	 * Retrieve the Series Name
	 * 
	 * @return The Series Name
	 */

	public java.lang.String name()
	{
		return _strName;
	}

	/**
	 * Retrieve the Number of Samples
	 * 
	 * @return The Number of Samples
	 */

	public int numSample()
	{
		return _iNumSample;
	}

	/**
	 * Retrieve the Series Mean
	 * 
	 * @return The Series Mean
	 */

	public double mean()
	{
		return _dblMean;
	}

	/**
	 * Retrieve the Series Variance
	 * 
	 * @return The Series Variance
	 */

	public double variance()
	{
		return _dblVariance;
	}

	/**
	 * Retrieve the Series Standard Deviation
	 * 
	 * @return The Series Standard Deviation
	 */

	public double stdDev()
	{
		return java.lang.Math.sqrt (_dblVariance);
	}

	/**
	 * Retrieve the Moments Map
	 * 
	 * @return The Map of Moments
	 */

	public java.util.Map<java.lang.Integer, java.lang.Double> momentMap()
	{
		return _mapMoment;
	}
}
