
package org.drip.specialfunction.incompletegamma;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.differentiation.DerivativeControl;

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
 * <i>UpperLimitPowerIntegrand</i> contains the Integrand that is the Product of the Limit raised to a Power
 * 	Exponent and the corresponding Upper Incomplete Gamma, for a given s. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Geddes, K. O., M. L. Glasser, R. A. Moore, and T. C. Scott (1990): Evaluation of Classes of
 * 				Definite Integrals involving Elementary Functions via Differentiation of Special Functions
 * 				<i>Applicable Algebra in Engineering, Communications, and </i> <b>1 (2)</b> 149-165
 * 		</li>
 * 		<li>
 * 			Gradshteyn, I. S., I. M. Ryzhik, Y. V. Geronimus, M. Y. Tseytlin, and A. Jeffrey (2015):
 * 				<i>Tables of Integrals, Series, and Products</i> <b>Academic Press</b>
 * 		</li>
 * 		<li>
 * 			Mathar, R. J. (2010): Numerical Evaluation of the Oscillatory Integral over
 *				e<sup>iπx</sup> x<sup>(1/x)</sup> between 1 and ∞
 *				https://arxiv.org/pdf/0912.3844.pdf <b>arXiV</b>
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2019): Incomplete Gamma and Related Functions
 * 				https://dlmf.nist.gov/8
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Incomplete Gamma Function
 * 				https://en.wikipedia.org/wiki/Incomplete_gamma_function
 * 		</li>
 * 	</ul>
 * 
 * 	It provides the following functionality:
 *
 *  <ul>
 * 		<li><i>UpperLimitPowerIntegrand</i> Constructor</li>
 * 		<li>Retrieve s</li>
 * 		<li>Retrieve the Limit Power Exponent</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/incompletegamma/README.md">Upper/Lower Incomplete Gamma Functions</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class UpperLimitPowerIntegrand extends R1ToR1
{
	private double _s = Double.NaN;
	private double _limitExponent = Double.NaN;

	/**
	 * <i>UpperLimitPowerIntegrand</i> Constructor
	 * 
	 * @param derivativeControl The Derivative Control
	 * @param s s
	 * @param limitExponent The Limit Power Exponent
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public UpperLimitPowerIntegrand (
		final DerivativeControl derivativeControl,
		final double s,
		final double limitExponent)
		throws Exception
	{
		super (derivativeControl);

		if (!NumberUtil.IsValid (_s = s) ||
			!NumberUtil.IsValid (_limitExponent = limitExponent) ||
			1. >= _limitExponent)
		{
			throw new Exception ("UpperLimitPowerIntegrand Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve s
	 * 
	 * @return s
	 */

	public double s()
	{
		return _s;
	}

	/**
	 * Retrieve the Limit Power Exponent
	 * 
	 * @return The Limit Power Exponent
	 */

	public double limitExponent()
	{
		return _limitExponent;
	}

	@Override public double evaluate (
		final double z)
		throws Exception
	{
		if (!NumberUtil.IsValid (z)) {
			throw new Exception ("UpperLimitPowerIntegrand::evaluate => Invalid Inputs");
		}

		return Math.pow (z, _limitExponent - 1.) * new UpperEulerIntegral (null, z).evaluate (_s);
	}

	@Override public R1ToR1 antiDerivative()
	{
		return new R1ToR1 (null) {
			@Override public double evaluate (
				final double z)
				throws Exception
			{
				if (!NumberUtil.IsValid (z)) {
					throw new Exception (
						"UpperLimitPowerIntegrand::antiDerivative::evaluate => Invalid Inputs"
					);
				}

				UpperEulerIntegral upperEulerIntegral = new UpperEulerIntegral (null, z);

				return (
					Math.pow (z, _limitExponent) * upperEulerIntegral.evaluate (_s) -
						upperEulerIntegral.evaluate (_s + _limitExponent)
				) / _limitExponent;
			}
		};
	}

	@Override public double integrate (
		final double left,
		final double right)
		throws Exception
	{
		R1ToR1 antiDerivative = antiDerivative();

		return antiDerivative.evaluate (right) - antiDerivative.evaluate (left);
	}
}
