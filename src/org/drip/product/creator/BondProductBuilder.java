
package org.drip.product.creator;

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
 * BondProductBuilder holds the static parameters of the bond product needed for the full bond valuation. It
 * 		contains the bond identifier parameters (ISIN, CUSIP), the issuer level parameters (Ticker, SPN or
 * 		the credit curve string), coupon parameters (coupon rate, coupon frequency, coupon type, day count),
 * 		maturity parameters (maturity date, maturity type, final maturity, redemption value), date parameters
 * 		(announce, first settle, first coupon, interest accrual start, and issue dates), embedded option
 * 		parameters (callable, putable, has been exercised), currency parameters (trade, coupon, and
 * 		redemption currencies), floater parameters (floater flag, floating coupon convention, current coupon,
 * 		rate index, spread), and whether the bond is perpetual or has defaulted.
 * 
 * @author Lakshmi Krishnamurthy
 *
 */

public class BondProductBuilder {
	private static final boolean m_bBlog = false;
	private static final boolean m_bDisplayWarnings = true;

	/**
	 * ISIN
	 */

	public java.lang.String _strISIN = "";

	/**
	 * CUSIP
	 */

	public java.lang.String _strCUSIP = "";

	/**
	 * Ticker
	 */

	public java.lang.String _strTicker = "";

	/**
	 * Coupon
	 */

	public double _dblCoupon = java.lang.Double.NaN;

	/**
	 * Maturity
	 */

	public org.drip.analytics.date.JulianDate _dtMaturity = null;

	/**
	 * Coupon Frequency
	 */

	public int _iCouponFreq = 0;

	/**
	 * Coupon Type
	 */

	public java.lang.String _strCouponType = "";

	/**
	 * Maturity Type
	 */

	public java.lang.String _strMaturityType = "";

	/**
	 * Calculation Type
	 */

	public java.lang.String _strCalculationType = "";

	/**
	 * Day count Code
	 */

	public java.lang.String _strDayCountCode = "";

	/**
	 * Redemption Value
	 */

	public double _dblRedemptionValue = java.lang.Double.NaN;

	/**
	 * Announce Date
	 */

	public org.drip.analytics.date.JulianDate _dtAnnounce = null;

	/**
	 * First Settle Date
	 */

	public org.drip.analytics.date.JulianDate _dtFirstSettle = null;

	/**
	 * First Coupon Date
	 */

	public org.drip.analytics.date.JulianDate _dtFirstCoupon = null;

	/**
	 * Interest Accrual Start Date
	 */

	public org.drip.analytics.date.JulianDate _dtInterestAccrualStart = null;

	/**
	 * Issue Date
	 */

	public org.drip.analytics.date.JulianDate _dtIssue = null;

	/**
	 * Callable flag
	 */

	public boolean _bIsCallable = false;

	/**
	 * Putable flag
	 */

	public boolean _bIsPutable = false;

	/**
	 * Sinkable flag
	 */

	public boolean _bIsSinkable = false;

	/**
	 * Redemption Currency
	 */

	public java.lang.String _strRedemptionCurrency = "";

	/**
	 * Coupon Currency
	 */

	public java.lang.String _strCouponCurrency = "";

	/**
	 * Trade Currency
	 */

	public java.lang.String _strTradeCurrency = "";

	/**
	 * Has Been Exercised flag
	 */

	public boolean _bHasBeenCalled = false;

	/**
	 * Floater Coupon Day Count Convention
	 */

	public java.lang.String _strFloatCouponConvention = "";

	/**
	 * Current Coupon
	 */

	public double _dblCurrentCoupon = java.lang.Double.NaN;

	/**
	 * Is Floater flag
	 */

	public boolean _bIsFloater = false;

	/**
	 * Final Maturity Date
	 */

	public org.drip.analytics.date.JulianDate _dtFinalMaturity = null;

	/**
	 * Is Perpetual flag
	 */

	public boolean _bIsPerpetual = false;

	/**
	 * Is Defaulted flag
	 */

	public boolean _bIsDefaulted = false;

	/**
	 * Floater Spread
	 */

	public double _dblFloatSpread = java.lang.Double.NaN;

	/**
	 * Rate Index
	 */

	public java.lang.String _strRateIndex = "";

	/**
	 * Issuer SPN
	 */

	public java.lang.String _strIssuerSPN = "";

	private static final java.lang.String DES (
		final BondProductBuilder bpb)
	{
		return bpb._strTicker + "  " + bpb._dtMaturity.toString() + "[" + bpb._strISIN + "]";
	}

	private org.drip.analytics.date.JulianDate reconcileStartDate()
	{
		if (null != _dtInterestAccrualStart) return _dtInterestAccrualStart;

		if (null != _dtIssue) return _dtIssue;

		if (null != _dtFirstSettle) return _dtFirstSettle;

		return _dtAnnounce;
	}

	/**
	 * Create BondProductBuilder from the SQL ResultSet and the input MPC
	 * 
	 * @param rs SQL ResultSet
	 * @param mpc org.drip.param.definition.MarketParams to help fill some of the fields in
	 * 
	 * @return BondProductBuilder object
	 */

	public static final BondProductBuilder CreateFromResultSet (
		final java.sql.ResultSet rs,
		final org.drip.param.definition.ScenarioMarketParams mpc)
	{
		try {
			BondProductBuilder bpb = new BondProductBuilder();

			if (null == (bpb._strISIN = rs.getString ("ISIN"))) {
				System.out.println ("No ISIN!");

				return null;
			}

			if (m_bBlog) System.out.println ("Loading " + bpb._strISIN + " ...");

			if (null == (bpb._strCUSIP = rs.getString ("CUSIP"))) {
				System.out.println ("No CUSIP!");

				return null;
			}

			bpb._strTicker = rs.getString ("Ticker");

			if (!org.drip.quant.common.NumberUtil.IsValid (bpb._dblCoupon = 0.01 * rs.getDouble ("Coupon")))
			{
				System.out.println ("Invalid coupon for ISIN " + bpb._strISIN);

				return null;
			}

			if (null == (bpb._dtMaturity = org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry (rs.getDate
				("Maturity")))) {
				System.out.println ("Invalid maturity for ISIN " + bpb._strISIN);

				return null;
			}

			bpb._iCouponFreq = rs.getInt ("CouponFreq");

			bpb._strCouponType = rs.getString ("CouponType");

			bpb._strMaturityType = rs.getString ("MaturityType");

			bpb._strCalculationType = rs.getString ("CalculationType");

			bpb._strDayCountCode = rs.getString ("DayCountConv");

			bpb._dblRedemptionValue = rs.getDouble ("RedemptionValue");

			if (null == (bpb._dtAnnounce = org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry (rs.getDate
				("AnnounceDate")))) {
				System.out.println ("Invalid announce date for ISIN " + DES (bpb));

				return null;
			}

			if (null == (bpb._dtFirstSettle = org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry
				(rs.getDate ("FirstSettleDate")))) {
				System.out.println ("Invalid first settle date for ISIN " + DES (bpb));

				return null;
			}

			if (null == (bpb._dtFirstCoupon = org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry
				(rs.getDate ("FirstCouponDate")))) {
				if (m_bBlog) System.out.println ("Invalid first coupon date for ISIN " + DES (bpb));
			}

			if (null == (bpb._dtInterestAccrualStart = org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry
				(rs.getDate ("AccrualStartDate")))) {
				System.out.println ("Invalid accrual start date for " + DES (bpb));

				return null;
			}

			if (null == (bpb._dtIssue = org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry (rs.getDate
				("IssueDate")))) {
				System.out.println ("Invalid issue date for " + DES (bpb));

				return null;
			}

			bpb._bIsCallable = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("IsCallable"));

			bpb._bIsPutable = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("IsPutable"));

			bpb._bIsSinkable = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("IsSinkable"));

			bpb._strRedemptionCurrency = org.drip.analytics.support.Helper.SwitchIRCurve
				(rs.getString ("RedemptionCurrency"));

			if (null == bpb._strRedemptionCurrency || bpb._strRedemptionCurrency.isEmpty()) {
				System.out.println ("Invalid redemption currency for " + DES (bpb));

				return null;
			}

			bpb._strCouponCurrency = org.drip.analytics.support.Helper.SwitchIRCurve
				(rs.getString ("CouponCurrency"));

			if (null == bpb._strCouponCurrency || bpb._strCouponCurrency.isEmpty()) {
				System.out.println ("Invalid coupon currency for " + DES (bpb));

				return null;
			}

			bpb._strTradeCurrency = org.drip.analytics.support.Helper.SwitchIRCurve
				(rs.getString ("TradeCurrency"));

			if (null == bpb._strTradeCurrency || bpb._strTradeCurrency.isEmpty()) {
				System.out.println ("Invalid trade currency for " + DES (bpb));

				return null;
			}

			bpb._bHasBeenCalled = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("Called"));

			bpb._strFloatCouponConvention = rs.getString ("FloatCouponConvention");

			bpb._bIsFloater = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("Floater"));

			// bpb._dblCurrentCoupon = 0.01 * rs.getDouble ("CurrentCoupon");

			bpb._dtFinalMaturity = org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry (rs.getDate
				("FinalMaturity"));

			bpb._bIsPerpetual = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("Perpetual"));

			bpb._bIsDefaulted = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("Defaulted"));

			bpb._dblFloatSpread = 0.0001 * rs.getDouble ("FloatSpread");

			bpb._strRateIndex = rs.getString ("RateIndex");

			if (bpb._bIsFloater && !org.drip.quant.common.NumberUtil.IsValid (bpb._dblFloatSpread) && (null ==
				bpb._strRateIndex || bpb._strRateIndex.isEmpty())) {
				System.out.println ("Invalid float spread for " + DES (bpb));

				return null;
			}

			bpb._strIssuerSPN = rs.getString ("SPN");

			if (!bpb.validate (mpc)) return null;

			if (m_bBlog) System.out.println ("Loaded " + DES (bpb) + ".");

			return bpb;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create BondProductBuilder from the JSON Map and the input MPC
	 * 
	 * @param mapJSON The JSON Ref Data Map
	 * @param mpc org.drip.param.definition.MarketParams to help fill some of the fields in
	 * 
	 * @return BondProductBuilder object
	 */

	public static final BondProductBuilder CreateFromJSONMap (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> mapJSON,
		final org.drip.param.definition.ScenarioMarketParams mpc)
	{
		/* if (null == mapJSON || 0 == mapJSON.size() || !mapJSON.containsKey ("isin") || !mapJSON.containsKey
			("cusip") || !mapJSON.containsKey ("ticker") || !mapJSON.containsKey ("coupon") ||
				!mapJSON.containsKey ("maturity") || !mapJSON.containsKey ("frequency") ||
					!mapJSON.containsKey ("couponType") || !mapJSON.containsKey ("maturityType") ||
						!mapJSON.containsKey ("calcType") || !mapJSON.containsKey ("dayCount") ||
							!mapJSON.containsKey ("redempValue") || !mapJSON.containsKey ("redempCrncy") ||
								!mapJSON.containsKey ("cpnCrncy") || !mapJSON.containsKey ("tradeCrncy") ||
									!mapJSON.containsKey ("firstCpnDate") || !mapJSON.containsKey
										("issueDate") || !mapJSON.containsKey ("called") ||
											!mapJSON.containsKey ("defaulted") || !mapJSON.containsKey
												("quotedMargin"))
			return null; */

		BondProductBuilder bpb = new BondProductBuilder();

		if (null == (bpb._strISIN = mapJSON.get ("isin"))) return null;

		if (null == (bpb._strCUSIP = mapJSON.get ("cusip"))) return null;

		if (null == (bpb._strTicker = mapJSON.get ("ticker"))) return null;

		if (!org.drip.quant.common.NumberUtil.IsValid (bpb._dblCoupon = 0.01 * java.lang.Double.parseDouble
			(mapJSON.get ("coupon"))))
			return null;

		if (null == (bpb._dtMaturity = org.drip.analytics.date.DateUtil.MakeJulianFromYYYYMMDD
			(mapJSON.get ("maturity"), "-")))
			return null;

		bpb._iCouponFreq = java.lang.Integer.parseInt (mapJSON.get ("frequency"));

		bpb._strCouponType = mapJSON.get ("couponType");

		bpb._strMaturityType = mapJSON.get ("maturityType");

		bpb._strCalculationType = mapJSON.get ("calcType");

		bpb._strDayCountCode = mapJSON.get ("dayCount");

		bpb._dblRedemptionValue = java.lang.Double.parseDouble (mapJSON.get ("redempValue"));

		if (null == (bpb._strRedemptionCurrency = mapJSON.get ("redempCrncy")) ||
			bpb._strRedemptionCurrency.isEmpty()) {
			System.out.println ("Invalid redemption currency for " + DES (bpb));

			return null;
		}

		if (null == (bpb._strCouponCurrency = mapJSON.get ("cpnCrncy")) || bpb._strCouponCurrency.isEmpty())
		{
			System.out.println ("Invalid Coupon currency for " + DES (bpb));

			return null;
		}

		if (null == (bpb._strTradeCurrency = mapJSON.get ("tradeCrncy")) || bpb._strTradeCurrency.isEmpty())
		{
			System.out.println ("Invalid Trade currency for " + DES (bpb));

			return null;
		}

		if (null == (bpb._dtFirstCoupon = org.drip.analytics.date.DateUtil.MakeJulianFromYYYYMMDD
			(mapJSON.get ("firstCpnDate"), "-")))
			return null;

		if (null == (bpb._dtIssue = org.drip.analytics.date.DateUtil.MakeJulianFromYYYYMMDD
			(mapJSON.get ("issueDate"), "-")))
			return null;

		try {
			bpb._bIsCallable = java.lang.Boolean.parseBoolean (mapJSON.get ("callable"));

			bpb._bIsPutable = java.lang.Boolean.parseBoolean (mapJSON.get ("putable"));

			bpb._bIsSinkable = java.lang.Boolean.parseBoolean (mapJSON.get ("sinkable"));

			bpb._bHasBeenCalled = java.lang.Boolean.parseBoolean (mapJSON.get ("called"));

			// bpb._strFloatCouponConvention = rs.getString ("FloatCouponConvention");

			bpb._bIsFloater = java.lang.Boolean.parseBoolean (mapJSON.get ("floater"));

			// bpb._dblCurrentCoupon = 0.01 * rs.getDouble ("CurrentCoupon");

			if (null == (bpb._dtFinalMaturity = org.drip.analytics.date.DateUtil.MakeJulianFromYYYYMMDD
				(mapJSON.get ("finalMaturityDt"), "-")))
				return null;

			bpb._bIsPerpetual = java.lang.Boolean.parseBoolean (mapJSON.get ("perpetual"));

			bpb._bIsDefaulted = java.lang.Boolean.parseBoolean (mapJSON.get ("defaulted"));

			// bpb._dblFloatSpread = java.lang.Double.parseDouble (mapJSON.get ("quotedMargin"));

			bpb._strRateIndex = mapJSON.get ("resetIndex");

			if (bpb._bIsFloater && !org.drip.quant.common.NumberUtil.IsValid (bpb._dblFloatSpread) && (null ==
				bpb._strRateIndex || bpb._strRateIndex.isEmpty())) {
				System.out.println ("Invalid float spread for " + DES (bpb));

				return null;
			}

			// bpb._strIssuerSPN = rs.getString ("SPN");

			if (!bpb.validate (mpc)) return null;

			if (m_bBlog) System.out.println ("Loaded " + DES (bpb) + ".");

			return bpb;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Empty BondProductBuilder ctr - uninitialized members
	 */

	public BondProductBuilder()
	{
	}

	/**
	 * Set the Bond ISIN
	 * 
	 * @param strISIN ISIN input
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setISIN (
		final java.lang.String strISIN)
	{
		if (null == strISIN || strISIN.trim().isEmpty() || "null".equalsIgnoreCase (strISIN.trim()))
			return false;

		_strISIN = strISIN;
		return true;
	}

	/**
	 * Set the Bond CUSIP
	 * 
	 * @param strCUSIP CUSIP input
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setCUSIP (
		final java.lang.String strCUSIP)
	{
		if (null == strCUSIP || strCUSIP.trim().isEmpty() || "null".equalsIgnoreCase (strCUSIP.trim()))
			return false;

		_strCUSIP = strCUSIP;
		return true;
	}

	/**
	 * Set the Bond Ticker
	 * 
	 * @param strTicker Ticker input
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setTicker (
		final java.lang.String strTicker)
	{
		if (null == (_strTicker = strTicker.trim())) _strTicker = "";

		return true;
	}

	/**
	 * Set the Bond Coupon
	 * 
	 * @param strCoupon Coupon input
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setCoupon (
		final java.lang.String strCoupon)
	{
		if (null == strCoupon || strCoupon.trim().isEmpty() || "null".equalsIgnoreCase (strCoupon.trim()))
			_dblCoupon = 0.;

		try {
			_dblCoupon = new java.lang.Double (strCoupon.trim()).doubleValue();

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog) System.out.println ("Bad coupon " + strCoupon + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set the Bond Maturity
	 * 
	 * @param strMaturity Maturity input
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setMaturity (
		final java.lang.String strMaturity)
	{
		try {
			if (null == (_dtMaturity = org.drip.analytics.date.DateUtil.MakeJulianDateFromBBGDate
				(strMaturity.trim())))
				return false;

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog) System.out.println ("Bad Maturity " + strMaturity + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set the Bond Coupon Frequency
	 * 
	 * @param strCouponFreq Coupon Frequency input
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setCouponFreq (
		final java.lang.String strCouponFreq)
	{
		if (null == strCouponFreq || strCouponFreq.isEmpty() || "null".equalsIgnoreCase (strCouponFreq))
			_iCouponFreq = 0;
		else {
			try {
				_iCouponFreq = (int) new java.lang.Double (strCouponFreq.trim()).doubleValue();
			} catch (java.lang.Exception e) {
				if (m_bBlog) System.out.println ("Bad Cpn Freq " + strCouponFreq + " for ISIN " + _strISIN);

				return false;
			}
		}

		return true;
	}

	/**
	 * Set the Bond Coupon Type
	 * 
	 * @param strCouponType Coupon Type input
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setCouponType (
		final java.lang.String strCouponType)
	{
		if (null == (_strCouponType = strCouponType.trim())) _strCouponType = "";

		return true;
	}

	/**
	 * Set the Bond Maturity Type
	 * 
	 * @param strMaturityType Maturity Type input
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setMaturityType (
		final java.lang.String strMaturityType)
	{
		if (null == (_strMaturityType = strMaturityType.trim())) _strMaturityType = "";

		return true;
	}

	/**
	 * Set the Bond Calculation Type
	 * 
	 * @param strCalculationType Calculation Type input
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setCalculationType (
		final java.lang.String strCalculationType)
	{
		if (null == (_strCalculationType = strCalculationType.trim())) _strCalculationType = "";

		return true;
	}

	/**
	 * Set the Bond Day Count Code
	 * 
	 * @param strDayCountCode Day Count Code input
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setDayCountCode (
		final java.lang.String strDayCountCode)
	{
		_strDayCountCode = "Unknown DC";

		try {
			_strDayCountCode = org.drip.analytics.support.Helper.ParseFromBBGDCCode
				(strDayCountCode.trim());

			return true;
		} catch (java.lang.Exception e) {
		}

		return false;
	}

	/**
	 * Set the Bond Redemption Value
	 * 
	 * @param strRedemptionValue Redemption Value input
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setRedemptionValue (
		final java.lang.String strRedemptionValue)
	{
		try {
			_dblRedemptionValue = new java.lang.Double (strRedemptionValue.trim());

			return true;
		} catch (java.lang.Exception e) {
			System.out.println ("Bad Redemption Value " + strRedemptionValue + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set the Bond Announce
	 * 
	 * @param strAnnounce Announce Date String
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setAnnounce (
		final java.lang.String strAnnounce)
	{
		try {
			_dtAnnounce = org.drip.analytics.date.DateUtil.MakeJulianDateFromBBGDate (strAnnounce.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog) System.out.println ("Bad Announce " + strAnnounce + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set the Bond First Settle
	 * 
	 * @param strFirstSettle First Settle Date String
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setFirstSettle (
		final java.lang.String strFirstSettle)
	{
		try {
			_dtFirstSettle = org.drip.analytics.date.DateUtil.MakeJulianDateFromBBGDate
				(strFirstSettle.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog) System.out.println ("Bad First Settle " + strFirstSettle + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set the Bond First Coupon Date
	 * 
	 * @param strFirstCoupon First Coupon Date String
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setFirstCoupon (
		final java.lang.String strFirstCoupon)
	{
		try {
			_dtFirstCoupon = org.drip.analytics.date.DateUtil.MakeJulianDateFromBBGDate
				(strFirstCoupon.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog) System.out.println ("Bad First Coupon " + strFirstCoupon + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set the Bond Interest Accrual Start Date
	 * 
	 * @param strInterestAccrualStart Interest Accrual Start Date String
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setInterestAccrualStart (
		final java.lang.String strInterestAccrualStart)
	{
		try {
			_dtInterestAccrualStart = org.drip.analytics.date.DateUtil.MakeJulianDateFromBBGDate
				(strInterestAccrualStart.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog)
				System.out.println ("Bad Announce " + strInterestAccrualStart + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set the Bond Issue Date
	 * 
	 * @param strIssue Issue Date String
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setIssue (
		final java.lang.String strIssue)
	{
		try {
			_dtIssue = org.drip.analytics.date.DateUtil.MakeJulianDateFromBBGDate (strIssue.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog) System.out.println ("Bad Issue " + strIssue + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set whether the Bond Is Callable
	 * 
	 * @param strCallable Callable String
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setIsCallable (
		final java.lang.String strCallable)
	{
		if (null == strCallable) _bIsCallable = false;

		if ("1".equalsIgnoreCase (strCallable.trim()))
			_bIsCallable = true;
		else
			_bIsCallable = false;

		return true;
	}

	/**
	 * Set whether the Bond Is Putable
	 * 
	 * @param strPutable Putable String
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setIsPutable (
		final java.lang.String strPutable)
	{
		if (null == strPutable) _bIsPutable = false;

		if ("1".equalsIgnoreCase (strPutable.trim()))
			_bIsPutable = true;
		else
			_bIsPutable = false;

		return true;
	}

	/**
	 * Set whether the Bond Is Sinkable
	 * 
	 * @param strSinkable Sinkable String
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setIsSinkable (
		final java.lang.String strSinkable)
	{
		if (null == strSinkable) _bIsSinkable = false;

		if ("1".equalsIgnoreCase (strSinkable.trim()))
			_bIsSinkable = true;
		else
			_bIsSinkable = false;

		return true;
	}

	/**
	 * Set The redemption Currency
	 * 
	 * @param strRedemptionCurrency Redemption Currency String
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setRedemptionCurrency (
		final java.lang.String strRedemptionCurrency)
	{
		if (null == (_strRedemptionCurrency = strRedemptionCurrency.trim()) || "null".equalsIgnoreCase
			(strRedemptionCurrency.trim()))
			return false;

		return true;
	}

	/**
	 * Set The Coupon Currency
	 * 
	 * @param strCouponCurrency Coupon Currency String
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setCouponCurrency (
		final java.lang.String strCouponCurrency)
	{
		if (null == (_strCouponCurrency = strCouponCurrency.trim()) || "null".equalsIgnoreCase
			(strCouponCurrency.trim()))
			return false;

		return true;
	}

	/**
	 * Set The Trade Currency
	 * 
	 * @param strTradeCurrency Trade Currency String
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setTradeCurrency (
		final java.lang.String strTradeCurrency)
	{
		if (null == (_strTradeCurrency = strTradeCurrency.trim()) || "null".equalsIgnoreCase
			(strTradeCurrency.trim()))
			return false;

		return true;
	}

	/**
	 * Set whether the bond Has Been Called
	 * 
	 * @param strHasBeenCalled Has Been Called String
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setHasBeenCalled (
		final java.lang.String strHasBeenCalled)
	{
		if (null == strHasBeenCalled) _bHasBeenCalled = false;

		if ("1".equalsIgnoreCase (strHasBeenCalled.trim()))
			_bHasBeenCalled = true;
		else
			_bHasBeenCalled = false;

		return true;
	}

	/**
	 * Set the bond's Float Coupon Convention
	 * 
	 * @param strFloatCouponConvention Float Coupon Convention String
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setFloatCouponConvention (
		final java.lang.String strFloatCouponConvention)
	{
		if (null == (_strFloatCouponConvention = strFloatCouponConvention.trim()))
			_strFloatCouponConvention = "";

		return true;
	}

	/**
	 * Set the bond's Current Coupon
	 * 
	 * @param strCurrentCoupon Current Coupon String
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setCurrentCoupon (
		final java.lang.String strCurrentCoupon)
	{
		if (null == strCurrentCoupon || strCurrentCoupon.trim().isEmpty() || "null".equalsIgnoreCase
			(strCurrentCoupon.trim()))
			_dblCurrentCoupon = 0.;
		else {
			try {
				_dblCurrentCoupon = new java.lang.Double (strCurrentCoupon.trim()).doubleValue();

				return true;
			} catch (java.lang.Exception e) {
				if (m_bBlog)
					System.out.println ("Bad Curr Cpn " + strCurrentCoupon + " for ISIN " + _strISIN);
			}
		}

		return false;
	}

	/**
	 * Set whether the bond is a floater or not
	 * 
	 * @param strIsFloater String indicating whether the bond is a floater
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setIsFloater (
		final java.lang.String strIsFloater)
	{
		if (null == strIsFloater) _bIsFloater = false;

		if ("1".equalsIgnoreCase (strIsFloater.trim()))
			_bIsFloater = true;
		else
			_bIsFloater = false;

		return true;
	}

	/**
	 * Set the final maturity of the bond
	 * 
	 * @param strFinalMaturity String representing the bond's final maturity
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setFinalMaturity (
		final java.lang.String strFinalMaturity)
	{
		try {
			_dtFinalMaturity = org.drip.analytics.date.DateUtil.MakeJulianDateFromBBGDate
				(strFinalMaturity.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog)
				System.out.println ("Bad Final Maturity " + strFinalMaturity + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set whether the bond is perpetual or not
	 * 
	 * @param strIsPerpetual String representing whether the bond is perpetual or not
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setIsPerpetual (
		final java.lang.String strIsPerpetual)
	{
		if (null == strIsPerpetual) _bIsPerpetual = false;

		if ("1".equalsIgnoreCase (strIsPerpetual.trim()))
			_bIsPerpetual = true;
		else
			_bIsPerpetual = false;

		return true;
	}

	/**
	 * Set whether the bond is defaulted or not
	 * 
	 * @param strIsDefaulted String representing whether the bond is defaulted or not
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setIsDefaulted (
		final java.lang.String strIsDefaulted)
	{
		if (null == strIsDefaulted) _bIsDefaulted = false;

		if ("1".equalsIgnoreCase (strIsDefaulted.trim()))
			_bIsDefaulted = true;
		else
			_bIsDefaulted = false;

		return true;
	}

	/**
	 * Set the bond's floating rate spread
	 * 
	 * @param strFloatSpread String representing the bond's floating spread
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setFloatSpread (
		final java.lang.String strFloatSpread)
	{
		try {
			_dblFloatSpread = new java.lang.Double (strFloatSpread.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog) System.out.println ("Bad Float Spread " + strFloatSpread + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set the bond's floating rate spread from the MPC
	 * 
	 * @param mpc org.drip.param.definition.MarketParams
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setFloatSpread (
		final org.drip.param.definition.ScenarioMarketParams mpc)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblCurrentCoupon)) {
			System.out.println ("Curr cpn for ISIN " + _strISIN + " is NaN!");

			return false;
		}

		if (null == mpc || null == mpc.scenarioDiscountCurveMap() || null == mpc.scenarioDiscountCurveMap().get (_strCouponCurrency) ||
			null == mpc.scenarioDiscountCurveMap().get (_strCouponCurrency).base()) {
			if (m_bBlog) System.out.println ("Bad mpc In for ISIN " + _strISIN);

			return false;
		}

		try {
			if (0. != _dblCurrentCoupon) {
				org.drip.state.discount.MergedDiscountForwardCurve dcBase = mpc.scenarioDiscountCurveMap().get (_strCouponCurrency).base();

				_dblFloatSpread = _dblCurrentCoupon - 100. * dcBase.libor (dcBase.epoch().julian(),
					(org.drip.analytics.support.Helper.GetTenorFromFreq (_iCouponFreq)));
			} else
				_dblFloatSpread = 0.;

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog) e.printStackTrace();
		}

		return false;
	}

	/**
	 * Set the bond's Rate Index
	 * 
	 * @param strRateIndex Rate Index
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setRateIndex (
		final java.lang.String strRateIndex)
	{
		if (null == (_strRateIndex = strRateIndex)) _strRateIndex = "";

		return true;
	}

	/**
	 * Set the bond's Issuer SPN
	 * 
	 * @param strIssuerSPN Issuer SPN String
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean setIssuerSPN (
		final java.lang.String strIssuerSPN)
	{
		if (null == (_strIssuerSPN = strIssuerSPN)) _strIssuerSPN = "";

		return true;
	}

	/**
	 * Validate the state
	 * 
	 * @param mpc org.drip.param.definition.MarketParams
	 * 
	 * @return Success (true), failure (false)
	 */

	public boolean validate (
		final org.drip.param.definition.ScenarioMarketParams mpc)
	{
		if (null == _strISIN || _strISIN.isEmpty() || null == _strCUSIP || _strCUSIP.isEmpty()) {
			if (m_bDisplayWarnings)
				System.out.println ("Check ISIN[" + _strISIN + "] or CUSIP[" + _strCUSIP + "]");

			return false;
		}

		if (0 == _iCouponFreq && 0. != _dblCoupon) {
			if (m_bDisplayWarnings)
				System.out.println ("Coupon Freq and Cpn amt both not sero for ISIN[" + _strISIN + "]");

			return false;
		}

		if (49 == _iCouponFreq || 52 == _iCouponFreq) {
			if (m_bDisplayWarnings)
				System.out.println ("ISIN[" + _strISIN + "] has cpn freq of " + _iCouponFreq + "!");

			return false;
		}

		if (null == _dtInterestAccrualStart) {
			if (null == (_dtInterestAccrualStart = reconcileStartDate())) {
				if (m_bDisplayWarnings)
					System.out.println ("All possible date init candidates are null for ISIN " + _strISIN);

				return false;
			}
		}

		if (_bIsFloater && (null == _strRateIndex || _strRateIndex.isEmpty()) &&
			!org.drip.quant.common.NumberUtil.IsValid (_dblFloatSpread) && java.lang.Double.isNaN
				(_dblCurrentCoupon)) {
			if (m_bDisplayWarnings)
				System.out.println ("Invalid Rate index & float spread & current coupon for " + _strISIN);

			return false;
		}

		if (_bIsFloater && (null == _strRateIndex || _strRateIndex.isEmpty())) {
			if (null == (_strRateIndex = org.drip.analytics.support.Helper.CalcRateIndex
				(_strCouponCurrency, _iCouponFreq))) {
				if (m_bDisplayWarnings)
					System.out.println ("Warning: Cannot find Rate index for ISIN " + _strISIN);
			}
		}

		if (_bIsFloater && !org.drip.quant.common.NumberUtil.IsValid (_dblFloatSpread)) {
			try {
				if (!setFloatSpread (mpc)) {
					if (m_bDisplayWarnings)
						System.out.println ("Warning: Cannot set float spread for ISIN " + _strISIN +
							" and Coupon Currency " + _strCouponCurrency);
				}
			} catch (java.lang.Exception e) {
				if (m_bDisplayWarnings)
					System.out.println ("Warning: Cannot set float spread for ISIN " + _strISIN +
						" and Coupon Currency " + _strCouponCurrency);

				e.printStackTrace();
			}
		}

		if (null == _dtIssue) _dtIssue = reconcileStartDate();

		if (null == _dtFirstSettle) _dtFirstSettle = reconcileStartDate();

		if (null == _dtAnnounce) _dtAnnounce = reconcileStartDate();

		return true;
	}

	/**
	 * Create an SQL Insert statement from the object's state
	 * 
	 * @return String representing the SQL Insert
	 */

	public java.lang.String makeSQLInsert()
	{
		java.lang.StringBuilder sb = new java.lang.StringBuilder();

		sb.append ("insert into BondValData values(");

		sb.append ("'").append (_strISIN).append ("', ");

		sb.append ("'").append (_strCUSIP).append ("', ");

		sb.append ("'").append (_strTicker).append ("', ");

		sb.append (_dblCoupon).append (", ");

		sb.append ("'").append (_dtMaturity.toOracleDate()).append ("', ");

		sb.append (_iCouponFreq).append (", ");

		sb.append ("'").append (_strCouponType).append ("', ");

		sb.append ("'").append (_strMaturityType).append ("', ");

		sb.append ("'").append (_strCalculationType).append ("', ");

		sb.append ("'").append (_strDayCountCode).append ("', ");

		sb.append (_dblRedemptionValue).append (", ");

		sb.append ("'").append (_dtAnnounce.toOracleDate()).append ("', ");

		sb.append ("'").append (_dtFirstSettle.toOracleDate()).append ("', ");

		if (null == _dtFirstCoupon)
			sb.append ("null, ");
		else
			sb.append ("'").append (_dtFirstCoupon.toOracleDate()).append ("', ");

		sb.append ("'").append (_dtInterestAccrualStart.toOracleDate()).append ("', ");

		sb.append ("'").append (_dtIssue.toOracleDate()).append ("', ");

		sb.append ("'").append (_bIsCallable ? 1 : 0).append ("', ");

		sb.append ("'").append (_bIsPutable ? 1 : 0).append ("', ");

		sb.append ("'").append (_bIsSinkable ? 1 : 0).append ("', ");

		sb.append ("'").append (_strRedemptionCurrency).append ("', ");

		sb.append ("'").append (_strCouponCurrency).append ("', ");

		sb.append ("'").append (_strTradeCurrency).append ("', ");

		sb.append ("'").append (_bHasBeenCalled ? 1 : 0).append ("', ");

		sb.append ("'").append (_strFloatCouponConvention).append ("', ");

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblCurrentCoupon))
			sb.append ("null, ");
		else
			sb.append (_dblCurrentCoupon).append (", ");

		sb.append ("'").append (_bIsFloater ? 1 : 0).append ("', ");

		if (null == _dtFinalMaturity)
			sb.append ("null, ");
		else
			sb.append ("'").append (_dtFinalMaturity.toOracleDate()).append ("', ");

		sb.append ("'").append (_bIsPerpetual ? 1 : 0).append ("', ");

		sb.append ("'").append (_bIsDefaulted ? 1 : 0).append ("', ");

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblFloatSpread))
			sb.append ("null, ");
		else
			sb.append (_dblFloatSpread).append (", ");

		sb.append ("'").append (_strRateIndex).append ("', ");

		sb.append ("'").append (_strIssuerSPN).append ("')");

		return sb.toString();
	}

	/**
	 * Get the Bond's identifier Parameters
	 * 
	 * @return BondIdentifierParams object
	 */

	public org.drip.product.params.IdentifierSet getIdentifierParams()
	{
		org.drip.product.params.IdentifierSet idParams = new org.drip.product.params.IdentifierSet (_strISIN,
			_strCUSIP, _strISIN, _strTicker);

		return idParams.validate() ? idParams : null;
	}

	/**
	 * Get the Bond's Coupon Parameters
	 * 
	 * @return BondCouponParams object
	 */

	public org.drip.product.params.CouponSetting getCouponParams()
	{
		org.drip.product.params.CouponSetting cpnParams = new org.drip.product.params.CouponSetting
			(null, _strCouponType, _dblCoupon, java.lang.Double.NaN, java.lang.Double.NaN);

		return cpnParams.validate() ? cpnParams : null;
	}

	/**
	 * Get the Bond's Floater Parameters
	 * 
	 * @return BondFloaterParams object
	 */

	public org.drip.product.params.FloaterSetting getFloaterParams()
	{
		if (!_bIsFloater) return null;

		org.drip.product.params.FloaterSetting fltParams = new org.drip.product.params.FloaterSetting
			(_strRateIndex, _strFloatCouponConvention, _dblFloatSpread, _dblCurrentCoupon);

		return fltParams.validate() ? fltParams : null;
	}

	/**
	 * Get the Bond's Market Convention
	 * 
	 * @return MarketConvention object
	 */

	public org.drip.product.params.QuoteConvention getMarketConvention()
	{
		org.drip.product.params.QuoteConvention mktConv = new org.drip.product.params.QuoteConvention (null,
			_strCalculationType, _dtFirstSettle.julian(), _dblRedemptionValue, 0, "",
				org.drip.analytics.daycount.Convention.DATE_ROLL_ACTUAL);

		return mktConv.validate() ? mktConv : null;
	}

	/**
	 * Get the Bond's Credit Component Parameters
	 * 
	 * @return CompCRValParams object
	 */

	public org.drip.product.params.CreditSetting getCRValuationParams()
	{
		org.drip.product.params.CreditSetting crValParams = new org.drip.product.params.CreditSetting (30,
			java.lang.Double.NaN, true, "", true);

		return crValParams.validate() ? crValParams : null;
	}

	/**
	 * Get the Bond's CF termination event Parameters
	 * 
	 * @return BondCFTerminationEvent object
	 */

	public org.drip.product.params.TerminationSetting getCFTEParams()
	{
		org.drip.product.params.TerminationSetting cfteParams = new
			org.drip.product.params.TerminationSetting (_bIsPerpetual, _bIsDefaulted, _bHasBeenCalled);

		return cfteParams.validate() ? cfteParams : null;
	}

	/**
	 * Get the Bond's Notional Parameters
	 * 
	 * @return BondNotionalParams object
	 */

	public org.drip.product.params.NotionalSetting getNotionalParams()
	{
		org.drip.product.params.NotionalSetting notlParams = new org.drip.product.params.NotionalSetting
			(100., _strRedemptionCurrency, null,
				org.drip.product.params.NotionalSetting.PERIOD_AMORT_AT_START, false);

		return notlParams.validate() ? notlParams : null;
	}

	/**
	 * Get the Bond's Period Generation Parameters
	 * 
	 * @return BondPeriodGenerationParams object
	 */

	public org.drip.product.params.BondStream getPeriodGenParams()
	{
		return org.drip.product.params.BondStream.Create (_dtMaturity.julian(),
			_dtInterestAccrualStart.julian(), null == _dtFinalMaturity ? java.lang.Integer.MIN_VALUE :
				_dtFinalMaturity.julian(), null == _dtFirstCoupon ? java.lang.Integer.MIN_VALUE :
					_dtFirstCoupon.julian(), _dtInterestAccrualStart.julian(), _iCouponFreq,
						_dblCurrentCoupon, _strDayCountCode, _strDayCountCode, null, null, null, null, null,
							null, null, null, _strMaturityType, false, _strCouponCurrency,
								_strCouponCurrency, !org.drip.quant.common.StringUtil.IsEmpty (_strRateIndex)
									? org.drip.state.identifier.ForwardLabel.Standard (_strRateIndex) : null,
										!org.drip.quant.common.StringUtil.IsEmpty (_strIssuerSPN) ?
											org.drip.state.identifier.CreditLabel.Standard (_strIssuerSPN) :
												null);
	}

	/**
	 * Create an SQL Delete statement from the object's state
	 * 
	 * @return String representing the SQL Delete
	 */

	public java.lang.String makeSQLDelete()
	{
		java.lang.StringBuilder sb = new java.lang.StringBuilder();

		sb.append ("delete from BondValData where ISIN = '").append (_strISIN).append
			("' or CUSIP = '").append (_strCUSIP).append ("'");

		return sb.toString();
	}
}
