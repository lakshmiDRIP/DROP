
package org.drip.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.output.BondRVMeasures;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.historical.attribution.BondMarketSnap;
import org.drip.historical.attribution.PositionChangeComponents;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.quote.MultiSided;
import org.drip.param.quote.ProductMultiMeasure;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.creator.BondBuilder;
import org.drip.product.credit.BondComponent;
import org.drip.service.common.FormatUtil;
import org.drip.service.template.LatentMarketStateBuilder;
import org.drip.state.discount.MergedDiscountForwardCurve;
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
 * <i>FixedBondAPI</i> demonstrates the Details behind the Pricing and the Scenario Runs behind a Fixed Bond.
 *  It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Generate a Full Map Invocation of the Bond Valuation Run</li>
 * 		<li>Generate the Treasury Curve Tenor Key Rate Sensitivity/Duration</li>
 * 		<li>Return Attribution for the Specified Bond Instance</li>
 * 		<li>Generate the Relative Value Metrics for the Specified Bond</li>
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

public class FixedBondAPI
{

	/**
	 * Generate a Full Map Invocation of the Bond Valuation Run
	 * 
	 * @param issuerName Bond Issuer Name
	 * @param bondEffectiveDate Bond Effective Date
	 * @param bondMaturityDate Bond Maturity Date
	 * @param bondCoupon Bond Coupon
	 * @param bondCouponFrequency Bond Coupon Frequency
	 * @param bondCouponDayCount Bond Coupon Day Count
	 * @param bondCouponCurrency Bond Coupon Currency
	 * @param spotDate Spot Date
	 * @param fundingCurveDepositTenor Deposit Instruments Tenor (for Funding Curve)
	 * @param fundingCurveDepositQuote Deposit Instruments Quote (for Funding Curve)
	 * @param fundingCurveDepositMeasure Deposit Instruments Measure (for Funding Curve)
	 * @param fundingCurveFuturesQuote Futures Instruments Tenor (for Funding Curve)
	 * @param fundingCurveFuturesMeasure Futures Instruments Measure (for Funding Curve)
	 * @param fundingCurveFixFloatTenor Fix-Float Instruments Tenor (for Funding Curve)
	 * @param fundingCurveFixFloatQuote Fix-Float Instruments Quote (for Funding Curve)
	 * @param fundingFixFloatMeasure Fix-Float Instruments Tenor (for Funding Curve)
	 * @param govvieCode Govvie Bond Code (for Treasury Curve)
	 * @param govvieCurveTreasuryEffectiveDateArray Array of the Treasury Instrument Effective Date (for
	 * 	Treasury Curve)
	 * @param govvieCurveTreasuryMaturityDateArray Array of the Treasury Instrument Maturity Date (for
	 * 	Treasury Curve)
	 * @param govvieCurveTreasuryCouponArray Array of the Treasury Instrument Coupon (for Treasury Curve)
	 * @param govvieCurveTreasuryYieldArray Array of the Treasury Instrument Yield (for Treasury Curve)
	 * @param govvieCurveTreasuryMeasure Treasury Instrument Measure (for Treasury Curve)
	 * @param creditCurveName Credit Curve Name (for Credit Curve)
	 * @param creditCurveCDSTenorArray CDS Maturity Tenor (for Credit Curve)
	 * @param creditCurveCDSCouponArray Array of CDS Fixed Coupon (for Credit Curve)
	 * @param creditCurveCDSQuoteArray Array of CDS Market Quotes (for Credit Curve)
	 * @param creditCurveCDSMeasure CDS Calibration Measure (for Credit Curve)
	 * @param bondMarketQuoteName Name of the Bond Market Quote
	 * @param bondMarketQuote Bond Market Quote Value
	 * 
	 * @return The Output Measure Map
	 */

	public static final Map<String, Double> ValuationMetrics (
		final String issuerName,
		final int bondEffectiveDate,
		final int bondMaturityDate,
		final double bondCoupon,
		final int bondCouponFrequency,
		final String bondCouponDayCount,
		final String bondCouponCurrency,
		final int spotDate,
		final String[] fundingCurveDepositTenor,
		final double[] fundingCurveDepositQuote,
		final String fundingCurveDepositMeasure,
		final double[] fundingCurveFuturesQuote,
		final String fundingCurveFuturesMeasure,
		final String[] fundingCurveFixFloatTenor,
		final double[] fundingCurveFixFloatQuote,
		final String fundingFixFloatMeasure,
		final String govvieCode,
		final int[] govvieCurveTreasuryEffectiveDateArray,
		final int[] govvieCurveTreasuryMaturityDateArray,
		final double[] govvieCurveTreasuryCouponArray,
		final double[] govvieCurveTreasuryYieldArray,
		final String govvieCurveTreasuryMeasure,
		final String creditCurveName,
		final String[] creditCurveCDSTenorArray,
		final double[] creditCurveCDSCouponArray,
		final double[] creditCurveCDSQuoteArray,
		final String creditCurveCDSMeasure,
		final String bondMarketQuoteName,
		final double bondMarketQuote)
	{
		JulianDate maturityDate = null;
		JulianDate effectiveDate = null;
		JulianDate julianSpotDate = null;
		JulianDate[] govvieCurveMaturityDateArray = null;
		JulianDate[] govvieCurveEffectiveDateArray = null;
		String[] treasuryBenchmarkCodeArray = new String[] {
			"01YON",
			"02YON",
			"03YON",
			"05YON",
			"07YON",
			"10YON",
			"30YON"
		};
		int govvieCurveMaturityCount = null == govvieCurveTreasuryMaturityDateArray ? 0 :
			govvieCurveTreasuryMaturityDateArray.length;
		int govvieCurveEffectiveCount = null == govvieCurveTreasuryEffectiveDateArray ? 0 :
			govvieCurveTreasuryEffectiveDateArray.length;

		if (0 != govvieCurveMaturityCount) {
			govvieCurveMaturityDateArray = new JulianDate[govvieCurveMaturityCount];
		}

		if (0 != govvieCurveEffectiveCount) {
			govvieCurveEffectiveDateArray = new JulianDate[govvieCurveEffectiveCount];
		}

		CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		try {
			julianSpotDate = new JulianDate (spotDate);

			maturityDate = new JulianDate (bondMaturityDate);

			effectiveDate = new JulianDate (bondEffectiveDate);

			for (int govvieCurveMaturityIndex = 0;
				govvieCurveMaturityIndex < govvieCurveMaturityCount;
				++govvieCurveMaturityIndex)
			{
				govvieCurveMaturityDateArray[govvieCurveMaturityIndex] =
					new JulianDate (govvieCurveTreasuryMaturityDateArray[govvieCurveMaturityIndex]);
			}

			for (int govvieCurveEffectiveIndex = 0;
				govvieCurveEffectiveIndex < govvieCurveEffectiveCount;
				++govvieCurveEffectiveIndex)
			{
				govvieCurveEffectiveDateArray[govvieCurveEffectiveIndex] =
					new JulianDate (govvieCurveTreasuryEffectiveDateArray[govvieCurveEffectiveIndex]);
			}

			if (null != govvieCurveTreasuryYieldArray &&
				govvieCurveTreasuryYieldArray.length == treasuryBenchmarkCodeArray.length)
			{
				for (int treasuryBenchmarkCodeIndex = 0;
					treasuryBenchmarkCodeIndex < treasuryBenchmarkCodeArray.length;
					++treasuryBenchmarkCodeIndex)
				{
					ProductMultiMeasure productMultiMeasure = new ProductMultiMeasure();

					productMultiMeasure.addQuote (
						"Yield",
						new MultiSided ("mid", govvieCurveTreasuryYieldArray[treasuryBenchmarkCodeIndex]),
						true
					);

					if (!curveSurfaceQuoteContainer.setProductQuote (
						treasuryBenchmarkCodeArray[treasuryBenchmarkCodeIndex],
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

		BondComponent bond = BondBuilder.CreateSimpleFixed (
			issuerName + " " + FormatUtil.FormatDouble (bondCoupon, 1, 4, 100.) + " " + maturityDate,
			bondCouponCurrency,
			issuerName,
			bondCoupon,
			bondCouponFrequency,
			bondCouponDayCount,
			effectiveDate,
			maturityDate,
			null,
			null
		);

		if (null == bond) {
			return null;
		}

		ProductMultiMeasure productMultiMeasure = new ProductMultiMeasure();

		try {
			productMultiMeasure.addQuote (
				bondMarketQuoteName,
				new MultiSided ("mid", bondMarketQuote),
				true
			);
		} catch (Exception e) {
		}

		curveSurfaceQuoteContainer.setProductQuote (bond.name(), productMultiMeasure);

		MergedDiscountForwardCurve fundingDiscountCurve = LatentMarketStateBuilder.SmoothFundingCurve (
			julianSpotDate,
			bondCouponCurrency,
			fundingCurveDepositTenor,
			fundingCurveDepositQuote,
			fundingCurveDepositMeasure,
			fundingCurveFuturesQuote,
			fundingCurveFuturesMeasure,
			fundingCurveFixFloatTenor,
			fundingCurveFixFloatQuote,
			fundingFixFloatMeasure
		);

		curveSurfaceQuoteContainer.setFundingState (fundingDiscountCurve);

		curveSurfaceQuoteContainer.setGovvieState (
			LatentMarketStateBuilder.ShapePreservingGovvieCurve (
				govvieCode,
				julianSpotDate,
				govvieCurveEffectiveDateArray,
				govvieCurveMaturityDateArray,
				govvieCurveTreasuryCouponArray,
				govvieCurveTreasuryYieldArray,
				govvieCurveTreasuryMeasure
			)
		);

		curveSurfaceQuoteContainer.setCreditState (
			LatentMarketStateBuilder.CreditCurve (
				julianSpotDate,
				creditCurveName,
				creditCurveCDSTenorArray,
				creditCurveCDSCouponArray,
				creditCurveCDSQuoteArray,
				creditCurveCDSMeasure,
				fundingDiscountCurve
			)
		);

		return bond.value (ValuationParams.Spot (spotDate), null, curveSurfaceQuoteContainer, null);
	}

	/**
	 * Generate the Treasury Curve Tenor Key Rate Sensitivity/Duration
	 * 
	 * @param issuerName Bond Issuer Name
	 * @param bondEffectiveDate Bond Effective Date
	 * @param bondMaturityDate Bond Maturity Date
	 * @param bondCoupon Bond Coupon
	 * @param bondCouponFrequency Bond Coupon Frequency
	 * @param bondCouponDayCount Bond Coupon Day Count
	 * @param bondCouponCurrency Bond Coupon Currency
	 * @param spotDate Spot Date
	 * @param govvieCode Govvie Bond Code (for Treasury Curve)
	 * @param govvieCurveTreasuryEffectiveDateArray Array of the Treasury Instrument Effective Date (for
	 * 	Treasury Curve)
	 * @param govvieCurveTreasuryMaturityDateArray Array of the Treasury Instrument Maturity Date (for
	 * 	Treasury Curve)
	 * @param govvieCurveTreasuryCouponArray Array of the Treasury Instrument Coupon (for Treasury Curve)
	 * @param govvieCurveTreasuryYieldArray Array of the Treasury Instrument Yield (for Treasury Curve)
	 * @param govvieCurveTreasuryMeasure Treasury Instrument Measure (for Govvie Curve)
	 * @param bondMarketCleanPrice Bond Market Clean Price
	 * 
	 * @return The Treasury Curve Tenor Sensitivity/Duration
	 */

	public static final Map<String, Double> KeyRateDuration (
		final String issuerName,
		final int bondEffectiveDate,
		final int bondMaturityDate,
		final double bondCoupon,
		final int bondCouponFrequency,
		final String bondCouponDayCount,
		final String bondCouponCurrency,
		final int spotDate,
		final String govvieCode,
		final int[] govvieCurveTreasuryEffectiveDateArray,
		final int[] govvieCurveTreasuryMaturityDateArray,
		final double[] govvieCurveTreasuryCouponArray,
		final double[] govvieCurveTreasuryYieldArray,
		final String govvieCurveTreasuryMeasure,
		final double bondMarketCleanPrice)
	{
		JulianDate maturityDate = null;
		double baselineOAS = Double.NaN;
		JulianDate effectiveDate = null;
		JulianDate julianSpotDate = null;
		JulianDate[] govvieCurveMaturityDateArray = null;
		JulianDate[] govvieCurveEffectiveDateArray = null;
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

		if (0 != govvieCurveMaturityCount) {
			govvieCurveMaturityDateArray = new JulianDate[govvieCurveMaturityCount];
		}

		if (0 != govvieCurveEffectiveCount) {
			govvieCurveEffectiveDateArray = new JulianDate[govvieCurveEffectiveCount];
		}

		CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		try {
			julianSpotDate = new JulianDate (spotDate);

			maturityDate = new JulianDate (bondMaturityDate);

			effectiveDate = new JulianDate (bondEffectiveDate);

			for (int maturityDateIndex = 0;
				maturityDateIndex < govvieCurveMaturityCount;
				++maturityDateIndex)
			{
				govvieCurveMaturityDateArray[maturityDateIndex] =
					new JulianDate (govvieCurveTreasuryMaturityDateArray[maturityDateIndex]);
			}

			for (int effectiveDateIndex = 0;
				effectiveDateIndex < govvieCurveEffectiveCount;
				++effectiveDateIndex)
			{
				govvieCurveEffectiveDateArray[effectiveDateIndex] =
					new JulianDate (govvieCurveTreasuryEffectiveDateArray[effectiveDateIndex]);
			}

			if (null != govvieCurveTreasuryYieldArray &&
				govvieCurveTreasuryYieldArray.length == treasuryBenchmarkCodeArray.length)
			{
				for (int treasuryBenchmarkIndex = 0;
					treasuryBenchmarkIndex < treasuryBenchmarkCodeArray.length;
					++treasuryBenchmarkIndex)
				{
					ProductMultiMeasure productMultiMeasure = new ProductMultiMeasure();

					productMultiMeasure.addQuote (
						"Yield",
						new MultiSided ("mid", govvieCurveTreasuryYieldArray[treasuryBenchmarkIndex]),
						true
					);

					if (!curveSurfaceQuoteContainer.setProductQuote (
						treasuryBenchmarkCodeArray[treasuryBenchmarkIndex],
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

		BondComponent bond = BondBuilder.CreateSimpleFixed (
			issuerName + " " + FormatUtil.FormatDouble (bondCoupon, 1, 4, 100.) + " " + maturityDate,
			bondCouponCurrency,
			issuerName,
			bondCoupon,
			bondCouponFrequency,
			bondCouponDayCount,
			effectiveDate,
			maturityDate,
			null,
			null
		);

		if (null == bond) {
			return null;
		}

		ProductMultiMeasure productMultiMeasure = new ProductMultiMeasure();

		try {
			productMultiMeasure.addQuote ("Price", new MultiSided ("mid", bondMarketCleanPrice), true);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		curveSurfaceQuoteContainer.setProductQuote (bond.name(), productMultiMeasure);

		GovvieCurve govvieCurve = LatentMarketStateBuilder.ShapePreservingGovvieCurve (
			govvieCode,
			julianSpotDate,
			govvieCurveEffectiveDateArray,
			govvieCurveMaturityDateArray,
			govvieCurveTreasuryCouponArray,
			govvieCurveTreasuryYieldArray,
			govvieCurveTreasuryMeasure
		);

		curveSurfaceQuoteContainer.setGovvieState (govvieCurve);

		ValuationParams valParams = ValuationParams.Spot (spotDate);

		try {
			if (!NumberUtil.IsValid (
				baselineOAS = bond.oasFromPrice (
					valParams,
					curveSurfaceQuoteContainer,
					null,
					bondMarketCleanPrice
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
				govvieCode,
				julianSpotDate,
				govvieCurveEffectiveDateArray,
				govvieCurveMaturityDateArray,
				govvieCurveTreasuryCouponArray,
				govvieCurveTreasuryYieldArray,
				govvieCurveTreasuryMeasure,
				LatentMarketStateBuilder.SHAPE_PRESERVING,
				0.0001,
				false
			);

		if (null == tenorGovvieCurveMap || treasuryBenchmarkCodeArray.length > tenorGovvieCurveMap.size()) {
			return null;
		}

		CaseInsensitiveTreeMap<Double> keyRateDurationMap = new CaseInsensitiveTreeMap<Double>();

		for (Map.Entry<String, GovvieCurve> tenorGovvieCurveMapEntry : tenorGovvieCurveMap.entrySet()) {
			String key = tenorGovvieCurveMapEntry.getKey();

			if (!key.contains ("tsy")) {
				continue;
			}

			if (!curveSurfaceQuoteContainer.setGovvieState (tenorGovvieCurveMapEntry.getValue())) {
				return null;
			}

			try {
				keyRateDurationMap.put (
					key,
					10000. * (
						bond.priceFromOAS (valParams, curveSurfaceQuoteContainer, null, baselineOAS) -
							bondMarketCleanPrice
					) / bondMarketCleanPrice
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return keyRateDurationMap;
	}

	/**
	 * Returns Attribution for the Specified Bond Instance
	 * 
	 * @param issuerName Bond Issuer Name
	 * @param bondEffectiveDate Bond Effective Date
	 * @param bondMaturityDate Bond Maturity Date
	 * @param bondCoupon Bond Coupon
	 * @param bondCouponFrequency Bond Coupon Frequency
	 * @param bondCouponDayCount Bond Coupon Day Count
	 * @param bondCouponCurrency Bond Coupon Currency
	 * @param julianSpotDateArray Array of Spot Dates
	 * @param cleanPriceArray Array of Closing Clean Prices
	 * 
	 * @return List of the Position Change Components
	 */

	public static final List<PositionChangeComponents> HorizonChangeAttribution (
		final String issuerName,
		final int bondEffectiveDate,
		final int bondMaturityDate,
		final double bondCoupon,
		final int bondCouponFrequency,
		final String bondCouponDayCount,
		final String bondCouponCurrency,
		final JulianDate[] julianSpotDateArray,
		final double[] cleanPriceArray)
	{
		JulianDate maturityDate = null;
		JulianDate effectiveDate = null;

		try {
			maturityDate = new JulianDate (bondMaturityDate);

			effectiveDate = new JulianDate (bondEffectiveDate);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		BondComponent bond = BondBuilder.CreateSimpleFixed (
			issuerName + " " + FormatUtil.FormatDouble (bondCoupon, 1, 4, 100.) + " " + maturityDate,
			bondCouponCurrency,
			issuerName,
			bondCoupon,
			bondCouponFrequency,
			bondCouponDayCount,
			effectiveDate,
			maturityDate,
			null,
			null
		);

		if (null == bond || null == julianSpotDateArray || null == cleanPriceArray) {
			return null;
		}

		int[] spotDateArray = new int[julianSpotDateArray.length];
		double[] yieldArray = new double[julianSpotDateArray.length];
		double[] dirtyPriceArray = new double[julianSpotDateArray.length];
		double[] modifiedDurationArray = new double[julianSpotDateArray.length];

		if (1 >= julianSpotDateArray.length || julianSpotDateArray.length != cleanPriceArray.length) {
			return null;
		}

		for (int spotDateIndex = 0; spotDateIndex < julianSpotDateArray.length; ++spotDateIndex) {
			ValuationParams spotValuationParams = ValuationParams.Spot (
				spotDateArray[spotDateIndex] = julianSpotDateArray[spotDateIndex].julian()
			);

			try {
				if (!NumberUtil.IsValid (
					yieldArray[spotDateIndex] = bond.yieldFromPrice (
						spotValuationParams,
						null,
						null,
						cleanPriceArray[spotDateIndex]
					)
				))
				{
					return null;
				}

				if (!NumberUtil.IsValid (
					modifiedDurationArray[spotDateIndex] = bond.modifiedDurationFromPrice (
						spotValuationParams,
						null,
						null,
						cleanPriceArray[spotDateIndex]
					)
				))
				{
					return null;
				}

				if (!NumberUtil.IsValid (
					dirtyPriceArray[spotDateIndex] = cleanPriceArray[spotDateIndex] + bond.accrued (
						spotDateArray[spotDateIndex],
						null
					)
				))
				{
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		List<PositionChangeComponents> positionChangeComponentsList =
			new ArrayList<PositionChangeComponents>();

		for (int spotDateIndex = 1; spotDateIndex < julianSpotDateArray.length; ++spotDateIndex) {
			try {
				BondMarketSnap bondMarketSnap1 = new BondMarketSnap (
					julianSpotDateArray[spotDateIndex - 1],
					cleanPriceArray[spotDateIndex - 1]
				);

				if (!bondMarketSnap1.setYieldMarketFactor (
					yieldArray[spotDateIndex - 1],
					-1. * dirtyPriceArray[spotDateIndex - 1] * modifiedDurationArray[spotDateIndex - 1],
					0.
				))
				{
					return null;
				}

				BondMarketSnap bondMarketSnap2 = new BondMarketSnap (
					julianSpotDateArray[spotDateIndex],
					cleanPriceArray[spotDateIndex]
				);

				if (!bondMarketSnap2.setYieldMarketFactor (
					yieldArray[spotDateIndex],
					-1. * dirtyPriceArray[spotDateIndex] * modifiedDurationArray[spotDateIndex],
					0.
				))
				{
					return null;
				}

				positionChangeComponentsList.add (
					new PositionChangeComponents (
						false,
						bondMarketSnap1,
						bondMarketSnap2,
						Convention.YearFraction (
							spotDateArray[spotDateIndex - 1],
							spotDateArray[spotDateIndex],
							bondCouponDayCount,
							false,
							null,
							bondCouponCurrency
						),
						null
					)
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return positionChangeComponentsList;
	}

	/**
	 * Generate the Relative Value Metrics for the Specified Bond
	 * 
	 * @param issuerName Bond Issuer Name
	 * @param bondEffectiveDate Bond Effective Date
	 * @param bondMaturityDate Bond Maturity Date
	 * @param bondCoupon Bond Coupon
	 * @param bondCouponFrequency Bond Coupon Frequency
	 * @param bondCouponDayCount Bond Coupon Day Count
	 * @param bondCouponCurrency Bond Coupon Currency
	 * @param spotDate Spot Date
	 * @param fundingCurveDepositTenor Deposit Instruments Tenor (for Funding Curve)
	 * @param fundingCurveDepositQuote Deposit Instruments Quote (for Funding Curve)
	 * @param fundingCurveDepositMeasure Deposit Instruments Measure (for Funding Curve)
	 * @param fundingCurveFuturesQuote Futures Instruments Tenor (for Funding Curve)
	 * @param fundingCurveFuturesMeasure Futures Instruments Measure (for Funding Curve)
	 * @param fundingCurveFixFloatTenor Fix-Float Instruments Tenor (for Funding Curve)
	 * @param fundingCurveFixFloatQuote Fix-Float Instruments Quote (for Funding Curve)
	 * @param fundingFixFloatMeasure Fix-Float Instruments Tenor (for Funding Curve)
	 * @param govvieCode Govvie Bond Code (for Treasury Curve)
	 * @param govvieCurveTreasuryEffectiveDateArray Array of the Treasury Instrument Effective Date (for
	 * 	Treasury Curve)
	 * @param govvieCurveTreasuryMaturityDateArray Array of the Treasury Instrument Maturity Date (for
	 * 	Treasury Curve)
	 * @param govvieCurveTreasuryCouponArray Array of the Treasury Instrument Coupon (for Treasury Curve)
	 * @param govvieCurveTreasuryYieldArray Array of the Treasury Instrument Yield (for Treasury Curve)
	 * @param govvieCurveTreasuryMeasure Treasury Instrument Measure (for Treasury Curve)
	 * @param creditCurveName Credit Curve Name (for Credit Curve)
	 * @param creditCurveCDSTenorArray CDS Maturity Tenor (for Credit Curve)
	 * @param creditCurveCDSCouponArray Array of CDS Fixed Coupon (for Credit Curve)
	 * @param creditCurveCDSQuoteArray Array of CDS Market Quotes (for Credit Curve)
	 * @param creditCurveCDSMeasure CDS Calibration Measure (for Credit Curve)
	 * @param bondMarketCleanPrice Bond Market Clean Price
	 * 
	 * @return The Relative Value Metrics
	 */

	public static final BondRVMeasures RelativeValueMetrics (
		final String issuerName,
		final int bondEffectiveDate,
		final int bondMaturityDate,
		final double bondCoupon,
		final int bondCouponFrequency,
		final String bondCouponDayCount,
		final String bondCouponCurrency,
		final int spotDate,
		final String[] fundingCurveDepositTenor,
		final double[] fundingCurveDepositQuote,
		final String fundingCurveDepositMeasure,
		final double[] fundingCurveFuturesQuote,
		final String fundingCurveFuturesMeasure,
		final String[] fundingCurveFixFloatTenor,
		final double[] fundingCurveFixFloatQuote,
		final String fundingFixFloatMeasure,
		final String govvieCode,
		final int[] govvieCurveTreasuryEffectiveDateArray,
		final int[] govvieCurveTreasuryMaturityDateArray,
		final double[] govvieCurveTreasuryCouponArray,
		final double[] govvieCurveTreasuryYieldArray,
		final String govvieCurveTreasuryMeasure,
		final String creditCurveName,
		final String[] creditCurveCDSTenorArray,
		final double[] creditCurveCDSCouponArray,
		final double[] creditCurveCDSQuoteArray,
		final String creditCurveCDSMeasure,
		final double bondMarketCleanPrice)
	{
		JulianDate maturityDate = null;
		JulianDate effectiveDate = null;
		JulianDate julianSpotDate = null;
		JulianDate[] govvieCurveMaturityDateArray = null;
		JulianDate[] govvieCurveEffectiveDateArray = null;
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

		if (0 != govvieCurveMaturityCount) {
			govvieCurveMaturityDateArray = new JulianDate[govvieCurveMaturityCount];
		}

		if (0 != govvieCurveEffectiveCount) {
			govvieCurveEffectiveDateArray = new JulianDate[govvieCurveEffectiveCount];
		}

		CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = new CurveSurfaceQuoteContainer();

		try {
			julianSpotDate = new JulianDate (spotDate);

			maturityDate = new JulianDate (bondMaturityDate);

			effectiveDate = new JulianDate (bondEffectiveDate);

			for (int maturityIndex = 0; maturityIndex < govvieCurveMaturityCount; ++maturityIndex) {
				govvieCurveMaturityDateArray[maturityIndex] =
					new JulianDate (govvieCurveTreasuryMaturityDateArray[maturityIndex]);
			}

			for (int effectiveIndex = 0; effectiveIndex < govvieCurveEffectiveCount; ++effectiveIndex) {
				govvieCurveEffectiveDateArray[effectiveIndex] =
					new JulianDate (govvieCurveTreasuryEffectiveDateArray[effectiveIndex]);
			}

			if (null != govvieCurveTreasuryYieldArray &&
				govvieCurveTreasuryYieldArray.length == treasuryBenchmarkCodeArray.length)
			{
				for (int treasuryBenchmarIndex = 0;
					treasuryBenchmarIndex < treasuryBenchmarkCodeArray.length;
					++treasuryBenchmarIndex)
				{
					ProductMultiMeasure productMultiMeasure = new ProductMultiMeasure();

					productMultiMeasure.addQuote (
						"Yield",
						new MultiSided ("mid", govvieCurveTreasuryYieldArray[treasuryBenchmarIndex]),
						true
					);

					if (!curveSurfaceQuoteContainer.setProductQuote (
						treasuryBenchmarkCodeArray[treasuryBenchmarIndex],
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

		BondComponent bond = BondBuilder.CreateSimpleFixed (
			issuerName + " " + FormatUtil.FormatDouble (bondCoupon, 1, 4, 100.) + " " + maturityDate,
			bondCouponCurrency,
			issuerName,
			bondCoupon,
			bondCouponFrequency,
			bondCouponDayCount,
			effectiveDate,
			maturityDate,
			null,
			null
		);

		if (null == bond) {
			return null;
		}

		ProductMultiMeasure productMultiMeasure = new ProductMultiMeasure();

		try {
			productMultiMeasure.addQuote ("Price", new MultiSided ("mid", bondMarketCleanPrice), true);
		} catch (Exception e) {
		}

		curveSurfaceQuoteContainer.setProductQuote (bond.name(), productMultiMeasure);

		MergedDiscountForwardCurve fundingDiscountCurve = LatentMarketStateBuilder.SmoothFundingCurve (
			julianSpotDate,
			bondCouponCurrency,
			fundingCurveDepositTenor,
			fundingCurveDepositQuote,
			fundingCurveDepositMeasure,
			fundingCurveFuturesQuote,
			fundingCurveFuturesMeasure,
			fundingCurveFixFloatTenor,
			fundingCurveFixFloatQuote,
			fundingFixFloatMeasure
		);

		curveSurfaceQuoteContainer.setFundingState (fundingDiscountCurve);

		curveSurfaceQuoteContainer.setGovvieState (
			LatentMarketStateBuilder.ShapePreservingGovvieCurve (
				govvieCode,
				julianSpotDate,
				govvieCurveEffectiveDateArray,
				govvieCurveMaturityDateArray,
				govvieCurveTreasuryCouponArray,
				govvieCurveTreasuryYieldArray,
				govvieCurveTreasuryMeasure
			)
		);

		curveSurfaceQuoteContainer.setCreditState (
			LatentMarketStateBuilder.CreditCurve (
				julianSpotDate,
				creditCurveName,
				creditCurveCDSTenorArray,
				creditCurveCDSCouponArray,
				creditCurveCDSQuoteArray,
				creditCurveCDSMeasure,
				fundingDiscountCurve
			)
		);

		ValuationParams valuationParams = ValuationParams.Spot (spotDate);

		return bond.standardMeasures (
			valuationParams,
			null,
			curveSurfaceQuoteContainer,
			null,
			bond.exerciseYieldFromPrice (
				valuationParams,
				curveSurfaceQuoteContainer,
				null,
				bondMarketCleanPrice
			),
			bondMarketCleanPrice
		);
	}
}
