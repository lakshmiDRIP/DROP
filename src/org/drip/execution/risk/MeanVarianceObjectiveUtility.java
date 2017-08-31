
package org.drip.execution.risk;

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
 * MeanVarianceObjectiveUtility implements the Mean-Variance Objective Utility Function that needs to be
 *  optimized to extract the Optimal Execution Trajectory. The References are:
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

public class MeanVarianceObjectiveUtility implements org.drip.execution.risk.ObjectiveUtility {
	private double _dblRiskAversion = java.lang.Double.NaN;

	/**
	 * MeanVarianceObjectiveUtility Constructor
	 * 
	 * @param dblRiskAversion The Risk Aversion Parameter
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public MeanVarianceObjectiveUtility (
		final double dblRiskAversion)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblRiskAversion = dblRiskAversion) || 0. >
			_dblRiskAversion)
			throw new java.lang.Exception ("MeanVarianceObjectiveUtility Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Risk Aversion Parameter
	 * 
	 * @return The Risk Aversion Parameter
	 */

	public double riskAversion()
	{
		return _dblRiskAversion;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek sensitivity (
		final org.drip.execution.sensitivity.TrajectoryControlNodesGreek tcngExpectation,
		final org.drip.execution.sensitivity.TrajectoryControlNodesGreek tcngVariance)
	{
		if (null == tcngExpectation || null == tcngVariance) return null;

		double[] adblVarianceJacobian = tcngVariance.innerJacobian();

		if (null == adblVarianceJacobian) return null;

		double[][] aadblVarianceHessian = tcngVariance.innerHessian();

		double[] adblExpectationJacobian = tcngExpectation.innerJacobian();

		double[][] aadblExpectationHessian = tcngExpectation.innerHessian();

		int iNumControlNode = adblVarianceJacobian.length;
		double[] adblObjectiveJacobian = new double[iNumControlNode];
		double[][] aadblObjectiveHessian = new double[iNumControlNode][iNumControlNode];

		for (int i = 0; i < iNumControlNode; ++i) {
			adblObjectiveJacobian[i] = adblExpectationJacobian[i] + _dblRiskAversion *
				adblVarianceJacobian[i];

			for (int j = 0; j < iNumControlNode; ++j)
				aadblObjectiveHessian[i][j] = aadblExpectationHessian[i][j] + _dblRiskAversion *
					aadblVarianceHessian[i][j];
		}

		try {
			return new org.drip.execution.sensitivity.ControlNodesGreek (tcngExpectation.value() +
				_dblRiskAversion * tcngVariance.value(), adblObjectiveJacobian, aadblObjectiveHessian);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
