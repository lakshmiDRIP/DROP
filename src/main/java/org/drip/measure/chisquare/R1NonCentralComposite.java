
package org.drip.measure.chisquare;

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
 * <i>R1NonCentralComposite</i> implements Composite R<sup>1</sup> Non-central Chi-Square Distributions. The
 * 	References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Johnson, N. L., S. Kotz, and N. Balakrishnan (1995): <i>Continuous Univariate Distributions
 * 				2<sup>nd</sup> Edition</i> <b>John Wiley and Sons</b>
 * 		</li>
 * 		<li>
 * 			Muirhead, R. (2005): <i>Aspects of Multivariate Statistical Theory 2<sup>nd</sup> Edition</i>
 * 				<b>Wiley</b>
 * 		</li>
 * 		<li>
 * 			Non-central Chi-Squared Distribution (2019): Chi-Squared Function
 * 				https://en.wikipedia.org/wiki/Noncentral_chi-squared_distribution
 * 		</li>
 * 		<li>
 * 			Sankaran, M. (1963): Approximations to the Non-Central Chi-Square Distribution <i>Biometrika</i>
 * 				<b>50 (1-2)</b> 199-204
 * 		</li>
 * 		<li>
 * 			Young, D. S. (2010): tolerance: An R Package for Estimating Tolerance Intervals <i>Journal of
 * 				Statistical Software</i> <b>36 (5)</b> 1-39
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

public class R1NonCentralComposite
{

	/**
	 * Generate a Random Variable following the Rice Distribution
	 * 
	 * @param lambda Lambda of the Rice Distribution
	 * 
	 * @return Random Variable following the Rice Distribution
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double RandomRice (
		final double lambda)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				lambda
			) || 0. >= lambda
		)
		{
			throw new java.lang.Exception (
				"R1NonCentralComposite::RandomRice => Invalid Inputs"
			);
		}

		double random1 = new org.drip.measure.gaussian.R1UnivariateNormal (
			0.,
			1.
		).random();

		double random2 = new org.drip.measure.gaussian.R1UnivariateNormal (
			java.lang.Math.sqrt (
				lambda
			),
			1.
		).random();

		return random1 * random1 + random2 * random2;
	}

	/**
	 * Generate a Non-Central F Distribution Based off of R<sup>1</sup> Non-central Chi-Square Distribution
	 * 	Pair
	 * 
	 * @param r1NonCentral1 R<sup>1</sup> Non-central Chi-Square Distribution #1
	 * @param r1NonCentral2 R<sup>1</sup> Non-central Chi-Square Distribution #2
	 * 
	 * @return Non-Central F Distribution Random Variable
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double RandomNonCentralF (
		final org.drip.measure.chisquare.R1NonCentral r1NonCentral1,
		final org.drip.measure.chisquare.R1NonCentral r1NonCentral2)
		throws java.lang.Exception
	{
		if (null == r1NonCentral1 ||
			null == r1NonCentral2)
		{
			throw new java.lang.Exception (
				"R1NonCentralComposite::RandomNonCentralF => Invalid Inputs"
			);
		}

		return r1NonCentral1.random() * r1NonCentral2.parameters().degreesOfFreedom() /
			r1NonCentral1.parameters().degreesOfFreedom() / r1NonCentral2.random();
	}

	/**
	 * Generate the R<sup>1</sup> Non-central Distribution corresponding to the Sum of Independent
	 * 		R<sup>1</sup> Non-central Distributions
	 * 
	 * @param r1NonCentralArray Array of Independent R<sup>1</sup> Non-central Distributions
	 * 
	 * @return R<sup>1</sup> Non-central Distribution corresponding to the Sum of Independent R<sup>1</sup>
	 * 		Non-central Distributions
	 */

	public static final org.drip.measure.chisquare.R1NonCentral IndependentSum (
		final org.drip.measure.chisquare.R1NonCentral[] r1NonCentralArray)
	{
		if (null == r1NonCentralArray)
		{
			return null;
		}

		double compositeDegreesOfFreedom = 0.;
		double compositeNonCentralityParameter = 0.;
		int nonCentralDistributionCount = r1NonCentralArray.length;

		for (int nonCentralDistributionIndex = 0;
			nonCentralDistributionIndex < nonCentralDistributionCount;
			++nonCentralDistributionIndex
			)
		{
			if (null == r1NonCentralArray[nonCentralDistributionIndex])
			{
				return null;
			}

			org.drip.measure.chisquare.R1NonCentralParameters r1NonCentralParameters =
				r1NonCentralArray[nonCentralDistributionIndex].parameters();

			compositeDegreesOfFreedom = r1NonCentralParameters.degreesOfFreedom();

			compositeNonCentralityParameter = r1NonCentralParameters.nonCentralityParameter();
		}

		return org.drip.measure.chisquare.R1NonCentral.Standard (
			compositeDegreesOfFreedom,
			compositeNonCentralityParameter,
			r1NonCentralArray[0].gammaEstimator(),
			r1NonCentralArray[0].digammaEstimator(),
			r1NonCentralArray[0].lowerIncompleteGammaEstimator(),
			r1NonCentralArray[0].modifiedBesselFirstKindEstimator()
		);
	}
}
