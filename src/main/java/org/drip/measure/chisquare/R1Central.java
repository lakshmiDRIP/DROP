
package org.drip.measure.chisquare;

import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.R2ToR1;
import org.drip.measure.distribution.R1Continuous;
import org.drip.measure.gamma.R1ShapeScaleDistribution;
import org.drip.measure.gamma.ShapeScaleParameters;
import org.drip.measure.gaussian.NormalQuadrature;
import org.drip.measure.gaussian.R1UnivariateNormal;
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
 * <i>R1Central</i> implements the Probability Density Function for the R<sup>1</sup> Central Chi-Square
 * 	Distribution. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Backstrom, T., and J. Fischer (2018): Fast Randomization for Distributed Low Bit-rate Coding of
 * 				Speech and Audio <i>IEEE/ACM Transactions on Audio, Speech, and Language Processing</i> <b>26
 * 				(1)</b> 19-30
 * 		</li>
 * 		<li>
 * 			Chi-Squared Distribution (2019): Chi-Squared Function
 * 				https://en.wikipedia.org/wiki/Chi-squared_distribution
 * 		</li>
 * 		<li>
 * 			Johnson, N. L., S. Kotz, and N. Balakrishnan (1994): <i>Continuous Univariate Distributions
 * 				2<sup>nd</sup> Edition</i> <b>John Wiley and Sons</b>
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2019): Chi-Squared Distribution
 * 				https://www.itl.nist.gov/div898/handbook/eda/section3/eda3666.htm
 * 		</li>
 * 	</ul>
 * 
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Generate a Consolidated Chi-squared Distribution from Independent Component Distributions</li>
 * 		<li><i>R1Central</i> Constructor</li>
 * 		<li>Retrieve the Degrees of Freedom</li>
 * 		<li>Retrieve the Gamma Estimator</li>
 * 		<li>Retrieve the Digamma Estimator</li>
 * 		<li>Retrieve the Lower Incomplete Gamma Estimator</li>
 * 		<li>Lay out the Support of the PDF Range</li>
 * 		<li>Compute the Density under the Distribution at the given Variate</li>
 * 		<li>Compute the cumulative under the distribution to the given value</li>
 * 		<li>Retrieve the Mean of the Distribution</li>
 * 		<li>Retrieve the Median of the Distribution</li>
 * 		<li>Retrieve the Mode of the Distribution</li>
 * 		<li>Retrieve the Variance of the Distribution</li>
 * 		<li>Retrieve the Skewness of the Distribution</li>
 * 		<li>Retrieve the Excess Kurtosis of the Distribution</li>
 * 		<li>Retrieve the Differential Entropy of the Distribution</li>
 * 		<li>Construct the Moment Generating Function</li>
 * 		<li>Construct the Probability Generating Function</li>
 * 		<li>Generate a Random Variable corresponding to the Distribution</li>
 * 		<li>Retrieve the Normalizer</li>
 * 		<li>Retrieve the CDF Scaler</li>
 * 		<li>Compute the Chernoff Upper Bound</li>
 *		<li> Compute the Non-central Moment about Zero</li>
 * 		<li>Compute the Cumulant</li>
 * 		<li>Retrieve the Central Limit Theorem Equivalent Normal Distribution Proxy</li>
 * 		<li>Indicate if the Current Distribution is a Valid Proxy as a CLT</li>
 * 		<li>Generate a Gamma-distribution off of the Scaled Chi-Square Distribution</li>
 * 		<li>Generate Logarithm Proxy Based Random Number - Proxy to Univariate Normal Distribution</li>
 * 		<li>Generate CLT Proxy Based Random Number - Proxy to Univariate Normal Distribution</li>
 * 		<li>Generate Fisher Proxy Random Number - Proxy to Univariate Normal Distribution</li>
 * 		<li>Generate Wilson-Hilferty Proxy Random Number - Proxy to Univariate Normal Distribution</li>
 * 		<li>Generate Gamma Distributed Random Number</li>
 * 		<li>Generate the Chi Distributed Random Number</li>
 * 		<li>Generate Exponential (0.5) Distributed Random Number</li>
 * 		<li>Generate Rayleigh (1) Distributed Random Number</li>
 * 		<li>Generate Maxwell (1) Distributed Random Number</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/chisquare/README.md">Chi-Square Distribution Implementation/Properties</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1Central
	extends R1Continuous
{
	private R1ToR1 _gammaEstimator = null;
	private double _degreesOfFreedom = -1.;
	private double _cdfScaler = Double.NaN;
	private double _normalizer = Double.NaN;
	private R1ToR1 _digammaEstimator = null;
	private R2ToR1 _lowerIncompleteGammaEstimator = null;

	/**
	 * Generate a Consolidated Chi-squared Distribution from Independent Component Distributions
	 * 
	 * @param chiSquaredDistributionArray Independent Component Distribution Array
	 * 
	 * @return Consolidated Chi-squared Distribution
	 */

	public static final R1Central FromIndependentChiSquared (
		final R1Central[] chiSquaredDistributionArray)
	{
		if (null == chiSquaredDistributionArray || 0 == chiSquaredDistributionArray.length) {
			return null;
		}

		double degreesOfFreedom = 0;

		for (R1Central chiSquaredDistribution : chiSquaredDistributionArray) {
			if (null == chiSquaredDistribution) {
				return null;
			}

			degreesOfFreedom = degreesOfFreedom + chiSquaredDistribution.degreesOfFreedom();
		}

		try {
			return new R1Central (
				degreesOfFreedom,
				chiSquaredDistributionArray[0].gammaEstimator(),
				chiSquaredDistributionArray[0].digammaEstimator(),
				chiSquaredDistributionArray[0].lowerIncompleteGammaEstimator()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>R1Central</i> Constructor
	 * 
	 * @param degreesOfFreedom Degrees of Freedom
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public R1Central (
		final double degreesOfFreedom,
		final R1ToR1 gammaEstimator,
		final R1ToR1 digammaEstimator,
		final R2ToR1 lowerIncompleteGammaEstimator)
		throws Exception
	{
		if (!NumberUtil.IsValid (_degreesOfFreedom = degreesOfFreedom) || 0. >= _degreesOfFreedom ||
			null == (_gammaEstimator = gammaEstimator) ||
			null == (_digammaEstimator = digammaEstimator) ||
			null == (_lowerIncompleteGammaEstimator = lowerIncompleteGammaEstimator))
		{
			throw new Exception ("R1Central Constructor => Invalid Inputs");
		}

		double halfDOF = 0.5 * _degreesOfFreedom;

		_normalizer = (_cdfScaler = 1. / _gammaEstimator.evaluate (halfDOF)) * Math.pow (2., -1. * halfDOF);
	}

	/**
	 * Retrieve the Degrees of Freedom
	 * 
	 * @return The Degrees of Freedom
	 */

	public double degreesOfFreedom()
	{
		return _degreesOfFreedom;
	}

	/**
	 * Retrieve the Gamma Estimator
	 * 
	 * @return Gamma Estimator
	 */

	public R1ToR1 gammaEstimator()
	{
		return _gammaEstimator;
	}

	/**
	 * Retrieve the Digamma Estimator
	 * 
	 * @return Digamma Estimator
	 */

	public R1ToR1 digammaEstimator()
	{
		return _digammaEstimator;
	}

	/**
	 * Retrieve the Lower Incomplete Gamma Estimator
	 * 
	 * @return Lower Incomplete Gamma Estimator
	 */

	public R2ToR1 lowerIncompleteGammaEstimator()
	{
		return _lowerIncompleteGammaEstimator;
	}

	/**
	 * Lay out the Support of the PDF Range
	 * 
	 * @return Support of the PDF Range
	 */

	@Override public double[] support()
	{
		return new double[] {0., Double.POSITIVE_INFINITY};
	}

	/**
	 * Compute the Density under the Distribution at the given Variate
	 * 
	 * @param t Variate at which the Density needs to be computed
	 * 
	 * @return The Density
	 * 
	 * @throws Exception Thrown if the input is invalid
	 */

	@Override public double density (
		final double t)
		throws Exception
	{
		if (!supported (t)) {
			throw new Exception ("R1Central::density => Variate not in Range");
		}

		return _normalizer * Math.pow (t, 0.5 * _degreesOfFreedom - 1.) * Math.exp (-0.5 * t);
	}

	/**
	 * Compute the cumulative under the distribution to the given value
	 * 
	 * @param t Variate to which the cumulative is to be computed
	 * 
	 * @return The cumulative
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	@Override public double cumulative (
		final double t)
		throws Exception
	{
		if (!supported (t)) {
			throw new Exception ("R1Central::cumulative => Invalid Inputs");
		}

		return _cdfScaler * _lowerIncompleteGammaEstimator.evaluate (0.5 * _degreesOfFreedom, 0.5 * t);
	}

	/**
	 * Retrieve the Mean of the Distribution
	 * 
	 * @return The Mean of the Distribution
	 * 
	 * @throws Exception Thrown if the Mean cannot be estimated
	 */

	@Override public double mean()
		throws Exception
	{
		return _degreesOfFreedom;
	}

	/**
	 * Retrieve the Median of the Distribution
	 * 
	 * @return The Median of the Distribution
	 * 
	 * @throws Exception Thrown if the Median cannot be estimated
	 */

	@Override public double median()
		throws Exception
	{
		double oneMinus_twoOver_9dof__ = 1. - (2. / (9. * _degreesOfFreedom));

		return _degreesOfFreedom * oneMinus_twoOver_9dof__ * oneMinus_twoOver_9dof__ *
			oneMinus_twoOver_9dof__;
	}

	/**
	 * Retrieve the Mode of the Distribution
	 * 
	 * @return The Mode of the Distribution
	 * 
	 * @throws Exception Thrown if the Mode cannot be estimated
	 */

	@Override public double mode()
		throws Exception
	{
		return Math.max (_degreesOfFreedom - 2., 0.);
	}

	/**
	 * Retrieve the Variance of the Distribution
	 * 
	 * @return The Variance of the Distribution
	 * 
	 * @throws Exception Thrown if the Variance cannot be estimated
	 */

	@Override public double variance()
		throws Exception
	{
		return 2. * _degreesOfFreedom;
	}

	/**
	 * Retrieve the Skewness of the Distribution
	 * 
	 * @return The Skewness of the Distribution
	 * 
	 * @throws Exception Thrown if the Skewness cannot be estimated
	 */

	@Override public double skewness()
		throws Exception
	{
		return Math.sqrt (8. / _degreesOfFreedom);
	}

	/**
	 * Retrieve the Excess Kurtosis of the Distribution
	 * 
	 * @return The Excess Kurtosis of the Distribution
	 * 
	 * @throws Exception Thrown if the Skewness cannot be estimated
	 */

	@Override public double excessKurtosis()
		throws Exception
	{
		return 12. / _degreesOfFreedom;
	}

	/**
	 * Retrieve the Differential Entropy of the Distribution
	 * 
	 * @return The Differential Entropy of the Distribution
	 * 
	 * @throws Exception Thrown if the Entropy cannot be estimated
	 */

	@Override public double differentialEntropy()
		throws Exception
	{
		double halfDOF = 0.5 * _degreesOfFreedom;

		return halfDOF + Math.log (2. * _gammaEstimator.evaluate (halfDOF)) +
			(1. - halfDOF) * _digammaEstimator.evaluate (halfDOF);
	}

	/**
	 * Construct the Moment Generating Function
	 * 
	 * @return The Moment Generating Function
	 */

	@Override public R1ToR1 momentGeneratingFunction()
	{
		return new R1ToR1 (null) {
			@Override public double evaluate (
				final double t)
				throws Exception
			{
				if (!NumberUtil.IsValid (t) || t > 0.5) {
					throw new Exception ("R1Central::momentGeneratingFunction::evaluate => Invalid Input");
				}

				return Math.pow (1. - 2. * t, -0.5 * _degreesOfFreedom);
			}
		};
	}

	/**
	 * Construct the Probability Generating Function
	 * 
	 * @return The Probability Generating Function
	 */

	@Override public R1ToR1 probabilityGeneratingFunction()
	{
		return new R1ToR1 (null) {
			@Override public double evaluate (
				final double t)
				throws Exception
			{
				if (!NumberUtil.IsValid (t) || t <= 0. || t > Math.sqrt (Math.E)) {
					throw new Exception (
						"R1Central::probabilityGeneratingFunction::evaluate => Invalid Input"
					);
				}

				return Math.pow (1. - 2. * Math.log (t), -0.5 * _degreesOfFreedom);
			}
		};
	}

	/**
	 * Generate a Random Variable corresponding to the Distribution
	 * 
	 * @return Random Variable corresponding to the Distribution
	 * 
	 * @throws Exception Thrown if the Random Instance cannot be estimated
	 */

	@Override public double random()
		throws Exception
	{
		double sumOfStandardNormalSquares = 0.;

		for (int drawIndex = 0; drawIndex < _degreesOfFreedom; ++drawIndex) {
			double randomStandardNormal = NormalQuadrature.InverseCDF (Math.random());

			sumOfStandardNormalSquares = sumOfStandardNormalSquares +
				randomStandardNormal * randomStandardNormal;
		}

		return sumOfStandardNormalSquares;
	}

	/**
	 * Retrieve the Normalizer
	 * 
	 * @return Normalizer
	 */

	public double normalizer()
	{
		return _normalizer;
	}

	/**
	 * Retrieve the CDF Scaler
	 * 
	 * @return CDF Scaler
	 */

	public double cdfScaler()
	{
		return _cdfScaler;
	}

	/**
	 * Compute the Chernoff Upper Bound
	 * 
	 * @param x A
	 * 
	 * @return The Chernoff Upper Bound
	 * 
	 * @throws Exception Thrown if the Chernoff Upper Bound cannot be calculated
	 */

	public double chernoffBound (
		final double x)
		throws Exception
	{
		if (!NumberUtil.IsValid (x) || 0. >= x) {
			throw new Exception ("R1Central::chernoffBound => Invalid Inputs");
		}

		double z = x / _degreesOfFreedom;

		if (1. == z) {
			throw new Exception ("R1Central::chernoffBound => Invalid Inputs");
		}

		double _zExponent_OneMinusZ__powerHalfDegreesOfFreedom = Math.pow (
			z * Math.exp (1. - z),
			0.5 * _degreesOfFreedom
		);

		return 1. > z ? _zExponent_OneMinusZ__powerHalfDegreesOfFreedom : 1. -
			_zExponent_OneMinusZ__powerHalfDegreesOfFreedom;
	}

	/**
	 * Compute the Non-central Moment about Zero
	 * 
	 * @param m Non-central Moment Index
	 * 
	 * @return The Non-central Moment about Zero
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double nonCentralMoment (
		final int m)
		throws Exception
	{
		if (0 > m) {
			throw new Exception ("R1Central::nonCentralMoment => Invalid Inputs");
		}

		double halfDOF = 0.5 * _degreesOfFreedom;

		return Math.pow (2., m) *
			_gammaEstimator.evaluate (m + halfDOF) / _gammaEstimator.evaluate (halfDOF);
	}

	/**
	 * Compute the Cumulant
	 * 
	 * @param n Cumulant Index
	 * 
	 * @return The Cumulant
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double cumulant (
		final int n)
		throws Exception
	{
		if (0 > n) {
			throw new Exception ("R1Central::cumulant => Invalid Inputs");
		}

		return _degreesOfFreedom * Math.pow (2., n - 1.) * _gammaEstimator.evaluate (n);
	}

	/**
	 * Retrieve the Central Limit Theorem Equivalent Normal Distribution Proxy
	 * 
	 * @return The Central Limit Theorem Equivalent Normal Distribution Proxy
	 */

	public R1UnivariateNormal cltProxy()
	{
		try {
			return new R1UnivariateNormal (_degreesOfFreedom, 2. * _degreesOfFreedom);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Indicate if the Current Distribution is a Valid Proxy as a CLT
	 * 
	 * @return TRUE - The Current Distribution is a Valid Proxy as a CLT
	 */

	public boolean validCLTProxy()
	{
		return 50. <= _degreesOfFreedom;
	}

	/**
	 * Generate a Gamma-distribution off of the Scaled Chi-Square Distribution
	 * 
	 * @param scale The Scale
	 * 
	 * @return The Gamma Distribution
	 */

	public R1ShapeScaleDistribution gammaDistribution (
		final double scale)
	{
		try {
			return new R1ShapeScaleDistribution (
				new ShapeScaleParameters (0.5 * _degreesOfFreedom, 2. * scale),
				_gammaEstimator,
				_digammaEstimator,
				_lowerIncompleteGammaEstimator
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate Logarithm Proxy Based Random Number - Proxy to Univariate Normal Distribution
	 * 
	 * @return Logarithm Proxy Based Random Number - Proxy to Univariate Normal Distribution
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double randomLogProxy()
		throws Exception
	{
		return Math.log (_degreesOfFreedom);
	}

	/**
	 * Generate CLT Proxy Based Random Number - Proxy to Univariate Normal Distribution
	 * 
	 * @return CLT Proxy Based Random Number - Proxy to Univariate Normal Distribution
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double randomCLTProxy()
		throws Exception
	{
		return (random() - _degreesOfFreedom) / Math.sqrt (2. * _degreesOfFreedom);
	}

	/**
	 * Generate Fisher Proxy Random Number - Proxy to Univariate Normal Distribution
	 * 
	 * @return Fisher Proxy Random Number - Proxy to Univariate Normal Distribution
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double randomFisherProxy()
		throws Exception
	{
		return Math.sqrt (2. * random());
	}

	/**
	 * Generate Wilson-Hilferty Proxy Random Number - Proxy to Univariate Normal Distribution
	 * 
	 * @return Wilson-Hilferty Proxy Random Number - Proxy to Univariate Normal Distribution
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double randomWilsonHilfertyProxy()
		throws Exception
	{
		return Math.pow (random() / _degreesOfFreedom, 1. / 3.);
	}

	/**
	 * Generate Gamma Distributed Random Number
	 * 
	 * @param c The Scale Parameter
	 * 
	 * @return Gamma Distributed Random Number
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double randomGamma (
		final double c)
		throws Exception
	{
		if (!NumberUtil.IsValid (c) || 0. >= c) {
			throw new Exception ("R1Central::randomGamma => Invalid Inputs");
		}

		return random() * c;
	}

	/**
	 * Generate the Chi Distributed Random Number
	 * 
	 * @return Chi Distributed Random Number
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double randomChi()
		throws Exception
	{
		return Math.sqrt (random());
	}

	/**
	 * Generate Exponential (0.5) Distributed Random Number
	 * 
	 * @return Exponential (0.5) Distributed Random Number
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double randomExponentialHalf()
		throws Exception
	{
		if (2. != _degreesOfFreedom) {
			throw new Exception ("R1Central::randomExponentialHalf => Invalid Inputs");
		}

		return random();
	}

	/**
	 * Generate Rayleigh (1) Distributed Random Number
	 * 
	 * @return Rayleigh (1) Distributed Random Number
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double randomRayleigh1()
		throws Exception
	{
		if (2. != _degreesOfFreedom) {
			throw new Exception ("R1Central::randomRayleigh1 => Invalid Inputs");
		}

		return random();
	}

	/**
	 * Generate Maxwell (1) Distributed Random Number
	 * 
	 * @return Maxwell (1) Distributed Random Number
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double randomMaxwell1()
		throws Exception
	{
		if (3. != _degreesOfFreedom) {
			throw new Exception ("R1Central::randomMaxwell1 => Invalid Inputs");
		}

		return random();
	}
}
