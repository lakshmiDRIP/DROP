
package org.drip.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.drip.analytics.date.JulianDate;
import org.drip.historical.attribution.CDSMarketSnap;
import org.drip.historical.attribution.PositionChangeComponents;
import org.drip.market.otc.CreditIndexConvention;
import org.drip.market.otc.CreditIndexConventionContainer;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.CreditDefaultSwap;
import org.drip.state.credit.CreditCurve;
import org.drip.state.discount.DiscountCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>CreditIndexAPI</i> contains the Functionality associated with the Horizon Analysis of the CDS Index. It
 * 	provides the following Functionality:
 *
 *  <ul>
 * 		<li>Encode a list into JSON text and write it to out.</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/product/README.md">Product Horizon PnL Attribution Decomposition</a></td></tr>
 *  </table>
 *	<br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CreditIndexAPI
{

	/**
	 * Implementation of <i>ParCDS</i> Inner Class
	 */

	static class ParCDS
	{
		double _fairPremium = Double.NaN;
		double _fixedCoupon = Double.NaN;
		CreditDefaultSwap _creditDefaultSwap = null;

		/**
		 * <i>ParCDS</i> Constructor
		 * 
		 * @param creditDefaultSwap CDS Instance
		 * @param fixedCoupon Fixed Coupon
		 * @param fairPremium Fair Premium
		 */

		ParCDS (
			final CreditDefaultSwap creditDefaultSwap,
			final double fixedCoupon,
			final double fairPremium)
		{
			_fairPremium = fairPremium;
			_fixedCoupon = fixedCoupon;
			_creditDefaultSwap = creditDefaultSwap;
		}

		/**
		 * Retrieve the Fair Premium
		 * 
		 * @return Fair Premium
		 */

		double fairPremium()
		{
			return _fairPremium;
		}

		/**
		 * Retrieve the Fixed Coupon
		 * 
		 * @return Fixed Coupon
		 */

		double fixedCoupon()
		{
			return _fixedCoupon;
		}

		/**
		 * Retrieve the CDS Instance
		 * 
		 * @return CDS Instance
		 */

		CreditDefaultSwap cds()
		{
			return _creditDefaultSwap;
		}
	};

	private static final ParCDS HorizonCreditIndex (
		final DiscountCurve discountCurve,
		final CreditCurve creditCurve,
		final String fullCreditIndexName)
	{
		CreditIndexConvention creditIndexConvention =
			CreditIndexConventionContainer.ConventionFromFullName (fullCreditIndexName);

		if (null == creditIndexConvention) {
			return null;
		}

		CreditDefaultSwap cdsIndex = creditIndexConvention.indexCDS();

		if (null == cdsIndex) {
			return null;
		}

		CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		if (!curveSurfaceQuoteContainer.setFundingState ((MergedDiscountForwardCurve) discountCurve) ||
			!curveSurfaceQuoteContainer.setCreditState (creditCurve))
		{
			return null;
		}

		try {
			return new ParCDS (
				cdsIndex,
				creditIndexConvention.fixedCoupon(),
				0.0001 * cdsIndex.measureValue (
					ValuationParams.Spot (discountCurve.epoch().julian()),
					null,
					curveSurfaceQuoteContainer,
					null,
					"FairPremium"
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static final CDSMarketSnap MarketValuationSnap (
		final CreditDefaultSwap creditDefaultSwap,
		final DiscountCurve discountCurve,
		final CreditCurve creditCurve,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer,
		final double rollDownFairPremium)
	{
		if (!curveSurfaceQuoteContainer.setFundingState ((MergedDiscountForwardCurve) discountCurve) ||
			!curveSurfaceQuoteContainer.setCreditState (creditCurve))
		{
			return null;
		}

		JulianDate epochDate = discountCurve.epoch();

		Map<String, Double> creditDefaultSwapMap = creditDefaultSwap.value (
			ValuationParams.Spot (epochDate.julian()),
			null,
			curveSurfaceQuoteContainer,
			null
		);

		if (null == creditDefaultSwapMap ||
			!creditDefaultSwapMap.containsKey ("Accrued") ||
			!creditDefaultSwapMap.containsKey ("CleanDV01") ||
			!creditDefaultSwapMap.containsKey ("CleanPV") ||
			!creditDefaultSwapMap.containsKey ("CleanCouponPV") ||
			!creditDefaultSwapMap.containsKey ("CumulativeCouponAmount") ||
			!creditDefaultSwapMap.containsKey ("FairPremium") ||
			!creditDefaultSwapMap.containsKey ("LossPV"))
		{
			return null;
		}

		double cleanPV = creditDefaultSwapMap.get ("CleanPV");

		JulianDate effectiveDate = creditDefaultSwap.effectiveDate();

		double fairPremium = 0.0001 * creditDefaultSwapMap.get ("FairPremium");

		double fairPremiumSensitivity = 10000. * creditDefaultSwapMap.get ("CleanDV01");

		try {
			CDSMarketSnap cdsMarketSnap = new CDSMarketSnap (epochDate, cleanPV);

			return cdsMarketSnap.setEffectiveDate (effectiveDate) &&
				cdsMarketSnap.setMaturityDate (creditDefaultSwap.maturityDate()) &&
				cdsMarketSnap.setCleanDV01 (fairPremiumSensitivity) &&
				cdsMarketSnap.setCurrentFairPremium (fairPremium) &&
				cdsMarketSnap.setRollDownFairPremium (rollDownFairPremium) &&
				cdsMarketSnap.setAccrued (creditDefaultSwapMap.get ("Accrued")) &&
				cdsMarketSnap.setCumulativeCouponAmount (
					creditDefaultSwapMap.get ("CumulativeCouponAmount")
				) &&
				cdsMarketSnap.setCreditLabel (creditDefaultSwap.creditLabel().fullyQualifiedName()) &&
				cdsMarketSnap.setRecoveryRate (
					creditDefaultSwap.recovery (effectiveDate.julian(), creditCurve)
				) &&
				cdsMarketSnap.setCouponPV (creditDefaultSwapMap.get ("CleanCouponPV")) &&
				cdsMarketSnap.setLossPV (creditDefaultSwapMap.get ("LossPV")) &&
				cdsMarketSnap.setFairPremiumMarketFactor (
					fairPremium,
					-1. * fairPremiumSensitivity,
					rollDownFairPremium
				) ? cdsMarketSnap : null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static final double RollDownFairPremium (
		final CreditDefaultSwap creditDefaultSwap,
		final int spotDate,
		final DiscountCurve previousDiscountCurve,
		final CreditCurve previousCreditCurve,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer)
		throws Exception
	{
		if (!curveSurfaceQuoteContainer.setFundingState ((MergedDiscountForwardCurve) previousDiscountCurve)
			|| !curveSurfaceQuoteContainer.setCreditState (previousCreditCurve))
		{
			throw new Exception ("CreditIndexAPI::RollDownFairPremium => Invalid Inputs");
		}

		Map<String, Double> creditDefaultSwapMap = creditDefaultSwap.value (
			ValuationParams.Spot (spotDate),
			null,
			curveSurfaceQuoteContainer,
			null
		);

		if (null == creditDefaultSwapMap || !creditDefaultSwapMap.containsKey ("FairPremium")) {
			throw new Exception ("CreditIndexAPI::RollDownFairPremium => Invalid Inputs");
		}

		return 0.0001 * creditDefaultSwapMap.get ("FairPremium");
	}

	/**
	 * Generate the CDS Horizon Change Attribution
	 * 
	 * @param firstDiscountCurve The First Discount Curve
	 * @param firstCreditCurve The First Credit Curve
	 * @param secondDiscountCurve The Second Discount Curve
	 * @param secondCreditCurve The Second Credit Curve
	 * @param fullCreditIndexName The Full Credit Index Name
	 * 
	 * @return The CDS Horizon Change Attribution
	 */

	public static final PositionChangeComponents HorizonChangeAttribution (
		final DiscountCurve firstDiscountCurve,
		final CreditCurve firstCreditCurve,
		final DiscountCurve secondDiscountCurve,
		final CreditCurve secondCreditCurve,
		final String fullCreditIndexName)
	{
		if (null == firstDiscountCurve ||
			null == firstCreditCurve ||
			null == secondDiscountCurve ||
			null == secondCreditCurve)
		{
			return null;
		}

		int secondDate = secondDiscountCurve.epoch().julian();

		int firstDate = firstDiscountCurve.epoch().julian();

		String currency = secondDiscountCurve.currency();

		if (!currency.equalsIgnoreCase (firstDiscountCurve.currency()) ||
			firstDate >= secondDate ||
			firstCreditCurve.epoch().julian() != firstDate ||
			secondCreditCurve.epoch().julian() != secondDate)
		{
			return null;
		}

		ParCDS parCDS = HorizonCreditIndex (firstDiscountCurve, firstCreditCurve, fullCreditIndexName);

		if (null == parCDS) {
			return null;
		}

		CreditDefaultSwap cds = parCDS.cds();

		if (null == cds) {
			return null;
		}

		double rollDownFairPremium = Double.NaN;

		double fixedCoupon = parCDS.fixedCoupon();

		double initialFairPremium = parCDS.fairPremium();

		CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		try {
			rollDownFairPremium = RollDownFairPremium (
				cds,
				secondDate,
				firstDiscountCurve,
				firstCreditCurve,
				curveSurfaceQuoteContainer
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		if (!NumberUtil.IsValid (rollDownFairPremium)) {
			return null;
		}

		CDSMarketSnap cdsMarketSnapFirst = MarketValuationSnap (
			cds,
			firstDiscountCurve,
			firstCreditCurve,
			curveSurfaceQuoteContainer,
			rollDownFairPremium
		);

		if (null == cdsMarketSnapFirst ||
			!cdsMarketSnapFirst.setInitialFairPremium (initialFairPremium) ||
			!cdsMarketSnapFirst.setFixedCoupon (fixedCoupon))
		{
			return null;
		}

		CDSMarketSnap cdsMarketSnapSecond = MarketValuationSnap (
			cds,
			secondDiscountCurve,
			secondCreditCurve,
			curveSurfaceQuoteContainer,
			rollDownFairPremium
		);

		if (null == cdsMarketSnapSecond ||
			!cdsMarketSnapSecond.setInitialFairPremium (initialFairPremium) ||
			!cdsMarketSnapSecond.setFixedCoupon (fixedCoupon))
		{
			return null;
		}

		try {
			return new PositionChangeComponents (
				false,
				cdsMarketSnapFirst,
				cdsMarketSnapSecond,
				cdsMarketSnapSecond.cumulativeCouponAmount() - cdsMarketSnapFirst.cumulativeCouponAmount(),
				null
			);
		} catch (Exception e) {
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
	 * @param afullCreditIndexName Array of the Full Credit Index Names
	 * @param adblCreditIndexQuotedSpread Array of the Quoted Spreads
	 * 
	 * @return The Funding/Credit Curve Horizon Metrics
	 */

	public static final List<PositionChangeComponents>
		HorizonChangeAttribution (
			final JulianDate[] adtSpot,
			final int iHorizonGap,
			final String[] astrFundingFixingMaturityTenor,
			final double[][] aadblFundingFixingQuote,
			final String[] afullCreditIndexName,
			final double[] adblCreditIndexQuotedSpread)
	{
		if (null == adtSpot || 0 >= iHorizonGap || null == astrFundingFixingMaturityTenor || null ==
			aadblFundingFixingQuote || null == afullCreditIndexName || null ==
				adblCreditIndexQuotedSpread)
			return null;

		int iNumClose = adtSpot.length;
		int iNumFundingInstrument = astrFundingFixingMaturityTenor.length;

		List<PositionChangeComponents> lsPCC = new
			ArrayList<PositionChangeComponents>();

		for (int i = iHorizonGap; i < iNumClose; ++i) {
			int iNumSecondFundingQuote = null == aadblFundingFixingQuote[i] ? 0 :
				aadblFundingFixingQuote[i].length;
			int iNumFirstFundingQuote = null == aadblFundingFixingQuote[i - iHorizonGap] ? 0 :
				aadblFundingFixingQuote[i - iHorizonGap].length;

			if (0 == iNumFirstFundingQuote || iNumFirstFundingQuote != iNumFundingInstrument || 0 ==
				iNumSecondFundingQuote || iNumSecondFundingQuote != iNumFundingInstrument)
				continue;

			CreditIndexConvention cic =
				CreditIndexConventionContainer.ConventionFromFullName
					(afullCreditIndexName[i]);

			if (null == cic) return null;

			String currency = cic.currency();

			CreditDefaultSwap cdsIndex = cic.indexCDS();

			MergedDiscountForwardCurve dcFundingFixingFirst =
				org.drip.service.template.LatentMarketStateBuilder.FundingCurve (adtSpot[i - iHorizonGap],
					currency, null, null, "ForwardRate", null, "ForwardRate",
						astrFundingFixingMaturityTenor, aadblFundingFixingQuote[i - iHorizonGap], "SwapRate",
							org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING);

			CreditCurve firstCreditCurve =
				org.drip.service.template.LatentMarketStateBuilder.CreditCurve (adtSpot[i - iHorizonGap], new
					CreditDefaultSwap[] {cdsIndex}, new double[]
						{adblCreditIndexQuotedSpread[i - iHorizonGap]}, "FairPremium", dcFundingFixingFirst);

			MergedDiscountForwardCurve dcFundingFixingSecond =
				org.drip.service.template.LatentMarketStateBuilder.FundingCurve (adtSpot[i], currency,
					null, null, "ForwardRate", null, "ForwardRate", astrFundingFixingMaturityTenor,
						aadblFundingFixingQuote[i], "SwapRate",
							org.drip.service.template.LatentMarketStateBuilder.SHAPE_PRESERVING);

			CreditCurve secondCreditCurve =
				org.drip.service.template.LatentMarketStateBuilder.CreditCurve (adtSpot[i], new
					CreditDefaultSwap[] {cdsIndex}, new double[]
						{adblCreditIndexQuotedSpread[i]}, "FairPremium", dcFundingFixingSecond);

			lsPCC.add (HorizonChangeAttribution (dcFundingFixingFirst, firstCreditCurve, dcFundingFixingSecond,
				secondCreditCurve, afullCreditIndexName[i]));
		}

		return lsPCC;
	}
}
