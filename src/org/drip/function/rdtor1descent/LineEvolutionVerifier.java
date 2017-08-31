
package org.drip.function.rdtor1descent;

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
 * LineEvolutionVerifier implements the Step Length Verification Criterion used for the Inexact Line Search
 *  Increment Generation. The References are:
 * 
 * 	- Armijo, L. (1966): Minimization of Functions having Lipschitz-Continuous First Partial Derivatives,
 * 		Pacific Journal of Mathematics 16 (1) 1-3.
 * 
 * 	- Wolfe, P. (1969): Convergence Conditions for Ascent Methods, SIAM Review 11 (2) 226-235.
 * 
 * 	- Wolfe, P. (1971): Convergence Conditions for Ascent Methods; II: Some Corrections, SIAM Review 13 (2)
 * 		185-188.
 * 
 * 	- Nocedal, J., and S. Wright (1999): Numerical Optimization, Wiley.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class LineEvolutionVerifier {

	protected static final double[] NextVariate (
		final org.drip.function.definition.UnitVector uvTargetDirection,
		final double[] adblCurrentVariate,
		final double dblStepLength)
	{
		if (null == adblCurrentVariate || !org.drip.quant.common.NumberUtil.IsValid (dblStepLength))
			return null;

		int iDimension = adblCurrentVariate.length;
		double[] adblNextVariate = 0 == iDimension ? null : new double[iDimension];

		if (null == adblNextVariate || null == uvTargetDirection) return null;

		double[] adblTargetDirection = uvTargetDirection.component();

		if (null == adblTargetDirection || iDimension != adblTargetDirection.length) return null;

		for (int i = 0; i < iDimension; ++i) {
			if (!org.drip.quant.common.NumberUtil.IsValid (adblCurrentVariate[i]) ||
				!org.drip.quant.common.NumberUtil.IsValid (adblTargetDirection[i]))
				return null;

			adblNextVariate[i] = adblCurrentVariate[i] + dblStepLength * adblTargetDirection[i];
		}

		return adblNextVariate;
	}

	/**
	 * Verify if the specified Inputs satisfy the Criterion
	 * 
	 * @param uvTargetDirection The Target Direction Unit Vector
	 * @param adblCurrentVariate The Current Variate
	 * @param funcRdToR1 The R^d To R^1 Function
	 * @param dblStepLength The Incremental Step Length
	 * 
	 * @return TRUE - The Specified Inputs satisfy the Criterion
	 * 
	 * @throws java.lang.Exception Thrown if the Verification cannot be performed
	 */

	public boolean verify (
		final org.drip.function.definition.UnitVector uvTargetDirection,
		final double[] adblCurrentVariate,
		final org.drip.function.definition.RdToR1 funcRdToR1,
		final double dblStepLength)
		throws java.lang.Exception
	{
		org.drip.function.rdtor1descent.LineEvolutionVerifierMetrics levm = metrics (uvTargetDirection,
			adblCurrentVariate, funcRdToR1, dblStepLength);

		if (null == levm) throw new java.lang.Exception ("LineEvolutionVerifier::verify => Cannot Verify");

		return levm.verify();
	}

	/**
	 * Generate the Verifier Metrics for the Specified Inputs
	 * 
	 * @param uvTargetDirection The Target Direction Unit Vector
	 * @param adblCurrentVariate The Current Variate
	 * @param funcRdToR1 The R^d To R^1 Function
	 * @param dblStepLength The Incremental Step Length
	 * 
	 * @return The Verifier Metrics
	 */

	public abstract org.drip.function.rdtor1descent.LineEvolutionVerifierMetrics metrics (
		final org.drip.function.definition.UnitVector uvTargetDirection,
		final double[] adblCurrentVariate,
		final org.drip.function.definition.RdToR1 funcRdToR1,
		final double dblStepLength);
}
