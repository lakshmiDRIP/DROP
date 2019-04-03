
package org.drip.function.gammaincomplete;

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
 * <i>UpperSFixed</i> implements the Upper Incomplete Gamma Function using the Power Expansion Series,
 * starting with s = 0 if Recurrence is employed. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Geddes, K. O., M. L. Glasser, R. A. Moore, and T. C. Scott (1990): Evaluation of Classes of
 * 				Definite Integrals involving Elementary Functions via Differentiation of Special Functions
 * 				<i>Applicable Algebra in Engineering, Communications, and </i> <b>1 (2)</b> 149-165
 * 		</li>
 * 		<li>
 * 			Gradshteyn, I. S., I. M. Ryzhik, Y. V. Geronimus, M. Y. Tseytlin, and A. Jeffrey (2015):
 * 				<i>Tables of Integrals, Series, and Products</i> <b>Academic Press</b>
 * 		</li>
 * 		<li>
 * 			Mathar, R. J. (2010): Numerical Evaluation of the Oscillatory Integral over
 *				e<sup>iπx</sup> x<sup>(1/x)</sup> between 1 and ∞
 *				https://arxiv.org/pdf/0912.3844.pdf <b>arXiV</b>
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2019): Incomplete Gamma and Related Functions
 * 				https://dlmf.nist.gov/8
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Incomplete Gamma Function
 * 				https://en.wikipedia.org/wiki/Incomplete_gamma_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/gammaincomplete/README.md">Upper/Lower Incomplete Gamma Functions</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class UpperSFixed extends org.drip.numerical.estimation.R1ToR1Estimator
{

	/**
	 * The Euler-Mascheroni Constant
	 */

	public static final double EULER_MASCHERONI = 0.57721566490153286060;

	private org.drip.numerical.estimation.R1ToR1Series _upperSFixedSeries = null;

	/**
	 * Retrieve the NIST (2019) Version of Upper Incomplete Gamma s = 0 Estimator
	 * 
	 * @param termCount Number of Terms in the Estimation
	 * 
	 * @return NIST (2019) Version of Upper Incomplete Gamma s = 0 Estimator
	 */

	public static final UpperSFixed NIST2019 (
		final int termCount)
	{
		try
		{
			return new UpperSFixed (
				org.drip.function.gammaincomplete.UpperSFixedSeries.NIST2019 (termCount),
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z) || z <= 0.)
					{
						throw new java.lang.Exception ("UpperSFixed::evaluate => Invalid Inputs");
					}

					return -1. * (EULER_MASCHERONI + java.lang.Math.log (z) + upperSFixedSeries().evaluate
						(z));
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Weisstein Version of Upper Incomplete Gamma Estimator
	 * 
	 * @param s s
	 * 
	 * @return Weisstein Version of Upper Incomplete Gamma Estimator
	 */

	public static final UpperSFixed Weisstein (
		final int s)
	{
		try
		{
			return new UpperSFixed (
				org.drip.function.gammaincomplete.UpperSFixedSeries.Weisstein (s),
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z) || z < 0.)
					{
						throw new java.lang.Exception ("UpperSFixed::Weisstein::evaluate => Invalid Inputs");
					}

					return upperSFixedSeries().evaluate (z) * java.lang.Math.exp (
						(0 == s ? 1. : new org.drip.function.stirling.NemesLogGamma (null).evaluate (s))
					);
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * UpperSFixed Constructor
	 * 
	 * @param upperSFixedSeries R<sup>1</sup> To R<sup>1</sup> Upper S Fixed Limit Series
	 * @param dc Differential Control
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	protected UpperSFixed (
		final org.drip.numerical.estimation.R1ToR1Series upperSFixedSeries,
		final org.drip.numerical.differentiation.DerivativeControl dc)
		throws java.lang.Exception
	{
		super (dc);

		_upperSFixedSeries = upperSFixedSeries;
	}

	/**
	 * Retrieve the Underlying Upper S Fixed Series
	 * 
	 * @return The Underlying Upper S Fixed Series
	 */

	public org.drip.numerical.estimation.R1ToR1Series upperSFixedSeries()
	{
		return _upperSFixedSeries;
	}

	@Override public org.drip.numerical.estimation.R1Estimate seriesEstimateNative (
		final double x)
	{
		return null == _upperSFixedSeries ? seriesEstimate (
			x,
			null,
			null
		) : seriesEstimate (
			x,
			_upperSFixedSeries.termWeightMap(),
			_upperSFixedSeries
		);
	}

	/**
	 * Evaluate the Upper Gamma (-n, z) recursively from n = 0
	 * 
	 * @param n n
	 * @param z z
	 * 
	 * @return Upper Gamma (-n, z)
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double evaluateRecursive (
		final int n,
		final double z)
		throws java.lang.Exception
	{
		org.drip.numerical.estimation.R1ToR1Series upperSRecursiveSeries =
			org.drip.function.gammaincomplete.UpperSFixedSeries.NIST2019Recursive (n);

		if (null == upperSRecursiveSeries)
		{
			throw new java.lang.Exception ("UpperSFixed::evaluateRecursive => Invalid Inputs");
		}

		return (
			upperSRecursiveSeries.evaluate (z) * java.lang.Math.exp (-n * java.lang.Math.log (z) - z) +
			(n % 2 == 0 ? 1. : -1.) * evaluate (z)
		) / java.lang.Math.exp (new org.drip.function.stirling.NemesLogGamma (null).evaluate (n));
	}
}
