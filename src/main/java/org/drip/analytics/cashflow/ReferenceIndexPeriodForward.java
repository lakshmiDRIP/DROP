
package org.drip.analytics.cashflow;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 *  - DRIP Asset Allocation: Library for models for MPT framework, Black Litterman Strategy Incorporator,
 *  	Holdings Constraint, and Transaction Costs.
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
 * ReferenceIndexPeriodForward contains the Reference Index Forward Period Details. Currently it holds the
 * 	Start Date, the End Date, the Fixing Date, and the Reference Forward Label.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ReferenceIndexPeriodForward extends org.drip.analytics.cashflow.ReferenceIndexPeriod
{

	/**
	 * Standard Instance of ReferenceIndexPeriodForward
	 * 
	 * @param iStartDate Reference Period Start Date
	 * @param iEndDate Reference Period End Date
	 * @param forwardLabel Period Forward Label
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final ReferenceIndexPeriodForward Standard (
		final int iStartDate,
		final int iEndDate,
		final org.drip.state.identifier.ForwardLabel forwardLabel)
	{
		if (null == forwardLabel) return null;

		org.drip.analytics.daycount.DateAdjustParams dapFixing =
			forwardLabel.floaterIndex().spotLagDAPBackward();

		org.drip.param.period.UnitCouponAccrualSetting ucas = forwardLabel.ucas();

		try {
			return new ReferenceIndexPeriodForward (
				iStartDate,
				iEndDate,
				null == dapFixing ? iStartDate : dapFixing.roll (iStartDate),
				ucas.couponDCFOffOfFreq() ? 1. / ucas.freq() :
					org.drip.analytics.daycount.Convention.YearFraction (
						iStartDate,
						iEndDate,
						ucas.couponDC(),
						ucas.couponEOMAdjustment(),
						null,
						ucas.calendar()
					),
				forwardLabel
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * The ReferenceIndexPeriodForward Constructor
	 * 
	 * @param iStartDate Reference Period Start Date
	 * @param iEndDate Reference Period End Date
	 * @param iFixingDate Reference Period Fixing Date
	 * @param dblDCF Reference Period Day Count Fraction
	 * @param forwardLabel Period Forward Label
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ReferenceIndexPeriodForward (
		final int iStartDate,
		final int iEndDate,
		final int iFixingDate,
		final double dblDCF,
		final org.drip.state.identifier.ForwardLabel forwardLabel)
		throws java.lang.Exception
	{
		super (iStartDate, iEndDate, iFixingDate, dblDCF, forwardLabel);
	}

	/**
	 * Retrieve the Forward Label
	 * 
	 * @return The Forward Label
	 */

	public org.drip.state.identifier.ForwardLabel forwardLabel()
	{
		return (org.drip.state.identifier.ForwardLabel) floaterLabel();
	}
}
