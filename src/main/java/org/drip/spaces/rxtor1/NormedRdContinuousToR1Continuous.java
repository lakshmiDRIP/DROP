
package org.drip.spaces.rxtor1;

import org.drip.function.definition.RdToR1;
import org.drip.measure.continuous.Rd;
import org.drip.spaces.metric.R1Continuous;
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
 * <i>NormedRdContinuousToR1Continuous</i> implements the f : Validated Normed R<sup>d</sup> Continuous To
 * 	Validated Normed R<sup>1</sup> Continuous Function Spaces. The Reference we've used is:
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
 * 		<li><i>NormedRdContinuousToR1Continuous</i> Function Space Constructor</li>
 * 		<li>Retrieve the Population Metric Norm</li>
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

public class NormedRdContinuousToR1Continuous extends NormedRdToNormedR1
{

	/**
	 * <i>NormedRdContinuousToR1Continuous</i> Function Space Constructor
	 * 
	 * @param rdContinuousBanachInput The Continuous R<sup>d</sup> Input Banach Metric Vector Space
	 * @param r1ContinuousOutput The Continuous R<sup>1</sup> Output Metric Vector Space
	 * @param rdToR1Function The R<sup>d</sup> To R<sup>1</sup> Function
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public NormedRdContinuousToR1Continuous (
		final RdContinuousBanach rdContinuousBanachInput,
		final R1Continuous r1ContinuousOutput,
		final RdToR1 rdToR1Function)
		throws Exception
	{
		super (rdContinuousBanachInput, r1ContinuousOutput, rdToR1Function);
	}

	/**
	 * Retrieve the Population Metric Norm
	 * 
	 * @return The Population Metric Norm
	 * 
	 * @throws Exception Thrown if the Population Metric Norm cannot be computed
	 */

	@Override public double populationMetricNorm()
		throws Exception
	{
		final int pNorm = outputMetricVectorSpace().pNorm();

		if (Integer.MAX_VALUE == pNorm) {
			return populationSupremumMetricNorm();
		}

		RdContinuousBanach rdContinuousBanachInput = (RdContinuousBanach) inputMetricVectorSpace();

		final Rd rdContinuousDistribution = rdContinuousBanachInput.borelSigmaMeasure();

		final RdToR1 rdToR1Function = function();

		if (null == rdContinuousDistribution || null == rdToR1Function) {
			throw new Exception (
				"NormedRdContinuousToR1Continuous::populationMetricNorm => Measure/Function not specified"
			);
		}

		RdToR1 rdToR1MetricFunction = new RdToR1 (null) {
			@Override public int dimension()
			{
				return RdToR1.DIMENSION_NOT_FIXED;
			}

			@Override public double evaluate (
				final double[] xArray)
				throws Exception
			{
				return Math.pow (Math.abs (rdToR1Function.evaluate (xArray)), pNorm) *
					rdContinuousDistribution.density (xArray);
			}
		};

		double[] leftDimensionEdgeArray = rdContinuousBanachInput.leftDimensionEdge();

		double[] rightDimensionEdgeArray = rdContinuousBanachInput.rightDimensionEdge();

		return Math.pow (
			rdToR1MetricFunction.integrate (leftDimensionEdgeArray, rightDimensionEdgeArray) /
				rdContinuousDistribution.incremental (leftDimensionEdgeArray, rightDimensionEdgeArray),
			1. / pNorm
		);
	}
}
