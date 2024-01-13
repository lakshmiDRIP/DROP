
package org.drip.spline.segment;

import org.drip.numerical.common.NumberUtil;
import org.drip.spline.basis.FunctionSet;
import org.drip.spline.params.ResponseScalingShapeControl;

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
 * <i>SegmentBasisEvaluator</i> implements the BasisEvaluator interface for the given set of the Segment
 * 	Basis Evaluator Functions.
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

public class SegmentBasisEvaluator
	implements BasisEvaluator
{
	private FunctionSet _functionSet = null;
	private LatentStateInelastic _latentStateInelastic = null;
	private ResponseScalingShapeControl _responseScalingShapeControl = null;

	/**
	 * SegmentBasisEvaluator constructor
	 * 
	 * @param functionSet The Function Set Instance the contains the Basis Function Set
	 * @param responseScalingShapeControl The Segment Wide Shape Controller
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public SegmentBasisEvaluator (
		final FunctionSet functionSet,
		final ResponseScalingShapeControl responseScalingShapeControl)
		throws Exception
	{
		if (null == (_functionSet = functionSet)) {
			throw new Exception ("SegmentBasisEvaluator ctr: Invalid Inputs");
		}

		_responseScalingShapeControl = responseScalingShapeControl;
	}

	@Override public int numBasis()
	{
		return _functionSet.numBasis();
	}

	@Override public boolean setContainingInelastics (
		final LatentStateInelastic latentStateInelastic)
	{
		_latentStateInelastic = latentStateInelastic;
		return true;
	}

	@Override public BasisEvaluator replicate()
	{
		try {
			return new SegmentBasisEvaluator (_functionSet, _responseScalingShapeControl);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public double shapedBasisFunctionResponse (
		final double predictorOrdinate,
		final int basisFunctionIndex)
		throws Exception
	{
		return _functionSet.indexedBasisFunction (basisFunctionIndex).evaluate (
			null == _latentStateInelastic ? predictorOrdinate : _latentStateInelastic.localize (
				predictorOrdinate
			)
		) * (
			null == _responseScalingShapeControl ? 1. :
				_responseScalingShapeControl.shapeController().evaluate (
					_responseScalingShapeControl.isLocal() && null != _latentStateInelastic ?
					_latentStateInelastic.localize (predictorOrdinate) : predictorOrdinate
				)
			);
	}

	@Override public double unshapedResponseValue (
		final double[] responseBasisCoefficientArray,
		final double predictorOrdinate)
		throws Exception
	{
		double response = 0.;

		int basisCount = numBasis();

		for (int basisIndex = 0; basisIndex < basisCount; ++basisIndex) {
			response += responseBasisCoefficientArray[basisIndex] * _functionSet.indexedBasisFunction (
				basisIndex
			).evaluate (
				null == _latentStateInelastic ? predictorOrdinate : _latentStateInelastic.localize (
					predictorOrdinate
				)
			);
		}

		return response;
	}

	@Override public double responseValue (
		final double[] responseBasisCoefficientArray,
		final double predictorOrdinate)
		throws Exception
	{
		return unshapedResponseValue (responseBasisCoefficientArray, predictorOrdinate) * (
			null == _responseScalingShapeControl ? 1. :
			_responseScalingShapeControl.shapeController().evaluate (
				_responseScalingShapeControl.isLocal() && null != _latentStateInelastic ?
					_latentStateInelastic.localize (predictorOrdinate) : predictorOrdinate
			)
		);
	}

	@Override public double shapedBasisFunctionDerivative (
		final double predictorOrdinate,
		final int order,
		final int basisFunctionIndex)
		throws Exception
	{
		double x = null == _latentStateInelastic ? predictorOrdinate : _latentStateInelastic.localize (
			predictorOrdinate
		);

		if (null == _responseScalingShapeControl) {
			return _functionSet.indexedBasisFunction (basisFunctionIndex).derivative (x, order);
		}

		double shapeControllerPredictorOrdinate =
			_responseScalingShapeControl.isLocal() && null != _latentStateInelastic ? x : predictorOrdinate;

		double responseDerivative = 0.;

		for (int subIndex = 0; subIndex <= order; ++subIndex) {
			double basisFunctionDerivative = 0 == subIndex ? _functionSet.indexedBasisFunction (
				basisFunctionIndex
			).evaluate (x) : _functionSet.indexedBasisFunction (basisFunctionIndex).derivative (x, subIndex);

			if (!NumberUtil.IsValid (basisFunctionDerivative)) {
				throw new Exception (
					"SegmentBasisEvaluator::shapedBasisFunctionDerivative => Cannot compute Basis Function Derivative"
				);
			}

			double shapeControlDerivative = order == subIndex ?
				_responseScalingShapeControl.shapeController().evaluate (shapeControllerPredictorOrdinate) :
				_responseScalingShapeControl.shapeController().derivative (
					shapeControllerPredictorOrdinate,
					order - subIndex
				);

			if (!NumberUtil.IsValid (shapeControlDerivative)) {
				throw new Exception (
					"SegmentBasisEvaluator::shapedBasisFunctionDerivative => Cannot compute Shape Control Derivative"
				);
			}

			double basisFunctionDerivativeScale = 1.;
			double shapeControllerDerivativeScale = 1.;

			if (null != _latentStateInelastic) {
				for (int subSubIndex = 0; subSubIndex < subIndex; ++subSubIndex) {
					basisFunctionDerivativeScale /= _latentStateInelastic.width();
				}
			}

			if (_responseScalingShapeControl.isLocal() && null != _latentStateInelastic) {
				for (int subSubComplementIndex = 0; subSubComplementIndex < order - subIndex;
					++subSubComplementIndex) {
					shapeControllerDerivativeScale /= _latentStateInelastic.width();
				}
			}

			responseDerivative += (
				NumberUtil.NCK (order, subIndex) * basisFunctionDerivative * basisFunctionDerivativeScale *
				shapeControllerDerivativeScale *
				shapeControlDerivative
			);
		}

		return responseDerivative;
	}

	@Override public double unshapedBasisFunctionDerivative (
		final double[] responseBasisCoefficientArray,
		final double predictorOrdinate,
		final int order)
		throws Exception
	{
		double derivative = 0.;

		int basisCount = numBasis();

		double x = null == _latentStateInelastic ? predictorOrdinate : _latentStateInelastic.localize (
			predictorOrdinate
		);

		for (int basisIndex = 0; basisIndex < basisCount; ++basisIndex) {
			derivative += responseBasisCoefficientArray[basisIndex] * _functionSet.indexedBasisFunction (
				basisIndex
			).derivative (x, order);
		}

		return derivative;
	}

	@Override public double responseValueDerivative (
		final double[] responseBasisCoefficientArray,
		final double predictorOrdinate,
		final int order)
		throws java.lang.Exception
	{
		if (null == _responseScalingShapeControl) {
			return unshapedBasisFunctionDerivative (responseBasisCoefficientArray, predictorOrdinate, order);
		}

		double shapeControllerPredictorOrdinate =
			_responseScalingShapeControl.isLocal() && null != _latentStateInelastic ?
			_latentStateInelastic.localize (predictorOrdinate) : predictorOrdinate;

		double responseDerivative = 0.;

		double latentStateInelasticWidth = _latentStateInelastic.width();

		for (int subOrder = 0; subOrder <= order; ++subOrder) {
			double basisFunctionDerivative = 0 == subOrder ? unshapedResponseValue (
				responseBasisCoefficientArray,
				predictorOrdinate
			) : unshapedBasisFunctionDerivative (responseBasisCoefficientArray, predictorOrdinate, subOrder);

			if (!NumberUtil.IsValid (basisFunctionDerivative)) {
				throw new Exception (
					"SegmentBasisEvaluator::responseValueDerivative => Cannot compute Basis Function Derivative"
				);
			}

			double shapeControlDerivative = order == subOrder ?
				_responseScalingShapeControl.shapeController().evaluate (shapeControllerPredictorOrdinate) :
				_responseScalingShapeControl.shapeController().derivative (
					shapeControllerPredictorOrdinate,
					order - subOrder
				);

			if (!NumberUtil.IsValid (shapeControlDerivative)) {
				throw new Exception (
					"SegmentBasisEvaluator::responseValueDerivative => Cannot compute Shape Control Derivative"
				);
			}

			double basisFunctionDerivativeScale = 1.;
			double shapeControllerDerivativeScale = 1.;

			if (null != _latentStateInelastic) {
				for (int subSubOrder = 0; subSubOrder < subOrder; ++subSubOrder) {
					basisFunctionDerivativeScale /= latentStateInelasticWidth;
				}
			}

			if (_responseScalingShapeControl.isLocal() && null != _latentStateInelastic) {
				for (int subSubOrderComplement = 0; subSubOrderComplement < order - subOrder;
					++subSubOrderComplement) {
					shapeControllerDerivativeScale /= latentStateInelasticWidth;
				}
			}

			responseDerivative += (
				NumberUtil.NCK (order, subOrder) * basisFunctionDerivative * basisFunctionDerivativeScale *
				shapeControllerDerivativeScale * shapeControlDerivative
			);
		}

		return responseDerivative;
	}
}
