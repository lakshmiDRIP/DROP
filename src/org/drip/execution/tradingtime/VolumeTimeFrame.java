
package org.drip.execution.tradingtime;

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
 * VolumeTimeFrame implements the Pre- and Post-transformed Increment in the Volume Time Space as used in the
 *  "Trading Time" Model. The References are:
 * 
 * 	- Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3
 * 		(2) 5-39.
 *
 * 	- Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 * 		https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf.
 *
 * 	- Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility, SIAM Journal of
 * 		Financial Mathematics  3 (1) 163-181.
 * 
 * 	- Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes, Mathematical Finance 11 (1)
 * 		79-96.
 * 
 * 	- Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility, Review of
 * 		Financial Studies 7 (4) 631-651.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class VolumeTimeFrame extends org.drip.measure.realization.JumpDiffusionEdge {
	private double _dblHoldings = java.lang.Double.NaN;
	private double _dblTradeRate = java.lang.Double.NaN;

	/**
	 * VolumeTimeFrame Constructor
	 * 
	 * @param dblTimeIncrement Time Increment
	 * @param dblPrevious The Previous Realization
	 * @param dblTemporal The Temporal Increment
	 * @param dblBrownian The Brownian Increment
	 * @param dblVolatility The Volatility
	 * @param dblHoldings Current Holdings
	 * @param dblTradeRate Current Trade Rate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public VolumeTimeFrame (
		final double dblTimeIncrement,
		final double dblPrevious,
		final double dblTemporal,
		final double dblBrownian,
		final double dblVolatility,
		final double dblHoldings,
		final double dblTradeRate)
		throws java.lang.Exception
	{
		super (dblPrevious, dblVolatility * dblVolatility * dblTemporal, new
			org.drip.measure.realization.StochasticEdgeDiffusion (dblVolatility * dblBrownian), null, new
				org.drip.measure.realization.JumpDiffusionEdgeUnit (dblTimeIncrement, dblBrownian, 0.));

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblHoldings = dblHoldings) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblTradeRate = dblTradeRate / (dblVolatility *
				dblVolatility)))
			throw new java.lang.Exception ("VolumeTimeFrame Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Holdings
	 * 
	 * @return The Holdings
	 */

	public double holdings()
	{
		return _dblHoldings;
	}

	/**
	 * Retrieve the Trade Rate
	 * 
	 * @return The Trade Rate
	 */

	public double tradeRate()
	{
		return _dblTradeRate;
	}

	/**
	 * Generate the Transaction Cost Increment
	 * 
	 * @param cv The Coordinated Variation Parameters
	 * 
	 * @return The Transaction Cost Increment
	 * 
	 * @throws java.lang.Exception Throw if the Inputs are Invalid
	 */

	public double transactionCostIncrement (
		final org.drip.execution.tradingtime.CoordinatedVariation cv)
		throws java.lang.Exception
	{
		if (null == cv)
			throw new java.lang.Exception ("VolumeTimeFrame::transactionCostIncrement => Invalid Inputs");

		return _dblHoldings * diffusionStochastic() + cv.invariant() * _dblTradeRate * _dblTradeRate *
			deterministic();
	}
}
