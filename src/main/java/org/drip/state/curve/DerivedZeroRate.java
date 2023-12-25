
package org.drip.state.curve;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.drip.analytics.cashflow.CompositePeriod;
import org.drip.analytics.date.JulianDate;
import org.drip.analytics.daycount.ActActDCParams;
import org.drip.analytics.daycount.Convention;
import org.drip.analytics.definition.Curve;
import org.drip.analytics.input.CurveConstructionInputSet;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.analytics.support.Helper;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.definition.ManifestMeasureTweak;
import org.drip.param.valuation.ValuationCustomizationParams;
import org.drip.product.definition.CalibratableComponent;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.stretch.BoundarySettings;
import org.drip.spline.stretch.MultiSegmentSequence;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.discount.DiscountCurve;
import org.drip.state.discount.ZeroCurve;
import org.drip.state.govvie.GovvieCurve;
import org.drip.state.identifier.LatentStateLabel;

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
 * Copyright (C) 2012 Lakshmi Krishnamurthy
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
 * <i>DerivedZeroRate</i> implements the delegated ZeroCurve functionality. Beyond discount factor/zero rate
 * 	computation at specific cash pay nodes, all other functions are delegated to the embedded discount curve.
 *  It exports the following Functionality:
 *
 *  <ul>
 *  	<li>Construct an Instance from the Discount Curve and the related Parameters</li>
 *  	<li>Construct an Instance from the Govvie Curve and the related Parameters</li>
 *  	<li>Construct an Instance from the Input Curve and the related Parameters</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/curve/README.md">Basis Spline Based Latent States</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DerivedZeroRate extends ZeroCurve
{
	private static final int NUM_DF_QUADRATURES = 5;

	private DiscountCurve _discountCurve = null;
	private MultiSegmentSequence _zeroRateMultiSegmentSequence = null;
	private MultiSegmentSequence _discountFactorMultiSegmentSequence = null;

	private static final boolean EntryFromDiscountCurve (
		final DiscountCurve discountCurve,
		final int date,
		final int frequency,
		final double yearFraction,
		final double shift,
		final Map<Integer, Double> dateDiscountFactorMap,
		final Map<Integer, Double> dateZeroRateMap)
	{
		try {
			double zeroRate = Helper.DF2Yield (frequency, discountCurve.df (date), yearFraction) + shift;

			dateDiscountFactorMap.put (date, Helper.Yield2DF (frequency, zeroRate, yearFraction));

			dateZeroRateMap.put (date, zeroRate);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private static final boolean EntryFromYield (
		final int date,
		final int frequency,
		final double yearFraction,
		final double shiftedYield,
		final Map<Integer, Double> dateDiscountFactorMap,
		final Map<Integer, Double> dateZeroRateMap)
	{
		try {
			dateDiscountFactorMap.put (date, Helper.Yield2DF (frequency, shiftedYield, yearFraction));

			dateZeroRateMap.put (date, shiftedYield);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Construct an Instance from the Discount Curve and the related Parameters
	 * 
	 * @param zeroCurveFrequency Zero Curve Frequency
	 * @param zeroCurveDayCount Zero Curve Day Count
	 * @param zeroCurveCalendar Zero Curve Calendar
	 * @param zeroCurveApplyEOMAdjustment Zero Coupon EOM Adjustment Flag
	 * @param couponPeriodList List of Bond coupon periods
	 * @param workoutDate Work-out Date
	 * @param valuationDate Value Date
	 * @param cashPayDate Cash-Pay Date
	 * @param discountCurve Underlying Discount Curve
	 * @param zeroCurveBump DC Bump
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * @param segmentCustomBuilderControl Segment Custom Builder Control Parameters
	 * 
	 * @return The Derived Zero Rate Instance
	 */

	public static final DerivedZeroRate FromDiscountCurve (
		final int zeroCurveFrequency,
		final String zeroCurveDayCount,
		final String zeroCurveCalendar,
		final boolean zeroCurveApplyEOMAdjustment,
		final List<CompositePeriod> couponPeriodList,
		final int workoutDate,
		final int valuationDate,
		final int cashPayDate,
		final DiscountCurve discountCurve,
		final double zeroCurveBump,
		final ValuationCustomizationParams valuationCustomizationParams,
		final SegmentCustomBuilderControl segmentCustomBuilderControl)
	{
		if (null == couponPeriodList || 2 > couponPeriodList.size() || null == discountCurve ||
			!NumberUtil.IsValid (zeroCurveBump) || null == segmentCustomBuilderControl) {
			return null;
		}

		String calendar = zeroCurveCalendar;
		int frequency = 0 == zeroCurveFrequency ? 2 : zeroCurveFrequency;

		String dayCount = null == zeroCurveDayCount || zeroCurveDayCount.isEmpty() ?
			"30/360" : zeroCurveDayCount;

		if (null != valuationCustomizationParams) {
			dayCount = valuationCustomizationParams.yieldDayCount();

			frequency = valuationCustomizationParams.yieldFreq();

			calendar = valuationCustomizationParams.yieldCalendar();
		}

		Map<Integer, Double> dateDiscountFactorMap = new TreeMap<Integer, Double>();

		Map<Integer, Double> dateZeroRateMap = new TreeMap<Integer, Double>();

		dateDiscountFactorMap.put (valuationDate, 1.);

		dateZeroRateMap.put (valuationDate, 0.);

		for (CompositePeriod period : couponPeriodList) {
			int periodPayDate = period.payDate();

			if (valuationDate >= periodPayDate) {
				continue;
			}

			int periodStartDate = period.startDate();

			int periodEndDate = period.endDate();

			try {
				if (!EntryFromDiscountCurve (
					discountCurve,
					periodPayDate,
					frequency,
					Convention.YearFraction (
						valuationDate,
						periodPayDate,
						dayCount,
						true,
						new ActActDCParams (
							frequency,
							periodEndDate - periodStartDate
						),
						calendar
					),
					zeroCurveBump,
					dateDiscountFactorMap,
					dateZeroRateMap
				)) {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		ActActDCParams actActDCParams = ActActDCParams.FromFrequency (frequency);

		try {
			if (!EntryFromDiscountCurve (
				discountCurve,
				workoutDate,
				frequency,
				Convention.YearFraction (
					valuationDate,
					workoutDate,
					dayCount,
					true,
					actActDCParams,
					calendar
				),
				zeroCurveBump,
				dateDiscountFactorMap,
				dateZeroRateMap
			)) {
				return null;
			}

			if (valuationDate != cashPayDate) {
				if (!EntryFromDiscountCurve (
					discountCurve,
					cashPayDate,
					frequency,
					Convention.YearFraction (
						valuationDate,
						cashPayDate,
						dayCount,
						true,
						actActDCParams,
						calendar
					),
					zeroCurveBump,
					dateDiscountFactorMap,
					dateZeroRateMap
				)) {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		int nodeCount = dateDiscountFactorMap.size();

		int nodeIndex = 0;
		double[] dateArray = new double[nodeCount];
		double[] zeroRateArray = new double[nodeCount];
		double[] discountFactorArray = new double[nodeCount];

		for (Map.Entry<Integer, Double> dateDiscountFactorMapEntry : dateDiscountFactorMap.entrySet()) {
			dateArray[nodeIndex] = dateDiscountFactorMapEntry.getKey();

			discountFactorArray[nodeIndex] = dateDiscountFactorMapEntry.getValue();

			zeroRateArray[nodeIndex++] = dateZeroRateMap.get (dateDiscountFactorMapEntry.getKey());
		}

		SegmentCustomBuilderControl[] segmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[discountFactorArray.length - 1]; 

		for (int discountFactorArrayIndex = 0; discountFactorArrayIndex < discountFactorArray.length - 1;
			++discountFactorArrayIndex) {
			segmentCustomBuilderControlArray[discountFactorArrayIndex] = segmentCustomBuilderControl;
		}

		BoundarySettings naturalBoundarySettings = BoundarySettings.NaturalStandard();

		try {
			return new DerivedZeroRate (
				discountCurve,
				MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
					"DF_STRETCH",
					dateArray,
					discountFactorArray,
					segmentCustomBuilderControlArray,
					null,
					naturalBoundarySettings,
					MultiSegmentSequence.CALIBRATE
				),
				MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
					"ZERO_RATE_STRETCH",
					dateArray,
					zeroRateArray,
					segmentCustomBuilderControlArray,
					null,
					naturalBoundarySettings,
					MultiSegmentSequence.CALIBRATE
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance from the Govvie Curve and the related Parameters
	 * 
	 * @param zeroCurveFrequency Zero Curve Frequency
	 * @param zeroCurveDayCountConvention Zero Curve Day Count
	 * @param zeroCurveCalendar Zero Curve Calendar
	 * @param zeroCurveApplyEOMAdjustment Zero Coupon EOM Adjustment Flag
	 * @param couponPeriodList List of bond coupon periods
	 * @param workoutDate Work-out Date
	 * @param valuationDate Value Date
	 * @param cashPayDate Cash-Pay Date
	 * @param govvieCurve Underlying Govvie Curve
	 * @param zeroCurveBump DC Bump
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * @param segmentCustomBuilderControl Segment Custom Builder Control Parameters
	 * 
	 * @return The Derived Zero Rate Instance
	 */

	public static final DerivedZeroRate FromGovvieCurve (
		final int zeroCurveFrequency,
		final String zeroCurveDayCountConvention,
		final String zeroCurveCalendar,
		final boolean zeroCurveApplyEOMAdjustment,
		final List<CompositePeriod> couponPeriodList,
		final int workoutDate,
		final int valuationDate,
		final int cashPayDate,
		final GovvieCurve govvieCurve,
		final double zeroCurveBump,
		final ValuationCustomizationParams valuationCustomizationParams,
		final SegmentCustomBuilderControl segmentCustomBuilderControl)
	{
		if (null == couponPeriodList || 2 > couponPeriodList.size() || null == govvieCurve ||
			!NumberUtil.IsValid (zeroCurveBump) || null == segmentCustomBuilderControl) {
			return null;
		}

		int frequency = 0 == zeroCurveFrequency ? 2 : zeroCurveFrequency;
		boolean applyCouponEOMAdjustment = zeroCurveApplyEOMAdjustment;
		String calendar = zeroCurveCalendar;
		double shiftedYield = Double.NaN;

		try {
			shiftedYield = govvieCurve.yld (workoutDate) + zeroCurveBump;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		String dayCountConvention = null == zeroCurveDayCountConvention ||
			zeroCurveDayCountConvention.isEmpty() ? "30/360" : zeroCurveDayCountConvention;

		if (null != valuationCustomizationParams) {
			frequency = valuationCustomizationParams.yieldFreq();

			calendar = valuationCustomizationParams.yieldCalendar();

			dayCountConvention = valuationCustomizationParams.yieldDayCount();

			applyCouponEOMAdjustment = valuationCustomizationParams.applyYieldEOMAdj();
		}

		Map<Integer, Double> dateDiscountFactorMap = new TreeMap<Integer, Double>();

		Map<Integer, Double> dateZeroRateMap = new TreeMap<Integer, Double>();

		dateDiscountFactorMap.put (valuationDate, 1.);

		dateZeroRateMap.put (valuationDate, 0.);

		for (CompositePeriod period : couponPeriodList) {
			int periodPayDate = period.payDate();

			if (valuationDate >= periodPayDate) {
				continue;
			}

			try {
				if (!EntryFromYield (
					periodPayDate,
					frequency,
					Convention.YearFraction (
						valuationDate,
						periodPayDate,
						dayCountConvention,
						applyCouponEOMAdjustment,
						new ActActDCParams (frequency, period.endDate() - period.startDate()),
						calendar
					),
					shiftedYield,
					dateDiscountFactorMap,
					dateZeroRateMap
				)) {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		ActActDCParams actActDCParams = ActActDCParams.FromFrequency (frequency);

		try {
			if (!EntryFromYield (
				workoutDate,
				frequency,
				Convention.YearFraction (
					valuationDate,
					workoutDate,
					dayCountConvention,
					applyCouponEOMAdjustment,
					actActDCParams,
					calendar
				),
				shiftedYield,
				dateDiscountFactorMap,
				dateZeroRateMap
			)) {
				return null;
			}

			if (cashPayDate != valuationDate) {
				if (!EntryFromYield (
					cashPayDate,
					frequency,
					Convention.YearFraction (
						valuationDate,
						cashPayDate,
						dayCountConvention,
						applyCouponEOMAdjustment,
						actActDCParams,
						calendar
					),
					shiftedYield,
					dateDiscountFactorMap,
					dateZeroRateMap
				)) {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		int nodeCount = dateDiscountFactorMap.size();

		int nodeIndex = 0;
		double[] dateArray = new double[nodeCount];
		double[] zeroRateArray = new double[nodeCount];
		double[] discountFactorArray = new double[nodeCount];

		for (Map.Entry<Integer, Double> dateDiscountFactorMapEntry : dateDiscountFactorMap.entrySet()) {
			dateArray[nodeIndex] = dateDiscountFactorMapEntry.getKey();

			discountFactorArray[nodeIndex] = dateDiscountFactorMapEntry.getValue();

			zeroRateArray[nodeIndex++] = dateZeroRateMap.get (dateDiscountFactorMapEntry.getKey());
		}

		SegmentCustomBuilderControl[] segmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[discountFactorArray.length - 1]; 

		for (int discountFactorArrayIndex = 0; discountFactorArrayIndex < discountFactorArray.length - 1;
			++discountFactorArrayIndex) {
			segmentCustomBuilderControlArray[discountFactorArrayIndex] = segmentCustomBuilderControl;
		}

		BoundarySettings naturalBoundarySettings = BoundarySettings.NaturalStandard();

		try {
			return new DerivedZeroRate (
				govvieCurve,
				MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
					"DF_STRETCH",
					dateArray,
					discountFactorArray,
					segmentCustomBuilderControlArray,
					null,
					naturalBoundarySettings,
					MultiSegmentSequence.CALIBRATE
				),
				MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
					"ZERO_RATE_STRETCH",
					dateArray,
					zeroRateArray,
					segmentCustomBuilderControlArray,
					null,
					naturalBoundarySettings,
					MultiSegmentSequence.CALIBRATE
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance from the Input Curve and the related Parameters
	 * 
	 * @param frequency Zero Curve Frequency
	 * @param dayCount Zero Curve Day Count
	 * @param calendar Zero Curve Calendar
	 * @param applyEOMAdjustment Zero Coupon EOM Adjustment Flag
	 * @param couponPeriodList List of bond coupon periods
	 * @param workoutDate Work-out Date
	 * @param valuationDate Value Date
	 * @param cashPayDate Cash-Pay Date
	 * @param discountCurve Underlying Discount Curve
	 * @param bump DC Bump
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * @param segmentCustomBuilderControl Segment Custom Builder Control Parameters
	 * 
	 * @return The Derived Zero Rate Instance
	 */

	public static final DerivedZeroRate FromBaseCurve (
		final int frequency,
		final String dayCount,
		final String calendar,
		final boolean applyEOMAdjustment,
		final List<CompositePeriod> couponPeriodList,
		final int workoutDate,
		final int valuationDate,
		final int cashPayDate,
		final DiscountCurve discountCurve,
		final double bump,
		final ValuationCustomizationParams valuationCustomizationParams,
		final SegmentCustomBuilderControl segmentCustomBuilderControl)
	{
		return null == discountCurve ? null : discountCurve instanceof GovvieCurve ? FromGovvieCurve (
			frequency,
			dayCount,
			calendar,
			applyEOMAdjustment,
			couponPeriodList,
			workoutDate,
			valuationDate,
			cashPayDate,
			(GovvieCurve) discountCurve,
			bump,
			valuationCustomizationParams,
			segmentCustomBuilderControl
		) : FromDiscountCurve (
			frequency,
			dayCount,
			calendar,
			applyEOMAdjustment,
			couponPeriodList,
			workoutDate,
			valuationDate,
			cashPayDate,
			discountCurve,
			bump,
			valuationCustomizationParams,
			segmentCustomBuilderControl
		);
	}

	private DerivedZeroRate (
		final DiscountCurve discountCurve,
		final MultiSegmentSequence discountFactorMultiSegmentSequence,
		final MultiSegmentSequence zeroRateMultiSegmentSequence)
		throws Exception
	{
		super (discountCurve.epoch().julian(), discountCurve.currency());

		if (null == (_discountFactorMultiSegmentSequence = discountFactorMultiSegmentSequence) ||
			null == (_zeroRateMultiSegmentSequence = zeroRateMultiSegmentSequence)) {
			throw new Exception ("DerivedZeroRate Constructor: Invalid Inputs");
		}

		_discountCurve = discountCurve;
	}

	@Override public double df (
		final int date)
		throws Exception
	{
		return date <= epoch().julian() ? 1. : _discountFactorMultiSegmentSequence.responseValue (date);
	}

	@Override public double df (
		final String tenor)
		throws Exception
	{
		return df (epoch().addTenor (tenor));
	}

	@Override public double zeroRate (
		final int date)
		throws Exception
	{
		return date <= epoch().julian() ? 1. : _zeroRateMultiSegmentSequence.responseValue (date);
	}

	@Override public CaseInsensitiveTreeMap<Double> manifestMeasure (
		final String instrument)
	{
		return _discountCurve.manifestMeasure (instrument);
	}

	@Override public CalibratableComponent[] calibComp()
	{
		return _discountCurve.calibComp();
	}

	@Override public LatentStateLabel label()
	{
		return _discountCurve.label();
	}

	@Override public Curve parallelShiftManifestMeasure (
		final String manifestMeasure,
		final double shift)
	{
		return null;
	}

	@Override public org.drip.analytics.definition.Curve shiftManifestMeasure (
		final int spanIndex,
		final String manifestMeasure,
		final double shift)
	{
		return null;
	}

	@Override public org.drip.analytics.definition.Curve customTweakManifestMeasure (
		final String manifestMeasure,
		final ManifestMeasureTweak manifestMeasureTweak)
	{
		return null;
	}

	@Override public boolean setCCIS (
		final CurveConstructionInputSet curveConstructionInputSet)
	{
		 return _discountCurve.setCCIS (curveConstructionInputSet);
	}

	@Override public org.drip.state.representation.LatentState parallelShiftQuantificationMetric (
		final double shift)
	{
		return _discountCurve.parallelShiftQuantificationMetric (shift);
	}

	@Override public org.drip.state.representation.LatentState customTweakQuantificationMetric (
		final ManifestMeasureTweak manifestMeasureTweak)
	{
		return _discountCurve.customTweakQuantificationMetric (manifestMeasureTweak);
	}

	@Override public double df (
		final JulianDate date)
		throws Exception
	{
		if (null == date) {
			throw new Exception ("DerivedZeroRate::df => Invalid Inputs");
		}

		return df (date.julian());
	}

	@Override public double effectiveDF (
		final int date1,
		final int date2)
		throws Exception
	{
		if (date1 == date2) {
			return df (date1);
		}

		int quadratureCount = 0;
		double effectiveDiscountFactor = 0.;
		int quadratureWidth = (date2 - date1) / NUM_DF_QUADRATURES;

		if (0 == quadratureWidth) {
			quadratureWidth = 1;
		}

		for (int date = date1; date <= date2; date += quadratureWidth) {
			++quadratureCount;

			effectiveDiscountFactor += (df (date) + df (date + quadratureWidth));
		}

		return effectiveDiscountFactor / (2. * quadratureCount);
	}

	@Override public double effectiveDF (
		final JulianDate date1,
		final JulianDate date2)
		throws java.lang.Exception
	{
		if (null == date1 || null == date2) {
			throw new Exception ("DerivedZeroRate::effectiveDF => Got null for date");
		}

		return effectiveDF (date1.julian(), date2.julian());
	}

	@Override public double effectiveDF (
		final String tenor1,
		final String tenor2)
		throws Exception
	{
		if (null == tenor1 || tenor1.isEmpty() || null == tenor2 || tenor2.isEmpty()) {
			throw new Exception ("DerivedZeroRate::effectiveDF => Got bad tenor");
		}

		JulianDate startDate = epoch();

		return effectiveDF (startDate.addTenor (tenor1), startDate.addTenor (tenor2));
	}
}
