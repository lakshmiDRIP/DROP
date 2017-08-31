
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
 * ShortfallIncrementDistribution holds the Parameters of the R^1 Normal Short fall Increment Distribution.
 *  The References are:
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

public class ShortfallIncrementDistribution extends org.drip.measure.gaussian.R1UnivariateNormal {
	public double _dblMarketDynamicVariance = java.lang.Double.NaN;
	public double _dblPermanentImpactVariance = java.lang.Double.NaN;
	public double _dblTemporaryImpactVariance = java.lang.Double.NaN;
	public double _dblMarketDynamicExpectation = java.lang.Double.NaN;
	public double _dblPermanentImpactExpectation = java.lang.Double.NaN;
	public double _dblTemporaryImpactExpectation = java.lang.Double.NaN;

	/**
	 * ShortfallIncrementDistribution Constructor
	 * 
	 * @param dblPermanentImpactExpectation The Permanent Market Impact Expectation Component
	 * @param dblTemporaryImpactExpectation The Temporary Market Impact Expectation Component
	 * @param dblMarketDynamicExpectation The Market Dynamics Expectation Component
	 * @param dblPermanentImpactVariance The Permanent Market Impact Variance Component
	 * @param dblTemporaryImpactVariance The Temporary Market Impact Variance Component
	 * @param dblMarketDynamicVariance The Market Dynamics Variance Component
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ShortfallIncrementDistribution (
		final double dblPermanentImpactExpectation,
		final double dblTemporaryImpactExpectation,
		final double dblMarketDynamicExpectation,
		final double dblPermanentImpactVariance,
		final double dblTemporaryImpactVariance,
		final double dblMarketDynamicVariance)
		throws java.lang.Exception
	{
		super (dblPermanentImpactExpectation + dblTemporaryImpactExpectation + dblMarketDynamicExpectation,
			java.lang.Math.sqrt (dblPermanentImpactVariance + dblTemporaryImpactVariance +
				dblMarketDynamicVariance));

		_dblPermanentImpactExpectation = dblPermanentImpactExpectation;
		_dblTemporaryImpactExpectation = dblTemporaryImpactExpectation;
		_dblMarketDynamicExpectation = dblMarketDynamicExpectation;
		_dblPermanentImpactVariance = dblPermanentImpactVariance;
		_dblTemporaryImpactVariance = dblTemporaryImpactVariance;
		_dblMarketDynamicVariance = dblMarketDynamicVariance;
	}

	/**
	 * Retrieve the Total Expectation
	 * 
	 * @return The Total Expectation
	 */

	public double expectation()
	{
		return mean();
	}

	/**
	 * Retrieve the Market Dynamic Expectation Component
	 * 
	 * @return The Market Dynamic Expectation Component
	 */

	public double marketDynamicExpectation()
	{
		return _dblMarketDynamicExpectation;
	}

	/**
	 * Retrieve the Market Dynamic Variance Component
	 * 
	 * @return The Market Dynamic Variance Component
	 */

	public double marketDynamicVariance()
	{
		return _dblMarketDynamicVariance;
	}

	/**
	 * Retrieve the Permanent Market Impact Expectation Component
	 * 
	 * @return The Permanent Market Impact Expectation Component
	 */

	public double permanentImpactExpectation()
	{
		return _dblPermanentImpactExpectation;
	}

	/**
	 * Retrieve the Permanent Market Impact Variance Component
	 * 
	 * @return The Permanent Market Impact Variance Component
	 */

	public double permanentImpactVariance()
	{
		return _dblPermanentImpactVariance;
	}

	/**
	 * Retrieve the Temporary Market Impact Expectation Component
	 * 
	 * @return The Temporary Market Impact Expectation Component
	 */

	public double temporaryImpactExpectation()
	{
		return _dblTemporaryImpactExpectation;
	}

	/**
	 * Retrieve the Temporary Market Impact Variance Component
	 * 
	 * @return The Temporary Market Impact Variance Component
	 */

	public double temporaryImpactVariance()
	{
		return _dblTemporaryImpactVariance;
	}
}
