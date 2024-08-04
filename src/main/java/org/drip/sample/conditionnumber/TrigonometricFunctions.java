
package org.drip.sample.conditionnumber;

import org.drip.function.r1tor1trigonometric.Cosine;
import org.drip.function.r1tor1trigonometric.InverseCosine;
import org.drip.function.r1tor1trigonometric.InverseSine;
import org.drip.function.r1tor1trigonometric.InverseTangent;
import org.drip.function.r1tor1trigonometric.Sine;
import org.drip.function.r1tor1trigonometric.Tangent;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2025 Lakshmi Krishnamurthy
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
 * <i>TrigonometricFunctions</i> illustrates the Estimation of Condition Numbers for Trigonometric Functions.
 *  The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Belsley, D. A., E. Kuh, and R. E. Welsch (1980): <i>Regression Dynamics: Identifying Influential
 * 				Data and Sources of Collinearity</i> <b>John Wiley and Sons</b> New York NY
 * 		</li>
 * 		<li>
 * 			Cheney, K. (2008): <i>Numerical Mathematics and Computing</i> <b>Cengage Learning</b> New York NY
 * 		</li>
 * 		<li>
 * 			Pesaran, M. H. (2015): <i>Time Series and Panel Data Econometrics</i> <b>Oxford University
 * 				Press</b> New York NY
 * 		</li>
 * 		<li>
 * 			Trefethen, L. N., and D. Bau III (1997): <i>Numerical Linear Algebra</i> <b>Society for
 * 				Industrial and Applied Mathematics</b> Philadelphia PA
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Condition Number https://en.wikipedia.org/wiki/Condition_number
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/conditionnumber/README.md">Condition Number Analysis of R<sup>1</sup> To R<sup>1</sup> Functions</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class TrigonometricFunctions
{

	/**
	 * Entry Point
	 * 
	 * @param argumentArray Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double[] x1Array = {0.01, 0.10, 0.20, 0.30, 0.40, 0.49, 0.51, 0.60, 0.70, 0.80, 0.90, 0.99};

		Sine sine = new Sine();

		Cosine cosine = new Cosine();

		Tangent tangent = new Tangent();

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\t|       TRIGONOMETRIC CONDITION NUMBERS       ||");

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\t|  Input L -> R:                              ||");

		System.out.println ("\t|    - Variate                                ||");

		System.out.println ("\t|---------------------------------------------||");

		System.out.println ("\t|  Output L -> R:                             ||");

		System.out.println ("\t|    - Sine                                   ||");

		System.out.println ("\t|    - Cosine                                 ||");

		System.out.println ("\t|    - Tangent                                ||");

		System.out.println ("\t|---------------------------------------------||");

		for (double x : x1Array) {
			System.out.println (
				"\t| [" + FormatUtil.FormatDouble (x * Math.PI, 1, 2, 1.) + "] =>" +
				FormatUtil.FormatDouble (sine.conditionNumber (x * Math.PI), 2, 6, 1.) + " |" +
				FormatUtil.FormatDouble (cosine.conditionNumber (x * Math.PI), 2, 6, 1.) + " |" +
				FormatUtil.FormatDouble (tangent.conditionNumber (x * Math.PI), 1, 6, 1.) + " ||"
			);
		}

		System.out.println ("\t|---------------------------------------------||");

		System.out.println();

		double[] x2Array = {-0.99, -0.75, -0.50, -0.25, -0.01, 0.01, 0.25, 0.50, 0.75, 0.99};

		InverseSine inverseSine = new InverseSine();

		InverseCosine inverseCosine = new InverseCosine();

		InverseTangent inverseTangent = new InverseTangent();

		System.out.println ("\t|--------------------------------------------||");

		System.out.println ("\t|  INVERSE TRIGONOMETRIC CONDITION NUMBERS   ||");

		System.out.println ("\t|--------------------------------------------||");

		System.out.println ("\t|  Input L -> R:                             ||");

		System.out.println ("\t|    - Variate                               ||");

		System.out.println ("\t|--------------------------------------------||");

		System.out.println ("\t|  Output L -> R:                            ||");

		System.out.println ("\t|    - Inverse Sine                          ||");

		System.out.println ("\t|    - Inverse Cosine                        ||");

		System.out.println ("\t|    - Inverse Tangent                       ||");

		System.out.println ("\t|--------------------------------------------||");

		for (double x : x2Array) {
			System.out.println (
				"\t| [" + FormatUtil.FormatDouble (x, 1, 2, 1.) + "] =>" +
				FormatUtil.FormatDouble (inverseSine.conditionNumber (x), 1, 6, 1.) + " |" +
				FormatUtil.FormatDouble (inverseCosine.conditionNumber (x), 2, 6, 1.) + " |" +
				FormatUtil.FormatDouble (inverseTangent.conditionNumber (x), 1, 6, 1.) + " ||"
			);
		}

		System.out.println ("\t|--------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
