
package org.drip.service.template;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>TreasuryBuilder</i> contains Static Helper API to facilitate Construction of the Sovereign Treasury
 * Bonds.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service">Service</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/template">Template</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryBuilder {

	/**
	 * Construct an Instance of the Australian Treasury AUD AGB Bond
	 * 
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param dblCoupon Coupon
	 * 
	 * @return Instance of the Australian Treasury AUD AGB Bond
	 */

	public static final org.drip.product.govvie.TreasuryComponent AGB (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon)
	{
		org.drip.market.issue.TreasurySetting ts =
			org.drip.market.issue.TreasurySettingContainer.TreasurySetting ("AGB");

		return null == ts ? null : org.drip.product.creator.BondBuilder.Treasury (ts.code(), dtEffective,
			dtMaturity, ts.currency(), dblCoupon, ts.frequency(), ts.dayCount());
	}

	/**
	 * Construct an Instance of the Italian Treasury EUR BTPS Bond
	 * 
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param dblCoupon Coupon
	 * 
	 * @return Instance of the Italian Treasury EUR BTPS Bond
	 */

	public static final org.drip.product.govvie.TreasuryComponent BTPS (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon)
	{
		org.drip.market.issue.TreasurySetting ts =
			org.drip.market.issue.TreasurySettingContainer.TreasurySetting ("BTPS");

		return null == ts ? null : org.drip.product.creator.BondBuilder.Treasury (ts.code(), dtEffective,
			dtMaturity, ts.currency(), dblCoupon, ts.frequency(), ts.dayCount());
	}

	/**
	 * Construct an Instance of the Canadian Government CAD CAN Bond
	 * 
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param dblCoupon Coupon
	 * 
	 * @return Instance of the Canadian Government CAD CAN Bond
	 */

	public static final org.drip.product.govvie.TreasuryComponent CAN (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon)
	{
		org.drip.market.issue.TreasurySetting ts =
			org.drip.market.issue.TreasurySettingContainer.TreasurySetting ("CAN");

		return null == ts ? null : org.drip.product.creator.BondBuilder.Treasury (ts.code(), dtEffective,
			dtMaturity, ts.currency(), dblCoupon, ts.frequency(), ts.dayCount());
	}

	/**
	 * Construct an Instance of the German Treasury EUR DBR Bond
	 * 
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param dblCoupon Coupon
	 * 
	 * @return Instance of the German Treasury EUR DBR Bond
	 */

	public static final org.drip.product.govvie.TreasuryComponent DBR (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon)
	{
		org.drip.market.issue.TreasurySetting ts =
			org.drip.market.issue.TreasurySettingContainer.TreasurySetting ("DBR");

		return null == ts ? null : org.drip.product.creator.BondBuilder.Treasury (ts.code(), dtEffective,
			dtMaturity, ts.currency(), dblCoupon, ts.frequency(), ts.dayCount());
	}

	/**
	 * Construct an Instance of the French Treasury EUR FRTR Bond
	 * 
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param dblCoupon Coupon
	 * 
	 * @return Instance of the French Treasury EUR FRTR Bond
	 */

	public static final org.drip.product.govvie.TreasuryComponent FRTR (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon)
	{
		org.drip.market.issue.TreasurySetting ts =
			org.drip.market.issue.TreasurySettingContainer.TreasurySetting ("FRTR");

		return null == ts ? null : org.drip.product.creator.BondBuilder.Treasury (ts.code(), dtEffective,
			dtMaturity, ts.currency(), dblCoupon, ts.frequency(), ts.dayCount());
	}

	/**
	 * Construct an Instance of the Greek Treasury EUR GGB Bond
	 * 
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param dblCoupon Coupon
	 * 
	 * @return Instance of the Greek Treasury EUR GGB Bond
	 */

	public static final org.drip.product.govvie.TreasuryComponent GGB (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon)
	{
		org.drip.market.issue.TreasurySetting ts =
			org.drip.market.issue.TreasurySettingContainer.TreasurySetting ("GGB");

		return null == ts ? null : org.drip.product.creator.BondBuilder.Treasury (ts.code(), dtEffective,
			dtMaturity, ts.currency(), dblCoupon, ts.frequency(), ts.dayCount());
	}

	/**
	 * Construct an Instance of the UK Treasury GBP GILT Bond
	 * 
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param dblCoupon Coupon
	 * 
	 * @return Instance of the UK Treasury GBP GILT Bond
	 */

	public static final org.drip.product.govvie.TreasuryComponent GILT (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon)
	{
		org.drip.market.issue.TreasurySetting ts =
			org.drip.market.issue.TreasurySettingContainer.TreasurySetting ("GILT");

		return null == ts ? null : org.drip.product.creator.BondBuilder.Treasury (ts.code(), dtEffective,
			dtMaturity, ts.currency(), dblCoupon, ts.frequency(), ts.dayCount());
	}

	/**
	 * Construct an Instance of the Japanese Treasury JPY JGB Bond
	 * 
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param dblCoupon Coupon
	 * 
	 * @return Instance of the Japanese Treasury JPY JGB Bond
	 */

	public static final org.drip.product.govvie.TreasuryComponent JGB (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon)
	{
		org.drip.market.issue.TreasurySetting ts =
			org.drip.market.issue.TreasurySettingContainer.TreasurySetting ("JGB");

		return null == ts ? null : org.drip.product.creator.BondBuilder.Treasury (ts.code(), dtEffective,
			dtMaturity, ts.currency(), dblCoupon, ts.frequency(), ts.dayCount());
	}

	/**
	 * Construct an Instance of the Mexican Treasury MXN MBONO Bond
	 * 
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param dblCoupon Coupon
	 * 
	 * @return Instance of the Mexican Treasury MXN MBONO Bond
	 */

	public static final org.drip.product.govvie.TreasuryComponent MBONO (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon)
	{
		org.drip.market.issue.TreasurySetting ts =
			org.drip.market.issue.TreasurySettingContainer.TreasurySetting ("MBONO");

		return null == ts ? null : org.drip.product.creator.BondBuilder.Treasury (ts.code(), dtEffective,
			dtMaturity, ts.currency(), dblCoupon, ts.frequency(), ts.dayCount());
	}

	/**
	 * Construct an Instance of the Spanish Treasury EUR SPGB Bond
	 * 
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param dblCoupon Coupon
	 * 
	 * @return Instance of the Spanish Treasury EUR SPGB Bond
	 */

	public static final org.drip.product.govvie.TreasuryComponent SPGB (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon)
	{
		org.drip.market.issue.TreasurySetting ts =
			org.drip.market.issue.TreasurySettingContainer.TreasurySetting ("SPGB");

		return null == ts ? null : org.drip.product.creator.BondBuilder.Treasury (ts.code(), dtEffective,
			dtMaturity, ts.currency(), dblCoupon, ts.frequency(), ts.dayCount());
	}

	/**
	 * Construct an Instance of the US Treasury USD UST Bond
	 * 
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param dblCoupon Coupon
	 * 
	 * @return Instance of the US Treasury USD UST Bond
	 */

	public static final org.drip.product.govvie.TreasuryComponent UST (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon)
	{
		org.drip.market.issue.TreasurySetting ts =
			org.drip.market.issue.TreasurySettingContainer.TreasurySetting ("UST");

		return null == ts ? null : org.drip.product.creator.BondBuilder.Treasury (ts.code(), dtEffective,
			dtMaturity, ts.currency(), dblCoupon, ts.frequency(), ts.dayCount());
	}

	/**
	 * Construct an Instance of the Treasury Bond From the Code
	 * 
	 * @param strCode The Treasury Code
	 * @param dtEffective Effective Date
	 * @param dtMaturity Maturity Date
	 * @param dblCoupon Coupon
	 * 
	 * @return Instance of the Treasury Bond From the Code
	 */

	public static final org.drip.product.govvie.TreasuryComponent FromCode (
		final java.lang.String strCode,
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon)
	{
		org.drip.market.issue.TreasurySetting ts =
			org.drip.market.issue.TreasurySettingContainer.TreasurySetting (strCode);

		return null == ts ? null : org.drip.product.creator.BondBuilder.Treasury (ts.code(), dtEffective,
			dtMaturity, ts.currency(), dblCoupon, ts.frequency(), ts.dayCount());
	}

	/**
	 * Construct an Array of the Treasury Instances from the Code
	 * 
	 * @param strCode The Treasury Code
	 * @param adtEffective Array of Effective Dates
	 * @param adtMaturity Array of Maturity Dates
	 * @param adblCoupon Array of Coupons
	 * 
	 * @return Array of the Treasury Instances from the Code
	 */

	public static final org.drip.product.govvie.TreasuryComponent[] FromCode (
		final java.lang.String strCode,
		final org.drip.analytics.date.JulianDate[] adtEffective,
		final org.drip.analytics.date.JulianDate[] adtMaturity,
		final double[] adblCoupon)
	{
		if (null == adtEffective || null == adtMaturity || null == adblCoupon) return null;

		int iNumTreasury = adtEffective.length;
		org.drip.product.govvie.TreasuryComponent[] aTreasury = new
			org.drip.product.govvie.TreasuryComponent[iNumTreasury];

		if (0 == iNumTreasury || iNumTreasury != adtMaturity.length || iNumTreasury != adblCoupon.length)
			return null;

		for (int i = 0; i < iNumTreasury; ++i) {
			if (null == (aTreasury[i] = FromCode (strCode, adtEffective[i], adtMaturity[i], adblCoupon[i])))
				return null;
		}

		return aTreasury;
	}
}
