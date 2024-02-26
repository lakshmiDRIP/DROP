
package org.drip.specialfunction.hypergeometric;

import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.R2ToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.integration.NewtonCotesQuadratureGenerator;
import org.drip.specialfunction.definition.HypergeometricParameters;
import org.drip.specialfunction.definition.RegularHypergeometricEstimator;

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
 * <i>EulerQuadratureEstimator</i> estimates the Hyper-geometric Function using the Euler Integral
 * 	Representation. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Gessel, I., and D. Stanton (1982): Strange Evaluations of Hyper-geometric Series <i>SIAM Journal
 * 				on Mathematical Analysis</i> <b>13 (2)</b> 295-308
 * 		</li>
 * 		<li>
 * 			Koepf, W (1995): Algorithms for m-fold Hyper-geometric Summation <i>Journal of Symbolic
 * 				Computation</i> <b>20 (4)</b> 399-417
 * 		</li>
 * 		<li>
 * 			Lavoie, J. L., F. Grondin, and A. K. Rathie (1996): Generalization of Whipple’s Theorem on the
 * 				Sum of a (_2^3)F(a,b;c;z) <i>Journal of Computational and Applied Mathematics</i> <b>72</b>
 * 				293-300
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2019): Hyper-geometric Function
 * 				https://dlmf.nist.gov/15
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Hyper-geometric Function https://en.wikipedia.org/wiki/Hypergeometric_function
 * 		</li>
 * 	</ul>
 * 
 * 	It provides the following functionality:
 *
 *  <ul>
 * 		<li><i>EulerQuadratureEstimator</i> Constructor</li>
 * 		<li>Retrieve the Quadrature Count</li>
 * 		<li>Retrieve the Log Beta Estimator</li>
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
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation and Analysis</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/hypergeometric/README.md">Hyper-geometric Function Estimation Schemes</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class EulerQuadratureEstimator extends RegularHypergeometricEstimator
{
	private int _quadratureCount = -1;
	private R2ToR1 _logBetaEstimator = null;

	/**
	 * <i>EulerQuadratureEstimator</i> Constructor
	 * 
	 * @param hypergeometricParameters Hyper-geometric Parameters
	 * @param logBetaEstimator Log Beta Estimator
	 * @param quadratureCount Count of the Integrand Quadrature
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public EulerQuadratureEstimator (
		final HypergeometricParameters hypergeometricParameters,
		final R2ToR1 logBetaEstimator,
		final int quadratureCount)
		throws Exception
	{
		super (hypergeometricParameters);

		if (null == (_logBetaEstimator = logBetaEstimator) || 0 >= (_quadratureCount = quadratureCount)) {
			throw new Exception ("EulerQuadratureEstimator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Quadrature Count
	 * 
	 * @return The Quadrature Count
	 */

	public int quadratureCount()
	{
		return _quadratureCount;
	}

	/**
	 * Retrieve the Log Beta Estimator
	 * 
	 * @return The Log Beta Estimator
	 */

	public R2ToR1 logBetaEstimator()
	{
		return _logBetaEstimator;
	}

	@Override public double regularHypergeometric (
		final double z)
		throws Exception
	{
		if (!NumberUtil.IsValid (z) || -1. > z || 1. < z) {
			throw new Exception ("EulerQuadratureEstimator::regularHypergeometric => Invalid Inputs");
		}

		HypergeometricParameters hypergeometricParameters = hypergeometricParameters();

		final double a = hypergeometricParameters.a();

		final double b = hypergeometricParameters.b();

		final double c = hypergeometricParameters.c();

		return NewtonCotesQuadratureGenerator.Zero_PlusOne (0., 1., _quadratureCount).integrate (
			new R1ToR1 (null) {
				@Override public double evaluate (
					final double t)
					throws Exception
				{
					return 0. == t || 1. == t ? 0. :
						Math.pow (t, b - 1.) * Math.pow (1. - t, c - b - 1.) * Math.pow (1. - z * t, -a);
				}
			}
		) * Math.exp (-1. * _logBetaEstimator.evaluate (b, c - b));
	}

	@Override public double derivative (
		final double z,
		final int order)
		throws java.lang.Exception
	{
		org.drip.specialfunction.definition.HypergeometricParameters hypergeometricParameters =
			hypergeometricParameters();

		double a = hypergeometricParameters.a();

		double b = hypergeometricParameters.b();

		double c = hypergeometricParameters.c();

		return new EulerQuadratureEstimator (
			new org.drip.specialfunction.definition.HypergeometricParameters (
				a + order,
				b + order,
				c + order
			),
			_logBetaEstimator,
			_quadratureCount
		).regularHypergeometric (z) * org.drip.numerical.common.NumberUtil.PochhammerSymbol (
			a,
			order
		) * org.drip.numerical.common.NumberUtil.PochhammerSymbol (
			b,
			order
		) / org.drip.numerical.common.NumberUtil.PochhammerSymbol (
			c,
			order
		);
	}

	@Override public org.drip.specialfunction.definition.RegularHypergeometricEstimator albinate (
		final org.drip.specialfunction.definition.HypergeometricParameters hypergeometricParametersAlbinate,
		final org.drip.function.definition.R1ToR1 valueScaler,
		final org.drip.function.definition.R1ToR1 zTransformer)
	{
		try
		{
			return new EulerQuadratureEstimator (
				hypergeometricParametersAlbinate,
				_logBetaEstimator,
				_quadratureCount
			)
			{
				@Override public double regularHypergeometric (
					final double z)
					throws java.lang.Exception
				{
					return (null == valueScaler ? 1. : valueScaler.evaluate (z)) *
						super.regularHypergeometric (null == zTransformer ? z : zTransformer.evaluate (z));
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
