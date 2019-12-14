
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

			if (_fsPnLDecompositionExplainMap.containsKey (fsType))
			{
				_fsPnLDecompositionExplainMap.put (
					fsType,
					_fsPnLDecompositionExplainMap.get (fsType) + fsPnLDecompositionExplainEntry.getValue()
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

	private boolean updateGSSTPnLExplainMap (
		final java.util.Map<java.lang.String, java.lang.Double> unitGSSTPnLExplainMap)
	{
		if (null == unitGSSTPnLExplainMap)
		{
			return true;
		}

		if (null == _gsstPnLExplainMap)
		{
			_gsstPnLExplainMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> gsstExplainEntry :
			unitGSSTPnLExplainMap.entrySet())
		{
			java.lang.String gsstEventName = gsstExplainEntry.getKey();

			if (_gsstPnLExplainMap.containsKey (gsstEventName))
			{
				_gsstPnLExplainMap.put (
					gsstEventName,
					_gsstPnLExplainMap.get (gsstEventName) + gsstExplainEntry.getValue()
				);
			}
			else
			{
				_gsstPnLExplainMap.put (
					gsstEventName,
					gsstExplainEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean updateGSSTGrossPnLExplainMap (
		final java.util.Map<java.lang.String, java.lang.Double> unitGSSTGrossPnLExplainMap)
	{
		if (null == unitGSSTGrossPnLExplainMap)
		{
			return true;
		}

		if (null == _gsstGrossPnLExplainMap)
		{
			_gsstGrossPnLExplainMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> gsstGrossExplainEntry :
			unitGSSTGrossPnLExplainMap.entrySet())
		{
			java.lang.String gsstEventName = gsstGrossExplainEntry.getKey();

			if (_gsstGrossPnLExplainMap.containsKey (gsstEventName))
			{
				_gsstGrossPnLExplainMap.put (
					gsstEventName,
					_gsstGrossPnLExplainMap.get (gsstEventName) + gsstGrossExplainEntry.getValue()
				);
			}
			else
			{
				_gsstGrossPnLExplainMap.put (
					gsstEventName,
					gsstGrossExplainEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean updateGSSTInstanceCountMap (
		final java.util.Map<java.lang.String, java.lang.Integer> unitGSSTInstanceCountMap)
	{
		if (null == unitGSSTInstanceCountMap)
		{
			return true;
		}

		if (null == _gsstInstanceCountMap)
		{
			_gsstInstanceCountMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Integer> gsstInstanceCountEntry :
			unitGSSTInstanceCountMap.entrySet())
		{
			java.lang.String gsstEventName = gsstInstanceCountEntry.getKey();

			if (_gsstInstanceCountMap.containsKey (gsstEventName))
			{
				_gsstInstanceCountMap.put (
					gsstEventName,
					_gsstInstanceCountMap.get (gsstEventName) + gsstInstanceCountEntry.getValue()
				);
			}
			else
			{
				_gsstInstanceCountMap.put (
					gsstEventName,
					gsstInstanceCountEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean updateCBSSTPnLExplainMap (
		final java.util.Map<java.lang.String, java.lang.Double> unitCBSSTPnLExplainMap)
	{
		if (null == unitCBSSTPnLExplainMap)
		{
			return true;
		}

		if (null == _cBSSTPnLExplainMap)
		{
			_cBSSTPnLExplainMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> cBSSTExplainEntry :
			unitCBSSTPnLExplainMap.entrySet())
		{
			java.lang.String cBSSTEventName = cBSSTExplainEntry.getKey();

			if (_cBSSTPnLExplainMap.containsKey (cBSSTEventName))
			{
				_cBSSTPnLExplainMap.put (
					cBSSTEventName,
					_cBSSTPnLExplainMap.get (cBSSTEventName) + cBSSTExplainEntry.getValue()
				);
			}
			else
			{
				_cBSSTPnLExplainMap.put (
					cBSSTEventName,
					cBSSTExplainEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean updateCBSSTInstanceCountMap (
		final java.util.Map<java.lang.String, java.lang.Integer> unitCBSSTInstanceCountMap)
	{
		if (null == unitCBSSTInstanceCountMap)
		{
			return true;
		}

		if (null == _cBSSTInstanceCountMap)
		{
			_cBSSTInstanceCountMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Integer> cBSSTInstanceCountEntry :
			unitCBSSTInstanceCountMap.entrySet())
		{
			java.lang.String cBSSTEventName = cBSSTInstanceCountEntry.getKey();

			if (_cBSSTInstanceCountMap.containsKey (cBSSTEventName))
			{
				_cBSSTInstanceCountMap.put (
					cBSSTEventName,
					_cBSSTInstanceCountMap.get (cBSSTEventName) + cBSSTInstanceCountEntry.getValue()
				);
			}
			else
			{
				_cBSSTInstanceCountMap.put (
					cBSSTEventName,
					cBSSTInstanceCountEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean updateIBSSTPnLExplainMap (
		final java.util.Map<java.lang.String, java.lang.Double> unitIBSSTPnLExplainMap)
	{
		if (null == unitIBSSTPnLExplainMap)
		{
			return true;
		}

		if (null == _iBSSTPnLExplainMap)
		{
			_iBSSTPnLExplainMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> iBSSTExplainEntry :
			unitIBSSTPnLExplainMap.entrySet())
		{
			java.lang.String iBSSTEventName = iBSSTExplainEntry.getKey();

			if (_iBSSTPnLExplainMap.containsKey (iBSSTEventName))
			{
				_iBSSTPnLExplainMap.put (
					iBSSTEventName,
					_iBSSTPnLExplainMap.get (iBSSTEventName) + iBSSTExplainEntry.getValue()
				);
			}
			else
			{
				_iBSSTPnLExplainMap.put (
					iBSSTEventName,
					iBSSTExplainEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean updateIBSSTInstanceCountMap (
		final java.util.Map<java.lang.String, java.lang.Integer> unitIBSSTInstanceCountMap)
	{
		if (null == unitIBSSTInstanceCountMap)
		{
			return true;
		}

		if (null == _iBSSTInstanceCountMap)
		{
			_iBSSTInstanceCountMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Integer>();
		}

		for (java.util.Map.Entry<java.lang.String, java.lang.Integer> iBSSTInstanceCountEntry :
			unitIBSSTInstanceCountMap.entrySet())
		{
			java.lang.String iBSSTEventName = iBSSTInstanceCountEntry.getKey();

			if (_iBSSTInstanceCountMap.containsKey (iBSSTEventName))
			{
				_iBSSTInstanceCountMap.put (
					iBSSTEventName,
					_iBSSTInstanceCountMap.get (iBSSTEventName) + iBSSTInstanceCountEntry.getValue()
				);
			}
			else
			{
				_iBSSTInstanceCountMap.put (
					iBSSTEventName,
					iBSSTInstanceCountEntry.getValue()
				);
			}
		}

		return true;
	}

	private boolean accumulateUnitAttribution (
		final org.drip.capital.explain.PnLAttribution pnlAttribution)
	{
		if (!updateFSDecompositionExplainMap (pnlAttribution.fsPnLDecompositionExplainMap()))
		{
			return false;
		}

		if (!updateGSSTPnLExplainMap (pnlAttribution.gsstPnLExplainMap()))
		{
			return false;
		}

		if (!updateGSSTGrossPnLExplainMap (pnlAttribution.gsstGrossPnLExplainMap()))
		{
			return false;
		}

		if (!updateGSSTInstanceCountMap (pnlAttribution.gsstInstanceCountMap()))
		{
			return false;
		}

		if (!updateCBSSTPnLExplainMap (pnlAttribution.cBSSTPnLExplainMap()))
		{
			return false;
		}

		if (!updateCBSSTInstanceCountMap (pnlAttribution.cBSSTInstanceCountMap()))
		{
			return false;
		}

		if (!updateIBSSTPnLExplainMap (pnlAttribution.iBSSTPnLExplainMap()))
		{
			return false;
		}

		if (!updateIBSSTInstanceCountMap (pnlAttribution.iBSSTInstanceCountMap()))
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
			throw new java.lang.Exception ("CapitalSegmentPnLAttribution Constructor => Invalid Inputs");
		}

		int capitalUnitCount = pnlAttributionArray.length;

		if (0 >= capitalUnitCount)
		{
			throw new java.lang.Exception ("CapitalSegmentPnLAttribution Constructor => Invalid Inputs");
		}

		_var = 0.;
		_expectedShortfall = 0.;

		_pathCount = pnlAttributionArray[0].pathCount();

		for (int capitalUnitIndex = 0; capitalUnitIndex < capitalUnitCount; ++capitalUnitIndex)
		{
			if (null == pnlAttributionArray[capitalUnitIndex])
			{
				throw new java.lang.Exception ("CapitalSegmentPnLAttribution Constructor => Invalid Inputs");
			}

			_var = _var + pnlAttributionArray[capitalUnitIndex].var();

			_expectedShortfall = _expectedShortfall +
				pnlAttributionArray[capitalUnitIndex].expectedShortfall();

			if (!accumulateUnitAttribution (pnlAttributionArray[capitalUnitIndex]))
			{
				throw new java.lang.Exception ("CapitalSegmentPnLAttribution Constructor => Invalid Inputs");
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
