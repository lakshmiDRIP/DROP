
package org.drip.spline.params;

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
 * SegmentBasisFlexureConstraint holds the set of fields needed to characterize a single local linear
 * 	Constraint, expressed linearly as a combination of the local Predictor Ordinates and their corresponding
 *  Response Basis Function Realizations. Constraints are expressed as
 * 
 * 			C := Sigma_(i,j) [W_i * B_i(x_j)] = V where
 * 
 * 	x_j - The Predictor Ordinate at Node j
 * 	B_i - The Coefficient for the Response Basis Function i
 * 	W_i - Weight applied for the Response Basis Function i
 * 	V - Value of the Constraint
 * 
 * SegmentBasisFlexureConstraint can be viewed as the localized basis function transpose of
 *  SegmentResponseValueConstraint.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SegmentBasisFlexureConstraint {
	private double[] _adblResponseBasisCoeffWeight = null;
	private double _dblConstraintValue = java.lang.Double.NaN;

	/**
	 * SegmentBasisFlexureConstraint constructor
	 * 
	 * @param adblResponseBasisCoeffWeight The Weight for each of the Coefficients in the Basis Function Set
	 * @param dblConstraintValue The Constraint Value
	 *
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public SegmentBasisFlexureConstraint (
		double[] adblResponseBasisCoeffWeight,
		double dblConstraintValue)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblConstraintValue = dblConstraintValue) || null ==
			(_adblResponseBasisCoeffWeight = adblResponseBasisCoeffWeight) || 0 ==
				_adblResponseBasisCoeffWeight.length)
			throw new java.lang.Exception ("SegmentBasisFlexureConstraint ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Array of the Response Basis Coefficient Weights
	 * 
	 * @return The Array of the Response Basis Coefficient Weights
	 */

	public double[] responseBasisCoeffWeights()
	{
		return _adblResponseBasisCoeffWeight;
	}

	/**
	 * Retrieve the Constraint Value
	 * 
	 * @return The Constraint Value
	 */

	public double contraintValue()
	{
		return _dblConstraintValue;
	}
}
