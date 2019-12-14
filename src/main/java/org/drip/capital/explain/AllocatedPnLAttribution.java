
package org.drip.capital.explain;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>AllocatedPnLAttribution</i> exposes the Path-Level Capital Component Attributions Post Allocation
 * 	Adjustments. The References are:
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

public class AllocatedPnLAttribution
	extends org.drip.capital.explain.PnLAttribution
{
	private org.drip.capital.explain.PnLAttribution _standalonePnLAttribution = null;
	private org.drip.capital.allocation.EntityComponentCapital _entityComponentCapital = null;

	/**
	 * AllocatedPnLAttribution Constructor
	 * 
	 * @param standalonePnLAttribution The Stand-alone PnL Attribution
	 * @param entityComponentCapital The Entity Component Capital
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public AllocatedPnLAttribution (
		final org.drip.capital.explain.PnLAttribution standalonePnLAttribution,
		final org.drip.capital.allocation.EntityComponentCapital entityComponentCapital)
		throws java.lang.Exception
	{
		if (null == (_standalonePnLAttribution = standalonePnLAttribution) ||
			null == (_entityComponentCapital = entityComponentCapital)
		)
		{
			throw new java.lang.Exception (
				"AllocatedPnLAttribution Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Entity Component Capital
	 * 
	 * @return The Entity Component Capital
	 */

	public org.drip.capital.allocation.EntityComponentCapital entityComponentCapital()
	{
		return _entityComponentCapital;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> fsPnLDecompositionExplainMap()
	{
		java.util.Map<java.lang.String, java.lang.Double> standaloneFSPnLDecompositionExplainMap =
			_standalonePnLAttribution.fsPnLDecompositionExplainMap();

		if (null == standaloneFSPnLDecompositionExplainMap ||
			0 == standaloneFSPnLDecompositionExplainMap.size())
		{
			return null;
		}

		double allocationScale = _entityComponentCapital.noStressStandaloneMultiplier();

		java.util.Map<java.lang.String, java.lang.Double> fsPnLDecompositionExplainMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double>
			standaloneFSPnLDecompositionExplainMapEntry : standaloneFSPnLDecompositionExplainMap.entrySet())
		{
			fsPnLDecompositionExplainMap.put (
				standaloneFSPnLDecompositionExplainMapEntry.getKey(),
				standaloneFSPnLDecompositionExplainMapEntry.getValue() * allocationScale
			);
		}

		return fsPnLDecompositionExplainMap;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> paaCategoryDecompositionExplainMap()
	{
		java.util.Map<java.lang.String, java.lang.Double> standalonePAACategoryDecompositionExplainMap =
			_standalonePnLAttribution.paaCategoryDecompositionExplainMap();

		if (null == standalonePAACategoryDecompositionExplainMap ||
			0 == standalonePAACategoryDecompositionExplainMap.size())
		{
			return null;
		}

		double allocationScale = _entityComponentCapital.gsstStandaloneMultiplier();

		java.util.Map<java.lang.String, java.lang.Double> paaCategoryDecompositionExplainMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double>
			standalonePAACategoryDecompositionExplainMapEntry :
				standalonePAACategoryDecompositionExplainMap.entrySet())
		{
			paaCategoryDecompositionExplainMap.put (
				standalonePAACategoryDecompositionExplainMapEntry.getKey(),
				standalonePAACategoryDecompositionExplainMapEntry.getValue() * allocationScale
			);
		}

		return paaCategoryDecompositionExplainMap;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> gsstPnLExplainMap()
	{
		java.util.Map<java.lang.String, java.lang.Double> standaloneGSSTPnLExplainMap =
			_standalonePnLAttribution.gsstPnLExplainMap();

		if (null == standaloneGSSTPnLExplainMap || 0 == standaloneGSSTPnLExplainMap.size())
		{
			return null;
		}

		double allocationScale = _entityComponentCapital.gsstStandaloneMultiplier();

		java.util.Map<java.lang.String, java.lang.Double> gsstPnLExplainMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> standaloneGSSTPnLExplainMapEntry :
			standaloneGSSTPnLExplainMap.entrySet())
		{
			gsstPnLExplainMap.put (
				standaloneGSSTPnLExplainMapEntry.getKey(),
				standaloneGSSTPnLExplainMapEntry.getValue() * allocationScale
			);
		}

		return gsstPnLExplainMap;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> gsstGrossPnLExplainMap()
	{
		return null;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> cBSSTPnLExplainMap()
	{
		java.util.Map<java.lang.String, java.lang.Double> standaloneCBSSTPnLExplainMap =
			_standalonePnLAttribution.cBSSTPnLExplainMap();

		if (null == standaloneCBSSTPnLExplainMap || 0 == standaloneCBSSTPnLExplainMap.size())
		{
			return null;
		}

		double allocationScale = _entityComponentCapital.cBSSTStandaloneMultiplier();

		java.util.Map<java.lang.String, java.lang.Double> cBSSTPnLExplainMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> standaloneCBSSTPnLExplainMapEntry :
			standaloneCBSSTPnLExplainMap.entrySet())
		{
			cBSSTPnLExplainMap.put (
				standaloneCBSSTPnLExplainMapEntry.getKey(),
				standaloneCBSSTPnLExplainMapEntry.getValue() * allocationScale
			);
		}

		return cBSSTPnLExplainMap;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> cBSSTPnLWorstMap()
	{
		java.util.Map<java.lang.String, java.lang.Double> standaloneCBSSTPnLWorstMap =
			_standalonePnLAttribution.cBSSTPnLWorstMap();

		if (null == standaloneCBSSTPnLWorstMap || 0 == standaloneCBSSTPnLWorstMap.size())
		{
			return null;
		}

		double allocationScale = _entityComponentCapital.cBSSTStandaloneMultiplier();

		java.util.Map<java.lang.String, java.lang.Double> cBSSTPnLWorstMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> standaloneCBSSTPnLWorstMapEntry :
			standaloneCBSSTPnLWorstMap.entrySet())
		{
			cBSSTPnLWorstMap.put (
				standaloneCBSSTPnLWorstMapEntry.getKey(),
				standaloneCBSSTPnLWorstMapEntry.getValue() * allocationScale
			);
		}

		return cBSSTPnLWorstMap;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> iBSSTPnLExplainMap()
	{
		java.util.Map<java.lang.String, java.lang.Double> standaloneIBSSTPnLExplainMap =
			_standalonePnLAttribution.iBSSTPnLExplainMap();

		if (null == standaloneIBSSTPnLExplainMap || 0 == standaloneIBSSTPnLExplainMap.size())
		{
			return null;
		}

		double allocationScale = _entityComponentCapital.iBSSTStandaloneMultiplier();

		java.util.Map<java.lang.String, java.lang.Double> iBSSTPnLExplainMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> standaloneIBSSTPnLExplainMapEntry :
			standaloneIBSSTPnLExplainMap.entrySet())
		{
			iBSSTPnLExplainMap.put (
				standaloneIBSSTPnLExplainMapEntry.getKey(),
				standaloneIBSSTPnLExplainMapEntry.getValue() * allocationScale
			);
		}

		return iBSSTPnLExplainMap;
	}

	@Override public java.util.Map<java.lang.String, java.lang.Double> iBSSTPnLWorstMap()
	{
		java.util.Map<java.lang.String, java.lang.Double> standaloneIBSSTPnLWorstMap =
			_standalonePnLAttribution.iBSSTPnLWorstMap();

		if (null == standaloneIBSSTPnLWorstMap || 0 == standaloneIBSSTPnLWorstMap.size())
		{
			return null;
		}

		double allocationScale = _entityComponentCapital.iBSSTStandaloneMultiplier();

		java.util.Map<java.lang.String, java.lang.Double> iBSSTPnLWorstMap = new
			org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> standaloneIBSSTPnLWorstMapEntry :
			standaloneIBSSTPnLWorstMap.entrySet())
		{
			iBSSTPnLWorstMap.put (
				standaloneIBSSTPnLWorstMapEntry.getKey(),
				standaloneIBSSTPnLWorstMapEntry.getValue() * allocationScale
			);
		}

		return iBSSTPnLWorstMap;
	}

	@Override public double var()
	{
		return _entityComponentCapital.noStressStandaloneMultiplier() * super.var();
	}

	@Override public double expectedShortfall()
	{
		return gsstPnL() + cBSSTPnL() + iBSSTGrossPnL() + fsGrossPnL();
	}

	@Override public java.util.Map<java.lang.String, java.lang.Integer> gsstInstanceCountMap()
	{
		return _standalonePnLAttribution.gsstInstanceCountMap();
	}

	@Override public java.util.Map<java.lang.String, java.lang.Integer> cBSSTInstanceCountMap()
	{
		return _standalonePnLAttribution.cBSSTInstanceCountMap();
	}

	@Override public java.util.Map<java.lang.String, java.lang.Integer> iBSSTInstanceCountMap()
	{
		return _standalonePnLAttribution.iBSSTInstanceCountMap();
	}

	@Override public java.util.List<java.lang.Integer> pathIndexList()
	{
		return _standalonePnLAttribution.pathIndexList();
	}

	@Override public int pathCount()
	{
		return _standalonePnLAttribution.pathCount();
	}

	@Override public double gsstPnL()
	{
		return _entityComponentCapital.gsstStandaloneMultiplier() * _standalonePnLAttribution.gsstPnL();
	}

	@Override public double gsstGrossPnL()
	{
		return _entityComponentCapital.gsstStandaloneMultiplier() * _standalonePnLAttribution.gsstGrossPnL();
	}

	@Override public double cBSSTPnL()
	{
		return _entityComponentCapital.cBSSTStandaloneMultiplier() * _standalonePnLAttribution.cBSSTPnL();
	}

	@Override public double iBSSTGrossPnL()
	{
		return _entityComponentCapital.iBSSTStandaloneMultiplier() *
			_standalonePnLAttribution.iBSSTGrossPnL();
	}

	@Override public double fsGrossPnL()
	{
		return _entityComponentCapital.noStressStandaloneMultiplier() *
			_standalonePnLAttribution.fsGrossPnL();
	}
}
