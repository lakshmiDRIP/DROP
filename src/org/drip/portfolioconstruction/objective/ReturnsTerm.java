
package org.drip.portfolioconstruction.objective;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * ReturnsTerm holds the Details of the Portfolio Returns Based Objective Terms. Returns can be Absolute or
 *  in relation to a Benchmark.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class ReturnsTerm extends org.drip.portfolioconstruction.optimizer.ObjectiveTerm {
	private double[] _adblAlpha = null;
	private double[] _adblBenchmarkConstrictedHoldings = null;
	private org.drip.portfolioconstruction.composite.Benchmark _benchmark = null;
	private org.drip.portfolioconstruction.composite.BlockAttribute _baAlpha = null;

	protected ReturnsTerm (
		final java.lang.String strName,
		final java.lang.String strID,
		final java.lang.String strDescription,
		final org.drip.portfolioconstruction.composite.Holdings holdingsInitial,
		final org.drip.portfolioconstruction.composite.BlockAttribute baAlpha,
		final org.drip.portfolioconstruction.composite.Benchmark benchmark)
		throws java.lang.Exception
	{
		super (
			strName,
			strID,
			strDescription,
			"RETURNS",
			holdingsInitial
		);

		if (null == (_baAlpha = baAlpha))
			throw new java.lang.Exception ("ReturnsTerm Constructor => Invalid Inputs");

		if (null != (_benchmark = benchmark) && null == (_adblBenchmarkConstrictedHoldings =
			_benchmark.holdings().constrict (holdingsInitial)))
			throw new java.lang.Exception ("ReturnsTerm Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Alpha Block Attributes
	 *  
	 * @return The Alpha Block Attributes
	 */

	public org.drip.portfolioconstruction.composite.BlockAttribute alphaAttribute()
	{
		return _baAlpha;
	}

	/**
	 * Retrieve the Array of Alphas
	 *  
	 * @return The Array of Alphas
	 */

	public double[] alpha()
	{
		return _adblAlpha;
	}

	/**
	 * Retrieve the Benchmark
	 * 
	 * @return The Benchmark
	 */

	public org.drip.portfolioconstruction.composite.Benchmark benchmark()
	{
		return _benchmark;
	}

	/**
	 * Retrieve the Benchmark Constricted Holdings
	 * 
	 * @return The Benchmark Constricted Holdings
	 */

	public double[] benchmarkConstrictedHoldings()
	{
		return _adblBenchmarkConstrictedHoldings;
	}
}
