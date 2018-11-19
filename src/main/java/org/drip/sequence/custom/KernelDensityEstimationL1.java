
package org.drip.sequence.custom;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>KernelDensityEstimationL1</i> implements the L1 Error Scheme Estimation for a Multivariate Kernel
 * Density Estimator with Focus on establishing targeted Variate-Specific and Agnostic Bounds.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Project</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence">Sequence</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sequence/custom">Custom</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
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
