
package org.drip.measure.gamma;

import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.R2ToR1;
import org.drip.measure.continuous.R1Distribution;
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
 * <i>R1ShapeScaleDistribution</i> implements the Shape and Scale Parameterization of the R<sup>1</sup> Gamma
 * 	Distribution. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Devroye, L. (1986): <i>Non-Uniform Random Variate Generation</i> <b>Springer-Verlag</b> New York
 * 		</li>
 * 		<li>
 * 			Gamma Distribution (2019): Gamma Distribution
 * 				https://en.wikipedia.org/wiki/Chi-squared_distribution
 * 		</li>
 * 		<li>
 * 			Louzada, F., P. L. Ramos, and E. Ramos (2019): A Note on Bias of Closed-Form Estimators for the
 * 				Gamma Distribution Derived From Likelihood Equations <i>The American Statistician</i> <b>73
 * 				(2)</b> 195-199
 * 		</li>
 * 		<li>
 * 			Minka, T. (2002): Estimating a Gamma distribution https://tminka.github.io/papers/minka-gamma.pdf
 * 		</li>
 * 		<li>
 * 			Ye, Z. S., and N. Chen (2017): Closed-Form Estimators for the Gamma Distribution Derived from
 * 				Likelihood Equations <i>The American Statistician</i> <b>71 (2)</b> 177-181
 * 		</li>
 * 	</ul>
 * 
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Construct a Gamma Distribution from Shape and Rate Parameters</li>
 * 		<li>Shape Summation Based <i>R1ShapeScaleDistribution</i></li>
 * 		<li>Construct the Standard <i>R1ShapeScaleDistribution</i> Instance</li>
 * 		<li><i>R1ShapeScaleDistribution</i> Constructor</li>
 * 		<li>Retrieve the Shape-Scale Parameters</li>
 * 		<li>Retrieve the Gamma Estimator</li>
 * 		<li>Retrieve the Digamma Estimator</li>
 * 		<li>Retrieve the Lower Incomplete Gamma Estimator</li>
 * 		<li>Lay out the Support of the PDF Range</li>
 * 		<li>Compute the Density under the Distribution at the given Variate</li>
 * 		<li>Compute the cumulative under the distribution to the given value</li>
 * 		<li>Retrieve the Mean of the Distribution</li>
 * 		<li>Retrieve the Mode of the Distribution</li>
 * 		<li>Retrieve the Variance of the Distribution</li>
 * 		<li>Retrieve the Skewness of the Distribution</li>
 * 		<li>Retrieve the Excess Kurtosis of the Distribution</li>
 * 		<li>Retrieve the Differential Entropy of the Distribution</li>
 * 		<li>Retrieve the Moment Generating Function of the Distribution</li>
 * 		<li>Retrieve the Central Limit Theorem Equivalent Normal Distribution Proxy</li>
 * 		<li>Compute the Logarithmic Expectation</li>
 * 		<li>Compute the Banneheke-Ekayanake Approximation for the Median when k gte 1</li>
 * 		<li>Compute the Ramanujan-Choi Approximation for the Median</li>
 * 		<li>Compute the Chen-Rubin Median Lower Bound</li>
 * 		<li>Compute the Chen-Rubin Median Upper Bound</li>
 * 		<li>Generate a Scaled Gamma Distribution</li>
 * 		<li>Retrieve the Array of Natural Parameters</li>
 * 		<li>Retrieve the Array of Natural Statistics</li>
 * 		<li>Generate the Exponential Family Representation</li>
 * 		<li>Compute the Laplacian</li>
 * 		<li>Generate a Random Variable using the Ahrens-Dieter (1982) Scheme</li>
 * 		<li>Generate a Random Variable using the Marsaglia (1977) Scheme</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/gamma/README.md">R<sup>1</sup> Gamma Distribution Implementation/Properties</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1ShapeScaleDistribution
	extends R1Distribution
{
	private R1ToR1 _gammaEstimator = null;
	private double _cdfScaler = Double.NaN;
	private double _pdfScaler = Double.NaN;
	private R1ToR1 _digammaEstimator = null;
	private R2ToR1 _lowerIncompleteGammaEstimator = null;
	private ShapeScaleParameters _shapeScaleParameters = null;

	/**
	 * Construct a Gamma Distribution from Shape and Rate Parameters
	 * 
	 * @param shapeParameter Shape Parameter
	 * @param rateParameter Rate Parameter
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * 
	 * @return Gamma Distribution from Shape Alpha and Rate Beta Parameters
	 */

	public static final R1ShapeScaleDistribution FromShapeAndRate (
		final double shapeParameter,
		final double rateParameter,
		final R1ToR1 gammaEstimator,
		final R1ToR1 digammaEstimator,
		final R2ToR1 lowerIncompleteGammaEstimator)
	{
		return R1ShapeScaleDistribution.Standard (
			shapeParameter,
			1. / rateParameter,
			gammaEstimator,
			digammaEstimator,
			lowerIncompleteGammaEstimator
		);
	}

	/**
	 * Shape Summation Based <i>R1ShapeScaleDistribution</i>
	 * 
	 * @param shapeParameterArray Shape Parameter Array
	 * @param scaleParameter Scale Parameter
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * 
	 * @return Shape Summation Based <i>R1ShapeScaleDistribution</i>
	 */

	public static final R1ShapeScaleDistribution FromShapeAndSummation (
		final double[] shapeParameterArray,
		final double scaleParameter,
		final R1ToR1 gammaEstimator,
		final R1ToR1 digammaEstimator,
		final R2ToR1 lowerIncompleteGammaEstimator)
	{
		if (null == shapeParameterArray) {
			return null;
		}

		double shapeParameter = 0.;

		if (0 == shapeParameterArray.length) {
			return null;
		}

		for (int shapeParameterIndex = 0;
			shapeParameterIndex < shapeParameterArray.length;
			++shapeParameterIndex)
		{
			if (!NumberUtil.IsValid (shapeParameterArray[shapeParameterIndex])) {
				return null;
			}

			shapeParameter += shapeParameterArray[shapeParameterIndex];
		}

		return R1ShapeScaleDistribution.Standard (
			shapeParameter,
			scaleParameter,
			gammaEstimator,
			digammaEstimator,
			lowerIncompleteGammaEstimator
		);
	}

	/**
	 * Construct the Standard <i>R1ShapeScaleDistribution</i> Instance
	 * 
	 * @param shapeParameter Shape Parameter
	 * @param scaleParameter Scale Parameter
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * 
	 * @return The <i>R1ShapeScaleDistribution</i> Instance
	 */

	public static final R1ShapeScaleDistribution Standard (
		final double shapeParameter,
		final double scaleParameter,
		final R1ToR1 gammaEstimator,
		final R1ToR1 digammaEstimator,
		final R2ToR1 lowerIncompleteGammaEstimator)
	{
		try {
			return new R1ShapeScaleDistribution (
				new ShapeScaleParameters (shapeParameter, scaleParameter),
				gammaEstimator,
				digammaEstimator,
				lowerIncompleteGammaEstimator
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private double randomMarsaglia1977 (
		final double shapeParameterIn)
		throws Exception
	{
		double shapeParameter = shapeParameterIn < 1. ? shapeParameterIn + 1. : shapeParameterIn;
		double d = shapeParameter - 1. / 3.;
		double v = 0.;
		double u = 0.;

		double c = 1. / Math.sqrt (9. * d);

		while (true) {
			double x = NormalQuadrature.Random();

			u = Math.random();

			v = 1. + c * x;
			v = v * v * v;

			if (v > 0. && 0.5 * x * x + d - d * v + d * Math.log (v) > Math.log (u)) {
				double marsagliaRandom = _shapeScaleParameters.scale() * d * v;

				return shapeParameter != shapeParameterIn ?
					marsagliaRandom * Math.pow (u, 1. / shapeParameterIn) : marsagliaRandom;
			}
		}
	}

	/**
	 * <i>R1ShapeScaleDistribution</i> Constructor
	 * 
	 * @param shapeScaleParameters Shape-Scale Parameters
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public R1ShapeScaleDistribution (
		final ShapeScaleParameters shapeScaleParameters,
		final R1ToR1 gammaEstimator,
		final R1ToR1 digammaEstimator,
		final R2ToR1 lowerIncompleteGammaEstimator)
		throws Exception
	{
		if (null == (_shapeScaleParameters = shapeScaleParameters) ||
			null == (_gammaEstimator = gammaEstimator) ||
			null == (_digammaEstimator = digammaEstimator) ||
			null == (_lowerIncompleteGammaEstimator = lowerIncompleteGammaEstimator))
		{
			throw new Exception ("R1ShapeScaleDistribution Constructor => Invalid Inputs");
		}

		double shape = _shapeScaleParameters.shape();

		_pdfScaler = (_cdfScaler = 1. / _gammaEstimator.evaluate (shape)) *
			Math.pow (_shapeScaleParameters.scale(), -1. * shape);
	}

	/**
	 * Retrieve the Shape-Scale Parameters
	 * 
	 * @return The Shape-Scale Parameters
	 */

	public ShapeScaleParameters shapeScaleParameters()
	{
		return _shapeScaleParameters;
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
			throw new Exception ("ShapeScaleDistribution::density => Variate not in Range");
		}

		return _pdfScaler * Math.pow (t, _shapeScaleParameters.shape() - 1.) *
			Math.exp (-1. * t / _shapeScaleParameters.scale());
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
			throw new Exception ("ShapeScaleDistribution::cumulative => Invalid Inputs");
		}

		return _cdfScaler * _lowerIncompleteGammaEstimator.evaluate (
			_shapeScaleParameters.shape(),
			t / _shapeScaleParameters.scale()
		);
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
		return _shapeScaleParameters.shape() * _shapeScaleParameters.scale();
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
		double shape = _shapeScaleParameters.shape();

		if (shape < 1.) {
			throw new Exception ("ShapeScaleDistribution::mode => No Closed Form Available");
		}

		return (shape - 1.) * _shapeScaleParameters.scale();
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
		double scale = _shapeScaleParameters.scale();

		return _shapeScaleParameters.shape() * scale * scale;
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
		return 2. * Math.sqrt (1. / _shapeScaleParameters.shape());
	}

	/**
	 * Retrieve the Excess Kurtosis of the Distribution
	 * 
	 * @return The Excess Kurtosis of the Distribution
	 * 
	 * @throws Exception Thrown if the Excess Kurtosis cannot be estimated
	 */

	@Override public double excessKurtosis()
		throws Exception
	{
		return 6. / _shapeScaleParameters.shape();
	}

	/**
	 * Retrieve the Differential Entropy of the Distribution
	 * 
	 * @return The Differential Entropy of the Distribution
	 * 
	 * @throws Exception Thrown if the Differential Entropy cannot be estimated
	 */

	@Override public double differentialEntropy()
		throws Exception
	{
		double shape = _shapeScaleParameters.shape();

		return shape + Math.log (_shapeScaleParameters.scale() / _cdfScaler) +
			(1. - shape) * _digammaEstimator.evaluate (shape);
	}

	/**
	 * Retrieve the Moment Generating Function of the Distribution
	 * 
	 * @return The Moment Generating Function of the Distribution
	 * 
	 * @throws Exception Thrown if the Moment Generating Function cannot be estimated
	 */

	@Override public R1ToR1 momentGeneratingFunction()
	{
		final double scale = _shapeScaleParameters.scale();

		return new R1ToR1 (null) {
			@Override public double evaluate (
				final double t)
				throws Exception
			{
				if (!NumberUtil.IsValid (t) || t >= 1. / scale) {
					throw new Exception (
						"ShapeScaleDistribution::momentGeneratingFunction::evaluate => Invalid Input"
					);
				}

				return Math.pow (1. - scale * t, -1. * _shapeScaleParameters.shape());
			}
		};
	}

	/**
	 * Retrieve the Central Limit Theorem Equivalent Normal Distribution Proxy
	 * 
	 * @return The Central Limit Theorem Equivalent Normal Distribution Proxy
	 */

	public R1UnivariateNormal cltProxy()
	{
		double scale = _shapeScaleParameters.scale();

		double shape = _shapeScaleParameters.shape();

		try {
			return new R1UnivariateNormal (shape * scale, scale * Math.sqrt (shape));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Logarithmic Expectation
	 * 
	 * @return The Logarithmic Expectation
	 * 
	 * @throws Exception Thrown if the Logarithmic Expectation cannot be computed
	 */

	public double logarithmicExpectation()
		throws Exception
	{
		return _digammaEstimator.evaluate (_shapeScaleParameters.shape()) -
			Math.log (_shapeScaleParameters.scale());
	}

	/**
	 * Compute the Banneheke-Ekayanake Approximation for the Median when k gte 1
	 * 
	 * @return The Banneheke-Ekayanake Approximation for the Median
	 * 
	 * @throws Exception Thrown if the Median cannot be computed
	 */

	public double bannehekeEkayanakeMedianApproximation()
		throws Exception
	{
		double shape = _shapeScaleParameters.shape();

		if (1. > shape) {
			throw new Exception (
				"ShapeScaleDistribution::bannehekeEkayanakeMedianApproximation => Invalid Shape Parameter"
			);
		}

		return (3. * shape - 0.8) / (3. * shape - 0.2) * mean();
	}

	/**
	 * Compute the Ramanujan-Choi Approximation for the Median
	 * 
	 * @return The Ramanujan-Choi Approximation for the Median
	 */

	public double ramanujanChoiMedianApproximation()
	{
		double shape = _shapeScaleParameters.shape();

		double inverseShapeParameter = 1. / shape;

		return shape - 1. / 3. + 8. * inverseShapeParameter / 405. +
			184. * inverseShapeParameter * inverseShapeParameter / 25515. +
			2248. * inverseShapeParameter * inverseShapeParameter * inverseShapeParameter / 3444525.;
	}

	/**
	 * Compute the Chen-Rubin Median Lower Bound
	 * 
	 * @return The Chen-Rubin Median Lower Bound
	 */

	public double chenRubinMedianLowerBound()
	{
		return _shapeScaleParameters.shape() - 1. / 3.;
	}

	/**
	 * Compute the Chen-Rubin Median Upper Bound
	 * 
	 * @return The Chen-Rubin Median Upper Bound
	 */

	public double chenRubinMedianUpperBound()
	{
		return _shapeScaleParameters.shape();
	}

	/**
	 * Generate a Scaled Gamma Distribution
	 * 
	 * @param scaleFactor The Gamma Distribution Scale Factor
	 * 
	 * @return Scaled Gamma Distribution
	 */

	public R1ShapeScaleDistribution scale (
		final double scaleFactor)
	{
		try {
			return new R1ShapeScaleDistribution (
				new ShapeScaleParameters (
					_shapeScaleParameters.shape(),
					_shapeScaleParameters.scale() * scaleFactor
				),
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
	 * Retrieve the Array of Natural Parameters
	 * 
	 * @return Array of Natural Parameters
	 */

	public double[] naturalParameters()
	{
		return new double[] {_shapeScaleParameters.shape() - 1, -1. / _shapeScaleParameters.scale()};
	}

	/**
	 * Retrieve the Array of Natural Statistics
	 * 
	 * @param x X
	 * 
	 * @return Array of Natural Statistics
	 */

	public double[] naturalStatistics (
		final double x)
	{
		return NumberUtil.IsValid (x) ? new double[] {x, Math.log (x)} : null;
	}

	/**
	 * Generate the Exponential Family Representation
	 * 
	 * @param x X
	 * 
	 * @return Exponential Family Representation
	 */

	public ExponentialFamilyRepresentation exponentialFamilyRepresentation (
		final double x)
	{
		try {
			return new ExponentialFamilyRepresentation (naturalParameters(), naturalStatistics (x));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Laplacian
	 * 
	 * @param s S
	 * 
	 * @return The Laplacian
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double laplacian (
		final double s)
		throws Exception
	{
		if (0. > s) {
			throw new Exception ("ShapeScaleDistribution::laplacian => Invalid Shape Parameter");
		}

		return Math.pow (1. + s * _shapeScaleParameters.scale(), -1. * _shapeScaleParameters.shape());
	}

	/**
	 * Generate a Random Variable using the Ahrens-Dieter (1982) Scheme
	 * 
	 * @return Random Variable using the Ahrens-Dieter (1982) Scheme
	 * 
	 * @throws Exception Thrown if the Random Instance cannot be estimated
	 */

	public double randomAhrensDieter1982()
		throws Exception
	{
		double shape = _shapeScaleParameters.shape();

		double eta = 0.;
		double random = 0.;
		double epsilon = 0.;
		int k = (int) shape;
		double delta = shape - k;

		for (int index = 0; index < k; ++index) {
			random = random - Math.log (Math.random());
		}

		if (0. == delta) {
			return random;
		}

		while (true) {
			double u = Math.random();

			double v = Math.random();

			double w = Math.random();

			if (u <= Math.E / (Math.E + delta)) {
				epsilon = Math.pow (v, 1. / delta);

				eta = w * Math.pow (epsilon, delta - 1.);
			} else {
				epsilon = 1. - Math.log (v);

				eta = w * Math.exp (-1. * epsilon);
			}

			if (eta <= Math.pow (epsilon, delta - 1.) * Math.exp (-1. * epsilon)) {
				break;
			}
		}

		return _shapeScaleParameters.scale() * (random + epsilon);
	}

	/**
	 * Generate a Random Variable using the Marsaglia (1977) Scheme
	 * 
	 * @return Random Variable using the Marsaglia (1977) Scheme
	 * 
	 * @throws Exception Thrown if the Random Instance cannot be estimated
	 */

	public double randomMarsaglia1977()
		throws Exception
	{
		return randomMarsaglia1977 (_shapeScaleParameters.shape());
	}
}
