
package org.drip.execution.dynamics;

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
 * ArithmeticPriceEvolutionParameters contains the Exogenous Parameters that determine the Dynamics of the
 *  Arithmetic Price Movements exhibited by an Asset owing to the Volatility and the Market Impact Factors.
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
 * @author Lakshmi Krishnamurthy
 */

public class ArithmeticPriceEvolutionParameters {
	private org.drip.execution.parameters.ArithmeticPriceDynamicsSettings _apds = null;
	private org.drip.execution.profiletime.BackgroundParticipationRate _bprPermanentVolatility = null;
	private org.drip.execution.profiletime.BackgroundParticipationRate _bprTemporaryVolatility = null;
	private org.drip.execution.profiletime.BackgroundParticipationRate _bprPermanentExpectation = null;
	private org.drip.execution.profiletime.BackgroundParticipationRate _bprTemporaryExpectation = null;

	/**
	 * ArithmeticPriceEvolutionParameters Constructor
	 * 
	 * @param apds The Asset Price Dynamics Settings
	 * @param bprPermanentExpectation The Background Participation Permanent Market Impact Expectation
	 * 		Function
	 * @param bprTemporaryExpectation The Background Participation Temporary Market Impact Expectation
	 * 		Function
	 * @param bprPermanentVolatility The Background Participation Permanent Market Impact Volatility Function
	 * @param bprTemporaryVolatility The Background Participation Temporary Market Impact Volatility Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public ArithmeticPriceEvolutionParameters (
		final org.drip.execution.parameters.ArithmeticPriceDynamicsSettings apds,
		final org.drip.execution.profiletime.BackgroundParticipationRate bprPermanentExpectation,
		final org.drip.execution.profiletime.BackgroundParticipationRate bprTemporaryExpectation,
		final org.drip.execution.profiletime.BackgroundParticipationRate bprPermanentVolatility,
		final org.drip.execution.profiletime.BackgroundParticipationRate bprTemporaryVolatility)
		throws java.lang.Exception
	{
		if (null == (_apds = apds) || null == (_bprPermanentExpectation = bprPermanentExpectation) || null ==
			(_bprTemporaryExpectation = bprTemporaryExpectation) || null == (_bprPermanentVolatility =
				bprPermanentVolatility) || null == (_bprTemporaryVolatility = bprTemporaryVolatility))
			throw new java.lang.Exception
				("ArithmeticPriceEvolutionParameters Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Arithmetic Price Dynamics Settings Instance
	 * 
	 * @return The Arithmetic Price Dynamics Settings Instance
	 */

	public org.drip.execution.parameters.ArithmeticPriceDynamicsSettings arithmeticPriceDynamicsSettings()
	{
		return _apds;
	}

	/**
	 * Retrieve the Background Participation Permanent Market Impact Expectation Function
	 * 
	 * @return The Background Participation Permanent Market Impact Expectation Function
	 */

	public org.drip.execution.profiletime.BackgroundParticipationRate permanentExpectation()
	{
		return _bprPermanentExpectation;
	}

	/**
	 * Retrieve the Background Participation Temporary Market Impact Expectation Function
	 * 
	 * @return The Background Participation Temporary Market Impact Expectation Function
	 */

	public org.drip.execution.profiletime.BackgroundParticipationRate temporaryExpectation()
	{
		return _bprTemporaryExpectation;
	}

	/**
	 * Retrieve the Background Participation Permanent Market Impact Volatility Function
	 * 
	 * @return The Background Participation Permanent Market Impact Volatility Function
	 */

	public org.drip.execution.profiletime.BackgroundParticipationRate permanentVolatility()
	{
		return _bprPermanentVolatility;
	}

	/**
	 * Retrieve the Background Participation Temporary Market Impact Volatility Function
	 * 
	 * @return The Background Participation Temporary Market Impact Volatility Function
	 */

	public org.drip.execution.profiletime.BackgroundParticipationRate temporaryVolatility()
	{
		return _bprTemporaryVolatility;
	}
}
