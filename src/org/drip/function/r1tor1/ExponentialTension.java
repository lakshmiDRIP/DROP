
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
 * ExponentialTension provides the evaluation of the Exponential Tension Function and its derivatives for a
 *  specified variate.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ExponentialTension extends org.drip.function.definition.R1ToR1 {
	private boolean _bIsBaseNatural = false;
	private double _dblBase = java.lang.Double.NaN;
	private double _dblTension = java.lang.Double.NaN;

	/**
	 * ExponentialTension constructor
	 * 
	 * @param dblBase Base of the ExponentialTension Function
	 * @param dblTension Tension of the ExponentialTension Function
	 * 
	 * @throws java.lang.Exception Thrown if the input is invalid
	 */

	public ExponentialTension (
		final double dblBase,
		final double dblTension)
		throws java.lang.Exception
	{
		super (null);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblBase = dblBase) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblTension = dblTension))
			throw new java.lang.Exception ("ExponentialTension ctr: Invalid Inputs");

		_bIsBaseNatural = _dblBase == java.lang.Math.E;
	}

	@Override public double evaluate (
		final double dblVariate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate))
			throw new java.lang.Exception ("ExponentialTension::evaluate => Invalid Inputs");

		return _bIsBaseNatural ? java.lang.Math.exp (_dblTension * dblVariate) : java.lang.Math.pow
			(_dblBase, _dblTension * dblVariate);
	}

	@Override public double derivative (
		final double dblVariate,
		final int iOrder)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate) || 0 > iOrder)
			throw new java.lang.Exception ("ExponentialTension::derivative => Invalid Inputs");

		double dblDerivFactor = 1.;

		for (int i = 0; i < iOrder; ++i)
			dblDerivFactor *= _dblTension;

		return _bIsBaseNatural ? dblDerivFactor * java.lang.Math.exp (_dblTension * dblVariate) :
			dblDerivFactor * java.lang.Math.exp (_dblTension * dblVariate);
	}

	@Override public double integrate (
		final double dblBegin,
		final double dblEnd)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblBegin) || !org.drip.quant.common.NumberUtil.IsValid
			(dblEnd))
			throw new java.lang.Exception ("ExponentialTension::integrate => Invalid Inputs");

		return _bIsBaseNatural ? (java.lang.Math.exp (_dblTension * dblEnd) - java.lang.Math.exp (_dblTension
			* dblBegin)) / _dblTension : (java.lang.Math.pow (_dblBase, _dblTension * dblEnd) -
				java.lang.Math.pow (_dblBase, _dblTension * dblBegin)) / (_dblTension * java.lang.Math.log
					(_dblBase));
	}

	/**
	 * Is the base natural?
	 * 
	 * @return TRUE - Base is off of natural logarithm
	 */

	public boolean isBaseNatural()
	{
		return _bIsBaseNatural;
	}

	/**
	 * Retrieve the Base
	 * 
	 * @return The Base
	 */

	public double getBase()
	{
		return _dblBase;
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
		ExponentialTension e = new ExponentialTension (java.lang.Math.E, 2.);

		System.out.println ("E[0.0] = " + e.evaluate (0.0));

		System.out.println ("E[0.5] = " + e.evaluate (0.5));

		System.out.println ("E[1.0] = " + e.evaluate (1.0));

		System.out.println ("EDeriv[0.0] = " + e.derivative (0.0, 2));

		System.out.println ("EDeriv[0.5] = " + e.derivative (0.5, 2));

		System.out.println ("EDeriv[1.0] = " + e.derivative (1.0, 2));
	}
}
