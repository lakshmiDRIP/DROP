
package org.drip.param.quoting;

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
 * QuotedSpreadInterpreter holds the fields needed to interpret a Quoted Spread Quote. It contains the
 * 	contract type and the coupon.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class QuotedSpreadInterpreter extends org.drip.param.quoting.MeasureInterpreter {

	/**
	 * SNAC CDS Contract
	 */

	public static final java.lang.String SNAC_CDS = "SNAC";

	/**
	 * Conventional CDS Contract
	 */

	public static final java.lang.String CONV_CDS = "CONV";

	/**
	 * STEM CDS Contract
	 */

	public static final java.lang.String STEM_CDS = "CONV";

	private java.lang.String _strCDSContractType = "";
	private double _dblCouponStrike = java.lang.Double.NaN;

	/**
	 * QuotedSpreadInterpreter constructor
	 * 
	 * @param strCDSContractType The CDS Contract Type
	 * @param dblCouponStrike The Coupon Strike
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public QuotedSpreadInterpreter (
		final java.lang.String strCDSContractType,
		final double dblCouponStrike)
		throws java.lang.Exception
	{
		if (null == (_strCDSContractType = strCDSContractType) || (!CONV_CDS.equalsIgnoreCase
			(_strCDSContractType) && !SNAC_CDS.equalsIgnoreCase (_strCDSContractType) &&
				!STEM_CDS.equalsIgnoreCase (_strCDSContractType)))
			throw new java.lang.Exception ("QuotedSpreadInterpreter ctr: Invalid Inputs");

		_dblCouponStrike = dblCouponStrike;
	}

	/**
	 * Retrieve the CDS Contract Type
	 * 
	 * @return The CDS Contract Type
	 */

	public java.lang.String cdsContractType()
	{
		return _strCDSContractType;
	}

	/**
	 * Retrieve the Coupon Strike
	 * 
	 * @return The Coupon Strike
	 */

	public double couponStrike()
	{
		return _dblCouponStrike;
	}
}
