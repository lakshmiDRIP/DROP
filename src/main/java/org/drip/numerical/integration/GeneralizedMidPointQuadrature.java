
package org.drip.numerical.integration;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>GeneralizedMidPointQuadrature</i> computes the R<sup>1</sup> Numerical Estimate of a Function
 * Quadrature using the Generalized Mid-Point Scheme. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Briol, F. X., C. J. Oates, M. Girolami, and M. A. Osborne (2015): <i>Frank-Wolfe Bayesian
 * 				Quadrature: Probabilistic Integration with Theoretical Guarantees</i> <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Forsythe, G. E., M. A. Malcolm, and C. B. Moler (1977): <i>Computer Methods for Mathematical
 * 				Computation</i> <b>Prentice Hall</b> Englewood Cliffs NJ
 * 		</li>
 * 		<li>
 * 			Leader, J. J. (2004): <i>Numerical Analysis and Scientific Computation</i> <b>Addison Wesley</b>
 * 		</li>
 * 		<li>
 * 			Stoer, J., and R. Bulirsch (1980): <i>Introduction to Numerical Analysis</i>
 * 				<b>Springer-Verlag</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Numerical Integration https://en.wikipedia.org/wiki/Numerical_integration
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/integration/README.md">R<sup>1</sup> R<sup>d</sup> Numerical Integration Schemes</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class GeneralizedMidPointQuadrature extends org.drip.numerical.integration.QuadratureBase
{
	private int _seriesTermCount = -1;

	/**
	 * GeneralizedMidPointQuadrature Constructor
	 * 
	 * @param r1ToR1 R<sup>1</sup> To R<sup>1</sup> Integrand
	 * @param nodeCount Quadrature Node Count
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public GeneralizedMidPointQuadrature (
		final org.drip.function.definition.R1ToR1 r1ToR1,
		final int nodeCount,
		final int seriesTermCount)
		throws java.lang.Exception
	{
		super (
			r1ToR1,
			nodeCount
		);

		if (0 >= (_seriesTermCount = seriesTermCount))
		{
			throw new java.lang.Exception ("GeneralizedMidPointQuadrature Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Series Term Count
	 * 
	 * @return The Series Term Count
	 */

	public int seriesTermCount()
	{
		return _seriesTermCount;
	}

	@Override public double integrate (
		final double left,
		final double right)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (left) ||
			!org.drip.numerical.common.NumberUtil.IsValid (right))
		{
			throw new java.lang.Exception ("GeneralizedMidPointQuadrature::integrate => Invalid Inputs");
		}

		if (left == right)
		{
			return 0.;
		}

		boolean flip = false;
		double quadrature = 0.;
		double leftLimit = left;
		double rightLimit = right;

		if (leftLimit > rightLimit)
		{
			flip = true;
			leftLimit = right;
			rightLimit = left;
		}

		int nodeCount = nodeCount();

		double m2 = 2. * nodeCount;
		double space = rightLimit - leftLimit;

		org.drip.function.definition.R1ToR1 r1ToR1 = r1ToR1();

		for (int nodeIndex = 1; nodeIndex <= nodeCount; ++nodeIndex)
		{
			for (int seriesTermIndex = 0; seriesTermIndex <= _seriesTermCount; ++seriesTermIndex)
			{
				int seriesTermIndex2 = 2 * seriesTermIndex;
				int seriesTermIndex2Plus1 = seriesTermIndex2 + 1;

				quadrature = quadrature + r1ToR1.derivative (
					space * (nodeIndex - 0.5) / nodeCount + leftLimit,
					2 * seriesTermIndex
				) / org.drip.numerical.common.NumberUtil.Factorial (seriesTermIndex2Plus1) /
				java.lang.Math.pow (
					m2,
					seriesTermIndex2Plus1
				);
			}
		}

		return (flip ? -1. : 1.) * space * quadrature;
	}
}
