
package org.drip.service.env;

import java.util.Date;

import org.drip.analytics.support.Helper;

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
 * <i>InvocationRecord</i> implements the Invocation Start/Finish Times of a given Invocation. It provides
 * 	the following Functions:
 * 
 * <ul>
 * 		<li><i>InvocationRecord</i> Constructor</li>
 * 		<li>Record the Setup of the Invocation Record</li>
 * 		<li>Retrieve the Setup Time</li>
 * 		<li>Record the Finish of the Invocation Record</li>
 * 		<li>Retrieve the Elapsed Time</li>
 * 		<li>Retrieve the Begin Snapshot</li>
 * 		<li>Retrieve the Setup Snapshot</li>
 * 		<li>Retrieve the Finish Snapshot</li>
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

public class InvocationRecord
{
	private Date _beginDate = null;
	private Date _setupDate = null;
	private long _beginNanos = -1L;
	private long _setupNanos = -1L;
	private Date _finishDate = null;
	private long _finishNanos = -1L;

	/**
	 * <i>InvocationRecord</i> Constructor
	 */

	public InvocationRecord()
	{
		_beginDate = new Date();

		_beginNanos = System.nanoTime();
	}

	/**
	 * Record the Setup of the Invocation Record
	 * 
	 * @return TRUE - The Invocation Record Setup successfully recorded
	 */

	public boolean recordSetup()
	{
		_setupNanos = System.nanoTime();

		_setupDate = new Date();

		return true;
	}

	/**
	 * Retrieve the Setup Time
	 * 
	 * @param generateHMS Generate the Result in Hours-Minutes-Seconds Format
	 * 
	 * @return The Setup Time
	 */

	public String setup (
		final boolean generateHMS)
	{
		long elapsedNanos = _setupNanos - _beginNanos;

		return generateHMS ? Helper.IntervalHMSMS (elapsedNanos) : "" + elapsedNanos;
	}

	/**
	 * Record the Finish of the Invocation Record
	 * 
	 * @return TRUE - The Invocation Record Finish successfully recorded
	 */

	public boolean recordFinish()
	{
		_finishNanos = System.nanoTime();

		_finishDate = new Date();

		return true;
	}

	/**
	 * Retrieve the Elapsed Time
	 * 
	 * @param generateHMS Generate the Result in Hours-Minutes-Seconds Format
	 * 
	 * @return The Elapsed Time
	 */

	public String elapsed (
		final boolean generateHMS)
	{
		long elapsedNanos = _finishNanos - _setupNanos;

		return generateHMS ? Helper.IntervalHMSMS (elapsedNanos) : "" + elapsedNanos;
	}

	/**
	 * Retrieve the Begin Snapshot
	 * 
	 * @return The Begin Snapshot
	 */

	public Date startSnap()
	{
		return _beginDate;
	}

	/**
	 * Retrieve the Setup Snapshot
	 * 
	 * @return The Setup Snapshot
	 */

	public Date setupSnap()
	{
		return _setupDate;
	}

	/**
	 * Retrieve the Finish Snapshot
	 * 
	 * @return The Finish Snapshot
	 */

	public Date finishSnap()
	{
		return _finishDate;
	}
}
