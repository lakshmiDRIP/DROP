
package org.drip.product.option;

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
 * <i>FixFloatEuropeanOption</i> implements the Payer/Receiver European Option on the Fix-Float Swap.
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

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblATMManifestMeasure)) return null;

		try {
			double dblSTIRIntegratedSurfaceVariance =
				org.drip.analytics.support.OptionHelper.IntegratedSurfaceVariance (csqs.customVolatility
					(org.drip.state.identifier.CustomLabel.Standard (_stir.name() + "_" +
						strManifestMeasure)), iValueDate, iExerciseDate);

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblSTIRIntegratedSurfaceVariance)) return null;

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

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblManifestMeasurePriceTransformer)) return null;

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

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblATMManifestMeasure))
			throw new java.lang.Exception ("FixFloatEuropeanOption::pv => Invalid Inputs");

		double dblSTIRIntegratedSurfaceVariance =
			org.drip.analytics.support.OptionHelper.IntegratedSurfaceVariance (csqs.customVolatility
				(org.drip.state.identifier.CustomLabel.Standard (_stir.name() + "_" + strManifestMeasure)),
					iValueDate, iExerciseDate);

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblSTIRIntegratedSurfaceVariance))
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

		if (!org.drip.numerical.common.NumberUtil.IsValid (dblManifestMeasurePriceTransformer))
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
