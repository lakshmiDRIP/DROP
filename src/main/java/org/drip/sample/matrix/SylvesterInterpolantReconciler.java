
package org.drip.sample.matrix;

import java.util.Map;

import org.drip.function.definition.R1ToR1;
import org.drip.function.matrix.FrobeniusCovariance;
import org.drip.function.matrix.Square;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.eigen.EigenOutput;
import org.drip.numerical.eigen.QREigenComponentExtractor;
import org.drip.numerical.linearalgebra.R1MatrixUtil;
import org.drip.service.env.EnvManager;

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
 * <i>SylvesterInterpolantReconciler</i> demonstrates the Construction and Usage of the Sylvester Matrix
 * 	Interpolant. The References are:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Claerbout, J. F. (1985): <i>Fundamentals of Geo-physical Data Processing</i> <b>Blackwell
 *  			Scientific</b>
 *  	</li>
 *  	<li>
 *  		Horn, R. A., and C. R. Johnson (1991): <i>Topics in Matrix Analysis</i> <b>Cambridge University
 *  			Press</b>
 *  	</li>
 *  	<li>
 *  		Schwerdtfeger, A. (1938): <i>Les Fonctions de Matrices: Les Fonctions Univalentes I</i>
 *  			<b>Hermann</b> Paris, France
 *  	</li>
 *  	<li>
 *  		Sylvester, J. J. (1883): On the Equation to the Secular Inequalities in the Planetary Theory
 *  			<i>The London, Edinburgh, and Dublin Philosophical Magazine and Journal of Science</i> <b>16
 *  			(100)</b> 267-269
 *  	</li>
 *  	<li>
 *  		Wikipedia (2019): Sylvester Formula https://en.wikipedia.org/wiki/Sylvester%27s_formula
 *  	</li>
 *  </ul>
 *  
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/matrix/README.md">Cholesky Factorization, PCA, and Eigenization</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SylvesterInterpolantReconciler
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
		EnvManager.InitEnv (
			""
		);

		double[][] a =
		{
			{1, 3},
			{4, 2},
		};

		QREigenComponentExtractor qrece = new QREigenComponentExtractor (
			50
		);

		EigenOutput eigenOutput = qrece.eigenize (
			a
		);

		double[] eigenValueArray = eigenOutput.eigenValueArray();

		System.out.println ("\t|-----------------------------------------|");

		NumberUtil.PrintMatrix (
			"\t| ORIGINAL MATRIX",
			a
		);

		System.out.println ("\t|-----------------------------------------|");

		System.out.println();

		System.out.println ("\t|-----------------------------------------|");

		System.out.println ("\t|              EIGEN VALUES               |");

		System.out.println ("\t|-----------------------------------------|");

		System.out.println ("\t|  " + eigenValueArray[0] + "  |  " + eigenValueArray[1]);

		System.out.println ("\t|-----------------------------------------|");

		double[][] frobeniusCovariant0 = new double[2][2];
		double[][] frobeniusCovariant1 = new double[2][2];
		frobeniusCovariant0[0][0] = (a[0][0] - eigenValueArray[1]) / (eigenValueArray[0] - eigenValueArray[1]);
		frobeniusCovariant0[1][1] = (a[1][1] - eigenValueArray[1]) / (eigenValueArray[0] - eigenValueArray[1]);
		frobeniusCovariant0[0][1] = a[0][1] / (eigenValueArray[0] - eigenValueArray[1]);
		frobeniusCovariant0[1][0] = a[1][0] / (eigenValueArray[0] - eigenValueArray[1]);
		frobeniusCovariant1[0][0] = (a[0][0] - eigenValueArray[0]) / (eigenValueArray[1] - eigenValueArray[0]);
		frobeniusCovariant1[1][1] = (a[1][1] - eigenValueArray[0]) / (eigenValueArray[1] - eigenValueArray[0]);
		frobeniusCovariant1[0][1] = a[0][1] / (eigenValueArray[1] - eigenValueArray[0]);
		frobeniusCovariant1[1][0] = a[1][0] / (eigenValueArray[1] - eigenValueArray[0]);

		System.out.println();

		System.out.println ("\t|-----------------------------------------|");

		System.out.println ("\t|          SYLVESTER RECONCILER           |");

		System.out.println ("\t|-----------------------------------------|");

		NumberUtil.PrintMatrix (
			"\t| FROBENIUS COVARIANT 0",
			frobeniusCovariant0
		);

		System.out.println ("\t|-----------------------------------------|");

		NumberUtil.PrintMatrix (
			"\t| FROBENIUS COVARIANT 1",
			frobeniusCovariant1
		);

		System.out.println ("\t|-----------------------------------------|");

		double[][] recoveredA = R1MatrixUtil.Scale2D (
			frobeniusCovariant0,
			eigenValueArray[0]
		);

		double[][] recoveredA1 = R1MatrixUtil.Scale2D (
			frobeniusCovariant1,
			eigenValueArray[1]
		);

		recoveredA[0][0] += recoveredA1[0][0];
		recoveredA[0][1] += recoveredA1[0][1];
		recoveredA[1][0] += recoveredA1[1][0];
		recoveredA[1][1] += recoveredA1[1][1];

		System.out.println ("\t|------------------------------------------|");

		NumberUtil.PrintMatrix (
			"\t| RECOVERED MATRIX",
			recoveredA
		);

		System.out.println ("\t|------------------------------------------|");

		double[][] inverseA = R1MatrixUtil.Scale2D (
			frobeniusCovariant0,
			1. / eigenValueArray[0]
		);

		double[][] inverseA1 = R1MatrixUtil.Scale2D (
			frobeniusCovariant1,
			1. / eigenValueArray[1]
		);

		inverseA[0][0] += inverseA1[0][0];
		inverseA[0][1] += inverseA1[0][1];
		inverseA[1][0] += inverseA1[1][0];
		inverseA[1][1] += inverseA1[1][1];

		System.out.println ("\t|----------------------------------------|");

		NumberUtil.PrintMatrix (
			"\t| INVERSE MATRIX",
			inverseA
		);

		System.out.println ("\t|----------------------------------------|");

		NumberUtil.PrintMatrix (
			"\t| INVERSE MATRIX",
			R1MatrixUtil.Invert (
				a,
				""
			)
		);

		System.out.println ("\t|----------------------------------------|");

		Square aSquare = new Square (
			a
		);

		FrobeniusCovariance frobeniusCovariance = aSquare.frobeniusCovariance();

		Map<Double, Square> componentMap = frobeniusCovariance.componentMap();

		Object[] eigenValueKey = componentMap.keySet().toArray();

		frobeniusCovariant0 = componentMap.get (
			eigenValueKey[0]
		).grid();

		frobeniusCovariant1 = frobeniusCovariance.componentMap().get (
			eigenValueKey[1]
		).grid();

		System.out.println ("\t|-----------------------------------------|");

		System.out.println ("\t|          SYLVESTER RECONCILER           |");

		System.out.println ("\t|-----------------------------------------|");

		NumberUtil.PrintMatrix (
			"\t| FROBENIUS COVARIANT 0",
			frobeniusCovariant0
		);

		System.out.println ("\t|-----------------------------------------|");

		NumberUtil.PrintMatrix (
			"\t| FROBENIUS COVARIANT 1",
			frobeniusCovariant1
		);

		System.out.println ("\t|-----------------------------------------|");

		recoveredA = aSquare.evaluate (
			new R1ToR1 (
				null
			)
			{
				@Override public double evaluate (
					final double x)
					throws Exception
				{
					return x;
				}
			}
		);

		System.out.println ("\t|----------------------------------------|");

		NumberUtil.PrintMatrix (
			"\t| RECOVERED MATRIX",
			recoveredA
		);

		System.out.println ("\t|----------------------------------------|");

		inverseA = aSquare.evaluate (
			new R1ToR1 (
				null
			)
			{
				@Override public double evaluate (
					final double x)
					throws Exception
				{
					return 1. / x;
				}
			}
		);

		System.out.println ("\t|----------------------------------------|");

		NumberUtil.PrintMatrix (
			"\t| INVERSE MATRIX",
			inverseA
		);

		System.out.println ("\t|----------------------------------------|");

		EnvManager.TerminateEnv();
	}
}
