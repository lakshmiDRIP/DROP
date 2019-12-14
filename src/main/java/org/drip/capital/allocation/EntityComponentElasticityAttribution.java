
package org.drip.capital.allocation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>EntityComponentElasticityAttribution</i> holds the Attributions of a single Individual Entity Component
 * 	into Fixed, Float, and Pro-rata Elasticities. The References are:
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

public class EntityComponentElasticityAttribution
{
	private double _fixed = -0.;
	private double _proRata = -0.;
	private double _floating = -0.;
	private boolean _unitLoading = false;
	private org.drip.capital.allocation.CorrelationCategoryBetaManager _correlationCategoryBetaManager =
		null;

	/**
	 * EntityComponentElasticityAttribution Constructor
	 * 
	 * @param correlationCategoryBetaManager The Correlation Category Beta Manager
	 * @param unitLoading The Unit Loading Flag
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EntityComponentElasticityAttribution (
		final org.drip.capital.allocation.CorrelationCategoryBetaManager correlationCategoryBetaManager,
		final boolean unitLoading)
		throws java.lang.Exception
	{
		if (null == (_correlationCategoryBetaManager = correlationCategoryBetaManager))
		{
			throw new java.lang.Exception (
				"EntityComponentElasticityAttribution Constructor => Invalid Inputs"
			);
		}

		_unitLoading = unitLoading;
	}

	/**
	 * Retrieve the Correlation Category Beta Manager
	 * 
	 * @return The Correlation Category Beta Manager
	 */

	public org.drip.capital.allocation.CorrelationCategoryBetaManager correlationCategoryBetaManager()
	{
		return _correlationCategoryBetaManager;
	}

	/**
	 * Retrieve the Fixed Attribution
	 * 
	 * @return Total Fixed Attribution
	 */

	public double fixed()
	{
		return _fixed;
	}

	/**
	 * Retrieve the Floating Attribution
	 * 
	 * @return Floating Attribution
	 */

	public double floating()
	{
		return _floating;
	}

	/**
	 * Retrieve the Pro-Rata Attribution
	 * 
	 * @return Pro-Rata Attribution
	 */

	public double proRata()
	{
		return _proRata;
	}

	/**
	 * Retrieve the Unit Loading Flag
	 * 
	 * @return Unit Loading Flag
	 */

	public boolean unitLoading()
	{
		return _unitLoading;
	}

	/**
	 * Accumulate the Fixed Attribution
	 * 
	 * @param fixedAttribution The Component Fixed Attribution
	 * 
	 * @return TRUE - The Fixed Attribution successfully updated
	 */

	public boolean accumulateFixed (
		final double fixedAttribution)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
			fixedAttribution
		))
		{
			return false;
		}

		_fixed += fixedAttribution;
		return true;
	}

	/**
	 * Accumulate the Floating Attribution
	 * 
	 * @param floatingAttribution The Component Floating Attribution
	 * 
	 * @return TRUE - The Floating Attribution successfully updated
	 */

	public boolean accumulateFloating (
		final double floatingAttribution)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
			floatingAttribution
		))
		{
			return false;
		}

		_floating += floatingAttribution;
		return true;
	}

	/**
	 * Accumulate the Pro-Rata Attribution
	 * 
	 * @param proRataAttribution The Component Pro-Rata Attribution
	 * 
	 * @return TRUE - The Pro-Rata Attribution successfully updated
	 */

	public boolean accumulateProRata (
		final double proRataAttribution)
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (
			proRataAttribution
		))
		{
			return false;
		}

		_proRata += proRataAttribution;
		return true;
	}

	/**
	 * Accumulate the Appropriate Attribution with the Beta-Adjusted Component Attribution
	 * 
	 * @param attribution The Component Attribution
	 * @param componentAllocationCategory The Component Allocation Category
	 * @param componentAllocationScheme The Component Allocation Scheme
	 * 
	 * @return TRUE - The Appropriate Partition with the Beta-Adjusted Increment
	 */

	public boolean accumulate (
		final double attribution,
		final int componentAllocationCategory,
		final int componentAllocationScheme)
	{
		if (org.drip.capital.allocation.EntityComponentAssignmentScheme.PRO_RATA ==
			componentAllocationScheme)
		{
			return accumulateProRata (
				attribution
			);
		}

		org.drip.capital.allocation.CorrelationCategoryBeta correlationCategoryBeta =
			_correlationCategoryBetaManager.correlationCategoryBeta (
				componentAllocationCategory
			);

		if (null == correlationCategoryBeta)
		{
			return false;
		}

		if (org.drip.capital.allocation.EntityComponentCorrelationCategory.HEDGE ==
			componentAllocationCategory)
		{
			return true;
		}

		int componentElasticity = correlationCategoryBeta.elasticity();

		if (org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FIXED == componentElasticity)
		{
			return accumulateFixed (
				(_unitLoading ? 1. : correlationCategoryBeta.loading()) * attribution
			);
		}

		if (org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FLOAT == componentElasticity)
		{
			return accumulateFloating (
				(_unitLoading ? 1. : correlationCategoryBeta.loading()) * attribution
			);
		}

		return false;
	}
}
