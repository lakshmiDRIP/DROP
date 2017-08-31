
package org.drip.service.product;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * CreditIndexAPI contains the Functionality associated with the Horizon Analysis of the CDS Index.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CreditIndexAPI {

	static class ParCDS {
		double _dblFairPremium = java.lang.Double.NaN;
		double _dblFixedCoupon = java.lang.Double.NaN;
		org.drip.product.definition.CreditDefaultSwap _cds = null;

		ParCDS (
			final org.drip.product.definition.CreditDefaultSwap cds,
			final double dblFixedCoupon,
			final double dblFairPremium)
		{
			_cds = cds;
			_dblFixedCoupon = dblFixedCoupon;
			_dblFairPremium = dblFairPremium;
		}

		double fairPremium()
		{
			return _dblFairPremium;
		}

		double fixedCoupon()
		{
			return _dblFixedCoupon;
		}

		org.drip.product.definition.CreditDefaultSwap cds()
		{
			return _cds;
		}
	};

	private static final ParCDS HorizonCreditIndex (
		final org.drip.state.discount.DiscountCurve dc,
		final org.drip.state.credit.CreditCurve cc,
		final java.lang.String strFullCreditIndexName)
	{
		org.drip.market.otc.CreditIndexConvention cic =
			org.drip.market.otc.CreditIndexConventionContainer.ConventionFromFullName
				(strFullCreditIndexName);

		if (null == cic) return null;

		org.drip.product.definition.CreditDefaultSwap cdsIndex = cic.indexCDS();

		if (null == cdsIndex) return null;

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		if (!csqc.setFundingState ((org.drip.state.discount.MergedDiscountForwardCurve) dc) ||
			!csqc.setCreditState (cc))
			return null;

		try {
			return new ParCDS (cdsIndex, cic.fixedCoupon(), 0.0001 * cdsIndex.measureValue
				(org.drip.param.valuation.ValuationParams.Spot (dc.epoch().julian()), null, csqc, null,
					"FairPremium"));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static final org.drip.historical.attribution.CDSMarketSnap MarketValuationSnap (
		final org.drip.product.definition.CreditDefaultSwap cds,
		final org.drip.state.discount.DiscountCurve dc,
		final org.drip.state.credit.CreditCurve cc,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc,
		final double dblRollDownFairPremium)
	{
		if (!csqc.setFundingState ((org.drip.state.discount.MergedDiscountForwardCurve) dc) ||
			!csqc.setCreditState (cc))
			return null;

		org.drip.analytics.date.JulianDate dt = dc.epoch();

		java.util.Map<java.lang.String, java.lang.Double> mapCDS = cds.value
			(org.drip.param.valuation.ValuationParams.Spot (dt.julian()), null, csqc, null);

		if (null == mapCDS || !mapCDS.containsKey ("Accrued") || !mapCDS.containsKey ("CleanDV01") ||
			!mapCDS.containsKey ("CleanPV") || !mapCDS.containsKey ("CleanCouponPV") || !mapCDS.containsKey
				("CumulativeCouponAmount") || !mapCDS.containsKey ("FairPremium") || !mapCDS.containsKey
					("LossPV"))
			return null;

		double dblCleanPV = mapCDS.get ("CleanPV");

		double dblFairPremium = 0.0001 * mapCDS.get ("FairPremium");

		org.drip.analytics.date.JulianDate dtEffective = cds.effectiveDate();

		double dblFairPremiumSensitivity = 10000. * mapCDS.get ("CleanDV01");

		try {
			org.drip.historical.attribution.CDSMarketSnap cdsms = new
				org.drip.historical.attribution.CDSMarketSnap (dt, dblCleanPV);

			return cdsms.setEffectiveDate (dtEffective) && cdsms.setMaturityDate (cds.maturityDate()) &&
				cdsms.setCleanDV01 (dblFairPremiumSensitivity) && cdsms.setCurrentFairPremium
					(dblFairPremium) && cdsms.setRollDownFairPremium (dblRollDownFairPremium) &&
						cdsms.setAccrued (mapCDS.get ("Accrued")) && cdsms.setCumulativeCouponAmount
							(mapCDS.get ("CumulativeCouponAmount")) && cdsms.setCreditLabel
								(cds.creditLabel().fullyQualifiedName()) && cdsms.setRecoveryRate
									(cds.recovery (dtEffective.julian(), cc)) && cdsms.setCouponPV
										(mapCDS.get ("CleanCouponPV")) && cdsms.setLossPV (mapCDS.get
											("LossPV")) && cdsms.setFairPremiumMarketFactor (dblFairPremium,
												-1. * dblFairPremiumSensitivity, dblRollDownFairPremium) ?
													cdsms : null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static final double RollDownFairPremium (
		final org.drip.product.definition.CreditDefaultSwap cds,
		final int iSpotDate,
		final org.drip.state.discount.DiscountCurve dcPrevious,
		final org.drip.state.credit.CreditCurve ccPrevious,
		final org.drip.param.market.CurveSurfaceQuoteContainer csqc)
		throws java.lang.Exception
	{
		if (!csqc.setFundingState ((org.drip.state.discount.MergedDiscountForwardCurve) dcPrevious) ||
			!csqc.setCreditState (ccPrevious))
			throw new java.lang.Exception ("CreditIndexAPI::RollDownFairPremium => Invalid Inputs");

		java.util.Map<java.lang.String, java.lang.Double> mapCDS = cds.value
			(org.drip.param.valuation.ValuationParams.Spot (iSpotDate), null, csqc, null);

		if (null == mapCDS || !mapCDS.containsKey ("FairPremium"))
			throw new java.lang.Exception ("CreditIndexAPI::RollDownFairPremium => Invalid Inputs");

		return 0.0001 * mapCDS.get ("FairPremium");
	}

	/**
	 * Generate the CDS Horizon Change Attribution
	 * 
	 * @param dcFirst The First Discount Curve
	 * @param ccFirst The First Credit Curve
	 * @param dcSecond The Second Discount Curve
	 * @param ccSecond The Second Credit Curve
	 * @param strFullCreditIndexName The Full Credit Index Name
	 * 
	 * @return The CDS Horizon Change Attribution
	 */

	public static final org.drip.historical.attribution.PositionChangeComponents HorizonChangeAttribution (
		final org.drip.state.discount.DiscountCurve dcFirst,
		final org.drip.state.credit.CreditCurve ccFirst,
		final org.drip.state.discount.DiscountCurve dcSecond,
		final org.drip.state.credit.CreditCurve ccSecond,
		final java.lang.String strFullCreditIndexName)
	{
		if (null == dcFirst || null == ccFirst || null == dcSecond || null == ccSecond) return null;

		int iFirstDate = dcFirst.epoch().julian();

		int iSecondDate = dcSecond.epoch().julian();

		java.lang.String strCurrency = dcSecond.currency();

		if (!strCurrency.equalsIgnoreCase (dcFirst.currency()) || iFirstDate >= iSecondDate ||
			ccFirst.epoch().julian() != iFirstDate || ccSecond.epoch().julian() != iSecondDate)
			return null;

		ParCDS parCDS = HorizonCreditIndex (dcFirst, ccFirst, strFullCreditIndexName);

		if (null == parCDS) return null;

		org.drip.product.definition.CreditDefaultSwap cds = parCDS.cds();

		if (null == cds) return null;

		double dblFixedCoupon = parCDS.fixedCoupon();

		double dblInitialFairPremium = parCDS.fairPremium();

		org.drip.param.market.CurveSurfaceQuoteContainer csqc = new
			org.drip.param.market.CurveSurfaceQuoteContainer();

		double dblRollDownFairPremium = java.lang.Double.NaN;

		try {
			dblRollDownFairPremium = RollDownFairPremium (cds, iSecondDate, dcFirst, ccFirst, csqc);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		if (!org.drip.quant.common.NumberUtil.IsValid (dblRollDownFairPremium)) return null;

		org.drip.historical.attribution.CDSMarketSnap cdsmsFirst = MarketValuationSnap (cds, dcFirst,
			ccFirst, csqc, dblRollDownFairPremium);

		if (null == cdsmsFirst || !cdsmsFirst.setInitialFairPremium (dblInitialFairPremium) ||
			!cdsmsFirst.setFixedCoupon (dblFixedCoupon))
			return null;

		org.drip.historical.attribution.CDSMarketSnap cdsmsSecond = MarketValuationSnap (cds, dcSecond,
			ccSecond, csqc, dblRollDownFairPremium);

		if (null == cdsmsSecond || !cdsmsSecond.setInitialFairPremium (dblInitialFairPremium) ||
			!cdsmsSecond.setFixedCoupon (dblFixedCoupon))
			return null;

		try {
			return new org.drip.historical.attribution.PositionChangeComponents (false, cdsmsFirst,
				cdsmsSecond, cdsmsSecond.cumulativeCouponAmount() - cdsmsFirst.cumulativeCouponAmount(),
					null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Funding/Credit Curve Horizon Metrics
	 * 
	 * @param adtSpot Array of Spot
	 * @param iHorizonGap The Horizon Gap
	 * @param astrFundingFixingMaturityTenor Array of Funding Fixing Maturity Tenors
	 * @param aadblFundingFixingQuote Double Array of Funding Fixing Swap Rates
	 * @param astrFullCreditIndexName Array of the Full Credit Index Names
	 * @param adblCreditIndexQuotedSpread Array of the Quoted Spreads
	 * 
	 * @return The Funding/Credit Curve Horizon Metrics
	 */

	public static final java.util.List<org.drip.historical.attribution.PositionChangeComponents>
		HorizonChangeAttribution (
			final org.drip.analytics.date.JulianDate[] adtSpot,
			final int iHorizonGap,
			final java.lang.String[] astrFundingFixingMaturityTenor,
			final double[][] aadblFundingFixingQuote,
			final java.lang.String[] astrFullCreditIndexName,
			final double[] adblCreditIndexQuotedSpread)
	{
		if (null == adtSpot || 0 >= iHorizonGap || null == astrFundingFixingMaturityTenor || null ==
			aadblFundingFixingQuote || null == astrFullCreditIndexName || null ==
				adblCreditIndexQuotedSpread)
			return null;

		int iNumClose = adtSpot.length;
		int iNumFundingInstrument = astrFundingFixingMaturityTenor.length;

		java.util.List<org.drip.historical.attribution.PositionChangeComponents> lsPCC = new
			java.util.ArrayList<org.drip.historical.attribution.PositionChangeComponents>();

		for (int i = iHorizonGap; i < iNumClose; ++i) {
			int iNumSecondFundingQuote = null == aadblFundingFixingQuote[i] ? 0 :
				aadblFundingFixingQuote[i].length;
			int iNumFirstFundingQuote = null == aadblFundingFixingQuote[i - iHorizonGap] ? 0 :
				aadblFundingFixingQuote[i - iHorizonGap].length;

			if (0 == iNumFirstFundingQuote || iNumFirstFundingQuote != iNumFundingInstrument || 0 ==
				iNumSecondFundingQuote || iNumSecondFundingQuote != iNumFundingInstrument)
				continue;

			org.drip.market.otc.CreditIndexConvention cic =
				org.drip.market.otc.CreditIndexConventionContainer.ConventionFromFullName
					(astrFullCreditIndexName[i]);

			if (null == cic) return null;

			java.lang.String strCurrency = cic.currency();

			org.drip.product.definition.CreditDefaultSwap cdsIndex = cic.indexCDS();

			org.drip.state.discount.MergedDiscountForwardCurve dcFundingFixingFirst =
				org.drip.service.template.LatentMarketStateBuilder.FundingCurve (adtSpot[i - iHorizonGap],
					strCurrency, null, null, "ForwardRate", null, "ForwardRate",
						astrFundingFixingMaturityTenor, aadblFundingFixingQuote[i - iHorizonGap], "SwapRate",
							org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING);

			org.drip.state.credit.CreditCurve ccFirst =
				org.drip.service.template.LatentMarketStateBuilder.CreditCurve (adtSpot[i - iHorizonGap], new
					org.drip.product.definition.CreditDefaultSwap[] {cdsIndex}, new double[]
						{adblCreditIndexQuotedSpread[i - iHorizonGap]}, "FairPremium", dcFundingFixingFirst);

			org.drip.state.discount.MergedDiscountForwardCurve dcFundingFixingSecond =
				org.drip.service.template.LatentMarketStateBuilder.FundingCurve (adtSpot[i], strCurrency,
					null, null, "ForwardRate", null, "ForwardRate", astrFundingFixingMaturityTenor,
						aadblFundingFixingQuote[i], "SwapRate",
							org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING);

			org.drip.state.credit.CreditCurve ccSecond =
				org.drip.service.template.LatentMarketStateBuilder.CreditCurve (adtSpot[i], new
					org.drip.product.definition.CreditDefaultSwap[] {cdsIndex}, new double[]
						{adblCreditIndexQuotedSpread[i]}, "FairPremium", dcFundingFixingSecond);

			lsPCC.add (HorizonChangeAttribution (dcFundingFixingFirst, ccFirst, dcFundingFixingSecond,
				ccSecond, astrFullCreditIndexName[i]));
		}

		return lsPCC;
	}
}
