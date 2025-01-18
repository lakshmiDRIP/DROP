
package org.drip.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.drip.analytics.date.JulianDate;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.quote.MultiSided;
import org.drip.param.quote.ProductMultiMeasure;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.Bond;
import org.drip.product.govvie.TreasuryFutures;
import org.drip.service.template.ExchangeInstrumentBuilder;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.service.template.TreasuryBuilder;
import org.drip.state.govvie.GovvieCurve;

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
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>TreasuryFuturesAPI</i> demonstrates the Details behind the Pricing and the Scenario Runs behind a
 * 	Treasury Futures Contract. It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Compute the Horizon Change Attribution Details for the Specified Treasury Bond</li>
 * 		<li>Generate the Govvie Curve Horizon Metrics #1</li>
 * 		<li>Generate the Govvie Curve Horizon Metrics #2</li>
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

public class TreasuryFuturesAPI
{

	/**
	 * Generate a Full Map Invocation of the Treasury Futures Run Use Case
	 * 
	 * @param futuresCodeArray The Treasury Futures Code
	 * @param futuresComponentTreasuryEffectiveDateArray Array of the Treasury Futures Component Effective Date
	 * @param futuresComponentTreasuryMaturityDateArray Array of the Treasury Futures Component Maturity Date
	 * @param futuresComponentTreasuryCouponArray Array of the Treasury Futures Component Coupon
	 * @param futuresComponentConversionFactorArray Array of the Treasury Futures Component Conversion Factor
	 * @param spotDate Spot Date
	 * @param fundingCurveDepositTenorArray Deposit Instruments Tenor (for Funding Curve)
	 * @param fundingCurveDepositQuoteArray Deposit Instruments Quote (for Funding Curve)
	 * @param fundingCurveDepositMeasure Deposit Instruments Measure (for Funding Curve)
	 * @param fundingCurveFuturesQuoteArray Futures Instruments Tenor (for Funding Curve)
	 * @param fundingCurveFuturesMeasure Futures Instruments Measure (for Funding Curve)
	 * @param fundingCurveFixFloatTenorArray Fix-Float Instruments Tenor (for Funding Curve)
	 * @param fundingCurveFixFloatQuoteArray Fix-Float Instruments Quote (for Funding Curve)
	 * @param fundingFixFloatMeasure Fix-Float Instruments Tenor (for Funding Curve)
	 * @param govvieCurveTreasuryEffectiveDateArray Array of the Treasury Instrument Effective Date (for Treasury
	 * 		Curve)
	 * @param govvieCurveTreasuryMaturityDateArray Array of the Treasury Instrument Maturity Date (for Treasury
	 * 		Curve)
	 * @param govvieCurveTreasuryCouponArray Array of the Treasury Instrument Coupon (for Treasury Curve)
	 * @param govvieCurveTreasuryYieldArray Array of the Treasury Instrument Yield (for Treasury Curve)
	 * @param govvieCurveTreasuryMeasure Treasury Instrument Measure (for Govvie Curve)
	 * @param futuresComponentTreasuryPriceArray Array of the Treasury Futures Component Clean Prices
	 * 
	 * @return The Output Measure Map
	 */

	public static final Map<String, Double> ValuationMetrics (
		final String futuresCodeArray,
		final int[] futuresComponentTreasuryEffectiveDateArray,
		final int[] futuresComponentTreasuryMaturityDateArray,
		final double[] futuresComponentTreasuryCouponArray,
		final double[] futuresComponentConversionFactorArray,
		final int spotDate,
		final String[] fundingCurveDepositTenorArray,
		final double[] fundingCurveDepositQuoteArray,
		final String fundingCurveDepositMeasure,
		final double[] fundingCurveFuturesQuoteArray,
		final String fundingCurveFuturesMeasure,
		final String[] fundingCurveFixFloatTenorArray,
		final double[] fundingCurveFixFloatQuoteArray,
		final String fundingFixFloatMeasure,
		final int[] govvieCurveTreasuryEffectiveDateArray,
		final int[] govvieCurveTreasuryMaturityDateArray,
		final double[] govvieCurveTreasuryCouponArray,
		final double[] govvieCurveTreasuryYieldArray,
		final String govvieCurveTreasuryMeasure,
		final double[] futuresComponentTreasuryPriceArray)
	{
		JulianDate julianSpotDate = null;
		JulianDate[] govvieCurveTreasuryMaturityArray = null;
		JulianDate[] govvieCurveTreasuryEffectiveArray = null;
		int govvieCurveMaturityCount = null == govvieCurveTreasuryMaturityDateArray ? 0 :
			govvieCurveTreasuryMaturityDateArray.length;
		int govvieCurveEffectiveCount = null == govvieCurveTreasuryEffectiveDateArray ? 0 :
			govvieCurveTreasuryEffectiveDateArray.length;
		String[] treasuryBenchmarkCodeArray = new String[] {
			"01YON",
			"02YON",
			"03YON",
			"05YON",
			"07YON",
			"10YON",
			"30YON"
		};
		int treasuryBenchmarkCount = treasuryBenchmarkCodeArray.length;

		if (null == futuresComponentTreasuryPriceArray) {
			return null;
		}

		if (0 != govvieCurveMaturityCount) {
			govvieCurveTreasuryMaturityArray = new JulianDate[govvieCurveMaturityCount];
		}

		if (0 != govvieCurveEffectiveCount) {
			govvieCurveTreasuryEffectiveArray = new JulianDate[govvieCurveEffectiveCount];
		}

		CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		try {
			julianSpotDate = new JulianDate (spotDate);

			for (int govvieCurveMaturity = 0;
				govvieCurveMaturity < govvieCurveMaturityCount;
				++govvieCurveMaturity)
			{
				govvieCurveTreasuryMaturityArray[govvieCurveMaturity] =
					new JulianDate (govvieCurveTreasuryMaturityDateArray[govvieCurveMaturity]);
			}

			for (int govvieCurveEffective = 0;
				govvieCurveEffective < govvieCurveEffectiveCount;
				++govvieCurveEffective)
			{
				govvieCurveTreasuryEffectiveArray[govvieCurveEffective] =
					new JulianDate (govvieCurveTreasuryEffectiveDateArray[govvieCurveEffective]);
			}

			if (null != govvieCurveTreasuryYieldArray &&
				govvieCurveTreasuryYieldArray.length == treasuryBenchmarkCount)
			{
				for (int treasuryBenchmark = 0;
					treasuryBenchmark < treasuryBenchmarkCount;
					++treasuryBenchmark)
				{
					ProductMultiMeasure productMultiMeasure = new ProductMultiMeasure();

					productMultiMeasure.addQuote (
						"Yield",
						new MultiSided ("mid", govvieCurveTreasuryYieldArray[treasuryBenchmark]),
						true
					);

					if (!curveSurfaceQuoteContainer.setProductQuote (
						treasuryBenchmarkCodeArray[treasuryBenchmark],
						productMultiMeasure
					))
					{
						return null;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		TreasuryFutures treasuryFutures = ExchangeInstrumentBuilder.TreasuryFutures (
			julianSpotDate,
			futuresCodeArray,
			futuresComponentTreasuryEffectiveDateArray,
			futuresComponentTreasuryMaturityDateArray,
			futuresComponentTreasuryCouponArray,
			futuresComponentConversionFactorArray
		);

		if (null == treasuryFutures) {
			return null;
		}

		Bond[] bondComponentArray = treasuryFutures.basket();

		for (int futuresComponentTreasuryPriceIndex = 0;
			futuresComponentTreasuryPriceIndex < futuresComponentTreasuryPriceArray.length;
			++futuresComponentTreasuryPriceIndex)
		{
			ProductMultiMeasure productMultiMeasure = new ProductMultiMeasure();

			try {
				productMultiMeasure.addQuote (
					"Price",
					new MultiSided (
						"mid",
						futuresComponentTreasuryPriceArray[futuresComponentTreasuryPriceIndex]
					),
					true
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			curveSurfaceQuoteContainer.setProductQuote (
				bondComponentArray[futuresComponentTreasuryPriceIndex].name(),
				productMultiMeasure
			);
		}

		curveSurfaceQuoteContainer.setFundingState (
			LatentMarketStateBuilder.SmoothFundingCurve (
				julianSpotDate,
				bondComponentArray[0].currency(),
				fundingCurveDepositTenorArray,
				fundingCurveDepositQuoteArray,
				fundingCurveDepositMeasure,
				fundingCurveFuturesQuoteArray,
				fundingCurveFuturesMeasure,
				fundingCurveFixFloatTenorArray,
				fundingCurveFixFloatQuoteArray,
				fundingFixFloatMeasure
			)
		);

		GovvieCurve govvieCurve = LatentMarketStateBuilder.ShapePreservingGovvieCurve (
			treasuryFutures.type(),
			julianSpotDate,
			govvieCurveTreasuryEffectiveArray,
			govvieCurveTreasuryMaturityArray,
			govvieCurveTreasuryCouponArray,
			govvieCurveTreasuryYieldArray,
			govvieCurveTreasuryMeasure
		);

		curveSurfaceQuoteContainer.setGovvieState (govvieCurve);

		return treasuryFutures.value (
			ValuationParams.Spot (julianSpotDate.julian()),
			null,
			curveSurfaceQuoteContainer,
			null
		);
	}

	/**
	 * Generate the Treasury Curve Tenor Key Rate Sensitivity/Duration
	 * 
	 * @param futuresCodeArray The Treasury Futures Code
	 * @param futuresComponentTreasuryEffectiveDateArray Array of the Treasury Futures Component Effective Date
	 * @param futuresComponentTreasuryMaturityDateArray Array of the Treasury Futures Component Maturity Date
	 * @param futuresComponentTreasuryCouponArray Array of the Treasury Futures Component Coupon
	 * @param futuresComponentConversionFactorArray Array of the Treasury Futures Component Conversion Factor
	 * @param spotDate Spot Date
	 * @param govvieCurveTreasuryEffectiveDateArray Array of the Treasury Instrument Effective Date (for Treasury
	 * 		Curve)
	 * @param govvieCurveTreasuryMaturityDateArray Array of the Treasury Instrument Maturity Date (for Treasury
	 * 		Curve)
	 * @param govvieCurveTreasuryCouponArray Array of the Treasury Instrument Coupon (for Treasury Curve)
	 * @param govvieCurveTreasuryYieldArray Array of the Treasury Instrument Yield (for Treasury Curve)
	 * @param govvieCurveTreasuryMeasure Treasury Instrument Measure (for Govvie Curve)
	 * @param futuresComponentTreasuryPriceArray Array of the Treasury Futures Component Clean Prices
	 * 
	 * @return The Treasury Curve Tenor Sensitivity/Duration
	 */

	public static final Map<String, Double> KeyRateDuration (
		final String futuresCodeArray,
		final int[] futuresComponentTreasuryEffectiveDateArray,
		final int[] futuresComponentTreasuryMaturityDateArray,
		final double[] futuresComponentTreasuryCouponArray,
		final double[] futuresComponentConversionFactorArray,
		final int spotDate,
		final int[] govvieCurveTreasuryEffectiveDateArray,
		final int[] govvieCurveTreasuryMaturityDateArray,
		final double[] govvieCurveTreasuryCouponArray,
		final double[] govvieCurveTreasuryYieldArray,
		final String govvieCurveTreasuryMeasure,
		final double[] futuresComponentTreasuryPriceArray)
	{
		double dblBaselineCTDOAS = Double.NaN;
		JulianDate julianSpotDate = null;
		JulianDate[] govvieCurveTreasuryMaturityArray = null;
		JulianDate[] govvieCurveTreasuryEffectiveArray = null;
		int govvieCurveMaturityCount = null == govvieCurveTreasuryMaturityDateArray ? 0 :
			govvieCurveTreasuryMaturityDateArray.length;
		int govvieCurveEffectiveCount = null == govvieCurveTreasuryEffectiveDateArray ? 0 :
			govvieCurveTreasuryEffectiveDateArray.length;
		String[] treasuryBenchmarkCodeArray = new String[] {"01YON", "02YON", "03YON",
			"05YON", "07YON", "10YON", "30YON"};
		int treasuryBenchmarkCount = treasuryBenchmarkCodeArray.length;
		int futuresComponentCount = null == futuresComponentTreasuryPriceArray ? 0 :
			futuresComponentTreasuryPriceArray.length;

		if (0 == futuresComponentCount) return null;

		if (0 != govvieCurveMaturityCount)
			govvieCurveTreasuryMaturityArray = new JulianDate[govvieCurveMaturityCount];

		if (0 != govvieCurveEffectiveCount)
			govvieCurveTreasuryEffectiveArray = new
				JulianDate[govvieCurveEffectiveCount];

		CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = new
			CurveSurfaceQuoteContainer();

		try {
			julianSpotDate = new JulianDate (spotDate);

			for (int i = 0; i < govvieCurveMaturityCount; ++i)
				govvieCurveTreasuryMaturityArray[i] = new JulianDate
					(govvieCurveTreasuryMaturityDateArray[i]);

			for (int i = 0; i < govvieCurveEffectiveCount; ++i)
				govvieCurveTreasuryEffectiveArray[i] = new JulianDate
					(govvieCurveTreasuryEffectiveDateArray[i]);

			if (null != govvieCurveTreasuryYieldArray && govvieCurveTreasuryYieldArray.length ==
				treasuryBenchmarkCount) {
				for (int i = 0; i < treasuryBenchmarkCount; ++i) {
					ProductMultiMeasure pmm = new
						ProductMultiMeasure();

					pmm.addQuote ("Yield", new MultiSided ("mid",
						govvieCurveTreasuryYieldArray[i]), true);

					if (!curveSurfaceQuoteContainer.setProductQuote (treasuryBenchmarkCodeArray[i], pmm)) return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		TreasuryFutures treasuryFutures =
			ExchangeInstrumentBuilder.TreasuryFutures (julianSpotDate, futuresCodeArray,
				futuresComponentTreasuryEffectiveDateArray, futuresComponentTreasuryMaturityDateArray,
					futuresComponentTreasuryCouponArray, futuresComponentConversionFactorArray);

		if (null == treasuryFutures) return null;

		Bond[] bondComponentArray = treasuryFutures.basket();

		for (int i = 0; i < futuresComponentCount; ++i) {
			ProductMultiMeasure pmm = new ProductMultiMeasure();

			try {
				pmm.addQuote ("Price", new MultiSided ("mid",
					futuresComponentTreasuryPriceArray[i]), true);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			if (!curveSurfaceQuoteContainer.setProductQuote (bondComponentArray[i].name(), pmm)) return null;
		}

		String strTreasuryType = treasuryFutures.type();

		if (!curveSurfaceQuoteContainer.setGovvieState
			(LatentMarketStateBuilder.ShapePreservingGovvieCurve (strTreasuryType,
				julianSpotDate, govvieCurveTreasuryEffectiveArray, govvieCurveTreasuryMaturityArray,
					govvieCurveTreasuryCouponArray, govvieCurveTreasuryYieldArray,
						govvieCurveTreasuryMeasure)))
			return null;

		org.drip.product.params.CTDEntry ctdEntry = treasuryFutures.cheapestToDeliverYield (spotDate,
			futuresComponentTreasuryPriceArray);

		if (null == ctdEntry) return null;

		Bond bondCTD = ctdEntry.bond();

		if (null == bondCTD) return null;

		double dblCTDExpiryPrice = ctdEntry.forwardPrice();

		ValuationParams valParamsExpiry =
			ValuationParams.Spot (treasuryFutures.expiry().julian());

		try {
			if (!org.drip.numerical.common.NumberUtil.IsValid (dblBaselineCTDOAS = bondCTD.oasFromPrice
				(valParamsExpiry, curveSurfaceQuoteContainer, null, dblCTDExpiryPrice)))
				return null;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.analytics.support.CaseInsensitiveTreeMap<GovvieCurve>
			mapTenorGovvieCurve = LatentMarketStateBuilder.BumpedGovvieCurve
				(strTreasuryType, julianSpotDate, govvieCurveTreasuryEffectiveArray, govvieCurveTreasuryMaturityArray,
					govvieCurveTreasuryCouponArray, govvieCurveTreasuryYieldArray,
						govvieCurveTreasuryMeasure,
							LatentMarketStateBuilder.SHAPE_PRESERVING, 0.0001,
								false);

		if (null == mapTenorGovvieCurve || treasuryBenchmarkCount > mapTenorGovvieCurve.size()) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<Double> mapKeyRateDuration = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<Double>();

		for (Map.Entry<String, GovvieCurve> me :
			mapTenorGovvieCurve.entrySet()) {
			String strKey = me.getKey();

			if (!strKey.contains ("tsy")) continue;

			if (!curveSurfaceQuoteContainer.setGovvieState (me.getValue())) return null;

			org.drip.product.params.CTDEntry tenorCTDEntry = treasuryFutures.cheapestToDeliverYield (spotDate,
				futuresComponentTreasuryPriceArray);

			if (null == tenorCTDEntry) return null;

			Bond tenorBondCTD = tenorCTDEntry.bond();

			if (null == tenorBondCTD) return null;

			try {
				mapKeyRateDuration.put (strKey, 10000. * (tenorBondCTD.priceFromOAS (valParamsExpiry, curveSurfaceQuoteContainer,
					null, dblBaselineCTDOAS) - dblCTDExpiryPrice) / dblCTDExpiryPrice);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return mapKeyRateDuration;
	}

	/**
	 * Returns Attribution for the Treasury Futures
	 * 
	 * @param strTreasuryCode The Treasury Code
	 * @param adtEffective Array of Effective Dates
	 * @param adtMaturity Array of Maturity Dates
	 * @param adblCoupon Array of Coupons
	 * @param adtExpiry Array of Futures Expiry Dates
	 * @param ajulianSpotDate Array of Spot Dates
	 * @param adblCleanPrice Array of Closing Clean Prices
	 * @param adblConversionFactor Array of the Conversion Factor
	 * 
	 * @return List of the Position Change Components
	 */

	public static final List<org.drip.historical.attribution.PositionChangeComponents>
		HorizonChangeAttribution (
			final String strTreasuryCode,
			final JulianDate[] adtEffective,
			final JulianDate[] adtMaturity,
			final double[] adblCoupon,
			final JulianDate[] adtExpiry,
			final JulianDate[] ajulianSpotDate,
			final double[] adblCleanPrice,
			final double[] adblConversionFactor)
	{
		org.drip.product.credit.BondComponent[] aTreasury =
			TreasuryBuilder.FromCode (strTreasuryCode, adtEffective, adtMaturity,
				adblCoupon);

		if (null == aTreasury || null == adtExpiry || null == ajulianSpotDate || null == adblCleanPrice || null ==
			adblConversionFactor)
			return null;

		int iNumCloses = ajulianSpotDate.length;
		int[] aiExpiryDate = new int[iNumCloses];
		double[] adblYield = new double[iNumCloses];
		double[] adblForwardAccrued = new double[iNumCloses];
		double[] adblForwardCleanPrice = new double[iNumCloses];
		double[] adblForwardModifiedDuration = new double[iNumCloses];

		if (1 >= iNumCloses || iNumCloses != aTreasury.length || iNumCloses != adblCleanPrice.length ||
			iNumCloses != adtExpiry.length || iNumCloses != adblConversionFactor.length)
			return null;

		List<org.drip.historical.attribution.PositionChangeComponents> lsPCC = new
			ArrayList<org.drip.historical.attribution.PositionChangeComponents>();

		for (int i = 0; i < iNumCloses; ++i) {
			if (null == aTreasury[i]) return null;

			ValuationParams valParamsSpot =
				ValuationParams.Spot (ajulianSpotDate[i].julian());

			ValuationParams valParamsExpiry =
				ValuationParams.Spot (aiExpiryDate[i] = adtExpiry[i].julian());

			try {
				adblForwardAccrued[i] = aTreasury[i].accrued (aiExpiryDate[i], null);

				adblYield[i] = aTreasury[i].yieldFromPrice (valParamsSpot, null, null, adblCleanPrice[i]);

				adblForwardCleanPrice[i] = aTreasury[i].priceFromYield (valParamsExpiry, null, null,
					adblYield[i]);

				adblForwardModifiedDuration[i] = aTreasury[i].modifiedDurationFromPrice (valParamsExpiry,
					null, null, adblForwardCleanPrice[i]) * 10000.;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (int i = 1; i < iNumCloses; ++i) {
			if (adblConversionFactor[i] != adblConversionFactor[i - 1]) continue;

			String strCurrentBondName = aTreasury[i].name();

			String strPreviousBondName = aTreasury[i - 1].name();

			double dblScaledPrice1 = (adblForwardCleanPrice[i - 1] + adblForwardAccrued[i - 1]) /
				adblConversionFactor[i - 1];
			double dblScaledPrice2 = (adblForwardCleanPrice[i] + adblForwardAccrued[i]) /
				adblConversionFactor[i];

			try {
				org.drip.historical.attribution.TreasuryFuturesMarketSnap tfpms1 = new
					org.drip.historical.attribution.TreasuryFuturesMarketSnap (ajulianSpotDate[i - 1],
						dblScaledPrice1);

				if (!tfpms1.setYieldMarketFactor (adblYield[i - 1], -1. * dblScaledPrice1 *
					adblForwardModifiedDuration[i - 1], 0.) || !tfpms1.setExpiryDate (adtExpiry[i - 1]) ||
						!tfpms1.setCTDName (strPreviousBondName) || !tfpms1.setCleanExpiryPrice
							(adblForwardCleanPrice[i - 1]) || !tfpms1.setConversionFactor
								(adblConversionFactor[i - 1]))
					return null;

				org.drip.historical.attribution.TreasuryFuturesMarketSnap tfpms2 = new
					org.drip.historical.attribution.TreasuryFuturesMarketSnap (ajulianSpotDate[i],
						dblScaledPrice2);

				if (!tfpms2.setYieldMarketFactor (adblYield[i], -1. * dblScaledPrice2 *
					adblForwardModifiedDuration[i], 0.) || !tfpms2.setExpiryDate (adtExpiry[i]) ||
						!tfpms2.setCTDName (strCurrentBondName) || !tfpms2.setCleanExpiryPrice
							(adblForwardCleanPrice[i]) || !tfpms2.setConversionFactor
								(adblConversionFactor[i]))
					return null;

				org.drip.historical.attribution.PositionChangeComponents pcc = new
					org.drip.historical.attribution.PositionChangeComponents (false, tfpms1, tfpms2, 0.,
						null);

				lsPCC.add (pcc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return lsPCC;
	}

	/**
	 * Generate the Horizon Treasury Curve Tenor Key Rate Sensitivity/Duration
	 * 
	 * @param strTreasuryType The Treasury Type
	 * @param adtEffective Array of Effective Dates
	 * @param adtMaturity Array of Maturity Dates
	 * @param adblCoupon Array of Coupons
	 * @param adtExpiry Array of Futures Expiry Dates
	 * @param ajulianSpotDate Array of Spot Dates
	 * @param adblCleanPrice Array of Closing Clean Prices
	 * @param astrBenchmarkTenor Array of Benchmark Tenors
	 * @param agovvieCurveTreasuryYieldArray Array of the Treasury Instrument Yield (for Treasury Curve)
	 * 
	 * @return The Treasury Curve Tenor Sensitivity/Duration
	 */

	public static final List<org.drip.historical.sensitivity.TenorDurationNodeMetrics>
		HorizonKeyRateDuration (
			final String strTreasuryType,
			final JulianDate[] adtEffective,
			final JulianDate[] adtMaturity,
			final double[] adblCoupon,
			final JulianDate[] adtExpiry,
			final JulianDate[] ajulianSpotDate,
			final double[] adblCleanPrice,
			final String[] astrBenchmarkTenor,
			final double[][] agovvieCurveTreasuryYieldArray)
	{
		if (null == ajulianSpotDate || null == astrBenchmarkTenor || null == agovvieCurveTreasuryYieldArray || null ==
			agovvieCurveTreasuryYieldArray[0])
			return null;

		double dblExpiryCleanPrice = Double.NaN;
		double dblExpiryGSpread = Double.NaN;
		int iNumBenchmark = astrBenchmarkTenor.length;
		double dblExpiryYield = Double.NaN;
		double dblSpotGSpread = Double.NaN;
		double dblSpotYield = Double.NaN;
		int iNumCloses = ajulianSpotDate.length;

		if (0 >= iNumCloses || iNumCloses != agovvieCurveTreasuryYieldArray.length) return null;

		List<org.drip.historical.sensitivity.TenorDurationNodeMetrics> lsTDNM = new
			ArrayList<org.drip.historical.sensitivity.TenorDurationNodeMetrics>();

		for (int i = 0; i < iNumCloses; ++i) {
			if (null == agovvieCurveTreasuryYieldArray[i] || iNumBenchmark !=
				agovvieCurveTreasuryYieldArray[i].length)
				return null;

			JulianDate[] govvieCurveTreasuryEffectiveArray = new
				JulianDate[iNumBenchmark];
			JulianDate[] govvieCurveTreasuryMaturityArray = new
				JulianDate[iNumBenchmark];
			org.drip.historical.sensitivity.TenorDurationNodeMetrics tdnm = null;
			double dblParallelKRD = 0.;

			for (int j = 0; j < iNumBenchmark; ++j) {
				govvieCurveTreasuryEffectiveArray[j] = ajulianSpotDate[i];

				govvieCurveTreasuryMaturityArray[j] = ajulianSpotDate[i].addTenor (astrBenchmarkTenor[j]);
			}

			GovvieCurve govvieCurve =
				LatentMarketStateBuilder.ShapePreservingGovvieCurve
					(strTreasuryType, ajulianSpotDate[i], govvieCurveTreasuryEffectiveArray,
						govvieCurveTreasuryMaturityArray, agovvieCurveTreasuryYieldArray[i],
							agovvieCurveTreasuryYieldArray[i], "Yield");

			CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = new
				CurveSurfaceQuoteContainer();

			if (!curveSurfaceQuoteContainer.setGovvieState (govvieCurve)) continue;

			ValuationParams valParamsSpot =
				ValuationParams.Spot (ajulianSpotDate[i].julian());

			ValuationParams valParamsExpiry =
				ValuationParams.Spot (adtExpiry[i].julian());

			org.drip.product.credit.BondComponent bondCTD =
				TreasuryBuilder.FromCode (strTreasuryType, adtEffective[i],
					adtMaturity[i], adblCoupon[i]);

			if (null == bondCTD) continue;

			try {
				dblSpotGSpread = bondCTD.gSpreadFromPrice (valParamsSpot, curveSurfaceQuoteContainer, null, adblCleanPrice[i]);

				dblSpotYield = bondCTD.yieldFromPrice (valParamsSpot, curveSurfaceQuoteContainer, null, adblCleanPrice[i]);

				dblExpiryCleanPrice = bondCTD.priceFromGSpread (valParamsExpiry, curveSurfaceQuoteContainer, null, dblSpotGSpread);

				dblExpiryGSpread = bondCTD.gSpreadFromPrice (valParamsExpiry, curveSurfaceQuoteContainer, null,
					dblExpiryCleanPrice);

				dblExpiryYield = bondCTD.yieldFromPrice (valParamsExpiry, curveSurfaceQuoteContainer, null, dblExpiryCleanPrice);
			} catch (Exception e) {
				e.printStackTrace();

				continue;
			}

			org.drip.analytics.support.CaseInsensitiveTreeMap<GovvieCurve>
				mapTenorGovvieCurve = LatentMarketStateBuilder.BumpedGovvieCurve
					(strTreasuryType, ajulianSpotDate[i], govvieCurveTreasuryEffectiveArray,
						govvieCurveTreasuryMaturityArray, agovvieCurveTreasuryYieldArray[i],
							agovvieCurveTreasuryYieldArray[i], "Yield",
								LatentMarketStateBuilder.SHAPE_PRESERVING, 0.0001,
									false);

			if (null == mapTenorGovvieCurve || iNumBenchmark > mapTenorGovvieCurve.size()) continue;

			try {
				tdnm = new org.drip.historical.sensitivity.TenorDurationNodeMetrics (ajulianSpotDate[i]);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			tdnm.setR1 ("SpotGSpread", dblSpotGSpread);

			tdnm.setR1 ("ExpiryGSpread", dblExpiryGSpread);

			tdnm.setR1 ("SpotYield", dblSpotYield);

			tdnm.setR1 ("ExpiryYield", dblExpiryYield);

			tdnm.setDate ("ExpiryDate", adtExpiry[i]);

			tdnm.setC1 ("CTDName", bondCTD.name());

			tdnm.setR1 ("SpotCTDCleanPrice", adblCleanPrice[i]);

			tdnm.setR1 ("ExpiryCTDCleanPrice", dblExpiryCleanPrice);

			for (Map.Entry<String, GovvieCurve> me :
				mapTenorGovvieCurve.entrySet()) {
				String strKey = me.getKey();

				if (!strKey.contains ("tsy")) continue;

				if (!curveSurfaceQuoteContainer.setGovvieState (me.getValue())) return null;

				double dblTenorKRD = Double.NaN;

				try {
					dblTenorKRD = -10000. * (bondCTD.priceFromGSpread (valParamsExpiry, curveSurfaceQuoteContainer, null,
						dblSpotGSpread) - dblExpiryCleanPrice) / dblExpiryCleanPrice;

					if (!tdnm.addKRDNode (strKey, dblTenorKRD)) continue;

					dblParallelKRD += dblTenorKRD;
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			}

			tdnm.setR1 ("ParallelKRD", dblParallelKRD);

			lsTDNM.add (tdnm);
		}

		return lsTDNM;
	}
}
