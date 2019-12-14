
package org.drip.capital.allocation;

/**
 * <i>EntityCapitalAssignmentSetting</i> holds the Correlation Elasticities for the different Capital
 * 	Components as well as the Entity's Correlation Category. The References are:
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

public class EntityCapitalAssignmentSetting
{
	private int _gsstAllocationScheme = java.lang.Integer.MIN_VALUE;
	private int _cBSSTAllocationScheme = java.lang.Integer.MIN_VALUE;
	private int _iBSSTAllocationScheme = java.lang.Integer.MIN_VALUE;
	private int _gsstAllocationCategory = java.lang.Integer.MIN_VALUE;
	private int _cBSSTAllocationCategory = java.lang.Integer.MIN_VALUE;
	private int _iBSSTAllocationCategory = java.lang.Integer.MIN_VALUE;
	private int _noStressAllocationScheme = java.lang.Integer.MIN_VALUE;
	private int _noStressAllocationCategory = java.lang.Integer.MIN_VALUE;
	private int _allocationCorrelationCategory = java.lang.Integer.MIN_VALUE;

	/**
	 * Construct the Uniform Beta Instance of EntityCapitalAssignmentSetting
	 * 
	 * @param allocationCorrelationCategory Allocation Correlation Category of the Capital Entity
	 * @param noStressAllocationCategory Allocation Category for the "No-Stress" Capital Component
	 * @param gsstAllocationCategory Allocation Category for the GSST Capital Component
	 * @param cBSSTAllocationCategory Allocation Category for the cBSST Capital Component
	 * @param iBSSTAllocationCategory Allocation Category for the iBSST Capital Component
	 * 
	 * @return Uniform Beta Instance of EntityCapitalAssignmentSetting
	 */

	public static final EntityCapitalAssignmentSetting UniformBeta (
		final int allocationCorrelationCategory,
		final int noStressAllocationCategory,
		final int gsstAllocationCategory,
		final int cBSSTAllocationCategory,
		final int iBSSTAllocationCategory)
	{
		try
		{
			return new EntityCapitalAssignmentSetting (
				allocationCorrelationCategory,
				noStressAllocationCategory,
				org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA,
				gsstAllocationCategory,
				org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA,
				cBSSTAllocationCategory,
				org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA,
				iBSSTAllocationCategory,
				org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the iBSST Pro-Rata + Others Beta Instance of EntityCapitalAssignmentSetting
	 * 
	 * @param allocationCorrelationCategory Allocation Correlation Category of the Capital Entity
	 * @param noStressAllocationCategory Allocation Category for the "No-Stress" Capital Component
	 * @param gsstAllocationCategory Allocation Category for the GSST Capital Component
	 * @param cBSSTAllocationCategory Allocation Category for the cBSST Capital Component
	 * @param iBSSTAllocationCategory Allocation Category for the iBSST Capital Component
	 * 
	 * @return iBSST Pro-Rata + Others Beta Instance of EntityCapitalAssignmentSetting
	 */

	public static final EntityCapitalAssignmentSetting UniformBetaIBSSTProRata (
		final int allocationCorrelationCategory,
		final int noStressAllocationCategory,
		final int gsstAllocationCategory,
		final int cBSSTAllocationCategory,
		final int iBSSTAllocationCategory)
	{
		try
		{
			return new EntityCapitalAssignmentSetting (
				allocationCorrelationCategory,
				noStressAllocationCategory,
				org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA,
				gsstAllocationCategory,
				org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA,
				cBSSTAllocationCategory,
				org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA,
				iBSSTAllocationCategory,
				org.drip.capital.allocation.EntityComponentAssignmentScheme.PRO_RATA
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * EntityCapitalAssignmentSetting Constructor
	 * 
	 * @param allocationCorrelationCategory Allocation Correlation Category of the Capital Entity
	 * @param noStressAllocationCategory Allocation Category for the "No-Stress" Capital Component
	 * @param noStressAllocationScheme Allocation Scheme for the "No-Stress" Capital Component
	 * @param gsstAllocationCategory Allocation Category for the GSST Capital Component
	 * @param gsstAllocationScheme Allocation Scheme for the GSST Capital Component
	 * @param cBSSTAllocationCategory Allocation Category for the cBSST Capital Component
	 * @param cBSSTAllocationScheme Allocation Scheme for the cBSST Capital Component
	 * @param iBSSTAllocationCategory Allocation Category for the iBSST Capital Component
	 * @param iBSSTAllocationScheme Allocation Scheme for the iBSST Capital Component
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EntityCapitalAssignmentSetting (
		final int allocationCorrelationCategory,
		final int noStressAllocationCategory,
		final int noStressAllocationScheme,
		final int gsstAllocationCategory,
		final int gsstAllocationScheme,
		final int cBSSTAllocationCategory,
		final int cBSSTAllocationScheme,
		final int iBSSTAllocationCategory,
		final int iBSSTAllocationScheme)
		throws java.lang.Exception
	{
		if (java.lang.Integer.MIN_VALUE == (
				_allocationCorrelationCategory = allocationCorrelationCategory
			) || java.lang.Integer.MIN_VALUE == (
				_noStressAllocationCategory = noStressAllocationCategory
			) || java.lang.Integer.MIN_VALUE == (
				_noStressAllocationScheme = noStressAllocationScheme
			) || java.lang.Integer.MIN_VALUE == (
				_gsstAllocationCategory = gsstAllocationCategory
			) || java.lang.Integer.MIN_VALUE == (
				_gsstAllocationScheme = gsstAllocationScheme
			) || java.lang.Integer.MIN_VALUE == (
				_cBSSTAllocationCategory = cBSSTAllocationCategory
			) || java.lang.Integer.MIN_VALUE == (
				_cBSSTAllocationScheme = cBSSTAllocationScheme
			) || java.lang.Integer.MIN_VALUE == (
				_iBSSTAllocationCategory = iBSSTAllocationCategory
			) || java.lang.Integer.MIN_VALUE == (
				_iBSSTAllocationScheme = iBSSTAllocationScheme
			) || org.drip.capital.allocation.EntityComponentCorrelationCategory.ENTITY_CORRELATION ==
				_allocationCorrelationCategory
		)
		{
			throw new java.lang.Exception (
				"EntityCapitalAssignmentSetting Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Allocation Correlation Category of the Capital Entity
	 * 
	 * @return TRUE - The Allocation Correlation Category of the Capital Entity
	 */

	public int allocationCorrelationCategory()
	{
		return _allocationCorrelationCategory;
	}

	/**
	 * Retrieve the Allocation Category for the "No-Stress" Capital Component
	 * 
	 * @return The Allocation Category for the "No-Stress" Capital Component
	 */

	public int noStressAllocationCategory()
	{
		if (org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA == _noStressAllocationScheme
			&& org.drip.capital.allocation.EntityComponentCorrelationCategory.ENTITY_CORRELATION ==
				_noStressAllocationCategory)
		{
			return _allocationCorrelationCategory;
		}

		return _noStressAllocationCategory;
	}

	/**
	 * Retrieve the Allocation Scheme for the "No-Stress" Capital Component
	 * 
	 * @return The Elasticity for the "No-Stress" Capital Component
	 */

	public int noStressAllocationScheme()
	{
		return _noStressAllocationScheme;
	}

	/**
	 * Retrieve the GSST Allocation Category of the Capital Entity
	 * 
	 * @return TRUE - The GSST Allocation Category of the Capital Entity
	 */

	public int gsstAllocationCategory()
	{
		if (org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA == _gsstAllocationScheme &&
			org.drip.capital.allocation.EntityComponentCorrelationCategory.ENTITY_CORRELATION ==
				_gsstAllocationCategory)
		{
			return _allocationCorrelationCategory;
		}

		return _gsstAllocationCategory;
	}

	/**
	 * Retrieve the Allocation Scheme for the GSST Capital Component
	 * 
	 * @return The Allocation Scheme for the GSST Capital Component
	 */

	public int gsstAllocationScheme()
	{
		return _gsstAllocationScheme;
	}

	/**
	 * Retrieve the Allocation Category for the cBSST Capital Component
	 * 
	 * @return The Allocation Category for the cBSST Capital Component
	 */

	public int cBSSTAllocationCategory()
	{
		if (org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA == _cBSSTAllocationScheme &&
			org.drip.capital.allocation.EntityComponentCorrelationCategory.ENTITY_CORRELATION ==
				_cBSSTAllocationCategory)
		{
			return _allocationCorrelationCategory;
		}

		return _cBSSTAllocationCategory;
	}

	/**
	 * Retrieve the Allocation Scheme for the cBSST Capital Component
	 * 
	 * @return The Allocation Scheme for the cBSST Capital Component
	 */

	public int cBSSTAllocationScheme()
	{
		return _cBSSTAllocationScheme;
	}

	/**
	 * Retrieve the Allocation Category for the iBSST Capital Component
	 * 
	 * @return The Allocation Category for the iBSST Capital Component
	 */

	public int iBSSTAllocationCategory()
	{
		if (org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA == _iBSSTAllocationScheme &&
			org.drip.capital.allocation.EntityComponentCorrelationCategory.ENTITY_CORRELATION ==
				_iBSSTAllocationCategory)
		{
			return _allocationCorrelationCategory;
		}

		return _iBSSTAllocationCategory;
	}

	/**
	 * Retrieve the Allocation Scheme for the iBSST Capital Component
	 * 
	 * @return The Allocation Scheme for the iBSST Capital Component
	 */

	public int iBSSTAllocationScheme()
	{
		return _iBSSTAllocationScheme;
	}
}
