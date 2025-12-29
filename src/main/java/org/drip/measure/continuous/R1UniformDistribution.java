
package org.drip.measure.continuous;

import org.drip.numerical.common.NumberUtil;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
 * Copyright (C) 2025 Lakshmi Krishnamurthy
 * Copyright (C) 2024 Lakshmi Krishnamurthy
 * Copyright (C) 2023 Lakshmi Krishnamurthy
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting analytics/risk, transaction cost analytics,
 *  	asset liability management analytics, capital, exposure, and margin analytics, valuation adjustment
 *  	analytics, and portfolio construction analytics within and across fixed income, credit, commodity,
 *  	equity, FX, and structured products. It also includes auxiliary libraries for algorithm support,
 *  	numerical analysis, numerical optimization, spline builder, model validation, statistical learning,
 *  	graph builder/navigator, and computational support.
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
 *  - Graph Algorithm
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
 * <i>R1UniformDistribution</i> implements the Univariate R<sup>1</sup> Uniform Distribution. It implements
 * 	the Incremental, the Cumulative, and the Inverse Cumulative Distribution Densities. It provides the
 * 	following Functionality:
 *
 *  <ul>
 * 		<li>Construct a Standard (0, 1) R<sup>1</sup> Univariate Uniform Distribution</li>
 * 		<li><i>R1UniformDistribution</i> Constructor</li>
 * 		<li>Retrieve the Left Support</li>
 * 		<li>Retrieve the Right Support</li>
 * 		<li>Indicate if the specified x Value stays inside the Support</li>
 * 		<li>Lay out the Support of the PDF Range</li>
 * 		<li>Compute the cumulative under the distribution to the given value</li>
 * 		<li>Compute the Incremental under the Distribution between the 2 variates</li>
 * 		<li>Compute the inverse cumulative under the distribution corresponding to the given value</li>
 * 		<li>Compute the Density under the Distribution at the given Variate</li>
 * 		<li>Retrieve the Mean of the Distribution</li>
 * 		<li>Retrieve the Variance of the Distribution</li>
 * 		<li>Generate a Random Variable corresponding to the Distribution</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/README.md">R<sup>1</sup> and R<sup>d</sup> Continuous Random Measure</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1UniformDistribution
	extends R1Distribution
{
	private double _leftSupport = Double.NaN;
	private double _rightSupport = Double.NaN;

	/**
	 * Construct a Standard (0, 1) R<sup>1</sup> Uniform Distribution
	 * 
	 * @return Standard (0, 1) R<sup>1</sup> Uniform Distribution
	 */

	public static final R1UniformDistribution Standard()
	{
		try {
			return new R1UniformDistribution (0., 1.);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>R1UniformDistribution</i> Constructor
	 * 
	 * @param leftSupport The Left Support
	 * @param rightSupport The Right Support
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public R1UniformDistribution (
		final double leftSupport,
		final double rightSupport)
		throws Exception
	{
		if (!NumberUtil.IsValid (_leftSupport = leftSupport) ||
			!NumberUtil.IsValid (_rightSupport = rightSupport) ||
			_leftSupport >= _rightSupport)
		{
			throw new Exception ("R1UniformDistribution Constructor => Invalid Inputs");
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
		return NumberUtil.IsValid (x) && x >= _leftSupport || x <= _rightSupport;			
	}

	/**
	 * Lay out the Support of the PDF Range
	 * 
	 * @return Support of the PDF Range
	 */

	@Override public double[] support()
	{
		return new double[] {_leftSupport, _rightSupport};
	}

	/**
	 * Compute the cumulative under the distribution to the given value
	 * 
	 * @param x Variate to which the cumulative is to be computed
	 * 
	 * @return The cumulative
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	@Override public double cumulative (
		final double x)
		throws Exception
	{
		if (!supported (x)) {
			throw new Exception ("R1UniformDistribution::cumulative => Invalid Inputs");
		}

		return  (x - _leftSupport) / (_rightSupport - _leftSupport);
	}

	/**
	 * Compute the Incremental under the Distribution between the 2 variates
	 * 
	 * @param xLeft Left Variate to which the cumulative is to be computed
	 * @param xRight Right Variate to which the cumulative is to be computed
	 * 
	 * @return The Incremental under the Distribution between the 2 variates
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	@Override public double incremental (
		final double xLeft,
		final double xRight)
		throws Exception
	{
		return cumulative (xLeft) - cumulative (xRight);
	}

	/**
	 * Compute the inverse cumulative under the distribution corresponding to the given value
	 * 
	 * @param p Value corresponding to which the inverse cumulative is to be computed
	 * 
	 * @return The inverse cumulative
	 * 
	 * @throws Exception Thrown if the Input is invalid
	 */

	@Override public double invCumulative (
		final double y)
		throws Exception
	{
		if (!NumberUtil.IsValid (y) || 1. < y || 0. > y) {
			throw new Exception ("R1UniformDistribution::invCumulative => Cannot calculate");
		}

	    return y * (_rightSupport - _leftSupport) + _leftSupport;
	}

	/**
	 * Compute the Density under the Distribution at the given Variate
	 * 
	 * @param x Variate at which the Density needs to be computed
	 * 
	 * @return The Density
	 * 
	 * @throws Exception Thrown if the input is invalid
	 */

	@Override public double density (
		final double x)
		throws Exception
	{
		if (!supported (x)) {
			throw new Exception ("R1UniformDistribution::density => Invalid Inputs");
		}

		return 1. / (_rightSupport - _leftSupport);
	}

	/**
	 * Retrieve the Mean of the Distribution
	 * 
	 * @return The Mean of the Distribution
	 * 
	 * @throws Exception Thrown if the Mean cannot be estimated
	 */

	@Override public double mean()
	{
	    return 0.5 * (_rightSupport + _leftSupport);
	}

	/**
	 * Retrieve the Variance of the Distribution
	 * 
	 * @return The Variance of the Distribution
	 * 
	 * @throws Exception Thrown if the Variance cannot be estimated
	 */

	@Override public double variance()
	{
		double support = _rightSupport - _leftSupport;
		return support * support / 12.;
	}

	/**
	 * Generate a Random Variable corresponding to the Distribution
	 * 
	 * @return Random Variable corresponding to the Distribution
	 * 
	 * @throws Exception Thrown if the Random Instance cannot be estimated
	 */

	@Override public double random()
	{
	    return Math.random() * (_rightSupport - _leftSupport) + _leftSupport;
	}
}
