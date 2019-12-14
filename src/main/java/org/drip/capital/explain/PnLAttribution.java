
package org.drip.capital.explain;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>PnLAttribution</i> exposes the Path-Level Capital Component Attributions. The References are:
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

public abstract class PnLAttribution
{
	protected double _var = java.lang.Double.NaN;
	protected double _expectedShortfall = java.lang.Double.NaN;
	protected java.util.Map<java.lang.String, java.lang.Double> _cBSSTPnLWorstMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _iBSSTPnLWorstMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _gsstPnLExplainMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _cBSSTPnLExplainMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _iBSSTPnLExplainMap = null;
	protected java.util.Map<java.lang.String, java.lang.Integer> _gsstInstanceCountMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _gsstGrossPnLExplainMap = null;
	protected java.util.Map<java.lang.String, java.lang.Integer> _cBSSTInstanceCountMap = null;
	protected java.util.Map<java.lang.String, java.lang.Integer> _iBSSTInstanceCountMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _fsPnLDecompositionExplainMap = null;
	protected java.util.Map<java.lang.String, java.lang.Double> _paaCategoryDecompositionExplainMap = null;

	/**
	 * Retrieve the FS PnL Decomposition Explain Map
	 * 
	 * @return The FS PnL Decomposition Explain Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> fsPnLDecompositionExplainMap()
	{
		return _fsPnLDecompositionExplainMap;
	}

	/**
	 * Retrieve the PAA Category Decomposition Explain Map
	 * 
	 * @return The PAA Category Decomposition Explain Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> paaCategoryDecompositionExplainMap()
	{
		return _paaCategoryDecompositionExplainMap;
	}

	/**
	 * Retrieve the GSST PnL Explain Map
	 * 
	 * @return The GSST PnL Explain Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> gsstPnLExplainMap()
	{
		return _gsstPnLExplainMap;
	}

	/**
	 * Retrieve the GSST Gross PnL Explain Map
	 * 
	 * @return The GSST Gross PnL Explain Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> gsstGrossPnLExplainMap()
	{
		return _gsstGrossPnLExplainMap;
	}

	/**
	 * Retrieve the cBSST PnL Explain Map
	 * 
	 * @return The cBSST PnL Explain Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> cBSSTPnLExplainMap()
	{
		return _cBSSTPnLExplainMap;
	}

	/**
	 * Retrieve the cBSST Worst PnL Map
	 * 
	 * @return The cBSST Worst PnL Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> cBSSTPnLWorstMap()
	{
		return _cBSSTPnLWorstMap;
	}

	/**
	 * Retrieve the iBSST PnL Explain Map
	 * 
	 * @return The iBSST PnL Explain Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> iBSSTPnLExplainMap()
	{
		return _iBSSTPnLExplainMap;
	}

	/**
	 * Retrieve the iBSST Worst PnL Map
	 * 
	 * @return The iBSST Worst PnL Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> iBSSTPnLWorstMap()
	{
		return _iBSSTPnLWorstMap;
	}

	/**
	 * Retrieve the VaR
	 * 
	 * @return VaR
	 */

	public double var()
	{
		return _var;
	}

	/**
	 * Retrieve the Expected Short-fall
	 * 
	 * @return Expected Short-fall
	 */

	public double expectedShortfall()
	{
		return _expectedShortfall;
	}

	/**
	 * Retrieve the GSST Instance Count Map
	 * 
	 * @return The GSST Instance Count Map
	 */

	public java.util.Map<java.lang.String, java.lang.Integer> gsstInstanceCountMap()
	{
		return _gsstInstanceCountMap;
	}

	/**
	 * Retrieve the cBSST Instance Count Map
	 * 
	 * @return The cBSST Instance Count Map
	 */

	public java.util.Map<java.lang.String, java.lang.Integer> cBSSTInstanceCountMap()
	{
		return _cBSSTInstanceCountMap;
	}

	/**
	 * Retrieve the iBSST Instance Count Map
	 * 
	 * @return The iBSST Instance Count Map
	 */

	public java.util.Map<java.lang.String, java.lang.Integer> iBSSTInstanceCountMap()
	{
		return _iBSSTInstanceCountMap;
	}

	/**
	 * Generate the Contributing Path Index List
	 * 
	 * @return Contributing Path Index List
	 */

	public abstract java.util.List<java.lang.Integer> pathIndexList();

	/**
	 * Retrieve the Number of Contributing Paths
	 * 
	 * @return The Number of Contributing Paths
	 */

	public abstract int pathCount();

	/**
	 * Retrieve the GSST PnL
	 * 
	 * @return The GSST PnL
	 */

	public double gsstPnL()
	{
		if (null == _gsstPnLExplainMap || 0 == _gsstPnLExplainMap.size())
		{
			return 0.;
		}

		double gsstPnL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> gsstPnLExplainEntry :
			_gsstPnLExplainMap.entrySet())
		{
			gsstPnL = gsstPnL + gsstPnLExplainEntry.getValue();
		}

		return gsstPnL;
	}

	/**
	 * Retrieve the GSST Gross PnL
	 * 
	 * @return The GSST Gross PnL
	 */

	public double gsstGrossPnL()
	{
		if (null == _gsstGrossPnLExplainMap || 0 == _gsstGrossPnLExplainMap.size())
		{
			return 0.;
		}

		double gsstGrossPnL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> gsstGrossPnLExplainEntry :
			_gsstGrossPnLExplainMap.entrySet())
		{
			gsstGrossPnL = gsstGrossPnL + gsstGrossPnLExplainEntry.getValue();
		}

		return gsstGrossPnL;
	}

	/**
	 * Retrieve the cBSST PnL
	 * 
	 * @return The cBSST PnL
	 */

	public double cBSSTPnL()
	{
		if (null == _cBSSTPnLExplainMap || 0 == _cBSSTPnLExplainMap.size())
		{
			return 0.;
		}

		double cBSSTPnL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> cBSSTPnLExplainEntry :
			_cBSSTPnLExplainMap.entrySet())
		{
			cBSSTPnL = cBSSTPnL + cBSSTPnLExplainEntry.getValue();
		}

		return cBSSTPnL;
	}

	/**
	 * Retrieve the iBSST Gross PnL
	 * 
	 * @return The iBSST Gross PnL
	 */

	public double iBSSTGrossPnL()
	{
		if (null == _iBSSTPnLExplainMap || 0 == _iBSSTPnLExplainMap.size())
		{
			return 0.;
		}

		double iBSSTGrossPnL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> iBSSTPnLExplainEntry :
			_iBSSTPnLExplainMap.entrySet())
		{
			iBSSTGrossPnL = iBSSTGrossPnL + iBSSTPnLExplainEntry.getValue();
		}

		return iBSSTGrossPnL;
	}

	/**
	 * Retrieve the Gross VaR FS PnL
	 * 
	 * @return The Gross VaR FS PnL
	 */

	public double fsGrossPnL()
	{
		if (null == _fsPnLDecompositionExplainMap || 0 == _fsPnLDecompositionExplainMap.size())
		{
			return 0.;
		}

		double fsGrossPnL = 0.;

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> fsPnLDecompositionExplainEntry :
			_fsPnLDecompositionExplainMap.entrySet())
		{
			fsGrossPnL = fsGrossPnL + fsPnLDecompositionExplainEntry.getValue();
		}

		return fsGrossPnL;
	}
}
