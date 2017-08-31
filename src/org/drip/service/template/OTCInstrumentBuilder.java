
package org.drip.service.template;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * OTCInstrumentBuilder contains static Helper API to facilitate Construction of OTC Instruments.
 *
 * @author Lakshmi Krishnamurthy
 */

public class OTCInstrumentBuilder {

	/**
	 * Construct an OTC Funding Deposit Instrument from the Spot Date and the Maturity Tenor
	 * 
	 * @param dtSpot The Spot Date
	 * @param strCurrency Currency
	 * @param strMaturityTenor The Maturity Tenor
	 * 
	 * @return Funding Deposit Instrument Instance from the Spot Date and the corresponding Maturity Tenor
	 */

	public static final org.drip.product.rates.SingleStreamComponent FundingDeposit (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCurrency,
		final java.lang.String strMaturityTenor)
	{
		if (null == dtSpot || null == strCurrency || strCurrency.isEmpty() || null == strMaturityTenor ||
			strMaturityTenor.isEmpty())
			return null;

		org.drip.market.otc.FixedFloatSwapConvention ffsc =
			org.drip.market.otc.IBORFixedFloatContainer.ConventionFromJurisdiction (strCurrency, "ALL",
				strMaturityTenor, "MAIN");

		if (null == ffsc) return null;

		org.drip.state.identifier.ForwardLabel forwardLabel = ffsc.floatStreamConvention().floaterIndex();

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (0, strCurrency);

		try {
			java.lang.String strFloaterTenor = forwardLabel.tenor();

			org.drip.analytics.date.JulianDate dtMaturity = strMaturityTenor.contains ("D") ? new
				org.drip.analytics.date.JulianDate (org.drip.analytics.daycount.Convention.AddBusinessDays
					(dtEffective.julian(), org.drip.analytics.support.Helper.TenorToDays (strMaturityTenor),
						strCurrency)) : dtEffective.addTenorAndAdjust (strMaturityTenor, strCurrency);

			return new org.drip.product.rates.SingleStreamComponent ("DEPOSIT_" + strMaturityTenor, new
				org.drip.product.rates.Stream
					(org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit
						(org.drip.analytics.support.CompositePeriodBuilder.EdgePair (dtEffective,
							dtMaturity), new org.drip.param.period.CompositePeriodSetting
								(org.drip.analytics.support.Helper.TenorToFreq (strFloaterTenor),
									strFloaterTenor, strCurrency, null, 1., null, null, null, null), new
										org.drip.param.period.ComposableFloatingUnitSetting (strFloaterTenor,
											org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
				null, org.drip.state.identifier.ForwardLabel.Create (strCurrency, strFloaterTenor),
					org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE, 0.))),
						null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an OTC Forward Deposit Instrument from Spot Date and the Maturity Tenor
	 * 
	 * @param dtSpot The Spot Date
	 * @param strMaturityTenor The Maturity Tenor
	 * @param forwardLabel The Forward Label
	 * 
	 * @return Forward Deposit Instrument Instance from the Spot Date and the corresponding Maturity Tenor
	 */

	public static final org.drip.product.rates.SingleStreamComponent ForwardRateDeposit (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strMaturityTenor,
		final org.drip.state.identifier.ForwardLabel forwardLabel)
	{
		if (null == dtSpot || null == forwardLabel) return null;

		java.lang.String strCalendar = forwardLabel.currency();

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (0, strCalendar);

		return org.drip.product.creator.SingleStreamComponentBuilder.Deposit (dtEffective,
			dtEffective.addTenor (strMaturityTenor), forwardLabel);
	}

	/**
	 * Construct an OTC Overnight Deposit Instrument from the Spot Date and the Maturity Tenor
	 * 
	 * @param dtSpot The Spot Date
	 * @param strCurrency Currency
	 * @param strMaturityTenor The Maturity Tenor
	 * 
	 * @return Overnight Deposit Instrument Instance from the Spot Date and the corresponding Maturity Tenor
	 */

	public static final org.drip.product.rates.SingleStreamComponent OvernightDeposit (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCurrency,
		final java.lang.String strMaturityTenor)
	{
		if (null == dtSpot) return null;

		org.drip.state.identifier.OvernightLabel overnightLabel =
			org.drip.state.identifier.OvernightLabel.Create (strCurrency);

		if (null == overnightLabel) return null;

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (0, strCurrency);

		return null == dtEffective ? null : org.drip.product.creator.SingleStreamComponentBuilder.Deposit
			(dtEffective, dtEffective.addTenorAndAdjust (strMaturityTenor, strCurrency),overnightLabel);
	}

	/**
	 * Create a Standard FRA from the Spot Date, the Forward Label, and the Strike
	 * 
	 * @param dtSpot Spot Date
	 * @param forwardLabel The Forward Label
	 * @param strMaturityTenor Maturity Tenor
	 * @param dblStrike Futures Strike
	 * 
	 * @return The Standard FRA Instance
	 */

	public static final org.drip.product.fra.FRAStandardComponent FRAStandard (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final java.lang.String strMaturityTenor,
		final double dblStrike)
	{
		return null == dtSpot || null == forwardLabel ? null :
			org.drip.product.creator.SingleStreamComponentBuilder.FRAStandard (dtSpot.addBusDays (0,
				forwardLabel.currency()).addTenor (strMaturityTenor), forwardLabel, dblStrike);
	}

	/**
	 * Construct an OTC Standard Fix Float Swap using the specified Input Parameters
	 * 
	 * @param dtSpot The Spot Date
	 * @param strCurrency The OTC Currency
	 * @param strLocation Location
	 * @param strMaturityTenor Maturity Tenor
	 * @param strIndex Index
	 * @param dblCoupon Coupon
	 * 
	 * @return The OTC Standard Fix Float Swap constructed using the specified Input Parameters
	 */

	public static final org.drip.product.rates.FixFloatComponent FixFloatStandard (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCurrency,
		final java.lang.String strLocation,
		final java.lang.String strMaturityTenor,
		final java.lang.String strIndex,
		final double dblCoupon)
	{
		if (null == dtSpot) return null;

		org.drip.market.otc.FixedFloatSwapConvention ffsc =
			org.drip.market.otc.IBORFixedFloatContainer.ConventionFromJurisdiction (strCurrency, strLocation,
				strMaturityTenor, strIndex);

		return null == ffsc ? null : ffsc.createFixFloatComponent (dtSpot.addBusDays (0, strCurrency),
			strMaturityTenor, dblCoupon, 0., 1.);
	}

	/**
	 * Construct a Standard Fix Float Swap Instances
	 * 
	 * @param dtSpot The Spot Date
	 * @param forwardLabel The Forward Label
	 * @param strMaturityTenor Maturity Tenor
	 * 
	 * @return A Standard Fix Float Swap Instances
	 */

	public static final org.drip.product.rates.FixFloatComponent FixFloatCustom (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final java.lang.String strMaturityTenor)
	{
		if (null == dtSpot || null == forwardLabel) return null;

		java.lang.String strCurrency = forwardLabel.currency();

		java.lang.String strForwardTenor = forwardLabel.tenor();

		int iTenorInMonths = new java.lang.Integer (strForwardTenor.split ("M")[0]);

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (0, strCurrency).addDays (2);

		org.drip.market.otc.FixedFloatSwapConvention ffsc =
			org.drip.market.otc.IBORFixedFloatContainer.ConventionFromJurisdiction (strCurrency, "ALL",
				strMaturityTenor, "MAIN");

		if (null == ffsc) return null;

		try {
			org.drip.param.period.ComposableFloatingUnitSetting cfusFloating = new
				org.drip.param.period.ComposableFloatingUnitSetting (strForwardTenor,
					org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR, null,
						forwardLabel,
							org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
								0.);

			org.drip.param.period.CompositePeriodSetting cpsFloating = new
				org.drip.param.period.CompositePeriodSetting (12 / iTenorInMonths, strForwardTenor,
					strCurrency, null, -1., null, null, null, null);

			org.drip.product.rates.Stream floatingStream = new org.drip.product.rates.Stream
				(org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit
					(org.drip.analytics.support.CompositePeriodBuilder.RegularEdgeDates (dtEffective,
						strForwardTenor, strMaturityTenor, null), cpsFloating, cfusFloating));

			org.drip.product.rates.Stream fixedStream = ffsc.fixedStreamConvention().createStream
				(dtEffective, strMaturityTenor, 0., 1.);

			org.drip.product.rates.FixFloatComponent ffc = new org.drip.product.rates.FixFloatComponent
				(fixedStream, floatingStream, null);

			ffc.setPrimaryCode ("FixFloat:" + strMaturityTenor);

			return ffc;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of OTC OIS Fix Float Swap
	 * 
	 * @param dtSpot Spot Date
	 * @param strCurrency Currency
	 * @param strMaturityTenor The OIS Maturity Tenor
	 * @param dblCoupon The Fixed Coupon Rate
	 * @param bFund TRUE - Floater Based off of Fund
	 * 
	 * @return Instance of OIS Fix Float Swap
	 */

	public static final org.drip.product.rates.FixFloatComponent OISFixFloat (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCurrency,
		final java.lang.String strMaturityTenor,
		final double dblCoupon,
		final boolean bFund)
	{
		if (null == dtSpot) return null;

		org.drip.market.otc.FixedFloatSwapConvention ffsc = bFund ?
			org.drip.market.otc.OvernightFixedFloatContainer.FundConventionFromJurisdiction (strCurrency) :
				org.drip.market.otc.OvernightFixedFloatContainer.IndexConventionFromJurisdiction (strCurrency,
					strMaturityTenor);

		return null == ffsc ? null : ffsc.createFixFloatComponent (dtSpot.addBusDays (0, strCurrency),
			strMaturityTenor, dblCoupon, 0., 1.);
	}

	/**
	 * Construct an OTC Float-Float Swap Instance
	 * 
	 * @param dtSpot Spot Date
	 * @param strCurrency Currency 
	 * @param strDerivedTenor Tenor of the Derived Leg
	 * @param strMaturityTenor Maturity Tenor of the Float-Float Swap
	 * @param dblBasis The Float-Float Swap Basis
	 * 
	 * @return The OTC Float-Float Swap Instance
	 */

	public static final org.drip.product.rates.FloatFloatComponent FloatFloat (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCurrency,
		final java.lang.String strDerivedTenor,
		final java.lang.String strMaturityTenor,
		final double dblBasis)
	{
		if (null == dtSpot) return null;

		org.drip.market.otc.FloatFloatSwapConvention ffsc =
			org.drip.market.otc.IBORFloatFloatContainer.ConventionFromJurisdiction (strCurrency);

		return null == ffsc ? null : ffsc.createFloatFloatComponent (dtSpot.addBusDays (0, strCurrency),
			strDerivedTenor, strMaturityTenor, dblBasis, 1.);
	}

	/**
	 * Create an Instance of the OTC CDS.
	 * 
	 * @param dtSpot The Spot Date
	 * @param strMaturityTenor Maturity Tenor
	 * @param dblCoupon Coupon
	 * @param strCurrency Currency
	 * @param strCredit Credit Curve
	 * 
	 * @return The OTC CDS Instance
	 */

	public static final org.drip.product.definition.CreditDefaultSwap CDS (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strMaturityTenor,
		final double dblCoupon,
		final java.lang.String strCurrency,
		final java.lang.String strCredit)
	{
		if (null == dtSpot || null == strCurrency) return null;

		org.drip.analytics.date.JulianDate dtFirstCoupon = dtSpot.addBusDays (0, strCurrency).nextCreditIMM
			(3);

		return null == dtFirstCoupon ? null : org.drip.product.creator.CDSBuilder.CreateCDS 
			(dtFirstCoupon.subtractTenor ("3M"), dtFirstCoupon.addTenor (strMaturityTenor), dblCoupon,
				strCurrency, "CAD".equalsIgnoreCase (strCurrency) || "EUR".equalsIgnoreCase (strCurrency) ||
					"GBP".equalsIgnoreCase (strCurrency) || "HKD".equalsIgnoreCase (strCurrency) ||
						"USD".equalsIgnoreCase (strCurrency) ? 0.40 : 0.25, strCredit, strCurrency, true);
	}

	/**
	 * Create an OTC FX Forward Component
	 * 
	 * @param dtSpot Spot Date
	 * @param ccyPair Currency Pair
	 * @param strMaturityTenor Maturity Tenor
	 * 
	 * @return The OTC FX Forward Component Instance
	 */

	public static final org.drip.product.fx.FXForwardComponent FXForward (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.product.params.CurrencyPair ccyPair,
		final java.lang.String strMaturityTenor)
	{
		if (null == dtSpot || null == ccyPair) return null;

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (0, ccyPair.denomCcy());

		try {
			return new org.drip.product.fx.FXForwardComponent ("FXFWD::" + ccyPair.code() + "::" +
				strMaturityTenor, ccyPair, dtEffective.julian(), dtEffective.addTenor
					(strMaturityTenor).julian(), 1., null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Array of OTC Funding Deposit Instruments from their corresponding Maturity Tenors
	 * 
	 * @param dtSpot Spot Date
	 * @param strCurrency Currency
	 * @param astrMaturityTenor Array of Maturity Tenors
	 * 
	 * @return Array of OTC Funding Deposit Instruments from their corresponding Maturity Tenors
	 */

	public static final org.drip.product.rates.SingleStreamComponent[] FundingDeposit (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCurrency,
		final java.lang.String[] astrMaturityTenor)
	{
		if (null == astrMaturityTenor) return null;

		int iNumDeposit = astrMaturityTenor.length;
		org.drip.product.rates.SingleStreamComponent[] aSSCDeposit = new
			org.drip.product.rates.SingleStreamComponent[iNumDeposit];

		if (0 == iNumDeposit) return null;

		for (int i = 0; i < astrMaturityTenor.length; ++i) {
			if (null == (aSSCDeposit[i] = FundingDeposit (dtSpot, strCurrency, astrMaturityTenor[i])))
				return null;

			aSSCDeposit[i].setPrimaryCode (astrMaturityTenor[i]);
		}

		return aSSCDeposit;
	}

	/**
	 * Construct an Array of OTC Forward Deposit Instruments from the corresponding Maturity Tenors
	 * 
	 * @param dtSpot Spot Date
	 * @param astrMaturityTenor Array of Maturity Tenors
	 * @param forwardLabel The Forward Label
	 * 
	 * @return Forward Deposit Instrument Instance from the corresponding Maturity Tenor
	 */

	public static final org.drip.product.rates.SingleStreamComponent[] ForwardRateDeposit (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String[] astrMaturityTenor,
		final org.drip.state.identifier.ForwardLabel forwardLabel)
	{
		if (null == astrMaturityTenor) return null;

		int iNumDeposit = astrMaturityTenor.length;
		org.drip.product.rates.SingleStreamComponent[] aSSCDeposit = new
			org.drip.product.rates.SingleStreamComponent[iNumDeposit];

		if (0 == iNumDeposit) return null;

		for (int i = 0; i < astrMaturityTenor.length; ++i) {
			if (null == (aSSCDeposit[i] = ForwardRateDeposit (dtSpot, astrMaturityTenor[i], forwardLabel)))
				return null;

			aSSCDeposit[i].setPrimaryCode (astrMaturityTenor[i]);
		}

		return aSSCDeposit;
	}

	/**
	 * Construct an Array of OTC Overnight Deposit Instrument from their Maturity Tenors
	 * 
	 * @param dtSpot Spot Date
	 * @param strCurrency Currency
	 * @param astrMaturityTenor Array of Maturity Tenor
	 * 
	 * @return Array of Overnight Deposit Instrument from their Maturity Tenors
	 */

	public static final org.drip.product.rates.SingleStreamComponent[] OvernightDeposit (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCurrency,
		final java.lang.String[] astrMaturityTenor)
	{
		if (null == astrMaturityTenor) return null;

		int iNumDeposit = astrMaturityTenor.length;
		org.drip.product.rates.SingleStreamComponent[] aSSCDeposit = new
			org.drip.product.rates.SingleStreamComponent[iNumDeposit];

		if (0 == iNumDeposit) return null;

		for (int i = 0; i < iNumDeposit; ++i) {
			if (null == (aSSCDeposit[i] = OvernightDeposit (dtSpot, strCurrency, astrMaturityTenor[i])))
				return null;
		}
		return aSSCDeposit;
	}

	/**
	 * Create an Array of Standard FRAs from the Spot Date, the Forward Label, and the Strike
	 * 
	 * @param dtSpot Spot Date
	 * @param forwardLabel The Forward Label
	 * @param astrMaturityTenor Array of Maturity Tenors
	 * @param adblFRAStrike Array of FRA Strikes
	 * 
	 * @return Array of Standard FRA Instances
	 */

	public static final org.drip.product.fra.FRAStandardComponent[] FRAStandard (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final java.lang.String[] astrMaturityTenor,
		final double[] adblFRAStrike)
	{
		if (null == astrMaturityTenor || null == adblFRAStrike) return null;

		int iNumFRA = astrMaturityTenor.length;
		org.drip.product.fra.FRAStandardComponent[] aFRA = new
			org.drip.product.fra.FRAStandardComponent[iNumFRA];

		if (0 == iNumFRA || iNumFRA != adblFRAStrike.length) return null;

		for (int i = 0; i < iNumFRA; ++i) {
			if (null == (aFRA[i] = FRAStandard (dtSpot, forwardLabel, astrMaturityTenor[i],
				adblFRAStrike[i])))
				return null;
		}

		return aFRA;
	}

	/**
	 * Construct an Array of OTC Fix Float Swaps using the specified Input Parameters
	 * 
	 * @param dtSpot The Spot Date
	 * @param strCurrency The OTC Currency
	 * @param strLocation Location
	 * @param astrMaturityTenor Array of Maturity Tenors
	 * @param strIndex Index
	 * @param dblCoupon Coupon
	 * 
	 * @return The Array of OTC Fix Float Swaps
	 */

	public static final org.drip.product.rates.FixFloatComponent[] FixFloatStandard (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCurrency,
		final java.lang.String strLocation,
		final java.lang.String[] astrMaturityTenor,
		final java.lang.String strIndex,
		final double dblCoupon)
	{
		if (null == astrMaturityTenor) return null;

		int iNumFixFloat = astrMaturityTenor.length;
		org.drip.product.rates.FixFloatComponent[] aFFC = new
			org.drip.product.rates.FixFloatComponent[iNumFixFloat];

		if (0 == iNumFixFloat) return null;

		for (int i = 0; i < iNumFixFloat; ++i) {
			if (null == (aFFC[i] = FixFloatStandard (dtSpot, strCurrency, strLocation, astrMaturityTenor[i],
				strIndex, 0.)))
				return null;
		}

		return aFFC;
	}

	/**
	 * Construct an Array of Custom Fix Float Swap Instances
	 * 
	 * @param dtSpot The Spot Date
	 * @param forwardLabel The Forward Label
	 * @param astrMaturityTenor Array of Maturity Tenors
	 * 
	 * @return Array of Custom Fix Float Swap Instances
	 */

	public static final org.drip.product.rates.FixFloatComponent[] FixFloatCustom (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final java.lang.String[] astrMaturityTenor)
	{
		if (null == dtSpot || null == forwardLabel || null == astrMaturityTenor) return null;

		int iNumComp = astrMaturityTenor.length;
		org.drip.param.period.CompositePeriodSetting cpsFloating = null;
		org.drip.param.period.ComposableFloatingUnitSetting cfusFloating = null;
		org.drip.product.rates.FixFloatComponent[] aFFC = new
			org.drip.product.rates.FixFloatComponent[iNumComp];

		if (0 == iNumComp) return null;

		java.lang.String strCurrency = forwardLabel.currency();

		java.lang.String strForwardTenor = forwardLabel.tenor();

		int iTenorInMonths = new java.lang.Integer (strForwardTenor.split ("M")[0]);

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (0, strCurrency).addDays (2);

		try {
			cfusFloating = new org.drip.param.period.ComposableFloatingUnitSetting (strForwardTenor,
				org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_REGULAR, null,
					forwardLabel,
						org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE, 0.);

			cpsFloating = new org.drip.param.period.CompositePeriodSetting (12 / iTenorInMonths,
				strForwardTenor, strCurrency, null, -1., null, null, null, null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i < iNumComp; ++i) {
			org.drip.market.otc.FixedFloatSwapConvention ffsc =
				org.drip.market.otc.IBORFixedFloatContainer.ConventionFromJurisdiction (strCurrency, "ALL",
					astrMaturityTenor[i], "MAIN");

			if (null == ffsc) return null;

			try {
				org.drip.product.rates.Stream floatingStream = new org.drip.product.rates.Stream
					(org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit
						(org.drip.analytics.support.CompositePeriodBuilder.RegularEdgeDates (dtEffective,
							strForwardTenor, astrMaturityTenor[i], null), cpsFloating, cfusFloating));

				org.drip.product.rates.Stream fixedStream = ffsc.fixedStreamConvention().createStream
					(dtEffective, astrMaturityTenor[i], 0., 1.);

				aFFC[i] = new org.drip.product.rates.FixFloatComponent (fixedStream, floatingStream, null);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			aFFC[i].setPrimaryCode ("FixFloat:" + astrMaturityTenor[i]);
		}

		return aFFC;
	}

	/**
	 * Construct an Array of OTC Fix Float OIS Instances
	 * 
	 * @param dtSpot Spot Date
	 * @param strCurrency Currency
	 * @param astrMaturityTenor Array of OIS Maturity Tenors
	 * @param adblCoupon OIS Fixed Rate Coupon
	 * @param bFund TRUE - Floater Based off of Fund
	 * 
	 * @return Array of Fix Float OIS Instances
	 */

	public static final org.drip.product.rates.FixFloatComponent[] OISFixFloat (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCurrency,
		final java.lang.String[] astrMaturityTenor,
		final double[] adblCoupon,
		final boolean bFund)
	{
		if (null == astrMaturityTenor) return null;

		int iNumOIS = astrMaturityTenor.length;
		org.drip.product.rates.FixFloatComponent[] aFixFloatOIS = new
			org.drip.product.rates.FixFloatComponent[iNumOIS];

		if (0 == iNumOIS) return null;

		for (int i = 0; i < iNumOIS; ++i) {
			if (null == (aFixFloatOIS[i] = OISFixFloat (dtSpot, strCurrency, astrMaturityTenor[i],
				adblCoupon[i], bFund)))
				return null;
		}

		return aFixFloatOIS;
	}

	/**
	 * Construct an Array of OTC OIS Fix-Float Futures
	 * 
	 * @param dtSpot Spot Date
	 * @param strCurrency Currency
	 * @param astrEffectiveTenor Array of Effective Tenors
	 * @param astrMaturityTenor Array of Maturity Tenors
	 * @param adblCoupon Array of Coupons
	 * @param bFund TRUE - Floater Based off of Fund
	 * 
	 * @return Array of OIS Fix-Float Futures
	 */

	public static final org.drip.product.rates.FixFloatComponent[] OISFixFloatFutures (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCurrency,
		final java.lang.String[] astrEffectiveTenor,
		final java.lang.String[] astrMaturityTenor,
		final double[] adblCoupon,
		final boolean bFund)
	{
		if (null == dtSpot || null == astrEffectiveTenor || null == astrMaturityTenor || null == adblCoupon)
			return null;

		int iNumOISFutures = astrEffectiveTenor.length;
		org.drip.product.rates.FixFloatComponent[] aOISFutures = new
			org.drip.product.rates.FixFloatComponent[iNumOISFutures];

		if (0 == iNumOISFutures || iNumOISFutures != astrMaturityTenor.length || iNumOISFutures !=
			adblCoupon.length)
			return null;

		for (int i = 0; i < iNumOISFutures; ++i) {
			if (null == (aOISFutures[i] = OISFixFloat (dtSpot.addTenor (astrEffectiveTenor[i]), strCurrency,
				astrMaturityTenor[i], adblCoupon[i], bFund)))
				return null;
		}

		return aOISFutures;
	}

	/**
	 * Construct an Array of OTC Float-Float Swap Instances
	 * 
	 * @param dtSpot Spot Date
	 * @param strCurrency Currency 
	 * @param strDerivedTenor Tenor of the Derived Leg
	 * @param astrMaturityTenor Array of the Float-Float Swap Maturity Tenors
	 * @param dblBasis The Float-Float Swap Basis
	 * 
	 * @return Array of OTC Float-Float Swap Instances
	 */

	public static final org.drip.product.rates.FloatFloatComponent[] FloatFloat (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String strCurrency,
		final java.lang.String strDerivedTenor,
		final java.lang.String[] astrMaturityTenor,
		final double dblBasis)
	{
		if (null == astrMaturityTenor) return null;

		org.drip.market.otc.FloatFloatSwapConvention ffsc =
			org.drip.market.otc.IBORFloatFloatContainer.ConventionFromJurisdiction (strCurrency);

		int iNumFFC = astrMaturityTenor.length;
		org.drip.product.rates.FloatFloatComponent[] aFFC = new
			org.drip.product.rates.FloatFloatComponent[iNumFFC];

		if (null == ffsc || 0 == iNumFFC) return null;

		for (int i = 0; i < iNumFFC; ++i) {
			if (null == (aFFC[i] = ffsc.createFloatFloatComponent (dtSpot, strDerivedTenor,
				astrMaturityTenor[i], dblBasis, 1.)))
				return null;
		}

		return aFFC;
	}

	/**
	 * Create an Array of the OTC CDS Instance.
	 * 
	 * @param dtSpot Spot Date
	 * @param astrMaturityTenor Array of Maturity Tenors
	 * @param adblCoupon Array of Coupon
	 * @param strCurrency Currency
	 * @param strCredit Credit Curve
	 * 
	 * @return Array of OTC CDS Instances
	 */

	public static final org.drip.product.definition.CreditDefaultSwap[] CDS (
		final org.drip.analytics.date.JulianDate dtSpot,
		final java.lang.String[] astrMaturityTenor,
		final double[] adblCoupon,
		final java.lang.String strCurrency,
		final java.lang.String strCredit)
	{
		if (null == dtSpot || null == strCurrency || null == astrMaturityTenor || null == adblCoupon)
			return null;

		int iNumCDS = astrMaturityTenor.length;
		java.lang.String strCalendar = strCurrency;
		org.drip.product.definition.CreditDefaultSwap[] aCDS = new
			org.drip.product.definition.CreditDefaultSwap[iNumCDS];

		if (0 == iNumCDS || iNumCDS != adblCoupon.length) return null;

		org.drip.analytics.date.JulianDate dtFirstCoupon = dtSpot.addBusDays (0, strCalendar).nextCreditIMM
			(3);

		if (null == dtFirstCoupon) return null;

		org.drip.analytics.date.JulianDate dtEffective = dtFirstCoupon.subtractTenor ("3M");

		if (null == dtEffective) return null;

		double dblRecovery = "CAD".equalsIgnoreCase (strCurrency) || "EUR".equalsIgnoreCase (strCurrency) ||
			"GBP".equalsIgnoreCase (strCurrency) || "HKD".equalsIgnoreCase (strCurrency) ||
				"USD".equalsIgnoreCase (strCurrency) ? 0.40 : 0.25;

		for (int i = 0; i < iNumCDS; ++i)
			aCDS[i] = org.drip.product.creator.CDSBuilder.CreateCDS (dtEffective, dtFirstCoupon.addTenor
				(astrMaturityTenor[i]), adblCoupon[i], strCurrency, dblRecovery, strCredit, strCalendar,
					true);

		return aCDS;
	}

	/**
	 * Create an Array of OTC FX Forward Components
	 * 
	 * @param dtSpot Spot Date
	 * @param ccyPair Currency Pair
	 * @param astrMaturityTenor Array of Maturity Tenors
	 * 
	 * @return Array of OTC FX Forward Component Instances
	 */

	public static final org.drip.product.fx.FXForwardComponent[] FXForward (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.product.params.CurrencyPair ccyPair,
		final java.lang.String[] astrMaturityTenor)
	{
		if (null == astrMaturityTenor) return null;

		int iNumFXComp = astrMaturityTenor.length;
		org.drip.product.fx.FXForwardComponent[] aFXFC = new 
			org.drip.product.fx.FXForwardComponent[iNumFXComp];

		if (0 == iNumFXComp) return null;

		for (int i = 0; i < iNumFXComp; ++i)
			aFXFC[i] = FXForward (dtSpot, ccyPair, astrMaturityTenor[i]);

		return aFXFC;
	}

	/**
	 * Construct an Instance of the Standard OTC FRA Cap/Floor
	 * 
	 * @param dtSpot Spot Date
	 * @param forwardLabel The Forward Label
	 * @param strMaturityTenor Cap/Floor Maturity Tenor
	 * @param dblStrike Cap/Floor Strike
	 * @param bIsCap TRUE - Contract is a Cap
	 * 
	 * @return The Cap/Floor Instance
	 */

	public static final org.drip.product.fra.FRAStandardCapFloor CapFloor (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final java.lang.String strMaturityTenor,
		final double dblStrike,
		final boolean bIsCap)
	{
		if (null == dtSpot || null == forwardLabel) return null;

		java.lang.String strForwardTenor = forwardLabel.tenor();

		java.lang.String strCurrency = forwardLabel.currency();

		java.lang.String strCalendar = strCurrency;

		org.drip.analytics.date.JulianDate dtEffective = dtSpot.addBusDays (0, strCalendar);

		try {
			org.drip.param.period.ComposableFloatingUnitSetting cfus = new
				org.drip.param.period.ComposableFloatingUnitSetting (strForwardTenor,
					org.drip.analytics.support.CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE, null,
						forwardLabel,
							org.drip.analytics.support.CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
								0.);

			org.drip.param.period.CompositePeriodSetting cps = new
				org.drip.param.period.CompositePeriodSetting
					(org.drip.analytics.support.Helper.TenorToFreq (strForwardTenor),
						strForwardTenor, strCurrency, null, 1., null, null, null, null);

			org.drip.product.rates.Stream floatStream = new org.drip.product.rates.Stream
				(org.drip.analytics.support.CompositePeriodBuilder.FloatingCompositeUnit
					(org.drip.analytics.support.CompositePeriodBuilder.RegularEdgeDates
						(dtEffective.julian(), strForwardTenor, strMaturityTenor, null), cps, cfus));

			return new org.drip.product.fra.FRAStandardCapFloor (forwardLabel.fullyQualifiedName() + (bIsCap
				? "::CAP" : "::FLOOR"), floatStream, "ParForward", bIsCap, dblStrike, new
					org.drip.product.params.LastTradingDateSetting
						(org.drip.product.params.LastTradingDateSetting.MID_CURVE_OPTION_QUARTERLY, "",
							java.lang.Integer.MIN_VALUE), null, new
								org.drip.pricer.option.BlackScholesAlgorithm());
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of the Standard OTC FRA Cap/Floor
	 * 
	 * @param dtSpot Spot Date
	 * @param forwardLabel The Forward Label
	 * @param astrMaturityTenor Array of Cap/Floor Maturity Tenors
	 * @param adblStrike Array of Cap/Floor Strikes
	 * @param bIsCap TRUE - Contract is a Cap
	 * 
	 * @return The Cap/Floor Instance
	 */

	public static final org.drip.product.fra.FRAStandardCapFloor[] CapFloor (
		final org.drip.analytics.date.JulianDate dtSpot,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final java.lang.String[] astrMaturityTenor,
		final double[] adblStrike,
		final boolean bIsCap)
	{
		if (null == astrMaturityTenor || null == adblStrike) return null;

		int iNumCapFloor = astrMaturityTenor.length;
		org.drip.product.fra.FRAStandardCapFloor[] aFRACapFloor = new
			org.drip.product.fra.FRAStandardCapFloor[iNumCapFloor];

		if (0 == iNumCapFloor || iNumCapFloor != adblStrike.length) return null;

		for (int i = 0; i < iNumCapFloor; ++i) {
			if (null == (aFRACapFloor[i] = org.drip.service.template.OTCInstrumentBuilder.CapFloor (dtSpot,
				forwardLabel, astrMaturityTenor[i], adblStrike[i], bIsCap)))
				return null;
		}

		return aFRACapFloor;
	}
}
