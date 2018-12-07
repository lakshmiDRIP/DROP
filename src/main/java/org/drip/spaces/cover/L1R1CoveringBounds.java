
package org.drip.spaces.cover;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces">Spaces</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/cover">Cover</a></li>
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
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblSupport = dblSupport) || 0. >= _dblSupport ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblVariation = dblVariation) || 0. >= _dblVariation)
			throw new java.lang.Exception ("L1R1CoveringBounds ctr: Invalid Inputs");

		if (org.drip.quant.common.NumberUtil.IsValid (_dblBound = dblBound) && _dblBound <= 0.5 *
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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblCover) || 0. == dblCover)
			throw new java.lang.Exception ("L1R1CoveringBounds::logLowerBound => Invalid Inputs");

		double dblVariationCoverScale = dblCover / (_dblSupport * _dblVariation);
		double dblVariationLogLowerBound = 1. / (54. * dblVariationCoverScale);

		if (1. < 12. * dblVariationCoverScale)
			throw new java.lang.Exception ("L1R1CoveringBounds::logLowerBound => Invalid Inputs");

		return !org.drip.quant.common.NumberUtil.IsValid (_dblBound) ? dblVariationLogLowerBound : 1. +
			dblVariationLogLowerBound * java.lang.Math.log (2.) + java.lang.Math.log (_dblSupport * _dblBound
				/ (6. * dblCover));
	}

	@Override public double logUpperBound (
		final double dblCover)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblCover))
			throw new java.lang.Exception ("L1R1CoveringBounds::logUpperBound => Invalid Inputs");

		double dblVariationCoverScale = dblCover / (_dblSupport * _dblVariation);

		if (1. < 12. * dblVariationCoverScale)
			throw new java.lang.Exception ("L1R1CoveringBounds::logUpperBound => Invalid Inputs");

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblBound))
			return java.lang.Math.log (2.) * 12. / dblVariationCoverScale;

		return java.lang.Math.log (2.) * 18. / dblVariationCoverScale + 3. * _dblSupport * (2. * _dblBound -
			_dblVariation) / (8. * dblCover);
	}
}
