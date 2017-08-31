
package org.drip.product.option;

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
 * OptionComponent extends ComponentMarketParamRef and provides the following methods:
 *  - Get the component's initial notional, notional, and coupon.
 *  - Get the Effective date, Maturity date, First Coupon Date.
 *  - Set the market curves - discount, TSY, forward, and Credit curves.
 *  - Retrieve the component's settlement parameters.
 *  - Value the component using standard/custom market parameters.
 *  - Retrieve the component's named measures and named measure values.
 *  - Retrieve the Underlying Fixed Income Product, Day Count, Strike, Calendar, and Manifest Measure.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class OptionComponent extends org.drip.product.definition.CalibratableComponent {
	private java.lang.String _strCode = "";
	private java.lang.String _strName = "";
	private double _dblStrike = java.lang.Double.NaN;
	private java.lang.String _strManifestMeasure = "";
	private double _dblNotional = java.lang.Double.NaN;
	private org.drip.product.definition.Component _comp = null;
	private org.drip.param.valuation.CashSettleParams _csp = null;
	private org.drip.product.params.LastTradingDateSetting _ltds = null;

	protected OptionComponent (
		final java.lang.String strName,
		final org.drip.product.definition.Component comp,
		final java.lang.String strManifestMeasure,
		final double dblStrike,
		final double dblNotional,
		final org.drip.product.params.LastTradingDateSetting ltds,
		final org.drip.param.valuation.CashSettleParams csp)
		throws java.lang.Exception
	{
		if (null == (_strName = strName) || _strName.isEmpty() || null == (_comp = comp) || null ==
			(_strManifestMeasure = strManifestMeasure) || _strManifestMeasure.isEmpty() ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblStrike = dblStrike) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblNotional = dblNotional))
			throw new java.lang.Exception ("OptionComponent ctr: Invalid Inputs");

		_csp = csp;
		_ltds = ltds;
	}

	/**
	 * Retrieve the Underlying Component
	 * 
	 * @return The Underlying Component
	 */

	public org.drip.product.definition.Component underlying()
	{
		return _comp;
	}

	/**
	 * Retrieve the Manifest Measure on which the Option's Strike is quoted
	 * 
	 * @return The Manifest Measure on which the Option's Strike is quoted
	 */

	public java.lang.String manifestMeasure()
	{
		return _strManifestMeasure;
	}

	/**
	 * Retrieve the Strike
	 * 
	 * @return The Strike
	 */

	public double strike()
	{
		return _dblStrike;
	}

	/**
	 * Retrieve the Notional
	 * 
	 * @return The Notional
	 */

	public double notional()
	{
		return _dblNotional;
	}

	/**
	 * Retrieve the Option Exercise Date
	 * 
	 * @return The Option Exercise Date
	 */

	public org.drip.analytics.date.JulianDate exerciseDate()
	{
		return _comp.effectiveDate();
	}

	/**
	 * Retrieve the Option Last Trading Date Setting
	 * 
	 * @return The Option Last Trading Date Setting 
	 */

	public org.drip.product.params.LastTradingDateSetting lastTradingDateSetting()
	{
		return _ltds;
	}

	@Override public void setPrimaryCode (
		final java.lang.String strCode)
	{
		_strCode = strCode;
	}

	@Override public java.lang.String primaryCode()
	{
		return _strCode;
	}

	@Override public java.lang.String name()
	{
		return _strName;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> couponCurrency()
	{
		return _comp.couponCurrency();
	}

	@Override public java.lang.String payCurrency()
	{
		return _comp.payCurrency();
	}

	@Override public java.lang.String principalCurrency()
	{
		return _comp.payCurrency();
	}

	@Override public org.drip.analytics.date.JulianDate effectiveDate()
	{
		return null;
	}

	@Override public org.drip.analytics.date.JulianDate maturityDate()
	{
		return exerciseDate();
	}

	@Override public org.drip.analytics.date.JulianDate firstCouponDate()
	{
		return _comp.effectiveDate();
	}

	@Override public double initialNotional()
	{
		return _dblNotional;
	}

	@Override public double notional (
		final int dblDate1)
	{
		return _dblNotional;
	}

	@Override public double notional (
		final int dblDate1,
		final int dblDate2)
	{
		return _dblNotional;
	}

	@Override public int freq()
	{
		return _comp.freq();
	}

	@Override public org.drip.state.identifier.CreditLabel creditLabel()
	{
		return _comp.creditLabel();
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
			forwardLabel()
	{
		return _comp.forwardLabel();
	}

	@Override public org.drip.state.identifier.FundingLabel fundingLabel()
	{
		return _comp.fundingLabel();
	}

	@Override public org.drip.state.identifier.GovvieLabel govvieLabel()
	{
		return _comp.govvieLabel();
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel>
		fxLabel()
	{
		return _comp.fxLabel();
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.VolatilityLabel>
			volatilityLabel()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
			mapForwardLabel = forwardLabel();

		if (null == mapForwardLabel || 0 == mapForwardLabel.size()) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.VolatilityLabel>
			mapVolatilityLabel = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.VolatilityLabel>();

		for (java.util.Map.Entry<java.lang.String, org.drip.state.identifier.ForwardLabel> forwardLabelEntry
			: mapForwardLabel.entrySet())
			mapVolatilityLabel.put (forwardLabelEntry.getKey(),
				org.drip.state.identifier.VolatilityLabel.Standard (forwardLabelEntry.getValue()));

		return mapVolatilityLabel;
	}

	@Override public org.drip.analytics.output.CompositePeriodCouponMetrics couponMetrics (
		final int iAccrualEndDate,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
	{
		return null;
	}

	@Override public java.util.List<org.drip.analytics.cashflow.CompositePeriod> couponPeriods()
	{
		return null;
	}

	@Override public org.drip.param.valuation.CashSettleParams cashSettleParams()
	{
		return _csp;
	}

	@Override public org.drip.product.calib.ProductQuoteSet calibQuoteSet (
		final org.drip.state.representation.LatentStateSpecification[] aLSS)
	{
		try {
			return new org.drip.product.calib.VolatilityProductQuoteSet (aLSS);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> calibMeasures (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint forwardPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint fundingPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint fundingForwardPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
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

	@Override public org.drip.quant.calculus.WengertJacobian jackDDirtyPVDManifestMeasure (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		return null;
	}

	@Override public org.drip.quant.calculus.WengertJacobian manifestMeasureDFMicroJack (
		final java.lang.String strMainfestMeasure,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		return null;
	}
}
