
package org.drip.learning.svm;

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
 * KernelRdDecisionFunction implements the Kernel-based R^d Decision Function-Based SVM Functionality for
 * 	Classification and Regression.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class KernelRdDecisionFunction extends org.drip.learning.svm.RdDecisionFunction {
	private double[][] _aadblKernelPredictorPivot = null;
	private org.drip.learning.kernel.SymmetricRdToNormedRdKernel _kernel = null;

	/**
	 * KernelRdDecisionFunction Constructor
	 * 
	 * @param rdInverseMargin The Inverse Margin Weights R^d Space
	 * @param adblInverseMarginWeight Array of Inverse Margin Weights
	 * @param dblB The Kernel Offset
	 * @param kernel The Kernel
	 * @param aadblKernelPredictorPivot Array of the Kernel R^d Predictor Pivot Nodes
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public KernelRdDecisionFunction (
		final org.drip.spaces.metric.RdNormed rdInverseMargin,
		final double[] adblInverseMarginWeight,
		final double dblB,
		final org.drip.learning.kernel.SymmetricRdToNormedRdKernel kernel,
		final double[][] aadblKernelPredictorPivot)
		throws java.lang.Exception
	{
		super (kernel.inputMetricVectorSpace(), rdInverseMargin, adblInverseMarginWeight, dblB);

		if (null == (_kernel = kernel) || null == (_aadblKernelPredictorPivot = aadblKernelPredictorPivot))
			throw new java.lang.Exception ("KernelRdDecisionFunction ctr: Invalid Inputs");

		int iKernelInputDimension = _kernel.inputMetricVectorSpace().dimension();

		int iNumPredictorPivot = adblInverseMarginWeight.length;

		if (0 == iNumPredictorPivot || iNumPredictorPivot != _aadblKernelPredictorPivot.length)
			throw new java.lang.Exception ("KernelRdDecisionFunction ctr: Invalid Inputs");

		for (int i = 0; i < iNumPredictorPivot; ++i) {
			if (null == _aadblKernelPredictorPivot[i] || _aadblKernelPredictorPivot[i].length !=
				iKernelInputDimension)
				throw new java.lang.Exception ("KernelRdDecisionFunction ctr: Invalid Inputs");
		}
	}

	@Override public double evaluate (
		final double[] adblX)
		throws java.lang.Exception
	{
		if (null == adblX || adblX.length != _kernel.inputMetricVectorSpace().dimension())
			throw new java.lang.Exception ("KernelRdDecisionFunction::evaluate => Invalid Inputs");

		double[] adblInverseMarginWeight = inverseMarginWeights();

		double dblDotProduct = 0.;
		int iNumPredictorPivot = adblInverseMarginWeight.length;

		for (int i = 0; i < iNumPredictorPivot; ++i)
			dblDotProduct += adblInverseMarginWeight[i] * _kernel.evaluate (_aadblKernelPredictorPivot[i],
				adblX);

		return dblDotProduct + offset();
	}

	/**
	 * Retrieve the Decision Kernel
	 * 
	 * @return The Decision Kernel
	 */

	public org.drip.learning.kernel.SymmetricRdToNormedRdKernel kernel()
	{
		return _kernel;
	}

	/**
	 * Retrieve the Decision Kernel Predictor Pivot Nodes
	 * 
	 * @return The Decision Kernel Predictor Pivot Nodes
	 */

	public double[][] kernelPredictorPivot()
	{
		return _aadblKernelPredictorPivot;
	}
}
