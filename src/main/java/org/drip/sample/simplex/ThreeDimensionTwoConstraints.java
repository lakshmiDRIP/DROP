
package org.drip.sample.simplex;

import org.drip.measure.crng.RdRandomSequence;
import org.drip.numerical.common.NumberUtil;
import org.drip.optimization.simplex.LinearExpression;
import org.drip.optimization.simplex.ObjectiveFunction;
import org.drip.optimization.simplex.PivotRun;
import org.drip.optimization.simplex.StandardConstraint;
import org.drip.optimization.simplex.StandardForm;
import org.drip.optimization.simplex.StandardFormBuilder;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

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
 * <i>ThreeDimensionTwoConstraints</i> constructs and optimizes a variety of 3D linear system with 2
 * 	constraints. The References are:
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

public class ThreeDimensionTwoConstraints
{

	private static final void OptimizerRun (
		final String description)
		throws Exception
	{
		double[] objectiveCoefficientArray = RdRandomSequence.OneD (3, 10., true);

		double[] constraintCoefficient1 = RdRandomSequence.OneD (3, 10., true);

		double[] constraintCoefficient2 = RdRandomSequence.OneD (3, 10., true);

		double constraintRHS1 = RdRandomSequence.Single (100., true);

		double constraintRHS2 = RdRandomSequence.Single (100., true);

		for (int i = 0; i < objectiveCoefficientArray.length; ++i) {
			objectiveCoefficientArray[i] = -1. * (objectiveCoefficientArray[i] + 1.);
		}

		System.out.println ("\t|-------------------------------------------------------|");

		System.out.println ("\t|    " + description);

		System.out.println ("\t|-------------------------------------------------------|");

		System.out.println (
			"\t| OBJECTIVE ARRAY: " + NumberUtil.ArrayRow (objectiveCoefficientArray, 2, 0, false)
		);

		System.out.println ("\t|-------------------------------------------------------|");

		System.out.println (
			"\t| CONSTRAINT #1: " + NumberUtil.ArrayRow (constraintCoefficient1, 2, 0, false) +
				" =>" + FormatUtil.FormatDouble (constraintRHS1, 2, 0, 1.)
		);

		System.out.println (
			"\t| CONSTRAINT #2: " + NumberUtil.ArrayRow (constraintCoefficient2, 2, 0, false) +
				" =>" + FormatUtil.FormatDouble (constraintRHS2, 2, 0, 1.)
		);

		System.out.println ("\t|-------------------------------------------------------|");

		StandardFormBuilder standardFormBuilder = new StandardFormBuilder (
			0,
			ObjectiveFunction.StandardMinimization (new LinearExpression (objectiveCoefficientArray))
		);

		standardFormBuilder.addStandardConstraint (
			StandardConstraint.LT (new LinearExpression (constraintCoefficient1), constraintRHS1)
		);

		standardFormBuilder.addStandardConstraint (
			StandardConstraint.LT (new LinearExpression (constraintCoefficient2), constraintRHS2)
		);

		StandardForm standardForm = standardFormBuilder.build(false);

		NumberUtil.PrintMatrix ("\t| Full Tableau", standardForm.tableau(), 2, 0);

		System.out.println ("\t|-------------------------------------------------------|");

		PivotRun pivotRun = standardForm.multiPhaseOptimize();

		System.out.println ("\t| Finite Optimum Reached => " + pivotRun.finiteOptimumReached());

		System.out.println (
			"\t| Optimal Value          => " + FormatUtil.FormatDouble (pivotRun.optimumValue(), 2, 3, 1.)
		);

		System.out.println ("\t|-------------------------------------------------------|");

		System.out.println();
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		OptimizerRun ("RUN #1");

		OptimizerRun ("RUN #2");

		OptimizerRun ("RUN #3");

		OptimizerRun ("RUN #4");

		OptimizerRun ("RUN #5");
	}
}
