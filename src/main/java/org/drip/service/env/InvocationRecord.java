
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
 * <i>InvocationRecord</i> implements the Invocation Start/Finish Times of a given Invocation.
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
