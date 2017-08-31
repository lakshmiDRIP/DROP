
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
 * CoordinatedVariationRollingHorizon implements the "Rolling Horizon" Approximation of the Optimal Cost
 *  Dynamic Trajectory arising from the Coordinated Variation Version of the Stochastic Volatility and the
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

public class CoordinatedVariationRollingHorizon extends
	org.drip.execution.adaptive.CoordinatedVariationTrajectory {
	private double[] _adblNonDimensionalCost = null;
	private double[] _adblNonDimensionalHoldings = null;
	private double[] _adblNonDimensionalTradeRate = null;

	/**
	 * CoordinatedVariationRollingHorizon Constructor
	 * 
	 * @param cvtd The Coordinated Variation Trajectory Determinant 
	 * @param adblNonDimensionalHoldings The Array of the Non Dimensional Holdings
	 * @param adblNonDimensionalTradeRate The Array of the Non Dimensional Trade Rate
	 * @param adblNonDimensionalCost The Array of the Non Dimensional Cost
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CoordinatedVariationRollingHorizon (
		final org.drip.execution.adaptive.CoordinatedVariationTrajectoryDeterminant cvtd,
		final double[] adblNonDimensionalHoldings,
		final double[] adblNonDimensionalTradeRate,
		final double[] adblNonDimensionalCost)
		throws java.lang.Exception
	{
		super (cvtd);

		if (null == (_adblNonDimensionalHoldings = adblNonDimensionalHoldings) || null ==
			(_adblNonDimensionalTradeRate = adblNonDimensionalTradeRate) || null == (_adblNonDimensionalCost
				= adblNonDimensionalCost) || !org.drip.quant.common.NumberUtil.IsValid
					(_adblNonDimensionalHoldings) || !org.drip.quant.common.NumberUtil.IsValid
						(_adblNonDimensionalTradeRate) || !org.drip.quant.common.NumberUtil.IsValid
							(_adblNonDimensionalCost))
			throw new java.lang.Exception
				("CoordinatedVariationRollingHorizon Constructor => Invalid Inputs");

		int iNumTimeNode = _adblNonDimensionalHoldings.length;

		if (0 == iNumTimeNode || iNumTimeNode != _adblNonDimensionalTradeRate.length)
			throw new java.lang.Exception
				("CoordinatedVariationRollingHorizon Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Array of the Non Dimensional Holdings
	 * 
	 * @return The Array of the Non Dimensional Holdings
	 */

	public double[] nonDimensionalHoldings()
	{
		return _adblNonDimensionalHoldings;
	}

	/**
	 * Retrieve the Array of the Non Dimensional Trade Rate
	 * 
	 * @return The Array of the Non Dimensional Trade Rate
	 */

	public double[] nonDimensionalTradeRate()
	{
		return _adblNonDimensionalTradeRate;
	}

	/**
	 * Retrieve the Array of the Non Dimensional Cost
	 * 
	 * @return The Array of the Non Dimensional Cost
	 */

	public double[] nonDimensionalCost()
	{
		return _adblNonDimensionalCost;
	}
}
