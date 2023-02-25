
package org.drip.numerical.eigen;

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
 * <i>QREigenComponentExtractor</i> extracts the Eigenvalues and Eigenvectors using QR Decomposition.
 * 
 * <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/ComputationalCore.md">Computational Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/NumericalAnalysisLibrary.md">Numerical Analysis Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical">Numerical Quadrature, Differentiation, Eigenization, Linear Algebra, and Utilities</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/numerical/eigen">QR PICE Eigen-Component Extraction Methodologies</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class QREigenComponentExtractor
	implements org.drip.numerical.eigen.ComponentExtractor
{
	private int _maxIterations = -1;
	private double _tolerance = java.lang.Double.NaN;

	/**
	 * QREigenComponentExtractor Constructor
	 * 
	 * @param maxIterations Maximum Number of Iterations
	 * @param tolerance Tolerance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public QREigenComponentExtractor (
		final int maxIterations,
		final double tolerance)
		throws java.lang.Exception
	{
		if (0 >= (_maxIterations = maxIterations) ||
			!org.drip.numerical.common.NumberUtil.IsValid (
				_tolerance = tolerance
			) || 0. == _tolerance
		)
		{
			throw new java.lang.Exception ("QREigenComponentExtractor ctr: Invalid Inputs!");
		}
	}

	/**
	 * Retrieve the Maximum Number of Iterations
	 * 
	 * @return The Maximum Number of Iterations
	 */

	public int maxIterations()
	{
		return _maxIterations;
	}

	/**
	 * Retrieve the Tolerance Level
	 * 
	 * @return The Tolerance Level
	 */

	public double tolerance()
	{
		return _tolerance;
	}

	@Override public org.drip.numerical.eigen.EigenOutput eigenize (
		final double[][] a)
	{
		org.drip.numerical.linearalgebra.QR qr = org.drip.numerical.linearalgebra.Matrix.QRDecomposition (
			a
		);

		if (null == qr)
		{
			return null;
		}

		double[][] q = qr.q();

		double[][] qTranspose = org.drip.numerical.linearalgebra.Matrix.Transpose (
			q
		);

		if (null == qTranspose)
		{
			return null;
		}

		int iterationIndex = 0;
		int eigenComponentCount = a.length;
		double[] eigenvalueArray = new double[eigenComponentCount];
		double[][] b = new double[eigenComponentCount][eigenComponentCount];
		double[][] v = new double[eigenComponentCount][eigenComponentCount];

		if (0 == eigenComponentCount || null == qTranspose[0] || eigenComponentCount != qTranspose[0].length)
		{
			return null;
		}

		for (int rowIndex = 0;
			rowIndex < eigenComponentCount;
			++rowIndex)
		{
			for (int columnIndex = 0;
				columnIndex < eigenComponentCount;
				++columnIndex)
			{
				b[rowIndex][columnIndex] = q[rowIndex][columnIndex];
				v[rowIndex][columnIndex] = a[rowIndex][columnIndex];
			}
		}

		while (iterationIndex++ < _maxIterations &&
			org.drip.numerical.linearalgebra.Matrix.NON_TRIANGULAR ==
			org.drip.numerical.linearalgebra.Matrix.TriangularType (
				v,
				_tolerance
			)
		)
		{
			if (null == (qr = org.drip.numerical.linearalgebra.Matrix.QRDecomposition (
				v = org.drip.numerical.linearalgebra.Matrix.Product (
					qTranspose,
					org.drip.numerical.linearalgebra.Matrix.Product (
						v,
						q
					)
				)
			)))
			{
				return null;
			}

			qTranspose = org.drip.numerical.linearalgebra.Matrix.Transpose (
				q = qr.q()
			);

			b = org.drip.numerical.linearalgebra.Matrix.Product (
				b,
				q
			);
		}

		if (iterationIndex >= _maxIterations)
		{
			return null;
		}

		for (int rowIndex = 0;
			rowIndex < eigenComponentCount;
			++rowIndex)
		{
			eigenvalueArray[rowIndex] = v[rowIndex][rowIndex];
		}

		try
		{
			return new org.drip.numerical.eigen.EigenOutput (
				org.drip.numerical.linearalgebra.Matrix.Transpose (
					b
				),
				eigenvalueArray
			);
		} catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Ordered List of Eigenvalues for the specified Eigen-output
	 * 
	 * @param eigenOutput The Eigen Output
	 * 
	 * @return The Order List
	 */

	public java.util.List<java.lang.Integer> eigenComponentOrderList ( 
		final org.drip.numerical.eigen.EigenOutput eigenOutput)
	{
		if (null == eigenOutput)
		{
			return null;
		}

		double[] eigenvalueArray = eigenOutput.eigenValueArray();

		int eigenComponentCount = eigenvalueArray.length;

		java.util.List<java.lang.Double> eigenValueList = new java.util.ArrayList<java.lang.Double>();

		java.util.List<java.lang.Integer> eigenValueOrder = new java.util.ArrayList<java.lang.Integer>();

		for (int eigenComponentIndex = 0;
			eigenComponentIndex < eigenComponentCount;
			++eigenComponentIndex)
		{
			int eigenValueOrderSize = eigenValueOrder.size();

			if (0 == eigenValueOrderSize)
			{
				eigenValueOrder.add (
					eigenComponentIndex
				);

				eigenValueList.add (
					eigenvalueArray[eigenComponentIndex]
				);
			}
			else
			{
				int insertionIndex = 0;

				for (int eigenValueOrderIndex = 0;
					eigenValueOrderIndex < eigenValueOrderSize;
					++eigenValueOrderIndex)
				{
					if (eigenvalueArray[eigenComponentIndex] <= eigenValueList.get (
						eigenValueOrderIndex
					))
					{
						insertionIndex = eigenValueOrderIndex;
						break;
					}
				}

				eigenValueOrder.add (
					insertionIndex,
					eigenComponentIndex
				);

				eigenValueList.add (
					insertionIndex,
					eigenvalueArray[eigenComponentIndex]
				);
			}
		}

		return eigenValueOrder;
	}

	/**
	 * Generate the Ordered List of Eigen Components arranged by Ascending Eigenvalue
	 * 
	 * @param a Input Matrix
	 * 
	 * @return The Ordered List of Eigen Components arranged by Ascending Eigenvalue
	 */

	public org.drip.numerical.eigen.EigenComponent[] orderedEigenComponentArray (
		final double[][] a)
	{
		org.drip.numerical.eigen.EigenOutput eigenOutput = eigenize (
			a
		);

		java.util.List<java.lang.Integer> eigenComponentOrderList = eigenComponentOrderList (
			eigenOutput
		);

		if (null == eigenComponentOrderList)
		{
			return null;
		}

		double[] eigenValueArray = eigenOutput.eigenValueArray();

		double[][] eigenVectorArray = eigenOutput.eigenVectorArray();

		int eigenComponentCount = eigenComponentOrderList.size();

		org.drip.numerical.eigen.EigenComponent[] eigenComponentArray =
			new org.drip.numerical.eigen.EigenComponent[eigenComponentCount];

		for (int eigenComponentIndex = 0;
			eigenComponentIndex < eigenComponentCount;
			++eigenComponentIndex)
		{
			int eigenComponentOrder = eigenComponentOrderList.get (
				eigenComponentIndex
			);

			try
			{
				eigenComponentArray[eigenComponentIndex] = new org.drip.numerical.eigen.EigenComponent (
					eigenVectorArray[eigenComponentOrder],
					eigenValueArray[eigenComponentOrder]
				);
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();

				return null;
			}
		}

		return eigenComponentArray;
	}

	@Override public org.drip.numerical.eigen.EigenComponent principalComponent (
		final double[][] a)
	{
		org.drip.numerical.eigen.EigenComponent[] eigenComponentArray = orderedEigenComponentArray (
			a
		);

		return null == eigenComponentArray ? null : eigenComponentArray[0];
	}

	/**
	 * Generate the UD Form of the Input Matrix
	 * 
	 * @param a The Input Matrix
	 * 
	 * @return The UD Form
	 */

	public org.drip.numerical.linearalgebra.UD udForm (
		final double[][] a)
	{
		org.drip.numerical.eigen.EigenComponent[] eigenComponentArray = orderedEigenComponentArray (
			a
		);

		if (null == eigenComponentArray)
		{
			return null;
		}

		int eigenComponentCount = eigenComponentArray.length;
		double[][] d = new double[eigenComponentCount][eigenComponentCount];
		double[][] u = new double[eigenComponentCount][];

		for (int eigenComponentIndexI = 0;
			eigenComponentIndexI < eigenComponentCount;
			++eigenComponentIndexI)
		{
			u[eigenComponentIndexI] = eigenComponentArray[eigenComponentIndexI].eigenVector();

			for (int eigenComponentIndexJ = 0;
				eigenComponentIndexJ < eigenComponentCount;
				++eigenComponentIndexJ)
			{
				d[eigenComponentIndexI][eigenComponentIndexJ] = eigenComponentIndexI != eigenComponentIndexJ
					? 0. : eigenComponentArray[eigenComponentIndexI].eigenValue();
			}
		}

		try
		{
			return new org.drip.numerical.linearalgebra.UD (
				u,
				d
			);
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
