
package org.drip.sample.matrix;

import org.drip.quant.common.FormatUtil;
import org.drip.quant.common.NumberUtil;
import org.drip.quant.linearalgebra.Matrix;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2018 Lakshmi Krishnamurthy
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
 * RayleighQuotient demonstrates the Computation of an Approximate to the Eigenvalue using the Rayleigh
 * 	Quotient. The References are:
 *  
 *  - Wikipedia - Power Iteration (2018): https://en.wikipedia.org/wiki/Power_iteration.
 *  
 *  - Wikipedia - Rayleigh Quotient Iteration (2018):
 *  	https://en.wikipedia.org/wiki/Rayleigh_quotient_iteration.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class RayleighQuotient
{

	private static final void EigenDump (
		final int iteration,
		final double[] eigenvector,
		final double eigenvalue)
		throws Exception
	{
		java.lang.String strDump = "\t|| Iteration => " + FormatUtil.FormatDouble (iteration, 2, 0, 1.) +
			"[" + FormatUtil.FormatDouble (eigenvalue, 3, 4, 1.) + "] => ";

		for (int i = 0; i < eigenvector.length; ++i)
			strDump += FormatUtil.FormatDouble (eigenvector[i], 1, 4, 1.) + " | ";

		System.out.println ("\t" + strDump);
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		int iterationCount = 5;
		double eigenvalue = 200.;
		double[][] a = {
			{1., 2., 3.},
			{1., 2., 1.},
			{3., 2., 1.},
		};
		double[] eigenvector = {
			1. / Math.sqrt (3.),
			1. / Math.sqrt (3.),
			1. / Math.sqrt (3.)
		};

		NumberUtil.PrintMatrix (
			"\t|| A ",
			a
		);

		EigenDump (
			0,
			eigenvector,
			eigenvalue
		);

		int iterationIndex = 0;

		while (++iterationIndex < iterationCount)
		{
			double[][] deDiagonalized = new double[a.length][a.length];

			for (int row = 0; row < a.length; ++row)
			{
				for (int column = 0; column < a.length; ++column)
				{
					deDiagonalized[row][column] = a[row][column];

					if (row == column)
					{
						deDiagonalized[row][column] -= eigenvalue;
					}
				}
			}

			eigenvector = Matrix.Normalize (
				Matrix.Product (
					Matrix.InvertUsingGaussianElimination (deDiagonalized),
					eigenvector
				)
			);

			eigenvalue = Matrix.DotProduct (
				eigenvector,
				Matrix.Product (
					a,
					eigenvector
				)
			);

			EigenDump (
				iterationIndex,
				eigenvector,
				eigenvalue
			);
		}

		EnvManager.TerminateEnv();
	}
}
