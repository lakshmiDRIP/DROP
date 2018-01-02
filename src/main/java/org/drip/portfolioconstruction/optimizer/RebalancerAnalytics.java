
package org.drip.portfolioconstruction.optimizer;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * RebalancerAnalytics holds the Analytics from a given Rebalancing Run.
 *
 * @author Lakshmi Krishnamurthy
 */

public class RebalancerAnalytics
{
	private double _dblObjectiveValue = java.lang.Double.NaN;
	private org.drip.portfolioconstruction.composite.Holdings _holdingsFinal = null;
	private org.drip.portfolioconstruction.asset.PortfolioMetrics _portfolioMetrics = null;
	private org.drip.portfolioconstruction.asset.PortfolioBenchmarkMetrics _portfolioBenchmarkMetrics = null;
	private org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double> _mapObjectiveTermRealization
		= null;
	private
		org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.optimizer.ConstraintRealization>
			_mapConstraintRealization = null;

	/**
	 * RebalancerAnalytics Constructor
	 * 
	 * @param dblObjectiveValue The Objective Value
	 * @param holdingsFinal The Final Holdings
	 * @param mapObjectiveTermRealization Map of the Realized Objective Terms
	 * @param mapConstraintRealization Map of the Constraint Terms
	 * @param portfolioMetrics Portfolio Metrics
	 * @param portfolioBenchmarkMetrics Portfolio Benchmark Metrics
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public RebalancerAnalytics (
		final double dblObjectiveValue,
		final org.drip.portfolioconstruction.composite.Holdings holdingsFinal,
		final org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>
			mapObjectiveTermRealization,
		final
			org.drip.analytics.support.CaseInsensitiveHashMap<org.drip.portfolioconstruction.optimizer.ConstraintRealization>
				mapConstraintRealization,
		final org.drip.portfolioconstruction.asset.PortfolioMetrics portfolioMetrics,
		final org.drip.portfolioconstruction.asset.PortfolioBenchmarkMetrics portfolioBenchmarkMetrics)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblObjectiveValue = dblObjectiveValue) ||
			null == (_holdingsFinal = holdingsFinal) ||
			null == (_mapObjectiveTermRealization = mapObjectiveTermRealization) ||
			null == (_mapConstraintRealization = mapConstraintRealization))
			throw new java.lang.Exception ("RebalancerAnalytics Constructor => Invalid Inputs!");

		_portfolioMetrics = portfolioMetrics;
		_portfolioBenchmarkMetrics = portfolioBenchmarkMetrics;
	}

	/**
	 * Retrieve the Objective Term
	 * 
	 * @return Objective Term
	 */

	public double objectiveValue()
	{
		return _dblObjectiveValue;
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
		return _holdingsFinal;
	}

	/**
	 * Retrieve the Map of Constraint Realizations
	 * 
	 * @return Map of Constraint Realizations
	 */

	public java.util.Map<java.lang.String, org.drip.portfolioconstruction.optimizer.ConstraintRealization>
		constraintRealizaton()
	{
		return _mapConstraintRealization;
	}

	/**
	 * Retrieve the Map of Objective Term Realizations
	 * 
	 * @return Map of Objective Term Realizations
	 */

	public java.util.Map<java.lang.String, java.lang.Double> objectiveTermRealizaton()
	{
		return _mapObjectiveTermRealization;
	}
}
