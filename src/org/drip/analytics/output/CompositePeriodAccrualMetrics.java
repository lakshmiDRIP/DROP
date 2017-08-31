
package org.drip.analytics.output;

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
 * CompositePeriodAccrualMetrics holds the results of the compounded Composed period Accrual Metrics Estimate
 *  Output.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CompositePeriodAccrualMetrics extends org.drip.analytics.output.CompositePeriodCouponMetrics {
	private int _iResetDate = java.lang.Integer.MIN_VALUE;

	/**
	 * CompositePeriodAccrualMetrics Instance from the list of the composite period metrics
	 * 
	 * @param iResetDate Reset Date
	 * @param lsUPM List of Unit Period Metrics
	 * 
	 * @return Instance of CompositePeriodAccrualMetrics
	 */

	public static final CompositePeriodAccrualMetrics Create (
		final int iResetDate,
		final java.util.List<org.drip.analytics.output.UnitPeriodMetrics> lsUPM)
	{
		try {
			CompositePeriodAccrualMetrics cpam = new CompositePeriodAccrualMetrics (iResetDate, lsUPM);

			return cpam.initialize() ? cpam : null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected CompositePeriodAccrualMetrics (
		final int iResetDate,
		final java.util.List<org.drip.analytics.output.UnitPeriodMetrics> lsUPM)
		throws java.lang.Exception
	{
		super (lsUPM);

		_iResetDate = iResetDate;
	}

	/**
	 * Retrieve the Reset Date
	 * 
	 * @return The Reset Date
	 */

	public int resetDate()
	{
		return _iResetDate;
	}
}
