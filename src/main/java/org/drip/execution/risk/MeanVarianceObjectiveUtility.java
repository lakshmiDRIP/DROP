
package org.drip.execution.risk;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
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
 * <i>MeanVarianceObjectiveUtility</i> implements the Mean-Variance Objective Utility Function that needs to
 * be optimized to extract the Optimal Execution Trajectory. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ProductCore.md">Product Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/risk/README.md">Optimal Execution MVO Efficient Frontier</a></li>
 *  </ul>
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
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblRiskAversion = dblRiskAversion) || 0. >
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
