
package org.drip.specialfunction.property;

import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.R1ToR1Property;
import org.drip.function.definition.R2ToR1;
import org.drip.specialfunction.beta.LogGammaEstimator;
import org.drip.specialfunction.definition.HypergeometricParameters;
import org.drip.specialfunction.definition.RegularHypergeometricEstimator;
import org.drip.specialfunction.hypergeometric.EulerQuadratureEstimator;
import org.drip.specialfunction.hypergeometric.GaussContiguousRelations;

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
 * <i>GaussContiguousEqualityLemma</i> verifies the Hyper-geometric Gauss Contiguous Equality Lemma
 * 	Properties. The References are:
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
 * It provides the following functionality:
 *
 *  <ul>
 * 		<li>Construct the Hyper-geometric Gauss Contiguous Identity #2 Verifier</li>
 * 		<li>Construct the Hyper-geometric Gauss Contiguous Identity #3 Verifier</li>
 * 		<li>Construct the Hyper-geometric Gauss Contiguous Identity #4 Verifier</li>
 * 		<li>Construct the Hyper-geometric Gauss Contiguous Identity #5 Verifier</li>
 * 		<li>Construct the Hyper-geometric Gauss Contiguous Identity #6 Verifier</li>
 * 		<li>Construct the Hyper-geometric Gauss Contiguous Identity #7 Verifier</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/property/README.md">Special Function Property Lemma Verifiers</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class GaussContiguousEqualityLemma
{

	/**
	 * Construct the Hyper-geometric Gauss Contiguous Identity #2 Verifier
	 * 
	 * @param a A
	 * @param b B
	 * @param c C
	 * 
	 * @return The Hyper-geometric Gauss Contiguous Identity #2 Verifier
	 */

	public static final R1ToR1Property RelationIdentity2 (
		final double a,
		final double b,
		final double c)
	{
		R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (1000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			final EulerQuadratureEstimator hypergeometricEstimator = new EulerQuadratureEstimator (
				new HypergeometricParameters (a, b, c),
				logBetaEstimator,
				10000
			);

			final RegularHypergeometricEstimator aPlusRegularHypergeometricEstimator = new
				GaussContiguousRelations (hypergeometricEstimator).aPlus();

			return new R1ToR1Property (
				R1ToR1Property.EQ,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return z * hypergeometricEstimator.derivative (z, 1);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return a * (
							aPlusRegularHypergeometricEstimator.regularHypergeometric (z) -
								hypergeometricEstimator.regularHypergeometric (z)
						);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Hyper-geometric Gauss Contiguous Identity #3 Verifier
	 * 
	 * @param a A
	 * @param b B
	 * @param c C
	 * 
	 * @return The Hyper-geometric Gauss Contiguous Identity #3 Verifier
	 */

	public static final R1ToR1Property RelationIdentity3 (
		final double a,
		final double b,
		final double c)
	{
		R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (1000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			final EulerQuadratureEstimator hypergeometricEstimator = new EulerQuadratureEstimator (
				new HypergeometricParameters (a, b, c),
				logBetaEstimator,
				10000
			);

			final RegularHypergeometricEstimator bPlusHypergeometricEstimator =
				new GaussContiguousRelations (hypergeometricEstimator).bPlus();

			return new R1ToR1Property (
				R1ToR1Property.EQ,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return z * hypergeometricEstimator.derivative (z, 1);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return b * (
							bPlusHypergeometricEstimator.regularHypergeometric (z) -
								hypergeometricEstimator.regularHypergeometric (z)
						);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Hyper-geometric Gauss Contiguous Identity #4 Verifier
	 * 
	 * @param a A
	 * @param b B
	 * @param c C
	 * 
	 * @return The Hyper-geometric Gauss Contiguous Identity #4 Verifier
	 */

	public static final R1ToR1Property RelationIdentity4 (
		final double a,
		final double b,
		final double c)
	{
		R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			final EulerQuadratureEstimator hypergeometricEstimator = new EulerQuadratureEstimator (
				new HypergeometricParameters (a, b, c),
				logBetaEstimator,
				100000
			);

			final RegularHypergeometricEstimator cMinusHypergeometricEstimator =
				new GaussContiguousRelations (hypergeometricEstimator).cMinus();

			return new R1ToR1Property (
				R1ToR1Property.EQ,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return z * hypergeometricEstimator.derivative (z, 1);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return (c - 1.) * (
							cMinusHypergeometricEstimator.regularHypergeometric (z) -
								hypergeometricEstimator.regularHypergeometric (z)
						);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Hyper-geometric Gauss Contiguous Identity #5 Verifier
	 * 
	 * @param a A
	 * @param b B
	 * @param c C
	 * 
	 * @return The Hyper-geometric Gauss Contiguous Identity #5 Verifier
	 */

	public static final R1ToR1Property RelationIdentity5 (
		final double a,
		final double b,
		final double c)
	{
		R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			final EulerQuadratureEstimator hypergeometricEstimator = new EulerQuadratureEstimator (
				new HypergeometricParameters (a, b, c),
				logBetaEstimator,
				100000
			);

			final RegularHypergeometricEstimator aMinusHypergeometricEstimator =
				new GaussContiguousRelations (hypergeometricEstimator).aMinus();

			return new R1ToR1Property (
				R1ToR1Property.EQ,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return z * hypergeometricEstimator.derivative (z, 1);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return (
							(c - a) * aMinusHypergeometricEstimator.regularHypergeometric (z) +
								(a - c + b * z) * hypergeometricEstimator.regularHypergeometric (z)
						) / (1. - z);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Hyper-geometric Gauss Contiguous Identity #6 Verifier
	 * 
	 * @param a A
	 * @param b B
	 * @param c C
	 * 
	 * @return The Hyper-geometric Gauss Contiguous Identity #6 Verifier
	 */

	public static final R1ToR1Property RelationIdentity6 (
		final double a,
		final double b,
		final double c)
	{
		R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			final EulerQuadratureEstimator hypergeometricEstimator = new EulerQuadratureEstimator (
				new HypergeometricParameters (a, b, c),
				logBetaEstimator,
				100000
			);

			final RegularHypergeometricEstimator bMinusHypergeometricEstimator =
				new GaussContiguousRelations (hypergeometricEstimator).bMinus();

			return new R1ToR1Property (
				R1ToR1Property.EQ,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return z * hypergeometricEstimator.derivative (z, 1);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return (
							(c - b) * bMinusHypergeometricEstimator.regularHypergeometric (z) +
								(b - c + a * z) * hypergeometricEstimator.regularHypergeometric (z)
						) / (1. - z);
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Hyper-geometric Gauss Contiguous Identity #7 Verifier
	 * 
	 * @param a A
	 * @param b B
	 * @param c C
	 * 
	 * @return The Hyper-geometric Gauss Contiguous Identity #7 Verifier
	 */

	public static final R1ToR1Property RelationIdentity7 (
		final double a,
		final double b,
		final double c)
	{
		R2ToR1 logBetaEstimator = LogGammaEstimator.Weierstrass (10000);

		if (null == logBetaEstimator) {
			return null;
		}

		try {
			final EulerQuadratureEstimator hypergeometricEstimator = new EulerQuadratureEstimator (
				new HypergeometricParameters (a, b, c),
				logBetaEstimator,
				100000
			);

			final RegularHypergeometricEstimator cPlusHypergeometricEstimator =
				new GaussContiguousRelations (hypergeometricEstimator).cPlus();

			return new R1ToR1Property (
				R1ToR1Property.EQ,
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return hypergeometricEstimator.derivative (z, 1);
					}
				},
				new R1ToR1 (null) {
					@Override public double evaluate (
						final double z)
						throws Exception
					{
						return (
							(c - a) * (c - b) * cPlusHypergeometricEstimator.regularHypergeometric (z) +
							c * (a + b - c) * hypergeometricEstimator.regularHypergeometric (z)
						) / (c * (1. - z));
					}
				},
				R1ToR1Property.MISMATCH_TOLERANCE
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
