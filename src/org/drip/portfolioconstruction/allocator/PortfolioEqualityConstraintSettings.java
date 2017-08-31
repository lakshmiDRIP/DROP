
package org.drip.portfolioconstruction.allocator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * PortfolioEqualityConstraintSettings holds the Parameters required to generate the Mandatory Constraints
 * 	for the Portfolio.
 *
 * @author Lakshmi Krishnamurthy
 */

public class PortfolioEqualityConstraintSettings {

	/**
	 * NO_CONSTRAINT - No Constraint of any Kind
	 */

	public static final int NO_CONSTRAINT = 1;

	/**
	 * FULLY_INVESTED_CONSTRAINT - The Mandatory Completely Invested Constraint
	 */

	public static final int FULLY_INVESTED_CONSTRAINT = 2;

	/**
	 * RETURNS_CONSTRAINT - The Mandatory Returns Constraint
	 */

	public static final int RETURNS_CONSTRAINT = 4;

	private int _iConstraintType = -1;
	private double _dblReturnsConstraint = java.lang.Double.NaN;

	/**
	 * Construct an Unconstrained Instance of PortfolioEqualityConstraintSettings
	 * 
	 * @return Unconstrained PortfolioEqualityConstraintSettings Instance
	 */

	public static final PortfolioEqualityConstraintSettings Unconstrained()
	{
		try {
			return new PortfolioEqualityConstraintSettings (NO_CONSTRAINT, java.lang.Double.NaN);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Fully Invested Instance of PortfolioEqualityConstraintSettings
	 * 
	 * @return Fully Invested PortfolioEqualityConstraintSettings Instance
	 */

	public static final PortfolioEqualityConstraintSettings FullyInvested()
	{
		try {
			return new PortfolioEqualityConstraintSettings (FULLY_INVESTED_CONSTRAINT, java.lang.Double.NaN);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Returns Constrained Instance of PortfolioEqualityConstraintSettings
	 * 
	 * @param dblReturnsConstraint The Returns Constraint
	 * 
	 * @return Returns Constrained PortfolioEqualityConstraintSettings Instance
	 */

	public static final PortfolioEqualityConstraintSettings ReturnsConstrained (
		final double dblReturnsConstraint)
	{
		try {
			return new PortfolioEqualityConstraintSettings (FULLY_INVESTED_CONSTRAINT | RETURNS_CONSTRAINT,
				dblReturnsConstraint);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * PortfolioEqualityConstraintSettings Constructor
	 * 
	 * @param iConstraintType The Constraint Type
	 * @param dblReturnsConstraint The Returns Constraint
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PortfolioEqualityConstraintSettings (
		final int iConstraintType,
		final double dblReturnsConstraint)
		throws java.lang.Exception
	{
		_iConstraintType = iConstraintType;
		_dblReturnsConstraint = dblReturnsConstraint;

		if (0 == (NO_CONSTRAINT & _iConstraintType) && 0 == (FULLY_INVESTED_CONSTRAINT & _iConstraintType) &&
			0 == (RETURNS_CONSTRAINT & _iConstraintType))
			throw new java.lang.Exception
				("PortfolioEqualityConstraintSettings Constructor => Invalid Inputs!");

		if (0 != (RETURNS_CONSTRAINT & _iConstraintType) && !org.drip.quant.common.NumberUtil.IsValid
			(_dblReturnsConstraint = dblReturnsConstraint))
			throw new java.lang.Exception
				("PortfolioEqualityConstraintSettings Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Constraint Type
	 * 
	 * @return The Constraint Type
	 */

	public int constraintType()
	{
		return _iConstraintType;
	}

	/**
	 * Retrieve the Returns Constraint
	 * 
	 * @return The Returns Constraint
	 */

	public double returnsConstraint()
	{
		return _dblReturnsConstraint;
	}
}
