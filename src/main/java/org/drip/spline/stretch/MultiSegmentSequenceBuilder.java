
package org.drip.spline.stretch;

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
 * basis splines, with customized segment behavior using the segment control. It exports the following
 * method of Stretch Creation:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Create an uncalibrated Stretch instance over the specified Predictor Ordinate Array using the
 *  			specified Basis Spline Parameters for the Segment
 *  	</li>
 *  	<li>
 *  		Create a calibrated Stretch Instance over the specified array of Predictor Ordinates and Response
 *  			Values using the specified Basis Splines
 *  	</li>
 *  	<li>
 *  		Create a calibrated Stretch Instance over the specified Predictor Ordinates, Response Values, and
 *  			their Constraints, using the specified Segment Builder Parameters
 *  	</li>
 *  	<li>
 * 			Create a Calibrated Stretch Instance from the Array of Predictor Ordinates and a flat Response
 * 				Value
 *  	</li>
 *  	<li>
 * 			Create a Regression Spline Instance over the specified array of Predictor Ordinate Knot Points
 * 				and the Set of the Points to be Best Fit
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/stretch/README.md">Multi-Segment Sequence Spline Stretch</a></li>
 *  </ul>
 * <br><br>
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
	 * @param adblPredictorOrdinate Predictor Ordinate Array
	 * @param aSCBC Array of Segment Builder Parameters
	 * 
	 * @return Stretch instance
	 */

	public static final org.drip.spline.segment.LatentStateResponseModel[] CreateSegmentSet (
		final double[] adblPredictorOrdinate,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC)
	{
		if (null == adblPredictorOrdinate || null == aSCBC) return null;

		int iNumSegment = adblPredictorOrdinate.length - 1;

		if (1 > iNumSegment || iNumSegment != aSCBC.length) return null;

		org.drip.spline.segment.LatentStateResponseModel[] aCS = new
			org.drip.spline.segment.LatentStateResponseModel[iNumSegment];

		for (int i = 0; i < iNumSegment; ++i) {
			if (null == aSCBC[i]) return null;

			java.lang.String strBasisSpline = aSCBC[i].basisSpline();

			if (null == strBasisSpline || (!BASIS_SPLINE_POLYNOMIAL.equalsIgnoreCase (strBasisSpline) &&
				!BASIS_SPLINE_BERNSTEIN_POLYNOMIAL.equalsIgnoreCase (strBasisSpline) &&
					!BASIS_SPLINE_HYPERBOLIC_TENSION.equalsIgnoreCase (strBasisSpline) &&
						!BASIS_SPLINE_EXPONENTIAL_TENSION.equalsIgnoreCase (strBasisSpline) &&
							!BASIS_SPLINE_KAKLIS_PANDELIS.equalsIgnoreCase (strBasisSpline) &&
								!BASIS_SPLINE_EXPONENTIAL_RATIONAL.equalsIgnoreCase (strBasisSpline) &&
									!BASIS_SPLINE_EXPONENTIAL_MIXTURE.equalsIgnoreCase (strBasisSpline) &&
										!BASIS_SPLINE_KLK_EXPONENTIAL_TENSION.equalsIgnoreCase
											(strBasisSpline) &&
												!BASIS_SPLINE_KLK_HYPERBOLIC_TENSION.equalsIgnoreCase
													(strBasisSpline) &&
														!BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION.equalsIgnoreCase
				(strBasisSpline) && !BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION.equalsIgnoreCase
					(strBasisSpline)))
				return null;

			if (BASIS_SPLINE_POLYNOMIAL.equalsIgnoreCase (strBasisSpline)) {
				if (null == (aCS[i] = org.drip.spline.segment.LatentStateResponseModel.Create
					(adblPredictorOrdinate[i], adblPredictorOrdinate[i + 1],
						org.drip.spline.basis.FunctionSetBuilder.PolynomialBasisSet
							((org.drip.spline.basis.PolynomialFunctionSetParams) aSCBC[i].basisSetParams()),
								aSCBC[i].shapeController(), aSCBC[i].inelasticParams())))
					return null;
			} else if (BASIS_SPLINE_BERNSTEIN_POLYNOMIAL.equalsIgnoreCase (strBasisSpline)) {
				if (null == (aCS[i] = org.drip.spline.segment.LatentStateResponseModel.Create
					(adblPredictorOrdinate[i], adblPredictorOrdinate[i + 1],
						org.drip.spline.basis.FunctionSetBuilder.BernsteinPolynomialBasisSet
							((org.drip.spline.basis.PolynomialFunctionSetParams) aSCBC[i].basisSetParams()),
								aSCBC[i].shapeController(), aSCBC[i].inelasticParams())))
					return null;
			} else if (BASIS_SPLINE_HYPERBOLIC_TENSION.equalsIgnoreCase (strBasisSpline)) {
				if (null == (aCS[i] = org.drip.spline.segment.LatentStateResponseModel.Create
					(adblPredictorOrdinate[i], adblPredictorOrdinate[i + 1],
						org.drip.spline.basis.FunctionSetBuilder.HyperbolicTensionBasisSet
							((org.drip.spline.basis.ExponentialTensionSetParams) aSCBC[i].basisSetParams()),
								aSCBC[i].shapeController(), aSCBC[i].inelasticParams())))
					return null;
			} else if (BASIS_SPLINE_EXPONENTIAL_TENSION.equalsIgnoreCase (strBasisSpline)) {
				if (null == (aCS[i] = org.drip.spline.segment.LatentStateResponseModel.Create
					(adblPredictorOrdinate[i], adblPredictorOrdinate[i + 1],
						org.drip.spline.basis.FunctionSetBuilder.ExponentialTensionBasisSet
							((org.drip.spline.basis.ExponentialTensionSetParams) aSCBC[i].basisSetParams()),
								aSCBC[i].shapeController(), aSCBC[i].inelasticParams())))
					return null;
			} else if (BASIS_SPLINE_KAKLIS_PANDELIS.equalsIgnoreCase (strBasisSpline)) {
				if (null == (aCS[i] = org.drip.spline.segment.LatentStateResponseModel.Create
					(adblPredictorOrdinate[i], adblPredictorOrdinate[i + 1],
						org.drip.spline.basis.FunctionSetBuilder.KaklisPandelisBasisSet
							((org.drip.spline.basis.KaklisPandelisSetParams) aSCBC[i].basisSetParams()),
								aSCBC[i].shapeController(), aSCBC[i].inelasticParams())))
					return null;
			} else if (BASIS_SPLINE_EXPONENTIAL_RATIONAL.equalsIgnoreCase (strBasisSpline)) {
				if (null == (aCS[i] = org.drip.spline.segment.LatentStateResponseModel.Create
					(adblPredictorOrdinate[i], adblPredictorOrdinate[i + 1],
						org.drip.spline.basis.FunctionSetBuilder.ExponentialRationalBasisSet
							((org.drip.spline.basis.ExponentialRationalSetParams) aSCBC[i].basisSetParams()),
								aSCBC[i].shapeController(), aSCBC[i].inelasticParams())))
					return null;
			} else if (BASIS_SPLINE_EXPONENTIAL_MIXTURE.equalsIgnoreCase (strBasisSpline)) {
				if (null == (aCS[i] = org.drip.spline.segment.LatentStateResponseModel.Create
					(adblPredictorOrdinate[i], adblPredictorOrdinate[i + 1],
						org.drip.spline.basis.FunctionSetBuilder.ExponentialMixtureBasisSet
							((org.drip.spline.basis.ExponentialMixtureSetParams) aSCBC[i].basisSetParams()),
								aSCBC[i].shapeController(), aSCBC[i].inelasticParams())))
					return null;
			} else if (BASIS_SPLINE_KLK_EXPONENTIAL_TENSION.equalsIgnoreCase (strBasisSpline)) {
				if (null == (aCS[i] = org.drip.spline.segment.LatentStateResponseModel.Create
					(adblPredictorOrdinate[i], adblPredictorOrdinate[i + 1],
						org.drip.spline.tension.KochLycheKvasovFamily.FromExponentialPrimitive
							((org.drip.spline.basis.ExponentialTensionSetParams) aSCBC[i].basisSetParams()),
								aSCBC[i].shapeController(), aSCBC[i].inelasticParams())))
					return null;
			} else if (BASIS_SPLINE_KLK_HYPERBOLIC_TENSION.equalsIgnoreCase (strBasisSpline)) {
				if (null == (aCS[i] = org.drip.spline.segment.LatentStateResponseModel.Create
					(adblPredictorOrdinate[i], adblPredictorOrdinate[i + 1],
						org.drip.spline.tension.KochLycheKvasovFamily.FromHyperbolicPrimitive
							((org.drip.spline.basis.ExponentialTensionSetParams) aSCBC[i].basisSetParams()),
								aSCBC[i].shapeController(), aSCBC[i].inelasticParams())))
					return null;
			} else if (BASIS_SPLINE_KLK_RATIONAL_LINEAR_TENSION.equalsIgnoreCase (strBasisSpline)) {
				if (null == (aCS[i] = org.drip.spline.segment.LatentStateResponseModel.Create
					(adblPredictorOrdinate[i], adblPredictorOrdinate[i + 1],
						org.drip.spline.tension.KochLycheKvasovFamily.FromRationalLinearPrimitive
							((org.drip.spline.basis.ExponentialTensionSetParams) aSCBC[i].basisSetParams()),
								aSCBC[i].shapeController(), aSCBC[i].inelasticParams())))
					return null;
			} else if (BASIS_SPLINE_KLK_RATIONAL_QUADRATIC_TENSION.equalsIgnoreCase (strBasisSpline)) {
				if (null == (aCS[i] = org.drip.spline.segment.LatentStateResponseModel.Create
					(adblPredictorOrdinate[i], adblPredictorOrdinate[i + 1],
						org.drip.spline.tension.KochLycheKvasovFamily.FromRationalQuadraticPrimitive
							((org.drip.spline.basis.ExponentialTensionSetParams) aSCBC[i].basisSetParams()),
								aSCBC[i].shapeController(), aSCBC[i].inelasticParams())))
					return null;
			}
		}

		return aCS;
	}

	/**
	 * Create an uncalibrated Stretch instance over the specified Predictor Ordinate Array using the specified
	 * 	Basis Spline Parameters for the Segment.
	 * 
	 * @param strName Name of the Stretch
	 * @param adblPredictorOrdinate Predictor Ordinate Array
	 * @param aSCBC Array of Segment Builder Parameters
	 * 
	 * @return Stretch instance
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateUncalibratedStretchEstimator (
		final java.lang.String strName,
		final double[] adblPredictorOrdinate,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC)
	{
		org.drip.spline.segment.LatentStateResponseModel[] latentStateResponseModelArray = CreateSegmentSet (
			adblPredictorOrdinate,
			aSCBC
		);

		try {
			return null == latentStateResponseModelArray ? null :
			new org.drip.spline.stretch.CalibratableMultiSegmentSequence (
				strName,
				latentStateResponseModelArray,
				aSCBC
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create a calibrated Stretch Instance over the specified array of Predictor Ordinates and Response
	 *  Values using the specified Basis Splines.
	 * 
	 * @param strName Name of the Stretch
	 * @param adblPredictorOrdinate Predictor Ordinate Array
	 * @param adblResponseValue Response Value Array
	 * @param aSCBC Array of Segment Builder Parameters
	 * @param rbfr Stretch Fitness Weighted Response
	 * @param bs The Calibration Boundary Condition
	 * @param iCalibrationDetail The Calibration Detail
	 * 
	 * @return Stretch instance
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateCalibratedStretchEstimator (
		final java.lang.String strName,
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC,
		final org.drip.spline.params.StretchBestFitResponse rbfr,
		final org.drip.spline.stretch.BoundarySettings bs,
		final int iCalibrationDetail)
	{
		org.drip.spline.stretch.MultiSegmentSequence mss = CreateUncalibratedStretchEstimator (strName,
			adblPredictorOrdinate, aSCBC);

		if (null == mss || null == adblResponseValue) return null;

		int iNumRightNode = adblResponseValue.length - 1;
		double[] adblResponseValueRight = new double[iNumRightNode];

		if (0 == iNumRightNode) return null;

		for (int i = 0; i < iNumRightNode; ++i)
			adblResponseValueRight[i] = adblResponseValue[i + 1];

		return mss.setup (adblResponseValue[0], adblResponseValueRight, rbfr, bs, iCalibrationDetail) ? mss :
			null;
	}

	/**
	 * Create a calibrated Stretch Instance over the specified array of Predictor Ordinates and Response
	 *  Values using the specified Basis Splines.
	 * 
	 * @param strName Name of the Stretch
	 * @param aiPredictorOrdinate Predictor Ordinate Array
	 * @param adblResponseValue Response Value Array
	 * @param aSCBC Array of Segment Builder Parameters
	 * @param rbfr Stretch Fitness Weighted Response
	 * @param bs The Calibration Boundary Condition
	 * @param iCalibrationDetail The Calibration Detail
	 * 
	 * @return Stretch instance
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateCalibratedStretchEstimator (
		final java.lang.String strName,
		final int[] aiPredictorOrdinate,
		final double[] adblResponseValue,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC,
		final org.drip.spline.params.StretchBestFitResponse rbfr,
		final org.drip.spline.stretch.BoundarySettings bs,
		final int iCalibrationDetail)
	{
		if (null == aiPredictorOrdinate) return null;

		int iNumPredictorOrdinate = aiPredictorOrdinate.length;
		double[] adblPredictorOrdinate = new double[iNumPredictorOrdinate];

		if (0 == iNumPredictorOrdinate) return null;

		for (int i = 0; i < iNumPredictorOrdinate; ++i)
			adblPredictorOrdinate[i] = aiPredictorOrdinate[i];

		return CreateCalibratedStretchEstimator (strName, adblPredictorOrdinate, adblResponseValue, aSCBC,
			rbfr, bs, iCalibrationDetail);
	}

	/**
	 * Create a calibrated Stretch Instance over the specified Predictor Ordinates, Response Values, and their
	 * 	Constraints, using the specified Segment Builder Parameters.
	 * 
	 * @param strName Name of the Stretch
	 * @param adblPredictorOrdinate Predictor Ordinate Array
	 * @param dblStretchLeftResponseValue Left-most Y Point
	 * @param aSRVC Array of Response Value Constraints - One per Segment
	 * @param aSCBC Array of Segment Builder Parameters - One per Segment
	 * @param rbfr Stretch Fitness Weighted Response
	 * @param bs The Calibration Boundary Condition
	 * @param iCalibrationDetail The Calibration Detail
	 * 
	 * @return Stretch Instance
	 */

	public static final org.drip.spline.stretch.MultiSegmentSequence CreateCalibratedStretchEstimator (
		final java.lang.String strName,
		final double[] adblPredictorOrdinate,
		final double dblStretchLeftResponseValue,
		final org.drip.spline.params.SegmentResponseValueConstraint[] aSRVC,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC,
		final org.drip.spline.params.StretchBestFitResponse rbfr,
		final org.drip.spline.stretch.BoundarySettings bs,
		final int iCalibrationDetail)
	{
		org.drip.spline.stretch.MultiSegmentSequence mss = CreateUncalibratedStretchEstimator (strName,
			adblPredictorOrdinate, aSCBC);

		return null == mss ? null : mss.setup (dblStretchLeftResponseValue, aSRVC, rbfr, bs,
			iCalibrationDetail) ? mss : null;
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
