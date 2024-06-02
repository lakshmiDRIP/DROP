
package org.drip.spaces.rxtor1;

import org.drip.numerical.common.NumberUtil;
import org.drip.spaces.instance.GeneralizedValidatedVector;
import org.drip.spaces.metric.GeneralizedMetricVectorSpace;
import org.drip.spaces.metric.R1Normed;

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
 * <i>NormedRxToNormedR1</i> is the Abstract Class that exposes f : Normed R<sup>x</sup> (x .gte. 1) To
 * 	Normed R<sup>1</sup> Function Space. The Reference we've used is:
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
 * 		<li>Retrieve the Sample Supremum Norm</li>
 * 		<li>Retrieve the Sample Metric Norm</li>
 * 		<li>Retrieve the Sample Covering Number</li>
 * 		<li>Retrieve the Sample Supremum Covering Number</li>
 * 		<li>Retrieve the Population ESS (Essential Spectrum)</li>
 * 		<li>Retrieve the Population Metric Norm</li>
 * 		<li>Retrieve the Population Supremum Metric Norm</li>
 * 		<li>Retrieve the Population Covering Number</li>
 * 		<li>Retrieve the Population Supremum Covering Number</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/rxtor1/README.md">R<sup>x</sup> To R<sup>1</sup> Normed Function Spaces</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class NormedRxToNormedR1
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

	public abstract R1Normed outputMetricVectorSpace();

	/**
	 * Retrieve the Sample Supremum Norm
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * 
	 * @return The Sample Supremum Norm
	 * 
	 * @throws Exception Thrown if the Supremum Norm cannot be computed
	 */

	public abstract double sampleSupremumNorm (
		final GeneralizedValidatedVector generalizedValidatedVector)
		throws Exception;

	/**
	 * Retrieve the Sample Metric Norm
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * 
	 * @return The Sample Metric Norm
	 * 
	 * @throws Exception Thrown if the Sample Metric Norm cannot be computed
	 */

	public abstract double sampleMetricNorm (
		final GeneralizedValidatedVector generalizedValidatedVector)
		throws Exception;

	/**
	 * Retrieve the Sample Covering Number
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * @param cover The Cover
	 * 
	 * @return The Sample Covering Number
	 * 
	 * @throws Exception Thrown if the Sample Covering Number cannot be computed
	 */

	public double sampleCoveringNumber (
		final GeneralizedValidatedVector generalizedValidatedVector,
		final double cover)
		throws Exception
	{
		if (!NumberUtil.IsValid (cover) || 0. >= cover) {
			throw new Exception ("NormedRxToNormedR1::sampleCoveringNumber => Invalid Inputs");
		}

		return sampleMetricNorm (generalizedValidatedVector) / cover;
	}

	/**
	 * Retrieve the Sample Supremum Covering Number
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * @param cover The Cover
	 * 
	 * @return The Sample Supremum Covering Number
	 * 
	 * @throws Exception Thrown if the Sample Covering Number cannot be computed
	 */

	public double sampleSupremumCoveringNumber (
		final GeneralizedValidatedVector generalizedValidatedVector,
		final double cover)
		throws Exception
	{
		if (!NumberUtil.IsValid (cover) || 0. >= cover) {
			throw new Exception ("NormedRxToNormedR1::sampleSupremumCoveringNumber => Invalid Inputs");
		}

		return sampleSupremumNorm (generalizedValidatedVector) / cover;
	}

	/**
	 * Retrieve the Population ESS (Essential Spectrum)
	 * 
	 * @return The Population ESS (Essential Spectrum)
	 * 
	 * @throws Exception Thrown if the Population ESS (Essential Spectrum) cannot be computed
	 */

	public abstract double populationESS()
		throws Exception;

	/**
	 * Retrieve the Population Metric Norm
	 * 
	 * @return The Population Metric Norm
	 * 
	 * @throws Exception Thrown if the Population Metric Norm cannot be computed
	 */

	public abstract double populationMetricNorm()
		throws Exception;

	/**
	 * Retrieve the Population Supremum Metric Norm
	 * 
	 * @return The Population Supremum Metric Norm
	 * 
	 * @throws Exception Thrown if the Population Supremum Metric Norm cannot be computed
	 */

	public double populationSupremumMetricNorm()
		throws Exception
	{
		return populationESS();
	}

	/**
	 * Retrieve the Population Covering Number
	 * 
	 * @param cover The Cover
	 * 
	 * @return The Population Covering Number
	 * 
	 * @throws Exception Thrown if the Population Covering Number cannot be computed
	 */

	public double populationCoveringNumber (
		final double cover)
		throws Exception
	{
		if (!NumberUtil.IsValid (cover) || 0. >= cover) {
			throw new Exception ("NormedRxToNormedR1::populationCoveringNumber => Invalid Inputs");
		}

		return populationMetricNorm() / cover;
	}

	/**
	 * Retrieve the Population Supremum Covering Number
	 * 
	 * @param cover The Cover
	 * 
	 * @return The Population Supremum Covering Number
	 * 
	 * @throws Exception Thrown if the Population Supremum Covering Number cannot be computed
	 */

	public double populationSupremumCoveringNumber (
		final double cover)
		throws Exception
	{
		if (!NumberUtil.IsValid (cover) || 0. >= cover) {
			throw new Exception ("NormedRxToNormedR1::populationSupremumCoveringNumber => Invalid Inputs");
		}

		return populationSupremumMetricNorm() / cover;
	}
}
