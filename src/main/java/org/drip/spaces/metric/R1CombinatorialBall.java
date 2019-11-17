
package org.drip.spaces.metric;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 * <i>R1CombinatorialBall</i> extends the Combinatorial R<sup>1</sup> Banach Space by enforcing the Closed
 * Bounded Metric. The Reference we've used is:
 *
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Carl, B., and I. Stephani (1990): <i>Entropy, Compactness, and the Approximation of Operators</i>
 *  			<b>Cambridge University Press</b> Cambridge UK 
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/StatisticalLearningLibrary.md">Statistical Learning Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/README.md">R<sup>1</sup> and R<sup>d</sup> Vector/Tensor Spaces (Validated and/or Normed), and Function Classes</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/spaces/metric/README.md">Hilbert/Banach Normed Metric Spaces</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1CombinatorialBall extends org.drip.spaces.metric.R1Combinatorial {
	private double _dblNormRadius = java.lang.Double.NaN;

	/**
	 * Construct a R1CombinatorialBall Instance of Unit Radius
	 * 
	 * @param lsElementSpace The List Space of Elements
	 * @param distR1 The R^1 Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * 
	 * @return ContinuousRealUnidimensionalBall Instance of Unit Radius
	 */

	public static final R1CombinatorialBall ClosedUnit (
		final java.util.List<java.lang.Double> lsElementSpace,
		final org.drip.measure.continuous.R1Univariate distR1,
		final int iPNorm)
	{
		try {
			return new R1CombinatorialBall (lsElementSpace, distR1, iPNorm, 1.);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * R1CombinatorialBall Constructor
	 * 
	 * @param lsElementSpace The List Space of Elements
	 * @param distR1 The R^1 Borel Sigma Measure
	 * @param iPNorm The p-norm of the Space
	 * @param dblNormRadius Radius Norm of the Unit Ball
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public R1CombinatorialBall (
		final java.util.List<java.lang.Double> lsElementSpace,
		final org.drip.measure.continuous.R1Univariate distR1,
		final int iPNorm,
		final double dblNormRadius)
		throws java.lang.Exception
	{
		super (lsElementSpace, distR1, iPNorm);

		if (!org.drip.numerical.common.NumberUtil.IsValid (_dblNormRadius = dblNormRadius) || 0. >=
			_dblNormRadius)
			throw new java.lang.Exception ("R1CombinatorialBall Constructor: Invalid Inputs");
	}

	/**
	 * Retrieve the Radius Norm
	 * 
	 * @return The Radius Norm
	 */

	public double normRadius()
	{
		return _dblNormRadius;
	}

	@Override public boolean validateInstance (
		final double dblInstance)
	{
		try {
			return super.validateInstance (dblInstance) && _dblNormRadius <= sampleMetricNorm (dblInstance);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
