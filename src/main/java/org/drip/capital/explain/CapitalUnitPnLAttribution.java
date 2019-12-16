
package org.drip.capital.explain;

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
 * <i>CapitalUnitPnLAttribution</i> holds the Attributions of the PnL from the Contributing Paths for a Single
 * Capital Unit. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/explain/README.md">Economic Risk Capital Attribution Explain</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CapitalUnitPnLAttribution
	extends org.drip.capital.explain.PnLAttribution
	implements org.drip.capital.simulation.EnsemblePnLDistributionGenerator
{
	private java.util.List<org.drip.capital.simulation.PathPnLRealization> _pathPnLRealizationList = null;

	private static final boolean NormalizeExplainMap (
		final java.util.Map<java.lang.String, java.lang.Double> pnlExplainMap,
		final int normalizer)
	{
		if (null != pnlExplainMap)
		{
			for (java.util.Map.Entry<java.lang.String, java.lang.Double> pnlExplainEntry :
				pnlExplainMap.entrySet())
			{
				pnlExplainMap.put (
					pnlExplainEntry.getKey(),
					pnlExplainEntry.getValue() / normalizer
				);
			}
		}

		return true;
	}

	private boolean updateFSDecompositionMap (
		final java.util.Map<java.lang.String, java.lang.Double> fsPnLMap)
	{
		if (null == _fsPnLDecompositionExplainMap)
		{
			_fsPnLDecompositionExplainMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> fsPnLEntry : fsPnLMap.entrySet())
		{
			java.lang.String fsType = fsPnLEntry.getKey();

			if (_fsPnLDecompositionExplainMap.containsKey (
				fsType
			))
			{
				_fsPnLDecompositionExplainMap.put (
					fsType,
					_fsPnLDecompositionExplainMap.get (
						fsType
					) + fsPnLEntry.getValue()
				);
			}
			else
			{
				_fsPnLDecompositionExplainMap.put (
					fsType,
					fsPnLEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean updatePAACategoryDecompositionMap (
		final java.util.Map<java.lang.String, java.lang.Double> paaCategoryPnLDecomposition)
	{
		if (null == _paaCategoryDecompositionExplainMap)
		{
			_paaCategoryDecompositionExplainMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> paaCategoryPnLEntry :
			paaCategoryPnLDecomposition.entrySet())
		{
			java.lang.String paaCategoryName = paaCategoryPnLEntry.getKey();

			if (_paaCategoryDecompositionExplainMap.containsKey (
				paaCategoryName
			))
			{
				_paaCategoryDecompositionExplainMap.put (
					paaCategoryName,
					paaCategoryPnLEntry.getValue() + _paaCategoryDecompositionExplainMap.get (
						paaCategoryName
					)
				);
			}
			else
			{
				_paaCategoryDecompositionExplainMap.put (
					paaCategoryName,
					paaCategoryPnLEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean addPathPnLRealization (
		final org.drip.capital.simulation.PathPnLRealization pathPnLRealization)
	{
		if (null == pathPnLRealization)
		{
			return false;
		}

		_expectedShortfall = _expectedShortfall + (_var = pathPnLRealization.grossPnL());

		java.util.Map<java.lang.String, java.lang.Double> fsPnLDecompositionMap =
			pathPnLRealization.fsPnLDecompositionMap();

		if (null != fsPnLDecompositionMap)
		{
			if (!updateFSDecompositionMap (
				fsPnLDecompositionMap
			))
			{
				return false;
			}
		}

		org.drip.capital.simulation.StressEventIncidenceEnsemble systemicStressEventIncidenceEnsemble =
			pathPnLRealization.systemic();

		if (null != systemicStressEventIncidenceEnsemble)
		{
			for (org.drip.capital.simulation.StressEventIncidence stressEventIncidence :
				systemicStressEventIncidenceEnsemble.stressEventIncidenceList())
			{
				java.lang.String eventName = stressEventIncidence.name();

				java.lang.String eventType = stressEventIncidence.type();

				if (org.drip.capital.definition.StressScenarioType.SYSTEMIC.equalsIgnoreCase (
					eventType
				))
				{
					if (null == _systemicPnLExplainMap)
					{
						_systemicPnLExplainMap = new
							org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

						_systemicGrossPnLExplainMap = new
							org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
					}

					double systemicPnL = stressEventIncidence.pnl();

					if (_systemicPnLExplainMap.containsKey (
						eventName
					))
					{
						_systemicPnLExplainMap.put (
							eventName,
							_systemicPnLExplainMap.get (
								eventName
							) + systemicPnL
						);

						_systemicGrossPnLExplainMap.put (
							eventName,
							_systemicGrossPnLExplainMap.get (
								eventName
							) + systemicPnL
						);
					}
					else
					{
						_systemicPnLExplainMap.put (
							eventName,
							systemicPnL
						);

						_systemicGrossPnLExplainMap.put (
							eventName,
							systemicPnL
						);
					}

					if (null == _systemicInstanceCountMap)
					{
						_systemicInstanceCountMap = new
							org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();
					}

					if (_systemicInstanceCountMap.containsKey (
						eventName
					))
					{
						_systemicInstanceCountMap.put (
							eventName,
							_systemicInstanceCountMap.get (
								eventName
							) + 1
						);
					}
					else
					{
						_systemicInstanceCountMap.put (
							eventName,
							1
						);
					}
				}
				else if (org.drip.capital.definition.StressScenarioType.CORRELATED.equalsIgnoreCase (
					eventType
				))
				{
					if (null == _correlatedPnLExplainMap)
					{
						_correlatedPnLExplainMap = new
							org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
					}

					if (null == _correlatedPnLWorstMap)
					{
						_correlatedPnLWorstMap = new
							org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
					}

					double correlatedPnL = stressEventIncidence.pnl();

					if (_correlatedPnLExplainMap.containsKey (
						eventName
					))
					{
						_correlatedPnLExplainMap.put (
							eventName,
							_correlatedPnLExplainMap.get (
								eventName
							) + correlatedPnL
						);
					}
					else
					{
						_correlatedPnLExplainMap.put (
							eventName,
							correlatedPnL
						);
					}

					if (_correlatedPnLWorstMap.containsKey (
						eventName
					))
					{
						double correlatedWorstPnL = _correlatedPnLWorstMap.get (
							eventName
						);

						_correlatedPnLWorstMap.put (
							eventName,
							correlatedWorstPnL < correlatedPnL ? correlatedWorstPnL : correlatedPnL
						);
					}
					else
					{
						_correlatedPnLWorstMap.put (
							eventName,
							correlatedPnL
						);
					}

					java.lang.String parentSystemicEventName = org.drip.numerical.common.StringUtil.Split (
						eventName,
						"::"
					)[0];

					_systemicGrossPnLExplainMap.put (
						parentSystemicEventName,
						_systemicGrossPnLExplainMap.get (
							parentSystemicEventName
						) + correlatedPnL
					);

					if (null == _correlatedInstanceCountMap)
					{
						_correlatedInstanceCountMap = new
							org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();
					}

					if (_correlatedInstanceCountMap.containsKey (
						eventName
					))
					{
						_correlatedInstanceCountMap.put (
							eventName,
							_correlatedInstanceCountMap.get (
								eventName
							) + 1
						);
					}
					else
					{
						_correlatedInstanceCountMap.put (
							eventName,
							1
						);
					}
				}
			}

			java.util.Map<java.lang.String, java.lang.Double> paaCategoryPnLDecomposition =
				systemicStressEventIncidenceEnsemble.grossPAACategoryPnLDecomposition();

			if (null != paaCategoryPnLDecomposition && 0 != paaCategoryPnLDecomposition.size())
			{
				if (!updatePAACategoryDecompositionMap (
					paaCategoryPnLDecomposition
				))
				{
					return false;
				}
			}
		}

		org.drip.capital.simulation.StressEventIncidenceEnsemble idiosyncraticStressEventIncidenceEnsemble =
			pathPnLRealization.idiosyncratic();

		if (null != idiosyncraticStressEventIncidenceEnsemble)
		{
			for (org.drip.capital.simulation.StressEventIncidence stressEventIncidence :
				idiosyncraticStressEventIncidenceEnsemble.stressEventIncidenceList())
			{
				java.lang.String eventName = stressEventIncidence.name();

				if (null == _idiosyncraticPnLExplainMap)
				{
					_idiosyncraticPnLExplainMap = new
						org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
				}

				if (null == _idiosyncraticPnLWorstMap)
				{
					_idiosyncraticPnLWorstMap = new
						org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
				}

				double idiosyncraticPnL = stressEventIncidence.pnl();

				if (_idiosyncraticPnLExplainMap.containsKey (
					eventName
				))
				{
					_idiosyncraticPnLExplainMap.put (
						eventName,
						_idiosyncraticPnLExplainMap.get (
							eventName
						) + idiosyncraticPnL
					);
				}
				else
				{
					_idiosyncraticPnLExplainMap.put (
						eventName,
						idiosyncraticPnL
					);
				}

				if (_idiosyncraticPnLWorstMap.containsKey (
					eventName
				))
				{
					double idiosyncraticPnLWorst = _idiosyncraticPnLWorstMap.get (
						eventName
					);

					_idiosyncraticPnLWorstMap.put (
						eventName,
						idiosyncraticPnLWorst < idiosyncraticPnL ? idiosyncraticPnLWorst : idiosyncraticPnL
					);
				}
				else
				{
					_idiosyncraticPnLWorstMap.put (
						eventName,
						idiosyncraticPnL
					);
				}

				if (null == _idiosyncraticInstanceCountMap)
				{
					_idiosyncraticInstanceCountMap = new
						org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();
				}

				if (_idiosyncraticInstanceCountMap.containsKey (
					eventName
				))
				{
					_idiosyncraticInstanceCountMap.put (
						eventName,
						_idiosyncraticInstanceCountMap.get (
							eventName
						) + 1
					);
				}
				else
				{
					_idiosyncraticInstanceCountMap.put (
						eventName,
						1
					);
				}
			}
		}

		return true;
	}

	/**
	 * CapitalUnitPnLAttribution Constructor
	 * 
	 * @param pathPnLRealizationList Path PnL Realization List
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CapitalUnitPnLAttribution (
		final java.util.List<org.drip.capital.simulation.PathPnLRealization> pathPnLRealizationList)
		throws java.lang.Exception
	{
		if (null == (_pathPnLRealizationList = pathPnLRealizationList))
		{
			throw new java.lang.Exception (
				"CapitalUnitPnLAttribution Constructor => Invalid Inputs"
			);
		}

		int pathCount = _pathPnLRealizationList.size();

		if (0 == pathCount)
		{
			throw new java.lang.Exception (
				"CapitalUnitPnLAttribution Constructor => Invalid Inputs"
			);
		}

		_expectedShortfall = 0.;

		for (org.drip.capital.simulation.PathPnLRealization pathPnLRealization : _pathPnLRealizationList)
		{
			if (!addPathPnLRealization (
				pathPnLRealization
			))
			{
				throw new java.lang.Exception (
					"CapitalUnitPnLAttribution Constructor => Invalid Inputs"
				);
			}
		}

		NormalizeExplainMap (
			_systemicPnLExplainMap,
			pathCount
		);

		NormalizeExplainMap (
			_systemicGrossPnLExplainMap,
			pathCount
		);

		NormalizeExplainMap (
			_correlatedPnLExplainMap,
			pathCount
		);

		NormalizeExplainMap (
			_idiosyncraticPnLExplainMap,
			pathCount
		);

		NormalizeExplainMap (
			_fsPnLDecompositionExplainMap,
			pathCount
		);

		NormalizeExplainMap (
			_paaCategoryDecompositionExplainMap,
			pathCount
		);

		_expectedShortfall = _expectedShortfall / pathCount;
	}

	/**
	 * Retrieve the Contributing Path PnL Realization List
	 * 
	 * @return The Contributing Path PnL Realization List
	 */

	public java.util.List<org.drip.capital.simulation.PathPnLRealization> pathPnLRealizationList()
	{
		return _pathPnLRealizationList;
	}

	@Override public java.util.List<java.lang.Integer> pathIndexList()
	{
		java.util.List<java.lang.Integer> pathIndexList = new java.util.ArrayList<java.lang.Integer>();

		for (org.drip.capital.simulation.PathPnLRealization pathPnLRealization : _pathPnLRealizationList)
		{
			pathIndexList.add (
				pathPnLRealization.pathIndex()
			);
		}

		return pathIndexList;
	}

	@Override public int pathCount()
	{
		return _pathPnLRealizationList.size();
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
