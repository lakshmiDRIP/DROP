
package org.drip.specialfunction.definition;

import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.R2ToR1;
import org.drip.numerical.common.NumberUtil;

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
 * <i>BesselFirstKindEstimator</i> exposes the Estimator for the Bessel Function of the First Kind. The
 * References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Arfken, G. B., and H. J. Weber (2005): <i>Mathematical Methods for Physicists 6<sup>th</sup>
 * 				Edition</i> <b>Harcourt</b> San Diego
 * 		</li>
 * 		<li>
 * 			Temme N. M. (1996): <i>Special Functions: An Introduction to the Classical Functions of
 * 				Mathematical Physics 2<sup>nd</sup> Edition</i> <b>Wiley</b> New York
 * 		</li>
 * 		<li>
 * 			Watson, G. N. (1995): <i>A Treatise on the Theory of Bessel Functions</i> <b>Cambridge University
 * 				Press</b>
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Bessel Function https://en.wikipedia.org/wiki/Bessel_function
 * 		</li>
 * 	</ul>
 * 
 * 	It provides the following functionality:
 *
 *  <ul>
 * 		<li>Construct the Alpha Positive Integer Asymptotic Version of <i>BesselFirstKindEstimator</i></li>
 * 		<li>Construct the Alpha Negative Integer Asymptotic Version of <i>BesselFirstKindEstimator</i></li>
 * 		<li>Construct the High z Asymptotic Version of <i>BesselFirstKindEstimator</i></li>
 * 		<li>Construct the Alpha = 0 Negative z Asymptotic Version of <i>BesselFirstKindEstimator</i></li>
 * 		<li>Construct the Approximate Zero Alpha Bessel Estimator of the First Kind</li>
 * 		<li>Evaluate Bessel Function First Kind J given Alpha and z</li>
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

public abstract class BesselFirstKindEstimator implements R2ToR1
{

	/**
	 * Construct the Alpha Positive Integer or Zero Asymptotic Version of BesselFirstKindEstimator
	 * 
	 * @param gammaEstimator Gamma Estimator
	 * 
	 * @return Alpha Positive Integer or Zero Asymptotic Version of BesselFirstKindEstimator
	 */

	public static final BesselFirstKindEstimator AlphaPositiveIntegerOrZeroAsymptote (
		final R1ToR1 gammaEstimator)
	{
		return null == gammaEstimator ? null : new BesselFirstKindEstimator() {
			@Override public double bigJ (
				final double alpha,
				final double z)
				throws Exception
			{
				if (!NumberUtil.IsInteger (alpha) || 0. > alpha || !NumberUtil.IsValid (z)) {
					throw new Exception (
						"BesselFirstKindEstimator::AlphaPositiveIntegerOrZeroAsymptote => Invalid Inputs"
					);
				}

				return Math.pow (0.5 * z, alpha) / gammaEstimator.evaluate (alpha + 1.);
			}
		};
	}

	/**
	 * Construct the Alpha Negative Integer Asymptotic Version of BesselFirstKindEstimator
	 * 
	 * @param gammaEstimator Gamma Estimator
	 * 
	 * @return Alpha Negative Integer Asymptotic Version of BesselFirstKindEstimator
	 */

	public static final BesselFirstKindEstimator AlphaNegativeIntegerAsymptote (
		final R1ToR1 gammaEstimator)
	{
		return null == gammaEstimator ? null : new BesselFirstKindEstimator() {
			@Override public double bigJ (
				final double alpha,
				final double z)
				throws Exception
			{
				if (!NumberUtil.IsNegativeInteger (alpha) || !NumberUtil.IsValid (z)) {
					throw new Exception (
						"BesselFirstKindEstimator::AlphaNegativeIntegerAsymptote => Invalid Inputs"
					);
				}

				double negativeAlpha = -1. * alpha;

				return (0 == negativeAlpha % 2 ? 1. : -1.) *
					Math.pow (0.5 * z, negativeAlpha) / gammaEstimator.evaluate (negativeAlpha);
			}
		};
	}

	/**
	 * Construct the High z Asymptotic Version of BesselFirstKindEstimator
	 * 
	 * @return High z Asymptotic Version of BesselFirstKindEstimator
	 */

	public static final BesselFirstKindEstimator HighZAsymptote()
	{
		return new BesselFirstKindEstimator() {
			@Override public double bigJ (
				final double alpha,
				final double z)
				throws Exception
			{
				if (!NumberUtil.IsValid (alpha) || !NumberUtil.IsValid (z)) {
					throw new Exception ("BesselFirstKindEstimator::HighZAsymptote => Invalid Inputs");
				}

				return Math.sqrt (2. / Math.PI / z) * Math.cos (z - 0.5 * Math.PI * alpha - 0.25 * Math.PI);
			}
		};
	}

	/**
	 * Construct the Alpha = 0 Negative z Asymptotic Version of BesselFirstKindEstimator
	 * 
	 * @return Alpha = 0 Negative z Asymptotic Version of BesselFirstKindEstimator
	 */

	public static final BesselFirstKindEstimator AlphaZeroNegativeZAsymptote()
	{
		return new BesselFirstKindEstimator() {
			@Override public double bigJ (
				final double alpha,
				final double z)
				throws Exception
			{
				if (0. != alpha || !NumberUtil.IsValid (z) || 0. < z) {
					throw new Exception (
						"BesselFirstKindEstimator::AlphaZeroNegativeZAsymptote => Invalid Inputs"
					);
				}

				return Math.sqrt (-2. / Math.PI / z) * Math.cos (z + 0.25 * Math.PI);
			}
		};
	}

	/**
	 * Construct the Approximate Zero Alpha Bessel Estimator of the First Kind
	 * 
	 * @return The Approximate Zero Alpha Bessel Estimator of the First Kind
	 */

	public static final BesselFirstKindEstimator AlphaZeroApproximate()
	{
		return new BesselFirstKindEstimator()
		{
			@Override public double bigJ (
				final double alpha,
				final double z)
				throws Exception
			{
				if (0. != alpha || !NumberUtil.IsValid (z)) {
					throw new Exception ("BesselFirstKindEstimator::AlphaZeroApproximate => Invalid Inputs");
				}

				double oneOver_OnePlus__zOver7_Power20__ = 1. / (1 + Math.pow (z / 7., 20.));

				double zAbsolute = Math.abs (z);

				double zOver2 = z / 2.;
				double zSign = 0 == z ? 1. : zAbsolute / z;

				return oneOver_OnePlus__zOver7_Power20__ * (
					(1. + Math.cos (z)) / 6. + (Math.cos (zOver2) + Math.cos (Math.sqrt (3.) * zOver2)) / 3.
				) + (1. - oneOver_OnePlus__zOver7_Power20__) * Math.sqrt (2. / Math.PI / zAbsolute) *
					Math.cos (z -  0.25 * Math.PI * zSign);
			}
		};
	}

	/**
	 * Evaluate Bessel Function First Kind J given Alpha and z
	 * 
	 * @param alpha Alpha
	 * @param z Z
	 *  
	 * @return Bessel Function First Kind J Value
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public abstract double bigJ (
		final double alpha,
		final double z)
		throws Exception;

	@Override public double evaluate (
		final double alpha,
		final double z)
		throws Exception
	{
		return bigJ (alpha, z);
	}
}
