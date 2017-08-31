
package org.drip.spline.stretch;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * MultiSegmentSequenceBuilder exports Stretch creation/calibration methods to generate customized basis
 *  splines, with customized segment behavior using the segment control. It exports the following
 *  method of Stretch Creation:
 *  - Create an uncalibrated Stretch instance over the specified Predictor Ordinate Array using the specified
 *  	Basis Spline Parameters for the Segment.
 *  - Create a calibrated Stretch Instance over the specified array of Predictor Ordinates and Response
 *  	Values using the specified Basis Splines.
 *  - Create a calibrated Stretch Instance over the specified Predictor Ordinates, Response Values, and their
 * 		Constraints, using the specified Segment Builder Parameters.
 * 	- Create a Calibrated Stretch Instance from the Array of Predictor Ordinates and a flat Response Value.
 * 	- Create a Regression Spline Instance over the specified array of Predictor Ordinate Knot Points and the
 *  	Set of the Points to be Best Fit.
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
		try {
			return new org.drip.spline.stretch.CalibratableMultiSegmentSequence (strName, CreateSegmentSet
				(adblPredictorOrdinate, aSCBC), aSCBC);
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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblResponseValue) || null == adblPredictorOrdinate ||
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
