
package org.drip.spline.pchip;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.integration.R1ToR1Integrator;

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
 * <i>MonotoneConvexHaganWest</i> implements the regime using the Hagan and West (2006) Estimator. It
 * 	provides the following functionality:
 *
 * <br>
 *  <ul>
 * 		<li>Create an instance of <i>MonotoneConvexHaganWest</i></li>
 * 		<li>Enforce the Positivity of the Inferred Response Values</li>
 * 		<li>Create an Ameliorated Instance of the Current Instance</li>
 * 		<li>Retrieve the Array of Predictor Ordinates</li>
 * 		<li>Retrieve the Array of Response Values</li>
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
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spline/pchip/README.md">Monotone Convex Themed PCHIP Splines</a></td></tr>
 *  </table>
 *  <br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MonotoneConvexHaganWest
	extends R1ToR1
{
	private double[] _observationArray = null;
	private boolean _linearNodeInference = true;
	private double[] _responseValueArray = null;
	private double[] _predictorOrdinateArray = null;
	private double[] _responseZScoreLeftArray = null;
	private R1ToR1[] _univariateFunctionArray = null;
	private double[] _responseZScoreRightArray = null;

	class Case1Univariate
		extends R1ToR1
	{
		private double _responseZScoreLeft = Double.NaN;
		private double _responseZScoreRight = Double.NaN;
		private double _predictorOrdinateLeft = Double.NaN;
		private double _predictorOrdinateRight = Double.NaN;

		Case1Univariate (
			final double predictorOrdinateLeft,
			final double predictorOrdinateRight,
			final double responseZScoreLeft,
			final double responseZScoreRight)
		{
			super (null);

			_responseZScoreLeft = responseZScoreLeft;
			_responseZScoreRight = responseZScoreRight;
			_predictorOrdinateLeft = predictorOrdinateLeft;
			_predictorOrdinateRight = predictorOrdinateRight;
		}

		@Override public double evaluate (
			final double predictorOrdinate)
			throws Exception
		{
			if (!NumberUtil.IsValid (predictorOrdinate) || predictorOrdinate < _predictorOrdinateLeft ||
				predictorOrdinate > _predictorOrdinateRight) {
				throw new Exception ("Case1Univariate::evaluate => Invalid Inputs");
			}

			double x = (predictorOrdinate - _predictorOrdinateLeft) / (
				_predictorOrdinateRight - _predictorOrdinateLeft
			);
			return _responseZScoreLeft * (1. - 4. * x + 3. * x * x) + _responseZScoreRight * (
				-2. * x + 3. * x * x
			);
		}

		@Override public double integrate (
			final double begin,
			final double end)
			throws Exception
		{
			return R1ToR1Integrator.Boole (this, begin, end);
		}
	}

	class Case2Univariate
		extends R1ToR1
	{
		private double _eta = Double.NaN;
		private double _responseZScoreLeft = Double.NaN;
		private double _responseZScoreRight = Double.NaN;
		private double _predictorOrdinateLeft = Double.NaN;
		private double _predictorOrdinateRight = Double.NaN;

		Case2Univariate (
			final double predictorOrdinateLeft,
			final double predictorOrdinateRight,
			final double responseZScoreLeft,
			final double responseZScoreRight)
		{
			super (null);

			_responseZScoreLeft = responseZScoreLeft;
			_responseZScoreRight = responseZScoreRight;
			_predictorOrdinateLeft = predictorOrdinateLeft;
			_predictorOrdinateRight = predictorOrdinateRight;
			_eta = _responseZScoreLeft != _responseZScoreRight ? (
				_responseZScoreRight + 2. * _responseZScoreLeft
			) / (_responseZScoreRight - _responseZScoreLeft) : 0.;
		}

		@Override public double evaluate (
			final double predictorOrdinate)
			throws Exception
		{
			if (!NumberUtil.IsValid (predictorOrdinate) || predictorOrdinate < _predictorOrdinateLeft ||
				predictorOrdinate > _predictorOrdinateRight) {
				throw new Exception ("Case2Univariate::evaluate => Invalid Inputs");
			}

			if (_responseZScoreLeft == _responseZScoreRight) {
				return _responseZScoreRight;
			}

			double x = (predictorOrdinate - _predictorOrdinateLeft) / (
				_predictorOrdinateRight - _predictorOrdinateLeft
			);
			return x <= _eta ? _responseZScoreLeft : _responseZScoreLeft + (
				_responseZScoreRight - _responseZScoreLeft
			) * (x - _eta) * (x - _eta) / (1. - _eta) / (1. - _eta);
		}

		@Override public double integrate (
			final double begin,
			final double end)
			throws Exception
		{
			return R1ToR1Integrator.Boole (this, begin, end);
		}
	}

	class Case3Univariate
		extends R1ToR1
	{
		private double _eta = Double.NaN;
		private double _responseZScoreLeft = Double.NaN;
		private double _dblResponseZScoreRight = Double.NaN;
		private double _dblPredictorOrdinateLeft = Double.NaN;
		private double _dblPredictorOrdinateRight = Double.NaN;

		Case3Univariate (
			final double dblPredictorOrdinateLeft,
			final double dblPredictorOrdinateRight,
			final double dblResponseZScoreLeft,
			final double dblResponseZScoreRight)
		{
			super (null);

			_responseZScoreLeft = dblResponseZScoreLeft;
			_dblResponseZScoreRight = dblResponseZScoreRight;
			_dblPredictorOrdinateLeft = dblPredictorOrdinateLeft;
			_dblPredictorOrdinateRight = dblPredictorOrdinateRight;
			_eta = _responseZScoreLeft != _dblResponseZScoreRight ? 3. * _dblResponseZScoreRight /
				(_dblResponseZScoreRight - _responseZScoreLeft) : 0.;
		}

		@Override public double evaluate (
			final double dblPredictorOrdinate)
			throws java.lang.Exception
		{
			if (!org.drip.numerical.common.NumberUtil.IsValid (dblPredictorOrdinate) || dblPredictorOrdinate <
				_dblPredictorOrdinateLeft || dblPredictorOrdinate > _dblPredictorOrdinateRight)
				throw new java.lang.Exception ("Case3Univariate::evaluate => Invalid Inputs");

			if (_responseZScoreLeft == _dblResponseZScoreRight) return _dblResponseZScoreRight;

			double dblX = (dblPredictorOrdinate - _dblPredictorOrdinateLeft) / (_dblPredictorOrdinateRight -
				_dblPredictorOrdinateLeft);
			return dblX < _eta ? _responseZScoreLeft + (_responseZScoreLeft -
				_dblResponseZScoreRight) * (_eta - dblX) * (_eta - dblX) / _eta / _eta :
					_dblResponseZScoreRight;
		}

		@Override public double integrate (
			final double dblBegin,
			final double dblEnd)
			throws java.lang.Exception
		{
			return org.drip.numerical.integration.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
		}
	}

	class Case4Univariate extends org.drip.function.definition.R1ToR1 {
		private double _dblA = java.lang.Double.NaN;
		private double _dblEta = java.lang.Double.NaN;
		private double _dblResponseZScoreLeft = java.lang.Double.NaN;
		private double _dblResponseZScoreRight = java.lang.Double.NaN;
		private double _dblPredictorOrdinateLeft = java.lang.Double.NaN;
		private double _dblPredictorOrdinateRight = java.lang.Double.NaN;

		Case4Univariate (
			final double dblPredictorOrdinateLeft,
			final double dblPredictorOrdinateRight,
			final double dblResponseZScoreLeft,
			final double dblResponseZScoreRight)
		{
			super (null);

			_dblResponseZScoreLeft = dblResponseZScoreLeft;
			_dblResponseZScoreRight = dblResponseZScoreRight;
			_dblPredictorOrdinateLeft = dblPredictorOrdinateLeft;
			_dblPredictorOrdinateRight = dblPredictorOrdinateRight;

			if (_dblResponseZScoreLeft != _dblResponseZScoreRight) {
				_dblEta = _dblResponseZScoreRight / (_dblResponseZScoreRight - _dblResponseZScoreLeft);
				_dblA = -1. * _dblResponseZScoreLeft * _dblResponseZScoreRight / (_dblResponseZScoreRight -
					_dblResponseZScoreLeft);
			} else {
				_dblA = 0.;
				_dblEta = 0.;
			}
		}

		@Override public double evaluate (
			final double dblPredictorOrdinate)
			throws java.lang.Exception
		{
			if (!org.drip.numerical.common.NumberUtil.IsValid (dblPredictorOrdinate) || dblPredictorOrdinate <
				_dblPredictorOrdinateLeft || dblPredictorOrdinate > _dblPredictorOrdinateRight)
				throw new java.lang.Exception ("Case4Univariate::evaluate => Invalid Inputs");

			if (_dblResponseZScoreLeft == _dblResponseZScoreRight) return _dblResponseZScoreRight;

			double dblX = (dblPredictorOrdinate - _dblPredictorOrdinateLeft) / (_dblPredictorOrdinateRight -
				_dblPredictorOrdinateLeft);
			return dblX < _dblEta ? _dblA + (_dblResponseZScoreLeft - _dblA) * (_dblEta - dblX) * (_dblEta -
				dblX) / _dblEta / _dblEta : _dblA + (_dblResponseZScoreRight - _dblA) * (dblX - _dblEta) *
					(dblX - _dblEta) / (1. - _dblEta) / (1. - _dblEta);
		}

		@Override public double integrate (
			final double dblBegin,
			final double dblEnd)
			throws java.lang.Exception
		{
			return org.drip.numerical.integration.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
		}
	}

	/**
	 * Create an instance of <i>MonotoneConvexHaganWest</i>
	 * 
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblObservation Array of Observations
	 * @param bLinearNodeInference Apply Linear Node Inference from Observations
	 * 
	 * @return Instance of <i>MonotoneConvexHaganWest</i>
	 */

	public static final MonotoneConvexHaganWest Create (
		final double[] adblPredictorOrdinate,
		final double[] adblObservation,
		final boolean bLinearNodeInference)
	{
		MonotoneConvexHaganWest mchw = null;

		try {
			mchw = new MonotoneConvexHaganWest (adblPredictorOrdinate, adblObservation,
				bLinearNodeInference);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return mchw.inferResponseValues() && mchw.inferResponseZScores() && mchw.generateUnivariate() ? mchw
			: null;
	}

	private MonotoneConvexHaganWest (
		final double[] adblPredictorOrdinate,
		final double[] adblObservation,
		final boolean bLinearNodeInference)
		throws java.lang.Exception
	{
		super (null);

		if (null == (_observationArray = adblObservation) || null == (_predictorOrdinateArray =
			adblPredictorOrdinate))
			throw new java.lang.Exception ("MonotoneConvexHaganWest ctr: Invalid Inputs!");

		_linearNodeInference = bLinearNodeInference;
		int iNumObservation = _observationArray.length;

		if (1 >= iNumObservation || iNumObservation + 1 != _predictorOrdinateArray.length)
			throw new java.lang.Exception ("MonotoneConvexHaganWest ctr: Invalid Inputs!");
	}

	private boolean inferResponseValues()
	{
		int iNumPredictorOrdinate = _predictorOrdinateArray.length;
		_responseValueArray = new double[iNumPredictorOrdinate];

		for (int i = 1; i < iNumPredictorOrdinate - 1; ++i) {
			if (_linearNodeInference)
				_responseValueArray[i] = (_predictorOrdinateArray[i] - _predictorOrdinateArray[i - 1]) /
					(_predictorOrdinateArray[i + 1] - _predictorOrdinateArray[i - 1]) * _observationArray[i] +
						(_predictorOrdinateArray[i + 1] - _predictorOrdinateArray[i]) /
							(_predictorOrdinateArray[i + 1] - _predictorOrdinateArray[i - 1]) *
								_observationArray[i - 1];
			else {
				_responseValueArray[i] = 0.;

				if (_observationArray[i - 1] * _observationArray[i] > 0.) {
					_responseValueArray[i] = (_predictorOrdinateArray[i] - _predictorOrdinateArray[i - 1] + 2. *
						(_predictorOrdinateArray[i + 1] - _predictorOrdinateArray[i])) / (3. *
							(_predictorOrdinateArray[i + 1] - _predictorOrdinateArray[i])) /
								_observationArray[i - 1];
					_responseValueArray[i] += (_predictorOrdinateArray[i + 1] - _predictorOrdinateArray[i] + 2.
						* (_predictorOrdinateArray[i] - _predictorOrdinateArray[i - 1])) / (3. *
							(_predictorOrdinateArray[i + 1] - _predictorOrdinateArray[i])) /
								_observationArray[i];
					_responseValueArray[i] = 1. / _responseValueArray[i];
				}
			}
		}

		_responseValueArray[0] = _observationArray[0] - 0.5 * (_responseValueArray[1] - _observationArray[0]);
		_responseValueArray[iNumPredictorOrdinate - 1] = _observationArray[iNumPredictorOrdinate - 2] - 0.5 *
			(_responseValueArray[iNumPredictorOrdinate - 2] - _observationArray[iNumPredictorOrdinate - 2]);
		return true;
	}

	private boolean inferResponseZScores()
	{
		int iNumSegment = _predictorOrdinateArray.length - 1;
		_responseZScoreLeftArray = new double[iNumSegment];
		_responseZScoreRightArray = new double[iNumSegment];

		for (int i = 0; i < iNumSegment; ++i) {
			_responseZScoreLeftArray[i] = _responseValueArray[i] - _observationArray[i];
			_responseZScoreRightArray[i] = _responseValueArray[i + 1] - _observationArray[i];
		}

		return true;
	}

	private boolean generateUnivariate()
	{
		int iNumSegment = _predictorOrdinateArray.length - 1;
		_univariateFunctionArray = new org.drip.function.definition.R1ToR1[iNumSegment];

		for (int i = 0; i < iNumSegment; ++i) {
			if ((_responseZScoreLeftArray[i] > 0. && -0.5 * _responseZScoreLeftArray[i] >=
				_responseZScoreRightArray[i] && _responseZScoreRightArray[i] >= -2. *
					_responseZScoreLeftArray[i]) || (_responseZScoreLeftArray[i] < 0. && -0.5 *
						_responseZScoreLeftArray[i] <= _responseZScoreRightArray[i] &&
							_responseZScoreRightArray[i] <= -2. * _responseZScoreLeftArray[i]))
				_univariateFunctionArray[i] = new Case1Univariate (_predictorOrdinateArray[i], _predictorOrdinateArray[i + 1],
					_responseZScoreLeftArray[i], _responseZScoreRightArray[i]);
			else if ((_responseZScoreLeftArray[i] < 0. && _responseZScoreRightArray[i] > -2. *
				_responseZScoreLeftArray[i]) || (_responseZScoreLeftArray[i] > 0. &&
					_responseZScoreRightArray[i] < -2. * _responseZScoreLeftArray[i]))
				_univariateFunctionArray[i] = new Case2Univariate (_predictorOrdinateArray[i], _predictorOrdinateArray[i + 1],
					_responseZScoreLeftArray[i], _responseZScoreRightArray[i]);
			else if ((_responseZScoreLeftArray[i] > 0. && _responseZScoreRightArray[i] > -0.5 *
				_responseZScoreLeftArray[i]) || (_responseZScoreLeftArray[i] < 0. &&
					_responseZScoreRightArray[i] < -0.5 * _responseZScoreLeftArray[i]))
				_univariateFunctionArray[i] = new Case3Univariate (_predictorOrdinateArray[i], _predictorOrdinateArray[i + 1],
					_responseZScoreLeftArray[i], _responseZScoreRightArray[i]);
			else if ((_responseZScoreLeftArray[i] >= 0. && _responseZScoreRightArray[i] >= 0.) ||
				(_responseZScoreLeftArray[i] <= 0. && _responseZScoreRightArray[i] <= 0.))
				_univariateFunctionArray[i] = new Case4Univariate (_predictorOrdinateArray[i], _predictorOrdinateArray[i + 1],
					_responseZScoreLeftArray[i], _responseZScoreRightArray[i]);
		}

		return true;
	}

	private boolean ameliorate (
		final double[] adblResponseLeftMin,
		final double[] adblResponseLeftMax,
		final double[] adblResponseRightMin,
		final double[] adblResponseRightMax)
	{
		int iNumObservation = _observationArray.length;

		if (iNumObservation != adblResponseLeftMin.length || iNumObservation != adblResponseLeftMax.length ||
			iNumObservation != adblResponseRightMin.length || iNumObservation != adblResponseRightMax.length)
			return false;

		for (int i = 0; i < iNumObservation; ++i) {
			if (_responseValueArray[i] < java.lang.Math.max (adblResponseLeftMin[i], adblResponseRightMin[i])
				|| _responseValueArray[i] > java.lang.Math.min (adblResponseLeftMax[i],
					adblResponseRightMax[i])) {
				if (_responseValueArray[i] < java.lang.Math.max (adblResponseLeftMin[i],
					adblResponseRightMin[i]))
					_responseValueArray[i] = java.lang.Math.max (adblResponseLeftMin[i],
						adblResponseRightMin[i]);
				else if (_responseValueArray[i] > java.lang.Math.min (adblResponseLeftMax[i],
					adblResponseRightMax[i]))
					_responseValueArray[i] = java.lang.Math.min (adblResponseLeftMax[i],
						adblResponseRightMax[i]);
			} else {
				if (_responseValueArray[i] < java.lang.Math.min (adblResponseLeftMax[i],
					adblResponseRightMax[i]))
					_responseValueArray[i] = java.lang.Math.min (adblResponseLeftMax[i],
						adblResponseRightMax[i]);
				else if (_responseValueArray[i] > java.lang.Math.max (adblResponseLeftMin[i],
					adblResponseRightMin[i]))
					_responseValueArray[i] = java.lang.Math.max (adblResponseLeftMin[i],
						adblResponseRightMin[i]);
			}
		}

		if (java.lang.Math.abs (_responseValueArray[0] - _observationArray[0]) > 0.5 * java.lang.Math.abs
			(_responseValueArray[1] - _observationArray[0]))
			_responseValueArray[0] = _observationArray[1] - 0.5 * (_responseValueArray[1] -
				_observationArray[0]);

		if (java.lang.Math.abs (_responseValueArray[iNumObservation] - _observationArray[iNumObservation - 1])
			> 0.5 * java.lang.Math.abs (_responseValueArray[iNumObservation - 1] -
				_observationArray[iNumObservation - 1]))
			_responseValueArray[iNumObservation] = _observationArray[iNumObservation - 1] - 0.5 *
				(_observationArray[iNumObservation - 1] - _responseValueArray[iNumObservation - 1]);

		return inferResponseZScores() && generateUnivariate();
	}

	private int containingIndex (
		final double dblPredictorOrdinate,
		final boolean bIncludeLeft,
		final boolean bIncludeRight)
		throws java.lang.Exception
	{
		int iNumSegment = _univariateFunctionArray.length;

		for (int i = 0 ; i < iNumSegment; ++i) {
			boolean bLeftValid = bIncludeLeft ? _predictorOrdinateArray[i] <= dblPredictorOrdinate :
				_predictorOrdinateArray[i] < dblPredictorOrdinate;

			boolean bRightValid = bIncludeRight ? _predictorOrdinateArray[i + 1] >= dblPredictorOrdinate :
				_predictorOrdinateArray[i + 1] > dblPredictorOrdinate;

			if (bLeftValid && bRightValid) return i;
		}

		throw new java.lang.Exception
			("MonotoneConvexHaganWest::containingIndex => Cannot locate Containing Index");
	}

	@Override public double evaluate (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		int iContainingIndex = containingIndex (dblPredictorOrdinate, true, true);

		return _univariateFunctionArray[iContainingIndex].evaluate (dblPredictorOrdinate) + _observationArray[iContainingIndex];
	}

	/**
	 * Enforce the Positivity of the Inferred Response Values
	 * 
	 * @return TRUE - Positivity Enforcement is successful
	 */

	public boolean enforcePositivity()
	{
		try {
			_responseValueArray[0] = org.drip.numerical.common.NumberUtil.Bound (_responseValueArray[0], 0., 2. *
				_observationArray[0]);

			int iNumObservation = _observationArray.length;

			for (int i = 1; i < iNumObservation; ++i)
				_responseValueArray[i] = org.drip.numerical.common.NumberUtil.Bound (_responseValueArray[i], 0., 2.
					* java.lang.Math.min (_observationArray[i - 1], _observationArray[i]));

			_responseValueArray[iNumObservation] = org.drip.numerical.common.NumberUtil.Bound
				(_responseValueArray[iNumObservation], 0., 2. * _observationArray[iNumObservation - 1]);

			return inferResponseZScores() && generateUnivariate();
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Create an Ameliorated Instance of the Current Instance
	 * 
	 * @param adblResponseLeftMin Response Left Floor
	 * @param adblResponseLeftMax Response Left Ceiling
	 * @param adblResponseRightMin Response Right Floor
	 * @param adblResponseRightMax Response Right Ceiling
	 * @param bEnforcePositivity TRUE - Enforce Positivity
	 * 
	 * @return The Ameliorated Version of the Current Instance
	 */

	public MonotoneConvexHaganWest generateAmelioratedInstance (
		final double[] adblResponseLeftMin,
		final double[] adblResponseLeftMax,
		final double[] adblResponseRightMin,
		final double[] adblResponseRightMax,
		final boolean bEnforcePositivity)
	{
		if (null == adblResponseLeftMin || null == adblResponseLeftMax | null == adblResponseRightMin || null
			== adblResponseRightMax)
			return null;

		int iNumAmelioratedObservation = _observationArray.length + 2;
		int iNumAmelioratedPredicatorOrdinate = _predictorOrdinateArray.length + 2;
		double[] adblAmelioratedObservation = new double[iNumAmelioratedObservation];
		double[] adblAmelioratedPredictorOrdinate = new double[iNumAmelioratedPredicatorOrdinate];

		for (int i = 0; i < iNumAmelioratedPredicatorOrdinate; ++i) {
			if (0 == i)
				adblAmelioratedPredictorOrdinate[0] = -1. * _predictorOrdinateArray[1];
			else if (iNumAmelioratedPredicatorOrdinate - 1 == i)
				adblAmelioratedPredictorOrdinate[i] = 2. * _predictorOrdinateArray[i - 1] -
					_predictorOrdinateArray[i - 2];
			else
				adblAmelioratedPredictorOrdinate[i] = _predictorOrdinateArray[i - 1];
		}

		for (int i = 0; i < iNumAmelioratedObservation; ++i) {
			if (0 == i)
				adblAmelioratedObservation[0] = _observationArray[0] - (_predictorOrdinateArray[1] -
					_predictorOrdinateArray[0]) * (_observationArray[1] - _observationArray[0]) /
						(_predictorOrdinateArray[2] - _predictorOrdinateArray[0]);
			else if (iNumAmelioratedPredicatorOrdinate - 1 == i)
				adblAmelioratedObservation[i] = _observationArray[i - 1] - (_predictorOrdinateArray[i - 1] -
					_predictorOrdinateArray[i - 2]) * (_observationArray[i - 1] - _observationArray[i - 2]) /
						(_predictorOrdinateArray[i - 1] - _predictorOrdinateArray[i - 3]);
			else
				adblAmelioratedObservation[i] = _observationArray[i - 1];
		}

		MonotoneConvexHaganWest mchwAmeliorated = Create (adblAmelioratedPredictorOrdinate,
			adblAmelioratedObservation, _linearNodeInference);

		if (null == mchwAmeliorated || mchwAmeliorated.ameliorate (adblResponseLeftMin, adblResponseLeftMax,
			adblResponseRightMin, adblResponseRightMax))
			return null;

		if (bEnforcePositivity) {
			if (!mchwAmeliorated.enforcePositivity()) return null;
		}

		return mchwAmeliorated;
	}

	/**
	 * Retrieve the Array of Predictor Ordinates
	 * 
	 * @return The Array of Predictor Ordinates
	 */

	public double[] predictorOrdinates()
	{
		return _predictorOrdinateArray;
	}

	/**
	 * Retrieve the Array of Response Values
	 * 
	 * @return The Array of Response Values
	 */

	public double[] responseValues()
	{
		return _responseValueArray;
	}
}
