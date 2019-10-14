
package org.drip.analytics.cashflow;

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
 * <i>CompositePeriod</i> implements the Composite Coupon Period Functionality. It contains the Composite
 * Period Coupon Frequency, Tenor, Accrual Compounding Rule, Day Count, Base Notional, Coupon/Notional
 * Schedules, Pay Currency, Credit Label, FX Fixing Setting, and the List of Composable Period Units.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/README.md">Date, Cash Flow, and Cash Flow Period Measure Generation Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/cashflow/README.md">Unit and Composite Cash Flow Periods</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class CompositePeriod {
	private int _iFreq = -1;
	private java.lang.String _strTenor = "";
	private int _iAccrualCompoundingRule = -1;
	private java.lang.String _strPayCurrency = "";
	private int _iPayDate = java.lang.Integer.MIN_VALUE;
	private double _dblBaseNotional = java.lang.Double.NaN;
	private org.drip.numerical.common.Array2D _fsCoupon = null;
	private org.drip.numerical.common.Array2D _fsNotional = null;
	private org.drip.state.identifier.EntityCDSLabel _creditLabel = null;
	private org.drip.param.period.FixingSetting _fxFixingSetting = null;
	private java.util.List<org.drip.analytics.cashflow.ComposableUnitPeriod> _lsCUP = null;

	protected CompositePeriod (
		final org.drip.param.period.CompositePeriodSetting cps,
		final java.util.List<org.drip.analytics.cashflow.ComposableUnitPeriod> lsCUP)
		throws java.lang.Exception
	{
		if (
			null == cps ||
			null == (_lsCUP = lsCUP) || 0 == _lsCUP.size()
		)
			throw new java.lang.Exception ("CompositePeriod Constructor => Invalid Inputs");

		_iFreq = cps.freq();

		_strTenor = cps.tenor();

		org.drip.analytics.daycount.DateAdjustParams dapPay = cps.dapPay();

		org.drip.analytics.cashflow.ComposableUnitPeriod cupFinal = _lsCUP.get (_lsCUP.size() - 1);

		_iAccrualCompoundingRule = cupFinal.accrualCompoundingRule();

		_iPayDate = cupFinal.endDate();

		if (null != dapPay) _iPayDate = dapPay.roll (_iPayDate);

		_strPayCurrency = cps.payCurrency();

		_dblBaseNotional = cps.baseNotional();

		_fxFixingSetting = cps.fxFixingSetting();

		_creditLabel = cps.creditLabel();

		_fsNotional = cps.notionalSchedule();

		_fsCoupon = cps.couponSchedule();
	}

	/**
	 * Retrieve the List of Composable Periods
	 * 
	 * @return The List of Composable Periods
	 */

	public java.util.List<org.drip.analytics.cashflow.ComposableUnitPeriod> periods()
	{
		return _lsCUP;
	}

	/**
	 * Retrieve the Period Start Date
	 * 
	 * @return The Period Start Date
	 */

	public int startDate()
	{
		return _lsCUP.get (0).startDate();
	}

	/**
	 * Retrieve the Period End Date
	 * 
	 * @return The Period End Date
	 */

	public int endDate()
	{
		return _lsCUP.get (_lsCUP.size() - 1).endDate();
	}

	/**
	 * Check whether the supplied Date is inside the Period specified
	 * 
	 * @param iDate Date
	 * 
	 * @return TRUE - The specified Date is inside the Period
	 */

	public boolean contains (
		final int iDate)
	{
		return iDate >= startDate() && iDate <= endDate();
	}

	/**
	 * Return the Unit Period to which the Date belongs
	 * 
	 * @param iDate Date
	 * 
	 * @return The Unit Period to which the Date belongs
	 */

	public org.drip.analytics.cashflow.ComposableUnitPeriod enclosingCUP (
		final int iDate)
	{
		if (!contains (iDate)) return null;

		for (org.drip.analytics.cashflow.ComposableUnitPeriod cup : _lsCUP) {
			int iDateLocation = cup.dateLocation (iDate);

			if (org.drip.analytics.cashflow.ComposableUnitFixedPeriod.NODE_INSIDE_SEGMENT == iDateLocation)
				return cup;
		}

		return null;
	}

	/**
	 * Retrieve the Accrual Compounding Rule
	 * 
	 * @return The Accrual Compounding Rule
	 */

	public int accrualCompoundingRule()
	{
		return _iAccrualCompoundingRule;
	}

	/**
	 * Return the Period Pay Date
	 * 
	 * @return Period Pay Date
	 */

	public int payDate()
	{
		return _iPayDate;
	}

	/**
	 * Return the Period FX Fixing Date
	 * 
	 * @return Period FX Fixing Date
	 * 
	 * @throws java.lang.Exception Thrown if FX Fixing Date cannot be generated
	 */

	public int fxFixingDate()
		throws java.lang.Exception
	{
		if (null == _fxFixingSetting) return _iPayDate;

		int iUnadjustedFixingDate = java.lang.Integer.MIN_VALUE;

		int iFixingType = _fxFixingSetting.type();

		if (org.drip.param.period.FixingSetting.FIXING_COMPOSITE_PERIOD_END == iFixingType)
			iUnadjustedFixingDate = endDate();
		else if (org.drip.param.period.FixingSetting.FIXING_COMPOSITE_PERIOD_START == iFixingType)
			iUnadjustedFixingDate = startDate();
		else if (org.drip.param.period.FixingSetting.FIXING_PRESET_STATIC == iFixingType)
			iUnadjustedFixingDate = _fxFixingSetting.staticDate();

		org.drip.analytics.daycount.DateAdjustParams dapFixing = _fxFixingSetting.dap();

		if (null == dapFixing) return iUnadjustedFixingDate;

		return dapFixing.roll (iUnadjustedFixingDate);
	}

	/**
	 * Is this Cash Flow FX MTM?
	 * 
	 * @return TRUE - FX MTM is on (i.e., FX is not Driven by Fixing)
	 */

	public boolean isFXMTM()
	{
		return null == _fxFixingSetting;
	}

	/**
	 * Coupon Period FX
	 * 
	 * @param csqc Market Parameters
	 * 
	 * @return The Period FX
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double fx (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		org.drip.state.identifier.FXLabel fxLabel = fxLabel();

		if (null == fxLabel) return 1.;

		if (null == csqc) throw new java.lang.Exception ("CompositePeriod::fx => Invalid Inputs");

		if (!isFXMTM())
			return csqc.fixing (
				fxFixingDate(),
				fxLabel
			);

		org.drip.state.fx.FXCurve fxfc = csqc.fxState (fxLabel);

		if (null == fxfc)
			throw new java.lang.Exception ("CompositePeriod::fx => No Curve for " +
				fxLabel.fullyQualifiedName());

		return fxfc.fx (_iPayDate);
	}

	/**
	 * Retrieve the Coupon Frequency
	 * 
	 * @return The Coupon Frequency
	 */

	public int freq()
	{
		return _iFreq;
	}

	/**
	 * Convert the Coupon Frequency into a Tenor
	 * 
	 * @return The Coupon Frequency converted into a Tenor
	 */

	public java.lang.String tenor()
	{
		if (null != _strTenor && !_strTenor.isEmpty()) return _strTenor;

		int iTenorInMonths = 12 / freq() ;

		return
			1 == iTenorInMonths ||
			2 == iTenorInMonths ||
			3 == iTenorInMonths ||
			6 == iTenorInMonths ||
			12 == iTenorInMonths ?
			iTenorInMonths + "M" : "ON";
	}

	/**
	 * Retrieve the Pay Currency
	 * 
	 * @return The Pay Currency
	 */

	public java.lang.String payCurrency()
	{
		return _strPayCurrency;
	}

	/**
	 * Retrieve the Coupon Currency
	 * 
	 * @return The Coupon Currency
	 */

	public java.lang.String couponCurrency()
	{
		return _lsCUP.get (0).couponCurrency();
	}

	/**
	 * Retrieve the Period Basis
	 * 
	 * @return The Period Basis
	 */

	public double basis()
	{
		return _lsCUP.get (0).basis();
	}

	/**
	 * Coupon Period Survival Probability
	 * 
	 * @param csqc Market Parameters
	 * 
	 * @return The Period Survival Probability
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double survival (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		org.drip.state.identifier.EntityCDSLabel creditLabel = creditLabel();

		if (null == creditLabel) return 1.;

		if (null == csqc) throw new java.lang.Exception ("CompositePeriod::survival => Invalid Inputs");

		org.drip.state.credit.CreditCurve cc = csqc.creditState (creditLabel);

		if (null == cc)
			throw new java.lang.Exception ("CompositePeriod::survival => No Curve for " +
				creditLabel.fullyQualifiedName());

		return cc.survival (_iPayDate);
	}

	/**
	 * Retrieve the Coupon Period Recovery
	 * 
	 * @param csqc Market Parameters
	 * 
	 * @return The Period Recovery
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double recovery (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		org.drip.state.identifier.EntityCDSLabel creditLabel = creditLabel();

		if (null == creditLabel) return 1.;

		if (null == csqc) throw new java.lang.Exception ("CompositePeriod::recovery => Invalid Inputs");

		org.drip.state.credit.CreditCurve cc = csqc.creditState (creditLabel);

		if (null == cc)
			throw new java.lang.Exception ("CompositePeriod::recovery => No Curve for " +
				creditLabel.fullyQualifiedName());

		return cc.recovery (_iPayDate);
	}

	/**
	 * Retrieve the Coupon Period Discount Factor
	 * 
	 * @param csqc Market Parameters
	 * 
	 * @return The Period Discount Factor
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double df (
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		org.drip.state.identifier.FundingLabel fundingLabel = fundingLabel();

		if (null == csqc) throw new java.lang.Exception ("CompositePeriod::df => Invalid Inputs");

		org.drip.state.discount.MergedDiscountForwardCurve dc = csqc.fundingState (fundingLabel);

		if (null == dc)
			throw new java.lang.Exception ("CompositePeriod::df => No Curve for " +
				fundingLabel.fullyQualifiedName());

		return dc.df (_iPayDate);
	}

	/**
	 * Get the Period Base Notional
	 * 
	 * @return Period Base Notional
	 */

	public double baseNotional()
	{
		return _dblBaseNotional;
	}

	/**
	 * Get the Period Notional Schedule
	 * 
	 * @return Period Notional Schedule
	 */

	public org.drip.numerical.common.Array2D notionalSchedule()
	{
		return _fsNotional;
	}

	/**
	 * Retrieve the Coupon Period Notional Corresponding to the specified Date
	 * 
	 * @param iDate The Specified Date
	 * 
	 * @return The Period Notional Corresponding to the specified Date
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double notional (
		final int iDate)
		throws java.lang.Exception
	{
		if (!contains (iDate))
			throw new java.lang.Exception ("CompositePeriod::notional => Invalid Inputs: " + iDate + " [" +
				startDate() + " => " + endDate() + "]");

		return _dblBaseNotional * (null == _fsNotional ? 1. : _fsNotional.y (iDate));
	}

	/**
	 * Retrieve the Coupon Period Notional Aggregated over the specified Dates
	 * 
	 * @param iDate1 The Date #1
	 * @param iDate2 The Date #2
	 * 
	 * @return The Period Notional Aggregated over the specified Dates
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double notional (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		if (
			!contains (iDate1) ||
			!contains (iDate2)
		)
			throw new java.lang.Exception ("CompositePeriod::notional => Invalid Dates");

		return _dblBaseNotional * (
			null == _fsNotional ? 1. : _fsNotional.y (
				iDate1,
				iDate2
			)
		);
	}

	/**
	 * Get the Period Coupon Schedule
	 * 
	 * @return Period Coupon Schedule
	 */

	public org.drip.numerical.common.Array2D couponSchedule()
	{
		return _fsCoupon;
	}

	/**
	 * Retrieve the Period Coupon Schedule Factor Corresponding to the specified Date
	 * 
	 * @param iDate The Specified Date
	 * 
	 * @return The Period Coupon Schedule Factor Corresponding to the specified Date
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double couponFactor (
		final int iDate)
		throws java.lang.Exception
	{
		if (!contains (iDate))
			throw new java.lang.Exception ("CompositePeriod::couponFactor => Invalid Inputs");

		return null == _fsCoupon ? 1. : _fsCoupon.y (iDate);
	}

	/**
	 * Retrieve the Period Coupon Schedule Factor Aggregated over the specified Dates
	 * 
	 * @param iDate1 The Date #1
	 * @param iDate2 The Date #2
	 * 
	 * @return The Period Coupon Schedule Factor Aggregated over the specified Dates
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double couponFactor (
		final int iDate1,
		final int iDate2)
		throws java.lang.Exception
	{
		if (!contains (iDate1) || !contains (iDate2))
			throw new java.lang.Exception ("CompositePeriod::couponFactor => Invalid Dates");

		return null == _fsCoupon ? 1. : _fsCoupon.y (
			iDate1,
			iDate2
		);
	}

	/**
	 * Return the Credit Label
	 * 
	 * @return The Credit Label
	 */

	public org.drip.state.identifier.EntityCDSLabel creditLabel()
	{
		return _creditLabel;
	}

	/**
	 * Return the Floater Label
	 * 
	 * @return The Floater Label
	 */

	public org.drip.state.identifier.FloaterLabel floaterLabel()
	{
		org.drip.analytics.cashflow.ComposableUnitPeriod cp = _lsCUP.get (0);

		return cp instanceof org.drip.analytics.cashflow.ComposableUnitFixedPeriod ? null :
			((org.drip.analytics.cashflow.ComposableUnitFloatingPeriod)
				cp).referenceIndexPeriod().floaterLabel();
	}

	/**
	 * Return the Funding Label
	 * 
	 * @return The Funding Label
	 */

	public org.drip.state.identifier.FundingLabel fundingLabel()
	{
		return org.drip.state.identifier.FundingLabel.Standard (_strPayCurrency);
	}

	/**
	 * Return the FX Label
	 * 
	 * @return The FX Label
	 */

	public org.drip.state.identifier.FXLabel fxLabel()
	{
		java.lang.String strCouponCurrency = couponCurrency();

		return _strPayCurrency.equalsIgnoreCase (strCouponCurrency) ? null :
			org.drip.state.identifier.FXLabel.Standard (_strPayCurrency + "/" + strCouponCurrency);
	}

	/**
	 * Compute the Convexity Adjustment for the Composable Periods that use Arithmetic Compounding using the
	 *  specified Value Date using the Market Data provided
	 * 
	 * @param iValueDate The Valuation Date
	 * @param csqc The Market Curves/Surface
	 * 
	 * @return The List of Convexity Adjustments
	 */

	public java.util.List<org.drip.analytics.output.ConvexityAdjustment> periodWiseConvexityAdjustment (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
	{
		java.util.List<org.drip.analytics.output.ConvexityAdjustment> lsConvAdj = new
			java.util.ArrayList<org.drip.analytics.output.ConvexityAdjustment>();

		if (null == csqc || iValueDate >= _iPayDate) {
			for (int i = 0; i < _lsCUP.size(); ++i)
				lsConvAdj.add (new org.drip.analytics.output.ConvexityAdjustment());

			return lsConvAdj;
		}

		org.drip.state.identifier.FXLabel fxLabel = fxLabel();

		org.drip.state.identifier.EntityCDSLabel creditLabel = creditLabel();

		org.drip.state.identifier.FloaterLabel floaterLabel = floaterLabel();

		org.drip.state.identifier.ForwardLabel forwardLabel = floaterLabel instanceof
			org.drip.state.identifier.ForwardLabel ? (org.drip.state.identifier.ForwardLabel) floaterLabel :
				null;

		org.drip.state.identifier.FundingLabel fundingLabel = fundingLabel();

		org.drip.state.volatility.VolatilityCurve vcFX = csqc.fxVolatility (fxLabel);

		org.drip.state.volatility.VolatilityCurve vcCredit = csqc.creditVolatility (creditLabel);

		org.drip.state.volatility.VolatilityCurve vcForward = csqc.forwardVolatility (forwardLabel);

		org.drip.state.volatility.VolatilityCurve vcFunding = csqc.fundingVolatility (fundingLabel);

		org.drip.function.definition.R1ToR1 r1ToR1CreditForwardCorr = csqc.creditForwardCorrelation (
			creditLabel,
			forwardLabel
		);

		org.drip.function.definition.R1ToR1 r1ToR1ForwardFundingCorr = csqc.forwardFundingCorrelation (
			forwardLabel,
			fundingLabel
		);

		org.drip.function.definition.R1ToR1 r1ToR1ForwardFXCorr = csqc.forwardFXCorrelation (
			forwardLabel,
			fxLabel
		);

		try {
			double dblCreditFundingConvexityAdjustment = java.lang.Math.exp (
				org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto (
					vcCredit,
					vcFunding,
					csqc.creditFundingCorrelation (
						creditLabel,
						fundingLabel
					),
					iValueDate,
					_iPayDate
				)
			);

			double dblCreditFXConvexityAdjustment = isFXMTM() ? java.lang.Math.exp (
				org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto (
					vcCredit,
					vcFX,
					csqc.creditFXCorrelation (
						creditLabel,
						fxLabel
					),
					iValueDate,
					_iPayDate
				)
			) : 1.;

			double dblFundingFXConvexityAdjustment = isFXMTM() ? java.lang.Math.exp (
				org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto (
					vcFunding,
					vcFX,
					csqc.fundingFXCorrelation (
						fundingLabel,
						fxLabel
					),
					iValueDate,
					_iPayDate
				)
			): 1.;

			for (org.drip.analytics.cashflow.ComposableUnitPeriod cup : _lsCUP) {
				org.drip.analytics.output.ConvexityAdjustment convAdj = new
					org.drip.analytics.output.ConvexityAdjustment();

				if (
					!convAdj.setCreditFunding (dblCreditFundingConvexityAdjustment) ||
					!convAdj.setCreditFX (dblCreditFXConvexityAdjustment) ||
					!convAdj.setFundingFX (dblFundingFXConvexityAdjustment)
				)
					return null;

				if (null != forwardLabel) {
					if (!(cup instanceof org.drip.analytics.cashflow.ComposableUnitFloatingPeriod))
						return null;

					int iFixingDate = ((org.drip.analytics.cashflow.ComposableUnitFloatingPeriod)
						cup).referenceIndexPeriod().fixingDate();

					if (
						!convAdj.setCreditForward (
							iValueDate < iFixingDate ? java.lang.Math.exp (
								org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto (
									vcCredit,
									vcForward,
									r1ToR1CreditForwardCorr,
									iValueDate,
									iFixingDate
								)
							) : 1.
						)
					)
						return null;

					if (
						!convAdj.setForwardFunding (
							iValueDate < iFixingDate ? java.lang.Math.exp (
								org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto (
									vcForward,
									vcFunding,
									r1ToR1ForwardFundingCorr,
									iValueDate,
									iFixingDate
								)
							) : 1.
						)
					)
						return null;

					if (
						!convAdj.setForwardFX (
							isFXMTM() && iValueDate < iFixingDate ? java.lang.Math.exp (
								org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto (
									vcForward,
									vcFX,
									r1ToR1ForwardFXCorr,
									iValueDate,
									iFixingDate
								)
							) : 1.
						)
					)
						return null;
				}

				lsConvAdj.add (convAdj);
			}

			return lsConvAdj;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Convexity Adjustment for the Composable Periods that use geometric Compounding using the
	 *  specified Value Date using the Market Data provided
	 * 
	 * @param iValueDate The Valuation Date
	 * @param csqc The Market Curves/Surface
	 * 
	 * @return The Convexity Adjustment
	 */

	public org.drip.analytics.output.ConvexityAdjustment terminalConvexityAdjustment (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
	{
		if (null == csqc || iValueDate >= _iPayDate)
			return new org.drip.analytics.output.ConvexityAdjustment();

		boolean bFXIsMTM = isFXMTM();

		org.drip.state.identifier.FXLabel fxLabel = fxLabel();

		org.drip.state.identifier.EntityCDSLabel creditLabel = creditLabel();

		org.drip.state.identifier.FloaterLabel floaterLabel = floaterLabel();

		org.drip.state.identifier.ForwardLabel forwardLabel = floaterLabel instanceof
			org.drip.state.identifier.ForwardLabel ? (org.drip.state.identifier.ForwardLabel) floaterLabel :
				null;

		org.drip.state.identifier.FundingLabel fundingLabel = fundingLabel();

		org.drip.state.volatility.VolatilityCurve vcFX = csqc.fxVolatility (fxLabel);

		org.drip.state.volatility.VolatilityCurve vcCredit = csqc.creditVolatility (creditLabel);

		org.drip.state.volatility.VolatilityCurve vcForward = csqc.forwardVolatility (forwardLabel);

		org.drip.state.volatility.VolatilityCurve vcFunding = csqc.fundingVolatility (fundingLabel);

		org.drip.analytics.output.ConvexityAdjustment convAdj = new
			org.drip.analytics.output.ConvexityAdjustment();

		try {
			if (
				!convAdj.setCreditFunding (
					java.lang.Math.exp (
						org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto (
							vcCredit,
							vcFunding,
							csqc.creditFundingCorrelation (
								creditLabel,
								fundingLabel
							), iValueDate, _iPayDate
						)
					)
				)
			)
				return null;

			if (
				bFXIsMTM && !convAdj.setCreditFX (
					java.lang.Math.exp (
						org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto (
							vcCredit,
							vcFX,
							csqc.creditFXCorrelation (
								creditLabel,
								fxLabel
							),
							iValueDate,
							_iPayDate
						)
					)
				)
			)
				return null;

			if (
				bFXIsMTM && !convAdj.setFundingFX (
					java.lang.Math.exp (
						org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto (
							vcFunding,
							vcFX,
							csqc.fundingFXCorrelation (
								fundingLabel,
								fxLabel
							),
							iValueDate,
							_iPayDate
						)
					)
				)
			)
				return null;

			if (null == forwardLabel) return convAdj;

			org.drip.analytics.cashflow.ComposableUnitPeriod cup = _lsCUP.get (0);

			if (!(cup instanceof org.drip.analytics.cashflow.ComposableUnitFloatingPeriod)) return null;

			int iFixingDate = ((org.drip.analytics.cashflow.ComposableUnitFloatingPeriod)
				cup).referenceIndexPeriod().fixingDate();

			if (iValueDate < iFixingDate) {
				if (
					!convAdj.setCreditForward (
						java.lang.Math.exp (
							org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto (
								vcCredit,
								vcForward,
								csqc.creditForwardCorrelation (
									creditLabel,
									forwardLabel
								),
								iValueDate,
								iFixingDate
							)
						)
					)
				)
					return null;

				if (
					!convAdj.setForwardFunding (
						java.lang.Math.exp (
							org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto (
								vcForward,
								vcFunding,
								csqc.forwardFundingCorrelation (
									forwardLabel,
									fundingLabel
								),
								iValueDate,
								iFixingDate
							)
						)
					)
				)
					return null;

				if (
					bFXIsMTM && !convAdj.setForwardFX (
						java.lang.Math.exp (
							org.drip.analytics.support.OptionHelper.IntegratedCrossVolQuanto (
								vcForward,
								vcFX,
								csqc.forwardFXCorrelation (
									forwardLabel,
									fxLabel
								),
								iValueDate,
								iFixingDate
							)
						)
					)
				)
					return null;
			}

			return convAdj;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Unit Period Convexity Measures
	 * 
	 * @param iValueDate Valuation Date
	 * @param csqc The Market Curve Surface/Quote Set
	 * 
	 * @return The Unit Period Convexity Measures
	 */

	public java.util.List<org.drip.analytics.output.UnitPeriodConvexityMetrics> unitPeriodConvexityMetrics (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
	{
		java.util.List<org.drip.analytics.output.UnitPeriodConvexityMetrics> lsUPCM = new
			java.util.ArrayList<org.drip.analytics.output.UnitPeriodConvexityMetrics>();

		int iNumPeriodUnit = _lsCUP.size();

		try {
			if (org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_ARITHMETIC ==
				_iAccrualCompoundingRule) {
				java.util.List<org.drip.analytics.output.ConvexityAdjustment> lsConvAdj =
					periodWiseConvexityAdjustment (
						iValueDate,
						csqc
					);

				if (null == lsConvAdj || iNumPeriodUnit != lsConvAdj.size()) return null;

				for (int i = 0; i < iNumPeriodUnit; ++i) {
					org.drip.analytics.cashflow.ComposableUnitPeriod cup = _lsCUP.get (i);

					lsUPCM.add (
						new org.drip.analytics.output.UnitPeriodConvexityMetrics (
							cup.startDate(),
							cup.endDate(),
							lsConvAdj.get (i)
						)
					);
				}
			} else if (org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
				== _iAccrualCompoundingRule)
				lsUPCM.add (
					new org.drip.analytics.output.UnitPeriodConvexityMetrics (
						startDate(),
						endDate(),
						terminalConvexityAdjustment (
							iValueDate,
							csqc
						)
					)
				);

			return lsUPCM;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Full Period Coupon Measures
	 * 
	 * @param iValueDate Valuation Date
	 * @param csqc The Market Curve Surface/Quote Set
	 * 
	 * @return The Full Period Coupon Measures
	 */

	public org.drip.analytics.output.CompositePeriodCouponMetrics couponMetrics (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (iValueDate)) return null;

		java.util.List<org.drip.analytics.output.UnitPeriodMetrics> lsUPM = new
			java.util.ArrayList<org.drip.analytics.output.UnitPeriodMetrics>();

		int iNumPeriodUnit = _lsCUP.size();

		double dblUnitAccrual = 1.;

		try {
			if (org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_ARITHMETIC ==
				_iAccrualCompoundingRule) {
				java.util.List<org.drip.analytics.output.ConvexityAdjustment> lsConvAdj =
					periodWiseConvexityAdjustment (
						iValueDate,
						csqc
					);

				if (null == lsConvAdj || iNumPeriodUnit != lsConvAdj.size()) return null;

				for (int i = 0; i < iNumPeriodUnit; ++i) {
					org.drip.analytics.cashflow.ComposableUnitPeriod cup = _lsCUP.get (i);

					lsUPM.add (
						new org.drip.analytics.output.UnitPeriodMetrics (
							cup.startDate(),
							cup.endDate(),
							cup.fullCouponDCF(),
							cup.fullCouponRate (csqc),
							lsConvAdj.get (i)
						)
					);
				}
			} else if (org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
				== _iAccrualCompoundingRule) {
				double dblDCF = 0.;

				for (int i = 0; i < iNumPeriodUnit; ++i) {
					org.drip.analytics.cashflow.ComposableUnitPeriod cup = _lsCUP.get (i);

					double dblPeriodDCF = cup.fullCouponDCF();

					dblDCF += dblPeriodDCF;

					dblUnitAccrual *= (1. + cup.fullCouponRate (csqc) * dblPeriodDCF);
				}

				lsUPM.add (
					new org.drip.analytics.output.UnitPeriodMetrics (
						startDate(),
						endDate(),
						dblDCF,
						(dblUnitAccrual - 1.) / dblDCF,
						terminalConvexityAdjustment (
							iValueDate,
							csqc
						)
					)
				);
			}

			return org.drip.analytics.output.CompositePeriodCouponMetrics.Create (lsUPM);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Coupon Accrual DCF to the specified Accrual End Date
	 * 
	 * @param iValueDate The Valuation Date
	 * 
	 * @return The Coupon Accrual DCF to the specified Accrual End Date
	 * 
	 * @throws java.lang.Exception Thrown if the Accrual DCF cannot be calculated
	 */

	public double accrualDCF (
		final int iValueDate)
		throws java.lang.Exception
	{
		if (!contains (iValueDate)) return 0.;

		int iNumPeriodUnit = _lsCUP.size();

		double dblAccrualDCF = 0.;

		for (int i = 0; i < iNumPeriodUnit; ++i) {
			org.drip.analytics.cashflow.ComposableUnitPeriod cup = _lsCUP.get (i);

			int iDateLocation = cup.dateLocation (iValueDate);

			if (org.drip.analytics.cashflow.ComposableUnitFixedPeriod.NODE_INSIDE_SEGMENT == iDateLocation)
				dblAccrualDCF += cup.accrualDCF (iValueDate);
			else if (org.drip.analytics.cashflow.ComposableUnitFixedPeriod.NODE_RIGHT_OF_SEGMENT ==
				iDateLocation)
				dblAccrualDCF += cup.fullCouponDCF();
		}

		return dblAccrualDCF;
	}

	/**
	 * Compute the Full Coupon DCF
	 * 
	 * @return The Full Coupon DCF
	 * 
	 * @throws java.lang.Exception Thrown if the Full Coupon DCF cannot be calculated
	 */

	public double couponDCF()
		throws java.lang.Exception
	{
		int iNumPeriodUnit = _lsCUP.size();

		double dblAccrualDCF = 0.;

		for (int i = 0; i < iNumPeriodUnit; ++i)
			dblAccrualDCF += _lsCUP.get (i).fullCouponDCF();

		return dblAccrualDCF;
	}

	/**
	 * Compute the Coupon Accrual Measures to the specified Accrual End Date
	 * 
	 * @param iValueDate The Valuation Date
	 * @param csqc The Market Curve Surface/Quote Set
	 * 
	 * @return The Coupon Accrual Measures to the specified Accrual End Date
	 */

	public org.drip.analytics.output.CompositePeriodAccrualMetrics accrualMetrics (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
	{
		try {
			if (!contains (iValueDate)) return null;

			java.util.List<org.drip.analytics.output.UnitPeriodMetrics> lsUPM = new
				java.util.ArrayList<org.drip.analytics.output.UnitPeriodMetrics>();

			int iNumPeriodUnit = _lsCUP.size();

			int iResetDate = java.lang.Integer.MIN_VALUE;

			if (org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_ARITHMETIC ==
				_iAccrualCompoundingRule) {
				java.util.List<org.drip.analytics.output.ConvexityAdjustment> lsConvAdj =
					periodWiseConvexityAdjustment (
						iValueDate,
						csqc
					);

				if (null == lsConvAdj || iNumPeriodUnit != lsConvAdj.size()) return null;

				for (int i = 0; i < iNumPeriodUnit; ++i) {
					org.drip.analytics.cashflow.ComposableUnitPeriod cup = _lsCUP.get (i);

					int iDateLocation = cup.dateLocation (iValueDate);

					if (org.drip.analytics.cashflow.ComposableUnitFixedPeriod.NODE_INSIDE_SEGMENT ==
						iDateLocation) {
						if (cup instanceof org.drip.analytics.cashflow.ComposableUnitFloatingPeriod)
							iResetDate = ((org.drip.analytics.cashflow.ComposableUnitFloatingPeriod)
								cup).referenceIndexPeriod().fixingDate();

						lsUPM.add (
							new org.drip.analytics.output.UnitPeriodMetrics (
								cup.startDate(),
								iValueDate,
								cup.accrualDCF (iValueDate),
								cup.fullCouponRate (csqc),
								lsConvAdj.get (i)
							)
						);
					} else if (org.drip.analytics.cashflow.ComposableUnitFixedPeriod.NODE_RIGHT_OF_SEGMENT ==
						iDateLocation) {
						if (cup instanceof org.drip.analytics.cashflow.ComposableUnitFloatingPeriod)
							iResetDate = ((org.drip.analytics.cashflow.ComposableUnitFloatingPeriod)
								cup).referenceIndexPeriod().fixingDate();

						lsUPM.add (
							new org.drip.analytics.output.UnitPeriodMetrics (
								cup.startDate(),
								cup.endDate(),
								cup.fullCouponDCF(),
								cup.fullCouponRate (csqc),
								lsConvAdj.get (i)
							)
						);
					}
				}
			} else if (org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC
				== _iAccrualCompoundingRule) {
				double dblAccrualDCF = 0.;
				double dblUnitAccrual = 1.;

				for (int i = 0; i < iNumPeriodUnit; ++i) {
					org.drip.analytics.cashflow.ComposableUnitPeriod cup = _lsCUP.get (i);

					int iDateLocation = cup.dateLocation (iValueDate);

					if (org.drip.analytics.cashflow.ComposableUnitFixedPeriod.NODE_INSIDE_SEGMENT ==
						iDateLocation) {
						double dblPeriodAccrualDCF = cup.accrualDCF (iValueDate);

						dblAccrualDCF += dblPeriodAccrualDCF;

						dblUnitAccrual *= (1. + cup.fullCouponRate (csqc) * dblPeriodAccrualDCF);

						if (cup instanceof org.drip.analytics.cashflow.ComposableUnitFloatingPeriod)
							iResetDate = ((org.drip.analytics.cashflow.ComposableUnitFloatingPeriod)
								cup).referenceIndexPeriod().fixingDate();
					} else if (org.drip.analytics.cashflow.ComposableUnitFixedPeriod.NODE_RIGHT_OF_SEGMENT ==
						iDateLocation) {
						double dblPeriodDCF = cup.fullCouponDCF();

						dblAccrualDCF += dblPeriodDCF;

						dblUnitAccrual *= (1. + cup.fullCouponRate (csqc) * dblPeriodDCF);

						if (cup instanceof org.drip.analytics.cashflow.ComposableUnitFloatingPeriod)
							iResetDate = ((org.drip.analytics.cashflow.ComposableUnitFloatingPeriod)
								cup).referenceIndexPeriod().fixingDate();
					}
				}

				if (0. < dblAccrualDCF)
					lsUPM.add (
						new org.drip.analytics.output.UnitPeriodMetrics (
							startDate(),
							iValueDate,
							dblAccrualDCF,
							(dblUnitAccrual - 1.) / dblAccrualDCF, terminalConvexityAdjustment (
								iValueDate,
								csqc
							)
						)
					);
			}

			return 0 == lsUPM.size() ? null : org.drip.analytics.output.CompositePeriodAccrualMetrics.Create (
				iResetDate,
				lsUPM
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a List of Loss Period Measures
	 * 
	 * @param creditComponent Component for which the Measures are to be generated
	 * @param valuationParameters Valuation Parameters from which the Periods are generated
	 * @param cpp Pricer Parameters that control the Generation Characteristics
	 * @param iWorkoutDate Date representing the Absolute End of all the generated Periods
	 * @param csqc Market Parameters
	 *  
	 * @return The Generated Loss Quadrature Metrics
	 */

	public java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics> lossMetrics (
		final org.drip.product.definition.CreditComponent creditComponent,
		final org.drip.param.valuation.ValuationParams valuationParameters,
		final org.drip.param.pricer.CreditPricerParams cpp,
		final int iWorkoutDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
	{
		if (
			null == creditComponent || null == csqc.creditState (creditComponent.creditLabel()) ||
			null == valuationParameters ||
			null == cpp ||
			null == csqc ||
			startDate() > iWorkoutDate
		)
			return null;

		org.drip.state.discount.MergedDiscountForwardCurve dc = csqc.fundingState
			(org.drip.state.identifier.FundingLabel.Standard (_strPayCurrency));

		if (null == dc) return null;

		int iEndDate = endDate();

		int iDiscretizationScheme = cpp.discretizationScheme();

		int iPeriodEndDate = iEndDate < iWorkoutDate ? iEndDate : iWorkoutDate;
		java.util.List<org.drip.analytics.cashflow.LossQuadratureMetrics> lsLQM = null;

		if (
			org.drip.param.pricer.CreditPricerParams.PERIOD_DISCRETIZATION_DAY_STEP == iDiscretizationScheme
			&& (
				null == (
					lsLQM = org.drip.analytics.support.LossQuadratureGenerator.GenerateDayStepLossPeriods (
						creditComponent,
						valuationParameters,
						this,
						iPeriodEndDate,
						cpp.unitSize(),
						csqc
					)
				) || 0 == lsLQM.size()
			)
		)
			return null;

		if (
			org.drip.param.pricer.CreditPricerParams.PERIOD_DISCRETIZATION_PERIOD_STEP ==
			iDiscretizationScheme && (
				null == (
					lsLQM = org.drip.analytics.support.LossQuadratureGenerator.GeneratePeriodUnitLossPeriods (
						creditComponent,
						valuationParameters,
						this,
						iPeriodEndDate,
						cpp.unitSize(),
						csqc
					)
				) || 0 == lsLQM.size()
			)
		)
			return null;

		if (
			org.drip.param.pricer.CreditPricerParams.PERIOD_DISCRETIZATION_FULL_COUPON ==
			iDiscretizationScheme && (
				null == (
					lsLQM = org.drip.analytics.support.LossQuadratureGenerator.GenerateWholeLossPeriods (
						creditComponent,
						valuationParameters,
						this,
						iPeriodEndDate,
						csqc
					)
				) || 0 == lsLQM.size()
			)
		)
			return null;

		return lsLQM;
	}

	/**
	 * Generate the Forward Predictor/Response Constraint
	 * 
	 * @param iValueDate The Valuation Date
	 * @param csqc The Market Curve Surface/Quote Set
	 * @param pqs Product Quote Set
	 * 
	 * @return The Forward Predictor/Response Constraint
	 */

	public org.drip.state.estimator.PredictorResponseWeightConstraint forwardPRWC (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == pqs) return null;

		double dblDF = java.lang.Double.NaN;
		double dblFX = java.lang.Double.NaN;
		double dblBasis = java.lang.Double.NaN;
		double dblAccrued = java.lang.Double.NaN;
		double dblBaseRate = java.lang.Double.NaN;
		double dblNotional = java.lang.Double.NaN;
		double dblSurvival = java.lang.Double.NaN;

		org.drip.product.calib.CompositePeriodQuoteSet cpqs = periodQuoteSet (
			pqs,
			csqc
		);

		try {
			dblDF = df (csqc);

			dblFX = fx (csqc);

			dblSurvival = survival (csqc);

			int iEndDate = endDate();

			dblBasis = cpqs.containsBasis() ? cpqs.basis() : 0.;

			dblBaseRate = cpqs.containsBaseRate() ? cpqs.baseRate() : 0.;

			dblNotional = notional (iEndDate) * couponFactor (iEndDate);

			dblAccrued = dblNotional * dblFX * accrualDCF (iValueDate) * (dblBaseRate + dblBasis);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.output.CompositePeriodCouponMetrics cpm = couponMetrics (
			iValueDate,
			csqc
		);

		if (null == cpm) return null;

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		org.drip.state.identifier.FloaterLabel floaterLabel = floaterLabel();

		org.drip.state.identifier.ForwardLabel forwardLabel = floaterLabel instanceof
			org.drip.state.identifier.ForwardLabel ? (org.drip.state.identifier.ForwardLabel) floaterLabel :
				null;

		if (null == forwardLabel) {
			for (org.drip.analytics.output.UnitPeriodMetrics upm : cpm.unitMetrics()) {
				if (!prwc.updateValue (-1. * dblNotional * dblFX * upm.dcf() * (dblBaseRate + dblBasis) *
					dblSurvival * dblDF * upm.convAdj().cumulative()))
					return null;
			}

			if (!prwc.updateValue (dblAccrued)) return null;
		} else if (!forwardLabel.match (pqs.forwardLabel())) {
			java.util.List<org.drip.analytics.output.UnitPeriodMetrics> lsUPM = cpm.unitMetrics();

			for (int i = 0; i < lsUPM.size(); ++i) {
				org.drip.analytics.output.UnitPeriodMetrics upm = lsUPM.get (i);

				try {
					if (!prwc.updateValue (-1. * dblNotional * dblFX * upm.dcf() * (_lsCUP.get (i).baseRate
						(csqc) + dblBasis) * dblSurvival * dblDF * upm.convAdj().cumulative()))
						return null;
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return null;
				}
			}

			if (!prwc.updateValue (dblAccrued)) return null;
		} else {
			for (org.drip.analytics.output.UnitPeriodMetrics upm : cpm.unitMetrics()) {
				int iDateAnchor = upm.endDate();

				if (cpqs.containsBaseRate()) {
					if (
						!prwc.addPredictorResponseWeight (
							iDateAnchor,
							1.
						)
					)
						return null;

					if (
						!prwc.addDResponseWeightDManifestMeasure (
							"PV",
							iDateAnchor,
							1.
						)
					) return null;
				} else {
					double dblForwardLoading = dblNotional * dblFX * upm.dcf() * dblSurvival * dblDF *
						upm.convAdj().cumulative();

					if (
						!prwc.addPredictorResponseWeight (
							iDateAnchor,
							dblForwardLoading
						)
					)
						return null;

					if (
						!prwc.addDResponseWeightDManifestMeasure (
							"PV",
							iDateAnchor,
							dblForwardLoading
						)
					)
						return null;

					if (!prwc.updateValue (-1. * dblForwardLoading * dblBasis)) return null;
				}
			}

			if (!prwc.updateValue (cpqs.containsBaseRate() ? dblBaseRate : dblAccrued)) return null;
		}

		if (
			!prwc.updateDValueDManifestMeasure (
				"PV",
				1.
			)
		)
			return null;

		return prwc;
	}

	/**
	 * Generate the Funding Predictor/Response Constraint
	 * 
	 * @param iValueDate The Valuation Date
	 * @param csqc The Market Curve Surface/Quote Set
	 * @param pqs Product Quote Set
	 * 
	 * @return The Funding Predictor/Response Constraint
	 */

	public org.drip.state.estimator.PredictorResponseWeightConstraint fundingPRWC (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == pqs) return null;

		double dblDF = java.lang.Double.NaN;
		double dblFX = java.lang.Double.NaN;
		double dblBasis = java.lang.Double.NaN;
		double dblAccrued = java.lang.Double.NaN;
		double dblBaseRate = java.lang.Double.NaN;
		double dblNotional = java.lang.Double.NaN;
		double dblSurvival = java.lang.Double.NaN;

		org.drip.product.calib.CompositePeriodQuoteSet cpqs = periodQuoteSet (
			pqs,
			csqc
		);

		try {
			dblFX = fx (csqc);

			dblSurvival = survival (csqc);

			dblBasis = cpqs.containsBasis() ? cpqs.basis() : 0.;

			dblBaseRate = cpqs.containsBaseRate() ? cpqs.baseRate() : 0.;

			dblNotional = notional (_iPayDate) * couponFactor (_iPayDate);

			dblAccrued = dblNotional * dblFX * accrualDCF (iValueDate) * (dblBaseRate + dblBasis);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.output.CompositePeriodCouponMetrics cpcm = couponMetrics (
			iValueDate,
			csqc
		);

		if (null == cpcm) return null;

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		org.drip.state.identifier.FundingLabel fundingLabel = fundingLabel();

		if (!fundingLabel.match (pqs.fundingLabel())) {
			try {
				dblDF = df (csqc);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			for (org.drip.analytics.output.UnitPeriodMetrics upm : cpcm.unitMetrics()) {
				if (!prwc.updateValue (-1. * dblNotional * dblFX * upm.dcf() * (dblBaseRate + dblBasis) *
					dblSurvival * dblDF * upm.convAdj().cumulative()))
					return null;
			}
		} else {
			for (org.drip.analytics.output.UnitPeriodMetrics upm : cpcm.unitMetrics()) {
				double dblFundingLoading = dblNotional * dblFX * upm.dcf() * (dblBaseRate + dblBasis) *
					dblSurvival * upm.convAdj().cumulative();

				if (
					!prwc.addPredictorResponseWeight (
						_iPayDate,
						dblFundingLoading
					)
				)
					return null;

				if (
					!prwc.addDResponseWeightDManifestMeasure (
						"PV",
						_iPayDate,
						dblFundingLoading
					)
				)
					return null;
			}
		}

		if (!prwc.updateValue (dblAccrued)) return null;

		if (
			!prwc.updateDValueDManifestMeasure (
				"PV",
				1.
			)
		)
			return null;

		return prwc;
	}

	/**
	 * Generate the Merged Forward/Funding Predictor/Response Constraint
	 * 
	 * @param iValueDate The Valuation Date
	 * @param csqc The Market Curve Surface/Quote Set
	 * @param pqs Product Quote Set
	 * 
	 * @return The Merged Forward/Funding Predictor/Response Constraint
	 */

	public org.drip.state.estimator.PredictorResponseWeightConstraint forwardFundingPRWC (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == pqs) return null;

		org.drip.state.identifier.FloaterLabel floaterLabel = floaterLabel();

		org.drip.state.identifier.ForwardLabel forwardLabel = floaterLabel instanceof
			org.drip.state.identifier.ForwardLabel ? (org.drip.state.identifier.ForwardLabel) floaterLabel :
				null;

		if (null == forwardLabel || !fundingLabel().match (pqs.fundingLabel()))
			return fundingPRWC (
				iValueDate,
				csqc,
				pqs
			);

		double dblFX = java.lang.Double.NaN;
		double dblAccrued = java.lang.Double.NaN;
		double dblNotional = java.lang.Double.NaN;
		double dblSurvival = java.lang.Double.NaN;

		org.drip.product.calib.CompositePeriodQuoteSet cpqs = periodQuoteSet (
			pqs,
			csqc
		);

		try {
			dblFX = fx (csqc);

			dblSurvival = survival (csqc);

			dblNotional = notional (_iPayDate) * couponFactor (_iPayDate);

			dblAccrued = accrualDCF (iValueDate) * cpqs.basis() * dblNotional * dblFX;
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		if (cpqs.containsBaseRate()) {
			int iEndDate = endDate();

			int iStartDate = startDate();

			org.drip.analytics.cashflow.ComposableUnitPeriod cup = _lsCUP.get (0);

			try {
				double dblForwardDF = 1. / (
					1. + org.drip.analytics.daycount.Convention.YearFraction (
						iStartDate,
						iEndDate,
						cup.couponDC(),
						false,
						null,
						cup.calendar()
					) * cpqs.baseRate()
				);

				if (
					!prwc.addPredictorResponseWeight (
						iStartDate,
						dblNotional * dblForwardDF
					)
				)
					return null;

				if (
					!prwc.addDResponseWeightDManifestMeasure (
						"PV",
						iStartDate,
						dblNotional * dblForwardDF
					)
				)
					return null;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (
				!prwc.addPredictorResponseWeight (
					iEndDate,
					-1. * dblNotional
				)
			)
				return null;

			if (
				!prwc.addDResponseWeightDManifestMeasure (
					"PV",
					iEndDate,
					-1. * dblNotional
				)
			)
				return null;
		} else {
			java.util.List<org.drip.analytics.output.UnitPeriodConvexityMetrics> lsUPCM =
				unitPeriodConvexityMetrics (
					iValueDate,
					csqc
				);

			if (null == lsUPCM || 0 == lsUPCM.size()) return null;

			for (org.drip.analytics.output.UnitPeriodConvexityMetrics upcm : lsUPCM) {
				double dblFundingLoading = dblNotional * dblFX * dblSurvival * upcm.convAdj().cumulative();

				int iStartDate = upcm.startDate();

				int iEndDate = upcm.endDate();

				if (
					!prwc.addPredictorResponseWeight (
						iStartDate,
						dblFundingLoading
					)
				)
					return null;

				if (
					!prwc.addPredictorResponseWeight (
						iEndDate,
						-1. * dblFundingLoading
					)
				)
					return null;

				if (
					!prwc.addDResponseWeightDManifestMeasure (
						"PV",
						iStartDate,
						dblFundingLoading
					)
				)
					return null;

				if (
					!prwc.addDResponseWeightDManifestMeasure (
						"PV",
						iEndDate,
						-1. * dblFundingLoading
					)
				)
					return null;
			}

			if (!prwc.updateValue (dblAccrued)) return null;
		}

		if (
			!prwc.updateDValueDManifestMeasure (
				"PV",
				1.
			)
		)
			return null;

		if (!prwc.addMergeLabel (forwardLabel)) return null;

		return prwc;
	}

	/**
	 * Generate the FX Predictor/Response Constraint
	 * 
	 * @param iValueDate The Valuation Date
	 * @param csqc The Market Curve Surface/Quote Set
	 * @param pqs Product Quote Set
	 * 
	 * @return The FX Predictor/Response Constraint
	 */

	public org.drip.state.estimator.PredictorResponseWeightConstraint fxPRWC (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == pqs) return null;

		int iEndDate = endDate();

		if (iValueDate > iEndDate) return null;

		org.drip.state.identifier.FXLabel fxLabel = fxLabel();

		if (null == fxLabel || !fxLabel.match (pqs.fxLabel())) return null;

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		if (
			!prwc.addPredictorResponseWeight (
				iEndDate,
				1.
			)
		)
			return null;

		if (
			!prwc.addDResponseWeightDManifestMeasure (
				"Outright",
				iEndDate,
				1.
			)
		)
			return null;

		return prwc;
	}

	/**
	 * Generate the Volatility Predictor/Response Constraint
	 * 
	 * @param iValueDate The Valuation Date
	 * @param csqc The Market Curve Surface/Quote Set
	 * @param pqs Product Quote Set
	 * 
	 * @return The Volatility Predictor/Response Constraint
	 */

	public org.drip.state.estimator.PredictorResponseWeightConstraint volatilityPRWC (
		final int iValueDate,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final org.drip.product.calib.ProductQuoteSet pqs)
	{
		if (null == pqs) return null;

		int iEndDate = endDate();

		if (iValueDate > iEndDate) return null;

		org.drip.state.identifier.VolatilityLabel volLabel = pqs.volatilityLabel();

		if (null == volLabel) return null;

		org.drip.state.identifier.FloaterLabel floaterLabel = floaterLabel();

		org.drip.state.identifier.ForwardLabel forwardLabel = floaterLabel instanceof
			org.drip.state.identifier.ForwardLabel ? (org.drip.state.identifier.ForwardLabel) floaterLabel :
				null;

		if (null == forwardLabel || !forwardLabel.match (volLabel.underlyingLatentState())) return null;

		org.drip.state.estimator.PredictorResponseWeightConstraint prwc = new
			org.drip.state.estimator.PredictorResponseWeightConstraint();

		if (
			!prwc.addPredictorResponseWeight (
				iEndDate,
				1.
			)
		)
			return null;

		if (
			!prwc.addDResponseWeightDManifestMeasure (
				"OptionPV",
				iEndDate,
				1.
			)
		)
			return null;

		return prwc;
	}

	/**
	 * Retrieve the Period Calibration Quotes from the specified product quote set
	 * 
	 * @param pqs The Product Quote Set
	 * @param csqc The Market Curve Surface/Quote Set
	 * 
	 * @return The Composed Period Quote Set
	 */

	public abstract org.drip.product.calib.CompositePeriodQuoteSet periodQuoteSet (
		final org.drip.product.calib.ProductQuoteSet pqs,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc);

	/**
	 * Retrieve the Period Calibration Basis Quote from the specified product quote set
	 * 
	 * @param pqs The Product Quote Set
	 * 
	 * @return The Period Calibration Basis Quote
	 */

	public abstract double basisQuote (
		final org.drip.product.calib.ProductQuoteSet pqs);
}
