
package org.drip.dynamics.lmm;

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
 * ContinuouslyCompoundedForwardProcess implements the Continuously Compounded Forward Rate Process defined
 *  in the LIBOR Market Model. The References are:
 * 
 *  1) Goldys, B., M. Musiela, and D. Sondermann (1994): Log-normality of Rates and Term Structure Models,
 *  	The University of New South Wales.
 * 
 *  2) Musiela, M. (1994): Nominal Annual Rates and Log-normal Volatility Structure, The University of New
 *   	South Wales.
 * 
 * 	3) Brace, A., D. Gatarek, and M. Musiela (1997): The Market Model of Interest Rate Dynamics, Mathematical
 * 		Finance 7 (2), 127-155.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ContinuouslyCompoundedForwardProcess {
	private int _iSpotDate = java.lang.Integer.MIN_VALUE;
	private org.drip.measure.stochastic.R1R1ToR1 _funcR1R1ToR1 = null;

	/**
	 * ContinuouslyCompoundedForwardProcess Constructor
	 * 
	 * @param iSpotDate The Spot Date
	 * @param funcR1R1ToR1 The Stochastic Forward Rate Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ContinuouslyCompoundedForwardProcess (
		final int iSpotDate,
		final org.drip.measure.stochastic.R1R1ToR1 funcR1R1ToR1)
		throws java.lang.Exception
	{
		if (null == (_funcR1R1ToR1 = funcR1R1ToR1))
			throw new java.lang.Exception ("ContinuouslyCompoundedForwardProcess ctr: Invalid Inputs");

		_iSpotDate = iSpotDate;
	}

	/**
	 * Retrieve the Spot Date
	 * 
	 * @return The Spot Date
	 */

	public int spotDate()
	{
		return _iSpotDate;
	}

	/**
	 * Retrieve the Stochastic Forward Rate Function
	 * 
	 * @return The Stochastic Forward Rate Function
	 */

	public org.drip.measure.stochastic.R1R1ToR1 stochasticForwardRateFunction()
	{
		return _funcR1R1ToR1;
	}

	/**
	 * Retrieve a Realized Zero-Coupon Bond Price
	 * 
	 * @param iMaturityDate The Maturity Date
	 * 
	 * @return The Realized Zero-Coupon Bond Price
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double realizedZeroCouponPrice (
		final int iMaturityDate)
		throws java.lang.Exception
	{
		if (iMaturityDate <= _iSpotDate)
			throw new java.lang.Exception
				("ContinuouslyCompoundedForwardProcess::realizedZeroCouponPrice => Invalid Maturity Date");

		return java.lang.Math.exp (-1. * _funcR1R1ToR1.integralRealization (0., iMaturityDate - _iSpotDate));
	}

	/**
	 * Compute the Realized/Expected Instantaneous Forward Rate Integral to the Target Date
	 * 
	 * @param iTargetDate The Target Date
	 * @param bRealized TRUE - Compute the Realized (TRUE) / Expected (FALSE) Instantaneous Forward Rate
	 *  Integral
	 * 
	 * @return The Realized/Expected Instantaneous Forward Rate Integral
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double instantaneousForwardRateIntegral (
		final int iTargetDate,
		final boolean bRealized)
		throws java.lang.Exception
	{
		if (iTargetDate <= _iSpotDate)
			throw new java.lang.Exception
				("ContinuouslyCompoundedForwardProcess::instantaneousForwardRateIntegral => Invalid Target Date");

		return bRealized ? java.lang.Math.exp (-1. * _funcR1R1ToR1.integralRealization (0., iTargetDate -
			_iSpotDate)) : java.lang.Math.exp (-1. * _funcR1R1ToR1.integralExpectation (0., iTargetDate -
				_iSpotDate));
	}

	/**
	 * Retrieve a Realized/Expected Value of the Discount to the Target Date
	 * 
	 * @param iTargetDate The Target Date
	 * @param bRealized TRUE - Compute the Realized (TRUE) / Expected (FALSE) Instantaneous Forward Rate
	 *  Integral
	 * 
	 * @return The Realized/Expected Value of the Discount to the Target Date
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double discountFunctionValue (
		final int iTargetDate,
		final boolean bRealized)
		throws java.lang.Exception
	{
		if (iTargetDate <= _iSpotDate)
			throw new java.lang.Exception
				("ContinuouslyCompoundedForwardProcess::discountFunctionValue => Invalid Target Date");

		return bRealized ? java.lang.Math.exp (-1. * _funcR1R1ToR1.integralRealization (0., iTargetDate -
			_iSpotDate)) : java.lang.Math.exp (-1. * _funcR1R1ToR1.integralExpectation (0., iTargetDate -
				_iSpotDate));
	}

	/**
	 * Retrieve a Realized/Expected Value of the LIBOR Rate at the Target Date
	 * 
	 * @param iTargetDate The Target Date
	 * @param strTenor The LIBOR Tenor
	 * @param bRealized TRUE - Compute the Realized (TRUE) / Expected (FALSE) LIBOR Rate
	 * 
	 * @return The Realized/Expected Value of the LIBOR Rate at the Target Date
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double liborRate (
		final int iTargetDate,
		final java.lang.String strTenor,
		final boolean bRealized)
		throws java.lang.Exception
	{
		if (iTargetDate <= _iSpotDate)
			throw new java.lang.Exception
				("ContinuouslyCompoundedForwardProcess::liborRate => Invalid Inputs");

		return (discountFunctionValue (new org.drip.analytics.date.JulianDate (iTargetDate).addTenor
			(strTenor).julian(), bRealized) / discountFunctionValue (iTargetDate, bRealized) - 1.) /
				org.drip.analytics.support.Helper.TenorToYearFraction (strTenor);
	}
}
