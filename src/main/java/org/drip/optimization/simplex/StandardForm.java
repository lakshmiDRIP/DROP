
package org.drip.optimization.simplex;

import java.util.Collection;

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
 * <i>StandardForm</i> exposes the Standard Form of the Simplex Scheme. The References are:
 * 
 * <br><br>
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md">Necessary, Sufficient, and Regularity Checks for Gradient Descent in a Constrained Optimization Setup</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/simplex">R<sup>d</sup> to R<sup>1</sup> Simplex Scheme</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class StandardForm
{
	private LinearExpression _objectiveFunction = null;
	private StandardPolytope _constraintPolytope = null;

	private boolean pivotRowIndexForTableauColumn (
		final int pivotColumnIndex,
		final PivotRun pivotRun,
		final double[][] tableau)
	{
		int dimension = _constraintPolytope.dimension();

		if (0 >= pivotColumnIndex || pivotColumnIndex > dimension || null == pivotRun) {
			return false;
		}

		double[] tableauB = tableauB();

		int tableauRowIndex = 1;
		int pivotRowIndex = Integer.MIN_VALUE;
		double minimumImpliedVariate = Double.MAX_VALUE;

		Collection<Integer> coveredRowSet = pivotRun.tableauColumnToRowMap().values();

		while (tableauRowIndex < tableau.length) {
			if (0. == tableau[tableauRowIndex][pivotColumnIndex] ||
				coveredRowSet.contains (tableauRowIndex))
			{
				++tableauRowIndex;
				continue;
			}

			double impliedVariate =
				tableauB[tableauRowIndex - 1] / tableau[tableauRowIndex][pivotColumnIndex];

			if (impliedVariate < minimumImpliedVariate) {
				minimumImpliedVariate = impliedVariate;
				pivotRowIndex = tableauRowIndex;
			}

			++tableauRowIndex;
		}

		if (!pivotRun.addTableauRowForColumn (pivotRowIndex, pivotColumnIndex)) {
			return false;
		}

		return pivotRun instanceof PivotRunDiagnostics ? ((PivotRunDiagnostics) pivotRun).addColumnPivoting (
			pivotColumnIndex,
			pivotRowIndex,
			1. / tableau[pivotRowIndex][pivotColumnIndex],
			minimumImpliedVariate
		) : true;
	}

	private boolean processTableauColumn (
		final int pivotColumnIndex,
		final PivotRun pivotRun,
		final double[][] tableau)
	{
		if (!pivotRowIndexForTableauColumn (pivotColumnIndex, pivotRun, tableau)) {
			return false;
		}

		int pivotRowIndex = pivotRun.tableauColumnToRowMap().get (pivotColumnIndex);

		boolean optimumReached = false;
		double unitRowScaler = 1. / tableau[pivotRowIndex][pivotColumnIndex];

		for (int columnIndex = 1; columnIndex < tableau[pivotRowIndex].length; ++columnIndex) {
			tableau[pivotRowIndex][columnIndex] *= unitRowScaler;
		}

		for (int tableauRowIndex = 0; tableauRowIndex < tableau.length; ++tableauRowIndex) {
			if (optimumReached) {
				break;
			}

			if (tableauRowIndex == pivotRowIndex) {
				continue;
			}

			double pivotElement = tableau[tableauRowIndex][pivotColumnIndex];

			for (int tableauColumnIndex = 0;
				tableauColumnIndex < tableau[tableauRowIndex].length;
				++tableauColumnIndex)
			{
				tableau[tableauRowIndex][tableauColumnIndex] -=
					pivotElement * tableau[pivotRowIndex][tableauColumnIndex];
			}

			if (0 == tableauRowIndex && pivotRun.finiteOptimumReached (tableau)) {
				optimumReached = true;
				break;
			}
		}

		return pivotRun instanceof PivotRunDiagnostics ?
			((PivotRunDiagnostics) pivotRun).updateColumnPivotingTableau (pivotColumnIndex, tableau) : true;
	}

	/**
	 * <i>StandardForm</i> Constructor
	 * 
	 * @param objectiveFunction Objective Function
	 * @param constraintPolytope Constraint Polytope
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public StandardForm (
		final LinearExpression objectiveFunction,
		final StandardPolytope constraintPolytope)
		throws Exception
	{
		if (null == (_objectiveFunction = objectiveFunction) ||
			null == (_constraintPolytope = constraintPolytope))
		{
			throw new Exception ("StandardForm Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Simplex Objective Function
	 * 
	 * @return The Simplex Objective Function
	 */

	public LinearExpression objectiveFunction()
	{
		return _objectiveFunction;
	}

	/**
	 * Retrieve the Simplex Standard Constraint Polytope
	 * 
	 * @return The Simplex Standard Constraint Polytope
	 */

	public StandardPolytope constraintPolytope()
	{
		return _constraintPolytope;
	}

	/**
	 * Construct the Tableau <i>A</i>
	 * 
	 * @return Tableau <i>A</i>
	 */

	public double[][] tableauA()
	{
		return _constraintPolytope.tableauA();
	}

	/**
	 * Construct the Tableau <i>B</i>
	 * 
	 * @return Tableau <i>B</i>
	 */

	public double[] tableauB()
	{
		return _constraintPolytope.tableauB();
	}

	/**
	 * Retrieve the Basic Variable Count
	 * 
	 * @return Basic Variable Count
	 */

	public int basicVariableCount()
	{
		return _constraintPolytope.dimension();
	}

	/**
	 * Retrieve the Free Variable Count
	 * 
	 * @return Free Variable Count
	 */

	public int freeVariableCount()
	{
		return _constraintPolytope.tableauRowSize() - _constraintPolytope.dimension();
	}

	/**
	 * Retrieve the Slack Variable Count
	 * 
	 * @return Slack Variable Count
	 */

	public int slackVariableCount()
	{
		return _constraintPolytope.slackVariableCount();
	}

	/**
	 * Construct the Tableau <i>C</i>
	 * 
	 * @return Tableau <i>C</i>
	 */

	public double[] tableauC()
	{
		double[] objectiveCoefficientArray = _objectiveFunction.coefficientArray();

		double[] tableauC = new double[objectiveCoefficientArray.length];

		for (int coefficientIndex = 0;
			coefficientIndex < objectiveCoefficientArray.length;
			++coefficientIndex)
		{
			tableauC[coefficientIndex] = -1. * objectiveCoefficientArray[coefficientIndex];
		}

		return tableauC;
	}

	/**
	 * Construct the Full Tableau
	 * 
	 * @return Full Tableau
	 */

	public double[][] tableau()
	{
		int constraintCount = _constraintPolytope.constraintCount();

		double[][] tableau = new double[constraintCount + 1][_constraintPolytope.tableauRowSize() + 2];

		tableau[0][0] = 1.;

		for (int constraintIndex = 0; constraintIndex < constraintCount; ++constraintIndex) {
			tableau[constraintIndex+1][0] = 0.;
		}

		double[] tableauC = tableauC();

		double[] tableauB = tableauB();

		double[][] tableauA = tableauA();

		for (int tableauCIndex = 0; tableauCIndex < tableauC.length; ++tableauCIndex) {
			tableau[0][tableauCIndex + 1] = tableauC[tableauCIndex];
		}

		for (int tableauRowIndex = 0; tableauRowIndex < tableauA.length; ++tableauRowIndex) {
			for (int tableauColumnIndex = 0; tableauColumnIndex < tableauA[0].length; ++tableauColumnIndex) {
				tableau[tableauRowIndex + 1][tableauColumnIndex + 1] =
					tableauA[tableauRowIndex][tableauColumnIndex];
			}

			tableau[tableauRowIndex + 1][tableauA[0].length + 1] = tableauB[tableauRowIndex];
		}

		return tableau;
	}

	/**
	 * Process the Simplex Tableau to locate the Optimal Solution
	 * 
	 * @param diagnosticsOn TRUE - Pivot Run Diagnostics Turned On
	 * 
	 * @return The Resulting Pivot Run
	 */

	public PivotRun processTableau (
		final boolean diagnosticsOn)
	{
		PivotRun pivotRun = diagnosticsOn ? new PivotRunDiagnostics() : new PivotRun();

		int pivotColumnIndex = basicVariableCount();

		double[][] tableau = tableau();

		while (1 <= pivotColumnIndex) {
			if (!processTableauColumn (pivotColumnIndex, pivotRun, tableau) ||
				pivotRun.finiteOptimumReached())
			{
				break;
			}

			--pivotColumnIndex;
		}

		return pivotRun;
	}

	/**
	 * Compute the Basic Feasible Solution
	 * 
	 * @return Basic Feasible Solution
	 */

	public double[] basicFeasibleSolution()
	{
		int dimension = _constraintPolytope.dimension();

		int feasibleVariableCount = _constraintPolytope.tableauRowSize();

		double[] basicFeasibleSolutionArray = new double[feasibleVariableCount];

		StandardConstraint[] standardConstraintArray = _constraintPolytope.standardConstraintArray();

		for (int feasibleVariableIndex = 0;
			feasibleVariableIndex < feasibleVariableCount;
			++feasibleVariableIndex)
		{
			basicFeasibleSolutionArray[feasibleVariableIndex] = 0.;
		}

		for (int constraintIndex = 0;
			constraintIndex < standardConstraintArray.length;
			++constraintIndex)
		{
			basicFeasibleSolutionArray[dimension + constraintIndex] =
				standardConstraintArray[constraintIndex].rhs();
		}

		return basicFeasibleSolutionArray;
	}

	/**
	 * Convert the Standard Form into a String
	 * 
	 * @return The Standard Form into a String
	 */

	@Override public String toString()
	{
		return "Objective Function => " + _objectiveFunction + "\n" +
			"Constraint Polytope => " + _constraintPolytope + "\n";
	}
}
