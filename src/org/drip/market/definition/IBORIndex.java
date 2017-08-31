
package org.drip.market.definition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * IBORIndex contains the definitions of the IBOR indexes of different jurisdictions.
 *
 * @author Lakshmi Krishnamurthy
 */

public class IBORIndex extends org.drip.market.definition.FloaterIndex {
	private int _iSpotLag = 0;
	private java.lang.String _strLongestMaturity = "";
	private java.lang.String _strShortestMaturity = "";

	/**
	 * IBORIndex Constructor
	 * 
	 * @param strName Index Name
	 * @param strFamily Index Family
	 * @param strCurrency Index Currency
	 * @param strDayCount Index Day Count
	 * @param strCalendar Index Holiday Calendar
	 * @param iSpotLag The Spot Lag
	 * @param strShortestMaturity Index Shortest Maturity
	 * @param strLongestMaturity Index Longest Maturity
	 * @param iAccrualCompoundingRule Accrual Compounding Rule
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public IBORIndex (
		final java.lang.String strName,
		final java.lang.String strFamily,
		final java.lang.String strCurrency,
		final java.lang.String strDayCount,
		final java.lang.String strCalendar,
		final int iSpotLag,
		final java.lang.String strShortestMaturity,
		final java.lang.String strLongestMaturity,
		final int iAccrualCompoundingRule)
		throws java.lang.Exception
	{
		super (strName, strFamily, strCurrency, strDayCount, strCalendar, iAccrualCompoundingRule);

		if (0 > (_iSpotLag = iSpotLag)) throw new java.lang.Exception ("IBORIndex ctr: Invalid Inputs");

		_strLongestMaturity = strLongestMaturity;
		_strShortestMaturity = strShortestMaturity;
	}

	@Override public int spotLag()
	{
		return _iSpotLag;
	}

	/**
	 * Retrieve the Index Shortest Maturity
	 * 
	 * @return The Index Shortest Maturity
	 */

	public java.lang.String shortestMaturity()
	{
		return _strShortestMaturity;
	}

	/**
	 * Retrieve the Longest Maturity
	 * 
	 * @return The Index Longest Maturity
	 */

	public java.lang.String longestMaturity()
	{
		return _strLongestMaturity;
	}
}
