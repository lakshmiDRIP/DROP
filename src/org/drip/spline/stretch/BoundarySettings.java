
package org.drip.spline.stretch;

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
 * This class implements the Boundary Settings that determine the full extent of description of the regime's
 * 	State. It exports functions that:
 * 	- Specify the type of the boundary condition (NATURAL/FLOATING/IS-A-KNOT)
 * 	- Boundary COndition specific additional parameters (e.g., Derivative Orders and Matches)
 * 	- Static methods that help construct standard boundary settings
 *
 * @author Lakshmi Krishnamurthy
 */

public class BoundarySettings {

	/**
	 * Calibration Boundary Condition: Floating Boundary Condition
	 */

	public static final int BOUNDARY_CONDITION_FLOATING = 1;

	/**
	 * Calibration Boundary Condition: Natural Boundary Condition
	 */

	public static final int BOUNDARY_CONDITION_NATURAL = 2;

	/**
	 * Calibration Boundary Condition: Financial Boundary Condition
	 */

	public static final int BOUNDARY_CONDITION_FINANCIAL = 4;

	/**
	 * Calibration Boundary Condition: Not-A-Knot Boundary Condition
	 */

	public static final int BOUNDARY_CONDITION_NOT_A_KNOT = 8;

	private int _iLeftDerivOrder = -1;
	private int _iRightDerivOrder = -1;
	private int _iBoundaryConditionType = -1;

	/**
	 * Return the Instance of the Standard Natural Boundary Condition
	 * 
	 * @return Instance of the Standard Natural Boundary Condition
	 */

	public static final BoundarySettings NaturalStandard()
	{
		try {
			return new BoundarySettings (-1, 2, BOUNDARY_CONDITION_NATURAL);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Return the Instance of the Standard Floating Boundary Condition
	 * 
	 * @return Instance of the Standard Floating Boundary Condition
	 */

	public static final BoundarySettings FloatingStandard()
	{
		try {
			return new BoundarySettings (-1, -1, BOUNDARY_CONDITION_FLOATING);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Return the Instance of the Standard Financial Boundary Condition
	 * 
	 * @return Instance of the Standard Financial Boundary Condition
	 */

	public static final BoundarySettings FinancialStandard()
	{
		try {
			return new BoundarySettings (-1, 1, BOUNDARY_CONDITION_FINANCIAL);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Return the Instance of the Standard Not-A-Knot Boundary Condition
	 * 
	 * @param iLeftDerivOrder Order of the Left Derivative
	 * @param iRightDerivOrder Order of the Right Derivative
	 * 
	 * @return Instance of the Standard Not-A-Knot Boundary Condition
	 */

	public static final BoundarySettings NotAKnotStandard (
		final int iLeftDerivOrder,
		final int iRightDerivOrder)
	{
		try {
			return new BoundarySettings (iLeftDerivOrder, iRightDerivOrder, BOUNDARY_CONDITION_NOT_A_KNOT);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * BoundarySettings constructor
	 * 
	 * @param iLeftDerivOrder Order of the Left Derivative
	 * @param iRightDerivOrder Order of the Right Derivative
	 * @param iBoundaryConditionType Type of the Boundary Condition - NATURAL/FINANCIAL/NOT_A_KNOT
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public BoundarySettings (
		final int iLeftDerivOrder,
		final int iRightDerivOrder,
		final int iBoundaryConditionType)
		throws java.lang.Exception
	{
		if (BOUNDARY_CONDITION_FLOATING != (_iBoundaryConditionType = iBoundaryConditionType) &&
			BOUNDARY_CONDITION_NATURAL != _iBoundaryConditionType && BOUNDARY_CONDITION_FINANCIAL !=
				_iBoundaryConditionType && BOUNDARY_CONDITION_NOT_A_KNOT != _iBoundaryConditionType)
			throw new java.lang.Exception ("BoundarySettings ct: Invalid Inputs");

		_iLeftDerivOrder = iLeftDerivOrder;
		_iRightDerivOrder = iRightDerivOrder;
	}

	/**
	 * Retrieve the Order of the Left Derivative
	 * 
	 * @return The Order of the Left Derivative
	 */

	public int leftDerivOrder()
	{
		return _iLeftDerivOrder;
	}

	/**
	 * Retrieve the Order of the Right Derivative
	 * 
	 * @return The Order of the Right Derivative
	 */

	public int rightDerivOrder()
	{
		return _iRightDerivOrder;
	}

	/**
	 * Retrieve the Type of the Boundary Condition
	 * 
	 * @return The Type of the Boundary Condition
	 */

	public int boundaryCondition()
	{
		return _iBoundaryConditionType;
	}
}
