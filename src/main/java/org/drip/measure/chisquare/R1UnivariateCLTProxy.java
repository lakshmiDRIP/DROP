
package org.drip.measure.chisquare;

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
 * <i>R1UnivariateCLTProxy</i> implements the N (0, 1) CLT Proxy Version for the Chi-Square Distribution. The
 * References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Chi-Squared Distribution (2019): Chi-Squared Function
 * 				https://en.wikipedia.org/wiki/Chi-squared_distribution
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Project</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/ode/README.md">Special Function Ordinary Differential Equations</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1UnivariateCLTProxy extends org.drip.measure.continuous.R1Univariate
{
	private int _degreesOfFreedom = -1;

	private org.drip.measure.gaussian.R1UnivariateNormal _r1UnivariateNormal =
		org.drip.measure.gaussian.R1UnivariateNormal.Standard();

	/**
	 * R1UnivariateCLTProxy Constructor
	 * 
	 * @param degreesOfFreedom Degrees of Freedom
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1UnivariateCLTProxy (
		final int degreesOfFreedom)
		throws java.lang.Exception
	{
		if (0 >= (_degreesOfFreedom = degreesOfFreedom))
		{
			throw new java.lang.Exception ("R1UnivariateCLTProxy Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Degrees of Freedom
	 * 
	 * @return The Degrees of Freedom
	 */

	public int degreesOfFreedom()
	{
		return _degreesOfFreedom;
	}

	@Override public double[] support()
	{
		return new double[]
		{
			-1. * java.lang.Math.sqrt (0.5 * _degreesOfFreedom),
			java.lang.Double.POSITIVE_INFINITY
		};
	}

	@Override public double density (
		final double t)
		throws java.lang.Exception
	{
		return _r1UnivariateNormal.density (t);
	}

	@Override public double cumulative (
		final double t)
		throws java.lang.Exception
	{
		return _r1UnivariateNormal.cumulative (t);
	}

	@Override public double mean()
		throws java.lang.Exception
	{
		return 0.;
	}

	@Override public double median()
		throws java.lang.Exception
	{
		return 0.;
	}

	@Override public double mode()
		throws java.lang.Exception
	{
		return 0.;
	}

	@Override public double variance()
		throws java.lang.Exception
	{
		return 1.;
	}

	@Override public double skewness()
		throws java.lang.Exception
	{
		return java.lang.Math.sqrt (8. / _degreesOfFreedom);
	}

	@Override public double excessKurtosis()
		throws java.lang.Exception
	{
		return 12. / _degreesOfFreedom;
	}

	@Override public double differentialEntropy()
		throws java.lang.Exception
	{
		return _r1UnivariateNormal.differentialEntropy();
	}

	@Override public org.drip.function.definition.R1ToR1 momentGeneratingFunction()
	{
		return _r1UnivariateNormal.momentGeneratingFunction();
	}

	@Override public org.drip.function.definition.R1ToR1 probabilityGeneratingFunction()
	{
		return _r1UnivariateNormal.probabilityGeneratingFunction();
	}

	@Override public double random()
		throws java.lang.Exception
	{
		double sumOfStandardNormalSquares = 0.;

		for (int drawIndex = 0; drawIndex < _degreesOfFreedom; ++drawIndex)
		{
			double randomStandardNormal = org.drip.measure.gaussian.NormalQuadrature.InverseCDF
				(java.lang.Math.random());

			sumOfStandardNormalSquares = sumOfStandardNormalSquares +
				randomStandardNormal * randomStandardNormal;
		}

		return (sumOfStandardNormalSquares - _degreesOfFreedom) /
			java.lang.Math.sqrt (2. * _degreesOfFreedom);
	}
}
