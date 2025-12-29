
package org.drip.measure.realization;

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
 * <i>JumpDiffusionEdge</i> implements the Deterministic and the Stochastic Components of a R<sup>d</sup>
 * 	Marginal Random Increment Edge as well the Original Marginal Random Variate. The References are:
 * 
 * <br><br>
 * 	<ul>
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
 * 				of Financial Mathematics</i> <b>3 (1)</b> 163-181
 * 		</li>
 * 		<li>
 * 			Geman, H., D. B. Madan, and M. Yor (2001): Time Changes for Levy Processes <i>Mathematical
 * 				Finance</i> <b>11 (1)</b> 79-96
 * 		</li>
 * 		<li>
 * 			Jones, C. M., G. Kaul, and M. L. Lipson (1994): Transactions, Volume, and Volatility <i>Review of
 * 				Financial Studies</i> <b>7 (4)</b> 631-651
 * 		</li>
 * 	</ul>
 *
 * 	It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Construct the Standard <i>JumpDiffusionEdge</i> Instance #1</li>
 * 		<li>Construct the Standard <i>JumpDiffusionEdge</i> Instance #2</li>
 * 		<li><i>JumpDiffusionEdge</i> Constructor</li>
 * 		<li>Retrieve the Edge Time Increment</li>
 * 		<li>Retrieve the Start Realization</li>
 * 		<li>Retrieve the Deterministic Component</li>
 * 		<li>Retrieve the Diffusion Stochastic Component</li>
 * 		<li>Retrieve the Diffusion Wander Realization</li>
 * 		<li>Retrieve the Jump Stochastic Component</li>
 * 		<li>Retrieve the Jump Wander Realization</li>
 * 		<li>Retrieve the Finish Realization</li>
 * 		<li>Retrieve the Gross Change</li>
 * 		<li>Retrieve the Stochastic Diffusion Edge Instance</li>
 * 		<li>Retrieve the Stochastic Jump Edge Instance</li>
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

public class JumpDiffusionEdge
{
	private double _start = Double.NaN;
	private double _deterministic = Double.NaN;
	private JumpDiffusionEdgeUnit _unit = null;
	private StochasticEdgeJump _stochasticEdgeJump = null;
	private StochasticEdgeDiffusion _stochasticEdgeDiffusion = null;

	/**
	 * Construct the Standard <i>JumpDiffusionEdge</i> Instance #1
	 * 
	 * @param start The Starting Random Variable Realization
	 * @param deterministic The Deterministic Increment Component
	 * @param diffusionStochastic The Diffusion Stochastic Edge Change Amount
	 * @param jumpOccurred TRUE - The Jump Occurred in this Edge Period
	 * @param hazardRate The Hazard Rate
	 * @param hazardIntegral The Level Hazard Integral
	 * @param jumpTarget The Jump Target
	 * @param timeIncrement The Time Increment
	 * @param unitDiffusion The Diffusion Random Variable
	 * @param unitJump The Jump Random Variable
	 * 
	 * @return The <i>JumpDiffusionEdge</i> Instance
	 */

	public static final JumpDiffusionEdge Standard (
		final double start,
		final double deterministic,
		final double diffusionStochastic,
		final boolean jumpOccurred,
		final double hazardRate,
		final double hazardIntegral,
		final double jumpTarget,
		final double timeIncrement,
		final double unitDiffusion,
		final double unitJump)
	{
		try {
			return new JumpDiffusionEdge (
				start,
				deterministic,
				new StochasticEdgeDiffusion (diffusionStochastic),
				new StochasticEdgeJump (jumpOccurred, hazardRate, hazardIntegral, jumpTarget),
				new JumpDiffusionEdgeUnit (timeIncrement, unitDiffusion, unitJump)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Standard <i>JumpDiffusionEdge</i> Instance #2
	 * 
	 * @param start The Starting Random Variable Realization
	 * @param deterministic The Deterministic Increment Component
	 * @param diffusionStochastic The Diffusion Stochastic Edge Change Amount
	 * @param stochasticEdgeJump The Stochastic Jump Edge Instance
	 * @param unit The Random Unit Realization
	 * 
	 * @return The <i>JumpDiffusionEdge</i> Instance
	 */

	public static final JumpDiffusionEdge Standard (
		final double start,
		final double deterministic,
		final double diffusionStochastic,
		final StochasticEdgeJump stochasticEdgeJump,
		final JumpDiffusionEdgeUnit unit)
	{
		try {
			return new JumpDiffusionEdge (
				start,
				deterministic,
				new StochasticEdgeDiffusion (diffusionStochastic),
				stochasticEdgeJump,
				unit
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>JumpDiffusionEdge</i> Constructor
	 * 
	 * @param start The Starting Random Variable Realization
	 * @param deterministic The Deterministic Increment Component
	 * @param stochasticEdgeDiffusion The Stochastic Diffusion Edge Instance
	 * @param stochasticEdgeJump The Stochastic Jump Edge Instance
	 * @param unit The Random Unit Realization
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public JumpDiffusionEdge (
		final double start,
		final double deterministic,
		final StochasticEdgeDiffusion stochasticEdgeDiffusion,
		final StochasticEdgeJump stochasticEdgeJump,
		final JumpDiffusionEdgeUnit unit)
		throws Exception
	{
		_stochasticEdgeJump = stochasticEdgeJump;
		_stochasticEdgeDiffusion = stochasticEdgeDiffusion;

		if (!NumberUtil.IsValid (_start = start) ||
			!NumberUtil.IsValid (_deterministic = deterministic) ||
			(null == _stochasticEdgeDiffusion && null == _stochasticEdgeJump) ||
			null == (_unit = unit))
		{
			throw new Exception ("JumpDiffusionEdge Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Edge Time Increment
	 * 
	 * @return The Edge Time Increment
	 */

	public double timeIncrement()
	{
		return _unit.timeIncrement();
	}

	/**
	 * Retrieve the Start Realization
	 * 
	 * @return The Start Realization
	 */

	public double start()
	{
		return _start;
	}

	/**
	 * Retrieve the Deterministic Component
	 * 
	 * @return The Deterministic Component
	 */

	public double deterministic()
	{
		return _deterministic;
	}

	/**
	 * Retrieve the Diffusion Stochastic Component
	 * 
	 * @return The Diffusion Stochastic Component
	 */

	public double diffusionStochastic()
	{
		return null == _stochasticEdgeDiffusion ? 0. : _stochasticEdgeDiffusion.change();
	}

	/**
	 * Retrieve the Diffusion Wander Realization
	 * 
	 * @return The Diffusion Wander Realization
	 */

	public double diffusionWander()
	{
		return _unit.diffusion();
	}

	/**
	 * Retrieve the Jump Stochastic Component
	 * 
	 * @return The Jump Stochastic Component
	 */

	public double jumpStochastic()
	{
		return null == _stochasticEdgeJump ? 0. : _stochasticEdgeJump.target();
	}

	/**
	 * Retrieve the Jump Wander Realization
	 * 
	 * @return The Jump Wander Realization
	 */

	public double jumpWander()
	{
		return _unit.jump();
	}

	/**
	 * Retrieve the Finish Realization
	 * 
	 * @return The Finish Realization
	 */

	public double finish()
	{
		return null == _stochasticEdgeJump || !_stochasticEdgeJump.jumpOccurred() ?
			_start + _deterministic + diffusionStochastic() : _stochasticEdgeJump.target();
	}

	/**
	 * Retrieve the Gross Change
	 * 
	 * @return The Gross Change
	 */

	public double grossChange()
	{
		return finish() - _start;
	}

	/**
	 * Retrieve the Stochastic Diffusion Edge Instance
	 * 
	 * @return The Stochastic Diffusion Edge Instance
	 */

	public StochasticEdgeDiffusion stochasticDiffusionEdge()
	{
		return _stochasticEdgeDiffusion;
	}

	/**
	 * Retrieve the Stochastic Jump Edge Instance
	 * 
	 * @return The Stochastic Jump Edge Instance
	 */

	public StochasticEdgeJump stochasticJumpEdge()
	{
		return _stochasticEdgeJump;
	}
}
