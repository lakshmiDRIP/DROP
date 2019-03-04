
package org.drip.function.stirling;

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
 * <i>RaabeLogGamma</i> implements the Raabe's Version of Stirling's Approximation of the Log Gamma Function.
 * This Version is Series Convergent. The References are:
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
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/stirling/README.md">Stirling Variants Gamma/Factorial Implementation</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class RaabeLogGamma extends org.drip.function.numerical.R1ToR1Estimator
{

	/**
	 * RaabeLogGamma Constructor
	 * 
	 * @param dc The Derivative Control
	 */

	public RaabeLogGamma (
		final org.drip.quant.calculus.DerivativeControl dc)
	{
		super (dc);
	}

	@Override public double evaluate (
		final double x)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (x) || 0. >= x)
		{
			throw new java.lang.Exception ("RaabeLogGamma::evaluate => Invalid Inputs");
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

	public org.drip.function.numerical.R1Estimate invertedRisingExponentialEstimate (
		final double x)
	{
		org.drip.function.numerical.R1Estimate r1NumericalEstimate = estimate (x);

		if (null == r1NumericalEstimate)
		{
			return null;
		}

		if (0. >= x)
		{
			return r1NumericalEstimate;
		}

		return !r1NumericalEstimate.addCorrection (
			1,
			1. / (12. * (x + 1.))
		) || !r1NumericalEstimate.addCorrection (
			2,
			1. / (12. * (x + 1.) * (x + 2.))
		) || !r1NumericalEstimate.addCorrection (
			3,
			59. / (360. * (x + 1.) * (x + 2.) * (x + 3.))
		) || !r1NumericalEstimate.addCorrection (
			4,
			29. / (60. * (x + 1.) * (x + 2.) * (x + 3.) * (x + 4.))
		) ? null : r1NumericalEstimate;
	}
}
