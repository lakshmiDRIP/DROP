
package org.drip.sample.optimizer;

import org.drip.function.definition.RdToR1;
import org.drip.function.rdtor1solver.*;
import org.drip.optimization.constrained.*;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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

public class KKTNecessarySufficientConditions {

	private static final RdToR1 Objective (
		final double dblX0,
		final double dblX1,
		final double dblX2)
		throws Exception
	{
		return new RdToR1 (null) {
			@Override public int dimension()
			{
				return 3;
			}

			@Override public double evaluate (
				final double[] adblVariate)
				throws Exception
			{
				return (adblVariate[0] - dblX0) * (adblVariate[0] - dblX0) +
					(adblVariate[1] - dblX1) * (adblVariate[1] - dblX1) +
					(adblVariate[2] - dblX2) * (adblVariate[2] - dblX2);
			}

			@Override public double[] jacobian (
				final double[] adblVariate)
			{
				return new double[] {
					2. * (dblX0 - adblVariate[0]),
					2. * (dblX1 - adblVariate[1]),
					2. * (dblX2 - adblVariate[2])
				};
			}

			@Override public double[][] hessian (
				final double[] adblVariate)
			{
				return new double[][] {
					{2., 0., 0.},
					{0., 2., 0.},
					{0., 0., 2.}
				};
			}
		};
	}

	private static final RdToR1 RightConstraint (
		final double dblDeadCenter,
		final int iDimension,
		final double dblHalfWidth,
		final boolean bSignFlip)
		throws Exception
	{
		return new RdToR1 (null) {
			@Override public int dimension()
			{
				return 3;
			}

			@Override public double evaluate (
				final double[] adblVariate)
				throws Exception
			{
				return (bSignFlip ? -1. : 1.) * (dblDeadCenter + dblHalfWidth - adblVariate[iDimension]);
			}

			@Override public double[] jacobian (
				final double[] adblVariate)
			{
				return new double[] {
					iDimension == 0 ? (bSignFlip ? -1. : 1.) * -1. : 0.,
					iDimension == 1 ? (bSignFlip ? -1. : 1.) * -1. : 0.,
					iDimension == 2 ? (bSignFlip ? -1. : 1.) * -1. : 0.
				};
			}

			@Override public double[][] hessian (
				final double[] adblVariate)
			{
				return new double[][] {
					{0., 0., 0.},
					{0., 0., 0.},
					{0., 0., 0.}
				};
			}
		};
	}

	private static final RdToR1 LeftConstraint (
		final double dblDeadCenter,
		final int iDimension,
		final double dblHalfWidth,
		final boolean bSignFlip)
		throws Exception
	{
		return new RdToR1 (null) {
			@Override public int dimension()
			{
				return 3;
			}

			@Override public double evaluate (
				final double[] adblVariate)
				throws Exception
			{
				return (bSignFlip ? -1. : 1.) * (adblVariate[iDimension] - dblDeadCenter + dblHalfWidth);
			}

			@Override public double[] jacobian (
				final double[] adblVariate)
			{
				return new double[] {
					iDimension == 0 ? (bSignFlip ? -1. : 1.) * 1. : 0.,
					iDimension == 1 ? (bSignFlip ? -1. : 1.) * 1. : 0.,
					iDimension == 2 ? (bSignFlip ? -1. : 1.) * 1. : 0.
				};
			}

			@Override public double[][] hessian (
				final double[] adblVariate)
			{
				return new double[][] {
					{0., 0., 0.},
					{0., 0., 0.},
					{0., 0., 0.}
				};
			}
		};
	}

	private static final RdToR1[] ConstraintSet (
		final double dblX0,
		final double dblX1,
		final double dblX2,
		final double dblHalfWidth,
		final boolean bSignFlip)
		throws Exception
	{
		return new RdToR1[] {
			LeftConstraint (
				dblX0,
				0,
				dblHalfWidth,
				bSignFlip
			),
			RightConstraint (
				dblX0,
				0,
				dblHalfWidth,
				bSignFlip
			),
			LeftConstraint (
				dblX1,
				1,
				dblHalfWidth,
				bSignFlip
			),
			RightConstraint (
				dblX1,
				1,
				dblHalfWidth,
				bSignFlip
			),
			LeftConstraint (
				dblX2,
				2,
				dblHalfWidth,
				bSignFlip
			),
			RightConstraint (
				dblX2,
				2,
				dblHalfWidth,
				bSignFlip
			)
		};
	}
	public static final void main (
		final String[] asrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double dblX0 = 1.;
		double dblX1 = 2.;
		double dblX2 = 3.;
		double dblHalfWidth = 1.;

		RdToR1 objectiveRdToR1 = Objective (
			dblX0,
			dblX1,
			dblX2
		);

		RdToR1[] aConstraintRdToR1 = ConstraintSet (
			dblX0,
			dblX1,
			dblX2,
			dblHalfWidth,
			false
		);

		BarrierFixedPointFinder bfpf = new BarrierFixedPointFinder (
			objectiveRdToR1,
			aConstraintRdToR1,
			new InteriorPointBarrierControl (
				InteriorPointBarrierControl.VARIATE_CONSTRAINT_SEQUENCE_CONVERGENCE,
				5.0e-06,
				1.0e-07,
				1.0e+10,
				0.5,
				20
			),
			null
		);

		double[] adblStartingVariate = new double[3];
		adblStartingVariate[0] = dblX0 + 0.25 * dblHalfWidth;
		adblStartingVariate[1] = dblX1 + 0.25 * dblHalfWidth;
		adblStartingVariate[2] = dblX2 + 0.25 * dblHalfWidth;

		VariateInequalityConstraintMultiplier vicm = bfpf.solve (adblStartingVariate);

		double[] adblVariate = new double[] {
			dblX0,
			dblX1,
			dblX2
		};

		FritzJohnMultipliers fjm = FritzJohnMultipliers.KarushKuhnTucker (
			null,
			vicm.constraintMultipliers()
		);

		OptimizationFramework of = new OptimizationFramework (
			objectiveRdToR1,
			null,
			ConstraintSet (
				dblX0,
				dblX1,
				dblX2,
				dblHalfWidth,
				true
			)
		);

		NecessarySufficientConditions nsc = of.necessarySufficientQualifier (
			fjm,
			adblVariate,
			true
		);

		System.out.println();

		System.out.println ("\t||---------------------------------------------------||");

		System.out.println ("\t||    KKT NECESSARY & SUFFICIENT CONDITIONS CHECK    ||");

		System.out.println ("\t||---------------------------------------------------||");

		System.out.println ("\t|| KKT Multiplier Compatibility             : " +
			of.isCompatible (fjm) + "   ||"
		);

		System.out.println ("\t|| Dual Feasibility Check                   : " +
			fjm.dualFeasibilityCheck() + "   ||"
		);

		System.out.println ("\t|| Primal Feasibility Check                 : " +
			of.primalFeasibilityCheck (adblVariate) + "   ||"
		);

		System.out.println ("\t|| Complementary Slackness Check            : " +
			of.complementarySlacknessCheck (
				fjm,
				adblVariate
			) + "  ||"
		);

		System.out.println ("\t|| First Order Necessary Condition Check    : " +
			of.isFONC (
				fjm,
				adblVariate
			) + "   ||"
		);

		System.out.println ("\t|| Second Order Sufficiency Condition Check : " +
			of.isSOSC (
				fjm,
				adblVariate,
				true
			) + "   ||"
		);

		System.out.println ("\t||---------------------------------------------------||");

		System.out.println();

		System.out.println ("\t||------------------------------------------------------------------------------------------------------||");

		System.out.println ("\t||                 KKT NECESSARY & SUFFICIENT CONSTIONS - ZERO, FIRST, & SECOND ORDERS                  ||");

		System.out.println ("\t||------------------------------------------------------------------------------------------------------||");

		String[] astrNSC = nsc.conditionOrder();

		for (int i = 0; i < astrNSC.length; ++i)
			System.out.println ("\t|| " + astrNSC[i]);

		System.out.println ("\t||------------------------------------------------------------------------------------------------------||");

		System.out.println();
	}
}
