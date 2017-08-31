
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
 * TensionBasisHat implements the common basis hat function that form the basis for all B Splines. It
 *  contains the left/right ordinates, the tension, and the normalizer.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class TensionBasisHat extends org.drip.function.definition.R1ToR1 {
	private double _dblTension = java.lang.Double.NaN;
	private double _dblLeftPredictorOrdinate = java.lang.Double.NaN;
	private double _dblRightPredictorOrdinate = java.lang.Double.NaN;

	protected TensionBasisHat (
		final double dblLeftPredictorOrdinate,
		final double dblRightPredictorOrdinate,
		final double dblTension)
		throws java.lang.Exception
	{
		super (null);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblLeftPredictorOrdinate = dblLeftPredictorOrdinate)
			|| !org.drip.quant.common.NumberUtil.IsValid (_dblRightPredictorOrdinate =
				dblRightPredictorOrdinate) || !org.drip.quant.common.NumberUtil.IsValid (_dblTension =
					dblTension))
			throw new java.lang.Exception ("TensionBasisHat ctr: Invalid Inputs");
	}

	/**
	 * Identifies if the ordinate is local to the range
	 * 
	 * @param dblPredictorOrdinate The Predictor Ordinate
	 * 
	 * @return TRUE - The Ordinate is local to the Specified Range
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public boolean in (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate))
			throw new java.lang.Exception ("TensionBasisHat::in => Invalid Input");

		return dblPredictorOrdinate >= _dblLeftPredictorOrdinate && dblPredictorOrdinate <=
			_dblRightPredictorOrdinate;
	}

	/**
	 * Retrieve the Left Predictor Ordinate
	 * 
	 * @return The Left Predictor Ordinate
	 */

	public double left()
	{
		return _dblLeftPredictorOrdinate;
	}

	/**
	 * Retrieve the Right Predictor Ordinate
	 * 
	 * @return The Right Predictor Ordinate
	 */

	public double right()
	{
		return _dblRightPredictorOrdinate;
	}

	/**
	 * Retrieve the Tension
	 * 
	 * @return The Tension
	 */

	public double tension()
	{
		return _dblTension;
	}

	/**
	 * Compute the Normalizer
	 * 
	 * @return The Normalizer
	 * 
	 * @throws java.lang.Exception Thrown if the Normalizer cannot be computed
	 */

	public abstract double normalizer()
		throws java.lang.Exception;
}
