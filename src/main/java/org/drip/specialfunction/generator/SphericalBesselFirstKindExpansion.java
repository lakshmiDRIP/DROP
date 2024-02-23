
package org.drip.specialfunction.generator;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.estimation.R2ToR1Series;
import org.drip.numerical.estimation.R2ToR1SeriesTerm;
import org.drip.specialfunction.definition.SphericalBesselFirstKindEstimator;

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
 * <i>SphericalBesselFirstKindExpansion</i> implements the Generating Function and the Expansion Terms for
 * 	the Spherical Bessel Function of the First Kind. The References are:
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
 * 	It provides the following functionality:
 *
 *  <ul>
 * 		<li><i>SphericalBesselFirstKindExpansion</i> Constructor</li>
 * 		<li>Retrieve the First Kind Spherical Bessel Function Estimator</li>
 * 		<li>Retrieve the Gamma Estimator</li>
 * 		<li>Generate the Default Series</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/generator/README.md">Special Function Series Term Generators</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SphericalBesselFirstKindExpansion extends SeriesExpansion
{
	private R1ToR1 _gammaEstimator = null;
	private SphericalBesselFirstKindEstimator _sphericalBesselFirstKindEstimator = null;

	/**
	 * <i>SphericalBesselFirstKindExpansion</i> Constructor
	 * 
	 * @param sphericalBesselFirstKindEstimator First Kind Spherical Bessel Function Estimator
	 * @param gammaEstimator The Gamma Estimator
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public SphericalBesselFirstKindExpansion (
		final SphericalBesselFirstKindEstimator sphericalBesselFirstKindEstimator,
		final R1ToR1 gammaEstimator)
		throws Exception
	{
		if (null == (_sphericalBesselFirstKindEstimator = sphericalBesselFirstKindEstimator) ||
			null == (_gammaEstimator = gammaEstimator))
		{
			throw new Exception ("SphericalBesselFirstKindExpansion Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the First Kind Spherical Bessel Function Estimator
	 * 
	 * @return The First Kind Spherical Bessel Function Estimator
	 */

	public SphericalBesselFirstKindEstimator sphericalBesselFirstKindEstimator()
	{
		return _sphericalBesselFirstKindEstimator;
	}

	/**
	 * Retrieve the Gamma Estimator
	 * 
	 * @return The Gamma Estimator
	 */

	public R1ToR1 gammaEstimator()
	{
		return _gammaEstimator;
	}

	@Override public double evaluate (
		final double z,
		final double t)
		throws Exception
	{
		if (!NumberUtil.IsValid (z) || !NumberUtil.IsValid (t)) {
			throw new Exception ("SphericalBesselFirstKindExpansion::evaluate => Invalid Inputs");
		}

		return Math.cos (Math.sqrt (z * z - 2. * z * t)) / z;
	}

	@Override public R2ToR1SeriesTerm seriesTerm()
	{
		return new R2ToR1SeriesTerm() {
			@Override public double value (
				final int order,
				final double z,
				final double t)
				throws Exception
			{
				if (0 > order || !NumberUtil.IsValid (z) || !NumberUtil.IsValid (t)) {
					throw new Exception (
						"SphericalBesselFirstKindExpansion::seriesTerm::value => Invalid Inputs"
					);
				}

				return Math.pow (t, order) * _sphericalBesselFirstKindEstimator.evaluate (order - 1, z) /
					_gammaEstimator.evaluate (order + 1);
			}
		};
	}

	/**
	 * Generate the Default Series
	 * 
	 * @return The Default Series
	 */

	public R2ToR1Series series()
	{
		return series (0, Integer.MAX_VALUE);
	}
}
