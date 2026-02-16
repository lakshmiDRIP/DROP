
package org.drip.fdm.implicit;

import org.drip.fdm.definition.EvolutionGrid1D;
import org.drip.function.definition.RdToR1;
import org.drip.numerical.linearsolver.NonPeriodicTridiagonalScheme;

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
 * <i>ForwardEulerDiffusion1D</i> implements the 1D Forward-Euler Discretized State-Space Diffusion Evolution
 * 	Scheme. The References are:
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
 * 		<li><i>ForwardEulerDiffusion1D</i> Constructor</li>
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

public class ForwardEulerDiffusion1D
	extends DiscretizedDiffusionEvolver1D
{

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

	@Override protected boolean setUpStateTransition (
		final double[][] stateResponseTransitionMatrix,
		final double[] rhsArray,
		final double[] stateResponseArray,
		final double[] factorPredictorArray,
		final int factorIndex,
		final double vonNeumannStabilityNumber)
	{
		if (0 != factorIndex && factorPredictorArray.length - 1 != factorIndex) {
			stateResponseTransitionMatrix[factorIndex][factorIndex - 1] = 0.;
			stateResponseTransitionMatrix[factorIndex][factorIndex] = 1.;
			stateResponseTransitionMatrix[factorIndex][factorIndex + 1] = 0.;
			rhsArray[factorIndex] = 2. * vonNeumannStabilityNumber * stateResponseArray[factorIndex - 1] +
				(1. - 4. * vonNeumannStabilityNumber) * stateResponseArray[factorIndex] +
					2. * vonNeumannStabilityNumber * stateResponseArray[factorIndex + 1];
		} else if (factorPredictorArray.length - 1 == factorIndex) {
			stateResponseTransitionMatrix[factorIndex][factorIndex - 1] = 0.;
			stateResponseTransitionMatrix[factorIndex][factorIndex] = 1.;
			rhsArray[factorIndex] = 2. * vonNeumannStabilityNumber * stateResponseArray[factorIndex - 1] +
				(1. - 4. * vonNeumannStabilityNumber) * stateResponseArray[factorIndex];
		} else {
			stateResponseTransitionMatrix[factorIndex][factorIndex] = 1.;
			stateResponseTransitionMatrix[factorIndex][factorIndex + 1] = 0.;
			rhsArray[factorIndex] = (1. - 4. * vonNeumannStabilityNumber) * stateResponseArray[factorIndex] +
				2. * vonNeumannStabilityNumber * stateResponseArray[factorIndex + 1];
		}

		return true;
	}

	/**
	 * Update the State Response Array
	 * 
	 * @param stateResponseTransitionMatrix State Response Transition Matrix
	 * @param rhsArray RHS Array
	 * 
	 * @return State Response Array
	 */

	@Override protected double[] updateStateResponse (
		final double[][] stateResponseTransitionMatrix,
		final double[] rhsArray)
	{
		NonPeriodicTridiagonalScheme nonPeriodicTridiagonalScheme = NonPeriodicTridiagonalScheme.Standard (
			stateResponseTransitionMatrix,
			rhsArray
		);

		return null == nonPeriodicTridiagonalScheme ?
			null : nonPeriodicTridiagonalScheme.forwardSweepBackSubstitution();
	}

	/**
	 * <i>ForwardEulerDiffusion1D</i> Constructor
	 * 
	 * @param evolutionGrid1D R<sup>1</sup> Evolution Increment
	 * @param diffusionFunction Diffusion Function
	 * @param diagnosticsOn TRUE - "Diagnostics On" Mode
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public ForwardEulerDiffusion1D (
		final EvolutionGrid1D evolutionGrid1D,
		final RdToR1 diffusionFunction,
		final boolean diagnosticsOn)
		throws Exception
	{
		super (evolutionGrid1D, diffusionFunction, diagnosticsOn);
	}
}
