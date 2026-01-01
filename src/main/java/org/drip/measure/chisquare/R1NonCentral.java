
package org.drip.measure.chisquare;

import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.R2ToR1;
import org.drip.measure.continuous.R1Distribution;
import org.drip.numerical.common.NumberUtil;
import org.drip.specialfunction.definition.ModifiedBesselFirstKindEstimator;

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
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Construct the Standard Instance of <i>R1NonCentral</i></li>
 * 		<li><i>R1NonCentral</i> Constructor</li>
 * 		<li>Retrieve the R<sup>1</sup> Non-Central Parameters</li>
 * 		<li>Retrieve the Gamma Estimator</li>
 * 		<li>Retrieve the Digamma Estimator</li>
 * 		<li>Retrieve the Lower Incomplete Gamma Estimator</li>
 * 		<li>Retrieve the Modified Bessel First Kind Estimator</li>
 * 		<li>Lay out the Support of the PDF Range</li>
 * 		<li>Compute the Density under the Distribution at the given Variate</li>
 * 		<li>Compute the cumulative under the distribution to the given value</li>
 * 		<li>Retrieve the Mean of the Distribution</li>
 * 		<li>Retrieve the Variance of the Distribution</li>
 * 		<li>Retrieve the Skewness of the Distribution</li>
 * 		<li>Retrieve the Excess Kurtosis of the Distribution</li>
 * 		<li>Construct the Moment Generating Function</li>
 * 		<li>Compute the Cumulant</li>
 * 		<li>Compute the Leading Non-central Moments</li>
 * 		<li>Compute the Leading Central Moments</li>
 * 		<li>Compute the Non-central Moment</li>
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

public class R1NonCentral
	extends R1Distribution
{
	private R1ToR1 _gammaEstimator = null;
	private double _cdfScaler = Double.NaN;
	private R1ToR1 _digammaEstimator = null;
	private R1NonCentralParameters _parameters = null;
	private R2ToR1 _lowerIncompleteGammaEstimator = null;
	private ModifiedBesselFirstKindEstimator _modifiedBesselFirstKindEstimator = null;

	private double moment (
		final int n,
		final double[] fourLeadingRawMomentArray)
		throws Exception
	{
		if (n <= 3) {
			return fourLeadingRawMomentArray[n - 1];
		}

		double nonCentralMoment = cumulant (n);

		double degreesOfFreedom = _parameters.degreesOfFreedom();

		double nonCentralityParameter = _parameters.nonCentralityParameter();

		for (int nonCentralMomentIndex = 1; nonCentralMomentIndex < n; ++nonCentralMomentIndex) {
			nonCentralMoment = nonCentralMoment +
				_gammaEstimator.evaluate (n) * Math.pow (
					2.,
					nonCentralMomentIndex - 1.
				) * (
					degreesOfFreedom + nonCentralMomentIndex * nonCentralityParameter
				) * moment (
					n - nonCentralMomentIndex,
					fourLeadingRawMomentArray
				) / _gammaEstimator.evaluate (
					n - nonCentralMomentIndex + 1
				);
		}

		return nonCentralMoment;
	}

	/**
	 * Construct the Standard Instance of <i>R1NonCentral</i>
	 * 
	 * @param degreesOfFreedom Degrees of Freedom
	 * @param nonCentralityParameter Non-centrality Parameter
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * @param modifiedBesselFirstKindEstimator Modified Bessel First Kind Estimator
	 * 
	 * @return The Standard Instance of <i>R1NonCentral</i>
	 */

	public static final R1NonCentral Standard (
		final double degreesOfFreedom,
		final double nonCentralityParameter,
		final R1ToR1 gammaEstimator,
		final R1ToR1 digammaEstimator,
		final R2ToR1 lowerIncompleteGammaEstimator,
		final ModifiedBesselFirstKindEstimator modifiedBesselFirstKindEstimator)
	{
		try {
			return new R1NonCentral (
				new R1NonCentralParameters (degreesOfFreedom, nonCentralityParameter),
				gammaEstimator,
				digammaEstimator,
				lowerIncompleteGammaEstimator,
				modifiedBesselFirstKindEstimator
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>R1NonCentral</i> Constructor
	 * 
	 * @param parameters R<sup>1</sup> Non-central Parameters
	 * @param gammaEstimator Gamma Estimator
	 * @param digammaEstimator Digamma Estimator
	 * @param lowerIncompleteGammaEstimator Lower Incomplete Gamma Estimator
	 * @param modifiedBesselFirstKindEstimator Modified Bessel First Kind Estimator
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public R1NonCentral (
		final R1NonCentralParameters parameters,
		final R1ToR1 gammaEstimator,
		final R1ToR1 digammaEstimator,
		final R2ToR1 lowerIncompleteGammaEstimator,
		final ModifiedBesselFirstKindEstimator modifiedBesselFirstKindEstimator)
		throws Exception
	{
		if (null == (_parameters = parameters) ||
			null == (_gammaEstimator = gammaEstimator) ||
			null == (_digammaEstimator = digammaEstimator) ||
			null == (_lowerIncompleteGammaEstimator = lowerIncompleteGammaEstimator) ||
			null == (_modifiedBesselFirstKindEstimator = modifiedBesselFirstKindEstimator))
		{
			throw new Exception ("R1NonCentral Constructor => Invalid Inputs");
		}

		_cdfScaler = Math.exp (-0.5 * _parameters.nonCentralityParameter());
	}

	/**
	 * Retrieve the R<sup>1</sup> Non-Central Parameters
	 * 
	 * @return The R<sup>1</sup> Non-Central Parameters
	 */

	public R1NonCentralParameters parameters()
	{
		return _parameters;
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
	 * Retrieve the Modified Bessel First Kind Estimator
	 * 
	 * @return Modified Bessel First Kind Estimator
	 */

	public ModifiedBesselFirstKindEstimator modifiedBesselFirstKindEstimator()
	{
		return _modifiedBesselFirstKindEstimator;
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
		if (!supported (x)) {
			throw new Exception ("R1NonCentral::density => Variate not in Range");
		}

		double degreesOfFreedom = _parameters.degreesOfFreedom();

		double nonCentralityParameter = _parameters.nonCentralityParameter();

		return 0.5 * Math.pow (
			x / nonCentralityParameter,
			0.25 * degreesOfFreedom - 0.5
		) * Math.exp (
			-0.5 * (x + nonCentralityParameter)
		) * _modifiedBesselFirstKindEstimator.bigI (
			0.5 * degreesOfFreedom - 1.,
			Math.sqrt (x * nonCentralityParameter)
		);
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
			throw new Exception ("R1NonCentral::cumulative => Invalid Inputs");
		}

		int termCount = 10;
		double cumulative = 0.;

		double degreesOfFreedom = _parameters.degreesOfFreedom();

		double nonCentralityParameter = _parameters.nonCentralityParameter();

		for (int termIndex = 0; termIndex < termCount; ++termIndex) {
			cumulative = cumulative +
				Math.pow (
					0.5 * nonCentralityParameter,
					termIndex
				) * new R1Central (
					degreesOfFreedom + 2 * termIndex,
					_gammaEstimator,
					_digammaEstimator,
					_lowerIncompleteGammaEstimator
				).cumulative (
					t
				) / NumberUtil.Factorial (
					termIndex
				);
		}

		return _cdfScaler * cumulative;
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
		return _parameters.degreesOfFreedom() + _parameters.nonCentralityParameter();
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
		return 2. * _parameters.degreesOfFreedom() + 4. * _parameters.nonCentralityParameter();
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
		double degreesOfFreedom = _parameters.degreesOfFreedom();

		double nonCentralityParameter = _parameters.nonCentralityParameter();

		return (degreesOfFreedom + 3. * nonCentralityParameter) *
			Math.pow (2., degreesOfFreedom + 2. * nonCentralityParameter);
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
		double degreesOfFreedom = _parameters.degreesOfFreedom();

		double nonCentralityParameter = _parameters.nonCentralityParameter();

		double kPlusTwoLambda = degreesOfFreedom + 2. * nonCentralityParameter;
		return 12. * (degreesOfFreedom + 4. * nonCentralityParameter) / (kPlusTwoLambda* kPlusTwoLambda);
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
					throw new Exception (
						"R1NonCentral::momentGeneratingFunction::evaluate => Invalid Input"
					);
				}

				double oneMinusTwoT = 1. - 2. * t;

				return Math.exp (_parameters.nonCentralityParameter() * t / oneMinusTwoT) /
					Math.pow (oneMinusTwoT, 0.5 * _parameters.degreesOfFreedom());
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
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double cumulant (
		final int n)
		throws Exception
	{
		if (0 > n) {
			throw new Exception ("R1NonCentral::cumulant => Invalid Inputs");
		}

		return (_parameters.degreesOfFreedom() + n * _parameters.nonCentralityParameter()) *
			Math.pow (2., n - 1.) * _gammaEstimator.evaluate (n);
	}

	/**
	 * Compute the Leading Non-central Moments
	 * 
	 * @return Leading Non-central Moments
	 */

	public double[] leadingRawMoments()
	{
		double degreesOfFreedom = _parameters.degreesOfFreedom();

		double nonCentralityParameter = _parameters.nonCentralityParameter();

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
	 * Compute the Leading Central Moments
	 * 
	 * @return Leading Central Moments
	 */

	public double[] leadingCentralMoments()
	{
		double degreesOfFreedom = _parameters.degreesOfFreedom();

		double nonCentralityParameter = _parameters.nonCentralityParameter();

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
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double nonCentralMoment (
		final int n)
		throws Exception
	{
		if (0 >= n) {
			throw new Exception ("R1NonCentral::nonCentralMoment => Invalid Inputs");
		}

		return moment (n, leadingRawMoments());
	}
}
