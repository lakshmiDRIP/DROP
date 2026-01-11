
package org.drip.measure.realization;

import org.drip.measure.dynamics.DiffusionEvaluator;
import org.drip.measure.dynamics.LocalEvaluator;
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
 * <i>DiffusionEvolver</i> implements the Functionality that guides the Single Factor R<sup>1</sup> Diffusion
 * 	Random Process Variable Evolution. It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>DiffusionEvolver</i> Constructor</li>
 * 		<li>Retrieve the Diffusion Evaluator</li>
 * 		<li>Generate the <i>JumpDiffusionEdge</i> Instance from the specified Jump Diffusion Instance</li>
 * 		<li>Generate the <i>JumpDiffusionEdge</i> Instance Backwards from the specified Jump Diffusion Instance</li>
 * 		<li>Generate the Array of Adjacent <i>JumpDiffusionEdge</i> from the specified Random Variate Array</li>
 * 		<li>Generate the Array of <i>JumpDiffusionVertex</i> Snaps from the specified Random Variate Array</li>
 * 		<li>Generate the Array of <i>JumpDiffusionVertex</i> Snaps from the specified Random Variate Array</li>
 * 		<li>Generate the Array of <i>JumpDiffusionVertex</i> Snaps Backwards from the specified Random Variate Array</li>
 * 		<li>Generate the Adjacent <i>JumpDiffusionEdge</i> Instance from the specified Random Variate and a Weiner Driver</li>
 * 		<li>Generate the Adjacent <i>JumpDiffusionEdge</i> Instance from the specified Random Variate and a Jump Driver</li>
 * 		<li>Generate the Adjacent <i>JumpDiffusionEdge</i> Instance from the specified Random Variate and Jump/Weiner Drivers</li>
 * 		<li>Generate the Adjacent <i>JumpDiffusionEdge</i> Instance from the specified Random Variate and Weiner/Jump Drivers</li>
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

public class DiffusionEvolver
{
	private DiffusionEvaluator _evaluator = null;

	/**
	 * <i>DiffusionEvolver</i> Constructor
	 * 
	 * @param evaluator The Diffusion Evaluator Instance
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public DiffusionEvolver (
		final DiffusionEvaluator evaluator)
		throws Exception
	{
		if (null == (_evaluator = evaluator)) {
			throw new Exception ("DiffusionEvolver Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Diffusion Evaluator
	 * 
	 * @return The Diffusion Evaluator
	 */

	public DiffusionEvaluator evaluator()
	{
		return _evaluator;
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

	public JumpDiffusionEdge increment (
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

		try {
			LocalEvaluator localVolatilityEvaluator = _evaluator.localVolatilityEvaluator();

			return JumpDiffusionEdge.Standard (
				jumpDiffusionVertex.value(),
				_evaluator.localDriftEvaluator().value (jumpDiffusionVertex) * timeIncrement,
				null == localVolatilityEvaluator ? 0. :
					localVolatilityEvaluator.value (jumpDiffusionVertex) *
						jumpDiffusionEdgeUnit.diffusion() * Math.sqrt (Math.abs (timeIncrement)),
				null,
				jumpDiffusionEdgeUnit
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the <i>JumpDiffusionEdge</i> Instance Backwards from the specified Jump Diffusion Instance
	 * 
	 * @param jumpDiffusionVertex The <i>JumpDiffusionVertex</i> Instance
	 * @param jumpDiffusionEdgeUnit The Random Unit Realization
	 * @param timeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Reverse <i>JumpDiffusionEdge</i> Instance
	 */

	public JumpDiffusionEdge incrementReverse (
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

		try {
			LocalEvaluator localVolatilityEvaluator = _evaluator.localVolatilityEvaluator();

			return JumpDiffusionEdge.Standard (
				jumpDiffusionVertex.value(),
				-1. * _evaluator.localDriftEvaluator().value (jumpDiffusionVertex) * timeIncrement,
				null == localVolatilityEvaluator ? 0. :
					-1. * localVolatilityEvaluator.value (jumpDiffusionVertex) *
						jumpDiffusionEdgeUnit.diffusion() * Math.sqrt (Math.abs (timeIncrement)),
				null,
				jumpDiffusionEdgeUnit
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Array of Adjacent <i>JumpDiffusionEdge</i> from the specified Random Variate Array
	 * 
	 * @param jumpDiffusionVertex The <i>JumpDiffusionVertex</i> Instance
	 * @param jumpDiffusionEdgeUnitArray Array of Random Unit Realizations
	 * @param timeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Array of Adjacent <i>JumpDiffusionEdge</i> Instances
	 */

	public JumpDiffusionEdge[] incrementSequence (
		final JumpDiffusionVertex jumpDiffusionVertex,
		final JumpDiffusionEdgeUnit[] jumpDiffusionEdgeUnitArray,
		final double timeIncrement)
	{
		if (null == jumpDiffusionEdgeUnitArray || 0 == jumpDiffusionEdgeUnitArray.length) {
			return null;
		}

		JumpDiffusionVertex jumpDiffusionVertexIterator = jumpDiffusionVertex;
		JumpDiffusionEdge[] jumpDiffusionEdgeArray = 0 == jumpDiffusionEdgeUnitArray.length ?
			null : new JumpDiffusionEdge[jumpDiffusionEdgeUnitArray.length];

		for (int jumpDiffusionEdgeUnitIndex = 0;
			jumpDiffusionEdgeUnitIndex < jumpDiffusionEdgeUnitArray.length;
			++jumpDiffusionEdgeUnitIndex)
		{
			if (null == (
				jumpDiffusionEdgeArray[jumpDiffusionEdgeUnitIndex] = increment (
					jumpDiffusionVertexIterator,
					jumpDiffusionEdgeUnitArray[jumpDiffusionEdgeUnitIndex],
					timeIncrement
				)
			))
			{
				return null;
			}

			try {
				double hazardIntegral = 0.;
				boolean jumpOccurred = false;

				StochasticEdgeJump stochasticEdgeJump =
					jumpDiffusionEdgeArray[jumpDiffusionEdgeUnitIndex].stochasticJumpEdge();

				if (null != stochasticEdgeJump) {
					jumpOccurred = stochasticEdgeJump.jumpOccurred();

					hazardIntegral = stochasticEdgeJump.hazardIntegral();
				}

				jumpDiffusionVertexIterator = new JumpDiffusionVertex (
					jumpDiffusionVertexIterator.time() + timeIncrement,
					jumpDiffusionEdgeArray[jumpDiffusionEdgeUnitIndex].finish(),
					jumpDiffusionVertexIterator.cumulativeHazardIntegral() + hazardIntegral,
					jumpOccurred || jumpDiffusionVertexIterator.jumpOccurred()
				);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return jumpDiffusionEdgeArray;
	}

	/**
	 * Generate the Array of <i>JumpDiffusionVertex</i> Snaps from the specified Random Variate Array
	 * 
	 * @param jumpDiffusionVertex The <i>JumpDiffusionVertex</i> Instance
	 * @param jumpDiffusionEdgeUnitArray Array of Random Unit Realizations
	 * @param timeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Array of <i>JumpDiffusionVertex</i> Snaps
	 */

	public JumpDiffusionVertex[] vertexSequence (
		final JumpDiffusionVertex jumpDiffusionVertex,
		final JumpDiffusionEdgeUnit[] jumpDiffusionEdgeUnitArray,
		final double timeIncrement)
	{
		if (null == jumpDiffusionEdgeUnitArray) {
			return null;
		}

		JumpDiffusionVertex previousJumpDiffusionVertex = jumpDiffusionVertex;
		JumpDiffusionVertex[] jumpDiffusionVertexArray =
			new JumpDiffusionVertex[jumpDiffusionEdgeUnitArray.length + 1];
		jumpDiffusionVertexArray[0] = jumpDiffusionVertex;

		for (int jumpDiffusionEdgeUnitIndex = 0;
			jumpDiffusionEdgeUnitIndex < jumpDiffusionEdgeUnitArray.length;
			++jumpDiffusionEdgeUnitIndex)
		{
			JumpDiffusionEdge jumpDiffusionEdge = increment (
				previousJumpDiffusionVertex,
				jumpDiffusionEdgeUnitArray[jumpDiffusionEdgeUnitIndex],
				timeIncrement
			);

			if (null == jumpDiffusionEdge) {
				return null;
			}

			try {
				StochasticEdgeJump stochasticEdgeJump = jumpDiffusionEdge.stochasticJumpEdge();

				boolean jumpOccurred = false;
				double hazardIntegral = 0.;

				if (null != stochasticEdgeJump) {
					jumpOccurred = stochasticEdgeJump.jumpOccurred();

					hazardIntegral = stochasticEdgeJump.hazardIntegral();
				}

				previousJumpDiffusionVertex = jumpDiffusionVertexArray[jumpDiffusionEdgeUnitIndex + 1] =
					new JumpDiffusionVertex (
						previousJumpDiffusionVertex.time() + timeIncrement,
						jumpDiffusionEdge.finish(),
						previousJumpDiffusionVertex.cumulativeHazardIntegral() + hazardIntegral,
						jumpOccurred || previousJumpDiffusionVertex.jumpOccurred()
					);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return jumpDiffusionVertexArray;
	}

	/**
	 * Generate the Array of <i>JumpDiffusionVertex</i> Snaps from the specified Random Variate Array
	 * 
	 * @param jumpDiffusionVertex The <i>JumpDiffusionVertex</i> Instance
	 * @param jumpDiffusionEdgeUnitArray Array of Random Unit Realizations
	 * @param timeIncrementArray The Time Increment Evolution Unit
	 * 
	 * @return The Array of <i>JumpDiffusionVertex</i> Snaps
	 */

	public JumpDiffusionVertex[] vertexSequence (
		final JumpDiffusionVertex jumpDiffusionVertex,
		final JumpDiffusionEdgeUnit[] jumpDiffusionEdgeUnitArray,
		final double[] timeIncrementArray)
	{
		if (null == jumpDiffusionEdgeUnitArray || null == timeIncrementArray) {
			return null;
		}

		JumpDiffusionVertex previousJumpDiffusionVertex = jumpDiffusionVertex;
		JumpDiffusionVertex[] jumpDiffusionVertexArray =
			new JumpDiffusionVertex[jumpDiffusionEdgeUnitArray.length + 1];
		jumpDiffusionVertexArray[0] = jumpDiffusionVertex;

		if (jumpDiffusionEdgeUnitArray.length != timeIncrementArray.length) {
			return null;
		}

		for (int timeIndex = 0; timeIndex < jumpDiffusionEdgeUnitArray.length; ++timeIndex) {
			JumpDiffusionEdge jumpDiffusionEdge = increment (
				previousJumpDiffusionVertex,
				jumpDiffusionEdgeUnitArray[timeIndex],
				timeIncrementArray[timeIndex]
			);

			if (null == jumpDiffusionEdge) {
				return null;
			}

			try {
				StochasticEdgeJump stochasticEdgeJump = jumpDiffusionEdge.stochasticJumpEdge();

				boolean bJumpOccurred = false;
				double hazardIntegral = 0.;

				if (null != stochasticEdgeJump) {
					bJumpOccurred = stochasticEdgeJump.jumpOccurred();

					hazardIntegral = stochasticEdgeJump.hazardIntegral();
				}

				previousJumpDiffusionVertex = jumpDiffusionVertexArray[timeIndex + 1] =
					new JumpDiffusionVertex (
						previousJumpDiffusionVertex.time() + timeIncrementArray[timeIndex],
						jumpDiffusionEdge.finish(),
						previousJumpDiffusionVertex.cumulativeHazardIntegral() + hazardIntegral,
						bJumpOccurred || previousJumpDiffusionVertex.jumpOccurred()
					);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return jumpDiffusionVertexArray;
	}

	/**
	 * Generate the Array of <i>JumpDiffusionVertex</i> Snaps Backwards from the specified Random Variate
	 * 	Array
	 * 
	 * @param jumpDiffusionVertex The <i>JumpDiffusionVertex</i> Instance
	 * @param jumpDiffusionEdgeUnitArray Array of Random Unit Realizations
	 * @param timeIncrementArray The Time Increment Evolution Unit
	 * 
	 * @return The Array of Reverse <i>JumpDiffusionVertex</i> Snaps
	 */

	public JumpDiffusionVertex[] vertexSequenceReverse (
		final JumpDiffusionVertex jumpDiffusionVertex,
		final JumpDiffusionEdgeUnit[] jumpDiffusionEdgeUnitArray,
		final double[] timeIncrementArray)
	{
		if (null == jumpDiffusionEdgeUnitArray || null == timeIncrementArray) {
			return null;
		}

		JumpDiffusionVertex previousJumpDiffusionVertex = jumpDiffusionVertex;
		JumpDiffusionVertex[] jumpDiffusionVertexArray =
			new JumpDiffusionVertex[jumpDiffusionEdgeUnitArray.length + 1];
		jumpDiffusionVertexArray[jumpDiffusionEdgeUnitArray.length] = jumpDiffusionVertex;

		if (jumpDiffusionEdgeUnitArray.length != timeIncrementArray.length) {
			return null;
		}

		for (int timeIndex = jumpDiffusionEdgeUnitArray.length - 1; timeIndex >= 0; --timeIndex) {
			JumpDiffusionEdge jumpDiffusionEdge = incrementReverse (
				previousJumpDiffusionVertex,
				jumpDiffusionEdgeUnitArray[timeIndex],
				timeIncrementArray[timeIndex]
			);

			if (null == jumpDiffusionEdge) {
				return null;
			}

			try {
				StochasticEdgeJump stochasticEdgeJump = jumpDiffusionEdge.stochasticJumpEdge();

				boolean jumpOccurred = false;
				double hazardIntegral = 0.;

				if (null != stochasticEdgeJump) {
					jumpOccurred = stochasticEdgeJump.jumpOccurred();

					hazardIntegral = stochasticEdgeJump.hazardIntegral();
				}

				previousJumpDiffusionVertex = jumpDiffusionVertexArray[timeIndex] =
					new JumpDiffusionVertex (
						previousJumpDiffusionVertex.time() - timeIncrementArray[timeIndex],
						jumpDiffusionEdge.finish(),
						previousJumpDiffusionVertex.cumulativeHazardIntegral() + hazardIntegral,
						jumpOccurred || previousJumpDiffusionVertex.jumpOccurred()
					);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return jumpDiffusionVertexArray;
	}

	/**
	 * Generate the Adjacent <i>JumpDiffusionEdge</i> Instance from the specified Random Variate and a Weiner
	 * 	Driver
	 * 
	 * @param jumpDiffusionVertex The <i>JumpDiffusionVertex</i> Instance
	 * @param timeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Adjacent <i>JumpDiffusionEdge</i> Instance
	 */

	public JumpDiffusionEdge weinerIncrement (
		final JumpDiffusionVertex jumpDiffusionVertex,
		final double timeIncrement)
	{
		try {
			return increment (
				jumpDiffusionVertex,
				JumpDiffusionEdgeUnit.GaussianDiffusion (timeIncrement),
				timeIncrement
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Adjacent <i>JumpDiffusionEdge</i> Instance from the specified Random Variate and a Jump
	 * 	Driver
	 * 
	 * @param jumpDiffusionVertex The <i>JumpDiffusionVertex</i> Instance
	 * @param timeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Adjacent <i>JumpDiffusionEdge</i> Instance
	 */

	public JumpDiffusionEdge jumpIncrement (
		final JumpDiffusionVertex jumpDiffusionVertex,
		final double timeIncrement)
	{
		return increment (
			jumpDiffusionVertex,
			JumpDiffusionEdgeUnit.UniformJump (timeIncrement),
			timeIncrement
		);
	}

	/**
	 * Generate the Adjacent <i>JumpDiffusionEdge</i> Instance from the specified Random Variate and
	 *  Jump/Weiner Drivers
	 * 
	 * @param jumpDiffusionVertex The <i>JumpDiffusionVertex</i> Instance
	 * @param timeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Adjacent <i>JumpDiffusionEdge</i> Instance
	 */

	public JumpDiffusionEdge jumpWeinerIncrement (
		final JumpDiffusionVertex jumpDiffusionVertex,
		final double timeIncrement)
	{
		try {
			return increment (
				jumpDiffusionVertex,
				new JumpDiffusionEdgeUnit (timeIncrement, NormalQuadrature.Random(), Math.random()),
				timeIncrement
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Adjacent <i>JumpDiffusionEdge</i> Instance from the specified Random Variate and
	 * 	Weiner/Jump Drivers
	 * 
	 * @param jumpDiffusionVertex The <i>JumpDiffusionVertex</i> Instance
	 * @param timeIncrement The Time Increment Evolution Unit
	 * 
	 * @return The Adjacent <i>JumpDiffusionEdge</i> Instance
	 */

	public JumpDiffusionEdge weinerJumpIncrement (
		final JumpDiffusionVertex jumpDiffusionVertex,
		final double timeIncrement)
	{
		try {
			return increment (
				jumpDiffusionVertex,
				new JumpDiffusionEdgeUnit (timeIncrement, NormalQuadrature.Random(), Math.random()),
				timeIncrement
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
