
package org.drip.capital.allocation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Quantitative Risk Analytics
 */

/**
 * <i>CorrelationCategoryBetaManager</i> holds the Beta Loading Map Scheme for the different Correlation
 * 	Categories. The References are:
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

public class CorrelationCategoryBetaManager
{

	private java.util.Map<java.lang.Integer, org.drip.capital.allocation.CorrelationCategoryBeta>
		_correlationCategoryBetaMap = null;

	/**
	 * Construct the Three-Beta Fixed-High Float-Medium Float-Low Instance of CorrelationCategoryBetaManager
	 * 
	 * @param highCorrelationFixedBetaLoading High Correlation Beta FIXED Loading
	 * @param mediumCorrelationFloatBetaLoading Medium Correlation Beta FLOAT Loading
	 * 
	 * @return The Three-Beta Fixed-High Float-Medium Float-Low Instance of CorrelationCategoryBetaManager
	 */

	public static final CorrelationCategoryBetaManager ThreeBetaFixedFloatFloat (
		final double highCorrelationFixedBetaLoading,
		final double mediumCorrelationFloatBetaLoading)
	{
		CorrelationCategoryBetaManager correlationCategoryBetaManager =
			new CorrelationCategoryBetaManager();

		try
		{
			if (!correlationCategoryBetaManager.addCorrelationCategoryBeta (
				org.drip.capital.allocation.EntityComponentCorrelationCategory.HIGH_CORRELATION,
				new org.drip.capital.allocation.CorrelationCategoryBeta (
					org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FIXED,
					highCorrelationFixedBetaLoading
				)
			))
			{
				return null;
			}

			if (!correlationCategoryBetaManager.addCorrelationCategoryBeta (
				org.drip.capital.allocation.EntityComponentCorrelationCategory.MEDIUM_CORRELATION,
				new org.drip.capital.allocation.CorrelationCategoryBeta (
					org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FLOAT,
					mediumCorrelationFloatBetaLoading
				)
			))
			{
				return null;
			}

			if (!correlationCategoryBetaManager.addCorrelationCategoryBeta (
				org.drip.capital.allocation.EntityComponentCorrelationCategory.LOW_CORRELATION,
				new org.drip.capital.allocation.CorrelationCategoryBeta (
					org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FLOAT,
					1.
				)
			))
			{
				return null;
			}

			if (!correlationCategoryBetaManager.addCorrelationCategoryBeta (
				org.drip.capital.allocation.EntityComponentCorrelationCategory.HEDGE,
				new org.drip.capital.allocation.CorrelationCategoryBeta (
					org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FIXED,
					-1.
				)
			))
			{
				return null;
			}

		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		return correlationCategoryBetaManager;
	}

	/**
	 * Construct the Fixed-High Float-Low Two-Beta Instance of CorrelationCategoryBetaManager
	 * 
	 * @param highCorrelationFixedBetaLoading High Correlation Beta FIXED Loading
	 * 
	 * @return The Fixed-High Float-Low Two-Beta Instance of CorrelationCategoryBetaManager
	 */

	public static final CorrelationCategoryBetaManager TwoBetaFixedFloat (
		final double highCorrelationFixedBetaLoading)
	{
		CorrelationCategoryBetaManager correlationCategoryBetaManager =
			new CorrelationCategoryBetaManager();

		try
		{
			if (!correlationCategoryBetaManager.addCorrelationCategoryBeta (
				org.drip.capital.allocation.EntityComponentCorrelationCategory.HIGH_CORRELATION,
				new org.drip.capital.allocation.CorrelationCategoryBeta (
					org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FIXED,
					highCorrelationFixedBetaLoading
				)
			))
			{
				return null;
			}

			if (!correlationCategoryBetaManager.addCorrelationCategoryBeta (
				org.drip.capital.allocation.EntityComponentCorrelationCategory.LOW_CORRELATION,
				new org.drip.capital.allocation.CorrelationCategoryBeta (
					org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FLOAT,
					1.
				)
			))
			{
				return null;
			}

			if (!correlationCategoryBetaManager.addCorrelationCategoryBeta (
				org.drip.capital.allocation.EntityComponentCorrelationCategory.HEDGE,
				new org.drip.capital.allocation.CorrelationCategoryBeta (
					org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FIXED,
					-1.
				)
			))
			{
				return null;
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		return correlationCategoryBetaManager;
	}

	/**
	 * Construct the Float-High Float-Low Two-Beta Instance of CorrelationCategoryBetaManager
	 * 
	 * @param highCorrelationFloatBetaLoading High Correlation Beta FLOAT Loading
	 * 
	 * @return The Float-High Float-Low Two-Beta Instance of CorrelationCategoryBetaManager
	 */

	public static final CorrelationCategoryBetaManager TwoBetaFloatFloat (
		final double highCorrelationFloatBetaLoading)
	{
		CorrelationCategoryBetaManager correlationCategoryBetaManager =
			new CorrelationCategoryBetaManager();

		try
		{
			if (!correlationCategoryBetaManager.addCorrelationCategoryBeta (
				org.drip.capital.allocation.EntityComponentCorrelationCategory.HIGH_CORRELATION,
				new org.drip.capital.allocation.CorrelationCategoryBeta (
					org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FLOAT,
					highCorrelationFloatBetaLoading
				)
			))
			{
				return null;
			}

			if (!correlationCategoryBetaManager.addCorrelationCategoryBeta (
				org.drip.capital.allocation.EntityComponentCorrelationCategory.LOW_CORRELATION,
				new org.drip.capital.allocation.CorrelationCategoryBeta (
					org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FLOAT,
					1.
				)
			))
			{
				return null;
			}

			if (!correlationCategoryBetaManager.addCorrelationCategoryBeta (
				org.drip.capital.allocation.EntityComponentCorrelationCategory.HEDGE,
				new org.drip.capital.allocation.CorrelationCategoryBeta (
					org.drip.capital.allocation.CorrelationCategoryBeta.ELASTICITY_FIXED,
					-1.
				)
			))
			{
				return null;
			}
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();

			return null;
		}

		return correlationCategoryBetaManager;
	}

	/**
	 * Empty CorrelationCategoryBetaManager Constructor
	 */

	public CorrelationCategoryBetaManager()
	{
	}

	/**
	 * Retrieve the Historical Correlation Category Beta Map
	 * 
	 * @return The Historical Correlation Category Beta Map
	 */

	public java.util.Map<java.lang.Integer, org.drip.capital.allocation.CorrelationCategoryBeta>
		correlationCategoryBetaMap()
	{
		return _correlationCategoryBetaMap;
	}

	/**
	 * Add the Beta Loading corresponding to the Correlation Category
	 * 
	 * @param correlationCategory The Correlation Category
	 * @param correlationCategoryBeta The Correlation Category Beta Loading
	 * 
	 * @return TRUE - The Beta Loading successfully added
	 */

	public boolean addCorrelationCategoryBeta (
		final int correlationCategory,
		final org.drip.capital.allocation.CorrelationCategoryBeta correlationCategoryBeta)
	{
		if (null == correlationCategoryBeta)
		{
			return false;
		}

		if (null == _correlationCategoryBetaMap)
		{
			_correlationCategoryBetaMap =
				new java.util.TreeMap<java.lang.Integer,
					org.drip.capital.allocation.CorrelationCategoryBeta>();
		}

		_correlationCategoryBetaMap.put (
			correlationCategory,
			correlationCategoryBeta
		);

		return true;
	}

	/**
	 * Indicate of the Correlation Category Exists
	 * 
	 * @param correlationCategory The Correlation Category
	 * 
	 * @return TRUE - The Correlation Category Exists
	 */

	public boolean categoryExists (
		final int correlationCategory)
	{
		return _correlationCategoryBetaMap.containsKey (
			correlationCategory
		);
	}

	/**
	 * Retrieve the Correlation Category Beta Loading for the Correlation Category
	 * 
	 * @param correlationCategory The Correlation Category
	 * 
	 * @return The Correlation Category Beta Loading
	 */

	public org.drip.capital.allocation.CorrelationCategoryBeta correlationCategoryBeta (
		final int correlationCategory)
	{
		return categoryExists (
			correlationCategory
		) ? _correlationCategoryBetaMap.get (
			correlationCategory
		) : null;
	}
}
