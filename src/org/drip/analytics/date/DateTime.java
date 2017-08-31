
package org.drip.analytics.date;

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
 * This class provides the representation of the instantiation-time date and time objects. It provides the
 *  following functionality:
 *  - Instantiation-time and Explicit Date/Time Construction
 *  - Retrieval of Date/Time Fields
 *  - Serialization/De-serialization to and from Byte Arrays
 * 
 * @author Lakshmi Krishnamurthy
 */

public class DateTime {
	private long _lTime = 0L;
	private double _dblDate = java.lang.Double.NaN;

	/**
	 * Default constructor initializes the time and date to the current time and current date.
	 * 
	 * @throws java.lang.Exception Thrown if the DateTime Instance cnnot be created
	 */

	public DateTime()
		throws java.lang.Exception
	{
		_lTime = System.nanoTime();

		java.util.Date dtNow = new java.util.Date();

		_dblDate = org.drip.analytics.date.DateUtil.ToJulian (org.drip.analytics.date.DateUtil.Year (dtNow),
			org.drip.analytics.date.DateUtil.Month (dtNow), org.drip.analytics.date.DateUtil.Day (dtNow));
	}

	/**
	 * Constructs DateTime from separate date and time inputs 
	 * 
	 * @param dblDate Date
	 * @param lTime Time
	 * 
	 * @throws java.lang.Exception Thrown on Invalid Inputs
	 */

	public DateTime (
		final double dblDate,
		final long lTime)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblDate))
			throw new java.lang.Exception ("DateTime ctr: Invalid Inputs!");

		_lTime = lTime;
		_dblDate = dblDate;
	}

	/**
	 * Retrieve the Date
	 * 
	 * @return date
	 */

	public double date()
	{
		return _dblDate;
	}

	/**
	 * Retrieve the time
	 * 
	 * @return time
	 */

	public long time()
	{
		return _lTime;
	}
}
