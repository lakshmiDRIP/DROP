
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
 * BondRefDataBuilder holds the entire set of static parameters for the bond product. In particular, it contains
 * 		the bond identifier parameters (ISIN, CUSIP, BBG ID, name short name), the issuer level parameters
 * 		(Ticker, category, industry, issue type, issuer country, issuer country code, collateral type,
 * 		description, security type, unique Bloomberg ID, long company name, issuer name, SPN or the credit
 * 		curve string), issue parameters (issue amount, issue price, outstanding amount, minimum piece,
 * 		minimum increment, par amount, lead manager, exchange code, country of incorporation, country of
 * 		guarantor, country of domicile, industry sector, industry group, industry sub-group, senior/sub),
 * 		coupon parameters (coupon rate, coupon frequency, coupon type, day count), maturity parameters
 * 		(maturity date, maturity type, final maturity, redemption value), date parameters (announce, first
 * 		settle, first coupon, interest accrual start, next coupon, previous coupon, penultimate coupon, and
 * 		issue dates), embedded option parameters (callable, putable, has been exercised), currency parameters
 * 		(trade, coupon, and redemption currencies), floater parameters (floater flag, floating coupon
 * 		convention, current coupon, rate index, spread), trade status, ratings (SnP, Moody, and Fitch), and
 * 		whether the bond is private placement, is registered, is a bearer bond, is reverse convertible, is a
 * 		structured note, can be unit traded, is perpetual or has defaulted.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class BondRefDataBuilder implements org.drip.product.params.Validatable {
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
	 * Bloomberg ID
	 */

	public java.lang.String _strBBGID = "";

	/**
	 * Issuer Category
	 */

	public java.lang.String _strIssuerCategory = "";

	/**
	 * Ticker
	 */

	public java.lang.String _strTicker = "";

	/**
	 * Series
	 */

	public java.lang.String _strSeries = "";

	/**
	 * Name
	 */

	public java.lang.String _strName = "";

	/**
	 * Short Name
	 */

	public java.lang.String _strShortName = "";

	/**
	 * Issuer Industry
	 */

	public java.lang.String _strIssuerIndustry = "";

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
	 * Day Count Code
	 */

	public java.lang.String _strDayCountCode = "";

	/**
	 * Market Issue Type
	 */

	public java.lang.String _strMarketIssueType = "";

	/**
	 * Issue Country Code
	 */

	public java.lang.String _strIssueCountryCode = "";

	/**
	 * Issue Country
	 */

	public java.lang.String _strIssueCountry = "";

	/**
	 * Collateral Type
	 */

	public java.lang.String _strCollateralType = "";

	/**
	 * Issue Amount
	 */

	public double _dblIssueAmount = java.lang.Double.NaN;

	/**
	 * Outstanding Amount
	 */

	public double _dblOutstandingAmount = java.lang.Double.NaN;

	/**
	 * Minimum Piece
	 */

	public double _dblMinimumPiece = java.lang.Double.NaN;

	/**
	 * Minimum Increment
	 */

	public double _dblMinimumIncrement = java.lang.Double.NaN;

	/**
	 * Par Amount
	 */

	public double _dblParAmount = java.lang.Double.NaN;

	/**
	 * Lead Manager
	 */

	public java.lang.String _strLeadManager = "";

	/**
	 * Exchange Code
	 */

	public java.lang.String _strExchangeCode = "";

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
	 * Next Coupon Date
	 */

	public org.drip.analytics.date.JulianDate _dtNextCouponDate = null;

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
	 * Bloomberg Parent
	 */

	public java.lang.String _strBBGParent = "";

	/**
	 * Country of Incorporation
	 */

	public java.lang.String _strCountryOfIncorporation = "";

	/**
	 * Industry Sector
	 */

	public java.lang.String _strIndustrySector = "";

	/**
	 * Industry Group
	 */

	public java.lang.String _strIndustryGroup = "";

	/**
	 * Industry Sub Group
	 */

	public java.lang.String _strIndustrySubgroup = "";

	/**
	 * Country of Guarantor
	 */

	public java.lang.String _strCountryOfGuarantor = "";

	/**
	 * Country of Domicile
	 */

	public java.lang.String _strCountryOfDomicile = "";

	/**
	 * Description
	 */

	public java.lang.String _strDescription = "";

	/**
	 * Security Type
	 */

	public java.lang.String _strSecurityType = "";

	/**
	 * Previous Coupon Date
	 */

	public org.drip.analytics.date.JulianDate _dtPrevCouponDate = null;

	/**
	 * Unique Bloomberg ID
	 */

	public java.lang.String _strBBGUniqueID = "";

	/**
	 * Long Company Name
	 */

	public java.lang.String _strLongCompanyName = "";

	/**
	 * Flag indicating Structured Note
	 */

	public boolean _bIsStructuredNote = false;

	/**
	 * Flag indicating whether unit traded
	 */

	public boolean _bIsUnitTraded = false;

	/**
	 * Flag indicating is reverse convertible
	 */

	public boolean _bIsReversibleConvertible = false;

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
	 * Is this a Bearer Bond
	 */

	public boolean _bIsBearer = false;

	/**
	 * Is this registered
	 */

	public boolean _bIsRegistered = false;

	/**
	 * Has this been called
	 */

	public boolean _bHasBeenCalled = false;

	/**
	 * Issuer Name
	 */

	public java.lang.String _strIssuer = "";

	/**
	 * Penultimate Coupon Date
	 */

	public org.drip.analytics.date.JulianDate _dtPenultimateCouponDate = null;

	/**
	 * Float Coupon Convention
	 */

	public java.lang.String _strFloatCouponConvention = "";

	/**
	 * Current Coupon
	 */

	public double _dblCurrentCoupon = java.lang.Double.NaN;

	/**
	 * Is this bond a floater
	 */

	public boolean _bIsFloater = false;

	/**
	 * Trade Status
	 */

	public boolean _bTradeStatus = false;

	/**
	 * CDR Country Code
	 */

	public java.lang.String _strCDRCountryCode = "";

	/**
	 * CDR Settle Code
	 */

	public java.lang.String _strCDRSettleCode = "";

	/**
	 * Final Maturity Date
	 */

	public org.drip.analytics.date.JulianDate _dtFinalMaturity = null;

	/**
	 * Is this a private placement
	 */

	public boolean _bIsPrivatePlacement = false;

	/**
	 * Is this bond perpetual
	 */

	public boolean _bIsPerpetual = false;

	/**
	 * Has this bond defaulted
	 */

	public boolean _bIsDefaulted = false;

	/**
	 * Spread over the floater index for this bond
	 */

	public double _dblFloatSpread = java.lang.Double.NaN;

	/**
	 * Floating rate index
	 */

	public java.lang.String _strRateIndex = "";

	/**
	 * Moody's Rating
	 */

	public java.lang.String _strMoody = "";

	/**
	 * SnP rating
	 */

	public java.lang.String _strSnP = "";

	/**
	 * Fitch Rating
	 */

	public java.lang.String _strFitch = "";

	/**
	 * Senior or Sub-ordinate
	 */

	public java.lang.String _strSnrSub = "";

	/**
	 * Issuer SPN
	 */

	public java.lang.String _strIssuerSPN = "";

	/**
	 * Issue Price
	 */

	public double _dblIssuePrice = java.lang.Double.NaN;

	/**
	 * Coupon
	 */

	public double _dblCoupon = java.lang.Double.NaN;

	/**
	 * Maturity
	 */

	public org.drip.analytics.date.JulianDate _dtMaturity = null;

	private org.drip.analytics.date.JulianDate reconcileStartDate()
	{
		if (null != _dtInterestAccrualStart) return _dtInterestAccrualStart;

		if (null != _dtFirstCoupon) return _dtFirstCoupon;

		if (null != _dtIssue) return _dtIssue;

		if (null != _dtFirstSettle) return _dtFirstSettle;

		return _dtAnnounce;
	}

	/**
	 * Create BondRefDataBuilder object from java ResultSet SQL
	 * 
	 * @param rs SQL ResultSet
	 * 
	 * @return BondRefDataBuilder object
	 */

	public static final BondRefDataBuilder CreateFromResultSet (
		final java.sql.ResultSet rs)
	{
		try {
			BondRefDataBuilder brdb = new BondRefDataBuilder();

			if (null == (brdb._strISIN = rs.getString ("ISIN"))) return null;

			if (null == (brdb._strCUSIP = rs.getString ("CUSIP"))) return null;

			brdb._strBBGID = rs.getString ("BBG_ID");

			brdb._strIssuerCategory = rs.getString ("IssuerCategory");

			brdb._strTicker = rs.getString ("Ticker");

			if (!org.drip.quant.common.NumberUtil.IsValid (brdb._dblCoupon = rs.getDouble ("Coupon")))
				return null;

			brdb._dtMaturity = org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry (rs.getDate
				("Maturity"));

			brdb._strSeries = rs.getString ("Series");

			brdb._strName = rs.getString ("Name");

			brdb._strShortName = rs.getString ("ShortName");

			brdb._strIssuerIndustry = rs.getString ("IssuerIndustry");

			brdb._strCouponType = rs.getString ("CouponType");

			brdb._strMaturityType = rs.getString ("MaturityType");

			brdb._strCalculationType = rs.getString ("CalculationType");

			brdb._strDayCountCode = rs.getString ("DayCountConv");

			brdb._strMarketIssueType = rs.getString ("MarketIssueType");

			brdb._strIssueCountryCode = rs.getString ("IssueCountryCode");

			brdb._strIssueCountry = rs.getString ("IssueCountry");

			brdb._strCollateralType = rs.getString ("CollateralType");

			brdb._dblIssueAmount = rs.getDouble ("IssueAmount");

			brdb._dblOutstandingAmount = rs.getDouble ("OutstandingAmount");

			brdb._dblMinimumPiece = rs.getDouble ("MinimumPiece");

			brdb._dblMinimumIncrement = rs.getDouble ("MinimumIncrement");

			brdb._dblParAmount = rs.getDouble ("ParAmount");

			brdb._strLeadManager = rs.getString ("LeadManager");

			brdb._strExchangeCode = rs.getString ("ExchangeCode");

			brdb._dblRedemptionValue = rs.getDouble ("RedemptionValue");

			brdb._dtNextCouponDate = org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry (rs.getDate
				("NextCouponDate"));

			if (null == (brdb._dtAnnounce = org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry
				(rs.getDate ("AnnounceDate"))))
				return null;

			if (null == (brdb._dtFirstSettle = org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry
				(rs.getDate ("FirstSettleDate"))))
				return null;

			if (null == (brdb._dtFirstCoupon = org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry
				(rs.getDate ("FirstCouponDate"))))
				return null;

			if (null == (brdb._dtInterestAccrualStart =
				org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry (rs.getDate ("AccrualStartDate"))))
				return null;

			if (null == (brdb._dtIssue = org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry (rs.getDate
				("IssueDate"))))
				return null;

			brdb._bIsCallable = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("IsCallable"));

			brdb._bIsPutable = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("IsPutable"));

			brdb._bIsSinkable = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("IsSinkable"));

			brdb._strBBGParent = rs.getString ("BBGParent");

			brdb._strCountryOfIncorporation = rs.getString ("CountryOfIncorporation");

			brdb._strIndustrySector = rs.getString ("IndustrySector");

			brdb._strIndustryGroup = rs.getString ("IndustryGroup");

			brdb._strIndustrySubgroup = rs.getString ("IndustrySubgroup");

			brdb._strCountryOfGuarantor = rs.getString ("CountryOfGuarantor");

			brdb._strCountryOfDomicile = rs.getString ("CountryOfDomicile");

			brdb._strDescription = rs.getString ("Description");

			brdb._strSecurityType = rs.getString ("SecurityType");

			brdb._dtPrevCouponDate = org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry (rs.getDate
				("PrevCouponDate"));

			brdb._strBBGUniqueID = rs.getString ("BBUniqueID");

			brdb._strLongCompanyName = rs.getString ("LongCompanyName");

			brdb._strRedemptionCurrency = rs.getString ("RedemptionCurrency");

			if (null == brdb._strRedemptionCurrency || brdb._strRedemptionCurrency.isEmpty()) return null;

			brdb._strCouponCurrency = rs.getString ("CouponCurrency");

			if (null == brdb._strCouponCurrency || brdb._strCouponCurrency.isEmpty()) return null;

			brdb._bIsStructuredNote = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("StructuredNote"));

			brdb._bIsUnitTraded = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("UnitTraded"));

			brdb._bIsReversibleConvertible = org.drip.quant.common.StringUtil.ParseFromUnitaryString
				(rs.getString ("ReverseConvertible"));

			brdb._strTradeCurrency = rs.getString ("TradeCurrency");

			if (null == brdb._strTradeCurrency || brdb._strTradeCurrency.isEmpty()) return null;

			brdb._bIsBearer = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("Bearer"));

			brdb._bIsRegistered = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("Registered"));

			brdb._bHasBeenCalled = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("Called"));

			brdb._strIssuer = rs.getString ("Issuer");

			brdb._dtPenultimateCouponDate = org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry
				(rs.getDate ("PenultimateCouponDate"));

			brdb._strFloatCouponConvention = rs.getString ("FloatCouponConvention");

			brdb._dblCurrentCoupon = rs.getDouble ("CurrentCoupon");

			brdb._bIsFloater = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("Floater"));

			brdb._bTradeStatus = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("TradeStatus"));

			brdb._strCDRCountryCode = rs.getString ("CDRCountryCode");

			brdb._strCDRSettleCode = rs.getString ("CDRSettleCode");

			brdb._strFloatCouponConvention = rs.getString ("FloatCouponConvention");

			brdb._dtFinalMaturity = org.drip.analytics.date.DateUtil.MakeJulianFromRSEntry (rs.getDate
				("FinalMaturity"));

			brdb._bIsPrivatePlacement = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("PrivatePlacement"));

			brdb._bIsPerpetual = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("Perpetual"));

			brdb._bIsDefaulted = org.drip.quant.common.StringUtil.ParseFromUnitaryString (rs.getString
				("Defaulted"));

			brdb._dblFloatSpread = rs.getDouble ("FloatSpread");

			brdb._strRateIndex = rs.getString ("RateIndex");

			brdb._strMoody = rs.getString ("Moody");

			brdb._strSnP = rs.getString ("SnP");

			brdb._strFitch = rs.getString ("Fitch");

			brdb._strSnrSub = rs.getString ("SnrSub");

			brdb._strIssuerSPN = rs.getString ("SPN");

			brdb._dblIssuePrice = rs.getDouble ("IssuePrice");

			return brdb;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Empty BondRefDataBuilder ctr - uninitialized members
	 */

	public BondRefDataBuilder()
	{
	}

	/**
	 * BondRefDataBuilder de-serialization from input JSON Map
	 * 
	 * @param mapJSON Input JSON Map
	 * 
	 * @throws java.lang.Exception Thrown if BondRefDataBuilder cannot be properly de-serialized
	 */

	public BondRefDataBuilder (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> mapJSON)
		throws java.lang.Exception
	{
		if (null == mapJSON || 0 == mapJSON.size())
			throw new java.lang.Exception ("BondRefDataBuilder de-serializer: Invalid input JSON Map");

		// double dblVersion = mapJSON.get ("version");

		_strISIN = mapJSON.get ("isin");

		_strCUSIP = mapJSON.get ("cusip");

		_strBBGID = mapJSON.get ("bbgid");

		_strIssuerCategory = mapJSON.get ("issuercategory");

		_strTicker = mapJSON.get ("ticker");

		_strSeries = mapJSON.get ("series");

		_strName = mapJSON.get ("name");

		_strShortName = mapJSON.get ("shortname");

		_strIssuerIndustry = mapJSON.get ("issuerindustry");

		_strCouponType = mapJSON.get ("coupontype");

		_strMaturityType = mapJSON.get ("maturitytype");

		_strCalculationType = mapJSON.get ("calculationtype");

		_strDayCountCode = mapJSON.get ("daycountcode");

		_strMarketIssueType = mapJSON.get ("marketissuetype");

		_strIssueCountryCode = mapJSON.get ("issuecountrycode");

		_strIssueCountry = mapJSON.get ("issuecountry");

		_strCollateralType = mapJSON.get ("collateraltype");

		_dblIssueAmount = new java.lang.Double (mapJSON.get ("issueamount"));

		_dblOutstandingAmount = new java.lang.Double (mapJSON.get ("outstandingamount"));

		_dblMinimumPiece = new java.lang.Double (mapJSON.get ("minimumpiece"));

		_dblMinimumIncrement = new java.lang.Double (mapJSON.get ("minimumincrement"));

		_dblParAmount = new java.lang.Double (mapJSON.get ("paramount"));

		_strLeadManager = mapJSON.get ("leadmanager");

		_strExchangeCode = mapJSON.get ("exchangecode");

		_dblRedemptionValue = new java.lang.Double (mapJSON.get ("redemptionvalue"));

		_dtAnnounce = org.drip.analytics.date.DateUtil.MakeJulianFromYYYYMMDD (mapJSON.get ("announcedate"),
			"-");

		_dtFirstSettle = org.drip.analytics.date.DateUtil.MakeJulianFromYYYYMMDD (mapJSON.get
			("firstsettledate"), "-");

		_dtFirstCoupon = org.drip.analytics.date.DateUtil.MakeJulianFromYYYYMMDD (mapJSON.get
			("firstcoupondate"), "-");

		_dtInterestAccrualStart = org.drip.analytics.date.DateUtil.MakeJulianFromYYYYMMDD (mapJSON.get
			("interestaccrualstartdate"), "-");

		_dtIssue = org.drip.analytics.date.DateUtil.MakeJulianFromYYYYMMDD (mapJSON.get ("issuedate"), "-");

		_dtNextCouponDate = org.drip.analytics.date.DateUtil.MakeJulianFromYYYYMMDD (mapJSON.get
			("nextcoupondate"), "-");

		_bIsCallable = new java.lang.Boolean (mapJSON.get ("iscallable"));

		_bIsPutable = new java.lang.Boolean (mapJSON.get ("isputabale"));

		_bIsSinkable = new java.lang.Boolean (mapJSON.get ("issinkable"));

		_strBBGParent = mapJSON.get ("bbgparent");

		_strCountryOfIncorporation = mapJSON.get ("countryofincorporation");

		_strIndustrySector = mapJSON.get ("industrysector");

		_strIndustryGroup = mapJSON.get ("industrygroup");

		_strIndustrySubgroup = mapJSON.get ("industrysubgroup");

		_strCountryOfGuarantor = mapJSON.get ("countryofguarantor");

		_strCountryOfDomicile = mapJSON.get ("countryofdomicile");

		_strDescription = mapJSON.get ("description");

		_strSecurityType = mapJSON.get ("securitytype");

		_dtPrevCouponDate = org.drip.analytics.date.DateUtil.MakeJulianFromYYYYMMDD (mapJSON.get
			("prevcoupondate"), "-");

		_strBBGUniqueID = mapJSON.get ("bbguniqueid");

		_strLongCompanyName = mapJSON.get ("longcompanyname");

		_bIsStructuredNote = new java.lang.Boolean (mapJSON.get ("isstructurednote"));

		_bIsUnitTraded = new java.lang.Boolean (mapJSON.get ("isunittraded"));

		_bIsReversibleConvertible = new java.lang.Boolean (mapJSON.get ("isreversibleconvertible"));

		_strRedemptionCurrency = mapJSON.get ("redemptioncurrency");

		_strCouponCurrency = mapJSON.get ("couponcurrency");

		_strTradeCurrency = mapJSON.get ("tradecurrency");

		_bIsBearer = new java.lang.Boolean (mapJSON.get ("isbearer"));

		_bIsRegistered = new java.lang.Boolean (mapJSON.get ("isregistered"));

		_bHasBeenCalled = new java.lang.Boolean (mapJSON.get ("hasbeencalled"));

		_strIssuer = mapJSON.get ("issuer");

		_dtPenultimateCouponDate = org.drip.analytics.date.DateUtil.MakeJulianFromYYYYMMDD (mapJSON.get
			("penultimatecoupondate"), "-");

		_strFloatCouponConvention = mapJSON.get ("floatcouponconvention");

		_dblCurrentCoupon = new java.lang.Double (mapJSON.get ("currentcoupon"));

		_bIsFloater = new java.lang.Boolean (mapJSON.get ("isfloater"));

		_bTradeStatus = new java.lang.Boolean (mapJSON.get ("tradestatus"));

		_strCDRCountryCode = mapJSON.get ("cdrcountrycode");

		_strCDRSettleCode = mapJSON.get ("cdrsettlecode");

		_dtFinalMaturity = org.drip.analytics.date.DateUtil.MakeJulianFromYYYYMMDD (mapJSON.get
			("finalmaturitydate"), "-");

		_bIsPrivatePlacement = new java.lang.Boolean (mapJSON.get ("isprivateplacement"));

		_bIsPerpetual = new java.lang.Boolean (mapJSON.get ("isperpetual"));

		_bIsDefaulted = new java.lang.Boolean (mapJSON.get ("isdefaulted"));

		_dblFloatSpread = new java.lang.Double (mapJSON.get ("floatspread"));

		_strRateIndex = mapJSON.get ("rateindex");

		_strMoody = mapJSON.get ("moody");

		_strSnP = mapJSON.get ("snp");

		_strFitch = mapJSON.get ("fitch");

		_strSnrSub = mapJSON.get ("snrsub");

		_strIssuerSPN = mapJSON.get ("issuerspn");

		_dblIssuePrice = new java.lang.Double (mapJSON.get ("issueprice"));

		_dblCoupon = new java.lang.Double (mapJSON.get ("coupon"));

		_dtMaturity = org.drip.analytics.date.DateUtil.MakeJulianFromYYYYMMDD (mapJSON.get ("maturitydate"),
			"-");
	}

	/**
	 * Set the ISIN
	 * 
	 * @param strISIN ISIN
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setISIN (
		final java.lang.String strISIN)
	{
		if (null == strISIN || strISIN.isEmpty()) return false;

		_strISIN = strISIN;
		return true;
	}

	/**
	 * Set the CUSIP
	 * 
	 * @param strCUSIP CUSIP
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setCUSIP (
		final java.lang.String strCUSIP)
	{
		if (null == strCUSIP || strCUSIP.isEmpty()) return false;

		_strCUSIP = strCUSIP;
		return true;
	}

	/**
	 * Set the Bloomberg ID
	 * 
	 * @param strBBGID Bloomberg ID String
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setBBGID (
		final java.lang.String strBBGID)
	{
		if (null == (_strBBGID = strBBGID)) _strBBGID = "";

		return true;
	}

	/**
	 * Set the Issuer Category
	 * 
	 * @param strIssuerCategory Issuer Category
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIssuerCategory (
		final java.lang.String strIssuerCategory)
	{
		if (null == (_strIssuerCategory = strIssuerCategory)) _strIssuerCategory = "";

		return true;
	}

	/**
	 * Set the Issuer Ticker
	 * 
	 * @param strTicker Ticker
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setTicker (
		final java.lang.String strTicker)
	{
		if (null == (_strTicker = strTicker)) _strTicker = "";

		return true;
	}

	/**
	 * Set the Issuer Series
	 * 
	 * @param strSeries series
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setSeries (
		final java.lang.String strSeries)
	{
		if (null == (_strSeries = strSeries)) _strSeries = "";

		return true;
	}

	/**
	 * Set the Issuer Name
	 * 
	 * @param strName Name
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setName (
		final java.lang.String strName)
	{
		if (null == (_strName = strName)) _strName = "";

		return true;
	}

	/**
	 * Set the Issuer Short Name
	 * 
	 * @param strShortName Short Name
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setShortName (
		final java.lang.String strShortName)
	{
		if (null == (_strShortName = strShortName)) _strShortName = "";

		return true;
	}

	/**
	 * Set the Issuer Industry
	 * 
	 * @param strIssuerIndustry Issuer Industry
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIssuerIndustry (
		final java.lang.String strIssuerIndustry)
	{
		if (null == (_strIssuerIndustry = strIssuerIndustry)) _strIssuerIndustry = "";

		return true;
	}

	/**
	 * Set the Coupon Type
	 * 
	 * @param strCouponType Coupon Type
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setCouponType (
		final java.lang.String strCouponType)
	{
		if (null == (_strCouponType = strCouponType)) _strCouponType = "";

		return true;
	}

	/**
	 * Set the Maturity Type
	 * 
	 * @param strMaturityType Maturity Type
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setMaturityType (
		final java.lang.String strMaturityType)
	{
		if (null == (_strMaturityType = strMaturityType)) _strMaturityType = "";

		return true;
	}

	/**
	 * Set the Calculation Type
	 * 
	 * @param strCalculationType Calculation Type
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setCalculationType (
		final java.lang.String strCalculationType)
	{
		if (null == (_strCalculationType = strCalculationType)) _strCalculationType = "";

		return true;
	}

	/**
	 * Set the Day Count Code
	 * 
	 * @param strDayCountCode Day Count Code
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setDayCountCode (
		final java.lang.String strDayCountCode)
	{
		_strDayCountCode = "Unknown DC";

		try {
			_strDayCountCode = org.drip.analytics.support.Helper.ParseFromBBGDCCode
				(strDayCountCode);
		} catch (java.lang.Exception e) {
			if (m_bBlog)
				System.out.println ("Bad dayCount " + strDayCountCode + " for ISIN " +
					_strISIN);

			return false;
		}

		return true;
	}

	/**
	 * Set the Market Issue Type
	 * 
	 * @param strMarketIssueType Market Issue Type
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setMarketIssueType (
		final java.lang.String strMarketIssueType)
	{
		if (null == (_strMarketIssueType = strMarketIssueType)) _strMarketIssueType = "";

		return true;
	}

	/**
	 * Set the Issue Country Code
	 * 
	 * @param strIssueCountryCode Issue Country Code
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIssueCountryCode (
		final java.lang.String strIssueCountryCode)
	{
		if (null == (_strIssueCountryCode = strIssueCountryCode)) _strIssueCountryCode = "";

		return true;
	}

	/**
	 * Set the Issue Country
	 * 
	 * @param strIssueCountry Issue Country
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIssueCountry (
		final java.lang.String strIssueCountry)
	{
		if (null == (_strIssueCountry = strIssueCountry)) _strIssueCountry = "";

		return true;
	}

	/**
	 * Set the Collateral Type
	 * 
	 * @param strCollateralType Collateral Type
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setCollateralType (
		final java.lang.String strCollateralType)
	{
		if (null == (_strCollateralType = strCollateralType)) _strCollateralType = "";

		return true;
	}

	/**
	 * Set the Issue Amount
	 * 
	 * @param strIssueAmount Issue Amount
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIssueAmount (
		final java.lang.String strIssueAmount)
	{
		try {
			_dblIssueAmount = new java.lang.Double (strIssueAmount.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog) System.out.println ("Bad Issue Amount " + strIssueAmount + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set the Outstanding Amount
	 * 
	 * @param strOutstandingAmount Outstanding Amount
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setOutstandingAmount (
		final java.lang.String strOutstandingAmount)
	{
		try {
			_dblOutstandingAmount = new java.lang.Double (strOutstandingAmount.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog)
				System.out.println ("Bad Outstanding Amount " + strOutstandingAmount + " for ISIN " +
					_strISIN);
		}

		return false;
	}

	/**
	 * Set the Minimum Piece
	 * 
	 * @param strMinimumPiece Minimum Piece
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setMinimumPiece (
		final java.lang.String strMinimumPiece)
	{
		try {
			_dblMinimumPiece = new java.lang.Double (strMinimumPiece.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog)
				System.out.println ("Bad Minimum Piece " + strMinimumPiece + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set the Minimum Increment
	 * 
	 * @param strMinimumIncrement Minimum Increment
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setMinimumIncrement (
		final java.lang.String strMinimumIncrement)
	{
		try {
			_dblMinimumIncrement = new java.lang.Double (strMinimumIncrement.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog)
				System.out.println ("Bad Minimum Increment " + strMinimumIncrement + " for ISIN " +
					_strISIN);
		}

		return false;
	}

	/**
	 * Set the Par Amount
	 * 
	 * @param strParAmount Par Amount
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setParAmount (
		final java.lang.String strParAmount)
	{
		try {
			_dblParAmount = new java.lang.Double (strParAmount.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog) System.out.println ("Bad Par Amount " + strParAmount + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set the Lead Manager
	 * 
	 * @param strLeadManager Lead Manager
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setLeadManager (
		final java.lang.String strLeadManager)
	{
		if (null == (_strLeadManager = strLeadManager)) _strLeadManager = "";

		return true;
	}

	/**
	 * Set the Exchange Code
	 * 
	 * @param strExchangeCode Exchange Code
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setExchangeCode (
		final java.lang.String strExchangeCode)
	{
		if (null == (_strExchangeCode = strExchangeCode)) _strExchangeCode = "";

		return true;
	}

	/**
	 * Set the Redemption Value
	 * 
	 * @param strRedemptionValue Redemption Value
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setRedemptionValue (
		final java.lang.String strRedemptionValue)
	{
		try {
			_dblRedemptionValue = new java.lang.Double (strRedemptionValue.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog)
				System.out.println ("Bad Redemption Value " + strRedemptionValue + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set the Announce Date
	 * 
	 * @param strAnnounce Announce Date String
	 * 
	 * @return True (success), false (failure)
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
	 * Set the First Settle
	 * 
	 * @param strFirstSettle First Settle
	 * 
	 * @return True (success), false (failure)
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
	 * Set the First Coupon
	 * 
	 * @param strFirstCoupon First Coupon
	 * 
	 * @return True (success), false (failure)
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
	 * Set the Interest Accrual Start Date
	 * 
	 * @param strInterestAccrualStart Interest Accrual Start Date
	 * 
	 * @return True (success), false (failure)
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
	 * Set the Issue Date
	 * 
	 * @param strIssue Issue Date
	 * 
	 * @return True (success), false (failure)
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
	 * Set the Next Coupon Date
	 * 
	 * @param strNextCouponDate Next Coupon Date
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setNextCouponDate (
		final java.lang.String strNextCouponDate)
	{
		try {
			_dtNextCouponDate = org.drip.analytics.date.DateUtil.MakeJulianDateFromBBGDate
				(strNextCouponDate.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog)
				System.out.println ("Bad Next Coupon Date " + strNextCouponDate + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set whether is Callable
	 * 
	 * @param strCallable Callable?
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIsCallable (
		final java.lang.String strCallable)
	{
		if (null == strCallable) _bIsCallable = false;

		if ("1".equalsIgnoreCase (strCallable))
			_bIsCallable = true;
		else
			_bIsCallable = false;

		return true;
	}

	/**
	 * Set whether is Putable
	 * 
	 * @param strPutable Putable?
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIsPutable (
		final java.lang.String strPutable)
	{
		if (null == strPutable) _bIsPutable = false;

		if ("1".equalsIgnoreCase (strPutable))
			_bIsPutable = true;
		else
			_bIsPutable = false;

		return true;
	}

	/**
	 * Set whether is Sinkable
	 * 
	 * @param strSinkable Sinkable?
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIsSinkable (
		final java.lang.String strSinkable)
	{
		if (null == strSinkable) _bIsSinkable = false;

		if ("1".equalsIgnoreCase (strSinkable))
			_bIsSinkable = true;
		else
			_bIsSinkable = false;

		return true;
	}

	/**
	 * Set the Bloomberg Parent
	 * 
	 * @param strBBGParent Bloomberg Parent?
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setBBGParent (
		final java.lang.String strBBGParent)
	{
		if (null == (_strBBGParent = strBBGParent)) _strBBGParent = "";

		return true;
	}

	/**
	 * Set the Country Of Incorporation
	 * 
	 * @param strCountryOfIncorporation Country Of Incorporation
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setCountryOfIncorporation (
		final java.lang.String strCountryOfIncorporation)
	{
		if (null == (_strCountryOfIncorporation = strCountryOfIncorporation))
			_strCountryOfIncorporation = "";

		return true;
	}

	/**
	 * Set the Industry Sector
	 * 
	 * @param strIndustrySector Industry Sector
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIndustrySector (
		final java.lang.String strIndustrySector)
	{
		if (null == (_strIndustrySector = strIndustrySector)) _strIndustrySector = "";

		return true;
	}

	/**
	 * Set the Industry Group
	 * 
	 * @param strIndustryGroup Industry Group
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIndustryGroup (
		final java.lang.String strIndustryGroup)
	{
		if (null == (_strIndustryGroup = strIndustryGroup)) _strIndustryGroup = "";

		return true;
	}

	/**
	 * Set the Industry Subgroup
	 * 
	 * @param strIndustrySubgroup Industry Subgroup
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIndustrySubgroup (
		final java.lang.String strIndustrySubgroup)
	{
		if (null == (_strIndustrySubgroup = strIndustrySubgroup)) _strIndustrySubgroup = "";

		return true;
	}

	/**
	 * Set the Country Of Guarantor
	 * 
	 * @param strCountryOfGuarantor Country Of Guarantor
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setCountryOfGuarantor (
		final java.lang.String strCountryOfGuarantor)
	{
		if (null == (_strCountryOfGuarantor = strCountryOfGuarantor)) _strCountryOfGuarantor = "";

		return true;
	}

	/**
	 * Set the Country Of Domicile
	 * 
	 * @param strCountryOfDomicile Country Of Domicile
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setCountryOfDomicile (
		final java.lang.String strCountryOfDomicile)
	{
		if (null == (_strCountryOfDomicile = strCountryOfDomicile)) _strCountryOfDomicile = "";

		return true;
	}

	/**
	 * Set the Description
	 * 
	 * @param strDescription Description
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setDescription (
		final java.lang.String strDescription)
	{
		if (null == (_strDescription = strDescription)) _strDescription = "";

		return true;
	}

	/**
	 * Set the Security Type
	 * 
	 * @param strSecurityType Security Type
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setSecurityType (
		final java.lang.String strSecurityType)
	{
		if (null == (_strSecurityType = strSecurityType)) _strSecurityType = "";

		return true;
	}

	/**
	 * Set the Previous Coupon Date
	 * 
	 * @param strPrevCouponDate Previous Coupon Date
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setPrevCouponDate (
		final java.lang.String strPrevCouponDate)
	{
		try {
			_dtPrevCouponDate = org.drip.analytics.date.DateUtil.MakeJulianDateFromBBGDate
				(strPrevCouponDate.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog)
				System.out.println ("Bad Prev Coupon Date " + strPrevCouponDate + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set the Unique Bloomberg ID
	 * 
	 * @param strBBGUniqueID BBGUniqueID
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setBBGUniqueID (
		final java.lang.String strBBGUniqueID)
	{
		if (null == (_strBBGUniqueID = strBBGUniqueID)) _strBBGUniqueID = "";

		return true;
	}

	/**
	 * Set the Long Company Name
	 * 
	 * @param strLongCompanyName Long Company Name
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setLongCompanyName (
		final java.lang.String strLongCompanyName)
	{
		if (null == (_strLongCompanyName = strLongCompanyName)) _strLongCompanyName = "";

		return true;
	}

	/**
	 * Set the Flag indicating Structured Note
	 * 
	 * @param strIsStructuredNote Flag indicating Structured Note
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIsStructuredNote (
		final java.lang.String strIsStructuredNote)
	{
		if (null == strIsStructuredNote) _bIsStructuredNote = false;

		if ("1".equalsIgnoreCase (strIsStructuredNote))
			_bIsStructuredNote = true;
		else
			_bIsStructuredNote = false;

		return true;
	}

	/**
	 * Set the Flag indicating Unit Traded
	 * 
	 * @param strIsUnitTraded Flag indicating Unit Traded
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIsUnitTraded (
		final java.lang.String strIsUnitTraded)
	{
		if (null == strIsUnitTraded) _bIsUnitTraded = false;

		if ("1".equalsIgnoreCase (strIsUnitTraded))
			_bIsUnitTraded = true;
		else
			_bIsUnitTraded = false;

		return true;
	}

	/**
	 * Set the Flag indicating Reverse Convertible
	 * 
	 * @param strIsReversibleConvertible Flag indicating Reverse Convertible
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIsReversibleConvertible (
		final java.lang.String strIsReversibleConvertible)
	{
		if (null == strIsReversibleConvertible) _bIsReversibleConvertible = false;

		if ("1".equalsIgnoreCase (strIsReversibleConvertible))
			_bIsReversibleConvertible = true;
		else
			_bIsReversibleConvertible = false;

		return true;
	}

	/**
	 * Set the Redemption Currency
	 * 
	 * @param strRedemptionCurrency Redemption Currency
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setRedemptionCurrency (
		final java.lang.String strRedemptionCurrency)
	{
		if (null == (_strRedemptionCurrency = strRedemptionCurrency)) return false;

		return true;
	}

	/**
	 * Set the Coupon Currency
	 * 
	 * @param strCouponCurrency Coupon Currency
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setCouponCurrency (
		final java.lang.String strCouponCurrency)
	{
		if (null == (_strCouponCurrency = strCouponCurrency)) return false;

		return true;
	}

	/**
	 * Set the Trade Currency
	 * 
	 * @param strTradeCurrency Trade Currency
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setTradeCurrency (
		final java.lang.String strTradeCurrency)
	{
		if (null == (_strTradeCurrency = strTradeCurrency)) return false;

		return true;
	}

	/**
	 * Set the Flag indicating Bearer Bond
	 * 
	 * @param strIsBearer Flag indicating Bearer Bond
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIsBearer (
		final java.lang.String strIsBearer)
	{
		if (null == strIsBearer) _bIsBearer = false;

		if ("1".equalsIgnoreCase (strIsBearer))
			_bIsBearer = true;
		else
			_bIsBearer = false;

		return true;
	}

	/**
	 * Set the Flag Registered
	 * 
	 * @param strIsRegistered Flag indicating Is Registered
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIsRegistered (
		final java.lang.String strIsRegistered)
	{
		if (null == strIsRegistered) _bIsRegistered = false;

		if ("1".equalsIgnoreCase (strIsRegistered))
			_bIsRegistered = true;
		else
			_bIsRegistered = false;

		return true;
	}

	/**
	 * Set the Flag indicating If bond has been called
	 * 
	 * @param strHasBeenCalled Flag indicating If bond has been called
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setHasBeenCalled (
		final java.lang.String strHasBeenCalled)
	{
		if (null == strHasBeenCalled) _bHasBeenCalled = false;

		if ("1".equalsIgnoreCase (strHasBeenCalled))
			_bHasBeenCalled = true;
		else
			_bHasBeenCalled = false;

		return true;
	}

	/**
	 * Set the Issuer
	 * 
	 * @param strIssuer Issuer Name
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIssuer (
		final java.lang.String strIssuer)
	{
		if (null == (_strIssuer = strIssuer)) _strIssuer = "";

		return true;
	}

	/**
	 * Set the Penultimate Coupon Date
	 * 
	 * @param strPenultimateCouponDate setPenultimateCouponDate
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setPenultimateCouponDate (
		final java.lang.String strPenultimateCouponDate)
	{
		try {
			_dtPenultimateCouponDate = org.drip.analytics.date.DateUtil.MakeJulianDateFromBBGDate
				(strPenultimateCouponDate.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog)
				System.out.println ("Bad Penultimate Coupon Date " + strPenultimateCouponDate + " for ISIN "
					+ _strISIN);
		}

		return false;
	}

	/**
	 * Set the Float Coupon Convention
	 * 
	 * @param strFloatCouponConvention Float Coupon Convention
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setFloatCouponConvention (
		final java.lang.String strFloatCouponConvention)
	{
		if (null == (_strFloatCouponConvention = strFloatCouponConvention)) _strFloatCouponConvention = "";

		return true;
	}

	/**
	 * Set the Current Coupon
	 * 
	 * @param strCurrentCoupon Current Coupon
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setCurrentCoupon (
		final java.lang.String strCurrentCoupon)
	{
		if (null == strCurrentCoupon || strCurrentCoupon.isEmpty() || "null".equalsIgnoreCase
			(strCurrentCoupon))
			_dblCurrentCoupon = 0.;
		else {
			try {
				_dblCurrentCoupon = new java.lang.Double (strCurrentCoupon.trim()).doubleValue();

				return true;
			} catch (java.lang.Exception e) {
				if (m_bBlog)
					System.out.println ("Bad Current Coupon " + strCurrentCoupon + " for ISIN " + _strISIN);
			}
		}

		return false;
	}

	/**
	 * Set the Floater Flag
	 * 
	 * @param strIsFloater Flag indicating Is Floater
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIsFloater (
		final java.lang.String strIsFloater)
	{
		if (null == strIsFloater) _bIsFloater = false;

		if ("1".equalsIgnoreCase (strIsFloater))
			_bIsFloater = true;
		else
			_bIsFloater = false;

		return true;
	}

	/**
	 * Set Trade Status
	 * 
	 * @param strTradeStatus Trade Status
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setTradeStatus (
		final java.lang.String strTradeStatus)
	{
		if (null == strTradeStatus) _bTradeStatus = false;

		if ("1".equalsIgnoreCase (strTradeStatus))
			_bTradeStatus = true;
		else
			_bTradeStatus = false;

		return true;
	}

	/**
	 * Set the CDR Country Code
	 * 
	 * @param strCDRCountryCode CDR Country Code
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setCDRCountryCode (
		final java.lang.String strCDRCountryCode)
	{
		if (null == (_strCDRCountryCode = strCDRCountryCode)) _strCDRCountryCode = "";

		return true;
	}

	/**
	 * Set the CDR Settle Code
	 * 
	 * @param strCDRSettleCode CDR Settle Code
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setCDRSettleCode (
		final java.lang.String strCDRSettleCode)
	{
		if (null == (_strCDRSettleCode = strCDRSettleCode)) _strCDRSettleCode = "";

		return true;
	}

	/**
	 * Set the Final Maturity
	 * 
	 * @param strFinalMaturity Final Maturity
	 * 
	 * @return True (success), false (failure)
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
	 * Set the Private Placement Flag
	 * 
	 * @param strIsPrivatePlacement Flag indicating Is Private Placement
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIsPrivatePlacement (
		final java.lang.String strIsPrivatePlacement)
	{
		if (null == strIsPrivatePlacement) _bIsPrivatePlacement = false;

		if ("1".equalsIgnoreCase (strIsPrivatePlacement))
			_bIsPrivatePlacement = true;
		else
			_bIsPrivatePlacement = false;

		return true;
	}

	/**
	 * Set the Perpetual Flag
	 * 
	 * @param strIsPerpetual Flag indicating Is Perpetual
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIsPerpetual (
		final java.lang.String strIsPerpetual)
	{
		if (null == strIsPerpetual) _bIsPerpetual = false;

		if ("1".equalsIgnoreCase (strIsPerpetual))
			_bIsPerpetual = true;
		else
			_bIsPerpetual = false;

		return true;
	}

	/**
	 * Set the Defaulted Flag
	 * 
	 * @param strIsDefaulted Flag indicating Is Defaulted
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIsDefaulted (
		final java.lang.String strIsDefaulted)
	{
		if (null == strIsDefaulted) _bIsDefaulted = false;

		if ("1".equalsIgnoreCase (strIsDefaulted))
			_bIsDefaulted = true;
		else
			_bIsDefaulted = false;

		return true;
	}

	/**
	 * Set the Float Spread
	 * 
	 * @param strFloatSpread Float Spread
	 * 
	 * @return True (success), false (failure)
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
	 * Set the Rate Index
	 * 
	 * @param strRateIndex Rate Index
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setRateIndex (
		final java.lang.String strRateIndex)
	{
		if (null == (_strRateIndex = strRateIndex)) _strRateIndex = "";

		return true;
	}

	/**
	 * Set the Moodys Rating
	 * 
	 * @param strMoody Moodys Rating
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setMoody (
		final java.lang.String strMoody)
	{
		if (null == (_strMoody = strMoody)) _strMoody = "";

		return true;
	}

	/**
	 * Set the SnP Rating
	 * 
	 * @param strSnP SnP Rating
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setSnP (
		final java.lang.String strSnP)
	{
		if (null == (_strSnP = strSnP)) _strSnP = "";

		return true;
	}

	/**
	 * Set the Fitch Rating
	 * 
	 * @param strFitch Fitch Rating
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setFitch (
		final java.lang.String strFitch)
	{
		if (null == (_strFitch = strFitch)) _strFitch = "";

		return true;
	}

	/**
	 * Set Senior or Sub-ordinate
	 * 
	 * @param strSnrSub Senior or Sub-ordinate
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setSnrSub (
		final java.lang.String strSnrSub)
	{
		if (null == (_strSnrSub = strSnrSub)) _strSnrSub = "";

		return true;
	}

	/**
	 * Set Issuer SPN
	 * 
	 * @param strIssuerSPN Issuer SPN
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIssuerSPN (
		final java.lang.String strIssuerSPN)
	{
		if (null == (_strIssuerSPN = strIssuerSPN)) _strIssuerSPN = "";

		return true;
	}

	/**
	 * Set Issue Price
	 * 
	 * @param strIssuePrice Issue Price
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setIssuePrice (
		final java.lang.String strIssuePrice)
	{
		try {
			_dblIssuePrice = new java.lang.Double (strIssuePrice.trim()).doubleValue();

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog) System.out.println ("Bad Issue Price " + strIssuePrice + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set the coupon
	 * 
	 * @param strCoupon Coupon
	 * 
	 * @return True (success), false (failure)
	 */

	public boolean setCoupon (
		final java.lang.String strCoupon)
	{
		if (null == strCoupon || strCoupon.isEmpty() || "null".equalsIgnoreCase (strCoupon)) _dblCoupon = 0.;

		try {
			_dblCoupon = new java.lang.Double (strCoupon.trim());

			return true;
		} catch (java.lang.Exception e) {
			if (m_bBlog) System.out.println ("Bad coupon " + strCoupon + " for ISIN " + _strISIN);
		}

		return false;
	}

	/**
	 * Set the maturity
	 * 
	 * @param strMaturity maturity
	 * 
	 * @return True (success), false (failure)
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

	@Override public boolean validate()
	{
		if (null == _strISIN || _strISIN.isEmpty() || null == _strCUSIP || _strCUSIP.isEmpty()) {
			if (m_bDisplayWarnings)
				System.out.println ("Check ISIN[" + _strISIN + "] or CUSIP[" + _strCUSIP + "]");

			return false;
		}

		if (null == _dtInterestAccrualStart) {
			if (null == (_dtInterestAccrualStart = reconcileStartDate())) {
				if (m_bDisplayWarnings)
					System.out.println ("All possible date init candidates are null for ISIN " + _strISIN);

				return false;
			}
		}

		if (null == _dtFirstCoupon) _dtFirstCoupon = reconcileStartDate();

		if (null == _dtIssue) _dtIssue = reconcileStartDate();

		if (null == _dtFirstSettle) _dtFirstSettle = reconcileStartDate();

		if (null == _dtAnnounce) _dtAnnounce = reconcileStartDate();

		return true;
	}

	/**
	 * Create an SQL Insert string for the given object
	 * 
	 * @return SQL Insert string
	 */

	public java.lang.String makeSQLInsert()
	{
		java.lang.StringBuilder sb = new java.lang.StringBuilder();

		sb.append ("insert into BondRefData values(");

		sb.append ("'").append (_strISIN).append ("', ");

		sb.append ("'").append (_strCUSIP).append ("', ");

		sb.append ("'").append (_strBBGID).append ("', ");

		sb.append ("'").append (_strIssuerCategory).append ("', ");

		sb.append ("'").append (_strTicker).append ("', ");

		sb.append ("'").append (_strSeries).append ("', ");

		sb.append ("'").append (_strName).append ("', ");

		sb.append ("'").append (_strShortName).append ("', ");

		sb.append ("'").append (_strIssuerIndustry).append ("', ");

		sb.append ("'").append (_strCouponType).append ("', ");

		sb.append ("'").append (_strMaturityType).append ("', ");

		sb.append ("'").append (_strCalculationType).append ("', ");

		sb.append ("'").append (_strDayCountCode).append ("', ");

		sb.append ("'").append (_strMarketIssueType).append ("', ");

		sb.append ("'").append (_strIssueCountryCode).append ("', ");

		sb.append ("'").append (_strIssueCountry).append ("', ");

		sb.append ("'").append (_strCollateralType).append ("', ");

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblIssueAmount))
			sb.append ("null, ");
		else
			sb.append (_dblIssueAmount).append (", ");

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblOutstandingAmount))
			sb.append ("null, ");
		else
			sb.append (_dblOutstandingAmount).append (", ");

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblMinimumPiece))
			sb.append ("null, ");
		else
			sb.append (_dblMinimumPiece).append (", ");

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblMinimumIncrement))
			sb.append ("null, ");
		else
			sb.append (_dblMinimumIncrement).append (", ");

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblParAmount))
			sb.append ("null, ");
		else
			sb.append (_dblParAmount).append (", ");

		sb.append ("'").append (_strLeadManager).append ("', ");

		sb.append ("'").append (_strExchangeCode).append ("', ");

		sb.append (_dblRedemptionValue).append (", ");

		sb.append ("'").append (_dtAnnounce.toOracleDate()).append ("', ");

		sb.append ("'").append (_dtFirstSettle.toOracleDate()).append ("', ");

		sb.append ("'").append (_dtFirstCoupon.toOracleDate()).append ("', ");

		sb.append ("'").append (_dtInterestAccrualStart.toOracleDate()).append ("', ");

		sb.append ("'").append (_dtIssue.toOracleDate()).append ("', ");

		if (null == _dtNextCouponDate)
			sb.append ("null, ");
		else
			sb.append ("'").append (_dtNextCouponDate.toOracleDate()).append ("', ");

		sb.append ("'").append (_bIsCallable ? 1 : 0).append ("', ");

		sb.append ("'").append (_bIsPutable ? 1 : 0).append ("', ");

		sb.append ("'").append (_bIsSinkable ? 1 : 0).append ("', ");

		sb.append ("'").append (_strBBGParent).append ("', "); // Done

		sb.append ("'").append (_strCountryOfIncorporation).append ("', ");

		sb.append ("'").append (_strIndustrySector).append ("', ");

		sb.append ("'").append (_strIndustryGroup).append ("', ");

		sb.append ("'").append (_strIndustrySubgroup).append ("', ");

		sb.append ("'").append (_strCountryOfGuarantor).append ("', ");

		sb.append ("'").append (_strCountryOfDomicile).append ("', ");

		sb.append ("'").append (_strDescription).append ("', ");

		sb.append ("'").append (_strSecurityType).append ("', ");

		if (null == _dtPrevCouponDate)
			sb.append ("null, ");
		else
			sb.append ("'").append (_dtPrevCouponDate.toOracleDate()).append ("', ");

		sb.append ("'").append (_strBBGUniqueID).append ("', ");

		sb.append ("'").append (_strLongCompanyName).append ("', ");

		sb.append ("'").append (_strRedemptionCurrency).append ("', ");

		sb.append ("'").append (_strCouponCurrency).append ("', ");

		sb.append ("'").append (_bIsStructuredNote ? 1 : 0).append ("', ");

		sb.append ("'").append (_bIsUnitTraded ? 1 : 0).append ("', ");

		sb.append ("'").append (_bIsReversibleConvertible ? 1 : 0).append ("', ");

		sb.append ("'").append (_strTradeCurrency).append ("', ");

		sb.append ("'").append (_bIsBearer ? 1 : 0).append ("', ");

		sb.append ("'").append (_bIsRegistered ? 1 : 0).append ("', ");

		sb.append ("'").append (_bHasBeenCalled ? 1 : 0).append ("', ");

		sb.append ("'").append (_strIssuer).append ("', ");

		if (null == _dtPenultimateCouponDate)
			sb.append ("null, ");
		else
			sb.append ("'").append (_dtPenultimateCouponDate.toOracleDate()).append ("', ");

		sb.append ("'").append (_strFloatCouponConvention).append ("', ");

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblCurrentCoupon))
			sb.append ("null, ");
		else
			sb.append (_dblCurrentCoupon).append (", ");

		sb.append ("'").append (_bIsFloater ? 1 : 0).append ("', ");

		sb.append ("'").append (_bTradeStatus ? 1 : 0).append ("', ");

		sb.append ("'").append (_strCDRCountryCode).append ("', ");

		sb.append ("'").append (_strCDRSettleCode).append ("', ");

		if (null == _dtFinalMaturity)
			sb.append ("null, ");
		else
			sb.append ("'").append (_dtFinalMaturity.toOracleDate()).append ("', ");

		sb.append ("'").append (_bIsPrivatePlacement ? 1 : 0).append ("', ");

		sb.append ("'").append (_bIsPerpetual ? 1 : 0).append ("', ");

		sb.append ("'").append (_bIsDefaulted ? 1 : 0).append ("', ");

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblFloatSpread))
			sb.append ("null, ");
		else
			sb.append (_dblFloatSpread).append (", ");

		sb.append ("'").append (_strRateIndex).append ("', ");

		sb.append ("'").append (_strMoody).append ("', ");

		sb.append ("'").append (_strSnP).append ("', ");

		sb.append ("'").append (_strFitch).append ("', ");

		sb.append ("'").append (_strSnrSub).append ("', ");

		sb.append ("'").append (_strIssuerSPN).append ("', ");

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblIssuePrice))
			sb.append ("null, ");
		else
			sb.append (_dblIssuePrice).append (", ");

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblCoupon))
			sb.append ("null, ");
		else
			sb.append (_dblCoupon).append (", ");

		if (null == _dtMaturity)
			sb.append ("null");
		else
			sb.append ("'").append (_dtMaturity.toOracleDate()).append ("'");

		return sb.append (")").toString();
	}

	/**
	 * Create an SQL Delete string for the given object
	 * 
	 * @return SQL Delete string
	 */

	public java.lang.String makeSQLDelete()
	{
		java.lang.StringBuilder sb = new java.lang.StringBuilder();

		sb.append ("delete from BondRefData where ISIN = '").append (_strISIN).append
			("' or CUSIP = '").append (_strCUSIP).append ("'");

		return sb.toString();
	}

	public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> toJSON()
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> mapJSON = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String>();

		mapJSON.put ("version", "" + org.drip.quant.common.StringUtil.VERSION);

		if (null == _strISIN || _strISIN.isEmpty())
			mapJSON.put ("isin", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("isin", _strISIN);

		if (null == _strCUSIP || _strCUSIP.isEmpty())
			mapJSON.put ("cusip", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("cusip", _strCUSIP);

		if (null == _strBBGID || _strBBGID.isEmpty())
			mapJSON.put ("bbgid", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("bbgid", _strBBGID);

		if (null == _strIssuerCategory || _strIssuerCategory.isEmpty())
			mapJSON.put ("issuercategory", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("issuercategory", _strIssuerCategory);

		if (null == _strTicker || _strTicker.isEmpty())
			mapJSON.put ("ticker", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("ticker", _strTicker);

		if (null == _strSeries || _strSeries.isEmpty())
			mapJSON.put ("series", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("series", _strSeries);

		if (null == _strName || _strName.isEmpty())
			mapJSON.put ("name", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("name", _strName);

		if (null == _strShortName || _strShortName.isEmpty())
			mapJSON.put ("shortname", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("shortname", _strShortName);

		if (null == _strIssuerIndustry || _strIssuerIndustry.isEmpty())
			mapJSON.put ("issuerindustry", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("issuerindustry", _strIssuerIndustry);

		if (null == _strCouponType || _strCouponType.isEmpty())
			mapJSON.put ("coupontype", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("coupontype", _strCouponType);

		if (null == _strMaturityType || _strMaturityType.isEmpty())
			mapJSON.put ("maturitytype", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("maturitytype", _strMaturityType);

		if (null == _strCalculationType || _strCalculationType.isEmpty())
			mapJSON.put ("calculationtype", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("calculationtype", _strCalculationType);

		if (null == _strDayCountCode || _strDayCountCode.isEmpty())
			mapJSON.put ("daycountcode", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("daycountcode", _strDayCountCode);

		if (null == _strMarketIssueType || _strMarketIssueType.isEmpty())
			mapJSON.put ("marketissuetype", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("marketissuetype", _strMarketIssueType);

		if (null == _strIssueCountryCode || _strIssueCountryCode.isEmpty())
			mapJSON.put ("issuecountrycode", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("issuecountrycode", _strIssueCountryCode);

		if (null == _strIssueCountry || _strIssueCountry.isEmpty())
			mapJSON.put ("issuecountry", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("issuecountry", _strIssueCountry);

		if (null == _strCollateralType || _strCollateralType.isEmpty())
			mapJSON.put ("collateraltype", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("collateraltype", _strCollateralType);

		mapJSON.put ("issueamount", "" + _dblIssueAmount);

		mapJSON.put ("outstandingamount", "" + _dblOutstandingAmount);

		mapJSON.put ("minimumpiece", "" + _dblMinimumPiece);

		mapJSON.put ("minimumincrement", "" + _dblMinimumIncrement);

		mapJSON.put ("paramount", "" + _dblParAmount);

		if (null == _strLeadManager || _strLeadManager.isEmpty())
			mapJSON.put ("leadmanager", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("leadmanager", _strLeadManager);

		if (null == _strExchangeCode || _strExchangeCode.isEmpty())
			mapJSON.put ("exchangecode", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("exchangecode", _strExchangeCode);

		mapJSON.put ("redemptionvalue", "" + _dblRedemptionValue);

		if (null == _dtAnnounce)
			mapJSON.put ("announcedate", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("announcedate", _dtAnnounce.toYYYYMMDD ("-"));

		if (null == _dtFirstSettle)
			mapJSON.put ("firstsettledate", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("firstsettledate", _dtFirstSettle.toYYYYMMDD ("-"));

		if (null == _dtFirstCoupon)
			mapJSON.put ("firstcoupondate", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("firstcoupondate", _dtFirstCoupon.toYYYYMMDD ("-"));

		if (null == _dtInterestAccrualStart)
			mapJSON.put ("interestaccrualstartdate", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("interestaccrualstartdate", _dtInterestAccrualStart.toYYYYMMDD ("-"));

		if (null == _dtIssue)
			mapJSON.put ("issuedate", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("issuedate", _dtIssue.toYYYYMMDD ("-"));

		if (null == _dtNextCouponDate)
			mapJSON.put ("nextcoupondate", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("nextcoupondate", _dtNextCouponDate.toYYYYMMDD ("-"));

		mapJSON.put ("iscallable", "" + _bIsCallable);

		mapJSON.put ("isputable", "" + _bIsPutable);

		mapJSON.put ("issinkable", "" + _bIsSinkable);

		if (null == _strBBGParent || _strBBGParent.isEmpty())
			mapJSON.put ("bbgparent", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("bbgparent", _strBBGParent);

		if (null == _strCountryOfIncorporation || _strCountryOfIncorporation.isEmpty())
			mapJSON.put ("countryofincorporation", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("countryofincorporation", _strCountryOfIncorporation);

		if (null == _strIndustrySector || _strIndustrySector.isEmpty())
			mapJSON.put ("industrysector", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("industrysector", _strIndustrySector);

		if (null == _strIndustryGroup || _strIndustryGroup.isEmpty())
			mapJSON.put ("industrygroup", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("industrygroup", _strIndustryGroup);

		if (null == _strIndustrySubgroup || _strIndustrySubgroup.isEmpty())
			mapJSON.put ("industrysubgroup", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("industrysubgroup", _strIndustrySubgroup);

		if (null == _strCountryOfGuarantor || _strCountryOfGuarantor.isEmpty())
			mapJSON.put ("countryofguarantor", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("countryofguarantor", _strCountryOfGuarantor);

		if (null == _strCountryOfDomicile || _strCountryOfDomicile.isEmpty())
			mapJSON.put ("countryofdomicile", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("countryofdomicile", _strCountryOfDomicile);

		if (null == _strDescription || _strDescription.isEmpty())
			mapJSON.put ("description", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("description", _strDescription);

		if (null == _strSecurityType || _strSecurityType.isEmpty())
			mapJSON.put ("securitytype", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("securitytype", _strSecurityType);

		if (null == _dtPrevCouponDate)
			mapJSON.put ("prevcoupondate", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("prevcoupondate", _dtPrevCouponDate.toYYYYMMDD ("-"));

		if (null == _strBBGUniqueID || _strBBGUniqueID.isEmpty())
			mapJSON.put ("bbguniqueid", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("bbguniqueid", _strBBGUniqueID);

		if (null == _strLongCompanyName || _strLongCompanyName.isEmpty())
			mapJSON.put ("longcompanyname", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("longcompanyname", _strLongCompanyName);

		mapJSON.put ("isstructurednote", "" + _bIsStructuredNote);

		mapJSON.put ("isunittraded", "" + _bIsUnitTraded);

		mapJSON.put ("isreversibleconvertible", "" + _bIsReversibleConvertible);

		if (null == _strRedemptionCurrency || _strRedemptionCurrency.isEmpty())
			mapJSON.put ("redemptioncurrency", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("redemptioncurrency", _strRedemptionCurrency);

		if (null == _strCouponCurrency || _strCouponCurrency.isEmpty())
			mapJSON.put ("couponcurrency", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("couponcurrency", _strCouponCurrency);

		if (null == _strTradeCurrency || _strTradeCurrency.isEmpty())
			mapJSON.put ("tradecurrency", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("tradecurrency", _strTradeCurrency);

		mapJSON.put ("isbearer", "" + _bIsBearer);

		mapJSON.put ("isregistered", "" + _bIsRegistered);

		mapJSON.put ("hasbeencalled", "" + _bHasBeenCalled);

		if (null == _strIssuer || _strIssuer.isEmpty())
			mapJSON.put ("issuer", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("issuer", _strIssuer);

		if (null == _dtPenultimateCouponDate)
			mapJSON.put ("penultimatecoupondate", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("penultimatecoupondate", _dtPenultimateCouponDate.toYYYYMMDD ("-"));

		if (null == _strFloatCouponConvention || _strFloatCouponConvention.isEmpty())
			mapJSON.put ("floatcouponconvention", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("floatcouponconvention", _strFloatCouponConvention);

		mapJSON.put ("currentcoupon", "" + _dblCurrentCoupon);

		mapJSON.put ("isfloater", "" + _bIsFloater);

		mapJSON.put ("tradestatus", "" + _bTradeStatus);

		if (null == _strCDRCountryCode || _strCDRCountryCode.isEmpty())
			mapJSON.put ("cdrcountrycode", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("cdrcountrycode", _strCDRCountryCode);

		if (null == _strCDRSettleCode || _strCDRSettleCode.isEmpty())
			mapJSON.put ("cdrsettlecode", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("cdrsettlecode", _strCDRSettleCode);

		if (null == _dtFinalMaturity)
			mapJSON.put ("finalmaturitydate", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("finalmaturitydate", _dtFinalMaturity.toYYYYMMDD ("-"));

		mapJSON.put ("isprivateplacement", "" + _bIsPrivatePlacement);

		mapJSON.put ("isperpetual", "" + _bIsPerpetual);

		mapJSON.put ("isdefaulted", "" + _bIsDefaulted);

		mapJSON.put ("floatspread", "" + _dblFloatSpread);

		if (null == _strRateIndex || _strRateIndex.isEmpty())
			mapJSON.put ("rateindex", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("rateindex", _strRateIndex);

		if (null == _strMoody || _strMoody.isEmpty())
			mapJSON.put ("moody", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("moody", _strMoody);

		if (null == _strSnP || _strSnP.isEmpty())
			mapJSON.put ("snp", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("snp", _strSnP);

		if (null == _strFitch || _strFitch.isEmpty())
			mapJSON.put ("fitch", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("fitch", _strFitch);

		if (null == _strSnrSub || _strSnrSub.isEmpty())
			mapJSON.put ("snrsub", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("snrsub", _strSnrSub);

		if (null == _strIssuerSPN || _strIssuerSPN.isEmpty())
			mapJSON.put ("issuerspn", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("issuerspn", _strIssuerSPN);

		mapJSON.put ("issueprice", "" + _dblIssuePrice);

		mapJSON.put ("coupon", "" + _dblCoupon);

		if (null == _dtMaturity)
			mapJSON.put ("maturitydate", org.drip.quant.common.StringUtil.NULL_SER_STRING);
		else
			mapJSON.put ("maturitydate", _dtMaturity.toYYYYMMDD ("-"));

		return mapJSON;
	}
}
