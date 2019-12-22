
package org.drip.measure.gamma;

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
 * <i>R1ScaleInvariantScaleParameterEstimator</i> implements the Scale Parameter Estimator using
 * 	Scale-Invariant Prior for the Scale Parameter under a Sequence of Observations. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Devroye, L. (1986): <i>Non-Uniform Random Variate Generation</i> <b>Springer-Verlag</b> New York
 * 		</li>
 * 		<li>
 * 			Gamma Distribution (2019): Gamma Distribution
 * 				https://en.wikipedia.org/wiki/Chi-squared_distribution
 * 		</li>
 * 		<li>
 * 			Louzada, F., P. L. Ramos, and E. Ramos (2019): A Note on Bias of Closed-Form Estimators for the
 * 				Gamma Distribution Derived From Likelihood Equations <i>The American Statistician</i> <b>73
 * 				(2)</b> 195-199
 * 		</li>
 * 		<li>
 * 			Minka, T. (2002): Estimating a Gamma distribution https://tminka.github.io/papers/minka-gamma.pdf
 * 		</li>
 * 		<li>
 * 			Ye, Z. S., and N. Chen (2017): Closed-Form Estimators for the Gamma Distribution Derived from
 * 				Likelihood Equations <i>The American Statistician</i> <b>71 (2)</b> 177-181
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/gamma/README.md">R<sup>1</sup> Gamma Distribution Implementation/Properties</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1ScaleInvariantScaleParameterEstimator
{
	private org.drip.validation.evidence.Sample _sample = null;

	/**
	 * Construct and Instance of R1ScaleInvariantScaleParameterEstimator from the Array of Realizations
	 * 
	 * @param realizationArray The Realization Array
	 * 
	 * @return Instance of R1ScaleInvariantScaleParameterEstimator
	 */

	public static final R1ScaleInvariantScaleParameterEstimator FromRealizationArray (
		final double[] realizationArray)
	{
		try
		{
			return new R1ScaleInvariantScaleParameterEstimator (
				new org.drip.validation.evidence.Sample (
					realizationArray
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * R1ScaleInvariantScaleParameterEstimator Constructor
	 * 
	 * @param sample The Sample
	 * 
	 * @throws java.lang.Exception Thrown of the Inputs are Invalid
	 */

	public R1ScaleInvariantScaleParameterEstimator (
		final org.drip.validation.evidence.Sample sample)
		throws java.lang.Exception
	{
		if (null == (_sample = sample))
		{
			throw new java.lang.Exception (
				"R1ScaleInvariantScaleParameterEstimator Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Samples used for the ML Estimate
	 * 
	 * @return Samples used for the ML Estimate
	 */

	public org.drip.validation.evidence.Sample sample()
	{
		return _sample;
	}

	/**
	 * Infer the Scale Parameter Distribution
	 * 
	 * @param shape Shape Parameter
	 * 
	 * @return The Scale Parameter Distribution
	 */

	public org.drip.measure.gaussian.R1UnivariateNormal inferScaleParameterDistribution (
		final double shape)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
			shape
		))
		{
			return null;
		}

		double[] realizationArray = sample().realizationArray();

		double realizationCount = realizationArray.length;
		double nk = realizationCount * shape;
		double oneOver_nkMinusOne_ = 1. / (nk - 1.);
		double realizationSum = 0.;

		for (int realizationIndex = 0;
			realizationIndex < realizationCount;
			++realizationIndex)
		{
			realizationSum = realizationSum + realizationArray[realizationIndex];
		}

		try
		{
			return new org.drip.measure.gaussian.R1UnivariateNormal (
				realizationSum * oneOver_nkMinusOne_,
				realizationSum * oneOver_nkMinusOne_ * java.lang.Math.sqrt (
					1. / (nk - 2.)
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public double inferScaleParameterDistributionMoment (
		final double shape,
		final int moment,
		final org.drip.function.definition.R1ToR1 gammaEstimator)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				shape
			) || 1 > moment ||
			null == gammaEstimator
		)
		{
			throw new java.lang.Exception (
				"R1ScaleInvariantScaleParameterEstimator::inferScaleParameterDistributionMoment => Invalid Inputs"
			);
		}

		double[] realizationArray = sample().realizationArray();

		double realizationCount = realizationArray.length;
		double nk = realizationCount * shape;
		double realizationSum = 0.;

		for (int realizationIndex = 0;
			realizationIndex < realizationCount;
			++realizationIndex)
		{
			realizationSum = realizationSum + realizationArray[realizationIndex];
		}

		return gammaEstimator.evaluate (
			nk - moment
		) * java.lang.Math.pow (
			realizationSum,
			moment
		) / gammaEstimator.evaluate (
			nk
		);
	}
}
