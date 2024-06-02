
package org.drip.spaces.rxtord;

import org.drip.numerical.common.NumberUtil;
import org.drip.spaces.instance.GeneralizedValidatedVector;
import org.drip.spaces.metric.GeneralizedMetricVectorSpace;
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
 * <i>NormedRxToNormedRd</i> is the Abstract Class that exposes f : Normed R<sup>x</sup> (x .gte. 1) to
 * 	Normed R<sup>d</sup> Function Space. The Reference we've used is:
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
 * 		<li>Retrieve the Input Metric Vector Space</li>
 * 		<li>Retrieve the Output Metric Vector Space</li>
 * 		<li>Retrieve the Sample Supremum Norm Array</li>
 * 		<li>Retrieve the Sample Metric Norm Array</li>
 * 		<li>Retrieve the Sample Covering Number Array</li>
 * 		<li>Retrieve the Sample Supremum Covering Number Array</li>
 * 		<li>Retrieve the Population ESS (Essential Spectrum) Array</li>
 * 		<li>Retrieve the Population Metric Norm Array</li>
 * 		<li>Retrieve the Population Supremum Norm Array</li>
 * 		<li>Retrieve the Population Covering Number Array</li>
 * 		<li>Retrieve the Population Supremum Covering Number Array</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/rxtord/README.md">R<sup>x</sup> To R<sup>d</sup> Normed Function Spaces</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class NormedRxToNormedRd
{

	/**
	 * Retrieve the Input Metric Vector Space
	 * 
	 * @return The Input Metric Vector Space
	 */

	public abstract GeneralizedMetricVectorSpace inputMetricVectorSpace();

	/**
	 * Retrieve the Output Metric Vector Space
	 * 
	 * @return The Output Metric Vector Space
	 */

	public abstract RdNormed outputMetricVectorSpace();

	/**
	 * Retrieve the Sample Supremum Norm Array
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * 
	 * @return The Sample Supremum Norm Array
	 */

	public abstract double[] sampleSupremumNorm (
		final GeneralizedValidatedVector generalizedValidatedVector
	);

	/**
	 * Retrieve the Sample Metric Norm Array
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * 
	 * @return The Sample Metric Norm Array
	 */

	public abstract double[] sampleMetricNorm (
		final GeneralizedValidatedVector generalizedValidatedVector
	);

	/**
	 * Retrieve the Sample Covering Number Array
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * @param cover The Cover
	 * 
	 * @return The Sample Covering Number Array
	 */

	public double[] sampleCoveringNumber (
		final GeneralizedValidatedVector generalizedValidatedVector,
		final double cover)
	{
		if (!NumberUtil.IsValid (cover) || 0. >= cover) {
			return null;
		}

		double[] sampleMetricNormArray = sampleMetricNorm (generalizedValidatedVector);

		if (null == sampleMetricNormArray) {
			return null;
		}

		int outputDimensionality = sampleMetricNormArray.length;
		double[] sampleCoveringNumberArray = new double[outputDimensionality];

		if (0 == outputDimensionality) {
			return null;
		}

		double coverBallVolume = Math.pow (cover, outputMetricVectorSpace().pNorm());

		for (int i = 0; i < outputDimensionality; ++i) {
			sampleCoveringNumberArray[i] = sampleMetricNormArray[i] / coverBallVolume;
		}

		return sampleCoveringNumberArray;
	}

	/**
	 * Retrieve the Sample Supremum Covering Number Array
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * @param cover The Cover
	 * 
	 * @return The Sample Supremum Covering Number Array
	 */

	public double[] sampleSupremumCoveringNumber (
		final GeneralizedValidatedVector generalizedValidatedVector,
		final double cover)
	{
		if (!NumberUtil.IsValid (cover) || 0. >= cover) {
			return null;
		}

		double[] sampleSupremumNormArray = sampleSupremumNorm (generalizedValidatedVector);

		if (null == sampleSupremumNormArray) {
			return null;
		}

		int outputDimensionality = sampleSupremumNormArray.length;
		double[] sampleSupremumCoveringNumberArray = new double[outputDimensionality];

		if (0 == outputDimensionality) {
			return null;
		}

		double coverBallVolume = Math.pow (cover, outputMetricVectorSpace().pNorm());

		for (int i = 0; i < outputDimensionality; ++i) {
			sampleSupremumCoveringNumberArray[i] = sampleSupremumNormArray[i] / coverBallVolume;
		}

		return sampleSupremumCoveringNumberArray;
	}

	/**
	 * Retrieve the Population ESS (Essential Spectrum) Array
	 * 
	 * @return The Population ESS (Essential Spectrum) Array
	 */

	public abstract double[] populationESS();

	/**
	 * Retrieve the Population Metric Norm Array
	 * 
	 * @return The Population Metric Norm Array
	 */

	public abstract double[] populationMetricNorm();

	/**
	 * Retrieve the Population Supremum Norm Array
	 * 
	 * @return The Population Supremum Norm Array
	 */

	public double[] populationSupremumNorm()
	{
		return populationMetricNorm();
	}

	/**
	 * Retrieve the Population Covering Number Array
	 * 
	 * @param cover The Cover
	 * 
	 * @return The Population Covering Number Array
	 */

	public double[] populationCoveringNumber (
		final double cover)
	{
		if (!NumberUtil.IsValid (cover) || 0. >= cover) {
			return null;
		}

		double[] populationMetricNormArray = populationMetricNorm();

		if (null == populationMetricNormArray) {
			return null;
		}

		int outputDimensionality = populationMetricNormArray.length;
		double[] populationCoveringNumberArray = new double[outputDimensionality];

		if (0 == outputDimensionality) {
			return null;
		}

		double coverBallVolume = Math.pow (cover, outputMetricVectorSpace().pNorm());

		for (int i = 0; i < outputDimensionality; ++i) {
			populationCoveringNumberArray[i] = populationMetricNormArray[i] / coverBallVolume;
		}

		return populationCoveringNumberArray;
	}

	/**
	 * Retrieve the Population Supremum Covering Number Array
	 * 
	 * @param cover The Cover
	 * 
	 * @return The Population Supremum Covering Number Array
	 */

	public double[] populationSupremumCoveringNumber (
		final double cover)
	{
		if (!NumberUtil.IsValid (cover) || 0. >= cover) {
			return null;
		}

		double[] populationSupremumNormArray = populationSupremumNorm();

		if (null == populationSupremumNormArray) {
			return null;
		}

		int outputDimensionality = populationSupremumNormArray.length;
		double[] populationSupremumCoveringNumberArray = new double[outputDimensionality];

		if (0 == outputDimensionality) {
			return null;
		}

		double coverBallVolume = Math.pow (cover, outputMetricVectorSpace().pNorm());

		for (int i = 0; i < outputDimensionality; ++i) {
			populationSupremumCoveringNumberArray[i] = populationSupremumNormArray[i] / coverBallVolume;
		}

		return populationSupremumCoveringNumberArray;
	}
}
