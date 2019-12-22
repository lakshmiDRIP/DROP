
package org.drip.measure.gamma;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/gamma/README.md">R<sup>1</sup> Gamma Distribution Implementation/Properties</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1ShapeScaleDistribution
	extends org.drip.measure.continuous.R1Univariate
{
	private double _cdfScaler = java.lang.Double.NaN;
	private double _pdfScaler = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _gammaEstimator = null;
	private org.drip.function.definition.R1ToR1 _digammaEstimator = null;
	private org.drip.measure.gamma.ShapeScaleParameters _shapeScaleParameters = null;
	private org.drip.function.definition.R2ToR1 _lowerIncompleteGammaEstimator = null;

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

	public static final R1ShapeScaleDistribution ShapeRate (
		final double shapeParameter,
		final double rateParameter,
		final org.drip.function.definition.R1ToR1 gammaEstimator,
		final org.drip.function.definition.R1ToR1 digammaEstimator,
		final org.drip.function.definition.R2ToR1 lowerIncompleteGammaEstimator)
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
	 * Shape Summation Based ShapeScaleDistribution
	 * 
	 * @param shapeParameterArray Shape Parameter Array
	 * @param scaleParameter Scale Parameter
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * 
	 * @return Shape Summation Based ShapeScaleDistribution
	 */

	public static final R1ShapeScaleDistribution ShapeSummation (
		final double[] shapeParameterArray,
		final double scaleParameter,
		final org.drip.function.definition.R1ToR1 gammaEstimator,
		final org.drip.function.definition.R1ToR1 digammaEstimator,
		final org.drip.function.definition.R2ToR1 lowerIncompleteGammaEstimator)
	{
		if (null == shapeParameterArray)
		{
			return null;
		}

		double shapeParameter = 0.;
		int shapeParameterArraySize = shapeParameterArray.length;

		if (0 == shapeParameterArraySize)
		{
			return null;
		}

		for (int shapeParameterIndex = 0;
			shapeParameterIndex < shapeParameterArraySize;
			++shapeParameterIndex)
		{
			if (!org.drip.numerical.common.NumberUtil.IsValid (
				shapeParameterArray[shapeParameterIndex]
			))
			{
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
	 * Construct the Standard R1ShapeScaleDistribution Instance
	 * 
	 * @param shapeParameter Shape Parameter
	 * @param scaleParameter Scale Parameter
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * 
	 * @return The R1ShapeScaleDistribution Instance
	 */

	public static final R1ShapeScaleDistribution Standard (
		final double shapeParameter,
		final double scaleParameter,
		final org.drip.function.definition.R1ToR1 gammaEstimator,
		final org.drip.function.definition.R1ToR1 digammaEstimator,
		final org.drip.function.definition.R2ToR1 lowerIncompleteGammaEstimator)
	{
		try
		{
			return new R1ShapeScaleDistribution (
				new org.drip.measure.gamma.ShapeScaleParameters (
					shapeParameter,
					scaleParameter
				),
				gammaEstimator,
				digammaEstimator,
				lowerIncompleteGammaEstimator
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private double randomMarsaglia1977 (
		final double shapeParameterIn)
		throws java.lang.Exception
	{
		double shapeParameter = shapeParameterIn < 1. ? shapeParameterIn + 1. : shapeParameterIn;
		double d = shapeParameter - 1. / 3.;
		double v = 0.;
		double u = 0.;

		double c = 1. / java.lang.Math.sqrt (
			9. * d
		);

		while (true)
		{
			double x = org.drip.measure.gaussian.NormalQuadrature.Random();

			u = java.lang.Math.random();

			v = 1. + c * x;
			v = v * v * v;

			if (v > 0. &&
				0.5 * x * x + d - d * v + d * java.lang.Math.log (
					v
				) > java.lang.Math.log (
					u
				)
			)
			{
				double marsagliaRandom =_shapeScaleParameters.scale() * d * v;

				return shapeParameter != shapeParameterIn ?
					marsagliaRandom * java.lang.Math.pow (
						u,
						1. / shapeParameterIn
					) : marsagliaRandom;
			}
		}
	}

	/**
	 * R1ShapeScaleDistribution Constructor
	 * 
	 * @param shapeScaleParameters Shape-Scale Parameters
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1ShapeScaleDistribution (
		final org.drip.measure.gamma.ShapeScaleParameters shapeScaleParameters,
		final org.drip.function.definition.R1ToR1 gammaEstimator,
		final org.drip.function.definition.R1ToR1 digammaEstimator,
		final org.drip.function.definition.R2ToR1 lowerIncompleteGammaEstimator)
		throws java.lang.Exception
	{
		if (null == (_shapeScaleParameters = shapeScaleParameters) ||
			null == (_gammaEstimator = gammaEstimator) ||
			null == (_digammaEstimator = digammaEstimator) ||
			null == (_lowerIncompleteGammaEstimator = lowerIncompleteGammaEstimator)
		)
		{
			throw new java.lang.Exception (
				"R1ShapeScaleDistribution Constructor => Invalid Inputs"
			);
		}

		double shape = _shapeScaleParameters.shape();

		_pdfScaler = (
			_cdfScaler = 1. / _gammaEstimator.evaluate (
				shape
			)
		) * java.lang.Math.pow (
			_shapeScaleParameters.scale(),
			-1. * shape
		);
	}

	/**
	 * Retrieve the Shape-Scale Parameters
	 * 
	 * @return The Shape-Scale Parameters
	 */

	public org.drip.measure.gamma.ShapeScaleParameters shapeScaleParameters()
	{
		return _shapeScaleParameters;
	}

	/**
	 * Retrieve the Gamma Estimator
	 * 
	 * @return Gamma Estimator
	 */

	public org.drip.function.definition.R1ToR1 gammaEstimator()
	{
		return _gammaEstimator;
	}

	/**
	 * Retrieve the Digamma Estimator
	 * 
	 * @return Digamma Estimator
	 */

	public org.drip.function.definition.R1ToR1 digammaEstimator()
	{
		return _digammaEstimator;
	}

	/**
	 * Retrieve the Lower Incomplete Gamma Estimator
	 * 
	 * @return Lower Incomplete Gamma Estimator
	 */

	public org.drip.function.definition.R2ToR1 lowerIncompleteGammaEstimator()
	{
		return _lowerIncompleteGammaEstimator;
	}

	@Override public double[] support()
	{
		return new double[]
		{
			0.,
			java.lang.Double.POSITIVE_INFINITY
		};
	}

	@Override public double density (
		final double t)
		throws java.lang.Exception
	{
		if (!supported (
			t
		))
		{
			throw new java.lang.Exception (
				"ShapeScaleDistribution::density => Variate not in Range"
			);
		}

		return _pdfScaler * java.lang.Math.pow (
			t,
			_shapeScaleParameters.shape() - 1.
		) * java.lang.Math.exp (
			-1. * t / _shapeScaleParameters.scale()
		);
	}

	@Override public double cumulative (
		final double t)
		throws java.lang.Exception
	{
		if (!supported (
			t
		))
		{
			throw new java.lang.Exception (
				"ShapeScaleDistribution::cumulative => Invalid Inputs"
			);
		}

		return _cdfScaler * _lowerIncompleteGammaEstimator.evaluate (
			_shapeScaleParameters.shape(),
			t / _shapeScaleParameters.scale()
		);
	}

	@Override public double mean()
		throws java.lang.Exception
	{
		return _shapeScaleParameters.shape() * _shapeScaleParameters.scale();
	}

	@Override public double mode()
		throws java.lang.Exception
	{
		double shape = _shapeScaleParameters.shape();

		if (shape < 1.)
		{
			throw new java.lang.Exception (
				"ShapeScaleDistribution::mode => No Closed Form Available"
			);
		}

		return (shape - 1.) * _shapeScaleParameters.scale();
	}

	@Override public double variance()
		throws java.lang.Exception
	{
		double scale = _shapeScaleParameters.scale();

		return _shapeScaleParameters.shape() * scale * scale;
	}

	@Override public double skewness()
		throws java.lang.Exception
	{
		return 2. * java.lang.Math.sqrt (1. / _shapeScaleParameters.shape());
	}

	@Override public double excessKurtosis()
		throws java.lang.Exception
	{
		return 6. / _shapeScaleParameters.shape();
	}

	@Override public double differentialEntropy()
		throws java.lang.Exception
	{
		double shape = _shapeScaleParameters.shape();

		return shape + java.lang.Math.log (
			_shapeScaleParameters.scale() / _cdfScaler
		) + (1. - shape) * _digammaEstimator.evaluate (
			shape
		);
	}

	@Override public org.drip.function.definition.R1ToR1 momentGeneratingFunction()
	{
		final double scale = _shapeScaleParameters.scale();

		return new org.drip.function.definition.R1ToR1 (
			null
		)
		{
			@Override public double evaluate (
				final double t)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (
						t
					) || t >= 1. / scale
				)
				{
					throw new java.lang.Exception (
						"ShapeScaleDistribution::momentGeneratingFunction::evaluate => Invalid Input"
					);
				}

				return java.lang.Math.pow (
					1. - scale * t,
					-1. * _shapeScaleParameters.shape()
				);
			}
		};
	}

	/**
	 * Retrieve the Central Limit Theorem Equivalent Normal Distribution Proxy
	 * 
	 * @return The Central Limit Theorem Equivalent Normal Distribution Proxy
	 */

	public org.drip.measure.gaussian.R1UnivariateNormal cltProxy()
	{
		double scale = _shapeScaleParameters.scale();

		double shape = _shapeScaleParameters.shape();

		try
		{
			return new org.drip.measure.gaussian.R1UnivariateNormal (
				shape * scale,
				scale * java.lang.Math.sqrt (
					shape
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Logarithmic Expectation
	 * 
	 * @return The Logarithmic Expectation
	 * 
	 * @throws java.lang.Exception Thrown if the Logarithmic Expectation cannot be computed
	 */

	public double logarithmicExpectation()
		throws java.lang.Exception
	{
		return _digammaEstimator.evaluate (
			_shapeScaleParameters.shape()
		) - java.lang.Math.log (
			_shapeScaleParameters.scale()
		);
	}

	/**
	 * Compute the Banneheke-Ekayanake Approximation for the Median when k gte 1
	 * 
	 * @return The Banneheke-Ekayanake Approximation for the Median
	 * 
	 * @throws java.lang.Exception Thrown if the Median cannot be computed
	 */

	public double bannehekeEkayanakeMedianApproximation()
		throws java.lang.Exception
	{
		double shape = _shapeScaleParameters.shape();

		if (1. > shape)
		{
			throw new java.lang.Exception (
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

		return shape - 1. / 3. +
			8. * inverseShapeParameter / 405. +
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
		try
		{
			return new R1ShapeScaleDistribution (
				new org.drip.measure.gamma.ShapeScaleParameters (
					_shapeScaleParameters.shape(),
					_shapeScaleParameters.scale() * scaleFactor
				),
				_gammaEstimator,
				_digammaEstimator,
				_lowerIncompleteGammaEstimator
			);
		}
		catch (java.lang.Exception e)
		{
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
		return new double[]
		{
			_shapeScaleParameters.shape() - 1,
			-1. / _shapeScaleParameters.scale()
		};
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
		return org.drip.numerical.common.NumberUtil.IsValid (
			x
		) ? new double[]
		{
			x,
			java.lang.Math.log (
				x
			)
		} : null;
	}

	/**
	 * Generate the Exponential Family Representation
	 * 
	 * @param x X
	 * 
	 * @return Exponential Family Representation
	 */

	public org.drip.measure.gamma.ExponentialFamilyRepresentation exponentialFamilyRepresentation (
		final double x)
	{
		try
		{
			return new org.drip.measure.gamma.ExponentialFamilyRepresentation (
				naturalParameters(),
				naturalStatistics (
					x
				)
			);
		}
		catch (java.lang.Exception e)
		{
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
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double laplacian (
		final double s)
		throws java.lang.Exception
	{
		if (0. > s)
		{
			throw new java.lang.Exception (
				"ShapeScaleDistribution::laplacian => Invalid Shape Parameter"
			);
		}

		return java.lang.Math.pow (
			1. + s * _shapeScaleParameters.scale(),
			-1. * _shapeScaleParameters.shape()
		);
	}

	/**
	 * Generate a Random Variable using the Ahrens-Dieter (1982) Scheme
	 * 
	 * @return Random Variable using the Ahrens-Dieter (1982) Scheme
	 * 
	 * @throws java.lang.Exception Thrown if the Random Instance cannot be estimated
	 */

	public double randomAhrensDieter1982()
		throws java.lang.Exception
	{
		double shape = _shapeScaleParameters.shape();

		double eta = 0.;
		double random = 0.;
		double epsilon = 0.;
		int k = (int) shape;
		double delta = shape - k;

		for (int index = 0;
			index < k;
			++index)
		{
			random = random - java.lang.Math.log (
				java.lang.Math.random()
			);
		}

		if (0. == delta)
		{
			return random;
		}

		while (true)
		{
			double u = java.lang.Math.random();

			double v = java.lang.Math.random();

			double w = java.lang.Math.random();

			if (u <= java.lang.Math.E / (java.lang.Math.E + delta))
			{
				epsilon = java.lang.Math.pow (
					v,
					1. / delta
				);

				eta = w * java.lang.Math.pow (
					epsilon,
					delta - 1.
				);
			}
			else
			{
				epsilon = 1. - java.lang.Math.log (
					v
				);

				eta = w * java.lang.Math.exp (
					-1. * epsilon
				);
			}

			if (eta <= java.lang.Math.pow (
				epsilon,
				delta - 1.
			) * java.lang.Math.exp (
				-1. * epsilon
				)
			)
			{
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
	 * @throws java.lang.Exception Thrown if the Random Instance cannot be estimated
	 */

	public double randomMarsaglia1977()
		throws java.lang.Exception
	{
		return randomMarsaglia1977 (
			_shapeScaleParameters.shape()
		);
	}
}
