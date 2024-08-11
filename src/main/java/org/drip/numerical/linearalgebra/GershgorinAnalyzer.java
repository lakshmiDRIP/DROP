
package org.drip.numerical.linearalgebra;

import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.common.R1ClosenessVerifier;
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
 * <i>GershgorinDisc</i> contains the diagonal entry and the "Radius" of a Row of a Square Matrix. The
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

public class GershgorinAnalyzer
{
	private R1Square _squareMatrix = null;
	private GershgorinDisc[] _gershgorinDiscArray = null;

	/**
	 * Construct a <i>GershgorinAnalyzer</i> Instance from the <i>SquareMatrix</i>
	 * 
	 * @param squareMatrix The <i>SquareMatrix</i>
	 * @param useRow TRUE - Use Rows for the Analysis
	 * 
	 * @return The <i>GershgorinAnalyzer</i> Instance
	 */

	public static final GershgorinAnalyzer FromSquareMatrix (
		final R1Square squareMatrix,
		final boolean useRow)
	{
		try {
			return new GershgorinAnalyzer (
				useRow ? squareMatrix : squareMatrix.transpose(),
				R1ClosenessVerifier.Standard()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <i>GershgorinAnalyzer</i> Constructor
	 * 
	 * @param squareMatrix Input Square Matrix
	 * @param r1ClosenessVerifier R<sup>1</sup> Closeness Verifier
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public GershgorinAnalyzer (
		final R1Square squareMatrix,
		final R1ClosenessVerifier r1ClosenessVerifier)
		throws Exception
	{
		if (null == (_squareMatrix = squareMatrix)) {
			throw new Exception ("GershgorinAnalyzer Constructor => Invalid Inouts");
		}

		int matrixSize = squareMatrix.size();

		double[][] r2Array = squareMatrix.r2Array();

		_gershgorinDiscArray = new GershgorinDisc[matrixSize];

		for (int i = 0; i < matrixSize; ++i) {
			if (null == (
				_gershgorinDiscArray[i] = GershgorinDisc.FromRow (r2Array[i], i, r1ClosenessVerifier)
			))
			{
				throw new Exception ("GershgorinAnalyzer Constructor => Invalid Inouts");
			}
		}
	}

	/**
	 * Retrieve the Square Matrix</i>
	 * 
	 * @return Square Matrix
	 */

	public R1Square squareMatrix()
	{
		return _squareMatrix;
	}

	/**
	 * Retrieve the Array of <i>GershgorinDisc</i>
	 * 
	 * @return Array of <i>GershgorinDisc</i>
	 */

	public GershgorinDisc[] gershgorinDiscArray()
	{
		return _gershgorinDiscArray;
	}

	/**
	 * Indicate if the Discs are Diagonally Dominant
	 * 
	 * @return TRUE - The Discs are Diagonally Dominant
	 */

	public boolean isDiagonallyDominant()
	{
		for (GershgorinDisc gershgorinDisc : _gershgorinDiscArray) {
			if (!gershgorinDisc.isDiagonallyDominant()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Construct a "Gershgorin Strengthened" Square Matrix
	 * 
	 * @param t Strengthener "t"
	 * 
	 * @return "Gershgorin Strengthened" Square Matrix
	 */

	public R1Square Strengthen (
		final double t)
	{
		if (!NumberUtil.IsValid (t) || 0. > t || 1. < t) {
			return null;
		}

		int matrixSize = _squareMatrix.size();

		double[][] r2Array = _squareMatrix.r2Array();

		double[][] r2ArrayStrengthened = new double[matrixSize][matrixSize];

		for (int i = 0; i < matrixSize; ++i) {
			for (int j = 0; j < matrixSize; ++j) {
				r2ArrayStrengthened[i][j] = (i == j ? 1. : 1. - t) * r2Array[i][j];
			}
		}

		return R1Square.Standard (r2ArrayStrengthened);
	}

	public static void main (
		final String[] argumentArray)
		throws Exception
	{
		double[][] r2Array = new double[][] {
			{10.0,  1.0,  0.0,   1.0},
			{ 0.2,  8.0,  0.2,   0.2},
			{ 1.0,  1.0,  2.0,   1.0},
			{-1.0, -1.0, -1.0, -11.0},
		};

		GershgorinAnalyzer gershgorinRowAnalyzer = GershgorinAnalyzer.FromSquareMatrix (
			R1Square.Standard (r2Array),
			true
		);

		GershgorinDisc[] gershgorinDiscRowArray = gershgorinRowAnalyzer.gershgorinDiscArray();

		for (int i = 0; i < gershgorinDiscRowArray.length; ++i) {
			System.out.println (
				"\t[" + i + "] => " + gershgorinDiscRowArray[i].diagonalEntry() + " | " +
					gershgorinDiscRowArray[i].radius() + " ||"
			);
		}


		GershgorinAnalyzer gershgorinColumnAnalyzer = GershgorinAnalyzer.FromSquareMatrix (
			R1Square.Standard (r2Array),
			true
		);

		GershgorinDisc[] gershgorinDiscColumnArray = gershgorinColumnAnalyzer.gershgorinDiscArray();

		for (int i = 0; i < gershgorinDiscColumnArray.length; ++i) {
			System.out.println (
				"\t[" + i + "] => " + gershgorinDiscColumnArray[i].diagonalEntry() + " | " +
					gershgorinDiscColumnArray[i].radius() + " ||"
			);
		}
	}
}
