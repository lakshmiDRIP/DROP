
package org.drip.product.option;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>OptionComponent</i> extends ComponentMarketParamRef and provides the following methods:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Get the component's initial notional, notional, and coupon.
 *  	</li>
 *  	<li>
 *  		Get the Effective date, Maturity date, First Coupon Date.
 *  	</li>
 *  	<li>
 *  		Set the market curves - discount, TSY, forward, and Credit curves.
 *  	</li>
 *  	<li>
 *  		Retrieve the component's settlement parameters.
 *  	</li>
 *  	<li>
 *  		Value the component using standard/custom market parameters.
 *  	</li>
 *  	<li>
 *  		Retrieve the component's named measures and named measure values.
 *  	</li>
 *  	<li>
 *  		Retrieve the Underlying Fixed Income Product, Day Count, Strike, Calendar, and Manifest Measure.
 *  	</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md">Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option AssetClasses</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/option/README.md">Options on Fixed Income Components</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class OptionComponent extends org.drip.product.definition.CalibratableComponent
{
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
				!org.drip.numerical.common.NumberUtil.IsValid (_dblStrike = dblStrike) ||
					!org.drip.numerical.common.NumberUtil.IsValid (_dblNotional = dblNotional))
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

	@Override public org.drip.state.identifier.EntityCDSLabel creditLabel()
	{
		return _comp.creditLabel();
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
			forwardLabel()
	{
		return _comp.forwardLabel();
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.OTCFixFloatLabel>
			otcFixFloatLabel()
	{
		return _comp.otcFixFloatLabel();
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

	@Override public org.drip.numerical.differentiation.WengertJacobian jackDDirtyPVDManifestMeasure (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		return null;
	}

	@Override public org.drip.numerical.differentiation.WengertJacobian manifestMeasureDFMicroJack (
		final java.lang.String strMainfestMeasure,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		return null;
	}
}
