
package org.drip.optimization.cuttingplane;

import org.drip.numerical.common.NumberUtil;

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
 * <i>StrengthenedChvatalGomoryCut</i> implements the Strengthened Chvatal Gomory Cut for ILP. It provides
 * 	the following Functions:
 * 	<ul>
 * 		<li><i>StrengthenedChvatalGomoryCut</i> Constructor</li>
 * 		<li>Retrieve the Strengthening Integer</li>
 * 		<li>Verify if the Variate Array satisfies the Constraint</li>
 * 	</ul>
 * 
 * The References are:
 * <br>
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
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md">Necessary, Sufficient, and Regularity Checks for Gradient Descent and LP/MILP/MINLP Schemes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/cuttingplane/README.md">Polyhedral Cutting Plane Generation Schemes</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class StrengthenedChvatalGomoryCut
	extends ChvatalGomoryCut
{
	private int _t = -1;

	/**
	 * <i>StrengthenedChvatalGomoryCut</i> Constructor
	 * 
	 * @param aGrid "A" Constraint Grid
	 * @param bArray "b" Constraint Array
	 * @param lambdaArray The Lambda Array
	 * @param t Strengthening Integer
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public StrengthenedChvatalGomoryCut (
		final int[][] aGrid,
		final int[] bArray,
		final double[] lambdaArray,
		final int t)
		throws Exception
	{
		super (aGrid, bArray, lambdaArray);

		if (0 >= (_t = t)) {
			throw new Exception ("StrengthenedChvatalGomoryCut Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Strengthening Integer
	 * 
	 * @return The Strengthening Integer
	 */

	public int t()
	{
		return _t;
	}

	/**
	 * Verify if the Variate Array satisfies the Constraint
	 * 
	 * @param variateArray The Input Variate Array
	 * 
	 * @return TRUE - The Variate Array satisfies the Constraint
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public boolean verify (
		final int[] variateArray)
		throws Exception
	{
		if (!super.verify (variateArray)) {
			return false;
		}

		int[] bArray = bArray();

		int[][] aGrid = aGrid();

		double[] lambdaArray = lambdaArray();

		double lhs = 0.;
		double rhs = 0.;
		int dimension = aGrid.length;
		int constraintCount = bArray.length;

		for (int constraintIndex = 0; constraintIndex < constraintCount; ++constraintIndex) {
			double lambdaTFraction = NumberUtil.Fractional (lambdaArray[constraintIndex] * _t);

			for (int dimensionIndex = 0; dimensionIndex < dimension; ++dimensionIndex) {
				lhs += variateArray[dimensionIndex] * lambdaTFraction *
					aGrid[constraintIndex][dimensionIndex];
			}

			rhs  = rhs + lambdaTFraction * bArray[constraintIndex];
		}

		return (int) lhs <= (int) rhs;
	}
}
