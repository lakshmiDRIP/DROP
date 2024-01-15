
package org.drip.spline.stretch;

import org.drip.spline.basis.ExponentialMixtureSetParams;
import org.drip.spline.basis.ExponentialRationalSetParams;
import org.drip.spline.basis.ExponentialTensionSetParams;
import org.drip.spline.basis.FunctionSetBuilder;
import org.drip.spline.basis.KaklisPandelisSetParams;
import org.drip.spline.basis.PolynomialFunctionSetParams;
import org.drip.spline.params.SegmentCustomBuilderControl;
import org.drip.spline.params.SegmentResponseValueConstraint;
import org.drip.spline.params.StretchBestFitResponse;
import org.drip.spline.segment.LatentStateResponseModel;
import org.drip.spline.tension.KochLycheKvasovFamily;

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
 * <i>MultiSegmentSequenceBuilder</i> exports Stretch creation/calibration methods to generate customized
 * 	basis splines, with customized segment behavior using the segment control. It exports the following
 * 	methods of Stretch Creation:
 *
 * <br>
 *  <ul>
 * 		<li>Polynomial Spline</li>
 * 		<li>Bernstein Polynomial Spline</li>
 * 		<li>Hyperbolic Tension Spline</li>
 * 		<li>Exponential Tension Spline</li>
 * 		<li>Kaklis-Pandelis Spline</li>
 * 		<li>Exponential Rational Spline</li>
 * 		<li>Exponential Mixture Spline</li>
 * 		<li>Koch-Lyche-Kvasov Rational Exponential Tension Spline</li>
 * 		<li>Koch-Lyche-Kvasov Rational Hyperbolic Tension Spline</li>
 * 		<li>Koch-Lyche-Kvasov Rational Linear Tension Spline</li>
 * 		<li>Koch-Lyche-Kvasov Rational Quadratic Tension Spline</li>
 * 		<li>Create an Uncalibrated Stretch instance over the specified Predictor Ordinate Array using the specified Basis Spline Parameters for the Segment</li>
 * 		<li>Create a calibrated Stretch Instance over the specified array of Predictor Ordinates and Response Values using the specified Basis Splines</li>
 * 		<li>Create a calibrated Stretch Instance over the specified Predictor Ordinates, Response Values, and their Constraints, using the specified Segment Builder Parameters</li>
 * 		<li>Create a calibrated Stretch Instance over the specified Predictor Ordinates and the Response Value Constraints, with the Segment Builder Parameters</li>
 * 		<li>Create a Calibrated Stretch Instance from the Array of Predictor Ordinates and a flat Response Value</li>
 * 		<li>Create a Regression Spline Instance over the specified array of Predictor Ordinate Knot Points and the Set of the Points to be Best Fit</li>
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
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/README.md">Multi-Segment Sequence Spline Stretch</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MultiSegmentSequenceBuilder {

	/**
	 * Polynomial Spline
	 */

	public static final java.lang.String BASIS_SPLINE_POLYNOMIAL = "Polynomial";

	/**
	 * Bernstein Polynomial Spline
	 */

	public static final java.lang.String BASIS_SPLINE_BERNSTEIN_POLYNOMIAL = "BernsteinPolynomial";

	/**
	 * Hyperbolic Tension Spline
	 */

	public static final java.lang.String BASIS_SPLINE_HYPERBOLIC_TENSION = "HyperbolicTension";

	/**
	 * Exponential Tension Spline
	 */

	public static final java.lang.String BASIS_SPLINE_EXPONENTIAL_TENSION = "ExponentialTension";

	/**
	 * Kaklis Pandelis Spline
	 */

	public static final java.lang.String BASIS_SPLINE_KAKLIS_PANDELIS = "KaklisPandelis";

	/**
	 * Exponential Rational Basis Spline
	 */

	public static final java.lang.String BASIS_SPLINE_EXPONENTIAL_RATIONAL = "ExponentialRational";

	/**
	 * Exponential Mixture Basis Spline
	 */

	public static final java.lang.String BASIS_SPLINE_EXPONENTIAL_MIXTURE = "ExponentialMixture";

	/**
	 * Koch-Lyche-Kvasov Exponential Tension Spline
	 */

	public static final java.lang.String BASIS_SPLINE_KLK_EXPONENTIAL_TENSION = "KLKExponentialTension";

	/**
	 * Koch-Lyche-Kvasov Hyperbolic Tension Spline
	 */

	public static final java.lang.String BASIS_SPLINE_KLK_HYPERBOLIC_TENSION = "KLKHyperbolicTension";

	/**
	 * Koch-Lyche-Kvasov Rational Linear Tension Spline
	 */

	public static final java.lang.String BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION =
		"KLKRationalLinearTension";

	/**
	 * Koch-Lyche-Kvasov Rational Quadratic Tension Spline
	 */

	public static final java.lang.String BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION =
		"KLKRationalQuadraticTension";

	/**
	 * Create an uncalibrated Stretch instance over the specified Predictor Ordinate Array using the
	 *  specified Basis Spline Parameters for the Segment.
	 * 
	 * @param predictorOrdinateArray Predictor Ordinate Array
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters
	 * 
	 * @return Stretch instance
	 */

	public static final LatentStateResponseModel[] CreateSegmentSet (
		final double[] predictorOrdinateArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray)
	{
		if (null == predictorOrdinateArray || null == segmentCustomBuilderControlArray) {
			return null;
		}

		int segmentCount = predictorOrdinateArray.length - 1;

		if (1 > segmentCount || segmentCount != segmentCustomBuilderControlArray.length) {
			return null;
		}

		LatentStateResponseModel[] latentStateResponseModelArray =
			new LatentStateResponseModel[segmentCount];

		for (int segmentIndex = 0; segmentIndex < segmentCount; ++segmentIndex) {
			if (null == segmentCustomBuilderControlArray[segmentIndex]) {
				return null;
			}

			String basisSpline = segmentCustomBuilderControlArray[segmentIndex].basisSpline();

			if (null == basisSpline || (
				!BASIS_SPLINE_POLYNOMIAL.equalsIgnoreCase (basisSpline) &&
				!BASIS_SPLINE_BERNSTEIN_POLYNOMIAL.equalsIgnoreCase (basisSpline) &&
				!BASIS_SPLINE_HYPERBOLIC_TENSION.equalsIgnoreCase (basisSpline) &&
				!BASIS_SPLINE_EXPONENTIAL_TENSION.equalsIgnoreCase (basisSpline) &&
				!BASIS_SPLINE_KAKLIS_PANDELIS.equalsIgnoreCase (basisSpline) &&
				!BASIS_SPLINE_EXPONENTIAL_RATIONAL.equalsIgnoreCase (basisSpline) &&
				!BASIS_SPLINE_EXPONENTIAL_MIXTURE.equalsIgnoreCase (basisSpline) &&
				!BASIS_SPLINE_KLK_EXPONENTIAL_TENSION.equalsIgnoreCase (basisSpline) &&
				!BASIS_SPLINE_KLK_HYPERBOLIC_TENSION.equalsIgnoreCase (basisSpline) &&
				!BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION.equalsIgnoreCase (basisSpline) &&
				!BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION.equalsIgnoreCase (basisSpline))) {
				return null;
			}

			if (BASIS_SPLINE_POLYNOMIAL.equalsIgnoreCase (basisSpline)) {
				if (null == (
					latentStateResponseModelArray[segmentIndex] = LatentStateResponseModel.Create (
						predictorOrdinateArray[segmentIndex],
						predictorOrdinateArray[segmentIndex + 1],
						FunctionSetBuilder.PolynomialBasisSet (
							(PolynomialFunctionSetParams)
							segmentCustomBuilderControlArray[segmentIndex].basisSetParams()
						),
						segmentCustomBuilderControlArray[segmentIndex].shapeController(),
						segmentCustomBuilderControlArray[segmentIndex].inelasticParams()
					)
				)) {
					return null;
				}
			} else if (BASIS_SPLINE_BERNSTEIN_POLYNOMIAL.equalsIgnoreCase (basisSpline)) {
				if (null == (
					latentStateResponseModelArray[segmentIndex] = LatentStateResponseModel.Create (
						predictorOrdinateArray[segmentIndex],
						predictorOrdinateArray[segmentIndex + 1],
						FunctionSetBuilder.BernsteinPolynomialBasisSet (
							(PolynomialFunctionSetParams)
							segmentCustomBuilderControlArray[segmentIndex].basisSetParams()
						),
						segmentCustomBuilderControlArray[segmentIndex].shapeController(),
						segmentCustomBuilderControlArray[segmentIndex].inelasticParams()
					)
				)) {
					return null;
				}
			} else if (BASIS_SPLINE_HYPERBOLIC_TENSION.equalsIgnoreCase (basisSpline)) {
				if (null == (
					latentStateResponseModelArray[segmentIndex] = LatentStateResponseModel.Create (
						predictorOrdinateArray[segmentIndex],
						predictorOrdinateArray[segmentIndex + 1],
						FunctionSetBuilder.HyperbolicTensionBasisSet (
							(ExponentialTensionSetParams)
							segmentCustomBuilderControlArray[segmentIndex].basisSetParams()
						),
						segmentCustomBuilderControlArray[segmentIndex].shapeController(),
						segmentCustomBuilderControlArray[segmentIndex].inelasticParams()
					)
				)) {
					return null;
				}
			} else if (BASIS_SPLINE_EXPONENTIAL_TENSION.equalsIgnoreCase (basisSpline)) {
				if (null == (
					latentStateResponseModelArray[segmentIndex] = LatentStateResponseModel.Create (
						predictorOrdinateArray[segmentIndex],
						predictorOrdinateArray[segmentIndex + 1],
						FunctionSetBuilder.ExponentialTensionBasisSet (
							(ExponentialTensionSetParams)
							segmentCustomBuilderControlArray[segmentIndex].basisSetParams()
						),
						segmentCustomBuilderControlArray[segmentIndex].shapeController(),
						segmentCustomBuilderControlArray[segmentIndex].inelasticParams()
						)
					)) {
					return null;
				}
			} else if (BASIS_SPLINE_KAKLIS_PANDELIS.equalsIgnoreCase (basisSpline)) {
				if (null == (
					latentStateResponseModelArray[segmentIndex] = LatentStateResponseModel.Create (
						predictorOrdinateArray[segmentIndex],
						predictorOrdinateArray[segmentIndex + 1],
						FunctionSetBuilder.KaklisPandelisBasisSet (
							(KaklisPandelisSetParams)
							segmentCustomBuilderControlArray[segmentIndex].basisSetParams()
						),
						segmentCustomBuilderControlArray[segmentIndex].shapeController(),
						segmentCustomBuilderControlArray[segmentIndex].inelasticParams()
					)
				)) {
					return null;
				}
			} else if (BASIS_SPLINE_EXPONENTIAL_RATIONAL.equalsIgnoreCase (basisSpline)) {
				if (null == (
					latentStateResponseModelArray[segmentIndex] = LatentStateResponseModel.Create (
						predictorOrdinateArray[segmentIndex],
						predictorOrdinateArray[segmentIndex + 1],
						FunctionSetBuilder.ExponentialRationalBasisSet (
							(ExponentialRationalSetParams)
							segmentCustomBuilderControlArray[segmentIndex].basisSetParams()
						),
						segmentCustomBuilderControlArray[segmentIndex].shapeController(),
						segmentCustomBuilderControlArray[segmentIndex].inelasticParams()
					)
				)) {
					return null;
				}
			} else if (BASIS_SPLINE_EXPONENTIAL_MIXTURE.equalsIgnoreCase (basisSpline)) {
				if (null == (
					latentStateResponseModelArray[segmentIndex] = LatentStateResponseModel.Create (
						predictorOrdinateArray[segmentIndex],
						predictorOrdinateArray[segmentIndex + 1],
						FunctionSetBuilder.ExponentialMixtureBasisSet (
							(ExponentialMixtureSetParams)
							segmentCustomBuilderControlArray[segmentIndex].basisSetParams()
						),
						segmentCustomBuilderControlArray[segmentIndex].shapeController(),
						segmentCustomBuilderControlArray[segmentIndex].inelasticParams()
					)
				)) {
					return null;
				}
			} else if (BASIS_SPLINE_KLK_EXPONENTIAL_TENSION.equalsIgnoreCase (basisSpline)) {
				if (null == (
					latentStateResponseModelArray[segmentIndex] = LatentStateResponseModel.Create (
						predictorOrdinateArray[segmentIndex], predictorOrdinateArray[segmentIndex + 1],
						KochLycheKvasovFamily.FromExponentialPrimitive (
							(ExponentialTensionSetParams)
							segmentCustomBuilderControlArray[segmentIndex].basisSetParams()
						),
						segmentCustomBuilderControlArray[segmentIndex].shapeController(),
						segmentCustomBuilderControlArray[segmentIndex].inelasticParams()
					)
				)) {
					return null;
				}
			} else if (BASIS_SPLINE_KLK_HYPERBOLIC_TENSION.equalsIgnoreCase (basisSpline)) {
				if (null == (
					latentStateResponseModelArray[segmentIndex] = LatentStateResponseModel.Create (
						predictorOrdinateArray[segmentIndex],
						predictorOrdinateArray[segmentIndex + 1],
						KochLycheKvasovFamily.FromHyperbolicPrimitive (
							(ExponentialTensionSetParams)
							segmentCustomBuilderControlArray[segmentIndex].basisSetParams()
						),
						segmentCustomBuilderControlArray[segmentIndex].shapeController(),
						segmentCustomBuilderControlArray[segmentIndex].inelasticParams()
					)
				)) {
					return null;
				}
			} else if (BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION.equalsIgnoreCase (basisSpline)) {
				if (null == (
					latentStateResponseModelArray[segmentIndex] = LatentStateResponseModel.Create (
						predictorOrdinateArray[segmentIndex],
						predictorOrdinateArray[segmentIndex + 1],
						KochLycheKvasovFamily.FromRationalLinearPrimitive (
							(ExponentialTensionSetParams)
							segmentCustomBuilderControlArray[segmentIndex].basisSetParams()
						),
						segmentCustomBuilderControlArray[segmentIndex].shapeController(),
						segmentCustomBuilderControlArray[segmentIndex].inelasticParams()
					)
				)) {
					return null;
				}
			} else if (BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION.equalsIgnoreCase (basisSpline)) {
				if (null == (
					latentStateResponseModelArray[segmentIndex] = LatentStateResponseModel.Create (
						predictorOrdinateArray[segmentIndex],
						predictorOrdinateArray[segmentIndex + 1],
						KochLycheKvasovFamily.FromRationalQuadraticPrimitive (
							(ExponentialTensionSetParams)
							segmentCustomBuilderControlArray[segmentIndex].basisSetParams()
						),
						segmentCustomBuilderControlArray[segmentIndex].shapeController(),
						segmentCustomBuilderControlArray[segmentIndex].inelasticParams()
					)
				)) {
					return null;
				}
			}
		}

		return latentStateResponseModelArray;
	}

	/**
	 * Create an Uncalibrated Stretch instance over the specified Predictor Ordinate Array using the specified
	 * 	Basis Spline Parameters for the Segment.
	 * 
	 * @param name Name of the Stretch
	 * @param predictorOrdinateArray Predictor Ordinate Array
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters
	 * 
	 * @return Stretch instance
	 */

	public static final MultiSegmentSequence CreateUncalibratedStretchEstimator (
		final String name,
		final double[] predictorOrdinateArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray)
	{
		LatentStateResponseModel[] latentStateResponseModelArray = CreateSegmentSet (
			predictorOrdinateArray,
			segmentCustomBuilderControlArray
		);

		try {
			return null == latentStateResponseModelArray ? null : new CalibratableMultiSegmentSequence (
				name,
				latentStateResponseModelArray,
				segmentCustomBuilderControlArray
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a calibrated Stretch Instance over the specified array of Predictor Ordinates and Response
	 *  Values using the specified Basis Splines.
	 * 
	 * @param name Name of the Stretch
	 * @param predictorOrdinateArray Predictor Ordinate Array
	 * @param responseValueArray Response Value Array
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param boundarySettings The Calibration Boundary Condition
	 * @param calibrationDetail The Calibration Detail
	 * 
	 * @return Stretch instance
	 */

	public static final MultiSegmentSequence CreateCalibratedStretchEstimator (
		final String name,
		final double[] predictorOrdinateArray,
		final double[] responseValueArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final BoundarySettings boundarySettings,
		final int calibrationDetail)
	{
		MultiSegmentSequence multiSegmentSequence = CreateUncalibratedStretchEstimator (
			name,
			predictorOrdinateArray,
			segmentCustomBuilderControlArray
		);

		if (null == multiSegmentSequence || null == responseValueArray) {
			return null;
		}

		int rightNodeCount = responseValueArray.length - 1;
		double[] rightResponseValueArray = new double[rightNodeCount];

		if (0 == rightNodeCount) {
			return null;
		}

		for (int rightNodeIndex = 0; rightNodeIndex < rightNodeCount; ++rightNodeIndex) {
			rightResponseValueArray[rightNodeIndex] = responseValueArray[rightNodeIndex + 1];
		}

		return multiSegmentSequence.setup (
			responseValueArray[0],
			rightResponseValueArray,
			stretchBestFitResponse,
			boundarySettings,
			calibrationDetail
		) ? multiSegmentSequence : null;
	}

	/**
	 * Create a calibrated Stretch Instance over the specified array of Predictor Ordinates and Response
	 *  Values using the specified Basis Splines.
	 * 
	 * @param name Name of the Stretch
	 * @param predictorOrdinateArray Predictor Ordinate Array
	 * @param responseValueArray Response Value Array
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param boundarySettings The Calibration Boundary Condition
	 * @param calibrationDetail The Calibration Detail
	 * 
	 * @return Stretch instance
	 */

	public static final MultiSegmentSequence CreateCalibratedStretchEstimator (
		final String name,
		final int[] predictorOrdinateArray,
		final double[] responseValueArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final BoundarySettings boundarySettings,
		final int calibrationDetail)
	{
		if (null == predictorOrdinateArray) {
			return null;
		}

		int predictorOrdinateCount = predictorOrdinateArray.length;
		double[] clonedPredictorOrdinateArray = new double[predictorOrdinateCount];

		if (0 == predictorOrdinateCount) {
			return null;
		}

		for (int predictorOrdinateIndex = 0; predictorOrdinateIndex < predictorOrdinateCount;
			++predictorOrdinateIndex) {
			clonedPredictorOrdinateArray[predictorOrdinateIndex] =
				predictorOrdinateArray[predictorOrdinateIndex];
		}

		return CreateCalibratedStretchEstimator (
			name,
			clonedPredictorOrdinateArray,
			responseValueArray,
			segmentCustomBuilderControlArray,
			stretchBestFitResponse,
			boundarySettings,
			calibrationDetail
		);
	}

	/**
	 * Create a calibrated Stretch Instance over the specified Predictor Ordinates, Response Values, and their
	 * 	Constraints, using the specified Segment Builder Parameters.
	 * 
	 * @param name Name of the Stretch
	 * @param predictorOrdinateArray Predictor Ordinate Array
	 * @param stretchLeftResponseValue Left-most Y Point
	 * @param segmentResponseValueConstraintArray Array of Response Value Constraints - One per Segment
	 * @param segmentCustomBuilderControlArray Array of Segment Builder Parameters - One per Segment
	 * @param stretchBestFitResponse Stretch Fitness Weighted Response
	 * @param boundarySettings The Calibration Boundary Condition
	 * @param calibrationDetail The Calibration Detail
	 * 
	 * @return Stretch Instance
	 */

	public static final MultiSegmentSequence CreateCalibratedStretchEstimator (
		final String name,
		final double[] predictorOrdinateArray,
		final double stretchLeftResponseValue,
		final SegmentResponseValueConstraint[] segmentResponseValueConstraintArray,
		final SegmentCustomBuilderControl[] segmentCustomBuilderControlArray,
		final StretchBestFitResponse stretchBestFitResponse,
		final BoundarySettings boundarySettings,
		final int calibrationDetail)
	{
		MultiSegmentSequence multiSegmentSequence = CreateUncalibratedStretchEstimator (
			name,
			predictorOrdinateArray,
			segmentCustomBuilderControlArray
		);

		return null == multiSegmentSequence ? null : multiSegmentSequence.setup (
			stretchLeftResponseValue,
			segmentResponseValueConstraintArray,
			stretchBestFitResponse,
			boundarySettings,
			calibrationDetail
		) ? multiSegmentSequence : null;
	}

	/**
	 * Create a calibrated Stretch Instance over the specified Predictor Ordinates and the Response Value
	 * 	Constraints, with the Segment Builder Parameters.
	 * 
	 * @param strName Name of the Stretch
	 * @param adblPredictorOrdinate Predictor Ordinate Array
	 * @param srvcStretchLeft Stretch Left Constraint
	 * @param aSRVC Array of Segment Constraints - One per Segment
	 * @param aSCBC Array of Segment Builder Parameters - One per Segment
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param bs The Calibration Boundary Condition
	 * @param iCalibrationDetail The Calibration Detail
	 * 
	 * @return Stretch Instance
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateCalibratedStretchEstimator (
		final java.lang.String strName,
		final double[] adblPredictorOrdinate,
		final org.drip.spline.params.SegmentResponseValueConstraint srvcStretchLeft,
		final org.drip.spline.params.SegmentResponseValueConstraint[] aSRVC,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final org.drip.spline.stretch.BoundarySettings bs,
		final int iCalibrationDetail)
	{
		org.drip.spline.stretch.MultiSegmentSequence mss = CreateUncalibratedStretchEstimator (strName,
			adblPredictorOrdinate, aSCBC);

		return null == mss ? null : mss.setup (srvcStretchLeft, aSRVC, sbfr, bs, iCalibrationDetail) ? mss :
			null;
	}

	/**
	 * Create a Calibrated Stretch Instance from the Array of Predictor Ordinates and a flat Response Value
	 * 
	 * @param strName Name of the Stretch
	 * @param adblPredictorOrdinate Predictor Ordinate Array
	 * @param dblResponseValue Response Value
	 * @param scbc Segment Builder Parameters - One per Segment
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param bs The Calibration Boundary Condition
	 * @param iCalibrationDetail The Calibration Detail
	 * 
	 * @return Stretch Instance
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateCalibratedStretchEstimator (
		final java.lang.String strName,
		final double[] adblPredictorOrdinate,
		final double dblResponseValue,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final org.drip.spline.stretch.BoundarySettings bs,
		final int iCalibrationDetail)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblResponseValue) || null == adblPredictorOrdinate ||
			null == scbc)
			return null;

		int iNumPredictorOrdinate = adblPredictorOrdinate.length;

		if (1 >= iNumPredictorOrdinate) return null;

		double[] adblResponseValue = new double[iNumPredictorOrdinate];
		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumPredictorOrdinate - 1];

		for (int i = 0; i < iNumPredictorOrdinate; ++i) {
			adblResponseValue[i] = dblResponseValue;

			if (0 != i) aSCBC[i - 1] = scbc;
		}

		return CreateCalibratedStretchEstimator (strName, adblPredictorOrdinate, adblResponseValue, aSCBC,
			sbfr, bs, iCalibrationDetail);
	}

	/**
	 * Create a Regression Spline Instance over the specified array of Predictor Ordinate Knot Points and the
	 *  Set of the Points to be Best Fit.
	 * <li>Create a Regression Spline Instance over the specified array of Predictor Ordinate Knot Points and the Set of the Points to be Best Fit</li>
	 * 
	 * @param strName Name of the Stretch
	 * @param adblKnotPredictorOrdinate Array of the Predictor Ordinate Knots
	 * @param aSCBC Array of Segment Builder Parameters
	 * @param sbfr Stretch Fitness Weighted Response
	 * @param bs The Calibration Boundary Condition
	 * @param iCalibrationDetail The Calibration Detail
	 * 
	 * @return Stretch instance
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateRegressionSplineEstimator (
		final java.lang.String strName,
		final double[] adblKnotPredictorOrdinate,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final org.drip.spline.stretch.BoundarySettings bs,
		final int iCalibrationDetail)
	{
		org.drip.spline.stretch.MultiSegmentSequence mss = CreateUncalibratedStretchEstimator (strName,
			adblKnotPredictorOrdinate, aSCBC);

		if (null == mss) return null;

		return mss.setup (null, null, sbfr, bs, iCalibrationDetail) ? mss : null;
	}
}
