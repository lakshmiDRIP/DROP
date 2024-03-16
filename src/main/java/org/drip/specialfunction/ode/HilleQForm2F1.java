
package org.drip.specialfunction.ode;

import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.R2ToR1;
import org.drip.numerical.common.NumberUtil;

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
 * <i>HilleQForm2F1</i> exposes the Coefficient Terms on the Q-form 2F1 Hyper-geometric ODE. The References
 * 	are:
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
 *  	<li>Construct the Hille Q-Form of 2F1 ODE</li>
 * 		<li>Retrieve the Q Form Function</li>
 * 		<li>Retrieve the v Function</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/ode/README.md">Special Function Ordinary Differential Equations</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class HilleQForm2F1 extends SecondOrder
{
	private R1ToR1 _q = null;
	private R1ToR1 _v = null;

	/**
	 * Construct the Hille Q-Form of 2F1 ODE
	 * 
	 * @param a A
	 * @param b B
	 * @param c C
	 * 
	 * @return Hille Q-Form of 2F1 ODE
	 */

	public static final HilleQForm2F1 Standard (
		final double a,
		final double b,
		final double c)
	{
		if (!NumberUtil.IsValid (a) || !NumberUtil.IsValid (b) || !NumberUtil.IsValid (c)) {
			return null;
		}

		final R1ToR1 q = new R1ToR1 (null) {
			@Override public double evaluate (
				final double z)
				throws Exception
			{
				if (!NumberUtil.IsValid (z)) {
					throw new Exception ("HilleQForm2F1::Standard::q::evaluate => Invalid Inputs");
				}

				double aMinusB = a - b;
				double zMinus1 = z - 1.;

				return (z * z * (1. - aMinusB * aMinusB) + z * (2. * c * (a + b - 1.) - 4. * a * b) + c *
					(2. - c)) / (4. * z * z * zMinus1 * zMinus1);
			}
		};

		try {
			return new HilleQForm2F1 (
				new R2ToR1() {
					@Override public double evaluate (
						final double z,
						final double u)
						throws Exception
					{
						return 1.;
					}
				},
				new R2ToR1() {
					@Override public double evaluate (
						final double z,
						final double w)
						throws Exception
					{
						return 0.;
					}
				},
				new R2ToR1() {
					@Override public double evaluate (
						final double z,
						final double u)
						throws Exception
					{
						if (!NumberUtil.IsValid (u)) {
							throw new Exception (
								"HilleQForm2F1::Standard::ZeroOrder::evaluate => Invalid Inputs"
							);
						}

						return q.evaluate (z) * u;
					}
				},
				q,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						if (!NumberUtil.IsValid (z)) {
							throw new Exception ("HilleQForm2F1::Standard::v::evaluate => Invalid Inputs");
						}

						return Math.pow (z, -0.5 * c) * Math.pow (1. - z, 0.5 * (c - a - b - 1.));
					}
				}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private HilleQForm2F1 (
		final R2ToR1 secondDerivativeCoefficient,
		final R2ToR1 firstDerivativeCoefficient,
		final R2ToR1 zeroDerivativeCoefficient,
		final R1ToR1 q,
		final R1ToR1 v)
		throws Exception
	{
		super (secondDerivativeCoefficient, firstDerivativeCoefficient, zeroDerivativeCoefficient);

		if (null == (_q = q) || null == (_v = v)) {
			throw new Exception ("HilleQForm2F1 Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Q Form Function
	 * 
	 * @return The Q Form Function
	 */

	public R1ToR1 q()
	{
		return _q;
	}

	/**
	 * Retrieve the v Function
	 * 
	 * @return The v Function
	 */

	public R1ToR1 v()
	{
		return _v;
	}
}
