
package org.drip.sequence.custom;

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
 * KernelDensityEstimationL1 implements the L1 Error Scheme Estimation for a Multivariate Kernel Density
 *  Estimator with Focus on establishing targeted Variate-Specific and Agnostic Bounds.
 *
 * @author Lakshmi Krishnamurthy
 */

public class KernelDensityEstimationL1 extends org.drip.sequence.functional.BoundedMultivariateRandom {
	private int _iSampleSize = -1;
	private double _dblSmoothingParameter = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _auKernel = null;
	private org.drip.function.definition.R1ToR1 _auResponse = null;

	/**
	 * KernelDensityEstimationL1 Constructor
	 * 
	 * @param auKernel The Kernel Function
	 * @param dblSmoothingParameter The Smoothing Parameter
	 * @param iSampleSize The Sample Size
	 * @param auResponse The Response Function
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are Invalid
	 */

	public KernelDensityEstimationL1 (
		final org.drip.function.definition.R1ToR1 auKernel,
		final double dblSmoothingParameter,
		final int iSampleSize,
		final org.drip.function.definition.R1ToR1 auResponse)
		throws java.lang.Exception
	{
		if (null == (_auKernel = auKernel) || !org.drip.quant.common.NumberUtil.IsValid
			(_dblSmoothingParameter = dblSmoothingParameter) || 0 >= (_iSampleSize = iSampleSize) || null ==
				(_auResponse = auResponse))
			throw new java.lang.Exception ("KernelDensityEstimationL1 Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Kernel Function
	 * 
	 * @return The Kernel Function
	 */

	public org.drip.function.definition.R1ToR1 kernelFunction()
	{
		return _auKernel;
	}

	/**
	 * Retrieve the Smoothing Parameter
	 * 
	 * @return The Smoothing Parameter
	 */

	public double smoothingParameter()
	{
		return _dblSmoothingParameter;
	}

	/**
	 * Retrieve the Sample Size
	 * 
	 * @return The Sample Size
	 */

	public int sampleSize()
	{
		return _iSampleSize;
	}

	/**
	 * Retrieve the Response Function
	 * 
	 * @return The Response Function
	 */

	public org.drip.function.definition.R1ToR1 responseFunction()
	{
		return _auResponse;
	}

	@Override public int dimension()
	{
		return org.drip.function.definition.RdToR1.DIMENSION_NOT_FIXED;
	}

	@Override public double evaluate (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		double dblMinVariate = org.drip.quant.common.NumberUtil.Minimum (adblVariate);

		double dblMaxVariate = org.drip.quant.common.NumberUtil.Maximum (adblVariate);

		double dblKernelIntegral = 0.;
		int iNumVariate = adblVariate.length;

		for (int i = 0; i < iNumVariate; ++i)
			dblKernelIntegral += _auKernel.integrate ((dblMinVariate - adblVariate[i]) /
				_dblSmoothingParameter, (dblMaxVariate - adblVariate[i]) / _dblSmoothingParameter);

		return dblKernelIntegral / (_iSampleSize * _dblSmoothingParameter) - _auResponse.integrate
			(dblMinVariate, dblMaxVariate);
	}

	@Override public double targetVariateVarianceBound (
		final int iTargetVariateIndex)
		throws java.lang.Exception
	{
		return 4. / (_iSampleSize * _iSampleSize);
	}
}
