
package org.drip.measure.bayesian;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
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
 * <br><br>
 * 	<ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure">Measure</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/bayesian">Bayesian</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income Analytics</a></li>
 *  </ul>
 * <br><br>
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
