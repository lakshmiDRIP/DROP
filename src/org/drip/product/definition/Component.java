
package org.drip.product.definition;

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
 * Component abstract class extends the ComponentMarketParamRef and provides the following methods:
 *  - Get the product's initial notional, notional, and coupon.
 *  - Get the Effective date, Maturity date, First Coupon Date.
 *  - List the coupon periods.
 *  - Set the market curves - discount, TSY, forward, and Credit curves.
 *  - Retrieve the product's settlement parameters.
 *  - Value the product's using standard/custom market parameters.
 *  - Retrieve the product's named measures and named measure values.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class Component implements org.drip.product.definition.ComponentMarketParamRef {

	protected double measureValue (
		final java.lang.String strMeasure,
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCalc)
		throws java.lang.Exception
	{
		if (null == strMeasure || strMeasure.isEmpty() || null == mapCalc)
			throw new java.lang.Exception ("Component::measureValue => Invalid Inputs");

		java.util.Set<java.util.Map.Entry<java.lang.String, java.lang.Double>> mapES = mapCalc.entrySet();

		if (null == mapES || 0 == mapES.size())
			throw new java.lang.Exception ("Component::measureValue => Invalid Inputs");

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> me : mapES) {
			if (me.getKey().equalsIgnoreCase (strMeasure)) return me.getValue();
		}

		throw new java.lang.Exception ("Component::measureValue => Invalid Measure: " + strMeasure);
	}

	protected boolean adjustForCashSettle (
		final int iSettleDate,
		final double dblPV,
		final org.drip.state.discount.MergedDiscountForwardCurve dc,
		final org.drip.quant.calculus.WengertJacobian wjPVDFMicroJack)
	{
		org.drip.quant.calculus.WengertJacobian wjCashSettleDFDF = dc.jackDDFDManifestMeasure (iSettleDate,
			"Rate");

		if (null == wjCashSettleDFDF) return false;

		double dblDFCashSettle = java.lang.Double.NaN;

		int iNumParameters = wjCashSettleDFDF.numParameters();

		try {
			dblDFCashSettle = dc.df (iSettleDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		if (!wjPVDFMicroJack.scale (1. / dblDFCashSettle)) return false;

		double dblSettleJackAdjust = -1. * dblPV / dblDFCashSettle / dblDFCashSettle;

		for (int k = 0; k < iNumParameters; ++k) {
			if (!wjPVDFMicroJack.accumulatePartialFirstDerivative (0, k, dblSettleJackAdjust *
				wjCashSettleDFDF.firstDerivative (0, k)))
				return false;
		}

		return true;
	}

	/**
	 * Get the Initial Notional for the Product
	 * 
	 * @return Initial Notional
	 * 
	 * @throws java.lang.Exception Thrown if Initial Notional cannot be computed
	 */

	public abstract double initialNotional()
		throws java.lang.Exception;

	/**
	 * Get the Notional for the Product at the given date
	 * 
	 * @param iDate Date
	 * 
	 * @return Product Notional
	 * 
	 * @throws java.lang.Exception Thrown if Notional cannot be computed
	 */

	public abstract double notional (
		final int iDate)
		throws java.lang.Exception;

	/**
	 * Get the time-weighted Notional for the Product between 2 dates
	 * 
	 * @param iDate1 Date #1
	 * @param iDate2 Date #2
	 * 
	 * @return The Product Notional
	 * 
	 * @throws java.lang.Exception Thrown if Notional cannot be computed
	 */

	public abstract double notional (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception;

	/**
	 * Get the Effective Date
	 * 
	 * @return Effective Date
	 */

	public abstract org.drip.analytics.date.JulianDate effectiveDate();

	/**
	 * Get the Maturity Date
	 * 
	 * @return Maturity Date
	 */

	public abstract org.drip.analytics.date.JulianDate maturityDate();

	/**
	 * Get the First Coupon Date
	 * 
	 * @return First Coupon Date
	 */

	public abstract org.drip.analytics.date.JulianDate firstCouponDate();

	/**
	 * Retrieve the Coupon Frequency
	 * 
	 * @return The Coupon Frequency
	 */

	public abstract int freq();

	/**
	 * Get the Product's Cash Flow Periods
	 * 
	 * @return List of the Product's Cash Flow Periods
	 */

	public abstract java.util.List<org.drip.analytics.cashflow.CompositePeriod> couponPeriods();

	/**
	 * Get the Product's coupon Metrics at the specified accrual date
	 * 
	 * @param iAccrualEndDate Accrual End Date
	 * @param valParams The Valuation Parameters
	 * @param csqs Component Market Parameters
	 * 
	 * @return The Product's coupon Nominal/Adjusted Coupon Measures
	 */

	public abstract org.drip.analytics.output.CompositePeriodCouponMetrics couponMetrics (
		final int iAccrualEndDate,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs);

	/**
	 * Get the Product's cash settlement parameters
	 * 
	 * @return Cash settlement Parameters
	 */

	public abstract org.drip.param.valuation.CashSettleParams cashSettleParams();

	/**
	 * Generate a full list of the Product measures for the full input set of market parameters
	 * 
	 * @param valParams ValuationParams
	 * @param pricerParams PricerParams
	 * @param csqs Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return Map of measure name and value
	 */

	public abstract org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp);

	/**
	 * Retrieve the ordered set of the measure names whose values will be calculated
	 * 
	 * @return Set of Measure Names
	 */

	public abstract java.util.Set<java.lang.String> measureNames();

	/**
	 * Compute the PV for the specified Market Parameters
	 * 
	 * @param valParams ValuationParams
	 * @param pricerParams PricerParams
	 * @param csqc Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return The PV
	 * 
	 * @throws java.lang.Exception Thrown if the PV cannot be computed
	 */

	public abstract double pv (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
		throws java.lang.Exception;

	/**
	 * Calculate the value of the given Product's measure
	 * 
	 * @param valParams ValuationParams
	 * @param pricerParams PricerParams
	 * @param csqs ComponentMarketParams
	 * @param strMeasure Measure String
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return Double measure value
	 * 
	 * @throws java.lang.Exception Thrown if the measure cannot be calculated
	 */

	public double measureValue (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final java.lang.String strMeasure)
		throws java.lang.Exception
	{
		return measureValue (strMeasure, value (valParams, pricerParams, csqs, vcp));
	}

	/**
	 * Retrieve the Instrument's Imputed Tenor
	 * 
	 * @return The Instrument's Imputed Tenor
	 */

	public java.lang.String tenor()
	{
		int iNumDays = maturityDate().julian() - effectiveDate().julian();

		if (365 > iNumDays) {
			int iNumMonth = (int) (0.5 + (iNumDays / 30.));

			return 12 == iNumMonth ? "1Y" : iNumMonth + "M";
		}

		 return ((int) (0.5 + (iNumDays / 365.))) + "Y";
	}

	/**
	 * Generate a full list of the Product's measures for the set of scenario market parameters present in
	 * 	the org.drip.param.definition.MarketParams
	 * 
	 * @param valParams ValuationParams
	 * @param pricerParams PricerParams
	 * @param mpc org.drip.param.definition.MarketParams
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return ComponentOutput object
	 */

	public org.drip.analytics.output.ComponentMeasures measures (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.definition.ScenarioMarketParams mpc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == valParams || null == mpc) return null;

		org.drip.param.market.CurveSurfaceQuoteContainer csqsBase = mpc.scenarioMarketParams (this, "Base");

		if (null == csqsBase) return null;

		org.drip.analytics.output.ComponentMeasures compOp = new
			org.drip.analytics.output.ComponentMeasures();

		long lStart = System.nanoTime();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapBaseMeasures = value
			(valParams, pricerParams, csqsBase, vcp);

		if (!compOp.setBaseMeasures (mapBaseMeasures)) return null;

		org.drip.param.market.CurveSurfaceQuoteContainer csqsFlatCreditBumpUp = mpc.scenarioMarketParams (this,
			"FlatCreditBumpUp");

		if (null != csqsFlatCreditBumpUp) {
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFlatCreditBumpUpMeasures =
				value (valParams, pricerParams, csqsFlatCreditBumpUp, vcp);

			java.util.Set<java.util.Map.Entry<java.lang.String, java.lang.Double>> mapFlatCreditBumpUpES =
				null == mapFlatCreditBumpUpMeasures ? null : mapFlatCreditBumpUpMeasures.entrySet();

			if (null != mapFlatCreditBumpUpES && 0 != mapFlatCreditBumpUpES.size()) {
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>
					mapFlatCreditDeltaMeasures = new
						org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

				for (java.util.Map.Entry<java.lang.String, java.lang.Double> me : mapFlatCreditBumpUpES) {
					java.lang.String strKey = me.getKey();

					mapFlatCreditDeltaMeasures.put (strKey, me.getValue() - mapBaseMeasures.get (strKey));
				}

				org.drip.param.market.CurveSurfaceQuoteContainer csqsFlatCreditBumpDown = mpc.scenarioMarketParams
					(this, "FlatCreditBumpDn");

				if (compOp.setFlatCreditDeltaMeasures (mapFlatCreditDeltaMeasures) && null !=
					csqsFlatCreditBumpDown) {
					org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>
						mapFlatCreditBumpDownMeasures = value (valParams, pricerParams,
							csqsFlatCreditBumpDown, vcp);

					java.util.Set<java.util.Map.Entry<java.lang.String, java.lang.Double>>
						mapFlatCreditBumpDownES = null == mapFlatCreditBumpDownMeasures ? null :
							mapFlatCreditBumpDownMeasures.entrySet();

					if (null != mapFlatCreditBumpDownES && 0 != mapFlatCreditBumpDownES.size()) {
						org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>
							mapFlatCreditGammaMeasures = new
								org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

						for (java.util.Map.Entry<java.lang.String, java.lang.Double> me :
							mapFlatCreditBumpDownES) {
							java.lang.String strKey = me.getKey();

							mapFlatCreditGammaMeasures.put (strKey, me.getValue() +
								mapFlatCreditBumpUpMeasures.get (strKey) - 2. * mapBaseMeasures.get
									(strKey));
						}

						compOp.setFlatCreditGammaMeasures (mapFlatCreditGammaMeasures);
					}
				}
			}
		}

		org.drip.param.market.CurveSurfaceQuoteContainer csqsRRBumpUp = mpc.scenarioMarketParams (this, "RRBumpUp");

		if (null != csqsRRBumpUp) {
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapRRBumpUpMeasures = value
				(valParams, pricerParams, csqsRRBumpUp, vcp);

			java.util.Set<java.util.Map.Entry<java.lang.String, java.lang.Double>> mapRRBumpUpES = null ==
				mapRRBumpUpMeasures ? null : mapRRBumpUpMeasures.entrySet();

			if (null != mapRRBumpUpES && 0 != mapRRBumpUpES.size()) {
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFlatRRDeltaMeasures =
					new org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

				for (java.util.Map.Entry<java.lang.String, java.lang.Double> me : mapRRBumpUpES) {
					java.lang.String strKey = me.getKey();

					mapFlatRRDeltaMeasures.put (strKey, me.getValue() - mapBaseMeasures.get (strKey));
				}

				org.drip.param.market.CurveSurfaceQuoteContainer csqsRRBumpDown = mpc.scenarioMarketParams (this,
					"RRBumpDn");

				if (compOp.setFlatRRDeltaMeasures (mapFlatRRDeltaMeasures) && null != csqsRRBumpDown) {
					org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapRRBumpDownMeasures
						= value (valParams, pricerParams, csqsRRBumpDown, vcp);

					java.util.Set<java.util.Map.Entry<java.lang.String, java.lang.Double>> mapRRBumpDownES =
						null == mapRRBumpDownMeasures ? null : mapRRBumpDownMeasures.entrySet();

					if (null != mapRRBumpDownES && 0 != mapRRBumpDownES.size()) {
						org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>
							mapFlatRRGammaMeasures = new
								org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

						for (java.util.Map.Entry<java.lang.String, java.lang.Double> me : mapRRBumpDownES) {
							java.lang.String strKey = me.getKey();

							mapFlatRRGammaMeasures.put (strKey, me.getValue() + mapRRBumpUpMeasures.get
								(strKey) - 2. * mapBaseMeasures.get (strKey));
						}

						compOp.setFlatRRGammaMeasures (mapFlatRRGammaMeasures);
					}
				}
			}
		}

		org.drip.param.market.CurveSurfaceQuoteContainer csqsIRCreditBumpUp = mpc.scenarioMarketParams (this,
			"IRCreditBumpUp");

		if (null != csqsIRCreditBumpUp) {
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapIRCreditBumpUpMeasures =
				value (valParams, pricerParams, csqsIRCreditBumpUp, vcp);

			java.util.Set<java.util.Map.Entry<java.lang.String, java.lang.Double>> mapIRCreditBumpUpES = null
				== mapIRCreditBumpUpMeasures ? null : mapIRCreditBumpUpMeasures.entrySet();

			if (null != mapIRCreditBumpUpES && 0 != mapIRCreditBumpUpES.size()) {
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapFlatIRDeltaMeasures =
					new org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

				for (java.util.Map.Entry<java.lang.String, java.lang.Double> me : mapIRCreditBumpUpES) {
					java.lang.String strKey = me.getKey();

					mapFlatIRDeltaMeasures.put (strKey, me.getValue() - mapBaseMeasures.get (strKey));
				}

				org.drip.param.market.CurveSurfaceQuoteContainer csqsIRCreditBumpDown = mpc.scenarioMarketParams (this,
					"IRCreditBumpDn");

				if (compOp.setFlatIRDeltaMeasures (mapFlatIRDeltaMeasures) && null != csqsIRCreditBumpDown) {
					org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>
						mapIRCreditBumpDownMeasures = value (valParams, pricerParams, csqsIRCreditBumpDown,
							vcp);

					java.util.Set<java.util.Map.Entry<java.lang.String, java.lang.Double>>
						mapIRCreditBumpDownES = null == mapIRCreditBumpDownMeasures ? null :
							mapIRCreditBumpDownMeasures.entrySet();

					if (null != mapIRCreditBumpDownES && 0 != mapIRCreditBumpDownES.size()) {
						org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>
							mapFlatIRGammaMeasures = new
								org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

						for (java.util.Map.Entry<java.lang.String, java.lang.Double> me :
							mapIRCreditBumpDownES) {
							java.lang.String strKey = me.getKey();

							mapFlatIRGammaMeasures.put (strKey, me.getValue() +
								mapIRCreditBumpUpMeasures.get (strKey) - 2. * mapBaseMeasures.get (strKey));
						}

						compOp.setFlatIRGammaMeasures (mapFlatIRGammaMeasures);
					}
				}
			}
		}

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
			mapCCTenorUpCSQS = mpc.creditTenorMarketParams (this, true);

		if (null != mapCCTenorUpCSQS) {
			compOp.setTenorCreditDeltaMeasures (new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>());

			if (null != mapCCTenorUpCSQS && null != mapCCTenorUpCSQS.entrySet()) {
				for (java.util.Map.Entry<java.lang.String, org.drip.param.market.CurveSurfaceQuoteContainer>
					meTenorUpMP : mapCCTenorUpCSQS.entrySet()) {
					if (null == meTenorUpMP || null == meTenorUpMP.getValue()) continue;

					org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCCTenorUp = value
						(valParams, pricerParams, meTenorUpMP.getValue(), vcp);

					if (null == mapCCTenorUp || null == mapCCTenorUp.entrySet()) continue;

					org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCalcUp = new
						org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

					for (java.util.Map.Entry<java.lang.String, java.lang.Double> me :
						mapCCTenorUp.entrySet()) {
						java.lang.String strKey = me.getKey();

						mapCalcUp.put (strKey, me.getValue() - mapBaseMeasures.get (strKey));
					}

					compOp.tenorCreditDeltaMeasures().put (meTenorUpMP.getKey(), mapCalcUp);
				}

				if (null != mpc.creditTenorMarketParams (this, false)) {
					compOp.setTenorCreditGammaMeasures (new
						org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>());

					org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
						mapCCTenorDnCSQS = mpc.creditTenorMarketParams (this, false);

					if (null != mapCCTenorDnCSQS && null != mapCCTenorDnCSQS.entrySet()) {
						for (java.util.Map.Entry<java.lang.String,
							org.drip.param.market.CurveSurfaceQuoteContainer> meTenorDnMP :
								mapCCTenorDnCSQS.entrySet()) {
							if (null == meTenorDnMP || null == meTenorDnMP.getValue()) continue;

							org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCCTenorDn
								= value (valParams, pricerParams, meTenorDnMP.getValue(), vcp);

							if (null == mapCCTenorDn || null == mapCCTenorDn.entrySet()) continue;

							org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCalcDn =
								new org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

							for (java.util.Map.Entry<java.lang.String, java.lang.Double> me :
								mapCCTenorDn.entrySet()) {
								java.lang.String strKey = me.getKey();

								mapCalcDn.put (strKey, me.getValue() - mapBaseMeasures.get (strKey) +
									compOp.tenorCreditDeltaMeasures().get (meTenorDnMP.getKey()).get
										(strKey));
							}

							compOp.tenorCreditGammaMeasures().put (meTenorDnMP.getKey(), mapCalcDn);
						}
					}
				}
			}
		}

		if (null != mpc.fundingTenorMarketParams (this, true)) {
			compOp.setTenorIRDeltaMeasures (new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>());

			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
				mapIRTenorUpCSQS = mpc.fundingTenorMarketParams (this, true);

			if (null != mapIRTenorUpCSQS && null != mapIRTenorUpCSQS.entrySet()) {
				for (java.util.Map.Entry<java.lang.String, org.drip.param.market.CurveSurfaceQuoteContainer>
					meTenorUpMP : mapIRTenorUpCSQS.entrySet()) {
					org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCCTenorUp = value
						(valParams, pricerParams, meTenorUpMP.getValue(), vcp);

					if (null == mapCCTenorUp || null == mapCCTenorUp.entrySet()) continue;

					org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCalcUp = new
						org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

					for (java.util.Map.Entry<java.lang.String, java.lang.Double> me :
						mapCCTenorUp.entrySet()) {
						java.lang.String strKey = me.getKey();

						mapCalcUp.put (strKey, me.getValue() - mapBaseMeasures.get (strKey));
					}

					compOp.tenorIRDeltaMeasures().put (meTenorUpMP.getKey(), mapCalcUp);
				}
			}

			if (null != mpc.fundingTenorMarketParams (this, false)) {
				compOp.setTenorIRGammaMeasures (new
					org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>>());

				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.market.CurveSurfaceQuoteContainer>
					mapIRTenorDnCSQS = mpc.fundingTenorMarketParams (this, false);

				if (null != mapIRTenorDnCSQS & null != mapIRTenorDnCSQS.entrySet()) {
					for (java.util.Map.Entry<java.lang.String, org.drip.param.market.CurveSurfaceQuoteContainer>
						meTenorDnMP : mapIRTenorDnCSQS.entrySet()) {
						org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCCTenorDn = value
							(valParams, pricerParams, meTenorDnMP.getValue(), vcp);

						org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCalcDn = new
							org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

						if (null == mapCalcDn || null == mapCalcDn.entrySet()) continue;

						for (java.util.Map.Entry<java.lang.String, java.lang.Double> me :
							mapCCTenorDn.entrySet()) {
							java.lang.String strKey = me.getKey();

							mapCalcDn.put (strKey, me.getValue() - mapBaseMeasures.get (strKey) +
								compOp.tenorIRDeltaMeasures().get (meTenorDnMP.getKey()).get (strKey));
						}

						compOp.tenorIRGammaMeasures().put (meTenorDnMP.getKey(), mapCalcDn);
					}
				}
			}
		}

		compOp.setCalcTime ((System.nanoTime() - lStart) * 1.e-09);

		return compOp;
	}

	/**
	 * Generate a full list of custom measures for the set of scenario market parameters present in
	 * 	the org.drip.param.definition.MarketParams
	 * 
	 * @param valParams ValuationParams
	 * @param pricerParams PricerParams
	 * @param mpc org.drip.param.definition.MarketParams
	 * @param strCustomScenName Custom Scenario Name
	 * @param vcp Valuation Customization Parameters
	 * @param mapBaseMeasures Base Measures from used to calculate the desired delta measure. If null, the
	 *  base measures will be generated.
	 * 
	 * @return Custom Scenarios Measures output set
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> customScenarioMeasures (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.definition.ScenarioMarketParams mpc,
		final java.lang.String strCustomScenName,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapBaseMeasures)
	{
		if (null == strCustomScenName || strCustomScenName.isEmpty() || null == valParams || null == mpc)
			return null;

		org.drip.param.market.CurveSurfaceQuoteContainer csqsCustom = mpc.scenarioMarketParams (this,
			strCustomScenName);

		if (null == csqsCustom) return null;

		if (null == mapBaseMeasures) {
			org.drip.param.market.CurveSurfaceQuoteContainer csqsBase = mpc.scenarioMarketParams (this, "Base");

			if (null == csqsBase || null == (mapBaseMeasures = value (valParams, pricerParams, csqsBase,
				vcp)))
				return null;
		}

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCustomMeasures = value
			(valParams, pricerParams, csqsCustom, vcp);

		java.util.Set<java.util.Map.Entry<java.lang.String, java.lang.Double>> mapCustomMeasuresES = null ==
			mapCustomMeasures ? null : mapCustomMeasures.entrySet();

		if (null == mapCustomMeasuresES || 0 == mapCustomMeasuresES.size()) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCustomDeltaMeasures = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> me : mapCustomMeasuresES) {
			java.lang.String strKey = me.getKey();

			mapCustomDeltaMeasures.put (strKey, me.getValue() - mapBaseMeasures.get (strKey));
		}

		return mapCustomDeltaMeasures;
	}
}
