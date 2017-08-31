
package org.drip.function.rdtor1;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * AffineBoundMultivariate implements a Bounded Planar Linear R^d To R^1 Function.
 *
 * @author Lakshmi Krishnamurthy
 */

public class AffineBoundMultivariate extends org.drip.function.definition.RdToR1 implements
	org.drip.function.rdtor1.BoundMultivariate, org.drip.function.rdtor1.ConvexMultivariate {
	private boolean _bIsUpper = false;
	private int _iNumTotalVariate = -1;
	private int _iBoundVariateIndex = -1;
	private double _dblBoundValue = java.lang.Double.NaN;

	/**
	 * AffineBoundMultivariate Constructor
	 * 
	 * @param bIsUpper TRUE To The Bound is an Upper Bound
	 * @param iBoundVariateIndex The Bound Variate Index
	 * @param iNumTotalVariate The Total Number of Variates
	 * @param dblBoundValue The Bounding Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AffineBoundMultivariate (
		final boolean bIsUpper,
		final int iBoundVariateIndex,
		final int iNumTotalVariate,
		final double dblBoundValue)
		throws java.lang.Exception
	{
		super (null);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblBoundValue = dblBoundValue) || 0 ==
			(_iNumTotalVariate = iNumTotalVariate) || _iNumTotalVariate <= (_iBoundVariateIndex =
				iBoundVariateIndex))
			throw new java.lang.Exception ("AffineBoundMultivariate Constructor => Invalid Inputs");

		_bIsUpper = bIsUpper;
	}

	@Override public boolean isUpper()
	{
		return _bIsUpper;
	}

	@Override public int boundVariateIndex()
	{
		return _iBoundVariateIndex;
	}

	@Override public double boundValue()
	{
		return _dblBoundValue;
	}

	@Override public boolean violated (
		final double dblVariate)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (dblVariate))
			throw new java.lang.Exception ("AffineBoundMultivariate::violated => Invalid Inputs");

		if (_bIsUpper && dblVariate > _dblBoundValue) return true;

		if (!_bIsUpper && dblVariate < _dblBoundValue) return true;

		return false;
	}

	@Override public int dimension()
	{
		return _iNumTotalVariate;
	}

	@Override public double evaluate (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		if (null == adblVariate || !org.drip.quant.common.NumberUtil.IsValid (adblVariate) ||
			adblVariate.length != dimension())
			throw new java.lang.Exception ("AffineBoundMultivariate::evaluate => Invalid Inputs");

		return _bIsUpper ? _dblBoundValue - adblVariate[_iBoundVariateIndex] :
			adblVariate[_iBoundVariateIndex] - _dblBoundValue;
	}

	@Override public double[] jacobian (
		final double[] adblVariate)
	{
		double[] adblJacobian = new double[_iNumTotalVariate];

		for (int i = 0; i < _iNumTotalVariate; ++i)
			adblJacobian[i] = i == _iBoundVariateIndex ? (_bIsUpper ? -1. : 1.) : 0.;

		return adblJacobian;
	}

	@Override public double[][] hessian (
		final double[] adblVariate)
	{
		int iDimension = dimension();

		double[][] aadblHessian = new double[iDimension][iDimension];

		for (int i = 0; i < iDimension; ++i) {
			for (int j = 0; j < iDimension; ++j)
				aadblHessian[i][j] = 0.;
		}

		return aadblHessian;
	}
}
