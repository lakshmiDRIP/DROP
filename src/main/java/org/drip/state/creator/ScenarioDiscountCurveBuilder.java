
package org.drip.state.creator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.definition.LatentStateStatic;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.analytics.support.CompositePeriodBuilder;
import org.drip.function.r1tor1.QuadraticRationalShapeControl;
import org.drip.market.otc.FixedFloatSwapConvention;
import org.drip.market.otc.IBORFixedFloatContainer;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.creator.MarketParamsBuilder;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.market.DiscountCurveScenarioContainer;
import org.drip.param.market.LatentStateFixingsContainer;
import org.drip.param.period.ComposableFloatingUnitSetting;
import org.drip.param.period.CompositePeriodSetting;
import org.drip.param.pricer.CreditPricerParams;
import org.drip.param.valuation.ValuationCustomizationParams;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.calib.ProductQuoteSet;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.rates.SingleStreamComponent;
import org.drip.spline.basis.ExponentialTensionSetParams;
import org.drip.spline.basis.FunctionSetBuilderParams;
import org.drip.spline.basis.KaklisPandelisSetParams;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.grid.OverlappingStretchSpan;
import org.drip.spline.grid.Span;
import org.drip.spline.params.ResponseScalingShapeControl;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.params.SegmentInelasticDesignControl;
import org.drip.spline.pchip.LocalControlStretchBuilder;
import org.drip.spline.pchip.LocalMonotoneCkGenerator;
import org.drip.spline.stretch.BoundarySettings;
import org.drip.spline.stretch.MultiSegmentSequence;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.boot.DiscountCurveScenario;
import org.drip.state.curve.DiscountFactorDiscountCurve;
import org.drip.state.curve.ZeroRateDiscountCurve;
import org.drip.state.discount.ExplicitBootDiscountCurve;
import org.drip.state.discount.MergedDiscountForwardCurve;
import org.drip.state.discount.TurnListDiscountFactor;
import org.drip.state.estimator.GlobalControlCurveParams;
import org.drip.state.estimator.LocalControlCurveParams;
import org.drip.state.identifier.ForwardLabel;
import org.drip.state.identifier.FundingLabel;
import org.drip.state.inference.LatentStateSegmentSpec;
import org.drip.state.inference.LatentStateStretchSpec;
import org.drip.state.inference.LinearLatentStateCalibrator;
import org.drip.state.nonlinear.FlatForwardDiscountCurve;
import org.drip.state.representation.LatentStateSpecification;

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
 * <i>ScenarioDiscountCurveBuilder</i> implements the the construction of the scenario discount curve using
 * the input discount curve instruments, and a wide variety of custom builds. It implements the following
 * functionality:
 *
 *  <ul>
 * 		<li>Create an DiscountCurveScenarioContainer Instance from the currency and the array of the calibration instruments</li>
 * 		<li>Create Discount Curve from the Calibration Instruments</li>
 * 		<li>Build the Shape Preserving Discount Curve using the Custom Parameters</li>
 * 		<li>Build a Globally Smoothed Instance of the Discount Curve using the Custom Parameters</li>
 * 		<li>Build a Locally Smoothed Instance of the Discount Curve using the Custom Parameters</li>
 * 		<li>Construct an instance of the Shape Preserver of the desired basis type, using the specified basis set builder parameters</li>
 * 		<li>Construct an instance of the Shape Preserver of the KLK Hyperbolic Tension Type, using the specified basis set builder parameters</li>
 * 		<li>Construct an instance of the Shape Preserver of the Cubic Polynomial Type, using the specified basis set builder parameters</li>
 * 		<li>Customizable DENSE Curve Creation Methodology</li>
 * 		<li>Standard DENSE Curve Creation Methodology</li>
 * 		<li>DUAL DENSE Curve Creation Methodology</li>
 * 		<li>Create an Instance of the Custom Splined DF Discount Curve</li>
 * 		<li>Create an Instance of the Cubic Polynomial Splined DF Discount Curve</li>
 * 		<li>Create an Instance of the Quartic Polynomial Splined DF Discount Curve</li>
 * 		<li>Create an Instance of the Kaklis-Pandelis Splined DF Discount Curve</li>
 * 		<li>Create an Instance of the KLK Hyperbolic Splined DF Discount Curve</li>
 * 		<li>Create an Instance of the KLK Exponential Splined DF Discount Curve</li>
 * 		<li>Create an Instance of the KLK Linear Rational Splined DF Discount Curve</li>
 * 		<li>Create an Instance of the KLK Quadratic Rational Splined DF Discount Curve</li>
 * 		<li>Build a Discount Curve from an array of discount factors</li>
 * 		<li>Create a Discount Curve from the Exponentially Compounded Flat Rate</li>
 * 		<li>Create a Discount Curve from the Discretely Compounded Flat Rate</li>
 * 		<li>Create a Discount Curve from the Flat Yield</li>
 * 		<li>Create a discount curve from an array of dates/rates</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/creator/README.md">Scenario State Curve/Surface Builders</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ScenarioDiscountCurveBuilder
{
	static class CompQuote
	{
		double _quote = Double.NaN;
		CalibratableComponent _calibratableComponent = null;
 
		CompQuote (
			final CalibratableComponent calibratableComponent,
			final double quote)
		{
			_quote = quote;
			_calibratableComponent = calibratableComponent;
		}
	}

	private static final boolean BLOG = false;

	private static final CompQuote[] CompQuote (
		final ValuationParams valuationParams,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer,
		final String currency,
		final JulianDate effectiveDate,
		final JulianDate initialMaturityDate,
		final JulianDate terminalMaturityDate,
		final String tenor,
		final boolean isIRS)
	{
		List<Double> calibrationQuoteList = new ArrayList<Double>();

		List<CalibratableComponent> denseCalibrationComponentList = new ArrayList<CalibratableComponent>();

		JulianDate maturityDate = initialMaturityDate;

		while (maturityDate.julian() <= terminalMaturityDate.julian()) {
			try {
				CalibratableComponent calibratableComponent = null;

				if (isIRS) {
					String maturityTenor =
						((int) ((maturityDate.julian() - effectiveDate.julian()) * 12 / 365.25)) + "M";

					FixedFloatSwapConvention fixedFloatSwapConvention =
						IBORFixedFloatContainer.ConventionFromJurisdiction (
							currency,
							"ALL",
							maturityTenor,
							"MAIN"
						);

					if (null == fixedFloatSwapConvention) {
						return null;
					}

					calibratableComponent = fixedFloatSwapConvention.createFixFloatComponent (
						effectiveDate,
						maturityTenor,
						0.,
						0.,
						1.
					);
				} else {
					calibratableComponent = new SingleStreamComponent (
						"DEPOSIT_" + maturityDate,
						new org.drip.product.rates.Stream (
							CompositePeriodBuilder.FloatingCompositeUnit (
								CompositePeriodBuilder.EdgePair (effectiveDate, maturityDate),
								new CompositePeriodSetting (
									4,
									"3M",
									currency,
									null,
									1.,
									null,
									null,
									null,
									null
								),
								new ComposableFloatingUnitSetting (
									"3M",
									CompositePeriodBuilder.EDGE_DATE_SEQUENCE_SINGLE,
									null,
									ForwardLabel.Standard (currency + "-3M"),
									CompositePeriodBuilder.REFERENCE_PERIOD_IN_ADVANCE,
									0.
								)
							)
						),
						null
					);
				}

				denseCalibrationComponentList.add (calibratableComponent);

				calibrationQuoteList.add (
					calibratableComponent.measureValue (
						valuationParams,
						null,
						curveSurfaceQuoteContainer,
						null,
						"Rate"
					)
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			if (null == (maturityDate = maturityDate.addTenorAndAdjust (tenor, currency))) {
				return null;
			}
		}

		int denseComponentCount = denseCalibrationComponentList.size();

		if (0 == denseComponentCount) {
			return null;
		}

		CompQuote[] compQuoteArray = new CompQuote[denseComponentCount];

		for (int denseComponentIndex = 0; denseComponentIndex < denseComponentCount; ++denseComponentIndex) {
			compQuoteArray[denseComponentIndex] = new CompQuote (
				denseCalibrationComponentList.get (denseComponentIndex),
				calibrationQuoteList.get (denseComponentIndex)
			);
		}

		return compQuoteArray;
	}

	/**
	 * Create an DiscountCurveScenarioContainer Instance from the currency and the array of the calibration
	 * 	instruments
	 * 
	 * @param currency Currency
	 * @param calibratableComponentArray Array of the calibration instruments
	 * 
	 * @return The DiscountCurveScenarioContainer instance
	 */

	public static final DiscountCurveScenarioContainer FromIRCSG (
		final String currency,
		final CalibratableComponent[] calibratableComponentArray)
	{
		try {
			return new DiscountCurveScenarioContainer (calibratableComponentArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create Discount Curve from the Calibration Instruments
	 * 
	 * @param date Valuation Date
	 * @param currency Currency
	 * @param calibrationInstrumentArray Input Calibration Instruments
	 * @param calibrationQuoteArray Input Calibration Quotes
	 * @param calibrationMeasureArray Input Calibration Measures
	 * @param latentStateFixingsContainer Latent State Fixings Container
	 * 
	 * @return The Calibrated Discount Curve
	 */

	public static final MergedDiscountForwardCurve NonlinearBuild (
		final JulianDate date,
		final String currency,
		final CalibratableComponent[] calibrationInstrumentArray,
		final double[] calibrationQuoteArray,
		final String[] calibrationMeasureArray,
		final LatentStateFixingsContainer latentStateFixingsContainer)
	{
		return null == date ? null : DiscountCurveScenario.Standard (
			ValuationParams.Spot (date.julian()),
			calibrationInstrumentArray,
			calibrationQuoteArray,
			calibrationMeasureArray,
			0.,
			null,
			latentStateFixingsContainer,
			null
		);
	}

	/**
	 * Build the Shape Preserving Discount Curve using the Custom Parameters
	 * 
	 * @param currency Currency
	 * @param linearLatentStateCalibrator The Linear Latent State Calibrator Instance
	 * @param latentStateStretchSpecArray Array of the Instrument Representation Stretches
	 * @param valuationParams Valuation Parameters
	 * @param creditPricerParams Pricer Parameters
	 * @param curveSurfaceQuoteContainer Market Parameters
	 * @param valuationCustomizationParams Quoting Parameters
	 * @param epochResponse The Starting Response Value
	 * 
	 * @return Instance of the Shape Preserving Discount Curve
	 */

	public static final MergedDiscountForwardCurve ShapePreservingDFBuild (
		final String currency,
		final LinearLatentStateCalibrator linearLatentStateCalibrator,
		final LatentStateStretchSpec[] latentStateStretchSpecArray,
		final ValuationParams valuationParams,
		final CreditPricerParams creditPricerParams,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer,
		final ValuationCustomizationParams valuationCustomizationParams,
		final double epochResponse)
	{
		if (null == linearLatentStateCalibrator) {
			return null;
		}

		try {
			Span discountFactorSpan = linearLatentStateCalibrator.calibrateSpan (
				latentStateStretchSpecArray,
				epochResponse,
				valuationParams,
				creditPricerParams,
				valuationCustomizationParams,
				curveSurfaceQuoteContainer
			);

			if (null == discountFactorSpan) {
				return null;
			}

			DiscountFactorDiscountCurve discountFactorDiscountCurve = new DiscountFactorDiscountCurve (
				currency,
				discountFactorSpan
			);

			return discountFactorDiscountCurve.setCCIS (
				new org.drip.analytics.input.LatentStateShapePreservingCCIS (
					linearLatentStateCalibrator,
					latentStateStretchSpecArray,
					valuationParams,
					creditPricerParams,
					valuationCustomizationParams,
					curveSurfaceQuoteContainer
				)
			) ? discountFactorDiscountCurve : null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Build a Globally Smoothed Instance of the Discount Curve using the Custom Parameters
	 * 
	 * @param shapePreserverDiscountCurve Instance of the Shape Preserving Discount Curve
	 * @param linearLatentStateCalibrator The Linear Latent State Calibrator Instance
	 * @param globalControlCurveParams Global Smoothing Curve Control Parameters
	 * @param valuationParams Valuation Parameters
	 * @param creditPricerParams Pricer Parameters
	 * @param curveSurfaceQuoteContainer Market Parameters
	 * @param valuationCustomizationParams Quoting Parameters
	 * 
	 * @return Globally Smoothed Instance of the Discount Curve
	 */

	public static final MergedDiscountForwardCurve SmoothingGlobalControlBuild (
		final MergedDiscountForwardCurve shapePreserverDiscountCurve,
		final LinearLatentStateCalibrator linearLatentStateCalibrator,
		final GlobalControlCurveParams globalControlCurveParams,
		final ValuationParams valuationParams,
		final CreditPricerParams creditPricerParams,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer,
		final ValuationCustomizationParams valuationCustomizationParams)
	{
		if (null == shapePreserverDiscountCurve) {
			return null;
		}

		if (null == globalControlCurveParams) {
			return shapePreserverDiscountCurve;
		}

		String smootheningQuantificationMetric = globalControlCurveParams.smootheningQuantificationMetric();

		Map<Integer, Double> canonicalTruthnessMap = shapePreserverDiscountCurve.canonicalTruthness
			(smootheningQuantificationMetric);

		if (null == canonicalTruthnessMap) {
			return null;
		}

		int truthnessSize = canonicalTruthnessMap.size();

		if (0 == truthnessSize) {
			return null;
		}

		Set<Map.Entry<Integer, Double>> canonicalTruthnessEntrySet = canonicalTruthnessMap.entrySet();

		if (null == canonicalTruthnessEntrySet || 0 == canonicalTruthnessEntrySet.size()) {
			return null;
		}

		String name = shapePreserverDiscountCurve.label().fullyQualifiedName();

		int i = 0;
		int[] dateArray = new int[truthnessSize];
		double[] quantificationMetricArray = new double[truthnessSize];
		SegmentCustomBuilderControl[] segmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[truthnessSize - 1];

		for (Map.Entry<Integer, Double> canonicalTruthnessEntry : canonicalTruthnessEntrySet) {
			if (null == canonicalTruthnessEntry) {
				return null;
			}

			if (0 != i) {
				segmentCustomBuilderControlArray[i - 1] =
					globalControlCurveParams.defaultSegmentBuilderControl();
			}

			dateArray[i] = canonicalTruthnessEntry.getKey();

			quantificationMetricArray[i++] = canonicalTruthnessEntry.getValue();

			if (BLOG) {
				System.out.println (
					"\t\t" + new JulianDate (canonicalTruthnessEntry.getKey()) + " = " +
						canonicalTruthnessEntry.getValue()
				);
			}
		}

		try {
			MultiSegmentSequence multiSegmentSequence =
				MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
					name + "_STRETCH",
					dateArray,
					quantificationMetricArray,
					segmentCustomBuilderControlArray,
					globalControlCurveParams.bestFitWeightedResponse(),
					globalControlCurveParams.calibrationBoundaryCondition(),
					globalControlCurveParams.calibrationDetail()
				);

			MergedDiscountForwardCurve multiPassMergedDiscountForwardCurve = null;

			if (LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR.equalsIgnoreCase
				(smootheningQuantificationMetric)) {
				multiPassMergedDiscountForwardCurve = new DiscountFactorDiscountCurve (
					name,
					new OverlappingStretchSpan (multiSegmentSequence)
				);
			} else if (LatentStateStatic.DISCOUNT_QM_ZERO_RATE.equalsIgnoreCase
				(smootheningQuantificationMetric)) {
				multiPassMergedDiscountForwardCurve = new ZeroRateDiscountCurve (
					name,
					new OverlappingStretchSpan (multiSegmentSequence)
				);
			}

			return multiPassMergedDiscountForwardCurve;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Build a Locally Smoothed Instance of the Discount Curve using the Custom Parameters
	 * 
	 * @param shapePreserverDiscountCurve Instance of the Shape Preserving Discount Curve
	 * @param linearLatentStateCalibrator The Linear Latent State Calibrator Instance
	 * @param localControlCurveParams Local Smoothing Curve Control Parameters
	 * @param valuationParams Valuation Parameters
	 * @param creditPricerParams Pricer Parameters
	 * @param curveSurfaceQuoteContainer Market Parameters
	 * @param valuationCustomizationParams Quoting Parameters
	 * 
	 * @return Locally Smoothed Instance of the Discount Curve
	 */

	public static final MergedDiscountForwardCurve SmoothingLocalControlBuild (
		final MergedDiscountForwardCurve shapePreserverDiscountCurve,
		final LinearLatentStateCalibrator linearLatentStateCalibrator,
		final LocalControlCurveParams localControlCurveParams,
		final ValuationParams valuationParams,
		final CreditPricerParams creditPricerParams,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer,
		final ValuationCustomizationParams valuationCustomizationParams)
	{
		if (null == shapePreserverDiscountCurve) {
			return null;
		}

		if (null == localControlCurveParams) {
			return shapePreserverDiscountCurve;
		}

		String smootheningQuantificationMetric = localControlCurveParams.smootheningQuantificationMetric();

		Map<Integer, Double> canonicalTruthnessMap = shapePreserverDiscountCurve.canonicalTruthness
			(smootheningQuantificationMetric);

		if (null == canonicalTruthnessMap) {
			return null;
		}

		int truthnessSize = canonicalTruthnessMap.size();

		if (0 == truthnessSize) {
			return null;
		}

		Set<Map.Entry<Integer, Double>> canonicalTruthnessMapEntrySet = canonicalTruthnessMap.entrySet();

		if (null == canonicalTruthnessMapEntrySet || 0 == canonicalTruthnessMapEntrySet.size()) {
			return null;
		}

		String name = shapePreserverDiscountCurve.label().fullyQualifiedName();

		int i = 0;
		int[] dateArray = new int[truthnessSize];
		double[] quantificationMetricArray = new double[truthnessSize];
		SegmentCustomBuilderControl[] segmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[truthnessSize - 1];

		for (Map.Entry<Integer, Double> canonicalTruthnessMapEntry : canonicalTruthnessMapEntrySet) {
			if (null == canonicalTruthnessMapEntry) {
				return null;
			}

			if (0 != i) {
				segmentCustomBuilderControlArray[i - 1] =
					localControlCurveParams.defaultSegmentBuilderControl();
			}

			dateArray[i] = canonicalTruthnessMapEntry.getKey();

			quantificationMetricArray[i++] = canonicalTruthnessMapEntry.getValue();

			if (BLOG) {
				System.out.println (
					"\t\t" + new JulianDate (canonicalTruthnessMapEntry.getKey()) + " = " +
						canonicalTruthnessMapEntry.getValue());
			}
		}

		try {
			LocalMonotoneCkGenerator localMonotoneCkGenerator = LocalMonotoneCkGenerator.Create (
				dateArray,
				quantificationMetricArray,
				localControlCurveParams.C1GeneratorScheme(),
				localControlCurveParams.eliminateSpuriousExtrema(),
				localControlCurveParams.applyMonotoneFilter()
			);

			if (null == localMonotoneCkGenerator) {
				return null;
			}

			MultiSegmentSequence multiSegmentSequence = LocalControlStretchBuilder.CustomSlopeHermiteSpline (
				name + "_STRETCH",
				dateArray,
				quantificationMetricArray,
				localMonotoneCkGenerator.C1(),
				segmentCustomBuilderControlArray,
				localControlCurveParams.bestFitWeightedResponse(),
				localControlCurveParams.calibrationDetail()
			);

			MergedDiscountForwardCurve multiPassDiscountCurve = null;

			if (LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR.equalsIgnoreCase
				(smootheningQuantificationMetric)) {
				multiPassDiscountCurve = new DiscountFactorDiscountCurve (
					name,
					new OverlappingStretchSpan (multiSegmentSequence)
				);
			} else if (LatentStateStatic.DISCOUNT_QM_ZERO_RATE.equalsIgnoreCase
				(smootheningQuantificationMetric)) {
				multiPassDiscountCurve = new ZeroRateDiscountCurve (
					name,
					new OverlappingStretchSpan (multiSegmentSequence)
				);
			}

			return multiPassDiscountCurve;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an instance of the Shape Preserver of the desired basis type, using the specified basis set
	 * 	builder parameters.
	 * 
	 * @param name Curve Name
	 * @param valuationParams Valuation Parameters
	 * @param creditPricerParams Pricer Parameters
	 * @param curveSurfaceQuoteContainer Market Parameters
	 * @param valuationCustomizationParams Quoting Parameters
	 * @param basisType The Basis Type
	 * @param functionSetBuilderParams The Function Set Basis Parameters
	 * @param calibrationComponentArray1 Array of Calibration Components #1
	 * @param calibrationQuoteArray1 Array of Calibration Quotes #1
	 * @param manifestMeasureArray1 Array of Manifest Measures for component Array #1
	 * @param calibrationComponentArray2 Array of Calibration Components #2
	 * @param calibrationQuoteArray2 Array of Calibration Quotes #2
	 * @param manifestMeasureArray2 Array of Manifest Measures for component Array #2
	 * @param epochResponse The Stretch Start DF
	 * @param zeroSmooth TRUE - Turn on the Zero Rate Smoothing
	 * 
	 * @return Instance of the Shape Preserver of the desired basis type
	 */

	public static final MergedDiscountForwardCurve DFRateShapePreserver (
		final String name,
		final ValuationParams valuationParams,
		final CreditPricerParams creditPricerParams,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer,
		final ValuationCustomizationParams valuationCustomizationParams,
		final String basisType,
		final FunctionSetBuilderParams functionSetBuilderParams,
		final CalibratableComponent[] calibrationComponentArray1,
		final double[] calibrationQuoteArray1,
		final String[] manifestMeasureArray1,
		final CalibratableComponent[] calibrationComponentArray2,
		final double[] calibrationQuoteArray2,
		final String[] manifestMeasureArray2,
		final double epochResponse,
		final boolean zeroSmooth)
	{
		if (null == name || name.isEmpty() || null == basisType || basisType.isEmpty() ||
			null == valuationParams || null == functionSetBuilderParams) {
			return null;
		}

		LatentStateStretchSpec latentStateStretchSpec1 = null;
		LatentStateStretchSpec latentStateStretchSpec2 = null;
		LocalControlCurveParams localControlCurveParams = null;
		CaseInsensitiveTreeMap<ForwardLabel> forwardLabelMap = null;
		LatentStateStretchSpec[] latentStateStretchSpecArray = null;
		LinearLatentStateCalibrator linearLatentStateCalibrator = null;
		MergedDiscountForwardCurve shapePreservingDiscountCurve = null;
		LatentStateSpecification fundingLatentStateSpecification = null;
		LatentStateSpecification[] latentStateSpecificationArray = null;
		int manifestMeasureCount1 = null == manifestMeasureArray1 ? 0 : manifestMeasureArray1.length;
		int manifestMeasureCount2 = null == manifestMeasureArray2 ? 0 : manifestMeasureArray2.length;
		int componentQuoteCount1 = null == calibrationQuoteArray1 ? 0 : calibrationQuoteArray1.length;
		int componentQuoteCount2 = null == calibrationQuoteArray2 ? 0 : calibrationQuoteArray2.length;
		int componentCount1 = null == calibrationComponentArray1 ? 0 : calibrationComponentArray1.length;
		int componentCount2 = null == calibrationComponentArray2 ? 0 : calibrationComponentArray2.length;

		if ((0 == componentCount1 && 0 == componentCount2) || componentCount1 != componentQuoteCount1 ||
			componentCount2 != componentQuoteCount2 || componentCount1 != manifestMeasureCount1 ||
			componentCount2 != manifestMeasureCount2) {
			return null;
		}

		String currency = (
			0 == componentCount1 ? calibrationComponentArray2 : calibrationComponentArray1
		)[0].payCurrency();

		try {
			fundingLatentStateSpecification = new LatentStateSpecification (
				LatentStateStatic.LATENT_STATE_FUNDING,
				LatentStateStatic.DISCOUNT_QM_DISCOUNT_FACTOR,
				FundingLabel.Standard (currency)
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		if (0 != componentCount1) {
			forwardLabelMap = calibrationComponentArray1[0].forwardLabel();
		}

		if (null == forwardLabelMap && 0 != componentCount2) {
			forwardLabelMap = calibrationComponentArray2[0].forwardLabel();
		}

		if (null == forwardLabelMap || 0 == forwardLabelMap.size())
			latentStateSpecificationArray = new LatentStateSpecification[] {fundingLatentStateSpecification};
		else {
			try {
				latentStateSpecificationArray = new LatentStateSpecification[] {
					fundingLatentStateSpecification,
					new LatentStateSpecification (
						LatentStateStatic.LATENT_STATE_FORWARD,
						LatentStateStatic.FORWARD_QM_FORWARD_RATE,
						forwardLabelMap.get ("DERIVED")
					)
				};
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		if (0 != componentCount1) {
			LatentStateSegmentSpec[] latentStateSegmentSpecArray =
				new LatentStateSegmentSpec[componentCount1];

			try {
				for (int componentIndex1 = 0; componentIndex1 < componentCount1; ++componentIndex1) {
					ProductQuoteSet productQuoteSet =
						calibrationComponentArray1[componentIndex1].calibQuoteSet
							(latentStateSpecificationArray);

					if (null == productQuoteSet ||
						!productQuoteSet.set (
							manifestMeasureArray1[componentIndex1],
							calibrationQuoteArray1[componentIndex1]
						)
					) {
						return null;
					}

					latentStateSegmentSpecArray[componentIndex1] = new LatentStateSegmentSpec (
						calibrationComponentArray1[componentIndex1],
						productQuoteSet
					);
				}

				latentStateStretchSpec1 = new LatentStateStretchSpec (
					name + "_COMP1",
					latentStateSegmentSpecArray
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		if (0 != componentCount2) {
			LatentStateSegmentSpec[] latentStateSegmentSpecArray =
				new LatentStateSegmentSpec[componentCount2];

			try {
				for (int componentIndex2 = 0; componentIndex2 < componentCount2; ++componentIndex2) {
					ProductQuoteSet productQuoteSet =
						calibrationComponentArray2[componentIndex2].calibQuoteSet
							(latentStateSpecificationArray);

					if (null == productQuoteSet || !productQuoteSet.set (
						manifestMeasureArray2[componentIndex2],
						calibrationQuoteArray2[componentIndex2]
					)) {
						return null;
					}

					latentStateSegmentSpecArray[componentIndex2] = new LatentStateSegmentSpec (
						calibrationComponentArray2[componentIndex2],
						productQuoteSet
					);
				}

				latentStateStretchSpec2 = new LatentStateStretchSpec (
					name + "_COMP2",
					latentStateSegmentSpecArray
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		if (null == latentStateStretchSpec1 && null == latentStateStretchSpec2) {
			return null;
		}

		if (null == latentStateStretchSpec1) {
			latentStateStretchSpecArray = new LatentStateStretchSpec[] {latentStateStretchSpec2};
		} else if (null == latentStateStretchSpec2) {
			latentStateStretchSpecArray = new LatentStateStretchSpec[] {latentStateStretchSpec1};
		} else {
			latentStateStretchSpecArray = new LatentStateStretchSpec[] {
				latentStateStretchSpec1,
				latentStateStretchSpec2
			};
		}

		try {
			linearLatentStateCalibrator = new LinearLatentStateCalibrator (
				new SegmentCustomBuilderControl (
					basisType,
					functionSetBuilderParams,
					SegmentInelasticDesignControl.Create (2, 2),
					new ResponseScalingShapeControl (true, new QuadraticRationalShapeControl (0.)),
					null
				),
				BoundarySettings.NaturalStandard(),
				MultiSegmentSequence.CALIBRATE,
				null,
				null
			);

			shapePreservingDiscountCurve = ShapePreservingDFBuild (
				currency,
				linearLatentStateCalibrator,
				latentStateStretchSpecArray,
				valuationParams,
				creditPricerParams,
				curveSurfaceQuoteContainer,
				valuationCustomizationParams,
				epochResponse
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		if (!zeroSmooth) {
			return shapePreservingDiscountCurve;
		}

		try {
			localControlCurveParams = new LocalControlCurveParams (
				LocalMonotoneCkGenerator.C1_HYMAN83,
				LatentStateStatic.DISCOUNT_QM_ZERO_RATE,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (4),
					SegmentInelasticDesignControl.Create (2, 2),
					new ResponseScalingShapeControl (true, new QuadraticRationalShapeControl (0.)),
					null
				),
				MultiSegmentSequence.CALIBRATE,
				null,
				null,
				true,
				true
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return SmoothingLocalControlBuild (
			shapePreservingDiscountCurve,
			linearLatentStateCalibrator,
			localControlCurveParams,
			valuationParams,
			null,
			null,
			null
		);
	}

	/**
	 * Construct an instance of the Shape Preserver of the KLK Hyperbolic Tension Type, using the specified
	 *  basis set builder parameters.
	 * 
	 * @param name Curve Name
	 * @param valuationParams Valuation Parameters
	 * @param calibrationComponentArray1 Array of Calibration Components #1
	 * @param calibrationQuoteArray1 Array of Calibration Quotes #1
	 * @param manifestMeasureArray1 Array of Manifest Measures for component Array #1
	 * @param calibrationComponentArray2 Array of Calibration Components #2
	 * @param calibrationQuoteArray2 Array of Calibration Quotes #2
	 * @param manifestMeasureArray2 Array of Manifest Measures for component Array #2
	 * @param zeroSmooth TRUE - Turn on the Zero Rate Smoothing
	 * 
	 * @return Instance of the Shape Preserver of the desired basis type
	 */

	public static final MergedDiscountForwardCurve CubicKLKHyperbolicDFRateShapePreserver (
		final String name,
		final ValuationParams valuationParams,
		final CalibratableComponent[] calibrationComponentArray1,
		final double[] calibrationQuoteArray1,
		final String[] manifestMeasureArray1,
		final CalibratableComponent[] calibrationComponentArray2,
		final double[] calibrationQuoteArray2,
		final String[] manifestMeasureArray2,
		final boolean zeroSmooth)
	{
		try {
			return DFRateShapePreserver (
				name,
				valuationParams,
				null,
				null,
				null,
				MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION,
				new ExponentialTensionSetParams (1.),
				calibrationComponentArray1,
				calibrationQuoteArray1,
				manifestMeasureArray1,
				calibrationComponentArray2,
				calibrationQuoteArray2,
				manifestMeasureArray2,
				1.,
				zeroSmooth
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an instance of the Shape Preserver of the Cubic Polynomial Type, using the specified
	 *  basis set builder parameters.
	 * 
	 * @param name Curve Name
	 * @param valuationParams Valuation Parameters
	 * @param calibrationComponentArray1 Array of Calibration Components #1
	 * @param calibrationQuoteArray1 Array of Calibration Quotes #1
	 * @param manifestMeasureArray1 Array of Manifest Measures for component Array #1
	 * @param calibrationComponentArray2 Array of Calibration Components #2
	 * @param calibrationQuoteArray2 Array of Calibration Quotes #2
	 * @param manifestMeasureArray2 Array of Manifest Measures for component Array #2
	 * @param zeroSmooth TRUE - Turn on the Zero Rate Smoothing
	 * 
	 * @return Instance of the Shape Preserver of the desired basis type
	 */

	public static final MergedDiscountForwardCurve CubicPolyDFRateShapePreserver (
		final String name,
		final ValuationParams valuationParams,
		final CalibratableComponent[] calibrationComponentArray1,
		final double[] calibrationQuoteArray1,
		final String[] manifestMeasureArray1,
		final CalibratableComponent[] calibrationComponentArray2,
		final double[] calibrationQuoteArray2,
		final String[] manifestMeasureArray2,
		final boolean zeroSmooth)
	{
		try {
			return DFRateShapePreserver (
				name,
				valuationParams,
				null,
				null,
				null,
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (4),
				calibrationComponentArray1,
				calibrationQuoteArray1,
				manifestMeasureArray1,
				calibrationComponentArray2,
				calibrationQuoteArray2,
				manifestMeasureArray2,
				1.,
				zeroSmooth
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Customizable DENSE Curve Creation Methodology - the references are:
	 * 
	 *  - Sankar, L. (1997): OFUTS – An Alternative Yield Curve Interpolator F. A. S. T. Research
	 *  	Documentation Bear Sterns.
	 *  
	 *  - Nahum, E. (2004): Changes to Yield Curve Construction – Linear Stripping of the Short End of the
	 *  	Curve F. A. S. T. Research Documentation Bear Sterns.
	 *  
	 *  - Kinlay, J., and X. Bai (2009): Yield Curve Construction Models – Tools and Techniques 
	 *  	(http://www.jonathankinlay.com/Articles/Yield Curve Construction Models.pdf)
	 * 
	 * @param name Curve Name
	 * @param valuationParams Valuation Parameters
	 * @param calibrationComponentArray1 Array of Calibration Components #1
	 * @param calibrationQuoteArray1 Array of Calibration Quotes #1
	 * @param tenor1 Stretch #1 Instrument set re-construction Tenor
	 * @param manifestMeasureArray1 Array of Manifest Measures for component Array #1
	 * @param calibrationComponentArray2 Array of Calibration Components #2
	 * @param calibrationQuoteArray2 Array of Calibration Quotes #2
	 * @param tenor2 Stretch #2 Instrument set re-construction Tenor
	 * @param manifestMeasureArray2 Array of Manifest Measures for component Array #2
	 * @param turnListDiscountFactor The Turns List
	 * 
	 * @return The Customized DENSE Curve.
	 */

	public static final MergedDiscountForwardCurve CustomDENSE (
		final String name,
		final ValuationParams valuationParams,
		final CalibratableComponent[] calibrationComponentArray1,
		final double[] calibrationQuoteArray1,
		final String tenor1,
		final String[] manifestMeasureArray1,
		final CalibratableComponent[] calibrationComponentArray2,
		final double[] calibrationQuoteArray2,
		final String tenor2,
		final String[] manifestMeasureArray2,
		final TurnListDiscountFactor turnListDiscountFactor)
	{
		MergedDiscountForwardCurve shapePreserverDiscountFactor = CubicKLKHyperbolicDFRateShapePreserver (
			name,
			valuationParams,
			calibrationComponentArray1,
			calibrationQuoteArray1,
			manifestMeasureArray1,
			calibrationComponentArray2,
			calibrationQuoteArray2,
			manifestMeasureArray2,
			false
		);

		if (null == shapePreserverDiscountFactor || (
				null != turnListDiscountFactor &&
					!shapePreserverDiscountFactor.setTurns (turnListDiscountFactor)
			)
		) {
			return null;
		}

		CurveSurfaceQuoteContainer curveSurfaceQuoteContainer = MarketParamsBuilder.Create (
			shapePreserverDiscountFactor,
			null,
			null,
			null,
			null,
			null,
			null
		);

		if (null == curveSurfaceQuoteContainer) {
			return null;
		}

		CompQuote[] compQuoteArray1 = null;

		String currency = calibrationComponentArray1[0].payCurrency();

		if (null == tenor1 || tenor1.isEmpty()) {
			if (null != calibrationComponentArray1) {
				int componentCount1 = calibrationComponentArray1.length;

				if (0 != componentCount1) {
					compQuoteArray1 = new CompQuote[componentCount1];

					for (int componentIndex1 = 0; componentIndex1 < componentCount1; ++componentIndex1) {
						compQuoteArray1[componentIndex1] = new CompQuote (
							calibrationComponentArray1[componentIndex1],
							calibrationQuoteArray1[componentIndex1]
						);
					}
				}
			}
		} else {
			compQuoteArray1 = CompQuote (
				valuationParams,
				curveSurfaceQuoteContainer,
				currency,
				calibrationComponentArray1[0].effectiveDate(),
				calibrationComponentArray1[0].maturityDate(),
				calibrationComponentArray1[calibrationComponentArray1.length - 1].maturityDate(),
				tenor1,
				false
			);
		}

		if (null == tenor2 || tenor2.isEmpty()) {
			return shapePreserverDiscountFactor;
		}

		CompQuote[] compQuoteArray2 = CompQuote (
			valuationParams,
			curveSurfaceQuoteContainer,
			currency,
			calibrationComponentArray2[0].effectiveDate(),
			calibrationComponentArray2[0].maturityDate(),
			calibrationComponentArray2[calibrationComponentArray2.length - 1].maturityDate(),
			tenor2,
			true
		);

		int denseComponentCount2 = null == compQuoteArray2 ? 0 : compQuoteArray2.length;
		int denseComponentCount1 = null == compQuoteArray1 ? 0 : compQuoteArray1.length;
		int totalDENSEComponentCount = denseComponentCount1 + denseComponentCount2;

		if (0 == totalDENSEComponentCount) {
			return null;
		}

		double[] calibrationQuoteArray = new double[totalDENSEComponentCount];
		String[] calibrationMeasureArray = new String[totalDENSEComponentCount];
		CalibratableComponent[] calibratableComponentArray =
			new CalibratableComponent[totalDENSEComponentCount];

		for (int denseComponentIndex = 0; denseComponentIndex < denseComponentCount1; ++denseComponentIndex)
		{
			calibrationMeasureArray[denseComponentIndex] = "Rate";
			calibrationQuoteArray[denseComponentIndex] = compQuoteArray1[denseComponentIndex]._quote;
			calibratableComponentArray[denseComponentIndex] =
				compQuoteArray1[denseComponentIndex]._calibratableComponent;
		}

		for (int denseComponentIndex = denseComponentCount1; denseComponentIndex < totalDENSEComponentCount;
			++denseComponentIndex) {
			calibrationMeasureArray[denseComponentIndex] = "Rate";
			calibrationQuoteArray[denseComponentIndex] =
				compQuoteArray2[denseComponentIndex - denseComponentCount1]._quote;
			calibratableComponentArray[denseComponentIndex] =
				compQuoteArray2[denseComponentIndex - denseComponentCount1]._calibratableComponent;
		}

		try {
			return ScenarioDiscountCurveBuilder.NonlinearBuild (
				new JulianDate (valuationParams.valueDate()),
				currency,
				calibratableComponentArray,
				calibrationQuoteArray,
				calibrationMeasureArray,
				null
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * The Standard DENSE Curve Creation Methodology - this uses no re-construction set for the short term,
	 * 	and uses 3M dense re-construction for the Swap Set. The references are:
	 * 
	 *  - Sankar, L. (1997): OFUTS – An Alternative Yield Curve Interpolator F. A. S. T. Research
	 *  	Documentation Bear Sterns.
	 *  
	 *  - Nahum, E. (2004): Changes to Yield Curve Construction – Linear Stripping of the Short End of the
	 *  	Curve F. A. S. T. Research Documentation Bear Sterns.
	 *  
	 *  - Kinlay, J., and X. Bai (2009): Yield Curve Construction Models – Tools and Techniques 
	 *  	(http://www.jonathankinlay.com/Articles/Yield Curve Construction Models.pdf)
	 * 
	 * @param name Curve Name
	 * @param valuationParams Valuation Parameters
	 * @param calibrationComponentArray1 Array of Calibration Components #1
	 * @param calibrationQuoteArray1 Array of Calibration Quotes #1
	 * @param manifestMeasureArray1 Array of Manifest Measures for component Array #1
	 * @param calibrationComponentArray2 Array of Calibration Components #2
	 * @param calibrationQuoteArray2 Array of Calibration Quotes #2
	 * @param manifestMeasureArray2 Array of Manifest Measures for component Array #2
	 * @param turnListDiscountFactor The Turns List
	 * 
	 * @return The Customized DENSE Curve.
	 */

	public static final MergedDiscountForwardCurve DENSE (
		final String name,
		final ValuationParams valuationParams,
		final CalibratableComponent[] calibrationComponentArray1,
		final double[] calibrationQuoteArray1,
		final String[] manifestMeasureArray1,
		final CalibratableComponent[] calibrationComponentArray2,
		final double[] calibrationQuoteArray2,
		final String[] manifestMeasureArray2,
		final TurnListDiscountFactor turnListDiscountFactor)
	{
		return CustomDENSE (
			name,
			valuationParams,
			calibrationComponentArray1,
			calibrationQuoteArray1,
			null,
			manifestMeasureArray1,
			calibrationComponentArray2,
			calibrationQuoteArray2,
			"3M",
			manifestMeasureArray2,
			turnListDiscountFactor
		);
	}

	/**
	 * The DUAL DENSE Curve Creation Methodology - this uses configurable re-construction set for the short
	 *  term, and another configurable re-construction for the Swap Set. 1D re-construction tenor for the
	 *  short end will result in CDF (Constant Daily Forward) Discount Curve. The references are:
	 * 
	 *  - Sankar, L. (1997): OFUTS – An Alternative Yield Curve Interpolator F. A. S. T. Research
	 *  	Documentation Bear Sterns.
	 *  
	 *  - Nahum, E. (2004): Changes to Yield Curve Construction – Linear Stripping of the Short End of the
	 *  	Curve F. A. S. T. Research Documentation Bear Sterns.
	 *  
	 *  - Kinlay, J., and X. Bai (2009): Yield Curve Construction Models – Tools and Techniques 
	 *  	(http://www.jonathankinlay.com/Articles/Yield Curve Construction Models.pdf)
	 * 
	 * @param name Curve Name
	 * @param valuationParams Valuation Parameters
	 * @param calibrationComponentArray1 Array of Calibration Components #1
	 * @param calibrationQuoteArray1 Array of Calibration Quotes #1
	 * @param tenor1 Stretch #1 Instrument set re-construction Tenor
	 * @param manifestMeasureArray1 Array of Manifest Measures for component Array #1
	 * @param calibrationComponentArray2 Array of Calibration Components #2
	 * @param calibrationQuoteArray2 Array of Calibration Quotes #2
	 * @param tenor2 Stretch #2 Instrument set re-construction Tenor
	 * @param manifestMeasureArray2 Array of Manifest Measures for component Array #2
	 * @param turnListDiscountFactor The Turns List
	 * 
	 * @return The Customized DENSE Curve.
	 */

	public static final MergedDiscountForwardCurve DUALDENSE (
		final String name,
		final ValuationParams valuationParams,
		final CalibratableComponent[] calibrationComponentArray1,
		final double[] calibrationQuoteArray1,
		final String tenor1,
		final String[] manifestMeasureArray1,
		final CalibratableComponent[] calibrationComponentArray2,
		final double[] calibrationQuoteArray2,
		final String tenor2,
		final String[] manifestMeasureArray2,
		final TurnListDiscountFactor turnListDiscountFactor)
	{
		return CustomDENSE (
			name,
			valuationParams,
			calibrationComponentArray1,
			calibrationQuoteArray1,
			tenor1,
			manifestMeasureArray1,
			calibrationComponentArray2,
			calibrationQuoteArray2,
			tenor2,
			manifestMeasureArray2,
			turnListDiscountFactor
		);
	}

	/**
	 * Create an Instance of the Custom Splined Discount Curve
	 * 
	 * @param name Curve Name
	 * @param startDate Tenor Start Date
	 * @param currency The Currency
	 * @param dateArray Array of Dates
	 * @param discountFactorArray Array of Discount Factors
	 * @param segmentCustomBuilderControl The Segment Custom Builder Control
	 * 
	 * @return The Instance of the Basis Curve
	 */

	public static final MergedDiscountForwardCurve CustomSplineDiscountCurve (
		final String name,
		final JulianDate startDate,
		final String currency,
		final int[] dateArray,
		final double[] discountFactorArray,
		final SegmentCustomBuilderControl segmentCustomBuilderControl)
	{
		if (null == dateArray || null == startDate) {
			return null;
		}

		int dateNodeCount = dateArray.length;

		if (0 == dateNodeCount) {
			return null;
		}

		double[] responseValueArray = new double[dateNodeCount + 1];
		double[] predictorOrdinateArray = new double[dateNodeCount + 1];
		SegmentCustomBuilderControl[] segmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[dateNodeCount];

		for (int dateNodeIndex = 0; dateNodeIndex <= dateNodeCount; ++dateNodeIndex) {
			predictorOrdinateArray[dateNodeIndex] =
				0 == dateNodeIndex ? startDate.julian() : dateArray[dateNodeIndex - 1];

			responseValueArray[dateNodeIndex] =
				0 == dateNodeIndex ? 1. : discountFactorArray[dateNodeIndex - 1];

			if (0 != dateNodeIndex) {
				segmentCustomBuilderControlArray[dateNodeIndex - 1] = segmentCustomBuilderControl;
			}
		}

		try {
			return new DiscountFactorDiscountCurve (
				currency,
				new OverlappingStretchSpan (
					MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
						name,
						predictorOrdinateArray,
						responseValueArray,
						segmentCustomBuilderControlArray,
						null,
						BoundarySettings.NaturalStandard(),
						MultiSegmentSequence.CALIBRATE
					)
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Cubic Polynomial Splined DF Discount Curve
	 * 
	 * @param name Curve Name
	 * @param startDate Tenor Start Date
	 * @param currency The Currency
	 * @param dateArray Array of Dates
	 * @param discountFactorArray Array of Discount Factors
	 * 
	 * @return The Instance of the Discount Curve
	 */

	public static final MergedDiscountForwardCurve CubicPolynomialDiscountCurve (
		final String name,
		final JulianDate startDate,
		final String currency,
		final int[] dateArray,
		final double[] discountFactorArray)
	{
		try {
			return CustomSplineDiscountCurve (
				name,
				startDate,
				currency,
				dateArray,
				discountFactorArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (4),
					SegmentInelasticDesignControl.Create (2, 2),
					null,
					null
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Quartic Polynomial Splined DF Discount Curve
	 * 
	 * @param name Curve Name
	 * @param startDate Tenor Start Date
	 * @param currency The Currency
	 * @param dateArray Array of Dates
	 * @param discountFactorArray Array of Discount Factors
	 * 
	 * @return The Instance of the Discount Curve
	 */

	public static final MergedDiscountForwardCurve QuarticPolynomialDiscountCurve (
		final String name,
		final JulianDate startDate,
		final String currency,
		final int[] dateArray,
		final double[] discountFactorArray)
	{
		try {
			return CustomSplineDiscountCurve (
				name,
				startDate,
				currency,
				dateArray,
				discountFactorArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (5),
					SegmentInelasticDesignControl.Create (2, 2),
					null,
					null
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Kaklis-Pandelis Splined DF Discount Curve
	 * 
	 * @param name Curve Name
	 * @param startDate Tenor Start Date
	 * @param currency The Currency
	 * @param dateArray Array of Dates
	 * @param discountFactorArray Array of Discount Factors
	 * 
	 * @return The Instance of the Discount Curve
	 */

	public static final MergedDiscountForwardCurve KaklisPandelisDiscountCurve (
		final String name,
		final JulianDate startDate,
		final String currency,
		final int[] dateArray,
		final double[] discountFactorArray)
	{
		try {
			return CustomSplineDiscountCurve (
				name,
				startDate,
				currency,
				dateArray,
				discountFactorArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KAKLIS_PANDELIS, new
					KaklisPandelisSetParams (2),
					SegmentInelasticDesignControl.Create (2, 2),
					null,
					null
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Hyperbolic Splined DF Discount Curve
	 * 
	 * @param name Curve Name
	 * @param startDate Tenor Start Date
	 * @param currency The Currency
	 * @param dateArray Array of Dates
	 * @param discountFactorArray Array of Discount Factors
	 * @param tension The Tension Parameter
	 * 
	 * @return The Instance of the Discount Curve
	 */

	public static final MergedDiscountForwardCurve KLKHyperbolicDiscountCurve (
		final String name,
		final JulianDate startDate,
		final String currency,
		final int[] dateArray,
		final double[] discountFactorArray,
		final double tension)
	{
		try {
			return CustomSplineDiscountCurve (
				name,
				startDate,
				currency,
				dateArray,
				discountFactorArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION,
					new ExponentialTensionSetParams (tension),
					SegmentInelasticDesignControl.Create (2, 2),
					null,
					null
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Exponential Splined DF Discount Curve
	 * 
	 * @param name Curve Name
	 * @param startDate Tenor Start Date
	 * @param currency The Currency
	 * @param dateArray Array of Dates
	 * @param discountFactorArray Array of Discount Factors
	 * @param tension The Tension Parameter
	 * 
	 * @return The Instance of the Discount Curve
	 */

	public static final MergedDiscountForwardCurve KLKExponentialDiscountCurve (
		final String name,
		final JulianDate startDate,
		final String currency,
		final int[] dateArray,
		final double[] discountFactorArray,
		final double tension)
	{
		try {
			return CustomSplineDiscountCurve (
				name,
				startDate,
				currency,
				dateArray,
				discountFactorArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_EXPONENTIAL_TENSION,
					new ExponentialTensionSetParams (tension),
					SegmentInelasticDesignControl.Create (2, 2),
					null,
					null
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Linear Rational Splined DF Discount Curve
	 * 
	 * @param name Curve Name
	 * @param startDate Tenor Start Date
	 * @param currency The Currency
	 * @param dateArray Array of Dates
	 * @param discountFactorArray Array of Discount Factors
	 * @param tension The Tension Parameter
	 * 
	 * @return The Instance of the Discount Curve
	 */

	public static final MergedDiscountForwardCurve KLKRationalLinearDiscountCurve (
		final String name,
		final JulianDate startDate,
		final String currency,
		final int[] dateArray,
		final double[] discountFactorArray,
		final double tension)
	{
		try {
			return CustomSplineDiscountCurve (
				name,
				startDate,
				currency,
				dateArray,
				discountFactorArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION,
					new ExponentialTensionSetParams (tension),
					SegmentInelasticDesignControl.Create (2, 2),
					null,
					null
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Quadratic Rational Splined DF Discount Curve
	 * 
	 * @param name Curve Name
	 * @param startDate Tenor Start Date
	 * @param currency The Currency
	 * @param dateArray Array of Dates
	 * @param discountFactorArray Array of Discount Factors
	 * @param tension The Tension Parameter
	 * 
	 * @return The Instance of the Discount Curve
	 */

	public static final MergedDiscountForwardCurve KLKRationalQuadraticDiscountCurve (
		final String name,
		final JulianDate startDate,
		final String currency,
		final int[] dateArray,
		final double[] discountFactorArray,
		final double tension)
	{
		try {
			return CustomSplineDiscountCurve (
				name,
				startDate,
				currency,
				dateArray,
				discountFactorArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION,
					new ExponentialTensionSetParams (tension),
					SegmentInelasticDesignControl.Create (2, 2),
					null,
					null
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Build a Discount Curve from an array of discount factors
	 * 
	 * @param startDate Start Date
	 * @param currency Currency
	 * @param dateArray Array of dates
	 * @param discountFactorArray array of discount factors
	 * 
	 * @return Discount Curve
	 */

	public static final MergedDiscountForwardCurve BuildFromDF (
		final JulianDate startDate,
		final String currency,
		final int[] dateArray,
		final double[] discountFactorArray)
	{
		if (null == dateArray || 0 == dateArray.length || null == discountFactorArray ||
			dateArray.length != discountFactorArray.length || null == startDate) {
			return null;
		}

		double beginDiscountFactor = 1.;
		double[] rateArray = new double[dateArray.length];

		double periodBegin = startDate.julian();

		for (int dateArrayIndex = 0; dateArrayIndex < dateArray.length; ++dateArrayIndex) {
			if (dateArray[dateArrayIndex] <= periodBegin) return null;

			rateArray[dateArrayIndex] = 365.25 / (dateArray[dateArrayIndex] - periodBegin) *
				Math.log (beginDiscountFactor / discountFactorArray[dateArrayIndex]);

			beginDiscountFactor = discountFactorArray[dateArrayIndex];
			periodBegin = dateArray[dateArrayIndex];
		}

		try {
			return new FlatForwardDiscountCurve (
				startDate,
				currency,
				dateArray,
				rateArray,
				false,
				"",
				-1
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a Discount Curve from the Exponentially Compounded Flat Rate
	 * 
	 * @param startDate Start Date
	 * @param currency Currency
	 * @param rate Rate
	 * 
	 * @return Discount Curve
	 */

	public static final ExplicitBootDiscountCurve ExponentiallyCompoundedFlatRate (
		final JulianDate startDate,
		final String currency,
		final double rate)
	{
		try {
			return null == startDate ? null : new FlatForwardDiscountCurve (
				startDate,
				currency,
				new int[] {startDate.julian()},
				new double[] {rate},
				false,
				"",
				-1
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a Discount Curve from the Discretely Compounded Flat Rate
	 * 
	 * @param startDate Start Date
	 * @param currency Currency
	 * @param rate Rate
	 * @param compoundingDayCount Day Count Convention to be used for Discrete Compounding
	 * @param compoundingFrequency Frequency to be used for Discrete Compounding
	 * 
	 * @return Discount Curve
	 */

	public static final ExplicitBootDiscountCurve DiscretelyCompoundedFlatRate (
		final JulianDate startDate,
		final String currency,
		final double rate,
		final String compoundingDayCount,
		final int compoundingFrequency)
	{
		try {
			return null == startDate ? null : new FlatForwardDiscountCurve (
				startDate,
				currency,
				new int[] {startDate.julian()},
				new double[] {rate},
				true,
				compoundingDayCount,
				compoundingFrequency
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a Discount Curve from the Flat Yield
	 * 
	 * @param startDate Start Date
	 * @param currency Currency
	 * @param yield Yield
	 * @param compoundingDayCount Day Count Convention to be used for Discrete Compounding
	 * @param compoundingFrequency Frequency to be used for Discrete Compounding
	 * 
	 * @return The Discount Curve Instance
	 */

	public static final ExplicitBootDiscountCurve CreateFromFlatYield (
		final JulianDate startDate,
		final String currency,
		final double yield,
		final String compoundingDayCount,
		final int compoundingFrequency)
	{
		try {
			return null == startDate || !NumberUtil.IsValid (yield) ? null : new FlatForwardDiscountCurve (
				startDate,
				currency,
				new int[] {startDate.julian()},
				new double[] {yield},
				true,
				compoundingDayCount,
				compoundingFrequency
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a discount curve from an array of dates/rates
	 * 
	 * @param startDate Start Date
	 * @param currency Currency
	 * @param dateArray array of dates
	 * @param rateArray array of rates
	 * 
	 * @return Creates the discount curve
	 */

	public static final ExplicitBootDiscountCurve PiecewiseForward (
		final JulianDate startDate,
		final String currency,
		final int[] dateArray,
		final double[] rateArray)
	{
		try {
			return new FlatForwardDiscountCurve (
				startDate,
				currency,
				dateArray,
				rateArray,
				false,
				"",
				-1
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
