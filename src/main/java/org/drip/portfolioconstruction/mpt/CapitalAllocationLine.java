
package org.drip.portfolioconstruction.mpt;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>CapitalAllocationLine</i> implements the Efficient Half-line created from the Combination of the Risk
 * Free Asset and the Tangency Point of the CAPM Market Portfolio.
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

public class CapitalAllocationLine
{
	private double _riskFreeRate = java.lang.Double.NaN;
	private org.drip.portfolioconstruction.asset.PortfolioMetrics _tangencyPortfolioMetrics = null;

	/**
	 * CapitalAllocationLine Constructor
	 * 
	 * @param riskFreeRate The Risk Free Rate
	 * @param tangencyPortfolioMetrics The Tangency Portfolio Metrics
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CapitalAllocationLine (
		final double riskFreeRate,
		final org.drip.portfolioconstruction.asset.PortfolioMetrics tangencyPortfolioMetrics)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_riskFreeRate = riskFreeRate) ||
			null == (_tangencyPortfolioMetrics = tangencyPortfolioMetrics))
		{
			throw new java.lang.Exception ("CapitalAllocationLine Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Risk-Free Rate
	 * 
	 * @return The Risk-Free Rate
	 */

	public double riskFreeRate()
	{
		return _riskFreeRate;
	}

	/**
	 * Retrieve the Tangency Portfolio Metrics
	 * 
	 * @return The Tangency Portfolio Metrics
	 */

	public org.drip.portfolioconstruction.asset.PortfolioMetrics tangencyPortfolioMetrics()
	{
		return _tangencyPortfolioMetrics;
	}

	/**
	 * Calculate the Combination Portfolio's Expected Returns from the corresponding Standard Deviation
	 * 
	 * @param combinationPortfolioStandardDeviation The Combination Portfolio's Standard Deviation
	 * 
	 * @return The Combination Portfolio's Expected Returns
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double combinationPortfolioExpectedReturn (
		final double combinationPortfolioStandardDeviation)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (combinationPortfolioStandardDeviation))
		{
			throw new java.lang.Exception
				("CapitalAllocationLine::combinationPortfolioExpectedReturn => Invalid Inputs");
		}

		return _riskFreeRate + combinationPortfolioStandardDeviation * (
			_tangencyPortfolioMetrics.excessReturnsMean() - _riskFreeRate
		) / _tangencyPortfolioMetrics.excessReturnsStandardDeviation();
	}

	/**
	 * Compute the Combination Portfolio's Standard Deviation
	 * 
	 * @param combinationPortfolioExpectedReturn The Expected Returns of the Combination Portfolio
	 * 
	 * @return The Combination Portfolio's Standard Deviation
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double combinationPortfolioStandardDeviation (
		final double combinationPortfolioExpectedReturn)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (combinationPortfolioExpectedReturn))
		{
			throw new java.lang.Exception
				("CapitalAllocationLine::combinationPortfolioStandardDeviation => Invalid Inputs");
		}

		return (combinationPortfolioExpectedReturn - _riskFreeRate) / (
			_tangencyPortfolioMetrics.excessReturnsMean() - _riskFreeRate
		) * _tangencyPortfolioMetrics.excessReturnsStandardDeviation();
	}
}
