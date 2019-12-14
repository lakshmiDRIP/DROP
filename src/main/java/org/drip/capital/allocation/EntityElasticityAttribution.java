
package org.drip.capital.allocation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>EntityElasticityAttribution</i> holds the Attributions across all Entity Components into Fixed, Float,
 * 	and Pro-rata Elasticities. The References are:
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
 * @author Lakshmi Krishnamurthy
 */

public class EntityElasticityAttribution
{
	private org.drip.capital.allocation.EntityComponentElasticityAttribution _gsst = null;
	private org.drip.capital.allocation.EntityComponentElasticityAttribution _cBSST = null;
	private org.drip.capital.allocation.EntityComponentElasticityAttribution _iBSST = null;
	private org.drip.capital.allocation.EntityComponentElasticityAttribution _noStress = null;

	/**
	 * EntityElasticityAttribution Constructor
	 * 
	 * @param correlationCategoryBetaManager The Correlation Category Beta Manager
	 * @param unitLoading The Unit Loading Flag
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EntityElasticityAttribution (
		final org.drip.capital.allocation.CorrelationCategoryBetaManager correlationCategoryBetaManager,
		final boolean unitLoading)
		throws java.lang.Exception
	{
		_gsst = new org.drip.capital.allocation.EntityComponentElasticityAttribution (
			correlationCategoryBetaManager,
			unitLoading
		);

		_cBSST = new org.drip.capital.allocation.EntityComponentElasticityAttribution (
			correlationCategoryBetaManager,
			unitLoading
		);

		_iBSST = new org.drip.capital.allocation.EntityComponentElasticityAttribution (
			correlationCategoryBetaManager,
			unitLoading
		);

		_noStress = new org.drip.capital.allocation.EntityComponentElasticityAttribution (
			correlationCategoryBetaManager,
			unitLoading
		);
	}

	/**
	 * Retrieve the GSST Elasticity Attribution
	 * 
	 * @return The GSST Elasticity Attribution
	 */

	public org.drip.capital.allocation.EntityComponentElasticityAttribution gsst()
	{
		return _gsst;
	}

	/**
	 * Retrieve the cBSST Elasticity Attribution
	 * 
	 * @return The cBSST Elasticity Attribution
	 */

	public org.drip.capital.allocation.EntityComponentElasticityAttribution cBSST()
	{
		return _cBSST;
	}

	/**
	 * Retrieve the iBSST Elasticity Attribution
	 * 
	 * @return The iBSST Elasticity Attribution
	 */

	public org.drip.capital.allocation.EntityComponentElasticityAttribution iBSST()
	{
		return _iBSST;
	}

	/**
	 * Retrieve the "No Stress" Elasticity Attribution
	 * 
	 * @return The "No Stress" Elasticity Attribution
	 */

	public org.drip.capital.allocation.EntityComponentElasticityAttribution noStress()
	{
		return _noStress;
	}

	/**
	 * Accumulate the GSST Fixed Attribution
	 * 
	 * @param gsstFixedAttribution GSST Fixed Attribution
	 * 
	 * @return TRUE - The GSST Fixed Attribution successfully updated
	 */

	public boolean accumulateGSSTFixed (
		final double gsstFixedAttribution)
	{
		return _gsst.accumulateFixed (
			gsstFixedAttribution
		);
	}

	/**
	 * Accumulate the cBSST Fixed Attribution
	 * 
	 * @param cBSSTFixedAttribution cBSST Fixed Attribution
	 * 
	 * @return TRUE - The cBSST Fixed Attribution successfully updated
	 */

	public boolean accumulateCBSSTFixed (
		final double cBSSTFixedAttribution)
	{
		return _cBSST.accumulateFixed (
			cBSSTFixedAttribution
		);
	}

	/**
	 * Accumulate the iBSST Fixed Attribution
	 * 
	 * @param iBSSTFixedAttribution cBSST Fixed Attribution
	 * 
	 * @return TRUE - The iBSST Fixed Attribution successfully updated
	 */

	public boolean accumulateIBSSTFixed (
		final double iBSSTFixedAttribution)
	{
		return _iBSST.accumulateFixed (
			iBSSTFixedAttribution
		);
	}

	/**
	 * Accumulate the No-Stress Fixed Attribution
	 * 
	 * @param noStressFixedAttribution No-Stress Fixed Attribution
	 * 
	 * @return TRUE - The No-Stress Fixed Attribution successfully updated
	 */

	public boolean accumulateNoStressFixed (
		final double noStressFixedAttribution)
	{
		return _noStress.accumulateFixed (
			noStressFixedAttribution
		);
	}

	/**
	 * Accumulate the GSST Floating Attribution
	 * 
	 * @param gsstFloatingAttribution GSST Floating Attribution
	 * 
	 * @return TRUE - The GSST Floating Attribution successfully updated
	 */

	public boolean accumulateGSSTFloating (
		final double gsstFloatingAttribution)
	{
		return _gsst.accumulateFloating (
			gsstFloatingAttribution
		);
	}

	/**
	 * Accumulate the cBSST Floating Attribution
	 * 
	 * @param cBSSTFloatingAttribution cBSST Floating Attribution
	 * 
	 * @return TRUE - The cBSST Floating Attribution successfully updated
	 */

	public boolean accumulateCBSSTFloating (
		final double cBSSTFloatingAttribution)
	{
		return _cBSST.accumulateFloating (
			cBSSTFloatingAttribution
		);
	}

	/**
	 * Accumulate the iBSST Floating Attribution
	 * 
	 * @param iBSSTFloatingAttribution cBSST Floating Attribution
	 * 
	 * @return TRUE - The iBSST Floating Attribution successfully updated
	 */

	public boolean accumulateIBSSTFloating (
		final double iBSSTFloatingAttribution)
	{
		return _iBSST.accumulateFloating (
			iBSSTFloatingAttribution
		);
	}

	/**
	 * Accumulate the No-Stress Floating Attribution
	 * 
	 * @param noStressFloatingAttribution No-Stress Floating Attribution
	 * 
	 * @return TRUE - The No-Stress Floating Attribution successfully updated
	 */

	public boolean accumulateNoStressFloating (
		final double noStressFloatingAttribution)
	{
		return _noStress.accumulateFloating (
			noStressFloatingAttribution
		);
	}

	/**
	 * Accumulate the GSST Pro-Rata Attribution
	 * 
	 * @param gsstProRataAttribution GSST Pro-Rata Attribution
	 * 
	 * @return TRUE - The GSST Pro-Rata Attribution successfully updated
	 */

	public boolean accumulateGSSTProRata (
		final double gsstProRataAttribution)
	{
		return _gsst.accumulateProRata (
			gsstProRataAttribution
		);
	}

	/**
	 * Accumulate the cBSST Pro-Rata Attribution
	 * 
	 * @param cBSSTProRataAttribution cBSST Pro-Rata Attribution
	 * 
	 * @return TRUE - The cBSST Pro-Rata Attribution successfully updated
	 */

	public boolean accumulateCBSSTProRata (
		final double cBSSTProRataAttribution)
	{
		return _cBSST.accumulateProRata (
			cBSSTProRataAttribution
		);
	}

	/**
	 * Accumulate the iBSST Pro-Rata Attribution
	 * 
	 * @param iBSSTProRataAttribution cBSST Pro-Rata Attribution
	 * 
	 * @return TRUE - The iBSST Pro-Rata Attribution successfully updated
	 */

	public boolean accumulateIBSSTProRata (
		final double iBSSTProRataAttribution)
	{
		return _iBSST.accumulateProRata (
			iBSSTProRataAttribution
		);
	}

	/**
	 * Accumulate the No-Stress Pro-Rata Attribution
	 * 
	 * @param noStressProRataAttribution No-Stress Pro-Rata Attribution
	 * 
	 * @return TRUE - The No-Stress Pro-Rata Attribution successfully updated
	 */

	public boolean accumulateNoStressProRata (
		final double noStressProRataAttribution)
	{
		return _noStress.accumulateProRata (
			noStressProRataAttribution
		);
	}

	/**
	 * Accumulate the GSST Attribution with the Beta-Adjusted Component Attribution
	 * 
	 * @param gsstAttribution The GSST Attribution
	 * @param gsstAllocationCategory The GSST Allocation Category
	 * @param gsstAllocationScheme The GSST Allocation Scheme
	 * 
	 * @return TRUE - The GSST Partition with the Beta-Adjusted Increment
	 */

	public boolean accumulateGSST (
		final double gsstAttribution,
		final int gsstAllocationCategory,
		final int gsstAllocationScheme)
	{
		return _gsst.accumulate (
			gsstAttribution,
			gsstAllocationCategory,
			gsstAllocationScheme
		);
	}

	/**
	 * Accumulate the cBSST Attribution with the Beta-Adjusted Component Attribution
	 * 
	 * @param cBSSTAttribution The cBSST Attribution
	 * @param cBSSTAllocationCategory The cBSST Allocation Category
	 * @param cBSSTAllocationScheme The cBSST Allocation Scheme
	 * 
	 * @return TRUE - The cBSST Partition with the Beta-Adjusted Increment
	 */

	public boolean accumulateCBSST (
		final double cBSSTAttribution,
		final int cBSSTAllocationCategory,
		final int cBSSTAllocationScheme)
	{
		return _cBSST.accumulate (
			cBSSTAttribution,
			cBSSTAllocationCategory,
			cBSSTAllocationScheme
		);
	}

	/**
	 * Accumulate the iBSST Attribution with the Beta-Adjusted Component Attribution
	 * 
	 * @param iBSSTAttribution The iBSST Attribution
	 * @param iBSSTAllocationCategory The iBSST Allocation Category
	 * @param iBSSTAllocationScheme The iBSST Allocation Scheme
	 * 
	 * @return TRUE - The iBSST Partition with the Beta-Adjusted Increment
	 */

	public boolean accumulateIBSST (
		final double iBSSTAttribution,
		final int iBSSTAllocationCategory,
		final int iBSSTAllocationScheme)
	{
		return _iBSST.accumulate (
			iBSSTAttribution,
			iBSSTAllocationCategory,
			iBSSTAllocationScheme
		);
	}

	/**
	 * Accumulate the No Stress Attribution with the Beta-Adjusted Component Attribution
	 * 
	 * @param noStressAttribution The No Stress Attribution
	 * @param noStressAllocationCategory The No Stress Allocation Category
	 * @param noStressAllocationScheme The No Stress Allocation Scheme
	 * 
	 * @return TRUE - The No Stress Partition with the Beta-Adjusted Increment
	 */

	public boolean accumulateNoStress (
		final double noStressAttribution,
		final int noStressAllocationCategory,
		final int noStressAllocationScheme)
	{
		return _noStress.accumulate (
			noStressAttribution,
			noStressAllocationCategory,
			noStressAllocationScheme
		);
	}

	/**
	 * Accumulate with the Beta-Adjusted Component Attribution
	 * 
	 * @param pnlAttribution PnL Attribution
	 * @param entityCapitalAssignmentSetting Entity Capital Assignment Setting
	 * 
	 * @return TRUE - The Accumulation is successful
	 */

	public boolean accumulate (
		final org.drip.capital.explain.PnLAttribution pnlAttribution,
		final org.drip.capital.allocation.EntityCapitalAssignmentSetting entityCapitalAssignmentSetting)
	{
		if (null == pnlAttribution ||
			null == entityCapitalAssignmentSetting)
		{
			return false;
		}

		if (!accumulateGSST (
			pnlAttribution.gsstPnL(),
			entityCapitalAssignmentSetting.gsstAllocationCategory(),
			entityCapitalAssignmentSetting.gsstAllocationScheme()
		))
		{
			return false;
		}

		if (!accumulateCBSST (
			pnlAttribution.cBSSTPnL(),
			entityCapitalAssignmentSetting.cBSSTAllocationCategory(),
			entityCapitalAssignmentSetting.cBSSTAllocationScheme()
		))
		{
			return false;
		}

		if (!accumulateIBSST (
			pnlAttribution.iBSSTGrossPnL(),
			entityCapitalAssignmentSetting.iBSSTAllocationCategory(),
			entityCapitalAssignmentSetting.iBSSTAllocationScheme()
		))
		{
			return false;
		}

		if (!accumulateNoStress (
			pnlAttribution.fsGrossPnL(),
			entityCapitalAssignmentSetting.noStressAllocationCategory(),
			entityCapitalAssignmentSetting.noStressAllocationScheme()
		))
		{
			return false;
		}

		return true;
	}

	/**
	 * Retrieve the Total GSST Component Capital
	 * 
	 * @return The Total GSST Component Capital
	 */

	public double gsstTotal()
	{
		return _gsst.fixed() + _gsst.floating() + _gsst.proRata();
	}

	/**
	 * Retrieve the Total cBSST Component Capital
	 * 
	 * @return The Total cBSST Component Capital
	 */

	public double cBSSTTotal()
	{
		return _cBSST.fixed() + _cBSST.floating() + _cBSST.proRata();
	}

	/**
	 * Retrieve the Total iBSST Component Capital
	 * 
	 * @return The Total iBSST Component Capital
	 */

	public double iBSSTTotal()
	{
		return _iBSST.fixed() + _iBSST.floating() + _iBSST.proRata();
	}

	/**
	 * Retrieve the Total No Stress Component Capital
	 * 
	 * @return The Total No Stress Component Capital
	 */

	public double noStressTotal()
	{
		return _noStress.fixed() + _noStress.floating() + _noStress.proRata();
	}

	/**
	 * Retrieve the Fixed Beta Capital Component
	 * 
	 * @return The Fixed Beta Capital Component
	 */

	public double fixed()
	{
		return _gsst.fixed() + _cBSST.fixed() + _iBSST.fixed() + _noStress.fixed();
	}

	/**
	 * Retrieve the Floating Beta Capital Component
	 * 
	 * @return The Floating Beta Capital Component
	 */

	public double floating()
	{
		return _gsst.floating() + _cBSST.floating() + _iBSST.floating() + _noStress.floating();
	}

	/**
	 * Retrieve the Pro-Rata Beta Capital Component
	 * 
	 * @return The Pro-Rata Beta Capital Component
	 */

	public double proRata()
	{
		return _gsst.proRata() + _cBSST.proRata() + _iBSST.proRata() + _noStress.proRata();
	}

	/**
	 * Retrieve the Pro-Rata GSST Capital
	 *  
	 * @return The Pro-Rata GSST Capital
	 */

	public double gsstProRata()
	{
		return _gsst.proRata();
	}

	/**
	 * Retrieve the Pro-Rata cBSST Capital
	 *  
	 * @return The Pro-Rata cBSST Capital
	 */

	public double cBSSTProRata()
	{
		return _cBSST.proRata();
	}

	/**
	 * Retrieve the Pro-Rata iBSST Capital
	 *  
	 * @return The Pro-Rata iBSST Capital
	 */

	public double iBSSTProRata()
	{
		return _iBSST.proRata();
	}

	/**
	 * Retrieve the Pro-Rata No-Stress Capital
	 *  
	 * @return The Pro-Rata No-Stress Capital
	 */

	public double noStressProRata()
	{
		return _noStress.proRata();
	}
}
