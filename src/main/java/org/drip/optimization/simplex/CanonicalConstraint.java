
package org.drip.optimization.simplex;

import org.drip.numerical.common.NumberUtil;
import org.drip.service.common.FormatUtil;

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
 * <i>CanonicalConstraint</i> implements the Canonical Constraint of the Simplex Scheme. The References are:
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

public class CanonicalConstraint
{

	/**
	 * TYPE - EQUALITY Constraint
	 */

	public static final int EQ = 1;

	/**
	 * TYPE - GREATER-THAN Constraint
	 */

	public static final int GT = 2;

	/**
	 * TYPE - LESSER-THAN Constraint
	 */

	public static final int LT = 3;

	private double _rhs = Double.NaN;
	private LinearExpression _lhs = null;
	private int _type = Integer.MIN_VALUE;

	/**
	 * Construct a Less-Than Canonical Constraint
	 * 
	 * @param lhs Constraint LHS Expression
	 * @param rhs Constraint RHS Value
	 * 
	 * @return The LT Canonical Constraint
	 */

	public static final CanonicalConstraint LT (
		final LinearExpression lhs,
		final double rhs)
	{
		try {
			return new CanonicalConstraint (lhs, rhs, LT);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct a Greater-Than Canonical Constraint
	 * 
	 * @param lhs Constraint LHS Expression
	 * @param rhs Constraint RHS Value
	 * 
	 * @return The GT Canonical Constraint
	 */

	public static final CanonicalConstraint GT (
		final LinearExpression lhs,
		final double rhs)
	{
		try {
			return new CanonicalConstraint (lhs, rhs, GT);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct an Equality Canonical Constraint
	 * 
	 * @param lhs Constraint LHS Expression
	 * @param rhs Constraint RHS Value
	 * 
	 * @return Equality Canonical Constraint
	 */

	public static final CanonicalConstraint EQ (
		final LinearExpression lhs,
		final double rhs)
	{
		try {
			return new CanonicalConstraint (lhs, rhs, EQ);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>CanonicalConstraint</i> Constructor
	 * 
	 * @param lhs Constraint LHS Expression
	 * @param rhs Constraint RHS Value
	 * @param type One of EQ/GT/LT
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public CanonicalConstraint (
		final LinearExpression lhs,
		final double rhs,
		final int type)
		throws Exception
	{
		if (null == (_lhs = lhs) || !NumberUtil.IsValid (_rhs = rhs)) {
			throw new Exception ("CanonicalConstraint Constructor => Invalid Inputs");
		}

		_type = type;
	}

	/**
	 * Retrieve the Constraint Type
	 * 
	 * @return The Constraint Type
	 */

	public int type()
	{
		return _type;
	}

	/**
	 * Retrieve the Constraint LHS
	 * 
	 * @return The Constraint LHS
	 */

	public LinearExpression lhs()
	{
		return _lhs;
	}

	/**
	 * Retrieve the Constraint RHS Value
	 * 
	 * @return The Constraint RHS Value
	 */

	public double rhs()
	{
		return _rhs;
	}

	/**
	 * Retrieve the Constraint Dimension
	 * 
	 * @return Constraint Dimension
	 */

	public int dimension()
	{
		return _lhs.dimension();
	}

	/**
	 * Construct a Row corresponding to the Constraint in the Tableau
	 * 
	 * @param rowLength Length of the Constraint Row
	 * @param index Constraint Index
	 * 
	 * @return Corresponding Constraint Row in the Tableau
	 */

	public double[] tableauRow (
		final int rowLength,
		final int index)
	{
		int dimension = _lhs.dimension();

		if (0 >= rowLength || 0 >= index || index >= rowLength || dimension > rowLength) {
			return null;
		}

		double[] tableauRow = new double[rowLength];

		double[] coefficientArray = _lhs.coefficientArray();

		for (int columnIndex = 0; columnIndex < rowLength; ++columnIndex) {
			if (columnIndex < dimension) {
				tableauRow[columnIndex] = coefficientArray[columnIndex];
			} else if (columnIndex == index) {
				if (GT == _type) {
					tableauRow[columnIndex] = -1.;
				} else if (LT == _type) {
					tableauRow[columnIndex] = 1.;
				}
			} else {
				tableauRow[columnIndex] = 0.;
			}
		}

		return tableauRow;
	}

	/**
	 * Convert the Canonical Constraint into a String
	 * 
	 * @return The Canonical Constraint into a String
	 */

	@Override public String toString()
	{
		String s = _lhs.toString();

		if (EQ == _type) {
			s += " ==";
		} else if (GT == _type) {
			s += " >=";
		} else if (LT == _type) {
			s += " <=";
		}

		return s + FormatUtil.FormatDouble (_rhs, 3, 6, 1.);
	}
}
