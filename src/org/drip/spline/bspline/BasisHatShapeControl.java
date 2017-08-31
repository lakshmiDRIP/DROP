
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
 * BasisHatShapeControl implements the shape control function for the hat basis set as laid out in the
 *  framework outlined in Koch and Lyche (1989), Koch and Lyche (1993), and Kvasov (2000) Papers.
 *  
 *  Currently BasisHatShapeControl implements the following shape control customizers:
 *  - Cubic Polynomial with Rational Linear Shape Controller.
 *  - Cubic Polynomial with Rational Quadratic Shape Controller.
 *  - Cubic Polynomial with Rational Exponential Shape Controller.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class BasisHatShapeControl extends org.drip.spline.bspline.TensionBasisHat {

	/**
	 * Cubic Polynomial with Rational Linear Shape Controller
	 */

	public static final java.lang.String SHAPE_CONTROL_RATIONAL_LINEAR =
		"SHAPE_CONTROL_RATIONAL_LINEAR";

	/**
	 * Cubic Polynomial with Rational Quadratic Shape Controller
	 */

	public static final java.lang.String SHAPE_CONTROL_RATIONAL_QUADRATIC =
		"SHAPE_CONTROL_RATIONAL_QUADRATIC";

	/**
	 * Cubic Polynomial with Rational Exponential Shape Controller
	 */

	public static final java.lang.String SHAPE_CONTROL_RATIONAL_EXPONENTIAL =
		"SHAPE_CONTROL_RATIONAL_EXPONENTIAL";

	private java.lang.String _strShapeControlType = "";

	/**
	 * BasisHatShapeControl constructor
	 * 
	 * @param dblLeftPredictorOrdinate The Left Predictor Ordinate
	 * @param dblRightPredictorOrdinate The Right Predictor Ordinate
	 * @param strShapeControlType Type of the Shape Controller to be used - LINEAR/QUADRATIC/EXPONENTIAL
	 * 	Rational
	 * @param dblTension Tension of the Tension Hat Function
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public BasisHatShapeControl (
		final double dblLeftPredictorOrdinate,
		final double dblRightPredictorOrdinate,
		final java.lang.String strShapeControlType,
		final double dblTension)
		throws java.lang.Exception
	{
		super (dblLeftPredictorOrdinate, dblRightPredictorOrdinate, dblTension);

		if (null == (_strShapeControlType = strShapeControlType) ||
			(!SHAPE_CONTROL_RATIONAL_LINEAR.equalsIgnoreCase (_strShapeControlType) &&
				!SHAPE_CONTROL_RATIONAL_QUADRATIC.equalsIgnoreCase (_strShapeControlType) &&
					!SHAPE_CONTROL_RATIONAL_EXPONENTIAL.equalsIgnoreCase (_strShapeControlType)))
			throw new java.lang.Exception ("BasisHatShapeControl ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Type of the Shape Controller
	 * 
	 * @return The Type of the Shape Controller
	 */

	public java.lang.String shapeControlType()
	{
		return _strShapeControlType;
	}
}
