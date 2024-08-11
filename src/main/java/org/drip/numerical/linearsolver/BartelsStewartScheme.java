
package org.drip.numerical.linearsolver;

import org.drip.measure.crng.RdRandomSequence;
import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.linearalgebra.R1MatrixUtil;
import org.drip.numerical.linearalgebra.QR;
import org.drip.numerical.linearalgebra.SylvesterEquation;
import org.drip.numerical.matrix.R1Square;

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
 * <i>BartelsStewartScheme</i> implements the solution to Sylvester Equation, which is defined by:
 * 
 * 									A.X + X.B = RHS
 * 
 * 	X is the unknown whose solution is to sought. Naturally, the sizes of A, B, and RHS need to be consistent.
 *  The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Bhatia, R., and P. Rosenthal (1997): How and why to solve the operator equation
 * 				<code>AX-XB=Y</code>? <i>Bulletin of London Mathematical Society</i> <b>29 (1)</b> 1-21
 * 		</li>
 * 		<li>
 * 			Dmytryshyn, A. and B. Kagstrom (2015): Coupled Sylvester-type Matrix Equation and Block
 * 				Diagonalization <i>SIAM Journal of Matrix Analysis and Applications</i> <b>36 (2)</b> 580-593
 * 		</li>
 * 		<li>
 * 			Gerrish, F., and A. G. B. Ward (1998): Sylvester Matrix Equation and Roth’s Removal Rule
 * 				<i>Mathematical Gazette</i> <b>82 (495)</b> 423-430
 * 		</li>
 * 		<li>
 * 			Wei, Q., N. Dobigeon, and J. Y. Tourneret (2015): Fast Fusion of Multi-band Images based on
 * 				solving a Sylvester Equation <i>IEEE</i> <b>24 (11)</b> 4109-4121
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Sylvester Equation https://en.wikipedia.org/wiki/Sylvester_equation
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/linearsolver/README.md">Solvers of Linear Systems of Equations</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class BartelsStewartScheme
{
	private double[][] _rhsMatrix = null;
	private boolean _diagnosticsOn = false;
	private SylvesterEquation _sylvesterEquation = null;

	/**
	 * <i>BartelsStewartScheme</i> Constructor
	 * 
	 * @param sylvesterEquation Sylvester Equation Instance
	 * @param rhsMatrix "RHS" Matrix
	 * @param diagnosticsOn Diagnostics-on Flag
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public BartelsStewartScheme (
		final SylvesterEquation sylvesterEquation,
		final double[][] rhsMatrix,
		final boolean diagnosticsOn)
		throws Exception
	{
		if (null == (_sylvesterEquation = sylvesterEquation) ||
			null == (_rhsMatrix = rhsMatrix) ||
			_sylvesterEquation.aSize() != _rhsMatrix.length ||
			_sylvesterEquation.bSize() != _rhsMatrix[0].length)
		{
			throw new Exception ("BartelsStewartScheme Constructor => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Sylvester Equation Instance
	 * 
	 * @return Sylvester Equation Instance
	 */

	public SylvesterEquation sylvesterEquation()
	{
		return _sylvesterEquation;
	}

	/**
	 * Retrieve the "RHS" Matrix
	 * 
	 * @return "RHS" Matrix
	 */

	public boolean diagnosticsOn()
	{
		return _diagnosticsOn;
	}

	/**
	 * Retrieve the Diagnostics On Flag
	 * 
	 * @return Diagnostics On Flag
	 */

	public double[][] rhsMatrix()
	{
		return _rhsMatrix;
	}

	public void solve()
	{
		QR qrA = R1MatrixUtil.QRDecomposition (_sylvesterEquation.squareMatrixA().r2Array());

		System.out.println();

		double[][] u = qrA.q();

		for (int i = 0; i < u.length; ++i) {
			System.out.println (
				"\t| Matrix U => [" + NumberUtil.ArrayRow (u[i], 2, 4, false) + " ]||"
			);
		}

		System.out.println();

		double[][] r = qrA.r();

		for (int i = 0; i < r.length; ++i) {
			System.out.println (
				"\t| Matrix R => [" + NumberUtil.ArrayRow (r[i], 2, 4, false) + " ]||"
			);
		}

		System.out.println();

		QR qrBTranspose = R1MatrixUtil.QRDecomposition (
			_sylvesterEquation.squareMatrixB().transpose().r2Array()
		);

		double[][] v = qrBTranspose.q();

		for (int i = 0; i < v.length; ++i) {
			System.out.println (
				"\t| Matrix V => [" + NumberUtil.ArrayRow (v[i], 2, 4, false) + " ]||"
			);
		}

		System.out.println();

		double[][] s = qrBTranspose.r();

		for (int i = 0; i < s.length; ++i) {
			System.out.println (
				"\t| Matrix S => [" + NumberUtil.ArrayRow (s[i], 2, 4, false) + " ]||"
			);
		}

		double[][] f = R1MatrixUtil.Product (R1MatrixUtil.Transpose (u), _rhsMatrix);

		f = R1MatrixUtil.Product (f, v);

		System.out.println();

		for (int i = 0; i < f.length; ++i) {
			System.out.println (
				"\t| Matrix F => [" + NumberUtil.ArrayRow (f[i], 2, 4, false) + " ]||"
			);
		}

		double[][] y = new double[f.length][f.length];

		for (int i = 0; i < f.length; ++i) {
			for (int j = 0; j < f.length; ++j) {
				y[i][j] = 0.;
			}
		}

		for (int i = f.length - 1; i >= 0 ; --i) {
			for (int j = f.length - 1; j >= 0; --j) {
				double coefficientIJ = 0.;
				double ryCumulativeSum = 0.;
				double syCumulativeSum = 0.;

				for (int k = i; k < f.length; ++k) {
					if (k == i) {
						coefficientIJ += r[i][k];
					} else {
						ryCumulativeSum += r[i][k] * y[k][j];
					}
				}

				for (int k = j; k < f.length; ++k) {
					if (k == j) {
						coefficientIJ += r[i][k];
					} else {
						syCumulativeSum += r[i][k] * y[k][j];
					}
				}

				y[i][j] = (f[i][j] - ryCumulativeSum - syCumulativeSum) / coefficientIJ;
			}
		}

		System.out.println();

		for (int i = 0; i < y.length; ++i) {
			System.out.println (
				"\t| Matrix Y => [" + NumberUtil.ArrayRow (y[i], 2, 4, false) + " ]||"
			);
		}

		System.out.println();
	}

	public static final void main (
		final String[] argumentArray)
		throws Exception
	{
		double[][] matrixA = RdRandomSequence.TwoD (
			3,
			99.,
			true
		);

		double[][] matrixB = RdRandomSequence.TwoD (
			3,
			99.,
			true
		);

		double[][] matrixC = R1MatrixUtil.Sum (matrixA, matrixB);

		BartelsStewartScheme bartelsStewartScheme = new BartelsStewartScheme (
			new SylvesterEquation (
				R1Square.Standard (matrixA),
				R1Square.Standard (matrixB)
			),
			matrixC,
			true
		);

		bartelsStewartScheme.solve();
	}
}
