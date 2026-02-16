
package org.drip.fdm.implicit;

import org.drip.fdm.definition.EvolutionGrid1D;
import org.drip.fdm.definition.R1EvolutionSnapshot;
import org.drip.function.definition.RdToR1;
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
 * <i>DiscretizedDiffusionEvolver1D</i> implements the 1D Discretized Diffusion State-Space Evolution Scheme.
 * 	The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Datta, B. N. (2010): <i>Numerical Linear Algebra and Applications 2<sup>nd</sup> Edition</i>
 * 				<b>SIAM</b> Philadelphia, PA
 * 		</li>
 * 		<li>
 * 			Cebeci, T. (2002): <i>Convective Heat Transfer</i> <b>Horizon Publishing</b> Hammond, IN
 * 		</li>
 * 		<li>
 * 			Crank, J., and P. Nicolson (1947): A Practical Method for Numerical Evaluation of Solutions of
 * 				Partial Differential Equations of the Heat Conduction Type <i>Proceedings of the Cambridge
 * 				Philosophical Society</i> <b>43 (1)</b> 50-67
 * 		</li>
 * 		<li>
 * 			Thomas, J. W. (1995): <i>Numerical Partial Differential Equations: Finite Difference Methods</i>
 * 				<b>Springer-Verlag</b> Berlin, Germany
 * 		</li>
 * 		<li>
 * 			Wikipedia (2023): Alternating-direction implicit method
 * 				https://en.wikipedia.org/wiki/Alternating-direction_implicit_method
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Crank–Nicolson method
 * 				https://en.wikipedia.org/wiki/Crank%E2%80%93Nicolson_method
 * 		</li>
 * 	</ul>
 *
 * It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Set up the State Transition Matrix for the Factor Index</li>
 * 		<li>Update the State Response Array</li>
 * 		<li><i>DiscretizedDiffusionEvolver1D</i> Constructor</li>
 * 		<li>Retrieve the 1D Evolution Grid</li>
 * 		<li>Retrieve the R<sup>d</sup> to R<sup>1</sup> Diffusion Function</li>
 * 		<li>Retrieve the "Diagnostics On" Mode</li>
 * 		<li>Compute the von Neumann Stability Number</li>
 * 		<li>Indicate if the Step Sizes enable Stable Usage of the Crank-Nicolson Scheme</li>
 * 		<li>Evolve the State Response from the Starting Value</li>
 *  </ul>
 * 
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/fdm/README.md">Multi-dimensional Finite Difference Evolution Schemes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/fdm/implicit/README.md">Finite Difference Implicit Evolution Schemes</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class DiscretizedDiffusionEvolver1D
{
	private boolean _diagnosticsOn = false;
	private RdToR1 _diffusionFunction = null;
	private EvolutionGrid1D _evolutionGrid1D = null;

	private final double[] zeroRHSArray()
	{
		int factorPredictorCount = _evolutionGrid1D.factorPredictorArray().length;

		double[] zeroRHSArray = new double[factorPredictorCount];

		for (int factorIndex = 0; factorIndex < factorPredictorCount; ++factorIndex) {
			zeroRHSArray[factorIndex] = 0.;
		}

		return zeroRHSArray;
	}

	private final double[][] zeroStateResponseTransitionMatrix()
	{
		int factorPredictorCount = _evolutionGrid1D.factorPredictorArray().length;

		double[][] zeroStateResponseTransitionMatrix =
			new double[factorPredictorCount][factorPredictorCount];

		for (int factorIndexI = 0; factorIndexI < factorPredictorCount; ++factorIndexI) {
			for (int factorIndexJ = 0; factorIndexJ < factorPredictorCount; ++factorIndexJ) {
				zeroStateResponseTransitionMatrix[factorIndexI][factorIndexJ] = 0.;
			}
		}

		return zeroStateResponseTransitionMatrix;
	}

	/**
	 * <i>DiscretizedDiffusionEvolver1D</i> Constructor
	 * 
	 * @param evolutionGrid1D R<sup>1</sup> Evolution Increment
	 * @param diffusionFunction Diffusion Function
	 * @param diagnosticsOn TRUE - "Diagnostics On" Mode
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	protected DiscretizedDiffusionEvolver1D (
		final EvolutionGrid1D evolutionGrid1D,
		final RdToR1 diffusionFunction,
		final boolean diagnosticsOn)
		throws Exception
	{
		if (null == (_evolutionGrid1D = evolutionGrid1D) || null == (_diffusionFunction = diffusionFunction))
		{
			throw new Exception ("DiscretizedDiffusionEvolver1D Constructor => Invalid Inputs");
		}

		_diagnosticsOn = diagnosticsOn;
	}

	/**
	 * Set up the State Transition Matrix for the Factor Index
	 * 
	 * @param stateResponseTransitionMatrix State Response Transition Matrix
	 * @param rhsArray RHS Array
	 * @param stateResponseArray Array of State Responses
	 * @param factorPredictorArray Array of Factor Predictors
	 * @param factorIndex Factor Index
	 * @param vonNeumannStabilityNumber von-Neumann Stability Number for the Time-step
	 * 
	 * @return TRUE - The State Transition Matrix successfully set up for the Factor Index
	 */

	protected abstract boolean setUpStateTransition (
		final double[][] stateResponseTransitionMatrix,
		final double[] rhsArray,
		final double[] stateResponseArray,
		final double[] factorPredictorArray,
		final int factorIndex,
		final double vonNeumannStabilityNumber
	);

	/**
	 * Update the State Response Array
	 * 
	 * @param stateResponseTransitionMatrix State Response Transition Matrix
	 * @param rhsArray RHS Array
	 * 
	 * @return State Response Array
	 */

	protected abstract double[] updateStateResponse (
		final double[][] stateResponseTransitionMatrix,
		final double[] rhsArray
	);

	/**
	 * Retrieve the 1D Evolution Grid
	 * 
	 * @return 1D Evolution Grid
	 */

	public EvolutionGrid1D evolutionGrid1D()
	{
		return _evolutionGrid1D;
	}

	/**
	 * Retrieve the R<sup>d</sup> to R<sup>1</sup> Diffusion Function
	 * 
	 * @return R<sup>d</sup> to R<sup>1</sup> Diffusion Function
	 */

	public RdToR1 diffusionFunction()
	{
		return _diffusionFunction;
	}

	/**
	 * Retrieve the "Diagnostics On" Mode
	 * 
	 * @return "Diagnostics On" Mode
	 */

	public boolean diagnosticsOn()
	{
		return _diagnosticsOn;
	}

	/**
	 * Compute the von Neumann Stability Number
	 * 
	 * @param time Time
	 * @param timeIncrement Time Increment
	 * @param factor Factor
	 * @param factorIncrement Factor Increment
	 * 
	 * @return von Neumann Stability Number
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double vonNeumannStabilityNumber (
		final double time,
		final double timeIncrement,
		final double factor,
		final double factorIncrement)
		throws Exception
	{
		double stepSizeHypotenuseSquare = factorIncrement * factorIncrement;

		return 0. == stepSizeHypotenuseSquare ? 0. : 0.5 * timeIncrement * _diffusionFunction.evaluate (
			new double[] {time, factor}
		) / stepSizeHypotenuseSquare;
	}

	/**
	 * Indicate if the Step Sizes enable Stable Usage of the Crank-Nicolson Scheme
	 * 
	 * @param time Time
	 * @param timeIncrement Time Increment
	 * @param factor Factor
	 * @param factorIncrement Factor Increment
	 * 
	 * @return TRUE - Step Sizes enable Stable Usage of the Crank-Nicolson Scheme
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public boolean stabilityCheck (
		final double time,
		final double timeIncrement,
		final double factor,
		final double factorIncrement)
		throws Exception
	{
		double vonNeumannStabilityNumber = vonNeumannStabilityNumber (
			time,
			timeIncrement,
			factor,
			factorIncrement
		);

		return 0. != vonNeumannStabilityNumber && 0.5 >= vonNeumannStabilityNumber;
	}

	/**
	 * Evolve the State Response from the Starting Value
	 * 
	 * @param startingStateResponseArray Starting State Response Array
	 * 
	 * @return Time Map of Factor Predictor/State Response Array
	 */

	public R1EvolutionSnapshot evolve (
		final double[] startingStateResponseArray)
	{
		double[] factorPredictorArray = _evolutionGrid1D.factorPredictorArray();

		if (null == startingStateResponseArray ||
			startingStateResponseArray.length != factorPredictorArray.length)
		{
			return null;
		}

		double[] vonNeumannStabilityMetricArray = null;
		R1EvolutionSnapshot r1EvolutionSnapshot = null;
		double[] stateResponseArray = startingStateResponseArray;

		if (_diagnosticsOn) {
			vonNeumannStabilityMetricArray = new double[factorPredictorArray.length];
		}

		try {
			r1EvolutionSnapshot = new R1EvolutionSnapshot (factorPredictorArray);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

		double[] timeArray = _evolutionGrid1D.timeArray();

		if (_diagnosticsOn) {
			if (!r1EvolutionSnapshot.addStateResponse (
				timeArray[0],
				startingStateResponseArray,
				zeroStateResponseTransitionMatrix(),
				zeroRHSArray(),
				vonNeumannStabilityMetricArray
			))
			{
				return null;
			}
		} else {
			if (!r1EvolutionSnapshot.addStateResponse (timeArray[0], startingStateResponseArray)) {
				return null;
			}
		}

		for (int timeIndex = 1; timeIndex < timeArray.length; ++timeIndex) {
			double[] rhsArray = zeroRHSArray();

			double[][] stateResponseTransitionMatrix = zeroStateResponseTransitionMatrix();

			for (int factorIndex = 0; factorIndex < factorPredictorArray.length; ++factorIndex) {
				double vonNeumannStabilityNumber = Double.NaN;

				try {
					if (!NumberUtil.IsValid (
						vonNeumannStabilityNumber = vonNeumannStabilityNumber (
							timeArray[timeIndex],
							timeArray[timeIndex] - timeArray[timeIndex - 1],
							factorPredictorArray[factorIndex],
							0 == factorIndex ? factorPredictorArray[1] - factorPredictorArray[0] :
								factorPredictorArray[factorIndex] - factorPredictorArray[factorIndex - 1]
						)
					))
					{
						return null;
					}
				} catch (Exception e) {
					e.printStackTrace();

					return null;
				}

				if (_diagnosticsOn) {
					vonNeumannStabilityMetricArray[factorIndex] = vonNeumannStabilityNumber;
				}

				if (!setUpStateTransition (
					stateResponseTransitionMatrix,
					rhsArray,
					stateResponseArray,
					factorPredictorArray,
					factorIndex,
					vonNeumannStabilityNumber
				))
				{
					return null;
				}
			}

			if (null == (
				stateResponseArray = updateStateResponse (stateResponseTransitionMatrix, rhsArray)
			))
			{
				return null;
			}

			if (_diagnosticsOn) {
				if (!r1EvolutionSnapshot.addStateResponse (
					timeArray[timeIndex],
					stateResponseArray,
					stateResponseTransitionMatrix,
					rhsArray,
					vonNeumannStabilityMetricArray
				))
				{
					return null;
				}
			} else {
				if (!r1EvolutionSnapshot.addStateResponse (timeArray[timeIndex], stateResponseArray)) {
					return null;
				}
			}
		}

		return r1EvolutionSnapshot;
	}
}
