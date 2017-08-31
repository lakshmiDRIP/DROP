
package org.drip.spaces.cover;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * L1R1CoveringBounds implements the Lower/Upper Bounds for the Class of Non-decreasing R^1 To L1 R^1
 * 	Functions that are:
 * 	- Absolutely Bounded
 * 	- Have Bounded Variation.
 * 
 * The References are:
 * 
 * 	1) L. Birge (1987): Estimating a Density Under Order Restrictions: Non-asymptotic Minimax Risk, Annals of
 * 		Statistics 15 995-1012.
 * 
 * 	2) P. L. Bartlett, S. R. Kulkarni, and S. E. Posner (1997): Covering Numbers for Real-valued Function
 * 		Classes, IEEE Transactions on Information Theory 43 (5) 1721-1724.
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
