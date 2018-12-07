
package org.drip.product.rates;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>Stream</i> implements the fixed and the floating streams.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product">Product</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/rates">Rates</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Stream {
	private java.util.List<org.drip.analytics.cashflow.CompositePeriod> _lsPeriod = null;

	private double fxAdjustedNotional (
		final int iDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
		throws java.lang.Exception
	{
		org.drip.analytics.cashflow.CompositePeriod cpLeft = _lsPeriod.get (0);

		int iLeftStartDate = cpLeft.startDate();

		if (iDate <= iLeftStartDate)
			return cpLeft.notional (iLeftStartDate) * cpLeft.couponFactor (iLeftStartDate) * cpLeft.fx
				(csqs);

		for (org.drip.analytics.cashflow.CompositePeriod cp : _lsPeriod) {
			if (cp.contains (iDate))
				return cp.notional (iDate) * cp.couponFactor (iDate) * cp.fx (csqs);
		}

		org.drip.analytics.cashflow.CompositePeriod cpRight = _lsPeriod.get (_lsPeriod.size() - 1);

		int iRightEndDate = cpRight.endDate();

		return cpRight.notional (iRightEndDate) * cpRight.couponFactor (iRightEndDate) * cpRight.fx (csqs);
	}

	/**
	 * Stream constructor
	 * 
	 * @param lsPeriod List of the Coupon Periods
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public Stream (
		final java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsPeriod)
		throws java.lang.Exception
	{
		if (null == (_lsPeriod = lsPeriod) || 0 == _lsPeriod.size())
			throw new java.lang.Exception ("Stream ctr => Invalid Input params!");
	}

	/**
	 * Retrieve a list of the component's coupon periods
	 * 
	 * @return List of Coupon Period
	 */

	public java.util.List<org.drip.analytics.cashflow.CompositePeriod> periods()
	{
		return _lsPeriod;
	}

	/**
	 * Retrieve the Stream Frequency
	 * 
	 * @return The Stream Frequency
	 */

	public int freq()
	{
		return _lsPeriod.get (0).freq();
	}

	/**
	 * Retrieve the Coupon Day Count
	 * 
	 * @return The Coupon Day Count
	 */

	public java.lang.String couponDC()
	{
		return _lsPeriod.get (0).periods().get (0).couponDC();
	}

	/**
	 * Retrieve the Coupon EOM Adjustment
	 * 
	 * @return The Coupon EOM Adjustment
	 */

	public boolean couponEOMAdjustment()
	{
		return _lsPeriod.get (0).periods().get (0).couponEOMAdjustment();
	}

	/**
	 * Retrieve the Calendar
	 * 
	 * @return The Calendar
	 */

	public java.lang.String calendar()
	{
		return _lsPeriod.get (0).periods().get (0).calendar();
	}

	/**
	 * Retrieve the Accrual Day Count
	 * 
	 * @return The Accrual Day Count
	 */

	public java.lang.String accrualDC()
	{
		return _lsPeriod.get (0).periods().get (0).accrualDC();
	}

	/**
	 * Retrieve the Accrual EOM Adjustment
	 * 
	 * @return The Accrual EOM Adjustment
	 */

	public boolean accrualEOMAdjustment()
	{
		return _lsPeriod.get (0).periods().get (0).accrualEOMAdjustment();
	}

	/**
	 * Retrieve the Credit Label
	 * 
	 * @return The Credit Label
	 */

	public org.drip.state.identifier.EntityCDSLabel creditLabel()
	{
		return _lsPeriod.get (0).creditLabel();
	}

	/**
	 * Retrieve the Floater Label
	 * 
	 * @return The Floater Label
	 */

	public org.drip.state.identifier.FloaterLabel floaterLabel()
	{
		return _lsPeriod.get (0).floaterLabel();
	}

	/**
	 * Retrieve the Forward Label, if Present
	 * 
	 * @return The Forward Label
	 */

	public org.drip.state.identifier.ForwardLabel forwardLabel()
	{
		org.drip.state.identifier.FloaterLabel floaterLabel = floaterLabel();

		return null != floaterLabel && floaterLabel instanceof org.drip.state.identifier.ForwardLabel ?
			(org.drip.state.identifier.ForwardLabel) floaterLabel : null;
	}

	/**
	 * Retrieve the OTC Fix Float Label, if Present
	 * 
	 * @return The OTC Fix Float Label
	 */

	public org.drip.state.identifier.OTCFixFloatLabel otcFixFloatLabel()
	{
		org.drip.state.identifier.FloaterLabel floaterLabel = floaterLabel();

		return null != floaterLabel && floaterLabel instanceof org.drip.state.identifier.OTCFixFloatLabel ?
			(org.drip.state.identifier.OTCFixFloatLabel) floaterLabel : null;
	}

	/**
	 * Retrieve the Funding Label
	 * 
	 * @return The Funding Label
	 */

	public org.drip.state.identifier.FundingLabel fundingLabel()
	{
		return _lsPeriod.get (0).fundingLabel();
	}

	/**
	 * Retrieve the FX Label
	 * 
	 * @return The FX Label
	 */

	public org.drip.state.identifier.FXLabel fxLabel()
	{
		return _lsPeriod.get (0).fxLabel();
	}

	/**
	 * Retrieve the Coupon Period List
	 * 
	 * @return The Coupon Period List
	 */

	public java.util.List<org.drip.analytics.cashflow.CompositePeriod> cashFlowPeriod()
	{
		return _lsPeriod;
	}

	/**
	 * Retrieve the Period Instance enveloping the specified Date
	 * 
	 * @param iDate The Date
	 * 
	 * @return The Period Instance enveloping the specified Date
	 */

	public org.drip.analytics.cashflow.CompositePeriod containingPeriod (
		final int iDate)
	{
		try {
			for (org.drip.analytics.cashflow.CompositePeriod cp : _lsPeriod) {
				if (cp.contains (iDate)) return cp;
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Initial Notional
	 * 
	 * @return The Initial Notional
	 */

	public double initialNotional()
	{
		return _lsPeriod.get (0).baseNotional();
	}

	/**
	 * Retrieve the Notional corresponding to the specified Date
	 * 
	 * @param iDate The Date
	 * 
	 * @return The Notional corresponding to the specified Date
	 * 
	 * @throws java.lang.Exception Thrown if the Notional cannot be computed
	 */

	public double notional (
		final int iDate)
		throws java.lang.Exception
	{
		int iEffectiveDate = effective().julian();

		int iAdjustedDate = iEffectiveDate > iDate ? iEffectiveDate : iDate;

		org.drip.analytics.cashflow.CompositePeriod cp = containingPeriod (iAdjustedDate);

		if (null == cp) throw new java.lang.Exception ("Stream::notional => Invalid Input");

		return cp.notional (iAdjustedDate);
	}

	/**
	 * Retrieve the Notional aggregated over the Date Pairs
	 * 
	 * @param iDate1 The Date #1
	 * @param iDate2 The Date #2
	 * 
	 * @return The Notional aggregated over the Date Pairs
	 * 
	 * @throws java.lang.Exception Thrown if the Notional cannot be computed
	 */

	public double notional (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		org.drip.analytics.cashflow.CompositePeriod cp = containingPeriod (iDate1);

		if (null == cp || !cp.contains (iDate2))
			throw new java.lang.Exception ("Stream::notional => Invalid Inputs");

		org.drip.quant.common.Array2D notlSchedule = cp.notionalSchedule();

		return initialNotional() * (null == notlSchedule ? 1. : notlSchedule.y (iDate1, iDate2));
	}

	/**
	 * Retrieve the Effective Date
	 * 
	 * @return The Effective Date
	 */

	public org.drip.analytics.date.JulianDate effective()
	{
		try {
			return new org.drip.analytics.date.JulianDate (_lsPeriod.get (0).startDate());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Maturity Date
	 * 
	 * @return The Maturity Date
	 */

	public org.drip.analytics.date.JulianDate maturity()
	{
		try {
			return new org.drip.analytics.date.JulianDate (_lsPeriod.get (_lsPeriod.size() - 1).endDate());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the First Coupon Pay Date
	 * 
	 * @return The First Coupon Pay Date
	 */

	public org.drip.analytics.date.JulianDate firstCouponDate()
	{
		try {
			return new org.drip.analytics.date.JulianDate (_lsPeriod.get (0).endDate());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Coupon Currency
	 * 
	 * @return The Coupon Currency
	 */

	public java.lang.String couponCurrency()
	{
		return _lsPeriod.get (_lsPeriod.size() - 1).couponCurrency();
	}

	/**
	 * Retrieve the Pay Currency
	 * 
	 * @return The Pay Currency
	 */

	public java.lang.String payCurrency()
	{
		return _lsPeriod.get (_lsPeriod.size() - 1).payCurrency();
	}

	/**
	 * Retrieve the Cash Flow Currency Set
	 * 
	 * @return The Cash Flow Currency Set
	 */

	public java.util.Set<java.lang.String> cashflowCurrencySet()
	{
		java.util.Set<java.lang.String> setCcy = new java.util.HashSet<java.lang.String>();

		setCcy.add (payCurrency());

		setCcy.add (couponCurrency());

		return setCcy;
	}

	/**
	 * Retrieve the Stream Name
	 * 
	 * @return The Stream Name
	 */

	public java.lang.String name()
	{
		org.drip.state.identifier.FloaterLabel floaterLabel = floaterLabel();

		java.lang.String strTrailer = "::{" + effective() + "->" + maturity() + "}";

		if (null != floaterLabel)
			return "FLOATSTREAM::" + payCurrency() + "::" + floaterLabel.fullyQualifiedName() + strTrailer;

		return "FIXEDSTREAM::" + payCurrency() + "/" + couponCurrency() + "::" + (12 / freq()) + strTrailer;
	}

	/**
	 * Get the Coupon Metrics for the period corresponding to the specified accrual end date
	 * 
	 * @param iAccrualEndDate The Accrual End Date
	 * @param valParams Valuation parameters
	 * @param csqs Market Parameters
	 * 
	 * @return The Coupon Metrics for the period corresponding to the specified accrual end date
	 */

	public org.drip.analytics.output.CompositePeriodCouponMetrics coupon (
		final int iAccrualEndDate,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs)
	{
		if (null == valParams) return null;

		org.drip.analytics.cashflow.CompositePeriod cp = containingPeriod (iAccrualEndDate);

		return null == cp ? null : cp.couponMetrics (valParams.valueDate(), csqs);
	}

	/**
	 * Generate the Calibration Quote Set corresponding to the specified Latent State Array
	 * 
	 * @param aLSS The Latent State Array
	 * 
	 * @return The Calibration Quote Set corresponding to the specified Latent State Array
	 */

	public org.drip.product.calib.ProductQuoteSet calibQuoteSet (
		final org.drip.state.representation.LatentStateSpecification[] aLSS)
	{
		try {
			return null == floaterLabel() ? new org.drip.product.calib.FixedStreamQuoteSet (aLSS) : new
				org.drip.product.calib.FloatingStreamQuoteSet (aLSS);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Stream Coupon Basis
	 * 
	 * @return The Stream Coupon Basis
	 */

	public double basis()
	{
		return _lsPeriod.get (0).basis();
	}

	/**
	 * Generate a Value Map for the Stream
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The Pricer parameters
	 * @param csqs The Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return The Value Map for the Stream
	 */

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> value (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == valParams || null == csqs) return null;

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqs.fundingState (fundingLabel());

		if (null == dcFunding) return null;

		long lStart = System.nanoTime();

		int iValueDate = valParams.valueDate();

		double dblAccrued01 = 0.;
		double dblTotalCoupon = 0.;
		double dblCumulativeCouponDCF = 0.;
		double dblCumulativeCouponAmount = 0.;
		double dblAccrualCoupon = java.lang.Double.NaN;
		double dblUnadjustedDirtyPV = 0.;
		double dblUnadjustedDirtyDV01 = 0.;
		double dblCompoundingAdjustedDirtyPV = 0.;
		double dblCompoundingAdjustedDirtyDV01 = 0.;
		double dblCashPayDF = java.lang.Double.NaN;
		int iResetDate = java.lang.Integer.MIN_VALUE;
		double dblFXAdjustedValueNotional = java.lang.Double.NaN;
		double dblCreditForwardConvexityAdjustedDirtyPV = 0.;
		double dblCreditForwardConvexityAdjustedDirtyDV01 = 0.;
		double dblCreditFundingConvexityAdjustedDirtyPV = 0.;
		double dblCreditFundingConvexityAdjustedDirtyDV01 = 0.;
		double dblCreditFXConvexityAdjustedDirtyPV = 0.;
		double dblCreditFXConvexityAdjustedDirtyDV01 = 0.;
		double dblCumulativeConvexityAdjustedDirtyPV = 0.;
		double dblCumulativeConvexityAdjustedDirtyDV01 = 0.;
		double dblForwardFundingConvexityAdjustedDirtyPV = 0.;
		double dblForwardFundingConvexityAdjustedDirtyDV01 = 0.;
		double dblForwardFXConvexityAdjustedDirtyPV = 0.;
		double dblForwardFXConvexityAdjustedDirtyDV01 = 0.;
		double dblFundingFXConvexityAdjustedDirtyPV = 0.;
		double dblFundingFXConvexityAdjustedDirtyDV01 = 0.;

		for (org.drip.analytics.cashflow.CompositePeriod period : _lsPeriod) {
			double dblPeriodFX = java.lang.Double.NaN;
			double dblPeriodNotional = java.lang.Double.NaN;
			double dblUnadjustedDirtyPeriodDV01 = java.lang.Double.NaN;

			int iPeriodPayDate = period.payDate();

			int iPeriodEndDate = period.endDate();

			try {
				dblPeriodNotional = period.notional (iPeriodEndDate) * period.couponFactor (iPeriodEndDate);

				dblPeriodFX = period.fx (csqs);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (iPeriodPayDate < iValueDate) {
				org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = period.couponMetrics
					(iPeriodEndDate, csqs);

				if (null == cpcm) return null;

				double dblPeriodDCF = cpcm.dcf();

				dblCumulativeCouponDCF += dblPeriodDCF;

				dblCumulativeCouponAmount += dblPeriodNotional * dblPeriodFX * cpcm.rate() * dblPeriodDCF;

				continue;
			}

			org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = period.couponMetrics (iValueDate,
				csqs);

			if (null == cpcm) return null;

			double dblPeriodDCF = cpcm.dcf();

			double dblPeriodFullRate = cpcm.rate();

			org.drip.analytics.output.CompositePeriodAccrualMetrics cpam = period.accrualMetrics (iValueDate,
				csqs);

			try {
				if (null != cpam) {
					dblAccrualCoupon = cpam.rate();

					iResetDate = cpam.resetDate();

					double dblPeriodAccrualDCF = cpam.dcf();

					dblCumulativeCouponDCF += dblPeriodAccrualDCF;
					dblAccrued01 = 0.0001 * dblPeriodAccrualDCF * dblPeriodNotional * dblPeriodFX;

					dblCumulativeCouponAmount += dblPeriodNotional * dblPeriodFX * dblAccrualCoupon *
						dblPeriodAccrualDCF;
				}

				dblUnadjustedDirtyPeriodDV01 = 0.0001 * dblPeriodDCF * dblPeriodNotional * dblPeriodFX *
					period.survival (csqs) * period.df (csqs);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			double dblCompoundingAdjustedDirtyPeriodDV01 = dblUnadjustedDirtyPeriodDV01 * cpcm.compounding();

			double dblCreditForwardConvexityAdjustedDirtyPeriodDV01 = dblUnadjustedDirtyPeriodDV01 *
				cpcm.creditForward();

			double dblCreditFundingConvexityAdjustedDirtyPeriodDV01 = dblUnadjustedDirtyPeriodDV01 *
				cpcm.creditFunding();

			double dblCreditFXConvexityAdjustedDirtyPeriodDV01 = dblUnadjustedDirtyPeriodDV01 *
				cpcm.creditFX();

			double dblCumulativeConvexityAdjustedDirtyPeriodDV01 = dblUnadjustedDirtyPeriodDV01 *
				cpcm.cumulative();

			double dblForwardFundingConvexityAdjustedDirtyPeriodDV01 = dblUnadjustedDirtyPeriodDV01 *
				cpcm.forwardFunding();

			double dblForwardFXConvexityAdjustedDirtyPeriodDV01 = dblUnadjustedDirtyPeriodDV01 *
				cpcm.forwardFX();

			double dblFundingFXConvexityAdjustedDirtyPeriodDV01 = dblUnadjustedDirtyPeriodDV01 *
				cpcm.fundingFX();

			dblTotalCoupon += dblPeriodFullRate;
			dblUnadjustedDirtyDV01 += dblUnadjustedDirtyPeriodDV01;
			dblUnadjustedDirtyPV += dblUnadjustedDirtyPeriodDV01 * 10000. * dblPeriodFullRate;
			dblCompoundingAdjustedDirtyDV01 += dblCompoundingAdjustedDirtyPeriodDV01;
			dblCompoundingAdjustedDirtyPV += dblCompoundingAdjustedDirtyPeriodDV01 * 10000. *
				dblPeriodFullRate;
			dblCreditForwardConvexityAdjustedDirtyDV01 += dblCreditForwardConvexityAdjustedDirtyPeriodDV01;
			dblCreditForwardConvexityAdjustedDirtyPV += dblCreditForwardConvexityAdjustedDirtyPeriodDV01 *
				10000. * dblPeriodFullRate;
			dblCreditFundingConvexityAdjustedDirtyDV01 += dblCreditFundingConvexityAdjustedDirtyPeriodDV01;
			dblCreditFundingConvexityAdjustedDirtyPV += dblCreditFundingConvexityAdjustedDirtyPeriodDV01 *
				10000. * dblPeriodFullRate;
			dblCreditFXConvexityAdjustedDirtyDV01 += dblCreditFXConvexityAdjustedDirtyPeriodDV01;
			dblCreditFXConvexityAdjustedDirtyPV += dblCreditFXConvexityAdjustedDirtyPeriodDV01 * 10000. *
				dblPeriodFullRate;
			dblCumulativeConvexityAdjustedDirtyDV01 += dblCumulativeConvexityAdjustedDirtyPeriodDV01;
			dblCumulativeConvexityAdjustedDirtyPV += dblCumulativeConvexityAdjustedDirtyPeriodDV01 * 10000. *
				dblPeriodFullRate;
			dblForwardFundingConvexityAdjustedDirtyDV01 += dblForwardFundingConvexityAdjustedDirtyPeriodDV01;
			dblForwardFundingConvexityAdjustedDirtyPV += dblForwardFundingConvexityAdjustedDirtyPeriodDV01 *
				10000. * dblPeriodFullRate;
			dblForwardFXConvexityAdjustedDirtyDV01 += dblForwardFXConvexityAdjustedDirtyPeriodDV01;
			dblForwardFXConvexityAdjustedDirtyPV += dblForwardFXConvexityAdjustedDirtyPeriodDV01 * 10000. *
				dblPeriodFullRate;
			dblFundingFXConvexityAdjustedDirtyDV01 += dblFundingFXConvexityAdjustedDirtyPeriodDV01;
			dblFundingFXConvexityAdjustedDirtyPV += dblFundingFXConvexityAdjustedDirtyPeriodDV01 * 10000. *
				dblPeriodFullRate;
		}

		try {
			dblCashPayDF = dcFunding.df (iValueDate);

			dblFXAdjustedValueNotional = fxAdjustedNotional (iValueDate, csqs);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		dblUnadjustedDirtyPV /= dblCashPayDF;
		dblUnadjustedDirtyDV01 /= dblCashPayDF;
		dblCompoundingAdjustedDirtyPV /= dblCashPayDF;
		dblCompoundingAdjustedDirtyDV01 /= dblCashPayDF;
		dblCreditForwardConvexityAdjustedDirtyPV /= dblCashPayDF;
		dblCreditForwardConvexityAdjustedDirtyDV01 /= dblCashPayDF;
		dblCreditFundingConvexityAdjustedDirtyPV /= dblCashPayDF;
		dblCreditFundingConvexityAdjustedDirtyDV01 /= dblCashPayDF;
		dblCreditFXConvexityAdjustedDirtyPV /= dblCashPayDF;
		dblCreditFXConvexityAdjustedDirtyDV01 /= dblCashPayDF;
		dblCumulativeConvexityAdjustedDirtyPV /= dblCashPayDF;
		dblCumulativeConvexityAdjustedDirtyDV01 /= dblCashPayDF;
		dblForwardFundingConvexityAdjustedDirtyPV /= dblCashPayDF;
		dblForwardFundingConvexityAdjustedDirtyDV01 /= dblCashPayDF;
		dblForwardFXConvexityAdjustedDirtyPV /= dblCashPayDF;
		dblForwardFXConvexityAdjustedDirtyDV01 /= dblCashPayDF;
		dblFundingFXConvexityAdjustedDirtyPV /= dblCashPayDF;
		dblFundingFXConvexityAdjustedDirtyDV01 /= dblCashPayDF;
		double dblAccrued = 0. == dblAccrued01 ? 0. : dblAccrued01 * 10000. * dblAccrualCoupon;
		double dblUnadjustedCleanPV = dblUnadjustedDirtyPV - dblAccrued;
		double dblUnadjustedCleanDV01 = dblUnadjustedDirtyDV01 - dblAccrued01;
		double dblUnadjustedFairPremium = 0.0001 * dblUnadjustedCleanPV / dblUnadjustedCleanDV01;
		double dblCompoundingAdjustedCleanPV = dblCompoundingAdjustedDirtyPV - dblAccrued;
		double dblCompoundingAdjustedCleanDV01 = dblCompoundingAdjustedDirtyDV01 - dblAccrued01;
		double dblCompoundingAdjustedFairPremium = 0.0001 * dblCompoundingAdjustedCleanPV /
			dblCompoundingAdjustedCleanDV01;
		double dblCreditForwardConvexityAdjustedCleanPV = dblCreditForwardConvexityAdjustedDirtyPV -
			dblAccrued;
		double dblCreditForwardConvexityAdjustedCleanDV01 = dblCreditForwardConvexityAdjustedDirtyDV01 -
			dblAccrued01;
		double dblCreditForwardConvexityAdjustedFairPremium = 0.0001 *
			dblCreditForwardConvexityAdjustedCleanPV / dblCreditForwardConvexityAdjustedCleanDV01;
		double dblCreditFundingConvexityAdjustedCleanPV = dblCreditFundingConvexityAdjustedDirtyPV -
			dblAccrued;
		double dblCreditFundingConvexityAdjustedCleanDV01 = dblCreditFundingConvexityAdjustedDirtyDV01 -
			dblAccrued01;
		double dblCreditFundingConvexityAdjustedFairPremium = 0.0001 *
			dblCreditFundingConvexityAdjustedCleanPV / dblCreditFundingConvexityAdjustedCleanDV01;
		double dblCreditFXConvexityAdjustedCleanPV = dblCreditFXConvexityAdjustedDirtyPV - dblAccrued;
		double dblCreditFXConvexityAdjustedCleanDV01 = dblCreditFXConvexityAdjustedDirtyDV01 - dblAccrued01;
		double dblCreditFXConvexityAdjustedFairPremium = 0.0001 * dblCreditFXConvexityAdjustedCleanPV /
			dblCreditFXConvexityAdjustedCleanDV01;
		double dblCumulativeConvexityAdjustedCleanPV = dblCumulativeConvexityAdjustedDirtyPV - dblAccrued;
		double dblCumulativeConvexityAdjustedCleanDV01 = dblCumulativeConvexityAdjustedDirtyDV01 -
			dblAccrued01;
		double dblCumulativeConvexityAdjustedFairPremium = 0.0001 * dblCumulativeConvexityAdjustedCleanPV /
			dblCumulativeConvexityAdjustedCleanDV01;
		double dblForwardFundingConvexityAdjustedCleanPV = dblForwardFundingConvexityAdjustedDirtyPV -
			dblAccrued;
		double dblForwardFundingConvexityAdjustedCleanDV01 = dblForwardFundingConvexityAdjustedDirtyDV01 -
			dblAccrued01;
		double dblForwardFundingConvexityAdjustedFairPremium = 0.0001 *
			dblForwardFundingConvexityAdjustedCleanPV / dblForwardFundingConvexityAdjustedCleanDV01;
		double dblForwardFXConvexityAdjustedCleanPV = dblForwardFXConvexityAdjustedDirtyPV - dblAccrued;
		double dblForwardFXConvexityAdjustedCleanDV01 = dblForwardFXConvexityAdjustedDirtyDV01 -
			dblAccrued01;
		double dblForwardFXConvexityAdjustedFairPremium = 0.0001 * dblForwardFXConvexityAdjustedCleanPV /
			dblForwardFXConvexityAdjustedCleanDV01;
		double dblFundingFXConvexityAdjustedCleanPV = dblFundingFXConvexityAdjustedDirtyPV - dblAccrued;
		double dblFundingFXConvexityAdjustedCleanDV01 = dblFundingFXConvexityAdjustedDirtyDV01 -
			dblAccrued01;
		double dblFundingFXConvexityAdjustedFairPremium = 0.0001 * dblFundingFXConvexityAdjustedCleanPV /
			dblFundingFXConvexityAdjustedCleanDV01;

		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapResult = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double>();

		mapResult.put ("AccrualCoupon", dblAccrualCoupon);

		mapResult.put ("Accrued", dblAccrued);

		mapResult.put ("Accrued01", dblAccrued01);

		mapResult.put ("CleanDV01", dblCumulativeConvexityAdjustedCleanDV01);

		mapResult.put ("CleanPV", dblCumulativeConvexityAdjustedCleanPV);

		mapResult.put ("CompoundingAdjustedCleanDV01", dblCompoundingAdjustedCleanDV01);

		mapResult.put ("CompoundingAdjustedCleanPV", dblCompoundingAdjustedCleanPV);

		mapResult.put ("CompoundingAdjustedDirtyPV", dblCompoundingAdjustedDirtyPV);

		mapResult.put ("CompoundingAdjustedDirtyDV01", dblCompoundingAdjustedDirtyDV01);

		mapResult.put ("CompoundingAdjustedDirtyPV", dblCompoundingAdjustedDirtyPV);

		mapResult.put ("CompoundingAdjustedFairPremium", dblCompoundingAdjustedFairPremium);

		mapResult.put ("CompoundingAdjustedParRate", dblCompoundingAdjustedFairPremium);

		mapResult.put ("CompoundingAdjustedPV", dblCompoundingAdjustedCleanPV);

		mapResult.put ("CompoundingAdjustedRate", dblCompoundingAdjustedFairPremium);

		mapResult.put ("CompoundingAdjustedUpfront", dblCompoundingAdjustedCleanPV);

		mapResult.put ("CompoundingAdjustmentFactor", dblCompoundingAdjustedDirtyDV01 /
			dblUnadjustedDirtyDV01);

		mapResult.put ("CompoundingAdjustmentPremium", dblCompoundingAdjustedCleanPV - dblUnadjustedCleanPV);

		mapResult.put ("CompoundingAdjustmentPremiumUpfront", (dblCompoundingAdjustedCleanPV -
			dblUnadjustedCleanPV) / dblFXAdjustedValueNotional);

		mapResult.put ("CreditForwardConvexityAdjustedCleanDV01",
			dblCreditForwardConvexityAdjustedCleanDV01);

		mapResult.put ("CreditForwardConvexityAdjustedCleanPV", dblCreditForwardConvexityAdjustedCleanPV);

		mapResult.put ("CreditForwardConvexityAdjustedDirtyDV01",
			dblCreditForwardConvexityAdjustedDirtyDV01);

		mapResult.put ("CreditForwardConvexityAdjustedDirtyPV", dblCreditForwardConvexityAdjustedDirtyPV);

		mapResult.put ("CreditForwardConvexityAdjustedDV01", dblCreditForwardConvexityAdjustedDirtyDV01);

		mapResult.put ("CreditForwardConvexityAdjustedFairPremium",
			dblCreditForwardConvexityAdjustedFairPremium);

		mapResult.put ("CreditForwardConvexityAdjustedParRate",
			dblCreditForwardConvexityAdjustedFairPremium);

		mapResult.put ("CreditForwardConvexityAdjustedPV", dblCreditForwardConvexityAdjustedCleanPV);

		mapResult.put ("CreditForwardConvexityAdjustedRate", dblCreditForwardConvexityAdjustedFairPremium);

		mapResult.put ("CreditForwardConvexityAdjustedUpfront", dblCreditForwardConvexityAdjustedCleanPV);

		mapResult.put ("CreditForwardConvexityAdjustmentFactor", dblCreditForwardConvexityAdjustedDirtyDV01
			/ dblUnadjustedDirtyDV01);

		mapResult.put ("CreditForwardConvexityAdjustmentPremium", dblCreditForwardConvexityAdjustedCleanPV
			- dblUnadjustedCleanPV);

		mapResult.put ("CreditForwardConvexityAdjustmentPremiumUpfront",
			(dblCreditForwardConvexityAdjustedCleanPV - dblUnadjustedCleanPV) / dblFXAdjustedValueNotional);

		mapResult.put ("CreditFundingConvexityAdjustedCleanDV01",
			dblCreditFundingConvexityAdjustedCleanDV01);

		mapResult.put ("CreditFundingConvexityAdjustedCleanPV", dblCreditFundingConvexityAdjustedCleanPV);

		mapResult.put ("CreditFundingConvexityAdjustedDirtyDV01",
			dblCreditFundingConvexityAdjustedDirtyDV01);

		mapResult.put ("CreditFundingConvexityAdjustedDirtyPV", dblCreditFundingConvexityAdjustedDirtyPV);

		mapResult.put ("CreditFundingConvexityAdjustedDV01", dblCreditFundingConvexityAdjustedDirtyDV01);

		mapResult.put ("CreditFundingConvexityAdjustedFairPremium",
			dblCreditFundingConvexityAdjustedFairPremium);

		mapResult.put ("CreditFundingConvexityAdjustedParRate",
			dblCreditFundingConvexityAdjustedFairPremium);

		mapResult.put ("CreditFundingConvexityAdjustedPV", dblCreditFundingConvexityAdjustedCleanPV);

		mapResult.put ("CreditFundingConvexityAdjustedRate", dblCreditFundingConvexityAdjustedFairPremium);

		mapResult.put ("CreditFundingConvexityAdjustedUpfront", dblCreditFundingConvexityAdjustedCleanPV);

		mapResult.put ("CreditFundingConvexityAdjustmentFactor", dblCreditFundingConvexityAdjustedDirtyDV01
			/ dblUnadjustedDirtyDV01);

		mapResult.put ("CreditFundingConvexityAdjustmentPremium", dblCreditFundingConvexityAdjustedCleanPV
			- dblUnadjustedCleanPV);

		mapResult.put ("CreditFundingConvexityAdjustmentPremiumUpfront",
			(dblCreditFundingConvexityAdjustedCleanPV - dblUnadjustedCleanPV) / dblFXAdjustedValueNotional);

		mapResult.put ("CreditFXConvexityAdjustedCleanDV01", dblCreditFXConvexityAdjustedCleanDV01);

		mapResult.put ("CreditFXConvexityAdjustedCleanPV", dblCreditFXConvexityAdjustedCleanPV);

		mapResult.put ("CreditFXConvexityAdjustedDirtyDV01", dblCreditFXConvexityAdjustedDirtyDV01);

		mapResult.put ("CreditFXConvexityAdjustedDirtyPV", dblCreditFXConvexityAdjustedDirtyPV);

		mapResult.put ("CreditFXConvexityAdjustedDV01", dblCreditFXConvexityAdjustedDirtyDV01);

		mapResult.put ("CreditFXConvexityAdjustedFairPremium", dblCreditFXConvexityAdjustedFairPremium);

		mapResult.put ("CreditFXConvexityAdjustedParRate", dblCreditFXConvexityAdjustedFairPremium);

		mapResult.put ("CreditFXConvexityAdjustedPV", dblCreditFXConvexityAdjustedCleanPV);

		mapResult.put ("CreditFXConvexityAdjustedRate", dblCreditFXConvexityAdjustedFairPremium);

		mapResult.put ("CreditFXConvexityAdjustedUpfront", dblCreditFXConvexityAdjustedCleanPV);

		mapResult.put ("CreditFXConvexityAdjustmentFactor", dblCreditFXConvexityAdjustedDirtyDV01 /
			dblUnadjustedDirtyDV01);

		mapResult.put ("CreditFXConvexityAdjustmentPremium", dblCreditFXConvexityAdjustedCleanPV -
			dblUnadjustedCleanPV);

		mapResult.put ("CreditFXConvexityAdjustmentPremiumUpfront", (dblCreditFXConvexityAdjustedCleanPV -
			dblUnadjustedCleanPV) / dblFXAdjustedValueNotional);

		mapResult.put ("CumulativeConvexityAdjustedCleanDV01", dblCumulativeConvexityAdjustedCleanDV01);

		mapResult.put ("CumulativeConvexityAdjustedCleanPV", dblCumulativeConvexityAdjustedCleanPV);

		mapResult.put ("CumulativeConvexityAdjustedDirtyDV01", dblCumulativeConvexityAdjustedDirtyDV01);

		mapResult.put ("CumulativeConvexityAdjustedDirtyPV", dblCumulativeConvexityAdjustedDirtyPV);

		mapResult.put ("CumulativeConvexityAdjustedDV01", dblCumulativeConvexityAdjustedDirtyDV01);

		mapResult.put ("CumulativeConvexityAdjustedFairPremium", dblCumulativeConvexityAdjustedFairPremium);

		mapResult.put ("CumulativeConvexityAdjustedParRate", dblCumulativeConvexityAdjustedFairPremium);

		mapResult.put ("CumulativeConvexityAdjustedPV", dblCumulativeConvexityAdjustedCleanPV);

		mapResult.put ("CumulativeConvexityAdjustedRate", dblCumulativeConvexityAdjustedFairPremium);

		mapResult.put ("CumulativeConvexityAdjustedUpfront", dblCumulativeConvexityAdjustedCleanPV);

		mapResult.put ("CumulativeConvexityAdjustmentFactor", dblCumulativeConvexityAdjustedDirtyDV01 /
			dblUnadjustedDirtyDV01);

		mapResult.put ("CumulativeConvexityAdjustmentPremium", dblCumulativeConvexityAdjustedCleanPV -
			dblUnadjustedCleanPV);

		mapResult.put ("CumulativeConvexityAdjustmentPremiumUpfront", (dblCumulativeConvexityAdjustedCleanPV
			- dblUnadjustedCleanPV) / dblFXAdjustedValueNotional);

		mapResult.put ("CumulativeCouponAmount", dblCumulativeCouponAmount);

		mapResult.put ("CumulativeCouponDCF", dblCumulativeCouponDCF);

		mapResult.put ("CV01", dblCumulativeConvexityAdjustedCleanDV01);

		mapResult.put ("DirtyDV01", dblCumulativeConvexityAdjustedDirtyDV01);

		mapResult.put ("DirtyPV", dblCumulativeConvexityAdjustedDirtyPV);

		mapResult.put ("DV01", dblCumulativeConvexityAdjustedCleanDV01);

		mapResult.put ("FairPremium", dblCumulativeConvexityAdjustedFairPremium);

		mapResult.put ("Fixing01", dblAccrued01);

		mapResult.put ("ForwardFundingConvexityAdjustedCleanDV01",
			dblForwardFundingConvexityAdjustedCleanDV01);

		mapResult.put ("ForwardFundingConvexityAdjustedCleanPV", dblForwardFundingConvexityAdjustedCleanPV);

		mapResult.put ("ForwardFundingConvexityAdjustedDirtyDV01",
			dblForwardFundingConvexityAdjustedDirtyDV01);

		mapResult.put ("ForwardFundingConvexityAdjustedDirtyPV", dblForwardFundingConvexityAdjustedDirtyPV);

		mapResult.put ("ForwardFundingConvexityAdjustedDV01", dblForwardFundingConvexityAdjustedDirtyDV01);

		mapResult.put ("ForwardFundingConvexityAdjustedFairPremium",
			dblForwardFundingConvexityAdjustedFairPremium);

		mapResult.put ("ForwardFundingConvexityAdjustedParRate",
			dblForwardFundingConvexityAdjustedFairPremium);

		mapResult.put ("ForwardFundingConvexityAdjustedPV", dblForwardFundingConvexityAdjustedCleanPV);

		mapResult.put ("ForwardFundingConvexityAdjustedRate", dblForwardFundingConvexityAdjustedFairPremium);

		mapResult.put ("ForwardFundingConvexityAdjustedUpfront", dblForwardFundingConvexityAdjustedCleanPV);

		mapResult.put ("ForwardFundingConvexityAdjustmentFactor", dblForwardFundingConvexityAdjustedDirtyDV01
			/ dblUnadjustedDirtyDV01);

		mapResult.put ("ForwardFundingConvexityAdjustmentPremium", dblForwardFundingConvexityAdjustedCleanPV
			- dblUnadjustedCleanPV);

		mapResult.put ("ForwardFundingConvexityAdjustmentPremiumUpfront",
			(dblForwardFundingConvexityAdjustedCleanPV - dblUnadjustedCleanPV) / dblFXAdjustedValueNotional);

		mapResult.put ("ForwardFXConvexityAdjustedCleanDV01", dblForwardFXConvexityAdjustedCleanDV01);

		mapResult.put ("ForwardFXConvexityAdjustedCleanPV", dblForwardFXConvexityAdjustedCleanPV);

		mapResult.put ("ForwardFXConvexityAdjustedDirtyDV01", dblForwardFXConvexityAdjustedDirtyDV01);

		mapResult.put ("ForwardFXConvexityAdjustedDirtyPV", dblForwardFXConvexityAdjustedDirtyPV);

		mapResult.put ("ForwardFXConvexityAdjustedDV01", dblForwardFXConvexityAdjustedDirtyDV01);

		mapResult.put ("ForwardFXConvexityAdjustedFairPremium", dblForwardFXConvexityAdjustedFairPremium);

		mapResult.put ("ForwardFXConvexityAdjustedParRate", dblForwardFXConvexityAdjustedFairPremium);

		mapResult.put ("ForwardFXConvexityAdjustedPV", dblForwardFXConvexityAdjustedCleanPV);

		mapResult.put ("ForwardFXConvexityAdjustedRate", dblForwardFXConvexityAdjustedFairPremium);

		mapResult.put ("ForwardFXConvexityAdjustedUpfront", dblForwardFXConvexityAdjustedCleanPV);

		mapResult.put ("ForwardFXConvexityAdjustmentFactor", dblForwardFXConvexityAdjustedDirtyDV01 /
			dblUnadjustedDirtyDV01);

		mapResult.put ("ForwardFXConvexityAdjustmentPremium", dblForwardFXConvexityAdjustedCleanPV -
			dblUnadjustedCleanPV);

		mapResult.put ("ForwardFXConvexityAdjustmentPremiumUpfront", (dblForwardFXConvexityAdjustedCleanPV -
			dblUnadjustedCleanPV) / dblFXAdjustedValueNotional);

		mapResult.put ("FundingFXConvexityAdjustedCleanDV01", dblFundingFXConvexityAdjustedCleanDV01);

		mapResult.put ("FundingFXConvexityAdjustedCleanPV", dblFundingFXConvexityAdjustedCleanPV);

		mapResult.put ("FundingFXConvexityAdjustedDirtyDV01", dblFundingFXConvexityAdjustedDirtyDV01);

		mapResult.put ("FundingFXConvexityAdjustedDirtyPV", dblFundingFXConvexityAdjustedDirtyPV);

		mapResult.put ("FundingFXConvexityAdjustedDV01", dblFundingFXConvexityAdjustedDirtyDV01);

		mapResult.put ("FundingFXConvexityAdjustedFairPremium", dblFundingFXConvexityAdjustedFairPremium);

		mapResult.put ("FundingFXConvexityAdjustedParRate", dblFundingFXConvexityAdjustedFairPremium);

		mapResult.put ("FundingFXConvexityAdjustedPV", dblFundingFXConvexityAdjustedCleanPV);

		mapResult.put ("FundingFXConvexityAdjustedRate", dblFundingFXConvexityAdjustedFairPremium);

		mapResult.put ("FundingFXConvexityAdjustedUpfront", dblFundingFXConvexityAdjustedCleanPV);

		mapResult.put ("FundingFXConvexityAdjustmentFactor", dblFundingFXConvexityAdjustedDirtyDV01 /
			dblUnadjustedDirtyDV01);

		mapResult.put ("FundingFXConvexityAdjustmentPremium", dblFundingFXConvexityAdjustedCleanPV -
			dblUnadjustedCleanPV);

		mapResult.put ("FundingFXConvexityAdjustmentPremiumUpfront", (dblFundingFXConvexityAdjustedCleanPV -
			dblUnadjustedCleanPV) / dblFXAdjustedValueNotional);

		mapResult.put ("ParRate", dblCumulativeConvexityAdjustedFairPremium);

		mapResult.put ("PV", dblCumulativeConvexityAdjustedCleanPV);

		mapResult.put ("Rate", dblCumulativeConvexityAdjustedFairPremium);

		mapResult.put ("ResetDate", (double) iResetDate);

		mapResult.put ("ResetRate", dblAccrualCoupon - basis());

		mapResult.put ("TotalCoupon", dblTotalCoupon);

		mapResult.put ("UnadjustedCleanDV01", dblUnadjustedCleanDV01);

		mapResult.put ("UnadjustedCleanPV", dblUnadjustedCleanPV);

		mapResult.put ("UnadjustedDirtyDV01", dblUnadjustedDirtyDV01);

		mapResult.put ("UnadjustedDirtyPV", dblUnadjustedDirtyPV);

		mapResult.put ("UnadjustedFairPremium", dblUnadjustedFairPremium);

		mapResult.put ("UnadjustedParRate", dblUnadjustedFairPremium);

		mapResult.put ("UnadjustedPV", dblUnadjustedCleanPV);

		mapResult.put ("UnadjustedRate", dblUnadjustedFairPremium);

		mapResult.put ("UnadjustedUpfront", dblUnadjustedCleanPV);

		mapResult.put ("Upfront", dblCumulativeConvexityAdjustedCleanPV);

		double dblCompoundingAdjustedCleanPrice = 100. * (1. + (dblCompoundingAdjustedCleanPV /
			dblFXAdjustedValueNotional));
		double dblCreditForwardConvexityAdjustedCleanPrice = 100. * (1. +
			(dblCreditForwardConvexityAdjustedCleanPV / dblFXAdjustedValueNotional));
		double dblCreditFundingConvexityAdjustedCleanPrice = 100. * (1. +
			(dblCreditFundingConvexityAdjustedCleanPV / dblFXAdjustedValueNotional));
		double dblCreditFXConvexityAdjustedCleanPrice = 100. * (1. + (dblCreditFXConvexityAdjustedCleanPV
			/ dblFXAdjustedValueNotional));
		double dblCumulativeConvexityAdjustedCleanPrice = 100. * (1. + (dblCumulativeConvexityAdjustedCleanPV
			/ dblFXAdjustedValueNotional));
		double dblForwardFundingConvexityAdjustedCleanPrice = 100. * (1. +
			(dblForwardFundingConvexityAdjustedCleanPV / dblFXAdjustedValueNotional));
		double dblForwardFXConvexityAdjustedCleanPrice = 100. * (1. + (dblForwardFXConvexityAdjustedCleanPV /
			dblFXAdjustedValueNotional));
		double dblFundingFXConvexityAdjustedCleanPrice = 100. * (1. + (dblFundingFXConvexityAdjustedCleanPV /
			dblFXAdjustedValueNotional));
		double dblUnadjustedCleanPrice = 100. * (1. + (dblUnadjustedCleanPV / dblFXAdjustedValueNotional));

		mapResult.put ("CleanPrice", dblCumulativeConvexityAdjustedCleanPrice);

		mapResult.put ("CompoundingAdjustedCleanPrice", dblCompoundingAdjustedCleanPrice);

		mapResult.put ("CompoundingAdjustedDirtyPrice", 100. * (1. + (dblCompoundingAdjustedDirtyPV /
			dblFXAdjustedValueNotional)));

		mapResult.put ("CompoundingAdjustedPrice", dblCompoundingAdjustedCleanPrice);

		mapResult.put ("CreditForwardConvexityAdjustedCleanPrice",
			dblCreditForwardConvexityAdjustedCleanPrice);

		mapResult.put ("CreditForwardConvexityAdjustedDirtyPrice", 100. * (1. +
			(dblCreditForwardConvexityAdjustedDirtyPV / dblFXAdjustedValueNotional)));

		mapResult.put ("CreditForwardConvexityAdjustedPrice", dblCreditForwardConvexityAdjustedCleanPrice);

		mapResult.put ("CreditFundingConvexityAdjustedCleanPrice",
			dblCreditFundingConvexityAdjustedCleanPrice);

		mapResult.put ("CreditFundingConvexityAdjustedDirtyPrice", 100. * (1. +
			(dblCreditFundingConvexityAdjustedDirtyPV / dblFXAdjustedValueNotional)));

		mapResult.put ("CreditFundingConvexityAdjustedPrice", dblCreditFundingConvexityAdjustedCleanPrice);

		mapResult.put ("CreditFXConvexityAdjustedCleanPrice", dblCreditFXConvexityAdjustedCleanPrice);

		mapResult.put ("CreditFXConvexityAdjustedDirtyPrice", 100. * (1. +
			(dblCreditFXConvexityAdjustedDirtyPV / dblFXAdjustedValueNotional)));

		mapResult.put ("CreditFXConvexityAdjustedPrice", dblCreditFXConvexityAdjustedCleanPrice);

		mapResult.put ("CumulativeConvexityAdjustedCleanPrice", dblCumulativeConvexityAdjustedCleanPrice);

		mapResult.put ("CumulativeConvexityAdjustedDirtyPrice", 100. * (1. +
			(dblCumulativeConvexityAdjustedDirtyPV / dblFXAdjustedValueNotional)));

		mapResult.put ("CumulativeConvexityAdjustedPrice", dblCumulativeConvexityAdjustedCleanPrice);

		mapResult.put ("DirtyPrice", 100. * (1. + (dblCumulativeConvexityAdjustedDirtyPV /
			dblFXAdjustedValueNotional)));

		mapResult.put ("ForwardFundingConvexityAdjustedCleanPrice",
			dblForwardFundingConvexityAdjustedCleanPrice);

		mapResult.put ("ForwardFundingConvexityAdjustedDirtyPrice", 100. * (1. +
			(dblForwardFundingConvexityAdjustedDirtyPV / dblFXAdjustedValueNotional)));

		mapResult.put ("ForwardFundingConvexityAdjustedPrice", dblForwardFundingConvexityAdjustedCleanPrice);

		mapResult.put ("ForwardFXConvexityAdjustedCleanPrice", dblForwardFXConvexityAdjustedCleanPrice);

		mapResult.put ("ForwardFXConvexityAdjustedDirtyPrice", 100. * (1. +
			(dblForwardFXConvexityAdjustedDirtyPV / dblFXAdjustedValueNotional)));

		mapResult.put ("ForwardFXConvexityAdjustedPrice", dblForwardFXConvexityAdjustedCleanPrice);

		mapResult.put ("FundingFXConvexityAdjustedCleanPrice", dblFundingFXConvexityAdjustedCleanPrice);

		mapResult.put ("FundingFXConvexityAdjustedDirtyPrice", 100. * (1. +
			(dblFundingFXConvexityAdjustedDirtyPV / dblFXAdjustedValueNotional)));

		mapResult.put ("FundingFXConvexityAdjustedPrice", dblFundingFXConvexityAdjustedCleanPrice);

		mapResult.put ("Price", dblCumulativeConvexityAdjustedCleanPrice);

		mapResult.put ("UnadjustedCleanPrice", dblUnadjustedCleanPrice);

		mapResult.put ("UnadjustedDirtyPrice", 100. * (1. + (dblUnadjustedDirtyPV /
			dblFXAdjustedValueNotional)));

		mapResult.put ("UnadjustedPrice", dblUnadjustedCleanPrice);

		mapResult.put ("CalcTime", (System.nanoTime() - lStart) * 1.e-09);

		return mapResult;
	}

	/**
	 * Retrieve the set of the implemented measures
	 * 
	 * @return The set of the implemented measures
	 */

	public java.util.Set<java.lang.String> availableMeasures()
	{
		java.util.Set<java.lang.String> setstrMeasures = new java.util.TreeSet<java.lang.String>();

		setstrMeasures.add ("AccrualCoupon");

		setstrMeasures.add ("Accrued");

		setstrMeasures.add ("Accrued01");

		setstrMeasures.add ("CleanDV01");

		setstrMeasures.add ("CleanPV");

		setstrMeasures.add ("CompoundingAdjustedCleanDV01");

		setstrMeasures.add ("CompoundingAdjustedCleanPV");

		setstrMeasures.add ("CompoundingAdjustedDirtyDV01");

		setstrMeasures.add ("CompoundingAdjustedDirtyPV");

		setstrMeasures.add ("CompoundingAdjustedFairPremium");

		setstrMeasures.add ("CompoundingAdjustedParRate");

		setstrMeasures.add ("CompoundingAdjustedPV");

		setstrMeasures.add ("CompoundingAdjustedRate");

		setstrMeasures.add ("CompoundingAdjustedUpfront");

		setstrMeasures.add ("CompoundingAdjustmentFactor");

		setstrMeasures.add ("CompoundingAdjustmentPremium");

		setstrMeasures.add ("CompoundingAdjustmentPremiumUpfront");

		setstrMeasures.add ("CreditForwardConvexityAdjustedCleanDV01");

		setstrMeasures.add ("CreditForwardConvexityAdjustedCleanPV");

		setstrMeasures.add ("CreditForwardConvexityAdjustedDirtyDV01");

		setstrMeasures.add ("CreditForwardConvexityAdjustedDirtyPV");

		setstrMeasures.add ("CreditForwardConvexityAdjustedDV01");

		setstrMeasures.add ("CreditForwardConvexityAdjustedFairPremium");

		setstrMeasures.add ("CreditForwardConvexityAdjustedParRate");

		setstrMeasures.add ("CreditForwardConvexityAdjustedPV");

		setstrMeasures.add ("CreditForwardConvexityAdjustedRate");

		setstrMeasures.add ("CreditForwardConvexityAdjustedUpfront");

		setstrMeasures.add ("CreditForwardConvexityAdjustmentFactor");

		setstrMeasures.add ("CreditForwardConvexityAdjustmentPremium");

		setstrMeasures.add ("CreditForwardConvexityAdjustmentPremiumUpfront");

		setstrMeasures.add ("CreditFundingConvexityAdjustedCleanDV01");

		setstrMeasures.add ("CreditFundingConvexityAdjustedCleanPV");

		setstrMeasures.add ("CreditFundingConvexityAdjustedDirtyDV01");

		setstrMeasures.add ("CreditFundingConvexityAdjustedDirtyPV");

		setstrMeasures.add ("CreditFundingConvexityAdjustedDV01");

		setstrMeasures.add ("CreditFundingConvexityAdjustedFairPremium");

		setstrMeasures.add ("CreditFundingConvexityAdjustedParRate");

		setstrMeasures.add ("CreditFundingConvexityAdjustedPV");

		setstrMeasures.add ("CreditFundingConvexityAdjustedRate");

		setstrMeasures.add ("CreditFundingConvexityAdjustedUpfront");

		setstrMeasures.add ("CreditFundingConvexityAdjustmentFactor");

		setstrMeasures.add ("CreditFundingConvexityAdjustmentPremium");

		setstrMeasures.add ("CreditFundingConvexityAdjustmentPremiumUpfront");

		setstrMeasures.add ("CreditFXConvexityAdjustedCleanDV01");

		setstrMeasures.add ("CreditFXConvexityAdjustedCleanPV");

		setstrMeasures.add ("CreditFXConvexityAdjustedDirtyDV01");

		setstrMeasures.add ("CreditFXConvexityAdjustedDirtyPV");

		setstrMeasures.add ("CreditFXConvexityAdjustedDV01");

		setstrMeasures.add ("CreditFXConvexityAdjustedFairPremium");

		setstrMeasures.add ("CreditFXConvexityAdjustedParRate");

		setstrMeasures.add ("CreditFXConvexityAdjustedPV");

		setstrMeasures.add ("CreditFXConvexityAdjustedRate");

		setstrMeasures.add ("CreditFXConvexityAdjustedUpfront");

		setstrMeasures.add ("CreditFXConvexityAdjustmentFactor");

		setstrMeasures.add ("CreditFXConvexityAdjustmentPremium");

		setstrMeasures.add ("CreditFXConvexityAdjustmentPremiumUpfront");

		setstrMeasures.add ("CumulativeConvexityAdjustedCleanDV01");

		setstrMeasures.add ("CumulativeConvexityAdjustedCleanPV");

		setstrMeasures.add ("CumulativeConvexityAdjustedDirtyDV01");

		setstrMeasures.add ("CumulativeConvexityAdjustedDirtyPV");

		setstrMeasures.add ("CumulativeConvexityAdjustedDV01");

		setstrMeasures.add ("CumulativeConvexityAdjustedFairPremium");

		setstrMeasures.add ("CumulativeConvexityAdjustedParRate");

		setstrMeasures.add ("CumulativeConvexityAdjustedPV");

		setstrMeasures.add ("CumulativeConvexityAdjustedRate");

		setstrMeasures.add ("CumulativeConvexityAdjustedUpfront");

		setstrMeasures.add ("CumulativeConvexityAdjustmentFactor");

		setstrMeasures.add ("CumulativeConvexityAdjustmentPremium");

		setstrMeasures.add ("CumulativeConvexityAdjustmentPremiumUpfront");

		setstrMeasures.add ("CumulativeCouponAmount");

		setstrMeasures.add ("CV01");

		setstrMeasures.add ("DirtyDV01");

		setstrMeasures.add ("DirtyPV");

		setstrMeasures.add ("DV01");

		setstrMeasures.add ("FairPremium");

		setstrMeasures.add ("Fixing01");

		setstrMeasures.add ("ForwardFundingConvexityAdjustedCleanDV01");

		setstrMeasures.add ("ForwardFundingConvexityAdjustedCleanPV");

		setstrMeasures.add ("ForwardFundingConvexityAdjustedDirtyDV01");

		setstrMeasures.add ("ForwardFundingConvexityAdjustedDirtyPV");

		setstrMeasures.add ("ForwardFundingConvexityAdjustedDV01");

		setstrMeasures.add ("ForwardFundingConvexityAdjustedFairPremium");

		setstrMeasures.add ("ForwardFundingConvexityAdjustedParRate");

		setstrMeasures.add ("ForwardFundingConvexityAdjustedPV");

		setstrMeasures.add ("ForwardFundingConvexityAdjustedRate");

		setstrMeasures.add ("ForwardFundingConvexityAdjustedUpfront");

		setstrMeasures.add ("ForwardFundingConvexityAdjustmentFactor");

		setstrMeasures.add ("ForwardFundingConvexityAdjustmentPremium");

		setstrMeasures.add ("ForwardFundingConvexityAdjustmentPremiumUpfront");

		setstrMeasures.add ("ForwardFXConvexityAdjustedCleanDV01");

		setstrMeasures.add ("ForwardFXConvexityAdjustedCleanPV");

		setstrMeasures.add ("ForwardFXConvexityAdjustedDirtyDV01");

		setstrMeasures.add ("ForwardFXConvexityAdjustedDirtyPV");

		setstrMeasures.add ("ForwardFXConvexityAdjustedDV01");

		setstrMeasures.add ("ForwardFXConvexityAdjustedFairPremium");

		setstrMeasures.add ("ForwardFXConvexityAdjustedParRate");

		setstrMeasures.add ("ForwardFXConvexityAdjustedPV");

		setstrMeasures.add ("ForwardFXConvexityAdjustedRate");

		setstrMeasures.add ("ForwardFXConvexityAdjustedUpfront");

		setstrMeasures.add ("ForwardFXConvexityAdjustmentFactor");

		setstrMeasures.add ("ForwardFXConvexityAdjustmentPremium");

		setstrMeasures.add ("ForwardFXConvexityAdjustmentPremiumUpfront");

		setstrMeasures.add ("FundingFXConvexityAdjustedCleanDV01");

		setstrMeasures.add ("FundingFXConvexityAdjustedCleanPV");

		setstrMeasures.add ("FundingFXConvexityAdjustedDirtyDV01");

		setstrMeasures.add ("FundingFXConvexityAdjustedDirtyPV");

		setstrMeasures.add ("FundingFXConvexityAdjustedDV01");

		setstrMeasures.add ("FundingFXConvexityAdjustedFairPremium");

		setstrMeasures.add ("FundingFXConvexityAdjustedParRate");

		setstrMeasures.add ("FundingFXConvexityAdjustedPV");

		setstrMeasures.add ("FundingFXConvexityAdjustedRate");

		setstrMeasures.add ("FundingFXConvexityAdjustedUpfront");

		setstrMeasures.add ("FundingFXConvexityAdjustmentFactor");

		setstrMeasures.add ("FundingFXConvexityAdjustmentPremium");

		setstrMeasures.add ("FundingFXConvexityAdjustmentPremiumUpfront");

		setstrMeasures.add ("ParRate");

		setstrMeasures.add ("PV");

		setstrMeasures.add ("Rate");

		setstrMeasures.add ("ResetDate");

		setstrMeasures.add ("ResetRate");

		setstrMeasures.add ("TotalCoupon");

		setstrMeasures.add ("UnadjustedCleanDV01");

		setstrMeasures.add ("UnadjustedCleanPV");

		setstrMeasures.add ("UnadjustedDirtyDV01");

		setstrMeasures.add ("UnadjustedDirtyPV");

		setstrMeasures.add ("UnadjustedFairPremium");

		setstrMeasures.add ("UnadjustedParRate");

		setstrMeasures.add ("UnadjustedPV");

		setstrMeasures.add ("UnadjustedRate");

		setstrMeasures.add ("UnadjustedUpfront");

		setstrMeasures.add ("Upfront");

		return setstrMeasures;
	}

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

	public double pv (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
		throws java.lang.Exception
	{
		if (null == valParams || null == csqc)
			throw new java.lang.Exception ("Stream::pv => Invalid Inputs");

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqc.fundingState (fundingLabel());

		if (null == dcFunding) throw new java.lang.Exception ("Stream::pv => Invalid Inputs");

		int iValueDate = valParams.valueDate();

		double dblCumulativeConvexityAdjustedDirtyPV = 0.;

		for (org.drip.analytics.cashflow.CompositePeriod period : _lsPeriod) {
			int iPeriodPayDate = period.payDate();

			if (iPeriodPayDate < iValueDate) continue;

			org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = period.couponMetrics (iValueDate,
				csqc);

			if (null == cpcm) throw new java.lang.Exception ("Stream::pv => Invalid Inputs");

			int iPeriodEndDate = period.endDate();

			dblCumulativeConvexityAdjustedDirtyPV += cpcm.dcf() * period.notional (iPeriodEndDate) *
				period.couponFactor (iPeriodEndDate) * period.fx (csqc) * period.survival (csqc) *
					period.df (csqc) * cpcm.cumulative() * cpcm.rate();
		}

		return dblCumulativeConvexityAdjustedDirtyPV / dcFunding.df (iValueDate);
	}

	/**
	 * Generate the State Loading Constraints for the Forward Latent State
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The Pricer parameters
	 * @param csqs The Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param pqs The Product Calibration Quote Set
	 * 
	 * @return The State Loading Constraints for the Forward Latent State
	 */

	public org.drip.state.estimator.PredictorResponseWeightConstraint forwardPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == valParams || null == pqs) return null;

		org.drip.state.identifier.FloaterLabel floaterLabel = floaterLabel();

		org.drip.state.identifier.ForwardLabel forwardLabel = null != floaterLabel && floaterLabel instanceof
			org.drip.state.identifier.ForwardLabel ? (org.drip.state.identifier.ForwardLabel) floaterLabel :
				null;

		if ((null != forwardLabel && !(pqs instanceof org.drip.product.calib.FloatingStreamQuoteSet)) ||
			(null == forwardLabel && !(pqs instanceof org.drip.product.calib.FixedStreamQuoteSet)))
			return null;

		int iValueDate = valParams.valueDate();

		if (iValueDate >= maturity().julian()) return null;

		double dblCleanPV = 0.;

		try {
			if (pqs instanceof org.drip.product.calib.FloatingStreamQuoteSet) {
				org.drip.product.calib.FloatingStreamQuoteSet fsqs =
					(org.drip.product.calib.FloatingStreamQuoteSet) pqs;

				if (fsqs.containsPV()) dblCleanPV = fsqs.pv();
			} else if (pqs instanceof org.drip.product.calib.FixedStreamQuoteSet) {
				org.drip.product.calib.FixedStreamQuoteSet fsqs =
					(org.drip.product.calib.FixedStreamQuoteSet) pqs;

				if (fsqs.containsPV()) dblCleanPV = fsqs.pv();
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		for (org.drip.analytics.cashflow.CompositePeriod period : _lsPeriod) {
			int iPeriodEndDate = period.endDate();

			if (iPeriodEndDate < iValueDate) continue;

			org.drip.state.estimator.PredictorResponseWeightConstraint prwcPeriod = period.forwardPRWC
				(iValueDate, csqs, pqs);

			if (null == prwcPeriod || !prwc.absorb (prwcPeriod)) return null;
		}

		if (!prwc.updateValue (dblCleanPV)) return null;

		if (!prwc.updateDValueDManifestMeasure ("PV", 1.)) return null;

		return prwc;
	}

	/**
	 * Generate the State Loading Constraints for the Funding Latent State
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The Pricer parameters
	 * @param csqs The Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param pqs The Product Calibration Quote Set
	 * 
	 * @return The State Loading Constraints for the Funding Latent State
	 */

	public org.drip.state.estimator.PredictorResponseWeightConstraint fundingPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == valParams || null == pqs) return null;

		org.drip.state.identifier.ForwardLabel forwardLabel = forwardLabel();

		if ((null != forwardLabel && !(pqs instanceof org.drip.product.calib.FloatingStreamQuoteSet)) ||
			(null == forwardLabel && !(pqs instanceof org.drip.product.calib.FixedStreamQuoteSet)))
			return null;

		int iValueDate = valParams.valueDate();

		if (iValueDate >= maturity().julian()) return null;

		double dblCleanPV = 0.;

		try {
			if (pqs instanceof org.drip.product.calib.FloatingStreamQuoteSet) {
				org.drip.product.calib.FloatingStreamQuoteSet fsqs =
					(org.drip.product.calib.FloatingStreamQuoteSet) pqs;

				if (fsqs.containsPV()) dblCleanPV = fsqs.pv();
			} else if (pqs instanceof org.drip.product.calib.FixedStreamQuoteSet) {
				org.drip.product.calib.FixedStreamQuoteSet fsqs =
					(org.drip.product.calib.FixedStreamQuoteSet) pqs;

				if (fsqs.containsPV()) dblCleanPV = fsqs.pv();
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		for (org.drip.analytics.cashflow.CompositePeriod period : _lsPeriod) {
			int iPeriodEndDate = period.endDate();

			if (iPeriodEndDate < iValueDate) continue;

			org.drip.state.estimator.PredictorResponseWeightConstraint prwcPeriod = period.fundingPRWC
				(iValueDate, csqs, pqs);

			if (null == prwcPeriod || !prwc.absorb (prwcPeriod)) return null;
		}

		if (!prwc.updateValue (dblCleanPV)) return null;

		if (!prwc.updateDValueDManifestMeasure ("PV", 1.)) return null;

		return prwc;
	}

	/**
	 * Generate the State Loading Constraints for the Merged Forward/Funding Latent State
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The Pricer parameters
	 * @param csqs The Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param pqs The Product Calibration Quote Set
	 * 
	 * @return The State Loading Constraints for the Merged Forward/Funding Latent State
	 */

	public org.drip.state.estimator.PredictorResponseWeightConstraint fundingForwardPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == valParams || null == pqs) return null;

		org.drip.state.identifier.ForwardLabel forwardLabel = forwardLabel();

		if ((null != forwardLabel && !(pqs instanceof org.drip.product.calib.FloatingStreamQuoteSet)) ||
			(null == forwardLabel && !(pqs instanceof org.drip.product.calib.FixedStreamQuoteSet)))
			return null;

		int iValueDate = valParams.valueDate();

		if (iValueDate >= maturity().julian()) return null;

		double dblCleanPV = 0.;

		try {
			if (pqs instanceof org.drip.product.calib.FloatingStreamQuoteSet) {
				org.drip.product.calib.FloatingStreamQuoteSet fsqs =
					(org.drip.product.calib.FloatingStreamQuoteSet) pqs;

				if (fsqs.containsPV()) dblCleanPV = fsqs.pv();
			} else if (pqs instanceof org.drip.product.calib.FixedStreamQuoteSet) {
				org.drip.product.calib.FixedStreamQuoteSet fsqs =
					(org.drip.product.calib.FixedStreamQuoteSet) pqs;

				if (fsqs.containsPV()) dblCleanPV = fsqs.pv();
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		for (org.drip.analytics.cashflow.CompositePeriod period : _lsPeriod) {
			int iPeriodEndDate = period.endDate();

			if (iPeriodEndDate < iValueDate) continue;

			org.drip.state.estimator.PredictorResponseWeightConstraint prwcPeriod = period.forwardFundingPRWC
				(iValueDate, csqs, pqs);

			if (null == prwcPeriod || !prwc.absorb (prwcPeriod)) return null;
		}

		if (!prwc.updateValue (dblCleanPV)) return null;

		if (!prwc.updateDValueDManifestMeasure ("PV", 1.)) return null;

		return prwc;
	}

	/**
	 * Generate the Calibratable Linearized Predictor/Response Constraint Weights for the Non-merged FX Curve
	 *  FX Forward Latent State from the Component's Cash Flows. The Constraints here typically correspond to
	 *  Date/Cash Flow pairs and the corresponding leading PV.
	 * 
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Component Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param pqs Product Quote Set
	 * 
	 * @return The Calibratable Linearized Predictor/Response Constraints (Date/Cash Flow pairs and the
	 * 	corresponding FX Forward)
	 */

	public org.drip.state.estimator.PredictorResponseWeightConstraint fxPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == valParams) return null;

		int iValueDate = valParams.valueDate();

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		for (org.drip.analytics.cashflow.CompositePeriod period : _lsPeriod) {
			int iPeriodEndDate = period.endDate();

			if (iPeriodEndDate < iValueDate) continue;

			org.drip.state.estimator.PredictorResponseWeightConstraint prwcPeriod = period.fxPRWC
				(iValueDate, csqs, pqs);

			if (null == prwcPeriod || !prwc.absorb (prwcPeriod)) return null;
		}

		return prwc;
	}

	/**
	 * Generate the Calibratable Linearized Predictor/Response Constraint Weights for the Non-merged Govvie
	 * 	Curve Yield Latent State from the Component's Cash Flows. The Constraints here typically correspond
	 *  to Date/Cash Flow pairs and the corresponding leading PV.
	 * 
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Component Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param pqs Product Quote Set
	 * 
	 * @return The Calibratable Linearized Predictor/Response Constraints (Date/Cash Flow pairs and the
	 * 	corresponding Yield)
	 */

	public org.drip.state.estimator.PredictorResponseWeightConstraint govviePRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		return null;
	}

	/**
	 * Generate the Calibratable Linearized Predictor/Response Constraint Weights for the Non-merged
	 *  Volatility Curve Volatility Latent State from the Component's Cash Flows. The Constraints here
	 *  typically correspond tovDate/Cash Flow pairs and the corresponding leading PV.
	 * 
	 * @param valParams Valuation Parameters
	 * @param pricerParams Pricer Parameters
	 * @param csqs Component Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * @param pqs Product Quote Set
	 * 
	 * @return The Calibratable Linearized Predictor/Response Constraints (Date/Cash Flow pairs and the
	 * 	corresponding Volatility)
	 */

	public org.drip.state.estimator.PredictorResponseWeightConstraint volatilityPRWC (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == valParams) return null;

		int iValueDate = valParams.valueDate();

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		for (org.drip.analytics.cashflow.CompositePeriod period : _lsPeriod) {
			int iPeriodEndDate = period.endDate();

			if (iPeriodEndDate < iValueDate) continue;

			org.drip.state.estimator.PredictorResponseWeightConstraint prwcPeriod = period.volatilityPRWC
				(iValueDate, csqs, pqs);

			if (null == prwcPeriod || !prwc.absorb (prwcPeriod)) return null;
		}

		return prwc;
	}

	/**
	 * Generate the Jacobian of the Dirty PV to the Manifest Measure
	 * 
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The Pricer parameters
	 * @param csqs The Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return The Jacobian of the Dirty PV to the Manifest Measure
	 */

	public org.drip.quant.calculus.WengertJacobian jackDDirtyPVDManifestMeasure (
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == valParams || valParams.valueDate() >= maturity().julian() || null == csqs) return null;

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqs.fundingState (fundingLabel());

		if (null == dcFunding) return null;

		try {
			org.drip.quant.calculus.WengertJacobian jackDDirtyPVDManifestMeasure = null;

			for (org.drip.analytics.cashflow.CompositePeriod p : _lsPeriod) {
				int iPeriodPayDate = p.payDate();

				if (p.startDate() < valParams.valueDate()) continue;

				org.drip.quant.calculus.WengertJacobian jackDDFDManifestMeasure =
					dcFunding.jackDDFDManifestMeasure (iPeriodPayDate, "PV");

				if (null == jackDDFDManifestMeasure) continue;

				int iNumQuote = jackDDFDManifestMeasure.numParameters();

				if (0 == iNumQuote) continue;

				if (null == jackDDirtyPVDManifestMeasure)
					jackDDirtyPVDManifestMeasure = new org.drip.quant.calculus.WengertJacobian (1,
						iNumQuote);

				double dblPeriodNotional = p.notional (p.startDate(), p.endDate()) * p.fx (csqs);

				double dblPeriodDCF = p.couponMetrics (valParams.valueDate(), csqs).dcf();

				for (int k = 0; k < iNumQuote; ++k) {
					if (!jackDDirtyPVDManifestMeasure.accumulatePartialFirstDerivative (0, k,
						dblPeriodNotional * dblPeriodDCF * jackDDFDManifestMeasure.firstDerivative (0, k)))
						return null;
				}
			}

			return jackDDirtyPVDManifestMeasure;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the micro-Jacobian of the Manifest Measure to the Discount Factor
	 * 
	 * @param strManifestMeasure The Manifest Measure
	 * @param valParams The Valuation Parameters
	 * @param pricerParams The Pricer parameters
	 * @param csqs The Market Parameters
	 * @param vcp Valuation Customization Parameters
	 * 
	 * @return The micro-Jacobian of the Manifest Measure to the Discount Factor
	 */

	public org.drip.quant.calculus.WengertJacobian manifestMeasureDFMicroJack (
		final java.lang.String strManifestMeasure,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.param.pricer.CreditPricerParams pricerParams,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqs,
		final org.drip.param.valuation.ValuationCustomizationParams vcp)
	{
		if (null == valParams || valParams.valueDate() >= _lsPeriod.get (_lsPeriod.size() - 1).endDate() ||
			null == strManifestMeasure)
			return null;

		org.drip.state.discount.MergedDiscountForwardCurve dcFunding = csqs.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (payCurrency()));

		if (null == dcFunding) return null;

		if ("Rate".equalsIgnoreCase (strManifestMeasure) || "SwapRate".equalsIgnoreCase (strManifestMeasure))
		{
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapMeasures = value
				(valParams, pricerParams, csqs, vcp);

			if (null == mapMeasures) return null;

			double dblDirtyDV01 = mapMeasures.get ("DirtyDV01");

			double dblParSwapRate = mapMeasures.get ("SwapRate");

			try {
				org.drip.quant.calculus.WengertJacobian wjSwapRateDFMicroJack = null;

				for (org.drip.analytics.cashflow.CompositePeriod p : _lsPeriod) {
					int iPeriodPayDate = p.payDate();

					if (iPeriodPayDate < valParams.valueDate()) continue;

					double dblPeriodDCF = p.couponMetrics (valParams.valueDate(), csqs).dcf();

					org.drip.quant.calculus.WengertJacobian wjPeriodFwdRateDF =
						dcFunding.jackDForwardDManifestMeasure (p.startDate(), p.endDate(), "Rate",
							dblPeriodDCF);

					org.drip.quant.calculus.WengertJacobian wjPeriodPayDFDF =
						dcFunding.jackDDFDManifestMeasure (iPeriodPayDate, "Rate");

					if (null == wjPeriodFwdRateDF || null == wjPeriodPayDFDF) continue;

					double dblForwardRate = dcFunding.libor (p.startDate(), p.endDate());

					double dblPeriodPayDF = dcFunding.df (iPeriodPayDate);

					if (null == wjSwapRateDFMicroJack)
						wjSwapRateDFMicroJack = new org.drip.quant.calculus.WengertJacobian (1,
							wjPeriodFwdRateDF.numParameters());

					double dblPeriodNotional = notional (p.startDate(), p.endDate());

					for (int k = 0; k < wjPeriodFwdRateDF.numParameters(); ++k) {
						double dblPeriodMicroJack = (dblForwardRate - dblParSwapRate) *
							wjPeriodPayDFDF.firstDerivative (0, k) + dblPeriodPayDF *
								wjPeriodFwdRateDF.firstDerivative (0, k);

						if (!wjSwapRateDFMicroJack.accumulatePartialFirstDerivative (0, k, dblPeriodNotional
							* dblPeriodDCF * dblPeriodMicroJack / dblDirtyDV01))
							return null;
					}
				}

				return wjSwapRateDFMicroJack;
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
