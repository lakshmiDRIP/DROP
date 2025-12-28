
package org.drip.measure.realization;

import org.drip.measure.gaussian.NormalQuadrature;
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
 * <i>JumpDiffusionEdgeUnit</i> holds the Jump Diffusion R<sup>d</sup> Unit Edge Realizations. It provides
 * 	the following Functionality:
 *
 *  <ul>
 * 		<li>Generate a <i>JumpDiffusionEdgeUnit</i> Uniform Diffusion Instance</li>
 * 		<li>Generate a <i>JumpDiffusionEdgeUnit</i> Gaussian Diffusion Realization Instance</li>
 * 		<li>Generate a <i>JumpDiffusionEdgeUnit</i> Uniform Jump Realization</li>
 * 		<li>Generate a <i>JumpDiffusionEdgeUnit</i> Gaussian Jump Realization</li>
 * 		<li>Generate an Array of <i>JumpDiffusionEdgeUnit</i> Realizations #1</li>
 * 		<li>Generate an Array of <i>JumpDiffusionEdgeUnit</i> Realizations #2</li>
 * 		<li>Generate an Array of <i>JumpDiffusionEdgeUnit</i> Realizations #3</li>
 * 		<li><i>JumpDiffusionEdgeUnit</i> Constructor</li>
 * 		<li>Retrieve the Edge Time Increment</li>
 * 		<li>Retrieve the Diffusion Unit Random Variable</li>
 * 		<li>Retrieve the Jump Unit Random Variable</li>
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

public class JumpDiffusionEdgeUnit
{
	private double _jump = Double.NaN;
	private double _diffusion = Double.NaN;
	private double _timeIncrement = Double.NaN;

	/**
	 * Generate a <i>JumpDiffusionEdgeUnit</i> Uniform Diffusion Instance
	 * 
	 * @param timeIncrement The Time Increment
	 * 
	 * @return The <i>JumpDiffusionEdgeUnit</i> Uniform Diffusion Instance
	 */

	public static final JumpDiffusionEdgeUnit UniformDiffusion (
		final double timeIncrement)
	{
		try {
			return new JumpDiffusionEdgeUnit (timeIncrement, Math.random(), 0.);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a <i>JumpDiffusionEdgeUnit</i> Gaussian Diffusion Realization Instance
	 * 
	 * @param timeIncrement The Time Increment
	 * 
	 * @return The <i>JumpDiffusionEdgeUnit</i> Gaussian Diffusion Realization Instance
	 */

	public static final JumpDiffusionEdgeUnit GaussianDiffusion (
		final double timeIncrement)
	{
		try {
			return new JumpDiffusionEdgeUnit (timeIncrement, NormalQuadrature.Random(), 0.);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a <i>JumpDiffusionEdgeUnit</i> Uniform Jump Realization
	 * 
	 * @param timeIncrement The Time Increment
	 * 
	 * @return The <i>JumpDiffusionEdgeUnit</i> Uniform Jump Realization
	 */

	public static final JumpDiffusionEdgeUnit UniformJump (
		final double timeIncrement)
	{
		try {
			return new JumpDiffusionEdgeUnit (timeIncrement, 0., Math.random());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a <i>JumpDiffusionEdgeUnit</i> Gaussian Jump Realization
	 * 
	 * @param timeIncrement The Time Increment
	 * 
	 * @return The <i>JumpDiffusionEdgeUnit</i> Gaussian Jump Realization
	 */

	public static final JumpDiffusionEdgeUnit GaussianJump (
		final double timeIncrement)
	{
		try {
			return new JumpDiffusionEdgeUnit (timeIncrement, 0., NormalQuadrature.Random());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate an Array of <i>JumpDiffusionEdgeUnit</i> Realizations #1
	 * 
	 * @param timeIncrementArray The Array of Time Increments
	 * @param diffusionRealizationArray The Array of Diffusion Realizations
	 * 
	 * @return Array of <i>JumpDiffusionEdgeUnit</i> Realizations
	 */

	public static final JumpDiffusionEdgeUnit[] Diffusion (
		final double[] timeIncrementArray,
		final double[] diffusionRealizationArray)
	{
		if (null == diffusionRealizationArray) {
			return null;
		}

		int size = diffusionRealizationArray.length;
		JumpDiffusionEdgeUnit[] jumpDiffusionEdgeUnitArray =
			0 == size ? null : new JumpDiffusionEdgeUnit[size];

		if (0 == size || size != timeIncrementArray.length) {
			return null;
		}

		for (int i = 0; i < size; ++i) {
			try {
				jumpDiffusionEdgeUnitArray[i] = new JumpDiffusionEdgeUnit (
					timeIncrementArray[i],
					diffusionRealizationArray[i],
					0.
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return jumpDiffusionEdgeUnitArray;
	}

	/**
	 * Generate an Array of <i>JumpDiffusionEdgeUnit</i> Realizations #2
	 * 
	 * @param timeIncrementArray The Array of Time Increments
	 * @param jumpRealizationArray The Array of Jump Realizations
	 * 
	 * @return Array of <i>JumpDiffusionEdgeUnit</i> Realizations
	 */

	public static final JumpDiffusionEdgeUnit[] Jump (
		final double[] timeIncrementArray,
		final double[] jumpRealizationArray)
	{
		if (null == jumpRealizationArray) {
			return null;
		}

		int size = jumpRealizationArray.length;
		JumpDiffusionEdgeUnit[] jumpDiffusionEdgeUnitArray =
			0 == size ? null : new JumpDiffusionEdgeUnit[size];

		if (0 == size || size != timeIncrementArray.length) {
			return null;
		}

		for (int i = 0; i < size; ++i) {
			try {
				jumpDiffusionEdgeUnitArray[i] = new JumpDiffusionEdgeUnit (
					timeIncrementArray[i],
					0.,
					jumpRealizationArray[i]
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return jumpDiffusionEdgeUnitArray;
	}

	/**
	 * Generate an Array of <i>JumpDiffusionEdgeUnit</i> Realizations #3
	 * 
	 * @param timeIncrementArray The Array of Time Increments
	 * @param diffusionRealizationArray The Array of Diffusion Realizations
	 * @param jumpRealizationArray The Array of Jump Realizations
	 * 
	 * @return Array of <i>JumpDiffusionEdgeUnit</i> Realizations
	 */

	public static final JumpDiffusionEdgeUnit[] JumpDiffusion (
		final double[] timeIncrementArray,
		final double[] diffusionRealizationArray,
		final double[] jumpRealizationArray)
	{
		if (null == diffusionRealizationArray || null == jumpRealizationArray) {
			return null;
		}

		int size = diffusionRealizationArray.length;
		JumpDiffusionEdgeUnit[] jumpDiffusionEdgeUnitArray =
			0 == size ? null : new JumpDiffusionEdgeUnit[size];

		if (0 == size || size != jumpRealizationArray.length || size != timeIncrementArray.length) {
			return null;
		}

		for (int i = 0; i < size; ++i) {
			try {
				jumpDiffusionEdgeUnitArray[i] = new JumpDiffusionEdgeUnit (
					timeIncrementArray[i],
					diffusionRealizationArray[i],
					jumpRealizationArray[i]
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return jumpDiffusionEdgeUnitArray;
	}

	/**
	 * <i>JumpDiffusionEdgeUnit</i> Constructor
	 * 
	 * @param timeIncrement The Edge Time Increment
	 * @param diffusion The Diffusion Random Variable
	 * @param jump The Jump Random Variable
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public JumpDiffusionEdgeUnit (
		final double timeIncrement,
		final double diffusion,
		final double jump)
		throws Exception
	{
		if (!NumberUtil.IsValid (_timeIncrement = timeIncrement) || 0. == _timeIncrement ||
			!NumberUtil.IsValid (_diffusion = diffusion) ||
			!NumberUtil.IsValid (_jump = jump))
		{
			throw new Exception ("JumpDiffusionEdgeUnit Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Edge Time Increment
	 * 
	 * @return The Edge Time Increment
	 */

	public double timeIncrement()
	{
		return _timeIncrement;
	}

	/**
	 * Retrieve the Diffusion Unit Random Variable
	 * 
	 * @return The Diffusion Unit Random Variable
	 */

	public double diffusion()
	{
		return _diffusion;
	}

	/**
	 * Retrieve the Jump Unit Random Variable
	 * 
	 * @return The Jump Unit Random Variable
	 */

	public double jump()
	{
		return _jump;
	}
}
