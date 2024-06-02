
package org.drip.spaces.rxtord;

import org.drip.function.definition.RdToRd;
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

public abstract class NormedRdToNormedRd
	extends NormedRxToNormedRd
{
	private RdToRd _rdToRdFunction = null;
	private RdNormed _rdNormedInput = null;
	private RdNormed _rdNormedOutput = null;

	protected NormedRdToNormedRd (
		final org.drip.spaces.metric.RdNormed rdInput,
		final org.drip.spaces.metric.RdNormed rdOutput,
		final org.drip.function.definition.RdToRd funcRdToRd)
		throws java.lang.Exception
	{
		if (null == (_rdNormedInput = rdInput) || null == (_rdNormedOutput = rdOutput))
			throw new java.lang.Exception ("NormedRdToNormedRd ctr: Invalid Inputs");

		_rdToRdFunction = funcRdToRd;
	}

	/**
	 * Retrieve the Underlying RdToRd Function
	 * 
	 * @return The Underlying RdToRd Function
	 */

	public RdToRd function()
	{
		return _rdToRdFunction;
	}

	/**
	 * Retrieve the Population R^d ESS (Essential Spectrum) Array
	 * 
	 * @return The Population R^d ESS (Essential Spectrum) Array
	 */

	public double[] populationRdESS()
	{
		return _rdToRdFunction.evaluate (_rdNormedInput.populationMode());
	}

	/**
	 * Retrieve the Population R^d Supremum Norm
	 * 
	 * @return The Population R^d Supremum Norm
	 */

	public double[] populationRdSupremumNorm()
	{
		return populationRdESS();
	}

	@Override public org.drip.spaces.metric.RdNormed inputMetricVectorSpace()
	{
		return _rdNormedInput;
	}

	@Override public org.drip.spaces.metric.RdNormed outputMetricVectorSpace()
	{
		return _rdNormedOutput;
	}

	@Override public double[] sampleSupremumNorm (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
	{
		if (null == _rdToRdFunction || null == gvvi || !gvvi.tensorSpaceType().match (_rdNormedInput) || ! (gvvi
			instanceof org.drip.spaces.instance.ValidatedRd))
			return null;

		org.drip.spaces.instance.ValidatedRd vrdInstance = (org.drip.spaces.instance.ValidatedRd) gvvi;

		double[][] aadblInstance = vrdInstance.instance();

		int iNumSample = aadblInstance.length;

		int iOutputDimension = _rdNormedOutput.dimension();

		double[] adblSupremumNorm = _rdToRdFunction.evaluate (aadblInstance[0]);

		if (null == adblSupremumNorm || iOutputDimension != adblSupremumNorm.length ||
			!org.drip.numerical.common.NumberUtil.IsValid (adblSupremumNorm))
			return null;

		for (int i = 0; i < iOutputDimension; ++i)
			adblSupremumNorm[i] = java.lang.Math.abs (adblSupremumNorm[i]);

		for (int i = 1; i < iNumSample; ++i) {
			double[] adblSampleNorm = _rdToRdFunction.evaluate (aadblInstance[i]);

			if (null == adblSampleNorm || iOutputDimension != adblSampleNorm.length) return null;

			for (int j = 0; j < iOutputDimension; ++j) {
				if (!org.drip.numerical.common.NumberUtil.IsValid (adblSampleNorm[j])) return null;

				if (adblSampleNorm[j] > adblSupremumNorm[j]) adblSupremumNorm[j] = adblSampleNorm[j];
			}
		}

		return adblSupremumNorm;
	}

	@Override public double[] sampleMetricNorm (
		final org.drip.spaces.instance.GeneralizedValidatedVector gvvi)
	{
		int iPNorm = outputMetricVectorSpace().pNorm();

		if (java.lang.Integer.MAX_VALUE == iPNorm) return sampleSupremumNorm (gvvi);

		if (null == _rdToRdFunction || null == gvvi || !gvvi.tensorSpaceType().match (_rdNormedInput) || ! (gvvi
			instanceof org.drip.spaces.instance.ValidatedRd))
			return null;

		int iOutputDimension = _rdNormedOutput.dimension();

		double[][] aadblInstance = ((org.drip.spaces.instance.ValidatedRd) gvvi).instance();

		double[] adblMetricNorm = new double[iOutputDimension];
		int iNumSample = aadblInstance.length;

		for (int i = 0; i < iNumSample; ++i)
			adblMetricNorm[i] = 0.;

		for (int i = 0; i < iNumSample; ++i) {
			double[] adblPointValue = _rdToRdFunction.evaluate (aadblInstance[i]);

			if (null == adblPointValue || iOutputDimension != adblPointValue.length) return null;

			for (int j = 0; j < iOutputDimension; ++j) {
				if (!org.drip.numerical.common.NumberUtil.IsValid (adblPointValue[j])) return null;

				adblMetricNorm[j] += java.lang.Math.pow (java.lang.Math.abs (adblPointValue[j]), iPNorm);
			}
		}

		for (int i = 0; i < iNumSample; ++i)
			adblMetricNorm[i] = java.lang.Math.pow (adblMetricNorm[i], 1. / iPNorm);

		return adblMetricNorm;
	}

	@Override public double[] populationESS()
	{
		return _rdToRdFunction.evaluate (_rdNormedInput.populationMode());
	}

	@Override public double[] populationSupremumNorm()
	{
		return populationESS();
	}
}
