
package org.drip.capital.allocation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>EntityComponentCapital</i> holds the Component Capital for each Entity. The References are:
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

public class EntityComponentCapital
{
	private double _gsstStandaloneMultiplier = java.lang.Double.NaN;
	private org.drip.capital.allocation.EntityCapital _gsst = null;
	private double _cBSSTStandaloneMultiplier = java.lang.Double.NaN;
	private double _iBSSTStandaloneMultiplier = java.lang.Double.NaN;
	private org.drip.capital.allocation.EntityCapital _cBSST = null;
	private org.drip.capital.allocation.EntityCapital _iBSST = null;
	private double _noStressStandaloneMultiplier = java.lang.Double.NaN;
	private org.drip.capital.allocation.EntityCapital _noStress = null;

	private static final double NoStressComponentCapitalMultiplier (
		final org.drip.capital.allocation.CorrelationCategoryBetaManager correlationCategoryBetaManager,
		final org.drip.capital.allocation.EntityCapitalAssignmentSetting entityCapitalAssignmentSetting,
		final java.util.Map<java.lang.String, java.lang.Double> proRataNormalizerMap,
		final double unitFloatBeta)
	{
		if (org.drip.capital.allocation.EntityComponentAssignmentScheme.PRO_RATA ==
			entityCapitalAssignmentSetting.noStressAllocationScheme())
		{
			return null == proRataNormalizerMap || !proRataNormalizerMap.containsKey (
				"NOSTRESS"
			) ? 0. : proRataNormalizerMap.get (
				"NOSTRESS"
			);
		}

		org.drip.capital.allocation.CorrelationCategoryBeta betaLoading =
			correlationCategoryBetaManager.correlationCategoryBeta (
				entityCapitalAssignmentSetting.noStressAllocationCategory()
			);

		int elasticity = betaLoading.elasticity();

		if (org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FIXED == elasticity)
		{
			return betaLoading.loading();
		}

		if (org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FLOAT == elasticity)
		{
			return betaLoading.loading() * unitFloatBeta;
		}

		return 0.;
	}

	private static final double GSSTComponentCapitalMultiplier (
		final org.drip.capital.allocation.CorrelationCategoryBetaManager correlationCategoryBetaManager,
		final org.drip.capital.allocation.EntityCapitalAssignmentSetting entityCapitalAssignmentSetting,
		final java.util.Map<java.lang.String, java.lang.Double> proRataNormalizerMap,
		final double unitFloatBeta)
	{
		if (org.drip.capital.allocation.EntityComponentAssignmentScheme.PRO_RATA ==
			entityCapitalAssignmentSetting.gsstAllocationScheme())
		{
			return null == proRataNormalizerMap || !proRataNormalizerMap.containsKey (
				"GSST"
			) ? 0. : proRataNormalizerMap.get (
				"GSST"
			);
		}

		org.drip.capital.allocation.CorrelationCategoryBeta betaLoading =
			correlationCategoryBetaManager.correlationCategoryBeta (
				entityCapitalAssignmentSetting.gsstAllocationCategory()
			);

		int elasticity = betaLoading.elasticity();

		if (org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FIXED == elasticity)
		{
			return betaLoading.loading();
		}

		if (org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FLOAT == elasticity)
		{
			return betaLoading.loading() * unitFloatBeta;
		}

		return 0.;
	}

	private static final double CBSSTComponentCapitalMultiplier (
		final org.drip.capital.allocation.CorrelationCategoryBetaManager correlationCategoryBetaManager,
		final org.drip.capital.allocation.EntityCapitalAssignmentSetting entityCapitalAssignmentSetting,
		final java.util.Map<java.lang.String, java.lang.Double> proRataNormalizerMap,
		final double unitFloatBeta)
	{
		if (org.drip.capital.allocation.EntityComponentAssignmentScheme.PRO_RATA ==
			entityCapitalAssignmentSetting.cBSSTAllocationScheme())
		{
			return null == proRataNormalizerMap || !proRataNormalizerMap.containsKey (
				"CBSST"
			) ? 0. : proRataNormalizerMap.get (
				"CBSST"
			);
		}

		org.drip.capital.allocation.CorrelationCategoryBeta betaLoading =
			correlationCategoryBetaManager.correlationCategoryBeta (
				entityCapitalAssignmentSetting.cBSSTAllocationCategory()
			);

		int elasticity = betaLoading.elasticity();

		if (org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FIXED == elasticity)
		{
			return betaLoading.loading();
		}

		if (org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FLOAT == elasticity)
		{
			return betaLoading.loading() * unitFloatBeta;
		}

		return 0.;
	}

	private static final double IBSSTComponentCapitalMultiplier (
		final org.drip.capital.allocation.CorrelationCategoryBetaManager correlationCategoryBetaManager,
		final org.drip.capital.allocation.EntityCapitalAssignmentSetting entityCapitalAssignmentSetting,
		final java.util.Map<java.lang.String, java.lang.Double> proRataNormalizerMap,
		final double unitFloatBeta)
	{
		if (org.drip.capital.allocation.EntityComponentAssignmentScheme.PRO_RATA ==
			entityCapitalAssignmentSetting.iBSSTAllocationScheme())
		{
			return null == proRataNormalizerMap || !proRataNormalizerMap.containsKey (
				"IBSST"
			) ? 0. : proRataNormalizerMap.get (
				"IBSST"
			);
		}

		org.drip.capital.allocation.CorrelationCategoryBeta betaLoading =
			correlationCategoryBetaManager.correlationCategoryBeta (
				entityCapitalAssignmentSetting.iBSSTAllocationCategory()
			);

		int elasticity = betaLoading.elasticity();

		if (org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FIXED == elasticity)
		{
			return betaLoading.loading();
		}

		if (org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FLOAT == elasticity)
		{
			return betaLoading.loading() * unitFloatBeta;
		}

		return 0.;
	}

	/**
	 * Construct the Entity Component Capital Instance from the Individual Component Capital
	 * 
	 * @param noStressComponentCapital No Stress Capital Component
	 * @param noStressStandaloneMultiplier No-Stress Stand-alone Multiplier
	 * @param gsstComponentCapital GSST Capital Component
	 * @param gsstStandaloneMultiplier GSST Stand-alone Multiplier
	 * @param cBSSTComponentCapital cBSST Capital Component
	 * @param cBSSTStandaloneMultiplier cBSST Stand-alone Multiplier
	 * @param iBSSTComponentCapital iBSST Capital Component
	 * @param iBSSTStandaloneMultiplier iBSST Stand-alone Multiplier
	 * @param grossEntityAllocation Entity Gross Capital Allocation
	 * 
	 * @return The Entity Component Capital Instance from the Individual Component Capital
	 */

	public static final EntityComponentCapital FromComponentCapital (
		final double noStressComponentCapital,
		final double noStressStandaloneMultiplier,
		final double gsstComponentCapital,
		final double gsstStandaloneMultiplier,
		final double cBSSTComponentCapital,
		final double cBSSTStandaloneMultiplier,
		final double iBSSTComponentCapital,
		final double iBSSTStandaloneMultiplier,
		final double grossEntityAllocation)
	{
		try
		{
			return new EntityComponentCapital (
				new org.drip.capital.allocation.EntityCapital (
					noStressComponentCapital,
					noStressComponentCapital / grossEntityAllocation
				),
				noStressStandaloneMultiplier,
				new org.drip.capital.allocation.EntityCapital (
					gsstComponentCapital,
					gsstComponentCapital / grossEntityAllocation
				),
				gsstStandaloneMultiplier,
				new org.drip.capital.allocation.EntityCapital (
					cBSSTComponentCapital,
					cBSSTComponentCapital / grossEntityAllocation
				),
				cBSSTStandaloneMultiplier,
				new org.drip.capital.allocation.EntityCapital (
					iBSSTComponentCapital,
					iBSSTComponentCapital / grossEntityAllocation
				),
				iBSSTStandaloneMultiplier
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Entity Component Capital from the PnL Attribution
	 * 
	 * @param correlationCategoryBetaManager The Correlation Category Beta Manager
	 * @param entityCapitalAssignmentSetting The Entity Capital Component Setting
	 * @param pnlAttribution The PnL Attribution
	 * @param proRataNormalizerMap The Pro-Rata Normalizer Map
	 * @param unitFloatBeta Unit Float Beta
	 * @param grossEntityAllocation Entity Gross Allocated Capital
	 * 
	 * @return The Entity Component Capital Instance from the PnL Attribution
	 */

	public static final EntityComponentCapital FromPnLAttribution (
		final org.drip.capital.allocation.CorrelationCategoryBetaManager correlationCategoryBetaManager,
		final org.drip.capital.allocation.EntityCapitalAssignmentSetting entityCapitalAssignmentSetting,
		final org.drip.capital.explain.PnLAttribution pnlAttribution,
		final java.util.Map<java.lang.String, java.lang.Double> proRataNormalizerMap,
		final double unitFloatBeta,
		final double grossEntityAllocation)
	{
		double noStressStandaloneMultiplier = NoStressComponentCapitalMultiplier (
			correlationCategoryBetaManager,
			entityCapitalAssignmentSetting,
			proRataNormalizerMap,
			unitFloatBeta
		);

		double gsstStandaloneMultiplier = GSSTComponentCapitalMultiplier (
			correlationCategoryBetaManager,
			entityCapitalAssignmentSetting,
			proRataNormalizerMap,
			unitFloatBeta
		);

		double cBSSTStandaloneMultiplier = CBSSTComponentCapitalMultiplier (
			correlationCategoryBetaManager,
			entityCapitalAssignmentSetting,
			proRataNormalizerMap,
			unitFloatBeta
		);

		double iBSSTStandaloneMultiplier = IBSSTComponentCapitalMultiplier (
			correlationCategoryBetaManager,
			entityCapitalAssignmentSetting,
			proRataNormalizerMap,
			unitFloatBeta
		);

		return null == correlationCategoryBetaManager ||
			null == entityCapitalAssignmentSetting ||
			null == pnlAttribution ? null :
			FromComponentCapital (
				noStressStandaloneMultiplier * pnlAttribution.fsGrossPnL(),
				noStressStandaloneMultiplier,
				gsstStandaloneMultiplier * pnlAttribution.gsstPnL(),
				gsstStandaloneMultiplier,
				cBSSTStandaloneMultiplier * pnlAttribution.cBSSTPnL(),
				cBSSTStandaloneMultiplier,
				iBSSTStandaloneMultiplier * pnlAttribution.iBSSTGrossPnL(),
				iBSSTStandaloneMultiplier,
				grossEntityAllocation
			);
	}

	/**
	 * EntityComponentCapital Constructor
	 * 
	 * @param noStress Entity No Stress Capital
	 * @param noStressStandaloneMultiplier No-Stress Stand-alone Multiplier
	 * @param gsst Entity GSST Capital
	 * @param gsstStandaloneMultiplier GSST Stand-alone Multiplier
	 * @param cBSST Entity cBSST Capital
	 * @param cBSSTStandaloneMultiplier cBSST Stand-alone Multiplier
	 * @param iBSST Entity iBSST Capital
	 * @param iBSSTStandaloneMultiplier iBSST Stand-alone Multiplier
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EntityComponentCapital (
		final org.drip.capital.allocation.EntityCapital noStress,
		final double noStressStandaloneMultiplier,
		final org.drip.capital.allocation.EntityCapital gsst,
		final double gsstStandaloneMultiplier,
		final org.drip.capital.allocation.EntityCapital cBSST,
		final double cBSSTStandaloneMultiplier,
		final org.drip.capital.allocation.EntityCapital iBSST,
		final double iBSSTStandaloneMultiplier)
		throws java.lang.Exception
	{
		if (null == (_noStress = noStress) || !org.drip.numerical.common.NumberUtil.IsValid (
				_noStressStandaloneMultiplier = noStressStandaloneMultiplier
			) || null == (_gsst = gsst) || !org.drip.numerical.common.NumberUtil.IsValid (
				_gsstStandaloneMultiplier = gsstStandaloneMultiplier
			) || null == (_cBSST = cBSST) || !org.drip.numerical.common.NumberUtil.IsValid (
				_cBSSTStandaloneMultiplier = cBSSTStandaloneMultiplier
			) || null == (_iBSST = iBSST) || !org.drip.numerical.common.NumberUtil.IsValid (
				_iBSSTStandaloneMultiplier = iBSSTStandaloneMultiplier
			)
		)
		{
			throw new java.lang.Exception (
				"EntityComponentCapital Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Entity No Stress Capital
	 * 
	 * @return The Entity No Stress Capital
	 */

	public org.drip.capital.allocation.EntityCapital noStress()
	{
		return _noStress;
	}

	/**
	 * Retrieve the Entity GSST Capital
	 * 
	 * @return The Entity GSST Capital
	 */

	public org.drip.capital.allocation.EntityCapital gsst()
	{
		return _gsst;
	}

	/**
	 * Retrieve the GSST Stand-alone Multiplier
	 * 
	 * @return The GSST Stand-alone Multiplier
	 */

	public double gsstStandaloneMultiplier()
	{
		return _gsstStandaloneMultiplier;
	}

	/**
	 * Retrieve the cBSST Stand-alone Multiplier
	 * 
	 * @return The cBSST Stand-alone Multiplier
	 */

	public double cBSSTStandaloneMultiplier()
	{
		return _cBSSTStandaloneMultiplier;
	}

	/**
	 * Retrieve the iBSST Stand-alone Multiplier
	 * 
	 * @return The iBSST Stand-alone Multiplier
	 */

	public double iBSSTStandaloneMultiplier()
	{
		return _iBSSTStandaloneMultiplier;
	}

	/**
	 * Retrieve the No-Stress Stand-alone Multiplier
	 * 
	 * @return The No-Stress Stand-alone Multiplier
	 */

	public double noStressStandaloneMultiplier()
	{
		return _noStressStandaloneMultiplier;
	}

	/**
	 * Retrieve the Entity cBSST Capital
	 * 
	 * @return The Entity cBSST Capital
	 */

	public org.drip.capital.allocation.EntityCapital cBSST()
	{
		return _cBSST;
	}

	/**
	 * Retrieve the Entity iBSST Capital
	 * 
	 * @return The Entity iBSST Capital
	 */

	public org.drip.capital.allocation.EntityCapital iBSST()
	{
		return _iBSST;
	}

	/**
	 * Retrieve the Total Entity Capital
	 * 
	 * @return The Total Entity Capital
	 */

	public org.drip.capital.allocation.EntityCapital total()
	{
		try
		{
			return new org.drip.capital.allocation.EntityCapital (
				_noStress.absolute() + _gsst.absolute() + _cBSST.absolute() + _iBSST.absolute(),
				_noStress.fractional() + _gsst.fractional() + _cBSST.fractional() + _iBSST.fractional()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
