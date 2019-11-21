
package org.drip.specialfunction.digamma;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FunctionAnalysisLibrary.md">Function Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/README.md">Special Function Implementation Analysis</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/specialfunction/digamma/README.md">Estimation Techniques for Digamma Function</a></li>
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

	public static final org.drip.function.definition.CartesianComplexNumber UnitImaginary (
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
			return new org.drip.function.definition.CartesianComplexNumber (
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
