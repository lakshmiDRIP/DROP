
package org.drip.dynamics.evolution;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
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
 * <i>LSQMCurveUpdate</i> contains the Snapshot and the Increment of the Evolving Curve Latent State
 * Quantification Metrics.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">Dynamics</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/evolution/README.md">Evolution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LSQMCurveUpdate {
	private int _iFinalDate = java.lang.Integer.MIN_VALUE;
	private int _iInitialDate = java.lang.Integer.MIN_VALUE;
	private org.drip.dynamics.evolution.LSQMCurveSnapshot _snapshot = null;
	private org.drip.dynamics.evolution.LSQMCurveIncrement _increment = null;

	/**
	 * LSQMCurveUpdate Constructor
	 * 
	 * @param iInitialDate The Initial Date
	 * @param iFinalDate The Final Date
	 * @param snapshot The LSQM Curve Snapshot
	 * @param increment The LSQM Curve Increment
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public LSQMCurveUpdate (
		final int iInitialDate,
		final int iFinalDate,
		final org.drip.dynamics.evolution.LSQMCurveSnapshot snapshot,
		final org.drip.dynamics.evolution.LSQMCurveIncrement increment)
		throws java.lang.Exception
	{
		if (null == (_snapshot = snapshot) || (_iFinalDate = iFinalDate) < (_iInitialDate = iInitialDate))
			throw new java.lang.Exception ("LSQMCurveUpdate ctr: Invalid Inputs");

		_increment = increment;
	}

	/**
	 * Retrieve the Initial Date
	 * 
	 * @return The Initial Date
	 */

	public int initialDate()
	{
		return _iInitialDate;
	}

	/**
	 * Retrieve the Final Date
	 * 
	 * @return The Final Date
	 */

	public int finalDate()
	{
		return _iFinalDate;
	}

	/**
	 * Retrieve the LSQM Curve Snapshot
	 * 
	 * @return The LSQM Curve Snapshot
	 */

	public org.drip.dynamics.evolution.LSQMCurveSnapshot snapshot()
	{
		return _snapshot;
	}

	/**
	 * Retrieve the LSQM Curve Increment
	 * 
	 * @return The LSQM Curve Increment
	 */

	public org.drip.dynamics.evolution.LSQMCurveIncrement increment()
	{
		return _increment;
	}
}
