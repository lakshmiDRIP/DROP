
package org.drip.product.params;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * CTDEntry implements the Bond Futures CTD Entry Details.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CTDEntry {
	private org.drip.product.definition.Bond _bond = null;
	private double _dblForwardPrice = java.lang.Double.NaN;
	private double _dblConversionFactor = java.lang.Double.NaN;

	/**
	 * CTDEntry Constructor
	 * 
	 * @param bond The Futures CTD Bond
	 * @param dblConversionFactor The CTD Conversion Factor
	 * @param dblForwardPrice The CTD Forward Price
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CTDEntry (
		final org.drip.product.definition.Bond bond,
		final double dblConversionFactor,
		final double dblForwardPrice)
		throws java.lang.Exception
	{
		if (null == (_bond = bond) || !org.drip.quant.common.NumberUtil.IsValid (_dblConversionFactor =
			dblConversionFactor) || !org.drip.quant.common.NumberUtil.IsValid (_dblForwardPrice =
				dblForwardPrice))
			throw new java.lang.Exception ("CTDEntry Constructor => Invalid Inputs");
	}

	/**
	 * 
	 * Retrieve the CTD Bond Instance
	 * 
	 * @return The CTD Bond Instance
	 */

	public org.drip.product.definition.Bond bond()
	{
		return _bond;
	}

	/**
	 * Retrieve the CTD Conversion Factor
	 * 
	 * @return The CTD Conversion Factor
	 */

	public double conversionFactor()
	{
		return _dblConversionFactor;
	}

	/**
	 * Retrieve the CTD Forward Price
	 * 
	 * @return The CTD Forward Price
	 */

	public double forwardPrice()
	{
		return _dblForwardPrice;
	}
}
