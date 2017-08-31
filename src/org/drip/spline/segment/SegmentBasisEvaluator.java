
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
 * This Class implements the BasisEvaluator interface for the given set of the Segment Basis Evaluator
 * 	Functions.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SegmentBasisEvaluator implements org.drip.spline.segment.BasisEvaluator {
	private org.drip.spline.basis.FunctionSet _fs = null;
	private org.drip.spline.segment.LatentStateInelastic _ics = null;
	private org.drip.spline.params.ResponseScalingShapeControl _rssc = null;

	/**
	 * SegmentBasisEvaluator constructor
	 * 
	 * @param fs The Function Set Instance the contains the Basis Function Set
	 * @param rssc The Segment Wide Shape Controller
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public SegmentBasisEvaluator (
		final org.drip.spline.basis.FunctionSet fs,
		final org.drip.spline.params.ResponseScalingShapeControl rssc)
		throws java.lang.Exception
	{
		if (null == (_fs = fs)) throw new java.lang.Exception ("SegmentBasisEvaluator ctr: Invalid Inputs");

		_rssc = rssc;
	}

	@Override public int numBasis()
	{
		return _fs.numBasis();
	}

	@Override public boolean setContainingInelastics (
		final org.drip.spline.segment.LatentStateInelastic ics)
	{
		_ics = ics;
		return true;
	}

	@Override public org.drip.spline.segment.BasisEvaluator replicate()
	{
		try {
			return new SegmentBasisEvaluator (_fs, _rssc);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public double shapedBasisFunctionResponse (
		final double dblPredictorOrdinate,
		final int iBasisFunctionIndex)
		throws java.lang.Exception
	{
		double dblX = null == _ics ? dblPredictorOrdinate : _ics.localize (dblPredictorOrdinate);

		double dblResponseValue = _fs.indexedBasisFunction (iBasisFunctionIndex).evaluate (dblX);

		return dblResponseValue * (null == _rssc ? 1. : _rssc.shapeController().evaluate (_rssc.isLocal() &&
			null != _ics ? _ics.localize (dblPredictorOrdinate) : dblPredictorOrdinate));
	}

	@Override public double unshapedResponseValue (
		final double[] adblResponseBasisCoeff,
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		double dblResponse = 0.;

		int iNumBasis = numBasis();

		for (int i = 0; i < iNumBasis; ++i) {
			dblResponse += adblResponseBasisCoeff[i] * _fs.indexedBasisFunction (i).evaluate (null == _ics ?
				dblPredictorOrdinate : _ics.localize (dblPredictorOrdinate));
		}

		return dblResponse;
	}

	@Override public double responseValue (
		final double[] adblResponseBasisCoeff,
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (null == _rssc) return unshapedResponseValue (adblResponseBasisCoeff, dblPredictorOrdinate);

		return unshapedResponseValue (adblResponseBasisCoeff, dblPredictorOrdinate) *
			_rssc.shapeController().evaluate (_rssc.isLocal() && null != _ics ? _ics.localize
				(dblPredictorOrdinate) : dblPredictorOrdinate);
	}

	@Override public double shapedBasisFunctionDerivative (
		final double dblPredictorOrdinate,
		final int iOrder,
		final int iBasisFunctionIndex)
		throws java.lang.Exception
	{
		double dblX = null == _ics ? dblPredictorOrdinate : _ics.localize (dblPredictorOrdinate);

		if (null == _rssc) return _fs.indexedBasisFunction (iBasisFunctionIndex).derivative (dblX, iOrder);

		double dblShapeControllerPredictorOrdinate = _rssc.isLocal() && null != _ics ? dblX :
			dblPredictorOrdinate;

		double dblResponseDerivative = 0.;

		for (int i = 0; i <= iOrder; ++i) {
			double dblBasisFunctionDeriv = 0 == i ? _fs.indexedBasisFunction (iBasisFunctionIndex).evaluate
				(dblX) : _fs.indexedBasisFunction (iBasisFunctionIndex).derivative (dblX, i);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblBasisFunctionDeriv))
				throw new java.lang.Exception
					("SegmentBasisEvaluator::shapedBasisFunctionDerivative => Cannot compute Basis Function Derivative");

			double dblShapeControlDeriv = iOrder == i ? _rssc.shapeController().evaluate
				(dblShapeControllerPredictorOrdinate) : _rssc.shapeController().derivative
					(dblShapeControllerPredictorOrdinate, iOrder - i);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblShapeControlDeriv))
				throw new java.lang.Exception
					("SegmentBasisEvaluator::shapedBasisFunctionDerivative => Cannot compute Shape Control Derivative");

			double dblBasisFunctionDerivScale = 1.;
			double dblShapeControllerDerivScale = 1.;

			if (null != _ics) {
				for (int j = 0; j < i; ++j)
					dblBasisFunctionDerivScale /= _ics.width();
			}

			if (_rssc.isLocal() && null != _ics) {
				for (int j = 0; j < iOrder - i; ++j)
					dblShapeControllerDerivScale /= _ics.width();
			}

			dblResponseDerivative += (org.drip.quant.common.NumberUtil.NCK (iOrder, i) *
				dblBasisFunctionDeriv * dblBasisFunctionDerivScale * dblShapeControllerDerivScale *
					dblShapeControlDeriv);
		}

		return dblResponseDerivative;
	}

	@Override public double unshapedBasisFunctionDerivative (
		final double[] adblResponseBasisCoeff,
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		double dblDerivative = 0.;

		int iNumBasis = numBasis();

		double dblX = null == _ics ? dblPredictorOrdinate : _ics.localize (dblPredictorOrdinate);

		for (int i = 0; i < iNumBasis; ++i)
			dblDerivative += adblResponseBasisCoeff[i] * _fs.indexedBasisFunction (i).derivative (dblX,
				iOrder);

		return dblDerivative;
	}

	@Override public double responseValueDerivative (
		final double[] adblResponseBasisCoeff,
		final double dblPredictorOrdinate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (null == _rssc)
			return unshapedBasisFunctionDerivative (adblResponseBasisCoeff, dblPredictorOrdinate, iOrder);

		double dblShapeControllerPredictorOrdinate = _rssc.isLocal() && null != _ics ? _ics.localize
			(dblPredictorOrdinate) : dblPredictorOrdinate;

		double dblResponseDerivative = 0.;

		for (int i = 0; i <= iOrder; ++i) {
			double dblBasisFunctionDeriv = 0 == i ? unshapedResponseValue (adblResponseBasisCoeff,
				dblPredictorOrdinate) : unshapedBasisFunctionDerivative (adblResponseBasisCoeff,
					dblPredictorOrdinate, i);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblBasisFunctionDeriv))
				throw new java.lang.Exception
					("SegmentBasisEvaluator::responseValueDerivative => Cannot compute Basis Function Derivative");

			double dblShapeControlDeriv = iOrder == i ? _rssc.shapeController().evaluate
				(dblShapeControllerPredictorOrdinate) : _rssc.shapeController().derivative
					(dblShapeControllerPredictorOrdinate, iOrder - i);

			if (!org.drip.quant.common.NumberUtil.IsValid (dblShapeControlDeriv))
				throw new java.lang.Exception
					("SegmentBasisEvaluator::responseValueDerivative => Cannot compute Shape Control Derivative");

			double dblBasisFunctionDerivScale = 1.;
			double dblShapeControllerDerivScale = 1.;

			if (null != _ics) {
				for (int j = 0; j < i; ++j)
					dblBasisFunctionDerivScale /= _ics.width();
			}

			if (_rssc.isLocal() && null != _ics) {
				for (int j = 0; j < iOrder - i; ++j)
					dblShapeControllerDerivScale /= _ics.width();
			}

			dblResponseDerivative += (org.drip.quant.common.NumberUtil.NCK (iOrder, i) *
				dblBasisFunctionDeriv * dblBasisFunctionDerivScale * dblShapeControllerDerivScale *
					dblShapeControlDeriv);
		}

		return dblResponseDerivative;
	}
}
