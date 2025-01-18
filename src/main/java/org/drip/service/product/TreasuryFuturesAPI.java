
package org.drip.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.historical.attribution.PositionChangeComponents;
import org.drip.historical.attribution.TreasuryFuturesMarketSnap;
import org.drip.historical.sensitivity.TenorDurationNodeMetrics;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.quote.MultiSided;
import org.drip.param.quote.ProductMultiMeasure;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.credit.BondComponent;
import org.drip.product.definition.Bond;
import org.drip.product.govvie.TreasuryFutures;
import org.drip.product.params.CTDEntry;
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
 * 		<li>Generate a Full Map Invocation of the Treasury Futures Run Use Case</li>
 * 		<li>Generate the Treasury Curve Tenor Key Rate Sensitivity/Duration</li>
 * 		<li>Return Attribution for the Treasury Futures</li>
 * 		<li>Generate the Horizon Treasury Curve Tenor Key Rate Sensitivity/Duration</li>
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
		JulianDate julianSpotDate = null;
		double baselineCTDOAS = Double.NaN;
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
		int futuresComponentCount = null == futuresComponentTreasuryPriceArray ? 0 :
			futuresComponentTreasuryPriceArray.length;

		if (0 == futuresComponentCount) {
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

			for (int govvieCurveMaturityIndex = 0;
				govvieCurveMaturityIndex < govvieCurveMaturityCount;
				++govvieCurveMaturityIndex)
			{
				govvieCurveTreasuryMaturityArray[govvieCurveMaturityIndex] =
					new JulianDate (govvieCurveTreasuryMaturityDateArray[govvieCurveMaturityIndex]);
			}

			for (int govvieCurveEffectiveIndex = 0;
				govvieCurveEffectiveIndex < govvieCurveEffectiveCount;
				++govvieCurveEffectiveIndex)
			{
				govvieCurveTreasuryEffectiveArray[govvieCurveEffectiveIndex] =
					new JulianDate (govvieCurveTreasuryEffectiveDateArray[govvieCurveEffectiveIndex]);
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

		for (int futuresComponent = 0; futuresComponent < futuresComponentCount; ++futuresComponent) {
			ProductMultiMeasure productMultiMeasure = new ProductMultiMeasure();

			try {
				productMultiMeasure.addQuote (
					"Price",
					new MultiSided ("mid", futuresComponentTreasuryPriceArray[futuresComponent]),
					true
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			if (!curveSurfaceQuoteContainer.setProductQuote (
				bondComponentArray[futuresComponent].name(),
				productMultiMeasure
			))
			{
				return null;
			}
		}

		String treasuryType = treasuryFutures.type();

		if (!curveSurfaceQuoteContainer.setGovvieState (
			LatentMarketStateBuilder.ShapePreservingGovvieCurve (
				treasuryType,
				julianSpotDate,
				govvieCurveTreasuryEffectiveArray,
				govvieCurveTreasuryMaturityArray,
				govvieCurveTreasuryCouponArray,
				govvieCurveTreasuryYieldArray,
				govvieCurveTreasuryMeasure
			)
		))
		{
			return null;
		}

		CTDEntry ctdEntry = treasuryFutures.cheapestToDeliverYield (
			spotDate,
			futuresComponentTreasuryPriceArray
		);

		if (null == ctdEntry) {
			return null;
		}

		Bond ctdBond = ctdEntry.bond();

		if (null == ctdBond) {
			return null;
		}

		double ctdExpiryPrice = ctdEntry.forwardPrice();

		ValuationParams expiryValuationParams = ValuationParams.Spot (treasuryFutures.expiry().julian());

		try {
			if (!NumberUtil.IsValid (
				baselineCTDOAS = ctdBond.oasFromPrice (
					expiryValuationParams,
					curveSurfaceQuoteContainer,
					null,
					ctdExpiryPrice
				)
			))
			{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		CaseInsensitiveTreeMap<GovvieCurve> tenorGovvieCurveMap =
			LatentMarketStateBuilder.BumpedGovvieCurve (
				treasuryType,
				julianSpotDate,
				govvieCurveTreasuryEffectiveArray,
				govvieCurveTreasuryMaturityArray,
				govvieCurveTreasuryCouponArray,
				govvieCurveTreasuryYieldArray,
				govvieCurveTreasuryMeasure,
				LatentMarketStateBuilder.SHAPE_PRESERVING,
				0.0001,
				false
			);

		if (null == tenorGovvieCurveMap || treasuryBenchmarkCount > tenorGovvieCurveMap.size()) {
			return null;
		}

		CaseInsensitiveTreeMap<Double> keyRateDurationMap = new CaseInsensitiveTreeMap<Double>();

		for (Map.Entry<String, GovvieCurve> tenorGovvieCurveMapEntry : tenorGovvieCurveMap.entrySet()) {
			String tenorGovvieCurveMapKey = tenorGovvieCurveMapEntry.getKey();

			if (!tenorGovvieCurveMapKey.contains ("tsy")) {
				continue;
			}

			if (!curveSurfaceQuoteContainer.setGovvieState (tenorGovvieCurveMapEntry.getValue())) {
				return null;
			}

			CTDEntry tenorCTDEntry = treasuryFutures.cheapestToDeliverYield (
				spotDate,
				futuresComponentTreasuryPriceArray
			);

			if (null == tenorCTDEntry) {
				return null;
			}

			Bond tenorBondCTD = tenorCTDEntry.bond();

			if (null == tenorBondCTD) {
				return null;
			}

			try {
				keyRateDurationMap.put (
					tenorGovvieCurveMapKey,
					10000. * (
						tenorBondCTD.priceFromOAS (
							expiryValuationParams,
							curveSurfaceQuoteContainer,
							null,
							baselineCTDOAS
						) - ctdExpiryPrice
					) / ctdExpiryPrice
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return keyRateDurationMap;
	}

	/**
	 * Return Attribution for the Treasury Futures
	 * 
	 * @param treasuryCode The Treasury Code
	 * @param effectiveDateArray Array of Effective Dates
	 * @param maturityDateArray Array of Maturity Dates
	 * @param couponArray Array of Coupons
	 * @param expiryDateArray Array of Futures Expiry Dates
	 * @param spotDateArray Array of Spot Dates
	 * @param cleanPriceArray Array of Closing Clean Prices
	 * @param conversionFactorArray Array of the Conversion Factor
	 * 
	 * @return List of the Position Change Components
	 */

	public static final List<PositionChangeComponents> HorizonChangeAttribution (
		final String treasuryCode,
		final JulianDate[] effectiveDateArray,
		final JulianDate[] maturityDateArray,
		final double[] couponArray,
		final JulianDate[] expiryDateArray,
		final JulianDate[] spotDateArray,
		final double[] cleanPriceArray,
		final double[] conversionFactorArray)
	{
		BondComponent[] treasuryComponentArray = TreasuryBuilder.FromCode (
			treasuryCode,
			effectiveDateArray,
			maturityDateArray,
			couponArray
		);

		if (null == treasuryComponentArray ||
			null == expiryDateArray ||
			null == spotDateArray ||
			null == cleanPriceArray ||
			null == conversionFactorArray)
		{
			return null;
		}

		int closesCount = spotDateArray.length;
		int[] expiryDateIntegerArray = new int[closesCount];
		double[] yieldArray = new double[closesCount];
		double[] forwardAccrualArray = new double[closesCount];
		double[] forwardCleanPriceArray = new double[closesCount];
		double[] forwardModifiedDurationArray = new double[closesCount];

		if (1 >= closesCount ||
			closesCount != treasuryComponentArray.length ||
			closesCount != cleanPriceArray.length ||
			closesCount != expiryDateArray.length ||
			closesCount != conversionFactorArray.length)
		{
			return null;
		}

		List<PositionChangeComponents> positionChangeComponentsList =
			new ArrayList<PositionChangeComponents>();

		for (int closesIndex = 0; closesIndex < closesCount; ++closesIndex) {
			if (null == treasuryComponentArray[closesIndex]) {
				return null;
			}

			ValuationParams valParamsSpot = ValuationParams.Spot (spotDateArray[closesIndex].julian());

			ValuationParams expiryValuationParams = ValuationParams.Spot (
				expiryDateIntegerArray[closesIndex] = expiryDateArray[closesIndex].julian()
			);

			try {
				forwardAccrualArray[closesIndex] =
					treasuryComponentArray[closesIndex].accrued (expiryDateIntegerArray[closesIndex], null);

				yieldArray[closesIndex] = treasuryComponentArray[closesIndex].yieldFromPrice (
					valParamsSpot,
					null,
					null,
					cleanPriceArray[closesIndex]
				);

				forwardCleanPriceArray[closesIndex] = treasuryComponentArray[closesIndex].priceFromYield (
					expiryValuationParams,
					null,
					null,
					yieldArray[closesIndex]
				);

				forwardModifiedDurationArray[closesIndex] =
					treasuryComponentArray[closesIndex].modifiedDurationFromPrice (
						expiryValuationParams,
						null,
						null,
						forwardCleanPriceArray[closesIndex]
				) * 10000.;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (int closesIndex = 1; closesIndex < closesCount; ++closesIndex) {
			if (conversionFactorArray[closesIndex] != conversionFactorArray[closesIndex - 1]) {
				continue;
			}

			String currentBondName = treasuryComponentArray[closesIndex].name();

			String previousBondName = treasuryComponentArray[closesIndex - 1].name();

			double scaledPrice1 = (
				forwardCleanPriceArray[closesIndex - 1] + forwardAccrualArray[closesIndex - 1]
			) / conversionFactorArray[closesIndex - 1];
			double scaledPrice2 = (
				forwardCleanPriceArray[closesIndex] + forwardAccrualArray[closesIndex]
			) / conversionFactorArray[closesIndex];

			try {
				TreasuryFuturesMarketSnap treasuryFuturesMarketSnap1 = new TreasuryFuturesMarketSnap (
					spotDateArray[closesIndex - 1],
					scaledPrice1
				);

				if (!treasuryFuturesMarketSnap1.setYieldMarketFactor (
						yieldArray[closesIndex - 1],
						-1. * scaledPrice1 * forwardModifiedDurationArray[closesIndex - 1],
						0.
					) || !treasuryFuturesMarketSnap1.setExpiryDate (
						expiryDateArray[closesIndex - 1]
					) || !treasuryFuturesMarketSnap1.setCTDName (
						previousBondName
					) || !treasuryFuturesMarketSnap1.setCleanExpiryPrice (
						forwardCleanPriceArray[closesIndex - 1]
					) || !treasuryFuturesMarketSnap1.setConversionFactor (
						conversionFactorArray[closesIndex - 1]
					)
				)
				{
					return null;
				}

				TreasuryFuturesMarketSnap treasuryFuturesMarketSnap2 = new TreasuryFuturesMarketSnap (
					spotDateArray[closesIndex],
					scaledPrice2
				);

				if (!treasuryFuturesMarketSnap2.setYieldMarketFactor (
						yieldArray[closesIndex],
						-1. * scaledPrice2 * forwardModifiedDurationArray[closesIndex],
						0.
					) || !treasuryFuturesMarketSnap2.setExpiryDate (
						expiryDateArray[closesIndex]
					) || !treasuryFuturesMarketSnap2.setCTDName (
						currentBondName
					) || !treasuryFuturesMarketSnap2.setCleanExpiryPrice (
						forwardCleanPriceArray[closesIndex]
					) || !treasuryFuturesMarketSnap2.setConversionFactor (
						conversionFactorArray[closesIndex]
					)
				)
				{
					return null;
				}

				positionChangeComponentsList.add (
					new PositionChangeComponents (
						false,
						treasuryFuturesMarketSnap1,
						treasuryFuturesMarketSnap2,
						0.,
						null
					)
				);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return positionChangeComponentsList;
	}

	/**
	 * Generate the Horizon Treasury Curve Tenor Key Rate Sensitivity/Duration
	 * 
	 * @param treasuryType The Treasury Type
	 * @param effectiveDateArray Array of Effective Dates
	 * @param maturityDateArray Array of Maturity Dates
	 * @param couponArray Array of Coupons
	 * @param expiryDateArray Array of Futures Expiry Dates
	 * @param spotDateArray Array of Spot Dates
	 * @param cleanPriceArray Array of Closing Clean Prices
	 * @param benchmarkTenorArray Array of Benchmark Tenors
	 * @param govvieCurveTreasuryYieldArray Array of the Treasury Instrument Yield (for Treasury Curve)
	 * 
	 * @return The Treasury Curve Tenor Sensitivity/Duration
	 */

	public static final List<TenorDurationNodeMetrics> HorizonKeyRateDuration (
		final String treasuryType,
		final JulianDate[] effectiveDateArray,
		final JulianDate[] maturityDateArray,
		final double[] couponArray,
		final JulianDate[] expiryDateArray,
		final JulianDate[] spotDateArray,
		final double[] cleanPriceArray,
		final String[] benchmarkTenorArray,
		final double[][] govvieCurveTreasuryYieldArray)
	{
		if (null == spotDateArray ||
			null == benchmarkTenorArray ||
			null == govvieCurveTreasuryYieldArray ||
			null == govvieCurveTreasuryYieldArray[0])
		{
			return null;
		}

		double spotYield = Double.NaN;
		double expiryYield = Double.NaN;
		double spotGSpread = Double.NaN;
		double expiryGSpread = Double.NaN;
		double expiryCleanPrice = Double.NaN;
		int closesCount = spotDateArray.length;
		int benchmarkCount = benchmarkTenorArray.length;

		if (0 >= closesCount || closesCount != govvieCurveTreasuryYieldArray.length) {
			return null;
		}

		List<TenorDurationNodeMetrics> tenorDurationNodeMetricsList =
			new ArrayList<TenorDurationNodeMetrics>();

		for (int closesIndex = 0; closesIndex < closesCount; ++closesIndex) {
			if (null == govvieCurveTreasuryYieldArray[closesIndex] ||
				benchmarkCount != govvieCurveTreasuryYieldArray[closesIndex].length)
			{
				return null;
			}

			JulianDate[] govvieCurveTreasuryEffectiveArray = new JulianDate[benchmarkCount];
			JulianDate[] govvieCurveTreasuryMaturityArray = new JulianDate[benchmarkCount];
			TenorDurationNodeMetrics tenorDurationNodeMetrics = null;
			double parallelKRD = 0.;

			for (int benchmarkIndex = 0; benchmarkIndex < benchmarkCount; ++benchmarkIndex) {
				govvieCurveTreasuryEffectiveArray[benchmarkIndex] = spotDateArray[closesIndex];

				govvieCurveTreasuryMaturityArray[benchmarkIndex] =
					spotDateArray[closesIndex].addTenor (benchmarkTenorArray[benchmarkIndex]);
			}

			GovvieCurve govvieCurve = LatentMarketStateBuilder.ShapePreservingGovvieCurve (
				treasuryType,
				spotDateArray[closesIndex],
				govvieCurveTreasuryEffectiveArray,
				govvieCurveTreasuryMaturityArray,
				govvieCurveTreasuryYieldArray[closesIndex],
				govvieCurveTreasuryYieldArray[closesIndex],
				"Yield"
			);

			CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

			if (!curveSurfaceQuoteContainer.setGovvieState (govvieCurve)) {
				continue;
			}

			ValuationParams valParamsSpot = ValuationParams.Spot (spotDateArray[closesIndex].julian());

			ValuationParams expiryValuationParams =
				ValuationParams.Spot (expiryDateArray[closesIndex].julian());

			BondComponent ctdBond = TreasuryBuilder.FromCode (
				treasuryType,
				effectiveDateArray[closesIndex],
				maturityDateArray[closesIndex],
				couponArray[closesIndex]
			);

			if (null == ctdBond) {
				continue;
			}

			try {
				spotGSpread = ctdBond.gSpreadFromPrice (
					valParamsSpot,
					curveSurfaceQuoteContainer,
					null,
					cleanPriceArray[closesIndex]
				);

				spotYield = ctdBond.yieldFromPrice (
					valParamsSpot,
					curveSurfaceQuoteContainer,
					null,
					cleanPriceArray[closesIndex]
				);

				expiryCleanPrice = ctdBond.priceFromGSpread (
					expiryValuationParams,
					curveSurfaceQuoteContainer,
					null,
					spotGSpread
				);

				expiryGSpread = ctdBond.gSpreadFromPrice (
					expiryValuationParams,
					curveSurfaceQuoteContainer,
					null,
					expiryCleanPrice
				);

				expiryYield = ctdBond.yieldFromPrice (
					expiryValuationParams,
					curveSurfaceQuoteContainer,
					null,
					expiryCleanPrice
				);
			} catch (Exception e) {
				e.printStackTrace();

				continue;
			}

			CaseInsensitiveTreeMap<GovvieCurve> tenorGovvieCurveMap =
				LatentMarketStateBuilder.BumpedGovvieCurve (
					treasuryType,
					spotDateArray[closesIndex],
					govvieCurveTreasuryEffectiveArray,
					govvieCurveTreasuryMaturityArray,
					govvieCurveTreasuryYieldArray[closesIndex],
					govvieCurveTreasuryYieldArray[closesIndex],
					"Yield",
					LatentMarketStateBuilder.SHAPE_PRESERVING,
					0.0001,
					false
				);

			if (null == tenorGovvieCurveMap || benchmarkCount > tenorGovvieCurveMap.size()) {
				continue;
			}

			try {
				tenorDurationNodeMetrics = new TenorDurationNodeMetrics (spotDateArray[closesIndex]);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			tenorDurationNodeMetrics.setR1 ("SpotGSpread", spotGSpread);

			tenorDurationNodeMetrics.setR1 ("ExpiryGSpread", expiryGSpread);

			tenorDurationNodeMetrics.setR1 ("SpotYield", spotYield);

			tenorDurationNodeMetrics.setR1 ("ExpiryYield", expiryYield);

			tenorDurationNodeMetrics.setDate ("ExpiryDate", expiryDateArray[closesIndex]);

			tenorDurationNodeMetrics.setC1 ("CTDName", ctdBond.name());

			tenorDurationNodeMetrics.setR1 ("SpotCTDCleanPrice", cleanPriceArray[closesIndex]);

			tenorDurationNodeMetrics.setR1 ("ExpiryCTDCleanPrice", expiryCleanPrice);

			for (Map.Entry<String, GovvieCurve> tenorGovvieCurveMapEntry : tenorGovvieCurveMap.entrySet()) {
				String tenorGovvieCurveMapKey = tenorGovvieCurveMapEntry.getKey();

				if (!tenorGovvieCurveMapKey.contains ("tsy")) {
					continue;
				}

				if (!curveSurfaceQuoteContainer.setGovvieState (tenorGovvieCurveMapEntry.getValue())) {
					return null;
				}

				double tenorKRD = Double.NaN;

				try {
					tenorKRD = -10000. * (
						ctdBond.priceFromGSpread (
							expiryValuationParams,
							curveSurfaceQuoteContainer,
							null,
							spotGSpread
						) - expiryCleanPrice
					) / expiryCleanPrice;

					if (!tenorDurationNodeMetrics.addKRDNode (tenorGovvieCurveMapKey, tenorKRD)) {
						continue;
					}

					parallelKRD += tenorKRD;
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}
			}

			tenorDurationNodeMetrics.setR1 ("ParallelKRD", parallelKRD);

			tenorDurationNodeMetricsList.add (tenorDurationNodeMetrics);
		}

		return tenorDurationNodeMetricsList;
	}
}
