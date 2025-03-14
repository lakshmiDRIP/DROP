
package org.drip.spaces.rxtord;

import org.drip.function.definition.RdToRd;
import org.drip.numerical.common.NumberUtil;
import org.drip.spaces.instance.GeneralizedValidatedVector;
import org.drip.spaces.instance.ValidatedRd;
import org.drip.spaces.metric.RdNormed;

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
 * <i>NormedRdToNormedRd</i> is the Abstract Class underlying the f : Validated Normed R<sup>d</sup> to
 * 	Validated Normed R<sup>d</sup> Function Spaces. The Reference we've used is:
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
 * 		<li>Retrieve the Underlying <i>RdToRd</i> Function</li>
 * 		<li>Retrieve the Population R<sup>d</sup> ESS (Essential Spectrum) Array</li>
 * 		<li>Retrieve the Population R<sup>d</sup> Supremum Norm</li>
 * 		<li>Retrieve the Input Metric Vector Space</li>
 * 		<li>Retrieve the Output Metric Vector Space</li>
 * 		<li>Retrieve the Sample Supremum Norm Array</li>
 * 		<li>Retrieve the Sample Metric Norm Array</li>
 * 		<li>Retrieve the Population ESS (Essential Spectrum) Array</li>
 * 		<li>Retrieve the Population Supremum Norm Array</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/rxtord/README.md">R<sup>x</sup> To R<sup>d</sup> Normed Function Spaces</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class NormedRdToNormedRd
	extends NormedRxToNormedRd
{
	private RdToRd _rdToRdFunction = null;
	private RdNormed _rdNormedInput = null;
	private RdNormed _rdNormedOutput = null;

	/**
	 * <i>NormedRdToNormedRd</i> Constructor
	 * 
	 * @param rdNormedInput Normed R<sup>1</sup> Input Space
	 * @param rdNormedOutput Normed R<sup>1</sup> Output Space
	 * @param r1ToRdFunction R<sup>1</sup> to R<sup>1</sup> Function
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	protected NormedRdToNormedRd (
		final RdNormed rdNormedInput,
		final RdNormed rdNormedOutput,
		final RdToRd rdToRdFunction)
		throws Exception
	{
		if (null == (_rdNormedInput = rdNormedInput) || null == (_rdNormedOutput = rdNormedOutput)) {
			throw new Exception ("NormedRdToNormedRd ctr: Invalid Inputs");
		}

		_rdToRdFunction = rdToRdFunction;
	}

	/**
	 * Retrieve the Underlying <i>RdToRd</i> Function
	 * 
	 * @return The Underlying <i>RdToRd</i> Function
	 */

	public RdToRd function()
	{
		return _rdToRdFunction;
	}

	/**
	 * Retrieve the Population R<sup>d</sup> ESS (Essential Spectrum) Array
	 * 
	 * @return The Population R<sup>d</sup> ESS (Essential Spectrum) Array
	 */

	public double[] populationRdESS()
	{
		return _rdToRdFunction.evaluate (_rdNormedInput.populationMode());
	}

	/**
	 * Retrieve the Population R<sup>d</sup> Supremum Norm
	 * 
	 * @return The Population R<sup>d</sup> Supremum Norm
	 */

	public double[] populationRdSupremumNorm()
	{
		return populationRdESS();
	}

	/**
	 * Retrieve the Input Metric Vector Space
	 * 
	 * @return The Input Metric Vector Space
	 */

	@Override public org.drip.spaces.metric.RdNormed inputMetricVectorSpace()
	{
		return _rdNormedInput;
	}

	/**
	 * Retrieve the Output Metric Vector Space
	 * 
	 * @return The Output Metric Vector Space
	 */

	@Override public org.drip.spaces.metric.RdNormed outputMetricVectorSpace()
	{
		return _rdNormedOutput;
	}

	/**
	 * Retrieve the Sample Supremum Norm Array
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * 
	 * @return The Sample Supremum Norm Array
	 */

	@Override public double[] sampleSupremumNorm (
		final GeneralizedValidatedVector generalizedValidatedVector)
	{
		if (null == _rdToRdFunction || null == generalizedValidatedVector ||
			!generalizedValidatedVector.tensorSpaceType().match (_rdNormedInput) ||
			!(generalizedValidatedVector instanceof ValidatedRd))
		{
			return null;
		}

		double[][] rdValidatedVectorArray = ((ValidatedRd) generalizedValidatedVector).instance();

		double[] supremumNormArray = _rdToRdFunction.evaluate (rdValidatedVectorArray[0]);

		int outputDimension = _rdNormedOutput.dimension();

		if (null == supremumNormArray || outputDimension != supremumNormArray.length ||
			!NumberUtil.IsValid (supremumNormArray))
		{
			return null;
		}

		for (int i = 0; i < outputDimension; ++i) {
			supremumNormArray[i] = Math.abs (supremumNormArray[i]);
		}

		for (int i = 1; i < rdValidatedVectorArray.length; ++i) {
			double[] sampleNormArray = _rdToRdFunction.evaluate (rdValidatedVectorArray[i]);

			if (null == sampleNormArray || outputDimension != sampleNormArray.length) {
				return null;
			}

			for (int j = 0; j < outputDimension; ++j) {
				if (!NumberUtil.IsValid (sampleNormArray[j])) {
					return null;
				}

				if (sampleNormArray[j] > supremumNormArray[j]) {
					supremumNormArray[j] = sampleNormArray[j];
				}
			}
		}

		return supremumNormArray;
	}

	/**
	 * Retrieve the Sample Metric Norm Array
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * 
	 * @return The Sample Metric Norm Array
	 */

	@Override public double[] sampleMetricNorm (
		final GeneralizedValidatedVector generalizedValidatedVector)
	{
		int pNorm = outputMetricVectorSpace().pNorm();

		if (Integer.MAX_VALUE == pNorm) {
			return sampleSupremumNorm (generalizedValidatedVector);
		}

		if (null == _rdToRdFunction || null == generalizedValidatedVector ||
			!generalizedValidatedVector.tensorSpaceType().match (_rdNormedInput) ||
			!(generalizedValidatedVector instanceof ValidatedRd))
		{
			return null;
		}

		int outputDimension = _rdNormedOutput.dimension();

		double[][] rdValidatedVectorArray = ((ValidatedRd) generalizedValidatedVector).instance();

		double[] metricNormArray = new double[outputDimension];

		for (int i = 0; i < rdValidatedVectorArray.length; ++i) {
			metricNormArray[i] = 0.;
		}

		for (int i = 0; i < rdValidatedVectorArray.length; ++i) {
			double[] pointValueArray = _rdToRdFunction.evaluate (rdValidatedVectorArray[i]);

			if (null == pointValueArray || outputDimension != pointValueArray.length) {
				return null;
			}

			for (int j = 0; j < outputDimension; ++j) {
				if (!NumberUtil.IsValid (pointValueArray[j])) {
					return null;
				}

				metricNormArray[j] += Math.pow (Math.abs (pointValueArray[j]), pNorm);
			}
		}

		for (int i = 0; i < rdValidatedVectorArray.length; ++i) {
			metricNormArray[i] = Math.pow (metricNormArray[i], 1. / pNorm);
		}

		return metricNormArray;
	}

	/**
	 * Retrieve the Population ESS (Essential Spectrum) Array
	 * 
	 * @return The Population ESS (Essential Spectrum) Array
	 */

	@Override public double[] populationESS()
	{
		return _rdToRdFunction.evaluate (_rdNormedInput.populationMode());
	}

	/**
	 * Retrieve the Population Supremum Norm Array
	 * 
	 * @return The Population Supremum Norm Array
	 */

	@Override public double[] populationSupremumNorm()
	{
		return populationESS();
	}
}
