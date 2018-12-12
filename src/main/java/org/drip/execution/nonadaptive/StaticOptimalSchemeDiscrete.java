
package org.drip.execution.nonadaptive;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>StaticOptimalSchemeDiscrete</i> generates the Trade/Holdings List of Static Optimal Execution Schedule
 * based on the Discrete Trade Trajectory Control, the Price Walk Parameters, and the Objective Utility
 * Function. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 * 		</li>
 * 		<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 * 		</li>
 * 		<li>
 * 			Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional
 * 				Trades <i>Journal of Finance</i> <b>50</b> 1147-1174
 * 		</li>
 * 		<li>
 * 			Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange
 * 				Analysis of Institutional Equity Trades <i>Journal of Financial Economics</i> <b>46</b>
 * 				265-292
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/nonadaptive/README.md">Non Adaptive</a></li>
 *  </ul>
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
