
package org.drip.param.valuation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
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
 * WorkoutInfo is the place-holder for the work-out parameters. It contains the date, the factor, the type,
 *  and the yield of the work-out.
 *
 * @author Lakshmi Krishnamurthy
 */

public class WorkoutInfo {

	/**
	 * Work out type Call
	 */

	public static final int WO_TYPE_CALL = 1;

	/**
	 * Work out type Put
	 */

	public static final int WO_TYPE_PUT = 2;

	/**
	 * Work out type Maturity
	 */

	public static final int WO_TYPE_MATURITY = 3;

	private int _iWOType = WO_TYPE_MATURITY;
	private int _iDate = java.lang.Integer.MIN_VALUE;
	private double _dblYield = java.lang.Double.NaN;
	private double _dblExerciseFactor = java.lang.Double.NaN;

	/**
	 * Constructor: Construct the class from the work-out date, yield, exercise factor, and type
	 * 
	 * @param iDate Work-out Date
	 * @param dblYield Work-out Yield
	 * @param dblExerciseFactor Work-out Factor
	 * @param iWOType Work out Type
	 * 
	 * @throws java.lang.Exception Thrown if input is invalid
	 */

	public WorkoutInfo (
		final int iDate,
		final double dblYield,
		final double dblExerciseFactor,
		final int iWOType)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblYield = dblYield) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblExerciseFactor = dblExerciseFactor))
			throw new java.lang.Exception ("WorkoutInfo ctr: One of wkout dat/yld/ex factor came out NaN!");

		_iDate = iDate;
		_iWOType = iWOType;
	}

	/**
	 * Retrieve the Work-out Date
	 * 
	 * @return The Work-out Date
	 */

	public int date()
	{
		return _iDate;
	}

	/**
	 * Retrieve the Work-out Yield
	 * 
	 * @return The Work-out Yield
	 */

	public double yield()
	{
		return _dblYield;
	}

	/**
	 * Retrieve the Work-out Factor
	 * 
	 * @return The Work-out Factor
	 */

	public double factor()
	{
		return _dblExerciseFactor;
	}

	/**
	 * Retrieve the Work-out Type
	 * 
	 * @return The Work-out Type
	 */

	public int type()
	{
		return _iWOType;
	}
}
