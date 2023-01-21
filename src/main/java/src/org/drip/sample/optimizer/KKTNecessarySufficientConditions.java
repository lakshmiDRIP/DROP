
package org.drip.sample.optimizer;

import org.drip.function.definition.RdToR1;
import org.drip.function.rdtor1solver.*;
import org.drip.optimization.constrained.*;
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
 * <i>KKTNecessarySufficientConditions</i> carries out the Zero and the First Order Necessary and the Second
 * 	Order Sufficiency Checks for a Constrained KKT Optimization Problem. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Boyd, S., and L. van den Berghe (2009): <i>Convex Optimization</i> <b>Cambridge University
 * 				Press</b> Cambridge UK
 * 		</li>
 * 		<li>
 * 			Eustaquio, R., E. Karas, and A. Ribeiro (2008): <i>Constraint Qualification for Nonlinear
 * 				Programming</i> <b>Federal University of Parana</b>
 * 		</li>
 * 		<li>
 * 			Karush, A. (1939): <i>Minima of Functions of Several Variables with Inequalities as Side
 * 				Constraints</i> <b>University of Chicago</b>
 * 		</li>
 * 		<li>
 * 			Kuhn, H. W., and A. W. Tucker (1951): Nonlinear Programming <i>Proceedings of the Second Berkeley
 * 				Symposium</i>
 * 		</li>
 * 		<li>
 * 			Ruszczynski, A. (2006): <i>Nonlinear Optimization</i> <b>Princeton University Press</b>
 * 				Princeton NJ
 * 		</li>
 * 	</ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/optimizer/README.md">Lagrangian/KKT Necessary Sufficient Conditions</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class KKTNecessarySufficientConditions
{

	private static final RdToR1 ObjectiveFunction (
		final double x0,
		final double x1,
		final double x2)
		throws Exception
	{
		return new RdToR1 (
			null
		)
		{
			@Override public int dimension()
			{
				return 3;
			}

			@Override public double evaluate (
				final double[] variateArray)
				throws Exception
			{
				return (variateArray[0] - x0) * (variateArray[0] - x0) +
					(variateArray[1] - x1) * (variateArray[1] - x1) +
					(variateArray[2] - x2) * (variateArray[2] - x2);
			}

			@Override public double[] jacobian (
				final double[] variateArray)
			{
				return new double[]
				{
					2. * (x0 - variateArray[0]),
					2. * (x1 - variateArray[1]),
					2. * (x2 - variateArray[2])
				};
			}

			@Override public double[][] hessian (
				final double[] variateArray)
			{
				return new double[][]
				{
					{2., 0., 0.},
					{0., 2., 0.},
					{0., 0., 2.}
				};
			}
		};
	}

	private static final RdToR1 RightConstraintFunction (
		final double deadCenter,
		final int dimension,
		final double halfWidth,
		final boolean signFlip)
		throws Exception
	{
		return new RdToR1 (
			null
		)
		{
			@Override public int dimension()
			{
				return 3;
			}

			@Override public double evaluate (
				final double[] variateArray)
				throws Exception
			{
				return (signFlip ? -1. : 1.) * (deadCenter + halfWidth - variateArray[dimension]);
			}

			@Override public double[] jacobian (
				final double[] variateArray)
			{
				return new double[]
				{
					dimension == 0 ? (signFlip ? -1. : 1.) * -1. : 0.,
					dimension == 1 ? (signFlip ? -1. : 1.) * -1. : 0.,
					dimension == 2 ? (signFlip ? -1. : 1.) * -1. : 0.
				};
			}

			@Override public double[][] hessian (
				final double[] variateArray)
			{
				return new double[][]
				{
					{0., 0., 0.},
					{0., 0., 0.},
					{0., 0., 0.}
				};
			}
		};
	}

	private static final RdToR1 LeftConstraintFunction (
		final double deadCenter,
		final int dimension,
		final double halfWidth,
		final boolean signFlip)
		throws Exception
	{
		return new RdToR1 (
			null
		)
		{
			@Override public int dimension()
			{
				return 3;
			}

			@Override public double evaluate (
				final double[] variateArray)
				throws Exception
			{
				return (signFlip ? -1. : 1.) * (variateArray[dimension] - deadCenter + halfWidth);
			}

			@Override public double[] jacobian (
				final double[] variateArray)
			{
				return new double[]
				{
					dimension == 0 ? (signFlip ? -1. : 1.) * 1. : 0.,
					dimension == 1 ? (signFlip ? -1. : 1.) * 1. : 0.,
					dimension == 2 ? (signFlip ? -1. : 1.) * 1. : 0.
				};
			}

			@Override public double[][] hessian (
				final double[] variateArray)
			{
				return new double[][]
				{
					{0., 0., 0.},
					{0., 0., 0.},
					{0., 0., 0.}
				};
			}
		};
	}

	private static final RdToR1[] ConstraintFunctionArray (
		final double x0,
		final double x1,
		final double x2,
		final double halfWidth,
		final boolean signFlip)
		throws Exception
	{
		return new RdToR1[] {
			LeftConstraintFunction (
				x0,
				0,
				halfWidth,
				signFlip
			),
			RightConstraintFunction (
				x0,
				0,
				halfWidth,
				signFlip
			),
			LeftConstraintFunction (
				x1,
				1,
				halfWidth,
				signFlip
			),
			RightConstraintFunction (
				x1,
				1,
				halfWidth,
				signFlip
			),
			LeftConstraintFunction (
				x2,
				2,
				halfWidth,
				signFlip
			),
			RightConstraintFunction (
				x2,
				2,
				halfWidth,
				signFlip
			)
		};
	}
	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		double x0 = 1.;
		double x1 = 2.;
		double x2 = 3.;
		double halfWidth = 1.;

		RdToR1 objectiveFunction = ObjectiveFunction (
			x0,
			x1,
			x2
		);

		double[] variateArray = new double[]
		{
			x0,
			x1,
			x2
		};

		FritzJohnMultipliers karushKuhnTuckerMultipliers = FritzJohnMultipliers.KarushKuhnTucker (
			null,
			new BarrierFixedPointFinder (
				objectiveFunction,
				ConstraintFunctionArray (
					x0,
					x1,
					x2,
					halfWidth,
					false
				),
				new InteriorPointBarrierControl (
					InteriorPointBarrierControl.VARIATE_CONSTRAINT_SEQUENCE_CONVERGENCE,
					5.0e-06,
					1.0e-07,
					1.0e+10,
					0.5,
					20
				),
				null
			).solve (
				new double[]
				{
					x0 + 0.25 * halfWidth,
					x1 + 0.25 * halfWidth,
					x2 + 0.25 * halfWidth
				}
			).constraintMultiplierArray()
		);

		OptimizationFramework optimizationFramework = new OptimizationFramework (
			objectiveFunction,
			null,
			ConstraintFunctionArray (
				x0,
				x1,
				x2,
				halfWidth,
				true
			)
		);

		System.out.println();

		System.out.println ("\t||---------------------------------------------------||");

		System.out.println ("\t||    KKT NECESSARY & SUFFICIENT CONDITIONS CHECK    ||");

		System.out.println ("\t||---------------------------------------------------||");

		System.out.println ("\t|| KKT Multiplier Compatibility             : " +
			optimizationFramework.isCompatible (
				karushKuhnTuckerMultipliers
			) + "   ||"
		);

		System.out.println ("\t|| Dual Feasibility Check                   : " +
			karushKuhnTuckerMultipliers.dualFeasibilityCheck() + "   ||"
		);

		System.out.println ("\t|| Primal Feasibility Check                 : " +
			optimizationFramework.primalFeasibilityCheck (
				variateArray
			) + "   ||"
		);

		System.out.println ("\t|| Complementary Slackness Check            : " +
			optimizationFramework.complementarySlacknessCheck (
				karushKuhnTuckerMultipliers,
				variateArray
			) + "  ||"
		);

		System.out.println ("\t|| First Order Necessary Condition Check    : " +
			optimizationFramework.isFONC (
				karushKuhnTuckerMultipliers,
				variateArray
			) + "   ||"
		);

		System.out.println ("\t|| Second Order Sufficiency Condition Check : " +
			optimizationFramework.isSOSC (
				karushKuhnTuckerMultipliers,
				variateArray,
				true
			) + "   ||"
		);

		System.out.println ("\t||---------------------------------------------------||");

		System.out.println();

		System.out.println ("\t||------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                 KKT NECESSARY & SUFFICIENT CONSTIONS - ZERO, FIRST, & SECOND ORDERS                  ||");

		System.out.println ("\t||------------------------------------------------------------------------------------------------------||");

		String[] necessarySufficientConditionOrderArray =
			optimizationFramework.necessarySufficientQualifier (
				karushKuhnTuckerMultipliers,
				variateArray,
				true
			).conditionOrder();

		for (int necessarySufficientConditionOrderIndex = 0;
			necessarySufficientConditionOrderIndex < necessarySufficientConditionOrderArray.length;
			++necessarySufficientConditionOrderIndex)
		{
			System.out.println (
				"\t|| " +
				necessarySufficientConditionOrderArray[necessarySufficientConditionOrderIndex]
			);
		}

		System.out.println ("\t||------------------------------------------------------------------------------------------------------||");

		System.out.println();

		EnvManager.TerminateEnv();
	}
}
