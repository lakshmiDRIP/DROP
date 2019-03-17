
package org.drip.measure.continuous;

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
 * <i>R1UnivariateExponential</i> implements the Univariate R<sup>1</sup> Exponential Distribution. It
 * implements the Incremental, the Cumulative, and the Inverse Cumulative Distribution Densities.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous">Continuous</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1UnivariateExponential extends org.drip.measure.continuous.R1Univariate
{
	private double _lambda = java.lang.Double.NaN;

	/**
	 * Construct the Standard R1UnivariateExponential Distribution (lambda = 1.)
	 * 
	 * @return The Standard R1UnivariateExponential Distribution
	 */

	public static final R1UnivariateExponential Standard()
	{
		try
		{
			return new R1UnivariateExponential (1.);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * R1UnivariateExponential Constructor
	 * 
	 * @param lambda Lambda (Inverse Scaling Parameter)
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1UnivariateExponential (
		final double lambda)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_lambda = lambda) || 0. >= _lambda)
		{
			throw new java.lang.Exception ("R1UnivariateExponential Constructor => Invalid Inputs: " + _lambda);
		}
	}

	/**
	 * Retrieve the Lambda (Inverse Scaling Parameter)
	 * 
	 * @return The Lambda (Inverse Scaling Parameter)
	 */

	public double lambda()
	{
		return _lambda;
	}

	@Override public double cumulative (
		final double x)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (x) || x < 0.)
			throw new java.lang.Exception ("R1UnivariateExponential::cumulative => Invalid Inputs");

		return 1. - java.lang.Math.exp (-1. * _lambda * x);
	}

	@Override public double incremental (
		final double xLeft,
		final double xRight)
		throws java.lang.Exception
	{
		return cumulative (xLeft) - cumulative (xRight);
	}

	@Override public double invCumulative (
		final double y)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (y) || 1. < y || 0. > y)
			throw new java.lang.Exception ("R1UnivariateExponential::invCumulative => Cannot calculate");

	    return -1. / _lambda * java.lang.Math.log (1. - y);
	}

	@Override public double density (
		final double x)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (x) || x < 0.)
			throw new java.lang.Exception ("R1UnivariateExponential::density => Invalid Inputs");

		return _lambda * java.lang.Math.exp (-1. * _lambda * x);
	}

	@Override public double mean()
	{
	    return 1. / _lambda;
	}

	@Override public double variance()
	{
	    return 1. / (_lambda * _lambda);
	}

	@Override public org.drip.numerical.common.Array2D histogram()
	{
		return null;
	}
}
