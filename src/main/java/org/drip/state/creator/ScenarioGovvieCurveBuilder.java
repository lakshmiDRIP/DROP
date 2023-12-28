
package org.drip.state.creator;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.definition.LatentStateStatic;
import org.drip.analytics.input.LatentStateShapePreservingCCIS;
import org.drip.function.r1tor1.QuadraticRationalShapeControl;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.pricer.CreditPricerParams;
import org.drip.param.valuation.ValuationCustomizationParams;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.calib.ProductQuoteSet;
import org.drip.product.definition.CalibratableComponent;
import org.drip.spline.basis.ExponentialTensionSetParams;
import org.drip.spline.basis.FunctionSetBuilderParams;
import org.drip.spline.basis.KaklisPandelisSetParams;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.grid.OverlappingStretchSpan;
import org.drip.spline.params.ResponseScalingShapeControl;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.params.SegmentInelasticDesignControl;
import org.drip.spline.stretch.BoundarySettings;
import org.drip.spline.stretch.MultiSegmentSequence;
import org.drip.spline.stretch.MultiSegmentSequenceBuilder;
import org.drip.state.curve.BasisSplineGovvieYield;
import org.drip.state.govvie.GovvieCurve;
import org.drip.state.identifier.GovvieLabel;
import org.drip.state.inference.LatentStateSegmentSpec;
import org.drip.state.inference.LatentStateStretchSpec;
import org.drip.state.inference.LinearLatentStateCalibrator;
import org.drip.state.nonlinear.FlatYieldGovvieCurve;
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
 * <i>ScenarioGovvieCurveBuilder</i> implements the Construction of the Scenario Govvie Curve using the Input
 * 	Govvie Curve Instruments. It implements the following Functions:
 * 
 * <ul>
 * 		<li>Build the Shape Preserving Govvie Curve using the Custom Parameters</li>
 * 		<li>Construct an Instance of the Shape Preserver of the desired Basis Spline Type, using the Specified Basis Set Builder Parameters</li>
 * 		<li>Construct an Instance of the Shape Preserver of the Linear Polynomial Type, using the Specified Basis Set Builder Parameters</li>
 * 		<li>Construct an Instance of the Shape Preserver of the Cubic Polynomial Type, using the Specified Basis Set Builder Parameters</li>
 * 		<li>Create an Instance of the Custom Splined Govvie Yield Curve</li>
 * 		<li>Create an Instance of the Linear Polynomial Splined Govvie Yield Curve</li>
 * 		<li>Create an Instance of the Cubic Polynomial Splined Govvie Yield Curve</li>
 * 		<li>Create an Instance of the Quartic Polynomial Splined Govvie Yield Curve</li>
 * 		<li>Create an Instance of the Kaklis-Pandelis Splined Govvie Yield Curve</li>
 * 		<li>Create an Instance of the KLK Hyperbolic Splined Govvie Yield Curve</li>
 * 		<li>Create an Instance of the KLK Rational Linear Splined Govvie Yield Curve</li>
 * 		<li>Create an Instance of the KLK Rational Quadratic Splined Govvie Yield Curve</li>
 * 		<li>Construct a Govvie Curve from an Array of Dates and Yields</li>
 * 		<li>Construct a Govvie Curve from the Specified Date and Yield</li>
 * </ul>
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

public class ScenarioGovvieCurveBuilder
{

	/**
	 * Build the Shape Preserving Govvie Curve using the Custom Parameters
	 * 
	 * @param linearLatentStateCalibrator The Linear Latent State Calibrator Instance
	 * @param latentStateStretchSpecArray Array of the Latent State Stretches
	 * @param treasuryCode Treasury Code
	 * @param currency Currency
	 * @param valuationParams Valuation Parameters
	 * @param creditPricerParams Pricer Parameters
	 * @param curveSurfaceQuoteContainer Market Parameters
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * @param epochResponse The Starting Response Value
	 * 
	 * @return Instance of the Shape Preserving Discount Curve
	 */

	public static final GovvieCurve ShapePreservingGovvieCurve (
		final LinearLatentStateCalibrator linearLatentStateCalibrator,
		final LatentStateStretchSpec[] latentStateStretchSpecArray,
		final String treasuryCode,
		final String currency,
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
			GovvieCurve govvieCurve = new BasisSplineGovvieYield (
				treasuryCode,
				currency,
				linearLatentStateCalibrator.calibrateSpan (
					latentStateStretchSpecArray,
					epochResponse,
					valuationParams,
					creditPricerParams,
					valuationCustomizationParams,
					curveSurfaceQuoteContainer
				)
			);

			return govvieCurve.setCCIS (
				new LatentStateShapePreservingCCIS (
					linearLatentStateCalibrator,
					latentStateStretchSpecArray,
					valuationParams,
					creditPricerParams,
					valuationCustomizationParams,
					curveSurfaceQuoteContainer
				)
			) ? govvieCurve : null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of the Shape Preserver of the desired Basis Spline Type, using the specified
	 *  Basis Spline Set Builder Parameters.
	 *  
	 * @param name Curve Name
	 * @param treasuryCode Treasury Code
	 * @param currency Currency
	 * @param valuationParams Valuation Parameters
	 * @param creditPricerParams Pricer Parameters
	 * @param curveSurfaceQuoteContainer Market Parameters
	 * @param valuationCustomizationParams Valuation Customization Parameters
	 * @param basisType The Basis Type
	 * @param functionSetBuilderParams The Function Set Basis Parameters
	 * @param segmentInelasticDesignControl Segment Design In-elastic Control
	 * @param calibratableComponentArray Array of Calibration Components
	 * @param manifestMeasure The Calibration Manifest Measure
	 * @param quoteArray Array of Calibration Quotes
	 * 
	 * @return Instance of the Shape Preserver of the Desired Basis Type
	 */

	public static final GovvieCurve ShapePreservingGovvieCurve (
		final String name,
		final String treasuryCode,
		final String currency,
		final ValuationParams valuationParams,
		final CreditPricerParams creditPricerParams,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer,
		final ValuationCustomizationParams valuationCustomizationParams,
		final String basisType,
		final FunctionSetBuilderParams functionSetBuilderParams,
		final SegmentInelasticDesignControl segmentInelasticDesignControl,
		final CalibratableComponent[] calibratableComponentArray,
		final String manifestMeasure,
		final double[] quoteArray)
	{
		int quoteCount = null == quoteArray ? 0 : quoteArray.length;
		int componentCount = null == calibratableComponentArray ? 0 : calibratableComponentArray.length;

		if (0 == componentCount || componentCount != quoteCount) {
			return null;
		}

		GovvieLabel govvieLabel = calibratableComponentArray[0].govvieLabel();

		try {
			LatentStateSpecification[] latentStateSpecificationArray = new LatentStateSpecification[] {
				new LatentStateSpecification (
					LatentStateStatic.LATENT_STATE_GOVVIE,
					LatentStateStatic.GOVVIE_QM_YIELD, govvieLabel
				)
			};

			LatentStateSegmentSpec[] latentStateSegmentSpecArray =
				new LatentStateSegmentSpec[componentCount];

			for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
				ProductQuoteSet productQuoteSet = calibratableComponentArray[componentIndex].calibQuoteSet
					(latentStateSpecificationArray);

				if (null == productQuoteSet ||
					!productQuoteSet.set (manifestMeasure, quoteArray[componentIndex])) {
					return null;
				}

				latentStateSegmentSpecArray[componentIndex] = new LatentStateSegmentSpec (
					calibratableComponentArray[componentIndex],
					productQuoteSet
				);
			}

			return ShapePreservingGovvieCurve (
				new LinearLatentStateCalibrator (
					new SegmentCustomBuilderControl (
						basisType,
						functionSetBuilderParams,
						segmentInelasticDesignControl,
						new ResponseScalingShapeControl (
							true,
							new QuadraticRationalShapeControl (0.)
						),
						null
					),
					BoundarySettings.FinancialStandard(),
					MultiSegmentSequence.CALIBRATE,
					null,
					null
				),
				new LatentStateStretchSpec[] {
					new LatentStateStretchSpec (name, latentStateSegmentSpecArray)
				},
				treasuryCode,
				currency,
				valuationParams,
				creditPricerParams,
				curveSurfaceQuoteContainer,
				valuationCustomizationParams,
				quoteArray[0]
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of the Shape Preserver of the Linear Polynomial Type, using the Specified Basis
	 *  Set Builder Parameters.
	 *
	 * @param name Curve Name
	 * @param treasuryCode Treasury Code
	 * @param currency Currency
	 * @param spotDate Spot Date
	 * @param calibratableComponentArray Array of Calibration Components
	 * @param quoteArray Array of Calibration Quotes
	 * @param manifestMeasure The Calibration Manifest Measure
	 * 
	 * @return Instance of the Shape Preserver of the Cubic Polynomial Type
	 */

	public static final GovvieCurve LinearPolyShapePreserver (
		final String name,
		final String treasuryCode,
		final String currency,
		final int spotDate,
		final CalibratableComponent[] calibratableComponentArray,
		final double[] quoteArray,
		final String manifestMeasure)
	{
		try {
			return ShapePreservingGovvieCurve (
				name,
				treasuryCode,
				currency,
				ValuationParams.Spot (spotDate),
				null,
				null,
				null,
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (2),
				SegmentInelasticDesignControl.Create (0, 2),
				calibratableComponentArray,
				manifestMeasure,
				quoteArray
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Instance of the Shape Preserver of the Cubic Polynomial Type, using the Specified Basis
	 *  Set Builder Parameters.
	 *
	 * @param name Curve Name
	 * @param treasuryCode Treasury Code
	 * @param currency Currency
	 * @param spotDate Spot Date
	 * @param calibratableComponentArray Array of Calibration Components
	 * @param quoteArray Array of Calibration Quotes
	 * @param manifestMeasure The Calibration Manifest Measure
	 * 
	 * @return Instance of the Shape Preserver of the Cubic Polynomial Type
	 */

	public static final GovvieCurve CubicPolyShapePreserver (
		final String name,
		final String treasuryCode,
		final String currency,
		final int spotDate,
		final CalibratableComponent[] calibratableComponentArray,
		final double[] quoteArray,
		final String manifestMeasure)
	{
		try {
			return ShapePreservingGovvieCurve (
				name,
				treasuryCode,
				currency,
				ValuationParams.Spot (spotDate),
				null,
				null,
				null,
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (4),
				SegmentInelasticDesignControl.Create (0, 2),
				calibratableComponentArray,
				manifestMeasure,
				quoteArray
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Custom Splined Govvie Yield Curve
	 * 
	 * @param name Curve Name
	 * @param startDate The Tenor Start Date
	 * @param treasuryCode Treasury Code
	 * @param currency Currency
	 * @param maturityDateArray Array of the Maturity Dates
	 * @param yieldArray Array of the Yields
	 * @param segmentCustomBuilderControl The Segment Custom Builder Control
	 * 
	 * @return The Instance of the Govvie Yield Curve
	 */

	public static final GovvieCurve CustomSplineCurve (
		final String name,
		final JulianDate startDate,
		final String treasuryCode,
		final String currency,
		final int[] maturityDateArray,
		final double[] yieldArray,
		final SegmentCustomBuilderControl segmentCustomBuilderControl)
	{
		if (null == startDate || null == maturityDateArray || null == yieldArray) {
			return null;
		}

		int treasuryCount = maturityDateArray.length;
		int[] predictorOrdinateCount = new int[treasuryCount + 1];
		double[] responseValueArray = new double[treasuryCount + 1];
		SegmentCustomBuilderControl[] segmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[treasuryCount];

		if (0 == treasuryCount || treasuryCount != yieldArray.length) {
			return null;
		}

		for (int treasuryIndex = 0; treasuryIndex <= treasuryCount; ++treasuryIndex) {
			predictorOrdinateCount[treasuryIndex] = 0 == treasuryIndex ? startDate.julian() :
				maturityDateArray[treasuryIndex - 1];

			responseValueArray[treasuryIndex] = 0 == treasuryIndex ?
				yieldArray[0] : yieldArray[treasuryIndex - 1];

			if (0 != treasuryIndex) {
				segmentCustomBuilderControlArray[treasuryIndex - 1] = segmentCustomBuilderControl;
			}
		}

		try {
			return new BasisSplineGovvieYield (
				treasuryCode,
				currency,
				new OverlappingStretchSpan (
					MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
						name,
						predictorOrdinateCount,
						responseValueArray,
						segmentCustomBuilderControlArray,
						null,
						BoundarySettings.FloatingStandard(),
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
	 * Create an Instance of the Linear Polynomial Splined Govvie Yield Curve
	 * 
	 * @param name Curve Name
	 * @param startDate The Tenor Start Date
	 * @param treasuryCode Treasury Code
	 * @param currency Currency
	 * @param maturityDateArray Array of the Maturity Dates
	 * @param yieldArray Array of the Yields
	 * 
	 * @return The Instance of the Govvie Yield Curve
	 */

	public static final GovvieCurve LinearPolynomialCurve (
		final String name,
		final JulianDate startDate,
		final String treasuryCode,
		final String currency,
		final int[] maturityDateArray,
		final double[] yieldArray)
	{
		try {
			return CustomSplineCurve (
				name,
				startDate,
				treasuryCode,
				currency,
				maturityDateArray,
				yieldArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (2),
					SegmentInelasticDesignControl.Create (0, 2),
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
	 * Create an Instance of the Cubic Polynomial Splined Govvie Yield Curve
	 * 
	 * @param name Curve Name
	 * @param startDate The Tenor Start Date
	 * @param treasuryCode Treasury Code
	 * @param currency Currency
	 * @param maturityDateArray Array of the Maturity Dates
	 * @param yieldArray Array of the Yields
	 * 
	 * @return The Instance of the Govvie Yield Curve
	 */

	public static final GovvieCurve CubicPolynomialCurve (
		final String name,
		final JulianDate startDate,
		final String treasuryCode,
		final String currency,
		final int[] maturityDateArray,
		final double[] yieldArray)
	{
		try {
			return CustomSplineCurve (
				name,
				startDate,
				treasuryCode,
				currency,
				maturityDateArray,
				yieldArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (4),
					SegmentInelasticDesignControl.Create (0, 2),
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
	 * Create an Instance of the Quartic Polynomial Splined Govvie Yield Curve
	 * 
	 * @param name Curve Name
	 * @param startDate The Tenor Start Date
	 * @param treasuryCode Treasury Code
	 * @param currency Currency
	 * @param maturityDateArray Array of the Maturity Dates
	 * @param yieldArray Array of the Yields
	 * 
	 * @return The Instance of the Govvie Yield Curve
	 */

	public static final GovvieCurve QuarticPolynomialCurve (
		final String name,
		final JulianDate startDate,
		final String treasuryCode,
		final String currency,
		final int[] maturityDateArray,
		final double[] yieldArray)
	{
		try {
			return CustomSplineCurve (
				name,
				startDate,
				treasuryCode,
				currency,
				maturityDateArray,
				yieldArray,
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
	 * Create an Instance of the Kaklis-Pandelis Splined Govvie Yield Curve
	 * 
	 * @param name Curve Name
	 * @param startDate The Tenor Start Date
	 * @param treasuryCode Treasury Code
	 * @param currency Currency
	 * @param maturityDateArray Array of the Maturity Dates
	 * @param yieldArray Array of the Yields
	 * 
	 * @return The Instance of the Govvie Yield Curve
	 */

	public static final GovvieCurve KaklisPandelisCurve (
		final String name,
		final JulianDate startDate,
		final String treasuryCode,
		final String currency,
		final int[] maturityDateArray,
		final double[] yieldArray)
	{
		try {
			return CustomSplineCurve (
				name,
				startDate,
				treasuryCode,
				currency,
				maturityDateArray,
				yieldArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KAKLIS_PANDELIS,
					new KaklisPandelisSetParams (2),
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
	 * Create an Instance of the KLK Hyperbolic Splined Govvie Yield Curve
	 * 
	 * @param name Curve Name
	 * @param startDate The Tenor Start Date
	 * @param treasuryCode Treasury Code
	 * @param currency Currency
	 * @param maturityDateArray Array of the Maturity Dates
	 * @param yieldArray Array of the Yields
	 * @param tension The Tension Parameter
	 * 
	 * @return The Instance of the Govvie Yield Curve
	 */

	public static final GovvieCurve KLKHyperbolicCurve (
		final String name,
		final JulianDate startDate,
		final String treasuryCode,
		final String currency,
		final int[] maturityDateArray,
		final double[] yieldArray,
		final double tension)
	{
		try {
			return CustomSplineCurve (
				name,
				startDate,
				treasuryCode,
				currency,
				maturityDateArray,
				yieldArray,
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
	 * Create an Instance of the KLK Rational Linear Splined Govvie Yield Curve
	 * 
	 * @param name Curve Name
	 * @param startDate The Tenor Start Date
	 * @param treasuryCode Treasury Code
	 * @param currency Currency
	 * @param maturityDateArray Array of the Maturity Dates
	 * @param yieldArray Array of the Yields
	 * @param tension The Tension Parameter
	 * 
	 * @return The Instance of the Govvie Yield Curve
	 */

	public static final GovvieCurve KLKRationalLinearCurve (
		final String name,
		final JulianDate startDate,
		final String treasuryCode,
		final String currency,
		final int[] maturityDateArray,
		final double[] yieldArray,
		final double tension)
	{
		try {
			return CustomSplineCurve (
				name,
				startDate,
				treasuryCode,
				currency,
				maturityDateArray,
				yieldArray,
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
	 * Create an Instance of the KLK Rational Quadratic Splined Govvie Yield Curve
	 * 
	 * @param name Curve Name
	 * @param startDate The Tenor Start Date
	 * @param treasuryCode Treasury Code
	 * @param currency Currency
	 * @param maturityDateArray Array of the Maturity Dates
	 * @param yieldArray Array of the Yields
	 * @param tension The Tension Parameter
	 * 
	 * @return The Instance of the Govvie Yield Curve
	 */

	public static final GovvieCurve KLKRationalQuadraticCurve (
		final String name,
		final JulianDate startDate,
		final String treasuryCode,
		final String currency,
		final int[] maturityDateArray,
		final double[] yieldArray,
		final double tension)
	{
		try {
			return CustomSplineCurve (
				name,
				startDate,
				treasuryCode,
				currency,
				maturityDateArray,
				yieldArray,
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
	 * Construct a Govvie Curve from an Array of Dates and Yields
	 * 
	 * @param epochDate Epoch Date
	 * @param treasuryCode Treasury Code
	 * @param currency Currency
	 * @param dateArray Array of Dates
	 * @param yieldArray Array of Yields
	 * 
	 * @return The Govvie Curve Instance
	 */

	public static final GovvieCurve DateYield (
		final int epochDate,
		final String treasuryCode,
		final String currency,
		final int[] dateArray,
		final double[] yieldArray)
	{
		try {
			return new FlatYieldGovvieCurve (epochDate, treasuryCode, currency, dateArray, yieldArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Govvie Curve from the Specified Date and Yield
	 * 
	 * @param epochDate Epoch Date
	 * @param treasuryCode Treasury Code
	 * @param currency Currency
	 * @param yield Yield
	 * 
	 * @return The Govvie Curve Instance
	 */

	public static final GovvieCurve ConstantYield (
		final int epochDate,
		final String treasuryCode,
		final String currency,
		final double yield)
	{
		try {
			return new FlatYieldGovvieCurve (
				epochDate,
				treasuryCode,
				currency,
				new int[] {epochDate},
				new double[] {yield}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
