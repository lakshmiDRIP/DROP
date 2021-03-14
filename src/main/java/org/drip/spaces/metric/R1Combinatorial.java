
package org.drip.spaces.metric;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * Spaces. The Reference we've used is:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/metric/README.md">Hilbert/Banach Normed Metric Spaces</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1Combinatorial extends org.drip.spaces.tensor.R1CombinatorialVector implements
	org.drip.spaces.metric.R1Normed {
	private int _iPNorm = -1;
	private org.drip.measure.continuous.R1Univariate _distR1 = null;

	/**
	 * Construct the Standard l^p R^1 Combinatorial Space Instance
	 * 
	 * @param lsElementSpace The List Space of Elements
	 * @param distR1 The R^1 Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * 
	 * @return The Standard l^p R^1 Combinatorial Space Instance
	 */

	public static final R1Combinatorial Standard (
		final java.util.List<java.lang.Double> lsElementSpace,
		final org.drip.measure.continuous.R1Univariate distR1,
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
	 * Construct the Supremum (i.e., l^Infinity) R^1 Combinatorial Space Instance
	 * 
	 * @param lsElementSpace The List Space of Elements
	 * @param distR1 The R^1 Borel Sigma Measure
	 * 
	 * @return The Supremum (i.e., l^Infinity) R^1 Combinatorial Space Instance
	 */

	public static final R1Combinatorial Supremum (
		final java.util.List<java.lang.Double> lsElementSpace,
		final org.drip.measure.continuous.R1Univariate distR1)
	{
		try {
			return new R1Combinatorial (lsElementSpace, distR1, java.lang.Integer.MAX_VALUE);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * R1Combinatorial Space Constructor
	 * 
	 * @param lsElementSpace The List Space of Elements
	 * @param distR1 The R^1 Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1Combinatorial (
		final java.util.List<java.lang.Double> lsElementSpace,
		final org.drip.measure.continuous.R1Univariate distR1,
		final int iPNorm)
		throws java.lang.Exception
	{
		super (lsElementSpace);

		if (0 > (_iPNorm = iPNorm))
			throw new java.lang.Exception ("R1Combinatorial Constructor: Invalid p-norm");

		_distR1 = distR1;
	}

	@Override public int pNorm()
	{
		return _iPNorm;
	}

	@Override public org.drip.measure.continuous.R1Univariate borelSigmaMeasure()
	{
		return _distR1;
	}

	@Override public double sampleMetricNorm (
		final double dblX)
		throws java.lang.Exception
	{
		if (!validateInstance (dblX))
			throw new java.lang.Exception ("R1Combinatorial::sampleMetricNorm => Invalid Inputs");

		return java.lang.Math.abs (dblX);
	}

	@Override public double populationMode()
		throws java.lang.Exception
	{
		if (null == _distR1)
			throw new java.lang.Exception ("R1Combinatorial::populationMode => Invalid Inputs");

		double dblMode = java.lang.Double.NaN;
		double dblModeProbability = java.lang.Double.NaN;

		for (double dblElement : elementSpace()) {
			if (!org.drip.numerical.common.NumberUtil.IsValid (dblMode))
				dblModeProbability = _distR1.density (dblMode = dblElement);
			else {
				double dblElementProbability = _distR1.density (dblElement);

				if (dblElementProbability > dblModeProbability) {
					dblMode = dblElement;
					dblModeProbability = dblElementProbability;
				}
			}
		}

		return dblMode;
	}

	@Override public double populationMetricNorm()
		throws java.lang.Exception
	{
		if (null == _distR1)
			throw new java.lang.Exception ("R1Combinatorial::populationMetricNorm => Invalid Inputs");

		double dblNorm = 0.;
		double dblNormalizer = 0.;

		for (double dblElement : elementSpace()) {
			double dblElementProbability = _distR1.density (dblElement);

			dblNormalizer += dblElementProbability;

			dblNorm += sampleMetricNorm (dblElement) * dblElementProbability;
		}

		return dblNorm / dblNormalizer;
	}

	@Override public double borelMeasureSpaceExpectation (
		final org.drip.function.definition.R1ToR1 funcR1ToR1)
		throws java.lang.Exception
	{
		if (null == funcR1ToR1 || null == _distR1)
			throw new java.lang.Exception
				("R1Combinatorial::borelMeasureSpaceExpectation => Invalid Inputs");

		double dblNormalizer = 0.;
		double dblBorelMeasureSpaceExpectation = 0.;

		for (double dblElement : elementSpace()) {
			double dblElementProbability = _distR1.density (dblElement);

			dblNormalizer += dblElementProbability;

			dblBorelMeasureSpaceExpectation += funcR1ToR1.evaluate (dblElement) * dblElementProbability;
		}

		return dblBorelMeasureSpaceExpectation / dblNormalizer;
	}
}
