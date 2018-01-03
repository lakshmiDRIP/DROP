
package org.drip.state.csa;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * CashFlowEstimator estimates the Cash Flow Rate to be applied between the specified Dates.
 *
 * @author Lakshmi Krishnamurthy
 */

public interface CashFlowEstimator extends org.drip.state.discount.DiscountFactorEstimator
{

	/**
	 * Calculate the Cash Flow Rate Effective to the given Date
	 * 
	 * @param iDate Date
	 * 
	 * @return The Cash Flow Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Cash Flow Rate cannot be calculated
	 */

	public abstract double rate (
		final int iDate)
		throws java.lang.Exception;

	/**
	 * Calculate the Cash Flow Rate Effective to the given date
	 * 
	 * @param dt Date
	 * 
	 * @return The Cash Flow Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Cash Flow Rate cannot be Calculated
	 */

	public abstract double rate (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception;

	/**
	 * Calculate the Cash Flow Rate Effective to the given Tenor
	 * 
	 * @param strTenor Tenor
	 * 
	 * @return The Cash Flow Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Cash Flow Rate cannot be calculated
	 */

	public abstract double rate (
		final java.lang.String strTenor)
		throws java.lang.Exception;

	/**
	 * Calculate the Cash Flow Rate Effective between the Tenors
	 * 
	 * @param strTenor1 Tenor #1
	 * @param strTenor2 Tenor #2
	 * 
	 * @return The Cash Flow Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Cash Flow Rate cannot be calculated
	 */

	public abstract double rate (
		final java.lang.String strTenor1,
		final java.lang.String strTenor2)
		throws java.lang.Exception;

	/**
	 * Calculate the Cash Flow Rate Effective between the Dates
	 * 
	 * @param iDate1 Date #1
	 * @param iDate2 Date #2
	 * 
	 * @return The Cash Flow Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Cash Flow Rate cannot be calculated
	 */

	public abstract double rate (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception;

	/**
	 * Calculate the Cash Flow Rate Effective between the Dates
	 * 
	 * @param dt1 Date #1
	 * @param dt2 Date #2
	 * 
	 * @return The Cash Flow Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Cash Flow Rate cannot be calculated
	 */

	public abstract double rate (
		final org.drip.analytics.date.JulianDate dt1,
		final org.drip.analytics.date.JulianDate dt2)
		throws java.lang.Exception;
}
