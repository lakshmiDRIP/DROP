
package org.drip.spline.pchip;

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
 * This class implements the regime using the Hagan and West (2006) Estimator. It provides the following
 * 	functionality:
 * 	- Static Method to Create an instance of MonotoneConvexHaganWest.
 * 	- Ensure that the estimated regime is monotone an convex.
 * 	- If need be, enforce positivity and/or apply amelioration.
 * 	- Apply segment-by-segment range bounds as needed.
 * 	- Retrieve predictor ordinates/response values.
 *
 * @author Lakshmi Krishnamurthy
 */

public class MonotoneConvexHaganWest extends org.drip.function.definition.R1ToR1 {
	private double[] _adblObservation = null;
	private double[] _adblResponseValue = null;
	private boolean _bLinearNodeInference = true;
	private double[] _adblPredictorOrdinate = null;
	private double[] _adblResponseZScoreLeft = null;
	private double[] _adblResponseZScoreRight = null;
	private org.drip.function.definition.R1ToR1[] _aAU = null;

	class Case1Univariate extends org.drip.function.definition.R1ToR1 {
		private double _dblResponseZScoreLeft = java.lang.Double.NaN;
		private double _dblResponseZScoreRight = java.lang.Double.NaN;
		private double _dblPredictorOrdinateLeft = java.lang.Double.NaN;
		private double _dblPredictorOrdinateRight = java.lang.Double.NaN;

		Case1Univariate (
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
		}

		@Override public double evaluate (
			final double dblPredictorOrdinate)
			throws java.lang.Exception
		{
			if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate) || dblPredictorOrdinate <
				_dblPredictorOrdinateLeft || dblPredictorOrdinate > _dblPredictorOrdinateRight)
				throw new java.lang.Exception ("Case1Univariate::evaluate => Invalid Inputs");

			double dblX = (dblPredictorOrdinate - _dblPredictorOrdinateLeft) / (_dblPredictorOrdinateRight -
				_dblPredictorOrdinateLeft);
			return _dblResponseZScoreLeft * (1. - 4. * dblX + 3. * dblX * dblX) + _dblResponseZScoreRight *
				(-2. * dblX + 3. * dblX * dblX);
		}

		@Override public double integrate (
			final double dblBegin,
			final double dblEnd)
			throws java.lang.Exception
		{
			return org.drip.quant.calculus.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
		}
	}

	class Case2Univariate extends org.drip.function.definition.R1ToR1 {
		private double _dblEta = java.lang.Double.NaN;
		private double _dblResponseZScoreLeft = java.lang.Double.NaN;
		private double _dblResponseZScoreRight = java.lang.Double.NaN;
		private double _dblPredictorOrdinateLeft = java.lang.Double.NaN;
		private double _dblPredictorOrdinateRight = java.lang.Double.NaN;

		Case2Univariate (
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
			_dblEta = _dblResponseZScoreLeft != _dblResponseZScoreRight ? (_dblResponseZScoreRight + 2. *
				_dblResponseZScoreLeft) / (_dblResponseZScoreRight - _dblResponseZScoreLeft) : 0.;
		}

		@Override public double evaluate (
			final double dblPredictorOrdinate)
			throws java.lang.Exception
		{
			if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate) || dblPredictorOrdinate <
				_dblPredictorOrdinateLeft || dblPredictorOrdinate > _dblPredictorOrdinateRight)
				throw new java.lang.Exception ("Case2Univariate::evaluate => Invalid Inputs");

			if (_dblResponseZScoreLeft == _dblResponseZScoreRight) return _dblResponseZScoreRight;

			double dblX = (dblPredictorOrdinate - _dblPredictorOrdinateLeft) / (_dblPredictorOrdinateRight -
				_dblPredictorOrdinateLeft);
			return dblX <= _dblEta ? _dblResponseZScoreLeft : _dblResponseZScoreLeft +
				(_dblResponseZScoreRight - _dblResponseZScoreLeft) * (dblX - _dblEta) * (dblX - _dblEta) /
					(1. - _dblEta) / (1. - _dblEta);
		}

		@Override public double integrate (
			final double dblBegin,
			final double dblEnd)
			throws java.lang.Exception
		{
			return org.drip.quant.calculus.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
		}
	}

	class Case3Univariate extends org.drip.function.definition.R1ToR1 {
		private double _dblEta = java.lang.Double.NaN;
		private double _dblResponseZScoreLeft = java.lang.Double.NaN;
		private double _dblResponseZScoreRight = java.lang.Double.NaN;
		private double _dblPredictorOrdinateLeft = java.lang.Double.NaN;
		private double _dblPredictorOrdinateRight = java.lang.Double.NaN;

		Case3Univariate (
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
			_dblEta = _dblResponseZScoreLeft != _dblResponseZScoreRight ? 3. * _dblResponseZScoreRight /
				(_dblResponseZScoreRight - _dblResponseZScoreLeft) : 0.;
		}

		@Override public double evaluate (
			final double dblPredictorOrdinate)
			throws java.lang.Exception
		{
			if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate) || dblPredictorOrdinate <
				_dblPredictorOrdinateLeft || dblPredictorOrdinate > _dblPredictorOrdinateRight)
				throw new java.lang.Exception ("Case3Univariate::evaluate => Invalid Inputs");

			if (_dblResponseZScoreLeft == _dblResponseZScoreRight) return _dblResponseZScoreRight;

			double dblX = (dblPredictorOrdinate - _dblPredictorOrdinateLeft) / (_dblPredictorOrdinateRight -
				_dblPredictorOrdinateLeft);
			return dblX < _dblEta ? _dblResponseZScoreLeft + (_dblResponseZScoreLeft -
				_dblResponseZScoreRight) * (_dblEta - dblX) * (_dblEta - dblX) / _dblEta / _dblEta :
					_dblResponseZScoreRight;
		}

		@Override public double integrate (
			final double dblBegin,
			final double dblEnd)
			throws java.lang.Exception
		{
			return org.drip.quant.calculus.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
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
			if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate) || dblPredictorOrdinate <
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
			return org.drip.quant.calculus.R1ToR1Integrator.Boole (this, dblBegin, dblEnd);
		}
	}

	/**
	 * Create an instance of MonotoneConvexHaganWest
	 * 
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblObservation Array of Observations
	 * @param bLinearNodeInference Apply Linear Node Inference from Observations
	 * 
	 * @return Instance of MonotoneConvexHaganWest
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

		if (null == (_adblObservation = adblObservation) || null == (_adblPredictorOrdinate =
			adblPredictorOrdinate))
			throw new java.lang.Exception ("MonotoneConvexHaganWest ctr: Invalid Inputs!");

		_bLinearNodeInference = bLinearNodeInference;
		int iNumObservation = _adblObservation.length;

		if (1 >= iNumObservation || iNumObservation + 1 != _adblPredictorOrdinate.length)
			throw new java.lang.Exception ("MonotoneConvexHaganWest ctr: Invalid Inputs!");
	}

	private boolean inferResponseValues()
	{
		int iNumPredictorOrdinate = _adblPredictorOrdinate.length;
		_adblResponseValue = new double[iNumPredictorOrdinate];

		for (int i = 1; i < iNumPredictorOrdinate - 1; ++i) {
			if (_bLinearNodeInference)
				_adblResponseValue[i] = (_adblPredictorOrdinate[i] - _adblPredictorOrdinate[i - 1]) /
					(_adblPredictorOrdinate[i + 1] - _adblPredictorOrdinate[i - 1]) * _adblObservation[i] +
						(_adblPredictorOrdinate[i + 1] - _adblPredictorOrdinate[i]) /
							(_adblPredictorOrdinate[i + 1] - _adblPredictorOrdinate[i - 1]) *
								_adblObservation[i - 1];
			else {
				_adblResponseValue[i] = 0.;

				if (_adblObservation[i - 1] * _adblObservation[i] > 0.) {
					_adblResponseValue[i] = (_adblPredictorOrdinate[i] - _adblPredictorOrdinate[i - 1] + 2. *
						(_adblPredictorOrdinate[i + 1] - _adblPredictorOrdinate[i])) / (3. *
							(_adblPredictorOrdinate[i + 1] - _adblPredictorOrdinate[i])) /
								_adblObservation[i - 1];
					_adblResponseValue[i] += (_adblPredictorOrdinate[i + 1] - _adblPredictorOrdinate[i] + 2.
						* (_adblPredictorOrdinate[i] - _adblPredictorOrdinate[i - 1])) / (3. *
							(_adblPredictorOrdinate[i + 1] - _adblPredictorOrdinate[i])) /
								_adblObservation[i];
					_adblResponseValue[i] = 1. / _adblResponseValue[i];
				}
			}
		}

		_adblResponseValue[0] = _adblObservation[0] - 0.5 * (_adblResponseValue[1] - _adblObservation[0]);
		_adblResponseValue[iNumPredictorOrdinate - 1] = _adblObservation[iNumPredictorOrdinate - 2] - 0.5 *
			(_adblResponseValue[iNumPredictorOrdinate - 2] - _adblObservation[iNumPredictorOrdinate - 2]);
		return true;
	}

	private boolean inferResponseZScores()
	{
		int iNumSegment = _adblPredictorOrdinate.length - 1;
		_adblResponseZScoreLeft = new double[iNumSegment];
		_adblResponseZScoreRight = new double[iNumSegment];

		for (int i = 0; i < iNumSegment; ++i) {
			_adblResponseZScoreLeft[i] = _adblResponseValue[i] - _adblObservation[i];
			_adblResponseZScoreRight[i] = _adblResponseValue[i + 1] - _adblObservation[i];
		}

		return true;
	}

	private boolean generateUnivariate()
	{
		int iNumSegment = _adblPredictorOrdinate.length - 1;
		_aAU = new org.drip.function.definition.R1ToR1[iNumSegment];

		for (int i = 0; i < iNumSegment; ++i) {
			if ((_adblResponseZScoreLeft[i] > 0. && -0.5 * _adblResponseZScoreLeft[i] >=
				_adblResponseZScoreRight[i] && _adblResponseZScoreRight[i] >= -2. *
					_adblResponseZScoreLeft[i]) || (_adblResponseZScoreLeft[i] < 0. && -0.5 *
						_adblResponseZScoreLeft[i] <= _adblResponseZScoreRight[i] &&
							_adblResponseZScoreRight[i] <= -2. * _adblResponseZScoreLeft[i]))
				_aAU[i] = new Case1Univariate (_adblPredictorOrdinate[i], _adblPredictorOrdinate[i + 1],
					_adblResponseZScoreLeft[i], _adblResponseZScoreRight[i]);
			else if ((_adblResponseZScoreLeft[i] < 0. && _adblResponseZScoreRight[i] > -2. *
				_adblResponseZScoreLeft[i]) || (_adblResponseZScoreLeft[i] > 0. &&
					_adblResponseZScoreRight[i] < -2. * _adblResponseZScoreLeft[i]))
				_aAU[i] = new Case2Univariate (_adblPredictorOrdinate[i], _adblPredictorOrdinate[i + 1],
					_adblResponseZScoreLeft[i], _adblResponseZScoreRight[i]);
			else if ((_adblResponseZScoreLeft[i] > 0. && _adblResponseZScoreRight[i] > -0.5 *
				_adblResponseZScoreLeft[i]) || (_adblResponseZScoreLeft[i] < 0. &&
					_adblResponseZScoreRight[i] < -0.5 * _adblResponseZScoreLeft[i]))
				_aAU[i] = new Case3Univariate (_adblPredictorOrdinate[i], _adblPredictorOrdinate[i + 1],
					_adblResponseZScoreLeft[i], _adblResponseZScoreRight[i]);
			else if ((_adblResponseZScoreLeft[i] >= 0. && _adblResponseZScoreRight[i] >= 0.) ||
				(_adblResponseZScoreLeft[i] <= 0. && _adblResponseZScoreRight[i] <= 0.))
				_aAU[i] = new Case4Univariate (_adblPredictorOrdinate[i], _adblPredictorOrdinate[i + 1],
					_adblResponseZScoreLeft[i], _adblResponseZScoreRight[i]);
		}

		return true;
	}

	private boolean ameliorate (
		final double[] adblResponseLeftMin,
		final double[] adblResponseLeftMax,
		final double[] adblResponseRightMin,
		final double[] adblResponseRightMax)
	{
		int iNumObservation = _adblObservation.length;

		if (iNumObservation != adblResponseLeftMin.length || iNumObservation != adblResponseLeftMax.length ||
			iNumObservation != adblResponseRightMin.length || iNumObservation != adblResponseRightMax.length)
			return false;

		for (int i = 0; i < iNumObservation; ++i) {
			if (_adblResponseValue[i] < java.lang.Math.max (adblResponseLeftMin[i], adblResponseRightMin[i])
				|| _adblResponseValue[i] > java.lang.Math.min (adblResponseLeftMax[i],
					adblResponseRightMax[i])) {
				if (_adblResponseValue[i] < java.lang.Math.max (adblResponseLeftMin[i],
					adblResponseRightMin[i]))
					_adblResponseValue[i] = java.lang.Math.max (adblResponseLeftMin[i],
						adblResponseRightMin[i]);
				else if (_adblResponseValue[i] > java.lang.Math.min (adblResponseLeftMax[i],
					adblResponseRightMax[i]))
					_adblResponseValue[i] = java.lang.Math.min (adblResponseLeftMax[i],
						adblResponseRightMax[i]);
			} else {
				if (_adblResponseValue[i] < java.lang.Math.min (adblResponseLeftMax[i],
					adblResponseRightMax[i]))
					_adblResponseValue[i] = java.lang.Math.min (adblResponseLeftMax[i],
						adblResponseRightMax[i]);
				else if (_adblResponseValue[i] > java.lang.Math.max (adblResponseLeftMin[i],
					adblResponseRightMin[i]))
					_adblResponseValue[i] = java.lang.Math.max (adblResponseLeftMin[i],
						adblResponseRightMin[i]);
			}
		}

		if (java.lang.Math.abs (_adblResponseValue[0] - _adblObservation[0]) > 0.5 * java.lang.Math.abs
			(_adblResponseValue[1] - _adblObservation[0]))
			_adblResponseValue[0] = _adblObservation[1] - 0.5 * (_adblResponseValue[1] -
				_adblObservation[0]);

		if (java.lang.Math.abs (_adblResponseValue[iNumObservation] - _adblObservation[iNumObservation - 1])
			> 0.5 * java.lang.Math.abs (_adblResponseValue[iNumObservation - 1] -
				_adblObservation[iNumObservation - 1]))
			_adblResponseValue[iNumObservation] = _adblObservation[iNumObservation - 1] - 0.5 *
				(_adblObservation[iNumObservation - 1] - _adblResponseValue[iNumObservation - 1]);

		return inferResponseZScores() && generateUnivariate();
	}

	private int containingIndex (
		final double dblPredictorOrdinate,
		final boolean bIncludeLeft,
		final boolean bIncludeRight)
		throws java.lang.Exception
	{
		int iNumSegment = _aAU.length;

		for (int i = 0 ; i < iNumSegment; ++i) {
			boolean bLeftValid = bIncludeLeft ? _adblPredictorOrdinate[i] <= dblPredictorOrdinate :
				_adblPredictorOrdinate[i] < dblPredictorOrdinate;

			boolean bRightValid = bIncludeRight ? _adblPredictorOrdinate[i + 1] >= dblPredictorOrdinate :
				_adblPredictorOrdinate[i + 1] > dblPredictorOrdinate;

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

		return _aAU[iContainingIndex].evaluate (dblPredictorOrdinate) + _adblObservation[iContainingIndex];
	}

	/**
	 * Enforce the Positivity of the Inferred Response Values
	 * 
	 * @return TRUE - Positivity Enforcement is successful
	 */

	public boolean enforcePositivity()
	{
		try {
			_adblResponseValue[0] = org.drip.quant.common.NumberUtil.Bound (_adblResponseValue[0], 0., 2. *
				_adblObservation[0]);

			int iNumObservation = _adblObservation.length;

			for (int i = 1; i < iNumObservation; ++i)
				_adblResponseValue[i] = org.drip.quant.common.NumberUtil.Bound (_adblResponseValue[i], 0., 2.
					* java.lang.Math.min (_adblObservation[i - 1], _adblObservation[i]));

			_adblResponseValue[iNumObservation] = org.drip.quant.common.NumberUtil.Bound
				(_adblResponseValue[iNumObservation], 0., 2. * _adblObservation[iNumObservation - 1]);

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

		int iNumAmelioratedObservation = _adblObservation.length + 2;
		int iNumAmelioratedPredicatorOrdinate = _adblPredictorOrdinate.length + 2;
		double[] adblAmelioratedObservation = new double[iNumAmelioratedObservation];
		double[] adblAmelioratedPredictorOrdinate = new double[iNumAmelioratedPredicatorOrdinate];

		for (int i = 0; i < iNumAmelioratedPredicatorOrdinate; ++i) {
			if (0 == i)
				adblAmelioratedPredictorOrdinate[0] = -1. * _adblPredictorOrdinate[1];
			else if (iNumAmelioratedPredicatorOrdinate - 1 == i)
				adblAmelioratedPredictorOrdinate[i] = 2. * _adblPredictorOrdinate[i - 1] -
					_adblPredictorOrdinate[i - 2];
			else
				adblAmelioratedPredictorOrdinate[i] = _adblPredictorOrdinate[i - 1];
		}

		for (int i = 0; i < iNumAmelioratedObservation; ++i) {
			if (0 == i)
				adblAmelioratedObservation[0] = _adblObservation[0] - (_adblPredictorOrdinate[1] -
					_adblPredictorOrdinate[0]) * (_adblObservation[1] - _adblObservation[0]) /
						(_adblPredictorOrdinate[2] - _adblPredictorOrdinate[0]);
			else if (iNumAmelioratedPredicatorOrdinate - 1 == i)
				adblAmelioratedObservation[i] = _adblObservation[i - 1] - (_adblPredictorOrdinate[i - 1] -
					_adblPredictorOrdinate[i - 2]) * (_adblObservation[i - 1] - _adblObservation[i - 2]) /
						(_adblPredictorOrdinate[i - 1] - _adblPredictorOrdinate[i - 3]);
			else
				adblAmelioratedObservation[i] = _adblObservation[i - 1];
		}

		MonotoneConvexHaganWest mchwAmeliorated = Create (adblAmelioratedPredictorOrdinate,
			adblAmelioratedObservation, _bLinearNodeInference);

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
		return _adblPredictorOrdinate;
	}

	/**
	 * Retrieve the Array of Response Values
	 * 
	 * @return The Array of Response Values
	 */

	public double[] responseValues()
	{
		return _adblResponseValue;
	}
}
