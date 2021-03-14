
package org.drip.spaces.cover;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * To L<sub>1</sub> R<sup>1</sup> for Functions that are:
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
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/cover/README.md">Vector Spaces Covering Number Estimator</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class L1R1CoveringBounds implements org.drip.spaces.cover.FunctionClassCoveringBounds {
	private double _dblBound = java.lang.Double.NaN;
	private double _dblSupport = java.lang.Double.NaN;
	private double _dblVariation = java.lang.Double.NaN;

	/**
	 * L1R1CoveringBounds Constructor
	 * 
	 * @param dblSupport The Ordinate Support
	 * @param dblVariation The Function Variation
	 * @param dblBound The Function Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public L1R1CoveringBounds (
		final double dblSupport,
		final double dblVariation,
		final double dblBound)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblSupport = dblSupport) || 0. >= _dblSupport ||
			!org.drip.numerical.common.NumberUtil.IsValid (_dblVariation = dblVariation) || 0. >= _dblVariation)
			throw new java.lang.Exception ("L1R1CoveringBounds ctr: Invalid Inputs");

		if (org.drip.numerical.common.NumberUtil.IsValid (_dblBound = dblBound) && _dblBound <= 0.5 *
			_dblVariation)
			throw new java.lang.Exception ("L1R1CoveringBounds ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Ordinate Support
	 * 
	 * @return The Ordinate Support
	 */

	public double support()
	{
		return _dblSupport;
	}

	/**
	 * Retrieve the Function Variation
	 * 
	 * @return The Function Variation
	 */

	public double variation()
	{
		return _dblVariation;
	}

	/**
	 * Retrieve the Function Bound
	 * 
	 * @return The Function Bound
	 */

	public double bound()
	{
		return _dblBound;
	}

	@Override public double logLowerBound (
		final double dblCover)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblCover) || 0. == dblCover)
			throw new java.lang.Exception ("L1R1CoveringBounds::logLowerBound => Invalid Inputs");

		double dblVariationCoverScale = dblCover / (_dblSupport * _dblVariation);
		double dblVariationLogLowerBound = 1. / (54. * dblVariationCoverScale);

		if (1. < 12. * dblVariationCoverScale)
			throw new java.lang.Exception ("L1R1CoveringBounds::logLowerBound => Invalid Inputs");

		return !org.drip.numerical.common.NumberUtil.IsValid (_dblBound) ? dblVariationLogLowerBound : 1. +
			dblVariationLogLowerBound * java.lang.Math.log (2.) + java.lang.Math.log (_dblSupport * _dblBound
				/ (6. * dblCover));
	}

	@Override public double logUpperBound (
		final double dblCover)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblCover))
			throw new java.lang.Exception ("L1R1CoveringBounds::logUpperBound => Invalid Inputs");

		double dblVariationCoverScale = dblCover / (_dblSupport * _dblVariation);

		if (1. < 12. * dblVariationCoverScale)
			throw new java.lang.Exception ("L1R1CoveringBounds::logUpperBound => Invalid Inputs");

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblBound))
			return java.lang.Math.log (2.) * 12. / dblVariationCoverScale;

		return java.lang.Math.log (2.) * 18. / dblVariationCoverScale + 3. * _dblSupport * (2. * _dblBound -
			_dblVariation) / (8. * dblCover);
	}
}
