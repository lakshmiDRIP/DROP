
package org.drip.numerical.decomposition;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2024 Lakshmi Krishnamurthy
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
 * <i>JordanNormalJ</i> implements the J in the Jordan Normal Form Matrix VJV<sup>-1</sup>. The References
 * 	are:
 * 
 * <br><br>
 *  <ul>
 *  	<li>
 *  		Dunford, N., and J. Schwartz (1963): <i>Linear Operators II: Spectral Theory: Self-adjoint
 *  			Operators in the Hilbert Space</i> <b>Wiley Interscience</b> Hoboken NJ
 *  	</li>
 *  	<li>
 *  		Gradshteyn, I. S., I. M. Ryzhik, Y. V. Geronimus, M. Y. Tseytlin, and A. Jeffrey (2015):
 *  			<i>Tables of Integrals, Series, and Products</i> <b>Academic Press</b> Cambridge MA
 *  	</li>
 *  	<li>
 *  		Guo, J. M., Z. W. Wang, and X. Li (2019): Sharp Upper Bounds of the Spectral Radius of a Graph
 *  			<i>Discrete Mathematics</i> <b>342 (9)</b> 2559-2563
 *  	</li>
 *  	<li>
 *  		Lax, P. D. (2002): <i>Functional Analysis</i> <b>Wiley Interscience</b> Hoboken NJ
 *  	</li>
 *  	<li>
 *  		Wikipedia (2024): Spectral Radius https://en.wikipedia.org/wiki/Spectral_radius
 *  	</li>
 *  </ul>
 *
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/GraphAlgorithmLibrary.md">Graph Algorithm Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/decomposition/README.md">Jordan Normal, UV, and QR Decompositions</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class JordanNormalJ
{
	private double[][] _r1Grid = null;
	private int _size = Integer.MIN_VALUE;
	private JordanNormalJSubM[] _jSubMArray = null;

	/**
	 * <i>JordanNormalJ</i> Constructor
	 * 
	 * @param jSubMArray <i>JSubM</i> Array
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public JordanNormalJ (
		final JordanNormalJSubM[] jSubMArray)
		throws Exception
	{
		if (null == (_jSubMArray = jSubMArray) || 0 == _jSubMArray.length) {
			throw new Exception ("JordanNormalJ Constructor => Invalid Inputs");
		}

		_size = 0;

		for (int i = 0; i < _jSubMArray.length; ++i) {
			if (null == _jSubMArray[i]) {
				throw new Exception ("JordanNormalJ Constructor => Invalid Inputs");
			}

			_size += _jSubMArray[i].mSubI();
		}

		int startIndex = 0;
		_r1Grid = new double[_size][_size];

		for (int i = 0; i < _size; ++i) {
			for (int j = 0; j < _size; ++j) {
				_r1Grid[i][j] = 0.;
			}
		}

		for (int k = 0; k < _jSubMArray.length; ++k) {
			int mSubI = _jSubMArray[k].mSubI();

			double[][] jSubMR1Grid = _jSubMArray[k].r1Grid();

			for (int i = 0; i < mSubI; ++i) {
				for (int j = 0; j < mSubI; ++j) {
					_r1Grid[startIndex + i][startIndex + j] = jSubMR1Grid[i][j];
				}
			}

			startIndex += mSubI;
		}
	}

	/**
	 * Retrieve the <i>JSubM</i> Array
	 * 
	 * @return The <i>JSubM</i> Array
	 */

	public JordanNormalJSubM[] jSubMArray()
	{
		return _jSubMArray;
	}

	/**
	 * Retrieve the R1 Grid
	 * 
	 * @return The R1 Grid
	 */

	public double[][] r1Grid()
	{
		return _r1Grid;
	}

	/**
	 * <i>J</i> to the power of k
	 * 
	 * @param k K
	 * 
	 * @return <i>J</i> to the power of k
	 */

	public double[][] power (
		final int k)
	{
		if (0 >= k) {
			return null;
		}

		int startIndex = 0;
		double[][] r1Grid = new double[_size][_size];

		for (int i = 0; i < _size; ++i) {
			for (int j = 0; j < _size; ++j) {
				r1Grid[i][j] = 0.;
			}
		}

		for (int p = 0; p < _jSubMArray.length; ++p) {
			int mSubI = _jSubMArray[k].mSubI();

			double[][] jSubMR1Grid = _jSubMArray[p].power (k);

			if (null == jSubMR1Grid) {
				return null;
			}

			for (int i = 0; i < mSubI; ++i) {
				for (int j = 0; j < mSubI; ++j) {
					_r1Grid[startIndex + i][startIndex + j] = jSubMR1Grid[i][j];
				}
			}

			startIndex += mSubI;
		}

		return r1Grid;
	}

	/**
	 * Is this Diagonal
	 * 
	 * @return YES - This is Diagonal
	 */

	public boolean isDiagonal()
	{
		return true;
	}
}
