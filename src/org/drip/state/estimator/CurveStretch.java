
package org.drip.state.estimator;

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
 * CurveStretch expands the regular Multi-Segment Stretch to aid the calibration of Boot-strapped
 *  Instruments.
 *  
 * In particular, CurveStretch implements the following functions that are used at different stages of
 * 	curve construction sequence:
 * 	- Mark the Range of the "built" Segments
 * 	- Clear the built range mark to signal the start of a fresh calibration run
 * 	- Indicate if the specified Predictor Ordinate is inside the "Built" Range
 * 	- Retrieve the MergeSubStretchManager
 *
 * @author Lakshmi Krishnamurthy
 */

public class CurveStretch extends org.drip.spline.stretch.CalibratableMultiSegmentSequence {
	private double _dblBuiltPredictorOrdinateRight = java.lang.Double.NaN;
	private org.drip.state.representation.MergeSubStretchManager _msm = null;

	/**
	 * CurveStretch constructor - Construct a sequence of Basis Spline Segments
	 * 
	 * @param strName Name of the Stretch
	 * @param aCS Array of Segments
	 * @param aSCBC Array of Segment Builder Parameters
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public CurveStretch (
		final java.lang.String strName,
		final org.drip.spline.segment.LatentStateResponseModel[] aCS,
		final org.drip.spline.params.SegmentCustomBuilderControl[] aSCBC)
		throws java.lang.Exception
	{
		super (strName, aCS, aSCBC);

		_dblBuiltPredictorOrdinateRight = getLeftPredictorOrdinateEdge();
	}

	/**
	 * Mark the Range of the "built" Segments, and set the set of Merge Latent States
	 * 
	 * @param iSegment The Current Segment Range Built
	 * @param setLSL Set of the merging Latent State Labels
	 * 
	 * @return TRUE - Range successfully marked as "built"
	 */

	public boolean markSegmentBuilt (
		final int iSegment,
		final java.util.Set<org.drip.state.identifier.LatentStateLabel> setLSL)
	{
		org.drip.spline.segment.LatentStateResponseModel[] aCS = segments();

		if (iSegment >= aCS.length) return false;

		_dblBuiltPredictorOrdinateRight = aCS[iSegment].right();

		if (null == setLSL || 0 == setLSL.size()) return true;

		if (null == _msm) _msm = new org.drip.state.representation.MergeSubStretchManager();

		for (org.drip.state.identifier.LatentStateLabel lsl : setLSL) {
			try {
				if (!_msm.addMergeStretch (new org.drip.state.representation.LatentStateMergeSubStretch
					(aCS[iSegment].left(), aCS[iSegment].right(), lsl)))
					return false;
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	/**
	 * Clear the built range mark to signal the start of a fresh calibration run
	 * 
	 * @return TRUE - Built Range successfully cleared
	 */

	public boolean clearBuiltRange()
	{
		_dblBuiltPredictorOrdinateRight = getLeftPredictorOrdinateEdge();

		_msm = null;
		return true;
	}

	/**
	 * Indicate if the specified Predictor Ordinate is inside the "Built" Range
	 * 
	 * @param dblPredictorOrdinate The Predictor Ordinate
	 * 
	 * @return TRUE - The specified Predictor Ordinate is inside the "Built" Range
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public boolean inBuiltRange (
		final double dblPredictorOrdinate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblPredictorOrdinate))
			throw new java.lang.Exception ("CurveStretch.inBuiltRange => Invalid Inputs");

		return dblPredictorOrdinate >= getLeftPredictorOrdinateEdge() && dblPredictorOrdinate <=
			_dblBuiltPredictorOrdinateRight;
	}

	@Override public org.drip.state.representation.MergeSubStretchManager msm()
	{
		return _msm;
	}
}
