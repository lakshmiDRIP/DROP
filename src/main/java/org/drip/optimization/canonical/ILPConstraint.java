
package org.drip.optimization.canonical;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.common.NumberUtil;
import org.drip.optimization.cuttingplane.BurdetJohnsonCut;
import org.drip.optimization.cuttingplane.ChvatalGomoryCut;
import org.drip.optimization.cuttingplane.StrengthenedBurdetJohnsonCut;
import org.drip.optimization.cuttingplane.StrengthenedChvatalGomoryCut;

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
 * <i>ILPConstraint</i> holds the Constraint Matrix LHS and Constraint Array RHS for an Integer Linear
 * 	Program Ax lte B, where A is Z<sup>m x n</sup>, B is Z<sup>m</sup>, and x is Z<sub>+</sub><sup>n</sup>.
 * 	It provides the following Functions:
 * 	<ul>
 * 		<li><i>ILPConstraint</i> Constructor</li>
 * 		<li>Retrieve "A" Grid</li>
 * 		<li>Retrieve "b" Array</li>
 * 		<li>Retrieve the Constraint Count</li>
 * 		<li>Retrieve the Variate Dimension</li>
 * 		<li>Validate the Variate Input</li>
 * 		<li>Verify if the Variate Array satisfies the Constraint</li>
 * 		<li>Generate a Chvatal-Gomory Cut</li>
 * 		<li>Generate a Strengthened Chvatal-Gomory Cut</li>
 * 		<li>Generate a Burdet-Johnson Cut</li>
 * 		<li>Generate a Strengthened Burdet-Johnson Cut</li>
 * 	</ul>
 * 
 * 	The References are:
 * 	<br><br>
 *  <ul>
 *  	<li>
 * 			Burdet, C. A., and E. L. Johnson (1977): A Sub-additive Approach to Solve Linear Integer Programs
 * 				<i>Annals of Discrete Mathematics</i> <b>1</b> 117-143
 *  	</li>
 *  	<li>
 * 			Chvatal, V. (1973): Edmonds Polytopes in a Hierarchy of Combinatorial Problems <i>Discrete
 * 				Mathematics</i> <b>4 (4)</b> 305-337
 *  	</li>
 *  	<li>
 * 			Gomory, R. E. (1958): Outline of an Algorithm for Integer Solutions to Linear Programs
 * 				<i>Bulletin of the American Mathematical Society</i> <b>64 (5)</b> 275-278
 *  	</li>
 *  	<li>
 * 			Kelley, J. E. (1960): The Cutting Plane Method for Solving Convex Problems <i>Journal for the
 * 				Society of the Industrial and Applied Mathematics</i> <b>8 (4)</b> 703-712
 *  	</li>
 *  	<li>
 * 			Letchford, A. N. and A. Lodi (2002): Strengthening Chvatal-Gomory Cuts and Gomory Fractional Cuts
 * 				<i>Operations Research Letters</i> <b>30 (2)</b> 74-82
 *  	</li>
 *  </ul>
 *
 * 	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md">Necessary, Sufficient, and Regularity Checks for Gradient Descent and LP/MILP/MINLP Schemes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/canonical/README.md">Linear Programming Framework Canonical Elements</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ILPConstraint
	implements LinearConstraint
{
	private int[] _bArray = null;
	private int[][] _aGrid = null;

	/**
	 * <i>ILPConstraint</i> Constructor
	 * 
	 * @param aGrid "A" Constraint Grid
	 * @param bArray "b" Constraint Array
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public ILPConstraint (
		final int[][] aGrid,
		final int[] bArray)
		throws Exception
	{
		if (null == (_aGrid = aGrid) || null == (_bArray = bArray)) {
			throw new Exception ("ILPConstraint Constructor => Invalid Inputs");
		}

		int dimension = -1;
		int constraintCount = _bArray.length;

		if (0 == constraintCount || _aGrid.length != constraintCount) {
			throw new Exception ("ILPConstraint Constructor => Invalid Inputs");
		}

		for (int constraintIndex = 0; constraintIndex < constraintCount; ++constraintIndex) {
			if (null == _aGrid[constraintIndex]) {
				throw new Exception ("ILPConstraint Constructor => Invalid Inputs");
			}

			if (-1 == dimension) {
				if (0 == (dimension = _aGrid[constraintIndex].length)) {
					throw new Exception ("ILPConstraint Constructor => Invalid Inputs");
				}
			} else {
				if (dimension != _aGrid[constraintIndex].length) {
					throw new Exception ("ILPConstraint Constructor => Invalid Inputs");
				}
			}
		}
	}

	/**
	 * Retrieve "A" Grid
	 * 
	 * @return A Grid
	 */

	public int[][] aGrid()
	{
		return _aGrid;
	}

	/**
	 * Retrieve "b" Array
	 * 
	 * @return b Array
	 */

	public int[] bArray()
	{
		return _bArray;
	}

	/**
	 * Retrieve the Constraint Count
	 * 
	 * @return Constraint Count
	 */

	@Override public int constraintCount()
	{
		return _bArray.length;
	}

	/**
	 * Retrieve the Variate Dimension
	 * 
	 * @return Variate Dimension
	 */

	@Override public int dimension()
	{
		return _aGrid[0].length;
	}

	/**
	 * Validate the Variate Input
	 * 
	 * @param variateArray The Input Variate Array
	 * 
	 * @return TRUE - The Input Variate successfully Validated
	 */

	public boolean validate (
		final int[] variateArray)
	{
		if (null == variateArray) {
			return false;
		}

		int dimension = _aGrid[0].length;

		if (dimension != variateArray.length) {
			return false;
		}

		for (int dimensionIndex = 0; dimensionIndex < dimension; ++dimensionIndex) {
			if (0 >= variateArray[dimensionIndex]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Verify if the Variate Array satisfies the Constraint
	 * 
	 * @param variateArray The Input Variate Array
	 * 
	 * @return TRUE - The Variate Array satisfies the Constraint
	 * 
	 * @throws Exception Thrown if the Verification cannot be done
	 */

	public boolean verify (
		final int[] variateArray)
		throws Exception
	{
		if (!validate (variateArray)) {
			throw new Exception ("ILPConstraint::verify => Variate Array not Valid");
		}

		int dimension = _aGrid[0].length;
		int constraintCount = _bArray.length;

		for (int constraintIndex = 0; constraintIndex < constraintCount; ++constraintIndex) {
			int constraintLHS = 0;

			for (int dimensionIndex = 0; dimensionIndex < dimension; ++dimensionIndex) {
				constraintLHS += _aGrid[constraintIndex][dimensionIndex] * variateArray[dimensionIndex];
			}

			if (constraintLHS > _bArray[constraintIndex]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Generate a Chvatal-Gomory Cut
	 * 
	 * @param lambdaArray The Lambda Array
	 * 
	 * @return The Chvatal-Gomory Cut
	 */

	public ChvatalGomoryCut chvatalGomoryCut (
		final double[] lambdaArray)
	{
		try {
			return new ChvatalGomoryCut (_aGrid, _bArray, lambdaArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a Strengthened Chvatal-Gomory Cut
	 * 
	 * @param lambdaArray The Lambda Array
	 * @param t Strengthening Integer
	 * 
	 * @return The Chvatal-Gomory Cut
	 */

	public StrengthenedChvatalGomoryCut strengthenedChvatalGomoryCut (
		final double[] lambdaArray,
		final int t)
	{
		if (null == lambdaArray) {
			return null;
		}

		double lambdaB = 0.;
		int constraintCount = _bArray.length;

		if (lambdaArray.length != constraintCount) {
			return null;
		}

		for (int constraintIndex = 0; constraintIndex < constraintCount; ++constraintIndex) {
			lambdaB = lambdaB + lambdaArray[constraintIndex] * _bArray[constraintIndex];
		}

		try {
			double fractionalLambdaB = NumberUtil.Fractional (lambdaB);

			if (0.5 >= fractionalLambdaB) {
				return null;
			}

			double tFractionalLambdaB = NumberUtil.Fractional (t * fractionalLambdaB);

			return 0.5 <= tFractionalLambdaB && 1. > tFractionalLambdaB ? null :
				new StrengthenedChvatalGomoryCut (_aGrid, _bArray, lambdaArray, t);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a Burdet-Johnson Cut
	 * 
	 * @param lambdaArray The Lambda Array
	 * 
	 * @return The Burdet-Johnson Cut
	 */

	public BurdetJohnsonCut burdetJohnsonCut (
		final double[] lambdaArray)
	{
		try {
			return new BurdetJohnsonCut (_aGrid, _bArray, lambdaArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate a Strengthened Burdet-Johnson Cut
	 * 
	 * @param lambdaArray The Lambda Array
	 * @param r1ToR1Increasing R<sup>1</sup> To R<sup>1</sup> Increasing Function
	 * 
	 * @return The Strengthened Burdet-Johnson Cut
	 */

	public StrengthenedBurdetJohnsonCut strengthenedBurdetJohnsonCut (
		final double[] lambdaArray,
		final R1ToR1 r1ToR1Increasing)
	{
		try {
			return new StrengthenedBurdetJohnsonCut (_aGrid, _bArray, lambdaArray, r1ToR1Increasing);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
