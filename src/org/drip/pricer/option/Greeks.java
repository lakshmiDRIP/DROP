
package org.drip.pricer.option;

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
 * Greeks contains the Sensitivities/Pricing Measures common across both Call and Put Option Pricing Runs.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Greeks {
	private double _dblDF = java.lang.Double.NaN;
	private double _dblRho = java.lang.Double.NaN;
	private double _dblVega = java.lang.Double.NaN;
	private double _dblVeta = java.lang.Double.NaN;
	private double _dblCharm = java.lang.Double.NaN;
	private double _dblColor = java.lang.Double.NaN;
	private double _dblDelta = java.lang.Double.NaN;
	private double _dblGamma = java.lang.Double.NaN;
	private double _dblPrice = java.lang.Double.NaN;
	private double _dblProb1 = java.lang.Double.NaN;
	private double _dblProb2 = java.lang.Double.NaN;
	private double _dblSpeed = java.lang.Double.NaN;
	private double _dblTheta = java.lang.Double.NaN;
	private double _dblVanna = java.lang.Double.NaN;
	private double _dblVomma = java.lang.Double.NaN;
	private double _dblUltima = java.lang.Double.NaN;
	private double _dblExpectedPayoff = java.lang.Double.NaN;
	private double _dblExpectedATMPayoff = java.lang.Double.NaN;
	private double _dblEffectiveVolatility = java.lang.Double.NaN;

	/**
	 * The Greeks Constructor
	 * 
	 * @param dblDF The Payoff Discount Factor
	 * @param dblEffectiveVolatility Effective Volatility
	 * @param dblExpectedPayoff Expected Forward Payoff
	 * @param dblExpectedATMPayoff Expected ATM Forward Payoff
	 * @param dblPrice Price
	 * @param dblProb1 Probability Term #1
	 * @param dblProb2 Probability Term #2
	 * @param dblDelta Delta
	 * @param dblVega Vega
	 * @param dblTheta Theta
	 * @param dblRho Rho
	 * @param dblGamma Gamma
	 * @param dblVanna Vanna
	 * @param dblVomma Vomma
	 * @param dblCharm Charm
	 * @param dblVeta Veta
	 * @param dblColor Color
	 * @param dblSpeed Speed
	 * @param dblUltima Ultima
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Greeks (
		final double dblDF,
		final double dblEffectiveVolatility,
		final double dblExpectedPayoff,
		final double dblExpectedATMPayoff,
		final double dblPrice,
		final double dblProb1,
		final double dblProb2,
		final double dblDelta,
		final double dblVega,
		final double dblTheta,
		final double dblRho,
		final double dblGamma,
		final double dblVanna,
		final double dblVomma,
		final double dblCharm,
		final double dblVeta,
		final double dblColor,
		final double dblSpeed,
		final double dblUltima)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblDF = dblDF) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblEffectiveVolatility = dblEffectiveVolatility))
			throw new java.lang.Exception ("Greeks ctr: Invalid Inputs");

		_dblExpectedPayoff = dblExpectedPayoff;
		_dblExpectedATMPayoff = dblExpectedATMPayoff;
		_dblPrice = dblPrice;
		_dblProb1 = dblProb1;
		_dblProb2 = dblProb2;
		_dblDelta = dblDelta;
		_dblVega = dblVega;
		_dblTheta = dblTheta;
		_dblRho = dblRho;
		_dblGamma = dblGamma;
		_dblVanna = dblVanna;
		_dblVomma = dblVomma;
		_dblCharm = dblCharm;
		_dblVeta = dblVeta;
		_dblColor = dblColor;
		_dblSpeed = dblSpeed;
		_dblUltima = dblUltima;
	}

	/**
	 * The Option Terminal Discount Factor
	 * 
	 * @return The Option Terminal Discount Factor
	 */

	public double df()
	{
		return _dblDF;
	}

	/**
	 * The "Effective" Volatility
	 * 
	 * @return The "Effective" Volatility
	 */

	public double effectiveVolatility()
	{
		return _dblEffectiveVolatility;
	}

	/**
	 * The Expected Payoff
	 * 
	 * @return The Expected Payoff
	 */

	public double expectedPayoff()
	{
		return _dblExpectedPayoff;
	}

	/**
	 * The Expected ATM Payoff
	 * 
	 * @return The Expected ATM Payoff
	 */

	public double expectedATMPayoff()
	{
		return _dblExpectedATMPayoff;
	}

	/**
	 * The Option Price
	 * 
	 * @return The Option Price
	 */

	public double price()
	{
		return _dblPrice;
	}

	/**
	 * The Prob 1 Term
	 * 
	 * @return The Prob 1 Term
	 */

	public double prob1()
	{
		return _dblProb1;
	}

	/**
	 * The Prob 2 Term
	 * 
	 * @return The Prob 2 Term
	 */

	public double prob2()
	{
		return _dblProb2;
	}

	/**
	 * The Option Delta
	 * 
	 * @return The Option Delta
	 */

	public double delta()
	{
		return _dblDelta;
	}

	/**
	 * The Option Vega
	 * 
	 * @return The Option Vega
	 */

	public double vega()
	{
		return _dblVega;
	}

	/**
	 * The Option Theta
	 * 
	 * @return The Option Theta
	 */

	public double theta()
	{
		return _dblTheta;
	}

	/**
	 * The Option Rho
	 * 
	 * @return The Option Rho
	 */

	public double rho()
	{
		return _dblRho;
	}

	/**
	 * The Option Gamma
	 * 
	 * @return The Option Gamma
	 */

	public double gamma()
	{
		return _dblGamma;
	}

	/**
	 * The Option Vanna
	 * 
	 * @return The Option Vanna
	 */

	public double vanna()
	{
		return _dblVanna;
	}

	/**
	 * The Option Vomma
	 * 
	 * @return The Option Vomma
	 */

	public double vomma()
	{
		return _dblVomma;
	}

	/**
	 * The Option Charm
	 * 
	 * @return The Option Charm
	 */

	public double charm()
	{
		return _dblCharm;
	}

	/**
	 * The Option Veta
	 * 
	 * @return The Option Veta
	 */

	public double veta()
	{
		return _dblVeta;
	}

	/**
	 * The Option Color
	 * 
	 * @return The Option Color
	 */

	public double color()
	{
		return _dblColor;
	}

	/**
	 * The Option Speed
	 * 
	 * @return The Option Speed
	 */

	public double speed()
	{
		return _dblSpeed;
	}

	/**
	 * The Option Ultima
	 * 
	 * @return The Option Ultima
	 */

	public double ultima()
	{
		return _dblUltima;
	}
}
