
package org.drip.execution.sensitivity;

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
 * <i>ControlNodesGreekGenerator</i> exposes the Functionality to compute the Base Value, the Jacobian, and
 * the Hessian Sensitivities of the Mean and the Variance Contributions to the Permanent Impact, Temporary
 * Impact, and the Market Core Components. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/sensitivity/README.md">Trajectory Control Nodes Sensitivity Greeks</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public interface ControlNodesGreekGenerator {

	/**
	 * Generate the Permanent Impact Expectation Contribution
	 * 
	 * @param apep The Arithmetic Price Walk Evolution Parameters
	 * 
	 * @return The Permanent Impact Expectation Contribution
	 */

	public abstract org.drip.execution.sensitivity.ControlNodesGreek permanentImpactExpectation (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep);

	/**
	 * Generate the Permanent Impact Variance Contribution
	 * 
	 * @param apep The Arithmetic Price Walk Evolution Parameters
	 * 
	 * @return The Permanent Impact Variance Contribution
	 */

	public abstract org.drip.execution.sensitivity.ControlNodesGreek permanentImpactVariance (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep);

	/**
	 * Generate the Temporary Impact Expectation Contribution
	 * 
	 * @param apep The Arithmetic Price Walk Evolution Parameters
	 * 
	 * @return The Temporary Impact Expectation Contribution
	 */

	public abstract org.drip.execution.sensitivity.ControlNodesGreek temporaryImpactExpectation (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep);

	/**
	 * Generate the Temporary Impact Variance Contribution
	 * 
	 * @param apep The Arithmetic Price Walk Evolution Parameters
	 * 
	 * @return The Temporary Impact Variance Contribution
	 */

	public abstract org.drip.execution.sensitivity.ControlNodesGreek temporaryImpactVariance (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep);

	/**
	 * Generate the Market Dynamics Expectation Contribution
	 * 
	 * @param apep The Arithmetic Price Walk Evolution Parameters
	 * 
	 * @return The Market Dynamics Expectation Contribution
	 */

	public abstract org.drip.execution.sensitivity.ControlNodesGreek marketDynamicsExpectation (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep);

	/**
	 * Generate the Market Dynamics Variance Contribution
	 * 
	 * @param apep The Arithmetic Price Walk Evolution Parameters
	 * 
	 * @return The Market Dynamics Variance Contribution
	 */

	public abstract org.drip.execution.sensitivity.ControlNodesGreek marketDynamicsVariance (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep);

	/**
	 * Generate the Total Expectation Contribution
	 * 
	 * @param apep The Arithmetic Price Walk Evolution Parameters
	 * 
	 * @return The Total Expectation Contribution
	 */

	public abstract org.drip.execution.sensitivity.ControlNodesGreek expectationContribution (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep);

	/**
	 * Generate the Total Variance Contribution
	 * 
	 * @param apep The Arithmetic Price Walk Evolution Parameters
	 * 
	 * @return The Total Variance Contribution
	 */

	public abstract org.drip.execution.sensitivity.ControlNodesGreek varianceContribution (
		final org.drip.execution.dynamics.ArithmeticPriceEvolutionParameters apep);
}
