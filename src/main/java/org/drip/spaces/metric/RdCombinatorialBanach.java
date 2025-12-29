
package org.drip.spaces.metric;

import org.drip.function.definition.RdToR1;
import org.drip.measure.continuous.RdDistribution;
import org.drip.spaces.iterator.RdSpanningCombinatorialIterator;
import org.drip.spaces.tensor.R1CombinatorialVector;
import org.drip.spaces.tensor.RdCombinatorialVector;

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
 * <i>RdCombinatorialBanach</i> implements the Bounded/Unbounded Combinatorial l<sub>p</sub> R<sup>d</sup>
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
 * 		<li><i>RdCombinatorialBanach</i> Space Constructor</li>
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

public class RdCombinatorialBanach extends RdCombinatorialVector implements RdNormed
{
	private int _pNorm = -1;
	private RdDistribution _rdContinuous = null;

	/**
	 * <i>RdCombinatorialBanach</i> Space Constructor
	 * 
	 * @param r1CombinatorialVectorArray Array of Combinatorial R<sup>1</sup> Vector Spaces
	 * @param rdContinuous The R<sup>d</sup> Borel Sigma Measure
	 * @param pNorm The p-norm of the Space
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public RdCombinatorialBanach (
		final R1CombinatorialVector[] r1CombinatorialVectorArray,
		final RdDistribution rdContinuous,
		final int pNorm)
		throws Exception
	{
		super (r1CombinatorialVectorArray);

		if (0 > (_pNorm = pNorm)) {
			throw new Exception ("RdCombinatorialBanach Constructor: Invalid p-norm");
		}

		_rdContinuous = rdContinuous;
	}

	@Override public int pNorm()
	{
		return _pNorm;
	}

	@Override public RdDistribution borelSigmaMeasure()
	{
		return _rdContinuous;
	}

	@Override public double sampleSupremumNorm (
		final double[] xArray)
		throws Exception
	{
		if (!validateInstance (xArray)) {
			throw new Exception ("RdCombinatorialBanach::sampleSupremumNorm => Invalid Inputs");
		}

		int dimension = xArray.length;

		double norm = Math.abs (xArray[0]);

		for (int i = 1; i < dimension; ++i) {
			double absoluteX = Math.abs (xArray[i]);

			norm = norm > absoluteX ? norm : absoluteX;
		}

		return norm;
	}

	@Override public double sampleMetricNorm (
		final double[] xArray)
		throws Exception
	{
		if (!validateInstance (xArray)) {
			throw new Exception ("RdCombinatorialBanach::sampleMetricNorm => Invalid Inputs");
		}

		if (Integer.MAX_VALUE == _pNorm) {
			return sampleSupremumNorm (xArray);
		}

		double norm = 0.;
		int dimension = xArray.length;

		for (int i = 0; i < dimension; ++i) {
			norm += Math.pow (Math.abs (xArray[i]), _pNorm);
		}

		return Math.pow (norm, 1. / _pNorm);
	}

	@Override public double[] populationMode()
	{
		if (null == _rdContinuous) {
			return null;
		}

		RdSpanningCombinatorialIterator rdSpanningCombinatorialIterator = iterator();

		double[] variateArray = rdSpanningCombinatorialIterator.cursorVariates();

		double modeProbabilityDensity = 0.;
		int dimension = variateArray.length;
		double probabilityDensity = Double.NaN;
		double[] modeVariateArray = new double[dimension];

		while (null != variateArray) {
			try {
				probabilityDensity = _rdContinuous.density (variateArray);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}

			if (probabilityDensity > modeProbabilityDensity) {
				for (int i = 0; i < dimension; ++i) {
					modeVariateArray[i] = variateArray[i];
				}

				modeProbabilityDensity = probabilityDensity;
			}

			variateArray = rdSpanningCombinatorialIterator.nextVariates();
		}

		return modeVariateArray;
	}

	@Override public double populationSupremumNorm()
		throws Exception
	{
		if (null == _rdContinuous) {
			throw new Exception ("RdCombinatorialBanach::populationSupremumNorm => Invalid Inputs");
		}

		return sampleSupremumNorm (populationMode());
	}

	@Override public double populationMetricNorm()
		throws Exception
	{
		if (Integer.MAX_VALUE == _pNorm) {
			return sampleSupremumNorm (populationMode());
		}

		if (null == _rdContinuous) {
			throw new Exception (
				"RdCombinatorialBanach::populationMetricNorm => No Multivariate Distribution"
			);
		}

		RdSpanningCombinatorialIterator rdSpanningCombinatorialIterator = iterator();

		double[] variateArray = rdSpanningCombinatorialIterator.cursorVariates();

		double normalizer = 0.;
		double populationMetricNorm  = 0.;
		int dimension = variateArray.length;

		while (null != variateArray) {
			double probabilityDensity = _rdContinuous.density (variateArray);

			normalizer += probabilityDensity;

			for (int i = 0; i < dimension; ++i) {
				populationMetricNorm += probabilityDensity * Math.pow (Math.abs (variateArray[i]), _pNorm);
			}

			variateArray = rdSpanningCombinatorialIterator.nextVariates();
		}

		return Math.pow (populationMetricNorm / normalizer, 1. / _pNorm);
	}

	@Override public double borelMeasureSpaceExpectation (
		final RdToR1 rdToR1Function)
		throws Exception
	{
		if (null == _rdContinuous || null == rdToR1Function) {
			throw new Exception ("RdCombinatorialBanach::borelMeasureSpaceExpectation => Invalid Inputs");
		}

		RdSpanningCombinatorialIterator rdSpanningCombinatorialIterator = iterator();

		double[] variateArray = rdSpanningCombinatorialIterator.cursorVariates();

		double borelMeasureSpaceExpectation = 0.;
		double normalizer = 0.;

		while (null != variateArray) {
			double probabilityDensity = _rdContinuous.density (variateArray);

			normalizer += probabilityDensity;

			borelMeasureSpaceExpectation += probabilityDensity * rdToR1Function.evaluate (variateArray);

			variateArray = rdSpanningCombinatorialIterator.nextVariates();
		}

		return borelMeasureSpaceExpectation / normalizer;
	}
}
