
package org.drip.spaces.cover;

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
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>L1R1CoveringBounds</i> implements the Lower/Upper Bounds for the Class of Non-decreasing R<sup>1</sup>
 * 	to L<sub>1</sub> R<sup>1</sup> for Functions that are:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Absolutely Bounded
 *  	</li>
 *  	<li>
 * 			Have Bounded Variation
 *  	</li>
 *  </ul>
 * 
 * The References are:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		P. L. Bartlett, S. R. Kulkarni, and S. E. Posner (1997): Covering Numbers for Real-valued
 *  			Function Classes <i>IEEE Transactions on Information Theory</i> <b>45 (5)</b> 1721-1724
 *  	</li>
 *  	<li>
 *  		L. Birge (1987): Estimating a Density Under Order Restrictions: Non-asymptotic Minimax Risk
 *  			<i>Annals of Statistics</i> <b>15</b> 995-1012 
 *  	</li>
 *  </ul>
 *
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>L1R1CoveringBounds</i> Constructor</li>
 * 		<li>Retrieve the Ordinate Support</li>
 * 		<li>Retrieve the Function Variation</li>
 * 		<li>Retrieve the Function Bound</li>
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
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/cover/README.md">Vector Spaces Covering Number Estimator</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class L1R1CoveringBounds implements FunctionClassCoveringBounds
{
	private double _bound = Double.NaN;
	private double _support = Double.NaN;
	private double _variation = Double.NaN;

	/**
	 * <i>L1R1CoveringBounds</i> Constructor
	 * 
	 * @param support The Ordinate Support
	 * @param variation The Function Variation
	 * @param bound The Function Bound
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public L1R1CoveringBounds (
		final double support,
		final double variation,
		final double bound)
		throws Exception
	{
		if (!NumberUtil.IsValid (_support = support) || 0. >= _support ||
			!NumberUtil.IsValid (_variation = variation) || 0. >= _variation)
		{
			throw new Exception ("L1R1CoveringBounds ctr: Invalid Inputs");
		}

		if (NumberUtil.IsValid (_bound = bound) && _bound <= 0.5 * _variation) {
			throw new Exception ("L1R1CoveringBounds ctr: Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Ordinate Support
	 * 
	 * @return The Ordinate Support
	 */

	public double support()
	{
		return _support;
	}

	/**
	 * Retrieve the Function Variation
	 * 
	 * @return The Function Variation
	 */

	public double variation()
	{
		return _variation;
	}

	/**
	 * Retrieve the Function Bound
	 * 
	 * @return The Function Bound
	 */

	public double bound()
	{
		return _bound;
	}

	@Override public double logLowerBound (
		final double cover)
		throws Exception
	{
		if (!NumberUtil.IsValid (cover) || 0. == cover) {
			throw new Exception ("L1R1CoveringBounds::logLowerBound => Invalid Inputs");
		}

		double variationCoverScale = cover / (_support * _variation);
		double variationLogLowerBound = 1. / (54. * variationCoverScale);

		if (1. < 12. * variationCoverScale) {
			throw new Exception ("L1R1CoveringBounds::logLowerBound => Invalid Inputs");
		}

		return !NumberUtil.IsValid (_bound) ? variationLogLowerBound :
			1. + variationLogLowerBound * Math.log (2.) + Math.log (_support * _bound / (6. * cover));
	}

	@Override public double logUpperBound (
		final double cover)
		throws Exception
	{
		if (!NumberUtil.IsValid (cover)) {
			throw new Exception ("L1R1CoveringBounds::logUpperBound => Invalid Inputs");
		}

		double variationCoverScale = cover / (_support * _variation);

		if (1. < 12. * variationCoverScale) {
			throw new Exception ("L1R1CoveringBounds::logUpperBound => Invalid Inputs");
		}

		return !NumberUtil.IsValid (_bound) ? Math.log (2.) * 12. / variationCoverScale :
			Math.log (2.) * 18. / variationCoverScale +
				3. * _support * (2. * _bound - _variation) / (8. * cover);
	}
}
