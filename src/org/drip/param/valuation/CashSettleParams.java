
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
 * CashSettleParams is the place-holder for the cash settlement parameters for a given product. It contains
 *  the cash settle lag, the calendar, and the date adjustment mode.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CashSettleParams {
	private int _iLag = 3;
	private java.lang.String _strCalendar = "";
	private int _iAdjustMode = org.drip.analytics.daycount.Convention.DATE_ROLL_FOLLOWING;

	/**
	 * Construct the CashSettleParams object from the settle lag and the settle calendar objects
	 * 
	 * @param iLag Cash Settle Lag
	 * @param iAdjustMode Settle adjust Mode
	 * @param strCalendar Settlement Calendar
	 */

	public CashSettleParams (
		final int iLag,
		final java.lang.String strCalendar,
		final int iAdjustMode)
	{
		_iLag = iLag;
		_iAdjustMode = iAdjustMode;
		_strCalendar = strCalendar;
	}

	/**
	 * Retrieve the Settle Lag
	 * 
	 * @return The Settle Lag
	 */

	public int lag()
	{
		return _iLag;
	}

	/**
	 * Retrieve the Settle Calendar
	 * 
	 * @return The Settle Calendar
	 */

	public java.lang.String calendar()
	{
		return _strCalendar;
	}

	/**
	 * Retrieve the Adjustment Mode
	 * 
	 * @return The Adjustment Mode
	 */

	public int adjustMode()
	{
		return _iAdjustMode;
	}

	/**
	 * Construct and return the cash settle date from the valuation date
	 * 
	 * @param iValueDate Valuation Date
	 * 
	 * @return Cash settle date
	 */

	public int cashSettleDate (
		final int iValueDate)
	{
		return org.drip.analytics.daycount.Convention.Adjust (iValueDate + _iLag, _strCalendar,
			_iAdjustMode);
	}
}
