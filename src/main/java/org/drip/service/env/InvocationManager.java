
package org.drip.service.env;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>InvocationManager</i> records the manages the Build/Execution Environment of an Invocation.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service">Service</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env">Env</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/NumericalOptimizer">Numerical Optimizer Library</a></li>
 *  </ul>
 * <br><br>
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
