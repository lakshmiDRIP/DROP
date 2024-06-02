
package org.drip.spaces.rxtord;

import org.drip.function.definition.R1ToRd;
import org.drip.measure.continuous.R1Univariate;
import org.drip.spaces.metric.R1Combinatorial;
import org.drip.spaces.metric.RdContinuousBanach;

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
 * <i>NormedR1CombinatorialToRdContinuous</i> implements the f : Validated Normed R<sup>1</sup> Combinatorial
 * to Validated Normed R<sup>d</sup> Continuous Function Spaces. The Reference we've used is:
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
 * 		<li><i>NormedR1CombinatorialToRdContinuous</i> Constructor</li>
 * 		<li>Retrieve the Population Metric Norm Array</li>
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

public class NormedR1CombinatorialToRdContinuous
	extends NormedR1ToNormedRd
{

	/**
	 * <i>NormedR1CombinatorialToRdContinuous</i> Function Space Constructor
	 * 
	 * @param r1CombinatorialInput The Combinatorial R<sup>1</sup> Input Metric Vector Space
	 * @param rdContinuousBanachOutput The Continuous R<sup>d</sup> Banach Output Metric Vector Space
	 * @param r1ToRdFunction R<sup>1</sup> to R<sup>d</sup> Function
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public NormedR1CombinatorialToRdContinuous (
		final R1Combinatorial r1CombinatorialInput,
		final RdContinuousBanach rdContinuousBanachOutput,
		final R1ToRd r1ToRdFunction)
		throws Exception
	{
		super (r1CombinatorialInput, rdContinuousBanachOutput, r1ToRdFunction);
	}

	/**
	 * Retrieve the Population Metric Norm Array
	 * 
	 * @return The Population Metric Norm Array
	 */

	@Override public double[] populationMetricNorm()
	{
		int pNorm = outputMetricVectorSpace().pNorm();

		if (Integer.MAX_VALUE == pNorm) {
			return populationSupremumNorm();
		}

		R1Combinatorial r1CombinatorialInput = (R1Combinatorial) inputMetricVectorSpace();

		R1Univariate r1UnivariateDistribution = r1CombinatorialInput.borelSigmaMeasure();

		R1ToRd r1ToRdFunction = function();

		if (null == r1UnivariateDistribution || null == r1ToRdFunction) {
			return null;
		}

		double[] populationMetricNormArray = null;
		double probabilityDensity = Double.NaN;
		int outputDimension = -1;
		double normalizer = 0.;

		for (double element : r1CombinatorialInput.elementSpace()) {
			try {
				probabilityDensity = r1UnivariateDistribution.density (element);
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}

			double[] r1ToRdFunctionValueArray = r1ToRdFunction.evaluate (element);

			if (null == r1ToRdFunctionValueArray || 0 == (outputDimension = r1ToRdFunctionValueArray.length))
			{
				return null;
			}

			normalizer += probabilityDensity;

			if (null == populationMetricNormArray) {
				populationMetricNormArray = new double[outputDimension];

				for (int i = 0; i < outputDimension; ++i) {
					populationMetricNormArray[i] = 0.;
				}
			}

			for (int i = 0; i < outputDimension; ++i) {
				populationMetricNormArray[i] += probabilityDensity *
					Math.pow (Math.abs (r1ToRdFunctionValueArray[i]), pNorm);
			}
		}

		for (int i = 0; i < outputDimension; ++i) {
			populationMetricNormArray[i] += Math.pow (populationMetricNormArray[i] / normalizer, 1. / pNorm);
		}

		return populationMetricNormArray;
	}
}
