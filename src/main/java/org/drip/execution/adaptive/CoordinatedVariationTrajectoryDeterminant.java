
package org.drip.execution.adaptive;

import org.drip.numerical.common.NumberUtil;

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
 * <i>CoordinatedVariationTrajectoryDeterminant</i> contains the HJB-based MultiStep Optimal Cost Dynamic
 * 	Trajectory Generation Metrics using the Coordinated Variation Version of the Stochastic Volatility and
 * 	the Transaction Function arising from the Realization of the Market State Variable as described in the
 * 	"Trading Time" Model. It provides the following Functions:
 * 	<ul>
 * 		<li><i>CoordinatedVariationTrajectoryDeterminant</i> Constructor</li>
 * 		<li>Retrieve the Order Size</li>
 * 		<li>Retrieve the Time Scale</li>
 * 		<li>Retrieve the Cost Scale</li>
 * 		<li>Retrieve the Trade Rate Scale</li>
 * 		<li>Retrieve the Mean Market Urgency</li>
 * 		<li>Retrieve the Non Dimensional Risk Aversion Parameter</li>
 * 		<li>Retrieve the Preference-free "Market Power" Parameter</li>
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

public class CoordinatedVariationTrajectoryDeterminant
{
	private double _orderSize = Double.NaN;
	private double _costScale = Double.NaN;
	private double _timeScale = Double.NaN;
	private double _marketPower = Double.NaN;
	private double _tradeRateScale = Double.NaN;
	private double _meanMarketUrgency = Double.NaN;
	private double _nonDimensionalRiskAversion = Double.NaN;

	/**
	 * <i>CoordinatedVariationTrajectoryDeterminant</i> Constructor
	 * 
	 * @param orderSize The Order Size
	 * @param timeScale The Time Scale
	 * @param costScale The Cost Scale
	 * @param tradeRateScale The Trade Rate Scale
	 * @param meanMarketUrgency The Mean Market Urgency
	 * @param nonDimensionalRiskAversion The Non Dimensional Risk Aversion Parameter
	 * @param marketPower The Preference-free "Market Power" Parameter
	 * 
	 * @throws Exception Thrown if the the Inputs are Invalid
	 */

	public CoordinatedVariationTrajectoryDeterminant (
		final double orderSize,
		final double timeScale,
		final double costScale,
		final double tradeRateScale,
		final double meanMarketUrgency,
		final double nonDimensionalRiskAversion,
		final double marketPower)
		throws Exception
	{
		if (!NumberUtil.IsValid (_orderSize = orderSize) ||
			!NumberUtil.IsValid (_timeScale = timeScale) ||
			!NumberUtil.IsValid (_costScale = costScale) ||
			!NumberUtil.IsValid (_tradeRateScale = tradeRateScale) ||
			!NumberUtil.IsValid (_meanMarketUrgency = meanMarketUrgency) ||
			!NumberUtil.IsValid (_nonDimensionalRiskAversion = nonDimensionalRiskAversion) ||
			!NumberUtil.IsValid (_marketPower = marketPower))
		{
			throw new Exception ("CoordinatedVariationTrajectoryDeterminant Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Order Size
	 * 
	 * @return The Order Size
	 */

	public double orderSize()
	{
		return _orderSize;
	}

	/**
	 * Retrieve the Time Scale
	 * 
	 * @return The Time Scale
	 */

	public double timeScale()
	{
		return _timeScale;
	}

	/**
	 * Retrieve the Cost Scale
	 * 
	 * @return The Cost Scale
	 */

	public double costScale()
	{
		return _costScale;
	}

	/**
	 * Retrieve the Trade Rate Scale
	 * 
	 * @return The Trade Rate Scale
	 */

	public double tradeRateScale()
	{
		return _tradeRateScale;
	}

	/**
	 * Retrieve the Mean Market Urgency
	 * 
	 * @return The Mean Market Urgency
	 */

	public double meanMarketUrgency()
	{
		return _meanMarketUrgency;
	}

	/**
	 * Retrieve the Non Dimensional Risk Aversion Parameter
	 * 
	 * @return The Non Dimensional Risk Aversion Parameter
	 */

	public double nonDimensionalRiskAversion()
	{
		return _nonDimensionalRiskAversion;
	}

	/**
	 * Retrieve the Preference-free "Market Power" Parameter
	 * 
	 * @return The Preference-free "Market Power" Parameter
	 */

	public double marketPower()
	{
		return _marketPower;
	}
}
