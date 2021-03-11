
package org.drip.measure.bayesian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * 	Univariate Convolution Execution.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian/README.md">Prior, Conditional, Posterior Theil Bayesian</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1UnivariateConvolutionMetrics
{
	private org.drip.measure.continuous.R1Univariate _joint = null;
	private org.drip.measure.continuous.R1Univariate _prior = null;
	private org.drip.measure.continuous.R1Univariate _posterior = null;
	private org.drip.measure.continuous.R1Univariate _conditional = null;
	private org.drip.measure.continuous.R1Univariate _unconditional = null;

	/**
	 * R1UnivariateConvolutionMetrics Constructor
	 * 
	 * @param prior The R<sup>1</sup> Univariate Prior Distribution (Input)
	 * @param unconditional The R<sup>1</sup> Univariate Unconditional Distribution (Input)
	 * @param conditional The R<sup>1</sup> Univariate Conditional Distribution (Input)
	 * @param joint The R<sup>1</sup> Univariate Joint Distribution (Output)
	 * @param posterior The R<sup>1</sup> Univariate Posterior Distribution (Output)
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1UnivariateConvolutionMetrics (
		final org.drip.measure.continuous.R1Univariate prior,
		final org.drip.measure.continuous.R1Univariate unconditional,
		final org.drip.measure.continuous.R1Univariate conditional,
		final org.drip.measure.continuous.R1Univariate joint,
		final org.drip.measure.continuous.R1Univariate posterior)
		throws java.lang.Exception
	{
		if (null == (_prior = prior) ||
			null == (_unconditional = unconditional) ||
			null == (_conditional = conditional) ||
			null == (_joint = joint) ||
			null == (_posterior = posterior)
		)
		{
			throw new java.lang.Exception (
				"R1UnivariateConvolutionMetrics Constructor => Invalid Inputs!"
			);
		}
	}

	/**
	 * Retrieve the R<sup>1</sup> Univariate Prior Distribution
	 * 
	 * @return The R<sup>1</sup> Univariate Prior Distribution
	 */

	public org.drip.measure.continuous.R1Univariate prior()
	{
		return _prior;
	}

	/**
	 * Retrieve the R<sup>1</sup> Univariate Unconditional Distribution
	 * 
	 * @return The R<sup>1</sup> Univariate Unconditional Distribution
	 */

	public org.drip.measure.continuous.R1Univariate unconditional()
	{
		return _unconditional;
	}

	/**
	 * Retrieve the R<sup>1</sup> Univariate Conditional Distribution
	 * 
	 * @return The R<sup>1</sup> Univariate Conditional Distribution
	 */

	public org.drip.measure.continuous.R1Univariate conditional()
	{
		return _conditional;
	}

	/**
	 * Retrieve the R<sup>1</sup> Univariate Joint Distribution
	 * 
	 * @return The R<sup>1</sup> Univariate Joint Distribution
	 */

	public org.drip.measure.continuous.R1Univariate joint()
	{
		return _joint;
	}

	/**
	 * Retrieve the R<sup>1</sup> Univariate Posterior Distribution
	 * 
	 * @return The R<sup>1</sup> Univariate Posterior Distribution
	 */

	public org.drip.measure.continuous.R1Univariate posterior()
	{
		return _posterior;
	}
}
