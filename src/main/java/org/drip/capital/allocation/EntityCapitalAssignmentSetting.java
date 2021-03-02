
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

public class EntityCapitalAssignmentSetting
{
	private int _noStressAllocationScheme = java.lang.Integer.MIN_VALUE;
	private int _systemicAllocationScheme = java.lang.Integer.MIN_VALUE;
	private int _noStressAllocationCategory = java.lang.Integer.MIN_VALUE;
	private int _correlatedAllocationScheme = java.lang.Integer.MIN_VALUE;
	private int _systemicAllocationCategory = java.lang.Integer.MIN_VALUE;
	private int _correlatedAllocationCategory = java.lang.Integer.MIN_VALUE;
	private int _allocationCorrelationCategory = java.lang.Integer.MIN_VALUE;
	private int _idiosyncraticAllocationScheme = java.lang.Integer.MIN_VALUE;
	private int _idiosyncraticAllocationCategory = java.lang.Integer.MIN_VALUE;

	/**
	 * Construct the Uniform Beta Instance of EntityCapitalAssignmentSetting
	 * 
	 * @param allocationCorrelationCategory Allocation Correlation Category of the Capital Entity
	 * @param noStressAllocationCategory Allocation Category for the "No-Stress" Capital Component
	 * @param systemicAllocationCategory Allocation Category for the Systemic Capital Component
	 * @param correlatedAllocationCategory Allocation Category for the Correlated Capital Component
	 * @param idiosyncraticAllocationCategory Allocation Category for the Idiosyncratic Capital Component
	 * 
	 * @return Uniform Beta Instance of EntityCapitalAssignmentSetting
	 */

	public static final EntityCapitalAssignmentSetting UniformBeta (
		final int allocationCorrelationCategory,
		final int noStressAllocationCategory,
		final int systemicAllocationCategory,
		final int correlatedAllocationCategory,
		final int idiosyncraticAllocationCategory)
	{
		try
		{
			return new EntityCapitalAssignmentSetting (
				allocationCorrelationCategory,
				noStressAllocationCategory,
				org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA,
				systemicAllocationCategory,
				org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA,
				correlatedAllocationCategory,
				org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA,
				idiosyncraticAllocationCategory,
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
	 * Construct the Idiosyncratic Pro-Rata + Others Beta Instance of EntityCapitalAssignmentSetting
	 * 
	 * @param allocationCorrelationCategory Allocation Correlation Category of the Capital Entity
	 * @param noStressAllocationCategory Allocation Category for the "No-Stress" Capital Component
	 * @param systemicAllocationCategory Allocation Category for the Systemic Capital Component
	 * @param correlatedAllocationCategory Allocation Category for the Correlated Capital Component
	 * @param idiosyncraticAllocationCategory Allocation Category for the Idiosyncratic Capital Component
	 * 
	 * @return Idiosyncratic Pro-Rata + Others Beta Instance of EntityCapitalAssignmentSetting
	 */

	public static final EntityCapitalAssignmentSetting UniformBetaIdiosyncraticProRata (
		final int allocationCorrelationCategory,
		final int noStressAllocationCategory,
		final int systemicAllocationCategory,
		final int correlatedAllocationCategory,
		final int idiosyncraticAllocationCategory)
	{
		try
		{
			return new EntityCapitalAssignmentSetting (
				allocationCorrelationCategory,
				noStressAllocationCategory,
				org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA,
				systemicAllocationCategory,
				org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA,
				correlatedAllocationCategory,
				org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA,
				idiosyncraticAllocationCategory,
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
	 * @param systemicAllocationCategory Allocation Category for the Systemic Capital Component
	 * @param gsstAllocationScheme Allocation Scheme for the Systemic Capital Component
	 * @param correlatedAllocationCategory Allocation Category for the Correlated Capital Component
	 * @param correlatedAllocationScheme Allocation Scheme for the Correlated Capital Component
	 * @param idiosyncraticAllocationCategory Allocation Category for the Idiosyncratic Capital Component
	 * @param idiosyncraticAllocationScheme Allocation Scheme for the Idiosyncratic Capital Component
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EntityCapitalAssignmentSetting (
		final int allocationCorrelationCategory,
		final int noStressAllocationCategory,
		final int noStressAllocationScheme,
		final int systemicAllocationCategory,
		final int gsstAllocationScheme,
		final int correlatedAllocationCategory,
		final int correlatedAllocationScheme,
		final int idiosyncraticAllocationCategory,
		final int idiosyncraticAllocationScheme)
		throws java.lang.Exception
	{
		if (java.lang.Integer.MIN_VALUE == (
				_allocationCorrelationCategory = allocationCorrelationCategory
			) || java.lang.Integer.MIN_VALUE == (
				_noStressAllocationCategory = noStressAllocationCategory
			) || java.lang.Integer.MIN_VALUE == (
				_noStressAllocationScheme = noStressAllocationScheme
			) || java.lang.Integer.MIN_VALUE == (
				_systemicAllocationCategory = systemicAllocationCategory
			) || java.lang.Integer.MIN_VALUE == (
				_systemicAllocationScheme = gsstAllocationScheme
			) || java.lang.Integer.MIN_VALUE == (
				_correlatedAllocationCategory = correlatedAllocationCategory
			) || java.lang.Integer.MIN_VALUE == (
				_correlatedAllocationScheme = correlatedAllocationScheme
			) || java.lang.Integer.MIN_VALUE == (
				_idiosyncraticAllocationCategory = idiosyncraticAllocationCategory
			) || java.lang.Integer.MIN_VALUE == (
				_idiosyncraticAllocationScheme = idiosyncraticAllocationScheme
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
	 * Retrieve the Systemic Allocation Category of the Capital Entity
	 * 
	 * @return TRUE - The Systemic Allocation Category of the Capital Entity
	 */

	public int systemicAllocationCategory()
	{
		if (org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA == _systemicAllocationScheme &&
			org.drip.capital.allocation.EntityComponentCorrelationCategory.ENTITY_CORRELATION ==
				_systemicAllocationCategory)
		{
			return _allocationCorrelationCategory;
		}

		return _systemicAllocationCategory;
	}

	/**
	 * Retrieve the Allocation Scheme for the Systemic Capital Component
	 * 
	 * @return The Allocation Scheme for the Systemic Capital Component
	 */

	public int systemicAllocationScheme()
	{
		return _systemicAllocationScheme;
	}

	/**
	 * Retrieve the Allocation Category for the Correlated Capital Component
	 * 
	 * @return The Allocation Category for the Correlated Capital Component
	 */

	public int correlatedAllocationCategory()
	{
		if (org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA == _correlatedAllocationScheme
			&& org.drip.capital.allocation.EntityComponentCorrelationCategory.ENTITY_CORRELATION ==
				_correlatedAllocationCategory)
		{
			return _allocationCorrelationCategory;
		}

		return _correlatedAllocationCategory;
	}

	/**
	 * Retrieve the Allocation Scheme for the Correlated Capital Component
	 * 
	 * @return The Allocation Scheme for the Correlated Capital Component
	 */

	public int correlatedAllocationScheme()
	{
		return _correlatedAllocationScheme;
	}

	/**
	 * Retrieve the Allocation Category for the Idiosyncratic Capital Component
	 * 
	 * @return The Allocation Category for the Idiosyncratic Capital Component
	 */

	public int idiosyncraticAllocationCategory()
	{
		if (org.drip.capital.allocation.EntityComponentAssignmentScheme.BETA ==
				_idiosyncraticAllocationScheme &&
			org.drip.capital.allocation.EntityComponentCorrelationCategory.ENTITY_CORRELATION ==
				_idiosyncraticAllocationCategory)
		{
			return _allocationCorrelationCategory;
		}

		return _idiosyncraticAllocationCategory;
	}

	/**
	 * Retrieve the Allocation Scheme for the Idiosyncratic Capital Component
	 * 
	 * @return The Allocation Scheme for the Idiosyncratic Capital Component
	 */

	public int idiosyncraticAllocationScheme()
	{
		return _idiosyncraticAllocationScheme;
	}
}
