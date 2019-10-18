
package org.drip.sample.optimizer;

import org.drip.function.definition.RdToR1;
import org.drip.function.rdtor1solver.*;
import org.drip.optimization.constrained.*;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * KKTNecessarySufficientConditions carries out the Zero and the First Order Necessary and the Second Order
 * 	Sufficiency Checks for a Constrained KKT Optimization Problem. The References are:
 * 
 * 	- Boyd, S., and L. van den Berghe (2009): Convex Optimization, Cambridge University Press, Cambridge UK.
 * 
 * 	- Eustaquio, R., E. Karas, and A. Ribeiro (2008): Constraint Qualification for Nonlinear Programming,
 * 		Technical Report, Federal University of Parana.
 * 
 * 	- Karush, A. (1939): Minima of Functions of Several Variables with Inequalities as Side Constraints,
 * 		M. Sc., University of Chicago, Chicago IL.
 * 
 * 	- Kuhn, H. W., and A. W. Tucker (1951): Nonlinear Programming, Proceedings of the Second Berkeley
 * 		Symposium, University of California, Berkeley CA 481-492.
 * 
 * 	- Ruszczynski, A. (2006): Nonlinear Optimization, Princeton University Press, Princeton NJ.
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
	}
}
