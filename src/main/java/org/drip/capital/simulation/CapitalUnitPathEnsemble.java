
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
 * <i>CapitalUnitPathEnsemble</i> generates the Ensemble of Capital Paths from the Simulation PnL
 * 	Realizations for the specified Capital Unit. The References are:
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

public class CapitalUnitPathEnsemble
	implements org.drip.capital.simulation.PathEnsemble
{
	private java.util.Map<java.lang.Double, java.util.List<java.lang.Integer>> _pnlListMap = null;
	private java.util.Map<java.lang.String, java.lang.Integer> _systemicEventIncidenceCountMap = null;
	private java.util.List<org.drip.capital.simulation.PathPnLRealization> _pathPnLRealizationList = null;
	private java.util.Map<java.lang.String, java.lang.Integer> _idiosyncraticEventIncidenceCountMap = null;

	/**
	 * CapitalUnitPathEnsemble Constructor
	 */

	public CapitalUnitPathEnsemble()
	{
		_pnlListMap = new java.util.TreeMap<java.lang.Double, java.util.List<java.lang.Integer>>();

		_systemicEventIncidenceCountMap = new java.util.HashMap<java.lang.String, java.lang.Integer>();

		_idiosyncraticEventIncidenceCountMap = new java.util.HashMap<java.lang.String, java.lang.Integer>();

		_pathPnLRealizationList = new
			java.util.ArrayList<org.drip.capital.simulation.PathPnLRealization>();
	}

	/**
	 * Retrieve the Path PnL Realization List
	 * 
	 * @return The Path PnL Realization List
	 */

	public java.util.List<org.drip.capital.simulation.PathPnLRealization> pathPnLRealizationList()
	{
		return _pathPnLRealizationList;
	}

	/**
	 * Retrieve the Path PnL Realization Array
	 * 
	 * @return The Path PnL Realization Array
	 */

	public org.drip.capital.simulation.PathPnLRealization[] pathPnLRealizationArray()
	{
		int pathCount = _pathPnLRealizationList.size();

		org.drip.capital.simulation.PathPnLRealization[] pathPnLRealizationArray =
			new org.drip.capital.simulation.PathPnLRealization[pathCount];

		for (int pathIndex = 0; pathIndex < pathCount; ++pathIndex)
		{
			pathPnLRealizationArray[pathIndex] = _pathPnLRealizationList.get (
				pathIndex
			);
		}

		return pathPnLRealizationArray;
	}

	@Override public boolean addPathPnLRealization (
		final org.drip.capital.simulation.PathPnLRealization pathPnLRealization)
	{
		if (null == pathPnLRealization)
		{
			return false;
		}

		_pathPnLRealizationList.add (
			pathPnLRealization
		);

		double grossPnL = pathPnLRealization.grossPnL();

		if (_pnlListMap.containsKey (
			grossPnL
		))
		{
			_pnlListMap.get (
				grossPnL
			).add (
				_pathPnLRealizationList.size() - 1
			);
		}
		else
		{
			java.util.List<java.lang.Integer> instanceList = new java.util.ArrayList<java.lang.Integer>();

			instanceList.add (
				_pathPnLRealizationList.size() - 1
			);

			_pnlListMap.put (
				grossPnL,
				instanceList
			);
		}

		org.drip.capital.simulation.StressEventIncidenceEnsemble systemicStressEventIncidenceEnsemble =
			pathPnLRealization.systemic();

		if (null != systemicStressEventIncidenceEnsemble)
		{
			for (org.drip.capital.simulation.StressEventIncidence stressEventIncidence :
				systemicStressEventIncidenceEnsemble.stressEventIncidenceList())
			{
				java.lang.String event = stressEventIncidence.name();

				if (_systemicEventIncidenceCountMap.containsKey (
					event
				))
				{
					_systemicEventIncidenceCountMap.put (
						event,
						_systemicEventIncidenceCountMap.get (
							event
						) + 1
					);
				}
				else
				{
					_systemicEventIncidenceCountMap.put (
						event,
						1
					);
				}
			}
		}

		org.drip.capital.simulation.StressEventIncidenceEnsemble idiosyncraticStressEventIncidenceEnsemble
			= pathPnLRealization.idiosyncratic();

		if (null != idiosyncraticStressEventIncidenceEnsemble)
		{
			for (org.drip.capital.simulation.StressEventIncidence stressEventIncidence :
				idiosyncraticStressEventIncidenceEnsemble.stressEventIncidenceList())
			{
				java.lang.String event = stressEventIncidence.name();

				if (_idiosyncraticEventIncidenceCountMap.containsKey (
					event
				))
				{
					_idiosyncraticEventIncidenceCountMap.put (
						event,
						_idiosyncraticEventIncidenceCountMap.get (
							event
						) + 1
					);
				}
				else
				{
					_idiosyncraticEventIncidenceCountMap.put (
						event,
						1
					);
				}
			}
		}

		return true;
	}

	@Override public java.util.Map<java.lang.Double, java.util.List<java.lang.Integer>> pnlListMap()
	{
		return _pnlListMap;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Integer> systemicEventIncidenceCountMap()
	{
		return _systemicEventIncidenceCountMap;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Integer> idiosyncraticEventIncidenceCountMap()
	{
		return _idiosyncraticEventIncidenceCountMap;
	}

	@Override public int count()
	{
		return _pathPnLRealizationList.size();
	}

	@Override public int systemicEventIncidenceCount (
		final java.lang.String event)
	{
		return null == event || event.isEmpty() || !_systemicEventIncidenceCountMap.containsKey (
			event
		) ? 0 : _systemicEventIncidenceCountMap.get (
			event
		);
	}

	@Override public int idiosyncraticEventIncidenceCount (
		final java.lang.String event)
	{
		return null == event || event.isEmpty() || !_idiosyncraticEventIncidenceCountMap.containsKey (
			event
		) ? 0 : _idiosyncraticEventIncidenceCountMap.get (
			event
		);
	}

	@Override public double var (
		final int confidenceCount)
		throws java.lang.Exception
	{
		if (0 >= confidenceCount || _pathPnLRealizationList.size() <= confidenceCount)
		{
			throw new java.lang.Exception (
				"CapitalUnitPathEnsemble::var => Invalid Inputs!"
			);
		}

		int cumulativeInstanceCount = 0;

		for (java.util.Map.Entry<java.lang.Double, java.util.List<java.lang.Integer>> pnlListEntry :
			_pnlListMap.entrySet())
		{
			if (confidenceCount <=
				(cumulativeInstanceCount = cumulativeInstanceCount + pnlListEntry.getValue().size()))
			{
				return pnlListEntry.getKey();
			}
		}

		throw new java.lang.Exception (
			"CapitalUnitPathEnsemble::var => Confidence Count Too High!"
		);
	}

	@Override public double var (
		final double confidenceLevel)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				confidenceLevel
			) || 0. >= confidenceLevel || 1. <= confidenceLevel)
		{
			throw new java.lang.Exception (
				"CapitalUnitPathEnsemble::var => Invalid Inputs!"
			);
		}

		return var (
			(int) (_pathPnLRealizationList.size() * (1. - confidenceLevel))
		);
	}

	@Override public double expectedShortfall (
		final int confidenceCount)
		throws java.lang.Exception
	{
		if (0 >= confidenceCount || _pathPnLRealizationList.size() <= confidenceCount)
		{
			throw new java.lang.Exception (
				"CapitalUnitPathEnsemble::expectedShortfall => Invalid Inputs!"
			);
		}

		int instanceCount = 0;
		double cumulativeShortfall = 0.;

		for (java.util.Map.Entry<java.lang.Double, java.util.List<java.lang.Integer>> pnlListEntry :
			_pnlListMap.entrySet())
		{
			double pnl = pnlListEntry.getKey();

			int entrySize = pnlListEntry.getValue().size();

			for (int entryIndex = 0;
				entryIndex < entrySize;
				++entryIndex)
			{
				cumulativeShortfall = cumulativeShortfall + pnl;

				if (++instanceCount == confidenceCount)
				{
					return cumulativeShortfall / confidenceCount;
				}
			}
		}

		throw new java.lang.Exception (
			"CapitalUnitPathEnsemble::expectedShortfall => Confidence Count Too High!"
		);
	}

	@Override public double expectedShortfall (
		final double confidenceLevel)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				confidenceLevel
			) || 0. >= confidenceLevel || 1. <= confidenceLevel
		)
		{
			throw new java.lang.Exception (
				"CapitalUnitPathEnsemble::expectedShortfall => Invalid Inputs!"
			);
		}

		return expectedShortfall (
			(int) (_pathPnLRealizationList.size() * (1. - confidenceLevel))
		);
	}

	@Override public org.drip.capital.explain.CapitalUnitPnLAttribution pnlAttribution (
		final int confidenceCount)
	{
		if (0 >= confidenceCount || _pathPnLRealizationList.size() <= confidenceCount)
		{
			return null;
		}

		int instanceCount = 0;

		java.util.List<org.drip.capital.simulation.PathPnLRealization> selectedPathPnLRealizationList =
			new java.util.ArrayList<org.drip.capital.simulation.PathPnLRealization>();

		for (java.util.Map.Entry<java.lang.Double, java.util.List<java.lang.Integer>> pnlListEntry :
			_pnlListMap.entrySet())
		{
			for (int listIndex : pnlListEntry.getValue())
			{
				selectedPathPnLRealizationList.add (
					_pathPnLRealizationList.get (
						listIndex
					)
				);

				if (++instanceCount == confidenceCount)
				{
					break;
				}
			}

			if (instanceCount == confidenceCount)
			{
				break;
			}
		}

		try
		{
			return new org.drip.capital.explain.CapitalUnitPnLAttribution (
				selectedPathPnLRealizationList
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.capital.explain.CapitalUnitPnLAttribution pnlAttribution (
		final double confidenceLevel)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
				confidenceLevel
			) || 0. >= confidenceLevel || 1. <= confidenceLevel
		)
		{
			return null;
		}

		return pnlAttribution (
			(int) (_pathPnLRealizationList.size() * (1. - confidenceLevel))
		);
	}

	@Override public org.drip.capital.explain.CapitalUnitPnLAttribution pnlAttribution (
		final java.util.List<java.lang.Integer> pathIndexList)
	{
		if (null == pathIndexList || 0 == pathIndexList.size())
		{
			return null;
		}

		java.util.List<org.drip.capital.simulation.PathPnLRealization> selectedPathPnLRealizationList =
			new java.util.ArrayList<org.drip.capital.simulation.PathPnLRealization>();

		for (int pathIndex : pathIndexList)
		{
			selectedPathPnLRealizationList.add (
				_pathPnLRealizationList.get (
					pathIndex
				)
			);
		}

		try
		{
			return new org.drip.capital.explain.CapitalUnitPnLAttribution (
				selectedPathPnLRealizationList
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override public java.util.List<java.lang.Double> grossSystemicStressPnLList()
	{
		java.util.List<java.lang.Double> grossSystemicStressPnLList =
			new java.util.ArrayList<java.lang.Double>();

		for (org.drip.capital.simulation.PathPnLRealization pathPnLRealization : _pathPnLRealizationList)
		{
			grossSystemicStressPnLList.add (
				pathPnLRealization.grossSystemicStressPnL()
			);
		}

		return grossSystemicStressPnLList;
	}

	@Override public java.util.List<java.lang.Double> grossIdiosyncraticStressPnLList()
	{
		java.util.List<java.lang.Double> grossIdiosyncraticStressPnLList =
			new java.util.ArrayList<java.lang.Double>();

		for (org.drip.capital.simulation.PathPnLRealization pathPnLRealization : _pathPnLRealizationList)
		{
			grossIdiosyncraticStressPnLList.add (
				pathPnLRealization.grossIdiosyncraticStressPnL()
			);
		}

		return grossIdiosyncraticStressPnLList;
	}

	@Override public java.util.List<java.lang.Double> grossFSPnLList()
	{
		java.util.List<java.lang.Double> grossFSPnLList = new java.util.ArrayList<java.lang.Double>();

		for (org.drip.capital.simulation.PathPnLRealization pathPnLRealization : _pathPnLRealizationList)
		{
			grossFSPnLList.add (
				pathPnLRealization.grossFSPnL()
			);
		}

		return grossFSPnLList;
	}

	@Override public java.util.List<java.lang.Double> grossPnLList()
	{
		java.util.List<java.lang.Double> grossPnLList = new java.util.ArrayList<java.lang.Double>();

		for (org.drip.capital.simulation.PathPnLRealization pathPnLRealization : _pathPnLRealizationList)
		{
			grossPnLList.add (
				pathPnLRealization.grossPnL()
			);
		}

		return grossPnLList;
	}

	@Override public org.drip.capital.simulation.EnsemblePnLDistribution ensembleDistribution()
	{
		java.util.List<java.lang.Double> grossPnLList = new java.util.ArrayList<java.lang.Double>();

		java.util.List<java.lang.Double> grossFSPnLList = new java.util.ArrayList<java.lang.Double>();

		java.util.List<java.lang.Double> grossSystemicStressPnLList =
			new java.util.ArrayList<java.lang.Double>();

		java.util.List<java.lang.Double> grossIdiosyncraticStressPnLList =
			new java.util.ArrayList<java.lang.Double>();

		for (org.drip.capital.simulation.PathPnLRealization pathPnLRealization : _pathPnLRealizationList)
		{
			grossSystemicStressPnLList.add (
				pathPnLRealization.grossSystemicStressPnL()
			);

			grossIdiosyncraticStressPnLList.add (
				pathPnLRealization.grossIdiosyncraticStressPnL()
			);

			grossFSPnLList.add (
				pathPnLRealization.grossFSPnL()
			);

			grossPnLList.add (
				pathPnLRealization.grossPnL()
			);
		}

		try
		{
			return new org.drip.capital.simulation.EnsemblePnLDistribution (
				grossSystemicStressPnLList,
				grossIdiosyncraticStressPnLList,
				grossFSPnLList,
				grossPnLList
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
