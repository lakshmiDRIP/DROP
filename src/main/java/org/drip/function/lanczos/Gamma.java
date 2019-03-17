
package org.drip.function.lanczos;

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
 * <i>Gamma</i> implements the Lanczos Gamma Function Estimation Scheme. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Godfrey, P. (2001): Lanczos Implementation of the Gamma Function
 * 				http://www.numericana.com/answer/info/godfrey.htm
 * 		</li>
 * 		<li>
 * 			Press, W. H., S. A. Teukolsky, W. T. Vetterling, and B. P. Flannery (2007): <i>Numerical Recipes:
 * 				The Art of Scientific Computing 3rd Edition</i> <b>Cambridge University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Pugh, G. R. (2004): <i>An Analysis of the Lanczos Gamma Approximation</i> Ph. D. <b>University of
 * 				British Columbia</b>
 * 		</li>
 * 		<li>
 * 			Toth V. T. (2016): Programmable Calculators – The Gamma Function
 * 				http://www.rskey.org/CMS/index.php/the-library/11
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Lanczos Approximation https://en.wikipedia.org/wiki/Lanczos_approximation
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/feed/lanczos/README.md">Lanczos Scheme for Gamma Estimate</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class Gamma extends org.drip.numerical.estimation.R1ToR1Estimator
{
	private org.drip.function.lanczos.ASeriesGenerator _aSeriesGenerator = null;

	/**
	 * Gamma Constructor
	 * 
	 * @param aSeriesGenerator Lanczos A Series Generator
	 * @param dc Derivative Control
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public Gamma (
		final org.drip.function.lanczos.ASeriesGenerator aSeriesGenerator,
		final org.drip.numerical.differentiation.DerivativeControl dc)
		throws java.lang.Exception
	{
		super (dc);

		if (null == (_aSeriesGenerator = aSeriesGenerator))
		{
			throw new java.lang.Exception ("Gamma Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the A Series Generator
	 * 
	 * @return The A Series Generator
	 */

	public org.drip.function.lanczos.ASeriesGenerator aSeriesGenerator()
	{
		return _aSeriesGenerator;
	}

	@Override public double evaluate (
		final double z)
		throws java.lang.Exception
	{
		if (!org.drip.numerical.common.NumberUtil.IsValid (z) || 0. >= z)
		{
			throw new java.lang.Exception ("Gamma::evaluate => Invalid Inputs");
		}

		int g = ((org.drip.function.lanczos.PSeriesTerm)
			_aSeriesGenerator.pSeriesGenerator().r0Tor1SeriesTerm()).g();

		double zPlusGPlusHalf = z + g + 0.5;

		return java.lang.Math.sqrt (2. * java.lang.Math.PI) * java.lang.Math.pow (
			zPlusGPlusHalf,
			z + 0.5
		) * java.lang.Math.exp (-1. * zPlusGPlusHalf) * _aSeriesGenerator.cumulative (
			0.,
			z
		);
	}

	@Override public org.drip.numerical.estimation.R1Estimate seriesEstimateNative (
		final double x)
	{
		return seriesEstimate (
			x,
			_aSeriesGenerator.termWeightMap(),
			_aSeriesGenerator
		);
	}
}
