
package org.drip.measure.bridge;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * BrokenDateInterpolatorSqrtT Interpolates using Two Stochastic Value Nodes with Linear Scheme. The Scheme
 *  is Linear in Square Root of Time.
 *
 * @author Lakshmi Krishnamurthy
 */

public class BrokenDateInterpolatorSqrtT implements org.drip.measure.bridge.BrokenDateInterpolator {
	private double _dblT1 = java.lang.Double.NaN;
	private double _dblT2 = java.lang.Double.NaN;
	private double _dblV1 = java.lang.Double.NaN;
	private double _dblV2 = java.lang.Double.NaN;

	/**
	 * BrokenDateInterpolatorSqrtT Constructor
	 * 
	 * @param dblT1 T1
	 * @param dblT2 T2
	 * @param dblV1 V1
	 * @param dblV2 V2
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public BrokenDateInterpolatorSqrtT (
		final double dblT1,
		final double dblT2,
		final double dblV1,
		final double dblV2)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblT1 = dblT1) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblT2 = dblT2) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblV1 = dblV1) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblV2 = dblV2)|| _dblT1 >= _dblT2)
			throw new java.lang.Exception ("BrokenDateInterpolatorSqrtT Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve T1
	 * 
	 * @return T1
	 */

	public double t1()
	{
		return _dblT1;
	}

	/**
	 * Retrieve T2
	 * 
	 * @return T2
	 */

	public double t2()
	{
		return _dblT2;
	}

	/**
	 * Retrieve V1
	 * 
	 * @return V1
	 */

	public double v1()
	{
		return _dblV1;
	}

	/**
	 * Retrieve V2
	 * 
	 * @return V2
	 */

	public double v2()
	{
		return _dblV2;
	}

	@Override public double interpolate (
		final double dblT)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblT) || dblT < _dblT1 || dblT > _dblT2)
			throw new java.lang.Exception ("BrokenDateInterpolatorSqrtT::interpolate => Invalid Inputs");

		return (java.lang.Math.sqrt (_dblT2 - dblT) * _dblV1 + java.lang.Math.sqrt (dblT - _dblT1) * _dblV2)
			/ java.lang.Math.sqrt (_dblT2 - _dblT1);
	}
}
