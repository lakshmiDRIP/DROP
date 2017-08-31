
package org.drip.learning.kernel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * IntegralOperator implements the R^x L2 To R^x L2 Mercer Kernel Integral Operator defined by:
 * 
 * 		T_k [f(.)] := Integral Over Input Space {k (., y) * f(y) * d[Prob(y)]}
 *  
 *  The References are:
 *  
 *  1) Ash, R. (1965): Information Theory, Inter-science New York.
 *  
 *  2) Konig, H. (1986): Eigenvalue Distribution of Compact Operators, Birkhauser, Basel, Switzerland. 
 *  
 *  3) Smola, A. J., A. Elisseff, B. Scholkopf, and R. C. Williamson (2000): Entropy Numbers for Convex
 *  	Combinations and mlps, in: Advances in Large Margin Classifiers, A. Smola, P. Bartlett, B. Scholkopf,
 *  	and D. Schuurmans - editors, MIT Press, Cambridge, MA.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class IntegralOperator {
	private org.drip.measure.continuous.Rd _distRd = null;
	private org.drip.function.definition.RdToR1 _funcRdToR1 = null;
	private org.drip.spaces.metric.R1Normed _r1OperatorOutput = null;
	private org.drip.learning.kernel.SymmetricRdToNormedR1Kernel _kernel = null;

	/**
	 * IntegralOperator Constructor
	 * 
	 * @param kernel The Symmetric Mercer Kernel - this should be R^x L2 X R^x L2 To R^1
	 * @param funcRdToR1 The R^d To R^1 Operator Function
	 * @param r1OperatorOutput The Kernel Integral Operator Output Space - this is R^1 L2
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public IntegralOperator (
		final org.drip.learning.kernel.SymmetricRdToNormedR1Kernel kernel,
		final org.drip.function.definition.RdToR1 funcRdToR1,
		final org.drip.spaces.metric.R1Normed r1OperatorOutput)
		throws java.lang.Exception
	{
		if (null == (_kernel = kernel) || null == (_funcRdToR1 = funcRdToR1) || null == (_r1OperatorOutput =
			r1OperatorOutput) || null == (_distRd = _kernel.inputMetricVectorSpace().borelSigmaMeasure()))
			throw new java.lang.Exception ("IntegralOperator ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Symmetric R^d To R^1 Kernel
	 * 
	 * @return The Symmetric R^d To R^1 Kernel
	 */

	public org.drip.learning.kernel.SymmetricRdToNormedR1Kernel kernel()
	{
		return _kernel;
	}

	/**
	 * Retrieve the R^d To R^1 Kernel Operator Function
	 * 
	 * @return The R^d To R^1 Kernel Operator Function
	 */

	public org.drip.function.definition.RdToR1 kernelOperatorFunction()
	{
		return _funcRdToR1;
	}

	/**
	 * Retrieve the Input Space Borel Sigma Measure
	 * 
	 * @return The Input Space Borel Sigma Measure
	 */

	public org.drip.measure.continuous.Rd inputSpaceBorelMeasure()
	{
		return _distRd;
	}

	/**
	 * Retrieve the Kernel Integral Operator Output Space
	 * 
	 * @return The Kernel Integral Operator Output Space
	 */

	public org.drip.spaces.metric.R1Normed outputVectorMetricSpace()
	{
		return _r1OperatorOutput;
	}

	/**
	 * Compute the Operator's Kernel Integral across the specified X Variate Instance
	 * 
	 * @param adblX Validated Vector Instance X
	 * 
	 * @return The Operator's Kernel Integral across the specified X Variate Instance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public double computeOperatorIntegral (
		final double[] adblX)
		throws java.lang.Exception
	{
		org.drip.function.definition.RdToR1 funcRdToR1 = new org.drip.function.definition.RdToR1 (null) {
			@Override public int dimension()
			{
				return null == adblX ? 0 : adblX.length;
			}

			@Override public double evaluate (
				final double[] adblY)
				throws java.lang.Exception
			{
				return _kernel.evaluate (adblX, adblY) * _funcRdToR1.evaluate (adblY);
			}
		};

		return _kernel.inputMetricVectorSpace().borelMeasureSpaceExpectation (funcRdToR1);
	}

	/**
	 * Indicate the Kernel Operator Integral's Positive-definiteness across the specified X Variate Instance
	 * 
	 * @param adblX Validated Vector Instance X
	 * 
	 * @return TRUE - The Kernel Operator Integral is Positive Definite across the specified X Variate
	 *  Instance
	 */

	public boolean isPositiveDefinite (
		final double[] adblX)
	{
		try {
			return 0 < computeOperatorIntegral (adblX);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Eigenize the Kernel Integral Operator
	 * 
	 * @return The Eigenization Output
	 */

	public abstract org.drip.learning.kernel.IntegralOperatorEigenContainer eigenize();
}
