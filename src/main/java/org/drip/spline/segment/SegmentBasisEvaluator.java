
package org.drip.spline.segment;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>SegmentBasisEvaluator</i> implements the BasisEvaluator interface for the given set of the Segment
 * Basis Evaluator Functions.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/SplineBuilderLibrary.md">Spline Builder Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/README.md">Basis Splines and Linear Compounders across a Broad Family of Spline Basis Functions</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/segment/README.md">Flexure Penalizing Best Fit Segment</a></li>
 *  </ul>
 * <br><br>
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

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblBasisFunctionDeriv))
				throw new java.lang.Exception
					("SegmentBasisEvaluator::shapedBasisFunctionDerivative => Cannot compute Basis Function Derivative");

			double dblShapeControlDeriv = iOrder == i ? _rssc.shapeController().evaluate
				(dblShapeControllerPredictorOrdinate) : _rssc.shapeController().derivative
					(dblShapeControllerPredictorOrdinate, iOrder - i);

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblShapeControlDeriv))
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

			dblResponseDerivative += (org.drip.numerical.common.NumberUtil.NCK (iOrder, i) *
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

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblBasisFunctionDeriv))
				throw new java.lang.Exception
					("SegmentBasisEvaluator::responseValueDerivative => Cannot compute Basis Function Derivative");

			double dblShapeControlDeriv = iOrder == i ? _rssc.shapeController().evaluate
				(dblShapeControllerPredictorOrdinate) : _rssc.shapeController().derivative
					(dblShapeControllerPredictorOrdinate, iOrder - i);

			if (!org.drip.numerical.common.NumberUtil.IsValid (dblShapeControlDeriv))
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

			dblResponseDerivative += (org.drip.numerical.common.NumberUtil.NCK (iOrder, i) *
				dblBasisFunctionDeriv * dblBasisFunctionDerivScale * dblShapeControllerDerivScale *
					dblShapeControlDeriv);
		}

		return dblResponseDerivative;
	}
}
