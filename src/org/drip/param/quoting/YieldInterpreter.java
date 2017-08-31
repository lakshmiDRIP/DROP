
package org.drip.param.quoting;

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
 * YieldInterpreter holds the fields needed to interpret a Yield Quote. It contains the quote day count,
 *  quote frequency, quote EOM Adjustment, quote Act/Act parameters, and quote Calendar.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class YieldInterpreter extends org.drip.param.quoting.MeasureInterpreter {

	/*
	 * Quote Day Count
	 */

	private java.lang.String _strDC = "";

	/*
	 * Quote Frequency
	 */

	private int _iFreq = 0;

	/*
	 * Quote Apply EOM Adjustment?
	 */

	private boolean _bApplyEOMAdj = false;

	/*
	 * Quote Act Act DC Params
	 */

	private org.drip.analytics.daycount.ActActDCParams _aap = null;

	/*
	 * Quote Calendar
	 */

	private java.lang.String _strCalendar = "";

	/**
	 * Construct YieldInterpreter from the Day Count and the Frequency parameters
	 * 
	 * @param strDC Quoting Day Count
	 * @param iFreq Quoting Frequency
	 * @param bApplyEOMAdj TRUE - Apply the EOM Adjustment
	 * @param aap The ActActDCParams Instance
	 * @param strCalendar The Calendar
	 * 
	 * @throws java.lang.Exception Thrown if inputs are invalid
	 */

	public YieldInterpreter (
		final java.lang.String strDC,
		final int iFreq,
		final boolean bApplyEOMAdj,
		final org.drip.analytics.daycount.ActActDCParams aap,
		final java.lang.String strCalendar)
		throws java.lang.Exception
	{
		if (null == (_strDC = strDC) || _strDC.isEmpty() || 0 == (_iFreq = iFreq))
			throw new java.lang.Exception ("YieldInterpreter ctr: Invalid quoting params!");

		_aap = aap;
		_strCalendar = strCalendar;
		_bApplyEOMAdj = bApplyEOMAdj;
	}

	/**
	 * Retrieve the Day Count Convention
	 * 
	 * @return The Day Count Convention
	 */

	public java.lang.String dayCount()
	{
		return _strDC;
	}

	/**
	 * Retrieve the Frequency
	 * 
	 * @return The Frequency
	 */

	public int freq()
	{
		return _iFreq;
	}

	/**
	 * Retrieve the EOM Adjustment
	 * 
	 * @return The EOM Adjustment
	 */

	public boolean eomAdj()
	{
		return _bApplyEOMAdj;
	}

	/**
	 * Retrieve the Act/Act Day Count Parameters
	 * 
	 * @return The Act/Act Day Count Parameters
	 */

	public org.drip.analytics.daycount.ActActDCParams aap()
	{
		return _aap;
	}

	/**
	 * Retrieve the Calendar
	 * 
	 * @return The Calendar
	 */

	public java.lang.String calendar()
	{
		return _strCalendar;
	}
}
