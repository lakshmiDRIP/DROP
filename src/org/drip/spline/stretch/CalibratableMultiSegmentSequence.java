
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
 * CalibratableMultiSegmentSequence implements the MultiSegmentSequence span that spans multiple segments. It
 *  holds the ordered segment sequence, segment sequence builder, the segment control parameters, and, if
 *  available, the spanning Jacobian. It provides a variety of customization for the segment construction and
 *  state r4epresentation control.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CalibratableMultiSegmentSequence extends org.drip.function.definition.R1ToR1 implements
	org.drip.spline.stretch.MultiSegmentSequence {
	private static final int MAXIMA_PREDICTOR_ORDINATE_NODE = 1;
	private static final int MINIMA_PREDICTOR_ORDINATE_NODE = 2;
	private static final int MONOTONE_PREDICTOR_ORDINATE_NODE = 4;

	private java.lang.String _strName = "";
	private org.drip.spline.stretch.SegmentSequenceBuilder _ssb = null;
	private org.drip.spline.segment.LatentStateResponseModel[] _aLSRM = null;
	private org.drip.spline.params.SegmentCustomBuilderControl[] _aSCBC = null;
	private org.drip.quant.calculus.WengertJacobian _wjDCoeffDEdgeParams = null;

	private boolean setDCoeffDEdgeParams (
		final int iNodeIndex,
		final org.drip.quant.calculus.WengertJacobian wjDCoeffDEdgeParams)
	{
		if (null == wjDCoeffDEdgeParams) return false;

		int iParameterIndex = 0 == iNodeIndex ? 0 : 2;

		if (!_wjDCoeffDEdgeParams.accumulatePartialFirstDerivative (0, iNodeIndex,
			wjDCoeffDEdgeParams.firstDerivative (0, iParameterIndex)))
			return false;

		if (!_wjDCoeffDEdgeParams.accumulatePartialFirstDerivative (1, iNodeIndex,
			wjDCoeffDEdgeParams.firstDerivative (1, iParameterIndex)))
			return false;

		if (!_wjDCoeffDEdgeParams.accumulatePartialFirstDerivative (2, iNodeIndex,
			wjDCoeffDEdgeParams.firstDerivative (2, iParameterIndex)))
			return false;

		return _wjDCoeffDEdgeParams.accumulatePartialFirstDerivative (3, iNodeIndex,
			wjDCoeffDEdgeParams.firstDerivative (3, iParameterIndex));
	}

	private final org.drip.quant.calculus.WengertJacobian setDResponseDEdgeResponse (
		final int iNodeIndex,
		final org.drip.quant.calculus.WengertJacobian wjDResponseDEdgeParams)
	{
		if (null == wjDResponseDEdgeParams) return null;

		int iNumSegment = _aLSRM.length;
		org.drip.quant.calculus.WengertJacobian wjDResponseDEdgeResponse = null;

		try {
			wjDResponseDEdgeResponse = new org.drip.quant.calculus.WengertJacobian (1, iNumSegment + 1);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		for (int i = 0; i <= iNumSegment; ++i) {
			if (i == iNodeIndex) {
				if (!wjDResponseDEdgeResponse.accumulatePartialFirstDerivative (0, i,
					wjDResponseDEdgeParams.firstDerivative (0,
						org.drip.spline.segment.LatentStateResponseModel.LEFT_NODE_VALUE_PARAMETER_INDEX)) ||
							!wjDResponseDEdgeResponse.accumulatePartialFirstDerivative (0, i + 1,
								wjDResponseDEdgeParams.firstDerivative (0,
									org.drip.spline.segment.LatentStateResponseModel.RIGHT_NODE_VALUE_PARAMETER_INDEX)))
					return null;
			}
		}

		return wjDResponseDEdgeResponse;
	}

	/**
	 * CalibratableMultiSegmentSequence constructor - Construct a sequence of Basis Spline Segments
	 * 
	 * @param strName Name of the Stretch
	 * @param aCS Array of Segments
	 * @param aSCBC Array of Segment Builder Parameters
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public CalibratableMultiSegmentSequence (
		final java.lang.String strName,
		final org.drip.spline.segment.LatentStateResponseModel[] aCS,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC)
		throws java.lang.Exception
	{
		super (null);

		if (null == aCS || null == aSCBC || null == (_strName = strName) || _strName.isEmpty())
			throw new java.lang.Exception ("CalibratableMultiSegmentSequence ctr => Invalid inputs!");

		int iNumSegment = aCS.length;
		_aLSRM = new org.drip.spline.segment.LatentStateResponseModel[iNumSegment];
		_aSCBC = new org.drip.spline.params.SegmentCustomBuilderControl[iNumSegment];

		if (0 == iNumSegment || iNumSegment != aSCBC.length)
			throw new java.lang.Exception ("CalibratableMultiSegmentSequence ctr => Invalid inputs!");

		for (int i = 0; i < iNumSegment; ++i) {
			if (null == (_aLSRM[i] = aCS[i]) || null == (_aSCBC[i] = aSCBC[i]))
				throw new java.lang.Exception ("CalibratableMultiSegmentSequence ctr => Invalid inputs!");
		}
	}

	@Override public java.lang.String name()
	{
		return _strName;
	}

	@Override public org.drip.spline.segment.LatentStateResponseModel[] segments()
	{
		return _aLSRM;
	}

	@Override public org.drip.spline.params.SegmentCustomBuilderControl[] segmentBuilderControl()
	{
		return _aSCBC;
	}

	@Override public boolean setup (
		final org.drip.spline.stretch.SegmentSequenceBuilder ssb,
		final int iCalibrationDetail)
	{
		if (null == (_ssb = ssb) || !_ssb.setStretch (this)) return false;

		if (org.drip.spline.stretch.BoundarySettings.BOUNDARY_CONDITION_FLOATING ==
			_ssb.getCalibrationBoundaryCondition().boundaryCondition()) {
			if (!_ssb.calibStartingSegment (0.) || !_ssb.calibSegmentSequence (1) ||
				!_ssb.manifestMeasureSensitivity (0.))
				return false;
		} else if (0 != (org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE & iCalibrationDetail)) {
			org.drip.function.r1tor1solver.FixedPointFinderOutput fpop = null;

			if (null == fpop || !fpop.containsRoot()) {
				try {
					fpop = new org.drip.function.r1tor1solver.FixedPointFinderZheng (0., this,
						true).findRoot();
				} catch (java.lang.Exception e) {
					e.printStackTrace();

					return false;
				}
			}

			if (null == fpop || !org.drip.quant.common.NumberUtil.IsValid (fpop.getRoot()) ||
				!_ssb.manifestMeasureSensitivity (0.)) {
				System.out.println ("FPOP: " + fpop);

				return false;
			}
		}

		if (0 != (org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE_JACOBIAN & iCalibrationDetail)) {
			int iNumSegment = _aLSRM.length;

			try {
				if (null == (_wjDCoeffDEdgeParams = new org.drip.quant.calculus.WengertJacobian
					(_aLSRM[0].basisEvaluator().numBasis(), iNumSegment + 1)))
					return false;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return false;
			}

			org.drip.quant.calculus.WengertJacobian wjHead = _aLSRM[0].jackDCoeffDEdgeInputs();

			if (!setDCoeffDEdgeParams (0, wjHead) || !setDCoeffDEdgeParams (1, wjHead)) return false;

			for (int i = 1; i < iNumSegment; ++i) {
				if (!setDCoeffDEdgeParams (i + 1, _aLSRM[i].jackDCoeffDEdgeInputs())) return false;
			}
		}

		return true;
	}

	@Override public boolean setup (
		final org.drip.spline.params.SegmentResponseValueConstraint srvcLeading,
		final org.drip.spline.params.SegmentResponseValueConstraint[] aSRVC,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final org.drip.spline.stretch.BoundarySettings bs,
		final int iCalibrationDetail)
	{
		try {
			return setup (new org.drip.spline.stretch.CkSegmentSequenceBuilder (srvcLeading, aSRVC, sbfr,
				bs), iCalibrationDetail);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override public boolean setup (
		final double dblLeftStretchResponseValue,
		final org.drip.spline.params.SegmentResponseValueConstraint[] aSRVC,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final org.drip.spline.stretch.BoundarySettings bs,
		final int iCalibrationDetail)
	{
		return setup (org.drip.spline.params.SegmentResponseValueConstraint.FromPredictorResponsePair
			(getLeftPredictorOrdinateEdge(), dblLeftStretchResponseValue), aSRVC, sbfr, bs,
				iCalibrationDetail);
	}

	@Override public boolean setup (
		final double dblLeftStretchResponseValue,
		final double[] adblSegmentRightResponseValue,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final org.drip.spline.stretch.BoundarySettings bs,
		final int iCalibrationDetail)
	{
		int iNumSegment = _aLSRM.length;
		org.drip.spline.params.SegmentResponseValueConstraint[] aSRVCRight = new
			org.drip.spline.params.SegmentResponseValueConstraint[iNumSegment];

		if (0 == iNumSegment || iNumSegment != adblSegmentRightResponseValue.length) return false;

		try {
			for (int i = 0; i < iNumSegment; ++i)
				aSRVCRight[i] = new org.drip.spline.params.SegmentResponseValueConstraint (new double[]
					{_aLSRM[i].right()}, new double[] {1.}, adblSegmentRightResponseValue[i]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return false;
		}

		return setup (dblLeftStretchResponseValue, aSRVCRight, sbfr, bs, iCalibrationDetail);
	}

	@Override public boolean setupHermite (
		final org.drip.spline.params.SegmentPredictorResponseDerivative[] aSPRDLeft,
		final org.drip.spline.params.SegmentPredictorResponseDerivative[] aSPRDRight,
		final org.drip.spline.params.SegmentResponseValueConstraint[][] aaSRVC,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final int iSetupMode)
	{
		if (null == aSPRDLeft || null == aSPRDRight) return false;

		int iNumSegment = _aLSRM.length;

		if (iNumSegment != aSPRDLeft.length || iNumSegment != aSPRDRight.length || (null != aaSRVC &&
			iNumSegment != aaSRVC.length))
			return false;

		for (int i = 0; i < iNumSegment; ++i) {
			try {
				int iNumSegmentConstraint = 0;
				org.drip.spline.params.SegmentBasisFlexureConstraint[] aSBFC = null;

				if (null != aaSRVC && null != aaSRVC[i]) {
					aSBFC = new org.drip.spline.params.SegmentBasisFlexureConstraint[iNumSegmentConstraint =
						aaSRVC[i].length];

					for (int j = 0; i < iNumSegmentConstraint; ++j)
						aSBFC[j] = null == aaSRVC[i][j] ? null : aaSRVC[i][j].responseIndexedBasisConstraint
							(_aLSRM[i].basisEvaluator(), _aLSRM[i]);
				}

				if (0 != (org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE & iSetupMode) &&
					!_aLSRM[i].calibrateState (new org.drip.spline.params.SegmentStateCalibrationInputs (new
						double[] {_aLSRM[i].left(), _aLSRM[i].right()}, new double[]
							{aSPRDLeft[i].responseValue(), aSPRDRight[i].responseValue()},
								aSPRDLeft[i].getDResponseDPredictorOrdinate(),
									aSPRDRight[i].getDResponseDPredictorOrdinate(), aSBFC, null == sbfr ?
										null : sbfr.sizeToSegment (_aLSRM[i]))))
					return false;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return false;
			}
		}

		if (0 != (org.drip.spline.stretch.MultiSegmentSequence.CALIBRATE_JACOBIAN & iSetupMode)) {
			try {
				if (null == (_wjDCoeffDEdgeParams = new org.drip.quant.calculus.WengertJacobian
					(_aLSRM[0].basisEvaluator().numBasis(), iNumSegment + 1)))
					return false;
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return false;
			}

			org.drip.quant.calculus.WengertJacobian wjDCoeffDEdgeParamsHead =
				_aLSRM[0].jackDCoeffDEdgeInputs();

			if (!setDCoeffDEdgeParams (0, wjDCoeffDEdgeParamsHead) || !setDCoeffDEdgeParams (1,
				wjDCoeffDEdgeParamsHead))
				return false;

			for (int i = 1; i < iNumSegment; ++i) {
				if (!setDCoeffDEdgeParams (i + 1, _aLSRM[i].jackDCoeffDEdgeInputs())) return false;
			}
		}

		return true;
	}

	@Override public double evaluate (
		final double dblLeftSlope)
		throws java.lang.Exception
	{
		if (null == _ssb || !_ssb.calibStartingSegment (dblLeftSlope) || !_ssb.calibSegmentSequence (1))
			throw new java.lang.Exception
				("CalibratableMultiSegmentSequence::evaluate => cannot set up segments!");

		org.drip.spline.stretch.BoundarySettings bs = _ssb.getCalibrationBoundaryCondition();

		int iBC = bs.boundaryCondition();

		if (org.drip.spline.stretch.BoundarySettings.BOUNDARY_CONDITION_NATURAL == iBC)
			return calcRightEdgeDerivative (bs.rightDerivOrder());

		if (org.drip.spline.stretch.BoundarySettings.BOUNDARY_CONDITION_FINANCIAL == iBC)
			return calcRightEdgeDerivative (bs.rightDerivOrder());

		if (org.drip.spline.stretch.BoundarySettings.BOUNDARY_CONDITION_NOT_A_KNOT == iBC)
			return calcRightEdgeDerivative (bs.rightDerivOrder()) - calcLeftEdgeDerivative
				(bs.leftDerivOrder());

		throw new java.lang.Exception ("CalibratableMultiSegmentSequence::evaluate => Boundary Condition " +
			iBC + " unknown");
	}

	@Override public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBegin) || !org.drip.quant.common.NumberUtil.IsValid
			(dblEnd))
			throw new java.lang.Exception ("CalibratableMultiSegmentSequence::integrate => Invalid Inputs");

		return org.drip.quant.calculus.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
	}

	@Override public boolean setLeftNode (
		final double dblStretchLeftResponse,
		final double dblStretchLeftResponseSlope,
		final double dblStretchRightResponse,
		final org.drip.spline.params.StretchBestFitResponse sbfr)
	{
		return _aLSRM[0].calibrate
			(org.drip.spline.params.SegmentResponseValueConstraint.FromPredictorResponsePair
				(getLeftPredictorOrdinateEdge(), dblStretchLeftResponse), dblStretchLeftResponseSlope,
					org.drip.spline.params.SegmentResponseValueConstraint.FromPredictorResponsePair
						(getRightPredictorOrdinateEdge(), dblStretchRightResponse), null == sbfr ? null :
							sbfr.sizeToSegment (_aLSRM[0]));
	}

	@Override public double responseValue (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		return _aLSRM[containingIndex (dblPredictorOrdinate, true, true)].responseValue
			(dblPredictorOrdinate);
	}

	@Override public double responseValueDerivative (
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		return _aLSRM[containingIndex (dblPredictorOrdinate, true, true)].calcResponseValueDerivative
			(dblPredictorOrdinate, iOrder);
	}

	@Override public org.drip.spline.params.SegmentPredictorResponseDerivative calcSPRD (
		final double dblPredictorOrdinate)
	{
		int iIndex = -1;

		try {
			iIndex = containingIndex (dblPredictorOrdinate, true, true);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		int iCk = _aSCBC[iIndex].inelasticParams().Ck();

		double adblDeriv[] = new double[iCk];

		try {
			for (int i = 0; i < iCk; ++i)
				adblDeriv[i] = _aLSRM[iIndex].calcResponseValueDerivative (dblPredictorOrdinate, i);

			return new org.drip.spline.params.SegmentPredictorResponseDerivative
				(_aLSRM[iIndex].responseValue (dblPredictorOrdinate), adblDeriv);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.quant.calculus.WengertJacobian jackDResponseDCalibrationInput (
		final double dblPredictorOrdinate,
		final int iOrder)
	{
		int iIndex = -1;

		try {
			iIndex = containingIndex (dblPredictorOrdinate, true, true);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return setDResponseDEdgeResponse (iIndex, _aLSRM[iIndex].jackDResponseDEdgeInput
			(dblPredictorOrdinate, iOrder));
	}

	@Override public org.drip.quant.calculus.WengertJacobian jackDResponseDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final double dblPredictorOrdinate,
		final int iOrder)
	{
		int iPriorImpactFadeIndex = 0;
		int iNumSegment = _aLSRM.length;

		try {
			int iIndex = containingIndex (dblPredictorOrdinate, true, true);

			boolean bContainingSegmentImpactFade = _aLSRM[iIndex].impactFade (strManifestMeasure);

			if (!bContainingSegmentImpactFade && 0 != iIndex) {
				for (int i = iIndex - 1; i >= 0; --i) {
					if (_aLSRM[i].impactFade (strManifestMeasure)) {
						iPriorImpactFadeIndex = i;
						break;
					}
				}
			}

			org.drip.quant.calculus.WengertJacobian wjDResponseDManifestMeasure = new
				org.drip.quant.calculus.WengertJacobian (1, iNumSegment);

			for (int i = 0; i < iNumSegment; ++i) {
				double dblDResponseDManifestMeasurei = 0.;

				if (i == iIndex)
					dblDResponseDManifestMeasurei = _aLSRM[i].calcDResponseDManifest (strManifestMeasure,
						dblPredictorOrdinate, iOrder);
				else if (i == iIndex - 1)
					dblDResponseDManifestMeasurei = _aLSRM[i + 1].calcDResponseDPreceedingManifest
						(strManifestMeasure, dblPredictorOrdinate, iOrder);
				else if (!bContainingSegmentImpactFade && i >= iPriorImpactFadeIndex && i < iIndex - 1)
					dblDResponseDManifestMeasurei = _aLSRM[i].calcDResponseDManifest (strManifestMeasure,
						_aLSRM[i].right(), iOrder);

				if (!wjDResponseDManifestMeasure.accumulatePartialFirstDerivative (0, i,
					dblDResponseDManifestMeasurei))
					return null;
			}

			return wjDResponseDManifestMeasure;
		} catch (java.lang.Exception e) {
			// e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.spline.segment.Monotonocity monotoneType (
		final double dblPredictorOrdinate)
	{
		int iIndex = -1;

		try {
			iIndex = containingIndex (dblPredictorOrdinate, true, true);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return _aLSRM[iIndex].monotoneType();
	}

	@Override public boolean isLocallyMonotone()
		throws java.lang.Exception
	{
		int iNumSegment = _aLSRM.length;

		for (int i = 0; i < iNumSegment; ++i) {
			org.drip.spline.segment.Monotonocity mono = null;

			try {
				mono = _aLSRM[i].monotoneType();
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}

			if (null == mono || org.drip.spline.segment.Monotonocity.MONOTONIC != mono.type()) return false;
		}

		return true;
	}

	@Override public boolean isCoMonotone (
		final double[] adblMeasuredResponse)
		throws java.lang.Exception
	{
		int iNumSegment = _aLSRM.length;
		int[] aiMonotoneType = new int[iNumSegment];
		int[] aiNodeMiniMax = new int[iNumSegment + 1];

		if (null == adblMeasuredResponse || adblMeasuredResponse.length != iNumSegment + 1)
			throw new java.lang.Exception
				("CalibratableMultiSegmentSequence::isCoMonotone => Data input inconsistent with the segment");

		for (int i = 0; i < iNumSegment + 1; ++i) {
			if (0 == i || iNumSegment == i)
				aiNodeMiniMax[i] = MONOTONE_PREDICTOR_ORDINATE_NODE;
			else {
				if (adblMeasuredResponse[i - 1] < adblMeasuredResponse[i] && adblMeasuredResponse[i + 1] <
					adblMeasuredResponse[i])
					aiNodeMiniMax[i] = MAXIMA_PREDICTOR_ORDINATE_NODE;
				else if (adblMeasuredResponse[i - 1] > adblMeasuredResponse[i] && adblMeasuredResponse[i + 1]
					> adblMeasuredResponse[i])
					aiNodeMiniMax[i] = MINIMA_PREDICTOR_ORDINATE_NODE;
				else
					aiNodeMiniMax[i] = MONOTONE_PREDICTOR_ORDINATE_NODE;
			}

			if (i < iNumSegment) {
				org.drip.spline.segment.Monotonocity mono = _aLSRM[i].monotoneType();

				if (null != mono) aiMonotoneType[i] = mono.type();
			}
		}

		for (int i = 1; i < iNumSegment; ++i) {
			if (MAXIMA_PREDICTOR_ORDINATE_NODE == aiNodeMiniMax[i]) {
				if (org.drip.spline.segment.Monotonocity.MAXIMA != aiMonotoneType[i] &&
					org.drip.spline.segment.Monotonocity.MAXIMA != aiMonotoneType[i - 1])
					return false;
			} else if (MINIMA_PREDICTOR_ORDINATE_NODE == aiNodeMiniMax[i]) {
				if (org.drip.spline.segment.Monotonocity.MINIMA != aiMonotoneType[i] &&
					org.drip.spline.segment.Monotonocity.MINIMA != aiMonotoneType[i - 1])
					return false;
			}
		}

		return true;
	}

	@Override public boolean isKnot (
		final double dblPredictorOrdinate)
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate)) return false;

		int iNumSegment = _aLSRM.length;

		for (int i = 0; i < iNumSegment; ++i) {
			if (dblPredictorOrdinate == _aLSRM[i].left()) return false;
		}

		return dblPredictorOrdinate == _aLSRM[iNumSegment - 1].left();
	}

	@Override public double calcLeftEdgeDerivative (
		final int iOrder)
		throws java.lang.Exception
	{
		org.drip.spline.segment.LatentStateResponseModel lsrm = _aLSRM[0];

		return lsrm.calcResponseValueDerivative (lsrm.left(), iOrder);
	}

	@Override public double calcRightEdgeDerivative (
		final int iOrder)
		throws java.lang.Exception
	{
		org.drip.spline.segment.LatentStateResponseModel lsrm = _aLSRM[_aLSRM.length - 1];

		return lsrm.calcResponseValueDerivative (lsrm.right(), iOrder);
	}

	@Override public boolean resetNode (
		final int iPredictorOrdinateIndex,
		final double dblResponseReset)
	{
		if (0 == iPredictorOrdinateIndex || 1 == iPredictorOrdinateIndex || _aLSRM.length <
			iPredictorOrdinateIndex || !org.drip.quant.common.NumberUtil.IsValid (dblResponseReset))
			return false;

		return _aLSRM[iPredictorOrdinateIndex - 1].calibrate (_aLSRM[iPredictorOrdinateIndex - 2],
			dblResponseReset, null);
	}

	@Override public boolean resetNode (
		final int iPredictorOrdinateIndex,
		final org.drip.spline.params.SegmentResponseValueConstraint srvcReset)
	{
		if (0 == iPredictorOrdinateIndex || 1 == iPredictorOrdinateIndex || _aLSRM.length <
			iPredictorOrdinateIndex || null == srvcReset)
			return false;

		return _aLSRM[iPredictorOrdinateIndex - 1].calibrate (_aLSRM[iPredictorOrdinateIndex - 2], srvcReset,
			null);
	}

	@Override public org.drip.function.definition.R1ToR1 toAU()
	{
		org.drip.function.definition.R1ToR1 au = new
			org.drip.function.definition.R1ToR1 (null)
		{
			@Override public double evaluate (
				final double dblVariate)
				throws java.lang.Exception
			{
				return responseValue (dblVariate);
			}

			@Override public double derivative (
				final double dblVariate,
				final int iOrder)
				throws java.lang.Exception
			{
				return responseValueDerivative (dblVariate, iOrder);
			}
		};

		return au;
	}

	@Override public boolean in (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate))
			throw new java.lang.Exception ("CalibratableMultiSegmentSequence::in => Invalid inputs");

		return dblPredictorOrdinate >= getLeftPredictorOrdinateEdge() && dblPredictorOrdinate <=
			getRightPredictorOrdinateEdge();
	}

	@Override public double getLeftPredictorOrdinateEdge()
	{
		return _aLSRM[0].left();
	}

	@Override public double getRightPredictorOrdinateEdge()
	{
		return _aLSRM[_aLSRM.length - 1].right();
	}

	@Override public int containingIndex (
		final double dblPredictorOrdinate,
		final boolean bIncludeLeft,
		final boolean bIncludeRight)
		throws java.lang.Exception
	{
		if (!in (dblPredictorOrdinate))
			throw new java.lang.Exception
				("CalibratableMultiSegmentSequence::containingIndex => Predictor Ordinate not in the Stretch Range");

		int iNumSegment = _aLSRM.length;

		for (int i = 0 ; i < iNumSegment; ++i) {
			boolean bLeftValid = bIncludeLeft ? _aLSRM[i].left() <= dblPredictorOrdinate : _aLSRM[i].left() <
				dblPredictorOrdinate;

			boolean bRightValid = bIncludeRight ? _aLSRM[i].right() >= dblPredictorOrdinate :
				_aLSRM[i].right() > dblPredictorOrdinate;

			if (bLeftValid && bRightValid) return i;
		}

		throw new java.lang.Exception
			("CalibratableMultiSegmentSequence::containingIndex => Cannot locate Containing Index");
	}

	@Override public CalibratableMultiSegmentSequence clipLeft (
		final java.lang.String strName,
		final double dblPredictorOrdinate)
	{
		int iNumSegment = _aLSRM.length;
		int iContainingPredictorOrdinateIndex = 0;

		try {
			iContainingPredictorOrdinateIndex = containingIndex (dblPredictorOrdinate, true, false);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		int iNumClippedSegment = iNumSegment - iContainingPredictorOrdinateIndex;
		org.drip.spline.segment.LatentStateResponseModel[] aCS = new
			org.drip.spline.segment.LatentStateResponseModel[iNumClippedSegment];
		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
			org.drip.spline.params.SegmentCustomBuilderControl[iNumClippedSegment];

		for (int i = 0; i < iNumClippedSegment; ++i) {
			if (null == (aCS[i] = 0 == i ?
				_aLSRM[iContainingPredictorOrdinateIndex].clipLeftOfPredictorOrdinate (dblPredictorOrdinate)
					: _aLSRM[i + iContainingPredictorOrdinateIndex]))
				return null;

			aSCBC[i] = _aSCBC[i + iContainingPredictorOrdinateIndex];
		}

		try {
			return new CalibratableMultiSegmentSequence (strName, aCS, aSCBC);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public CalibratableMultiSegmentSequence clipRight (
		final java.lang.String strName,
		final double dblPredictorOrdinate)
	{
		int iContainingPredictorOrdinateIndex = 0;

		try {
			iContainingPredictorOrdinateIndex = containingIndex (dblPredictorOrdinate, false, true);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.spline.segment.LatentStateResponseModel[] aCS = new
			org.drip.spline.segment.LatentStateResponseModel[iContainingPredictorOrdinateIndex + 1];
		org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC = new
			org.drip.spline.params.SegmentCustomBuilderControl[iContainingPredictorOrdinateIndex + 1];

		for (int i = 0; i <= iContainingPredictorOrdinateIndex; ++i) {
			if (null == (aCS[i] = iContainingPredictorOrdinateIndex == i ?
				_aLSRM[iContainingPredictorOrdinateIndex].clipRightOfPredictorOrdinate (dblPredictorOrdinate)
					: _aLSRM[i]))
				return null;

			aSCBC[i] = _aSCBC[i];
		}

		try {
			return new CalibratableMultiSegmentSequence (strName, aCS, aSCBC);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public double curvatureDPE()
		throws java.lang.Exception
	{
		double dblDPE = 0.;

		for (org.drip.spline.segment.LatentStateResponseModel lsrm : _aLSRM)
			dblDPE += lsrm.curvatureDPE();

		return dblDPE;
	}

	@Override public double lengthDPE()
		throws java.lang.Exception
	{
		double dblDPE = 0.;

		for (org.drip.spline.segment.LatentStateResponseModel lsrm : _aLSRM)
			dblDPE += lsrm.lengthDPE();

		return dblDPE;
	}

	@Override public double bestFitDPE (
		final org.drip.spline.params.StretchBestFitResponse rbfr)
		throws java.lang.Exception
	{
		if (null == rbfr) return 0.;

		double dblDPE = 0.;

		for (org.drip.spline.segment.LatentStateResponseModel lsrm : _aLSRM)
			dblDPE += lsrm.bestFitDPE (rbfr.sizeToSegment (lsrm));

		return dblDPE;
	}

	@Override public org.drip.state.representation.MergeSubStretchManager msm()
	{
		return null;
	}

	@Override public java.lang.String displayString()
	{
		java.lang.StringBuffer sb = new java.lang.StringBuffer();

		for (int i = 0; i < _aLSRM.length; ++i)
			sb.append (_aLSRM[i].displayString() + " \n");

		return sb.toString();
	}
}
