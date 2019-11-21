
package org.drip.specialfunction.beta;

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
 * <i>AsymptoticLogEstimator</i> implements the various Asymptotic Estimators for the Log Beta Function. The
 * References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Davis, P. J. (1959): Leonhard Euler's Integral: A Historical Profile of the Gamma Function
 * 				<i>American Mathematical Monthly</i> <b>66 (10)</b> 849-869
 * 		</li>
 * 		<li>
 * 			Whitaker, E. T., and G. N. Watson (1996): <i>A Course on Modern Analysis</i> <b>Cambridge
 * 				University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Beta Function https://en.wikipedia.org/wiki/Beta_function
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Gamma Function https://en.wikipedia.org/wiki/Gamma_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/beta/README.md">Estimation Techniques for Beta Function</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class AsymptoticLogEstimator
{

	/**
	 * Construct the Stirling Asymptote Estimate for the Log Beta Function
	 * 
	 * @return The Stirling Asymptote Estimate for the Log Beta Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final org.drip.function.definition.R2ToR1 Stirling()
		throws java.lang.Exception
	{
		return new org.drip.function.definition.R2ToR1()
		{
			@Override public double evaluate (
				final double x,
				final double y)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (x) ||
					!org.drip.numerical.common.NumberUtil.IsValid (y))
				{
					throw new java.lang.Exception
						("AsymptoticLogEstimator::Stirling::evaluate => Invalid Inputs");
				}

				return 0.5 * java.lang.Math.log (2. * java.lang.Math.PI) +
					(x - 0.5) * java.lang.Math.log (x) +
					(y - 0.5) * java.lang.Math.log (y) -
					(x + y - 0.5) * java.lang.Math.log (x + y);
			}
		};
	}

	/**
	 * Construct the Large X Asymptote Estimate for the Log Beta Function
	 * 
	 * @param logGammaEstimator The Log Gamma Estimator
	 * 
	 * @return The Large X Asymptote Estimate for the Log Beta Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final org.drip.function.definition.R2ToR1 LargeX (
		final org.drip.function.definition.R1ToR1 logGammaEstimator)
		throws java.lang.Exception
	{
		if (null == logGammaEstimator)
		{
			return null;
		}

		return new org.drip.function.definition.R2ToR1()
		{
			@Override public double evaluate (
				final double x,
				final double y)
				throws java.lang.Exception
			{
				if (!org.drip.numerical.common.NumberUtil.IsValid (x) ||
					!org.drip.numerical.common.NumberUtil.IsValid (y))
				{
					throw new java.lang.Exception
						("AsymptoticLogEstimator::LargeX::evaluate => Invalid Inputs");
				}

				return logGammaEstimator.evaluate (y) - y * java.lang.Math.log (x);
			}
		};
	}
}
