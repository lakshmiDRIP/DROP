
package org.drip.function.rdtor1solver;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>NewtonFixedPointFinder</i> generates the Iterators for solving R<sup>d</sup> To R<sup>1</sup>
 * Convex/Non-Convex Functions Using the Multivariate Newton Method.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/rdtor1solver/README.md">R<sup>d</sup> To R<sup>1</sup> Solver</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class NewtonFixedPointFinder
	extends org.drip.function.rdtor1solver.FixedRdFinder
{

	/**
	 * NewtonFixedPointFinder Constructor
	 * 
	 * @param objectiveFunction The Objective Function
	 * @param lineStepEvolutionControl The Line Step Evolution Control
	 * @param convergenceControl Convergence Control Parameters
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public NewtonFixedPointFinder (
		final org.drip.function.definition.RdToR1 objectiveFunction,
		final org.drip.function.rdtor1descent.LineStepEvolutionControl lineStepEvolutionControl,
		final org.drip.function.rdtor1solver.ConvergenceControl convergenceControl)
		throws java.lang.Exception
	{
		super (
			objectiveFunction,
			lineStepEvolutionControl,
			convergenceControl
		);
	}

	@Override public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier increment (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier curentVariateConstraint)
	{
		if (null == curentVariateConstraint)
		{
			return null;
		}

		double[] variateArray = curentVariateConstraint.variateArray();

		org.drip.function.definition.RdToR1 objectiveFunction = objectiveFunction();

		double[] variateIncrementArray = org.drip.numerical.linearalgebra.Matrix.Product (
			org.drip.numerical.linearalgebra.Matrix.InvertUsingGaussianElimination (
				objectiveFunction.hessian (
					variateArray
				)
			),
			objectiveFunction.jacobian (
				variateArray
			)
		);

		if (null == variateIncrementArray)
		{
			return null;
		}

		int variateDimension = variateIncrementArray.length;

		for (int variateDimensionIndex = 0;
			variateDimensionIndex < variateDimension;
			++variateDimensionIndex)
		{
			variateIncrementArray[variateDimensionIndex] =
				-1. * variateIncrementArray[variateDimensionIndex];
		}

		try
		{
			return new org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier (
				true,
				variateIncrementArray,
				null
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier next (
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier curentVariateConstraint,
		final org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier
			incrementalVariateConstraint,
		final double incrementFraction)
	{
		return org.drip.function.rdtor1solver.VariateInequalityConstraintMultiplier.Add (
			curentVariateConstraint,
			incrementalVariateConstraint,
			incrementFraction,
			null
		);
	}
}
