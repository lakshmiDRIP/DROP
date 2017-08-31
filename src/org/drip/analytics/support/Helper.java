
package org.drip.analytics.support;

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
 * AnalyticsHelper contains the collection of the analytics related utility functions used by the modules.
 * 	The following are the functionality that it exposes:
 * 	- Yield to Discount Factor, and vice versa.
 * 	- Map Bloomberg Day Count Codes to Credit Analytics Day Count Codes
 * 	- Generate rule-based curve node manifest measure bumps
 * 	- Generate loss periods using a variety of different schemes
 * 	- Aggregate/disaggregate/merge coupon period lists
 * 	- Create fixings objects, rate index from currency/coupon/frequency
 * 	- String Tenor/Month Code/Work-out
 * 	- Standard Treasury Bench-mark off of Maturity
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Helper {

	/**
	 * Tenor Comparator - Left Tenor Greater than Right
	 */

	public static int LEFT_TENOR_GREATER = 1;

	/**
	 * Tenor Comparator - Left Tenor Lesser than Right
	 */

	public static int LEFT_TENOR_LESSER = 2;

	/**
	 * Tenor Comparator - Left Tenor Matches Right
	 */

	public static int LEFT_TENOR_EQUALS = 4;

	private static final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String> s_mapIRSwitch =
		new org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.String>();

	private static final java.util.Map<java.lang.Integer, java.lang.String> s_mapDCBBGCode = new
		java.util.HashMap<java.lang.Integer, java.lang.String>();

	/**
	 * Initialize IR switcher and Bloomberg day count maps
	 */

	public static final void Init()
	{
		s_mapDCBBGCode.put (1, "ACT/ACT");

		s_mapDCBBGCode.put (2, "ACT/360");

		s_mapDCBBGCode.put (3, "ACT/365");

		s_mapDCBBGCode.put (4, "30/ACT");

		s_mapDCBBGCode.put (5, "30/360");

		s_mapDCBBGCode.put (6, "30/365");

		s_mapDCBBGCode.put (7, "NL/ACT");

		s_mapDCBBGCode.put (8, "NL/360");

		s_mapDCBBGCode.put (9, "NL/365");

		s_mapDCBBGCode.put (10, "ACT/ACT NON-EOM");

		s_mapDCBBGCode.put (11, "ACT/360 NON-EOM");

		s_mapDCBBGCode.put (12, "ACT/365 NON-EOM");

		s_mapDCBBGCode.put (13, "30/ACT NON-EOM");

		s_mapDCBBGCode.put (14, "30/360 NON-EOM");

		s_mapDCBBGCode.put (15, "30/365 NON-EOM");

		s_mapDCBBGCode.put (16, "NL/ACT NON-EOM");

		s_mapDCBBGCode.put (17, "NL/360 NON-EOM");

		s_mapDCBBGCode.put (18, "NL/365 NON-EOM");

		s_mapDCBBGCode.put (19, "ISMA 30/ACT");

		s_mapDCBBGCode.put (20, "ISMA 30/360");

		s_mapDCBBGCode.put (21, "ISMA 30/365");

		s_mapDCBBGCode.put (22, "ISMA 30/ACT NON-EOM");

		s_mapDCBBGCode.put (23, "ISMA 30/360 NON-EOM");

		s_mapDCBBGCode.put (24, "ISMA 30/365 NON-EOM");

		s_mapDCBBGCode.put (27, "ACT/364");

		s_mapDCBBGCode.put (29, "US MUNI: 30/360");

		s_mapDCBBGCode.put (30, "ACT/364 NON-EOM");

		s_mapDCBBGCode.put (32, "MUNI30/360 NON-EOM");

		s_mapDCBBGCode.put (33, "BUS DAYS/252");

		s_mapDCBBGCode.put (35, "GERMAN:30/360");

		s_mapDCBBGCode.put (36, "BUS DAY/252 NON-EOM");

		s_mapDCBBGCode.put (38, "GER:30/360 NON-EOM");

		s_mapDCBBGCode.put (40, "US:WIT ACT/ACT");

		s_mapDCBBGCode.put (41, "US:WIB ACT/360");

		s_mapDCBBGCode.put (44, "ISDA SWAPS:30/360");

		s_mapDCBBGCode.put (45, "ISDA SWAPS:30/365");

		s_mapDCBBGCode.put (46, "ISDA SWAPS:30/ACT");

		s_mapDCBBGCode.put (47, "ISDA30/360 NON-EOM");

		s_mapDCBBGCode.put (48, "ISDA30/365 NON-EOM");

		s_mapDCBBGCode.put (49, "ISDA30/ACT NON-EOM");

		s_mapDCBBGCode.put (50, "ISDA 30E/360");

		s_mapDCBBGCode.put (51, "ISDA 30E/365");

		s_mapDCBBGCode.put (52, "ISDA 30E/ACT");

		s_mapDCBBGCode.put (53, "ISDA 30E/360 N-EOM");

		s_mapDCBBGCode.put (54, "ISDA 30E/365 N-EOM");

		s_mapDCBBGCode.put (55, "ISDA 30E/ACT N-EOM");

		s_mapDCBBGCode.put (101, "ACT/ACT");

		s_mapDCBBGCode.put (102, "ACT/360");

		s_mapDCBBGCode.put (103, "ACT/365");

		s_mapDCBBGCode.put (104, "30/360");

		s_mapDCBBGCode.put (105, "ACT/ACT NON-EOM");

		s_mapDCBBGCode.put (106, "ACT/360 NON-EOM");

		s_mapDCBBGCode.put (107, "ACT/365 NON-EOM");

		s_mapDCBBGCode.put (108, "ACT/360");

		s_mapDCBBGCode.put (131, "ISMA 30/360");

		s_mapDCBBGCode.put (201, "ISDA ACT/ACT");

		s_mapDCBBGCode.put (202, "AFB ACT/ACT");

		s_mapDCBBGCode.put (203, "ISDA ACT/ACT NOM");

		s_mapDCBBGCode.put (204, "AFB ACT/ACT NOM");

		s_mapDCBBGCode.put (206, "ISMA ACT/ACT");

		s_mapDCBBGCode.put (207, "ISMA ACT/ACT NON-EOM");

		s_mapIRSwitch.put ("ITL", "EUR");

		s_mapIRSwitch.put ("FRF", "EUR");

		s_mapIRSwitch.put ("CZK", "EUR");

		s_mapIRSwitch.put ("BEF", "EUR");

		s_mapIRSwitch.put ("ATS", "EUR");

		s_mapIRSwitch.put ("SKK", "EUR");
	}

	/**
	 * Calculate the discount factor from the specified frequency, yield, and accrual year fraction
	 * 
	 * @param iFreqIn Input frequency - if zero, set to semi-annual.
	 * @param dblYield Yield
	 * @param dblTime Time in DC years
	 * 
	 * @return the discount factor
	 * 
	 * @throws java.lang.Exception if input are invalid.
	 */

	public static final double Yield2DF (
		final int iFreqIn,
		final double dblYield,
		final double dblTime)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblYield) || !org.drip.quant.common.NumberUtil.IsValid
			(dblTime)) {
			System.out.println (dblYield + " | " + dblTime);

			System.exit (36);

			throw new java.lang.Exception ("Helper::YieldDF => Bad yield/time");
		}

		int iFreq = (0 == iFreqIn) ? 2 : iFreqIn;

		return java.lang.Math.pow (1. + (dblYield / iFreq), -1. * dblTime * iFreq);
	}

	/**
	 * Calculate the yield from the specified discount factor to the given time.
	 * 
	 * @param iFreqIn Yield calculation frequency - defaults to semi-annual if zero.
	 * @param dblDF Discount Factor
	 * @param dblTime Time to which the yield/DF are specified
	 * 
	 * @return Implied yield
	 * 
	 * @throws java.lang.Exception Thrown if yield cannot be computed
	 */

	public static final double DF2Yield (
		final int iFreqIn,
		final double dblDF,
		final double dblTime)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblDF) || !org.drip.quant.common.NumberUtil.IsValid
			(dblTime))
			throw new java.lang.Exception ("CurveProductHelper.DFYield: Bad yield/time");

		int iFreq = (0 == iFreqIn) ? 2 : iFreqIn;

		return iFreq * (java.lang.Math.pow (dblDF, -1. / (iFreq * dblTime)) - 1.);
	}

	/**
	 * Compute the uncompounded OIS Rate from the LIBOR Swap Rate and the LIBOR Swap Rate - Fed Fund Basis.
	 *  The calculation is from the following Bloomberg Publication:
	 * 
	 * 	- Lipman, H. and F. Mercurio (2012): OIS Discounting and Dual-Curve Stripping Methodology at
	 * 		Bloomberg
	 * 
	 * @param dblLIBORSwapRate LIBOR Swap Rate
	 * @param dblFedFundLIBORSwapBasis Fed Fund - LIBOR Swap Rate Basis
	 * 
	 * @return The Uncompounded OIS Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public static final double OISFromLIBORSwapFedFundBasis (
		final double dblLIBORSwapRate,
		final double dblFedFundLIBORSwapBasis)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblLIBORSwapRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblFedFundLIBORSwapBasis))
			throw new java.lang.Exception ("Helper::OISFromLIBORSwapFedFundBasis => Invalid Inputs!");

		double dblOISAnnuity = 1. + 0.25 * (4. * (java.lang.Math.sqrt (1. + (dblLIBORSwapRate * 180. / 365.))
			- 1.) - dblFedFundLIBORSwapBasis);

		return dblOISAnnuity * dblOISAnnuity * dblOISAnnuity * dblOISAnnuity - 1.;
	}

	/**
	 * Compute the Daily Compounded OIS Rate from the LIBOR Swap Rate and the LIBOR Swap Rate - Fed Fund
	 *  Basis. The calculation is from the following Bloomberg Publication:
	 * 
	 * 	- Lipman, H. and F. Mercurio (2012): OIS Discounting and Dual-Curve Stripping Methodology at
	 * 		Bloomberg
	 * 
	 * @param dblLIBORSwapRate LIBOR Swap Rate
	 * @param dblFedFundLIBORSwapBasis Fed Fund - LIBOR Swap Rate Basis
	 * 
	 * @return The Daily Compounded OIS Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public static final double OISFromLIBORSwapFedFundBasis2 (
		final double dblLIBORSwapRate,
		final double dblFedFundLIBORSwapBasis)
		throws java.lang.Exception
	{
		return 4. * (java.lang.Math.pow (1. + (OISFromLIBORSwapFedFundBasis (dblLIBORSwapRate,
			dblFedFundLIBORSwapBasis) / 360.), 90.) - 1.);
	}

	/**
	 * Compute the DI-Style Price given the Rate
	 * 
	 * @param dblDIRate The DI Rate
	 * @param iStartDate The Start Date
	 * @param iEndDate The End Date
	 * @param strCalendar The Calendar
	 * 
	 * @return The DI-Style Price
	 * 
	 * @throws java.lang.Exception Thrown if the DI-Style Price cannot be calculated
	 */

	public static final double DIStylePriceFromRate (
		final double dblDIRate,
		final int iStartDate,
		final int iEndDate,
		final java.lang.String strCalendar)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblDIRate) || iStartDate >= iEndDate)
			throw new java.lang.Exception ("Helper::DIStylePriceFromRate => Invalid Inputs");

		return java.lang.Math.pow (1. + dblDIRate, -1. * org.drip.analytics.daycount.Convention.BusinessDays
			(iStartDate, iEndDate, strCalendar) / 252.);
	}

	/**
	 * Compute the DI-Style Rate given the Price
	 * 
	 * @param dblDIPrice The DI Price
	 * @param iStartDate The Start Date
	 * @param iEndDate The End Date
	 * @param strCalendar The Calendar
	 * 
	 * @return The DI-Style Rate
	 * 
	 * @throws java.lang.Exception Thrown if the DI-Style Price cannot be calculated
	 */

	public static final double DIStyleRateFromPrice (
		final double dblDIPrice,
		final int iStartDate,
		final int iEndDate,
		final java.lang.String strCalendar)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblDIPrice) || iStartDate >= iEndDate)
			throw new java.lang.Exception ("Helper::DIStyleRateFromPrice => Invalid Inputs");

		return java.lang.Math.pow (dblDIPrice, -252. / org.drip.analytics.daycount.Convention.BusinessDays
			(iStartDate, iEndDate, strCalendar)) - 1.;
	}

	/**
	 * Convert the Nominal Yield to the Post Tax Equivalent Yield
	 * 
	 * @param dblNominalYield The Nominal Yield
	 * @param dblTaxRate The Tax Rate
	 * 
	 * @return The Post Tax Equivalent Yield
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double NominalYieldToPostTaxEquivalent (
		final double dblNominalYield,
		final double dblTaxRate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblNominalYield) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblTaxRate))
			throw new java.lang.Exception ("Helper::NominalYieldToPostTaxEquivalent => Invalid Inputs");

		return dblNominalYield * (1. - dblTaxRate);
	}

	/**
	 * Convert the Post Tax Equivalent Yield to the Nominal Yield
	 * 
	 * @param dblPostTaxEquivalentYield The Post Tax Equivalent Yield
	 * @param dblTaxRate The Tax Rate
	 * 
	 * @return The Nominal Yield
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double PostTaxEquivalentYieldToNominal (
		final double dblPostTaxEquivalentYield,
		final double dblTaxRate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPostTaxEquivalentYield) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblTaxRate))
			throw new java.lang.Exception ("Helper::PostTaxEquivalentYieldToNominal => Invalid Inputs");

		return dblPostTaxEquivalentYield / (1. - dblTaxRate);
	}

	/**
	 * Return the standard on-the-run benchmark treasury string from the valuation and the maturity dates
	 * 
	 * @param iValueDate the Valuation date
	 * @param iMaturityDate the Maturity date
	 * 
	 * @return the standard on-the-run benchmark treasury string
	 */

	public static final java.lang.String BaseTsyBmk (
		final int iValueDate,
		final int iMaturityDate)
	{
		double dblMatYears = 1. * (iMaturityDate - iValueDate) / 365.25;

		if (1.0 < dblMatYears && dblMatYears <= 2.5) return "02YON";

		if (2.5 < dblMatYears && dblMatYears <= 4.0) return "03YON";

		if (4.0 < dblMatYears && dblMatYears <= 6.0) return "05YON";

		if (6.0 < dblMatYears && dblMatYears <= 8.5) return "07YON";

		if (8.5 < dblMatYears && dblMatYears <= 15.) return "10YON";

		if (dblMatYears > 15.) return "30YON";

		return null;
	}

	/**
	 * Turn the work out type to string
	 * 
	 * @param iWOType One of the WO_TYPE_* fields in the WorkoutInfo class
	 * 
	 * @return String representation of the work out type field
	 */

	public static final java.lang.String WorkoutTypeToString (
		final int iWOType)
	{
		if (org.drip.param.valuation.WorkoutInfo.WO_TYPE_PUT == iWOType) return "Put";

		if (org.drip.param.valuation.WorkoutInfo.WO_TYPE_CALL == iWOType) return "Call";

		if (org.drip.param.valuation.WorkoutInfo.WO_TYPE_MATURITY == iWOType) return "Maturity";

		return "Unknown work out type";
	}

	/**
	 * Convert the Bloomberg day count code to DRIP day count code.
	 *  
	 * @param strBBGDCCode String representing the Bloomberg day count code.
	 * 
	 * @return String representing the DRIP day count code.
	 */

	public static final java.lang.String ParseFromBBGDCCode (
		final java.lang.String strBBGDCCode)
	{
		if (null == strBBGDCCode) return "Unknown BBG DC";

		try {
			return s_mapDCBBGCode.get ((int) new java.lang.Double (strBBGDCCode.trim()).doubleValue());
		} catch (java.lang.Exception e) {
		}

		return "Unknown BBG DC";
	}

	/**
	 * Retrieve the tenor from the frequency
	 * 
	 * @param iFreq Integer frequency
	 * 
	 * @return String representing the tenor
	 */

	public static final java.lang.String GetTenorFromFreq (
		final int iFreq)
	{
		if (1 == iFreq) return "1Y";

		if (2 == iFreq) return "6M";

		if (3 == iFreq) return "4M";

		if (4 == iFreq) return "3M";

		if (6 == iFreq) return "2M";

		if (12 == iFreq) return "1M";

		return null;
	}

	/**
	 * Retrieve the Number of Years from the Tenor
	 * 
	 * @param strTenor The Specified Tenor
	 * 
	 * @return The Number of Years
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final int TenorToYears (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("Helper::TenorToYears => Invalid Inputs");

		char chTenor = strTenor.charAt (strTenor.length() - 1);

		int iTimeUnit = (int) new java.lang.Double (strTenor.substring (0, strTenor.length() -
			1)).doubleValue();

		if ('y' == chTenor || 'Y' == chTenor) return iTimeUnit * 12;

		throw new java.lang.Exception ("AnalyticsHelper::TenorToYears => Invalid tenor format " + strTenor);
	}

	/**
	 * Retrieve the Number of Months from the Tenor
	 * 
	 * @param strTenor The Specified Tenor
	 * 
	 * @return The Number of Months
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final int TenorToMonths (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("Helper::TenorToMonths => Invalid Inputs");

		char chTenor = strTenor.charAt (strTenor.length() - 1);

		int iTimeUnit = (int) new java.lang.Double (strTenor.substring (0, strTenor.length() -
			1)).doubleValue();

		if ('d' == chTenor || 'D' == chTenor) return iTimeUnit * (iTimeUnit / 30);

		if ('w' == chTenor || 'W' == chTenor) return iTimeUnit * (iTimeUnit / 7);

		if ('l' == chTenor || 'L' == chTenor) return iTimeUnit;

		if ('m' == chTenor || 'M' == chTenor) return iTimeUnit;

		if ('y' == chTenor || 'Y' == chTenor) return iTimeUnit * 12;

		throw new java.lang.Exception ("Helper::TenorToMonths => Invalid Tenor Format " + strTenor);
	}

	/**
	 * Retrieve the Number of Days from the Tenor
	 * 
	 * @param strTenor The Specified Tenor
	 * 
	 * @return The Number of Days
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final int TenorToDays (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("Helper::TenorToDays => Invalid Inputs");

		char chTenor = strTenor.charAt (strTenor.length() - 1);

		int iTimeUnit = (int) new java.lang.Double (strTenor.substring (0, strTenor.length() -
			1)).doubleValue();

		if ('d' == chTenor || 'D' == chTenor) return iTimeUnit;

		if ('w' == chTenor || 'W' == chTenor) return iTimeUnit * 7;

		if ('l' == chTenor || 'L' == chTenor) return iTimeUnit * 28;

		if ('m' == chTenor || 'M' == chTenor) return iTimeUnit * 30;

		if ('y' == chTenor || 'Y' == chTenor) return (int) (365.25 * iTimeUnit);

		throw new java.lang.Exception ("Helper::TenorToDays => Unknown tenor format " + strTenor);
	}

	/**
	 * Retrieve the Year Fraction from the Tenor
	 * 
	 * @param strTenor The Specified Tenor
	 * 
	 * @return The Year Fraction
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final double TenorToYearFraction (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("Helper::TenorToYearFraction => Invalid Inputs");

		char chTenor = strTenor.charAt (strTenor.length() - 1);

		int iTimeUnit = (int) new java.lang.Double (strTenor.substring (0, strTenor.length() -
			1)).doubleValue();

		if ('d' == chTenor || 'D' == chTenor) return ((double) iTimeUnit) / 365.25;

		if ('w' == chTenor || 'W' == chTenor) return ((double) (7. * iTimeUnit)) / 365.25;

		if ('w' == chTenor || 'W' == chTenor) return ((double) (iTimeUnit)) / 52.;

		if ('l' == chTenor || 'L' == chTenor) return ((double) (28. * iTimeUnit)) / 365.25;

		if ('l' == chTenor || 'L' == chTenor) return ((double) (iTimeUnit)) / 13.;

		if ('m' == chTenor || 'M' == chTenor) return ((double) (iTimeUnit)) / 12.;

		if ('y' == chTenor || 'Y' == chTenor) return iTimeUnit;

		throw new java.lang.Exception ("Helper::TenorToYearFraction => Unknown tenor format " + strTenor);
	}

	/**
	 * Retrieve the Year Fraction from the Tenor Array 
	 * 
	 * @param astrTenor The Specified Tenor Array
	 * @param bForward TRUE - Generated the Incremental Forward Year Fraction
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 * 
	 * @return The Year Fraction Array
	 */

	public static final double[] TenorToYearFraction (
		final java.lang.String[] astrTenor,
		final boolean bForward)
		throws java.lang.Exception
	{
		if (null == astrTenor)
			throw new java.lang.Exception ("Helper::TenorToYearFraction => Invalid Inputs");

		int iNumTenor = astrTenor.length;
		double[] adblYearFraction = 0 == iNumTenor ? null : new double[iNumTenor];

		for (int i = 0; i < iNumTenor; ++i) {
			try {
				adblYearFraction[i] = TenorToYearFraction (astrTenor[i]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		if (!bForward) return adblYearFraction;

		for (int i = iNumTenor - 1; i > 1; --i)
			adblYearFraction[i] = adblYearFraction[i] - adblYearFraction[i - 1];

		return adblYearFraction;
	}

	/**
	 * Retrieve the Annual Frequency from the Tenor
	 * 
	 * @param strTenor The Specified Tenor
	 * 
	 * @return The Annual Frequency
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public static final int TenorToFreq (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("Helper::TenorToFreq => Invalid Inputs");

		if ("ON".equalsIgnoreCase (strTenor)) return 365;

		char chTenor = strTenor.charAt (strTenor.length() - 1);

		int iTimeUnit = (int) new java.lang.Double (strTenor.substring (0, strTenor.length() -
			1)).doubleValue();

		if ('d' == chTenor || 'D' == chTenor) return (int) (365. / iTimeUnit);

		if ('w' == chTenor || 'W' == chTenor) return (int) (52. / iTimeUnit);

		if ('l' == chTenor || 'L' == chTenor) return (int) (13. / iTimeUnit);

		if ('m' == chTenor || 'M' == chTenor) return (int) (12. / iTimeUnit);

		if ('y' == chTenor || 'Y' == chTenor) return iTimeUnit;

		throw new java.lang.Exception ("Helper::TenorToFreq => Unknown tenor format " + strTenor);
	}

	/**
	 * Retrieve the Date Array From the Tenor Array
	 * 
	 * @param dtSpot The Spot Date Array
	 * @param astrTenor The Specified Tenor Array
	 * 
	 * @return The Date Array From the Tenor Array
	 */

	public static final int[] TenorToDate (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String[] astrTenor)
	{
		if (null == dtSpot || null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		int[] aiTenorDate = new int[iNumTenor];

		for (int i = 0; i < iNumTenor; ++i) {
			org.drip.analytics.date.JulianDate dtTenor = dtSpot.addTenor (astrTenor[i]);

			if (null == dtTenor) return null;

			aiTenorDate[i] = dtTenor.julian();
		}

		return aiTenorDate;
	}

	/**
	 * Compare the Left and the Right Tenors
	 * 
	 * @param strTenorLeft Left Tenor
	 * @param strTenorRight Right Tenor
	 * 
	 * @return Results of the Comparison
	 * 
	 * @throws java.lang.Exception Thrown if the Comparison cannot be done
	 */

	public static final int TenorCompare (
		final java.lang.String strTenorLeft,
		final java.lang.String strTenorRight)
		throws java.lang.Exception
	{
		int iLeftTenorDays = TenorToDays (strTenorLeft);

		int iRightTenorDays = TenorToDays (strTenorRight);

		if (iLeftTenorDays == iRightTenorDays) return LEFT_TENOR_EQUALS;

		return iLeftTenorDays > iRightTenorDays ? LEFT_TENOR_GREATER : LEFT_TENOR_LESSER;
	}

	/**
	 * Retrieve the month code from input frequency
	 * 
	 * @param iFreq Integer frequency
	 * 
	 * @return String representing the month code
	 */

	public static final java.lang.String GetMonthCodeFromFreq (
		final int iFreq)
	{
		if (1 == iFreq) return "0012M";

		if (2 == iFreq) return "0006M";

		if (3 == iFreq) return "0004M";

		if (4 == iFreq) return "0003M";

		if (6 == iFreq) return "0002M";

		if (12 == iFreq) return "0001M";

		return null;
	}

	/**
	 * Calculate the rate index from the coupon currency and the frequency
	 * 
	 * @param strCouponCurrency String representing the coupon currency
	 * @param iCouponFreq Integer representing the coupon frequency
	 * 
	 * @return String representing the rate index
	 */

	public static final java.lang.String CalcRateIndex (
		final java.lang.String strCouponCurrency,
		final int iCouponFreq)
	{
		if (null == strCouponCurrency || strCouponCurrency.isEmpty()) return null;

		java.lang.String strFreqMonthCode = GetMonthCodeFromFreq (iCouponFreq);

		if (null == strFreqMonthCode) return null;

		return strCouponCurrency.substring (0, 2) + strFreqMonthCode;
	}

	/**
	 * Get the DRIP day count from the Bloomberg code
	 * 
	 * @param strBBGDC String representing the Bloomberg day count convention
	 * 
	 * @return String representing DRIP day count
	 */

	public static final java.lang.String GetDayCountFromBBGCode (
		final java.lang.String strBBGDC)
	{
		if (null == strBBGDC || strBBGDC.isEmpty()) return "30/360";

		return "30/360";
	}

	/**
	 * Calculate the rate index from currency and coupon frequency
	 * 
	 * @param strCcy String representing coupon currency
	 * @param iCouponFreq Integer representing coupon frequency
	 * 
	 * @return String representing the rate index
	 */

	public static final java.lang.String RateIndexFromCcyAndCouponFreq (
		final java.lang.String strCcy,
		final int iCouponFreq)
	{
		if (null == strCcy || strCcy.isEmpty() || 0 >= iCouponFreq) return "";

		java.lang.String strCcyPrefix = strCcy.substring (0, 2);

		if (1 == iCouponFreq)  return strCcyPrefix + "0012M";

		if (2 == iCouponFreq)  return strCcyPrefix + "0006M";

		if (3 == iCouponFreq)  return strCcyPrefix + "0004M";

		if (4 == iCouponFreq)  return strCcyPrefix + "0003M";

		if (6 == iCouponFreq)  return strCcyPrefix + "0002M";

		if (12 == iCouponFreq)  return strCcyPrefix + "0001M";

		return "";
	}

	/**
	 * Switch the given IR curve if necessary
	 * 
	 * @param strCurveIn String representing the input curve
	 * 
	 * @return String representing the switched curve
	 */

	public static final java.lang.String SwitchIRCurve (
		final java.lang.String strCurveIn)
	{
		if (null == strCurveIn) return null;

		if (!s_mapIRSwitch.containsKey (strCurveIn)) return strCurveIn;

		return s_mapIRSwitch.get (strCurveIn);
	}

	/**
	 * Create the Latent State Fixings object from the bond, the fixings date, and the fixing.
	 * 
	 * @param bond The input bond
	 * @param dtFixing The Fixings Date
	 * @param dblFixing Double representing the fixing
	 * 
	 * @return The Latent State Fixings Instance
	 */
	
	public static final org.drip.param.market.LatentStateFixingsContainer CreateFixingsObject (
		final org.drip.product.definition.Bond bond,
		final org.drip.analytics.date.JulianDate dtFixing,
		final double dblFixing)
	{
		if (!bond.isFloater()) return null;

		org.drip.param.market.LatentStateFixingsContainer lsfc = new
			org.drip.param.market.LatentStateFixingsContainer();

		return lsfc.add (dtFixing, bond.forwardLabel().get (0), dblFixing) ? lsfc : null;
	}

	/**
	 * Bump the input array quotes
	 * 
	 * @param adblQuotesIn Array of the input double quotes
	 * @param dblBump Bump amount
	 * @param bIsProportional True - Bump is proportional
	 * 
	 * @return Bumped array output
	 */

	public static final double[] BumpQuotes (
		final double[] adblQuotesIn,
		final double dblBump,
		final boolean bIsProportional)
	{
		if (null == adblQuotesIn || 0 == adblQuotesIn.length || !org.drip.quant.common.NumberUtil.IsValid
			(dblBump))
			return null;

		double[] adblQuotesOut = new double[adblQuotesIn.length];

		for (int i = 0; i < adblQuotesIn.length; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (adblQuotesIn[i])) return null;

			if (!bIsProportional)
				adblQuotesOut[i] = adblQuotesIn[i] + dblBump;
			else
				adblQuotesOut[i] = adblQuotesIn[i] * (1. + dblBump);
		}

		return adblQuotesOut;
	}

	/**
	 * Tweak the Manifest Measures (gor the given set of nodes) in accordance with the specified tweak
	 *  parameters
	 * 
	 * @param adblQuotesIn Array of quotes to be bumped
	 * @param ntp NodeTweakParams input
	 * 
	 * @return Bumped array output
	 */

	public static final double[] TweakManifestMeasure (
		final double[] adblQuotesIn,
		final org.drip.param.definition.ManifestMeasureTweak ntp)
	{
		if (null == adblQuotesIn || 0 == adblQuotesIn.length || null == ntp) return adblQuotesIn;

		double[] adblQuotesOut = new double[adblQuotesIn.length];

		if (org.drip.param.definition.ManifestMeasureTweak.FLAT == ntp.node()) {
			for (int i = 0; i < adblQuotesIn.length; ++i) {
				if (!org.drip.quant.common.NumberUtil.IsValid (adblQuotesIn[i])) return null;

				if (!ntp.isProportional())
					adblQuotesOut[i] = adblQuotesIn[i] + ntp.amount();
				else
					adblQuotesOut[i] = adblQuotesIn[i] * (1. + ntp.amount());
			}
		} else {
			if (ntp.node() < 0 || ntp.node() >= adblQuotesIn.length) return null;

			for (int i = 0; i < adblQuotesIn.length; ++i) {
				if (!org.drip.quant.common.NumberUtil.IsValid (adblQuotesIn[i])) return null;

				if (i == ntp.node()) {
					if (!ntp.isProportional())
						adblQuotesOut[i] = adblQuotesIn[i] + ntp.amount();
					else
						adblQuotesOut[i] = adblQuotesIn[i] * (1. + ntp.amount());
				} else
					adblQuotesOut[i] = adblQuotesIn[i];
			}
		}

		return adblQuotesOut;
	}

	/**
	 * Merge two lists of periods
	 * 
	 * @param lsPeriod1 Period 1
	 * @param lsPeriod2 Period 2
	 * 
	 * @return The Merged Period List
	 */

	public static final java.util.List<org.drip.analytics.cashflow.CompositePeriod> MergePeriodLists (
		final java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsPeriod1,
		final java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsPeriod2)
	{
		if ((null == lsPeriod1 || 0 == lsPeriod1.size()) && (null == lsPeriod2 || 0 == lsPeriod2.size()))
			return null;

		java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsPeriodMerged = new
			java.util.ArrayList<org.drip.analytics.cashflow.CompositePeriod>();

		if (null == lsPeriod1 || 0 == lsPeriod1.size()) {
			for (org.drip.analytics.cashflow.CompositePeriod p : lsPeriod2) {
				if (null != p) lsPeriodMerged.add (p);
			}

			return lsPeriodMerged;
		}

		if (null == lsPeriod2 || 0 == lsPeriod2.size()) {
			for (org.drip.analytics.cashflow.CompositePeriod p : lsPeriod1) {
				if (null != p) lsPeriodMerged.add (p);
			}

			return lsPeriodMerged;
		}

		int iPeriod1Index = 0;
		int iPeriod2Index = 0;

		while (iPeriod1Index < lsPeriod1.size() && iPeriod2Index < lsPeriod2.size()) {
			org.drip.analytics.cashflow.CompositePeriod p1 = lsPeriod1.get (iPeriod1Index);

			org.drip.analytics.cashflow.CompositePeriod p2 = lsPeriod2.get (iPeriod2Index);

			if (p1.payDate() < p2.payDate()) {
				lsPeriodMerged.add (p1);

				++iPeriod1Index;
			} else {
				lsPeriodMerged.add (p2);

				++iPeriod2Index;
			}
		}

		if (iPeriod1Index < lsPeriod1.size() - 1) {
			for (int i = iPeriod1Index; i < lsPeriod1.size(); ++i)
				lsPeriodMerged.add (lsPeriod1.get (i));
		} else if (iPeriod2Index < lsPeriod2.size() - 1) {
			for (int i = iPeriod2Index; i < lsPeriod2.size(); ++i)
				lsPeriodMerged.add (lsPeriod2.get (i));
		}

		return lsPeriodMerged;
	}

	/**
	 * Aggregate the period lists for an array of components
	 * 
	 * @param aComp Array of Components
	 * 
	 * @return The Aggregated Period Set
	 */

	public static final java.util.Set<org.drip.analytics.cashflow.CompositePeriod> AggregateComponentPeriods
		(final org.drip.product.definition.Component[] aComp)
	{
		if (null == aComp) return null;

		int iStartIndex = 0;
		int iNumComp = aComp.length;

		if (0 == iNumComp) return null;

		for (int i = 0; i < iNumComp; ++i) {
			if (null != aComp[i]) {
				iStartIndex = i;
				break;
			}
		}

		java.util.Set<org.drip.analytics.cashflow.CompositePeriod> setAggregatedPeriod = new
			java.util.TreeSet<org.drip.analytics.cashflow.CompositePeriod>();

		for (int i = iStartIndex; i < iNumComp; ++i) {
			if (null == aComp[i]) continue;

			java.util.List<org.drip.analytics.cashflow.CompositePeriod> lsCompPeriod =
				aComp[i].couponPeriods();

			if (null == lsCompPeriod || 0 == lsCompPeriod.size()) continue;

			for (org.drip.analytics.cashflow.CompositePeriod p : lsCompPeriod) {
				if (null != p) setAggregatedPeriod.add (p);
			}
		}

		return setAggregatedPeriod;
	}

	/**
	 * Append the Prefixed Map Entries of the specified Input Map onto the Output Map
	 * 
	 * @param mapOutput The Output Map
	 * @param strPrefix The Entry Prefix
	 * @param mapInput The Input Map
	 * 
	 * @return TRUE - At least one entry appended
	 */

	public static final boolean AccumulateMeasures (
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapOutput,
		final java.lang.String strPrefix,
		final org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> mapInput)
	{
		if (null == mapOutput || null == strPrefix || strPrefix.isEmpty() || null == mapInput) return false;

		java.util.Set<java.util.Map.Entry<java.lang.String, java.lang.Double>> mapInputESSingle =
			mapInput.entrySet();

		if (null == mapInputESSingle) return false;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> me : mapInputESSingle) {
			if (null == me) continue;

			java.lang.String strKey = me.getKey();

			if (null == strKey || strKey.isEmpty()) continue;

			mapOutput.put (strPrefix + "@" + strKey, me.getValue());
		}

		return true;
	}

	/**
	 * Do the Left and the Right Labels Match?
	 * 
	 * @param lslLeft Left Cash Flow Period Label
	 * @param lslRight Right Cash Flow Period Label
	 * 
	 * @return TRUE - The Labels Match
	 */

	public static final boolean LabelMatch (
		final org.drip.state.identifier.LatentStateLabel lslLeft,
		final org.drip.state.identifier.LatentStateLabel lslRight)
	{
		if (null == lslLeft && null == lslRight) return true;

		if ((null == lslLeft && null != lslRight) || (null != lslLeft && null == lslRight)) return false;

		return lslLeft.match (lslRight);
	}

	/**
	 * Compute the Bond Futures Price AUD Bill Style from the Reference Index Level
	 * 
	 * @param dtValue The Valuation Date
	 * @param bond The Bond Instance
	 * @param dblReferenceIndex The Reference Index
	 * 
	 * @return The Bond Futures Price AUD Bill Style
	 * 
	 * @throws java.lang.Exception Thrown if the Bond Futures Price AUD Bill Style cannot be computed
	 */

	public static final double BondFuturesPriceAUDBillStyle (
		final org.drip.analytics.date.JulianDate dtValue,
		final org.drip.product.definition.Bond bond,
		final double dblReferenceIndex)
		throws java.lang.Exception
	{
		if (null == dtValue || null == bond || !org.drip.quant.common.NumberUtil.IsValid (dblReferenceIndex))
			throw new java.lang.Exception
				("AnalyticsHelper::BondFuturesPriceAUDBillStyle => Invalid Inputs");

		return 1. / (1. + (1. - dblReferenceIndex) * org.drip.analytics.daycount.Convention.YearFraction
			(dtValue.julian(), bond.maturityDate().julian(), bond.accrualDC(), false, null,
				bond.currency()));
	}

	/**
	 * Construct a Normalized, Equally Weighted Array from the Specified Number of Elements
	 * 
	 * @param iNumElement Number of Elements
	 * 
	 * @return The Normalized, Equally Weighted Array
	 */

	public static final double[] NormalizedEqualWeightedArray (
		final int iNumElement)
	{
		if (0 >= iNumElement) return null;

		double dblWeight = 1. / iNumElement;
		double[] adblEqualWeighted = new double[iNumElement];

		for (int i = 0; i < iNumElement; ++i)
			adblEqualWeighted[i] = dblWeight;

		return adblEqualWeighted;
	}

	/**
	 * Aggregate the Base and the Roll Tenors onto a Composite Tenor
	 * 
	 * @param strBaseTenor The Base Tenor
	 * @param strRollTenor The Roll Tenor
	 * 
	 * @return The Agrregated Composite Tenor
	 */

	public static final java.lang.String AggregateTenor (
		final java.lang.String strBaseTenor,
		final java.lang.String strRollTenor)
	{
		if (null == strBaseTenor || strBaseTenor.isEmpty()) return strRollTenor;

		char chBaseTenor = strBaseTenor.charAt (strBaseTenor.length() - 1);

		char chRollTenor = strRollTenor.charAt (strRollTenor.length() - 1);

		if (chRollTenor != chBaseTenor) return null;

		int iBaseTimeUnit = (int) new java.lang.Double (strBaseTenor.substring (0, strBaseTenor.length() -
			1)).doubleValue();

		int iRollTimeUnit = (int) new java.lang.Double (strRollTenor.substring (0, strRollTenor.length() -
			1)).doubleValue();

		return "" + (iBaseTimeUnit + iRollTimeUnit) + chBaseTenor;
	}

	/**
	 * Convert the Array of Tenors into Dates off of a Spot
	 * 
	 * @param dtSpot Spot Date
	 * @param astrTenor Array of Tenors
	 * 
	 * @return Array of Dates
	 */

	public static final org.drip.analytics.date.JulianDate[] FromTenor (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String[] astrTenor)
	{
		if (null == dtSpot || null == astrTenor) return null;

		int iNumTenor = astrTenor.length;
		org.drip.analytics.date.JulianDate[] adt = 0 == iNumTenor ? null : new
			org.drip.analytics.date.JulianDate[iNumTenor];

		if (0 == iNumTenor) return null;

		for (int i = 0; i < iNumTenor; ++i) {
			if (null == (adt[i] = dtSpot.addTenor (astrTenor[i]))) return null;
		}

		return adt;
	}

	/**
	 * Generate an Array of Repeated Spot Dates
	 * 
	 * @param dtSpot Spot Date
	 * @param iCount Repeat Count
	 * 
	 * @return Array of the Repeated Spot Dates
	 */

	public static final org.drip.analytics.date.JulianDate[] SpotDateArray (
		final org.drip.analytics.date.JulianDate dtSpot,
		final int iCount)
	{
		if (null == dtSpot || 0 >= iCount) return null;

		org.drip.analytics.date.JulianDate[] adtSpot = new org.drip.analytics.date.JulianDate[iCount];

		for (int i = 0; i < iCount; ++i)
			adtSpot[i] = dtSpot;

		return adtSpot;
	}

	/**
	 * Generate an Array of Bumped Nodes
	 * 
	 * @param adblNode Array of Unbumped Nodes
	 * @param dblBump Bump Amount
	 * 
	 * @return Array of Bumped Nodes
	 */

	public static final double[] ParallelNodeBump (
		final double[] adblNode,
		final double dblBump)
	{
		if (null == adblNode) return null;

		int iNumNode = adblNode.length;
		double[] adblBumpedNode = 0 == iNumNode ? null : new double[iNumNode];

		for (int i = 0; i < iNumNode; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (adblBumpedNode[i] = adblNode[i] + dblBump))
				return null;
		}

		return adblBumpedNode;
	}
}
