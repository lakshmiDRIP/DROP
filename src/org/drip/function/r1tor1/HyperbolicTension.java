
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
 * HyperbolicTension provides the evaluation of the Hyperbolic Tension Function and its derivatives for a
 *  specified variate.
 *
 * @author Lakshmi Krishnamurthy
 */

public class HyperbolicTension extends org.drip.function.definition.R1ToR1 {

	/**
	 * Hyperbolic Tension Function Type - sinh
	 */

	public static final int SINH = 1;

	/**
	 * Hyperbolic Tension Function Type - cosh
	 */

	public static final int COSH = 2;

	private int _iType = -1;
	private double _dblTension = java.lang.Double.NaN;

	/**
	 * HyperbolicTension constructor
	 * 
	 * @param iType Type of the HyperbolicTension Function - SINH/COSH/TANH
	 * @param dblTension Tension of the HyperbolicTension Function
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public HyperbolicTension (
		final int iType,
		final double dblTension)
		throws java.lang.Exception
	{
		super (null);

		if ((SINH != (_iType = iType) && COSH != _iType) || !org.drip.quant.common.NumberUtil.IsValid
			(_dblTension = dblTension))
			throw new java.lang.Exception ("HyperbolicTension ctr: Invalid Inputs");
	}

	@Override public double evaluate (
		final double dblVariate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate))
			throw new java.lang.Exception ("HyperbolicTension::evaluate => Invalid Inputs");

		return SINH == _iType ? java.lang.Math.sinh (_dblTension * dblVariate) : java.lang.Math.cosh
			(_dblTension * dblVariate);
	}

	@Override public double derivative (
		final double dblVariate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate) || 0 > iOrder)
			throw new java.lang.Exception ("HyperbolicTension::derivative => Invalid Inputs");

		double dblDerivFactor = 1.;

		for (int i = 0; i < iOrder; ++i)
			dblDerivFactor *= _dblTension;

		return (SINH == _iType) ? dblDerivFactor * (1 == iOrder % 2 ? java.lang.Math.cosh (_dblTension *
			dblVariate) : java.lang.Math.sinh (_dblTension * dblVariate)) : dblDerivFactor * (1 == iOrder % 2
				? java.lang.Math.sinh (_dblTension * dblVariate) : java.lang.Math.cosh (_dblTension *
					dblVariate));
	}

	@Override public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBegin) || !org.drip.quant.common.NumberUtil.IsValid
			(dblEnd))
			throw new java.lang.Exception ("HyperbolicTension::integrate => Invalid Inputs");

		return SINH == _iType ? (java.lang.Math.cosh (_dblTension * dblEnd) - java.lang.Math.cosh
			(_dblTension * dblBegin)) / _dblTension : (java.lang.Math.sinh (_dblTension * dblEnd) -
				java.lang.Math.sinh (_dblTension * dblBegin)) / _dblTension;
	}

	/**
	 * Retrieve the hyperbolic function type
	 * 
	 * @return Hyperbolic function type
	 */

	public int getType()
	{
		return _iType;
	}

	/**
	 * Retrieve the Tension Parameter
	 * 
	 * @return Tension Parameter
	 */

	public double getTension()
	{
		return _dblTension;
	}

	public static final void main (
		final java.lang.String[] astrArgs)
		throws java.lang.Exception
	{
		HyperbolicTension e = new HyperbolicTension (SINH, 2.);

		System.out.println ("E[0.0] = " + e.evaluate (0.0));

		System.out.println ("E[0.5] = " + e.evaluate (0.5));

		System.out.println ("E[1.0] = " + e.evaluate (1.0));

		System.out.println ("EDeriv[0.0] = " + e.derivative (0.0, 2));

		System.out.println ("EDeriv[0.5] = " + e.derivative (0.5, 2));

		System.out.println ("EDeriv[1.0] = " + e.derivative (1.0, 2));
	}
}
