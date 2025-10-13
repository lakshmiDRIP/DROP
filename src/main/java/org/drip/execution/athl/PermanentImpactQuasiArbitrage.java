
package org.drip.execution.athl;

import org.drip.execution.impact.TransactionFunctionPower;
import org.drip.execution.parameters.AssetFlowSettings;
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
 * <i>PermanentImpactQuasiArbitrage</i> implements the Linear Permanent Market Impact with Coefficients that
 * 	have been determined empirically by Almgren, Thum, Hauptmann, and Li (2005), independent of the no Quasi-
 * 	Arbitrage Criterion identified by Huberman and Stanzl (2004). It provides the following Functions:
 * 	<ul>
 * 		<li><i>PermanentImpactQuasiArbitrage</i> Constructor</li>
 * 		<li>Retrieve the Liquidity Factor</li>
 * 		<li>Retrieve the Asset Flow Parameters</li>
 * 		<li>Regularize the Input Function using the specified Trade Inputs</li>
 * 		<li>Modulate/Scale the Impact Output</li>
 * 		<li>Retrieve the Constant Market Impact Parameter</li>
 * 		<li>Retrieve the Power Law Exponent Market Impact Parameter</li>
 * 		<li>Evaluate the Impact for the given Normalized Holdings</li>
 * 		<li>Calculate the Ordered Derivative</li>
 * 	</ul>
 * 
 * The References are:
 * <br>
 * 	<ul>
 * 	<li>
 * 		Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions <i>Journal of Risk</i>
 * 			<b>3 (2)</b> 5-39
 * 	</li>
 * 	<li>
 * 		Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk
 * 			<i>Applied Mathematical Finance</i> <b>10 (1)</b> 1-18
 * 	</li>
 * 	<li>
 * 		Almgren, R., and N. Chriss (2003): Bidding Principles <i>Risk</i> 97-102
 * 	</li>
 * 	<li>
 * 		Almgren, R., C. Thum, E. Hauptmann, and H. Li (2005): Equity Market Impact <i>Risk</i> <b>18 (7)</b>
 * 			57-62
 * 	</li>
 * 	<li>
 * 		Huberman, G., and W. Stanzl (2004): Price Manipulation and Quasi-arbitrage <i>Econometrics</i>
 * 			<b>72 (4)</b> 1247-1275
 * 	</li>
 * 	</ul>
 *
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/README.md">Optimal Impact/Capture Based Trading Trajectories - Deterministic, Stochastic, Static, and Dynamic</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/execution/athl/README.md">Almgren-Thum-Hauptmann-Li Calibration</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class PermanentImpactQuasiArbitrage
	extends TransactionFunctionPower
{
	private double _liquidityFactor = Double.NaN;
	private AssetFlowSettings _assetFlowSettings = null;

	/**
	 * <i>PermanentImpactQuasiArbitrage</i> Constructor
	 * 
	 * @param assetFlowSettings Asset Flow Parameters
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public PermanentImpactQuasiArbitrage (
		final AssetFlowSettings assetFlowSettings)
		throws Exception
	{
		if (null == (_assetFlowSettings = assetFlowSettings)) {
			throw new Exception ("PermanentImpactQuasiArbitrage Constructor => Invalid Inputs");
		}

		_liquidityFactor = Math.pow (
			assetFlowSettings.inverseTurnover(),
			CalibrationEmpirics.PERMANENT_IMPACT_INVERSE_TURNOVER_EXPONENT
		);
	}

	/**
	 * Retrieve the Liquidity Factor
	 * 
	 * @return The Liquidity Factor
	 */

	public double liquidityFactor()
	{
		return _liquidityFactor;
	}

	/**
	 * Retrieve the Asset Flow Parameters
	 * 
	 * @return The Asset Flow Parameters
	 */

	public AssetFlowSettings assetFlowSettings()
	{
		return _assetFlowSettings;
	}

	/**
	 * Regularize the Input Function using the specified Trade Inputs
	 * 
	 * @param tradeInterval The Trade Interval
	 * 
	 * @return The Regularize Input
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	@Override public double regularize (
		final double tradeInterval)
		throws Exception
	{
		if (!NumberUtil.IsValid (tradeInterval) || 0 >= tradeInterval) {
			throw new Exception ("PermanentImpactQuasiArbitrage::regularize => Invalid Inputs");
		}

		return 1. / (_assetFlowSettings.averageDailyVolume() * tradeInterval);
	}

	/**
	 * Modulate/Scale the Impact Output
	 * 
	 * @param tradeInterval The Trade Interval
	 * 
	 * @return The Modulated Output
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	@Override public double modulate (
		final double tradeInterval)
		throws Exception
	{
		if (!NumberUtil.IsValid (tradeInterval) || 0. >= tradeInterval) {
			throw new Exception ("PermanentImpactQuasiArbitrage::modulate => Invalid Inputs");
		}

		return tradeInterval * _assetFlowSettings.dailyVolatility();
	}

	/**
	 * Retrieve the Constant Market Impact Parameter
	 * 
	 * @return The Constant Market Impact Parameter
	 */

	@Override public double constant()
	{
		return CalibrationEmpirics.PERMANENT_IMPACT_COEFFICIENT * _liquidityFactor;
	}

	/**
	 * Retrieve the Power Law Exponent Market Impact Parameter
	 * 
	 * @return The Power Law Exponent Market Impact Parameter
	 */

	@Override public double exponent()
	{
		return CalibrationEmpirics.PERMANENT_IMPACT_EXPONENT_ATHL2005;
	}

	/**
	 * Evaluate the Impact for the given Normalized Holdings
	 * 
	 * @param normalizedX Normalized Holdings
	 *  
	 * @return The calculated Impact
	 * 
	 * @throws Exception Thrown if evaluation cannot be done
	 */

	@Override public double evaluate (
		final double normalizedX)
		throws Exception
	{
		if (!NumberUtil.IsValid (normalizedX)) {
			throw new Exception ("PermanentImpactQuasiArbitrage::evaluate => Invalid Inputs");
		}

		return 0.5 * CalibrationEmpirics.PERMANENT_IMPACT_COEFFICIENT * (
			0. >normalizedX ? -1. : 1.
		) * Math.pow (
			Math.abs (normalizedX),
			CalibrationEmpirics.PERMANENT_IMPACT_EXPONENT_ATHL2005
		) * _liquidityFactor;
	}

	/**
	 * Calculate the Ordered Derivative
	 * 
	 * @param normalizedX Normalized Holdings
	 * @param order Order of the derivative to be computed
	 * 
	 * @return The Ordered Derivative
	 */

	@Override public double derivative  (
		final double normalizedX,
		final int order)
		throws Exception
	{
		if (0 >= order || !NumberUtil.IsValid (normalizedX)) {
			throw new Exception ("PermanentImpactQuasiArbitrage::derivative => Invalid Inputs");
		}

		double coefficient = 1.;

		for (int i = 0; i < order; ++i) {
			coefficient = coefficient * (CalibrationEmpirics.PERMANENT_IMPACT_EXPONENT_ATHL2005 - i);
		}

		return 0.5 * CalibrationEmpirics.PERMANENT_IMPACT_COEFFICIENT * (
			0. > normalizedX ? -1. : 1.
		) * coefficient * Math.pow (
			Math.abs (normalizedX),
			CalibrationEmpirics.PERMANENT_IMPACT_EXPONENT_ATHL2005 - order
		) * _liquidityFactor;
	}
}
