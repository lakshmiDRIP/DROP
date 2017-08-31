
package org.drip.portfolioconstruction.asset;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * PortfolioBenchmarkMetrics holds the Metrics that result from a Relative Valuation of a Portfolio with
 *  respect to a Benchmark.
 *  
 *  - Grinold, R. C., and R. N. Kahn (1999): Active Portfolio Management, 2nd Edition, McGraw-Hill, NY.
 *  
 *  - Idzorek, T. (2005): A Step-by-Step Guide to the Black-Litterman Model: Incorporating User-Specified
 *  	Confidence Levels, Ibbotson Associates, Chicago
 *
 * @author Lakshmi Krishnamurthy
 */

public class PortfolioBenchmarkMetrics {
	private double _dblBeta = java.lang.Double.NaN;
	private double _dblActiveBeta = java.lang.Double.NaN;
	private double _dblActiveRisk = java.lang.Double.NaN;
	private double _dblActiveReturn = java.lang.Double.NaN;
	private double _dblResidualRisk = java.lang.Double.NaN;
	private double _dblResidualReturn = java.lang.Double.NaN;

	/**
	 * PortfolioBenchmarkMetrics Constructor
	 * 
	 * @param dblBeta Portfolio-to-Benchmark Beta
	 * @param dblActiveBeta Portfolio-to-Benchmark Active Beta
	 * @param dblActiveRisk Portfolio-to-Benchmark Active Risk
	 * @param dblActiveReturn Portfolio-to-Benchmark Active Return
	 * @param dblResidualRisk Portfolio-to-Benchmark Residual Risk
	 * @param dblResidualReturn Portfolio-to-Benchmark Residual Return
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PortfolioBenchmarkMetrics (
		final double dblBeta,
		final double dblActiveBeta,
		final double dblActiveRisk,
		final double dblActiveReturn,
		final double dblResidualRisk,
		final double dblResidualReturn)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblBeta = dblBeta) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblActiveBeta = dblActiveBeta) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblActiveRisk = dblActiveRisk) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblActiveReturn = dblActiveReturn) ||
						!org.drip.quant.common.NumberUtil.IsValid (_dblResidualRisk = dblResidualRisk) ||
							!org.drip.quant.common.NumberUtil.IsValid (_dblResidualReturn =
								dblResidualReturn))
			throw new java.lang.Exception ("PortfolioBenchmarkMetrics Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Portfolio-to-Benchmark Beta
	 * 
	 * @return The Portfolio-to-Benchmark Beta
	 */

	public double beta()
	{
		return _dblBeta;
	}

	/**
	 * Retrieve the Portfolio-to-Benchmark Active Beta
	 * 
	 * @return The Portfolio-to-Benchmark Active Beta
	 */

	public double activeBeta()
	{
		return _dblActiveBeta;
	}

	/**
	 * Retrieve the Portfolio-to-Benchmark Active Risk
	 * 
	 * @return The Portfolio-to-Benchmark Active Risk
	 */

	public double activeRisk()
	{
		return _dblActiveRisk;
	}

	/**
	 * Retrieve the Portfolio-to-Benchmark Active Return
	 * 
	 * @return The Portfolio-to-Benchmark Active Return
	 */

	public double activeReturn()
	{
		return _dblActiveReturn;
	}

	/**
	 * Retrieve the Portfolio-to-Benchmark Residual Risk
	 * 
	 * @return The Portfolio-to-Benchmark Residual Risk
	 */

	public double residualRisk()
	{
		return _dblResidualRisk;
	}

	/**
	 * Retrieve the Portfolio-to-Benchmark Residual Return
	 * 
	 * @return The Portfolio-to-Benchmark Residual Return
	 */

	public double residualReturn()
	{
		return _dblResidualReturn;
	}
}
