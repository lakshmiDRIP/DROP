
package org.drip.portfolioconstruction.mpt;

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
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>MarkovitzBullet</i> holds the Portfolio Performance Metrics across a Variety of Return Constraints.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/mpt/README.md">Security Characteristic Capital Allocation Lines</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MarkovitzBullet
{
	private org.drip.portfolioconstruction.allocator.HoldingsAllocation
		_globalMinimumVarianceOptimizationOutput = null;
	private org.drip.portfolioconstruction.allocator.HoldingsAllocation
		_longOnlyMaximumReturnsOptimizationOutput = null;

	private java.util.TreeMap<java.lang.Double, org.drip.portfolioconstruction.allocator.HoldingsAllocation>
		_optimalPortfolioMap = new java.util.TreeMap<java.lang.Double,
			org.drip.portfolioconstruction.allocator.HoldingsAllocation>();

	/**
	 * MarkovitzBullet Constructor
	 * 
	 * @param globalMinimumVarianceOptimizationOutput The Global Minimum Variance Optimal Portfolio
	 * @param longOnlyMaximumReturnsOptimizationOutput The Long Only Maximum Returns Optimal Portfolio
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarkovitzBullet (
		final org.drip.portfolioconstruction.allocator.HoldingsAllocation
			globalMinimumVarianceOptimizationOutput,
		final org.drip.portfolioconstruction.allocator.HoldingsAllocation
			longOnlyMaximumReturnsOptimizationOutput)
		throws java.lang.Exception
	{
		if (null == (_globalMinimumVarianceOptimizationOutput = globalMinimumVarianceOptimizationOutput) ||
			null == (_longOnlyMaximumReturnsOptimizationOutput = longOnlyMaximumReturnsOptimizationOutput))
		{
			throw new java.lang.Exception ("MarkovitzBullet Constructor => Invalid inputs");
		}

		_optimalPortfolioMap.put (
			_globalMinimumVarianceOptimizationOutput.optimalMetrics().excessReturnsMean(),
			_globalMinimumVarianceOptimizationOutput
		);

		_optimalPortfolioMap.put (
			_longOnlyMaximumReturnsOptimizationOutput.optimalMetrics().excessReturnsMean(),
			_longOnlyMaximumReturnsOptimizationOutput
		);
	}

	/**
	 * Retrieve the Global Minimum Variance Portfolio Metrics
	 * 
	 * @return The Global Minimum Variance Portfolio Metrics
	 */

	public org.drip.portfolioconstruction.allocator.HoldingsAllocation globalMinimumVariance()
	{
		return _globalMinimumVarianceOptimizationOutput;
	}

	/**
	 * Retrieve the Long Only Maximum Returns Portfolio Metrics
	 * 
	 * @return The Long Only Maximum Returns Portfolio Metrics
	 */

	public org.drip.portfolioconstruction.allocator.HoldingsAllocation longOnlyMaximumReturns()
	{
		return _longOnlyMaximumReturnsOptimizationOutput;
	}

	/**
	 * Add a Returns Constrained Optimal Portfolio
	 * 
	 * @param optimizationOutput The Returns Constrained Optimal Portfolio
	 * 
	 * @return TRUE - The Returns Constrained Optimal Portfolio Successfully Added
	 */

	public boolean addOptimalPortfolio (
		final org.drip.portfolioconstruction.allocator.HoldingsAllocation optimizationOutput)
	{
		if (null == optimizationOutput)
		{
			return false;
		}

		_optimalPortfolioMap.put (
			optimizationOutput.optimalMetrics().excessReturnsMean(),
			optimizationOutput
		);

		return true;
	}

	/**
	 * Retrieve the Map of Optimal Portfolios
	 * 
	 * @return The Map of Optimal Portfolios
	 */

	public java.util.TreeMap<java.lang.Double, org.drip.portfolioconstruction.allocator.HoldingsAllocation>
		optimalPortfolioMap()
	{
		return _optimalPortfolioMap;
	}
}
