
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
 * AffineMultivariate implements a Planar Linear R^d To R^1 Function using a Multivariate Vector.
 *
 * @author Lakshmi Krishnamurthy
 */

public class AffineMultivariate extends org.drip.function.definition.RdToR1 implements
	org.drip.function.rdtor1.ConvexMultivariate {
	private double[] _adblCoefficient = null;
	private double _dblConstant = java.lang.Double.NaN;

	/**
	 * AffineMultivariate Constructor
	 * 
	 * @param adblCoefficient Array of Variate Coefficients
	 * @param dblConstant The Constant Offset
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AffineMultivariate (
		final double[] adblCoefficient,
		final double dblConstant)
		throws java.lang.Exception
	{
		super (null);

		if (null == (_adblCoefficient = adblCoefficient) || 0 == _adblCoefficient.length ||
			!org.drip.quant.common.NumberUtil.IsValid (_adblCoefficient) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblConstant = dblConstant))
			throw new java.lang.Exception ("AffineMultivariate Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Array of the Coefficients
	 * 
	 * @return The Array of the Coefficients
	 */

	public double[] coefficients()
	{
		return _adblCoefficient;
	}

	/**
	 * Retrieve the Constant
	 * 
	 * @return The Constant
	 */

	public double constant()
	{
		return _dblConstant;
	}

	@Override public int dimension()
	{
		return _adblCoefficient.length;
	}

	@Override public double evaluate (
		final double[] adblVariate)
		throws java.lang.Exception
	{
		if (null == adblVariate || !org.drip.quant.common.NumberUtil.IsValid (adblVariate))
			throw new java.lang.Exception ("AffineMultivariate::evaluate => Invalid Inputs");

		double dblValue = 0.;
		int iDimension = adblVariate.length;

		if (iDimension != dimension())
			throw new java.lang.Exception ("AffineMultivariate::evaluate => Invalid Inputs");

		for (int i = 0; i < iDimension; ++i)
			dblValue += adblVariate[i] * _adblCoefficient[i];

		return dblValue + _dblConstant;
	}

	@Override public double[] jacobian (
		final double[] adblVariate)
	{
		return _adblCoefficient;
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
