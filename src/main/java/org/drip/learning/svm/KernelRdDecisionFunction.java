
package org.drip.learning.svm;

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
 * <i>KernelRdDecisionFunction</i> implements the Kernel-based R<sup>d</sup> Decision Function-Based SVM
 * Functionality for Classification and Regression.
 * 
 * <br><br>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Learning</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/svm">Support Vector Machines</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/StatisticalLearning">Statistical Learning Library</a></li>
 *  </ul>
 * <br><br>
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
