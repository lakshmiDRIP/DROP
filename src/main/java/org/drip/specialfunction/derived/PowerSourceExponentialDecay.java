
package org.drip.specialfunction.derived;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.differentiation.DerivativeControl;
import org.drip.numerical.r1integration.NewtonCotesQuadratureGenerator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>PowerSourceExponentialDecay</i> implements the Power Source Exponential Decay Function. The References
 * 	are:
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
 * 	It provides the following functionality:
 *
 *  <ul>
 * 		<li>Construct the Analytic Version of <i>PowerSourceExponentialDecay</i></li>
 * 		<li><i>PowerSourceExponentialDecay</i> Constructor</li>
 * 		<li>Retrieve the Source Power Coefficient</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation and Analysis</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/derived/README.md">Special Functions Derived using Others</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PowerSourceExponentialDecay extends R1ToR1
{
	private double _sourcePowerCoefficient = Double.NaN;

	/**
	 * Construct the Analytic Version of <i>PowerSourceExponentialDecay</i>
	 * 
	 * @param logGammaEstimator The Log Gamma Estimator
	 * @param sourcePowerCoefficient The Source Power Coefficient
	 * 
	 * @return The Analytic Version of <i>PowerSourceExponentialDecay</i>
	 */

	public static final PowerSourceExponentialDecay Analytic (
		final org.drip.function.definition.R1ToR1 logGammaEstimator,
		final double sourcePowerCoefficient)
	{
		if (null == logGammaEstimator)
		{
			return null;
		}

		try
		{
			return new PowerSourceExponentialDecay (
				null,
				sourcePowerCoefficient
			)
			{
				@Override public double evaluate (
					final double exponentialDecayCoefficient)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (exponentialDecayCoefficient) ||
						0. >= exponentialDecayCoefficient)
					{
						throw new java.lang.Exception
							("PowerSourceExponentialDecay:: Analytic::evaluate => Invalid Inputs");
					}

					double bPlusOne = sourcePowerCoefficient + 1.;

					return java.lang.Math.exp (
						logGammaEstimator.evaluate (bPlusOne) -
						bPlusOne * java.lang.Math.log (exponentialDecayCoefficient)
					);
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
	 * <i>PowerSourceExponentialDecay</i> Constructor
	 * 
	 * @param derivativeControl The Derivative Control
	 * @param sourcePowerCoefficient The Source Power Coefficient
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public PowerSourceExponentialDecay (
		final DerivativeControl derivativeControl,
		final double sourcePowerCoefficient)
		throws java.lang.Exception
	{
		super (derivativeControl);

		if (!NumberUtil.IsValid (_sourcePowerCoefficient = sourcePowerCoefficient) ||
			0. >= _sourcePowerCoefficient) {
			throw new Exception ("PowerSourceExponentialDecay Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Source Power Coefficient
	 * 
	 * @return The Source Power Coefficient
	 */

	public double sourcePowerCoefficient()
	{
		return _sourcePowerCoefficient;
	}

	@Override public double evaluate (
		final double exponentialDecayCoefficient)
		throws Exception
	{
		if (!NumberUtil.IsValid (exponentialDecayCoefficient) || 0. >= exponentialDecayCoefficient) {
			throw new Exception ("PowerSourceExponentialDecay::evaluate => Invalid Inputs");
		}

		return NewtonCotesQuadratureGenerator.GaussLaguerreLeftDefinite (0., 100).integrate (
			new R1ToR1 (null) {
				@Override public double evaluate (
					final double t)
					throws Exception
				{
					return Double.isInfinite (t) ? 0. : Math.pow (t, _sourcePowerCoefficient) *
						Math.exp (-1. * exponentialDecayCoefficient * t);
				}
			}
		);
	}
}
