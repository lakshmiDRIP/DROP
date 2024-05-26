
package org.drip.spaces.metric;

import org.drip.function.definition.R1ToR1;
import org.drip.function.definition.VariateOutputPair;
import org.drip.measure.continuous.R1Univariate;
import org.drip.spaces.tensor.R1ContinuousVector;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>R1Continuous</i> implements the Normed, Bounded/Unbounded Continuous l<sub>p</sub> R<sup>1</sup>
 * 	Spaces. The Reference we've used is:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  </ul>
 *
 * It provides the following Functionality:
 *
 *  <ul>
 * 		<li>Construct the Standard l<sup>p</sup> R<sup>1</sup>1 Continuous Space Instance</li>
 * 		<li>Construct the Supremum (i.e., l^Infinity) R<sup>1</sup> Continuous Space Instance</li>
 * 		<li><i>R1Continuous</i> Space Constructor</li>
 *  </ul>
 *
 *  <br>
 *  <style>table, td, th {
 *  	padding: 1px; border: 2px solid #008000; border-radius: 8px; background-color: #dfff00;
 *		text-align: center; color:  #0000ff;
 *  }
 *  </style>
 *  
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/metric/README.md">Hilbert/Banach Normed Metric Spaces</a></td></tr>
 *  </table>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1Continuous extends R1ContinuousVector implements R1Normed
{
	private int _pNorm = -1;
	private R1Univariate _r1Univariate = null;

	/**
	 * Construct the Standard l<sup>p</sup> R<sup>1</sup> Continuous Space Instance
	 * 
	 * @param dblLeftEdge The Left Edge
	 * @param dblRightEdge The Right Edge
	 * @param distR1 The R<sup>1</sup> Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * 
	 * @return The Standard l<sup>p</sup> R<sup>1</sup> Continuous Space Instance
	 */

	public static final R1Continuous Standard (
		final double dblLeftEdge,
		final double dblRightEdge,
		final org.drip.measure.continuous.R1Univariate distR1,
		final int iPNorm)
	{
		try {
			return new R1Continuous (dblLeftEdge, dblRightEdge, distR1, iPNorm);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Supremum (i.e., l<sup>Infinity</sup>) R<sup>1</sup> Continuous Space Instance
	 * 
	 * @param dblLeftEdge The Left Edge
	 * @param dblRightEdge The Right Edge
	 * @param distR1 The R<sup>1</sup> Borel Sigma Measure
	 * 
	 * @return The Supremum (i.e., l<sup>Infinity</sup>) R<sup>1</sup> Continuous Space Instance
	 */

	public static final R1Continuous Supremum (
		final double dblLeftEdge,
		final double dblRightEdge,
		final org.drip.measure.continuous.R1Univariate distR1)
	{
		try {
			return new R1Continuous (dblLeftEdge, dblRightEdge, distR1, java.lang.Integer.MAX_VALUE);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>R1Continuous</i> Space Constructor
	 * 
	 * @param leftEdge The Left Edge
	 * @param rightEdge The Right Edge
	 * @param r1Univariate The R<sup>1</sup> Borel Sigma Measure
	 * @param pNorm The p-norm of the Space
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public R1Continuous (
		final double leftEdge,
		final double rightEdge,
		final R1Univariate r1Univariate,
		final int pNorm)
		throws Exception
	{
		super (leftEdge, rightEdge);

		if (0 > (_pNorm = pNorm)) {
			throw new Exception ("R1Continuous Constructor: Invalid p-norm");
		}

		_r1Univariate = r1Univariate;
	}

	@Override public int pNorm()
	{
		return _pNorm;
	}

	@Override public R1Univariate borelSigmaMeasure()
	{
		return _r1Univariate;
	}

	@Override public double sampleMetricNorm (
		final double x)
		throws Exception
	{
		if (!validateInstance (x)) {
			throw new Exception ("R1Continuous::sampleMetricNorm => Invalid Inputs");
		}

		return Math.abs (x);
	}

	@Override public double populationMode()
		throws Exception
	{
		if (null == _r1Univariate) {
			throw new Exception ("R1Continuous::populationMode => Invalid Inputs");
		}

		VariateOutputPair modeVariateOutputPair = new R1ToR1 (null) {
			@Override public double evaluate (
				final double dblX)
				throws Exception
			{
				return _r1Univariate.density (dblX);
			}
		}.maxima (leftEdge(), rightEdge());

		if (null == modeVariateOutputPair) {
			throw new Exception ("R1Continuous::populationMode => Cannot compute VOP Mode");
		}

		return modeVariateOutputPair.variates()[0];
	}

	@Override public double populationMetricNorm()
		throws Exception
	{
		if (null == _r1Univariate) {
			throw new Exception ("R1Continuous::populationMetricNorm => Invalid Inputs");
		}

		return new R1ToR1 (null) {
			@Override public double evaluate (
				final double x)
				throws Exception
			{
				return sampleMetricNorm (x) * _r1Univariate.density (x);
			}
		}.integrate (leftEdge(), rightEdge());
	}

	@Override public double borelMeasureSpaceExpectation (
		final R1ToR1 r1ToR1Function)
		throws Exception
	{
		if (null == r1ToR1Function || null == _r1Univariate) {
			throw new Exception ("R1Continuous::borelMeasureSpaceExpectation => Invalid Inputs");
		}

		return new R1ToR1 (null) {
			@Override public double evaluate (
				final double dblX)
				throws Exception
			{
				return r1ToR1Function.evaluate (dblX) * _r1Univariate.density (dblX);
			}
		}.integrate (leftEdge(), rightEdge());
	}
}
