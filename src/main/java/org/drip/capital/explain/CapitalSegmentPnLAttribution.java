
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
 * <i>CapitalSegmentPnLAttribution</i> holds the Scenario-Level Cumulative Capital Attributions from the
 * 	Contributing Paths of the Stand-alone Capital Units corresponding to a Capital Segment. The References
 * 	are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bank for International Supervision (2005): Stress Testing at Major Financial Institutions: Survey
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

public class CapitalSegmentPnLAttribution
	extends org.drip.capital.explain.PnLAttribution
{
	private int _pathCount = -1;
	private java.util.List<java.lang.Integer> _pathIndexList = null;

	private boolean updateFSDecompositionExplainMap (
		final java.util.Map<java.lang.String, java.lang.Double> unitFSPnLDecompositionExplainMap)
	{
		if (null == unitFSPnLDecompositionExplainMap)
		{
			return true;
		}

		if (null == _fsPnLDecompositionExplainMap)
		{
			_fsPnLDecompositionExplainMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> fsPnLDecompositionExplainEntry :
			unitFSPnLDecompositionExplainMap.entrySet())
		{
			java.lang.String fsType = fsPnLDecompositionExplainEntry.getKey();

			if (_fsPnLDecompositionExplainMap.containsKey (
				fsType
			))
			{
				_fsPnLDecompositionExplainMap.put (
					fsType,
					_fsPnLDecompositionExplainMap.get (
						fsType
					) + fsPnLDecompositionExplainEntry.getValue()
				);
			}
			else
			{
				_fsPnLDecompositionExplainMap.put (
					fsType,
					fsPnLDecompositionExplainEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean updateSystemicEventNamePnLExplainMap (
		final java.util.Map<java.lang.String, java.lang.Double> unitSystemicPnLExplainMap)
	{
		if (null == unitSystemicPnLExplainMap)
		{
			return true;
		}

		if (null == _systemicPnLExplainMap)
		{
			_systemicPnLExplainMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> systemicExplainEntry :
			unitSystemicPnLExplainMap.entrySet())
		{
			java.lang.String systemicEventName = systemicExplainEntry.getKey();

			if (_systemicPnLExplainMap.containsKey (
				systemicEventName
			))
			{
				_systemicPnLExplainMap.put (
					systemicEventName,
					_systemicPnLExplainMap.get (
						systemicEventName
					) + systemicExplainEntry.getValue()
				);
			}
			else
			{
				_systemicPnLExplainMap.put (
					systemicEventName,
					systemicExplainEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean updateSystemicGrossPnLExplainMap (
		final java.util.Map<java.lang.String, java.lang.Double> unitSystemicGrossPnLExplainMap)
	{
		if (null == unitSystemicGrossPnLExplainMap)
		{
			return true;
		}

		if (null == _systemicGrossPnLExplainMap)
		{
			_systemicGrossPnLExplainMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> systemicGrossExplainEntry :
			unitSystemicGrossPnLExplainMap.entrySet())
		{
			java.lang.String systemicEventName = systemicGrossExplainEntry.getKey();

			if (_systemicGrossPnLExplainMap.containsKey (
				systemicEventName
			))
			{
				_systemicGrossPnLExplainMap.put (
					systemicEventName,
					_systemicGrossPnLExplainMap.get (
						systemicEventName
					) + systemicGrossExplainEntry.getValue()
				);
			}
			else
			{
				_systemicGrossPnLExplainMap.put (
					systemicEventName,
					systemicGrossExplainEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean updateSystemicInstanceCountMap (
		final java.util.Map<java.lang.String, java.lang.Integer> unitSystemicInstanceCountMap)
	{
		if (null == unitSystemicInstanceCountMap)
		{
			return true;
		}

		if (null == _systemicInstanceCountMap)
		{
			_systemicInstanceCountMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Integer> systemicInstanceCountEntry :
			unitSystemicInstanceCountMap.entrySet())
		{
			java.lang.String systemicEventName = systemicInstanceCountEntry.getKey();

			if (_systemicInstanceCountMap.containsKey (
				systemicEventName
			))
			{
				_systemicInstanceCountMap.put (
					systemicEventName,
					_systemicInstanceCountMap.get (
						systemicEventName
					) + systemicInstanceCountEntry.getValue()
				);
			}
			else
			{
				_systemicInstanceCountMap.put (
					systemicEventName,
					systemicInstanceCountEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean updateCorrelatedPnLExplainMap (
		final java.util.Map<java.lang.String, java.lang.Double> unitCorrelatedPnLExplainMap)
	{
		if (null == unitCorrelatedPnLExplainMap)
		{
			return true;
		}

		if (null == _correlatedPnLExplainMap)
		{
			_correlatedPnLExplainMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> correlatedExplainEntry :
			unitCorrelatedPnLExplainMap.entrySet())
		{
			java.lang.String correlatedEventName = correlatedExplainEntry.getKey();

			if (_correlatedPnLExplainMap.containsKey (
				correlatedEventName
			))
			{
				_correlatedPnLExplainMap.put (
					correlatedEventName,
					_correlatedPnLExplainMap.get (
						correlatedEventName
					) + correlatedExplainEntry.getValue()
				);
			}
			else
			{
				_correlatedPnLExplainMap.put (
					correlatedEventName,
					correlatedExplainEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean updateCorrelatedInstanceCountMap (
		final java.util.Map<java.lang.String, java.lang.Integer> unitCorrelatedInstanceCountMap)
	{
		if (null == unitCorrelatedInstanceCountMap)
		{
			return true;
		}

		if (null == _correlatedInstanceCountMap)
		{
			_correlatedInstanceCountMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Integer> correlatedInstanceCountEntry :
			unitCorrelatedInstanceCountMap.entrySet())
		{
			java.lang.String correlatedEventName = correlatedInstanceCountEntry.getKey();

			if (_correlatedInstanceCountMap.containsKey (
				correlatedEventName
			))
			{
				_correlatedInstanceCountMap.put (
					correlatedEventName,
					_correlatedInstanceCountMap.get (
						correlatedEventName
					) + correlatedInstanceCountEntry.getValue()
				);
			}
			else
			{
				_correlatedInstanceCountMap.put (
					correlatedEventName,
					correlatedInstanceCountEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean updateIdiosyncraticPnLExplainMap (
		final java.util.Map<java.lang.String, java.lang.Double> unitIdiosyncraticPnLExplainMap)
	{
		if (null == unitIdiosyncraticPnLExplainMap)
		{
			return true;
		}

		if (null == _idiosyncraticPnLExplainMap)
		{
			_idiosyncraticPnLExplainMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> idiosyncraticExplainEntry :
			unitIdiosyncraticPnLExplainMap.entrySet())
		{
			java.lang.String idiosyncraticEventName = idiosyncraticExplainEntry.getKey();

			if (_idiosyncraticPnLExplainMap.containsKey (
				idiosyncraticEventName
			))
			{
				_idiosyncraticPnLExplainMap.put (
					idiosyncraticEventName,
					_idiosyncraticPnLExplainMap.get (
						idiosyncraticEventName
					) + idiosyncraticExplainEntry.getValue()
				);
			}
			else
			{
				_idiosyncraticPnLExplainMap.put (
					idiosyncraticEventName,
					idiosyncraticExplainEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean updateIdiosyncraticInstanceCountMap (
		final java.util.Map<java.lang.String, java.lang.Integer> unitIdiosyncraticInstanceCountMap)
	{
		if (null == unitIdiosyncraticInstanceCountMap)
		{
			return true;
		}

		if (null == _idiosyncraticInstanceCountMap)
		{
			_idiosyncraticInstanceCountMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Integer> idiosyncraticInstanceCountEntry :
			unitIdiosyncraticInstanceCountMap.entrySet())
		{
			java.lang.String idiosyncraticEventName = idiosyncraticInstanceCountEntry.getKey();

			if (_idiosyncraticInstanceCountMap.containsKey (
				idiosyncraticEventName
			))
			{
				_idiosyncraticInstanceCountMap.put (
					idiosyncraticEventName,
					_idiosyncraticInstanceCountMap.get (
						idiosyncraticEventName
					) + idiosyncraticInstanceCountEntry.getValue()
				);
			}
			else
			{
				_idiosyncraticInstanceCountMap.put (
					idiosyncraticEventName,
					idiosyncraticInstanceCountEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean accumulateUnitAttribution (
		final org.drip.capital.explain.PnLAttribution pnlAttribution)
	{
		if (!updateFSDecompositionExplainMap (
			pnlAttribution.fsPnLDecompositionExplainMap()
		))
		{
			return false;
		}

		if (!updateSystemicEventNamePnLExplainMap (
			pnlAttribution.systemicPnLExplainMap()
		))
		{
			return false;
		}

		if (!updateSystemicGrossPnLExplainMap (
			pnlAttribution.systemicGrossPnLExplainMap()
		))
		{
			return false;
		}

		if (!updateSystemicInstanceCountMap (
			pnlAttribution.systemicInstanceCountMap()
		))
		{
			return false;
		}

		if (!updateCorrelatedPnLExplainMap (
			pnlAttribution.correlatedPnLExplainMap()
		))
		{
			return false;
		}

		if (!updateCorrelatedInstanceCountMap (
			pnlAttribution.correlatedInstanceCountMap()
		))
		{
			return false;
		}

		if (!updateIdiosyncraticPnLExplainMap (
			pnlAttribution.idiosyncraticPnLExplainMap()
		))
		{
			return false;
		}

		if (!updateIdiosyncraticInstanceCountMap (
			pnlAttribution.idiosyncraticInstanceCountMap()
		))
		{
			return false;
		}

		return true;
	}

	/**
	 * CapitalSegmentPnLAttribution Constructor
	 * 
	 * @param pnlAttributionArray Array of PnL Attributions
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CapitalSegmentPnLAttribution (
		final org.drip.capital.explain.PnLAttribution[] pnlAttributionArray)
		throws java.lang.Exception
	{
		if (null == pnlAttributionArray)
		{
			throw new java.lang.Exception (
				"CapitalSegmentPnLAttribution Constructor => Invalid Inputs"
			);
		}

		int capitalUnitCount = pnlAttributionArray.length;

		if (0 >= capitalUnitCount)
		{
			throw new java.lang.Exception (
				"CapitalSegmentPnLAttribution Constructor => Invalid Inputs"
			);
		}

		_var = 0.;
		_expectedShortfall = 0.;

		_pathCount = pnlAttributionArray[0].pathCount();

		for (int capitalUnitIndex = 0; capitalUnitIndex < capitalUnitCount; ++capitalUnitIndex)
		{
			if (null == pnlAttributionArray[capitalUnitIndex])
			{
				throw new java.lang.Exception (
					"CapitalSegmentPnLAttribution Constructor => Invalid Inputs"
				);
			}

			_var = _var + pnlAttributionArray[capitalUnitIndex].var();

			_expectedShortfall = _expectedShortfall +
				pnlAttributionArray[capitalUnitIndex].expectedShortfall();

			if (!accumulateUnitAttribution (
				pnlAttributionArray[capitalUnitIndex]
			))
			{
				throw new java.lang.Exception (
					"CapitalSegmentPnLAttribution Constructor => Invalid Inputs"
				);
			}
		}

		_pathIndexList = pnlAttributionArray[0].pathIndexList();
	}

	@Override public java.util.List<java.lang.Integer> pathIndexList()
	{
		return _pathIndexList;
	}

	@Override public int pathCount()
	{
		return _pathCount;
	}
}
