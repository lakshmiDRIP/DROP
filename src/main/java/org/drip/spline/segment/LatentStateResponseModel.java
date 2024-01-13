
package org.drip.spline.segment;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.differentiation.WengertJacobian;
import org.drip.numerical.linearalgebra.LinearSystemSolver;
import org.drip.numerical.linearalgebra.LinearizationOutput;
import org.drip.spline.basis.FunctionSet;
import org.drip.spline.params.PreceedingManifestSensitivityControl;
import org.drip.spline.params.ResponseScalingShapeControl;
import org.drip.spline.params.SegmentBasisFlexureConstraint;
import org.drip.spline.params.SegmentBestFitResponse;
import org.drip.spline.params.SegmentInelasticDesignControl;
import org.drip.spline.params.SegmentResponseValueConstraint;
import org.drip.spline.params.SegmentStateCalibrationInputs;

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
 * <i>LatentStateResponseModel</i> implements the single segment basis calibration and inference
 * 	functionality. It exports the following functionality:
 *
 * <br>
 *  <ul>
 *  	<li>Build the <i>LatentStateResponseModel</i> instance from the Basis Function/Shape Controller Set</li>
 *  	<li>Build the <i>LatentStateResponseModel</i> instance from the Basis Evaluator Set</li>
 *  	<li>Retrieve the Number of Parameters, Basis Evaluator, Array of the Response Basis Coefficients, and Segment Design Inelastic Control</li>
 *  	<li>Calibrate the Segment State from the Calibration Parameter Set</li>
 *  	<li>Sensitivity Calibrator: Calibrate the Segment Manifest Jacobian from the Calibration Parameter Set</li>
 *  	<li>Calibrate the coefficients from the prior Predictor/Response Segment, the Constraint, and fitness Weights</li>
 *  	<li>Calibrate the coefficients from the prior Segment and the Response Value at the Right Predictor Ordinate</li>
 *  	<li>Calibrate the Coefficients from the Edge Response Values and the Left Edge Response Slope</li>
 *  	<li>Calibrate the coefficients from the Left Edge Response Value Constraint, the Left Edge Response Value Slope, and the Right Edge Response Value Constraint</li>
 *  	<li>Retrieve the Segment Curvature, Length, and the Best Fit DPE</li>
 *  	<li>Calculate the Response Value and its Derivative at the given Predictor Ordinate</li>
 *  	<li>Calculate the Ordered Derivative of the Coefficient to the Manifest</li>
 *  	<li>Calculate the Jacobian of the Segment's Response Basis Function Coefficients to the Edge Inputs</li>
 *  	<li>Calculate the Jacobian of the Response to the Edge Inputs at the given Predictor Ordinate</li>
 *  	<li>Calculate the Jacobian of the Response to the Basis Coefficients at the given Predictor Ordinate</li>
 *  	<li>Calibrate the segment and calculate the Jacobian of the Segment's Response Basis Function Coefficients to the Edge Parameters</li>
 *  	<li>Calibrate the Coefficients from the Edge Response Values and the Left Edge Response Value Slope and calculate the Jacobian of the Segment's Response Basis Function Coefficients to the Edge Parameters</li>
 *  	<li>Calibrate the coefficients from the prior Segment and the Response Value at the Right Predictor Ordinate and calculate the Jacobian of the Segment's Response Basis Function Coefficients to the Edge Parameters</li>
 *  	<li>Indicate whether the given segment is monotone. If monotone, may optionally indicate the nature of the extrema contained inside (maxima/minima/infection)</li>
 *  	<li>Clip the part of the Segment to the Right of the specified Predictor Ordinate. Retain all other constraints the same</li>
 *  	<li>Clip the part of the Segment to the Left of the specified Predictor Ordinate. Retain all other constraints the same</li>
 *  	<li>Display the string representation for diagnostic purposes</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment/README.md">Flexure Penalizing Best Fit Segment</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateResponseModel
	extends LatentStateInelastic
{

	/**
	 * LEFT NODE VALUE PARAMETER INDEX
	 */

	public static final int LEFT_NODE_VALUE_PARAMETER_INDEX = 0;

	/**
	 * RIGHT NODE VALUE PARAMETER INDEX
	 */

	public static final int RIGHT_NODE_VALUE_PARAMETER_INDEX = 1;

	private BasisEvaluator _basisEvaluator = null;
	private double[] _responseToBasisCoefficientSensitivity = null;
	private double[][] _responseBasisCoefficientToConstraintHessian = null;
	private WengertJacobian _basisCoefficientToEdgeValueWengertJacobian = null;
	private SegmentInelasticDesignControl _segmentInelasticDesignControl = null;

	private CaseInsensitiveHashMap<LatentStateManifestSensitivity> _latentStateManifestSensitivityMap =
		new CaseInsensitiveHashMap<LatentStateManifestSensitivity>();

	/**
	 * Build the <i>LatentStateResponseModel</i> instance from the Basis Function/Shape Controller Set
	 * 
	 * @param leftPredictorOrdinate Left Predictor Ordinate
	 * @param rightPredictorOrdinate Right Predictor Ordinate
	 * @param functionSet Response Basis Function Set
	 * @param responseScalingShapeControl Shape Controller
	 * @param segmentInelasticDesignControl Segment Inelastic Design Parameters
	 * 
	 * @return Instance of <i>LatentStateResponseModel</i>
	 */

	public static final org.drip.spline.segment.LatentStateResponseModel Create (
		final double leftPredictorOrdinate,
		final double rightPredictorOrdinate,
		final FunctionSet functionSet,
		final ResponseScalingShapeControl responseScalingShapeControl,
		final SegmentInelasticDesignControl segmentInelasticDesignControl)
	{
		try {
			SegmentBasisEvaluator segmentBasisEvaluator = new SegmentBasisEvaluator (
				functionSet,
				responseScalingShapeControl
			);

			if (!NumberUtil.IsValid (leftPredictorOrdinate) || NumberUtil.IsValid (rightPredictorOrdinate) ||
				leftPredictorOrdinate == rightPredictorOrdinate)
			{
				return null;
			}

			LatentStateResponseModel latentStateResponseModel = new LatentStateResponseModel (
				leftPredictorOrdinate,
				rightPredictorOrdinate,
				segmentBasisEvaluator,
				segmentInelasticDesignControl
			);

			return segmentBasisEvaluator.setContainingInelastics (latentStateResponseModel) ?
				latentStateResponseModel : null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Build the <i>LatentStateResponseModel</i> instance from the Basis Evaluator Set
	 * 
	 * @param leftPredictorOrdinate Left Predictor Ordinate
	 * @param rightPredictorOrdinate Right Predictor Ordinate
	 * @param basisEvaluator Basis Evaluator
	 * @param segmentInelasticDesignControl Segment Inelastic Design Parameters
	 * 
	 * @return Instance of <i>LatentStateResponseModel</i>
	 */

	public static final LatentStateResponseModel Create (
		final double leftPredictorOrdinate,
		final double rightPredictorOrdinate,
		final BasisEvaluator basisEvaluator,
		final SegmentInelasticDesignControl segmentInelasticDesignControl)
	{
		try {
			LatentStateResponseModel latentStateResponseModel = new LatentStateResponseModel (
				leftPredictorOrdinate,
				rightPredictorOrdinate,
				basisEvaluator,
				segmentInelasticDesignControl
			);

			return basisEvaluator.setContainingInelastics (latentStateResponseModel) ?
				latentStateResponseModel : null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private LatentStateResponseModel (
		final double leftPredictorOrdinate,
		final double rightPredictorOrdinate,
		final BasisEvaluator basisEvaluator,
		final SegmentInelasticDesignControl segmentInelasticDesignControl)
		throws Exception
	{
		super (leftPredictorOrdinate, rightPredictorOrdinate);

		if (null == (_basisEvaluator = basisEvaluator) ||
			null == (_segmentInelasticDesignControl = segmentInelasticDesignControl)) {
			throw new Exception ("LatentStateResponseModel ctr: Invalid Basis Functions!");
		}

		int basisCount = _basisEvaluator.numBasis();

		_responseToBasisCoefficientSensitivity = new double[basisCount];

		if (0 >= basisCount || _segmentInelasticDesignControl.Ck() > basisCount - 2) {
			throw new Exception ("LatentStateResponseModel ctr: Invalid inputs!");
		}
	}

	private double[] DResponseDBasisCoeff (
		final double predictorOrdinate,
		final int order)
	{
		if (0 == order) {
			return null;
		}

		int basisCount = _basisEvaluator.numBasis();

		double[] responseToBasisCoefficientSensitivityArray = new double[basisCount];

		for (int basisIndex = 0; basisIndex < basisCount; ++basisIndex) {
			try {
				responseToBasisCoefficientSensitivityArray[basisIndex] = 1 == order ?
					_basisEvaluator.shapedBasisFunctionResponse (predictorOrdinate, basisIndex) : 0.;
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return responseToBasisCoefficientSensitivityArray;
	}

	private double[] transmissionCk (
		final double predictorOrdinate,
		final LatentStateResponseModel precedingLatentStateResponseModel,
		final int ck)
	{
		double[] derivativeArray = new double[ck];

		for (int ci = 0; ci < ck; ++ci) {
			try {
				derivativeArray[ci] = precedingLatentStateResponseModel.calcResponseValueDerivative (
					predictorOrdinate,
					ci + 1
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return derivativeArray;
	}

	private LatentStateManifestSensitivity manifestSensitivity (
		final String manifestMeasure)
	{
		return null == manifestMeasure || manifestMeasure.isEmpty() ||
			!_latentStateManifestSensitivityMap.containsKey (manifestMeasure) ?
			null : _latentStateManifestSensitivityMap.get (manifestMeasure);
	}

	private double[] CkDBasisCoeffDPreceedingManifestMeasure (
		final String manifestMeasure)
	{
		LatentStateManifestSensitivity latentStateManifestSensitivity = manifestSensitivity
			(manifestMeasure);

		if (null == latentStateManifestSensitivity) {
			return null;
		}

		int ck = latentStateManifestSensitivity.getPMSC().Ck();

		if (0 == ck) {
			return null;
		}

		double[] basisCoefficientToPrecedingManifestTailSensitivity = new double[ck];

		for (int i = 0; i < ck; ++i) {
			basisCoefficientToPrecedingManifestTailSensitivity[i] = 0.;
		}

		return basisCoefficientToPrecedingManifestTailSensitivity;
	}

	/**
	 * Set the Preceding Manifest Sensitivity Control Parameters for the specified Manifest Measure
	 * 
	 * @param manifestMeasure The Manifest Measure
	 * @param precedingManifestSensitivityControl The Preceding Manifest Sensitivity Control Instance
	 * 
	 * @return TRUE - Named Preceding Manifest Sensitivity Control Instance Successfully Set
	 */

	public boolean setPreceedingManifestSensitivityControl (
		final String manifestMeasure,
		final PreceedingManifestSensitivityControl precedingManifestSensitivityControl)
	{
		if (null == manifestMeasure || manifestMeasure.isEmpty()) {
			return false;
		}

		try {
			_latentStateManifestSensitivityMap.put (
				manifestMeasure,
				new LatentStateManifestSensitivity (precedingManifestSensitivityControl)
			);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Retrieve the Number of Parameters
	 * 
	 * @return The Number of Parameters
	 */

	public int numParameters()
	{
		return _segmentInelasticDesignControl.Ck() + 2;
	}

	/**
	 * Retrieve the Basis Evaluator
	 * 
	 * @return The Basis Evaluator
	 */

	public BasisEvaluator basisEvaluator()
	{
		return _basisEvaluator;
	}

	/**
	 * Retrieve the Array of Response Basis Coefficients
	 * 
	 * @return The Array of Response Basis Coefficients
	 */

	public double[] responseBasisCoefficient()
	{
		return _responseToBasisCoefficientSensitivity;
	}

	/**
	 * Retrieve the Segment Inelastic Design Control
	 * 
	 * @return The Segment Inelastic Design Control
	 */

	public SegmentInelasticDesignControl designControl()
	{
		return _segmentInelasticDesignControl;
	}

	/**
	 * Main Calibrator: Calibrate the Segment State from the Calibration Parameter Set
	 * 
	 * @param stateSegmentStateCalibrationInputs The Segment State Calibration Inputs Set
	 * 
	 * @return TRUE - Calibration Successful
	 */

	public boolean calibrateState (
		final SegmentStateCalibrationInputs stateSegmentStateCalibrationInputs)
	{
		if (null == stateSegmentStateCalibrationInputs) {
			return false;
		}

		double[] predictorOrdinateArray = stateSegmentStateCalibrationInputs.predictorOrdinates();

		double[] responseValueArray = stateSegmentStateCalibrationInputs.responseValues();

		double[] leftEdgeDerivativeArray = stateSegmentStateCalibrationInputs.leftEdgeDeriv();

		double[] rightEdgeDerivativeArray = stateSegmentStateCalibrationInputs.rightEdgeDeriv();

		SegmentBestFitResponse segmentBestFitResponse = stateSegmentStateCalibrationInputs.bestFitResponse();

		SegmentBasisFlexureConstraint[] segmentBasisFlexureConstraintArray =
			stateSegmentStateCalibrationInputs.flexureConstraint();

		int constraintCount = 0;
		int responseBasisCoefficientCount = _responseToBasisCoefficientSensitivity.length;
		int leftDerivativeCount = null == leftEdgeDerivativeArray ? 0 : leftEdgeDerivativeArray.length;
		int rightDerivativeCount = null == rightEdgeDerivativeArray ? 0 : rightEdgeDerivativeArray.length;
		double[] predictorResponseConstraintValueArray = new double[responseBasisCoefficientCount];
		int predictorCount = null == predictorOrdinateArray ? 0 : predictorOrdinateArray.length;
		double[][] responseBasisCoefficientConstraintMatrix = new
			double[responseBasisCoefficientCount][responseBasisCoefficientCount];

		if (null != segmentBasisFlexureConstraintArray) {
			int potentialConstraintCount = segmentBasisFlexureConstraintArray.length;

			for (int potentialConstraintIndex = 0; potentialConstraintIndex < potentialConstraintCount;
				++potentialConstraintIndex) {
				if (null != segmentBasisFlexureConstraintArray[potentialConstraintIndex]) {
					++constraintCount;
				}
			}
		}

		if (responseBasisCoefficientCount <
			predictorCount + leftDerivativeCount + rightDerivativeCount + constraintCount) {
			return false;
		}

		try {
			BestFitFlexurePenalizer bestFitFlexurePenalizer = new BestFitFlexurePenalizer (
				this,
				_segmentInelasticDesignControl.curvaturePenaltyControl(),
				_segmentInelasticDesignControl.lengthPenaltyControl(),
				segmentBestFitResponse,
				_basisEvaluator
			);

			for (int responseBasisCoefficientIndex = 0;
				responseBasisCoefficientIndex < responseBasisCoefficientCount;
				++responseBasisCoefficientIndex) {
				if (responseBasisCoefficientIndex < predictorCount) {
					predictorResponseConstraintValueArray[responseBasisCoefficientIndex] =
						responseValueArray[responseBasisCoefficientIndex];
				} else if (responseBasisCoefficientIndex < predictorCount + constraintCount) {
					predictorResponseConstraintValueArray[responseBasisCoefficientIndex] =
						segmentBasisFlexureConstraintArray[responseBasisCoefficientIndex - predictorCount].contraintValue();
				} else if (responseBasisCoefficientIndex <
					predictorCount + constraintCount + leftDerivativeCount) {
					predictorResponseConstraintValueArray[responseBasisCoefficientIndex] =
						leftEdgeDerivativeArray[responseBasisCoefficientIndex - predictorCount - constraintCount];
				} else if (responseBasisCoefficientIndex <
					predictorCount + constraintCount + leftDerivativeCount + rightDerivativeCount) {
					predictorResponseConstraintValueArray[responseBasisCoefficientIndex] =
						rightEdgeDerivativeArray[responseBasisCoefficientIndex - predictorCount - constraintCount - leftDerivativeCount];
				} else {
					predictorResponseConstraintValueArray[responseBasisCoefficientIndex] =
						bestFitFlexurePenalizer.basisPairPenaltyConstraint (responseBasisCoefficientIndex);
				}
			}

			for (int responseBasisCoefficientIndexI = 0;
				responseBasisCoefficientIndexI < responseBasisCoefficientCount;
				++responseBasisCoefficientIndexI) {
				for (int responseBasisCoefficientIndexL = 0;
					responseBasisCoefficientIndexL < responseBasisCoefficientCount;
					++responseBasisCoefficientIndexL) {
					double[] adblCalibBasisConstraintWeight = null;

					if (0 != constraintCount && (
						responseBasisCoefficientIndexL >= predictorCount &&
						responseBasisCoefficientIndexL < predictorCount + constraintCount
					)) {
						adblCalibBasisConstraintWeight =
							segmentBasisFlexureConstraintArray[responseBasisCoefficientIndexL - predictorCount].responseBasisCoeffWeights();
					}

					if (responseBasisCoefficientIndexL < predictorCount) {
						responseBasisCoefficientConstraintMatrix[responseBasisCoefficientIndexL][responseBasisCoefficientIndexI] =
							_basisEvaluator.shapedBasisFunctionResponse (
								predictorOrdinateArray[responseBasisCoefficientIndexL],
								responseBasisCoefficientIndexI
							);
					} else if (responseBasisCoefficientIndexL < predictorCount + constraintCount) {
						responseBasisCoefficientConstraintMatrix[responseBasisCoefficientIndexL][responseBasisCoefficientIndexI] =
							adblCalibBasisConstraintWeight[responseBasisCoefficientIndexI];
					} else if (responseBasisCoefficientIndexL <
						predictorCount + constraintCount + leftDerivativeCount) {
						responseBasisCoefficientConstraintMatrix[responseBasisCoefficientIndexL][responseBasisCoefficientIndexI] =
							_basisEvaluator.shapedBasisFunctionDerivative (
								left(),
								responseBasisCoefficientIndexL - predictorCount - constraintCount + 1,
								responseBasisCoefficientIndexI
							);
					} else if (responseBasisCoefficientIndexL <
						predictorCount + constraintCount + leftDerivativeCount + rightDerivativeCount) {
						responseBasisCoefficientConstraintMatrix[responseBasisCoefficientIndexL][responseBasisCoefficientIndexI] =
							_basisEvaluator.shapedBasisFunctionDerivative (
								right(),
								responseBasisCoefficientIndexL - predictorCount - constraintCount -
									leftDerivativeCount + 1,
								responseBasisCoefficientIndexI
							);
					} else {
						responseBasisCoefficientConstraintMatrix[responseBasisCoefficientIndexL][responseBasisCoefficientIndexI] =
							bestFitFlexurePenalizer.basisPairConstraintCoefficient (
								responseBasisCoefficientIndexI,
								responseBasisCoefficientIndexL
							);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

		LinearizationOutput linearizationOutput = LinearSystemSolver.SolveUsingMatrixInversion (
			responseBasisCoefficientConstraintMatrix,
			predictorResponseConstraintValueArray
		);

		if (null == linearizationOutput) {
			return false;
		}

		double[] calibrationResponseBasisCoefficientArray = linearizationOutput.getTransformedRHS();

		if (null == calibrationResponseBasisCoefficientArray ||
			calibrationResponseBasisCoefficientArray.length != responseBasisCoefficientCount ||
			null == (
				_responseBasisCoefficientToConstraintHessian = linearizationOutput.getTransformedMatrix()
			) ||
			_responseBasisCoefficientToConstraintHessian.length != responseBasisCoefficientCount ||
			_responseBasisCoefficientToConstraintHessian[0].length != responseBasisCoefficientCount) {
			return false;
		}

		for (int responseBasisCoefficientIndex = 0;
			responseBasisCoefficientIndex < responseBasisCoefficientCount;
			++responseBasisCoefficientIndex) {
			if (!NumberUtil.IsValid (
				_responseToBasisCoefficientSensitivity[responseBasisCoefficientIndex] =
				calibrationResponseBasisCoefficientArray[responseBasisCoefficientIndex])) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Sensitivity Calibrator: Calibrate the Segment Manifest Measure Jacobian from the Calibration Inputs
	 * 
	 * @param manifestSensitivitySegmentStateCalibrationInputs The Segment Manifest Calibration Sensitivity
	 *  Inputs
	 * @param stateSegmentBasisFlexureConstraintArray Array of Segment State Basis Flexure Constraints
	 * 
	 * @return The Manifest Sensitivity Coefficients
	 */

	public double[] calibrateManifestJacobian (
		final SegmentStateCalibrationInputs manifestSensitivitySegmentStateCalibrationInputs,
		final SegmentBasisFlexureConstraint[] stateSegmentBasisFlexureConstraintArray)
	{
		if (null == manifestSensitivitySegmentStateCalibrationInputs) {
			return null;
		}

		double[] predictorOrdinateArray =
			manifestSensitivitySegmentStateCalibrationInputs.predictorOrdinates();

		double[] responseValueManifestSensitivityArray =
			manifestSensitivitySegmentStateCalibrationInputs.responseValues();

		double[] leftEdgeDerivativeManifestSensitivityArray =
			manifestSensitivitySegmentStateCalibrationInputs.leftEdgeDeriv();

		double[] rightEdgeDerivativeManifestSensitivityArray =
			manifestSensitivitySegmentStateCalibrationInputs.rightEdgeDeriv();

		SegmentBasisFlexureConstraint[] manifestSensitivitySegmentBasisFlexureConstraintArray =
			manifestSensitivitySegmentStateCalibrationInputs.flexureConstraint();

		int constraintCount = 0;
		int responseBasisCoefficientCount = _responseToBasisCoefficientSensitivity.length;
		int predictorOrdinateCount = null == predictorOrdinateArray ? 0 : predictorOrdinateArray.length;
		double[] predictorResponseManifestSensitivityConstraintArray =
			new double[responseBasisCoefficientCount];
		int leftDerivativeManifestSensitivityCount = null == leftEdgeDerivativeManifestSensitivityArray ?
			0 : leftEdgeDerivativeManifestSensitivityArray.length;
		int rightDerivativeManifestSensitivityCount = null == rightEdgeDerivativeManifestSensitivityArray ?
			0 : rightEdgeDerivativeManifestSensitivityArray.length;
		double[][] responseCoefficientConstraintManifestSensitivityMatrix = new
			double[responseBasisCoefficientCount][responseBasisCoefficientCount];

		if (null != stateSegmentBasisFlexureConstraintArray) {
			int potentialConstraintCount = stateSegmentBasisFlexureConstraintArray.length;

			for (int potentialConstraintIndex = 0; potentialConstraintIndex < potentialConstraintCount;
				++potentialConstraintIndex) {
				if (null != stateSegmentBasisFlexureConstraintArray[potentialConstraintIndex]) {
					++constraintCount;
				}
			}
		}

		if (responseBasisCoefficientCount < predictorOrdinateCount + leftDerivativeManifestSensitivityCount +
			rightDerivativeManifestSensitivityCount + constraintCount) {
			return null;
		}

		try {
			BestFitFlexurePenalizer manifestSensitivityBestFitFlexurePenalizer =
				new BestFitFlexurePenalizer (
					this,
					null == _segmentInelasticDesignControl ?
						null : _segmentInelasticDesignControl.curvaturePenaltyControl(),
					null == _segmentInelasticDesignControl ?
						null : _segmentInelasticDesignControl.lengthPenaltyControl(),
					manifestSensitivitySegmentStateCalibrationInputs.bestFitResponse(),
					_basisEvaluator
				);

			for (int responseBasisCoefficientIndex = 0;
				responseBasisCoefficientIndex < responseBasisCoefficientCount;
				++responseBasisCoefficientIndex) {
				if (responseBasisCoefficientIndex < predictorOrdinateCount) {
					predictorResponseManifestSensitivityConstraintArray[responseBasisCoefficientIndex] =
						responseValueManifestSensitivityArray[responseBasisCoefficientIndex];
				} else if (responseBasisCoefficientIndex < predictorOrdinateCount + constraintCount) {
					predictorResponseManifestSensitivityConstraintArray[responseBasisCoefficientIndex] = 0.;
					SegmentBasisFlexureConstraint manifestSensitivitySegmentBasisFlexureConstraint =
						manifestSensitivitySegmentBasisFlexureConstraintArray[responseBasisCoefficientIndex - predictorOrdinateCount];

					if (null != manifestSensitivitySegmentBasisFlexureConstraint) {
						predictorResponseManifestSensitivityConstraintArray[responseBasisCoefficientIndex] =
							manifestSensitivitySegmentBasisFlexureConstraint.contraintValue();

						double[] calibrationConstraintWeightManifestSensitivityArray =
							manifestSensitivitySegmentBasisFlexureConstraint.responseBasisCoeffWeights();

						for (int i = 0; i < responseBasisCoefficientCount; ++i) {
							predictorResponseManifestSensitivityConstraintArray[responseBasisCoefficientIndex] -=
								_responseToBasisCoefficientSensitivity[i] *
								calibrationConstraintWeightManifestSensitivityArray[i];
						}
					}
				} else if (responseBasisCoefficientIndex <
					predictorOrdinateCount + constraintCount + leftDerivativeManifestSensitivityCount) {
					predictorResponseManifestSensitivityConstraintArray[responseBasisCoefficientIndex] =
						leftEdgeDerivativeManifestSensitivityArray[responseBasisCoefficientIndex - predictorOrdinateCount - constraintCount];
				} else if (responseBasisCoefficientIndex < predictorOrdinateCount + constraintCount +
					leftDerivativeManifestSensitivityCount + rightDerivativeManifestSensitivityCount) {
					predictorResponseManifestSensitivityConstraintArray[responseBasisCoefficientIndex] =
						rightEdgeDerivativeManifestSensitivityArray[responseBasisCoefficientIndex - predictorOrdinateCount - constraintCount - leftDerivativeManifestSensitivityCount];
				} else {
					predictorResponseManifestSensitivityConstraintArray[responseBasisCoefficientIndex] =
						manifestSensitivityBestFitFlexurePenalizer.basisPairPenaltyConstraint (
							responseBasisCoefficientIndex
						);
				}
			}

			for (int responseBasisCoefficientIndexI = 0;
				responseBasisCoefficientIndexI < responseBasisCoefficientCount;
				++responseBasisCoefficientIndexI) {
				for (int responseBasisCoefficientIndexL = 0;
					responseBasisCoefficientIndexL < responseBasisCoefficientCount;
					++responseBasisCoefficientIndexL) {
					double[] adblCalibBasisConstraintWeight = null;

					if (0 != constraintCount && (
						responseBasisCoefficientIndexL >= predictorOrdinateCount &&
						responseBasisCoefficientIndexL < predictorOrdinateCount + constraintCount
					)) {
						adblCalibBasisConstraintWeight =
							stateSegmentBasisFlexureConstraintArray[responseBasisCoefficientIndexL - predictorOrdinateCount].responseBasisCoeffWeights();
					}

					if (responseBasisCoefficientIndexL < predictorOrdinateCount) {
						responseCoefficientConstraintManifestSensitivityMatrix[responseBasisCoefficientIndexL][responseBasisCoefficientIndexI] =
							_basisEvaluator.shapedBasisFunctionResponse (predictorOrdinateArray[responseBasisCoefficientIndexL], responseBasisCoefficientIndexI);
					} else if (responseBasisCoefficientIndexL < predictorOrdinateCount + constraintCount) {
						responseCoefficientConstraintManifestSensitivityMatrix[responseBasisCoefficientIndexL][responseBasisCoefficientIndexI] =
							adblCalibBasisConstraintWeight[responseBasisCoefficientIndexI];
					} else if (responseBasisCoefficientIndexL <
						predictorOrdinateCount + constraintCount + leftDerivativeManifestSensitivityCount) {
						responseCoefficientConstraintManifestSensitivityMatrix[responseBasisCoefficientIndexL][responseBasisCoefficientIndexI] =
							_basisEvaluator.shapedBasisFunctionDerivative (
								left(),
								responseBasisCoefficientIndexL - predictorOrdinateCount - constraintCount + 1,
								responseBasisCoefficientIndexI
							);
					} else if (responseBasisCoefficientIndexL <
						predictorOrdinateCount + constraintCount + leftDerivativeManifestSensitivityCount +
							rightDerivativeManifestSensitivityCount) {
						responseCoefficientConstraintManifestSensitivityMatrix[responseBasisCoefficientIndexL][responseBasisCoefficientIndexI] =
							_basisEvaluator.shapedBasisFunctionDerivative (
								right(),
								responseBasisCoefficientIndexL - predictorOrdinateCount - constraintCount -
									leftDerivativeManifestSensitivityCount + 1,
								responseBasisCoefficientIndexI
							);
					} else {
						responseCoefficientConstraintManifestSensitivityMatrix[responseBasisCoefficientIndexL][responseBasisCoefficientIndexI] =
							manifestSensitivityBestFitFlexurePenalizer.basisPairConstraintCoefficient (
								responseBasisCoefficientIndexI,
								responseBasisCoefficientIndexL
							);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		LinearizationOutput linearizationOutput = LinearSystemSolver.SolveUsingMatrixInversion (
			responseCoefficientConstraintManifestSensitivityMatrix,
			predictorResponseManifestSensitivityConstraintArray
		);

		return null == linearizationOutput ? null : linearizationOutput.getTransformedRHS();
	}

	/**
	 * Sensitivity Calibrator: Calibrate the Segment Local Manifest Jacobian from the Calibration Parameter
	 * 	Set
	 * 
	 * @param manifestMeasure Latent State Manifest Measure
	 * @param manifestSensitivitySegmentStateCalibrationInputs The Segment Manifest Calibration Parameter
	 *  Sensitivity
	 * @param stateSegmentBasisFlexureConstraintArray Array of Segment State Basis Flexure Constraints
	 * 
	 * @return TRUE - Local Manifest Sensitivity Calibration Successful
	 */

	public boolean calibrateLocalManifestJacobian (
		final String manifestMeasure,
		final SegmentStateCalibrationInputs manifestSensitivitySegmentStateCalibrationInputs,
		final SegmentBasisFlexureConstraint[] stateSegmentBasisFlexureConstraintArray)
	{
		LatentStateManifestSensitivity latentStateManifestSensitivity = manifestSensitivity (
			manifestMeasure
		);

		if (null == latentStateManifestSensitivity) {
			return false;
		}

		double[] basisCoefficientToLocalManifestSensitivityArray = calibrateManifestJacobian (
			manifestSensitivitySegmentStateCalibrationInputs,
			stateSegmentBasisFlexureConstraintArray
		);

		return null != basisCoefficientToLocalManifestSensitivityArray &&
			basisCoefficientToLocalManifestSensitivityArray.length ==
				_responseToBasisCoefficientSensitivity.length &&
			latentStateManifestSensitivity.setDBasisCoeffDLocalManifest (
				basisCoefficientToLocalManifestSensitivityArray
			);
	}

	/**
	 * Sensitivity Calibrator: Calibrate the Segment Preceding Manifest Jacobian from the Calibration
	 *	Parameter Set
	 * 
	 * @param manifestMeasure Latent State Manifest
	 * @param precedingManifestSensitivitySegmentStateCalibrationInputs The Segment Preceding Manifest
	 *  Calibration Parameter Sensitivity
	 * 
	 * @return TRUE - Preceding Manifest Sensitivity Calibration Successful
	 */

	public boolean calibratePreceedingManifestJacobian (
		final String manifestMeasure,
		final SegmentStateCalibrationInputs precedingManifestSensitivitySegmentStateCalibrationInputs)
	{
		LatentStateManifestSensitivity latentStateManifestSensitivity = manifestSensitivity (
			manifestMeasure
		);

		if (null == latentStateManifestSensitivity) {
			return false;
		}

		double[] basisCoefficientToPrecedingManifestSensitivityArray = calibrateManifestJacobian (
			precedingManifestSensitivitySegmentStateCalibrationInputs,
			null
		);

		return null != basisCoefficientToPrecedingManifestSensitivityArray &&
			basisCoefficientToPrecedingManifestSensitivityArray.length ==
				_responseToBasisCoefficientSensitivity.length &&
			latentStateManifestSensitivity.setDBasisCoeffDPreceedingManifest (
				basisCoefficientToPrecedingManifestSensitivityArray
			);
	}

	/**
	 * Calibrate the coefficients from the prior Predictor/Response Segment, the Constraint, and fitness
	 * 	Weights
	 * 
	 * @param precedingLatentStateResponseModel Preceeding Predictor/Response Segment
	 * @param stateSegmentResponseValueConstraint The Segment State Response Value Constraint
	 * @param stateSegmentBestFitResponse Segment's Best Fit Weighted State Response Values
	 * 
	 * @return TRUE - If the calibration succeeds
	 */

	public boolean calibrate (
		final LatentStateResponseModel precedingLatentStateResponseModel,
		final SegmentResponseValueConstraint stateSegmentResponseValueConstraint,
		final SegmentBestFitResponse stateSegmentBestFitResponse)
	{
		int ck = _segmentInelasticDesignControl.Ck();

		SegmentBasisFlexureConstraint[] stateSegmentBasisFlexureConstraintArray =
			null == stateSegmentResponseValueConstraint ? null : new SegmentBasisFlexureConstraint[] {
				stateSegmentResponseValueConstraint.responseIndexedBasisConstraint (
					_basisEvaluator,
					this
				)
			};

		double[] manifestJacobianDerivativeAtLeftOrdinateArray = null;

		if (0 != ck) {
			manifestJacobianDerivativeAtLeftOrdinateArray = new double[ck];

			for (int ci = 0; ci < ck; ++ci) {
				manifestJacobianDerivativeAtLeftOrdinateArray[ci] = 0.;
			}
		}

		if (null == precedingLatentStateResponseModel) {
			try {
				double[] stateDerivationAtLeftOrdinateArray = null;

				if (0 != ck) {
					stateDerivationAtLeftOrdinateArray = new double[ck];

					for (int ci = 0; ci < ck; ++ci) {
						stateDerivationAtLeftOrdinateArray[ci] = _basisEvaluator.responseValueDerivative (
							_responseToBasisCoefficientSensitivity,
							left(),
							ci
						);
					}
				}

				return calibrateState (
					new SegmentStateCalibrationInputs (
						new double[] {left()},
						new double[] {
							_basisEvaluator.responseValue (_responseToBasisCoefficientSensitivity, left())
						},
						stateDerivationAtLeftOrdinateArray,
						null,
						stateSegmentBasisFlexureConstraintArray,
						stateSegmentBestFitResponse
					)
				);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return false;
		}

		try {
			return calibrateState (
				new SegmentStateCalibrationInputs (
					new double[] {left()},
					new double[] {precedingLatentStateResponseModel.responseValue (left())},
					0 == ck ? null : transmissionCk (left(), precedingLatentStateResponseModel, ck),
					null,
					stateSegmentBasisFlexureConstraintArray,
					stateSegmentBestFitResponse
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Calibrate the coefficients from the prior Segment and the Response Value at the Right Predictor
	 *  Ordinate
	 * 
	 * @param precedingLatentStateResponseModel Preceding Predictor/Response Segment
	 * @param rightStateValue Response Value at the Right Predictor Ordinate
	 * @param stateSegmentBestFitResponse Segment's Best Fit Weighted Response Values
	 * 
	 * @return TRUE - If the calibration succeeds
	 */

	public boolean calibrate (
		final LatentStateResponseModel precedingLatentStateResponseModel,
		final double rightStateValue,
		final SegmentBestFitResponse stateSegmentBestFitResponse)
	{
		if (null == precedingLatentStateResponseModel) {
			return false;
		}

		int ck = _segmentInelasticDesignControl.Ck();

		try {
			return calibrateState (
				new SegmentStateCalibrationInputs (
					new double[] {left(), right()},
					new double[] {precedingLatentStateResponseModel.responseValue (left()), rightStateValue},
					0 != ck ? precedingLatentStateResponseModel.transmissionCk (left(), this, ck) : null,
					null,
					null,
					stateSegmentBestFitResponse
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Calibrate the Coefficients from the Edge Response Values and the Left Edge Response Slope
	 * 
	 * @param dblLeftValue Left Edge Response Value
	 * @param dblLeftSlope Left Edge Response Slope
	 * @param dblRightValue Right Edge Response Value
	 * @param sbfrState Segment's Best Fit Weighted Response Values
	 * 
	 * @return TRUE - The Calibration Succeeded
	 */

	public boolean calibrate (
		final double dblLeftValue,
		final double dblLeftSlope,
		final double dblRightValue,
		final org.drip.spline.params.SegmentBestFitResponse sbfrState)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (dblLeftValue) ||
			!org.drip.numerical.common.NumberUtil.IsValid (dblLeftSlope) ||
				!org.drip.numerical.common.NumberUtil.IsValid (dblRightValue))
			return false;

		try {
			return calibrateState (new org.drip.spline.params.SegmentStateCalibrationInputs (new double[]
				{left(), right()}, new double[] {dblLeftValue, dblRightValue},
					org.drip.service.common.CollectionUtil.DerivArrayFromSlope (numParameters() - 2,
						dblLeftSlope), null, null, sbfrState));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Calibrate the coefficients from the Left Edge Response Value Constraint, the Left Edge Response Value
	 *  Slope, and the Right Edge Response Value Constraint
	 * 
	 * @param wrvcStateLeft Left Edge Response Value Constraint
	 * @param dblLeftSlope Left Edge Response Value Slope
	 * @param wrvcStateRight Right Edge Response Value Constraint
	 * @param sbfrState Segment's Best Fit Weighted Response
	 * 
	 * @return TRUE - If the calibration succeeds
	 */

	public boolean calibrate (
		final org.drip.spline.params.SegmentResponseValueConstraint wrvcStateLeft,
		final double dblLeftSlope,
		final org.drip.spline.params.SegmentResponseValueConstraint wrvcStateRight,
		final org.drip.spline.params.SegmentBestFitResponse sbfrState)
	{
		org.drip.spline.params.SegmentBasisFlexureConstraint[] aSBFCState = null;

		try {
			if (null != wrvcStateLeft || null != wrvcStateRight)
				aSBFCState = new org.drip.spline.params.SegmentBasisFlexureConstraint[] {null ==
					wrvcStateLeft ? null : wrvcStateLeft.responseIndexedBasisConstraint (_basisEvaluator, this), null ==
						wrvcStateRight ? null : wrvcStateRight.responseIndexedBasisConstraint (_basisEvaluator, this)};

			return calibrateState (new org.drip.spline.params.SegmentStateCalibrationInputs (null, null,
				org.drip.service.common.CollectionUtil.DerivArrayFromSlope (numParameters() - 2, dblLeftSlope),
					null, aSBFCState, sbfrState));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Compute the Local and the Preceeding Manifest Measure Sensitivity Coefficients from the Preceeding
	 * 	Segment, the Local Response Value, the Local Response Value Manifest Measure Sensitivity, and the
	 * 	Local Best Fit Response Sensitivity
	 * 
	 * @param csPreceeding Preceeding Predictor/Response Segment
	 * @param strManifestMeasure Manifest Measure whose Sensitivity is sought
	 * @param srvcState The Segment State Response Value Constraint
	 * @param srvcManifestSensitivity The Segment State Response Value Constraint Manifest Sensitivity
	 * @param sbfrManifestSensitivity Segment's Best Fit Weighted State Response Value Manifest Sensitivity
	 * 
	 * @return TRUE - If the calibration succeeds
	 */

	public boolean manifestMeasureSensitivity (
		final org.drip.spline.segment.LatentStateResponseModel csPreceeding,
		final java.lang.String strManifestMeasure,
		final org.drip.spline.params.SegmentResponseValueConstraint srvcState,
		final org.drip.spline.params.SegmentResponseValueConstraint srvcManifestSensitivity,
		final org.drip.spline.params.SegmentBestFitResponse sbfrManifestSensitivity)
	{
		if (null == srvcState && null != srvcManifestSensitivity) return false;

		org.drip.spline.params.SegmentBasisFlexureConstraint[] aSBFCState = null == srvcState ? null : new
			org.drip.spline.params.SegmentBasisFlexureConstraint[] {srvcState.responseIndexedBasisConstraint
				(_basisEvaluator, this)};

		org.drip.spline.params.SegmentBasisFlexureConstraint[] aSBFCManifestSensitivity = null ==
			srvcManifestSensitivity ? null : new org.drip.spline.params.SegmentBasisFlexureConstraint[]
				{srvcManifestSensitivity.responseIndexedBasisConstraint (_basisEvaluator, this)};

		double[] adblManifestJacobianDerivAtLeftOrdinate = null;

		int iCk = _segmentInelasticDesignControl.Ck();

		if (0 != iCk) {
			adblManifestJacobianDerivAtLeftOrdinate = new double[iCk];

			for (int i = 0; i < iCk; ++i)
				adblManifestJacobianDerivAtLeftOrdinate[i] = 0.;
		}

		if (null == csPreceeding) return false;

		try {
			if (null == aSBFCManifestSensitivity) return true;

			if (!calibrateLocalManifestJacobian (strManifestMeasure, new
				org.drip.spline.params.SegmentStateCalibrationInputs (new double[] {left()}, new double[]
					{0.}, adblManifestJacobianDerivAtLeftOrdinate, null, aSBFCManifestSensitivity,
						sbfrManifestSensitivity), aSBFCState))
				return false;

			org.drip.spline.segment.LatentStateManifestSensitivity lsms = manifestSensitivity
				(strManifestMeasure);

			if (null == lsms) return true;

			return lsms.getPMSC().impactFade() ? calibratePreceedingManifestJacobian (strManifestMeasure, new
				org.drip.spline.params.SegmentStateCalibrationInputs (new double[] {left(), right()}, new
					double[] {csPreceeding.calcDResponseDManifest (strManifestMeasure, left(), 1), 0.}, null,
						CkDBasisCoeffDPreceedingManifestMeasure (strManifestMeasure), null, null)) :
							lsms.setDResponseDPreceedingManifest (csPreceeding.calcDResponseDManifest
								(strManifestMeasure, left(), 1));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Compute the Local and the Preceeding Manifest Measure Sensitivity Coefficients from the Preceeding
	 * 	Segments, the Local Response Value Sensitivity at the Right Predictor Ordinate, and the Local Best
	 * 	Fit Response Sensitivity
	 * 
	 * @param csPreceeding Preceeding Predictor/Response Segment
	 * @param strManifestMeasure Manifest Measure whose Sensitivity is sought
	 * @param dblRightStateManifestSensitivity Response Value Manifest Sensitivity at the Right Predictor
	 * 	Ordinate
	 * @param sbfrManifestSensitivity Segment's Best Fit Weighted Response Value Manifest Sensitivity
	 * 
	 * @return TRUE - If the calibration succeeds
	 */

	public boolean manifestMeasureSensitivity (
		final LatentStateResponseModel csPreceeding,
		final java.lang.String strManifestMeasure,
		final double dblRightStateManifestSensitivity,
		final org.drip.spline.params.SegmentBestFitResponse sbfrManifestSensitivity)
	{
		if (null == csPreceeding) return false;

		int iCk = _segmentInelasticDesignControl.Ck();

		try {
			double[] adblManifestJacobianDerivAtLeftOrdinate = null;

			if (0 != iCk) {
				adblManifestJacobianDerivAtLeftOrdinate = new double[iCk];

				for (int i = 0; i < iCk; ++i)
					adblManifestJacobianDerivAtLeftOrdinate[i] = 0.;
			}

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblRightStateManifestSensitivity)) return true;

			if (!calibrateLocalManifestJacobian (strManifestMeasure, new
				org.drip.spline.params.SegmentStateCalibrationInputs (new double[] {left(), right()}, new
					double[] {0., dblRightStateManifestSensitivity}, 0 != iCk ?
						adblManifestJacobianDerivAtLeftOrdinate : null, null, null, sbfrManifestSensitivity),
							null))
				return false;

			org.drip.spline.segment.LatentStateManifestSensitivity lsms = manifestSensitivity
				(strManifestMeasure);

			if (null == lsms) return true;

			return lsms.getPMSC().impactFade() ? calibratePreceedingManifestJacobian (strManifestMeasure, new
				org.drip.spline.params.SegmentStateCalibrationInputs (new double[] {left(), right()}, new
					double[] {csPreceeding.calcDResponseDManifest (strManifestMeasure, left(), 1), 0.}, null,
						CkDBasisCoeffDPreceedingManifestMeasure (strManifestMeasure), null, null)) :
							lsms.setDResponseDPreceedingManifest (csPreceeding.calcDResponseDManifest
								(strManifestMeasure, left(), 1));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Compute the Local and the Preceeding Manifest Measure Sensitivity Coefficients from the Local
	 * 	Response Value Sensitivity at the Left/Right Predictor Ordinate, the Local Left Response Value
	 * 	Sensitivity Slope, and the Local Best Fit Response Sensitivity.
	 * 
	 * @param strManifestMeasure Manifest Measure whose Sensitivity is sought
	 * @param dblLeftManifestSensitivity Left Edge Response Value Manifest Sensitivity
	 * @param dblLeftSlopeManifestSensitivity Left Edge Response Slope Manifest Sensitivity
	 * @param dblRightManifestSensitivity Right Edge Response Value Manifest Sensitivity
	 * @param sbfrManifestSensitivity Segment's Best Fit Weighted Response Values Manifest Sensitivity
	 * 
	 * @return TRUE - The Calibration Succeeded
	 */

	public boolean manifestMeasureSensitivity (
		final java.lang.String strManifestMeasure,
		final double dblLeftManifestSensitivity,
		final double dblLeftSlopeManifestSensitivity,
		final double dblRightManifestSensitivity,
		final org.drip.spline.params.SegmentBestFitResponse sbfrManifestSensitivity)
	{
		try {
			return org.drip.numerical.common.NumberUtil.IsValid (dblLeftManifestSensitivity) &&
				org.drip.numerical.common.NumberUtil.IsValid (dblLeftSlopeManifestSensitivity) &&
					org.drip.numerical.common.NumberUtil.IsValid (dblRightManifestSensitivity) ?
						calibrateLocalManifestJacobian (strManifestMeasure, new
							org.drip.spline.params.SegmentStateCalibrationInputs (new double[] {left(),
								right()}, new double[] {dblLeftManifestSensitivity,
									dblRightManifestSensitivity},
										org.drip.service.common.CollectionUtil.DerivArrayFromSlope
											(numParameters() - 2, dblLeftSlopeManifestSensitivity), null,
												null, sbfrManifestSensitivity), null) : true;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Compute the Local and the Preceeding Manifest Measure Sensitivity Coefficients from the Local
	 * 	Response Value/Sensitivity Constraints at the Left/Right Predictor Ordinate, the Local Left
	 * 	Response Value Sensitivity Slope, and the Local Best Fit Response Sensitivity
	 * 
	 * @param strManifestMeasure Manifest Measure whose Sensitivity is sought
	 * @param wrvcStateLeft Left Edge Response Value Constraint
	 * @param wrvcStateRight Right Edge Response Value Constraint
	 * @param dblLeftSlopeManifestSensitivity Left Edge Response Value Slope Manifest Sensitivity
	 * @param wrvcStateLeftManifestSensitivity Left Edge Response Value Constraint Manifest Sensitivity
	 * @param wrvcStateRightManifestSensitivity Right Edge Response Value Constraint Manifest Sensitivity
	 * @param sbfrManifestSensitivity Segment's Best Fit Weighted Response Manifest Sensitivity
	 * 
	 * @return TRUE - If the calibration succeeds
	 */

	public boolean manifestMeasureSensitivity (
		final java.lang.String strManifestMeasure,
		final org.drip.spline.params.SegmentResponseValueConstraint wrvcStateLeft,
		final org.drip.spline.params.SegmentResponseValueConstraint wrvcStateRight,
		final double dblLeftSlopeManifestSensitivity,
		final org.drip.spline.params.SegmentResponseValueConstraint wrvcStateLeftManifestSensitivity,
		final org.drip.spline.params.SegmentResponseValueConstraint wrvcStateRightManifestSensitivity,
		final org.drip.spline.params.SegmentBestFitResponse sbfrManifestSensitivity)
	{
		org.drip.spline.params.SegmentBasisFlexureConstraint[] aSBFCState = null;
		org.drip.spline.params.SegmentBasisFlexureConstraint[] aSBFCManifestSensitivity = null;

		try {
			if (null != wrvcStateLeft || null != wrvcStateRight)
				aSBFCState = new org.drip.spline.params.SegmentBasisFlexureConstraint[] {null ==
					wrvcStateLeft ? null : wrvcStateLeft.responseIndexedBasisConstraint (_basisEvaluator, this), null ==
						wrvcStateRight ? null : wrvcStateRight.responseIndexedBasisConstraint (_basisEvaluator, this)};

			if (null != wrvcStateLeftManifestSensitivity || null != wrvcStateRightManifestSensitivity)
				aSBFCManifestSensitivity = new org.drip.spline.params.SegmentBasisFlexureConstraint[] {null
					== wrvcStateLeftManifestSensitivity ? null :
						wrvcStateLeftManifestSensitivity.responseIndexedBasisConstraint (_basisEvaluator, this), null ==
							wrvcStateRightManifestSensitivity ? null :
								wrvcStateRightManifestSensitivity.responseIndexedBasisConstraint (_basisEvaluator,
									this)};

			return null == aSBFCManifestSensitivity ? true : calibrateLocalManifestJacobian
				(strManifestMeasure, new org.drip.spline.params.SegmentStateCalibrationInputs (null, null,
					org.drip.service.common.CollectionUtil.DerivArrayFromSlope (numParameters() - 2,
						dblLeftSlopeManifestSensitivity), null, aSBFCManifestSensitivity,
							sbfrManifestSensitivity), aSBFCState);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Retrieve the Segment Curvature DPE
	 * 
	 * @return The Segment Curvature DPE
	 * 
	 * @throws java.lang.Exception Thrown if the Segment Curvature DPE cannot be computed
	 */

	public double curvatureDPE()
		throws java.lang.Exception
	{
		double dblDPE = 0.;

		int iNumBasis = _basisEvaluator.numBasis();

		org.drip.spline.params.SegmentFlexurePenaltyControl sfpc = _segmentInelasticDesignControl.curvaturePenaltyControl();

		if (null == sfpc) sfpc = new org.drip.spline.params.SegmentFlexurePenaltyControl (2, 1.);

		org.drip.spline.segment.BestFitFlexurePenalizer bffp = new
			org.drip.spline.segment.BestFitFlexurePenalizer (this, sfpc, null, null, _basisEvaluator);

		for (int i = 0; i < iNumBasis; ++i) {
			for (int j = 0; j < iNumBasis; ++j)
				dblDPE += _responseToBasisCoefficientSensitivity[i] * _responseToBasisCoefficientSensitivity[j] *
					bffp.basisPairCurvaturePenalty (i, j);
		}

		return sfpc.amplitude() * dblDPE;
	}

	/**
	 * Retrieve the Segment Length DPE
	 * 
	 * @return The Segment Length DPE
	 * 
	 * @throws java.lang.Exception Thrown if the Segment Length DPE cannot be computed
	 */

	public double lengthDPE()
		throws java.lang.Exception
	{
		double dblDPE = 0.;

		int iNumBasis = _basisEvaluator.numBasis();

		org.drip.spline.params.SegmentFlexurePenaltyControl sfpcLength = _segmentInelasticDesignControl.lengthPenaltyControl();

		if (null == sfpcLength) sfpcLength = new org.drip.spline.params.SegmentFlexurePenaltyControl (1, 1.);

		org.drip.spline.segment.BestFitFlexurePenalizer bffp = new
			org.drip.spline.segment.BestFitFlexurePenalizer (this, null, sfpcLength, null, _basisEvaluator);

		for (int i = 0; i < iNumBasis; ++i) {
			for (int j = 0; j < iNumBasis; ++j)
				dblDPE += _responseToBasisCoefficientSensitivity[i] * _responseToBasisCoefficientSensitivity[j] *
					bffp.basisPairLengthPenalty (i, j);
		}

		return sfpcLength.amplitude() * dblDPE;
	}

	/**
	 * Retrieve the Segment Best Fit DPE
	 * 
	 * @param sbfr The Segment's Best Fit Response Inputs
	 * 
	 * @return The Segment Best Fit DPE
	 * 
	 * @throws java.lang.Exception Thrown if the Segment Best Fit DPE cannot be computed
	 */

	public double bestFitDPE (
		final org.drip.spline.params.SegmentBestFitResponse sbfr)
		throws java.lang.Exception
	{
		if (null == sbfr) return 0.;

		double dblDPE = 0.;

		int iNumBasis = _basisEvaluator.numBasis();

		org.drip.spline.segment.BestFitFlexurePenalizer bffp = new
			org.drip.spline.segment.BestFitFlexurePenalizer (this, null, null, sbfr, _basisEvaluator);

		for (int i = 0; i < iNumBasis; ++i) {
			for (int j = 0; j < iNumBasis; ++j)
				dblDPE += _responseToBasisCoefficientSensitivity[i] * _responseToBasisCoefficientSensitivity[j] * bffp.basisBestFitPenalty
					(i, j);
		}

		return dblDPE;
	}

	/**
	 * Calculate the Response Value at the given Predictor Ordinate
	 * 
	 * @param dblPredictorOrdinate Predictor Ordinate
	 * 
	 * @return The Response Value
	 * 
	 * @throws java.lang.Exception Thrown if the calculation did not succeed
	 */

	public double responseValue (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		return _basisEvaluator.responseValue (_responseToBasisCoefficientSensitivity, dblPredictorOrdinate);
	}

	/**
	 * Calculate the Ordered Response Value Derivative at the Predictor Ordinate
	 * 
	 * @param dblPredictorOrdinate Predictor Ordinate at which the ordered Response Derivative is to be
	 * 	calculated
	 * @param iOrder Derivative Order
	 * 
	 * @throws java.lang.Exception Thrown if the Ordered Response Value Derivative cannot be calculated
	 * 
	 * @return Retrieve the Ordered Response Value Derivative
	 */

	public double calcResponseValueDerivative (
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		return 0 == iOrder ? responseValue (dblPredictorOrdinate) : _basisEvaluator.responseValueDerivative
			(_responseToBasisCoefficientSensitivity, dblPredictorOrdinate, iOrder);
	}

	/**
	 * Calculate the Ordered Derivative of the Response to the Manifest
	 * 
	 * @param strManifestMeasure Manifest Measure whose Sensitivity is sought
	 * @param dblPredictorOrdinate Predictor Ordinate at which the ordered Derivative of the Response to the
	 * 	Manifest is to be calculated
	 * @param iOrder Derivative Order
	 * 
	 * @throws java.lang.Exception Thrown if the Ordered Derivative of the Response to the Manifest cannot be
	 *  calculated
	 * 
	 * @return Retrieve the Ordered Derivative of the Response to the Manifest
	 */

	public double calcDResponseDManifest (
		final java.lang.String strManifestMeasure,
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (0 == iOrder)
			throw new java.lang.Exception
				("LatentStateResponseModel::calcDResponseDManifest => Invalid Inputs");

		org.drip.spline.segment.LatentStateManifestSensitivity lsms = manifestSensitivity
			(strManifestMeasure);

		if (null == lsms)
			throw new java.lang.Exception
				("LatentStateResponseModel::calcDResponseDManifest => Invalid Inputs");

		return _basisEvaluator.responseValue (lsms.getDBasisCoeffDLocalManifest(), dblPredictorOrdinate);
	}

	/**
	 * Calculate the Ordered Derivative of the Response to the Preceeding Manifest
	 * 
	 * @param strManifestMeasure Manifest Measure whose Sensitivity is sought
	 * @param dblPredictorOrdinate Predictor Ordinate at which the ordered Derivative of the Response to the
	 * 	Manifest is to be calculated
	 * @param iOrder Derivative Order
	 * 
	 * @throws java.lang.Exception Thrown if the Ordered Derivative of the Response to the Manifest cannot be
	 *  calculated
	 * 
	 * @return Retrieve the Ordered Derivative of the Response to the Preceeding Manifest
	 */

	public double calcDResponseDPreceedingManifest (
		final java.lang.String strManifestMeasure,
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (0 == iOrder)
			throw new java.lang.Exception
				("LatentStateResponseModel::calcDResponseDPreceedingManifest => Invalid Inputs");

		org.drip.spline.segment.LatentStateManifestSensitivity lsms = manifestSensitivity
			(strManifestMeasure);

		if (null == lsms)
			throw new java.lang.Exception
				("LatentStateResponseModel::calcDResponseDPreceedingManifest => Cannot locate state Manifest sensitivity");

		org.drip.spline.params.PreceedingManifestSensitivityControl pqsc = lsms.getPMSC();

		double dblDResponseDPreceedingManifest = lsms.getDResponseDPreceedingManifest();

		if (!pqsc.impactFade())
			return org.drip.numerical.common.NumberUtil.IsValid (dblDResponseDPreceedingManifest) ?
				dblDResponseDPreceedingManifest : 0.;

		org.drip.spline.segment.BasisEvaluator be = pqsc.basisEvaluator();

		double[] adblDBasisCoeffDPreceedingManifest = lsms.getDBasisCoeffDPreceedingManifest();

		return null == adblDBasisCoeffDPreceedingManifest ? 0. : (null == be ? _basisEvaluator : be).responseValue
			(adblDBasisCoeffDPreceedingManifest, dblPredictorOrdinate);
	}

	/**
	 * Retrieve the Manifest Measure Preceeding Manifest Impact Flag
	 * 
	 * @param strManifestMeasure Manifest Measure whose Sensitivity is sought
	 * 
	 * @return The Manifest Measure Preceeding Manifest Impact Flag
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public boolean impactFade (
		final java.lang.String strManifestMeasure)
		throws java.lang.Exception
	{
		org.drip.spline.segment.LatentStateManifestSensitivity lsms = manifestSensitivity
			(strManifestMeasure);

		if (null == lsms)
			throw new java.lang.Exception
				("LatentStateResponseModel::impactFade => Cannot locate state Manifest sensitivity");

		return lsms.getPMSC().impactFade();
	}

	/**
	 * Calculate the Jacobian of the Segment's Response Basis Function Coefficients to the Edge Inputs
	 * 
	 * @return The Jacobian of the Segment's Response Basis Function Coefficients to the Edge Inputs
	 */

	public org.drip.numerical.differentiation.WengertJacobian jackDCoeffDEdgeInputs()
	{
		if (null != _basisCoefficientToEdgeValueWengertJacobian) return _basisCoefficientToEdgeValueWengertJacobian;

		int iNumResponseBasisCoeff = _basisEvaluator.numBasis();

		try {
			_basisCoefficientToEdgeValueWengertJacobian = new org.drip.numerical.differentiation.WengertJacobian (iNumResponseBasisCoeff,
				iNumResponseBasisCoeff);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return _basisCoefficientToEdgeValueWengertJacobian = null;
		}

		for (int i = 0; i < iNumResponseBasisCoeff; ++i) {
			if (!_basisCoefficientToEdgeValueWengertJacobian.setWengert (i, _responseToBasisCoefficientSensitivity[i]))
				return _basisCoefficientToEdgeValueWengertJacobian = null;
		}

		if (null == _responseBasisCoefficientToConstraintHessian) return null;

		int iSize = _responseBasisCoefficientToConstraintHessian.length;

		for (int i = 0; i < iSize; ++i) {
			for (int j = 0; j < iSize; ++j) {
				if (!_basisCoefficientToEdgeValueWengertJacobian.accumulatePartialFirstDerivative (i, j,
						_responseBasisCoefficientToConstraintHessian[i][j]))
					return null;
			}
		}

		return _basisCoefficientToEdgeValueWengertJacobian;
	}

	/**
	 * Calculate the Jacobian of the Response to the Edge Inputs at the given Predictor Ordinate
	 * 
	 * @param dblPredictorOrdinate The Predictor Ordinate
	 * @param iOrder Order of the Derivative Desired
	 * 
	 * @return The Jacobian of the Response to the Edge Inputs at the given Predictor Ordinate
	 */

	public org.drip.numerical.differentiation.WengertJacobian jackDResponseDEdgeInput (
		final double dblPredictorOrdinate,
		final int iOrder)
	{
		try {
			int iNumResponseBasisCoeff = _basisEvaluator.numBasis();

			org.drip.numerical.differentiation.WengertJacobian wjDResponseDEdgeParams = null;
			double[][] aadblDBasisCoeffDEdgeParams = new
				double[iNumResponseBasisCoeff][iNumResponseBasisCoeff];

			double[] adblDResponseDBasisCoeff = DResponseDBasisCoeff (dblPredictorOrdinate, iOrder);

			if (null == adblDResponseDBasisCoeff || iNumResponseBasisCoeff !=
				adblDResponseDBasisCoeff.length)
				return null;

			org.drip.numerical.differentiation.WengertJacobian wjDBasisCoeffDEdgeParams = (null ==
				_basisCoefficientToEdgeValueWengertJacobian) ? jackDCoeffDEdgeInputs() : _basisCoefficientToEdgeValueWengertJacobian;

			for (int i = 0; i < iNumResponseBasisCoeff; ++i) {
				for (int j = 0; j < iNumResponseBasisCoeff; ++j)
					aadblDBasisCoeffDEdgeParams[j][i] = wjDBasisCoeffDEdgeParams.firstDerivative (j, i);
			}

			if (!(wjDResponseDEdgeParams = new org.drip.numerical.differentiation.WengertJacobian (1,
				iNumResponseBasisCoeff)).setWengert (0, responseValue (dblPredictorOrdinate)))
				return null;

			for (int i = 0; i < iNumResponseBasisCoeff; ++i) {
				for (int j = 0; j < iNumResponseBasisCoeff; ++j) {
					if (!wjDResponseDEdgeParams.accumulatePartialFirstDerivative (0, i,
						adblDResponseDBasisCoeff[j] * aadblDBasisCoeffDEdgeParams[j][i]))
						return null;
				}
			}

			return wjDResponseDEdgeParams;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Calculate the Jacobian of the Response to the Basis Coefficients at the given Predictor Ordinate
	 * 
	 * @param dblPredictorOrdinate The Predictor Ordinate
	 * @param iOrder Order of the Derivative Desired
	 * 
	 * @return The Jacobian of the Response to the Basis Coefficients at the given Predictor Ordinate
	 */

	public org.drip.numerical.differentiation.WengertJacobian jackDResponseDBasisCoeff (
		final double dblPredictorOrdinate,
		final int iOrder)
	{
		try {
			int iNumResponseBasisCoeff = _basisEvaluator.numBasis();

			double[] adblBasisDResponseDBasisCoeff = DResponseDBasisCoeff (dblPredictorOrdinate, iOrder);

			if (null == adblBasisDResponseDBasisCoeff || iNumResponseBasisCoeff !=
				adblBasisDResponseDBasisCoeff.length)
				return null;

			org.drip.numerical.differentiation.WengertJacobian wjDResponseDBasisCoeff = new
				org.drip.numerical.differentiation.WengertJacobian (1, iNumResponseBasisCoeff);

			for (int i = 0; i < iNumResponseBasisCoeff; ++i) {
				if (!wjDResponseDBasisCoeff.accumulatePartialFirstDerivative (0, i,
					adblBasisDResponseDBasisCoeff[i]))
					return null;
			}

			return wjDResponseDBasisCoeff;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Calibrate the segment and calculate the Jacobian of the Segment's Response Basis Function Coefficients
	 *  to the Edge Parameters
	 * 
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param adblLeftEdgeDeriv Array of Left Edge Derivatives
	 * @param adblRightEdgeDeriv Array of Right Edge Derivatives
	 * @param aSBFC Array of Segment Flexure Constraints, expressed as Basis Coefficients
	 * @param sbfr Segment Best Fit Response Instance
	 * 
	 * @return The Jacobian of the Segment's Response Basis Function Coefficients to the Edge Parameters
	 */

	public org.drip.numerical.differentiation.WengertJacobian jackDCoeffDEdgeParams (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final double[] adblLeftEdgeDeriv,
		final double[] adblRightEdgeDeriv,
		final org.drip.spline.params.SegmentBasisFlexureConstraint[] aSBFC,
		final org.drip.spline.params.SegmentBestFitResponse sbfr)
	{
		try {
			return calibrateState (new org.drip.spline.params.SegmentStateCalibrationInputs
				(adblPredictorOrdinate, adblResponseValue, adblLeftEdgeDeriv, adblRightEdgeDeriv, aSBFC,
					sbfr)) ? jackDCoeffDEdgeInputs() : null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Calibrate the Coefficients from the Edge Response Values and the Left Edge Response Value Slope and
	 *  calculate the Jacobian of the Segment's Response Basis Function Coefficients to the Edge Parameters
	 * 
	 * @param dblLeftValue Left Edge Response Value
	 * @param dblLeftSlope Left Edge Response Slope
	 * @param dblRightValue Right Edge Response Value
	 * @param sbfrState Segment's Best Fit Weighted Response Values
	 * 
	 * @return The Jacobian of the Segment's Response Basis Function Coefficients to the Edge Parameters
	 */

	public org.drip.numerical.differentiation.WengertJacobian jackDCoeffDEdgeParams (
		final double dblLeftValue,
		final double dblLeftSlope,
		final double dblRightValue,
		final org.drip.spline.params.SegmentBestFitResponse sbfrState)
	{
		return calibrate (dblLeftValue, dblLeftSlope, dblRightValue, sbfrState) ? jackDCoeffDEdgeInputs() :
			null;
	}

	/**
	 * Calibrate the coefficients from the prior Segment and the Response Value at the Right Predictor
	 *  Ordinate and calculate the Jacobian of the Segment's Response Basis Function Coefficients to the Edge
	 *  Parameters
	 * 
	 * @param csPreceeding Previous Predictor/Response Segment
	 * @param strManifestMeasure Manifest Measure whose Sensitivity is sought
	 * @param dblRightStateValue Response Value at the Right Predictor Ordinate
	 * @param sbfrState Segment's Best Fit Weighted Response Values
	 * @param dblRightStateManifestSensitivity Response Value Manifest Sensitivity at the Right Predictor
	 * 	Ordinate
	 * @param sbfrManifestSensitivity Segment's Best Fit Weighted Response Value Manifest Sensitivity
	 * 
	 * @return The Jacobian
	 */

	public org.drip.numerical.differentiation.WengertJacobian jackDCoeffDEdgeParams (
		final LatentStateResponseModel csPreceeding,
		final java.lang.String strManifestMeasure,
		final double dblRightStateValue,
		final org.drip.spline.params.SegmentBestFitResponse sbfrState,
		final double dblRightStateManifestSensitivity,
		final org.drip.spline.params.SegmentBestFitResponse sbfrManifestSensitivity)
	{
		return !calibrate (csPreceeding, dblRightStateValue, sbfrState) || !manifestMeasureSensitivity
			(csPreceeding, strManifestMeasure, dblRightStateManifestSensitivity, sbfrManifestSensitivity) ?
				null : jackDCoeffDEdgeInputs();
	}

	/**
	 * Indicate whether the given segment is monotone. If monotone, may optionally indicate the nature of
	 * 	the extrema contained inside (maxima/minima/infection).
	 *  
	 * @return The monotone Type
	 */

	public org.drip.spline.segment.Monotonocity monotoneType()
	{
		if (1 >= _segmentInelasticDesignControl.Ck()) {
			try {
				return new org.drip.spline.segment.Monotonocity
					(org.drip.spline.segment.Monotonocity.MONOTONIC);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		org.drip.function.definition.R1ToR1 ofDeriv = new
			org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblX)
				throws java.lang.Exception
			{
				return _basisEvaluator.responseValueDerivative (_responseToBasisCoefficientSensitivity, dblX, 1);
			}

			@Override public org.drip.numerical.differentiation.Differential differential (
				final double dblX,
				final double dblOFBase,
				final int iOrder)
			{
				try {
					double dblVariateInfinitesimal = _dc.getVariateInfinitesimal (dblX);

					return new org.drip.numerical.differentiation.Differential (dblVariateInfinitesimal,
						_basisEvaluator.responseValueDerivative (_responseToBasisCoefficientSensitivity, dblX, iOrder) *
							dblVariateInfinitesimal);
				} catch (java.lang.Exception e) {
					e.printStackTrace();
				}

				return null;
			}

			@Override public double integrate (
				final double dblBegin,
				final double dblEnd)
				throws java.lang.Exception
			{
				return org.drip.numerical.integration.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
			}
		};

		try {
			org.drip.function.r1tor1solver.FixedPointFinderOutput fpop = new
				org.drip.function.r1tor1solver.FixedPointFinderBrent (0., ofDeriv, false).findRoot
					(org.drip.function.r1tor1solver.InitializationHeuristics.FromHardSearchEdges (0., 1.));

			if (null == fpop || !fpop.containsRoot())
				return new org.drip.spline.segment.Monotonocity
					(org.drip.spline.segment.Monotonocity.MONOTONIC);

			double dblExtremum = fpop.getRoot();

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblExtremum) || dblExtremum <= 0. || dblExtremum
				>= 1.)
				return new org.drip.spline.segment.Monotonocity
					(org.drip.spline.segment.Monotonocity.MONOTONIC);

			double dbl2ndDeriv = _basisEvaluator.responseValueDerivative (_responseToBasisCoefficientSensitivity, dblExtremum, 2);

			if (0. > dbl2ndDeriv)
				return new org.drip.spline.segment.Monotonocity
					(org.drip.spline.segment.Monotonocity.MAXIMA);

			if (0. < dbl2ndDeriv)
				return new org.drip.spline.segment.Monotonocity
					(org.drip.spline.segment.Monotonocity.MINIMA);

			if (0. == dbl2ndDeriv)
				return new org.drip.spline.segment.Monotonocity
					(org.drip.spline.segment.Monotonocity.INFLECTION);

			return new org.drip.spline.segment.Monotonocity
				(org.drip.spline.segment.Monotonocity.NON_MONOTONIC);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		try {
			return new org.drip.spline.segment.Monotonocity (org.drip.spline.segment.Monotonocity.MONOTONIC);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Clip the part of the Segment to the Right of the specified Predictor Ordinate. Retain all other
	 * 	constraints the same.
	 * 
	 * @param dblPredictorOrdinate The Predictor Ordinate
	 * 
	 * @return The Clipped Segment
	 */

	public LatentStateResponseModel clipLeftOfPredictorOrdinate (
		final double dblPredictorOrdinate)
	{
		try {
			LatentStateResponseModel csLeftSnipped = LatentStateResponseModel.Create (dblPredictorOrdinate,
				right(), _basisEvaluator.replicate(), _segmentInelasticDesignControl);

			int iCk = _segmentInelasticDesignControl.Ck();

			double[] adblCalibLeftEdgeDeriv = 0 != iCk ? csLeftSnipped.transmissionCk (dblPredictorOrdinate,
				this, iCk) : null;

			return csLeftSnipped.calibrateState (new org.drip.spline.params.SegmentStateCalibrationInputs
				(new double[] {dblPredictorOrdinate, right()}, new double[] {responseValue
					(dblPredictorOrdinate), responseValue (right())}, adblCalibLeftEdgeDeriv, null, null,
						null)) ? csLeftSnipped : null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Clip the part of the Segment to the Right of the specified Predictor Ordinate. Retain all other
	 * 	constraints the same.
	 * 
	 * @param dblPredictorOrdinate The Predictor Ordinate
	 * 
	 * @return The Clipped Segment
	 */

	public LatentStateResponseModel clipRightOfPredictorOrdinate (
		final double dblPredictorOrdinate)
	{
		try {
			LatentStateResponseModel csRightSnipped = LatentStateResponseModel.Create (left(),
				dblPredictorOrdinate, _basisEvaluator.replicate(), _segmentInelasticDesignControl);

			int iCk = _segmentInelasticDesignControl.Ck();

			return csRightSnipped.calibrateState (new org.drip.spline.params.SegmentStateCalibrationInputs
				(new double[] {left(), dblPredictorOrdinate}, new double[] {responseValue (left()),
					responseValue (dblPredictorOrdinate)}, 0 != iCk ? csRightSnipped.transmissionCk (left(),
						this, iCk) : null, null, null, null)) ? csRightSnipped : null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Display the string representation for diagnostic purposes
	 * 
	 * @return The string representation
	 */

	public java.lang.String displayString()
	{
		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		sb.append ("\t\t\t[" + left() + " => " + right() + "\n");

		for (int i = 0; i < _responseToBasisCoefficientSensitivity.length; ++i) {
			if (0 != i) sb.append ("  |  ");

			sb.append (_responseToBasisCoefficientSensitivity[i] + "\n");
		}

		return sb.toString();
	}
}
