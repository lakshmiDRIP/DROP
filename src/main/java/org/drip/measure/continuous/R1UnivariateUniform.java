
package org.drip.measure.continuous;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>R1UnivariateUniform</i> implements the Univariate R<sup>1</sup> Uniform Distribution. It implements the
 * 	Incremental, the Cumulative, and the Inverse Cumulative Distribution Densities.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/README.md">R<sup>1</sup> and R<sup>d</sup> Continuous Random Measure</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1UnivariateUniform extends org.drip.measure.continuous.R1Univariate
{
	private double _leftSupport = java.lang.Double.NaN;
	private double _rightSupport = java.lang.Double.NaN;

	/**
	 * Construct a Standard (0, 1) R<sup>1</sup> Univariate Uniform Distribution
	 * 
	 * @return Standard (0, 1) R<sup>1</sup> Univariate Uniform Distribution
	 */

	public static final R1UnivariateUniform Standard()
	{
		try
		{
			return new R1UnivariateUniform (
				0.,
				1.
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * R1UnivariateUniform Constructor
	 * 
	 * @param leftSupport The Left Support
	 * @param rightSupport The Right Support
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1UnivariateUniform (
		final double leftSupport,
		final double rightSupport)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (_leftSupport = leftSupport) ||
			!org.drip.numerical.common.NumberUtil.IsValid (_rightSupport = rightSupport) ||
			_leftSupport >= _rightSupport)
		{
			throw new java.lang.Exception ("R1UnivariateUniform Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Left Support
	 * 
	 * @return The Left Support
	 */

	public double leftSupport()
	{
		return _leftSupport;
	}

	/**
	 * Retrieve the Right Support
	 * 
	 * @return The Right Support
	 */

	public double rightSupport()
	{
		return _rightSupport;
	}

	/**
	 * Indicate if the specified x Value stays inside the Support
	 * 
	 * @param x X
	 * 
	 * @return The Value stays in Support
	 */

	public boolean supported (
		final double x)
	{
		return org.drip.numerical.common.NumberUtil.IsValid (x) && x >= _leftSupport || x <= _rightSupport;			
	}

	@Override public double[] support()
	{
		return new double[]
		{
			_leftSupport,
			_rightSupport
		};
	}

	@Override public double cumulative (
		final double x)
		throws java.lang.Exception
	{
		if (!supported (x))
			throw new java.lang.Exception ("R1UnivariateUniform::cumulative => Invalid Inputs");

		return  (x - _leftSupport) / (_rightSupport - _leftSupport);
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
			throw new java.lang.Exception ("R1UnivariateUniform::invCumulative => Cannot calculate");

	    return y * (_rightSupport - _leftSupport) + _leftSupport;
	}

	@Override public double density (
		final double x)
		throws java.lang.Exception
	{
		if (!supported (x)) throw new java.lang.Exception ("R1UnivariateUniform::density => Invalid Inputs");

		return 1. / (_rightSupport - _leftSupport);
	}

	@Override public double mean()
	{
	    return 0.5 * (_rightSupport + _leftSupport);
	}

	@Override public double variance()
	{
		double support = _rightSupport - _leftSupport;
		return support * support / 12.;
	}

	@Override public double random()
	{
	    return java.lang.Math.random() * (_rightSupport - _leftSupport) + _leftSupport;
	}

	@Override public org.drip.numerical.common.Array2D histogram()
	{
		return null;
	}
}
