
package org.drip.service.env;

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
 * FrameContext implements the Invocation Context of the Environment Frame Shell.
 *
 * @author Lakshmi Krishnamurthy
 */

public class FrameContext {
	private long _lStartNanos = -1L;
	private long _lElapsedNanos = -1L;
	private java.util.Date _dtStart = null;
	private java.util.Date _dtFinish = null;

	/**
	 * FrameContext Constructor
	 */

	public FrameContext()
	{
		_lStartNanos = System.nanoTime();

		_dtStart = new java.util.Date();
	}

	/**
	 * Retrieve the Start Time
	 * 
	 * @return The Start Time
	 */

	public java.util.Date startTime()
	{
		return _dtStart;
	}

	/**
	 * Record the Finish of the Frame Context
	 * 
	 * @return TRUE - The Frame Context FInish successfully recorded
	 */

	public boolean recordFinish()
	{
		_dtFinish = new java.util.Date();

		_lElapsedNanos = System.nanoTime() - _lStartNanos;

		return true;
	}

	/**
	 * Retrieve the Finish Time
	 * 
	 * @return The Finish Time
	 */

	public java.util.Date finishTime()
	{
		return _dtFinish;
	}

	/**
	 * Retrieve the Elapsed Time
	 * 
	 * @param bHMS Generate the Result in Hours-Minutes-Seconds Format
	 * 
	 * @return The Elapsed Time
	 */

	public java.lang.String elapsed (
		final boolean bHMS)
	{
		if (!bHMS)
		{
			return "" + _lElapsedNanos;
		}

		java.lang.String strHMS = "";
		long lElapsedSeconds = (long) ((0.5 + _lElapsedNanos) * 1.e-09);
		long lElapsedMillis = (_lElapsedNanos - lElapsedSeconds * 1000000000) / 1000000;

		if (lElapsedSeconds >= 3600)
		{
			strHMS = strHMS + (lElapsedSeconds / 3600) + " h ";
			lElapsedSeconds = lElapsedSeconds % 3600;
		}

		if (lElapsedSeconds >= 60)
		{
			strHMS = strHMS + (lElapsedSeconds / 60) + " m ";
			lElapsedSeconds = lElapsedSeconds % 60;
		}

		if (lElapsedSeconds > 0)
		{
			strHMS = strHMS + lElapsedSeconds + " s ";
		}

		return strHMS + lElapsedMillis + " ms";
	}
}
