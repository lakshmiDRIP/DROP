
package org.drip.measure.distribution;

import org.drip.numerical.common.Array2D;
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
 * <i>I1ContinuousUniform</i> implements the Univariate Bounded Uniform Integer Distribution, with the
 * 	Integer being generated between a (n inclusive) lower and an upper Bound. It provides the following
 * 	Functionality:
 *
 *  <ul>
 * 		<li>Construct a Univariate Bounded Uniform Integer Distribution</li>
 * 		<li>Retrieve the Start</li>
 * 		<li>Retrieve the Finish</li>
 * 		<li>Lay out the Support of the PDF Range</li>
 * 		<li>Compute the cumulative under the distribution to the given value</li>
 * 		<li>Compute the Incremental under the Distribution between the 2 variates</li>
 * 		<li>Compute the inverse cumulative under the distribution corresponding to the given value</li>
 * 		<li>Compute the Density under the Distribution at the given Variate</li>
 * 		<li>Retrieve the Mean of the Distribution</li>
 * 		<li>Retrieve the Variance of the Distribution</li>
 * 		<li>Retrieve the Univariate Weighted Histogram</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/distribution/README.md">R<sup>1</sup> and R<sup>d</sup> Continuous Random Measure</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class I1ContinuousUniform
	extends R1Continuous
	{
	private int _start = -1;
	private int _finish = -1;

	/**
	 * Construct a Univariate Bounded Uniform Integer Distribution
	 * 
	 * @param start The Starting Integer
	 * @param finish The Finishing Integer
	 * 
	 * @throws Exception Thrown if the inputs are invalid
	 */

	public I1ContinuousUniform (
		final int start,
		final int finish)
		throws Exception
	{
		if ((_finish = finish) <= (_start = start)) {
			throw new Exception ("I1ContinuousUniform constructor: Invalid inputs");
		}
	}

	/**
	 * Retrieve the Start
	 * 
	 * @return The Start
	 */

	public int start()
	{
		return _start;
	}

	/**
	 * Retrieve the Finish
	 * 
	 * @return The Finish
	 */

	public int finish()
	{
		return _finish;
	}

	/**
	 * Lay out the Support of the PDF Range
	 * 
	 * @return Support of the PDF Range
	 */

	@Override public double[] support()
	{
		return new double[] {_start, _finish};
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
		if (!NumberUtil.IsValid (x)) {
			throw new Exception ("I1ContinuousUniform::cumulative => Invalid inputs");
		}

		if (x <= _start) {
			return 0.;
		}

		if (x >= _finish) {
			return 1.;
		}

		return (x - _start) / (_finish - _start);
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
		return cumulative (xRight) - cumulative (xLeft);
	}

	/**
	 * Compute the inverse cumulative under the distribution corresponding to the given value
	 * 
	 * @param y Value corresponding to which the inverse cumulative is to be computed
	 * 
	 * @return The inverse cumulative
	 * 
	 * @throws Exception Thrown if the Input is invalid
	 */

	@Override public double invCumulative (
		final double y)
		throws Exception
	{
		if (!NumberUtil.IsValid (y) || y < 0. || y > 1.) {
			throw new Exception ("I1ContinuousUniform::invCumulative => Invalid inputs");
		}

	    return y * (_finish - _start) + _start;
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
		throw new Exception ("I1ContinuousUniform::density => Not available for discrete distributions");
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
	    return 0.5 * (_finish + _start);
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
	    return (_finish - _start) * (_finish - _start) / 12.;
	}

	/**
	 * Retrieve the Univariate Weighted Histogram
	 * 
	 * @return The Univariate Weighted Histogram
	 */

	@Override public Array2D histogram()
	{
		int gridWidth = _finish - _start;
		double[] xArray = new double[gridWidth];
		double[] yArray = new double[gridWidth];

		for (int i = 0; i < gridWidth; ++i) {
			yArray[i] = 1. / gridWidth;
			xArray[i] = _start + (i + 1);
		}

		return Array2D.FromArray (xArray, yArray);
	}
}
