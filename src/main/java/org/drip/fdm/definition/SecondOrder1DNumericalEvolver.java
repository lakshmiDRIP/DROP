
package org.drip.fdm.definition;

import org.drip.function.definition.RdToR1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>SecondOrder1DNumericalEvolver</i> implements key Second Order Finite Difference Schemes for
 *  R<sup>1</sup> State Space Evolution. The References are:
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
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/pde/README.md">Numerical Solution Schemes for PDEs</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/fdm/definition/README.md">Finite Difference PDE Evolver Schemes</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SecondOrder1DNumericalEvolver
{
	private RdToR1 _diffusionFunction = null;
	private SecondOrder1DPDE _secondOrder1DPDE = null;
	private R1EvolutionIncrement _evolutionIncrement = null;

	/**
	 * <i>SecondOrder1DNumericalEvolver</i> Constructor
	 * 
	 * @param evolutionIncrement R<sup>1</sup> Evolution Increment
	 * @param diffusionFunction Diffusion Function
	 * @param secondOrder1DPDE Second Order R<sup>1</sup> State Space Evolution PDE
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public SecondOrder1DNumericalEvolver (
		final R1EvolutionIncrement evolutionIncrement,
		final RdToR1 diffusionFunction,
		final SecondOrder1DPDE secondOrder1DPDE)
		throws Exception
	{
		if (null == (_evolutionIncrement = evolutionIncrement) ||
			null == (_diffusionFunction = diffusionFunction) ||
			null == (_secondOrder1DPDE = secondOrder1DPDE))
		{
			throw new Exception ("SecondOrder1DNumericalEvolver Constructor => Invalid Inputs");
		}
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
	 * Retrieve the Second Order R<sup>1</sup> State Space Evolution PDE
	 * 
	 * @return Second Order R<sup>1</sup> State Space Evolution PDE
	 */

	public SecondOrder1DPDE secondOrder1DPDE()
	{
		return _secondOrder1DPDE;
	}

	/**
	 * Compute the von Neumann CFL Stability Number
	 * 
	 * @param time Time
	 * @param factor Factor
	 * 
	 * @return von Neumann CFL Stability Number
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double cflNumber (
		final double time,
		final double factor)
		throws Exception
	{
		double r1SpaceStep = _evolutionIncrement.r1SpaceStep();

		double stepSizeHypotenuseSquare = r1SpaceStep * r1SpaceStep;

		return 0. == stepSizeHypotenuseSquare ? 0. :
			_evolutionIncrement.timeStamp() * diffusionFunction().evaluate (
				new double[] {time, factor}
			) / stepSizeHypotenuseSquare;
	}

	/**
	 * Indicate if the Step Sizes enable Stable Usage of the Crank-Nicolson Scheme
	 * 
	 * @param time Time
	 * @param factor Factor
	 * 
	 * @return TRUE - Step Sizes enable Stable Usage of the Crank-Nicolson Scheme
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public boolean crankNicolsonStabilityCheck (
		final double time,
		final double factor)
		throws Exception
	{
		double cflNumber = cflNumber (time, factor);

		return 0. != cflNumber && 0.5 >= cflNumber;
	}

	/**
	 * Compute the State Increment using the Euler Forward Difference State Evolver Scheme
	 * 
	 * @param time Time
	 * @param space Space
	 * 
	 * @return State Increment using the Euler Forward Difference State Evolver Scheme
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double eulerForwardDifferenceScheme (
		final double time,
		final double space)
		throws Exception
	{
		return _secondOrder1DPDE.timeDifferential (time, space);
	}

	/**
	 * Compute the State Increment using the Euler Backward Difference State Evolver Scheme
	 * 
	 * @param time Time
	 * @param space Space
	 * 
	 * @return State Increment using the Euler Backward Difference State Evolver Scheme
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double eulerBackwardDifferenceScheme (
		final double time,
		final double space)
		throws Exception
	{
		return _secondOrder1DPDE.timeDifferential (time, space + _evolutionIncrement.r1SpaceStep());
	}

	/**
	 * Compute the State Increment using the Crank-Nicolson State Evolver Scheme
	 * 
	 * @param time Time
	 * @param space Space
	 * 
	 * @return State Increment using the Crank-Nicolson State Evolver Scheme
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public double crankNicolsonDifferenceScheme (
		final double time,
		final double space)
		throws Exception
	{
		return 0.5 * (
			eulerForwardDifferenceScheme (time, space) + eulerBackwardDifferenceScheme (time, space)
		);
	}
}
