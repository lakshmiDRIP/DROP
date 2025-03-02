
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
 * <i>CanonicalFormBuilder</i> builds the Canonical Form of the Simplex Scheme. The References are:
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

public class CanonicalFormBuilder
{
	private LinearExpression _objectiveFunction = null;
	private int _unrestrictedVariableCount = Integer.MIN_VALUE;
	private List<CanonicalConstraint> _eqCanonicalConstraintList = null;
	private List<CanonicalConstraint> _gtCanonicalConstraintList = null;
	private List<CanonicalConstraint> _ltCanonicalConstraintList = null;

	private boolean addEQCanonicalConstraint (
		final CanonicalConstraint canonicalConstraint)
	{
		CanonicalConstraint eqCanonicalConstraint = CanonicalConstraint.EQ (
			canonicalConstraint.lhs(),
			canonicalConstraint.rhs()
		);

		if (null == eqCanonicalConstraint) {
			return false;
		}

		_eqCanonicalConstraintList.add (eqCanonicalConstraint);

		return true;
	}

	private boolean addGTCanonicalConstraint (
		final CanonicalConstraint canonicalConstraint)
	{
		CanonicalConstraint gtCanonicalConstraint = CanonicalConstraint.GT (
			canonicalConstraint.lhs(),
			canonicalConstraint.rhs()
		);

		if (null == gtCanonicalConstraint) {
			return false;
		}

		_gtCanonicalConstraintList.add (gtCanonicalConstraint);

		return true;
	}

	private boolean addLTCanonicalConstraint (
		final CanonicalConstraint canonicalConstraint)
	{
		CanonicalConstraint ltCanonicalConstraint = CanonicalConstraint.LT (
			canonicalConstraint.lhs(),
			canonicalConstraint.rhs()
		);

		if (null == ltCanonicalConstraint) {
			return false;
		}

		_ltCanonicalConstraintList.add (ltCanonicalConstraint);

		return true;
	}

	/**
	 * <i>CanonicalFormBuilder</i> Constructor
	 * 
	 * @param unrestrictedVariableCount Number of Unrestricted Variables
	 * @param objectiveFunction Objective Function
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public CanonicalFormBuilder (
		final int unrestrictedVariableCount,
		final LinearExpression objectiveFunction)
		throws Exception
	{
		if (null == (_objectiveFunction = objectiveFunction) ||
			0 > (_unrestrictedVariableCount = unrestrictedVariableCount)) {
			throw new Exception ("CanonicalFormBuilder Constructor => Invalid Inputs");
		}

		_eqCanonicalConstraintList = new ArrayList<CanonicalConstraint>();

		_gtCanonicalConstraintList = new ArrayList<CanonicalConstraint>();

		_ltCanonicalConstraintList = new ArrayList<CanonicalConstraint>();
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

	public List<CanonicalConstraint> eqCanonicalConstraintList()
	{
		return _eqCanonicalConstraintList;
	}

	/**
	 * Retrieve the List of Greater-Than Constraints
	 * 
	 * @return List of Greater-Than Constraints
	 */

	public List<CanonicalConstraint> gtCanonicalConstraintList()
	{
		return _gtCanonicalConstraintList;
	}

	/**
	 * Retrieve the List of Less-Than Constraints
	 * 
	 * @return List of Less-Than Constraints
	 */

	public List<CanonicalConstraint> ltCanonicalConstraintList()
	{
		return _ltCanonicalConstraintList;
	}

	/**
	 * Convert the Constraint to its Canonical Form and add it to the Constraint List
	 * 
	 * @param canonicalConstraint Canonical Constraint
	 * 
	 * @return TRUE - Constraint converted to its Canonical Form and added to the Constraint List
	 */

	public boolean addCanonicalConstraint (
		final CanonicalConstraint canonicalConstraint)
	{
		if (null == canonicalConstraint) {
			return false;
		}

		if (CanonicalConstraint.EQ == canonicalConstraint.type()) {
			return addEQCanonicalConstraint (canonicalConstraint);
		}

		if (CanonicalConstraint.GT == canonicalConstraint.type()) {
			return addGTCanonicalConstraint (canonicalConstraint);
		}

		if (CanonicalConstraint.LT == canonicalConstraint.type()) {
			return addLTCanonicalConstraint (canonicalConstraint);
		}

		return false;
	}

	/**
	 * Construct an Instance of Simplex <i>CanonicalForm</i> from the EQ/LT/GT Constraints and the Objective
	 * 
	 * @return <i>CanonicalForm</i> Instance
	 */

	public CanonicalForm build()
	{
		int i = 0;

		int eqCanonicalConstraintSize = _eqCanonicalConstraintList.size();

		int gtCanonicalConstraintSize = _gtCanonicalConstraintList.size();

		CanonicalConstraint[] canonicalConstraintArray =
			new CanonicalConstraint[eqCanonicalConstraintSize + gtCanonicalConstraintSize +
			                        _ltCanonicalConstraintList.size()];

		for (CanonicalConstraint canonicalConstraint : _eqCanonicalConstraintList) {
			canonicalConstraintArray[i++] = canonicalConstraint;
		}

		for (CanonicalConstraint canonicalConstraint : _gtCanonicalConstraintList) {
			canonicalConstraintArray[i++] = canonicalConstraint;
		}

		for (CanonicalConstraint canonicalConstraint : _ltCanonicalConstraintList) {
			canonicalConstraintArray[i++] = canonicalConstraint;
		}

		try {
			return new CanonicalForm (
				_objectiveFunction,
				new CanonicalPolytope (_unrestrictedVariableCount, canonicalConstraintArray)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Convert the Canonical Form Builder into a String
	 * 
	 * @return The Canonical Form Builder into a String
	 */

	@Override public String toString()
	{
		String s = "Objective Function => " + _objectiveFunction.toString() + "\n";

		for (CanonicalConstraint canonicalConstraint : _eqCanonicalConstraintList) {
			s += "EQ Constraint => " + canonicalConstraint.toString() + "\n";
		}

		for (CanonicalConstraint canonicalConstraint : _gtCanonicalConstraintList) {
			s += "GT Constraint => " + canonicalConstraint.toString() + "\n";
		}

		for (CanonicalConstraint canonicalConstraint : _ltCanonicalConstraintList) {
			s += "LT Constraint => " + canonicalConstraint.toString() + "\n";
		}

		return s;
	}
}
