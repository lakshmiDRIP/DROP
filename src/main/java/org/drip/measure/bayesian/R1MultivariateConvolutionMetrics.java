
package org.drip.measure.bayesian;

import org.drip.measure.state.LabelledRdContinuousDistribution;

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
 * <i>R1MultivariateConvolutionMetrics</i> holds the Inputs and the Results of a Bayesian Multivariate
 * 	Convolution Execution. It provides the following Functionality:
 *
 *  <ul>
 * 		<li><i>R1MultivariateConvolutionMetrics</i> Constructor</li>
 * 		<li>Retrieve the Prior Distribution</li>
 * 		<li>Retrieve the Unconditional Distribution</li>
 * 		<li>Retrieve the Conditional Distribution</li>
 * 		<li>Retrieve the Joint Distribution</li>
 * 		<li>Retrieve the Posterior Distribution</li>
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

public class R1MultivariateConvolutionMetrics
{
	private LabelledRdContinuousDistribution _jointDistribution = null;
	private LabelledRdContinuousDistribution _priorDistribution = null;
	private LabelledRdContinuousDistribution _posteriorDistribution = null;
	private LabelledRdContinuousDistribution _conditionalDistribution = null;
	private LabelledRdContinuousDistribution _unconditionalDistribution = null;

	/**
	 * <i>R1MultivariateConvolutionMetrics</i> Constructor
	 * 
	 * @param priorDistribution The R<sup>1</sup> Multivariate Prior Distribution (Input)
	 * @param unconditionalDistribution The R<sup>1</sup> Multivariate Unconditional Distribution (Input)
	 * @param conditionalDistribution The R<sup>1</sup> Multivariate Conditional Distribution (Input)
	 * @param jointDistribution The R<sup>1</sup> Multivariate Joint Distribution (Output)
	 * @param posteriorDistribution The R<sup>1</sup> Multivariate Posterior Distribution (Output)
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public R1MultivariateConvolutionMetrics (
		final LabelledRdContinuousDistribution priorDistribution,
		final LabelledRdContinuousDistribution unconditionalDistribution,
		final LabelledRdContinuousDistribution conditionalDistribution,
		final LabelledRdContinuousDistribution jointDistribution,
		final LabelledRdContinuousDistribution posteriorDistribution)
		throws Exception
	{
		if (null == (_priorDistribution = priorDistribution) ||
			null == (_unconditionalDistribution = unconditionalDistribution) ||
			null == (_conditionalDistribution = conditionalDistribution) ||
			null == (_jointDistribution = jointDistribution) ||
			null == (_posteriorDistribution = posteriorDistribution))
		{
			throw new Exception ("R1MultivariateConvolutionMetrics Constructor => Invalid Inputs!");
		}
	}

	/**
	 * Retrieve the Prior Distribution
	 * 
	 * @return The Prior Distribution
	 */

	public LabelledRdContinuousDistribution priorDistribution()
	{
		return _priorDistribution;
	}

	/**
	 * Retrieve the Unconditional Distribution
	 * 
	 * @return The Unconditional Distribution
	 */

	public LabelledRdContinuousDistribution unconditionalDistribution()
	{
		return _unconditionalDistribution;
	}

	/**
	 * Retrieve the Conditional Distribution
	 * 
	 * @return The Conditional Distribution
	 */

	public LabelledRdContinuousDistribution conditionalDistribution()
	{
		return _conditionalDistribution;
	}

	/**
	 * Retrieve the Joint Distribution
	 * 
	 * @return The Joint Distribution
	 */

	public LabelledRdContinuousDistribution jointDistribution()
	{
		return _jointDistribution;
	}

	/**
	 * Retrieve the Posterior Distribution
	 * 
	 * @return The Posterior Distribution
	 */

	public LabelledRdContinuousDistribution posteriorDistribution()
	{
		return _posteriorDistribution;
	}
}
