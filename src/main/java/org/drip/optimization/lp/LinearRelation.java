
package org.drip.optimization.lp;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>LinearRelation</i> holds the Coefficients, the Relationship, and the RHS of an LP Relation. The
 * 	References are:
 * 
 * <br><br>
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
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/README.md">Necessary, Sufficient, and Regularity Checks for Gradient Descent and LP/MILP/MINLP Schemes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/optimization/lp/README.md">LP Objectives, Constraints, and Optimizers</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LinearRelation
{

	/**
	 * "Lesser Than Or Equal To" Relation
	 */

	public static final int LTE = 1;

	/**
	 * "Equal To" Relation
	 */

	public static final int EQ = 2;

	/**
	 * "Greater Than Or Equal To" Relation
	 */

	public static final int GTE = 4;

	private int _comparison = EQ;
	private double _rhs = java.lang.Double.NaN;
	private java.util.Map<java.lang.String, java.lang.Double> _coefficientMap = null;

	/**
	 * LinearRelation Constructor
	 * 
	 * @param coefficientMap LHS Coefficient Map
	 * @param rhs RHS
	 * @param comparison LTE, EQ, or GTE
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LinearRelation (
		final java.util.Map<java.lang.String, java.lang.Double> coefficientMap,
		final double rhs,
		final int comparison)
		throws java.lang.Exception
	{
		if (null == (_coefficientMap = coefficientMap) || 0 == _coefficientMap.size() ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				_rhs = rhs
			)
		)
		{
			throw new java.lang.Exception (
				"LinearRelation Constructor => Invalid Inputs"
			);
		}

		_comparison = comparison;
	}

	/**
	 * Retrieve the LHS Coefficient Map
	 * 
	 * @return The LHS Coefficient Map
	 */

	public java.util.Map<java.lang.String, java.lang.Double> coefficientMap()
	{
		return _coefficientMap;
	}

	/**
	 * Retrieve the RHS
	 * 
	 * @return The RHS
	 */

	public double rhs()
	{
		return _rhs;
	}

	/**
	 * Retrieve the Comparison
	 * 
	 * @return The Comparison
	 */

	public double comparison()
	{
		return _comparison;
	}

	/**
	 * Retrieve the Variable Set
	 * 
	 * @return The Variable Set
	 */

	public java.util.Set<java.lang.String> variableSet()
	{
		return _coefficientMap.keySet();
	}

	/**
	 * Generate the Canonical Linear Equality From the Set of Unrestricted Variables
	 * 
	 * @param unrestrictedVariableSet Set of Unrestricted Variables
	 * 
	 * @return The Canonical Linear Equality From the Set of Unrestricted Variables
	 */

	public org.drip.optimization.lp.LinearEquality canonicalize (
		final java.util.Set<java.lang.String> unrestrictedVariableSet)
	{
		java.util.Map<java.lang.String, java.lang.Double> variableCoefficientMap =
			new java.util.HashMap<java.lang.String, java.lang.Double>();

		if (null == unrestrictedVariableSet || 0 == unrestrictedVariableSet.size())
		{
			for (java.util.Map.Entry<java.lang.String, java.lang.Double> coefficientEntry :
				_coefficientMap.entrySet())
			{
				variableCoefficientMap.put (
					coefficientEntry.getKey(),
					coefficientEntry.getValue()
				);
			}
		}
		else
		{
			for (java.util.Map.Entry<java.lang.String, java.lang.Double> coefficientEntry :
				_coefficientMap.entrySet())
			{
				java.lang.String variableName = coefficientEntry.getKey();

				double coefficient = coefficientEntry.getValue();

				if (!unrestrictedVariableSet.contains (
					variableName
				))
				{
					variableCoefficientMap.put (
						variableName,
						coefficient
					);
				}
				else
				{
					variableCoefficientMap.put (
						variableName + "Plus",
						coefficient
					);

					variableCoefficientMap.put (
						variableName + "Minus",
						-1. * coefficient
					);
				}
			}
		}

		java.lang.String slackVariableName = "";

		for (java.util.Map.Entry<java.lang.String, java.lang.Double> coefficientEntry :
			_coefficientMap.entrySet())
		{
			slackVariableName = slackVariableName + coefficientEntry.getKey() + "=" +
				coefficientEntry.getValue() + ";";
		}

		try
		{
			return new org.drip.optimization.lp.LinearEquality (
				variableCoefficientMap,
				_rhs,
				new org.drip.optimization.lp.SyntheticVariable (
					slackVariableName,
					GTE == _comparison ? -1. : 1.,
					EQ == _comparison ?
						org.drip.optimization.lp.SyntheticVariableType.ARTIFICIAL :
						org.drip.optimization.lp.SyntheticVariableType.SLACK
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
