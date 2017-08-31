
package org.drip.execution.nonadaptive;

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
 * StaticOptimalSchemeDiscrete generates the Trade/Holdings List of Static Optimal Execution Schedule based
 *  on the Discrete Trade Trajectory Control, the Price Walk Parameters, and the Objective Utility Function.
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

public class StaticOptimalSchemeDiscrete extends org.drip.execution.nonadaptive.StaticOptimalScheme {
	private org.drip.execution.strategy.DiscreteTradingTrajectoryControl _dttc = null;

	private double[] completeHoldings (
		final double[] adblInnerHoldings)
	{
		if (null == adblInnerHoldings) return null;

		int iNumCompleteHoldings = adblInnerHoldings.length + 2;
		double[] adblCompleteHoldings = new double[iNumCompleteHoldings];

		for (int i = 0; i < iNumCompleteHoldings; ++i) {
			if (0 == i)
				adblCompleteHoldings[i] = _dttc.startHoldings();
			else if (iNumCompleteHoldings - 1 == i)
				adblCompleteHoldings[i] = 0.;
			else
				adblCompleteHoldings[i] = adblInnerHoldings[i - 1];
		}

		return adblCompleteHoldings;
	}

	private org.drip.execution.sensitivity.ControlNodesGreek objectiveSensitivity (
		final double[] adblInnerHoldings)
	{
		org.drip.execution.capture.TrajectoryShortfallEstimator tse = null;

		try {
			tse = new org.drip.execution.capture.TrajectoryShortfallEstimator
				(org.drip.execution.strategy.DiscreteTradingTrajectory.Standard (_dttc.executionTimeNodes(),
					completeHoldings (adblInnerHoldings)));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep = priceEvolutionParameters();

		org.drip.execution.sensitivity.ControlNodesGreek cngVariance = tse.varianceContribution (apep);

		org.drip.execution.sensitivity.ControlNodesGreek cngExpectation = tse.expectationContribution (apep);

		if (null == cngExpectation || null == cngVariance) return null;

		return objectiveUtility().sensitivity ((org.drip.execution.sensitivity.TrajectoryControlNodesGreek)
			cngExpectation, (org.drip.execution.sensitivity.TrajectoryControlNodesGreek) cngVariance);
	}

	private org.drip.function.definition.RdToR1 optimizerRdToR1()
	{
		return new org.drip.function.definition.RdToR1 (null) {
			@Override public int dimension()
			{
				return _dttc.executionTimeNodes().length - 2;
			}

			@Override public double evaluate (
				final double[] adblInnerHoldings)
				throws java.lang.Exception
			{
				org.drip.execution.sensitivity.ControlNodesGreek cngObjectiveUtility = objectiveSensitivity
					(adblInnerHoldings);

				if (null == cngObjectiveUtility)
					throw new java.lang.Exception
						("StaticOptimalSchemeDiscrete::optimizerRdToR1::evaluate => Invalid Inputs");

				return cngObjectiveUtility.value();
			}

			@Override public double[] jacobian (
				final double[] adblInnerHoldings)
			{
				org.drip.execution.sensitivity.ControlNodesGreek cngObjectiveUtility = objectiveSensitivity
					(adblInnerHoldings);

				return null == cngObjectiveUtility ? null : cngObjectiveUtility.jacobian();
			}

			@Override public double[][] hessian (
				final double[] adblInnerHoldings)
			{
				org.drip.execution.sensitivity.ControlNodesGreek cngObjectiveUtility = objectiveSensitivity
					(adblInnerHoldings);

				return null == cngObjectiveUtility ? null : cngObjectiveUtility.hessian();
			}
		};
	}

	/**
	 * StaticOptimalSchemeDiscrete Constructor
	 * 
	 * @param dttc The Discrete Trading Trajectory Control Parameters
	 * @param apep The Arithmetic Price Walk Parameters
	 * @param ou The Optimizer Objective Utility Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are not valid
	 */

	public StaticOptimalSchemeDiscrete (
		final org.drip.execution.strategy.DiscreteTradingTrajectoryControl dttc,
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep,
		final org.drip.execution.risk.ObjectiveUtility ou)
		throws java.lang.Exception
	{
		super (apep, ou);

		if (null == (_dttc = dttc))
			throw new java.lang.Exception ("StaticOptimalSchemeDiscrete Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Discrete Trajectory Control Settings
	 * 
	 * @return The Discrete Trajectory Control Settings
	 */

	public org.drip.execution.strategy.DiscreteTradingTrajectoryControl control()
	{
		return _dttc;
	}

	@Override public org.drip.execution.optimum.EfficientTradingTrajectory generate()
	{
		double[] adblExecutionTimeNode = _dttc.executionTimeNodes();

		org.drip.execution.strategy.DiscreteTradingTrajectory dtt =
			org.drip.execution.strategy.DiscreteTradingTrajectory.Linear (adblExecutionTimeNode,
				_dttc.startHoldings(), 0.);

		if (null == dtt) return null;

		org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier vicm = null;

		try {
			if (null == (vicm = new org.drip.function.rdtor1solver.NewtonFixedPointFinder (optimizerRdToR1(),
				null, org.drip.function.rdtor1solver.ConvergenceControl.Standard()).convergeVariate (new
					org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier (false,
						dtt.innerHoldings(), null))))
				return null;
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		return org.drip.execution.optimum.EfficientTradingTrajectoryDiscrete.Standard (adblExecutionTimeNode,
			completeHoldings (vicm.variates()), priceEvolutionParameters());
	}
}
