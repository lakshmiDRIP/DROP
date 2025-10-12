
package org.drip.execution.adaptive;

import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
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
 * <i>CoordinatedVariationRollingHorizon</i> implements the "Rolling Horizon" Approximation of the Optimal
 * 	Cost Dynamic Trajectory arising from the Coordinated Variation Version of the Stochastic Volatility and
 * 	the Transaction Function arising from the Realization of the Market State Variable as described in the
 * 	"Trading Time" Model. It provides the following Functions:
 * 	<ul>
 * 		<li><i>CoordinatedVariationDynamic</i> Constructor</li>
 * 		<li>Retrieve the Array of the Non Dimensional Holdings</li>
 * 		<li>Retrieve the Array of the Scaled Non Dimensional Trade Rate</li>
 * 		<li>Retrieve the Array of the Non Dimensional Costs</li>
 * 	</ul>
 * 
 * The References are:
 * <br>
 *  <ul>
 * 		<li>
 * 			Almgren, R. F., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Almgren, R. F. (2009): Optimal Trading in a Dynamic Market
 * 				https://www.math.nyu.edu/financial_mathematics/content/02_financial/2009-2.pdf
 * 		</li>
 * 		<li>
 * 			Almgren, R. F. (2012): Optimal Trading with Stochastic Liquidity and Volatility <i>SIAM Journal
 * 			of Financial Mathematics</i> <b>3 (1)</b> 163-181
 * 		</li>
 * 		<li>
 * 			Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes <i>Mathematical
 * 				Finance</i> <b>11 (1)</b> 79-96
 * 		</li>
 * 		<li>
 * 			Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility <i>Review of
 * 				Financial Studies</i> <b>7 (4)</b> 631-651
 * 		</li>
 *  </ul>
 *
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/adaptive/README.md">Coordinated Variation Based Adaptive Execution</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class CoordinatedVariationRollingHorizon
	extends CoordinatedVariationTrajectory
{
	private double[] _nonDimensionalCostArray = null;
	private double[] _nonDimensionalHoldingsArray = null;
	private double[] _nonDimensionalTradeRateArray = null;

	/**
	 * <i>CoordinatedVariationRollingHorizon</i> Constructor
	 * 
	 * @param trajectoryDeterminant The Coordinated Variation Trajectory Determinant 
	 * @param nonDimensionalHoldingsArray The Array of the Non Dimensional Holdings
	 * @param nonDimensionalTradeRateArray The Array of the Non Dimensional Trade Rate
	 * @param nonDimensionalCostArray The Array of the Non Dimensional Cost
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public CoordinatedVariationRollingHorizon (
		final CoordinatedVariationTrajectoryDeterminant trajectoryDeterminant,
		final double[] nonDimensionalHoldingsArray,
		final double[] nonDimensionalTradeRateArray,
		final double[] nonDimensionalCostArray)
		throws java.lang.Exception
	{
		super (trajectoryDeterminant);

		if (null == (_nonDimensionalHoldingsArray = nonDimensionalHoldingsArray) ||
			null == (_nonDimensionalTradeRateArray = nonDimensionalTradeRateArray) ||
			null == (_nonDimensionalCostArray = nonDimensionalCostArray) ||
			!NumberUtil.IsValid (_nonDimensionalHoldingsArray) ||
			!NumberUtil.IsValid (_nonDimensionalTradeRateArray) ||
			!NumberUtil.IsValid (_nonDimensionalCostArray))
		{
			throw new Exception ("CoordinatedVariationRollingHorizon Constructor => Invalid Inputs");
		}

		if (0 == _nonDimensionalHoldingsArray.length ||
			_nonDimensionalHoldingsArray.length != _nonDimensionalTradeRateArray.length)
		{
			throw new Exception ("CoordinatedVariationRollingHorizon Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of the Non Dimensional Holdings
	 * 
	 * @return The Array of the Non Dimensional Holdings
	 */

	public double[] nonDimensionalHoldings()
	{
		return _nonDimensionalHoldingsArray;
	}

	/**
	 * Retrieve the Array of the Non Dimensional Trade Rate
	 * 
	 * @return The Array of the Non Dimensional Trade Rate
	 */

	public double[] nonDimensionalTradeRate()
	{
		return _nonDimensionalTradeRateArray;
	}

	/**
	 * Retrieve the Array of the Non Dimensional Cost
	 * 
	 * @return The Array of the Non Dimensional Cost
	 */

	public double[] nonDimensionalCost()
	{
		return _nonDimensionalCostArray;
	}
}
