
package org.drip.numerical.iterativesolver;

import org.drip.numerical.common.NumberUtil;
import org.drip.numerical.linearalgebra.Matrix;

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
 * <i>SuccessiveOverRelaxation</i> implements the SOR Linear Solution schemes. The References are:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Greenbaum, A. (1997): <i>Iterative Methods for Solving Linear Systems</i> <b>Society for
 * 				Industrial and Applied Mathematics</b> Philadelphia, PA
 * 		</li>
 * 		<li>
 * 			Hackbusch, W. (2016): <i>Iterative Solution of Large Sparse Systems of Equations</i>
 * 				<b>Spring-Verlag</b> Berlin, Germany
 * 		</li>
 * 		<li>
 * 			Wikipedia (2023): Symmetric Successive Over-Relaxation
 * 				https://en.wikipedia.org/wiki/Symmetric_successive_over-relaxation
 * 		</li>
 * 		<li>
 * 			Wikipedia (2024): Successive Over-Relaxation
 * 				https://en.wikipedia.org/wiki/Successive_over-relaxation
 * 		</li>
 * 		<li>
 * 			Young, D. M. (1950): <i>Iterative methods for solving partial difference equations of elliptical
 * 				type</i> <b>Harvard University</b> Cambridge, MA
 * 		</li>
 * 	</ul>
 * 
 * It provides the following functionality:
 *
 *  <ul>
 * 		<li>Construct the R<sup>1</sup> To R<sup>1</sup> Bessel First Kind Frobenius Summation Series</li>
 *  </ul>
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/README.md">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/iterativesolver/README.md">Linear System Iterative Solver Schemes</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class SuccessiveOverRelaxation
{
	private double[] _rhsArray = null;
	private double[][] _squareMatrix = null;
	private SuccessiveOverRelaxationIteratorSetting _iteratorSetting = null;

	/**
	 * Construct an Instance of <i>SuccessiveOverRelaxation</i> using the Standard Solver Settings
	 * 
	 * @param squareMatrix Square Matrix
	 * @param rhsArray RHS Array
	 * 
	 * @return Instance of <i>SuccessiveOverRelaxation</i>
	 */

	public static final SuccessiveOverRelaxation Standard (
		final double[][] squareMatrix,
		final double[] rhsArray)
	{
		try {
			return new SuccessiveOverRelaxation (
				SuccessiveOverRelaxationIteratorSetting.Standard(),
				squareMatrix,
				rhsArray
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected final boolean VectorsMatch (
		final double[] array1,
		final double[] array2)
		throws Exception
	{
		for (int i = 0; i < array1.length; ++i) {
			if (!NumberUtil.IsValid (array1[i]) || !NumberUtil.IsValid (array2[i])) {
				throw new Exception ("SuccessiveOverRelaxation::VectorsMatch => Invalid Array Element(s)");
			}

			double mid = 0.5 * (array1[i] + array2[i]);

			double absoluteDifference = Math.abs (array1[i] - array2[i]);

			if (mid <= _iteratorSetting.absoluteLevelThreshold()) {
				if (_iteratorSetting.absoluteTolerance() < absoluteDifference) {
					return false;
				}
			} else {
				if (_iteratorSetting.relativeTolerance() * mid < absoluteDifference) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * <i>SuccessiveOverRelaxation</i> Constructor
	 * 
	 * @param iteratorSetting Successive Over-Relaxation Iterator Setting
	 * @param squareMatrix Square Matrix
	 * @param rhsArray RHS Array
	 * 
	 * @throws Exception Thrown if the Inputs are Invalid
	 */

	public SuccessiveOverRelaxation (
		final SuccessiveOverRelaxationIteratorSetting iteratorSetting,
		final double[][] squareMatrix,
		final double[] rhsArray)
		throws Exception
	{
		if (null == (_iteratorSetting = iteratorSetting) ||
			!Matrix.IsSquare (_squareMatrix = squareMatrix) ||
			null == (_rhsArray = rhsArray))
		{
			throw new Exception ("SuccessiveOverRelaxation Construction => Invalid Inputs");
		}

		if (_squareMatrix.length != _rhsArray.length) {
			throw new Exception ("SuccessiveOverRelaxation Construction => Invalid Inputs");
		}
	}

	/**
	 * Retrieve the Square Matrix
	 * 
	 * @return Square Matrix
	 */

	public double[][] squareMatrix()
	{
		return _squareMatrix;
	}

	/**
	 * Retrieve the Diagonal Matrix
	 * 
	 * @return Diagonal Matrix
	 */

	public double[][] diagonalMatrix()
	{
		return Matrix.Diagonal (_squareMatrix);
	}

	/**
	 * Retrieve the Strictly Lower Triangular Matrix
	 * 
	 * @return Strictly Lower Triangular Matrix
	 */

	public double[][] strictlyLowerTriangularMatrix()
	{
		return Matrix.StrictlyLowerTriangular (_squareMatrix);
	}

	/**
	 * Retrieve the Strictly Upper Triangular Matrix
	 * 
	 * @return Strictly Upper Triangular Matrix
	 */

	public double[][] strictlyUpperTriangularMatrix()
	{
		return Matrix.StrictlyUpperTriangular (_squareMatrix);
	}

	/**
	 * Retrieve the RHS Array
	 * 
	 * @return RHS Array
	 */

	public double[] rhsArray()
	{
		return _rhsArray;
	}

	/**
	 * Retrieve the Successive Over Relaxation Iterator Setting
	 * 
	 * @return Successive Over Relaxation Iterator Setting
	 */

	public SuccessiveOverRelaxationIteratorSetting iteratorSetting()
	{
		return _iteratorSetting;
	}

	/**
	 * Solve using Forward Substitution
	 * 
	 * @return The Solution Array
	 */

	public double[] forwardSubstitution()
	{
		int iteration = 0;
		double[] updatedUnknownArray = new double[_rhsArray.length];
		double[] previousUnknownArray = new double[_rhsArray.length];

		for (int i = 0; i < updatedUnknownArray.length; ++i) {
			updatedUnknownArray[i] = Math.random();
		}

		int iterationLimit = _iteratorSetting.iterationLimit();

		double relaxationParameter = _iteratorSetting.relaxationParameter();

		try {
			 while (!VectorsMatch (previousUnknownArray, updatedUnknownArray) && iteration < iterationLimit)
			 {
				for (int i = 0; i < previousUnknownArray.length; ++i) {
					previousUnknownArray[i] = updatedUnknownArray[i];
				}

				for (int i = 0; i < previousUnknownArray.length; ++i) {
					updatedUnknownArray[i] = _rhsArray[i];

					for (int j = 0; j < previousUnknownArray.length; ++j) {
						if (j < i) {
							updatedUnknownArray[i] -= _squareMatrix[i][j] * updatedUnknownArray[j];
						} else if (j > i) {
							updatedUnknownArray[i] -= _squareMatrix[i][j] * previousUnknownArray[j];
						}
					}

					updatedUnknownArray[i] = (1. - relaxationParameter) * previousUnknownArray[i] + (
						relaxationParameter * updatedUnknownArray[i] / _squareMatrix[i][i]
					);
				}

				++iteration;
			 }

			return iteration < iterationLimit ? updatedUnknownArray : null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
