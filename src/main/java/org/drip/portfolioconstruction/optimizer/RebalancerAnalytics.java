
package org.drip.portfolioconstruction.optimizer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>RebalancerAnalytics</i> holds the Analytics from a given Rebalancing Run.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/optimizer/README.md">Core Portfolio Construction Optimizer Suite</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RebalancerAnalytics
{
	private double _objectiveValue = java.lang.Double.NaN;
	private org.drip.portfolioconstruction.composite.Holdings _finalHoldings = null;
	private org.drip.portfolioconstruction.asset.PortfolioMetrics _portfolioMetrics = null;
	private org.drip.portfolioconstruction.asset.PortfolioBenchmarkMetrics _portfolioBenchmarkMetrics = null;
	private org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> _objectiveTermRealizationMap
		= null;
	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.optimizer.ConstraintRealization>
			_constraintRealizationMap = null;

	/**
	 * RebalancerAnalytics Constructor
	 * 
	 * @param objectiveValue The Objective Value
	 * @param finalHoldings The Final Holdings
	 * @param objectiveTermRealizationMap Map of the Realized Objective Terms
	 * @param constraintRealizationMap Map of the Constraint Terms
	 * @param portfolioMetrics Portfolio Metrics
	 * @param portfolioBenchmarkMetrics Portfolio Benchmark Metrics
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RebalancerAnalytics (
		final double objectiveValue,
		final org.drip.portfolioconstruction.composite.Holdings finalHoldings,
		final org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>
			objectiveTermRealizationMap,
		final
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.optimizer.ConstraintRealization>
				constraintRealizationMap,
		final org.drip.portfolioconstruction.asset.PortfolioMetrics portfolioMetrics,
		final org.drip.portfolioconstruction.asset.PortfolioBenchmarkMetrics portfolioBenchmarkMetrics)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_objectiveValue = objectiveValue) ||
			null == (_finalHoldings = finalHoldings) ||
			null == (_objectiveTermRealizationMap = objectiveTermRealizationMap) ||
			null == (_constraintRealizationMap = constraintRealizationMap))
		{
			throw new java.lang.Exception (
				"RebalancerAnalytics Constructor => Invalid Inputs!"
			);
		}

		_portfolioMetrics = portfolioMetrics;
		_portfolioBenchmarkMetrics = portfolioBenchmarkMetrics;
	}

	/**
	 * Retrieve the Objective Function Value
	 * 
	 * @return Objective Function Value
	 */

	public double objectiveValue()
	{
		return _objectiveValue;
	}

	/**
	 * Retrieve the Portfolio Metrics
	 * 
	 * @return The Portfolio Metrics
	 */

	public org.drip.portfolioconstruction.asset.PortfolioMetrics portfolioMetrics()
	{
		return _portfolioMetrics;
	}

	/**
	 * Retrieve the Portfolio Benchmark Metrics
	 * 
	 * @return The Portfolio Benchmark Metrics
	 */

	public org.drip.portfolioconstruction.asset.PortfolioBenchmarkMetrics portfolioBenchmarkMetrics()
	{
		return _portfolioBenchmarkMetrics;
	}

	/**
	 * Retrieve the Final Holdings of the Optimizer Run
	 * 
	 * @return Final Holdings of the Optimizer Run
	 */

	public org.drip.portfolioconstruction.composite.Holdings finalHoldings()
	{
		return _finalHoldings;
	}

	/**
	 * Retrieve the Map of Constraint Realizations
	 * 
	 * @return Map of Constraint Realizations
	 */

	public java.util.Map<java.lang.String, org.drip.portfolioconstruction.optimizer.ConstraintRealization>
		constraintRealizationMap()
	{
		return _constraintRealizationMap;
	}

	/**
	 * Retrieve the Map of Objective Term Realizations
	 * 
	 * @return Map of Objective Term Realizations
	 */

	public java.util.Map<java.lang.String, java.lang.Double> objectiveTermRealizationMap()
	{
		return _objectiveTermRealizationMap;
	}
}
