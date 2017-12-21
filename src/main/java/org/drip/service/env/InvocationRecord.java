
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
 * InvocationRecord implements the Invocation Start/Finish Times of a given Invocation.
 *
 * @author Lakshmi Krishnamurthy
 */

public class InvocationRecord
{
	private long _lBeginNanos = -1L;
	private long _lSetupNanos = -1L;
	private long _lFinishNanos = -1L;
	private java.util.Date _dtBegin = null;
	private java.util.Date _dtSetup = null;
	private java.util.Date _dtFinish = null;

	/**
	 * InvocationTimes Constructor
	 */

	public InvocationRecord()
	{
		_dtBegin = new java.util.Date();

		_lBeginNanos = System.nanoTime();
	}

	/**
	 * Record the Setup of the Invocation Record
	 * 
	 * @return TRUE - The Invocation Record Setup successfully recorded
	 */

	public boolean recordSetup()
	{
		_lSetupNanos = System.nanoTime();

		_dtSetup = new java.util.Date();

		return true;
	}

	/**
	 * Retrieve the Setup Time
	 * 
	 * @param bHMS Generate the Result in Hours-Minutes-Seconds Format
	 * 
	 * @return The Setup Time
	 */

	public java.lang.String setup (
		final boolean bHMS)
	{
		long lElapsedNanos = _lSetupNanos - _lBeginNanos;

		return bHMS ? org.drip.analytics.support.Helper.IntervalHMSMS (lElapsedNanos) : "" + lElapsedNanos;
	}

	/**
	 * Record the Finish of the Invocation Record
	 * 
	 * @return TRUE - The Invocation Record Finish successfully recorded
	 */

	public boolean recordFinish()
	{
		_lFinishNanos = System.nanoTime();

		_dtFinish = new java.util.Date();

		return true;
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
		long lElapsedNanos = _lFinishNanos - _lSetupNanos;

		return bHMS ? org.drip.analytics.support.Helper.IntervalHMSMS (lElapsedNanos) : "" + lElapsedNanos;
	}

	/**
	 * Retrieve the Begin Snapshot
	 * 
	 * @return The Begin Snapshot
	 */

	public java.util.Date startSnap()
	{
		return _dtBegin;
	}

	/**
	 * Retrieve the Setup Snapshot
	 * 
	 * @return The Setup Snapshot
	 */

	public java.util.Date setupSnap()
	{
		return _dtSetup;
	}

	/**
	 * Retrieve the Finish Snapshot
	 * 
	 * @return The Finish Snapshot
	 */

	public java.util.Date finishSnap()
	{
		return _dtFinish;
	}
}
