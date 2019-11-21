
package org.drip.specialfunction.generator;

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
 * <i>BesselFirstKindLaurentExpansion</i> implements the Laurent-Series Generating Function and the Expansion
 * Terms for the Cylindrical Bessel Function of the First Kind. The References are:
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/generator/README.md">Special Function Series Term Generators</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BesselFirstKindLaurentExpansion extends org.drip.specialfunction.generator.SeriesExpansion
{
	private org.drip.specialfunction.definition.BesselFirstKindEstimator _besselFirstKindEstimator = null;

	/**
	 * BesselFirstKindLaurentExpansion Constructor
	 * 
	 * @param besselFirstKindEstimator Bessel Function First Kind Estimator
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BesselFirstKindLaurentExpansion (
		final org.drip.specialfunction.definition.BesselFirstKindEstimator besselFirstKindEstimator)
		throws java.lang.Exception
	{
		if (null == (_besselFirstKindEstimator = besselFirstKindEstimator))
		{
			throw new java.lang.Exception ("BesselFirstKindLaurentExpansion Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Bessel First Kind Function Estimator
	 * 
	 * @return The Bessel First Kind Function Estimator
	 */

	public org.drip.specialfunction.definition.BesselFirstKindEstimator besselFirstKindEstimator()
	{
		return _besselFirstKindEstimator;
	}

	@Override public double evaluate (
		final double z,
		final double t)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (z) ||
			!org.drip.numerical.common.NumberUtil.IsValid (t))
		{
			throw new java.lang.Exception
				("BesselFirstKindLaurentExpansion::evaluate => Invalid Inputs");
		}

		return java.lang.Math.exp (0.5 * z * (t - (1. / t)));
	}

	@Override public org.drip.numerical.estimation.R2ToR1SeriesTerm seriesTerm()
	{
		return new org.drip.numerical.estimation.R2ToR1SeriesTerm()
		{
			@Override public double value (
				final int order,
				final double z,
				final double t)
				throws java.lang.Exception
			{
				if (0 > order ||
					!org.drip.numerical.common.NumberUtil.IsValid (z) ||
					!org.drip.numerical.common.NumberUtil.IsValid (t))
				{
					throw new java.lang.Exception
						("BesselFirstKindLaurentExpansion::seriesTerm::value => Invalid Inputs");
				}

				return java.lang.Math.pow (
					t,
					order
				) * _besselFirstKindEstimator.evaluate (
					order,
					z
				);
			}
		};
	}

	/**
	 * Generate the Default Series
	 * 
	 * @return The Default Series
	 */

	public org.drip.numerical.estimation.R2ToR1Series series()
	{
		return series (
			java.lang.Integer.MIN_VALUE,
			java.lang.Integer.MAX_VALUE
		);
	}
}
