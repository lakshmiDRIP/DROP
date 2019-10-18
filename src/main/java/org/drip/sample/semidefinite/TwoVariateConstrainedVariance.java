
package org.drip.sample.semidefinite;

import org.drip.function.definition.RdToR1;
import org.drip.function.rdtor1.*;
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
 * TwoVariateConstrainedVariance demonstrates the Application of the Interior Point Method for minimizing
 * 	the Variance Across Two Variates under the Normalization Constraint.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TwoVariateConstrainedVariance
{

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
	}
}
