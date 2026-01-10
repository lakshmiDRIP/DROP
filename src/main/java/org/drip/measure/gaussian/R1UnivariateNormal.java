
package org.drip.measure.gaussian;

import org.drip.measure.distribution.R1Continuous;
import org.drip.numerical.common.Array2D;
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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>R1UnivariateNormal</i> implements the Univariate R<sup>1</sup> Normal Distribution. It implements the
 * 	Incremental, the Cumulative, and the Inverse Cumulative Distribution Densities. It provides the following
 * 	Functionality:
 *
 *  <ul>
 * 		<li>Generate a N (0, 1) distribution</li>
 * 		<li>Construct a R1 Normal/Gaussian Distribution</li>
 * 		<li>Retrieve the Sigma</li>
 * 		<li>Lay out the Support of the PDF Range</li>
 * 		<li>Compute the cumulative under the distribution to the given value</li>
 * 		<li>Compute the Incremental under the Distribution between the 2 variates</li>
 * 		<li>Compute the inverse cumulative under the distribution corresponding to the given value</li>
 * 		<li>Compute the Density under the Distribution at the given Variate</li>
 * 		<li>Retrieve the Mean of the Distribution</li>
 * 		<li>Retrieve the Median of the Distribution</li>
 * 		<li>Retrieve the Mode of the Distribution</li>
 * 		<li>Retrieve the Variance of the Distribution</li>
 * 		<li>Retrieve the Univariate Weighted Histogram</li>
 * 		<li>Generate a Random Variable corresponding to the Distribution</li>
 * 		<li>Compute the Error Function Around an Absolute Width around the Mean</li>
 * 		<li>Compute the Confidence given the Width around the Mean</li>
 * 		<li>Compute the Width around the Mean given the Confidence Level</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/gaussian/README.md">R<sup>1</sup> Covariant Gaussian Quadrature</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1UnivariateNormal
	extends R1Continuous
{
	private double _mean = Double.NaN;
	private double _sigma = Double.NaN;

	/**
	 * Generate a N (0, 1) distribution
	 * 
	 * @return The N (0, 1) distribution
	 */

	public static final R1UnivariateNormal Standard()
	{
		try {
			return new R1UnivariateNormal (0., 1.);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a R1 Normal/Gaussian Distribution
	 * 
	 * @param mean Mean of the Distribution
	 * @param sigma Sigma of the Distribution
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	public R1UnivariateNormal (
		final double mean,
		final double sigma)
		throws Exception
	{
		if (!NumberUtil.IsValid (_mean = mean) || !NumberUtil.IsValid (_sigma = sigma) || 0. > _sigma) {
			throw new Exception ("R1UnivariateNormal Constructor: Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Sigma
	 * 
	 * @return The Sigma
	 */

	public double sigma()
	{
	    return _sigma;
	}

	/**
	 * Lay out the Support of the PDF Range
	 * 
	 * @return Support of the PDF Range
	 */

	@Override public double[] support()
	{
		return new double[] {Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY};
	}

	/**
	 * Compute the cumulative under the distribution to the given value
	 * 
	 * @param x Variate to which the cumulative is to be computed
	 * 
	 * @return The cumulative
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	@Override public double cumulative (
		final double x)
		throws Exception
	{
		if (!NumberUtil.IsValid (x)) {
			throw new Exception ("R1UnivariateNormal::cumulative => Invalid Inputs");
		}

		if (0. == _sigma) {
			return x >= _mean ? 1. : 0.;
		}

		return NormalQuadrature.CDF ((x - _mean) / _sigma);
	}

	/**
	 * Compute the Incremental under the Distribution between the 2 variates
	 * 
	 * @param xLeft Left Variate to which the cumulative is to be computed
	 * @param xRight Right Variate to which the cumulative is to be computed
	 * 
	 * @return The Incremental under the Distribution between the 2 variates
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	@Override public double incremental (
		final double xLeft,
		final double xRight)
		throws Exception
	{
		return cumulative (xRight) - cumulative (xLeft);
	}

	/**
	 * Compute the inverse cumulative under the distribution corresponding to the given value
	 * 
	 * @param y Value corresponding to which the inverse cumulative is to be computed
	 * 
	 * @return The inverse cumulative
	 * 
	 * @throws Exception Thrown if the Input is invalid
	 */

	@Override public double invCumulative (
		final double y)
		throws Exception
	{
		if (!NumberUtil.IsValid (y) || 0. == _sigma) {
			throw new Exception ("R1UnivariateNormal::invCumulative => Cannot calculate");
		}

	    return NormalQuadrature.InverseCDF (y) * _sigma + _mean;
	}

	/**
	 * Compute the Density under the Distribution at the given Variate
	 * 
	 * @param x Variate at which the Density needs to be computed
	 * 
	 * @return The Density
	 * 
	 * @throws Exception Thrown if the input is invalid
	 */

	@Override public double density (
		final double x)
		throws Exception
	{
		if (!NumberUtil.IsValid (x)) {
			throw new Exception ("R1UnivariateNormal::density => Invalid Inputs");
		}

		if (0. == _sigma) {
			return x == _mean ? 1. : 0.;
		}

		double meanShift = (x - _mean) / _sigma;

		return Math.exp (-0.5 * meanShift * meanShift);
	}

	/**
	 * Retrieve the Mean of the Distribution
	 * 
	 * @return The Mean of the Distribution
	 * 
	 * @throws Exception Thrown if the Mean cannot be estimated
	 */

	@Override public double mean()
	{
	    return _mean;
	}

	/**
	 * Retrieve the Median of the Distribution
	 * 
	 * @return The Median of the Distribution
	 * 
	 * @throws Exception Thrown if the Median cannot be estimated
	 */

	@Override public double median()
	{
	    return _mean;
	}

	/**
	 * Retrieve the Mode of the Distribution
	 * 
	 * @return The Mode of the Distribution
	 * 
	 * @throws Exception Thrown if the Mode cannot be estimated
	 */

	@Override public double mode()
	{
	    return _mean;
	}

	/**
	 * Retrieve the Variance of the Distribution
	 * 
	 * @return The Variance of the Distribution
	 * 
	 * @throws Exception Thrown if the Variance cannot be estimated
	 */

	@Override public double variance()
	{
	    return _sigma * _sigma;
	}

	/**
	 * Retrieve the Univariate Weighted Histogram
	 * 
	 * @return The Univariate Weighted Histogram
	 */

	@Override public Array2D histogram()
	{
		return null;
	}

	/**
	 * Generate a Random Variable corresponding to the Distribution
	 * 
	 * @return Random Variable corresponding to the Distribution
	 * 
	 * @throws Exception Thrown if the Random Instance cannot be estimated
	 */

	@Override public double random()
	{
		try {
			return invCumulative (Math.random());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Double.NaN;
	}

	/**
	 * Compute the Error Function Around an Absolute Width around the Mean
	 * 
	 * @param x The Width
	 * 
	 * @return The Error Function Around an Absolute Width around the Mean
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double errorFunction (
		final double x)
		throws Exception
	{
		if (!NumberUtil.IsValid (x)) {
			throw new Exception ("R1UnivariateNormal::errorFunction => Invalid Inputs");
		}

		double dblWidth = Math.abs (x);

		return cumulative (_mean + dblWidth) - cumulative (_mean - dblWidth);
	}

	/**
	 * Compute the Confidence given the Width around the Mean
	 * 
	 * @param width The Width
	 * 
	 * @return The Error Function Around an Absolute Width around the Mean
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double confidence (
		final double width)
		throws Exception
	{
		return errorFunction (width);
	}

	/**
	 * Compute the Width around the Mean given the Confidence Level
	 * 
	 * @param confidence The Confidence Level
	 * 
	 * @return The Width around the Mean given the Confidence Level
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double confidenceInterval (
		final double confidence)
		throws Exception
	{
		if (!NumberUtil.IsValid (confidence) || 0. >= confidence || 1. <= confidence) {
			throw new Exception ("R1UnivariateNormal::confidenceInterval => Invalid Inputs");
		}

		return invCumulative (0.5 * (1. + confidence));
	}
}
