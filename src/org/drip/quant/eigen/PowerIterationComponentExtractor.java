
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
 * PowerIterationComponentExtractor extracts the Linear System Components using the Power Iteration Method.
 *
 * @author Lakshmi Krishnamurthy
 */

public class PowerIterationComponentExtractor implements org.drip.quant.eigen.ComponentExtractor {
	private int _iMaxIteration = -1;
	private boolean _bToleranceAbsolute = false;
	private double _dblTolerance = java.lang.Double.NaN;

	/**
	 * PowerIterationComponentExtractor Constructor
	 * 
	 * @param iMaxIteration Maximum Number of Iterations
	 * @param dblTolerance Tolerance
	 * @param bToleranceAbsolute Is Tolerance Absolute
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public PowerIterationComponentExtractor (
		final int iMaxIteration,
		final double dblTolerance,
		final boolean bToleranceAbsolute)
		throws java.lang.Exception
	{
		if (0 >= (_iMaxIteration = iMaxIteration) || !org.drip.quant.common.NumberUtil.IsValid (_dblTolerance
			= dblTolerance) || 0. == _dblTolerance)
			throw new java.lang.Exception ("PowerIterationComponentExtractor ctr: Invalid Inputs!");

		_bToleranceAbsolute = bToleranceAbsolute;
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

	/**
	 * Indicate if the specified Tolerance is Absolute
	 * 
	 * @return TRUE - The specified Tolerance is Absolute
	 */

	public boolean isToleranceAbsolute()
	{
		return _bToleranceAbsolute;
	}

	@Override public org.drip.quant.eigen.EigenComponent principalComponent (
		final double[][] aadblA)
	{
		if (null == aadblA) return null;

		int iIter = 0;
		double dblEigenvalue = 0.;
		int iSize = aadblA.length;
		double[] adblEigenvector = new double[iSize];
		double[] adblUpdatedEigenvector = new double[iSize];

		if (0 == iSize || null == aadblA[0] || iSize != aadblA[0].length) return null;

		for (int i = 0; i < iSize; ++i) {
			adblEigenvector[i] = java.lang.Math.random();

			dblEigenvalue += adblEigenvector[i] * adblEigenvector[i];
		}

		double dblEigenvalueOld = (dblEigenvalue = java.lang.Math.sqrt (dblEigenvalue));

		for (int i = 0; i < iSize; ++i)
			adblEigenvector[i] /= dblEigenvalue;

		double dblAbsoluteTolerance = _bToleranceAbsolute ? _dblTolerance : dblEigenvalue * _dblTolerance;
		dblAbsoluteTolerance = dblAbsoluteTolerance > _dblTolerance ? dblAbsoluteTolerance : _dblTolerance;

		while (iIter < _iMaxIteration) {
			for (int i = 0; i < iSize; ++i) {
				adblUpdatedEigenvector[i] = 0.;

				for (int j = 0; j < iSize; ++j)
					adblUpdatedEigenvector[i] += aadblA[i][j] * adblEigenvector[j];
			}

			dblEigenvalue = 0.;

			for (int i = 0; i < iSize; ++i)
				dblEigenvalue += adblUpdatedEigenvector[i] * adblUpdatedEigenvector[i];

			dblEigenvalue = java.lang.Math.sqrt (dblEigenvalue);

			for (int i = 0; i < iSize; ++i)
				adblUpdatedEigenvector[i] /= dblEigenvalue;

			if (dblAbsoluteTolerance > java.lang.Math.abs (dblEigenvalue - dblEigenvalueOld)) break;

			adblEigenvector = adblUpdatedEigenvector;
			dblEigenvalueOld = dblEigenvalue;
			++iIter;
		}

		if (iIter >= _iMaxIteration) return null;

		try {
			return new org.drip.quant.eigen.EigenComponent (adblEigenvector, dblEigenvalue);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override public org.drip.quant.eigen.EigenOutput eigenize (
		final double[][] aadblA)
	{
		return null;
	}
}
