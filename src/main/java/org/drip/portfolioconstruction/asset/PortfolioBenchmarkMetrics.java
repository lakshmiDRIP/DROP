
package org.drip.portfolioconstruction.asset;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AssetAllocationAnalyticsLibrary.md">Asset Allocation Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction">Portfolio Construction</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/portfolioconstruction/asset">Asset</a></li>
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
