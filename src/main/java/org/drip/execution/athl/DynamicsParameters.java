
package org.drip.execution.athl;

import org.drip.execution.dynamics.ArithmeticPriceEvolutionParametersBuilder;
import org.drip.execution.dynamics.LinearPermanentExpectationParameters;
import org.drip.execution.parameters.ArithmeticPriceDynamicsSettings;
import org.drip.execution.parameters.AssetFlowSettings;
import org.drip.execution.profiletime.UniformParticipationRate;
import org.drip.execution.profiletime.UniformParticipationRateLinear;
import org.drip.function.r1tor1operator.Flat;

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
 * <i>DynamicsParameters</i> generates the Variants of the Market Dynamics Parameters constructed using the
 * 	Methodologies presented in Almgren, Thum, Hauptmann, and Li (2005), using the Parameterization of Almgren
 * 	(2003). It provides the following Functions:
 * 	<ul>
 * 		<li><i>DynamicsParameters</i> Constructor</li>
 * 		<li>Retrieve the Asset Flow Parameters Instance</li>
 * 		<li>Generate an Instance of the Almgren 2003 Dynamics Parameters</li>
 * 	</ul>
 * 
 * The References are:
 * <br>
 * 	<ul>
 * 	<li>
 * 		Almgren, R., and N. Chriss (1999): Value under Liquidation <i>Risk</i> <b>12 (12)</b>
 * 	</li>
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

public class DynamicsParameters
{
	private AssetFlowSettings _assetFlowSettings = null;

	/**
	 * <i>DynamicsParameters</i> Constructor
	 * 
	 * @param assetFlowSettings The Asset Flow Parameters Instance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public DynamicsParameters (
		final AssetFlowSettings assetFlowSettings)
		throws java.lang.Exception
	{
		if (null == (_assetFlowSettings = assetFlowSettings)) {
			throw new Exception ("DynamicsParameters Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Asset Flow Parameters Instance
	 * 
	 * @return The Asset Flow Parameters Instance
	 */

	public AssetFlowSettings assetFlowSettings()
	{
		return _assetFlowSettings;
	}

	/**
	 * Generate an Instance of the Almgren 2003 Dynamics Parameters
	 * 
	 * @return Instance of the Almgren 2003 Dynamics Parameters
	 */

	public LinearPermanentExpectationParameters almgren2003()
	{
		try {
			return ArithmeticPriceEvolutionParametersBuilder.Almgren2003 (
				new ArithmeticPriceDynamicsSettings (
					0.,
					new Flat (_assetFlowSettings.dailyVolatility()),
					0.
				),
				new UniformParticipationRateLinear (new PermanentImpactNoArbitrage (_assetFlowSettings)),
				new UniformParticipationRate (new TemporaryImpact (_assetFlowSettings))
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
