
package org.drip.spline.segment;

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
 * LatentStateResponseModel implements the single segment basis calibration and inference functionality. It
 * 	exports the following functionality:
 * 	- Build the LatentStateResponseModel instance from the Basis Function/Shape Controller Set.
 * 	- Build the LatentStateResponseModel instance from the Basis Evaluator Set.
 * 	- Retrieve the Number of Parameters, Basis Evaluator, Array of the Response Basis Coefficients, and
 * 		Segment Design Inelastic Control.
 * 	- Calibrate the Segment State from the Calibration Parameter Set.
 * 	- Sensitivity Calibrator: Calibrate the Segment Manifest Jacobian from the Calibration Parameter Set.
 * 	- Calibrate the coefficients from the prior Predictor/Response Segment, the Constraint, and fitness
 *		Weights
 *	- Calibrate the coefficients from the prior Segment and the Response Value at the Right Predictor
 *		Ordinate.
 *	- Calibrate the Coefficients from the Edge Response Values and the Left Edge Response Slope.
 *	- Calibrate the coefficients from the Left Edge Response Value Constraint, the Left Edge Response Value
 *		Slope, and the Right Edge Response Value Constraint.
 *	- Retrieve the Segment Curvature, Length, and the Best Fit DPE.
 *	- Calculate the Response Value and its Derivative at the given Predictor Ordinate.
 *	- Calculate the Ordered Derivative of the Coefficient to the Manifest.
 *	- Calculate the Jacobian of the Segment's Response Basis Function Coefficients to the Edge Inputs.
 *	- Calculate the Jacobian of the Response to the Edge Inputs at the given Predictor Ordinate.
 *	- Calculate the Jacobian of the Response to the Basis Coefficients at the given Predictor Ordinate.
 *	- Calibrate the segment and calculate the Jacobian of the Segment's Response Basis Function Coefficients
 *		to the Edge Parameters.
 *	- Calibrate the Coefficients from the Edge Response Values and the Left Edge Response Value Slope and
 *		calculate the Jacobian of the Segment's Response Basis Function Coefficients to the Edge Parameters.
 *	- Calibrate the coefficients from the prior Segment and the Response Value at the Right Predictor
 *		Ordinate and calculate the Jacobian of the Segment's Response Basis Function Coefficients to the Edge
 *  	Parameters.
 *  - Indicate whether the given segment is monotone. If monotone, may optionally indicate the nature of the
 *  	extrema contained inside (maxima/minima/infection).
 *  - Clip the part of the Segment to the Right of the specified Predictor Ordinate. Retain all other
 *  	constraints the same.
 *  - Clip the part of the Segment to the Left of the specified Predictor Ordinate. Retain all other
 *  	constraints the same.
 *  - Display the string representation for diagnostic purposes.
 *
 * @author Lakshmi Krishnamurthy
 */

public class LatentStateResponseModel extends org.drip.spline.segment.LatentStateInelastic {

	/**
	 * LEFT NODE VALUE PARAMETER INDEX
	 */

	public static final int LEFT_NODE_VALUE_PARAMETER_INDEX = 0;

	/**
	 * RIGHT NODE VALUE PARAMETER INDEX
	 */

	public static final int RIGHT_NODE_VALUE_PARAMETER_INDEX = 1;

	private double[] _adblResponseBasisCoeff = null;
	private org.drip.spline.segment.BasisEvaluator _be = null;
	private double[][] _aadblDResponseBasisCoeffDConstraint = null;
	private org.drip.spline.params.SegmentInelasticDesignControl _sidc = null;
	private org.drip.quant.calculus.WengertJacobian _wjDBasisCoeffDEdgeValue = null;

	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.spline.segment.LatentStateManifestSensitivity>
			_mapLSMS = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.spline.segment.LatentStateManifestSensitivity>();

	/**
	 * Build the LatentStateResponseModel instance from the Basis Function/Shape Controller Set
	 * 
	 * @param dblLeftPredictorOrdinate Left Predictor Ordinate
	 * @param dblRightPredictorOrdinate Right Predictor Ordinate
	 * @param fs Response Basis Function Set
	 * @param rssc Shape Controller
	 * @param sidc Segment Inelastic Design Parameters
	 * 
	 * @return Instance of LatentStateResponseModel
	 */

	public static final org.drip.spline.segment.LatentStateResponseModel Create (
		final double dblLeftPredictorOrdinate,
		final double dblRightPredictorOrdinate,
		final org.drip.spline.basis.FunctionSet fs,
		final org.drip.spline.params.ResponseScalingShapeControl rssc,
		final org.drip.spline.params.SegmentInelasticDesignControl sidc)
	{
		try {
			org.drip.spline.segment.SegmentBasisEvaluator sbe = new
				org.drip.spline.segment.SegmentBasisEvaluator (fs, rssc);

			org.drip.spline.segment.LatentStateResponseModel lsrm = new
				org.drip.spline.segment.LatentStateResponseModel (dblLeftPredictorOrdinate,
					dblRightPredictorOrdinate, sbe, sidc);

			return sbe.setContainingInelastics (lsrm) ? lsrm : null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Build the LatentStateResponseModel instance from the Basis Evaluator Set
	 * 
	 * @param dblLeftPredictorOrdinate Left Predictor Ordinate
	 * @param dblRightPredictorOrdinate Right Predictor Ordinate
	 * @param be Basis Evaluator
	 * @param sidc Segment Inelastic Design Parameters
	 * 
	 * @return Instance of LatentStateResponseModel
	 */

	public static final org.drip.spline.segment.LatentStateResponseModel Create (
		final double dblLeftPredictorOrdinate,
		final double dblRightPredictorOrdinate,
		final org.drip.spline.segment.BasisEvaluator be,
		final org.drip.spline.params.SegmentInelasticDesignControl sidc)
	{
		try {
			org.drip.spline.segment.LatentStateResponseModel lsrm = new
				org.drip.spline.segment.LatentStateResponseModel (dblLeftPredictorOrdinate,
					dblRightPredictorOrdinate, be, sidc);

			return be.setContainingInelastics (lsrm) ? lsrm : null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private LatentStateResponseModel (
		final double dblLeftPredictorOrdinate,
		final double dblRightPredictorOrdinate,
		final org.drip.spline.segment.BasisEvaluator be,
		final org.drip.spline.params.SegmentInelasticDesignControl sidc)
		throws java.lang.Exception
	{
		super (dblLeftPredictorOrdinate, dblRightPredictorOrdinate);

		if (null == (_be = be) || null == (_sidc = sidc))
			throw new java.lang.Exception ("LatentStateResponseModel ctr: Invalid Basis Functions!");

		int iNumBasis = _be.numBasis();

		_adblResponseBasisCoeff = new double[iNumBasis];

		if (0 >= iNumBasis || _sidc.Ck() > iNumBasis - 2)
			throw new java.lang.Exception ("LatentStateResponseModel ctr: Invalid inputs!");
	}

	private double[] DResponseDBasisCoeff (
		final double dblPredictorOrdinate,
		final int iOrder)
	{
		if (0 == iOrder) return null;

		int iNumBasis = _be.numBasis();

		double[] adblDResponseDBasisCoeff = new double[iNumBasis];

		for (int i = 0; i < iNumBasis; ++i) {
			try {
				adblDResponseDBasisCoeff[i] = 1 == iOrder ? _be.shapedBasisFunctionResponse
					(dblPredictorOrdinate, i) : 0.;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblDResponseDBasisCoeff;
	}

	private double[] transmissionCk (
		final double dblPredictorOrdinate,
		final org.drip.spline.segment.LatentStateResponseModel csPreceeding,
		final int iCk)
	{
		double[] adblDeriv = new double[iCk];

		for (int i = 0; i < iCk; ++i) {
			try {
				adblDeriv[i] = csPreceeding.calcResponseValueDerivative (dblPredictorOrdinate, i + 1);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return adblDeriv;
	}

	private org.drip.spline.segment.LatentStateManifestSensitivity manifestSensitivity (
		final java.lang.String strManifestMeasure)
	{
		return null == strManifestMeasure || strManifestMeasure.isEmpty() || !_mapLSMS.containsKey
			(strManifestMeasure) ? null : _mapLSMS.get (strManifestMeasure);
	}

	private double[] CkDBasisCoeffDPreceedingManifestMeasure (
		final java.lang.String strManifestMeasure)
	{
		org.drip.spline.segment.LatentStateManifestSensitivity lsms = manifestSensitivity
			(strManifestMeasure);

		if (null == lsms) return null;

		int iCk = lsms.getPMSC().Ck();

		if (0 == iCk) return null;

		double[] adblDBasisCoeffDPreceedingManifestTail = new double[iCk];

		for (int i = 0; i < iCk; ++i)
			adblDBasisCoeffDPreceedingManifestTail[i] = 0.;

		return adblDBasisCoeffDPreceedingManifestTail;
	}

	/**
	 * Set the Preceeding Manifest Sensitivity Control Parameters for the specified Manifest Measure
	 * 
	 * @param strManifestMeasure The Manifest Measure
	 * @param pmsc The Preceeding Manifest Sensitivity Control Instance
	 * 
	 * @return TRUE - Named Preceeding Manifest Sensitivity Control Instance Successfully Set
	 */

	public boolean setPreceedingManifestSensitivityControl (
		final java.lang.String strManifestMeasure,
		final org.drip.spline.params.PreceedingManifestSensitivityControl pmsc)
	{
		if (null == strManifestMeasure || strManifestMeasure.isEmpty()) return false;

		try {
			_mapLSMS.put (strManifestMeasure, new org.drip.spline.segment.LatentStateManifestSensitivity
				(pmsc));

			return true;
		} catch (java.lang.Exception e) {
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
		return _sidc.Ck() + 2;
	}

	/**
	 * Retrieve the Basis Evaluator
	 * 
	 * @return The Basis Evaluator
	 */

	public org.drip.spline.segment.BasisEvaluator basisEvaluator()
	{
		return _be;
	}

	/**
	 * Retrieve the Array of Response Basis Coefficients
	 * 
	 * @return The Array of Response Basis Coefficients
	 */

	public double[] responseBasisCoefficient()
	{
		return _adblResponseBasisCoeff;
	}

	/**
	 * Retrieve the Segment Inelastic Design Control
	 * 
	 * @return The Segment Inelastic Design Control
	 */

	public org.drip.spline.params.SegmentInelasticDesignControl designControl()
	{
		return _sidc;
	}

	/**
	 * Main Calibrator: Calibrate the Segment State from the Calibration Parameter Set
	 * 
	 * @param ssciState The Segment State Calibration Inputs Set
	 * 
	 * @return TRUE - Calibration Successful
	 */

	public boolean calibrateState (
		final org.drip.spline.params.SegmentStateCalibrationInputs ssciState)
	{
		if (null == ssciState) return false;

		double[] adblPredictorOrdinate = ssciState.predictorOrdinates();

		double[] adblResponseValue = ssciState.responseValues();

		double[] adblLeftEdgeDeriv = ssciState.leftEdgeDeriv();

		double[] adblRightEdgeDeriv = ssciState.rightEdgeDeriv();

		org.drip.spline.params.SegmentBestFitResponse sbfr = ssciState.bestFitResponse();

		org.drip.spline.params.SegmentBasisFlexureConstraint[] aSBFC = ssciState.flexureConstraint();

		int iNumConstraint = 0;
		int iNumResponseBasisCoeff = _adblResponseBasisCoeff.length;
		int iNumLeftDeriv = null == adblLeftEdgeDeriv ? 0 : adblLeftEdgeDeriv.length;
		int iNumRightDeriv = null == adblRightEdgeDeriv ? 0 : adblRightEdgeDeriv.length;
		double[] adblPredictorResponseConstraintValue = new double[iNumResponseBasisCoeff];
		int iNumPredictorOrdinate = null == adblPredictorOrdinate ? 0 : adblPredictorOrdinate.length;
		double[][] aadblResponseBasisCoeffConstraint = new
			double[iNumResponseBasisCoeff][iNumResponseBasisCoeff];

		if (null != aSBFC) {
			int iNumPotentialConstraint = aSBFC.length;

			for (int i = 0; i < iNumPotentialConstraint; ++i) {
				if (null != aSBFC[i]) ++iNumConstraint;
			}
		}

		if (iNumResponseBasisCoeff < iNumPredictorOrdinate + iNumLeftDeriv + iNumRightDeriv + iNumConstraint)
			return false;

		try {
			org.drip.spline.segment.BestFitFlexurePenalizer bffp = new
				org.drip.spline.segment.BestFitFlexurePenalizer (this, _sidc.curvaturePenaltyControl(),
					_sidc.lengthPenaltyControl(), sbfr, _be);

			for (int j = 0; j < iNumResponseBasisCoeff; ++j) {
				if (j < iNumPredictorOrdinate)
					adblPredictorResponseConstraintValue[j] = adblResponseValue[j];
				else if (j < iNumPredictorOrdinate + iNumConstraint)
					adblPredictorResponseConstraintValue[j] = aSBFC[j -
					    iNumPredictorOrdinate].contraintValue();
				else if (j < iNumPredictorOrdinate + iNumConstraint + iNumLeftDeriv)
					adblPredictorResponseConstraintValue[j] = adblLeftEdgeDeriv[j - iNumPredictorOrdinate -
					    iNumConstraint];
				else if (j < iNumPredictorOrdinate + iNumConstraint + iNumLeftDeriv + iNumRightDeriv)
					adblPredictorResponseConstraintValue[j] = adblRightEdgeDeriv[j - iNumPredictorOrdinate -
					    iNumConstraint - iNumLeftDeriv];
				else
					adblPredictorResponseConstraintValue[j] = bffp.basisPairPenaltyConstraint (j);
			}

			for (int i = 0; i < iNumResponseBasisCoeff; ++i) {
				for (int l = 0; l < iNumResponseBasisCoeff; ++l) {
					double[] adblCalibBasisConstraintWeight = null;

					if (0 != iNumConstraint && (l >= iNumPredictorOrdinate && l < iNumPredictorOrdinate +
						iNumConstraint))
						adblCalibBasisConstraintWeight = aSBFC[l -
						    iNumPredictorOrdinate].responseBasisCoeffWeights();

					if (l < iNumPredictorOrdinate)
						aadblResponseBasisCoeffConstraint[l][i] = _be.shapedBasisFunctionResponse
							(adblPredictorOrdinate[l], i);
					else if (l < iNumPredictorOrdinate + iNumConstraint)
						aadblResponseBasisCoeffConstraint[l][i] = adblCalibBasisConstraintWeight[i];
					else if (l < iNumPredictorOrdinate + iNumConstraint + iNumLeftDeriv)
						aadblResponseBasisCoeffConstraint[l][i] = _be.shapedBasisFunctionDerivative (left(),
							l - iNumPredictorOrdinate - iNumConstraint + 1, i);
					else if (l < iNumPredictorOrdinate + iNumConstraint + iNumLeftDeriv + iNumRightDeriv)
						aadblResponseBasisCoeffConstraint[l][i] = _be.shapedBasisFunctionDerivative
							(right(), l - iNumPredictorOrdinate - iNumConstraint - iNumLeftDeriv + 1, i);
					else
						aadblResponseBasisCoeffConstraint[l][i] = bffp.basisPairConstraintCoefficient (i, l);
				}
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		org.drip.quant.linearalgebra.LinearizationOutput lo =
			org.drip.quant.linearalgebra.LinearSystemSolver.SolveUsingMatrixInversion
				(aadblResponseBasisCoeffConstraint, adblPredictorResponseConstraintValue);

		if (null == lo) return false;

		double[] adblCalibResponseBasisCoeff = lo.getTransformedRHS();

		if (null == adblCalibResponseBasisCoeff || adblCalibResponseBasisCoeff.length !=
			iNumResponseBasisCoeff || null == (_aadblDResponseBasisCoeffDConstraint =
				lo.getTransformedMatrix()) || _aadblDResponseBasisCoeffDConstraint.length !=
					iNumResponseBasisCoeff || _aadblDResponseBasisCoeffDConstraint[0].length !=
						iNumResponseBasisCoeff)
			return false;

		for (int i = 0; i < iNumResponseBasisCoeff; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (_adblResponseBasisCoeff[i] =
				adblCalibResponseBasisCoeff[i]))
				return false;
		}

		return true;
	}

	/**
	 * Sensitivity Calibrator: Calibrate the Segment Manifest Measure Jacobian from the Calibration Inputs
	 * 
	 * @param ssciManifestSensitivity The Segment Manifest Calibration Sensitivity Inputs
	 * @param aSBFCState Array of Segment State Basis Flexure Constraints
	 * 
	 * @return The Manifest Sensitivity Coefficients
	 */

	public double[] calibrateManifestJacobian (
		final org.drip.spline.params.SegmentStateCalibrationInputs ssciManifestSensitivity,
		final org.drip.spline.params.SegmentBasisFlexureConstraint[] aSBFCState)
	{
		if (null == ssciManifestSensitivity) return null;

		double[] adblPredictorOrdinate = ssciManifestSensitivity.predictorOrdinates();

		double[] adblResponseValueManifestSensitivity = ssciManifestSensitivity.responseValues();

		double[] adblLeftEdgeDerivManifestSensitivity = ssciManifestSensitivity.leftEdgeDeriv();

		double[] adblRightEdgeDerivManifestSensitivity = ssciManifestSensitivity.rightEdgeDeriv();

		org.drip.spline.params.SegmentBestFitResponse sbfrManifestSensitivity =
			ssciManifestSensitivity.bestFitResponse();

		org.drip.spline.params.SegmentBasisFlexureConstraint[] aSBFCManifestSensitivity =
			ssciManifestSensitivity.flexureConstraint();

		int iNumConstraint = 0;
		int iNumResponseBasisCoeff = _adblResponseBasisCoeff.length;
		int iNumPredictorOrdinate = null == adblPredictorOrdinate ? 0 : adblPredictorOrdinate.length;
		double[] adblPredictorResponseManifestSensitivityConstraint = new double[iNumResponseBasisCoeff];
		int iNumLeftDerivManifestSensitivity = null == adblLeftEdgeDerivManifestSensitivity ? 0 :
			adblLeftEdgeDerivManifestSensitivity.length;
		int iNumRightDerivManifestSensitivity = null == adblRightEdgeDerivManifestSensitivity ? 0 :
			adblRightEdgeDerivManifestSensitivity.length;
		double[][] aadblResponseCoeffConstraintManifestSensitivity = new
			double[iNumResponseBasisCoeff][iNumResponseBasisCoeff];

		if (null != aSBFCState) {
			int iNumPotentialConstraint = aSBFCState.length;

			for (int i = 0; i < iNumPotentialConstraint; ++i) {
				if (null != aSBFCState[i]) ++iNumConstraint;
			}
		}

		if (iNumResponseBasisCoeff < iNumPredictorOrdinate + iNumLeftDerivManifestSensitivity +
			iNumRightDerivManifestSensitivity + iNumConstraint)
			return null;

		try {
			org.drip.spline.segment.BestFitFlexurePenalizer bffpManifestSensitivity = new
				org.drip.spline.segment.BestFitFlexurePenalizer (this, null == _sidc ? null :
					_sidc.curvaturePenaltyControl(), null == _sidc ? null : _sidc.lengthPenaltyControl(),
						sbfrManifestSensitivity, _be);

			for (int j = 0; j < iNumResponseBasisCoeff; ++j) {
				if (j < iNumPredictorOrdinate)
					adblPredictorResponseManifestSensitivityConstraint[j] =
						adblResponseValueManifestSensitivity[j];
				else if (j < iNumPredictorOrdinate + iNumConstraint) {
					adblPredictorResponseManifestSensitivityConstraint[j] = 0.;
					org.drip.spline.params.SegmentBasisFlexureConstraint sbfcManifestSensitivity =
						aSBFCManifestSensitivity[j - iNumPredictorOrdinate];

					if (null != sbfcManifestSensitivity) {
						adblPredictorResponseManifestSensitivityConstraint[j] =
							sbfcManifestSensitivity.contraintValue();

						double[] adblCalibConstraintWeightManifestSensitivity =
							sbfcManifestSensitivity.responseBasisCoeffWeights();

						for (int i = 0; i < iNumResponseBasisCoeff; ++i)
							adblPredictorResponseManifestSensitivityConstraint[j] -=
								_adblResponseBasisCoeff[i] * adblCalibConstraintWeightManifestSensitivity[i];
					}
				} else if (j < iNumPredictorOrdinate + iNumConstraint + iNumLeftDerivManifestSensitivity)
					adblPredictorResponseManifestSensitivityConstraint[j] =
						adblLeftEdgeDerivManifestSensitivity[j - iNumPredictorOrdinate - iNumConstraint];
				else if (j < iNumPredictorOrdinate + iNumConstraint + iNumLeftDerivManifestSensitivity +
					iNumRightDerivManifestSensitivity)
					adblPredictorResponseManifestSensitivityConstraint[j] =
						adblRightEdgeDerivManifestSensitivity[j - iNumPredictorOrdinate - iNumConstraint -
						    iNumLeftDerivManifestSensitivity];
				else
					adblPredictorResponseManifestSensitivityConstraint[j] =
						bffpManifestSensitivity.basisPairPenaltyConstraint (j);
			}

			for (int i = 0; i < iNumResponseBasisCoeff; ++i) {
				for (int l = 0; l < iNumResponseBasisCoeff; ++l) {
					double[] adblCalibBasisConstraintWeight = null;

					if (0 != iNumConstraint && (l >= iNumPredictorOrdinate && l < iNumPredictorOrdinate +
						iNumConstraint))
						adblCalibBasisConstraintWeight = aSBFCState[l -
						    iNumPredictorOrdinate].responseBasisCoeffWeights();

					if (l < iNumPredictorOrdinate)
						aadblResponseCoeffConstraintManifestSensitivity[l][i] =
							_be.shapedBasisFunctionResponse (adblPredictorOrdinate[l], i);
					else if (l < iNumPredictorOrdinate + iNumConstraint)
						aadblResponseCoeffConstraintManifestSensitivity[l][i] =
							adblCalibBasisConstraintWeight[i];
					else if (l < iNumPredictorOrdinate + iNumConstraint + iNumLeftDerivManifestSensitivity)
						aadblResponseCoeffConstraintManifestSensitivity[l][i] =
							_be.shapedBasisFunctionDerivative (left(), l - iNumPredictorOrdinate -
								iNumConstraint + 1, i);
					else if (l < iNumPredictorOrdinate + iNumConstraint + iNumLeftDerivManifestSensitivity +
						iNumRightDerivManifestSensitivity)
						aadblResponseCoeffConstraintManifestSensitivity[l][i] =
							_be.shapedBasisFunctionDerivative (right(), l - iNumPredictorOrdinate -
								iNumConstraint - iNumLeftDerivManifestSensitivity + 1, i);
					else
						aadblResponseCoeffConstraintManifestSensitivity[l][i] =
							bffpManifestSensitivity.basisPairConstraintCoefficient (i, l);
				}
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.quant.linearalgebra.LinearizationOutput lo =
			org.drip.quant.linearalgebra.LinearSystemSolver.SolveUsingMatrixInversion
				(aadblResponseCoeffConstraintManifestSensitivity,
					adblPredictorResponseManifestSensitivityConstraint);

		return null == lo ? null : lo.getTransformedRHS();
	}

	/**
	 * Sensitivity Calibrator: Calibrate the Segment Local Manifest Jacobian from the Calibration Parameter
	 * 	Set
	 * 
	 * @param strManifestMeasure Latent State Manifest Measure
	 * @param ssciManifestSensitivity The Segment Manifest Calibration Parameter Sensitivity
	 * @param aSBFCState Array of Segment State Basis Flexure Constraints
	 * 
	 * @return TRUE - Local Manifest Sensitivity Calibration Successful
	 */

	public boolean calibrateLocalManifestJacobian (
		final java.lang.String strManifestMeasure,
		final org.drip.spline.params.SegmentStateCalibrationInputs ssciManifestSensitivity,
		final org.drip.spline.params.SegmentBasisFlexureConstraint[] aSBFCState)
	{
		org.drip.spline.segment.LatentStateManifestSensitivity lsms = manifestSensitivity
			(strManifestMeasure);

		if (null == lsms) return false;

		double[] adblDBasisCoeffDLocalManifest = calibrateManifestJacobian (ssciManifestSensitivity,
			aSBFCState);

		return null == adblDBasisCoeffDLocalManifest || adblDBasisCoeffDLocalManifest.length !=
			_adblResponseBasisCoeff.length ? false : lsms.setDBasisCoeffDLocalManifest
				(adblDBasisCoeffDLocalManifest);
	}

	/**
	 * Sensitivity Calibrator: Calibrate the Segment Preceeding Manifest Jacobian from the Calibration
	 *	Parameter Set
	 * 
	 * @param strManifestMeasure Latent State Manifest
	 * @param ssciPreceedingManifestSensitivity The Segment Preceeding Manifest Calibration Parameter
	 * 	Sensitivity
	 * 
	 * @return TRUE - Preceeding Manifest Sensitivity Calibration Successful
	 */

	public boolean calibratePreceedingManifestJacobian (
		final java.lang.String strManifestMeasure,
		final org.drip.spline.params.SegmentStateCalibrationInputs ssciPreceedingManifestSensitivity)
	{
		org.drip.spline.segment.LatentStateManifestSensitivity lsms = manifestSensitivity
			(strManifestMeasure);

		if (null == lsms) return false;

		double[] adblDBasisCoeffDPreceedingManifest = calibrateManifestJacobian
			(ssciPreceedingManifestSensitivity, null);

		return null == adblDBasisCoeffDPreceedingManifest || adblDBasisCoeffDPreceedingManifest.length !=
			_adblResponseBasisCoeff.length ? false : lsms.setDBasisCoeffDPreceedingManifest
				(adblDBasisCoeffDPreceedingManifest);
	}

	/**
	 * Calibrate the coefficients from the prior Predictor/Response Segment, the Constraint, and fitness
	 * 	Weights
	 * 
	 * @param csPreceeding Preceeding Predictor/Response Segment
	 * @param srvcState The Segment State Response Value Constraint
	 * @param sbfrState Segment's Best Fit Weighted State Response Values
	 * 
	 * @return TRUE - If the calibration succeeds
	 */

	public boolean calibrate (
		final org.drip.spline.segment.LatentStateResponseModel csPreceeding,
		final org.drip.spline.params.SegmentResponseValueConstraint srvcState,
		final org.drip.spline.params.SegmentBestFitResponse sbfrState)
	{
		int iCk = _sidc.Ck();

		org.drip.spline.params.SegmentBasisFlexureConstraint[] aSBFCState = null == srvcState ? null : new
			org.drip.spline.params.SegmentBasisFlexureConstraint[] {srvcState.responseIndexedBasisConstraint
				(_be, this)};

		double[] adblManifestJacobianDerivAtLeftOrdinate = null;

		if (0 != iCk) {
			adblManifestJacobianDerivAtLeftOrdinate = new double[iCk];

			for (int i = 0; i < iCk; ++i)
				adblManifestJacobianDerivAtLeftOrdinate[i] = 0.;
		}

		if (null == csPreceeding) {
			try {
				double[] adblStateDerivAtLeftOrdinate = null;

				if (0 != iCk) {
					adblStateDerivAtLeftOrdinate = new double[iCk];

					for (int i = 0; i < iCk; ++i)
						adblStateDerivAtLeftOrdinate[i] = _be.responseValueDerivative
							(_adblResponseBasisCoeff, left(), i);
				}

				return calibrateState (new org.drip.spline.params.SegmentStateCalibrationInputs (new double[]
					{left()}, new double[] {_be.responseValue (_adblResponseBasisCoeff, left())},
						adblStateDerivAtLeftOrdinate, null, aSBFCState, sbfrState));
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}

			return false;
		}

		try {
			return calibrateState (new org.drip.spline.params.SegmentStateCalibrationInputs (new double[]
				{left()}, new double[] {csPreceeding.responseValue (left())}, 0 == iCk ? null :
					transmissionCk (left(), csPreceeding, iCk), null, aSBFCState, sbfrState));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Calibrate the coefficients from the prior Segment and the Response Value at the Right Predictor
	 *  Ordinate
	 * 
	 * @param csPreceeding Preceeding Predictor/Response Segment
	 * @param dblRightStateValue Response Value at the Right Predictor Ordinate
	 * @param sbfrState Segment's Best Fit Weighted Response Values
	 * 
	 * @return TRUE - If the calibration succeeds
	 */

	public boolean calibrate (
		final LatentStateResponseModel csPreceeding,
		final double dblRightStateValue,
		final org.drip.spline.params.SegmentBestFitResponse sbfrState)
	{
		if (null == csPreceeding) return false;

		int iCk = _sidc.Ck();

		try {
			return calibrateState (new org.drip.spline.params.SegmentStateCalibrationInputs (new double[]
				{left(), right()}, new double[] {csPreceeding.responseValue (left()), dblRightStateValue}, 0
					!= iCk ? csPreceeding.transmissionCk (left(), this, iCk) : null, null, null, sbfrState));
		} catch (java.lang.Exception e) {
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
		if (!org.drip.quant.common.NumberUtil.IsValid (dblLeftValue) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblLeftSlope) ||
				!org.drip.quant.common.NumberUtil.IsValid (dblRightValue))
			return false;

		try {
			return calibrateState (new org.drip.spline.params.SegmentStateCalibrationInputs (new double[]
				{left(), right()}, new double[] {dblLeftValue, dblRightValue},
					org.drip.quant.common.CollectionUtil.DerivArrayFromSlope (numParameters() - 2,
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
					wrvcStateLeft ? null : wrvcStateLeft.responseIndexedBasisConstraint (_be, this), null ==
						wrvcStateRight ? null : wrvcStateRight.responseIndexedBasisConstraint (_be, this)};

			return calibrateState (new org.drip.spline.params.SegmentStateCalibrationInputs (null, null,
				org.drip.quant.common.CollectionUtil.DerivArrayFromSlope (numParameters() - 2, dblLeftSlope),
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
				(_be, this)};

		org.drip.spline.params.SegmentBasisFlexureConstraint[] aSBFCManifestSensitivity = null ==
			srvcManifestSensitivity ? null : new org.drip.spline.params.SegmentBasisFlexureConstraint[]
				{srvcManifestSensitivity.responseIndexedBasisConstraint (_be, this)};

		double[] adblManifestJacobianDerivAtLeftOrdinate = null;

		int iCk = _sidc.Ck();

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

		int iCk = _sidc.Ck();

		try {
			double[] adblManifestJacobianDerivAtLeftOrdinate = null;

			if (0 != iCk) {
				adblManifestJacobianDerivAtLeftOrdinate = new double[iCk];

				for (int i = 0; i < iCk; ++i)
					adblManifestJacobianDerivAtLeftOrdinate[i] = 0.;
			}

			if (!org.drip.quant.common.NumberUtil.IsValid (dblRightStateManifestSensitivity)) return true;

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
			return org.drip.quant.common.NumberUtil.IsValid (dblLeftManifestSensitivity) &&
				org.drip.quant.common.NumberUtil.IsValid (dblLeftSlopeManifestSensitivity) &&
					org.drip.quant.common.NumberUtil.IsValid (dblRightManifestSensitivity) ?
						calibrateLocalManifestJacobian (strManifestMeasure, new
							org.drip.spline.params.SegmentStateCalibrationInputs (new double[] {left(),
								right()}, new double[] {dblLeftManifestSensitivity,
									dblRightManifestSensitivity},
										org.drip.quant.common.CollectionUtil.DerivArrayFromSlope
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
					wrvcStateLeft ? null : wrvcStateLeft.responseIndexedBasisConstraint (_be, this), null ==
						wrvcStateRight ? null : wrvcStateRight.responseIndexedBasisConstraint (_be, this)};

			if (null != wrvcStateLeftManifestSensitivity || null != wrvcStateRightManifestSensitivity)
				aSBFCManifestSensitivity = new org.drip.spline.params.SegmentBasisFlexureConstraint[] {null
					== wrvcStateLeftManifestSensitivity ? null :
						wrvcStateLeftManifestSensitivity.responseIndexedBasisConstraint (_be, this), null ==
							wrvcStateRightManifestSensitivity ? null :
								wrvcStateRightManifestSensitivity.responseIndexedBasisConstraint (_be,
									this)};

			return null == aSBFCManifestSensitivity ? true : calibrateLocalManifestJacobian
				(strManifestMeasure, new org.drip.spline.params.SegmentStateCalibrationInputs (null, null,
					org.drip.quant.common.CollectionUtil.DerivArrayFromSlope (numParameters() - 2,
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

		int iNumBasis = _be.numBasis();

		org.drip.spline.params.SegmentFlexurePenaltyControl sfpc = _sidc.curvaturePenaltyControl();

		if (null == sfpc) sfpc = new org.drip.spline.params.SegmentFlexurePenaltyControl (2, 1.);

		org.drip.spline.segment.BestFitFlexurePenalizer bffp = new
			org.drip.spline.segment.BestFitFlexurePenalizer (this, sfpc, null, null, _be);

		for (int i = 0; i < iNumBasis; ++i) {
			for (int j = 0; j < iNumBasis; ++j)
				dblDPE += _adblResponseBasisCoeff[i] * _adblResponseBasisCoeff[j] *
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

		int iNumBasis = _be.numBasis();

		org.drip.spline.params.SegmentFlexurePenaltyControl sfpcLength = _sidc.lengthPenaltyControl();

		if (null == sfpcLength) sfpcLength = new org.drip.spline.params.SegmentFlexurePenaltyControl (1, 1.);

		org.drip.spline.segment.BestFitFlexurePenalizer bffp = new
			org.drip.spline.segment.BestFitFlexurePenalizer (this, null, sfpcLength, null, _be);

		for (int i = 0; i < iNumBasis; ++i) {
			for (int j = 0; j < iNumBasis; ++j)
				dblDPE += _adblResponseBasisCoeff[i] * _adblResponseBasisCoeff[j] *
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

		int iNumBasis = _be.numBasis();

		org.drip.spline.segment.BestFitFlexurePenalizer bffp = new
			org.drip.spline.segment.BestFitFlexurePenalizer (this, null, null, sbfr, _be);

		for (int i = 0; i < iNumBasis; ++i) {
			for (int j = 0; j < iNumBasis; ++j)
				dblDPE += _adblResponseBasisCoeff[i] * _adblResponseBasisCoeff[j] * bffp.basisBestFitPenalty
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
		return _be.responseValue (_adblResponseBasisCoeff, dblPredictorOrdinate);
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
		return 0 == iOrder ? responseValue (dblPredictorOrdinate) : _be.responseValueDerivative
			(_adblResponseBasisCoeff, dblPredictorOrdinate, iOrder);
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

		return _be.responseValue (lsms.getDBasisCoeffDLocalManifest(), dblPredictorOrdinate);
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
			return org.drip.quant.common.NumberUtil.IsValid (dblDResponseDPreceedingManifest) ?
				dblDResponseDPreceedingManifest : 0.;

		org.drip.spline.segment.BasisEvaluator be = pqsc.basisEvaluator();

		double[] adblDBasisCoeffDPreceedingManifest = lsms.getDBasisCoeffDPreceedingManifest();

		return null == adblDBasisCoeffDPreceedingManifest ? 0. : (null == be ? _be : be).responseValue
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

	public org.drip.quant.calculus.WengertJacobian jackDCoeffDEdgeInputs()
	{
		if (null != _wjDBasisCoeffDEdgeValue) return _wjDBasisCoeffDEdgeValue;

		int iNumResponseBasisCoeff = _be.numBasis();

		try {
			_wjDBasisCoeffDEdgeValue = new org.drip.quant.calculus.WengertJacobian (iNumResponseBasisCoeff,
				iNumResponseBasisCoeff);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return _wjDBasisCoeffDEdgeValue = null;
		}

		for (int i = 0; i < iNumResponseBasisCoeff; ++i) {
			if (!_wjDBasisCoeffDEdgeValue.setWengert (i, _adblResponseBasisCoeff[i]))
				return _wjDBasisCoeffDEdgeValue = null;
		}

		if (null == _aadblDResponseBasisCoeffDConstraint) return null;

		int iSize = _aadblDResponseBasisCoeffDConstraint.length;

		for (int i = 0; i < iSize; ++i) {
			for (int j = 0; j < iSize; ++j) {
				if (!_wjDBasisCoeffDEdgeValue.accumulatePartialFirstDerivative (i, j,
					_aadblDResponseBasisCoeffDConstraint[i][j]))
					return null;
			}
		}

		return _wjDBasisCoeffDEdgeValue;
	}

	/**
	 * Calculate the Jacobian of the Response to the Edge Inputs at the given Predictor Ordinate
	 * 
	 * @param dblPredictorOrdinate The Predictor Ordinate
	 * @param iOrder Order of the Derivative Desired
	 * 
	 * @return The Jacobian of the Response to the Edge Inputs at the given Predictor Ordinate
	 */

	public org.drip.quant.calculus.WengertJacobian jackDResponseDEdgeInput (
		final double dblPredictorOrdinate,
		final int iOrder)
	{
		try {
			int iNumResponseBasisCoeff = _be.numBasis();

			org.drip.quant.calculus.WengertJacobian wjDResponseDEdgeParams = null;
			double[][] aadblDBasisCoeffDEdgeParams = new
				double[iNumResponseBasisCoeff][iNumResponseBasisCoeff];

			double[] adblDResponseDBasisCoeff = DResponseDBasisCoeff (dblPredictorOrdinate, iOrder);

			if (null == adblDResponseDBasisCoeff || iNumResponseBasisCoeff !=
				adblDResponseDBasisCoeff.length)
				return null;

			org.drip.quant.calculus.WengertJacobian wjDBasisCoeffDEdgeParams = (null ==
				_wjDBasisCoeffDEdgeValue) ? jackDCoeffDEdgeInputs() : _wjDBasisCoeffDEdgeValue;

			for (int i = 0; i < iNumResponseBasisCoeff; ++i) {
				for (int j = 0; j < iNumResponseBasisCoeff; ++j)
					aadblDBasisCoeffDEdgeParams[j][i] = wjDBasisCoeffDEdgeParams.firstDerivative (j, i);
			}

			if (!(wjDResponseDEdgeParams = new org.drip.quant.calculus.WengertJacobian (1,
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

	public org.drip.quant.calculus.WengertJacobian jackDResponseDBasisCoeff (
		final double dblPredictorOrdinate,
		final int iOrder)
	{
		try {
			int iNumResponseBasisCoeff = _be.numBasis();

			double[] adblBasisDResponseDBasisCoeff = DResponseDBasisCoeff (dblPredictorOrdinate, iOrder);

			if (null == adblBasisDResponseDBasisCoeff || iNumResponseBasisCoeff !=
				adblBasisDResponseDBasisCoeff.length)
				return null;

			org.drip.quant.calculus.WengertJacobian wjDResponseDBasisCoeff = new
				org.drip.quant.calculus.WengertJacobian (1, iNumResponseBasisCoeff);

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

	public org.drip.quant.calculus.WengertJacobian jackDCoeffDEdgeParams (
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

	public org.drip.quant.calculus.WengertJacobian jackDCoeffDEdgeParams (
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

	public org.drip.quant.calculus.WengertJacobian jackDCoeffDEdgeParams (
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
		if (1 >= _sidc.Ck()) {
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
				return _be.responseValueDerivative (_adblResponseBasisCoeff, dblX, 1);
			}

			@Override public org.drip.quant.calculus.Differential differential (
				final double dblX,
				final double dblOFBase,
				final int iOrder)
			{
				try {
					double dblVariateInfinitesimal = _dc.getVariateInfinitesimal (dblX);

					return new org.drip.quant.calculus.Differential (dblVariateInfinitesimal,
						_be.responseValueDerivative (_adblResponseBasisCoeff, dblX, iOrder) *
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
				return org.drip.quant.calculus.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
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

			if (!org.drip.quant.common.NumberUtil.IsValid (dblExtremum) || dblExtremum <= 0. || dblExtremum
				>= 1.)
				return new org.drip.spline.segment.Monotonocity
					(org.drip.spline.segment.Monotonocity.MONOTONIC);

			double dbl2ndDeriv = _be.responseValueDerivative (_adblResponseBasisCoeff, dblExtremum, 2);

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
				right(), _be.replicate(), _sidc);

			int iCk = _sidc.Ck();

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
				dblPredictorOrdinate, _be.replicate(), _sidc);

			int iCk = _sidc.Ck();

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

		for (int i = 0; i < _adblResponseBasisCoeff.length; ++i) {
			if (0 != i) sb.append ("  |  ");

			sb.append (_adblResponseBasisCoeff[i] + "\n");
		}

		return sb.toString();
	}
}
