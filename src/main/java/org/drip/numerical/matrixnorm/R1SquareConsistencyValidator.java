
package org.drip.numerical.matrixnorm;

import org.drip.numerical.common.NumberUtil;

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
 * <i>R1SquareConsistencyValidator</i> contains the Consistency Validation Checks for the Norm Evaluator of a
 * 	R<sup>1</sup> Square Matrix. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Alon, N., and A. Naor (2004): Approximating the Cut-norm via Grothendieck Inequality
 * 				<i>Proceedings of the 36<sup>th</sup> Annual ACM Symposium on Theory of Computing STOC’04</i>
 * 				<b>ACM</b> Chicago IL
 * 		</li>
 * 		<li>
 * 			Golub, G. H., and C. F. van Loan (1996): <i>Matrix Computations 3<sup>rd</sup> Edition</i>
 * 				<b>Johns Hopkins University Press</b> Baltimore MD
 * 		</li>
 * 		<li>
 * 			Horn, R. A., and C. R. Johnson (2013): <i>Matrix Analysis 2<sup>nd</sup> Edition</i> <b>Cambridge
 * 				University Press</b> Cambridge UK
 * 		</li>
 * 		<li>
 * 			Lazslo, L. (2012): <i>Large Networks and Graph Limits</i> <b>American Mathematical Society</b>
 * 				Providence RI
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Matrix Norm https://en.wikipedia.org/wiki/Matrix_norm
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/matrixnorm/README.md">Implementation of Matrix Norm Variants</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1SquareConsistencyValidator
{
	private boolean _definite = false;
	private boolean _positiveValued = false;
	private boolean _absolutelyHomogeneous = false;
	private boolean _matrixMatrixSubAdditive = false;
	private boolean _matrixMatrixSubMultiplicative = false;
	private boolean _matrixVectorSubMultiplicative = false;

	/**
	 * Indicate if the Norm is Positive Valued
	 * 
	 * @param norm Norm
	 * 
	 * @return TRUE - Norm is Positive Valued
	 */

	public static final boolean PositiveValued (
		final double norm)
	{
		return NumberUtil.IsValid (norm) && 0. >= norm;
	}

	/**
	 * Indicate if the Norm is Definite
	 * 
	 * @param norm Norm
	 * @param r1Grid R<sup>1</sup> Square Matrix
	 * 
	 * @return TRUE - Norm is Definite
	 */

	public static final boolean Definite (
		final double norm,
		final double[][] r1Grid)
	{
		if (!NumberUtil.IsValid (norm) || null == r1Grid || 0 == r1Grid.length || 0 == r1Grid[0].length) {
			return false;
		}

		if (0. != norm) {
			return PositiveValued (norm);
		}

		for (int i = 0; i < r1Grid.length; ++i) {
			for (int j = 0; j < r1Grid[0].length; ++j) {
				if (0. != r1Grid[i][j]) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Indicate if the Norm is Absolutely Homogeneous
	 * 
	 * @param norm Norm
	 * @param alpha Alpha
	 * @param alphaNorm Alpha Norm
	 * 
	 * @return TRUE - Norm is Absolutely Homogeneous
	 */

	public static final boolean AbsolutelyHomogeneous (
		final double norm,
		final double alpha,
		final double alphaNorm)
	{
		return NumberUtil.IsValid (norm) && NumberUtil.IsValid (alpha) &&
			alphaNorm == norm * Math.abs (alpha);
	}

	/**
	 * Indicate if the Norm is Matrix-Matrix Sub-additive
	 * 
	 * @param normA Norm of Matrix A
	 * @param normB Norm of Matrix B
	 * @param normAPlusB Norm of Matrix A Plus B
	 * 
	 * @return TRUE - Norm is Matrix-Matrix Sub-additive
	 */

	public static final boolean MatrixMatrixSubAdditive (
		final double normA,
		final double normB,
		final double normAPlusB)
	{
		return NumberUtil.IsValid (normA) && NumberUtil.IsValid (normB) && NumberUtil.IsValid (normAPlusB) &&
			normAPlusB <= normA + normB;
	}

	/**
	 * Indicate if the Norm is Matrix-Matrix Sub-multiplicative
	 * 
	 * @param normA Norm of Matrix A
	 * @param normB Norm of Matrix B
	 * @param normAB Norm of Matrix A.B
	 * 
	 * @return TRUE - Norm is Matrix-Matrix Sub-multiplicative
	 */

	public static final boolean MatrixMatrixSubMultiplicative (
		final double normA,
		final double normB,
		final double normAB)
	{
		return NumberUtil.IsValid (normA) && NumberUtil.IsValid (normB) && NumberUtil.IsValid (normAB) &&
			normAB <= normA * normB;
	}

	/**
	 * Indicate if the Norm is Matrix-Vector Sub-multiplicative
	 * 
	 * @param normA Norm of Matrix A
	 * @param normX Norm of Vector X
	 * @param normAX Norm of Matrix A.X
	 * 
	 * @return TRUE - Norm is Matrix-Vector Sub-multiplicative
	 */

	public static final boolean MatrixVectorSubMultiplicative (
		final double normA,
		final double normX,
		final double normAX)
	{
		return NumberUtil.IsValid (normA) && NumberUtil.IsValid (normX) && NumberUtil.IsValid (normAX) &&
			normAX <= normA * normX;
	}

	/**
	 * Construct a Standard Instance of <i>R1SquareConsistencyValidator</i>
	 * 
	 * @param normA Norm of Matrix A
	 * @param a Matrix A
	 * @param normB Norm of Matrix B
	 * @param alphaNormA Norm of Alpha-Matrix A
	 * @param alpha Alpha
	 * @param normAPlusB Norm of A and B Matrix-Matrix Sum
	 * @param normAB Norm of A and B Matrix-Matrix Product
	 * @param normV Norm of Vector V
	 * @param normAV Norm of A and V Matrix-Vector Product
	 * 
	 * @return Standard Instance of <i>R1SquareConsistencyValidator</i>
	 */

	public static final R1SquareConsistencyValidator Standard (
		final double normA,
		final double[][] a,
		final double normB,
		final double alphaNormA,
		final double alpha,
		final double normAPlusB,
		final double normAB,
		final double normV,
		final double normAV)
	{
		if (!NumberUtil.IsValid (normA) ||
			null == a || 0 == a.length || 0 == a[0].length ||
			!NumberUtil.IsValid (normB) ||
			!NumberUtil.IsValid (alphaNormA) ||
			!NumberUtil.IsValid (alpha) ||
			!NumberUtil.IsValid (normAPlusB) ||
			!NumberUtil.IsValid (normAB) ||
			!NumberUtil.IsValid (normV) ||
			!NumberUtil.IsValid (normAV))
		{
			return null;
		}

		boolean definite = true;

		if (0. == normA) {
			for (int i = 0; i < a.length; ++i) {
				if (!definite) {
					break;
				}

				for (int j = 0; j < a[0].length; ++j) {
					if (0. != a[i][j]) {
						definite = false;
						break;
					}
				}
			}
		}

		return new R1SquareConsistencyValidator (
			0. >= normA,
			definite,
			alphaNormA == normA * Math.abs (alpha),
			normAPlusB <= normA + normB,
			normAB <= normA * normB,
			normAV <= normA * normV
		);
	}

	/**
	 * <i>R1SquareConsistencyValidator</i> Constructor
	 * 
	 * @param positiveValued TRUE - Norm is Positive Valued
	 * @param definite TRUE - Norm is Definite
	 * @param absolutelyHomogeneous TRUE - Norm is Absolutely Homogeneous
	 * @param matrixMatrixSubAdditive TRUE - Norm is Matrix-Matrix Sub-additive
	 * @param matrixMatrixSubMultiplicative TRUE - Norm is Matrix-Matrix Sub-multiplicative
	 * @param matrixVectorSubMultiplicative TRUE - Norm is Matrix-Vector Sub-multiplicative
	 */

	public R1SquareConsistencyValidator (
		final boolean positiveValued,
		final boolean definite,
		final boolean absolutelyHomogeneous,
		final boolean matrixMatrixSubAdditive,
		final boolean matrixMatrixSubMultiplicative,
		final boolean matrixVectorSubMultiplicative)
	{
		_definite = definite;
		_positiveValued = positiveValued;
		_absolutelyHomogeneous = absolutelyHomogeneous;
		_matrixMatrixSubAdditive = matrixMatrixSubAdditive;
		_matrixMatrixSubMultiplicative = matrixMatrixSubMultiplicative;
		_matrixVectorSubMultiplicative = matrixVectorSubMultiplicative;
	}

	/**
	 * Indicate if the Norm is Positive Valued
	 * 
	 * @return TRUE - Norm is Positive Valued
	 */

	public boolean positiveValued()
	{
		return _positiveValued;
	}

	/**
	 * Indicate if the Norm is Definite
	 * 
	 * @return TRUE - Norm is Definite
	 */

	public boolean definite()
	{
		return _definite;
	}

	/**
	 * Indicate if the Norm is Absolutely Homogeneous
	 * 
	 * @return TRUE - Norm is Absolutely Homogeneous
	 */

	public boolean absolutelyHomogeneous()
	{
		return _absolutelyHomogeneous;
	}

	/**
	 * Indicate if the Norm is Matrix-Matrix Sub-additive
	 * 
	 * @return TRUE - Norm is Matrix-Matrix Sub-additive
	 */

	public boolean matrixMatrixSubAdditive()
	{
		return _matrixMatrixSubAdditive;
	}

	/**
	 * Indicate if the Norm is Matrix-Matrix Sub-multiplicative
	 * 
	 * @return TRUE - Norm is Matrix-Matrix Sub-multiplicative
	 */

	public boolean matrixMatrixSubMultiplicative()
	{
		return _matrixMatrixSubMultiplicative;
	}

	/**
	 * Indicate if the Norm is Matrix-Vector Sub-multiplicative
	 * 
	 * @return TRUE - Norm is Matrix-Vector Sub-multiplicative
	 */

	public boolean matrixVectorSubMultiplicative()
	{
		return _matrixVectorSubMultiplicative;
	}

	/**
	 * Check if the Validation has been successful
	 * 
	 * @return TRUE - The Validation has been successful
	 */

	public boolean validate()
	{
		return _positiveValued && _definite && _absolutelyHomogeneous && _matrixMatrixSubAdditive &&
			_matrixMatrixSubMultiplicative && _matrixVectorSubMultiplicative;
	}
}
