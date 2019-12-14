
package org.drip.measure.continuous;

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
 * <i>RdR1</i> implements the Base Abstract Class behind R<sup>d</sup> X R<sup>1</sup> Distributions. It
 * exports Methods for incremental, cumulative, and inverse cumulative Distribution Densities.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/README.md">R<sup>d</sup> Continuous/Discrete Probability Measures</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/measure/continuous/README.md">R<sup>1</sup> and R<sup>d</sup> Continuous Random Measure</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class RdR1 {

	/**
	 * Compute the Cumulative under the Distribution to the given Variate Array/Variate Combination
	 * 
	 * @param adblX R^d The Variate Array to which the Cumulative is to be computed
	 * @param dblY R^1 The Variate to which the Cumulative is to be computed
	 * 
	 * @return The Cumulative under the Distribution to the given Variate Array/Variate Combination
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public abstract double cumulative (
		final double[] adblX,
		final double dblY)
		throws java.lang.Exception;

	/**
	 * Compute the Incremental under the Distribution between the Variate Array/Variate Pair
	 * 
	 * @param adblXLeft Left R^d Variate Array from which the Cumulative is to be computed
	 * @param dblYLeft Left R^1 Variate from which the Cumulative is to be computed
	 * @param adblXRight Right R^d Variate Array to which the Cumulative is to be computed
	 * @param dblYRight Right R^1 Variate to which the Cumulative is to be computed
	 * 
	 * @return The Incremental under the Distribution between the Variate Array/Variate Pair
	 * 
	 * @throws java.lang.Exception Thrown if the inputs are invalid
	 */

	public abstract double incremental (
		final double[] adblXLeft,
		final double dblYLeft,
		final double[] adblXRight,
		final double dblYRight)
		throws java.lang.Exception;

	/**
	 * Compute the Density under the Distribution at the given Variate Array/Variate
	 * 
	 * @param adblX R^d The Variate Array to which the Cumulative is to be computed
	 * @param dblY R^1 The Variate to which the Cumulative is to be computed
	 * 
	 * @return The Density under the Distribution at the given Variate Array/Variate
	 * 
	 * @throws java.lang.Exception Thrown if the Input is Invalid
	 */

	public abstract double density (
		final double[] adblX,
		final double dblY)
		throws java.lang.Exception;
}
