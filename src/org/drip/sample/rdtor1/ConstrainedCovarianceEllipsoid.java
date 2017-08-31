
package org.drip.sample.rdtor1;

import org.drip.function.definition.RdToR1;
import org.drip.function.rdtor1.*;
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
 * ConstrainedCovarianceEllipsoid demonstrates the Construction and Usage of a Co-variance Ellipsoid with
 *  Linear Constraints.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ConstrainedCovarianceEllipsoid {

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double[][] aadblCovarianceMatrix = new double[][] {
			{0.09, 0.12},
			{0.12, 0.04}
		};

		double[] adblEqualityConstraint = new double[] {
			1.,
			1.
		};

		double dblEqualityConstraintConstant = -1.;

		AffineMultivariate lmConstraintRdToR1 = new AffineMultivariate (
			adblEqualityConstraint,
			dblEqualityConstraintConstant
		);

		CovarianceEllipsoidMultivariate ceObjectiveRdToR1 = new CovarianceEllipsoidMultivariate (aadblCovarianceMatrix);

		LagrangianMultivariate ceec = new LagrangianMultivariate (
			ceObjectiveRdToR1,
			new RdToR1[] {
				lmConstraintRdToR1
			}
		);

		double[][] aadblVariate = {
			{0.0, 1.0, 1.0},
			{0.1, 0.9, 1.0},
			{0.2, 0.8, 1.0},
			{0.3, 0.7, 1.0},
			{0.4, 0.6, 1.0},
			{0.5, 0.5, 1.0},
			{0.6, 0.4, 1.0},
			{0.7, 0.3, 1.0},
			{0.8, 0.2, 1.0},
			{0.9, 0.1, 1.0},
			{1.0, 0.0, 1.0},
		};

		System.out.println ("\n\n\t|------------------------||");

		System.out.println ("\t|       POINT VALUE      ||");

		System.out.println ("\t|------------------------||");

		for (double[] adblVariate : aadblVariate)
			System.out.println (
				"\t|  [" + adblVariate[0] +
				" | " + adblVariate[1] +
				"] = " + FormatUtil.FormatDouble (ceec.evaluate (adblVariate), 1, 4, 1.) + " ||"
			);

		System.out.println ("\t|------------------------||");

		System.out.println ("\n\n\t|-------------------------------------------||");

		System.out.println ("\t|                 JACOBIAN                  ||");

		System.out.println ("\t|-------------------------------------------||");

		for (double[] adblVariate : aadblVariate) {
			String strJacobian = "";

			double[] adblJacobian = ceec.jacobian (adblVariate);

			for (double dblJacobian : adblJacobian)
				strJacobian += FormatUtil.FormatDouble (dblJacobian, 1, 4, 1.) + ",";

			System.out.println (
				"\t|  [" + adblVariate[0] +
				" | " + adblVariate[1] +
				"] = {" + strJacobian + "} ||"
			);
		}

		System.out.println ("\t|-------------------------------------------||");

		double[][] aadblHessian = ceec.hessian (
			new double[] {
				0.20,
				0.80,
				1.
			}
		);

		System.out.println ("\n\n\t|----------------------------||");

		System.out.println ("\t|          HESSIAN           ||");

		System.out.println ("\t|----------------------------||");

		for (double[] adblHessian : aadblHessian) {
			String strHessian = "";

			for (double dblHessian : adblHessian)
				strHessian += FormatUtil.FormatDouble (dblHessian, 1, 4, 1.) + ",";

			System.out.println ("\t| [" + strHessian + "] ||");
		}

		System.out.println ("\t|----------------------------||");
	}
}
