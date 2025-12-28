
package org.drip.measure.statistics;

import java.util.Set;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * 	other selected Moments. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Generate the <i>MultivariateMoments</i> Instance from the Series Realizations provided</li>
 * 		<li>Generate the <i>MultivariateMoments</i> Instance from the Specified Mean and Co-variance Inputs</li>
 * 		<li>Retrieve the Number of Variates in the Distribution</li>
 * 		<li>Retrieve the Variates for which the Metrics are available</li>
 * 		<li>Add the Mean for the Named Variate</li>
 * 		<li>Retrieve the Mean of the Named Variate</li>
 * 		<li>Add the Co-variance for the Named Variate Pair</li>
 * 		<li>Retrieve the Variance of the Named Variate</li>
 * 		<li>Retrieve the Co-variance of the Named Variate Pair</li>
 * 		<li>Retrieve the Correlation between the Named Variate Pair</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/statistics/README.md">R<sup>1</sup> R<sup>d</sup> Thin Thick Moments</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultivariateMoments
{
	private CaseInsensitiveHashMap<Double> _meanMap = new CaseInsensitiveHashMap<Double>();

	private CaseInsensitiveHashMap<Double> _covarianceMap = new CaseInsensitiveHashMap<Double>();

	/**
	 * Generate the <i>MultivariateMoments</i> Instance from the Series Realizations provided
	 * 
	 * @param variateNameArray Array of Variate Name Headers
	 * @param multiVariateArray Array of Variate Realization Arrays
	 * 
	 * @return The <i>MultivariateMoments</i> Instance
	 */

	public static final MultivariateMoments Standard (
		final String[] variateNameArray,
		final double[][] multiVariateArray)
	{
		if (null == variateNameArray || null == multiVariateArray) {
			return null;
		}

		int variateCount = variateNameArray.length;
		double[] meanArray = new double[variateCount];

		if (0 == variateCount || variateCount != multiVariateArray.length) {
			return null;
		}

		int sampleCount = multiVariateArray[0].length;

		if (0 == sampleCount) {
			return null;
		}

		MultivariateMoments multivariateMoments = new MultivariateMoments();

		for (int variateIndex = 0; variateIndex < variateCount; ++variateIndex) {
			meanArray[variateIndex] = 0.;
			double[] variateSampleArray = multiVariateArray[variateIndex];

			if (null == variateSampleArray || variateSampleArray.length != sampleCount) {
				return null;
			}

			for (int sampleIndex = 0; sampleIndex < sampleCount; ++sampleIndex) {
				if (!NumberUtil.IsValid (variateSampleArray[sampleIndex])) {
					return null;
				}

				meanArray[variateIndex] += variateSampleArray[sampleIndex];
			}

			if (!multivariateMoments.addMean (
				variateNameArray[variateIndex],
				meanArray[variateIndex] /= sampleCount
			))
			{
				return null;
			}
		}

		for (int variateIndexI = 0; variateIndexI < variateCount; ++variateIndexI) {
			for (int variateIndexJ = 0; variateIndexJ < variateCount; ++variateIndexJ) {
				double covariance = 0.;

				for (int variateIndexK = 0; variateIndexK < sampleCount; ++variateIndexK) {
					covariance +=
						(multiVariateArray[variateIndexI][variateIndexK] - meanArray[variateIndexI]) *
							(multiVariateArray[variateIndexJ][variateIndexK] - meanArray[variateIndexJ]);
				}

				if (!multivariateMoments.addCovariance (
					variateNameArray[variateIndexI],
					variateNameArray[variateIndexJ],
					covariance / sampleCount
				))
				{
					return null;
				}
			}
		}

		return multivariateMoments;
	}

	/**
	 * Generate the <i>MultivariateMoments</i> Instance from the Specified Mean and Co-variance Inputs
	 * 
	 * @param variateNameArray Array of Variate Name Headers
	 * @param meanArray Array of Variate Means
	 * @param covarianceMatrix Double Array of the Variate Co-variance
	 * 
	 * @return The <i>MultivariateMoments</i> Instance
	 */

	public static final MultivariateMoments Standard (
		final String[] variateNameArray,
		final double[] meanArray,
		final double[][] covarianceMatrix)
	{
		if (null == variateNameArray || null == meanArray || null == covarianceMatrix) {
			return null;
		}

		int variateCount = variateNameArray.length;

		if (0 == variateCount || variateCount != meanArray.length || variateCount != covarianceMatrix.length
			|| null == covarianceMatrix[0] || variateCount != covarianceMatrix[0].length)
		{
			return null;
		}

		MultivariateMoments multivariateMoments = new MultivariateMoments();

		for (int variateIndex = 0; variateIndex < variateCount; ++variateIndex) {
			if (!multivariateMoments.addMean (variateNameArray[variateIndex], meanArray[variateIndex])) {
				return null;
			}
		}

		for (int variateIndexI = 0; variateIndexI < variateCount; ++variateIndexI) {
			for (int variateIndexJ = 0; variateIndexJ < variateCount; ++variateIndexJ) {
				if (!multivariateMoments.addCovariance (
					variateNameArray[variateIndexI],
					variateNameArray[variateIndexJ],
					covarianceMatrix[variateIndexI][variateIndexJ]
				))
				{
					return null;
				}
			}
		}

		return multivariateMoments;
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
		return _meanMap.size();
	}

	/**
	 * Retrieve the Variates for which the Metrics are available
	 * 
	 * @return The Set of Variates
	 */

	public Set<String> variateList()
	{
		return _meanMap.keySet();
	}

	/**
	 * Add the Mean for the Named Variate
	 * 
	 * @param variateName The Named Variate
	 * @param mean The Variate Mean
	 * 
	 * @return TRUE - The Variate Mean successfully added
	 */

	public boolean addMean (
		final String variateName,
		final double mean)
	{
		if (null == variateName || variateName.isEmpty() || !NumberUtil.IsValid (mean)) {
			return false;
		}

		_meanMap.put (variateName, mean);

		return true;
	}

	/**
	 * Retrieve the Mean of the Named Variate
	 * 
	 * @param variateName The Named Variate
	 * 
	 * @return Mean of the Named Variate
	 * 
	 * @throws Exception Thrown if the Named Variate Mean cannot be retrieved
	 */

	public double mean (
		final String variateName)
		throws Exception
	{
		if (null == variateName || variateName.isEmpty() || !_meanMap.containsKey (variateName)) {
			throw new Exception ("MultivariateMetrics::mean => Invalid Inputs");
		}

		return _meanMap.get (variateName);
	}

	/**
	 * Add the Co-variance for the Named Variate Pair
	 * 
	 * @param variate1Name The Named Variate #1
	 * @param variate2Name The Named Variate #2
	 * @param covariance The Variate-Pair Covariance
	 * 
	 * @return TRUE - The Variate Pair Co-variance successfully added
	 */

	public boolean addCovariance (
		final String variate1Name,
		final String variate2Name,
		final double covariance)
	{
		if (null == variate1Name || variate1Name.isEmpty() ||
			null == variate2Name || variate2Name.isEmpty() ||
			!NumberUtil.IsValid (covariance))
		{
			return false;
		}

		_covarianceMap.put (variate1Name + "@#" + variate2Name, covariance);

		_covarianceMap.put (variate2Name + "@#" + variate1Name, covariance);

		return true;
	}

	/**
	 * Retrieve the Variance of the Named Variate
	 * 
	 * @param variateName The Named Variate
	 * 
	 * @return Variance of the Named Variate
	 * 
	 * @throws Exception Thrown if the Named Variate Variance cannot be retrieved
	 */

	public double variance (
		final String variateName)
		throws Exception
	{
		if (null == variateName || variateName.isEmpty()) {
			throw new Exception ("MultivariateMetrics::variance => Invalid Inputs");
		}

		String varianceEntry = variateName + "@#" + variateName;

		if (!_covarianceMap.containsKey (varianceEntry)) {
			throw new Exception ("MultivariateMetrics::variance => Invalid Inputs");
		}

		return _covarianceMap.get (varianceEntry);
	}

	/**
	 * Retrieve the Co-variance of the Named Variate Pair
	 * 
	 * @param variate1Name The Named Variate #1
	 * @param variate2Name The Named Variate #2
	 * 
	 * @return Co-variance of the Named Variate Pair
	 * 
	 * @throws Exception Thrown if the Named Variate Co-variance cannot be retrieved
	 */

	public double covariance (
		final String variate1Name,
		final String variate2Name)
		throws Exception
	{
		if (null == variate1Name || variate1Name.isEmpty() || null == variate2Name || variate2Name.isEmpty())
		{
			throw new Exception ("MultivariateMetrics::covariance => Invalid Inputs");
		}

		String covarianceEntry12 = variate1Name + "@#" + variate2Name;
		String covarianceEntry21 = variate1Name + "@#" + variate2Name;

		if (_covarianceMap.containsKey (covarianceEntry12)) {
			return _covarianceMap.get (covarianceEntry12);
		}

		if (_covarianceMap.containsKey (covarianceEntry21)) {
			return _covarianceMap.get (covarianceEntry21);
		}

		throw new Exception ("MultivariateMetrics::covariance => Invalid Inputs");
	}

	/**
	 * Retrieve the Correlation between the Named Variate Pair
	 * 
	 * @param variate1Name The Named Variate #1
	 * @param variate2Name The Named Variate #2
	 * 
	 * @return Correlation between the Named Variate Pair
	 * 
	 * @throws Exception Thrown if the Named Variate Correlation cannot be retrieved
	 */

	public double correlation (
		final String variate1Name,
		final String variate2Name)
		throws Exception
	{
		return covariance (variate1Name, variate2Name) /
			Math.sqrt (variance (variate1Name) * variance (variate2Name));
	}
}
