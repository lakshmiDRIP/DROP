
package org.drip.sample.matrix;

import org.drip.quant.common.*;
import org.drip.quant.linearalgebra.*;
import org.drip.service.env.EnvManager;

/*

 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>LinearAlgebra</i> implements Samples for Linear Algebra and Matrix Manipulations. It demonstrates the
 * following:
 *  
 * <br><br>
 *  <ul>
 *  	<li>
 * 			Compute the inverse of a matrix, and multiply with the original to recover the unit matrix
 *  	</li>
 *  	<li>
 * 			Solves system of linear equations using one the exposed techniques
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

public class LinearAlgebra {

	/*
	 * Sample illustrating the Invocation of Base Matrix Inversion and Product Computation Verification.
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	private static final void InverseVerifyDump (
		final String strLabel,
		final double[][] aadblA)
	{
		double[][] aadblAInv = Matrix.InvertUsingGaussianElimination (aadblA);

		System.out.println ("--- TESTS FOR " + strLabel + "---");

		System.out.println ("---------------------------------");

		NumberUtil.Print2DArrayTriplet (
			"\tSOURCE" + strLabel,
			"INVERSE" + strLabel,
			"PRODUCT" + strLabel,
			aadblA,
			aadblAInv,
			Matrix.Product (
				aadblA,
				aadblAInv
			),
			false
		);

		System.out.println ("---------------------------------\n\n");
	}

	/*
	 * Sample illustrating the Invocation of Base Matrix Manipulation Functionality
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	public static final void MatrixManipulation()
	{
		InverseVerifyDump ("#A", new double[][] {
			{1, 2, 3},
			{4, 5, 6},
			{7, 8, 9.01}
		});

		InverseVerifyDump ("#B", new double[][] {
			{ 0.1667,  0.0000,  0.0000,  0.0000},
			{ 0.0000,  0.0000,  0.0000,  0.1667},
			{-0.6667,  0.5000,  0.0000,  0.0000},
			{ 2.6667, -3.0000,  1.0000,  0.0000}
		});

		InverseVerifyDump ("#C", new double[][] {
			{ 1.0000,  0.0000,  0.0000,  0.0000},
			{ 1.0000,  1.0000,  1.0000,  1.0000},
			{ 0.0000,  1.0000,  0.0000,  0.0000},
			{ 0.0000,  0.0000,  2.0000,  0.0000}
		});

		InverseVerifyDump ("#D", new double[][] {
			{ 0.0000,  1.0000},
			{ 1.0000,  2.0000}
		});

		InverseVerifyDump ("#E", new double[][] {
			{ 0.0000,  1.0000},
			{ 1.0000,  0.0000}
		});

		InverseVerifyDump ("#F", new double[][] {
			{ 1.0000,  0.0000,  0.0000,  0.0000},
			{ 1.0000,  1.0000,  1.0000,  1.0000},
			{-1.0000,  1.0000,  0.0000,  0.0000},
			{ 1.0000,  2.0000,  3.0000,  4.0000}
		});

		InverseVerifyDump ("#G", new double[][] {
			{ 0.0000,  1.0000,  0.0000,  0.0000},
			{ 0.0000,  0.0000,  2.0000,  0.0000},
			{ 0.0434,  0.0188, 16.0083, 24.0037},
			{ 0.0188,  0.0083, 24.0037, 48.0017}
		});
	}

	/*
	 * Sample illustrating the Invocation of Linear System Solver Functionality
	 * 
	 * 	WARNING: Insufficient Error Checking, so use caution
	 */

	public static final void LinearSystemSolver()
	{
		double[][] aadblA = new double[][] {
			{1.000, 0.500, 0.333,  0.000,  0.000, 0.000},
			{0.000, 0.000, 0.000,  1.000,  0.500, 0.333},
			{1.000, 1.000, 1.000, -1.000,  0.000, 0.000},
			{0.000, 0.500, 2.000,  0.000, -0.500, 0.000},
			{0.000, 1.000, 0.000,  0.000,  0.000, 0.000},
			{0.000, 0.000, 0.000,  0.000,  1.000, 0.000},
		};
		double[] adblB = new double[] {0.02, 0.026, 0., 0., 0., 0.};

		org.drip.quant.common.NumberUtil.Print2DArray (
			"\tCOEFF",
			aadblA,
			false
		);

		/*
		 * Solve the Linear System using Gaussian Elimination
		 */

		LinearizationOutput lssGaussianElimination = LinearSystemSolver.SolveUsingGaussianElimination (
			aadblA,
			adblB
		);

		for (int i = 0; i < lssGaussianElimination.getTransformedRHS().length; ++i)
			System.out.println ("GaussianElimination[" + i + "] = " + FormatUtil.FormatDouble
				(lssGaussianElimination.getTransformedRHS()[i], 0, 6, 1.));

		for (int i = 0; i < 6; ++i) {
			double dblRHS = 0.;

			for (int j = 0; j < 6; ++j)
				dblRHS += aadblA[i][j] * lssGaussianElimination.getTransformedRHS()[j];

			System.out.println ("RHS[" + i + "]: " + dblRHS);
		}

		/*
		 * Solve the Linear System using the Gauss-Seidel method
		 */

		/* LinearSystemSolution lssGaussSeidel = LinearSystemSolver.SolveUsingGaussSeidel (aadblA, adblB);

		for (int i = 0; i < lssGaussSeidel.getSolution().length; ++i)
			System.out.println ("GaussSeidel[" + i + "] = " + FormatUtil.FormatDouble (lssGaussSeidel.getSolution()[i], 0, 2, 1.)); */
	}

	public static final void main (
		final String[] astrArgs)
	{
		EnvManager.InitEnv ("");

		MatrixManipulation();

		LinearSystemSolver();

		EnvManager.TerminateEnv();
	}
}
