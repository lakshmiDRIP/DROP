
package org.drip.state.creator;

import org.drip.analytics.date.JulianDate;
import org.drip.analytics.definition.LatentStateStatic;
import org.drip.analytics.input.LatentStateShapePreservingCCIS;
import org.drip.analytics.support.CaseInsensitiveTreeMap;
import org.drip.function.r1tor1.QuadraticRationalShapeControl;
import org.drip.numerical.common.NumberUtil;
import org.drip.param.market.CurveSurfaceQuoteContainer;
import org.drip.param.pricer.CreditPricerParams;
import org.drip.param.valuation.ValuationCustomizationParams;
import org.drip.param.valuation.ValuationParams;
import org.drip.product.calib.ProductQuoteSet;
import org.drip.product.definition.CalibratableComponent;
import org.drip.product.params.CurrencyPair;
import org.drip.product.rates.DualStreamComponent;
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
import org.drip.state.curve.BasisSplineFXForward;
import org.drip.state.fx.FXCurve;
import org.drip.state.identifier.FXLabel;
import org.drip.state.inference.LatentStateSegmentSpec;
import org.drip.state.inference.LatentStateStretchSpec;
import org.drip.state.inference.LinearLatentStateCalibrator;
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
 * <i>ScenarioFXCurveBuilder</i> implements the construction of the scenario FX Curve using the input FX
 * Curve instruments. It implements the following Functions:
 * 
 *  <ul>
 *		<li>Build the Shape Preserving FX Curve using the Custom Parameters</li>
 * 		<li>Construct an instance of the Shape Preserver of the desired basis type, using the specified basis set builder parameters</li>
 * 		<li>Construct an Instance of the Shape Preserver of the Cubic Polynomial Type, using the Specified Basis Set Builder Parameters</li>
 * 		<li>Create an Instance of the Custom Splined FX Forward Curve</li>
 * 		<li>Create an Instance of the Cubic Polynomial Splined FX Forward Curve</li>
 * 		<li>Create an Instance of the Quartic Polynomial Splined FX Forward Curve</li>
 * 		<li>Create an Instance of the Kaklis-Pandelis Splined FX Forward Curve</li>
 * 		<li>Create an Instance of the KLK Hyperbolic Splined FX Forward Curve</li>
 * 		<li>Create an Instance of the KLK Rational Linear Splined FX Forward Curve</li>
 * 		<li>Create an Instance of the KLK Rational Quadratic Splined FX Forward Curve</li>
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

public class ScenarioFXCurveBuilder
{

	/**
	 * Build the Shape Preserving FX Curve using the Custom Parameters
	 * 
	 * @param linearLatentStateCalibrator The Linear Latent State Calibrator Instance
	 * @param latentStateStretchSpecArray Array of the Latent State Stretches
	 * @param currencyPair The FX Currency Pair
	 * @param valuationParams Valuation Parameters
	 * @param creditPricerParams Pricer Parameters
	 * @param curveSurfaceQuoteContainer Market Parameters
	 * @param valuationCustomizationParams Quoting Parameters
	 * @param epochResponse The Starting Response Value
	 * 
	 * @return Instance of the Shape Preserving Discount Curve
	 */

	public static final FXCurve ShapePreservingFXCurve (
		final LinearLatentStateCalibrator linearLatentStateCalibrator,
		final LatentStateStretchSpec[] latentStateStretchSpecArray,
		final CurrencyPair currencyPair,
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
			FXCurve fxCurve = new BasisSplineFXForward (
				currencyPair,
				linearLatentStateCalibrator.calibrateSpan (
					latentStateStretchSpecArray,
					epochResponse,
					valuationParams,
					creditPricerParams,
					valuationCustomizationParams,
					curveSurfaceQuoteContainer
				)
			);

			return fxCurve.setCCIS (
				new LatentStateShapePreservingCCIS (
					linearLatentStateCalibrator,
					latentStateStretchSpecArray,
					valuationParams,
					creditPricerParams,
					valuationCustomizationParams,
					curveSurfaceQuoteContainer
				)
			) ? fxCurve : null;
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
	 * @param currencyPair The FX Currency Pair
	 * @param valuationParams Valuation Parameters
	 * @param creditPricerParams Pricer Parameters
	 * @param curveSurfaceQuoteContainer Market Parameters
	 * @param valuationCustomizationParams Quoting Parameters
	 * @param calibratableComponentArray Array of Calibration Components
	 * @param manifestMeasure The Calibration Manifest Measure
	 * @param quoteArray Array of Calibration Quotes
	 * @param epochResponse The Stretch Start DF
	 * @param segmentCustomBuilderControl Segment Custom Builder Control Parameters
	 * 
	 * @return Instance of the Shape Preserver of the desired basis type
	 */

	public static final FXCurve ShapePreservingFXCurve (
		final String name,
		final CurrencyPair currencyPair,
		final ValuationParams valuationParams,
		final CreditPricerParams creditPricerParams,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer,
		final ValuationCustomizationParams valuationCustomizationParams,
		final CalibratableComponent[] calibratableComponentArray,
		final String manifestMeasure,
		final double[] quoteArray,
		final double epochResponse,
		final SegmentCustomBuilderControl segmentCustomBuilderControl)
	{
		if (null == segmentCustomBuilderControl) {
			return null;
		}

		int quoteCount = null == quoteArray ? 0 : quoteArray.length;
		int componentCount = null == calibratableComponentArray ? 0 : calibratableComponentArray.length;

		if (0 == componentCount || componentCount != quoteCount) {
			return null;
		}

		try {
			FXLabel fxLabel = null;

			if (calibratableComponentArray[0] instanceof DualStreamComponent) {
				fxLabel = ((DualStreamComponent) calibratableComponentArray[0]).derivedStream().fxLabel();
			} else {
				CaseInsensitiveTreeMap<FXLabel> fxLabelMap = calibratableComponentArray[0].fxLabel();

				if (null != fxLabelMap && 0 != fxLabelMap.size()) {
					fxLabel = fxLabelMap.get ("DERIVED");
				}
			}

			LatentStateSpecification[] latentStateSpecificationArray = new LatentStateSpecification[] {
				new LatentStateSpecification (
					LatentStateStatic.LATENT_STATE_FX,
					LatentStateStatic.FX_QM_FORWARD_OUTRIGHT,
					fxLabel
				)
			};

			LatentStateSegmentSpec[] latentStateSegmentSpecArray =
				new LatentStateSegmentSpec[componentCount];

			for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
				ProductQuoteSet productQuoteSet = calibratableComponentArray[componentIndex].calibQuoteSet
					(latentStateSpecificationArray);

				if (null == productQuoteSet || !productQuoteSet.set (
					manifestMeasure,
					quoteArray[componentIndex]
				)) {
					return null;
				}

				latentStateSegmentSpecArray[componentIndex] =
					new LatentStateSegmentSpec (calibratableComponentArray[componentIndex], productQuoteSet);
			}

			return ShapePreservingFXCurve (
				new LinearLatentStateCalibrator (
					segmentCustomBuilderControl,
					BoundarySettings.FinancialStandard(),
					MultiSegmentSequence.CALIBRATE,
					null,
					null
				),
				new LatentStateStretchSpec[] {
					new LatentStateStretchSpec (name, latentStateSegmentSpecArray)
				},
				currencyPair,
				valuationParams,
				creditPricerParams,
				curveSurfaceQuoteContainer,
				valuationCustomizationParams,
				epochResponse
			);
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
	 * @param currencyPair The FX Currency Pair
	 * @param valuationParams Valuation Parameters
	 * @param creditPricerParams Pricer Parameters
	 * @param curveSurfaceQuoteContainer Market Parameters
	 * @param valuationCustomizationParams Quoting Parameters
	 * @param basisType The Basis Type
	 * @param functionSetBuilderParams The Function Set Basis Parameters
	 * @param calibratableComponentArray Array of Calibration Components
	 * @param manifestMeasure The Calibration Manifest Measure
	 * @param quoteArray Array of Calibration Quotes
	 * @param epochResponse The Stretch Start DF
	 * 
	 * @return Instance of the Shape Preserver of the desired basis type
	 */

	public static final FXCurve ShapePreservingFXCurve (
		final String name,
		final CurrencyPair currencyPair,
		final ValuationParams valuationParams,
		final CreditPricerParams creditPricerParams,
		final CurveSurfaceQuoteContainer curveSurfaceQuoteContainer,
		final ValuationCustomizationParams valuationCustomizationParams,
		final String basisType,
		final FunctionSetBuilderParams functionSetBuilderParams,
		final CalibratableComponent[] calibratableComponentArray,
		final String manifestMeasure,
		final double[] quoteArray,
		final double epochResponse)
	{
		int quoteCount = null == quoteArray ? 0 : quoteArray.length;
		int componentCount = null == calibratableComponentArray ? 0 : calibratableComponentArray.length;

		if (0 == componentCount || componentCount != quoteCount) {
			return null;
		}

		try {
			FXLabel fxLabel = null;

			if (calibratableComponentArray[0] instanceof DualStreamComponent) {
				fxLabel = ((DualStreamComponent) calibratableComponentArray[0]).derivedStream().fxLabel();
			} else {
				CaseInsensitiveTreeMap<FXLabel> fxLabelMap = calibratableComponentArray[0].fxLabel();

				if (null != fxLabelMap && 0 != fxLabelMap.size()) {
					fxLabel = fxLabelMap.get ("DERIVED");
				}
			}

			LatentStateSpecification[] latentStateSpecificationArray = new LatentStateSpecification[] {
				new LatentStateSpecification (
					LatentStateStatic.LATENT_STATE_FX,
					LatentStateStatic.FX_QM_FORWARD_OUTRIGHT,
					fxLabel
				)
			};

			LatentStateSegmentSpec[] latentStateSegmentSpecArray =
				new LatentStateSegmentSpec[componentCount];

			for (int componentIndex = 0; componentIndex < componentCount; ++componentIndex) {
				ProductQuoteSet productQuoteSet = calibratableComponentArray[componentIndex].calibQuoteSet
					(latentStateSpecificationArray);

				if (null == productQuoteSet ||
					!productQuoteSet.set (
						manifestMeasure,
						quoteArray[componentIndex]
					)
				) {
					return null;
				}

				latentStateSegmentSpecArray[componentIndex] = new LatentStateSegmentSpec (
					calibratableComponentArray[componentIndex],
					productQuoteSet
				);
			}

			return ShapePreservingFXCurve (
				new LinearLatentStateCalibrator (
					new SegmentCustomBuilderControl (
						basisType,
						functionSetBuilderParams,
						SegmentInelasticDesignControl.Create (2, 2),
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
				currencyPair,
				valuationParams,
				creditPricerParams,
				curveSurfaceQuoteContainer,
				valuationCustomizationParams,
				epochResponse
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
	 * @param currencyPair The FX Currency Pair
	 * @param spotDate Spot Date
	 * @param calibratableComponentArray Array of Calibration Components
	 * @param quoteArray Array of Calibration Quotes
	 * @param manifestMeasure The Calibration Manifest Measure
	 * @param fxSpot The FX Spot
	 * 
	 * @return Instance of the Shape Preserver of the Cubic Polynomial Type
	 */

	public static final FXCurve CubicPolyShapePreserver (
		final String name,
		final CurrencyPair currencyPair,
		final int spotDate,
		final CalibratableComponent[] calibratableComponentArray,
		final double[] quoteArray,
		final String manifestMeasure,
		final double fxSpot)
	{
		try {
			return ShapePreservingFXCurve (
				name,
				currencyPair,
				ValuationParams.Spot (spotDate),
				null,
				null,
				null,
				MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
				new PolynomialFunctionSetParams (4),
				calibratableComponentArray,
				manifestMeasure,
				quoteArray,
				fxSpot
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Custom Splined FX Forward Curve
	 * 
	 * @param name Curve Name
	 * @param startDate The Tenor Start Date
	 * @param currencyPair The FX Currency Pair
	 * @param tenorArray Array of the Tenors
	 * @param fxForwardArray Array of the FX Forwards
	 * @param segmentCustomBuilderControl The Segment Custom Builder Control
	 * @param fxSpot The FX Spot
	 * 
	 * @return The Instance of the FX Forward Curve
	 */

	public static final FXCurve CustomSplineCurve (
		final String name,
		final JulianDate startDate,
		final CurrencyPair currencyPair,
		final String[] tenorArray,
		final double[] fxForwardArray,
		final SegmentCustomBuilderControl segmentCustomBuilderControl,
		final double fxSpot)
	{
		if (null == tenorArray || null == startDate || !NumberUtil.IsValid (fxSpot)) {
			return null;
		}

		int tenorCount = tenorArray.length;

		if (0 == tenorCount) {
			return null;
		}

		int[] basisPredictorOrdinateArray = new int[tenorCount + 1];
		double[] basisResponseValueArray = new double[tenorCount + 1];
		SegmentCustomBuilderControl[] segmentCustomBuilderControlArray =
			new SegmentCustomBuilderControl[tenorCount];

		for (int tenorIndex = 0; tenorIndex <= tenorCount; ++tenorIndex) {
			if (0 != tenorIndex) {
				String strTenor = tenorArray[tenorIndex - 1];

				if (null == strTenor || strTenor.isEmpty()) return null;

				JulianDate maturityDate = startDate.addTenor (strTenor);

				if (null == maturityDate) {
					return null;
				}

				basisPredictorOrdinateArray[tenorIndex] = maturityDate.julian();
			} else {
				basisPredictorOrdinateArray[tenorIndex] = startDate.julian();
			}

			basisResponseValueArray[tenorIndex] = 0 == tenorIndex ? fxSpot : fxForwardArray[tenorIndex - 1];

			if (0 != tenorIndex) {
				segmentCustomBuilderControlArray[tenorIndex - 1] = segmentCustomBuilderControl;
			}
		}

		try {
			return new BasisSplineFXForward (
				currencyPair,
				new OverlappingStretchSpan (
					MultiSegmentSequenceBuilder.CreateCalibratedStretchEstimator (
						name,
						basisPredictorOrdinateArray,
						basisResponseValueArray,
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
	 * Create an Instance of the Cubic Polynomial Splined FX Forward Curve
	 * 
	 * @param name Curve Name
	 * @param startDate The Tenor Start Date
	 * @param currencyPair The FX Currency Pair
	 * @param tenorArray Array of the Tenors
	 * @param fxForwardArray Array of the FX Forwards
	 * @param fxSpot The FX Spot
	 * 
	 * @return The Instance of the FX Forward Curve
	 */

	public static final FXCurve CubicPolynomialCurve (
		final String name,
		final JulianDate startDate,
		final CurrencyPair currencyPair,
		final String[] tenorArray,
		final double[] fxForwardArray,
		final double fxSpot)
	{
		try {
			return CustomSplineCurve (
				name,
				startDate,
				currencyPair,
				tenorArray,
				fxForwardArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (4),
					SegmentInelasticDesignControl.Create (2, 2),
					null,
					null
				),
				fxSpot
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Quartic Polynomial Splined FX Forward Curve
	 * 
	 * @param name Curve Name
	 * @param startDate The Tenor Start Date
	 * @param currencyPair The FX Currency Pair
	 * @param tenorArray Array of the Tenors
	 * @param fxForwardArray Array of the FX Forwards
	 * @param fxSpot The FX Spot
	 * 
	 * @return The Instance of the FX Forward Curve
	 */

	public static final FXCurve QuarticPolynomialCurve (
		final String name,
		final JulianDate startDate,
		final CurrencyPair currencyPair,
		final String[] tenorArray,
		final double[] fxForwardArray,
		final double fxSpot)
	{
		try {
			return CustomSplineCurve (
				name,
				startDate,
				currencyPair,
				tenorArray,
				fxForwardArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_POLYNOMIAL,
					new PolynomialFunctionSetParams (5),
					SegmentInelasticDesignControl.Create (2, 2),
					null,
					null
				),
				fxSpot
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the Kaklis-Pandelis Splined FX Forward Curve
	 * 
	 * @param name Curve Name
	 * @param startDate The Tenor Start Date
	 * @param currencyPair The FX Currency Pair
	 * @param tenorArray Array of the Tenors
	 * @param fxForwardArray Array of the FX Forwards
	 * @param fxSpot The FX Spot
	 * 
	 * @return The Instance of the FX Forward Curve
	 */

	public static FXCurve KaklisPandelisCurve (
		final String name,
		final JulianDate startDate,
		final CurrencyPair currencyPair,
		final String[] tenorArray,
		final double[] fxForwardArray,
		final double fxSpot)
	{
		try {
			return CustomSplineCurve (
				name,
				startDate,
				currencyPair,
				tenorArray,
				fxForwardArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KAKLIS_PANDELIS,
					new KaklisPandelisSetParams (5),
					SegmentInelasticDesignControl.Create (2, 2),
					null,
					null
				),
				fxSpot
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Hyperbolic Splined FX Forward Curve
	 * 
	 * @param name Curve Name
	 * @param startDate The Tenor Start Date
	 * @param currencyPair The FX Currency Pair
	 * @param tenorArray Array of the Tenors
	 * @param fxForwardArray Array of the FX Forwards
	 * @param fxSpot The FX Spot
	 * @param tension The Tension Parameter
	 * 
	 * @return The Instance of the FX Forward Curve
	 */

	public static final org.drip.state.fx.FXCurve KLKHyperbolicCurve (
		final String name,
		final JulianDate startDate,
		final CurrencyPair currencyPair,
		final String[] tenorArray,
		final double[] fxForwardArray,
		final double fxSpot,
		final double tension)
	{
		try {
			return CustomSplineCurve (
				name,
				startDate,
				currencyPair,
				tenorArray,
				fxForwardArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_HYPERBOLIC_TENSION,
					new ExponentialTensionSetParams (tension),
					SegmentInelasticDesignControl.Create (2, 2),
					null,
					null
				),
				fxSpot
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Rational Linear Splined FX Forward Curve
	 * 
	 * @param name Curve Name
	 * @param startDate The Tenor Start Date
	 * @param currencyPair The FX Currency Pair
	 * @param tenorArray Array of the Tenors
	 * @param fxForwardArray Array of the FX Forwards
	 * @param fxSpot The FX Spot
	 * @param tension The Tension Parameter
	 * 
	 * @return The Instance of the FX Forward Curve
	 */

	public static final FXCurve KLKRationalLinearCurve (
		final String name,
		final JulianDate startDate,
		final CurrencyPair currencyPair,
		final String[] tenorArray,
		final double[] fxForwardArray,
		final double fxSpot,
		final double tension)
	{
		try {
			return CustomSplineCurve (
				name,
				startDate,
				currencyPair,
				tenorArray,
				fxForwardArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION,
					new ExponentialTensionSetParams (tension),
					SegmentInelasticDesignControl.Create (2, 2),
					null,
					null
				),
				fxSpot
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Instance of the KLK Rational Quadratic Splined FX Forward Curve
	 * 
	 * @param name Curve Name
	 * @param startDate The Tenor Start Date
	 * @param currencyPair The FX Currency Pair
	 * @param tenorArray Array of the Tenors
	 * @param fxForwardArray Array of the FX Forwards
	 * @param fxSpot The FX Spot
	 * @param tension The Tension Parameter
	 * 
	 * @return The Instance of the FX Forward Curve
	 */

	public static final FXCurve KLKRationalQuadraticCurve (
		final String name,
		final JulianDate startDate,
		final CurrencyPair currencyPair,
		final String[] tenorArray,
		final double[] fxForwardArray,
		final double fxSpot,
		final double tension)
	{
		try {
			return CustomSplineCurve (
				name,
				startDate,
				currencyPair,
				tenorArray,
				fxForwardArray,
				new SegmentCustomBuilderControl (
					MultiSegmentSequenceBuilder.BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION,
					new ExponentialTensionSetParams (tension),
					SegmentInelasticDesignControl.Create (2, 2),
					null,
					null
				),
				fxSpot
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
