
package org.drip.numerical.eigen;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
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
 *  	and computational support.
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

public class QREigenComponentExtractor implements org.drip.numerical.eigen.ComponentExtractor {
	private int _iMaxIteration = -1;
	private double _dblTolerance = java.lang.Double.NaN;

	/**
	 * QREigenComponentExtractor Constructor
	 * 
	 * @param iMaxIteration Maximum Number of Iterations
	 * @param dblTolerance Tolerance
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public QREigenComponentExtractor (
		final int iMaxIteration,
		final double dblTolerance)
		throws java.lang.Exception
	{
		if (0 >= (_iMaxIteration = iMaxIteration) || !org.drip.numerical.common.NumberUtil.IsValid (_dblTolerance
			= dblTolerance) || 0. == _dblTolerance)
			throw new java.lang.Exception ("QREigenComponentExtractor ctr: Invalid Inputs!");
	}

	/**
	 * Retrieve the Maximum Number of Iterations
	 * 
	 * @return The Maximum Number of Iterations
	 */

	public int maxIterations()
	{
		return _iMaxIteration;
	}

	/**
	 * Retrieve the Tolerance Level
	 * 
	 * @return The Tolerance Level
	 */

	public double tolerance()
	{
		return _dblTolerance;
	}

	@Override public org.drip.numerical.eigen.EigenOutput eigenize (
		final double[][] aadblA)
	{
		org.drip.numerical.linearalgebra.QR qr = org.drip.numerical.linearalgebra.Matrix.QRDecomposition (aadblA);

		if (null == qr) return null;

		double[][] aadblQ = qr.q();

		double[][] aadblQT = org.drip.numerical.linearalgebra.Matrix.Transpose (aadblQ);

		if (null == aadblQT) return null;

		int iIter = 0;
		int iSize = aadblA.length;
		double[] adblEigenvalue = new double[iSize];
		double[][] aadblB = new double[iSize][iSize];
		double[][] aadblV = new double[iSize][iSize];

		if (0 == iSize || null == aadblQT[0] || iSize != aadblQT[0].length) return null;

		for (int i = 0; i < iSize; ++i) {
			for (int j = 0; j < iSize; ++j) {
				aadblB[i][j] = aadblQ[i][j];
				aadblV[i][j] = aadblA[i][j];
			}
		}

		while (iIter++ < _iMaxIteration && org.drip.numerical.linearalgebra.Matrix.NON_TRIANGULAR ==
			org.drip.numerical.linearalgebra.Matrix.TriangularType (aadblV, _dblTolerance)) {
			if (null == (qr = org.drip.numerical.linearalgebra.Matrix.QRDecomposition (aadblV =
				org.drip.numerical.linearalgebra.Matrix.Product (aadblQT,
					org.drip.numerical.linearalgebra.Matrix.Product (aadblV, aadblQ)))))
				return null;

			aadblQT = org.drip.numerical.linearalgebra.Matrix.Transpose (aadblQ = qr.q());

			aadblB = org.drip.numerical.linearalgebra.Matrix.Product (aadblB, aadblQ);
		}

		if (iIter >= _iMaxIteration) return null;

		for (int i = 0; i < iSize; ++i)
			adblEigenvalue[i] = aadblV[i][i];

		try {
			return new org.drip.numerical.eigen.EigenOutput (org.drip.numerical.linearalgebra.Matrix.Transpose
				(aadblB), adblEigenvalue);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generate the Order List of Eigenvalues for the specified Eigen-output
	 * 
	 * @param eo The Eigen Output
	 * 
	 * @return The Order List
	 */

	public java.util.List<java.lang.Integer> orderedEigenList ( 
		final org.drip.numerical.eigen.EigenOutput eo)
	{
		if (null == eo) return null;

		double[] adblEigenvalue = eo.eigenvalue();

		int iSize = adblEigenvalue.length;

		java.util.List<java.lang.Double> lsEigenValue = new java.util.ArrayList<java.lang.Double>();

		java.util.List<java.lang.Integer> lsEigenOrder = new java.util.ArrayList<java.lang.Integer>();

		for (int i = 0; i < iSize; ++i) {
			int iNumOrder = lsEigenOrder.size();

			if (0 == iNumOrder) {
				lsEigenOrder.add (i);

				lsEigenValue.add (adblEigenvalue[i]);
			} else {
				int iInsertIndex = 0;

				for (int j = 0; j < iNumOrder; ++j) {
					if (adblEigenvalue[i] <= lsEigenValue.get (j)) {
						iInsertIndex = j;
						break;
					}
				}

				lsEigenOrder.add (iInsertIndex, i);

				lsEigenValue.add (iInsertIndex, adblEigenvalue[i]);
			}
		}

		return lsEigenOrder;
	}

	/**
	 * Generate the Ordered List of Eigen Components arranged by Ascending Eigenvalue
	 * 
	 * @param aadblA Input Matrix
	 * 
	 * @return The Ordered List of Eigen Components arranged by Ascending Eigenvalue
	 */

	public org.drip.numerical.eigen.EigenComponent[] orderedComponents (
		final double[][] aadblA)
	{
		org.drip.numerical.eigen.EigenOutput eo = eigenize (aadblA);

		java.util.List<java.lang.Integer> lsEigenOrder = orderedEigenList (eo);

		if (null == lsEigenOrder) return null;

		int iNumComponent = lsEigenOrder.size();

		double[] adblEigenvalue = eo.eigenvalue();

		double[][] aadblEigenvector = eo.eigenvector();

		org.drip.numerical.eigen.EigenComponent[] aEC = new org.drip.numerical.eigen.EigenComponent[iNumComponent];

		for (int i = 0; i < iNumComponent; ++i) {
			int iIndex = lsEigenOrder.get (i);

			try {
				aEC[i] = new org.drip.numerical.eigen.EigenComponent (aadblEigenvector[iIndex],
					adblEigenvalue[iIndex]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return aEC;
	}

	@Override public org.drip.numerical.eigen.EigenComponent principalComponent (
		final double[][] aadblA)
	{
		org.drip.numerical.eigen.EigenComponent[] aEC = orderedComponents (aadblA);

		return null == aEC ? null : aEC[0];
	}
}
