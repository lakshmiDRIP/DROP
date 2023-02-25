
package org.drip.function.r1tor1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>MonicPolynomial</i> implements the Multi-root R<sup>1</sup> to R<sup>1</sup> Monic Polynomial.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/README.md">R<sup>d</sup> To R<sup>d</sup> Function Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/function/r1tor1/README.md">Built-in R<sup>1</sup> To R<sup>1</sup> Functions</a></li>
 *  </ul>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class MonicPolynomial extends org.drip.function.definition.R1ToR1
{
	private double[] _rootArray = null;

	/**
	 * MonicPolynomial constructor
	 * 
	 * @param rootArray The Array of Roots
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public MonicPolynomial (
		final double[] rootArray)
		throws java.lang.Exception
	{
		super (null);

		if (null == (_rootArray = rootArray) ||
			0 == _rootArray.length ||
			!org.drip.numerical.common.NumberUtil.IsValid (_rootArray))
		{
			throw new java.lang.Exception ("MonicPolynomial Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Array of Roots
	 * 
	 * @return The Array of Roots
	 */

	public double[] rootArray()
	{
		return _rootArray;
	}

	@Override public double evaluate (
		final double x)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (x))
		{
			throw new java.lang.Exception ("MonicPolynomial::evaluate => Invalid Inputs");
		}

		return 0.;
	}

	/**
	 * Compute the Log Product over [a, a + 1, ..., b] of the Monic Polynomial
	 * 
	 * @param a a
	 * @param b b
	 * @param logGammaEstimator Log Gamma Estimator
	 * 
	 * @return The Log Product over [a, a + 1, ..., b] of the Monic Polynomial
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double logGammaProduct (
		final int a,
		final int b,
		final org.drip.function.definition.R1ToR1 logGammaEstimator)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (a) ||
			!org.drip.numerical.common.NumberUtil.IsValid (b) ||
			null == logGammaEstimator)
		{
			throw new java.lang.Exception ("MonicPolynomial::logGammaProduct => Invalid Inputs");
		}

		double logGammaProduct = 0.;

		for (double root : _rootArray)
		{
			logGammaProduct = logGammaProduct +
				logGammaEstimator.evaluate (b - root + 1) -
				logGammaEstimator.evaluate (a - root);
		}

		return logGammaProduct;
	}
}
