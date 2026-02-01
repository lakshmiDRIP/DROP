
package org.drip.numerical.r1integration;

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
 * <i>AbscissaTransform</i> transforms the Abscissa over into Corresponding Integrand Variable. The
 * References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Briol, F. X., C. J. Oates, M. Girolami, and M. A. Osborne (2015): <i>Frank-Wolfe Bayesian
 * 				Quadrature: Probabilistic Integration with Theoretical Guarantees</i> <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Forsythe, G. E., M. A. Malcolm, and C. B. Moler (1977): <i>Computer Methods for Mathematical
 * 				Computation</i> <b>Prentice Hall</b> Englewood Cliffs NJ
 * 		</li>
 * 		<li>
 * 			Leader, J. J. (2004): <i>Numerical Analysis and Scientific Computation</i> <b>Addison Wesley</b>
 * 		</li>
 * 		<li>
 * 			Stoer, J., and R. Bulirsch (1980): <i>Introduction to Numerical Analysis</i>
 * 				<b>Springer-Verlag</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Numerical Integration https://en.wikipedia.org/wiki/Numerical_integration
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/r1integration/README.md">R<sup>1</sup> to R<sup>1</sup> Numerical Integration Schemes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class AbscissaTransform
{
	private double _quadratureScale = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _r1PointValueScale = null;
	private org.drip.function.definition.R1ToR1 _r1ToR1VariateChange = null;

	/**
	 * Generate the Scaled and Displaced Abscissa Transform from (left, right) To (0, +1)
	 * 
	 * @param left Span Left
	 * @param right Span Right
	 * 
	 * @return The Scaled and Displaced Abscissa Transform from (left, right) To (0, +1)
	 */

	public static final AbscissaTransform DisplaceAndScaleZero_PlusOne (
		final double left,
		final double right)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (left) ||
			!org.drip.numerical.common.NumberUtil.IsValid (right))
		{
			return null;
		}

		try
		{
			return new AbscissaTransform (
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
					{
						return (right - left) * x + left;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
					{
						return 1.;
					}
				},
				right - left
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Scaled and Displaced Abscissa Transform from (left, right) To (-1, +1)
	 * 
	 * @param left Span Left
	 * @param right Span Right
	 * 
	 * @return The Scaled and Displaced Abscissa Transform from (left, right) To (-1, +1)
	 */

	public static final AbscissaTransform DisplaceAndScaleMinusOne_PlusOne (
		final double left,
		final double right)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (left) ||
			!org.drip.numerical.common.NumberUtil.IsValid (right))
		{
			return null;
		}

		final double scale = 0.5 * (right - left);
		final double offset = 0.5 * (right + left);

		try
		{
			return new AbscissaTransform (
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
					{
						return scale * x + offset;
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
					{
						return 1.;
					}
				},
				scale
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Gauss-Hermite Abscissa Transform
	 * 
	 * @return The Gauss-Hermite Abscissa Transform
	 */

	public static final AbscissaTransform GaussHermite()
	{
		try
		{
			return new AbscissaTransform (
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
					{
						return x / (1. - x * x);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
					{
						double xSquared = x * x;

						return -1. == x || 1. == x ? 0. : (1. + xSquared) / (1. - xSquared) / (1. - xSquared);
					}
				},
				1.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Gauss-Laguerre Abscissa Transform for Integrals in [a, +Infinity]
	 * 
	 * @param left Span Left
	 * 
	 * @return The Gauss-Laguerre Abscissa Transform for Integrals in [a, +Infinity]
	 */

	public static final AbscissaTransform GaussLaguerreLeftDefinite (
		final double left)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (left))
		{
			return null;
		}

		try
		{
			return new AbscissaTransform (
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
					{
						return 1. == x ? left : left + (x / (1. - x));
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
					{
						return -1. == x || 1. == x ? 0. : 1. / (1. - x) / (1. - x);
					}
				},
				1.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Gauss-Laguerre Abscissa Transform for Integrals in [-Infinity, a]
	 * 
	 * @param right Span Right
	 * 
	 * @return The Gauss-Laguerre Abscissa Transform for Integrals in [-Infinity, a]
	 */

	public static final AbscissaTransform GaussLaguerreRightDefinite (
		final double right)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (right))
		{
			return null;
		}

		try
		{
			return new AbscissaTransform (
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
					{
						return right - ((1. - x) / x);
					}
				},
				new org.drip.function.definition.R1ToR1 (null)
				{
					@Override public double evaluate (
						final double x)
					{
						return 0. == x ? 0. : 1. / (x * x);
					}
				},
				1.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * AbscissaTransform Constructor
	 * 
	 * @param r1ToR1VariateChange R<sup>1</sup> to R<sup>1</sup> Variate Change Function
	 * @param r1PointValueScale R<sup>1</sup> Point Value Scale Function
	 * @param quadratureScale Quadrature Scale
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AbscissaTransform (
		final org.drip.function.definition.R1ToR1 r1ToR1VariateChange,
		final org.drip.function.definition.R1ToR1 r1PointValueScale,
		final double quadratureScale)
		throws java.lang.Exception
	{
		if (null == (_r1ToR1VariateChange = r1ToR1VariateChange) ||
			null == (_r1PointValueScale = r1PointValueScale) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_quadratureScale = quadratureScale))
		{
			throw new java.lang.Exception ("AbscissaTransform Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the R<sup>1</sup> to R<sup>1</sup> Variate Change Function
	 * 
	 * @return The R<sup>1</sup> to R<sup>1</sup> Variate Change Function
	 */

	public org.drip.function.definition.R1ToR1 variateChange()
	{
		return _r1ToR1VariateChange;
	}

	/**
	 * Retrieve the R<sup>1</sup> Point Value Scale Function
	 * 
	 * @return The R<sup>1</sup> Point Value Scale Function
	 */

	public org.drip.function.definition.R1ToR1 pointValueScale()
	{
		return _r1PointValueScale;
	}

	/**
	 * Retrieve the Quadrature Scale
	 * 
	 * @return The Quadrature Scale
	 */

	public double quadratureScale()
	{
		return _quadratureScale;
	}
}
