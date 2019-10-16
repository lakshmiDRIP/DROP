
package org.drip.dynamics.evolution;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>LSQMPointUpdate</i> contains the Snapshot and the Increment of the Evolving Point Latent State
 * Quantification Metrics.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/README.md">HJM, Hull White, LMM, and SABR Dynamic Evolution Models</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/evolution/README.md">Latent State Evolution Edges/Vertexes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LSQMPointUpdate {
	private int _iViewDate = java.lang.Integer.MIN_VALUE;
	private int _iEvolutionStartDate = java.lang.Integer.MIN_VALUE;
	private int _iEvolutionFinishDate = java.lang.Integer.MIN_VALUE;
	private org.drip.dynamics.evolution.LSQMPointRecord _lprSnapshot = null;
	private org.drip.dynamics.evolution.LSQMPointRecord _lprIncrement = null;

	/**
	 * LSQMPointUpdate Constructor
	 * 
	 * @param iEvolutionStartDate The Evolution Start Date
	 * @param iEvolutionFinishDate The Evolution Finish Date
	 * @param iViewDate The View Date
	 * @param lprSnapshot The LSQM Point Record Snapshot
	 * @param lprIncrement The LSQM Point Record Update
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public LSQMPointUpdate (
		final int iEvolutionStartDate,
		final int iEvolutionFinishDate,
		final int iViewDate,
		final org.drip.dynamics.evolution.LSQMPointRecord lprSnapshot,
		final org.drip.dynamics.evolution.LSQMPointRecord lprIncrement)
		throws java.lang.Exception
	{
		if (null == (_lprSnapshot = lprSnapshot) || (_iEvolutionFinishDate = iEvolutionFinishDate) <
			(_iEvolutionStartDate = iEvolutionStartDate) || (_iViewDate = iViewDate) < _iEvolutionStartDate)
			throw new java.lang.Exception ("LSQMPointUpdate ctr: Invalid Inputs");

		_lprIncrement = lprIncrement;
	}

	/**
	 * Retrieve the Evolution Start Date
	 * 
	 * @return The Evolution Start Date
	 */

	public int evolutionStartDate()
	{
		return _iEvolutionStartDate;
	}

	/**
	 * Retrieve the Evolution Finish Date
	 * 
	 * @return The Evolution Finish Date
	 */

	public int evolutionFinishDate()
	{
		return _iEvolutionFinishDate;
	}

	/**
	 * Retrieve the View Date
	 * 
	 * @return The View Date
	 */

	public int viewDate()
	{
		return _iViewDate;
	}

	/**
	 * Retrieve the LSQM Point Snapshot
	 * 
	 * @return The LSQM Point Snapshot
	 */

	public org.drip.dynamics.evolution.LSQMPointRecord snapshot()
	{
		return _lprSnapshot;
	}

	/**
	 * Retrieve the LSQM Point Increment
	 * 
	 * @return The LSQM Point Increment
	 */

	public org.drip.dynamics.evolution.LSQMPointRecord increment()
	{
		return _lprIncrement;
	}
}
