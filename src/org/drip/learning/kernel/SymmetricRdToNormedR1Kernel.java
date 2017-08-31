
package org.drip.learning.kernel;

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
 * SymmetricRdToNormedR1Kernel exposes the Functionality behind the Kernel that is Normed R^d X Normed R^d To
 *  Supremum R^1, that is, a Kernel that symmetric in the Input Metric Vector Space in terms of both the
 *  Metric and the Dimensionality.
 *  
 *  The References are:
 *  
 *  1) Ash, R. (1965): Information Theory, Inter-science New York.
 *  
 *  2) Konig, H. (1986): Eigenvalue Distribution of Compact Operators, Birkhauser, Basel, Switzerland. 
 *  
 *  3) Smola, A. J., A. Elisseff, B. Scholkopf, and R. C. Williamson (2000): Entropy Numbers for Convex
 *  	Combinations and mlps, in: Advances in Large Margin Classifiers, A. Smola, P. Bartlett, B. Scholkopf,
 *  	and D. Schuurmans - editors, MIT Press, Cambridge, MA.
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class SymmetricRdToNormedR1Kernel {
	private org.drip.spaces.metric.RdNormed _rdContinuousInput = null;
	private org.drip.spaces.metric.R1Normed _r1ContinuousOutput = null;

	/**
	 * SymmetricRdToNormedR1Kernel Constructor
	 * 
	 * @param rdContinuousInput The Symmetric Input R^d Metric Vector Space
	 * @param r1ContinuousOutput The Output R^1 Metric Vector Space
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public SymmetricRdToNormedR1Kernel (
		final org.drip.spaces.metric.RdNormed rdContinuousInput,
		final org.drip.spaces.metric.R1Normed r1ContinuousOutput)
		throws java.lang.Exception
	{
		if (null == (_rdContinuousInput = rdContinuousInput) || null == (_r1ContinuousOutput =
			r1ContinuousOutput))
			throw new java.lang.Exception ("SymmetricRdToNormedR1Kernel ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Symmetric Input Metric R^d Vector Space
	 * 
	 * @return The Symmetric Input Metric R^d Vector Space
	 */

	public org.drip.spaces.metric.RdNormed inputMetricVectorSpace()
	{
		return _rdContinuousInput;
	}

	/**
	 * Retrieve the Output R^1 Metric Vector Space
	 * 
	 * @return The Output R^1 Metric Vector Space
	 */

	public org.drip.spaces.metric.R1Normed outputMetricVectorSpace()
	{
		return _r1ContinuousOutput;
	}

	/**
	 * Compute the Feature Space Input Dimension
	 * 
	 * @return The Feature Space Input Dimension
	 */

	public int featureSpaceDimension()
	{
		return _rdContinuousInput.dimension();
	}

	/**
	 * Compute the Kernel's R^d X R^d To R^1 Value
	 * 
	 * @param adblX Validated Vector Instance X
	 * @param adblY Validated Vector Instance Y
	 * 
	 * @return The Kernel's R^d X R^d To R^1 Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are invalid
	 */

	public abstract double evaluate (
		final double[] adblX,
		final double[] adblY)
		throws java.lang.Exception;
}
