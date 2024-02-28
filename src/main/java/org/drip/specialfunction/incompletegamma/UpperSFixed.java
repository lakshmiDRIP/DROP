
package org.drip.specialfunction.incompletegamma;

import org.drip.numerical.differentiation.DerivativeControl;
import org.drip.numerical.estimation.R1Estimate;
import org.drip.numerical.estimation.R1ToR1Estimator;
import org.drip.numerical.estimation.R1ToR1Series;
import org.drip.specialfunction.loggamma.NemesAnalyticEstimator;

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
 * <i>UpperSFixed</i> implements the Upper Incomplete Gamma Function using the Power Expansion Series,
 * 	starting with s = 0 if Recurrence is employed. The References are:
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
 * 		<li>Compute the NIST (2019) Version of Upper Incomplete Gamma s = 0 Estimator</li>
 * 		<li>Compute the Weisstein Version of Upper Incomplete Gamma Estimator</li>
 * 		<li><i>UpperSFixed</i> Constructor</li>
 * 		<li>Retrieve the Underlying Upper S Fixed Series</li>
 * 		<li>Evaluate the Upper Gamma (-n, z) recursively from n = 0</li>
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

public abstract class UpperSFixed extends R1ToR1Estimator
{
	private R1ToR1Series _upperSFixedSeries = null;

	/**
	 * Compute the NIST (2019) Version of Upper Incomplete Gamma s = 0 Estimator
	 * 
	 * @param termCount Number of Terms in the Estimation
	 * 
	 * @return NIST (2019) Version of Upper Incomplete Gamma s = 0 Estimator
	 */

	public static final UpperSFixed NIST2019 (
		final int termCount)
	{
		try
		{
			return new UpperSFixed (
				org.drip.specialfunction.incompletegamma.UpperSFixedSeries.NIST2019 (termCount),
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z) || z <= 0.)
					{
						throw new java.lang.Exception ("UpperSFixed::evaluate => Invalid Inputs");
					}

					return -1. * (org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI +
						java.lang.Math.log (z) + upperSFixedSeries().evaluate (z));
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
	 * Compute the Weisstein Version of Upper Incomplete Gamma Estimator
	 * 
	 * @param s s
	 * 
	 * @return Weisstein Version of Upper Incomplete Gamma Estimator
	 */

	public static final UpperSFixed Weisstein (
		final int s)
	{
		try
		{
			return new UpperSFixed (
				org.drip.specialfunction.incompletegamma.UpperSFixedSeries.Weisstein (s),
				null
			)
			{
				@Override public double evaluate (
					final double z)
					throws java.lang.Exception
				{
					if (!org.drip.numerical.common.NumberUtil.IsValid (z) || z < 0.)
					{
						throw new java.lang.Exception ("UpperSFixed::Weisstein::evaluate => Invalid Inputs");
					}

					return upperSFixedSeries().evaluate (z) * java.lang.Math.exp (
						(0 == s ? 1. : new org.drip.specialfunction.loggamma.NemesAnalyticEstimator (null).evaluate (s))
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
	 * <i>UpperSFixed</i> Constructor
	 * 
	 * @param upperSFixedSeries R<sup>1</sup> To R<sup>1</sup> Upper S Fixed Limit Series
	 * @param derivativeControl Differential Control
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	protected UpperSFixed (
		final R1ToR1Series upperSFixedSeries,
		final DerivativeControl derivativeControl)
		throws Exception
	{
		super (derivativeControl);

		_upperSFixedSeries = upperSFixedSeries;
	}

	/**
	 * Retrieve the Underlying Upper S Fixed Series
	 * 
	 * @return The Underlying Upper S Fixed Series
	 */

	public R1ToR1Series upperSFixedSeries()
	{
		return _upperSFixedSeries;
	}

	@Override public R1Estimate seriesEstimateNative (
		final double x)
	{
		return null == _upperSFixedSeries ? seriesEstimate (x, null, null) :
			seriesEstimate (x, _upperSFixedSeries.termWeightMap(), _upperSFixedSeries);
	}

	/**
	 * Evaluate the Upper Gamma (-n, z) recursively from n = 0
	 * 
	 * @param n n
	 * @param z z
	 * 
	 * @return Upper Gamma (-n, z)
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double evaluateRecursive (
		final int n,
		final double z)
		throws Exception
	{
		R1ToR1Series upperSRecursiveSeries = UpperSFixedSeries.NIST2019Recursive (n);

		if (null == upperSRecursiveSeries) {
			throw new Exception ("UpperSFixed::evaluateRecursive => Invalid Inputs");
		}

		return (
			upperSRecursiveSeries.evaluate (z) * Math.exp (-n * Math.log (z) - z) +
				(n % 2 == 0 ? 1. : -1.) * evaluate (z)
		) / Math.exp (new NemesAnalyticEstimator (null).evaluate (n));
	}
}
