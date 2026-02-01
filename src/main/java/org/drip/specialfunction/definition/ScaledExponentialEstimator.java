
package org.drip.specialfunction.definition;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.r1integration.NewtonCotesQuadratureGenerator;
import org.drip.specialfunction.gamma.Definitions;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>ScaledExponentialEstimator</i> exposes the Estimator for the Scaled (i.e., Stretched/Compressed)
 * 	Exponential Function. The References are:
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
 * 	It provides the following functionality:
 *
 *  <ul>
 * 		<li><i>ScaledExponentialEstimator</i> Constructor</li>
 * 		<li>Retrieve the Exponent</li>
 * 		<li>Retrieve the Characteristic Relaxation Time</li>
 * 		<li>Evaluate using the Relaxation Time Density</li>
 * 		<li>Indicate if the Function is Compressed Exponential</li>
 * 		<li>Indicate if the Function is Stretched Exponential</li>
 * 		<li>Indicate if the Function is Unscaled (i.e., Standard) Exponential</li>
 * 		<li>Indicate if the Function is Normal (i.e., Gaussian) Exponential</li>
 * 		<li>Compute the First Moment</li>
 * 		<li>Compute the Higher Moment</li>
 * 		<li>Compute the Higher Moment using the Relaxation Time Density</li>
 * 		<li>Compute the First Moment of Log Relaxation Time</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation and Analysis</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/definition/README.md">Definition of Special Function Estimators</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ScaledExponentialEstimator extends R1ToR1
{
	private double _exponent = Double.NaN;
	private double _characteristicRelaxationTime = Double.NaN;

	/**
	 * <i>ScaledExponentialEstimator</i> Constructor
	 * 
	 * @param exponent The Exponent
	 * @param characteristicRelaxationTime The Characteristic Relaxation Time
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public ScaledExponentialEstimator (
		final double exponent,
		final double characteristicRelaxationTime)
		throws Exception
	{
		super (null);

		if (!NumberUtil.IsValid (_exponent = exponent) || 0. > _exponent ||
			!NumberUtil.IsValid (_characteristicRelaxationTime = characteristicRelaxationTime) ||
			0. > _characteristicRelaxationTime) {
			throw new Exception ("ScaledExponentialEstimator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Exponent
	 * 
	 * @return The Exponent
	 */

	public double exponent()
	{
		return _exponent;
	}

	/**
	 * Retrieve the Characteristic Relaxation Time
	 * 
	 * @return The Characteristic Relaxation Time
	 */

	public double characteristicRelaxationTime()
	{
		return _characteristicRelaxationTime;
	}

	@Override public double evaluate (
		final double t)
		throws Exception
	{
		if (!NumberUtil.IsValid (t) || 0. > t) {
			throw new Exception ("ScaledExponentialEstimator::evaluate => Invalid Inputs");
		}

		return Math.exp (-1. * Math.pow (t / _characteristicRelaxationTime, _exponent));
	}

	/**
	 * Evaluate using the Relaxation Time Density
	 * 
	 * @param t Time
	 * @param relaxationTimeDistributionEstimator Relaxation Time Distribution Estimator
	 * 
	 * @return The Evaluation using the Relaxation Time Density
	 * 
	 * @throws Exception Thrown if the Evaluation cannot be done
	 */

	public double evaluateUsingDensity (
		final double t,
		final RelaxationTimeDistributionEstimator relaxationTimeDistributionEstimator)
		throws Exception
	{
		if (!NumberUtil.IsValid (t) || 0. > t || null == relaxationTimeDistributionEstimator) {
			throw new Exception ("ScaledExponentialEstimator::evaluateUsingDensity => Invalid Inputs");
		}

		return NewtonCotesQuadratureGenerator.GaussLaguerreLeftDefinite (0., 100).integrate (
			new R1ToR1 (null) {
				@Override public double evaluate (
					final double u)
					throws Exception
				{
					return Double.isInfinite (u) || 0. == u ? 0. : Math.exp (-t / u) *
						relaxationTimeDistributionEstimator.relaxationTimeDensity (u);
				}
			}
		);
	}

	/**
	 * Indicate if the Function is Compressed Exponential
	 * 
	 * @return TRUE - The Function is Compressed Exponential
	 */

	public boolean isCompressed()
	{
		return 1. < _exponent;
	}

	/**
	 * Indicate if the Function is Stretched Exponential
	 * 
	 * @return TRUE - The Function is Stretched Exponential
	 */

	public boolean isStretched()
	{
		return 1. > _exponent;
	}

	/**
	 * Indicate if the Function is Unscaled (i.e., Standard) Exponential
	 * 
	 * @return TRUE - The Function is Unscaled Exponential
	 */

	public boolean isUnscaled()
	{
		return 1. == _exponent;
	}

	/**
	 * Indicate if the Function is Normal (i.e., Gaussian) Exponential
	 * 
	 * @return TRUE - The Function is Normal Exponential
	 */

	public boolean isNormal()
	{
		return 2. == _exponent;
	}

	/**
	 * Compute the First Moment
	 * 
	 * @param gammaEstimator Gamma Estimator
	 * 
	 * @return The First Moment
	 * 
	 * @throws Exception Thrown if the First Moment cannot be calculated
	 */

	public double firstMoment (
		final R1ToR1 gammaEstimator)
		throws Exception
	{
		if (null == gammaEstimator) {
			throw new Exception ("ScaledExponentialEstimator::firstMoment => Invalid Inputs");
		}

		double inverseExponent = 1. / _exponent;

		return _characteristicRelaxationTime * inverseExponent * gammaEstimator.evaluate (inverseExponent);
	}

	/**
	 * Compute the Higher Moment
	 * 
	 * @param momentOrder The Moment Order
	 * @param gammaEstimator Gamma Estimator
	 * 
	 * @return The Higher Moment
	 * 
	 * @throws Exception Thrown if the Higher Moment cannot be calculated
	 */

	public double higherMoment (
		final int momentOrder,
		final R1ToR1 gammaEstimator)
		throws Exception
	{
		if (0 > momentOrder || null == gammaEstimator) {
			throw new Exception ("ScaledExponentialEstimator::higherMoment => Invalid Inputs");
		}

		double inverseExponent = 1. / _exponent;

		return Math.pow (_characteristicRelaxationTime, momentOrder) * inverseExponent *
			gammaEstimator.evaluate (momentOrder * inverseExponent);
	}

	/**
	 * Compute the Higher Moment using the Relaxation Time Density
	 * 
	 * @param momentOrder The Moment Order
	 * @param relaxationTimeDistributionEstimator Relaxation Time Distribution Estimator
	 * @param gammaEstimator Gamma Estimator
	 * 
	 * @return The Higher Moment using the Relaxation Time Density
	 * 
	 * @throws Exception Thrown if the Higher Moment cannot be calculated
	 */

	public double higherMomentUsingDensity (
		final int momentOrder,
		final RelaxationTimeDistributionEstimator relaxationTimeDistributionEstimator,
		final R1ToR1 gammaEstimator)
		throws Exception
	{
		if (0 > momentOrder || null == relaxationTimeDistributionEstimator || null == gammaEstimator) {
			throw new Exception ("ScaledExponentialEstimator::higherMomentUsingDensity => Invalid Inputs");
		}

		return NewtonCotesQuadratureGenerator.GaussLaguerreLeftDefinite (0., 100).integrate (
			new R1ToR1 (null) {
				@Override public double evaluate (
					final double t)
					throws Exception
				{
					return Double.isInfinite (t) || 0. == t ? 0. : Math.pow (t, momentOrder) *
						relaxationTimeDistributionEstimator.relaxationTimeDensity (t);
				}
			}
		) * gammaEstimator.evaluate (momentOrder + 1.) * Math.pow (
			_characteristicRelaxationTime,
			momentOrder
		);
	}

	/**
	 * Compute the First Moment of Log Relaxation Time
	 * 
	 * @return The First Moment of Log Relaxation Time
	 */

	public double logRelaxationFirstMoment()
	{
		return (1. - (1. / _exponent)) * Definitions.EULER_MASCHERONI +
			Math.log (_characteristicRelaxationTime);
	}
}
