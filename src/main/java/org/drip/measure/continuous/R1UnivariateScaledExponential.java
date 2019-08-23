
package org.drip.measure.continuous;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>R1UnivariateScaledExponential</i> implements the Probability Density Function for the Scaled
 * Exponential Function. The References are:
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Project</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/ode/README.md">Special Function Ordinary Differential Equations</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1UnivariateScaledExponential extends org.drip.measure.continuous.R1Univariate
{
	private double _normalizer = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _gammaEstimator = null;
	private org.drip.specialfunction.definition.ScaledExponentialEstimator _scaledExponentialEstimator =
		null;

	/**
	 * R1UnivariateScaledExponential Constructor
	 * 
	 * @param scaledExponentialEstimator Scaled Exponential Estimator
	 * @param gammaEstimator Gamma Estimator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1UnivariateScaledExponential (
		final org.drip.specialfunction.definition.ScaledExponentialEstimator scaledExponentialEstimator,
		final org.drip.function.definition.R1ToR1 gammaEstimator)
		throws java.lang.Exception
	{
		if (null == (_scaledExponentialEstimator = scaledExponentialEstimator) ||
			null == (_gammaEstimator = gammaEstimator))
		{
			throw new java.lang.Exception ("R1UnivariateScaledExponential Constructor => Invalid Inputs");
		}

		_normalizer = 1. / _gammaEstimator.evaluate (1. + (1. / _scaledExponentialEstimator.exponent())) /
			_scaledExponentialEstimator.characteristicRelaxationTime();
	}

	/**
	 * Retrieve the Scaled Exponential Estimator
	 * 
	 * @return Scaled Exponential Estimator
	 */

	public org.drip.specialfunction.definition.ScaledExponentialEstimator scaledExponentialEstimator()
	{
		return _scaledExponentialEstimator;
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
		return _scaledExponentialEstimator.evaluate (t) * _normalizer;
	}

	@Override public double cumulative (
		final double t)
		throws java.lang.Exception
	{
		if (!supported (t))
		{
			throw new java.lang.Exception ("R1UnivariateScaledExponential::cumulative => Invalid Inputs");
		}

		return org.drip.numerical.integration.NewtonCotesQuadratureGenerator.Zero_PlusOne (
			0.,
			t,
			100
		).integrate (
			new org.drip.function.definition.R1ToR1 (null)
			{
				@Override public double evaluate (
					final double u)
					throws java.lang.Exception
				{
					return java.lang.Double.isInfinite (u) || 0. == u ? 0. :
						_scaledExponentialEstimator.evaluate (u);
				}
			}
		) * _normalizer;
	}

	@Override public double incremental (
		final double t1,
		final double t2)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (t1) || 0. > t1 ||
			!org.drip.numerical.common.NumberUtil.IsValid (t2) || t1 > t2)
		{
			throw new java.lang.Exception ("R1UnivariateScaledExponential::incremental => Invalid Inputs");
		}

		return org.drip.numerical.integration.NewtonCotesQuadratureGenerator.Zero_PlusOne (
			t1,
			t2,
			100
		).integrate (
			new org.drip.function.definition.R1ToR1 (null)
			{
				@Override public double evaluate (
					final double u)
					throws java.lang.Exception
				{
					return java.lang.Double.isInfinite (u) || 0. == u ? 0. :
						_scaledExponentialEstimator.evaluate (u);
				}
			}
		) * _normalizer;
	}

	@Override public double mean()
		throws java.lang.Exception
	{
		return _scaledExponentialEstimator.firstMoment (_gammaEstimator) * _normalizer;
	}

	@Override public double variance()
		throws java.lang.Exception
	{
		try
		{
			double mean = _scaledExponentialEstimator.firstMoment (_gammaEstimator) * _normalizer;

			return _scaledExponentialEstimator.higherMoment (
				2,
				_gammaEstimator
			) * _normalizer * _normalizer - mean * mean;
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return java.lang.Double.NaN;
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
