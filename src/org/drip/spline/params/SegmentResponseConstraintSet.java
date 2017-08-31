
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
 * SegmentResponseConstraintSet holds the set of SegmentResponseValueConstraint (Base + One/more
 *  Sensitivities) for the given Segment. It exposes functions to add/retrieve the base RVC as well as
 *  additional RVC sensitivities.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SegmentResponseConstraintSet {
	private org.drip.spline.params.SegmentResponseValueConstraint _srvcBase = null;
	private org.drip.spline.params.SegmentResponseValueConstraint _srvcSensitivity = null;

	/**
	 * Empty SegmentResponseConstraintSet Constructor
	 */

	public SegmentResponseConstraintSet()
	{
	}

	/**
	 * Add the Base Segment Response Value Constraint
	 * 
	 * @param srvcBase The Base Segment Response Value Constraint
	 * 
	 * @return TRUE - The Base Segment Response Value Constraint Successfully Set
	 */

	public boolean addBase (
		final org.drip.spline.params.SegmentResponseValueConstraint srvcBase)
	{
		if (null == srvcBase) return false;

		_srvcBase = srvcBase;
		return true;
	}

	/**
	 * Add the Base Segment Response Value Constraint Sensitivity
	 * 
	 * @param srvcSensitivity The Base Segment Response Value Constraint Sensitivity
	 * 
	 * @return TRUE - The Base Segment Response Value Constraint Sensitivity Successfully Set
	 */

	public boolean addSensitivity (
		final org.drip.spline.params.SegmentResponseValueConstraint srvcSensitivity)
	{
		if (null == srvcSensitivity) return false;

		_srvcSensitivity = srvcSensitivity;
		return true;
	}

	/**
	 * Retrieve the Base Segment Response Value Constraint
	 * 
	 * @return The Base Segment Response Value Constraint
	 */

	public org.drip.spline.params.SegmentResponseValueConstraint getBase()
	{
		return _srvcBase;
	}

	/**
	 * Retrieve the Base Segment Response Value Constraint Sensitivity
	 * 
	 * @return The Base Segment Response Value Constraint Sensitivity
	 */

	public org.drip.spline.params.SegmentResponseValueConstraint getSensitivity()
	{
		return _srvcSensitivity;
	}
}
