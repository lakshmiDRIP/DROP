
package org.drip.sample.triangular;

import java.util.Date;

import org.drip.measure.crng.RandomMatrixGenerator;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.matrix.R1Triangular;
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
 * <i>StrictlyLower</i> shows the Construction, the Usage, and the Analysis of a Strictly Lower Triangular
 * 	Matrix. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Axler, S. J. (1997): <i>Linear Algebra Done Right 2<sup>nd</sup> Edition</i> <b>Springer</b>
 * 				New York NY
 * 		</li>
 * 		<li>
 * 			Bernstein, D. S. (2009): <i>Matrix Mathematics: Theory, Facts, and Formulas 2<sup>nd</sup>
 * 				Edition</i> <b>Princeton University Press</b> Princeton NJ
 * 		</li>
 * 		<li>
 * 			Herstein, I. N. (1975): <i>Topics in Algebra 2<sup>nd</sup> Edition</i> <b>Wiley</b> New York NY
 * 		</li>
 * 		<li>
 * 			Prasolov, V. V. (1994): <i>Topics in Algebra</i> <b>American Mathematical Society</b> Providence
 * 				RI
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Triangular Matrix https://en.wikipedia.org/wiki/Triangular_matrix
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/triangular/README.md">Triangular Matrix Variants and Solutions</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class StrictlyLower
{

	private static final void Trial (
		final int elementCount,
		final double maximumElement)
		throws Exception
	{
		R1Triangular strictlyLowerTriangular = RandomMatrixGenerator.StrictlyLowerTriangular (
			elementCount,
			maximumElement,
			true
		);

		System.out.println (
			"\t|----------------------------------------------------------------------------------||"
		);

		System.out.println ("\t| Trial at " + new Date());

		System.out.println (
			"\t|----------------------------------------------------------------------------------||"
		);

		double[][] strictlyLowerTriangularR2Array = strictlyLowerTriangular.r1Grid();

		for (int i = 0; i < strictlyLowerTriangularR2Array.length; ++i) {
			System.out.println (
				"\t| Strictly Lower Triangular " + elementCount + " x " + elementCount + "    => [" +
					NumberUtil.ArrayRow (
						strictlyLowerTriangularR2Array[i],
						2,
						1,
						false
					) + " ]||"
			);
		}

		System.out.println (
			"\t|----------------------------------------------------------------------------------||"
		);

		double[][] strictlyLowerTriangularR2ArrayTranspose = strictlyLowerTriangular.transpose().r1Grid();

		for (int i = 0; i < strictlyLowerTriangularR2Array.length; ++i) {
			System.out.println (
				"\t| Transpose " + elementCount + " x " + elementCount + "                    => [" +
					NumberUtil.ArrayRow (
						strictlyLowerTriangularR2ArrayTranspose[i],
						2,
						1,
						false
					) + " ]||"
			);
		}

		System.out.println (
			"\t|----------------------------------------------------------------------------------||"
		);

		double[][] baseTransposeProduct =
			strictlyLowerTriangular.product (strictlyLowerTriangular.transpose()).r1Grid();

		for (int i = 0; i < strictlyLowerTriangularR2Array.length; ++i) {
			System.out.println (
				"\t| Base Transpose Product " + elementCount + " x " + elementCount + " => [" +
					NumberUtil.ArrayRow (
						baseTransposeProduct[i],
						5,
						0,
						false
					) + " ]||"
			);
		}

		System.out.println (
			"\t|----------------------------------------------------------------------------------||"
		);

		double[][] transposeBaseProduct =
			strictlyLowerTriangular.transpose().product (strictlyLowerTriangular).r1Grid();

		for (int i = 0; i < strictlyLowerTriangularR2Array.length; ++i) {
			System.out.println (
				"\t| Transpose Base Product " + elementCount + " x " + elementCount + " => [" +
					NumberUtil.ArrayRow (
						transposeBaseProduct[i],
						5,
						0,
						false
					) + " ]||"
			);
		}

		System.out.println (
			"\t|----------------------------------------------------------------------------------||"
		);

		System.out.println (
			"\t| Is Upper Triangular             => " +
				StringUtil.ToString (strictlyLowerTriangular.isUpper())
		);

		System.out.println (
			"\t| Is Lower Triangular             => " +
				StringUtil.ToString (strictlyLowerTriangular.isLower())
		);

		System.out.println (
			"\t| Is Diagonal                     => " +
				StringUtil.ToString (strictlyLowerTriangular.isDiagonal())
		);

		System.out.println (
			"\t| Is Triangularizable             => " +
				StringUtil.ToString (strictlyLowerTriangular.isTriangularizable())
		);

		System.out.println (
			"\t| Is Unitriangular                => " +
				StringUtil.ToString (strictlyLowerTriangular.isUnitriangular())
		);

		System.out.println (
			"\t| Is Unit Triangular              => " +
				StringUtil.ToString (strictlyLowerTriangular.isUnit())
		);

		System.out.println (
			"\t| Is Normed Triangular            => " +
				StringUtil.ToString (strictlyLowerTriangular.isNormed())
		);

		System.out.println (
			"\t| Is Upper Unitriangular          => " +
				StringUtil.ToString (strictlyLowerTriangular.isUpperUnitriangular())
		);

		System.out.println (
			"\t| Is Lower Unitriangular          => " +
				StringUtil.ToString (strictlyLowerTriangular.isLowerUnitriangular())
		);

		System.out.println (
			"\t| Is Strictly Upper Triangular    => " +
				StringUtil.ToString (strictlyLowerTriangular.isStrictlyUpper())
		);

		System.out.println (
			"\t| Is Strictly Lower Triangular    => " +
				StringUtil.ToString (strictlyLowerTriangular.isStrictlyLower())
		);

		System.out.println (
			"\t| Is Atomic Upper Unitriangular   => " +
				StringUtil.ToString (strictlyLowerTriangular.isAtomicUpper())
		);

		System.out.println (
			"\t| Is Atomic Lower Unitriangular   => " +
				StringUtil.ToString (strictlyLowerTriangular.isAtomicLower())
		);

		System.out.println (
			"\t| Is Atomic                       => " +
				StringUtil.ToString (strictlyLowerTriangular.isAtomic())
		);

		System.out.println (
			"\t| Is Frobenius                    => " +
				StringUtil.ToString (strictlyLowerTriangular.isFrobenius())
		);

		System.out.println (
			"\t| Is Gauss                        => " +
				StringUtil.ToString (strictlyLowerTriangular.isGauss())
		);

		System.out.println (
			"\t| Is Gauss Transformation         => " +
				StringUtil.ToString (strictlyLowerTriangular.isGaussTransformation())
		);

		System.out.println (
			"\t| Determinant                     => " +
				FormatUtil.FormatDouble (strictlyLowerTriangular.determinant(), 8, 0, 1., false)
		);

		System.out.println (
			"\t| Permanent                       => " +
				FormatUtil.FormatDouble (strictlyLowerTriangular.permanent(), 8, 0, 1., false)
		);

		System.out.println (
			"\t| Eigenvalue Multiplicity Map     => " + strictlyLowerTriangular.eigenValueMultiplicityMap()
		);

		System.out.println (
			"\t| Is Normal                       => " +
				StringUtil.ToString (strictlyLowerTriangular.isNormal())
		);

		System.out.println (
			"\t|----------------------------------------------------------------------------------||"
		);

		System.out.println();
	}

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
		EnvManager.InitEnv (
			""
		);

		int elementCount = 6;
		double maximumElement = 99.;

		Trial (elementCount, maximumElement);

		Trial (elementCount, maximumElement);

		Trial (elementCount, maximumElement);

		Trial (elementCount, maximumElement);

		Trial (elementCount, maximumElement);

		EnvManager.TerminateEnv();
	}
}
