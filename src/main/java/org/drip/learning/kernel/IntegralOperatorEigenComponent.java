
package org.drip.learning.kernel;

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
 * <i>IntegralOperatorEigenComponent</i> holds the Eigen-Function Space and the Eigenvalue Functions/Spaces
 * of the R<sup>x</sup> L<sub>2</sub> To R<sup>x</sup> L<sub>2</sub> Kernel Linear Integral Operator defined
 * by:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning">Learning</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/learning/kernel">Kernel</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class IntegralOperatorEigenComponent {
	private double _dblEigenValue = java.lang.Double.NaN;
	private org.drip.learning.kernel.EigenFunctionRdToR1 _efRdToR1 = null;
	private org.drip.spaces.rxtor1.NormedRdToNormedR1 _rkhsFeatureMap = null;

	/**
	 * IntegralOperatorEigenComponent Constructor
	 * 
	 * @param efRdToR1 Normed R^d To Normed R^1 Eigen-Function
	 * @param dblEigenValue The Eigenvalue
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public IntegralOperatorEigenComponent (
		final org.drip.learning.kernel.EigenFunctionRdToR1 efRdToR1,
		final double dblEigenValue)
		throws java.lang.Exception
	{
		if (null == (_efRdToR1 = efRdToR1) || !org.drip.quant.common.NumberUtil.IsValid (_dblEigenValue =
			dblEigenValue))
			throw new java.lang.Exception ("IntegralOperatorEigenComponent ctr: Invalid Inputs");

		final org.drip.function.definition.RdToR1 eigenFuncRdToR1 = _efRdToR1.function();

		if (null != eigenFuncRdToR1) {
			org.drip.function.definition.RdToR1 rkhsFeatureMapRdToR1 = new
				org.drip.function.definition.RdToR1 (null) {
				@Override public int dimension()
				{
					return org.drip.function.definition.RdToR1.DIMENSION_NOT_FIXED;
				}

				@Override public double evaluate (
					final double[] adblX)
					throws java.lang.Exception
				{
					return java.lang.Math.sqrt (_dblEigenValue) * eigenFuncRdToR1.evaluate (adblX);
				}
			};

			org.drip.spaces.metric.RdNormed rdContinuousInput = efRdToR1.inputMetricVectorSpace();

			org.drip.spaces.metric.R1Normed r1ContinuousOutput = efRdToR1.outputMetricVectorSpace();

			org.drip.spaces.metric.R1Continuous r1Continuous = org.drip.spaces.metric.R1Continuous.Standard
				(r1ContinuousOutput.leftEdge(), r1ContinuousOutput.rightEdge(),
					r1ContinuousOutput.borelSigmaMeasure(), 2);

			_rkhsFeatureMap = rdContinuousInput instanceof org.drip.spaces.metric.RdCombinatorialBanach ? new
				org.drip.spaces.rxtor1.NormedRdCombinatorialToR1Continuous
					((org.drip.spaces.metric.RdCombinatorialBanach) rdContinuousInput, r1Continuous,
						rkhsFeatureMapRdToR1) : new org.drip.spaces.rxtor1.NormedRdContinuousToR1Continuous
							((org.drip.spaces.metric.RdContinuousBanach) rdContinuousInput, r1Continuous,
								rkhsFeatureMapRdToR1);
		}
	}

	/**
	 * Retrieve the Eigen-Function
	 * 
	 * @return The Eigen-Function
	 */

	public org.drip.learning.kernel.EigenFunctionRdToR1 eigenFunction()
	{
		return _efRdToR1;
	}

	/**
	 * Retrieve the Eigenvalue
	 * 
	 * @return The Eigenvalue
	 */

	public double eigenvalue()
	{
		return _dblEigenValue;
	}

	/**
	 * Retrieve the Feature Map Space represented via the Reproducing Kernel Hilbert Space
	 * 
	 * @return The Feature Map Space representation using the Reproducing Kernel Hilbert Space
	 */

	public org.drip.spaces.rxtor1.NormedRdToNormedR1 rkhsFeatureMap()
	{
		return _rkhsFeatureMap;
	}

	/**
	 * Retrieve the RKHS Feature Map Parallelepiped Agnostic Upper Bound Length
	 * 
	 * @return The RKHS Feature Map Parallelepiped Agnostic Upper Bound Length
	 */

	public double rkhsFeatureParallelepipedLength()
	{
		return 2. * _efRdToR1.agnosticUpperBound() * java.lang.Math.sqrt (_dblEigenValue);
	}

	/**
	 * Compute the Eigen-Component Contribution to the Kernel Value
	 * 
	 * @param adblX The X Variate Array
	 * @param adblY The Y Variate Array
	 * 
	 * @return The Eigen-Component Contribution to the Kernel Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public double evaluate (
		final double[] adblX,
		final double[] adblY)
		throws java.lang.Exception
	{
		org.drip.function.definition.RdToR1 eigenFuncRdToR1 = _efRdToR1.function();

		return eigenFuncRdToR1.evaluate (adblX) * eigenFuncRdToR1.evaluate (adblY) * _dblEigenValue;
	}
}
