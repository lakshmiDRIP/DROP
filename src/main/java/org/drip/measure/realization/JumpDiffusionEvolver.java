
package org.drip.measure.realization;

import org.drip.measure.dynamics.DiffusionEvaluator;
import org.drip.measure.dynamics.HazardJumpEvaluator;
import org.drip.measure.dynamics.LocalEvaluator;
import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
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
 * <i>JumpDiffusionEvolver</i> implements the Functionality that guides the Single Factor R<sup>1</sup> Jump
 * 	Diffusion Random Process Variable Evolution. It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>JumpDiffusionEvolver</i> Constructor</li>
 * 		<li>Retrieve the Hazard Point Event Indicator Instance</li>
 * 		<li>Generate the <i>JumpDiffusionEdge</i> Instance from the specified Jump Diffusion Instance</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/realization/README.md">Stochastic Jump Diffusion Vertex Edge</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class JumpDiffusionEvolver
	extends DiffusionEvolver
{
	private HazardJumpEvaluator _eventIndicationEvaluator = null;

	/**
	 * <i>JumpDiffusionEvolver</i> Constructor
	 * 
	 * @param diffusionEvaluator The Diffusion Evaluator Instance
	 * @param eventIndicationEvaluator The Hazard Point Event Indicator Function Instance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public JumpDiffusionEvolver (
		final DiffusionEvaluator diffusionEvaluator,
		final HazardJumpEvaluator eventIndicationEvaluator)
		throws Exception
	{
		super (diffusionEvaluator);

		if (null == (_eventIndicationEvaluator = eventIndicationEvaluator)) {
			throw new Exception ("JumpDiffusionEvolver Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Hazard Point Event Indicator Instance
	 * 
	 * @return The Hazard Point Event Indicator Instance
	 */

	public HazardJumpEvaluator eventIndicationEvaluator()
	{
		return _eventIndicationEvaluator;
	}

	/**
	 * Generate the <i>JumpDiffusionEdge</i> Instance from the specified Jump Diffusion Instance
	 * 
	 * @param jumpDiffusionVertex The <i>JumpDiffusionVertex</i> Instance
	 * @param jumpDiffusionEdgeUnit The Random Unit Realization
	 * @param timeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The <i>JumpDiffusionEdge</i> Instance
	 */

	@Override public JumpDiffusionEdge increment (
		final JumpDiffusionVertex jumpDiffusionVertex,
		final JumpDiffusionEdgeUnit jumpDiffusionEdgeUnit,
		final double timeIncrement)
	{
		if (null == jumpDiffusionVertex ||
			null == jumpDiffusionEdgeUnit ||
			!NumberUtil.IsValid (timeIncrement))
		{
			return null;
		}

		double previousValue = jumpDiffusionVertex.value();

		try {
			if (jumpDiffusionVertex.jumpOccurred()) {
				return JumpDiffusionEdge.Standard (
					previousValue,
					0.,
					0.,
					new StochasticEdgeJump (false, 0., 0., previousValue),
					jumpDiffusionEdgeUnit
				);
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		double hazardRate = _eventIndicationEvaluator.rate();

		DiffusionEvaluator diffusionEvaluator = evaluator();

		double levelHazardIntegral = hazardRate * timeIncrement;

		boolean eventOccurred = Math.exp (
			-1. * (jumpDiffusionVertex.cumulativeHazardIntegral() + levelHazardIntegral)
		) <= jumpDiffusionEdgeUnit.jump();

		try {
			StochasticEdgeJump stochasticEdgeJump = new StochasticEdgeJump (
				eventOccurred,
				hazardRate,
				levelHazardIntegral,
				_eventIndicationEvaluator.magnitudeEvaluator().value (jumpDiffusionVertex)
			);

			if (eventOccurred) {
				return JumpDiffusionEdge.Standard (
					previousValue,
					0.,
					0.,
					stochasticEdgeJump,
					jumpDiffusionEdgeUnit
				);
			}

			LocalEvaluator localVolatilityEvaluator = diffusionEvaluator.localVolatilityEvaluator();

			return JumpDiffusionEdge.Standard (
				previousValue,
				diffusionEvaluator.localDriftEvaluator().value (jumpDiffusionVertex) * timeIncrement,
				null == localVolatilityEvaluator ? 0. : localVolatilityEvaluator.value (
					jumpDiffusionVertex
				) * jumpDiffusionEdgeUnit.diffusion() * Math.sqrt (Math.abs (timeIncrement)),
				stochasticEdgeJump,
				jumpDiffusionEdgeUnit
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
