
package org.drip.analytics.support;

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
 * LossQuadratureGenerator generates the decomposed Integrand Quadrature for the Loss Steps.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class LossQuadratureGenerator {

	/**
	 * Generate the Set of Loss Quadrature Metrics from the Day Step Loss Periods
	 * 
	 * @param comp Component for which the measures are to be generated
	 * @param valParams ValuationParams from which the periods are generated
	 * @param period The enveloping coupon period
	 * @param iWorkoutDate Date representing the absolute end of all the generated periods
	 * @param iPeriodUnit Day Step Size Unit of the generated Loss Quadrature Periods
	 * @param csqs The Market Parameters Curves/Quotes
	 *  
	 * @return List of the generated LossQuadratureMetrics
	 */

	public static final java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics>
		GenerateDayStepLossPeriods (
			final org.drip.product.definition.CreditComponent comp,
			final org.drip.param.valuation.ValuationParams valParams,
			final org.drip.analytics.cashflow.CompositePeriod period,
			final int iWorkoutDate,
			final int iPeriodUnit,
			final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
	{
		if (null == comp || null == valParams || null == period || null == csqs) return null;

		org.drip.state.discount.MergedDiscountForwardCurve dc = csqs.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (comp.payCurrency()));

		if (null == dc) return null;

		org.drip.state.credit.CreditCurve cc = csqs.creditState (comp.creditLabel());

		if (null == cc) return null;

		int iLossPayLag = comp.creditValuationParams().lossPayLag();

		int iSubPeriodStartDate = period.startDate();

		if (iSubPeriodStartDate > iWorkoutDate) return null;

		int iPeriodEndDate = period.endDate();

		int iValueDate = valParams.valueDate();

		boolean bPeriodDone = false;
		iPeriodEndDate = iPeriodEndDate < iWorkoutDate ? iPeriodEndDate : iWorkoutDate;
		iSubPeriodStartDate = iSubPeriodStartDate < iValueDate ? iValueDate : iSubPeriodStartDate;

		java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics> sLP = new
			java.util.ArrayList<org.drip.analytics.cashflow.LossQuadratureMetrics>();

		while (!bPeriodDone) {
			int iSubPeriodEndDate = iSubPeriodStartDate + iPeriodUnit;

			if (iSubPeriodEndDate < iValueDate) return null;

			if (iSubPeriodEndDate >= iPeriodEndDate) {
				bPeriodDone = true;
				iSubPeriodEndDate = iPeriodEndDate;
			}

			try {
				org.drip.analytics.cashflow.LossQuadratureMetrics lp =
					org.drip.analytics.cashflow.LossQuadratureMetrics.MakeDefaultPeriod (iSubPeriodStartDate,
						iSubPeriodEndDate, period.accrualDCF ((iSubPeriodStartDate + iSubPeriodEndDate) / 2),
							comp.notional (iSubPeriodStartDate, iSubPeriodEndDate), comp.recovery
								(iSubPeriodStartDate, iSubPeriodEndDate, cc), dc, cc, iLossPayLag);

				if (null != lp) sLP.add (lp);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			iSubPeriodStartDate = iSubPeriodEndDate;
		}

		return sLP;
	}

	/**
	 * Generate the Set of Loss Quadrature Metrics from the Day Step Loss Periods
	 * 
	 * @param comp Component for which the measures are to be generated
	 * @param valParams ValuationParams from which the periods are generated
	 * @param period The enveloping coupon period
	 * @param iWorkoutDate The absolute end of all the generated periods
	 * @param iPeriodUnit Loss Grid Size Unit of the generated Loss Quadrature Periods
	 * @param csqs The Market Parameters Curves/Quotes
	 *  
	 * @return List of the generated LossQuadratureMetrics
	 */

	public static final java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics>
		GeneratePeriodUnitLossPeriods (
			final org.drip.product.definition.CreditComponent comp,
			final org.drip.param.valuation.ValuationParams valParams,
			final org.drip.analytics.cashflow.CompositePeriod period,
			final int iWorkoutDate,
			final int iPeriodUnit,
			final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
	{
		if (null == comp || null == valParams || null == period || null == csqs) return null;

		org.drip.state.discount.MergedDiscountForwardCurve dc = csqs.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (comp.payCurrency()));

		if (null == dc) return null;

		org.drip.state.credit.CreditCurve cc = csqs.creditState (comp.creditLabel());

		if (null == cc) return null;

		int iValueDate = valParams.valueDate();

		int iPeriodEndDate = period.endDate();

		int iSubPeriodStartDate = period.startDate();

		boolean bPeriodDone = false;
		iPeriodEndDate = iPeriodEndDate < iWorkoutDate ? iPeriodEndDate : iWorkoutDate;
		iSubPeriodStartDate = iSubPeriodStartDate < iValueDate ? iValueDate : iSubPeriodStartDate;
		int iDayStep = (iPeriodEndDate - iSubPeriodStartDate) / iPeriodUnit;

		if (iSubPeriodStartDate > iWorkoutDate || iPeriodEndDate < iValueDate) return null;

		if (iDayStep < org.drip.param.pricer.CreditPricerParams.PERIOD_DAY_STEPS_MINIMUM)
			iDayStep = org.drip.param.pricer.CreditPricerParams.PERIOD_DAY_STEPS_MINIMUM;

		int iLossPayLag = comp.creditValuationParams().lossPayLag();

		java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics> sLP = new
			java.util.ArrayList<org.drip.analytics.cashflow.LossQuadratureMetrics>();

		while (!bPeriodDone) {
			int iSubPeriodEndDate = iSubPeriodStartDate + iDayStep;

			if (iSubPeriodEndDate < iValueDate) return null;

			try {
				if (iSubPeriodEndDate >= iPeriodEndDate) {
					bPeriodDone = true;
					iSubPeriodEndDate = iPeriodEndDate;
				}

				org.drip.analytics.cashflow.LossQuadratureMetrics lp =
					org.drip.analytics.cashflow.LossQuadratureMetrics.MakeDefaultPeriod (iSubPeriodStartDate,
						iSubPeriodEndDate, period.accrualDCF ((iSubPeriodStartDate + iSubPeriodEndDate) / 2),
							comp.notional (iSubPeriodStartDate, iSubPeriodEndDate), comp.recovery
								(iSubPeriodStartDate, iSubPeriodEndDate, cc),  dc, cc, iLossPayLag);

				if (null != lp) sLP.add (lp);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			iSubPeriodStartDate = iSubPeriodEndDate;
		}

		return sLP;
	}


	/**
	 * Generate the Set of Loss Quadrature Metrics from the Day Step Loss Periods
	 * 
	 * @param comp Component for which the measures are to be generated
	 * @param valParams ValuationParams from which the periods are generated
	 * @param period The Enveloping Coupon period
	 * @param iWorkoutDate The Absolute End of all the generated periods
	 * @param csqs The Market Parameters Curves/Quotes
	 *  
	 * @return List of the generated LossQuadratureMetrics
	 */

	public static final java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics>
		GenerateWholeLossPeriods (
			final org.drip.product.definition.CreditComponent comp,
			final org.drip.param.valuation.ValuationParams valParams,
			final org.drip.analytics.cashflow.CompositePeriod period,
			final int iWorkoutDate,
			final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
	{
		if (null == comp || null == valParams || null == period || null == csqs) return null;

		org.drip.state.discount.MergedDiscountForwardCurve dc = csqs.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (comp.payCurrency()));

		if (null == dc) return null;

		org.drip.state.credit.CreditCurve cc = csqs.creditState (comp.creditLabel());

		if (null == cc) return null;

		int iPeriodStartDate = period.startDate();

		if (iPeriodStartDate > iWorkoutDate) return null;

		int iPeriodEndDate = period.endDate();

		int iValueDate = valParams.valueDate();

		iPeriodStartDate = iPeriodStartDate < iValueDate ? iValueDate : iPeriodStartDate;
		iPeriodEndDate = iPeriodEndDate < iWorkoutDate ? iPeriodEndDate : iWorkoutDate;

		int iLossPayLag = comp.creditValuationParams().lossPayLag();

		java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics> sLP = new
			java.util.ArrayList<org.drip.analytics.cashflow.LossQuadratureMetrics>();

		try {
			org.drip.analytics.cashflow.LossQuadratureMetrics lp =
				org.drip.analytics.cashflow.LossQuadratureMetrics.MakeDefaultPeriod (iPeriodStartDate,
					iPeriodEndDate, period.accrualDCF ((iPeriodStartDate + iPeriodEndDate) / 2),
						comp.notional (iPeriodStartDate, iPeriodEndDate), comp.recovery (iPeriodStartDate,
							iPeriodEndDate, cc), dc, cc, iLossPayLag);

			if (null != lp) sLP.add (lp);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return sLP;
	}
}
