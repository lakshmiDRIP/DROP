
package org.drip.quant.eigen;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * QREigenComponentExtractor extracts the Eigenvalues and Eigenvectors using QR Decomposition.
 *
 * @author Lakshmi Krishnamurthy
 */

public class QREigenComponentExtractor implements org.drip.quant.eigen.ComponentExtractor {
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
		if (0 >= (_iMaxIteration = iMaxIteration) || !org.drip.quant.common.NumberUtil.IsValid (_dblTolerance
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

	@Override public org.drip.quant.eigen.EigenOutput eigenize (
		final double[][] aadblA)
	{
		org.drip.quant.linearalgebra.QR qr = org.drip.quant.linearalgebra.Matrix.QRDecomposition (aadblA);

		if (null == qr) return null;

		double[][] aadblQ = qr.q();

		double[][] aadblQT = org.drip.quant.linearalgebra.Matrix.Transpose (aadblQ);

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

		while (iIter++ < _iMaxIteration && org.drip.quant.linearalgebra.Matrix.NON_TRIANGULAR ==
			org.drip.quant.linearalgebra.Matrix.TriangularType (aadblV, _dblTolerance)) {
			if (null == (qr = org.drip.quant.linearalgebra.Matrix.QRDecomposition (aadblV =
				org.drip.quant.linearalgebra.Matrix.Product (aadblQT,
					org.drip.quant.linearalgebra.Matrix.Product (aadblV, aadblQ)))))
				return null;

			aadblQT = org.drip.quant.linearalgebra.Matrix.Transpose (aadblQ = qr.q());

			aadblB = org.drip.quant.linearalgebra.Matrix.Product (aadblB, aadblQ);
		}

		if (iIter >= _iMaxIteration) return null;

		for (int i = 0; i < iSize; ++i)
			adblEigenvalue[i] = aadblV[i][i];

		try {
			return new org.drip.quant.eigen.EigenOutput (org.drip.quant.linearalgebra.Matrix.Transpose
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
		final org.drip.quant.eigen.EigenOutput eo)
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

	public org.drip.quant.eigen.EigenComponent[] orderedComponents (
		final double[][] aadblA)
	{
		org.drip.quant.eigen.EigenOutput eo = eigenize (aadblA);

		java.util.List<java.lang.Integer> lsEigenOrder = orderedEigenList (eo);

		if (null == lsEigenOrder) return null;

		int iNumComponent = lsEigenOrder.size();

		double[] adblEigenvalue = eo.eigenvalue();

		double[][] aadblEigenvector = eo.eigenvector();

		org.drip.quant.eigen.EigenComponent[] aEC = new org.drip.quant.eigen.EigenComponent[iNumComponent];

		for (int i = 0; i < iNumComponent; ++i) {
			int iIndex = lsEigenOrder.get (i);

			try {
				aEC[i] = new org.drip.quant.eigen.EigenComponent (aadblEigenvector[iIndex],
					adblEigenvalue[iIndex]);
			} catch (java.lang.Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		return aEC;
	}

	@Override public org.drip.quant.eigen.EigenComponent principalComponent (
		final double[][] aadblA)
	{
		org.drip.quant.eigen.EigenComponent[] aEC = orderedComponents (aadblA);

		return null == aEC ? null : aEC[0];
	}
}
