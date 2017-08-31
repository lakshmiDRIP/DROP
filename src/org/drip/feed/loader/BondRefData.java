
package org.drip.feed.loader;

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
 * BondRefData contains functionality to load a variety of Bond Product reference data and closing
 * 	marks. It exposes the following functionality:
 * 	- Load the bond valuation-based reference data, amortization schedule and EOS
 * 	- Build the bond instance entities from the valuation-based reference data
 * 	- Load the bond non-valuation-based reference data
 * 
 * BondRefData assumes the appropriate connections are available to load the data.
 * 
 * @author Lakshmi Krishnamurthy
 */

class BondRefData {
	private static final boolean m_bBlog = false;
	private static final boolean m_bDBExec = true;

	private static final org.drip.product.creator.BondRefDataBuilder MakeBRDB (
		final java.lang.String[] astrFODATA)
	{
		if (null == astrFODATA || 88 != astrFODATA.length) return null;

		org.drip.product.creator.BondRefDataBuilder brdb = new org.drip.product.creator.BondRefDataBuilder();

		if (!brdb.setISIN (astrFODATA[40])) {
			System.out.println ("Bad ISIN " + astrFODATA[40]);

			return null;
		}

		if (!brdb.setCUSIP (astrFODATA[42])) {
			System.out.println ("Bad CUSIP " + astrFODATA[42]);

			return null;
		}

		brdb.setBBGID (astrFODATA[1]);

		brdb.setIssuerCategory (astrFODATA[2]);

		brdb.setTicker (astrFODATA[3]);

		brdb.setSeries (astrFODATA[7]);

		brdb.setName (astrFODATA[8]);

		brdb.setShortName (astrFODATA[9]);

		brdb.setIssuerIndustry (astrFODATA[10]);

		brdb.setCouponType (astrFODATA[13]);

		brdb.setMaturityType (astrFODATA[14]);

		brdb.setCalculationType (astrFODATA[15]);

		brdb.setDayCountCode (astrFODATA[16]);

		brdb.setMarketIssueType (astrFODATA[17]);

		brdb.setIssueCountryCode (astrFODATA[18]);

		brdb.setIssueCountry (astrFODATA[19]);

		brdb.setCollateralType (astrFODATA[20]);

		brdb.setIssueAmount (astrFODATA[21]);

		brdb.setOutstandingAmount (astrFODATA[22]);

		brdb.setMinimumPiece (astrFODATA[23]);

		brdb.setMinimumIncrement (astrFODATA[24]);

		brdb.setParAmount (astrFODATA[25]);

		brdb.setLeadManager (astrFODATA[26]);

		brdb.setExchangeCode (astrFODATA[27]);

		brdb.setRedemptionValue (astrFODATA[28]);

		brdb.setAnnounce (astrFODATA[29]);

		brdb.setFirstSettle (astrFODATA[31]);

		brdb.setFirstCoupon (astrFODATA[33]);

		brdb.setInterestAccrualStart (astrFODATA[35]);

		brdb.setIssue (astrFODATA[37]);

		brdb.setIssuePrice (astrFODATA[39]);

		brdb.setNextCouponDate (astrFODATA[43]);

		brdb.setIsCallable (astrFODATA[45]);

		brdb.setIsSinkable (astrFODATA[46]);

		brdb.setIsPutable (astrFODATA[47]);

		brdb.setBBGParent (astrFODATA[48]);

		brdb.setCountryOfIncorporation (astrFODATA[53]);

		brdb.setIndustrySector (astrFODATA[54]);

		brdb.setIndustryGroup (astrFODATA[55]);

		brdb.setIndustrySubgroup (astrFODATA[56]);

		brdb.setCountryOfGuarantor (astrFODATA[57]);

		brdb.setCountryOfDomicile (astrFODATA[58]);

		brdb.setDescription (astrFODATA[59]);

		brdb.setSecurityType (astrFODATA[60]);

		brdb.setPrevCouponDate (astrFODATA[61]);

		brdb.setBBGUniqueID (astrFODATA[63]);

		brdb.setLongCompanyName (astrFODATA[64]);

		brdb.setRedemptionCurrency (astrFODATA[66]);

		brdb.setCouponCurrency (astrFODATA[67]);

		brdb.setIsStructuredNote (astrFODATA[68]);

		brdb.setIsUnitTraded (astrFODATA[69]);

		brdb.setIsReversibleConvertible (astrFODATA[70]);

		brdb.setTradeCurrency (astrFODATA[71]);

		brdb.setIsBearer (astrFODATA[72]);

		brdb.setIsRegistered (astrFODATA[73]);

		brdb.setHasBeenCalled (astrFODATA[74]);

		brdb.setIssuer (astrFODATA[75]);

		brdb.setPenultimateCouponDate (astrFODATA[76]);

		brdb.setFloatCouponConvention (astrFODATA[77]);

		brdb.setCurrentCoupon (astrFODATA[78]);

		brdb.setIsFloater (astrFODATA[79]);

		brdb.setTradeStatus (astrFODATA[80]);

		brdb.setCDRCountryCode (astrFODATA[81]);

		brdb.setCDRSettleCode (astrFODATA[82]);

		brdb.setFinalMaturity (astrFODATA[83]);

		brdb.setIsPrivatePlacement (astrFODATA[85]);

		brdb.setIsPerpetual (astrFODATA[86]);

		brdb.setIsDefaulted (astrFODATA[87]);

		if (!brdb.validate()) return null;

		return brdb;
	}

	private static final org.drip.product.creator.BondProductBuilder MakeBPB (
		final java.lang.String[] astrFODATA,
		final org.drip.param.definition.ScenarioMarketParams mpc)
	{
		if (null == astrFODATA || 88 != astrFODATA.length || null == mpc) return null;

		org.drip.product.creator.BondProductBuilder bpb = new org.drip.product.creator.BondProductBuilder();

		if (!bpb.setISIN (astrFODATA[40])) {
			System.out.println ("Bad ISIN " + astrFODATA[40]);

			return null;
		}

		if (!bpb.setCUSIP (astrFODATA[42])) {
			System.out.println ("Bad CUSIP " + astrFODATA[42]);

			return null;
		}

		bpb.setTicker (astrFODATA[3]);

		bpb.setCoupon (astrFODATA[4]);

		if (!bpb.setMaturity (astrFODATA[5])) {
			System.out.println ("Bad Maturity " + astrFODATA[5]);

			return null;
		}

		if (!bpb.setCouponFreq (astrFODATA[12])) {
			System.out.println ("Bad Cpn Freq " + astrFODATA[12]);

			return null;
		}

		bpb.setCouponType (astrFODATA[13]);

		bpb.setMaturityType (astrFODATA[14]);

		bpb.setCalculationType (astrFODATA[15]);

		if (!bpb.setDayCountCode (astrFODATA[16])) {
			System.out.println ("Bad Day Count " + astrFODATA[40]);

			return null;
		}

		if (!bpb.setRedemptionValue (astrFODATA[28])) {
			System.out.println ("Bad Redemp Value " + astrFODATA[40]);

			return null;
		}

		bpb.setAnnounce (astrFODATA[29]);

		bpb.setFirstSettle (astrFODATA[31]);

		bpb.setFirstCoupon (astrFODATA[33]);

		bpb.setInterestAccrualStart (astrFODATA[35]);

		bpb.setIssue (astrFODATA[37]);

		bpb.setIsCallable (astrFODATA[45]);

		bpb.setIsSinkable (astrFODATA[46]);

		bpb.setIsPutable (astrFODATA[47]);

		if (!bpb.setRedemptionCurrency (astrFODATA[66])) {
			System.out.println ("Bad Redemp Ccy " + astrFODATA[66]);

			return null;
		}

		if (!bpb.setCouponCurrency (astrFODATA[67])) {
			System.out.println ("Bad Cpn Ccy " + astrFODATA[40]);

			return null;
		}

		if (!bpb.setTradeCurrency (astrFODATA[71])) {
			System.out.println ("Bad Trade ccy " + astrFODATA[40]);

			return null;
		}

		bpb.setHasBeenCalled (astrFODATA[74]);

		bpb.setFloatCouponConvention (astrFODATA[77]);

		bpb.setCurrentCoupon (astrFODATA[78]);

		bpb.setIsFloater (astrFODATA[79]);

		bpb.setFinalMaturity (astrFODATA[83]);

		bpb.setIsPerpetual (astrFODATA[86]);

		bpb.setIsDefaulted (astrFODATA[87]);

		if (!bpb.validate (mpc)) return null;

		return bpb;
	}

	public static final void UploadBondFromFODATA (
		final java.lang.String strFODATAFile,
		final java.sql.Statement stmt,
		final org.drip.param.definition.ScenarioMarketParams mpc)
		throws java.lang.Exception
	{
		int iNumBonds = 0;
		int iNumFloaters = 0;
		int iNumFailedToLoad = 0;
		java.lang.String strBondFODATALine = "";

		java.io.BufferedReader inBondFODATA = new java.io.BufferedReader (new java.io.FileReader
			(strFODATAFile));

		while (null != (strBondFODATALine = inBondFODATA.readLine())) {
			++iNumBonds;

			java.lang.String[] astrBondFODATARecord = strBondFODATALine.split (",");

			org.drip.product.creator.BondRefDataBuilder brdb = MakeBRDB (astrBondFODATARecord);

			if (null != brdb) {
				System.out.println ("Doing #" + iNumBonds + ": " + brdb._strCUSIP);

				java.lang.String strSQLBRDBDelete = brdb.makeSQLDelete();

				if (null != strSQLBRDBDelete) {
					if (m_bBlog) System.out.println (strSQLBRDBDelete);

					if (m_bDBExec) stmt.executeUpdate (strSQLBRDBDelete);
				}

				java.lang.String strSQLBRDBInsert = brdb.makeSQLInsert();

				if (null != strSQLBRDBInsert) {
					if (m_bBlog) System.out.println (strSQLBRDBInsert);

					if (m_bDBExec) stmt.executeUpdate (strSQLBRDBInsert);
				}
			}

			org.drip.product.creator.BondProductBuilder bpb = MakeBPB (astrBondFODATARecord, mpc);

			if (null != bpb) {
				if (null != bpb.getFloaterParams()) ++iNumFloaters;

				java.lang.String strSQLBPBDelete = bpb.makeSQLDelete();

				if (null != strSQLBPBDelete) {
					if (m_bBlog) System.out.println (strSQLBPBDelete);

					if (m_bDBExec) stmt.executeUpdate (strSQLBPBDelete);
				}

				java.lang.String strSQLBPBInsert = bpb.makeSQLInsert();

				if (null != strSQLBPBInsert) {
					if (m_bBlog) System.out.println (strSQLBPBInsert);

					if (m_bDBExec) stmt.executeUpdate (strSQLBPBInsert);
				}
			}

			if (null == brdb || null == bpb) ++iNumFailedToLoad;
		}

		inBondFODATA.close();

		System.out.println (iNumFailedToLoad + " out of " + iNumBonds + " failed to load");

		System.out.println ("There were " + iNumFloaters + " floaters!");
	}
}
