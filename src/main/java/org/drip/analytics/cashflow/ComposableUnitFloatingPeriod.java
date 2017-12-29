
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
 * ComposableUnitFloatingPeriod contains the Cash Flow Periods' Composable Period Details. Currently it holds
 *  the Accrual Start Date, the Accrual End Date, the Fixing Date, the Spread over the Index, and the
 * 	corresponding Reference Index Period.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ComposableUnitFloatingPeriod extends org.drip.analytics.cashflow.ComposableUnitPeriod {
	private double _dblSpread = java.lang.Double.NaN;
	private org.drip.analytics.cashflow.ReferenceIndexPeriod _rip = null;

	private org.drip.analytics.date.JulianDate lookBackProjectionDate (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.market.definition.OvernightIndex oi)
	{
		int iSkipBackDay = 0;
		org.drip.analytics.date.JulianDate dtFixing = null;

		org.drip.state.identifier.ForwardLabel forwardLabel =
			((org.drip.analytics.cashflow.ReferenceIndexPeriodForward) _rip).forwardLabel();

		org.drip.market.definition.FloaterIndex floaterIndex = forwardLabel.floaterIndex();

		int iLookBackProjectionWindow = oi.publicationLag();

		try {
			dtFixing = new org.drip.analytics.date.JulianDate (_rip.fixingDate());

			while (iSkipBackDay <= iLookBackProjectionWindow) {
				if (
					csqc.available (
						dtFixing,
						forwardLabel
					)
				)
				return dtFixing;

				if (null == (dtFixing = dtFixing.subtractBusDays (
					1,
					floaterIndex.calendar()
				)))
					return null;

				iSkipBackDay += 1;
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private double baseForwardRate (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		org.drip.state.identifier.ForwardLabel forwardLabel =
			((org.drip.analytics.cashflow.ReferenceIndexPeriodForward) _rip).forwardLabel();

		org.drip.market.definition.FloaterIndex floaterIndex = forwardLabel.floaterIndex();

		if (!(floaterIndex instanceof org.drip.market.definition.OvernightIndex)) {
			int iFixingDate = _rip.fixingDate();

			if (
				csqc.available (
					iFixingDate,
					forwardLabel
				)
			)
				return csqc.fixing (
					iFixingDate,
					forwardLabel
				);
		} else {
			org.drip.analytics.date.JulianDate dtValidFixing = lookBackProjectionDate (
				csqc,
				(org.drip.market.definition.OvernightIndex) floaterIndex
			);

			if (null != dtValidFixing)
				return csqc.fixing (
					dtValidFixing,
					forwardLabel
				);
		}

		int iReferencePeriodEndDate = _rip.endDate();

		org.drip.state.forward.ForwardRateEstimator fre = csqc.forwardState (forwardLabel);

		if (null != fre) return fre.forward (iReferencePeriodEndDate);

		java.lang.String strForwardCurrency = forwardLabel.currency();

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqc.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (strForwardCurrency));

		if (null == dcFunding)
			throw new java.lang.Exception
				("ComposableUnitFloatingPeriod::baseForwardRate => Cannot locate Funding Curve " +
					strForwardCurrency);

		int iEpochDate = dcFunding.epoch().julian();

		int iReferencePeriodStartDate = _rip.startDate();

		if (iEpochDate > iReferencePeriodStartDate)
			iReferencePeriodEndDate = new org.drip.analytics.date.JulianDate (iReferencePeriodStartDate =
				iEpochDate).addTenor (forwardLabel.tenor()).julian();

		return dcFunding.libor (
			iReferencePeriodStartDate,
			iReferencePeriodEndDate,
			_rip.dcf()
		);
	}

	private double baseOTCFixFloatRate (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		org.drip.state.identifier.OTCFixFloatLabel otcFixFloatLabel =
			((org.drip.analytics.cashflow.ReferenceIndexPeriodOTCFixFloat) _rip).otcFixFloatLabel();

		int iFixingDate = _rip.fixingDate();

		if (csqc.available (
			iFixingDate,
			otcFixFloatLabel
		))
			return csqc.fixing (
				iFixingDate,
				otcFixFloatLabel
			);

		java.lang.String strForwardCurrency = otcFixFloatLabel.currency();

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqc.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (strForwardCurrency));

		if (null == dcFunding)
			throw new java.lang.Exception
				("ComposableUnitFloatingPeriod::baseOTCFixFloatRate => Cannot locate Funding Curve " +
					strForwardCurrency);

		return dcFunding.estimateManifestMeasure (
			"SwapRate",
			_rip.endDate()
		);
	}

	/**
	 * The ComposableUnitFloatingPeriod Constructor
	 * 
	 * @param iStartDate Accrual Start Date
	 * @param iEndDate Accrual End Date
	 * @param strTenor The Composable Period Tenor
	 * @param rip The Reference Index Period
	 * @param dblSpread The Floater Spread
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ComposableUnitFloatingPeriod (
		final int iStartDate,
		final int iEndDate,
		final java.lang.String strTenor,
		final org.drip.analytics.cashflow.ReferenceIndexPeriod rip,
		final double dblSpread)
		throws java.lang.Exception
	{
		super (
			iStartDate,
			iEndDate,
			strTenor,
			rip.floaterLabel().ucas()
		);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblSpread = dblSpread))
			throw new java.lang.Exception ("ComposableUnitFloatingPeriod Constructor => Invalid Inputs");

		_rip = rip;
	}

	/**
	 * Retrieve the Reference Rate for the Floating Period
	 * 
	 * @param csqc The Market Curve and Surface
	 * 
	 * @return The Reference Rate for the Floating Period
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	@Override public double baseRate (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		if (null == csqc) return java.lang.Double.NaN;

		if (_rip instanceof org.drip.analytics.cashflow.ReferenceIndexPeriodForward)
			return baseForwardRate (csqc);

		if (_rip instanceof org.drip.analytics.cashflow.ReferenceIndexPeriodOTCFixFloat)
			return baseOTCFixFloatRate (csqc);

		throw new java.lang.Exception
			("ComposableUnitFloatingPeriod::baseRate => Unknown Reference Period Index");
	}

	@Override public double basis()
	{
		return _dblSpread;
	}

	@Override public java.lang.String couponCurrency()
	{
		return _rip.floaterLabel().currency();
	}

	/**
	 * Retrieve the Reference Index Period
	 * 
	 * @return The Reference Index Period
	 */

	public org.drip.analytics.cashflow.ReferenceIndexPeriod referenceIndexPeriod()
	{
		return _rip;
	}
}
