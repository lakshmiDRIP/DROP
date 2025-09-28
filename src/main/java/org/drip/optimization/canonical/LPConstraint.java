
package org.drip.optimization.canonical;

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
 * <i>LPConstraint</i> holds the Constraint Matrix LHS and Constraint Array RHS for an Linear Program Ax lte
 * 	B, where A is R<sup>m x n</sup>, B is R<sup>m</sup>, and x is R<sub>+</sub><sup>n</sup>. It provides the
 * 	following Functions:
 * 	<ul>
 * 		<li><i>LPConstraint</i> Constructor</li>
 * 		<li>Retrieve "A" Grid</li>
 * 		<li>Retrieve "b" Array</li>
 * 		<li>Retrieve the Constraint Count</li>
 * 		<li>Retrieve the Variate Dimension</li>
 * 		<li>Validate the Variate Input</li>
 * 		<li>Verify if the Variate Array satisfies the Constraint</li>
 * 	</ul>
 * 
 * The References are:
 * <br>
 *  <ul>
 *  	<li>
 * 			Nering, E. D., and A. W. Tucker (1993): <i>Linear Programs and Related Problems</i> <b>Academic
 * 				Press</b>
 *  	</li>
 *  	<li>
 * 			Murty, K. G. (1983): <i>Linear Programming</i> <b>John Wiley and Sons</b> New York
 *  	</li>
 *  	<li>
 * 			Padberg, M. W. (1999): <i>Linear Optimization and Extensions 2<sup>nd</sup> Edition</i>
 * 				<b>Springer-Verlag</b>
 *  	</li>
 *  	<li>
 * 			van der Bei, R. J. (2008): Linear Programming: Foundations and Extensions 3<sup>rd</sup> Edition
 * 				<i>International Series in Operations Research and Management Science</i> <b>114
 * 				Springer-Verlag</b>
 *  	</li>
 *  	<li>
 * 			Wikipedia (2020): Simplex Algorithm https://en.wikipedia.org/wiki/Simplex_algorithm
 *  	</li>
 *  </ul>
 *
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md">Necessary, Sufficient, and Regularity Checks for Gradient Descent and LP/MILP/MINLP Schemes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/canonical/README.md">Linear Programming Framework Canonical Elements</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LPConstraint
	implements LinearConstraint
{
	private double[] _bArray = null;
	private double[][] _aGrid = null;

	/**
	 * <i>LPConstraint</i> Constructor
	 * 
	 * @param aGrid "A" Constraint Grid
	 * @param bArray "b" Constraint Array
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public LPConstraint (
		final double[][] aGrid,
		final double[] bArray)
		throws Exception
	{
		if (null == (_aGrid = aGrid) || null == (_bArray = bArray) || !NumberUtil.IsValid (_bArray)) {
			throw new Exception ("LPConstraint Constructor => Invalid Inputs");
		}

		int dimension = -1;
		int constraintCount = _bArray.length;

		if (0 == constraintCount || _aGrid.length != constraintCount) {
			throw new Exception ("LPConstraint Constructor => Invalid Inputs");
		}

		for (int constraintIndex = 0; constraintIndex < constraintCount; ++constraintIndex) {
			if (null == _aGrid[constraintIndex] || !NumberUtil.IsValid (_aGrid[constraintIndex])) {
				throw new java.lang.Exception ("LPConstraint Constructor => Invalid Inputs");
			}

			if (-1 == dimension) {
				if (0 == (dimension = _aGrid[constraintIndex].length)) {
					throw new Exception ("LPConstraint Constructor => Invalid Inputs");
				}
			} else {
				if (dimension != _aGrid[constraintIndex].length) {
					throw new Exception ("LPConstraint Constructor => Invalid Inputs");
				}
			}
		}
	}

	/**
	 * Retrieve "A" Grid
	 * 
	 * @return A Grid
	 */

	public double[][] aGrid()
	{
		return _aGrid;
	}

	/**
	 * Retrieve "b" Array
	 * 
	 * @return b Array
	 */

	public double[] bArray()
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
		final double[] variateArray)
	{
		if (null == variateArray || !NumberUtil.IsValid (variateArray)) {
			return false;
		}

		int dimension = _aGrid[0].length;

		if (dimension != variateArray.length) {
			return false;
		}

		for (int dimensionIndex = 0; dimensionIndex < dimension; ++dimensionIndex) {
			if (0 > variateArray[dimensionIndex]) {
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
		final double[] variateArray)
		throws Exception
	{
		if (!validate (variateArray)) {
			throw new Exception ("LPConstraint::verify => Variate Array not Valid");
		}

		int dimension = _aGrid[0].length;
		int constraintCount = _bArray.length;

		for (int constraintIndex = 0; constraintIndex < constraintCount; ++constraintIndex) {
			double constraintLHS = 0.;

			for (int dimensionIndex = 0; dimensionIndex < dimension; ++dimensionIndex) {
				constraintLHS += _aGrid[constraintIndex][dimensionIndex] * variateArray[dimensionIndex];
			}

			if (constraintLHS > _bArray[constraintIndex]) {
				return false;
			}
		}

		return true;
	}
}
