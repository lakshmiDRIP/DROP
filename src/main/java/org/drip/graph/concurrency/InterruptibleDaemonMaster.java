
package org.drip.graph.concurrency;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
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
 * <i>InterruptibleDaemonMaster</i> controls a Gracefully Interruptible Daemon.
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/README.md">Graph Optimization and Tree Construction Algorithms</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/graph/concurrency/README.md">Helper Classes For Concurrent Tasks</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class InterruptibleDaemonMaster
{
	private long _hardTimeout = -1L;
	private long _softTimeout = -1L;
	private org.drip.graph.concurrency.InterruptibleDaemon _interruptibleDaemon = null;

	/**
	 * InterruptibleDaemonMaster Constructor
	 * 
	 * @param interruptibleDaemon Interruptible Daemon Task
	 * @param softTimeout Soft Timeout
	 * @param hardTimeout Hard Timeout
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public InterruptibleDaemonMaster (
		final org.drip.graph.concurrency.InterruptibleDaemon interruptibleDaemon,
		final long softTimeout,
		final long hardTimeout)
		throws java.lang.Exception
	{
		if (null == (_interruptibleDaemon = interruptibleDaemon) ||
			0 >= (_softTimeout = softTimeout) ||
			0 >= (_hardTimeout = hardTimeout) || _hardTimeout <= _softTimeout
		)
		{
			throw new java.lang.Exception (
				"InterruptibleDaemonMaster Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Interruptible Daemon Task
	 * 
	 * @return The Interruptible Daemon Task
	 */

	public org.drip.graph.concurrency.InterruptibleDaemon interruptibleDaemon()
	{
		return _interruptibleDaemon;
	}

	/**
	 * Retrieve the Soft Timeout
	 * 
	 * @return The Soft Timeout
	 */

	public long softTimeout()
	{
		return _softTimeout;
	}

	/**
	 * Retrieve the Hard Timeout
	 * 
	 * @return The Hard Timeout
	 */

	public long hardTimeout()
	{
		return _hardTimeout;
	}

	/**
	 * Run the Daemon Monitor
	 * 
	 * @return TRUE - The Daemon successfully completed
	 * 
	 * @throws java.lang.InterruptedException Thrown if the Underlying Thread Calls are Interrupted
	 */

	public boolean monitor()
		throws java.lang.InterruptedException
	{
		long startTime = System.currentTimeMillis();

		java.lang.Thread interruptibleDaemonThread = new java.lang.Thread (
			_interruptibleDaemon
		);

		interruptibleDaemonThread.start();

		while (interruptibleDaemonThread.isAlive())
		{
			interruptibleDaemonThread.join (
				_softTimeout
			);

			if (System.currentTimeMillis() - startTime > _hardTimeout &&
				interruptibleDaemonThread.isAlive()
			)
			{
				interruptibleDaemonThread.interrupt();

				interruptibleDaemonThread.join();
			}
		}

		return true;
	}
}
