
package org.drip.measure.bayesian;

import org.drip.measure.continuous.R1Distribution;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2030 Lakshmi Krishnamurthy
 * Copyright (C) 2029 Lakshmi Krishnamurthy
 * Copyright (C) 2028 Lakshmi Krishnamurthy
 * Copyright (C) 2027 Lakshmi Krishnamurthy
 * Copyright (C) 2026 Lakshmi Krishnamurthy
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
 * <i>R1UnivariateConvolutionMetrics</i> holds the Inputs and the Results of a Bayesian R<sup>1</sup>
 * 	Univariate Convolution Execution. It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>R1UnivariateConvolutionMetrics</i> Constructor</li>
 * 		<li>Retrieve the R<sup>1</sup> Univariate Prior Distribution</li>
 * 		<li>Retrieve the R<sup>1</sup> Univariate Unconditional Distribution</li>
 * 		<li>Retrieve the R<sup>1</sup> Univariate Conditional Distribution</li>
 * 		<li>Retrieve the R<sup>1</sup> Univariate Joint Distribution</li>
 * 		<li>Retrieve the R<sup>1</sup> Univariate Posterior Distribution</li>
 *  </ul>
 *
 *	<br>
 *  <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
 *		<tr><td><b>Module </b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></td></tr>
 *		<tr><td><b>Library</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></td></tr>
 *		<tr><td><b>Project</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></td></tr>
 *		<tr><td><b>Package</b></td> <td><a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/README.md">Prior, Conditional, Posterior Theil Bayesian</a></td></tr>
 *  </table>
 *	<br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1UnivariateConvolutionMetrics
{
	private R1Distribution _jointDistribution = null;
	private R1Distribution _priorDistribution = null;
	private R1Distribution _posteriorDistribution = null;
	private R1Distribution _conditionalDistribution = null;
	private R1Distribution _unconditionalDistribution = null;

	/**
	 * <i>R1UnivariateConvolutionMetrics</i> Constructor
	 * 
	 * @param priorDistribution The R<sup>1</sup> Univariate Prior Distribution (Input)
	 * @param unconditionalDistribution The R<sup>1</sup> Univariate Unconditional Distribution (Input)
	 * @param conditionalDistribution The R<sup>1</sup> Univariate Conditional Distribution (Input)
	 * @param jointDistribution The R<sup>1</sup> Univariate Joint Distribution (Output)
	 * @param posteriorDistribution The R<sup>1</sup> Univariate Posterior Distribution (Output)
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public R1UnivariateConvolutionMetrics (
		final R1Distribution priorDistribution,
		final R1Distribution unconditionalDistribution,
		final R1Distribution conditionalDistribution,
		final R1Distribution jointDistribution,
		final R1Distribution posteriorDistribution)
		throws Exception
	{
		if (null == (_priorDistribution = priorDistribution) ||
			null == (_unconditionalDistribution = unconditionalDistribution) ||
			null == (_conditionalDistribution = conditionalDistribution) ||
			null == (_jointDistribution = jointDistribution) ||
			null == (_posteriorDistribution = posteriorDistribution))
		{
			throw new Exception ("R1UnivariateConvolutionMetrics Constructor => Invalid Inputs!");
		}
	}

	/**
	 * Retrieve the R<sup>1</sup> Univariate Prior Distribution
	 * 
	 * @return The R<sup>1</sup> Univariate Prior Distribution
	 */

	public R1Distribution priorDistribution()
	{
		return _priorDistribution;
	}

	/**
	 * Retrieve the R<sup>1</sup> Univariate Unconditional Distribution
	 * 
	 * @return The R<sup>1</sup> Univariate Unconditional Distribution
	 */

	public R1Distribution unconditionalDistribution()
	{
		return _unconditionalDistribution;
	}

	/**
	 * Retrieve the R<sup>1</sup> Univariate Conditional Distribution
	 * 
	 * @return The R<sup>1</sup> Univariate Conditional Distribution
	 */

	public R1Distribution conditionalDistribution()
	{
		return _conditionalDistribution;
	}

	/**
	 * Retrieve the R<sup>1</sup> Univariate Joint Distribution
	 * 
	 * @return The R<sup>1</sup> Univariate Joint Distribution
	 */

	public R1Distribution jointDistribution()
	{
		return _jointDistribution;
	}

	/**
	 * Retrieve the R<sup>1</sup> Univariate Posterior Distribution
	 * 
	 * @return The R<sup>1</sup> Univariate Posterior Distribution
	 */

	public R1Distribution posteriorDistribution()
	{
		return _posteriorDistribution;
	}
}
