
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
 * ShortfallIncrement generates the Realized Incremental Stochastic Trading/Execution Short-fall and the
 *  corresponding Implementation Short-fall corresponding to the Trajectory of a Holdings Block that is to be
 *  executed over Time. The References are:
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
 * 	- Perold, A. F. (1988): The Implementation Short-fall: Paper versus Reality, Journal of Portfolio
 * 		Management, 14, 4-9.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ShortfallIncrement extends org.drip.execution.discrete.EvolutionIncrement {
	private org.drip.execution.discrete.PriceIncrement _pi = null;

	/**
	 * Generate a Standard ShortfallIncrement Instance
	 * 
	 * @param pi The Composite Slice Price Increment
	 * @param dblPreviousHoldings The Previous Holdings
	 * @param dblHoldingsIncrement The Holdings Increment
	 * 
	 * @return The Standard ShortfallIncrement Instance
	 */

	public static final ShortfallIncrement Standard (
		final org.drip.execution.discrete.PriceIncrement pi,
		final double dblPreviousHoldings,
		final double dblHoldingsIncrement)
	{
		if (null == pi || !org.drip.quant.common.NumberUtil.IsValid (dblPreviousHoldings) ||
			!org.drip.quant.common.NumberUtil.IsValid (dblHoldingsIncrement))
			return null;

		double dblCurrentHoldings = dblPreviousHoldings + dblHoldingsIncrement;

		try {
			org.drip.execution.evolution.MarketImpactComponent micStochastic = pi.stochastic();

			org.drip.execution.evolution.MarketImpactComponent micDeterministic = pi.deterministic();

			return new ShortfallIncrement (
				new org.drip.execution.evolution.MarketImpactComponent (
					micDeterministic.currentStep() * dblCurrentHoldings,
					micDeterministic.previousStep() * dblCurrentHoldings,
					pi.permanentImpactDrift() * dblCurrentHoldings,
					pi.temporaryImpactDrift() * dblHoldingsIncrement
				),
				new org.drip.execution.evolution.MarketImpactComponent (
					micStochastic.currentStep() * dblCurrentHoldings,
					micStochastic.previousStep() * dblCurrentHoldings,
					pi.permanentImpactWander() * dblCurrentHoldings,
					pi.temporaryImpactWander() * dblHoldingsIncrement
				),
				pi
			);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private ShortfallIncrement (
		final org.drip.execution.evolution.MarketImpactComponent micDeterministic,
		final org.drip.execution.evolution.MarketImpactComponent micStochastic,
		final org.drip.execution.discrete.PriceIncrement pi)
		throws java.lang.Exception
	{
		super (micDeterministic, micStochastic);

		_pi = pi;
	}

	/**
	 * Compute the Implementation Short-fall
	 * 
	 * @return The Implementation Short-fall
	 */

	public double implementationShortfall()
	{
		return total();
	}

	/**
	 * Retrieve the Composite Price Increment Instance
	 * 
	 * @return The Composite Price Increment Instance
	 */

	public org.drip.execution.discrete.PriceIncrement compositePriceIncrement()
	{
		return _pi;
	}
}
