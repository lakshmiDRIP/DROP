
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
 * FixFloatEuropeanOption implements the Payer/Receiver European Option on the Fix-Float Swap.
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixFloatEuropeanOption extends org.drip.product.option.OptionComponent {
	private boolean _bIsReceiver = false;
	private org.drip.product.rates.FixFloatComponent _stir = null;

	/**
	 * FixFloatEuropeanOption constructor
	 * 
	 * @param strName Name
	 * @param stir The Underlying STIR Future Component
	 * @param strManifestMeasure Measure of the Underlying Component
	 * @param bIsReceiver Is the STIR Option a Receiver/Payer? TRUE - Receiver
	 * @param dblStrike Strike of the Underlying Component's Measure
	 * @param dblNotional Option Notional
	 * @param ltds Last Trading Date Setting
	 * @param csp Cash Settle Parameters
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public FixFloatEuropeanOption (
		final java.lang.String strName,
		final org.drip.product.rates.FixFloatComponent stir,
		final java.lang.String strManifestMeasure,
		final boolean bIsReceiver,
		final double dblStrike,
		final double dblNotional,
		final org.drip.product.params.LastTradingDateSetting ltds,
		final org.drip.param.valuation.CashSettleParams csp)
		throws java.lang.Exception
	{
		super (strName, stir, strManifestMeasure, dblStrike, dblNotional, ltds, csp);

		_stir = stir;
		_bIsReceiver = bIsReceiver;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> couponCurrency()
	{
		return _stir.couponCurrency();
	}

	@Override public java.lang.String payCurrency()
	{
		return _stir.payCurrency();
	}

	@Override public java.lang.String principalCurrency()
	{
		return _stir.principalCurrency();
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
	{
		if (null == valParams) return null;

		int iValueDate = valParams.valueDate();

		int iExerciseDate = exerciseDate().julian();

		org.drip.analytics.date.JulianDate dtEffective = _stir.effectiveDate();

		org.drip.product.params.LastTradingDateSetting ltds = lastTradingDateSetting();

		try {
			if (null != ltds && iValueDate >= ltds.lastTradingDate (dtEffective.julian(),
				_stir.referenceStream().calendar()))
				return null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		java.lang.String strPayCurrency = _stir.payCurrency();

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqs.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (strPayCurrency));

		if (null == dcFunding) return null;

		long lStart = System.nanoTime();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapSTIROutput = _stir.value
			(valParams, pricerParams, csqs, quotingParams);

		java.lang.String strManifestMeasure = manifestMeasure();

		if (null == mapSTIROutput || !mapSTIROutput.containsKey (strManifestMeasure)) return null;

		double dblFixedCleanDV01 = mapSTIROutput.get ("CleanFixedDV01");

		double dblATMManifestMeasure = mapSTIROutput.get (strManifestMeasure);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblATMManifestMeasure)) return null;

		try {
			double dblSTIRIntegratedSurfaceVariance =
				org.drip.analytics.support.OptionHelper.IntegratedSurfaceVariance (csqs.customVolatility
					(org.drip.state.identifier.CustomLabel.Standard (_stir.name() + "_" +
						strManifestMeasure)), iValueDate, iExerciseDate);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblSTIRIntegratedSurfaceVariance)) return null;

			double dblSTIRIntegratedSurfaceVolatility = java.lang.Math.sqrt
				(dblSTIRIntegratedSurfaceVariance);

			double dblStrike = strike();

			double dblMoneynessFactor = dblATMManifestMeasure / dblStrike;

			double dblLogMoneynessFactor = java.lang.Math.log (dblMoneynessFactor);

			double dblForwardIntrinsic = java.lang.Double.NaN;
			double dblForwardATMIntrinsic = java.lang.Double.NaN;
			double dblManifestMeasurePriceTransformer = java.lang.Double.NaN;
			double dblManifestMeasureIntrinsic = _bIsReceiver ? dblATMManifestMeasure - dblStrike : dblStrike
				- dblATMManifestMeasure;
			double dblATMDPlus = 0.5 * dblSTIRIntegratedSurfaceVariance / dblSTIRIntegratedSurfaceVolatility;
			double dblATMDMinus = -1. * dblATMDPlus;
			double dblDPlus = (dblLogMoneynessFactor + 0.5 * dblSTIRIntegratedSurfaceVariance) /
				dblSTIRIntegratedSurfaceVolatility;
			double dblDMinus = (dblLogMoneynessFactor - 0.5 * dblSTIRIntegratedSurfaceVariance) /
				dblSTIRIntegratedSurfaceVolatility;

			if (strManifestMeasure.equalsIgnoreCase ("Price") || strManifestMeasure.equalsIgnoreCase ("PV"))
				dblManifestMeasurePriceTransformer = dcFunding.df (iExerciseDate);
			else if (strManifestMeasure.equalsIgnoreCase ("FairPremium") ||
				strManifestMeasure.equalsIgnoreCase ("SwapRate") || strManifestMeasure.equalsIgnoreCase
					("Rate"))
				dblManifestMeasurePriceTransformer = 10000. * dblFixedCleanDV01;

			if (!org.drip.quant.common.NumberUtil.IsValid (dblManifestMeasurePriceTransformer)) return null;

			if (_bIsReceiver) {
				dblForwardIntrinsic = dblATMManifestMeasure * org.drip.measure.gaussian.NormalQuadrature.CDF
					(dblDPlus) - dblStrike * org.drip.measure.gaussian.NormalQuadrature.CDF (dblDMinus);

				dblForwardATMIntrinsic = dblATMManifestMeasure * org.drip.measure.gaussian.NormalQuadrature.CDF
					(dblATMDPlus) - dblStrike * org.drip.measure.gaussian.NormalQuadrature.CDF (dblATMDMinus);
			} else {
				dblForwardIntrinsic = dblStrike * org.drip.measure.gaussian.NormalQuadrature.CDF (-dblDMinus) -
					dblATMManifestMeasure * org.drip.measure.gaussian.NormalQuadrature.CDF (-dblDPlus);

				dblForwardATMIntrinsic = dblStrike * org.drip.measure.gaussian.NormalQuadrature.CDF (-dblATMDMinus)
					- dblATMManifestMeasure * org.drip.measure.gaussian.NormalQuadrature.CDF (-dblATMDPlus);
			}

			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapResult = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

			double dblSpotPrice = dblForwardIntrinsic * dblManifestMeasurePriceTransformer;

			mapResult.put ("ATMSwapRate", dblATMManifestMeasure);

			mapResult.put ("CalcTime", (System.nanoTime() - lStart) * 1.e-09);

			mapResult.put ("ForwardATMIntrinsic", dblForwardATMIntrinsic);

			mapResult.put ("ForwardIntrinsic", dblForwardIntrinsic);

			mapResult.put ("IntegratedSurfaceVariance", dblSTIRIntegratedSurfaceVariance);

			mapResult.put ("ManifestMeasureIntrinsic", dblManifestMeasureIntrinsic);

			mapResult.put ("ManifestMeasureIntrinsicValue", dblManifestMeasureIntrinsic *
				dblManifestMeasurePriceTransformer);

			mapResult.put ("MoneynessFactor", dblMoneynessFactor);

			mapResult.put ("Price", dblSpotPrice);

			mapResult.put ("PV", dblSpotPrice);

			org.drip.market.otc.SwapOptionSettlement sos =
				org.drip.market.otc.SwapOptionSettlementContainer.ConventionFromJurisdiction
					(strPayCurrency);

			if (null != sos) {
				int iSettlementType = sos.settlementType();

				int iSettlementQuote = sos.settlementQuote();

				mapResult.put ("SettleType", (double) iSettlementType);

				mapResult.put ("SettleQuote", (double) iSettlementQuote);

				if (org.drip.market.otc.SwapOptionSettlement.SETTLEMENT_TYPE_CASH_SETTLED == iSettlementType)
				{
					if (org.drip.market.otc.SwapOptionSettlement.SETTLEMENT_QUOTE_EXACT_CURVE ==
						iSettlementQuote)
						mapResult.put ("SettleAmount", dblSpotPrice);
					else if (org.drip.market.otc.SwapOptionSettlement.SETTLEMENT_QUOTE_IRR ==
						iSettlementQuote && (strManifestMeasure.equalsIgnoreCase ("FairPremium") ||
							strManifestMeasure.equalsIgnoreCase ("SwapRate") ||
								strManifestMeasure.equalsIgnoreCase ("Rate"))) {
						org.drip.product.rates.Stream streamDerived = _stir.derivedStream();

						if (csqs.setFundingState
							(org.drip.state.creator.ScenarioDiscountCurveBuilder.CreateFromFlatYield
								(dtEffective, strPayCurrency, dblATMManifestMeasure,
									streamDerived.couponDC(), streamDerived.freq())) && null !=
										(mapSTIROutput = _stir.value (valParams, pricerParams, csqs,
											quotingParams)))
								mapResult.put ("SettleAmount", dblForwardIntrinsic * 10000. *
									mapSTIROutput.get ("CleanFixedDV01"));
					}
				}
			} else
				mapResult.put ("SettleAmount", dblSpotPrice);

			mapResult.put ("SpotPrice", dblSpotPrice);

			mapResult.put ("Upfront", dblSpotPrice);

			return mapResult;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public java.util.Set<java.lang.String> measureNames()
	{
		java.util.Set<java.lang.String> setstrMeasureNames = new java.util.TreeSet<java.lang.String>();

		setstrMeasureNames.add ("ATMSwapRate");

		setstrMeasureNames.add ("CalcTime");

		setstrMeasureNames.add ("ForwardATMIntrinsic");

		setstrMeasureNames.add ("ForwardIntrinsic");

		setstrMeasureNames.add ("IntegratedSurfaceVariance");

		setstrMeasureNames.add ("ManifestMeasureIntrinsic");

		setstrMeasureNames.add ("ManifestMeasureIntrinsicValue");

		setstrMeasureNames.add ("MoneynessFactor");

		setstrMeasureNames.add ("Price");

		setstrMeasureNames.add ("PV");

		setstrMeasureNames.add ("SettleAmount");

		setstrMeasureNames.add ("SettleQuote");

		setstrMeasureNames.add ("SettleType");

		setstrMeasureNames.add ("SpotPrice");

		setstrMeasureNames.add ("Upfront");

		return setstrMeasureNames;
	}

	@Override public double pv (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams)
		throws java.lang.Exception
	{
		if (null == valParams)
			throw new java.lang.Exception ("FixFloatEuropeanOption::pv => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		int iExerciseDate = exerciseDate().julian();

		org.drip.product.params.LastTradingDateSetting ltds = lastTradingDateSetting();

		if (null != ltds && iValueDate >= ltds.lastTradingDate (_stir.effectiveDate().julian(),
			_stir.referenceStream().calendar()))
			throw new java.lang.Exception ("FixFloatEuropeanOption::pv => Invalid Inputs");;

		java.lang.String strPayCurrency = _stir.payCurrency();

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqs.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (strPayCurrency));

		if (null == dcFunding)
			throw new java.lang.Exception ("FixFloatEuropeanOption::pv => Invalid Inputs");

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapSTIROutput = _stir.value
			(valParams, pricerParams, csqs, quotingParams);

		java.lang.String strManifestMeasure = manifestMeasure();

		if (null == mapSTIROutput || !mapSTIROutput.containsKey (strManifestMeasure))
			throw new java.lang.Exception ("FixFloatEuropeanOption::pv => Invalid Inputs");

		double dblFixedCleanDV01 = mapSTIROutput.get ("CleanFixedDV01");

		double dblATMManifestMeasure = mapSTIROutput.get (strManifestMeasure);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblATMManifestMeasure))
			throw new java.lang.Exception ("FixFloatEuropeanOption::pv => Invalid Inputs");

		double dblSTIRIntegratedSurfaceVariance =
			org.drip.analytics.support.OptionHelper.IntegratedSurfaceVariance (csqs.customVolatility
				(org.drip.state.identifier.CustomLabel.Standard (_stir.name() + "_" + strManifestMeasure)),
					iValueDate, iExerciseDate);

		if (!org.drip.quant.common.NumberUtil.IsValid (dblSTIRIntegratedSurfaceVariance))
			throw new java.lang.Exception ("FixFloatEuropeanOption::pv => Invalid Inputs");

		double dblSTIRIntegratedSurfaceVolatility = java.lang.Math.sqrt (dblSTIRIntegratedSurfaceVariance);

		double dblStrike = strike();

		double dblMoneynessFactor = dblATMManifestMeasure / dblStrike;

		double dblLogMoneynessFactor = java.lang.Math.log (dblMoneynessFactor);

		double dblForwardIntrinsic = java.lang.Double.NaN;
		double dblManifestMeasurePriceTransformer = java.lang.Double.NaN;
		double dblDPlus = (dblLogMoneynessFactor + 0.5 * dblSTIRIntegratedSurfaceVariance) /
			dblSTIRIntegratedSurfaceVolatility;
		double dblDMinus = (dblLogMoneynessFactor - 0.5 * dblSTIRIntegratedSurfaceVariance) /
			dblSTIRIntegratedSurfaceVolatility;

		if (strManifestMeasure.equalsIgnoreCase ("Price") || strManifestMeasure.equalsIgnoreCase ("PV"))
			dblManifestMeasurePriceTransformer = dcFunding.df (iExerciseDate);
		else if (strManifestMeasure.equalsIgnoreCase ("FairPremium") ||
			strManifestMeasure.equalsIgnoreCase ("SwapRate") || strManifestMeasure.equalsIgnoreCase
				("Rate"))
			dblManifestMeasurePriceTransformer = 10000. * dblFixedCleanDV01;

		if (!org.drip.quant.common.NumberUtil.IsValid (dblManifestMeasurePriceTransformer))
			throw new java.lang.Exception ("FixFloatEuropeanOption::pv => Invalid Inputs");

		if (_bIsReceiver)
			dblForwardIntrinsic = dblATMManifestMeasure * org.drip.measure.gaussian.NormalQuadrature.CDF
				(dblDPlus) - dblStrike * org.drip.measure.gaussian.NormalQuadrature.CDF (dblDMinus);
		else
			dblForwardIntrinsic = dblStrike * org.drip.measure.gaussian.NormalQuadrature.CDF (-dblDMinus) -
				dblATMManifestMeasure * org.drip.measure.gaussian.NormalQuadrature.CDF (-dblDPlus);

		return dblForwardIntrinsic * dblManifestMeasurePriceTransformer;
	}

	@Override public org.drip.product.calib.ProductQuoteSet calibQuoteSet (
		final org.drip.state.representation.LatentStateSpecification[] aLSS)
	{
		try {
			return new org.drip.product.calib.ProductQuoteSet (aLSS);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint volatilityPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams quotingParams,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}
}
