
package org.drip.sample.semidefinite;

import org.drip.function.definition.RdToR1;
import org.drip.function.rdtor1.*;
import org.drip.function.rdtor1descent.LineStepEvolutionControl;
import org.drip.function.rdtor1solver.*;
import org.drip.quant.common.FormatUtil;
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
 * WeightConstrainedEllipsoidVariance demonstrates the Application of the Interior Point Method for
 *  Minimizing the Variance Across The Specified Ellipsoid under the Normalization Constraint.
 *
 * @author Lakshmi Krishnamurthy
 */

public class WeightConstrainedEllipsoidVariance {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		String[] astrEntityName = new String[] {
			"IBM",
			"ATT",
			"ALU",
			"QCO",
			"INT",
			"MSF",
			"VER"
		};

		double[][] aadblCovarianceMatrix = new double[][] {
			{1.00, 0.76, 0.80, 0.38, 0.60, 0.61, 0.51},
			{0.76, 1.00, 0.65, 0.35, 0.56, 0.43, 0.40},
			{0.80, 0.65, 1.00, 0.68, 0.74, 0.40, 0.51},
			{0.38, 0.35, 0.68, 1.00, 0.72, 0.02, 0.57},
			{0.60, 0.56, 0.74, 0.72, 1.00, 0.31, 0.67},
			{0.61, 0.43, 0.40, 0.02, 0.31, 1.00, 0.39},
			{0.51, 0.40, 0.51, 0.57, 0.67, 0.39, 1.00}
		};

		System.out.println ("\n\n\t|------------------------------------------------------||");

		String strHeader = "\t|     |";

		for (int i = 0; i < astrEntityName.length; ++i)
			strHeader += " " + astrEntityName[i] + "  |";

		System.out.println (strHeader + "|");

		System.out.println ("\t|------------------------------------------------------||");

		for (int i = 0; i < astrEntityName.length; ++i) {
			String strDump = "\t| " + astrEntityName[i] + " ";

			for (int j = 0; j < astrEntityName.length; ++j)
				strDump += "|" + FormatUtil.FormatDouble (aadblCovarianceMatrix[i][j], 1, 2, 1.) + " ";

			System.out.println (strDump + "||");
		}

		System.out.println ("\t|------------------------------------------------------||\n\n");

		double dblEqualityConstraintConstant = -1.;
		int iNumEntity = aadblCovarianceMatrix.length;

		InteriorPointBarrierControl ipbc = InteriorPointBarrierControl.Standard();

		RdToR1[] aRdToR1EqualityConstraint = new RdToR1[] {
			new AffineMultivariate (
				ObjectiveConstraintVariateSet.Unitary (iNumEntity),
				dblEqualityConstraintConstant
			)
		};

		int iNumEqualityConstraint = aRdToR1EqualityConstraint.length;

		RdToR1[] aRdToR1InequalityConstraint = new RdToR1[] {
			new AffineBoundMultivariate (false, 0, iNumEntity + iNumEqualityConstraint, 0.05),
			new AffineBoundMultivariate (true, 0, iNumEntity + iNumEqualityConstraint, 0.65),
			new AffineBoundMultivariate (false, 1, iNumEntity + iNumEqualityConstraint, 0.05),
			new AffineBoundMultivariate (true, 1, iNumEntity + iNumEqualityConstraint, 0.65),
			new AffineBoundMultivariate (false, 2, iNumEntity + iNumEqualityConstraint, 0.05),
			new AffineBoundMultivariate (true, 2, iNumEntity + iNumEqualityConstraint, 0.65),
			new AffineBoundMultivariate (false, 3, iNumEntity + iNumEqualityConstraint, 0.05),
			new AffineBoundMultivariate (true, 3, iNumEntity + iNumEqualityConstraint, 0.65),
			new AffineBoundMultivariate (false, 4, iNumEntity + iNumEqualityConstraint, 0.05),
			new AffineBoundMultivariate (true, 4, iNumEntity + iNumEqualityConstraint, 0.65),
			new AffineBoundMultivariate (false, 5, iNumEntity + iNumEqualityConstraint, 0.05),
			new AffineBoundMultivariate (true, 5, iNumEntity + iNumEqualityConstraint, 0.65),
			new AffineBoundMultivariate (false, 6, iNumEntity + iNumEqualityConstraint, 0.05),
			new AffineBoundMultivariate (true, 6, iNumEntity + iNumEqualityConstraint, 0.65)
		};

		LagrangianMultivariate ceec = new LagrangianMultivariate (
			new CovarianceEllipsoidMultivariate (aadblCovarianceMatrix),
			aRdToR1EqualityConstraint
		);

		BarrierFixedPointFinder ifpm = new BarrierFixedPointFinder (
			ceec,
			aRdToR1InequalityConstraint,
			ipbc,
			LineStepEvolutionControl.NocedalWrightStrongWolfe (false)
		);

		VariateInequalityConstraintMultiplier vcmt = ifpm.solve (
			ObjectiveConstraintVariateSet.Uniform (
				iNumEntity,
				iNumEqualityConstraint
			)
		);

		double[] adblOptimalVariate = vcmt.variates();

		System.out.println ("\t|----------------------||");

		System.out.println ("\t|   OPTIMAL ENTITIES   ||");

		System.out.println ("\t|----------------------||");

		for (int i = 0; i < iNumEntity; ++i)
			System.out.println ("\t|   " + astrEntityName[i] + "   =>  " + FormatUtil.FormatDouble (adblOptimalVariate[i], 2, 2, 100.) + "%  ||");

		System.out.println ("\t|----------------------||\n");

		System.out.println ("\t|------------------------------||");

		System.out.println ("\t| OPTIMAL VARIANCE => " + FormatUtil.FormatDouble (ceec.evaluate (adblOptimalVariate), 1, 5, 1.) + " ||");

		System.out.println ("\t|------------------------------||\n");
	}
}
