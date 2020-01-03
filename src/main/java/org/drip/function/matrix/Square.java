
package org.drip.function.matrix;

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
 * <i>Square</i> implements a Square Matrix. The References are:
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

public class Square
{
	public double[][] _grid = null;

	/**
	 * Square Constructor
	 * 
	 * @param grid Grid of Elements
	 * 
	 * @throws java.lang.Exception Throwjn if the Inputs are Invalid
	 */

	public Square (
		final double[][] grid)
		throws java.lang.Exception
	{
		if (null == (_grid = grid))
		{
			throw new java.lang.Exception (
				"Square Constructor => Invalid Inputs"
			);
		}

		int dimension = _grid.length;

		if (0 == dimension)
		{
			throw new java.lang.Exception (
				"Square Constructor => Invalid Inputs"
			);
		}

		for (int dimensionIndex = 0;
			dimensionIndex < dimension;
			++dimensionIndex)
		{
			if (null == _grid[dimensionIndex] ||
				dimension != _grid[dimensionIndex].length ||
				!org.drip.numerical.common.NumberUtil.IsValid (
					_grid[dimensionIndex]
				)
			)
			{
				throw new java.lang.Exception (
					"Square Constructor => Invalid Inputs"
				);
			}
		}
	}

	/**
	 * Retrieve the Grid of Elements
	 * 
	 * @return Grid of Elements
	 */

	public double[][] grid()
	{
		return _grid;
	}

	/**
	 * Retrieve the Dimension of the Square Matrix
	 * 
	 * @return Dimension of the Square Matrix
	 */

	public int dimension()
	{
		return _grid.length;
	}

	/**
	 * Retrieve the Eigen-Components of the Square Matrix
	 * 
	 * @return The Eigen-Components of the Square Matrix
	 */

	public org.drip.numerical.eigen.EigenOutput eigenize()
	{
		try
		{
			return new org.drip.numerical.eigen.QREigenComponentExtractor (
				100,
				1.e-06
			).eigenize (
				_grid
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Frobenius Covariance
	 * 
	 * @return The Frobenius Covariance
	 */

	public org.drip.function.matrix.FrobeniusCovariance frobeniusCovariance()
	{
		org.drip.function.matrix.FrobeniusCovariance frobeniusCovariance =
			new org.drip.function.matrix.FrobeniusCovariance();

		org.drip.numerical.eigen.EigenOutput eigenOutput = eigenize();

		if (null == eigenOutput)
		{
			return null;
		}

		double[] eigenValueArray = eigenOutput.eigenValueArray();

		int dimension = _grid.length;
		double[][][] eigenShadowArray = new double[dimension][dimension][dimension];

		for (int eigenIndex = 0;
			eigenIndex < dimension;
			++eigenIndex)
		{
			for (int dimensionIndexI = 0;
				dimensionIndexI < dimension;
				++dimensionIndexI)
			{
				for (int dimensionIndexJ = 0;
					dimensionIndexJ < dimension;
					++dimensionIndexJ)
				{
					eigenShadowArray[eigenIndex][dimensionIndexI][dimensionIndexJ] =
						_grid[dimensionIndexI][dimensionIndexJ] - (
							dimensionIndexI == dimensionIndexJ ? eigenValueArray[eigenIndex] : 0.
						);
				}
			}
		}

		for (int componentIndex = 0;
			componentIndex < dimension;
			++componentIndex)
		{
			double[][] frobeniusComponentMatrix = null;
			double componentEigenValue = eigenValueArray[componentIndex];

			for (int eigenIndex = 0;
				eigenIndex < dimension;
				++eigenIndex)
			{
				if (eigenIndex == componentIndex)
				{
					continue;
				}

				if (null == frobeniusComponentMatrix)
				{
					frobeniusComponentMatrix = org.drip.numerical.linearalgebra.Matrix.Scale2D (
						eigenShadowArray[eigenIndex],
						1. / (componentEigenValue - eigenValueArray[eigenIndex])
					);
				}
				else
				{
					frobeniusComponentMatrix = org.drip.numerical.linearalgebra.Matrix.Scale2D (
						org.drip.numerical.linearalgebra.Matrix.Product (
							frobeniusComponentMatrix,
							eigenShadowArray[eigenIndex]
						),
						1. / (componentEigenValue - eigenValueArray[eigenIndex])
					);
				}
			}

			try
			{
				if (!frobeniusCovariance.addComponent (
					componentEigenValue,
					new org.drip.function.matrix.Square (
						frobeniusComponentMatrix
					)
				))
				{
					return null;
				}
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();
			}
		}

		return frobeniusCovariance;
	}

	/**
	 * Compute the Value of the Matrix using the specified Function
	 * 
	 * @param r1ToR1Function The R<sup>1</sup> To R<sup>1</sup> Function
	 * 
	 * @return The Function Matrix Value
	 */

	public double[][] evaluate (
		final org.drip.function.definition.R1ToR1 r1ToR1Function)
	{
		if (null == r1ToR1Function)
		{
			return null;
		}

		int dimension = _grid.length;
		double[][] matrixFunction = null;

		org.drip.function.matrix.FrobeniusCovariance frobeniusCovariance = frobeniusCovariance();

		if (null == frobeniusCovariance)
		{
			return null;
		}

		for (java.util.Map.Entry<java.lang.Double, org.drip.function.matrix.Square> componentMapEntry :
			frobeniusCovariance.componentMap().entrySet())
		{
			double[][] frobeniusComponentFunctionProjection = null;

			try
			{
				frobeniusComponentFunctionProjection = org.drip.numerical.linearalgebra.Matrix.Scale2D (
					componentMapEntry.getValue().grid(),
					r1ToR1Function.evaluate (
						componentMapEntry.getKey()
					)
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}

			if (null == frobeniusComponentFunctionProjection)
			{
				return null;
			}

			if (null == matrixFunction)
			{
				matrixFunction = frobeniusComponentFunctionProjection;
			}
			else
			{
				for (int dimensionIndexI = 0;
					dimensionIndexI < dimension;
					++dimensionIndexI)
				{
					for (int dimensionIndexJ = 0;
						dimensionIndexJ < dimension;
						++dimensionIndexJ)
					{
						matrixFunction[dimensionIndexI][dimensionIndexJ] =
							matrixFunction[dimensionIndexI][dimensionIndexJ] +
							frobeniusComponentFunctionProjection[dimensionIndexI][dimensionIndexJ];
					}
				}
			}
		}

		return matrixFunction;
	}

    /**
     * Compute the Determinant
     *
     * @return The Determinant
     */

	public double determinant()
	{
		org.drip.numerical.eigen.EigenOutput eigenOutput = eigenize();

		if (null == eigenOutput)
		{
			return 0.;
		}

		double[] eigenValueArray = eigenOutput.eigenValueArray();

		double determinant = 1.;
		int dimension = _grid.length;

		for (int eigenIndex = 0;
			eigenIndex < dimension;
	        ++eigenIndex)
		{
	         determinant = determinant * eigenValueArray[eigenIndex];
		}

		return determinant;
    }
}
