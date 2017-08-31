
package org.drip.execution.evolution;

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
 * MarketImpactComponent exposes the Evolution Increment Components of the Movements exhibited by an Asset's
 *  Manifest Measures owing to either Stochastic or Deterministic Factors. The References are:
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

public class MarketImpactComponent {
	private double _dblCurrentStep = java.lang.Double.NaN;
	private double _dblPreviousStep = java.lang.Double.NaN;
	private double _dblPermanentImpact = java.lang.Double.NaN;
	private double _dblTemporaryImpact = java.lang.Double.NaN;

	/**
	 * MarketImpactComponent Constructor
	 * 
	 * @param dblCurrentStep The Current Step Volatility Component Contribution
	 * @param dblPreviousStep The Previous Step Volatility Component Contribution
	 * @param dblPermanentImpact The Permanent Market Impact Contribution
	 * @param dblTemporaryImpact The Temporary Market Impact Contribution
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MarketImpactComponent (
		final double dblCurrentStep,
		final double dblPreviousStep,
		final double dblPermanentImpact,
		final double dblTemporaryImpact)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblCurrentStep = dblCurrentStep) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblPreviousStep = dblPreviousStep) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblPermanentImpact = dblPermanentImpact) ||
					!org.drip.quant.common.NumberUtil.IsValid (_dblTemporaryImpact = dblTemporaryImpact))
			throw new java.lang.Exception ("MarketImpactComponent Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Previous Step Contribution
	 * 
	 * @return The Previous Step Contribution
	 */

	public double previousStep()
	{
		return _dblPreviousStep;
	}

	/**
	 * Retrieve the Current Step Contribution
	 * 
	 * @return The Current Step Contribution
	 */

	public double currentStep()
	{
		return _dblCurrentStep;
	}

	/**
	 * Retrieve the Permanent Market Impact Contribution
	 * 
	 * @return The Permanent Market Impact Contribution
	 */

	public double permanentImpact()
	{
		return _dblPermanentImpact;
	}

	/**
	 * Retrieve the Temporary Market Impact Contribution
	 * 
	 * @return The Temporary Market Impact Contribution
	 */

	public double temporaryImpact()
	{
		return _dblTemporaryImpact;
	}

	/**
	 * Retrieve the Total Component Impact
	 * 
	 * @return The Total Component Impact
	 */

	public double total()
	{
		return previousStep() + currentStep() + permanentImpact() + temporaryImpact();
	}
}
