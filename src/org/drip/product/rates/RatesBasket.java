
package org.drip.product.rates;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * RatesBasket contains the implementation of the Basket of Rates Component legs. RatesBasket is made from
 * 	zero/more fixed and floating streams. It exports the following functionality:
 *  - Standard/Custom Constructor for the RatesBasket
 *  - Dates: Effective, Maturity, Coupon dates and Product settlement Parameters
 *  - Coupon/Notional Outstanding as well as schedules
 *  - Retrieve the constituent fixed and floating streams
 *  - Market Parameters: Discount, Forward, Credit, Treasury Curves
 *  - Cash Flow Periods: Coupon flows and (Optionally) Loss Flows
 *  - Valuation: Named Measure Generation
 *  - Calibration: The codes and constraints generation
 *  - Jacobians: Quote/DF and PV/DF micro-Jacobian generation
 *  - Serialization into and de-serialization out of byte arrays
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RatesBasket extends org.drip.product.definition.CalibratableComponent {
	private java.lang.String _strName = "";
	private org.drip.product.rates.Stream[] _aCompFixedStream = null;
	private org.drip.product.rates.Stream[] _aCompFloatStream = null;

	/**
	 * RatesBasket constructor
	 * 
	 * @param strName Basket Name
	 * @param aCompFixedStream Array of Fixed Stream Components
	 * @param aCompFloatStream Array of Float Stream Components
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public RatesBasket (
		final java.lang.String strName,
		final org.drip.product.rates.Stream[] aCompFixedStream,
		final org.drip.product.rates.Stream[] aCompFloatStream)
		throws java.lang.Exception
	{
		if (null == (_strName = strName) || _strName.isEmpty() || null == (_aCompFixedStream =
			aCompFixedStream) || 0 == _aCompFixedStream.length || null == (_aCompFloatStream =
				aCompFloatStream) || 0 == _aCompFloatStream.length)
			throw new java.lang.Exception ("RatesBasket ctr => Invalid Inputs");
	}

	@Override public java.lang.String name()
	{
		return _strName;
	}

	@Override public java.lang.String primaryCode()
	{
		return _strName;
	}


	/**
	 * Retrieve the array of the fixed stream components
	 * 
	 * @return The array of the fixed stream components
	 */

	public org.drip.product.rates.Stream[] getFixedStreamComponents()
	{
		return _aCompFixedStream;
	}

	/**
	 * Retrieve the array of the float stream components
	 * 
	 * @return The array of the float stream components
	 */

	public org.drip.product.rates.Stream[] getFloatStreamComponents()
	{
		return _aCompFloatStream;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> couponCurrency()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> mapCouponCurrency = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String>();

		if (null != _aCompFixedStream) {
			int iFixedStreamLength = _aCompFixedStream.length;

			if (0 != iFixedStreamLength) {
				for (int i = 0; i < iFixedStreamLength; ++i)
					mapCouponCurrency.put ("FIXED" + i, _aCompFixedStream[i].couponCurrency());
			}
		}

		if (null != _aCompFloatStream) {
			int iFloatStreamLength = _aCompFloatStream.length;

			if (0 != iFloatStreamLength) {
				for (int i = 0; i < iFloatStreamLength; ++i)
					mapCouponCurrency.put ("FLOAT" + i, _aCompFloatStream[i].couponCurrency());
			}
		}

		return mapCouponCurrency;
	}

	@Override public java.lang.String payCurrency()
	{
		if (null != _aCompFixedStream && 0 != _aCompFixedStream.length)
			return _aCompFixedStream[0].payCurrency();

		if (null != _aCompFloatStream && 0 != _aCompFloatStream.length)
			return _aCompFloatStream[0].payCurrency();

		return null;
	}

	@Override public java.lang.String principalCurrency()
	{
		return null;
	}

	@Override public org.drip.state.identifier.CreditLabel creditLabel()
	{
		if (null != _aCompFixedStream && 0 != _aCompFixedStream.length) {
			for (org.drip.product.rates.Stream fixedStream : _aCompFixedStream) {
				org.drip.state.identifier.CreditLabel creditLabel = fixedStream.creditLabel();

				if (null != creditLabel) return creditLabel;
			}
		}

		if (null != _aCompFloatStream && 0 != _aCompFloatStream.length) {
			for (org.drip.product.rates.Stream floatStream : _aCompFloatStream) {
				org.drip.state.identifier.CreditLabel creditLabel = floatStream.creditLabel();

				if (null != creditLabel) return creditLabel;
			}
		}

		return null;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
			forwardLabel()
	{
		int iNumFloatStream = null == _aCompFloatStream ? 0 : _aCompFloatStream.length;

		if (0 == iNumFloatStream) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
			mapForwardLabel = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>();

		for (int i = 0; i < iNumFloatStream; ++i)
			mapForwardLabel.put ("FLOAT" + i, _aCompFloatStream[i].forwardLabel());

		return mapForwardLabel;
	}

	@Override public org.drip.state.identifier.FundingLabel fundingLabel()
	{
		return org.drip.state.identifier.FundingLabel.Standard (payCurrency());
	}

	@Override public org.drip.state.identifier.GovvieLabel govvieLabel()
	{
		return org.drip.state.identifier.GovvieLabel.Standard (payCurrency());
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel>
		fxLabel()
	{
		int iNumFixedStream = null == _aCompFixedStream ? 0 : _aCompFixedStream.length;
		int iNumFloatStream = null == _aCompFloatStream ? 0 : _aCompFloatStream.length;

		if (0 == iNumFixedStream && 0 == iNumFloatStream) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel> mapFXLabel = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel>();

		for (int i = 0; i < iNumFixedStream; ++i) {
			org.drip.state.identifier.FXLabel fxLabel = _aCompFixedStream[i].fxLabel();

			if (null != fxLabel) mapFXLabel.put ("FIXED" + i, fxLabel);
		}

		for (int i = 0; i < iNumFloatStream; ++i) {
			org.drip.state.identifier.FXLabel fxLabel = _aCompFloatStream[i].fxLabel();

			if (null != fxLabel) mapFXLabel.put ("FLOAT" + i, fxLabel);
		}

		return mapFXLabel;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.VolatilityLabel>
			volatilityLabel()
	{
		return null;
	}

	@Override public void setPrimaryCode (
		final java.lang.String strCode)
	{
	}

	@Override public org.drip.quant.calculus.WengertJacobian jackDDirtyPVDManifestMeasure (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		return null;
	}

	@Override public org.drip.quant.calculus.WengertJacobian manifestMeasureDFMicroJack (
		final java.lang.String strMainfestMeasure,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		return null;
	}

	@Override public org.drip.product.calib.ProductQuoteSet calibQuoteSet (
		final org.drip.state.representation.LatentStateSpecification[] aLSS)
	{
		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint fundingPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint forwardPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint fundingForwardPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint fxPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint govviePRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint volatilityPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	@Override public double initialNotional()
		throws java.lang.Exception
	{
		return 0;
	}

	@Override public double notional (
		final int iDate)
		throws java.lang.Exception
	{
		return 0;
	}

	@Override public double notional (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		return 0;
	}

	@Override public org.drip.analytics.output.CompositePeriodCouponMetrics couponMetrics (
		final int iAccrualEndDate,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
	{
		return null;
	}

	@Override public int freq()
	{
		return 0;
	}

	@Override public org.drip.analytics.date.JulianDate effectiveDate()
	{
		return null;
	}

	@Override public org.drip.analytics.date.JulianDate maturityDate()
	{
		return null;
	}

	@Override public org.drip.analytics.date.JulianDate firstCouponDate()
	{
		return null;
	}

	@Override public java.util.List<org.drip.analytics.cashflow.CompositePeriod> couponPeriods()
	{
		java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCP = new
			java.util.ArrayList<org.drip.analytics.cashflow.CompositePeriod>();

		if (null != _aCompFixedStream && 0 != _aCompFixedStream.length) {
			for (org.drip.product.rates.Stream fixedStream : _aCompFixedStream)
				lsCP.addAll (fixedStream.cashFlowPeriod());
		}

		if (null != _aCompFloatStream && 0 != _aCompFloatStream.length) {
			for (org.drip.product.rates.Stream floatStream : _aCompFloatStream)
				lsCP.addAll (floatStream.cashFlowPeriod());
		}

		return lsCP;
	}

	@Override public org.drip.param.valuation.CashSettleParams cashSettleParams()
	{
		return null;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		long lStart = System.nanoTime();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapResult = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		if (null != _aCompFixedStream && 0 != _aCompFixedStream.length) {
			for (org.drip.product.rates.Stream fixedStream : _aCompFixedStream) {
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>
					mapFixedStreamResult = fixedStream.value (valParams, pricerParams, csqs, quotingParams);

				if (!org.drip.analytics.support.Helper.AccumulateMeasures (mapResult,
					fixedStream.name(), mapFixedStreamResult))
					return null;
			}
		}

		if (null != _aCompFloatStream && 0 != _aCompFloatStream.length) {
			for (org.drip.product.rates.Stream floatStream : _aCompFloatStream) {
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>
					mapFixedStreamResult = floatStream.value (valParams, pricerParams, csqs, quotingParams);

				if (!org.drip.analytics.support.Helper.AccumulateMeasures (mapResult,
					floatStream.name(), mapFixedStreamResult))
					return null;
			}
		}

		mapResult.put ("CalcTime", (System.nanoTime() - lStart) * 1.e-09);

		return mapResult;
	}

	@Override public java.util.Set<java.lang.String> measureNames()
	{
		return null;
	}

	@Override public double pv (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
		throws java.lang.Exception
	{
		double dblDirtyPV = 0.;

		if (null != _aCompFixedStream && 0 != _aCompFixedStream.length) {
			for (org.drip.product.rates.Stream fixedStream : _aCompFixedStream)
				dblDirtyPV += fixedStream.pv (valParams, pricerParams, csqc, vcp);
		}

		if (null != _aCompFloatStream && 0 != _aCompFloatStream.length) {
			for (org.drip.product.rates.Stream floatStream : _aCompFloatStream)
				dblDirtyPV += floatStream.pv (valParams, pricerParams, csqc, vcp);
		}

		return dblDirtyPV;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> calibMeasures (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		return null;
	}
}
