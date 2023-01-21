
package org.drip.product.params;

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
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>BondStream</i> is the place-holder for the bond period generation parameters. Contains the bond date
 * adjustment parameters for period start/end, period accrual start/end, effective, maturity, pay and reset,
 * first coupon date, and interest accrual start date. It exports serialization into and de-serialization out
 * of byte arrays.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md">Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option AssetClasses</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/params/README.md">Fixed Income Product Customization Parameters</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BondStream extends org.drip.product.rates.Stream {
	private java.lang.String _strMaturityType = "";
	private int _iFinalMaturityDate = java.lang.Integer.MIN_VALUE;

	/**
	 * Construct an Instance of BondStream from the First/Penultimate Dates using the specified Parameters
	 * 
	 * @param iMaturityDate Maturity Date
	 * @param iEffectiveDate Effective Date
	 * @param iFinalMaturityDate Final Maturity Date
	 * @param iFirstCouponDate First Coupon Date
	 * @param iPenultimateCouponDate Penultimate Coupon Date
	 * @param iFreq Coupon Frequency
	 * @param dblCoupon Coupon Rate
	 * @param strCouponDC Coupon day count convention
	 * @param strAccrualDC Accrual day count convention
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapReset Reset Date Adjustment Parameters
	 * @param dapMaturity Maturity Date Adjustment Parameters
	 * @param dapEffective Effective Date Adjustment Parameters
	 * @param dapPeriodEnd Period End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual Date Adjustment Parameters
	 * @param dapPeriodStart Period Start Date Adjustment Parameters
	 * @param dapAccrualStart Accrual Start  Date Adjustment Parameters
	 * @param strMaturityType Maturity Type
	 * @param bPeriodsFromForward Generate Periods forward (True) or Backward (False)
	 * @param strCalendar Optional Holiday Calendar for Accrual Calculations
	 * @param strCurrency Coupon Currency
	 * @param floaterLabel The Floater Label
	 * @param creditLabel The Credit Label
	 * 
	 * @return The BondStream Instance
	 */

	public static final BondStream FromFirstPenultimateCouponDate (
		final int iMaturityDate,
		final int iEffectiveDate,
		final int iFinalMaturityDate,
		final int iFirstCouponDate,
		final int iPenultimateCouponDate,
		final int iFreq,
		final double dblCoupon,
		final java.lang.String strCouponDC,
		final java.lang.String strAccrualDC,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final org.drip.analytics.daycount.DateAdjustParams dapReset,
		final org.drip.analytics.daycount.DateAdjustParams dapMaturity,
		final org.drip.analytics.daycount.DateAdjustParams dapEffective,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodStart,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualStart,
		final java.lang.String strMaturityType,
		final boolean bPeriodsFromForward,
		final java.lang.String strCalendar,
		final java.lang.String strCurrency,
		final org.drip.state.identifier.FloaterLabel floaterLabel,
		final org.drip.state.identifier.EntityCDSLabel creditLabel)
	{
		java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCouponPeriod = null == floaterLabel ?
			org.drip.product.creator.StreamBuilder.FirstPenultimateDateFixedStream (
				iEffectiveDate,
				iMaturityDate,
				iFirstCouponDate,
				iPenultimateCouponDate,
				iFreq,
				dblCoupon,
				strCouponDC,
				strAccrualDC,
				dapPay,
				dapMaturity,
				dapAccrualEnd,
				strCurrency,
				creditLabel
			) : org.drip.product.creator.StreamBuilder.FirstPenultimateDateFloatStream (
				iEffectiveDate,
				iMaturityDate,
				iFirstCouponDate,
				iPenultimateCouponDate,
				iFreq,
				dblCoupon,
				dapPay,
				dapMaturity,
				dapAccrualEnd,
				floaterLabel,
				creditLabel
			);

		try {
			return new BondStream (
				lsCouponPeriod,
				iFinalMaturityDate,
				strMaturityType
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct and Instance of BondStream from the specified Parameters
	 * 
	 * @param iMaturityDate Maturity Date
	 * @param iEffectiveDate Effective Date
	 * @param iFinalMaturityDate Final Maturity Date
	 * @param iFirstCouponDate First Coupon Date
	 * @param iInterestAccrualStartDate Interest Accrual Start Date
	 * @param iFreq Coupon Frequency
	 * @param dblCoupon Coupon Rate
	 * @param strCouponDC Coupon day count convention
	 * @param strAccrualDC Accrual day count convention
	 * @param dapPay Pay Date Adjustment Parameters
	 * @param dapReset Reset Date Adjustment Parameters
	 * @param dapMaturity Maturity Date Adjustment Parameters
	 * @param dapEffective Effective Date Adjustment Parameters
	 * @param dapPeriodEnd Period End Date Adjustment Parameters
	 * @param dapAccrualEnd Accrual Date Adjustment Parameters
	 * @param dapPeriodStart Period Start Date Adjustment Parameters
	 * @param dapAccrualStart Accrual Start  Date Adjustment Parameters
	 * @param strMaturityType Maturity Type
	 * @param bPeriodsFromForward Generate Periods forward (True) or Backward (False)
	 * @param strCalendar Optional Holiday Calendar for accrual calculations
	 * @param strCurrency Coupon Currency
	 * @param floaterLabel The Floater Label
	 * @param creditLabel The Credit Label
	 * 
	 * @return The BondStream Instance
	 */

	public static final BondStream Create (
		final int iMaturityDate,
		final int iEffectiveDate,
		final int iFinalMaturityDate,
		final int iFirstCouponDate,
		final int iInterestAccrualStartDate,
		final int iFreq,
		final double dblCoupon,
		final java.lang.String strCouponDC,
		final java.lang.String strAccrualDC,
		final org.drip.analytics.daycount.DateAdjustParams dapPay,
		final org.drip.analytics.daycount.DateAdjustParams dapReset,
		final org.drip.analytics.daycount.DateAdjustParams dapMaturity,
		final org.drip.analytics.daycount.DateAdjustParams dapEffective,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualEnd,
		final org.drip.analytics.daycount.DateAdjustParams dapPeriodStart,
		final org.drip.analytics.daycount.DateAdjustParams dapAccrualStart,
		final java.lang.String strMaturityType,
		final boolean bPeriodsFromForward,
		final java.lang.String strCalendar,
		final java.lang.String strCurrency,
		final org.drip.state.identifier.FloaterLabel floaterLabel,
		final org.drip.state.identifier.EntityCDSLabel creditLabel)
	{
		boolean bCouponEOMAdj = null == strCouponDC ? false : strCouponDC.toUpperCase().contains ("EOM");

		int iCouponDCIndex = null == strCouponDC ? -1 : strCouponDC.indexOf (" NON");

		java.lang.String strCouponDCAdj = -1 != iCouponDCIndex ? strCouponDC.substring (0, iCouponDCIndex) :
			strCouponDC;

		boolean bAccrualEOMAdj = null == strAccrualDC ? false : strAccrualDC.toUpperCase().contains ("EOM");

		int iAccrualDCIndex = null == strAccrualDC ? -1 : strAccrualDC.indexOf (" NON");

		java.lang.String strAccrualDCAdj = -1 != iAccrualDCIndex ? strAccrualDC.substring (0,
			iAccrualDCIndex) : strAccrualDC;

		org.drip.analytics.date.JulianDate dtEffective = new org.drip.analytics.date.JulianDate
			(iEffectiveDate);

		org.drip.analytics.date.JulianDate dtMaturity = new org.drip.analytics.date.JulianDate
			(iMaturityDate);

		java.lang.String strTenor = (12 / iFreq) + "M";
		java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCouponPeriod = null;

		try {
			org.drip.param.period.UnitCouponAccrualSetting ucas = new
				org.drip.param.period.UnitCouponAccrualSetting (iFreq, strCouponDCAdj, bCouponEOMAdj,
					strAccrualDCAdj, bAccrualEOMAdj, strCurrency, false,
						org.drip.analytics.support.CompositePeriodBuilder.ACCRUAL_COMPOUNDING_RULE_GEOMETRIC);

			org.drip.param.period.CompositePeriodSetting cps = new
				org.drip.param.period.CompositePeriodSetting (iFreq, strTenor, strCurrency, dapPay, 1., null,
					null, null, creditLabel);

			java.util.List<java.lang.Integer> lsStreamEdgeDate = bPeriodsFromForward ?
				org.drip.analytics.support.CompositePeriodBuilder.ForwardEdgeDates (dtEffective, dtMaturity,
					strTenor, dapAccrualEnd, org.drip.analytics.support.CompositePeriodBuilder.LONG_STUB) :
						org.drip.analytics.support.CompositePeriodBuilder.BackwardEdgeDates (dtEffective,
							dtMaturity, strTenor, dapAccrualEnd,
								org.drip.analytics.support.CompositePeriodBuilder.LONG_STUB);

			if (null == floaterLabel) {
				org.drip.param.period.ComposableFixedUnitSetting cfus = new
					org.drip.param.period.ComposableFixedUnitSetting (strTenor,
						org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE, null,
							dblCoupon, 0., strCurrency);

				lsCouponPeriod = org.drip.analytics.support.CompositePeriodBuilder.FixedCompositeUnit
					(lsStreamEdgeDate, cps, ucas, cfus);
			} else {
				org.drip.param.period.ComposableFloatingUnitSetting cfus = new
					org.drip.param.period.ComposableFloatingUnitSetting (strTenor,
						org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE, null,
							floaterLabel,
								org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
					dblCoupon);

				lsCouponPeriod = org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit
					(lsStreamEdgeDate, cps, cfus);
			}

			return new BondStream (lsCouponPeriod, iFinalMaturityDate, strMaturityType);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the BondStream instance from the list of coupon periods
	 * 
	 * @param lsCouponPeriod List of Coupon Period
	 * @param iFinalMaturityDate Final Maturity Date
	 * @param strMaturityType Maturity Type
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public BondStream (
		final java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCouponPeriod,
		final int iFinalMaturityDate,
		final java.lang.String strMaturityType)
		throws java.lang.Exception
	{
		super (lsCouponPeriod);

		_strMaturityType = strMaturityType;
		_iFinalMaturityDate = iFinalMaturityDate;
	}

	/**
	 * Return the first Coupon period
	 * 
	 * @return The first Coupon period
	 */

	public org.drip.analytics.cashflow.CompositePeriod firstPeriod()
	{
		return periods().get (0);
	}

	/**
	 * Returns the final Coupon period
	 * 
	 * @return The final Coupon period
	 */

	public org.drip.analytics.cashflow.CompositePeriod lastPeriod()
	{
		java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCouponPeriod = periods();

		return lsCouponPeriod.get (lsCouponPeriod.size() - 1);
	}

	/**
	 * Return the period index containing the specified date
	 * 
	 * @param iDate Date input
	 * 
	 * @return Period index containing the date
	 * 
	 * @throws java.lang.Exception Thrown if the input date not in the period set range
	 */

	public int periodIndex (
		final int iDate)
		throws java.lang.Exception
	{
		int i = 0;

		for (org.drip.analytics.cashflow.CompositePeriod period : periods()) {
			if (period.contains (iDate)) return i;

			++i;
		}

		throw new java.lang.Exception ("BondStream::periodIndex => Input date not in the period set range!");
	}
	
	/**
	 * Retrieve the period corresponding to the given index
	 * 
	 * @param iIndex Period index
	 * 
	 * @return Period object corresponding to the input index
	 */

	public org.drip.analytics.cashflow.CompositePeriod period (
		final int iIndex)
	{
		try {
			return periods().get (iIndex);
		} catch (java.lang.Exception e) {
		}

		return null;
	}

	/**
	 * Retrieve the Maturity Type
	 * 
	 * @return The Maturity Type
	 */

	public java.lang.String maturityType()
	{
		return _strMaturityType;
	}

	/**
	 * Retrieve the Final Maturity Date
	 * 
	 * @return The Final Maturity Date
	 */

	public int finalMaturityDate()
	{
		return _iFinalMaturityDate;
	}
}
