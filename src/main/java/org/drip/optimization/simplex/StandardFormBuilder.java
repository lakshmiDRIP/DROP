
package org.drip.optimization.simplex;

import java.util.ArrayList;
import java.util.List;

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
 * <i>StandardFormBuilder</i> builds the Standard Form of the Simplex Scheme. The References are:
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

public class StandardFormBuilder
{
	private LinearExpression _objectiveFunction = null;
	private int _unrestrictedVariableCount = Integer.MIN_VALUE;
	private List<StandardConstraint> _eqStandardConstraintList = null;
	private List<StandardConstraint> _gtStandardConstraintList = null;
	private List<StandardConstraint> _ltStandardConstraintList = null;

	private boolean addEQStandardConstraint (
		final StandardConstraint standardConstraint)
	{
		StandardConstraint eqStandardConstraint = StandardConstraint.EQ (
			standardConstraint.lhs(),
			standardConstraint.rhs()
		);

		if (null == eqStandardConstraint) {
			return false;
		}

		_eqStandardConstraintList.add (eqStandardConstraint);

		return true;
	}

	private boolean addGTStandardConstraint (
		final StandardConstraint standardConstraint)
	{
		StandardConstraint gtStandardConstraint = StandardConstraint.GT (
			standardConstraint.lhs(),
			standardConstraint.rhs()
		);

		if (null == gtStandardConstraint) {
			return false;
		}

		_gtStandardConstraintList.add (gtStandardConstraint);

		return true;
	}

	private boolean addLTStandardConstraint (
		final StandardConstraint standardConstraint)
	{
		StandardConstraint ltStandardConstraint = StandardConstraint.LT (
			standardConstraint.lhs(),
			standardConstraint.rhs()
		);

		if (null == ltStandardConstraint) {
			return false;
		}

		_ltStandardConstraintList.add (ltStandardConstraint);

		return true;
	}

	/**
	 * <i>StandardFormBuilder</i> Constructor
	 * 
	 * @param unrestrictedVariableCount Number of Unrestricted Variables
	 * @param objectiveFunction Objective Function
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public StandardFormBuilder (
		final int unrestrictedVariableCount,
		final LinearExpression objectiveFunction)
		throws Exception
	{
		if (null == (_objectiveFunction = objectiveFunction) ||
			0 > (_unrestrictedVariableCount = unrestrictedVariableCount))
		{
			throw new Exception ("StandardFormBuilder Constructor => Invalid Inputs");
		}

		_eqStandardConstraintList = new ArrayList<StandardConstraint>();

		_gtStandardConstraintList = new ArrayList<StandardConstraint>();

		_ltStandardConstraintList = new ArrayList<StandardConstraint>();
	}

	/**
	 * Retrieve the Objective Function
	 * 
	 * @return Objective Function
	 */

	public LinearExpression objectiveFunction()
	{
		return _objectiveFunction;
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
	 * Retrieve the List of Equality Constraints
	 * 
	 * @return List of Equality Constraints
	 */

	public List<StandardConstraint> eqStandardConstraintList()
	{
		return _eqStandardConstraintList;
	}

	/**
	 * Retrieve the List of Greater-Than Constraints
	 * 
	 * @return List of Greater-Than Constraints
	 */

	public List<StandardConstraint> gtStandardConstraintList()
	{
		return _gtStandardConstraintList;
	}

	/**
	 * Retrieve the List of Less-Than Constraints
	 * 
	 * @return List of Less-Than Constraints
	 */

	public List<StandardConstraint> ltStandardConstraintList()
	{
		return _ltStandardConstraintList;
	}

	/**
	 * Convert the Constraint to its Standard Form and add it to the Constraint List
	 * 
	 * @param standardConstraint Standard Constraint
	 * 
	 * @return TRUE - Constraint converted to its Standard Form and added to the Constraint List
	 */

	public boolean addStandardConstraint (
		final StandardConstraint standardConstraint)
	{
		if (null == standardConstraint) {
			return false;
		}

		if (StandardConstraint.EQ == standardConstraint.type()) {
			return addEQStandardConstraint (standardConstraint);
		}

		if (StandardConstraint.GT == standardConstraint.type()) {
			return addGTStandardConstraint (standardConstraint);
		}

		if (StandardConstraint.LT == standardConstraint.type()) {
			return addLTStandardConstraint (standardConstraint);
		}

		return false;
	}

	/**
	 * Construct an Instance of Simplex <i>CanonicalForm</i> from the EQ/LT/GT Constraints and the Objective
	 * 
	 * @param diagnosticsOn Diagnostics On Flag
	 * 
	 * @return <i>CanonicalForm</i> Instance
	 */

	public StandardForm build (
		final boolean diagnosticsOn)
	{
		int i = 0;

		int eqStandardConstraintSize = _eqStandardConstraintList.size();

		int gtStandardConstraintSize = _gtStandardConstraintList.size();

		StandardConstraint[] standardConstraintArray =
			new StandardConstraint[eqStandardConstraintSize + gtStandardConstraintSize +
			                       _ltStandardConstraintList.size()];

		for (StandardConstraint standardConstraint : _eqStandardConstraintList) {
			standardConstraintArray[i++] = standardConstraint;
		}

		for (StandardConstraint standardConstraint : _gtStandardConstraintList) {
			standardConstraintArray[i++] = standardConstraint;
		}

		for (StandardConstraint standardConstraint : _ltStandardConstraintList) {
			standardConstraintArray[i++] = standardConstraint;
		}

		try {
			return new StandardForm (
				_objectiveFunction,
				new StandardPolytope (_unrestrictedVariableCount, standardConstraintArray),
				diagnosticsOn
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Convert the Standard Form Builder into a String
	 * 
	 * @return The Standard Form Builder into a String
	 */

	@Override public String toString()
	{
		String s = "Objective Function => " + _objectiveFunction.toString() + "\n";

		for (StandardConstraint standardConstraint : _eqStandardConstraintList) {
			s += "EQ Constraint => " + standardConstraint.toString() + "\n";
		}

		for (StandardConstraint standardConstraint : _gtStandardConstraintList) {
			s += "GT Constraint => " + standardConstraint.toString() + "\n";
		}

		for (StandardConstraint standardConstraint : _ltStandardConstraintList) {
			s += "LT Constraint => " + standardConstraint.toString() + "\n";
		}

		return s;
	}
}
