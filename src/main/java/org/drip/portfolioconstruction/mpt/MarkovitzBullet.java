
package org.drip.portfolioconstruction.mpt;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <i>MarkovitzBullet</i> holds the Portfolio Performance Metrics across a Variety of Return Constraints.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/mpt">Modern Portfolio Theory</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class MarkovitzBullet
{
	private org.drip.portfolioconstruction.allocator.OptimizationOutput
		_globalMinimumVarianceOptimizationOutput = null;
	private org.drip.portfolioconstruction.allocator.OptimizationOutput
		_longOnlyMaximumReturnsOptimizationOutput = null;

	private java.util.TreeMap<java.lang.Double, org.drip.portfolioconstruction.allocator.OptimizationOutput>
		_optimalPortfolioMap = new java.util.TreeMap<java.lang.Double,
			org.drip.portfolioconstruction.allocator.OptimizationOutput>();

	/**
	 * MarkovitzBullet Constructor
	 * 
	 * @param globalMinimumVarianceOptimizationOutput The Global Minimum Variance Optimal Portfolio
	 * @param longOnlyMaximumReturnsOptimizationOutput The Long Only Maximum Returns Optimal Portfolio
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarkovitzBullet (
		final org.drip.portfolioconstruction.allocator.OptimizationOutput
			globalMinimumVarianceOptimizationOutput,
		final org.drip.portfolioconstruction.allocator.OptimizationOutput
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

	public org.drip.portfolioconstruction.allocator.OptimizationOutput globalMinimumVariance()
	{
		return _globalMinimumVarianceOptimizationOutput;
	}

	/**
	 * Retrieve the Long Only Maximum Returns Portfolio Metrics
	 * 
	 * @return The Long Only Maximum Returns Portfolio Metrics
	 */

	public org.drip.portfolioconstruction.allocator.OptimizationOutput longOnlyMaximumReturns()
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
		final org.drip.portfolioconstruction.allocator.OptimizationOutput optimizationOutput)
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

	public java.util.TreeMap<java.lang.Double, org.drip.portfolioconstruction.allocator.OptimizationOutput>
		optimalPortfolioMap()
	{
		return _optimalPortfolioMap;
	}
}
