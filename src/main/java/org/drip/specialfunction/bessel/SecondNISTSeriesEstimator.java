
package org.drip.specialfunction.bessel;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.estimation.R2ToR1Series;
import org.drip.specialfunction.definition.BesselFirstKindEstimator;
import org.drip.specialfunction.definition.BesselSecondKindEstimator;

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
 * <i>SecondNISTSeriesEstimator</i> implements the NIST Series Estimator for the Cylindrical Bessel Function
 * 	of the Second Kind. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): <i>Handbook of Mathematics Functions</i> <b>Dover Book
 * 				on Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Arfken, G. B., and H. J. Weber (2005): <i>Mathematical Methods for Physicists 6<sup>th</sup>
 * 				Edition</i> <b>Harcourt</b> San Diego
 * 		</li>
 * 		<li>
 * 			Temme N. M. (1996): <i>Special Functions: An Introduction to the Classical Functions of
 * 				Mathematical Physics 2<sup>nd</sup> Edition</i> <b>Wiley</b> New York
 * 		</li>
 * 		<li>
 * 			Watson, G. N. (1995): <i>A Treatise on the Theory of Bessel Functions</i> <b>Cambridge University
 * 				Press</b>
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Bessel Function https://en.wikipedia.org/wiki/Bessel_function
 * 		</li>
 * 	</ul>
 * 
 * It provides the following functionality:
 *
 *  <ul>
 * 		<li>Construct a Standard Instance of <i>SecondNISTSeriesEstimator</i></li>
 * 		<li>Retrieve the Bessel Second Kind NIST Series</li>
 * 		<li>Retrieve the Bessel Function First Kind Estimator</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/bessel/README.md">Ordered Bessel Function Variant Estimators</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SecondNISTSeriesEstimator extends BesselSecondKindEstimator
{
	private R2ToR1Series _besselSecondKindNISTSeries = null;
	private BesselFirstKindEstimator _besselFirstKindEstimator = null;

	/**
	 * Construct a Standard Instance of <i>SecondNISTSeriesEstimator</i>
	 * 
	 * @param digammaEstimator The Digamma Estimator
	 * @param gammaEstimator The Gamma Estimator
	 * @param besselFirstKindEstimator The Bessel Function First Kind Estimator
	 * @param termCount Count of the Number of Terms
	 * 
	 * @return The Standard Instance of <i>SecondNISTSeriesEstimator</i>
	 */

	public static final SecondNISTSeriesEstimator Standard (
		final R1ToR1 digammaEstimator,
		final R1ToR1 gammaEstimator,
		final BesselFirstKindEstimator besselFirstKindEstimator,
		final int termCount)
	{
		try {
			return new SecondNISTSeriesEstimator (
				SecondNISTSeries.SecondKind (digammaEstimator, gammaEstimator, termCount),
				besselFirstKindEstimator
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected SecondNISTSeriesEstimator (
		final R2ToR1Series besselSecondKindNISTSeries,
		final BesselFirstKindEstimator besselFirstKindEstimator)
		throws Exception
	{
		if (null == (_besselSecondKindNISTSeries = besselSecondKindNISTSeries) ||
			null == (_besselFirstKindEstimator = besselFirstKindEstimator)) {
			throw new Exception ("SecondNISTSeriesEstimator Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Bessel Second Kind NIST Series
	 * 
	 * @return The Bessel Second Kind NIST Series
	 */

	public R2ToR1Series besselSecondKindNISTSeries()
	{
		return _besselSecondKindNISTSeries;
	}

	/**
	 * Retrieve the Bessel Function First Kind Estimator
	 * 
	 * @return The Bessel Function First Kind Estimator
	 */

	public BesselFirstKindEstimator besselFirstKindEstimator()
	{
		return _besselFirstKindEstimator;
	}

	@Override public double bigY (
		final double alpha,
		final double z)
		throws Exception
	{
		return _besselSecondKindNISTSeries.evaluate (alpha, z) +
			2. / Math.PI * Math.log (0.5 * z) * _besselFirstKindEstimator.bigJ (alpha, z);
	}
}
