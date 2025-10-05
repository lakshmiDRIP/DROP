
package org.drip.optimization.simplex;

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
 * <i>StandardPolytope</i> implements the Standard Constraint Polytope of the Simplex Scheme. It provides the
 * 	following Functions:
 * 	<ul>
 * 		<li><i>StandardPolytope</i> Constructor</li>
 * 		<li>Retrieve the Number of Unrestricted Variables</li>
 * 		<li>Retrieve the Array of <i>StandardConstraint</i>'s</li>
 * 		<li>Retrieve the Constraint Dimension</li>
 * 		<li>Retrieve the Constraint Count</li>
 * 		<li>Compute the Size of a Tableau Row</li>
 * 		<li>Construct the Tableau <i>A</i></li>
 * 		<li>Construct the Tableau <i>B</i></li>
 * 		<li>Retrieve the Slack Variable Count</li>
 * 		<li>Convert the Standard Polytope into a String</li>
 * 	</ul>
 * 
 * The References are:
 * 	<ul>
 *  	<li>
 * 			Dadush, D., and S. Huiberts (2020): A Friendly Smoothed Analysis of the Simplex Method <i>SIAM
 * 				Journal on Computing</i> <b>49 (5)</b> 449-499
 *  	</li>
 * 		<li>
 * 			Dantzig, G. B., and M. N. Thapa (1997): <i>Linear Programming 1: Introduction</i>
 * 				<b>Springer-Verlag</b> New York NY
 * 		</li>
 * 		<li>
 * 			Murty, K. G. (1983): <i>Linear Programming</i> <b>John Wiley and Sons</b> New York NY
 * 		</li>
 * 		<li>
 * 			Nering, E. D., and A. W. Tucker (1993): <i>Linear Programs and Related Problems</i>
 * 				<b>Academic Press</b> Cambridge MA
 * 		</li>
 * 		<li>
 * 			Padberg, M. (1999): <i> Linear Optimization and Extensions 2<sup>nd</sup> Edition</i>
 * 				<b>Springer-Verlag</b> New York NY
 * 		</li>
 * 	</ul>
 *
 * <br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md">Necessary, Sufficient, and Regularity Checks for Gradient Descent and LP/MILP/MINLP Schemes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/simplex/README.md">R<sup>d</sup> to R<sup>1</sup> Simplex Scheme</a></td></tr>
 *  </table>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class StandardPolytope
{
	private int _unrestrictedVariableCount = Integer.MIN_VALUE;
	private StandardConstraint[] _standardConstraintArray = null;

	/**
	 * <i>StandardPolytope</i> Constructor
	 * 
	 * @param unrestrictedVariableCount Number of Unrestricted Variables
	 * @param standardConstraintArray Array of <i>StandardConstraint</i>'s
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public StandardPolytope (
		final int unrestrictedVariableCount,
		final StandardConstraint[] standardConstraintArray)
		throws Exception
	{
		if (null == (_standardConstraintArray = standardConstraintArray) ||
			0 == _standardConstraintArray.length ||
			0 > (_unrestrictedVariableCount = unrestrictedVariableCount))
		{
			throw new Exception ("StandardPolytope Constructor => Invalid Inputs");
		}

		int constraintDimension = -1;

		for (int constraintIndex = 0; constraintIndex < _standardConstraintArray.length; ++constraintIndex) {
			if (null == standardConstraintArray[constraintIndex]) {
				throw new Exception ("StandardPolytope Constructor => Invalid Inputs");
			}

			if (-1 == constraintDimension) {
				constraintDimension = standardConstraintArray[constraintIndex].dimension();
			} else {
				if (constraintDimension != standardConstraintArray[constraintIndex].dimension()) {
					throw new Exception ("StandardPolytope Constructor => Invalid Inputs");
				}
			}
		}
	}

	/**
	 * Retrieve the Number of Unrestricted Variables
	 * 
	 * @return Number of Unrestricted Variables
	 */

	public int unrestrictedVariableCount()
	{
		return _unrestrictedVariableCount;
	}

	/**
	 * Retrieve the Array of <i>StandardConstraint</i>'s
	 * 
	 * @return Array of <i>StandardConstraint</i>'s
	 */

	public StandardConstraint[] standardConstraintArray()
	{
		return _standardConstraintArray;
	}

	/**
	 * Retrieve the Constraint Dimension
	 * 
	 * @return Constraint Dimension
	 */

	public int dimension()
	{
		return _standardConstraintArray[0].dimension();
	}

	/**
	 * Retrieve the Constraint Count
	 * 
	 * @return Constraint Count
	 */

	public int constraintCount()
	{
		return _standardConstraintArray.length;
	}

	/**
	 * Compute the Size of a Tableau Row
	 * 
	 * @return Size of a Tableau Row
	 */

	public int tableauRowSize()
	{
		return _standardConstraintArray[0].dimension() + _standardConstraintArray.length +
			2 * _unrestrictedVariableCount;
	}

	/**
	 * Construct the Tableau <i>A</i>
	 * 
	 * @return Tableau <i>A</i>
	 */

	public double[][] tableauA()
	{
		int tableauRowSize = tableauRowSize();

		int dimension = _standardConstraintArray[0].dimension();

		int constraintCount = _standardConstraintArray.length;
		double[][] tableauA = new double[constraintCount][];
		int unrestrictedVariable = 0;
		int constraintIndex = 0;

		for (StandardConstraint canonicalConstraint : _standardConstraintArray) {
			tableauA[constraintIndex] = canonicalConstraint.tableauRow (tableauRowSize, constraintIndex);

			int constraintType = canonicalConstraint.type();

			if (StandardConstraint.GT == constraintType) {
				tableauA[constraintIndex][dimension + constraintIndex] = -1.;
			} else if (StandardConstraint.LT == constraintType) {
				tableauA[constraintIndex][dimension + constraintIndex] = 1.;
			} else {
				tableauA[constraintIndex][dimension + constraintIndex] = 0.;
			}

			++constraintIndex;
		}

		if (0 == _unrestrictedVariableCount) {
			return tableauA;
		}

		for (constraintIndex = 0; constraintIndex < constraintCount; ++constraintIndex) {
			for (int columnIndex = dimension + 1; columnIndex < tableauRowSize; ++columnIndex) {
				if (columnIndex == dimension + 1 + 2 * unrestrictedVariable) {
					tableauA[constraintIndex][columnIndex] = 1.;
				} else if (columnIndex == dimension + 1 + 2 * unrestrictedVariable + 1) {
					tableauA[constraintIndex][columnIndex] = -1.;
				} else {
					tableauA[constraintIndex][columnIndex] = 0.;
				}
			}

			++unrestrictedVariable;
		}

		return tableauA;
	}

	/**
	 * Construct the Tableau <i>B</i>
	 * 
	 * @return Tableau <i>B</i>
	 */

	public double[] tableauB()
	{
		int constraintIndex = 0;
		double[] tableauB = new double[_standardConstraintArray.length];

		for (StandardConstraint canonicalConstraint : _standardConstraintArray) {
			tableauB[constraintIndex] = canonicalConstraint.rhs();

			++constraintIndex;
		}

		return tableauB;
	}

	/**
	 * Retrieve the Slack Variable Count
	 * 
	 * @return Slack Variable Count
	 */

	public int slackVariableCount()
	{
		int slackVariableCount = 0;

		for (StandardConstraint canonicalConstraint : _standardConstraintArray) {
			int constraintType = canonicalConstraint.type();

			if (StandardConstraint.EQ != constraintType) {
				++slackVariableCount;
			}
		}

		return slackVariableCount;
	}

	/**
	 * Convert the Standard Polytope into a String
	 * 
	 * @return The Standard Polytope into a String
	 */

	@Override public String toString()
	{
		String s = "Unrestricted Variable Count => " + _unrestrictedVariableCount + "\n";

		for (StandardConstraint standardConstraint : _standardConstraintArray) {
			s += "Polytope Constraint => " + standardConstraint.toString() + "\n";
		}

		return s;
	}
}
