
package org.drip.measure.bayesian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
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
 * <i>JointPosteriorMetrics</i> holds the Inputs and the Results of a Bayesian Computation Execution.
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

public class JointPosteriorMetrics {
	private org.drip.measure.continuous.R1Multivariate _r1mJoint = null;
	private org.drip.measure.continuous.R1Multivariate _r1mPrior = null;
	private org.drip.measure.continuous.R1Multivariate _r1mPosterior = null;
	private org.drip.measure.continuous.R1Multivariate _r1mConditional = null;
	private org.drip.measure.continuous.R1Multivariate _r1mUnconditional = null;

	/**
	 * JointPosteriorMetrics Constructor
	 * 
	 * @param r1mPrior The R^1 Multivariate Prior Distribution (Input)
	 * @param r1mUnconditional The R^1 Multivariate Unconditional Distribution (Input)
	 * @param r1mConditional The R^1 Multivariate Conditional Distribution (Input)
	 * @param r1mJoint The R^1 Multivariate Joint Distribution (Output)
	 * @param r1mPosterior The R^1 Multivariate Posterior Distribution (Output)
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public JointPosteriorMetrics (
		final org.drip.measure.continuous.R1Multivariate r1mPrior,
		final org.drip.measure.continuous.R1Multivariate r1mUnconditional,
		final org.drip.measure.continuous.R1Multivariate r1mConditional,
		final org.drip.measure.continuous.R1Multivariate r1mJoint,
		final org.drip.measure.continuous.R1Multivariate r1mPosterior)
		throws java.lang.Exception
	{
		if (null == (_r1mPrior = r1mPrior) || null == (_r1mUnconditional = r1mUnconditional) || null ==
			(_r1mConditional= r1mConditional) || null == (_r1mJoint= r1mJoint) || null == (_r1mPosterior =
				r1mPosterior))
			throw new java.lang.Exception ("JointPosteriorMetrics Constructor => Invalid Inputs!");
	}

	/**
	 * Retrieve the Prior Distribution
	 * 
	 * @return The Prior Distribution
	 */

	public org.drip.measure.continuous.R1Multivariate prior()
	{
		return _r1mPrior;
	}

	/**
	 * Retrieve the Unconditional Distribution
	 * 
	 * @return The Unconditional Distribution
	 */

	public org.drip.measure.continuous.R1Multivariate unconditional()
	{
		return _r1mUnconditional;
	}

	/**
	 * Retrieve the Conditional Distribution
	 * 
	 * @return The Conditional Distribution
	 */

	public org.drip.measure.continuous.R1Multivariate conditional()
	{
		return _r1mConditional;
	}

	/**
	 * Retrieve the Joint Distribution
	 * 
	 * @return The Joint Distribution
	 */

	public org.drip.measure.continuous.R1Multivariate joint()
	{
		return _r1mJoint;
	}

	/**
	 * Retrieve the Posterior Distribution
	 * 
	 * @return The Posterior Distribution
	 */

	public org.drip.measure.continuous.R1Multivariate posterior()
	{
		return _r1mPosterior;
	}
}
