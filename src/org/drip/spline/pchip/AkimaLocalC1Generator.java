
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
 * AkimaLocalC1Generator generates the local control C1 Slope using the Akima Cubic Algorithm:
 * 
 * 	Akima (1970): A New Method of Interpolation and Smooth Curve Fitting based on Local Procedures,
 * 		Journal of the Association for the Computing Machinery 17 (4), 589-602.
 *
 * @author Lakshmi Krishnamurthy
 */

public class AkimaLocalC1Generator {
	private double[] _adblResponseValue = null;
	private double[] _adblPredictorOrdinate = null;
	private double[] _adblExtendedResponseValue = null;
	private double[] _adblExtendedPredictorOrdinate = null;

	/**
	 * Construct an Instance of AkimaLocalC1Generator from the Array of the supplied Predictor Ordinates
	 *  and the Response Values
	 *  
	 * @param adblPredictorOrdinate Array of the Predictor Ordinates
	 * @param adblResponseValue Array of the Response Values
	 * 
	 * @return Instance of AkimaLocalC1Generator
	 */

	public static final AkimaLocalC1Generator Create (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue)
	{
		AkimaLocalC1Generator alcr = null;

		try {
			alcr = new AkimaLocalC1Generator (adblPredictorOrdinate, adblResponseValue);
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return alcr.extendPredictorOrdinate() && alcr.extendResponseValue() ? alcr : null;
	}

	private AkimaLocalC1Generator (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue)
		throws java.lang.Exception
	{
		if (null == (_adblPredictorOrdinate = adblPredictorOrdinate) || null == (_adblResponseValue =
			adblResponseValue))
			throw new java.lang.Exception ("AkimaLocalC1Generator ctr: Invalid Inputs");

		int iNumPredictorOrdinate = _adblPredictorOrdinate.length;

		if (2 >= iNumPredictorOrdinate || iNumPredictorOrdinate != _adblResponseValue.length)
			throw new java.lang.Exception ("AkimaLocalC1Generator ctr: Invalid Inputs");
	}

	private boolean extendPredictorOrdinate()
	{
		int iNumPredictorOrdinate = _adblPredictorOrdinate.length;
		int iNumExtendedPredictorOrdinate = iNumPredictorOrdinate + 4;
		_adblExtendedPredictorOrdinate = new double[iNumExtendedPredictorOrdinate];

		for (int i = 0; i < iNumExtendedPredictorOrdinate; ++i) {
			if (2 <= i && iNumExtendedPredictorOrdinate - 3 >= i)
				_adblExtendedPredictorOrdinate[i] = _adblPredictorOrdinate[i - 2];
		}

		double dblSkippedLeftPredictorWidth = _adblPredictorOrdinate[2] - _adblPredictorOrdinate[0];
		_adblExtendedPredictorOrdinate[0] = _adblPredictorOrdinate[0] - dblSkippedLeftPredictorWidth;
		_adblExtendedPredictorOrdinate[1] = _adblPredictorOrdinate[1] - dblSkippedLeftPredictorWidth;
		double dblSkippedRightPredictorWidth = _adblPredictorOrdinate[iNumPredictorOrdinate - 1] -
			_adblPredictorOrdinate[iNumPredictorOrdinate - 3];
		_adblExtendedPredictorOrdinate[iNumExtendedPredictorOrdinate - 2] =
			_adblPredictorOrdinate[iNumPredictorOrdinate - 2] + dblSkippedRightPredictorWidth;
		_adblExtendedPredictorOrdinate[iNumExtendedPredictorOrdinate - 1] =
			_adblPredictorOrdinate[iNumPredictorOrdinate - 1] + dblSkippedRightPredictorWidth;
		return true;
	}

	private boolean setExtendedResponseValue (
		final int i,
		final boolean bRight)
	{
		if (bRight) {
			_adblExtendedResponseValue[i] = 2. * (_adblExtendedResponseValue[i - 1] -
				_adblExtendedResponseValue[i - 2]) / (_adblExtendedPredictorOrdinate[i - 1] -
					_adblExtendedPredictorOrdinate[i - 2]) - ((_adblExtendedResponseValue[i - 2] -
						_adblExtendedResponseValue[i - 3]) / (_adblExtendedPredictorOrdinate[i - 2] -
							_adblExtendedPredictorOrdinate[i - 3]));
			_adblExtendedResponseValue[i] = _adblExtendedResponseValue[i] * (_adblExtendedResponseValue[i] -
				_adblExtendedResponseValue[i - 1]) + _adblExtendedResponseValue[i - 1];
		} else {
			_adblExtendedResponseValue[i] = 2. * (_adblExtendedResponseValue[i + 2] -
				_adblExtendedResponseValue[i + 1]) / (_adblExtendedPredictorOrdinate[i + 2] -
					_adblExtendedPredictorOrdinate[i + 1]) - ((_adblExtendedResponseValue[i + 3] -
						_adblExtendedResponseValue[i + 2]) / (_adblExtendedPredictorOrdinate[i + 3] -
							_adblExtendedPredictorOrdinate[i + 2]));
			_adblExtendedResponseValue[i] = _adblExtendedResponseValue[i + 1] - _adblExtendedResponseValue[i]
				* (_adblExtendedResponseValue[i + 1] - _adblExtendedResponseValue[i]);
		}

		return true;
	}

	private boolean extendResponseValue()
	{
		int iNumResponseValue = _adblResponseValue.length;
		int iNumExtendedResponseValue = iNumResponseValue + 4;
		_adblExtendedResponseValue = new double[iNumExtendedResponseValue];

		for (int i = 0; i < iNumExtendedResponseValue; ++i) {
			if (2 <= i && iNumExtendedResponseValue - 3 >= i)
				_adblExtendedResponseValue[i] = _adblResponseValue[i - 2];
		}

		return setExtendedResponseValue (1, false) && setExtendedResponseValue (0, false) &&
			setExtendedResponseValue (iNumExtendedResponseValue - 2, true) && setExtendedResponseValue
				(iNumExtendedResponseValue - 1, true) ? true : false;
	}

	public double[] C1()
	{
		int iNumPredictorOrdinate = _adblPredictorOrdinate.length;
		double[] adblC1 = new double[iNumPredictorOrdinate];
		double[] adblExtendedSlope = new double[iNumPredictorOrdinate + 3];

		for (int i = 0; i < iNumPredictorOrdinate + 3; ++i)
			adblExtendedSlope[i] = (_adblExtendedResponseValue[i + 1] - _adblExtendedResponseValue[i]) /
				(_adblExtendedPredictorOrdinate[i + 1] - _adblExtendedPredictorOrdinate[i]);

		for (int i = 0; i < iNumPredictorOrdinate; ++i) {
			double dblSlope10 = java.lang.Math.abs (adblExtendedSlope[i + 1] - adblExtendedSlope[i]);

			double dblSlope32 = java.lang.Math.abs (adblExtendedSlope[i + 3] - adblExtendedSlope[i + 2]);

			if (0. == dblSlope10 && 0. == dblSlope32)
				adblC1[i] = 0.5 * (adblExtendedSlope[i + 1] + adblExtendedSlope[i + 2]);
			else
				adblC1[i] = (dblSlope32 * adblExtendedSlope[i + 1] + dblSlope10 * adblExtendedSlope[i + 2]) /
					(dblSlope10 + dblSlope32);
		}

		return adblC1;
	}
}
