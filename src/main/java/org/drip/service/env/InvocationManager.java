
package org.drip.service.env;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
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
 * <i>InvocationManager</i> records the manages the Build/Execution Environment of an Invocation. It provides
 * 	the following Functions:
 * 
 * <ul>
 * 		<li>Initialize the Invocation Manager</li>
 * 		<li>Setup the Invocation Manager</li>
 * 		<li>Retrieve the Latest Build Record</li>
 * 		<li>Retrieve the Invocation Record</li>
 * 		<li>Terminate the Invocation Manager</li>
 * </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationSupportLibrary.md">Computation Support</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/README.md">Environment, Product/Definition Containers, and Scenario/State Manipulation APIs</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/service/env/README.md">Library Module Loader Environment Manager</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class InvocationManager
{
	private static BuildRecord _buildRecordLatest = null;
	private static InvocationRecord _invocationRecord = null;

	/**
	 * Initialize the Invocation Manager
	 * 
	 * @return TRUE - The Invocation Manager successfully initialized
	 */

	public static final boolean Init()
	{
		if (!BuildManager.Init()) {
			System.out.println ("InvocationManager::Init => Cannot Initialize Build Manager!");

			return false;
		}

		_buildRecordLatest = BuildManager.latestBuildRecord();

		_invocationRecord = new InvocationRecord();

		return true;
	}

	/**
	 * Setup the Invocation Manager
	 * 
	 * @return TRUE - The Invocation Manager successfully Setup
	 */

	public static final boolean Setup()
	{
		if (!_invocationRecord.recordSetup()) {
			return false;
		}

		System.out.println();

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println ("\t|    Copyright (C) 2011-2025 (DRIP, DROP)");

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println ("\t|    Build Version  => " + _buildRecordLatest.dripVersion() + " multi");

		System.out.println (
			"\t|    Build JVM (TM) => " + _buildRecordLatest.javaVersion() + " mixed mode, sharing"
		);

		System.out.println ("\t|    Build Snap     => " + _buildRecordLatest.timeStamp());

		System.out.println ("\t|    Start Time     => " + _invocationRecord.startSnap());

		System.out.println ("\t|    Setup Time     => " + _invocationRecord.setupSnap());

		System.out.println ("\t|    Setup Duration => " + _invocationRecord.setup (true));

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println();

		return true;
	}

	/**
	 * Retrieve the Latest Build Record
	 * 
	 * @return The Latest Build Record
	 */

	public static final BuildRecord latestBuildRecord()
	{
		return _buildRecordLatest;
	}

	/**
	 * Retrieve the Invocation Record
	 * 
	 * @return The Invocation Record
	 */

	public static final InvocationRecord invocationRecord()
	{
		return _invocationRecord;
	}

	/**
	 * Terminate the Invocation Manager
	 * 
	 * @return TRUE - The Invocation Manager successfully terminated
	 */

	public static final boolean Terminate()
	{
		if (!_invocationRecord.recordFinish()) {
			return false;
		}

		System.out.println();

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println ("\t|    Copyright (C) 2011-2025 (DRIP, DROP)");

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println ("\t|    Build Version  => " + _buildRecordLatest.dripVersion() + " multi");

		System.out.println (
			"\t|    Build JVM (TM) => " + _buildRecordLatest.javaVersion() + " mixed mode, sharing"
		);

		System.out.println ("\t|    Build Snap     => " + _buildRecordLatest.timeStamp());

		System.out.println ("\t|    Start Time     => " + _invocationRecord.startSnap());

		System.out.println ("\t|    Setup Time     => " + _invocationRecord.setupSnap());

		System.out.println ("\t|    Finish Time    => " + _invocationRecord.finishSnap());

		System.out.println ("\t|    Setup Duration => " + _invocationRecord.setup (true));

		System.out.println ("\t|    Run Duration   => " + _invocationRecord.elapsed (true));

		System.out.println ("\t|-----------------------------------------------------------------|");

		System.out.println();

		return true;
	}
}
