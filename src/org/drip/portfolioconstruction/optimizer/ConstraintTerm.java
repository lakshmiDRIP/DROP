
package org.drip.portfolioconstruction.optimizer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * ConstraintTerm holds the Details of a given Constraint Term.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ConstraintTerm extends org.drip.portfolioconstruction.core.Block {
	private double _dblMaximum = java.lang.Double.NaN;
	private double _dblMinimum = java.lang.Double.NaN;
	private org.drip.portfolioconstruction.optimizer.Unit _unit = null;
	private org.drip.portfolioconstruction.optimizer.Scope _scope = null;
	private org.drip.portfolioconstruction.optimizer.SoftConstraint _sc = null;
	private org.drip.portfolioconstruction.composite.BlockClassification _classSelection = null;

	/**
	 * ConstraintTerm Constructor
	 * 
	 * @param strName The Constraint Name
	 * @param strID The Constraint ID
	 * @param strDescription The Constraint Description
	 * @param scope The Constraint Scope
	 * @param classSelection The Constraint Selection
	 * @param unit The Constraint Unit
	 * @param dblMinimum The Constraint Minimum
	 * @param dblMaximum The Constraint Maximum
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ConstraintTerm (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final org.drip.portfolioconstruction.optimizer.Scope scope,
		final org.drip.portfolioconstruction.composite.BlockClassification classSelection,
		final org.drip.portfolioconstruction.optimizer.Unit unit,
		final double dblMinimum,
		final double dblMaximum)
		throws java.lang.Exception
	{
		super (strName, strID, strDescription);

		if (null == (_scope = scope) || null == (_unit = unit) || (!org.drip.quant.common.NumberUtil.IsValid
			(_dblMinimum = dblMinimum) && !org.drip.quant.common.NumberUtil.IsValid (_dblMaximum =
				dblMaximum)))
			throw new java.lang.Exception ("ConstraintTerm Constructor => Invalid Inputs");

		_classSelection = classSelection;
	}

	/**
	 * Set the Soft Constraint
	 * 
	 * @param sc The Soft Constraint
	 * 
	 * @return TRUE => The Soft Constraint successfully set
	 */

	public boolean setSoftConstraint (
		final org.drip.portfolioconstruction.optimizer.SoftConstraint sc)
	{
		if (null == sc) return false;

		_sc = sc;
		return true;
	}

	/**
	 * Retrieve the Soft Constraint
	 * 
	 * @return The Soft Constraint
	 */

	public org.drip.portfolioconstruction.optimizer.SoftConstraint softContraint()
	{
		return _sc;
	}

	/**
	 * Retrieve the Constraint Scope
	 * 
	 * @return The Constraint Scope
	 */

	public org.drip.portfolioconstruction.optimizer.Scope scope()
	{
		return _scope;
	}

	/**
	 * Retrieve the Constraint Selection
	 * 
	 * @return The Constraint Selection
	 */

	public org.drip.portfolioconstruction.composite.BlockClassification selection()
	{
		return _classSelection;
	}

	/**
	 * Retrieve the Constraint Unit
	 * 
	 * @return The Constraint Unit
	 */

	public org.drip.portfolioconstruction.optimizer.Unit unit()
	{
		return _unit;
	}

	/**
	 * Retrieve the Constraint Minimum
	 * 
	 * @return The Constraint Minimum
	 */

	public double minimum()
	{
		return _dblMinimum;
	}

	/**
	 * Retrieve the Constraint Maximum
	 * 
	 * @return The Constraint Maximum
	 */

	public double maximum()
	{
		return _dblMaximum;
	}
}
