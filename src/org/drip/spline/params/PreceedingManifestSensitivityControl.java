
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
 * PreceedingManifestSensitivityControl provides the control parameters that determine the behavior of
 * 	non-local manifest sensitivity.
 *
 * @author Lakshmi Krishnamurthy
 */

public class PreceedingManifestSensitivityControl {
	private boolean _bImpactFade = false;
	private int _iCkDBasisCoeffDPreceedingManifest = 0;
	private org.drip.spline.segment.BasisEvaluator _be = null;

	/**
	 * PreceedingManifestSensitivityControl constructor
	 * 
	 * @param bImpactFade TRUE - Fade the Manifest Sensitivity Impact; FALSE - Retain it
	 * @param iCkDBasisCoeffDPreceedingManifest Ck of DBasisCoeffDPreceedingManifest
	 * @param be Basis Evaluator Instance
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public PreceedingManifestSensitivityControl (
		final boolean bImpactFade,
		final int iCkDBasisCoeffDPreceedingManifest,
		final org.drip.spline.segment.BasisEvaluator be)
		throws java.lang.Exception
	{
		if (0 > (_iCkDBasisCoeffDPreceedingManifest = iCkDBasisCoeffDPreceedingManifest))
			throw new java.lang.Exception ("PreceedingManifestSensitivityControl ctr: Invalid Inputs");

		_be = be;
		_bImpactFade = bImpactFade;
	}

	/**
	 * Retrieve the Ck of DBasisCoeffDPreceedingManifest
	 * 
	 * @return Ck of DBasisCoeffDPreceedingManifest
	 */

	public int Ck()
	{
		return _iCkDBasisCoeffDPreceedingManifest;
	}

	/**
	 * Retrieve the Basis Evaluator Instance
	 * 
	 * @return The Basis Evaluator Instance
	 */

	public org.drip.spline.segment.BasisEvaluator basisEvaluator()
	{
		return _be;
	}

	/**
	 * Retrieve the Preceeding Manifest Measure Impact Flag
	 * 
	 * @return The Preceeding Manifest Measure Impact Flag
	 */

	public boolean impactFade()
	{
		return _bImpactFade;
	}
}
