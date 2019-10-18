
package org.drip.sample.optimizer;

import org.drip.function.definition.RdToR1;
import org.drip.function.rdtor1.LagrangianMultivariate;
import org.drip.function.rdtor1descent.LineStepEvolutionControl;
import org.drip.function.rdtor1solver.*;
import org.drip.numerical.common.FormatUtil;
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
 * NSphereSurfaceExtremization computes the Equality-Constrained Extrema of the Specified Function along the
 *  Surface of an N-Sphere using Lagrange Multipliers.
 *
 * @author Lakshmi Krishnamurthy
 */

public class NSphereSurfaceExtremization
{

	private static final void Solve (
		final NewtonFixedPointFinder newtonFixedPointFinder,
		final double[] startingVariateArray)
		throws Exception
	{
		System.out.println ("\n\t|------------------------------------||");

		String strDump = "\t| STARTER: [";

		strDump += FormatUtil.FormatDouble (startingVariateArray[0], 1, 4, 1.) + ",";

		strDump += FormatUtil.FormatDouble (startingVariateArray[1], 1, 4, 1.) + ",";

		strDump += FormatUtil.FormatDouble (startingVariateArray[2], 1, 4, 1.);

		System.out.println (strDump + "] ||");

		System.out.println ("\t|------------------------------------||");

		double[] variateArray = newtonFixedPointFinder.convergeVariate (
			new VariateInequalityConstraintMultiplier (
				false,
				startingVariateArray,
				null
			)
		).variateArray();

		System.out.println (
			"\t| Optimal X      : " + FormatUtil.FormatDouble (variateArray[0], 1, 4, 1.) + "           ||"
		);

		System.out.println (
			"\t| Optimal Y      : " + FormatUtil.FormatDouble (variateArray[1], 1, 4, 1.) + "           ||"
		);

		System.out.println (
			"\t| Optimal Lambda : " + FormatUtil.FormatDouble (variateArray[2], 1, 4, 1.) + "           ||"
		);

		System.out.println ("\t|------------------------------------||");
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		RdToR1 variateSumObjectiveFunction = new RdToR1 (
			null
		)
		{
			@Override public double evaluate (
				final double[] variateArray)
				throws Exception
			{
				return variateArray[0] * variateArray[0] * variateArray[1];
			}

			@Override public int dimension()
			{
				return 2;
			}

			@Override public double[] jacobian (
				final double[] variateArray)
			{
				double[] jacobian = new double[2];
				jacobian[0] = 2. * variateArray[0] * variateArray[1];
				jacobian[1] = variateArray[0] * variateArray[0];
				return jacobian;
			}

			@Override public double[][] hessian (
				final double[] variateArray)
			{
				double[][] hessian = new double[2][2];
				hessian[0][0] = 2. * variateArray[1];
				hessian[0][1] = 2. * variateArray[0];
				hessian[1][0] = 2. * variateArray[0];
				hessian[1][1] = 0.;
				return hessian;
			}
		};

		RdToR1 sphereSurfaceConstraintFunction = new RdToR1 (
			null
		)
		{
			@Override public double evaluate (
				final double[] variateArray)
				throws Exception
			{
				return variateArray[0] * variateArray[0] + variateArray[1] * variateArray[1] - 3.;
			}

			@Override public int dimension()
			{
				return 2;
			}

			@Override public double[] jacobian (
				final double[] variateArray)
			{
				double[] jacobian = new double[2];
				jacobian[0] = 2. * variateArray[0];
				jacobian[1] = 2. * variateArray[1];
				return jacobian;
			}

			@Override public double[][] hessian (
				final double[] variateArray)
			{
				double[][] hessian = new double[2][2];
				hessian[0][0] = 2.;
				hessian[0][1] = 0.;
				hessian[1][0] = 0.;
				hessian[1][1] = 2.;
				return hessian;
			}
		};

		NewtonFixedPointFinder newtonFixedPointFinder = new NewtonFixedPointFinder (
			new LagrangianMultivariate (
				variateSumObjectiveFunction,
				new RdToR1[]
				{
					sphereSurfaceConstraintFunction
				}
			),
			LineStepEvolutionControl.NocedalWrightStrongWolfe (
				false
			),
			ConvergenceControl.Standard()
		);

		Solve (
			newtonFixedPointFinder,
			new double[]
			{
				2.,
				1.,
				1.
			}
		);

		Solve (
			newtonFixedPointFinder,
			new double[]
			{
				-2.,
				 1.,
				 1.
			}
		);

		Solve (
			newtonFixedPointFinder,
			new double[] {
				 2.,
				-1.,
				 1.
			}
		);

		Solve (
			newtonFixedPointFinder,
			new double[]
			{
				-2.,
				-1.,
				 1.
			}
		);

		Solve (
			newtonFixedPointFinder,
			new double[]
			{
				0.,
				1.,
				0.
			}
		);

		Solve (
			newtonFixedPointFinder,
			new double[]
			{
				 0.,
				-1.,
				 0.
			}
		);
	}
}
