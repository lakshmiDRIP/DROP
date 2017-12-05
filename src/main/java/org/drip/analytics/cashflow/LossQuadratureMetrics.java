
package org.drip.analytics.cashflow;

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
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * LossPeriodCurveFactors is an Implementation of the Period Class enhanced by the Loss Period Measures. It
 * 	exports the following Functionality:
 * 
 * - Start/End Survival Probabilities, Period Effective Notional/Recovery/Discount Factor - Serialization
 * 	into and De-serialization out of Byte Arrays
 *
 * @author Lakshmi Krishnamurthy
 */

public class LossQuadratureMetrics {
	private int _iEndDate = java.lang.Integer.MIN_VALUE;
	private int _iStartDate = java.lang.Integer.MIN_VALUE;
	private double _dblAccrualDCF = java.lang.Double.NaN;
	private double _dblEffectiveDF = java.lang.Double.NaN;
	private double _dblEndSurvival = java.lang.Double.NaN;
	private double _dblStartSurvival = java.lang.Double.NaN;
	private double _dblEffectiveNotional = java.lang.Double.NaN;
	private double _dblEffectiveRecovery = java.lang.Double.NaN;

	/**
	 * Create an Instance of the LossPeriodCurveFactors using the Period's Dates and Curves to generate the
	 *  Curve Measures
	 * 
	 * @param iStartDate Period Start Date
	 * @param iEndDate Period End Date
	 * @param dblAccrualDCF Period's Accrual Day Count Fraction
	 * @param dblEffectiveNotional Period's Effective Notional
	 * @param dblEffectiveRecovery Period's Effective Recovery
	 * @param dc Discount Curve
	 * @param cc Credit Curve
	 * @param iDefaultLag Default Pay Lag
	 * 
	 * @return LossPeriodCurveFactors Instance
	 */

	public static final LossQuadratureMetrics MakeDefaultPeriod (
		final int iStartDate,
		final int iEndDate,
		final double dblAccrualDCF,
		final double dblEffectiveNotional,
		final double dblEffectiveRecovery,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.credit.CreditCurve cc,
		final int iDefaultLag)
	{
		if (
			!org.drip.quant.common.NumberUtil.IsValid (dblAccrualDCF) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblEffectiveNotional) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblEffectiveRecovery) ||
			null == dc ||
			null == cc
		)
			return null;

		try {
			return new LossQuadratureMetrics (
				iStartDate,
				iEndDate,
				cc.survival (iStartDate),
				cc.survival (iEndDate),
				dblAccrualDCF,
				dblEffectiveNotional,
				dblEffectiveRecovery,
				dc.effectiveDF (
					iStartDate + iDefaultLag,
					iEndDate + iDefaultLag
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a LossPeriodCurveFactors Instance from the Period Dates and the Curve Measures
	 * 
	 * @param iStartDate Period Start Date
	 * @param iEndDate Period End Date
	 * @param dblAccrualDCF Period's Accrual Day Count Fraction
	 * @param dblEffectiveNotional Period's Effective Notional
	 * @param dc Discount Curve
	 * @param cc Credit Curve
	 * @param iDefaultLag Default Pay Lag
	 * 
	 * @return LossPeriodCurveFactors instance
	 */

	public static final LossQuadratureMetrics MakeDefaultPeriod (
		final int iStartDate,
		final int iEndDate,
		final double dblAccrualDCF,
		final double dblEffectiveNotional,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.state.credit.CreditCurve cc,
		final int iDefaultLag)
	{
		if (
			!org.drip.quant.common.NumberUtil.IsValid (dblAccrualDCF) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblEffectiveNotional) ||
			null == dc ||
			null == cc
		)
			return null;

		try {
			return new LossQuadratureMetrics (
				iStartDate,
				iEndDate,
				cc.survival (iStartDate),
				cc.survival (iEndDate),
				dblAccrualDCF,
				dblEffectiveNotional,
				cc.effectiveRecovery (
					iStartDate + iDefaultLag,
					iEndDate + iDefaultLag
				),
				dc.effectiveDF (
					iStartDate + iDefaultLag,
					iEndDate + iDefaultLag
				)
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * LossPeriodCurveFactors Constructor
	 * 
	 * @param iStartDate Period Start Date
	 * @param iEndDate Period End Date
	 * @param dblStartSurvival Period Start Survival
	 * @param dblEndSurvival Period End Survival
	 * @param dblAccrualDCF Period Accrual DCF
	 * @param dblEffectiveNotional Period Effective Notional
	 * @param dblEffectiveRecovery Period Effective Recovery
	 * @param dblEffectiveDF Period Effective Discount Factor
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public LossQuadratureMetrics (
		final int iStartDate,
		final int iEndDate,
		final double dblStartSurvival,
		final double dblEndSurvival,
		final double dblAccrualDCF,
		final double dblEffectiveNotional,
		final double dblEffectiveRecovery,
		final double dblEffectiveDF)
		throws java.lang.Exception
	{
		if (
			!org.drip.quant.common.NumberUtil.IsValid (_dblStartSurvival = dblStartSurvival) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblEndSurvival = dblEndSurvival) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblAccrualDCF = dblAccrualDCF) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblEffectiveNotional = dblEffectiveNotional) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblEffectiveRecovery = dblEffectiveRecovery) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblEffectiveDF = dblEffectiveDF)
		)
			throw new java.lang.Exception ("LossPeriodCurveFactors Constructor => Invalid params");

		_iEndDate = iEndDate;
		_iStartDate = iStartDate;
	}

	/**
	 * Period Start Date
	 * 
	 * @return Period Start Date
	 */

	public int startDate()
	{
		return _iStartDate;
	}

	/**
	 * Survival Probability at the Period Beginning
	 * 
	 * @return Survival Probability at the Period Beginning
	 */

	public double startSurvival()
	{
		return _dblStartSurvival;
	}

	/**
	 * Period End Date
	 * 
	 * @return Period End Date
	 */

	public int endDate()
	{
		return _iEndDate;
	}

	/**
	 * Survival at the Period End
	 * 
	 * @return Survival at the Period End
	 */

	public double endSurvival()
	{
		return _dblEndSurvival;
	}

	/**
	 * Get the Period Effective Notional
	 * 
	 * @return Period Effective Notional
	 */

	public double effectiveNotional()
	{
		return _dblEffectiveNotional;
	}

	/**
	 * Get the Period Effective Recovery
	 * 
	 * @return Period Effective Recovery
	 */

	public double effectiveRecovery()
	{
		return _dblEffectiveRecovery;
	}

	/**
	 * Get the Period Effective Discount Factor
	 * 
	 * @return Period Effective Discount Factor
	 */

	public double effectiveDF()
	{
		return _dblEffectiveDF;
	}

	/**
	 * Get the Period Accrual Day Count Fraction
	 * 
	 * @return Period Accrual Day Count Fraction
	 */

	public double accrualDCF()
	{
		return _dblAccrualDCF;
	}
}
