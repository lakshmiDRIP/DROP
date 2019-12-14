
package org.drip.capital.explain;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CapitalSegmentStandaloneMarginal</i> holds the Top-of-the-House Capital Attributions as well the
 * Segment-Level Contributions from the Stand-alone Capital Units. The References are:
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

public class CapitalSegmentStandaloneMarginal
	extends org.drip.capital.explain.CapitalUnitPnLAttribution
{
	private java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution>
		_marginalPnLAttributionMap = null;

	private java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution>
		_standalonePnLAttributionMap = null;

	private static final java.util.Map<java.lang.String, java.lang.Double> UpdateProRataNormalizerMap (
		final org.drip.capital.allocation.EntityElasticityAttribution
			standaloneEntityCapitalElasticityAttribution,
		final org.drip.capital.allocation.EntityElasticityAttribution
			marginalEntityCapitalElasticityAttribution)
	{
		java.util.Map<java.lang.String, java.lang.Double> proRataNormalizerMap =
			new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

		double gsstProRataStandalone = standaloneEntityCapitalElasticityAttribution.gsstProRata();

		if (0. != gsstProRataStandalone)
		{
			proRataNormalizerMap.put (
				"GSST",
				marginalEntityCapitalElasticityAttribution.gsstProRata() / gsstProRataStandalone
			);
		}

		double cBSSTProRataStandalone = standaloneEntityCapitalElasticityAttribution.cBSSTProRata();

		if (0. != cBSSTProRataStandalone)
		{
			proRataNormalizerMap.put (
				"CBSST",
				marginalEntityCapitalElasticityAttribution.cBSSTProRata() / cBSSTProRataStandalone
			);
		}

		double iBSSTProRataStandalone = standaloneEntityCapitalElasticityAttribution.iBSSTProRata();

		if (0. != iBSSTProRataStandalone)
		{
			proRataNormalizerMap.put (
				"IBSST",
				marginalEntityCapitalElasticityAttribution.iBSSTProRata() / iBSSTProRataStandalone
			);
		}

		double noStressProRataStandalone = standaloneEntityCapitalElasticityAttribution.noStressProRata();

		if (0. != noStressProRataStandalone)
		{
			proRataNormalizerMap.put (
				"NOSTRESS",
				marginalEntityCapitalElasticityAttribution.noStressProRata() / noStressProRataStandalone
			);
		}

		return proRataNormalizerMap;
	}

	/**
	 * CapitalSegmentStandaloneMarginal Constructor
	 * 
	 * @param pathPnLRealizationList Segment Level Merged Path PnL Realization List
	 * @param marginalPnLAttributionMap Capital Unit Marginal PnL Attribution Map
	 * @param standalonePnLAttributionMap Capital Unit Marginal PnL Attribution Map
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CapitalSegmentStandaloneMarginal (
		final java.util.List<org.drip.capital.simulation.PathPnLRealization> pathPnLRealizationList,
		final java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution>
			marginalPnLAttributionMap,
		final java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution>
			standalonePnLAttributionMap)
		throws java.lang.Exception
	{
		super (
			pathPnLRealizationList
		);

		_marginalPnLAttributionMap = marginalPnLAttributionMap;
		_standalonePnLAttributionMap = standalonePnLAttributionMap;
	}

	/**
	 * Retrieve the Capital Unit Marginal PnL Attribution Map
	 * 
	 * @return The Capital Unit Marginal PnL Attribution Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution>
		marginalPnLAttributionMap()
	{
		return _marginalPnLAttributionMap;
	}

	/**
	 * Retrieve the Capital Unit Stand-alone PnL Attribution Map
	 * 
	 * @return The Capital Unit Stand-alone PnL Attribution Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution>
		standalonePnLAttributionMap()
	{
		return _standalonePnLAttributionMap;
	}

	/**
	 * Compute the Expected Short-fall Based Beta Allocation Map
	 * 
	 * @param capitalAllocationControl Capital Allocation Setting Control
	 * 
	 * @return The Expected Short-fall Based Beta Allocation Map
	 */

	public org.drip.capital.allocation.EntityComponentCapitalAssignment betaAllocation (
		final org.drip.capital.setting.CapitalAllocationControl capitalAllocationControl)
	{
		if (null == capitalAllocationControl)
		{
			return null;
		}

		org.drip.capital.allocation.EntityElasticityAttribution
			marginalEntityCapitalElasticityAttribution = null;
		org.drip.capital.allocation.EntityElasticityAttribution
			allocatedEntityCapitalElasticityAttribution = null;

		org.drip.capital.allocation.CorrelationCategoryBetaManager correlationCategoryBetaManager =
			capitalAllocationControl.correlationCategoryBetaManager();

		java.util.Map<java.lang.String, org.drip.capital.allocation.EntityCapitalAssignmentSetting>
			entityCapitalAssignmentSettingMap =
				capitalAllocationControl.entityCapitalAssignmentSettingMap();

		try
		{
			marginalEntityCapitalElasticityAttribution =
				new org.drip.capital.allocation.EntityElasticityAttribution (
					correlationCategoryBetaManager,
					true
				);

			allocatedEntityCapitalElasticityAttribution =
				new org.drip.capital.allocation.EntityElasticityAttribution (
					correlationCategoryBetaManager,
					false
				);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.explain.PnLAttribution>
			marginalPnLAttributionEntry : _marginalPnLAttributionMap.entrySet())
		{
			if (!marginalEntityCapitalElasticityAttribution.accumulate (
				marginalPnLAttributionEntry.getValue(),
				entityCapitalAssignmentSettingMap.get (
					marginalPnLAttributionEntry.getKey()
				)
			))
			{
				return null;
			}
		}

		java.util.Map<java.lang.String, org.drip.capital.explain.PnLAttribution> pnlAttributionMap =
			capitalAllocationControl.useMarginal() ? _marginalPnLAttributionMap :
				_standalonePnLAttributionMap;

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.explain.PnLAttribution>
			pnlAttributionEntry : pnlAttributionMap.entrySet())
		{
			if (!allocatedEntityCapitalElasticityAttribution.accumulate (
				pnlAttributionEntry.getValue(),
				entityCapitalAssignmentSettingMap.get (
					pnlAttributionEntry.getKey()
				)
			))
			{
				return null;
			}
		}

		double floatingBetaCapitalStandalone = allocatedEntityCapitalElasticityAttribution.floating();

		double fixedBetaCapitalStandalone = allocatedEntityCapitalElasticityAttribution.fixed();

		double allocatedBetaCapital = marginalEntityCapitalElasticityAttribution.floating() +
			marginalEntityCapitalElasticityAttribution.fixed();

		if (0. == floatingBetaCapitalStandalone ||
			allocatedBetaCapital > fixedBetaCapitalStandalone)
		{
			return null;
		}

		double unitFloatBeta = (allocatedBetaCapital - fixedBetaCapitalStandalone) /
			floatingBetaCapitalStandalone;

		java.util.Map<java.lang.String, java.lang.Double> proRataNormalizerMap = UpdateProRataNormalizerMap (
			allocatedEntityCapitalElasticityAttribution,
			marginalEntityCapitalElasticityAttribution
		);

		if (null == proRataNormalizerMap)
		{
			return null;
		}

		double allocatedProRataCapital = marginalEntityCapitalElasticityAttribution.noStressProRata() +
			marginalEntityCapitalElasticityAttribution.gsstProRata() +
			marginalEntityCapitalElasticityAttribution.cBSSTProRata() +
			marginalEntityCapitalElasticityAttribution.iBSSTProRata();

		java.util.Map<java.lang.String, org.drip.capital.allocation.EntityComponentCapital>
			entityComponentCapitalMap = new
				org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.capital.allocation.EntityComponentCapital>();

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.explain.PnLAttribution>
			pnlAttributionEntry : pnlAttributionMap.entrySet())
		{
			java.lang.String capitalUnitCoordinate = pnlAttributionEntry.getKey();

			entityComponentCapitalMap.put (
				capitalUnitCoordinate,
				org.drip.capital.allocation.EntityComponentCapital.FromPnLAttribution (
					correlationCategoryBetaManager,
					entityCapitalAssignmentSettingMap.get (
						capitalUnitCoordinate
					),
					pnlAttributionEntry.getValue(),
					proRataNormalizerMap,
					unitFloatBeta,
					allocatedBetaCapital + allocatedProRataCapital
				)
			);
		}

		try
		{
			return new org.drip.capital.allocation.EntityComponentCapitalAssignment (
				allocatedEntityCapitalElasticityAttribution,
				entityComponentCapitalMap,
				unitFloatBeta,
				allocatedBetaCapital,
				allocatedProRataCapital
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
