
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
