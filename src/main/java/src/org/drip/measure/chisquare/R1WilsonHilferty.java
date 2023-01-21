
package org.drip.measure.chisquare;

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
 * <i>R1CentralWilsonHilferty</i> implements the Normal Proxy Version for the R<sup>1</sup> Chi-Square
 * 	Distribution using the Wilson-Hilferty Transformation. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Backstrom, T., and J. Fischer (2018): Fast Randomization for Distributed Low Bit-rate Coding of
 * 				Speech and Audio <i>IEEE/ACM Transactions on Audio, Speech, and Language Processing</i> <b>26
 * 				(1)</b> 19-30
 * 		</li>
 * 		<li>
 * 			Chi-Squared Distribution (2019): Chi-Squared Function
 * 				https://en.wikipedia.org/wiki/Chi-squared_distribution
 * 		</li>
 * 		<li>
 * 			Johnson, N. L., S. Kotz, and N. Balakrishnan (1994): <i>Continuous Univariate Distributions
 * 				2<sup>nd</sup> Edition</i> <b>John Wiley and Sons</b>
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2019): Chi-Squared Distribution
 * 				https://www.itl.nist.gov/div898/handbook/eda/section3/eda3666.htm
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/chisquare/README.md">Chi-Square Distribution Implementation/Properties</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class R1WilsonHilferty
	extends org.drip.measure.continuous.R1Univariate
{
	private double _degreesOfFreedom = -1;
	private org.drip.measure.gaussian.R1UnivariateNormal _r1UnivariateNormal = null;

	protected R1WilsonHilferty (
		final double degreesOfFreedom,
		final org.drip.measure.gaussian.R1UnivariateNormal r1UnivariateNormal)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				_degreesOfFreedom = degreesOfFreedom
			) || 0. >= _degreesOfFreedom ||
			null == (_r1UnivariateNormal = r1UnivariateNormal)
		)
		{
			throw new java.lang.Exception (
				"R1WilsonHilferty Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Degrees of Freedom
	 * 
	 * @return The Degrees of Freedom
	 */

	public double degreesOfFreedom()
	{
		return _degreesOfFreedom;
	}

	/**
	 * Retrieve the R<sup>1</sup> Univariate Normal
	 * 
	 * @return The R<sup>1</sup> Univariate Normal
	 */

	public org.drip.measure.gaussian.R1UnivariateNormal r1UnivariateNormal()
	{
		return _r1UnivariateNormal;
	}

	/**
	 * Transform x into the Wilson-Hilferty Variate
	 * 
	 * @param x X
	 * 
	 * @return The Wilson-Hilferty Variate
	 */

	public abstract double transform (
		final double x);

	/**
	 * Transform the Wilson-Hilferty Variate into x
	 * 
	 * @param wilsonHilferty The Wilson-Hilferty Variate
	 * 
	 * @return The Wilson-Hilferty Variate transformed back to x
	 */

	public abstract double inverseTransform (
		final double wilsonHilferty);

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
		return _r1UnivariateNormal.density (
			transform (
				t
			)
		);
	}

	@Override public double cumulative (
		final double t)
		throws java.lang.Exception
	{
		return _r1UnivariateNormal.cumulative (
			transform (
				t
			)
		);
	}

	@Override public double invCumulative (
		final double y)
		throws java.lang.Exception
	{
		return inverseTransform (
			_r1UnivariateNormal.invCumulative (
				y
			)
		);
	}

	@Override public double mean()
		throws java.lang.Exception
	{
		return _r1UnivariateNormal.mean();
	}

	@Override public double median()
		throws java.lang.Exception
	{
		return _r1UnivariateNormal.median();
	}

	@Override public double mode()
		throws java.lang.Exception
	{
		return _r1UnivariateNormal.mode();
	}

	@Override public double variance()
		throws java.lang.Exception
	{
		return _r1UnivariateNormal.variance();
	}

	@Override public double skewness()
		throws java.lang.Exception
	{
		return _r1UnivariateNormal.skewness();
	}

	@Override public double excessKurtosis()
		throws java.lang.Exception
	{
		return _r1UnivariateNormal.excessKurtosis();
	}

	@Override public org.drip.function.definition.R1ToR1 momentGeneratingFunction()
	{
		return _r1UnivariateNormal.momentGeneratingFunction();
	}

	@Override public org.drip.function.definition.R1ToR1 probabilityGeneratingFunction()
	{
		return _r1UnivariateNormal.probabilityGeneratingFunction();
	}
}
