
package org.drip.spaces.rxtor1;

import org.drip.function.definition.RdToR1;
import org.drip.spaces.instance.GeneralizedValidatedVector;
import org.drip.spaces.instance.ValidatedRd;
import org.drip.spaces.metric.R1Normed;
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
 * <i>NormedRdToNormedR1</i> is the Abstract Class underlying the f : Validated Normed R<sup>d</sup> To
 * 	Validated Normed R<sup>1</sup> Function Spaces. The Reference we've used is:
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
 * 		<li>Retrieve the Underlying <i>RdToR1</i> Function</li>
 * 		<li>Retrieve the Sample Supremum Norm</li>
 * 		<li>Retrieve the Sample Metric Norm</li>
 * 		<li>Retrieve the Population ESS (Essential Spectrum)</li>
 * 		<li>Retrieve the Input Metric Vector Space</li>
 * 		<li>Retrieve the Output Metric Vector Space</li>
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

public abstract class NormedRdToNormedR1 extends NormedRxToNormedR1
{
	private RdNormed _rdInput = null;
	private R1Normed _r1Output = null;
	private RdToR1 _rdToR1Function = null;

	protected NormedRdToNormedR1 (
		final RdNormed rdInput,
		final R1Normed r1Output,
		final RdToR1 rdToR1Function)
		throws Exception
	{
		if (null == (_rdInput = rdInput) || null == (_r1Output = r1Output)) {
			throw new Exception ("NormedRdToNormedR1 Constructor => Invalid Inputs");
		}

		_rdToR1Function = rdToR1Function;
	}

	/**
	 * Retrieve the Underlying <i>RdToR1</i> Function
	 * 
	 * @return The Underlying <i>RdToR1</i> Function
	 */

	public RdToR1 function()
	{
		return _rdToR1Function;
	}

	/**
	 * Retrieve the Sample Supremum Norm
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * 
	 * @return The Sample Supremum Norm
	 * 
	 * @throws Exception Thrown if the Supremum Norm cannot be computed
	 */

	@Override public double sampleSupremumNorm (
		final GeneralizedValidatedVector generalizedValidatedVector)
		throws Exception
	{
		if (null == _rdToR1Function || null == generalizedValidatedVector ||
			!generalizedValidatedVector.tensorSpaceType().match (_rdInput))
		{
			throw new Exception ("NormedRdToNormedR1::sampleSupremumNorm => Invalid Input");
		}

		double[][] rdInstanceArray = ((ValidatedRd) generalizedValidatedVector).instance();

		int sampleCount = rdInstanceArray.length;

		double supremumNorm = Math.abs (_rdToR1Function.evaluate (rdInstanceArray[0]));

		for (int i = 1; i < sampleCount; ++i) {
			double dblResponse = Math.abs (_rdToR1Function.evaluate (rdInstanceArray[i]));

			if (dblResponse > supremumNorm) {
				supremumNorm = dblResponse;
			}
		}

		return supremumNorm;
	}

	/**
	 * Retrieve the Sample Metric Norm
	 * 
	 * @param generalizedValidatedVector The Validated Vector Space Instance
	 * 
	 * @return The Sample Metric Norm
	 * 
	 * @throws Exception Thrown if the Sample Metric Norm cannot be computed
	 */

	@Override public double sampleMetricNorm (
		final GeneralizedValidatedVector generalizedValidatedVector)
		throws Exception
	{
		int pNorm = _r1Output.pNorm();

		if (Integer.MAX_VALUE == pNorm) {
			return sampleSupremumNorm (generalizedValidatedVector);
		}

		if (null == _rdToR1Function || null == generalizedValidatedVector ||
			!generalizedValidatedVector.tensorSpaceType().match (_rdInput))
		{
			throw new Exception ("NormedRdToNormedR1::sampleMetricNorm => Invalid Input");
		}

		double[][] rdInstanceArray = ((ValidatedRd) generalizedValidatedVector).instance();

		int sampleCount = rdInstanceArray.length;
		double norm = 0.;

		for (int i = 0; i < sampleCount; ++i) {
			norm += Math.pow (Math.abs (_rdToR1Function.evaluate (rdInstanceArray[i])), pNorm);
		}

		return Math.pow (norm, 1. / pNorm);
	}

	/**
	 * Retrieve the Population ESS (Essential Spectrum)
	 * 
	 * @return The Population ESS (Essential Spectrum)
	 * 
	 * @throws Exception Thrown if the Population ESS (Essential Spectrum) cannot be computed
	 */

	@Override public double populationESS()
		throws Exception
	{
		if (null == _rdToR1Function) {
			throw new Exception ("NormedRdToNormedR1::populationESS => Invalid Input");
		}

		return _rdToR1Function.evaluate (_rdInput.populationMode());
	}

	/**
	 * Retrieve the Input Metric Vector Space
	 * 
	 * @return The Input Metric Vector Space
	 */

	@Override public RdNormed inputMetricVectorSpace()
	{
		return _rdInput;
	}

	/**
	 * Retrieve the Output Metric Vector Space
	 * 
	 * @return The Output Metric Vector Space
	 */

	@Override public R1Normed outputMetricVectorSpace()
	{
		return _r1Output;
	}
}
