
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
		private double _responseZScoreRight = Double.NaN;
		private double _predictorOrdinateLeft = Double.NaN;
		private double _predictorOrdinateRight = Double.NaN;

		Case3Univariate (
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
			_eta = _responseZScoreLeft != _responseZScoreRight ? 3. * _responseZScoreRight / (
				_responseZScoreRight - _responseZScoreLeft
			) : 0.;
		}

		@Override public double evaluate (
			final double predictorOrdinate)
			throws Exception
		{
			if (!NumberUtil.IsValid (predictorOrdinate) || predictorOrdinate < _predictorOrdinateLeft ||
				predictorOrdinate > _predictorOrdinateRight) {
				throw new Exception ("Case3Univariate::evaluate => Invalid Inputs");
			}

			if (_responseZScoreLeft == _responseZScoreRight) {
				return _responseZScoreRight;
			}

			double x = (predictorOrdinate - _predictorOrdinateLeft) / (
				_predictorOrdinateRight - _predictorOrdinateLeft
			);
			return x < _eta ? _responseZScoreLeft + (_responseZScoreLeft - _responseZScoreRight) * (_eta - x)
				* (_eta - x) / _eta / _eta : _responseZScoreRight;
		}

		@Override public double integrate (
			final double begin,
			final double end)
			throws Exception
		{
			return R1ToR1Integrator.Boole (this, begin, end);
		}
	}

	class Case4Univariate
		extends R1ToR1
	{
		private double _a = Double.NaN;
		private double _eta = Double.NaN;
		private double _responseZScoreLeft = Double.NaN;
		private double _responseZScoreRight = Double.NaN;
		private double _predictorOrdinateLeft = Double.NaN;
		private double _predictorOrdinateRight = Double.NaN;

		Case4Univariate (
			final double dblPredictorOrdinateLeft,
			final double dblPredictorOrdinateRight,
			final double dblResponseZScoreLeft,
			final double dblResponseZScoreRight)
		{
			super (null);

			_responseZScoreLeft = dblResponseZScoreLeft;
			_responseZScoreRight = dblResponseZScoreRight;
			_predictorOrdinateLeft = dblPredictorOrdinateLeft;
			_predictorOrdinateRight = dblPredictorOrdinateRight;

			if (_responseZScoreLeft != _responseZScoreRight) {
				_eta = _responseZScoreRight / (_responseZScoreRight - _responseZScoreLeft);
				_a = -1. * _responseZScoreLeft * _responseZScoreRight / (
					_responseZScoreRight - _responseZScoreLeft
				);
			} else {
				_a = 0.;
				_eta = 0.;
			}
		}

		@Override public double evaluate (
			final double predictorOrdinate)
			throws Exception
		{
			if (!NumberUtil.IsValid (predictorOrdinate) || predictorOrdinate < _predictorOrdinateLeft ||
				predictorOrdinate > _predictorOrdinateRight) {
				throw new Exception ("Case4Univariate::evaluate => Invalid Inputs");
			}

			if (_responseZScoreLeft == _responseZScoreRight) {
				return _responseZScoreRight;
			}

			double x = (predictorOrdinate - _predictorOrdinateLeft) / (
				_predictorOrdinateRight - _predictorOrdinateLeft
			);
			return x < _eta ? _a + (_responseZScoreLeft - _a) * (_eta - x) * (_eta - x) / _eta / _eta :
				_a + (_responseZScoreRight - _a) * (x - _eta) * (x - _eta) / (1. - _eta) / (1. - _eta);
		}

		@Override public double integrate (
			final double begin,
			final double end)
			throws Exception
		{
			return R1ToR1Integrator.Boole (this, begin, end);
		}
	}

	/**
	 * Create an instance of <i>MonotoneConvexHaganWest</i>
	 * 
	 * @param predictorOrdinateArray Array of Predictor Ordinates
	 * @param observationArray Array of Observations
	 * @param linearNodeInference Apply Linear Node Inference from Observations
	 * 
	 * @return Instance of <i>MonotoneConvexHaganWest</i>
	 */

	public static final MonotoneConvexHaganWest Create (
		final double[] predictorOrdinateArray,
		final double[] observationArray,
		final boolean linearNodeInference)
	{
		MonotoneConvexHaganWest monotoneConvexHaganWest = null;

		try {
			monotoneConvexHaganWest = new MonotoneConvexHaganWest (
				predictorOrdinateArray,
				observationArray,
				linearNodeInference
			);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		return monotoneConvexHaganWest.inferResponseValues() &&
			monotoneConvexHaganWest.inferResponseZScores() && monotoneConvexHaganWest.generateUnivariate() ?
			monotoneConvexHaganWest : null;
	}

	private MonotoneConvexHaganWest (
		final double[] predictorOrdinateArray,
		final double[] observationArray,
		final boolean linearNodeInference)
		throws Exception
	{
		super (null);

		if (null == (_observationArray = observationArray) ||
			null == (_predictorOrdinateArray = predictorOrdinateArray)) {
			throw new Exception ("MonotoneConvexHaganWest ctr: Invalid Inputs!");
		}

		_linearNodeInference = linearNodeInference;
		int observationCount = _observationArray.length;

		if (1 >= observationCount || observationCount + 1 != _predictorOrdinateArray.length) {
			throw new Exception ("MonotoneConvexHaganWest ctr: Invalid Inputs!");
		}
	}

	private boolean inferResponseValues()
	{
		int predictorOrdinateArray = _predictorOrdinateArray.length;
		_responseValueArray = new double[predictorOrdinateArray];

		for (int predictorOrdinateIndex = 1; predictorOrdinateIndex < predictorOrdinateArray - 1;
			++predictorOrdinateIndex) {
			if (_linearNodeInference) {
				_responseValueArray[predictorOrdinateIndex] = (
					_predictorOrdinateArray[predictorOrdinateIndex] -
					_predictorOrdinateArray[predictorOrdinateIndex - 1]
				) / (
					_predictorOrdinateArray[predictorOrdinateIndex + 1] -
					_predictorOrdinateArray[predictorOrdinateIndex - 1]
				) * _observationArray[predictorOrdinateIndex] + (
					_predictorOrdinateArray[predictorOrdinateIndex + 1] -
					_predictorOrdinateArray[predictorOrdinateIndex]
				) / (
					_predictorOrdinateArray[predictorOrdinateIndex + 1] -
					_predictorOrdinateArray[predictorOrdinateIndex - 1]
				) * _observationArray[predictorOrdinateIndex - 1];
			} else {
				_responseValueArray[predictorOrdinateIndex] = 0.;

				if (0. < _observationArray[predictorOrdinateIndex - 1] * _observationArray[predictorOrdinateIndex])
				{
					_responseValueArray[predictorOrdinateIndex] = (
						_predictorOrdinateArray[predictorOrdinateIndex] -
						_predictorOrdinateArray[predictorOrdinateIndex - 1] + 2. * (
							_predictorOrdinateArray[predictorOrdinateIndex + 1] -
							_predictorOrdinateArray[predictorOrdinateIndex]
						)
					) / (
						3. * (
							_predictorOrdinateArray[predictorOrdinateIndex + 1] -
							_predictorOrdinateArray[predictorOrdinateIndex]
						)
					) / _observationArray[predictorOrdinateIndex - 1];
					_responseValueArray[predictorOrdinateIndex] += (
						_predictorOrdinateArray[predictorOrdinateIndex + 1] -
						_predictorOrdinateArray[predictorOrdinateIndex] + 2. * (
							_predictorOrdinateArray[predictorOrdinateIndex] -
							_predictorOrdinateArray[predictorOrdinateIndex - 1]
						)
					) / (
						3. * (
							_predictorOrdinateArray[predictorOrdinateIndex + 1] -
							_predictorOrdinateArray[predictorOrdinateIndex]
						)
					) / _observationArray[predictorOrdinateIndex];
					_responseValueArray[predictorOrdinateIndex] = 1. /
						_responseValueArray[predictorOrdinateIndex];
				}
			}
		}

		_responseValueArray[0] = _observationArray[0] - 0.5 * (
			_responseValueArray[1] - _observationArray[0]
		);
		_responseValueArray[predictorOrdinateArray - 1] =
			_observationArray[predictorOrdinateArray - 2] - 0.5 * (
				_responseValueArray[predictorOrdinateArray - 2] -
				_observationArray[predictorOrdinateArray - 2]
			);
		return true;
	}

	private boolean inferResponseZScores()
	{
		int segmentCount = _predictorOrdinateArray.length - 1;
		_responseZScoreRightArray = new double[segmentCount];
		_responseZScoreLeftArray = new double[segmentCount];

		for (int segmentIndex = 0; segmentIndex < segmentCount; ++segmentIndex) {
			_responseZScoreLeftArray[segmentIndex] = _responseValueArray[segmentIndex] -
				_observationArray[segmentIndex];
			_responseZScoreRightArray[segmentIndex] = _responseValueArray[segmentIndex + 1] -
				_observationArray[segmentIndex];
		}

		return true;
	}

	private boolean generateUnivariate()
	{
		int segmentCount = _predictorOrdinateArray.length - 1;
		_univariateFunctionArray = new R1ToR1[segmentCount];

		for (int segmentIndex = 0; segmentIndex < segmentCount; ++segmentIndex) {
			if (
				(
					0. < _responseZScoreLeftArray[segmentIndex] &&
					-0.5 * _responseZScoreLeftArray[segmentIndex] >= _responseZScoreRightArray[segmentIndex] &&
					_responseZScoreRightArray[segmentIndex] >= -2. * _responseZScoreLeftArray[segmentIndex]
				) || (
					0. > _responseZScoreLeftArray[segmentIndex] &&
					-0.5 * _responseZScoreLeftArray[segmentIndex] <= _responseZScoreRightArray[segmentIndex] &&
					_responseZScoreRightArray[segmentIndex] <= -2. * _responseZScoreLeftArray[segmentIndex]
				)
			) {
				_univariateFunctionArray[segmentIndex] = new Case1Univariate (
					_predictorOrdinateArray[segmentIndex],
					_predictorOrdinateArray[segmentIndex + 1],
					_responseZScoreLeftArray[segmentIndex],
					_responseZScoreRightArray[segmentIndex]
				);
			} else if (
				(
					0. > _responseZScoreLeftArray[segmentIndex] &&
					_responseZScoreRightArray[segmentIndex] > -2. * _responseZScoreLeftArray[segmentIndex]
				) || (
					0. < _responseZScoreLeftArray[segmentIndex] &&
					_responseZScoreRightArray[segmentIndex] < -2. * _responseZScoreLeftArray[segmentIndex]
				)
			) {
				_univariateFunctionArray[segmentIndex] = new Case2Univariate (
					_predictorOrdinateArray[segmentIndex],
					_predictorOrdinateArray[segmentIndex + 1],
					_responseZScoreLeftArray[segmentIndex],
					_responseZScoreRightArray[segmentIndex]
				);
			} else if (
				(
					0. < _responseZScoreLeftArray[segmentIndex] &&
					_responseZScoreRightArray[segmentIndex] > -0.5 * _responseZScoreLeftArray[segmentIndex]
				) || (
					0. > _responseZScoreLeftArray[segmentIndex] &&
					_responseZScoreRightArray[segmentIndex] < -0.5 * _responseZScoreLeftArray[segmentIndex]
				)
			) {
				_univariateFunctionArray[segmentIndex] = new Case3Univariate (
					_predictorOrdinateArray[segmentIndex],
					_predictorOrdinateArray[segmentIndex + 1],
					_responseZScoreLeftArray[segmentIndex],
					_responseZScoreRightArray[segmentIndex]
				);
			} else if (
				(
					0. <= _responseZScoreLeftArray[segmentIndex] &&
					0. <= _responseZScoreRightArray[segmentIndex]
				) || (
					0. >= _responseZScoreLeftArray[segmentIndex] &&
					0. >= _responseZScoreRightArray[segmentIndex]
				)
			) {
				_univariateFunctionArray[segmentIndex] = new Case4Univariate (
					_predictorOrdinateArray[segmentIndex],
					_predictorOrdinateArray[segmentIndex + 1],
					_responseZScoreLeftArray[segmentIndex],
					_responseZScoreRightArray[segmentIndex]
				);
			}
		}

		return true;
	}

	private boolean ameliorate (
		final double[] minimumLeftResponseArray,
		final double[] maximumLeftResponseArray,
		final double[] minimumRightResponseArray,
		final double[] maximumRightResponseArray)
	{
		int observationCount = _observationArray.length;

		if (observationCount != minimumLeftResponseArray.length ||
			observationCount != maximumLeftResponseArray.length ||
			observationCount != minimumRightResponseArray.length ||
			observationCount != maximumRightResponseArray.length) {
			return false;
		}

		for (int observationIndex = 0; observationIndex < observationCount; ++observationIndex) {
			if (_responseValueArray[observationIndex] < Math.max (
					minimumLeftResponseArray[observationIndex],
					minimumRightResponseArray[observationIndex]
				) || _responseValueArray[observationIndex] > Math.min (
					maximumLeftResponseArray[observationIndex],
					maximumRightResponseArray[observationIndex]
				)
			) {
				if (_responseValueArray[observationIndex] < Math.max (
						minimumLeftResponseArray[observationIndex],
						minimumRightResponseArray[observationIndex]
					)
				) {
					_responseValueArray[observationIndex] = Math.max (
						minimumLeftResponseArray[observationIndex],
						minimumRightResponseArray[observationIndex]
					);
				} else if (_responseValueArray[observationIndex] > Math.min (
						maximumLeftResponseArray[observationIndex],
						maximumRightResponseArray[observationIndex]
					)
				) {
					_responseValueArray[observationIndex] = Math.min (
						maximumLeftResponseArray[observationIndex],
						maximumRightResponseArray[observationIndex]
					);
				}
			} else {
				if (_responseValueArray[observationIndex] < Math.min (
						maximumLeftResponseArray[observationIndex],
						maximumRightResponseArray[observationIndex]
					)
				) {
					_responseValueArray[observationIndex] = Math.min (
						maximumLeftResponseArray[observationIndex],
						maximumRightResponseArray[observationIndex]
					);
				} else if (_responseValueArray[observationIndex] > Math.max (
						minimumLeftResponseArray[observationIndex],
						minimumRightResponseArray[observationIndex]
					)
				) {
					_responseValueArray[observationIndex] = Math.max (
						minimumLeftResponseArray[observationIndex],
						minimumRightResponseArray[observationIndex]
					);
				}
			}
		}

		if (Math.abs (_responseValueArray[0] - _observationArray[0]) >
			0.5 * Math.abs (_responseValueArray[1] - _observationArray[0])) {
			_responseValueArray[0] = _observationArray[1] - 0.5 * (
				_responseValueArray[1] - _observationArray[0]
			);
		}

		if (Math.abs (_responseValueArray[observationCount] - _observationArray[observationCount - 1]) >
			0.5 * Math.abs (
				_responseValueArray[observationCount - 1] - _observationArray[observationCount - 1]
			)
		) {
			_responseValueArray[observationCount] = _observationArray[observationCount - 1] - 0.5 * (
				_observationArray[observationCount - 1] - _responseValueArray[observationCount - 1]
			);
		}

		return inferResponseZScores() && generateUnivariate();
	}

	private int containingIndex (
		final double predictorOrdinate,
		final boolean includeLeft,
		final boolean includeRight)
		throws Exception
	{
		for (int segmentIndex = 0 ; segmentIndex < _univariateFunctionArray.length; ++segmentIndex) {
			boolean leftValid = includeLeft ? _predictorOrdinateArray[segmentIndex] <= predictorOrdinate :
				_predictorOrdinateArray[segmentIndex] < predictorOrdinate;

			boolean rightValid = includeRight ?
				_predictorOrdinateArray[segmentIndex + 1] >= predictorOrdinate :
				_predictorOrdinateArray[segmentIndex + 1] > predictorOrdinate;

			if (leftValid && rightValid) {
				return segmentIndex;
			}
		}

		throw new Exception ("MonotoneConvexHaganWest::containingIndex => Cannot locate Containing Index");
	}

	@Override public double evaluate (
		final double predictorOrdinate)
		throws Exception
	{
		int containingIndex = containingIndex (predictorOrdinate, true, true);

		return _univariateFunctionArray[containingIndex].evaluate (predictorOrdinate) +
			_observationArray[containingIndex];
	}

	/**
	 * Enforce the Positivity of the Inferred Response Values
	 * 
	 * @return TRUE - Positivity Enforcement is successful
	 */

	public boolean enforcePositivity()
	{
		try {
			_responseValueArray[0] = NumberUtil.Bound (
				_responseValueArray[0],
				0.,
				2. * _observationArray[0]
			);

			for (int observationIndex = 1; observationIndex < _observationArray.length; ++observationIndex) {
				_responseValueArray[observationIndex] = NumberUtil.Bound (
					_responseValueArray[observationIndex],
					0.,
					2. * Math.min (
						_observationArray[observationIndex - 1],
						_observationArray[observationIndex]
					)
				);
			}

			_responseValueArray[_observationArray.length] = NumberUtil.Bound (
				_responseValueArray[_observationArray.length],
				0.,
				2. * _observationArray[_observationArray.length - 1]
			);

			return inferResponseZScores() && generateUnivariate();
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Create an Ameliorated Instance of the Current Instance
	 * 
	 * @param minimumLeftResponseArray Response Left Floor
	 * @param maximumLeftResponseArray Response Left Ceiling
	 * @param minimumRightResponseArray Response Right Floor
	 * @param maximumRightResponseArray Response Right Ceiling
	 * @param enforcePositivity TRUE - Enforce Positivity
	 * 
	 * @return The Ameliorated Version of the Current Instance
	 */

	public MonotoneConvexHaganWest generateAmelioratedInstance (
		final double[] minimumLeftResponseArray,
		final double[] maximumLeftResponseArray,
		final double[] minimumRightResponseArray,
		final double[] maximumRightResponseArray,
		final boolean enforcePositivity)
	{
		if (null == minimumLeftResponseArray || null == maximumLeftResponseArray ||
			null == minimumRightResponseArray || null == maximumRightResponseArray) {
			return null;
		}

		int amelioratedObservationCount = _observationArray.length + 2;
		int amelioratedPredicatorOrdinateCount = _predictorOrdinateArray.length + 2;
		double[] amelioratedObservationArray = new double[amelioratedObservationCount];
		double[] amelioratedPredictorOrdinateArray = new double[amelioratedPredicatorOrdinateCount];

		for (int amelioratedPredicatorOrdinateIndex = 0;
			amelioratedPredicatorOrdinateIndex < amelioratedPredicatorOrdinateCount;
			++amelioratedPredicatorOrdinateIndex) {
			if (0 == amelioratedPredicatorOrdinateIndex) {
				amelioratedPredictorOrdinateArray[0] = -1. * _predictorOrdinateArray[1];
			} else if (amelioratedPredicatorOrdinateCount - 1 == amelioratedPredicatorOrdinateIndex) {
				amelioratedPredictorOrdinateArray[amelioratedPredicatorOrdinateIndex] =
					2. * _predictorOrdinateArray[amelioratedPredicatorOrdinateIndex - 1] -
						_predictorOrdinateArray[amelioratedPredicatorOrdinateIndex - 2];
			} else {
				amelioratedPredictorOrdinateArray[amelioratedPredicatorOrdinateIndex] =
					_predictorOrdinateArray[amelioratedPredicatorOrdinateIndex - 1];
			}
		}

		for (int amelioratedObservationIndex = 0; amelioratedObservationIndex < amelioratedObservationCount;
			++amelioratedObservationIndex) {
			if (0 == amelioratedObservationIndex) {
				amelioratedObservationArray[0] = _observationArray[0] - (
					_predictorOrdinateArray[1] - _predictorOrdinateArray[0]
				) * (_observationArray[1] - _observationArray[0]) / (
					_predictorOrdinateArray[2] - _predictorOrdinateArray[0]
				);
			} else if (amelioratedPredicatorOrdinateCount - 1 == amelioratedObservationIndex) {
				amelioratedObservationArray[amelioratedObservationIndex] =
					_observationArray[amelioratedObservationIndex - 1] - (
						_predictorOrdinateArray[amelioratedObservationIndex - 1] -
						_predictorOrdinateArray[amelioratedObservationIndex - 2]
					) * (
						_observationArray[amelioratedObservationIndex - 1] -
						_observationArray[amelioratedObservationIndex - 2]
					) / (
						_predictorOrdinateArray[amelioratedObservationIndex - 1] -
						_predictorOrdinateArray[amelioratedObservationIndex - 3]
					);
			} else {
				amelioratedObservationArray[amelioratedObservationIndex] =
					_observationArray[amelioratedObservationIndex - 1];
			}
		}

		MonotoneConvexHaganWest amelioratedMonotoneConvexHaganWest = Create (
			amelioratedPredictorOrdinateArray,
			amelioratedObservationArray,
			_linearNodeInference
		);

		if (null == amelioratedMonotoneConvexHaganWest ||
			amelioratedMonotoneConvexHaganWest.ameliorate (
				minimumLeftResponseArray,
				maximumLeftResponseArray,
				minimumRightResponseArray,
				maximumRightResponseArray
			)
		) {
			return null;
		}

		if (enforcePositivity) {
			if (!amelioratedMonotoneConvexHaganWest.enforcePositivity()) {
				return null;
			}
		}

		return amelioratedMonotoneConvexHaganWest;
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
