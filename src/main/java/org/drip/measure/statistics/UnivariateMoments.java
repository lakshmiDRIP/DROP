
package org.drip.measure.statistics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>UnivariateMoments</i> generates and holds the Specified Univariate Series Mean, Variance, and a few
 * selected Moments.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/statistics">Statistics</a></li>
 *  </ul>
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
			if (!org.drip.numerical.common.NumberUtil.IsValid (adblEntry[i])) return null;

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
		if (null == (_strName = strName) || _strName.isEmpty() || !org.drip.numerical.common.NumberUtil.IsValid
			(_dblMean = dblMean) || !org.drip.numerical.common.NumberUtil.IsValid (_dblVariance = dblVariance) ||
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
	 * Retrieve the Series Standard Error
	 * 
	 * @return The Series Standard Error
	 */

	public double stdError()
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

	/**
	 * Compute the Series t-Statistic around the Series Hypothesis Pivot
	 * 
	 * @param hypothesisPivot The Series Hypothesis Pivot
	 * 
	 * @return The Series t-Statistic around the Series Hypothesis Pivot
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double tStatistic (
		final double hypothesisPivot)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (hypothesisPivot))
		{
			throw new java.lang.Exception ("UnivariateMetrics::tStatistic => Invalid Inputs");
		}

		return (_dblMean - hypothesisPivot) / java.lang.Math.sqrt (_dblVariance);
	}

	/**
	 * Compute the Series t-Statistic for Hypothesis Pivot = 0 (e.g., the False Positive NULL Hypothesis for
	 * 	for Homoscedastic Univariate Linear Regression)
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 * 
	 * @return The Series t-Statistic
	 */

	public double tStatistic()
		throws java.lang.Exception
	{
		return _dblMean / java.lang.Math.sqrt (_dblVariance);
	}

	/**
	 * Estimate the Offset in Terms of the NUmber of Standard Errors
	 * 
	 * @param x The Observation Point
	 * 
	 * @return The Offset in Terms of the NUmber of Standard Errors
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double standardErrorOffset (
		final double x)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (x))
		{
			throw new java.lang.Exception ("UnivariateMetrics::standardErrorOffset => Invalid Inputs");
		}

		return (_dblMean - x) / java.lang.Math.sqrt (_dblVariance);
	}

	/**
	 * Retrieve the Degrees of Freedom
	 * 
	 * @return The Degrees of Freedom
	 */

	public int degreesOfFreedom()
	{
		return _iNumSample - 1;
	}

	/**
	 * Compute the Predictive Confidence Level
	 * 
	 * @return The Predictive Confidence Level
	 */

	public double predictiveConfidenceLevel()
	{
		return java.lang.Math.sqrt (_dblVariance * (1. + 1. / (1. + _iNumSample)));
	}
}
