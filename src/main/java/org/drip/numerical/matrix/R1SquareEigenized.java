
package org.drip.numerical.matrix;

import java.util.Map;

import org.drip.function.definition.R1ToR1;
import org.drip.numerical.eigenization.EigenOutput;
import org.drip.numerical.linearalgebra.R1MatrixUtil;

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
 * <i>R1SquareEigenized</i> implements an R<sup>1</sup> Square Matrix with its Pre-computed Eigen-values and
 * 	Eigen-vectors. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Dunford, N., and J. Schwartz (1963): <i>Linear Operators II: Spectral Theory: Self-adjoint
 * 				Operators in the Hilbert Space</i> <b>Wiley Interscience</b> Hoboken NJ
 * 		</li>
 * 		<li>
 * 			Gradshteyn, I. S., I. M. Ryzhik, Y. V. Geronimus, M. Y. Tseytlin, and A. Jeffrey (2015):
 * 				<i>Tables of Integrals, Series, and Products</i> <b>Academic Press</b> Cambridge MA
 * 		</li>
 * 		<li>
 * 			Guo, J. M., Z. W. Wang, and X. Li (2019): Sharp Upper Bounds of the Spectral Radius of a Graph
 * 				<i>Discrete Mathematics</i> <b>342 (9)</b> 2559-2563
 * 		</li>
 * 		<li>
 * 			Lax, P. D. (2002): <i>Functional Analysis</i> <b>Wiley Interscience</b> Hoboken NJ
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Spectral Radius https://en.wikipedia.org/wiki/Spectral_radius
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/matrix/README.md">Implementation of R<sup>1</sup> C<sup>1</sup> Matrices</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class R1SquareEigenized extends R1Square
{
	private EigenOutput _eigenOutput = null;

	/**
	 * Construct a Standard Instance of <i>R1SquareEigenized</i> from the <i>EigenOutput</i>
	 * 
	 * @param eigenOutput The <i>EigenOutput</i> Instance
	 * 
	 * @return Standard Instance of <i>R1SquareEigenized</i>
	 */

	public static final R1SquareEigenized Standard (
		final EigenOutput eigenOutput)
	{
		if (null == eigenOutput) {
			return null;
		}

		int dimension = eigenOutput.dimension();

		double[][] eigenValueMatrix = new double[dimension][];
		double[][] eigenVectorMatrix = new double[dimension][];

		double[] eigenValueArray = eigenOutput.eigenValueArray();

		double[][] eigenVectorArray = eigenOutput.eigenVectorArray();

		for (int i = 0; i < dimension; ++i) {
			eigenVectorMatrix[i] = eigenVectorArray[i];
		}

		for (int i = 0; i < dimension; ++i) {
			for (int j = 0; j < dimension; ++j) {
				eigenValueMatrix[i][j] = i == j ? eigenValueArray[i] : 0.;
			}
		}

		double[][] r1Grid = R1MatrixUtil.Product (
			eigenValueMatrix,
			R1MatrixUtil.InvertUsingGaussianElimination (eigenVectorMatrix)
		);

		r1Grid = null == r1Grid ? null : R1MatrixUtil.Product (
			eigenVectorMatrix,
			eigenValueMatrix
		);

		return null == r1Grid ? null : new R1SquareEigenized (
			r1Grid,
			eigenOutput
		);
	}

	protected R1SquareEigenized (
		final double[][] r1Grid,
		final EigenOutput eigenOutput)
	{
		super (r1Grid);

		_eigenOutput = eigenOutput;
	}

	/**
	 * Retrieve the Eigen Components and the Eigen Vectors
	 * 
	 * @return Eigen Components and the Eigen Vectors
	 */

	public EigenOutput eigenOutput()
	{
		return _eigenOutput;
	}

	/**
	 * Eigenize and Extract the Components of the Specified Matrix
	 * 
	 * @return The EigenComponents
	 */

	public EigenOutput eigenize()
	{
		return _eigenOutput;
	}

	/**
	 * Perform Singular Value Decomposition and Extract the Components of the Specified Matrix
	 * 
	 * @return The Singular Value Decomposition Components
	 */

	public EigenOutput svd()
	{
		return _eigenOutput;
	}

	/**
	 * Retrieve the Eigenvalue Multiplicity Map
	 * 
	 * @return Eigenvalue Multiplicity Map
	 */

	public Map<Double, Integer> eigenValueMultiplicityMap()
	{
		return _eigenOutput.eigenValueMultiplicityMap();
	}

	/**
	 * Compute the Determinant of the Matrix
	 * 
	 * @return Determinant of the Matrix
	 * 
	 * @throws Exception Thrown if the Determinant cannot be calculated
	 */

	public double determinant()
		throws Exception
	{
		return _eigenOutput.determinant();
	}

	/**
	 * Compute the L<sub>2</sub> Condition Number of the Matrix
	 * 
	 * @return L<sub>2</sub> Condition Number of the Matrix
	 * 
	 * @throws Exception Thrown if the Condition Number cannot be calculated
	 */

	public double conditionNumberL2()
		throws Exception
	{
		return _eigenOutput.conditionNumber();
	}

	/**
	 * Retrieve the Characteristic Polynomial of the Eigenvalues
	 * 
	 * @return Characteristic Polynomial of the Eigenvalues
	 */

	public R1ToR1 characteristicPolynomial()
	{
		return _eigenOutput.characteristicPolynomial();
	}

	/**
	 * Compute the Spectral Radius of the Matrix
	 * 
	 * @return Spectral Radius of the Matrix
	 * 
	 * @throws Exception Thrown if the Spectral Radius cannot be calculated
	 */

	public double spectralRadius()
		throws Exception
	{
		return _eigenOutput.spectralRadius();
	}
}
