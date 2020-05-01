
package org.drip.sample.simplex;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.drip.analytics.support.CaseInsensitiveHashMap;
import org.drip.optimization.lp.LinearEquality;
import org.drip.optimization.lp.LinearProgramFormulator;
import org.drip.optimization.lp.LinearRelation;
import org.drip.service.env.EnvManager;

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
 * <i>LPConstraintFormulation</i> illustrates the Formulation and Canonicalization of the LP Simplex
 * 	Constraint. The References are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Murty, K. G. (1983): <i>Linear Programming</i> <b>John Wiley and Sons</b> New York
 *  	</li>
 *  	<li>
 * 			Nering, E. D., and A. W. Tucker (1993): <i>Linear Programs and Related Problems</i> <b>Academic
 * 				Press</b>
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/simplex/README.md">LP Simplex Formulation and Solution</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LPConstraintFormulation
{

	private static final LinearRelation MakeLinearRelation (
		final String[] variableArray,
		final double[] coefficientArray,
		final double rhs,
		final int relationComparison)
		throws Exception
	{
		Map<java.lang.String, java.lang.Double> coefficientMap1 = new CaseInsensitiveHashMap<Double>();

		for (int index = 0;
			index < variableArray.length;
			++index)
		{
			coefficientMap1.put (
				variableArray[index],
				coefficientArray[index]
			);
		}

		return new LinearRelation (
			coefficientMap1,
			rhs,
			relationComparison
		);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		String[] variableArray =
		{
			"x",
			"y",
			"z"
		};

		LinearRelation linearRelation1 = MakeLinearRelation (
			variableArray,
			new double[]
			{
				3.,
				2.,
				1.
			},
			10.,
			LinearRelation.LTE
		);

		LinearRelation linearRelation2 = MakeLinearRelation (
			variableArray,
			new double[]
			{
				2.,
				5.,
				3.
			},
			15.,
			LinearRelation.LTE
		);

		Set<String> restrictedVariableSet = new HashSet<String>();

		for (String variable : variableArray)
		{
			restrictedVariableSet.add (
				variable
			);
		}

		LinearProgramFormulator linearProgramFormulator = new LinearProgramFormulator();

		linearProgramFormulator.addLinearRelation (
			linearRelation1
		);

		linearProgramFormulator.addLinearRelation (
			linearRelation2
		);

		linearProgramFormulator.restrictedVariableSet (
			restrictedVariableSet
		);

		List<LinearEquality> spanningLinearEqualityList = linearProgramFormulator.synthesize();

		for (LinearEquality spanningLinearEquality : spanningLinearEqualityList)
		{
			String equation = "";

			for (Map.Entry<String, Double> variableCoefficientEntry :
				spanningLinearEquality.variableCoefficientMap().entrySet())
			{
				equation = equation + " " + variableCoefficientEntry.getKey() + "::" +
					variableCoefficientEntry.getValue();
			}

			equation = equation + " " + spanningLinearEquality.syntheticVariable().name() + "::" +
				spanningLinearEquality.syntheticVariable().value();

			System.out.println (equation + " = " + spanningLinearEquality.rhs());
		}

		EnvManager.TerminateEnv();
	}
}
