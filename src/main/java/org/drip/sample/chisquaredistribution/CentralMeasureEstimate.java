
package org.drip.sample.chisquaredistribution;

import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.R2ToR1;
import org.drip.measure.chisquare.R1Central;
import org.drip.numerical.common.FormatUtil;
import org.drip.service.env.EnvManager;
import org.drip.specialfunction.digamma.CumulativeSeriesEstimator;
import org.drip.specialfunction.gamma.EulerIntegralSecondKind;
import org.drip.specialfunction.incompletegamma.LowerEulerIntegral;

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
 * <i>CentralMeasureEstimate</i> illustrates the Estimation of the Central Chi-squared Distribution Measures.
 * The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Chi-Squared Distribution (2019): Chi-Squared Function
 * 				https://en.wikipedia.org/wiki/Chi-squared_distribution
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Project</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/ode/README.md">Special Function Ordinary Differential Equations</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class CentralMeasureEstimate
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
				).evaluate (s);
			}
		};
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int digammaTermCount = 1000;
		int[] dofArray =
		{
			 // 1,
			 2,
			 3,
			 4,
			 5,
			 6,
			 7,
			 8,
			 9,
			10,
			11,
			12,
			13,
			14,
			15,
			16,
			17,
			18,
			19,
			20,
			21,
			22,
			23,
			24,
			25,
		};

		R1ToR1 gammaEstimator = new EulerIntegralSecondKind (null);

		R2ToR1 lowerIncompleteGammaEstimator = LowerIncompleteGamma();

		R1ToR1 digammaEstimator = CumulativeSeriesEstimator.AbramowitzStegun2007 (digammaTermCount);

		System.out.println ("\t|----------------------------------------------------------------------------||");

		System.out.println ("\t|             CENTRAL CHI-SQUARED DISTRIBUTION MEASURES ESTIMATE             ||");

		System.out.println ("\t|----------------------------------------------------------------------------||");

		System.out.println ("\t|        L -> R:                                                             ||");

		System.out.println ("\t|                - Degrees of Freedom                                        ||");

		System.out.println ("\t|                - Mean                                                      ||");

		System.out.println ("\t|                - Median                                                    ||");

		System.out.println ("\t|                - Mode                                                      ||");

		System.out.println ("\t|                - Variance                                                  ||");

		System.out.println ("\t|                - Skewness                                                  ||");

		System.out.println ("\t|                - Excess Kurtosis                                           ||");

		System.out.println ("\t|                - Differential Entropy                                      ||");

		System.out.println ("\t|----------------------------------------------------------------------------||");

		for (int dof : dofArray)
		{
			R1Central r1UnivariateCentral = new R1Central (
				dof,
				gammaEstimator,
				digammaEstimator,
				lowerIncompleteGammaEstimator
			);

			String display = "\t| [" + FormatUtil.FormatDouble (dof, 2, 0, 1., false) + "] =>";

			display = display + " " + FormatUtil.FormatDouble (
				r1UnivariateCentral.mean(), 2, 2, 1., false
			) + " | " + FormatUtil.FormatDouble (
				r1UnivariateCentral.median(), 2, 6, 1., false
			) + " | " + FormatUtil.FormatDouble (
				r1UnivariateCentral.mode(), 2, 2, 1., false
			) + " | " + FormatUtil.FormatDouble (
				r1UnivariateCentral.variance(), 2, 2, 1., false
			) + " | " + FormatUtil.FormatDouble (
				r1UnivariateCentral.skewness(), 1, 6, 1., false
			) + " | " + FormatUtil.FormatDouble (
				r1UnivariateCentral.excessKurtosis(), 1, 6, 1., false
			) + " | " + FormatUtil.FormatDouble (
				r1UnivariateCentral.differentialEntropy(), 1, 6, 1., false
			) + " |";

			System.out.println (display + "|");
		}

		System.out.println ("\t|----------------------------------------------------------------------------||");
	}
}
