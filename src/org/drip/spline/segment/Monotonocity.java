
package org.drip.spline.segment;

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
 * This class contains the monotonicity details related to the given segment. Indicates whether the segment
 * 	is monotonic, and if not, whether it contains a maximum, a minimum, or an inflection.
 *
 * @author Lakshmi Krishnamurthy
 */

public class Monotonocity {

	/**
	 * MONOTONIC
	 */

	public static final int MONOTONIC = 2;

	/**
	 * NON-MONOTONIC
	 */

	public static final int NON_MONOTONIC = 4;

	/**
	 * NON MONOTONE - MINIMA
	 */

	public static final int MINIMA = 5;

	/**
	 * NON MONOTONE - MAXIMA
	 */

	public static final int MAXIMA = 6;

	/**
	 * NON MONOTONE - INFLECTION
	 */

	public static final int INFLECTION = 7;

	private int _iMonotoneType = -1;

	/**
	 * Monotonocity constructor
	 * 
	 * @param iMonotoneType One of the valid monotone types
	 * 
	 * @throws java.lang.Exception Thrown if the input monotone type is invalid
	 */

	public Monotonocity (
		final int iMonotoneType)
		throws java.lang.Exception
	{
		if (org.drip.spline.segment.Monotonocity.MONOTONIC != (_iMonotoneType = iMonotoneType) &&
			org.drip.spline.segment.Monotonocity.NON_MONOTONIC != _iMonotoneType &&
				org.drip.spline.segment.Monotonocity.MINIMA != _iMonotoneType &&
					org.drip.spline.segment.Monotonocity.MAXIMA != _iMonotoneType &&
						org.drip.spline.segment.Monotonocity.INFLECTION != _iMonotoneType)
			throw new java.lang.Exception ("Monotonocity ctr: Unknown monotone type " + _iMonotoneType);
	}

	/**
	 * Retrieve the Monotone Type
	 * 
	 * @return The Monotone Type
	 */

	public int type()
	{
		return _iMonotoneType;
	}

	@Override public java.lang.String toString()
	{
		if (org.drip.spline.segment.Monotonocity.NON_MONOTONIC == _iMonotoneType) return "NON_MONOTONIC";

		if (org.drip.spline.segment.Monotonocity.MONOTONIC == _iMonotoneType) return "MONOTONIC    ";

		if (org.drip.spline.segment.Monotonocity.MINIMA == _iMonotoneType) return "MINIMA       ";

		if (org.drip.spline.segment.Monotonocity.MAXIMA == _iMonotoneType) return "MAXIMA       ";

		return "INFLECTION   ";
	}
}
