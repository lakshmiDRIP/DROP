
package org.drip.execution.bayesian;

import org.drip.measure.gaussian.NormalQuadrature;
import org.drip.measure.gaussian.R1UnivariateNormal;
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
 * <i>ConditionalPriceDistribution</i> holds the Price Distribution Conditional on a given Drift. It provides
 * 	the following Functions:
 * 	<ul>
 * 		<li><i>ConditionalPriceDistribution</i> Constructor</li>
 * 		<li>Retrieve the Distribution Time Horizon</li>
 * 		<li>Retrieve the Distribution Price Volatility</li>
 * 		<li>Retrieve the Distribution Conditional Drift</li>
 * 		<li>Generate Single Price Volatility Swings</li>
 * 		<li>Generate the given Number of Price Volatility Swings</li>
 * 	</ul>
 * 
 * The References are:
 * <br>
 * 	<ul>
 * 		<li>
 * 			Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs <i>Journal of Financial
 * 				Markets</i> <b>1</b> 1-50
 * 		</li>
 * 		<li>
 * 			Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of
 * 				Risk</i> <b>3 (2)</b> 5-39
 * 		</li>
 * 		<li>
 * 			Brunnermeier, L. K., and L. H. Pedersen (2005): Predatory Trading <i>Journal of Finance</i> <b>60
 * 				(4)</b> 1825-1863
 * 		</li>
 * 		<li>
 * 			Almgren, R., and J. Lorenz (2006): Bayesian Adaptive Trading with a Daily Cycle <i>Journal of
 * 				Trading</i> <b>1 (4)</b> 38-46
 * 		</li>
 * 		<li>
 * 			Kissell, R., and R. Malamut (2007): Algorithmic Decision Making Framework <i>Journal of
 * 				Trading</i> <b>1 (1)</b> 12-21
 * 		</li>
 * 	</ul>
 *
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/bayesian/README.md">Bayesian Price Based Optimal Execution</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class ConditionalPriceDistribution
	extends R1UnivariateNormal
{
	private double _time = Double.NaN;
	private double _drift = Double.NaN;
	private double _volatility = Double.NaN;

	/**
	 * <i>ConditionalPriceDistribution</i> Constructor
	 * 
	 * @param drift The Conditional Drift
	 * @param volatility The Price Volatility
	 * @param time The Distribution Time Horizon
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public ConditionalPriceDistribution (
		final double drift,
		final double volatility,
		final double time)
		throws Exception
	{
		super (drift * time, volatility * Math.sqrt (time));

		if (!NumberUtil.IsValid (_time = time) || 0. >= _time ||
			!NumberUtil.IsValid (_drift = drift) ||
			!NumberUtil.IsValid (_volatility = volatility))
		{
			throw new Exception ("ConditionalPriceDistribution Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Distribution Time Horizon
	 * 
	 * @return The Distribution Time Horizon
	 */

	public double time()
	{
		return _time;
	}

	/**
	 * Retrieve the Distribution Price Volatility
	 * 
	 * @return The Distribution Price Volatility
	 */

	public double volatility()
	{
		return _volatility;
	}

	/**
	 * Retrieve the Distribution Conditional Drift
	 * 
	 * @return The Distribution Conditional Drift
	 */

	public double drift()
	{
		return _drift;
	}

	/**
	 * Generate Single Price Volatility Swings
	 * 
	 * @return The Price Volatility Swings
	 * 
	 * @throws Exception Thrown if the Swing cannot be generated
	 */

	public double volatilitySwing()
		throws Exception
	{
		return _volatility * NormalQuadrature.InverseCDF (Math.random()) * Math.sqrt (_time);
	}

	/**
	 * Generate the given Number of Price Volatility Swings
	 * 
	 * @param realizationCount The Number of Swings to be generated
	 * 
	 * @return Array of the Price Volatility Swings
	 */

	public double[] volatilitySwingArray (
		final int realizationCount)
	{
		if (0 >= realizationCount) {
			return null;
		}

		double[] volatilitySwingArray = new double[realizationCount];

		double volatilityTimeSQRT = _volatility * Math.sqrt (_time);

		for (int realizationIndex = 0; realizationIndex < realizationCount; ++realizationIndex) {
			try {
				volatilitySwingArray[realizationIndex] =
					NormalQuadrature.InverseCDF (Math.random()) * volatilityTimeSQRT;
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return volatilitySwingArray;
	}
}
