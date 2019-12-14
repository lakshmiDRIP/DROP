
package org.drip.capital.simulation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>PathPnLRealization</i> holds the Realized PnL and its Components along a Simulated Path. The References
 * 	are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision(2005): Stress Testing at Major Financial Institutions: Survey
 * 				Results and Practice https://www.bis.org/publ/cgfs24.htm
 * 		</li>
 * 		<li>
 * 			Glasserman, P. (2004): <i>Monte Carlo Methods in Financial Engineering</i> <b>Springer</b>
 * 		</li>
 * 		<li>
 * 			Kupiec, P. H. (2000): Stress Tests and Risk Capital <i>Risk</i> <b>2 (4)</b> 27-39
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/simulation/README.md">Economic Risk Capital Simulation Ensemble</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PathPnLRealization
{
	private int _pathIndex = -1;
	private org.drip.capital.simulation.StressEventIncidenceEnsemble _systemic = null;
	private java.util.Map<java.lang.String, java.lang.Double> _fsPnLDecompositionMap = null;
	private org.drip.capital.simulation.StressEventIncidenceEnsemble _idiosyncratic = null;

	/**
	 * Combine the Path Realizations onto One
	 * 
	 * @param pathPnLRealizationArray Array of Path PnL Realizations
	 * 
	 * @return The Path Realizations combined into One
	 */

	public static final PathPnLRealization Combine (
		final PathPnLRealization[] pathPnLRealizationArray)
	{
		if (null == pathPnLRealizationArray)
		{
			return null;
		}

		int pathPnLRealizationCount = pathPnLRealizationArray.length;

		if (0 == pathPnLRealizationCount)
		{
			return null;
		}

		java.util.Map<java.lang.String, java.lang.Double> fsPnLDecompositionMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<>();

		org.drip.capital.simulation.StressEventIncidenceEnsemble systemicStressEventIncidenceEnsemble =
			new org.drip.capital.simulation.StressEventIncidenceEnsemble();

		org.drip.capital.simulation.StressEventIncidenceEnsemble idiosyncraticStressEventIncidenceEnsemble
			= new org.drip.capital.simulation.StressEventIncidenceEnsemble();

		for (int pathPnLRealizationIndex = 0;
			pathPnLRealizationIndex < pathPnLRealizationCount;
			++pathPnLRealizationIndex)
		{
			if (null == pathPnLRealizationArray[pathPnLRealizationIndex])
			{
				return null;
			}

			java.util.Map<java.lang.String, java.lang.Double> pathFSPnLDecompositionMap =
				pathPnLRealizationArray[pathPnLRealizationIndex].fsPnLDecompositionMap();

			if (null != pathFSPnLDecompositionMap)
			{
				java.util.Set<java.lang.String> fsPnLDecompositionKeySet = pathFSPnLDecompositionMap.keySet();

				for (java.lang.String fsPnLDecompositionKey : fsPnLDecompositionKeySet)
				{
					if (fsPnLDecompositionMap.containsKey (fsPnLDecompositionKey))
					{
						fsPnLDecompositionMap.put (
							fsPnLDecompositionKey,
							fsPnLDecompositionMap.get (fsPnLDecompositionKey) +
								pathFSPnLDecompositionMap.get (fsPnLDecompositionKey)
						);
					}
					else
					{
						fsPnLDecompositionMap.put (
							fsPnLDecompositionKey,
							pathFSPnLDecompositionMap.get (fsPnLDecompositionKey)
						);
					}
				}
			}

			org.drip.capital.simulation.StressEventIncidenceEnsemble
				pathSystemicStressEventIncidenceEnsemble =
					pathPnLRealizationArray[pathPnLRealizationIndex].systemic();

			if (null != pathSystemicStressEventIncidenceEnsemble)
			{
				java.util.List<org.drip.capital.simulation.StressEventIncidence>
					pathSystemicStressEventIncidenceList =
						pathSystemicStressEventIncidenceEnsemble.stressEventIncidenceList();

				for (org.drip.capital.simulation.StressEventIncidence pathSystemicStressEventIncidence :
					pathSystemicStressEventIncidenceList)
				{
					systemicStressEventIncidenceEnsemble.addStressEventIncidence (
						pathSystemicStressEventIncidence
					);
				}
			}

			org.drip.capital.simulation.StressEventIncidenceEnsemble
				pathIdiosyncraticStressEventIncidenceEnsemble =
					pathPnLRealizationArray[pathPnLRealizationIndex].idiosyncratic();

			if (null != pathIdiosyncraticStressEventIncidenceEnsemble)
			{
				java.util.List<org.drip.capital.simulation.StressEventIncidence>
					pathIdiosyncraticStressEventIncidenceList =
						pathIdiosyncraticStressEventIncidenceEnsemble.stressEventIncidenceList();

				for (org.drip.capital.simulation.StressEventIncidence pathIdiosyncraticStressEventIncidence
					: pathIdiosyncraticStressEventIncidenceList)
				{
					idiosyncraticStressEventIncidenceEnsemble.addStressEventIncidence (
						pathIdiosyncraticStressEventIncidence
					);
				}
			}
		}

		try
		{
			return new PathPnLRealization (
				pathPnLRealizationArray[0].pathIndex(),
				fsPnLDecompositionMap,
				systemicStressEventIncidenceEnsemble,
				idiosyncraticStressEventIncidenceEnsemble
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * PathPnLRealization Constructor
	 * 
	 * @param pathIndex Index of the Realized Path
	 * @param fsPnLDecompositionMap Single Path PnL Decomposition Map by FS Type
	 * @param systemic Systemic Stress Event Incidence Ensemble
	 * @param idiosyncratic Idiosyncratic Stress Event Incidence Ensemble
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PathPnLRealization (
		final int pathIndex,
		final java.util.Map<java.lang.String, java.lang.Double> fsPnLDecompositionMap,
		final org.drip.capital.simulation.StressEventIncidenceEnsemble systemic,
		final org.drip.capital.simulation.StressEventIncidenceEnsemble idiosyncratic)
		throws java.lang.Exception
	{
		if (-1 >= (_pathIndex = pathIndex))
		{
			throw new java.lang.Exception ("PathPnLRealization Constructor => Invalid Inputs");
		}

		_systemic = systemic;
		_idiosyncratic = idiosyncratic;
		_fsPnLDecompositionMap = fsPnLDecompositionMap;
	}

	/**
	 * Retrieve the Path Index
	 * 
	 * @return Path index
	 */

	public int pathIndex()
	{
		return _pathIndex;
	}

	/**
	 * Retrieve the Path FS PnL Decomposition
	 * 
	 * @return Path FS PnL Decomposition
	 */

	public java.util.Map<java.lang.String, java.lang.Double> fsPnLDecompositionMap()
	{
		return _fsPnLDecompositionMap;
	}

	/**
	 * Retrieve the Idiosyncratic Stress Event Incidence Ensemble
	 * 
	 * @return The Idiosyncratic Stress Event Incidence Ensemble
	 */

	public org.drip.capital.simulation.StressEventIncidenceEnsemble idiosyncratic()
	{
		return _idiosyncratic;
	}

	/**
	 * Retrieve the Systemic Stress Event Incidence Ensemble
	 * 
	 * @return The Systemic Stress Event Incidence Ensemble
	 */

	public org.drip.capital.simulation.StressEventIncidenceEnsemble systemic()
	{
		return _systemic;
	}

	/**
	 * Retrieve the Realized Systemic Stress PnL
	 * 
	 * @return The Realized Systemic Stress PnL
	 */

	public double grossSystemicStressPnL()
	{
		return null != _systemic ? _systemic.grossPnL() : 0.;
	}

	/**
	 * Retrieve the Realized Idiosyncratic Stress PnL
	 * 
	 * @return The Realized Idiosyncratic Stress PnL
	 */

	public double grossIdiosyncraticStressPnL()
	{
		return null != _idiosyncratic ? _idiosyncratic.grossPnL() : 0.;
	}

	/**
	 * Retrieve the Realized FS Gross PnL
	 * 
	 * @return The Realized FS Gross PnL
	 */

	public double grossFSPnL()
	{
		if (null == _fsPnLDecompositionMap)
		{
			return 0.;
		}

		double grossFSPnL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> fsPnLDecompositionEntry :
			_fsPnLDecompositionMap.entrySet())
		{
			grossFSPnL = grossFSPnL + fsPnLDecompositionEntry.getValue();
		}

		return grossFSPnL;
	}

	/**
	 * Retrieve the Total Realized PnL
	 * 
	 * @return The Total Realized PnL
	 */

	public double grossPnL()
	{
		return grossSystemicStressPnL() + grossIdiosyncraticStressPnL() + grossFSPnL();
	}
}
