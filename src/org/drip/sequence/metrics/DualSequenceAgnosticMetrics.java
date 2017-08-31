
package org.drip.sequence.metrics;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * DualSequenceAgnosticMetrics contains the Joint Distribution Metrics and Agnostic Bounds related to the
 *  specified Sequence Pair.
 *
 * @author Lakshmi Krishnamurthy
 */

public class DualSequenceAgnosticMetrics {
	private org.drip.sequence.metrics.SingleSequenceAgnosticMetrics _ssam1 = null;
	private org.drip.sequence.metrics.SingleSequenceAgnosticMetrics _ssam2 = null;

	/**
	 * DualSequenceAgnosticMetrics Constructor
	 * 
	 * @param ssam1 First Sequence Metrics
	 * @param ssam2 Second Sequence Metrics
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public DualSequenceAgnosticMetrics (
		final org.drip.sequence.metrics.SingleSequenceAgnosticMetrics ssam1,
		final org.drip.sequence.metrics.SingleSequenceAgnosticMetrics ssam2)
		throws java.lang.Exception
	{
		if (null == (_ssam1 = ssam1) || null == (_ssam2 = ssam2) || _ssam1.sequence().length !=
			_ssam2.sequence().length)
			throw new java.lang.Exception ("DualSequenceAgnosticMetrics ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Array of the Component Single Sequences
	 * 
	 * @return The Array of the Component Single Sequences
	 */

	public org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] components()
	{
		return new org.drip.sequence.metrics.SingleSequenceAgnosticMetrics[] {_ssam1, _ssam2};
	}

	/**
	 * Retrieve the Cauchy-Schwarz Joint Expectation Bound
	 * 
	 * @return The Cauchy-Schwarz Joint Expectation Bound
	 * 
	 * @throws java.lang.Exception Thrown if the Cauchy-Schwarz Joint Expectation Bound cannot be computed
	 */

	public double cauchySchwarzAbsoluteBound()
		throws java.lang.Exception
	{
		return java.lang.Math.sqrt (_ssam1.empiricalRawMoment (2, true) * _ssam2.empiricalRawMoment (2,
			true));
	}
}
