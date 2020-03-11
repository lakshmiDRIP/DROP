
package org.drip.optimization.lp;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
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
 * <i>LinearProgramFormulator</i> contains the Entities needed for the Formulation of a Linear Program. The
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

public class LinearProgramFormulator
{
	private java.util.Set<java.lang.String> _restrictedVariableSet = null;
	private java.util.Map<java.lang.String, java.lang.Double> _objectiveCoefficientMap = null;
	private java.util.Map<java.lang.String, java.lang.Double> _syntheticCoefficientMap = null;
	private java.util.List<org.drip.optimization.lp.LinearRelation> _linearRelationList = null;

	/**
	 * Empty LinearProgramFormulator Constructor
	 */

	public LinearProgramFormulator()
	{
	}

	/**
	 * Retrieve the List of Linear Relations
	 * 
	 * @return The List of Linear Relations
	 */

	public java.util.List<org.drip.optimization.lp.LinearRelation> linearRelationList()
	{
		return _linearRelationList;
	}

	/**
	 * Retrieve the Map of the Objective Coefficients
	 * 
	 * @return Map of the Objective Coefficients
	 */

	public java.util.Map<java.lang.String, java.lang.Double> objectiveCoefficientMap()
	{
		return _objectiveCoefficientMap;
	}

	/**
	 * Retrieve the Restricted Variable Set
	 * 
	 * @return The Restricted Variable Set
	 */

	public java.util.Set<java.lang.String> restrictedVariableSet()
	{
		return _restrictedVariableSet;
	}

	/**
	 * Retrieve the Map of the Synthetic Coefficients
	 * 
	 * @return Map of the Synthetic Coefficients
	 */

	public java.util.Map<java.lang.String, java.lang.Double> syntheticCoefficientMap()
	{
		return _syntheticCoefficientMap;
	}

	/**
	 * Set the Objective Coefficient Map
	 * 
	 * @param objectiveCoefficientMap Objective Coefficient Map
	 * 
	 * @return TRUE - The Objective Coefficient Map successfully set
	 */

	public boolean objectiveCoefficientMap (
		final java.util.Map<java.lang.String, java.lang.Double> objectiveCoefficientMap)
	{
		_objectiveCoefficientMap = objectiveCoefficientMap;
		return true;
	}

	/**
	 * Set the Set of Restricted Variables
	 * 
	 * @param restrictedVariableSet Restricted Variables Set
	 * 
	 * @return TRUE - The Set of Restricted Variables successfully set
	 */

	public boolean restrictedVariableSet (
		final java.util.Set<java.lang.String> restrictedVariableSet)
	{
		_restrictedVariableSet = restrictedVariableSet;
		return true;
	}

	/**
	 * Add a Linear Relation
	 * 
	 * @param linearRelation The Linear Relation
	 * 
	 * @return TRUE - The Linear Relation successfully added
	 */

	public boolean addLinearRelation (
		final org.drip.optimization.lp.LinearRelation linearRelation)
	{
		if (null == linearRelation)
		{
			return false;
		}

		if (null == _linearRelationList)
		{
			_linearRelationList = new java.util.ArrayList<org.drip.optimization.lp.LinearRelation>();
		}

		_linearRelationList.add (
			linearRelation
		);

		return true;
	}

	/**
	 * Generate the Linear Equality List from the Relation List
	 * 
	 * @return The Linear Equality List
	 */

	public java.util.List<org.drip.optimization.lp.LinearEquality> synthesize()
	{
		if (null == _linearRelationList)
		{
			return null;
		}

		java.util.Set<java.lang.String> programVariableSet = new java.util.HashSet<java.lang.String>();

		java.util.Set<java.lang.String> canonicalVariableSet = new java.util.HashSet<java.lang.String>();

		java.util.Set<java.lang.String> unrestrictedVariableSet = new java.util.HashSet<java.lang.String>();

		for (org.drip.optimization.lp.LinearRelation linearRelation : _linearRelationList)
		{
			programVariableSet.addAll (
				linearRelation.variableSet()
			);
		}

		if (null != _restrictedVariableSet && 0 != _restrictedVariableSet.size())
		{
			for (java.lang.String programVariable : programVariableSet)
			{
				if (!_restrictedVariableSet.contains (
					programVariable
				))
				{
					unrestrictedVariableSet.add (
						programVariable
					);
				}
			}
		}

		java.util.List<org.drip.optimization.lp.LinearEquality> linearEqualityList =
			new java.util.ArrayList<org.drip.optimization.lp.LinearEquality>();

		java.util.List<org.drip.optimization.lp.LinearEquality> spanningLinearEqualityList =
			new java.util.ArrayList<org.drip.optimization.lp.LinearEquality>();

		for (org.drip.optimization.lp.LinearRelation linearRelation : _linearRelationList)
		{
			org.drip.optimization.lp.LinearEquality linearEquality = linearRelation.canonicalize (
				unrestrictedVariableSet
			);

			if (null == linearEquality)
			{
				return null;
			}

			if (null == _syntheticCoefficientMap)
			{
				_syntheticCoefficientMap =
					new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();
			}

			org.drip.optimization.lp.SyntheticVariable syntheticVariable =
				linearEquality.syntheticVariable();

			if (null != syntheticVariable)
			{
				_syntheticCoefficientMap.put (
					syntheticVariable.name(),
					syntheticVariable.value()
				);
			}

			for (java.util.Map.Entry<java.lang.String, java.lang.Double> variableCoefficientEntry :
				linearEquality.variableCoefficientMap().entrySet())
			{
				canonicalVariableSet.add (
					variableCoefficientEntry.getKey()
				);
			}

			linearEqualityList.add (
				linearEquality
			);
		}

		for (org.drip.optimization.lp.LinearEquality linearEquality : linearEqualityList)
		{
			java.util.Map<java.lang.String, java.lang.Double> spanningVariableCoefficientMap =
				new org.drip.analytics.support.CaseInsensitiveHashMap<java.lang.Double>();

			java.util.Map<java.lang.String, java.lang.Double> variableCoefficientMap =
				linearEquality.variableCoefficientMap();

			for (java.lang.String variableName : canonicalVariableSet)
			{
				spanningVariableCoefficientMap.put (
					variableName,
					variableCoefficientMap.containsKey (
						variableName
					) ? variableCoefficientMap.get (
						variableName
					) : 0.
				);
			}

			try
			{
				spanningLinearEqualityList.add (
					new org.drip.optimization.lp.LinearEquality (
						spanningVariableCoefficientMap,
						linearEquality.rhs(),
						linearEquality.syntheticVariable()
					)
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return spanningLinearEqualityList;
	}
}
