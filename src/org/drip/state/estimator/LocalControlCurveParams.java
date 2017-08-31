	
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
 * LocalControlCurveParams enhances the SmoothingCurveStretchParams to produce locally customized curve
 *  smoothing.
 * 
 * Flags implemented by LocalControlCurveParams control the following:
 *  - The C1 generator scheme to be used
 *  - Whether to eliminate spurious extrema, 
 *  - Whether or not to apply monotone filtering
 *
 * @author Lakshmi Krishnamurthy
 */

public class LocalControlCurveParams extends org.drip.state.estimator.SmoothingCurveStretchParams {
	private boolean _bApplyMonotoneFilter = false;
	private boolean _bEliminateSpuriousExtrema = false;
	private java.lang.String _strC1GeneratorScheme = "";

	/**
	 * LocalControlCurveParams constructor
	 * 
	 * @param strC1GeneratorScheme C1 Generator Stretch
	 * @param strSmootheningQuantificationMetric Curve Smoothening Quantification Metric
	 * @param scbc Segment Builder Parameters
	 * @param iCalibrationDetail The Calibration Detail
	 * @param sbfr Curve Fitness Weighted Response
	 * @param sbfrSensitivity Curve Fitness Weighted Response Sensitivity
	 * @param bEliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param bApplyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LocalControlCurveParams (
		final java.lang.String strC1GeneratorScheme,
		final java.lang.String strSmootheningQuantificationMetric,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc,
		final int iCalibrationDetail,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final org.drip.spline.params.StretchBestFitResponse sbfrSensitivity,
		final boolean bEliminateSpuriousExtrema,
		final boolean bApplyMonotoneFilter)
		throws java.lang.Exception
	{
		super (strSmootheningQuantificationMetric, scbc, iCalibrationDetail, sbfr, sbfrSensitivity);

		if (null == (_strC1GeneratorScheme = strC1GeneratorScheme))
			throw new java.lang.Exception ("LocalControlCurveParams ctr: Invalid Inputs!");

		_bApplyMonotoneFilter = bApplyMonotoneFilter;
		_bEliminateSpuriousExtrema = bEliminateSpuriousExtrema;
	}

	/**
	 * Retrieve the Apply Monotone Filter Flag
	 * 
	 * @return The Apply Monotone Filter Flag
	 */

	public boolean applyMonotoneFilter()
	{
		return _bApplyMonotoneFilter;
	}

	/**
	 * Retrieve the Eliminate Spurious Extrema Flag
	 * 
	 * @return The Eliminate Spurious Extrema Flag
	 */

	public boolean eliminateSpuriousExtrema()
	{
		return _bEliminateSpuriousExtrema;
	}

	/**
	 * Retrieve the C1 Generator Scheme
	 * 
	 * @return The C1 Generator Scheme
	 */

	public java.lang.String C1GeneratorScheme()
	{
		return _strC1GeneratorScheme;
	}
}
