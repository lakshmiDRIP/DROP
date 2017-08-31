
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
 * EigenOutput holds the results of the Eigenization Operation - the Eigenvectors and the Eigenvalues.
 *
 * @author Lakshmi Krishnamurthy
 */

public class EigenOutput {
	private double[] _adblEigenvalue = null;
	private double[][] _aadblEigenvector = null;

	/**
	 * EigenOutput Constructor
	 * 
	 * @param aadblEigenvector Array of Eigenvectors
	 * @param adblEigenvalue Array of Eigenvalues
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public EigenOutput (
		final double[][] aadblEigenvector,
		final double[] adblEigenvalue)
		throws java.lang.Exception
	{
		if (null == (_aadblEigenvector = aadblEigenvector) || null == (_adblEigenvalue = adblEigenvalue))
			throw new java.lang.Exception ("EigenOutput ctr: Invalid Inputs");

		int iNumVector = _adblEigenvalue.length;

		if (0 == iNumVector || iNumVector != _aadblEigenvector.length || null == _aadblEigenvector[0] ||
			iNumVector != _aadblEigenvector[0].length)
			throw new java.lang.Exception ("EigenOutput ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Array of Eigenvectors
	 * 
	 * @return The Array of Eigenvectors
	 */

	public double[][] eigenvector()
	{
		return _aadblEigenvector;
	}

	/**
	 * Retrieve the Array of Eigenvalues
	 * 
	 * @return The Array of Eigenvalues
	 */

	public double[] eigenvalue()
	{
		return _adblEigenvalue;
	}
}
