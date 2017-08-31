
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
 * SegmentCustomBuilderControl holds the parameters the guide the creation/behavior of the segment. It holds the
 *  segment elastic/inelastic parameters and the named basis function set.
 *
 * @author Lakshmi Krishnamurthy
 */

public class SegmentCustomBuilderControl {
	private java.lang.String _strBasisSpline = "";
	private org.drip.spline.basis.FunctionSetBuilderParams _fsbp = null;
	private org.drip.spline.params.ResponseScalingShapeControl _rssc = null;
	private org.drip.spline.params.SegmentInelasticDesignControl _sdic = null;
	private org.drip.spline.params.PreceedingManifestSensitivityControl _pmsc = null;

	/**
	 * SegmentCustomBuilderControl constructor
	 * 
	 * @param strBasisSpline Named Segment Basis Spline
	 * @param fsbp Segment Basis Set Construction Parameters
	 * @param sdic Segment Design Inelastic Parameters
	 * @param rssc Segment Shape Controller
	 * @param pmsc Preceeding Manifest Sensitivity Control Parameters
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public SegmentCustomBuilderControl (
		final java.lang.String strBasisSpline,
		final org.drip.spline.basis.FunctionSetBuilderParams fsbp,
		final org.drip.spline.params.SegmentInelasticDesignControl sdic,
		final org.drip.spline.params.ResponseScalingShapeControl rssc,
		final org.drip.spline.params.PreceedingManifestSensitivityControl pmsc)
		throws java.lang.Exception
	{
		if (null == (_strBasisSpline = strBasisSpline) || null == (_fsbp = fsbp) || null == (_sdic = sdic))
			throw new java.lang.Exception ("SegmentCustomBuilderControl ctr => Invalid Inputs");

		_pmsc = pmsc;
		_rssc = rssc;
	}

	/**
	 * Retrieve the Basis Spline Name
	 * 
	 * @return The Basis Spline Name
	 */

	public java.lang.String basisSpline()
	{
		return _strBasisSpline;
	}

	/**
	 * Retrieve the Basis Set Parameters
	 * 
	 * @return The Basis Set Parameters
	 */

	public org.drip.spline.basis.FunctionSetBuilderParams basisSetParams()
	{
		return _fsbp;
	}

	/**
	 * Retrieve the Segment Inelastic Parameters
	 * 
	 * @return The Segment Inelastic Parameters
	 */

	public org.drip.spline.params.SegmentInelasticDesignControl inelasticParams()
	{
		return _sdic;
	}

	/**
	 * Retrieve the Segment Shape Controller
	 * 
	 * @return The Segment Shape Controller
	 */

	public org.drip.spline.params.ResponseScalingShapeControl shapeController()
	{
		return _rssc;
	}

	/**
	 * Retrieve the Preceeding Manifest Sensitivity Control Parameters
	 * 
	 * @return The Preceeding Manifest Sensitivity Control Parameters
	 */

	public org.drip.spline.params.PreceedingManifestSensitivityControl preceedingManifestSensitivityControl()
	{
		return _pmsc;
	}
}
