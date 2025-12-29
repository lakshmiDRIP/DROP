
package org.drip.measure.chisquare;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>R1NonCentral</i> implements the Distribution Table for the R<sup>1</sup> Non-central Chi-Square
 * 	Distribution. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Johnson, N. L., S. Kotz, and N. Balakrishnan (1995): <i>Continuous Univariate Distributions
 * 				2<sup>nd</sup> Edition</i> <b>John Wiley and Sons</b>
 * 		</li>
 * 		<li>
 * 			Muirhead, R. (2005): <i>Aspects of Multivariate Statistical Theory 2<sup>nd</sup> Edition</i>
 * 				<b>Wiley</b>
 * 		</li>
 * 		<li>
 * 			Non-central Chi-Squared Distribution (2019): Chi-Squared Function
 * 				https://en.wikipedia.org/wiki/Noncentral_chi-squared_distribution
 * 		</li>
 * 		<li>
 * 			Sankaran, M. (1963): Approximations to the Non-Central Chi-Square Distribution <i>Biometrika</i>
 * 				<b>50 (1-2)</b> 199-204
 * 		</li>
 * 		<li>
 * 			Young, D. S. (2010): tolerance: An R Package for Estimating Tolerance Intervals <i>Journal of
 * 				Statistical Software</i> <b>36 (5)</b> 1-39
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/chisquare/README.md">Chi-Square Distribution Implementation/Properties</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1NonCentral
	extends org.drip.measure.continuous.R1Distribution
{
	private double _cdfScaler = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _gammaEstimator = null;
	private org.drip.function.definition.R1ToR1 _digammaEstimator = null;
	private org.drip.function.definition.R2ToR1 _lowerIncompleteGammaEstimator = null;
	private org.drip.measure.chisquare.R1NonCentralParameters _r1NonCentralParameters = null;
	private org.drip.specialfunction.definition.ModifiedBesselFirstKindEstimator
		_modifiedBesselFirstKindEstimator = null;

	private double nonCentralMoment (
		final int n,
		final double[] fourLeadingRawMoments)
		throws java.lang.Exception
	{
		if (n <= 3)
		{
			return fourLeadingRawMoments[n - 1];
		}

		double nonCentralMoment = cumulant (
			n
		);

		double degreesOfFreedom = _r1NonCentralParameters.degreesOfFreedom();

		double nonCentralityParameter = _r1NonCentralParameters.nonCentralityParameter();

		for (int j = 1;
			j < n;
			++j)
		{
			nonCentralMoment = nonCentralMoment +
				_gammaEstimator.evaluate (
					n
				) * java.lang.Math.pow (
					2.,
					j - 1.
				) * (
					degreesOfFreedom + j * nonCentralityParameter
				) * nonCentralMoment (
					n - j,
					fourLeadingRawMoments
				) / _gammaEstimator.evaluate (
					n - j + 1
				);
		}

		return nonCentralMoment;
	}

	/**
	 * Construct the Standard Instance of R1NonCentral
	 * 
	 * @param degreesOfFreedom Degrees of Freedom
	 * @param nonCentralityParameter Non-centrality Parameter
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * @param modifiedBesselFirstKindEstimator Modified Bessel First Kind Estimator
	 * 
	 * @return The Standard Instance of R1NonCentral
	 */

	public static final R1NonCentral Standard (
		final double degreesOfFreedom,
		final double nonCentralityParameter,
		final org.drip.function.definition.R1ToR1 gammaEstimator,
		final org.drip.function.definition.R1ToR1 digammaEstimator,
		final org.drip.function.definition.R2ToR1 lowerIncompleteGammaEstimator,
		final org.drip.specialfunction.definition.ModifiedBesselFirstKindEstimator
			modifiedBesselFirstKindEstimator)
	{
		try
		{
			return new R1NonCentral (
				new org.drip.measure.chisquare.R1NonCentralParameters (
					degreesOfFreedom,
					nonCentralityParameter
				),
				gammaEstimator,
				digammaEstimator,
				lowerIncompleteGammaEstimator,
				modifiedBesselFirstKindEstimator
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * R1NonCentral Constructor
	 * 
	 * @param r1NonCentralParameters R<sup>1</sup> Non-central Parameters
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * @param modifiedBesselFirstKindEstimator Modified Bessel First Kind Estimator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1NonCentral (
		final org.drip.measure.chisquare.R1NonCentralParameters r1NonCentralParameters,
		final org.drip.function.definition.R1ToR1 gammaEstimator,
		final org.drip.function.definition.R1ToR1 digammaEstimator,
		final org.drip.function.definition.R2ToR1 lowerIncompleteGammaEstimator,
		final org.drip.specialfunction.definition.ModifiedBesselFirstKindEstimator
			modifiedBesselFirstKindEstimator)
		throws java.lang.Exception
	{
		if (null == (_r1NonCentralParameters = r1NonCentralParameters) ||
			null == (_gammaEstimator = gammaEstimator) ||
			null == (_digammaEstimator = digammaEstimator) ||
			null == (_lowerIncompleteGammaEstimator = lowerIncompleteGammaEstimator) ||
			null == (_modifiedBesselFirstKindEstimator = modifiedBesselFirstKindEstimator)
		)
		{
			throw new java.lang.Exception (
				"R1NonCentral Constructor => Invalid Inputs"
			);
		}

		_cdfScaler = java.lang.Math.exp (
			-0.5 * _r1NonCentralParameters.nonCentralityParameter()
		);
	}

	/**
	 * Retrieve the R<sup>1</sup> Non-Central Parameters
	 * 
	 * @return The R<sup>1</sup> Non-Central Parameters
	 */

	public org.drip.measure.chisquare.R1NonCentralParameters parameters()
	{
		return _r1NonCentralParameters;
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

	/**
	 * Retrieve the Modified Bessel First Kind Estimator
	 * 
	 * @return Modified Bessel First Kind Estimator
	 */

	public org.drip.specialfunction.definition.ModifiedBesselFirstKindEstimator
		modifiedBesselFirstKindEstimator()
	{
		return _modifiedBesselFirstKindEstimator;
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
		final double x)
		throws java.lang.Exception
	{
		if (!supported (
			x
		))
		{
			throw new java.lang.Exception (
				"R1NonCentral::density => Variate not in Range"
			);
		}

		double degreesOfFreedom = _r1NonCentralParameters.degreesOfFreedom();

		double nonCentralityParameter = _r1NonCentralParameters.nonCentralityParameter();

		return 0.5 * java.lang.Math.pow (
			x / nonCentralityParameter,
			0.25 * degreesOfFreedom - 0.5
		) * java.lang.Math.exp (
			-0.5 * (x + nonCentralityParameter)
		) * _modifiedBesselFirstKindEstimator.bigI (
			0.5 * degreesOfFreedom - 1.,
			java.lang.Math.sqrt (
				x * nonCentralityParameter
			)
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
				"R1NonCentral::cumulative => Invalid Inputs"
			);
		}

		int termCount = 10;
		double cumulative = 0.;

		double degreesOfFreedom = _r1NonCentralParameters.degreesOfFreedom();

		double nonCentralityParameter = _r1NonCentralParameters.nonCentralityParameter();

		for (int termIndex = 0;
			termIndex < termCount;
			++termIndex)
		{
			cumulative = cumulative +
				java.lang.Math.pow (
					0.5 * nonCentralityParameter,
					termIndex
				) * new org.drip.measure.chisquare.R1Central (
					degreesOfFreedom + 2 * termIndex,
					_gammaEstimator,
					_digammaEstimator,
					_lowerIncompleteGammaEstimator
				).cumulative (
					t
				) / org.drip.numerical.common.NumberUtil.Factorial (
					termIndex
				);
		}

		return _cdfScaler * cumulative;
	}

	@Override public double mean()
		throws java.lang.Exception
	{
		return _r1NonCentralParameters.degreesOfFreedom() + _r1NonCentralParameters.nonCentralityParameter();
	}

	@Override public double variance()
		throws java.lang.Exception
	{
		return 2. * _r1NonCentralParameters.degreesOfFreedom() +
			4. * _r1NonCentralParameters.nonCentralityParameter();
	}

	@Override public double skewness()
		throws java.lang.Exception
	{
		double degreesOfFreedom = _r1NonCentralParameters.degreesOfFreedom();

		double nonCentralityParameter = _r1NonCentralParameters.nonCentralityParameter();

		return (degreesOfFreedom + 3. * nonCentralityParameter) * java.lang.Math.pow (
			2.,
			degreesOfFreedom + 2. * nonCentralityParameter
		);
	}

	@Override public double excessKurtosis()
		throws java.lang.Exception
	{
		double degreesOfFreedom = _r1NonCentralParameters.degreesOfFreedom();

		double nonCentralityParameter = _r1NonCentralParameters.nonCentralityParameter();

		double kPlusTwoLambda = degreesOfFreedom + 2. * nonCentralityParameter;
		return 12. * (degreesOfFreedom + 4. * nonCentralityParameter) /
			(kPlusTwoLambda* kPlusTwoLambda);
	}

	@Override public org.drip.function.definition.R1ToR1 momentGeneratingFunction()
	{
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
					) || t > 0.5
				)
				{
					throw new java.lang.Exception (
						"R1NonCentral::momentGeneratingFunction::evaluate => Invalid Input"
					);
				}

				double oneMinusTwoT = 1. - 2. * t;

				return java.lang.Math.exp (
					_r1NonCentralParameters.nonCentralityParameter() * t / oneMinusTwoT
				) / java.lang.Math.pow (
					oneMinusTwoT,
					0.5 * _r1NonCentralParameters.degreesOfFreedom()
				);
			}
		};
	}

	/**
	 * Compute the Cumulant
	 * 
	 * @param n Cumulant Index
	 * 
	 * @return The Cumulant
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double cumulant (
		final int n)
		throws java.lang.Exception
	{
		if (0 > n)
		{
			throw new java.lang.Exception (
				"R1NonCentral::cumulant => Invalid Inputs"
			);
		}

		return (
			_r1NonCentralParameters.degreesOfFreedom() + n * _r1NonCentralParameters.nonCentralityParameter()
		) * java.lang.Math.pow (
			2.,
			n - 1.
		) * _gammaEstimator.evaluate (
			n
		);
	}

	/**
	 * Compute the Leading Non-central Moments
	 * 
	 * @return Leading Non-central Moments
	 */

	public double[] leadingRawMoments()
	{
		double degreesOfFreedom = _r1NonCentralParameters.degreesOfFreedom();

		double nonCentralityParameter = _r1NonCentralParameters.nonCentralityParameter();

		double[] fourLeadingRawMomentArray = new double[4];
		fourLeadingRawMomentArray[0] = nonCentralityParameter + degreesOfFreedom;
		fourLeadingRawMomentArray[1] =
			fourLeadingRawMomentArray[0] * fourLeadingRawMomentArray[0] +
			2. * (nonCentralityParameter + 2. * degreesOfFreedom);
		fourLeadingRawMomentArray[2] =
			fourLeadingRawMomentArray[0] * fourLeadingRawMomentArray[0] * fourLeadingRawMomentArray[0] +
			6. * fourLeadingRawMomentArray[0] * (nonCentralityParameter + 2. * degreesOfFreedom) +
			8. * (nonCentralityParameter + 3. * degreesOfFreedom);
		fourLeadingRawMomentArray[3] =
			fourLeadingRawMomentArray[0] * fourLeadingRawMomentArray[0] * fourLeadingRawMomentArray[0] *
				fourLeadingRawMomentArray[0] +
			12. * fourLeadingRawMomentArray[0] * fourLeadingRawMomentArray[0] *
				(nonCentralityParameter + 2. * degreesOfFreedom) +
			4. * (
				11. * degreesOfFreedom * degreesOfFreedom +
				44. * degreesOfFreedom * nonCentralityParameter +
				36. * nonCentralityParameter * nonCentralityParameter
			) +
			48. * (nonCentralityParameter + 4. * degreesOfFreedom);
		return fourLeadingRawMomentArray;
	}

	/**
	 * Compute the Leading central Moments
	 * 
	 * @return Leading central Moments
	 */

	public double[] leadingCentralMoments()
	{
		double degreesOfFreedom = _r1NonCentralParameters.degreesOfFreedom();

		double nonCentralityParameter = _r1NonCentralParameters.nonCentralityParameter();

		double[] fourLeadingCentralMomentArray = new double[4];
		fourLeadingCentralMomentArray[0] = 0.;
		fourLeadingCentralMomentArray[1] =
			2. * (nonCentralityParameter + 2. * degreesOfFreedom);
		fourLeadingCentralMomentArray[2] =
			8. * (nonCentralityParameter + 3. * degreesOfFreedom);
		fourLeadingCentralMomentArray[3] =
			12. * (nonCentralityParameter + 2. * degreesOfFreedom) *
				(nonCentralityParameter + 2. * degreesOfFreedom) +
			48. * (nonCentralityParameter + 4. * degreesOfFreedom);
		return fourLeadingCentralMomentArray;
	}

	/**
	 * Compute the Non-central Moment
	 * 
	 * @param n Moment Index
	 * 
	 * @return The Non-central Moment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double nonCentralMoment (
		final int n)
		throws java.lang.Exception
	{
		if (0 >= n)
		{
			throw new java.lang.Exception (
				"R1NonCentral::nonCentralMoment => Invalid Inputs"
			);
		}

		return nonCentralMoment (
			n,
			leadingRawMoments()
		);
	}
}
