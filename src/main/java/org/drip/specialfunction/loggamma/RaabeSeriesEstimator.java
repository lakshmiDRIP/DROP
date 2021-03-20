
package org.drip.specialfunction.loggamma;

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
 * <i>RaabeSeriesEstimator</i> implements the Raabe Series Version of Log Gamma Function. This Version is
 * Series Convergent. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Mortici, C. (2011): Improved Asymptotic Formulas for the Gamma Function <i>Computers and
 * 				Mathematics with Applications</i> <b>61 (11)</b> 3364-3369
 * 		</li>
 * 		<li>
 * 			National Institute of Standards and Technology (2018): NIST Digital Library of Mathematical
 * 				Functions https://dlmf.nist.gov/5.11
 * 		</li>
 * 		<li>
 * 			Nemes, G. (2010): On the Coefficients of the Asymptotic Expansion of n!
 * 				https://arxiv.org/abs/1003.2907 <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Toth V. T. (2016): Programmable Calculators – The Gamma Function
 * 				http://www.rskey.org/CMS/index.php/the-library/11
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Stirling's Approximation
 * 				https://en.wikipedia.org/wiki/Stirling%27s_approximation
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/loggamma/README.md">Analytic/Series/Integral Log Gamma Estimators</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RaabeSeriesEstimator extends org.drip.numerical.estimation.R1ToR1Estimator
{

	/**
	 * RaabeSeriesEstimator Constructor
	 * 
	 * @param dc The Derivative Control
	 */

	public RaabeSeriesEstimator (
		final org.drip.numerical.differentiation.DerivativeControl dc)
	{
		super (dc);
	}

	@Override public double evaluate (
		final double x)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (x) || 0. >= x)
		{
			throw new java.lang.Exception ("RaabeSeriesEstimator::evaluate => Invalid Inputs");
		}

		return x * java.lang.Math.log (x) - x + 0.5 * java.lang.Math.log (2. * java.lang.Math.PI / x);
	}

	/**
	 * Compute the Bounded Function Estimates along with the Higher Order Inverted Rising Exponentials
	 * 
	 * @param x X
	 * 
	 * @return The Bounded Function Estimates along with the Higher Order Inverted Rising Exponentials
	 */

	public org.drip.numerical.estimation.R1Estimate invertedRisingExponentialCorrectionEstimate (
		final double x)
	{
		java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap = new
			java.util.TreeMap<java.lang.Integer, java.lang.Double>();

		termWeightMap.put (
			1,
			1. / 12.
		);

		termWeightMap.put (
			2,
			1. / 12.
		);

		termWeightMap.put (
			3,
			59. / 360.
		);

		termWeightMap.put (
			4,
			29. / 60.
		);

		try
		{
			return seriesEstimate (
				x,
				termWeightMap,
				new org.drip.numerical.estimation.R1ToR1Series (
					org.drip.numerical.estimation.R1ToR1SeriesTerm.InvertedRisingExponential(),
					false,
					termWeightMap
				)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Raabe's Strip Integral between (a, a + 1) for the Log Gamma Function
	 * 
	 * @param a a
	 * 
	 * @return The Raabe's Strip Integral between (a, a + 1) for the Log Gamma Function
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public double stripIntegral (
		final double a)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (a) || 0. > a)
		{
			throw new java.lang.Exception ("RaabeSeriesEstimator::stripIntegral => Invalid Inputs");
		}

		return 0.5 * java.lang.Math.log (2. * java.lang.Math.PI) + a * java.lang.Math.log (a) - a;
	}
}
