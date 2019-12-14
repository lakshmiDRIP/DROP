
package org.drip.capital.explain;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
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
