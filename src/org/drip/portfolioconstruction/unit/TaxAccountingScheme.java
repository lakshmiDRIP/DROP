
package org.drip.portfolioconstruction.unit;

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
 * TaxAccountingScheme contains the Attributes for the specified Tax Accounting Scheme.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TaxAccountingScheme extends org.drip.portfolioconstruction.unit.Block {
	private int _iWashDays = -1;
	private int _iShortTermDays = -1;
	private double _dblLongTermTaxRate = java.lang.Double.NaN;
	private double _dblShortTermTaxRate = java.lang.Double.NaN;

	/**
	 * TaxAccountingScheme Constructor
	 * 
	 * @param strName The Name
	 * @param strID The ID
	 * @param strDescription The Description
	 * @param dblShortTermTaxRate Short Term Tax Rate
	 * @param dblLongTermTaxRate Long Term Tax Rate
	 * @param iShortTermDays Short Term Days
	 * @param iWashDays Wash Days
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TaxAccountingScheme (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final double dblShortTermTaxRate,
		final double dblLongTermTaxRate,
		final int iShortTermDays,
		final int iWashDays)
		throws java.lang.Exception
	{
		super (strName, strID, strDescription);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblShortTermTaxRate = dblShortTermTaxRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblLongTermTaxRate = dblLongTermTaxRate) || -1 >=
				(_iShortTermDays = iShortTermDays) || -1 >= (_iWashDays = iWashDays))
			throw new java.lang.Exception ("TaxAccountingScheme Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Short Term Tax Rate
	 * 
	 * @return The Short Term Tax Rate
	 */

	public double shortTermTaxRate()
	{
		return _dblShortTermTaxRate;
	}

	/**
	 * Retrieve the Long Term Tax Rate
	 * 
	 * @return The Long Term Tax Rate
	 */

	public double longTermTaxRate()
	{
		return _dblLongTermTaxRate;
	}

	/**
	 * Retrieve the Short Term Days
	 * 
	 * @return The Short Term Days
	 */

	public int shortTermDays()
	{
		return _iShortTermDays;
	}

	/**
	 * Retrieve the Wash Days
	 * 
	 * @return The Wash Days
	 */

	public int washDays()
	{
		return _iWashDays;
	}
}
