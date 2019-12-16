
package org.drip.capital.allocation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
	 * Retrieve the Total Systemic Entity Capital
	 * 
	 * @return Total Systemic Entity Capital
	 */

	public org.drip.capital.allocation.EntityCapital systemic()
	{
		double systemic = 0.;

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.allocation.EntityComponentCapital>
			entityComponentCapitalEntry : _entityComponentCapitalMap.entrySet())
		{
			systemic = systemic + entityComponentCapitalEntry.getValue().systemic().absolute();
		}

		try
		{
			return new org.drip.capital.allocation.EntityCapital (
				systemic,
				systemic / (_allocatedBetaCapital + _allocatedProRataCapital)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Total Correlated Entity Capital
	 * 
	 * @return Total Correlated Entity Capital
	 */

	public org.drip.capital.allocation.EntityCapital correlated()
	{
		double correlated = 0.;

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.allocation.EntityComponentCapital>
			entityComponentCapitalEntry : _entityComponentCapitalMap.entrySet())
		{
			correlated = correlated + entityComponentCapitalEntry.getValue().correlated().absolute();
		}

		try
		{
			return new org.drip.capital.allocation.EntityCapital (
				correlated,
				correlated / (_allocatedBetaCapital + _allocatedProRataCapital)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Retrieve the Total Idiosyncratic Entity Capital
	 * 
	 * @return Total Idiosyncratic Entity Capital
	 */

	public org.drip.capital.allocation.EntityCapital idiosyncratic()
	{
		double idiosyncratic = 0.;

		for (java.util.Map.Entry<java.lang.String, org.drip.capital.allocation.EntityComponentCapital>
			entityComponentCapitalEntry : _entityComponentCapitalMap.entrySet())
		{
			idiosyncratic = idiosyncratic + entityComponentCapitalEntry.getValue().idiosyncratic().absolute();
		}

		try
		{
			return new org.drip.capital.allocation.EntityCapital (
				idiosyncratic,
				idiosyncratic / (_allocatedBetaCapital + _allocatedProRataCapital)
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
