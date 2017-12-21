
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
 * InvocationManager records the manages the Build/Execution Environment of an Invocation.
 *
 * @author Lakshmi Krishnamurthy
 */

public class InvocationManager
{
	private static org.drip.service.env.BuildRecord s_BuildRecordLatest = null;
	private static org.drip.service.env.InvocationRecord s_InvocationRecord = null;

	/**
	 * Initialize the Invocation Manager
	 * 
	 * @return TRUE - The Invocation Manager successfully initialized
	 */

	public static final boolean Init()
	{
		if (!org.drip.service.env.BuildManager.Init())
		{
			System.out.println ("InvocationManager::Init => Cannot Initialize Build Manager!");

			return false;
		}

		s_BuildRecordLatest = org.drip.service.env.BuildManager.latestBuildRecord();

		s_InvocationRecord = new org.drip.service.env.InvocationRecord();

		return true;
	}

	/**
	 * Setup the Invocation Manager
	 * 
	 * @return TRUE - The Invocation Manager successfully Setup
	 */

	public static final boolean Setup()
	{
		if (!s_InvocationRecord.recordSetup()) return false;

		System.out.println();

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println ("\t|    Copyright (C) 2011-2018 (DRIP)");

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println ("\t|    Build Version  => " + s_BuildRecordLatest.dripVersion() + " multi mode");

		System.out.println ("\t|    Build JVM (TM) => " + s_BuildRecordLatest.javaVersion());

		System.out.println ("\t|    Build Snap     => " + s_BuildRecordLatest.timeStamp());

		System.out.println ("\t|    Start Time     => " + s_InvocationRecord.startSnap());

		System.out.println ("\t|    Setup Time     => " + s_InvocationRecord.setupSnap());

		System.out.println ("\t|    Setup Duration => " + s_InvocationRecord.setup (true));

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println();

		return true;
	}

	/**
	 * Retrieve the Latest Build Record
	 * 
	 * @return The Latest Build Record
	 */

	public static final org.drip.service.env.BuildRecord latestBuildRecord()
	{
		return s_BuildRecordLatest;
	}

	/**
	 * Retrieve the Invocation Record
	 * 
	 * @return The Invocation Record
	 */

	public static final org.drip.service.env.InvocationRecord invocationRecord()
	{
		return s_InvocationRecord;
	}

	/**
	 * Terminate the Invocation Manager
	 * 
	 * @return TRUE - The Invocation Manager successfully terminated
	 */

	public static final boolean Terminate()
	{
		if (!s_InvocationRecord.recordFinish()) return false;

		System.out.println();

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println ("\t|    Copyright (C) 2011-2018 (DRIP)");

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println ("\t|    Build Version  => " + s_BuildRecordLatest.dripVersion() + " multi mode");

		System.out.println ("\t|    Build JVM (TM) => " + s_BuildRecordLatest.javaVersion());

		System.out.println ("\t|    Build Snap     => " + s_BuildRecordLatest.timeStamp());

		System.out.println ("\t|    Start Time     => " + s_InvocationRecord.startSnap());

		System.out.println ("\t|    Setup Time     => " + s_InvocationRecord.setupSnap());

		System.out.println ("\t|    Finish Time    => " + s_InvocationRecord.finishSnap());

		System.out.println ("\t|    Setup Duration => " + s_InvocationRecord.setup (true));

		System.out.println ("\t|    Run Duration   => " + s_InvocationRecord.elapsed (true));

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println();

		return true;
	}
}
