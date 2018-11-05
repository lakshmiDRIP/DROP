
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
 * <i>LSQMPointUpdate</i> contains the Snapshot and the Increment of the Evolving Point Latent State
 *  Quantification Metrics.
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics">Dynamics</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/dynamics/evolution">Evolution</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/Fixed Income">Fixed Income Analytics</a></li>
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
