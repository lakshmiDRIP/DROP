
package org.drip.spline.params;

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
 * SegmentStateCalibrationInputs implements basis per-segment Calibration Parameter Input Set. It exposes the
 * 	following functionality:
 *  - Retrieve the Array of the Calibration Predictor Ordinates.
 *  - Retrieve the Array of the Calibration Response Values.
 *  - Retrieve the Array of the Left/Right Edge Derivatives.
 *  - Retrieve the Segment Best Fit Response.
 *  - Retrieve the Array of Segment Basis Flexure Constraints.
 *
 * @author Lakshmi Krishnamurthy
 */

public class SegmentStateCalibrationInputs {
	private double[] _adblResponseValue = null;
	private double[] _adblLeftEdgeDeriv = null;
	private double[] _adblRightEdgeDeriv = null;
	private double[] _adblPredictorOrdinate = null;
	private org.drip.spline.params.SegmentBestFitResponse _sbfr = null;
	private org.drip.spline.params.SegmentBasisFlexureConstraint[] _aSBFC = null;

	/**
	 * SegmentStateCalibrationInputs Constructor
	 * 
	 * @param adblPredictorOrdinate Array of Predictor Ordinates
	 * @param adblResponseValue Array of Response Values
	 * @param adblLeftEdgeDeriv Array of the Left Edge Derivatives
	 * @param adblRightEdgeDeriv Array of the Right Edge  Derivatives
	 * @param aSBFC Array of the Segment Basis Flexure Constraints
	 * @param sbfr Segment Basis Fit Response
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public SegmentStateCalibrationInputs (
		final double[] adblPredictorOrdinate,
		final double[] adblResponseValue,
		final double[] adblLeftEdgeDeriv,
		final double[] adblRightEdgeDeriv,
		final org.drip.spline.params.SegmentBasisFlexureConstraint[] aSBFC,
		final org.drip.spline.params.SegmentBestFitResponse sbfr)
		throws java.lang.Exception
	{
		_sbfr = sbfr;
		int iNumSBFC = null == (_aSBFC = aSBFC) ? 0 : _aSBFC.length;
		int iNumLeftEdgeDeriv = null == (_adblLeftEdgeDeriv = adblLeftEdgeDeriv) ? 0 :
			_adblLeftEdgeDeriv.length;
		int iNumResponseValue = null == (_adblResponseValue = adblResponseValue) ? 0 :
			_adblResponseValue.length;
		int iNumRightEdgeDeriv = null == (_adblRightEdgeDeriv = adblRightEdgeDeriv) ? 0 :
			_adblRightEdgeDeriv.length;
		int iNumPredictorOrdinate = null == (_adblPredictorOrdinate = adblPredictorOrdinate) ? 0 :
			_adblPredictorOrdinate.length;

		if (null == _sbfr && null == _aSBFC && null == _adblPredictorOrdinate && null == _adblResponseValue
			&& null == _adblLeftEdgeDeriv && null == _adblRightEdgeDeriv)
			throw new java.lang.Exception ("SegmentStateCalibrationInputs ctr: Invalid Inputs");

		if (iNumPredictorOrdinate != iNumResponseValue || (null == _sbfr && 0 == iNumSBFC && 0 ==
			iNumPredictorOrdinate && 0 == iNumLeftEdgeDeriv && 0 == iNumRightEdgeDeriv))
			throw new java.lang.Exception ("SegmentStateCalibrationInputs ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Array of the Calibration Predictor Ordinates
	 * 
	 * @return The Array of the Calibration Predictor Ordinates
	 */

	public double[] predictorOrdinates()
	{
		return _adblPredictorOrdinate;
	}

	/**
	 * Retrieve the Array of the Calibration Response Values
	 * 
	 * @return The Array of the Calibration Response Values
	 */

	public double[] responseValues()
	{
		return _adblResponseValue;
	}

	/**
	 * Retrieve the Array of the Left Edge Derivatives
	 * 
	 * @return The Array of the Left Edge Derivatives
	 */

	public double[] leftEdgeDeriv()
	{
		return _adblLeftEdgeDeriv;
	}

	/**
	 * Retrieve the Array of the Right Edge Derivatives
	 * 
	 * @return The Array of the Right Edge Derivatives
	 */

	public double[] rightEdgeDeriv()
	{
		return _adblRightEdgeDeriv;
	}

	/**
	 * Retrieve the Segment Best Fit Response
	 * 
	 * @return The Segment Best Fit Response
	 */

	public org.drip.spline.params.SegmentBestFitResponse bestFitResponse()
	{
		return _sbfr;
	}

	/**
	 * Retrieve the Array of Segment Basis Flexure Constraints
	 * 
	 * @return The Array of Segment Basis Flexure Constraints
	 */

	public org.drip.spline.params.SegmentBasisFlexureConstraint[] flexureConstraint()
	{
		return _aSBFC;
	}
}
