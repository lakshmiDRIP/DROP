
package org.drip.product.creator;

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
 * <i>CDSBuilder</i> contains the suite of helper functions for creating the CreditDefaultSwap product from
 * the parameters/byte array streams. It also creates the standard EU, NA, ASIA contracts, CDS with
 * amortization schedules, and custom CDS from product codes/tenors.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/README.md">Product Components/Baskets for Credit, FRA, FX, Govvie, Rates, and Option AssetClasses</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator/README.md">Streams and Products Construction Utilities</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CDSBuilder {

	/**
	 * Create the credit default swap from the effective/maturity dates, coupon, IR curve name, and
	 * 	component credit valuation parameters.
	 * 
	 * @param dtEffective JulianDate effective
	 * @param dtMaturity JulianDate maturity
	 * @param dblCoupon Coupon
	 * @param strCurrency Currency
	 * @param cs Credit Setting Parameters
	 * @param strCalendar Optional Holiday Calendar for Accrual calculation
	 * @param bAdjustDates Roll using the FWD mode for the period end dates and the pay dates
	 * 
	 * @return CreditDefaultSwap product
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateCDS (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon,
		final java.lang.String strCurrency,
		final org.drip.product.params.CreditSetting cs,
		final java.lang.String strCalendar,
		final boolean bAdjustDates)
	{
		if (null == dtEffective || null == dtMaturity || null == strCurrency || strCurrency.isEmpty() || null
			== cs || !org.drip.numerical.common.NumberUtil.IsValid (dblCoupon))
			return null;

		try {
			org.drip.analytics.daycount.DateAdjustParams dap = bAdjustDates ? new
				org.drip.analytics.daycount.DateAdjustParams 
					(org.drip.analytics.daycount.Convention.DATE_ROLL_FOLLOWING, 1, strCalendar) : null;

			org.drip.product.definition.CreditDefaultSwap cds = new org.drip.product.credit.CDSComponent
				(dtEffective.julian(), dtMaturity.julian(), dblCoupon, 4, "Act/360", "Act/360", "", false,
					null, null, null, dap, dap, dap, dap, null, null, 1., strCurrency, cs, strCalendar);

			cds.setPrimaryCode ("CDS." + dtMaturity.toString() + "." + cs.creditCurveName());

			return cds;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the credit default swap from the effective/maturity dates, coupon, IR curve name, and
	 * 	credit curve.
	 * 
	 * @param dtEffective JulianDate effective
	 * @param dtMaturity JulianDate maturity
	 * @param dblCoupon Coupon
	 * @param strCurrency Currency
	 * @param dblRecovery Recovery Rate
	 * @param strCredit Credit curve name
	 * @param strCalendar Optional Holiday Calendar for Accrual calculation
	 * @param bAdjustDates Roll using the FWD mode for the period end dates and the pay dates
	 * 
	 * @return CreditDefaultSwap product
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateCDS (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final double dblCoupon,
		final java.lang.String strCurrency,
		final double dblRecovery,
		final java.lang.String strCredit,
		final java.lang.String strCalendar,
		final boolean bAdjustDates)
	{
		if (null == dtEffective || null == dtMaturity || null == strCurrency || strCurrency.isEmpty() || null
			== strCredit || strCredit.isEmpty() || !org.drip.numerical.common.NumberUtil.IsValid (dblCoupon))
			return null;

		org.drip.product.params.CreditSetting cs = new org.drip.product.params.CreditSetting (30,
			dblRecovery, true, strCredit, true);

		return cs.validate() ? CreateCDS (dtEffective, dtMaturity, dblCoupon, strCurrency, cs, strCalendar,
			bAdjustDates) : null;
	}

	/**
	 * Create the credit default swap from the effective date, tenor, coupon, IR curve name, and component
	 * 	credit valuation parameters.
	 * 
	 * @param dtEffective JulianDate effective
	 * @param strTenor String tenor
	 * @param dblCoupon Coupon
	 * @param strCurrency Currency
	 * @param cs Credit Setting Parameters
	 * @param strCalendar Optional Holiday Calendar for Accrual calculation
	 * 
	 * @return CreditDefaultSwap product
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateCDS (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strTenor,
		final double dblCoupon,
		final java.lang.String strCurrency,
		final org.drip.product.params.CreditSetting cs,
		final java.lang.String strCalendar)
	{
		if (null == dtEffective || null == strTenor || strTenor.isEmpty() || null == strCurrency ||
			strCurrency.isEmpty() || null == cs || !org.drip.numerical.common.NumberUtil.IsValid (dblCoupon))
			return null;

		try {
			org.drip.product.definition.CreditDefaultSwap cds = new org.drip.product.credit.CDSComponent
				(dtEffective.julian(), dtEffective.addTenor (strTenor).julian(), dblCoupon, 4, "30/360",
					"30/360", "", true, null, null, null, null, null, null, null, null, null, 100.,
						strCurrency, cs, strCalendar);

			cds.setPrimaryCode ("CDS." + strTenor + "." + cs.creditCurveName());

			return cds;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create the credit default swap from the effective/maturity dates, coupon, IR curve name, and credit
	 * 	curve.
	 * 
	 * @param dtEffective JulianDate effective
	 * @param strTenor String tenor
	 * @param dblCoupon Coupon
	 * @param strCurrency Currency
	 * @param strCredit Credit curve name
	 * @param strCalendar Optional Holiday Calendar for accrual calculation
	 * 
	 * @return CreditDefaultSwap product
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateCDS (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strTenor,
		final double dblCoupon,
		final java.lang.String strCurrency,
		final java.lang.String strCredit,
		final java.lang.String strCalendar)
	{
		if (null == dtEffective || null == strTenor || strTenor.isEmpty() || null == strCurrency ||
			strCurrency.isEmpty() || null == strCredit || strCredit.isEmpty() ||
				!org.drip.numerical.common.NumberUtil.IsValid (dblCoupon))
			return null;

		org.drip.product.params.CreditSetting cs = new org.drip.product.params.CreditSetting (30,
			java.lang.Double.NaN, true, strCredit, true);

		return cs.validate() ? CreateCDS (dtEffective, strTenor, dblCoupon, strCurrency, cs, strCalendar) :
			null;
	}

	/**
	 * Create an SNAC style CDS contract with full first stub
	 * 
	 * @param dtEffective CDS Effective date
	 * @param strTenor CDS Tenor
	 * @param dblCoupon SNAC strike coupon
	 * @param strCurrency Currency
	 * @param strCredit Credit Curve name
	 * @param strCalendar Holiday Calendar
	 * 
	 * @return CDS instance object
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateSNAC (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strTenor,
		final double dblCoupon,
		final java.lang.String strCurrency,
		final java.lang.String strCredit,
		final java.lang.String strCalendar)
	{
		if (null == dtEffective || null == strTenor || strTenor.isEmpty()) return null;

		org.drip.analytics.date.JulianDate dtFirstCoupon = dtEffective.nextCreditIMM (3);

		if (null == dtFirstCoupon) return null;

		org.drip.product.definition.CreditDefaultSwap cds = CreateCDS (dtFirstCoupon.subtractTenor ("3M"),
			dtFirstCoupon.addTenor (strTenor), dblCoupon, strCurrency, 0.40, strCredit, strCalendar, true);

		if (null == cds) return null;

		cds.setPrimaryCode ("CDS." + strTenor + "." + strCredit);

		return cds;
	}

	/**
	 * Create an SNAC style CDS contract with full first stub
	 * 
	 * @param dtEffective CDS Effective date
	 * @param strTenor CDS Tenor
	 * @param dblCoupon SNAC strike coupon
	 * @param strCredit Credit Curve name
	 * 
	 * @return CDS instance object
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateSNAC (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strTenor,
		final double dblCoupon,
		final java.lang.String strCredit)
	{
		return CreateSNAC (dtEffective, strTenor, dblCoupon, "USD", strCredit, "USD");
	}

	/**
	 * Create an Standard EU CDS contract with full first stub
	 * 
	 * @param dtEffective CDS Effective date
	 * @param strTenor CDS Tenor
	 * @param dblCoupon Strike coupon
	 * @param strCredit Credit Curve name
	 * 
	 * @return CDS instance object
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateSTEU (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strTenor,
		final double dblCoupon,
		final java.lang.String strCredit)
	{
		if (null == dtEffective || null == strTenor || strTenor.isEmpty()) return null;

		org.drip.analytics.date.JulianDate dtFirstCoupon = dtEffective.nextCreditIMM (3);

		if (null == dtFirstCoupon) return null;

		org.drip.product.definition.CreditDefaultSwap cds = CreateCDS (dtFirstCoupon.subtractTenor ("3M"),
			dtFirstCoupon.addTenor (strTenor), dblCoupon, "EUR", 0.40, strCredit, "EUR", true);

		cds.setPrimaryCode ("CDS." + strTenor + "." + strCredit);

		return cds;
	}

	/**
	 * Create an Standard Asia Pacific CDS contract with full first stub
	 * 
	 * @param dtEffective CDS Effective date
	 * @param strTenor CDS Tenor
	 * @param dblCoupon Strike coupon
	 * @param strCredit Credit Curve name
	 * 
	 * @return CDS instance object
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateSAPC (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strTenor,
		final double dblCoupon,
		final java.lang.String strCredit)
	{
		if (null == dtEffective || null == strTenor || strTenor.isEmpty()) return null;

		org.drip.analytics.date.JulianDate dtFirstCoupon = dtEffective.nextCreditIMM (3);

		if (null == dtFirstCoupon) return null;

		org.drip.product.definition.CreditDefaultSwap cds = CreateCDS (dtFirstCoupon.subtractTenor ("3M"),
			dtFirstCoupon.addTenor (strTenor), dblCoupon, "HKD", 0.40, strCredit, "HKD", true);

		cds.setPrimaryCode ("CDS." + strTenor + "." + strCredit);

		return cds;
	}

	/**
	 * Create an Standard Emerging Market CDS contract with full first stub
	 * 
	 * @param dtEffective CDS Effective date
	 * @param strTenor CDS Tenor
	 * @param dblCoupon Strike coupon
	 * @param strCredit Credit Curve name
	 * @param strLocation Location
	 * 
	 * @return CDS instance object
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CreateSTEM (
		final org.drip.analytics.date.JulianDate dtEffective,
		final java.lang.String strTenor,
		final double dblCoupon,
		final java.lang.String strCredit,
		final java.lang.String strLocation)
	{
		if (null == dtEffective || null == strTenor || strTenor.isEmpty()) return null;

		org.drip.analytics.date.JulianDate dtFirstCoupon = dtEffective.nextCreditIMM (3);

		if (null == dtFirstCoupon) return null;

		org.drip.product.definition.CreditDefaultSwap cds = CreateCDS (dtFirstCoupon.subtractTenor ("3M"),
			dtFirstCoupon.addTenor (strTenor), dblCoupon, strLocation, 0.25, strCredit, strLocation, true);

		cds.setPrimaryCode ("CDS." + strTenor + "." + strCredit);

		return cds;
	}
}
