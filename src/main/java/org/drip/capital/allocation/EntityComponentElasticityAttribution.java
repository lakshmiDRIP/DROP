
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
