
package org.drip.portfolioconstruction.asset;

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
 * <i>PortfolioBenchmarkMetrics</i> holds the Metrics that result from a Relative Valuation of a Portfolio
 * with respect to a Benchmark.
 *  
 *  <br><br>
 *  	<ul>
 *  		<li>
 *  			Grinold, R. C., and R. N. Kahn (1999): <i>Active Portfolio Management, 2nd Edition</i>
 *  				<b>McGraw-Hill</b> NY
 *  		</li>
 *  		<li>
 *  			Idzorek, T. (2005): <i>A Step-by-Step Guide to the Black-Litterman Model: Incorporating
 *  				User-Specified Confidence Levels</i> <b>Ibbotson Associates</b> Chicago, IL
 *  		</li>
 *  	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/README.md">Portfolio Construction under Allocation Constraints</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/asset/README.md">Asset Characteristics, Bounds, Portfolio Benchmarks</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class PortfolioBenchmarkMetrics
{
	private double _beta = java.lang.Double.NaN;
	private double _activeBeta = java.lang.Double.NaN;
	private double _activeRisk = java.lang.Double.NaN;
	private double _activeReturn = java.lang.Double.NaN;
	private double _residualRisk = java.lang.Double.NaN;
	private double _residualReturn = java.lang.Double.NaN;

	/**
	 * PortfolioBenchmarkMetrics Constructor
	 * 
	 * @param beta Portfolio-to-Benchmark Beta
	 * @param activeBeta Portfolio-to-Benchmark Active Beta
	 * @param activeRisk Portfolio-to-Benchmark Active Risk
	 * @param activeReturn Portfolio-to-Benchmark Active Return
	 * @param residualRisk Portfolio-to-Benchmark Residual Risk
	 * @param residualReturn Portfolio-to-Benchmark Residual Return
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PortfolioBenchmarkMetrics (
		final double beta,
		final double activeBeta,
		final double activeRisk,
		final double activeReturn,
		final double residualRisk,
		final double residualReturn)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_beta = beta) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_activeBeta = activeBeta) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_activeRisk = activeRisk) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_activeReturn = activeReturn) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_residualRisk = residualRisk) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_residualReturn = residualReturn))
		{
			throw new java.lang.Exception ("PortfolioBenchmarkMetrics Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Portfolio-to-Benchmark Beta
	 * 
	 * @return The Portfolio-to-Benchmark Beta
	 */

	public double beta()
	{
		return _beta;
	}

	/**
	 * Retrieve the Portfolio-to-Benchmark Active Beta
	 * 
	 * @return The Portfolio-to-Benchmark Active Beta
	 */

	public double activeBeta()
	{
		return _activeBeta;
	}

	/**
	 * Retrieve the Portfolio-to-Benchmark Active Risk
	 * 
	 * @return The Portfolio-to-Benchmark Active Risk
	 */

	public double activeRisk()
	{
		return _activeRisk;
	}

	/**
	 * Retrieve the Portfolio-to-Benchmark Active Return
	 * 
	 * @return The Portfolio-to-Benchmark Active Return
	 */

	public double activeReturn()
	{
		return _activeReturn;
	}

	/**
	 * Retrieve the Portfolio-to-Benchmark Residual Risk
	 * 
	 * @return The Portfolio-to-Benchmark Residual Risk
	 */

	public double residualRisk()
	{
		return _residualRisk;
	}

	/**
	 * Retrieve the Portfolio-to-Benchmark Residual Return
	 * 
	 * @return The Portfolio-to-Benchmark Residual Return
	 */

	public double residualReturn()
	{
		return _residualReturn;
	}
}
