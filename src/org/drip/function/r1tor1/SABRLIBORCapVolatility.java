
package org.drip.function.r1tor1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
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
 * SABRLIBORCapVolatility implements the Deterministic, Non-local Cap Volatility Scheme detailed in:
 * 
 * 	- Rebonato, R., K. McKay, and R. White (2009): The SABR/LIBOR Market Model: Pricing, Calibration, and
 * 		Hedging for Complex Interest-Rate Derivatives, John Wiley and Sons.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SABRLIBORCapVolatility extends org.drip.function.definition.R1ToR1 {
	private double _dblA = java.lang.Double.NaN;
	private double _dblB = java.lang.Double.NaN;
	private double _dblC = java.lang.Double.NaN;
	private double _dblD = java.lang.Double.NaN;
	private double _dblEpoch = java.lang.Double.NaN;

	/**
	 * SABRLIBORCapVolatility Constructor
	 * 
	 * @param dblEpoch Epoch
	 * @param dblA A
	 * @param dblB B
	 * @param dblC C
	 * @param dblD D
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public SABRLIBORCapVolatility (
		final double dblEpoch,
		final double dblA,
		final double dblB,
		final double dblC,
		final double dblD)
		throws java.lang.Exception
	{
		super (null);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblEpoch = dblEpoch) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblA = dblA) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblB = dblB) ||
						!org.drip.quant.common.NumberUtil.IsValid (_dblC = dblC) ||
							!org.drip.quant.common.NumberUtil.IsValid (_dblD = dblD))
			throw new java.lang.Exception ("SABRLIBORCapVolatility ctr: Invalid Inputs");
	}

	@Override public double evaluate (
		final double dblVariate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate))
			throw new java.lang.Exception ("SABRLIBORCapVolatility::evaluate => Invalid Inputs");

		double dblDateGap = dblVariate - _dblEpoch;

		return (_dblB * dblDateGap + _dblA) * java.lang.Math.exp (-1. * _dblC * dblDateGap) + _dblD;
	}

	/**
	 * Return "A"
	 * 
	 * @return "A"
	 */

	public double A()
	{
		return _dblA;
	}

	/**
	 * Return "B"
	 * 
	 * @return "B"
	 */

	public double B()
	{
		return _dblB;
	}

	/**
	 * Return "C"
	 * 
	 * @return "C"
	 */

	public double C()
	{
		return _dblC;
	}

	/**
	 * Return "D"
	 * 
	 * @return "D"
	 */

	public double D()
	{
		return _dblD;
	}

	/**
	 * Return the Epoch
	 * 
	 * @return The Epoch
	 */

	public double epoch()
	{
		return _dblEpoch;
	}
}
