
package org.drip.learning.kernel;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	and computational support.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
 *  
 *  - DROP Product Core - https://lakshmidrip.github.io/DROP-Product-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Computational Core - https://lakshmidrip.github.io/DROP-Computational-Core/
 * 
 * 	DROP Product Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Loan Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 *  - Asset Liability Management Analytics
 * 	- Capital Estimation Analytics
 * 	- Exposure Analytics
 * 	- Margin Analytics
 * 	- XVA Analytics
 * 
 * 	DROP Computational Core implements libraries for the following:
 * 	- Algorithm Support
 * 	- Computation Support
 * 	- Function Analysis
 *  - Model Validation
 * 	- Numerical Analysis
 * 	- Numerical Optimizer
 * 	- Spline Builder
 *  - Statistical Learning
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Repo Layout Taxonomy     => https://github.com/lakshmiDRIP/DROP/blob/master/Taxonomy.md
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
 * <i>IntegralOperator</i> implements the R<sup>x</sup> L<sub>2</sub> To R<sup>x</sup> L<sub>2</sub> Mercer
 * 	Kernel Integral Operator defined by:
 * 
 * 		T_k [f(.)] := Integral Over Input Space {k (., y) * f(y) * d[Prob(y)]}
 *  
 * <br><br>
 *  The References are:
 * <br><br>
 * <ul>
 * 	<li>
 *  	Ash, R. (1965): <i>Information Theory</i> <b>Inter-science</b> New York
 * 	</li>
 * 	<li>
 *  	Konig, H. (1986): <i>Eigenvalue Distribution of Compact Operators</i> <b>Birkhauser</b> Basel,
 *  		Switzerland
 * 	</li>
 * 	<li>
 *  	Smola, A. J., A. Elisseff, B. Scholkopf, and R. C. Williamson (2000): Entropy Numbers for Convex
 *  		Combinations and mlps, in: <i>Advances in Large Margin Classifiers, A. Smola, P. Bartlett, B.
 *  		Scholkopf, and D. Schuurmans - editors</i> <b>MIT Press</b> Cambridge, MA
 * 	</li>
 * </ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Agnostic Learning Bounds under Empirical Loss Minimization Schemes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/kernel">Statistical Learning Banach Mercer Kernels</a></li>
 *  </ul>
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
