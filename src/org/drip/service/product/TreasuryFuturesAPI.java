
package org.drip.service.product;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * TreasuryFuturesAPI demonstrates the Details behind the Pricing and the Scenario Runs behind a Treasury
 *  Futures Contract.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryFuturesAPI {

	/**
	 * Generate a Full Map Invocation of the Treasury Futures Run Use Case
	 * 
	 * @param strFuturesCode The Treasury Futures Code
	 * @param aiFuturesComponentTreasuryEffectiveDate Array of the Treasury Futures Component Effective Date
	 * @param aiFuturesComponentTreasuryMaturityDate Array of the Treasury Futures Component Maturity Date
	 * @param adblFuturesComponentTreasuryCoupon Array of the Treasury Futures Component Coupon
	 * @param adblFuturesComponentConversionFactor Array of the Treasury Futures Component Conversion Factor
	 * @param iSpotDate Spot Date
	 * @param astrFundingCurveDepositTenor Deposit Instruments Tenor (for Funding Curve)
	 * @param adblFundingCurveDepositQuote Deposit Instruments Quote (for Funding Curve)
	 * @param strFundingCurveDepositMeasure Deposit Instruments Measure (for Funding Curve)
	 * @param adblFundingCurveFuturesQuote Futures Instruments Tenor (for Funding Curve)
	 * @param strFundingCurveFuturesMeasure Futures Instruments Measure (for Funding Curve)
	 * @param astrFundingCurveFixFloatTenor Fix-Float Instruments Tenor (for Funding Curve)
	 * @param adblFundingCurveFixFloatQuote Fix-Float Instruments Quote (for Funding Curve)
	 * @param strFundingFixFloatMeasure Fix-Float Instruments Tenor (for Funding Curve)
	 * @param aiGovvieCurveTreasuryEffectiveDate Array of the Treasury Instrument Effective Date (for Treasury
	 * 		Curve)
	 * @param aiGovvieCurveTreasuryMaturityDate Array of the Treasury Instrument Maturity Date (for Treasury
	 * 		Curve)
	 * @param adblGovvieCurveTreasuryCoupon Array of the Treasury Instrument Coupon (for Treasury Curve)
	 * @param adblGovvieCurveTreasuryYield Array of the Treasury Instrument Yield (for Treasury Curve)
	 * @param strGovvieCurveTreasuryMeasure Treasury Instrument Measure (for Govvie Curve)
	 * @param adblFuturesComponentTreasuryPrice Array of the Treasury Futures Component Clean Prices
	 * 
	 * @return The Output Measure Map
	 */

	public static final java.util.Map<java.lang.String, java.lang.Double> ValuationMetrics (
		final java.lang.String strFuturesCode,
		final int[] aiFuturesComponentTreasuryEffectiveDate,
		final int[] aiFuturesComponentTreasuryMaturityDate,
		final double[] adblFuturesComponentTreasuryCoupon,
		final double[] adblFuturesComponentConversionFactor,
		final int iSpotDate,
		final java.lang.String[] astrFundingCurveDepositTenor,
		final double[] adblFundingCurveDepositQuote,
		final java.lang.String strFundingCurveDepositMeasure,
		final double[] adblFundingCurveFuturesQuote,
		final java.lang.String strFundingCurveFuturesMeasure,
		final java.lang.String[] astrFundingCurveFixFloatTenor,
		final double[] adblFundingCurveFixFloatQuote,
		final java.lang.String strFundingFixFloatMeasure,
		final int[] aiGovvieCurveTreasuryEffectiveDate,
		final int[] aiGovvieCurveTreasuryMaturityDate,
		final double[] adblGovvieCurveTreasuryCoupon,
		final double[] adblGovvieCurveTreasuryYield,
		final java.lang.String strGovvieCurveTreasuryMeasure,
		final double[] adblFuturesComponentTreasuryPrice)
	{
		org.drip.analytics.date.JulianDate dtSpot = null;
		org.drip.analytics.date.JulianDate[] adtGovvieCurveTreasuryMaturity = null;
		org.drip.analytics.date.JulianDate[] adtGovvieCurveTreasuryEffective = null;
		int iNumGovvieCurveMaturity = null == aiGovvieCurveTreasuryMaturityDate ? 0 :
			aiGovvieCurveTreasuryMaturityDate.length;
		int iNumGovvieCurveEffective = null == aiGovvieCurveTreasuryEffectiveDate ? 0 :
			aiGovvieCurveTreasuryEffectiveDate.length;
		java.lang.String[] astrTreasuryBenchmarkCode = new java.lang.String[] {"01YON", "02YON", "03YON",
			"05YON", "07YON", "10YON", "30YON"};
		int iNumTreasuryBenchmark = astrTreasuryBenchmarkCode.length;

		if (null == adblFuturesComponentTreasuryPrice) return null;

		if (0 != iNumGovvieCurveMaturity)
			adtGovvieCurveTreasuryMaturity = new org.drip.analytics.date.JulianDate[iNumGovvieCurveMaturity];

		if (0 != iNumGovvieCurveEffective)
			adtGovvieCurveTreasuryEffective = new
				org.drip.analytics.date.JulianDate[iNumGovvieCurveEffective];

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		try {
			dtSpot = new org.drip.analytics.date.JulianDate (iSpotDate);

			for (int i = 0; i < iNumGovvieCurveMaturity; ++i)
				adtGovvieCurveTreasuryMaturity[i] = new org.drip.analytics.date.JulianDate
					(aiGovvieCurveTreasuryMaturityDate[i]);

			for (int i = 0; i < iNumGovvieCurveEffective; ++i)
				adtGovvieCurveTreasuryEffective[i] = new org.drip.analytics.date.JulianDate
					(aiGovvieCurveTreasuryEffectiveDate[i]);

			if (null != adblGovvieCurveTreasuryYield && adblGovvieCurveTreasuryYield.length ==
				iNumTreasuryBenchmark) {
				for (int i = 0; i < iNumTreasuryBenchmark; ++i) {
					org.drip.param.quote.ProductMultiMeasure pmm = new
						org.drip.param.quote.ProductMultiMeasure();

					pmm.addQuote ("Yield", new org.drip.param.quote.MultiSided ("mid",
						adblGovvieCurveTreasuryYield[i]), true);

					if (!csqc.setProductQuote (astrTreasuryBenchmarkCode[i], pmm)) return null;
				}
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.product.govvie.TreasuryFutures tsyFut =
			org.drip.service.template.ExchangeInstrumentBuilder.TreasuryFutures (dtSpot, strFuturesCode,
				aiFuturesComponentTreasuryEffectiveDate, aiFuturesComponentTreasuryMaturityDate,
					adblFuturesComponentTreasuryCoupon, adblFuturesComponentConversionFactor);

		if (null == tsyFut) return null;

		org.drip.product.definition.Bond[] aBond = tsyFut.basket();

		int iNumFuturesComponent = adblFuturesComponentTreasuryPrice.length;

		for (int i = 0; i < iNumFuturesComponent; ++i) {
			org.drip.param.quote.ProductMultiMeasure pmm = new org.drip.param.quote.ProductMultiMeasure();

			try {
				pmm.addQuote ("Price", new org.drip.param.quote.MultiSided ("mid",
					adblFuturesComponentTreasuryPrice[i]), true);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			csqc.setProductQuote (aBond[i].name(), pmm);
		}

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding =
			org.drip.service.template.LatentMarketStateBuilder.SmoothFundingCurve (dtSpot,
				aBond[0].currency(), astrFundingCurveDepositTenor, adblFundingCurveDepositQuote,
					strFundingCurveDepositMeasure, adblFundingCurveFuturesQuote,
						strFundingCurveFuturesMeasure, astrFundingCurveFixFloatTenor,
							adblFundingCurveFixFloatQuote, strFundingFixFloatMeasure);

		csqc.setFundingState (dcFunding);

		org.drip.state.govvie.GovvieCurve gc =
			org.drip.service.template.LatentMarketStateBuilder.ShapePreservingGovvieCurve (tsyFut.type(),
				dtSpot, adtGovvieCurveTreasuryEffective, adtGovvieCurveTreasuryMaturity,
					adblGovvieCurveTreasuryCoupon, adblGovvieCurveTreasuryYield,
						strGovvieCurveTreasuryMeasure);

		csqc.setGovvieState (gc);

		return tsyFut.value (org.drip.param.valuation.ValuationParams.Spot (dtSpot.julian()), null, csqc,
			null);
	}

	/**
	 * Generate the Treasury Curve Tenor Key Rate Sensitivity/Duration
	 * 
	 * @param strFuturesCode The Treasury Futures Code
	 * @param aiFuturesComponentTreasuryEffectiveDate Array of the Treasury Futures Component Effective Date
	 * @param aiFuturesComponentTreasuryMaturityDate Array of the Treasury Futures Component Maturity Date
	 * @param adblFuturesComponentTreasuryCoupon Array of the Treasury Futures Component Coupon
	 * @param adblFuturesComponentConversionFactor Array of the Treasury Futures Component Conversion Factor
	 * @param iSpotDate Spot Date
	 * @param aiGovvieCurveTreasuryEffectiveDate Array of the Treasury Instrument Effective Date (for Treasury
	 * 		Curve)
	 * @param aiGovvieCurveTreasuryMaturityDate Array of the Treasury Instrument Maturity Date (for Treasury
	 * 		Curve)
	 * @param adblGovvieCurveTreasuryCoupon Array of the Treasury Instrument Coupon (for Treasury Curve)
	 * @param adblGovvieCurveTreasuryYield Array of the Treasury Instrument Yield (for Treasury Curve)
	 * @param strGovvieCurveTreasuryMeasure Treasury Instrument Measure (for Govvie Curve)
	 * @param adblFuturesComponentTreasuryPrice Array of the Treasury Futures Component Clean Prices
	 * 
	 * @return The Treasury Curve Tenor Sensitivity/Duration
	 */

	public static final java.util.Map<java.lang.String, java.lang.Double> KeyRateDuration (
		final java.lang.String strFuturesCode,
		final int[] aiFuturesComponentTreasuryEffectiveDate,
		final int[] aiFuturesComponentTreasuryMaturityDate,
		final double[] adblFuturesComponentTreasuryCoupon,
		final double[] adblFuturesComponentConversionFactor,
		final int iSpotDate,
		final int[] aiGovvieCurveTreasuryEffectiveDate,
		final int[] aiGovvieCurveTreasuryMaturityDate,
		final double[] adblGovvieCurveTreasuryCoupon,
		final double[] adblGovvieCurveTreasuryYield,
		final java.lang.String strGovvieCurveTreasuryMeasure,
		final double[] adblFuturesComponentTreasuryPrice)
	{
		double dblBaselineCTDOAS = java.lang.Double.NaN;
		org.drip.analytics.date.JulianDate dtSpot = null;
		org.drip.analytics.date.JulianDate[] adtGovvieCurveTreasuryMaturity = null;
		org.drip.analytics.date.JulianDate[] adtGovvieCurveTreasuryEffective = null;
		int iNumGovvieCurveMaturity = null == aiGovvieCurveTreasuryMaturityDate ? 0 :
			aiGovvieCurveTreasuryMaturityDate.length;
		int iNumGovvieCurveEffective = null == aiGovvieCurveTreasuryEffectiveDate ? 0 :
			aiGovvieCurveTreasuryEffectiveDate.length;
		java.lang.String[] astrTreasuryBenchmarkCode = new java.lang.String[] {"01YON", "02YON", "03YON",
			"05YON", "07YON", "10YON", "30YON"};
		int iNumTreasuryBenchmark = astrTreasuryBenchmarkCode.length;
		int iNumFuturesComponent = null == adblFuturesComponentTreasuryPrice ? 0 :
			adblFuturesComponentTreasuryPrice.length;

		if (0 == iNumFuturesComponent) return null;

		if (0 != iNumGovvieCurveMaturity)
			adtGovvieCurveTreasuryMaturity = new org.drip.analytics.date.JulianDate[iNumGovvieCurveMaturity];

		if (0 != iNumGovvieCurveEffective)
			adtGovvieCurveTreasuryEffective = new
				org.drip.analytics.date.JulianDate[iNumGovvieCurveEffective];

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		try {
			dtSpot = new org.drip.analytics.date.JulianDate (iSpotDate);

			for (int i = 0; i < iNumGovvieCurveMaturity; ++i)
				adtGovvieCurveTreasuryMaturity[i] = new org.drip.analytics.date.JulianDate
					(aiGovvieCurveTreasuryMaturityDate[i]);

			for (int i = 0; i < iNumGovvieCurveEffective; ++i)
				adtGovvieCurveTreasuryEffective[i] = new org.drip.analytics.date.JulianDate
					(aiGovvieCurveTreasuryEffectiveDate[i]);

			if (null != adblGovvieCurveTreasuryYield && adblGovvieCurveTreasuryYield.length ==
				iNumTreasuryBenchmark) {
				for (int i = 0; i < iNumTreasuryBenchmark; ++i) {
					org.drip.param.quote.ProductMultiMeasure pmm = new
						org.drip.param.quote.ProductMultiMeasure();

					pmm.addQuote ("Yield", new org.drip.param.quote.MultiSided ("mid",
						adblGovvieCurveTreasuryYield[i]), true);

					if (!csqc.setProductQuote (astrTreasuryBenchmarkCode[i], pmm)) return null;
				}
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.product.govvie.TreasuryFutures tsyFut =
			org.drip.service.template.ExchangeInstrumentBuilder.TreasuryFutures (dtSpot, strFuturesCode,
				aiFuturesComponentTreasuryEffectiveDate, aiFuturesComponentTreasuryMaturityDate,
					adblFuturesComponentTreasuryCoupon, adblFuturesComponentConversionFactor);

		if (null == tsyFut) return null;

		org.drip.product.definition.Bond[] aBond = tsyFut.basket();

		for (int i = 0; i < iNumFuturesComponent; ++i) {
			org.drip.param.quote.ProductMultiMeasure pmm = new org.drip.param.quote.ProductMultiMeasure();

			try {
				pmm.addQuote ("Price", new org.drip.param.quote.MultiSided ("mid",
					adblFuturesComponentTreasuryPrice[i]), true);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (!csqc.setProductQuote (aBond[i].name(), pmm)) return null;
		}

		java.lang.String strTreasuryType = tsyFut.type();

		if (!csqc.setGovvieState
			(org.drip.service.template.LatentMarketStateBuilder.ShapePreservingGovvieCurve (strTreasuryType,
				dtSpot, adtGovvieCurveTreasuryEffective, adtGovvieCurveTreasuryMaturity,
					adblGovvieCurveTreasuryCoupon, adblGovvieCurveTreasuryYield,
						strGovvieCurveTreasuryMeasure)))
			return null;

		org.drip.product.params.CTDEntry ctdEntry = tsyFut.cheapestToDeliverYield (iSpotDate,
			adblFuturesComponentTreasuryPrice);

		if (null == ctdEntry) return null;

		org.drip.product.definition.Bond bondCTD = ctdEntry.bond();

		if (null == bondCTD) return null;

		double dblCTDExpiryPrice = ctdEntry.forwardPrice();

		org.drip.param.valuation.ValuationParams valParamsExpiry =
			org.drip.param.valuation.ValuationParams.Spot (tsyFut.expiry().julian());

		try {
			if (!org.drip.quant.common.NumberUtil.IsValid (dblBaselineCTDOAS = bondCTD.oasFromPrice
				(valParamsExpiry, csqc, null, dblCTDExpiryPrice)))
				return null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.govvie.GovvieCurve>
			mapTenorGovvieCurve = org.drip.service.template.LatentMarketStateBuilder.BumpedGovvieCurve
				(strTreasuryType, dtSpot, adtGovvieCurveTreasuryEffective, adtGovvieCurveTreasuryMaturity,
					adblGovvieCurveTreasuryCoupon, adblGovvieCurveTreasuryYield,
						strGovvieCurveTreasuryMeasure,
							org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING, 0.0001,
								false);

		if (null == mapTenorGovvieCurve || iNumTreasuryBenchmark > mapTenorGovvieCurve.size()) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapKeyRateDuration = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, org.drip.state.govvie.GovvieCurve> me :
			mapTenorGovvieCurve.entrySet()) {
			java.lang.String strKey = me.getKey();

			if (!strKey.contains ("tsy")) continue;

			if (!csqc.setGovvieState (me.getValue())) return null;

			org.drip.product.params.CTDEntry tenorCTDEntry = tsyFut.cheapestToDeliverYield (iSpotDate,
				adblFuturesComponentTreasuryPrice);

			if (null == tenorCTDEntry) return null;

			org.drip.product.definition.Bond tenorBondCTD = tenorCTDEntry.bond();

			if (null == tenorBondCTD) return null;

			try {
				mapKeyRateDuration.put (strKey, 10000. * (tenorBondCTD.priceFromOAS (valParamsExpiry, csqc,
					null, dblBaselineCTDOAS) - dblCTDExpiryPrice) / dblCTDExpiryPrice);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return mapKeyRateDuration;
	}

	/**
	 * Returns Attribution for the Treasury Futures
	 * 
	 * @param strTreasuryCode The Treasury Code
	 * @param adtEffective Array of Effective Dates
	 * @param adtMaturity Array of Maturity Dates
	 * @param adblCoupon Array of Coupons
	 * @param adtExpiry Array of Futures Expiry Dates
	 * @param adtSpot Array of Spot Dates
	 * @param adblCleanPrice Array of Closing Clean Prices
	 * @param adblConversionFactor Array of the Conversion Factor
	 * 
	 * @return List of the Position Change Components
	 */

	public static final java.util.List<org.drip.historical.attribution.PositionChangeComponents>
		HorizonChangeAttribution (
			final java.lang.String strTreasuryCode,
			final org.drip.analytics.date.JulianDate[] adtEffective,
			final org.drip.analytics.date.JulianDate[] adtMaturity,
			final double[] adblCoupon,
			final org.drip.analytics.date.JulianDate[] adtExpiry,
			final org.drip.analytics.date.JulianDate[] adtSpot,
			final double[] adblCleanPrice,
			final double[] adblConversionFactor)
	{
		org.drip.product.credit.BondComponent[] aTreasury =
			org.drip.service.template.TreasuryBuilder.FromCode (strTreasuryCode, adtEffective, adtMaturity,
				adblCoupon);

		if (null == aTreasury || null == adtExpiry || null == adtSpot || null == adblCleanPrice || null ==
			adblConversionFactor)
			return null;

		int iNumCloses = adtSpot.length;
		int[] aiExpiryDate = new int[iNumCloses];
		double[] adblYield = new double[iNumCloses];
		double[] adblForwardAccrued = new double[iNumCloses];
		double[] adblForwardCleanPrice = new double[iNumCloses];
		double[] adblForwardModifiedDuration = new double[iNumCloses];

		if (1 >= iNumCloses || iNumCloses != aTreasury.length || iNumCloses != adblCleanPrice.length ||
			iNumCloses != adtExpiry.length || iNumCloses != adblConversionFactor.length)
			return null;

		java.util.List<org.drip.historical.attribution.PositionChangeComponents> lsPCC = new
			java.util.ArrayList<org.drip.historical.attribution.PositionChangeComponents>();

		for (int i = 0; i < iNumCloses; ++i) {
			if (null == aTreasury[i]) return null;

			org.drip.param.valuation.ValuationParams valParamsSpot =
				org.drip.param.valuation.ValuationParams.Spot (adtSpot[i].julian());

			org.drip.param.valuation.ValuationParams valParamsExpiry =
				org.drip.param.valuation.ValuationParams.Spot (aiExpiryDate[i] = adtExpiry[i].julian());

			try {
				adblForwardAccrued[i] = aTreasury[i].accrued (aiExpiryDate[i], null);

				adblYield[i] = aTreasury[i].yieldFromPrice (valParamsSpot, null, null, adblCleanPrice[i]);

				adblForwardCleanPrice[i] = aTreasury[i].priceFromYield (valParamsExpiry, null, null,
					adblYield[i]);

				adblForwardModifiedDuration[i] = aTreasury[i].modifiedDurationFromPrice (valParamsExpiry,
					null, null, adblForwardCleanPrice[i]) * 10000.;
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		for (int i = 1; i < iNumCloses; ++i) {
			if (adblConversionFactor[i] != adblConversionFactor[i - 1]) continue;

			java.lang.String strCurrentBondName = aTreasury[i].name();

			java.lang.String strPreviousBondName = aTreasury[i - 1].name();

			double dblScaledPrice1 = (adblForwardCleanPrice[i - 1] + adblForwardAccrued[i - 1]) /
				adblConversionFactor[i - 1];
			double dblScaledPrice2 = (adblForwardCleanPrice[i] + adblForwardAccrued[i]) /
				adblConversionFactor[i];

			try {
				org.drip.historical.attribution.TreasuryFuturesMarketSnap tfpms1 = new
					org.drip.historical.attribution.TreasuryFuturesMarketSnap (adtSpot[i - 1],
						dblScaledPrice1);

				if (!tfpms1.setYieldMarketFactor (adblYield[i - 1], -1. * dblScaledPrice1 *
					adblForwardModifiedDuration[i - 1], 0.) || !tfpms1.setExpiryDate (adtExpiry[i - 1]) ||
						!tfpms1.setCTDName (strPreviousBondName) || !tfpms1.setCleanExpiryPrice
							(adblForwardCleanPrice[i - 1]) || !tfpms1.setConversionFactor
								(adblConversionFactor[i - 1]))
					return null;

				org.drip.historical.attribution.TreasuryFuturesMarketSnap tfpms2 = new
					org.drip.historical.attribution.TreasuryFuturesMarketSnap (adtSpot[i],
						dblScaledPrice2);

				if (!tfpms2.setYieldMarketFactor (adblYield[i], -1. * dblScaledPrice2 *
					adblForwardModifiedDuration[i], 0.) || !tfpms2.setExpiryDate (adtExpiry[i]) ||
						!tfpms2.setCTDName (strCurrentBondName) || !tfpms2.setCleanExpiryPrice
							(adblForwardCleanPrice[i]) || !tfpms2.setConversionFactor
								(adblConversionFactor[i]))
					return null;

				org.drip.historical.attribution.PositionChangeComponents pcc = new
					org.drip.historical.attribution.PositionChangeComponents (false, tfpms1, tfpms2, 0.,
						null);

				lsPCC.add (pcc);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		return lsPCC;
	}

	/**
	 * Generate the Horizon Treasury Curve Tenor Key Rate Sensitivity/Duration
	 * 
	 * @param strTreasuryType The Treasury Type
	 * @param adtEffective Array of Effective Dates
	 * @param adtMaturity Array of Maturity Dates
	 * @param adblCoupon Array of Coupons
	 * @param adtExpiry Array of Futures Expiry Dates
	 * @param adtSpot Array of Spot Dates
	 * @param adblCleanPrice Array of Closing Clean Prices
	 * @param astrBenchmarkTenor Array of Benchmark Tenors
	 * @param aadblGovvieCurveTreasuryYield Array of the Treasury Instrument Yield (for Treasury Curve)
	 * 
	 * @return The Treasury Curve Tenor Sensitivity/Duration
	 */

	public static final java.util.List<org.drip.historical.sensitivity.TenorDurationNodeMetrics>
		HorizonKeyRateDuration (
			final java.lang.String strTreasuryType,
			final org.drip.analytics.date.JulianDate[] adtEffective,
			final org.drip.analytics.date.JulianDate[] adtMaturity,
			final double[] adblCoupon,
			final org.drip.analytics.date.JulianDate[] adtExpiry,
			final org.drip.analytics.date.JulianDate[] adtSpot,
			final double[] adblCleanPrice,
			final java.lang.String[] astrBenchmarkTenor,
			final double[][] aadblGovvieCurveTreasuryYield)
	{
		if (null == adtSpot || null == astrBenchmarkTenor || null == aadblGovvieCurveTreasuryYield || null ==
			aadblGovvieCurveTreasuryYield[0])
			return null;

		double dblExpiryCleanPrice = java.lang.Double.NaN;
		double dblExpiryGSpread = java.lang.Double.NaN;
		int iNumBenchmark = astrBenchmarkTenor.length;
		double dblExpiryYield = java.lang.Double.NaN;
		double dblSpotGSpread = java.lang.Double.NaN;
		double dblSpotYield = java.lang.Double.NaN;
		int iNumCloses = adtSpot.length;

		if (0 >= iNumCloses || iNumCloses != aadblGovvieCurveTreasuryYield.length) return null;

		java.util.List<org.drip.historical.sensitivity.TenorDurationNodeMetrics> lsTDNM = new
			java.util.ArrayList<org.drip.historical.sensitivity.TenorDurationNodeMetrics>();

		for (int i = 0; i < iNumCloses; ++i) {
			if (null == aadblGovvieCurveTreasuryYield[i] || iNumBenchmark !=
				aadblGovvieCurveTreasuryYield[i].length)
				return null;

			org.drip.analytics.date.JulianDate[] adtGovvieCurveTreasuryEffective = new
				org.drip.analytics.date.JulianDate[iNumBenchmark];
			org.drip.analytics.date.JulianDate[] adtGovvieCurveTreasuryMaturity = new
				org.drip.analytics.date.JulianDate[iNumBenchmark];
			org.drip.historical.sensitivity.TenorDurationNodeMetrics tdnm = null;
			double dblParallelKRD = 0.;

			for (int j = 0; j < iNumBenchmark; ++j) {
				adtGovvieCurveTreasuryEffective[j] = adtSpot[i];

				adtGovvieCurveTreasuryMaturity[j] = adtSpot[i].addTenor (astrBenchmarkTenor[j]);
			}

			org.drip.state.govvie.GovvieCurve gc =
				org.drip.service.template.LatentMarketStateBuilder.ShapePreservingGovvieCurve
					(strTreasuryType, adtSpot[i], adtGovvieCurveTreasuryEffective,
						adtGovvieCurveTreasuryMaturity, aadblGovvieCurveTreasuryYield[i],
							aadblGovvieCurveTreasuryYield[i], "Yield");

			org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
				org.drip.param.market.CurveSurfaceQuoteContainer();

			if (!csqc.setGovvieState (gc)) continue;

			org.drip.param.valuation.ValuationParams valParamsSpot =
				org.drip.param.valuation.ValuationParams.Spot (adtSpot[i].julian());

			org.drip.param.valuation.ValuationParams valParamsExpiry =
				org.drip.param.valuation.ValuationParams.Spot (adtExpiry[i].julian());

			org.drip.product.credit.BondComponent bondCTD =
				org.drip.service.template.TreasuryBuilder.FromCode (strTreasuryType, adtEffective[i],
					adtMaturity[i], adblCoupon[i]);

			if (null == bondCTD) continue;

			try {
				dblSpotGSpread = bondCTD.gSpreadFromPrice (valParamsSpot, csqc, null, adblCleanPrice[i]);

				dblSpotYield = bondCTD.yieldFromPrice (valParamsSpot, csqc, null, adblCleanPrice[i]);

				dblExpiryCleanPrice = bondCTD.priceFromGSpread (valParamsExpiry, csqc, null, dblSpotGSpread);

				dblExpiryGSpread = bondCTD.gSpreadFromPrice (valParamsExpiry, csqc, null,
					dblExpiryCleanPrice);

				dblExpiryYield = bondCTD.yieldFromPrice (valParamsExpiry, csqc, null, dblExpiryCleanPrice);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				continue;
			}

			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.govvie.GovvieCurve>
				mapTenorGovvieCurve = org.drip.service.template.LatentMarketStateBuilder.BumpedGovvieCurve
					(strTreasuryType, adtSpot[i], adtGovvieCurveTreasuryEffective,
						adtGovvieCurveTreasuryMaturity, aadblGovvieCurveTreasuryYield[i],
							aadblGovvieCurveTreasuryYield[i], "Yield",
								org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING, 0.0001,
									false);

			if (null == mapTenorGovvieCurve || iNumBenchmark > mapTenorGovvieCurve.size()) continue;

			try {
				tdnm = new org.drip.historical.sensitivity.TenorDurationNodeMetrics (adtSpot[i]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			tdnm.setR1 ("SpotGSpread", dblSpotGSpread);

			tdnm.setR1 ("ExpiryGSpread", dblExpiryGSpread);

			tdnm.setR1 ("SpotYield", dblSpotYield);

			tdnm.setR1 ("ExpiryYield", dblExpiryYield);

			tdnm.setDate ("ExpiryDate", adtExpiry[i]);

			tdnm.setC1 ("CTDName", bondCTD.name());

			tdnm.setR1 ("SpotCTDCleanPrice", adblCleanPrice[i]);

			tdnm.setR1 ("ExpiryCTDCleanPrice", dblExpiryCleanPrice);

			for (java.util.Map.Entry<java.lang.String, org.drip.state.govvie.GovvieCurve> me :
				mapTenorGovvieCurve.entrySet()) {
				java.lang.String strKey = me.getKey();

				if (!strKey.contains ("tsy")) continue;

				if (!csqc.setGovvieState (me.getValue())) return null;

				double dblTenorKRD = java.lang.Double.NaN;

				try {
					dblTenorKRD = -10000. * (bondCTD.priceFromGSpread (valParamsExpiry, csqc, null,
						dblSpotGSpread) - dblExpiryCleanPrice) / dblExpiryCleanPrice;

					if (!tdnm.addKRDNode (strKey, dblTenorKRD)) continue;

					dblParallelKRD += dblTenorKRD;
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}

			tdnm.setR1 ("ParallelKRD", dblParallelKRD);

			lsTDNM.add (tdnm);
		}

		return lsTDNM;
	}
}
