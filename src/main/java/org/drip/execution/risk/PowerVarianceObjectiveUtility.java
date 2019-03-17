
package org.drip.execution.risk;

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
 * <i>PowerVarianceObjectiveUtility</i> implements the Mean-Power-Variance Objective Utility Function that
 * needs to be optimized to extract the Optimal Execution Trajectory. The Exact Objective Function is of the
 * Form:
 *  
 *  			U[x] = E[x] + lambda * (V[x] ^p)
 *  
 *  where p is greater than 0.
 *  
 *  p = 1
 *  
 *  is the Regular Mean-Variance, and
 *  
 *  p = 0.5
 *  
 *  is VaR Minimization (L-VaR). The References are:
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
 * 			Almgren, R. (2003): Optimal Execution with Non-linear Impact Functions and Trading Enhanced Risk
 * 				<i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18
 * 		</li>
 * 		<li>
 * 			Artzner, P., F. Delbaen, J. M. Eber, and D. Heath (1999): Coherent Measures of Risk
 * 				<i>Mathematical Finance</i> <b>9</b> 203-228
 * 		</li>
 * 		<li>
 * 			Basak, S., and A. Shapiro (2001): Value-at-Risk Based Risk Management: Optimal Policies and Asset
 * 				Prices <i>Review of Financial Studies</i> <b>14</b> 371-405
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/PortfolioCore.md">Portfolio Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/TransactionCostAnalyticsLibrary.md">Transaction Cost Analytics</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Execution</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/risk/README.md">Risk</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PowerVarianceObjectiveUtility implements org.drip.execution.risk.ObjectiveUtility {
	private double _dblRiskAversion = java.lang.Double.NaN;
	private double _dblVarianceExponent = java.lang.Double.NaN;

	/**
	 * Generate the Liquidity VaR Version of the Power Variance Utility Function
	 * 
	 * @param dblRiskAversion The Risk Aversion Parameter
	 * 
	 * @return The Liquidity VaR Version of the Power Variance Utility Function
	 */

	public static final PowerVarianceObjectiveUtility LiquidityVaR (
		final double dblRiskAversion)
	{
		try {
			return new PowerVarianceObjectiveUtility (0.5, dblRiskAversion);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * PowerVarianceObjectiveUtility Constructor
	 * 
	 * @param dblVarianceExponent The Variance Exponent
	 * @param dblRiskAversion The Risk Aversion Parameter
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PowerVarianceObjectiveUtility (
		final double dblVarianceExponent,
		final double dblRiskAversion)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblVarianceExponent = dblVarianceExponent) || 0. >
			_dblVarianceExponent || !org.drip.numerical.common.NumberUtil.IsValid (_dblRiskAversion =
				dblRiskAversion) || 0. > _dblRiskAversion)
			throw new java.lang.Exception ("PowerVarianceObjectiveUtility Constructor => Invalid Inputs!");
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

	/**
	 * Retrieve the Variance Exponent
	 * 
	 * @return The Variance Exponent
	 */

	public double varianceExponent()
	{
		return _dblVarianceExponent;
	}

	@Override public org.drip.execution.sensitivity.ControlNodesGreek sensitivity (
		final org.drip.execution.sensitivity.TrajectoryControlNodesGreek tcngExpectation,
		final org.drip.execution.sensitivity.TrajectoryControlNodesGreek tcngVariance)
	{
		if (null == tcngExpectation || null == tcngVariance) return null;

		double[] adblVarianceJacobian = tcngVariance.innerJacobian();

		if (null == adblVarianceJacobian) return null;

		double dblVarianceValue = tcngVariance.value();

		double[][] aadblVarianceHessian = tcngVariance.innerHessian();

		double[] adblExpectationJacobian = tcngExpectation.innerJacobian();

		double[][] aadblExpectationHessian = tcngExpectation.innerHessian();

		int iNumControlNode = adblVarianceJacobian.length;
		double[] adblObjectiveJacobian = new double[iNumControlNode];
		double[][] aadblObjectiveHessian = new double[iNumControlNode][iNumControlNode];

		double dblJacobianMultiplier = _dblVarianceExponent * _dblRiskAversion * java.lang.Math.pow
			(dblVarianceValue, _dblVarianceExponent - 1.);

		double dblJacobianProductMultiplier = _dblVarianceExponent * (_dblVarianceExponent - 1.) *
			_dblRiskAversion * java.lang.Math.pow (dblVarianceValue, _dblVarianceExponent - 2.);

		for (int i = 0; i < iNumControlNode; ++i) {
			adblObjectiveJacobian[i] = adblExpectationJacobian[i] + dblJacobianMultiplier *
				adblVarianceJacobian[i];

			for (int j = 0; j < iNumControlNode; ++j)
				aadblObjectiveHessian[i][j] = aadblExpectationHessian[i][j] + dblJacobianProductMultiplier *
					adblVarianceJacobian[i] * adblVarianceJacobian[j] + dblJacobianMultiplier *
						aadblVarianceHessian[i][j];
		}

		try {
			return new org.drip.execution.sensitivity.ControlNodesGreek (tcngExpectation.value() +
				_dblRiskAversion * java.lang.Math.pow (dblVarianceValue, _dblVarianceExponent),
					adblObjectiveJacobian, aadblObjectiveHessian);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
