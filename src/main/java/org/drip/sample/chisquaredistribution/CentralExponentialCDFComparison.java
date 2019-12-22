
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
 * <i>CentralExponentialCDFComparison</i> illustrates the Comparison of the CDF between the Exponential
 * Distribution and the Central Chi-squared Distribution with 2 Degrees of Freedom. The References are:
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

public class CentralExponentialCDFComparison
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
		double[] tArray =
		{
			 0.1,
			 0.5,
			 1.0,
			 1.5,
			 2.0,
			 2.5,
			 3.0,
			 3.5,
			 4.0,
			 4.5,
			 5.0,
			 5.5,
			 6.0,
			 6.5,
			 7.0,
			 7.5,
			 8.0,
			 8.5,
			 9.0,
			 9.5,
			10.0,
			10.5,
			11.0,
			11.5,
			12.0,
		};

		R1ToR1 gammaEstimator = new EulerIntegralSecondKind (null);

		R2ToR1 lowerIncompleteGammaEstimator = LowerIncompleteGamma();

		R1ToR1 digammaEstimator = CumulativeSeriesEstimator.AbramowitzStegun2007 (digammaTermCount);

		R1Central r1UnivariateScaledExponential = new R1Central (
			2,
			gammaEstimator,
			digammaEstimator,
			lowerIncompleteGammaEstimator
		);

		System.out.println ("\t|-----------------------------------||");

		System.out.println ("\t|  EXPONENTIAL vs CHI-SQUARED k=2   ||");

		System.out.println ("\t|-----------------------------------||");

		System.out.println ("\t|      L -> R:                      ||");

		System.out.println ("\t|            - t                    ||");

		System.out.println ("\t|            - Chi-squared CDF      ||");

		System.out.println ("\t|            - Exponential CDF      ||");

		System.out.println ("\t|-----------------------------------||");

		for (double t : tArray)
		{
			System.out.println (
				"\t| [" + FormatUtil.FormatDouble (t, 2, 1, 1., false) + "] => " +
				FormatUtil.FormatDouble (
					r1UnivariateScaledExponential.cumulative (t), 1, 8, 1., false
				) + " | " + FormatUtil.FormatDouble (
					1. - Math.exp (-0.5 * t), 1, 8, 1., false
				) + " ||"
			);
		}

		System.out.println ("\t|-----------------------------------||");
	}
}
