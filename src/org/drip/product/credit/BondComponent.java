
package org.drip.product.credit;

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
 * BondComponent is the base class that extends CreditComponent abstract class and implements the
 * 	functionality behind bonds of all kinds. Bond static data is captured in a set of 11 container classes –
 *  BondTSYParams, BondCouponParams, BondNotionalParams, BondFloaterParams, BondCurrencyParams,
 *  BondIdentifierParams, CompCRValParams, BondCFTerminationEvent, BondFixedPeriodGenerationParams, and one
 *  EmbeddedOptionSchedule object instance each for the call and the put objects. Each of these parameter set
 *  can be set separately.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BondComponent extends org.drip.product.definition.Bond implements
	org.drip.product.definition.BondProduct {
	private static final boolean s_bSuppressErrors = true;
	private static final boolean s_bYieldDFOffofCouponAccrualDCF = true;

	/*
	 * Width for calculating local forward rate width
	 */

	private static final int LOCAL_FORWARD_RATE_WIDTH = 1;

	/*
	 * Recovery Period discretization Mode
	 */

	private static final int s_iDiscretizationScheme =
		org.drip.param.pricer.CreditPricerParams.PERIOD_DISCRETIZATION_DAY_STEP;

	/*
	 * Discount Curve to derive the zero curve off of
	 */

	private static final int ZERO_OFF_OF_RATES_INSTRUMENTS_DISCOUNT_CURVE = 1;
	private static final int ZERO_OFF_OF_TREASURIES_DISCOUNT_CURVE = 2;

	/*
	 * Discount Curve to derive Bumped Prices
	 */

	private static final int PRICE_OFF_OF_FUNDING_CURVE = 1;
	private static final int PRICE_OFF_OF_TREASURY_CURVE = 2;
	// private static final int PRICE_OFF_OF_OVERNIGHT_CURVE = 3;

	private org.drip.product.params.BondStream _stream = null;
	private org.drip.product.params.IdentifierSet _idParams = null;
	private org.drip.product.params.CouponSetting _couponSetting = null;
	private org.drip.product.params.CreditSetting _creditSetting = null;
	private org.drip.product.params.FloaterSetting _floaterSetting = null;
	private org.drip.product.params.NotionalSetting _notionalSetting = null;
	private org.drip.product.params.QuoteConvention _quoteConvention = null;
	private org.drip.product.params.TerminationSetting _terminationSetting = null;
	private org.drip.product.params.TreasuryBenchmarks _treasuryBenchmarks = null;

	/*
	 * Bond EOS Params
	 */

	protected org.drip.product.params.EmbeddedOptionSchedule _eosPut = null;
	protected org.drip.product.params.EmbeddedOptionSchedule _eosCall = null;

	private double treasuryBenchmarkYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final int iWorkoutDate)
		throws java.lang.Exception
	{
		if (null == valParams || null == csqc)
			throw new java.lang.Exception ("BondComponent::treasuryBenchmarkYield => Invalid Inputs");

		java.lang.String strTsyBmk = null;
		org.drip.param.definition.ProductQuote pqTsyBmkYield = null;

		if (null != _treasuryBenchmarks) strTsyBmk = _treasuryBenchmarks.primary();

		int iValDate = valParams.valueDate();

		if (null == strTsyBmk || strTsyBmk.isEmpty())
			strTsyBmk = org.drip.analytics.support.Helper.BaseTsyBmk (iValDate, iWorkoutDate);

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote> pqMap =
			csqc.quoteMap();

		if (null != pqMap && null != strTsyBmk && !strTsyBmk.isEmpty())
			pqTsyBmkYield = pqMap.get (strTsyBmk);

		if (null != pqTsyBmkYield) {
			 org.drip.param.definition.Quote q = pqTsyBmkYield.quote ("Yield");

			 if (null != q) return q.value ("mid");
		}

		org.drip.state.govvie.GovvieCurve gc = csqc.govvieState (govvieLabel());

		return null == gc ? java.lang.Double.NaN : gc.yield (iWorkoutDate);
	}

	private org.drip.param.valuation.WorkoutInfo exerciseCallYieldFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
	{
		if (null == valParams || null == csqs || !org.drip.quant.common.NumberUtil.IsValid (dblPrice) || null
			== _eosCall)
			return null;

		int iValDate = valParams.valueDate();

		int[] aiEOSDate = _eosCall.dates();

		int iMaturityDate = maturityDate().julian();

		double[] adblEOSFactor = _eosCall.factors();

		int iNoticePeriod = _eosCall.exerciseNoticePeriod();

		int iExercise = -1;
		double dblExerciseYield = java.lang.Double.NaN;
		int iNumEOSDate = null == aiEOSDate ? 0 : aiEOSDate.length;

		try {
			dblExerciseYield = yieldFromPrice (valParams, csqs, vcp, iMaturityDate, 1., dblPrice);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();

			return null;
		}

		for (int i = 0; i < iNumEOSDate; ++i) {
			if (iValDate > aiEOSDate[i] || aiEOSDate[i] - iValDate < iNoticePeriod) continue;

			try {
				double dblYield = yieldFromPrice (valParams, csqs, vcp, aiEOSDate[i], adblEOSFactor[i],
					dblPrice);

				if (dblYield < dblExerciseYield) {
					iExercise = i;
					dblExerciseYield = dblYield;
				}
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();
			}
		}

		try {
			return -1 == iExercise ? new org.drip.param.valuation.WorkoutInfo (iMaturityDate,
				dblExerciseYield, 1., org.drip.param.valuation.WorkoutInfo.WO_TYPE_MATURITY) : new
					org.drip.param.valuation.WorkoutInfo (aiEOSDate[iExercise], dblExerciseYield,
						adblEOSFactor[iExercise], org.drip.param.valuation.WorkoutInfo.WO_TYPE_CALL);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		return null;
	}

	private org.drip.param.valuation.WorkoutInfo exercisePutYieldFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
	{
		if (null == valParams || null == csqs || !org.drip.quant.common.NumberUtil.IsValid (dblPrice) || null
			== _eosPut)
			return null;

		int iValueDate = valParams.valueDate();

		int[] aiEOSDate = _eosPut.dates();

		double[] adblEOSFactor = _eosPut.factors();

		int iNoticePeriod = _eosPut.exerciseNoticePeriod();

		int iMaturityDate = maturityDate().julian();

		int iExercise = -1;
		double dblExerciseYield = java.lang.Double.NaN;
		int iNumEOSDate = null == aiEOSDate ? 0 : aiEOSDate.length;

		try {
			dblExerciseYield = yieldFromPrice (valParams, csqs, vcp, iMaturityDate, 1., dblPrice);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();

			return null;
		}

		for (int i = 0; i < iNumEOSDate; ++i) {
			if (iValueDate > aiEOSDate[i] || aiEOSDate[i] - iValueDate < iNoticePeriod) continue;

			try {
				double dblYield = yieldFromPrice (valParams, csqs, vcp, aiEOSDate[i], adblEOSFactor[i],
					dblPrice);

				if (dblYield > dblExerciseYield) {
					iExercise = i;
					dblExerciseYield = dblYield;
				}
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();
			}
		}

		try {
			return -1 == iExercise ? new org.drip.param.valuation.WorkoutInfo (iMaturityDate,
				dblExerciseYield, 1., org.drip.param.valuation.WorkoutInfo.WO_TYPE_MATURITY) : new
					org.drip.param.valuation.WorkoutInfo (aiEOSDate[iExercise], dblExerciseYield,
						adblEOSFactor[iExercise], org.drip.param.valuation.WorkoutInfo.WO_TYPE_PUT);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		return null;
	}

	private double priceFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield,
		final boolean bApplyCouponExtension)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.quant.common.NumberUtil.IsValid (dblWorkoutFactor))
			throw new java.lang.Exception ("BondComponent::priceFromYield => Invalid inputs");

		int iValueDate = valParams.valueDate();

		if (iValueDate >= iWorkoutDate)
			throw new java.lang.Exception ("BondComponent::priceFromYield => Invalid inputs");

		boolean bFirstPeriod = true;
		double dblPeriodYearFract = 0.;
		double dblCumulativePeriodPV = 0.;
		boolean bTerminateCouponFlow = false;
		boolean bApplyFlatForwardRate = false;
		int iCashPayDate = java.lang.Integer.MIN_VALUE;
		double dblFlatForwardRate = java.lang.Double.NaN;
		double dblScalingNotional = java.lang.Double.NaN;
		org.drip.analytics.daycount.ActActDCParams aap = null;

		if (null != _notionalSetting && _notionalSetting.priceOffOfOriginalNotional())
			dblScalingNotional = 1.;

		int iFrequency = freq();

		java.lang.String strDC = couponDC();

		java.lang.String strCalendar = currency();

		boolean bApplyCpnEOMAdj = _stream.couponEOMAdjustment();

		if (null == strCalendar || strCalendar.isEmpty()) strCalendar = redemptionCurrency();

		org.drip.param.valuation.ValuationCustomizationParams vcpQuote = null == _quoteConvention ? null :
			_quoteConvention.valuationCustomizationParams();

		if (null != vcp) {
			strDC = vcp.yieldDayCount();

			iFrequency = vcp.yieldFreq();

			strCalendar = vcp.yieldCalendar();

			bApplyCpnEOMAdj = vcp.applyYieldEOMAdj();

			bApplyFlatForwardRate = vcp.applyFlatForwardRate();
		} else if (null != vcpQuote) {
			strDC = vcpQuote.yieldDayCount();

			iFrequency = vcpQuote.yieldFreq();

			strCalendar = vcpQuote.yieldCalendar();

			bApplyCpnEOMAdj = vcpQuote.applyYieldEOMAdj();

			bApplyFlatForwardRate = vcpQuote.applyFlatForwardRate();
		}

		int iPeriodAmortizationMode = _notionalSetting.periodAmortizationMode();

		for (org.drip.analytics.cashflow.CompositePeriod period : couponPeriods()) {
			int iPeriodPayDate = period.payDate();

			if (iPeriodPayDate < iValueDate) continue;

			if (bFirstPeriod) {
				bFirstPeriod = false;

				dblPeriodYearFract = period.couponDCF() - period.accrualDCF (iValueDate);
			} else
				dblPeriodYearFract += period.couponDCF();

			int iPeriodEndDate = period.endDate();

			int iPeriodStartDate = period.startDate();

			if (iPeriodEndDate >= iWorkoutDate) {
				iPeriodEndDate = iWorkoutDate;
				bTerminateCouponFlow = true;
			}

			if (!org.drip.quant.common.NumberUtil.IsValid (dblScalingNotional))
				dblScalingNotional = notional (iPeriodStartDate);

			org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = couponMetrics (iValueDate,
				valParams, csqc);

			if (null == cpcm)
				throw new java.lang.Exception ("BondComponent::priceFromYield => No PCM for " + new
					org.drip.analytics.date.JulianDate (iValueDate) + " | " + effectiveDate());

			if (null != vcp) {
				if (null == (aap = vcp.yieldAAP()))
					aap = new org.drip.analytics.daycount.ActActDCParams (vcp.yieldFreq(), iPeriodEndDate -
						iPeriodStartDate);
			} else if (null != vcpQuote) {
				if (null == (aap = vcpQuote.yieldAAP()))
					aap = new org.drip.analytics.daycount.ActActDCParams (vcpQuote.yieldFreq(),
						iPeriodEndDate - iPeriodStartDate);
			} else
				aap = new org.drip.analytics.daycount.ActActDCParams (iFrequency, iPeriodEndDate -
					iPeriodStartDate);

			double dblYieldAnnuity = org.drip.analytics.support.Helper.Yield2DF (iFrequency, dblYield,
				s_bYieldDFOffofCouponAccrualDCF ? dblPeriodYearFract :
					org.drip.analytics.daycount.Convention.YearFraction (iValueDate, iPeriodPayDate, strDC,
						bApplyCpnEOMAdj, aap, strCalendar)) * cpcm.cumulative();

			double dblPeriodStartNotional = notional (iPeriodStartDate);

			double dblPeriodEndNotional = notional (iPeriodEndDate);

			double dblCouponNotional = dblPeriodStartNotional;

			if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END == iPeriodAmortizationMode)
				dblCouponNotional = dblPeriodEndNotional;
			else if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_EFFECTIVE ==
				iPeriodAmortizationMode)
				dblCouponNotional = notional (iPeriodStartDate, iPeriodEndDate);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblFlatForwardRate))
				dblFlatForwardRate = cpcm.rate();

			dblCumulativePeriodPV += (period.accrualDCF (iPeriodEndDate) * (bApplyFlatForwardRate ?
				dblFlatForwardRate : cpcm.rate() + (bApplyCouponExtension ?
					_couponSetting.couponRateExtension() : 0.)) * dblCouponNotional + dblPeriodStartNotional
						- dblPeriodEndNotional) * dblYieldAnnuity;

			if (bTerminateCouponFlow) break;
		}

		try {
			iCashPayDate = null != _quoteConvention ? _quoteConvention.settleDate (valParams) :
				valParams.cashPayDate();
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();

			iCashPayDate = valParams.cashPayDate();
		}

		return (((dblCumulativePeriodPV + dblWorkoutFactor * org.drip.analytics.support.Helper.Yield2DF
			(iFrequency, dblYield, s_bYieldDFOffofCouponAccrualDCF ? dblPeriodYearFract :
				org.drip.analytics.daycount.Convention.YearFraction (iValueDate, iWorkoutDate, strDC,
					bApplyCpnEOMAdj, aap, strCalendar)) * notional (iWorkoutDate)) /
						org.drip.analytics.support.Helper.Yield2DF (iFrequency, dblYield,
							org.drip.analytics.daycount.Convention.YearFraction (iValueDate, iCashPayDate,
								strDC, bApplyCpnEOMAdj, aap, strCalendar))) - accrued (iValueDate, csqc)) /
									dblScalingNotional;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> calibMeasures (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		double dblCleanPrice = java.lang.Double.NaN;

		org.drip.param.definition.CalibrationParams calibParams = null == pricerParams ? null :
			pricerParams.calibParams();

		if (null == calibParams) return null;

		org.drip.param.valuation.WorkoutInfo wi = calibParams.workout();

		double dblExerciseFactor = null == wi ? 1. : wi.factor();

		int iExerciseDate = null == wi ? maturityDate().julian() : wi.date();

		org.drip.state.credit.CreditCurve cc = csqc.creditState (creditLabel());

		try {
			dblCleanPrice = null == cc ? priceFromFundingCurve (valParams, csqc, iExerciseDate,
				dblExerciseFactor, 0.) : priceFromCreditCurve (valParams, csqc, iExerciseDate,
					dblExerciseFactor, 0., false);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();

			return null;
		}

		if (!org.drip.quant.common.NumberUtil.IsValid (dblCleanPrice)) return null;

		java.lang.String strCalibMeasure = calibParams.measure();

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCalibMeasures = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		if (org.drip.quant.common.StringUtil.MatchInStringArray (strCalibMeasure, new java.lang.String[]
			{"CleanPrice", "FairCleanPrice", "FairPrice", "Price"}, false)) {
			mapCalibMeasures.put (strCalibMeasure, dblCleanPrice);

			return mapCalibMeasures;
		}

		if (org.drip.quant.common.StringUtil.MatchInStringArray (strCalibMeasure, new java.lang.String[]
			{"DirtyPrice", "FairDirtyPrice"}, false)) {
			try {
				mapCalibMeasures.put (strCalibMeasure, dblCleanPrice + accrued (valParams.valueDate(),
					csqc));

				return mapCalibMeasures;
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();
			}
		}

		if (org.drip.quant.common.StringUtil.MatchInStringArray (strCalibMeasure, new java.lang.String[]
			{"Yield", "FairYield"}, false)) {
			try {
				mapCalibMeasures.put (strCalibMeasure, yieldFromPrice (valParams, csqc, vcp, iExerciseDate,
					dblExerciseFactor, dblCleanPrice));

				return mapCalibMeasures;
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();
			}
		}

		if (org.drip.quant.common.StringUtil.MatchInStringArray (strCalibMeasure, new java.lang.String[]
			{"TSYSpread", "FairTSYSpread"}, false)) {
			try {
				mapCalibMeasures.put (strCalibMeasure, tsySpreadFromPrice (valParams, csqc, vcp,
					iExerciseDate, dblExerciseFactor, dblCleanPrice));

				return mapCalibMeasures;
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();
			}
		}

		if (org.drip.quant.common.StringUtil.MatchInStringArray (strCalibMeasure, new java.lang.String[]
			{"OAS", "OASpread", "OptionAdjustedSpread"}, false)) {
			try {
				mapCalibMeasures.put (strCalibMeasure, oasFromPrice (valParams, csqc, vcp, iExerciseDate,
					dblExerciseFactor, dblCleanPrice));

				return mapCalibMeasures;
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();
			}
		}

		if (org.drip.quant.common.StringUtil.MatchInStringArray (strCalibMeasure, new java.lang.String[]
			{"BondBasis", "YieldBasis", "YieldSpread"}, false)) {
			try {
				mapCalibMeasures.put (strCalibMeasure, bondBasisFromPrice (valParams, csqc, vcp,
					iExerciseDate, dblExerciseFactor, dblCleanPrice));

				return mapCalibMeasures;
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();
			}
		}

		if (org.drip.quant.common.StringUtil.MatchInStringArray (strCalibMeasure, new java.lang.String[]
			{"CreditBasis"}, false)) {
			if (null == cc) return null;

			try {
				mapCalibMeasures.put (strCalibMeasure, creditBasisFromPrice (valParams, csqc, vcp,
					iExerciseDate, dblExerciseFactor, dblCleanPrice));

				return mapCalibMeasures;
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();
			}
		}

		if (org.drip.quant.common.StringUtil.MatchInStringArray (strCalibMeasure, new java.lang.String[]
			{"PECS", "ParEquivalentCDSSpread"}, false)) {
			if (null == cc) return null;

			try {
				mapCalibMeasures.put (strCalibMeasure, pecsFromPrice (valParams, csqc, vcp, iExerciseDate,
					dblExerciseFactor, dblCleanPrice));

				return mapCalibMeasures;
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();
			}
		}

		return null;
	}

	@Override public org.drip.param.valuation.WorkoutInfo exerciseYieldFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
	{
		if (null == valParams || null == csqc || !org.drip.quant.common.NumberUtil.IsValid (dblPrice))
			return null;

		int iMaturityDate = maturityDate().julian();

		try {
			if (null == _eosCall && null == _eosPut)
				return new org.drip.param.valuation.WorkoutInfo (iMaturityDate, yieldFromPrice (valParams,
					csqc, vcp, iMaturityDate, 1., dblPrice), 1.,
						org.drip.param.valuation.WorkoutInfo.WO_TYPE_MATURITY);

			if (null == _eosCall && null != _eosPut)
				return exercisePutYieldFromPrice (valParams, csqc, vcp, dblPrice);

			if (null != _eosCall && null == _eosPut)
				return exerciseCallYieldFromPrice (valParams, csqc, vcp, dblPrice);

			org.drip.param.valuation.WorkoutInfo wiPut = exercisePutYieldFromPrice (valParams, csqc, vcp,
				dblPrice);

			org.drip.param.valuation.WorkoutInfo wiCall = exerciseCallYieldFromPrice (valParams, csqc, vcp,
				dblPrice);

			if (null == wiPut || null == wiCall) return null;

			return wiPut.date() < wiCall.date() ? wiPut : wiCall;
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		return null;
	}

	private double indexRate (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.analytics.cashflow.CompositeFloatingPeriod cup)
		throws java.lang.Exception
	{
		org.drip.state.discount.MergedDiscountForwardCurve dc = csqc.fundingState (fundingLabel());

		int iFreq = freq();

		if (null != cup) {
			org.drip.analytics.cashflow.ComposableUnitPeriod cupFirst = cup.periods().get (0);

			if (!(cupFirst instanceof org.drip.analytics.cashflow.ComposableUnitFloatingPeriod))
				throw new java.lang.Exception ("BondComponent::indexRate => Not a floater");

			int iFixingDate = ((org.drip.analytics.cashflow.ComposableUnitFloatingPeriod)
				cupFirst).referenceIndexPeriod().fixingDate();

			org.drip.state.identifier.ForwardLabel forwardLabel = null == _floaterSetting ? null :
				_floaterSetting.fri();

			if (!csqc.available (iFixingDate, forwardLabel)) {
				org.drip.state.forward.ForwardRateEstimator fc = null;

				int iPayDate = cup.payDate();

				int iStartDate = cup.startDate();

				if (null == forwardLabel || null == (fc = csqc.forwardState (forwardLabel)) ||
					!forwardLabel.match (fc.index()))
					fc = dc.forwardRateEstimator (iPayDate, forwardLabel);

				if (null != fc) return fc.forward (iPayDate);

				return iStartDate < iValueDate && 0 != iFreq ? dc.libor (iValueDate, (12 / iFreq) + "M") :
					dc.libor (iStartDate, cup.endDate());
			}

			return csqc.fixing (iFixingDate, forwardLabel);
		}

		return dc.libor (iValueDate, 0 != iFreq ? iValueDate + 365 / iFreq : iValueDate +
			LOCAL_FORWARD_RATE_WIDTH);
	}

	private org.drip.analytics.output.BondWorkoutMeasures workoutMeasures (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final int iWorkoutDate,
		final double dblWorkoutFactor)
	{
		if (null == valParams || null == csqc) return null;

		int iValueDate = valParams.valueDate();

		if (iValueDate >= iWorkoutDate) return null;

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqc.fundingState (fundingLabel());

		if (null == dcFunding) return null;

		int iLossPayLag = null == _creditSetting ? 0 : _creditSetting.lossPayLag();

		double dblProductRecovery = null == _creditSetting ? java.lang.Double.NaN :
			_creditSetting.recovery();

		boolean bUseCurveRecovery = null == _creditSetting ? false : _creditSetting.useCurveRecovery();

		boolean bAccrualOnDefault = null == _creditSetting ? false : _creditSetting.accrualOnDefault();

		org.drip.state.credit.CreditCurve cc = csqc.creditState (creditLabel());

		double dblAccrued01 = 0.;
		double dblRecoveryPV = 0.;
		boolean bPeriodZero = true;
		double dblExpectedRecovery = 0.;
		double dblCreditRiskyDirtyDV01 = 0.;
		boolean bTerminateCouponFlow = false;
		double dblCreditRiskyPrincipalPV = 0.;
		double dblCreditRisklessDirtyDV01 = 0.;
		double dblCreditRiskyDirtyCouponPV = 0.;
		double dblCreditRisklessPrincipalPV = 0.;
		double dblCreditRisklessDirtyCouponPV = 0.;
		double dblFirstCoupon = java.lang.Double.NaN;
		double dblCreditRiskyDirtyIndexCouponPV = 0.;
		double dblFirstIndexRate = java.lang.Double.NaN;
		double dblCreditRisklessDirtyIndexCouponPV = 0.;
		double dblCreditRiskyParPV = java.lang.Double.NaN;
		double dblFlatForwardCoupon = java.lang.Double.NaN;
		double dblCreditRisklessParPV = java.lang.Double.NaN;

		org.drip.param.valuation.ValuationCustomizationParams vcp = null == _quoteConvention ? null :
			_quoteConvention.valuationCustomizationParams();

		boolean bApplyFlatForwardRate = null == vcp ? false : vcp.applyFlatForwardRate();

		try {
			for (org.drip.analytics.cashflow.CompositePeriod period : couponPeriods()) {
				int iPeriodPayDate = period.payDate();

				if (iPeriodPayDate < iValueDate) continue;

				int iPeriodEndDate = period.endDate();

				int iPeriodStartDate = period.startDate();

				if (iWorkoutDate <= iPeriodEndDate) {
					bTerminateCouponFlow = true;
					iPeriodEndDate = iWorkoutDate;
				}

				org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = couponMetrics (iValueDate,
					valParams, csqc);

				if (null == cpcm) return null;

				if (!org.drip.quant.common.NumberUtil.IsValid (dblFlatForwardCoupon))
					dblFlatForwardCoupon = cpcm.rate();

				double dblPeriodBaseRate = period.periods().get (0).baseRate (csqc);

				double dblPeriodAnnuity = dcFunding.df (iPeriodPayDate) * cpcm.cumulative();

				double dblPeriodCoupon = bApplyFlatForwardRate ? dblFlatForwardCoupon : cpcm.rate();

				if (bPeriodZero) {
					bPeriodZero = false;
					dblFirstCoupon = dblPeriodCoupon;

					if (iPeriodStartDate < iValueDate)
						dblAccrued01 = 0.0001 * period.accrualDCF (iValueDate) * notional (iPeriodStartDate,
							iValueDate);

					if (null != _floaterSetting) dblFirstIndexRate = dblPeriodBaseRate;
				}

				double dblPeriodCreditRisklessDirtyDV01 = 0.0001 * period.accrualDCF (iPeriodEndDate) *
					dblPeriodAnnuity * notional (iPeriodStartDate, iPeriodEndDate);

				double dblPeriodCreditRiskessPrincipalPV = (notional (iPeriodStartDate) - notional
					(iPeriodEndDate)) * dblPeriodAnnuity;

				double dblPeriodCreditRiskyDirtyDV01 = dblPeriodCreditRisklessDirtyDV01;
				double dblPeriodCreditRiskyPrincipalPV = dblPeriodCreditRiskessPrincipalPV;

				if (null != cc && null != pricerParams) {
					double dblSurvProb = cc.survival (pricerParams.survivalToPayDate() ? iPeriodPayDate :
						iPeriodEndDate);

					dblPeriodCreditRiskyDirtyDV01 *= dblSurvProb;
					dblPeriodCreditRiskyPrincipalPV *= dblSurvProb;

					for (org.drip.analytics.cashflow.LossQuadratureMetrics lqm : period.lossMetrics (this,
						valParams, pricerParams, iWorkoutDate, csqc)) {
						if (null == lqm) continue;

						int iSubPeriodEndDate = lqm.endDate();

						int iSubPeriodStartDate = lqm.startDate();

						double dblSubPeriodDF = dcFunding.effectiveDF (iSubPeriodStartDate + iLossPayLag,
							iSubPeriodEndDate + iLossPayLag);

						double dblSubPeriodNotional = notional (iSubPeriodStartDate, iSubPeriodEndDate);

						double dblSubPeriodSurvival = cc.survival (iSubPeriodStartDate) - cc.survival
							(iSubPeriodEndDate);

						if (bAccrualOnDefault)
							dblPeriodCreditRiskyDirtyDV01 += 0.0001 * lqm.accrualDCF() * dblSubPeriodSurvival
								* dblSubPeriodDF * dblSubPeriodNotional;

						double dblRecovery = bUseCurveRecovery ? cc.effectiveRecovery (iSubPeriodStartDate,
							iSubPeriodEndDate) : dblProductRecovery;

						double dblSubPeriodExpRecovery = dblRecovery * dblSubPeriodSurvival *
							dblSubPeriodNotional;
						dblRecoveryPV += dblSubPeriodExpRecovery * dblSubPeriodDF;
						dblExpectedRecovery += dblSubPeriodExpRecovery;
					}
				}

				dblCreditRiskyDirtyDV01 += dblPeriodCreditRiskyDirtyDV01;
				dblCreditRiskyPrincipalPV += dblPeriodCreditRiskyPrincipalPV;
				dblCreditRisklessDirtyDV01 += dblPeriodCreditRisklessDirtyDV01;
				dblCreditRisklessPrincipalPV += dblPeriodCreditRiskessPrincipalPV;
				dblCreditRiskyDirtyCouponPV += 10000. * dblPeriodCoupon * dblPeriodCreditRiskyDirtyDV01;
				dblCreditRisklessDirtyCouponPV += 10000. * dblPeriodCoupon *
					dblPeriodCreditRisklessDirtyDV01;
				dblCreditRiskyDirtyIndexCouponPV += 10000. * dblPeriodBaseRate *
					dblPeriodCreditRiskyDirtyDV01;
				dblCreditRisklessDirtyIndexCouponPV += 10000. * dblPeriodBaseRate *
					dblPeriodCreditRisklessDirtyDV01;

				if (bTerminateCouponFlow) break;
			}
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();

			return null;
		}

		int iCashPayDate = java.lang.Integer.MIN_VALUE;

		try {
			iCashPayDate = null != _quoteConvention ? _quoteConvention.settleDate (valParams) :
				valParams.cashPayDate();
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();

			iCashPayDate = valParams.cashPayDate();
		}

		try {
			double dblCashPayDF = dcFunding.df (iCashPayDate);

			int iMaturityDate = maturityDate().julian();

			dblCreditRisklessParPV = dcFunding.df (iMaturityDate) * notional (iMaturityDate) *
				dblWorkoutFactor;

			if (null != cc && null != pricerParams)
				dblCreditRiskyParPV = dblCreditRisklessParPV * cc.survival (iMaturityDate);

			org.drip.analytics.output.BondCouponMeasures bcmCreditRisklessDirty = new
				org.drip.analytics.output.BondCouponMeasures (dblCreditRisklessDirtyDV01,
					dblCreditRisklessDirtyIndexCouponPV, dblCreditRisklessDirtyCouponPV,
						dblCreditRisklessDirtyCouponPV + dblCreditRisklessPrincipalPV +
							dblCreditRisklessParPV);

			double dblDefaultExposure = java.lang.Double.NaN;
			double dblDefaultExposureNoRec = java.lang.Double.NaN;
			double dblLossOnInstantaneousDefault = java.lang.Double.NaN;
			org.drip.analytics.output.BondCouponMeasures bcmCreditRiskyDirty = null;

			if (null != cc && null != pricerParams) {
				double dblInitialNotional = notional (iValueDate);

				double dblInitialRecovery = cc.recovery (iValueDate);

				bcmCreditRiskyDirty = new org.drip.analytics.output.BondCouponMeasures
					(dblCreditRiskyDirtyDV01, dblCreditRiskyDirtyIndexCouponPV, dblCreditRiskyDirtyCouponPV,
						dblCreditRiskyDirtyCouponPV + dblCreditRiskyPrincipalPV + dblCreditRiskyParPV);

				dblDefaultExposure = (dblDefaultExposureNoRec = dblInitialNotional) * dblInitialRecovery;
				dblLossOnInstantaneousDefault = dblInitialNotional * (1. - dblInitialRecovery);
			}

			return new org.drip.analytics.output.BondWorkoutMeasures (bcmCreditRiskyDirty,
				bcmCreditRisklessDirty, dblCreditRiskyParPV, dblCreditRisklessParPV,
					dblCreditRiskyPrincipalPV, dblCreditRisklessPrincipalPV, dblRecoveryPV,
						dblExpectedRecovery, dblDefaultExposure, dblDefaultExposureNoRec,
							dblLossOnInstantaneousDefault, dblAccrued01, dblFirstCoupon, dblFirstIndexRate,
								dblCashPayDF);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		return null;
	}

	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> rvMeasures (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.param.valuation.WorkoutInfo wi,
		final double dblPrice,
		final java.lang.String strPrefix)
	{
		if (null == strPrefix) return null;

		org.drip.analytics.output.BondRVMeasures bmRV = standardMeasures (valParams, pricerParams, csqc, vcp,
			wi, dblPrice);

		return null == bmRV ? null : bmRV.toMap (strPrefix);
	}

	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> fairMeasures (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		int iMaturityDate = maturityDate().julian();

		org.drip.analytics.output.BondWorkoutMeasures bwmFair = workoutMeasures (valParams, pricerParams,
			csqc, iMaturityDate, 1.);

		if (null == bwmFair) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapMeasures = bwmFair.toMap ("");

		org.drip.analytics.output.BondCouponMeasures bcm = bwmFair.creditRiskyCleanbcm();

		double dblCreditRiskyPV = null == bcm ? java.lang.Double.NaN : bcm.pv();

		double dblPrice = !org.drip.quant.common.NumberUtil.IsValid (dblCreditRiskyPV) ?
			bwmFair.creditRisklessCleanbcm().pv() : dblCreditRiskyPV;

		try {
			org.drip.quant.common.CollectionUtil.MergeWithMain (mapMeasures, rvMeasures (valParams,
				pricerParams, csqc, vcp, new org.drip.param.valuation.WorkoutInfo (iMaturityDate,
					yieldFromPrice (valParams, csqc, vcp, dblPrice / notional (valParams.valueDate())), 1.,
						org.drip.param.valuation.WorkoutInfo.WO_TYPE_MATURITY), dblPrice, ""));

			org.drip.quant.common.CollectionUtil.MergeWithMain (mapMeasures,
				org.drip.quant.common.CollectionUtil.PrefixKeys (mapMeasures, "Fair"));

			return mapMeasures;
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		return null;
	}

	private org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> marketMeasures (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.param.valuation.WorkoutInfo wiMarket)
	{
		try {
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapMeasures = rvMeasures
				(valParams, pricerParams, csqc, vcp, wiMarket, priceFromYield (valParams, csqc, vcp,
					wiMarket.date(), wiMarket.factor(), wiMarket.yield()), "");

			org.drip.quant.common.CollectionUtil.MergeWithMain (mapMeasures,
				org.drip.quant.common.CollectionUtil.PrefixKeys (mapMeasures, "Market"));

			return mapMeasures;
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		return null;
	}

	private org.drip.analytics.cashflow.CompositePeriod currentPeriod (
		final int iDate)
	{
		try {
			return _stream.period (_stream.periodIndex (iDate));
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		return null;
	}

	private double priceFromDiscountCurve (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBump,
		final int iDiscountCurveType)
		throws java.lang.Exception
	{
		if (null == valParams || null == csqc ||!org.drip.quant.common.NumberUtil.IsValid (dblWorkoutFactor)
			|| !org.drip.quant.common.NumberUtil.IsValid (dblBump))
			throw new java.lang.Exception ("BondComponent::priceFromDiscountCurve => Invalid Inputs");

		org.drip.state.discount.DiscountCurve dc = null;

		if (PRICE_OFF_OF_FUNDING_CURVE == iDiscountCurveType)
			dc = csqc.fundingState (fundingLabel());
		else if (PRICE_OFF_OF_TREASURY_CURVE == iDiscountCurveType)
			dc = csqc.govvieState (govvieLabel());
		/* else if (PRICE_OFF_OF_OVERNIGHT_CURVE == iDiscountCurveType)
			dfe = csqc.overnightState (overnightLabel()); */

		if (null == dc)
			throw new java.lang.Exception ("BondComponent::priceFromDiscountCurve => No Discount Curve");

		int iValueDate = valParams.valueDate();

		if (iValueDate >= iWorkoutDate)
			throw new java.lang.Exception ("BondComponent::priceFromDiscountCurve => Val date " +
				org.drip.analytics.date.DateUtil.YYYYMMDD (iValueDate) + " greater than Work-out " +
					org.drip.analytics.date.DateUtil.YYYYMMDD (iWorkoutDate));

		double dblPV = 0.;
		boolean bTerminateCouponFlow = false;
		int iCashPayDate = java.lang.Integer.MIN_VALUE;
		double dblFlatForwardRate = java.lang.Double.NaN;
		double dblScalingNotional = java.lang.Double.NaN;

		org.drip.param.valuation.ValuationCustomizationParams vcp = null == _quoteConvention ? null :
			_quoteConvention.valuationCustomizationParams();

		boolean bApplyFlatForward = null == vcp ? false : vcp.applyFlatForwardRate();

		if (null != _notionalSetting && _notionalSetting.priceOffOfOriginalNotional())
			dblScalingNotional = 1.;

		if (0. != dblBump) {
			if (PRICE_OFF_OF_FUNDING_CURVE == iDiscountCurveType)
				dc = (org.drip.state.discount.MergedDiscountForwardCurve) dc.parallelShiftManifestMeasure
					("SwapRate", dblBump);
			else if (PRICE_OFF_OF_TREASURY_CURVE == iDiscountCurveType)
				dc = (org.drip.state.govvie.GovvieCurve) dc.parallelShiftManifestMeasure ("Yield", dblBump);
		}

		if (null == dc)
			throw new java.lang.Exception
				("BondComponent::priceFromDiscountCurve => Cannot shift Discount Curve");

		for (org.drip.analytics.cashflow.CompositePeriod period : couponPeriods()) {
			int iPeriodPayDate = period.payDate();

			if (iPeriodPayDate < iValueDate) continue;

			int iPeriodStartDate = period.startDate();

			if (!org.drip.quant.common.NumberUtil.IsValid (dblScalingNotional))
				dblScalingNotional = notional (iPeriodStartDate);

			int iAccrualEndDate = period.endDate();

			int iNotionalEndDate = period.endDate();

			if (iAccrualEndDate >= iWorkoutDate) {
				bTerminateCouponFlow = true;
				iAccrualEndDate = iWorkoutDate;
				iNotionalEndDate = iWorkoutDate;
			}

			org.drip.analytics.output.CompositePeriodCouponMetrics pcm = couponMetrics (iAccrualEndDate,
				valParams, csqc);

			if (null == pcm)
				throw new java.lang.Exception ("BondComponent::priceFromDiscountCurve => No PCM");

			double dblPeriodAnnuity = dc.df (iPeriodPayDate) * pcm.cumulative();

			double dblPeriodStartNotional = notional (iPeriodStartDate);

			double dblPeriodEndNotional = notional (iNotionalEndDate);

			double dblCouponNotional = dblPeriodStartNotional;

			int iPeriodAmortizationMode = _notionalSetting.periodAmortizationMode();

			if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END == iPeriodAmortizationMode)
				dblCouponNotional = dblPeriodEndNotional;
			else if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_EFFECTIVE ==
				iPeriodAmortizationMode)
				dblCouponNotional = notional (iPeriodStartDate, iNotionalEndDate);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblFlatForwardRate))
				dblFlatForwardRate = pcm.rate();

			dblPV += period.accrualDCF (iAccrualEndDate) * dblPeriodAnnuity * (bApplyFlatForward ?
				dblFlatForwardRate : pcm.rate()) * dblCouponNotional;

			dblPV += (dblPeriodStartNotional - dblPeriodEndNotional) * dblPeriodAnnuity;

			if (bTerminateCouponFlow) break;
		}

		try {
			iCashPayDate = null != _quoteConvention ? _quoteConvention.settleDate (valParams) :
				valParams.cashPayDate();
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();

			iCashPayDate = valParams.cashPayDate();
		}

		return ((dblPV + dblWorkoutFactor * dc.df (iWorkoutDate) * notional (iWorkoutDate)) / dc.df
			(iCashPayDate) - accrued (iValueDate, csqc)) / dblScalingNotional;
	}

	/**
	 * Constructor: Construct an empty bond object
	 */

	public BondComponent()
	{
	}

	@Override public double[] secTreasurySpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
	{
		if (null == valParams || null == csqc) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote> mapTSYQuote
			= csqc.quoteMap();

		if (null == mapTSYQuote || 0 == mapTSYQuote.size()) return null;

		java.lang.String[] astrTreasuryBenchmark = null == _treasuryBenchmarks ? null :
			_treasuryBenchmarks.secondary();

		int iNumTreasuryBenchmark = null == astrTreasuryBenchmark ? 0 : astrTreasuryBenchmark.length;
		double[] adblSecTSYSpread = new double[iNumTreasuryBenchmark];

		if (0 == iNumTreasuryBenchmark) return null;

		for (int i = 0; i < iNumTreasuryBenchmark; ++i) {
			org.drip.param.definition.ProductQuote pqTSYBenchmark = mapTSYQuote.get
				(astrTreasuryBenchmark[i]);

			org.drip.param.definition.Quote q = null == pqTSYBenchmark ? null : pqTSYBenchmark.quote
				("Yield");

			adblSecTSYSpread[i] = null == q ? java.lang.Double.NaN : q.value ("mid");
		}

		return adblSecTSYSpread;
	}

	@Override public double effectiveTreasuryBenchmarkYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		if (null == valParams || null == csqc || !org.drip.quant.common.NumberUtil.IsValid (dblPrice))
			throw new java.lang.Exception
				("BondComponent::effectiveTreasuryBenchmarkYield => Bad val/mkt Params");

		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception
				("BondComponent::effectiveTreasuryBenchmarkYield => Invalid Work-out!");

		java.lang.String strTreasuryBenchmark = null != _treasuryBenchmarks ? _treasuryBenchmarks.primary() :
			null;

		int iValueDate = valParams.valueDate();

		int iWorkoutDate = wi.date();

		if (null == strTreasuryBenchmark || strTreasuryBenchmark.isEmpty())
			strTreasuryBenchmark = org.drip.analytics.support.Helper.BaseTsyBmk (iValueDate, iWorkoutDate);

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.param.definition.ProductQuote> mapTSYQuote
			= csqc.quoteMap();

		org.drip.param.definition.ProductQuote pqTSYBenchmark = null != mapTSYQuote &&
			mapTSYQuote.containsKey (strTreasuryBenchmark) && !strTreasuryBenchmark.isEmpty() ?
				mapTSYQuote.get (strTreasuryBenchmark) : null;

		org.drip.param.definition.Quote q = null != pqTSYBenchmark ? pqTSYBenchmark.quote ("Yield") : null;

		if (null != q) return q.value ("mid");

		org.drip.state.govvie.GovvieCurve gc = csqc.govvieState (govvieLabel());;

		return null == gc ? java.lang.Double.NaN : gc.yield (iWorkoutDate);
	}

	@Override public boolean setTreasuryBenchmark (
		final org.drip.product.params.TreasuryBenchmarks treasuryBenchmarks)
	{
		return null != (_treasuryBenchmarks = treasuryBenchmarks);
	}

	@Override public org.drip.product.params.TreasuryBenchmarks treasuryBenchmark()
	{
		return _treasuryBenchmarks;
	}

	@Override public boolean setIdentifierSet (
		final org.drip.product.params.IdentifierSet idParams)
	{
		return null != (_idParams = idParams);
	}

	@Override public org.drip.product.params.IdentifierSet identifierSet()
	{
		return _idParams;
	}

	@Override public boolean setCouponSetting (
		final org.drip.product.params.CouponSetting couponSetting)
	{
		return null != (_couponSetting = couponSetting);
	}

	@Override public org.drip.product.params.CouponSetting couponSetting()
	{
		return _couponSetting;
	}

	@Override public boolean setFloaterSetting (
		final org.drip.product.params.FloaterSetting fltParams)
	{
		return null == (_floaterSetting = fltParams);
	}

	@Override public org.drip.product.params.FloaterSetting floaterSetting()
	{
		return _floaterSetting;
	}

	@Override public boolean setMarketConvention (
		final org.drip.product.params.QuoteConvention quoteConvention)
	{
		return null == (_quoteConvention = quoteConvention);
	}

	@Override public org.drip.product.params.QuoteConvention marketConvention()
	{
		return _quoteConvention;
	}

	@Override public boolean setCreditSetting (
		final org.drip.product.params.CreditSetting creditSetting)
	{
		return null == (_creditSetting = creditSetting);
	}

	@Override public org.drip.product.params.CreditSetting creditSetting()
	{
		return _creditSetting;
	}

	@Override public boolean setTerminationSetting (
		final org.drip.product.params.TerminationSetting terminationSetting)
	{
		return null == (_terminationSetting = terminationSetting);
	}

	@Override public org.drip.product.params.TerminationSetting terminationSetting()
	{
		return _terminationSetting;
	}

	@Override public boolean setStream (
		final org.drip.product.params.BondStream stream)
	{
		return null != (_stream = stream);
	}

	@Override public org.drip.product.params.BondStream stream()
	{
		return _stream;
	}

	@Override public boolean setNotionalSetting (
		final org.drip.product.params.NotionalSetting notionalSetting)
	{
		return null == (_notionalSetting = notionalSetting);
	}

	@Override public org.drip.product.params.NotionalSetting notionalSetting()
	{
		return _notionalSetting;
	}

	@Override public java.lang.String primaryCode()
	{
		return null == _idParams ? null : "BOND." + _idParams.id();
	}

	@Override public void setPrimaryCode (
		final java.lang.String strCode)
	{
		// _strCode = strCode;
	}

	@Override public java.lang.String[] secondaryCode()
	{
		return new java.lang.String[] {_idParams.id()};
	}

	@Override public java.lang.String isin()
	{
		return null == _idParams ? null : _idParams.isin();
	}

	@Override public java.lang.String cusip()
	{
		return null == _idParams ? null : _idParams.cusip();
	}

	@Override public java.lang.String name()
	{
		return null == _idParams ? null : _idParams.id();
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> couponCurrency()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> mapCouponCurrency = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String>();

		mapCouponCurrency.put (name(), _stream.couponCurrency());

		return mapCouponCurrency;
	}

	@Override public java.lang.String payCurrency()
	{
		return _stream.couponCurrency();
	}

	@Override public java.lang.String principalCurrency()
	{
		return _notionalSetting.denominationCurrency();
	}

	@Override public double notional (
		final int iDate)
		throws java.lang.Exception
	{
		if (null == _notionalSetting || null == _notionalSetting.outstandingFactorSchedule())
			throw new java.lang.Exception ("BondComponent::notional => Bad state/inputs");

		return _notionalSetting.outstandingFactorSchedule().y (iDate);
	}

	@Override public double notional (
		final int iStartDate,
		final int iEndDate)
		throws java.lang.Exception
	{
		if (null == _notionalSetting || null == _notionalSetting.outstandingFactorSchedule())
			throw new java.lang.Exception ("BondComponent::notional => Bad state/inputs");

		return _notionalSetting.outstandingFactorSchedule().y (iStartDate, iEndDate);
	}

	@Override public double initialNotional()
		throws java.lang.Exception
	{
		if (null == _notionalSetting)
			throw new java.lang.Exception ("BondComponent::initialNotional => Bad state/inputs");

		return _notionalSetting.notionalAmount();
	}

	@Override public double recovery (
		final int iDate,
		final org.drip.state.credit.CreditCurve cc)
		throws java.lang.Exception
	{
		if (null == cc) throw new java.lang.Exception ("BondComponent::recovery: Bad state/inputs");

		return _creditSetting.useCurveRecovery() ? cc.recovery (iDate) : _creditSetting.recovery();
	}

	@Override public double recovery (
		final int iStartDate,
		final int iEndDate,
		final org.drip.state.credit.CreditCurve cc)
		throws java.lang.Exception
	{
		if (null == cc) throw new java.lang.Exception ("BondComponent::recovery: Bad state/inputs");

		return _creditSetting.useCurveRecovery() ? cc.effectiveRecovery (iStartDate, iEndDate) :
			_creditSetting.recovery();
	}

	@Override public org.drip.product.params.CreditSetting creditValuationParams()
	{
		return _creditSetting;
	}

	@Override public org.drip.analytics.output.CompositePeriodCouponMetrics couponMetrics (
		final int iAccrualEndDate,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
	{
		org.drip.analytics.cashflow.CompositePeriod cp = currentPeriod (iAccrualEndDate);

		if (null == cp) return null;

		java.util.List<org.drip.analytics.output.UnitPeriodMetrics> lsUPM = new
			java.util.ArrayList<org.drip.analytics.output.UnitPeriodMetrics>();

		try {
			lsUPM.add (new org.drip.analytics.output.UnitPeriodMetrics (cp.startDate(), cp.endDate(),
				cp.couponDCF(), cp.couponMetrics (valParams.valueDate(), csqc).rate(), new
					org.drip.analytics.output.ConvexityAdjustment()));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return org.drip.analytics.output.CompositePeriodCouponMetrics.Create (lsUPM);
	}

	@Override public int freq()
	{
		return couponPeriods().get (0).freq();
	}

	@Override public org.drip.state.identifier.CreditLabel creditLabel()
	{
		if (null == _creditSetting) return null;

		java.lang.String strCreditCurveName = _creditSetting.creditCurveName();

		return null == strCreditCurveName  || strCreditCurveName.isEmpty() ? null :
			org.drip.state.identifier.CreditLabel.Standard (strCreditCurveName);
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
			forwardLabel()
	{
		if (null == _floaterSetting) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>
			mapForwardLabel = new
				org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.ForwardLabel>();

		mapForwardLabel.put (name(), _floaterSetting.fri());

		return mapForwardLabel;
	}

	@Override public org.drip.state.identifier.FundingLabel fundingLabel()
	{
		return org.drip.state.identifier.FundingLabel.Standard (payCurrency());
	}

	@Override public org.drip.state.identifier.GovvieLabel govvieLabel()
	{
		return org.drip.state.identifier.GovvieLabel.Standard
			(org.drip.market.issue.TreasurySettingContainer.CurrencyBenchmarkCode (payCurrency()));
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.FXLabel>
		fxLabel()
	{
		return null;
	}

	@Override public
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.state.identifier.VolatilityLabel>
			volatilityLabel()
	{
		return null;
	}

	@Override public org.drip.analytics.date.JulianDate effectiveDate()
	{
		return _stream.effective();
	}

	@Override public org.drip.analytics.date.JulianDate maturityDate()
	{
		return _stream.maturity();
	}

	@Override public org.drip.analytics.date.JulianDate firstCouponDate()
	{
		try {
			return new org.drip.analytics.date.JulianDate (couponPeriods().get (0).endDate());
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		return null;
	}

	@Override public java.util.List<org.drip.analytics.cashflow.CompositePeriod> couponPeriods()
	{
		return null == _stream ? null : _stream.periods();
	}

	@Override public org.drip.param.valuation.CashSettleParams cashSettleParams()
	{
		return null == _quoteConvention ? null : _quoteConvention.cashSettleParams();
	}

	@Override public java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics> lossFlow (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
	{
		if (null == valParams || null == pricerParams || null == csqc) return null;

		java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics> sLP = new
			java.util.ArrayList<org.drip.analytics.cashflow.LossQuadratureMetrics>();

		for (org.drip.analytics.cashflow.CompositePeriod period : couponPeriods()) {
			if (null == period) continue;

			java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics> sLPSub = period.lossMetrics
				(this, valParams, pricerParams, period.endDate(), csqc);

			if (null != sLPSub) sLP.addAll (sLPSub);
		}

		return sLP;
	}

	@Override public java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics> lossFlowFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
	{
		if (null == valParams || null == pricerParams || null == csqc ||
			!org.drip.quant.common.NumberUtil.IsValid (dblPrice))
			return null;

		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi) return null;

		int iValueDate = valParams.valueDate();

		int iWorkoutDate = wi.date();

		java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics> sLP = new
			java.util.ArrayList<org.drip.analytics.cashflow.LossQuadratureMetrics>();

		for (org.drip.analytics.cashflow.CompositePeriod period : couponPeriods()) {
			if (null == period) continue;

			int iPeriodEndDate = period.endDate();

			if (null == period || iPeriodEndDate < iValueDate) continue;

			if (period.startDate() > iWorkoutDate) break;

			java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics> sLPSub = period.lossMetrics
				(this, valParams, pricerParams, iPeriodEndDate, csqc);

			if (null != sLPSub) sLP.addAll (sLPSub);
		}

		return sLP;
	}

	@Override public boolean isFloater()
	{
		return null == _floaterSetting ? false : true;
	}

	@Override public java.lang.String rateIndex()
	{
		return null == _floaterSetting ? "" : _floaterSetting.fri().fullyQualifiedName();
	}

	@Override public double currentCoupon()
	{
		return null == _floaterSetting ? java.lang.Double.NaN : _floaterSetting.currentFullCoupon();
	}

	@Override public double floatSpread()
	{
		return null == _floaterSetting ? java.lang.Double.NaN : _floaterSetting.spread();
	}

	@Override public java.lang.String ticker()
	{
		return null == _idParams ? null : _idParams.ticker();
	}

	@Override public void setEmbeddedCallSchedule (
		final org.drip.product.params.EmbeddedOptionSchedule eos)
	{
		if (null == eos || eos.isPut()) return;

		_eosCall = new org.drip.product.params.EmbeddedOptionSchedule (eos);
	}

	@Override public void setEmbeddedPutSchedule (
		final org.drip.product.params.EmbeddedOptionSchedule eos)
	{
		if (null == eos || !eos.isPut()) return;

		_eosPut = new org.drip.product.params.EmbeddedOptionSchedule (eos);
	}

	@Override public boolean callable()
	{
		return null != _eosCall;
	}

	@Override public boolean putable()
	{
		return null != _eosPut;
	}

	@Override public boolean sinkable()
	{
		return null == _notionalSetting ? false : true;
	}

	@Override public boolean variableCoupon()
	{
		return null == _couponSetting || null == _couponSetting.couponType() || !"variable".equalsIgnoreCase
			(_couponSetting.couponType()) ? false : true;
	}

	@Override public boolean exercised()
	{
		return null == _terminationSetting ? false : _terminationSetting.exercised();
	}

	@Override public boolean defaulted()
	{
		return null == _terminationSetting ? false : _terminationSetting.defaulted();
	}

	@Override public boolean perpetual()
	{
		return null == _terminationSetting ? false : _terminationSetting.perpetual();
	}

	@Override public boolean tradeable (
		final org.drip.param.valuation.ValuationParams valParams)
		throws java.lang.Exception
	{
		if (null == valParams) throw new java.lang.Exception ("BondComponent::tradeable => invalid Inputs");

		return !_terminationSetting.exercised() && !_terminationSetting.defaulted() && valParams.valueDate()
			< maturityDate().julian();
	}

	@Override public org.drip.product.params.EmbeddedOptionSchedule callSchedule()
	{
		return _eosCall;
	}

	@Override public org.drip.product.params.EmbeddedOptionSchedule putSchedule()
	{
		return _eosPut;
	}

	@Override public java.lang.String couponType()
	{
		return null == _couponSetting ? "" : _couponSetting.couponType();
	}

	@Override public java.lang.String couponDC()
	{
		return null == _stream ? "" : _stream.couponDC();
	}

	@Override public java.lang.String accrualDC()
	{
		return null == _stream ? "" : _stream.accrualDC();
	}

	@Override public java.lang.String maturityType()
	{
		return null == _stream ? "" : maturityType();
	}

	@Override public org.drip.analytics.date.JulianDate finalMaturity()
	{
		return null == _stream ? null : new org.drip.analytics.date.JulianDate (_stream.finalMaturityDate());
	}

	@Override public java.lang.String calculationType()
	{
		return null == _quoteConvention ? "" : _quoteConvention.calculationType();
	}

	@Override public double redemptionValue()
	{
		return null == _quoteConvention ? java.lang.Double.NaN : _quoteConvention.redemptionValue();
	}

	@Override public java.lang.String currency()
	{
		return _stream.couponCurrency();
	}

	@Override public java.lang.String redemptionCurrency()
	{
		return _notionalSetting.denominationCurrency();
	}

	@Override public boolean inFirstCouponPeriod (
		final int iDate)
		throws java.lang.Exception
	{
		return _stream.firstPeriod().contains (iDate);
	}

	@Override public boolean inLastCouponPeriod (
		final int iDate)
		throws java.lang.Exception
	{
		return _stream.lastPeriod().contains (iDate);
	}

	@Override public java.lang.String floatCouponConvention()
	{
		return null == _floaterSetting ? null : _floaterSetting.dayCount();
	}

	@Override public org.drip.analytics.date.JulianDate periodFixingDate (
		final int iValueDate)
	{
		if (null == _floaterSetting || iValueDate >= maturityDate().julian()) return null;

		for (org.drip.analytics.cashflow.CompositePeriod period : couponPeriods()) {
			if (period.payDate() < iValueDate) continue;

			org.drip.analytics.cashflow.ComposableUnitPeriod cup = period.periods().get (0);

			if (!(cup instanceof org.drip.analytics.cashflow.ComposableUnitFloatingPeriod)) continue;

			try {
				return new org.drip.analytics.date.JulianDate
					(((org.drip.analytics.cashflow.ComposableUnitFloatingPeriod)
						(cup)).referenceIndexPeriod().fixingDate());
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();
			}
		}

		return null;
	}

	@Override public org.drip.analytics.date.JulianDate previousCouponDate (
		final org.drip.analytics.date.JulianDate dt)
	{
		if (null == dt || dt.julian() > maturityDate().julian()) return null;

		try {
			int iIndex = _stream.periodIndex (dt.julian());

			if (0 == iIndex) return new org.drip.analytics.date.JulianDate (_stream.period (0).startDate());
			
			org.drip.analytics.cashflow.CompositePeriod period = _stream.period (iIndex - 1);

			return null == period ? null : new org.drip.analytics.date.JulianDate (period.payDate());
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		return null;
	}

	@Override public double previousCouponRate (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		if (null == dt)
			throw new java.lang.Exception ("BondComponent::previousCouponRate => Invalid Inputs");

		org.drip.analytics.cashflow.CompositePeriod period = _stream.period (_stream.periodIndex
			(dt.julian()) - 1);

		if (null == period)
			throw new java.lang.Exception
				("BondComponent::previousCouponRate => Cannot find previous period!");

		org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = couponMetrics (period.endDate(), new
			org.drip.param.valuation.ValuationParams (dt, dt, ""), csqc);

		if (null == cpcm)
			throw new java.lang.Exception
				("BondComponent::previousCouponRate => Invalid previous period metrics!");

		return cpcm.rate();
	}

	@Override public org.drip.analytics.date.JulianDate currentCouponDate (
		final org.drip.analytics.date.JulianDate dt)
	{
		if (null == dt || dt.julian() > maturityDate().julian()) return null;

		try {
			org.drip.analytics.cashflow.CompositePeriod period = _stream.period (_stream.periodIndex
				(dt.julian()));

			return null == period ? null : new org.drip.analytics.date.JulianDate (period.payDate());
		} catch (java.lang.Exception e) {
		}

		return null;
	}

	@Override public org.drip.analytics.date.JulianDate nextCouponDate (
		final org.drip.analytics.date.JulianDate dt)
	{
		if (null == dt || dt.julian() > maturityDate().julian()) return null;

		try {
			org.drip.analytics.cashflow.CompositePeriod period = _stream.period (_stream.periodIndex
				(dt.julian()) + 1);

			return null == period ? null : new org.drip.analytics.date.JulianDate (period.payDate());
		} catch (java.lang.Exception e) {
		}

		return null;
	}

	@Override public org.drip.analytics.output.ExerciseInfo nextValidExerciseDateOfType (
		final org.drip.analytics.date.JulianDate dt,
		final boolean bPut)
	{
		if (null == dt || (bPut && null == _eosPut) || (!bPut && null == _eosCall)) return null;

		org.drip.product.params.EmbeddedOptionSchedule eos = bPut ? _eosPut : _eosCall;

		int[] aiEOSExerciseDates = eos.dates();

		if (null == eos || null == aiEOSExerciseDates) return null;

		int iNumExerciseDates = aiEOSExerciseDates.length;

		if (0 == iNumExerciseDates) return null;

		for (int i = 0; i < iNumExerciseDates; ++i) {
			if (aiEOSExerciseDates[i] - dt.julian() < eos.exerciseNoticePeriod())
				continue;

			try {
				return new org.drip.analytics.output.ExerciseInfo (aiEOSExerciseDates[i], eos.factor (i),
					bPut ? org.drip.param.valuation.WorkoutInfo.WO_TYPE_PUT :
						org.drip.param.valuation.WorkoutInfo.WO_TYPE_CALL);
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();

				return null;
			}
		}

		return null;
	}

	@Override public org.drip.analytics.output.ExerciseInfo nextValidExerciseInfo (
		final org.drip.analytics.date.JulianDate dt)
	{
		if (null == dt) return null;

		org.drip.analytics.output.ExerciseInfo neiNextCall = nextValidExerciseDateOfType (dt, false);

		org.drip.analytics.output.ExerciseInfo neiNextPut = nextValidExerciseDateOfType (dt, true);

		if (null == neiNextCall && null == neiNextPut) {
			try {
				return new org.drip.analytics.output.ExerciseInfo (maturityDate().julian(), 1.,
					org.drip.param.valuation.WorkoutInfo.WO_TYPE_MATURITY);
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();

				return null;
			}
		}

		if (null != neiNextCall && null == neiNextPut) return neiNextCall;

		if (null == neiNextCall && null != neiNextPut) return neiNextPut;

		return neiNextCall.date() < neiNextPut.date() ? neiNextCall : neiNextPut;
	}

	@Override public double currentCouponRate (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		if (null == dt || null == csqc)
			throw new java.lang.Exception ("BondComponent::currentCouponRate => Null val/mkt params!");

		if (null != _floaterSetting) {
			double dblCurrentFullCoupon = _floaterSetting.currentFullCoupon();

			if (!org.drip.quant.common.NumberUtil.IsValid (dblCurrentFullCoupon))
				return dblCurrentFullCoupon;
		}

		org.drip.analytics.output.CompositePeriodCouponMetrics pcm = couponMetrics (dt.julian(), new
			org.drip.param.valuation.ValuationParams (dt, dt, ""), csqc);

		if (null == pcm) throw new java.lang.Exception ("BondComponent::currentCouponRate => Null PCM!");

		return pcm.rate();
	}

	@Override public double nextCouponRate (
		final org.drip.analytics.date.JulianDate dt,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		if (null == dt)
			throw new java.lang.Exception ("BondComponent::nextCouponRate => Null val/mkt params!");

		int iIndex = _stream.periodIndex (dt.julian());

		org.drip.analytics.cashflow.CompositePeriod period = _stream.period (iIndex + 1);

		if (null == period)
			throw new java.lang.Exception ("BondComponent::nextCouponRate => Cannot find next period!");

		org.drip.analytics.output.CompositePeriodCouponMetrics pcm = couponMetrics (period.endDate(), new
			org.drip.param.valuation.ValuationParams (dt, dt, ""), csqc);

		if (null == pcm) throw new java.lang.Exception ("BondComponent::nextCouponRate => Null PCM!");

		return pcm.rate();
	}

	@Override public double accrued (
		final int iDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		org.drip.analytics.date.JulianDate dt = new org.drip.analytics.date.JulianDate (iDate);

		if (iDate >= maturityDate().julian())
			throw new java.lang.Exception ("BondComponent::accrued => Val date " + dt +
				" greater than maturity " + maturityDate());

		org.drip.param.valuation.ValuationParams valParams = new org.drip.param.valuation.ValuationParams
			(dt, dt, "");

		for (org.drip.analytics.cashflow.CompositePeriod period : couponPeriods()) {
			int iEndDate = period.endDate();

			int iStartDate = period.startDate();

			if (iStartDate < iDate && iEndDate >= iDate) {
				org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = couponMetrics (iEndDate,
					valParams, csqc);

				if (null == cpcm) throw new java.lang.Exception ("BondComponent::accrued => No PCM");

				double dblCoupon = cpcm.rate();

				if (!org.drip.quant.common.NumberUtil.IsValid (dblCoupon))
					throw new java.lang.Exception ("BondComponent::accrued => Invalid Coupon For " + dt);

				return period.accrualDCF (iDate) * dblCoupon * notional (iEndDate);
			}
		}

		return 0.;
	}

	@Override public int weightedAverageMaturityDate (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final int iWorkoutDate,
		final double dblWorkoutFactor)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.quant.common.NumberUtil.IsValid (dblWorkoutFactor))
			throw new java.lang.Exception ("BondComponent::weightedAverageMaturityDate => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		if (iValueDate >= iWorkoutDate)
			throw new java.lang.Exception ("BondComponent::weightedAverageMaturityDate => Val date " +
				org.drip.analytics.date.DateUtil.YYYYMMDD (iValueDate) + " greater than Work-out " +
					org.drip.analytics.date.DateUtil.YYYYMMDD (iWorkoutDate));

		double iPeriodEndDate = 0.;
		double dblTotalCashflow = 0.;
		boolean bTerminateCouponFlow = false;
		double dblTimeWeightedTotalCashflow = 0.;
		double dblFlatForwardRate = java.lang.Double.NaN;

		org.drip.param.valuation.ValuationCustomizationParams vcp = null == _quoteConvention ? null :
			_quoteConvention.valuationCustomizationParams();

		boolean bApplyFlatForward = null == vcp ? false : vcp.applyFlatForwardRate();

		for (org.drip.analytics.cashflow.CompositePeriod period : couponPeriods()) {
			int iPeriodPayDate = period.payDate();

			if (iPeriodPayDate < iValueDate) continue;

			int iNotionalEndDate = period.endDate();

			int iPeriodStartDate = period.startDate();

			int iAccrualEndDate = iNotionalEndDate;
			int iAccrualStartDate = iPeriodStartDate > iValueDate ? iPeriodStartDate : iValueDate;

			if (iAccrualEndDate >= iWorkoutDate) {
				bTerminateCouponFlow = true;
				iAccrualEndDate = iWorkoutDate;
				iNotionalEndDate = iWorkoutDate;
			}

			org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = couponMetrics (iAccrualEndDate,
				valParams, csqc);

			if (null == cpcm)
				throw new java.lang.Exception ("BondComponent::weightedAverageMaturityDate => No CPCM");

			double dblPeriodStartNotional = notional (iPeriodStartDate);

			double dblPeriodEndNotional = notional (iNotionalEndDate);

			double dblCouponNotional = dblPeriodStartNotional;

			int iPeriodAmortizationMode = _notionalSetting.periodAmortizationMode();

			if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END == iPeriodAmortizationMode)
				dblCouponNotional = dblPeriodEndNotional;
			else if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_EFFECTIVE ==
				iPeriodAmortizationMode)
				dblCouponNotional = notional (iPeriodStartDate, iNotionalEndDate);

			double dblPeriodTimeWidth = period.accrualDCF (iAccrualEndDate) - period.accrualDCF
				(iAccrualStartDate);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblFlatForwardRate))
				dblFlatForwardRate = cpcm.rate();

			double dblPeriodCashflow = dblPeriodTimeWidth * (bApplyFlatForward ? dblFlatForwardRate :
				cpcm.rate()) * dblCouponNotional + dblPeriodStartNotional - dblPeriodEndNotional;

			dblTotalCashflow += dblPeriodCashflow;
			iPeriodEndDate += (iAccrualEndDate - iAccrualStartDate);
			dblTimeWeightedTotalCashflow += iPeriodEndDate * dblPeriodCashflow;

			if (bTerminateCouponFlow) break;
		}

		double dblTerminalCashflow = dblWorkoutFactor * notional (iWorkoutDate);

		dblTotalCashflow += dblTerminalCashflow;
		dblTimeWeightedTotalCashflow += iPeriodEndDate * dblTerminalCashflow;
		return (int) (dblTimeWeightedTotalCashflow / dblTotalCashflow);
	}

	@Override public int weightedAverageMaturityDate (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		return weightedAverageMaturityDate (valParams, csqc, maturityDate().julian(), 1.);
	}

	@Override public double weightedAverageLife (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final int iWorkoutDate,
		final double dblWorkoutFactor)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.quant.common.NumberUtil.IsValid (dblWorkoutFactor))
			throw new java.lang.Exception ("BondComponent::weightedAverageLife => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		if (iValueDate >= iWorkoutDate)
			throw new java.lang.Exception ("BondComponent::weightedAverageLife => Val date " +
				org.drip.analytics.date.DateUtil.YYYYMMDD (iValueDate) + " greater than Work-out " +
					org.drip.analytics.date.DateUtil.YYYYMMDD (iWorkoutDate));

		double dblTotalCashflow = 0.;
		double dblPeriodEndTime = 0.;
		boolean bTerminateCouponFlow = false;
		double dblTimeWeightedTotalCashflow = 0.;
		double dblFlatForwardRate = java.lang.Double.NaN;

		org.drip.param.valuation.ValuationCustomizationParams vcp = null == _quoteConvention ? null :
			_quoteConvention.valuationCustomizationParams();

		boolean bApplyFlatForward = null == vcp ? false : vcp.applyFlatForwardRate();

		for (org.drip.analytics.cashflow.CompositePeriod period : couponPeriods()) {
			int iPeriodPayDate = period.payDate();

			if (iPeriodPayDate < iValueDate) continue;

			int iNotionalEndDate = period.endDate();

			int iPeriodStartDate = period.startDate();

			int iAccrualEndDate = iNotionalEndDate;
			int iAccrualStartDate = iPeriodStartDate > iValueDate ? iPeriodStartDate : iValueDate;

			if (iAccrualEndDate >= iWorkoutDate) {
				bTerminateCouponFlow = true;
				iAccrualEndDate = iWorkoutDate;
				iNotionalEndDate = iWorkoutDate;
			}

			org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = couponMetrics (iAccrualEndDate,
				valParams, csqc);

			if (null == cpcm)
				throw new java.lang.Exception ("BondComponent::weightedAverageLife => No CPCM");

			double dblPeriodStartNotional = notional (iPeriodStartDate);

			double dblPeriodEndNotional = notional (iNotionalEndDate);

			double dblCouponNotional = dblPeriodStartNotional;

			int iPeriodAmortizationMode = _notionalSetting.periodAmortizationMode();

			if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END == iPeriodAmortizationMode)
				dblCouponNotional = dblPeriodEndNotional;
			else if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_EFFECTIVE ==
				iPeriodAmortizationMode)
				dblCouponNotional = notional (iPeriodStartDate, iNotionalEndDate);

			double dblPeriodTimeWidth = period.accrualDCF (iAccrualEndDate) - period.accrualDCF
				(iAccrualStartDate);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblFlatForwardRate))
				dblFlatForwardRate = cpcm.rate();

			double dblPeriodCashflow = dblPeriodTimeWidth * (bApplyFlatForward ? dblFlatForwardRate :
				cpcm.rate()) * dblCouponNotional + dblPeriodStartNotional - dblPeriodEndNotional;

			dblTotalCashflow += dblPeriodCashflow;
			dblPeriodEndTime += dblPeriodTimeWidth;
			dblTimeWeightedTotalCashflow += dblPeriodEndTime * dblPeriodCashflow;

			if (bTerminateCouponFlow) break;
		}

		double dblTerminalCashflow = dblWorkoutFactor * notional (iWorkoutDate);

		dblTotalCashflow += dblTerminalCashflow;
		dblTimeWeightedTotalCashflow += dblPeriodEndTime * dblTerminalCashflow;
		return dblTimeWeightedTotalCashflow / dblTotalCashflow;
	}

	@Override public double weightedAverageLife (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		return weightedAverageLife (valParams, csqc, maturityDate().julian(), 1.);
	}

	@Override public double weightedAverageLifePrincipalOnly (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final int iWorkoutDate,
		final double dblWorkoutFactor)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.quant.common.NumberUtil.IsValid (dblWorkoutFactor))
			throw new java.lang.Exception
				("BondComponent::weightedAverageLifePrincipalOnly => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		if (iValueDate >= iWorkoutDate)
			throw new java.lang.Exception ("BondComponent::weightedAverageLifePrincipalOnly => Val date " +
				org.drip.analytics.date.DateUtil.YYYYMMDD (iValueDate) + " greater than Work-out " +
					org.drip.analytics.date.DateUtil.YYYYMMDD (iWorkoutDate));

		double dblTotalCashflow = 0.;
		double dblPeriodEndTime = 0.;
		boolean bTerminateCouponFlow = false;
		double dblTimeWeightedTotalCashflow = 0.;

		for (org.drip.analytics.cashflow.CompositePeriod period : couponPeriods()) {
			int iPeriodPayDate = period.payDate();

			if (iPeriodPayDate < iValueDate) continue;

			int iNotionalEndDate = period.endDate();

			int iPeriodStartDate = period.startDate();

			int iAccrualEndDate = iNotionalEndDate;
			int iAccrualStartDate = iPeriodStartDate > iValueDate ? iPeriodStartDate : iValueDate;

			if (iAccrualEndDate >= iWorkoutDate) {
				bTerminateCouponFlow = true;
				iAccrualEndDate = iWorkoutDate;
				iNotionalEndDate = iWorkoutDate;
			}

			double dblPeriodStartNotional = notional (iPeriodStartDate);

			double dblPeriodEndNotional = notional (iNotionalEndDate);

			double dblPeriodTimeWidth = period.accrualDCF (iAccrualEndDate) - period.accrualDCF
				(iAccrualStartDate);

			double dblPeriodCashflow = dblPeriodStartNotional - dblPeriodEndNotional;
			dblTimeWeightedTotalCashflow += dblPeriodEndTime * dblPeriodCashflow;
			dblTotalCashflow += dblPeriodCashflow;
			dblPeriodEndTime += dblPeriodTimeWidth;

			if (bTerminateCouponFlow) break;
		}

		double dblTerminalCashflow = dblWorkoutFactor * notional (iWorkoutDate);

		dblTotalCashflow += dblTerminalCashflow;
		dblTimeWeightedTotalCashflow += dblPeriodEndTime * dblTerminalCashflow;
		return dblTimeWeightedTotalCashflow / dblTotalCashflow;
	}

	@Override public double weightedAverageLifePrincipalOnly (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		return weightedAverageLifePrincipalOnly (valParams, csqc, maturityDate().julian(), 1.);
	}

	@Override public double weightedAverageLifeCouponOnly (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final int iWorkoutDate,
		final double dblWorkoutFactor)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.quant.common.NumberUtil.IsValid (dblWorkoutFactor))
			throw new java.lang.Exception ("BondComponent::weightedAverageLifeCouponOnly => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		if (iValueDate >= iWorkoutDate)
			throw new java.lang.Exception ("BondComponent::weightedAverageLifeCouponOnly => Val date " +
				org.drip.analytics.date.DateUtil.YYYYMMDD (iValueDate) + " greater than Work-out " +
					org.drip.analytics.date.DateUtil.YYYYMMDD (iWorkoutDate));

		double dblTotalCashflow = 0.;
		double dblPeriodEndTime = 0.;
		boolean bTerminateCouponFlow = false;
		double dblTimeWeightedTotalCashflow = 0.;
		double dblFlatForwardRate = java.lang.Double.NaN;

		org.drip.param.valuation.ValuationCustomizationParams vcp = null == _quoteConvention ? null :
			_quoteConvention.valuationCustomizationParams();

		boolean bApplyFlatForward = null == vcp ? false : vcp.applyFlatForwardRate();

		for (org.drip.analytics.cashflow.CompositePeriod period : couponPeriods()) {
			int iPeriodPayDate = period.payDate();

			if (iPeriodPayDate < iValueDate) continue;

			int iNotionalEndDate = period.endDate();

			int iPeriodStartDate = period.startDate();

			int iAccrualEndDate = iNotionalEndDate;
			int iAccrualStartDate = iPeriodStartDate > iValueDate ? iPeriodStartDate : iValueDate;

			if (iAccrualEndDate >= iWorkoutDate) {
				bTerminateCouponFlow = true;
				iAccrualEndDate = iWorkoutDate;
				iNotionalEndDate = iWorkoutDate;
			}

			org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = couponMetrics (iAccrualEndDate,
				valParams, csqc);

			if (null == cpcm)
				throw new java.lang.Exception ("BondComponent::weightedAverageLifeCouponOnly => No CPCM");

			double dblPeriodStartNotional = notional (iPeriodStartDate);

			double dblPeriodEndNotional = notional (iNotionalEndDate);

			double dblCouponNotional = dblPeriodStartNotional;

			int iPeriodAmortizationMode = _notionalSetting.periodAmortizationMode();

			if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END == iPeriodAmortizationMode)
				dblCouponNotional = dblPeriodEndNotional;
			else if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_EFFECTIVE ==
				iPeriodAmortizationMode)
				dblCouponNotional = notional (iPeriodStartDate, iNotionalEndDate);

			double dblPeriodTimeWidth = period.accrualDCF (iAccrualEndDate) - period.accrualDCF
				(iAccrualStartDate);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblFlatForwardRate))
				dblFlatForwardRate = cpcm.rate();

			double dblPeriodCashflow = dblPeriodTimeWidth * (bApplyFlatForward ? dblFlatForwardRate :
				cpcm.rate()) * dblCouponNotional;

			dblTotalCashflow += dblPeriodCashflow;
			dblPeriodEndTime += dblPeriodTimeWidth;
			dblTimeWeightedTotalCashflow += dblPeriodEndTime * dblPeriodCashflow;

			if (bTerminateCouponFlow) break;
		}

		return dblTimeWeightedTotalCashflow / dblTotalCashflow;
	}

	@Override public double weightedAverageLifeCouponOnly (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		return weightedAverageLifeCouponOnly (valParams, csqc, maturityDate().julian(), 1.);
	}

	@Override public double weightedAverageLifeLossOnly (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final int iWorkoutDate,
		final double dblWorkoutFactor)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.quant.common.NumberUtil.IsValid (dblWorkoutFactor))
			throw new java.lang.Exception ("BondComponent::weightedAverageLifeLossOnly => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		org.drip.state.credit.CreditCurve cc = csqc.creditState (creditLabel());

		if (iValueDate >= iWorkoutDate || null == cc)
			throw new java.lang.Exception ("BondComponent::weightedAverageLifeLossOnly => Invalid Inputs");

		double dblPeriodEndTime = 0.;
		double dblTotalLossCashflow = 0.;
		boolean bTerminateCouponFlow = false;
		double dblTimeWeightedTotalLossCashflow = 0.;
		double dblFlatForwardRate = java.lang.Double.NaN;

		org.drip.param.valuation.ValuationCustomizationParams vcp = null == _quoteConvention ? null :
			_quoteConvention.valuationCustomizationParams();

		boolean bApplyFlatForward = null == vcp ? false : vcp.applyFlatForwardRate();

		for (org.drip.analytics.cashflow.CompositePeriod period : couponPeriods()) {
			int iPeriodPayDate = period.payDate();

			if (iPeriodPayDate < iValueDate) continue;

			int iNotionalEndDate = period.endDate();

			int iPeriodStartDate = period.startDate();

			int iAccrualEndDate = iNotionalEndDate;
			int iAccrualStartDate = iPeriodStartDate > iValueDate ? iPeriodStartDate : iValueDate;

			if (iAccrualEndDate >= iWorkoutDate) {
				bTerminateCouponFlow = true;
				iAccrualEndDate = iWorkoutDate;
				iNotionalEndDate = iWorkoutDate;
			}

			org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = couponMetrics (iAccrualEndDate,
				valParams, csqc);

			if (null == cpcm)
				throw new java.lang.Exception ("BondComponent::weightedAverageLifeLossOnly => No CPCM");

			double dblPeriodStartNotional = notional (iPeriodStartDate);

			double dblPeriodEndNotional = notional (iNotionalEndDate);

			double dblCouponNotional = dblPeriodStartNotional;

			int iPeriodAmortizationMode = _notionalSetting.periodAmortizationMode();

			if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END == iPeriodAmortizationMode)
				dblCouponNotional = dblPeriodEndNotional;
			else if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_EFFECTIVE ==
				iPeriodAmortizationMode)
				dblCouponNotional = notional (iPeriodStartDate, iNotionalEndDate);

			double dblPeriodTimeWidth = period.accrualDCF (iAccrualEndDate) - period.accrualDCF
				(iAccrualStartDate);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblFlatForwardRate))
				dblFlatForwardRate = cpcm.rate();

			double dblPeriodLossCashflow = (dblPeriodTimeWidth * (bApplyFlatForward ? dblFlatForwardRate :
				cpcm.rate()) * dblCouponNotional + dblPeriodStartNotional - dblPeriodEndNotional) *
					cc.survival (iPeriodPayDate) * (1. - cc.effectiveRecovery (iPeriodStartDate,
						period.endDate()));

			dblPeriodEndTime += dblPeriodTimeWidth;
			dblTotalLossCashflow += dblPeriodLossCashflow;
			dblTimeWeightedTotalLossCashflow += dblPeriodEndTime * dblPeriodLossCashflow;

			if (bTerminateCouponFlow) break;
		}

		double dblTerminalLossCashflow = dblWorkoutFactor * notional (iWorkoutDate) * cc.survival
			(iWorkoutDate) * (1. - cc.recovery (iWorkoutDate));

		dblTotalLossCashflow += dblTerminalLossCashflow;
		dblTimeWeightedTotalLossCashflow += dblPeriodEndTime * dblTerminalLossCashflow;
		return dblTimeWeightedTotalLossCashflow / dblTotalLossCashflow;
	}

	@Override public double weightedAverageLifeLossOnly (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		return weightedAverageLifeLossOnly (valParams, csqc, maturityDate().julian(), 1.);
	}

	@Override public double priceFromZeroCurve (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iZeroCurveBaseDC,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBump)
		throws java.lang.Exception
	{
		if (null == valParams)
			throw new java.lang.Exception ("BondComponent::priceFromZeroCurve => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		if (iValueDate >= iWorkoutDate || null == csqc || !org.drip.quant.common.NumberUtil.IsValid
			(dblWorkoutFactor) || !org.drip.quant.common.NumberUtil.IsValid (dblBump))
			throw new java.lang.Exception ("BondComponent::priceFromZeroCurve => Invalid Inputs " + dblBump);

		double dblPV = 0.;
		boolean bTerminateCouponFlow = false;
		boolean bApplyFlatForwardRate = false;
		org.drip.state.discount.ZeroCurve zc = null;
		int iCashPayDate = java.lang.Integer.MIN_VALUE;
		double dblFlatForwardRate = java.lang.Double.NaN;
		double dblScalingNotional = java.lang.Double.NaN;

		if (null != vcp)
			bApplyFlatForwardRate = vcp.applyFlatForwardRate();
		else {
			org.drip.param.valuation.ValuationCustomizationParams vcpQuote =
				_quoteConvention.valuationCustomizationParams();

			if (null != vcpQuote) bApplyFlatForwardRate = vcpQuote.applyFlatForwardRate();
		}

		org.drip.state.discount.DiscountCurve dcBase = ZERO_OFF_OF_RATES_INSTRUMENTS_DISCOUNT_CURVE ==
			iZeroCurveBaseDC ? csqc.fundingState (fundingLabel()) : csqc.govvieState (govvieLabel());

		if (null == dcBase)
			throw new java.lang.Exception ("BondComponent::priceFromZeroCurve => Invalid Discount Curve");

		try {
			iCashPayDate = null != _quoteConvention ? _quoteConvention.settleDate (valParams) :
				valParams.cashPayDate();
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();

			iCashPayDate = valParams.cashPayDate();
		}

		if (null != _notionalSetting && _notionalSetting.priceOffOfOriginalNotional())
			dblScalingNotional = 1.;

		java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCompositePeriod = couponPeriods();

		try {
			zc = org.drip.state.curve.DerivedZeroRate.FromBaseCurve (freq(), couponDC(), currency(),
				_stream.couponEOMAdjustment(), lsCompositePeriod, iWorkoutDate, iValueDate, iCashPayDate,
					dcBase, dblBump, null == vcp ? (null == _quoteConvention ? null :
						_quoteConvention.valuationCustomizationParams()) : vcp, new
							org.drip.spline.params.SegmentCustomBuilderControl
								(org.drip.spline.stretch.MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
									new org.drip.spline.basis.PolynomialFunctionSetParams (2),
										org.drip.spline.params.SegmentInelasticDesignControl.Create (0, 2),
											new org.drip.spline.params.ResponseScalingShapeControl (true, new
												org.drip.function.r1tor1.QuadraticRationalShapeControl (0.)),
													null));
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		if (null == zc)
			throw new java.lang.Exception ("BondComponent::priceFromZeroCurve => Cannot create shifted ZC");

		for (org.drip.analytics.cashflow.CompositePeriod period : lsCompositePeriod) {
			int iPeriodPayDate = period.payDate();

			if (iPeriodPayDate < iValueDate) continue;

			int iPeriodStartDate = period.startDate();

			double dblPeriodStartNotional = notional (iPeriodStartDate);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblScalingNotional))
				dblScalingNotional = dblPeriodStartNotional;

			int iAccrualEndDate = period.endDate();

			int iNotionalEndDate = iAccrualEndDate;
			double dblCouponNotional = dblPeriodStartNotional;

			double dblPeriodEndNotional = notional (iNotionalEndDate);

			if (iAccrualEndDate >= iWorkoutDate) {
				bTerminateCouponFlow = true;
				iAccrualEndDate = iWorkoutDate;
				iNotionalEndDate = iWorkoutDate;
			}

			if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END ==
				_notionalSetting.periodAmortizationMode())
				dblCouponNotional = dblPeriodEndNotional;
			else if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_EFFECTIVE ==
				_notionalSetting.periodAmortizationMode())
				dblCouponNotional = notional (iPeriodStartDate, iNotionalEndDate);

			org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = couponMetrics (iValueDate,
				valParams, csqc);

			if (null == cpcm) throw new java.lang.Exception ("BondComponent::priceFromZeroCurve => No PCM");

			if (!org.drip.quant.common.NumberUtil.IsValid (dblFlatForwardRate))
				dblFlatForwardRate = cpcm.rate();

			dblPV += (period.accrualDCF (iAccrualEndDate) * (bApplyFlatForwardRate ? dblFlatForwardRate :
				cpcm.rate()) * dblCouponNotional + dblPeriodStartNotional - dblPeriodEndNotional) * zc.df
					(iPeriodPayDate);

			if (bTerminateCouponFlow) break;
		}

		return ((dblPV + dblWorkoutFactor * zc.df (iWorkoutDate) * notional (iWorkoutDate)) /
			zc.df (iCashPayDate) - accrued (iValueDate, csqc)) / dblScalingNotional;
	}

	@Override public double priceFromFundingCurve (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBump)
		throws java.lang.Exception
	{
		return priceFromDiscountCurve (valParams, csqc, iWorkoutDate, dblWorkoutFactor, dblBump,
			PRICE_OFF_OF_FUNDING_CURVE);
	}

	@Override public double priceFromTreasuryCurve (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBump)
		throws java.lang.Exception
	{
		return priceFromDiscountCurve (valParams, csqc, iWorkoutDate, dblWorkoutFactor, dblBump,
			PRICE_OFF_OF_TREASURY_CURVE);
	}

	@Override public double priceFromCreditCurve (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis,
		final boolean bFlat)
		throws java.lang.Exception
	{
		if (null == valParams || null == csqc || !org.drip.quant.common.NumberUtil.IsValid (dblWorkoutFactor)
			|| !org.drip.quant.common.NumberUtil.IsValid (dblCreditBasis) || null == _creditSetting)
			throw new java.lang.Exception ("BondComponent::priceFromCreditCurve => Invalid inputs");

		org.drip.state.credit.CreditCurve ccIn = csqc.creditState (creditLabel());

		if (null == ccIn)
			throw new java.lang.Exception ("BondComponent::priceFromCreditCurve => Invalid inputs");

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqc.fundingState (fundingLabel());

		if (null == dcFunding)
			throw new java.lang.Exception ("BondComponent::priceFromCreditCurve => No funding curve");

		int iValueDate = valParams.valueDate();

		if (iValueDate >= iWorkoutDate)
			throw new java.lang.Exception ("BondComponent::priceFromCreditCurve => Val date " +
				org.drip.analytics.date.DateUtil.YYYYMMDD (iValueDate) + " greater than Work-out " +
					org.drip.analytics.date.DateUtil.YYYYMMDD (iWorkoutDate));

		double dblRecoveryToUse = !_creditSetting.useCurveRecovery() ? _creditSetting.recovery() :
			java.lang.Double.NaN;

		org.drip.state.credit.CreditCurve cc = bFlat ? ccIn.flatCurve (dblCreditBasis, true,
			dblRecoveryToUse) : (org.drip.state.credit.CreditCurve) ccIn.parallelShiftManifestMeasure
				("SwapRate", dblCreditBasis);

		if (null == cc)
			throw new java.lang.Exception
				("BondComponent::priceFromCreditCurve => Cannot create adjusted Curve");

		double dblPV = 0.;
		double dblScalingNotional = 1.;
		boolean bTerminateCashFlow = false;
		int iCashPayDate = java.lang.Integer.MIN_VALUE;
		double dblFlatForwardRate = java.lang.Double.NaN;

		org.drip.param.valuation.ValuationCustomizationParams vcp = null == _quoteConvention ? null :
			_quoteConvention.valuationCustomizationParams();

		boolean bApplyFlatForwardRate = null == vcp ? false : vcp.applyFlatForwardRate();

		org.drip.param.pricer.CreditPricerParams pricerParams = new org.drip.param.pricer.CreditPricerParams
			(7, null, false, s_iDiscretizationScheme);

		int iLossPayLag = _creditSetting.lossPayLag();

		boolean bAccrualOnDefault = _creditSetting.accrualOnDefault();

		int iPeriodAmortizationMode = _notionalSetting.periodAmortizationMode();

		for (org.drip.analytics.cashflow.CompositePeriod period : couponPeriods()) {
			if (bTerminateCashFlow) continue;

			int iPeriodPayDate = period.payDate();

			if (iPeriodPayDate < iValueDate) continue;

			int iPeriodEndDate = period.endDate();

			if (iPeriodEndDate >= iWorkoutDate) {
				bTerminateCashFlow = true;
				iPeriodEndDate = iWorkoutDate;
			}

			int iPeriodStartDate = period.startDate();

			if (iPeriodStartDate < iValueDate) iPeriodStartDate = iValueDate;

			org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = couponMetrics (iPeriodEndDate,
				valParams, csqc);

			if (null == cpcm)
				throw new java.lang.Exception ("BondComponent::priceFromCreditCurve => No PCM");

			if (!org.drip.quant.common.NumberUtil.IsValid (dblFlatForwardRate))
				dblFlatForwardRate = cpcm.rate();

			double dblPeriodCoupon = bApplyFlatForwardRate ? dblFlatForwardRate : cpcm.rate();

			double dblPeriodStartNotional = notional (iPeriodStartDate);

			double dblPeriodEndNotional = notional (iPeriodEndDate);

			double dblCouponNotional = dblPeriodStartNotional;

			if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END == iPeriodAmortizationMode)
				dblCouponNotional = notional (iPeriodEndDate);
			else if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_EFFECTIVE ==
				iPeriodAmortizationMode)
				dblCouponNotional = notional (iPeriodStartDate, iPeriodEndDate);

			dblPV += (period.accrualDCF (iPeriodEndDate) * dblPeriodCoupon * dblCouponNotional +
				dblPeriodStartNotional - dblPeriodEndNotional) * dcFunding.df (iPeriodPayDate) *
					cpcm.cumulative() * cc.survival (iPeriodEndDate);

			for (org.drip.analytics.cashflow.LossQuadratureMetrics lqm : period.lossMetrics (this, valParams,
				pricerParams, iPeriodEndDate, csqc)) {
				if (null == lqm) continue;

				int iSubPeriodEndDate = lqm.endDate();

				int iSubPeriodStartDate = lqm.startDate();

				double dblSubPeriodDF = dcFunding.effectiveDF (iSubPeriodStartDate + iLossPayLag,
					iSubPeriodEndDate + iLossPayLag);

				double dblSubPeriodNotional = notional (iSubPeriodStartDate, iSubPeriodEndDate);

				double dblSubPeriodSurvival = cc.survival (iSubPeriodStartDate) - cc.survival
					(iSubPeriodEndDate);

				if (bAccrualOnDefault)
					dblPV += 0.0001 * lqm.accrualDCF() * dblSubPeriodSurvival * dblSubPeriodDF *
						dblSubPeriodNotional * dblPeriodCoupon;

				dblPV += (_creditSetting.useCurveRecovery() ? cc.effectiveRecovery (iSubPeriodStartDate,
					iSubPeriodEndDate) : _creditSetting.recovery()) * dblSubPeriodSurvival *
						dblSubPeriodNotional * dblSubPeriodDF;
			}
		}

		try {
			iCashPayDate = null == _quoteConvention ? valParams.cashPayDate() : _quoteConvention.settleDate
				(valParams);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();

			iCashPayDate = valParams.cashPayDate();
		}

		if (!_notionalSetting.priceOffOfOriginalNotional()) dblScalingNotional = notional (iWorkoutDate);

		return ((dblPV + dblWorkoutFactor * dcFunding.df (iWorkoutDate) * cc.survival (iWorkoutDate) *
			notional (iWorkoutDate)) / dcFunding.df (iCashPayDate) - accrued (iValueDate, csqc)) /
				dblScalingNotional;
	}

	@Override public double aswFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return aswFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromBondBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double aswFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return aswFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double aswFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::aswFromBondBasisToOptimalExercise => " +
				"Cannot calc ASW from Bond Basis to Optimal Exercise for bonds w emb option");

		return aswFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double aswFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return aswFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromCreditBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double aswFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return aswFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double aswFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::aswFromCreditBasisToOptimalExercise => " +
				"Cannot calc ASW from Credit Basis to Optimal Exercise for bonds w emb option");

		return aswFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double aswFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return aswFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromDiscountMargin
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblDiscountMargin));
	}

	@Override public double aswFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return aswFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1., dblDiscountMargin);
	}

	@Override public double aswFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::aswFromDiscountMarginToOptimalExercise => " +
				"Cannot calc ASW from Discount Margin to optimal exercise for bonds w emb option");

		return aswFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1., dblDiscountMargin);
	}

	@Override public double aswFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return aswFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromESpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double aswFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return aswFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double aswFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::aswFromESpreadToOptimalExercise => " +
				"Cannot calc ASW from E Spread to optimal exercise for bonds w emb option");

		return aswFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double aswFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return aswFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromGSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double aswFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return aswFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double aswFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::aswFromGSpreadToOptimalExercise => " +
				"Cannot calc ASW from G Spread to optimal exercise for bonds w emb option");

		return aswFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double aswFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return aswFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromISpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double aswFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return aswFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double aswFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::aswFromISpreadToOptimalExercise => " +
				"Cannot calc ASW from I Spread to optimal exercise for bonds w emb option");

		return aswFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double aswFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return aswFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromJSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double aswFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return aswFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double aswFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::aswFromJSpreadToOptimalExercise => " +
				"Cannot calc ASW from J Spread to optimal exercise for bonds w emb option");

		return aswFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double aswFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return aswFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromOAS (valParams,
			csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double aswFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return aswFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double aswFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::aswFromOASToOptimalExercise => " +
				"Cannot calc ASW from OAS to optimal exercise for bonds w emb option");

		return aswFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double aswFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return aswFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromPECS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double aswFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return aswFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double aswFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::aswFromPECSToOptimalExercise => " +
				"Cannot calc ASW from PECS to optimal exercise for bonds w emb option");

		return aswFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double aswFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		if (null == valParams || null == csqc || !org.drip.quant.common.NumberUtil.IsValid (dblWorkoutFactor)
			|| !org.drip.quant.common.NumberUtil.IsValid (dblPrice))
			throw new java.lang.Exception ("BondComponent::aswFromPrice => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		if (iValueDate >= iWorkoutDate)
			throw new java.lang.Exception ("BondComponent::aswFromPrice => Invalid Inputs");

		org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = couponMetrics (iValueDate, valParams,
			csqc);

		if (null == cpcm) throw new java.lang.Exception ("BondComponent::aswFromPrice => No CPCM");

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqc.fundingState (fundingLabel());

		if (null == dcFunding)
			throw new java.lang.Exception ("BondComponent::aswFromPrice => Invalid Inputs");

		return cpcm.rate() - dcFunding.estimateManifestMeasure ("SwapRate", iWorkoutDate) + 0.0001 *
			(dblWorkoutFactor - dblPrice) / dcFunding.parSwapDV01 (iWorkoutDate);
	}

	@Override public double aswFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return aswFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double aswFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception
				("BondComponent::aswFromPriceToOptimalExercise => Can't determine Optimal Work-out");

		return aswFromPrice (valParams, csqc, vcp, wi.date(), wi.factor(), dblPrice);
	}

	@Override public double aswFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return aswFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromTSYSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double aswFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return aswFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double aswFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::aswFromTSYSpreadToOptimalExercise => " +
				"Cannot calc ASW from TSY Spread to optimal exercise for bonds w emb option");

		return aswFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double aswFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		return aswFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromYield (valParams,
			csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYield));
	}

	@Override public double aswFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return aswFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double aswFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::aswFromYieldToOptimalExercise => " +
				"Cannot calc ASW from Yield to optimal exercise for bonds w emb option");

		return aswFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double aswFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return aswFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromYieldSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double aswFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return aswFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double aswFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::aswFromYieldSpreadToOptimalExercise => " +
				"Cannot calc ASW from Yield Spread to optimal exercise for bonds w emb option");

		return aswFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double aswFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return aswFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromZSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblZSpread));
	}

	@Override public double aswFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return aswFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double aswFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::aswFromZSpreadToOptimalExercise => " +
				"Cannot calc ASW from Yield Spread to optimal exercise for bonds w emb option");

		return aswFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double bondBasisFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return bondBasisFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromASW
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double bondBasisFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return bondBasisFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double bondBasisFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::bondBasisFromASWToOptimalExercise => " +
				"Cannot calc Bond Basis from ASW to optimal exercise for bonds w emb option");

		return bondBasisFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double bondBasisFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return bondBasisFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromCreditBasis (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double bondBasisFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return bondBasisFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double bondBasisFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::bondBasisFromCreditBasisToOptimalExercise => " +
				"Cannot calc Bond Basis from Credit Basis to optimal exercise for bonds w emb option");

		return bondBasisFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double bondBasisFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return bondBasisFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
				dblDiscountMargin));
	}

	@Override public double bondBasisFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return bondBasisFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double bondBasisFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::bondBasisFromDiscountMarginToOptimalExercise " +
				"=> Cant calc Bond Basis from Discount Margin to optimal exercise for bonds w emb option");

		return bondBasisFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double bondBasisFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return bondBasisFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromESpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double bondBasisFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return bondBasisFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double bondBasisFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::bondBasisFromESpreadToOptimalExercise => " +
				"Cant calc Bond Basis from E Spread to optimal exercise for bonds w emb option");

		return bondBasisFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double bondBasisFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return bondBasisFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromGSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double bondBasisFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return bondBasisFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double bondBasisFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::bondBasisFromGSpreadToOptimalExercise => " +
				"Cant calc Bond Basis from G Spread to optimal exercise for bonds w emb option");

		return bondBasisFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double bondBasisFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return bondBasisFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromISpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double bondBasisFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return bondBasisFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double bondBasisFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::bondBasisFromISpreadToOptimalExercise => " +
				"Cant calc Bond Basis from I Spread to optimal exercise for bonds w emb option");

		return bondBasisFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double bondBasisFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return bondBasisFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromJSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double bondBasisFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return bondBasisFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double bondBasisFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::bondBasisFromJSpreadToOptimalExercise => " +
				"Cant calc Bond Basis from J Spread to optimal exercise for bonds w emb option");

		return bondBasisFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double bondBasisFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return bondBasisFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromOAS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double bondBasisFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return bondBasisFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double bondBasisFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::bondBasisFromOASToOptimalExercise => " +
				"Cant calc Bond Basis from OAS to optimal exercise for bonds w emb option");

		return bondBasisFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double bondBasisFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return bondBasisFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromPECS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double bondBasisFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return bondBasisFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double bondBasisFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::bondBasisFromPECSToOptimalExercise => " +
				"Cant calc Bond Basis from PECS to optimal exercise for bonds w emb option");

		return bondBasisFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double bondBasisFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return bondBasisFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromPrice
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPrice));
	}

	@Override public double bondBasisFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return bondBasisFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double bondBasisFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);
		
		if (null == wi)
			throw new java.lang.Exception
				("BondComponent::bondBasisFromPriceToOptimalExercise => cant calc Work-out info");

		return bondBasisFromPrice (valParams, csqc, vcp, wi.date(), wi.factor(), dblPrice);
	}

	@Override public double bondBasisFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return bondBasisFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromTSYSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double bondBasisFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return bondBasisFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double bondBasisFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::bondBasisFromTSYSpreadToOptimalExercise => " +
				"Cant calc Bond Basis from TSY Spread to optimal exercise for bonds w emb option");

		return bondBasisFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double bondBasisFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblYield))
			throw new java.lang.Exception ("BondComponent::bondBasisFromYield => Invalid inputs");

		return dblYield - yieldFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromFundingCurve (valParams, csqc, iWorkoutDate, dblWorkoutFactor, 0.));
	}

	@Override public double bondBasisFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return bondBasisFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double bondBasisFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::bondBasisFromYieldToOptimalExercise => " +
				"Cant calc Bond Basis from Yield to optimal exercise for bonds w emb option");

		return bondBasisFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double bondBasisFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return bondBasisFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromYieldSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double bondBasisFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return bondBasisFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double bondBasisFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::bondBasisFromYieldSpreadToOptimalExercise " +
				"=> Cant calc Bond Basis from Yield Spread to optimal exercise for bonds w emb option");

		return bondBasisFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double bondBasisFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return bondBasisFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromZSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblZSpread));
	}

	@Override public double bondBasisFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return bondBasisFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double bondBasisFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::bondBasisFromZSpreadToOptimalExercise => " +
				"Cant calc Bond Basis from Z Spread to optimal exercise for bonds w emb option");

		return bondBasisFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double convexityFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return convexityFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromASW
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double convexityFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return convexityFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double convexityFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::convexityFromASWToOptimalExercise => " +
				"Cant calc Convexity from ASW to optimal exercise for bonds w emb option");

		return convexityFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double convexityFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return convexityFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromBondBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double convexityFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return convexityFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double convexityFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::convexityFromBondBasisToOptimalExercise => " +
				"Cant calc Convexity from Bond Basis to optimal exercise for bonds w emb option");

		return convexityFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double convexityFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return convexityFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromCreditBasis (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double convexityFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return convexityFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double convexityFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::convexityFromCreditBasisToOptimalExercise => " +
				"Cant calc Convexity from Credit Basis to optimal exercise for bonds w emb option");

		return convexityFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double convexityFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return convexityFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
				dblDiscountMargin));
	}

	@Override public double convexityFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return convexityFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double convexityFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::convexityFromDiscountMarginToOptimalExercise " +
				"=> Cant calc Convexity from Discount Margin to optimal exercise for bonds w emb option");

		return convexityFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double convexityFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return convexityFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromESpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double convexityFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return convexityFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double convexityFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::convexityFromESpreadToOptimalExercise => " +
				"Cant calc Convexity from E Spread to optimal exercise for bonds w emb option");

		return convexityFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double convexityFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return convexityFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromGSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double convexityFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return convexityFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double convexityFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::convexityFromGSpreadToOptimalExercise => " +
				"Cant calc Convexity from G Spread to optimal exercise for bonds w emb option");

		return convexityFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double convexityFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return convexityFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromISpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double convexityFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return convexityFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double convexityFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::convexityFromISpreadToOptimalExercise => " +
				"Cant calc Convexity from I Spread to optimal exercise for bonds w emb option");

		return convexityFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double convexityFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return convexityFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromJSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double convexityFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return convexityFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double convexityFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::convexityFromJSpreadToOptimalExercise => " +
				"Cant calc Convexity from J Spread to optimal exercise for bonds w emb option");

		return convexityFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double convexityFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return convexityFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromOAS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double convexityFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return convexityFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double convexityFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::convexityFromOASToOptimalExercise => " +
				"Cant calc Convexity from OAS to optimal exercise for bonds w emb option");

		return convexityFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double convexityFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return convexityFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromPECS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double convexityFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return convexityFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double convexityFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::convexityFromPECSToOptimalExercise => " +
				"Cant calc Convexity from PECS to optimal exercise for bonds w emb option");

		return convexityFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double convexityFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.quant.common.NumberUtil.IsValid (dblPrice))
			throw new java.lang.Exception ("BondComponent::convexityFromPrice => Input inputs");

		double dblYield = yieldFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPrice);

		return (priceFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYield - 0.0001) +
			priceFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYield + 0.0001) - 2. *
				dblPrice) / (dblPrice + accrued (valParams.valueDate(), csqc));
	}

	@Override public double convexityFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return convexityFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double convexityFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::calcConvexityFromPriceToOptimalExercise => " +
				"Cant calc Convexity from Price to optimal exercise for bonds w emb option");

		return convexityFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double convexityFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return convexityFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromTSYSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double convexityFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return convexityFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double convexityFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::convexityFromTSYSpreadToOptimalExercise => " +
				"Cant calc Convexity from TSY Sprd to optimal exercise for bonds w emb option");

		return convexityFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double convexityFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		return convexityFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromYield
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYield));
	}

	@Override public double convexityFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return convexityFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double convexityFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::convexityFromYieldToOptimalExercise => " +
				"Cant calc Convexity from Yield to optimal exercise for bonds w emb option");

		return convexityFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double convexityFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return convexityFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromYieldSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double convexityFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return convexityFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double convexityFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::convexityFromYieldSpreadToOptimalExercise => " +
				"Cant calc Convexity from Yld Sprd to optimal exercise for bonds w emb option");

		return convexityFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double convexityFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return convexityFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromZSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblZSpread));
	}

	@Override public double convexityFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return convexityFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double convexityFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::convexityFromZSpreadToOptimalExercise => " +
				"Cant calc Convexity from Z Spread to optimal exercise for bonds w emb option");

		return convexityFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double creditBasisFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return creditBasisFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromASW
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double creditBasisFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return creditBasisFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double creditBasisFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::creditBasisFromASWToOptimalExercise => " +
				"Cannot calc Credit Basis from ASW to optimal exercise for bonds w emb option");

		return creditBasisFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double creditBasisFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return creditBasisFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromBondBasis (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double creditBasisFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return creditBasisFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double creditBasisFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::creditBasisFromBondBasisToOptimalExercise " +
				"=> Cant calc Credit Basis from Bond Basis to optimal exercise for bonds w emb option");

		return creditBasisFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double creditBasisFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return creditBasisFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
				dblDiscountMargin));
	}

	@Override public double creditBasisFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return creditBasisFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double creditBasisFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::creditBasisFromDiscountMarginToOptimalExercise => " +
					"Cant calc Credit Basis from Discnt Margin to optimal exercise for bonds w emb option");

		return creditBasisFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double creditBasisFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return creditBasisFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromESpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double creditBasisFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return creditBasisFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double creditBasisFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::creditBasisFromESpreadToOptimalExercise => " +
				"Cant calc Credit Basis from E Spread to optimal exercise for bonds w emb option");

		return creditBasisFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double creditBasisFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return creditBasisFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromGSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double creditBasisFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return creditBasisFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double creditBasisFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::creditBasisFromGSpreadToOptimalExercise => " +
				"Cant calc Credit Basis from G Spread to optimal exercise for bonds w emb option");

		return creditBasisFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double creditBasisFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return creditBasisFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromISpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double creditBasisFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return creditBasisFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double creditBasisFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::creditBasisFromISpreadToOptimalExercise => " +
				"Cant calc Credit Basis from I Spread to optimal exercise for bonds w emb option");

		return creditBasisFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double creditBasisFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return creditBasisFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromJSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double creditBasisFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return creditBasisFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double creditBasisFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::creditBasisFromJSpreadToOptimalExercise => " +
				"Cant calc Credit Basis from J Spread to optimal exercise for bonds w emb option");

		return creditBasisFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double creditBasisFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return creditBasisFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromOAS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double creditBasisFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return creditBasisFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double creditBasisFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::creditBasisFromOASToOptimalExercise => " +
				"Cant calc Credit Basis from OAS to optimal exercise for bonds w emb option");

		return creditBasisFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double creditBasisFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return creditBasisFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromPECS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double creditBasisFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return creditBasisFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double creditBasisFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::creditBasisFromPECSToOptimalExercise => " +
				"Cant calc Credit Basis from PECS to optimal exercise for bonds w emb option");

		return creditBasisFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double creditBasisFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return new BondCalibrator (this, false).calibrateCreditBasisFromPrice (valParams, csqc, iWorkoutDate,
			dblWorkoutFactor, dblPrice, false);
	}

	@Override public double creditBasisFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return creditBasisFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double creditBasisFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception
				("BondComponent::creditBasisFromPriceToOptimalExercise => cant calc Work-out");

		return creditBasisFromPrice (valParams, csqc, vcp, wi.date(), wi.factor(), dblPrice);
	}

	@Override public double creditBasisFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return creditBasisFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromTSYSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double creditBasisFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return creditBasisFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double creditBasisFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::creditBasisFromTSYSpreadToOptimalExercise => " +
				"Cant calc Credit Basis from TSY Spread to optimal exercise for bonds w emb option");

		return creditBasisFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double creditBasisFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		return creditBasisFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromYield
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYield));
	}

	@Override public double creditBasisFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return creditBasisFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double creditBasisFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::creditBasisFromYieldToOptimalExercise => " +
				"Cant calc Credit Basis from Yield to optimal exercise for bonds w emb option");

		return creditBasisFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double creditBasisFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return creditBasisFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromYieldSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double creditBasisFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return creditBasisFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblYieldSpread);
	}

	@Override public double creditBasisFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws	java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::creditBasisFromYieldSpreadToOptimalExercise " +
				"=> Cant calc Credit Basis from Yield Spread to optimal exercise for bonds w emb option");

		return creditBasisFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblYieldSpread);
	}

	@Override public double creditBasisFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return creditBasisFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromZSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblZSpread));
	}

	@Override public double creditBasisFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return creditBasisFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double creditBasisFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::creditBasisFromZSpreadToOptimalExercise => " +
				"Cant calc Credit Basis from Z Spread to optimal exercise for bonds w emb option");

		return creditBasisFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double discountMarginFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return discountMarginFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromASW
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double discountMarginFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return discountMarginFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double discountMarginFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::discountMarginFromASWToOptimalExercise => " +
				"Cant calc Discount Margin from ASW to optimal exercise for bonds w emb option");

		return discountMarginFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double discountMarginFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return discountMarginFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromBondBasis (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double discountMarginFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return discountMarginFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double discountMarginFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::discountMarginFromBondBasisToOptimalExercise " +
				"=> Cant calc Discount Margin from Bond Basis to optimal exercise for bonds w emb option");

		return discountMarginFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double discountMarginFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return discountMarginFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromCreditBasis (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double discountMarginFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return discountMarginFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblCreditBasis);
	}

	@Override public double discountMarginFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::discountMarginFromCreditBasisToOptimalExercise => " +
					"Cant calc Discount Margin from Crdit Basis to optimal exercise for bonds w emb option");

		return discountMarginFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblCreditBasis);
	}

	@Override public double discountMarginFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return discountMarginFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromESpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double discountMarginFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return discountMarginFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double discountMarginFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::discountMarginFromESpreadToOptimalExercise =>" +
				" => Cant calc Discount Margin from E Spread to optimal exercise for bonds w emb option");

		return discountMarginFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double discountMarginFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return discountMarginFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromGSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double discountMarginFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return discountMarginFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double discountMarginFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::discountMarginFromGSpreadToOptimalExercise =>" +
				" => Cant calc Discount Margin from G Spread to optimal exercise for bonds w emb option");

		return discountMarginFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double discountMarginFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return discountMarginFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromISpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double discountMarginFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return discountMarginFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double discountMarginFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::discountMarginFromISpreadToOptimalExercise " +
				"=> Cant calc Discount Margin from I Spread to optimal exercise for bonds w emb option");

		return discountMarginFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double discountMarginFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return discountMarginFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromJSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double discountMarginFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return discountMarginFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double discountMarginFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::discountMarginFromJSpreadToOptimalExercise " +
				"=> Cant calc Discount Margin from J Spread to optimal exercise for bonds w emb option");

		return discountMarginFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double discountMarginFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return discountMarginFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromOAS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double discountMarginFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return discountMarginFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double discountMarginFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::calcDiscountMarginFromOASToOptimalExercise => " +
				"Cant calc Discount Margin from OAS to optimal exercise for bonds w emb option");

		return discountMarginFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double discountMarginFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return discountMarginFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromPECS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double discountMarginFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return discountMarginFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double discountMarginFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::discountMarginFromPECSToOptimalExercise => " +
				"Cant calc Discount Margin from PECS to optimal exercise for bonds w emb option");

		return discountMarginFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double discountMarginFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return discountMarginFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPrice));
	}

	@Override public double discountMarginFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return discountMarginFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double discountMarginFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception
				("BondComponent::discountMarginFromPriceToOptimalExercise => Can't do Work-out");

		return discountMarginFromYield (valParams, csqc, vcp, wi.date(), wi.factor(), wi.yield());
	}

	@Override public double discountMarginFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return discountMarginFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromTSYSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double discountMarginFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return discountMarginFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double discountMarginFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::discountMarginFromTSYSpreadToOptimalExercise " +
				"=> Cant calc Discount Margin from TSY Spread to optimal exercise for bonds w emb option");

		return discountMarginFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double discountMarginFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null == valParams || null == csqc || !org.drip.quant.common.NumberUtil.IsValid (dblYield))
			throw new java.lang.Exception ("BondComponent::discountMarginFromYield => Invalid inputs");

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqc.fundingState (fundingLabel());

		if (null == dcFunding)
			throw new java.lang.Exception ("BondComponent::discountMarginFromYield => Invalid inputs");

		int iValueDate = valParams.valueDate();

		int iFreq = freq();

		return null == _floaterSetting ? dblYield - dcFunding.libor (iValueDate, ((int) (12. / (0 == iFreq
			? 2 : iFreq))) + "M") : dblYield - indexRate (iValueDate, csqc,
				(org.drip.analytics.cashflow.CompositeFloatingPeriod) currentPeriod (iValueDate));
	}

	@Override public double discountMarginFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return discountMarginFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double discountMarginFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::discountMarginFromYieldToOptimalExercise =>" +
				" Cant calc Discount Margin from Yield to optimal exercise for bonds w emb option");

		return discountMarginFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double discountMarginFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return discountMarginFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromYieldSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double discountMarginFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return discountMarginFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblYieldSpread);
	}

	@Override public double discountMarginFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::discountMarginFromYieldSpreadToOptimalExercise => " +
					"Cant calc Discount Margin from Yield Sprd to optimal exercise for bonds w emb option");

		return discountMarginFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblYieldSpread);
	}

	@Override public double discountMarginFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return discountMarginFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromZSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblZSpread));
	}

	@Override public double discountMarginFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return discountMarginFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double discountMarginFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::discountMarginFromZSpreadToOptimalExercise =>" +
				" Cant calc Discount Margin from Z Spread to optimal exercise for bonds w emb option");

		return discountMarginFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double durationFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return durationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromASW
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double durationFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return durationFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double durationFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::durationFromASWToOptimalExercise => " +
				"Cant calc Duration from ASW to optimal exercise for bonds w emb option");

		return durationFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double durationFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return durationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromBondBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double durationFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return durationFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double durationFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::durationFromBondBasisToOptimalExercise => " +
				"Cant calc Duration from Bond Basis to optimal exercise for bonds w emb option");

		return durationFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double durationFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return durationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromCreditBasis (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double durationFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return durationFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double durationFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::durationFromCreditBasisToOptimalExercise => " +
				"Cant calc Duration from Credit Basis to optimal exercise for bonds w emb option");

		return durationFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double durationFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return durationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
				dblDiscountMargin));
	}

	@Override public double durationFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return durationFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double durationFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::durationFromDiscountMarginToOptimalExercise " +
				"=> Cant calc Duration from Discount Margin to optimal exercise for bonds w emb option");

		return durationFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double durationFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return durationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromESpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double durationFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return durationFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double durationFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::durationFromESpreadToOptimalExercise => " +
				"Cant calc Duration from E Spread to optimal exercise for bonds w emb option");

		return durationFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double durationFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return durationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromGSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double durationFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return durationFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double durationFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::durationFromGSpreadToOptimalExercise => " +
				"Cant calc Duration from G Spread to optimal exercise for bonds w emb option");

		return durationFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double durationFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return durationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromISpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double durationFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return durationFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double durationFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::durationFromISpreadToOptimalExercise => " +
				"Cant calc Duration from I Spread to optimal exercise for bonds w emb option");

		return durationFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double durationFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return durationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromJSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double durationFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return durationFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double durationFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::durationFromJSpreadToOptimalExercise => " +
				"Cant calc Duration from J Spread to optimal exercise for bonds w emb option");

		return durationFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double durationFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return durationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromOAS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double durationFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return durationFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double durationFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::durationFromOASToOptimalExercise => " +
				"Cant calc Duration from OAS to optimal exercise for bonds w emb option");

		return durationFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double durationFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return durationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromPECS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double durationFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return durationFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double durationFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::durationFromPECSToOptimalExercise => " +
				"Cant calc Duration from PECS to optimal exercise for bonds w emb option");

		return durationFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double durationFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return modifiedDurationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPrice);
	}

	@Override public double durationFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return durationFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double durationFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::durationFromPriceToOptimalExercise => " +
				"Cant calc Duration from Price to optimal exercise for bonds w emb option");

		return durationFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double durationFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return durationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromTSYSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double durationFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return durationFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double durationFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::durationFromTSYSpreadToOptimalExercise => " +
				"Cant calc Duration from TSY Sprd to optimal exercise for bonds w emb option");

		return durationFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double durationFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		return durationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromYield
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYield));
	}

	@Override public double durationFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return durationFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double durationFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::durationFromYieldToOptimalExercise => " +
				"Cant calc Duration from Yield to optimal exercise for bonds w emb option");

		return durationFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double durationFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return durationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromYieldSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double durationFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return durationFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double durationFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::durationFromYieldSpreadToOptimalExercise => " +
				"Cant calc Duration from Yield Spread to optimal exercise for bonds w emb option");

		return durationFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double durationFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return durationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromZSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblZSpread));
	}

	@Override public double durationFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return durationFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double durationFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::durationFromZSpreadToOptimalExercise => " +
				"Cant calc Duration from Z Spread to optimal exercise for bonds w emb option");

		return durationFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double eSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return eSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromASW
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double eSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return eSpreadFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double eSpreadFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::eSpreadFromASWToOptimalExercise => " +
				"Cant calc E Spread from ASW to optimal exercise for bonds w emb option");

		return eSpreadFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double eSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return eSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromBondBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double eSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return eSpreadFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double eSpreadFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::eSpreadFromBondBasisToOptimalExercise => " +
				"Cant calc E Spread from Bond Basis to optimal exercise for bonds w emb option");

		return eSpreadFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double eSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return eSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromCreditBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double eSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return eSpreadFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double eSpreadFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::eSpreadFromCreditBasisToOptimalExercise => " +
				"Cant calc E Spread from Credit Basis to optimal exercise for bonds w emb option");

		return eSpreadFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double eSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return eSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
				dblDiscountMargin));
	}

	@Override public double eSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return eSpreadFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double eSpreadFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::eSpreadFromDiscountMarginToOptimalExercise => " +
					"Cant calc E Spread from Discount Margin to optimal exercise for bonds w emb option");

		return eSpreadFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double eSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return eSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromGSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double eSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return eSpreadFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double eSpreadFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::eSpreadFromGSpreadToOptimalExercise => " +
				"Cant calc E Spread from G Spread to optimal exercise for bonds w emb option");

		return eSpreadFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double eSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return eSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromISpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double eSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return eSpreadFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double eSpreadFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::eSpreadFromISpreadToOptimalExercise => " +
				"Cant calc E Spread from I Spread to optimal exercise for bonds w emb option");

		return eSpreadFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double eSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return eSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromJSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double eSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return eSpreadFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double eSpreadFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::eSpreadFromJSpreadToOptimalExercise => " +
				"Cant calc E Spread from J Spread to optimal exercise for bonds w emb option");

		return eSpreadFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double eSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return eSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromOAS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double eSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return eSpreadFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double eSpreadFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::eSpreadFromOASToOptimalExercise => " +
				"Cant calc E Spread from OAS to optimal exercise for bonds w emb option");

		return eSpreadFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double eSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return eSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromPECS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double eSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return eSpreadFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double eSpreadFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::eSpreadFromPECSToOptimalExercise => " +
				"Cant calc E Spread from PECS to optimal exercise for bonds w emb option");

		return eSpreadFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double eSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return new BondCalibrator (this, false).calibrateZSpreadFromPrice (valParams, csqc, vcp,
			ZERO_OFF_OF_RATES_INSTRUMENTS_DISCOUNT_CURVE, iWorkoutDate, dblWorkoutFactor, dblPrice);
	}

	@Override public double eSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return eSpreadFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double eSpreadFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception ("BondComponent::eSpreadFromPriceToOptimalExercise => " +
				"Cant calc Workout from Price to optimal exercise for bonds w emb option");

		return eSpreadFromPrice (valParams, csqc, vcp, wi.date(), wi.factor(), dblPrice);
	}

	@Override public double eSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return eSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromTSYSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double eSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return eSpreadFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double eSpreadFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::eSpreadFromTSYSpreadToOptimalExercise => " +
				"Cant calc E Spread from TSY Spread to optimal exercise for bonds w emb option");

		return eSpreadFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double eSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		return eSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromYield
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYield));
	}

	@Override public double eSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return eSpreadFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double eSpreadFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::eSpreadFromYieldToOptimalExercise => " +
				"Cant calc E Spread from Yield to optimal exercise for bonds w emb option");

		return eSpreadFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double eSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return eSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromYieldSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double eSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return eSpreadFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double eSpreadFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::eSpreadFromYieldSpreadToOptimalExercise => " +
				"Cant calc E Spread from Yield Spread to optimal exercise for bonds w emb option");

		return eSpreadFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double gSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return gSpreadFromYield (valParams, csqs, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromASW
			(valParams, csqs, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double gSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return gSpreadFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double gSpreadFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::gSpreadFromASWToOptimalExercise => " +
				"Cant calc G Spread from ASW to optimal exercise for bonds w emb option");

		return gSpreadFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double gSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return gSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromBondBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double gSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return gSpreadFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double gSpreadFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::gSpreadFromBondBasisToOptimalExercise => " +
				"Cant calc G Spread from Bond Basis to optimal exercise for bonds w emb option");

		return gSpreadFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double gSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return gSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromCreditBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double gSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return gSpreadFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double gSpreadFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::gSpreadFromCreditBasisToOptimalExercise => " +
				"Cant calc G Spread from Credit Basis to optimal exercise for bonds w emb option");

		return gSpreadFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double gSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return gSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
				dblDiscountMargin));
	}

	@Override public double gSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return gSpreadFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double gSpreadFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::gSpreadFromDiscountMarginToOptimalExercise =>" +
				" Cant calc G Spread from Discount Margin to optimal exercise for bonds w emb option");

		return gSpreadFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double gSpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return gSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromESpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double gSpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return gSpreadFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double gSpreadFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::gSpreadFromESpreadToOptimalExercise => " +
				"Cant calc G Spread from E Spread to optimal exercise for bonds w emb option");

		return gSpreadFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double gSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return gSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromISpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double gSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return gSpreadFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double gSpreadFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::gSpreadFromISpreadToOptimalExercise => " +
				"Cant calc G Spread from I Spread to optimal exercise for bonds w emb option");

		return gSpreadFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double gSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return gSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromJSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double gSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return gSpreadFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double gSpreadFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::gSpreadFromJSpreadToOptimalExercise => " +
				"Cant calc G Spread from J Spread to optimal exercise for bonds w emb option");

		return gSpreadFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double gSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return gSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromOAS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double gSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return gSpreadFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double gSpreadFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::gSpreadFromOASToOptimalExercise => " +
				"Cant calc G Spread from OAS to optimal exercise for bonds w emb option");

		return gSpreadFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double gSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return gSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromPECS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double gSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return gSpreadFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double gSpreadFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::gSpreadFromPECSToOptimalExercise => " +
				"Cant calc G Spread from PECS to optimal exercise for bonds w emb option");

		return gSpreadFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double gSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return gSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromPrice
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPrice));
	}

	@Override public double gSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return gSpreadFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double gSpreadFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception
				("BondComponent::gSpreadFromPriceToOptimalExercise => Can't do Work-out");

		return gSpreadFromPrice (valParams, csqc, vcp, wi.date(), wi.factor(), dblPrice);
	}

	@Override public double gSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return gSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromTSYSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double gSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return gSpreadFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double gSpreadFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::gSpreadFromTSYSpreadToOptimalExercise => " +
				"Cant calc G Spread from TSY Spread to optimal exercise for bonds w emb option");

		return gSpreadFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double gSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null == valParams || null == csqc || !org.drip.quant.common.NumberUtil.IsValid (dblYield) ||
			valParams.valueDate() >= iWorkoutDate)
			throw new java.lang.Exception ("BondComponent::gSpreadFromYield => Invalid inputs");

		org.drip.state.govvie.GovvieCurve gc = csqc.govvieState (govvieLabel());

		if (null == gc) throw new java.lang.Exception ("BondComponent::gSpreadFromYield => Invalid inputs");

		return dblYield - gc.yield (iWorkoutDate);
	}

	@Override public double gSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return gSpreadFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double gSpreadFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::gSpreadFromYieldToOptimalExercise => " +
				"Cant calc G Spread from Yield to optimal exercise for bonds w emb option");

		return gSpreadFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double gSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return gSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromYieldSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double gSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return gSpreadFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double gSpreadFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::gSpreadFromYieldSpreadToOptimalExercise => " +
				"Cant calc G Spread from Yield Spread to optimal exercise for bonds w emb option");

		return gSpreadFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double gSpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return gSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromZSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblZSpread));
	}

	@Override public double gSpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return gSpreadFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double gSpreadFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::gSpreadFromZSpreadToOptimalExercise => " +
				"Cant calc G Spread from Z Spread to optimal exercise for bonds w emb option");

		return gSpreadFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double iSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return iSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromASW
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double iSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return iSpreadFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double iSpreadFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromASWToOptimalExercise => " +
				"Cant calc I Spread from ASW to optimal exercise for bonds w emb option");

		return iSpreadFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double iSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return iSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromBondBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double iSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return iSpreadFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double iSpreadFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromBondBasisToOptimalExercise => " +
				"Cant calc I Spread from Bond Basis to optimal exercise for bonds w emb option");

		return iSpreadFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double iSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return iSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromCreditBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double iSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return iSpreadFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double iSpreadFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromCreditBasisToOptimalExercise => " +
				"Cant calc I Spread from Credit Basis to optimal exercise for bonds w emb option");

		return iSpreadFromCreditBasis (valParams, csqs, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double iSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return iSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
				dblDiscountMargin));
	}

	@Override public double iSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return iSpreadFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double iSpreadFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromDiscountMarginToOptimalExercise =>" +
				" Cant calc I Spread from Discount Margin to optimal exercise for bonds w emb option");

		return iSpreadFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double iSpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return iSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromESpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double iSpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return iSpreadFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double iSpreadFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromESpreadToOptimalExercise => " +
				"Cant calc I Spread from E Spread to optimal exercise for bonds w emb option");

		return iSpreadFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double iSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return iSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromGSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double iSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return iSpreadFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double iSpreadFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromGSpreadToOptimalExercise => " +
				"Cant calc I Spread from G Spread to optimal exercise for bonds w emb option");

		return iSpreadFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double iSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return iSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromJSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double iSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return iSpreadFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double iSpreadFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromJSpreadToOptimalExercise => " +
				"Cant calc I Spread from J Spread to optimal exercise for bonds w emb option");

		return iSpreadFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double iSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return iSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromOAS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double iSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return iSpreadFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double iSpreadFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromOASToOptimalExercise => " +
				"Cant calc I Spread from OAS to optimal exercise for bonds w emb option");

		return iSpreadFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double iSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return iSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromPECS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double iSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return iSpreadFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double iSpreadFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromPECSToOptimalExercise => " +
				"Cant calc I Spread from PECS to optimal exercise for bonds w emb option");

		return iSpreadFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double iSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return iSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromPrice
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPrice));
	}

	@Override public double iSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return iSpreadFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double iSpreadFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception
				("BondComponent::iSpreadFromPriceToOptimalExercise => Can't do Work-out");

		return iSpreadFromYield (valParams, csqc, vcp, wi.date(), wi.factor(), wi.yield());
	}

	@Override public double iSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return iSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromTSYSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double iSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return iSpreadFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double iSpreadFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromTSYSpreadToOptimalExercise => " +
				"Cant calc I Spread from TSY Spread to optimal exercise for bonds w emb option");

		return iSpreadFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double iSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null == csqc || !org.drip.quant.common.NumberUtil.IsValid (dblYield))
			throw new java.lang.Exception ("BondComponent::iSpreadFromYield => Invalid inputs");

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqc.fundingState (fundingLabel());

		if (null == dcFunding)
			throw new java.lang.Exception ("BondComponent::iSpreadFromYield => Invalid inputs");

		return dblYield - dcFunding.estimateManifestMeasure ("SwapRate", iWorkoutDate);
	}

	@Override public double iSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return iSpreadFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double iSpreadFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromYieldToOptimalExercise => " +
				"Cant calc I Spread from Yield to optimal exercise for bonds w emb option");

		return iSpreadFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double iSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return iSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromYieldSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double iSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return iSpreadFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double iSpreadFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromYieldSpreadToOptimalExercise => " +
				"Cant calc I Spread from Yield Spread to optimal exercise for bonds w emb option");

		return iSpreadFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double iSpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return iSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromZSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblZSpread));
	}

	@Override public double iSpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return iSpreadFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double iSpreadFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromZSpreadToOptimalExercise => " +
				"Cant calc I Spread from Z Spread to optimal exercise for bonds w emb option");

		return iSpreadFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double jSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return jSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromASW
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double jSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return jSpreadFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double jSpreadFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromASWToOptimalExercise => " +
				"Cant calc J Spread from ASW to optimal exercise for bonds w emb option");

		return jSpreadFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double jSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return jSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromBondBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double jSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return jSpreadFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double jSpreadFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromBondBasisToOptimalExercise => " +
				"Cant calc J Spread from Bond Basis to optimal exercise for bonds w emb option");

		return jSpreadFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double jSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return jSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromCreditBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double jSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return jSpreadFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double jSpreadFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::jSpreadFromCreditBasisToOptimalExercise => " +
				"Cant calc I Spread from Credit Basis to optimal exercise for bonds w emb option");

		return iSpreadFromCreditBasis (valParams, csqs, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double jSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return jSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
				dblDiscountMargin));
	}

	@Override public double jSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return jSpreadFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double jSpreadFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::jSpreadFromDiscountMarginToOptimalExercise =>" +
				" Cant calc J Spread from Discount Margin to optimal exercise for bonds w emb option");

		return jSpreadFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double jSpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return jSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromESpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double jSpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return jSpreadFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double jSpreadFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::jSpreadFromESpreadToOptimalExercise => " +
				"Cant calc J Spread from E Spread to optimal exercise for bonds w emb option");

		return jSpreadFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double jSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return jSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromGSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double jSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return jSpreadFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double jSpreadFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::jSpreadFromGSpreadToOptimalExercise => " +
				"Cant calc J Spread from G Spread to optimal exercise for bonds w emb option");

		return jSpreadFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double jSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return jSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromISpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double jSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return jSpreadFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double jSpreadFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::jSpreadFromISpreadToOptimalExercise => " +
				"Cant calc J Spread from I Spread to optimal exercise for bonds w emb option");

		return jSpreadFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double jSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return jSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromOAS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double jSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return jSpreadFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double jSpreadFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::jSpreadFromOASToOptimalExercise => " +
				"Cant calc J Spread from OAS to optimal exercise for bonds w emb option");

		return jSpreadFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double jSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return jSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromPECS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double jSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return jSpreadFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double jSpreadFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::jSpreadFromPECSToOptimalExercise => " +
				"Cant calc J Spread from PECS to optimal exercise for bonds w emb option");

		return jSpreadFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double jSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return jSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromPrice
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPrice));
	}

	@Override public double jSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return jSpreadFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double jSpreadFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception
				("BondComponent::jSpreadFromPriceToOptimalExercise => Can't do Work-out");

		return jSpreadFromYield (valParams, csqc, vcp, wi.date(), wi.factor(), wi.yield());
	}

	@Override public double jSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return jSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromTSYSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double jSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return jSpreadFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double jSpreadFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::jSpreadFromTSYSpreadToOptimalExercise => " +
				"Cant calc J Spread from TSY Spread to optimal exercise for bonds w emb option");

		return jSpreadFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double jSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null == csqc || !org.drip.quant.common.NumberUtil.IsValid (dblYield))
			throw new java.lang.Exception ("BondComponent::jSpreadFromYield => Invalid inputs");

		org.drip.state.govvie.GovvieCurve gc = csqc.govvieState (govvieLabel());

		if (null == gc) throw new java.lang.Exception ("BondComponent::jSpreadFromYield => Invalid inputs");

		return dblYield - gc.yield (weightedAverageMaturityDate (valParams, csqc, iWorkoutDate,
			dblWorkoutFactor));
	}

	@Override public double jSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return jSpreadFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double jSpreadFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::jSpreadFromYieldToOptimalExercise => " +
				"Cant calc J Spread from Yield to optimal exercise for bonds w emb option");

		return jSpreadFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double jSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return jSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromYieldSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double jSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return jSpreadFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double jSpreadFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromYieldSpreadToOptimalExercise => " +
				"Cant calc J Spread from Yield Spread to optimal exercise for bonds w emb option");

		return jSpreadFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double jSpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return jSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromZSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblZSpread));
	}

	@Override public double jSpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return jSpreadFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double jSpreadFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::iSpreadFromZSpreadToOptimalExercise => " +
				"Cant calc J Spread from Z Spread to optimal exercise for bonds w emb option");

		return jSpreadFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double macaulayDurationFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return macaulayDurationFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromASW (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double macaulayDurationFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return macaulayDurationFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double macaulayDurationFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::macaulayDurationFromASWToOptimalExercise => " +
					"Cant calc Macaulay Duration from ASW to optimal exercise for bonds w emb option");

		return macaulayDurationFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double macaulayDurationFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return macaulayDurationFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromBondBasis (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double macaulayDurationFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return macaulayDurationFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblBondBasis);
	}

	@Override public double macaulayDurationFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::macaulayDurationFromBondBasisToOptimalExercise => " +
					"Cant calc Macaulay Duration from Bnd Basis to optimal exercise for bonds w emb option");

		return macaulayDurationFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblBondBasis);
	}

	@Override public double macaulayDurationFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return macaulayDurationFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromCreditBasis (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double macaulayDurationFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return macaulayDurationFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblCreditBasis);
	}

	@Override public double macaulayDurationFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::macaulayDurationFromCreditBasisToOptimalExercise => " +
					"Cant calc Macaulay Duration from Crd Basis to optimal exercise for bonds w emb option");

		return macaulayDurationFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblCreditBasis);
	}

	@Override public double macaulayDurationFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return macaulayDurationFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
				dblDiscountMargin));
	}

	@Override public double macaulayDurationFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return macaulayDurationFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double macaulayDurationFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::macaulayDurationFromDiscountMarginToOptimalExercise => " +
					"Cant calc Macaulay Duration from Disc Marg to optimal exercise for bonds w emb option");

		return macaulayDurationFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double macaulayDurationFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return macaulayDurationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromESpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double macaulayDurationFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return macaulayDurationFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double macaulayDurationFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::macaulayDurationFromESpreadToOptimalExercise => " +
					"Cant calc Macaulay Duration from E Spread to optimal exercise for bonds w emb option");

		return macaulayDurationFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double macaulayDurationFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return macaulayDurationFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromGSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double macaulayDurationFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return macaulayDurationFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double macaulayDurationFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::macaulayDurationFromGSpreadToOptimalExercise => " +
					"Cant calc Macaulay Duration from G Spread to optimal exercise for bonds w emb option");

		return macaulayDurationFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double macaulayDurationFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return macaulayDurationFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromISpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double macaulayDurationFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return macaulayDurationFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double macaulayDurationFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::macaulayDurationFromISpreadToOptimalExercise => " +
					"Cant calc Macaulay Duration from I Spread to optimal exercise for bonds w emb option");

		return macaulayDurationFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double macaulayDurationFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return macaulayDurationFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromJSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double macaulayDurationFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return macaulayDurationFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double macaulayDurationFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::macaulayDurationFromJSpreadToOptimalExercise => " +
					"Cant calc Macaulay Duration from J Spread to optimal exercise for bonds w emb option");

		return macaulayDurationFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double macaulayDurationFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return macaulayDurationFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromOAS (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double macaulayDurationFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return macaulayDurationFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double mnacaulayDurationFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::mnacaulayDurationFromOASToOptimalExercise => " +
					"Cant calc Macaulay Duration from OAS to optimal exercise for bonds w emb option");

		return macaulayDurationFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double macaulayDurationFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return macaulayDurationFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromPECS (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double macaulayDurationFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return macaulayDurationFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double macaulayDurationFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::macaulayDurationFromPECSToOptimalExercise => " +
					"Cant calc Macaulay Duration from PECS to optimal exercise for bonds w emb option");

		return macaulayDurationFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double macaulayDurationFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return macaulayDurationFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPrice));
	}

	@Override public double macaulayDurationFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return macaulayDurationFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double macaulayDurationFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception
				("BondComponent::macaulayDurationFromPriceToOptimalExercise => Cant determine Work-out");

		return macaulayDurationFromPrice (valParams, csqc, vcp, wi.date(), wi.factor(), wi.yield());
	}

	@Override public double macaulayDurationFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return macaulayDurationFromTSYSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromTSYSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double macaulayDurationFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return macaulayDurationFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblTSYSpread);
	}

	@Override public double macaulayDurationFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::macaulayDurationFromTSYSpreadToOptimalExercise => " +
					"Cant calc Macaulay Duration from TSY Sprd to optimal exercise for bonds w emb option");

		return macaulayDurationFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblTSYSpread);
	}

	@Override public double macaulayDurationFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.quant.common.NumberUtil.IsValid (dblWorkoutFactor))
			throw new java.lang.Exception ("BondComponent::macaulayDurationFromYield => Invalid inputs");

		int iValueDate = valParams.valueDate();

		if (iValueDate >= iWorkoutDate)
			throw new java.lang.Exception ("BondComponent::macaulayDurationFromYield => Invalid inputs");

		boolean bFirstPeriod = true;
		double dblPeriodYearFract = 0.;
		double dblCumulativePeriodPV = 0.;
		boolean bTerminateCouponFlow = false;
		boolean bApplyFlatForwardRate = false;
		double dblCumulativePeriodDuration = 0.;
		double dblFlatForwardRate = java.lang.Double.NaN;
		org.drip.analytics.daycount.ActActDCParams aap = null;
		org.drip.analytics.cashflow.CompositePeriod periodRef = null;

		int iFrequency = freq();

		java.lang.String strDC = couponDC();

		java.lang.String strCalendar = currency();

		boolean bApplyCpnEOMAdj = _stream.couponEOMAdjustment();

		if (null == strCalendar || strCalendar.isEmpty()) strCalendar = redemptionCurrency();

		org.drip.param.valuation.ValuationCustomizationParams vcpQuote = null == _quoteConvention ? null :
			_quoteConvention.valuationCustomizationParams();

		if (null != vcp) {
			strDC = vcp.yieldDayCount();

			iFrequency = vcp.yieldFreq();

			strCalendar = vcp.yieldCalendar();

			bApplyCpnEOMAdj = vcp.applyYieldEOMAdj();

			bApplyFlatForwardRate = vcp.applyFlatForwardRate();
		} else if (null != vcpQuote) {
			strDC = vcpQuote.yieldDayCount();

			iFrequency = vcpQuote.yieldFreq();

			strCalendar = vcpQuote.yieldCalendar();

			bApplyCpnEOMAdj = vcpQuote.applyYieldEOMAdj();

			bApplyFlatForwardRate = vcpQuote.applyFlatForwardRate();
		}

		int iAmortizationMode = _notionalSetting.periodAmortizationMode();

		for (org.drip.analytics.cashflow.CompositePeriod period : couponPeriods()) {
			int iPeriodPayDate = period.payDate();

			if (iPeriodPayDate < iValueDate) continue;

			if (bFirstPeriod) {
				bFirstPeriod = false;

				dblPeriodYearFract = period.couponDCF() - period.accrualDCF (iValueDate);
			} else
				dblPeriodYearFract += period.couponDCF();

			periodRef = period;

			int iPeriodEndDate = period.endDate();

			int iPeriodStartDate = period.startDate();

			if (iPeriodEndDate >= iWorkoutDate) {
				bTerminateCouponFlow = true;
				iPeriodEndDate = iWorkoutDate;
			}

			org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = couponMetrics (iValueDate,
				valParams, csqc);

			if (null == cpcm)
				throw new java.lang.Exception ("BondComponent::macaulayDurationFromYield => No CPCM");

			if (null != vcp) {
				if (null == (aap = vcp.yieldAAP()))
					aap = new org.drip.analytics.daycount.ActActDCParams (vcp.yieldFreq(), iPeriodEndDate -
						iPeriodStartDate);
			} else if (null != vcpQuote) {
				if (null == (aap = vcpQuote.yieldAAP()))
					aap = new org.drip.analytics.daycount.ActActDCParams (vcpQuote.yieldFreq(),
						iPeriodEndDate - iPeriodStartDate);
			} else
				aap = new org.drip.analytics.daycount.ActActDCParams (iFrequency, iPeriodEndDate -
					iPeriodStartDate);

			double dblYearFract = org.drip.analytics.daycount.Convention.YearFraction (iValueDate,
				iPeriodPayDate, strDC, bApplyCpnEOMAdj, aap, strCalendar);

			double dblYieldAnnuity = org.drip.analytics.support.Helper.Yield2DF (iFrequency, dblYield,
				s_bYieldDFOffofCouponAccrualDCF ? dblPeriodYearFract : dblYearFract) * cpcm.cumulative();

			double dblPeriodStartNotional = notional (iPeriodStartDate);

			double dblPeriodEndNotional = notional (iPeriodEndDate);

			double dblCouponNotional = dblPeriodStartNotional;

			if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_END == iAmortizationMode)
				dblCouponNotional = dblPeriodEndNotional;
			else if (org.drip.product.params.NotionalSetting.PERIOD_AMORT_EFFECTIVE == iAmortizationMode)
				dblCouponNotional = notional (iPeriodStartDate, iPeriodEndDate);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblFlatForwardRate))
				dblFlatForwardRate = cpcm.rate();

			double dblCouponPV = period.accrualDCF (iPeriodEndDate) * (bApplyFlatForwardRate ?
				dblFlatForwardRate : cpcm.rate()) * dblYieldAnnuity * dblCouponNotional;

			double dblPeriodNotionalPV = (dblPeriodStartNotional - dblPeriodEndNotional) * dblYieldAnnuity;
			dblCumulativePeriodDuration += dblPeriodYearFract * (dblCouponPV + dblPeriodNotionalPV);
			dblCumulativePeriodPV += (dblCouponPV + dblPeriodNotionalPV);

			if (bTerminateCouponFlow) break;
		}

		if (null != periodRef)
			aap = new org.drip.analytics.daycount.ActActDCParams (iFrequency, periodRef.endDate() -
				periodRef.startDate());

		double dblRedemptionPV = dblWorkoutFactor * org.drip.analytics.support.Helper.Yield2DF (iFrequency,
			dblYield, s_bYieldDFOffofCouponAccrualDCF ? dblPeriodYearFract :
				org.drip.analytics.daycount.Convention.YearFraction (iValueDate, iWorkoutDate, strDC,
					bApplyCpnEOMAdj, aap, strCalendar)) * notional (iWorkoutDate);

		return (dblCumulativePeriodDuration + dblPeriodYearFract * dblRedemptionPV) / (dblCumulativePeriodPV
			+ dblRedemptionPV);
	}

	@Override public double macaulayDurationFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return macaulayDurationFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double macaulayDurationFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::macaulayDurationFromYieldToOptimalExercise =>" +
				" Cant calc Macaulay Duration from Yield to optimal exercise for bonds w emb option");

		return macaulayDurationFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double macaulayDurationFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return macaulayDurationFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromYieldSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double macaulayDurationFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return macaulayDurationFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblYieldSpread);
	}

	@Override public double macaulayDurationFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::macaulayDurationFromYieldSpreadToOptimalExercise => " +
					"Cant calc Macaulay Duration from Yld Sprd to optimal exercise for bonds w emb option");

		return macaulayDurationFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblYieldSpread);
	}

	@Override public double macaulayDurationFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return macaulayDurationFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromZSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblZSpread));
	}

	@Override public double macaulayDurationFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return macaulayDurationFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double macaulayDurationFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::macaulayDurationFromZSpreadToOptimalExercise => " +
					"Cant calc Macaulay Duration from Z Spread to optimal exercise for bonds w emb option");

		return macaulayDurationFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double modifiedDurationFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return modifiedDurationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromASW (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double modifiedDurationFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return modifiedDurationFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double modifiedDurationFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::modifiedDurationFromASWToOptimalExercise => " +
					"Cant calc Modified Duration from ASW to optimal exercise for bonds w emb option");

		return modifiedDurationFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double modifiedDurationFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return modifiedDurationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromBondBasis (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double modifiedDurationFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return modifiedDurationFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblBondBasis);
	}

	@Override public double modifiedDurationFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::modifiedDurationFromBondBasisToOptimalExercise => " +
					"Cant calc Modified Duration from Bnd Basis to optimal exercise for bonds w emb option");

		return modifiedDurationFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblBondBasis);
	}

	@Override public double modifiedDurationFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return modifiedDurationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromCreditBasis (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double modifiedDurationFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return modifiedDurationFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblCreditBasis);
	}

	@Override public double modifiedDurationFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::modifiedDurationFromCreditBasisToOptimalExercise => " +
					"Cant calc Modified Duration from Crd Basis to optimal exercise for bonds w emb option");

		return modifiedDurationFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblCreditBasis);
	}

	@Override public double modifiedDurationFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return modifiedDurationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
				dblDiscountMargin));
	}

	@Override public double modifiedDurationFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return modifiedDurationFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double modifiedDurationFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::modifiedDurationFromDiscountMarginToOptimalExercise => " +
					"Cant calc Modified Duration from Disc Marg to optimal exercise for bonds w emb option");

		return modifiedDurationFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double modifiedDurationFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return modifiedDurationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromESpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double modifiedDurationFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return modifiedDurationFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double modifiedDurationFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::modifiedDurationFromESpreadToOptimalExercise => " +
					"Cant calc Modified Duration from E Spread to optimal exercise for bonds w emb option");

		return modifiedDurationFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double modifiedDurationFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return modifiedDurationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromGSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double modifiedDurationFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return modifiedDurationFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double modifiedDurationFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::modifiedDurationFromGSpreadToOptimalExercise => " +
					"Cant calc Modified Duration from G Spread to optimal exercise for bonds w emb option");

		return modifiedDurationFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double modifiedDurationFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return modifiedDurationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromISpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double modifiedDurationFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return modifiedDurationFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double modifiedDurationFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::modifiedDurationFromISpreadToOptimalExercise => " +
					"Cant calc Modified Duration from I Spread to optimal exercise for bonds w emb option");

		return modifiedDurationFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double modifiedDurationFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return modifiedDurationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromJSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double modifiedDurationFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return modifiedDurationFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double modifiedDurationFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::modifiedDurationFromJSpreadToOptimalExercise => " +
					"Cant calc Modified Duration from J Spread to optimal exercise for bonds w emb option");

		return modifiedDurationFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double modifiedDurationFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return modifiedDurationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromOAS (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double modifiedDurationFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return modifiedDurationFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double modifiedDurationFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::modifiedDurationFromOASToOptimalExercise => " +
					"Cant calc Modified Duration from OAS to optimal exercise for bonds w emb option");

		return modifiedDurationFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double modifiedDurationFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return modifiedDurationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromPECS (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double modifiedDurationFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return modifiedDurationFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double modifiedDurationFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::modifiedDurationFromPECSToOptimalExercise => " +
					"Cant calc Modified Duration from PECS to optimal exercise for bonds w emb option");

		return modifiedDurationFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double modifiedDurationFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		if (null == valParams || !org.drip.quant.common.NumberUtil.IsValid (dblPrice))
			throw new java.lang.Exception ("BondComponent::modifiedDurationFromPrice => Input inputs");

		if (null == _floaterSetting)
			return (dblPrice - priceFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
				yieldFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPrice) + 0.0001)) /
					(dblPrice + accrued (valParams.valueDate(), csqc));

		return (dblPrice - priceFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			discountMarginFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPrice) +
				0.0001)) / (dblPrice + accrued (valParams.valueDate(), csqc));
	}

	@Override public double modifiedDurationFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return modifiedDurationFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double modifiedDurationFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception
				("BondComponent::modifiedDurationFromPriceToOptimalExercise => Cant determine Work-out");

		return modifiedDurationFromYield (valParams, csqc, vcp, wi.date(), wi.factor(), wi.yield());
	}

	@Override public double modifiedDurationFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return modifiedDurationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromTSYSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double modifiedDurationFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return modifiedDurationFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblTSYSpread);
	}

	@Override public double modifiedDurationFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::modifiedDurationFromTSYSpreadToOptimalExercise => " +
					"Cant calc Modified Duration from TSY Sprd to optimal exercise for bonds w emb option");

		return modifiedDurationFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblTSYSpread);
	}

	@Override public double modifiedDurationFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		return modifiedDurationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYield));
	}

	@Override public double modifiedDurationFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return modifiedDurationFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double modifiedDurationFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::modifiedDurationFromYieldToOptimalExercise =>" +
				" Cant calc Modified Duration from Yield to optimal exercise for bonds w emb option");

		return modifiedDurationFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double modifiedDurationFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return modifiedDurationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromYieldSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double modifiedDurationFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return modifiedDurationFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblYieldSpread);
	}

	@Override public double modifiedDurationFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::modifiedDurationFromYieldSpreadToOptimalExercise => " +
					"Cant calc Modified Duration from Yld Sprd to optimal exercise for bonds w emb option");

		return modifiedDurationFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblYieldSpread);
	}

	@Override public double modifiedDurationFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return modifiedDurationFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromZSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblZSpread));
	}

	@Override public double modifiedDurationFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return modifiedDurationFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double modifiedDurationFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::modifiedDurationFromZSpreadToOptimalExercise => " +
					"Cant calc Modified Duration from Z Spread to optimal exercise for bonds w emb option");

		return modifiedDurationFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double oasFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return oasFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromASW (valParams,
			csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double oasFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return oasFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double oasFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::oasFromASWToOptimalExercise => " +
				"Cant calc OAS from ASW to optimal exercise for bonds w emb option");

		return oasFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double oasFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return oasFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromBondBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double oasFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return oasFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double oasFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::oasFromBondBasisToOptimalExercise => " +
				"Cant calc OAS from Bnd Basis to optimal exercise for bonds w emb option");

		return oasFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double oasFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return oasFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromCreditBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double oasFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return oasFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double oasFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::oasFromCreditBasisToOptimalExercise => " +
				"Cant calc OAS from Credit Basis to optimal exercise for bonds w emb option");

		return oasFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double oasFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return oasFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromDiscountMargin
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblDiscountMargin));
	}

	@Override public double oasFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return oasFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1., dblDiscountMargin);
	}

	@Override public double oasFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::oasFromDiscountMarginToOptimalExercise => " +
				"Cant calc OAS from Discount Margin to optimal exercise for bonds w emb option");

		return oasFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1., dblDiscountMargin);
	}

	@Override public double oasFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return oasFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromESpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double oasFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return oasFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double oasFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::oasFromESpreadToOptimalExercise => " +
				"Cant calc OAS from E Spread to optimal exercise for bonds w emb option");

		return oasFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double oasFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return oasFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromGSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double oasFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return oasFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double oasFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::oasFromGSpreadToOptimalExercise => " +
				"Cant calc OAS from G Spread to optimal exercise for bonds w emb option");

		return oasFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double oasFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return oasFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromISpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double oasFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return oasFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double oasFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::oasFromISpreadToOptimalExercise => " +
				"Cant calc OAS from I Spread to optimal exercise for bonds w emb option");

		return oasFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double oasFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return oasFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromJSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double oasFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return oasFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double oasFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::oasFromJSpreadToOptimalExercise => " +
				"Cant calc OAS from J Spread to optimal exercise for bonds w emb option");

		return oasFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double oasFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return oasFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromPrice
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double oasFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return oasFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double oasFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::oasFromPECSToOptimalExercise => " +
				"Cant calc OAS from PECS to optimal exercise for bonds w emb option");

		return oasFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double oasFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return new BondCalibrator (this, false).calibrateOASFromPrice (valParams, csqc, vcp,
			ZERO_OFF_OF_TREASURIES_DISCOUNT_CURVE, iWorkoutDate, dblWorkoutFactor, dblPrice);
	}

	@Override public double oasFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return oasFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double oasFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception
				("BondComponent::oasFromPriceToOptimalExercise - cant calc Work-out");

		return oasFromPrice (valParams, csqc, vcp, wi.date(), wi.factor(), dblPrice);
	}

	@Override public double oasFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return oasFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromTSYSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double oasFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return oasFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double oasFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::oasFromTSYSpreadToOptimalExercise => " +
				"Cant calc OAS from TSY Sprd to optimal exercise for bonds w emb option");

		return oasFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double oasFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		return oasFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromYield
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYield));
	}

	@Override public double oasFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return oasFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double oasFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::oasFromYieldToOptimalExercise => " +
				"Cant calc OAS from Yield to optimal exercise for bonds w emb option");

		return oasFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double oasFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return oasFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromYieldSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double oasFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return oasFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double oasFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::oasFromYieldSpreadToOptimalExercise => " +
				"Cant calc OAS from Yield Sprd to optimal exercise for bonds w emb option");

		return oasFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double oasFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return oasFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromZSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblZSpread));
	}

	@Override public double oasFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return oasFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double oasFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::oasFromZSpreadToOptimalExercise => " +
				"Cant calc OAS from Z Spread to optimal exercise for bonds w emb option");

		return oasFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double pecsFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return pecsFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromASW
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double pecsFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return pecsFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double pecsFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::pecsFromASWToOptimalExercise => " +
				"Cant calc PECS from ASW to optimal exercise for bonds w emb option");

		return pecsFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double pecsFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return pecsFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromBondBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double pecsFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return pecsFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double pecsFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::pecsFromBondBasisToOptimalExercise => " +
				"Cant calc PECS from Bond Basis to optimal exercise for bonds w emb option");

		return pecsFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double pecsFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return pecsFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromCreditBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double pecsFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return pecsFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double pecsFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::pecsFromCreditBasisToOptimalExercise => " +
				"Cant calc PECS from Credit Basis to optimal exercise for bonds w emb option");

		return pecsFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double pecsFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return pecsFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromDiscountMargin
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblDiscountMargin));
	}

	@Override public double pecsFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return pecsFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1., dblDiscountMargin);
	}

	@Override public double pecsFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::pecsFromDiscountMarginToOptimalExercise => " +
				"Cant calc PECS from Discount Margin to optimal exercise for bonds w emb option");

		return pecsFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1., dblDiscountMargin);
	}

	@Override public double pecsFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return pecsFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromESpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double pecsFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return pecsFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double pecsFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::pecsFromESpreadToOptimalExercise => " +
				"Cant calc PECS from E Spread to optimal exercise for bonds w emb option");

		return pecsFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double pecsFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return pecsFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromGSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double pecsFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return pecsFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double pecsFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::pecsFromGSpreadToOptimalExercise => " +
				"Cant calc PECS from G Spread to optimal exercise for bonds w emb option");

		return pecsFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double pecsFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return pecsFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromISpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double pecsFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return pecsFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double pecsFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::pecsFromISpreadToOptimalExercise => " +
				"Cant calc PECS from I Spread to optimal exercise for bonds w emb option");

		return pecsFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double pecsFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return pecsFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromJSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double pecsFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return pecsFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double pecsFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::pecsFromJSpreadToOptimalExercise => " +
				"Cant calc PECS from J Spread to optimal exercise for bonds w emb option");

		return pecsFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double pecsFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return pecsFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromOAS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double pecsFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return pecsFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double pecsFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::pecsFromOASToOptimalExercise => " +
				"Cant calc PECS from OAS to optimal exercise for bonds w emb option");

		return pecsFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double pecsFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return new BondCalibrator (this, false).calibrateCreditBasisFromPrice (valParams, csqc, iWorkoutDate,
			dblWorkoutFactor, dblPrice, true);
	}

	@Override public double pecsFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return pecsFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double pecsFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception
				("BondComponent::pecsFromPriceToOptimalExercise => Cant determine Work-out");

		return pecsFromPrice (valParams, csqc, vcp, wi.date(), wi.factor(), dblPrice);
	}

	@Override public double pecsFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return pecsFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromTSYSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double pecsFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return pecsFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double pecsFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::pecsFromTSYSpreadToOptimalExercise => " +
				"Cant calc PECS from TSY Spread to optimal exercise for bonds w emb option");

		return pecsFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double pecsFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		return pecsFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromYield
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYield));
	}

	@Override public double pecsFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return pecsFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double pecsFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::pecsFromYieldToOptimalExercise => " +
				"Cant calc PECS from Yield to optimal exercise for bonds w emb option");

		return pecsFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double pecsFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return pecsFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromYieldSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double pecsFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return pecsFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double pecsFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::pecsFromYieldSpreadToOptimalExercise => " +
				"Cant calc PECS from Yield Spread to optimal exercise for bonds w emb option");

		return pecsFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double pecsFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return pecsFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromZSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblZSpread));
	}

	@Override public double pecsFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return pecsFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double pecsFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::pecsFromZSpreadToOptimalExercise => " +
				"Cant calc PECS from Z Spread to optimal exercise for bonds w emb option");

		return pecsFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double priceFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null == valParams || null == csqc || !org.drip.quant.common.NumberUtil.IsValid (dblWorkoutFactor)
			|| !org.drip.quant.common.NumberUtil.IsValid (dblASW))
			throw new java.lang.Exception ("BondComponent::priceFromASW => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		if (iValueDate >= iWorkoutDate)
			throw new java.lang.Exception ("BondComponent::priceFromASW => Invalid Inputs");

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqc.fundingState (fundingLabel());

		if (null == dcFunding)
			throw new java.lang.Exception ("BondComponent::priceFromASW => Invalid Inputs");

		org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = couponMetrics (iValueDate, valParams,
			csqc);

		if (null == cpcm) throw new java.lang.Exception ("BondComponent::priceFromASW => No CPCM");

		return dblWorkoutFactor - 100. * dcFunding.parSwapDV01 (iWorkoutDate) * (dblASW +
			dcFunding.estimateManifestMeasure ("SwapRate", iWorkoutDate) - cpcm.rate());
	}

	@Override public double priceFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return priceFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double priceFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::priceFromASWToOptimalExercise => " +
				"Cant calc Price from ASW to optimal exercise for bonds w emb option");

		return priceFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double priceFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return priceFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromBondBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double priceFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return priceFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double priceFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::priceFromBondBasisToOptimalExercise => " +
				"Cant calc Price from Bond Basis to optimal exercise for bonds w emb option");

		return priceFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double priceFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return priceFromCreditCurve (valParams, csqc, iWorkoutDate, dblWorkoutFactor, dblCreditBasis,
			false);
	}

	@Override public double priceFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return priceFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double priceFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::priceFromCreditBasisToOptimalExercise => " +
				"Cant calc Price from Credit Basis to optimal exercise for bonds w emb option");

		return priceFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double priceFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return priceFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
				dblDiscountMargin));
	}

	@Override public double priceFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		double dblDiscountMargin)
		throws java.lang.Exception
	{
		return priceFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double priceFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::priceFromDiscountMarginToOptimalExercise => " +
				"Cant calc Price from Discount Margin to optimal exercise for bonds w emb option");

		return priceFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double priceFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return priceFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromESpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double priceFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return priceFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double priceFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::priceFromESpreadToOptimalExercise => " +
				"Cant calc Price from E Spread to optimal exercise for bonds w emb option");

		return priceFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double priceFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return priceFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromGSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double priceFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return priceFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double priceFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::priceFromGSpreadToOptimalExercise => " +
				"Cant calc Price from G Spread to optimal exercise for bonds w emb option");

		return priceFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double priceFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return priceFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromISpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double priceFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return priceFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double priceFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::priceFromISpreadToOptimalExercise => " +
				"Cant calc Price from I Spread to optimal exercise for bonds w emb option");

		return priceFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double priceFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return priceFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromJSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double priceFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return priceFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double priceFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::priceFromJSpreadToOptimalExercise => " +
				"Cant calc Price from I Spread to optimal exercise for bonds w emb option");

		return priceFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double priceFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return priceFromZeroCurve (valParams, csqc, vcp, ZERO_OFF_OF_TREASURIES_DISCOUNT_CURVE,
			iWorkoutDate, dblWorkoutFactor, dblOAS);
	}

	@Override public double priceFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return priceFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double priceFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::priceFromOASToOptimalExercise => " +
				"Cant calc Price from OAS to optimal exercise for bonds w emb option");

		return priceFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double priceFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return priceFromCreditCurve (valParams, csqc, iWorkoutDate, dblWorkoutFactor, dblPECS, true);
	}

	@Override public double priceFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return priceFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double priceFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::priceFromPECSToOptimalExercise => " +
				"Cant calc Price from PECS to optimal exercise for bonds w emb option");

		return priceFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double priceFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return priceFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromTSYSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double priceFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return priceFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double priceFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::priceFromTSYSpreadToOptimalExercise => " +
				"Cant calc Price from TSY Spread to optimal exercise for bonds w emb option");

		return priceFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double priceFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		return priceFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYield, false);
	}

	@Override public double priceFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return priceFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double priceFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::priceFromYieldToOptimalExercise => " +
				"Cannot calc exercise px from yld for bonds w emb option");

		return priceFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double priceFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return priceFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromYieldSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double priceFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return priceFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double priceFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::priceFromYieldSpreadToOptimalExercise => " +
				"Cant calc Price from Yield Spread to optimal exercise for bonds w emb option");

		return priceFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double priceFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return priceFromZeroCurve (valParams, csqc, vcp, ZERO_OFF_OF_RATES_INSTRUMENTS_DISCOUNT_CURVE,
			iWorkoutDate, dblWorkoutFactor, dblZSpread);
	}

	@Override public double priceFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return priceFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double priceFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::priceFromZSpreadToOptimalExercise => " +
				"Cant calc Price from Z Spread to optimal exercise for bonds w emb option");

		return priceFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double tsySpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return tsySpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromASW
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double tsySpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return tsySpreadFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double tsySpreadFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::tsySpreadFromASWToOptimalExercise => " +
				"Cant calc TSY Spread from ASW to optimal exercise for bonds w emb option");

		return tsySpreadFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double tsySpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return tsySpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromBondBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double tsySpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return tsySpreadFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double tsySpreadFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::tsySpreadFromBondBasisToOptimalExercise => " +
				"Cant calc TSY Spread from Bond Basis to optimal exercise for bonds w emb option");

		return tsySpreadFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double tsySpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return tsySpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromCreditBasis (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double tsySpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return tsySpreadFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double tsySpreadFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::tsySpreadFromCreditBasisToOptimalExercise => " +
				"Cant calc TSY Spread from Credit Basis to optimal exercise for bonds w emb option");

		return tsySpreadFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double tsySpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return tsySpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
				dblDiscountMargin));
	}

	@Override public double tsySpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return tsySpreadFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double tsySpreadFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::tsySpreadFromDiscountMarginToOptimalExercise " +
				"=> Cant calc TSY Spread from Discount Margin to optimal exercise for bonds w emb option");

		return tsySpreadFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double tsySpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return tsySpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromESpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double tsySpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return tsySpreadFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double tsySpreadFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::tsySpreadFromESpreadToOptimalExercise => " +
				"Cant calc TSY Spread from E Spread to optimal exercise for bonds w emb option");

		return tsySpreadFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double tsySpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return tsySpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromGSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double tsySpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return tsySpreadFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double tsySpreadFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::tsySpreadFromGSpreadToOptimalExercise => " +
				"Cant calc TSY Spread from G Spread to optimal exercise for bonds w emb option");

		return tsySpreadFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double tsySpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return tsySpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromISpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double tsySpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return tsySpreadFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double tsySpreadFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::tsySpreadFromISpreadToOptimalExercise => " +
				"Cant calc TSY Spread from I Spread to optimal exercise for bonds w emb option");

		return tsySpreadFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double tsySpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return tsySpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromJSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double tsySpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return tsySpreadFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double tsySpreadFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::tsySpreadFromJSpreadToOptimalExercise => " +
				"Cant calc TSY Spread from J Spread to optimal exercise for bonds w emb option");

		return tsySpreadFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double tsySpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return tsySpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromOAS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double tsySpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return tsySpreadFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double tsySpreadFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::tsySpreadFromOASToOptimalExercise => " +
				"Cant calc TSY Spread from OAS to optimal exercise for bonds w emb option");

		return tsySpreadFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double tsySpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return tsySpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromPECS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double tsySpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return tsySpreadFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double tsySpreadFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::tsySpreadFromPECSToOptimalExercise => " +
				"Cant calc TSY Spread from PECS to optimal exercise for bonds w emb option");

		return tsySpreadFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double tsySpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return tsySpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromPrice
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPrice));
	}

	@Override public double tsySpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return tsySpreadFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double tsySpreadFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception
				("BondComponent::tsySpreadFromPriceToOptimalExercise => Cant determine Work-out");

		return tsySpreadFromPrice (valParams, csqc, vcp, wi.date(), wi.factor(), dblPrice);
	}

	@Override public double tsySpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		return dblYield - treasuryBenchmarkYield (valParams, csqc, iWorkoutDate);
	}

	@Override public double tsySpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return tsySpreadFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double tsySpreadFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::tsySpreadFromYieldToOptimalExercise => " +
				"Cant calc TSY Spread from Yield to optimal exercise for bonds w emb option");

		return tsySpreadFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double tsySpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return tsySpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromYieldSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double tsySpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return tsySpreadFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double tsySpreadFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::tsySpreadFromYieldSpreadToOptimalExercise => " +
				"Cant calc TSY Spread from Yield Spread to optimal exercise for bonds w emb option");

		return tsySpreadFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double tsySpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return tsySpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromZSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblZSpread));
	}

	@Override public double tsySpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return tsySpreadFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double tsySpreadFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::tsySpreadFromZSpreadToOptimalExercise => " +
				"Cant calc TSY Spread from Z Spread to optimal exercise for bonds w emb option");

		return tsySpreadFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double yieldFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return yieldFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromASW
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double yieldFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return yieldFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double yieldFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldFromASWToOptimalExercise => " +
				"Cant calc Yield from ASW to optimal exercise for bonds w emb option");

		return yieldFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double yieldFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBondBasis))
			throw new java.lang.Exception ("BondComponent::yieldFromBondBasis => Invalid Inputs");

		return yieldFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromFundingCurve
			(valParams, csqc, iWorkoutDate, dblWorkoutFactor, 0.)) + dblBondBasis;
	}

	@Override public double yieldFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return yieldFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double yieldFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldFromBondBasisToOptimalExercise => " +
				"Cant calc Yield from Bond Basis to optimal exercise for bonds w emb option");

		return yieldFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double yieldFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return yieldFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromCreditBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double yieldFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return yieldFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double yieldFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldFromCreditBasisToOptimalExercise => " +
				"Cant calc Yield from Credit Basis to optimal exercise for bonds w emb option");

		return yieldFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double yieldFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null == valParams || null == csqc || !org.drip.quant.common.NumberUtil.IsValid (dblWorkoutFactor)
			|| !org.drip.quant.common.NumberUtil.IsValid (dblDiscountMargin))
			throw new java.lang.Exception ("BondComponent::yieldFromDiscountMargin => Invalid inputs");

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqc.fundingState (fundingLabel());

		if (null == dcFunding)
			throw new java.lang.Exception ("BondComponent::yieldFromDiscountMargin => Invalid inputs");

		int iValueDate = valParams.valueDate();

		int iFreq = freq();

		return null == _floaterSetting ? dblDiscountMargin + dcFunding.libor (iValueDate, ((int) (12. / (0 ==
			iFreq ? 2 : iFreq))) + "M") : dblDiscountMargin + indexRate (iValueDate, csqc,
				(org.drip.analytics.cashflow.CompositeFloatingPeriod) currentPeriod (iValueDate));
	}

	@Override public double yieldFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return yieldFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double yieldFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return yieldFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double yieldFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return yieldFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromZeroCurve
			(valParams, csqc, vcp, ZERO_OFF_OF_RATES_INSTRUMENTS_DISCOUNT_CURVE, iWorkoutDate,
				dblWorkoutFactor, dblESpread));
	}

	@Override public double yieldFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return yieldFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double yieldFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldFromESpreadToOptimalExercise => " +
				"Cant calc Yield from E Spread to optimal exercise for bonds w emb option");

		return yieldFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double yieldFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblGSpread) || valParams.valueDate() >= iWorkoutDate
			|| null == csqc)
			throw new java.lang.Exception ("BondComponent::yieldFromGSpread => Invalid Inputs");

		org.drip.state.govvie.GovvieCurve gc = csqc.govvieState (govvieLabel());

		if (null == gc) throw new java.lang.Exception ("BondComponent::yieldFromGSpread => Invalid Inputs");

		return gc.yield (iWorkoutDate) + dblGSpread;
	}

	@Override public double yieldFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return yieldFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double yieldFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldFromGSpreadToOptimalExercise => " +
				"Cant calc Yield from G Spread to optimal exercise for bonds w emb option");

		return yieldFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double yieldFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null == valParams || valParams.valueDate() >= iWorkoutDate || null == csqc ||
			!org.drip.quant.common.NumberUtil.IsValid (dblISpread))
			throw new java.lang.Exception ("BondComponent::yieldFromISpread => Invalid Inputs");

		org.drip.state.discount.MergedDiscountForwardCurve dc = csqc.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (payCurrency()));

		if (null == dc) throw new java.lang.Exception ("BondComponent::yieldFromISpread => Invalid Inputs");

		return dc.estimateManifestMeasure ("SwapRate", iWorkoutDate) + dblISpread;
	}

	@Override public double yieldFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return yieldFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double yieldFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return yieldFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double yieldFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null == valParams || valParams.valueDate() >= iWorkoutDate || null == csqc ||
			!org.drip.quant.common.NumberUtil.IsValid (dblJSpread))
			throw new java.lang.Exception ("BondComponent::yieldFromJSpread => Invalid Inputs");

		org.drip.state.govvie.GovvieCurve gc = csqc.govvieState (govvieLabel());

		if (null == gc) throw new java.lang.Exception ("BondComponent::yieldFromJSpread => Invalid Inputs");

		return dblJSpread + gc.yield (weightedAverageMaturityDate (valParams, csqc, iWorkoutDate,
			dblWorkoutFactor));
	}

	@Override public double yieldFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return yieldFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double yieldFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return yieldFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double yieldFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return yieldFromOAS (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromOAS (valParams,
			csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double yieldFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return yieldFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double yieldFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldFromOASToOptimalExercise => " +
				"Cant calc Yield from OAS to optimal exercise for bonds w emb option");

		return yieldFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double yieldFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return yieldFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromPECS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double yieldFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return yieldFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double yieldFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldFromPECSToOptimalExercise => " +
				"Cant calc Yield from PECS to optimal exercise for bonds w emb option");

		return yieldFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double yieldFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return new BondCalibrator (this, false).calibrateYieldFromPrice (valParams, csqc, vcp, iWorkoutDate,
			dblWorkoutFactor, dblPrice);
	}

	@Override public double yieldFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return yieldFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double yieldFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception ("BondComponent::yieldFromPriceToOptimalExercise => " +
				"Cant calc Workout from Price to optimal exercise for bonds w emb option");

		return wi.yield();
	}

	@Override public double yieldFromPriceTC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return new BondCalibrator (this, true).calibrateYieldFromPrice (valParams, csqc, vcp, iWorkoutDate,
			dblWorkoutFactor, dblPrice);
	}

	@Override public double yieldFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblTSYSpread) || valParams.valueDate() >=
			iWorkoutDate)
			throw new java.lang.Exception ("BondComponent::calcYieldFromTSYSpread => Invalid Inputs");

		return treasuryBenchmarkYield (valParams, csqc, iWorkoutDate) + dblTSYSpread;
	}

	@Override public double yieldFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return yieldFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double yieldFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldFromTSYSpreadToOptimalExercise => " +
				"Cant calc Yield from TSY Spread to optimal exercise for bonds w emb option");

		return yieldFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double yieldFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblYieldSpread) || valParams.valueDate() >=
			iWorkoutDate)
			throw new java.lang.Exception ("BondComponent::yieldFromYieldSpread => Invalid Inputs");

		return yieldFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromFundingCurve
			(valParams, csqc, iWorkoutDate, dblWorkoutFactor, 0.)) + dblYieldSpread;
	}

	@Override public double yieldFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return yieldFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double yieldFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldFromYieldSpreadToOptimalExercise => " +
				"Cant calc Yield from Yield Spread to optimal exercise for bonds w emb option");

		return yieldFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double yieldFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return yieldFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromZeroCurve
			(valParams, csqc, vcp, ZERO_OFF_OF_RATES_INSTRUMENTS_DISCOUNT_CURVE, iWorkoutDate,
				dblWorkoutFactor, dblZSpread));
	}

	@Override public double yieldFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return yieldFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double yieldFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldFromZSpreadToOptimalExercise => " +
				"Cant calc Yield from Z Spread to optimal exercise for bonds w emb option");

		return yieldFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double yield01FromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return yield01FromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromASW
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double yield01FromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return yield01FromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double yield01FromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yield01FromASWToOptimalExercise => " +
				"Cant calc Yield from ASW to optimal exercise for bonds w emb option");

		return yield01FromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double yield01FromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return yield01FromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromBondBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double yield01FromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return yieldFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double yield01FromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yield01FromBondBasisToOptimalExercise => " +
				"Cant calc Yield01 from Bond Basis to optimal exercise for bonds w emb option");

		return yield01FromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double yield01FromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return yield01FromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromCreditBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double yield01FromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return yield01FromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double yield01FromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yield01FromCreditBasisToOptimalExercise => " +
				"Cant calc Yield01 from Credit Basis to optimal exercise for bonds w emb option");

		return yield01FromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double yield01FromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return yield01FromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
				dblDiscountMargin));
	}

	@Override public double yield01FromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return yield01FromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double yield01FromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yield01FromDiscountMarginToOptimalExercise =>" +
				" Cant calc Yield01 from Discount Margin to optimal exercise for bonds w emb option");

		return yield01FromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double yield01FromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return yield01FromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromESpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double yield01FromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return yield01FromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double yield01FromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yield01FromESpreadToOptimalExercise => " +
				"Cant calc Yield01 from E Spread to optimal exercise for bonds w emb option");

		return yield01FromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double yield01FromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return yield01FromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromGSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double yield01FromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return yield01FromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double yield01FromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yield01FromGSpreadToOptimalExercise => " +
				"Cant calc Yield01 from G Spread to optimal exercise for bonds w emb option");

		return yield01FromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double yield01FromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return yield01FromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromISpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double yield01FromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return yield01FromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double yield01FromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yield01FromISpreadToOptimalExercise => " +
				"Cant calc Yield01 from I Spread to optimal exercise for bonds w emb option");

		return yield01FromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double yield01FromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return yield01FromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromJSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double yield01FromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return yield01FromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double yield01FromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yield01FromJSpreadToOptimalExercise => " +
				"Cant calc Yield01 from J Spread to optimal exercise for bonds w emb option");

		return yield01FromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double yield01FromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return yield01FromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromOAS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double yield01FromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return yield01FromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double yield01FromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yield01FromOASToOptimalExercise => " +
				"Cant calc Yield01 from OAS to optimal exercise for bonds w emb option");

		return yield01FromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double yield01FromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return yield01FromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromPECS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double yield01FromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return yield01FromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double yield01FromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yield01FromPECSToOptimalExercise => " +
				"Cant calc Yield01 from PECS to optimal exercise for bonds w emb option");

		return yield01FromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double yield01FromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return yield01FromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromPrice
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPrice));
	}

	@Override public double yield01FromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return yield01FromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double yield01FromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception ("BondComponent::yield01FromPriceToOptimalExercise => " +
				"Cant calc Workout from Price to optimal exercise for bonds w emb option");

		return yield01FromPrice (valParams, csqc, vcp, wi.date(), wi.factor(), dblPrice);
	}

	@Override public double yield01FromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return yield01FromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromTSYSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double yield01FromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return yield01FromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double yield01FromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yield01FromTSYSpreadToOptimalExercise => " +
				"Cant calc Yield01 from TSY Spread to optimal exercise for bonds w emb option");

		return yield01FromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double yield01FromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblYield))
			throw new java.lang.Exception ("BondComponent::yield01FromYield => Invalid Inputs");

		return priceFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYield) -
			priceFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYield + 0.0001);
	}

	@Override public double yield01FromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return yield01FromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double yield01FromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yield01FromYieldToOptimalExercise => " +
				"Cant calc Yield01 from Yield to optimal exercise for bonds w emb option");

		return yield01FromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double yield01FromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return yield01FromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromYieldSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double yield01FromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return yield01FromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double yield01FromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yield01FromYieldSpreadToOptimalExercise => " +
				"Cant calc Yield01 from Yield Spread to optimal exercise for bonds w emb option");

		return yield01FromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double yield01FromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return yield01FromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromZSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblZSpread));
	}

	@Override public double yield01FromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return yield01FromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double yield01FromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yield01FromZSpreadToOptimalExercise => " +
				"Cant calc Yield01 from Z Spread to optimal exercise for bonds w emb option");

		return yield01FromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double yieldSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return yieldSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromASW
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double yieldSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return yieldSpreadFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double yieldSpreadFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldSpreadFromASWToOptimalExercise => " +
				"Cant calc Yield Spread from ASW to optimal exercise for bonds w emb option");

		return yieldSpreadFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double yieldSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return yieldSpreadFromBondBasis (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromBondBasis (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double yieldSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return yieldSpreadFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double yieldSpreadFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldSpreadFromBondBasisToOptimalExercise => "
				+ "Cant calc Yield Spread from Bond Basis to optimal exercise for bonds w emb option");

		return yieldSpreadFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double yieldSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return yieldSpreadFromCreditBasis (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromCreditBasis (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double yieldSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return yieldSpreadFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblCreditBasis);
	}

	@Override public double yieldSpreadFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldSpreadFromCreditBasisToOptimalExercise " +
				"=> Cant calc Yield Spread from Credit Basis to optimal exercise for bonds w emb option");

		return yieldSpreadFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblCreditBasis);
	}

	@Override public double yieldSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return yieldSpreadFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
				dblDiscountMargin));
	}

	@Override public double yieldSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return yieldSpreadFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double yieldSpreadFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::yieldSpreadFromDiscountMarginToOptimalExercise => " +
					"Cant calc Yield Spread from Disc Margin to optimal exercise for bonds w emb option");

		return yieldSpreadFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double yieldSpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblESpread)
		throws java.lang.Exception
	{
		return yieldSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromESpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblESpread));
	}

	@Override public double yieldSpreadFromESpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		return yieldSpreadFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double yieldSpreadFromESpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblESpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldSpreadFromESpreadToOptimalExercise => " +
				"Cant calc Yield Spread from E Spread to optimal exercise for bonds w emb option");

		return yieldSpreadFromESpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblESpread);
	}

	@Override public double yieldSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return yieldSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromGSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double yieldSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return yieldSpreadFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double yieldSpreadFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldSpreadFromGSpreadToOptimalExercise => " +
				"Cant calc Yield Spread from G Spread to optimal exercise for bonds w emb option");

		return yieldSpreadFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double yieldSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return yieldSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromISpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double yieldSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return yieldSpreadFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double yieldSpreadFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldSpreadFromISpreadToOptimalExercise => " +
				"Cant calc Yield Spread from I Spread to optimal exercise for bonds w emb option");

		return yieldSpreadFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double yieldSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return yieldSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromJSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double yieldSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return yieldSpreadFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double yieldSpreadFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldSpreadFromJSpreadToOptimalExercise => " +
				"Cant calc Yield Spread from J Spread to optimal exercise for bonds w emb option");

		return yieldSpreadFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double yieldSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return yieldSpreadFromOAS (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromOAS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double yieldSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return yieldSpreadFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double yieldSpreadFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldSpreadFromOASToOptimalExercise => " +
				"Cant calc Yield Spread from OAS to optimal exercise for bonds w emb option");

		return yieldSpreadFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double yieldSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return yieldSpreadFromPECS (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromPECS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double yieldSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return yieldSpreadFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double yieldSpreadFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldSpreadFromPECSToOptimalExercise => " +
				"Cant calc Yield Spread from PECS to optimal exercise for bonds w emb option");

		return yieldSpreadFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double yieldSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return yieldSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromPrice
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPrice));
	}

	@Override public double yieldSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return yieldSpreadFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double yieldSpreadFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception ("BondComponent::yieldSpreadFromPriceToOptimalExercise => " +
				"Cant calc Workout from Price to optimal exercise for bonds w emb option");

		return yieldSpreadFromPrice (valParams, csqc, vcp, wi.date(), wi.factor(), dblPrice);
	}

	@Override public double yieldSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return yieldSpreadFromTSYSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			yieldFromTSYSpread (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double yieldSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return yieldSpreadFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double yieldSpreadFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldSpreadFromTSYSpreadToOptimalExercise => " +
				"Cant calc Yield Spread from TSY Spread to optimal exercise for bonds w emb option");

		return yieldSpreadFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double yieldSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblYield))
			throw new java.lang.Exception ("BondComponent::yieldSpreadFromYield => Invalid Inputs");

		return dblYield - yieldFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromFundingCurve (valParams, csqc, iWorkoutDate, dblWorkoutFactor, 0.));
	}

	@Override public double yieldSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return yieldSpreadFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double yieldSpreadFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldSpreadFromYieldToOptimalExercise => " +
				"Cant calc Yield Spread from Yield to optimal exercise for bonds w emb option");

		return yieldSpreadFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double yieldSpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return yieldSpreadFromYield (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, yieldFromZSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblZSpread));
	}

	@Override public double yieldSpreadFromZSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		return yieldSpreadFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double yieldSpreadFromZSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblZSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::yieldSpreadFromZSpreadToOptimalExercise => " +
				"Cant calc Yield Spread from Z Spread to optimal exercise for bonds w emb option");

		return yieldSpreadFromZSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblZSpread);
	}

	@Override public double zSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblASW)
		throws java.lang.Exception
	{
		return zSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromASW
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblASW));
	}

	@Override public double zSpreadFromASW (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		return zSpreadFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double zSpreadFromASWToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblASW)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::zSpreadFromASWToOptimalExercise => " +
				"Cant calc Z Spread from ASW to optimal exercise for bonds w emb option");

		return zSpreadFromASW (valParams, csqc, vcp, maturityDate().julian(), 1., dblASW);
	}

	@Override public double zSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return zSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromBondBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblBondBasis));
	}

	@Override public double zSpreadFromBondBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		return zSpreadFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double zSpreadFromBondBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblBondBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::zSpreadFromBondBasisToOptimalExercise => " +
				"Cant calc Z Spread from Bond Basis to optimal exercise for bonds w emb option");

		return zSpreadFromBondBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblBondBasis);
	}

	@Override public double zSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return zSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromCreditBasis
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblCreditBasis));
	}

	@Override public double zSpreadFromCreditBasis (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		return zSpreadFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double zSpreadFromCreditBasisToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCreditBasis)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::zSpreadFromCreditBasisToOptimalExercise => " +
				"Cant calc Z Spread from Credit Basis to optimal exercise for bonds w emb option");

		return zSpreadFromCreditBasis (valParams, csqc, vcp, maturityDate().julian(), 1., dblCreditBasis);
	}

	@Override public double zSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return zSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
			priceFromDiscountMargin (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor,
				dblDiscountMargin));
	}

	@Override public double zSpreadFromDiscountMargin (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		return zSpreadFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double zSpreadFromDiscountMarginToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblDiscountMargin)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception
				("BondComponent::zSpreadFromDiscountMarginToOptimalExercise => " +
					"Cant calc Z Spread from Discount Margin to optimal exercise for bonds w emb option");

		return zSpreadFromDiscountMargin (valParams, csqc, vcp, maturityDate().julian(), 1.,
			dblDiscountMargin);
	}

	@Override public double zSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return zSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromGSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblGSpread));
	}

	@Override public double zSpreadFromGSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		return zSpreadFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double zSpreadFromGSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblGSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::zSpreadFromGSpreadToOptimalExercise => " +
				"Cant calc Z Spread from G Spread to optimal exercise for bonds w emb option");

		return zSpreadFromGSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblGSpread);
	}

	@Override public double zSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblISpread)
		throws java.lang.Exception
	{
		return zSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromISpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblISpread));
	}

	@Override public double zSpreadFromISpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		return zSpreadFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double zSpreadFromISpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblISpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::zSpreadFromISpreadToOptimalExercise => " +
				"Cant calc Z Spread from I Spread to optimal exercise for bonds w emb option");

		return zSpreadFromISpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblISpread);
	}

	@Override public double zSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return zSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromJSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblJSpread));
	}

	@Override public double zSpreadFromJSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		return zSpreadFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double zSpreadFromJSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblJSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::zSpreadFromJSpreadToOptimalExercise => " +
				"Cant calc Z Spread from J Spread to optimal exercise for bonds w emb option");

		return zSpreadFromJSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblJSpread);
	}

	@Override public double zSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblOAS)
		throws java.lang.Exception
	{
		return zSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromOAS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblOAS));
	}

	@Override public double zSpreadFromOAS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		return zSpreadFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double zSpreadFromOASToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblOAS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::zSpreadFromOASToOptimalExercise => " +
				"Cant calc Z Spread from OAS to optimal exercise for bonds w emb option");

		return zSpreadFromOAS (valParams, csqc, vcp, maturityDate().julian(), 1., dblOAS);
	}

	@Override public double zSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPECS)
		throws java.lang.Exception
	{
		return zSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromPECS
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblPECS));
	}

	@Override public double zSpreadFromPECS (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		return zSpreadFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double zSpreadFromPECSToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPECS)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::zSpreadFromPECSToOptimalExercise => " +
				"Cant calc Z Spread from PECS to optimal exercise for bonds w emb option");

		return zSpreadFromPECS (valParams, csqc, vcp, maturityDate().julian(), 1., dblPECS);
	}

	@Override public double zSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblPrice)
		throws java.lang.Exception
	{
		return new BondCalibrator (this, false).calibrateZSpreadFromPrice (valParams, csqc, vcp,
			ZERO_OFF_OF_RATES_INSTRUMENTS_DISCOUNT_CURVE, iWorkoutDate, dblWorkoutFactor, dblPrice);
	}

	@Override public double zSpreadFromPrice (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		return zSpreadFromPrice (valParams, csqc, vcp, maturityDate().julian(), 1., dblPrice);
	}

	@Override public double zSpreadFromPriceToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblPrice)
		throws java.lang.Exception
	{
		org.drip.param.valuation.WorkoutInfo wi = exerciseYieldFromPrice (valParams, csqc, vcp, dblPrice);

		if (null == wi)
			throw new java.lang.Exception ("BondComponent::zSpreadFromPriceToOptimalExercise => " +
				"Cant calc Workout from Price to optimal exercise for bonds w emb option");

		return zSpreadFromPrice (valParams, csqc, vcp, wi.date(), wi.factor(), dblPrice);
	}

	@Override public double zSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return zSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromTSYSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblTSYSpread));
	}

	@Override public double zSpreadFromTSYSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		return zSpreadFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double zSpreadFromTSYSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblTSYSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::zSpreadFromTSYSpreadToOptimalExercise => " +
				"Cant calc Z Spread from TSY Spread to optimal exercise for bonds w emb option");

		return zSpreadFromTSYSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblTSYSpread);
	}

	@Override public double zSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYield)
		throws java.lang.Exception
	{
		return zSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromYield
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYield));
	}

	@Override public double zSpreadFromYield (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		return zSpreadFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double zSpreadFromYieldToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYield)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::zSpreadFromYieldToOptimalExercise => " +
				"Cant calc Z Spread from Yield to optimal exercise for bonds w emb option");

		return zSpreadFromYield (valParams, csqc, vcp, maturityDate().julian(), 1., dblYield);
	}

	@Override public double zSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final int iWorkoutDate,
		final double dblWorkoutFactor,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return zSpreadFromPrice (valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, priceFromYieldSpread
			(valParams, csqc, vcp, iWorkoutDate, dblWorkoutFactor, dblYieldSpread));
	}

	@Override public double zSpreadFromYieldSpread (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		return zSpreadFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public double zSpreadFromYieldSpreadToOptimalExercise (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblYieldSpread)
		throws java.lang.Exception
	{
		if (null != _eosCall || null != _eosPut)
			throw new java.lang.Exception ("BondComponent::zSpreadFromYieldSpreadToOptimalExercise => " +
				"Cant calc Z Spread from Yield Spread to optimal exercise for bonds w emb option");

		return zSpreadFromYieldSpread (valParams, csqc, vcp, maturityDate().julian(), 1., dblYieldSpread);
	}

	@Override public org.drip.analytics.output.BondRVMeasures standardMeasures (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.param.valuation.WorkoutInfo wi,
		final double dblPrice)
	{
		if (null == valParams || null == csqs || null == wi || !org.drip.quant.common.NumberUtil.IsValid
			(dblPrice))
			return null;

		int iWorkoutDate = wi.date();

		double dblWorkoutYield = wi.yield();

		double dblWorkoutFactor = wi.factor();

		if (valParams.valueDate() >= iWorkoutDate) return null;

		double dblASW = java.lang.Double.NaN;
		double dblPECS = java.lang.Double.NaN;
		double dblGSpread = java.lang.Double.NaN;
		double dblISpread = java.lang.Double.NaN;
		double dblYield01 = java.lang.Double.NaN;
		double dblZSpread = java.lang.Double.NaN;
		double dblOASpread = java.lang.Double.NaN;
		double dblBondBasis = java.lang.Double.NaN;
		double dblConvexity = java.lang.Double.NaN;
		double dblTSYSpread = java.lang.Double.NaN;
		double dblCreditBasis = java.lang.Double.NaN;
		double dblDiscountMargin = java.lang.Double.NaN;
		double dblMacaulayDuration = java.lang.Double.NaN;
		double dblModifiedDuration = java.lang.Double.NaN;

		try {
			dblDiscountMargin = discountMarginFromYield (valParams, csqs, vcp, iWorkoutDate,
				dblWorkoutFactor, dblWorkoutYield);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		if (null == _floaterSetting) {
			try {
				dblZSpread = zSpreadFromPrice (valParams, csqs, vcp, iWorkoutDate, dblWorkoutFactor,
					dblPrice);
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();
			}
		}

		try {
			dblOASpread = oasFromPrice (valParams, csqs, vcp, iWorkoutDate, dblWorkoutFactor, dblPrice);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		try {
			dblISpread = iSpreadFromYield (valParams, csqs, vcp, iWorkoutDate, dblWorkoutFactor,
				dblWorkoutYield);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		try {
			dblGSpread = gSpreadFromYield (valParams, csqs, vcp, iWorkoutDate, dblWorkoutFactor,
				dblWorkoutYield);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		try {
			dblTSYSpread = tsySpreadFromYield (valParams, csqs, vcp, iWorkoutDate, dblWorkoutFactor,
				dblWorkoutYield);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		try {
			dblMacaulayDuration = macaulayDurationFromPrice (valParams, csqs, vcp, iWorkoutDate,
				dblWorkoutFactor, dblPrice);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		try {
			dblModifiedDuration = modifiedDurationFromPrice (valParams, csqs, vcp, iWorkoutDate,
				dblWorkoutFactor, dblPrice);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		try {
			dblASW = aswFromPrice (valParams, csqs, vcp, iWorkoutDate, dblWorkoutFactor, dblPrice);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		try {
			dblConvexity = convexityFromPrice (valParams, csqs, vcp, iWorkoutDate, dblWorkoutFactor,
				dblPrice);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		try {
			dblCreditBasis = creditBasisFromPrice (valParams, csqs, vcp, iWorkoutDate, dblWorkoutFactor,
				dblPrice);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		try {
			// dblPECS = pecsFromPrice (valParams, csqs, vcp, iWorkoutDate, dblWorkoutFactor, dblPrice);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		try {
			dblBondBasis = bondBasisFromYield (valParams, csqs, vcp, iWorkoutDate, dblWorkoutFactor,
				dblWorkoutYield);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		try {
			dblYield01 = yield01FromYield (valParams, csqs, vcp, iWorkoutDate, dblWorkoutFactor,
				dblWorkoutYield);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		try {
			return new org.drip.analytics.output.BondRVMeasures (dblPrice, dblBondBasis, dblZSpread,
				dblGSpread, dblISpread, dblOASpread, dblTSYSpread, dblDiscountMargin, dblASW, dblCreditBasis,
					dblPECS, dblYield01, dblModifiedDuration, dblMacaulayDuration, dblConvexity, wi);
		} catch (java.lang.Exception e) {
			if (!s_bSuppressErrors) e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == valParams || null == csqs) return null;

		if (null != pricerParams) {
			org.drip.param.definition.CalibrationParams calibParams = pricerParams.calibParams();

			if (null != calibParams) {
				org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapCalibMeasures =
					calibMeasures (valParams, pricerParams, csqs, vcp);

				if (null != mapCalibMeasures && mapCalibMeasures.containsKey (calibParams.measure()))
					return mapCalibMeasures;
			}
		}

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapMeasures = fairMeasures
			(valParams, pricerParams, csqs, vcp);

		if (null == mapMeasures) return null;

		java.lang.String strName = name();

		org.drip.param.definition.ProductQuote pq = csqs.productQuote (strName);

		if (null == pq) return mapMeasures;

		int iMaturityDate = maturityDate().julian();

		if (null == _floaterSetting) {
			double dblParSpread = (mapMeasures.get ("FairDirtyPV") - mapMeasures.get ("FairParPV") -
				mapMeasures.get ("FairPrincipalPV")) / mapMeasures.get ("FairDirtyDV01");

			mapMeasures.put ("ParSpread", dblParSpread);

			mapMeasures.put ("FairParSpread", dblParSpread);
		} else {
			double dblCleanIndexCouponPV = mapMeasures.containsKey ("FairRiskyCleanIndexCouponPV") ?
				mapMeasures.get ("FairRiskyCleanIndexCouponPV") : mapMeasures.get
					("FairRisklessCleanIndexCouponPV");

			double dblZeroDiscountMargin = (mapMeasures.get ("FairCleanPV") - mapMeasures.get ("FairParPV") -
				dblCleanIndexCouponPV - mapMeasures.get ("FairPrincipalPV")) / mapMeasures.get
					("FairCleanDV01");

			mapMeasures.put ("ZeroDiscountMargin", dblZeroDiscountMargin);

			mapMeasures.put ("FairZeroDiscountMargin", dblZeroDiscountMargin);
		}

		org.drip.param.valuation.WorkoutInfo wiMarket = null;

		if (pq.containsQuote ("Price")) {
			double dblMarketPrice = pq.quote ("Price").value ("mid");

			mapMeasures.put ("MarketInputType=CleanPrice", dblMarketPrice);

			wiMarket = exerciseYieldFromPrice (valParams, csqs, vcp, dblMarketPrice);
		} else if (pq.containsQuote ("CleanPrice")) {
			double dblCleanMarketPrice = pq.quote ("CleanPrice").value ("mid");

			mapMeasures.put ("MarketInputType=CleanPrice", dblCleanMarketPrice);

			wiMarket = exerciseYieldFromPrice (valParams, csqs, vcp, dblCleanMarketPrice);
		} else if (pq.containsQuote ("QuotedMargin")) {
			double dblQuotedMargin = pq.quote ("QuotedMargin").value ("mid");

			mapMeasures.put ("MarketInputType=QuotedMargin", dblQuotedMargin);

			try {
				wiMarket = exerciseYieldFromPrice (valParams, csqs, vcp, priceFromDiscountMargin (valParams,
					csqs, vcp, dblQuotedMargin));
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();
			}
		} else if (pq.containsQuote ("DirtyPrice")) {
			try {
				double dblDirtyMarketPrice = pq.quote ("DirtyPrice").value ("mid");

				mapMeasures.put ("MarketInputType=DirtyPrice", dblDirtyMarketPrice);

				wiMarket = exerciseYieldFromPrice (valParams, csqs, vcp, dblDirtyMarketPrice - accrued
					(valParams.valueDate(), csqs));
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();

				wiMarket = null;
			}
		} else if (pq.containsQuote ("TSYSpread")) {
			try {
				double dblTSYSpread = pq.quote ("TSYSpread").value ("mid");

				mapMeasures.put ("MarketInputType=TSYSpread", dblTSYSpread);

				wiMarket = new org.drip.param.valuation.WorkoutInfo (iMaturityDate, treasuryBenchmarkYield
					(valParams, csqs, iMaturityDate) + dblTSYSpread, 1.,
						org.drip.param.valuation.WorkoutInfo.WO_TYPE_MATURITY);
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();

				wiMarket = null;
			}
		} else if (pq.containsQuote ("Yield")) {
			try {
				double dblYield = pq.quote ("Yield").value ("mid");

				mapMeasures.put ("MarketInputType=Yield", dblYield);

				wiMarket = new org.drip.param.valuation.WorkoutInfo (iMaturityDate, dblYield, 1.,
					org.drip.param.valuation.WorkoutInfo.WO_TYPE_MATURITY);
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();

				wiMarket = null;
			}
		} else if (pq.containsQuote ("ZSpread")) {
			try {
				double dblZSpread = pq.quote ("ZSpread").value ("mid");

				mapMeasures.put ("MarketInputType=ZSpread", dblZSpread);

				wiMarket = new org.drip.param.valuation.WorkoutInfo (iMaturityDate, yieldFromZSpread
					(valParams, csqs, vcp, dblZSpread), 1.,
						org.drip.param.valuation.WorkoutInfo.WO_TYPE_MATURITY);
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();

				wiMarket = null;
			}
		} else if (pq.containsQuote ("ISpread")) {
			try {
				double dblISpread = pq.quote ("ISpread").value ("mid");

				mapMeasures.put ("MarketInputType=ISpread", dblISpread);

				wiMarket = new org.drip.param.valuation.WorkoutInfo (iMaturityDate, yieldFromISpread
					(valParams, csqs, vcp, dblISpread), 1.,
						org.drip.param.valuation.WorkoutInfo.WO_TYPE_MATURITY);
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();

				wiMarket = null;
			}
		} else if (pq.containsQuote ("CreditBasis")) {
			try {
				double dblCreditBasis = pq.quote ("CreditBasis").value ("mid");

				mapMeasures.put ("MarketInputType=CreditBasis", dblCreditBasis);

				wiMarket = new org.drip.param.valuation.WorkoutInfo (iMaturityDate, yieldFromCreditBasis
					(valParams, csqs, vcp, dblCreditBasis), 1.,
						org.drip.param.valuation.WorkoutInfo.WO_TYPE_MATURITY);
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();

				wiMarket = null;
			}
		} else if (pq.containsQuote ("PECS")) {
			try {
				double dblCreditBasis = pq.quote ("PECS").value ("mid");

				mapMeasures.put ("MarketInputType=PECS", dblCreditBasis);

				wiMarket = new org.drip.param.valuation.WorkoutInfo (iMaturityDate, yieldFromPECS (valParams,
					csqs, vcp, dblCreditBasis), 1., org.drip.param.valuation.WorkoutInfo.WO_TYPE_MATURITY);
			} catch (java.lang.Exception e) {
				if (!s_bSuppressErrors) e.printStackTrace();

				wiMarket = null;
			}
		}

		if (null != wiMarket) {
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapWorkoutMeasures =
				marketMeasures (valParams, pricerParams, csqs, vcp, wiMarket);

			if (null == _floaterSetting) {
				double dblParSpread = (mapWorkoutMeasures.get ("Price") - mapMeasures.get ("FairParPV") -
					mapMeasures.get ("FairPrincipalPV")) / mapMeasures.get ("FairCleanDV01");

				mapMeasures.put ("ParSpread", dblParSpread);

				mapMeasures.put ("MarketParSpread", dblParSpread);
			} else {
				double dblCleanIndexCouponPV = mapMeasures.containsKey ("FairRiskyCleanIndexCouponPV") ?
					mapMeasures.get ("FairRiskyCleanIndexCouponPV") : mapMeasures.get
						("FairRisklessCleanIndexCouponPV");

				double dblZeroDiscountMargin = (mapMeasures.get ("Price") - mapMeasures.get ("FairParPV") -
					dblCleanIndexCouponPV - mapMeasures.get ("FairPrincipalPV")) / mapMeasures.get
						("FairCleanDV01");

				mapMeasures.put ("ZeroDiscountMargin", dblZeroDiscountMargin);

				mapMeasures.put ("MarketZeroDiscountMargin", dblZeroDiscountMargin);
			}

			org.drip.quant.common.CollectionUtil.MergeWithMain (mapMeasures, mapWorkoutMeasures);

			org.drip.state.credit.CreditCurve cc = csqs.creditState (creditLabel());

			if (null != mapMeasures.get ("FairYield")) {
				org.drip.param.market.CurveSurfaceQuoteContainer csqsMarket =
					org.drip.param.creator.MarketParamsBuilder.Create
						((org.drip.state.discount.MergedDiscountForwardCurve) csqs.fundingState
							(fundingLabel()).parallelShiftQuantificationMetric (wiMarket.yield() -
								mapMeasures.get ("FairYield")), csqs.govvieState (govvieLabel()), cc,
									strName, csqs.productQuote (strName), csqs.quoteMap(), csqs.fixings());

				if (null != csqsMarket) {
					org.drip.analytics.output.BondWorkoutMeasures bwmMarket = workoutMeasures (valParams,
						pricerParams, csqsMarket, wiMarket.date(), wiMarket.factor());

					if (null != bwmMarket) {
						org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapMarketMeasures
							= bwmMarket.toMap ("");

						org.drip.quant.common.CollectionUtil.MergeWithMain (mapMarketMeasures,
							org.drip.quant.common.CollectionUtil.PrefixKeys (mapMarketMeasures, "Market"));

						org.drip.quant.common.CollectionUtil.MergeWithMain (mapMeasures, mapMarketMeasures);
					}
				}
			}
		}

		return mapMeasures;
	}

	@Override public java.util.Set<java.lang.String> measureNames()
	{
		java.util.Set<java.lang.String> setstrMeasureNames = new java.util.TreeSet<java.lang.String>();

		setstrMeasureNames.add ("Accrued");

		setstrMeasureNames.add ("Accrued01");

		setstrMeasureNames.add ("AssetSwapSpread");

		setstrMeasureNames.add ("ASW");

		setstrMeasureNames.add ("BondBasis");

		setstrMeasureNames.add ("CleanCouponPV");

		setstrMeasureNames.add ("CleanDV01");

		setstrMeasureNames.add ("CleanIndexCouponPV");

		setstrMeasureNames.add ("CleanPrice");

		setstrMeasureNames.add ("CleanPV");

		setstrMeasureNames.add ("Convexity");

		setstrMeasureNames.add ("CreditRisklessParPV");

		setstrMeasureNames.add ("CreditRisklessPrincipalPV");

		setstrMeasureNames.add ("CreditRiskyParPV");

		setstrMeasureNames.add ("CreditRiskyPrincipalPV");

		setstrMeasureNames.add ("CreditBasis");

		setstrMeasureNames.add ("DiscountMargin");

		setstrMeasureNames.add ("DefaultExposure");

		setstrMeasureNames.add ("DefaultExposureNoRec");

		setstrMeasureNames.add ("DirtyCouponPV");

		setstrMeasureNames.add ("DirtyDV01");

		setstrMeasureNames.add ("DirtyIndexCouponPV");

		setstrMeasureNames.add ("DirtyPrice");

		setstrMeasureNames.add ("DirtyPV");

		setstrMeasureNames.add ("Duration");

		setstrMeasureNames.add ("DV01");

		setstrMeasureNames.add ("ExpectedRecovery");

		setstrMeasureNames.add ("FairAccrued");

		setstrMeasureNames.add ("FairAccrued01");

		setstrMeasureNames.add ("FairAssetSwapSpread");

		setstrMeasureNames.add ("FairASW");

		setstrMeasureNames.add ("FairBondBasis");

		setstrMeasureNames.add ("FairCleanCouponPV");

		setstrMeasureNames.add ("FairCleanDV01");

		setstrMeasureNames.add ("FairCleanIndexCouponPV");

		setstrMeasureNames.add ("FairCleanPrice");

		setstrMeasureNames.add ("FairCleanPV");

		setstrMeasureNames.add ("FairConvexity");

		setstrMeasureNames.add ("FairCreditBasis");

		setstrMeasureNames.add ("FairCreditRisklessParPV");

		setstrMeasureNames.add ("FairCreditRisklessPrincipalPV");

		setstrMeasureNames.add ("FairCreditRiskyParPV");

		setstrMeasureNames.add ("FairCreditRiskyPrincipalPV");

		setstrMeasureNames.add ("FairDefaultExposure");

		setstrMeasureNames.add ("FairDefaultExposureNoRec");

		setstrMeasureNames.add ("FairDirtyCouponPV");

		setstrMeasureNames.add ("FairDirtyDV01");

		setstrMeasureNames.add ("FairDirtyIndexCouponPV");

		setstrMeasureNames.add ("FairDirtyPrice");

		setstrMeasureNames.add ("FairDirtyPV");

		setstrMeasureNames.add ("FairDiscountMargin");

		setstrMeasureNames.add ("FairDuration");

		setstrMeasureNames.add ("FairDV01");

		setstrMeasureNames.add ("FairExpectedRecovery");

		setstrMeasureNames.add ("FairFirstIndexRate");

		setstrMeasureNames.add ("FairGSpread");

		setstrMeasureNames.add ("FairISpread");

		setstrMeasureNames.add ("FairLossOnInstantaneousDefault");

		setstrMeasureNames.add ("FairMacaulayDuration");

		setstrMeasureNames.add ("FairModifiedDuration");

		setstrMeasureNames.add ("FairOAS");

		setstrMeasureNames.add ("FairOASpread");

		setstrMeasureNames.add ("FairOptionAdjustedSpread");

		setstrMeasureNames.add ("FairParPV");

		setstrMeasureNames.add ("FairParSpread");

		setstrMeasureNames.add ("FairPECS");

		setstrMeasureNames.add ("FairPrice");

		setstrMeasureNames.add ("FairPrincipalPV");

		setstrMeasureNames.add ("FairPV");

		setstrMeasureNames.add ("FairRecoveryPV");

		setstrMeasureNames.add ("FairRisklessCleanCouponPV");

		setstrMeasureNames.add ("FairRisklessCleanDV01");

		setstrMeasureNames.add ("FairRisklessCleanIndexCouponPV");

		setstrMeasureNames.add ("FairRisklessCleanPV");

		setstrMeasureNames.add ("FairRisklessDirtyCouponPV");

		setstrMeasureNames.add ("FairRisklessDirtyDV01");

		setstrMeasureNames.add ("FairRisklessDirtyIndexCouponPV");

		setstrMeasureNames.add ("FairRisklessDirtyPV");

		setstrMeasureNames.add ("FairRiskyCleanCouponPV");

		setstrMeasureNames.add ("FairRiskyCleanDV01");

		setstrMeasureNames.add ("FairRiskyCleanIndexCouponPV");

		setstrMeasureNames.add ("FairRiskyCleanPV");

		setstrMeasureNames.add ("FairRiskyDirtyCouponPV");

		setstrMeasureNames.add ("FairRiskyDirtyDV01");

		setstrMeasureNames.add ("FairRiskyDirtyIndexCouponPV");

		setstrMeasureNames.add ("FairRiskyDirtyPV");

		setstrMeasureNames.add ("FairTSYSpread");

		setstrMeasureNames.add ("FairWorkoutDate");

		setstrMeasureNames.add ("FairWorkoutFactor");

		setstrMeasureNames.add ("FairWorkoutType");

		setstrMeasureNames.add ("FairWorkoutYield");

		setstrMeasureNames.add ("FairYield");

		setstrMeasureNames.add ("FairYield01");

		setstrMeasureNames.add ("FairYieldBasis");

		setstrMeasureNames.add ("FairYieldSpread");

		setstrMeasureNames.add ("FairZeroDiscountMargin");

		setstrMeasureNames.add ("FairZSpread");

		setstrMeasureNames.add ("FirstCouponRate");

		setstrMeasureNames.add ("FirstIndexRate");

		setstrMeasureNames.add ("GSpread");

		setstrMeasureNames.add ("ISpread");

		setstrMeasureNames.add ("LossOnInstantaneousDefault");

		setstrMeasureNames.add ("MacaulayDuration");

		setstrMeasureNames.add ("MarketAccrued");

		setstrMeasureNames.add ("MarketAccrued01");

		setstrMeasureNames.add ("MarketCleanCouponPV");

		setstrMeasureNames.add ("MarketCleanDV01");

		setstrMeasureNames.add ("MarketCleanIndexCouponPV");

		setstrMeasureNames.add ("MarketCleanPrice");

		setstrMeasureNames.add ("MarketCleanPV");

		setstrMeasureNames.add ("MarketCreditRisklessParPV");

		setstrMeasureNames.add ("MarketCreditRisklessPrincipalPV");

		setstrMeasureNames.add ("MarketCreditRiskyParPV");

		setstrMeasureNames.add ("MarketCreditRiskyPrincipalPV");

		setstrMeasureNames.add ("MarketDefaultExposure");

		setstrMeasureNames.add ("MarketDefaultExposureNoRec");

		setstrMeasureNames.add ("MarketDirtyCouponPV");

		setstrMeasureNames.add ("MarketDirtyDV01");

		setstrMeasureNames.add ("MarketDirtyIndexCouponPV");

		setstrMeasureNames.add ("MarketDirtyPrice");

		setstrMeasureNames.add ("MarketDirtyPV");

		setstrMeasureNames.add ("MarketDV01");

		setstrMeasureNames.add ("MarketExpectedRecovery");

		setstrMeasureNames.add ("MarketFirstCouponRate");

		setstrMeasureNames.add ("MarketFirstIndexRate");

		setstrMeasureNames.add ("MarketInputType=CleanPrice");

		setstrMeasureNames.add ("MarketInputType=CreditBasis");

		setstrMeasureNames.add ("MarketInputType=DirtyPrice");

		setstrMeasureNames.add ("MarketInputType=GSpread");

		setstrMeasureNames.add ("MarketInputType=ISpread");

		setstrMeasureNames.add ("MarketInputType=PECS");

		setstrMeasureNames.add ("MarketInputType=QuotedMargin");

		setstrMeasureNames.add ("MarketInputType=TSYSpread");

		setstrMeasureNames.add ("MarketInputType=Yield");

		setstrMeasureNames.add ("MarketInputType=ZSpread");

		setstrMeasureNames.add ("MarketLossOnInstantaneousDefault");

		setstrMeasureNames.add ("MarketParPV");

		setstrMeasureNames.add ("MarketPrincipalPV");

		setstrMeasureNames.add ("MarketPV");

		setstrMeasureNames.add ("MarketRecoveryPV");

		setstrMeasureNames.add ("MarketRisklessDirtyCouponPV");

		setstrMeasureNames.add ("MarketRisklessDirtyDV01");

		setstrMeasureNames.add ("MarketRisklessDirtyIndexCouponPV");

		setstrMeasureNames.add ("MarketRisklessDirtyPV");

		setstrMeasureNames.add ("MarketRiskyDirtyCouponPV");

		setstrMeasureNames.add ("MarketRiskyDirtyDV01");

		setstrMeasureNames.add ("MarketRiskyDirtyIndexCouponPV");

		setstrMeasureNames.add ("MarketRiskyDirtyPV");

		setstrMeasureNames.add ("ModifiedDuration");

		setstrMeasureNames.add ("OAS");

		setstrMeasureNames.add ("OASpread");

		setstrMeasureNames.add ("OptionAdjustedSpread");

		setstrMeasureNames.add ("ParEquivalentCDSSpread");

		setstrMeasureNames.add ("ParPV");

		setstrMeasureNames.add ("ParSpread");

		setstrMeasureNames.add ("PECS");

		setstrMeasureNames.add ("Price");

		setstrMeasureNames.add ("PrincipalPV");

		setstrMeasureNames.add ("PV");

		setstrMeasureNames.add ("RecoveryPV");

		setstrMeasureNames.add ("RisklessCleanCouponPV");

		setstrMeasureNames.add ("RisklessCleanDV01");

		setstrMeasureNames.add ("RisklessCleanIndexCouponPV");

		setstrMeasureNames.add ("RisklessCleanPV");

		setstrMeasureNames.add ("RisklessDirtyCouponPV");

		setstrMeasureNames.add ("RisklessDirtyDV01");

		setstrMeasureNames.add ("RisklessDirtyIndexCouponPV");

		setstrMeasureNames.add ("RisklessDirtyPV");

		setstrMeasureNames.add ("RiskyCleanCouponPV");

		setstrMeasureNames.add ("RiskyCleanDV01");

		setstrMeasureNames.add ("RiskyCleanIndexCouponPV");

		setstrMeasureNames.add ("RiskyCleanPV");

		setstrMeasureNames.add ("RiskyDirtyCouponPV");

		setstrMeasureNames.add ("RiskyDirtyDV01");

		setstrMeasureNames.add ("RiskyDirtyIndexCouponPV");

		setstrMeasureNames.add ("RiskyDirtyPV");

		setstrMeasureNames.add ("TSYSpread");

		setstrMeasureNames.add ("WorkoutDate");

		setstrMeasureNames.add ("WorkoutFactor");

		setstrMeasureNames.add ("WorkoutType");

		setstrMeasureNames.add ("WorkoutYield");

		setstrMeasureNames.add ("Yield");

		setstrMeasureNames.add ("Yield01");

		setstrMeasureNames.add ("YieldBasis");

		setstrMeasureNames.add ("YieldSpread");

		setstrMeasureNames.add ("ZeroDiscountMargin");

		setstrMeasureNames.add ("ZSpread");

		return setstrMeasureNames;
	}

	@Override public double pv (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParamsIn,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
		throws java.lang.Exception
	{
		if (null == valParams || null == csqc)
			throw new java.lang.Exception ("BondComponent::pv => Invalid Inputs!");

		int iValueDate = valParams.valueDate();

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqc.fundingState (fundingLabel());

		if (null == dcFunding) throw new java.lang.Exception ("BondComponent::pv => Invalid Inputs!");

		int iLossPayLag = null == _creditSetting ? 0 : _creditSetting.lossPayLag();

		double dblProductRecovery = null == _creditSetting ? java.lang.Double.NaN :
			_creditSetting.recovery();

		boolean bUseCurveRecovery = null == _creditSetting ? false : _creditSetting.useCurveRecovery();

		boolean bAccrualOnDefault = null == _creditSetting ? false : _creditSetting.accrualOnDefault();

		org.drip.state.credit.CreditCurve cc = csqc.creditState (creditLabel());

		double dblRecoveryPV = 0.;
		double dblPrincipalPV = 0.;
		double dblDirtyCouponPV = 0.;
		boolean bApplyFlatForwardRate = false;
		double dblFlatForwardRate = java.lang.Double.NaN;

		if (null != vcp)
			bApplyFlatForwardRate = vcp.applyFlatForwardRate();
		else {
			org.drip.param.valuation.ValuationCustomizationParams vcpQuote = null == _quoteConvention ? null
				: _quoteConvention.valuationCustomizationParams();

			if (null != vcpQuote) bApplyFlatForwardRate = vcpQuote.applyFlatForwardRate();
		}

		int iMaturityDate = maturityDate().julian();

		org.drip.param.pricer.CreditPricerParams pricerParams = null != pricerParamsIn ? pricerParamsIn : new
			org.drip.param.pricer.CreditPricerParams (7, null, false,
				org.drip.param.pricer.CreditPricerParams.PERIOD_DISCRETIZATION_FULL_COUPON);

		for (org.drip.analytics.cashflow.CompositePeriod period : couponPeriods()) {
			int iPeriodPayDate = period.payDate();

			if (iPeriodPayDate < iValueDate) continue;

			int iPeriodEndDate = period.endDate();

			int iPeriodStartDate = period.startDate();

			org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = couponMetrics (iValueDate,
				valParams, csqc);

			if (null == cpcm) throw new java.lang.Exception ("BondComponent::pv => Invalid Inputs!");

			if (!org.drip.quant.common.NumberUtil.IsValid (dblFlatForwardRate))
				dblFlatForwardRate = cpcm.rate();

			double dblPeriodCoupon = bApplyFlatForwardRate ? dblFlatForwardRate : cpcm.rate();

			double dblPeriodAnnuity = dcFunding.df (iPeriodPayDate) * cpcm.cumulative();

			double dblPeriodDirtyDV01 = 0.0001 * period.accrualDCF (iPeriodEndDate) * dblPeriodAnnuity *
				notional (iPeriodStartDate, iPeriodEndDate);

			double dblPeriodPrincipalPV = (notional (iPeriodStartDate) - notional (iPeriodEndDate)) *
				dblPeriodAnnuity;

			if (null != cc && null != pricerParams) {
				double dblSurvProb = cc.survival (pricerParams.survivalToPayDate() ? iPeriodPayDate :
					iPeriodEndDate);

				dblPeriodDirtyDV01 *= dblSurvProb;
				dblPeriodPrincipalPV *= dblSurvProb;

				for (org.drip.analytics.cashflow.LossQuadratureMetrics lqm : period.lossMetrics (this,
					valParams, pricerParams, iMaturityDate, csqc)) {
					if (null == lqm) continue;

					int iSubPeriodEndDate = lqm.endDate();

					int iSubPeriodStartDate = lqm.startDate();

					double dblSubPeriodDF = dcFunding.effectiveDF (iSubPeriodStartDate + iLossPayLag,
						iSubPeriodEndDate + iLossPayLag);

					double dblSubPeriodNotional = notional (iSubPeriodStartDate, iSubPeriodEndDate);

					double dblSubPeriodSurvival = cc.survival (iSubPeriodStartDate) - cc.survival
						(iSubPeriodEndDate);

					if (bAccrualOnDefault)
						dblPeriodDirtyDV01 += 0.0001 * lqm.accrualDCF() * dblSubPeriodSurvival *
							dblSubPeriodDF * dblSubPeriodNotional;

					dblRecoveryPV += (bUseCurveRecovery ? cc.effectiveRecovery (iSubPeriodStartDate,
						iSubPeriodEndDate) : dblProductRecovery) * dblSubPeriodSurvival * dblSubPeriodNotional
							* dblSubPeriodDF;
				}
			}

			dblPrincipalPV += dblPeriodPrincipalPV;
			dblDirtyCouponPV += 10000. * dblPeriodCoupon * dblPeriodDirtyDV01;
		}

		double dblParPV = dcFunding.df (iMaturityDate) * notional (iMaturityDate);

		if (null != cc && null != pricerParams) dblParPV *= cc.survival (iMaturityDate);

		return (dblDirtyCouponPV + dblPrincipalPV + dblParPV + dblRecoveryPV) / dcFunding.df (null !=
			_quoteConvention ? _quoteConvention.settleDate (valParams) : valParams.cashPayDate());
	}

	@Override public org.drip.quant.calculus.WengertJacobian jackDDirtyPVDManifestMeasure (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
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
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
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

	@Override public org.drip.state.estimator.PredictorResponseWeightConstraint volatilityPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	@Override public org.drip.quant.calculus.WengertJacobian manifestMeasureDFMicroJack (
		final java.lang.String strManifestMeasure,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		return null;
	}

	/**
	 * The BondCalibrator implements a calibrator that calibrates the yield, the credit basis, or the Z
	 * 		Spread for the bond given the price input. Calibration happens via either Newton-Raphson method,
	 * 		or via bracketing/root searching.
	 * 
	 * @author Lakshmi Krishnamurthy
	 *
	 */

	public class BondCalibrator {
		private BondComponent _bond = null;
		private boolean _bApplyCouponExtension = false;

		/**
		 * Constructor: Construct the calibrator from the parent bond.
		 * 
		 * @param bond Parent
		 * @param bApplyCouponExtension TRUE - Apply the Coupon Extension
		 * 
		 * @throws java.lang.Exception Thrown if the inputs are invalid
		 */

		public BondCalibrator (
			final BondComponent bond,
			final boolean bApplyCouponExtension)
			throws java.lang.Exception
		{
			if (null == (_bond = bond))
				throw new java.lang.Exception ("BondComponent::BondCalibrator ctr => Invalid Inputs");

			_bApplyCouponExtension = bApplyCouponExtension;
		}

		/**
		 * Calibrate the bond yield from the market price using the root bracketing technique.
		 * 
		 * @param valParams Valuation Parameters
		 * @param csqs Bond Market Parameters
		 * @param vcp Valuation Customization Parameters
		 * @param iWorkoutDate JulianDate Work-out
		 * @param dblWorkoutFactor Work-out factor
		 * @param dblPrice Price to be calibrated to
		 * 
		 * @return The calibrated Yield
		 * 
		 * @throws java.lang.Exception Thrown if the yield cannot be calibrated
		 */

		public double calibrateYieldFromPrice (
			final org.drip.param.valuation.ValuationParams valParams,
			final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
			final org.drip.param.valuation.ValuationCustomizationParams vcp,
			final int iWorkoutDate,
			final double dblWorkoutFactor,
			final double dblPrice)
			throws java.lang.Exception
		{
			if (!org.drip.quant.common.NumberUtil.IsValid (dblPrice))
				throw new java.lang.Exception
					("BondComponent::BondCalibrator::calibrateYieldFromPrice => Invalid Inputs!");

			org.drip.function.definition.R1ToR1 ofYieldToPrice = new org.drip.function.definition.R1ToR1
				(null) {
				@Override public double evaluate (
					final double dblYield)
					throws java.lang.Exception
				{
					return _bond.priceFromYield (valParams, csqs, vcp, iWorkoutDate, dblWorkoutFactor,
						dblYield, _bApplyCouponExtension) - dblPrice;
				}
			};

			org.drip.function.r1tor1solver.FixedPointFinderOutput rfop = new
				org.drip.function.r1tor1solver.FixedPointFinderNewton (0., ofYieldToPrice, true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				rfop = new org.drip.function.r1tor1solver.FixedPointFinderZheng (0., ofYieldToPrice,
					true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				rfop = new org.drip.function.r1tor1solver.FixedPointFinderBracketing (0., ofYieldToPrice,
					null, org.drip.function.r1tor1solver.VariateIteratorPrimitive.FALSE_POSITION,
						true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				throw new java.lang.Exception
					("BondComponent::BondCalibrator::calibrateYieldFromPrice => Cannot get root!");

			return rfop.getRoot();
		}

		/**
		 * Calibrate the bond Z Spread from the market price using the root bracketing technique.
		 * 
		 * @param valParams Valuation Parameters
		 * @param csqs Bond Market Parameters
		 * @param vcp Valuation Customization Parameters
		 * @param iZeroCurveBaseDC The Discount Curve to derive the zero curve off of
		 * @param iWorkoutDate JulianDate Work-out
		 * @param dblWorkoutFactor Work-out factor
		 * @param dblPrice Price to be calibrated to
		 * 
		 * @return The calibrated Z Spread
		 * 
		 * @throws java.lang.Exception Thrown if the Z Spread cannot be calibrated
		 */

		public double calibrateZSpreadFromPrice (
			final org.drip.param.valuation.ValuationParams valParams,
			final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
			final org.drip.param.valuation.ValuationCustomizationParams vcp,
			final int iZeroCurveBaseDC,
			final int iWorkoutDate,
			final double dblWorkoutFactor,
			final double dblPrice)
			throws java.lang.Exception
		{
			if (!org.drip.quant.common.NumberUtil.IsValid (dblPrice))
				throw new java.lang.Exception
					("BondComponent::BondCalibrator::calibrateZSpreadFromPrice => Invalid Inputs!");

			if (null != _floaterSetting)
				throw new java.lang.Exception
					("BondComponent::BondCalibrator::calibrateZSpreadFromPrice => Z Spread Calculation turned off for floaters!");

			org.drip.function.definition.R1ToR1 ofZSpreadToPrice = new org.drip.function.definition.R1ToR1
				(null) {
				@Override public double evaluate (
					final double dblZSpread)
					throws java.lang.Exception
				{
					return _bond.priceFromZeroCurve (valParams, csqs, vcp, iZeroCurveBaseDC, iWorkoutDate,
						dblWorkoutFactor, dblZSpread) - dblPrice;
				}
			};

			org.drip.function.r1tor1solver.FixedPointFinderOutput rfop = new
				org.drip.function.r1tor1solver.FixedPointFinderNewton (0., ofZSpreadToPrice,
					true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				rfop = new org.drip.function.r1tor1solver.FixedPointFinderBrent (0., ofZSpreadToPrice,
					true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				rfop = new org.drip.function.r1tor1solver.FixedPointFinderBracketing (0., ofZSpreadToPrice,
					new org.drip.function.r1tor1solver.ExecutionControl (ofZSpreadToPrice, new
						org.drip.function.r1tor1solver.ExecutionControlParams (200, false, 1.e-02, 1.e-02,
							1.e-01, 1.e-01)),
								org.drip.function.r1tor1solver.VariateIteratorPrimitive.FALSE_POSITION,
									true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				throw new java.lang.Exception
					("BondComponent::BondCalibrator::calibrateZSpreadFromPrice => Cannot get root!");

			return rfop.getRoot();
		}

		/**
		 * Calibrate the Bond OAS from the Market Price using the Root Bracketing Technique.
		 * 
		 * @param valParams Valuation Parameters
		 * @param csqs Bond Market Parameters
		 * @param vcp Valuation Customization Parameters
		 * @param iZeroCurveBaseDC The Discount Curve to derive the zero curve off of
		 * @param iWorkoutDate JulianDate Work-out
		 * @param dblWorkoutFactor Work-out factor
		 * @param dblPrice Price to be calibrated to
		 * 
		 * @return The Calibrated OAS
		 * 
		 * @throws java.lang.Exception Thrown if the OAS cannot be calibrated
		 */

		public double calibrateOASFromPrice (
			final org.drip.param.valuation.ValuationParams valParams,
			final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
			final org.drip.param.valuation.ValuationCustomizationParams vcp,
			final int iZeroCurveBaseDC,
			final int iWorkoutDate,
			final double dblWorkoutFactor,
			final double dblPrice)
			throws java.lang.Exception
		{
			if (!org.drip.quant.common.NumberUtil.IsValid (dblPrice))
				throw new java.lang.Exception
					("BondComponent::BondCalibrator::calibrateOASFromPrice => Invalid Inputs!");

			org.drip.function.definition.R1ToR1 r1ToR1OASToPrice = new org.drip.function.definition.R1ToR1
				(null) {
				@Override public double evaluate (
					final double dblZSpread)
					throws java.lang.Exception
				{
					return _bond.priceFromZeroCurve (valParams, csqs, vcp, iZeroCurveBaseDC, iWorkoutDate,
						dblWorkoutFactor, dblZSpread) - dblPrice;
				}
			};

			org.drip.function.r1tor1solver.FixedPointFinderOutput rfop = new
				org.drip.function.r1tor1solver.FixedPointFinderNewton (0., r1ToR1OASToPrice,
					true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				rfop = new org.drip.function.r1tor1solver.FixedPointFinderBrent (0., r1ToR1OASToPrice,
					true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				rfop = new org.drip.function.r1tor1solver.FixedPointFinderBracketing (0., r1ToR1OASToPrice,
					null, org.drip.function.r1tor1solver.VariateIteratorPrimitive.FALSE_POSITION,
						true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				throw new java.lang.Exception
					("BondComponent::BondCalibrator::calibrateOASFromPrice => Cannot get root!");

			return rfop.getRoot();
		}

		/**
		 * Calibrate the bond Z Spread from the market price. Calibration is done by bumping the discount
		 * 	curve.
		 * 
		 * @param valParams Valuation Parameters
		 * @param csqs Bond Market Parameters
		 * @param iWorkoutDate JulianDate Work-out
		 * @param dblWorkoutFactor Work-out factor
		 * @param dblPrice Price to be calibrated to
		 * 
		 * @return The calibrated Z Spread
		 * 
		 * @throws java.lang.Exception Thrown if the yield cannot be calibrated
		 */

		public double calibDiscCurveSpreadFromPrice (
			final org.drip.param.valuation.ValuationParams valParams,
			final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
			final int iWorkoutDate,
			final double dblWorkoutFactor,
			final double dblPrice)
			throws java.lang.Exception
		{
			if (!org.drip.quant.common.NumberUtil.IsValid (dblPrice))
				throw new java.lang.Exception
					("BondComponent::BondCalibrator::calibrateDiscCurveSpreadFromPrice => Invalid Inputs!");

			org.drip.function.definition.R1ToR1 ofDiscCurveSpreadToPrice = new
				org.drip.function.definition.R1ToR1 (null) {
				@Override public double evaluate (
					final double dblZSpread)
					throws java.lang.Exception
				{
					return _bond.priceFromFundingCurve (valParams, csqs, iWorkoutDate, dblWorkoutFactor,
						dblZSpread) - dblPrice;
				}
			};

			org.drip.function.r1tor1solver.FixedPointFinderOutput rfop = new
				org.drip.function.r1tor1solver.FixedPointFinderNewton (0., ofDiscCurveSpreadToPrice,
					true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				rfop = new org.drip.function.r1tor1solver.FixedPointFinderZheng (0.,
					ofDiscCurveSpreadToPrice, true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				rfop = new org.drip.function.r1tor1solver.FixedPointFinderBracketing (0.,
					ofDiscCurveSpreadToPrice, null,
						org.drip.function.r1tor1solver.VariateIteratorPrimitive.FALSE_POSITION,
							true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				throw new java.lang.Exception
					("BondComponent::BondCalibrator::calibDiscCurveSpreadFromPrice => Cannot get root!");

			return rfop.getRoot();
		}

		/**
		 * Calibrate the bond Z Spread from the market price. Calibration is done by bumping the Zero Curve.
		 * 
		 * @param valParams Valuation Parameters
		 * @param csqs Bond Market Parameters
		 * @param iWorkoutDate JulianDate Work-out
		 * @param dblWorkoutFactor Work-out factor
		 * @param dblPrice Price to be calibrated to
		 * 
		 * @return The calibrated Z Spread
		 * 
		 * @throws java.lang.Exception Thrown if the yield cannot be calibrated
		 */

		public double calibZeroCurveSpreadFromPrice (
			final org.drip.param.valuation.ValuationParams valParams,
			final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
			final int iWorkoutDate,
			final double dblWorkoutFactor,
			final double dblPrice)
			throws java.lang.Exception
		{
			if (!org.drip.quant.common.NumberUtil.IsValid (dblPrice))
				throw new java.lang.Exception
					("BondComponent::BondCalibrator::calibrateZeroCurveSpreadFromPrice => Invalid Inputs!");

			if (null != _floaterSetting)
				throw new java.lang.Exception
					("BondComponent::BondCalibrator::calibZeroCurveSpreadFromPrice => Z Spread Calculation turned off for floaters!");

			org.drip.function.definition.R1ToR1 ofZSpreadToPrice = new org.drip.function.definition.R1ToR1
				(null) {
				@Override public double evaluate (
					final double dblZSpread)
					throws java.lang.Exception
				{
					return _bond.priceFromFundingCurve (valParams, csqs, iWorkoutDate, dblWorkoutFactor,
						dblZSpread) - dblPrice;
				}
			};

			org.drip.function.r1tor1solver.FixedPointFinderOutput rfop = new
				org.drip.function.r1tor1solver.FixedPointFinderNewton (0., ofZSpreadToPrice,
					true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				rfop = new org.drip.function.r1tor1solver.FixedPointFinderZheng (0., ofZSpreadToPrice,
					true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				rfop = new org.drip.function.r1tor1solver.FixedPointFinderBracketing (0., ofZSpreadToPrice,
					null, org.drip.function.r1tor1solver.VariateIteratorPrimitive.FALSE_POSITION,
						true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				throw new java.lang.Exception
					("BondComponent.calibZeroCurveSpreadFromPrice => Cannot get root!");

			return rfop.getRoot();
		}

		/**
		 * Calibrate the bond Credit Basis from the market price
		 * 
		 * @param valParams Valuation Parameters
		 * @param csqs Bond Market Parameters
		 * @param iWorkoutDate JulianDate Work-out
		 * @param dblWorkoutFactor Work-out factor
		 * @param dblPrice Price to be calibrated to
		 * @param bFlat TRUE - Calibrate to Flat Curve
		 * 
		 * @return The calibrated Credit Basis
		 * 
		 * @throws java.lang.Exception Thrown if the Credit Basis cannot be calibrated
		 */

		public double calibrateCreditBasisFromPrice (
			final org.drip.param.valuation.ValuationParams valParams,
			final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
			final int iWorkoutDate,
			final double dblWorkoutFactor,
			final double dblPrice,
			final boolean bFlat)
			throws java.lang.Exception
		{
			if (!org.drip.quant.common.NumberUtil.IsValid (dblPrice))
				throw new java.lang.Exception
					("BondComponent::BondCalibrator::calibrateCreditBasisFromPrice => Invalid Inputs!");

			org.drip.function.definition.R1ToR1 ofCreditBasisToPrice = new
				org.drip.function.definition.R1ToR1 (null) {
				@Override public double evaluate (
					final double dblCreditBasis)
					throws java.lang.Exception
				{
					return _bond.priceFromCreditCurve (valParams, csqs, iWorkoutDate, dblWorkoutFactor,
						dblCreditBasis, bFlat) - dblPrice;
				}
			};

			org.drip.function.r1tor1solver.FixedPointFinderOutput rfop = new
				org.drip.function.r1tor1solver.FixedPointFinderNewton (0., ofCreditBasisToPrice,
					true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				rfop = new org.drip.function.r1tor1solver.FixedPointFinderZheng (0., ofCreditBasisToPrice,
					true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				rfop = new org.drip.function.r1tor1solver.FixedPointFinderBracketing (0., ofCreditBasisToPrice,
					null, org.drip.function.r1tor1solver.VariateIteratorPrimitive.FALSE_POSITION,
						true).findRoot();

			if (null == rfop || !rfop.containsRoot())
				throw new java.lang.Exception
					("BondComponent.calibrateCreditBasisFromPrice => Cannot get root!");

			return rfop.getRoot();
		}
	}

	/**
	 * Generate the EOS Callable Option Adjusted Metrics
	 * 
	 * @param valParams The Valuation Parameters
	 * @param csqc The Market Parameters
	 * @param vcp The Valuation Customization Parameters
	 * @param dblCleanPrice Clean Price
	 * @param gbs The Govvie Builder Settings
	 * @param deGovvieForward The Govvie Forward Diffusion Evolver
	 * @param iNumPath The Number of Paths
	 * 
	 * @return The Bond EOS Metrics
	 */

	public org.drip.analytics.output.BondEOSMetrics callMetrics (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCleanPrice,
		final org.drip.state.sequence.GovvieBuilderSettings gbs,
		final org.drip.measure.process.DiffusionEvolver deGovvieForward,
		final int iNumPath)
	{
		if (null == valParams || null == csqc || null == gbs) return null;

		org.drip.product.params.EmbeddedOptionSchedule eosCall = callSchedule();

		if (null == eosCall) return null;

		int iNumDimension = gbs.dimension();

		int iValueDate = valParams.valueDate();

		int[] aiExerciseDate = eosCall.exerciseDates (iValueDate);

		double[] adblExercisePrice = eosCall.exerciseFactors (iValueDate);

		if (null == aiExerciseDate || null == adblExercisePrice) return null;

		double dblOAS = java.lang.Double.NaN;
		int iNumVertex = aiExerciseDate.length;
		org.drip.state.sequence.PathVertexGovvie pvg = null;
		double[] adblOptimalExercisePV = new double[iNumPath];
		int[] aiOptimalExerciseVertexIndex = new int[iNumPath];
		double[] adblOptimalExerciseOAS = new double[iNumPath];
		double[] adblOptimalExercisePrice = new double[iNumPath];
		double[] adblOptimalExerciseOASGap = new double[iNumPath];
		double[] adblOptimalExerciseDuration = new double[iNumPath];
		double[] adblOptimalExerciseConvexity = new double[iNumPath];
		double[][] aadblForwardPrice = new double[iNumPath][iNumVertex];
		boolean[][] aabExerciseIndicator = new boolean[iNumPath][iNumVertex];
		double[][] aadblCorrelation = new double[iNumDimension][iNumDimension];
		org.drip.analytics.date.JulianDate[] adtOptimalExerciseDate = new
			org.drip.analytics.date.JulianDate[iNumPath];
		org.drip.param.valuation.ValuationParams[] aValParamsEvent = new
			org.drip.param.valuation.ValuationParams[iNumVertex];

		for (int i = 0; i < iNumDimension; ++i) {
			for (int j = 0; j < iNumDimension; ++j)
				aadblCorrelation[i][j] = i == j ? 1. : 0.;
		}

		try {
			if (null == (pvg = org.drip.state.sequence.PathVertexGovvie.Standard (gbs, new
				org.drip.measure.discrete.CorrelatedPathVertexDimension (new
					org.drip.measure.crng.RandomNumberGenerator(), aadblCorrelation, iNumVertex, iNumPath,
						false, null), deGovvieForward)))
				return null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.state.govvie.GovvieCurve[][] aaGCPathEvent = pvg.pathVertex (aiExerciseDate);

		if (null == aaGCPathEvent) return null;

		try {
			dblOAS = oasFromPrice (valParams, csqc, vcp, dblCleanPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int iVertex = 0; iVertex < iNumVertex; ++iVertex) {
			if (null == (aValParamsEvent[iVertex] = org.drip.param.valuation.ValuationParams.Spot
				(aiExerciseDate[iVertex])))
				return null;
		}

		org.drip.state.discount.MergedDiscountForwardCurve mdfcFunding = csqc.fundingState (fundingLabel());

		for (int iPath = 0; iPath < iNumPath; ++iPath) {
			for (int iVertex = 0; iVertex < iNumVertex; ++iVertex) {
				try {
					aadblForwardPrice[iPath][iVertex] = priceFromOAS (aValParamsEvent[iVertex],
						org.drip.param.creator.MarketParamsBuilder.Create (mdfcFunding,
							aaGCPathEvent[iPath][iVertex], null, null, null, null, null), null, dblOAS);
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		for (int iPath = 0; iPath < iNumPath; ++iPath) {
			adblOptimalExercisePV[iPath] = 0.;
			adblOptimalExercisePrice[iPath] = 1.;
			aiOptimalExerciseVertexIndex[iPath] = iNumVertex;

			adtOptimalExerciseDate[iPath] = maturityDate();

			for (int iVertex = 0; iVertex < iNumVertex; ++iVertex) {
				double dblExercisePV = java.lang.Double.NaN;
				aabExerciseIndicator[iPath][iVertex] = false;

				try {
					dblExercisePV = (aadblForwardPrice[iPath][iVertex] - adblExercisePrice[iVertex]) *
						mdfcFunding.df (aiExerciseDate[iVertex]);
				} catch (java.lang.Exception e) {
					e.printStackTrace();
				}

				if (dblExercisePV > adblOptimalExercisePV[iPath]) {
					adtOptimalExerciseDate[iPath] = new org.drip.analytics.date.JulianDate
						(aiExerciseDate[iVertex]);

					adblOptimalExercisePrice[iPath] = adblExercisePrice[iVertex];
					aiOptimalExerciseVertexIndex[iPath] = iVertex;
					adblOptimalExercisePV[iPath] = dblExercisePV;
					aabExerciseIndicator[iPath][iVertex] = true;
				}
			}
		}

		for (int iPath = 0; iPath < iNumPath; ++iPath) {
			int iOptimalExerciseDate = adtOptimalExerciseDate[iPath].julian();

			try {
				adblOptimalExerciseOAS[iPath] = oasFromPrice (valParams, csqc, null, iOptimalExerciseDate,
					adblOptimalExercisePrice[iPath], dblCleanPrice);

				adblOptimalExerciseDuration[iPath] = modifiedDurationFromPrice (valParams, csqc, null,
					iOptimalExerciseDate, adblOptimalExercisePrice[iPath], dblCleanPrice);

				adblOptimalExerciseConvexity[iPath] = convexityFromPrice (valParams, csqc, null,
					iOptimalExerciseDate, adblOptimalExercisePrice[iPath], dblCleanPrice);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			adblOptimalExerciseOASGap[iPath] = adblOptimalExerciseOAS[iPath] - dblOAS;
		}

		try {
			return new org.drip.analytics.output.BondEOSMetrics (dblOAS, adblOptimalExercisePrice,
				adblOptimalExercisePV, adblOptimalExerciseOAS, adblOptimalExerciseOASGap,
					adblOptimalExerciseDuration, adblOptimalExerciseConvexity, aadblForwardPrice,
						aabExerciseIndicator);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the EOS Putable Option Adjusted Metrics
	 * 
	 * @param valParams The Valuation Parameters
	 * @param csqc The Market Parameters
	 * @param vcp The Valuation Customization Parameters
	 * @param dblCleanPrice Clean Price
	 * @param gbs The Govvie Builder Settings
	 * @param deGovvieForward The Govvie Forward Diffusion Evolver
	 * @param iNumPath The Number of Paths
	 * 
	 * @return The Bond EOS Metrics
	 */

	public org.drip.analytics.output.BondEOSMetrics putMetrics (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final double dblCleanPrice,
		final org.drip.state.sequence.GovvieBuilderSettings gbs,
		final org.drip.measure.process.DiffusionEvolver deGovvieForward,
		final int iNumPath)
	{
		if (null == valParams || null == csqc || null == gbs) return null;

		org.drip.product.params.EmbeddedOptionSchedule eosPut = putSchedule();

		if (null == eosPut) return null;

		int iNumDimension = gbs.dimension();

		int iValueDate = valParams.valueDate();

		int[] aiExerciseDate = eosPut.exerciseDates (iValueDate);

		double[] adblExercisePrice = eosPut.exerciseFactors (iValueDate);

		if (null == aiExerciseDate || null == adblExercisePrice) return null;

		double dblOAS = java.lang.Double.NaN;
		int iNumVertex = aiExerciseDate.length;
		org.drip.state.sequence.PathVertexGovvie pvg = null;
		double[] adblOptimalExercisePV = new double[iNumPath];
		int[] aiOptimalExerciseVertexIndex = new int[iNumPath];
		double[] adblOptimalExerciseOAS = new double[iNumPath];
		double[] adblOptimalExercisePrice = new double[iNumPath];
		double[] adblOptimalExerciseOASGap = new double[iNumPath];
		double[] adblOptimalExerciseDuration = new double[iNumPath];
		double[] adblOptimalExerciseConvexity = new double[iNumPath];
		double[][] aadblForwardPrice = new double[iNumPath][iNumVertex];
		boolean[][] aabExerciseIndicator = new boolean[iNumPath][iNumVertex];
		double[][] aadblCorrelation = new double[iNumDimension][iNumDimension];
		org.drip.analytics.date.JulianDate[] adtOptimalExerciseDate = new
			org.drip.analytics.date.JulianDate[iNumPath];
		org.drip.param.valuation.ValuationParams[] aValParamsEvent = new
			org.drip.param.valuation.ValuationParams[iNumVertex];

		for (int i = 0; i < iNumDimension; ++i) {
			for (int j = 0; j < iNumDimension; ++j)
				aadblCorrelation[i][j] = i == j ? 1. : 0.;
		}

		try {
			if (null == (pvg = org.drip.state.sequence.PathVertexGovvie.Standard (gbs, new
				org.drip.measure.discrete.CorrelatedPathVertexDimension (new
					org.drip.measure.crng.RandomNumberGenerator(), aadblCorrelation, iNumVertex, iNumPath,
						false, null), deGovvieForward)))
				return null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.state.govvie.GovvieCurve[][] aaGCPathEvent = pvg.pathVertex (aiExerciseDate);

		if (null == aaGCPathEvent) return null;

		try {
			dblOAS = oasFromPrice (valParams, csqc, vcp, dblCleanPrice);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int iVertex = 0; iVertex < iNumVertex; ++iVertex) {
			if (null == (aValParamsEvent[iVertex] = org.drip.param.valuation.ValuationParams.Spot
				(aiExerciseDate[iVertex])))
				return null;
		}

		org.drip.state.discount.MergedDiscountForwardCurve mdfcFunding = csqc.fundingState (fundingLabel());

		for (int iPath = 0; iPath < iNumPath; ++iPath) {
			for (int iVertex = 0; iVertex < iNumVertex; ++iVertex) {
				try {
					aadblForwardPrice[iPath][iVertex] = priceFromOAS (aValParamsEvent[iVertex],
						org.drip.param.creator.MarketParamsBuilder.Create (mdfcFunding,
							aaGCPathEvent[iPath][iVertex], null, null, null, null, null), null, dblOAS);
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}
		}

		for (int iPath = 0; iPath < iNumPath; ++iPath) {
			adblOptimalExercisePV[iPath] = 0.;
			adblOptimalExercisePrice[iPath] = 1.;
			aiOptimalExerciseVertexIndex[iPath] = iNumVertex;

			adtOptimalExerciseDate[iPath] = maturityDate();

			for (int iVertex = 0; iVertex < iNumVertex; ++iVertex) {
				double dblExercisePV = java.lang.Double.NaN;
				aabExerciseIndicator[iPath][iVertex] = false;

				try {
					dblExercisePV = (adblExercisePrice[iVertex] - aadblForwardPrice[iPath][iVertex]) *
						mdfcFunding.df (aiExerciseDate[iVertex]);
				} catch (java.lang.Exception e) {
					e.printStackTrace();
				}

				if (dblExercisePV > adblOptimalExercisePV[iPath]) {
					adtOptimalExerciseDate[iPath] = new org.drip.analytics.date.JulianDate
						(aiExerciseDate[iVertex]);

					adblOptimalExercisePrice[iPath] = adblExercisePrice[iVertex];
					aiOptimalExerciseVertexIndex[iPath] = iVertex;
					adblOptimalExercisePV[iPath] = dblExercisePV;
					aabExerciseIndicator[iPath][iVertex] = true;
				}
			}
		}

		for (int iPath = 0; iPath < iNumPath; ++iPath) {
			int iOptimalExerciseDate = adtOptimalExerciseDate[iPath].julian();

			try {
				adblOptimalExerciseOAS[iPath] = oasFromPrice (valParams, csqc, null, iOptimalExerciseDate,
					adblOptimalExercisePrice[iPath], dblCleanPrice);

				adblOptimalExerciseDuration[iPath] = modifiedDurationFromPrice (valParams, csqc, null,
					iOptimalExerciseDate, adblOptimalExercisePrice[iPath], dblCleanPrice);

				adblOptimalExerciseConvexity[iPath] = convexityFromPrice (valParams, csqc, null,
					iOptimalExerciseDate, adblOptimalExercisePrice[iPath], dblCleanPrice);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			adblOptimalExerciseOASGap[iPath] = adblOptimalExerciseOAS[iPath] - dblOAS;
		}

		try {
			return new org.drip.analytics.output.BondEOSMetrics (dblOAS, adblOptimalExercisePrice,
				adblOptimalExercisePV, adblOptimalExerciseOAS, adblOptimalExerciseOASGap,
					adblOptimalExerciseDuration, adblOptimalExerciseConvexity, aadblForwardPrice,
						aabExerciseIndicator);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public void showPeriods()
		throws java.lang.Exception
	{
		for (org.drip.analytics.cashflow.CompositePeriod period : couponPeriods())
			System.out.println ("\t" + org.drip.analytics.date.DateUtil.YYYYMMDD (period.startDate()) +
				"->" + org.drip.analytics.date.DateUtil.YYYYMMDD (period.endDate()) + "    " +
					period.accrualDCF (period.endDate()));
	}
}
