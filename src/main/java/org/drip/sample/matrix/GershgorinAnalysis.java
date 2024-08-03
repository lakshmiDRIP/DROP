
package org.drip.sample.matrix;

import org.drip.numerical.linearalgebra.GershgorinAnalyzer;
import org.drip.numerical.linearalgebra.GershgorinDisc;
import org.drip.numerical.linearalgebra.SquareMatrix;
import org.drip.service.common.FormatUtil;
import org.drip.service.common.StringUtil;
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
 * <i>GershgorinAnalysis</i> illustrates the Analysis of a Square Matrix using Gershgorin Discs. The
 *  References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Golub, G. H., and C. F. van Loan (1996): <i>Matrix Computations 3<sup>rd</sup> Edition</i>
 * 				<b>Johns Hopkins University Press</b> Baltimore MD
 * 		</li>
 * 		<li>
 * 			Horn, R. A., and C. R. Johnson (2013): <i>Matrix Analysis 2<sup>nd</sup> Edition</i> <b>Cambridge
 * 				University Press</b> Cambridge UK
 * 		</li>
 * 		<li>
 * 			Li, C. K., and F. Zhang (2019): Eigenvalue Continuity and Gershgorin’s Theorem <i>Electronic
 * 				Journal of Linear Algebra</i> <b>35</b> 619-625
 * 		</li>
 * 		<li>
 * 			Trefethen, L. N., and D. Bau III (1997): <i>Numerical Linear Algebra</i> <b>Society for
 * 				Industrial and Applied Mathematics</b> Philadelphia PA
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Gershgorin Circle Theorem
 * 				https://en.wikipedia.org/wiki/Gershgorin_circle_theorem
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/linearalgebra/README.md">Linear Algebra Matrix Transform Library</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class GershgorinAnalysis
{

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 * 
	 * @throws Exception Thrown on Error/Exception Situation
	 */

	public static final void main (
		final String[] astrArgs)
		throws Exception
	{
		EnvManager.InitEnv ("");

		double[][] r2Array = new double[][] {
			{10.0,  1.0,  0.0,   1.0},
			{ 0.2,  8.0,  0.2,   0.2},
			{ 1.0,  1.0,  2.0,   1.0},
			{-1.0, -1.0, -1.0, -11.0},
		};

		GershgorinDisc[] gershgorinDiscRowArray = GershgorinAnalyzer.FromSquareMatrix (
			SquareMatrix.Standard (r2Array),
			true
		).gershgorinDiscArray();

		System.out.println ("\t|----------------------------------------------------||");

		System.out.println ("\t|              ROW GERSHGORIN ANALYSIS               ||");

		System.out.println ("\t|----------------------------------------------------||");

		System.out.println ("\t|  Input L -> R:                                     ||");

		System.out.println ("\t|    - Diagonal Index                                ||");

		System.out.println ("\t|----------------------------------------------------||");

		System.out.println ("\t|  Outputs L -> R:                                   ||");

		System.out.println ("\t|    - Diagonal Entry                                ||");

		System.out.println ("\t|    - Disc Radius                                   ||");

		System.out.println ("\t|    - Disc Left Edge                                ||");

		System.out.println ("\t|    - Disc Right Edge                               ||");

		System.out.println ("\t|    - Is Diagonally Dominant                        ||");

		System.out.println ("\t|----------------------------------------------------||");

		for (int i = 0; i < gershgorinDiscRowArray.length; ++i) {
			System.out.println (
				"\t| [" + i + "] => " +
				FormatUtil.FormatDouble (gershgorinDiscRowArray[i].diagonalEntry(), 2, 2, 1.) + " |" +
				FormatUtil.FormatDouble (gershgorinDiscRowArray[i].radius(), 2, 2, 1.) + " | {" +
				FormatUtil.FormatDouble (gershgorinDiscRowArray[i].leftEdge(), 2, 2, 1.) + " -> " +
				FormatUtil.FormatDouble (gershgorinDiscRowArray[i].rightEdge(), 2, 2, 1.) + "} | " +
				StringUtil.ToString (gershgorinDiscRowArray[i].isDiagonallyDominant()) + " ||"
			);
		}

		System.out.println ("\t|----------------------------------------------------||");

		System.out.println();

		GershgorinDisc[] gershgorinDiscColumnArray = GershgorinAnalyzer.FromSquareMatrix (
			SquareMatrix.Standard (r2Array),
			false
		).gershgorinDiscArray();

		System.out.println ("\t|----------------------------------------------------||");

		System.out.println ("\t|            COLUMN GERSHGORIN ANALYSIS              ||");

		System.out.println ("\t|----------------------------------------------------||");

		System.out.println ("\t|  Input L -> R:                                     ||");

		System.out.println ("\t|    - Diagonal Index                                ||");

		System.out.println ("\t|----------------------------------------------------||");

		System.out.println ("\t|  Outputs L -> R:                                   ||");

		System.out.println ("\t|    - Diagonal Entry                                ||");

		System.out.println ("\t|    - Disc Radius                                   ||");

		System.out.println ("\t|    - Disc Left Edge                                ||");

		System.out.println ("\t|    - Disc Right Edge                               ||");

		System.out.println ("\t|    - Is Diagonally Dominant                        ||");

		System.out.println ("\t|----------------------------------------------------||");

		for (int i = 0; i < gershgorinDiscColumnArray.length; ++i) {
			System.out.println (
				"\t| [" + i + "] => " +
				FormatUtil.FormatDouble (gershgorinDiscColumnArray[i].diagonalEntry(), 2, 2, 1.) + " |" +
				FormatUtil.FormatDouble (gershgorinDiscColumnArray[i].radius(), 2, 2, 1.) + " | {" +
				FormatUtil.FormatDouble (gershgorinDiscColumnArray[i].leftEdge(), 2, 2, 1.) + " -> " +
				FormatUtil.FormatDouble (gershgorinDiscColumnArray[i].rightEdge(), 2, 2, 1.) + "} | " +
				StringUtil.ToString (gershgorinDiscColumnArray[i].isDiagonallyDominant()) + " ||"
			);
		}

		System.out.println ("\t|----------------------------------------------------||");

		EnvManager.TerminateEnv();
	}
}
