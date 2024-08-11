
package org.drip.sample.matrix;

import org.drip.numerical.common.*;
import org.drip.numerical.linearalgebra.*;
import org.drip.numerical.linearsolver.LinearSystem;
import org.drip.service.common.FormatUtil;
import org.drip.service.env.EnvManager;

/*

 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2022 Lakshmi Krishnamurthy
 * Copyright (C) 2021 Lakshmi Krishnamurthy
 * Copyright (C) 2020 Lakshmi Krishnamurthy
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
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
 * <i>LinearAlgebra</i> implements Samples for Linear Algebra and Matrix Manipulations. It demonstrates the
 * 	following:
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
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/README.md">DROP API Construction and Usage</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/sample/matrix/README.md">Cholesky Factorization, PCA, and Eigenization</a></li>
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
		double[][] aadblAInv = R1MatrixUtil.InvertUsingGaussianElimination (aadblA);

		System.out.println ("--- TESTS FOR " + strLabel + "---");

		System.out.println ("---------------------------------");

		NumberUtil.Print2DArrayTriplet (
			"\tSOURCE" + strLabel,
			"INVERSE" + strLabel,
			"PRODUCT" + strLabel,
			aadblA,
			aadblAInv,
			R1MatrixUtil.Product (
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

	private static final void MatrixManipulation()
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

	private static final void LinearSystemSolver()
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

		org.drip.numerical.common.NumberUtil.Print2DArray (
			"\tCOEFF",
			aadblA,
			false
		);

		/*
		 * Solve the Linear System using Gaussian Elimination
		 */

		LinearizationOutput lssGaussianElimination = LinearSystem.SolveUsingGaussianElimination (
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

	/**
	 * Entry Point
	 * 
	 * @param astrArgs Command Line Argument Array
	 */

	public static final void main (
		final String[] astrArgs)
	{
		EnvManager.InitEnv ("");

		MatrixManipulation();

		LinearSystemSolver();

		EnvManager.TerminateEnv();
	}
}
