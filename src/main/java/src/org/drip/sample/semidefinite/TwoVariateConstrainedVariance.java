
package org.drip.sample.semidefinite;

import org.drip.function.definition.RdToR1;
import org.drip.function.rdtor1.*;
import org.drip.function.rdtor1descent.LineStepEvolutionControl;
import org.drip.function.rdtor1solver.*;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * <i>TwoVariateConstrainedVariance</i> demonstrates the Application of the Interior Point Method for
 * 	minimizing the Variance Across Two Variates under the Normalization Constraint. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Armijo, L. (1966): Minimization of Functions having Lipschitz-Continuous First Partial
 * 				Derivatives <i>Pacific Journal of Mathematics</i> <b>16 (1)</b> 1-3
 * 		</li>
 * 		<li>
 * 			Nocedal, J., and S. Wright (1999): <i>Numerical Optimization</i> <b>Wiley</b>
 * 		</li>
 * 		<li>
 * 			Wolfe, P. (1969): Convergence Conditions for Ascent Methods <i>SIAM Review</i> <b>11 (2)</b>
 * 				226-235
 * 		</li>
 * 		<li>
 * 			Wolfe, P. (1971): Convergence Conditions for Ascent Methods; II: Some Corrections <i>SIAM
 * 				Review</i> <b>13 (2)</b> 185-188
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/semidefinite/README.md">Semi-Definite Constrained Ellipsoid Variance</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TwoVariateConstrainedVariance
{

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		double[][] covarianceMatrix = new double[][]
		{
			{0.09, 0.12},
			{0.12, 0.04}
		};

		double[] equalityConstraintRHSArray = new double[]
		{
			1.,
			1.
		};

		double equalityConstraintConstant = -1.;
		int objectiveDimension = covarianceMatrix.length;

		RdToR1[] equalityConstraintMultivariateFunctionArray = new AffineMultivariate[]
		{
			new AffineMultivariate (
				equalityConstraintRHSArray,
				equalityConstraintConstant
			)
		};

		int equalityConstraintCount = equalityConstraintMultivariateFunctionArray.length;

		AffineBoundMultivariate affineBoundMultivariateFunction1 = new AffineBoundMultivariate (
			true,
			0,
			2 + equalityConstraintCount,
			0.65
		);

		AffineBoundMultivariate affineBoundMultivariateFunction2 = new AffineBoundMultivariate (
			true,
			1,
			2 + equalityConstraintCount,
			0.65
		);

		AffineBoundMultivariate affineBoundMultivariateFunction3 = new AffineBoundMultivariate (
			false,
			0,
			2 + equalityConstraintCount,
			0.15
		);

		AffineBoundMultivariate affineBoundMultivariateFunction4 = new AffineBoundMultivariate (
			false,
			1,
			2 + equalityConstraintCount,
			0.15
		);

		RdToR1[] inequalityConstraintFunctionArray = new RdToR1[]
		{
			affineBoundMultivariateFunction1,
			affineBoundMultivariateFunction2,
			affineBoundMultivariateFunction3,
			affineBoundMultivariateFunction4
		};

		double barrierStrength = 1.;

		LagrangianMultivariate lagrangianMultivariate = new LagrangianMultivariate (
			new CovarianceEllipsoidMultivariate (
				covarianceMatrix
			),
			equalityConstraintMultivariateFunctionArray
		);

		double[] startingVariateArray = ObjectiveConstraintVariateSet.Uniform (
			objectiveDimension,
			1
		);

		VariateInequalityConstraintMultiplier variateInequalityConstraintMultiplier =
			new BarrierFixedPointFinder (
				lagrangianMultivariate,
				inequalityConstraintFunctionArray,
				InteriorPointBarrierControl.Standard(),
				LineStepEvolutionControl.NocedalWrightStrongWolfe (
					false
				)
			).solve (
				startingVariateArray
			);

		System.out.println ("\n\n\t|----------------------------------------------------||");

		System.out.println (
			"\t| OPTIMAL VARIATES => " + FormatUtil.FormatDouble (variateInequalityConstraintMultiplier.variateArray()[0], 1, 5, 1.) +
			" | " + FormatUtil.FormatDouble (variateInequalityConstraintMultiplier.variateArray()[1], 1, 5, 1.) +
			" | " + FormatUtil.FormatDouble (lagrangianMultivariate.evaluate (variateInequalityConstraintMultiplier.variateArray()), 1, 5, 1.) + " ||"
		);

		System.out.println ("\t|----------------------------------------------------||\n\n");

		int stepDown = 20;

		double[] constraintMultiplierArray = new double[inequalityConstraintFunctionArray.length];

		for (int inequalityConstraintFunctionIndex = 0;
			inequalityConstraintFunctionIndex < inequalityConstraintFunctionArray.length;
			++inequalityConstraintFunctionIndex)
		{
			constraintMultiplierArray[inequalityConstraintFunctionIndex] = barrierStrength /
				inequalityConstraintFunctionArray[inequalityConstraintFunctionIndex].evaluate (
					startingVariateArray
				);
		}

		variateInequalityConstraintMultiplier = new VariateInequalityConstraintMultiplier (
			false,
			startingVariateArray,
			constraintMultiplierArray
		);

		ConvergenceControl convergenceControl = new ConvergenceControl (
			ConvergenceControl.OBJECTIVE_FUNCTION_SEQUENCE_CONVERGENCE,
			5.0e-02,
			1.0e-06,
			70
		);

		System.out.println ("\t|-------------------------------------------------||");

		System.out.println ("\t|    BARRIER    =>      VARIATES       | VARIANCE ||");

		System.out.println ("\t|-------------------------------------------------||");

		while (--stepDown > 0)
		{
			variateInequalityConstraintMultiplier = new InteriorFixedPointFinder (
				lagrangianMultivariate,
				inequalityConstraintFunctionArray,
				LineStepEvolutionControl.NocedalWrightStrongWolfe (
					false
				),
				convergenceControl,
				barrierStrength
			).find (
				variateInequalityConstraintMultiplier
			);

			startingVariateArray = variateInequalityConstraintMultiplier.variateArray();

			System.out.println (
				"\t| " + FormatUtil.FormatDouble (barrierStrength, 1, 10, 1.) +
				" => " + FormatUtil.FormatDouble (
					variateInequalityConstraintMultiplier.variateArray()[0], 1, 5, 1.
				) +
				" | " + FormatUtil.FormatDouble (
					variateInequalityConstraintMultiplier.variateArray()[1], 1, 5, 1.
				) +
				" | " + FormatUtil.FormatDouble (
					lagrangianMultivariate.evaluate (
						variateInequalityConstraintMultiplier.variateArray()
					), 1, 5, 1.
				) + " ||"
			);

			barrierStrength *= 0.5;
		}

		System.out.println ("\t|-------------------------------------------------||\n\n");

		EnvManager.TerminateEnv();
	}
}
