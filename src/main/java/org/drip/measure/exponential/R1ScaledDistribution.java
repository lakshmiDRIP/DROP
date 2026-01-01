
package org.drip.measure.exponential;

import org.drip.function.definition.R1ToR1;
import org.drip.measure.continuous.R1Distribution;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.integration.NewtonCotesQuadratureGenerator;
import org.drip.specialfunction.definition.ScaledExponentialEstimator;

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
 * <i>R1ScaledDistribution</i> implements the Probability Density Function for the Scaled R<sup>1</sup>
 *  Exponential Function. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Gradshteyn, I. S., I. M. Ryzhik, Y. V. Geronimus, M. Y. Tseytlin, and A. Jeffrey (2015):
 * 				<i>Tables of Integrals, Series, and Products</i> <b>Academic Press</b>
 * 		</li>
 * 		<li>
 * 			Hilfer, J. (2002): H-function Representations for Stretched Exponential Relaxation and non-Debye
 * 				Susceptibilities in Glassy Systems <i>Physical Review E</i> <b>65 (6)</b> 061510
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Stretched Exponential Function
 * 				https://en.wikipedia.org/wiki/Stretched_exponential_function
 * 		</li>
 * 		<li>
 * 			Wuttke, J. (2012): Laplace-Fourier Transform of the Stretched Exponential Function: Analytic
 * 				Error-Bounds, Double Exponential Transform, and Open Source Implementation <i>libkw</i>
 * 				<i>Algorithm</i> <b>5 (4)</b> 604-628
 * 		</li>
 * 		<li>
 * 			Zorn, R. (2002): Logarithmic Moments of Relaxation Time Distributions <i>Journal of Chemical
 * 				Physics</i> <b>116 (8)</b> 3204-3209
 * 		</li>
 * 	</ul>
 *
 * 	It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>R1ScaledDistribution</i> Constructor</li>
 * 		<li>Retrieve the Scaled Exponential Estimator</li>
 * 		<li>Retrieve the Gamma Estimator</li>
 * 		<li>Lay out the Support of the PDF Range</li>
 * 		<li>Compute the Density under the Distribution at the given Variate</li>
 * 		<li>Compute the cumulative under the distribution to the given value</li>
 * 		<li>Compute the Incremental under the Distribution between the 2 variates</li>
 * 		<li>Retrieve the Mean of the Distribution</li>
 * 		<li>Retrieve the Variance of the Distribution</li>
 * 		<li>Retrieve the Normalizer</li>
 * 		<li>Retrieve the "Lambda" Parameter</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/README.md">R<sup>1</sup> and R<sup>d</sup> Continuous Random Measure</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1ScaledDistribution
	extends R1Distribution
{
	private R1ToR1 _gammaEstimator = null;
	private double _normalizer = Double.NaN;
	private ScaledExponentialEstimator _scaledExponentialEstimator = null;

	/**
	 * <i>R1ScaledDistribution</i> Constructor
	 * 
	 * @param scaledExponentialEstimator Scaled Exponential Estimator
	 * @param gammaEstimator Gamma Estimator
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public R1ScaledDistribution (
		final ScaledExponentialEstimator scaledExponentialEstimator,
		final R1ToR1 gammaEstimator)
		throws Exception
	{
		if (null == (_scaledExponentialEstimator = scaledExponentialEstimator) ||
			null == (_gammaEstimator = gammaEstimator))
		{
			throw new Exception ("R1ScaledDistribution Constructor => Invalid Inputs");
		}

		_normalizer = 1. / _gammaEstimator.evaluate (1. + (1. / _scaledExponentialEstimator.exponent())) /
			_scaledExponentialEstimator.characteristicRelaxationTime();
	}

	/**
	 * Retrieve the Scaled Exponential Estimator
	 * 
	 * @return Scaled Exponential Estimator
	 */

	public ScaledExponentialEstimator scaledExponentialEstimator()
	{
		return _scaledExponentialEstimator;
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
		return _scaledExponentialEstimator.evaluate (t) * _normalizer;
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
			throw new Exception ("R1ScaledDistribution::cumulative => Invalid Inputs");
		}

		return NewtonCotesQuadratureGenerator.Zero_PlusOne (0., t, 100).integrate (
			new R1ToR1 (null) {
				@Override public double evaluate (
					final double u)
					throws Exception
				{
					return Double.isInfinite (u) || 0. == u ? 0. : _scaledExponentialEstimator.evaluate (u);
				}
			}
		) * _normalizer;
	}

	/**
	 * Compute the Incremental under the Distribution between the 2 variates
	 * 
	 * @param t1 Left Variate to which the cumulative is to be computed
	 * @param t2 Right Variate to which the cumulative is to be computed
	 * 
	 * @return The Incremental under the Distribution between the 2 variates
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	@Override public double incremental (
		final double t1,
		final double t2)
		throws Exception
	{
		if (NumberUtil.IsValid (t1) || 0. > t1 || !NumberUtil.IsValid (t2) || t1 > t2) {
			throw new Exception ("R1ScaledDistribution::incremental => Invalid Inputs");
		}

		return NewtonCotesQuadratureGenerator.Zero_PlusOne (t1, t2, 100).integrate (
			new R1ToR1 (null) {
				@Override public double evaluate (
					final double u)
					throws Exception
				{
					return Double.isInfinite (u) || 0. == u ? 0. : _scaledExponentialEstimator.evaluate (u);
				}
			}
		) * _normalizer;
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
		return _scaledExponentialEstimator.firstMoment (_gammaEstimator) * _normalizer;
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
		try {
			double mean = _scaledExponentialEstimator.firstMoment (_gammaEstimator) * _normalizer;

			return _scaledExponentialEstimator.higherMoment (2, _gammaEstimator) * _normalizer * _normalizer
				- mean * mean;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Double.NaN;
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
	 * Retrieve the "Lambda" Parameter
	 * 
	 * @return "Lambda" Parameter
	 */

	public double lambda()
	{
		return 1. / _scaledExponentialEstimator.characteristicRelaxationTime();
	}
}
