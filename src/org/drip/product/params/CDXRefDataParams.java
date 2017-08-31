
package org.drip.product.params;

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
 * CDXRefDataParams contains the complete set of reference data that corresponds to the contract of a
 *  standard CDX. It consists of the following category and fields:
 *  - Descriptive - Index Label, Index Name, Curve Name, Index Class, Index Group Name, Index Short Group
 *  	Name, Index Short Name, Short Name
 *  - Issuer ID - Curve ID, Red ID, Series, Version, Curvy Curve ID, Location, Bloomberg Ticker
 *  - Quote Details - Quote As CDS
 *  - Date - Issue Date, Maturity Date
 *  - Coupon Parameters - Coupon Rate, Currency, Day Count, Full First Stub, Frequency
 *  - Component Details - Original Count, Defaulted Count
 *  - Payoff Details - Knock-out on Default, Pay Accrued Amount, Recovery on Default
 *  - Other - Index Life Span, Index Factor
 *  
 *  It also exports serialization into and de-serialization out of byte arrays.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CDXRefDataParams {

	/**
	 * Index Curve ID
	 */

	public java.lang.String _strCurveID = "";

	/**
	 * Index Curve SPN
	 */

	public java.lang.String _strSPN = "";

	/**
	 * Index Label
	 */

	public java.lang.String _strIndexLabel = "";

	/**
	 * Index Name
	 */

	public java.lang.String _strIndexName = "";

	/**
	 * Index Curve Name
	 */

	public java.lang.String _strCurveName = "";

	/**
	 * Index Issue Date
	 */

	public org.drip.analytics.date.JulianDate _dtIssue = null;

	/**
	 * Index Maturity Date
	 */

	public org.drip.analytics.date.JulianDate _dtMaturity = null;

	/**
	 * Index Coupon (bp)
	 */

	public double _dblCoupon = java.lang.Double.NaN;

	/**
	 * Index Currency
	 */

	public java.lang.String _strCurrency = "";

	/**
	 * Index DayCount
	 */

	public java.lang.String _strDayCount = "";

	/**
	 * Index Full First Stub
	 */

	public boolean _bFullFirstStub = false;

	/**
	 * Index Recovery
	 */

	public double _dblRecovery = java.lang.Double.NaN;

	/**
	 * Index Frequency
	 */

	public int _iFrequency = 0;

	/**
	 * Index Red ID
	 */

	public java.lang.String _strRedID = "";

	/**
	 * Index Class
	 */

	public java.lang.String _strIndexClass = "";

	/**
	 * Index Series
	 */

	public int _iIndexSeries = 0;

	/**
	 * Index Group Name
	 */

	public java.lang.String _strIndexGroupName = "";

	/**
	 * Index Short Name
	 */

	public java.lang.String _strIndexShortName = "";

	/**
	 * Index Short Group Name
	 */

	public java.lang.String _strIndexShortGroupName = "";

	/**
	 * Index Version
	 */

	public int _iIndexVersion = 0;

	/**
	 * Index Life Span
	 */

	public int _iIndexLifeSpan = 0;

	/**
	 * Index Curvy Curve ID
	 */

	public java.lang.String _strCurvyCurveID = "";

	/**
	 * Index Factor
	 */

	public double _dblIndexFactor = java.lang.Double.NaN;

	/**
	 * Index Original Component Count
	 */

	public int _iOriginalComponentCount = 0;

	/**
	 * Index Defaulted Component Count
	 */

	public int _iDefaultedComponentCount = 0;

	/**
	 * Index Location
	 */

	public java.lang.String _strLocation = "";

	/**
	 * Index Pay Accrued
	 */

	public boolean _bPayAccrued = false;

	/**
	 * Index Knock-out On Default
	 */

	public boolean _bKnockOutOnDefault = false;

	/**
	 * Index Quote As CDS
	 */

	public boolean _bQuoteAsCDS = false;

	/**
	 * Index Bloomberg Ticker
	 */

	public java.lang.String _strBBGTicker = "";

	/**
	 * Index Short Name
	 */

	public java.lang.String _strShortName = "";

	/**
	 * Create a CDXRefData instance from valid individual parameters (so no additional validation is
	 * 	performed).
	 * 
	 * @param strCurveID Index Curve ID
	 * @param strSPN Index SPN
	 * @param strIndexLabel Index Label
	 * @param strIndexName Index Name
	 * @param strCurveName Index Curve Name
	 * @param iIssueDate Index Issue Date
	 * @param iMaturityDate Index Maturity Date
	 * @param dblCoupon Index Coupon
	 * @param strCurrency Index Currency
	 * @param strDayCount Index Day Count Convention
	 * @param bFullFirstStub Index Flag indicating whether it is a full front stub
	 * @param dblRecovery Index Recovery Rate
	 * @param iFrequency Index Frequency
	 * @param strRedID Index Reference Entity Database ID
	 * @param strIndexClass Index Class
	 * @param iIndexSeries Index Series
	 * @param strIndexGroupName Index Group Name
	 * @param strIndexShortName Index Short Name
	 * @param strIndexShortGroupName Index SHort Group Name
	 * @param iIndexVersion Index Version
	 * @param iIndexLifeSpan Index Life Span
	 * @param strCurvyCurveID Full Index Curve ID
	 * @param dblIndexFactor Index Factor
	 * @param iOriginalComponentCount Original Index Component Count
	 * @param iDefaultedComponentCount Defaulted Component Count in the Index
	 * @param strLocation Index Domicile Location
	 * @param bPayAccrued Does Index Pay Accrued
	 * @param bKnockOutOnDefault Does the Index Knock Out On Default
	 * @param bQuoteAsCDS Is the Index Quoted as a CDS (i.e., spread/up-front)
	 * @param strBBGTicker Index Bloomberg Ticker
	 * @param strShortName Index Short Name
	 * 
	 * @return The CDXRefData instance
	 */

	public static final CDXRefDataParams CreateCDXRefDataBuilder (
		final java.lang.String strCurveID,
		final java.lang.String strSPN,
		final java.lang.String strIndexLabel,
		final java.lang.String strIndexName,
		final java.lang.String strCurveName,
		final int iIssueDate,
		final int iMaturityDate,
		final double dblCoupon,
		final java.lang.String strCurrency,
		final java.lang.String strDayCount,
		final boolean bFullFirstStub,
		final double dblRecovery,
		final int iFrequency,
		final java.lang.String strRedID,
		final java.lang.String strIndexClass,
		final int iIndexSeries,
		final java.lang.String strIndexGroupName,
		final java.lang.String strIndexShortName,
		final java.lang.String strIndexShortGroupName,
		final int iIndexVersion,
		final int iIndexLifeSpan,
		final java.lang.String strCurvyCurveID,
		final double dblIndexFactor,
		final int iOriginalComponentCount,
		final int iDefaultedComponentCount,
		final java.lang.String strLocation,
		final boolean bPayAccrued,
		final boolean bKnockOutOnDefault,
		final boolean bQuoteAsCDS,
		final java.lang.String strBBGTicker,
		final java.lang.String strShortName)
	{
		CDXRefDataParams cdxrd = new CDXRefDataParams();

		cdxrd.setCurveID (strCurveID);

		cdxrd.setSPN (strSPN);

		cdxrd.setIndexLabel (strIndexLabel);

		cdxrd.setIndexName (strIndexName);

		cdxrd.setCurveName (strCurveName);

		cdxrd.setIssueDate (new org.drip.analytics.date.JulianDate (iIssueDate));

		cdxrd.setMaturityDate (new org.drip.analytics.date.JulianDate (iMaturityDate));

		cdxrd.setCoupon (dblCoupon);

		cdxrd.setCurrency (strCurrency);

		cdxrd.setDayCount (strDayCount);

		cdxrd.setFullFirstStub (bFullFirstStub);

		cdxrd.setRecovery (dblRecovery);

		cdxrd.setFrequency (iFrequency);

		cdxrd.setRedID (strRedID);

		cdxrd.setIndexClass (strIndexClass);

		cdxrd.setIndexSeries (iIndexSeries);

		cdxrd.setIndexGroupName (strIndexGroupName);

		cdxrd.setIndexShortName (strIndexShortName);

		cdxrd.setIndexShortGroupName (strIndexShortGroupName);

		cdxrd.setIndexVersion (iIndexVersion);

		cdxrd.setIndexLifeSpan (iIndexLifeSpan);

		cdxrd.setCurvyCurveID (strCurvyCurveID);

		cdxrd.setIndexFactor (dblIndexFactor);

		cdxrd.setOriginalComponentCount (iOriginalComponentCount);

		cdxrd.setDefaultedComponentCount (iDefaultedComponentCount);

		cdxrd.setLocation (strLocation);

		cdxrd.setPayAccrued (bPayAccrued);

		cdxrd.setKnockOutOnDefault (bKnockOutOnDefault);

		cdxrd.setQuoteAsCDS (bQuoteAsCDS);

		cdxrd.setBBGTicker (strBBGTicker);

		cdxrd.setShortName (strShortName);

		if (!cdxrd.validate()) return null;

		return cdxrd;
	}

	 /**
	 * Empty Default constructor
	 */

	public CDXRefDataParams()
	{
	}

	/**
	 * Set the Index Curve ID
	 * 
	 * @param strCurveID Index Curve ID
	 * 
	 * @return TRUE if successful
	 */

	public boolean setCurveID (
		final java.lang.String strCurveID)
	{
		if (null == (_strCurveID = strCurveID) || _strCurveID.isEmpty()) return false;

		return true;
	}

	/**
	 * Set the Index SPN
	 * 
	 * @param strSPN Index SPN
	 * 
	 * @return TRUE if successful
	 */

	public boolean setSPN (
		final java.lang.String strSPN)
	{
		if (null == (_strSPN = strSPN) || _strSPN.isEmpty()) return false;

		return true;
	}

	/**
	 * Set the Index Label
	 * 
	 * @param strIndexLabel Index Label
	 * 
	 * @return TRUE if successful
	 */

	public boolean setIndexLabel (
		final java.lang.String strIndexLabel)
	{
		if (null == (_strIndexLabel = strIndexLabel) || _strIndexLabel.isEmpty()) return false;

		return true;
	}

	/**
	 * Set the Index Name
	 * 
	 * @param strIndexName Index Name
	 * 
	 * @return TRUE if successful
	 */

	public boolean setIndexName (
		final java.lang.String strIndexName)
	{
		if (null == (_strIndexName = strIndexName) || _strIndexName.isEmpty()) return false;

		return true;
	}

	/**
	 * Set the Index Curve Name
	 * 
	 * @param strCurveName Index Curve Name
	 * 
	 * @return TRUE if successful
	 */

	public boolean setCurveName (
		final java.lang.String strCurveName)
	{
		if (null == (_strCurveName = strCurveName) || _strCurveName.isEmpty()) return false;

		return true;
	}

	/**
	 * Set the Index Issue Date
	 * 
	 * @param dtIssue Index Issue Date
	 * 
	 * @return TRUE if successful
	 */

	public boolean setIssueDate (
		final org.drip.analytics.date.JulianDate dtIssue)
	{
		if (null == (_dtIssue = dtIssue)) return false;

		return true;
	}

	/**
	 * Set the Index Maturity Date
	 * 
	 * @param dtMaturity Index Maturity Date
	 * 
	 * @return TRUE if successful
	 */

	public boolean setMaturityDate (
		final org.drip.analytics.date.JulianDate dtMaturity)
	{
		if (null == (_dtMaturity = dtMaturity)) return false;

		return true;
	}

	/**
	 * Set the Index Coupon
	 * 
	 * @param dblCoupon Index Coupon
	 * 
	 * @return TRUE if successful
	 */

	public boolean setCoupon (
		final double dblCoupon)
	{
		return org.drip.quant.common.NumberUtil.IsValid (_dblCoupon = dblCoupon);
	}

	/**
	 * Set the Index Currency
	 * 
	 * @param strCurrency Index Currency
	 * 
	 * @return TRUE if successful
	 */

	public boolean setCurrency (
		final java.lang.String strCurrency)
	{
		if (null == (_strCurrency = strCurrency) || _strCurrency.isEmpty()) return false;

		return true;
	}

	/**
	 * Set the Index Day Count
	 * 
	 * @param strDayCount Index Day Count
	 * 
	 * @return TRUE if successful
	 */

	public boolean setDayCount (
		final java.lang.String strDayCount)
	{
		if (null == (_strDayCount = strDayCount) || _strDayCount.isEmpty()) return false;

		return true;
	}

	/**
	 * Set the flag indicating whether the Index has a Full First Stub
	 * 
	 * @param bFullFirstStub Flag indicating whether the Index has a Full First Stub
	 * 
	 * @return TRUE if successful
	 */

	public boolean setFullFirstStub (
		final boolean bFullFirstStub)
	{
		_bFullFirstStub = bFullFirstStub;
		return true;
	}

	/**
	 * Set the Index Recovery
	 * 
	 * @param dblRecovery Index Recovery
	 * 
	 * @return TRUE if successful
	 */

	public boolean setRecovery (
		final double dblRecovery)
	{
		return org.drip.quant.common.NumberUtil.IsValid (_dblRecovery = dblRecovery);
	}

	/**
	 * Set the Index Coupon Frequency
	 * 
	 * @param iFrequency Index Coupon Frequency
	 * 
	 * @return TRUE if successful
	 */

	public boolean setFrequency (
		final int iFrequency)
	{
		_iFrequency = iFrequency;
		return true;
	}

	/**
	 * Set the Index Red ID
	 * 
	 * @param strRedID Index Red ID
	 * 
	 * @return TRUE if successful
	 */

	public boolean setRedID (
		final java.lang.String strRedID)
	{
		if (null == (_strRedID = strRedID) || _strRedID.isEmpty()) return false;

		return true;
	}

	/**
	 * Set the Index Class
	 * 
	 * @param strIndexClass Index Class
	 * 
	 * @return TRUE if successful
	 */

	public boolean setIndexClass (
		final java.lang.String strIndexClass)
	{
		if (null == (_strIndexClass = strIndexClass) || _strIndexClass.isEmpty()) return false;

		return true;
	}

	/**
	 * Set the Index Series
	 * 
	 * @param iIndexSeries Index Series
	 * 
	 * @return TRUE if successful
	 */

	public boolean setIndexSeries (
		final int iIndexSeries)
	{
		_iIndexSeries = iIndexSeries;
		return true;
	}

	/**
	 * Set the Index Group Name
	 * 
	 * @param strIndexGroupName Index Group Name
	 * 
	 * @return TRUE if successful
	 */

	public boolean setIndexGroupName (
		final java.lang.String strIndexGroupName)
	{
		if (null == (_strIndexGroupName = strIndexGroupName) || _strIndexGroupName.isEmpty()) return false;

		return true;
	}

	/**
	 * Set the Index Short Name
	 * 
	 * @param strIndexShortName Index Short Name
	 * 
	 * @return TRUE if successful
	 */

	public boolean setIndexShortName (
		final java.lang.String strIndexShortName)
	{
		if (null == (_strIndexShortName = strIndexShortName) || _strIndexShortName.isEmpty()) return false;

		return true;
	}

	/**
	 * Set the Index Short Group Name
	 * 
	 * @param strIndexShortGroupName Index Short Group Name
	 * 
	 * @return TRUE if successful
	 */

	public boolean setIndexShortGroupName (
		final java.lang.String strIndexShortGroupName)
	{
		if (null == (_strIndexShortGroupName = strIndexShortGroupName) || _strIndexShortGroupName.isEmpty())
			return false;

		return true;
	}

	/**
	 * Set the Index Version
	 * 
	 * @param iIndexVersion Index Version
	 * 
	 * @return TRUE if successful
	 */

	public boolean setIndexVersion (
		final int iIndexVersion)
	{
		_iIndexVersion = iIndexVersion;
		return true;
	}

	/**
	 * Set the Index Life Span
	 * 
	 * @param iIndexLifeSpan Index Life Span
	 * 
	 * @return TRUE if successful
	 */

	public boolean setIndexLifeSpan (
		final int iIndexLifeSpan)
	{
		_iIndexLifeSpan = iIndexLifeSpan;
		return true;
	}

	/**
	 * Set the Index Composite Curve ID
	 * 
	 * @param strCurvyCurveID Index Composite Curve ID
	 * 
	 * @return TRUE if successful
	 */

	public boolean setCurvyCurveID (
		final java.lang.String strCurvyCurveID)
	{
		if (null == (_strCurvyCurveID = strCurvyCurveID) || _strCurvyCurveID.isEmpty()) return false;

		return true;
	}

	/**
	 * Set the Index Factor
	 * 
	 * @param dblIndexFactor Index Factor
	 * 
	 * @return TRUE if successful
	 */

	public boolean setIndexFactor (
		final double dblIndexFactor)
	{
		return org.drip.quant.common.NumberUtil.IsValid (_dblIndexFactor = dblIndexFactor);
	}

	/**
	 * Set the Number of Original Components in the Index
	 * 
	 * @param iOriginalComponentCount Number of Original Components in the Index
	 * 
	 * @return TRUE if successful
	 */

	public boolean setOriginalComponentCount (
		final int iOriginalComponentCount)
	{
		_iOriginalComponentCount = iOriginalComponentCount;
		return true;
	}

	/**
	 * Set the Number of Defaulted Components in the Index
	 * 
	 * @param iDefaultedComponentCount Number of Defaulted Components in the Index
	 * 
	 * @return TRUE if successful
	 */

	public boolean setDefaultedComponentCount (
		final int iDefaultedComponentCount)
	{
		_iDefaultedComponentCount = iDefaultedComponentCount;
		return true;
	}

	/**
	 * Set the Index Location
	 * 
	 * @param strLocation Index Location
	 * 
	 * @return TRUE if successful
	 */

	public boolean setLocation (
		final java.lang.String strLocation)
	{
		if (null == (_strLocation = strLocation) || _strLocation.isEmpty()) return false;

		return true;
	}

	/**
	 * Set if the Index pays accrued on termination
	 * 
	 * @param bPayAccrued Flag indicating if the Index pays accrued on termination
	 * 
	 * @return TRUE if successful
	 */

	public boolean setPayAccrued (
		final boolean bPayAccrued)
	{
		_bPayAccrued = bPayAccrued;
		return true;
	}

	/**
	 * Set if the Index knocks out on Default
	 * 
	 * @param bKnockOutOnDefault Flag indicating if the Index knocks out on Default
	 * 
	 * @return TRUE if successful
	 */

	public boolean setKnockOutOnDefault (
		final boolean bKnockOutOnDefault)
	{
		_bKnockOutOnDefault = bKnockOutOnDefault;
		return true;
	}

	/**
	 * Set whether the quote is marked as a CDS
	 * 
	 * @param bQuoteAsCDS Flag indicating whether the quote is marked as a CDS
	 * 
	 * @return TRUE if successful
	 */

	public boolean setQuoteAsCDS (
		final boolean bQuoteAsCDS)
	{
		_bQuoteAsCDS = bQuoteAsCDS;
		return true;
	}

	/**
	 * Set the Index BBG Ticker
	 * 
	 * @param strBBGTicker Index BBG Ticker
	 * 
	 * @return TRUE if successful
	 */

	public boolean setBBGTicker (
		final java.lang.String strBBGTicker)
	{
		if (null == (_strBBGTicker = strBBGTicker) || strBBGTicker.isEmpty()) return false;

		return true;
	}

	/**
	 * Set the index short name
	 * 
	 * @param strShortName Index Short Name
	 * 
	 * @return TRUE if successful
	 */

	public boolean setShortName (
		final java.lang.String strShortName)
	{
		if (null == (_strShortName = strShortName) || _strShortName.isEmpty()) return false;

		return true;
	}

	/**
	 * Validate the CDXRefData instance
	 * 
	 * @return TRUE if successful
	 */

	public boolean validate()
	{
		if (null == _strCurveID || _strCurveID.isEmpty() || null == _strSPN || _strSPN.isEmpty() || null ==
			_strIndexLabel || _strIndexLabel.isEmpty() || null == _strIndexName || _strIndexName.isEmpty() ||
				null == _strCurveName || _strCurveName.isEmpty() || null == _dtIssue || null == _dtMaturity
					|| _dtIssue.julian() >= _dtMaturity.julian() || !org.drip.quant.common.NumberUtil.IsValid
						(_dblCoupon) || null == _strCurrency || _strCurrency.isEmpty() || null ==
							_strDayCount || _strDayCount.isEmpty() ||
								!org.drip.quant.common.NumberUtil.IsValid (_dblRecovery) || null == _strRedID
									|| _strRedID.isEmpty() || null == _strIndexClass ||
										_strIndexClass.isEmpty() || null == _strIndexGroupName ||
											_strIndexGroupName.isEmpty())
			return false;

		return true;
	}

	/**
	 * Return the stringified set of parameters in a java call that can be statically used to re-construct
	 *  the index.
	 * 
	 * @return Set of Stringified parameters as a java call. 
	 */

	public java.lang.String setConstructionString()
	{
		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		java.lang.String strCDXCode = _strIndexClass + "." + _strIndexGroupName + "." + _iIndexLifeSpan +
			"Y." + _iIndexSeries + "." + _iIndexVersion;

		sb.append ("\t\tUpdateCDXRefDataMap (" + org.drip.quant.common.StringUtil.MakeStringArg (strCDXCode) +
			",\n\t\t\torg.drip.product.creator.CDXRefDataBuilder.CreateCDXRefDataBuilder (");

		sb.append (org.drip.quant.common.StringUtil.MakeStringArg (_strCurveID) + ", ");

		sb.append (org.drip.quant.common.StringUtil.MakeStringArg (_strSPN) + ",\n\t\t\t\t");

		sb.append (org.drip.quant.common.StringUtil.MakeStringArg (_strIndexLabel) + ", ");

		sb.append (org.drip.quant.common.StringUtil.MakeStringArg (_strIndexName) + ",\n\t\t\t\t\t");

		sb.append (org.drip.quant.common.StringUtil.MakeStringArg (_strCurveName) + ", ");

		sb.append (_dtIssue.julian() + ", ");

		sb.append (_dtMaturity.julian() + ", ");

		sb.append (_dblCoupon + ", ");

		sb.append (org.drip.quant.common.StringUtil.MakeStringArg (_strCurrency) + ",\n\t\t\t\t\t\t");

		sb.append (org.drip.quant.common.StringUtil.MakeStringArg (_strDayCount) + ", ");

		sb.append (_bFullFirstStub + ", ");

		sb.append (_dblRecovery + ", ");

		sb.append (_iFrequency + ", ");

		sb.append (org.drip.quant.common.StringUtil.MakeStringArg (_strRedID) + ", ");

		sb.append (org.drip.quant.common.StringUtil.MakeStringArg (_strIndexClass) + ", ");

		sb.append (_iIndexSeries + ", ");

		sb.append (org.drip.quant.common.StringUtil.MakeStringArg (_strIndexGroupName) + ", ");

		sb.append (org.drip.quant.common.StringUtil.MakeStringArg (_strIndexShortName) + ", ");

		sb.append (org.drip.quant.common.StringUtil.MakeStringArg (_strIndexShortGroupName) + ", ");

		sb.append (_iIndexVersion + ", ");

		sb.append (_iIndexLifeSpan + ",\n\t\t\t\t\t\t\t");

		sb.append (org.drip.quant.common.StringUtil.MakeStringArg (_strCurvyCurveID) + ", ");

		sb.append (_dblIndexFactor + ", ");

		sb.append (_iOriginalComponentCount + ", ");

		sb.append (_iDefaultedComponentCount + ", ");

		sb.append (org.drip.quant.common.StringUtil.MakeStringArg (_strLocation) + ", ");

		sb.append (_bPayAccrued + ", ");

		sb.append (_bKnockOutOnDefault + ", ");

		sb.append (_bQuoteAsCDS + ", ");

		sb.append (org.drip.quant.common.StringUtil.MakeStringArg (_strBBGTicker) + ", ");

		sb.append (org.drip.quant.common.StringUtil.MakeStringArg (_strShortName) + "));\n\n");

		return sb.toString();
	}
}
