
package org.drip.execution.adaptive;

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
 * CoordinatedVariationTrajectoryDeterminant contains the HJB-based MultiStep Optimal Cost Dynamic Trajectory
 *  Generation Metrics using the Coordinated Variation Version of the Stochastic Volatility and the
 *  Transaction Function arising from the Realization of the Market State Variable as described in the
 *  "Trading Time" Model. The References are:
 * 
 * 	- Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3
 * 		(2) 5-39.
 *
 * 	- Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 * 		https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf.
 *
 * 	- Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility, SIAM Journal of
 * 		Financial Mathematics 3 (1) 163-181.
 * 
 * 	- Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes, Mathematical Finance 11 (1)
 * 		79-96.
 * 
 * 	- Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility, Review of
 * 		Financial Studies 7 (4) 631-651.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CoordinatedVariationTrajectoryDeterminant {
	private double _dblOrderSize = java.lang.Double.NaN;
	private double _dblCostScale = java.lang.Double.NaN;
	private double _dblTimeScale = java.lang.Double.NaN;
	private double _dblMarketPower = java.lang.Double.NaN;
	private double _dblTradeRateScale = java.lang.Double.NaN;
	private double _dblMeanMarketUrgency = java.lang.Double.NaN;
	private double _dblNonDimensionalRiskAversion = java.lang.Double.NaN;

	/**
	 * CoordinatedVariationTrajectoryDeterminant Constructor
	 * 
	 * @param dblOrderSize The Order Size
	 * @param dblTimeScale The Time Scale
	 * @param dblCostScale The Cost Scale
	 * @param dblTradeRateScale The Trade Rate Scale
	 * @param dblMeanMarketUrgency The Mean Market Urgency
	 * @param dblNonDimensionalRiskAversion The Non Dimensional Risk Aversion Parameter
	 * @param dblMarketPower The Preference-free "Market Power" Parameter
	 * 
	 * @throws java.lang.Exception Thrown if the the Inputs are Invalid
	 */

	public CoordinatedVariationTrajectoryDeterminant (
		final double dblOrderSize,
		final double dblTimeScale,
		final double dblCostScale,
		final double dblTradeRateScale,
		final double dblMeanMarketUrgency,
		final double dblNonDimensionalRiskAversion,
		final double dblMarketPower)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblOrderSize = dblOrderSize) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblTimeScale = dblTimeScale) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblCostScale = dblCostScale) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblTradeRateScale = dblTradeRateScale) ||
						!org.drip.quant.common.NumberUtil.IsValid (_dblMeanMarketUrgency =
							dblMeanMarketUrgency) || !org.drip.quant.common.NumberUtil.IsValid
								(_dblNonDimensionalRiskAversion = dblNonDimensionalRiskAversion) ||
									!org.drip.quant.common.NumberUtil.IsValid (_dblMarketPower =
										dblMarketPower))
			throw new java.lang.Exception
				("CoordinatedVariationTrajectoryDeterminant Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Order Size
	 * 
	 * @return The Order Size
	 */

	public double orderSize()
	{
		return _dblOrderSize;
	}

	/**
	 * Retrieve the Time Scale
	 * 
	 * @return The Time Scale
	 */

	public double timeScale()
	{
		return _dblTimeScale;
	}

	/**
	 * Retrieve the Cost Scale
	 * 
	 * @return The Cost Scale
	 */

	public double costScale()
	{
		return _dblCostScale;
	}

	/**
	 * Retrieve the Trade Rate Scale
	 * 
	 * @return The Trade Rate Scale
	 */

	public double tradeRateScale()
	{
		return _dblTradeRateScale;
	}

	/**
	 * Retrieve the Mean Market Urgency
	 * 
	 * @return The Mean Market Urgency
	 */

	public double meanMarketUrgency()
	{
		return _dblMeanMarketUrgency;
	}

	/**
	 * Retrieve the Non Dimensional Risk Aversion Parameter
	 * 
	 * @return The Non Dimensional Risk Aversion Parameter
	 */

	public double nonDimensionalRiskAversion()
	{
		return _dblNonDimensionalRiskAversion;
	}

	/**
	 * Retrieve the Preference-free "Market Power" Parameter
	 * 
	 * @return The Preference-free "Market Power" Parameter
	 */

	public double marketPower()
	{
		return _dblMarketPower;
	}
}
