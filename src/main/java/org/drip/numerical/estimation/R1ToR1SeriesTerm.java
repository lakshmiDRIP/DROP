
package org.drip.numerical.estimation;

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
 * <i>R1ToR1SeriesTerm</i> exposes the R<sup>1</sup> To R<sup>1</sup> Series Expansion Term in the Ordered
 * Series of the Numerical Estimate for a Function. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Mortici, C. (2011): Improved Asymptotic Formulas for the Gamma Function <i>Computers and
 * 				Mathematics with Applications</i> <b>61 (11)</b> 3364-3369
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2018): NIST Digital Library of Mathematical
 * 				Functions https://dlmf.nist.gov/5.11
 * 		</li>
 * 		<li>
 * 			Nemes, G. (2010): On the Coefficients of the Asymptotic Expansion of n!
 * 				https://arxiv.org/abs/1003.2907 <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Toth V. T. (2016): Programmable Calculators – The Gamma Function
 * 				http://www.rskey.org/CMS/index.php/the-library/11
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Stirling's Approximation
 * 				https://en.wikipedia.org/wiki/Stirling%27s_approximation
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/README.md">Function Numerical Estimates/Corrections/Bounds</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class R1ToR1SeriesTerm
{

	/**
	 * Construct the Asymptotic Series Expansion Term
	 * 
	 * @return The Asymptotic Series Expansion Term
	 */

	public static final R1ToR1SeriesTerm Asymptotic()
	{
		return new R1ToR1SeriesTerm()
		{
			@Override public double value (
				final int order,
				final double x)
				throws java.lang.Exception
			{
				if (0 >= order ||
					!org.drip.numerical.common.NumberUtil.IsValid (x) || 0. == x)
				{
					throw new java.lang.Exception ("Asymptotic::R1ToR1SeriesTerm::value => Invalid Inputs");
				}

				return java.lang.Math.pow (
					x,
					-1. * order
				);
			}
		};
	}

	/**
	 * Construct the Inverted Rising Exponential Series Expansion Term
	 * 
	 * @return The Inverted Rising Exponential Series Expansion Term
	 */

	public static final R1ToR1SeriesTerm InvertedRisingExponential()
	{
		return new R1ToR1SeriesTerm()
		{
			@Override public double value (
				final int order,
				final double x)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (x))
				{
					throw new java.lang.Exception
						("InvertedRisingExponential::R1ToR1SeriesTerm::evaluate => Invalid Inputs");
				}

				double risingExponential = 1.;

				for (int orderIndex = 1; orderIndex <= order; ++orderIndex)
				{
					risingExponential = risingExponential * (x + orderIndex);
				}

				if (0. == risingExponential)
				{
					throw new java.lang.Exception
						("InvertedRisingExponential::R1ToR1SeriesTerm::evaluate => Invalid Inputs");
				}

				return 1. / risingExponential;
			}
		};
	}

	/**
	 * Construct the Taylor Series Expansion Term
	 * 
	 * @return The Taylor Series Expansion Term
	 */

	public static final R1ToR1SeriesTerm Taylor()
	{
		return new R1ToR1SeriesTerm()
		{
			@Override public double value (
				final int order,
				final double x)
				throws java.lang.Exception
			{
				if (0 >= order ||
					!org.drip.numerical.common.NumberUtil.IsValid (x))
				{
					throw new java.lang.Exception ("Taylor::R1ToR1SeriesTerm::value => Invalid Inputs");
				}

				return java.lang.Math.pow (
					x,
					order
				);
			}

			@Override public double derivative (
				final int order,
				final int derivativeOrder,
				final double x)
				throws java.lang.Exception
			{
				if (0 >= order ||
					0 >= derivativeOrder ||
					!org.drip.numerical.common.NumberUtil.IsValid (x))
				{
					throw new java.lang.Exception ("Taylor::R1ToR1SeriesTerm::derivative => Invalid Inputs");
				}

				return derivativeOrder > order ? 0. : org.drip.numerical.common.NumberUtil.NPK (
					order,
					derivativeOrder
				) * java.lang.Math.pow (
					x,
					order - derivativeOrder
				);
			}
		};
	}

	protected R1ToR1SeriesTerm()
	{
	}

	/**
	 * Compute the Value of the R<sup>1</sup> To R<sup>1</sup> Series Expansion Term
	 * 
	 * @param order Order of the R<sup>1</sup> To R<sup>1</sup> Series Expansion Term
	 * @param x X
	 * 
	 * @return The Value of the R<sup>1</sup> To R<sup>1</sup> Series Expansion Term
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public abstract double value (
		final int order,
		final double x)
		throws java.lang.Exception;

	/**
	 * Compute the Derivative of the R<sup>1</sup> To R<sup>1</sup> Series Expansion Term
	 * 
	 * @param order Order of the R<sup>1</sup> To R<sup>1</sup> Series Expansion Term
	 * @param derivativeOrder Order of the R<sup>1</sup> To R<sup>1</sup> Series Derivative
	 * @param x X
	 * 
	 * @return The Derivative of the R<sup>1</sup> To R<sup>1</sup> Series Expansion Term
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double derivative (
		final int order,
		final int derivativeOrder,
		final double x)
		throws java.lang.Exception
	{
		throw new java.lang.Exception ("R1ToR1SeriesTerm::value => Generic Derivative Not Implemented");
	}
}
