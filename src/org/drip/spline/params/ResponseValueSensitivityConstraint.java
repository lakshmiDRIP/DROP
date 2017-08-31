
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
 * SegmentResponseValueConstraint holds the SegmentBasisFlexureConstraint instances for the Base Calibration
 * 	and one for each Manifest Measure Sensitivity.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ResponseValueSensitivityConstraint {
	private org.drip.spline.params.SegmentResponseValueConstraint _srvcBase = null;

	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.spline.params.SegmentResponseValueConstraint>
			_mapSRVCManifestMeasure = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.spline.params.SegmentResponseValueConstraint>();

	/**
	 * ResponseValueSensitivityConstraint constructor
	 * 
	 * @param srvcBase The Base Calibration Instance of SRVC
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public ResponseValueSensitivityConstraint (
		final org.drip.spline.params.SegmentResponseValueConstraint srvcBase)
		throws java.lang.Exception
	{
		if (null == (_srvcBase = srvcBase))
			throw new java.lang.Exception ("ResponseValueSensitivityConstraint ctr: Invalid Inputs");
	}

	/**
	 * Add the SRVC Instance corresponding to the specified Manifest Measure
	 * 
	 * @param strManifestMeasure The Manifest Measure
	 * @param srvc The SRVC Instance
	 * 
	 * @return TRUE - The SRVC Instance was successfully added
	 */

	public boolean addManifestMeasureSensitivity (
		final java.lang.String strManifestMeasure,
		final org.drip.spline.params.SegmentResponseValueConstraint srvc)
	{
		if (null == strManifestMeasure || strManifestMeasure.isEmpty() || null == srvc) return false;

		_mapSRVCManifestMeasure.put (strManifestMeasure, srvc);

		return true;
	}

	/**
	 * Retrieve the base SRVC Instance
	 * 
	 * @return The Base SRVC Instance
	 */

	public org.drip.spline.params.SegmentResponseValueConstraint base()
	{
		return _srvcBase;
	}

	/**
	 * Retrieve the SRVC Instance Specified by the Manifest Measure
	 * 
	 * @param strManifestMeasure The Manifest Measure
	 * 
	 * @return The SRVC Instance Specified by the Manifest Measure
	 */

	public org.drip.spline.params.SegmentResponseValueConstraint manifestMeasureSensitivity (
		final java.lang.String strManifestMeasure)
	{
		return null != strManifestMeasure && _mapSRVCManifestMeasure.containsKey (strManifestMeasure) ?
			_mapSRVCManifestMeasure.get (strManifestMeasure) : null;
	}

	/**
	 * Return the Set of Available Manifest Measures (if any)
	 * 
	 * @return The Set of Available Manifest Measures
	 */

	public java.util.Set<java.lang.String> manifestMeasures()
	{
		return _mapSRVCManifestMeasure.keySet();
	}
}
