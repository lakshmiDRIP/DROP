
package org.drip.analytics.daycount;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * This interface is the stub for all the day count convention functionality. It exposes the base/alternate
 *  day count convention names, the year-fraction and the days accrued.
 *
 * @author Lakshmi Krishnamurthy
 */

public interface DCFCalculator {

	/**
	 * Retrieves the base calculation type corresponding to the DCF Calculator
	 * 
	 * @return Name of the base calculation type
	 */

	public abstract java.lang.String baseCalculationType();

	/**
	 * Retrieves the full set of alternate names corresponding to the DCF Calculator
	 * 
	 * @return Array of alternate names
	 */

	public abstract java.lang.String[] alternateNames();

	/**
	 * Calculates the accrual fraction in years between 2 given days
	 * 
	 * @param iStartDate Start Date
	 * @param iEndDate End Date
	 * @param bApplyEOMAdj Apply end-of-month adjustment (true)
	 * @param actactParams ActActParams
	 * @param strCalendar Holiday Calendar
	 * 
	 * @return Accrual Fraction in years
	 * 
	 * @throws java.lang.Exception Thrown if the accrual fraction cannot be calculated
	 */

	public abstract double yearFraction (
		final int iStartDate,
		final int iEndDate,
		final boolean bApplyEOMAdj,
		final ActActDCParams actactParams,
		final java.lang.String strCalendar)
		throws java.lang.Exception;

	/**
	 * Calculates the number of days accrued between the two given days
	 * 
	 * @param iStartDate Start Date
	 * @param iEndDate End Date
	 * @param bApplyEOMAdj Apply end-of-month adjustment (true)
	 * @param actactParams ActActParams
	 * @param strCalendar Holiday Calendar
	 * 
	 * @return Accrual Fraction in years
	 * 
	 * @throws java.lang.Exception Thrown if the accrual fraction cannot be calculated
	 */

	public abstract int daysAccrued (
		final int iStartDate,
		final int iEndDate,
		final boolean bApplyEOMAdj,
		final ActActDCParams actactParams,
		final java.lang.String strCalendar)
		throws java.lang.Exception;
}
