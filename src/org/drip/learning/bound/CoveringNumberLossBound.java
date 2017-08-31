
package org.drip.learning.bound;

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
 * CoveringNumberLossBound provides the Upper Probability Bound that the Loss/Deviation of the Empirical from
 *  the Actual Mean of the given Learner Class exceeds 'epsilon', using the Covering Number Generalization
 *  Bounds. This is expressed as
 *  
 *  						C1 (n) * N (epsilon, n) * exp (-n.epsilon^b/C2),
 *  
 *  where:
 *  	- n is the Size of the Sample
 *  	- 'epsilon' is the Deviation Empirical Mean from the Population Mean
 *  	- C1 (n) is the sample coefficient function
 *  	- C2 is an exponent scaling constant
 *  	- 'b' an exponent ((i.e., the Epsilon Exponent) that depends on the setting (i.e.,
 *  		agnostic/classification/regression/convex etc)
 *  
 *  The References are:
 *  
 *  1) Alon, N., S. Ben-David, N. Cesa Bianchi, and D. Haussler (1997): Scale-sensitive Dimensions, Uniform
 *  	Convergence, and Learnability, Journal of Association of Computational Machinery, 44 (4) 615-631.
 * 
 *  2) Anthony, M., and P. L. Bartlett (1999): Artificial Neural Network Learning - Theoretical Foundations,
 *  	Cambridge University Press, Cambridge, UK.
 *  
 *  3) Kearns, M. J., R. E. Schapire, and L. M. Sellie (1994): Towards Efficient Agnostic Learning, Machine
 *  	Learning, 17 (2) 115-141.
 *  
 *  4) Lee, W. S., P. L. Bartlett, and R. C. Williamson (1998): The Importance of Convexity in Learning with
 *  	Squared Loss, IEEE Transactions on Information Theory, 44 1974-1980.
 * 
 *  5) Vapnik, V. N. (1998): Statistical learning Theory, Wiley, New York.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CoveringNumberLossBound {
	private double _dblExponentScaler = java.lang.Double.NaN;
	private double _dblEpsilonExponent = java.lang.Double.NaN;
	private org.drip.function.definition.R1ToR1 _funcSampleCoefficient = null;

	/**
	 * CoveringNumberLossBound Constructor
	 * 
	 * @param funcSampleCoefficient The Sample Coefficient Function
	 * @param dblEpsilonExponent The Epsilon Exponent
	 * @param dblExponentScaler The Exponent Scaler
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CoveringNumberLossBound (
		final org.drip.function.definition.R1ToR1 funcSampleCoefficient,
		final double dblEpsilonExponent,
		final double dblExponentScaler)
		throws java.lang.Exception
	{
		if (null == (_funcSampleCoefficient = funcSampleCoefficient) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblEpsilonExponent = dblEpsilonExponent) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblExponentScaler = dblExponentScaler))
			throw new java.lang.Exception ("CoveringNumberLossBound ctr: Invalid Inputs");
	}

	/**
	 * Retrieve the Sample Coefficient Function
	 * 
	 * @return The Sample Coefficient Function
	 */

	public org.drip.function.definition.R1ToR1 sampleCoefficient()
	{
		return _funcSampleCoefficient;
	}

	/**
	 * Retrieve the Exponential Epsilon Exponent
	 * 
	 * @return The Exponential Epsilon Exponent
	 */

	public double epsilonExponent()
	{
		return _dblEpsilonExponent;
	}

	/**
	 * Retrieve the Exponent Scaler
	 * 
	 * @return The Exponent Scaler
	 */

	public double exponentScaler()
	{
		return _dblExponentScaler;
	}

	/**
	 * Compute the Upper Bound of the Probability of the Absolute Deviation between the Empirical and the
	 * 	Population Means
	 * 
	 * @param iSampleSize The Sample Size
	 * @param dblEpsilon The Deviation between Population and Empirical Means
	 * 
	 * @return The Upper Bound of the Probability of the Deviation between the Empirical and the Population
	 *  Means
	 * 
	 * @throws java.lang.Exception Thrown if the Upper Bound of the Probability cannot be computed
	 */

	public double deviationProbabilityUpperBound (
		final int iSampleSize,
		final double dblEpsilon)
		throws java.lang.Exception
	{
		if (0 >= iSampleSize || !org.drip.quant.common.NumberUtil.IsValid (dblEpsilon) || 0. >= dblEpsilon)
			throw new java.lang.Exception
				("CoveringNumberLossBound::deviationProbabilityUpperBound => Invalid Inputs");

		return _funcSampleCoefficient.evaluate (iSampleSize) * java.lang.Math.exp (-1. * iSampleSize *
			java.lang.Math.pow (dblEpsilon, _dblEpsilonExponent) / _dblExponentScaler);
	}
}
