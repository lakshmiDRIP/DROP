
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
 * SegmentMonicBasisFunction implements the local monic B Spline that envelopes the predictor ordinates, and
 * 	the corresponding set of ordinates/basis functions. SegmentMonicBasisFunction uses the left/right
 *  TensionBasisHat instances to achieve its implementation goals.
 *
 * @author Lakshmi Krishnamurthy
 */

public class SegmentMonicBasisFunction extends org.drip.spline.bspline.SegmentBasisFunction {
	private org.drip.spline.bspline.TensionBasisHat _tbhLeft = null;
	private org.drip.spline.bspline.TensionBasisHat _tbhRight = null;

	/**
	 * SegmentMonicBasisFunction constructor
	 * 
	 * @param tbhLeft Left Tension Basis Hat Function
	 * @param tbhRight Right Tension Basis Hat Function
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public SegmentMonicBasisFunction (
		final org.drip.spline.bspline.TensionBasisHat tbhLeft,
		final org.drip.spline.bspline.TensionBasisHat tbhRight)
		throws java.lang.Exception
	{
		super (2, tbhLeft.left(), tbhRight.left(), tbhRight.right());

		if (null == (_tbhLeft = tbhLeft) || null == (_tbhRight = tbhRight))
			throw new java.lang.Exception ("SegmentMonicBasisFunction ctr: Invalid Inputs");
	}

	@Override public double evaluate (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate))
			throw new java.lang.Exception ("SegmentMonicBasisFunction::evaluate => Invalid Inputs");

		if (dblPredictorOrdinate < leading() || dblPredictorOrdinate > trailing()) return 0.;

		return dblPredictorOrdinate < following() ? _tbhLeft.evaluate (dblPredictorOrdinate) :
			_tbhRight.evaluate (dblPredictorOrdinate);
	}

	@Override public double derivative (
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate))
			throw new java.lang.Exception ("SegmentMonicBasisFunction::derivative => Invalid Inputs");

		if (dblPredictorOrdinate < leading() || dblPredictorOrdinate > trailing()) return 0.;

		return dblPredictorOrdinate < following() ? _tbhLeft.derivative (dblPredictorOrdinate, iOrder) :
			_tbhRight.derivative (dblPredictorOrdinate, iOrder);
	}

	@Override public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBegin) || !org.drip.quant.common.NumberUtil.IsValid
			(dblEnd))
			throw new java.lang.Exception ("SegmentMonicBasisFunction::integrate => Invalid Inputs");

		if (dblBegin >= dblEnd) return 0.;

		if (dblBegin <= leading()) {
			if (dblEnd <= leading()) return 0.;

			if (dblEnd <= following()) return _tbhLeft.integrate (leading(), dblEnd);

			if (dblEnd <= trailing())
				return _tbhLeft.integrate (leading(), following()) + _tbhRight.integrate (following(),
					dblEnd);

			return _tbhLeft.integrate (leading(), following()) + _tbhRight.integrate (following(),
				trailing());
		}

		if (dblBegin <= following()) {
			if (dblEnd <= following()) return _tbhLeft.integrate (dblBegin, dblEnd);

			if (dblEnd <= trailing())
				return _tbhLeft.integrate (dblBegin, following()) + _tbhRight.integrate (following(),
					dblEnd);

			return _tbhLeft.integrate (dblBegin, following()) + _tbhRight.integrate (following(),
				trailing());
		}

		if (dblBegin <= trailing()) {
			if (dblEnd <= trailing()) return _tbhRight.integrate (following(), dblEnd);

			return _tbhRight.integrate (following(), trailing());
		}

		return 0.;
	}

	@Override public double normalizer()
		throws java.lang.Exception
	{
		return _tbhLeft.integrate (leading(), following()) + _tbhRight.integrate (following(), trailing());
	}

	@Override public double normalizedCumulative (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate))
			throw new java.lang.Exception
				("SegmentMonicBasisFunction::normalizedCumulative => Invalid Inputs");

		if (dblPredictorOrdinate <= leading()) return 0.;

		if (dblPredictorOrdinate >= trailing()) return 1.;

		if (dblPredictorOrdinate <= following())
			return _tbhLeft.integrate (leading(), dblPredictorOrdinate) / normalizer();

		return (_tbhLeft.integrate (leading(), following()) + _tbhRight.integrate (following(),
			dblPredictorOrdinate)) / normalizer();
	}
}
