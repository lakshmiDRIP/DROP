
package org.drip.function.r1tor1;

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
 * Polynomial provides the evaluation of the n-th order Polynomial and its derivatives for a specified
 * 	variate.
 *
 * @author Lakshmi Krishnamurthy
 */

public class Polynomial extends org.drip.function.definition.R1ToR1 {
	private int _iDegree = -1;

	/**
	 * Polynomial constructor
	 * 
	 * @param iDegree Degree of the Polynomial
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public Polynomial (
		final int iDegree)
		throws java.lang.Exception
	{
		super (null);

		if (0 > (_iDegree = iDegree)) throw new java.lang.Exception ("Polynomial ctr: Invalid Inputs");
	}

	@Override public double evaluate (
		final double dblVariate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate))
			throw new java.lang.Exception ("Polynomial::evaluate => Invalid Inputs");

		return java.lang.Math.pow (dblVariate, _iDegree);
	}

	@Override public double derivative (
		final double dblVariate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate) || 0 > iOrder)
			throw new java.lang.Exception ("Polynomial::derivative => Invalid Inputs");

		return iOrder > _iDegree ? 0. : java.lang.Math.pow (dblVariate, _iDegree - iOrder) *
			org.drip.quant.common.NumberUtil.NPK (_iDegree, _iDegree - iOrder);
	}

	@Override public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBegin) || !org.drip.quant.common.NumberUtil.IsValid
			(dblEnd))
			throw new java.lang.Exception ("Polynomial::integrate => Invalid Inputs");

		return (java.lang.Math.pow (dblEnd, _iDegree + 1) - java.lang.Math.pow (dblBegin, _iDegree + 1)) /
			(_iDegree + 1);
	}

	/**
	 * Retrieve the degree of the polynomial
	 * 
	 * @return Degree of the polynomial
	 */

	public double getDegree()
	{
		 return _iDegree;
	}

	public static final void main (
		final java.lang.String[] astrArgs)
		throws java.lang.Exception
	{
		Polynomial poly = new Polynomial (4);

		System.out.println ("Poly[0.0] = " + poly.evaluate (0.0));

		System.out.println ("Poly[0.5] = " + poly.evaluate (0.5));

		System.out.println ("Poly[1.0] = " + poly.evaluate (1.0));

		System.out.println ("Deriv[0.0] = " + poly.derivative (0.0, 3));

		System.out.println ("Deriv[0.5] = " + poly.derivative (0.5, 3));

		System.out.println ("Deriv[1.0] = " + poly.derivative (1.0, 3));
	}
}
