
package org.drip.state.discount;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.daycount.ActActDCParams;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.definition.Curve;
import org.drip.market.otc.FixedFloatSwapConvention;
import org.drip.market.otc.IBORFixedFloatContainer;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.period.UnitCouponAccrualSetting;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.definition.CalibratableComponent;
import org.drip.state.creator.ScenarioDiscountCurveBuilder;
import org.drip.state.nonlinear.FlatForwardDiscountCurve;

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
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>DiscountCurve</i> Interface combines the Interfaces of Latent State Curve Representation and Discount
 * Factor Estimator. It exposes the following functionality:
 * 
 *  <ul>
 *  	<li>Construct a Flat Forward Instance of the Curve at the specified Date Nodes</li>
 *  	<li>Construct Flat Native Forward Instance of the Curve at the specified Date Nodes</li>
 *  	<li>Construct Flat Native Forward Instance of the Curve at the specified Date Node Tenors</li>
 *  	<li>Construct Flat Native Forward Instance of the Curve at the specified Date Nodes with (Exclusive/Inclusive) Bumps applied within the Tenors</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/README.md">Latent State Inference and Creation Utilities</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/discount/README.md">Discount Curve Spline Latent State</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class DiscountCurve implements Curve, DiscountFactorEstimator
{

	/**
	 * Construct a Flat Forward Instance of the Curve at the specified Date Nodes
	 * 
	 * @param dayCount Forward Curve Day Count
	 * @param frequency Forward Curve Frequency
	 * @param dateArray Array of Date Nodes
	 * 
	 * @return The Flat Forward Instance
	 */

	public FlatForwardDiscountCurve flatForward (
		final String dayCount,
		final int frequency,
		final int[] dateArray)
	{
		if (null == dateArray) {
			return null;
		}

		int dateNodeCount = dateArray.length;
		double[] forwardRateArray = 0 == dateNodeCount ? null : new double [dateNodeCount];

		if (0 == dateNodeCount) {
			return null;
		}

		String currency = currency();

		JulianDate startDate = epoch();

		ActActDCParams actActDCParams = ActActDCParams.FromFrequency (frequency);

		try {
			for (int dateNodeIndex = 0; dateNodeIndex < dateNodeCount; ++dateNodeIndex) {
				forwardRateArray[dateNodeIndex] = (
					(df (startDate) / df (dateArray[dateNodeIndex])) - 1.
				) / Convention.YearFraction (
					0 == dateNodeIndex ? startDate.julian() : dateArray[dateNodeIndex - 1],
					dateArray[dateNodeIndex],
					dayCount,
					false,
					actActDCParams,
					currency
				);
			}

			return new FlatForwardDiscountCurve (
				startDate,
				currency,
				dateArray,
				forwardRateArray,
				true,
				dayCount,
				frequency
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct Flat Native Forward Instance of the Curve at the specified Date Nodes
	 * 
	 * @param dateArray Array of Date Nodes
	 * @param bump The Bump Amount
	 * 
	 * @return The Flat Forward Instance
	 */

	public org.drip.state.nonlinear.FlatForwardDiscountCurve flatNativeForward (
		final int[] dateArray,
		final double bump)
	{
		if (null == dateArray || !NumberUtil.IsValid (bump)) {
			return null;
		}

		int nodeCount = dateArray.length;
		double[] forwardRateArray = 0 == nodeCount ? null : new double [nodeCount];

		if (0 == nodeCount) {
			return null;
		}

		String currency = currency();

		FixedFloatSwapConvention fixedFloatSwapConvention =
			IBORFixedFloatContainer.ConventionFromJurisdiction (currency);

		if (null == fixedFloatSwapConvention) {
			return null;
		}

		UnitCouponAccrualSetting unitCouponAccrualSetting =
			fixedFloatSwapConvention.floatStreamConvention().floaterIndex().ucas();

		JulianDate epochDate = epoch();

		int spotDate = epochDate.julian();

		int frequency = unitCouponAccrualSetting.freq();

		String dayCount = unitCouponAccrualSetting.couponDC();

		ActActDCParams actActDCParams = ActActDCParams.FromFrequency (frequency);

		CalibratableComponent[] calibratableComponentArray = calibComp();

		int calibratableComponentCount = calibratableComponentArray.length;
		String[] calibrationMeasureArray = new String[calibratableComponentCount];
		double[] componentCalibrationValueArray = new double[calibratableComponentCount];

		CurveSurfaceQuoteContainer nativeCurveSurfaceQuoteContainer = MarketParamsBuilder.Create (
			(MergedDiscountForwardCurve) this,
			null,
			null,
			null,
			null,
			null,
			null
		);

		ValuationParams valuationParams = ValuationParams.Spot (spotDate);

		for (int componentIndex = 0; componentIndex < calibratableComponentCount; ++componentIndex) {
			calibrationMeasureArray[componentIndex] = "Rate";

			try {
				componentCalibrationValueArray[componentIndex] =
					calibratableComponentArray[componentIndex].measureValue (
						valuationParams,
						null,
						nativeCurveSurfaceQuoteContainer,
						null,
						calibrationMeasureArray[componentIndex]
					);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		MergedDiscountForwardCurve nonLinearDiscountCurve = ScenarioDiscountCurveBuilder.NonlinearBuild (
			epochDate,
			currency,
			calibratableComponentArray,
			componentCalibrationValueArray,
			calibrationMeasureArray,
			null
		);

		try {
			for (int nodeIndex = 0; nodeIndex < nodeCount; ++nodeIndex) {
				int startDate = 0 == nodeIndex ? spotDate : dateArray[nodeIndex - 1];

				forwardRateArray[nodeIndex] = (
					(nonLinearDiscountCurve.df (startDate) /
						nonLinearDiscountCurve.df (dateArray[nodeIndex])) - 1.
				) / Convention.YearFraction (
					startDate,
					dateArray[nodeIndex],
					dayCount,
					false,
					actActDCParams,
					currency
				) + bump;
			}

			return new FlatForwardDiscountCurve (
				epochDate,
				currency,
				dateArray,
				forwardRateArray,
				true,
				dayCount,
				frequency
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct Flat Native Forward Instance of the Curve at the specified Date Node Tenors
	 * 
	 * @param tenorArray Array of Date Tenors
	 * @param bump The Bump Amount
	 * 
	 * @return The Flat Forward Instance
	 */

	public FlatForwardDiscountCurve flatNativeForward (
		final String[] tenorArray,
		final double bump)
	{
		if (null == tenorArray || !NumberUtil.IsValid (bump)) {
			return null;
		}

		int tenorCount = tenorArray.length;
		double[] forwardRateArray = 0 == tenorCount ? null : new double [tenorCount];

		if (0 == tenorCount) {
			return null;
		}

		String currency = currency();

		FixedFloatSwapConvention fixedFloatSwapConvention =
			IBORFixedFloatContainer.ConventionFromJurisdiction (currency);

		if (null == fixedFloatSwapConvention) {
			return null;
		}

		UnitCouponAccrualSetting unitCouponAccrualSetting =
			fixedFloatSwapConvention.floatStreamConvention().floaterIndex().ucas();

		JulianDate startDate = epoch();

		int frequency = unitCouponAccrualSetting.freq();

		String dayCount = unitCouponAccrualSetting.couponDC();

		ActActDCParams actActDCParams = ActActDCParams.FromFrequency (frequency);

		int[] dateArray = new int[tenorCount];

		try {
			for (int tenorIndex = 0; tenorIndex < tenorCount; ++tenorIndex) {
				JulianDate tenorDate = startDate.addTenor (tenorArray[tenorIndex]);

				if (null == tenorDate) {
					return null;
				}

				dateArray[tenorIndex] = tenorDate.julian();

				int tenorStartDate = 0 == tenorIndex ? startDate.julian() : dateArray[tenorIndex - 1];

				forwardRateArray[tenorIndex] = (
					(df (tenorStartDate) / df (dateArray[tenorIndex])) - 1.
				) / Convention.YearFraction (
					tenorStartDate,
					dateArray[tenorIndex],
					dayCount,
					false,
					actActDCParams,
					currency
				) + bump;
			}

			return new FlatForwardDiscountCurve (
				startDate,
				currency,
				dateArray,
				forwardRateArray,
				true,
				dayCount,
				frequency
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct Flat Native Forward Instance of the Curve at the specified Date Nodes with
	 * 	(Exclusive/Inclusive) Bumps applied within the Tenors
	 * 
	 * @param dateArray Array of Date Nodes
	 * @param bumpNodeIndex The Node to be Bumped
	 * @param bump The Bump Amount
	 * 
	 * @return The Flat Forward Instance
	 */

	public FlatForwardDiscountCurve flatNativeForwardEI (
		final int[] dateArray,
		final int bumpNodeIndex,
		final double bump)
	{
		if (null == dateArray || !NumberUtil.IsValid (bump)) {
			return null;
		}

		int nodeCount = dateArray.length;
		double[] forwardRateArray = 0 == nodeCount ? null : new double [nodeCount];

		if (0 == nodeCount) {
			return null;
		}

		String currency = currency();

		FixedFloatSwapConvention fixedFloatSwapConvention =
			IBORFixedFloatContainer.ConventionFromJurisdiction (currency);

		if (null == fixedFloatSwapConvention) {
			return null;
		}

		UnitCouponAccrualSetting unitCouponAccrualSetting =
			fixedFloatSwapConvention.floatStreamConvention().floaterIndex().ucas();

		JulianDate epochDate = epoch();

		int frequency = unitCouponAccrualSetting.freq();

		String dayCount = unitCouponAccrualSetting.couponDC();

		ActActDCParams actActDCParams = ActActDCParams.FromFrequency (frequency);

		try {
			for (int nodeIndex = 0; nodeIndex < nodeCount; ++nodeIndex) {
				int startDate = 0 == nodeIndex ? epochDate.julian() : dateArray[nodeIndex - 1];

				forwardRateArray[nodeIndex] = (
					(df (startDate) / df (dateArray[nodeIndex])) - 1.
				) / Convention.YearFraction (
					startDate,
					dateArray[nodeIndex],
					dayCount,
					false,
					actActDCParams,
					currency
				) + (nodeIndex == bumpNodeIndex ? bump : 0.);
			}

			return new FlatForwardDiscountCurve (
				epochDate,
				currency,
				dateArray,
				forwardRateArray,
				true,
				dayCount,
				frequency
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
