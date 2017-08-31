
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
 * MeasureConcentrationExpectationBound provides the Upper Bound of the Expected Loss between Empirical
 * 	Outcome and the Prediction of the given Learner Class using the Concentration of Measure Inequalities.
 *  This is expressed as C * n^a, where n is the Size of the Sample, and 'C' and 'a' are Constants specific
 *  to the Learning Class.
 *  
 *  The References are:
 *  
 *  1) Lugosi, G. (2002): Pattern Classification and Learning Theory, in: L. Györ, editor,
 *   Principles of Non-parametric Learning, 5-62, Springer, Wien.
 * 
 *  2) Boucheron, S., G. Lugosi, and P. Massart (2003): Concentration Inequalities Using the Entropy Method,
 *   Annals of Probability, 31, 1583-1614.
 *
 * @author Lakshmi Krishnamurthy
 */

public class MeasureConcentrationExpectationBound {
	private double _dblConstant = java.lang.Double.NaN;
	private double _dblExponent = java.lang.Double.NaN;

	/**
	 * MeasureConcentrationExpectationBound Constructor
	 * 
	 * @param dblConstant Asymptote Constant
	 * @param dblExponent Asymptote Exponent
	 * 
	 * @throws java.lang.Exception Thrown if the Constant and/or Exponent is Invalid
	 */

	public MeasureConcentrationExpectationBound (
		final double dblConstant,
		final double dblExponent)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblConstant = dblConstant) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblExponent = dblExponent))
			throw new java.lang.Exception ("MeasureConcentrationExpectationBound ctr: Invalid Inputs!");
	}

	/**
	 * Retrieve the Asymptote Constant
	 * 
	 * @return The Asymptote Constant
	 */

	public double constant()
	{
		return _dblConstant;
	}

	/**
	 * Retrieve the Asymptote Exponent
	 * 
	 * @return The Asymptote Exponent
	 */

	public double exponent()
	{
		return _dblExponent;
	}

	/**
	 * Compute the Expected Loss Upper Bound between the Sample and the Population for the specified Sample
	 *  Size
	 * 
	 * @param iSampleSize The Sample Size
	 * 
	 * @return The Expected Loss Upper Bound the Sample and the Population for the specified Sample Size
	 * 
	 * @throws java.lang.Exception Thrown if the Expected Loss Upper Bound cannot be computed
	 */

	public double lossExpectationUpperBound (
		final int iSampleSize)
		throws java.lang.Exception
	{
		if (0 >= iSampleSize)
			throw new java.lang.Exception
				("MeasureConcentrationExpectationBound::lossExpectationUpperBound => Invalid Inputs");

		return _dblConstant * java.lang.Math.pow (iSampleSize, _dblExponent);
	}
}
