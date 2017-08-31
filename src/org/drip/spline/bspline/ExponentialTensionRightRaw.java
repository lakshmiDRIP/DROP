
package org.drip.spline.bspline;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * ExponentialTensionRightRaw implements the TensionBasisHat interface in accordance with the raw right
 *  exponential hat basis function laid out in the basic framework outlined in Koch and Lyche (1989), Koch
 *  and Lyche (1993), and Kvasov (2000) Papers.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ExponentialTensionRightRaw extends org.drip.spline.bspline.TensionBasisHat {

	/**
	 * ExponentialTensionRightRaw constructor
	 * 
	 * @param dblLeftPredictorOrdinate The Left Predictor Ordinate
	 * @param dblRightPredictorOrdinate The Right Predictor Ordinate
	 * @param dblTension Tension of the Tension Hat Function
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public ExponentialTensionRightRaw (
		final double dblLeftPredictorOrdinate,
		final double dblRightPredictorOrdinate,
		final double dblTension)
		throws java.lang.Exception
	{
		super (dblLeftPredictorOrdinate, dblRightPredictorOrdinate, dblTension);
	}

	@Override public double evaluate (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!in (dblPredictorOrdinate)) return 0.;

		double dblAdjPredictorOrdinate = tension() * (right() - dblPredictorOrdinate);

		return (java.lang.Math.sinh (dblAdjPredictorOrdinate) - dblAdjPredictorOrdinate) / (tension() *
			tension() * java.lang.Math.sinh (tension() * (right() - left())));
	}

	@Override public double derivative (
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (0 > iOrder)
			throw new java.lang.Exception ("ExponentialTensionRightRaw::derivative => Invalid Inputs");

		if (!in (dblPredictorOrdinate)) return 0.;

		double dblWidth = right() - left();

		if (1 == iOrder)
			return (1. - java.lang.Math.cosh (tension() * (right() - dblPredictorOrdinate))) / (tension() *
				java.lang.Math.sinh (tension() * dblWidth));

		return java.lang.Math.pow (-tension(), iOrder - 2) * (0 == iOrder % 2 ? java.lang.Math.sinh
			(tension() * (right() - dblPredictorOrdinate)) : java.lang.Math.cosh (tension() * (right() -
				dblPredictorOrdinate))) / java.lang.Math.sinh (tension() * dblWidth);
	}

	@Override public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBegin) || !org.drip.quant.common.NumberUtil.IsValid
			(dblEnd))
			throw new java.lang.Exception ("ExponentialTensionRightRaw::integrate => Invalid Inputs");

		double dblBoundedBegin = org.drip.quant.common.NumberUtil.Bound (dblBegin, left(), right());

		double dblBoundedEnd = org.drip.quant.common.NumberUtil.Bound (dblEnd, left(), right());

		if (dblBoundedBegin >= dblBoundedEnd) return 0.;

		if (0. == tension()) return dblBoundedEnd - dblBoundedBegin;

		double dblBoundedBeginRightGap = right() - dblBoundedBegin;

		double dblBoundedEndRightGap = right() - dblBoundedEnd;

		return (0.5 * tension() * tension() * (dblBoundedEndRightGap * dblBoundedEndRightGap -
			dblBoundedBeginRightGap * dblBoundedBeginRightGap) + java.lang.Math.cosh (tension() *
				dblBoundedBeginRightGap) - java.lang.Math.cosh (tension() * dblBoundedEndRightGap)) /
					(tension() * tension() * tension() * java.lang.Math.sinh (tension() * (right() -
						left())));
	}

	@Override public double normalizer()
		throws java.lang.Exception
	{
		double dblWidth = right() - left();

		return (-0.5 * tension() * tension() * dblWidth * dblWidth + java.lang.Math.cosh (tension() *
			dblWidth) - 1.) / (tension() * tension() * tension() * java.lang.Math.sinh (tension() *
				dblWidth));
	}
}
