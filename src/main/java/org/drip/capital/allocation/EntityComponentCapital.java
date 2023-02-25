
package org.drip.capital.allocation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Graph Algorithm
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/CapitalAnalyticsLibrary.md">Capital Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/README.md">Basel Market Risk and Operational Capital</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/capital/allocation/README.md">Economic Risk Capital Entity Allocation</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EntityComponentCapital
{
	private org.drip.capital.allocation.EntityCapital _noStress = null;
	private org.drip.capital.allocation.EntityCapital _systemic = null;
	private double _noStressStandaloneMultiplier = java.lang.Double.NaN;
	private double _systemicStandaloneMultiplier = java.lang.Double.NaN;
	private org.drip.capital.allocation.EntityCapital _correlated = null;
	private double _correlatedStandaloneMultiplier = java.lang.Double.NaN;
	private org.drip.capital.allocation.EntityCapital _idiosyncratic = null;
	private double _idiosyncraticStandaloneMultiplier = java.lang.Double.NaN;

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

	private static final double SystemicComponentCapitalMultiplier (
		final org.drip.capital.allocation.CorrelationCategoryBetaManager correlationCategoryBetaManager,
		final org.drip.capital.allocation.EntityCapitalAssignmentSetting entityCapitalAssignmentSetting,
		final java.util.Map<java.lang.String, java.lang.Double> proRataNormalizerMap,
		final double unitFloatBeta)
	{
		if (org.drip.capital.allocation.EntityComponentAssignmentScheme.PRO_RATA ==
			entityCapitalAssignmentSetting.systemicAllocationScheme())
		{
			return null == proRataNormalizerMap || !proRataNormalizerMap.containsKey (
				"Systemic"
			) ? 0. : proRataNormalizerMap.get (
				"Systemic"
			);
		}

		org.drip.capital.allocation.CorrelationCategoryBeta betaLoading =
			correlationCategoryBetaManager.correlationCategoryBeta (
				entityCapitalAssignmentSetting.systemicAllocationCategory()
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

	private static final double CorrelatedComponentCapitalMultiplier (
		final org.drip.capital.allocation.CorrelationCategoryBetaManager correlationCategoryBetaManager,
		final org.drip.capital.allocation.EntityCapitalAssignmentSetting entityCapitalAssignmentSetting,
		final java.util.Map<java.lang.String, java.lang.Double> proRataNormalizerMap,
		final double unitFloatBeta)
	{
		if (org.drip.capital.allocation.EntityComponentAssignmentScheme.PRO_RATA ==
			entityCapitalAssignmentSetting.correlatedAllocationScheme())
		{
			return null == proRataNormalizerMap || !proRataNormalizerMap.containsKey (
				"Correlated"
			) ? 0. : proRataNormalizerMap.get (
				"Correlated"
			);
		}

		org.drip.capital.allocation.CorrelationCategoryBeta betaLoading =
			correlationCategoryBetaManager.correlationCategoryBeta (
				entityCapitalAssignmentSetting.correlatedAllocationCategory()
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

	private static final double IdiosyncraticComponentCapitalMultiplier (
		final org.drip.capital.allocation.CorrelationCategoryBetaManager correlationCategoryBetaManager,
		final org.drip.capital.allocation.EntityCapitalAssignmentSetting entityCapitalAssignmentSetting,
		final java.util.Map<java.lang.String, java.lang.Double> proRataNormalizerMap,
		final double unitFloatBeta)
	{
		if (org.drip.capital.allocation.EntityComponentAssignmentScheme.PRO_RATA ==
			entityCapitalAssignmentSetting.idiosyncraticAllocationScheme())
		{
			return null == proRataNormalizerMap || !proRataNormalizerMap.containsKey (
				"Idiosyncratic"
			) ? 0. : proRataNormalizerMap.get (
				"Idiosyncratic"
			);
		}

		org.drip.capital.allocation.CorrelationCategoryBeta betaLoading =
			correlationCategoryBetaManager.correlationCategoryBeta (
				entityCapitalAssignmentSetting.idiosyncraticAllocationCategory()
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
	 * @param systemicComponentCapital Systemic Capital Component
	 * @param systemicStandaloneMultiplier Systemic Stand-alone Multiplier
	 * @param correlatedComponentCapital Correlated Capital Component
	 * @param correlatedStandaloneMultiplier Correlated Stand-alone Multiplier
	 * @param idiosyncraticComponentCapital Idiosyncratic Capital Component
	 * @param idiosyncraticStandaloneMultiplier Idiosyncratic Stand-alone Multiplier
	 * @param grossEntityAllocation Entity Gross Capital Allocation
	 * 
	 * @return The Entity Component Capital Instance from the Individual Component Capital
	 */

	public static final EntityComponentCapital FromComponentCapital (
		final double noStressComponentCapital,
		final double noStressStandaloneMultiplier,
		final double systemicComponentCapital,
		final double systemicStandaloneMultiplier,
		final double correlatedComponentCapital,
		final double correlatedStandaloneMultiplier,
		final double idiosyncraticComponentCapital,
		final double idiosyncraticStandaloneMultiplier,
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
					systemicComponentCapital,
					systemicComponentCapital / grossEntityAllocation
				),
				systemicStandaloneMultiplier,
				new org.drip.capital.allocation.EntityCapital (
					correlatedComponentCapital,
					correlatedComponentCapital / grossEntityAllocation
				),
				correlatedStandaloneMultiplier,
				new org.drip.capital.allocation.EntityCapital (
					idiosyncraticComponentCapital,
					idiosyncraticComponentCapital / grossEntityAllocation
				),
				idiosyncraticStandaloneMultiplier
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

		double systemicStandaloneMultiplier = SystemicComponentCapitalMultiplier (
			correlationCategoryBetaManager,
			entityCapitalAssignmentSetting,
			proRataNormalizerMap,
			unitFloatBeta
		);

		double correlatedStandaloneMultiplier = CorrelatedComponentCapitalMultiplier (
			correlationCategoryBetaManager,
			entityCapitalAssignmentSetting,
			proRataNormalizerMap,
			unitFloatBeta
		);

		double idiosyncraticStandaloneMultiplier = IdiosyncraticComponentCapitalMultiplier (
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
				systemicStandaloneMultiplier * pnlAttribution.systemicPnL(),
				systemicStandaloneMultiplier,
				correlatedStandaloneMultiplier * pnlAttribution.correlatedPnL(),
				correlatedStandaloneMultiplier,
				idiosyncraticStandaloneMultiplier * pnlAttribution.idiosyncraticGrossPnL(),
				idiosyncraticStandaloneMultiplier,
				grossEntityAllocation
			);
	}

	/**
	 * EntityComponentCapital Constructor
	 * 
	 * @param noStress Entity No Stress Capital
	 * @param noStressStandaloneMultiplier No-Stress Stand-alone Multiplier
	 * @param systemic Entity Systemic Capital
	 * @param systemicStandaloneMultiplier Systemic Stand-alone Multiplier
	 * @param correlated Entity cBSST Capital
	 * @param correlatedStandaloneMultiplier Correlated Stand-alone Multiplier
	 * @param idiosyncratic Entity Idiosyncratic Capital
	 * @param idiosyncraticStandaloneMultiplier Idiosyncratic Stand-alone Multiplier
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EntityComponentCapital (
		final org.drip.capital.allocation.EntityCapital noStress,
		final double noStressStandaloneMultiplier,
		final org.drip.capital.allocation.EntityCapital systemic,
		final double systemicStandaloneMultiplier,
		final org.drip.capital.allocation.EntityCapital correlated,
		final double correlatedStandaloneMultiplier,
		final org.drip.capital.allocation.EntityCapital idiosyncratic,
		final double idiosyncraticStandaloneMultiplier)
		throws java.lang.Exception
	{
		if (null == (_noStress = noStress) || !org.drip.numerical.common.NumberUtil.IsValid (
				_noStressStandaloneMultiplier = noStressStandaloneMultiplier
			) || null == (_systemic = systemic) || !org.drip.numerical.common.NumberUtil.IsValid (
				_systemicStandaloneMultiplier = systemicStandaloneMultiplier
			) || null == (_correlated = correlated) || !org.drip.numerical.common.NumberUtil.IsValid (
				_correlatedStandaloneMultiplier = correlatedStandaloneMultiplier
			) || null == (_idiosyncratic = idiosyncratic) || !org.drip.numerical.common.NumberUtil.IsValid (
				_idiosyncraticStandaloneMultiplier = idiosyncraticStandaloneMultiplier
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
	 * Retrieve the Entity Systemic Capital
	 * 
	 * @return The Entity Systemic Capital
	 */

	public org.drip.capital.allocation.EntityCapital systemic()
	{
		return _systemic;
	}

	/**
	 * Retrieve the Systemic Stand-alone Multiplier
	 * 
	 * @return The Systemic Stand-alone Multiplier
	 */

	public double systemicStandaloneMultiplier()
	{
		return _systemicStandaloneMultiplier;
	}

	/**
	 * Retrieve the Correlated Stand-alone Multiplier
	 * 
	 * @return The Correlated Stand-alone Multiplier
	 */

	public double correlatedStandaloneMultiplier()
	{
		return _correlatedStandaloneMultiplier;
	}

	/**
	 * Retrieve the Idiosyncratic Stand-alone Multiplier
	 * 
	 * @return The Idiosyncratic Stand-alone Multiplier
	 */

	public double idiosyncraticStandaloneMultiplier()
	{
		return _idiosyncraticStandaloneMultiplier;
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
	 * Retrieve the Entity Correlated Capital
	 * 
	 * @return The Entity Correlated Capital
	 */

	public org.drip.capital.allocation.EntityCapital correlated()
	{
		return _correlated;
	}

	/**
	 * Retrieve the Entity Idiosyncratic Capital
	 * 
	 * @return The Entity Idiosyncratic Capital
	 */

	public org.drip.capital.allocation.EntityCapital idiosyncratic()
	{
		return _idiosyncratic;
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
				_noStress.absolute() + _systemic.absolute() + _correlated.absolute() +
					_idiosyncratic.absolute(),
				_noStress.fractional() + _systemic.fractional() + _correlated.fractional() +
					_idiosyncratic.fractional()
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
