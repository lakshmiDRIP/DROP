
package org.drip.product.rates;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>RatesBasket</i> contains the implementation of the Basket of Rates Component legs. RatesBasket is made
 * from zero/more fixed and floating streams. It exports the following functionality:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Standard/Custom Constructor for the RatesBasket
 *  	</li>
 *  	<li>
 *  		Dates: Effective, Maturity, Coupon dates and Product settlement Parameters
 *  	</li>
 *  	<li>
 *  		Coupon/Notional Outstanding as well as schedules
 *  	</li>
 *  	<li>
 *  		Retrieve the constituent fixed and floating streams
 *  	</li>
 *  	<li>
 *  		Market Parameters: Discount, Forward, Credit, Treasury Curves
 *  	</li>
 *  	<li>
 *  		Cash Flow Periods: Coupon flows and (Optionally) Loss Flows
 *  	</li>
 *  	<li>
 *  		Valuation: Named Measure Generation
 *  	</li>
 *  	<li>
 *  		Calibration: The codes and constraints generation
 *  	</li>
 *  	<li>
 *  		Jacobians: Quote/DF and PV/DF micro-Jacobian generation
 *  	</li>
 *  	<li>
 *  		Serialization into and de-serialization out of byte arrays
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md">Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option AssetClasses</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/rates/README.md">Fixed Income Multi-Stream Components</a></li>
 *  </ul>
 * <br><br>
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

	@Override public org.drip.state.identifier.EntityCDSLabel creditLabel()
	{
		if (null != _aCompFixedStream && 0 != _aCompFixedStream.length) {
			for (org.drip.product.rates.Stream fixedStream : _aCompFixedStream) {
				org.drip.state.identifier.EntityCDSLabel creditLabel = fixedStream.creditLabel();

				if (null != creditLabel) return creditLabel;
			}
		}

		if (null != _aCompFloatStream && 0 != _aCompFloatStream.length) {
			for (org.drip.product.rates.Stream floatStream : _aCompFloatStream) {
				org.drip.state.identifier.EntityCDSLabel creditLabel = floatStream.creditLabel();

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

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.OTCFixFloatLabel>
			otcFixFloatLabel()
	{
		int iNumFloatStream = null == _aCompFloatStream ? 0 : _aCompFloatStream.length;

		if (0 == iNumFloatStream) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.OTCFixFloatLabel>
			mapOTCFixFloatLabel = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.OTCFixFloatLabel>();

		for (int i = 0; i < iNumFloatStream; ++i)
			mapOTCFixFloatLabel.put ("FLOAT" + i, _aCompFloatStream[i].otcFixFloatLabel());

		return mapOTCFixFloatLabel;
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

	@Override public org.drip.numerical.differentiation.WengertJacobian jackDDirtyPVDManifestMeasure (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		return null;
	}

	@Override public org.drip.numerical.differentiation.WengertJacobian manifestMeasureDFMicroJack (
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
