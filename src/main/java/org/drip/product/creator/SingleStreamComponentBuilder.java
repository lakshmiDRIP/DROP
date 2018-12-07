
package org.drip.product.creator;

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
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * <i>SingleStreamComponentBuilder</i> contains the suite of helper functions for creating the Futures
 * product and product pack from the parameters/codes/byte array streams. It also contains function to
 * construct EDF codes and the EDF product from code.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product">Product</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/product/creator">Creator</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SingleStreamComponentBuilder {

	/**
	 * Construct the Forward Rate Futures Code given a Effective Date
	 * 
	 * @param strPrefix The Forward Rate Futures Code Prefix
	 * @param iEffectiveDate Double representing the Effective JulianDate
	 * 
	 * @return The Forward Rate Futures Code
	 */

	public static java.lang.String ForwardRateFuturesCode (
		final java.lang.String strPrefix,
		final int iEffectiveDate)
	{
		try {
			return strPrefix + org.drip.analytics.date.DateUtil.CodeFromMonth
				(org.drip.analytics.date.DateUtil.Month (iEffectiveDate)) +
					(org.drip.analytics.date.DateUtil.Year (iEffectiveDate) % 10);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a Forward Rate Futures Pack corresponding to the Specified Number of Contracts
	 * 
	 * @param dtSpot Spot Date specifying the Contract Issue
	 * @param iNumContract Number of Contracts
	 * @param strCurrency Contract Currency String
	 * 
	 * @return Array of Forward Rate Futures
	 */

	public static org.drip.product.rates.SingleStreamComponent[] ForwardRateFuturesPack (
		final org.drip.analytics.date.JulianDate dtSpot,
		final int iNumContract,
		final java.lang.String strCurrency)
	{
		if (null == dtSpot || 0 >= iNumContract || null == strCurrency || strCurrency.isEmpty()) return null;

		org.drip.product.rates.SingleStreamComponent[] aSSC = new
			org.drip.product.rates.SingleStreamComponent[iNumContract];

		try {
			org.drip.param.period.ComposableFloatingUnitSetting cfus = new
				org.drip.param.period.ComposableFloatingUnitSetting ("3M",
					org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE, null,
						org.drip.state.identifier.ForwardLabel.Standard (strCurrency + "-3M"),
							org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
								0.);

			org.drip.param.period.CompositePeriodSetting cps = new
				org.drip.param.period.CompositePeriodSetting (4, "3M", strCurrency, null, 1., null, null,
					null, null);

			org.drip.param.valuation.CashSettleParams csp = new org.drip.param.valuation.CashSettleParams (0,
				strCurrency, 0);

			org.drip.analytics.date.JulianDate dtStart = dtSpot.nextRatesFuturesIMM (3);

			for (int i = 0; i < iNumContract; ++i) {
				org.drip.analytics.date.JulianDate dtMaturity = dtStart.addMonths (3);

				aSSC[i] = new org.drip.product.rates.SingleStreamComponent ("FUTURE_" + i, new
					org.drip.product.rates.Stream
						(org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit
							(org.drip.analytics.support.CompositePeriodBuilder.EdgePair (dtStart,
								dtMaturity), cps, cfus)), csp);

				aSSC[i].setPrimaryCode (ForwardRateFuturesCode ("USD".equalsIgnoreCase (strCurrency) ? "ED" :
					strCurrency, dtStart.julian()));

				dtStart = dtMaturity;
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return aSSC;
	}

	/**
	 * Create a Deposit Product from the Effective and the Maturity Dates, and the Forward Label
	 * 
	 * @param dtEffective Effective date
	 * @param dtMaturity Maturity
	 * @param fri The Floating Rate Index
	 * 
	 * @return Deposit product
	 */

	public static final org.drip.product.rates.SingleStreamComponent Deposit (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.analytics.date.JulianDate dtMaturity,
		final org.drip.state.identifier.ForwardLabel fri)
	{
		java.lang.String strTenor = fri.tenor();

		java.lang.String strCurrency = fri.currency();

		boolean bIsON = "ON".equalsIgnoreCase (strTenor);

		java.lang.String strCode = "DEPOSIT::" + fri.fullyQualifiedName() + "::{" + dtEffective + "->" +
			dtMaturity + "}";

		try {
			int iFreq = bIsON ? 360 : org.drip.analytics.support.Helper.TenorToFreq (strTenor);

			org.drip.param.period.ComposableFloatingUnitSetting cfus = new
				org.drip.param.period.ComposableFloatingUnitSetting (strTenor, bIsON ?
					org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_OVERNIGHT :
						org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE, null,
							fri,
								org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
				0.);

			org.drip.param.period.CompositePeriodSetting cps = new
				org.drip.param.period.CompositePeriodSetting (iFreq, strTenor, strCurrency,
					fri.floaterIndex().spotLagDAPForward(), 1., null, null, null, null);

			org.drip.product.rates.SingleStreamComponent sscDeposit = new
				org.drip.product.rates.SingleStreamComponent (strCode, new org.drip.product.rates.Stream
					(org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit
						(org.drip.analytics.support.CompositePeriodBuilder.EdgePair (dtEffective,
							dtMaturity), cps, cfus)), new org.drip.param.valuation.CashSettleParams (0,
								strCurrency, 0));

			sscDeposit.setPrimaryCode (strCode);

			return sscDeposit;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a Standard FRA from the Spot Date, the Forward Label, and the Strike
	 * 
	 * @param dtForwardStart Forward Start Date
	 * @param forwardLabel The Floating Rate Index
	 * @param dblStrike Futures Strike
	 * 
	 * @return The Standard FRA Instance
	 */

	public static final org.drip.product.fra.FRAStandardComponent FRAStandard (
		final org.drip.analytics.date.JulianDate dtForwardStart,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final double dblStrike)
	{
		if (null == dtForwardStart || null == forwardLabel) return null;

		java.lang.String strCurrency = forwardLabel.currency();

		org.drip.analytics.date.JulianDate dtEffective = null;

		org.drip.analytics.daycount.DateAdjustParams dapEffective =
			forwardLabel.floaterIndex().spotLagDAPForward();

		try {
			dtEffective = null == dapEffective ? dtForwardStart : new org.drip.analytics.date.JulianDate
				(dapEffective.roll (dtForwardStart.julian()));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		java.lang.String strTenor = forwardLabel.tenor();

		boolean bIsON = "ON".equalsIgnoreCase (strTenor);

		org.drip.analytics.date.JulianDate dtMaturity = dtEffective.addTenor (strTenor);
		
		java.lang.String strCode = (0 == dblStrike ? "FUTURES::" : "FRA::") +
			forwardLabel.fullyQualifiedName() + "::{" + dtEffective + "->" + dtMaturity + "}";

		try {
			int iFreq = bIsON ? 360 : 12 / org.drip.analytics.support.Helper.TenorToMonths (strTenor);

			org.drip.param.period.ComposableFloatingUnitSetting cfus = new
				org.drip.param.period.ComposableFloatingUnitSetting (strTenor, bIsON ?
					org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_OVERNIGHT :
						org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE, null,
							forwardLabel,
								org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
				0.);

			org.drip.param.period.CompositePeriodSetting cps = new
				org.drip.param.period.CompositePeriodSetting (iFreq, strTenor, strCurrency, null, 1., null,
					null, null, null);

			org.drip.product.fra.FRAStandardComponent sscDeposit = new
				org.drip.product.fra.FRAStandardComponent (strCode, new org.drip.product.rates.Stream
					(org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit
						(org.drip.analytics.support.CompositePeriodBuilder.EdgePair (dtEffective,
							dtMaturity), cps, cfus)), dblStrike, new
								org.drip.param.valuation.CashSettleParams (0, strCurrency, 0));

			sscDeposit.setPrimaryCode (strCode);

			return sscDeposit;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a FRA Market Component Instance from the Spot Date, the Forward Label, and the Strike
	 * 
	 * @param dtForwardStart Forward Start Date
	 * @param forwardLabel The Floating Rate Index
	 * @param dblStrike Futures Strike
	 * 
	 * @return The Futures Product
	 */

	public static final org.drip.product.fra.FRAMarketComponent FRAMarket (
		final org.drip.analytics.date.JulianDate dtForwardStart,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final double dblStrike)
	{
		if (null == dtForwardStart || null == forwardLabel) return null;

		org.drip.analytics.date.JulianDate dtEffective = dtForwardStart;

		java.lang.String strTenor = forwardLabel.tenor();

		java.lang.String strCurrency = forwardLabel.currency();

		boolean bIsON = "ON".equalsIgnoreCase (strTenor);

		org.drip.analytics.date.JulianDate dtMaturity = dtEffective.addTenor (strTenor);
		
		java.lang.String strCode = "FUTURES::" + forwardLabel.fullyQualifiedName() + "::{" + dtEffective +
			"->" + dtMaturity + "}";

		try {
			int iFreq = org.drip.analytics.support.Helper.TenorToFreq (strTenor);

			org.drip.param.period.ComposableFloatingUnitSetting cfus = new
				org.drip.param.period.ComposableFloatingUnitSetting (strTenor, bIsON ?
					org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_OVERNIGHT :
						org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE, null,
							forwardLabel,
								org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
				0.);

			org.drip.param.period.CompositePeriodSetting cps = new
				org.drip.param.period.CompositePeriodSetting (iFreq, strTenor, strCurrency, null, 1., null,
					null, null, null);

			org.drip.product.fra.FRAMarketComponent sscDeposit = new org.drip.product.fra.FRAMarketComponent
				(strCode, new org.drip.product.rates.Stream
					(org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit
						(org.drip.analytics.support.CompositePeriodBuilder.EdgePair (dtEffective,
							dtMaturity), cps, cfus)), dblStrike, new
								org.drip.param.valuation.CashSettleParams (0, strCurrency, 0));

			sscDeposit.setPrimaryCode (strCode);

			return sscDeposit;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a Forward Rate Futures Product Instance from the Spot Date and the Forward Label
	 * 
	 * @param dtSpot Spot Date
	 * @param fri The Floating Rate Index
	 * 
	 * @return The Forward Rate Futures Product Instance
	 */

	public static final org.drip.product.fra.FRAStandardComponent ForwardRateFutures (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.state.identifier.ForwardLabel fri)
	{
		return FRAStandard (dtSpot, fri, 0.);
	}
}
