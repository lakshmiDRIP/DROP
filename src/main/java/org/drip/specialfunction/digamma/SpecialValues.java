
package org.drip.specialfunction.digamma;

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
 * <i>SpecialValues</i> holds a specific Collection of Special Values of the Digamma Function. The References
 * are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Abramowitz, M., and I. A. Stegun (2007): Handbook of Mathematics Functions <b>Dover Book on
 * 				Mathematics</b>
 * 		</li>
 * 		<li>
 * 			Blagouchine, I. V. (2018): Three Notes on Ser's and Hasse's Representations for the
 * 				Zeta-Functions https://arxiv.org/abs/1606.02044 <b>arXiv</b>
 * 		</li>
 * 		<li>
 * 			Mezo, I., and M. E. Hoffman (2017): Zeros of the Digamma Function and its Barnes G-function
 * 				Analogue <i>Integral Transforms and Special Functions</i> <b>28 (28)</b> 846-858
 * 		</li>
 * 		<li>
 * 			Whitaker, E. T., and G. N. Watson (1996): <i>A Course on Modern Analysis</i> <b>Cambridge
 * 				University Press</b> New York
 * 		</li>
 * 		<li>
 * 			Wikipedia (2019): Digamma Function https://en.wikipedia.org/wiki/Digamma_function
 * 		</li>
 * 	</ul>
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalOptimizerLibrary.md">Numerical Optimizer</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Function</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/digamma/README.md">Estimates of the Digamma Function</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SpecialValues
{

	/**
	 * Construct the Fractionals Map for Leading Digamma Fractions
	 * 
	 * @return Fractionals Map for Leading Digamma Fractions
	 */

	public static final java.util.Map<java.lang.Double, java.lang.Double> Fractionals()
	{
		java.util.Map<java.lang.Double, java.lang.Double> fractionalsMap = new
			java.util.TreeMap<java.lang.Double, java.lang.Double>();

		double log2 = java.lang.Math.log (2.);

		double log3 = java.lang.Math.log (3.);

		double sqrt2 = java.lang.Math.sqrt (2.);

		double sqrt3 = java.lang.Math.sqrt (3.);

		fractionalsMap.put (
			1.,
			-org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI
		);

		fractionalsMap.put (
			1. / 2.,
			-2. * log2 - org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI
		);

		fractionalsMap.put (
			1. / 3.,
			-0.5 * java.lang.Math.PI / sqrt3 - 1.5 * log3 -
				org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI
		);

		fractionalsMap.put (
			1. / 4.,
			-0.5 * java.lang.Math.PI - 3. * log2 -
				org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI
		);

		fractionalsMap.put (
			1. / 6.,
			-0.5 * java.lang.Math.PI * log3 - 2. * log2 - 1.5 * log3 -
				org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI
		);

		fractionalsMap.put (
			1. / 8.,
			-0.5 * java.lang.Math.PI - 4. * log2 -
				(java.lang.Math.PI + java.lang.Math.log (2. + sqrt2) - java.lang.Math.log (2. - sqrt2)) /
					sqrt2 -
				org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI
		);

		return fractionalsMap;
	}

	/**
	 * Construct the Unit Imaginary Digamma Complex Number
	 *  
	 * @param termCount The Term Count
	 * 
	 * @return Unit Imaginary Digamma Complex Number
	 */

	public static final org.drip.numerical.fourier.ComplexNumber UnitImaginary (
		final int termCount)
	{
		if (0 >= termCount)
		{
			return null;
		}

		double realPart = -1. * org.drip.specialfunction.gamma.Definitions.EULER_MASCHERONI;

		for (int n = 1; n <= termCount; ++n)
		{
			realPart = realPart + (1. - n) / (n * n * n + n * n + n + 1);
		}

		try
		{
			return new org.drip.numerical.fourier.ComplexNumber (
				realPart,
				0.5 + 0.5 * java.lang.Math.PI / java.lang.Math.tanh (java.lang.Math.PI)
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
