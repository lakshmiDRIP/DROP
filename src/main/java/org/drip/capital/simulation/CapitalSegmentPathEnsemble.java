
package org.drip.capital.simulation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalSegmentPathEnsemble</i> generates the Ensemble of Capital Paths from the Simulation PnL
 * Realizations for the Capital Units under the specified Capital Segments. The References are:
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

public class CapitalSegmentPathEnsemble
	extends org.drip.capital.simulation.CapitalUnitPathEnsemble
{
	private java.util.Map<java.lang.String, org.drip.capital.simulation.PathEnsemble> _pathEnsembleMap =
		null;

	/**
	 * CapitalSegmentPathEnsemble Constructor
	 * 
	 * @param pathEnsembleMap Map of Path Ensemble
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CapitalSegmentPathEnsemble (
		final java.util.Map<java.lang.String, org.drip.capital.simulation.PathEnsemble> pathEnsembleMap)
		throws java.lang.Exception
	{
		if (null == (_pathEnsembleMap = pathEnsembleMap))
		{
			throw new java.lang.Exception ("CapitalSegmentPathEnsemble Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Map of Path Ensembles
	 * 
	 * @return The Map of Path Ensembles
	 */

	public java.util.Map<java.lang.String, org.drip.capital.simulation.PathEnsemble> pathEnsembleMap()
	{
		return _pathEnsembleMap;
	}

	/**
	 * Construct the Contributing Marginal PnL Attribution given the Confidence Level by Count
	 * 
	 * @param confidenceCount Confidence Level by Count
	 * 
	 * @return The Contributing Marginal PnL Attribution
	 */

	public org.drip.capital.explain.CapitalSegmentStandaloneMarginal marginalPnLAttribution (
		final int confidenceCount)
	{
		org.drip.capital.explain.CapitalUnitPnLAttribution segmentCapitalUnitPnLAttribution =
			super.pnlAttribution (confidenceCount);

		if (null == segmentCapitalUnitPnLAttribution)
		{
			return null;
		}

		java.util.List<java.lang.Integer> pathIndexList = segmentCapitalUnitPnLAttribution.pathIndexList();

		java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution>
			marginalPnLAttributionMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.explain.PnLAttribution>();

		java.util.Set<java.lang.String> capitalUnitCoordinateSet = _pathEnsembleMap.keySet();

		for (java.lang.String capitalUnitCoordinate : capitalUnitCoordinateSet)
		{
			org.drip.capital.explain.CapitalUnitPnLAttribution capitalUnitPnLAttribution =
				_pathEnsembleMap.get (capitalUnitCoordinate).pnlAttribution (
					pathIndexList
				);

			if (null == capitalUnitPnLAttribution)
			{
				return null;
			}

			marginalPnLAttributionMap.put (
				capitalUnitCoordinate,
				capitalUnitPnLAttribution
			);
		}

		try
		{
			return new org.drip.capital.explain.CapitalSegmentStandaloneMarginal (
				segmentCapitalUnitPnLAttribution.pathPnLRealizationList(),
				marginalPnLAttributionMap,
				null
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Contributing Marginal PnL Attribution given the Confidence Level by Percentage
	 * 
	 * @param confidenceLevel Confidence Level by Percentage
	 * 
	 * @return The Contributing Marginal PnL Attribution
	 */

	public org.drip.capital.explain.CapitalSegmentStandaloneMarginal marginalPnLAttribution (
		final double confidenceLevel)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (confidenceLevel) ||
			0. >= confidenceLevel || 1. <= confidenceLevel)
		{
			return null;
		}

		return marginalPnLAttribution ((int) (count() * (1. - confidenceLevel)));
	}

	/**
	 * Construct the Capital Segment Stand-alone PnL Attribution given the Confidence Level by Count
	 * 
	 * @param confidenceCount Confidence Level by Count
	 * 
	 * @return The Capital Segment Stand-alone PnL Attribution
	 */

	public org.drip.capital.explain.CapitalSegmentPnLAttribution standalonePnLAttribution (
		final int confidenceCount)
	{
		int capitalUnitIndex = 0;

		org.drip.capital.explain.CapitalUnitPnLAttribution[] capitalUnitPathAttributionArray =
			new org.drip.capital.explain.CapitalUnitPnLAttribution[_pathEnsembleMap.size()];

		java.util.Set<java.lang.String> capitalUnitCoordinateSet = _pathEnsembleMap.keySet();

		for (java.lang.String capitalUnitCoordinate : capitalUnitCoordinateSet)
		{
			if (null == (
				capitalUnitPathAttributionArray[capitalUnitIndex++] =
					_pathEnsembleMap.get (capitalUnitCoordinate).pnlAttribution (confidenceCount)
			))
			{
				return null;
			}
		}

		try
		{
			return new org.drip.capital.explain.CapitalSegmentPnLAttribution (
				capitalUnitPathAttributionArray
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Capital Segment Stand-alone PnL Attribution given the Confidence Level by Percentage
	 * 
	 * @param confidenceLevel Confidence Level by Percentage
	 * 
	 * @return The Capital Segment Stand-alone PnL Attribution
	 */

	public org.drip.capital.explain.CapitalSegmentPnLAttribution standalonePnLAttribution (
		final double confidenceLevel)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (confidenceLevel) ||
			0. >= confidenceLevel || 1. <= confidenceLevel)
		{
			return null;
		}

		return standalonePnLAttribution ((int) (count() * (1. - confidenceLevel)));
	}

	/**
	 * Construct the Contributing Marginal and Stand-alone PnL Attribution given the Confidence Level by
	 * 	Count
	 * 
	 * @param confidenceCount Confidence Level by Count
	 * 
	 * @return The Contributing Marginal and Stand-alone PnL Attribution
	 */

	public org.drip.capital.explain.CapitalSegmentStandaloneMarginal marginalStandalonePnLAttribution (
		final int confidenceCount)
	{
		org.drip.capital.explain.CapitalUnitPnLAttribution segmentCapitalUnitPnLAttribution =
			super.pnlAttribution (confidenceCount);

		if (null == segmentCapitalUnitPnLAttribution)
		{
			return null;
		}

		java.util.List<java.lang.Integer> pathIndexList = segmentCapitalUnitPnLAttribution.pathIndexList();

		java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution> marginalPnLAttributionMap
			= new org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.explain.PnLAttribution>();

		java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution>
			standalonePnLAttributionMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.explain.PnLAttribution>();

		java.util.Set<java.lang.String> capitalUnitCoordinateSet = _pathEnsembleMap.keySet();

		for (java.lang.String capitalUnitCoordinate : capitalUnitCoordinateSet)
		{
			org.drip.capital.explain.CapitalUnitPnLAttribution capitalUnitMarginalAttribution =
				_pathEnsembleMap.get (
					capitalUnitCoordinate
				).pnlAttribution (
					pathIndexList
				);

			if (null == capitalUnitMarginalAttribution)
			{
				return null;
			}

			marginalPnLAttributionMap.put (
				capitalUnitCoordinate,
				capitalUnitMarginalAttribution
			);

			org.drip.capital.explain.CapitalUnitPnLAttribution capitalUnitStandaloneAttribution =
				_pathEnsembleMap.get (capitalUnitCoordinate).pnlAttribution (confidenceCount);

			if (null == capitalUnitStandaloneAttribution)
			{
				return null;
			}

			standalonePnLAttributionMap.put (
				capitalUnitCoordinate,
				capitalUnitStandaloneAttribution
			);
		}

		try
		{
			return new org.drip.capital.explain.CapitalSegmentStandaloneMarginal (
				segmentCapitalUnitPnLAttribution.pathPnLRealizationList(),
				marginalPnLAttributionMap,
				standalonePnLAttributionMap
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Contributing Marginal and Stand-alone PnL Attribution given the Confidence Level by
	 * 	Percentage
	 * 
	 * @param confidenceLevel Confidence Level by Count
	 * 
	 * @return The Contributing Marginal and Stand-alone PnL Attribution
	 */

	public org.drip.capital.explain.CapitalSegmentStandaloneMarginal marginalStandalonePnLAttribution (
		final double confidenceLevel)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (confidenceLevel) ||
			0. >= confidenceLevel || 1. <= confidenceLevel)
		{
			return null;
		}

		return marginalStandalonePnLAttribution ((int) (count() * (1. - confidenceLevel)));
	}
}
