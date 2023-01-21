
package org.drip.execution.discrete;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
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
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>ShortfallIncrementDistribution</i> holds the Parameters of the R<sup>1</sup> Normal Short fall
 * Increment Distribution. The References are:
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
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/discrete/README.md">Trajectory Slice Execution Cost Distribution</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ShortfallIncrementDistribution extends org.drip.measure.gaussian.R1UnivariateNormal {
	private double _dblMarketDynamicVariance = java.lang.Double.NaN;
	private double _dblPermanentImpactVariance = java.lang.Double.NaN;
	private double _dblTemporaryImpactVariance = java.lang.Double.NaN;
	private double _dblMarketDynamicExpectation = java.lang.Double.NaN;
	private double _dblPermanentImpactExpectation = java.lang.Double.NaN;
	private double _dblTemporaryImpactExpectation = java.lang.Double.NaN;

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
