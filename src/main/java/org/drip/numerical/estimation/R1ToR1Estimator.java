
package org.drip.numerical.estimation;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 * <i>R1ToR1Estimator</i> exposes the Stubs behind R<sup>1</sup> - R<sup>1</sup> Approximate Numerical
 * Estimators. The References are:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/estimation/README.md">Function Numerical Estimates/Corrections/Bounds</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class R1ToR1Estimator extends org.drip.function.definition.R1ToR1
{

	/**
	 * R<sup>1</sup> - R<sup>1</sup> Estimator Constructor
	 * 
	 * @param dc The Derivative Control
	 */

	public R1ToR1Estimator (
		final org.drip.numerical.differentiation.DerivativeControl dc)
	{
		super (dc);
	}

	/**
	 * Estimate a Bounded Numerical Approximation of the Function Value
	 * 
	 * @param x X
	 * 
	 * @return The Bounded Numerical Approximation
	 */

	public org.drip.numerical.estimation.R1Estimate boundedEstimate (
		final double x)
	{
		try
		{
			return org.drip.numerical.estimation.R1Estimate.BaselineOnly (evaluate (x));
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Compute the Higher Order Series Estimates
	 * 
	 * @param x X
	 * @param termWeightMap Error Term Weight Map
	 * @param r1ToR1SeriesGenerator R<sup>1</sup> To R<sup>1</sup> Series Generator
	 * 
	 * @return The Higher Order Series Estimates
	 */

	public org.drip.numerical.estimation.R1Estimate seriesEstimate (
		final double x,
		final java.util.TreeMap<java.lang.Integer, java.lang.Double> termWeightMap,
		final org.drip.numerical.estimation.R1ToR1SeriesGenerator r1ToR1SeriesGenerator)
	{
		org.drip.numerical.estimation.R1Estimate r1NumericalEstimate = boundedEstimate (x);

		if (null == r1NumericalEstimate ||
			null == termWeightMap || 0 == termWeightMap.size() ||
			null == r1ToR1SeriesGenerator)
		{
			return r1NumericalEstimate;
		}

		return r1NumericalEstimate.addOrderedSeriesMap (
			r1ToR1SeriesGenerator.generate (
				r1NumericalEstimate.baseline(),
				x
			)
		) ? r1NumericalEstimate : null;
	}

	/**
	 * Compute the Built-in Higher Order Series Estimates
	 * 
	 * @param x X
	 * 
	 * @return The Built-in Higher Order Series Estimates
	 */

	public org.drip.numerical.estimation.R1Estimate seriesEstimateNative (
		final double x)
	{
		return seriesEstimate (
			x,
			null,
			null
		);
	}
}
