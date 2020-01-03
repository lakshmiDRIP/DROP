
package org.drip.sample.matrix;

import java.util.Map;

import org.drip.function.definition.R1ToR1;
import org.drip.function.matrix.FrobeniusCovariance;
import org.drip.function.matrix.Square;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.eigen.EigenOutput;
import org.drip.numerical.eigen.QREigenComponentExtractor;
import org.drip.numerical.linearalgebra.Matrix;
import org.drip.service.env.EnvManager;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, valuation adjustment, and portfolio construction within and across fixed income,
 *  	credit, commodity, equity, FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three modules:
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
 * 	- Statistical Learning
 * 	- Numerical Optimizer
 * 	- Spline Builder
 * 	- Algorithm Support
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalCore.md">Numerical Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalSupportLibrary.md">Numerical Support Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">Sample</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/matrix/README.md">Linear Algebra and Matrix Utilities</a></li>
 *  </ul>
 * <br><br>
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SylvesterInterpolantReconciler
{

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
			50,
			0.00001
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

		double[][] recoveredA = Matrix.Scale2D (
			frobeniusCovariant0,
			eigenValueArray[0]
		);

		double[][] recoveredA1 = Matrix.Scale2D (
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

		double[][] inverseA = Matrix.Scale2D (
			frobeniusCovariant0,
			1. / eigenValueArray[0]
		);

		double[][] inverseA1 = Matrix.Scale2D (
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
			Matrix.Invert (
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
