
package org.drip.spaces.metric;

import org.drip.function.definition.RdToR1;
import org.drip.measure.continuous.RdDistribution;
import org.drip.spaces.tensor.RdGeneralizedVector;

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
 * <i>RdNormed</i> Abstract Class implements the Normed, Bounded/Unbounded Continuous/Combinatorial
 * 	l<sub>p</sub> R<sup>d</sup> Spaces.
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
 * 		<li>Retrieve the Borel Sigma R<sup>d</sup> Probability Measure</li>
 * 		<li>Compute the Supremum Norm of the Sample</li>
 * 		<li>Compute the Metric Norm of the Sample</li>
 * 		<li>Retrieve the Population Mode</li>
 * 		<li>Compute the Population Supremum Norm of the Sample</li>
 * 		<li>Compute the Borel Measure Expectation for the specified R<sup>d</sup> To R<sup>1</sup> Function over the full Input Space</li>
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

public interface RdNormed extends GeneralizedMetricVectorSpace, RdGeneralizedVector
{

	/**
	 * Retrieve the Borel Sigma R<sup>d</sup> Probability Measure
	 * 
	 * @return The Borel Sigma R<sup>d</sup> Probability Measure
	 */

	public abstract RdDistribution borelSigmaMeasure();

	/**
	 * Compute the Supremum Norm of the Sample
	 * 
	 * @param x The Sample
	 * 
	 * @return The Supremum Norm of the Sample
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public abstract double sampleSupremumNorm (
		final double[] x
	) throws Exception;

	/**
	 * Compute the Metric Norm of the Sample
	 * 
	 * @param x The Sample
	 * 
	 * @return The Metric Norm of the Sample
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public abstract double sampleMetricNorm (
		final double[] x
	) throws Exception;

	/**
	 * Retrieve the Population Mode
	 * 
	 * @return The Population Mode
	 */

	public abstract double[] populationMode();

	/**
	 * Compute the Population Supremum Norm of the Sample
	 * 
	 * @return The Population Supremum Norm of the Sample
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public abstract double populationSupremumNorm()
		throws Exception;

	/**
	 * Compute the Borel Measure Expectation for the specified R<sup>d</sup> To R<sup>1</sup> Function over
	 * 	 the full Input Space
	 * 
	 * @param rdToR1Function R<sup>d</sup> To R<sup>1</sup> Function
	 * 
	 * @return The Borel Measure Expectation for the specified R<sup>d</sup> To R<sup>1</sup> Function over
	 * 	 the full Input Space
	 * 
	 * @throws Exception Thrown if the Population Mode cannot be calculated
	 */

	public double borelMeasureSpaceExpectation (
		final RdToR1 rdToR1Function
	) throws Exception;
}
