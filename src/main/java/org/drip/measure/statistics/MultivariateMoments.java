
package org.drip.measure.statistics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>MultivariateMoments</i> generates and holds the Specified Multivariate Series Mean, Co-variance, and
 * other selected Moments.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/statistics/README.md">R<sup>1</sup> R<sup>d</sup> Thin Thick Moments</a></li>
 *  </ul>
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
				if (!org.drip.numerical.common.NumberUtil.IsValid (adblVariateSample[k])) return null;

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
		if (null == strVariateName || strVariateName.isEmpty() || !org.drip.numerical.common.NumberUtil.IsValid
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
			strVariate2Name.isEmpty() || !org.drip.numerical.common.NumberUtil.IsValid (dblCovariance))
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
