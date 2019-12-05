
package org.drip.validation.distance;

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
 * <i>ImportanceWeight</i> weighs the Importance of each Empirical Hypothesis Outcome.
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		Anfuso, F., D. Karyampas, and A. Nawroth (2017): A Sound Basel III Compliant Framework for
 *  			Back-testing Credit Exposure Models
 *  			https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2264620 <b>eSSRN</b>
 *  	</li>
 *  	<li>
 *  		Diebold, F. X., T. A. Gunther, and A. S. Tay (1998): Evaluating Density Forecasts with
 *  			Applications to Financial Risk Management, International Economic Review 39 (4) 863-883
 *  	</li>
 *  	<li>
 *  		Kenyon, C., and R. Stamm (2012): Discounting, LIBOR, CVA, and Funding: Interest Rate and Credit
 *  			Pricing, Palgrave Macmillan
 *  	</li>
 *  	<li>
 *  		Wikipedia (2018): Probability Integral Transform
 *  			https://en.wikipedia.org/wiki/Probability_integral_transform
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): p-value https://en.wikipedia.org/wiki/P-value
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ModelValidationAnalyticsLibrary.md">Model Validation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/README.md">Risk Factor and Hypothesis Validation, Evidence Processing, and Model Testing</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/validation/distance/README.md">Hypothesis Target Distance Test Builders</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ImportanceWeight
{
	private double _positiveExpectation = java.lang.Double.NaN;
	private org.drip.measure.continuous.R1Univariate _r1Univariate = null;

	/**
	 * Construct the Importance Weight Version based on Normal Distribution
	 * 
	 * @param r1UnivariateNormal R<sup>1</sup> Normal Distribution
	 * 
	 * @return The Importance Weight
	 */

	public static final ImportanceWeight Normal (
		final org.drip.measure.gaussian.R1UnivariateNormal r1UnivariateNormal)
	{
		if (null == r1UnivariateNormal)
		{
			return null;
		}

		final double mean = r1UnivariateNormal.mean();

		final double sigma = r1UnivariateNormal.sigma();

		org.drip.measure.gaussian.R1UnivariateNormal r1UnivariateNormalStandard =
			org.drip.measure.gaussian.R1UnivariateNormal.Standard();

		double meanOverSigma = mean / sigma;

		try
		{
			final double positiveExpectation = java.lang.Math.max (
				mean * r1UnivariateNormalStandard.cumulative (meanOverSigma) +
					sigma * r1UnivariateNormalStandard.density (meanOverSigma),
				0.
			);

			return new ImportanceWeight (
				r1UnivariateNormal,
				positiveExpectation
			)
			{
				@Override public double quantileLoading (
					final double q)
					throws java.lang.Exception
				{
					return 0. == positiveExpectation ? 0. : java.lang.Math.max (
						org.drip.function.e2erf.ErrorFunctionInverse.Winitzki2008b().evaluate
							(2. * q - 1.) * sigma * java.lang.Math.sqrt (2.) + mean,
						0
					) / positiveExpectation;
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
	 * ImportanceWeight Constructor
	 * 
	 * @param r1Univariate The Underlying R<sup>1</sup> Distribution
	 * @param positiveExpectation The Positive Expectation
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ImportanceWeight (
		final org.drip.measure.continuous.R1Univariate r1Univariate,
		final double positiveExpectation)
		throws java.lang.Exception
	{
		if (null == (_r1Univariate = r1Univariate) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_positiveExpectation = positiveExpectation) ||
				0. > _positiveExpectation)
		{
			throw new java.lang.Exception ("ImportanceWeight Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Positive Expectation
	 * 
	 * @return The Positive Expectation
	 */

	public double positiveExpectation()
	{
		return _positiveExpectation;
	}

	/**
	 * Retrieve the Underlying R<sup>1</sup> Distribution
	 * 
	 * @return The Underlying R<sup>1</sup> Distribution
	 */

	public org.drip.measure.continuous.R1Univariate r1Univariate()
	{
		return _r1Univariate;
	}

	/**
	 * Retrieve the Importance Weight Loading given the Quantile
	 * 
	 * @param q The Quantile
	 * 
	 * @return The Importance Weight Loading
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double quantileLoading (
		final double q)
		throws java.lang.Exception
	{
		return 0. == _positiveExpectation ? 0. : java.lang.Math.max (
			_r1Univariate.invCumulative (q),
			0
		) / _positiveExpectation;
	}
}
