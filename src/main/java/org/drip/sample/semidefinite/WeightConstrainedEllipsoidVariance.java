
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
 * WeightConstrainedEllipsoidVariance demonstrates the Application of the Interior Point Method for
 *  Minimizing the Variance Across The Specified Ellipsoid under the Normalization Constraint.
 *
 * @author Lakshmi Krishnamurthy
 */

public class WeightConstrainedEllipsoidVariance
{

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv (
			""
		);

		String[] entityNameArray = new String[]
		{
			"IBM",
			"ATT",
			"ALU",
			"QCO",
			"INT",
			"MSF",
			"VER"
		};

		double[][] covarianceMatrix = new double[][]
		{
			{1.00, 0.76, 0.80, 0.38, 0.60, 0.61, 0.51},
			{0.76, 1.00, 0.65, 0.35, 0.56, 0.43, 0.40},
			{0.80, 0.65, 1.00, 0.68, 0.74, 0.40, 0.51},
			{0.38, 0.35, 0.68, 1.00, 0.72, 0.02, 0.57},
			{0.60, 0.56, 0.74, 0.72, 1.00, 0.31, 0.67},
			{0.61, 0.43, 0.40, 0.02, 0.31, 1.00, 0.39},
			{0.51, 0.40, 0.51, 0.57, 0.67, 0.39, 1.00}
		};

		System.out.println ("\n\n\t|------------------------------------------------------||");

		int entityCount = covarianceMatrix.length;
		double equalityConstraintConstant = -1.;
		String header = "\t|     |";

		for (int entityIndex = 0;
			entityIndex < entityCount;
			++entityIndex)
		{
			header += " " + entityNameArray[entityIndex] + "  |";
		}

		System.out.println (header + "|");

		System.out.println ("\t|------------------------------------------------------||");

		for (int entityIndexI = 0;
			entityIndexI < entityCount;
			++entityIndexI)
		{
			String dump = "\t| " + entityNameArray[entityIndexI] + " ";

			for (int entityIndexJ = 0;
				entityIndexJ < entityCount;
				++entityIndexJ)
			{
				dump += "|" + FormatUtil.FormatDouble (
					covarianceMatrix[entityIndexI][entityIndexJ], 1, 2, 1.
				) + " ";
			}

			System.out.println (dump + "||");
		}

		System.out.println ("\t|------------------------------------------------------||\n\n");

		InteriorPointBarrierControl interiorPointBarrierControl = InteriorPointBarrierControl.Standard();

		RdToR1[] equalityConstraintArray = new RdToR1[]
		{
			new AffineMultivariate (
				ObjectiveConstraintVariateSet.Unitary (
					entityCount
				),
				equalityConstraintConstant
			)
		};

		int equalityConstraintCount = equalityConstraintArray.length;

		RdToR1[] inequalityConstraintArray = new RdToR1[]
		{
			new AffineBoundMultivariate (false, 0, entityCount + equalityConstraintCount, 0.05),
			new AffineBoundMultivariate (true, 0, entityCount + equalityConstraintCount, 0.65),
			new AffineBoundMultivariate (false, 1, entityCount + equalityConstraintCount, 0.05),
			new AffineBoundMultivariate (true, 1, entityCount + equalityConstraintCount, 0.65),
			new AffineBoundMultivariate (false, 2, entityCount + equalityConstraintCount, 0.05),
			new AffineBoundMultivariate (true, 2, entityCount + equalityConstraintCount, 0.65),
			new AffineBoundMultivariate (false, 3, entityCount + equalityConstraintCount, 0.05),
			new AffineBoundMultivariate (true, 3, entityCount + equalityConstraintCount, 0.65),
			new AffineBoundMultivariate (false, 4, entityCount + equalityConstraintCount, 0.05),
			new AffineBoundMultivariate (true, 4, entityCount + equalityConstraintCount, 0.65),
			new AffineBoundMultivariate (false, 5, entityCount + equalityConstraintCount, 0.05),
			new AffineBoundMultivariate (true, 5, entityCount + equalityConstraintCount, 0.65),
			new AffineBoundMultivariate (false, 6, entityCount + equalityConstraintCount, 0.05),
			new AffineBoundMultivariate (true, 6, entityCount + equalityConstraintCount, 0.65)
		};

		LagrangianMultivariate lagrangianMultivariate = new LagrangianMultivariate (
			new CovarianceEllipsoidMultivariate (
				covarianceMatrix
			),
			equalityConstraintArray
		);

		double[] optimalVariateArray = new BarrierFixedPointFinder (
			lagrangianMultivariate,
			inequalityConstraintArray,
			interiorPointBarrierControl,
			LineStepEvolutionControl.NocedalWrightStrongWolfe (
				false
			)
		).solve (
			ObjectiveConstraintVariateSet.Uniform (
				entityCount,
				equalityConstraintCount
			)
		).variateArray();

		System.out.println ("\t|----------------------||");

		System.out.println ("\t|   OPTIMAL ENTITIES   ||");

		System.out.println ("\t|----------------------||");

		for (int entityIndex = 0;
			entityIndex < entityCount;
			++entityIndex)
		{
			System.out.println (
				"\t|   " + entityNameArray[entityIndex] + "   =>  " + FormatUtil.FormatDouble (
					optimalVariateArray[entityIndex], 2, 2, 100.
				) + "%  ||"
			);
		}

		System.out.println ("\t|----------------------||\n");

		System.out.println ("\t|------------------------------||");

		System.out.println (
			"\t| OPTIMAL VARIANCE => " + FormatUtil.FormatDouble (
				lagrangianMultivariate.evaluate (
					optimalVariateArray
				), 1, 5, 1.
			) + " ||"
		);

		System.out.println ("\t|------------------------------||\n");
	}
}
