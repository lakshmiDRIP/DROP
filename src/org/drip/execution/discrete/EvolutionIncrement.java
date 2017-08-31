
package org.drip.execution.discrete;

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
 * EvolutionIncrement contains the Realized Stochastic Evolution Increments of the Price/Short-fall exhibited
 *  by an Asset owing to the Volatility and the Market Impact Factors over the Slice Time Interval. It is
 *  composed of Stochastic and Deterministic Price Increment Components. The References are:
 * 
 * 	- Almgren, R., and N. Chriss (1999): Value under Liquidation, Risk 12 (12).
 * 
 * 	- Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3 (2)
 * 		5-39.
 * 
 * 	- Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs, Journal of Financial Markets,
 * 		1, 1-50.
 *
 * 	- Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional Trades,
 * 		Journal of Finance, 50, 1147-1174.
 *
 * 	- Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange
 * 		Analysis of Institutional Equity Trades, Journal of Financial Economics, 46, 265-292.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class EvolutionIncrement extends org.drip.execution.evolution.MarketImpactComposite {

	/**
	 * EvolutionIncrement Constructor
	 * 
	 * @param micDeterministic The Deterministic Market Impact Component Instance
	 * @param micStochastic The Stochastic Market Impact Component Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EvolutionIncrement (
		final org.drip.execution.evolution.MarketImpactComponent micDeterministic,
		final org.drip.execution.evolution.MarketImpactComponent micStochastic)
		throws java.lang.Exception
	{
		super (micDeterministic, micStochastic);
	}

	/**
	 * Retrieve the Change induced by Deterministic Asset Price Market Dynamic Drivers
	 * 
	 * @return The Change induced by Deterministic Asset Price Market Dynamic Drivers
	 */

	public double marketDynamicDrift()
	{
		org.drip.execution.evolution.MarketImpactComponent micDeterministic = deterministic();

		return micDeterministic.previousStep() + micDeterministic.currentStep();
	}

	/**
	 * Retrieve the Change induced by Stochastic Asset Price Market Dynamic Drivers
	 * 
	 * @return The Change induced by Stochastic Asset Price Market Dynamic Drivers
	 */

	public double marketDynamicWander()
	{
		org.drip.execution.evolution.MarketImpactComponent micStochastic = stochastic();

		return micStochastic.previousStep() + micStochastic.currentStep();
	}

	/**
	 * Retrieve the Change induced by the Deterministic Asset Price Permanent Market Impact Drivers
	 * 
	 * @return The Change induced by the Deterministic Asset Price Permanent Market Impact Drivers
	 */

	public double permanentImpactDrift()
	{
		return deterministic().permanentImpact();
	}

	/**
	 * Retrieve the Change induced by the Stochastic Asset Price Permanent Market Impact Drivers
	 * 
	 * @return The Change induced by the Stochastic Asset Price Permanent Market Impact Drivers
	 */

	public double permanentImpactWander()
	{
		return stochastic().permanentImpact();
	}

	/**
	 * Retrieve the Change induced by the Deterministic Asset Price Temporary Market Impact Drivers
	 * 
	 * @return The Change induced by the Deterministic Asset Price Temporary Market Impact Drivers
	 */

	public double temporaryImpactDrift()
	{
		return deterministic().temporaryImpact();
	}

	/**
	 * Retrieve the Change induced by the Stochastic Asset Price Temporary Market Impact Drivers
	 * 
	 * @return The Change induced by the Stochastic Asset Price Temporary Market Impact Drivers
	 */

	public double temporaryImpactWander()
	{
		return stochastic().temporaryImpact();
	}
}
