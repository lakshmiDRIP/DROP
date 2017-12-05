
package org.drip.portfolioconstruction.optimizer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * Strategy holds the Details of a given Strategy.
 *
 * @author Lakshmi Krishnamurthy
 */

public class Strategy extends org.drip.portfolioconstruction.core.Block {
	private boolean _bAllowCrossOver = false;
	private boolean _bIgnoreCompliance = false;
	private boolean _bAllowGrandFathering = false;
	private org.drip.portfolioconstruction.optimizer.ObjectiveFunction _of = null;
	private org.drip.portfolioconstruction.optimizer.ConstraintHierarchy _ch = null;

	/**
	 * Strategy Constructor
	 * 
	 * @param strName The Constraint Name
	 * @param strID The Constraint ID
	 * @param strDescription The Constraint Description
	 * @param of The Objective Function
	 * @param ch Constraint Hierarchy
	 * @param bAllowGrandFathering TRUE - Grand-fathering of the "Previous" is to be performed
	 * @param bAllowCrossOver TRUE - Cross-Over is allowed
	 * @param bIgnoreCompliance TRUE - Ignore Compliance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Strategy (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final org.drip.portfolioconstruction.optimizer.ObjectiveFunction of,
		final org.drip.portfolioconstruction.optimizer.ConstraintHierarchy ch,
		final boolean bAllowGrandFathering,
		final boolean bAllowCrossOver,
		final boolean bIgnoreCompliance)
		throws java.lang.Exception
	{
		super (strName, strID, strDescription);

		_ch = ch;
		_bAllowCrossOver = bAllowCrossOver;
		_bIgnoreCompliance = bIgnoreCompliance;
		_bAllowGrandFathering = bAllowGrandFathering;

		if (null == (_of = of)) throw new java.lang.Exception ("Strategy Construtor => Invalid Inputs");
	}

	/**
	 * Indicate if Grand-fathering of the "Previous" is to be performed
	 * 
	 * @return TRUE - Grand-fathering of the "Previous" is to be performed
	 */

	public boolean allowGrandFathering()
	{
		return _bAllowGrandFathering;
	}

	/**
	 * Indicate if Cross Over is allowed
	 * 
	 * @return TRUE - Cross-Over is allowed
	 */

	public boolean allowCrossOver()
	{
		return _bAllowCrossOver;
	}

	/**
	 * Indicate if Compliance Checks are to be ignored
	 * 
	 * @return TRUE - Compliance Checks are to be ignored
	 */

	public boolean ignoreCompliance()
	{
		return _bIgnoreCompliance;
	}

	/**
	 * Retrieve the Objective Function
	 * 
	 * @return The Objective Function
	 */

	public org.drip.portfolioconstruction.optimizer.ObjectiveFunction objectiveFunction()
	{
		return _of;
	}

	/**
	 * Retrieve the Constraint Hierarchy
	 * 
	 * @return The Constraint Hierarchy
	 */

	public org.drip.portfolioconstruction.optimizer.ConstraintHierarchy constraintHierarchy()
	{
		return _ch;
	}

	/**
	 * Retrieve the Array of Constraint Attributes
	 * 
	 * @return The Array of Constraint Attributes
	 */

	public double[] constraintAttributes()
	{
		return null;
	}
}
