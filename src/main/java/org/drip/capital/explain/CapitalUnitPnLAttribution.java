
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

			if (_fsPnLDecompositionExplainMap.containsKey (fsType))
			{
				_fsPnLDecompositionExplainMap.put (
					fsType,
					_fsPnLDecompositionExplainMap.get (fsType) + fsPnLEntry.getValue()
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

			if (_paaCategoryDecompositionExplainMap.containsKey (paaCategoryName))
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
			if (!updateFSDecompositionMap (fsPnLDecompositionMap))
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

				if (org.drip.capital.definition.StressScenarioType.GSST.equalsIgnoreCase (eventType))
				{
					if (null == _gsstPnLExplainMap)
					{
						_gsstPnLExplainMap = new
							org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

						_gsstGrossPnLExplainMap = new
							org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
					}

					double gsstPnL = stressEventIncidence.pnl();

					if (_gsstPnLExplainMap.containsKey (eventName))
					{
						_gsstPnLExplainMap.put (
							eventName,
							_gsstPnLExplainMap.get (eventName) + gsstPnL
						);

						_gsstGrossPnLExplainMap.put (
							eventName,
							_gsstGrossPnLExplainMap.get (eventName) + gsstPnL
						);
					}
					else
					{
						_gsstPnLExplainMap.put (
							eventName,
							gsstPnL
						);

						_gsstGrossPnLExplainMap.put (
							eventName,
							gsstPnL
						);
					}

					if (null == _gsstInstanceCountMap)
					{
						_gsstInstanceCountMap = new
							org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();
					}

					if (_gsstInstanceCountMap.containsKey (eventName))
					{
						_gsstInstanceCountMap.put (
							eventName,
							_gsstInstanceCountMap.get (eventName) + 1
						);
					}
					else
					{
						_gsstInstanceCountMap.put (
							eventName,
							1
						);
					}
				}
				else if (org.drip.capital.definition.StressScenarioType.CBSST.equalsIgnoreCase (eventType))
				{
					if (null == _cBSSTPnLExplainMap)
					{
						_cBSSTPnLExplainMap = new
							org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
					}

					if (null == _cBSSTPnLWorstMap)
					{
						_cBSSTPnLWorstMap = new
							org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
					}

					double cBSSTPnL = stressEventIncidence.pnl();

					if (_cBSSTPnLExplainMap.containsKey (eventName))
					{
						_cBSSTPnLExplainMap.put (
							eventName,
							_cBSSTPnLExplainMap.get (eventName) + cBSSTPnL
						);
					}
					else
					{
						_cBSSTPnLExplainMap.put (
							eventName,
							cBSSTPnL
						);
					}

					if (_cBSSTPnLWorstMap.containsKey (eventName))
					{
						double cBSSTWorstPnL = _cBSSTPnLWorstMap.get (eventName);

						_cBSSTPnLWorstMap.put (
							eventName,
							cBSSTWorstPnL < cBSSTPnL ? cBSSTWorstPnL : cBSSTPnL
						);
					}
					else
					{
						_cBSSTPnLWorstMap.put (
							eventName,
							cBSSTPnL
						);
					}

					java.lang.String parentGSSTEventName = org.drip.numerical.common.StringUtil.Split (
						eventName,
						"::"
					)[0];

					_gsstGrossPnLExplainMap.put (
						parentGSSTEventName,
						_gsstGrossPnLExplainMap.get (parentGSSTEventName) + cBSSTPnL
					);

					if (null == _cBSSTInstanceCountMap)
					{
						_cBSSTInstanceCountMap = new
							org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();
					}

					if (_cBSSTInstanceCountMap.containsKey (eventName))
					{
						_cBSSTInstanceCountMap.put (
							eventName,
							_cBSSTInstanceCountMap.get (eventName) + 1
						);
					}
					else
					{
						_cBSSTInstanceCountMap.put (
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
				if (!updatePAACategoryDecompositionMap (paaCategoryPnLDecomposition))
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

				if (null == _iBSSTPnLExplainMap)
				{
					_iBSSTPnLExplainMap = new
						org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
				}

				if (null == _iBSSTPnLWorstMap)
				{
					_iBSSTPnLWorstMap = new
						org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
				}

				double iBSSTPnL = stressEventIncidence.pnl();

				if (_iBSSTPnLExplainMap.containsKey (eventName))
				{
					_iBSSTPnLExplainMap.put (
						eventName,
						_iBSSTPnLExplainMap.get (eventName) + iBSSTPnL
					);
				}
				else
				{
					_iBSSTPnLExplainMap.put (
						eventName,
						iBSSTPnL
					);
				}

				if (_iBSSTPnLWorstMap.containsKey (eventName))
				{
					double iBSSTPnLWorst = _iBSSTPnLWorstMap.get (eventName);

					_iBSSTPnLWorstMap.put (
						eventName,
						iBSSTPnLWorst < iBSSTPnL ? iBSSTPnLWorst : iBSSTPnL
					);
				}
				else
				{
					_iBSSTPnLWorstMap.put (
						eventName,
						iBSSTPnL
					);
				}

				if (null == _iBSSTInstanceCountMap)
				{
					_iBSSTInstanceCountMap = new
						org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();
				}

				if (_iBSSTInstanceCountMap.containsKey (eventName))
				{
					_iBSSTInstanceCountMap.put (
						eventName,
						_iBSSTInstanceCountMap.get (eventName) + 1
					);
				}
				else
				{
					_iBSSTInstanceCountMap.put (
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
			throw new java.lang.Exception ("CapitalUnitPnLAttribution Constructor => Invalid Inputs");
		}

		int pathCount = _pathPnLRealizationList.size();

		if (0 == pathCount)
		{
			throw new java.lang.Exception ("CapitalUnitPnLAttribution Constructor => Invalid Inputs");
		}

		_expectedShortfall = 0.;

		for (org.drip.capital.simulation.PathPnLRealization pathPnLRealization : _pathPnLRealizationList)
		{
			if (!addPathPnLRealization (pathPnLRealization))
			{
				throw new java.lang.Exception ("CapitalUnitPnLAttribution Constructor => Invalid Inputs");
			}
		}

		NormalizeExplainMap (
			_gsstPnLExplainMap,
			pathCount
		);

		NormalizeExplainMap (
			_gsstGrossPnLExplainMap,
			pathCount
		);

		NormalizeExplainMap (
			_cBSSTPnLExplainMap,
			pathCount
		);

		NormalizeExplainMap (
			_iBSSTPnLExplainMap,
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
			pathIndexList.add (pathPnLRealization.pathIndex());
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
			grossSystemicStressPnLList.add (pathPnLRealization.grossSystemicStressPnL());
		}

		return grossSystemicStressPnLList;
	}

	@Override public java.util.List<java.lang.Double> grossIdiosyncraticStressPnLList()
	{
		java.util.List<java.lang.Double> grossIdiosyncraticStressPnLList =
			new java.util.ArrayList<java.lang.Double>();

		for (org.drip.capital.simulation.PathPnLRealization pathPnLRealization : _pathPnLRealizationList)
		{
			grossIdiosyncraticStressPnLList.add (pathPnLRealization.grossIdiosyncraticStressPnL());
		}

		return grossIdiosyncraticStressPnLList;
	}

	@Override public java.util.List<java.lang.Double> grossFSPnLList()
	{
		java.util.List<java.lang.Double> grossFSPnLList = new java.util.ArrayList<java.lang.Double>();

		for (org.drip.capital.simulation.PathPnLRealization pathPnLRealization : _pathPnLRealizationList)
		{
			grossFSPnLList.add (pathPnLRealization.grossFSPnL());
		}

		return grossFSPnLList;
	}

	@Override public java.util.List<java.lang.Double> grossPnLList()
	{
		java.util.List<java.lang.Double> grossPnLList = new java.util.ArrayList<java.lang.Double>();

		for (org.drip.capital.simulation.PathPnLRealization pathPnLRealization : _pathPnLRealizationList)
		{
			grossPnLList.add (pathPnLRealization.grossPnL());
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
