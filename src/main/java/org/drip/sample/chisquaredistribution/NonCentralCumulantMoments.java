
package org.drip.sample.chisquaredistribution;

import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.R2ToR1;
import org.drip.measure.chisquare.R1NonCentral;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.specialfunction.bessel.ModifiedFirstFrobeniusSeriesEstimator;
import org.drip.specialfunction.definition.ModifiedBesselFirstKindEstimator;
import org.drip.specialfunction.digamma.CumulativeSeriesEstimator;
import org.drip.specialfunction.gamma.EulerIntegralSecondKind;
import org.drip.specialfunction.incompletegamma.LowerEulerIntegral;

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
 * <i>NonCentralCumulantMoments</i> illustrates the Computation of the Leading Cumulants and the Non-Central
 * 	Moments. The References are:
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

public class NonCentralCumulantMoments
{

	private static final R2ToR1 LowerIncompleteGamma()
		throws Exception
	{
		return new R2ToR1()
		{
			@Override public double evaluate (
				final double s,
				final double t)
				throws Exception
			{
				return new LowerEulerIntegral (
					null,
					t
				).evaluate (
					s
				);
			}
		};
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		int digammaTermCount = 1000;
		int besselFirstTermCount = 20;
		double[] nonCentralityParameterArray =
		{
			0.5,
			1.0,
			1.5,
			2.0,
			2.5,
			3.0,
			3.5,
			4.0,
		};
		int[] dofArray =
		{
			 2,
			 3,
			 4,
			 5,
			 6,
			 7,
			 8,
			 9,
		};
		int[] nArray =
		{
			1,
			2,
			3,
			4,
			5,
			6,
		};

		R1ToR1 gammaEstimator = new EulerIntegralSecondKind (
			null
		);

		R2ToR1 lowerIncompleteGammaEstimator = LowerIncompleteGamma();

		R1ToR1 digammaEstimator = CumulativeSeriesEstimator.AbramowitzStegun2007 (
			digammaTermCount
		);

		ModifiedBesselFirstKindEstimator modifiedBesselFirstKindEstimator =
			ModifiedFirstFrobeniusSeriesEstimator.Standard (
				gammaEstimator,
				besselFirstTermCount
			);

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                   NON CENTRAL CHI-SQUARE CUMULANTS AND NON-CENTRAL MOMENTS                                   ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                                                                               ||");

		System.out.println ("\t|                - Degrees of Freedom; Non Centrality Parameter                                                                ||");

		System.out.println ("\t|                - Cumulant                                                                                                    ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------||");

		for (double nonCentralityParameter : nonCentralityParameterArray)
		{
			for (int dof : dofArray)
			{
				R1NonCentral r1UnivariateNonCentral = new R1NonCentral (
					dof,
					nonCentralityParameter,
					gammaEstimator,
					digammaEstimator,
					lowerIncompleteGammaEstimator,
					modifiedBesselFirstKindEstimator
				);

				String display = "\t| [" +
					FormatUtil.FormatDouble (dof, 2, 0, 1., false) + " | " +
					FormatUtil.FormatDouble (nonCentralityParameter, 1, 1, 1., false) +
				"] =>";

				for (int n : nArray)
				{
					display = display + " " + FormatUtil.FormatDouble (
						r1UnivariateNonCentral.cumulant (
							n
						), 6, 0, 1., false
					) + " |";
				}

				System.out.println (display + "|");
			}

			System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|                                   NON CENTRAL CHI-SQUARE CUMULANTS AND NON-CENTRAL MOMENTS                                   ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                                                                               ||");

		System.out.println ("\t|                - Degrees of Freedom; Non Centrality Parameter                                                                ||");

		System.out.println ("\t|                - Non-central Moment                                                                                          ||");

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------||");

		for (double nonCentralityParameter : nonCentralityParameterArray)
		{
			for (int dof : dofArray)
			{
				R1NonCentral r1UnivariateNonCentral = new R1NonCentral (
					dof,
					nonCentralityParameter,
					gammaEstimator,
					digammaEstimator,
					lowerIncompleteGammaEstimator,
					modifiedBesselFirstKindEstimator
				);

				String display = "\t| [" +
					FormatUtil.FormatDouble (dof, 2, 0, 1., false) + " | " +
					FormatUtil.FormatDouble (nonCentralityParameter, 1, 1, 1., false) +
				"] =>";

				for (int n : nArray)
				{
					display = display + " " + FormatUtil.FormatDouble (
						r1UnivariateNonCentral.nonCentralMoment (
							n
						), 8, 0, 1., false
					) + " |";
				}

				System.out.println (display + "|");
			}

			System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------||");
		}

		System.out.println ("\t|------------------------------------------------------------------------------------------------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
