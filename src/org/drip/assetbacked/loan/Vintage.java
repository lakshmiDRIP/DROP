
package org.drip.assetbacked.loan;

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
 * Vintage contains the Loan Origination Vintage Details - i.e., the Year/Month of Loan Origination
 *
 * @author Lakshmi Krishnamurthy
 */

public class Vintage {
	private int _iOriginationYear = -1;
	private int _iOriginationMonth = -1;

	/**
	 * Construct a Vintage Instance from the Origination Date
	 * 
	 * @param dtOrigination The Origination Date
	 * 
	 * @return Vintage Instance
	 */

	public static final Vintage Standard (
		final org.drip.analytics.date.JulianDate dtOrigination)
	{
		if (null == dtOrigination) return null;

		try {
			int iDate = dtOrigination.julian();

			return new Vintage (org.drip.analytics.date.DateUtil.Year (iDate),
				org.drip.analytics.date.DateUtil.Month (iDate));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Vintage Constructor
	 * 
	 * @param iOriginationYear Loan Origination Year
	 * @param iOriginationMonth Loan Origination Month
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are not Valid
	 */

	public Vintage (
		final int iOriginationYear,
		final int iOriginationMonth)
		throws java.lang.Exception
	{
		if (0 > (_iOriginationYear = iOriginationYear) || 0 > (_iOriginationMonth = iOriginationMonth))
			throw new java.lang.Exception ("Vintage Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Origination Year
	 * 
	 * @return The Origination Year
	 */

	public int originationYear()
	{
		return _iOriginationYear;
	}

	/**
	 * Retrieve the Origination Month
	 * 
	 * @return The Origination Month
	 */

	public int originationMonth()
	{
		return _iOriginationMonth;
	}
}
