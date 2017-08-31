
package org.drip.service.product;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * BondAPI demonstrates the Details behind the Pricing and the Scenario Runs behind a Generic Bond.
 *
 * @author Lakshmi Krishnamurthy
 */

public class FixedBondAPI {

	/**
	 * Generate a Full Map Invocation of the Bond Valuation Run
	 * 
	 * @param strIssuerName Bond Issuer Name
	 * @param iBondEffectiveDate Bond Effective Date
	 * @param iBondMaturityDate Bond Maturity Date
	 * @param dblBondCoupon Bond Coupon
	 * @param iBondCouponFrequency Bond Coupon Frequency
	 * @param strBondCouponDayCount Bond Coupon Day Count
	 * @param strBondCouponCurrency Bond Coupon Currency
	 * @param iSpotDate Spot Date
	 * @param astrFundingCurveDepositTenor Deposit Instruments Tenor (for Funding Curve)
	 * @param adblFundingCurveDepositQuote Deposit Instruments Quote (for Funding Curve)
	 * @param strFundingCurveDepositMeasure Deposit Instruments Measure (for Funding Curve)
	 * @param adblFundingCurveFuturesQuote Futures Instruments Tenor (for Funding Curve)
	 * @param strFundingCurveFuturesMeasure Futures Instruments Measure (for Funding Curve)
	 * @param astrFundingCurveFixFloatTenor Fix-Float Instruments Tenor (for Funding Curve)
	 * @param adblFundingCurveFixFloatQuote Fix-Float Instruments Quote (for Funding Curve)
	 * @param strFundingFixFloatMeasure Fix-Float Instruments Tenor (for Funding Curve)
	 * @param strGovvieCode Govvie Bond Code (for Treasury Curve)
	 * @param aiGovvieCurveTreasuryEffectiveDate Array of the Treasury Instrument Effective Date (for Treasury
	 * 		Curve)
	 * @param aiGovvieCurveTreasuryMaturityDate Array of the Treasury Instrument Maturity Date (for Treasury
	 * 		Curve)
	 * @param adblGovvieCurveTreasuryCoupon Array of the Treasury Instrument Coupon (for Treasury Curve)
	 * @param adblGovvieCurveTreasuryYield Array of the Treasury Instrument Yield (for Treasury Curve)
	 * @param strGovvieCurveTreasuryMeasure Treasury Instrument Measure (for Treasury Curve)
	 * @param strCreditCurveName Credit Curve Name (for Credit Curve)
	 * @param astrCreditCurveCDSTenor CDS Maturity Tenor (for Credit Curve)
	 * @param adblCreditCurveCDSCoupon Array of CDS Fixed Coupon (for Credit Curve)
	 * @param adblCreditCurveCDSQuote Array of CDS Market Quotes (for Credit Curve)
	 * @param strCreditCurveCDSMeasure CDS Calibration Measure (for Credit Curve)
	 * @param strBondMarketQuoteName Name of the Bond Market Quote
	 * @param dblBondMarketQuote Bond Market Quote Value
	 * 
	 * @return The Output Measure Map
	 */

	public static final java.util.Map<java.lang.String, java.lang.Double> ValuationMetrics (
		final java.lang.String strIssuerName,
		final int iBondEffectiveDate,
		final int iBondMaturityDate,
		final double dblBondCoupon,
		final int iBondCouponFrequency,
		final java.lang.String strBondCouponDayCount,
		final java.lang.String strBondCouponCurrency,
		final int iSpotDate,
		final java.lang.String[] astrFundingCurveDepositTenor,
		final double[] adblFundingCurveDepositQuote,
		final java.lang.String strFundingCurveDepositMeasure,
		final double[] adblFundingCurveFuturesQuote,
		final java.lang.String strFundingCurveFuturesMeasure,
		final java.lang.String[] astrFundingCurveFixFloatTenor,
		final double[] adblFundingCurveFixFloatQuote,
		final java.lang.String strFundingFixFloatMeasure,
		final java.lang.String strGovvieCode,
		final int[] aiGovvieCurveTreasuryEffectiveDate,
		final int[] aiGovvieCurveTreasuryMaturityDate,
		final double[] adblGovvieCurveTreasuryCoupon,
		final double[] adblGovvieCurveTreasuryYield,
		final java.lang.String strGovvieCurveTreasuryMeasure,
		final java.lang.String strCreditCurveName,
		final java.lang.String[] astrCreditCurveCDSTenor,
		final double[] adblCreditCurveCDSCoupon,
		final double[] adblCreditCurveCDSQuote,
		final java.lang.String strCreditCurveCDSMeasure,
		final java.lang.String strBondMarketQuoteName,
		final double dblBondMarketQuote)
	{
		org.drip.analytics.date.JulianDate dtSpot = null;
		org.drip.analytics.date.JulianDate dtMaturity = null;
		org.drip.analytics.date.JulianDate dtEffective = null;
		org.drip.analytics.date.JulianDate[] adtGovvieCurveTreasuryMaturity = null;
		org.drip.analytics.date.JulianDate[] adtGovvieCurveTreasuryEffective = null;
		int iNumGovvieCurveMaturity = null == aiGovvieCurveTreasuryMaturityDate ? 0 :
			aiGovvieCurveTreasuryMaturityDate.length;
		int iNumGovvieCurveEffective = null == aiGovvieCurveTreasuryEffectiveDate ? 0 :
			aiGovvieCurveTreasuryEffectiveDate.length;
		java.lang.String[] astrTreasuryBenchmarkCode = new java.lang.String[] {"01YON", "02YON", "03YON",
			"05YON", "07YON", "10YON", "30YON"};
		int iNumTreasuryBenchmark = astrTreasuryBenchmarkCode.length;

		if (0 != iNumGovvieCurveMaturity)
			adtGovvieCurveTreasuryMaturity = new org.drip.analytics.date.JulianDate[iNumGovvieCurveMaturity];

		if (0 != iNumGovvieCurveEffective)
			adtGovvieCurveTreasuryEffective = new
				org.drip.analytics.date.JulianDate[iNumGovvieCurveEffective];

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		try {
			dtSpot = new org.drip.analytics.date.JulianDate (iSpotDate);

			dtMaturity = new org.drip.analytics.date.JulianDate (iBondMaturityDate);

			dtEffective = new org.drip.analytics.date.JulianDate (iBondEffectiveDate);

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

		org.drip.product.credit.BondComponent bond = org.drip.product.creator.BondBuilder.CreateSimpleFixed
			(strIssuerName + " " + org.drip.quant.common.FormatUtil.FormatDouble (dblBondCoupon, 1, 4, 100.)
				+ " " + dtMaturity, strBondCouponCurrency, strIssuerName, dblBondCoupon,
					iBondCouponFrequency, strBondCouponDayCount, dtEffective, dtMaturity, null, null);

		if (null == bond) return null;

		org.drip.param.quote.ProductMultiMeasure pmm = new org.drip.param.quote.ProductMultiMeasure();

		try {
			pmm.addQuote (strBondMarketQuoteName, new org.drip.param.quote.MultiSided ("mid",
				dblBondMarketQuote), true);
		} catch (java.lang.Exception e) {
		}

		csqc.setProductQuote (bond.name(), pmm);

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding =
			org.drip.service.template.LatentMarketStateBuilder.SmoothFundingCurve (dtSpot,
				strBondCouponCurrency, astrFundingCurveDepositTenor, adblFundingCurveDepositQuote,
					strFundingCurveDepositMeasure, adblFundingCurveFuturesQuote,
						strFundingCurveFuturesMeasure, astrFundingCurveFixFloatTenor,
							adblFundingCurveFixFloatQuote, strFundingFixFloatMeasure);

		csqc.setFundingState (dcFunding);

		csqc.setGovvieState (org.drip.service.template.LatentMarketStateBuilder.ShapePreservingGovvieCurve
			(strGovvieCode, dtSpot, adtGovvieCurveTreasuryEffective, adtGovvieCurveTreasuryMaturity,
				adblGovvieCurveTreasuryCoupon, adblGovvieCurveTreasuryYield, strGovvieCurveTreasuryMeasure));

		csqc.setCreditState (org.drip.service.template.LatentMarketStateBuilder.CreditCurve (dtSpot,
			strCreditCurveName, astrCreditCurveCDSTenor, adblCreditCurveCDSCoupon, adblCreditCurveCDSQuote,
				strCreditCurveCDSMeasure, dcFunding));

		return bond.value (org.drip.param.valuation.ValuationParams.Spot (iSpotDate), null, csqc, null);
	}

	/**
	 * Generate the Treasury Curve Tenor Key Rate Sensitivity/Duration
	 * 
	 * @param strIssuerName Bond Issuer Name
	 * @param iBondEffectiveDate Bond Effective Date
	 * @param iBondMaturityDate Bond Maturity Date
	 * @param dblBondCoupon Bond Coupon
	 * @param iBondCouponFrequency Bond Coupon Frequency
	 * @param strBondCouponDayCount Bond Coupon Day Count
	 * @param strBondCouponCurrency Bond Coupon Currency
	 * @param iSpotDate Spot Date
	 * @param strGovvieCode Govvie Bond Code (for Treasury Curve)
	 * @param aiGovvieCurveTreasuryEffectiveDate Array of the Treasury Instrument Effective Date (for Treasury
	 * 		Curve)
	 * @param aiGovvieCurveTreasuryMaturityDate Array of the Treasury Instrument Maturity Date (for Treasury
	 * 		Curve)
	 * @param adblGovvieCurveTreasuryCoupon Array of the Treasury Instrument Coupon (for Treasury Curve)
	 * @param adblGovvieCurveTreasuryYield Array of the Treasury Instrument Yield (for Treasury Curve)
	 * @param strGovvieCurveTreasuryMeasure Treasury Instrument Measure (for Govvie Curve)
	 * @param dblBondMarketCleanPrice Bond Market Clean Price
	 * 
	 * @return The Treasury Curve Tenor Sensitivity/Duration
	 */

	public static final java.util.Map<java.lang.String, java.lang.Double> KeyRateDuration (
		final java.lang.String strIssuerName,
		final int iBondEffectiveDate,
		final int iBondMaturityDate,
		final double dblBondCoupon,
		final int iBondCouponFrequency,
		final java.lang.String strBondCouponDayCount,
		final java.lang.String strBondCouponCurrency,
		final int iSpotDate,
		final java.lang.String strGovvieCode,
		final int[] aiGovvieCurveTreasuryEffectiveDate,
		final int[] aiGovvieCurveTreasuryMaturityDate,
		final double[] adblGovvieCurveTreasuryCoupon,
		final double[] adblGovvieCurveTreasuryYield,
		final java.lang.String strGovvieCurveTreasuryMeasure,
		final double dblBondMarketCleanPrice)
	{
		double dblBaselineOAS = java.lang.Double.NaN;
		org.drip.analytics.date.JulianDate dtSpot = null;
		org.drip.analytics.date.JulianDate dtMaturity = null;
		org.drip.analytics.date.JulianDate dtEffective = null;
		org.drip.analytics.date.JulianDate[] adtGovvieCurveTreasuryMaturity = null;
		org.drip.analytics.date.JulianDate[] adtGovvieCurveTreasuryEffective = null;
		int iNumGovvieCurveMaturity = null == aiGovvieCurveTreasuryMaturityDate ? 0 :
			aiGovvieCurveTreasuryMaturityDate.length;
		int iNumGovvieCurveEffective = null == aiGovvieCurveTreasuryEffectiveDate ? 0 :
			aiGovvieCurveTreasuryEffectiveDate.length;
		java.lang.String[] astrTreasuryBenchmarkCode = new java.lang.String[] {"01YON", "02YON", "03YON",
			"05YON", "07YON", "10YON", "30YON"};
		int iNumTreasuryBenchmark = astrTreasuryBenchmarkCode.length;

		if (0 != iNumGovvieCurveMaturity)
			adtGovvieCurveTreasuryMaturity = new org.drip.analytics.date.JulianDate[iNumGovvieCurveMaturity];

		if (0 != iNumGovvieCurveEffective)
			adtGovvieCurveTreasuryEffective = new
				org.drip.analytics.date.JulianDate[iNumGovvieCurveEffective];

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		try {
			dtSpot = new org.drip.analytics.date.JulianDate (iSpotDate);

			dtMaturity = new org.drip.analytics.date.JulianDate (iBondMaturityDate);

			dtEffective = new org.drip.analytics.date.JulianDate (iBondEffectiveDate);

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

		org.drip.product.credit.BondComponent bond = org.drip.product.creator.BondBuilder.CreateSimpleFixed
			(strIssuerName + " " + org.drip.quant.common.FormatUtil.FormatDouble (dblBondCoupon, 1, 4, 100.)
				+ " " + dtMaturity, strBondCouponCurrency, strIssuerName, dblBondCoupon,
					iBondCouponFrequency, strBondCouponDayCount, dtEffective, dtMaturity, null, null);

		if (null == bond) return null;

		org.drip.param.quote.ProductMultiMeasure pmm = new org.drip.param.quote.ProductMultiMeasure();

		try {
			pmm.addQuote ("Price", new org.drip.param.quote.MultiSided ("mid", dblBondMarketCleanPrice),
				true);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		csqc.setProductQuote (bond.name(), pmm);

		org.drip.state.govvie.GovvieCurve gc =
			org.drip.service.template.LatentMarketStateBuilder.ShapePreservingGovvieCurve (strGovvieCode,
				dtSpot, adtGovvieCurveTreasuryEffective, adtGovvieCurveTreasuryMaturity,
					adblGovvieCurveTreasuryCoupon, adblGovvieCurveTreasuryYield,
						strGovvieCurveTreasuryMeasure);

		csqc.setGovvieState (gc);

		org.drip.param.valuation.ValuationParams valParams = org.drip.param.valuation.ValuationParams.Spot
			(iSpotDate);

		try {
			if (!org.drip.quant.common.NumberUtil.IsValid (dblBaselineOAS = bond.oasFromPrice (valParams,
				csqc, null, dblBondMarketCleanPrice)))
				return null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.govvie.GovvieCurve>
			mapTenorGovvieCurve = org.drip.service.template.LatentMarketStateBuilder.BumpedGovvieCurve
				(strGovvieCode, dtSpot, adtGovvieCurveTreasuryEffective, adtGovvieCurveTreasuryMaturity,
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

			try {
				mapKeyRateDuration.put (strKey, 10000. * (bond.priceFromOAS (valParams, csqc, null,
					dblBaselineOAS) - dblBondMarketCleanPrice) / dblBondMarketCleanPrice);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return mapKeyRateDuration;
	}

	/**
	 * Returns Attribution for the Specified Bond Instance
	 * 
	 * @param strIssuerName Bond Issuer Name
	 * @param iBondEffectiveDate Bond Effective Date
	 * @param iBondMaturityDate Bond Maturity Date
	 * @param dblBondCoupon Bond Coupon
	 * @param iBondCouponFrequency Bond Coupon Frequency
	 * @param strBondCouponDayCount Bond Coupon Day Count
	 * @param strBondCouponCurrency Bond Coupon Currency
	 * @param adtSpot Array of Spot Dates
	 * @param adblCleanPrice Array of Closing Clean Prices
	 * 
	 * @return List of the Position Change Components
	 */

	public static final java.util.List<org.drip.historical.attribution.PositionChangeComponents>
		HorizonChangeAttribution (
			final java.lang.String strIssuerName,
			final int iBondEffectiveDate,
			final int iBondMaturityDate,
			final double dblBondCoupon,
			final int iBondCouponFrequency,
			final java.lang.String strBondCouponDayCount,
			final java.lang.String strBondCouponCurrency,
			final org.drip.analytics.date.JulianDate[] adtSpot,
			final double[] adblCleanPrice)
	{
		org.drip.analytics.date.JulianDate dtMaturity = null;
		org.drip.analytics.date.JulianDate dtEffective = null;

		try {
			dtMaturity = new org.drip.analytics.date.JulianDate (iBondMaturityDate);

			dtEffective = new org.drip.analytics.date.JulianDate (iBondEffectiveDate);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.product.credit.BondComponent bond = org.drip.product.creator.BondBuilder.CreateSimpleFixed
			(strIssuerName + " " + org.drip.quant.common.FormatUtil.FormatDouble (dblBondCoupon, 1, 4, 100.)
				+ " " + dtMaturity, strBondCouponCurrency, strIssuerName, dblBondCoupon,
					iBondCouponFrequency, strBondCouponDayCount, dtEffective, dtMaturity, null, null);

		if (null == bond || null == adtSpot || null == adblCleanPrice) return null;

		int iNumCloses = adtSpot.length;
		int[] aiSpotDate = new int[iNumCloses];
		double[] adblYield = new double[iNumCloses];
		double[] adblDirtyPrice = new double[iNumCloses];
		double[] adblModifiedDuration = new double[iNumCloses];

		if (1 >= iNumCloses || iNumCloses != adblCleanPrice.length) return null;

		for (int i = 0; i < iNumCloses; ++i) {
			org.drip.param.valuation.ValuationParams valParamsSpot =
				org.drip.param.valuation.ValuationParams.Spot (aiSpotDate[i] = adtSpot[i].julian());

			try {
				if (!org.drip.quant.common.NumberUtil.IsValid (adblYield[i] = bond.yieldFromPrice
					(valParamsSpot, null, null, adblCleanPrice[i])))
					return null;

				if (!org.drip.quant.common.NumberUtil.IsValid (adblModifiedDuration[i] =
					bond.modifiedDurationFromPrice (valParamsSpot, null, null, adblCleanPrice[i])))
					return null;

				if (!org.drip.quant.common.NumberUtil.IsValid (adblDirtyPrice[i] = adblCleanPrice[i] +
					bond.accrued (aiSpotDate[i], null)))
					return null;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		java.util.List<org.drip.historical.attribution.PositionChangeComponents> lsPCC = new
			java.util.ArrayList<org.drip.historical.attribution.PositionChangeComponents>();

		for (int i = 1; i < iNumCloses; ++i) {
			try {
				org.drip.historical.attribution.BondMarketSnap bpms1 = new
					org.drip.historical.attribution.BondMarketSnap (adtSpot[i - 1],
						adblCleanPrice[i - 1]);

				if (!bpms1.setYieldMarketFactor (adblYield[i - 1], -1. * adblDirtyPrice[i - 1] *
					adblModifiedDuration[i - 1], 0.))
					return null;

				org.drip.historical.attribution.BondMarketSnap bpms2 = new
					org.drip.historical.attribution.BondMarketSnap (adtSpot[i], adblCleanPrice[i]);

				if (!bpms2.setYieldMarketFactor (adblYield[i], -1. * adblDirtyPrice[i] *
					adblModifiedDuration[i], 0.))
					return null;

				lsPCC.add (new org.drip.historical.attribution.PositionChangeComponents (false, bpms1, bpms2,
					org.drip.analytics.daycount.Convention.YearFraction (aiSpotDate[i - 1],
						aiSpotDate[i], strBondCouponDayCount, false, null, strBondCouponCurrency), null));
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return lsPCC;
	}

	/**
	 * Generate the Relative Value Metrics for the Specified Bond
	 * 
	 * @param strIssuerName Bond Issuer Name
	 * @param iBondEffectiveDate Bond Effective Date
	 * @param iBondMaturityDate Bond Maturity Date
	 * @param dblBondCoupon Bond Coupon
	 * @param iBondCouponFrequency Bond Coupon Frequency
	 * @param strBondCouponDayCount Bond Coupon Day Count
	 * @param strBondCouponCurrency Bond Coupon Currency
	 * @param iSpotDate Spot Date
	 * @param astrFundingCurveDepositTenor Deposit Instruments Tenor (for Funding Curve)
	 * @param adblFundingCurveDepositQuote Deposit Instruments Quote (for Funding Curve)
	 * @param strFundingCurveDepositMeasure Deposit Instruments Measure (for Funding Curve)
	 * @param adblFundingCurveFuturesQuote Futures Instruments Tenor (for Funding Curve)
	 * @param strFundingCurveFuturesMeasure Futures Instruments Measure (for Funding Curve)
	 * @param astrFundingCurveFixFloatTenor Fix-Float Instruments Tenor (for Funding Curve)
	 * @param adblFundingCurveFixFloatQuote Fix-Float Instruments Quote (for Funding Curve)
	 * @param strFundingFixFloatMeasure Fix-Float Instruments Tenor (for Funding Curve)
	 * @param strGovvieCode Govvie Bond Code (for Treasury Curve)
	 * @param aiGovvieCurveTreasuryEffectiveDate Array of the Treasury Instrument Effective Date (for Treasury
	 * 		Curve)
	 * @param aiGovvieCurveTreasuryMaturityDate Array of the Treasury Instrument Maturity Date (for Treasury
	 * 		Curve)
	 * @param adblGovvieCurveTreasuryCoupon Array of the Treasury Instrument Coupon (for Treasury Curve)
	 * @param adblGovvieCurveTreasuryYield Array of the Treasury Instrument Yield (for Treasury Curve)
	 * @param strGovvieCurveTreasuryMeasure Treasury Instrument Measure (for Treasury Curve)
	 * @param strCreditCurveName Credit Curve Name (for Credit Curve)
	 * @param astrCreditCurveCDSTenor CDS Maturity Tenor (for Credit Curve)
	 * @param adblCreditCurveCDSCoupon Array of CDS Fixed Coupon (for Credit Curve)
	 * @param adblCreditCurveCDSQuote Array of CDS Market Quotes (for Credit Curve)
	 * @param strCreditCurveCDSMeasure CDS Calibration Measure (for Credit Curve)
	 * @param dblBondMarketCleanPrice Bond Market Clean Price
	 * 
	 * @return The Relative Value Metrics
	 */

	public static final org.drip.analytics.output.BondRVMeasures RelativeValueMetrics (
		final java.lang.String strIssuerName,
		final int iBondEffectiveDate,
		final int iBondMaturityDate,
		final double dblBondCoupon,
		final int iBondCouponFrequency,
		final java.lang.String strBondCouponDayCount,
		final java.lang.String strBondCouponCurrency,
		final int iSpotDate,
		final java.lang.String[] astrFundingCurveDepositTenor,
		final double[] adblFundingCurveDepositQuote,
		final java.lang.String strFundingCurveDepositMeasure,
		final double[] adblFundingCurveFuturesQuote,
		final java.lang.String strFundingCurveFuturesMeasure,
		final java.lang.String[] astrFundingCurveFixFloatTenor,
		final double[] adblFundingCurveFixFloatQuote,
		final java.lang.String strFundingFixFloatMeasure,
		final java.lang.String strGovvieCode,
		final int[] aiGovvieCurveTreasuryEffectiveDate,
		final int[] aiGovvieCurveTreasuryMaturityDate,
		final double[] adblGovvieCurveTreasuryCoupon,
		final double[] adblGovvieCurveTreasuryYield,
		final java.lang.String strGovvieCurveTreasuryMeasure,
		final java.lang.String strCreditCurveName,
		final java.lang.String[] astrCreditCurveCDSTenor,
		final double[] adblCreditCurveCDSCoupon,
		final double[] adblCreditCurveCDSQuote,
		final java.lang.String strCreditCurveCDSMeasure,
		final double dblBondMarketCleanPrice)
	{
		org.drip.analytics.date.JulianDate dtSpot = null;
		org.drip.analytics.date.JulianDate dtMaturity = null;
		org.drip.analytics.date.JulianDate dtEffective = null;
		org.drip.analytics.date.JulianDate[] adtGovvieCurveTreasuryMaturity = null;
		org.drip.analytics.date.JulianDate[] adtGovvieCurveTreasuryEffective = null;
		int iNumGovvieCurveMaturity = null == aiGovvieCurveTreasuryMaturityDate ? 0 :
			aiGovvieCurveTreasuryMaturityDate.length;
		int iNumGovvieCurveEffective = null == aiGovvieCurveTreasuryEffectiveDate ? 0 :
			aiGovvieCurveTreasuryEffectiveDate.length;
		java.lang.String[] astrTreasuryBenchmarkCode = new java.lang.String[] {"01YON", "02YON", "03YON",
			"05YON", "07YON", "10YON", "30YON"};
		int iNumTreasuryBenchmark = astrTreasuryBenchmarkCode.length;

		if (0 != iNumGovvieCurveMaturity)
			adtGovvieCurveTreasuryMaturity = new org.drip.analytics.date.JulianDate[iNumGovvieCurveMaturity];

		if (0 != iNumGovvieCurveEffective)
			adtGovvieCurveTreasuryEffective = new
				org.drip.analytics.date.JulianDate[iNumGovvieCurveEffective];

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		try {
			dtSpot = new org.drip.analytics.date.JulianDate (iSpotDate);

			dtMaturity = new org.drip.analytics.date.JulianDate (iBondMaturityDate);

			dtEffective = new org.drip.analytics.date.JulianDate (iBondEffectiveDate);

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

		org.drip.product.credit.BondComponent bond = org.drip.product.creator.BondBuilder.CreateSimpleFixed
			(strIssuerName + " " + org.drip.quant.common.FormatUtil.FormatDouble (dblBondCoupon, 1, 4, 100.)
				+ " " + dtMaturity, strBondCouponCurrency, strIssuerName, dblBondCoupon,
					iBondCouponFrequency, strBondCouponDayCount, dtEffective, dtMaturity, null, null);

		if (null == bond) return null;

		org.drip.param.quote.ProductMultiMeasure pmm = new org.drip.param.quote.ProductMultiMeasure();

		try {
			pmm.addQuote ("Price", new org.drip.param.quote.MultiSided ("mid", dblBondMarketCleanPrice),
				true);
		} catch (java.lang.Exception e) {
		}

		csqc.setProductQuote (bond.name(), pmm);

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding =
			org.drip.service.template.LatentMarketStateBuilder.SmoothFundingCurve (dtSpot,
				strBondCouponCurrency, astrFundingCurveDepositTenor, adblFundingCurveDepositQuote,
					strFundingCurveDepositMeasure, adblFundingCurveFuturesQuote,
						strFundingCurveFuturesMeasure, astrFundingCurveFixFloatTenor,
							adblFundingCurveFixFloatQuote, strFundingFixFloatMeasure);

		csqc.setFundingState (dcFunding);

		csqc.setGovvieState (org.drip.service.template.LatentMarketStateBuilder.ShapePreservingGovvieCurve
			(strGovvieCode, dtSpot, adtGovvieCurveTreasuryEffective, adtGovvieCurveTreasuryMaturity,
				adblGovvieCurveTreasuryCoupon, adblGovvieCurveTreasuryYield, strGovvieCurveTreasuryMeasure));

		csqc.setCreditState (org.drip.service.template.LatentMarketStateBuilder.CreditCurve (dtSpot,
			strCreditCurveName, astrCreditCurveCDSTenor, adblCreditCurveCDSCoupon, adblCreditCurveCDSQuote,
				strCreditCurveCDSMeasure, dcFunding));

		org.drip.param.valuation.ValuationParams valParams = org.drip.param.valuation.ValuationParams.Spot
			(iSpotDate);

		return bond.standardMeasures (valParams, null, csqc, null, bond.exerciseYieldFromPrice (valParams,
			csqc, null, dblBondMarketCleanPrice), dblBondMarketCleanPrice);
	}
}
