
package org.drip.analytics.cashflow;

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
 * ReferenceIndexPeriod contains the Cash Flow Period Details. Currently it holds the Start Date, the End
 *  Date, the Fixing Date, and the Reference Latent State Label.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ReferenceIndexPeriod
{
	private double _dblDCF = java.lang.Double.NaN;
	private int _iEndDate = java.lang.Integer.MIN_VALUE;
	private int _iStartDate = java.lang.Integer.MIN_VALUE;
	private int _iFixingDate = java.lang.Integer.MIN_VALUE;
	private org.drip.state.identifier.FloaterLabel _floaterLabel = null;

	/**
	 * Standard Instance of ReferenceIndexPeriod
	 * 
	 * @param iStartDate Reference Period Start Date
	 * @param iEndDate Reference Period End Date
	 * @param floaterLabel Period Forward Label
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final ReferenceIndexPeriod Standard (
		final int iStartDate,
		final int iEndDate,
		final org.drip.state.identifier.FloaterLabel floaterLabel)
	{
		if (null == floaterLabel) return null;

		org.drip.analytics.daycount.DateAdjustParams dapFixing =
			floaterLabel.floaterIndex().spotLagDAPBackward();

		org.drip.param.period.UnitCouponAccrualSetting ucas = floaterLabel.ucas();

		try {
			return new ReferenceIndexPeriod (
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
				floaterLabel
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * The ReferenceIndexPeriod Constructor
	 * 
	 * @param iStartDate Reference Period Start Date
	 * @param iEndDate Reference Period End Date
	 * @param iFixingDate Reference Period Fixing Date
	 * @param dblDCF Reference Period Day Count Fraction
	 * @param floaterLabel Period Floater Label
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ReferenceIndexPeriod (
		final int iStartDate,
		final int iEndDate,
		final int iFixingDate,
		final double dblDCF,
		final org.drip.state.identifier.FloaterLabel floaterLabel)
		throws java.lang.Exception
	{
		if ((_iEndDate = iEndDate) <= (_iStartDate = iStartDate) ||
			(_iFixingDate = iFixingDate) > _iStartDate ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblDCF = dblDCF) ||
			null == (_floaterLabel = floaterLabel))
			throw new java.lang.Exception ("ReferenceIndexPeriod ctr: Invalid Inputs");
	}

	/**
	 * Reference Period Start Date
	 * 
	 * @return The Reference Period Start Date
	 */

	public int startDate()
	{
		return _iStartDate;
	}

	/**
	 * Reference Period End Date
	 * 
	 * @return The Reference Period End Date
	 */

	public int endDate()
	{
		return _iEndDate;
	}

	/**
	 * Reference Period Fixing Date
	 * 
	 * @return The Reference Period Fixing Date
	 */

	public int fixingDate()
	{
		return _iFixingDate;
	}

	/**
	 * Retrieve the Floater Label
	 * 
	 * @return The Floater Label
	 */

	public org.drip.state.identifier.FloaterLabel floaterLabel()
	{
		return _floaterLabel;
	}

	/**
	 * Retrieve the Reference Period Day Count Fraction
	 * 
	 * @return The Reference Period Day Count Fraction
	 */

	public double dcf()
	{
		return _dblDCF;
	}
}
