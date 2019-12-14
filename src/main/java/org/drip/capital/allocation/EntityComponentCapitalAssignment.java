
package org.drip.capital.allocation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>EntityComponentCapitalAssignment</i> contains the Capital Assignment for each Entity and its Component.
 *  The References are:
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

public class EntityComponentCapitalAssignment
{
	private double _unitFloatBeta = java.lang.Double.NaN;
	private double _allocatedBetaCapital = java.lang.Double.NaN;
	private double _allocatedProRataCapital = java.lang.Double.NaN;
	private org.drip.capital.allocation.EntityElasticityAttribution _elasticityAttribution = null;
	private java.util.Map<java.lang.String, org.drip.capital.allocation.EntityComponentCapital>
		_entityComponentCapitalMap = null;

	/**
	 * EntityComponentCapitalAssignment Constructor
	 * 
	 * @param elasticityAttribution Entity Elasticity Attribution 
	 * @param entityComponentCapitalMap Entity Component Capital Assignment Map
	 * @param unitFloatBeta Unit Float Beta
	 * @param allocatedBetaCapital Allocated Beta Capital
	 * @param allocatedProRataCapital Allocated Pro-Rata Capital
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public EntityComponentCapitalAssignment (
		final org.drip.capital.allocation.EntityElasticityAttribution elasticityAttribution,
		final java.util.Map<java.lang.String, org.drip.capital.allocation.EntityComponentCapital>
			entityComponentCapitalMap,
		final double unitFloatBeta,
		final double allocatedBetaCapital,
		final double allocatedProRataCapital)
		throws java.lang.Exception
	{
		if (null == (_elasticityAttribution = elasticityAttribution) ||
			null == (_entityComponentCapitalMap = entityComponentCapitalMap) ||
				0 == _entityComponentCapitalMap.size() ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				_unitFloatBeta = unitFloatBeta
			) || !org.drip.numerical.common.NumberUtil.IsValid (
				_allocatedBetaCapital = allocatedBetaCapital
			) || !org.drip.numerical.common.NumberUtil.IsValid (
				_allocatedProRataCapital = allocatedProRataCapital
			)
		)
		{
			throw new java.lang.Exception (
				"EntityComponentCapitalAssignment Constructor => Invalid Inputs"
			);
		}
	}

	/**
	 * Retrieve the Entity Elasticity Attribution
	 * 
	 * @return The Entity Elasticity Attribution
	 */

	public org.drip.capital.allocation.EntityElasticityAttribution elasticityAttribution()
	{
		return _elasticityAttribution;
	}

	/**
	 * Retrieve the Entity Component Capital Assignment Map
	 * 
	 * @return The Entity Component Capital Assignment Map
	 */

	public java.util.Map<java.lang.String, org.drip.capital.allocation.EntityComponentCapital>
		entityComponentCapitalMap()
	{
		return _entityComponentCapitalMap;
	}

	/**
	 * Retrieve the Unit Float Beta
	 * 
	 * @return The Unit Float Beta
	 */

	public double unitFloatBeta()
	{
		return _unitFloatBeta;
	}

	/**
	 * Retrieve the Allocated Beta Capital
	 * 
	 * @return The Allocated Beta Capital
	 */

	public double allocatedBetaCapital()
	{
		return _allocatedBetaCapital;
	}

	/**
	 * Retrieve the Allocated Pro-Rata Capital
	 * 
	 * @return The Allocated Pro-Rata Capital
	 */

	public double allocatedProRataCapital()
	{
		return _allocatedProRataCapital;
	}

	/**
	 * Retrieve the Allocated Total Capital
	 * 
	 * @return The Allocated Total Capital
	 */

	public double allocatedTotalCapital()
	{
		return _allocatedBetaCapital + _allocatedProRataCapital;
	}

	/**
	 * Retrieve the Total GSST Entity Capital
	 * 
	 * @return Total GSST Entity Capital
	 */

	public org.drip.capital.allocation.EntityCapital gsst()
	{
		double gsst = 0.;

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.allocation.EntityComponentCapital>
			entityComponentCapitalEntry : _entityComponentCapitalMap.entrySet())
		{
			gsst = gsst + entityComponentCapitalEntry.getValue().gsst().absolute();
		}

		try
		{
			return new org.drip.capital.allocation.EntityCapital (
				gsst,
				gsst / (_allocatedBetaCapital + _allocatedProRataCapital)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Total cBSST Entity Capital
	 * 
	 * @return Total cBSST Entity Capital
	 */

	public org.drip.capital.allocation.EntityCapital cBSST()
	{
		double cBSST = 0.;

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.allocation.EntityComponentCapital>
			entityComponentCapitalEntry : _entityComponentCapitalMap.entrySet())
		{
			cBSST = cBSST + entityComponentCapitalEntry.getValue().cBSST().absolute();
		}

		try
		{
			return new org.drip.capital.allocation.EntityCapital (
				cBSST,
				cBSST / (_allocatedBetaCapital + _allocatedProRataCapital)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Total iBSST Entity Capital
	 * 
	 * @return Total iBSST Entity Capital
	 */

	public org.drip.capital.allocation.EntityCapital iBSST()
	{
		double iBSST = 0.;

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.allocation.EntityComponentCapital>
			entityComponentCapitalEntry : _entityComponentCapitalMap.entrySet())
		{
			iBSST = iBSST + entityComponentCapitalEntry.getValue().iBSST().absolute();
		}

		try
		{
			return new org.drip.capital.allocation.EntityCapital (
				iBSST,
				iBSST / (_allocatedBetaCapital + _allocatedProRataCapital)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Total No Stress Entity Capital
	 * 
	 * @return Total No Stress Entity Capital
	 */

	public org.drip.capital.allocation.EntityCapital noStress()
	{
		double noStress = 0.;

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.allocation.EntityComponentCapital>
			entityComponentCapitalEntry : _entityComponentCapitalMap.entrySet())
		{
			noStress = noStress + entityComponentCapitalEntry.getValue().noStress().absolute();
		}

		try
		{
			return new org.drip.capital.allocation.EntityCapital (
				noStress,
				noStress / (_allocatedBetaCapital + _allocatedProRataCapital)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Total Fixed Elasticity Capital
	 * 
	 * @return Total Fixed Elasticity Capital
	 */

	public org.drip.capital.allocation.EntityCapital fixed()
	{
		double fixed = _elasticityAttribution.fixed();

		try
		{
			return new org.drip.capital.allocation.EntityCapital (
				fixed,
				fixed / (_allocatedBetaCapital + _allocatedProRataCapital)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Total Floating Elasticity Capital
	 * 
	 * @return Total Floating Elasticity Capital
	 */

	public org.drip.capital.allocation.EntityCapital floating()
	{
		double floating = _elasticityAttribution.floating();

		try
		{
			return new org.drip.capital.allocation.EntityCapital (
				floating,
				floating / (_allocatedBetaCapital + _allocatedProRataCapital)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
