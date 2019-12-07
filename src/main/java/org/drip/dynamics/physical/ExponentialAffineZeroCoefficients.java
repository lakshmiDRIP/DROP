
package org.drip.dynamics.physical;

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
 * <i>ExponentialAffineZeroCoefficients</i> contains the Exponential Affine Coefficients for a Zero-coupon
 * 	Bond priced using the CIR Process. The References are:
 *  
 * 	<br><br>
 *  <ul>
 * 		<li>
 * 			Doob, J. L. (1942): The Brownian Movement and Stochastic Equations <i>Annals of Mathematics</i>
 * 				<b>43 (2)</b> 351-369
 * 		</li>
 * 		<li>
 * 			Gardiner, C. W. (2009): <i>Stochastic Methods: A Handbook for the Natural and Social Sciences
 * 				4<sup>th</sup> Edition</i> <b>Springer-Verlag</b>
 * 		</li>
 * 		<li>
 * 			Kadanoff, L. P. (2000): <i>Statistical Physics: Statics, Dynamics, and Re-normalization</i>
 * 				<b>World Scientific</b>
 * 		</li>
 * 		<li>
 * 			Karatzas, I., and S. E. Shreve (1991): <i>Brownian Motion and Stochastic Calculus 2<sup>nd</sup>
 * 				Edition</i> <b>Springer-Verlag</b>
 * 		</li>
 * 		<li>
 * 			Risken, H., and F. Till (1996): <i>The Fokker-Planck Equation – Methods of Solution and
 * 				Applications</i> <b>Springer</b>
 * 		</li>
 *  </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">HJM, Hull White, LMM, and SABR Dynamic Evolution Models</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/physical/README.md">Implementation of Physical Process Dynamics</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ExponentialAffineZeroCoefficients
{
	private double _A = java.lang.Double.NaN;
	private double _B = java.lang.Double.NaN;

	/**
	 * Construct an Instance of ExponentialAffineZeroCoefficients using the CIR Stochastic Evolver
	 * 
	 * @param r1CIRStochasticEvolver The CIR Stochastic Evolver
	 * @param ttm The Time to Maturity
	 * 
	 * @return The ExponentialAffineZeroCoefficients Instance
	 */

	public static final ExponentialAffineZeroCoefficients FromCIR (
		final org.drip.dynamics.meanreverting.R1CIRStochasticEvolver r1CIRStochasticEvolver,
		final double ttm)
	{
		if (null == r1CIRStochasticEvolver ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				ttm
			) || 0. > ttm
		)
		{
			return null;
		}

		org.drip.dynamics.meanreverting.CKLSParameters cklsParameters =
			r1CIRStochasticEvolver.cklsParameters();

		double meanReversionSpeed = cklsParameters.meanReversionSpeed();

		double volatilityCoefficient = cklsParameters.volatilityCoefficient();

		double sigmaSquared = volatilityCoefficient * volatilityCoefficient;

		double h = java.lang.Math.sqrt (
			meanReversionSpeed * meanReversionSpeed + 2. * sigmaSquared
		);

		double hT = h * ttm;
		double twoH = 2 * h;
		double aPlusH = meanReversionSpeed + h;

		double exp_hT_MinusOne = java.lang.Math.exp (
			hT
		) - 1.;

		double inverseOfTwoHPlus_APlusHTimes__exp_hT_MinusOne__ = 1. / (
			twoH + aPlusH * (
				java.lang.Math.exp (
					hT
				) - 1.
			)
		);

		try
		{
			return new ExponentialAffineZeroCoefficients (
				java.lang.Math.pow (
					twoH * java.lang.Math.exp (
						0.5 * aPlusH * ttm
					) * inverseOfTwoHPlus_APlusHTimes__exp_hT_MinusOne__,
					2. * meanReversionSpeed * cklsParameters.meanReversionLevel() / sigmaSquared
				),
				2. * exp_hT_MinusOne * inverseOfTwoHPlus_APlusHTimes__exp_hT_MinusOne__
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ExponentialAffineZeroCoefficients Constructor
	 * 
	 * @param a Exponential Affine "A"
	 * @param b Exponential Affine "B"
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ExponentialAffineZeroCoefficients (
		final double a,
		final double b)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				_A = a
			) || !org.drip.numerical.common.NumberUtil.IsValid (
				_B = b
			)
		)
		{
			throw new java.lang.Exception (
				"ExponentialAffineZeroCoefficients Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve Exponential Affine "A"
	 * 
	 * @return The Exponential Affine "A"
	 */

	public double a()
	{
		return _A;
	}

	/**
	 * Retrieve Exponential Affine "B"
	 * 
	 * @return The Exponential Affine "B"
	 */

	public double b()
	{
		return _B;
	}

	/**
	 * Compute the Price given the Initial Rate
	 * 
	 * @param r0 The Initial Rate
	 * 
	 * @return Price given the Initial Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double price (
		final double r0)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
			r0
		))
		{
			throw new java.lang.Exception (
				"ExponentialAffineZeroCoefficients::price => Invalid Inputs"
			);
		}

		return _A * java.lang.Math.exp (
			-1. * _B * r0
		);
	}
}
