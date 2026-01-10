
package org.drip.spaces.metric;

import java.util.List;

import org.drip.function.definition.R1ToR1;
import org.drip.measure.distribution.R1Continuous;
import org.drip.numerical.common.NumberUtil;
import org.drip.spaces.tensor.R1CombinatorialVector;

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
 * <i>R1Combinatorial</i> implements the Normed, Bounded/Unbounded Combinatorial l<sub>p</sub> R<sup>d</sup>
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
 * 		<li>Construct the Standard l<sub>p</sub> R<sub>1</sub> Combinatorial Space Instance</li>
 * 		<li>Construct the Supremum (i.e., l<sub>Infinity</sub>) R<sub>1</sub> Combinatorial Space Instance</li>
 * 		<li><i>R1Combinatorial</i> Space Constructor</li>
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

public class R1Combinatorial extends R1CombinatorialVector implements R1Normed
{
	private int _pNorm = -1;
	private R1Continuous _r1Distribution = null;

	/**
	 * Construct the Standard l<sub>p</sub> R<sub>1</sub> Combinatorial Space Instance
	 * 
	 * @param lsElementSpace The List Space of Elements
	 * @param distR1 The R<sub>1</sub> Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * 
	 * @return The Standard l<sub>p</sub> R<sub>1</sub> Combinatorial Space Instance
	 */

	public static final R1Combinatorial Standard (
		final java.util.List<java.lang.Double> lsElementSpace,
		final org.drip.measure.distribution.R1Continuous distR1,
		final int iPNorm)
	{
		try {
			return new R1Combinatorial (lsElementSpace, distR1, iPNorm);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Construct the Supremum (i.e., l<sub>Infinity</sub>) R<sub>1</sub> Combinatorial Space Instance
	 * 
	 * @param lsElementSpace The List Space of Elements
	 * @param distR1 The R<sub>1</sub> Borel Sigma Measure
	 * 
	 * @return The Supremum (i.e., l<sub>Infinity</sub>) R<sub>1</sub> Combinatorial Space Instance
	 */

	public static final R1Combinatorial Supremum (
		final java.util.List<java.lang.Double> lsElementSpace,
		final org.drip.measure.distribution.R1Continuous distR1)
	{
		try {
			return new R1Combinatorial (lsElementSpace, distR1, java.lang.Integer.MAX_VALUE);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>R1Combinatorial</i> Space Constructor
	 * 
	 * @param elementSpaceList The List Space of Elements
	 * @param r1Distribution The R<sub>1</sub> Borel Sigma Measure
	 * @param pNorm The p-norm of the Space
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public R1Combinatorial (
		final List<Double> elementSpaceList,
		final R1Continuous r1Distribution,
		final int pNorm)
		throws Exception
	{
		super (elementSpaceList);

		if (0 > (_pNorm = pNorm)) {
			throw new Exception ("R1Combinatorial Constructor: Invalid p-norm");
		}

		_r1Distribution = r1Distribution;
	}

	@Override public int pNorm()
	{
		return _pNorm;
	}

	@Override public R1Continuous borelSigmaMeasure()
	{
		return _r1Distribution;
	}

	@Override public double sampleMetricNorm (
		final double x)
		throws Exception
	{
		if (!validateInstance (x)) {
			throw new Exception ("R1Combinatorial::sampleMetricNorm => Invalid Inputs");
		}

		return Math.abs (x);
	}

	@Override public double populationMode()
		throws Exception
	{
		if (null == _r1Distribution) {
			throw new Exception ("R1Combinatorial::populationMode => Invalid Inputs");
		}

		double mode = Double.NaN;
		double modeProbability = Double.NaN;

		for (double element : elementSpace()) {
			if (!NumberUtil.IsValid (mode)) {
				modeProbability = _r1Distribution.density (mode = element);
			} else {
				double elementProbability = _r1Distribution.density (element);

				if (elementProbability > modeProbability) {
					mode = element;
					modeProbability = elementProbability;
				}
			}
		}

		return mode;
	}

	@Override public double populationMetricNorm()
		throws Exception
	{
		if (null == _r1Distribution) {
			throw new Exception ("R1Combinatorial::populationMetricNorm => Invalid Inputs");
		}

		double norm = 0.;
		double normalizer = 0.;

		for (double element : elementSpace()) {
			double elementProbability = _r1Distribution.density (element);

			normalizer += elementProbability;

			norm += sampleMetricNorm (element) * elementProbability;
		}

		return norm / normalizer;
	}

	@Override public double borelMeasureSpaceExpectation (
		final R1ToR1 r1ToR1Function)
		throws Exception
	{
		if (null == r1ToR1Function || null == _r1Distribution) {
			throw new Exception ("R1Combinatorial::borelMeasureSpaceExpectation => Invalid Inputs");
		}

		double normalizer = 0.;
		double borelMeasureSpaceExpectation = 0.;

		for (double element : elementSpace()) {
			double elementProbability = _r1Distribution.density (element);

			normalizer += elementProbability;

			borelMeasureSpaceExpectation += r1ToR1Function.evaluate (element) * elementProbability;
		}

		return borelMeasureSpaceExpectation / normalizer;
	}
}
