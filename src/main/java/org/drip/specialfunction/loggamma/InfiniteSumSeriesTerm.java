
package org.drip.specialfunction.loggamma;

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
 * <i>InfiniteSumSeriesTerm</i> implements a Single Term in the Infinite Series for Log Gamma Estimation. The
 * References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Blagouchine, I. V. (2014): Re-discovery of Malmsten's Integrals, their Evaluation by Contour
 * 				Integration Methods, and some Related Results <i>Ramanujan Journal</i> <b>35 (1)</b> 21-110
 * 		</li>
 * 		<li>
 * 			Borwein, J. M., and R. M. Corless (2017): Gamma Function and the Factorial in the Monthly
 * 				https://arxiv.org/abs/1703.05349 <b>arXiv</b>
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
 * 			Wikipedia (2019): Gamma Function https://en.wikipedia.org/wiki/Gamma_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/loggamma/README.md">Analytic/Series/Integral Log Gamma Estimators</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class InfiniteSumSeriesTerm
{

	/**
	 * Construct the Euler Infinite Sum Series Term for Log Gamma
	 * 
	 * @return The Euler Infinite Sum Series Term for Log Gamma
	 */

	public static final org.drip.numerical.estimation.R1ToR1SeriesTerm Euler()
	{
		try
		{
			return new org.drip.numerical.estimation.R1ToR1SeriesTerm()
			{
				@Override public double value (
					final int order,
					final double z)
					throws java.lang.Exception
				{
					if (0 >= order ||
						!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. > z)
					{
						throw new java.lang.Exception
							("InfiniteSumSeriesTerm::Euler::value => Invalid Inputs");
					}

					return 0. == z ? 0. : z * java.lang.Math.log (1. + (1. / order)) -
						java.lang.Math.log (1. + (z / order));
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
	 * Construct the Weierstrass Infinite Sum Series Term for Log Gamma
	 * 
	 * @return The Weierstrass Infinite Sum Series Term for Log Gamma
	 */

	public static final org.drip.numerical.estimation.R1ToR1SeriesTerm Weierstrass()
	{
		try
		{
			return new org.drip.numerical.estimation.R1ToR1SeriesTerm()
			{
				@Override public double value (
					final int order,
					final double z)
					throws java.lang.Exception
				{
					if (0 >= order ||
						!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. > z)
					{
						throw new java.lang.Exception
							("InfiniteSumSeriesTerm::Euler::value => Invalid Inputs");
					}

					double zOverOrder = z / order;

					return 0. == z ? 0. : zOverOrder - java.lang.Math.log (1. + zOverOrder);
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
	 * Construct the Malmsten-Blagouchine Fourier Series Term for Log Gamma
	 * 
	 * @return The Malmsten-Blagouchine Fourier Series Term for Log Gamma
	 */

	public static final org.drip.numerical.estimation.R1ToR1SeriesTerm Fourier()
	{
		try
		{
			return new org.drip.numerical.estimation.R1ToR1SeriesTerm()
			{
				@Override public double value (
					final int order,
					final double z)
					throws java.lang.Exception
				{
					if (0 >= order ||
						!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. >= z || 1. <= z)
					{
						throw new java.lang.Exception
							("InfiniteSumSeriesTerm::Fourier::value => Invalid Inputs");
					}

					return java.lang.Math.log (order) *
						java.lang.Math.sin (2. * java.lang.Math.PI * order * z) / order;
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
	 * Construct the Blagouchine (2015) Series Term for Log Gamma
	 * 
	 * @return The Blagouchine (2015) Series Term for Log Gamma
	 */

	public static final org.drip.numerical.estimation.R1ToR1SeriesTerm Blagouchine2015()
	{
		try
		{
			return new org.drip.numerical.estimation.R1ToR1SeriesTerm()
			{
				@Override public double value (
					final int order,
					final double z)
					throws java.lang.Exception
				{
					if (0 >= order ||
						!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. >= z || 1. <= z || 0.5 == z)
					{
						throw new java.lang.Exception
							("InfiniteSumSeriesTerm::Blagouchine2015::value => Invalid Inputs");
					}

					return (
						org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI + java.lang.Math.log
							(order)
					) * java.lang.Math.sin (2. * java.lang.Math.PI * order * z) / order;
				}
			};
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
